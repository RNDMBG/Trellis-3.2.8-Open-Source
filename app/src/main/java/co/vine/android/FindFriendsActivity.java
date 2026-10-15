            package co.vine.android;

            import android.os.Bundle;
            import android.view.LayoutInflater;
            import android.widget.TabHost;
            import co.vine.android.client.AppSessionListener;
            import co.vine.android.widget.tabs.IconTabHost;
            import co.vine.android.widget.tabs.TabIndicator;
            import co.vine.android.widget.tabs.TabsAdapter;
            
            
    public class FindFriendsActivity extends FindFriendsBaseActivity implements IconTabHost.OnTabClickedListener {
                @Override 
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setupActionBar((Boolean) true, (Boolean) true, R.string.find_friends, (Boolean) true, (Boolean) true);
            this.mAppSessionListener = new FindFriendsListener(this, null);
            IconTabHost tabHost = this.mTabHost;
            this.mScrollBar.setRange(3);
            this.mViewPager.setOffscreenPageLimit(2);
            this.mTabsAdapter = new TabsAdapter(this, tabHost, this.mViewPager, this.mScrollBar);
            LayoutInflater inflater = LayoutInflater.from(this);
            tabHost.setOnTabChangedListener(this);
            tabHost.setOnTabClickedListener(this);
            Bundle addressBundle = new Bundle();
            addressBundle.putInt("empty_desc", R.string.find_friends_address_no_friends);
            this.mTabsAdapter.addTab(tabHost.newTabSpec(FindFriendsBaseActivity.TAG_ADDRESS_BOOK).setIndicator(TabIndicator.newIconIndicator(inflater, R.layout.find_friends_tab_indicator, tabHost, R.drawable.find_friends_address, 0)), FindFriendsAddressFragment.class, addressBundle);
            Bundle twitterBundle = new Bundle();
            twitterBundle.putInt("empty_desc", R.string.find_friends_twitter_no_friends);
            this.mTabsAdapter.addTab(tabHost.newTabSpec("twitter").setIndicator(TabIndicator.newIconIndicator(inflater, R.layout.find_friends_tab_indicator, tabHost, R.drawable.find_friends_twitter, 0)), FindFriendsTwitterFragment.class, twitterBundle);
            Bundle searchBundle = new Bundle();
            searchBundle.putInt("layout", R.layout.find_friends_search);
            this.mTabsAdapter.addTab(tabHost.newTabSpec("search").setIndicator(TabIndicator.newIconIndicator(inflater, R.layout.find_friends_tab_indicator, tabHost, R.drawable.find_friends_search, 0)), FindFriendsSearchFragment.class, searchBundle);
                }

                @Override 
        public void onCurrentTabClicked() {
                }

                @Override 
        void setCurrentTabByTag(String tag) {
            TabHost tabHost = this.mTabHost;
            if (!tag.equals(tabHost.getCurrentTabTag())) {
                tabHost.setCurrentTabByTag(tag);
                    }
            this.mScrollBar.setPosition(this.mViewPager.getCurrentItem());
                }

        private class FindFriendsListener extends AppSessionListener {
            private FindFriendsListener() {
                    }

             FindFriendsListener(FindFriendsActivity x0, Object x1) {
                this();
                    }
                }
            }
