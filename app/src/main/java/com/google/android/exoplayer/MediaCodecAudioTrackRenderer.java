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
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            @TargetApi(16)
/* 40 */    public class MediaCodecAudioTrackRenderer extends MediaCodecTrackRenderer {
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

/* 41 */        static /* synthetic */ ConditionVariable access$100(MediaCodecAudioTrackRenderer x0) {
/* 42 */            return x0.audioTrackReleasingConditionVariable;
                }

/* 41 */        static /* synthetic */ EventListener access$200(MediaCodecAudioTrackRenderer x0) {
/* 42 */            return x0.eventListener;
                }

/* 69 */        public static class AudioTrackInitializationException extends Exception {
                    public final int audioTrackState;

/* 70 */            public AudioTrackInitializationException(int audioTrackState, int sampleRate, int channelConfig, int bufferSize) {
/* 71 */                super("AudioTrack init failed: " + audioTrackState + ", Config(" + sampleRate + ", " + channelConfig + ", " + bufferSize + ")");
/* 73 */                this.audioTrackState = audioTrackState;
                    }
                }

/* 148 */       public MediaCodecAudioTrackRenderer(SampleSource source) {
/* 149 */           this(source, (DrmSessionManager) null, true);
                }

/* 163 */       public MediaCodecAudioTrackRenderer(SampleSource source, DrmSessionManager drmSessionManager, boolean playClearSamplesWithoutKeys) {
/* 164 */           this(source, drmSessionManager, playClearSamplesWithoutKeys, null, null);
                }

/* 174 */       public MediaCodecAudioTrackRenderer(SampleSource source, Handler eventHandler, EventListener eventListener) {
/* 175 */           this(source, null, true, eventHandler, eventListener);
                }

/* 192 */       public MediaCodecAudioTrackRenderer(SampleSource source, DrmSessionManager drmSessionManager, boolean playClearSamplesWithoutKeys, Handler eventHandler, EventListener eventListener) {
/* 193 */           this(source, drmSessionManager, playClearSamplesWithoutKeys, 4.0f, eventHandler, eventListener);
                }

/* 208 */       public MediaCodecAudioTrackRenderer(SampleSource source, float minBufferMultiplicationFactor, Handler eventHandler, EventListener eventListener) {
/* 209 */           this(source, null, true, minBufferMultiplicationFactor, eventHandler, eventListener);
                }

/* 231 */       public MediaCodecAudioTrackRenderer(SampleSource source, DrmSessionManager drmSessionManager, boolean playClearSamplesWithoutKeys, float minBufferMultiplicationFactor, Handler eventHandler, EventListener eventListener) {
/* 232 */           super(source, drmSessionManager, playClearSamplesWithoutKeys, eventHandler, eventListener);
/* 233 */           Assertions.checkState(minBufferMultiplicationFactor >= 1.0f);
/* 234 */           this.minBufferMultiplicationFactor = minBufferMultiplicationFactor;
/* 235 */           this.eventListener = eventListener;
/* 236 */           this.audioTrackReleasingConditionVariable = new ConditionVariable(true);
/* 237 */           if (Util.SDK_INT >= 19) {
/* 238 */               this.audioTimestampCompat = new AudioTimestampCompatV19();
                    } else {
/* 240 */               this.audioTimestampCompat = new NoopAudioTimestampCompat(null);
                    }
/* 242 */           if (Util.SDK_INT >= 18) {
                        try {
/* 244 */                   this.audioTrackGetLatencyMethod = AudioTrack.class.getMethod("getLatency", (Class[]) null);
                        } catch (NoSuchMethodException e) {
                        }
                    }
/* 249 */           this.playheadOffsets = new long[10];
/* 250 */           this.volume = 1.0f;
                }

                @Override // com.google.android.exoplayer.TrackRenderer
/* 254 */       protected boolean isTimeSource() {
/* 255 */           return true;
                }

                @Override // com.google.android.exoplayer.MediaCodecTrackRenderer
/* 259 */       protected boolean handlesMimeType(String mimeType) {
                    return MimeTypes.isAudio(mimeType) && super.handlesMimeType(mimeType);
                }

                @Override // com.google.android.exoplayer.MediaCodecTrackRenderer, com.google.android.exoplayer.TrackRenderer
/* 264 */       protected void onEnabled(long timeUs, boolean joining) {
/* 265 */           super.onEnabled(timeUs, joining);
/* 266 */           this.lastReportedCurrentPositionUs = 0L;
                }

                @Override // com.google.android.exoplayer.MediaCodecTrackRenderer, com.google.android.exoplayer.TrackRenderer
/* 270 */       protected void doSomeWork(long timeUs) throws ExoPlaybackException {
/* 271 */           super.doSomeWork(timeUs);
/* 272 */           maybeSampleSyncParams();
                }

                @Override // com.google.android.exoplayer.MediaCodecTrackRenderer
/* 276 */       protected void onOutputFormatChanged(android.media.MediaFormat format) {
                    int channelConfig;
/* 277 */           int channelCount = format.getInteger("channel-count");
                    switch (channelCount) {
                        case 1:
/* 281 */                   channelConfig = 4;
                            break;
                        case 2:
/* 284 */                   channelConfig = 12;
                            break;
                        case 3:
                        case 4:
                        case 5:
                        default:
/* 290 */                   throw new IllegalArgumentException("Unsupported channel count: " + channelCount);
                        case 6:
/* 287 */                   channelConfig = 252;
                            break;
                    }
/* 293 */           int sampleRate = format.getInteger("sample-rate");
/* 294 */           if (this.audioTrack == null || this.sampleRate != sampleRate || this.channelConfig != channelConfig) {
/* 300 */               releaseAudioTrack();
/* 301 */               this.sampleRate = sampleRate;
/* 302 */               this.channelConfig = channelConfig;
/* 303 */               this.minBufferSize = AudioTrack.getMinBufferSize(sampleRate, channelConfig, 2);
/* 305 */               this.bufferSize = (int) (this.minBufferMultiplicationFactor * this.minBufferSize);
/* 306 */               this.frameSize = channelCount * 2;
                    }
                }

/* 314 */       private void initAudioTrack() throws ExoPlaybackException {
/* 315 */           this.audioTrackReleasingConditionVariable.block();
/* 316 */           if (this.audioSessionId == 0) {
/* 317 */               this.audioTrack = new AudioTrack(3, this.sampleRate, this.channelConfig, 2, this.bufferSize, 1);
/* 319 */               checkAudioTrackInitialized();
/* 320 */               this.audioSessionId = this.audioTrack.getAudioSessionId();
/* 321 */               onAudioSessionId(this.audioSessionId);
                    } else {
/* 324 */               this.audioTrack = new AudioTrack(3, this.sampleRate, this.channelConfig, 2, this.bufferSize, 1, this.audioSessionId);
/* 326 */               checkAudioTrackInitialized();
                    }
/* 328 */           this.audioTrack.setStereoVolume(this.volume, this.volume);
/* 329 */           if (getState() == 3) {
/* 330 */               this.audioTrackResumeSystemTimeUs = System.nanoTime() / 1000;
/* 331 */               this.audioTrack.play();
                    }
                }

/* 342 */       private void checkAudioTrackInitialized() throws ExoPlaybackException {
/* 343 */           int audioTrackState = this.audioTrack.getState();
/* 344 */           if (audioTrackState == 1) {
/* 345 */               return;
                    }
                    try {
/* 349 */               this.audioTrack.release();
/* 354 */               this.audioTrack = null;
                    } catch (Exception e) {
/* 354 */               this.audioTrack = null;
                    } catch (Throwable th) {
                        this.audioTrack = null;
                        throw th;
                    }
/* 357 */           AudioTrackInitializationException exception = new AudioTrackInitializationException(audioTrackState, this.sampleRate, this.channelConfig, this.bufferSize);
/* 359 */           notifyAudioTrackInitializationError(exception);
/* 360 */           throw new ExoPlaybackException(exception);
                }

/* 376 */       protected void onAudioSessionId(int audioSessionId) {
                }

/* 379 */       private void releaseAudioTrack() {
/* 380 */           if (this.audioTrack != null) {
/* 381 */               this.submittedBytes = 0L;
/* 382 */               this.temporaryBufferSize = 0;
/* 383 */               this.lastRawPlaybackHeadPosition = 0L;
/* 384 */               this.rawPlaybackHeadWrapCount = 0L;
/* 385 */               this.audioTrackStartMediaTimeUs = 0L;
/* 386 */               this.audioTrackStartMediaTimeState = 0;
/* 387 */               resetSyncParams();
/* 388 */               int playState = this.audioTrack.getPlayState();
/* 389 */               if (playState == 3) {
/* 390 */                   this.audioTrack.pause();
                        }
/* 393 */               AudioTrack toRelease = this.audioTrack;
/* 394 */               this.audioTrack = null;
/* 395 */               this.audioTrackReleasingConditionVariable.close();
/* 396 */               new C08541(toRelease).start();
                    }
                }

                /* JADX INFO: renamed from: com.google.android.exoplayer.MediaCodecAudioTrackRenderer$1 */
/* 394 */       class C08541 extends Thread {
                    final /* synthetic */ AudioTrack val$toRelease;

/* 395 */           C08541(AudioTrack audioTrack) {
                        this.val$toRelease = audioTrack;
                    }

                    @Override // java.lang.Thread, java.lang.Runnable
/* 399 */           public void run() {
                        try {
/* 400 */                   this.val$toRelease.release();
/* 402 */                   MediaCodecAudioTrackRenderer.access$100(MediaCodecAudioTrackRenderer.this).open();
                        } catch (Throwable th) {
                            MediaCodecAudioTrackRenderer.access$100(MediaCodecAudioTrackRenderer.this).open();
                            throw th;
                        }
                    }
                }

                @Override // com.google.android.exoplayer.MediaCodecTrackRenderer, com.google.android.exoplayer.TrackRenderer
/* 410 */       protected void onStarted() {
/* 411 */           super.onStarted();
/* 412 */           if (this.audioTrack != null) {
/* 413 */               this.audioTrackResumeSystemTimeUs = System.nanoTime() / 1000;
/* 414 */               this.audioTrack.play();
                    }
                }

                @Override // com.google.android.exoplayer.MediaCodecTrackRenderer, com.google.android.exoplayer.TrackRenderer
/* 419 */       protected void onStopped() {
/* 420 */           super.onStopped();
/* 421 */           if (this.audioTrack != null) {
/* 422 */               resetSyncParams();
/* 423 */               this.audioTrack.pause();
                    }
                }

                @Override // com.google.android.exoplayer.MediaCodecTrackRenderer, com.google.android.exoplayer.TrackRenderer
/* 428 */       protected boolean isReady() {
                    return getPendingFrameCount() > 0 || (super.isReady() && getSourceState() == 2);
                }

                @Override // com.google.android.exoplayer.MediaCodecTrackRenderer, com.google.android.exoplayer.TrackRenderer
/* 443 */       protected long getCurrentPositionUs() {
                    long currentPositionUs;
/* 444 */           long systemClockUs = System.nanoTime() / 1000;
/* 446 */           if (this.audioTrack == null || this.audioTrackStartMediaTimeState == 0) {
/* 448 */               currentPositionUs = super.getCurrentPositionUs();
                    } else if (this.audioTimestampSet) {
/* 451 */               long presentationDiff = systemClockUs - (this.audioTimestampCompat.getNanoTime() / 1000);
/* 452 */               long framesDiff = durationUsToFrames(presentationDiff);
/* 454 */               long currentFramePosition = this.audioTimestampCompat.getFramePosition() + framesDiff;
/* 455 */               currentPositionUs = framesToDurationUs(currentFramePosition) + this.audioTrackStartMediaTimeUs;
                    } else {
/* 457 */               if (this.playheadOffsetCount == 0) {
/* 459 */                   currentPositionUs = getPlayheadPositionUs() + this.audioTrackStartMediaTimeUs;
                        } else {
/* 464 */                   currentPositionUs = this.smoothedPlayheadOffsetUs + systemClockUs + this.audioTrackStartMediaTimeUs;
                        }
/* 466 */               if (!isEnded()) {
/* 467 */                   currentPositionUs -= this.audioTrackLatencyUs;
                        }
                    }
/* 472 */           long currentPositionUs2 = Math.max(this.lastReportedCurrentPositionUs, currentPositionUs);
/* 473 */           this.lastReportedCurrentPositionUs = currentPositionUs2;
/* 474 */           return currentPositionUs2;
                }

/* 477 */       private void maybeSampleSyncParams() {
/* 478 */           if (this.audioTrack != null && this.audioTrackStartMediaTimeState != 0 && getState() == 3) {
/* 483 */               long playheadPositionUs = getPlayheadPositionUs();
/* 484 */               long systemClockUs = System.nanoTime() / 1000;
                        if (systemClockUs - this.lastPlayheadSampleTimeUs >= 30000) {
/* 487 */                   this.playheadOffsets[this.nextPlayheadOffsetIndex] = playheadPositionUs - systemClockUs;
/* 488 */                   this.nextPlayheadOffsetIndex = (this.nextPlayheadOffsetIndex + 1) % 10;
/* 489 */                   if (this.playheadOffsetCount < 10) {
/* 490 */                       this.playheadOffsetCount++;
                            }
/* 492 */                   this.lastPlayheadSampleTimeUs = systemClockUs;
/* 493 */                   this.smoothedPlayheadOffsetUs = 0L;
                            for (int i = 0; i < this.playheadOffsetCount; i++) {
/* 495 */                       this.smoothedPlayheadOffsetUs += this.playheadOffsets[i] / ((long) this.playheadOffsetCount);
                            }
                        }
                        if (systemClockUs - this.lastTimestampSampleTimeUs >= 500000) {
/* 500 */                   this.audioTimestampSet = this.audioTimestampCompat.initTimestamp(this.audioTrack);
/* 501 */                   if (this.audioTimestampSet) {
/* 503 */                       long audioTimestampUs = this.audioTimestampCompat.getNanoTime() / 1000;
                                if (audioTimestampUs < this.audioTrackResumeSystemTimeUs) {
/* 506 */                           this.audioTimestampSet = false;
                                } else if (Math.abs(audioTimestampUs - systemClockUs) > MAX_AUDIO_TIMSTAMP_OFFSET_US) {
/* 509 */                           this.audioTimestampSet = false;
/* 510 */                           Log.w(TAG, "Spurious audio timestamp: " + this.audioTimestampCompat.getFramePosition() + ", " + audioTimestampUs + ", " + systemClockUs);
                                }
                            }
/* 514 */                   if (this.audioTrackGetLatencyMethod != null) {
                                try {
/* 518 */                           this.audioTrackLatencyUs = (((long) ((Integer) this.audioTrackGetLatencyMethod.invoke(this.audioTrack, (Object[]) null)).intValue()) * 1000) - framesToDurationUs(this.bufferSize / this.frameSize);
/* 522 */                           this.audioTrackLatencyUs = Math.max(this.audioTrackLatencyUs, 0L);
                                } catch (Exception e) {
/* 525 */                           this.audioTrackGetLatencyMethod = null;
                                }
                            }
/* 528 */                   this.lastTimestampSampleTimeUs = systemClockUs;
                        }
                    }
                }

/* 532 */       private void resetSyncParams() {
/* 533 */           this.smoothedPlayheadOffsetUs = 0L;
/* 534 */           this.playheadOffsetCount = 0;
/* 535 */           this.nextPlayheadOffsetIndex = 0;
/* 536 */           this.lastPlayheadSampleTimeUs = 0L;
/* 537 */           this.audioTimestampSet = false;
/* 538 */           this.lastTimestampSampleTimeUs = 0L;
                }

/* 541 */       private long getPlayheadPositionUs() {
/* 542 */           return framesToDurationUs(getPlaybackHeadPosition());
                }

/* 545 */       private long framesToDurationUs(long frameCount) {
/* 546 */           return (1000000 * frameCount) / ((long) this.sampleRate);
                }

/* 549 */       private long durationUsToFrames(long durationUs) {
/* 550 */           return (((long) this.sampleRate) * durationUs) / 1000000;
                }

                @Override // com.google.android.exoplayer.MediaCodecTrackRenderer, com.google.android.exoplayer.TrackRenderer
/* 554 */       protected void onDisabled() {
/* 555 */           super.onDisabled();
/* 556 */           releaseAudioTrack();
/* 557 */           this.audioSessionId = 0;
                }

                @Override // com.google.android.exoplayer.MediaCodecTrackRenderer, com.google.android.exoplayer.TrackRenderer
/* 561 */       protected void seekTo(long timeUs) throws ExoPlaybackException {
/* 562 */           super.seekTo(timeUs);
/* 564 */           releaseAudioTrack();
/* 565 */           this.lastReportedCurrentPositionUs = 0L;
                }

                @Override // com.google.android.exoplayer.MediaCodecTrackRenderer
/* 571 */       protected boolean processOutputBuffer(long timeUs, MediaCodec codec, ByteBuffer buffer, MediaCodec.BufferInfo bufferInfo, int bufferIndex, boolean shouldSkip) throws ExoPlaybackException {
/* 572 */           if (shouldSkip) {
/* 573 */               codec.releaseOutputBuffer(bufferIndex, false);
                        this.codecCounters.skippedOutputBufferCount++;
/* 575 */               if (this.audioTrackStartMediaTimeState == 1) {
/* 577 */                   this.audioTrackStartMediaTimeState = 2;
                        }
/* 579 */               return true;
                    }
/* 582 */           if (this.temporaryBufferSize == 0) {
/* 585 */               long bufferStartTime = bufferInfo.presentationTimeUs - framesToDurationUs(bufferInfo.size / this.frameSize);
/* 587 */               if (this.audioTrackStartMediaTimeState == 0) {
/* 588 */                   this.audioTrackStartMediaTimeUs = Math.max(0L, bufferStartTime);
/* 589 */                   this.audioTrackStartMediaTimeState = 1;
                        } else {
/* 592 */                   long expectedBufferStartTime = this.audioTrackStartMediaTimeUs + framesToDurationUs(this.submittedBytes / ((long) this.frameSize));
/* 594 */                   if (this.audioTrackStartMediaTimeState == 1 && Math.abs(expectedBufferStartTime - bufferStartTime) > 200000) {
/* 596 */                       Log.e(TAG, "Discontinuity detected [expected " + expectedBufferStartTime + ", got " + bufferStartTime + "]");
/* 598 */                       this.audioTrackStartMediaTimeState = 2;
                            }
/* 600 */                   if (this.audioTrackStartMediaTimeState == 2) {
/* 604 */                       this.audioTrackStartMediaTimeUs += bufferStartTime - expectedBufferStartTime;
/* 605 */                       this.lastReportedCurrentPositionUs = 0L;
                            }
                        }
/* 611 */               if (this.temporaryBuffer == null || this.temporaryBuffer.length < bufferInfo.size) {
/* 612 */                   this.temporaryBuffer = new byte[bufferInfo.size];
                        }
/* 614 */               buffer.position(bufferInfo.offset);
/* 615 */               buffer.get(this.temporaryBuffer, 0, bufferInfo.size);
/* 616 */               this.temporaryBufferOffset = 0;
/* 617 */               this.temporaryBufferSize = bufferInfo.size;
                    }
/* 620 */           if (this.audioTrack == null) {
/* 621 */               initAudioTrack();
                    }
/* 626 */           int bytesPending = (int) (this.submittedBytes - (getPlaybackHeadPosition() * ((long) this.frameSize)));
/* 627 */           int bytesToWrite = this.bufferSize - bytesPending;
/* 629 */           if (bytesToWrite > 0) {
/* 630 */               int bytesToWrite2 = Math.min(this.temporaryBufferSize, bytesToWrite);
/* 631 */               this.audioTrack.write(this.temporaryBuffer, this.temporaryBufferOffset, bytesToWrite2);
/* 632 */               this.temporaryBufferOffset += bytesToWrite2;
/* 633 */               this.temporaryBufferSize -= bytesToWrite2;
/* 634 */               this.submittedBytes += (long) bytesToWrite2;
/* 635 */               if (this.temporaryBufferSize == 0) {
/* 636 */                   codec.releaseOutputBuffer(bufferIndex, false);
                            this.codecCounters.renderedOutputBufferCount++;
/* 638 */                   return true;
                        }
                    }
/* 642 */           return false;
                }

/* 654 */       private long getPlaybackHeadPosition() {
/* 655 */           long rawPlaybackHeadPosition = 4294967295L & ((long) this.audioTrack.getPlaybackHeadPosition());
                    if (this.lastRawPlaybackHeadPosition > rawPlaybackHeadPosition) {
/* 658 */               this.rawPlaybackHeadWrapCount++;
                    }
/* 660 */           this.lastRawPlaybackHeadPosition = rawPlaybackHeadPosition;
/* 661 */           return (this.rawPlaybackHeadWrapCount << 32) + rawPlaybackHeadPosition;
                }

/* 664 */       private int getPendingFrameCount() {
/* 665 */           if (this.audioTrack == null) {
                        return 0;
                    }
                    return (int) ((this.submittedBytes / ((long) this.frameSize)) - getPlaybackHeadPosition());
                }

                @Override // com.google.android.exoplayer.TrackRenderer, com.google.android.exoplayer.ExoPlayer.ExoPlayerComponent
/* 670 */       public void handleMessage(int messageType, Object message) throws ExoPlaybackException {
/* 671 */           if (messageType == 1) {
/* 672 */               setVolume(((Float) message).floatValue());
                    } else {
/* 674 */               super.handleMessage(messageType, message);
                    }
                }

/* 678 */       private void setVolume(float volume) {
/* 679 */           this.volume = volume;
/* 680 */           if (this.audioTrack != null) {
/* 681 */               this.audioTrack.setStereoVolume(volume, volume);
                    }
                }

                /* JADX INFO: renamed from: com.google.android.exoplayer.MediaCodecAudioTrackRenderer$2 */
/* 685 */       class RunnableC08552 implements Runnable {
                    final /* synthetic */ AudioTrackInitializationException val$e;

/* 686 */           RunnableC08552(AudioTrackInitializationException audioTrackInitializationException) {
                        this.val$e = audioTrackInitializationException;
                    }

                    @Override // java.lang.Runnable
/* 689 */           public void run() {
/* 690 */               MediaCodecAudioTrackRenderer.access$200(MediaCodecAudioTrackRenderer.this).onAudioTrackInitializationError(this.val$e);
                    }
                }

/* 685 */       private void notifyAudioTrackInitializationError(AudioTrackInitializationException e) {
/* 686 */           if (this.eventHandler != null && this.eventListener != null) {
/* 687 */               this.eventHandler.post(new RunnableC08552(e));
                    }
                }

/* 713 */       private static final class NoopAudioTimestampCompat implements AudioTimestampCompat {
/* 714 */           private NoopAudioTimestampCompat() {
                    }

/* 714 */           /* synthetic */ NoopAudioTimestampCompat(C08541 x0) {
/* 715 */               this();
                    }

                    @Override // com.google.android.exoplayer.MediaCodecAudioTrackRenderer.AudioTimestampCompat
/* 718 */           public boolean initTimestamp(AudioTrack audioTrack) {
/* 719 */               return false;
                    }

                    @Override // com.google.android.exoplayer.MediaCodecAudioTrackRenderer.AudioTimestampCompat
/* 724 */           public long getNanoTime() {
/* 725 */               throw new UnsupportedOperationException();
                    }

                    @Override // com.google.android.exoplayer.MediaCodecAudioTrackRenderer.AudioTimestampCompat
/* 730 */           public long getFramePosition() {
/* 731 */               throw new UnsupportedOperationException();
                    }
                }

                @TargetApi(19)
/* 749 */       private static final class AudioTimestampCompatV19 implements AudioTimestampCompat {
                    private final AudioTimestamp audioTimestamp = new AudioTimestamp();

                    @Override // com.google.android.exoplayer.MediaCodecAudioTrackRenderer.AudioTimestampCompat
/* 750 */           public boolean initTimestamp(AudioTrack audioTrack) {
/* 751 */               return audioTrack.getTimestamp(this.audioTimestamp);
                    }

                    @Override // com.google.android.exoplayer.MediaCodecAudioTrackRenderer.AudioTimestampCompat
/* 755 */           public long getNanoTime() {
/* 756 */               return this.audioTimestamp.nanoTime;
                    }

                    @Override // com.google.android.exoplayer.MediaCodecAudioTrackRenderer.AudioTimestampCompat
/* 760 */           public long getFramePosition() {
/* 761 */               return this.audioTimestamp.framePosition;
                    }
                }
            }
