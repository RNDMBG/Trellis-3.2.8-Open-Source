            package android.support.v7.internal.view.menu;

            import android.os.Build;
            import android.support.v4.internal.view.SupportMenu;
            import android.support.v4.internal.view.SupportMenuItem;
            import android.support.v4.internal.view.SupportSubMenu;
            import android.view.Menu;
            import android.view.MenuItem;
            import android.view.SubMenu;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 28 */    public final class MenuWrapperFactory {
/* 29 */        private MenuWrapperFactory() {
                }

/* 33 */        public static Menu createMenuWrapper(Menu frameworkMenu) {
/* 34 */            if (Build.VERSION.SDK_INT >= 14) {
/* 35 */                return new MenuWrapperICS(frameworkMenu);
                    }
/* 37 */            return frameworkMenu;
                }

/* 40 */        public static MenuItem createMenuItemWrapper(MenuItem frameworkMenuItem) {
/* 41 */            if (Build.VERSION.SDK_INT >= 16) {
/* 42 */                return new MenuItemWrapperJB(frameworkMenuItem);
                    }
/* 43 */            if (Build.VERSION.SDK_INT >= 14) {
/* 44 */                return new MenuItemWrapperICS(frameworkMenuItem);
                    }
/* 46 */            return frameworkMenuItem;
                }

/* 49 */        public static SupportMenu createSupportMenuWrapper(Menu frameworkMenu) {
/* 50 */            if (Build.VERSION.SDK_INT >= 14) {
/* 51 */                return new MenuWrapperICS(frameworkMenu);
                    }
/* 53 */            throw new UnsupportedOperationException();
                }

/* 57 */        public static SupportSubMenu createSupportSubMenuWrapper(SubMenu frameworkSubMenu) {
/* 58 */            if (Build.VERSION.SDK_INT >= 14) {
/* 59 */                return new SubMenuWrapperICS(frameworkSubMenu);
                    }
/* 61 */            throw new UnsupportedOperationException();
                }

/* 65 */        public static SupportMenuItem createSupportMenuItemWrapper(MenuItem frameworkMenuItem) {
/* 66 */            if (Build.VERSION.SDK_INT >= 16) {
/* 67 */                return new MenuItemWrapperJB(frameworkMenuItem);
                    }
/* 68 */            if (Build.VERSION.SDK_INT >= 14) {
/* 69 */                return new MenuItemWrapperICS(frameworkMenuItem);
                    }
/* 71 */            throw new UnsupportedOperationException();
                }
            }
