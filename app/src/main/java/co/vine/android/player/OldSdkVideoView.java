            package co.vine.android.player;

            import android.content.Context;
            import android.content.Intent;
            import android.media.MediaPlayer;
            import android.net.Uri;
            import android.util.AttributeSet;
            import android.view.KeyEvent;
            import android.view.MotionEvent;
            import android.view.SurfaceHolder;
            import android.view.SurfaceView;
            import android.view.View;
            import android.widget.MediaController;
            import co.vine.android.cache.CacheKey;
            import co.vine.android.provider.VineVideoProvider;
            import co.vine.android.util.CrashUtil;
            import com.edisonwang.android.slog.SLog;
            import java.io.IOException;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 25 */    public class OldSdkVideoView extends SurfaceView implements MediaController.MediaPlayerControl, VideoViewInterface {
                private static final int STATE_ERROR = -1;
                private static final int STATE_IDLE = 0;
                private static final int STATE_PAUSED = 4;
                private static final int STATE_PLAYBACK_COMPLETED = 5;
                private static final int STATE_PLAYING = 3;
                private static final int STATE_PREPARED = 2;
                private static final int STATE_PREPARING = 1;
                private int mAudioSession;
                private boolean mAutoPlay;
                private MediaPlayer.OnBufferingUpdateListener mBufferingUpdateListener;
                private CacheKey mCacheKey;
                private boolean mCanPause;
                private boolean mCanSeekBack;
                private boolean mCanSeekForward;
                private MediaPlayer.OnCompletionListener mCompletionListener;
                private int mCurrentBufferPercentage;
                private int mCurrentState;
                private VideoViewInterface.OnPreparedListener mCustomOnPreParedListener;
                private MediaPlayer.OnErrorListener mErrorListener;
                private MediaPlayer.OnInfoListener mInfoListener;
                private boolean mLooping;
                private MediaController mMediaController;
                private MediaPlayer mMediaPlayer;
                private boolean mMuted;
                private VideoViewInterface.OnCompletionListener mOnCompletionListener;
                private VideoViewInterface.OnErrorListener mOnErrorListener;
                private MediaPlayer.OnInfoListener mOnInfoListener;
                private final MediaPlayer.OnPreparedListener mOnPreparedListener;
                MediaPlayer.OnPreparedListener mPreparedListener;
                private boolean mReportVideoError;
                SurfaceHolder.Callback mSHCallback;
                private int mSeekWhenPrepared;
                MediaPlayer.OnVideoSizeChangedListener mSizeChangedListener;
                private int mSurfaceHeight;
                private SurfaceHolder mSurfaceHolder;
                private int mSurfaceWidth;
                private int mTargetState;
                private Uri mUri;
                private int mVideoHeight;
                private int mVideoWidth;
                private boolean mWasMuted;

/* 26 */        static /* synthetic */ boolean access$000(OldSdkVideoView x0) {
/* 27 */            return x0.mAutoPlay;
                }

/* 26 */        static /* synthetic */ boolean access$100(OldSdkVideoView x0) {
/* 27 */            return x0.mLooping;
                }

/* 26 */        static /* synthetic */ MediaPlayer access$1000(OldSdkVideoView x0) {
/* 27 */            return x0.mMediaPlayer;
                }

/* 26 */        static /* synthetic */ MediaController access$1100(OldSdkVideoView x0) {
/* 27 */            return x0.mMediaController;
                }

/* 26 */        static /* synthetic */ int access$1200(OldSdkVideoView x0) {
/* 27 */            return x0.mSeekWhenPrepared;
                }

/* 26 */        static /* synthetic */ int access$1300(OldSdkVideoView x0) {
/* 27 */            return x0.mSurfaceWidth;
                }

/* 26 */        static /* synthetic */ int access$1302(OldSdkVideoView x0, int x1) {
/* 27 */            x0.mSurfaceWidth = x1;
                    return x1;
                }

/* 26 */        static /* synthetic */ int access$1400(OldSdkVideoView x0) {
/* 27 */            return x0.mSurfaceHeight;
                }

/* 26 */        static /* synthetic */ int access$1402(OldSdkVideoView x0, int x1) {
/* 27 */            x0.mSurfaceHeight = x1;
                    return x1;
                }

/* 26 */        static /* synthetic */ int access$1500(OldSdkVideoView x0) {
/* 27 */            return x0.mTargetState;
                }

/* 26 */        static /* synthetic */ int access$1502(OldSdkVideoView x0, int x1) {
/* 27 */            x0.mTargetState = x1;
                    return x1;
                }

/* 26 */        static /* synthetic */ VideoViewInterface.OnCompletionListener access$1600(OldSdkVideoView x0) {
/* 27 */            return x0.mOnCompletionListener;
                }

/* 26 */        static /* synthetic */ MediaPlayer.OnInfoListener access$1700(OldSdkVideoView x0) {
/* 27 */            return x0.mOnInfoListener;
                }

/* 26 */        static /* synthetic */ VideoViewInterface.OnErrorListener access$1800(OldSdkVideoView x0) {
/* 27 */            return x0.mOnErrorListener;
                }

/* 26 */        static /* synthetic */ int access$1902(OldSdkVideoView x0, int x1) {
/* 27 */            x0.mCurrentBufferPercentage = x1;
                    return x1;
                }

/* 26 */        static /* synthetic */ VideoViewInterface.OnPreparedListener access$200(OldSdkVideoView x0) {
/* 27 */            return x0.mCustomOnPreParedListener;
                }

/* 26 */        static /* synthetic */ SurfaceHolder access$2002(OldSdkVideoView x0, SurfaceHolder x1) {
/* 27 */            x0.mSurfaceHolder = x1;
                    return x1;
                }

/* 26 */        static /* synthetic */ void access$2100(OldSdkVideoView x0) {
/* 27 */            x0.openVideo();
                }

/* 26 */        static /* synthetic */ void access$2200(OldSdkVideoView x0, boolean x1) {
/* 27 */            x0.release(x1);
                }

/* 26 */        static /* synthetic */ int access$300(OldSdkVideoView x0) {
/* 27 */            return x0.mVideoWidth;
                }

/* 26 */        static /* synthetic */ int access$302(OldSdkVideoView x0, int x1) {
/* 27 */            x0.mVideoWidth = x1;
                    return x1;
                }

/* 26 */        static /* synthetic */ int access$400(OldSdkVideoView x0) {
/* 27 */            return x0.mVideoHeight;
                }

/* 26 */        static /* synthetic */ int access$402(OldSdkVideoView x0, int x1) {
/* 27 */            x0.mVideoHeight = x1;
                    return x1;
                }

/* 26 */        static /* synthetic */ int access$502(OldSdkVideoView x0, int x1) {
/* 27 */            x0.mCurrentState = x1;
                    return x1;
                }

/* 26 */        static /* synthetic */ boolean access$602(OldSdkVideoView x0, boolean x1) {
/* 27 */            x0.mCanPause = x1;
                    return x1;
                }

/* 26 */        static /* synthetic */ boolean access$702(OldSdkVideoView x0, boolean x1) {
/* 27 */            x0.mCanSeekBack = x1;
                    return x1;
                }

/* 26 */        static /* synthetic */ boolean access$802(OldSdkVideoView x0, boolean x1) {
/* 27 */            x0.mCanSeekForward = x1;
                    return x1;
                }

/* 26 */        static /* synthetic */ MediaPlayer.OnPreparedListener access$900(OldSdkVideoView x0) {
/* 27 */            return x0.mOnPreparedListener;
                }

                /* JADX INFO: renamed from: co.vine.android.player.OldSdkVideoView$1 */
/* 35 */        class C03911 implements MediaPlayer.OnPreparedListener {
/* 36 */            C03911() {
                    }

                    @Override // android.media.MediaPlayer.OnPreparedListener
/* 39 */            public void onPrepared(MediaPlayer mp) {
/* 40 */                SLog.m446d("Videoview prepared.");
/* 41 */                if (OldSdkVideoView.access$000(OldSdkVideoView.this)) {
/* 42 */                    mp.start();
                        }
/* 44 */                mp.setLooping(OldSdkVideoView.access$100(OldSdkVideoView.this));
/* 45 */                if (OldSdkVideoView.access$200(OldSdkVideoView.this) != null) {
/* 46 */                    OldSdkVideoView.access$200(OldSdkVideoView.this).onPrepared(OldSdkVideoView.this);
                        }
                    }
                }

/* 92 */        public OldSdkVideoView(Context context) {
/* 93 */            super(context);
/* 37 */            this.mOnPreparedListener = new C03911();
/* 66 */            this.mCurrentState = 0;
/* 67 */            this.mTargetState = 0;
/* 70 */            this.mSurfaceHolder = null;
/* 71 */            this.mMediaPlayer = null;
/* 87 */            this.mMuted = false;
/* 88 */            this.mWasMuted = false;
/* 316 */           this.mSizeChangedListener = new C03922();
/* 328 */           this.mPreparedListener = new C03933();
/* 377 */           this.mCompletionListener = new C03944();
/* 391 */           this.mInfoListener = new C03955();
/* 401 */           this.mErrorListener = new C03966();
/* 423 */           this.mBufferingUpdateListener = new C03977();
/* 462 */           this.mSHCallback = new SurfaceHolderCallbackC03988();
/* 94 */            initVideoView();
                }

/* 97 */        public OldSdkVideoView(Context context, AttributeSet attrs) {
/* 98 */            this(context, attrs, 0);
/* 99 */            initVideoView();
                }

/* 102 */       public OldSdkVideoView(Context context, AttributeSet attrs, int defStyle) {
/* 103 */           super(context, attrs, defStyle);
/* 37 */            this.mOnPreparedListener = new C03911();
/* 66 */            this.mCurrentState = 0;
/* 67 */            this.mTargetState = 0;
/* 70 */            this.mSurfaceHolder = null;
/* 71 */            this.mMediaPlayer = null;
/* 87 */            this.mMuted = false;
/* 88 */            this.mWasMuted = false;
/* 316 */           this.mSizeChangedListener = new C03922();
/* 328 */           this.mPreparedListener = new C03933();
/* 377 */           this.mCompletionListener = new C03944();
/* 391 */           this.mInfoListener = new C03955();
/* 401 */           this.mErrorListener = new C03966();
/* 423 */           this.mBufferingUpdateListener = new C03977();
/* 462 */           this.mSHCallback = new SurfaceHolderCallbackC03988();
/* 104 */           initVideoView();
                }

                @Override // android.view.SurfaceView, android.view.View
/* 111 */       protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
/* 112 */           int width = getDefaultSize(this.mVideoWidth, widthMeasureSpec);
/* 113 */           int height = getDefaultSize(this.mVideoHeight, heightMeasureSpec);
/* 114 */           if (this.mVideoWidth > 0 && this.mVideoHeight > 0) {
/* 116 */               int widthSpecMode = View.MeasureSpec.getMode(widthMeasureSpec);
/* 117 */               int widthSpecSize = View.MeasureSpec.getSize(widthMeasureSpec);
/* 118 */               int heightSpecMode = View.MeasureSpec.getMode(heightMeasureSpec);
/* 119 */               int heightSpecSize = View.MeasureSpec.getSize(heightMeasureSpec);
/* 121 */               if (widthSpecMode == 1073741824 && heightSpecMode == 1073741824) {
/* 123 */                   width = widthSpecSize;
/* 124 */                   height = heightSpecSize;
/* 127 */                   if (this.mVideoWidth * height < this.mVideoHeight * width) {
/* 129 */                       width = (this.mVideoWidth * height) / this.mVideoHeight;
                            } else if (this.mVideoWidth * height > this.mVideoHeight * width) {
/* 132 */                       height = (this.mVideoHeight * width) / this.mVideoWidth;
                            }
                        } else if (widthSpecMode == 1073741824) {
/* 136 */                   width = widthSpecSize;
/* 137 */                   height = (this.mVideoHeight * width) / this.mVideoWidth;
/* 138 */                   if (heightSpecMode == Integer.MIN_VALUE && height > heightSpecSize) {
/* 140 */                       height = heightSpecSize;
                            }
                        } else if (heightSpecMode == 1073741824) {
/* 144 */                   height = heightSpecSize;
/* 145 */                   width = (this.mVideoWidth * height) / this.mVideoHeight;
/* 146 */                   if (widthSpecMode == Integer.MIN_VALUE && width > widthSpecSize) {
/* 148 */                       width = widthSpecSize;
                            }
                        } else {
/* 152 */                   width = this.mVideoWidth;
/* 153 */                   height = this.mVideoHeight;
/* 154 */                   if (heightSpecMode == Integer.MIN_VALUE && height > heightSpecSize) {
/* 156 */                       height = heightSpecSize;
/* 157 */                       width = (this.mVideoWidth * height) / this.mVideoHeight;
                            }
/* 159 */                   if (widthSpecMode == Integer.MIN_VALUE && width > widthSpecSize) {
/* 161 */                       width = widthSpecSize;
/* 162 */                       height = (this.mVideoHeight * width) / this.mVideoWidth;
                            }
                        }
                    }
/* 168 */           setMeasuredDimension(width, height);
                }

