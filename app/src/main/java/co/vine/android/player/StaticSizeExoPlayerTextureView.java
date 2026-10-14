            package co.vine.android.player;

            import android.annotation.TargetApi;
            import android.content.Context;
            import android.graphics.Matrix;
            import android.graphics.SurfaceTexture;
            import android.media.MediaCodec;
            import android.net.Uri;
            import android.os.Handler;
            import android.os.Looper;
            import android.support.annotation.NonNull;
            import android.util.AttributeSet;
            import android.view.Surface;
            import android.view.TextureView;
            import co.vine.android.util.CrashUtil;
            import com.edisonwang.android.slog.SLog;
            import com.google.android.exoplayer.ExoPlaybackException;
            import com.google.android.exoplayer.ExoPlayer;
            import com.google.android.exoplayer.FrameworkSampleSource;
            import com.google.android.exoplayer.MediaCodecAudioTrackRenderer;
            import com.google.android.exoplayer.MediaCodecTrackRenderer;
            import com.google.android.exoplayer.MediaCodecVideoTrackRenderer;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            @TargetApi(14)
/* 33 */    public class StaticSizeExoPlayerTextureView extends TextureView implements ExoPlayer.Listener, MediaCodecVideoTrackRenderer.EventListener, TextureView.SurfaceTextureListener {
                private MediaCodecAudioTrackRenderer mAudioRenderer;
                private ExoPlayerErrorListener mErrorListener;
                private Uri mFileUri;
                private Handler mHandler;
                private int mHeight;
                private Thread mInitThread;
                private int mLastState;
                private boolean mLooping;
                private boolean mMuted;
                private OnPreparedListener mOnPreparedListener;
                private OnReadyListener mOnReadyListener;
                private PlaybackEndedListener mPlaybackEndedListener;
                private ExoPlayer mPlayer;
                private final int[] mPlayerInitLock;
                private Runnable mPlayerInitRunnable;
                private VideoSizeChangedListener mSizeListener;
                private SurfaceTexture mSurfaceTexture;
                private MediaCodecVideoTrackRenderer mVideoRenderer;
                private int mWidth;

                public interface ExoPlayerErrorListener {
                    void onError(ExoPlaybackException exoPlaybackException);
                }

                public interface OnPreparedListener {
                    void onPrepared();
                }

                public interface OnReadyListener {
                    void onReady();
                }

                public interface PlaybackEndedListener {
                    void onPlaybackEnded();
                }

                public interface VideoSizeChangedListener {
                    void onVideoSizeChanged(ExoPlayer exoPlayer, int i, int i2);
                }

/* 34 */        static /* synthetic */ void access$000(StaticSizeExoPlayerTextureView x0) {
/* 35 */            x0.setPlayerSurface();
                }

/* 34 */        static /* synthetic */ ExoPlayer access$100(StaticSizeExoPlayerTextureView x0) {
/* 35 */            return x0.mPlayer;
                }

/* 34 */        static /* synthetic */ int[] access$200(StaticSizeExoPlayerTextureView x0) {
/* 35 */            return x0.mPlayerInitLock;
                }

/* 34 */        static /* synthetic */ Uri access$300(StaticSizeExoPlayerTextureView x0) {
/* 35 */            return x0.mFileUri;
                }

/* 34 */        static /* synthetic */ void access$400(StaticSizeExoPlayerTextureView x0) {
/* 35 */            x0.setRenderersAndPreparePlayer();
                }

/* 62 */        public void setPlaybackEndedListener(PlaybackEndedListener playbackEndedListener) {
/* 63 */            this.mPlaybackEndedListener = playbackEndedListener;
                }

/* 86 */        public void setVideoSizeChangedListener(VideoSizeChangedListener videoSizeChangedListener) {
/* 87 */            this.mSizeListener = videoSizeChangedListener;
                }

/* 90 */        public void setExoPlayerErrorListener(ExoPlayerErrorListener errorListener) {
/* 91 */            this.mErrorListener = errorListener;
                }

/* 94 */        public void setOnPreparedListener(OnPreparedListener listener) {
/* 95 */            this.mOnPreparedListener = listener;
                }

/* 98 */        public void setOnReadyListener(OnReadyListener listener) {
/* 99 */            this.mOnReadyListener = listener;
                }

/* 102 */       public StaticSizeExoPlayerTextureView(Context context) {
/* 103 */           super(context);
/* 38 */            this.mWidth = 0;
/* 39 */            this.mHeight = 0;
/* 44 */            this.mFileUri = null;
/* 53 */            this.mLooping = false;
/* 54 */            this.mMuted = false;
/* 55 */            this.mLastState = -1;
/* 59 */            this.mPlayerInitLock = new int[0];
/* 181 */           this.mPlayerInitRunnable = new RunnableC04162();
/* 104 */           init();
                }

/* 107 */       public StaticSizeExoPlayerTextureView(Context context, AttributeSet attrs) {
/* 108 */           super(context, attrs);
/* 38 */            this.mWidth = 0;
/* 39 */            this.mHeight = 0;
/* 44 */            this.mFileUri = null;
/* 53 */            this.mLooping = false;
/* 54 */            this.mMuted = false;
/* 55 */            this.mLastState = -1;
/* 59 */            this.mPlayerInitLock = new int[0];
/* 181 */           this.mPlayerInitRunnable = new RunnableC04162();
/* 109 */           init();
                }

/* 112 */       public StaticSizeExoPlayerTextureView(Context context, AttributeSet attrs, int defStyle) {
/* 113 */           super(context, attrs, defStyle);
/* 38 */            this.mWidth = 0;
/* 39 */            this.mHeight = 0;
/* 44 */            this.mFileUri = null;
/* 53 */            this.mLooping = false;
/* 54 */            this.mMuted = false;
/* 55 */            this.mLastState = -1;
/* 59 */            this.mPlayerInitLock = new int[0];
/* 181 */           this.mPlayerInitRunnable = new RunnableC04162();
/* 114 */           init();
                }

/* 117 */       private void init() {
/* 118 */           this.mInitThread = new Thread(this.mPlayerInitRunnable);
/* 119 */           this.mHandler = new Handler(Looper.getMainLooper());
/* 120 */           setSurfaceTextureListener(this);
                }

/* 123 */       public void setSize(int width, int height) {
/* 124 */           this.mWidth = width;
/* 125 */           this.mHeight = height;
                }

                @Override // android.view.View
/* 129 */       protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
/* 130 */           setMeasuredDimension(this.mWidth, this.mHeight);
                }

