            package com.google.android.exoplayer;

            import android.annotation.TargetApi;
            import android.media.AudioTimestamp;
            import android.media.AudioTrack;
            import android.media.MediaCodec;
            import android.os.ConditionVariable;
            import android.os.Handler;
            import android.util.Log;
            import com.google.android.exoplayer.drm.DrmSessionManager;
            import com.google.android.exoplayer.util.Assertions;
            import com.google.android.exoplayer.util.MimeTypes;
            import com.google.android.exoplayer.util.Util;
            import java.lang.reflect.Method;
            import java.nio.ByteBuffer;
            
            
            @TargetApi(16)
    public class MediaCodecAudioTrackRenderer extends MediaCodecTrackRenderer {
                public static final float DEFAULT_MIN_BUFFER_MULTIPLICATION_FACTOR = 4.0f;
                private static final long MAX_AUDIO_TIMSTAMP_OFFSET_US = 10000000;
                private static final int MAX_PLAYHEAD_OFFSET_COUNT = 10;
                private static final long MICROS_PER_SECOND = 1000000;
                private static final int MIN_PLAYHEAD_OFFSET_SAMPLE_INTERVAL_US = 30000;
                private static final int MIN_TIMESTAMP_SAMPLE_INTERVAL_US = 500000;
                public static final int MSG_SET_VOLUME = 1;
                private static final int START_IN_SYNC = 1;
                private static final int START_NEED_SYNC = 2;
                private static final int START_NOT_SET = 0;
                private static final String TAG = "MediaCodecAudioTrackRenderer";
                private int audioSessionId;
                private final AudioTimestampCompat audioTimestampCompat;
                private boolean audioTimestampSet;
                private AudioTrack audioTrack;
                private Method audioTrackGetLatencyMethod;
                private long audioTrackLatencyUs;
                private final ConditionVariable audioTrackReleasingConditionVariable;
                private long audioTrackResumeSystemTimeUs;
                private int audioTrackStartMediaTimeState;
                private long audioTrackStartMediaTimeUs;
                private int bufferSize;
                private int channelConfig;
                private final EventListener eventListener;
                private int frameSize;
                private long lastPlayheadSampleTimeUs;
                private long lastRawPlaybackHeadPosition;
                private long lastReportedCurrentPositionUs;
                private long lastTimestampSampleTimeUs;
                private final float minBufferMultiplicationFactor;
                private int minBufferSize;
                private int nextPlayheadOffsetIndex;
                private int playheadOffsetCount;
                private final long[] playheadOffsets;
                private long rawPlaybackHeadWrapCount;
                private int sampleRate;
                private long smoothedPlayheadOffsetUs;
                private long submittedBytes;
                private byte[] temporaryBuffer;
                private int temporaryBufferOffset;
                private int temporaryBufferSize;
                private float volume;

                private interface AudioTimestampCompat {
                    long getFramePosition();

                    long getNanoTime();

                    boolean initTimestamp(AudioTrack audioTrack);
                }

                public interface EventListener extends MediaCodecTrackRenderer.EventListener {
                    void onAudioTrackInitializationError(AudioTrackInitializationException audioTrackInitializationException);
                }

        static  ConditionVariable access$100(MediaCodecAudioTrackRenderer x0) {
            return x0.audioTrackReleasingConditionVariable;
                }

        static  EventListener access$200(MediaCodecAudioTrackRenderer x0) {
            return x0.eventListener;
                }

        public static class AudioTrackInitializationException extends Exception {
                    public final int audioTrackState;

            public AudioTrackInitializationException(int audioTrackState, int sampleRate, int channelConfig, int bufferSize) {
                super("AudioTrack init failed: " + audioTrackState + ", Config(" + sampleRate + ", " + channelConfig + ", " + bufferSize + ")");
                this.audioTrackState = audioTrackState;
                    }
                }

       public MediaCodecAudioTrackRenderer(SampleSource source) {
           this(source, (DrmSessionManager) null, true);
                }

       public MediaCodecAudioTrackRenderer(SampleSource source, DrmSessionManager drmSessionManager, boolean playClearSamplesWithoutKeys) {
           this(source, drmSessionManager, playClearSamplesWithoutKeys, null, null);
                }

       public MediaCodecAudioTrackRenderer(SampleSource source, Handler eventHandler, EventListener eventListener) {
           this(source, null, true, eventHandler, eventListener);
                }

       public MediaCodecAudioTrackRenderer(SampleSource source, DrmSessionManager drmSessionManager, boolean playClearSamplesWithoutKeys, Handler eventHandler, EventListener eventListener) {
           this(source, drmSessionManager, playClearSamplesWithoutKeys, 4.0f, eventHandler, eventListener);
                }

       public MediaCodecAudioTrackRenderer(SampleSource source, float minBufferMultiplicationFactor, Handler eventHandler, EventListener eventListener) {
           this(source, null, true, minBufferMultiplicationFactor, eventHandler, eventListener);
                }

       public MediaCodecAudioTrackRenderer(SampleSource source, DrmSessionManager drmSessionManager, boolean playClearSamplesWithoutKeys, float minBufferMultiplicationFactor, Handler eventHandler, EventListener eventListener) {
           super(source, drmSessionManager, playClearSamplesWithoutKeys, eventHandler, eventListener);
           Assertions.checkState(minBufferMultiplicationFactor >= 1.0f);
           this.minBufferMultiplicationFactor = minBufferMultiplicationFactor;
           this.eventListener = eventListener;
           this.audioTrackReleasingConditionVariable = new ConditionVariable(true);
           if (Util.SDK_INT >= 19) {
               this.audioTimestampCompat = new AudioTimestampCompatV19();
                    } else {
               this.audioTimestampCompat = new NoopAudioTimestampCompat(null);
                    }
           if (Util.SDK_INT >= 18) {
                        try {
                   this.audioTrackGetLatencyMethod = AudioTrack.class.getMethod("getLatency", (Class[]) null);
                        } catch (NoSuchMethodException e) {
                        }
                    }
           this.playheadOffsets = new long[10];
           this.volume = 1.0f;
                }

                @Override 
       protected boolean isTimeSource() {
           return true;
                }

                @Override 
       protected boolean handlesMimeType(String mimeType) {
                    return MimeTypes.isAudio(mimeType) && super.handlesMimeType(mimeType);
                }

                @Override 
       protected void onEnabled(long timeUs, boolean joining) {
           super.onEnabled(timeUs, joining);
           this.lastReportedCurrentPositionUs = 0L;
                }

                @Override 
       protected void doSomeWork(long timeUs) throws ExoPlaybackException {
           super.doSomeWork(timeUs);
           maybeSampleSyncParams();
                }

                @Override 
       protected void onOutputFormatChanged(android.media.MediaFormat format) {
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
                        default:
                   throw new IllegalArgumentException("Unsupported channel count: " + channelCount);
                        case 6:
                   channelConfig = 252;
                            break;
                    }
           int sampleRate = format.getInteger("sample-rate");
           if (this.audioTrack == null || this.sampleRate != sampleRate || this.channelConfig != channelConfig) {
               releaseAudioTrack();
               this.sampleRate = sampleRate;
               this.channelConfig = channelConfig;
               this.minBufferSize = AudioTrack.getMinBufferSize(sampleRate, channelConfig, 2);
               this.bufferSize = (int) (this.minBufferMultiplicationFactor * this.minBufferSize);
               this.frameSize = channelCount * 2;
                    }
                }

       private void initAudioTrack() throws ExoPlaybackException {
           this.audioTrackReleasingConditionVariable.block();
           if (this.audioSessionId == 0) {
               this.audioTrack = new AudioTrack(3, this.sampleRate, this.channelConfig, 2, this.bufferSize, 1);
               checkAudioTrackInitialized();
               this.audioSessionId = this.audioTrack.getAudioSessionId();
               onAudioSessionId(this.audioSessionId);
                    } else {
               this.audioTrack = new AudioTrack(3, this.sampleRate, this.channelConfig, 2, this.bufferSize, 1, this.audioSessionId);
               checkAudioTrackInitialized();
                    }
           this.audioTrack.setStereoVolume(this.volume, this.volume);
           if (getState() == 3) {
               this.audioTrackResumeSystemTimeUs = System.nanoTime() / 1000;
               this.audioTrack.play();
                    }
                }

       private void checkAudioTrackInitialized() throws ExoPlaybackException {
           int audioTrackState = this.audioTrack.getState();
           if (audioTrackState == 1) {
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
           AudioTrackInitializationException exception = new AudioTrackInitializationException(audioTrackState, this.sampleRate, this.channelConfig, this.bufferSize);
           notifyAudioTrackInitializationError(exception);
           throw new ExoPlaybackException(exception);
                }

       protected void onAudioSessionId(int audioSessionId) {
                }

       private void releaseAudioTrack() {
           if (this.audioTrack != null) {
               this.submittedBytes = 0L;
               this.temporaryBufferSize = 0;
               this.lastRawPlaybackHeadPosition = 0L;
               this.rawPlaybackHeadWrapCount = 0L;
               this.audioTrackStartMediaTimeUs = 0L;
               this.audioTrackStartMediaTimeState = 0;
               resetSyncParams();
               int playState = this.audioTrack.getPlayState();
               if (playState == 3) {
                   this.audioTrack.pause();
                        }
               AudioTrack toRelease = this.audioTrack;
               this.audioTrack = null;
               this.audioTrackReleasingConditionVariable.close();
               new C08541(toRelease).start();
                    }
                }

                
       class C08541 extends Thread {
                    final  AudioTrack val$toRelease;

           C08541(AudioTrack audioTrack) {
                        this.val$toRelease = audioTrack;
                    }

                    @Override 
           public void run() {
                        try {
                   this.val$toRelease.release();
                   MediaCodecAudioTrackRenderer.access$100(MediaCodecAudioTrackRenderer.this).open();
                        } catch (Throwable th) {
                            MediaCodecAudioTrackRenderer.access$100(MediaCodecAudioTrackRenderer.this).open();
                            throw th;
                        }
                    }
                }

                @Override 
       protected void onStarted() {
           super.onStarted();
           if (this.audioTrack != null) {
               this.audioTrackResumeSystemTimeUs = System.nanoTime() / 1000;
               this.audioTrack.play();
                    }
                }

                @Override 
       protected void onStopped() {
           super.onStopped();
           if (this.audioTrack != null) {
               resetSyncParams();
               this.audioTrack.pause();
                    }
                }

                @Override 
       protected boolean isReady() {
                    return getPendingFrameCount() > 0 || (super.isReady() && getSourceState() == 2);
                }

                @Override 
       protected long getCurrentPositionUs() {
                    long currentPositionUs;
           long systemClockUs = System.nanoTime() / 1000;
           if (this.audioTrack == null || this.audioTrackStartMediaTimeState == 0) {
               currentPositionUs = super.getCurrentPositionUs();
                    } else if (this.audioTimestampSet) {
               long presentationDiff = systemClockUs - (this.audioTimestampCompat.getNanoTime() / 1000);
               long framesDiff = durationUsToFrames(presentationDiff);
               long currentFramePosition = this.audioTimestampCompat.getFramePosition() + framesDiff;
               currentPositionUs = framesToDurationUs(currentFramePosition) + this.audioTrackStartMediaTimeUs;
                    } else {
               if (this.playheadOffsetCount == 0) {
                   currentPositionUs = getPlayheadPositionUs() + this.audioTrackStartMediaTimeUs;
                        } else {
                   currentPositionUs = this.smoothedPlayheadOffsetUs + systemClockUs + this.audioTrackStartMediaTimeUs;
                        }
               if (!isEnded()) {
                   currentPositionUs -= this.audioTrackLatencyUs;
                        }
                    }
           long currentPositionUs2 = Math.max(this.lastReportedCurrentPositionUs, currentPositionUs);
           this.lastReportedCurrentPositionUs = currentPositionUs2;
           return currentPositionUs2;
                }

       private void maybeSampleSyncParams() {
           if (this.audioTrack != null && this.audioTrackStartMediaTimeState != 0 && getState() == 3) {
               long playheadPositionUs = getPlayheadPositionUs();
               long systemClockUs = System.nanoTime() / 1000;
                        if (systemClockUs - this.lastPlayheadSampleTimeUs >= 30000) {
                   this.playheadOffsets[this.nextPlayheadOffsetIndex] = playheadPositionUs - systemClockUs;
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
                   this.audioTimestampSet = this.audioTimestampCompat.initTimestamp(this.audioTrack);
                   if (this.audioTimestampSet) {
                       long audioTimestampUs = this.audioTimestampCompat.getNanoTime() / 1000;
                                if (audioTimestampUs < this.audioTrackResumeSystemTimeUs) {
                           this.audioTimestampSet = false;
                                } else if (Math.abs(audioTimestampUs - systemClockUs) > MAX_AUDIO_TIMSTAMP_OFFSET_US) {
                           this.audioTimestampSet = false;
                           Log.w(TAG, "Spurious audio timestamp: " + this.audioTimestampCompat.getFramePosition() + ", " + audioTimestampUs + ", " + systemClockUs);
                                }
                            }
                   if (this.audioTrackGetLatencyMethod != null) {
                                try {
                           this.audioTrackLatencyUs = (((long) ((Integer) this.audioTrackGetLatencyMethod.invoke(this.audioTrack, (Object[]) null)).intValue()) * 1000) - framesToDurationUs(this.bufferSize / this.frameSize);
                           this.audioTrackLatencyUs = Math.max(this.audioTrackLatencyUs, 0L);
                                } catch (Exception e) {
                           this.audioTrackGetLatencyMethod = null;
                                }
                            }
                   this.lastTimestampSampleTimeUs = systemClockUs;
                        }
                    }
                }

       private void resetSyncParams() {
           this.smoothedPlayheadOffsetUs = 0L;
           this.playheadOffsetCount = 0;
           this.nextPlayheadOffsetIndex = 0;
           this.lastPlayheadSampleTimeUs = 0L;
           this.audioTimestampSet = false;
           this.lastTimestampSampleTimeUs = 0L;
                }

       private long getPlayheadPositionUs() {
           return framesToDurationUs(getPlaybackHeadPosition());
                }

       private long framesToDurationUs(long frameCount) {
           return (1000000 * frameCount) / ((long) this.sampleRate);
                }

       private long durationUsToFrames(long durationUs) {
           return (((long) this.sampleRate) * durationUs) / 1000000;
                }

                @Override 
       protected void onDisabled() {
           super.onDisabled();
           releaseAudioTrack();
           this.audioSessionId = 0;
                }

                @Override 
       protected void seekTo(long timeUs) throws ExoPlaybackException {
           super.seekTo(timeUs);
           releaseAudioTrack();
           this.lastReportedCurrentPositionUs = 0L;
                }

                @Override 
       protected boolean processOutputBuffer(long timeUs, MediaCodec codec, ByteBuffer buffer, MediaCodec.BufferInfo bufferInfo, int bufferIndex, boolean shouldSkip) throws ExoPlaybackException {
           if (shouldSkip) {
               codec.releaseOutputBuffer(bufferIndex, false);
                        this.codecCounters.skippedOutputBufferCount++;
               if (this.audioTrackStartMediaTimeState == 1) {
                   this.audioTrackStartMediaTimeState = 2;
                        }
               return true;
                    }
           if (this.temporaryBufferSize == 0) {
               long bufferStartTime = bufferInfo.presentationTimeUs - framesToDurationUs(bufferInfo.size / this.frameSize);
               if (this.audioTrackStartMediaTimeState == 0) {
                   this.audioTrackStartMediaTimeUs = Math.max(0L, bufferStartTime);
                   this.audioTrackStartMediaTimeState = 1;
                        } else {
                   long expectedBufferStartTime = this.audioTrackStartMediaTimeUs + framesToDurationUs(this.submittedBytes / ((long) this.frameSize));
                   if (this.audioTrackStartMediaTimeState == 1 && Math.abs(expectedBufferStartTime - bufferStartTime) > 200000) {
                       Log.e(TAG, "Discontinuity detected [expected " + expectedBufferStartTime + ", got " + bufferStartTime + "]");
                       this.audioTrackStartMediaTimeState = 2;
                            }
                   if (this.audioTrackStartMediaTimeState == 2) {
                       this.audioTrackStartMediaTimeUs += bufferStartTime - expectedBufferStartTime;
                       this.lastReportedCurrentPositionUs = 0L;
                            }
                        }
               if (this.temporaryBuffer == null || this.temporaryBuffer.length < bufferInfo.size) {
                   this.temporaryBuffer = new byte[bufferInfo.size];
                        }
               buffer.position(bufferInfo.offset);
               buffer.get(this.temporaryBuffer, 0, bufferInfo.size);
               this.temporaryBufferOffset = 0;
               this.temporaryBufferSize = bufferInfo.size;
                    }
           if (this.audioTrack == null) {
               initAudioTrack();
                    }
           int bytesPending = (int) (this.submittedBytes - (getPlaybackHeadPosition() * ((long) this.frameSize)));
           int bytesToWrite = this.bufferSize - bytesPending;
           if (bytesToWrite > 0) {
               int bytesToWrite2 = Math.min(this.temporaryBufferSize, bytesToWrite);
               this.audioTrack.write(this.temporaryBuffer, this.temporaryBufferOffset, bytesToWrite2);
               this.temporaryBufferOffset += bytesToWrite2;
               this.temporaryBufferSize -= bytesToWrite2;
               this.submittedBytes += (long) bytesToWrite2;
               if (this.temporaryBufferSize == 0) {
                   codec.releaseOutputBuffer(bufferIndex, false);
                            this.codecCounters.renderedOutputBufferCount++;
                   return true;
                        }
                    }
           return false;
                }

       private long getPlaybackHeadPosition() {
           long rawPlaybackHeadPosition = 4294967295L & ((long) this.audioTrack.getPlaybackHeadPosition());
                    if (this.lastRawPlaybackHeadPosition > rawPlaybackHeadPosition) {
               this.rawPlaybackHeadWrapCount++;
                    }
           this.lastRawPlaybackHeadPosition = rawPlaybackHeadPosition;
           return (this.rawPlaybackHeadWrapCount << 32) + rawPlaybackHeadPosition;
                }

       private int getPendingFrameCount() {
           if (this.audioTrack == null) {
                        return 0;
                    }
                    return (int) ((this.submittedBytes / ((long) this.frameSize)) - getPlaybackHeadPosition());
                }

                @Override 
       public void handleMessage(int messageType, Object message) throws ExoPlaybackException {
           if (messageType == 1) {
               setVolume(((Float) message).floatValue());
                    } else {
               super.handleMessage(messageType, message);
                    }
                }

       private void setVolume(float volume) {
           this.volume = volume;
           if (this.audioTrack != null) {
               this.audioTrack.setStereoVolume(volume, volume);
                    }
                }

                
       class RunnableC08552 implements Runnable {
                    final  AudioTrackInitializationException val$e;

           RunnableC08552(AudioTrackInitializationException audioTrackInitializationException) {
                        this.val$e = audioTrackInitializationException;
                    }

                    @Override 
           public void run() {
               MediaCodecAudioTrackRenderer.access$200(MediaCodecAudioTrackRenderer.this).onAudioTrackInitializationError(this.val$e);
                    }
                }

       private void notifyAudioTrackInitializationError(AudioTrackInitializationException e) {
           if (this.eventHandler != null && this.eventListener != null) {
               this.eventHandler.post(new RunnableC08552(e));
                    }
                }

       private static final class NoopAudioTimestampCompat implements AudioTimestampCompat {
           private NoopAudioTimestampCompat() {
                    }

            NoopAudioTimestampCompat(C08541 x0) {
               this();
                    }

                    @Override 
           public boolean initTimestamp(AudioTrack audioTrack) {
               return false;
                    }

                    @Override 
           public long getNanoTime() {
               throw new UnsupportedOperationException();
                    }

                    @Override 
           public long getFramePosition() {
               throw new UnsupportedOperationException();
                    }
                }

                @TargetApi(19)
       private static final class AudioTimestampCompatV19 implements AudioTimestampCompat {
                    private final AudioTimestamp audioTimestamp = new AudioTimestamp();

                    @Override 
           public boolean initTimestamp(AudioTrack audioTrack) {
               return audioTrack.getTimestamp(this.audioTimestamp);
                    }

                    @Override 
           public long getNanoTime() {
               return this.audioTimestamp.nanoTime;
                    }

                    @Override 
           public long getFramePosition() {
               return this.audioTimestamp.framePosition;
                    }
                }
            }
