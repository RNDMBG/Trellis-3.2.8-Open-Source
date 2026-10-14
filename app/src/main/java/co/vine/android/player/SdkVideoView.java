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
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            @TargetApi(14)
/* 43 */    public class SdkVideoView extends TextureView implements MediaController.MediaPlayerControl, VideoViewInterface {
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

/* 44 */        static /* synthetic */ MediaPlayer access$000(SdkVideoView x0) {
/* 45 */            return x0.getPlayer();
                }

/* 44 */        static /* synthetic */ MediaPlayer.OnErrorListener access$100(SdkVideoView x0) {
/* 45 */            return x0.mErrorListener;
                }

/* 44 */        static /* synthetic */ void access$1001(SdkVideoView x0, boolean x1) {
/* 45 */            x0.setKeepScreenOn(x1);
                }

/* 44 */        static /* synthetic */ MediaPlayer.OnVideoSizeChangedListener access$1100(SdkVideoView x0) {
/* 45 */            return x0.mCustomOnSizeChangedListener;
                }

/* 44 */        static /* synthetic */ long access$1200(SdkVideoView x0) {
/* 45 */            return x0.mPrepareStart;
                }

/* 44 */        static /* synthetic */ boolean access$1302(SdkVideoView x0, boolean x1) {
/* 45 */            x0.mIsPrepared = x1;
                    return x1;
                }

/* 44 */        static /* synthetic */ boolean access$1400(SdkVideoView x0) {
/* 45 */            return x0.mAutoPlayOnPrepared;
                }

/* 44 */        static /* synthetic */ void access$1500(SdkVideoView x0, int x1) {
/* 45 */            x0.postMediaOp(x1);
                }

/* 44 */        static /* synthetic */ void access$1600(SdkVideoView x0) {
/* 45 */            x0.onPlayerPrepared();
                }

/* 44 */        static /* synthetic */ Runnable access$1700(SdkVideoView x0) {
/* 45 */            return x0.mPrepareNotifyRunnable;
                }

/* 44 */        static /* synthetic */ Handler access$1800(SdkVideoView x0) {
/* 45 */            return x0.mUiOpsHandler;
                }

/* 44 */        static /* synthetic */ int access$1902(SdkVideoView x0, int x1) {
/* 45 */            x0.mCurrentBufferPercentage = x1;
                    return x1;
                }

/* 44 */        static /* synthetic */ void access$200(SdkVideoView x0, int x1) {
/* 45 */            x0.runMediaOps(x1);
                }

/* 44 */        static /* synthetic */ boolean access$2002(SdkVideoView x0, boolean x1) {
/* 45 */            x0.mIsSeeking = x1;
                    return x1;
                }

/* 44 */        static /* synthetic */ int access$2100(SdkVideoView x0) {
/* 45 */            return x0.mNextSeek;
                }

/* 44 */        static /* synthetic */ int access$2102(SdkVideoView x0, int x1) {
/* 45 */            x0.mNextSeek = x1;
                    return x1;
                }

/* 44 */        static /* synthetic */ SurfaceReadyListener access$2200(SdkVideoView x0) {
/* 45 */            return x0.mSurfaceReadyListener;
                }

/* 44 */        static /* synthetic */ void access$2300(SdkVideoView x0) {
/* 45 */            x0.openVideo();
                }

/* 44 */        static /* synthetic */ int access$2402(SdkVideoView x0, int x1) {
/* 45 */            x0.mSurfaceWidth = x1;
                    return x1;
                }

/* 44 */        static /* synthetic */ int access$2502(SdkVideoView x0, int x1) {
/* 45 */            x0.mSurfaceHeight = x1;
                    return x1;
                }

/* 44 */        static /* synthetic */ int access$2600(SdkVideoView x0) {
/* 45 */            return x0.mTargetState;
                }

/* 44 */        static /* synthetic */ boolean access$2700(SdkVideoView x0) {
/* 45 */            return x0.hasPlayer();
                }

/* 44 */        static /* synthetic */ int access$2800(SdkVideoView x0) {
/* 45 */            return x0.mSeekWhenPrepared;
                }

/* 44 */        static /* synthetic */ MediaController access$2900(SdkVideoView x0) {
/* 45 */            return x0.mMediaController;
                }

/* 44 */        static /* synthetic */ boolean access$300(SdkVideoView x0, int x1, int x2) {
/* 45 */            return x0.onError(x1, x2);
                }

/* 44 */        static /* synthetic */ VinePlayer access$3000(SdkVideoView x0) {
/* 45 */            return x0.mVinePlayer;
                }

/* 44 */        static /* synthetic */ void access$3100(SdkVideoView x0, String x1, boolean x2, boolean x3) {
/* 45 */            x0.release(x1, x2, x3);
                }

/* 44 */        static /* synthetic */ SurfaceUpdatedListener access$3200(SdkVideoView x0) {
/* 45 */            return x0.mSurfaceUpdatedListener;
                }

/* 44 */        static /* synthetic */ VinePlayer.EventListener access$400(SdkVideoView x0) {
/* 45 */            return x0.mVinePlayerListener;
                }

/* 44 */        static /* synthetic */ void access$500(SdkVideoView x0, int x1, int x2) {
/* 45 */            x0.onVideoSizeChanged(x1, x2);
                }

/* 44 */        static /* synthetic */ void access$600(SdkVideoView x0) {
/* 45 */            x0.onComplete();
                }

/* 44 */        static /* synthetic */ void access$700(SdkVideoView x0, int x1) {
/* 45 */            x0.setCurrentState(x1);
                }

/* 44 */        static /* synthetic */ VideoViewInterface.OnPreparedListener access$800(SdkVideoView x0) {
/* 45 */            return x0.mOnPreparedListener;
                }

                static {
/* 47 */            IS_DEFECTIVE_ON_THREADED_MEDIA_PLAYERS = Build.VERSION.SDK_INT < 21;
/* 50 */            TAG = SdkVideoView.class.getSimpleName();
/* 56 */            sGloablUseVinePlayer = false;
/* 58 */            DISABLE_OPEN_ON_SEPARATE_THREAD = IS_DEFECTIVE_ON_THREADED_MEDIA_PLAYERS;
/* 60 */            sRunMediaOpsOnSeparateThread = !IS_DEFECTIVE_ON_THREADED_MEDIA_PLAYERS;
/* 62 */            OPEN_ON_MAIN_THREAD = false;
/* 155 */           singlePlayer = null;
/* 158 */           currentState = 1;
/* 159 */           LOCK = new int[0];
                }

/* 77 */        private static synchronized void onPlay() {
                    try {
                        sPlayingCount++;
/* 79 */                if (sPlayingCount > 1) {
/* 80 */                    SLog.m455e("Playing count > 1");
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }

/* 84 */        private static synchronized void onPause() {
                    try {
                        sPlayingCount--;
                    } catch (Throwable th) {
                        throw th;
                    }
                }

/* 88 */        public static void setRunMediaOpsOnSeparateThread(boolean enabled) {
/* 89 */            sRunMediaOpsOnSeparateThread = enabled;
                }

/* 95 */        public static void setGlobalUseVinePlayer(boolean useVinePlayer) {
/* 96 */            sGloablUseVinePlayer = useVinePlayer && Build.VERSION.SDK_INT >= 16;
                }

/* 99 */        public static boolean getGlobalUseVinePlayer() {
/* 100 */           return false;
                }

/* 103 */       public static boolean getDefaultGlobalVinePlayer() {
/* 104 */           return false;
                }

                /* JADX INFO: renamed from: co.vine.android.player.SdkVideoView$1 */
/* 188 */       class RunnableC04001 implements Runnable {
/* 189 */           RunnableC04001() {
                    }

                    @Override // java.lang.Runnable
/* 192 */           public void run() {
/* 193 */               SdkVideoView.access$100(SdkVideoView.this).onError(SdkVideoView.access$000(SdkVideoView.this), 1, 0);
                    }
                }

/* 197 */       private MediaPlayer getPlayer() {
                    return this.SINGLE_PLAYER ? singlePlayer : this.mMediaPlayer;
                }

/* 201 */       private boolean hasPlayer() {
                    return (getPlayer() == null && this.mVinePlayer == null) ? false : true;
                }

/* 205 */       public int getCurrentState() {
                    return this.SINGLE_PLAYER ? currentState : this.mCurrentState;
                }

/* 209 */       private void setCurrentState(int state) {
/* 210 */           if (this.SINGLE_PLAYER) {
/* 211 */               currentState = state;
                    } else {
/* 213 */               this.mCurrentState = state;
                    }
                }

/* 217 */       public SdkVideoView(Context context) {
/* 218 */           super(context);
/* 116 */           this.mMediaPlayer = null;
/* 166 */           this.mCurrentState = 1;
/* 167 */           this.mTargetState = 1;
/* 175 */           this.mPlayingPosition = -1;
/* 177 */           this.mMuted = false;
/* 178 */           this.mWasMuted = false;
/* 179 */           this.mAudioSession = 0;
/* 183 */           this.mSaveVideoClicker = new SaveVideoClicker();
/* 184 */           this.mUiOpsHandler = new Handler(Looper.getMainLooper());
/* 190 */           this.mOnOpenErrorListener = new RunnableC04001();
/* 583 */           this.mSizeChangedListener = new C04105();
/* 599 */           this.mPrepareNotifyRunnable = new RunnableC04116();
/* 608 */           this.mPreparedListener = new C04127();
/* 678 */           this.mCompletionListener = new C04138();
/* 696 */           this.mErrorListener = new C04149();
/* 722 */           this.mBufferingUpdateListener = new C040110();
/* 729 */           this.mOnSeekCompleteListener = new C040211();
/* 820 */           this.mSHCallback = new TextureViewSurfaceTextureListenerC040312();
/* 911 */           this.mReleaseTag = "";
/* 1050 */          this.mVisibileStartRunnable = new RunnableC040514();
/* 219 */           this.SINGLE_PLAYER = SystemUtil.isSinglePlayerEnabled(context);
/* 220 */           this.mUseVinePlayer = getGlobalUseVinePlayer();
/* 221 */           initVideoView();
                }

/* 224 */       public SdkVideoView(Context context, AttributeSet attrs) {
/* 225 */           super(context, attrs);
/* 116 */           this.mMediaPlayer = null;
/* 166 */           this.mCurrentState = 1;
/* 167 */           this.mTargetState = 1;
/* 175 */           this.mPlayingPosition = -1;
/* 177 */           this.mMuted = false;
/* 178 */           this.mWasMuted = false;
/* 179 */           this.mAudioSession = 0;
/* 183 */           this.mSaveVideoClicker = new SaveVideoClicker();
/* 184 */           this.mUiOpsHandler = new Handler(Looper.getMainLooper());
/* 190 */           this.mOnOpenErrorListener = new RunnableC04001();
/* 583 */           this.mSizeChangedListener = new C04105();
/* 599 */           this.mPrepareNotifyRunnable = new RunnableC04116();
/* 608 */           this.mPreparedListener = new C04127();
/* 678 */           this.mCompletionListener = new C04138();
/* 696 */           this.mErrorListener = new C04149();
/* 722 */           this.mBufferingUpdateListener = new C040110();
/* 729 */           this.mOnSeekCompleteListener = new C040211();
/* 820 */           this.mSHCallback = new TextureViewSurfaceTextureListenerC040312();
/* 911 */           this.mReleaseTag = "";
/* 1050 */          this.mVisibileStartRunnable = new RunnableC040514();
/* 226 */           this.SINGLE_PLAYER = SystemUtil.isSinglePlayerEnabled(context);
/* 227 */           this.mUseVinePlayer = getGlobalUseVinePlayer();
/* 228 */           this.mAttributes = attrs;
/* 229 */           initVideoView();
                }

/* 232 */       public void setUseVinePlayer(boolean useVinePlayer) {
/* 233 */           this.mUseVinePlayer = useVinePlayer;
                }

/* 236 */       public SdkVideoView(Context context, AttributeSet attrs, int defStyle) {
/* 237 */           super(context, attrs, defStyle);
/* 116 */           this.mMediaPlayer = null;
/* 166 */           this.mCurrentState = 1;
/* 167 */           this.mTargetState = 1;
/* 175 */           this.mPlayingPosition = -1;
/* 177 */           this.mMuted = false;
/* 178 */           this.mWasMuted = false;
/* 179 */           this.mAudioSession = 0;
/* 183 */           this.mSaveVideoClicker = new SaveVideoClicker();
/* 184 */           this.mUiOpsHandler = new Handler(Looper.getMainLooper());
/* 190 */           this.mOnOpenErrorListener = new RunnableC04001();
/* 583 */           this.mSizeChangedListener = new C04105();
/* 599 */           this.mPrepareNotifyRunnable = new RunnableC04116();
/* 608 */           this.mPreparedListener = new C04127();
/* 678 */           this.mCompletionListener = new C04138();
/* 696 */           this.mErrorListener = new C04149();
/* 722 */           this.mBufferingUpdateListener = new C040110();
/* 729 */           this.mOnSeekCompleteListener = new C040211();
/* 820 */           this.mSHCallback = new TextureViewSurfaceTextureListenerC040312();
/* 911 */           this.mReleaseTag = "";
/* 1050 */          this.mVisibileStartRunnable = new RunnableC040514();
/* 238 */           this.SINGLE_PLAYER = SystemUtil.isSinglePlayerEnabled(context);
/* 239 */           this.mUseVinePlayer = getGlobalUseVinePlayer();
/* 240 */           this.mAttributes = attrs;
/* 241 */           initVideoView();
                }

/* 244 */       public void onViewClicked() {
/* 245 */           this.mSaveVideoClicker.onClick(this);
                }

                @Override // android.view.View
/* 249 */       protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
/* 250 */           int width = getDefaultSize(this.mVideoWidth, widthMeasureSpec);
/* 251 */           int height = getDefaultSize(this.mVideoHeight, heightMeasureSpec);
/* 252 */           if (this.mVideoWidth > 0 && this.mVideoHeight > 0) {
/* 253 */               if (this.mVideoWidth * height > this.mVideoHeight * width) {
/* 254 */                   height = (this.mVideoHeight * width) / this.mVideoWidth;
                        } else if (this.mVideoWidth * height < this.mVideoHeight * width) {
/* 256 */                   width = (this.mVideoWidth * height) / this.mVideoHeight;
                        }
                    }
/* 259 */           setMeasuredDimension(width, height);
                }

                @Override // co.vine.android.player.VideoViewInterface
/* 262 */       public int getPlayingPosition() {
/* 263 */           return this.mPlayingPosition;
                }

/* 266 */       public void setPlayingPosition(int playingPosition) {
/* 267 */           this.mPlayingPosition = playingPosition;
                }

                @Override // android.view.TextureView, android.view.View
/* 271 */       protected void onAttachedToWindow() {
/* 272 */           super.onAttachedToWindow();
/* 273 */           this.mMediaOpThread = new HandlerThread("MediaOp");
/* 274 */           this.mMediaOpThread.start();
/* 275 */           this.mMediaOpHandler = new HandlerC04072(this.mMediaOpThread.getLooper());
                }

                /* JADX INFO: renamed from: co.vine.android.player.SdkVideoView$2 */
/* 273 */       class HandlerC04072 extends Handler {
/* 274 */           HandlerC04072(Looper x0) {
                        super(x0);
                    }

                    @Override // android.os.Handler
/* 277 */           public void handleMessage(Message msg) {
/* 278 */               SdkVideoView.access$200(SdkVideoView.this, msg.what);
                    }
                }

/* 283 */       private void initVideoView() {
/* 284 */           this.mContext = getContext();
/* 285 */           this.mVideoWidth = 0;
/* 286 */           this.mVideoHeight = 0;
/* 287 */           setSurfaceTextureListener(this.mSHCallback);
/* 288 */           setFocusable(true);
/* 289 */           setFocusableInTouchMode(true);
/* 290 */           requestFocus();
/* 291 */           this.mTargetState = 1;
                }

                @Override // android.view.View
/* 295 */       public void onInitializeAccessibilityEvent(@NonNull AccessibilityEvent event) {
/* 296 */           super.onInitializeAccessibilityEvent(event);
/* 297 */           event.setClassName(SdkVideoView.class.getName());
                }

                @Override // android.view.View
/* 301 */       public void onInitializeAccessibilityNodeInfo(@NonNull AccessibilityNodeInfo info) {
/* 302 */           super.onInitializeAccessibilityNodeInfo(info);
/* 303 */           info.setClassName(SdkVideoView.class.getName());
                }

                @Override // co.vine.android.player.VideoViewInterface
/* 307 */       public void setVideoPath(String path) {
/* 308 */           setVideoPathDirect(path);
                }

                @Override // co.vine.android.player.VideoViewInterface
/* 311 */       public void setVideoPathDirect(String path) {
/* 312 */           SLog.m447d("mPath={}.", path);
/* 313 */           this.mPath = path;
/* 314 */           setVideoURI(path != null ? Uri.parse(path) : null);
                }

/* 317 */       public String getPath() {
/* 318 */           return this.mPath;
                }

/* 321 */       public void setVideoURI(Uri uri) {
/* 322 */           setVideoURI(uri, null);
                }

/* 325 */       private void setVideoURI(Uri uri, Map<String, String> headers) {
/* 326 */           this.mUri = uri;
/* 327 */           currentUri = this.mUri;
/* 328 */           this.mHeaders = headers;
/* 329 */           this.mSeekWhenPrepared = 0;
/* 330 */           openVideo();
/* 331 */           requestLayout();
/* 332 */           postInvalidate();
                }

/* 335 */       public boolean retryOpenVideo(boolean forced) {
/* 336 */           SLog.m447d("Retry open video: {}", Boolean.valueOf(!this.mHasNotRetriedBefore));
/* 337 */           if (!forced && this.mHasNotRetriedBefore) {
                        return false;
                    }
/* 338 */           this.mHasNotRetriedBefore = true;
/* 339 */           openVideo();
                    return true;
                }

/* 346 */       public boolean isSurfaceReady() {
/* 347 */           return isAvailable();
                }

/* 350 */       public void startOpenVideo() {
/* 351 */           openVideo();
                }

/* 354 */       private void openVideo() {
/* 355 */           if (OPEN_ON_MAIN_THREAD) {
/* 356 */               mediaOpOpen();
                    } else {
/* 358 */               postMediaOp(1);
                    }
                }

/* 362 */       public void setVinePlayerListener(VinePlayer.EventListener listener) {
/* 363 */           this.mVinePlayerListener = listener;
                }

                /* JADX INFO: renamed from: co.vine.android.player.SdkVideoView$3 */
/* 368 */       class C04083 implements VinePlayer.EventListener {
/* 369 */           C04083() {
                    }

                    @Override // co.vine.android.embed.player.VinePlayer.EventListener
/* 372 */           public void onError(PlayerState state, int operation, Throwable e) {
/* 373 */               SLog.m459e("VinePlayer error", e);
/* 374 */               SdkVideoView.access$300(SdkVideoView.this, operation, state.ordinal());
/* 375 */               if (SdkVideoView.access$400(SdkVideoView.this) != null) {
/* 376 */                   SdkVideoView.access$400(SdkVideoView.this).onError(state, operation, e);
                        }
                    }

                    @Override // co.vine.android.embed.player.VinePlayer.EventListener
/* 381 */           public void onVideoSizeChanged(int width, int height) {
/* 382 */               SdkVideoView.access$500(SdkVideoView.this, width, height);
/* 383 */               if (SdkVideoView.access$400(SdkVideoView.this) != null) {
/* 384 */                   SdkVideoView.access$400(SdkVideoView.this).onVideoSizeChanged(width, height);
                        }
                    }

                    @Override // co.vine.android.embed.player.VinePlayer.EventListener
/* 389 */           public void onLoopCompleted() {
/* 390 */               SdkVideoView.access$600(SdkVideoView.this);
/* 391 */               if (SdkVideoView.access$400(SdkVideoView.this) != null) {
/* 392 */                   SdkVideoView.access$400(SdkVideoView.this).onLoopCompleted();
                        }
                    }

                    @Override // co.vine.android.embed.player.VinePlayer.EventListener
/* 397 */           public void onPrepared() {
/* 398 */               SdkVideoView.access$700(SdkVideoView.this, 4);
/* 399 */               if (SdkVideoView.access$800(SdkVideoView.this) != null) {
/* 400 */                   SdkVideoView.access$800(SdkVideoView.this).onPrepared(SdkVideoView.this);
                        }
/* 402 */               if (SdkVideoView.access$400(SdkVideoView.this) != null) {
/* 403 */                   SdkVideoView.access$400(SdkVideoView.this).onPrepared();
                        }
                    }

                    @Override // co.vine.android.embed.player.VinePlayer.EventListener
/* 408 */           public void onFirstFrameRendererd() {
/* 409 */               if (SdkVideoView.access$400(SdkVideoView.this) != null) {
/* 410 */                   SdkVideoView.access$400(SdkVideoView.this).onFirstFrameRendererd();
                        }
                    }

                    @Override // co.vine.android.embed.player.VinePlayer.EventListener
/* 415 */           public void onPastError(Exception error) {
/* 416 */               if (SdkVideoView.access$400(SdkVideoView.this) != null) {
/* 417 */                   SdkVideoView.access$400(SdkVideoView.this).onPastError(error);
                        }
                    }
                }

                @TargetApi(16)
/* 368 */       private void openVinePlayer(Surface surface) {
                    try {
/* 369 */               this.mVinePlayer = new VinePlayer(this.mContext);
/* 370 */               this.mVinePlayer.setEventListener(new C04083());
/* 421 */               this.mVinePlayer.setSurface(surface);
/* 422 */               this.mVinePlayer.prepareAsync(this.mUri, this.mAutoPlayOnPrepared);
/* 423 */               attachMediaController();
/* 424 */               setCurrentState(2);
                        int i = sTotalVideoViewed + 1;
                        sTotalVideoViewed = i;
/* 425 */               CrashUtil.set("videosViewed", i);
                    } catch (IllegalArgumentException | IllegalStateException | SecurityException ex) {
/* 427 */               if (this.mReportOpenError) {
/* 428 */                   CrashUtil.logException(ex, "Exo Unable to open content {}, {}.", this.mUri, getTag());
                        }
/* 430 */               setCurrentState(-1);
/* 431 */               this.mTargetState = -1;
/* 432 */               onError(1, 0);
                    }
                }

/* 443 */       private void openMediaPlayer(Surface surface) {
/* 444 */           long startTime = System.currentTimeMillis();
/* 445 */           PlayerMaker holder = new PlayerMaker(null);
/* 446 */           if (this.SINGLE_PLAYER && singlePlayer == null) {
/* 448 */               singlePlayer = makePlayerOnMainThread(holder);
                    }
                    try {
/* 452 */               if (!this.SINGLE_PLAYER) {
/* 453 */                   setPlayer(makePlayerOnMainThread(holder));
                        }
/* 455 */               MediaPlayer player = getPlayer();
/* 456 */               if (this.mAudioSession != 0) {
/* 457 */                   player.setAudioSessionId(this.mAudioSession);
                        } else {
/* 459 */                   this.mAudioSession = player.getAudioSessionId();
                        }
/* 461 */               player.setOnPreparedListener(this.mPreparedListener);
/* 462 */               player.setOnVideoSizeChangedListener(this.mSizeChangedListener);
/* 463 */               player.setOnCompletionListener(this.mCompletionListener);
/* 464 */               player.setOnErrorListener(this.mErrorListener);
/* 465 */               player.setOnInfoListener(this.mOnInfoListener);
/* 466 */               player.setOnBufferingUpdateListener(this.mBufferingUpdateListener);
/* 467 */               player.setOnSeekCompleteListener(this.mOnSeekCompleteListener);
/* 468 */               this.mCurrentBufferPercentage = 0;
/* 469 */               player.setDataSource(this.mContext, this.mUri, this.mHeaders);
/* 470 */               player.setSurface(surface);
/* 471 */               if (this.mLooping) {
/* 472 */                   player.setLooping(true);
                        }
/* 474 */               setKeepScreenOn(true);
/* 475 */               player.setAudioStreamType(3);
/* 476 */               if (this.mMuted) {
/* 477 */                   player.setVolume(0.0f, 0.0f);
                        }
/* 479 */               this.mNextSeek = -1;
/* 480 */               player.prepareAsync();
                        int i = sTotalVideoViewed + 1;
                        sTotalVideoViewed = i;
/* 481 */               CrashUtil.set("videosViewed", i);
/* 484 */               setCurrentState(2);
/* 485 */               attachMediaController();
/* 486 */               Log.d(TAG, PREPARE_SUCCESS_LOG);
/* 487 */               this.mPrepareStart = System.currentTimeMillis();
/* 488 */               long openTime = this.mPrepareStart - startTime;
/* 489 */               OPEN_ON_MAIN_THREAD = DISABLE_OPEN_ON_SEPARATE_THREAD || openTime < OPEN_ON_MAIN_THREAD_THRESHOLD;
/* 490 */               SLog.m465i("Open took {}ms", Long.valueOf(openTime));
                    } catch (IOException | IllegalArgumentException | IllegalStateException | SecurityException ex) {
/* 492 */               if (SLog.sLogsOn) {
/* 493 */                   SLog.m458e("Error opening video: {}.", (Object) ex.getMessage(), (Throwable) ex);
                        }
/* 495 */               if (this.mReportOpenError) {
/* 496 */                   CrashUtil.logException(ex, "Unable to open content {}, {}.", this.mUri, getTag());
                        }
/* 498 */               setCurrentState(-1);
/* 499 */               this.mTargetState = -1;
/* 500 */               if (Looper.getMainLooper() != Looper.myLooper()) {
/* 501 */                   this.mUiOpsHandler.post(this.mOnOpenErrorListener);
                        } else {
/* 503 */                   this.mOnOpenErrorListener.run();
                        }
                    }
                }

/* 508 */       private MediaPlayer makePlayerOnMainThread(PlayerMaker holder) {
/* 509 */           if (Looper.myLooper() != Looper.getMainLooper()) {
/* 510 */               this.mUiOpsHandler.postAtFrontOfQueue(holder);
/* 511 */               while (!holder.isReady()) {
                            try {
/* 513 */                       Thread.sleep(1L);
                            } catch (InterruptedException e) {
/* 515 */                       SLog.m464i("Waiting failed for player holder.");
                            }
                        }
                    } else {
/* 519 */               holder.run();
                    }
/* 521 */           return holder.getPlayer();
                }

/* 524 */       private String getErrorMessageFromFrameworkError(int frameworkError) {
/* 525 */           if (frameworkError == -1004) {
/* 526 */               return "File or network related operation errors.";
                    }
/* 527 */           if (frameworkError == -1007) {
/* 528 */               return "Bitstream is not conforming to the related coding standard or file spec.";
                    }
/* 529 */           if (frameworkError == 100) {
/* 530 */               return "TextureVideoView error. Media server died. In this case, the application must release the MediaPlayer object and instantiate a new one.";
                    }
/* 531 */           if (frameworkError == -110) {
/* 532 */               return "TextureVideoView error. Some operation takes too long to complete, usually more than 3-5 seconds.";
                    }
/* 533 */           if (frameworkError == 1) {
/* 534 */               return "TextureVideoView error. Unspecified media player error.";
                    }
/* 535 */           if (frameworkError == -1010) {
/* 536 */               return "TextureVideoView error. Bitstream is conforming to the related coding standard or file spec, but the media framework does not support the feature.";
                    }
/* 537 */           if (frameworkError == 200) {
/* 538 */               return "The video is streamed and its container is not valid for progressive playback i.e the video's index (e.g moov atom) is not at the start of the file.";
                    }
/* 540 */           return "Unknown Error";
                }

                @Override // android.view.View, co.vine.android.player.VideoViewInterface
/* 544 */       public void setKeepScreenOn(boolean keepScreenOn) {
/* 545 */           if (Looper.getMainLooper() == Looper.myLooper()) {
/* 546 */               super.setKeepScreenOn(keepScreenOn);
                    } else {
/* 548 */               post(new RunnableC04094(keepScreenOn));
                    }
                }

                /* JADX INFO: renamed from: co.vine.android.player.SdkVideoView$4 */
/* 546 */       class RunnableC04094 implements Runnable {
                    final /* synthetic */ boolean val$keepScreenOn;

/* 547 */           RunnableC04094(boolean z) {
                        this.val$keepScreenOn = z;
                    }

                    @Override // java.lang.Runnable
/* 550 */           public void run() {
/* 551 */               SdkVideoView.access$1001(SdkVideoView.this, this.val$keepScreenOn);
                    }
                }

/* 557 */       public void setAutoShow(boolean autoShow) {
/* 558 */           this.mAutoShow = autoShow;
                }

/* 561 */       public void setMediaController(MediaController controller) {
/* 562 */           if (this.mMediaController != null) {
/* 563 */               this.mMediaController.hide();
                    }
/* 565 */           this.mMediaController = controller;
/* 566 */           attachMediaController();
                }

/* 569 */       private void attachMediaController() {
/* 570 */           if (this.mMediaController != null && hasPlayer()) {
/* 571 */               this.mMediaController.setMediaPlayer(this);
/* 572 */               View anchorView = getParent() instanceof View ? (View) getParent() : this;
/* 574 */               this.mMediaController.setAnchorView(anchorView);
/* 575 */               this.mMediaController.setEnabled(isInPlaybackState());
                    }
                }

/* 579 */       public void setOnVideoSizeChangedListener(MediaPlayer.OnVideoSizeChangedListener listener) {
/* 580 */           this.mCustomOnSizeChangedListener = listener;
                }

                /* JADX INFO: renamed from: co.vine.android.player.SdkVideoView$5 */
/* 582 */       class C04105 implements MediaPlayer.OnVideoSizeChangedListener {
/* 583 */           C04105() {
                    }

                    @Override // android.media.MediaPlayer.OnVideoSizeChangedListener
/* 586 */           public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {
                        try {
/* 587 */                   int w = mp.getVideoWidth();
/* 588 */                   int h = mp.getVideoHeight();
/* 589 */                   SdkVideoView.access$500(SdkVideoView.this, w, h);
/* 590 */                   if (SdkVideoView.access$1100(SdkVideoView.this) != null) {
/* 591 */                       SdkVideoView.access$1100(SdkVideoView.this).onVideoSizeChanged(mp, w, h);
                            }
                        } catch (IllegalStateException e) {
                        }
                    }
                }

                /* JADX INFO: renamed from: co.vine.android.player.SdkVideoView$6 */
/* 597 */       class RunnableC04116 implements Runnable {
/* 598 */           RunnableC04116() {
                    }

                    @Override // java.lang.Runnable
/* 601 */           public void run() {
/* 602 */               if (SdkVideoView.access$800(SdkVideoView.this) != null) {
/* 603 */                   SdkVideoView.access$800(SdkVideoView.this).onPrepared(SdkVideoView.this);
                        }
                    }
                }

                /* JADX INFO: renamed from: co.vine.android.player.SdkVideoView$7 */
/* 606 */       class C04127 implements MediaPlayer.OnPreparedListener {
/* 607 */           C04127() {
                    }

                    @Override // android.media.MediaPlayer.OnPreparedListener
/* 609 */           public void onPrepared(MediaPlayer mp) {
/* 610 */               SLog.m442b("Prepare took {} ms", Long.valueOf(System.currentTimeMillis() - SdkVideoView.access$1200(SdkVideoView.this)));
                        try {
/* 612 */                   mp.seekTo(0);
                        } catch (Exception e) {
                        }
/* 616 */               SdkVideoView.access$700(SdkVideoView.this, 4);
/* 617 */               SdkVideoView.access$1302(SdkVideoView.this, true);
/* 618 */               if (SdkVideoView.access$1400(SdkVideoView.this)) {
/* 619 */                   SdkVideoView.access$1500(SdkVideoView.this, 2);
                        }
                        try {
/* 622 */                   SdkVideoView.access$500(SdkVideoView.this, mp.getVideoWidth(), mp.getVideoHeight());
                        } catch (IllegalStateException e2) {
/* 624 */                   SLog.m455e("IllegalState happened. ");
                        }
/* 626 */               SdkVideoView.access$1600(SdkVideoView.this);
/* 627 */               SdkVideoView.access$1800(SdkVideoView.this).post(SdkVideoView.access$1700(SdkVideoView.this));
                    }
                }

/* 631 */       private void checkForFrameDrops(String op, long start) {
/* 632 */           if (SLog.sLogsOn && Looper.getMainLooper() == Looper.myLooper()) {
/* 633 */               long time = System.currentTimeMillis() - start;
                        if (time > OPEN_ON_MAIN_THREAD_THRESHOLD) {
/* 635 */                   SLog.m479w("Frame drops caused by {}(): {}", op, Long.valueOf(time));
                        }
                    }
                }

/* 640 */       private void onPlayerPrepared() {
/* 641 */           if (this.mMediaController != null) {
/* 642 */               this.mMediaController.setEnabled(true);
                    }
/* 645 */           int seekToPosition = this.mSeekWhenPrepared;
/* 646 */           if (seekToPosition != 0) {
/* 647 */               mediaOpSeekTo(seekToPosition);
                    }
/* 649 */           if (this.mVideoWidth != 0 && this.mVideoHeight != 0) {
/* 652 */               if (this.mSurfaceWidth == this.mVideoWidth && this.mSurfaceHeight == this.mVideoHeight) {
/* 656 */                   if (this.mTargetState == 6) {
/* 657 */                       start();
/* 658 */                       if (this.mMediaController != null) {
/* 659 */                           this.mMediaController.show();
/* 676 */                           return;
                                }
/* 676 */                       return;
                            }
/* 661 */                   if (!isPlaying()) {
/* 663 */                       if ((seekToPosition != 0 || getCurrentPosition() > 0) && this.mMediaController != null) {
/* 665 */                           this.mMediaController.show(0);
/* 676 */                           return;
                                }
/* 676 */                       return;
                            }
/* 676 */                   return;
                        }
/* 676 */               return;
                    }
/* 672 */           if (this.mTargetState == 6) {
/* 673 */               start();
                    }
                }

                /* JADX INFO: renamed from: co.vine.android.player.SdkVideoView$8 */
/* 677 */       class C04138 implements MediaPlayer.OnCompletionListener {
/* 678 */           C04138() {
                    }

                    @Override // android.media.MediaPlayer.OnCompletionListener
/* 680 */           public void onCompletion(MediaPlayer mp) {
/* 681 */               SdkVideoView.access$600(SdkVideoView.this);
                    }
                }

/* 685 */       private void onComplete() {
/* 686 */           setCurrentState(5);
/* 687 */           this.mTargetState = 5;
/* 688 */           if (this.mMediaController != null) {
/* 689 */               this.mMediaController.hide();
                    }
/* 691 */           if (this.mOnCompletionListener != null) {
/* 692 */               this.mOnCompletionListener.onCompletion(this);
                    }
                }

                /* JADX INFO: renamed from: co.vine.android.player.SdkVideoView$9 */
/* 695 */       class C04149 implements MediaPlayer.OnErrorListener {
/* 696 */           C04149() {
                    }

                    @Override // android.media.MediaPlayer.OnErrorListener
/* 698 */           public boolean onError(MediaPlayer mp, int framework_err, int impl_err) {
/* 699 */               SdkVideoView.access$300(SdkVideoView.this, framework_err, impl_err);
/* 700 */               return true;
                    }
                }

/* 704 */       private boolean onError(int framework_err, int impl_err) {
/* 705 */           SLog.m453d("Error: {}, {}, {}, {}.", new Object[]{Integer.valueOf(framework_err), Integer.valueOf(impl_err), this, this.mPath});
/* 706 */           SLog.m447d("Framework Error: {}", getErrorMessageFromFrameworkError(framework_err));
/* 707 */           setCurrentState(-1);
/* 708 */           this.mTargetState = -1;
/* 709 */           this.mPath = null;
/* 710 */           if (this.mMediaController != null) {
/* 711 */               this.mMediaController.hide();
                    }
                    return this.mOnErrorListener != null && this.mOnErrorListener.onError(this, framework_err, impl_err);
                }

                /* JADX INFO: renamed from: co.vine.android.player.SdkVideoView$10 */
/* 721 */       class C040110 implements MediaPlayer.OnBufferingUpdateListener {
/* 722 */           C040110() {
                    }

                    @Override // android.media.MediaPlayer.OnBufferingUpdateListener
/* 724 */           public void onBufferingUpdate(MediaPlayer mp, int percent) {
/* 725 */               SdkVideoView.access$1902(SdkVideoView.this, percent);
                    }
                }

                /* JADX INFO: renamed from: co.vine.android.player.SdkVideoView$11 */
/* 727 */       class C040211 implements MediaPlayer.OnSeekCompleteListener {
/* 728 */           C040211() {
                    }

                    @Override // android.media.MediaPlayer.OnSeekCompleteListener
/* 732 */           public void onSeekComplete(MediaPlayer mp) {
/* 733 */               SdkVideoView.access$2002(SdkVideoView.this, false);
/* 734 */               if (SdkVideoView.access$2100(SdkVideoView.this) >= 0) {
/* 735 */                   int seek = SdkVideoView.access$2100(SdkVideoView.this);
/* 736 */                   SdkVideoView.access$2102(SdkVideoView.this, -1);
/* 737 */                   mp.seekTo(seek);
                        }
                    }
                }

                @Override // co.vine.android.player.VideoViewInterface
/* 748 */       public void setOnPreparedListener(VideoViewInterface.OnPreparedListener l) {
/* 749 */           this.mOnPreparedListener = l;
                }

                @Override // co.vine.android.player.VideoViewInterface
/* 753 */       public void setCacheKey(CacheKey cacheKey) {
/* 754 */           this.mCacheKey = cacheKey;
                }

                @Override // co.vine.android.player.VideoViewInterface
/* 758 */       public CacheKey getCacheKey() {
/* 759 */           return this.mCacheKey;
                }

                @Override // co.vine.android.player.VideoViewInterface
/* 762 */       public void setAutoPlayOnPrepared(boolean autoPlay) {
/* 763 */           this.mAutoPlayOnPrepared = autoPlay;
                }

                @Override // co.vine.android.player.VideoViewInterface
/* 772 */       public void setOnCompletionListener(VideoViewInterface.OnCompletionListener l) {
/* 773 */           this.mOnCompletionListener = l;
                }

/* 776 */       public VideoViewInterface.OnCompletionListener getOnCompletionListener() {
/* 777 */           return this.mOnCompletionListener;
                }

                @Override // co.vine.android.player.VideoViewInterface
/* 788 */       public void setOnErrorListener(VideoViewInterface.OnErrorListener l) {
/* 789 */           this.mOnErrorListener = l;
                }

/* 798 */       public void setOnInfoListener(MediaPlayer.OnInfoListener l) {
/* 799 */           this.mOnInfoListener = l;
                }

/* 802 */       public void setReportOpenError(boolean reportError) {
/* 803 */           this.mReportOpenError = reportError;
                }

/* 812 */       public void setSurfaceReadyListener(SurfaceReadyListener listener) {
/* 813 */           this.mSurfaceReadyListener = listener;
                }

/* 816 */       public void setSurfaceUpdatedListener(SurfaceUpdatedListener listener) {
/* 817 */           this.mSurfaceUpdatedListener = listener;
                }

                /* JADX INFO: renamed from: co.vine.android.player.SdkVideoView$12 */
/* 818 */       class TextureViewSurfaceTextureListenerC040312 implements TextureView.SurfaceTextureListener {
/* 819 */           TextureViewSurfaceTextureListenerC040312() {
                    }

                    @Override // android.view.TextureView.SurfaceTextureListener
/* 822 */           public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
/* 823 */               SLog.m464i("Surface is ready.");
/* 824 */               if (SdkVideoView.access$2200(SdkVideoView.this) == null) {
/* 825 */                   SdkVideoView.access$2300(SdkVideoView.this);
                        } else {
/* 827 */                   SdkVideoView.access$2200(SdkVideoView.this).onSurfaceTextureAvailable(SdkVideoView.this);
                        }
                    }

                    @Override // android.view.TextureView.SurfaceTextureListener
/* 832 */           public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
/* 833 */               SdkVideoView.access$2402(SdkVideoView.this, width);
/* 834 */               SdkVideoView.access$2502(SdkVideoView.this, height);
/* 835 */               boolean isValidState = SdkVideoView.access$2600(SdkVideoView.this) == 6;
/* 836 */               boolean hasValidSize = SdkVideoView.this.mVideoWidth == width && SdkVideoView.this.mVideoHeight == height;
/* 837 */               if (SdkVideoView.access$2700(SdkVideoView.this) && isValidState && hasValidSize) {
/* 838 */                   if (SdkVideoView.access$2800(SdkVideoView.this) != 0) {
/* 839 */                       SdkVideoView.this.seekTo(SdkVideoView.access$2800(SdkVideoView.this));
                            }
/* 841 */                   SdkVideoView.this.start();
                        }
                    }

                    @Override // android.view.TextureView.SurfaceTextureListener
/* 847 */           public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
/* 848 */               if (SdkVideoView.access$2900(SdkVideoView.this) != null) {
                            SdkVideoView.access$2900(SdkVideoView.this).hide();
                        }
/* 849 */               if (SdkVideoView.access$3000(SdkVideoView.this) != null) {
/* 850 */                   SdkVideoView.access$3100(SdkVideoView.this, "surface destroyed", true, false);
                        } else {
/* 852 */                   SdkVideoView.access$3100(SdkVideoView.this, "surface destroyed", true, true);
                        }
                        return true;
                    }

                    @Override // android.view.TextureView.SurfaceTextureListener
