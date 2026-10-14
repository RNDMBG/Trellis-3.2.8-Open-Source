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
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            @TargetApi(16)
/* 50 */    public class AudioTrack {
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

/* 51 */        static /* synthetic */ ConditionVariable access$100(AudioTrack x0) {
/* 52 */            return x0.releasingConditionVariable;
                }

/* 56 */        public int getLastSize() {
/* 57 */            return this.mLastSize;
                }

                /* JADX INFO: renamed from: co.vine.android.embed.player.AudioTrack$C */
/* 58 */        private static class C0375C {
                    private static final long MICROS_PER_SECOND = 1000000;

/* 59 */            private C0375C() {
                    }
                }

/* 62 */        private static class Util {
                    private static final int SDK_INT = Build.VERSION.SDK_INT;

/* 63 */            private Util() {
                    }

/* 63 */            static /* synthetic */ int access$000() {
/* 64 */                return SDK_INT;
                    }
                }

/* 78 */        public static class InitializationException extends Exception {
                    public final int audioTrackState;

/* 79 */            public InitializationException(int audioTrackState, int sampleRate, int channelConfig, int bufferSize) {
/* 80 */                super("AudioTrack init failed: " + audioTrackState + ", Config(" + sampleRate + ", " + channelConfig + ", " + bufferSize + ")");
/* 82 */                this.audioTrackState = audioTrackState;
                    }
                }

/* 96 */        public static class WriteException extends Exception {
                    public final int errorCode;

/* 97 */            public WriteException(int errorCode) {
/* 98 */                super("AudioTrack write failed: " + errorCode);
/* 99 */                this.errorCode = errorCode;
                    }
                }

/* 208 */       public AudioTrack() {
/* 211 */           if (Util.access$000() >= 18) {
                        try {
/* 213 */                   this.getLatencyMethod = android.media.AudioTrack.class.getMethod("getLatency", (Class[]) null);
                        } catch (NoSuchMethodException e) {
                        }
                    }
/* 219 */           this.playheadOffsets = new long[10];
/* 220 */           this.volume = 1.0f;
/* 221 */           this.startMediaTimeState = -1;
                }

/* 228 */       public boolean isInitialized() {
                    return this.audioTrack != null;
                }

/* 243 */       public long getCurrentPositionUs(boolean sourceEnded) {
                    long currentPositionUs;
/* 244 */           if (!hasCurrentPositionUs()) {
/* 245 */               return Long.MIN_VALUE;
                    }
/* 248 */           if (this.audioTrack.getPlayState() == 3) {
/* 249 */               maybeSampleSyncParams();
                    }
/* 252 */           long systemClockUs = System.nanoTime() / 1000;
/* 254 */           if (this.audioTimestampSet) {
/* 256 */               long presentationDiff = systemClockUs - (this.audioTrackUtil.getTimestampNanoTime() / 1000);
/* 257 */               long framesDiff = durationUsToFrames(presentationDiff);
/* 259 */               long currentFramePosition = this.audioTrackUtil.getTimestampFramePosition() + framesDiff;
/* 260 */               return framesToDurationUs(currentFramePosition) + this.startMediaTimeUs;
                    }
/* 262 */           if (this.playheadOffsetCount == 0) {
/* 264 */               currentPositionUs = this.audioTrackUtil.getPlaybackHeadPositionUs() + this.startMediaTimeUs;
                    } else {
/* 269 */               currentPositionUs = this.smoothedPlayheadOffsetUs + systemClockUs + this.startMediaTimeUs;
                    }
/* 271 */           if (!sourceEnded) {
/* 272 */               return currentPositionUs - this.latencyUs;
                    }
/* 276 */           return currentPositionUs;
                }

/* 284 */       public int initialize() throws InitializationException {
/* 285 */           return initialize(0);
                }

/* 300 */       public int initialize(int sessionId) throws InitializationException {
/* 301 */           this.releasingConditionVariable.block();
/* 303 */           if (sessionId == 0) {
/* 304 */               this.audioTrack = new android.media.AudioTrack(3, this.sampleRate, this.channelConfig, this.encoding, this.bufferSize, 1);
                    } else {
/* 308 */               this.audioTrack = new android.media.AudioTrack(3, this.sampleRate, this.channelConfig, this.encoding, this.bufferSize, 1, sessionId);
                    }
/* 312 */           checkAudioTrackInitialized();
/* 313 */           if (Util.access$000() >= 19) {
/* 314 */               this.audioTrackUtil = new AudioTrackUtilV19(this.audioTrack);
                    } else {
/* 316 */               this.audioTrackUtil = new AudioTrackUtil(this.audioTrack);
                    }
/* 318 */           setVolume(this.volume);
/* 319 */           return this.audioTrack.getAudioSessionId();
                }

/* 326 */       public void reconfigure(MediaFormat format) {
/* 327 */           reconfigure(format, 2, 0);
                }

                @SuppressLint({"InlinedApi"})
/* 341 */       public void reconfigure(MediaFormat format, int encoding, int specifiedBufferSize) {
                    int channelConfig;
/* 342 */           int channelCount = format.getInteger("channel-count");
                    switch (channelCount) {
                        case 1:
/* 346 */                   channelConfig = 4;
                            break;
                        case 2:
/* 349 */                   channelConfig = 12;
                            break;
                        case 3:
                        case 4:
                        case 5:
                        case 7:
                        default:
/* 358 */                   throw new IllegalArgumentException("Unsupported channel count: " + channelCount);
                        case 6:
/* 352 */                   channelConfig = 252;
                            break;
                        case 8:
/* 355 */                   channelConfig = 1020;
                            break;
                    }
/* 361 */           int sampleRate = format.getInteger("sample-rate");
/* 364 */           boolean isAc3 = encoding == 5 || encoding == 6;
/* 365 */           if (!isInitialized() || this.sampleRate != sampleRate || this.channelConfig != channelConfig || this.isAc3 || isAc3) {
/* 371 */               reset();
/* 373 */               this.encoding = encoding;
/* 374 */               this.sampleRate = sampleRate;
/* 375 */               this.channelConfig = channelConfig;
/* 376 */               this.isAc3 = isAc3;
/* 377 */               this.ac3Bitrate = 0;
/* 378 */               this.frameSize = channelCount * 2;
/* 379 */               this.minBufferSize = android.media.AudioTrack.getMinBufferSize(sampleRate, channelConfig, encoding);
/* 381 */               if (specifiedBufferSize != 0) {
/* 382 */                   this.bufferSize = specifiedBufferSize;
/* 392 */                   return;
                        }
/* 384 */               int multipliedBufferSize = this.minBufferSize * 4;
/* 385 */               int minAppBufferSize = ((int) durationUsToFrames(MIN_BUFFER_DURATION_US)) * this.frameSize;
/* 386 */               int maxAppBufferSize = (int) Math.max(this.minBufferSize, durationUsToFrames(MAX_BUFFER_DURATION_US) * ((long) this.frameSize));
/* 388 */               if (multipliedBufferSize >= minAppBufferSize) {
                            minAppBufferSize = multipliedBufferSize > maxAppBufferSize ? maxAppBufferSize : multipliedBufferSize;
                        }
                        this.bufferSize = minAppBufferSize;
                    }
                }

/* 397 */       public void play() {
/* 398 */           if (isInitialized()) {
/* 399 */               this.resumeSystemTimeUs = System.nanoTime() / 1000;
/* 400 */               this.audioTrack.play();
                    }
                }

/* 408 */       public void handleDiscontinuity() {
/* 409 */           if (this.startMediaTimeState == 1) {
/* 410 */               this.startMediaTimeState = 2;
                    }
                }

/* 430 */       public int handleBuffer(ByteBuffer buffer, int offset, int size, long presentationTimeUs) throws WriteException {
                    int bytesToWrite;
/* 431 */           if (size == 0) {
/* 432 */               return 2;
                    }
/* 435 */           int result = 0;
/* 436 */           if (this.temporaryBufferSize == 0) {
/* 437 */               if (this.isAc3 && this.ac3Bitrate == 0) {
/* 440 */                   int unscaledAc3Bitrate = size * 8 * this.sampleRate;
/* 441 */                   this.ac3Bitrate = (768000 + unscaledAc3Bitrate) / 1536000;
                        }
/* 447 */               long bufferStartTime = presentationTimeUs - framesToDurationUs(bytesToFrames(size));
                        if (this.startMediaTimeUs == -1) {
/* 449 */                   this.startMediaTimeUs = Math.max(0L, bufferStartTime);
/* 450 */                   this.startMediaTimeState = 1;
                        } else {
/* 453 */                   long expectedBufferStartTime = this.startMediaTimeUs + framesToDurationUs(bytesToFrames(this.submittedBytes));
/* 455 */                   if (this.startMediaTimeState == 1 && Math.abs(expectedBufferStartTime - bufferStartTime) > 200000) {
/* 457 */                       Log.e(TAG, "Discontinuity detected [expected " + expectedBufferStartTime + ", got " + bufferStartTime + "]");
/* 459 */                       this.startMediaTimeState = 2;
                            }
/* 461 */                   if (this.startMediaTimeState == 2) {
/* 464 */                       this.startMediaTimeUs += bufferStartTime - expectedBufferStartTime;
/* 465 */                       this.startMediaTimeState = 1;
/* 466 */                       result = 0 | 1;
                            }
                        }
                    }
/* 471 */           if (this.temporaryBufferSize == 0) {
/* 472 */               if (offset >= buffer.limit()) {
/* 473 */                   return 2;
                        }
/* 475 */               buffer.position(offset);
/* 476 */               this.mLastSize = Math.min(size, buffer.remaining());
/* 477 */               int size2 = this.mLastSize;
/* 478 */               this.temporaryBufferSize = size2;
/* 479 */               if (Util.access$000() < 21) {
/* 481 */                   if (this.temporaryBuffer == null || this.temporaryBuffer.length < size2) {
/* 482 */                       this.temporaryBuffer = new byte[size2];
                            }
                            try {
/* 485 */                       buffer.get(this.temporaryBuffer, 0, size2);
/* 490 */                       this.temporaryBufferOffset = 0;
                            } catch (BufferUnderflowException e) {
/* 487 */                       Log.e(TAG, "Want " + size2 + " has " + buffer.remaining());
/* 488 */                       throw e;
                            }
                        }
                    }
/* 494 */           int bytesWritten = 0;
/* 495 */           if (Util.access$000() < 21) {
/* 497 */               int bytesPending = (int) (this.submittedBytes - (this.audioTrackUtil.getPlaybackHeadPosition() * ((long) this.frameSize)));
/* 499 */               int bytesToWrite2 = this.bufferSize - bytesPending;
/* 500 */               if (bytesToWrite2 > 0 && (bytesToWrite = Math.min(this.temporaryBufferSize, bytesToWrite2)) > 0) {
/* 503 */                   bytesWritten = this.audioTrack.write(this.temporaryBuffer, this.temporaryBufferOffset, bytesToWrite);
/* 504 */                   if (bytesWritten > 0) {
/* 505 */                       this.temporaryBufferOffset += bytesWritten;
                            } else {
/* 507 */                       if (this.temporaryBufferOffset >= 4) {
/* 508 */                           Log.i(TAG, "Failed to write " + bytesToWrite);
                                }
/* 510 */                       if (this.temporaryBufferSize < 100) {
/* 511 */                           this.temporaryBufferOffset = 0;
/* 512 */                           this.temporaryBufferSize = 0;
/* 513 */                           return 4;
                                }
                            }
                        }
                    } else {
/* 519 */               bytesWritten = writeNonBlockingV21(this.audioTrack, buffer, this.temporaryBufferSize);
                    }
/* 522 */           if (bytesWritten < 0) {
/* 523 */               throw new WriteException(bytesWritten);
                    }
/* 526 */           this.temporaryBufferSize -= bytesWritten;
/* 527 */           this.submittedBytes += (long) bytesWritten;
/* 528 */           if (this.temporaryBufferSize == 0) {
/* 529 */               result |= 2;
                    }
/* 531 */           if (bytesWritten == 0 && this.temporaryBufferSize != 0 && !hasPendingData()) {
/* 533 */               this.temporaryBufferOffset = 0;
/* 534 */               this.temporaryBufferSize = 0;
/* 535 */               return 4;
                    }
/* 538 */           return result;
                }

/* 541 */       public long getTemporaryBufferSize() {
/* 542 */           return this.temporaryBufferSize;
                }

                @TargetApi(21)
/* 547 */       private static int writeNonBlockingV21(android.media.AudioTrack audioTrack, ByteBuffer buffer, int size) {
/* 548 */           return audioTrack.write(buffer, size, 1);
                }

/* 554 */       public boolean hasPendingData() {
                    return isInitialized() && bytesToFrames(this.submittedBytes) > this.audioTrackUtil.getPlaybackHeadPosition();
                }

/* 564 */       public boolean hasEnoughDataToBeginPlayback() {
                    return this.submittedBytes > ((long) ((this.minBufferSize * 3) / 2));
                }

/* 571 */       public void setVolume(float volume) {
/* 572 */           this.volume = volume;
/* 573 */           if (isInitialized()) {
/* 574 */               if (Util.access$000() >= 21) {
/* 575 */                   setVolumeV21(this.audioTrack, volume);
                        } else {
/* 577 */                   setVolumeV3(this.audioTrack, volume);
                        }
                    }
                }

                @TargetApi(21)
/* 583 */       private static void setVolumeV21(android.media.AudioTrack audioTrack, float volume) {
/* 584 */           audioTrack.setVolume(volume);
                }

/* 588 */       private static void setVolumeV3(android.media.AudioTrack audioTrack, float volume) {
/* 589 */           audioTrack.setStereoVolume(volume, volume);
                }

/* 595 */       public void pause() {
/* 596 */           if (isInitialized()) {
/* 597 */               resetSyncParams();
/* 598 */               this.audioTrack.pause();
                    }
                }

/* 607 */       public void reset() {
/* 608 */           if (isInitialized()) {
/* 609 */               this.submittedBytes = 0L;
/* 610 */               this.temporaryBufferSize = 0;
/* 611 */               this.startMediaTimeUs = -1L;
/* 612 */               resetSyncParams();
/* 613 */               int playState = this.audioTrack.getPlayState();
/* 614 */               if (playState == 3) {
/* 615 */                   this.audioTrack.pause();
                        }
/* 618 */               android.media.AudioTrack toRelease = this.audioTrack;
/* 619 */               this.audioTrack = null;
/* 620 */               this.audioTrackUtil = null;
/* 621 */               this.releasingConditionVariable.close();
/* 622 */               new C03741(toRelease).start();
                    }
                }

                /* JADX INFO: renamed from: co.vine.android.embed.player.AudioTrack$1 */
/* 620 */       class C03741 extends Thread {
                    final /* synthetic */ android.media.AudioTrack val$toRelease;

/* 621 */           C03741(android.media.AudioTrack audioTrack) {
                        this.val$toRelease = audioTrack;
                    }

                    @Override // java.lang.Thread, java.lang.Runnable
/* 625 */           public void run() {
                        try {
/* 626 */                   this.val$toRelease.release();
/* 628 */                   AudioTrack.access$100(AudioTrack.this).open();
                        } catch (Throwable th) {
                            AudioTrack.access$100(AudioTrack.this).open();
                            throw th;
                        }
                    }
                }

/* 638 */       private boolean hasCurrentPositionUs() {
                    return isInitialized() && this.startMediaTimeUs != -1;
                }

/* 645 */       private void maybeSampleSyncParams() {
/* 646 */           long playbackPositionUs = this.audioTrackUtil.getPlaybackHeadPositionUs();
                    if (playbackPositionUs != 0) {
/* 651 */               long systemClockUs = System.nanoTime() / 1000;
                        if (systemClockUs - this.lastPlayheadSampleTimeUs >= 30000) {
/* 654 */                   this.playheadOffsets[this.nextPlayheadOffsetIndex] = playbackPositionUs - systemClockUs;
/* 655 */                   this.nextPlayheadOffsetIndex = (this.nextPlayheadOffsetIndex + 1) % 10;
/* 656 */                   if (this.playheadOffsetCount < 10) {
/* 657 */                       this.playheadOffsetCount++;
                            }
/* 659 */                   this.lastPlayheadSampleTimeUs = systemClockUs;
/* 660 */                   this.smoothedPlayheadOffsetUs = 0L;
                            for (int i = 0; i < this.playheadOffsetCount; i++) {
/* 662 */                       this.smoothedPlayheadOffsetUs += this.playheadOffsets[i] / ((long) this.playheadOffsetCount);
                            }
                        }
                        if (systemClockUs - this.lastTimestampSampleTimeUs >= 500000) {
/* 667 */                   this.audioTimestampSet = this.audioTrackUtil.updateTimestamp();
/* 668 */                   if (this.audioTimestampSet) {
/* 670 */                       long audioTimestampUs = this.audioTrackUtil.getTimestampNanoTime() / 1000;
/* 671 */                       long audioTimestampFramePosition = this.audioTrackUtil.getTimestampFramePosition();
                                if (audioTimestampUs < this.resumeSystemTimeUs) {
/* 674 */                           this.audioTimestampSet = false;
                                } else if (Math.abs(audioTimestampUs - systemClockUs) > 5000000) {
/* 677 */                           this.audioTimestampSet = false;
/* 678 */                           Log.w(TAG, "Spurious audio timestamp (system clock mismatch): " + audioTimestampFramePosition + ", " + audioTimestampUs + ", " + systemClockUs + ", " + playbackPositionUs);
                                } else if (Math.abs(framesToDurationUs(audioTimestampFramePosition) - playbackPositionUs) > 5000000) {
/* 684 */                           this.audioTimestampSet = false;
/* 685 */                           Log.w(TAG, "Spurious audio timestamp (frame position mismatch): " + audioTimestampFramePosition + ", " + audioTimestampUs + ", " + systemClockUs + ", " + playbackPositionUs);
                                }
                            }
/* 690 */                   if (this.getLatencyMethod != null) {
                                try {
/* 694 */                           this.latencyUs = (((long) ((Integer) this.getLatencyMethod.invoke(this.audioTrack, (Object[]) null)).intValue()) * 1000) - framesToDurationUs(bytesToFrames(this.bufferSize));
/* 697 */                           this.latencyUs = Math.max(this.latencyUs, 0L);
                                    if (this.latencyUs > 5000000) {
/* 700 */                               Log.w(TAG, "Ignoring impossibly large audio latency: " + this.latencyUs);
/* 701 */                               this.latencyUs = 0L;
                                    }
                                } catch (Exception e) {
/* 705 */                           this.getLatencyMethod = null;
                                }
                            }
/* 708 */                   this.lastTimestampSampleTimeUs = systemClockUs;
                        }
                    }
                }

/* 719 */       private void checkAudioTrackInitialized() throws InitializationException {
/* 720 */           int state = this.audioTrack.getState();
/* 721 */           if (state == 1) {
/* 722 */               return;
                    }
                    try {
/* 726 */               this.audioTrack.release();
/* 731 */               this.audioTrack = null;
                    } catch (Exception e) {
/* 731 */               this.audioTrack = null;
                    } catch (Throwable th) {
                        this.audioTrack = null;
                        throw th;
                    }
/* 734 */           throw new InitializationException(state, this.sampleRate, this.channelConfig, this.bufferSize);
                }

/* 737 */       private long bytesToFrames(long byteCount) {
/* 738 */           if (this.isAc3) {
/* 739 */               if (this.ac3Bitrate == 0) {
                            return 0L;
                        }
/* 739 */               return ((8 * byteCount) * ((long) this.sampleRate)) / ((long) (this.ac3Bitrate * 1000));
                    }
/* 742 */           return byteCount / ((long) this.frameSize);
                }

/* 746 */       private long framesToDurationUs(long frameCount) {
/* 747 */           return (AudioUtils.MICROS_PER_SECOND * frameCount) / ((long) this.sampleRate);
                }

/* 750 */       private long durationUsToFrames(long durationUs) {
/* 751 */           return (((long) this.sampleRate) * durationUs) / AudioUtils.MICROS_PER_SECOND;
                }

/* 754 */       private void resetSyncParams() {
/* 755 */           this.smoothedPlayheadOffsetUs = 0L;
/* 756 */           this.playheadOffsetCount = 0;
/* 757 */           this.nextPlayheadOffsetIndex = 0;
/* 758 */           this.lastPlayheadSampleTimeUs = 0L;
/* 759 */           this.audioTimestampSet = false;
/* 760 */           this.lastTimestampSampleTimeUs = 0L;
                }

/* 772 */       private static class AudioTrackUtil {
                    protected final android.media.AudioTrack audioTrack;
                    private long lastRawPlaybackHeadPosition;
                    private long rawPlaybackHeadWrapCount;
                    private final int sampleRate;

/* 773 */           public AudioTrackUtil(android.media.AudioTrack audioTrack) {
/* 775 */               this.audioTrack = audioTrack;
/* 776 */               this.sampleRate = audioTrack.getSampleRate();
                    }

/* 788 */           public long getPlaybackHeadPosition() {
/* 789 */               long rawPlaybackHeadPosition = 4294967295L & ((long) this.audioTrack.getPlaybackHeadPosition());
                        if (this.lastRawPlaybackHeadPosition > rawPlaybackHeadPosition) {
/* 792 */                   this.rawPlaybackHeadWrapCount++;
                        }
/* 794 */               this.lastRawPlaybackHeadPosition = rawPlaybackHeadPosition;
/* 795 */               return (this.rawPlaybackHeadWrapCount << 32) + rawPlaybackHeadPosition;
                    }

/* 801 */           public long getPlaybackHeadPositionUs() {
/* 802 */               return (getPlaybackHeadPosition() * AudioUtils.MICROS_PER_SECOND) / ((long) this.sampleRate);
                    }

/* 811 */           public boolean updateTimestamp() {
/* 812 */               return false;
                    }

/* 825 */           public long getTimestampNanoTime() {
/* 826 */               throw new UnsupportedOperationException();
                    }

/* 841 */           public long getTimestampFramePosition() {
/* 842 */               throw new UnsupportedOperationException();
                    }
                }

                @TargetApi(19)
/* 855 */       private static class AudioTrackUtilV19 extends AudioTrackUtil {
                    private final AudioTimestamp audioTimestamp;
                    private long lastRawTimestampFramePosition;
                    private long lastTimestampFramePosition;
                    private long rawTimestampFramePositionWrapCount;

/* 856 */           public AudioTrackUtilV19(android.media.AudioTrack audioTrack) {
/* 857 */               super(audioTrack);
/* 858 */               this.audioTimestamp = new AudioTimestamp();
                    }

                    @Override // co.vine.android.embed.player.AudioTrack.AudioTrackUtil
/* 862 */           public boolean updateTimestamp() {
/* 863 */               boolean updated = this.audioTrack.getTimestamp(this.audioTimestamp);
/* 864 */               if (updated) {
/* 865 */                   long rawFramePosition = this.audioTimestamp.framePosition;
                            if (this.lastRawTimestampFramePosition > rawFramePosition) {
/* 868 */                       this.rawTimestampFramePositionWrapCount++;
                            }
/* 870 */                   this.lastRawTimestampFramePosition = rawFramePosition;
/* 871 */                   this.lastTimestampFramePosition = (this.rawTimestampFramePositionWrapCount << 32) + rawFramePosition;
                        }
/* 873 */               return updated;
                    }

                    @Override // co.vine.android.embed.player.AudioTrack.AudioTrackUtil
/* 877 */           public long getTimestampNanoTime() {
/* 878 */               return this.audioTimestamp.nanoTime;
                    }

                    @Override // co.vine.android.embed.player.AudioTrack.AudioTrackUtil
/* 882 */           public long getTimestampFramePosition() {
/* 883 */               return this.lastTimestampFramePosition;
                    }
                }
            }
