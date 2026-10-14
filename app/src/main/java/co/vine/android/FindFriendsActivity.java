            package co.vine.android;

            import android.os.Bundle;
            import android.view.LayoutInflater;
            import android.widget.TabHost;
            import co.vine.android.client.AppSessionListener;
            import co.vine.android.widget.tabs.IconTabHost;
            import co.vine.android.widget.tabs.TabIndicator;
            import co.vine.android.widget.tabs.TabsAdapter;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 18 */    public class FindFriendsActivity extends FindFriendsBaseActivity implements IconTabHost.OnTabClickedListener {
                @Override // co.vine.android.FindFriendsBaseActivity, co.vine.android.BaseActionBarActivity, android.support.v7.app.ActionBarActivity, android.support.v4.app.FragmentActivity, android.app.Activity
/* 19 */        public void onCreate(Bundle savedInstanceState) {
/* 20 */            super.onCreate(savedInstanceState);
/* 22 */            setupActionBar((Boolean) true, (Boolean) true, C0257R.string.find_friends, (Boolean) true, (Boolean) true);
/* 24 */            this.mAppSessionListener = new FindFriendsListener(this, null);
/* 26 */            IconTabHost tabHost = this.mTabHost;
/* 27 */            this.mScrollBar.setRange(3);
/* 28 */            this.mViewPager.setOffscreenPageLimit(2);
/* 29 */            this.mTabsAdapter = new TabsAdapter(this, tabHost, this.mViewPager, this.mScrollBar);
/* 31 */            LayoutInflater inflater = LayoutInflater.from(this);
/* 32 */            tabHost.setOnTabChangedListener(this);
/* 33 */            tabHost.setOnTabClickedListener(this);
/* 36 */            Bundle addressBundle = new Bundle();
/* 37 */            addressBundle.putInt("empty_desc", C0257R.string.find_friends_address_no_friends);
/* 38 */            this.mTabsAdapter.addTab(tabHost.newTabSpec(FindFriendsBaseActivity.TAG_ADDRESS_BOOK).setIndicator(TabIndicator.newIconIndicator(inflater, C0257R.layout.find_friends_tab_indicator, tabHost, C0257R.drawable.find_friends_address, 0)), FindFriendsAddressFragment.class, addressBundle);
/* 45 */            Bundle twitterBundle = new Bundle();
/* 46 */            twitterBundle.putInt("empty_desc", C0257R.string.find_friends_twitter_no_friends);
/* 47 */            this.mTabsAdapter.addTab(tabHost.newTabSpec("twitter").setIndicator(TabIndicator.newIconIndicator(inflater, C0257R.layout.find_friends_tab_indicator, tabHost, C0257R.drawable.find_friends_twitter, 0)), FindFriendsTwitterFragment.class, twitterBundle);
/* 54 */            Bundle searchBundle = new Bundle();
/* 55 */            searchBundle.putInt("layout", C0257R.layout.find_friends_search);
/* 57 */            this.mTabsAdapter.addTab(tabHost.newTabSpec("search").setIndicator(TabIndicator.newIconIndicator(inflater, C0257R.layout.find_friends_tab_indicator, tabHost, C0257R.drawable.find_friends_search, 0)), FindFriendsSearchFragment.class, searchBundle);
                }

                @Override // co.vine.android.FindFriendsBaseActivity, co.vine.android.widget.tabs.IconTabHost.OnTabClickedListener
/* 66 */        public void onCurrentTabClicked() {
                }

                @Override // co.vine.android.FindFriendsBaseActivity
/* 69 */        void setCurrentTabByTag(String tag) {
/* 70 */            TabHost tabHost = this.mTabHost;
/* 71 */            if (!tag.equals(tabHost.getCurrentTabTag())) {
/* 72 */                tabHost.setCurrentTabByTag(tag);
                    }
/* 74 */            this.mScrollBar.setPosition(this.mViewPager.getCurrentItem());
                }

/* 75 */        private class FindFriendsListener extends AppSessionListener {
/* 76 */            private FindFriendsListener() {
                    }

/* 76 */            /* synthetic */ FindFriendsListener(FindFriendsActivity x0, Object x1) {
/* 77 */                this();
                    }
                }
            }