/* 171 */       public int resolveAdjustedSize(int desiredSize, int measureSpec) {
/* 172 */           return getDefaultSize(desiredSize, measureSpec);
                }

/* 175 */       private void initVideoView() {
/* 176 */           this.mVideoWidth = 0;
/* 177 */           this.mVideoHeight = 0;
/* 178 */           getHolder().addCallback(this.mSHCallback);
/* 179 */           getHolder().setType(3);
/* 180 */           setFocusable(true);
/* 181 */           setFocusableInTouchMode(true);
/* 182 */           requestFocus();
/* 183 */           this.mCurrentState = 0;
/* 184 */           this.mTargetState = 0;
                }

                @Override // co.vine.android.player.VideoViewInterface
/* 191 */       public void setVideoPath(String path) {
/* 192 */           setVideoURI(Uri.parse(VineVideoProvider.CONTENT_AUTHORITY + path));
                }

                @Override // co.vine.android.player.VideoViewInterface
/* 199 */       public void setVideoPathDirect(String path) {
/* 200 */           setVideoURI(Uri.parse(path));
                }

/* 203 */       public void setVideoURI(Uri uri) {
/* 204 */           this.mUri = uri;
/* 205 */           this.mSeekWhenPrepared = 0;
/* 206 */           openVideo();
/* 207 */           requestLayout();
/* 208 */           invalidate();
                }

