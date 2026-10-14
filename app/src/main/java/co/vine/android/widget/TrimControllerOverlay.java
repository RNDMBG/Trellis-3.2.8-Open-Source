            package co.vine.android.widget;

            import android.animation.Animator;
            import android.animation.ObjectAnimator;
            import android.content.Context;
            import android.view.MotionEvent;
            import android.view.View;
            import android.view.ViewGroup;
            import android.widget.FrameLayout;
            import android.widget.ImageView;
            import android.widget.ProgressBar;
            import android.widget.RelativeLayout;
            import co.vine.android.C0257R;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 16 */    public class TrimControllerOverlay extends FrameLayout implements View.OnClickListener, TrimTimeBar.Listener {
                private OverlayListener mListener;
                private final ImageView mPlayButton;
                private final ProgressBar mProgressBar;
                private State mState;
                private TrimTimeBar mTimeBar;
                private int mVideoViewHeight;

                public interface OverlayListener {
                    void onPlayPause();

                    void onReplay();

                    void onSeekEnd(int i, int i2, int i3);

                    void onSeekMove(int i);

                    void onSeekStart();
                }

                protected enum State {
                    PLAYING,
                    PAUSED,
                    ENDED,
                    ERROR,
                    LOADING
                }

/* 17 */        static /* synthetic */ void access$000(TrimControllerOverlay x0) {
/* 18 */            x0.hidePlayButtonIfPlaying();
                }

/* 36 */        public TrimControllerOverlay(Context context, int videoViewHeight) {
/* 37 */            super(context);
/* 38 */            this.mVideoViewHeight = videoViewHeight;
/* 40 */            this.mState = State.LOADING;
/* 41 */            ViewGroup.LayoutParams wrapContent = new FrameLayout.LayoutParams(-2, -2);
/* 44 */            this.mTimeBar = new TrimTimeBar(context, this);
/* 45 */            addView(this.mTimeBar, wrapContent);
/* 47 */            ProgressBar spinner = new ProgressBar(context);
/* 48 */            spinner.setIndeterminate(true);
/* 49 */            addView(spinner, wrapContent);
/* 50 */            this.mProgressBar = spinner;
/* 52 */            ImageView playPauseView = new ImageView(context);
/* 53 */            playPauseView.setImageResource(C0257R.drawable.btn_import_play);
/* 54 */            playPauseView.setScaleType(ImageView.ScaleType.CENTER);
/* 55 */            playPauseView.setFocusable(true);
/* 56 */            playPauseView.setClickable(true);
/* 57 */            playPauseView.setOnClickListener(this);
/* 58 */            addView(playPauseView, wrapContent);
/* 59 */            this.mPlayButton = playPauseView;
/* 61 */            setLayoutParams(new RelativeLayout.LayoutParams(-1, -1));
                }

/* 65 */        public void setListener(OverlayListener listener) {
/* 66 */            this.mListener = listener;
                }

/* 69 */        private void hidePlayButtonIfPlaying() {
/* 70 */            if (this.mState == State.PLAYING) {
/* 71 */                this.mPlayButton.setVisibility(4);
                    }
/* 73 */            this.mPlayButton.setAlpha(1.0f);
                }

/* 76 */        public void showPlaying() {
/* 77 */            this.mState = State.PLAYING;
/* 78 */            showMainView(this.mPlayButton);
/* 80 */            ObjectAnimator anim = ObjectAnimator.ofFloat(this.mPlayButton, "alpha", 1.0f, 0.0f);
/* 81 */            anim.setDuration(200L);
/* 82 */            anim.start();
/* 83 */            anim.addListener(new C05741());
                }

                /* JADX INFO: renamed from: co.vine.android.widget.TrimControllerOverlay$1 */
/* 81 */        class C05741 implements Animator.AnimatorListener {
/* 82 */            C05741() {
                    }

                    @Override // android.animation.Animator.AnimatorListener
/* 85 */            public void onAnimationStart(Animator animation) {
                    }

                    @Override // android.animation.Animator.AnimatorListener
/* 89 */            public void onAnimationEnd(Animator animation) {
/* 90 */                TrimControllerOverlay.access$000(TrimControllerOverlay.this);
                    }

                    @Override // android.animation.Animator.AnimatorListener
/* 94 */            public void onAnimationCancel(Animator animation) {
/* 95 */                TrimControllerOverlay.access$000(TrimControllerOverlay.this);
                    }

                    @Override // android.animation.Animator.AnimatorListener
/* 99 */            public void onAnimationRepeat(Animator animation) {
                    }
                }

/* 104 */       public void showPaused() {
/* 105 */           this.mState = State.PAUSED;
/* 106 */           showMainView(this.mPlayButton);
                }

/* 109 */       public void showEnded() {
/* 110 */           this.mState = State.ENDED;
/* 111 */           showMainView(this.mPlayButton);
                }

/* 114 */       public void showLoading() {
/* 115 */           this.mState = State.LOADING;
/* 116 */           showMainView(this.mProgressBar);
                }

/* 119 */       public void setTimes(int currentTime, int duration, int trimStartTime, int trimEndTime) {
/* 120 */           this.mTimeBar.setTime(currentTime, duration, trimStartTime, trimEndTime);
                }

/* 123 */       private void showMainView(View view) {
/* 124 */           this.mProgressBar.setVisibility(view == this.mProgressBar ? 0 : 4);
/* 125 */           this.mPlayButton.setVisibility(view != this.mPlayButton ? 4 : 0);
                }

                @Override // android.view.View.OnClickListener
/* 129 */       public void onClick(View view) {
/* 130 */           if (this.mListener != null && view == this.mPlayButton) {
/* 132 */               if (this.mState == State.ENDED) {
/* 133 */                   this.mListener.onReplay();
                        } else if (this.mState == State.PAUSED || this.mState == State.PLAYING) {
/* 135 */                   this.mListener.onPlayPause();
                        }
                    }
                }

                @Override // android.view.View
/* 142 */       public boolean onTouchEvent(MotionEvent event) {
/* 143 */           if (!super.onTouchEvent(event)) {
                        switch (event.getAction()) {
                            case 0:
/* 151 */                       if (this.mState == State.PLAYING || this.mState == State.PAUSED) {
/* 152 */                           this.mListener.onPlayPause();
                                    break;
                                } else {
/* 153 */                           if (this.mState == State.ENDED) {
/* 154 */                               this.mListener.onReplay();
                                    }
                                    break;
                                }
                            default:
                                return true;
                        }
                    }
                    return true;
                }

                @Override // android.widget.FrameLayout, android.view.View
/* 164 */       protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
/* 165 */           int heightSpec = this.mVideoViewHeight > 0 ? View.MeasureSpec.makeMeasureSpec(this.mVideoViewHeight, 1073741824) : heightMeasureSpec;
/* 167 */           super.onMeasure(widthMeasureSpec, heightSpec);
/* 168 */           measureChildren(widthMeasureSpec, heightMeasureSpec);
                }

                @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
