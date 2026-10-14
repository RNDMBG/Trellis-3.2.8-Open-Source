            package android.support.v7.internal.view.menu;

            import android.content.Context;
            import android.content.Intent;
            import android.graphics.drawable.Drawable;
            import android.support.v4.internal.view.SupportMenuItem;
            import android.support.v4.view.MenuItemCompat;
            import android.view.ActionProvider;
            import android.view.ContextMenu;
            import android.view.MenuItem;
            import android.view.SubMenu;
            import android.view.View;
            
            
    public class ActionMenuItem implements SupportMenuItem {
                private static final int CHECKABLE = 1;
                private static final int CHECKED = 2;
                private static final int ENABLED = 16;
                private static final int EXCLUSIVE = 4;
                private static final int HIDDEN = 8;
                private static final int NO_ICON = 0;
                private final int mCategoryOrder;
                private MenuItem.OnMenuItemClickListener mClickListener;
                private Context mContext;
                private final int mGroup;
                private Drawable mIconDrawable;
                private final int mId;
                private Intent mIntent;
                private final int mOrdering;
                private char mShortcutAlphabeticChar;
                private char mShortcutNumericChar;
                private CharSequence mTitle;
                private CharSequence mTitleCondensed;
                private int mIconResId = 0;
                private int mFlags = 16;

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

        public ActionMenuItem(Context context, int group, int id, int categoryOrder, int ordering, CharSequence title) {
            this.mContext = context;
            this.mId = id;
            this.mGroup = group;
            this.mCategoryOrder = categoryOrder;
            this.mOrdering = ordering;
            this.mTitle = title;
                }

                @Override 
        public char getAlphabeticShortcut() {
            return this.mShortcutAlphabeticChar;
                }

                @Override 
        public int getGroupId() {
            return this.mGroup;
                }

                @Override 
        public Drawable getIcon() {
            return this.mIconDrawable;
                }

                @Override 
        public Intent getIntent() {
            return this.mIntent;
                }

                @Override 
        public int getItemId() {
            return this.mId;
                }

                @Override 
        public ContextMenu.ContextMenuInfo getMenuInfo() {
            return null;
                }

                @Override 
        public char getNumericShortcut() {
            return this.mShortcutNumericChar;
                }

                @Override 
       public int getOrder() {
           return this.mOrdering;
                }

                @Override 
       public SubMenu getSubMenu() {
           return null;
                }

                @Override 
       public CharSequence getTitle() {
           return this.mTitle;
                }

                @Override 
       public CharSequence getTitleCondensed() {
           return this.mTitleCondensed;
                }

                @Override 
       public boolean hasSubMenu() {
           return false;
                }

                @Override 
       public boolean isCheckable() {
                    return (this.mFlags & 1) != 0;
                }

                @Override 
       public boolean isChecked() {
                    return (this.mFlags & 2) != 0;
                }

                @Override 
       public boolean isEnabled() {
                    return (this.mFlags & 16) != 0;
                }

                @Override 
       public boolean isVisible() {
                    return (this.mFlags & 8) == 0;
                }

                @Override 
       public MenuItem setAlphabeticShortcut(char alphaChar) {
           this.mShortcutAlphabeticChar = alphaChar;
           return this;
                }

                @Override 
       public MenuItem setCheckable(boolean checkable) {
                    this.mFlags = (checkable ? 1 : 0) | (this.mFlags & (-2));
           return this;
                }

       public ActionMenuItem setExclusiveCheckable(boolean exclusive) {
                    this.mFlags = (exclusive ? 4 : 0) | (this.mFlags & (-5));
           return this;
                }

                @Override 
       public MenuItem setChecked(boolean checked) {
                    this.mFlags = (checked ? 2 : 0) | (this.mFlags & (-3));
           return this;
                }

                @Override 
       public MenuItem setEnabled(boolean enabled) {
                    this.mFlags = (enabled ? 16 : 0) | (this.mFlags & (-17));
           return this;
                }

                @Override 
       public MenuItem setIcon(Drawable icon) {
           this.mIconDrawable = icon;
           this.mIconResId = 0;
           return this;
                }

                @Override 
       public MenuItem setIcon(int iconRes) {
           this.mIconResId = iconRes;
           this.mIconDrawable = this.mContext.getResources().getDrawable(iconRes);
           return this;
                }

                @Override 
       public MenuItem setIntent(Intent intent) {
           this.mIntent = intent;
           return this;
                }

                @Override 
       public MenuItem setNumericShortcut(char numericChar) {
           this.mShortcutNumericChar = numericChar;
           return this;
                }

                @Override 
       public MenuItem setOnMenuItemClickListener(MenuItem.OnMenuItemClickListener menuItemClickListener) {
           this.mClickListener = menuItemClickListener;
           return this;
                }

                @Override 
       public MenuItem setShortcut(char numericChar, char alphaChar) {
           this.mShortcutNumericChar = numericChar;
           this.mShortcutAlphabeticChar = alphaChar;
           return this;
                }

                @Override 
       public MenuItem setTitle(CharSequence title) {
           this.mTitle = title;
           return this;
                }

                @Override 
       public MenuItem setTitle(int title) {
           this.mTitle = this.mContext.getResources().getString(title);
           return this;
                }

                @Override 
       public MenuItem setTitleCondensed(CharSequence title) {
           this.mTitleCondensed = title;
           return this;
                }

                @Override 
       public MenuItem setVisible(boolean visible) {
                    this.mFlags = (visible ? 0 : 8) | (this.mFlags & 8);
           return this;
                }

       public boolean invoke() {
           if (this.mClickListener != null && this.mClickListener.onMenuItemClick(this)) {
                        return true;
                    }
           if (this.mIntent != null) {
               this.mContext.startActivity(this.mIntent);
                        return true;
                    }
           return false;
                }

                @Override 
       public void setShowAsAction(int show) {
                }

                @Override 
       public SupportMenuItem setActionView(View actionView) {
           throw new UnsupportedOperationException();
                }

                @Override 
       public View getActionView() {
           return null;
                }

                @Override 
       public MenuItem setActionProvider(ActionProvider actionProvider) {
           throw new UnsupportedOperationException();
                }

                @Override 
       public ActionProvider getActionProvider() {
           throw new UnsupportedOperationException();
                }

                @Override 
       public SupportMenuItem setActionView(int resId) {
           throw new UnsupportedOperationException();
                }

                @Override 
       public android.support.v4.view.ActionProvider getSupportActionProvider() {
           return null;
                }

                @Override 
       public SupportMenuItem setSupportActionProvider(android.support.v4.view.ActionProvider actionProvider) {
           throw new UnsupportedOperationException();
                }

                @Override 
       public SupportMenuItem setShowAsActionFlags(int actionEnum) {
           setShowAsAction(actionEnum);
           return this;
                }

                @Override 
       public boolean expandActionView() {
           return false;
                }

                @Override 
       public boolean collapseActionView() {
           return false;
                }

                @Override 
       public boolean isActionViewExpanded() {
           return false;
                }

                @Override 
       public MenuItem setOnActionExpandListener(MenuItem.OnActionExpandListener listener) {
           throw new UnsupportedOperationException();
                }

                @Override 
       public SupportMenuItem setSupportOnActionExpandListener(MenuItemCompat.OnActionExpandListener listener) {
           return this;
                }
            }
