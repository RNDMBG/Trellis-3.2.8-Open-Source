            package android.support.v4.view;

            import android.view.MenuItem;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 20 */    class MenuItemCompatIcs {

                interface SupportActionExpandProxy {
                    boolean onMenuItemActionCollapse(MenuItem menuItem);

                    boolean onMenuItemActionExpand(MenuItem menuItem);
                }

/* 21 */        MenuItemCompatIcs() {
                }

/* 23 */        public static boolean expandActionView(MenuItem item) {
/* 24 */            return item.expandActionView();
                }

/* 27 */        public static boolean collapseActionView(MenuItem item) {
/* 28 */            return item.collapseActionView();
                }

/* 31 */        public static boolean isActionViewExpanded(MenuItem item) {
/* 32 */            return item.isActionViewExpanded();
                }

/* 36 */        public static MenuItem setOnActionExpandListener(MenuItem item, SupportActionExpandProxy listener) {
/* 37 */            return item.setOnActionExpandListener(new OnActionExpandListenerWrapper(listener));
                }

/* 52 */        static class OnActionExpandListenerWrapper implements MenuItem.OnActionExpandListener {
                    private SupportActionExpandProxy mWrapped;

/* 53 */            public OnActionExpandListenerWrapper(SupportActionExpandProxy wrapped) {
/* 55 */                this.mWrapped = wrapped;
                    }

                    @Override // android.view.MenuItem.OnActionExpandListener
/* 59 */            public boolean onMenuItemActionExpand(MenuItem item) {
/* 60 */                return this.mWrapped.onMenuItemActionExpand(item);
                    }

                    @Override // android.view.MenuItem.OnActionExpandListener
/* 64 */            public boolean onMenuItemActionCollapse(MenuItem item) {
/* 65 */                return this.mWrapped.onMenuItemActionCollapse(item);
                    }
                }
            }
