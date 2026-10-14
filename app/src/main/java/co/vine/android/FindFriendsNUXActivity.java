            package co.vine.android;

            import android.app.ProgressDialog;
            import android.content.ComponentCallbacks;
            import android.content.Context;
            import android.content.Intent;
            import android.content.res.ColorStateList;
            import android.os.Bundle;
            import android.support.v7.app.ActionBar;
            import android.text.Editable;
            import android.text.TextUtils;
            import android.text.TextWatcher;
            import android.view.KeyEvent;
            import android.view.LayoutInflater;
            import android.view.Menu;
            import android.view.MenuItem;
            import android.view.View;
            import android.view.ViewGroup;
            import android.widget.EditText;
            import android.widget.ImageButton;
            import android.widget.TabHost;
            import android.widget.TextView;
            import co.vine.android.api.VineUser;
            import co.vine.android.client.AppSessionListener;
            import co.vine.android.util.Util;
            import co.vine.android.util.ViewUtil;
            import co.vine.android.util.analytics.FlurryUtils;
            import co.vine.android.widget.Typefaces;
            import co.vine.android.widget.tabs.IconTabHost;
            import co.vine.android.widget.tabs.TabIndicator;
            import co.vine.android.widget.tabs.TabsAdapter;
            import java.util.ArrayList;
            import java.util.HashSet;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 54 */    public class FindFriendsNUXActivity extends FindFriendsBaseActivity implements TextWatcher, IconTabHost.OnTabClickedListener, TextView.OnEditorActionListener, View.OnClickListener {
                public static final String ARG_FROM_SIGN_UP = "from_sign_up";
                public static final String ARG_TAKE_FOCUS = "take_focus";
                private ImageButton mClear;
                private boolean mIsTwitterReg;
                private EditText mQuery;
                private MenuItem mSearch;
                private HashSet<String> mUserIdsToFollow;

                public interface FilterableFriendsList {
                    void onTextChanged(CharSequence charSequence, int i, int i2, int i3);

                    boolean shouldShowSearchIcon();
                }

                @Override // co.vine.android.FindFriendsBaseActivity, co.vine.android.BaseActionBarActivity, android.support.v7.app.ActionBarActivity, android.support.v4.app.FragmentActivity, android.app.Activity
/* 55 */        public void onCreate(Bundle savedInstanceState) {
/* 56 */            super.onCreate(savedInstanceState);
/* 58 */            setupActionBar((Boolean) false, (Boolean) true, 0, (Boolean) false, (Boolean) false);
/* 60 */            ActionBar ab = getSupportActionBar();
/* 61 */            if (ab != null) {
/* 62 */                ab.setDisplayShowCustomEnabled(true);
/* 63 */                ab.setCustomView(C0257R.layout.search_action_box);
/* 65 */                ViewGroup customView = (ViewGroup) ab.getCustomView();
/* 66 */                TextView title = (TextView) customView.findViewById(2131427378);
/* 67 */                title.setText(C0257R.string.find_friends);
/* 69 */                EditText query = (EditText) customView.findViewById(C0257R.id.query);
/* 70 */                ViewUtil.reduceTextSizeViaFontScaleIfNeeded(this, 1.1f, 12.0f, query);
/* 71 */                query.setOnEditorActionListener(this);
/* 72 */                query.addTextChangedListener(this);
/* 73 */                query.setInputType(524288);
/* 74 */                query.setPadding(0, 0, 0, 0);
/* 75 */                this.mQuery = query;
/* 77 */                ImageButton clear = (ImageButton) customView.findViewById(C0257R.id.clear);
/* 78 */                clear.setOnClickListener(this);
/* 79 */                this.mClear = clear;
/* 81 */                showSearchBar(false);
                    }
/* 84 */            this.mAppSessionListener = new FindFriendsNUXListener(this, null);
/* 86 */            IconTabHost tabHost = this.mTabHost;
/* 87 */            this.mScrollBar.setRange(2);
/* 88 */            this.mViewPager.setOffscreenPageLimit(1);
/* 89 */            this.mTabsAdapter = new TabsAdapter(this, tabHost, this.mViewPager, this.mScrollBar);
/* 90 */            this.mTabsAdapter.setSetActionBarColorOnPageSelectedEnabled(false);
/* 92 */            Intent intent = getIntent();
/* 93 */            if (intent != null) {
/* 94 */                this.mIsTwitterReg = intent.getBooleanExtra(FindFriendsBaseActivity.EXTRA_IS_TWITTER_REG, false);
                    }
/* 97 */            tabHost.setOnTabChangedListener(this);
/* 98 */            tabHost.setOnTabClickedListener(this);
/* 100 */           if (this.mIsTwitterReg) {
/* 101 */               addTwitterTab();
/* 102 */               addContactsTab();
                    } else {
/* 104 */               addContactsTab();
/* 105 */               addTwitterTab();
                    }
/* 108 */           this.mUserIdsToFollow = new HashSet<>();
/* 109 */           FlurryUtils.trackNuxScreenDisplayed("discover_friends");
                }

                @Override // android.view.View.OnClickListener
/* 113 */       public void onClick(View v) {
/* 114 */           if (v.getId() == 2131427686) {
/* 115 */               clearSearch();
                    }
                }

/* 119 */       public void clearSearch() {
/* 120 */           this.mQuery.setText("");
/* 121 */           this.mClear.setVisibility(8);
/* 122 */           this.mQuery.clearFocus();
/* 123 */           showSearchBar(false);
/* 124 */           Util.setSoftKeyboardVisibility(this, this.mQuery, false);
                }

                @Override // co.vine.android.BaseActionBarActivity, android.app.Activity
/* 128 */       public boolean onCreateOptionsMenu(Menu menu) {
                    switch (this.mViewPager.getCurrentItem()) {
                        case 1:
/* 131 */                   getMenuInflater().inflate(C0257R.menu.nux_done, menu);
                            break;
                        default:
/* 136 */                   getMenuInflater().inflate(C0257R.menu.nux_next, menu);
                            break;
                    }
/* 141 */           MenuItem search = menu.findItem(C0257R.id.search);
/* 142 */           ComponentCallbacks currentFragment = this.mTabsAdapter.getCurrentFragment();
/* 143 */           if ((currentFragment instanceof FilterableFriendsList) && search != null) {
/* 144 */               search.setVisible(((FilterableFriendsList) currentFragment).shouldShowSearchIcon());
                    }
/* 146 */           this.mSearch = search;
/* 148 */           return true;
                }

                @Override // co.vine.android.BaseActionBarActivity, android.app.Activity
/* 152 */       public boolean onOptionsItemSelected(MenuItem item) {
/* 153 */           int id = item.getItemId();
/* 154 */           if (id == 2131427554) {
/* 155 */               item.setVisible(false);
/* 156 */               showSearchBar(true);
/* 157 */               this.mClear.setVisibility(0);
/* 158 */               this.mQuery.requestFocus();
/* 159 */               Util.setSoftKeyboardVisibility(this, this.mQuery, true);
                    } else if (id == 2131427941) {
/* 161 */               this.mViewPager.setCurrentItem(1);
                    } else if (!this.mUserIdsToFollow.isEmpty()) {
/* 164 */               ProgressDialog dialog = new ProgressDialog(this, C0257R.style.ProgressDialogTheme);
/* 165 */               this.mProgressDialog = dialog;
/* 166 */               dialog.setProgressStyle(0);
/* 167 */               dialog.setMessage(getString(C0257R.string.following_friends));
                        try {
/* 169 */                   dialog.show();
                        } catch (Exception e) {
                        }
/* 173 */               ArrayList<String> userIdsToFollow = new ArrayList<>(this.mUserIdsToFollow);
/* 174 */               this.mAppController.bulkFollowUsers(this.mAppController.getActiveSession(), userIdsToFollow);
                    } else {
/* 176 */               finishNux();
                    }
                    return true;
                }

/* 182 */       private void addContactsTab() {
/* 183 */           TabHost.TabSpec tabSpec = this.mTabHost.newTabSpec(FindFriendsBaseActivity.TAG_ADDRESS_BOOK);
/* 184 */           tabSpec.setIndicator(getTabIndicator(C0257R.string.contacts, C0257R.color.find_friend_address));
/* 186 */           Bundle bundle = new Bundle();
/* 187 */           if (this.mIsTwitterReg) {
/* 188 */               bundle.putBoolean("take_focus", false);
                    }
/* 190 */           bundle.putInt("empty_desc", C0257R.string.find_friends_address_no_friends);
/* 191 */           this.mTabsAdapter.addTab(tabSpec, FindFriendsNUXAddressFragment.class, bundle);
                }

/* 194 */       private void addTwitterTab() {
/* 195 */           TabHost.TabSpec tabSpec = this.mTabHost.newTabSpec("twitter");
/* 196 */           tabSpec.setIndicator(getTabIndicator(C0257R.string.twitter, C0257R.color.find_friend_twitter));
/* 198 */           Bundle bundle = new Bundle();
/* 199 */           bundle.putBoolean("from_sign_up", true);
/* 200 */           bundle.putInt("empty_desc", C0257R.string.find_friends_twitter_no_friends);
/* 201 */           this.mTabsAdapter.addTab(tabSpec, FindFriendsTwitterFragment.class, bundle);
                }

/* 204 */       private TabIndicator getTabIndicator(int tabTextId, int colorResId) {
/* 205 */           TabIndicator indicator = TabIndicator.newTextIndicator(LayoutInflater.from(this), C0257R.layout.normal_tab_indicator, this.mTabHost, tabTextId, false);
/* 207 */           ColorStateList indicatorStateList = TabIndicator.createTextColorList(getResources().getColor(colorResId), getResources().getColor(C0257R.color.light_light_gray));
/* 209 */           TextView indicatorText = indicator.getIndicatorText();
/* 210 */           indicatorText.setTypeface(Typefaces.get(this).boldContent);
/* 211 */           indicatorText.setTextColor(indicatorStateList);
/* 213 */           return indicator;
                }

/* 216 */       public void addUserToFollow(long userId) {
/* 217 */           this.mUserIdsToFollow.add(String.valueOf(userId));
                }

/* 220 */       public void addUsersToFollow(ArrayList<VineUser> users, Friendships friendships) {
                    for (VineUser user : users) {
/* 222 */               this.mUserIdsToFollow.add(String.valueOf(user.userId));
/* 223 */               friendships.addFollowing(user.userId);
                    }
                }

/* 227 */       public void removeUserToFollow(long userId) {
/* 228 */           this.mUserIdsToFollow.remove(String.valueOf(userId));
                }

/* 231 */       public void toggleFollowAll(boolean selectAll, ArrayList<VineUser> users, Friendships friendships) {
/* 232 */           if (selectAll) {
/* 233 */               addUsersToFollow(users, friendships);
/* 240 */               return;
                    }
                    for (VineUser user : users) {
/* 236 */               this.mUserIdsToFollow.remove(String.valueOf(user.userId));
/* 237 */               friendships.removeFollowing(user.userId);
                    }
                }

                @Override // co.vine.android.BaseActionBarActivity, android.support.v7.app.ActionBarActivity, android.support.v4.app.FragmentActivity, android.app.Activity
/* 243 */       public void onBackPressed() {
/* 244 */           if (!TextUtils.isEmpty(this.mQuery.getText().toString()) || this.mQuery.hasFocus()) {
/* 245 */               clearSearch();
                    } else if (this.mViewPager.getCurrentItem() > 0) {
/* 247 */               this.mViewPager.setCurrentItem(this.mViewPager.getCurrentItem() - 1);
                    }
                }

/* 251 */       public void showSearchIcon(boolean show) {
/* 252 */           if (this.mSearch != null) {
                        this.mSearch.setVisible(show);
                    }
                }

                @Override // co.vine.android.FindFriendsBaseActivity, co.vine.android.widget.tabs.IconTabHost.OnTabClickedListener
/* 256 */       public void onCurrentTabClicked() {
                }

                @Override // co.vine.android.FindFriendsBaseActivity
/* 259 */       void setCurrentTabByTag(String tag) {
/* 260 */           TabHost tabHost = this.mTabHost;
/* 261 */           if (!tag.equals(tabHost.getCurrentTabTag())) {
/* 262 */               tabHost.setCurrentTabByTag(tag);
                    }
/* 264 */           this.mScrollBar.setPosition(this.mViewPager.getCurrentItem());
                }

                @Override // android.text.TextWatcher
/* 268 */       public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override // android.text.TextWatcher
/* 272 */       public void onTextChanged(CharSequence s, int start, int before, int count) {
/* 273 */           ComponentCallbacks currentFragment = this.mTabsAdapter.getCurrentFragment();
/* 274 */           if (currentFragment instanceof FilterableFriendsList) {
/* 275 */               ((FilterableFriendsList) currentFragment).onTextChanged(s, start, before, count);
                    }
                }

                @Override // android.text.TextWatcher
/* 286 */       public void afterTextChanged(Editable s) {
                }

                @Override // android.widget.TextView.OnEditorActionListener
/* 290 */       public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
/* 291 */           if (v.getId() != 2131427790) {
                        return false;
                    }
/* 292 */           if (actionId != 3 && (event == null || event.getKeyCode() != 66)) {
                        return false;
                    }
/* 294 */           this.mQuery.clearFocus();
/* 295 */           Util.setSoftKeyboardVisibility(this, this.mQuery, false);
/* 296 */           return true;
                }

