            package android.support.v7.internal.view.menu;

            import android.content.ComponentName;
            import android.content.Intent;
            import android.support.v4.internal.view.SupportMenu;
            import android.view.KeyEvent;
            import android.view.Menu;
            import android.view.MenuItem;
            import android.view.SubMenu;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 28 */    class MenuWrapperICS extends BaseMenuWrapper<Menu> implements SupportMenu {
/* 29 */        MenuWrapperICS(Menu object) {
/* 30 */            super(object);
                }

                @Override // android.view.Menu
/* 34 */        public MenuItem add(CharSequence title) {
/* 35 */            return getMenuItemWrapper(((Menu) this.mWrappedObject).add(title));
                }

                @Override // android.view.Menu
/* 39 */        public MenuItem add(int titleRes) {
/* 40 */            return getMenuItemWrapper(((Menu) this.mWrappedObject).add(titleRes));
                }

                @Override // android.view.Menu
/* 44 */        public MenuItem add(int groupId, int itemId, int order, CharSequence title) {
/* 45 */            return getMenuItemWrapper(((Menu) this.mWrappedObject).add(groupId, itemId, order, title));
                }

                @Override // android.view.Menu
/* 49 */        public MenuItem add(int groupId, int itemId, int order, int titleRes) {
/* 50 */            return getMenuItemWrapper(((Menu) this.mWrappedObject).add(groupId, itemId, order, titleRes));
                }

                @Override // android.view.Menu
/* 54 */        public SubMenu addSubMenu(CharSequence title) {
/* 55 */            return getSubMenuWrapper(((Menu) this.mWrappedObject).addSubMenu(title));
                }

                @Override // android.view.Menu
/* 59 */        public SubMenu addSubMenu(int titleRes) {
/* 60 */            return getSubMenuWrapper(((Menu) this.mWrappedObject).addSubMenu(titleRes));
                }

                @Override // android.view.Menu
/* 64 */        public SubMenu addSubMenu(int groupId, int itemId, int order, CharSequence title) {
/* 65 */            return getSubMenuWrapper(((Menu) this.mWrappedObject).addSubMenu(groupId, itemId, order, title));
                }

                @Override // android.view.Menu
/* 69 */        public SubMenu addSubMenu(int groupId, int itemId, int order, int titleRes) {
/* 70 */            return getSubMenuWrapper(((Menu) this.mWrappedObject).addSubMenu(groupId, itemId, order, titleRes));
                }

                @Override // android.view.Menu
/* 76 */        public int addIntentOptions(int groupId, int itemId, int order, ComponentName caller, Intent[] specifics, Intent intent, int flags, MenuItem[] outSpecificItems) {
/* 77 */            MenuItem[] items = null;
/* 78 */            if (outSpecificItems != null) {
/* 79 */                items = new MenuItem[outSpecificItems.length];
                    }
/* 82 */            int result = ((Menu) this.mWrappedObject).addIntentOptions(groupId, itemId, order, caller, specifics, intent, flags, items);
/* 85 */            if (items != null) {
                        int z = items.length;
                        for (int i = 0; i < z; i++) {
/* 87 */                    outSpecificItems[i] = getMenuItemWrapper(items[i]);
                        }
                    }
/* 91 */            return result;
                }

                @Override // android.view.Menu
/* 95 */        public void removeItem(int id) {
/* 96 */            internalRemoveItem(id);
/* 97 */            ((Menu) this.mWrappedObject).removeItem(id);
                }

                @Override // android.view.Menu
/* 101 */       public void removeGroup(int groupId) {
/* 102 */           internalRemoveGroup(groupId);
/* 103 */           ((Menu) this.mWrappedObject).removeGroup(groupId);
                }

                @Override // android.view.Menu
/* 107 */       public void clear() {
/* 108 */           internalClear();
/* 109 */           ((Menu) this.mWrappedObject).clear();
                }

                @Override // android.view.Menu
/* 113 */       public void setGroupCheckable(int group, boolean checkable, boolean exclusive) {
/* 114 */           ((Menu) this.mWrappedObject).setGroupCheckable(group, checkable, exclusive);
                }

                @Override // android.view.Menu
/* 118 */       public void setGroupVisible(int group, boolean visible) {
/* 119 */           ((Menu) this.mWrappedObject).setGroupVisible(group, visible);
                }

                @Override // android.view.Menu
/* 123 */       public void setGroupEnabled(int group, boolean enabled) {
/* 124 */           ((Menu) this.mWrappedObject).setGroupEnabled(group, enabled);
                }

                @Override // android.view.Menu
/* 128 */       public boolean hasVisibleItems() {
/* 129 */           return ((Menu) this.mWrappedObject).hasVisibleItems();
                }

                @Override // android.view.Menu
/* 133 */       public MenuItem findItem(int id) {
/* 134 */           return getMenuItemWrapper(((Menu) this.mWrappedObject).findItem(id));
                }

                @Override // android.view.Menu
/* 138 */       public int size() {
/* 139 */           return ((Menu) this.mWrappedObject).size();
                }

                @Override // android.view.Menu
/* 143 */       public MenuItem getItem(int index) {
/* 144 */           return getMenuItemWrapper(((Menu) this.mWrappedObject).getItem(index));
                }

                @Override // android.view.Menu
/* 148 */       public void close() {
/* 149 */           ((Menu) this.mWrappedObject).close();
                }

                @Override // android.view.Menu
/* 153 */       public boolean performShortcut(int keyCode, KeyEvent event, int flags) {
/* 154 */           return ((Menu) this.mWrappedObject).performShortcut(keyCode, event, flags);
                }

                @Override // android.view.Menu
/* 158 */       public boolean isShortcutKey(int keyCode, KeyEvent event) {
/* 159 */           return ((Menu) this.mWrappedObject).isShortcutKey(keyCode, event);
                }

                @Override // android.view.Menu
/* 163 */       public boolean performIdentifierAction(int id, int flags) {
/* 164 */           return ((Menu) this.mWrappedObject).performIdentifierAction(id, flags);
                }

                @Override // android.view.Menu
/* 168 */       public void setQwertyMode(boolean isQwerty) {
/* 169 */           ((Menu) this.mWrappedObject).setQwertyMode(isQwerty);
                }
            }