/* 133 */       public void openVideo(@NonNull Uri uri) {
/* 134 */           ExoPlayer player = this.mPlayer;
/* 135 */           if (player != null) {
/* 136 */               player.release();
                    }
/* 138 */           ExoPlayer player2 = ExoPlayer.Factory.newInstance(2, 1000, ExoPlayer.Factory.DEFAULT_MIN_REBUFFER_MS);
/* 139 */           player2.addListener(this);
/* 140 */           this.mPlayer = player2;
/* 141 */           this.mFileUri = uri;
                    synchronized (this.mPlayerInitLock) {
                        try {
/* 144 */                   if (this.mInitThread.isAlive()) {
/* 145 */                       this.mPlayerInitLock.notify();
                            } else if (this.mInitThread.getState() != Thread.State.NEW) {
/* 147 */                       this.mInitThread = new Thread(this.mPlayerInitRunnable);
/* 148 */                       this.mInitThread.start();
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }

/* 153 */       private synchronized void setRenderersAndPreparePlayer() {
                    try {
                        FrameworkSampleSource sampleSource = new FrameworkSampleSource(getContext(), this.mFileUri, null, 2);
/* 155 */               MediaCodecVideoTrackRenderer videoRenderer = new MediaCodecVideoTrackRenderer(sampleSource, 1, 0L, this.mHandler, this, 50);
/* 157 */               MediaCodecAudioTrackRenderer audioRenderer = new MediaCodecAudioTrackRenderer(sampleSource);
/* 158 */               this.mVideoRenderer = videoRenderer;
/* 159 */               this.mAudioRenderer = audioRenderer;
/* 161 */               post(new RunnableC04151(videoRenderer, audioRenderer));
                    } catch (Throwable th) {
                        throw th;
                    }
                }

                /* JADX INFO: renamed from: co.vine.android.player.StaticSizeExoPlayerTextureView$1 */
/* 159 */       class RunnableC04151 implements Runnable {
                    final /* synthetic */ MediaCodecAudioTrackRenderer val$audioRenderer;
                    final /* synthetic */ MediaCodecVideoTrackRenderer val$videoRenderer;

/* 160 */           RunnableC04151(MediaCodecVideoTrackRenderer mediaCodecVideoTrackRenderer, MediaCodecAudioTrackRenderer mediaCodecAudioTrackRenderer) {
                        this.val$videoRenderer = mediaCodecVideoTrackRenderer;
                        this.val$audioRenderer = mediaCodecAudioTrackRenderer;
                    }

                    @Override // java.lang.Runnable
/* 163 */           public void run() {
/* 164 */               StaticSizeExoPlayerTextureView.access$000(StaticSizeExoPlayerTextureView.this);
/* 165 */               if (StaticSizeExoPlayerTextureView.access$100(StaticSizeExoPlayerTextureView.this) != null) {
/* 166 */                   StaticSizeExoPlayerTextureView.access$100(StaticSizeExoPlayerTextureView.this).prepare(this.val$videoRenderer, this.val$audioRenderer);
/* 167 */                   StaticSizeExoPlayerTextureView.access$100(StaticSizeExoPlayerTextureView.this).seekTo(0);
/* 168 */                   StaticSizeExoPlayerTextureView.access$100(StaticSizeExoPlayerTextureView.this).setPlayWhenReady(true);
                        }
                    }
                }

/* 174 */       private void setPlayerSurface() {
/* 175 */           if (this.mPlayer != null && this.mVideoRenderer != null && this.mSurfaceTexture != null) {
/* 176 */               this.mPlayer.sendMessage(this.mVideoRenderer, 1, new Surface(this.mSurfaceTexture));
                    }
                }

                /* JADX INFO: renamed from: co.vine.android.player.StaticSizeExoPlayerTextureView$2 */
/* 179 */       class RunnableC04162 implements Runnable {
/* 180 */           RunnableC04162() {
                    }

                    @Override // java.lang.Runnable
/* 183 */           public void run() {
                        synchronized (StaticSizeExoPlayerTextureView.access$200(StaticSizeExoPlayerTextureView.this)) {
/* 185 */                   while (StaticSizeExoPlayerTextureView.access$300(StaticSizeExoPlayerTextureView.this) == null) {
                                try {
                                    try {
/* 187 */                               StaticSizeExoPlayerTextureView.access$200(StaticSizeExoPlayerTextureView.this).wait();
                                    } catch (InterruptedException e) {
/* 194 */                               return;
                                    }
                                } catch (Throwable th) {
                                    throw th;
                                }
                            }
/* 192 */                   StaticSizeExoPlayerTextureView.access$400(StaticSizeExoPlayerTextureView.this);
                        }
                    }
                }

                @Override // com.google.android.exoplayer.ExoPlayer.Listener
/* 198 */       public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
/* 199 */           if (this.mOnPreparedListener != null && this.mLastState == 2 && playbackState == 3) {
/* 201 */               SLog.m446d("Notify onPrepare");
/* 202 */               this.mOnPreparedListener.onPrepared();
                    }
/* 204 */           if (this.mOnReadyListener != null && this.mLastState == 3 && playbackState == 4) {
/* 206 */               SLog.m446d("Notify onReady");
/* 207 */               this.mOnReadyListener.onReady();
                    }
/* 209 */           if (this.mPlaybackEndedListener != null && this.mLastState == 4 && playbackState == 5) {
/* 211 */               SLog.m446d("Notify onPlaybackEnded()");
/* 212 */               this.mPlaybackEndedListener.onPlaybackEnded();
                    }
/* 214 */           this.mLastState = playbackState;
/* 215 */           SLog.m447d("mjama  player state changed, playbackState={}", Integer.valueOf(playbackState));
                }

                @Override // com.google.android.exoplayer.ExoPlayer.Listener
/* 219 */       public void onPlayWhenReadyCommitted() {
                }

                @Override // com.google.android.exoplayer.ExoPlayer.Listener
/* 223 */       public void onPlayerError(ExoPlaybackException error) {
/* 224 */           if (this.mErrorListener != null) {
/* 225 */               this.mErrorListener.onError(error);
                    }
/* 227 */           CrashUtil.logException(error);
/* 228 */           SLog.m459e(error.getMessage(), (Throwable) error);
                }

                @Override // com.google.android.exoplayer.MediaCodecVideoTrackRenderer.EventListener
/* 232 */       public void onDroppedFrames(int count, long elapsed) {
                }

                @Override // com.google.android.exoplayer.MediaCodecVideoTrackRenderer.EventListener
/* 236 */       public void onVideoSizeChanged(int width, int height) {
/* 237 */           if (this.mSizeListener != null) {
/* 238 */               this.mSizeListener.onVideoSizeChanged(this.mPlayer, width, height);
                    }
                }

                @Override // com.google.android.exoplayer.MediaCodecVideoTrackRenderer.EventListener
/* 243 */       public void onDrawnToSurface(Surface surface) {
                }

                @Override // com.google.android.exoplayer.MediaCodecTrackRenderer.EventListener
/* 247 */       public void onDecoderInitializationError(MediaCodecTrackRenderer.DecoderInitializationException e) {
                }

                @Override // com.google.android.exoplayer.MediaCodecTrackRenderer.EventListener
/* 252 */       public void onCryptoError(MediaCodec.CryptoException e) {
                }

                @Override // android.view.TextureView.SurfaceTextureListener
/* 256 */       public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int width, int height) {
/* 257 */           SLog.m446d("ryango surface available");
/* 258 */           this.mSurfaceTexture = surfaceTexture;
/* 259 */           Thread thread = this.mInitThread;
/* 260 */           if (thread != null && !thread.isAlive() && thread.getState() == Thread.State.NEW) {
/* 261 */               thread.start();
                    } else {
/* 263 */               setPlayerSurface();
                    }
                }

                @Override // android.view.TextureView.SurfaceTextureListener
/* 268 */       public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
                }

                @Override // android.view.TextureView.SurfaceTextureListener
