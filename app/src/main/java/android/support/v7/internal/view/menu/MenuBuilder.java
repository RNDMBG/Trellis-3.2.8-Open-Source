            package android.support.v7.internal.view.menu;

            import android.content.ComponentName;
            import android.content.Context;
            import android.content.Intent;
            import android.content.pm.PackageManager;
            import android.content.pm.ResolveInfo;
            import android.content.res.Resources;
            import android.graphics.drawable.Drawable;
            import android.os.Bundle;
            import android.os.Parcelable;
            import android.support.v4.internal.view.SupportMenu;
            import android.support.v4.view.ActionProvider;
            import android.support.v4.view.MenuItemCompat;
            import android.support.v7.appcompat.C0084R;
            import android.util.SparseArray;
            import android.view.ContextMenu;
            import android.view.KeyCharacterMap;
            import android.view.KeyEvent;
            import android.view.MenuItem;
            import android.view.SubMenu;
            import android.view.View;
            import java.lang.ref.WeakReference;
            import java.util.ArrayList;
            import java.util.List;
            import java.util.concurrent.CopyOnWriteArrayList;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 190 */   public class MenuBuilder implements SupportMenu {
                private static final String ACTION_VIEW_STATES_KEY = "android:menu:actionviewstates";
                private static final String EXPANDED_ACTION_VIEW_ID = "android:menu:expandedactionview";
                private static final String PRESENTER_KEY = "android:menu:presenters";
                private static final String TAG = "MenuBuilder";
                private static final int[] sCategoryToOrder = {1, 4, 5, 3, 2, 0};
                private Callback mCallback;
                private final Context mContext;
                private ContextMenu.ContextMenuInfo mCurrentMenuInfo;
                private MenuItemImpl mExpandedItem;
                Drawable mHeaderIcon;
                CharSequence mHeaderTitle;
                View mHeaderView;
                private boolean mQwertyMode;
                private final Resources mResources;
                private boolean mShortcutsVisible;
                private int mDefaultShowAsAction = 0;
                private boolean mPreventDispatchingItemsChanged = false;
                private boolean mItemsChangedWhileDispatchPrevented = false;
                private boolean mOptionalIconsVisible = false;
                private boolean mIsClosing = false;
                private ArrayList<MenuItemImpl> mTempShortcutItemList = new ArrayList<>();
                private CopyOnWriteArrayList<WeakReference<MenuPresenter>> mPresenters = new CopyOnWriteArrayList<>();
                private ArrayList<MenuItemImpl> mItems = new ArrayList<>();
                private ArrayList<MenuItemImpl> mVisibleItems = new ArrayList<>();
                private boolean mIsVisibleItemsStale = true;
                private ArrayList<MenuItemImpl> mActionItems = new ArrayList<>();
                private ArrayList<MenuItemImpl> mNonActionItems = new ArrayList<>();
                private boolean mIsActionItemsStale = true;

                public interface Callback {
                    boolean onMenuItemSelected(MenuBuilder menuBuilder, MenuItem menuItem);

                    void onMenuModeChange(MenuBuilder menuBuilder);
                }

                public interface ItemInvoker {
                    boolean invokeItem(MenuItemImpl menuItemImpl);
                }

/* 191 */       public MenuBuilder(Context context) {
/* 193 */           this.mContext = context;
/* 194 */           this.mResources = context.getResources();
/* 205 */           setShortcutsVisibleInner(true);
                }

/* 208 */       public MenuBuilder setDefaultShowAsAction(int defaultShowAsAction) {
/* 209 */           this.mDefaultShowAsAction = defaultShowAsAction;
/* 210 */           return this;
                }

/* 219 */       public void addMenuPresenter(MenuPresenter presenter) {
/* 220 */           this.mPresenters.add(new WeakReference<>(presenter));
/* 221 */           presenter.initForMenu(this.mContext, this);
/* 222 */           this.mIsActionItemsStale = true;
                }

/* 231 */       public void removeMenuPresenter(MenuPresenter presenter) {
                    for (WeakReference<MenuPresenter> ref : this.mPresenters) {
/* 233 */               MenuPresenter item = ref.get();
/* 234 */               if (item == null || item == presenter) {
/* 235 */                   this.mPresenters.remove(ref);
                        }
                    }
                }

/* 240 */       private void dispatchPresenterUpdate(boolean cleared) {
/* 241 */           if (!this.mPresenters.isEmpty()) {
/* 245 */               stopDispatchingItemsChanged();
                        for (WeakReference<MenuPresenter> ref : this.mPresenters) {
/* 247 */                   MenuPresenter presenter = ref.get();
/* 248 */                   if (presenter == null) {
/* 249 */                       this.mPresenters.remove(ref);
                            } else {
/* 251 */                       presenter.updateMenuView(cleared);
                            }
                        }
/* 254 */               startDispatchingItemsChanged();
                    }
                }

/* 257 */       private boolean dispatchSubMenuSelected(SubMenuBuilder subMenu) {
/* 258 */           if (this.mPresenters.isEmpty()) {
/* 259 */               return false;
                    }
/* 262 */           boolean result = false;
                    for (WeakReference<MenuPresenter> ref : this.mPresenters) {
/* 265 */               MenuPresenter presenter = ref.get();
/* 266 */               if (presenter == null) {
/* 267 */                   this.mPresenters.remove(ref);
                        } else if (!result) {
/* 269 */                   result = presenter.onSubMenuSelected(subMenu);
                        }
                    }
/* 272 */           return result;
                }

/* 275 */       private void dispatchSaveInstanceState(Bundle outState) {
                    Parcelable state;
/* 276 */           if (!this.mPresenters.isEmpty()) {
/* 280 */               SparseArray<Parcelable> presenterStates = new SparseArray<>();
                        for (WeakReference<MenuPresenter> ref : this.mPresenters) {
/* 283 */                   MenuPresenter presenter = ref.get();
/* 284 */                   if (presenter == null) {
/* 285 */                       this.mPresenters.remove(ref);
                            } else {
/* 287 */                       int id = presenter.getId();
/* 288 */                       if (id > 0 && (state = presenter.onSaveInstanceState()) != null) {
/* 291 */                           presenterStates.put(id, state);
                                }
                            }
                        }
/* 297 */               outState.putSparseParcelableArray(PRESENTER_KEY, presenterStates);
                    }
                }

/* 300 */       private void dispatchRestoreInstanceState(Bundle state) {
                    Parcelable parcel;
/* 301 */           SparseArray<Parcelable> presenterStates = state.getSparseParcelableArray(PRESENTER_KEY);
/* 303 */           if (presenterStates != null && !this.mPresenters.isEmpty()) {
                        for (WeakReference<MenuPresenter> ref : this.mPresenters) {
/* 308 */                   MenuPresenter presenter = ref.get();
/* 309 */                   if (presenter == null) {
/* 310 */                       this.mPresenters.remove(ref);
                            } else {
/* 312 */                       int id = presenter.getId();
/* 313 */                       if (id > 0 && (parcel = presenterStates.get(id)) != null) {
/* 316 */                           presenter.onRestoreInstanceState(parcel);
                                }
                            }
                        }
                    }
                }

/* 323 */       public void savePresenterStates(Bundle outState) {
/* 324 */           dispatchSaveInstanceState(outState);
                }

/* 327 */       public void restorePresenterStates(Bundle state) {
/* 328 */           dispatchRestoreInstanceState(state);
                }

/* 331 */       public void saveActionViewStates(Bundle outStates) {
/* 332 */           SparseArray<Parcelable> viewStates = null;
/* 334 */           int itemCount = size();
                    for (int i = 0; i < itemCount; i++) {
/* 336 */               MenuItem item = getItem(i);
/* 337 */               View v = MenuItemCompat.getActionView(item);
/* 338 */               if (v != null && v.getId() != -1) {
/* 339 */                   if (viewStates == null) {
/* 340 */                       viewStates = new SparseArray<>();
                            }
/* 342 */                   v.saveHierarchyState(viewStates);
/* 343 */                   if (MenuItemCompat.isActionViewExpanded(item)) {
/* 344 */                       outStates.putInt(EXPANDED_ACTION_VIEW_ID, item.getItemId());
                            }
                        }
/* 347 */               if (item.hasSubMenu()) {
/* 348 */                   SubMenuBuilder subMenu = (SubMenuBuilder) item.getSubMenu();
/* 349 */                   subMenu.saveActionViewStates(outStates);
                        }
                    }
/* 353 */           if (viewStates != null) {
/* 354 */               outStates.putSparseParcelableArray(getActionViewStatesKey(), viewStates);
                    }
                }

/* 358 */       public void restoreActionViewStates(Bundle states) {
                    MenuItem itemToExpand;
/* 359 */           if (states != null) {
/* 363 */               SparseArray<Parcelable> viewStates = states.getSparseParcelableArray(getActionViewStatesKey());
/* 366 */               int itemCount = size();
                        for (int i = 0; i < itemCount; i++) {
/* 368 */                   MenuItem item = getItem(i);
/* 369 */                   View v = MenuItemCompat.getActionView(item);
/* 370 */                   if (v != null && v.getId() != -1) {
/* 371 */                       v.restoreHierarchyState(viewStates);
                            }
/* 373 */                   if (item.hasSubMenu()) {
/* 374 */                       SubMenuBuilder subMenu = (SubMenuBuilder) item.getSubMenu();
/* 375 */                       subMenu.restoreActionViewStates(states);
                            }
                        }
/* 379 */               int expandedId = states.getInt(EXPANDED_ACTION_VIEW_ID);
/* 380 */               if (expandedId > 0 && (itemToExpand = findItem(expandedId)) != null) {
/* 383 */                   MenuItemCompat.expandActionView(itemToExpand);
                        }
                    }
                }

/* 388 */       protected String getActionViewStatesKey() {
/* 389 */           return ACTION_VIEW_STATES_KEY;
                }

/* 392 */       public void setCallback(Callback cb) {
/* 393 */           this.mCallback = cb;
                }

/* 399 */       private MenuItem addInternal(int group, int id, int categoryOrder, CharSequence title) {
/* 400 */           int ordering = getOrdering(categoryOrder);
/* 402 */           MenuItemImpl item = new MenuItemImpl(this, group, id, categoryOrder, ordering, title, this.mDefaultShowAsAction);
/* 405 */           if (this.mCurrentMenuInfo != null) {
/* 407 */               item.setMenuInfo(this.mCurrentMenuInfo);
                    }
/* 410 */           this.mItems.add(findInsertIndex(this.mItems, ordering), item);
/* 411 */           onItemsChanged(true);
/* 413 */           return item;
                }

                @Override // android.view.Menu
/* 417 */       public MenuItem add(CharSequence title) {
/* 418 */           return addInternal(0, 0, 0, title);
                }

                @Override // android.view.Menu
/* 422 */       public MenuItem add(int titleRes) {
/* 423 */           return addInternal(0, 0, 0, this.mResources.getString(titleRes));
                }

                @Override // android.view.Menu
/* 427 */       public MenuItem add(int group, int id, int categoryOrder, CharSequence title) {
/* 428 */           return addInternal(group, id, categoryOrder, title);
                }

                @Override // android.view.Menu
/* 432 */       public MenuItem add(int group, int id, int categoryOrder, int title) {
/* 433 */           return addInternal(group, id, categoryOrder, this.mResources.getString(title));
                }

                @Override // android.view.Menu
/* 437 */       public SubMenu addSubMenu(CharSequence title) {
/* 438 */           return addSubMenu(0, 0, 0, title);
                }

                @Override // android.view.Menu
/* 442 */       public SubMenu addSubMenu(int titleRes) {
/* 443 */           return addSubMenu(0, 0, 0, this.mResources.getString(titleRes));
                }

                @Override // android.view.Menu
/* 447 */       public SubMenu addSubMenu(int group, int id, int categoryOrder, CharSequence title) {
/* 448 */           MenuItemImpl item = (MenuItemImpl) addInternal(group, id, categoryOrder, title);
/* 449 */           SubMenuBuilder subMenu = new SubMenuBuilder(this.mContext, this, item);
/* 450 */           item.setSubMenu(subMenu);
/* 452 */           return subMenu;
                }

                @Override // android.view.Menu
/* 456 */       public SubMenu addSubMenu(int group, int id, int categoryOrder, int title) {
/* 457 */           return addSubMenu(group, id, categoryOrder, this.mResources.getString(title));
                }

                @Override // android.view.Menu
/* 462 */       public int addIntentOptions(int group, int id, int categoryOrder, ComponentName caller, Intent[] specifics, Intent intent, int flags, MenuItem[] outSpecificItems) {
/* 463 */           PackageManager pm = this.mContext.getPackageManager();
/* 464 */           List<ResolveInfo> lri = pm.queryIntentActivityOptions(caller, specifics, intent, 0);
/* 466 */           int N = lri != null ? lri.size() : 0;
/* 468 */           if ((flags & 1) == 0) {
/* 469 */               removeGroup(group);
                    }
                    for (int i = 0; i < N; i++) {
/* 473 */               ResolveInfo ri = lri.get(i);
/* 474 */               Intent rintent = new Intent(ri.specificIndex < 0 ? intent : specifics[ri.specificIndex]);
/* 476 */               rintent.setComponent(new ComponentName(ri.activityInfo.applicationInfo.packageName, ri.activityInfo.name));
/* 479 */               MenuItem item = add(group, id, categoryOrder, ri.loadLabel(pm)).setIcon(ri.loadIcon(pm)).setIntent(rintent);
/* 482 */               if (outSpecificItems != null && ri.specificIndex >= 0) {
/* 483 */                   outSpecificItems[ri.specificIndex] = item;
                        }
                    }
/* 487 */           return N;
                }

                @Override // android.view.Menu
/* 491 */       public void removeItem(int id) {
/* 492 */           removeItemAtInt(findItemIndex(id), true);
                }

                @Override // android.view.Menu
/* 496 */       public void removeGroup(int group) {
/* 497 */           int i = findGroupIndex(group);
/* 499 */           if (i >= 0) {
/* 500 */               int maxRemovable = this.mItems.size() - i;
/* 501 */               int numRemoved = 0;
                        while (true) {
                            int numRemoved2 = numRemoved;
/* 502 */                   numRemoved = numRemoved2 + 1;
                            if (numRemoved2 >= maxRemovable || this.mItems.get(i).getGroupId() != group) {
                                break;
                            } else {
/* 504 */                       removeItemAtInt(i, false);
                            }
                        }
/* 508 */               onItemsChanged(true);
                    }
                }

/* 520 */       private void removeItemAtInt(int index, boolean updateChildrenOnMenuViews) {
/* 521 */           if (index >= 0 && index < this.mItems.size()) {
/* 525 */               this.mItems.remove(index);
/* 527 */               if (updateChildrenOnMenuViews) {
/* 528 */                   onItemsChanged(true);
                        }
                    }
                }

/* 532 */       public void removeItemAt(int index) {
/* 533 */           removeItemAtInt(index, true);
                }

/* 536 */       public void clearAll() {
/* 537 */           this.mPreventDispatchingItemsChanged = true;
/* 538 */           clear();
/* 539 */           clearHeader();
/* 540 */           this.mPreventDispatchingItemsChanged = false;
/* 541 */           this.mItemsChangedWhileDispatchPrevented = false;
/* 542 */           onItemsChanged(true);
                }

                @Override // android.view.Menu
/* 546 */       public void clear() {
/* 547 */           if (this.mExpandedItem != null) {
/* 548 */               collapseItemActionView(this.mExpandedItem);
                    }
/* 550 */           this.mItems.clear();
/* 552 */           onItemsChanged(true);
                }

/* 555 */       void setExclusiveItemChecked(MenuItem item) {
/* 556 */           int group = item.getGroupId();
/* 558 */           int N = this.mItems.size();
                    for (int i = 0; i < N; i++) {
/* 560 */               MenuItemImpl curItem = this.mItems.get(i);
/* 561 */               if (curItem.getGroupId() == group && curItem.isExclusiveCheckable() && curItem.isCheckable()) {
/* 570 */                   curItem.setCheckedInt(curItem == item);
                        }
                    }
                }

                @Override // android.view.Menu
/* 576 */       public void setGroupCheckable(int group, boolean checkable, boolean exclusive) {
/* 577 */           int N = this.mItems.size();
                    for (int i = 0; i < N; i++) {
/* 580 */               MenuItemImpl item = this.mItems.get(i);
/* 581 */               if (item.getGroupId() == group) {
/* 582 */                   item.setExclusiveCheckable(exclusive);
/* 583 */                   item.setCheckable(checkable);
                        }
                    }
                }

                @Override // android.view.Menu
/* 589 */       public void setGroupVisible(int group, boolean visible) {
/* 590 */           int N = this.mItems.size();
/* 595 */           boolean changedAtLeastOneItem = false;
                    for (int i = 0; i < N; i++) {
/* 597 */               MenuItemImpl item = this.mItems.get(i);
/* 598 */               if (item.getGroupId() == group && item.setVisibleInt(visible)) {
/* 600 */                   changedAtLeastOneItem = true;
                        }
                    }
/* 605 */           if (changedAtLeastOneItem) {
/* 606 */               onItemsChanged(true);
                    }
                }

                @Override // android.view.Menu
/* 611 */       public void setGroupEnabled(int group, boolean enabled) {
/* 612 */           int N = this.mItems.size();
                    for (int i = 0; i < N; i++) {
/* 615 */               MenuItemImpl item = this.mItems.get(i);
/* 616 */               if (item.getGroupId() == group) {
/* 617 */                   item.setEnabled(enabled);
                        }
                    }
                }

                @Override // android.view.Menu
/* 623 */       public boolean hasVisibleItems() {
/* 624 */           int size = size();
                    for (int i = 0; i < size; i++) {
/* 627 */               MenuItemImpl item = this.mItems.get(i);
/* 628 */               if (item.isVisible()) {
/* 629 */                   return true;
                        }
                    }
/* 633 */           return false;
                }

                @Override // android.view.Menu
/* 637 */       public MenuItem findItem(int id) {
                    MenuItem possibleItem;
/* 638 */           int size = size();
                    for (int i = 0; i < size; i++) {
/* 640 */               MenuItemImpl item = this.mItems.get(i);
/* 641 */               if (item.getItemId() != id) {
/* 643 */                   if (item.hasSubMenu() && (possibleItem = item.getSubMenu().findItem(id)) != null) {
                                return possibleItem;
                            }
                        } else {
/* 652 */                   return item;
                        }
                    }
/* 652 */           return null;
                }

/* 655 */       public int findItemIndex(int id) {
/* 656 */           int size = size();
                    for (int i = 0; i < size; i++) {
/* 659 */               MenuItemImpl item = this.mItems.get(i);
/* 660 */               if (item.getItemId() == id) {
/* 665 */                   return i;
                        }
                    }
/* 665 */           return -1;
                }

/* 668 */       public int findGroupIndex(int group) {
/* 669 */           return findGroupIndex(group, 0);
                }

/* 672 */       public int findGroupIndex(int group, int start) {
/* 673 */           int size = size();
/* 675 */           if (start < 0) {
/* 676 */               start = 0;
                    }
                    for (int i = start; i < size; i++) {
/* 680 */               MenuItemImpl item = this.mItems.get(i);
/* 682 */               if (item.getGroupId() == group) {
/* 687 */                   return i;
                        }
                    }
/* 687 */           return -1;
                }

                @Override // android.view.Menu
/* 691 */       public int size() {
/* 692 */           return this.mItems.size();
                }

                @Override // android.view.Menu
/* 696 */       public MenuItem getItem(int index) {
/* 697 */           return this.mItems.get(index);
                }

                @Override // android.view.Menu
/* 701 */       public boolean isShortcutKey(int keyCode, KeyEvent event) {
                    return findItemWithShortcutForKey(keyCode, event) != null;
                }

                @Override // android.view.Menu
/* 706 */       public void setQwertyMode(boolean isQwerty) {
/* 707 */           this.mQwertyMode = isQwerty;
/* 709 */           onItemsChanged(false);
                }

/* 722 */       private static int getOrdering(int categoryOrder) {
/* 723 */           int index = ((-65536) & categoryOrder) >> 16;
/* 725 */           if (index < 0 || index >= sCategoryToOrder.length) {
/* 726 */               throw new IllegalArgumentException("order does not contain a valid category.");
                    }
/* 729 */           return (sCategoryToOrder[index] << 16) | (65535 & categoryOrder);
                }

/* 735 */       boolean isQwertyMode() {
/* 736 */           return this.mQwertyMode;
                }

/* 746 */       public void setShortcutsVisible(boolean shortcutsVisible) {
/* 747 */           if (this.mShortcutsVisible != shortcutsVisible) {
/* 751 */               setShortcutsVisibleInner(shortcutsVisible);
/* 752 */               onItemsChanged(false);
                    }
                }

/* 755 */       private void setShortcutsVisibleInner(boolean shortcutsVisible) {
/* 756 */           this.mShortcutsVisible = shortcutsVisible && this.mResources.getConfiguration().keyboard != 1 && this.mResources.getBoolean(C0084R.bool.abc_config_showMenuShortcutsWhenKeyboardPresent);
                }

/* 764 */       public boolean isShortcutsVisible() {
/* 765 */           return this.mShortcutsVisible;
                }

/* 768 */       Resources getResources() {
/* 769 */           return this.mResources;
                }

/* 772 */       public Context getContext() {
/* 773 */           return this.mContext;
                }

/* 776 */       boolean dispatchMenuItemSelected(MenuBuilder menu, MenuItem item) {
                    return this.mCallback != null && this.mCallback.onMenuItemSelected(menu, item);
                }

/* 783 */       public void changeMenuMode() {
/* 784 */           if (this.mCallback != null) {
/* 785 */               this.mCallback.onMenuModeChange(this);
                    }
                }

/* 789 */       private static int findInsertIndex(ArrayList<MenuItemImpl> items, int ordering) {
                    for (int i = items.size() - 1; i >= 0; i--) {
/* 791 */               MenuItemImpl item = items.get(i);
/* 792 */               if (item.getOrdering() <= ordering) {
/* 793 */                   return i + 1;
                        }
                    }
/* 797 */           return 0;
                }

                @Override // android.view.Menu
/* 801 */       public boolean performShortcut(int keyCode, KeyEvent event, int flags) {
/* 802 */           MenuItemImpl item = findItemWithShortcutForKey(keyCode, event);
/* 804 */           boolean handled = false;
/* 806 */           if (item != null) {
/* 807 */               handled = performItemAction(item, flags);
                    }
/* 810 */           if ((flags & 2) != 0) {
/* 811 */               close(true);
                    }
/* 814 */           return handled;
                }

/* 824 */       void findItemsWithShortcutForKey(List<MenuItemImpl> items, int keyCode, KeyEvent event) {
/* 825 */           boolean qwerty = isQwertyMode();
/* 826 */           int metaState = event.getMetaState();
/* 827 */           KeyCharacterMap.KeyData possibleChars = new KeyCharacterMap.KeyData();
/* 829 */           boolean isKeyCodeMapped = event.getKeyData(possibleChars);
/* 831 */           if (isKeyCodeMapped || keyCode == 67) {
/* 836 */               int N = this.mItems.size();
                        for (int i = 0; i < N; i++) {
/* 838 */                   MenuItemImpl item = this.mItems.get(i);
/* 839 */                   if (item.hasSubMenu()) {
/* 840 */                       ((MenuBuilder) item.getSubMenu()).findItemsWithShortcutForKey(items, keyCode, event);
                            }
/* 843 */                   char shortcutChar = qwerty ? item.getAlphabeticShortcut() : item.getNumericShortcut();
/* 845 */                   if ((metaState & 5) == 0 && shortcutChar != 0 && ((shortcutChar == possibleChars.meta[0] || shortcutChar == possibleChars.meta[2] || (qwerty && shortcutChar == '\b' && keyCode == 67)) && item.isEnabled())) {
/* 852 */                       items.add(item);
                            }
                        }
                    }
                }

/* 870 */       MenuItemImpl findItemWithShortcutForKey(int keyCode, KeyEvent event) {
/* 871 */           ArrayList<MenuItemImpl> items = this.mTempShortcutItemList;
/* 872 */           items.clear();
/* 873 */           findItemsWithShortcutForKey(items, keyCode, event);
/* 875 */           if (items.isEmpty()) {
                        return null;
                    }
/* 879 */           int metaState = event.getMetaState();
/* 880 */           KeyCharacterMap.KeyData possibleChars = new KeyCharacterMap.KeyData();
/* 882 */           event.getKeyData(possibleChars);
/* 885 */           int size = items.size();
/* 886 */           if (size == 1) {
/* 887 */               return items.get(0);
                    }
/* 890 */           boolean qwerty = isQwertyMode();
                    for (int i = 0; i < size; i++) {
/* 894 */               MenuItemImpl item = items.get(i);
/* 895 */               char shortcutChar = qwerty ? item.getAlphabeticShortcut() : item.getNumericShortcut();
/* 897 */               if ((shortcutChar == possibleChars.meta[0] && (metaState & 2) == 0) || ((shortcutChar == possibleChars.meta[2] && (metaState & 2) != 0) || (qwerty && shortcutChar == '\b' && keyCode == 67))) {
                            return item;
                        }
                    }
                    return null;
                }

                @Override // android.view.Menu
/* 911 */       public boolean performIdentifierAction(int id, int flags) {
/* 912 */           return performItemAction(findItem(id), flags);
                }

/* 915 */       public boolean performItemAction(MenuItem item, int flags) {
/* 916 */           MenuItemImpl itemImpl = (MenuItemImpl) item;
/* 918 */           if (itemImpl == null || !itemImpl.isEnabled()) {
                        return false;
                    }
/* 922 */           boolean invoked = itemImpl.invoke();
/* 924 */           ActionProvider provider = itemImpl.getSupportActionProvider();
/* 925 */           boolean providerHasSubMenu = provider != null && provider.hasSubMenu();
/* 926 */           if (itemImpl.hasCollapsibleActionView()) {
/* 927 */               boolean invoked2 = invoked | itemImpl.expandActionView();
/* 928 */               if (invoked2) {
/* 929 */                   close(true);
/* 952 */                   return invoked2;
                        }
/* 952 */               return invoked2;
                    }
/* 931 */           if (itemImpl.hasSubMenu() || providerHasSubMenu) {
/* 932 */               close(false);
/* 934 */               if (!itemImpl.hasSubMenu()) {
/* 935 */                   itemImpl.setSubMenu(new SubMenuBuilder(getContext(), this, itemImpl));
                        }
/* 938 */               SubMenuBuilder subMenu = (SubMenuBuilder) itemImpl.getSubMenu();
/* 939 */               if (providerHasSubMenu) {
/* 940 */                   provider.onPrepareSubMenu(subMenu);
                        }
/* 942 */               boolean invoked3 = invoked | dispatchSubMenuSelected(subMenu);
/* 943 */               if (!invoked3) {
/* 944 */                   close(true);
/* 952 */                   return invoked3;
                        }
/* 952 */               return invoked3;
                    }
/* 947 */           if ((flags & 1) == 0) {
/* 948 */               close(true);
/* 952 */               return invoked;
                    }
/* 952 */           return invoked;
                }

/* 963 */       final void close(boolean allMenusAreClosing) {
/* 964 */           if (!this.mIsClosing) {
/* 968 */               this.mIsClosing = true;
                        for (WeakReference<MenuPresenter> ref : this.mPresenters) {
/* 970 */                   MenuPresenter presenter = ref.get();
/* 971 */                   if (presenter == null) {
/* 972 */                       this.mPresenters.remove(ref);
                            } else {
/* 974 */                       presenter.onCloseMenu(this, allMenusAreClosing);
                            }
                        }
/* 977 */               this.mIsClosing = false;
                    }
                }

                @Override // android.view.Menu
/* 981 */       public void close() {
/* 982 */           close(true);
                }

/* 992 */       void onItemsChanged(boolean structureChanged) {
/* 993 */           if (!this.mPreventDispatchingItemsChanged) {
/* 994 */               if (structureChanged) {
/* 995 */                   this.mIsVisibleItemsStale = true;
/* 996 */                   this.mIsActionItemsStale = true;
                        }
/* 999 */               dispatchPresenterUpdate(structureChanged);
/* 1003 */              return;
                    }
/* 1001 */          this.mItemsChangedWhileDispatchPrevented = true;
                }

/* 1010 */      public void stopDispatchingItemsChanged() {
/* 1011 */          if (!this.mPreventDispatchingItemsChanged) {
/* 1012 */              this.mPreventDispatchingItemsChanged = true;
/* 1013 */              this.mItemsChangedWhileDispatchPrevented = false;
                    }
                }

/* 1017 */      public void startDispatchingItemsChanged() {
/* 1018 */          this.mPreventDispatchingItemsChanged = false;
/* 1020 */          if (this.mItemsChangedWhileDispatchPrevented) {
/* 1021 */              this.mItemsChangedWhileDispatchPrevented = false;
/* 1022 */              onItemsChanged(true);
                    }
                }

/* 1032 */      void onItemVisibleChanged(MenuItemImpl item) {
/* 1033 */          this.mIsVisibleItemsStale = true;
/* 1034 */          onItemsChanged(true);
                }

/* 1043 */      void onItemActionRequestChanged(MenuItemImpl item) {
/* 1044 */          this.mIsActionItemsStale = true;
/* 1045 */          onItemsChanged(true);
                }

/* 1048 */      ArrayList<MenuItemImpl> getVisibleItems() {
/* 1049 */          if (!this.mIsVisibleItemsStale) {
/* 1050 */              return this.mVisibleItems;
                    }
/* 1054 */          this.mVisibleItems.clear();
/* 1056 */          int itemsSize = this.mItems.size();
                    for (int i = 0; i < itemsSize; i++) {
/* 1059 */              MenuItemImpl item = this.mItems.get(i);
/* 1060 */              if (item.isVisible()) {
/* 1061 */                  this.mVisibleItems.add(item);
                        }
                    }
/* 1065 */          this.mIsVisibleItemsStale = false;
/* 1066 */          this.mIsActionItemsStale = true;
/* 1068 */          return this.mVisibleItems;
                }

/* 1095 */      public void flagActionItems() {
/* 1096 */          if (this.mIsActionItemsStale) {
/* 1101 */              boolean flagged = false;
                        for (WeakReference<MenuPresenter> ref : this.mPresenters) {
/* 1103 */                  MenuPresenter presenter = ref.get();
/* 1104 */                  if (presenter == null) {
/* 1105 */                      this.mPresenters.remove(ref);
                            } else {
/* 1107 */                      flagged |= presenter.flagActionItems();
                            }
                        }
/* 1111 */              if (flagged) {
/* 1112 */                  this.mActionItems.clear();
/* 1113 */                  this.mNonActionItems.clear();
/* 1114 */                  ArrayList<MenuItemImpl> visibleItems = getVisibleItems();
/* 1115 */                  int itemsSize = visibleItems.size();
                            for (int i = 0; i < itemsSize; i++) {
/* 1117 */                      MenuItemImpl item = visibleItems.get(i);
/* 1118 */                      if (item.isActionButton()) {
/* 1119 */                          this.mActionItems.add(item);
                                } else {
/* 1121 */                          this.mNonActionItems.add(item);
                                }
                            }
                        } else {
/* 1127 */                  this.mActionItems.clear();
/* 1128 */                  this.mNonActionItems.clear();
/* 1129 */                  this.mNonActionItems.addAll(getVisibleItems());
                        }
/* 1131 */              this.mIsActionItemsStale = false;
                    }
                }

/* 1134 */      ArrayList<MenuItemImpl> getActionItems() {
/* 1135 */          flagActionItems();
/* 1136 */          return this.mActionItems;
                }

/* 1139 */      ArrayList<MenuItemImpl> getNonActionItems() {
/* 1140 */          flagActionItems();
/* 1141 */          return this.mNonActionItems;
                }

/* 1144 */      public void clearHeader() {
/* 1145 */          this.mHeaderIcon = null;
/* 1146 */          this.mHeaderTitle = null;
/* 1147 */          this.mHeaderView = null;
/* 1149 */          onItemsChanged(false);
                }

/* 1153 */      private void setHeaderInternal(int titleRes, CharSequence title, int iconRes, Drawable icon, View view) {
/* 1154 */          Resources r = getResources();
/* 1156 */          if (view != null) {
/* 1157 */              this.mHeaderView = view;
/* 1160 */              this.mHeaderTitle = null;
/* 1161 */              this.mHeaderIcon = null;
                    } else {
/* 1163 */              if (titleRes > 0) {
/* 1164 */                  this.mHeaderTitle = r.getText(titleRes);
                        } else if (title != null) {
/* 1166 */                  this.mHeaderTitle = title;
                        }
/* 1169 */              if (iconRes > 0) {
/* 1170 */                  this.mHeaderIcon = r.getDrawable(iconRes);
                        } else if (icon != null) {
/* 1172 */                  this.mHeaderIcon = icon;
                        }
/* 1176 */              this.mHeaderView = null;
                    }
/* 1180 */          onItemsChanged(false);
                }

/* 1190 */      protected MenuBuilder setHeaderTitleInt(CharSequence title) {
/* 1191 */          setHeaderInternal(0, title, 0, null, null);
/* 1192 */          return this;
                }

/* 1202 */      protected MenuBuilder setHeaderTitleInt(int titleRes) {
/* 1203 */          setHeaderInternal(titleRes, null, 0, null, null);
/* 1204 */          return this;
                }

/* 1214 */      protected MenuBuilder setHeaderIconInt(Drawable icon) {
/* 1215 */          setHeaderInternal(0, null, 0, icon, null);
/* 1216 */          return this;
                }

/* 1226 */      protected MenuBuilder setHeaderIconInt(int iconRes) {
/* 1227 */          setHeaderInternal(0, null, iconRes, null, null);
/* 1228 */          return this;
                }

/* 1238 */      protected MenuBuilder setHeaderViewInt(View view) {
/* 1239 */          setHeaderInternal(0, null, 0, null, view);
/* 1240 */          return this;
                }

/* 1243 */      public CharSequence getHeaderTitle() {
/* 1244 */          return this.mHeaderTitle;
                }

/* 1247 */      public Drawable getHeaderIcon() {
/* 1248 */          return this.mHeaderIcon;
                }

/* 1251 */      public View getHeaderView() {
/* 1252 */          return this.mHeaderView;
                }

/* 1260 */      public MenuBuilder getRootMenu() {
/* 1261 */          return this;
                }

/* 1271 */      public void setCurrentMenuInfo(ContextMenu.ContextMenuInfo menuInfo) {
/* 1272 */          this.mCurrentMenuInfo = menuInfo;
                }

/* 1275 */      void setOptionalIconsVisible(boolean visible) {
/* 1276 */          this.mOptionalIconsVisible = visible;
                }

/* 1279 */      boolean getOptionalIconsVisible() {
/* 1280 */          return this.mOptionalIconsVisible;
                }

/* 1283 */      public boolean expandItemActionView(MenuItemImpl item) {
/* 1284 */          if (this.mPresenters.isEmpty()) {
/* 1285 */              return false;
                    }
/* 1288 */          boolean expanded = false;
/* 1290 */          stopDispatchingItemsChanged();
                    for (WeakReference<MenuPresenter> ref : this.mPresenters) {
/* 1292 */              MenuPresenter presenter = ref.get();
/* 1293 */              if (presenter == null) {
/* 1294 */                  this.mPresenters.remove(ref);
                        } else {
/* 1295 */                  expanded = presenter.expandItemActionView(this, item);
                            if (expanded) {
                                break;
                            }
                        }
                    }
/* 1299 */          startDispatchingItemsChanged();
/* 1301 */          if (expanded) {
/* 1302 */              this.mExpandedItem = item;
/* 1304 */              return expanded;
                    }
/* 1304 */          return expanded;
                }

/* 1307 */      public boolean collapseItemActionView(MenuItemImpl item) {
/* 1308 */          if (this.mPresenters.isEmpty() || this.mExpandedItem != item) {
/* 1309 */              return false;
                    }
/* 1312 */          boolean collapsed = false;
/* 1314 */          stopDispatchingItemsChanged();
                    for (WeakReference<MenuPresenter> ref : this.mPresenters) {
/* 1316 */              MenuPresenter presenter = ref.get();
/* 1317 */              if (presenter == null) {
/* 1318 */                  this.mPresenters.remove(ref);
                        } else {
/* 1319 */                  collapsed = presenter.collapseItemActionView(this, item);
                            if (collapsed) {
                                break;
                            }
                        }
                    }
/* 1323 */          startDispatchingItemsChanged();
/* 1325 */          if (collapsed) {
/* 1326 */              this.mExpandedItem = null;
/* 1328 */              return collapsed;
                    }
/* 1328 */          return collapsed;
                }

/* 1331 */      public MenuItemImpl getExpandedItem() {
/* 1332 */          return this.mExpandedItem;
                }
            }
