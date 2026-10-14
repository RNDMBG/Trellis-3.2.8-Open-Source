            package android.support.v7.internal.view;

            import android.content.Context;
            import android.content.res.Resources;
            import android.content.res.TypedArray;
            import android.os.Build;
            import android.support.v7.appcompat.C0084R;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 34 */    public class ActionBarPolicy {
                private Context mContext;

/* 35 */        public static ActionBarPolicy get(Context context) {
/* 36 */            return new ActionBarPolicy(context);
                }

/* 38 */        private ActionBarPolicy(Context context) {
/* 40 */            this.mContext = context;
                }

/* 43 */        public int getMaxActionButtons() {
/* 44 */            return this.mContext.getResources().getInteger(C0084R.integer.abc_max_action_buttons);
                }

/* 48 */        public boolean showsOverflowMenuButton() {
                    return Build.VERSION.SDK_INT >= 11;
                }

/* 52 */        public int getEmbeddedMenuWidthLimit() {
/* 53 */            return this.mContext.getResources().getDisplayMetrics().widthPixels / 2;
                }

/* 58 */        public boolean hasEmbeddedTabs() {
/* 59 */            return this.mContext.getResources().getBoolean(C0084R.bool.abc_action_bar_embed_tabs_pre_jb);
                }

/* 62 */        public int getTabContainerHeight() {
/* 63 */            TypedArray a = this.mContext.obtainStyledAttributes(null, C0084R.styleable.ActionBar, C0084R.attr.actionBarStyle, 0);
/* 65 */            int height = a.getLayoutDimension(0, 0);
/* 66 */            Resources r = this.mContext.getResources();
/* 67 */            if (!hasEmbeddedTabs()) {
/* 69 */                height = Math.min(height, r.getDimensionPixelSize(C0084R.dimen.abc_action_bar_stacked_max_height));
                    }
/* 72 */            a.recycle();
/* 73 */            return height;
                }

/* 78 */        public boolean enableHomeButtonByDefault() {
                    return this.mContext.getApplicationInfo().targetSdkVersion < 14;
                }

/* 82 */        public int getStackedTabMaxWidth() {
/* 83 */            return this.mContext.getResources().getDimensionPixelSize(C0084R.dimen.abc_action_bar_stacked_tab_max_width);
                }
            }
