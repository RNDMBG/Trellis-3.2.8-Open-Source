            package android.support.v7.internal.view.menu;

            import android.support.v4.internal.view.SupportMenuItem;
            import android.view.MenuItem;
            import android.view.SubMenu;
            import java.util.HashMap;
            import java.util.Iterator;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 31 */    abstract class BaseMenuWrapper<T> extends BaseWrapper<T> {
                private HashMap<MenuItem, SupportMenuItem> mMenuItems;
                private HashMap<SubMenu, SubMenu> mSubMenus;

/* 32 */        BaseMenuWrapper(T object) {
/* 33 */            super(object);
                }

/* 36 */        final SupportMenuItem getMenuItemWrapper(MenuItem frameworkItem) {
/* 37 */            if (frameworkItem == null) {
                        return null;
                    }
/* 39 */            if (this.mMenuItems == null) {
/* 40 */                this.mMenuItems = new HashMap<>();
                    }
/* 43 */            SupportMenuItem compatItem = this.mMenuItems.get(frameworkItem);
/* 45 */            if (compatItem == null) {
/* 46 */                SupportMenuItem compatItem2 = MenuWrapperFactory.createSupportMenuItemWrapper(frameworkItem);
/* 47 */                this.mMenuItems.put(frameworkItem, compatItem2);
/* 52 */                return compatItem2;
                    }
/* 52 */            return compatItem;
                }

/* 55 */        final SubMenu getSubMenuWrapper(SubMenu frameworkSubMenu) {
/* 56 */            if (frameworkSubMenu == null) {
                        return null;
                    }
/* 58 */            if (this.mSubMenus == null) {
/* 59 */                this.mSubMenus = new HashMap<>();
                    }
/* 62 */            SubMenu compatSubMenu = this.mSubMenus.get(frameworkSubMenu);
/* 64 */            if (compatSubMenu == null) {
/* 65 */                SubMenu compatSubMenu2 = MenuWrapperFactory.createSupportSubMenuWrapper(frameworkSubMenu);
/* 66 */                this.mSubMenus.put(frameworkSubMenu, compatSubMenu2);
/* 70 */                return compatSubMenu2;
                    }
/* 70 */            return compatSubMenu;
                }

/* 74 */        final void internalClear() {
/* 75 */            if (this.mMenuItems != null) {
/* 76 */                this.mMenuItems.clear();
                    }
/* 78 */            if (this.mSubMenus != null) {
/* 79 */                this.mSubMenus.clear();
                    }
                }

/* 83 */        final void internalRemoveGroup(int groupId) {
/* 84 */            if (this.mMenuItems != null) {
/* 88 */                Iterator<MenuItem> iterator = this.mMenuItems.keySet().iterator();
/* 91 */                while (iterator.hasNext()) {
/* 92 */                    MenuItem menuItem = iterator.next();
/* 93 */                    if (groupId == menuItem.getGroupId()) {
/* 94 */                        iterator.remove();
                            }
                        }
                    }
                }

/* 99 */        final void internalRemoveItem(int id) {
/* 100 */           if (this.mMenuItems != null) {
/* 104 */               Iterator<MenuItem> iterator = this.mMenuItems.keySet().iterator();
/* 107 */               while (iterator.hasNext()) {
/* 108 */                   MenuItem menuItem = iterator.next();
/* 109 */                   if (id == menuItem.getItemId()) {
/* 110 */                       iterator.remove();
/* 114 */                       return;
                            }
                        }
                    }
                }
            }
