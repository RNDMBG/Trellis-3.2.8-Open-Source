            package co.vine.android;

            import android.content.ComponentCallbacks;
            import android.content.Intent;
            import android.os.Bundle;
            import android.support.annotation.NonNull;
            import android.support.v4.app.Fragment;
            import android.support.v4.view.ViewPager;
            import android.widget.TabHost;
            import co.vine.android.util.BuildUtil;
            import co.vine.android.widget.OnTabChangedListener;
            import co.vine.android.widget.tabs.IconTabHost;
            import co.vine.android.widget.tabs.TabsAdapter;
            import co.vine.android.widget.tabs.ViewPagerScrollBar;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 36 */    public class FindFriendsBaseActivity extends BaseControllerActionBarActivity implements TabHost.OnTabChangeListener, IconTabHost.OnTabClickedListener {
                public static final String EXTRA_IS_TWITTER_REG = "is_twitter_reg";
                private static final String STATE_CURRENT_TAB = "currentTab";
                public static final String TAG_ADDRESS_BOOK = "address";
                public static final String TAG_SEARCH = "search";
                public static final String TAG_TWITTER = "twitter";
                protected ViewPagerScrollBar mScrollBar;
                protected IconTabHost mTabHost;
                protected TabsAdapter mTabsAdapter;
                protected ViewPager mViewPager;

                @Override // co.vine.android.BaseActionBarActivity, android.support.v7.app.ActionBarActivity, android.support.v4.app.FragmentActivity, android.app.Activity
/* 37 */        protected void onCreate(Bundle savedInstanceState) {
/* 38 */            super.onCreate(savedInstanceState, C0257R.layout.main_find_friends, true);
/* 39 */            if (BuildUtil.isExplore()) {
/* 40 */                setupTabs();
                    }
/* 43 */            this.mScrollBar = (ViewPagerScrollBar) findViewById(C0257R.id.scrollbar);
/* 44 */            Intent intent = getIntent();
/* 45 */            if (intent != null && intent.getBooleanExtra(EXTRA_IS_TWITTER_REG, false)) {
/* 47 */                this.mScrollBar.setColorIds(C0257R.array.find_friends_colors_twitter_reg);
                    }
/* 51 */            this.mViewPager = (ViewPager) findViewById(C0257R.id.pager);
/* 52 */            this.mViewPager.setPageMargin(getResources().getDimensionPixelSize(C0257R.dimen.home_pager_margin));
/* 53 */            this.mViewPager.setPageMarginDrawable(C0257R.color.bg_grouped_list);
                }

                @Override // android.app.Activity
/* 57 */        protected void onRestoreInstanceState(@NonNull Bundle state) {
/* 58 */            super.onRestoreInstanceState(state);
/* 59 */            String cur = state.getString(STATE_CURRENT_TAB);
/* 60 */            if (cur != null) {
/* 61 */                setCurrentTabByTag(cur);
                    }
                }

                @Override // co.vine.android.BaseActionBarActivity, android.support.v4.app.FragmentActivity, android.app.Activity
/* 67 */        protected void onSaveInstanceState(Bundle outState) {
/* 68 */            super.onSaveInstanceState(outState);
/* 69 */            String cur = this.mTabHost.getCurrentTabTag();
/* 70 */            if (cur != null) {
/* 71 */                outState.putString(STATE_CURRENT_TAB, cur);
                    }
                }

/* 75 */        private void setupTabs() {
/* 76 */            this.mTabHost = (IconTabHost) findViewById(android.R.id.tabhost);
/* 77 */            if (this.mTabHost == null) {
/* 78 */                throw new RuntimeException("Your content must have a TabHost whose id attribute is 'android.R.id.tabhost'");
                    }
/* 82 */            this.mTabHost.setup();
/* 83 */            this.mViewPager = (ViewPager) findViewById(C0257R.id.pager);
                }

                @Override // android.support.v7.app.ActionBarActivity
/* 87 */        public void onSupportContentChanged() {
/* 88 */            super.onSupportContentChanged();
/* 89 */            if (!BuildUtil.isExplore()) {
/* 90 */                setupTabs();
                    }
                }

/* 95 */        public void onCurrentTabClicked() {
                }

/* 98 */        void setCurrentTabByTag(String tag) {
/* 99 */            TabHost tabHost = this.mTabHost;
/* 100 */           if (!tag.equals(tabHost.getCurrentTabTag())) {
/* 101 */               tabHost.setCurrentTabByTag(tag);
                    }
/* 103 */           this.mScrollBar.setPosition(this.mViewPager.getCurrentItem());
                }

                @Override // android.widget.TabHost.OnTabChangeListener
/* 106 */       public void onTabChanged(String tag) {
/* 107 */           this.mViewPager.setCurrentItem(this.mTabHost.getCurrentTab());
                }

                @Override // co.vine.android.BaseActionBarActivity, android.support.v4.app.FragmentActivity, android.app.Activity
/* 111 */       protected void onDestroy() {
/* 112 */           super.onDestroy();
/* 113 */           int currentIndex = this.mTabHost.getCurrentTab();
/* 114 */           ComponentCallbacks componentCallbacksFragment = this.mTabsAdapter.getTabs().get(currentIndex).fragment();
/* 115 */           if (componentCallbacksFragment instanceof OnTabChangedListener) {
/* 116 */               ((OnTabChangedListener) componentCallbacksFragment).onMoveAway(currentIndex);
                    }
                }

                @Override // co.vine.android.BaseActionBarActivity, android.support.v4.app.FragmentActivity, android.app.Activity
/* 121 */       protected void onActivityResult(int requestCode, int resultCode, Intent data) {
                    switch (requestCode) {
                        case 1:
                        case 2:
/* 125 */                   Fragment frag = this.mTabsAdapter.getTab(1).fragment();
/* 128 */                   if (frag != null) {
/* 129 */                       frag.onActivityResult(requestCode, resultCode, data);
                            }
                            break;
                    }
                }
            }
