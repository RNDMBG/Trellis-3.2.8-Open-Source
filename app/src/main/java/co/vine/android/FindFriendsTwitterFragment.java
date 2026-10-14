            package co.vine.android;

            import android.accounts.Account;
            import android.accounts.AccountManager;
            import android.app.Activity;
            import android.app.ProgressDialog;
            import android.content.ContentUris;
            import android.content.Intent;
            import android.database.Cursor;
            import android.net.Uri;
            import android.os.Bundle;
            import android.support.v4.content.CursorLoader;
            import android.support.v4.content.Loader;
            import android.text.TextUtils;
            import android.text.style.StyleSpan;
            import android.view.LayoutInflater;
            import android.view.View;
            import android.view.ViewGroup;
            import android.widget.AdapterView;
            import android.widget.Button;
            import android.widget.ListAdapter;
            import android.widget.ListView;
            import android.widget.RelativeLayout;
            import android.widget.TextView;
            import co.vine.android.api.VineUser;
            import co.vine.android.cache.image.ImageKey;
            import co.vine.android.cache.image.UrlImage;
            import co.vine.android.client.AppController;
            import co.vine.android.client.AppSessionListener;
            import co.vine.android.client.TwitterVineApp;
            import co.vine.android.client.VineAccountHelper;
            import co.vine.android.provider.Vine;
            import co.vine.android.provider.VineDatabaseSQL;
            import co.vine.android.util.CommonUtil;
            import co.vine.android.util.CrashUtil;
            import co.vine.android.util.Util;
            import co.vine.android.util.analytics.FlurryUtils;
            import co.vine.android.widget.UserViewHolder;
            import com.twitter.android.sdk.Twitter;
            import com.twitter.android.widget.ItemPosition;
            import java.util.ArrayList;
            import java.util.HashMap;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 49 */    public class FindFriendsTwitterFragment extends BaseCursorListFragment implements View.OnClickListener, AdapterView.OnItemClickListener, FindFriendsNUXActivity.FilterableFriendsList {
                public static final String ARG_FROM_SIGN_UP = "from_sign_up";
                private static final String EVENT_SOURCE_TITLE = "Find Friends: Twitter";
                protected static final int LOADER_ID_FILTER = 1;
                private static final String STATE_FETCHED = "fetch";
                private static final String STATE_FRIENDSHIPS = "friendships";
                private static final String STATE_FROM_SIGN_UP = "from_sign_up";
                private FindFriendsNUXActivity mActivity;
                private StyleSpan[] mBold;
                private RelativeLayout mContactsCountContainer;
                private TextView mContactsText;
                private boolean mFetched;
                private boolean mFilterLoaderIsReady;
                private Runnable mFilterRunnable = new RunnableC02131();
                private Friendships mFriendships;
                private boolean mFromSignup;
                private int mNewFollowsCount;
                private String[] mProjection;
                private volatile String mSearchQuery;
                private String mSecret;
                private TextView mSelectAllToggle;
                private boolean mSelected;
                private String mSortOrder;
                private String mToken;
                private Twitter mTwitter;
                private ArrayList<VineUser> mTwitterFriends;

/* 50 */        static /* synthetic */ boolean access$000(FindFriendsTwitterFragment x0) {
/* 51 */            return x0.mFilterLoaderIsReady;
                }

/* 50 */        static /* synthetic */ boolean access$002(FindFriendsTwitterFragment x0, boolean x1) {
/* 51 */            x0.mFilterLoaderIsReady = x1;
                    return x1;
                }

/* 50 */        static /* synthetic */ boolean access$100(FindFriendsTwitterFragment x0) {
/* 51 */            return x0.mFromSignup;
                }

/* 50 */        static /* synthetic */ RelativeLayout access$200(FindFriendsTwitterFragment x0) {
/* 51 */            return x0.mContactsCountContainer;
                }

/* 50 */        static /* synthetic */ TextView access$300(FindFriendsTwitterFragment x0) {
/* 51 */            return x0.mContactsText;
                }

/* 50 */        static /* synthetic */ boolean access$400(FindFriendsTwitterFragment x0) {
/* 51 */            return x0.mSelected;
                }

/* 50 */        static /* synthetic */ TextView access$500(FindFriendsTwitterFragment x0) {
/* 51 */            return x0.mSelectAllToggle;
                }

/* 50 */        static /* synthetic */ Friendships access$600(FindFriendsTwitterFragment x0) {
/* 51 */            return x0.mFriendships;
                }

/* 50 */        static /* synthetic */ FindFriendsNUXActivity access$700(FindFriendsTwitterFragment x0) {
/* 51 */            return x0.mActivity;
                }

/* 50 */        static /* synthetic */ ArrayList access$800(FindFriendsTwitterFragment x0) {
/* 51 */            return x0.mTwitterFriends;
                }


                @Override // android.support.v4.app.Fragment
/* 84 */        public void onAttach(Activity activity) {
/* 85 */            super.onAttach(activity);
/* 86 */            if (activity instanceof FindFriendsNUXActivity) {
/* 87 */                this.mActivity = (FindFriendsNUXActivity) activity;
                    }
                }

                @Override // co.vine.android.BaseCursorListFragment, co.vine.android.BaseAdapterFragment, co.vine.android.BaseControllerFragment, android.support.v4.app.Fragment
/* 92 */        public void onCreate(Bundle savedInstanceState) {
/* 93 */            super.onCreate(savedInstanceState);
/* 95 */            this.mBold = new StyleSpan[]{new StyleSpan(1)};
/* 97 */            if (savedInstanceState != null) {
/* 98 */                if (savedInstanceState.containsKey(STATE_FETCHED)) {
/* 99 */                    this.mFetched = savedInstanceState.getBoolean(STATE_FETCHED);
                        }
/* 101 */               if (savedInstanceState.containsKey(STATE_FRIENDSHIPS)) {
/* 102 */                   this.mFriendships = (Friendships) savedInstanceState.getParcelable(STATE_FRIENDSHIPS);
                        }
/* 104 */               if (savedInstanceState.containsKey("from_sign_up")) {
/* 105 */                   this.mFromSignup = savedInstanceState.getBoolean("from_sign_up");
                        }
                    } else {
/* 108 */               this.mFriendships = new Friendships();
/* 109 */               Bundle args = getArguments();
/* 110 */               if (args != null) {
/* 111 */                   this.mFromSignup = args.getBoolean("from_sign_up");
                        }
                    }
/* 116 */           this.mAppController.removeUsers(this.mAppController.getActiveSession(), 6);
/* 119 */           this.mTwitter = new Twitter(TwitterVineApp.API_KEY, TwitterVineApp.API_SECRET);
/* 120 */           this.mAppSessionListener = new FriendsListener();
/* 121 */           this.mSelected = true;
/* 122 */           this.mTwitterFriends = new ArrayList<>();
                }

                @Override // co.vine.android.BaseCursorListFragment, android.support.v4.app.Fragment
/* 127 */       public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
/* 128 */           View v = inflater.inflate(C0257R.layout.find_friends_twitter_list_fragment, container, false);
/* 130 */           this.mListView = (ListView) v.findViewById(android.R.id.list);
/* 131 */           this.mListView.setEmptyView(v.findViewById(android.R.id.empty));
/* 132 */           this.mListView.setDividerHeight(0);
/* 133 */           this.mListView.setOnItemClickListener(this);
/* 134 */           this.mContactsCountContainer = (RelativeLayout) v.findViewById(C0257R.id.contacts_count_container);
/* 135 */           this.mContactsText = (TextView) v.findViewById(C0257R.id.contacts_text);
/* 136 */           this.mSelectAllToggle = (TextView) v.findViewById(C0257R.id.select_all_toggle);
/* 137 */           this.mSelectAllToggle.setOnClickListener(this);
/* 139 */           getTwitterTokenAndSecretFromAccountManager();
/* 141 */           Button actionButton = (Button) v.findViewById(C0257R.id.friends_btn);
/* 143 */           if (this.mToken != null && this.mSecret != null) {
/* 144 */               actionButton.setVisibility(8);
/* 145 */               fetchTwitterFriends(this.mToken, this.mSecret);
                    } else {
/* 147 */               actionButton.setText(Util.getSpannedText(this.mBold, getString(C0257R.string.find_friends_twitter_cta), CommonUtil.MARKER_QUOTES));
/* 151 */               actionButton.setOnClickListener(this);
                    }
/* 154 */           return v;
                }

                @Override // co.vine.android.BaseCursorListFragment, android.support.v4.app.Fragment
