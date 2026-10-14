            package android.support.v7.app;

            import android.app.Dialog;
            import android.content.Context;
            import android.content.res.Configuration;
            import android.content.res.Resources;
            import android.graphics.drawable.Drawable;
            import android.os.Handler;
            import android.support.v4.app.FragmentTransaction;
            import android.support.v7.appcompat.C0084R;
            import android.support.v7.internal.view.ActionBarPolicy;
            import android.support.v7.internal.view.SupportMenuInflater;
            import android.support.v7.internal.view.menu.MenuBuilder;
            import android.support.v7.internal.view.menu.SubMenuBuilder;
            import android.support.v7.internal.widget.ActionBarContainer;
            import android.support.v7.internal.widget.ActionBarContextView;
            import android.support.v7.internal.widget.ActionBarOverlayLayout;
            import android.support.v7.internal.widget.ActionBarView;
            import android.support.v7.internal.widget.ScrollingTabContainerView;
            import android.support.v7.view.ActionMode;
            import android.util.TypedValue;
            import android.view.ContextThemeWrapper;
            import android.view.LayoutInflater;
            import android.view.Menu;
            import android.view.MenuInflater;
            import android.view.MenuItem;
            import android.view.View;
            import android.view.ViewGroup;
            import android.view.animation.Animation;
            import android.view.animation.AnimationUtils;
            import android.widget.SpinnerAdapter;
            import java.lang.ref.WeakReference;
            import java.util.ArrayList;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 52 */    class ActionBarImplBase extends ActionBar {
                private static final int CONTEXT_DISPLAY_NORMAL = 0;
                private static final int CONTEXT_DISPLAY_SPLIT = 1;
                private static final int INVALID_POSITION = -1;
                ActionModeImpl mActionMode;
                private ActionBarView mActionView;
                private ActionBarActivity mActivity;
                private ActionBar.Callback mCallback;
                private ActionBarContainer mContainerView;
                private View mContentView;
                private Context mContext;
                private int mContextDisplayMode;
                private ActionBarContextView mContextView;
                ActionMode mDeferredDestroyActionMode;
                ActionMode.Callback mDeferredModeDestroyCallback;
                private Dialog mDialog;
                private boolean mDisplayHomeAsUpSet;
                private boolean mHasEmbeddedTabs;
                private boolean mHiddenByApp;
                private boolean mHiddenBySystem;
                private boolean mLastMenuVisibility;
                private ActionBarOverlayLayout mOverlayLayout;
                private TabImpl mSelectedTab;
                private boolean mShowHideAnimationEnabled;
                private boolean mShowingForMode;
                private ActionBarContainer mSplitView;
                private ScrollingTabContainerView mTabScrollView;
                Runnable mTabSelector;
                private Context mThemedContext;
                private ViewGroup mTopVisibilityView;
                private ArrayList<TabImpl> mTabs = new ArrayList<>();
                private int mSavedTabPosition = -1;
                private ArrayList<ActionBar.OnMenuVisibilityListener> mMenuVisibilityListeners = new ArrayList<>();
                final Handler mHandler = new Handler();
                private int mCurWindowVisibility = 0;
                private boolean mNowShowing = true;

/* 53 */        static /* synthetic */ ScrollingTabContainerView access$000(ActionBarImplBase x0) {
/* 54 */            return x0.mTabScrollView;
                }

/* 53 */        static /* synthetic */ Context access$100(ActionBarImplBase x0) {
/* 54 */            return x0.mContext;
                }

/* 53 */        static /* synthetic */ boolean access$200(ActionBarImplBase x0) {
/* 54 */            return x0.mHiddenByApp;
                }

/* 53 */        static /* synthetic */ boolean access$300(ActionBarImplBase x0) {
/* 54 */            return x0.mHiddenBySystem;
                }

/* 53 */        static /* synthetic */ boolean access$400(boolean x0, boolean x1, boolean x2) {
/* 54 */            return checkShowingFlags(x0, x1, x2);
                }

/* 53 */        static /* synthetic */ ActionBarContextView access$500(ActionBarImplBase x0) {
/* 54 */            return x0.mContextView;
                }

/* 53 */        static /* synthetic */ ActionBarView access$600(ActionBarImplBase x0) {
/* 54 */            return x0.mActionView;
                }

/* 105 */       public ActionBarImplBase(ActionBarActivity activity, ActionBar.Callback callback) {
/* 107 */           this.mActivity = activity;
/* 108 */           this.mContext = activity;
/* 109 */           this.mCallback = callback;
/* 110 */           init(this.mActivity);
                }

/* 113 */       private void init(ActionBarActivity activity) {
/* 114 */           this.mOverlayLayout = (ActionBarOverlayLayout) activity.findViewById(C0084R.id.action_bar_overlay_layout);
/* 116 */           if (this.mOverlayLayout != null) {
/* 117 */               this.mOverlayLayout.setActionBar(this);
                    }
/* 119 */           this.mActionView = (ActionBarView) activity.findViewById(C0084R.id.action_bar);
/* 120 */           this.mContextView = (ActionBarContextView) activity.findViewById(C0084R.id.action_context_bar);
/* 121 */           this.mContainerView = (ActionBarContainer) activity.findViewById(C0084R.id.action_bar_container);
/* 122 */           this.mTopVisibilityView = (ViewGroup) activity.findViewById(C0084R.id.top_action_bar);
/* 123 */           if (this.mTopVisibilityView == null) {
/* 124 */               this.mTopVisibilityView = this.mContainerView;
                    }
/* 126 */           this.mSplitView = (ActionBarContainer) activity.findViewById(C0084R.id.split_action_bar);
/* 128 */           if (this.mActionView == null || this.mContextView == null || this.mContainerView == null) {
/* 129 */               throw new IllegalStateException(getClass().getSimpleName() + " can only be used with a compatible window decor layout");
                    }
/* 133 */           this.mActionView.setContextView(this.mContextView);
/* 134 */           this.mContextDisplayMode = this.mActionView.isSplitActionBar() ? 1 : 0;
/* 138 */           int current = this.mActionView.getDisplayOptions();
/* 139 */           boolean homeAsUp = (current & 4) != 0;
/* 140 */           if (homeAsUp) {
/* 141 */               this.mDisplayHomeAsUpSet = true;
                    }
/* 144 */           ActionBarPolicy abp = ActionBarPolicy.get(this.mContext);
/* 145 */           setHomeButtonEnabled(abp.enableHomeButtonByDefault() || homeAsUp);
/* 146 */           setHasEmbeddedTabs(abp.hasEmbeddedTabs());
/* 147 */           setTitle(this.mActivity.getTitle());
                }

/* 150 */       public void onConfigurationChanged(Configuration newConfig) {
/* 151 */           setHasEmbeddedTabs(ActionBarPolicy.get(this.mContext).hasEmbeddedTabs());
                }

/* 154 */       private void setHasEmbeddedTabs(boolean hasEmbeddedTabs) {
/* 155 */           this.mHasEmbeddedTabs = hasEmbeddedTabs;
/* 157 */           if (!this.mHasEmbeddedTabs) {
/* 158 */               this.mActionView.setEmbeddedTabView(null);
/* 159 */               this.mContainerView.setTabContainer(this.mTabScrollView);
                    } else {
/* 161 */               this.mContainerView.setTabContainer(null);
/* 162 */               this.mActionView.setEmbeddedTabView(this.mTabScrollView);
                    }
/* 164 */           boolean isInTabMode = getNavigationMode() == 2;
/* 165 */           if (this.mTabScrollView != null) {
/* 166 */               if (isInTabMode) {
/* 167 */                   this.mTabScrollView.setVisibility(0);
                        } else {
/* 169 */                   this.mTabScrollView.setVisibility(8);
                        }
                    }
/* 172 */           this.mActionView.setCollapsable(!this.mHasEmbeddedTabs && isInTabMode);
                }

/* 175 */       public boolean hasNonEmbeddedTabs() {
                    return !this.mHasEmbeddedTabs && getNavigationMode() == 2;
                }

                @Override // android.support.v7.app.ActionBar
/* 180 */       public void setCustomView(View view) {
/* 181 */           this.mActionView.setCustomNavigationView(view);
                }

                @Override // android.support.v7.app.ActionBar
/* 185 */       public void setCustomView(View view, ActionBar.LayoutParams layoutParams) {
/* 186 */           view.setLayoutParams(layoutParams);
/* 187 */           this.mActionView.setCustomNavigationView(view);
                }

                @Override // android.support.v7.app.ActionBar
/* 192 */       public void setCustomView(int resId) {
/* 193 */           setCustomView(LayoutInflater.from(getThemedContext()).inflate(resId, (ViewGroup) this.mActionView, false));
                }

                @Override // android.support.v7.app.ActionBar
/* 198 */       public void setIcon(int resId) {
/* 199 */           this.mActionView.setIcon(resId);
                }

                @Override // android.support.v7.app.ActionBar
/* 203 */       public void setIcon(Drawable icon) {
/* 204 */           this.mActionView.setIcon(icon);
                }

                @Override // android.support.v7.app.ActionBar
/* 208 */       public void setLogo(int resId) {
/* 209 */           this.mActionView.setLogo(resId);
                }

                @Override // android.support.v7.app.ActionBar
/* 213 */       public void setLogo(Drawable logo) {
/* 214 */           this.mActionView.setLogo(logo);
                }

                @Override // android.support.v7.app.ActionBar
/* 218 */       public void setListNavigationCallbacks(SpinnerAdapter adapter, ActionBar.OnNavigationListener callback) {
/* 219 */           this.mActionView.setDropdownAdapter(adapter);
/* 220 */           this.mActionView.setCallback(callback);
                }

                @Override // android.support.v7.app.ActionBar
/* 224 */       public void setSelectedNavigationItem(int position) {
                    switch (this.mActionView.getNavigationMode()) {
                        case 1:
/* 230 */                   this.mActionView.setDropdownSelectedPosition(position);
/* 236 */                   return;
                        case 2:
/* 227 */                   selectTab(this.mTabs.get(position));
/* 236 */                   return;
                        default:
/* 233 */                   throw new IllegalStateException("setSelectedNavigationIndex not valid for current navigation mode");
                    }
                }

                @Override // android.support.v7.app.ActionBar
/* 239 */       public int getSelectedNavigationIndex() {
                    switch (this.mActionView.getNavigationMode()) {
                        case 1:
/* 244 */                   return this.mActionView.getDropdownSelectedPosition();
                        case 2:
/* 242 */                   if (this.mSelectedTab != null) {
                                return this.mSelectedTab.getPosition();
                            }
                            return -1;
                        default:
                            return -1;
                    }
                }

                @Override // android.support.v7.app.ActionBar
/* 251 */       public int getNavigationItemCount() {
                    switch (this.mActionView.getNavigationMode()) {
                        case 1:
/* 256 */                   SpinnerAdapter adapter = this.mActionView.getDropdownAdapter();
/* 257 */                   if (adapter != null) {
                                return adapter.getCount();
                            }
                            return 0;
                        case 2:
/* 254 */                   return this.mTabs.size();
                        default:
                            return 0;
                    }
                }

                @Override // android.support.v7.app.ActionBar
/* 264 */       public void setTitle(CharSequence title) {
/* 265 */           this.mActionView.setTitle(title);
                }

                @Override // android.support.v7.app.ActionBar
/* 269 */       public void setTitle(int resId) {
/* 270 */           setTitle(this.mContext.getString(resId));
                }

                @Override // android.support.v7.app.ActionBar
/* 274 */       public void setSubtitle(CharSequence subtitle) {
/* 275 */           this.mActionView.setSubtitle(subtitle);
                }

                @Override // android.support.v7.app.ActionBar
/* 279 */       public void setSubtitle(int resId) {
/* 280 */           setSubtitle(this.mContext.getString(resId));
                }

                @Override // android.support.v7.app.ActionBar
/* 284 */       public void setDisplayOptions(int options) {
/* 285 */           if ((options & 4) != 0) {
/* 286 */               this.mDisplayHomeAsUpSet = true;
                    }
/* 288 */           this.mActionView.setDisplayOptions(options);
                }

                @Override // android.support.v7.app.ActionBar
/* 292 */       public void setDisplayOptions(int options, int mask) {
/* 293 */           int current = this.mActionView.getDisplayOptions();
/* 294 */           if ((mask & 4) != 0) {
/* 295 */               this.mDisplayHomeAsUpSet = true;
                    }
/* 297 */           this.mActionView.setDisplayOptions((options & mask) | ((mask ^ (-1)) & current));
                }

                @Override // android.support.v7.app.ActionBar
/* 301 */       public void setDisplayUseLogoEnabled(boolean useLogo) {
/* 302 */           setDisplayOptions(useLogo ? 1 : 0, 1);
                }

                @Override // android.support.v7.app.ActionBar
/* 306 */       public void setDisplayShowHomeEnabled(boolean showHome) {
/* 307 */           setDisplayOptions(showHome ? 2 : 0, 2);
                }

                @Override // android.support.v7.app.ActionBar
/* 311 */       public void setDisplayHomeAsUpEnabled(boolean showHomeAsUp) {
/* 312 */           setDisplayOptions(showHomeAsUp ? 4 : 0, 4);
                }

                @Override // android.support.v7.app.ActionBar
/* 316 */       public void setDisplayShowTitleEnabled(boolean showTitle) {
/* 317 */           setDisplayOptions(showTitle ? 8 : 0, 8);
                }

                @Override // android.support.v7.app.ActionBar
/* 321 */       public void setDisplayShowCustomEnabled(boolean showCustom) {
/* 322 */           setDisplayOptions(showCustom ? 16 : 0, 16);
                }

                @Override // android.support.v7.app.ActionBar
/* 326 */       public void setHomeButtonEnabled(boolean enable) {
/* 327 */           this.mActionView.setHomeButtonEnabled(enable);
                }

                @Override // android.support.v7.app.ActionBar
/* 331 */       public void setBackgroundDrawable(Drawable d) {
/* 332 */           this.mContainerView.setPrimaryBackground(d);
                }

                @Override // android.support.v7.app.ActionBar
/* 336 */       public void setStackedBackgroundDrawable(Drawable d) {
/* 337 */           this.mContainerView.setStackedBackground(d);
                }

                @Override // android.support.v7.app.ActionBar
/* 341 */       public void setSplitBackgroundDrawable(Drawable d) {
/* 342 */           this.mContainerView.setSplitBackground(d);
                }

                @Override // android.support.v7.app.ActionBar
/* 346 */       public View getCustomView() {
/* 347 */           return this.mActionView.getCustomNavigationView();
                }

                @Override // android.support.v7.app.ActionBar
/* 351 */       public CharSequence getTitle() {
/* 352 */           return this.mActionView.getTitle();
                }

                @Override // android.support.v7.app.ActionBar
/* 356 */       public CharSequence getSubtitle() {
/* 357 */           return this.mActionView.getSubtitle();
                }

                @Override // android.support.v7.app.ActionBar
/* 361 */       public int getNavigationMode() {
/* 362 */           return this.mActionView.getNavigationMode();
                }

                @Override // android.support.v7.app.ActionBar
/* 366 */       public void setNavigationMode(int mode) {
                    boolean z = false;
/* 367 */           int oldMode = this.mActionView.getNavigationMode();
                    switch (oldMode) {
                        case 2:
/* 370 */                   this.mSavedTabPosition = getSelectedNavigationIndex();
/* 371 */                   selectTab(null);
/* 372 */                   this.mTabScrollView.setVisibility(8);
                            break;
                    }
/* 375 */           this.mActionView.setNavigationMode(mode);
                    switch (mode) {
                        case 2:
/* 378 */                   ensureTabsExist();
/* 379 */                   this.mTabScrollView.setVisibility(0);
/* 380 */                   if (this.mSavedTabPosition != -1) {
/* 381 */                       setSelectedNavigationItem(this.mSavedTabPosition);
/* 382 */                       this.mSavedTabPosition = -1;
                            }
                            break;
                    }
/* 386 */           ActionBarView actionBarView = this.mActionView;
                    if (mode == 2 && !this.mHasEmbeddedTabs) {
                        z = true;
                    }
                    actionBarView.setCollapsable(z);
                }

                @Override // android.support.v7.app.ActionBar
/* 390 */       public int getDisplayOptions() {
/* 391 */           return this.mActionView.getDisplayOptions();
                }

                @Override // android.support.v7.app.ActionBar
/* 395 */       public ActionBar.Tab newTab() {
/* 396 */           return new TabImpl();
                }

                @Override // android.support.v7.app.ActionBar
/* 400 */       public void addTab(ActionBar.Tab tab) {
/* 401 */           addTab(tab, this.mTabs.isEmpty());
                }

                @Override // android.support.v7.app.ActionBar
/* 405 */       public void addTab(ActionBar.Tab tab, boolean setSelected) {
/* 406 */           ensureTabsExist();
/* 407 */           this.mTabScrollView.addTab(tab, setSelected);
/* 408 */           configureTab(tab, this.mTabs.size());
/* 409 */           if (setSelected) {
/* 410 */               selectTab(tab);
                    }
                }

                @Override // android.support.v7.app.ActionBar
/* 415 */       public void addTab(ActionBar.Tab tab, int position) {
/* 416 */           addTab(tab, position, this.mTabs.isEmpty());
                }

                @Override // android.support.v7.app.ActionBar
/* 420 */       public void addTab(ActionBar.Tab tab, int position, boolean setSelected) {
/* 421 */           ensureTabsExist();
/* 422 */           this.mTabScrollView.addTab(tab, position, setSelected);
/* 423 */           configureTab(tab, position);
/* 424 */           if (setSelected) {
/* 425 */               selectTab(tab);
                    }
                }

                @Override // android.support.v7.app.ActionBar
/* 430 */       public void removeTab(ActionBar.Tab tab) {
/* 431 */           removeTabAt(tab.getPosition());
                }

                @Override // android.support.v7.app.ActionBar
/* 435 */       public void removeTabAt(int position) {
/* 436 */           if (this.mTabScrollView != null) {
/* 441 */               int selectedTabPosition = this.mSelectedTab != null ? this.mSelectedTab.getPosition() : this.mSavedTabPosition;
/* 443 */               this.mTabScrollView.removeTabAt(position);
/* 444 */               TabImpl removedTab = this.mTabs.remove(position);
/* 445 */               if (removedTab != null) {
/* 446 */                   removedTab.setPosition(-1);
                        }
/* 449 */               int newTabCount = this.mTabs.size();
                        for (int i = position; i < newTabCount; i++) {
/* 451 */                   this.mTabs.get(i).setPosition(i);
                        }
/* 454 */               if (selectedTabPosition == position) {
/* 455 */                   selectTab(this.mTabs.isEmpty() ? null : this.mTabs.get(Math.max(0, position - 1)));
                        }
                    }
                }

                @Override // android.support.v7.app.ActionBar
/* 460 */       public void removeAllTabs() {
/* 461 */           cleanupTabs();
                }

                @Override // android.support.v7.app.ActionBar
/* 465 */       public void selectTab(ActionBar.Tab tab) {
/* 466 */           if (getNavigationMode() != 2) {
/* 467 */               this.mSavedTabPosition = tab != null ? tab.getPosition() : -1;
/* 493 */               return;
                    }
/* 471 */           FragmentTransaction trans = this.mActivity.getSupportFragmentManager().beginTransaction().disallowAddToBackStack();
/* 474 */           if (this.mSelectedTab == tab) {
/* 475 */               if (this.mSelectedTab != null) {
/* 476 */                   this.mSelectedTab.getCallback().onTabReselected(this.mSelectedTab, trans);
/* 477 */                   this.mTabScrollView.animateToTab(tab.getPosition());
                        }
                    } else {
/* 480 */               this.mTabScrollView.setTabSelected(tab != null ? tab.getPosition() : -1);
/* 481 */               if (this.mSelectedTab != null) {
/* 482 */                   this.mSelectedTab.getCallback().onTabUnselected(this.mSelectedTab, trans);
                        }
/* 484 */               this.mSelectedTab = (TabImpl) tab;
/* 485 */               if (this.mSelectedTab != null) {
/* 486 */                   this.mSelectedTab.getCallback().onTabSelected(this.mSelectedTab, trans);
                        }
                    }
/* 490 */           if (!trans.isEmpty()) {
/* 491 */               trans.commit();
                    }
                }

                @Override // android.support.v7.app.ActionBar
/* 496 */       public ActionBar.Tab getSelectedTab() {
/* 497 */           return this.mSelectedTab;
                }

                @Override // android.support.v7.app.ActionBar
/* 501 */       public ActionBar.Tab getTabAt(int index) {
/* 502 */           return this.mTabs.get(index);
                }

                @Override // android.support.v7.app.ActionBar
/* 506 */       public int getTabCount() {
/* 507 */           return this.mTabs.size();
                }

                @Override // android.support.v7.app.ActionBar
/* 511 */       public Context getThemedContext() {
/* 512 */           if (this.mThemedContext == null) {
/* 513 */               TypedValue outValue = new TypedValue();
/* 514 */               Resources.Theme currentTheme = this.mContext.getTheme();
/* 515 */               currentTheme.resolveAttribute(C0084R.attr.actionBarWidgetTheme, outValue, true);
/* 516 */               int targetThemeRes = outValue.resourceId;
/* 518 */               if (targetThemeRes != 0) {
/* 519 */                   this.mThemedContext = new ContextThemeWrapper(this.mContext, targetThemeRes);
                        } else {
/* 521 */                   this.mThemedContext = this.mContext;
                        }
                    }
/* 524 */           return this.mThemedContext;
                }

                @Override // android.support.v7.app.ActionBar
/* 528 */       public void setHomeAsUpIndicator(Drawable indicator) {
/* 529 */           this.mActionView.setHomeAsUpIndicator(indicator);
                }

                @Override // android.support.v7.app.ActionBar
/* 533 */       public void setHomeAsUpIndicator(int resId) {
/* 534 */           this.mActionView.setHomeAsUpIndicator(resId);
                }

                @Override // android.support.v7.app.ActionBar
/* 538 */       public int getHeight() {
/* 539 */           return this.mContainerView.getHeight();
                }

                @Override // android.support.v7.app.ActionBar
/* 543 */       public void show() {
/* 544 */           if (this.mHiddenByApp) {
/* 545 */               this.mHiddenByApp = false;
/* 546 */               updateVisibility(false);
                    }
                }

/* 550 */       void showForActionMode() {
/* 551 */           if (!this.mShowingForMode) {
/* 552 */               this.mShowingForMode = true;
/* 553 */               updateVisibility(false);
                    }
                }

                @Override // android.support.v7.app.ActionBar
/* 558 */       public void hide() {
/* 559 */           if (!this.mHiddenByApp) {
/* 560 */               this.mHiddenByApp = true;
/* 561 */               updateVisibility(false);
                    }
                }

/* 565 */       void hideForActionMode() {
/* 566 */           if (this.mShowingForMode) {
/* 567 */               this.mShowingForMode = false;
/* 568 */               updateVisibility(false);
                    }
                }

                @Override // android.support.v7.app.ActionBar
/* 573 */       public boolean isShowing() {
/* 574 */           return this.mNowShowing;
                }

                @Override // android.support.v7.app.ActionBar
/* 578 */       public void addOnMenuVisibilityListener(ActionBar.OnMenuVisibilityListener listener) {
/* 579 */           this.mMenuVisibilityListeners.add(listener);
                }

                @Override // android.support.v7.app.ActionBar
/* 583 */       public void removeOnMenuVisibilityListener(ActionBar.OnMenuVisibilityListener listener) {
/* 584 */           this.mMenuVisibilityListeners.remove(listener);
                }

/* 587 */       public ActionMode startActionMode(ActionMode.Callback callback) {
/* 588 */           if (this.mActionMode != null) {
/* 589 */               this.mActionMode.finish();
                    }
/* 592 */           this.mContextView.killMode();
/* 593 */           ActionModeImpl mode = new ActionModeImpl(callback);
/* 594 */           if (!mode.dispatchOnCreate()) {
                        return null;
                    }
/* 595 */           mode.invalidate();
/* 596 */           this.mContextView.initForMode(mode);
/* 597 */           animateToMode(true);
/* 598 */           if (this.mSplitView != null && this.mContextDisplayMode == 1 && this.mSplitView.getVisibility() != 0) {
/* 600 */               this.mSplitView.setVisibility(0);
                    }
/* 603 */           this.mContextView.sendAccessibilityEvent(32);
/* 604 */           this.mActionMode = mode;
/* 607 */           return mode;
                }

/* 610 */       void animateToMode(boolean toActionMode) {
/* 611 */           if (toActionMode) {
/* 612 */               showForActionMode();
                    } else {
/* 614 */               hideForActionMode();
                    }
/* 617 */           this.mActionView.animateToVisibility(toActionMode ? 4 : 0);
/* 618 */           this.mContextView.animateToVisibility(toActionMode ? 0 : 8);
/* 619 */           if (this.mTabScrollView != null && !this.mActionView.hasEmbeddedTabs() && this.mActionView.isCollapsed()) {
/* 620 */               this.mTabScrollView.setVisibility(toActionMode ? 8 : 0);
                    }
                }

/* 625 */       public class TabImpl extends ActionBar.Tab {
                    private ActionBar.TabListener mCallback;
                    private CharSequence mContentDesc;
                    private View mCustomView;
                    private Drawable mIcon;
                    private int mPosition = -1;
                    private Object mTag;
                    private CharSequence mText;

/* 626 */           public TabImpl() {
                    }

                    @Override // android.support.v7.app.ActionBar.Tab
/* 638 */           public Object getTag() {
/* 639 */               return this.mTag;
                    }

                    @Override // android.support.v7.app.ActionBar.Tab
/* 643 */           public ActionBar.Tab setTag(Object tag) {
/* 644 */               this.mTag = tag;
/* 645 */               return this;
                    }

/* 648 */           public ActionBar.TabListener getCallback() {
/* 649 */               return this.mCallback;
                    }

                    @Override // android.support.v7.app.ActionBar.Tab
/* 653 */           public ActionBar.Tab setTabListener(ActionBar.TabListener callback) {
/* 654 */               this.mCallback = callback;
/* 655 */               return this;
                    }

                    @Override // android.support.v7.app.ActionBar.Tab
/* 659 */           public View getCustomView() {
/* 660 */               return this.mCustomView;
                    }

                    @Override // android.support.v7.app.ActionBar.Tab
/* 664 */           public ActionBar.Tab setCustomView(View view) {
/* 665 */               this.mCustomView = view;
/* 666 */               if (this.mPosition >= 0) {
/* 667 */                   ActionBarImplBase.access$000(ActionBarImplBase.this).updateTab(this.mPosition);
                        }
/* 669 */               return this;
                    }

                    @Override // android.support.v7.app.ActionBar.Tab
/* 673 */           public ActionBar.Tab setCustomView(int layoutResId) {
/* 674 */               return setCustomView(LayoutInflater.from(ActionBarImplBase.this.getThemedContext()).inflate(layoutResId, (ViewGroup) null));
                    }

                    @Override // android.support.v7.app.ActionBar.Tab
/* 679 */           public Drawable getIcon() {
/* 680 */               return this.mIcon;
                    }

                    @Override // android.support.v7.app.ActionBar.Tab
/* 684 */           public int getPosition() {
/* 685 */               return this.mPosition;
                    }

/* 688 */           public void setPosition(int position) {
/* 689 */               this.mPosition = position;
                    }

                    @Override // android.support.v7.app.ActionBar.Tab
/* 693 */           public CharSequence getText() {
/* 694 */               return this.mText;
                    }

                    @Override // android.support.v7.app.ActionBar.Tab
/* 698 */           public ActionBar.Tab setIcon(Drawable icon) {
/* 699 */               this.mIcon = icon;
/* 700 */               if (this.mPosition >= 0) {
/* 701 */                   ActionBarImplBase.access$000(ActionBarImplBase.this).updateTab(this.mPosition);
                        }
/* 703 */               return this;
                    }

                    @Override // android.support.v7.app.ActionBar.Tab
/* 707 */           public ActionBar.Tab setIcon(int resId) {
/* 708 */               return setIcon(ActionBarImplBase.access$100(ActionBarImplBase.this).getResources().getDrawable(resId));
                    }

                    @Override // android.support.v7.app.ActionBar.Tab
/* 712 */           public ActionBar.Tab setText(CharSequence text) {
/* 713 */               this.mText = text;
/* 714 */               if (this.mPosition >= 0) {
/* 715 */                   ActionBarImplBase.access$000(ActionBarImplBase.this).updateTab(this.mPosition);
                        }
/* 717 */               return this;
                    }

                    @Override // android.support.v7.app.ActionBar.Tab
/* 721 */           public ActionBar.Tab setText(int resId) {
/* 722 */               return setText(ActionBarImplBase.access$100(ActionBarImplBase.this).getResources().getText(resId));
                    }

                    @Override // android.support.v7.app.ActionBar.Tab
/* 726 */           public void select() {
/* 727 */               ActionBarImplBase.this.selectTab(this);
                    }

                    @Override // android.support.v7.app.ActionBar.Tab
/* 731 */           public ActionBar.Tab setContentDescription(int resId) {
/* 732 */               return setContentDescription(ActionBarImplBase.access$100(ActionBarImplBase.this).getResources().getText(resId));
                    }

                    @Override // android.support.v7.app.ActionBar.Tab
/* 736 */           public ActionBar.Tab setContentDescription(CharSequence contentDesc) {
/* 737 */               this.mContentDesc = contentDesc;
/* 738 */               if (this.mPosition >= 0) {
/* 739 */                   ActionBarImplBase.access$000(ActionBarImplBase.this).updateTab(this.mPosition);
                        }
/* 741 */               return this;
                    }

                    @Override // android.support.v7.app.ActionBar.Tab
/* 745 */           public CharSequence getContentDescription() {
/* 746 */               return this.mContentDesc;
                    }
                }

/* 754 */       class ActionModeImpl extends ActionMode implements MenuBuilder.Callback {
                    private ActionMode.Callback mCallback;
                    private WeakReference<View> mCustomView;
                    private MenuBuilder mMenu;

/* 755 */           public ActionModeImpl(ActionMode.Callback callback) {
/* 757 */               this.mCallback = callback;
/* 758 */               this.mMenu = new MenuBuilder(ActionBarImplBase.this.getThemedContext()).setDefaultShowAsAction(1);
/* 760 */               this.mMenu.setCallback(this);
                    }

                    @Override // android.support.v7.view.ActionMode
/* 764 */           public MenuInflater getMenuInflater() {
/* 765 */               return new SupportMenuInflater(ActionBarImplBase.this.getThemedContext());
                    }

                    @Override // android.support.v7.view.ActionMode
/* 769 */           public Menu getMenu() {
/* 770 */               return this.mMenu;
                    }

                    @Override // android.support.v7.view.ActionMode
/* 774 */           public void finish() {
/* 775 */               if (ActionBarImplBase.this.mActionMode == this) {
/* 784 */                   if (!ActionBarImplBase.access$400(ActionBarImplBase.access$200(ActionBarImplBase.this), ActionBarImplBase.access$300(ActionBarImplBase.this), false)) {
/* 787 */                       ActionBarImplBase.this.mDeferredDestroyActionMode = this;
/* 788 */                       ActionBarImplBase.this.mDeferredModeDestroyCallback = this.mCallback;
                            } else {
/* 790 */                       this.mCallback.onDestroyActionMode(this);
                            }
/* 792 */                   this.mCallback = null;
/* 793 */                   ActionBarImplBase.this.animateToMode(false);
/* 796 */                   ActionBarImplBase.access$500(ActionBarImplBase.this).closeMode();
/* 797 */                   ActionBarImplBase.access$600(ActionBarImplBase.this).sendAccessibilityEvent(32);
/* 799 */                   ActionBarImplBase.this.mActionMode = null;
                        }
                    }

                    @Override // android.support.v7.view.ActionMode
/* 803 */           public void invalidate() {
/* 804 */               this.mMenu.stopDispatchingItemsChanged();
                        try {
/* 806 */                   this.mCallback.onPrepareActionMode(this, this.mMenu);
/* 808 */                   this.mMenu.startDispatchingItemsChanged();
                        } catch (Throwable th) {
                            this.mMenu.startDispatchingItemsChanged();
                            throw th;
                        }
                    }

/* 812 */           public boolean dispatchOnCreate() {
/* 813 */               this.mMenu.stopDispatchingItemsChanged();
                        try {
/* 815 */                   boolean zOnCreateActionMode = this.mCallback.onCreateActionMode(this, this.mMenu);
/* 817 */                   this.mMenu.startDispatchingItemsChanged();
                            return zOnCreateActionMode;
                        } catch (Throwable th) {
                            this.mMenu.startDispatchingItemsChanged();
                            throw th;
                        }
                    }

                    @Override // android.support.v7.view.ActionMode
/* 822 */           public void setCustomView(View view) {
/* 823 */               ActionBarImplBase.access$500(ActionBarImplBase.this).setCustomView(view);
/* 824 */               this.mCustomView = new WeakReference<>(view);
                    }

                    @Override // android.support.v7.view.ActionMode
/* 828 */           public void setSubtitle(CharSequence subtitle) {
/* 829 */               ActionBarImplBase.access$500(ActionBarImplBase.this).setSubtitle(subtitle);
                    }

                    @Override // android.support.v7.view.ActionMode
/* 833 */           public void setTitle(CharSequence title) {
/* 834 */               ActionBarImplBase.access$500(ActionBarImplBase.this).setTitle(title);
                    }

                    @Override // android.support.v7.view.ActionMode
/* 838 */           public void setTitle(int resId) {
/* 839 */               setTitle(ActionBarImplBase.access$100(ActionBarImplBase.this).getResources().getString(resId));
                    }

                    @Override // android.support.v7.view.ActionMode
/* 843 */           public void setSubtitle(int resId) {
/* 844 */               setSubtitle(ActionBarImplBase.access$100(ActionBarImplBase.this).getResources().getString(resId));
                    }

                    @Override // android.support.v7.view.ActionMode
/* 848 */           public CharSequence getTitle() {
/* 849 */               return ActionBarImplBase.access$500(ActionBarImplBase.this).getTitle();
                    }

                    @Override // android.support.v7.view.ActionMode
/* 853 */           public CharSequence getSubtitle() {
/* 854 */               return ActionBarImplBase.access$500(ActionBarImplBase.this).getSubtitle();
                    }

                    @Override // android.support.v7.view.ActionMode
/* 858 */           public void setTitleOptionalHint(boolean titleOptional) {
/* 859 */               super.setTitleOptionalHint(titleOptional);
/* 860 */               ActionBarImplBase.access$500(ActionBarImplBase.this).setTitleOptional(titleOptional);
                    }

                    @Override // android.support.v7.view.ActionMode
/* 864 */           public boolean isTitleOptional() {
/* 865 */               return ActionBarImplBase.access$500(ActionBarImplBase.this).isTitleOptional();
                    }

                    @Override // android.support.v7.view.ActionMode
/* 869 */           public View getCustomView() {
/* 870 */               if (this.mCustomView != null) {
                            return this.mCustomView.get();
                        }
                        return null;
                    }

                    @Override // android.support.v7.internal.view.menu.MenuBuilder.Callback
/* 873 */           public boolean onMenuItemSelected(MenuBuilder menu, MenuItem item) {
/* 874 */               if (this.mCallback != null) {
/* 875 */                   return this.mCallback.onActionItemClicked(this, item);
                        }
                        return false;
                    }

                    @Override // android.support.v7.internal.view.menu.MenuBuilder.Callback
/* 882 */           public void onMenuModeChange(MenuBuilder menu) {
/* 883 */               if (this.mCallback != null) {
/* 886 */                   invalidate();
/* 887 */                   ActionBarImplBase.access$500(ActionBarImplBase.this).showOverflowMenu();
                        }
                    }

/* 890 */           public void onCloseMenu(MenuBuilder menu, boolean allMenusAreClosing) {
                    }

/* 893 */           public boolean onSubMenuSelected(SubMenuBuilder subMenu) {
/* 894 */               if (this.mCallback == null) {
/* 895 */                   return false;
                        }
/* 898 */               if (!subMenu.hasVisibleItems()) {
                        }
                        return true;
                    }

/* 906 */           public void onCloseSubMenu(SubMenuBuilder menu) {
                    }

/* 909 */           public void onMenuModeChange(Menu menu) {
/* 910 */               if (this.mCallback != null) {
/* 913 */                   invalidate();
/* 914 */                   ActionBarImplBase.access$500(ActionBarImplBase.this).showOverflowMenu();
                        }
                    }
                }

/* 918 */       private void ensureTabsExist() {
/* 919 */           if (this.mTabScrollView == null) {
/* 923 */               ScrollingTabContainerView tabScroller = new ScrollingTabContainerView(this.mContext);
/* 925 */               if (this.mHasEmbeddedTabs) {
/* 926 */                   tabScroller.setVisibility(0);
/* 927 */                   this.mActionView.setEmbeddedTabView(tabScroller);
                        } else {
/* 929 */                   if (getNavigationMode() == 2) {
/* 930 */                       tabScroller.setVisibility(0);
                            } else {
/* 932 */                       tabScroller.setVisibility(8);
                            }
/* 934 */                   this.mContainerView.setTabContainer(tabScroller);
                        }
/* 936 */               this.mTabScrollView = tabScroller;
                    }
                }

/* 939 */       private void configureTab(ActionBar.Tab tab, int position) {
/* 940 */           TabImpl tabi = (TabImpl) tab;
/* 941 */           ActionBar.TabListener callback = tabi.getCallback();
/* 943 */           if (callback == null) {
/* 944 */               throw new IllegalStateException("Action Bar Tab must have a Callback");
                    }
/* 947 */           tabi.setPosition(position);
/* 948 */           this.mTabs.add(position, tabi);
/* 950 */           int count = this.mTabs.size();
                    for (int i = position + 1; i < count; i++) {
/* 952 */               this.mTabs.get(i).setPosition(i);
                    }
                }

/* 956 */       private void cleanupTabs() {
/* 957 */           if (this.mSelectedTab != null) {
/* 958 */               selectTab(null);
                    }
/* 960 */           this.mTabs.clear();
/* 961 */           if (this.mTabScrollView != null) {
/* 962 */               this.mTabScrollView.removeAllTabs();
                    }
/* 964 */           this.mSavedTabPosition = -1;
                }

/* 968 */       private static boolean checkShowingFlags(boolean hiddenByApp, boolean hiddenBySystem, boolean showingForMode) {
/* 969 */           if (showingForMode) {
                        return true;
                    }
                    return (hiddenByApp || hiddenBySystem) ? false : true;
                }

/* 979 */       private void updateVisibility(boolean fromSystem) {
/* 980 */           boolean shown = checkShowingFlags(this.mHiddenByApp, this.mHiddenBySystem, this.mShowingForMode);
/* 982 */           if (shown) {
/* 983 */               if (!this.mNowShowing) {
/* 984 */                   this.mNowShowing = true;
/* 985 */                   doShow(fromSystem);
/* 993 */                   return;
                        }
/* 993 */               return;
                    }
/* 988 */           if (this.mNowShowing) {
/* 989 */               this.mNowShowing = false;
/* 990 */               doHide(fromSystem);
                    }
                }

/* 995 */       public void setShowHideAnimationEnabled(boolean enabled) {
/* 996 */           this.mShowHideAnimationEnabled = enabled;
/* 997 */           if (!enabled) {
/* 998 */               this.mTopVisibilityView.clearAnimation();
/* 999 */               if (this.mSplitView != null) {
/* 1000 */                  this.mSplitView.clearAnimation();
                        }
                    }
                }

/* 1005 */      public void doShow(boolean fromSystem) {
/* 1006 */          this.mTopVisibilityView.clearAnimation();
/* 1007 */          if (this.mTopVisibilityView.getVisibility() != 0) {
/* 1011 */              boolean animate = isShowHideAnimationEnabled() || fromSystem;
/* 1013 */              if (animate) {
/* 1014 */                  Animation anim = AnimationUtils.loadAnimation(this.mContext, C0084R.anim.abc_slide_in_top);
/* 1015 */                  this.mTopVisibilityView.startAnimation(anim);
                        }
/* 1017 */              this.mTopVisibilityView.setVisibility(0);
/* 1019 */              if (this.mSplitView != null && this.mSplitView.getVisibility() != 0) {
/* 1020 */                  if (animate) {
/* 1021 */                      Animation anim2 = AnimationUtils.loadAnimation(this.mContext, C0084R.anim.abc_slide_in_bottom);
/* 1022 */                      this.mSplitView.startAnimation(anim2);
                            }
/* 1024 */                  this.mSplitView.setVisibility(0);
                        }
                    }
                }

/* 1028 */      public void doHide(boolean fromSystem) {
/* 1029 */          this.mTopVisibilityView.clearAnimation();
/* 1030 */          if (this.mTopVisibilityView.getVisibility() != 8) {
/* 1034 */              boolean animate = isShowHideAnimationEnabled() || fromSystem;
/* 1036 */              if (animate) {
/* 1037 */                  Animation anim = AnimationUtils.loadAnimation(this.mContext, C0084R.anim.abc_slide_out_top);
/* 1038 */                  this.mTopVisibilityView.startAnimation(anim);
                        }
/* 1040 */              this.mTopVisibilityView.setVisibility(8);
/* 1042 */              if (this.mSplitView != null && this.mSplitView.getVisibility() != 8) {
/* 1043 */                  if (animate) {
/* 1044 */                      Animation anim2 = AnimationUtils.loadAnimation(this.mContext, C0084R.anim.abc_slide_out_bottom);
/* 1046 */                      this.mSplitView.startAnimation(anim2);
                            }
/* 1048 */                  this.mSplitView.setVisibility(8);
                        }
                    }
                }

/* 1052 */      boolean isShowHideAnimationEnabled() {
/* 1053 */          return this.mShowHideAnimationEnabled;
                }
            }
