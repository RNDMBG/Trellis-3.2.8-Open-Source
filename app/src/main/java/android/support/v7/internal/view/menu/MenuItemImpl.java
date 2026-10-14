            package android.support.v7.internal.view.menu;

            import android.content.ActivityNotFoundException;
            import android.content.Context;
            import android.content.Intent;
            import android.graphics.drawable.Drawable;
            import android.support.v4.internal.view.SupportMenuItem;
            import android.support.v4.view.ActionProvider;
            import android.support.v4.view.MenuItemCompat;
            import android.util.Log;
            import android.view.ContextMenu;
            import android.view.LayoutInflater;
            import android.view.MenuItem;
            import android.view.SubMenu;
            import android.view.View;
            import android.view.ViewDebug;
            import android.view.ViewGroup;
            import android.widget.LinearLayout;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 36 */    public final class MenuItemImpl implements SupportMenuItem {
                private static final int CHECKABLE = 1;
                private static final int CHECKED = 2;
                private static final int ENABLED = 16;
                private static final int EXCLUSIVE = 4;
                private static final int HIDDEN = 8;
                private static final int IS_ACTION = 32;
                static final int NO_ICON = 0;
                private static final int SHOW_AS_ACTION_MASK = 3;
                private static final String TAG = "MenuItemImpl";
                private static String sDeleteShortcutLabel;
                private static String sEnterShortcutLabel;
                private static String sPrependShortcutLabel;
                private static String sSpaceShortcutLabel;
                private ActionProvider mActionProvider;
                private View mActionView;
                private final int mCategoryOrder;
                private MenuItem.OnMenuItemClickListener mClickListener;
                private final int mGroup;
                private Drawable mIconDrawable;
                private final int mId;
                private Intent mIntent;
                private Runnable mItemCallback;
                private MenuBuilder mMenu;
                private ContextMenu.ContextMenuInfo mMenuInfo;
                private MenuItemCompat.OnActionExpandListener mOnActionExpandListener;
                private final int mOrdering;
                private char mShortcutAlphabeticChar;
                private char mShortcutNumericChar;
                private int mShowAsAction;
                private SubMenuBuilder mSubMenu;
                private CharSequence mTitle;
                private CharSequence mTitleCondensed;
                private int mIconResId = 0;
                private int mFlags = 16;
                private boolean mIsActionViewExpanded = false;

/* 37 */        static /* synthetic */ MenuBuilder access$000(MenuItemImpl x0) {
/* 38 */            return x0.mMenu;
                }

                @Override // android.support.v4.internal.view.SupportMenuItem, android.view.MenuItem
/* 37 */        public /* bridge */ /* synthetic */ MenuItem setActionView(int x0) {
/* 38 */            return setActionView(x0);
                }

                @Override // android.support.v4.internal.view.SupportMenuItem, android.view.MenuItem
/* 37 */        public /* bridge */ /* synthetic */ MenuItem setActionView(View x0) {
/* 38 */            return setActionView(x0);
                }

                @Override // android.support.v4.internal.view.SupportMenuItem, android.view.MenuItem
/* 37 */        public /* bridge */ /* synthetic */ MenuItem setShowAsActionFlags(int x0) {
/* 38 */            return setShowAsActionFlags(x0);
                }

/* 123 */       MenuItemImpl(MenuBuilder menu, int group, int id, int categoryOrder, int ordering, CharSequence title, int showAsAction) {
/* 88 */            this.mShowAsAction = 0;
/* 138 */           this.mMenu = menu;
/* 139 */           this.mId = id;
/* 140 */           this.mGroup = group;
/* 141 */           this.mCategoryOrder = categoryOrder;
/* 142 */           this.mOrdering = ordering;
/* 143 */           this.mTitle = title;
/* 144 */           this.mShowAsAction = showAsAction;
                }

/* 152 */       public boolean invoke() {
/* 153 */           if ((this.mClickListener != null && this.mClickListener.onMenuItemClick(this)) || this.mMenu.dispatchMenuItemSelected(this.mMenu.getRootMenu(), this)) {
                        return true;
                    }
/* 162 */           if (this.mItemCallback != null) {
/* 163 */               this.mItemCallback.run();
                        return true;
                    }
/* 167 */           if (this.mIntent != null) {
                        try {
/* 169 */                   this.mMenu.getContext().startActivity(this.mIntent);
                            return true;
                        } catch (ActivityNotFoundException e) {
/* 172 */                   Log.e(TAG, "Can't find activity to handle intent; ignoring", e);
                        }
                    }
                    return this.mActionProvider != null && this.mActionProvider.onPerformDefaultAction();
                }

                @Override // android.view.MenuItem
/* 184 */       public boolean isEnabled() {
                    return (this.mFlags & 16) != 0;
                }

                @Override // android.view.MenuItem
/* 189 */       public MenuItem setEnabled(boolean enabled) {
/* 190 */           if (enabled) {
/* 191 */               this.mFlags |= 16;
                    } else {
/* 193 */               this.mFlags &= -17;
                    }
/* 196 */           this.mMenu.onItemsChanged(false);
/* 198 */           return this;
                }

                @Override // android.view.MenuItem
/* 202 */       public int getGroupId() {
/* 203 */           return this.mGroup;
                }

                @Override // android.view.MenuItem
                @ViewDebug.CapturedViewProperty
/* 208 */       public int getItemId() {
/* 209 */           return this.mId;
                }

                @Override // android.view.MenuItem
/* 213 */       public int getOrder() {
/* 214 */           return this.mCategoryOrder;
                }

/* 217 */       public int getOrdering() {
/* 218 */           return this.mOrdering;
                }

                @Override // android.view.MenuItem
/* 222 */       public Intent getIntent() {
/* 223 */           return this.mIntent;
                }

                @Override // android.view.MenuItem
/* 227 */       public MenuItem setIntent(Intent intent) {
/* 228 */           this.mIntent = intent;
/* 229 */           return this;
                }

/* 232 */       Runnable getCallback() {
/* 233 */           return this.mItemCallback;
                }

/* 236 */       public MenuItem setCallback(Runnable callback) {
/* 237 */           this.mItemCallback = callback;
/* 238 */           return this;
                }

                @Override // android.view.MenuItem
/* 242 */       public char getAlphabeticShortcut() {
/* 243 */           return this.mShortcutAlphabeticChar;
                }

                @Override // android.view.MenuItem
/* 247 */       public MenuItem setAlphabeticShortcut(char alphaChar) {
/* 248 */           if (this.mShortcutAlphabeticChar != alphaChar) {
/* 252 */               this.mShortcutAlphabeticChar = Character.toLowerCase(alphaChar);
/* 254 */               this.mMenu.onItemsChanged(false);
                    }
/* 256 */           return this;
                }

                @Override // android.view.MenuItem
/* 260 */       public char getNumericShortcut() {
/* 261 */           return this.mShortcutNumericChar;
                }

                @Override // android.view.MenuItem
/* 265 */       public MenuItem setNumericShortcut(char numericChar) {
/* 266 */           if (this.mShortcutNumericChar != numericChar) {
/* 270 */               this.mShortcutNumericChar = numericChar;
/* 272 */               this.mMenu.onItemsChanged(false);
                    }
/* 274 */           return this;
                }

                @Override // android.view.MenuItem
/* 278 */       public MenuItem setShortcut(char numericChar, char alphaChar) {
/* 279 */           this.mShortcutNumericChar = numericChar;
/* 280 */           this.mShortcutAlphabeticChar = Character.toLowerCase(alphaChar);
/* 282 */           this.mMenu.onItemsChanged(false);
/* 284 */           return this;
                }

/* 291 */       char getShortcut() {
/* 292 */           return this.mShortcutAlphabeticChar;
                }

/* 301 */       String getShortcutLabel() {
/* 302 */           char shortcut = getShortcut();
/* 303 */           if (shortcut == 0) {
/* 304 */               return "";
                    }
/* 307 */           StringBuilder sb = new StringBuilder(sPrependShortcutLabel);
                    switch (shortcut) {
                        case '\b':
/* 315 */                   sb.append(sDeleteShortcutLabel);
                            break;
                        case '\n':
/* 311 */                   sb.append(sEnterShortcutLabel);
                            break;
                        case ' ':
/* 319 */                   sb.append(sSpaceShortcutLabel);
                            break;
                        default:
/* 323 */                   sb.append(shortcut);
                            break;
                    }
/* 327 */           return sb.toString();
                }

/* 335 */       boolean shouldShowShortcut() {
                    return this.mMenu.isShortcutsVisible() && getShortcut() != 0;
                }

                @Override // android.view.MenuItem
/* 340 */       public SubMenu getSubMenu() {
/* 341 */           return this.mSubMenu;
                }

                @Override // android.view.MenuItem
/* 345 */       public boolean hasSubMenu() {
                    return this.mSubMenu != null;
                }

/* 349 */       void setSubMenu(SubMenuBuilder subMenu) {
/* 350 */           this.mSubMenu = subMenu;
/* 352 */           subMenu.setHeaderTitle(getTitle());
                }

                @Override // android.view.MenuItem
                @ViewDebug.CapturedViewProperty
/* 357 */       public CharSequence getTitle() {
/* 358 */           return this.mTitle;
                }

/* 367 */       CharSequence getTitleForItemView(MenuView.ItemView itemView) {
                    return (itemView == null || !itemView.prefersCondensedTitle()) ? getTitle() : getTitleCondensed();
                }

                @Override // android.view.MenuItem
/* 374 */       public MenuItem setTitle(CharSequence title) {
/* 375 */           this.mTitle = title;
/* 377 */           this.mMenu.onItemsChanged(false);
/* 379 */           if (this.mSubMenu != null) {
/* 380 */               this.mSubMenu.setHeaderTitle(title);
                    }
/* 383 */           return this;
                }

                @Override // android.view.MenuItem
/* 387 */       public MenuItem setTitle(int title) {
/* 388 */           return setTitle(this.mMenu.getContext().getString(title));
                }

                @Override // android.view.MenuItem
/* 392 */       public CharSequence getTitleCondensed() {
                    return this.mTitleCondensed != null ? this.mTitleCondensed : this.mTitle;
                }

                @Override // android.view.MenuItem
/* 397 */       public MenuItem setTitleCondensed(CharSequence title) {
/* 398 */           this.mTitleCondensed = title;
/* 401 */           if (title == null) {
/* 402 */               CharSequence title2 = this.mTitle;
                    }
/* 405 */           this.mMenu.onItemsChanged(false);
/* 407 */           return this;
                }

                @Override // android.view.MenuItem
/* 411 */       public Drawable getIcon() {
/* 412 */           if (this.mIconDrawable != null) {
/* 413 */               return this.mIconDrawable;
                    }
/* 416 */           if (this.mIconResId != 0) {
/* 417 */               Drawable icon = this.mMenu.getResources().getDrawable(this.mIconResId);
/* 418 */               this.mIconResId = 0;
/* 419 */               this.mIconDrawable = icon;
/* 423 */               return icon;
                    }
/* 423 */           return null;
                }

                @Override // android.view.MenuItem
/* 427 */       public MenuItem setIcon(Drawable icon) {
/* 428 */           this.mIconResId = 0;
/* 429 */           this.mIconDrawable = icon;
/* 430 */           this.mMenu.onItemsChanged(false);
/* 432 */           return this;
                }

                @Override // android.view.MenuItem
/* 436 */       public MenuItem setIcon(int iconResId) {
/* 437 */           this.mIconDrawable = null;
/* 438 */           this.mIconResId = iconResId;
/* 441 */           this.mMenu.onItemsChanged(false);
/* 443 */           return this;
                }

                @Override // android.view.MenuItem
/* 447 */       public boolean isCheckable() {
                    return (this.mFlags & 1) == 1;
                }

                @Override // android.view.MenuItem
/* 452 */       public MenuItem setCheckable(boolean checkable) {
/* 453 */           int oldFlags = this.mFlags;
                    this.mFlags = (checkable ? 1 : 0) | (this.mFlags & (-2));
/* 455 */           if (oldFlags != this.mFlags) {
/* 456 */               this.mMenu.onItemsChanged(false);
                    }
/* 459 */           return this;
                }

/* 462 */       public void setExclusiveCheckable(boolean exclusive) {
                    this.mFlags = (exclusive ? 4 : 0) | (this.mFlags & (-5));
                }

/* 466 */       public boolean isExclusiveCheckable() {
                    return (this.mFlags & 4) != 0;
                }

                @Override // android.view.MenuItem
/* 471 */       public boolean isChecked() {
                    return (this.mFlags & 2) == 2;
                }

                @Override // android.view.MenuItem
/* 476 */       public MenuItem setChecked(boolean checked) {
/* 477 */           if ((this.mFlags & 4) != 0) {
/* 480 */               this.mMenu.setExclusiveItemChecked(this);
                    } else {
/* 482 */               setCheckedInt(checked);
                    }
/* 485 */           return this;
                }

/* 488 */       void setCheckedInt(boolean checked) {
/* 489 */           int oldFlags = this.mFlags;
                    this.mFlags = (checked ? 2 : 0) | (this.mFlags & (-3));
/* 491 */           if (oldFlags != this.mFlags) {
/* 492 */               this.mMenu.onItemsChanged(false);
                    }
                }

                @Override // android.view.MenuItem
/* 497 */       public boolean isVisible() {
/* 498 */           if (this.mActionProvider == null || !this.mActionProvider.overridesItemVisibility()) {
                        return (this.mFlags & 8) == 0;
                    }
                    return (this.mFlags & 8) == 0 && this.mActionProvider.isVisible();
                }

/* 512 */       boolean setVisibleInt(boolean shown) {
/* 513 */           int oldFlags = this.mFlags;
                    this.mFlags = (shown ? 0 : 8) | (this.mFlags & (-9));
                    return oldFlags != this.mFlags;
                }

                @Override // android.view.MenuItem
/* 522 */       public MenuItem setVisible(boolean shown) {
/* 523 */           if (setVisibleInt(shown)) {
                        this.mMenu.onItemVisibleChanged(this);
                    }
/* 525 */           return this;
                }

                @Override // android.view.MenuItem
/* 529 */       public MenuItem setOnMenuItemClickListener(MenuItem.OnMenuItemClickListener clickListener) {
/* 530 */           this.mClickListener = clickListener;
/* 531 */           return this;
                }

/* 535 */       public String toString() {
/* 536 */           return this.mTitle.toString();
                }

/* 539 */       void setMenuInfo(ContextMenu.ContextMenuInfo menuInfo) {
/* 540 */           this.mMenuInfo = menuInfo;
                }

                @Override // android.view.MenuItem
/* 544 */       public ContextMenu.ContextMenuInfo getMenuInfo() {
/* 545 */           return this.mMenuInfo;
                }

/* 548 */       public void actionFormatChanged() {
/* 549 */           this.mMenu.onItemActionRequestChanged(this);
                }

/* 555 */       public boolean shouldShowIcon() {
/* 556 */           return this.mMenu.getOptionalIconsVisible();
                }

/* 559 */       public boolean isActionButton() {
                    return (this.mFlags & 32) == 32;
                }

/* 563 */       public boolean requestsActionButton() {
                    return (this.mShowAsAction & 1) == 1;
                }

/* 567 */       public boolean requiresActionButton() {
                    return (this.mShowAsAction & 2) == 2;
                }

/* 571 */       public void setIsActionButton(boolean isActionButton) {
/* 572 */           if (isActionButton) {
/* 573 */               this.mFlags |= 32;
                    } else {
/* 575 */               this.mFlags &= -33;
                    }
                }

/* 579 */       public boolean showsTextAsAction() {
                    return (this.mShowAsAction & 4) == 4;
                }

                @Override // android.support.v4.internal.view.SupportMenuItem, android.view.MenuItem
/* 584 */       public void setShowAsAction(int actionEnum) {
                    switch (actionEnum & 3) {
                        case 0:
                        case 1:
                        case 2:
/* 597 */                   this.mShowAsAction = actionEnum;
/* 598 */                   this.mMenu.onItemActionRequestChanged(this);
/* 599 */                   return;
                        default:
/* 594 */                   throw new IllegalArgumentException("SHOW_AS_ACTION_ALWAYS, SHOW_AS_ACTION_IF_ROOM, and SHOW_AS_ACTION_NEVER are mutually exclusive.");
                    }
                }

                @Override // android.support.v4.internal.view.SupportMenuItem, android.view.MenuItem
/* 602 */       public SupportMenuItem setActionView(View view) {
/* 603 */           this.mActionView = view;
/* 604 */           this.mActionProvider = null;
/* 605 */           if (view != null && view.getId() == -1 && this.mId > 0) {
/* 606 */               view.setId(this.mId);
                    }
/* 608 */           this.mMenu.onItemActionRequestChanged(this);
/* 609 */           return this;
                }

                @Override // android.support.v4.internal.view.SupportMenuItem, android.view.MenuItem
/* 613 */       public SupportMenuItem setActionView(int resId) {
/* 614 */           Context context = this.mMenu.getContext();
/* 615 */           LayoutInflater inflater = LayoutInflater.from(context);
/* 616 */           setActionView(inflater.inflate(resId, (ViewGroup) new LinearLayout(context), false));
/* 617 */           return this;
                }

                @Override // android.support.v4.internal.view.SupportMenuItem, android.view.MenuItem
/* 621 */       public View getActionView() {
/* 622 */           if (this.mActionView != null) {
/* 623 */               return this.mActionView;
                    }
/* 624 */           if (this.mActionProvider != null) {
/* 625 */               this.mActionView = this.mActionProvider.onCreateActionView(this);
/* 626 */               return this.mActionView;
                    }
/* 628 */           return null;
                }

                @Override // android.view.MenuItem
/* 633 */       public MenuItem setActionProvider(android.view.ActionProvider actionProvider) {
/* 634 */           throw new UnsupportedOperationException("Implementation should use setSupportActionProvider!");
                }

                @Override // android.view.MenuItem
/* 639 */       public android.view.ActionProvider getActionProvider() {
/* 640 */           throw new UnsupportedOperationException("Implementation should use getSupportActionProvider!");
                }

                @Override // android.support.v4.internal.view.SupportMenuItem
/* 645 */       public ActionProvider getSupportActionProvider() {
/* 646 */           return this.mActionProvider;
                }

                @Override // android.support.v4.internal.view.SupportMenuItem
/* 650 */       public SupportMenuItem setSupportActionProvider(ActionProvider actionProvider) {
/* 651 */           if (this.mActionProvider != actionProvider) {
/* 655 */               this.mActionView = null;
/* 656 */               if (this.mActionProvider != null) {
/* 657 */                   this.mActionProvider.setVisibilityListener(null);
                        }
/* 659 */               this.mActionProvider = actionProvider;
/* 660 */               this.mMenu.onItemsChanged(true);
/* 661 */               if (actionProvider != null) {
/* 662 */                   actionProvider.setVisibilityListener(new C00871());
                        }
                    }
/* 669 */           return this;
                }

                /* JADX INFO: renamed from: android.support.v7.internal.view.menu.MenuItemImpl$1 */
/* 660 */       class C00871 implements ActionProvider.VisibilityListener {
/* 661 */           C00871() {
                    }

                    @Override // android.support.v4.view.ActionProvider.VisibilityListener
/* 664 */           public void onActionProviderVisibilityChanged(boolean isVisible) {
/* 665 */               MenuItemImpl.access$000(MenuItemImpl.this).onItemVisibleChanged(MenuItemImpl.this);
                    }
                }

                @Override // android.support.v4.internal.view.SupportMenuItem, android.view.MenuItem
/* 673 */       public SupportMenuItem setShowAsActionFlags(int actionEnum) {
/* 674 */           setShowAsAction(actionEnum);
/* 675 */           return this;
                }

                @Override // android.support.v4.internal.view.SupportMenuItem, android.view.MenuItem
/* 679 */       public boolean expandActionView() {
/* 680 */           if ((this.mShowAsAction & 8) == 0 || this.mActionView == null) {
                        return false;
                    }
/* 684 */           if (this.mOnActionExpandListener == null || this.mOnActionExpandListener.onMenuItemActionExpand(this)) {
/* 686 */               return this.mMenu.expandItemActionView(this);
                    }
                    return false;
                }

                @Override // android.support.v4.internal.view.SupportMenuItem, android.view.MenuItem
/* 693 */       public boolean collapseActionView() {
/* 694 */           if ((this.mShowAsAction & 8) == 0) {
                        return false;
                    }
/* 697 */           if (this.mActionView == null) {
/* 699 */               return true;
                    }
/* 702 */           if (this.mOnActionExpandListener == null || this.mOnActionExpandListener.onMenuItemActionCollapse(this)) {
/* 704 */               return this.mMenu.collapseItemActionView(this);
                    }
                    return false;
                }

                @Override // android.support.v4.internal.view.SupportMenuItem
/* 712 */       public SupportMenuItem setSupportOnActionExpandListener(MenuItemCompat.OnActionExpandListener listener) {
/* 713 */           this.mOnActionExpandListener = listener;
/* 714 */           return this;
                }

/* 717 */       public boolean hasCollapsibleActionView() {
                    return ((this.mShowAsAction & 8) == 0 || this.mActionView == null) ? false : true;
                }

/* 721 */       public void setActionViewExpanded(boolean isExpanded) {
/* 722 */           this.mIsActionViewExpanded = isExpanded;
/* 723 */           this.mMenu.onItemsChanged(false);
                }

                @Override // android.support.v4.internal.view.SupportMenuItem, android.view.MenuItem
/* 727 */       public boolean isActionViewExpanded() {
/* 728 */           return this.mIsActionViewExpanded;
                }

                @Override // android.view.MenuItem
/* 732 */       public MenuItem setOnActionExpandListener(MenuItem.OnActionExpandListener listener) {
/* 733 */           throw new UnsupportedOperationException("Implementation should use setSupportOnActionExpandListener!");
                }
            }