/* 158 */       public void onViewCreated(View view, Bundle savedInstanceState) {
/* 159 */           super.onViewCreated(view, savedInstanceState);
/* 160 */           if (!this.mFromSignup) {
/* 161 */               LayoutInflater inflater = LayoutInflater.from(getActivity());
/* 162 */               View title = inflater.inflate(C0257R.layout.list_header, (ViewGroup) null);
/* 163 */               this.mListView.addHeaderView(title);
/* 165 */               ((TextView) title.findViewById(C0257R.id.subtitle)).setText(C0257R.string.find_friends_twitter_subtitle);
                    }
                }

                @Override // android.support.v4.app.Fragment
/* 171 */       public void onActivityCreated(Bundle savedInstanceState) {
/* 172 */           super.onActivityCreated(savedInstanceState);
/* 173 */           if (this.mCursorAdapter == null) {
/* 174 */               this.mProjection = VineDatabaseSQL.UsersQuery.PROJECTION;
/* 175 */               this.mSortOrder = Vine.Users.SORT_ORDER_DEFAULT;
/* 176 */               this.mCursorAdapter = new UsersAdapter(getActivity(), this.mAppController, true, this, this.mFriendships, 0);
                    }
/* 179 */           this.mListView.setAdapter((ListAdapter) this.mCursorAdapter);
                }

                @Override // co.vine.android.BaseCursorListFragment, co.vine.android.BaseAdapterFragment, co.vine.android.BaseControllerFragment, co.vine.android.BaseFragment, android.support.v4.app.Fragment
