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
            
            
    public class OldSdkVideoView extends SurfaceView implements MediaController.MediaPlayerControl, VideoViewInterface {
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

        static  boolean access$000(OldSdkVideoView x0) {
            return x0.mAutoPlay;
                }

        static  boolean access$100(OldSdkVideoView x0) {
            return x0.mLooping;
                }

        static  MediaPlayer access$1000(OldSdkVideoView x0) {
            return x0.mMediaPlayer;
                }

        static  MediaController access$1100(OldSdkVideoView x0) {
            return x0.mMediaController;
                }

        static  int access$1200(OldSdkVideoView x0) {
            return x0.mSeekWhenPrepared;
                }

        static  int access$1300(OldSdkVideoView x0) {
            return x0.mSurfaceWidth;
                }

        static  int access$1302(OldSdkVideoView x0, int x1) {
            x0.mSurfaceWidth = x1;
                    return x1;
                }

        static  int access$1400(OldSdkVideoView x0) {
            return x0.mSurfaceHeight;
                }

        static  int access$1402(OldSdkVideoView x0, int x1) {
            x0.mSurfaceHeight = x1;
                    return x1;
                }

        static  int access$1500(OldSdkVideoView x0) {
            return x0.mTargetState;
                }

        static  int access$1502(OldSdkVideoView x0, int x1) {
            x0.mTargetState = x1;
                    return x1;
                }

        static  VideoViewInterface.OnCompletionListener access$1600(OldSdkVideoView x0) {
            return x0.mOnCompletionListener;
                }

        static  MediaPlayer.OnInfoListener access$1700(OldSdkVideoView x0) {
            return x0.mOnInfoListener;
                }

        static  VideoViewInterface.OnErrorListener access$1800(OldSdkVideoView x0) {
            return x0.mOnErrorListener;
                }

        static  int access$1902(OldSdkVideoView x0, int x1) {
            x0.mCurrentBufferPercentage = x1;
                    return x1;
                }

        static  VideoViewInterface.OnPreparedListener access$200(OldSdkVideoView x0) {
            return x0.mCustomOnPreParedListener;
                }

        static  SurfaceHolder access$2002(OldSdkVideoView x0, SurfaceHolder x1) {
            x0.mSurfaceHolder = x1;
                    return x1;
                }

        static  void access$2100(OldSdkVideoView x0) {
            x0.openVideo();
                }

        static  void access$2200(OldSdkVideoView x0, boolean x1) {
            x0.release(x1);
                }

        static  int access$300(OldSdkVideoView x0) {
            return x0.mVideoWidth;
                }

        static  int access$302(OldSdkVideoView x0, int x1) {
            x0.mVideoWidth = x1;
                    return x1;
                }

        static  int access$400(OldSdkVideoView x0) {
            return x0.mVideoHeight;
                }

        static  int access$402(OldSdkVideoView x0, int x1) {
            x0.mVideoHeight = x1;
                    return x1;
                }

        static  int access$502(OldSdkVideoView x0, int x1) {
            x0.mCurrentState = x1;
                    return x1;
                }

        static  boolean access$602(OldSdkVideoView x0, boolean x1) {
            x0.mCanPause = x1;
                    return x1;
                }

        static  boolean access$702(OldSdkVideoView x0, boolean x1) {
            x0.mCanSeekBack = x1;
                    return x1;
                }

        static  boolean access$802(OldSdkVideoView x0, boolean x1) {
            x0.mCanSeekForward = x1;
                    return x1;
                }

        static  MediaPlayer.OnPreparedListener access$900(OldSdkVideoView x0) {
            return x0.mOnPreparedListener;
                }

                
        class C03911 implements MediaPlayer.OnPreparedListener {
            C03911() {
                    }

                    @Override 
            public void onPrepared(MediaPlayer mp) {
                SLog.m446d("Videoview prepared.");
                if (OldSdkVideoView.access$000(OldSdkVideoView.this)) {
                    mp.start();
                        }
                mp.setLooping(OldSdkVideoView.access$100(OldSdkVideoView.this));
                if (OldSdkVideoView.access$200(OldSdkVideoView.this) != null) {
                    OldSdkVideoView.access$200(OldSdkVideoView.this).onPrepared(OldSdkVideoView.this);
                        }
                    }
                }

        public OldSdkVideoView(Context context) {
            super(context);
            this.mOnPreparedListener = new C03911();
            this.mCurrentState = 0;
            this.mTargetState = 0;
            this.mSurfaceHolder = null;
            this.mMediaPlayer = null;
            this.mMuted = false;
            this.mWasMuted = false;
           this.mSizeChangedListener = new C03922();
           this.mPreparedListener = new C03933();
           this.mCompletionListener = new C03944();
           this.mInfoListener = new C03955();
           this.mErrorListener = new C03966();
           this.mBufferingUpdateListener = new C03977();
           this.mSHCallback = new SurfaceHolderCallbackC03988();
            initVideoView();
                }

        public OldSdkVideoView(Context context, AttributeSet attrs) {
            this(context, attrs, 0);
            initVideoView();
                }

       public OldSdkVideoView(Context context, AttributeSet attrs, int defStyle) {
           super(context, attrs, defStyle);
            this.mOnPreparedListener = new C03911();
            this.mCurrentState = 0;
            this.mTargetState = 0;
            this.mSurfaceHolder = null;
            this.mMediaPlayer = null;
            this.mMuted = false;
            this.mWasMuted = false;
           this.mSizeChangedListener = new C03922();
           this.mPreparedListener = new C03933();
           this.mCompletionListener = new C03944();
           this.mInfoListener = new C03955();
           this.mErrorListener = new C03966();
           this.mBufferingUpdateListener = new C03977();
           this.mSHCallback = new SurfaceHolderCallbackC03988();
           initVideoView();
                }

                @Override 
       protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
           int width = getDefaultSize(this.mVideoWidth, widthMeasureSpec);
           int height = getDefaultSize(this.mVideoHeight, heightMeasureSpec);
           if (this.mVideoWidth > 0 && this.mVideoHeight > 0) {
               int widthSpecMode = View.MeasureSpec.getMode(widthMeasureSpec);
               int widthSpecSize = View.MeasureSpec.getSize(widthMeasureSpec);
               int heightSpecMode = View.MeasureSpec.getMode(heightMeasureSpec);
               int heightSpecSize = View.MeasureSpec.getSize(heightMeasureSpec);
               if (widthSpecMode == 1073741824 && heightSpecMode == 1073741824) {
                   width = widthSpecSize;
                   height = heightSpecSize;
                   if (this.mVideoWidth * height < this.mVideoHeight * width) {
                       width = (this.mVideoWidth * height) / this.mVideoHeight;
                            } else if (this.mVideoWidth * height > this.mVideoHeight * width) {
                       height = (this.mVideoHeight * width) / this.mVideoWidth;
                            }
                        } else if (widthSpecMode == 1073741824) {
                   width = widthSpecSize;
                   height = (this.mVideoHeight * width) / this.mVideoWidth;
                   if (heightSpecMode == Integer.MIN_VALUE && height > heightSpecSize) {
                       height = heightSpecSize;
                            }
                        } else if (heightSpecMode == 1073741824) {
                   height = heightSpecSize;
                   width = (this.mVideoWidth * height) / this.mVideoHeight;
                   if (widthSpecMode == Integer.MIN_VALUE && width > widthSpecSize) {
                       width = widthSpecSize;
                            }
                        } else {
                   width = this.mVideoWidth;
                   height = this.mVideoHeight;
                   if (heightSpecMode == Integer.MIN_VALUE && height > heightSpecSize) {
                       height = heightSpecSize;
                       width = (this.mVideoWidth * height) / this.mVideoHeight;
                            }
                   if (widthSpecMode == Integer.MIN_VALUE && width > widthSpecSize) {
                       width = widthSpecSize;
                       height = (this.mVideoHeight * width) / this.mVideoWidth;
                            }
                        }
                    }
           setMeasuredDimension(width, height);
                }

       public int resolveAdjustedSize(int desiredSize, int measureSpec) {
           return getDefaultSize(desiredSize, measureSpec);
                }

       private void initVideoView() {
           this.mVideoWidth = 0;
           this.mVideoHeight = 0;
           getHolder().addCallback(this.mSHCallback);
           getHolder().setType(3);
           setFocusable(true);
           setFocusableInTouchMode(true);
           requestFocus();
           this.mCurrentState = 0;
           this.mTargetState = 0;
                }

                @Override 
       public void setVideoPath(String path) {
           setVideoURI(Uri.parse(VineVideoProvider.CONTENT_AUTHORITY + path));
                }

                @Override 
       public void setVideoPathDirect(String path) {
           setVideoURI(Uri.parse(path));
                }

       public void setVideoURI(Uri uri) {
           this.mUri = uri;
           this.mSeekWhenPrepared = 0;
           openVideo();
           requestLayout();
           invalidate();
                }

       public void removeVideoUri() {
           this.mUri = null;
           this.mSeekWhenPrepared = 0;
                }

                @Override 
       public void stopPlayback() {
           if (this.mMediaPlayer != null) {
               SLog.m446d("Playback stopped.");
                        try {
                   this.mMediaPlayer.stop();
                        } catch (IllegalStateException e) {
                        }
                        try {
                   this.mMediaPlayer.reset();
                        } catch (IllegalStateException e2) {
                        }
                        try {
                   this.mMediaPlayer.release();
                        } catch (IllegalStateException e3) {
                        }
               this.mMediaPlayer = null;
               this.mCurrentState = 0;
               this.mTargetState = 0;
                    }
                }

                
       private void openVideo() {
           if (this.mUri != null && this.mSurfaceHolder != null) {
               Intent i = new Intent("com.android.music.musicservicecommand");
               i.putExtra("command", "pause");
               getContext().sendBroadcast(i);
               release(false);
                        try {
                   this.mMediaPlayer = new MediaPlayer();
                   Context context = getContext();
                   if (this.mAudioSession != 0) {
                       this.mMediaPlayer.setAudioSessionId(this.mAudioSession);
                            } else {
                       this.mAudioSession = this.mMediaPlayer.getAudioSessionId();
                            }
                   this.mMediaPlayer.setOnPreparedListener(this.mPreparedListener);
                   this.mMediaPlayer.setOnVideoSizeChangedListener(this.mSizeChangedListener);
                   this.mMediaPlayer.setOnCompletionListener(this.mCompletionListener);
                   this.mMediaPlayer.setOnErrorListener(this.mErrorListener);
                   this.mMediaPlayer.setOnInfoListener(this.mInfoListener);
                   this.mMediaPlayer.setOnBufferingUpdateListener(this.mBufferingUpdateListener);
                   this.mCurrentBufferPercentage = 0;
                   this.mMediaPlayer.setDataSource(context, this.mUri);
                   this.mMediaPlayer.setDisplay(this.mSurfaceHolder);
                   this.mMediaPlayer.setAudioStreamType(3);
                   this.mMediaPlayer.setScreenOnWhilePlaying(true);
                   this.mMediaPlayer.prepareAsync();
                   if (this.mMuted) {
                       this.mMediaPlayer.setVolume(0.0f, 0.0f);
                            }
                   this.mCurrentState = 1;
                   attachMediaController();
                        } catch (IOException e) {
                   if (this.mReportVideoError) {
                       CrashUtil.logException(e, "Unable to open content {}, {}.", this.mUri);
                            }
                   this.mCurrentState = -1;
                   this.mTargetState = -1;
                   this.mErrorListener.onError(this.mMediaPlayer, 1, 0);
                        } catch (IllegalArgumentException e2) {
                   if (this.mReportVideoError) {
                       CrashUtil.logException(e2, "Unable to open content {}, {}.", this.mUri);
                            }
                   this.mCurrentState = -1;
                   this.mTargetState = -1;
                   this.mErrorListener.onError(this.mMediaPlayer, 1, 0);
                        } catch (IllegalStateException ex) {
                   this.mCurrentState = -1;
                   this.mTargetState = -1;
                   CrashUtil.logException(ex, "IllegalState error {}, {}.", this.mUri);
                        }
                    }
                }

       public void setMediaController(MediaController controller) {
           if (this.mMediaController != null) {
               this.mMediaController.hide();
                    }
           this.mMediaController = controller;
           attachMediaController();
                }

       private void attachMediaController() {
           if (this.mMediaPlayer != null && this.mMediaController != null) {
               this.mMediaController.setMediaPlayer(this);
               View anchorView = getParent() instanceof View ? (View) getParent() : this;
               this.mMediaController.setAnchorView(anchorView);
               this.mMediaController.setEnabled(isInPlaybackState());
                    }
                }

                
       class C03922 implements MediaPlayer.OnVideoSizeChangedListener {
           C03922() {
                    }

                    @Override 
           public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {
               OldSdkVideoView.access$302(OldSdkVideoView.this, mp.getVideoWidth());
               OldSdkVideoView.access$402(OldSdkVideoView.this, mp.getVideoHeight());
               if (OldSdkVideoView.access$300(OldSdkVideoView.this) != 0 && OldSdkVideoView.access$400(OldSdkVideoView.this) != 0) {
                   OldSdkVideoView.this.getHolder().setFixedSize(OldSdkVideoView.access$300(OldSdkVideoView.this), OldSdkVideoView.access$400(OldSdkVideoView.this));
                   OldSdkVideoView.this.requestLayout();
                        }
                    }
                }

                
       class C03933 implements MediaPlayer.OnPreparedListener {
           C03933() {
                    }

                    @Override 
           public void onPrepared(MediaPlayer mp) {
               OldSdkVideoView.access$502(OldSdkVideoView.this, 2);
               OldSdkVideoView.access$602(OldSdkVideoView.this, OldSdkVideoView.access$702(OldSdkVideoView.this, OldSdkVideoView.access$802(OldSdkVideoView.this, true)));
               if (OldSdkVideoView.access$900(OldSdkVideoView.this) != null) {
                   OldSdkVideoView.access$900(OldSdkVideoView.this).onPrepared(OldSdkVideoView.access$1000(OldSdkVideoView.this));
                        }
               if (OldSdkVideoView.access$1100(OldSdkVideoView.this) != null) {
                   OldSdkVideoView.access$1100(OldSdkVideoView.this).setEnabled(true);
                        }
               OldSdkVideoView.access$302(OldSdkVideoView.this, mp.getVideoWidth());
               OldSdkVideoView.access$402(OldSdkVideoView.this, mp.getVideoHeight());
               int seekToPosition = OldSdkVideoView.access$1200(OldSdkVideoView.this);
               if (seekToPosition != 0) {
                   OldSdkVideoView.this.seekTo(seekToPosition);
                        }
               if (OldSdkVideoView.access$300(OldSdkVideoView.this) != 0 && OldSdkVideoView.access$400(OldSdkVideoView.this) != 0) {
                   OldSdkVideoView.this.getHolder().setFixedSize(OldSdkVideoView.access$300(OldSdkVideoView.this), OldSdkVideoView.access$400(OldSdkVideoView.this));
                   if (OldSdkVideoView.access$1300(OldSdkVideoView.this) == OldSdkVideoView.access$300(OldSdkVideoView.this) && OldSdkVideoView.access$1400(OldSdkVideoView.this) == OldSdkVideoView.access$400(OldSdkVideoView.this)) {
                       if (OldSdkVideoView.access$1500(OldSdkVideoView.this) == 3) {
                           OldSdkVideoView.this.start();
                           if (OldSdkVideoView.access$1100(OldSdkVideoView.this) != null) {
                               OldSdkVideoView.access$1100(OldSdkVideoView.this).show();
                               return;
                                    }
                           return;
                                }
                       if (!OldSdkVideoView.this.isPlaying()) {
                           if ((seekToPosition != 0 || OldSdkVideoView.this.getCurrentPosition() > 0) && OldSdkVideoView.access$1100(OldSdkVideoView.this) != null) {
                               OldSdkVideoView.access$1100(OldSdkVideoView.this).show(0);
                               return;
                                    }
                           return;
                                }
                       return;
                            }
                   return;
                        }
               if (OldSdkVideoView.access$1500(OldSdkVideoView.this) == 3) {
                   OldSdkVideoView.this.start();
                        }
                    }
                }

                
       class C03944 implements MediaPlayer.OnCompletionListener {
           C03944() {
                    }

                    @Override 
           public void onCompletion(MediaPlayer mp) {
               OldSdkVideoView.access$502(OldSdkVideoView.this, 5);
               OldSdkVideoView.access$1502(OldSdkVideoView.this, 5);
               if (OldSdkVideoView.access$1100(OldSdkVideoView.this) != null) {
                   OldSdkVideoView.access$1100(OldSdkVideoView.this).hide();
                        }
               if (OldSdkVideoView.access$1600(OldSdkVideoView.this) != null) {
                   OldSdkVideoView.access$1600(OldSdkVideoView.this).onCompletion(OldSdkVideoView.this);
                        }
                    }
                }

                
       class C03955 implements MediaPlayer.OnInfoListener {
           C03955() {
                    }

                    @Override 
           public boolean onInfo(MediaPlayer mp, int arg1, int arg2) {
               if (OldSdkVideoView.access$1700(OldSdkVideoView.this) != null) {
                   OldSdkVideoView.access$1700(OldSdkVideoView.this).onInfo(mp, arg1, arg2);
                   return true;
                        }
               return true;
                    }
                }

                
       class C03966 implements MediaPlayer.OnErrorListener {
           C03966() {
                    }

                    @Override 
           public boolean onError(MediaPlayer mp, int framework_err, int impl_err) {
               SLog.m446d("Error: " + framework_err + "," + impl_err);
               OldSdkVideoView.access$502(OldSdkVideoView.this, -1);
               OldSdkVideoView.access$1502(OldSdkVideoView.this, -1);
               if (OldSdkVideoView.access$1100(OldSdkVideoView.this) != null) {
                   OldSdkVideoView.access$1100(OldSdkVideoView.this).hide();
                        }
               if (OldSdkVideoView.access$1800(OldSdkVideoView.this) == null || !OldSdkVideoView.access$1800(OldSdkVideoView.this).onError(OldSdkVideoView.this, framework_err, impl_err)) {
                   SLog.m455e("Cannot play this video.");
                        }
                        return true;
                    }
                }

                
       class C03977 implements MediaPlayer.OnBufferingUpdateListener {
           C03977() {
                    }

                    @Override 
           public void onBufferingUpdate(MediaPlayer mp, int percent) {
               OldSdkVideoView.access$1902(OldSdkVideoView.this, percent);
                    }
                }

                @Override 
       public void setOnCompletionListener(VideoViewInterface.OnCompletionListener l) {
           this.mOnCompletionListener = l;
                }

                @Override 
       public void setOnErrorListener(VideoViewInterface.OnErrorListener l) {
           this.mOnErrorListener = l;
                }

       public void setOnInfoListener(MediaPlayer.OnInfoListener l) {
           this.mOnInfoListener = l;
                }

                
       class SurfaceHolderCallbackC03988 implements SurfaceHolder.Callback {
           SurfaceHolderCallbackC03988() {
                    }

                    @Override 
           public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
               OldSdkVideoView.access$1302(OldSdkVideoView.this, w);
               OldSdkVideoView.access$1402(OldSdkVideoView.this, h);
               boolean isValidState = OldSdkVideoView.access$1500(OldSdkVideoView.this) == 3;
               boolean hasValidSize = OldSdkVideoView.access$300(OldSdkVideoView.this) == w && OldSdkVideoView.access$400(OldSdkVideoView.this) == h;
               if (OldSdkVideoView.access$1000(OldSdkVideoView.this) != null && isValidState && hasValidSize) {
                   if (OldSdkVideoView.access$1200(OldSdkVideoView.this) != 0) {
                       OldSdkVideoView.this.seekTo(OldSdkVideoView.access$1200(OldSdkVideoView.this));
                            }
                   OldSdkVideoView.this.start();
                        }
                    }

                    @Override 
           public void surfaceCreated(SurfaceHolder holder) {
               OldSdkVideoView.access$2002(OldSdkVideoView.this, holder);
               OldSdkVideoView.access$2100(OldSdkVideoView.this);
                    }

                    @Override 
           public void surfaceDestroyed(SurfaceHolder holder) {
               OldSdkVideoView.access$2002(OldSdkVideoView.this, null);
               if (OldSdkVideoView.access$1100(OldSdkVideoView.this) != null) {
                            OldSdkVideoView.access$1100(OldSdkVideoView.this).hide();
                        }
               OldSdkVideoView.access$2200(OldSdkVideoView.this, true);
                    }
                }

       private void release(boolean cleartargetstate) {
           if (this.mMediaPlayer != null) {
               this.mMediaPlayer.reset();
               this.mMediaPlayer.release();
               this.mMediaPlayer = null;
               this.mCurrentState = 0;
               if (cleartargetstate) {
                   this.mTargetState = 0;
                        }
                    }
                }

                @Override 
       public boolean onTouchEvent(MotionEvent ev) {
           if (isInPlaybackState() && this.mMediaController != null) {
               toggleMediaControlsVisiblity();
               return false;
                    }
           return false;
                }

                @Override 
       public boolean onTrackballEvent(MotionEvent ev) {
           if (isInPlaybackState() && this.mMediaController != null) {
               toggleMediaControlsVisiblity();
               return false;
                    }
           return false;
                }

                @Override 
       public boolean onKeyDown(int keyCode, KeyEvent event) {
           boolean isKeyCodeSupported = (keyCode == 4 || keyCode == 24 || keyCode == 25 || keyCode == 164 || keyCode == 82 || keyCode == 5 || keyCode == 6) ? false : true;
           if (isInPlaybackState() && isKeyCodeSupported && this.mMediaController != null) {
               if (keyCode == 79 || keyCode == 85) {
                   if (this.mMediaPlayer.isPlaying()) {
                       pause();
                       this.mMediaController.show();
                                return true;
                            }
                   start();
                   this.mMediaController.hide();
                            return true;
                        }
               if (keyCode == 126) {
                   if (this.mMediaPlayer.isPlaying()) {
                                return true;
                            }
                   start();
                   this.mMediaController.hide();
                            return true;
                        }
               if (keyCode == 86 || keyCode == 127) {
                   if (!this.mMediaPlayer.isPlaying()) {
                                return true;
                            }
                   pause();
                   this.mMediaController.show();
                            return true;
                        }
               toggleMediaControlsVisiblity();
                    }
           return super.onKeyDown(keyCode, event);
                }

       private void toggleMediaControlsVisiblity() {
           if (this.mMediaController.isShowing()) {
               this.mMediaController.hide();
                    } else {
               this.mMediaController.show();
                    }
                }

                @Override 
       public void start() {
           if (isInPlaybackState()) {
               this.mMediaPlayer.start();
               this.mCurrentState = 3;
                    }
           this.mTargetState = 3;
                }

                @Override 
       public void pause() {
           if (isInPlaybackState() && this.mMediaPlayer.isPlaying()) {
               this.mMediaPlayer.pause();
               this.mCurrentState = 4;
                    }
           this.mTargetState = 4;
                }

                @Override 
       public void suspend() {
           release(false);
                }

                @Override 
       public void resume() {
           this.mWasMuted = !this.mMuted;
           setMute(this.mMuted);
           if (isInPlaybackState()) {
               this.mMediaPlayer.start();
                    } else {
               openVideo();
                    }
                }

                @Override 
       public int getDuration() {
           if (isInPlaybackState()) {
               return this.mMediaPlayer.getDuration();
                    }
                    return -1;
                }

                @Override 
       public int getCurrentPosition() {
           if (isInPlaybackState()) {
               return this.mMediaPlayer.getCurrentPosition();
                    }
                    return 0;
                }

                @Override 
       public void seekTo(int msec) {
           if (isInPlaybackState()) {
               this.mMediaPlayer.seekTo(msec);
               this.mSeekWhenPrepared = 0;
                    } else {
               this.mSeekWhenPrepared = msec;
                    }
                }

                @Override 
       public boolean isPlaying() {
                    return isInPlaybackState() && this.mMediaPlayer.isPlaying();
                }

                @Override 
       public boolean isPaused() {
                    return isInPlaybackState() && !this.mMediaPlayer.isPlaying();
                }

                @Override 
       public boolean hasStarted() {
           return isInPlaybackState();
                }

                @Override 
       public int getPlayingPosition() {
           if (!isInPlaybackState() || this.mMediaPlayer == null) {
                        return -1;
                    }
           return this.mMediaPlayer.getCurrentPosition();
                }

       public void onPrepared() {
           if (isInPlaybackState() && this.mCurrentState == 2 && this.mMediaPlayer != null) {
               this.mOnPreparedListener.onPrepared(this.mMediaPlayer);
                    }
                }

                @Override 
       public int getBufferPercentage() {
           if (this.mMediaPlayer != null) {
               return this.mCurrentBufferPercentage;
                    }
                    return 0;
                }

                @Override 
       public boolean isInPlaybackState() {
                    return (this.mMediaPlayer == null || this.mCurrentState == -1 || this.mCurrentState == 0 || this.mCurrentState == 1) ? false : true;
                }

                @Override 
       public boolean canPause() {
           return this.mCanPause;
                }

                @Override 
       public boolean canSeekBackward() {
           return this.mCanSeekBack;
                }

                @Override 
       public boolean canSeekForward() {
           return this.mCanSeekForward;
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

                @Override 
       public void setOnPreparedListener(VideoViewInterface.OnPreparedListener listener) {
           this.mCustomOnPreParedListener = listener;
                }

                @Override 
       public void setAutoPlayOnPrepared(boolean autoPlay) {
           this.mAutoPlay = autoPlay;
                }

                @Override 
       public void setLooping(boolean b) {
           this.mLooping = true;
                }

       public void setMute(boolean mute) {
           this.mMuted = mute;
           if (isInPlaybackState() && this.mWasMuted != this.mMuted) {
               if (this.mMuted) {
                   this.mMediaPlayer.setVolume(0.0f, 0.0f);
                        } else {
                   this.mMediaPlayer.setVolume(1.0f, 1.0f);
                        }
                    }
           this.mWasMuted = this.mMuted;
                }

       public void setReportOpenVideoError(boolean reportVideoError) {
           this.mReportVideoError = reportVideoError;
                }

       public boolean isReportErrorOn() {
           return this.mReportVideoError;
                }

                @Override 
       public void setCacheKey(CacheKey cacheKey) {
           this.mCacheKey = cacheKey;
                }

                @Override 
       public CacheKey getCacheKey() {
           return this.mCacheKey;
                }
            }
