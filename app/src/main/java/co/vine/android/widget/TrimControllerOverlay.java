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
            
            
    public class TrimControllerOverlay extends FrameLayout implements View.OnClickListener, TrimTimeBar.Listener {
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

        static  void access$000(TrimControllerOverlay x0) {
            x0.hidePlayButtonIfPlaying();
                }

        public TrimControllerOverlay(Context context, int videoViewHeight) {
            super(context);
            this.mVideoViewHeight = videoViewHeight;
            this.mState = State.LOADING;
            ViewGroup.LayoutParams wrapContent = new FrameLayout.LayoutParams(-2, -2);
            this.mTimeBar = new TrimTimeBar(context, this);
            addView(this.mTimeBar, wrapContent);
            ProgressBar spinner = new ProgressBar(context);
            spinner.setIndeterminate(true);
            addView(spinner, wrapContent);
            this.mProgressBar = spinner;
            ImageView playPauseView = new ImageView(context);
            playPauseView.setImageResource(C0257R.drawable.btn_import_play);
            playPauseView.setScaleType(ImageView.ScaleType.CENTER);
            playPauseView.setFocusable(true);
            playPauseView.setClickable(true);
            playPauseView.setOnClickListener(this);
            addView(playPauseView, wrapContent);
            this.mPlayButton = playPauseView;
            setLayoutParams(new RelativeLayout.LayoutParams(-1, -1));
                }

        public void setListener(OverlayListener listener) {
            this.mListener = listener;
                }

        private void hidePlayButtonIfPlaying() {
            if (this.mState == State.PLAYING) {
                this.mPlayButton.setVisibility(4);
                    }
            this.mPlayButton.setAlpha(1.0f);
                }

        public void showPlaying() {
            this.mState = State.PLAYING;
            showMainView(this.mPlayButton);
            ObjectAnimator anim = ObjectAnimator.ofFloat(this.mPlayButton, "alpha", 1.0f, 0.0f);
            anim.setDuration(200L);
            anim.start();
            anim.addListener(new C05741());
                }

                
        class C05741 implements Animator.AnimatorListener {
            C05741() {
                    }

                    @Override 
            public void onAnimationStart(Animator animation) {
                    }

                    @Override 
            public void onAnimationEnd(Animator animation) {
                TrimControllerOverlay.access$000(TrimControllerOverlay.this);
                    }

                    @Override 
            public void onAnimationCancel(Animator animation) {
                TrimControllerOverlay.access$000(TrimControllerOverlay.this);
                    }

                    @Override 
            public void onAnimationRepeat(Animator animation) {
                    }
                }

       public void showPaused() {
           this.mState = State.PAUSED;
           showMainView(this.mPlayButton);
                }

       public void showEnded() {
           this.mState = State.ENDED;
           showMainView(this.mPlayButton);
                }

       public void showLoading() {
           this.mState = State.LOADING;
           showMainView(this.mProgressBar);
                }

       public void setTimes(int currentTime, int duration, int trimStartTime, int trimEndTime) {
           this.mTimeBar.setTime(currentTime, duration, trimStartTime, trimEndTime);
                }

       private void showMainView(View view) {
           this.mProgressBar.setVisibility(view == this.mProgressBar ? 0 : 4);
           this.mPlayButton.setVisibility(view != this.mPlayButton ? 4 : 0);
                }

                @Override 
       public void onClick(View view) {
           if (this.mListener != null && view == this.mPlayButton) {
               if (this.mState == State.ENDED) {
                   this.mListener.onReplay();
                        } else if (this.mState == State.PAUSED || this.mState == State.PLAYING) {
                   this.mListener.onPlayPause();
                        }
                    }
                }

                @Override 
       public boolean onTouchEvent(MotionEvent event) {
           if (!super.onTouchEvent(event)) {
                        switch (event.getAction()) {
                            case 0:
                       if (this.mState == State.PLAYING || this.mState == State.PAUSED) {
                           this.mListener.onPlayPause();
                                    break;
                                } else {
                           if (this.mState == State.ENDED) {
                               this.mListener.onReplay();
                                    }
                                    break;
                                }
                            default:
                                return true;
                        }
                    }
                    return true;
                }

                @Override 
       protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
           int heightSpec = this.mVideoViewHeight > 0 ? View.MeasureSpec.makeMeasureSpec(this.mVideoViewHeight, 1073741824) : heightMeasureSpec;
           super.onMeasure(widthMeasureSpec, heightSpec);
           measureChildren(widthMeasureSpec, heightMeasureSpec);
                }

                @Override 
       protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
           int w = right - left;
           int y = top + this.mVideoViewHeight;
           this.mTimeBar.layout(0, y - this.mTimeBar.getPreferredHeight(), w, y);
           View playView = this.mPlayButton;
           int centeredLeft = (w - playView.getMeasuredWidth()) / 2;
           int centeredTop = (y - playView.getMeasuredHeight()) / 2;
           int centeredRight = centeredLeft + playView.getMeasuredWidth();
           int centeredBottom = centeredTop + playView.getMeasuredHeight();
           playView.layout(centeredLeft, centeredTop, centeredRight, centeredBottom);
                }

                @Override 
       public void onScrubbingStart() {
           this.mListener.onSeekStart();
                }

                @Override 
       public void onScrubbingMove(int time) {
           this.mListener.onSeekMove(time);
                }

                @Override 
       public void onScrubbingEnd(int time, int trimStartTime, int trimEndTime) {
           this.mListener.onSeekEnd(time, trimStartTime, trimEndTime);
                }
            }
