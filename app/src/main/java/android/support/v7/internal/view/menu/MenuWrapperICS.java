            package android.support.v7.internal.view.menu;

            import android.content.ComponentName;
            import android.content.Intent;
            import android.support.v4.internal.view.SupportMenu;
            import android.view.KeyEvent;
            import android.view.Menu;
            import android.view.MenuItem;
            import android.view.SubMenu;
            
            
    class MenuWrapperICS extends BaseMenuWrapper<Menu> implements SupportMenu {
        MenuWrapperICS(Menu object) {
            super(object);
                }

                @Override 
        public MenuItem add(CharSequence title) {
            return getMenuItemWrapper(((Menu) this.mWrappedObject).add(title));
                }

                @Override 
        public MenuItem add(int titleRes) {
            return getMenuItemWrapper(((Menu) this.mWrappedObject).add(titleRes));
                }

                @Override 
        public MenuItem add(int groupId, int itemId, int order, CharSequence title) {
            return getMenuItemWrapper(((Menu) this.mWrappedObject).add(groupId, itemId, order, title));
                }

                @Override 
        public MenuItem add(int groupId, int itemId, int order, int titleRes) {
            return getMenuItemWrapper(((Menu) this.mWrappedObject).add(groupId, itemId, order, titleRes));
                }

                @Override 
        public SubMenu addSubMenu(CharSequence title) {
            return getSubMenuWrapper(((Menu) this.mWrappedObject).addSubMenu(title));
                }

                @Override 
        public SubMenu addSubMenu(int titleRes) {
            return getSubMenuWrapper(((Menu) this.mWrappedObject).addSubMenu(titleRes));
                }

                @Override 
        public SubMenu addSubMenu(int groupId, int itemId, int order, CharSequence title) {
            return getSubMenuWrapper(((Menu) this.mWrappedObject).addSubMenu(groupId, itemId, order, title));
                }

                @Override 
        public SubMenu addSubMenu(int groupId, int itemId, int order, int titleRes) {
            return getSubMenuWrapper(((Menu) this.mWrappedObject).addSubMenu(groupId, itemId, order, titleRes));
                }

                @Override 
        public int addIntentOptions(int groupId, int itemId, int order, ComponentName caller, Intent[] specifics, Intent intent, int flags, MenuItem[] outSpecificItems) {
            MenuItem[] items = null;
            if (outSpecificItems != null) {
                items = new MenuItem[outSpecificItems.length];
                    }
            int result = ((Menu) this.mWrappedObject).addIntentOptions(groupId, itemId, order, caller, specifics, intent, flags, items);
            if (items != null) {
                        int z = items.length;
                        for (int i = 0; i < z; i++) {
                    outSpecificItems[i] = getMenuItemWrapper(items[i]);
                        }
                    }
            return result;
                }

                @Override 
        public void removeItem(int id) {
            internalRemoveItem(id);
            ((Menu) this.mWrappedObject).removeItem(id);
                }

                @Override 
       public void removeGroup(int groupId) {
           internalRemoveGroup(groupId);
           ((Menu) this.mWrappedObject).removeGroup(groupId);
                }

                @Override 
       public void clear() {
           internalClear();
           ((Menu) this.mWrappedObject).clear();
                }

                @Override 
       public void setGroupCheckable(int group, boolean checkable, boolean exclusive) {
           ((Menu) this.mWrappedObject).setGroupCheckable(group, checkable, exclusive);
                }

                @Override 
       public void setGroupVisible(int group, boolean visible) {
           ((Menu) this.mWrappedObject).setGroupVisible(group, visible);
                }

                @Override 
       public void setGroupEnabled(int group, boolean enabled) {
           ((Menu) this.mWrappedObject).setGroupEnabled(group, enabled);
                }

                @Override 
       public boolean hasVisibleItems() {
           return ((Menu) this.mWrappedObject).hasVisibleItems();
                }

                @Override 
       public MenuItem findItem(int id) {
           return getMenuItemWrapper(((Menu) this.mWrappedObject).findItem(id));
                }

                @Override 
       public int size() {
           return ((Menu) this.mWrappedObject).size();
                }

                @Override 
       public MenuItem getItem(int index) {
           return getMenuItemWrapper(((Menu) this.mWrappedObject).getItem(index));
                }

                @Override 
       public void close() {
           ((Menu) this.mWrappedObject).close();
                }

                @Override 
       public boolean performShortcut(int keyCode, KeyEvent event, int flags) {
           return ((Menu) this.mWrappedObject).performShortcut(keyCode, event, flags);
                }

                @Override 
       public boolean isShortcutKey(int keyCode, KeyEvent event) {
           return ((Menu) this.mWrappedObject).isShortcutKey(keyCode, event);
                }

                @Override 
       public boolean performIdentifierAction(int id, int flags) {
           return ((Menu) this.mWrappedObject).performIdentifierAction(id, flags);
                }

                @Override 
       public void setQwertyMode(boolean isQwerty) {
           ((Menu) this.mWrappedObject).setQwertyMode(isQwerty);
                }
            }
