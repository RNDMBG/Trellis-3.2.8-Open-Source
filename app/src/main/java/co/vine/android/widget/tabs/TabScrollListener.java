            package co.vine.android.widget.tabs;

            import co.vine.android.BaseControllerActionBarActivity;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 10 */    public class TabScrollListener {
                private final BaseControllerActionBarActivity mActivity;
                private final int mTabIndex;

/* 11 */        public TabScrollListener(BaseControllerActionBarActivity activity, int position) {
/* 13 */            this.mTabIndex = position;
/* 14 */            this.mActivity = activity;
                }

/* 18 */        public void onScrollFirstItem() {
                }
            }
