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
            
            
            @TargetApi(16)
    public class MediaCodecVideoTrackRenderer extends MediaCodecTrackRenderer {
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

        static  EventListener access$000(MediaCodecVideoTrackRenderer x0) {
            return x0.eventListener;
                }

       public MediaCodecVideoTrackRenderer(SampleSource source, int videoScalingMode) {
           this(source, null, true, videoScalingMode);
                }

       public MediaCodecVideoTrackRenderer(SampleSource source, DrmSessionManager drmSessionManager, boolean playClearSamplesWithoutKeys, int videoScalingMode) {
           this(source, drmSessionManager, playClearSamplesWithoutKeys, videoScalingMode, 0L);
                }

       public MediaCodecVideoTrackRenderer(SampleSource source, int videoScalingMode, long allowedJoiningTimeMs) {
           this(source, null, true, videoScalingMode, allowedJoiningTimeMs);
                }

       public MediaCodecVideoTrackRenderer(SampleSource source, DrmSessionManager drmSessionManager, boolean playClearSamplesWithoutKeys, int videoScalingMode, long allowedJoiningTimeMs) {
           this(source, drmSessionManager, playClearSamplesWithoutKeys, videoScalingMode, allowedJoiningTimeMs, null, null, -1);
                }

       public MediaCodecVideoTrackRenderer(SampleSource source, int videoScalingMode, long allowedJoiningTimeMs, Handler eventHandler, EventListener eventListener, int maxDroppedFrameCountToNotify) {
           this(source, null, true, videoScalingMode, allowedJoiningTimeMs, eventHandler, eventListener, maxDroppedFrameCountToNotify);
                }

       public MediaCodecVideoTrackRenderer(SampleSource source, DrmSessionManager drmSessionManager, boolean playClearSamplesWithoutKeys, int videoScalingMode, long allowedJoiningTimeMs, Handler eventHandler, EventListener eventListener, int maxDroppedFrameCountToNotify) {
           super(source, drmSessionManager, playClearSamplesWithoutKeys, eventHandler, eventListener);
           this.videoScalingMode = videoScalingMode;
           this.allowedJoiningTimeUs = 1000 * allowedJoiningTimeMs;
           this.eventListener = eventListener;
           this.maxDroppedFrameCountToNotify = maxDroppedFrameCountToNotify;
           this.joiningDeadlineUs = -1L;
           this.currentWidth = -1;
           this.currentHeight = -1;
           this.lastReportedWidth = -1;
           this.lastReportedHeight = -1;
                }

                @Override 
       protected boolean handlesMimeType(String mimeType) {
                    return MimeTypes.isVideo(mimeType) && super.handlesMimeType(mimeType);
                }

                @Override 
       protected void onEnabled(long startTimeUs, boolean joining) {
           super.onEnabled(startTimeUs, joining);
           this.renderedFirstFrame = false;
           if (joining && this.allowedJoiningTimeUs > 0) {
               this.joiningDeadlineUs = (SystemClock.elapsedRealtime() * 1000) + this.allowedJoiningTimeUs;
                    }
                }

                @Override 
       protected void seekTo(long timeUs) throws ExoPlaybackException {
           super.seekTo(timeUs);
           this.renderedFirstFrame = false;
           this.joiningDeadlineUs = -1L;
                }

                @Override 
       protected boolean isReady() {
           if (super.isReady() && (this.renderedFirstFrame || !codecInitialized() || getSourceState() == 2)) {
               this.joiningDeadlineUs = -1L;
                        return true;
                    }
                    if (this.joiningDeadlineUs == -1) {
                        return false;
                    }
                    if (SystemClock.elapsedRealtime() * 1000 < this.joiningDeadlineUs) {
                        return true;
                    }
           this.joiningDeadlineUs = -1L;
                    return false;
                }

                @Override 
       protected void onStarted() {
           super.onStarted();
           this.droppedFrameCount = 0;
           this.droppedFrameAccumulationStartTimeMs = SystemClock.elapsedRealtime();
                }

                @Override 
       protected void onStopped() {
           super.onStopped();
           this.joiningDeadlineUs = -1L;
           notifyAndResetDroppedFrameCount();
                }

                @Override 
       public void onDisabled() {
           super.onDisabled();
           this.currentWidth = -1;
           this.currentHeight = -1;
           this.lastReportedWidth = -1;
           this.lastReportedHeight = -1;
                }

                @Override 
       public void handleMessage(int messageType, Object message) throws ExoPlaybackException {
           if (messageType == 1) {
               setSurface((Surface) message);
                    } else {
               super.handleMessage(messageType, message);
                    }
                }

       private void setSurface(Surface surface) throws ExoPlaybackException {
           if (this.surface != surface) {
               this.surface = surface;
               this.drawnToSurface = false;
               int state = getState();
               if (state == 2 || state == 3) {
                   releaseCodec();
                   maybeInitCodec();
                        }
                    }
                }

                @Override 
       protected boolean shouldInitCodec() {
                    return super.shouldInitCodec() && this.surface != null;
                }

                @Override 
       protected void configureCodec(MediaCodec codec, android.media.MediaFormat format, MediaCrypto crypto) {
           if (format.containsKey("rotation-degrees")) {
               format.setInteger("rotation-degrees", 0);
                    }
           codec.configure(format, this.surface, crypto, 0);
           codec.setVideoScalingMode(this.videoScalingMode);
                }

                @Override 
       protected void onOutputFormatChanged(android.media.MediaFormat format) {
           boolean hasCrop = format.containsKey(KEY_CROP_RIGHT) && format.containsKey(KEY_CROP_LEFT) && format.containsKey(KEY_CROP_BOTTOM) && format.containsKey(KEY_CROP_TOP);
           this.currentWidth = hasCrop ? (format.getInteger(KEY_CROP_RIGHT) - format.getInteger(KEY_CROP_LEFT)) + 1 : format.getInteger("width");
           this.currentHeight = hasCrop ? (format.getInteger(KEY_CROP_BOTTOM) - format.getInteger(KEY_CROP_TOP)) + 1 : format.getInteger("height");
                }

                @Override 
       protected boolean canReconfigureCodec(MediaCodec codec, boolean codecIsAdaptive, MediaFormat oldFormat, MediaFormat newFormat) {
                    return (newFormat.mimeType.equals("video/avc") && oldFormat.mimeType.equals("video/avc") && codecIsAdaptive) || (oldFormat.width == newFormat.width && oldFormat.height == newFormat.height);
                }

                @Override 
       protected boolean processOutputBuffer(long timeUs, MediaCodec codec, ByteBuffer buffer, MediaCodec.BufferInfo bufferInfo, int bufferIndex, boolean shouldSkip) {
           if (shouldSkip) {
               skipOutputBuffer(codec, bufferIndex);
                        return true;
                    }
           long earlyUs = bufferInfo.presentationTimeUs - timeUs;
                    if (earlyUs < -30000) {
               dropOutputBuffer(codec, bufferIndex);
                        return true;
                    }
           if (!this.renderedFirstFrame) {
               renderOutputBuffer(codec, bufferIndex);
               this.renderedFirstFrame = true;
                        return true;
                    }
           if (getState() == 3 && earlyUs < 30000) {
                        if (earlyUs > 11000) {
                            try {
                       Thread.sleep((earlyUs - 10000) / 1000);
                            } catch (InterruptedException e) {
                       Thread.currentThread().interrupt();
                            }
                        }
               renderOutputBuffer(codec, bufferIndex);
                        return true;
                    }
           return false;
                }

       private void skipOutputBuffer(MediaCodec codec, int bufferIndex) {
           TraceUtil.beginSection("skipVideoBuffer");
           codec.releaseOutputBuffer(bufferIndex, false);
           TraceUtil.endSection();
                    this.codecCounters.skippedOutputBufferCount++;
                }

       private void dropOutputBuffer(MediaCodec codec, int bufferIndex) {
           TraceUtil.beginSection("dropVideoBuffer");
           codec.releaseOutputBuffer(bufferIndex, false);
           TraceUtil.endSection();
                    this.codecCounters.droppedOutputBufferCount++;
           this.droppedFrameCount++;
           if (this.droppedFrameCount == this.maxDroppedFrameCountToNotify) {
               notifyAndResetDroppedFrameCount();
                    }
                }

       private void renderOutputBuffer(MediaCodec codec, int bufferIndex) {
           if (this.lastReportedWidth != this.currentWidth || this.lastReportedHeight != this.currentHeight) {
               this.lastReportedWidth = this.currentWidth;
               this.lastReportedHeight = this.currentHeight;
               notifyVideoSizeChanged(this.currentWidth, this.currentHeight);
                    }
           TraceUtil.beginSection("renderVideoBuffer");
           codec.releaseOutputBuffer(bufferIndex, true);
           TraceUtil.endSection();
                    this.codecCounters.renderedOutputBufferCount++;
           if (!this.drawnToSurface) {
               this.drawnToSurface = true;
               notifyDrawnToSurface(this.surface);
                    }
                }

                
       class RunnableC08581 implements Runnable {
                    final  int val$height;
                    final  int val$width;

           RunnableC08581(int i, int i2) {
                        this.val$width = i;
                        this.val$height = i2;
                    }

                    @Override 
           public void run() {
               MediaCodecVideoTrackRenderer.access$000(MediaCodecVideoTrackRenderer.this).onVideoSizeChanged(this.val$width, this.val$height);
                    }
                }

       private void notifyVideoSizeChanged(int width, int height) {
           if (this.eventHandler != null && this.eventListener != null) {
               this.eventHandler.post(new RunnableC08581(width, height));
                    }
                }

                
       class RunnableC08592 implements Runnable {
                    final  Surface val$surface;

           RunnableC08592(Surface surface) {
                        this.val$surface = surface;
                    }

                    @Override 
           public void run() {
               MediaCodecVideoTrackRenderer.access$000(MediaCodecVideoTrackRenderer.this).onDrawnToSurface(this.val$surface);
                    }
                }

       private void notifyDrawnToSurface(Surface surface) {
           if (this.eventHandler != null && this.eventListener != null) {
               this.eventHandler.post(new RunnableC08592(surface));
                    }
                }

       private void notifyAndResetDroppedFrameCount() {
           if (this.eventHandler != null && this.eventListener != null && this.droppedFrameCount > 0) {
               long now = SystemClock.elapsedRealtime();
               int countToNotify = this.droppedFrameCount;
               long elapsedToNotify = now - this.droppedFrameAccumulationStartTimeMs;
               this.droppedFrameCount = 0;
               this.droppedFrameAccumulationStartTimeMs = now;
               this.eventHandler.post(new RunnableC08603(countToNotify, elapsedToNotify));
                    }
                }

                
       class RunnableC08603 implements Runnable {
                    final  int val$countToNotify;
                    final  long val$elapsedToNotify;

           RunnableC08603(int i, long j) {
                        this.val$countToNotify = i;
                        this.val$elapsedToNotify = j;
                    }

                    @Override 
           public void run() {
               MediaCodecVideoTrackRenderer.access$000(MediaCodecVideoTrackRenderer.this).onDroppedFrames(this.val$countToNotify, this.val$elapsedToNotify);
                    }
                }
            }
