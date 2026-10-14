            package co.vine.android.widget.tabs;

            import android.content.Context;
            import android.view.View;
            import android.widget.TabHost;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 11 */    public class DummyTabFactory implements TabHost.TabContentFactory {
                private final Context mContext;

/* 12 */        public DummyTabFactory(Context context) {
/* 14 */            this.mContext = context;
                }

                @Override // android.widget.TabHost.TabContentFactory
/* 17 */        public View createTabContent(String tag) {
/* 18 */            View v = new View(this.mContext);
/* 19 */            v.setMinimumWidth(0);
/* 20 */            v.setMinimumHeight(0);
/* 21 */            return v;
                }
            }