/* 272 */       public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
/* 273 */           return false;
                }

                @Override // android.view.TextureView.SurfaceTextureListener
/* 277 */       public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {
/* 278 */           if (this.mSurfaceTexture != surfaceTexture) {
/* 279 */               this.mSurfaceTexture = surfaceTexture;
/* 280 */               setPlayerSurface();
                    }
                }

/* 284 */       public void setPlayWhenReady(boolean playWhenReady) {
/* 285 */           if (this.mPlayer != null) {
/* 286 */               this.mPlayer.setPlayWhenReady(playWhenReady);
                    }
                }

/* 290 */       public boolean getPlayWhenReady() {
/* 291 */           if (this.mPlayer != null) {
/* 292 */               return this.mPlayer.getPlayWhenReady();
                    }
                    return false;
                }

/* 297 */       public void setLooping(boolean looping) {
/* 298 */           this.mLooping = looping;
                }

/* 301 */       public void seekTo(int ms) {
/* 302 */           if (this.mPlayer != null) {
/* 303 */               this.mPlayer.seekTo(ms);
                    }
                }

/* 307 */       public int getCurrentPosition() {
/* 308 */           if (this.mPlayer != null) {
/* 309 */               return this.mPlayer.getCurrentPosition();
                    }
                    return 0;
                }

