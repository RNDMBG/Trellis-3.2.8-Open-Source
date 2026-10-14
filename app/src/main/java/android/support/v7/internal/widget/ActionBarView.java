            package android.support.v7.internal.widget;

            import android.R;
            import android.app.Activity;
            import android.content.Context;
            import android.content.pm.ApplicationInfo;
            import android.content.pm.PackageManager;
            import android.content.res.Configuration;
            import android.content.res.TypedArray;
            import android.graphics.drawable.Drawable;
            import android.os.Build;
            import android.os.Parcel;
            import android.os.Parcelable;
            import android.support.v4.internal.view.SupportMenu;
            import android.support.v4.internal.view.SupportMenuItem;
            import android.support.v7.app.ActionBar;
            import android.support.v7.appcompat.C0084R;
            import android.support.v7.internal.view.menu.ActionMenuItem;
            import android.support.v7.internal.view.menu.ActionMenuPresenter;
            import android.support.v7.internal.view.menu.ActionMenuView;
            import android.support.v7.internal.view.menu.MenuBuilder;
            import android.support.v7.internal.view.menu.MenuItemImpl;
            import android.support.v7.internal.view.menu.MenuPresenter;
            import android.support.v7.internal.view.menu.MenuView;
            import android.support.v7.internal.view.menu.SubMenuBuilder;
            import android.support.v7.view.CollapsibleActionView;
            import android.text.TextUtils;
            import android.util.AttributeSet;
            import android.util.Log;
            import android.view.LayoutInflater;
            import android.view.View;
            import android.view.ViewGroup;
            import android.view.ViewParent;
            import android.view.Window;
            import android.view.accessibility.AccessibilityEvent;
            import android.widget.FrameLayout;
            import android.widget.ImageView;
            import android.widget.LinearLayout;
            import android.widget.SpinnerAdapter;
            import android.widget.TextView;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 61 */    public class ActionBarView extends AbsActionBarView {
                private static final int DEFAULT_CUSTOM_GRAVITY = 19;
                public static final int DISPLAY_DEFAULT = 0;
                private static final int DISPLAY_RELAYOUT_MASK = 31;
                private static final String TAG = "ActionBarView";
                private ActionBar.OnNavigationListener mCallback;
                private Context mContext;
                private ActionBarContextView mContextView;
                private View mCustomNavView;
                private int mDisplayOptions;
                View mExpandedActionView;
                private final View.OnClickListener mExpandedActionViewUpListener;
                private HomeView mExpandedHomeLayout;
                private ExpandedActionViewMenuPresenter mExpandedMenuPresenter;
                private HomeView mHomeLayout;
                private Drawable mIcon;
                private boolean mIncludeTabs;
                private int mIndeterminateProgressStyle;
                private ProgressBarICS mIndeterminateProgressView;
                private boolean mIsCollapsable;
                private boolean mIsCollapsed;
                private int mItemPadding;
                private LinearLayout mListNavLayout;
                private Drawable mLogo;
                private ActionMenuItem mLogoNavItem;
                private final AdapterViewICS.OnItemSelectedListener mNavItemSelectedListener;
                private int mNavigationMode;
                private MenuBuilder mOptionsMenu;
                private int mProgressBarPadding;
                private int mProgressStyle;
                private ProgressBarICS mProgressView;
                private SpinnerICS mSpinner;
                private SpinnerAdapter mSpinnerAdapter;
                private CharSequence mSubtitle;
                private int mSubtitleStyleRes;
                private TextView mSubtitleView;
                private ScrollingTabContainerView mTabScrollView;
                private Runnable mTabSelector;
                private CharSequence mTitle;
                private LinearLayout mTitleLayout;
                private int mTitleStyleRes;
                private View mTitleUpView;
                private TextView mTitleView;
                private final View.OnClickListener mUpClickListener;
                private boolean mUserTitle;
                Window.Callback mWindowCallback;

/* 62 */        static /* synthetic */ ActionBar.OnNavigationListener access$000(ActionBarView x0) {
/* 63 */            return x0.mCallback;
                }

/* 62 */        static /* synthetic */ ExpandedActionViewMenuPresenter access$100(ActionBarView x0) {
/* 63 */            return x0.mExpandedMenuPresenter;
                }

/* 62 */        static /* synthetic */ SpinnerICS access$1000(ActionBarView x0) {
/* 63 */            return x0.mSpinner;
                }

/* 62 */        static /* synthetic */ View access$1100(ActionBarView x0) {
/* 63 */            return x0.mCustomNavView;
                }

/* 62 */        static /* synthetic */ int access$1200(ActionBarView x0) {
/* 63 */            return x0.mDisplayOptions;
                }

/* 62 */        static /* synthetic */ void access$1300(ActionBarView x0) {
/* 63 */            x0.initTitle();
                }

/* 62 */        static /* synthetic */ int access$1400(ActionBarView x0) {
/* 63 */            return x0.mNavigationMode;
                }

/* 62 */        static /* synthetic */ ActionMenuItem access$200(ActionBarView x0) {
/* 63 */            return x0.mLogoNavItem;
                }

/* 62 */        static /* synthetic */ Drawable access$500(ActionBarView x0) {
/* 63 */            return x0.mIcon;
                }

/* 62 */        static /* synthetic */ HomeView access$600(ActionBarView x0) {
/* 63 */            return x0.mExpandedHomeLayout;
                }

/* 62 */        static /* synthetic */ HomeView access$700(ActionBarView x0) {
/* 63 */            return x0.mHomeLayout;
                }

/* 62 */        static /* synthetic */ LinearLayout access$800(ActionBarView x0) {
/* 63 */            return x0.mTitleLayout;
                }

/* 62 */        static /* synthetic */ ScrollingTabContainerView access$900(ActionBarView x0) {
/* 63 */            return x0.mTabScrollView;
                }

                @Override // android.support.v7.internal.widget.AbsActionBarView
/* 62 */        public /* bridge */ /* synthetic */ void animateToVisibility(int x0) {
/* 63 */            super.animateToVisibility(x0);
                }

                @Override // android.support.v7.internal.widget.AbsActionBarView
/* 62 */        public /* bridge */ /* synthetic */ void dismissPopupMenus() {
/* 63 */            super.dismissPopupMenus();
                }

                @Override // android.support.v7.internal.widget.AbsActionBarView
/* 62 */        public /* bridge */ /* synthetic */ int getAnimatedVisibility() {
/* 63 */            return super.getAnimatedVisibility();
                }

                @Override // android.support.v7.internal.widget.AbsActionBarView
/* 62 */        public /* bridge */ /* synthetic */ int getContentHeight() {
/* 63 */            return super.getContentHeight();
                }

                @Override // android.support.v7.internal.widget.AbsActionBarView
/* 62 */        public /* bridge */ /* synthetic */ boolean hideOverflowMenu() {
/* 63 */            return super.hideOverflowMenu();
                }

                @Override // android.support.v7.internal.widget.AbsActionBarView
/* 62 */        public /* bridge */ /* synthetic */ boolean isOverflowMenuShowing() {
/* 63 */            return super.isOverflowMenuShowing();
                }

                @Override // android.support.v7.internal.widget.AbsActionBarView
/* 62 */        public /* bridge */ /* synthetic */ boolean isOverflowReserved() {
/* 63 */            return super.isOverflowReserved();
                }

                @Override // android.support.v7.internal.widget.AbsActionBarView
/* 62 */        public /* bridge */ /* synthetic */ void postShowOverflowMenu() {
/* 63 */            super.postShowOverflowMenu();
                }

                @Override // android.support.v7.internal.widget.AbsActionBarView
/* 62 */        public /* bridge */ /* synthetic */ void setContentHeight(int x0) {
/* 63 */            super.setContentHeight(x0);
                }

                @Override // android.support.v7.internal.widget.AbsActionBarView
/* 62 */        public /* bridge */ /* synthetic */ void setSplitView(ActionBarContainer x0) {
/* 63 */            super.setSplitView(x0);
                }

                @Override // android.support.v7.internal.widget.AbsActionBarView
/* 62 */        public /* bridge */ /* synthetic */ void setSplitWhenNarrow(boolean x0) {
/* 63 */            super.setSplitWhenNarrow(x0);
                }

                @Override // android.support.v7.internal.widget.AbsActionBarView, android.view.View
/* 62 */        public /* bridge */ /* synthetic */ void setVisibility(int x0) {
/* 63 */            super.setVisibility(x0);
                }

                @Override // android.support.v7.internal.widget.AbsActionBarView
/* 62 */        public /* bridge */ /* synthetic */ boolean showOverflowMenu() {
/* 63 */            return super.showOverflowMenu();
                }

                /* JADX INFO: renamed from: android.support.v7.internal.widget.ActionBarView$1 */
/* 134 */       class C00931 implements AdapterViewICS.OnItemSelectedListener {
/* 135 */           C00931() {
                    }

                    @Override // android.support.v7.internal.widget.AdapterViewICS.OnItemSelectedListener
/* 138 */           public void onItemSelected(AdapterViewICS<?> parent, View view, int position, long id) {
/* 139 */               if (ActionBarView.access$000(ActionBarView.this) != null) {
/* 140 */                   ActionBarView.access$000(ActionBarView.this).onNavigationItemSelected(position, id);
                        }
                    }

                    @Override // android.support.v7.internal.widget.AdapterViewICS.OnItemSelectedListener
/* 145 */           public void onNothingSelected(AdapterViewICS<?> parent) {
                    }
                }

                /* JADX INFO: renamed from: android.support.v7.internal.widget.ActionBarView$2 */
/* 147 */       class ViewOnClickListenerC00942 implements View.OnClickListener {
/* 148 */           ViewOnClickListenerC00942() {
                    }

                    @Override // android.view.View.OnClickListener
/* 151 */           public void onClick(View v) {
/* 152 */               MenuItemImpl item = ActionBarView.access$100(ActionBarView.this).mCurrentExpandedItem;
/* 153 */               if (item != null) {
/* 154 */                   item.collapseActionView();
                        }
                    }
                }

                /* JADX INFO: renamed from: android.support.v7.internal.widget.ActionBarView$3 */
/* 157 */       class ViewOnClickListenerC00953 implements View.OnClickListener {
/* 158 */           ViewOnClickListenerC00953() {
                    }

                    @Override // android.view.View.OnClickListener
/* 160 */           public void onClick(View v) {
/* 161 */               ActionBarView.this.mWindowCallback.onMenuItemSelected(0, ActionBarView.access$200(ActionBarView.this));
                    }
                }

/* 165 */       public ActionBarView(Context context, AttributeSet attrs) {
/* 166 */           super(context, attrs);
/* 85 */            this.mDisplayOptions = -1;
/* 135 */           this.mNavItemSelectedListener = new C00931();
/* 149 */           this.mExpandedActionViewUpListener = new ViewOnClickListenerC00942();
/* 159 */           this.mUpClickListener = new ViewOnClickListenerC00953();
/* 167 */           this.mContext = context;
/* 170 */           setBackgroundResource(0);
/* 172 */           TypedArray a = context.obtainStyledAttributes(attrs, C0084R.styleable.ActionBar, C0084R.attr.actionBarStyle, 0);
/* 175 */           ApplicationInfo appInfo = context.getApplicationInfo();
/* 176 */           PackageManager pm = context.getPackageManager();
/* 177 */           this.mNavigationMode = a.getInt(2, 0);
/* 179 */           this.mTitle = a.getText(1);
/* 180 */           this.mSubtitle = a.getText(4);
/* 181 */           this.mLogo = a.getDrawable(8);
/* 182 */           if (this.mLogo == null && Build.VERSION.SDK_INT >= 9) {
/* 184 */               if (context instanceof Activity) {
                            try {
/* 186 */                       this.mLogo = pm.getActivityLogo(((Activity) context).getComponentName());
                            } catch (PackageManager.NameNotFoundException e) {
/* 188 */                       Log.e(TAG, "Activity component name not found!", e);
                            }
                        }
/* 191 */               if (this.mLogo == null) {
/* 192 */                   this.mLogo = appInfo.loadLogo(pm);
                        }
                    }
/* 198 */           this.mIcon = a.getDrawable(7);
/* 199 */           if (this.mIcon == null) {
/* 200 */               if (context instanceof Activity) {
                            try {
/* 202 */                       this.mIcon = pm.getActivityIcon(((Activity) context).getComponentName());
                            } catch (PackageManager.NameNotFoundException e2) {
/* 204 */                       Log.e(TAG, "Activity component name not found!", e2);
                            }
                        }
/* 207 */               if (this.mIcon == null) {
/* 208 */                   this.mIcon = appInfo.loadIcon(pm);
                        }
                    }
/* 212 */           LayoutInflater inflater = LayoutInflater.from(context);
/* 214 */           int homeResId = a.getResourceId(14, C0084R.layout.abc_action_bar_home);
/* 218 */           this.mHomeLayout = (HomeView) inflater.inflate(homeResId, (ViewGroup) this, false);
/* 220 */           this.mExpandedHomeLayout = (HomeView) inflater.inflate(homeResId, (ViewGroup) this, false);
/* 221 */           this.mExpandedHomeLayout.setUp(true);
/* 222 */           this.mExpandedHomeLayout.setOnClickListener(this.mExpandedActionViewUpListener);
/* 223 */           this.mExpandedHomeLayout.setContentDescription(getResources().getText(C0084R.string.abc_action_bar_up_description));
/* 226 */           this.mTitleStyleRes = a.getResourceId(5, 0);
/* 227 */           this.mSubtitleStyleRes = a.getResourceId(6, 0);
/* 228 */           this.mProgressStyle = a.getResourceId(15, 0);
/* 229 */           this.mIndeterminateProgressStyle = a.getResourceId(16, 0);
/* 232 */           this.mProgressBarPadding = a.getDimensionPixelOffset(17, 0);
/* 234 */           this.mItemPadding = a.getDimensionPixelOffset(18, 0);
/* 236 */           setDisplayOptions(a.getInt(3, 0));
/* 238 */           int customNavId = a.getResourceId(13, 0);
/* 239 */           if (customNavId != 0) {
/* 240 */               this.mCustomNavView = inflater.inflate(customNavId, (ViewGroup) this, false);
/* 241 */               this.mNavigationMode = 0;
/* 242 */               setDisplayOptions(this.mDisplayOptions | 16);
                    }
/* 245 */           this.mContentHeight = a.getLayoutDimension(0, 0);
/* 246 */           a.recycle();
/* 247 */           this.mLogoNavItem = new ActionMenuItem(context, 0, R.id.home, 0, 0, this.mTitle);
/* 248 */           this.mHomeLayout.setOnClickListener(this.mUpClickListener);
/* 249 */           this.mHomeLayout.setClickable(true);
/* 250 */           this.mHomeLayout.setFocusable(true);
                }

                @Override // android.support.v7.internal.widget.AbsActionBarView, android.view.View
/* 255 */       protected void onConfigurationChanged(Configuration newConfig) {
/* 256 */           super.onConfigurationChanged(newConfig);
/* 258 */           this.mTitleView = null;
/* 259 */           this.mSubtitleView = null;
/* 260 */           this.mTitleUpView = null;
/* 261 */           if (this.mTitleLayout != null && this.mTitleLayout.getParent() == this) {
/* 262 */               removeView(this.mTitleLayout);
                    }
/* 264 */           this.mTitleLayout = null;
/* 265 */           if ((this.mDisplayOptions & 8) != 0) {
/* 266 */               initTitle();
                    }
/* 269 */           if (this.mTabScrollView != null && this.mIncludeTabs) {
/* 270 */               ViewGroup.LayoutParams lp = this.mTabScrollView.getLayoutParams();
/* 271 */               if (lp != null) {
/* 272 */                   lp.width = -2;
/* 273 */                   lp.height = -1;
                        }
/* 275 */               this.mTabScrollView.setAllowCollapse(true);
                    }
/* 278 */           if (this.mProgressView != null) {
/* 279 */               removeView(this.mProgressView);
/* 280 */               initProgress();
                    }
/* 282 */           if (this.mIndeterminateProgressView != null) {
/* 283 */               removeView(this.mIndeterminateProgressView);
/* 284 */               initIndeterminateProgress();
                    }
                }

/* 293 */       public void setWindowCallback(Window.Callback cb) {
/* 294 */           this.mWindowCallback = cb;
                }

                @Override // android.view.ViewGroup, android.view.View
/* 298 */       public void onDetachedFromWindow() {
/* 299 */           super.onDetachedFromWindow();
/* 300 */           removeCallbacks(this.mTabSelector);
/* 301 */           if (this.mActionMenuPresenter != null) {
/* 302 */               this.mActionMenuPresenter.hideOverflowMenu();
/* 303 */               this.mActionMenuPresenter.hideSubMenus();
                    }
                }

                @Override // android.view.ViewGroup
/* 307 */       public boolean shouldDelayChildPressedState() {
/* 308 */           return false;
                }

/* 311 */       public void initProgress() {
/* 312 */           this.mProgressView = new ProgressBarICS(this.mContext, null, 0, this.mProgressStyle);
/* 313 */           this.mProgressView.setId(C0084R.id.progress_horizontal);
/* 314 */           this.mProgressView.setMax(10000);
/* 315 */           this.mProgressView.setVisibility(8);
/* 316 */           addView(this.mProgressView);
                }

/* 319 */       public void initIndeterminateProgress() {
/* 320 */           this.mIndeterminateProgressView = new ProgressBarICS(this.mContext, null, 0, this.mIndeterminateProgressStyle);
/* 322 */           this.mIndeterminateProgressView.setId(C0084R.id.progress_circular);
/* 323 */           this.mIndeterminateProgressView.setVisibility(8);
/* 324 */           addView(this.mIndeterminateProgressView);
                }

                @Override // android.support.v7.internal.widget.AbsActionBarView
/* 328 */       public void setSplitActionBar(boolean splitActionBar) {
/* 329 */           if (this.mSplitActionBar != splitActionBar) {
/* 330 */               if (this.mMenuView != null) {
/* 331 */                   ViewGroup oldParent = (ViewGroup) this.mMenuView.getParent();
/* 332 */                   if (oldParent != null) {
/* 333 */                       oldParent.removeView(this.mMenuView);
                            }
/* 335 */                   if (splitActionBar) {
/* 336 */                       if (this.mSplitView != null) {
/* 337 */                           this.mSplitView.addView(this.mMenuView);
                                }
/* 339 */                       this.mMenuView.getLayoutParams().width = -1;
                            } else {
/* 341 */                       addView(this.mMenuView);
/* 342 */                       this.mMenuView.getLayoutParams().width = -2;
                            }
/* 344 */                   this.mMenuView.requestLayout();
                        }
/* 346 */               if (this.mSplitView != null) {
/* 347 */                   this.mSplitView.setVisibility(splitActionBar ? 0 : 8);
                        }
/* 350 */               if (this.mActionMenuPresenter != null) {
/* 351 */                   if (!splitActionBar) {
/* 352 */                       this.mActionMenuPresenter.setExpandedActionViewsExclusive(getResources().getBoolean(C0084R.bool.abc_action_bar_expanded_action_views_exclusive));
                            } else {
/* 356 */                       this.mActionMenuPresenter.setExpandedActionViewsExclusive(false);
/* 358 */                       this.mActionMenuPresenter.setWidthLimit(getContext().getResources().getDisplayMetrics().widthPixels, true);
/* 361 */                       this.mActionMenuPresenter.setItemLimit(Integer.MAX_VALUE);
                            }
                        }
/* 364 */               super.setSplitActionBar(splitActionBar);
                    }
                }

/* 368 */       public boolean isSplitActionBar() {
/* 369 */           return this.mSplitActionBar;
                }

/* 372 */       public boolean hasEmbeddedTabs() {
/* 373 */           return this.mIncludeTabs;
                }

/* 376 */       public void setEmbeddedTabView(ScrollingTabContainerView tabs) {
/* 377 */           if (this.mTabScrollView != null) {
/* 378 */               removeView(this.mTabScrollView);
                    }
/* 380 */           this.mTabScrollView = tabs;
/* 381 */           this.mIncludeTabs = tabs != null;
/* 382 */           if (this.mIncludeTabs && this.mNavigationMode == 2) {
/* 383 */               addView(this.mTabScrollView);
/* 384 */               ViewGroup.LayoutParams lp = this.mTabScrollView.getLayoutParams();
/* 385 */               lp.width = -2;
/* 386 */               lp.height = -1;
/* 387 */               tabs.setAllowCollapse(true);
                    }
                }

/* 391 */       public void setCallback(ActionBar.OnNavigationListener callback) {
/* 392 */           this.mCallback = callback;
                }

/* 395 */       public void setMenu(SupportMenu menu, MenuPresenter.Callback cb) {
                    ActionMenuView menuView;
                    ViewGroup oldParent;
/* 396 */           if (menu != this.mOptionsMenu) {
/* 400 */               if (this.mOptionsMenu != null) {
/* 401 */                   this.mOptionsMenu.removeMenuPresenter(this.mActionMenuPresenter);
/* 402 */                   this.mOptionsMenu.removeMenuPresenter(this.mExpandedMenuPresenter);
                        }
/* 405 */               MenuBuilder builder = (MenuBuilder) menu;
/* 406 */               this.mOptionsMenu = builder;
/* 407 */               if (this.mMenuView != null && (oldParent = (ViewGroup) this.mMenuView.getParent()) != null) {
/* 410 */                   oldParent.removeView(this.mMenuView);
                        }
/* 413 */               if (this.mActionMenuPresenter == null) {
/* 414 */                   this.mActionMenuPresenter = new ActionMenuPresenter(this.mContext);
/* 415 */                   this.mActionMenuPresenter.setCallback(cb);
/* 416 */                   this.mActionMenuPresenter.setId(C0084R.id.action_menu_presenter);
/* 417 */                   this.mExpandedMenuPresenter = new ExpandedActionViewMenuPresenter(this, null);
                        }
/* 421 */               ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(-2, -1);
/* 423 */               if (!this.mSplitActionBar) {
/* 424 */                   this.mActionMenuPresenter.setExpandedActionViewsExclusive(getResources().getBoolean(C0084R.bool.abc_action_bar_expanded_action_views_exclusive));
/* 427 */                   configPresenters(builder);
/* 428 */                   menuView = (ActionMenuView) this.mActionMenuPresenter.getMenuView(this);
/* 429 */                   menuView.initialize(builder);
/* 430 */                   ViewGroup oldParent2 = (ViewGroup) menuView.getParent();
/* 431 */                   if (oldParent2 != null && oldParent2 != this) {
/* 432 */                       oldParent2.removeView(menuView);
                            }
/* 434 */                   addView(menuView, layoutParams);
                        } else {
/* 436 */                   this.mActionMenuPresenter.setExpandedActionViewsExclusive(false);
/* 438 */                   this.mActionMenuPresenter.setWidthLimit(getContext().getResources().getDisplayMetrics().widthPixels, true);
/* 441 */                   this.mActionMenuPresenter.setItemLimit(Integer.MAX_VALUE);
/* 443 */                   layoutParams.width = -1;
/* 444 */                   configPresenters(builder);
/* 445 */                   menuView = (ActionMenuView) this.mActionMenuPresenter.getMenuView(this);
/* 446 */                   if (this.mSplitView != null) {
/* 447 */                       ViewGroup oldParent3 = (ViewGroup) menuView.getParent();
/* 448 */                       if (oldParent3 != null && oldParent3 != this.mSplitView) {
/* 449 */                           oldParent3.removeView(menuView);
                                }
/* 451 */                       menuView.setVisibility(getAnimatedVisibility());
/* 452 */                       this.mSplitView.addView(menuView, layoutParams);
                            } else {
/* 455 */                       menuView.setLayoutParams(layoutParams);
                            }
                        }
/* 458 */               this.mMenuView = menuView;
                    }
                }

/* 461 */       private void configPresenters(MenuBuilder builder) {
/* 462 */           if (builder != null) {
/* 463 */               builder.addMenuPresenter(this.mActionMenuPresenter);
/* 464 */               builder.addMenuPresenter(this.mExpandedMenuPresenter);
                    } else {
/* 466 */               this.mActionMenuPresenter.initForMenu(this.mContext, null);
/* 467 */               this.mExpandedMenuPresenter.initForMenu(this.mContext, null);
                    }
/* 471 */           this.mActionMenuPresenter.updateMenuView(true);
/* 472 */           this.mExpandedMenuPresenter.updateMenuView(true);
                }

/* 475 */       public boolean hasExpandedActionView() {
                    return (this.mExpandedMenuPresenter == null || this.mExpandedMenuPresenter.mCurrentExpandedItem == null) ? false : true;
                }

/* 480 */       public void collapseActionView() {
/* 481 */           MenuItemImpl item = this.mExpandedMenuPresenter == null ? null : this.mExpandedMenuPresenter.mCurrentExpandedItem;
/* 483 */           if (item != null) {
/* 484 */               item.collapseActionView();
                    }
                }

/* 488 */       public void setCustomNavigationView(View view) {
/* 489 */           boolean showCustom = (this.mDisplayOptions & 16) != 0;
/* 490 */           if (this.mCustomNavView != null && showCustom) {
/* 491 */               removeView(this.mCustomNavView);
                    }
/* 493 */           this.mCustomNavView = view;
/* 494 */           if (this.mCustomNavView != null && showCustom) {
/* 495 */               addView(this.mCustomNavView);
                    }
                }

/* 499 */       public CharSequence getTitle() {
/* 500 */           return this.mTitle;
                }

/* 509 */       public void setTitle(CharSequence title) {
/* 510 */           this.mUserTitle = true;
/* 511 */           setTitleImpl(title);
                }

/* 520 */       public void setWindowTitle(CharSequence title) {
/* 521 */           if (!this.mUserTitle) {
/* 522 */               setTitleImpl(title);
                    }
                }

/* 526 */       private void setTitleImpl(CharSequence title) {
/* 527 */           this.mTitle = title;
/* 528 */           if (this.mTitleView != null) {
/* 529 */               this.mTitleView.setText(title);
/* 530 */               boolean visible = (this.mExpandedActionView != null || (this.mDisplayOptions & 8) == 0 || (TextUtils.isEmpty(this.mTitle) && TextUtils.isEmpty(this.mSubtitle))) ? false : true;
/* 533 */               this.mTitleLayout.setVisibility(visible ? 0 : 8);
                    }
/* 535 */           if (this.mLogoNavItem != null) {
/* 536 */               this.mLogoNavItem.setTitle(title);
                    }
                }

/* 540 */       public CharSequence getSubtitle() {
/* 541 */           return this.mSubtitle;
                }

/* 544 */       public void setSubtitle(CharSequence subtitle) {
/* 545 */           this.mSubtitle = subtitle;
/* 546 */           if (this.mSubtitleView != null) {
/* 547 */               this.mSubtitleView.setText(subtitle);
/* 548 */               this.mSubtitleView.setVisibility(subtitle != null ? 0 : 8);
/* 549 */               boolean visible = (this.mExpandedActionView != null || (this.mDisplayOptions & 8) == 0 || (TextUtils.isEmpty(this.mTitle) && TextUtils.isEmpty(this.mSubtitle))) ? false : true;
/* 552 */               this.mTitleLayout.setVisibility(visible ? 0 : 8);
                    }
                }

/* 556 */       public void setHomeButtonEnabled(boolean enable) {
/* 557 */           this.mHomeLayout.setEnabled(enable);
/* 558 */           this.mHomeLayout.setFocusable(enable);
/* 560 */           if (!enable) {
/* 561 */               this.mHomeLayout.setContentDescription(null);
                    } else if ((this.mDisplayOptions & 4) != 0) {
/* 563 */               this.mHomeLayout.setContentDescription(this.mContext.getResources().getText(C0084R.string.abc_action_bar_up_description));
                    } else {
/* 566 */               this.mHomeLayout.setContentDescription(this.mContext.getResources().getText(C0084R.string.abc_action_bar_home_description));
                    }
                }

/* 571 */       public void setDisplayOptions(int options) {
                    int i = 8;
/* 572 */           int flagsChanged = this.mDisplayOptions != -1 ? options ^ this.mDisplayOptions : -1;
/* 573 */           this.mDisplayOptions = options;
/* 575 */           if ((flagsChanged & 31) != 0) {
/* 576 */               boolean showHome = (options & 2) != 0;
/* 577 */               int vis = (showHome && this.mExpandedActionView == null) ? 0 : 8;
/* 578 */               this.mHomeLayout.setVisibility(vis);
/* 580 */               if ((flagsChanged & 4) != 0) {
/* 581 */                   boolean setUp = (options & 4) != 0;
/* 582 */                   this.mHomeLayout.setUp(setUp);
/* 588 */                   if (setUp) {
/* 589 */                       setHomeButtonEnabled(true);
                            }
                        }
/* 593 */               if ((flagsChanged & 1) != 0) {
/* 594 */                   boolean logoVis = (this.mLogo == null || (options & 1) == 0) ? false : true;
/* 596 */                   this.mHomeLayout.setIcon(logoVis ? this.mLogo : this.mIcon);
                        }
/* 599 */               if ((flagsChanged & 8) != 0) {
/* 600 */                   if ((options & 8) != 0) {
/* 601 */                       initTitle();
                            } else {
/* 603 */                       removeView(this.mTitleLayout);
                            }
                        }
/* 607 */               if (this.mTitleLayout != null && (flagsChanged & 6) != 0) {
/* 609 */                   boolean homeAsUp = (this.mDisplayOptions & 4) != 0;
/* 610 */                   View view = this.mTitleUpView;
                            if (!showHome) {
/* 610 */                       i = homeAsUp ? 0 : 4;
                            }
                            view.setVisibility(i);
/* 611 */                   this.mTitleLayout.setEnabled(!showHome && homeAsUp);
                        }
/* 614 */               if ((flagsChanged & 16) != 0 && this.mCustomNavView != null) {
/* 615 */                   if ((options & 16) != 0) {
/* 616 */                       addView(this.mCustomNavView);
                            } else {
/* 618 */                       removeView(this.mCustomNavView);
                            }
                        }
/* 622 */               requestLayout();
                    } else {
/* 624 */               invalidate();
                    }
/* 628 */           if (!this.mHomeLayout.isEnabled()) {
/* 629 */               this.mHomeLayout.setContentDescription(null);
                    } else if ((options & 4) != 0) {
/* 631 */               this.mHomeLayout.setContentDescription(this.mContext.getResources().getText(C0084R.string.abc_action_bar_up_description));
                    } else {
/* 634 */               this.mHomeLayout.setContentDescription(this.mContext.getResources().getText(C0084R.string.abc_action_bar_home_description));
                    }
                }

/* 639 */       public void setIcon(Drawable icon) {
/* 640 */           this.mIcon = icon;
/* 641 */           if (icon != null && ((this.mDisplayOptions & 1) == 0 || this.mLogo == null)) {
/* 643 */               this.mHomeLayout.setIcon(icon);
                    }
/* 645 */           if (this.mExpandedActionView != null) {
/* 646 */               this.mExpandedHomeLayout.setIcon(this.mIcon.getConstantState().newDrawable(getResources()));
                    }
                }

/* 650 */       public void setIcon(int resId) {
/* 651 */           setIcon(this.mContext.getResources().getDrawable(resId));
                }

/* 654 */       public void setLogo(Drawable logo) {
/* 655 */           this.mLogo = logo;
/* 656 */           if (logo != null && (this.mDisplayOptions & 1) != 0) {
/* 657 */               this.mHomeLayout.setIcon(logo);
                    }
                }

/* 661 */       public void setLogo(int resId) {
/* 662 */           setLogo(this.mContext.getResources().getDrawable(resId));
                }

/* 665 */       public void setNavigationMode(int mode) {
/* 666 */           int oldMode = this.mNavigationMode;
/* 667 */           if (mode != oldMode) {
                        switch (oldMode) {
                            case 1:
/* 670 */                       if (this.mListNavLayout != null) {
/* 671 */                           removeView(this.mListNavLayout);
                                }
                                break;
                            case 2:
/* 675 */                       if (this.mTabScrollView != null && this.mIncludeTabs) {
/* 676 */                           removeView(this.mTabScrollView);
                                }
                                break;
                        }
                        switch (mode) {
                            case 1:
/* 682 */                       if (this.mSpinner == null) {
/* 683 */                           this.mSpinner = new SpinnerICS(this.mContext, null, C0084R.attr.actionDropDownStyle);
/* 685 */                           this.mListNavLayout = (LinearLayout) LayoutInflater.from(this.mContext).inflate(C0084R.layout.abc_action_bar_view_list_nav_layout, (ViewGroup) null);
/* 687 */                           LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(-2, -1);
/* 689 */                           params.gravity = 17;
/* 690 */                           this.mListNavLayout.addView(this.mSpinner, params);
                                }
/* 692 */                       if (this.mSpinner.getAdapter() != this.mSpinnerAdapter) {
/* 693 */                           this.mSpinner.setAdapter(this.mSpinnerAdapter);
                                }
/* 695 */                       this.mSpinner.setOnItemSelectedListener(this.mNavItemSelectedListener);
/* 696 */                       addView(this.mListNavLayout);
                                break;
                            case 2:
/* 699 */                       if (this.mTabScrollView != null && this.mIncludeTabs) {
/* 700 */                           addView(this.mTabScrollView);
                                }
                                break;
                        }
/* 704 */               this.mNavigationMode = mode;
/* 705 */               requestLayout();
                    }
                }

/* 709 */       public void setDropdownAdapter(SpinnerAdapter adapter) {
/* 710 */           this.mSpinnerAdapter = adapter;
/* 711 */           if (this.mSpinner != null) {
/* 712 */               this.mSpinner.setAdapter(adapter);
                    }
                }

/* 716 */       public SpinnerAdapter getDropdownAdapter() {
/* 717 */           return this.mSpinnerAdapter;
                }

/* 720 */       public void setDropdownSelectedPosition(int position) {
/* 721 */           this.mSpinner.setSelection(position);
                }

/* 724 */       public int getDropdownSelectedPosition() {
/* 725 */           return this.mSpinner.getSelectedItemPosition();
                }

/* 728 */       public View getCustomNavigationView() {
/* 729 */           return this.mCustomNavView;
                }

/* 732 */       public int getNavigationMode() {
/* 733 */           return this.mNavigationMode;
                }

/* 736 */       public int getDisplayOptions() {
/* 737 */           return this.mDisplayOptions;
                }

                @Override // android.view.ViewGroup
/* 743 */       protected ViewGroup.LayoutParams generateDefaultLayoutParams() {
/* 744 */           return new ActionBar.LayoutParams(19);
                }

                @Override // android.view.View
/* 748 */       protected void onFinishInflate() {
                    ViewParent parent;
/* 749 */           super.onFinishInflate();
/* 751 */           addView(this.mHomeLayout);
/* 753 */           if (this.mCustomNavView != null && (this.mDisplayOptions & 16) != 0 && (parent = this.mCustomNavView.getParent()) != this) {
/* 756 */               if (parent instanceof ViewGroup) {
/* 757 */                   ((ViewGroup) parent).removeView(this.mCustomNavView);
                        }
/* 759 */               addView(this.mCustomNavView);
                    }
                }

/* 764 */       private void initTitle() {
                    int i;
/* 765 */           if (this.mTitleLayout == null) {
/* 766 */               LayoutInflater inflater = LayoutInflater.from(getContext());
/* 767 */               this.mTitleLayout = (LinearLayout) inflater.inflate(C0084R.layout.abc_action_bar_title_item, (ViewGroup) this, false);
/* 769 */               this.mTitleView = (TextView) this.mTitleLayout.findViewById(C0084R.id.action_bar_title);
/* 770 */               this.mSubtitleView = (TextView) this.mTitleLayout.findViewById(C0084R.id.action_bar_subtitle);
/* 771 */               this.mTitleUpView = this.mTitleLayout.findViewById(C0084R.id.f1up);
/* 773 */               this.mTitleLayout.setOnClickListener(this.mUpClickListener);
/* 775 */               if (this.mTitleStyleRes != 0) {
/* 776 */                   this.mTitleView.setTextAppearance(this.mContext, this.mTitleStyleRes);
                        }
/* 778 */               if (this.mTitle != null) {
/* 779 */                   this.mTitleView.setText(this.mTitle);
                        }
/* 782 */               if (this.mSubtitleStyleRes != 0) {
/* 783 */                   this.mSubtitleView.setTextAppearance(this.mContext, this.mSubtitleStyleRes);
                        }
/* 785 */               if (this.mSubtitle != null) {
/* 786 */                   this.mSubtitleView.setText(this.mSubtitle);
/* 787 */                   this.mSubtitleView.setVisibility(0);
                        }
/* 790 */               boolean homeAsUp = (this.mDisplayOptions & 4) != 0;
/* 791 */               boolean showHome = (this.mDisplayOptions & 2) != 0;
/* 792 */               View view = this.mTitleUpView;
                        if (showHome) {
                            i = 8;
                        } else {
/* 792 */                   i = homeAsUp ? 0 : 4;
                        }
                        view.setVisibility(i);
/* 793 */               this.mTitleLayout.setEnabled(homeAsUp && !showHome);
                    }
/* 796 */           addView(this.mTitleLayout);
/* 797 */           if (this.mExpandedActionView != null || (TextUtils.isEmpty(this.mTitle) && TextUtils.isEmpty(this.mSubtitle))) {
/* 800 */               this.mTitleLayout.setVisibility(8);
                    }
                }

/* 804 */       public void setContextView(ActionBarContextView view) {
/* 805 */           this.mContextView = view;
                }

/* 808 */       public void setCollapsable(boolean collapsable) {
/* 809 */           this.mIsCollapsable = collapsable;
                }

/* 812 */       public boolean isCollapsed() {
/* 813 */           return this.mIsCollapsed;
                }

                @Override // android.view.View
/* 817 */       protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
                    int homeWidthSpec;
/* 818 */           int childCount = getChildCount();
/* 819 */           if (this.mIsCollapsable) {
/* 820 */               int visibleChildren = 0;
                        for (int i = 0; i < childCount; i++) {
/* 822 */                   View child = getChildAt(i);
/* 823 */                   if (child.getVisibility() != 8 && (child != this.mMenuView || this.mMenuView.getChildCount() != 0)) {
/* 825 */                       visibleChildren++;
                            }
                        }
/* 829 */               if (visibleChildren == 0) {
/* 831 */                   setMeasuredDimension(0, 0);
/* 832 */                   this.mIsCollapsed = true;
/* 1011 */                  return;
                        }
                    }
/* 836 */           this.mIsCollapsed = false;
/* 838 */           int widthMode = View.MeasureSpec.getMode(widthMeasureSpec);
/* 839 */           if (widthMode != 1073741824) {
/* 840 */               throw new IllegalStateException(getClass().getSimpleName() + " can only be used with android:layout_width=\"MATCH_PARENT\" (or fill_parent)");
                    }
/* 844 */           int heightMode = View.MeasureSpec.getMode(heightMeasureSpec);
/* 845 */           if (heightMode != Integer.MIN_VALUE) {
/* 846 */               throw new IllegalStateException(getClass().getSimpleName() + " can only be used with android:layout_height=\"wrap_content\"");
                    }
/* 850 */           int contentWidth = View.MeasureSpec.getSize(widthMeasureSpec);
/* 852 */           int maxHeight = this.mContentHeight > 0 ? this.mContentHeight : View.MeasureSpec.getSize(heightMeasureSpec);
/* 855 */           int verticalPadding = getPaddingTop() + getPaddingBottom();
/* 856 */           int paddingLeft = getPaddingLeft();
/* 857 */           int paddingRight = getPaddingRight();
/* 858 */           int height = maxHeight - verticalPadding;
/* 859 */           int childSpecHeight = View.MeasureSpec.makeMeasureSpec(height, Integer.MIN_VALUE);
/* 861 */           int availableWidth = (contentWidth - paddingLeft) - paddingRight;
/* 862 */           int leftOfCenter = availableWidth / 2;
/* 863 */           int rightOfCenter = leftOfCenter;
/* 865 */           HomeView homeLayout = this.mExpandedActionView != null ? this.mExpandedHomeLayout : this.mHomeLayout;
/* 867 */           if (homeLayout.getVisibility() != 8) {
/* 868 */               ViewGroup.LayoutParams lp = homeLayout.getLayoutParams();
/* 870 */               if (lp.width < 0) {
/* 871 */                   homeWidthSpec = View.MeasureSpec.makeMeasureSpec(availableWidth, Integer.MIN_VALUE);
                        } else {
/* 873 */                   homeWidthSpec = View.MeasureSpec.makeMeasureSpec(lp.width, 1073741824);
                        }
/* 875 */               homeLayout.measure(homeWidthSpec, View.MeasureSpec.makeMeasureSpec(height, 1073741824));
/* 877 */               int homeWidth = homeLayout.getMeasuredWidth() + homeLayout.getLeftOffset();
/* 878 */               availableWidth = Math.max(0, availableWidth - homeWidth);
/* 879 */               leftOfCenter = Math.max(0, availableWidth - homeWidth);
                    }
/* 882 */           if (this.mMenuView != null && this.mMenuView.getParent() == this) {
/* 883 */               availableWidth = measureChildView(this.mMenuView, availableWidth, childSpecHeight, 0);
/* 885 */               rightOfCenter = Math.max(0, rightOfCenter - this.mMenuView.getMeasuredWidth());
                    }
/* 888 */           if (this.mIndeterminateProgressView != null && this.mIndeterminateProgressView.getVisibility() != 8) {
/* 890 */               availableWidth = measureChildView(this.mIndeterminateProgressView, availableWidth, childSpecHeight, 0);
/* 892 */               rightOfCenter = Math.max(0, rightOfCenter - this.mIndeterminateProgressView.getMeasuredWidth());
                    }
/* 896 */           boolean showTitle = (this.mTitleLayout == null || this.mTitleLayout.getVisibility() == 8 || (this.mDisplayOptions & 8) == 0) ? false : true;
/* 899 */           if (this.mExpandedActionView == null) {
                        switch (this.mNavigationMode) {
                            case 1:
/* 902 */                       if (this.mListNavLayout != null) {
/* 903 */                           int itemPaddingSize = showTitle ? this.mItemPadding * 2 : this.mItemPadding;
/* 904 */                           int availableWidth2 = Math.max(0, availableWidth - itemPaddingSize);
/* 905 */                           int leftOfCenter2 = Math.max(0, leftOfCenter - itemPaddingSize);
/* 906 */                           this.mListNavLayout.measure(View.MeasureSpec.makeMeasureSpec(availableWidth2, Integer.MIN_VALUE), View.MeasureSpec.makeMeasureSpec(height, 1073741824));
/* 909 */                           int listNavWidth = this.mListNavLayout.getMeasuredWidth();
/* 910 */                           availableWidth = Math.max(0, availableWidth2 - listNavWidth);
/* 911 */                           leftOfCenter = Math.max(0, leftOfCenter2 - listNavWidth);
                                }
                                break;
                            case 2:
/* 915 */                       if (this.mTabScrollView != null) {
/* 916 */                           int itemPaddingSize2 = showTitle ? this.mItemPadding * 2 : this.mItemPadding;
/* 917 */                           int availableWidth3 = Math.max(0, availableWidth - itemPaddingSize2);
/* 918 */                           int leftOfCenter3 = Math.max(0, leftOfCenter - itemPaddingSize2);
/* 919 */                           this.mTabScrollView.measure(View.MeasureSpec.makeMeasureSpec(availableWidth3, Integer.MIN_VALUE), View.MeasureSpec.makeMeasureSpec(height, 1073741824));
/* 922 */                           int tabWidth = this.mTabScrollView.getMeasuredWidth();
/* 923 */                           availableWidth = Math.max(0, availableWidth3 - tabWidth);
/* 924 */                           leftOfCenter = Math.max(0, leftOfCenter3 - tabWidth);
                                }
                                break;
                        }
                    }
/* 930 */           View customView = null;
/* 931 */           if (this.mExpandedActionView != null) {
/* 932 */               customView = this.mExpandedActionView;
                    } else if ((this.mDisplayOptions & 16) != 0 && this.mCustomNavView != null) {
/* 935 */               customView = this.mCustomNavView;
                    }
/* 938 */           if (customView != null) {
/* 939 */               ViewGroup.LayoutParams lp2 = generateLayoutParams(customView.getLayoutParams());
/* 940 */               ActionBar.LayoutParams ablp = lp2 instanceof ActionBar.LayoutParams ? (ActionBar.LayoutParams) lp2 : null;
/* 943 */               int horizontalMargin = 0;
/* 944 */               int verticalMargin = 0;
/* 945 */               if (ablp != null) {
/* 946 */                   horizontalMargin = ablp.leftMargin + ablp.rightMargin;
/* 947 */                   verticalMargin = ablp.topMargin + ablp.bottomMargin;
                        }
/* 953 */               int customNavHeightMode = (this.mContentHeight > 0 && lp2.height != -2) ? 1073741824 : Integer.MIN_VALUE;
                        if (lp2.height >= 0) {
                            height = Math.min(lp2.height, height);
                        }
/* 959 */               int customNavHeight = Math.max(0, height - verticalMargin);
/* 962 */               int customNavWidthMode = lp2.width != -2 ? 1073741824 : Integer.MIN_VALUE;
/* 964 */               int customNavWidth = Math.max(0, (lp2.width >= 0 ? Math.min(lp2.width, availableWidth) : availableWidth) - horizontalMargin);
/* 967 */               int hgrav = (ablp != null ? ablp.gravity : 19) & 7;
/* 972 */               if (hgrav == 1 && lp2.width == -1) {
/* 973 */                   customNavWidth = Math.min(leftOfCenter, rightOfCenter) * 2;
                        }
/* 976 */               customView.measure(View.MeasureSpec.makeMeasureSpec(customNavWidth, customNavWidthMode), View.MeasureSpec.makeMeasureSpec(customNavHeight, customNavHeightMode));
/* 979 */               availableWidth -= customView.getMeasuredWidth() + horizontalMargin;
                    }
/* 982 */           if (this.mExpandedActionView == null && showTitle) {
/* 983 */               measureChildView(this.mTitleLayout, availableWidth, View.MeasureSpec.makeMeasureSpec(this.mContentHeight, 1073741824), 0);
/* 985 */               Math.max(0, leftOfCenter - this.mTitleLayout.getMeasuredWidth());
                    }
/* 988 */           if (this.mContentHeight <= 0) {
/* 989 */               int measuredHeight = 0;
                        for (int i2 = 0; i2 < childCount; i2++) {
/* 991 */                   View v = getChildAt(i2);
/* 992 */                   int paddedViewHeight = v.getMeasuredHeight() + verticalPadding;
/* 993 */                   if (paddedViewHeight > measuredHeight) {
/* 994 */                       measuredHeight = paddedViewHeight;
                            }
                        }
/* 997 */               setMeasuredDimension(contentWidth, measuredHeight);
                    } else {
/* 999 */               setMeasuredDimension(contentWidth, maxHeight);
                    }
/* 1002 */          if (this.mContextView != null) {
/* 1003 */              this.mContextView.setContentHeight(getMeasuredHeight());
                    }
/* 1006 */          if (this.mProgressView != null && this.mProgressView.getVisibility() != 8) {
/* 1007 */              this.mProgressView.measure(View.MeasureSpec.makeMeasureSpec(contentWidth - (this.mProgressBarPadding * 2), 1073741824), View.MeasureSpec.makeMeasureSpec(getMeasuredHeight(), Integer.MIN_VALUE));
                    }
                }

                @Override // android.view.ViewGroup, android.view.View
