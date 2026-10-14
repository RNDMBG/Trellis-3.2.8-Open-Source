            package co.vine.android.player;

            import android.annotation.TargetApi;
            import android.content.Context;
            import android.content.Intent;
            import android.graphics.SurfaceTexture;
            import android.media.MediaPlayer;
            import android.net.Uri;
            import android.os.Build;
            import android.os.Handler;
            import android.os.HandlerThread;
            import android.os.Looper;
            import android.os.Message;
            import android.support.annotation.NonNull;
            import android.util.AttributeSet;
            import android.util.Log;
            import android.view.KeyEvent;
            import android.view.MotionEvent;
            import android.view.Surface;
            import android.view.TextureView;
            import android.view.View;
            import android.view.accessibility.AccessibilityEvent;
            import android.view.accessibility.AccessibilityNodeInfo;
            import android.widget.MediaController;
            import co.vine.android.cache.CacheKey;
            import co.vine.android.embed.player.PlayerState;
            import co.vine.android.embed.player.VinePlayer;
            import co.vine.android.util.CrashUtil;
            import co.vine.android.util.SystemUtil;
            import com.edisonwang.android.slog.SLog;
            import java.io.IOException;
            import java.util.Map;
            
            
            @TargetApi(14)
    public class SdkVideoView extends TextureView implements MediaController.MediaPlayerControl, VideoViewInterface {
                private static final boolean DEFAULT_GLOBAL_USE_VINE_PLAYER = false;
                private static final boolean DISABLE_OPEN_ON_SEPARATE_THREAD;
                private static final boolean IS_DEFECTIVE_ON_THREADED_MEDIA_PLAYERS;
                private static final int[] LOCK;
                private static final int MEDIA_OP_OPEN = 1;
                private static final int MEDIA_OP_RELEASE = 0;
                private static final int MEDIA_OP_SEEK_TO = 1000;
                private static final int MEDIA_OP_START = 2;
                private static final int MEDIA_OP_VOLUME_FULL = 4;
                private static final int MEDIA_OP_VOLUME_MUTE = 3;
                private static boolean OPEN_ON_MAIN_THREAD = false;
                private static final long OPEN_ON_MAIN_THREAD_THRESHOLD = 15;
                public static final String PREPARE_SUCCESS_LOG = "Successfully prepared the new MediaPlayer.";
                public static final int STATE_BUFFERING = 3;
                public static final int STATE_ERROR = -1;
                public static final int STATE_IDLE = 1;
                public static final int STATE_PAUSED = 7;
                public static final int STATE_PLAYBACK_COMPLETED = 5;
                public static final int STATE_PLAYING = 6;
                public static final int STATE_PREPARED = 4;
                public static final int STATE_PREPARING = 2;
                private static final String TAG;
                private static int currentState;
                private static Uri currentUri;
                private static View currentView;
                private static boolean sGloablUseVinePlayer;
                private static int sPlayingCount;
                private static boolean sRunMediaOpsOnSeparateThread;
                private static int sTotalVideoViewed;
                private static MediaPlayer singlePlayer;
                private final boolean SINGLE_PLAYER;
                private AttributeSet mAttributes;
                private int mAudioSession;
                private boolean mAutoPlayOnPrepared;
                private boolean mAutoShow;
                private MediaPlayer.OnBufferingUpdateListener mBufferingUpdateListener;
                private CacheKey mCacheKey;
                private MediaPlayer.OnCompletionListener mCompletionListener;
                private Context mContext;
                private int mCurrentBufferPercentage;
                public int mCurrentState;
                private MediaPlayer.OnVideoSizeChangedListener mCustomOnSizeChangedListener;
                private boolean mEnsureVisibilityWhenStart;
                private MediaPlayer.OnErrorListener mErrorListener;
                private boolean mHasNotRetriedBefore;
                private Map<String, String> mHeaders;
                private boolean mIsPrepared;
                private boolean mIsSeeking;
                private boolean mLooping;
                private MediaController mMediaController;
                private Handler mMediaOpHandler;
                private HandlerThread mMediaOpThread;
                private MediaPlayer mMediaPlayer;
                private boolean mMuted;
                private int mNextSeek;
                private VideoViewInterface.OnCompletionListener mOnCompletionListener;
                private VideoViewInterface.OnErrorListener mOnErrorListener;
                private MediaPlayer.OnInfoListener mOnInfoListener;
                private final Runnable mOnOpenErrorListener;
                private VideoViewInterface.OnPreparedListener mOnPreparedListener;
                private MediaPlayer.OnSeekCompleteListener mOnSeekCompleteListener;
                private String mPath;
                private int mPlayingPosition;
                private final Runnable mPrepareNotifyRunnable;
                private long mPrepareStart;
                MediaPlayer.OnPreparedListener mPreparedListener;
                private volatile String mReleaseTag;
                private boolean mReportOpenError;
                private TextureView.SurfaceTextureListener mSHCallback;
                private final SaveVideoClicker mSaveVideoClicker;
                private int mSeekWhenPrepared;
                private volatile boolean mShouldClearState;
                final MediaPlayer.OnVideoSizeChangedListener mSizeChangedListener;
                private int mSurfaceHeight;
                private SurfaceReadyListener mSurfaceReadyListener;
                private SurfaceUpdatedListener mSurfaceUpdatedListener;
                private int mSurfaceWidth;
                private int mTargetState;
                private final Handler mUiOpsHandler;
                private Uri mUri;
                private boolean mUseVinePlayer;
                protected int mVideoHeight;
                protected int mVideoWidth;
                private VinePlayer mVinePlayer;
                private VinePlayer.EventListener mVinePlayerListener;
                private final Runnable mVisibileStartRunnable;
                private boolean mWasMuted;

                public interface SurfaceReadyListener {
                    void onSurfaceTextureAvailable(SdkVideoView sdkVideoView);
                }

                public interface SurfaceUpdatedListener {
                    void onSurfaceUpdated();
                }

        static  MediaPlayer access$000(SdkVideoView x0) {
            return x0.getPlayer();
                }

        static  MediaPlayer.OnErrorListener access$100(SdkVideoView x0) {
            return x0.mErrorListener;
                }

        static  void access$1001(SdkVideoView x0, boolean x1) {
            x0.setKeepScreenOn(x1);
                }

        static  MediaPlayer.OnVideoSizeChangedListener access$1100(SdkVideoView x0) {
            return x0.mCustomOnSizeChangedListener;
                }

        static  long access$1200(SdkVideoView x0) {
            return x0.mPrepareStart;
                }

        static  boolean access$1302(SdkVideoView x0, boolean x1) {
            x0.mIsPrepared = x1;
                    return x1;
                }

        static  boolean access$1400(SdkVideoView x0) {
            return x0.mAutoPlayOnPrepared;
                }

        static  void access$1500(SdkVideoView x0, int x1) {
            x0.postMediaOp(x1);
                }

        static  void access$1600(SdkVideoView x0) {
            x0.onPlayerPrepared();
                }

        static  Runnable access$1700(SdkVideoView x0) {
            return x0.mPrepareNotifyRunnable;
                }

        static  Handler access$1800(SdkVideoView x0) {
            return x0.mUiOpsHandler;
                }

        static  int access$1902(SdkVideoView x0, int x1) {
            x0.mCurrentBufferPercentage = x1;
                    return x1;
                }

        static  void access$200(SdkVideoView x0, int x1) {
            x0.runMediaOps(x1);
                }

        static  boolean access$2002(SdkVideoView x0, boolean x1) {
            x0.mIsSeeking = x1;
                    return x1;
                }

        static  int access$2100(SdkVideoView x0) {
            return x0.mNextSeek;
                }

        static  int access$2102(SdkVideoView x0, int x1) {
            x0.mNextSeek = x1;
                    return x1;
                }

        static  SurfaceReadyListener access$2200(SdkVideoView x0) {
            return x0.mSurfaceReadyListener;
                }

        static  void access$2300(SdkVideoView x0) {
            x0.openVideo();
                }

        static  int access$2402(SdkVideoView x0, int x1) {
            x0.mSurfaceWidth = x1;
                    return x1;
                }

        static  int access$2502(SdkVideoView x0, int x1) {
            x0.mSurfaceHeight = x1;
                    return x1;
                }

        static  int access$2600(SdkVideoView x0) {
            return x0.mTargetState;
                }

        static  boolean access$2700(SdkVideoView x0) {
            return x0.hasPlayer();
                }

        static  int access$2800(SdkVideoView x0) {
            return x0.mSeekWhenPrepared;
                }

        static  MediaController access$2900(SdkVideoView x0) {
            return x0.mMediaController;
                }

        static  boolean access$300(SdkVideoView x0, int x1, int x2) {
            return x0.onError(x1, x2);
                }

        static  VinePlayer access$3000(SdkVideoView x0) {
            return x0.mVinePlayer;
                }

        static  void access$3100(SdkVideoView x0, String x1, boolean x2, boolean x3) {
            x0.release(x1, x2, x3);
                }

        static  SurfaceUpdatedListener access$3200(SdkVideoView x0) {
            return x0.mSurfaceUpdatedListener;
                }

        static  VinePlayer.EventListener access$400(SdkVideoView x0) {
            return x0.mVinePlayerListener;
                }

        static  void access$500(SdkVideoView x0, int x1, int x2) {
            x0.onVideoSizeChanged(x1, x2);
                }

        static  void access$600(SdkVideoView x0) {
            x0.onComplete();
                }

        static  void access$700(SdkVideoView x0, int x1) {
            x0.setCurrentState(x1);
                }

        static  VideoViewInterface.OnPreparedListener access$800(SdkVideoView x0) {
            return x0.mOnPreparedListener;
                }

                static {
            IS_DEFECTIVE_ON_THREADED_MEDIA_PLAYERS = Build.VERSION.SDK_INT < 21;
            TAG = SdkVideoView.class.getSimpleName();
            sGloablUseVinePlayer = false;
            DISABLE_OPEN_ON_SEPARATE_THREAD = IS_DEFECTIVE_ON_THREADED_MEDIA_PLAYERS;
            sRunMediaOpsOnSeparateThread = !IS_DEFECTIVE_ON_THREADED_MEDIA_PLAYERS;
            OPEN_ON_MAIN_THREAD = false;
           singlePlayer = null;
           currentState = 1;
           LOCK = new int[0];
                }

        private static synchronized void onPlay() {
                    try {
                        sPlayingCount++;
                if (sPlayingCount > 1) {
                    SLog.m455e("Playing count > 1");
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }

        private static synchronized void onPause() {
                    try {
                        sPlayingCount--;
                    } catch (Throwable th) {
                        throw th;
                    }
                }

        public static void setRunMediaOpsOnSeparateThread(boolean enabled) {
            sRunMediaOpsOnSeparateThread = enabled;
                }

        public static void setGlobalUseVinePlayer(boolean useVinePlayer) {
            sGloablUseVinePlayer = useVinePlayer && Build.VERSION.SDK_INT >= 16;
                }

        public static boolean getGlobalUseVinePlayer() {
           return false;
                }

       public static boolean getDefaultGlobalVinePlayer() {
           return false;
                }

                
       class RunnableC04001 implements Runnable {
           RunnableC04001() {
                    }

                    @Override 
           public void run() {
               SdkVideoView.access$100(SdkVideoView.this).onError(SdkVideoView.access$000(SdkVideoView.this), 1, 0);
                    }
                }

       private MediaPlayer getPlayer() {
                    return this.SINGLE_PLAYER ? singlePlayer : this.mMediaPlayer;
                }

       private boolean hasPlayer() {
                    return (getPlayer() == null && this.mVinePlayer == null) ? false : true;
                }

       public int getCurrentState() {
                    return this.SINGLE_PLAYER ? currentState : this.mCurrentState;
                }

       private void setCurrentState(int state) {
           if (this.SINGLE_PLAYER) {
               currentState = state;
                    } else {
               this.mCurrentState = state;
                    }
                }

       public SdkVideoView(Context context) {
           super(context);
           this.mMediaPlayer = null;
           this.mCurrentState = 1;
           this.mTargetState = 1;
           this.mPlayingPosition = -1;
           this.mMuted = false;
           this.mWasMuted = false;
           this.mAudioSession = 0;
           this.mSaveVideoClicker = new SaveVideoClicker();
           this.mUiOpsHandler = new Handler(Looper.getMainLooper());
           this.mOnOpenErrorListener = new RunnableC04001();
           this.mSizeChangedListener = new C04105();
           this.mPrepareNotifyRunnable = new RunnableC04116();
           this.mPreparedListener = new C04127();
           this.mCompletionListener = new C04138();
           this.mErrorListener = new C04149();
           this.mBufferingUpdateListener = new C040110();
           this.mOnSeekCompleteListener = new C040211();
           this.mSHCallback = new TextureViewSurfaceTextureListenerC040312();
           this.mReleaseTag = "";
          this.mVisibileStartRunnable = new RunnableC040514();
           this.SINGLE_PLAYER = SystemUtil.isSinglePlayerEnabled(context);
           this.mUseVinePlayer = getGlobalUseVinePlayer();
           initVideoView();
                }

       public SdkVideoView(Context context, AttributeSet attrs) {
           super(context, attrs);
           this.mMediaPlayer = null;
           this.mCurrentState = 1;
           this.mTargetState = 1;
           this.mPlayingPosition = -1;
           this.mMuted = false;
           this.mWasMuted = false;
           this.mAudioSession = 0;
           this.mSaveVideoClicker = new SaveVideoClicker();
           this.mUiOpsHandler = new Handler(Looper.getMainLooper());
           this.mOnOpenErrorListener = new RunnableC04001();
           this.mSizeChangedListener = new C04105();
           this.mPrepareNotifyRunnable = new RunnableC04116();
           this.mPreparedListener = new C04127();
           this.mCompletionListener = new C04138();
           this.mErrorListener = new C04149();
           this.mBufferingUpdateListener = new C040110();
           this.mOnSeekCompleteListener = new C040211();
           this.mSHCallback = new TextureViewSurfaceTextureListenerC040312();
           this.mReleaseTag = "";
          this.mVisibileStartRunnable = new RunnableC040514();
           this.SINGLE_PLAYER = SystemUtil.isSinglePlayerEnabled(context);
           this.mUseVinePlayer = getGlobalUseVinePlayer();
           this.mAttributes = attrs;
           initVideoView();
                }

       public void setUseVinePlayer(boolean useVinePlayer) {
           this.mUseVinePlayer = useVinePlayer;
                }

       public SdkVideoView(Context context, AttributeSet attrs, int defStyle) {
           super(context, attrs, defStyle);
           this.mMediaPlayer = null;
           this.mCurrentState = 1;
           this.mTargetState = 1;
           this.mPlayingPosition = -1;
           this.mMuted = false;
           this.mWasMuted = false;
           this.mAudioSession = 0;
           this.mSaveVideoClicker = new SaveVideoClicker();
           this.mUiOpsHandler = new Handler(Looper.getMainLooper());
           this.mOnOpenErrorListener = new RunnableC04001();
           this.mSizeChangedListener = new C04105();
           this.mPrepareNotifyRunnable = new RunnableC04116();
           this.mPreparedListener = new C04127();
           this.mCompletionListener = new C04138();
           this.mErrorListener = new C04149();
           this.mBufferingUpdateListener = new C040110();
           this.mOnSeekCompleteListener = new C040211();
           this.mSHCallback = new TextureViewSurfaceTextureListenerC040312();
           this.mReleaseTag = "";
          this.mVisibileStartRunnable = new RunnableC040514();
           this.SINGLE_PLAYER = SystemUtil.isSinglePlayerEnabled(context);
           this.mUseVinePlayer = getGlobalUseVinePlayer();
           this.mAttributes = attrs;
           initVideoView();
                }

       public void onViewClicked() {
           this.mSaveVideoClicker.onClick(this);
                }

                @Override 
       protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
           int width = getDefaultSize(this.mVideoWidth, widthMeasureSpec);
           int height = getDefaultSize(this.mVideoHeight, heightMeasureSpec);
           if (this.mVideoWidth > 0 && this.mVideoHeight > 0) {
               if (this.mVideoWidth * height > this.mVideoHeight * width) {
                   height = (this.mVideoHeight * width) / this.mVideoWidth;
                        } else if (this.mVideoWidth * height < this.mVideoHeight * width) {
                   width = (this.mVideoWidth * height) / this.mVideoHeight;
                        }
                    }
           setMeasuredDimension(width, height);
                }

                @Override 
       public int getPlayingPosition() {
           return this.mPlayingPosition;
                }

       public void setPlayingPosition(int playingPosition) {
           this.mPlayingPosition = playingPosition;
                }

                @Override 
       protected void onAttachedToWindow() {
           super.onAttachedToWindow();
           this.mMediaOpThread = new HandlerThread("MediaOp");
           this.mMediaOpThread.start();
           this.mMediaOpHandler = new HandlerC04072(this.mMediaOpThread.getLooper());
                }

                
       class HandlerC04072 extends Handler {
           HandlerC04072(Looper x0) {
                        super(x0);
                    }

                    @Override 
           public void handleMessage(Message msg) {
               SdkVideoView.access$200(SdkVideoView.this, msg.what);
                    }
                }

       private void initVideoView() {
           this.mContext = getContext();
           this.mVideoWidth = 0;
           this.mVideoHeight = 0;
           setSurfaceTextureListener(this.mSHCallback);
           setFocusable(true);
           setFocusableInTouchMode(true);
           requestFocus();
           this.mTargetState = 1;
                }

                @Override 
       public void onInitializeAccessibilityEvent(@NonNull AccessibilityEvent event) {
           super.onInitializeAccessibilityEvent(event);
           event.setClassName(SdkVideoView.class.getName());
                }

                @Override 
       public void onInitializeAccessibilityNodeInfo(@NonNull AccessibilityNodeInfo info) {
           super.onInitializeAccessibilityNodeInfo(info);
           info.setClassName(SdkVideoView.class.getName());
                }

                @Override 
       public void setVideoPath(String path) {
           setVideoPathDirect(path);
                }

                @Override 
       public void setVideoPathDirect(String path) {
           SLog.m447d("mPath={}.", path);
           this.mPath = path;
           setVideoURI(path != null ? Uri.parse(path) : null);
                }

       public String getPath() {
           return this.mPath;
                }

       public void setVideoURI(Uri uri) {
           setVideoURI(uri, null);
                }

       private void setVideoURI(Uri uri, Map<String, String> headers) {
           this.mUri = uri;
           currentUri = this.mUri;
           this.mHeaders = headers;
           this.mSeekWhenPrepared = 0;
           openVideo();
           requestLayout();
           postInvalidate();
                }

       public boolean retryOpenVideo(boolean forced) {
           SLog.m447d("Retry open video: {}", Boolean.valueOf(!this.mHasNotRetriedBefore));
           if (!forced && this.mHasNotRetriedBefore) {
                        return false;
                    }
           this.mHasNotRetriedBefore = true;
           openVideo();
                    return true;
                }

       public boolean isSurfaceReady() {
           return isAvailable();
                }

       public void startOpenVideo() {
           openVideo();
                }

       private void openVideo() {
           if (OPEN_ON_MAIN_THREAD) {
               mediaOpOpen();
                    } else {
               postMediaOp(1);
                    }
                }

       public void setVinePlayerListener(VinePlayer.EventListener listener) {
           this.mVinePlayerListener = listener;
                }

                
       class C04083 implements VinePlayer.EventListener {
           C04083() {
                    }

                    @Override 
           public void onError(PlayerState state, int operation, Throwable e) {
               SLog.m459e("VinePlayer error", e);
               SdkVideoView.access$300(SdkVideoView.this, operation, state.ordinal());
               if (SdkVideoView.access$400(SdkVideoView.this) != null) {
                   SdkVideoView.access$400(SdkVideoView.this).onError(state, operation, e);
                        }
                    }

                    @Override 
           public void onVideoSizeChanged(int width, int height) {
               SdkVideoView.access$500(SdkVideoView.this, width, height);
               if (SdkVideoView.access$400(SdkVideoView.this) != null) {
                   SdkVideoView.access$400(SdkVideoView.this).onVideoSizeChanged(width, height);
                        }
                    }

                    @Override 
           public void onLoopCompleted() {
               SdkVideoView.access$600(SdkVideoView.this);
               if (SdkVideoView.access$400(SdkVideoView.this) != null) {
                   SdkVideoView.access$400(SdkVideoView.this).onLoopCompleted();
                        }
                    }

                    @Override 
           public void onPrepared() {
               SdkVideoView.access$700(SdkVideoView.this, 4);
               if (SdkVideoView.access$800(SdkVideoView.this) != null) {
                   SdkVideoView.access$800(SdkVideoView.this).onPrepared(SdkVideoView.this);
                        }
               if (SdkVideoView.access$400(SdkVideoView.this) != null) {
                   SdkVideoView.access$400(SdkVideoView.this).onPrepared();
                        }
                    }

                    @Override 
           public void onFirstFrameRendererd() {
               if (SdkVideoView.access$400(SdkVideoView.this) != null) {
                   SdkVideoView.access$400(SdkVideoView.this).onFirstFrameRendererd();
                        }
                    }

                    @Override 
           public void onPastError(Exception error) {
               if (SdkVideoView.access$400(SdkVideoView.this) != null) {
                   SdkVideoView.access$400(SdkVideoView.this).onPastError(error);
                        }
                    }
                }

                @TargetApi(16)
       private void openVinePlayer(Surface surface) {
                    try {
               this.mVinePlayer = new VinePlayer(this.mContext);
               this.mVinePlayer.setEventListener(new C04083());
               this.mVinePlayer.setSurface(surface);
               this.mVinePlayer.prepareAsync(this.mUri, this.mAutoPlayOnPrepared);
               attachMediaController();
               setCurrentState(2);
                        int i = sTotalVideoViewed + 1;
                        sTotalVideoViewed = i;
               CrashUtil.set("videosViewed", i);
                    } catch (IllegalArgumentException | IllegalStateException | SecurityException ex) {
               if (this.mReportOpenError) {
                   CrashUtil.logException(ex, "Exo Unable to open content {}, {}.", this.mUri, getTag());
                        }
               setCurrentState(-1);
               this.mTargetState = -1;
               onError(1, 0);
                    }
                }

       private void openMediaPlayer(Surface surface) {
           long startTime = System.currentTimeMillis();
           PlayerMaker holder = new PlayerMaker(null);
           if (this.SINGLE_PLAYER && singlePlayer == null) {
               singlePlayer = makePlayerOnMainThread(holder);
                    }
                    try {
               if (!this.SINGLE_PLAYER) {
                   setPlayer(makePlayerOnMainThread(holder));
                        }
               MediaPlayer player = getPlayer();
               if (this.mAudioSession != 0) {
                   player.setAudioSessionId(this.mAudioSession);
                        } else {
                   this.mAudioSession = player.getAudioSessionId();
                        }
               player.setOnPreparedListener(this.mPreparedListener);
               player.setOnVideoSizeChangedListener(this.mSizeChangedListener);
               player.setOnCompletionListener(this.mCompletionListener);
               player.setOnErrorListener(this.mErrorListener);
               player.setOnInfoListener(this.mOnInfoListener);
               player.setOnBufferingUpdateListener(this.mBufferingUpdateListener);
               player.setOnSeekCompleteListener(this.mOnSeekCompleteListener);
               this.mCurrentBufferPercentage = 0;
               player.setDataSource(this.mContext, this.mUri, this.mHeaders);
               player.setSurface(surface);
               if (this.mLooping) {
                   player.setLooping(true);
                        }
               setKeepScreenOn(true);
               player.setAudioStreamType(3);
               if (this.mMuted) {
                   player.setVolume(0.0f, 0.0f);
                        }
               this.mNextSeek = -1;
               player.prepareAsync();
                        int i = sTotalVideoViewed + 1;
                        sTotalVideoViewed = i;
               CrashUtil.set("videosViewed", i);
               setCurrentState(2);
               attachMediaController();
               Log.d(TAG, PREPARE_SUCCESS_LOG);
               this.mPrepareStart = System.currentTimeMillis();
               long openTime = this.mPrepareStart - startTime;
               OPEN_ON_MAIN_THREAD = DISABLE_OPEN_ON_SEPARATE_THREAD || openTime < OPEN_ON_MAIN_THREAD_THRESHOLD;
               SLog.m465i("Open took {}ms", Long.valueOf(openTime));
                    } catch (IOException | IllegalArgumentException | IllegalStateException | SecurityException ex) {
               if (SLog.sLogsOn) {
                   SLog.m458e("Error opening video: {}.", (Object) ex.getMessage(), (Throwable) ex);
                        }
               if (this.mReportOpenError) {
                   CrashUtil.logException(ex, "Unable to open content {}, {}.", this.mUri, getTag());
                        }
               setCurrentState(-1);
               this.mTargetState = -1;
               if (Looper.getMainLooper() != Looper.myLooper()) {
                   this.mUiOpsHandler.post(this.mOnOpenErrorListener);
                        } else {
                   this.mOnOpenErrorListener.run();
                        }
                    }
                }

       private MediaPlayer makePlayerOnMainThread(PlayerMaker holder) {
           if (Looper.myLooper() != Looper.getMainLooper()) {
               this.mUiOpsHandler.postAtFrontOfQueue(holder);
               while (!holder.isReady()) {
                            try {
                       Thread.sleep(1L);
                            } catch (InterruptedException e) {
                       SLog.m464i("Waiting failed for player holder.");
                            }
                        }
                    } else {
               holder.run();
                    }
           return holder.getPlayer();
                }

       private String getErrorMessageFromFrameworkError(int frameworkError) {
           if (frameworkError == -1004) {
               return "File or network related operation errors.";
                    }
           if (frameworkError == -1007) {
               return "Bitstream is not conforming to the related coding standard or file spec.";
                    }
           if (frameworkError == 100) {
               return "TextureVideoView error. Media server died. In this case, the application must release the MediaPlayer object and instantiate a new one.";
                    }
           if (frameworkError == -110) {
               return "TextureVideoView error. Some operation takes too long to complete, usually more than 3-5 seconds.";
                    }
           if (frameworkError == 1) {
               return "TextureVideoView error. Unspecified media player error.";
                    }
           if (frameworkError == -1010) {
               return "TextureVideoView error. Bitstream is conforming to the related coding standard or file spec, but the media framework does not support the feature.";
                    }
           if (frameworkError == 200) {
               return "The video is streamed and its container is not valid for progressive playback i.e the video's index (e.g moov atom) is not at the start of the file.";
                    }
           return "Unknown Error";
                }

                @Override 
       public void setKeepScreenOn(boolean keepScreenOn) {
           if (Looper.getMainLooper() == Looper.myLooper()) {
               super.setKeepScreenOn(keepScreenOn);
                    } else {
               post(new RunnableC04094(keepScreenOn));
                    }
                }

                
       class RunnableC04094 implements Runnable {
                    final  boolean val$keepScreenOn;

           RunnableC04094(boolean z) {
                        this.val$keepScreenOn = z;
                    }

                    @Override 
           public void run() {
               SdkVideoView.access$1001(SdkVideoView.this, this.val$keepScreenOn);
                    }
                }

       public void setAutoShow(boolean autoShow) {
           this.mAutoShow = autoShow;
                }

       public void setMediaController(MediaController controller) {
           if (this.mMediaController != null) {
               this.mMediaController.hide();
                    }
           this.mMediaController = controller;
           attachMediaController();
                }

       private void attachMediaController() {
           if (this.mMediaController != null && hasPlayer()) {
               this.mMediaController.setMediaPlayer(this);
               View anchorView = getParent() instanceof View ? (View) getParent() : this;
               this.mMediaController.setAnchorView(anchorView);
               this.mMediaController.setEnabled(isInPlaybackState());
                    }
                }

       public void setOnVideoSizeChangedListener(MediaPlayer.OnVideoSizeChangedListener listener) {
           this.mCustomOnSizeChangedListener = listener;
                }

                
       class C04105 implements MediaPlayer.OnVideoSizeChangedListener {
           C04105() {
                    }

                    @Override 
           public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {
                        try {
                   int w = mp.getVideoWidth();
                   int h = mp.getVideoHeight();
                   SdkVideoView.access$500(SdkVideoView.this, w, h);
                   if (SdkVideoView.access$1100(SdkVideoView.this) != null) {
                       SdkVideoView.access$1100(SdkVideoView.this).onVideoSizeChanged(mp, w, h);
                            }
                        } catch (IllegalStateException e) {
                        }
                    }
                }

                
       class RunnableC04116 implements Runnable {
           RunnableC04116() {
                    }

                    @Override 
           public void run() {
               if (SdkVideoView.access$800(SdkVideoView.this) != null) {
                   SdkVideoView.access$800(SdkVideoView.this).onPrepared(SdkVideoView.this);
                        }
                    }
                }

                
       class C04127 implements MediaPlayer.OnPreparedListener {
           C04127() {
                    }

                    @Override 
           public void onPrepared(MediaPlayer mp) {
               SLog.m442b("Prepare took {} ms", Long.valueOf(System.currentTimeMillis() - SdkVideoView.access$1200(SdkVideoView.this)));
                        try {
                   mp.seekTo(0);
                        } catch (Exception e) {
                        }
               SdkVideoView.access$700(SdkVideoView.this, 4);
               SdkVideoView.access$1302(SdkVideoView.this, true);
               if (SdkVideoView.access$1400(SdkVideoView.this)) {
                   SdkVideoView.access$1500(SdkVideoView.this, 2);
                        }
                        try {
                   SdkVideoView.access$500(SdkVideoView.this, mp.getVideoWidth(), mp.getVideoHeight());
                        } catch (IllegalStateException e2) {
                   SLog.m455e("IllegalState happened. ");
                        }
               SdkVideoView.access$1600(SdkVideoView.this);
               SdkVideoView.access$1800(SdkVideoView.this).post(SdkVideoView.access$1700(SdkVideoView.this));
                    }
                }

       private void checkForFrameDrops(String op, long start) {
           if (SLog.sLogsOn && Looper.getMainLooper() == Looper.myLooper()) {
               long time = System.currentTimeMillis() - start;
                        if (time > OPEN_ON_MAIN_THREAD_THRESHOLD) {
                   SLog.m479w("Frame drops caused by {}(): {}", op, Long.valueOf(time));
                        }
                    }
                }

       private void onPlayerPrepared() {
           if (this.mMediaController != null) {
               this.mMediaController.setEnabled(true);
                    }
           int seekToPosition = this.mSeekWhenPrepared;
           if (seekToPosition != 0) {
               mediaOpSeekTo(seekToPosition);
                    }
           if (this.mVideoWidth != 0 && this.mVideoHeight != 0) {
               if (this.mSurfaceWidth == this.mVideoWidth && this.mSurfaceHeight == this.mVideoHeight) {
                   if (this.mTargetState == 6) {
                       start();
                       if (this.mMediaController != null) {
                           this.mMediaController.show();
                           return;
                                }
                       return;
                            }
                   if (!isPlaying()) {
                       if ((seekToPosition != 0 || getCurrentPosition() > 0) && this.mMediaController != null) {
                           this.mMediaController.show(0);
                           return;
                                }
                       return;
                            }
                   return;
                        }
               return;
                    }
           if (this.mTargetState == 6) {
               start();
                    }
                }

                
       class C04138 implements MediaPlayer.OnCompletionListener {
           C04138() {
                    }

                    @Override 
           public void onCompletion(MediaPlayer mp) {
               SdkVideoView.access$600(SdkVideoView.this);
                    }
                }

       private void onComplete() {
           setCurrentState(5);
           this.mTargetState = 5;
           if (this.mMediaController != null) {
               this.mMediaController.hide();
                    }
           if (this.mOnCompletionListener != null) {
               this.mOnCompletionListener.onCompletion(this);
                    }
                }

                
       class C04149 implements MediaPlayer.OnErrorListener {
           C04149() {
                    }

                    @Override 
           public boolean onError(MediaPlayer mp, int framework_err, int impl_err) {
               SdkVideoView.access$300(SdkVideoView.this, framework_err, impl_err);
               return true;
                    }
                }

       private boolean onError(int framework_err, int impl_err) {
           SLog.m453d("Error: {}, {}, {}, {}.", new Object[]{Integer.valueOf(framework_err), Integer.valueOf(impl_err), this, this.mPath});
           SLog.m447d("Framework Error: {}", getErrorMessageFromFrameworkError(framework_err));
           setCurrentState(-1);
           this.mTargetState = -1;
           this.mPath = null;
           if (this.mMediaController != null) {
               this.mMediaController.hide();
                    }
                    return this.mOnErrorListener != null && this.mOnErrorListener.onError(this, framework_err, impl_err);
                }

                
       class C040110 implements MediaPlayer.OnBufferingUpdateListener {
           C040110() {
                    }

                    @Override 
           public void onBufferingUpdate(MediaPlayer mp, int percent) {
               SdkVideoView.access$1902(SdkVideoView.this, percent);
                    }
                }

                
       class C040211 implements MediaPlayer.OnSeekCompleteListener {
           C040211() {
                    }

                    @Override 
           public void onSeekComplete(MediaPlayer mp) {
               SdkVideoView.access$2002(SdkVideoView.this, false);
               if (SdkVideoView.access$2100(SdkVideoView.this) >= 0) {
                   int seek = SdkVideoView.access$2100(SdkVideoView.this);
                   SdkVideoView.access$2102(SdkVideoView.this, -1);
                   mp.seekTo(seek);
                        }
                    }
                }

                @Override 
       public void setOnPreparedListener(VideoViewInterface.OnPreparedListener l) {
           this.mOnPreparedListener = l;
                }

                @Override 
       public void setCacheKey(CacheKey cacheKey) {
           this.mCacheKey = cacheKey;
                }

                @Override 
       public CacheKey getCacheKey() {
           return this.mCacheKey;
                }

                @Override 
       public void setAutoPlayOnPrepared(boolean autoPlay) {
           this.mAutoPlayOnPrepared = autoPlay;
                }

                @Override 
       public void setOnCompletionListener(VideoViewInterface.OnCompletionListener l) {
           this.mOnCompletionListener = l;
                }

       public VideoViewInterface.OnCompletionListener getOnCompletionListener() {
           return this.mOnCompletionListener;
                }

                @Override 
       public void setOnErrorListener(VideoViewInterface.OnErrorListener l) {
           this.mOnErrorListener = l;
                }

       public void setOnInfoListener(MediaPlayer.OnInfoListener l) {
           this.mOnInfoListener = l;
                }

       public void setReportOpenError(boolean reportError) {
           this.mReportOpenError = reportError;
                }

       public void setSurfaceReadyListener(SurfaceReadyListener listener) {
           this.mSurfaceReadyListener = listener;
                }

       public void setSurfaceUpdatedListener(SurfaceUpdatedListener listener) {
           this.mSurfaceUpdatedListener = listener;
                }

                
       class TextureViewSurfaceTextureListenerC040312 implements TextureView.SurfaceTextureListener {
           TextureViewSurfaceTextureListenerC040312() {
                    }

                    @Override 
           public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
               SLog.m464i("Surface is ready.");
               if (SdkVideoView.access$2200(SdkVideoView.this) == null) {
                   SdkVideoView.access$2300(SdkVideoView.this);
                        } else {
                   SdkVideoView.access$2200(SdkVideoView.this).onSurfaceTextureAvailable(SdkVideoView.this);
                        }
                    }

                    @Override 
           public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
               SdkVideoView.access$2402(SdkVideoView.this, width);
               SdkVideoView.access$2502(SdkVideoView.this, height);
               boolean isValidState = SdkVideoView.access$2600(SdkVideoView.this) == 6;
               boolean hasValidSize = SdkVideoView.this.mVideoWidth == width && SdkVideoView.this.mVideoHeight == height;
               if (SdkVideoView.access$2700(SdkVideoView.this) && isValidState && hasValidSize) {
                   if (SdkVideoView.access$2800(SdkVideoView.this) != 0) {
                       SdkVideoView.this.seekTo(SdkVideoView.access$2800(SdkVideoView.this));
                            }
                   SdkVideoView.this.start();
                        }
                    }

                    @Override 
           public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
               if (SdkVideoView.access$2900(SdkVideoView.this) != null) {
                            SdkVideoView.access$2900(SdkVideoView.this).hide();
                        }
               if (SdkVideoView.access$3000(SdkVideoView.this) != null) {
                   SdkVideoView.access$3100(SdkVideoView.this, "surface destroyed", true, false);
                        } else {
                   SdkVideoView.access$3100(SdkVideoView.this, "surface destroyed", true, true);
                        }
                        return true;
                    }

                    @Override 
           public void onSurfaceTextureUpdated(SurfaceTexture surface) {
               if (SdkVideoView.access$3200(SdkVideoView.this) != null) {
                   SdkVideoView.access$3200(SdkVideoView.this).onSurfaceUpdated();
                        }
                    }
                }

       public static void releaseStaticIfNeeded() {
                    synchronized (LOCK) {
                        try {
                            try {
                       if (singlePlayer != null) {
                           singlePlayer.release();
                           singlePlayer = null;
                                }
                            } catch (Exception e) {
                       SLog.m459e("Failed to release static player.", (Throwable) e);
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }

       private void setPlayer(MediaPlayer player) {
           if (this.SINGLE_PLAYER) {
               singlePlayer = player;
                    } else {
               this.mMediaPlayer = player;
                    }
                }

                @Override 
       public void stopPlayback() {
           if (this.SINGLE_PLAYER) {
                        synchronized (LOCK) {
                            try {
                       release("stop playback", true, false);
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
               return;
                    }
           release("stop playback", true, false);
                }

       private void onVideoSizeChanged(int width, int height) {
           if (this.mVideoHeight != height || this.mVideoWidth != width) {
               this.mVideoWidth = width;
               this.mVideoHeight = height;
               if (this.mVideoWidth != 0 && this.mVideoHeight != 0) {
                   this.mUiOpsHandler.post(new RunnableC040413());
                        }
                    }
                }

                
       class RunnableC040413 implements Runnable {
           RunnableC040413() {
                    }

                    @Override 
           public void run() {
               SdkVideoView.this.requestLayout();
                    }
                }

       private void release(String tag, boolean cleartargetstate, boolean runNow) {
           this.mReleaseTag = tag;
           this.mShouldClearState = cleartargetstate;
           if (runNow) {
               mediaOpRelease();
                    } else {
               removePendingMediaOps();
               postMediaOp(0);
                    }
           setKeepScreenOn(false);
                }

                @Override 
       public boolean onTouchEvent(@NonNull MotionEvent ev) {
           if (isInPlaybackState() && this.mMediaController != null) {
               toggleMediaControlsVisibility();
               return false;
                    }
           return false;
                }

                @Override 
       public boolean onTrackballEvent(MotionEvent ev) {
           if (isInPlaybackState() && this.mMediaController != null) {
               toggleMediaControlsVisibility();
               return false;
                    }
           return false;
                }

       public boolean isPlayerPlaying() {
                    boolean r = true;
           long start = System.currentTimeMillis();
           if (this.mUseVinePlayer) {
               if (this.mVinePlayer == null || !this.mVinePlayer.isPlaying()) {
                            r = false;
                        }
                    } else if (getPlayer() == null || !getPlayer().isPlaying()) {
                        r = false;
                    }
           checkForFrameDrops("isPlayerPlaying", start);
           return r;
                }

                @Override 
       public boolean onKeyDown(int keyCode, @NonNull KeyEvent event) {
           boolean isKeyCodeSupported = (keyCode == 4 || keyCode == 24 || keyCode == 25 || keyCode == 164 || keyCode == 82 || keyCode == 5 || keyCode == 6) ? false : true;
           if (isInPlaybackState() && isKeyCodeSupported && this.mMediaController != null) {
               if (keyCode == 79 || keyCode == 85) {
                   if (getPlayer().isPlaying()) {
                       pause();
                       this.mMediaController.show();
                                return true;
                            }
                   start();
                   this.mMediaController.hide();
                            return true;
                        }
               if (keyCode == 126) {
                   if (getPlayer().isPlaying()) {
                                return true;
                            }
                   start();
                   this.mMediaController.hide();
                            return true;
                        }
               if (keyCode == 86 || keyCode == 127) {
                   if (!getPlayer().isPlaying()) {
                                return true;
                            }
                   pause();
                   this.mMediaController.show();
                            return true;
                        }
               toggleMediaControlsVisibility();
                    }
           return super.onKeyDown(keyCode, event);
                }

      private void toggleMediaControlsVisibility() {
          if (this.mMediaController.isShowing()) {
              this.mMediaController.hide();
                    } else {
              this.mMediaController.show();
                    }
                }

                @Override 
      public void start() {
          start(true);
                }

      private void mediaOpStart() {
                    synchronized (LOCK) {
                        try {
                  if (isInPlaybackState()) {
                      long start = System.currentTimeMillis();
                      if (this.mUseVinePlayer) {
                          VinePlayer vinePlayer = this.mVinePlayer;
                          if (vinePlayer != null) {
                              vinePlayer.resume();
                                    }
                                } else {
                                    try {
                              MediaPlayer player = getPlayer();
                              if (player != null) {
                                  player.start();
                                        }
                                    } catch (IllegalStateException e) {
                              CrashUtil.logOrThrowInDebug(e);
                                    }
                                }
                      checkForFrameDrops("start", start);
                      setCurrentState(6);
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
          if (this.mEnsureVisibilityWhenStart && getVisibility() != 0) {
              this.mUiOpsHandler.postDelayed(this.mVisibileStartRunnable, 40L);
                    }
          this.mTargetState = 6;
                }

      public void start(boolean ensureVisibility) {
          this.mEnsureVisibilityWhenStart = ensureVisibility;
          postMediaOp(2);
                }

                
      class RunnableC040514 implements Runnable {
          RunnableC040514() {
                    }

                    @Override 
          public void run() {
              SdkVideoView.this.setVisibility(0);
                    }
                }

                @Override 
      public void setVisibility(int visibility) {
          super.setVisibility(visibility);
          this.mUiOpsHandler.removeCallbacks(this.mVisibileStartRunnable);
                }

                @Override 
      public void pause() {
          if (isInPlaybackState()) {
              if (this.mUseVinePlayer) {
                  this.mVinePlayer.pause();
                  if (this.mVinePlayer.isPaused()) {
                      setCurrentState(7);
                            }
                        } else {
                  long start = System.currentTimeMillis();
                  if (getPlayer().isPlaying()) {
                      getPlayer().pause();
                      onPause();
                      setCurrentState(7);
                            }
                  checkForFrameDrops("pause", start);
                        }
                    }
          this.mTargetState = 7;
                }

                @Override 
      public void suspend() {
          release("suspend", false, !this.mUseVinePlayer);
                }

                @Override 
      public boolean hasStarted() {
          return isInPlaybackState();
                }

                @Override 
      public boolean isPaused() {
                    return getCurrentState() == 7;
                }

                @Override 
      public void setLooping(boolean looping) {
          this.mLooping = looping;
                }

                @Override 
      public void resume() {
          this.mWasMuted = !this.mMuted;
          setMute(this.mMuted);
          start();
                }

                @Override 
      public int getDuration() {
                    int duration = -1;
                    try {
              if (isInPlaybackState()) {
                  if (this.mVinePlayer != null) {
                      duration = this.mVinePlayer.getDuration();
                            } else {
                      MediaPlayer player = getPlayer();
                      if (player != null) {
                          duration = player.getDuration();
                                }
                            }
                        }
                    } catch (Exception e) {
              CrashUtil.logException(e, "It's ok. probably a race condition.", new Object[0]);
                    }
          return duration;
                }

                
                @Override 
      public int getCurrentPosition() {
                    int currentPosition;
                    try {
              if (isInPlaybackState()) {
                  if (this.mVinePlayer != null) {
                      currentPosition = this.mVinePlayer.getCurrentPosition();
                            } else if (getPlayer() != null) {
                      currentPosition = getPlayer().getCurrentPosition();
                            } else {
                      currentPosition = 0;
                            }
                        } else {
                  currentPosition = 0;
                        }
              return currentPosition;
                    } catch (Exception e) {
              return -1;
                    }
                }

                @Override 
      public void seekTo(int msec) {
          postMediaOp(msec + 1000);
                }

      public void mediaOpSeekTo(int msec) {
          if (isInPlaybackState()) {
              if (!this.mIsSeeking) {
                  this.mIsSeeking = true;
                  MediaPlayer player = getPlayer();
                  if (player != null) {
                      player.seekTo(msec);
                            }
                  if (this.mUseVinePlayer) {
                      Log.w(TAG, "Seek does not work with VinePlayer.");
                            }
                        } else {
                  this.mNextSeek = msec;
                        }
              this.mSeekWhenPrepared = 0;
              return;
                    }
          this.mSeekWhenPrepared = msec;
                }

                @Override 
      public boolean isPlaying() {
                    try {
                        return isInPlaybackState() && isPlayerPlaying();
                    } catch (Exception e) {
                        return false;
                    }
                }

                @Override 
      public int getBufferPercentage() {
          if (hasPlayer()) {
              return this.mCurrentBufferPercentage;
                    }
                    return 0;
                }

      public boolean hasControlOfPlayer() {
                    return !this.SINGLE_PLAYER || (currentView == this && currentUri == this.mUri);
                }

                @Override 
      public boolean isInPlaybackState() {
                    return (!hasPlayer() || getCurrentState() == -1 || getCurrentState() == 1 || getCurrentState() == 2) ? false : true;
                }

                @Override 
      public boolean canPause() {
          return this.mIsPrepared;
                }

                @Override 
      public boolean canSeekBackward() {
          return this.mIsPrepared;
                }

                @Override 
      public boolean canSeekForward() {
          return this.mIsPrepared;
                }

                @Override 
      public int getAudioSessionId() {
          if (this.mAudioSession == 0) {
              MediaPlayer foo = new MediaPlayer();
              this.mAudioSession = foo.getAudioSessionId();
              foo.release();
                    }
          return this.mAudioSession;
                }

      public AttributeSet getAttributes() {
          return this.mAttributes;
                }

      private void postMediaOp(int what) {
          if (this.mUseVinePlayer || sRunMediaOpsOnSeparateThread) {
              Handler handler = this.mMediaOpHandler;
              if (handler != null) {
                  handler.sendEmptyMessage(what);
                  return;
                        }
              return;
                    }
          runMediaOps(what);
          if (what == 2) {
              onPlay();
                    }
                }

      private void removePendingMediaOps() {
          Handler handler = this.mMediaOpHandler;
          this.mIsSeeking = false;
          if (handler != null) {
              handler.removeCallbacksAndMessages(null);
                    }
                }

                @Override 
      protected void onDetachedFromWindow() {
          this.mUiOpsHandler.removeCallbacks(this.mVisibileStartRunnable);
          release("onDetachedFromWindow", true, false);
                    try {
              super.onDetachedFromWindow();
                    } catch (Exception e) {
              SurfaceTexture tx = null;
              boolean success = true;
                        try {
                  tx = getSurfaceTexture();
                  if (tx != null) {
                      tx.release();
                            }
                        } catch (Exception e2) {
                  success = false;
                  CrashUtil.log("Failed to release: {}.", e2);
                        }
              CrashUtil.logException(e, "Failed to detach from window, but it's ok, since we won't use this anyways: {}, {}.", Boolean.valueOf(success), tx);
                    }
          this.mMediaOpHandler = null;
          if (this.mMediaOpThread != null) {
              this.mMediaOpThread.quit();
              this.mMediaOpThread = null;
                    }
                }

      public void setMute(boolean mute) {
          this.mMuted = mute;
          postMediaOp(this.mMuted ? 3 : 4);
                }

      private void mediaOpChangeVolume(float volume) {
                    synchronized (LOCK) {
                        try {
                  if (isInPlaybackState() && this.mWasMuted != this.mMuted) {
                      if (this.mVinePlayer != null) {
                          this.mVinePlayer.setVolume(volume);
                                } else {
                          MediaPlayer player = getPlayer();
                          if (player != null) {
                                        try {
                                  player.setVolume(volume, volume);
                                        } catch (IllegalStateException e) {
                                  CrashUtil.logException(e);
                                        }
                                    }
                                }
                      this.mWasMuted = this.mMuted;
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }

      public boolean isPathPlaying(String newPath) {
                    return hasControlOfPlayer() && newPath.equals(this.mPath);
                }

      public static Uri getCurrentUri() {
          return currentUri;
                }

      private void mediaOpOpen() {
                    Handler handler;
                    synchronized (LOCK) {
                        try {
                  currentView = this;
                  if (this.mUri == null) {
                      Log.d(TAG, "mUri  is not ready yet.");
                      return;
                            }
                  SurfaceTexture texture = null;
                  if (isAvailable()) {
                      texture = getSurfaceTexture();
                            }
                  if (texture == null) {
                      boolean show = this.mAutoShow && getVisibility() != 0;
                      Log.d(TAG, "Surface is not ready yet, will autoshow ? " + this.mAutoShow + " & " + getVisibility());
                      if (show && (handler = getHandler()) != null) {
                          handler.post(new RunnableC040615());
                                }
                      return;
                            }
                  Surface surface = new Surface(texture);
                  Log.d(TAG, "Opening video: " + this.mUri);
                  Intent i = new Intent("com.android.music.musicservicecommand");
                  i.putExtra("command", "pause");
                  this.mContext.sendBroadcast(i);
                  release("open", false, true);
                  if (this.mUseVinePlayer) {
                      openVinePlayer(surface);
                            } else {
                      openMediaPlayer(surface);
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }

                
      class RunnableC040615 implements Runnable {
          RunnableC040615() {
                    }

                    @Override 
          public void run() {
              SdkVideoView.this.setVisibility(0);
                    }
                }

      private void mediaOpRelease() {
          MediaPlayer player = getPlayer();
          if (player != null) {
                        try {
                  long start = System.currentTimeMillis();
                  if (player.isPlaying()) {
                      player.stop();
                      onPause();
                            }
                  checkForFrameDrops(this.mReleaseTag + " stop", start);
                        } catch (IllegalStateException e) {
                        }
                        try {
                  long start2 = System.currentTimeMillis();
                  player.reset();
                  checkForFrameDrops(this.mReleaseTag + " reset", start2);
                        } catch (IllegalStateException e2) {
                        }
              if (!this.SINGLE_PLAYER) {
                  long start3 = System.currentTimeMillis();
                  player.release();
                  setPlayer(null);
                  checkForFrameDrops(this.mReleaseTag + " release", start3);
                        }
              this.mIsPrepared = false;
              setCurrentState(1);
              if (this.mShouldClearState) {
                  this.mTargetState = 1;
                  this.mPath = null;
                        }
                    }
          VinePlayer vp = this.mVinePlayer;
          if (vp != null) {
                        try {
                  vp.release();
                  this.mVinePlayer = null;
                        } catch (IllegalAccessException e3) {
                  throw new RuntimeException(e3);
                        }
                    }
                }

      private void runMediaOps(int what) {
          if (what >= 1000) {
              mediaOpSeekTo(what - 1000);
                    }
                    switch (what) {
                        case 0:
                  mediaOpRelease();
                            break;
                        case 1:
                  mediaOpOpen();
                            break;
                        case 2:
                  mediaOpStart();
                            break;
                        case 3:
                  mediaOpChangeVolume(0.0f);
                            break;
                        case 4:
                  mediaOpChangeVolume(1.0f);
                            break;
                    }
                }

      private static class PlayerMaker implements Runnable {
                    private MediaPlayer mMp;

          private PlayerMaker() {
                    }

           PlayerMaker(RunnableC04001 x0) {
              this();
                    }

          synchronized MediaPlayer getPlayer() {
                        try {
                        } catch (Throwable th) {
                            throw th;
                        }
                        return this.mMp;
                    }

                    @Override 
          public void run() {
              this.mMp = new MediaPlayer();
                    }

          synchronized boolean isReady() {
                        try {
                        } catch (Throwable th) {
                            throw th;
                        }
                        return getPlayer() != null;
                    }
                }
            }