/* 314 */       public void setSurfaceRotation(int rotation) {
/* 315 */           Matrix m = new Matrix();
/* 316 */           m.postRotate(rotation, this.mWidth / 2, this.mHeight / 2);
/* 317 */           if (rotation == 90 || rotation == 270) {
/* 318 */               float ratio = this.mWidth / this.mHeight;
                        if (ratio > 0.0f) {
/* 320 */                   m.postScale(ratio, 1.0f / ratio, this.mWidth / 2, this.mHeight / 2);
                        }
                    }
/* 323 */           setTransform(m);
                }

/* 326 */       public void release() {
/* 327 */           if (this.mPlayer != null) {
/* 328 */               this.mPlayer.release();
/* 329 */               this.mPlayer = null;
                    }
/* 331 */           Thread initThread = this.mInitThread;
/* 332 */           if (initThread != null) {
/* 333 */               Thread.State state = initThread.getState();
/* 334 */               if (state == Thread.State.WAITING || state == Thread.State.BLOCKED) {
/* 335 */                   initThread.interrupt();
                        }
/* 337 */               this.mInitThread = null;
                    }
                }

/* 341 */       public void setMute(boolean mute) {
/* 342 */           if (this.mPlayer != null && this.mAudioRenderer != null) {
/* 343 */               this.mPlayer.sendMessage(this.mAudioRenderer, 1, Float.valueOf(mute ? 0.0f : 1.0f));
                    }
/* 346 */           this.mMuted = mute;
                }
            }