/* 1014 */      protected void onLayout(boolean changed, int l, int t, int r, int b) {
/* 1015 */          int x = getPaddingLeft();
/* 1016 */          int y = getPaddingTop();
/* 1017 */          int contentHeight = ((b - t) - getPaddingTop()) - getPaddingBottom();
/* 1019 */          if (contentHeight > 0) {
/* 1024 */              HomeView homeLayout = this.mExpandedActionView != null ? this.mExpandedHomeLayout : this.mHomeLayout;
/* 1025 */              if (homeLayout.getVisibility() != 8) {
/* 1026 */                  int leftOffset = homeLayout.getLeftOffset();
/* 1027 */                  x += positionChild(homeLayout, x + leftOffset, y, contentHeight) + leftOffset;
                        }
/* 1030 */              if (this.mExpandedActionView == null) {
/* 1031 */                  boolean showTitle = (this.mTitleLayout == null || this.mTitleLayout.getVisibility() == 8 || (this.mDisplayOptions & 8) == 0) ? false : true;
/* 1034 */                  if (showTitle) {
/* 1035 */                      x += positionChild(this.mTitleLayout, x, y, contentHeight);
                            }
                            switch (this.mNavigationMode) {
                                case 1:
/* 1042 */                          if (this.mListNavLayout != null) {
/* 1043 */                              if (showTitle) {
/* 1044 */                                  x += this.mItemPadding;
                                        }
/* 1046 */                              x += positionChild(this.mListNavLayout, x, y, contentHeight) + this.mItemPadding;
                                    }
                                    break;
                                case 2:
/* 1050 */                          if (this.mTabScrollView != null) {
/* 1051 */                              if (showTitle) {
/* 1052 */                                  x += this.mItemPadding;
                                        }
/* 1054 */                              x += positionChild(this.mTabScrollView, x, y, contentHeight) + this.mItemPadding;
                                    }
                                    break;
                            }
                        }
/* 1060 */              int menuLeft = (r - l) - getPaddingRight();
/* 1061 */              if (this.mMenuView != null && this.mMenuView.getParent() == this) {
/* 1062 */                  positionChildInverse(this.mMenuView, menuLeft, y, contentHeight);
/* 1063 */                  menuLeft -= this.mMenuView.getMeasuredWidth();
                        }
/* 1066 */              if (this.mIndeterminateProgressView != null && this.mIndeterminateProgressView.getVisibility() != 8) {
/* 1068 */                  positionChildInverse(this.mIndeterminateProgressView, menuLeft, y, contentHeight);
/* 1069 */                  menuLeft -= this.mIndeterminateProgressView.getMeasuredWidth();
                        }
/* 1072 */              View customView = null;
/* 1073 */              if (this.mExpandedActionView != null) {
/* 1074 */                  customView = this.mExpandedActionView;
                        } else if ((this.mDisplayOptions & 16) != 0 && this.mCustomNavView != null) {
/* 1077 */                  customView = this.mCustomNavView;
                        }
/* 1079 */              if (customView != null) {
/* 1080 */                  ViewGroup.LayoutParams lp = customView.getLayoutParams();
/* 1081 */                  ActionBar.LayoutParams ablp = lp instanceof ActionBar.LayoutParams ? (ActionBar.LayoutParams) lp : null;
/* 1084 */                  int gravity = ablp != null ? ablp.gravity : 19;
/* 1085 */                  int navWidth = customView.getMeasuredWidth();
/* 1087 */                  int topMargin = 0;
/* 1088 */                  int bottomMargin = 0;
/* 1089 */                  if (ablp != null) {
/* 1090 */                      x += ablp.leftMargin;
/* 1091 */                      menuLeft -= ablp.rightMargin;
/* 1092 */                      topMargin = ablp.topMargin;
/* 1093 */                      bottomMargin = ablp.bottomMargin;
                            }
/* 1096 */                  int hgravity = gravity & 7;
/* 1098 */                  if (hgravity == 1) {
/* 1099 */                      int centeredLeft = (getWidth() - navWidth) / 2;
/* 1100 */                      if (centeredLeft < x) {
/* 1101 */                          hgravity = 3;
                                } else if (centeredLeft + navWidth > menuLeft) {
/* 1103 */                          hgravity = 5;
                                }
                            } else if (gravity == -1) {
/* 1106 */                      hgravity = 3;
                            }
/* 1109 */                  int xpos = 0;
                            switch (hgravity) {
                                case 1:
/* 1112 */                          xpos = (getWidth() - navWidth) / 2;
                                    break;
                                case 3:
/* 1115 */                          xpos = x;
                                    break;
                                case 5:
/* 1118 */                          xpos = menuLeft - navWidth;
                                    break;
                            }
/* 1122 */                  int vgravity = gravity & 112;
/* 1124 */                  if (gravity == -1) {
/* 1125 */                      vgravity = 16;
                            }
/* 1128 */                  int ypos = 0;
                            switch (vgravity) {
                                case 16:
/* 1131 */                          int paddedTop = getPaddingTop();
/* 1132 */                          int paddedBottom = getHeight() - getPaddingBottom();
/* 1133 */                          ypos = ((paddedBottom - paddedTop) - customView.getMeasuredHeight()) / 2;
                                    break;
                                case 48:
/* 1136 */                          ypos = getPaddingTop() + topMargin;
                                    break;
                                case 80:
/* 1139 */                          ypos = ((getHeight() - getPaddingBottom()) - customView.getMeasuredHeight()) - bottomMargin;
                                    break;
                            }
/* 1143 */                  int customWidth = customView.getMeasuredWidth();
/* 1144 */                  customView.layout(xpos, ypos, xpos + customWidth, customView.getMeasuredHeight() + ypos);
/* 1146 */                  int i = x + customWidth;
                        }
/* 1149 */              if (this.mProgressView != null) {
/* 1150 */                  this.mProgressView.bringToFront();
/* 1151 */                  int halfProgressHeight = this.mProgressView.getMeasuredHeight() / 2;
/* 1152 */                  this.mProgressView.layout(this.mProgressBarPadding, -halfProgressHeight, this.mProgressBarPadding + this.mProgressView.getMeasuredWidth(), halfProgressHeight);
                        }
                    }
                }

                @Override // android.view.ViewGroup
