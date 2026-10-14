            package co.vine.android.widget;

            import android.content.Context;
            import android.util.AttributeSet;
            import android.widget.ScrollView;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 13 */    public class ScrollDeltaScrollView extends ScrollView {
                private int mLastT;
                private ScrollDeltaListener mScrollDeltaListener;

                public interface ScrollDeltaListener {
                    void onScroll(int i);
                }

/* 14 */        public ScrollDeltaScrollView(Context context) {
/* 15 */            super(context);
/* 11 */            this.mScrollDeltaListener = null;
/* 12 */            this.mLastT = 0;
                }

/* 18 */        public ScrollDeltaScrollView(Context context, AttributeSet attrs) {
/* 19 */            super(context, attrs);
/* 11 */            this.mScrollDeltaListener = null;
/* 12 */            this.mLastT = 0;
                }

/* 22 */        public ScrollDeltaScrollView(Context context, AttributeSet attrs, int defStyle) {
/* 23 */            super(context, attrs, defStyle);
/* 11 */            this.mScrollDeltaListener = null;
/* 12 */            this.mLastT = 0;
                }

                @Override // android.view.View
/* 28 */        protected void onScrollChanged(int l, int t, int oldl, int oldt) {
/* 29 */            super.onScrollChanged(l, t, oldl, oldt);
/* 31 */            if (t < 0) {
                        t = 0;
                    }
/* 33 */            if (this.mScrollDeltaListener != null) {
/* 34 */                this.mScrollDeltaListener.onScroll(this.mLastT - t);
                    }
/* 36 */            this.mLastT = t;
                }

/* 44 */        public void setScrollDeltaListener(ScrollDeltaListener listener) {
/* 45 */            this.mScrollDeltaListener = listener;
                }
            }
