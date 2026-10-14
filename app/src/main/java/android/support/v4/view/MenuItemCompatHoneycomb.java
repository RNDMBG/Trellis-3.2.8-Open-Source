            package android.support.v4.view;

            import android.view.MenuItem;
            import android.view.View;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 23 */    class MenuItemCompatHoneycomb {
/* 24 */        MenuItemCompatHoneycomb() {
                }

/* 26 */        public static void setShowAsAction(MenuItem item, int actionEnum) {
/* 27 */            item.setShowAsAction(actionEnum);
                }

/* 30 */        public static MenuItem setActionView(MenuItem item, View view) {
/* 31 */            return item.setActionView(view);
                }

/* 34 */        public static MenuItem setActionView(MenuItem item, int resId) {
/* 35 */            return item.setActionView(resId);
                }

/* 38 */        public static View getActionView(MenuItem item) {
/* 39 */            return item.getActionView();
                }
            }
