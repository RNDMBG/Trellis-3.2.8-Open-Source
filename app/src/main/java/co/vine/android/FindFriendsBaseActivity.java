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
            
            
    public class FindFriendsBaseActivity extends BaseControllerActionBarActivity implements TabHost.OnTabChangeListener, IconTabHost.OnTabClickedListener {
                public static final String EXTRA_IS_TWITTER_REG = "is_twitter_reg";
                private static final String STATE_CURRENT_TAB = "currentTab";
                public static final String TAG_ADDRESS_BOOK = "address";
                public static final String TAG_SEARCH = "search";
                public static final String TAG_TWITTER = "twitter";
                protected ViewPagerScrollBar mScrollBar;
                protected IconTabHost mTabHost;
                protected TabsAdapter mTabsAdapter;
                protected ViewPager mViewPager;

                @Override 
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState, C0257R.layout.main_find_friends, true);
            if (BuildUtil.isExplore()) {
                setupTabs();
                    }
            this.mScrollBar = (ViewPagerScrollBar) findViewById(C0257R.id.scrollbar);
            Intent intent = getIntent();
            if (intent != null && intent.getBooleanExtra(EXTRA_IS_TWITTER_REG, false)) {
                this.mScrollBar.setColorIds(C0257R.array.find_friends_colors_twitter_reg);
                    }
            this.mViewPager = (ViewPager) findViewById(C0257R.id.pager);
            this.mViewPager.setPageMargin(getResources().getDimensionPixelSize(C0257R.dimen.home_pager_margin));
            this.mViewPager.setPageMarginDrawable(C0257R.color.bg_grouped_list);
                }

                @Override 
        protected void onRestoreInstanceState(@NonNull Bundle state) {
            super.onRestoreInstanceState(state);
            String cur = state.getString(STATE_CURRENT_TAB);
            if (cur != null) {
                setCurrentTabByTag(cur);
                    }
                }

                @Override 
        protected void onSaveInstanceState(Bundle outState) {
            super.onSaveInstanceState(outState);
            String cur = this.mTabHost.getCurrentTabTag();
            if (cur != null) {
                outState.putString(STATE_CURRENT_TAB, cur);
                    }
                }

        private void setupTabs() {
            this.mTabHost = (IconTabHost) findViewById(android.R.id.tabhost);
            if (this.mTabHost == null) {
                throw new RuntimeException("Your content must have a TabHost whose id attribute is 'android.R.id.tabhost'");
                    }
            this.mTabHost.setup();
            this.mViewPager = (ViewPager) findViewById(C0257R.id.pager);
                }

                @Override 
        public void onSupportContentChanged() {
            super.onSupportContentChanged();
            if (!BuildUtil.isExplore()) {
                setupTabs();
                    }
                }

        public void onCurrentTabClicked() {
                }

        void setCurrentTabByTag(String tag) {
            TabHost tabHost = this.mTabHost;
           if (!tag.equals(tabHost.getCurrentTabTag())) {
               tabHost.setCurrentTabByTag(tag);
                    }
           this.mScrollBar.setPosition(this.mViewPager.getCurrentItem());
                }

                @Override 
       public void onTabChanged(String tag) {
           this.mViewPager.setCurrentItem(this.mTabHost.getCurrentTab());
                }

                @Override 
       protected void onDestroy() {
           super.onDestroy();
           int currentIndex = this.mTabHost.getCurrentTab();
           ComponentCallbacks componentCallbacksFragment = this.mTabsAdapter.getTabs().get(currentIndex).fragment();
           if (componentCallbacksFragment instanceof OnTabChangedListener) {
               ((OnTabChangedListener) componentCallbacksFragment).onMoveAway(currentIndex);
                    }
                }

                @Override 
       protected void onActivityResult(int requestCode, int resultCode, Intent data) {
                    switch (requestCode) {
                        case 1:
                        case 2:
                   Fragment frag = this.mTabsAdapter.getTab(1).fragment();
                   if (frag != null) {
                       frag.onActivityResult(requestCode, resultCode, data);
                            }
                            break;
                    }
                }
            }
