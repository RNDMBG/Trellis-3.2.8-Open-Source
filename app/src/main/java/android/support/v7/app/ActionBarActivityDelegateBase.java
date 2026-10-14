            package android.support.v7.app;

            import android.R;
            import android.content.Context;
            import android.content.res.Configuration;
            import android.content.res.TypedArray;
            import android.os.Bundle;
            import android.support.v7.appcompat.C0084R;
            import android.support.v7.internal.view.menu.ListMenuPresenter;
            import android.support.v7.internal.view.menu.MenuBuilder;
            import android.support.v7.internal.view.menu.MenuPresenter;
            import android.support.v7.internal.view.menu.MenuView;
            import android.support.v7.internal.view.menu.MenuWrapperFactory;
            import android.support.v7.internal.widget.ActionBarContainer;
            import android.support.v7.internal.widget.ActionBarContextView;
            import android.support.v7.internal.widget.ActionBarView;
            import android.support.v7.internal.widget.ProgressBarICS;
            import android.support.v7.view.ActionMode;
            import android.util.DisplayMetrics;
            import android.util.TypedValue;
            import android.view.Menu;
            import android.view.MenuItem;
            import android.view.View;
            import android.view.ViewGroup;
            import android.widget.FrameLayout;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 46 */    class ActionBarActivityDelegateBase extends ActionBarActivityDelegate implements MenuPresenter.Callback, MenuBuilder.Callback {
                private static final int[] ACTION_BAR_DRAWABLE_TOGGLE_ATTRS = {C0084R.attr.homeAsUpIndicator};
                private static final String TAG = "ActionBarActivityDelegateBase";
                private ActionBarView mActionBarView;
                private ActionMode mActionMode;
                private boolean mClosingActionMenu;
                private boolean mFeatureIndeterminateProgress;
                private boolean mFeatureProgress;
                private ListMenuPresenter mListMenuPresenter;
                private MenuBuilder mMenu;
                private Bundle mPanelFrozenActionViewState;
                private boolean mPanelIsPrepared;
                private boolean mPanelRefreshContent;
                private boolean mSubDecorInstalled;
                private CharSequence mTitleToSet;

/* 47 */        static /* synthetic */ ActionMode access$002(ActionBarActivityDelegateBase x0, ActionMode x1) {
/* 48 */            x0.mActionMode = x1;
                    return x1;
                }

/* 76 */        ActionBarActivityDelegateBase(ActionBarActivity activity) {
/* 77 */            super(activity);
                }

                @Override // android.support.v7.app.ActionBarActivityDelegate
/* 81 */        public ActionBar createSupportActionBar() {
/* 82 */            ensureSubDecor();
/* 83 */            return new ActionBarImplBase(this.mActivity, this.mActivity);
                }

                @Override // android.support.v7.app.ActionBarActivityDelegate
/* 89 */        public void onConfigurationChanged(Configuration newConfig) {
/* 90 */            if (this.mHasActionBar && this.mSubDecorInstalled) {
/* 93 */                ActionBarImplBase actionBar = (ActionBarImplBase) getSupportActionBar();
/* 94 */                actionBar.onConfigurationChanged(newConfig);
                    }
                }

                @Override // android.support.v7.app.ActionBarActivityDelegate
/* 99 */        public void onStop() {
/* 100 */           ActionBarImplBase ab = (ActionBarImplBase) getSupportActionBar();
/* 101 */           if (ab != null) {
/* 102 */               ab.setShowHideAnimationEnabled(false);
                    }
                }

                @Override // android.support.v7.app.ActionBarActivityDelegate
/* 107 */       public void onPostResume() {
/* 108 */           ActionBarImplBase ab = (ActionBarImplBase) getSupportActionBar();
/* 109 */           if (ab != null) {
/* 110 */               ab.setShowHideAnimationEnabled(true);
                    }
                }

                @Override // android.support.v7.app.ActionBarActivityDelegate
/* 115 */       public void setContentView(View v) {
/* 116 */           ensureSubDecor();
/* 117 */           ViewGroup contentParent = (ViewGroup) this.mActivity.findViewById(R.id.content);
/* 118 */           contentParent.removeAllViews();
/* 119 */           contentParent.addView(v);
/* 120 */           this.mActivity.onSupportContentChanged();
                }

                @Override // android.support.v7.app.ActionBarActivityDelegate
/* 124 */       public void setContentView(int resId) {
/* 125 */           ensureSubDecor();
/* 126 */           ViewGroup contentParent = (ViewGroup) this.mActivity.findViewById(R.id.content);
/* 127 */           contentParent.removeAllViews();
/* 128 */           this.mActivity.getLayoutInflater().inflate(resId, contentParent);
/* 129 */           this.mActivity.onSupportContentChanged();
                }

                @Override // android.support.v7.app.ActionBarActivityDelegate
/* 133 */       public void setContentView(View v, ViewGroup.LayoutParams lp) {
/* 134 */           ensureSubDecor();
/* 135 */           ViewGroup contentParent = (ViewGroup) this.mActivity.findViewById(R.id.content);
/* 136 */           contentParent.removeAllViews();
/* 137 */           contentParent.addView(v, lp);
/* 138 */           this.mActivity.onSupportContentChanged();
                }

                @Override // android.support.v7.app.ActionBarActivityDelegate
/* 142 */       public void addContentView(View v, ViewGroup.LayoutParams lp) {
/* 143 */           ensureSubDecor();
/* 144 */           ViewGroup contentParent = (ViewGroup) this.mActivity.findViewById(R.id.content);
/* 145 */           contentParent.addView(v, lp);
/* 146 */           this.mActivity.onSupportContentChanged();
                }

                @Override // android.support.v7.app.ActionBarActivityDelegate
/* 151 */       public void onContentChanged() {
                }

/* 154 */       final void ensureSubDecor() {
                    boolean splitActionBar;
/* 155 */           if (!this.mSubDecorInstalled) {
/* 156 */               if (this.mHasActionBar) {
/* 157 */                   if (this.mOverlayActionBar) {
/* 158 */                       this.mActivity.superSetContentView(C0084R.layout.abc_action_bar_decor_overlay);
                            } else {
/* 160 */                       this.mActivity.superSetContentView(C0084R.layout.abc_action_bar_decor);
                            }
/* 162 */                   this.mActionBarView = (ActionBarView) this.mActivity.findViewById(C0084R.id.action_bar);
/* 163 */                   this.mActionBarView.setWindowCallback(this.mActivity);
/* 168 */                   if (this.mFeatureProgress) {
/* 169 */                       this.mActionBarView.initProgress();
                            }
/* 171 */                   if (this.mFeatureIndeterminateProgress) {
/* 172 */                       this.mActionBarView.initIndeterminateProgress();
                            }
/* 178 */                   boolean splitWhenNarrow = "splitActionBarWhenNarrow".equals(getUiOptionsFromMetadata());
/* 182 */                   if (splitWhenNarrow) {
/* 183 */                       splitActionBar = this.mActivity.getResources().getBoolean(C0084R.bool.abc_split_action_bar_is_narrow);
                            } else {
/* 186 */                       TypedArray a = this.mActivity.obtainStyledAttributes(C0084R.styleable.ActionBarWindow);
/* 187 */                       splitActionBar = a.getBoolean(2, false);
/* 189 */                       a.recycle();
                            }
/* 192 */                   ActionBarContainer splitView = (ActionBarContainer) this.mActivity.findViewById(C0084R.id.split_action_bar);
/* 194 */                   if (splitView != null) {
/* 195 */                       this.mActionBarView.setSplitView(splitView);
/* 196 */                       this.mActionBarView.setSplitActionBar(splitActionBar);
/* 197 */                       this.mActionBarView.setSplitWhenNarrow(splitWhenNarrow);
/* 199 */                       ActionBarContextView cab = (ActionBarContextView) this.mActivity.findViewById(C0084R.id.action_context_bar);
/* 201 */                       cab.setSplitView(splitView);
/* 202 */                       cab.setSplitActionBar(splitActionBar);
/* 203 */                       cab.setSplitWhenNarrow(splitWhenNarrow);
                            }
                        } else {
/* 206 */                   this.mActivity.superSetContentView(C0084R.layout.abc_simple_decor);
                        }
/* 211 */               View content = this.mActivity.findViewById(R.id.content);
/* 212 */               content.setId(-1);
/* 213 */               View abcContent = this.mActivity.findViewById(C0084R.id.action_bar_activity_content);
/* 214 */               abcContent.setId(R.id.content);
/* 217 */               if (this.mTitleToSet != null) {
/* 218 */                   this.mActionBarView.setWindowTitle(this.mTitleToSet);
/* 219 */                   this.mTitleToSet = null;
                        }
/* 222 */               applyFixedSizeWindow();
/* 224 */               this.mSubDecorInstalled = true;
/* 227 */               this.mActivity.getWindow().getDecorView().post(new RunnableC00831());
                    }
                }

                /* JADX INFO: renamed from: android.support.v7.app.ActionBarActivityDelegateBase$1 */
/* 225 */       class RunnableC00831 implements Runnable {
/* 226 */           RunnableC00831() {
                    }

                    @Override // java.lang.Runnable
/* 229 */           public void run() {
/* 230 */               ActionBarActivityDelegateBase.this.supportInvalidateOptionsMenu();
                    }
                }

/* 236 */       private void applyFixedSizeWindow() {
/* 237 */           TypedArray a = this.mActivity.obtainStyledAttributes(C0084R.styleable.ActionBarWindow);
/* 239 */           TypedValue mFixedWidthMajor = null;
/* 240 */           TypedValue mFixedWidthMinor = null;
/* 241 */           TypedValue mFixedHeightMajor = null;
/* 242 */           TypedValue mFixedHeightMinor = null;
/* 244 */           if (a.hasValue(3)) {
/* 245 */               mFixedWidthMajor = 0 == 0 ? new TypedValue() : null;
/* 246 */               a.getValue(3, mFixedWidthMajor);
                    }
/* 248 */           if (a.hasValue(5)) {
/* 249 */               mFixedWidthMinor = 0 == 0 ? new TypedValue() : null;
/* 250 */               a.getValue(5, mFixedWidthMinor);
                    }
/* 252 */           if (a.hasValue(6)) {
/* 253 */               mFixedHeightMajor = 0 == 0 ? new TypedValue() : null;
/* 254 */               a.getValue(6, mFixedHeightMajor);
                    }
/* 256 */           if (a.hasValue(4)) {
/* 257 */               mFixedHeightMinor = 0 == 0 ? new TypedValue() : null;
/* 258 */               a.getValue(4, mFixedHeightMinor);
                    }
/* 261 */           DisplayMetrics metrics = this.mActivity.getResources().getDisplayMetrics();
/* 262 */           boolean isPortrait = metrics.widthPixels < metrics.heightPixels;
/* 263 */           int w = -1;
/* 264 */           int h = -1;
/* 266 */           TypedValue tvw = isPortrait ? mFixedWidthMinor : mFixedWidthMajor;
/* 267 */           if (tvw != null && tvw.type != 0) {
/* 268 */               if (tvw.type == 5) {
/* 269 */                   w = (int) tvw.getDimension(metrics);
                        } else if (tvw.type == 6) {
/* 271 */                   w = (int) tvw.getFraction(metrics.widthPixels, metrics.widthPixels);
                        }
                    }
/* 275 */           TypedValue tvh = isPortrait ? mFixedHeightMajor : mFixedHeightMinor;
/* 276 */           if (tvh != null && tvh.type != 0) {
/* 277 */               if (tvh.type == 5) {
/* 278 */                   h = (int) tvh.getDimension(metrics);
                        } else if (tvh.type == 6) {
/* 280 */                   h = (int) tvh.getFraction(metrics.heightPixels, metrics.heightPixels);
                        }
                    }
/* 284 */           if (w != -1 || h != -1) {
/* 285 */               this.mActivity.getWindow().setLayout(w, h);
                    }
/* 288 */           a.recycle();
                }

                @Override // android.support.v7.app.ActionBarActivityDelegate
/* 292 */       public boolean supportRequestWindowFeature(int featureId) {
                    switch (featureId) {
                        case 2:
/* 301 */                   this.mFeatureProgress = true;
                            return true;
                        case 3:
                        case 4:
                        case 6:
                        case 7:
                        default:
/* 307 */                   return this.mActivity.requestWindowFeature(featureId);
                        case 5:
/* 304 */                   this.mFeatureIndeterminateProgress = true;
                            return true;
                        case 8:
/* 295 */                   this.mHasActionBar = true;
                            return true;
                        case 9:
/* 298 */                   this.mOverlayActionBar = true;
                            return true;
                    }
                }

                @Override // android.support.v7.app.ActionBarActivityDelegate
/* 312 */       public void onTitleChanged(CharSequence title) {
/* 313 */           if (this.mActionBarView != null) {
/* 314 */               this.mActionBarView.setWindowTitle(title);
                    } else {
/* 316 */               this.mTitleToSet = title;
                    }
                }

                @Override // android.support.v7.app.ActionBarActivityDelegate
/* 321 */       public View onCreatePanelView(int featureId) {
/* 324 */           if (featureId != 0 || !preparePanel()) {
/* 322 */               return null;
                    }
/* 325 */           View createdPanelView = (View) getListMenuView(this.mActivity, this);
/* 328 */           return createdPanelView;
                }

                @Override // android.support.v7.app.ActionBarActivityDelegate
/* 332 */       public boolean onCreatePanelMenu(int featureId, Menu menu) {
/* 333 */           if (featureId != 0) {
/* 334 */               return this.mActivity.superOnCreatePanelMenu(featureId, menu);
                    }
                    return false;
                }

                @Override // android.support.v7.app.ActionBarActivityDelegate
/* 340 */       public boolean onPreparePanel(int featureId, View view, Menu menu) {
/* 341 */           if (featureId != 0) {
/* 342 */               return this.mActivity.superOnPreparePanel(featureId, view, menu);
                    }
                    return false;
                }

                @Override // android.support.v7.app.ActionBarActivityDelegate
/* 348 */       public boolean onMenuItemSelected(int featureId, MenuItem item) {
/* 349 */           if (featureId == 0) {
/* 350 */               item = MenuWrapperFactory.createMenuItemWrapper(item);
                    }
/* 352 */           return this.mActivity.superOnMenuItemSelected(featureId, item);
                }

                @Override // android.support.v7.internal.view.menu.MenuBuilder.Callback
/* 356 */       public boolean onMenuItemSelected(MenuBuilder menu, MenuItem item) {
/* 357 */           return this.mActivity.onMenuItemSelected(0, item);
                }

                @Override // android.support.v7.internal.view.menu.MenuBuilder.Callback
/* 361 */       public void onMenuModeChange(MenuBuilder menu) {
/* 362 */           reopenMenu(menu, true);
                }

                @Override // android.support.v7.internal.view.menu.MenuPresenter.Callback
/* 366 */       public void onCloseMenu(MenuBuilder menu, boolean allMenusAreClosing) {
/* 367 */           if (!this.mClosingActionMenu) {
/* 370 */               this.mClosingActionMenu = true;
/* 371 */               this.mActivity.closeOptionsMenu();
/* 372 */               this.mActionBarView.dismissPopupMenus();
/* 373 */               this.mClosingActionMenu = false;
                    }
                }

                @Override // android.support.v7.internal.view.menu.MenuPresenter.Callback
/* 377 */       public boolean onOpenSubMenu(MenuBuilder subMenu) {
/* 378 */           return false;
                }

                @Override // android.support.v7.app.ActionBarActivityDelegate
/* 382 */       public ActionMode startSupportActionMode(ActionMode.Callback callback) {
/* 383 */           if (callback == null) {
/* 384 */               throw new IllegalArgumentException("ActionMode callback can not be null.");
                    }
/* 387 */           if (this.mActionMode != null) {
/* 388 */               this.mActionMode.finish();
                    }
/* 391 */           ActionMode.Callback wrappedCallback = new ActionModeCallbackWrapper(callback);
/* 393 */           ActionBarImplBase ab = (ActionBarImplBase) getSupportActionBar();
/* 394 */           if (ab != null) {
/* 395 */               this.mActionMode = ab.startActionMode(wrappedCallback);
                    }
/* 398 */           if (this.mActionMode != null) {
/* 399 */               this.mActivity.onSupportActionModeStarted(this.mActionMode);
                    }
/* 401 */           return this.mActionMode;
                }

                @Override // android.support.v7.app.ActionBarActivityDelegate
/* 405 */       public void supportInvalidateOptionsMenu() {
/* 406 */           if (this.mMenu != null) {
/* 407 */               Bundle savedActionViewStates = new Bundle();
/* 408 */               this.mMenu.saveActionViewStates(savedActionViewStates);
/* 409 */               if (savedActionViewStates.size() > 0) {
/* 410 */                   this.mPanelFrozenActionViewState = savedActionViewStates;
                        }
/* 413 */               this.mMenu.stopDispatchingItemsChanged();
/* 414 */               this.mMenu.clear();
                    }
/* 416 */           this.mPanelRefreshContent = true;
/* 419 */           if (this.mActionBarView != null) {
/* 420 */               this.mPanelIsPrepared = false;
/* 421 */               preparePanel();
                    }
                }

/* 425 */       private void reopenMenu(MenuBuilder menu, boolean toggleMenuMode) {
/* 426 */           if (this.mActionBarView != null && this.mActionBarView.isOverflowReserved()) {
/* 427 */               if (!this.mActionBarView.isOverflowMenuShowing() || !toggleMenuMode) {
/* 428 */                   if (this.mActionBarView.getVisibility() == 0) {
/* 429 */                       this.mActionBarView.showOverflowMenu();
/* 438 */                       return;
                            }
/* 438 */                   return;
                        }
/* 432 */               this.mActionBarView.hideOverflowMenu();
/* 438 */               return;
                    }
/* 437 */           menu.close();
                }

/* 440 */       private MenuView getListMenuView(Context context, MenuPresenter.Callback cb) {
/* 441 */           if (this.mMenu == null) {
/* 442 */               return null;
                    }
/* 445 */           if (this.mListMenuPresenter == null) {
/* 446 */               TypedArray a = context.obtainStyledAttributes(C0084R.styleable.Theme);
/* 447 */               int listPresenterTheme = a.getResourceId(4, C0084R.style.Theme_AppCompat_CompactMenu);
/* 450 */               a.recycle();
/* 452 */               this.mListMenuPresenter = new ListMenuPresenter(C0084R.layout.abc_list_menu_item_layout, listPresenterTheme);
/* 454 */               this.mListMenuPresenter.setCallback(cb);
/* 455 */               this.mMenu.addMenuPresenter(this.mListMenuPresenter);
                    } else {
/* 458 */               this.mListMenuPresenter.updateMenuView(false);
                    }
/* 461 */           return this.mListMenuPresenter.getMenuView(new FrameLayout(context));
                }

                @Override // android.support.v7.app.ActionBarActivityDelegate
/* 466 */       public boolean onBackPressed() {
/* 467 */           if (this.mActionMode != null) {
/* 468 */               this.mActionMode.finish();
                        return true;
                    }
/* 473 */           if (this.mActionBarView != null && this.mActionBarView.hasExpandedActionView()) {
/* 474 */               this.mActionBarView.collapseActionView();
                        return true;
                    }
/* 478 */           return false;
                }

                @Override // android.support.v7.app.ActionBarActivityDelegate
/* 482 */       void setSupportProgressBarVisibility(boolean visible) {
/* 483 */           updateProgressBars(visible ? -1 : -2);
                }

                @Override // android.support.v7.app.ActionBarActivityDelegate
/* 488 */       void setSupportProgressBarIndeterminateVisibility(boolean visible) {
/* 489 */           updateProgressBars(visible ? -1 : -2);
                }

                @Override // android.support.v7.app.ActionBarActivityDelegate
/* 494 */       void setSupportProgressBarIndeterminate(boolean indeterminate) {
/* 495 */           updateProgressBars(indeterminate ? -3 : -4);
                }

                @Override // android.support.v7.app.ActionBarActivityDelegate
/* 500 */       void setSupportProgress(int progress) {
/* 501 */           updateProgressBars(progress + 0);
                }

                @Override // android.support.v7.app.ActionBarActivityDelegate
/* 505 */       int getHomeAsUpIndicatorAttrId() {
/* 506 */           return C0084R.attr.homeAsUpIndicator;
                }

/* 512 */       private void updateProgressBars(int value) {
/* 513 */           ProgressBarICS circularProgressBar = getCircularProgressBar();
/* 514 */           ProgressBarICS horizontalProgressBar = getHorizontalProgressBar();
/* 516 */           if (value == -1) {
/* 517 */               if (this.mFeatureProgress) {
/* 518 */                   int level = horizontalProgressBar.getProgress();
/* 519 */                   int visibility = (horizontalProgressBar.isIndeterminate() || level < 10000) ? 0 : 4;
/* 521 */                   horizontalProgressBar.setVisibility(visibility);
                        }
/* 523 */               if (this.mFeatureIndeterminateProgress) {
/* 524 */                   circularProgressBar.setVisibility(0);
/* 549 */                   return;
                        }
/* 549 */               return;
                    }
/* 526 */           if (value == -2) {
/* 527 */               if (this.mFeatureProgress) {
/* 528 */                   horizontalProgressBar.setVisibility(8);
                        }
/* 530 */               if (this.mFeatureIndeterminateProgress) {
/* 531 */                   circularProgressBar.setVisibility(8);
/* 549 */                   return;
                        }
/* 549 */               return;
                    }
/* 533 */           if (value == -3) {
/* 534 */               horizontalProgressBar.setIndeterminate(true);
/* 549 */               return;
                    }
/* 535 */           if (value == -4) {
/* 536 */               horizontalProgressBar.setIndeterminate(false);
/* 549 */               return;
                    }
/* 537 */           if (value >= 0 && value <= 10000) {
/* 541 */               horizontalProgressBar.setProgress(value + 0);
/* 543 */               if (value < 10000) {
/* 544 */                   showProgressBars(horizontalProgressBar, circularProgressBar);
                        } else {
/* 546 */                   hideProgressBars(horizontalProgressBar, circularProgressBar);
                        }
                    }
                }

/* 552 */       private void showProgressBars(ProgressBarICS horizontalProgressBar, ProgressBarICS spinnyProgressBar) {
/* 553 */           if (this.mFeatureIndeterminateProgress && spinnyProgressBar.getVisibility() == 4) {
/* 554 */               spinnyProgressBar.setVisibility(0);
                    }
/* 557 */           if (this.mFeatureProgress && horizontalProgressBar.getProgress() < 10000) {
/* 558 */               horizontalProgressBar.setVisibility(0);
                    }
                }

/* 563 */       private void hideProgressBars(ProgressBarICS horizontalProgressBar, ProgressBarICS spinnyProgressBar) {
/* 564 */           if (this.mFeatureIndeterminateProgress && spinnyProgressBar.getVisibility() == 0) {
/* 565 */               spinnyProgressBar.setVisibility(4);
                    }
/* 567 */           if (this.mFeatureProgress && horizontalProgressBar.getVisibility() == 0) {
/* 568 */               horizontalProgressBar.setVisibility(4);
                    }
                }

/* 572 */       private ProgressBarICS getCircularProgressBar() {
/* 573 */           ProgressBarICS pb = (ProgressBarICS) this.mActionBarView.findViewById(C0084R.id.progress_circular);
/* 574 */           if (pb != null) {
/* 575 */               pb.setVisibility(4);
                    }
/* 577 */           return pb;
                }

/* 580 */       private ProgressBarICS getHorizontalProgressBar() {
/* 581 */           ProgressBarICS pb = (ProgressBarICS) this.mActionBarView.findViewById(C0084R.id.progress_horizontal);
/* 582 */           if (pb != null) {
/* 583 */               pb.setVisibility(4);
                    }
/* 585 */           return pb;
                }

/* 588 */       private boolean initializePanelMenu() {
/* 589 */           this.mMenu = new MenuBuilder(getActionBarThemedContext());
/* 590 */           this.mMenu.setCallback(this);
/* 591 */           return true;
                }

/* 595 */       private boolean preparePanel() {
/* 596 */           if (this.mPanelIsPrepared) {
                        return true;
                    }
/* 601 */           if (this.mMenu == null || this.mPanelRefreshContent) {
/* 602 */               if (this.mMenu == null && (!initializePanelMenu() || this.mMenu == null)) {
                            return false;
                        }
/* 608 */               if (this.mActionBarView != null) {
/* 609 */                   this.mActionBarView.setMenu(this.mMenu, this);
                        }
/* 614 */               this.mMenu.stopDispatchingItemsChanged();
/* 617 */               if (!this.mActivity.superOnCreatePanelMenu(0, this.mMenu)) {
/* 619 */                   this.mMenu = null;
/* 621 */                   if (this.mActionBarView != null) {
/* 623 */                       this.mActionBarView.setMenu(null, this);
                            }
                            return false;
                        }
/* 629 */               this.mPanelRefreshContent = false;
                    }
/* 634 */           this.mMenu.stopDispatchingItemsChanged();
/* 638 */           if (this.mPanelFrozenActionViewState != null) {
/* 639 */               this.mMenu.restoreActionViewStates(this.mPanelFrozenActionViewState);
/* 640 */               this.mPanelFrozenActionViewState = null;
                    }
/* 644 */           if (!this.mActivity.superOnPreparePanel(0, null, this.mMenu)) {
/* 645 */               if (this.mActionBarView != null) {
/* 648 */                   this.mActionBarView.setMenu(null, this);
                        }
/* 650 */               this.mMenu.startDispatchingItemsChanged();
                        return false;
                    }
/* 654 */           this.mMenu.startDispatchingItemsChanged();
/* 657 */           this.mPanelIsPrepared = true;
                    return true;
                }

/* 666 */       private class ActionModeCallbackWrapper implements ActionMode.Callback {
                    private ActionMode.Callback mWrapped;

/* 667 */           public ActionModeCallbackWrapper(ActionMode.Callback wrapped) {
/* 669 */               this.mWrapped = wrapped;
                    }

                    @Override // android.support.v7.view.ActionMode.Callback
/* 672 */           public boolean onCreateActionMode(ActionMode mode, Menu menu) {
/* 673 */               return this.mWrapped.onCreateActionMode(mode, menu);
                    }

                    @Override // android.support.v7.view.ActionMode.Callback
/* 676 */           public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
/* 677 */               return this.mWrapped.onPrepareActionMode(mode, menu);
                    }

                    @Override // android.support.v7.view.ActionMode.Callback
/* 680 */           public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
/* 681 */               return this.mWrapped.onActionItemClicked(mode, item);
                    }

                    @Override // android.support.v7.view.ActionMode.Callback
/* 684 */           public void onDestroyActionMode(ActionMode mode) {
/* 685 */               this.mWrapped.onDestroyActionMode(mode);
/* 686 */               ActionBarActivityDelegateBase.this.mActivity.onSupportActionModeFinished(mode);
/* 687 */               ActionBarActivityDelegateBase.access$002(ActionBarActivityDelegateBase.this, null);
                    }
                }
            }