/* 211 */       public void removeVideoUri() {
/* 212 */           this.mUri = null;
/* 213 */           this.mSeekWhenPrepared = 0;
                }

                @Override // co.vine.android.player.VideoViewInterface
/* 216 */       public void stopPlayback() {
/* 217 */           if (this.mMediaPlayer != null) {
/* 218 */               SLog.m446d("Playback stopped.");
                        try {
/* 220 */                   this.mMediaPlayer.stop();
                        } catch (IllegalStateException e) {
                        }
                        try {
/* 225 */                   this.mMediaPlayer.reset();
                        } catch (IllegalStateException e2) {
                        }
                        try {
/* 230 */                   this.mMediaPlayer.release();
                        } catch (IllegalStateException e3) {
                        }
/* 234 */               this.mMediaPlayer = null;
/* 235 */               this.mCurrentState = 0;
/* 236 */               this.mTargetState = 0;
                    }
                }

                /* JADX WARN: Code duplicated, block: B:19:0x009e  */
/* 240 */       private void openVideo() {
/* 241 */           if (this.mUri != null && this.mSurfaceHolder != null) {
/* 246 */               Intent i = new Intent("com.android.music.musicservicecommand");
/* 247 */               i.putExtra("command", "pause");
/* 248 */               getContext().sendBroadcast(i);
/* 252 */               release(false);
                        try {
/* 254 */                   this.mMediaPlayer = new MediaPlayer();
/* 256 */                   Context context = getContext();
/* 258 */                   if (this.mAudioSession != 0) {
/* 259 */                       this.mMediaPlayer.setAudioSessionId(this.mAudioSession);
                            } else {
/* 261 */                       this.mAudioSession = this.mMediaPlayer.getAudioSessionId();
                            }
/* 263 */                   this.mMediaPlayer.setOnPreparedListener(this.mPreparedListener);
/* 264 */                   this.mMediaPlayer.setOnVideoSizeChangedListener(this.mSizeChangedListener);
/* 265 */                   this.mMediaPlayer.setOnCompletionListener(this.mCompletionListener);
/* 266 */                   this.mMediaPlayer.setOnErrorListener(this.mErrorListener);
/* 267 */                   this.mMediaPlayer.setOnInfoListener(this.mInfoListener);
/* 268 */                   this.mMediaPlayer.setOnBufferingUpdateListener(this.mBufferingUpdateListener);
/* 269 */                   this.mCurrentBufferPercentage = 0;
/* 270 */                   this.mMediaPlayer.setDataSource(context, this.mUri);
/* 271 */                   this.mMediaPlayer.setDisplay(this.mSurfaceHolder);
/* 272 */                   this.mMediaPlayer.setAudioStreamType(3);
/* 273 */                   this.mMediaPlayer.setScreenOnWhilePlaying(true);
/* 274 */                   this.mMediaPlayer.prepareAsync();
/* 276 */                   if (this.mMuted) {
/* 277 */                       this.mMediaPlayer.setVolume(0.0f, 0.0f);
                            }
/* 282 */                   this.mCurrentState = 1;
/* 283 */                   attachMediaController();
                        } catch (IOException e) {
/* 285 */                   if (this.mReportVideoError) {
/* 286 */                       CrashUtil.logException(e, "Unable to open content {}, {}.", this.mUri);
                            }
/* 288 */                   this.mCurrentState = -1;
/* 289 */                   this.mTargetState = -1;
/* 290 */                   this.mErrorListener.onError(this.mMediaPlayer, 1, 0);
                        } catch (IllegalArgumentException e2) {
/* 285 */                   if (this.mReportVideoError) {
/* 286 */                       CrashUtil.logException(e2, "Unable to open content {}, {}.", this.mUri);
                            }
/* 288 */                   this.mCurrentState = -1;
/* 289 */                   this.mTargetState = -1;
/* 290 */                   this.mErrorListener.onError(this.mMediaPlayer, 1, 0);
                        } catch (IllegalStateException ex) {
/* 292 */                   this.mCurrentState = -1;
/* 293 */                   this.mTargetState = -1;
/* 294 */                   CrashUtil.logException(ex, "IllegalState error {}, {}.", this.mUri);
                        }
                    }
                }

