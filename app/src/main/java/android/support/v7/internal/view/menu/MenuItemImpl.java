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
            
            
    public final class MenuItemImpl implements SupportMenuItem {
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

        static  MenuBuilder access$000(MenuItemImpl x0) {
            return x0.mMenu;
                }

                @Override 
        public   MenuItem setActionView(int x0) {
            return setActionView(x0);
                }

                @Override 
        public   MenuItem setActionView(View x0) {
            return setActionView(x0);
                }

                @Override 
        public   MenuItem setShowAsActionFlags(int x0) {
            return setShowAsActionFlags(x0);
                }

       MenuItemImpl(MenuBuilder menu, int group, int id, int categoryOrder, int ordering, CharSequence title, int showAsAction) {
            this.mShowAsAction = 0;
           this.mMenu = menu;
           this.mId = id;
           this.mGroup = group;
           this.mCategoryOrder = categoryOrder;
           this.mOrdering = ordering;
           this.mTitle = title;
           this.mShowAsAction = showAsAction;
                }

       public boolean invoke() {
           if ((this.mClickListener != null && this.mClickListener.onMenuItemClick(this)) || this.mMenu.dispatchMenuItemSelected(this.mMenu.getRootMenu(), this)) {
                        return true;
                    }
           if (this.mItemCallback != null) {
               this.mItemCallback.run();
                        return true;
                    }
           if (this.mIntent != null) {
                        try {
                   this.mMenu.getContext().startActivity(this.mIntent);
                            return true;
                        } catch (ActivityNotFoundException e) {
                   Log.e(TAG, "Can't find activity to handle intent; ignoring", e);
                        }
                    }
                    return this.mActionProvider != null && this.mActionProvider.onPerformDefaultAction();
                }

                @Override 
       public boolean isEnabled() {
                    return (this.mFlags & 16) != 0;
                }

                @Override 
       public MenuItem setEnabled(boolean enabled) {
           if (enabled) {
               this.mFlags |= 16;
                    } else {
               this.mFlags &= -17;
                    }
           this.mMenu.onItemsChanged(false);
           return this;
                }

                @Override 
       public int getGroupId() {
           return this.mGroup;
                }

                @Override 
                @ViewDebug.CapturedViewProperty
       public int getItemId() {
           return this.mId;
                }

                @Override 
       public int getOrder() {
           return this.mCategoryOrder;
                }

       public int getOrdering() {
           return this.mOrdering;
                }

                @Override 
       public Intent getIntent() {
           return this.mIntent;
                }

                @Override 
       public MenuItem setIntent(Intent intent) {
           this.mIntent = intent;
           return this;
                }

       Runnable getCallback() {
           return this.mItemCallback;
                }

       public MenuItem setCallback(Runnable callback) {
           this.mItemCallback = callback;
           return this;
                }

                @Override 
       public char getAlphabeticShortcut() {
           return this.mShortcutAlphabeticChar;
                }

                @Override 
       public MenuItem setAlphabeticShortcut(char alphaChar) {
           if (this.mShortcutAlphabeticChar != alphaChar) {
               this.mShortcutAlphabeticChar = Character.toLowerCase(alphaChar);
               this.mMenu.onItemsChanged(false);
                    }
           return this;
                }

                @Override 
       public char getNumericShortcut() {
           return this.mShortcutNumericChar;
                }

                @Override 
       public MenuItem setNumericShortcut(char numericChar) {
           if (this.mShortcutNumericChar != numericChar) {
               this.mShortcutNumericChar = numericChar;
               this.mMenu.onItemsChanged(false);
                    }
           return this;
                }

                @Override 
       public MenuItem setShortcut(char numericChar, char alphaChar) {
           this.mShortcutNumericChar = numericChar;
           this.mShortcutAlphabeticChar = Character.toLowerCase(alphaChar);
           this.mMenu.onItemsChanged(false);
           return this;
                }

       char getShortcut() {
           return this.mShortcutAlphabeticChar;
                }

       String getShortcutLabel() {
           char shortcut = getShortcut();
           if (shortcut == 0) {
               return "";
                    }
           StringBuilder sb = new StringBuilder(sPrependShortcutLabel);
                    switch (shortcut) {
                        case '\b':
                   sb.append(sDeleteShortcutLabel);
                            break;
                        case '\n':
                   sb.append(sEnterShortcutLabel);
                            break;
                        case ' ':
                   sb.append(sSpaceShortcutLabel);
                            break;
                        default:
                   sb.append(shortcut);
                            break;
                    }
           return sb.toString();
                }

       boolean shouldShowShortcut() {
                    return this.mMenu.isShortcutsVisible() && getShortcut() != 0;
                }

                @Override 
       public SubMenu getSubMenu() {
           return this.mSubMenu;
                }

                @Override 
       public boolean hasSubMenu() {
                    return this.mSubMenu != null;
                }

       void setSubMenu(SubMenuBuilder subMenu) {
           this.mSubMenu = subMenu;
           subMenu.setHeaderTitle(getTitle());
                }

                @Override 
                @ViewDebug.CapturedViewProperty
       public CharSequence getTitle() {
           return this.mTitle;
                }

       CharSequence getTitleForItemView(MenuView.ItemView itemView) {
                    return (itemView == null || !itemView.prefersCondensedTitle()) ? getTitle() : getTitleCondensed();
                }

                @Override 
       public MenuItem setTitle(CharSequence title) {
           this.mTitle = title;
           this.mMenu.onItemsChanged(false);
           if (this.mSubMenu != null) {
               this.mSubMenu.setHeaderTitle(title);
                    }
           return this;
                }

                @Override 
       public MenuItem setTitle(int title) {
           return setTitle(this.mMenu.getContext().getString(title));
                }

                @Override 
       public CharSequence getTitleCondensed() {
                    return this.mTitleCondensed != null ? this.mTitleCondensed : this.mTitle;
                }

                @Override 
       public MenuItem setTitleCondensed(CharSequence title) {
           this.mTitleCondensed = title;
           if (title == null) {
               CharSequence title2 = this.mTitle;
                    }
           this.mMenu.onItemsChanged(false);
           return this;
                }

                @Override 
       public Drawable getIcon() {
           if (this.mIconDrawable != null) {
               return this.mIconDrawable;
                    }
           if (this.mIconResId != 0) {
               Drawable icon = this.mMenu.getResources().getDrawable(this.mIconResId);
               this.mIconResId = 0;
               this.mIconDrawable = icon;
               return icon;
                    }
           return null;
                }

                @Override 
       public MenuItem setIcon(Drawable icon) {
           this.mIconResId = 0;
           this.mIconDrawable = icon;
           this.mMenu.onItemsChanged(false);
           return this;
                }

                @Override 
       public MenuItem setIcon(int iconResId) {
           this.mIconDrawable = null;
           this.mIconResId = iconResId;
           this.mMenu.onItemsChanged(false);
           return this;
                }

                @Override 
       public boolean isCheckable() {
                    return (this.mFlags & 1) == 1;
                }

                @Override 
       public MenuItem setCheckable(boolean checkable) {
           int oldFlags = this.mFlags;
                    this.mFlags = (checkable ? 1 : 0) | (this.mFlags & (-2));
           if (oldFlags != this.mFlags) {
               this.mMenu.onItemsChanged(false);
                    }
           return this;
                }

       public void setExclusiveCheckable(boolean exclusive) {
                    this.mFlags = (exclusive ? 4 : 0) | (this.mFlags & (-5));
                }

       public boolean isExclusiveCheckable() {
                    return (this.mFlags & 4) != 0;
                }

                @Override 
       public boolean isChecked() {
                    return (this.mFlags & 2) == 2;
                }

                @Override 
       public MenuItem setChecked(boolean checked) {
           if ((this.mFlags & 4) != 0) {
               this.mMenu.setExclusiveItemChecked(this);
                    } else {
               setCheckedInt(checked);
                    }
           return this;
                }

       void setCheckedInt(boolean checked) {
           int oldFlags = this.mFlags;
                    this.mFlags = (checked ? 2 : 0) | (this.mFlags & (-3));
           if (oldFlags != this.mFlags) {
               this.mMenu.onItemsChanged(false);
                    }
                }

                @Override 
       public boolean isVisible() {
           if (this.mActionProvider == null || !this.mActionProvider.overridesItemVisibility()) {
                        return (this.mFlags & 8) == 0;
                    }
                    return (this.mFlags & 8) == 0 && this.mActionProvider.isVisible();
                }

       boolean setVisibleInt(boolean shown) {
           int oldFlags = this.mFlags;
                    this.mFlags = (shown ? 0 : 8) | (this.mFlags & (-9));
                    return oldFlags != this.mFlags;
                }

                @Override 
       public MenuItem setVisible(boolean shown) {
           if (setVisibleInt(shown)) {
                        this.mMenu.onItemVisibleChanged(this);
                    }
           return this;
                }

                @Override 
       public MenuItem setOnMenuItemClickListener(MenuItem.OnMenuItemClickListener clickListener) {
           this.mClickListener = clickListener;
           return this;
                }

       public String toString() {
           return this.mTitle.toString();
                }

       void setMenuInfo(ContextMenu.ContextMenuInfo menuInfo) {
           this.mMenuInfo = menuInfo;
                }

                @Override 
       public ContextMenu.ContextMenuInfo getMenuInfo() {
           return this.mMenuInfo;
                }

       public void actionFormatChanged() {
           this.mMenu.onItemActionRequestChanged(this);
                }

       public boolean shouldShowIcon() {
           return this.mMenu.getOptionalIconsVisible();
                }

       public boolean isActionButton() {
                    return (this.mFlags & 32) == 32;
                }

       public boolean requestsActionButton() {
                    return (this.mShowAsAction & 1) == 1;
                }

       public boolean requiresActionButton() {
                    return (this.mShowAsAction & 2) == 2;
                }

       public void setIsActionButton(boolean isActionButton) {
           if (isActionButton) {
               this.mFlags |= 32;
                    } else {
               this.mFlags &= -33;
                    }
                }

       public boolean showsTextAsAction() {
                    return (this.mShowAsAction & 4) == 4;
                }

                @Override 
       public void setShowAsAction(int actionEnum) {
                    switch (actionEnum & 3) {
                        case 0:
                        case 1:
                        case 2:
                   this.mShowAsAction = actionEnum;
                   this.mMenu.onItemActionRequestChanged(this);
                   return;
                        default:
                   throw new IllegalArgumentException("SHOW_AS_ACTION_ALWAYS, SHOW_AS_ACTION_IF_ROOM, and SHOW_AS_ACTION_NEVER are mutually exclusive.");
                    }
                }

                @Override 
       public SupportMenuItem setActionView(View view) {
           this.mActionView = view;
           this.mActionProvider = null;
           if (view != null && view.getId() == -1 && this.mId > 0) {
               view.setId(this.mId);
                    }
           this.mMenu.onItemActionRequestChanged(this);
           return this;
                }

                @Override 
       public SupportMenuItem setActionView(int resId) {
           Context context = this.mMenu.getContext();
           LayoutInflater inflater = LayoutInflater.from(context);
           setActionView(inflater.inflate(resId, (ViewGroup) new LinearLayout(context), false));
           return this;
                }

                @Override 
       public View getActionView() {
           if (this.mActionView != null) {
               return this.mActionView;
                    }
           if (this.mActionProvider != null) {
               this.mActionView = this.mActionProvider.onCreateActionView(this);
               return this.mActionView;
                    }
           return null;
                }

                @Override 
       public MenuItem setActionProvider(android.view.ActionProvider actionProvider) {
           throw new UnsupportedOperationException("Implementation should use setSupportActionProvider!");
                }

                @Override 
       public android.view.ActionProvider getActionProvider() {
           throw new UnsupportedOperationException("Implementation should use getSupportActionProvider!");
                }

                @Override 
       public ActionProvider getSupportActionProvider() {
           return this.mActionProvider;
                }

                @Override 
       public SupportMenuItem setSupportActionProvider(ActionProvider actionProvider) {
           if (this.mActionProvider != actionProvider) {
               this.mActionView = null;
               if (this.mActionProvider != null) {
                   this.mActionProvider.setVisibilityListener(null);
                        }
               this.mActionProvider = actionProvider;
               this.mMenu.onItemsChanged(true);
               if (actionProvider != null) {
                   actionProvider.setVisibilityListener(new C00871());
                        }
                    }
           return this;
                }

                
       class C00871 implements ActionProvider.VisibilityListener {
           C00871() {
                    }

                    @Override 
           public void onActionProviderVisibilityChanged(boolean isVisible) {
               MenuItemImpl.access$000(MenuItemImpl.this).onItemVisibleChanged(MenuItemImpl.this);
                    }
                }

                @Override 
       public SupportMenuItem setShowAsActionFlags(int actionEnum) {
           setShowAsAction(actionEnum);
           return this;
                }

                @Override 
       public boolean expandActionView() {
           if ((this.mShowAsAction & 8) == 0 || this.mActionView == null) {
                        return false;
                    }
           if (this.mOnActionExpandListener == null || this.mOnActionExpandListener.onMenuItemActionExpand(this)) {
               return this.mMenu.expandItemActionView(this);
                    }
                    return false;
                }

                @Override 
       public boolean collapseActionView() {
           if ((this.mShowAsAction & 8) == 0) {
                        return false;
                    }
           if (this.mActionView == null) {
               return true;
                    }
           if (this.mOnActionExpandListener == null || this.mOnActionExpandListener.onMenuItemActionCollapse(this)) {
               return this.mMenu.collapseItemActionView(this);
                    }
                    return false;
                }

                @Override 
       public SupportMenuItem setSupportOnActionExpandListener(MenuItemCompat.OnActionExpandListener listener) {
           this.mOnActionExpandListener = listener;
           return this;
                }

       public boolean hasCollapsibleActionView() {
                    return ((this.mShowAsAction & 8) == 0 || this.mActionView == null) ? false : true;
                }

       public void setActionViewExpanded(boolean isExpanded) {
           this.mIsActionViewExpanded = isExpanded;
           this.mMenu.onItemsChanged(false);
                }

                @Override 
       public boolean isActionViewExpanded() {
           return this.mIsActionViewExpanded;
                }

                @Override 
       public MenuItem setOnActionExpandListener(MenuItem.OnActionExpandListener listener) {
           throw new UnsupportedOperationException("Implementation should use setSupportOnActionExpandListener!");
                }
            }
