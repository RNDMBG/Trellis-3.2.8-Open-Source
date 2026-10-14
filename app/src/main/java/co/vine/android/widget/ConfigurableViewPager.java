            package co.vine.android.widget;

            import android.content.Context;
            import android.support.v4.view.ViewPager;
            import android.util.AttributeSet;
            import android.view.MotionEvent;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 13 */    public class ConfigurableViewPager extends ViewPager {
                private boolean mSwipingEnabled;

/* 14 */        public ConfigurableViewPager(Context context) {
/* 15 */            this(context, null);
                }

/* 18 */        public ConfigurableViewPager(Context context, AttributeSet attrs) {
/* 19 */            super(context, attrs);
/* 20 */            this.mSwipingEnabled = true;
                }

/* 23 */        public void setSwipingEnabled(boolean enabled) {
/* 24 */            this.mSwipingEnabled = enabled;
                }

                @Override // android.support.v4.view.ViewPager, android.view.ViewGroup
/* 28 */        public boolean onInterceptTouchEvent(MotionEvent ev) {
/* 29 */            if (this.mSwipingEnabled) {
/* 30 */                return super.onInterceptTouchEvent(ev);
                    }
                    return false;
                }

                @Override // android.support.v4.view.ViewPager, android.view.View
/* 36 */        public boolean onTouchEvent(MotionEvent ev) {
/* 37 */            if (this.mSwipingEnabled) {
/* 38 */                return super.onTouchEvent(ev);
                    }
                    return false;
                }
            }
