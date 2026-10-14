            package android.support.v7.internal.view.menu;

            import android.content.Intent;
            import android.graphics.drawable.Drawable;
            import android.support.v4.internal.view.SupportMenuItem;
            import android.support.v4.view.MenuItemCompat;
            import android.support.v7.view.CollapsibleActionView;
            import android.util.Log;
            import android.view.ActionProvider;
            import android.view.ContextMenu;
            import android.view.MenuItem;
            import android.view.SubMenu;
            import android.view.View;
            import android.widget.FrameLayout;
            import java.lang.reflect.Method;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 35 */    public class MenuItemWrapperICS extends BaseMenuWrapper<MenuItem> implements SupportMenuItem {
                static final String LOG_TAG = "MenuItemWrapper";
                private final boolean mEmulateProviderVisibilityOverride;
                private boolean mLastRequestVisible;
                private Method mSetExclusiveCheckableMethod;

/* 36 */        static /* synthetic */ boolean access$000(MenuItemWrapperICS x0) {
/* 37 */            return x0.mEmulateProviderVisibilityOverride;
                }

/* 36 */        static /* synthetic */ boolean access$100(MenuItemWrapperICS x0) {
/* 37 */            return x0.mLastRequestVisible;
                }

/* 47 */        MenuItemWrapperICS(MenuItem object, boolean emulateProviderVisibilityOverride) {
/* 48 */            super(object);
/* 49 */            this.mLastRequestVisible = object.isVisible();
/* 50 */            this.mEmulateProviderVisibilityOverride = emulateProviderVisibilityOverride;
                }

/* 53 */        MenuItemWrapperICS(MenuItem object) {
/* 54 */            this(object, true);
                }

                @Override // android.view.MenuItem
/* 58 */        public int getItemId() {
/* 59 */            return ((MenuItem) this.mWrappedObject).getItemId();
                }

                @Override // android.view.MenuItem
/* 63 */        public int getGroupId() {
/* 64 */            return ((MenuItem) this.mWrappedObject).getGroupId();
                }

                @Override // android.view.MenuItem
/* 68 */        public int getOrder() {
/* 69 */            return ((MenuItem) this.mWrappedObject).getOrder();
                }

                @Override // android.view.MenuItem
/* 73 */        public MenuItem setTitle(CharSequence title) {
/* 74 */            ((MenuItem) this.mWrappedObject).setTitle(title);
/* 75 */            return this;
                }

                @Override // android.view.MenuItem
/* 79 */        public MenuItem setTitle(int title) {
/* 80 */            ((MenuItem) this.mWrappedObject).setTitle(title);
/* 81 */            return this;
                }

                @Override // android.view.MenuItem
/* 85 */        public CharSequence getTitle() {
/* 86 */            return ((MenuItem) this.mWrappedObject).getTitle();
                }

                @Override // android.view.MenuItem
/* 90 */        public MenuItem setTitleCondensed(CharSequence title) {
/* 91 */            ((MenuItem) this.mWrappedObject).setTitleCondensed(title);
/* 92 */            return this;
                }

                @Override // android.view.MenuItem
/* 96 */        public CharSequence getTitleCondensed() {
/* 97 */            return ((MenuItem) this.mWrappedObject).getTitleCondensed();
                }

                @Override // android.view.MenuItem
/* 101 */       public MenuItem setIcon(Drawable icon) {
/* 102 */           ((MenuItem) this.mWrappedObject).setIcon(icon);
/* 103 */           return this;
                }

                @Override // android.view.MenuItem
/* 107 */       public MenuItem setIcon(int iconRes) {
/* 108 */           ((MenuItem) this.mWrappedObject).setIcon(iconRes);
/* 109 */           return this;
                }

                @Override // android.view.MenuItem
/* 113 */       public Drawable getIcon() {
/* 114 */           return ((MenuItem) this.mWrappedObject).getIcon();
                }

                @Override // android.view.MenuItem
/* 118 */       public MenuItem setIntent(Intent intent) {
/* 119 */           ((MenuItem) this.mWrappedObject).setIntent(intent);
/* 120 */           return this;
                }

                @Override // android.view.MenuItem
/* 124 */       public Intent getIntent() {
/* 125 */           return ((MenuItem) this.mWrappedObject).getIntent();
                }

                @Override // android.view.MenuItem
/* 129 */       public MenuItem setShortcut(char numericChar, char alphaChar) {
/* 130 */           ((MenuItem) this.mWrappedObject).setShortcut(numericChar, alphaChar);
/* 131 */           return this;
                }

                @Override // android.view.MenuItem
/* 135 */       public MenuItem setNumericShortcut(char numericChar) {
/* 136 */           ((MenuItem) this.mWrappedObject).setNumericShortcut(numericChar);
/* 137 */           return this;
                }

                @Override // android.view.MenuItem
/* 141 */       public char getNumericShortcut() {
/* 142 */           return ((MenuItem) this.mWrappedObject).getNumericShortcut();
                }

                @Override // android.view.MenuItem
/* 146 */       public MenuItem setAlphabeticShortcut(char alphaChar) {
/* 147 */           ((MenuItem) this.mWrappedObject).setAlphabeticShortcut(alphaChar);
/* 148 */           return this;
                }

                @Override // android.view.MenuItem
/* 152 */       public char getAlphabeticShortcut() {
/* 153 */           return ((MenuItem) this.mWrappedObject).getAlphabeticShortcut();
                }

                @Override // android.view.MenuItem
/* 157 */       public MenuItem setCheckable(boolean checkable) {
/* 158 */           ((MenuItem) this.mWrappedObject).setCheckable(checkable);
/* 159 */           return this;
                }

                @Override // android.view.MenuItem
/* 163 */       public boolean isCheckable() {
/* 164 */           return ((MenuItem) this.mWrappedObject).isCheckable();
                }

                @Override // android.view.MenuItem
/* 168 */       public MenuItem setChecked(boolean checked) {
/* 169 */           ((MenuItem) this.mWrappedObject).setChecked(checked);
/* 170 */           return this;
                }

                @Override // android.view.MenuItem
/* 174 */       public boolean isChecked() {
/* 175 */           return ((MenuItem) this.mWrappedObject).isChecked();
                }

                @Override // android.view.MenuItem
/* 179 */       public MenuItem setVisible(boolean visible) {
/* 180 */           if (this.mEmulateProviderVisibilityOverride) {
/* 181 */               this.mLastRequestVisible = visible;
/* 183 */               if (checkActionProviderOverrideVisibility()) {
/* 187 */                   return this;
                        }
                    }
                    return wrappedSetVisible(visible);
                }

                @Override // android.view.MenuItem
/* 191 */       public boolean isVisible() {
/* 192 */           return ((MenuItem) this.mWrappedObject).isVisible();
                }

                @Override // android.view.MenuItem
/* 196 */       public MenuItem setEnabled(boolean enabled) {
/* 197 */           ((MenuItem) this.mWrappedObject).setEnabled(enabled);
/* 198 */           return this;
                }

                @Override // android.view.MenuItem
/* 202 */       public boolean isEnabled() {
/* 203 */           return ((MenuItem) this.mWrappedObject).isEnabled();
                }

                @Override // android.view.MenuItem
/* 207 */       public boolean hasSubMenu() {
/* 208 */           return ((MenuItem) this.mWrappedObject).hasSubMenu();
                }

                @Override // android.view.MenuItem
/* 212 */       public SubMenu getSubMenu() {
/* 213 */           return getSubMenuWrapper(((MenuItem) this.mWrappedObject).getSubMenu());
                }

                @Override // android.view.MenuItem
/* 217 */       public MenuItem setOnMenuItemClickListener(MenuItem.OnMenuItemClickListener menuItemClickListener) {
/* 218 */           ((MenuItem) this.mWrappedObject).setOnMenuItemClickListener(menuItemClickListener != null ? new OnMenuItemClickListenerWrapper(menuItemClickListener) : null);
/* 220 */           return this;
                }

                @Override // android.view.MenuItem
/* 224 */       public ContextMenu.ContextMenuInfo getMenuInfo() {
/* 225 */           return ((MenuItem) this.mWrappedObject).getMenuInfo();
                }

                @Override // android.support.v4.internal.view.SupportMenuItem, android.view.MenuItem
/* 229 */       public void setShowAsAction(int actionEnum) {
/* 230 */           ((MenuItem) this.mWrappedObject).setShowAsAction(actionEnum);
                }

                @Override // android.support.v4.internal.view.SupportMenuItem, android.view.MenuItem
/* 234 */       public MenuItem setShowAsActionFlags(int actionEnum) {
/* 235 */           ((MenuItem) this.mWrappedObject).setShowAsActionFlags(actionEnum);
/* 236 */           return this;
                }

                @Override // android.support.v4.internal.view.SupportMenuItem, android.view.MenuItem
/* 240 */       public MenuItem setActionView(View view) {
/* 241 */           if (view instanceof CollapsibleActionView) {
/* 242 */               view = new CollapsibleActionViewWrapper(view);
                    }
/* 244 */           ((MenuItem) this.mWrappedObject).setActionView(view);
/* 245 */           return this;
                }

                @Override // android.support.v4.internal.view.SupportMenuItem, android.view.MenuItem
/* 250 */       public MenuItem setActionView(int resId) {
/* 251 */           ((MenuItem) this.mWrappedObject).setActionView(resId);
/* 253 */           View actionView = ((MenuItem) this.mWrappedObject).getActionView();
/* 254 */           if (actionView instanceof CollapsibleActionView) {
/* 256 */               ((MenuItem) this.mWrappedObject).setActionView(new CollapsibleActionViewWrapper(actionView));
                    }
/* 258 */           return this;
                }

                @Override // android.support.v4.internal.view.SupportMenuItem, android.view.MenuItem
/* 262 */       public View getActionView() {
/* 263 */           View actionView = ((MenuItem) this.mWrappedObject).getActionView();
/* 264 */           if (actionView instanceof CollapsibleActionViewWrapper) {
/* 265 */               return ((CollapsibleActionViewWrapper) actionView).getWrappedView();
                    }
/* 267 */           return actionView;
                }

                @Override // android.view.MenuItem
/* 271 */       public MenuItem setActionProvider(ActionProvider provider) {
/* 272 */           ((MenuItem) this.mWrappedObject).setActionProvider(provider);
/* 273 */           if (provider != null && this.mEmulateProviderVisibilityOverride) {
/* 274 */               checkActionProviderOverrideVisibility();
                    }
/* 276 */           return this;
                }

                @Override // android.view.MenuItem
/* 280 */       public ActionProvider getActionProvider() {
/* 281 */           return ((MenuItem) this.mWrappedObject).getActionProvider();
                }

                @Override // android.support.v4.internal.view.SupportMenuItem, android.view.MenuItem
/* 285 */       public boolean expandActionView() {
/* 286 */           return ((MenuItem) this.mWrappedObject).expandActionView();
                }

                @Override // android.support.v4.internal.view.SupportMenuItem, android.view.MenuItem
/* 290 */       public boolean collapseActionView() {
/* 291 */           return ((MenuItem) this.mWrappedObject).collapseActionView();
                }

                @Override // android.support.v4.internal.view.SupportMenuItem, android.view.MenuItem
/* 295 */       public boolean isActionViewExpanded() {
/* 296 */           return ((MenuItem) this.mWrappedObject).isActionViewExpanded();
                }

                @Override // android.view.MenuItem
/* 300 */       public MenuItem setOnActionExpandListener(MenuItem.OnActionExpandListener listener) {
/* 301 */           ((MenuItem) this.mWrappedObject).setOnActionExpandListener(listener);
/* 302 */           return this;
                }

                @Override // android.support.v4.internal.view.SupportMenuItem
/* 307 */       public SupportMenuItem setSupportOnActionExpandListener(MenuItemCompat.OnActionExpandListener listener) {
/* 308 */           ((MenuItem) this.mWrappedObject).setOnActionExpandListener(listener != null ? new OnActionExpandListenerWrapper(listener) : null);
                    return null;
                }

                @Override // android.support.v4.internal.view.SupportMenuItem
/* 314 */       public SupportMenuItem setSupportActionProvider(android.support.v4.view.ActionProvider actionProvider) {
/* 315 */           ((MenuItem) this.mWrappedObject).setActionProvider(actionProvider != null ? createActionProviderWrapper(actionProvider) : null);
/* 317 */           return this;
                }

                @Override // android.support.v4.internal.view.SupportMenuItem
/* 321 */       public android.support.v4.view.ActionProvider getSupportActionProvider() {
/* 322 */           ActionProviderWrapper providerWrapper = (ActionProviderWrapper) ((MenuItem) this.mWrappedObject).getActionProvider();
/* 324 */           if (providerWrapper != null) {
                        return providerWrapper.mInner;
                    }
                    return null;
                }

/* 328 */       public void setExclusiveCheckable(boolean checkable) {
                    try {
/* 329 */               if (this.mSetExclusiveCheckableMethod == null) {
                            this.mSetExclusiveCheckableMethod = ((MenuItem) this.mWrappedObject).getClass().getDeclaredMethod("setExclusiveCheckable", Boolean.TYPE);
                        }
/* 333 */               this.mSetExclusiveCheckableMethod.invoke(this.mWrappedObject, Boolean.valueOf(checkable));
                    } catch (Exception e) {
/* 335 */               Log.w(LOG_TAG, "Error while calling setExclusiveCheckable", e);
                    }
                }

/* 339 */       ActionProviderWrapper createActionProviderWrapper(android.support.v4.view.ActionProvider provider) {
/* 340 */           return new ActionProviderWrapper(provider);
                }

/* 346 */       final boolean checkActionProviderOverrideVisibility() {
                    android.support.v4.view.ActionProvider provider;
/* 347 */           if (!this.mLastRequestVisible || (provider = getSupportActionProvider()) == null || !provider.overridesItemVisibility() || provider.isVisible()) {
                        return false;
                    }
/* 350 */           wrappedSetVisible(false);
/* 351 */           return true;
                }

/* 357 */       final MenuItem wrappedSetVisible(boolean visible) {
/* 358 */           return ((MenuItem) this.mWrappedObject).setVisible(visible);
                }

/* 362 */       private class OnMenuItemClickListenerWrapper extends BaseWrapper<MenuItem.OnMenuItemClickListener> implements MenuItem.OnMenuItemClickListener {
/* 363 */           OnMenuItemClickListenerWrapper(MenuItem.OnMenuItemClickListener object) {
/* 365 */               super(object);
                    }

                    @Override // android.view.MenuItem.OnMenuItemClickListener
/* 369 */           public boolean onMenuItemClick(MenuItem item) {
/* 370 */               return ((MenuItem.OnMenuItemClickListener) this.mWrappedObject).onMenuItemClick(MenuItemWrapperICS.this.getMenuItemWrapper(item));
                    }
                }

/* 375 */       private class OnActionExpandListenerWrapper extends BaseWrapper<MenuItemCompat.OnActionExpandListener> implements MenuItem.OnActionExpandListener {
/* 376 */           OnActionExpandListenerWrapper(MenuItemCompat.OnActionExpandListener object) {
/* 378 */               super(object);
                    }

                    @Override // android.view.MenuItem.OnActionExpandListener
/* 382 */           public boolean onMenuItemActionExpand(MenuItem item) {
/* 383 */               return ((MenuItemCompat.OnActionExpandListener) this.mWrappedObject).onMenuItemActionExpand(MenuItemWrapperICS.this.getMenuItemWrapper(item));
                    }

                    @Override // android.view.MenuItem.OnActionExpandListener
/* 387 */           public boolean onMenuItemActionCollapse(MenuItem item) {
/* 388 */               return ((MenuItemCompat.OnActionExpandListener) this.mWrappedObject).onMenuItemActionCollapse(MenuItemWrapperICS.this.getMenuItemWrapper(item));
                    }
                }

/* 393 */       class ActionProviderWrapper extends ActionProvider {
                    final android.support.v4.view.ActionProvider mInner;

/* 394 */           public ActionProviderWrapper(android.support.v4.view.ActionProvider inner) {
/* 396 */               super(inner.getContext());
/* 397 */               this.mInner = inner;
/* 399 */               if (MenuItemWrapperICS.access$000(MenuItemWrapperICS.this)) {
/* 400 */                   this.mInner.setVisibilityListener(new C00881(MenuItemWrapperICS.this));
                        }
                    }

                    /* JADX INFO: renamed from: android.support.v7.internal.view.menu.MenuItemWrapperICS$ActionProviderWrapper$1 */
/* 398 */           class C00881 implements android.support.v4.view.ActionProvider.VisibilityListener {
                        final /* synthetic */ MenuItemWrapperICS val$this$0;

/* 399 */               C00881(MenuItemWrapperICS menuItemWrapperICS) {
                            this.val$this$0 = menuItemWrapperICS;
                        }

                        @Override // android.support.v4.view.ActionProvider.VisibilityListener
/* 402 */               public void onActionProviderVisibilityChanged(boolean isVisible) {
/* 403 */                   if (ActionProviderWrapper.this.mInner.overridesItemVisibility() && MenuItemWrapperICS.access$100(MenuItemWrapperICS.this)) {
/* 404 */                       MenuItemWrapperICS.this.wrappedSetVisible(isVisible);
                            }
                        }
                    }

                    @Override // android.view.ActionProvider
/* 412 */           public View onCreateActionView() {
/* 413 */               if (MenuItemWrapperICS.access$000(MenuItemWrapperICS.this)) {
/* 416 */                   MenuItemWrapperICS.this.checkActionProviderOverrideVisibility();
                        }
/* 418 */               return this.mInner.onCreateActionView();
                    }

                    @Override // android.view.ActionProvider
/* 422 */           public boolean onPerformDefaultAction() {
/* 423 */               return this.mInner.onPerformDefaultAction();
                    }

                    @Override // android.view.ActionProvider
/* 427 */           public boolean hasSubMenu() {
/* 428 */               return this.mInner.hasSubMenu();
                    }

                    @Override // android.view.ActionProvider
/* 432 */           public void onPrepareSubMenu(SubMenu subMenu) {
/* 433 */               this.mInner.onPrepareSubMenu(MenuItemWrapperICS.this.getSubMenuWrapper(subMenu));
                    }
                }

/* 440 */       static class CollapsibleActionViewWrapper extends FrameLayout implements android.view.CollapsibleActionView {
                    final CollapsibleActionView mWrappedView;

                    /* JADX WARN: Multi-variable type inference failed */
/* 441 */           CollapsibleActionViewWrapper(View view) {
/* 442 */               super(view.getContext());
/* 443 */               this.mWrappedView = (CollapsibleActionView) view;
/* 444 */               addView(view);
                    }

                    @Override // android.view.CollapsibleActionView
/* 448 */           public void onActionViewExpanded() {
/* 449 */               this.mWrappedView.onActionViewExpanded();
                    }

                    @Override // android.view.CollapsibleActionView
/* 453 */           public void onActionViewCollapsed() {
/* 454 */               this.mWrappedView.onActionViewCollapsed();
                    }

/* 457 */           View getWrappedView() {
/* 458 */               return (View) this.mWrappedView;
                    }
                }
            }