/* 183 */       public void onResume() {
/* 184 */           super.onResume();
                }

                @Override // co.vine.android.BaseCursorListFragment, com.twitter.android.widget.RefreshListener
/* 189 */       public ItemPosition getFirstItemPosition() {
/* 190 */           return null;
                }

                @Override // co.vine.android.BaseCursorListFragment, co.vine.android.BaseAdapterFragment, android.support.v4.app.Fragment
/* 194 */       public void onSaveInstanceState(Bundle outState) {
/* 195 */           super.onSaveInstanceState(outState);
/* 196 */           outState.putBoolean(STATE_FETCHED, this.mFetched);
/* 197 */           outState.putParcelable(STATE_FRIENDSHIPS, this.mFriendships);
/* 198 */           outState.putBoolean("from_sign_up", this.mFromSignup);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // co.vine.android.BaseCursorAdapterFragment
/* 202 */       public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
/* 203 */           super.onLoadFinished(loader, cursor);
/* 204 */           if (this.mActivity != null && cursor.getCount() > 0 && this.mFromSignup) {
/* 205 */               this.mActivity.showSearchIcon(true);
                    }
                }

                @Override // co.vine.android.BaseCursorAdapterFragment, android.support.v4.app.LoaderManager.LoaderCallbacks
/* 210 */       public Loader<Cursor> onCreateLoader(int id, Bundle args) {
                    switch (id) {
                        case 1:
/* 213 */                   Uri.Builder uri = ContentUris.withAppendedId(Vine.UserGroupsView.CONTENT_URI_FRIENDS_FILTER, this.mAppController.getActiveId()).buildUpon();
/* 215 */                   uri.appendQueryParameter(Vine.UserGroupColumns.QUERY_FRIEND_FILTER, Uri.encode(this.mSearchQuery));
/* 216 */                   uri.appendQueryParameter(Vine.UserGroupColumns.QUERY_GROUP_TYPE, String.valueOf(6));
/* 218 */                   return new CursorLoader(getActivity(), uri.build(), this.mProjection, null, null, this.mSortOrder);
                        default:
/* 223 */                   return new CursorLoader(getActivity(), ContentUris.withAppendedId(Vine.UserGroupsView.CONTENT_URI_FIND_FRIENDS_TWITTER, this.mAppController.getActiveId()), this.mProjection, null, null, this.mSortOrder);
                    }
                }

