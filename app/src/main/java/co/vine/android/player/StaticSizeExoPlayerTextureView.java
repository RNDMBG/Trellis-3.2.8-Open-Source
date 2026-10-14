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
            
            
            @TargetApi(14)
    public class StaticSizeExoPlayerTextureView extends TextureView implements ExoPlayer.Listener, MediaCodecVideoTrackRenderer.EventListener, TextureView.SurfaceTextureListener {
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

        static  void access$000(StaticSizeExoPlayerTextureView x0) {
            x0.setPlayerSurface();
                }

        static  ExoPlayer access$100(StaticSizeExoPlayerTextureView x0) {
            return x0.mPlayer;
                }

        static  int[] access$200(StaticSizeExoPlayerTextureView x0) {
            return x0.mPlayerInitLock;
                }

        static  Uri access$300(StaticSizeExoPlayerTextureView x0) {
            return x0.mFileUri;
                }

        static  void access$400(StaticSizeExoPlayerTextureView x0) {
            x0.setRenderersAndPreparePlayer();
                }

        public void setPlaybackEndedListener(PlaybackEndedListener playbackEndedListener) {
            this.mPlaybackEndedListener = playbackEndedListener;
                }

        public void setVideoSizeChangedListener(VideoSizeChangedListener videoSizeChangedListener) {
            this.mSizeListener = videoSizeChangedListener;
                }

        public void setExoPlayerErrorListener(ExoPlayerErrorListener errorListener) {
            this.mErrorListener = errorListener;
                }

        public void setOnPreparedListener(OnPreparedListener listener) {
            this.mOnPreparedListener = listener;
                }

        public void setOnReadyListener(OnReadyListener listener) {
            this.mOnReadyListener = listener;
                }

       public StaticSizeExoPlayerTextureView(Context context) {
           super(context);
            this.mWidth = 0;
            this.mHeight = 0;
            this.mFileUri = null;
            this.mLooping = false;
            this.mMuted = false;
            this.mLastState = -1;
            this.mPlayerInitLock = new int[0];
           this.mPlayerInitRunnable = new RunnableC04162();
           init();
                }

       public StaticSizeExoPlayerTextureView(Context context, AttributeSet attrs) {
           super(context, attrs);
            this.mWidth = 0;
            this.mHeight = 0;
            this.mFileUri = null;
            this.mLooping = false;
            this.mMuted = false;
            this.mLastState = -1;
            this.mPlayerInitLock = new int[0];
           this.mPlayerInitRunnable = new RunnableC04162();
           init();
                }

       public StaticSizeExoPlayerTextureView(Context context, AttributeSet attrs, int defStyle) {
           super(context, attrs, defStyle);
            this.mWidth = 0;
            this.mHeight = 0;
            this.mFileUri = null;
            this.mLooping = false;
            this.mMuted = false;
            this.mLastState = -1;
            this.mPlayerInitLock = new int[0];
           this.mPlayerInitRunnable = new RunnableC04162();
           init();
                }

       private void init() {
           this.mInitThread = new Thread(this.mPlayerInitRunnable);
           this.mHandler = new Handler(Looper.getMainLooper());
           setSurfaceTextureListener(this);
                }

       public void setSize(int width, int height) {
           this.mWidth = width;
           this.mHeight = height;
                }

                @Override 
       protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
           setMeasuredDimension(this.mWidth, this.mHeight);
                }

       public void openVideo(@NonNull Uri uri) {
           ExoPlayer player = this.mPlayer;
           if (player != null) {
               player.release();
                    }
           ExoPlayer player2 = ExoPlayer.Factory.newInstance(2, 1000, ExoPlayer.Factory.DEFAULT_MIN_REBUFFER_MS);
           player2.addListener(this);
           this.mPlayer = player2;
           this.mFileUri = uri;
                    synchronized (this.mPlayerInitLock) {
                        try {
                   if (this.mInitThread.isAlive()) {
                       this.mPlayerInitLock.notify();
                            } else if (this.mInitThread.getState() != Thread.State.NEW) {
                       this.mInitThread = new Thread(this.mPlayerInitRunnable);
                       this.mInitThread.start();
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }

       private synchronized void setRenderersAndPreparePlayer() {
                    try {
                        FrameworkSampleSource sampleSource = new FrameworkSampleSource(getContext(), this.mFileUri, null, 2);
               MediaCodecVideoTrackRenderer videoRenderer = new MediaCodecVideoTrackRenderer(sampleSource, 1, 0L, this.mHandler, this, 50);
               MediaCodecAudioTrackRenderer audioRenderer = new MediaCodecAudioTrackRenderer(sampleSource);
               this.mVideoRenderer = videoRenderer;
               this.mAudioRenderer = audioRenderer;
               post(new RunnableC04151(videoRenderer, audioRenderer));
                    } catch (Throwable th) {
                        throw th;
                    }
                }

                
       class RunnableC04151 implements Runnable {
                    final  MediaCodecAudioTrackRenderer val$audioRenderer;
                    final  MediaCodecVideoTrackRenderer val$videoRenderer;

           RunnableC04151(MediaCodecVideoTrackRenderer mediaCodecVideoTrackRenderer, MediaCodecAudioTrackRenderer mediaCodecAudioTrackRenderer) {
                        this.val$videoRenderer = mediaCodecVideoTrackRenderer;
                        this.val$audioRenderer = mediaCodecAudioTrackRenderer;
                    }

                    @Override 
           public void run() {
               StaticSizeExoPlayerTextureView.access$000(StaticSizeExoPlayerTextureView.this);
               if (StaticSizeExoPlayerTextureView.access$100(StaticSizeExoPlayerTextureView.this) != null) {
                   StaticSizeExoPlayerTextureView.access$100(StaticSizeExoPlayerTextureView.this).prepare(this.val$videoRenderer, this.val$audioRenderer);
                   StaticSizeExoPlayerTextureView.access$100(StaticSizeExoPlayerTextureView.this).seekTo(0);
                   StaticSizeExoPlayerTextureView.access$100(StaticSizeExoPlayerTextureView.this).setPlayWhenReady(true);
                        }
                    }
                }

       private void setPlayerSurface() {
           if (this.mPlayer != null && this.mVideoRenderer != null && this.mSurfaceTexture != null) {
               this.mPlayer.sendMessage(this.mVideoRenderer, 1, new Surface(this.mSurfaceTexture));
                    }
                }

                
       class RunnableC04162 implements Runnable {
           RunnableC04162() {
                    }

                    @Override 
           public void run() {
                        synchronized (StaticSizeExoPlayerTextureView.access$200(StaticSizeExoPlayerTextureView.this)) {
                   while (StaticSizeExoPlayerTextureView.access$300(StaticSizeExoPlayerTextureView.this) == null) {
                                try {
                                    try {
                               StaticSizeExoPlayerTextureView.access$200(StaticSizeExoPlayerTextureView.this).wait();
                                    } catch (InterruptedException e) {
                               return;
                                    }
                                } catch (Throwable th) {
                                    throw th;
                                }
                            }
                   StaticSizeExoPlayerTextureView.access$400(StaticSizeExoPlayerTextureView.this);
                        }
                    }
                }

                @Override 
       public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
           if (this.mOnPreparedListener != null && this.mLastState == 2 && playbackState == 3) {
               SLog.m446d("Notify onPrepare");
               this.mOnPreparedListener.onPrepared();
                    }
           if (this.mOnReadyListener != null && this.mLastState == 3 && playbackState == 4) {
               SLog.m446d("Notify onReady");
               this.mOnReadyListener.onReady();
                    }
           if (this.mPlaybackEndedListener != null && this.mLastState == 4 && playbackState == 5) {
               SLog.m446d("Notify onPlaybackEnded()");
               this.mPlaybackEndedListener.onPlaybackEnded();
                    }
           this.mLastState = playbackState;
           SLog.m447d("mjama  player state changed, playbackState={}", Integer.valueOf(playbackState));
                }

                @Override 
       public void onPlayWhenReadyCommitted() {
                }

                @Override 
       public void onPlayerError(ExoPlaybackException error) {
           if (this.mErrorListener != null) {
               this.mErrorListener.onError(error);
                    }
           CrashUtil.logException(error);
           SLog.m459e(error.getMessage(), (Throwable) error);
                }

                @Override 
       public void onDroppedFrames(int count, long elapsed) {
                }

                @Override 
       public void onVideoSizeChanged(int width, int height) {
           if (this.mSizeListener != null) {
               this.mSizeListener.onVideoSizeChanged(this.mPlayer, width, height);
                    }
                }

                @Override 
       public void onDrawnToSurface(Surface surface) {
                }

                @Override 
       public void onDecoderInitializationError(MediaCodecTrackRenderer.DecoderInitializationException e) {
                }

                @Override 
       public void onCryptoError(MediaCodec.CryptoException e) {
                }

                @Override 
       public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int width, int height) {
           SLog.m446d("ryango surface available");
           this.mSurfaceTexture = surfaceTexture;
           Thread thread = this.mInitThread;
           if (thread != null && !thread.isAlive() && thread.getState() == Thread.State.NEW) {
               thread.start();
                    } else {
               setPlayerSurface();
                    }
                }

                @Override 
       public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
                }

                @Override 
       public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
           return false;
                }

                @Override 
       public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {
           if (this.mSurfaceTexture != surfaceTexture) {
               this.mSurfaceTexture = surfaceTexture;
               setPlayerSurface();
                    }
                }

       public void setPlayWhenReady(boolean playWhenReady) {
           if (this.mPlayer != null) {
               this.mPlayer.setPlayWhenReady(playWhenReady);
                    }
                }

       public boolean getPlayWhenReady() {
           if (this.mPlayer != null) {
               return this.mPlayer.getPlayWhenReady();
                    }
                    return false;
                }

       public void setLooping(boolean looping) {
           this.mLooping = looping;
                }

       public void seekTo(int ms) {
           if (this.mPlayer != null) {
               this.mPlayer.seekTo(ms);
                    }
                }

       public int getCurrentPosition() {
           if (this.mPlayer != null) {
               return this.mPlayer.getCurrentPosition();
                    }
                    return 0;
                }

       public void setSurfaceRotation(int rotation) {
           Matrix m = new Matrix();
           m.postRotate(rotation, this.mWidth / 2, this.mHeight / 2);
           if (rotation == 90 || rotation == 270) {
               float ratio = this.mWidth / this.mHeight;
                        if (ratio > 0.0f) {
                   m.postScale(ratio, 1.0f / ratio, this.mWidth / 2, this.mHeight / 2);
                        }
                    }
           setTransform(m);
                }

       public void release() {
           if (this.mPlayer != null) {
               this.mPlayer.release();
               this.mPlayer = null;
                    }
           Thread initThread = this.mInitThread;
           if (initThread != null) {
               Thread.State state = initThread.getState();
               if (state == Thread.State.WAITING || state == Thread.State.BLOCKED) {
                   initThread.interrupt();
                        }
               this.mInitThread = null;
                    }
                }

       public void setMute(boolean mute) {
           if (this.mPlayer != null && this.mAudioRenderer != null) {
               this.mPlayer.sendMessage(this.mAudioRenderer, 1, Float.valueOf(mute ? 0.0f : 1.0f));
                    }
           this.mMuted = mute;
                }
            }