/* 298 */       public void setMediaController(MediaController controller) {
/* 299 */           if (this.mMediaController != null) {
/* 300 */               this.mMediaController.hide();
                    }
/* 302 */           this.mMediaController = controller;
/* 303 */           attachMediaController();
                }

/* 306 */       private void attachMediaController() {
/* 307 */           if (this.mMediaPlayer != null && this.mMediaController != null) {
/* 308 */               this.mMediaController.setMediaPlayer(this);
/* 309 */               View anchorView = getParent() instanceof View ? (View) getParent() : this;
/* 311 */               this.mMediaController.setAnchorView(anchorView);
/* 312 */               this.mMediaController.setEnabled(isInPlaybackState());
                    }
                }

                /* JADX INFO: renamed from: co.vine.android.player.OldSdkVideoView$2 */
/* 315 */       class C03922 implements MediaPlayer.OnVideoSizeChangedListener {
/* 316 */           C03922() {
                    }

                    @Override // android.media.MediaPlayer.OnVideoSizeChangedListener
/* 318 */           public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {
/* 319 */               OldSdkVideoView.access$302(OldSdkVideoView.this, mp.getVideoWidth());
/* 320 */               OldSdkVideoView.access$402(OldSdkVideoView.this, mp.getVideoHeight());
/* 321 */               if (OldSdkVideoView.access$300(OldSdkVideoView.this) != 0 && OldSdkVideoView.access$400(OldSdkVideoView.this) != 0) {
/* 322 */                   OldSdkVideoView.this.getHolder().setFixedSize(OldSdkVideoView.access$300(OldSdkVideoView.this), OldSdkVideoView.access$400(OldSdkVideoView.this));
/* 323 */                   OldSdkVideoView.this.requestLayout();
                        }
                    }
                }

                /* JADX INFO: renamed from: co.vine.android.player.OldSdkVideoView$3 */