/* 228 */       private void fetchContent(int fetchType) {
/* 229 */           this.mFetched = true;
                    switch (fetchType) {
                        case 3:
/* 233 */                   if (this.mToken == null || this.mSecret == null) {
/* 234 */                       AppController.startTwitterAuthWithFinish(this.mTwitter, getActivity());
                            } else {
/* 236 */                       fetchTwitterFriends(this.mToken, this.mSecret);
                            }
                            break;
                    }
                }

/* 246 */       private void getTwitterTokenAndSecretFromAccountManager() {
/* 247 */           Activity activity = getActivity();
/* 248 */           String uniqueLogin = this.mAppController.getActiveSession().getLoginEmail();
/* 249 */           Account ac = VineAccountHelper.getAccount(activity, uniqueLogin);
/* 250 */           if (ac == null) {
/* 251 */               CrashUtil.logException(new VineLoggingException("Find Friends Twitter account was null"), "Find Friends Twitter account was null. UniqueLogin: {} ", uniqueLogin);
/* 254 */               Util.showCenteredToast(activity, C0257R.string.find_friends_twitter_error);
                    } else {
/* 258 */               AccountManager am = AccountManager.get(getActivity());
/* 260 */               this.mToken = am.getUserData(ac, VineAccountHelper.KEY_TWITTER_TOKEN);
/* 261 */               this.mSecret = am.getUserData(ac, VineAccountHelper.KEY_TWITTER_SECRET);
                    }
                }

/* 264 */       private void fetchTwitterFriends(String token, String secret) {
/* 265 */           ProgressDialog dialog = new ProgressDialog(getActivity(), C0257R.style.ProgressDialogTheme);
/* 267 */           dialog.setProgress(0);
/* 268 */           dialog.setMessage(getString(C0257R.string.finding_friends_twitter));
/* 269 */           dialog.show();
/* 270 */           this.mProgressDialog = dialog;
/* 271 */           this.mAppController.fetchTwitterFriends(this.mAppController.getActiveSession(), token, secret);
                }

                @Override // co.vine.android.BaseCursorListFragment, android.support.v4.app.Fragment
/* 276 */       public void onActivityResult(int requestCode, int resultCode, Intent data) {
                    switch (requestCode) {
                        case 1:
/* 279 */                   if (resultCode == -1) {
/* 280 */                       this.mToken = data.getStringExtra(Twitter.EXTRA_TOKEN);
/* 281 */                       this.mSecret = data.getStringExtra(Twitter.EXTRA_TOKEN_SECRET);
/* 282 */                       fetchTwitterFriends(this.mToken, this.mSecret);
                            } else if (resultCode != 0) {
/* 284 */                       dismissProgressDialog();
/* 285 */                       Util.showCenteredToast(getActivity(), C0257R.string.find_friends_twitter_sdk_error);
                            }
                            break;
                        case 2:
/* 291 */                   if (resultCode == -1) {
/* 292 */                       this.mToken = data.getStringExtra("token");
/* 293 */                       this.mSecret = data.getStringExtra("secret");
/* 294 */                       fetchTwitterFriends(this.mToken, this.mSecret);
                            }
                            break;
                        default:
/* 299 */                   super.onActivityResult(requestCode, resultCode, data);
                            break;
                    }
                }

                @Override // android.view.View.OnClickListener
