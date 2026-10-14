            package co.vine.android.embed.player;

            import android.content.Context;
            import android.net.Uri;
            import android.os.Handler;
            import android.os.Looper;
            import android.os.Message;
            import android.os.SystemClock;
            import android.util.Log;
            import android.view.Surface;
            import co.vine.android.util.PriorityHandlerThread;
            import java.io.IOException;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 17 */    class VinePlayerInternal {
                private static final int IDLE_INTERVAL_MS = 300;
                private static final int MSG_DO_SOME_WORK = 1;
                private static final int MSG_PREPARE = 2;
                private static final int MSG_RESET = 5;
                private static final int RELEASE_WAIT = 10;
                private static final int RELEASE_WARN = 1000;
                private static final int RENDERING_INTERVAL_MS = 10;
                private static final String TAG = "VinePlayerInternal";
                private AudioRenderer mAudioRenderer;
                private final MediaClock mClock;
                private final Context mContext;
                private boolean mFirstFrameMessageSent;
                private Handler mHandler;
                private boolean mHasAudioTrack;
                private Boolean mLastPlayWhenReady;
                private final EventListener mListener;
                private final Handler mListenerHandler;
                private boolean mPlayWhenReady;
                private VineSampleSource mSampleSource;
                private PlayerState mState;
                private Surface mSurface;
                private Uri mUri;
                private VideoRenderer mVideoRenderer;
                private float mLastVolume = 1.0f;
                private float mVolume = 1.0f;
                private long mDurationMs = -1;
                private PriorityHandlerThread mPlaybackThread = new PriorityHandlerThread(getClass().getSimpleName() + ":Handler", -16);

                protected interface EventListener {
                    void onError(PlayerState playerState, int i, Throwable th);

                    void onFirstFrameRendered();

                    void onLoopCompleted();

                    void onPastError(Exception exc);

                    void onPrepared();

                    void onVideoSizeChanged(int i, int i2);
                }

/* 18 */        static /* synthetic */ EventListener access$100(VinePlayerInternal x0) {
/* 19 */            return x0.mListener;
                }

/* 18 */        static /* synthetic */ void access$200(VinePlayerInternal x0) throws AudioTrack.InitializationException, IOException, AudioTrack.WriteException {
/* 19 */            x0.doSomeWorkInternal();
                }

/* 18 */        static /* synthetic */ void access$300(VinePlayerInternal x0) throws AudioTrack.InitializationException, IOException, AudioTrack.WriteException {
/* 19 */            x0.prepareInternal();
                }

/* 18 */        static /* synthetic */ void access$400(VinePlayerInternal x0) {
/* 19 */            x0.resetInternal();
                }

/* 18 */        static /* synthetic */ PlayerState access$500(VinePlayerInternal x0) {
/* 19 */            return x0.mState;
                }

/* 18 */        static /* synthetic */ Handler access$600(VinePlayerInternal x0) {
/* 19 */            return x0.mListenerHandler;
                }

/* 69 */        public VinePlayerInternal(Context context, EventListener listener) {
/* 73 */            this.mPlaybackThread.start();
/* 74 */            this.mHandler = new Handler(this.mPlaybackThread.getLooper(), new PlayerMessageHandler(this, null));
/* 75 */            this.mListenerHandler = new Handler(Looper.getMainLooper());
/* 76 */            this.mState = PlayerState.IDLE;
/* 77 */            this.mContext = context;
/* 78 */            this.mClock = new MediaClock();
/* 79 */            this.mListener = listener;
                }

/* 82 */        public synchronized boolean playWhenReady() {
                    try {
                    } catch (Throwable th) {
                        throw th;
                    }
                    return this.mPlayWhenReady;
                }

/* 86 */        public synchronized void setPlayWhenReady(boolean playWhenReady) {
                    try {
                        this.mPlayWhenReady = playWhenReady;
                    } catch (Throwable th) {
                        throw th;
                    }
                }

/* 91 */        private void scheduleNextOperation(int operationType, long thisOperationStartTimeMs, long intervalMs) {
/* 92 */            long nextOperationStartTimeMs = thisOperationStartTimeMs + intervalMs;
/* 93 */            long nextOperationDelayMs = nextOperationStartTimeMs - SystemClock.elapsedRealtime();
                    if (nextOperationDelayMs <= 0) {
/* 95 */                this.mHandler.sendEmptyMessage(operationType);
                    } else {
/* 97 */                this.mHandler.sendEmptyMessageDelayed(operationType, nextOperationDelayMs);
                    }
                }

/* 101 */       private void resetInternal() {
/* 102 */           this.mPlayWhenReady = false;
/* 103 */           this.mUri = null;
/* 104 */           this.mSurface = null;
/* 105 */           this.mVolume = 1.0f;
/* 106 */           this.mLastPlayWhenReady = null;
/* 107 */           this.mLastVolume = 1.0f;
/* 108 */           this.mFirstFrameMessageSent = false;
/* 109 */           if (this.mSampleSource != null) {
/* 110 */               this.mSampleSource.release();
/* 111 */               Exception error = this.mSampleSource.getError();
/* 112 */               if (error != null) {
/* 113 */                   this.mListenerHandler.post(new RunnableC03771(error));
                        }
/* 120 */               this.mSampleSource = null;
                    }
/* 122 */           if (this.mVideoRenderer != null) {
/* 123 */               this.mVideoRenderer.release();
/* 124 */               this.mVideoRenderer = null;
                    }
/* 126 */           if (this.mAudioRenderer != null) {
/* 127 */               this.mAudioRenderer.release();
/* 128 */               this.mAudioRenderer = null;
                    }
/* 130 */           this.mClock.reset();
/* 131 */           setState(PlayerState.IDLE);
/* 132 */           this.mHandler.removeMessages(2);
/* 133 */           this.mHandler.removeMessages(1);
/* 134 */           Log.d(TAG, "Player reseted.");
                }

                /* JADX INFO: renamed from: co.vine.android.embed.player.VinePlayerInternal$1 */
/* 111 */       class RunnableC03771 implements Runnable {
                    final /* synthetic */ Exception val$error;

/* 112 */           RunnableC03771(Exception exc) {
                        this.val$error = exc;
                    }

                    @Override // java.lang.Runnable
/* 115 */           public void run() {
/* 116 */               VinePlayerInternal.access$100(VinePlayerInternal.this).onPastError(this.val$error);
                    }
                }

/* 140 */       public void reset() {
/* 141 */           if (getState() != PlayerState.IDLE) {
/* 142 */               this.mHandler.sendEmptyMessage(5);
/* 143 */               int s = 0;
/* 144 */               while (getState() != PlayerState.IDLE) {
/* 146 */                   s += 10;
                            try {
/* 147 */                       Thread.sleep(10L);
                            } catch (InterruptedException e) {
/* 149 */                       Thread.currentThread().interrupt();
                            }
/* 151 */                   if (s >= 1000) {
/* 152 */                       Log.e(TAG, "Taking too long to release, something is wrong.");
                            }
                        }
                    }
                }

/* 161 */       public void release() {
/* 162 */           reset();
/* 163 */           if (this.mPlaybackThread.isAlive()) {
/* 164 */               this.mPlaybackThread.quit();
                    }
                }

/* 168 */       public synchronized void prepareAsync() {
                    try {
                        if (this.mState != PlayerState.IDLE) {
/* 170 */                   throw new IllegalStateException("Player is not idle.");
                        }
/* 172 */               if (this.mUri == null) {
/* 173 */                   throw new IllegalStateException("Uri is not set.");
                        }
/* 175 */               if (!this.mPlaybackThread.isAlive()) {
/* 176 */                   throw new IllegalStateException("The player is already released.");
                        }
/* 178 */               this.mHandler.sendEmptyMessage(2);
                    } catch (Throwable th) {
                        throw th;
                    }
                }

/* 181 */       public synchronized void setUri(Uri uri) {
                    try {
                        this.mUri = uri;
                    } catch (Throwable th) {
                        throw th;
                    }
                }

/* 185 */       public synchronized Uri getUri() {
                    try {
                    } catch (Throwable th) {
                        throw th;
                    }
                    return this.mUri;
                }

/* 189 */       private void prepareInternal() throws AudioTrack.InitializationException, IOException, AudioTrack.WriteException {
/* 190 */           Log.i(TAG, "Preparing...");
/* 191 */           if (this.mState != PlayerState.IDLE) {
/* 192 */               throw new IllegalStateException("You cannot prepare when state is " + this.mState);
                    }
/* 194 */           setState(PlayerState.PREPARING);
/* 195 */           Uri uri = getUri();
/* 196 */           if (uri == null) {
/* 197 */               throw new IllegalArgumentException("You cannot prepare before setting a Uri");
                    }
/* 200 */           Surface surface = this.mSurface;
/* 201 */           if (surface == null) {
/* 202 */               throw new IllegalStateException("Surface is not set.");
                    }
/* 204 */           this.mSampleSource = new VineSampleSource(uri);
/* 205 */           this.mVideoRenderer = new VideoRenderer();
/* 206 */           this.mAudioRenderer = new AudioRenderer();
/* 208 */           this.mSampleSource.prepare(this.mContext);
/* 209 */           this.mAudioRenderer.prepare(this.mSampleSource);
/* 210 */           this.mVideoRenderer.prepare(this.mSampleSource, surface);
/* 212 */           if (this.mSampleSource.hasAudioTrack()) {
/* 213 */               this.mDurationMs = this.mAudioRenderer.getDurationMs();
/* 214 */               this.mHasAudioTrack = true;
                    } else {
/* 216 */               this.mDurationMs = this.mVideoRenderer.getDurationMs();
/* 217 */               this.mHasAudioTrack = false;
                    }
/* 219 */           setState(PlayerState.PLAYABLE);
/* 220 */           this.mListenerHandler.post(new RunnableC03782());
/* 226 */           doSomeWorkInternal();
                }

                /* JADX INFO: renamed from: co.vine.android.embed.player.VinePlayerInternal$2 */
/* 218 */       class RunnableC03782 implements Runnable {
/* 219 */           RunnableC03782() {
                    }

                    @Override // java.lang.Runnable
/* 222 */           public void run() {
/* 223 */               VinePlayerInternal.access$100(VinePlayerInternal.this).onPrepared();
                    }
                }

/* 229 */       public void setSurface(Surface surface) {
/* 230 */           this.mSurface = surface;
/* 231 */           if (this.mSurface == null) {
/* 232 */               release();
                    }
                }

/* 236 */       public int getCurrentPositionMs() {
/* 237 */           return (int) (this.mClock.positionUs() / 1000);
                }

/* 240 */       private synchronized void setState(PlayerState state) {
                    try {
                        this.mState = state;
                    } catch (Throwable th) {
                        throw th;
                    }
                }

/* 242 */       private class PlayerMessageHandler implements Handler.Callback {
/* 243 */           private PlayerMessageHandler() {
                    }

/* 243 */           /* synthetic */ PlayerMessageHandler(VinePlayerInternal x0, RunnableC03771 x1) {
/* 244 */               this();
                    }

                    @Override // android.os.Handler.Callback
/* 248 */           public boolean handleMessage(Message msg) {
                        boolean z = true;
                        try {
                            switch (msg.what) {
                                case 1:
/* 251 */                           VinePlayerInternal.access$200(VinePlayerInternal.this);
                                    break;
                                case 2:
/* 255 */                           VinePlayerInternal.access$300(VinePlayerInternal.this);
                                    break;
                                case 3:
                                case 4:
                                default:
/* 263 */                           z = false;
                                    break;
                                case 5:
/* 259 */                           VinePlayerInternal.access$400(VinePlayerInternal.this);
                                    break;
                            }
                        } catch (Throwable e) {
/* 267 */                   VinePlayerInternal.access$600(VinePlayerInternal.this).post(new RunnableC03821(msg, e));
                        }
/* 273 */               return z;
                    }

                    /* JADX INFO: renamed from: co.vine.android.embed.player.VinePlayerInternal$PlayerMessageHandler$1 */
/* 265 */           class RunnableC03821 implements Runnable {
                        final /* synthetic */ Throwable val$e;
                        final /* synthetic */ Message val$msg;

/* 266 */               RunnableC03821(Message message, Throwable th) {
                            this.val$msg = message;
                            this.val$e = th;
                        }

                        @Override // java.lang.Runnable
/* 269 */               public void run() {
/* 270 */                   VinePlayerInternal.access$100(VinePlayerInternal.this).onError(VinePlayerInternal.access$500(VinePlayerInternal.this), this.val$msg.what, this.val$e);
                        }
                    }
                }

/* 278 */       public synchronized PlayerState getState() {
                    try {
                    } catch (Throwable th) {
                        throw th;
                    }
                    return this.mState;
                }

/* 282 */       public void setVolume(float gain) {
/* 283 */           this.mVolume = gain;
                }

/* 286 */       public boolean isPlaying() {
                    return getState() == PlayerState.PLAYABLE && playWhenReady();
                }

/* 290 */       private void doSomeWorkInternal() throws AudioTrack.InitializationException, IOException, AudioTrack.WriteException {
/* 291 */           if (this.mState != PlayerState.IDLE) {
/* 292 */               boolean playWhenReady = playWhenReady();
/* 293 */               boolean justResumed = (this.mLastPlayWhenReady == null || !playWhenReady || this.mLastPlayWhenReady.booleanValue()) ? false : true;
/* 294 */               this.mLastPlayWhenReady = Boolean.valueOf(playWhenReady);
/* 295 */               this.mClock.operationStart();
/* 296 */               if (this.mState == PlayerState.PLAYABLE && playWhenReady) {
/* 297 */                   doAudioWork(justResumed);
/* 298 */                   doVideoWork(justResumed);
                        }
/* 300 */               this.mHandler.removeMessages(1);
/* 301 */               if (this.mState == PlayerState.PLAYABLE && playWhenReady) {
/* 302 */                   scheduleNextOperation(1, this.mClock.getOperationStartMs(), 10L);
                        } else {
/* 304 */                   scheduleNextOperation(1, this.mClock.getOperationStartMs(), 300L);
                        }
/* 306 */               this.mClock.operationEnd();
/* 310 */               return;
                    }
/* 308 */           Log.i(TAG, "Do nothing at state " + this.mState);
                }

/* 312 */       public boolean isReleased() {
                    return !this.mPlaybackThread.isAlive();
                }

/* 316 */       public long getDurationMs() {
/* 317 */           return this.mDurationMs;
                }

/* 320 */       private void doVideoWork(boolean justResumed) throws IOException {
/* 321 */           int width = this.mVideoRenderer.getWidth();
/* 322 */           int height = this.mVideoRenderer.getHeight();
/* 324 */           this.mVideoRenderer.doSomeWork(this.mClock, this.mHasAudioTrack, justResumed);
/* 326 */           int newWidth = this.mVideoRenderer.getWidth();
/* 327 */           int newHeight = this.mVideoRenderer.getHeight();
/* 329 */           if (width != newWidth || height != newHeight) {
/* 330 */               this.mListenerHandler.post(new RunnableC03793(newWidth, newHeight));
                    }
/* 338 */           if (!this.mFirstFrameMessageSent && this.mVideoRenderer.getFramesRendered() >= 1) {
/* 339 */               this.mFirstFrameMessageSent = true;
/* 340 */               this.mListenerHandler.post(new RunnableC03804());
                    }
                }

                /* JADX INFO: renamed from: co.vine.android.embed.player.VinePlayerInternal$3 */
/* 328 */       class RunnableC03793 implements Runnable {
                    final /* synthetic */ int val$newHeight;
                    final /* synthetic */ int val$newWidth;

/* 329 */           RunnableC03793(int i, int i2) {
                        this.val$newWidth = i;
                        this.val$newHeight = i2;
                    }

                    @Override // java.lang.Runnable
/* 332 */           public void run() {
/* 333 */               VinePlayerInternal.access$100(VinePlayerInternal.this).onVideoSizeChanged(this.val$newWidth, this.val$newHeight);
                    }
                }

                /* JADX INFO: renamed from: co.vine.android.embed.player.VinePlayerInternal$4 */
/* 338 */       class RunnableC03804 implements Runnable {
/* 339 */           RunnableC03804() {
                    }

                    @Override // java.lang.Runnable
/* 342 */           public void run() {
/* 343 */               VinePlayerInternal.access$100(VinePlayerInternal.this).onFirstFrameRendered();
                    }
                }

/* 349 */       private void doAudioWork(boolean justResumed) throws AudioTrack.InitializationException, AudioTrack.WriteException {
                    if (this.mVolume != this.mLastVolume) {
/* 351 */               this.mLastVolume = this.mVolume;
/* 352 */               this.mAudioRenderer.setVolume(this.mLastVolume);
                    }
/* 354 */           if (this.mAudioRenderer.doSomeWork(this.mClock, justResumed)) {
/* 355 */               this.mListenerHandler.post(new RunnableC03815());
                    }
                }

                /* JADX INFO: renamed from: co.vine.android.embed.player.VinePlayerInternal$5 */
/* 353 */       class RunnableC03815 implements Runnable {
/* 354 */           RunnableC03815() {
                    }

                    @Override // java.lang.Runnable
/* 357 */           public void run() {
/* 358 */               VinePlayerInternal.access$100(VinePlayerInternal.this).onLoopCompleted();
                    }
                }
            }