/* 326 */       class C03933 implements MediaPlayer.OnPreparedListener {
/* 327 */           C03933() {
                    }

                    @Override // android.media.MediaPlayer.OnPreparedListener
/* 329 */           public void onPrepared(MediaPlayer mp) {
/* 330 */               OldSdkVideoView.access$502(OldSdkVideoView.this, 2);
/* 332 */               OldSdkVideoView.access$602(OldSdkVideoView.this, OldSdkVideoView.access$702(OldSdkVideoView.this, OldSdkVideoView.access$802(OldSdkVideoView.this, true)));
/* 334 */               if (OldSdkVideoView.access$900(OldSdkVideoView.this) != null) {
/* 335 */                   OldSdkVideoView.access$900(OldSdkVideoView.this).onPrepared(OldSdkVideoView.access$1000(OldSdkVideoView.this));
                        }
/* 337 */               if (OldSdkVideoView.access$1100(OldSdkVideoView.this) != null) {
/* 338 */                   OldSdkVideoView.access$1100(OldSdkVideoView.this).setEnabled(true);
                        }
/* 340 */               OldSdkVideoView.access$302(OldSdkVideoView.this, mp.getVideoWidth());
/* 341 */               OldSdkVideoView.access$402(OldSdkVideoView.this, mp.getVideoHeight());
/* 343 */               int seekToPosition = OldSdkVideoView.access$1200(OldSdkVideoView.this);
/* 344 */               if (seekToPosition != 0) {
/* 345 */                   OldSdkVideoView.this.seekTo(seekToPosition);
                        }
/* 347 */               if (OldSdkVideoView.access$300(OldSdkVideoView.this) != 0 && OldSdkVideoView.access$400(OldSdkVideoView.this) != 0) {
/* 349 */                   OldSdkVideoView.this.getHolder().setFixedSize(OldSdkVideoView.access$300(OldSdkVideoView.this), OldSdkVideoView.access$400(OldSdkVideoView.this));
/* 350 */                   if (OldSdkVideoView.access$1300(OldSdkVideoView.this) == OldSdkVideoView.access$300(OldSdkVideoView.this) && OldSdkVideoView.access$1400(OldSdkVideoView.this) == OldSdkVideoView.access$400(OldSdkVideoView.this)) {
/* 354 */                       if (OldSdkVideoView.access$1500(OldSdkVideoView.this) == 3) {
/* 355 */                           OldSdkVideoView.this.start();
/* 356 */                           if (OldSdkVideoView.access$1100(OldSdkVideoView.this) != null) {
/* 357 */                               OldSdkVideoView.access$1100(OldSdkVideoView.this).show();
/* 374 */                               return;
                                    }
/* 374 */                           return;
                                }
/* 359 */                       if (!OldSdkVideoView.this.isPlaying()) {
/* 361 */                           if ((seekToPosition != 0 || OldSdkVideoView.this.getCurrentPosition() > 0) && OldSdkVideoView.access$1100(OldSdkVideoView.this) != null) {
/* 363 */                               OldSdkVideoView.access$1100(OldSdkVideoView.this).show(0);
/* 374 */                               return;
                                    }
/* 374 */                           return;
                                }
/* 374 */                       return;
                            }
/* 374 */                   return;
                        }
/* 370 */               if (OldSdkVideoView.access$1500(OldSdkVideoView.this) == 3) {
/* 371 */                   OldSdkVideoView.this.start();
                        }
                    }
                }

                /* JADX INFO: renamed from: co.vine.android.player.OldSdkVideoView$4 */
/* 376 */       class C03944 implements MediaPlayer.OnCompletionListener {
/* 377 */           C03944() {
                    }

                    @Override // android.media.MediaPlayer.OnCompletionListener
/* 379 */           public void onCompletion(MediaPlayer mp) {
/* 380 */               OldSdkVideoView.access$502(OldSdkVideoView.this, 5);
/* 381 */               OldSdkVideoView.access$1502(OldSdkVideoView.this, 5);
/* 382 */               if (OldSdkVideoView.access$1100(OldSdkVideoView.this) != null) {
/* 383 */                   OldSdkVideoView.access$1100(OldSdkVideoView.this).hide();
                        }
/* 385 */               if (OldSdkVideoView.access$1600(OldSdkVideoView.this) != null) {
/* 386 */                   OldSdkVideoView.access$1600(OldSdkVideoView.this).onCompletion(OldSdkVideoView.this);
                        }
                    }
                }

                /* JADX INFO: renamed from: co.vine.android.player.OldSdkVideoView$5 */
