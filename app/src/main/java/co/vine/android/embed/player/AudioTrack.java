            package co.vine.android.embed.player;

            import android.annotation.SuppressLint;
            import android.annotation.TargetApi;
            import android.media.AudioTimestamp;
            import android.media.MediaFormat;
            import android.os.Build;
            import android.os.ConditionVariable;
            import android.util.Log;
            import co.vine.android.util.AudioUtils;
            import java.lang.reflect.Method;
            import java.nio.BufferUnderflowException;
            import java.nio.ByteBuffer;
            
            
            @TargetApi(16)
    public class AudioTrack {
                private static final int BUFFER_MULTIPLICATION_FACTOR = 4;
                public static final long CURRENT_POSITION_NOT_SET = Long.MIN_VALUE;
                private static final long MAX_AUDIO_TIMESTAMP_OFFSET_US = 5000000;
                private static final long MAX_BUFFER_DURATION_US = 750000;
                private static final long MAX_LATENCY_US = 5000000;
                private static final int MAX_PLAYHEAD_OFFSET_COUNT = 10;
                private static final long MIN_BUFFER_DURATION_US = 250000;
                private static final int MIN_PLAYHEAD_OFFSET_SAMPLE_INTERVAL_US = 30000;
                private static final int MIN_TIMESTAMP_SAMPLE_INTERVAL_US = 500000;
                public static final int RESULT_BUFFER_CONSUMED = 2;
                public static final int RESULT_BUFFER_STALED = 4;
                public static final int RESULT_POSITION_DISCONTINUITY = 1;
                public static final int SESSION_ID_NOT_SET = 0;
                private static final int START_IN_SYNC = 1;
                private static final int START_NEED_SYNC = 2;
                private static final int START_NOT_SET = -1;
                private static final String TAG = "AudioTrack";
                private static final int UNKNOWN_AC3_BITRATE = 0;
                private int ac3Bitrate;
                private boolean audioTimestampSet;
                private android.media.AudioTrack audioTrack;
                private AudioTrackUtil audioTrackUtil;
                private int bufferSize;
                private int channelConfig;
                private int encoding;
                private int frameSize;
                private Method getLatencyMethod;
                private boolean isAc3;
                private long lastPlayheadSampleTimeUs;
                private long lastTimestampSampleTimeUs;
                private long latencyUs;
                private int mLastSize;
                private int minBufferSize;
                private int nextPlayheadOffsetIndex;
                private int playheadOffsetCount;
                private final long[] playheadOffsets;
                private final ConditionVariable releasingConditionVariable = new ConditionVariable(true);
                private long resumeSystemTimeUs;
                private int sampleRate;
                private long smoothedPlayheadOffsetUs;
                private int startMediaTimeState;
                private long startMediaTimeUs;
                private long submittedBytes;
                private byte[] temporaryBuffer;
                private int temporaryBufferOffset;
                private int temporaryBufferSize;
                private float volume;

        static  ConditionVariable access$100(AudioTrack x0) {
            return x0.releasingConditionVariable;
                }

        public int getLastSize() {
            return this.mLastSize;
                }

                
        private static class C0375C {
                    private static final long MICROS_PER_SECOND = 1000000;

            private C0375C() {
                    }
                }

        private static class Util {
                    private static final int SDK_INT = Build.VERSION.SDK_INT;

            private Util() {
                    }

            static  int access$000() {
                return SDK_INT;
                    }
                }

        public static class InitializationException extends Exception {
                    public final int audioTrackState;

            public InitializationException(int audioTrackState, int sampleRate, int channelConfig, int bufferSize) {
                super("AudioTrack init failed: " + audioTrackState + ", Config(" + sampleRate + ", " + channelConfig + ", " + bufferSize + ")");
                this.audioTrackState = audioTrackState;
                    }
                }

        public static class WriteException extends Exception {
                    public final int errorCode;

            public WriteException(int errorCode) {
                super("AudioTrack write failed: " + errorCode);
                this.errorCode = errorCode;
                    }
                }

       public AudioTrack() {
           if (Util.access$000() >= 18) {
                        try {
                   this.getLatencyMethod = android.media.AudioTrack.class.getMethod("getLatency", (Class[]) null);
                        } catch (NoSuchMethodException e) {
                        }
                    }
           this.playheadOffsets = new long[10];
           this.volume = 1.0f;
           this.startMediaTimeState = -1;
                }

       public boolean isInitialized() {
                    return this.audioTrack != null;
                }

       public long getCurrentPositionUs(boolean sourceEnded) {
                    long currentPositionUs;
           if (!hasCurrentPositionUs()) {
               return Long.MIN_VALUE;
                    }
           if (this.audioTrack.getPlayState() == 3) {
               maybeSampleSyncParams();
                    }
           long systemClockUs = System.nanoTime() / 1000;
           if (this.audioTimestampSet) {
               long presentationDiff = systemClockUs - (this.audioTrackUtil.getTimestampNanoTime() / 1000);
               long framesDiff = durationUsToFrames(presentationDiff);
               long currentFramePosition = this.audioTrackUtil.getTimestampFramePosition() + framesDiff;
               return framesToDurationUs(currentFramePosition) + this.startMediaTimeUs;
                    }
           if (this.playheadOffsetCount == 0) {
               currentPositionUs = this.audioTrackUtil.getPlaybackHeadPositionUs() + this.startMediaTimeUs;
                    } else {
               currentPositionUs = this.smoothedPlayheadOffsetUs + systemClockUs + this.startMediaTimeUs;
                    }
           if (!sourceEnded) {
               return currentPositionUs - this.latencyUs;
                    }
           return currentPositionUs;
                }

       public int initialize() throws InitializationException {
           return initialize(0);
                }

       public int initialize(int sessionId) throws InitializationException {
           this.releasingConditionVariable.block();
           if (sessionId == 0) {
               this.audioTrack = new android.media.AudioTrack(3, this.sampleRate, this.channelConfig, this.encoding, this.bufferSize, 1);
                    } else {
               this.audioTrack = new android.media.AudioTrack(3, this.sampleRate, this.channelConfig, this.encoding, this.bufferSize, 1, sessionId);
                    }
           checkAudioTrackInitialized();
           if (Util.access$000() >= 19) {
               this.audioTrackUtil = new AudioTrackUtilV19(this.audioTrack);
                    } else {
               this.audioTrackUtil = new AudioTrackUtil(this.audioTrack);
                    }
           setVolume(this.volume);
           return this.audioTrack.getAudioSessionId();
                }

       public void reconfigure(MediaFormat format) {
           reconfigure(format, 2, 0);
                }

                @SuppressLint({"InlinedApi"})
       public void reconfigure(MediaFormat format, int encoding, int specifiedBufferSize) {
                    int channelConfig;
           int channelCount = format.getInteger("channel-count");
                    switch (channelCount) {
                        case 1:
                   channelConfig = 4;
                            break;
                        case 2:
                   channelConfig = 12;
                            break;
                        case 3:
                        case 4:
                        case 5:
                        case 7:
                        default:
                   throw new IllegalArgumentException("Unsupported channel count: " + channelCount);
                        case 6:
                   channelConfig = 252;
                            break;
                        case 8:
                   channelConfig = 1020;
                            break;
                    }
           int sampleRate = format.getInteger("sample-rate");
           boolean isAc3 = encoding == 5 || encoding == 6;
           if (!isInitialized() || this.sampleRate != sampleRate || this.channelConfig != channelConfig || this.isAc3 || isAc3) {
               reset();
               this.encoding = encoding;
               this.sampleRate = sampleRate;
               this.channelConfig = channelConfig;
               this.isAc3 = isAc3;
               this.ac3Bitrate = 0;
               this.frameSize = channelCount * 2;
               this.minBufferSize = android.media.AudioTrack.getMinBufferSize(sampleRate, channelConfig, encoding);
               if (specifiedBufferSize != 0) {
                   this.bufferSize = specifiedBufferSize;
                   return;
                        }
               int multipliedBufferSize = this.minBufferSize * 4;
               int minAppBufferSize = ((int) durationUsToFrames(MIN_BUFFER_DURATION_US)) * this.frameSize;
               int maxAppBufferSize = (int) Math.max(this.minBufferSize, durationUsToFrames(MAX_BUFFER_DURATION_US) * ((long) this.frameSize));
               if (multipliedBufferSize >= minAppBufferSize) {
                            minAppBufferSize = multipliedBufferSize > maxAppBufferSize ? maxAppBufferSize : multipliedBufferSize;
                        }
                        this.bufferSize = minAppBufferSize;
                    }
                }

       public void play() {
           if (isInitialized()) {
               this.resumeSystemTimeUs = System.nanoTime() / 1000;
               this.audioTrack.play();
                    }
                }

       public void handleDiscontinuity() {
           if (this.startMediaTimeState == 1) {
               this.startMediaTimeState = 2;
                    }
                }

       public int handleBuffer(ByteBuffer buffer, int offset, int size, long presentationTimeUs) throws WriteException {
                    int bytesToWrite;
           if (size == 0) {
               return 2;
                    }
           int result = 0;
           if (this.temporaryBufferSize == 0) {
               if (this.isAc3 && this.ac3Bitrate == 0) {
                   int unscaledAc3Bitrate = size * 8 * this.sampleRate;
                   this.ac3Bitrate = (768000 + unscaledAc3Bitrate) / 1536000;
                        }
               long bufferStartTime = presentationTimeUs - framesToDurationUs(bytesToFrames(size));
                        if (this.startMediaTimeUs == -1) {
                   this.startMediaTimeUs = Math.max(0L, bufferStartTime);
                   this.startMediaTimeState = 1;
                        } else {
                   long expectedBufferStartTime = this.startMediaTimeUs + framesToDurationUs(bytesToFrames(this.submittedBytes));
                   if (this.startMediaTimeState == 1 && Math.abs(expectedBufferStartTime - bufferStartTime) > 200000) {
                       Log.e(TAG, "Discontinuity detected [expected " + expectedBufferStartTime + ", got " + bufferStartTime + "]");
                       this.startMediaTimeState = 2;
                            }
                   if (this.startMediaTimeState == 2) {
                       this.startMediaTimeUs += bufferStartTime - expectedBufferStartTime;
                       this.startMediaTimeState = 1;
                       result = 0 | 1;
                            }
                        }
                    }
           if (this.temporaryBufferSize == 0) {
               if (offset >= buffer.limit()) {
                   return 2;
                        }
               buffer.position(offset);
               this.mLastSize = Math.min(size, buffer.remaining());
               int size2 = this.mLastSize;
               this.temporaryBufferSize = size2;
               if (Util.access$000() < 21) {
                   if (this.temporaryBuffer == null || this.temporaryBuffer.length < size2) {
                       this.temporaryBuffer = new byte[size2];
                            }
                            try {
                       buffer.get(this.temporaryBuffer, 0, size2);
                       this.temporaryBufferOffset = 0;
                            } catch (BufferUnderflowException e) {
                       Log.e(TAG, "Want " + size2 + " has " + buffer.remaining());
                       throw e;
                            }
                        }
                    }
           int bytesWritten = 0;
           if (Util.access$000() < 21) {
               int bytesPending = (int) (this.submittedBytes - (this.audioTrackUtil.getPlaybackHeadPosition() * ((long) this.frameSize)));
               int bytesToWrite2 = this.bufferSize - bytesPending;
               if (bytesToWrite2 > 0 && (bytesToWrite = Math.min(this.temporaryBufferSize, bytesToWrite2)) > 0) {
                   bytesWritten = this.audioTrack.write(this.temporaryBuffer, this.temporaryBufferOffset, bytesToWrite);
                   if (bytesWritten > 0) {
                       this.temporaryBufferOffset += bytesWritten;
                            } else {
                       if (this.temporaryBufferOffset >= 4) {
                           Log.i(TAG, "Failed to write " + bytesToWrite);
                                }
                       if (this.temporaryBufferSize < 100) {
                           this.temporaryBufferOffset = 0;
                           this.temporaryBufferSize = 0;
                           return 4;
                                }
                            }
                        }
                    } else {
               bytesWritten = writeNonBlockingV21(this.audioTrack, buffer, this.temporaryBufferSize);
                    }
           if (bytesWritten < 0) {
               throw new WriteException(bytesWritten);
                    }
           this.temporaryBufferSize -= bytesWritten;
           this.submittedBytes += (long) bytesWritten;
           if (this.temporaryBufferSize == 0) {
               result |= 2;
                    }
           if (bytesWritten == 0 && this.temporaryBufferSize != 0 && !hasPendingData()) {
               this.temporaryBufferOffset = 0;
               this.temporaryBufferSize = 0;
               return 4;
                    }
           return result;
                }

       public long getTemporaryBufferSize() {
           return this.temporaryBufferSize;
                }

                @TargetApi(21)
       private static int writeNonBlockingV21(android.media.AudioTrack audioTrack, ByteBuffer buffer, int size) {
           return audioTrack.write(buffer, size, 1);
                }

       public boolean hasPendingData() {
                    return isInitialized() && bytesToFrames(this.submittedBytes) > this.audioTrackUtil.getPlaybackHeadPosition();
                }

       public boolean hasEnoughDataToBeginPlayback() {
                    return this.submittedBytes > ((long) ((this.minBufferSize * 3) / 2));
                }

       public void setVolume(float volume) {
           this.volume = volume;
           if (isInitialized()) {
               if (Util.access$000() >= 21) {
                   setVolumeV21(this.audioTrack, volume);
                        } else {
                   setVolumeV3(this.audioTrack, volume);
                        }
                    }
                }

                @TargetApi(21)
       private static void setVolumeV21(android.media.AudioTrack audioTrack, float volume) {
           audioTrack.setVolume(volume);
                }

       private static void setVolumeV3(android.media.AudioTrack audioTrack, float volume) {
           audioTrack.setStereoVolume(volume, volume);
                }

       public void pause() {
           if (isInitialized()) {
               resetSyncParams();
               this.audioTrack.pause();
                    }
                }

       public void reset() {
           if (isInitialized()) {
               this.submittedBytes = 0L;
               this.temporaryBufferSize = 0;
               this.startMediaTimeUs = -1L;
               resetSyncParams();
               int playState = this.audioTrack.getPlayState();
               if (playState == 3) {
                   this.audioTrack.pause();
                        }
               android.media.AudioTrack toRelease = this.audioTrack;
               this.audioTrack = null;
               this.audioTrackUtil = null;
               this.releasingConditionVariable.close();
               new C03741(toRelease).start();
                    }
                }

                
       class C03741 extends Thread {
                    final  android.media.AudioTrack val$toRelease;

           C03741(android.media.AudioTrack audioTrack) {
                        this.val$toRelease = audioTrack;
                    }

                    @Override 
           public void run() {
                        try {
                   this.val$toRelease.release();
                   AudioTrack.access$100(AudioTrack.this).open();
                        } catch (Throwable th) {
                            AudioTrack.access$100(AudioTrack.this).open();
                            throw th;
                        }
                    }
                }

       private boolean hasCurrentPositionUs() {
                    return isInitialized() && this.startMediaTimeUs != -1;
                }

       private void maybeSampleSyncParams() {
           long playbackPositionUs = this.audioTrackUtil.getPlaybackHeadPositionUs();
                    if (playbackPositionUs != 0) {
               long systemClockUs = System.nanoTime() / 1000;
                        if (systemClockUs - this.lastPlayheadSampleTimeUs >= 30000) {
                   this.playheadOffsets[this.nextPlayheadOffsetIndex] = playbackPositionUs - systemClockUs;
                   this.nextPlayheadOffsetIndex = (this.nextPlayheadOffsetIndex + 1) % 10;
                   if (this.playheadOffsetCount < 10) {
                       this.playheadOffsetCount++;
                            }
                   this.lastPlayheadSampleTimeUs = systemClockUs;
                   this.smoothedPlayheadOffsetUs = 0L;
                            for (int i = 0; i < this.playheadOffsetCount; i++) {
                       this.smoothedPlayheadOffsetUs += this.playheadOffsets[i] / ((long) this.playheadOffsetCount);
                            }
                        }
                        if (systemClockUs - this.lastTimestampSampleTimeUs >= 500000) {
                   this.audioTimestampSet = this.audioTrackUtil.updateTimestamp();
                   if (this.audioTimestampSet) {
                       long audioTimestampUs = this.audioTrackUtil.getTimestampNanoTime() / 1000;
                       long audioTimestampFramePosition = this.audioTrackUtil.getTimestampFramePosition();
                                if (audioTimestampUs < this.resumeSystemTimeUs) {
                           this.audioTimestampSet = false;
                                } else if (Math.abs(audioTimestampUs - systemClockUs) > 5000000) {
                           this.audioTimestampSet = false;
                           Log.w(TAG, "Spurious audio timestamp (system clock mismatch): " + audioTimestampFramePosition + ", " + audioTimestampUs + ", " + systemClockUs + ", " + playbackPositionUs);
                                } else if (Math.abs(framesToDurationUs(audioTimestampFramePosition) - playbackPositionUs) > 5000000) {
                           this.audioTimestampSet = false;
                           Log.w(TAG, "Spurious audio timestamp (frame position mismatch): " + audioTimestampFramePosition + ", " + audioTimestampUs + ", " + systemClockUs + ", " + playbackPositionUs);
                                }
                            }
                   if (this.getLatencyMethod != null) {
                                try {
                           this.latencyUs = (((long) ((Integer) this.getLatencyMethod.invoke(this.audioTrack, (Object[]) null)).intValue()) * 1000) - framesToDurationUs(bytesToFrames(this.bufferSize));
                           this.latencyUs = Math.max(this.latencyUs, 0L);
                                    if (this.latencyUs > 5000000) {
                               Log.w(TAG, "Ignoring impossibly large audio latency: " + this.latencyUs);
                               this.latencyUs = 0L;
                                    }
                                } catch (Exception e) {
                           this.getLatencyMethod = null;
                                }
                            }
                   this.lastTimestampSampleTimeUs = systemClockUs;
                        }
                    }
                }

       private void checkAudioTrackInitialized() throws InitializationException {
           int state = this.audioTrack.getState();
           if (state == 1) {
               return;
                    }
                    try {
               this.audioTrack.release();
               this.audioTrack = null;
                    } catch (Exception e) {
               this.audioTrack = null;
                    } catch (Throwable th) {
                        this.audioTrack = null;
                        throw th;
                    }
           throw new InitializationException(state, this.sampleRate, this.channelConfig, this.bufferSize);
                }

       private long bytesToFrames(long byteCount) {
           if (this.isAc3) {
               if (this.ac3Bitrate == 0) {
                            return 0L;
                        }
               return ((8 * byteCount) * ((long) this.sampleRate)) / ((long) (this.ac3Bitrate * 1000));
                    }
           return byteCount / ((long) this.frameSize);
                }

       private long framesToDurationUs(long frameCount) {
           return (AudioUtils.MICROS_PER_SECOND * frameCount) / ((long) this.sampleRate);
                }

       private long durationUsToFrames(long durationUs) {
           return (((long) this.sampleRate) * durationUs) / AudioUtils.MICROS_PER_SECOND;
                }

       private void resetSyncParams() {
           this.smoothedPlayheadOffsetUs = 0L;
           this.playheadOffsetCount = 0;
           this.nextPlayheadOffsetIndex = 0;
           this.lastPlayheadSampleTimeUs = 0L;
           this.audioTimestampSet = false;
           this.lastTimestampSampleTimeUs = 0L;
                }

       private static class AudioTrackUtil {
                    protected final android.media.AudioTrack audioTrack;
                    private long lastRawPlaybackHeadPosition;
                    private long rawPlaybackHeadWrapCount;
                    private final int sampleRate;

           public AudioTrackUtil(android.media.AudioTrack audioTrack) {
               this.audioTrack = audioTrack;
               this.sampleRate = audioTrack.getSampleRate();
                    }

           public long getPlaybackHeadPosition() {
               long rawPlaybackHeadPosition = 4294967295L & ((long) this.audioTrack.getPlaybackHeadPosition());
                        if (this.lastRawPlaybackHeadPosition > rawPlaybackHeadPosition) {
                   this.rawPlaybackHeadWrapCount++;
                        }
               this.lastRawPlaybackHeadPosition = rawPlaybackHeadPosition;
               return (this.rawPlaybackHeadWrapCount << 32) + rawPlaybackHeadPosition;
                    }

           public long getPlaybackHeadPositionUs() {
               return (getPlaybackHeadPosition() * AudioUtils.MICROS_PER_SECOND) / ((long) this.sampleRate);
                    }

           public boolean updateTimestamp() {
               return false;
                    }

           public long getTimestampNanoTime() {
               throw new UnsupportedOperationException();
                    }

           public long getTimestampFramePosition() {
               throw new UnsupportedOperationException();
                    }
                }

                @TargetApi(19)
       private static class AudioTrackUtilV19 extends AudioTrackUtil {
                    private final AudioTimestamp audioTimestamp;
                    private long lastRawTimestampFramePosition;
                    private long lastTimestampFramePosition;
                    private long rawTimestampFramePositionWrapCount;

           public AudioTrackUtilV19(android.media.AudioTrack audioTrack) {
               super(audioTrack);
               this.audioTimestamp = new AudioTimestamp();
                    }

                    @Override 
           public boolean updateTimestamp() {
               boolean updated = this.audioTrack.getTimestamp(this.audioTimestamp);
               if (updated) {
                   long rawFramePosition = this.audioTimestamp.framePosition;
                            if (this.lastRawTimestampFramePosition > rawFramePosition) {
                       this.rawTimestampFramePositionWrapCount++;
                            }
                   this.lastRawTimestampFramePosition = rawFramePosition;
                   this.lastTimestampFramePosition = (this.rawTimestampFramePositionWrapCount << 32) + rawFramePosition;
                        }
               return updated;
                    }

                    @Override 
           public long getTimestampNanoTime() {
               return this.audioTimestamp.nanoTime;
                    }

                    @Override 
           public long getTimestampFramePosition() {
               return this.lastTimestampFramePosition;
                    }
                }
            }
