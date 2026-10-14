            package co.vine.android.widget.tabs;

            import android.content.Context;
            import android.util.AttributeSet;
            import android.widget.TabHost;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 14 */    public class IconTabHost extends TabHost {
                private OnTabClickedListener mOnTabClickedListener;

                public interface OnTabClickedListener {
                    void onCurrentTabClicked();
                }

/* 15 */        public IconTabHost(Context context, AttributeSet attrs) {
/* 16 */            super(context, attrs);
                }

/* 19 */        public void setOnTabClickedListener(OnTabClickedListener listener) {
/* 20 */            this.mOnTabClickedListener = listener;
                }

                @Override // android.widget.TabHost, android.view.ViewGroup, android.view.View
/* 25 */        public void dispatchWindowFocusChanged(boolean hasFocus) {
/* 26 */            if (getCurrentView() != null) {
/* 27 */                super.dispatchWindowFocusChanged(hasFocus);
                    }
                }

                @Override // android.widget.TabHost
/* 32 */        public void setCurrentTab(int index) {
/* 33 */            if (index == getCurrentTab()) {
/* 34 */                if (this.mOnTabClickedListener != null) {
/* 35 */                    this.mOnTabClickedListener.onCurrentTabClicked();
/* 40 */                    return;
                        }
/* 40 */                return;
                    }
/* 38 */            super.setCurrentTab(index);
                }

/* 42 */        public void setTabExplicit(int index) {
/* 43 */            super.setCurrentTab(index);
                }
            }