/* 390 */       class C03955 implements MediaPlayer.OnInfoListener {
/* 391 */           C03955() {
                    }

                    @Override // android.media.MediaPlayer.OnInfoListener
/* 393 */           public boolean onInfo(MediaPlayer mp, int arg1, int arg2) {
/* 394 */               if (OldSdkVideoView.access$1700(OldSdkVideoView.this) != null) {
/* 395 */                   OldSdkVideoView.access$1700(OldSdkVideoView.this).onInfo(mp, arg1, arg2);
/* 397 */                   return true;
                        }
/* 397 */               return true;
                    }
                }

                /* JADX INFO: renamed from: co.vine.android.player.OldSdkVideoView$6 */
/* 400 */       class C03966 implements MediaPlayer.OnErrorListener {
/* 401 */           C03966() {
                    }

                    @Override // android.media.MediaPlayer.OnErrorListener
/* 403 */           public boolean onError(MediaPlayer mp, int framework_err, int impl_err) {
/* 404 */               SLog.m446d("Error: " + framework_err + "," + impl_err);
/* 405 */               OldSdkVideoView.access$502(OldSdkVideoView.this, -1);
/* 406 */               OldSdkVideoView.access$1502(OldSdkVideoView.this, -1);
/* 407 */               if (OldSdkVideoView.access$1100(OldSdkVideoView.this) != null) {
/* 408 */                   OldSdkVideoView.access$1100(OldSdkVideoView.this).hide();
                        }
/* 412 */               if (OldSdkVideoView.access$1800(OldSdkVideoView.this) == null || !OldSdkVideoView.access$1800(OldSdkVideoView.this).onError(OldSdkVideoView.this, framework_err, impl_err)) {
/* 418 */                   SLog.m455e("Cannot play this video.");
                        }
                        return true;
                    }
                }

                /* JADX INFO: renamed from: co.vine.android.player.OldSdkVideoView$7 */
/* 422 */       class C03977 implements MediaPlayer.OnBufferingUpdateListener {
/* 423 */           C03977() {
                    }

                    @Override // android.media.MediaPlayer.OnBufferingUpdateListener
/* 425 */           public void onBufferingUpdate(MediaPlayer mp, int percent) {
/* 426 */               OldSdkVideoView.access$1902(OldSdkVideoView.this, percent);
                    }
                }

                @Override // co.vine.android.player.VideoViewInterface
/* 436 */       public void setOnCompletionListener(VideoViewInterface.OnCompletionListener l) {
/* 437 */           this.mOnCompletionListener = l;
                }

                @Override // co.vine.android.player.VideoViewInterface
/* 448 */       public void setOnErrorListener(VideoViewInterface.OnErrorListener l) {
/* 449 */           this.mOnErrorListener = l;
                }

/* 458 */       public void setOnInfoListener(MediaPlayer.OnInfoListener l) {
/* 459 */           this.mOnInfoListener = l;
                }

                /* JADX INFO: renamed from: co.vine.android.player.OldSdkVideoView$8 */
/* 460 */       class SurfaceHolderCallbackC03988 implements SurfaceHolder.Callback {
/* 461 */           SurfaceHolderCallbackC03988() {
                    }

                    @Override // android.view.SurfaceHolder.Callback
/* 464 */           public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
/* 465 */               OldSdkVideoView.access$1302(OldSdkVideoView.this, w);
/* 466 */               OldSdkVideoView.access$1402(OldSdkVideoView.this, h);
/* 467 */               boolean isValidState = OldSdkVideoView.access$1500(OldSdkVideoView.this) == 3;
/* 468 */               boolean hasValidSize = OldSdkVideoView.access$300(OldSdkVideoView.this) == w && OldSdkVideoView.access$400(OldSdkVideoView.this) == h;
/* 469 */               if (OldSdkVideoView.access$1000(OldSdkVideoView.this) != null && isValidState && hasValidSize) {
/* 470 */                   if (OldSdkVideoView.access$1200(OldSdkVideoView.this) != 0) {
/* 471 */                       OldSdkVideoView.this.seekTo(OldSdkVideoView.access$1200(OldSdkVideoView.this));
                            }
/* 473 */                   OldSdkVideoView.this.start();
                        }
                    }

                    @Override // android.view.SurfaceHolder.Callback
/* 477 */           public void surfaceCreated(SurfaceHolder holder) {
/* 478 */               OldSdkVideoView.access$2002(OldSdkVideoView.this, holder);
/* 479 */               OldSdkVideoView.access$2100(OldSdkVideoView.this);
                    }

                    @Override // android.view.SurfaceHolder.Callback