/* 306 */       public void onClick(View view) {
/* 307 */           int id = view.getId();
/* 308 */           if (id == 2131427582) {
/* 309 */               fetchContent(3);
/* 340 */               return;
                    }
/* 310 */           if (id == 2131427917) {
/* 311 */               FollowButtonViewHolder holder = (FollowButtonViewHolder) view.getTag();
/* 312 */               if (holder != null) {
/* 313 */                   if (!holder.following) {
/* 314 */                       if (this.mFromSignup) {
/* 315 */                           this.mActivity.addUserToFollow(holder.userId);
                                } else {
/* 317 */                           this.mAppController.followUser(this.mAppController.getActiveSession(), holder.userId, true);
                                }
/* 320 */                       this.mFriendships.addFollowing(holder.userId);
/* 321 */                       this.mCursorAdapter.notifyDataSetChanged();
/* 322 */                       this.mNewFollowsCount++;
/* 340 */                       return;
                            }
/* 324 */                   if (this.mFromSignup) {
/* 325 */                       this.mActivity.removeUserToFollow(holder.userId);
                            } else {
/* 327 */                       this.mAppController.unfollowUser(this.mAppController.getActiveSession(), holder.userId, true);
                            }
/* 330 */                   this.mFriendships.removeFollowing(holder.userId);
/* 331 */                   this.mCursorAdapter.notifyDataSetChanged();
/* 340 */                   return;
                        }
/* 340 */               return;
                    }
/* 334 */           if (id == 2131427576) {
/* 335 */               this.mSelected = this.mSelected ? false : true;
/* 336 */               this.mActivity.toggleFollowAll(this.mSelected, this.mTwitterFriends, this.mFriendships);
/* 337 */               this.mSelectAllToggle.setText(this.mSelected ? C0257R.string.deselect_all : C0257R.string.select_all);
/* 338 */               this.mCursorAdapter.notifyDataSetChanged();
                    }
                }

                @Override // co.vine.android.BaseCursorListFragment, co.vine.android.widget.OnTabChangedListener
/* 343 */       public void onMoveAway(int newPosition) {
/* 344 */           super.onMoveAway(newPosition);
/* 345 */           if (this.mFetched) {
/* 346 */               String count = this.mNewFollowsCount > 15 ? ">15" : String.valueOf(this.mNewFollowsCount);
/* 347 */               FlurryUtils.trackTwitterNewFollowingCount(count);
                    }
/* 349 */           this.mNewFollowsCount = 0;
                }

                @Override // co.vine.android.BaseCursorListFragment, co.vine.android.widget.OnTabChangedListener
/* 353 */       public void onMoveTo(int oldPosition) {
/* 354 */           super.onMoveTo(oldPosition);
/* 355 */           getActivity().invalidateOptionsMenu();
/* 356 */           if (this.mActivity != null) {
/* 357 */               this.mActivity.clearSearch();
                    }
                }

                @Override // android.widget.AdapterView.OnItemClickListener
/* 362 */       public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
/* 363 */           if (view.getTag() instanceof UserViewHolder) {
/* 364 */               UserViewHolder h = (UserViewHolder) view.getTag();
/* 365 */               startProfileActivity(h.userId);
                    }
                }

/* 369 */       private void startProfileActivity(long userId) {
/* 370 */           ProfileActivity.start(getActivity(), userId, EVENT_SOURCE_TITLE, false);
                }

                @Override // co.vine.android.FindFriendsNUXActivity.FilterableFriendsList
/* 373 */       public void onTextChanged(CharSequence s, int start, int before, int count) {
/* 374 */           this.mHandler.removeCallbacks(this.mFilterRunnable);
/* 375 */           this.mSearchQuery = s.toString();
/* 376 */           if (!TextUtils.isEmpty(this.mSearchQuery)) {
/* 377 */               this.mHandler.postDelayed(this.mFilterRunnable, 300L);
                    } else {
/* 379 */               restartLoader(0);
                    }
                }

                @Override // co.vine.android.FindFriendsNUXActivity.FilterableFriendsList
/* 384 */       public boolean shouldShowSearchIcon() {
                    return (this.mCursorAdapter == null || this.mCursorAdapter.isEmpty()) ? false : true;
                }

                /* JADX INFO: renamed from: co.vine.android.FindFriendsTwitterFragment$1 */
/* 386 */       class RunnableC02131 implements Runnable {
/* 387 */           RunnableC02131() {
                    }

                    @Override // java.lang.Runnable
/* 390 */           public void run() {
/* 391 */               if (!FindFriendsTwitterFragment.access$000(FindFriendsTwitterFragment.this)) {
/* 392 */                   FindFriendsTwitterFragment.this.initLoader(1);
/* 393 */                   FindFriendsTwitterFragment.access$002(FindFriendsTwitterFragment.this, true);
                        } else {
/* 395 */                   FindFriendsTwitterFragment.this.restartLoader(1);
                        }
                    }
                }

                @Override // co.vine.android.BaseCursorAdapterFragment, android.support.v4.app.LoaderManager.LoaderCallbacks