/* 858 */           public void onSurfaceTextureUpdated(SurfaceTexture surface) {
/* 859 */               if (SdkVideoView.access$3200(SdkVideoView.this) != null) {
/* 860 */                   SdkVideoView.access$3200(SdkVideoView.this).onSurfaceUpdated();
                        }
                    }
                }

/* 865 */       public static void releaseStaticIfNeeded() {
                    synchronized (LOCK) {
                        try {
                            try {
/* 868 */                       if (singlePlayer != null) {
/* 869 */                           singlePlayer.release();
/* 870 */                           singlePlayer = null;
                                }
                            } catch (Exception e) {
/* 873 */                       SLog.m459e("Failed to release static player.", (Throwable) e);
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }

/* 878 */       private void setPlayer(MediaPlayer player) {
/* 879 */           if (this.SINGLE_PLAYER) {
/* 880 */               singlePlayer = player;
                    } else {
/* 882 */               this.mMediaPlayer = player;
                    }
                }

                @Override // co.vine.android.player.VideoViewInterface
/* 886 */       public void stopPlayback() {
/* 887 */           if (this.SINGLE_PLAYER) {
                        synchronized (LOCK) {
                            try {
/* 889 */                       release("stop playback", true, false);
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
/* 894 */               return;
                    }
/* 892 */           release("stop playback", true, false);
                }

/* 896 */       private void onVideoSizeChanged(int width, int height) {
/* 897 */           if (this.mVideoHeight != height || this.mVideoWidth != width) {
/* 898 */               this.mVideoWidth = width;
/* 899 */               this.mVideoHeight = height;
/* 900 */               if (this.mVideoWidth != 0 && this.mVideoHeight != 0) {
/* 901 */                   this.mUiOpsHandler.post(new RunnableC040413());
                        }
                    }
                }

                /* JADX INFO: renamed from: co.vine.android.player.SdkVideoView$13 */
/* 899 */       class RunnableC040413 implements Runnable {
/* 900 */           RunnableC040413() {
                    }

                    @Override // java.lang.Runnable
/* 903 */           public void run() {
/* 904 */               SdkVideoView.this.requestLayout();
                    }
                }

/* 920 */       private void release(String tag, boolean cleartargetstate, boolean runNow) {
/* 921 */           this.mReleaseTag = tag;
/* 922 */           this.mShouldClearState = cleartargetstate;
/* 923 */           if (runNow) {
/* 924 */               mediaOpRelease();
                    } else {
/* 926 */               removePendingMediaOps();
/* 927 */               postMediaOp(0);
                    }
/* 929 */           setKeepScreenOn(false);
                }

                @Override // android.view.View
/* 933 */       public boolean onTouchEvent(@NonNull MotionEvent ev) {
/* 934 */           if (isInPlaybackState() && this.mMediaController != null) {
/* 935 */               toggleMediaControlsVisibility();
/* 937 */               return false;
                    }
/* 937 */           return false;
                }

                @Override // android.view.View
/* 941 */       public boolean onTrackballEvent(MotionEvent ev) {
/* 942 */           if (isInPlaybackState() && this.mMediaController != null) {
/* 943 */               toggleMediaControlsVisibility();
/* 945 */               return false;
                    }
/* 945 */           return false;
                }

/* 949 */       public boolean isPlayerPlaying() {
                    boolean r = true;
/* 950 */           long start = System.currentTimeMillis();
/* 951 */           if (this.mUseVinePlayer) {
/* 952 */               if (this.mVinePlayer == null || !this.mVinePlayer.isPlaying()) {
                            r = false;
                        }
                    } else if (getPlayer() == null || !getPlayer().isPlaying()) {
                        r = false;
                    }
/* 956 */           checkForFrameDrops("isPlayerPlaying", start);
/* 957 */           return r;
                }

                @Override // android.view.View, android.view.KeyEvent.Callback
/* 961 */       public boolean onKeyDown(int keyCode, @NonNull KeyEvent event) {
/* 962 */           boolean isKeyCodeSupported = (keyCode == 4 || keyCode == 24 || keyCode == 25 || keyCode == 164 || keyCode == 82 || keyCode == 5 || keyCode == 6) ? false : true;
/* 969 */           if (isInPlaybackState() && isKeyCodeSupported && this.mMediaController != null) {
/* 970 */               if (keyCode == 79 || keyCode == 85) {
/* 972 */                   if (getPlayer().isPlaying()) {
/* 973 */                       pause();
/* 974 */                       this.mMediaController.show();
                                return true;
                            }
/* 976 */                   start();
/* 977 */                   this.mMediaController.hide();
                            return true;
                        }
/* 980 */               if (keyCode == 126) {
/* 981 */                   if (getPlayer().isPlaying()) {
                                return true;
                            }
/* 982 */                   start();
/* 983 */                   this.mMediaController.hide();
                            return true;
                        }
/* 986 */               if (keyCode == 86 || keyCode == 127) {
/* 988 */                   if (!getPlayer().isPlaying()) {
                                return true;
                            }
/* 989 */                   pause();
/* 990 */                   this.mMediaController.show();
                            return true;
                        }
/* 994 */               toggleMediaControlsVisibility();
                    }
/* 998 */           return super.onKeyDown(keyCode, event);
                }

/* 1001 */      private void toggleMediaControlsVisibility() {
/* 1002 */          if (this.mMediaController.isShowing()) {
/* 1003 */              this.mMediaController.hide();
                    } else {
/* 1005 */              this.mMediaController.show();
                    }
                }

                @Override // android.widget.MediaController.MediaPlayerControl, co.vine.android.player.VideoViewInterface
/* 1009 */      public void start() {
/* 1010 */          start(true);
                }

/* 1015 */      private void mediaOpStart() {
                    synchronized (LOCK) {
                        try {
/* 1017 */                  if (isInPlaybackState()) {
/* 1018 */                      long start = System.currentTimeMillis();
/* 1019 */                      if (this.mUseVinePlayer) {
/* 1020 */                          VinePlayer vinePlayer = this.mVinePlayer;
/* 1021 */                          if (vinePlayer != null) {
/* 1022 */                              vinePlayer.resume();
                                    }
                                } else {
                                    try {
/* 1026 */                              MediaPlayer player = getPlayer();
/* 1027 */                              if (player != null) {
/* 1028 */                                  player.start();
                                        }
                                    } catch (IllegalStateException e) {
/* 1032 */                              CrashUtil.logOrThrowInDebug(e);
                                    }
                                }
/* 1035 */                      checkForFrameDrops("start", start);
/* 1036 */                      setCurrentState(6);
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
/* 1039 */          if (this.mEnsureVisibilityWhenStart && getVisibility() != 0) {
/* 1040 */              this.mUiOpsHandler.postDelayed(this.mVisibileStartRunnable, 40L);
                    }
/* 1042 */          this.mTargetState = 6;
                }

/* 1045 */      public void start(boolean ensureVisibility) {
/* 1046 */          this.mEnsureVisibilityWhenStart = ensureVisibility;
/* 1047 */          postMediaOp(2);
                }

                /* JADX INFO: renamed from: co.vine.android.player.SdkVideoView$14 */
/* 1048 */      class RunnableC040514 implements Runnable {
/* 1049 */          RunnableC040514() {
                    }

                    @Override // java.lang.Runnable
/* 1052 */          public void run() {
/* 1053 */              SdkVideoView.this.setVisibility(0);
                    }
                }

                @Override // android.view.View
/* 1058 */      public void setVisibility(int visibility) {
/* 1059 */          super.setVisibility(visibility);
/* 1060 */          this.mUiOpsHandler.removeCallbacks(this.mVisibileStartRunnable);
                }

                @Override // android.widget.MediaController.MediaPlayerControl, co.vine.android.player.VideoViewInterface
/* 1063 */      public void pause() {
/* 1064 */          if (isInPlaybackState()) {
/* 1065 */              if (this.mUseVinePlayer) {
/* 1066 */                  this.mVinePlayer.pause();
/* 1067 */                  if (this.mVinePlayer.isPaused()) {
/* 1068 */                      setCurrentState(7);
                            }
                        } else {
/* 1071 */                  long start = System.currentTimeMillis();
/* 1072 */                  if (getPlayer().isPlaying()) {
/* 1073 */                      getPlayer().pause();
/* 1074 */                      onPause();
/* 1075 */                      setCurrentState(7);
                            }
/* 1077 */                  checkForFrameDrops("pause", start);
                        }
                    }
/* 1080 */          this.mTargetState = 7;
                }

                @Override // co.vine.android.player.VideoViewInterface
/* 1083 */      public void suspend() {
/* 1084 */          release("suspend", false, !this.mUseVinePlayer);
                }

                @Override // co.vine.android.player.VideoViewInterface
/* 1087 */      public boolean hasStarted() {
/* 1088 */          return isInPlaybackState();
                }

                @Override // co.vine.android.player.VideoViewInterface
/* 1091 */      public boolean isPaused() {
                    return getCurrentState() == 7;
                }

                @Override // co.vine.android.player.VideoViewInterface
/* 1095 */      public void setLooping(boolean looping) {
/* 1096 */          this.mLooping = looping;
                }

                @Override // co.vine.android.player.VideoViewInterface
/* 1099 */      public void resume() {
/* 1100 */          this.mWasMuted = !this.mMuted;
/* 1101 */          setMute(this.mMuted);
/* 1102 */          start();
                }

                @Override // android.widget.MediaController.MediaPlayerControl, co.vine.android.player.VideoViewInterface
/* 1107 */      public int getDuration() {
                    int duration = -1;
                    try {
/* 1108 */              if (isInPlaybackState()) {
/* 1109 */                  if (this.mVinePlayer != null) {
/* 1110 */                      duration = this.mVinePlayer.getDuration();
                            } else {
/* 1112 */                      MediaPlayer player = getPlayer();
/* 1113 */                      if (player != null) {
/* 1114 */                          duration = player.getDuration();
                                }
                            }
                        }
                    } catch (Exception e) {
/* 1120 */              CrashUtil.logException(e, "It's ok. probably a race condition.", new Object[0]);
                    }
/* 1122 */          return duration;
                }

                /* JADX WARN: Code duplicated, block: B:14:0x0023  */
                @Override // android.widget.MediaController.MediaPlayerControl, co.vine.android.player.VideoViewInterface
/* 1126 */      public int getCurrentPosition() {
                    int currentPosition;
                    try {
/* 1127 */              if (isInPlaybackState()) {
/* 1128 */                  if (this.mVinePlayer != null) {
/* 1129 */                      currentPosition = this.mVinePlayer.getCurrentPosition();
                            } else if (getPlayer() != null) {
/* 1132 */                      currentPosition = getPlayer().getCurrentPosition();
                            } else {
/* 1138 */                      currentPosition = 0;
                            }
                        } else {
/* 1138 */                  currentPosition = 0;
                        }
/* 1138 */              return currentPosition;
                    } catch (Exception e) {
/* 1136 */              return -1;
                    }
                }

                @Override // android.widget.MediaController.MediaPlayerControl, co.vine.android.player.VideoViewInterface
/* 1141 */      public void seekTo(int msec) {
/* 1142 */          postMediaOp(msec + 1000);
                }

/* 1145 */      public void mediaOpSeekTo(int msec) {
/* 1146 */          if (isInPlaybackState()) {
/* 1147 */              if (!this.mIsSeeking) {
/* 1148 */                  this.mIsSeeking = true;
/* 1149 */                  MediaPlayer player = getPlayer();
/* 1150 */                  if (player != null) {
/* 1151 */                      player.seekTo(msec);
                            }
/* 1153 */                  if (this.mUseVinePlayer) {
/* 1154 */                      Log.w(TAG, "Seek does not work with VinePlayer.");
                            }
                        } else {
/* 1158 */                  this.mNextSeek = msec;
                        }
/* 1160 */              this.mSeekWhenPrepared = 0;
/* 1164 */              return;
                    }
/* 1162 */          this.mSeekWhenPrepared = msec;
                }

                @Override // android.widget.MediaController.MediaPlayerControl, co.vine.android.player.VideoViewInterface
/* 1167 */      public boolean isPlaying() {
                    try {
                        return isInPlaybackState() && isPlayerPlaying();
                    } catch (Exception e) {
                        return false;
                    }
                }

                @Override // android.widget.MediaController.MediaPlayerControl
/* 1174 */      public int getBufferPercentage() {
/* 1175 */          if (hasPlayer()) {
/* 1176 */              return this.mCurrentBufferPercentage;
                    }
                    return 0;
                }

/* 1181 */      public boolean hasControlOfPlayer() {
                    return !this.SINGLE_PLAYER || (currentView == this && currentUri == this.mUri);
                }

                @Override // co.vine.android.player.VideoViewInterface
/* 1185 */      public boolean isInPlaybackState() {
                    return (!hasPlayer() || getCurrentState() == -1 || getCurrentState() == 1 || getCurrentState() == 2) ? false : true;
                }

                @Override // android.widget.MediaController.MediaPlayerControl
/* 1192 */      public boolean canPause() {
/* 1193 */          return this.mIsPrepared;
                }

                @Override // android.widget.MediaController.MediaPlayerControl
/* 1196 */      public boolean canSeekBackward() {
/* 1197 */          return this.mIsPrepared;
                }

                @Override // android.widget.MediaController.MediaPlayerControl
/* 1200 */      public boolean canSeekForward() {
/* 1201 */          return this.mIsPrepared;
                }

                @Override // android.widget.MediaController.MediaPlayerControl
/* 1204 */      public int getAudioSessionId() {
/* 1205 */          if (this.mAudioSession == 0) {
/* 1206 */              MediaPlayer foo = new MediaPlayer();
/* 1207 */              this.mAudioSession = foo.getAudioSessionId();
/* 1208 */              foo.release();
                    }
/* 1210 */          return this.mAudioSession;
                }

/* 1213 */      public AttributeSet getAttributes() {
/* 1214 */          return this.mAttributes;
                }

/* 1217 */      private void postMediaOp(int what) {
/* 1218 */          if (this.mUseVinePlayer || sRunMediaOpsOnSeparateThread) {
/* 1219 */              Handler handler = this.mMediaOpHandler;
/* 1220 */              if (handler != null) {
/* 1221 */                  handler.sendEmptyMessage(what);
/* 1229 */                  return;
                        }
/* 1229 */              return;
                    }
/* 1224 */          runMediaOps(what);
/* 1225 */          if (what == 2) {
/* 1226 */              onPlay();
                    }
                }

/* 1231 */      private void removePendingMediaOps() {
/* 1232 */          Handler handler = this.mMediaOpHandler;
/* 1233 */          this.mIsSeeking = false;
/* 1234 */          if (handler != null) {
/* 1235 */              handler.removeCallbacksAndMessages(null);
                    }
                }

                @Override // android.view.View
/* 1240 */      protected void onDetachedFromWindow() {
/* 1241 */          this.mUiOpsHandler.removeCallbacks(this.mVisibileStartRunnable);
/* 1242 */          release("onDetachedFromWindow", true, false);
                    try {
/* 1244 */              super.onDetachedFromWindow();
                    } catch (Exception e) {
/* 1246 */              SurfaceTexture tx = null;
/* 1247 */              boolean success = true;
                        try {
/* 1249 */                  tx = getSurfaceTexture();
/* 1250 */                  if (tx != null) {
/* 1251 */                      tx.release();
                            }
                        } catch (Exception e2) {
/* 1254 */                  success = false;
/* 1255 */                  CrashUtil.log("Failed to release: {}.", e2);
                        }
/* 1257 */              CrashUtil.logException(e, "Failed to detach from window, but it's ok, since we won't use this anyways: {}, {}.", Boolean.valueOf(success), tx);
                    }
/* 1259 */          this.mMediaOpHandler = null;
/* 1260 */          if (this.mMediaOpThread != null) {
/* 1261 */              this.mMediaOpThread.quit();
/* 1262 */              this.mMediaOpThread = null;
                    }
                }

/* 1266 */      public void setMute(boolean mute) {
/* 1267 */          this.mMuted = mute;
/* 1268 */          postMediaOp(this.mMuted ? 3 : 4);
                }

/* 1271 */      private void mediaOpChangeVolume(float volume) {
                    synchronized (LOCK) {
                        try {
/* 1273 */                  if (isInPlaybackState() && this.mWasMuted != this.mMuted) {
/* 1274 */                      if (this.mVinePlayer != null) {
/* 1275 */                          this.mVinePlayer.setVolume(volume);
                                } else {
/* 1277 */                          MediaPlayer player = getPlayer();
/* 1278 */                          if (player != null) {
                                        try {
/* 1280 */                                  player.setVolume(volume, volume);
                                        } catch (IllegalStateException e) {
/* 1282 */                                  CrashUtil.logException(e);
                                        }
                                    }
                                }
/* 1286 */                      this.mWasMuted = this.mMuted;
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }

/* 1291 */      public boolean isPathPlaying(String newPath) {
                    return hasControlOfPlayer() && newPath.equals(this.mPath);
                }

/* 1295 */      public static Uri getCurrentUri() {
/* 1296 */          return currentUri;
                }

/* 1299 */      private void mediaOpOpen() {
                    Handler handler;
                    synchronized (LOCK) {
                        try {
/* 1301 */                  currentView = this;
/* 1303 */                  if (this.mUri == null) {
/* 1304 */                      Log.d(TAG, "mUri  is not ready yet.");
/* 1346 */                      return;
                            }
/* 1308 */                  SurfaceTexture texture = null;
/* 1309 */                  if (isAvailable()) {
/* 1310 */                      texture = getSurfaceTexture();
                            }
/* 1312 */                  if (texture == null) {
/* 1313 */                      boolean show = this.mAutoShow && getVisibility() != 0;
/* 1314 */                      Log.d(TAG, "Surface is not ready yet, will autoshow ? " + this.mAutoShow + " & " + getVisibility());
/* 1315 */                      if (show && (handler = getHandler()) != null) {
/* 1318 */                          handler.post(new RunnableC040615());
                                }
/* 1346 */                      return;
                            }
/* 1328 */                  Surface surface = new Surface(texture);
/* 1329 */                  Log.d(TAG, "Opening video: " + this.mUri);
/* 1334 */                  Intent i = new Intent("com.android.music.musicservicecommand");
/* 1335 */                  i.putExtra("command", "pause");
/* 1336 */                  this.mContext.sendBroadcast(i);
/* 1338 */                  release("open", false, true);
/* 1340 */                  if (this.mUseVinePlayer) {
/* 1341 */                      openVinePlayer(surface);
                            } else {
/* 1343 */                      openMediaPlayer(surface);
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }

                /* JADX INFO: renamed from: co.vine.android.player.SdkVideoView$15 */
/* 1316 */      class RunnableC040615 implements Runnable {
/* 1317 */          RunnableC040615() {
                    }

                    @Override // java.lang.Runnable
/* 1320 */          public void run() {
/* 1321 */              SdkVideoView.this.setVisibility(0);
                    }
                }

/* 1348 */      private void mediaOpRelease() {
/* 1349 */          MediaPlayer player = getPlayer();
/* 1350 */          if (player != null) {
                        try {
/* 1352 */                  long start = System.currentTimeMillis();
/* 1353 */                  if (player.isPlaying()) {
/* 1354 */                      player.stop();
/* 1355 */                      onPause();
                            }
/* 1357 */                  checkForFrameDrops(this.mReleaseTag + " stop", start);
                        } catch (IllegalStateException e) {
                        }
                        try {
/* 1362 */                  long start2 = System.currentTimeMillis();
/* 1363 */                  player.reset();
/* 1364 */                  checkForFrameDrops(this.mReleaseTag + " reset", start2);
                        } catch (IllegalStateException e2) {
                        }
/* 1368 */              if (!this.SINGLE_PLAYER) {
/* 1369 */                  long start3 = System.currentTimeMillis();
/* 1370 */                  player.release();
/* 1371 */                  setPlayer(null);
/* 1372 */                  checkForFrameDrops(this.mReleaseTag + " release", start3);
                        }
/* 1374 */              this.mIsPrepared = false;
/* 1375 */              setCurrentState(1);
/* 1376 */              if (this.mShouldClearState) {
/* 1377 */                  this.mTargetState = 1;
/* 1378 */                  this.mPath = null;
                        }
                    }
/* 1381 */          VinePlayer vp = this.mVinePlayer;
/* 1382 */          if (vp != null) {
                        try {
/* 1384 */                  vp.release();
/* 1388 */                  this.mVinePlayer = null;
                        } catch (IllegalAccessException e3) {
/* 1386 */                  throw new RuntimeException(e3);
                        }
                    }
                }

/* 1392 */      private void runMediaOps(int what) {
/* 1393 */          if (what >= 1000) {
/* 1394 */              mediaOpSeekTo(what - 1000);
                    }
                    switch (what) {
                        case 0:
/* 1398 */                  mediaOpRelease();
                            break;
                        case 1:
/* 1402 */                  mediaOpOpen();
                            break;
                        case 2:
/* 1406 */                  mediaOpStart();
                            break;
                        case 3:
/* 1410 */                  mediaOpChangeVolume(0.0f);
                            break;
                        case 4:
/* 1414 */                  mediaOpChangeVolume(1.0f);
                            break;
                    }
                }

/* 1419 */      private static class PlayerMaker implements Runnable {
                    private MediaPlayer mMp;

/* 1420 */          private PlayerMaker() {
                    }

/* 1420 */          /* synthetic */ PlayerMaker(RunnableC04001 x0) {
/* 1421 */              this();
                    }

/* 1425 */          synchronized MediaPlayer getPlayer() {
                        try {
                        } catch (Throwable th) {
                            throw th;
                        }
                        return this.mMp;
                    }

                    @Override // java.lang.Runnable
/* 1430 */          public void run() {
/* 1431 */              this.mMp = new MediaPlayer();
                    }

/* 1434 */          synchronized boolean isReady() {
                        try {
                        } catch (Throwable th) {
                            throw th;
                        }
                        return getPlayer() != null;
                    }
                }
            }