/* 483 */           public void surfaceDestroyed(SurfaceHolder holder) {
/* 484 */               OldSdkVideoView.access$2002(OldSdkVideoView.this, null);
/* 485 */               if (OldSdkVideoView.access$1100(OldSdkVideoView.this) != null) {
                            OldSdkVideoView.access$1100(OldSdkVideoView.this).hide();
                        }
/* 486 */               OldSdkVideoView.access$2200(OldSdkVideoView.this, true);
                    }
                }

/* 493 */       private void release(boolean cleartargetstate) {
/* 494 */           if (this.mMediaPlayer != null) {
/* 495 */               this.mMediaPlayer.reset();
/* 496 */               this.mMediaPlayer.release();
/* 497 */               this.mMediaPlayer = null;
/* 498 */               this.mCurrentState = 0;
/* 499 */               if (cleartargetstate) {
/* 500 */                   this.mTargetState = 0;
                        }
                    }
                }

                @Override // android.view.View
/* 506 */       public boolean onTouchEvent(MotionEvent ev) {
/* 507 */           if (isInPlaybackState() && this.mMediaController != null) {
/* 508 */               toggleMediaControlsVisiblity();
/* 510 */               return false;
                    }
/* 510 */           return false;
                }

                @Override // android.view.View
/* 514 */       public boolean onTrackballEvent(MotionEvent ev) {
/* 515 */           if (isInPlaybackState() && this.mMediaController != null) {
/* 516 */               toggleMediaControlsVisiblity();
/* 518 */               return false;
                    }
/* 518 */           return false;
                }

                @Override // android.view.View, android.view.KeyEvent.Callback
/* 522 */       public boolean onKeyDown(int keyCode, KeyEvent event) {
/* 523 */           boolean isKeyCodeSupported = (keyCode == 4 || keyCode == 24 || keyCode == 25 || keyCode == 164 || keyCode == 82 || keyCode == 5 || keyCode == 6) ? false : true;
/* 530 */           if (isInPlaybackState() && isKeyCodeSupported && this.mMediaController != null) {
/* 531 */               if (keyCode == 79 || keyCode == 85) {
/* 533 */                   if (this.mMediaPlayer.isPlaying()) {
/* 534 */                       pause();
/* 535 */                       this.mMediaController.show();
                                return true;
                            }
/* 537 */                   start();
/* 538 */                   this.mMediaController.hide();
                            return true;
                        }
/* 541 */               if (keyCode == 126) {
/* 542 */                   if (this.mMediaPlayer.isPlaying()) {
                                return true;
                            }
/* 543 */                   start();
/* 544 */                   this.mMediaController.hide();
                            return true;
                        }
/* 547 */               if (keyCode == 86 || keyCode == 127) {
/* 549 */                   if (!this.mMediaPlayer.isPlaying()) {
                                return true;
                            }
/* 550 */                   pause();
/* 551 */                   this.mMediaController.show();
                            return true;
                        }
/* 555 */               toggleMediaControlsVisiblity();
                    }
/* 559 */           return super.onKeyDown(keyCode, event);
                }

/* 562 */       private void toggleMediaControlsVisiblity() {
/* 563 */           if (this.mMediaController.isShowing()) {
/* 564 */               this.mMediaController.hide();
                    } else {
/* 566 */               this.mMediaController.show();
                    }
                }

                @Override // android.widget.MediaController.MediaPlayerControl, co.vine.android.player.VideoViewInterface
/* 571 */       public void start() {
/* 572 */           if (isInPlaybackState()) {
/* 573 */               this.mMediaPlayer.start();
/* 574 */               this.mCurrentState = 3;
                    }
/* 576 */           this.mTargetState = 3;
                }

                @Override // android.widget.MediaController.MediaPlayerControl, co.vine.android.player.VideoViewInterface
/* 580 */       public void pause() {
/* 581 */           if (isInPlaybackState() && this.mMediaPlayer.isPlaying()) {
/* 583 */               this.mMediaPlayer.pause();
/* 584 */               this.mCurrentState = 4;
                    }
/* 587 */           this.mTargetState = 4;
                }

                @Override // co.vine.android.player.VideoViewInterface
/* 590 */       public void suspend() {
/* 591 */           release(false);
                }

                @Override // co.vine.android.player.VideoViewInterface
/* 594 */       public void resume() {
/* 595 */           this.mWasMuted = !this.mMuted;
/* 596 */           setMute(this.mMuted);
/* 597 */           if (isInPlaybackState()) {
/* 598 */               this.mMediaPlayer.start();
                    } else {
/* 600 */               openVideo();
                    }
                }

                @Override // android.widget.MediaController.MediaPlayerControl, co.vine.android.player.VideoViewInterface
/* 605 */       public int getDuration() {
/* 606 */           if (isInPlaybackState()) {
/* 607 */               return this.mMediaPlayer.getDuration();
                    }
                    return -1;
                }

                @Override // android.widget.MediaController.MediaPlayerControl, co.vine.android.player.VideoViewInterface