/* 302 */       public static void start(Context context) {
/* 303 */           context.startActivity(new Intent(context, (Class<?>) FindFriendsNUXActivity.class));
                }

/* 306 */       public void finishNux() {
/* 307 */           FlurryUtils.trackNuxCompleted();
/* 308 */           StartActivity.toStart(this, true);
/* 309 */           finish();
                }

/* 310 */       private class FindFriendsNUXListener extends AppSessionListener {
/* 311 */           private FindFriendsNUXListener() {
                    }

/* 311 */           /* synthetic */ FindFriendsNUXListener(FindFriendsNUXActivity x0, Object x1) {
/* 312 */               this();
                    }

                    @Override // co.vine.android.client.AppSessionListener
/* 314 */           public void onBulkFollowComplete(String reqId, int statusCode, String reasonPhrase) {
/* 315 */               FindFriendsNUXActivity.this.dismissDialog();
/* 316 */               FindFriendsNUXActivity.this.finishNux();
                    }
                }

/* 320 */       private void showSearchBar(boolean show) {
/* 321 */           ActionBar ab = getSupportActionBar();
/* 322 */           if (ab != null) {
/* 323 */               ViewGroup customView = (ViewGroup) ab.getCustomView();
/* 324 */               EditText query = (EditText) customView.findViewById(C0257R.id.query);
/* 325 */               ImageButton clear = (ImageButton) customView.findViewById(C0257R.id.clear);
/* 326 */               TextView title = (TextView) customView.findViewById(2131427378);
/* 327 */               query.setVisibility(show ? 0 : 8);
/* 328 */               clear.setVisibility(show ? 0 : 8);
/* 329 */               title.setVisibility(show ? 8 : 0);
                    }
                }
            }
