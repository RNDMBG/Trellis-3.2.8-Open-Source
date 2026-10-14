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
            
            
    class ActionBarImplBase extends ActionBar {
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

        static  ScrollingTabContainerView access$000(ActionBarImplBase x0) {
            return x0.mTabScrollView;
                }

        static  Context access$100(ActionBarImplBase x0) {
            return x0.mContext;
                }

        static  boolean access$200(ActionBarImplBase x0) {
            return x0.mHiddenByApp;
                }

        static  boolean access$300(ActionBarImplBase x0) {
            return x0.mHiddenBySystem;
                }

        static  boolean access$400(boolean x0, boolean x1, boolean x2) {
            return checkShowingFlags(x0, x1, x2);
                }

        static  ActionBarContextView access$500(ActionBarImplBase x0) {
            return x0.mContextView;
                }

        static  ActionBarView access$600(ActionBarImplBase x0) {
            return x0.mActionView;
                }

       public ActionBarImplBase(ActionBarActivity activity, ActionBar.Callback callback) {
           this.mActivity = activity;
           this.mContext = activity;
           this.mCallback = callback;
           init(this.mActivity);
                }

       private void init(ActionBarActivity activity) {
           this.mOverlayLayout = (ActionBarOverlayLayout) activity.findViewById(C0084R.id.action_bar_overlay_layout);
           if (this.mOverlayLayout != null) {
               this.mOverlayLayout.setActionBar(this);
                    }
           this.mActionView = (ActionBarView) activity.findViewById(C0084R.id.action_bar);
           this.mContextView = (ActionBarContextView) activity.findViewById(C0084R.id.action_context_bar);
           this.mContainerView = (ActionBarContainer) activity.findViewById(C0084R.id.action_bar_container);
           this.mTopVisibilityView = (ViewGroup) activity.findViewById(C0084R.id.top_action_bar);
           if (this.mTopVisibilityView == null) {
               this.mTopVisibilityView = this.mContainerView;
                    }
           this.mSplitView = (ActionBarContainer) activity.findViewById(C0084R.id.split_action_bar);
           if (this.mActionView == null || this.mContextView == null || this.mContainerView == null) {
               throw new IllegalStateException(getClass().getSimpleName() + " can only be used with a compatible window decor layout");
                    }
           this.mActionView.setContextView(this.mContextView);
           this.mContextDisplayMode = this.mActionView.isSplitActionBar() ? 1 : 0;
           int current = this.mActionView.getDisplayOptions();
           boolean homeAsUp = (current & 4) != 0;
           if (homeAsUp) {
               this.mDisplayHomeAsUpSet = true;
                    }
           ActionBarPolicy abp = ActionBarPolicy.get(this.mContext);
           setHomeButtonEnabled(abp.enableHomeButtonByDefault() || homeAsUp);
           setHasEmbeddedTabs(abp.hasEmbeddedTabs());
           setTitle(this.mActivity.getTitle());
                }

       public void onConfigurationChanged(Configuration newConfig) {
           setHasEmbeddedTabs(ActionBarPolicy.get(this.mContext).hasEmbeddedTabs());
                }

       private void setHasEmbeddedTabs(boolean hasEmbeddedTabs) {
           this.mHasEmbeddedTabs = hasEmbeddedTabs;
           if (!this.mHasEmbeddedTabs) {
               this.mActionView.setEmbeddedTabView(null);
               this.mContainerView.setTabContainer(this.mTabScrollView);
                    } else {
               this.mContainerView.setTabContainer(null);
               this.mActionView.setEmbeddedTabView(this.mTabScrollView);
                    }
           boolean isInTabMode = getNavigationMode() == 2;
           if (this.mTabScrollView != null) {
               if (isInTabMode) {
                   this.mTabScrollView.setVisibility(0);
                        } else {
                   this.mTabScrollView.setVisibility(8);
                        }
                    }
           this.mActionView.setCollapsable(!this.mHasEmbeddedTabs && isInTabMode);
                }

       public boolean hasNonEmbeddedTabs() {
                    return !this.mHasEmbeddedTabs && getNavigationMode() == 2;
                }

                @Override 
       public void setCustomView(View view) {
           this.mActionView.setCustomNavigationView(view);
                }

                @Override 
       public void setCustomView(View view, ActionBar.LayoutParams layoutParams) {
           view.setLayoutParams(layoutParams);
           this.mActionView.setCustomNavigationView(view);
                }

                @Override 
       public void setCustomView(int resId) {
           setCustomView(LayoutInflater.from(getThemedContext()).inflate(resId, (ViewGroup) this.mActionView, false));
                }

                @Override 
       public void setIcon(int resId) {
           this.mActionView.setIcon(resId);
                }

                @Override 
       public void setIcon(Drawable icon) {
           this.mActionView.setIcon(icon);
                }

                @Override 
       public void setLogo(int resId) {
           this.mActionView.setLogo(resId);
                }

                @Override 
       public void setLogo(Drawable logo) {
           this.mActionView.setLogo(logo);
                }

                @Override 
       public void setListNavigationCallbacks(SpinnerAdapter adapter, ActionBar.OnNavigationListener callback) {
           this.mActionView.setDropdownAdapter(adapter);
           this.mActionView.setCallback(callback);
                }

                @Override 
       public void setSelectedNavigationItem(int position) {
                    switch (this.mActionView.getNavigationMode()) {
                        case 1:
                   this.mActionView.setDropdownSelectedPosition(position);
                   return;
                        case 2:
                   selectTab(this.mTabs.get(position));
                   return;
                        default:
                   throw new IllegalStateException("setSelectedNavigationIndex not valid for current navigation mode");
                    }
                }

                @Override 
       public int getSelectedNavigationIndex() {
                    switch (this.mActionView.getNavigationMode()) {
                        case 1:
                   return this.mActionView.getDropdownSelectedPosition();
                        case 2:
                   if (this.mSelectedTab != null) {
                                return this.mSelectedTab.getPosition();
                            }
                            return -1;
                        default:
                            return -1;
                    }
                }

                @Override 
       public int getNavigationItemCount() {
                    switch (this.mActionView.getNavigationMode()) {
                        case 1:
                   SpinnerAdapter adapter = this.mActionView.getDropdownAdapter();
                   if (adapter != null) {
                                return adapter.getCount();
                            }
                            return 0;
                        case 2:
                   return this.mTabs.size();
                        default:
                            return 0;
                    }
                }

                @Override 
       public void setTitle(CharSequence title) {
           this.mActionView.setTitle(title);
                }

                @Override 
       public void setTitle(int resId) {
           setTitle(this.mContext.getString(resId));
                }

                @Override 
       public void setSubtitle(CharSequence subtitle) {
           this.mActionView.setSubtitle(subtitle);
                }

                @Override 
       public void setSubtitle(int resId) {
           setSubtitle(this.mContext.getString(resId));
                }

                @Override 
       public void setDisplayOptions(int options) {
           if ((options & 4) != 0) {
               this.mDisplayHomeAsUpSet = true;
                    }
           this.mActionView.setDisplayOptions(options);
                }

                @Override 
       public void setDisplayOptions(int options, int mask) {
           int current = this.mActionView.getDisplayOptions();
           if ((mask & 4) != 0) {
               this.mDisplayHomeAsUpSet = true;
                    }
           this.mActionView.setDisplayOptions((options & mask) | ((mask ^ (-1)) & current));
                }

                @Override 
       public void setDisplayUseLogoEnabled(boolean useLogo) {
           setDisplayOptions(useLogo ? 1 : 0, 1);
                }

                @Override 
       public void setDisplayShowHomeEnabled(boolean showHome) {
           setDisplayOptions(showHome ? 2 : 0, 2);
                }

                @Override 
       public void setDisplayHomeAsUpEnabled(boolean showHomeAsUp) {
           setDisplayOptions(showHomeAsUp ? 4 : 0, 4);
                }

                @Override 
       public void setDisplayShowTitleEnabled(boolean showTitle) {
           setDisplayOptions(showTitle ? 8 : 0, 8);
                }

                @Override 
       public void setDisplayShowCustomEnabled(boolean showCustom) {
           setDisplayOptions(showCustom ? 16 : 0, 16);
                }

                @Override 
       public void setHomeButtonEnabled(boolean enable) {
           this.mActionView.setHomeButtonEnabled(enable);
                }

                @Override 
       public void setBackgroundDrawable(Drawable d) {
           this.mContainerView.setPrimaryBackground(d);
                }

                @Override 
       public void setStackedBackgroundDrawable(Drawable d) {
           this.mContainerView.setStackedBackground(d);
                }

                @Override 
       public void setSplitBackgroundDrawable(Drawable d) {
           this.mContainerView.setSplitBackground(d);
                }

                @Override 
       public View getCustomView() {
           return this.mActionView.getCustomNavigationView();
                }

                @Override 
       public CharSequence getTitle() {
           return this.mActionView.getTitle();
                }

                @Override 
       public CharSequence getSubtitle() {
           return this.mActionView.getSubtitle();
                }

                @Override 
       public int getNavigationMode() {
           return this.mActionView.getNavigationMode();
                }

                @Override 
       public void setNavigationMode(int mode) {
                    boolean z = false;
           int oldMode = this.mActionView.getNavigationMode();
                    switch (oldMode) {
                        case 2:
                   this.mSavedTabPosition = getSelectedNavigationIndex();
                   selectTab(null);
                   this.mTabScrollView.setVisibility(8);
                            break;
                    }
           this.mActionView.setNavigationMode(mode);
                    switch (mode) {
                        case 2:
                   ensureTabsExist();
                   this.mTabScrollView.setVisibility(0);
                   if (this.mSavedTabPosition != -1) {
                       setSelectedNavigationItem(this.mSavedTabPosition);
                       this.mSavedTabPosition = -1;
                            }
                            break;
                    }
           ActionBarView actionBarView = this.mActionView;
                    if (mode == 2 && !this.mHasEmbeddedTabs) {
                        z = true;
                    }
                    actionBarView.setCollapsable(z);
                }

                @Override 
       public int getDisplayOptions() {
           return this.mActionView.getDisplayOptions();
                }

                @Override 
       public ActionBar.Tab newTab() {
           return new TabImpl();
                }

                @Override 
       public void addTab(ActionBar.Tab tab) {
           addTab(tab, this.mTabs.isEmpty());
                }

                @Override 
       public void addTab(ActionBar.Tab tab, boolean setSelected) {
           ensureTabsExist();
           this.mTabScrollView.addTab(tab, setSelected);
           configureTab(tab, this.mTabs.size());
           if (setSelected) {
               selectTab(tab);
                    }
                }

                @Override 
       public void addTab(ActionBar.Tab tab, int position) {
           addTab(tab, position, this.mTabs.isEmpty());
                }

                @Override 
       public void addTab(ActionBar.Tab tab, int position, boolean setSelected) {
           ensureTabsExist();
           this.mTabScrollView.addTab(tab, position, setSelected);
           configureTab(tab, position);
           if (setSelected) {
               selectTab(tab);
                    }
                }

                @Override 
       public void removeTab(ActionBar.Tab tab) {
           removeTabAt(tab.getPosition());
                }

                @Override 
       public void removeTabAt(int position) {
           if (this.mTabScrollView != null) {
               int selectedTabPosition = this.mSelectedTab != null ? this.mSelectedTab.getPosition() : this.mSavedTabPosition;
               this.mTabScrollView.removeTabAt(position);
               TabImpl removedTab = this.mTabs.remove(position);
               if (removedTab != null) {
                   removedTab.setPosition(-1);
                        }
               int newTabCount = this.mTabs.size();
                        for (int i = position; i < newTabCount; i++) {
                   this.mTabs.get(i).setPosition(i);
                        }
               if (selectedTabPosition == position) {
                   selectTab(this.mTabs.isEmpty() ? null : this.mTabs.get(Math.max(0, position - 1)));
                        }
                    }
                }

                @Override 
       public void removeAllTabs() {
           cleanupTabs();
                }

                @Override 
       public void selectTab(ActionBar.Tab tab) {
           if (getNavigationMode() != 2) {
               this.mSavedTabPosition = tab != null ? tab.getPosition() : -1;
               return;
                    }
           FragmentTransaction trans = this.mActivity.getSupportFragmentManager().beginTransaction().disallowAddToBackStack();
           if (this.mSelectedTab == tab) {
               if (this.mSelectedTab != null) {
                   this.mSelectedTab.getCallback().onTabReselected(this.mSelectedTab, trans);
                   this.mTabScrollView.animateToTab(tab.getPosition());
                        }
                    } else {
               this.mTabScrollView.setTabSelected(tab != null ? tab.getPosition() : -1);
               if (this.mSelectedTab != null) {
                   this.mSelectedTab.getCallback().onTabUnselected(this.mSelectedTab, trans);
                        }
               this.mSelectedTab = (TabImpl) tab;
               if (this.mSelectedTab != null) {
                   this.mSelectedTab.getCallback().onTabSelected(this.mSelectedTab, trans);
                        }
                    }
           if (!trans.isEmpty()) {
               trans.commit();
                    }
                }

                @Override 
       public ActionBar.Tab getSelectedTab() {
           return this.mSelectedTab;
                }

                @Override 
       public ActionBar.Tab getTabAt(int index) {
           return this.mTabs.get(index);
                }

                @Override 
       public int getTabCount() {
           return this.mTabs.size();
                }

                @Override 
       public Context getThemedContext() {
           if (this.mThemedContext == null) {
               TypedValue outValue = new TypedValue();
               Resources.Theme currentTheme = this.mContext.getTheme();
               currentTheme.resolveAttribute(C0084R.attr.actionBarWidgetTheme, outValue, true);
               int targetThemeRes = outValue.resourceId;
               if (targetThemeRes != 0) {
                   this.mThemedContext = new ContextThemeWrapper(this.mContext, targetThemeRes);
                        } else {
                   this.mThemedContext = this.mContext;
                        }
                    }
           return this.mThemedContext;
                }

                @Override 
       public void setHomeAsUpIndicator(Drawable indicator) {
           this.mActionView.setHomeAsUpIndicator(indicator);
                }

                @Override 
       public void setHomeAsUpIndicator(int resId) {
           this.mActionView.setHomeAsUpIndicator(resId);
                }

                @Override 
       public int getHeight() {
           return this.mContainerView.getHeight();
                }

                @Override 
       public void show() {
           if (this.mHiddenByApp) {
               this.mHiddenByApp = false;
               updateVisibility(false);
                    }
                }

       void showForActionMode() {
           if (!this.mShowingForMode) {
               this.mShowingForMode = true;
               updateVisibility(false);
                    }
                }

                @Override 
       public void hide() {
           if (!this.mHiddenByApp) {
               this.mHiddenByApp = true;
               updateVisibility(false);
                    }
                }

       void hideForActionMode() {
           if (this.mShowingForMode) {
               this.mShowingForMode = false;
               updateVisibility(false);
                    }
                }

                @Override 
       public boolean isShowing() {
           return this.mNowShowing;
                }

                @Override 
       public void addOnMenuVisibilityListener(ActionBar.OnMenuVisibilityListener listener) {
           this.mMenuVisibilityListeners.add(listener);
                }

                @Override 
       public void removeOnMenuVisibilityListener(ActionBar.OnMenuVisibilityListener listener) {
           this.mMenuVisibilityListeners.remove(listener);
                }

       public ActionMode startActionMode(ActionMode.Callback callback) {
           if (this.mActionMode != null) {
               this.mActionMode.finish();
                    }
           this.mContextView.killMode();
           ActionModeImpl mode = new ActionModeImpl(callback);
           if (!mode.dispatchOnCreate()) {
                        return null;
                    }
           mode.invalidate();
           this.mContextView.initForMode(mode);
           animateToMode(true);
           if (this.mSplitView != null && this.mContextDisplayMode == 1 && this.mSplitView.getVisibility() != 0) {
               this.mSplitView.setVisibility(0);
                    }
           this.mContextView.sendAccessibilityEvent(32);
           this.mActionMode = mode;
           return mode;
                }

       void animateToMode(boolean toActionMode) {
           if (toActionMode) {
               showForActionMode();
                    } else {
               hideForActionMode();
                    }
           this.mActionView.animateToVisibility(toActionMode ? 4 : 0);
           this.mContextView.animateToVisibility(toActionMode ? 0 : 8);
           if (this.mTabScrollView != null && !this.mActionView.hasEmbeddedTabs() && this.mActionView.isCollapsed()) {
               this.mTabScrollView.setVisibility(toActionMode ? 8 : 0);
                    }
                }

       public class TabImpl extends ActionBar.Tab {
                    private ActionBar.TabListener mCallback;
                    private CharSequence mContentDesc;
                    private View mCustomView;
                    private Drawable mIcon;
                    private int mPosition = -1;
                    private Object mTag;
                    private CharSequence mText;

           public TabImpl() {
                    }

                    @Override 
           public Object getTag() {
               return this.mTag;
                    }

                    @Override 
           public ActionBar.Tab setTag(Object tag) {
               this.mTag = tag;
               return this;
                    }

           public ActionBar.TabListener getCallback() {
               return this.mCallback;
                    }

                    @Override 
           public ActionBar.Tab setTabListener(ActionBar.TabListener callback) {
               this.mCallback = callback;
               return this;
                    }

                    @Override 
           public View getCustomView() {
               return this.mCustomView;
                    }

                    @Override 
           public ActionBar.Tab setCustomView(View view) {
               this.mCustomView = view;
               if (this.mPosition >= 0) {
                   ActionBarImplBase.access$000(ActionBarImplBase.this).updateTab(this.mPosition);
                        }
               return this;
                    }

                    @Override 
           public ActionBar.Tab setCustomView(int layoutResId) {
               return setCustomView(LayoutInflater.from(ActionBarImplBase.this.getThemedContext()).inflate(layoutResId, (ViewGroup) null));
                    }

                    @Override 
           public Drawable getIcon() {
               return this.mIcon;
                    }

                    @Override 
           public int getPosition() {
               return this.mPosition;
                    }

           public void setPosition(int position) {
               this.mPosition = position;
                    }

                    @Override 
           public CharSequence getText() {
               return this.mText;
                    }

                    @Override 
           public ActionBar.Tab setIcon(Drawable icon) {
               this.mIcon = icon;
               if (this.mPosition >= 0) {
                   ActionBarImplBase.access$000(ActionBarImplBase.this).updateTab(this.mPosition);
                        }
               return this;
                    }

                    @Override 
           public ActionBar.Tab setIcon(int resId) {
               return setIcon(ActionBarImplBase.access$100(ActionBarImplBase.this).getResources().getDrawable(resId));
                    }

                    @Override 
           public ActionBar.Tab setText(CharSequence text) {
               this.mText = text;
               if (this.mPosition >= 0) {
                   ActionBarImplBase.access$000(ActionBarImplBase.this).updateTab(this.mPosition);
                        }
               return this;
                    }

                    @Override 
           public ActionBar.Tab setText(int resId) {
               return setText(ActionBarImplBase.access$100(ActionBarImplBase.this).getResources().getText(resId));
                    }

                    @Override 
           public void select() {
               ActionBarImplBase.this.selectTab(this);
                    }

                    @Override 
           public ActionBar.Tab setContentDescription(int resId) {
               return setContentDescription(ActionBarImplBase.access$100(ActionBarImplBase.this).getResources().getText(resId));
                    }

                    @Override 
           public ActionBar.Tab setContentDescription(CharSequence contentDesc) {
               this.mContentDesc = contentDesc;
               if (this.mPosition >= 0) {
                   ActionBarImplBase.access$000(ActionBarImplBase.this).updateTab(this.mPosition);
                        }
               return this;
                    }

                    @Override 
           public CharSequence getContentDescription() {
               return this.mContentDesc;
                    }
                }

       class ActionModeImpl extends ActionMode implements MenuBuilder.Callback {
                    private ActionMode.Callback mCallback;
                    private WeakReference<View> mCustomView;
                    private MenuBuilder mMenu;

           public ActionModeImpl(ActionMode.Callback callback) {
               this.mCallback = callback;
               this.mMenu = new MenuBuilder(ActionBarImplBase.this.getThemedContext()).setDefaultShowAsAction(1);
               this.mMenu.setCallback(this);
                    }

                    @Override 
           public MenuInflater getMenuInflater() {
               return new SupportMenuInflater(ActionBarImplBase.this.getThemedContext());
                    }

                    @Override 
           public Menu getMenu() {
               return this.mMenu;
                    }

                    @Override 
           public void finish() {
               if (ActionBarImplBase.this.mActionMode == this) {
                   if (!ActionBarImplBase.access$400(ActionBarImplBase.access$200(ActionBarImplBase.this), ActionBarImplBase.access$300(ActionBarImplBase.this), false)) {
                       ActionBarImplBase.this.mDeferredDestroyActionMode = this;
                       ActionBarImplBase.this.mDeferredModeDestroyCallback = this.mCallback;
                            } else {
                       this.mCallback.onDestroyActionMode(this);
                            }
                   this.mCallback = null;
                   ActionBarImplBase.this.animateToMode(false);
                   ActionBarImplBase.access$500(ActionBarImplBase.this).closeMode();
                   ActionBarImplBase.access$600(ActionBarImplBase.this).sendAccessibilityEvent(32);
                   ActionBarImplBase.this.mActionMode = null;
                        }
                    }

                    @Override 
           public void invalidate() {
               this.mMenu.stopDispatchingItemsChanged();
                        try {
                   this.mCallback.onPrepareActionMode(this, this.mMenu);
                   this.mMenu.startDispatchingItemsChanged();
                        } catch (Throwable th) {
                            this.mMenu.startDispatchingItemsChanged();
                            throw th;
                        }
                    }

           public boolean dispatchOnCreate() {
               this.mMenu.stopDispatchingItemsChanged();
                        try {
                   boolean zOnCreateActionMode = this.mCallback.onCreateActionMode(this, this.mMenu);
                   this.mMenu.startDispatchingItemsChanged();
                            return zOnCreateActionMode;
                        } catch (Throwable th) {
                            this.mMenu.startDispatchingItemsChanged();
                            throw th;
                        }
                    }

                    @Override 
           public void setCustomView(View view) {
               ActionBarImplBase.access$500(ActionBarImplBase.this).setCustomView(view);
               this.mCustomView = new WeakReference<>(view);
                    }

                    @Override 
           public void setSubtitle(CharSequence subtitle) {
               ActionBarImplBase.access$500(ActionBarImplBase.this).setSubtitle(subtitle);
                    }

                    @Override 
           public void setTitle(CharSequence title) {
               ActionBarImplBase.access$500(ActionBarImplBase.this).setTitle(title);
                    }

                    @Override 
           public void setTitle(int resId) {
               setTitle(ActionBarImplBase.access$100(ActionBarImplBase.this).getResources().getString(resId));
                    }

                    @Override 
           public void setSubtitle(int resId) {
               setSubtitle(ActionBarImplBase.access$100(ActionBarImplBase.this).getResources().getString(resId));
                    }

                    @Override 
           public CharSequence getTitle() {
               return ActionBarImplBase.access$500(ActionBarImplBase.this).getTitle();
                    }

                    @Override 
           public CharSequence getSubtitle() {
               return ActionBarImplBase.access$500(ActionBarImplBase.this).getSubtitle();
                    }

                    @Override 
           public void setTitleOptionalHint(boolean titleOptional) {
               super.setTitleOptionalHint(titleOptional);
               ActionBarImplBase.access$500(ActionBarImplBase.this).setTitleOptional(titleOptional);
                    }

                    @Override 
           public boolean isTitleOptional() {
               return ActionBarImplBase.access$500(ActionBarImplBase.this).isTitleOptional();
                    }

                    @Override 
           public View getCustomView() {
               if (this.mCustomView != null) {
                            return this.mCustomView.get();
                        }
                        return null;
                    }

                    @Override 
           public boolean onMenuItemSelected(MenuBuilder menu, MenuItem item) {
               if (this.mCallback != null) {
                   return this.mCallback.onActionItemClicked(this, item);
                        }
                        return false;
                    }

                    @Override 
           public void onMenuModeChange(MenuBuilder menu) {
               if (this.mCallback != null) {
                   invalidate();
                   ActionBarImplBase.access$500(ActionBarImplBase.this).showOverflowMenu();
                        }
                    }

           public void onCloseMenu(MenuBuilder menu, boolean allMenusAreClosing) {
                    }

           public boolean onSubMenuSelected(SubMenuBuilder subMenu) {
               if (this.mCallback == null) {
                   return false;
                        }
               if (!subMenu.hasVisibleItems()) {
                        }
                        return true;
                    }

           public void onCloseSubMenu(SubMenuBuilder menu) {
                    }

           public void onMenuModeChange(Menu menu) {
               if (this.mCallback != null) {
                   invalidate();
                   ActionBarImplBase.access$500(ActionBarImplBase.this).showOverflowMenu();
                        }
                    }
                }

       private void ensureTabsExist() {
           if (this.mTabScrollView == null) {
               ScrollingTabContainerView tabScroller = new ScrollingTabContainerView(this.mContext);
               if (this.mHasEmbeddedTabs) {
                   tabScroller.setVisibility(0);
                   this.mActionView.setEmbeddedTabView(tabScroller);
                        } else {
                   if (getNavigationMode() == 2) {
                       tabScroller.setVisibility(0);
                            } else {
                       tabScroller.setVisibility(8);
                            }
                   this.mContainerView.setTabContainer(tabScroller);
                        }
               this.mTabScrollView = tabScroller;
                    }
                }

       private void configureTab(ActionBar.Tab tab, int position) {
           TabImpl tabi = (TabImpl) tab;
           ActionBar.TabListener callback = tabi.getCallback();
           if (callback == null) {
               throw new IllegalStateException("Action Bar Tab must have a Callback");
                    }
           tabi.setPosition(position);
           this.mTabs.add(position, tabi);
           int count = this.mTabs.size();
                    for (int i = position + 1; i < count; i++) {
               this.mTabs.get(i).setPosition(i);
                    }
                }

       private void cleanupTabs() {
           if (this.mSelectedTab != null) {
               selectTab(null);
                    }
           this.mTabs.clear();
           if (this.mTabScrollView != null) {
               this.mTabScrollView.removeAllTabs();
                    }
           this.mSavedTabPosition = -1;
                }

       private static boolean checkShowingFlags(boolean hiddenByApp, boolean hiddenBySystem, boolean showingForMode) {
           if (showingForMode) {
                        return true;
                    }
                    return (hiddenByApp || hiddenBySystem) ? false : true;
                }

       private void updateVisibility(boolean fromSystem) {
           boolean shown = checkShowingFlags(this.mHiddenByApp, this.mHiddenBySystem, this.mShowingForMode);
           if (shown) {
               if (!this.mNowShowing) {
                   this.mNowShowing = true;
                   doShow(fromSystem);
                   return;
                        }
               return;
                    }
           if (this.mNowShowing) {
               this.mNowShowing = false;
               doHide(fromSystem);
                    }
                }

       public void setShowHideAnimationEnabled(boolean enabled) {
           this.mShowHideAnimationEnabled = enabled;
           if (!enabled) {
               this.mTopVisibilityView.clearAnimation();
               if (this.mSplitView != null) {
                  this.mSplitView.clearAnimation();
                        }
                    }
                }

      public void doShow(boolean fromSystem) {
          this.mTopVisibilityView.clearAnimation();
          if (this.mTopVisibilityView.getVisibility() != 0) {
              boolean animate = isShowHideAnimationEnabled() || fromSystem;
              if (animate) {
                  Animation anim = AnimationUtils.loadAnimation(this.mContext, C0084R.anim.abc_slide_in_top);
                  this.mTopVisibilityView.startAnimation(anim);
                        }
              this.mTopVisibilityView.setVisibility(0);
              if (this.mSplitView != null && this.mSplitView.getVisibility() != 0) {
                  if (animate) {
                      Animation anim2 = AnimationUtils.loadAnimation(this.mContext, C0084R.anim.abc_slide_in_bottom);
                      this.mSplitView.startAnimation(anim2);
                            }
                  this.mSplitView.setVisibility(0);
                        }
                    }
                }

      public void doHide(boolean fromSystem) {
          this.mTopVisibilityView.clearAnimation();
          if (this.mTopVisibilityView.getVisibility() != 8) {
              boolean animate = isShowHideAnimationEnabled() || fromSystem;
              if (animate) {
                  Animation anim = AnimationUtils.loadAnimation(this.mContext, C0084R.anim.abc_slide_out_top);
                  this.mTopVisibilityView.startAnimation(anim);
                        }
              this.mTopVisibilityView.setVisibility(8);
              if (this.mSplitView != null && this.mSplitView.getVisibility() != 8) {
                  if (animate) {
                      Animation anim2 = AnimationUtils.loadAnimation(this.mContext, C0084R.anim.abc_slide_out_bottom);
                      this.mSplitView.startAnimation(anim2);
                            }
                  this.mSplitView.setVisibility(8);
                        }
                    }
                }

      boolean isShowHideAnimationEnabled() {
          return this.mShowHideAnimationEnabled;
                }
            }
