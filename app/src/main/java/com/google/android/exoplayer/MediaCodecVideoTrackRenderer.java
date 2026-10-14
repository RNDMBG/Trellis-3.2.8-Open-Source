            package com.google.android.exoplayer;

            import android.annotation.TargetApi;
            import android.media.MediaCodec;
            import android.media.MediaCrypto;
            import android.os.Handler;
            import android.os.SystemClock;
            import android.view.Surface;
            import com.google.android.exoplayer.drm.DrmSessionManager;
            import com.google.android.exoplayer.util.MimeTypes;
            import com.google.android.exoplayer.util.TraceUtil;
            import java.nio.ByteBuffer;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            @TargetApi(16)
/* 33 */    public class MediaCodecVideoTrackRenderer extends MediaCodecTrackRenderer {
                private static final String KEY_CROP_BOTTOM = "crop-bottom";
                private static final String KEY_CROP_LEFT = "crop-left";
                private static final String KEY_CROP_RIGHT = "crop-right";
                private static final String KEY_CROP_TOP = "crop-top";
                public static final int MSG_SET_SURFACE = 1;
                private final long allowedJoiningTimeUs;
                private int currentHeight;
                private int currentWidth;
                private boolean drawnToSurface;
                private long droppedFrameAccumulationStartTimeMs;
                private int droppedFrameCount;
                private final EventListener eventListener;
                private long joiningDeadlineUs;
                private int lastReportedHeight;
                private int lastReportedWidth;
                private final int maxDroppedFrameCountToNotify;
                private boolean renderedFirstFrame;
                private Surface surface;
                private final int videoScalingMode;

                public interface EventListener extends MediaCodecTrackRenderer.EventListener {
                    void onDrawnToSurface(Surface surface);

                    void onDroppedFrames(int i, long j);

                    void onVideoSizeChanged(int i, int i2);
                }

/* 34 */        static /* synthetic */ EventListener access$000(MediaCodecVideoTrackRenderer x0) {
/* 35 */            return x0.eventListener;
                }

/* 109 */       public MediaCodecVideoTrackRenderer(SampleSource source, int videoScalingMode) {
/* 110 */           this(source, null, true, videoScalingMode);
                }

/* 126 */       public MediaCodecVideoTrackRenderer(SampleSource source, DrmSessionManager drmSessionManager, boolean playClearSamplesWithoutKeys, int videoScalingMode) {
/* 127 */           this(source, drmSessionManager, playClearSamplesWithoutKeys, videoScalingMode, 0L);
                }

/* 138 */       public MediaCodecVideoTrackRenderer(SampleSource source, int videoScalingMode, long allowedJoiningTimeMs) {
/* 139 */           this(source, null, true, videoScalingMode, allowedJoiningTimeMs);
                }

/* 157 */       public MediaCodecVideoTrackRenderer(SampleSource source, DrmSessionManager drmSessionManager, boolean playClearSamplesWithoutKeys, int videoScalingMode, long allowedJoiningTimeMs) {
/* 158 */           this(source, drmSessionManager, playClearSamplesWithoutKeys, videoScalingMode, allowedJoiningTimeMs, null, null, -1);
                }

/* 176 */       public MediaCodecVideoTrackRenderer(SampleSource source, int videoScalingMode, long allowedJoiningTimeMs, Handler eventHandler, EventListener eventListener, int maxDroppedFrameCountToNotify) {
/* 177 */           this(source, null, true, videoScalingMode, allowedJoiningTimeMs, eventHandler, eventListener, maxDroppedFrameCountToNotify);
                }

/* 202 */       public MediaCodecVideoTrackRenderer(SampleSource source, DrmSessionManager drmSessionManager, boolean playClearSamplesWithoutKeys, int videoScalingMode, long allowedJoiningTimeMs, Handler eventHandler, EventListener eventListener, int maxDroppedFrameCountToNotify) {
/* 203 */           super(source, drmSessionManager, playClearSamplesWithoutKeys, eventHandler, eventListener);
/* 204 */           this.videoScalingMode = videoScalingMode;
/* 205 */           this.allowedJoiningTimeUs = 1000 * allowedJoiningTimeMs;
/* 206 */           this.eventListener = eventListener;
/* 207 */           this.maxDroppedFrameCountToNotify = maxDroppedFrameCountToNotify;
/* 208 */           this.joiningDeadlineUs = -1L;
/* 209 */           this.currentWidth = -1;
/* 210 */           this.currentHeight = -1;
/* 211 */           this.lastReportedWidth = -1;
/* 212 */           this.lastReportedHeight = -1;
                }

                @Override // com.google.android.exoplayer.MediaCodecTrackRenderer
/* 216 */       protected boolean handlesMimeType(String mimeType) {
                    return MimeTypes.isVideo(mimeType) && super.handlesMimeType(mimeType);
                }

                @Override // com.google.android.exoplayer.MediaCodecTrackRenderer, com.google.android.exoplayer.TrackRenderer
/* 221 */       protected void onEnabled(long startTimeUs, boolean joining) {
/* 222 */           super.onEnabled(startTimeUs, joining);
/* 223 */           this.renderedFirstFrame = false;
/* 224 */           if (joining && this.allowedJoiningTimeUs > 0) {
/* 225 */               this.joiningDeadlineUs = (SystemClock.elapsedRealtime() * 1000) + this.allowedJoiningTimeUs;
                    }
                }

                @Override // com.google.android.exoplayer.MediaCodecTrackRenderer, com.google.android.exoplayer.TrackRenderer
/* 230 */       protected void seekTo(long timeUs) throws ExoPlaybackException {
/* 231 */           super.seekTo(timeUs);
/* 232 */           this.renderedFirstFrame = false;
/* 233 */           this.joiningDeadlineUs = -1L;
                }

                @Override // com.google.android.exoplayer.MediaCodecTrackRenderer, com.google.android.exoplayer.TrackRenderer
/* 237 */       protected boolean isReady() {
/* 238 */           if (super.isReady() && (this.renderedFirstFrame || !codecInitialized() || getSourceState() == 2)) {
/* 241 */               this.joiningDeadlineUs = -1L;
                        return true;
                    }
                    if (this.joiningDeadlineUs == -1) {
                        return false;
                    }
                    if (SystemClock.elapsedRealtime() * 1000 < this.joiningDeadlineUs) {
                        return true;
                    }
/* 251 */           this.joiningDeadlineUs = -1L;
                    return false;
                }

                @Override // com.google.android.exoplayer.MediaCodecTrackRenderer, com.google.android.exoplayer.TrackRenderer
/* 257 */       protected void onStarted() {
/* 258 */           super.onStarted();
/* 259 */           this.droppedFrameCount = 0;
/* 260 */           this.droppedFrameAccumulationStartTimeMs = SystemClock.elapsedRealtime();
                }

                @Override // com.google.android.exoplayer.MediaCodecTrackRenderer, com.google.android.exoplayer.TrackRenderer
/* 264 */       protected void onStopped() {
/* 265 */           super.onStopped();
/* 266 */           this.joiningDeadlineUs = -1L;
/* 267 */           notifyAndResetDroppedFrameCount();
                }

                @Override // com.google.android.exoplayer.MediaCodecTrackRenderer, com.google.android.exoplayer.TrackRenderer
/* 271 */       public void onDisabled() {
/* 272 */           super.onDisabled();
/* 273 */           this.currentWidth = -1;
/* 274 */           this.currentHeight = -1;
/* 275 */           this.lastReportedWidth = -1;
/* 276 */           this.lastReportedHeight = -1;
                }

                @Override // com.google.android.exoplayer.TrackRenderer, com.google.android.exoplayer.ExoPlayer.ExoPlayerComponent
/* 280 */       public void handleMessage(int messageType, Object message) throws ExoPlaybackException {
/* 281 */           if (messageType == 1) {
/* 282 */               setSurface((Surface) message);
                    } else {
/* 284 */               super.handleMessage(messageType, message);
                    }
                }

/* 292 */       private void setSurface(Surface surface) throws ExoPlaybackException {
/* 293 */           if (this.surface != surface) {
/* 296 */               this.surface = surface;
/* 297 */               this.drawnToSurface = false;
/* 298 */               int state = getState();
/* 299 */               if (state == 2 || state == 3) {
/* 300 */                   releaseCodec();
/* 301 */                   maybeInitCodec();
                        }
                    }
                }

                @Override // com.google.android.exoplayer.MediaCodecTrackRenderer
/* 306 */       protected boolean shouldInitCodec() {
                    return super.shouldInitCodec() && this.surface != null;
                }

                @Override // com.google.android.exoplayer.MediaCodecTrackRenderer
/* 313 */       protected void configureCodec(MediaCodec codec, android.media.MediaFormat format, MediaCrypto crypto) {
/* 314 */           if (format.containsKey("rotation-degrees")) {
/* 317 */               format.setInteger("rotation-degrees", 0);
                    }
/* 319 */           codec.configure(format, this.surface, crypto, 0);
/* 320 */           codec.setVideoScalingMode(this.videoScalingMode);
                }

                @Override // com.google.android.exoplayer.MediaCodecTrackRenderer
/* 324 */       protected void onOutputFormatChanged(android.media.MediaFormat format) {
/* 325 */           boolean hasCrop = format.containsKey(KEY_CROP_RIGHT) && format.containsKey(KEY_CROP_LEFT) && format.containsKey(KEY_CROP_BOTTOM) && format.containsKey(KEY_CROP_TOP);
/* 327 */           this.currentWidth = hasCrop ? (format.getInteger(KEY_CROP_RIGHT) - format.getInteger(KEY_CROP_LEFT)) + 1 : format.getInteger("width");
/* 330 */           this.currentHeight = hasCrop ? (format.getInteger(KEY_CROP_BOTTOM) - format.getInteger(KEY_CROP_TOP)) + 1 : format.getInteger("height");
                }

                @Override // com.google.android.exoplayer.MediaCodecTrackRenderer
/* 338 */       protected boolean canReconfigureCodec(MediaCodec codec, boolean codecIsAdaptive, MediaFormat oldFormat, MediaFormat newFormat) {
                    return (newFormat.mimeType.equals("video/avc") && oldFormat.mimeType.equals("video/avc") && codecIsAdaptive) || (oldFormat.width == newFormat.width && oldFormat.height == newFormat.height);
                }

                @Override // com.google.android.exoplayer.MediaCodecTrackRenderer
/* 347 */       protected boolean processOutputBuffer(long timeUs, MediaCodec codec, ByteBuffer buffer, MediaCodec.BufferInfo bufferInfo, int bufferIndex, boolean shouldSkip) {
/* 348 */           if (shouldSkip) {
/* 349 */               skipOutputBuffer(codec, bufferIndex);
                        return true;
                    }
/* 353 */           long earlyUs = bufferInfo.presentationTimeUs - timeUs;
                    if (earlyUs < -30000) {
/* 356 */               dropOutputBuffer(codec, bufferIndex);
                        return true;
                    }
/* 360 */           if (!this.renderedFirstFrame) {
/* 361 */               renderOutputBuffer(codec, bufferIndex);
/* 362 */               this.renderedFirstFrame = true;
                        return true;
                    }
/* 366 */           if (getState() == 3 && earlyUs < 30000) {
                        if (earlyUs > 11000) {
                            try {
/* 372 */                       Thread.sleep((earlyUs - 10000) / 1000);
                            } catch (InterruptedException e) {
/* 374 */                       Thread.currentThread().interrupt();
                            }
                        }
/* 377 */               renderOutputBuffer(codec, bufferIndex);
                        return true;
                    }
/* 382 */           return false;
                }

/* 385 */       private void skipOutputBuffer(MediaCodec codec, int bufferIndex) {
/* 386 */           TraceUtil.beginSection("skipVideoBuffer");
/* 387 */           codec.releaseOutputBuffer(bufferIndex, false);
/* 388 */           TraceUtil.endSection();
                    this.codecCounters.skippedOutputBufferCount++;
                }

/* 392 */       private void dropOutputBuffer(MediaCodec codec, int bufferIndex) {
/* 393 */           TraceUtil.beginSection("dropVideoBuffer");
/* 394 */           codec.releaseOutputBuffer(bufferIndex, false);
/* 395 */           TraceUtil.endSection();
                    this.codecCounters.droppedOutputBufferCount++;
/* 397 */           this.droppedFrameCount++;
/* 398 */           if (this.droppedFrameCount == this.maxDroppedFrameCountToNotify) {
/* 399 */               notifyAndResetDroppedFrameCount();
                    }
                }

/* 403 */       private void renderOutputBuffer(MediaCodec codec, int bufferIndex) {
/* 404 */           if (this.lastReportedWidth != this.currentWidth || this.lastReportedHeight != this.currentHeight) {
/* 405 */               this.lastReportedWidth = this.currentWidth;
/* 406 */               this.lastReportedHeight = this.currentHeight;
/* 407 */               notifyVideoSizeChanged(this.currentWidth, this.currentHeight);
                    }
/* 409 */           TraceUtil.beginSection("renderVideoBuffer");
/* 410 */           codec.releaseOutputBuffer(bufferIndex, true);
/* 411 */           TraceUtil.endSection();
                    this.codecCounters.renderedOutputBufferCount++;
/* 413 */           if (!this.drawnToSurface) {
/* 414 */               this.drawnToSurface = true;
/* 415 */               notifyDrawnToSurface(this.surface);
                    }
                }

                /* JADX INFO: renamed from: com.google.android.exoplayer.MediaCodecVideoTrackRenderer$1 */
/* 419 */       class RunnableC08581 implements Runnable {
                    final /* synthetic */ int val$height;
                    final /* synthetic */ int val$width;

/* 420 */           RunnableC08581(int i, int i2) {
                        this.val$width = i;
                        this.val$height = i2;
                    }

                    @Override // java.lang.Runnable
/* 423 */           public void run() {
/* 424 */               MediaCodecVideoTrackRenderer.access$000(MediaCodecVideoTrackRenderer.this).onVideoSizeChanged(this.val$width, this.val$height);
                    }
                }

/* 419 */       private void notifyVideoSizeChanged(int width, int height) {
/* 420 */           if (this.eventHandler != null && this.eventListener != null) {
/* 421 */               this.eventHandler.post(new RunnableC08581(width, height));
                    }
                }

                /* JADX INFO: renamed from: com.google.android.exoplayer.MediaCodecVideoTrackRenderer$2 */
/* 430 */       class RunnableC08592 implements Runnable {
                    final /* synthetic */ Surface val$surface;

/* 431 */           RunnableC08592(Surface surface) {
                        this.val$surface = surface;
                    }

                    @Override // java.lang.Runnable
/* 434 */           public void run() {
/* 435 */               MediaCodecVideoTrackRenderer.access$000(MediaCodecVideoTrackRenderer.this).onDrawnToSurface(this.val$surface);
                    }
                }

/* 430 */       private void notifyDrawnToSurface(Surface surface) {
/* 431 */           if (this.eventHandler != null && this.eventListener != null) {
/* 432 */               this.eventHandler.post(new RunnableC08592(surface));
                    }
                }

/* 441 */       private void notifyAndResetDroppedFrameCount() {
/* 442 */           if (this.eventHandler != null && this.eventListener != null && this.droppedFrameCount > 0) {
/* 443 */               long now = SystemClock.elapsedRealtime();
/* 444 */               int countToNotify = this.droppedFrameCount;
/* 445 */               long elapsedToNotify = now - this.droppedFrameAccumulationStartTimeMs;
/* 446 */               this.droppedFrameCount = 0;
/* 447 */               this.droppedFrameAccumulationStartTimeMs = now;
/* 448 */               this.eventHandler.post(new RunnableC08603(countToNotify, elapsedToNotify));
                    }
                }

                /* JADX INFO: renamed from: com.google.android.exoplayer.MediaCodecVideoTrackRenderer$3 */
/* 446 */       class RunnableC08603 implements Runnable {
                    final /* synthetic */ int val$countToNotify;
                    final /* synthetic */ long val$elapsedToNotify;

/* 447 */           RunnableC08603(int i, long j) {
                        this.val$countToNotify = i;
                        this.val$elapsedToNotify = j;
                    }

                    @Override // java.lang.Runnable
/* 450 */           public void run() {
/* 451 */               MediaCodecVideoTrackRenderer.access$000(MediaCodecVideoTrackRenderer.this).onDroppedFrames(this.val$countToNotify, this.val$elapsedToNotify);
                    }
                }
            }