/* 1158 */      public ViewGroup.LayoutParams generateLayoutParams(AttributeSet attrs) {
/* 1159 */          return new ActionBar.LayoutParams(getContext(), attrs);
                }

                @Override // android.view.ViewGroup
/* 1163 */      public ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams lp) {
/* 1164 */          if (lp == null) {
/* 1165 */              return generateDefaultLayoutParams();
                    }
/* 1167 */          return lp;
                }

                @Override // android.view.View
/* 1171 */      public Parcelable onSaveInstanceState() {
/* 1172 */          Parcelable superState = super.onSaveInstanceState();
/* 1173 */          SavedState state = new SavedState(superState);
/* 1175 */          if (this.mExpandedMenuPresenter != null && this.mExpandedMenuPresenter.mCurrentExpandedItem != null) {
/* 1176 */              state.expandedMenuItemId = this.mExpandedMenuPresenter.mCurrentExpandedItem.getItemId();
                    }
/* 1179 */          state.isOverflowOpen = isOverflowMenuShowing();
/* 1181 */          return state;
                }

                @Override // android.view.View
/* 1185 */      public void onRestoreInstanceState(Parcelable p) {
                    SupportMenuItem item;
/* 1186 */          SavedState state = (SavedState) p;
/* 1188 */          super.onRestoreInstanceState(state.getSuperState());
/* 1190 */          if (state.expandedMenuItemId != 0 && this.mExpandedMenuPresenter != null && this.mOptionsMenu != null && (item = (SupportMenuItem) this.mOptionsMenu.findItem(state.expandedMenuItemId)) != null) {
/* 1195 */              item.expandActionView();
                    }
/* 1199 */          if (state.isOverflowOpen) {
/* 1200 */              postShowOverflowMenu();
                    }
                }

