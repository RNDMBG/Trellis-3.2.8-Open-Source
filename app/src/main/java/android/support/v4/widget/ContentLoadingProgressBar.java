            package android.support.v4.widget;

            import android.content.Context;
            import android.util.AttributeSet;
            import android.widget.ProgressBar;
            
            
    public class ContentLoadingProgressBar extends ProgressBar {
                private static final int MIN_DELAY = 500;
                private static final int MIN_SHOW_TIME = 500;
                private final Runnable mDelayedHide;
                private final Runnable mDelayedShow;
                private boolean mDismissed;
                private boolean mPostedHide;
                private boolean mPostedShow;
                private long mStartTime;

        static  boolean access$002(ContentLoadingProgressBar x0, boolean x1) {
            x0.mPostedHide = x1;
                    return x1;
                }

        static  long access$102(ContentLoadingProgressBar x0, long x1) {
            x0.mStartTime = x1;
                    return x1;
                }

        static  boolean access$202(ContentLoadingProgressBar x0, boolean x1) {
            x0.mPostedShow = x1;
                    return x1;
                }

        static  boolean access$300(ContentLoadingProgressBar x0) {
            return x0.mDismissed;
                }

                
        class RunnableC00621 implements Runnable {
            RunnableC00621() {
                    }

                    @Override 
            public void run() {
                ContentLoadingProgressBar.access$002(ContentLoadingProgressBar.this, false);
                ContentLoadingProgressBar.access$102(ContentLoadingProgressBar.this, -1L);
                ContentLoadingProgressBar.this.setVisibility(8);
                    }
                }

                
        class RunnableC00632 implements Runnable {
            RunnableC00632() {
                    }

                    @Override 
            public void run() {
                ContentLoadingProgressBar.access$202(ContentLoadingProgressBar.this, false);
                if (!ContentLoadingProgressBar.access$300(ContentLoadingProgressBar.this)) {
                    ContentLoadingProgressBar.access$102(ContentLoadingProgressBar.this, System.currentTimeMillis());
                    ContentLoadingProgressBar.this.setVisibility(0);
                        }
                    }
                }

        public ContentLoadingProgressBar(Context context) {
            this(context, null);
                }

        public ContentLoadingProgressBar(Context context, AttributeSet attrs) {
            super(context, attrs, 0);
            this.mStartTime = -1L;
            this.mPostedHide = false;
            this.mPostedShow = false;
            this.mDismissed = false;
            this.mDelayedHide = new RunnableC00621();
            this.mDelayedShow = new RunnableC00632();
                }

                @Override 
        public void onAttachedToWindow() {
            super.onAttachedToWindow();
            removeCallbacks();
                }

                @Override 
        public void onDetachedFromWindow() {
            super.onDetachedFromWindow();
            removeCallbacks();
                }

        private void removeCallbacks() {
            removeCallbacks(this.mDelayedHide);
            removeCallbacks(this.mDelayedShow);
                }

        public void hide() {
            this.mDismissed = true;
            removeCallbacks(this.mDelayedShow);
            long diff = System.currentTimeMillis() - this.mStartTime;
                    if (diff >= 500 || this.mStartTime == -1) {
               setVisibility(8);
                    } else if (!this.mPostedHide) {
               postDelayed(this.mDelayedHide, 500 - diff);
               this.mPostedHide = true;
                    }
                }

       public void show() {
           this.mStartTime = -1L;
           this.mDismissed = false;
           removeCallbacks(this.mDelayedHide);
           if (!this.mPostedShow) {
               postDelayed(this.mDelayedShow, 500L);
               this.mPostedShow = true;
                    }
                }
            }