/* 401 */       public void onLoaderReset(Loader<Cursor> loader) {
/* 402 */           this.mCursorAdapter.swapCursor(null);
                }

/* 403 */       class FriendsListener extends AppSessionListener {
/* 404 */           FriendsListener() {
                    }

                    @Override // co.vine.android.client.AppSessionListener
/* 408 */           public void onGetTwitterFriendsComplete(String reqId, int statusCode, String reasonPhrase, int count, ArrayList<VineUser> users) {
/* 409 */               FindFriendsTwitterFragment.this.dismissProgressDialog();
/* 410 */               if (reqId != null && statusCode == 200) {
/* 411 */                   FlurryUtils.trackFindFriendsTwitterCount(count);
/* 412 */                   if (FindFriendsTwitterFragment.access$100(FindFriendsTwitterFragment.this) && count > 0) {
/* 413 */                       FindFriendsTwitterFragment.access$200(FindFriendsTwitterFragment.this).setVisibility(0);
/* 414 */                       FindFriendsTwitterFragment.access$300(FindFriendsTwitterFragment.this).setText(FindFriendsTwitterFragment.this.getString(C0257R.string.find_friends_twitter_count, Integer.valueOf(count)));
/* 415 */                       FindFriendsTwitterFragment.access$500(FindFriendsTwitterFragment.this).setText(FindFriendsTwitterFragment.access$400(FindFriendsTwitterFragment.this) ? C0257R.string.deselect_all : C0257R.string.select_all);
/* 416 */                       FindFriendsTwitterFragment.access$700(FindFriendsTwitterFragment.this).addUsersToFollow(users, FindFriendsTwitterFragment.access$600(FindFriendsTwitterFragment.this));
/* 417 */                       FindFriendsTwitterFragment.access$800(FindFriendsTwitterFragment.this).addAll(users);
/* 425 */                       return;
                            }
/* 418 */                   if (count == 0) {
/* 419 */                       Util.showCenteredToast(FindFriendsTwitterFragment.this.getActivity(), C0257R.string.find_friends_twitter_no_friends);
/* 425 */                       return;
                            }
/* 425 */                   return;
                        }
/* 422 */               FlurryUtils.trackFindFriendsTwitterFailure();
/* 423 */               Util.showCenteredToast(FindFriendsTwitterFragment.this.getActivity(), C0257R.string.find_friends_twitter_error);
                    }

                    @Override // co.vine.android.client.AppSessionListener
/* 428 */           public void onRemoveUsersComplete(String reqId) {
/* 429 */               if (FindFriendsTwitterFragment.this.mCursorAdapter.getCursor() == null) {
/* 430 */                   FindFriendsTwitterFragment.this.initLoader();
                        }
                    }

                    @Override // co.vine.android.client.AppSessionListener
/* 435 */           public void onPhotoImageLoaded(HashMap<ImageKey, UrlImage> images) {
/* 436 */               ((UsersAdapter) FindFriendsTwitterFragment.this.mCursorAdapter).setUserImages(images);
                    }

                    @Override // co.vine.android.client.AppSessionListener
/* 441 */           public void onFollowComplete(String reqId, int statusCode, String reasonPhrase, long userId) {
/* 442 */               if (reqId != null && statusCode != 200) {
/* 443 */                   FindFriendsTwitterFragment.access$600(FindFriendsTwitterFragment.this).removeFollowing(userId);
                        }
                    }

                    @Override // co.vine.android.client.AppSessionListener
/* 449 */           public void onUnFollowComplete(String reqId, int statusCode, String reasonPhrase, long userId) {
/* 450 */               if (reqId != null && statusCode != 200) {
/* 451 */                   FindFriendsTwitterFragment.access$600(FindFriendsTwitterFragment.this).addFollowing(userId);
                        }
                    }
                }
            }