/* 172 */       protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
/* 173 */           int w = right - left;
/* 174 */           int y = top + this.mVideoViewHeight;
/* 176 */           this.mTimeBar.layout(0, y - this.mTimeBar.getPreferredHeight(), w, y);
/* 178 */           View playView = this.mPlayButton;
/* 179 */           int centeredLeft = (w - playView.getMeasuredWidth()) / 2;
/* 180 */           int centeredTop = (y - playView.getMeasuredHeight()) / 2;
/* 181 */           int centeredRight = centeredLeft + playView.getMeasuredWidth();
/* 182 */           int centeredBottom = centeredTop + playView.getMeasuredHeight();
/* 183 */           playView.layout(centeredLeft, centeredTop, centeredRight, centeredBottom);
                }

                @Override // co.vine.android.widget.TrimTimeBar.Listener
/* 188 */       public void onScrubbingStart() {
/* 189 */           this.mListener.onSeekStart();
                }

                @Override // co.vine.android.widget.TrimTimeBar.Listener
/* 193 */       public void onScrubbingMove(int time) {
/* 194 */           this.mListener.onSeekMove(time);
                }

                @Override // co.vine.android.widget.TrimTimeBar.Listener
/* 198 */       public void onScrubbingEnd(int time, int trimStartTime, int trimEndTime) {
/* 199 */           this.mListener.onSeekEnd(time, trimStartTime, trimEndTime);
                }
            }