/* 1204 */      public void setHomeAsUpIndicator(Drawable indicator) {
/* 1205 */          this.mHomeLayout.setUpIndicator(indicator);
                }

/* 1208 */      public void setHomeAsUpIndicator(int resId) {
/* 1209 */          this.mHomeLayout.setUpIndicator(resId);
                }

/* 1210 */      static class SavedState extends View.BaseSavedState {
                    public static final Parcelable.Creator<SavedState> CREATOR = new C00961();
                    int expandedMenuItemId;
                    boolean isOverflowOpen;

/* 1211 */          /* synthetic */ SavedState(Parcel x0, C00931 x1) {
/* 1212 */              this(x0);
                    }

/* 1217 */          SavedState(Parcelable superState) {
/* 1218 */              super(superState);
                    }

/* 1221 */          private SavedState(Parcel in) {
/* 1222 */              super(in);
/* 1223 */              this.expandedMenuItemId = in.readInt();
/* 1224 */              this.isOverflowOpen = in.readInt() != 0;
                    }

                    @Override // android.view.View.BaseSavedState, android.view.AbsSavedState, android.os.Parcelable
/* 1228 */          public void writeToParcel(Parcel out, int flags) {
/* 1229 */              super.writeToParcel(out, flags);
/* 1230 */              out.writeInt(this.expandedMenuItemId);
/* 1231 */              out.writeInt(this.isOverflowOpen ? 1 : 0);
                    }

                    /* JADX INFO: renamed from: android.support.v7.internal.widget.ActionBarView$SavedState$1 */
/* 1233 */          static class C00961 implements Parcelable.Creator<SavedState> {
/* 1234 */              C00961() {
                        }

                        @Override // android.os.Parcelable.Creator
/* 1234 */              public /* bridge */ /* synthetic */ SavedState createFromParcel(Parcel x0) {
/* 1235 */                  return createFromParcel(x0);
                        }

                        @Override // android.os.Parcelable.Creator
/* 1234 */              public /* bridge */ /* synthetic */ SavedState[] newArray(int x0) {
/* 1235 */                  return newArray(x0);
                        }

                        /* JADX WARN: Can't rename method to resolve collision */
                        @Override // android.os.Parcelable.Creator
/* 1236 */              public SavedState createFromParcel(Parcel in) {
/* 1237 */                  return new SavedState(in, null);
                        }

                        /* JADX WARN: Can't rename method to resolve collision */
                        @Override // android.os.Parcelable.Creator
/* 1240 */              public SavedState[] newArray(int size) {
/* 1241 */                  return new SavedState[size];
                        }
                    }
                }

