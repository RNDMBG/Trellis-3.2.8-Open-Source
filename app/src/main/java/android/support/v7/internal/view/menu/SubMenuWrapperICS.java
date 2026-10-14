            package android.support.v7.internal.view.menu;

            import android.graphics.drawable.Drawable;
            import android.support.v4.internal.view.SupportSubMenu;
            import android.view.MenuItem;
            import android.view.SubMenu;
            import android.view.View;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 23 */    class SubMenuWrapperICS extends MenuWrapperICS implements SupportSubMenu {
                @Override // android.support.v7.internal.view.menu.BaseWrapper
/* 24 */        public /* bridge */ /* synthetic */ Object getWrappedObject() {
/* 25 */            return getWrappedObject();
                }

/* 26 */        SubMenuWrapperICS(SubMenu subMenu) {
/* 27 */            super(subMenu);
                }

                @Override // android.support.v7.internal.view.menu.BaseWrapper
/* 31 */        public SubMenu getWrappedObject() {
/* 32 */            return (SubMenu) this.mWrappedObject;
                }

                @Override // android.view.SubMenu
/* 36 */        public SubMenu setHeaderTitle(int titleRes) {
/* 37 */            ((SubMenu) this.mWrappedObject).setHeaderTitle(titleRes);
/* 38 */            return this;
                }

                @Override // android.view.SubMenu
/* 42 */        public SubMenu setHeaderTitle(CharSequence title) {
/* 43 */            ((SubMenu) this.mWrappedObject).setHeaderTitle(title);
/* 44 */            return this;
                }

                @Override // android.view.SubMenu
/* 48 */        public SubMenu setHeaderIcon(int iconRes) {
/* 49 */            ((SubMenu) this.mWrappedObject).setHeaderIcon(iconRes);
/* 50 */            return this;
                }

                @Override // android.view.SubMenu
/* 54 */        public SubMenu setHeaderIcon(Drawable icon) {
/* 55 */            ((SubMenu) this.mWrappedObject).setHeaderIcon(icon);
/* 56 */            return this;
                }

                @Override // android.view.SubMenu
/* 60 */        public SubMenu setHeaderView(View view) {
/* 61 */            ((SubMenu) this.mWrappedObject).setHeaderView(view);
/* 62 */            return this;
                }

                @Override // android.view.SubMenu
/* 66 */        public void clearHeader() {
/* 67 */            ((SubMenu) this.mWrappedObject).clearHeader();
                }

                @Override // android.view.SubMenu
/* 71 */        public SubMenu setIcon(int iconRes) {
/* 72 */            ((SubMenu) this.mWrappedObject).setIcon(iconRes);
/* 73 */            return this;
                }

                @Override // android.view.SubMenu
/* 77 */        public SubMenu setIcon(Drawable icon) {
/* 78 */            ((SubMenu) this.mWrappedObject).setIcon(icon);
/* 79 */            return this;
                }

                @Override // android.view.SubMenu
/* 83 */        public MenuItem getItem() {
/* 84 */            return getMenuItemWrapper(((SubMenu) this.mWrappedObject).getItem());
                }
            }
