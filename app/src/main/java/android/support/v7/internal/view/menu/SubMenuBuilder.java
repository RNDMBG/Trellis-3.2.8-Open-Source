            package android.support.v7.internal.view.menu;

            import android.content.Context;
            import android.graphics.drawable.Drawable;
            import android.view.Menu;
            import android.view.MenuItem;
            import android.view.SubMenu;
            import android.view.View;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 36 */    public class SubMenuBuilder extends MenuBuilder implements SubMenu {
                private MenuItemImpl mItem;
                private MenuBuilder mParentMenu;

/* 37 */        public SubMenuBuilder(Context context, MenuBuilder parentMenu, MenuItemImpl item) {
/* 38 */            super(context);
/* 40 */            this.mParentMenu = parentMenu;
/* 41 */            this.mItem = item;
                }

                @Override // android.support.v7.internal.view.menu.MenuBuilder, android.view.Menu
/* 45 */        public void setQwertyMode(boolean isQwerty) {
/* 46 */            this.mParentMenu.setQwertyMode(isQwerty);
                }

                @Override // android.support.v7.internal.view.menu.MenuBuilder
/* 50 */        public boolean isQwertyMode() {
/* 51 */            return this.mParentMenu.isQwertyMode();
                }

                @Override // android.support.v7.internal.view.menu.MenuBuilder
/* 55 */        public void setShortcutsVisible(boolean shortcutsVisible) {
/* 56 */            this.mParentMenu.setShortcutsVisible(shortcutsVisible);
                }

                @Override // android.support.v7.internal.view.menu.MenuBuilder
/* 60 */        public boolean isShortcutsVisible() {
/* 61 */            return this.mParentMenu.isShortcutsVisible();
                }

/* 64 */        public Menu getParentMenu() {
/* 65 */            return this.mParentMenu;
                }

                @Override // android.view.SubMenu
/* 68 */        public MenuItem getItem() {
/* 69 */            return this.mItem;
                }

                @Override // android.support.v7.internal.view.menu.MenuBuilder
/* 73 */        public void setCallback(MenuBuilder.Callback callback) {
/* 74 */            this.mParentMenu.setCallback(callback);
                }

                @Override // android.support.v7.internal.view.menu.MenuBuilder
/* 77 */        public MenuBuilder getRootMenu() {
/* 78 */            return this.mParentMenu;
                }

                @Override // android.support.v7.internal.view.menu.MenuBuilder
/* 82 */        public boolean dispatchMenuItemSelected(MenuBuilder menu, MenuItem item) {
                    return super.dispatchMenuItemSelected(menu, item) || this.mParentMenu.dispatchMenuItemSelected(menu, item);
                }

                @Override // android.view.SubMenu
/* 87 */        public SubMenu setIcon(Drawable icon) {
/* 88 */            this.mItem.setIcon(icon);
/* 89 */            return this;
                }

                @Override // android.view.SubMenu
/* 92 */        public SubMenu setIcon(int iconRes) {
/* 93 */            this.mItem.setIcon(iconRes);
/* 94 */            return this;
                }

                @Override // android.view.SubMenu
/* 97 */        public SubMenu setHeaderIcon(Drawable icon) {
/* 98 */            super.setHeaderIconInt(icon);
/* 99 */            return this;
                }

                @Override // android.view.SubMenu
/* 102 */       public SubMenu setHeaderIcon(int iconRes) {
/* 103 */           super.setHeaderIconInt(getContext().getResources().getDrawable(iconRes));
/* 104 */           return this;
                }

                @Override // android.view.SubMenu
/* 107 */       public SubMenu setHeaderTitle(CharSequence title) {
/* 108 */           super.setHeaderTitleInt(title);
/* 109 */           return this;
                }

                @Override // android.view.SubMenu
/* 112 */       public SubMenu setHeaderTitle(int titleRes) {
/* 113 */           super.setHeaderTitleInt(getContext().getResources().getString(titleRes));
/* 114 */           return this;
                }

                @Override // android.view.SubMenu
/* 117 */       public SubMenu setHeaderView(View view) {
/* 118 */           super.setHeaderViewInt(view);
/* 119 */           return this;
                }

                @Override // android.support.v7.internal.view.menu.MenuBuilder, android.view.SubMenu
/* 123 */       public void clearHeader() {
                }

                @Override // android.support.v7.internal.view.menu.MenuBuilder
/* 127 */       public boolean expandItemActionView(MenuItemImpl item) {
/* 128 */           return this.mParentMenu.expandItemActionView(item);
                }

                @Override // android.support.v7.internal.view.menu.MenuBuilder
/* 132 */       public boolean collapseItemActionView(MenuItemImpl item) {
/* 133 */           return this.mParentMenu.collapseItemActionView(item);
                }

                @Override // android.support.v7.internal.view.menu.MenuBuilder
/* 137 */       public String getActionViewStatesKey() {
/* 138 */           int itemId = this.mItem != null ? this.mItem.getItemId() : 0;
/* 139 */           if (itemId == 0) {
/* 140 */               return null;
                    }
/* 142 */           return super.getActionViewStatesKey() + ":" + itemId;
                }
            }