/* 1252 */      private static class HomeView extends FrameLayout {
                    private Drawable mDefaultUpIndicator;
                    private ImageView mIconView;
                    private int mUpIndicatorRes;
                    private ImageView mUpView;
                    private int mUpWidth;

/* 1253 */          public HomeView(Context context) {
/* 1254 */              this(context, null);
                    }

/* 1257 */          public HomeView(Context context, AttributeSet attrs) {
/* 1258 */              super(context, attrs);
                    }

/* 1261 */          public void setUp(boolean isUp) {
/* 1262 */              this.mUpView.setVisibility(isUp ? 0 : 8);
                    }

/* 1265 */          public void setIcon(Drawable icon) {
/* 1266 */              this.mIconView.setImageDrawable(icon);
                    }

/* 1269 */          public void setUpIndicator(Drawable d) {
/* 1270 */              ImageView imageView = this.mUpView;
                        if (d == null) {
/* 1270 */                  d = this.mDefaultUpIndicator;
                        }
                        imageView.setImageDrawable(d);
/* 1271 */              this.mUpIndicatorRes = 0;
                    }

/* 1274 */          public void setUpIndicator(int resId) {
/* 1275 */              this.mUpIndicatorRes = resId;
/* 1276 */              this.mUpView.setImageDrawable(resId != 0 ? getResources().getDrawable(resId) : this.mDefaultUpIndicator);
                    }

                    @Override // android.view.View
/* 1281 */          protected void onConfigurationChanged(Configuration newConfig) {
/* 1282 */              super.onConfigurationChanged(newConfig);
/* 1283 */              if (this.mUpIndicatorRes != 0) {
/* 1285 */                  setUpIndicator(this.mUpIndicatorRes);
                        }
                    }

                    @Override // android.view.View
/* 1290 */          public boolean dispatchPopulateAccessibilityEvent(AccessibilityEvent event) {
/* 1291 */              CharSequence cdesc = getContentDescription();
/* 1292 */              if (!TextUtils.isEmpty(cdesc)) {
/* 1293 */                  event.getText().add(cdesc);
/* 1295 */                  return true;
                        }
/* 1295 */              return true;
                    }

                    @Override // android.view.View
/* 1299 */          protected void onFinishInflate() {
/* 1300 */              this.mUpView = (ImageView) findViewById(C0084R.id.f1up);
/* 1301 */              this.mIconView = (ImageView) findViewById(C0084R.id.home);
/* 1302 */              this.mDefaultUpIndicator = this.mUpView.getDrawable();
                    }

/* 1305 */          public int getLeftOffset() {
/* 1306 */              if (this.mUpView.getVisibility() == 8) {
                            return this.mUpWidth;
                        }
                        return 0;
                    }

                    @Override // android.widget.FrameLayout, android.view.View
/* 1310 */          protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
/* 1311 */              measureChildWithMargins(this.mUpView, widthMeasureSpec, 0, heightMeasureSpec, 0);
/* 1312 */              FrameLayout.LayoutParams upLp = (FrameLayout.LayoutParams) this.mUpView.getLayoutParams();
/* 1313 */              this.mUpWidth = upLp.leftMargin + this.mUpView.getMeasuredWidth() + upLp.rightMargin;
/* 1314 */              int width = this.mUpView.getVisibility() == 8 ? 0 : this.mUpWidth;
/* 1315 */              int height = upLp.topMargin + this.mUpView.getMeasuredHeight() + upLp.bottomMargin;
/* 1316 */              measureChildWithMargins(this.mIconView, widthMeasureSpec, width, heightMeasureSpec, 0);
/* 1317 */              FrameLayout.LayoutParams iconLp = (FrameLayout.LayoutParams) this.mIconView.getLayoutParams();
/* 1318 */              int width2 = width + iconLp.leftMargin + this.mIconView.getMeasuredWidth() + iconLp.rightMargin;
/* 1319 */              int height2 = Math.max(height, iconLp.topMargin + this.mIconView.getMeasuredHeight() + iconLp.bottomMargin);
/* 1322 */              int widthMode = View.MeasureSpec.getMode(widthMeasureSpec);
/* 1323 */              int heightMode = View.MeasureSpec.getMode(heightMeasureSpec);
/* 1324 */              int widthSize = View.MeasureSpec.getSize(widthMeasureSpec);
/* 1325 */              int heightSize = View.MeasureSpec.getSize(heightMeasureSpec);
                        switch (widthMode) {
                            case Integer.MIN_VALUE:
/* 1329 */                      width2 = Math.min(width2, widthSize);
                                break;
                            case 1073741824:
/* 1332 */                      width2 = widthSize;
                                break;
                        }
                        switch (heightMode) {
                            case Integer.MIN_VALUE:
/* 1340 */                      height2 = Math.min(height2, heightSize);
                                break;
                            case 1073741824:
/* 1343 */                      height2 = heightSize;
                                break;
                        }
/* 1349 */              setMeasuredDimension(width2, height2);
                    }

                    @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