/* 614 */       public int getCurrentPosition() {
/* 615 */           if (isInPlaybackState()) {
/* 616 */               return this.mMediaPlayer.getCurrentPosition();
                    }
                    return 0;
                }

                @Override // android.widget.MediaController.MediaPlayerControl, co.vine.android.player.VideoViewInterface
/* 622 */       public void seekTo(int msec) {
/* 623 */           if (isInPlaybackState()) {
/* 624 */               this.mMediaPlayer.seekTo(msec);
/* 625 */               this.mSeekWhenPrepared = 0;
                    } else {
/* 627 */               this.mSeekWhenPrepared = msec;
                    }
                }

                @Override // android.widget.MediaController.MediaPlayerControl, co.vine.android.player.VideoViewInterface
/* 632 */       public boolean isPlaying() {
                    return isInPlaybackState() && this.mMediaPlayer.isPlaying();
                }

                @Override // co.vine.android.player.VideoViewInterface
/* 637 */       public boolean isPaused() {
                    return isInPlaybackState() && !this.mMediaPlayer.isPlaying();
                }

                @Override // co.vine.android.player.VideoViewInterface
/* 642 */       public boolean hasStarted() {
/* 643 */           return isInPlaybackState();
                }

                @Override // co.vine.android.player.VideoViewInterface
/* 647 */       public int getPlayingPosition() {
/* 648 */           if (!isInPlaybackState() || this.mMediaPlayer == null) {
                        return -1;
                    }
/* 649 */           return this.mMediaPlayer.getCurrentPosition();
                }

/* 655 */       public void onPrepared() {
/* 656 */           if (isInPlaybackState() && this.mCurrentState == 2 && this.mMediaPlayer != null) {
/* 658 */               this.mOnPreparedListener.onPrepared(this.mMediaPlayer);
                    }
                }

                @Override // android.widget.MediaController.MediaPlayerControl
/* 664 */       public int getBufferPercentage() {
/* 665 */           if (this.mMediaPlayer != null) {
/* 666 */               return this.mCurrentBufferPercentage;
                    }
                    return 0;
                }

                @Override // co.vine.android.player.VideoViewInterface
/* 671 */       public boolean isInPlaybackState() {
                    return (this.mMediaPlayer == null || this.mCurrentState == -1 || this.mCurrentState == 0 || this.mCurrentState == 1) ? false : true;
                }

                @Override // android.widget.MediaController.MediaPlayerControl
/* 679 */       public boolean canPause() {
/* 680 */           return this.mCanPause;
                }

                @Override // android.widget.MediaController.MediaPlayerControl
/* 684 */       public boolean canSeekBackward() {
/* 685 */           return this.mCanSeekBack;
                }

                @Override // android.widget.MediaController.MediaPlayerControl
/* 689 */       public boolean canSeekForward() {
/* 690 */           return this.mCanSeekForward;
                }

                @Override // android.widget.MediaController.MediaPlayerControl
/* 694 */       public int getAudioSessionId() {
/* 695 */           if (this.mAudioSession == 0) {
/* 696 */               MediaPlayer foo = new MediaPlayer();
/* 697 */               this.mAudioSession = foo.getAudioSessionId();
/* 698 */               foo.release();
                    }
/* 700 */           return this.mAudioSession;
                }

                @Override // co.vine.android.player.VideoViewInterface
/* 703 */       public void setOnPreparedListener(VideoViewInterface.OnPreparedListener listener) {
/* 704 */           this.mCustomOnPreParedListener = listener;
                }

                @Override // co.vine.android.player.VideoViewInterface
/* 708 */       public void setAutoPlayOnPrepared(boolean autoPlay) {
/* 709 */           this.mAutoPlay = autoPlay;
                }

                @Override // co.vine.android.player.VideoViewInterface
/* 713 */       public void setLooping(boolean b) {
/* 714 */           this.mLooping = true;
                }

/* 717 */       public void setMute(boolean mute) {
/* 718 */           this.mMuted = mute;
/* 719 */           if (isInPlaybackState() && this.mWasMuted != this.mMuted) {
/* 720 */               if (this.mMuted) {
/* 721 */                   this.mMediaPlayer.setVolume(0.0f, 0.0f);
                        } else {
/* 723 */                   this.mMediaPlayer.setVolume(1.0f, 1.0f);
                        }
                    }
/* 726 */           this.mWasMuted = this.mMuted;
                }

/* 729 */       public void setReportOpenVideoError(boolean reportVideoError) {
/* 730 */           this.mReportVideoError = reportVideoError;
                }

/* 734 */       public boolean isReportErrorOn() {
/* 735 */           return this.mReportVideoError;
                }

                @Override // co.vine.android.player.VideoViewInterface
/* 740 */       public void setCacheKey(CacheKey cacheKey) {
/* 741 */           this.mCacheKey = cacheKey;
                }

                @Override // co.vine.android.player.VideoViewInterface
/* 745 */       public CacheKey getCacheKey() {
/* 746 */           return this.mCacheKey;
                }
            }
