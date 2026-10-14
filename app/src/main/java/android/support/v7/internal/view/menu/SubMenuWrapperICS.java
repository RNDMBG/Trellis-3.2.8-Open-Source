            package android.support.v7.internal.view.menu;

            import android.graphics.drawable.Drawable;
            import android.support.v4.internal.view.SupportSubMenu;
            import android.view.MenuItem;
            import android.view.SubMenu;
            import android.view.View;
            
            
    class SubMenuWrapperICS extends MenuWrapperICS implements SupportSubMenu {
                @Override 
        public   Object getWrappedObject() {
            return getWrappedObject();
                }

        SubMenuWrapperICS(SubMenu subMenu) {
            super(subMenu);
                }

                @Override 
        public SubMenu getWrappedObject() {
            return (SubMenu) this.mWrappedObject;
                }

                @Override 
        public SubMenu setHeaderTitle(int titleRes) {
            ((SubMenu) this.mWrappedObject).setHeaderTitle(titleRes);
            return this;
                }

                @Override 
        public SubMenu setHeaderTitle(CharSequence title) {
            ((SubMenu) this.mWrappedObject).setHeaderTitle(title);
            return this;
                }

                @Override 
        public SubMenu setHeaderIcon(int iconRes) {
            ((SubMenu) this.mWrappedObject).setHeaderIcon(iconRes);
            return this;
                }

                @Override 
        public SubMenu setHeaderIcon(Drawable icon) {
            ((SubMenu) this.mWrappedObject).setHeaderIcon(icon);
            return this;
                }

                @Override 
        public SubMenu setHeaderView(View view) {
            ((SubMenu) this.mWrappedObject).setHeaderView(view);
            return this;
                }

                @Override 
        public void clearHeader() {
            ((SubMenu) this.mWrappedObject).clearHeader();
                }

                @Override 
        public SubMenu setIcon(int iconRes) {
            ((SubMenu) this.mWrappedObject).setIcon(iconRes);
            return this;
                }

                @Override 
        public SubMenu setIcon(Drawable icon) {
            ((SubMenu) this.mWrappedObject).setIcon(icon);
            return this;
                }

                @Override 
        public MenuItem getItem() {
            return getMenuItemWrapper(((SubMenu) this.mWrappedObject).getItem());
                }
            }
