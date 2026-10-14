            package android.support.v7.internal.view.menu;

            import android.content.Context;
            import android.content.DialogInterface;
            import android.content.res.Configuration;
            import android.content.res.Resources;
            import android.os.Parcel;
            import android.os.Parcelable;
            import android.support.v4.view.ActionProvider;
            import android.support.v7.appcompat.C0084R;
            import android.support.v7.internal.view.ActionBarPolicy;
            import android.util.SparseBooleanArray;
            import android.view.MenuItem;
            import android.view.View;
            import android.view.ViewGroup;
            import android.widget.ImageButton;
            import java.util.ArrayList;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 42 */    public class ActionMenuPresenter extends BaseMenuPresenter implements ActionProvider.SubUiVisibilityListener {
                private static final String TAG = "ActionMenuPresenter";
                private final SparseBooleanArray mActionButtonGroups;
                private ActionButtonSubmenu mActionButtonPopup;
                private int mActionItemWidthLimit;
                private boolean mExpandedActionViewsExclusive;
                private int mMaxItems;
                private boolean mMaxItemsSet;
                private int mMinCellSize;
                int mOpenSubMenuId;
                private View mOverflowButton;
                private OverflowPopup mOverflowPopup;
                final PopupPresenterCallback mPopupPresenterCallback;
                private OpenOverflowRunnable mPostedOpenRunnable;
                private boolean mReserveOverflow;
                private boolean mReserveOverflowSet;
                private View mScrapActionButtonView;
                private boolean mStrictWidthLimit;
                private int mWidthLimit;
                private boolean mWidthLimitSet;

/* 43 */        static /* synthetic */ OverflowPopup access$102(ActionMenuPresenter x0, OverflowPopup x1) {
/* 44 */            x0.mOverflowPopup = x1;
                    return x1;
                }

/* 43 */        static /* synthetic */ ActionButtonSubmenu access$202(ActionMenuPresenter x0, ActionButtonSubmenu x1) {
/* 44 */            x0.mActionButtonPopup = x1;
                    return x1;
                }

/* 43 */        static /* synthetic */ OpenOverflowRunnable access$302(ActionMenuPresenter x0, OpenOverflowRunnable x1) {
/* 44 */            x0.mPostedOpenRunnable = x1;
                    return x1;
                }

/* 75 */        public ActionMenuPresenter(Context context) {
/* 76 */            super(context, C0084R.layout.abc_action_menu_layout, C0084R.layout.abc_action_menu_item_layout);
/* 63 */            this.mActionButtonGroups = new SparseBooleanArray();
/* 72 */            this.mPopupPresenterCallback = new PopupPresenterCallback(this, null);
                }

                @Override // android.support.v7.internal.view.menu.BaseMenuPresenter, android.support.v7.internal.view.menu.MenuPresenter
/* 80 */        public void initForMenu(Context context, MenuBuilder menu) {
/* 81 */            super.initForMenu(context, menu);
/* 83 */            Resources res = context.getResources();
/* 85 */            ActionBarPolicy abp = ActionBarPolicy.get(context);
/* 86 */            if (!this.mReserveOverflowSet) {
/* 87 */                this.mReserveOverflow = abp.showsOverflowMenuButton();
                    }
/* 90 */            if (!this.mWidthLimitSet) {
/* 91 */                this.mWidthLimit = abp.getEmbeddedMenuWidthLimit();
                    }
/* 95 */            if (!this.mMaxItemsSet) {
/* 96 */                this.mMaxItems = abp.getMaxActionButtons();
                    }
/* 99 */            int width = this.mWidthLimit;
/* 100 */           if (this.mReserveOverflow) {
/* 101 */               if (this.mOverflowButton == null) {
/* 102 */                   this.mOverflowButton = new OverflowMenuButton(this.mSystemContext);
/* 103 */                   int spec = View.MeasureSpec.makeMeasureSpec(0, 0);
/* 104 */                   this.mOverflowButton.measure(spec, spec);
                        }
/* 106 */               width -= this.mOverflowButton.getMeasuredWidth();
                    } else {
/* 108 */               this.mOverflowButton = null;
                    }
/* 111 */           this.mActionItemWidthLimit = width;
/* 113 */           this.mMinCellSize = (int) (56.0f * res.getDisplayMetrics().density);
/* 116 */           this.mScrapActionButtonView = null;
                }

/* 119 */       public void onConfigurationChanged(Configuration newConfig) {
/* 120 */           if (!this.mMaxItemsSet) {
/* 121 */               this.mMaxItems = this.mContext.getResources().getInteger(C0084R.integer.abc_max_action_buttons);
                    }
/* 124 */           if (this.mMenu != null) {
/* 125 */               this.mMenu.onItemsChanged(true);
                    }
                }

/* 129 */       public void setWidthLimit(int width, boolean strict) {
/* 130 */           this.mWidthLimit = width;
/* 131 */           this.mStrictWidthLimit = strict;
/* 132 */           this.mWidthLimitSet = true;
                }

/* 135 */       public void setReserveOverflow(boolean reserveOverflow) {
/* 136 */           this.mReserveOverflow = reserveOverflow;
/* 137 */           this.mReserveOverflowSet = true;
                }

/* 140 */       public void setItemLimit(int itemCount) {
/* 141 */           this.mMaxItems = itemCount;
/* 142 */           this.mMaxItemsSet = true;
                }

/* 145 */       public void setExpandedActionViewsExclusive(boolean isExclusive) {
/* 146 */           this.mExpandedActionViewsExclusive = isExclusive;
                }

                @Override // android.support.v7.internal.view.menu.BaseMenuPresenter, android.support.v7.internal.view.menu.MenuPresenter
/* 150 */       public MenuView getMenuView(ViewGroup root) {
/* 151 */           MenuView result = super.getMenuView(root);
/* 152 */           ((ActionMenuView) result).setPresenter(this);
/* 153 */           return result;
                }

                @Override // android.support.v7.internal.view.menu.BaseMenuPresenter
/* 157 */       public View getItemView(MenuItemImpl item, View convertView, ViewGroup parent) {
/* 158 */           View actionView = item.getActionView();
/* 159 */           if (actionView == null || item.hasCollapsibleActionView()) {
/* 160 */               if (!(convertView instanceof ActionMenuItemView)) {
/* 161 */                   convertView = null;
                        }
/* 163 */               actionView = super.getItemView(item, convertView, parent);
                    }
/* 165 */           actionView.setVisibility(item.isActionViewExpanded() ? 8 : 0);
/* 167 */           ActionMenuView menuParent = (ActionMenuView) parent;
/* 168 */           ViewGroup.LayoutParams lp = actionView.getLayoutParams();
/* 169 */           if (!menuParent.checkLayoutParams(lp)) {
/* 170 */               actionView.setLayoutParams(menuParent.generateLayoutParams(lp));
                    }
/* 172 */           return actionView;
                }

                @Override // android.support.v7.internal.view.menu.BaseMenuPresenter
/* 176 */       public void bindItemView(MenuItemImpl item, MenuView.ItemView itemView) {
/* 177 */           itemView.initialize(item, 0);
/* 179 */           ActionMenuView menuView = (ActionMenuView) this.mMenuView;
/* 180 */           ActionMenuItemView actionItemView = (ActionMenuItemView) itemView;
/* 181 */           actionItemView.setItemInvoker(menuView);
                }

                @Override // android.support.v7.internal.view.menu.BaseMenuPresenter
/* 185 */       public boolean shouldIncludeItem(int childIndex, MenuItemImpl item) {
/* 186 */           return item.isActionButton();
                }

                @Override // android.support.v7.internal.view.menu.BaseMenuPresenter, android.support.v7.internal.view.menu.MenuPresenter
/* 190 */       public void updateMenuView(boolean cleared) {
/* 191 */           super.updateMenuView(cleared);
/* 193 */           if (this.mMenuView != null) {
/* 197 */               if (this.mMenu != null) {
/* 198 */                   ArrayList<MenuItemImpl> actionItems = this.mMenu.getActionItems();
/* 199 */                   int count = actionItems.size();
                            for (int i = 0; i < count; i++) {
/* 201 */                       ActionProvider provider = actionItems.get(i).getSupportActionProvider();
/* 202 */                       if (provider != null) {
/* 203 */                           provider.setSubUiVisibilityListener(this);
                                }
                            }
                        }
/* 208 */               ArrayList<MenuItemImpl> nonActionItems = this.mMenu != null ? this.mMenu.getNonActionItems() : null;
/* 211 */               boolean hasOverflow = false;
/* 212 */               if (this.mReserveOverflow && nonActionItems != null) {
/* 213 */                   int count2 = nonActionItems.size();
/* 217 */                   hasOverflow = count2 == 1 ? !nonActionItems.get(0).isActionViewExpanded() : count2 > 0;
                        }
/* 221 */               if (hasOverflow) {
/* 222 */                   if (this.mOverflowButton == null) {
/* 223 */                       this.mOverflowButton = new OverflowMenuButton(this.mSystemContext);
                            }
/* 225 */                   ViewGroup parent = (ViewGroup) this.mOverflowButton.getParent();
/* 226 */                   if (parent != this.mMenuView) {
/* 227 */                       if (parent != null) {
/* 228 */                           parent.removeView(this.mOverflowButton);
                                }
/* 230 */                       ActionMenuView menuView = (ActionMenuView) this.mMenuView;
/* 231 */                       menuView.addView(this.mOverflowButton, menuView.generateOverflowButtonLayoutParams());
                            }
                        } else if (this.mOverflowButton != null && this.mOverflowButton.getParent() == this.mMenuView) {
/* 234 */                   ((ViewGroup) this.mMenuView).removeView(this.mOverflowButton);
                        }
/* 237 */               ((ActionMenuView) this.mMenuView).setOverflowReserved(this.mReserveOverflow);
                    }
                }

                @Override // android.support.v7.internal.view.menu.BaseMenuPresenter
/* 241 */       public boolean filterLeftoverView(ViewGroup parent, int childIndex) {
/* 242 */           if (parent.getChildAt(childIndex) == this.mOverflowButton) {
/* 243 */               return false;
                    }
                    return super.filterLeftoverView(parent, childIndex);
                }

                @Override // android.support.v7.internal.view.menu.BaseMenuPresenter, android.support.v7.internal.view.menu.MenuPresenter
/* 248 */       public boolean onSubMenuSelected(SubMenuBuilder subMenu) {
/* 249 */           if (!subMenu.hasVisibleItems()) {
                        return false;
                    }
/* 253 */           SubMenuBuilder topSubMenu = subMenu;
/* 254 */           while (topSubMenu.getParentMenu() != this.mMenu) {
/* 255 */               topSubMenu = (SubMenuBuilder) topSubMenu.getParentMenu();
                    }
/* 257 */           View anchor = findViewForItem(topSubMenu.getItem());
/* 258 */           if (anchor == null) {
/* 259 */               if (this.mOverflowButton == null) {
                            return false;
                        }
/* 262 */               View anchor2 = this.mOverflowButton;
                    }
/* 265 */           this.mOpenSubMenuId = subMenu.getItem().getItemId();
/* 266 */           this.mActionButtonPopup = new ActionButtonSubmenu(subMenu);
/* 267 */           this.mActionButtonPopup.show(null);
/* 268 */           super.onSubMenuSelected(subMenu);
/* 269 */           return true;
                }

                /* JADX WARN: Multi-variable type inference failed */
/* 272 */       private View findViewForItem(MenuItem item) {
/* 273 */           ViewGroup parent = (ViewGroup) this.mMenuView;
/* 274 */           if (parent == null) {
                        return null;
                    }
/* 278 */           int count = parent.getChildCount();
                    for (int i = 0; i < count; i++) {
/* 280 */               View childAt = parent.getChildAt(i);
/* 281 */               if ((childAt instanceof MenuView.ItemView) && ((MenuView.ItemView) childAt).getItemData() == item) {
/* 286 */                   return childAt;
                        }
                    }
                    return null;
                }

/* 294 */       public boolean showOverflowMenu() {
/* 295 */           if (!this.mReserveOverflow || isOverflowMenuShowing() || this.mMenu == null || this.mMenuView == null || this.mPostedOpenRunnable != null) {
                        return false;
                    }
/* 297 */           OverflowPopup popup = new OverflowPopup(this.mContext, this.mMenu, this.mOverflowButton, true);
/* 298 */           this.mPostedOpenRunnable = new OpenOverflowRunnable(popup);
/* 300 */           ((View) this.mMenuView).post(this.mPostedOpenRunnable);
/* 304 */           super.onSubMenuSelected(null);
                    return true;
                }

/* 316 */       public boolean hideOverflowMenu() {
/* 317 */           if (this.mPostedOpenRunnable != null && this.mMenuView != null) {
/* 318 */               ((View) this.mMenuView).removeCallbacks(this.mPostedOpenRunnable);
/* 319 */               this.mPostedOpenRunnable = null;
                        return true;
                    }
/* 323 */           MenuPopupHelper popup = this.mOverflowPopup;
/* 324 */           if (popup != null) {
/* 325 */               popup.dismiss();
                        return true;
                    }
/* 328 */           return false;
                }

/* 336 */       public boolean dismissPopupMenus() {
/* 337 */           boolean result = hideOverflowMenu();
/* 338 */           return result | hideSubMenus();
                }

/* 347 */       public boolean hideSubMenus() {
/* 348 */           if (this.mActionButtonPopup == null) {
                        return false;
                    }
/* 349 */           this.mActionButtonPopup.dismiss();
/* 350 */           return true;
                }

/* 358 */       public boolean isOverflowMenuShowing() {
                    return this.mOverflowPopup != null && this.mOverflowPopup.isShowing();
                }

/* 365 */       public boolean isOverflowReserved() {
/* 366 */           return this.mReserveOverflow;
                }

                @Override // android.support.v7.internal.view.menu.BaseMenuPresenter, android.support.v7.internal.view.menu.MenuPresenter
/* 369 */       public boolean flagActionItems() {
/* 370 */           ArrayList<MenuItemImpl> visibleItems = this.mMenu.getVisibleItems();
/* 371 */           int itemsSize = visibleItems.size();
/* 372 */           int maxActions = this.mMaxItems;
/* 373 */           int widthLimit = this.mActionItemWidthLimit;
/* 374 */           int querySpec = View.MeasureSpec.makeMeasureSpec(0, 0);
/* 375 */           ViewGroup parent = (ViewGroup) this.mMenuView;
/* 377 */           int requiredItems = 0;
/* 378 */           int requestedItems = 0;
/* 379 */           int firstActionWidth = 0;
/* 380 */           boolean hasOverflow = false;
                    for (int i = 0; i < itemsSize; i++) {
/* 382 */               MenuItemImpl item = visibleItems.get(i);
/* 383 */               if (item.requiresActionButton()) {
/* 384 */                   requiredItems++;
                        } else if (item.requestsActionButton()) {
/* 386 */                   requestedItems++;
                        } else {
/* 388 */                   hasOverflow = true;
                        }
/* 390 */               if (this.mExpandedActionViewsExclusive && item.isActionViewExpanded()) {
/* 393 */                   maxActions = 0;
                        }
                    }
/* 398 */           if (this.mReserveOverflow && (hasOverflow || requiredItems + requestedItems > maxActions)) {
/* 400 */               maxActions--;
                    }
/* 402 */           int maxActions2 = maxActions - requiredItems;
/* 404 */           SparseBooleanArray seenGroups = this.mActionButtonGroups;
/* 405 */           seenGroups.clear();
/* 407 */           int cellSize = 0;
/* 408 */           int cellsRemaining = 0;
/* 409 */           if (this.mStrictWidthLimit) {
/* 410 */               cellsRemaining = widthLimit / this.mMinCellSize;
/* 411 */               int cellSizeRemaining = widthLimit % this.mMinCellSize;
/* 412 */               cellSize = this.mMinCellSize + (cellSizeRemaining / cellsRemaining);
                    }
                    for (int i2 = 0; i2 < itemsSize; i2++) {
/* 417 */               MenuItemImpl item2 = visibleItems.get(i2);
/* 419 */               if (item2.requiresActionButton()) {
/* 420 */                   View v = getItemView(item2, this.mScrapActionButtonView, parent);
/* 421 */                   if (this.mScrapActionButtonView == null) {
/* 422 */                       this.mScrapActionButtonView = v;
                            }
/* 424 */                   if (this.mStrictWidthLimit) {
/* 425 */                       cellsRemaining -= ActionMenuView.measureChildForCells(v, cellSize, cellsRemaining, querySpec, 0);
                            } else {
/* 428 */                       v.measure(querySpec, querySpec);
                            }
/* 430 */                   int measuredWidth = v.getMeasuredWidth();
/* 431 */                   widthLimit -= measuredWidth;
/* 432 */                   if (firstActionWidth == 0) {
/* 433 */                       firstActionWidth = measuredWidth;
                            }
/* 435 */                   int groupId = item2.getGroupId();
/* 436 */                   if (groupId != 0) {
/* 437 */                       seenGroups.put(groupId, true);
                            }
/* 439 */                   item2.setIsActionButton(true);
                        } else if (item2.requestsActionButton()) {
/* 443 */                   int groupId2 = item2.getGroupId();
/* 444 */                   boolean inGroup = seenGroups.get(groupId2);
/* 445 */                   boolean isAction = (maxActions2 > 0 || inGroup) && widthLimit > 0 && (!this.mStrictWidthLimit || cellsRemaining > 0);
/* 448 */                   if (isAction) {
/* 449 */                       View v2 = getItemView(item2, this.mScrapActionButtonView, parent);
/* 450 */                       if (this.mScrapActionButtonView == null) {
/* 451 */                           this.mScrapActionButtonView = v2;
                                }
/* 453 */                       if (this.mStrictWidthLimit) {
/* 454 */                           int cells = ActionMenuView.measureChildForCells(v2, cellSize, cellsRemaining, querySpec, 0);
/* 456 */                           cellsRemaining -= cells;
/* 457 */                           if (cells == 0) {
/* 458 */                               isAction = false;
                                    }
                                } else {
/* 461 */                           v2.measure(querySpec, querySpec);
                                }
/* 463 */                       int measuredWidth2 = v2.getMeasuredWidth();
/* 464 */                       widthLimit -= measuredWidth2;
/* 465 */                       if (firstActionWidth == 0) {
/* 466 */                           firstActionWidth = measuredWidth2;
                                }
/* 469 */                       if (this.mStrictWidthLimit) {
/* 470 */                           isAction &= widthLimit >= 0;
                                } else {
/* 473 */                           isAction &= widthLimit + firstActionWidth > 0;
                                }
                            }
/* 477 */                   if (isAction && groupId2 != 0) {
/* 478 */                       seenGroups.put(groupId2, true);
                            } else if (inGroup) {
/* 481 */                       seenGroups.put(groupId2, false);
                                for (int j = 0; j < i2; j++) {
/* 483 */                           MenuItemImpl areYouMyGroupie = visibleItems.get(j);
/* 484 */                           if (areYouMyGroupie.getGroupId() == groupId2) {
/* 486 */                               if (areYouMyGroupie.isActionButton()) {
/* 487 */                                   maxActions2++;
                                        }
/* 489 */                               areYouMyGroupie.setIsActionButton(false);
                                    }
                                }
                            }
/* 494 */                   if (isAction) {
/* 495 */                       maxActions2--;
                            }
/* 498 */                   item2.setIsActionButton(isAction);
                        }
                    }
/* 501 */           return true;
                }

                @Override // android.support.v7.internal.view.menu.BaseMenuPresenter, android.support.v7.internal.view.menu.MenuPresenter
/* 505 */       public void onCloseMenu(MenuBuilder menu, boolean allMenusAreClosing) {
/* 506 */           dismissPopupMenus();
/* 507 */           super.onCloseMenu(menu, allMenusAreClosing);
                }

                @Override // android.support.v7.internal.view.menu.MenuPresenter
/* 511 */       public Parcelable onSaveInstanceState() {
/* 512 */           SavedState state = new SavedState();
/* 513 */           state.openSubMenuId = this.mOpenSubMenuId;
/* 514 */           return state;
                }

                @Override // android.support.v7.internal.view.menu.MenuPresenter
/* 518 */       public void onRestoreInstanceState(Parcelable state) {
                    MenuItem item;
/* 519 */           SavedState saved = (SavedState) state;
/* 520 */           if (saved.openSubMenuId > 0 && (item = this.mMenu.findItem(saved.openSubMenuId)) != null) {
/* 523 */               SubMenuBuilder subMenu = (SubMenuBuilder) item.getSubMenu();
/* 524 */               onSubMenuSelected(subMenu);
                    }
                }

                @Override // android.support.v4.view.ActionProvider.SubUiVisibilityListener
/* 530 */       public void onSubUiVisibilityChanged(boolean isVisible) {
/* 531 */           if (isVisible) {
/* 533 */               super.onSubMenuSelected(null);
                    } else {
/* 535 */               this.mMenu.close(false);
                    }
                }

/* 541 */       private static class SavedState implements Parcelable {
                    public static final Parcelable.Creator<SavedState> CREATOR = new C00861();
                    public int openSubMenuId;

/* 542 */           SavedState() {
                    }

/* 545 */           SavedState(Parcel in) {
/* 547 */               this.openSubMenuId = in.readInt();
                    }

                    @Override // android.os.Parcelable
/* 551 */           public int describeContents() {
/* 552 */               return 0;
                    }

                    @Override // android.os.Parcelable
/* 556 */           public void writeToParcel(Parcel dest, int flags) {
/* 557 */               dest.writeInt(this.openSubMenuId);
                    }

                    /* JADX INFO: renamed from: android.support.v7.internal.view.menu.ActionMenuPresenter$SavedState$1 */
/* 559 */           static class C00861 implements Parcelable.Creator<SavedState> {
/* 560 */               C00861() {
                        }

                        @Override // android.os.Parcelable.Creator
/* 560 */               public /* bridge */ /* synthetic */ SavedState createFromParcel(Parcel x0) {
/* 561 */                   return createFromParcel(x0);
                        }

                        @Override // android.os.Parcelable.Creator
/* 560 */               public /* bridge */ /* synthetic */ SavedState[] newArray(int x0) {
/* 561 */                   return newArray(x0);
                        }

                        /* JADX WARN: Can't rename method to resolve collision */
                        @Override // android.os.Parcelable.Creator
/* 562 */               public SavedState createFromParcel(Parcel in) {
/* 563 */                   return new SavedState(in);
                        }

                        /* JADX WARN: Can't rename method to resolve collision */
                        @Override // android.os.Parcelable.Creator
/* 566 */               public SavedState[] newArray(int size) {
/* 567 */                   return new SavedState[size];
                        }
                    }
                }

/* 572 */       private class OverflowMenuButton extends ImageButton implements ActionMenuView.ActionMenuChildView {
/* 573 */           public OverflowMenuButton(Context context) {
/* 575 */               super(context, null, C0084R.attr.actionOverflowButtonStyle);
/* 577 */               setClickable(true);
/* 578 */               setFocusable(true);
/* 579 */               setVisibility(0);
/* 580 */               setEnabled(true);
                    }

                    @Override // android.view.View
/* 584 */           public boolean performClick() {
/* 585 */               if (!super.performClick()) {
/* 589 */                   playSoundEffect(0);
/* 590 */                   ActionMenuPresenter.this.showOverflowMenu();
                        }
                        return true;
                    }

                    @Override // android.support.v7.internal.view.menu.ActionMenuView.ActionMenuChildView
/* 594 */           public boolean needsDividerBefore() {
/* 595 */               return false;
                    }

                    @Override // android.support.v7.internal.view.menu.ActionMenuView.ActionMenuChildView
/* 598 */           public boolean needsDividerAfter() {
/* 599 */               return false;
                    }
                }

/* 604 */       private class OverflowPopup extends MenuPopupHelper {
/* 605 */           public OverflowPopup(Context context, MenuBuilder menu, View anchorView, boolean overflowOnly) {
/* 607 */               super(context, menu, anchorView, overflowOnly);
/* 608 */               setCallback(ActionMenuPresenter.this.mPopupPresenterCallback);
                    }

                    @Override // android.support.v7.internal.view.menu.MenuPopupHelper, android.widget.PopupWindow.OnDismissListener
/* 612 */           public void onDismiss() {
/* 613 */               super.onDismiss();
/* 614 */               ActionMenuPresenter.this.mMenu.close();
/* 615 */               ActionMenuPresenter.access$102(ActionMenuPresenter.this, null);
                    }
                }

/* 619 */       private class ActionButtonSubmenu extends MenuDialogHelper {
/* 620 */           public ActionButtonSubmenu(SubMenuBuilder subMenu) {
/* 622 */               super(subMenu);
/* 623 */               ActionMenuPresenter.this.setCallback(ActionMenuPresenter.this.mPopupPresenterCallback);
                    }

                    @Override // android.support.v7.internal.view.menu.MenuDialogHelper, android.content.DialogInterface.OnDismissListener
/* 627 */           public void onDismiss(DialogInterface dialog) {
/* 628 */               super.onDismiss(dialog);
/* 629 */               ActionMenuPresenter.access$202(ActionMenuPresenter.this, null);
/* 630 */               ActionMenuPresenter.this.mOpenSubMenuId = 0;
                    }
                }

/* 632 */       private class PopupPresenterCallback implements MenuPresenter.Callback {
/* 633 */           private PopupPresenterCallback() {
                    }

/* 633 */           /* synthetic */ PopupPresenterCallback(ActionMenuPresenter x0, C00851 x1) {
/* 634 */               this();
                    }

                    @Override // android.support.v7.internal.view.menu.MenuPresenter.Callback
/* 637 */           public boolean onOpenSubMenu(MenuBuilder subMenu) {
/* 638 */               if (subMenu != null) {
/* 642 */                   ActionMenuPresenter.this.mOpenSubMenuId = ((SubMenuBuilder) subMenu).getItem().getItemId();
                        }
                        return false;
                    }

                    @Override // android.support.v7.internal.view.menu.MenuPresenter.Callback
/* 647 */           public void onCloseMenu(MenuBuilder menu, boolean allMenusAreClosing) {
/* 648 */               if (menu instanceof SubMenuBuilder) {
/* 649 */                   ((SubMenuBuilder) menu).getRootMenu().close(false);
                        }
                    }
                }

/* 656 */       private class OpenOverflowRunnable implements Runnable {
                    private OverflowPopup mPopup;

/* 657 */           public OpenOverflowRunnable(OverflowPopup popup) {
/* 659 */               this.mPopup = popup;
                    }

                    @Override // java.lang.Runnable
/* 662 */           public void run() {
/* 663 */               ActionMenuPresenter.this.mMenu.changeMenuMode();
/* 664 */               View menuView = (View) ActionMenuPresenter.this.mMenuView;
/* 665 */               if (menuView != null && menuView.getWindowToken() != null && this.mPopup.tryShow()) {
/* 666 */                   ActionMenuPresenter.access$102(ActionMenuPresenter.this, this.mPopup);
                        }
/* 668 */               ActionMenuPresenter.access$302(ActionMenuPresenter.this, null);
                    }
                }
            }
