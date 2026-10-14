            package android.support.v7.app;

            import android.R;
            import android.app.Activity;
            import android.content.Context;
            import android.content.res.TypedArray;
            import android.graphics.drawable.Drawable;
            import android.support.v4.app.FragmentTransaction;
            import android.view.View;
            import android.view.ViewGroup;
            import android.widget.ImageView;
            import android.widget.SpinnerAdapter;
            import java.lang.ref.WeakReference;
            import java.util.ArrayList;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 44 */    class ActionBarImplICS extends ActionBar {
                final android.app.ActionBar mActionBar;
                FragmentTransaction mActiveTransaction;
                final Activity mActivity;
                private ArrayList<WeakReference<OnMenuVisibilityListenerWrapper>> mAddedMenuVisWrappers;
                final ActionBar.Callback mCallback;
                private ImageView mHomeActionView;

/* 45 */        public ActionBarImplICS(Activity activity, ActionBar.Callback callback) {
/* 46 */            this(activity, callback, true);
                }

/* 48 */        ActionBarImplICS(Activity activity, ActionBar.Callback callback, boolean checkHomeAsUpOption) {
/* 42 */            this.mAddedMenuVisWrappers = new ArrayList<>();
/* 50 */            this.mActivity = activity;
/* 51 */            this.mCallback = callback;
/* 52 */            this.mActionBar = activity.getActionBar();
/* 54 */            if (checkHomeAsUpOption && (getDisplayOptions() & 4) != 0) {
/* 58 */                setHomeButtonEnabled(true);
                    }
                }

/* 64 */        private OnMenuVisibilityListenerWrapper findAndRemoveMenuVisWrapper(ActionBar.OnMenuVisibilityListener compatListener) {
/* 65 */            int i = 0;
                    while (i < this.mAddedMenuVisWrappers.size()) {
/* 66 */                OnMenuVisibilityListenerWrapper wrapper = this.mAddedMenuVisWrappers.get(i).get();
/* 67 */                if (wrapper == null) {
/* 68 */                    this.mAddedMenuVisWrappers.remove(i);
                            i--;
                        } else if (wrapper.mWrappedListener == compatListener) {
/* 70 */                    this.mAddedMenuVisWrappers.remove(i);
/* 74 */                    return wrapper;
                        }
/* 65 */                i++;
                    }
                    return null;
                }

                @Override // android.support.v7.app.ActionBar
/* 78 */        public void setCustomView(View view) {
/* 79 */            this.mActionBar.setCustomView(view);
                }

                @Override // android.support.v7.app.ActionBar
/* 83 */        public void setCustomView(View view, ActionBar.LayoutParams layoutParams) {
/* 84 */            android.app.ActionBar.LayoutParams lp = new android.app.ActionBar.LayoutParams(layoutParams);
/* 86 */            lp.gravity = layoutParams.gravity;
/* 88 */            this.mActionBar.setCustomView(view, lp);
                }

                @Override // android.support.v7.app.ActionBar
/* 92 */        public void setCustomView(int resId) {
/* 93 */            this.mActionBar.setCustomView(resId);
                }

                @Override // android.support.v7.app.ActionBar
/* 97 */        public void setIcon(int resId) {
/* 98 */            this.mActionBar.setIcon(resId);
                }

                @Override // android.support.v7.app.ActionBar
/* 102 */       public void setIcon(Drawable icon) {
/* 103 */           this.mActionBar.setIcon(icon);
                }

                @Override // android.support.v7.app.ActionBar
/* 107 */       public void setLogo(int resId) {
/* 108 */           this.mActionBar.setLogo(resId);
                }

                @Override // android.support.v7.app.ActionBar
/* 112 */       public void setLogo(Drawable logo) {
/* 113 */           this.mActionBar.setLogo(logo);
                }

                @Override // android.support.v7.app.ActionBar
/* 117 */       public void setListNavigationCallbacks(SpinnerAdapter adapter, ActionBar.OnNavigationListener callback) {
/* 118 */           this.mActionBar.setListNavigationCallbacks(adapter, callback != null ? new OnNavigationListenerWrapper(callback) : null);
                }

                @Override // android.support.v7.app.ActionBar
/* 123 */       public void setSelectedNavigationItem(int position) {
/* 124 */           this.mActionBar.setSelectedNavigationItem(position);
                }

                @Override // android.support.v7.app.ActionBar
/* 128 */       public int getSelectedNavigationIndex() {
/* 129 */           return this.mActionBar.getSelectedNavigationIndex();
                }

                @Override // android.support.v7.app.ActionBar
/* 133 */       public int getNavigationItemCount() {
/* 134 */           return this.mActionBar.getNavigationItemCount();
                }

                @Override // android.support.v7.app.ActionBar
/* 138 */       public void setTitle(CharSequence title) {
/* 139 */           this.mActionBar.setTitle(title);
                }

                @Override // android.support.v7.app.ActionBar
/* 143 */       public void setTitle(int resId) {
/* 144 */           this.mActionBar.setTitle(resId);
                }

                @Override // android.support.v7.app.ActionBar
/* 148 */       public void setSubtitle(CharSequence subtitle) {
/* 149 */           this.mActionBar.setSubtitle(subtitle);
                }

                @Override // android.support.v7.app.ActionBar
/* 153 */       public void setSubtitle(int resId) {
/* 154 */           this.mActionBar.setSubtitle(resId);
                }

                @Override // android.support.v7.app.ActionBar
/* 158 */       public void setDisplayOptions(int options) {
/* 159 */           this.mActionBar.setDisplayOptions(options);
                }

                @Override // android.support.v7.app.ActionBar
/* 163 */       public void setDisplayOptions(int options, int mask) {
/* 164 */           this.mActionBar.setDisplayOptions(options, mask);
                }

                @Override // android.support.v7.app.ActionBar
/* 168 */       public void setDisplayUseLogoEnabled(boolean useLogo) {
/* 169 */           this.mActionBar.setDisplayUseLogoEnabled(useLogo);
                }

                @Override // android.support.v7.app.ActionBar
/* 173 */       public void setDisplayShowHomeEnabled(boolean showHome) {
/* 174 */           this.mActionBar.setDisplayShowHomeEnabled(showHome);
                }

                @Override // android.support.v7.app.ActionBar
/* 178 */       public void setDisplayHomeAsUpEnabled(boolean showHomeAsUp) {
/* 179 */           this.mActionBar.setDisplayHomeAsUpEnabled(showHomeAsUp);
                }

                @Override // android.support.v7.app.ActionBar
/* 183 */       public void setDisplayShowTitleEnabled(boolean showTitle) {
/* 184 */           this.mActionBar.setDisplayShowTitleEnabled(showTitle);
                }

                @Override // android.support.v7.app.ActionBar
/* 188 */       public void setDisplayShowCustomEnabled(boolean showCustom) {
/* 189 */           this.mActionBar.setDisplayShowCustomEnabled(showCustom);
                }

                @Override // android.support.v7.app.ActionBar
/* 193 */       public void setBackgroundDrawable(Drawable d) {
/* 194 */           this.mActionBar.setBackgroundDrawable(d);
                }

                @Override // android.support.v7.app.ActionBar
/* 198 */       public void setStackedBackgroundDrawable(Drawable d) {
/* 199 */           this.mActionBar.setStackedBackgroundDrawable(d);
                }

                @Override // android.support.v7.app.ActionBar
/* 203 */       public void setSplitBackgroundDrawable(Drawable d) {
/* 204 */           this.mActionBar.setSplitBackgroundDrawable(d);
                }

                @Override // android.support.v7.app.ActionBar
/* 208 */       public View getCustomView() {
/* 209 */           return this.mActionBar.getCustomView();
                }

                @Override // android.support.v7.app.ActionBar
/* 213 */       public CharSequence getTitle() {
/* 214 */           return this.mActionBar.getTitle();
                }

                @Override // android.support.v7.app.ActionBar
/* 218 */       public CharSequence getSubtitle() {
/* 219 */           return this.mActionBar.getSubtitle();
                }

                @Override // android.support.v7.app.ActionBar
/* 223 */       public int getNavigationMode() {
/* 224 */           return this.mActionBar.getNavigationMode();
                }

                @Override // android.support.v7.app.ActionBar
/* 228 */       public void setNavigationMode(int mode) {
/* 229 */           this.mActionBar.setNavigationMode(mode);
                }

                @Override // android.support.v7.app.ActionBar
/* 233 */       public int getDisplayOptions() {
/* 234 */           return this.mActionBar.getDisplayOptions();
                }

                @Override // android.support.v7.app.ActionBar
/* 238 */       public ActionBar.Tab newTab() {
/* 239 */           android.app.ActionBar.Tab realTab = this.mActionBar.newTab();
/* 240 */           TabWrapper result = new TabWrapper(realTab);
/* 241 */           realTab.setTag(result);
/* 242 */           return result;
                }

                @Override // android.support.v7.app.ActionBar
/* 246 */       public void addTab(ActionBar.Tab tab) {
/* 247 */           this.mActionBar.addTab(((TabWrapper) tab).mWrappedTab);
                }

                @Override // android.support.v7.app.ActionBar
/* 251 */       public void addTab(ActionBar.Tab tab, boolean setSelected) {
/* 252 */           this.mActionBar.addTab(((TabWrapper) tab).mWrappedTab, setSelected);
                }

                @Override // android.support.v7.app.ActionBar
/* 256 */       public void addTab(ActionBar.Tab tab, int position) {
/* 257 */           this.mActionBar.addTab(((TabWrapper) tab).mWrappedTab, position);
                }

                @Override // android.support.v7.app.ActionBar
/* 261 */       public void addTab(ActionBar.Tab tab, int position, boolean setSelected) {
/* 262 */           this.mActionBar.addTab(((TabWrapper) tab).mWrappedTab, position, setSelected);
                }

                @Override // android.support.v7.app.ActionBar
/* 266 */       public void removeTab(ActionBar.Tab tab) {
/* 267 */           this.mActionBar.removeTab(((TabWrapper) tab).mWrappedTab);
                }

                @Override // android.support.v7.app.ActionBar
/* 271 */       public void removeTabAt(int position) {
/* 272 */           this.mActionBar.removeTabAt(position);
                }

                @Override // android.support.v7.app.ActionBar
/* 276 */       public void removeAllTabs() {
/* 277 */           this.mActionBar.removeAllTabs();
                }

                @Override // android.support.v7.app.ActionBar
/* 281 */       public void selectTab(ActionBar.Tab tab) {
/* 282 */           this.mActionBar.selectTab(((TabWrapper) tab).mWrappedTab);
                }

                @Override // android.support.v7.app.ActionBar
/* 286 */       public ActionBar.Tab getSelectedTab() {
/* 287 */           return (ActionBar.Tab) this.mActionBar.getSelectedTab().getTag();
                }

                @Override // android.support.v7.app.ActionBar
/* 291 */       public ActionBar.Tab getTabAt(int index) {
/* 292 */           return (ActionBar.Tab) this.mActionBar.getTabAt(index).getTag();
                }

                @Override // android.support.v7.app.ActionBar
/* 296 */       public int getTabCount() {
/* 297 */           return this.mActionBar.getTabCount();
                }

                @Override // android.support.v7.app.ActionBar
/* 301 */       public Context getThemedContext() {
/* 302 */           return this.mActionBar.getThemedContext();
                }

                @Override // android.support.v7.app.ActionBar
/* 306 */       public void setHomeAsUpIndicator(Drawable indicator) {
/* 307 */           ImageView homeActionView = getHomeActionView();
/* 308 */           if (homeActionView != null) {
/* 309 */               if (indicator == null) {
/* 310 */                   indicator = getThemeDefaultUpIndicator();
                        }
/* 312 */               homeActionView.setImageDrawable(indicator);
                    }
                }

                @Override // android.support.v7.app.ActionBar
/* 317 */       public void setHomeAsUpIndicator(int resId) {
/* 318 */           ImageView homeActionView = getHomeActionView();
/* 319 */           if (homeActionView != null) {
/* 320 */               if (resId != 0) {
/* 321 */                   homeActionView.setImageResource(resId);
                        } else {
/* 323 */                   homeActionView.setImageDrawable(getThemeDefaultUpIndicator());
                        }
                    }
                }

                @Override // android.support.v7.app.ActionBar
/* 329 */       public int getHeight() {
/* 330 */           return this.mActionBar.getHeight();
                }

                @Override // android.support.v7.app.ActionBar
/* 334 */       public void show() {
/* 335 */           this.mActionBar.show();
                }

                @Override // android.support.v7.app.ActionBar
/* 339 */       public void hide() {
/* 340 */           this.mActionBar.hide();
                }

                @Override // android.support.v7.app.ActionBar
/* 344 */       public boolean isShowing() {
/* 345 */           return this.mActionBar.isShowing();
                }

                @Override // android.support.v7.app.ActionBar
/* 349 */       public void addOnMenuVisibilityListener(ActionBar.OnMenuVisibilityListener listener) {
/* 350 */           if (listener != null) {
/* 351 */               OnMenuVisibilityListenerWrapper w = new OnMenuVisibilityListenerWrapper(listener);
/* 352 */               this.mAddedMenuVisWrappers.add(new WeakReference<>(w));
/* 353 */               this.mActionBar.addOnMenuVisibilityListener(w);
                    }
                }

                @Override // android.support.v7.app.ActionBar
/* 358 */       public void removeOnMenuVisibilityListener(ActionBar.OnMenuVisibilityListener listener) {
/* 359 */           OnMenuVisibilityListenerWrapper l = findAndRemoveMenuVisWrapper(listener);
/* 360 */           this.mActionBar.removeOnMenuVisibilityListener(l);
                }

                @Override // android.support.v7.app.ActionBar
/* 364 */       public void setHomeButtonEnabled(boolean enabled) {
/* 365 */           this.mActionBar.setHomeButtonEnabled(enabled);
                }

/* 368 */       FragmentTransaction getActiveTransaction() {
/* 369 */           if (this.mActiveTransaction == null) {
/* 370 */               this.mActiveTransaction = this.mCallback.getSupportFragmentManager().beginTransaction().disallowAddToBackStack();
                    }
/* 373 */           return this.mActiveTransaction;
                }

/* 376 */       void commitActiveTransaction() {
/* 377 */           if (this.mActiveTransaction != null && !this.mActiveTransaction.isEmpty()) {
/* 378 */               this.mActiveTransaction.commit();
                    }
/* 380 */           this.mActiveTransaction = null;
                }

/* 383 */       ImageView getHomeActionView() {
/* 384 */           if (this.mHomeActionView == null) {
/* 385 */               View home = this.mActivity.findViewById(R.id.home);
/* 386 */               if (home == null) {
                            return null;
                        }
/* 391 */               ViewGroup parent = (ViewGroup) home.getParent();
/* 392 */               int childCount = parent.getChildCount();
/* 393 */               if (childCount != 2) {
                            return null;
                        }
/* 398 */               View first = parent.getChildAt(0);
/* 399 */               View second = parent.getChildAt(1);
/* 400 */               View up = first.getId() == 16908332 ? second : first;
/* 402 */               if (up instanceof ImageView) {
/* 404 */                   this.mHomeActionView = (ImageView) up;
                        }
                    }
/* 407 */           return this.mHomeActionView;
                }

/* 410 */       Drawable getThemeDefaultUpIndicator() {
/* 411 */           TypedArray a = this.mActivity.obtainStyledAttributes(new int[]{R.attr.homeAsUpIndicator});
/* 413 */           Drawable result = a.getDrawable(0);
/* 414 */           a.recycle();
/* 415 */           return result;
                }

/* 420 */       static class OnNavigationListenerWrapper implements android.app.ActionBar.OnNavigationListener {
                    private final ActionBar.OnNavigationListener mWrappedListener;

/* 421 */           public OnNavigationListenerWrapper(ActionBar.OnNavigationListener l) {
/* 423 */               this.mWrappedListener = l;
                    }

                    @Override // android.app.ActionBar.OnNavigationListener
/* 427 */           public boolean onNavigationItemSelected(int itemPosition, long itemId) {
/* 428 */               return this.mWrappedListener.onNavigationItemSelected(itemPosition, itemId);
                    }
                }

/* 436 */       static class OnMenuVisibilityListenerWrapper implements android.app.ActionBar.OnMenuVisibilityListener {
                    final ActionBar.OnMenuVisibilityListener mWrappedListener;

/* 437 */           public OnMenuVisibilityListenerWrapper(ActionBar.OnMenuVisibilityListener l) {
/* 439 */               this.mWrappedListener = l;
                    }

                    @Override // android.app.ActionBar.OnMenuVisibilityListener
/* 443 */           public void onMenuVisibilityChanged(boolean isVisible) {
/* 444 */               this.mWrappedListener.onMenuVisibilityChanged(isVisible);
                    }
                }

/* 453 */       class TabWrapper extends ActionBar.Tab implements android.app.ActionBar.TabListener {
                    private CharSequence mContentDescription;
                    private ActionBar.TabListener mTabListener;
                    private Object mTag;
                    final android.app.ActionBar.Tab mWrappedTab;

/* 454 */           public TabWrapper(android.app.ActionBar.Tab tab) {
/* 456 */               this.mWrappedTab = tab;
                    }

                    @Override // android.support.v7.app.ActionBar.Tab
/* 460 */           public int getPosition() {
/* 461 */               return this.mWrappedTab.getPosition();
                    }

                    @Override // android.support.v7.app.ActionBar.Tab
/* 465 */           public Drawable getIcon() {
/* 466 */               return this.mWrappedTab.getIcon();
                    }

                    @Override // android.support.v7.app.ActionBar.Tab
/* 470 */           public CharSequence getText() {
/* 471 */               return this.mWrappedTab.getText();
                    }

                    @Override // android.support.v7.app.ActionBar.Tab
/* 475 */           public ActionBar.Tab setIcon(Drawable icon) {
/* 476 */               this.mWrappedTab.setIcon(icon);
/* 477 */               return this;
                    }

                    @Override // android.support.v7.app.ActionBar.Tab
/* 481 */           public ActionBar.Tab setIcon(int resId) {
/* 482 */               this.mWrappedTab.setIcon(resId);
/* 483 */               return this;
                    }

                    @Override // android.support.v7.app.ActionBar.Tab
/* 487 */           public ActionBar.Tab setText(CharSequence text) {
/* 488 */               this.mWrappedTab.setText(text);
/* 489 */               return this;
                    }

                    @Override // android.support.v7.app.ActionBar.Tab
/* 493 */           public ActionBar.Tab setText(int resId) {
/* 494 */               this.mWrappedTab.setText(resId);
/* 495 */               return this;
                    }

                    @Override // android.support.v7.app.ActionBar.Tab
/* 499 */           public ActionBar.Tab setCustomView(View view) {
/* 500 */               this.mWrappedTab.setCustomView(view);
/* 501 */               return this;
                    }

                    @Override // android.support.v7.app.ActionBar.Tab
/* 505 */           public ActionBar.Tab setCustomView(int layoutResId) {
/* 506 */               this.mWrappedTab.setCustomView(layoutResId);
/* 507 */               return this;
                    }

                    @Override // android.support.v7.app.ActionBar.Tab
/* 511 */           public View getCustomView() {
/* 512 */               return this.mWrappedTab.getCustomView();
                    }

                    @Override // android.support.v7.app.ActionBar.Tab
/* 516 */           public ActionBar.Tab setTag(Object obj) {
/* 517 */               this.mTag = obj;
/* 518 */               return this;
                    }

                    @Override // android.support.v7.app.ActionBar.Tab
/* 522 */           public Object getTag() {
/* 523 */               return this.mTag;
                    }

                    @Override // android.support.v7.app.ActionBar.Tab
/* 527 */           public ActionBar.Tab setTabListener(ActionBar.TabListener listener) {
/* 528 */               this.mTabListener = listener;
/* 529 */               this.mWrappedTab.setTabListener(listener != null ? this : null);
/* 530 */               return this;
                    }

                    @Override // android.support.v7.app.ActionBar.Tab
/* 534 */           public void select() {
/* 535 */               this.mWrappedTab.select();
                    }

                    @Override // android.support.v7.app.ActionBar.Tab
/* 539 */           public ActionBar.Tab setContentDescription(int resId) {
/* 540 */               this.mContentDescription = ActionBarImplICS.this.mActivity.getText(resId);
/* 541 */               return this;
                    }

                    @Override // android.support.v7.app.ActionBar.Tab
/* 545 */           public ActionBar.Tab setContentDescription(CharSequence contentDesc) {
/* 546 */               this.mContentDescription = contentDesc;
/* 547 */               return this;
                    }

                    @Override // android.support.v7.app.ActionBar.Tab
/* 551 */           public CharSequence getContentDescription() {
/* 552 */               return this.mContentDescription;
                    }

                    @Override // android.app.ActionBar.TabListener
/* 557 */           public void onTabSelected(android.app.ActionBar.Tab tab, android.app.FragmentTransaction ft) {
/* 558 */               this.mTabListener.onTabSelected(this, ft != null ? ActionBarImplICS.this.getActiveTransaction() : null);
/* 559 */               ActionBarImplICS.this.commitActiveTransaction();
                    }

                    @Override // android.app.ActionBar.TabListener
/* 564 */           public void onTabUnselected(android.app.ActionBar.Tab tab, android.app.FragmentTransaction ft) {
/* 565 */               this.mTabListener.onTabUnselected(this, ft != null ? ActionBarImplICS.this.getActiveTransaction() : null);
                    }

                    @Override // android.app.ActionBar.TabListener
/* 570 */           public void onTabReselected(android.app.ActionBar.Tab tab, android.app.FragmentTransaction ft) {
/* 571 */               this.mTabListener.onTabReselected(this, ft != null ? ActionBarImplICS.this.getActiveTransaction() : null);
/* 572 */               ActionBarImplICS.this.commitActiveTransaction();
                    }
                }
            }