/* 1353 */          protected void onLayout(boolean changed, int l, int t, int r, int b) {
/* 1354 */              int vCenter = (b - t) / 2;
/* 1355 */              int width = r - l;
/* 1356 */              int upOffset = 0;
/* 1357 */              if (this.mUpView.getVisibility() != 8) {
/* 1358 */                  FrameLayout.LayoutParams upLp = (FrameLayout.LayoutParams) this.mUpView.getLayoutParams();
/* 1359 */                  int upHeight = this.mUpView.getMeasuredHeight();
/* 1360 */                  int upWidth = this.mUpView.getMeasuredWidth();
/* 1361 */                  int upTop = vCenter - (upHeight / 2);
/* 1362 */                  this.mUpView.layout(0, upTop, upWidth, upTop + upHeight);
/* 1363 */                  upOffset = upLp.leftMargin + upWidth + upLp.rightMargin;
/* 1364 */                  int i = width - upOffset;
/* 1365 */                  l += upOffset;
                        }
/* 1367 */              FrameLayout.LayoutParams iconLp = (FrameLayout.LayoutParams) this.mIconView.getLayoutParams();
/* 1368 */              int iconHeight = this.mIconView.getMeasuredHeight();
/* 1369 */              int iconWidth = this.mIconView.getMeasuredWidth();
/* 1370 */              int hCenter = (r - l) / 2;
/* 1371 */              int iconLeft = upOffset + Math.max(iconLp.leftMargin, hCenter - (iconWidth / 2));
/* 1372 */              int iconTop = Math.max(iconLp.topMargin, vCenter - (iconHeight / 2));
/* 1373 */              this.mIconView.layout(iconLeft, iconTop, iconLeft + iconWidth, iconTop + iconHeight);
                    }
                }

