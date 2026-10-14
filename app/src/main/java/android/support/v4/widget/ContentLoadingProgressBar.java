            package android.support.v4.widget;

            import android.content.Context;
            import android.util.AttributeSet;
            import android.widget.ProgressBar;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 28 */    public class ContentLoadingProgressBar extends ProgressBar {
                private static final int MIN_DELAY = 500;
                private static final int MIN_SHOW_TIME = 500;
                private final Runnable mDelayedHide;
                private final Runnable mDelayedShow;
                private boolean mDismissed;
                private boolean mPostedHide;
                private boolean mPostedShow;
                private long mStartTime;

/* 29 */        static /* synthetic */ boolean access$002(ContentLoadingProgressBar x0, boolean x1) {
/* 30 */            x0.mPostedHide = x1;
                    return x1;
                }

/* 29 */        static /* synthetic */ long access$102(ContentLoadingProgressBar x0, long x1) {
/* 30 */            x0.mStartTime = x1;
                    return x1;
                }

/* 29 */        static /* synthetic */ boolean access$202(ContentLoadingProgressBar x0, boolean x1) {
/* 30 */            x0.mPostedShow = x1;
                    return x1;
                }

/* 29 */        static /* synthetic */ boolean access$300(ContentLoadingProgressBar x0) {
/* 30 */            return x0.mDismissed;
                }

                /* JADX INFO: renamed from: android.support.v4.widget.ContentLoadingProgressBar$1 */
/* 40 */        class RunnableC00621 implements Runnable {
/* 41 */            RunnableC00621() {
                    }

                    @Override // java.lang.Runnable
/* 45 */            public void run() {
/* 46 */                ContentLoadingProgressBar.access$002(ContentLoadingProgressBar.this, false);
/* 47 */                ContentLoadingProgressBar.access$102(ContentLoadingProgressBar.this, -1L);
/* 48 */                ContentLoadingProgressBar.this.setVisibility(8);
                    }
                }

                /* JADX INFO: renamed from: android.support.v4.widget.ContentLoadingProgressBar$2 */
/* 50 */        class RunnableC00632 implements Runnable {
/* 51 */            RunnableC00632() {
                    }

                    @Override // java.lang.Runnable
/* 55 */            public void run() {
/* 56 */                ContentLoadingProgressBar.access$202(ContentLoadingProgressBar.this, false);
/* 57 */                if (!ContentLoadingProgressBar.access$300(ContentLoadingProgressBar.this)) {
/* 58 */                    ContentLoadingProgressBar.access$102(ContentLoadingProgressBar.this, System.currentTimeMillis());
/* 59 */                    ContentLoadingProgressBar.this.setVisibility(0);
                        }
                    }
                }

/* 64 */        public ContentLoadingProgressBar(Context context) {
/* 65 */            this(context, null);
                }

/* 68 */        public ContentLoadingProgressBar(Context context, AttributeSet attrs) {
/* 69 */            super(context, attrs, 0);
/* 34 */            this.mStartTime = -1L;
/* 36 */            this.mPostedHide = false;
/* 38 */            this.mPostedShow = false;
/* 40 */            this.mDismissed = false;
/* 42 */            this.mDelayedHide = new RunnableC00621();
/* 52 */            this.mDelayedShow = new RunnableC00632();
                }

                @Override // android.widget.ProgressBar, android.view.View
/* 73 */        public void onAttachedToWindow() {
/* 74 */            super.onAttachedToWindow();
/* 75 */            removeCallbacks();
                }

                @Override // android.widget.ProgressBar, android.view.View
/* 79 */        public void onDetachedFromWindow() {
/* 80 */            super.onDetachedFromWindow();
/* 81 */            removeCallbacks();
                }

/* 84 */        private void removeCallbacks() {
/* 85 */            removeCallbacks(this.mDelayedHide);
/* 86 */            removeCallbacks(this.mDelayedShow);
                }

/* 94 */        public void hide() {
/* 95 */            this.mDismissed = true;
/* 96 */            removeCallbacks(this.mDelayedShow);
/* 97 */            long diff = System.currentTimeMillis() - this.mStartTime;
                    if (diff >= 500 || this.mStartTime == -1) {
/* 102 */               setVisibility(8);
                    } else if (!this.mPostedHide) {
/* 108 */               postDelayed(this.mDelayedHide, 500 - diff);
/* 109 */               this.mPostedHide = true;
                    }
                }

/* 119 */       public void show() {
/* 120 */           this.mStartTime = -1L;
/* 121 */           this.mDismissed = false;
/* 122 */           removeCallbacks(this.mDelayedHide);
/* 123 */           if (!this.mPostedShow) {
/* 124 */               postDelayed(this.mDelayedShow, 500L);
/* 125 */               this.mPostedShow = true;
                    }
                }
            }
