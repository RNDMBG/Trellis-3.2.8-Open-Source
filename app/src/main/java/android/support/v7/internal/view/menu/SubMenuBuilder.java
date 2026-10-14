            package android.support.v7.internal.view.menu;

            import android.content.Context;
            import android.graphics.drawable.Drawable;
            import android.view.Menu;
            import android.view.MenuItem;
            import android.view.SubMenu;
            import android.view.View;
            
            
    public class SubMenuBuilder extends MenuBuilder implements SubMenu {
                private MenuItemImpl mItem;
                private MenuBuilder mParentMenu;

        public SubMenuBuilder(Context context, MenuBuilder parentMenu, MenuItemImpl item) {
            super(context);
            this.mParentMenu = parentMenu;
            this.mItem = item;
                }

                @Override 
        public void setQwertyMode(boolean isQwerty) {
            this.mParentMenu.setQwertyMode(isQwerty);
                }

                @Override 
        public boolean isQwertyMode() {
            return this.mParentMenu.isQwertyMode();
                }

                @Override 
        public void setShortcutsVisible(boolean shortcutsVisible) {
            this.mParentMenu.setShortcutsVisible(shortcutsVisible);
                }

                @Override 
        public boolean isShortcutsVisible() {
            return this.mParentMenu.isShortcutsVisible();
                }

        public Menu getParentMenu() {
            return this.mParentMenu;
                }

                @Override 
        public MenuItem getItem() {
            return this.mItem;
                }

                @Override 
        public void setCallback(MenuBuilder.Callback callback) {
            this.mParentMenu.setCallback(callback);
                }

                @Override 
        public MenuBuilder getRootMenu() {
            return this.mParentMenu;
                }

                @Override 
        public boolean dispatchMenuItemSelected(MenuBuilder menu, MenuItem item) {
                    return super.dispatchMenuItemSelected(menu, item) || this.mParentMenu.dispatchMenuItemSelected(menu, item);
                }

                @Override 
        public SubMenu setIcon(Drawable icon) {
            this.mItem.setIcon(icon);
            return this;
                }

                @Override 
        public SubMenu setIcon(int iconRes) {
            this.mItem.setIcon(iconRes);
            return this;
                }

                @Override 
        public SubMenu setHeaderIcon(Drawable icon) {
            super.setHeaderIconInt(icon);
            return this;
                }

                @Override 
       public SubMenu setHeaderIcon(int iconRes) {
           super.setHeaderIconInt(getContext().getResources().getDrawable(iconRes));
           return this;
                }

                @Override 
       public SubMenu setHeaderTitle(CharSequence title) {
           super.setHeaderTitleInt(title);
           return this;
                }

                @Override 
       public SubMenu setHeaderTitle(int titleRes) {
           super.setHeaderTitleInt(getContext().getResources().getString(titleRes));
           return this;
                }

                @Override 
       public SubMenu setHeaderView(View view) {
           super.setHeaderViewInt(view);
           return this;
                }

                @Override 
       public void clearHeader() {
                }

                @Override 
       public boolean expandItemActionView(MenuItemImpl item) {
           return this.mParentMenu.expandItemActionView(item);
                }

                @Override 
       public boolean collapseItemActionView(MenuItemImpl item) {
           return this.mParentMenu.collapseItemActionView(item);
                }

                @Override 
       public String getActionViewStatesKey() {
           int itemId = this.mItem != null ? this.mItem.getItemId() : 0;
           if (itemId == 0) {
               return null;
                    }
           return super.getActionViewStatesKey() + ":" + itemId;
                }
            }