/* 1375 */      private class ExpandedActionViewMenuPresenter implements MenuPresenter {
                    MenuItemImpl mCurrentExpandedItem;
                    MenuBuilder mMenu;

/* 1376 */          private ExpandedActionViewMenuPresenter() {
                    }

/* 1376 */          /* synthetic */ ExpandedActionViewMenuPresenter(ActionBarView x0, C00931 x1) {
/* 1377 */              this();
                    }

                    @Override // android.support.v7.internal.view.menu.MenuPresenter
/* 1384 */          public void initForMenu(Context context, MenuBuilder menu) {
/* 1385 */              if (this.mMenu != null && this.mCurrentExpandedItem != null) {
/* 1386 */                  this.mMenu.collapseItemActionView(this.mCurrentExpandedItem);
                        }
/* 1388 */              this.mMenu = menu;
                    }

                    @Override // android.support.v7.internal.view.menu.MenuPresenter
/* 1392 */          public MenuView getMenuView(ViewGroup root) {
/* 1393 */              return null;
                    }

                    @Override // android.support.v7.internal.view.menu.MenuPresenter
/* 1398 */          public void updateMenuView(boolean cleared) {
/* 1399 */              if (this.mCurrentExpandedItem != null) {
/* 1400 */                  boolean found = false;
/* 1402 */                  if (this.mMenu != null) {
/* 1403 */                      int count = this.mMenu.size();
                                for (int i = 0; i < count; i++) {
/* 1405 */                          SupportMenuItem item = (SupportMenuItem) this.mMenu.getItem(i);
/* 1406 */                          if (item == this.mCurrentExpandedItem) {
/* 1407 */                              found = true;
                                        break;
                                    }
                                }
                            }
/* 1413 */                  if (!found) {
/* 1415 */                      collapseItemActionView(this.mMenu, this.mCurrentExpandedItem);
                            }
                        }
                    }

                    @Override // android.support.v7.internal.view.menu.MenuPresenter
/* 1421 */          public void setCallback(MenuPresenter.Callback cb) {
                    }

                    @Override // android.support.v7.internal.view.menu.MenuPresenter
/* 1425 */          public boolean onSubMenuSelected(SubMenuBuilder subMenu) {
/* 1426 */              return false;
                    }

                    @Override // android.support.v7.internal.view.menu.MenuPresenter
/* 1430 */          public void onCloseMenu(MenuBuilder menu, boolean allMenusAreClosing) {
                    }

                    @Override // android.support.v7.internal.view.menu.MenuPresenter
/* 1434 */          public boolean flagActionItems() {
/* 1435 */              return false;
                    }

                    @Override // android.support.v7.internal.view.menu.MenuPresenter
/* 1439 */          public boolean expandItemActionView(MenuBuilder menu, MenuItemImpl item) {
/* 1440 */              ActionBarView.this.mExpandedActionView = item.getActionView();
/* 1441 */              ActionBarView.access$600(ActionBarView.this).setIcon(ActionBarView.access$500(ActionBarView.this).getConstantState().newDrawable(ActionBarView.this.getResources()));
/* 1442 */              this.mCurrentExpandedItem = item;
/* 1443 */              if (ActionBarView.this.mExpandedActionView.getParent() != ActionBarView.this) {
/* 1444 */                  ActionBarView.this.addView(ActionBarView.this.mExpandedActionView);
                        }
/* 1446 */              if (ActionBarView.access$600(ActionBarView.this).getParent() != ActionBarView.this) {
/* 1447 */                  ActionBarView.this.addView(ActionBarView.access$600(ActionBarView.this));
                        }
/* 1449 */              ActionBarView.access$700(ActionBarView.this).setVisibility(8);
/* 1450 */              if (ActionBarView.access$800(ActionBarView.this) != null) {
/* 1451 */                  ActionBarView.access$800(ActionBarView.this).setVisibility(8);
                        }
/* 1453 */              if (ActionBarView.access$900(ActionBarView.this) != null) {
/* 1454 */                  ActionBarView.access$900(ActionBarView.this).setVisibility(8);
                        }
/* 1456 */              if (ActionBarView.access$1000(ActionBarView.this) != null) {
/* 1457 */                  ActionBarView.access$1000(ActionBarView.this).setVisibility(8);
                        }
/* 1459 */              if (ActionBarView.access$1100(ActionBarView.this) != null) {
/* 1460 */                  ActionBarView.access$1100(ActionBarView.this).setVisibility(8);
                        }
/* 1462 */              ActionBarView.this.requestLayout();
/* 1463 */              item.setActionViewExpanded(true);
/* 1465 */              if (ActionBarView.this.mExpandedActionView instanceof CollapsibleActionView) {
/* 1466 */                  ((CollapsibleActionView) ActionBarView.this.mExpandedActionView).onActionViewExpanded();
                        }
                        return true;
                    }

                    @Override // android.support.v7.internal.view.menu.MenuPresenter
/* 1475 */          public boolean collapseItemActionView(MenuBuilder menu, MenuItemImpl item) {
/* 1476 */              if (ActionBarView.this.mExpandedActionView instanceof CollapsibleActionView) {
/* 1477 */                  ((CollapsibleActionView) ActionBarView.this.mExpandedActionView).onActionViewCollapsed();
                        }
/* 1480 */              ActionBarView.this.removeView(ActionBarView.this.mExpandedActionView);
/* 1481 */              ActionBarView.this.removeView(ActionBarView.access$600(ActionBarView.this));
/* 1482 */              ActionBarView.this.mExpandedActionView = null;
/* 1483 */              if ((ActionBarView.access$1200(ActionBarView.this) & 2) != 0) {
/* 1484 */                  ActionBarView.access$700(ActionBarView.this).setVisibility(0);
                        }
/* 1486 */              if ((ActionBarView.access$1200(ActionBarView.this) & 8) != 0) {
/* 1487 */                  if (ActionBarView.access$800(ActionBarView.this) == null) {
/* 1488 */                      ActionBarView.access$1300(ActionBarView.this);
                            } else {
/* 1490 */                      ActionBarView.access$800(ActionBarView.this).setVisibility(0);
                            }
                        }
/* 1493 */              if (ActionBarView.access$900(ActionBarView.this) != null && ActionBarView.access$1400(ActionBarView.this) == 2) {
/* 1494 */                  ActionBarView.access$900(ActionBarView.this).setVisibility(0);
                        }
/* 1496 */              if (ActionBarView.access$1000(ActionBarView.this) != null && ActionBarView.access$1400(ActionBarView.this) == 1) {
/* 1497 */                  ActionBarView.access$1000(ActionBarView.this).setVisibility(0);
                        }
/* 1499 */              if (ActionBarView.access$1100(ActionBarView.this) != null && (ActionBarView.access$1200(ActionBarView.this) & 16) != 0) {
/* 1500 */                  ActionBarView.access$1100(ActionBarView.this).setVisibility(0);
                        }
/* 1502 */              ActionBarView.access$600(ActionBarView.this).setIcon(null);
/* 1503 */              this.mCurrentExpandedItem = null;
/* 1504 */              ActionBarView.this.requestLayout();
/* 1505 */              item.setActionViewExpanded(false);
                        return true;
                    }

                    @Override // android.support.v7.internal.view.menu.MenuPresenter
/* 1511 */          public int getId() {
/* 1512 */              return 0;
                    }

                    @Override // android.support.v7.internal.view.menu.MenuPresenter
/* 1516 */          public Parcelable onSaveInstanceState() {
/* 1517 */              return null;
                    }

                    @Override // android.support.v7.internal.view.menu.MenuPresenter
/* 1521 */          public void onRestoreInstanceState(Parcelable state) {
                    }
                }
            }
