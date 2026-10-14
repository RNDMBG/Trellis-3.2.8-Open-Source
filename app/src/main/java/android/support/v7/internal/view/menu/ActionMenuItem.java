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
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 31 */    public class ActionMenuItem implements SupportMenuItem {
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

                @Override // android.support.v4.internal.view.SupportMenuItem, android.view.MenuItem
/* 32 */        public /* bridge */ /* synthetic */ MenuItem setActionView(int x0) {
/* 33 */            return setActionView(x0);
                }

                @Override // android.support.v4.internal.view.SupportMenuItem, android.view.MenuItem
/* 32 */        public /* bridge */ /* synthetic */ MenuItem setActionView(View x0) {
/* 33 */            return setActionView(x0);
                }

                @Override // android.support.v4.internal.view.SupportMenuItem, android.view.MenuItem
/* 32 */        public /* bridge */ /* synthetic */ MenuItem setShowAsActionFlags(int x0) {
/* 33 */            return setShowAsActionFlags(x0);
                }

/* 62 */        public ActionMenuItem(Context context, int group, int id, int categoryOrder, int ordering, CharSequence title) {
/* 64 */            this.mContext = context;
/* 65 */            this.mId = id;
/* 66 */            this.mGroup = group;
/* 67 */            this.mCategoryOrder = categoryOrder;
/* 68 */            this.mOrdering = ordering;
/* 69 */            this.mTitle = title;
                }

                @Override // android.view.MenuItem
/* 72 */        public char getAlphabeticShortcut() {
/* 73 */            return this.mShortcutAlphabeticChar;
                }

                @Override // android.view.MenuItem
/* 76 */        public int getGroupId() {
/* 77 */            return this.mGroup;
                }

                @Override // android.view.MenuItem
/* 80 */        public Drawable getIcon() {
/* 81 */            return this.mIconDrawable;
                }

                @Override // android.view.MenuItem
/* 84 */        public Intent getIntent() {
/* 85 */            return this.mIntent;
                }

                @Override // android.view.MenuItem
/* 88 */        public int getItemId() {
/* 89 */            return this.mId;
                }

                @Override // android.view.MenuItem
/* 92 */        public ContextMenu.ContextMenuInfo getMenuInfo() {
/* 93 */            return null;
                }

                @Override // android.view.MenuItem
/* 96 */        public char getNumericShortcut() {
/* 97 */            return this.mShortcutNumericChar;
                }

                @Override // android.view.MenuItem
/* 100 */       public int getOrder() {
/* 101 */           return this.mOrdering;
                }

                @Override // android.view.MenuItem
/* 104 */       public SubMenu getSubMenu() {
/* 105 */           return null;
                }

                @Override // android.view.MenuItem
/* 108 */       public CharSequence getTitle() {
/* 109 */           return this.mTitle;
                }

                @Override // android.view.MenuItem
/* 112 */       public CharSequence getTitleCondensed() {
/* 113 */           return this.mTitleCondensed;
                }

                @Override // android.view.MenuItem
/* 116 */       public boolean hasSubMenu() {
/* 117 */           return false;
                }

                @Override // android.view.MenuItem
/* 120 */       public boolean isCheckable() {
                    return (this.mFlags & 1) != 0;
                }

                @Override // android.view.MenuItem
/* 124 */       public boolean isChecked() {
                    return (this.mFlags & 2) != 0;
                }

                @Override // android.view.MenuItem
/* 128 */       public boolean isEnabled() {
                    return (this.mFlags & 16) != 0;
                }

                @Override // android.view.MenuItem
/* 132 */       public boolean isVisible() {
                    return (this.mFlags & 8) == 0;
                }

                @Override // android.view.MenuItem
/* 136 */       public MenuItem setAlphabeticShortcut(char alphaChar) {
/* 137 */           this.mShortcutAlphabeticChar = alphaChar;
/* 138 */           return this;
                }

                @Override // android.view.MenuItem
/* 141 */       public MenuItem setCheckable(boolean checkable) {
                    this.mFlags = (checkable ? 1 : 0) | (this.mFlags & (-2));
/* 143 */           return this;
                }

/* 146 */       public ActionMenuItem setExclusiveCheckable(boolean exclusive) {
                    this.mFlags = (exclusive ? 4 : 0) | (this.mFlags & (-5));
/* 148 */           return this;
                }

                @Override // android.view.MenuItem
/* 151 */       public MenuItem setChecked(boolean checked) {
                    this.mFlags = (checked ? 2 : 0) | (this.mFlags & (-3));
/* 153 */           return this;
                }

                @Override // android.view.MenuItem
/* 156 */       public MenuItem setEnabled(boolean enabled) {
                    this.mFlags = (enabled ? 16 : 0) | (this.mFlags & (-17));
/* 158 */           return this;
                }

                @Override // android.view.MenuItem
/* 161 */       public MenuItem setIcon(Drawable icon) {
/* 162 */           this.mIconDrawable = icon;
/* 163 */           this.mIconResId = 0;
/* 164 */           return this;
                }

                @Override // android.view.MenuItem
/* 167 */       public MenuItem setIcon(int iconRes) {
/* 168 */           this.mIconResId = iconRes;
/* 169 */           this.mIconDrawable = this.mContext.getResources().getDrawable(iconRes);
/* 170 */           return this;
                }

                @Override // android.view.MenuItem
/* 173 */       public MenuItem setIntent(Intent intent) {
/* 174 */           this.mIntent = intent;
/* 175 */           return this;
                }

                @Override // android.view.MenuItem
/* 178 */       public MenuItem setNumericShortcut(char numericChar) {
/* 179 */           this.mShortcutNumericChar = numericChar;
/* 180 */           return this;
                }

                @Override // android.view.MenuItem
/* 183 */       public MenuItem setOnMenuItemClickListener(MenuItem.OnMenuItemClickListener menuItemClickListener) {
/* 184 */           this.mClickListener = menuItemClickListener;
/* 185 */           return this;
                }

                @Override // android.view.MenuItem
/* 188 */       public MenuItem setShortcut(char numericChar, char alphaChar) {
/* 189 */           this.mShortcutNumericChar = numericChar;
/* 190 */           this.mShortcutAlphabeticChar = alphaChar;
/* 191 */           return this;
                }

                @Override // android.view.MenuItem
/* 194 */       public MenuItem setTitle(CharSequence title) {
/* 195 */           this.mTitle = title;
/* 196 */           return this;
                }

                @Override // android.view.MenuItem
/* 199 */       public MenuItem setTitle(int title) {
/* 200 */           this.mTitle = this.mContext.getResources().getString(title);
/* 201 */           return this;
                }

                @Override // android.view.MenuItem
/* 204 */       public MenuItem setTitleCondensed(CharSequence title) {
/* 205 */           this.mTitleCondensed = title;
/* 206 */           return this;
                }

                @Override // android.view.MenuItem
/* 209 */       public MenuItem setVisible(boolean visible) {
                    this.mFlags = (visible ? 0 : 8) | (this.mFlags & 8);
/* 211 */           return this;
                }

/* 214 */       public boolean invoke() {
/* 215 */           if (this.mClickListener != null && this.mClickListener.onMenuItemClick(this)) {
                        return true;
                    }
/* 219 */           if (this.mIntent != null) {
/* 220 */               this.mContext.startActivity(this.mIntent);
                        return true;
                    }
/* 224 */           return false;
                }

                @Override // android.support.v4.internal.view.SupportMenuItem, android.view.MenuItem
/* 228 */       public void setShowAsAction(int show) {
                }

                @Override // android.support.v4.internal.view.SupportMenuItem, android.view.MenuItem
/* 231 */       public SupportMenuItem setActionView(View actionView) {
/* 232 */           throw new UnsupportedOperationException();
                }

                @Override // android.support.v4.internal.view.SupportMenuItem, android.view.MenuItem
/* 235 */       public View getActionView() {
/* 236 */           return null;
                }

                @Override // android.view.MenuItem
/* 240 */       public MenuItem setActionProvider(ActionProvider actionProvider) {
/* 241 */           throw new UnsupportedOperationException();
                }

                @Override // android.view.MenuItem
/* 245 */       public ActionProvider getActionProvider() {
/* 246 */           throw new UnsupportedOperationException();
                }

                @Override // android.support.v4.internal.view.SupportMenuItem, android.view.MenuItem
/* 250 */       public SupportMenuItem setActionView(int resId) {
/* 251 */           throw new UnsupportedOperationException();
                }

                @Override // android.support.v4.internal.view.SupportMenuItem
/* 255 */       public android.support.v4.view.ActionProvider getSupportActionProvider() {
/* 256 */           return null;
                }

                @Override // android.support.v4.internal.view.SupportMenuItem
/* 260 */       public SupportMenuItem setSupportActionProvider(android.support.v4.view.ActionProvider actionProvider) {
/* 261 */           throw new UnsupportedOperationException();
                }

                @Override // android.support.v4.internal.view.SupportMenuItem, android.view.MenuItem
/* 265 */       public SupportMenuItem setShowAsActionFlags(int actionEnum) {
/* 266 */           setShowAsAction(actionEnum);
/* 267 */           return this;
                }

                @Override // android.support.v4.internal.view.SupportMenuItem, android.view.MenuItem
/* 271 */       public boolean expandActionView() {
/* 272 */           return false;
                }

                @Override // android.support.v4.internal.view.SupportMenuItem, android.view.MenuItem
/* 276 */       public boolean collapseActionView() {
/* 277 */           return false;
                }

                @Override // android.support.v4.internal.view.SupportMenuItem, android.view.MenuItem
/* 281 */       public boolean isActionViewExpanded() {
/* 282 */           return false;
                }

                @Override // android.view.MenuItem
/* 286 */       public MenuItem setOnActionExpandListener(MenuItem.OnActionExpandListener listener) {
/* 287 */           throw new UnsupportedOperationException();
                }

                @Override // android.support.v4.internal.view.SupportMenuItem
/* 292 */       public SupportMenuItem setSupportOnActionExpandListener(MenuItemCompat.OnActionExpandListener listener) {
/* 293 */           return this;
                }
            }
