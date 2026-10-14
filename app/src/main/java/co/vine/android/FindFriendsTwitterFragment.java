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
            
            
    public class FindFriendsTwitterFragment extends BaseCursorListFragment implements View.OnClickListener, AdapterView.OnItemClickListener, FindFriendsNUXActivity.FilterableFriendsList {
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

        static  boolean access$000(FindFriendsTwitterFragment x0) {
            return x0.mFilterLoaderIsReady;
                }

        static  boolean access$002(FindFriendsTwitterFragment x0, boolean x1) {
            x0.mFilterLoaderIsReady = x1;
                    return x1;
                }

        static  boolean access$100(FindFriendsTwitterFragment x0) {
            return x0.mFromSignup;
                }

        static  RelativeLayout access$200(FindFriendsTwitterFragment x0) {
            return x0.mContactsCountContainer;
                }

        static  TextView access$300(FindFriendsTwitterFragment x0) {
            return x0.mContactsText;
                }

        static  boolean access$400(FindFriendsTwitterFragment x0) {
            return x0.mSelected;
                }

        static  TextView access$500(FindFriendsTwitterFragment x0) {
            return x0.mSelectAllToggle;
                }

        static  Friendships access$600(FindFriendsTwitterFragment x0) {
            return x0.mFriendships;
                }

        static  FindFriendsNUXActivity access$700(FindFriendsTwitterFragment x0) {
            return x0.mActivity;
                }

        static  ArrayList access$800(FindFriendsTwitterFragment x0) {
            return x0.mTwitterFriends;
                }


                @Override 
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            if (activity instanceof FindFriendsNUXActivity) {
                this.mActivity = (FindFriendsNUXActivity) activity;
                    }
                }

                @Override 
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            this.mBold = new StyleSpan[]{new StyleSpan(1)};
            if (savedInstanceState != null) {
                if (savedInstanceState.containsKey(STATE_FETCHED)) {
                    this.mFetched = savedInstanceState.getBoolean(STATE_FETCHED);
                        }
               if (savedInstanceState.containsKey(STATE_FRIENDSHIPS)) {
                   this.mFriendships = (Friendships) savedInstanceState.getParcelable(STATE_FRIENDSHIPS);
                        }
               if (savedInstanceState.containsKey("from_sign_up")) {
                   this.mFromSignup = savedInstanceState.getBoolean("from_sign_up");
                        }
                    } else {
               this.mFriendships = new Friendships();
               Bundle args = getArguments();
               if (args != null) {
                   this.mFromSignup = args.getBoolean("from_sign_up");
                        }
                    }
           this.mAppController.removeUsers(this.mAppController.getActiveSession(), 6);
           this.mTwitter = new Twitter(TwitterVineApp.API_KEY, TwitterVineApp.API_SECRET);
           this.mAppSessionListener = new FriendsListener();
           this.mSelected = true;
           this.mTwitterFriends = new ArrayList<>();
                }

                @Override 
       public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
           View v = inflater.inflate(C0257R.layout.find_friends_twitter_list_fragment, container, false);
           this.mListView = (ListView) v.findViewById(android.R.id.list);
           this.mListView.setEmptyView(v.findViewById(android.R.id.empty));
           this.mListView.setDividerHeight(0);
           this.mListView.setOnItemClickListener(this);
           this.mContactsCountContainer = (RelativeLayout) v.findViewById(C0257R.id.contacts_count_container);
           this.mContactsText = (TextView) v.findViewById(C0257R.id.contacts_text);
           this.mSelectAllToggle = (TextView) v.findViewById(C0257R.id.select_all_toggle);
           this.mSelectAllToggle.setOnClickListener(this);
           getTwitterTokenAndSecretFromAccountManager();
           Button actionButton = (Button) v.findViewById(C0257R.id.friends_btn);
           if (this.mToken != null && this.mSecret != null) {
               actionButton.setVisibility(8);
               fetchTwitterFriends(this.mToken, this.mSecret);
                    } else {
               actionButton.setText(Util.getSpannedText(this.mBold, getString(C0257R.string.find_friends_twitter_cta), CommonUtil.MARKER_QUOTES));
               actionButton.setOnClickListener(this);
                    }
           return v;
                }

                @Override 
       public void onViewCreated(View view, Bundle savedInstanceState) {
           super.onViewCreated(view, savedInstanceState);
           if (!this.mFromSignup) {
               LayoutInflater inflater = LayoutInflater.from(getActivity());
               View title = inflater.inflate(C0257R.layout.list_header, (ViewGroup) null);
               this.mListView.addHeaderView(title);
               ((TextView) title.findViewById(C0257R.id.subtitle)).setText(C0257R.string.find_friends_twitter_subtitle);
                    }
                }

                @Override 
       public void onActivityCreated(Bundle savedInstanceState) {
           super.onActivityCreated(savedInstanceState);
           if (this.mCursorAdapter == null) {
               this.mProjection = VineDatabaseSQL.UsersQuery.PROJECTION;
               this.mSortOrder = Vine.Users.SORT_ORDER_DEFAULT;
               this.mCursorAdapter = new UsersAdapter(getActivity(), this.mAppController, true, this, this.mFriendships, 0);
                    }
           this.mListView.setAdapter((ListAdapter) this.mCursorAdapter);
                }

                @Override 
       public void onResume() {
           super.onResume();
                }

                @Override 
       public ItemPosition getFirstItemPosition() {
           return null;
                }

                @Override 
       public void onSaveInstanceState(Bundle outState) {
           super.onSaveInstanceState(outState);
           outState.putBoolean(STATE_FETCHED, this.mFetched);
           outState.putParcelable(STATE_FRIENDSHIPS, this.mFriendships);
           outState.putBoolean("from_sign_up", this.mFromSignup);
                }

                
                @Override 
       public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
           super.onLoadFinished(loader, cursor);
           if (this.mActivity != null && cursor.getCount() > 0 && this.mFromSignup) {
               this.mActivity.showSearchIcon(true);
                    }
                }

                @Override 
       public Loader<Cursor> onCreateLoader(int id, Bundle args) {
                    switch (id) {
                        case 1:
                   Uri.Builder uri = ContentUris.withAppendedId(Vine.UserGroupsView.CONTENT_URI_FRIENDS_FILTER, this.mAppController.getActiveId()).buildUpon();
                   uri.appendQueryParameter(Vine.UserGroupColumns.QUERY_FRIEND_FILTER, Uri.encode(this.mSearchQuery));
                   uri.appendQueryParameter(Vine.UserGroupColumns.QUERY_GROUP_TYPE, String.valueOf(6));
                   return new CursorLoader(getActivity(), uri.build(), this.mProjection, null, null, this.mSortOrder);
                        default:
                   return new CursorLoader(getActivity(), ContentUris.withAppendedId(Vine.UserGroupsView.CONTENT_URI_FIND_FRIENDS_TWITTER, this.mAppController.getActiveId()), this.mProjection, null, null, this.mSortOrder);
                    }
                }

       private void fetchContent(int fetchType) {
           this.mFetched = true;
                    switch (fetchType) {
                        case 3:
                   if (this.mToken == null || this.mSecret == null) {
                       AppController.startTwitterAuthWithFinish(this.mTwitter, getActivity());
                            } else {
                       fetchTwitterFriends(this.mToken, this.mSecret);
                            }
                            break;
                    }
                }

       private void getTwitterTokenAndSecretFromAccountManager() {
           Activity activity = getActivity();
           String uniqueLogin = this.mAppController.getActiveSession().getLoginEmail();
           Account ac = VineAccountHelper.getAccount(activity, uniqueLogin);
           if (ac == null) {
               CrashUtil.logException(new VineLoggingException("Find Friends Twitter account was null"), "Find Friends Twitter account was null. UniqueLogin: {} ", uniqueLogin);
               Util.showCenteredToast(activity, C0257R.string.find_friends_twitter_error);
                    } else {
               AccountManager am = AccountManager.get(getActivity());
               this.mToken = am.getUserData(ac, VineAccountHelper.KEY_TWITTER_TOKEN);
               this.mSecret = am.getUserData(ac, VineAccountHelper.KEY_TWITTER_SECRET);
                    }
                }

       private void fetchTwitterFriends(String token, String secret) {
           ProgressDialog dialog = new ProgressDialog(getActivity(), C0257R.style.ProgressDialogTheme);
           dialog.setProgress(0);
           dialog.setMessage(getString(C0257R.string.finding_friends_twitter));
           dialog.show();
           this.mProgressDialog = dialog;
           this.mAppController.fetchTwitterFriends(this.mAppController.getActiveSession(), token, secret);
                }

                @Override 
       public void onActivityResult(int requestCode, int resultCode, Intent data) {
                    switch (requestCode) {
                        case 1:
                   if (resultCode == -1) {
                       this.mToken = data.getStringExtra(Twitter.EXTRA_TOKEN);
                       this.mSecret = data.getStringExtra(Twitter.EXTRA_TOKEN_SECRET);
                       fetchTwitterFriends(this.mToken, this.mSecret);
                            } else if (resultCode != 0) {
                       dismissProgressDialog();
                       Util.showCenteredToast(getActivity(), C0257R.string.find_friends_twitter_sdk_error);
                            }
                            break;
                        case 2:
                   if (resultCode == -1) {
                       this.mToken = data.getStringExtra("token");
                       this.mSecret = data.getStringExtra("secret");
                       fetchTwitterFriends(this.mToken, this.mSecret);
                            }
                            break;
                        default:
                   super.onActivityResult(requestCode, resultCode, data);
                            break;
                    }
                }

                @Override 
       public void onClick(View view) {
           int id = view.getId();
           if (id == 2131427582) {
               fetchContent(3);
               return;
                    }
           if (id == 2131427917) {
               FollowButtonViewHolder holder = (FollowButtonViewHolder) view.getTag();
               if (holder != null) {
                   if (!holder.following) {
                       if (this.mFromSignup) {
                           this.mActivity.addUserToFollow(holder.userId);
                                } else {
                           this.mAppController.followUser(this.mAppController.getActiveSession(), holder.userId, true);
                                }
                       this.mFriendships.addFollowing(holder.userId);
                       this.mCursorAdapter.notifyDataSetChanged();
                       this.mNewFollowsCount++;
                       return;
                            }
                   if (this.mFromSignup) {
                       this.mActivity.removeUserToFollow(holder.userId);
                            } else {
                       this.mAppController.unfollowUser(this.mAppController.getActiveSession(), holder.userId, true);
                            }
                   this.mFriendships.removeFollowing(holder.userId);
                   this.mCursorAdapter.notifyDataSetChanged();
                   return;
                        }
               return;
                    }
           if (id == 2131427576) {
               this.mSelected = this.mSelected ? false : true;
               this.mActivity.toggleFollowAll(this.mSelected, this.mTwitterFriends, this.mFriendships);
               this.mSelectAllToggle.setText(this.mSelected ? C0257R.string.deselect_all : C0257R.string.select_all);
               this.mCursorAdapter.notifyDataSetChanged();
                    }
                }

                @Override 
       public void onMoveAway(int newPosition) {
           super.onMoveAway(newPosition);
           if (this.mFetched) {
               String count = this.mNewFollowsCount > 15 ? ">15" : String.valueOf(this.mNewFollowsCount);
               FlurryUtils.trackTwitterNewFollowingCount(count);
                    }
           this.mNewFollowsCount = 0;
                }

                @Override 
       public void onMoveTo(int oldPosition) {
           super.onMoveTo(oldPosition);
           getActivity().invalidateOptionsMenu();
           if (this.mActivity != null) {
               this.mActivity.clearSearch();
                    }
                }

                @Override 
       public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
           if (view.getTag() instanceof UserViewHolder) {
               UserViewHolder h = (UserViewHolder) view.getTag();
               startProfileActivity(h.userId);
                    }
                }

       private void startProfileActivity(long userId) {
           ProfileActivity.start(getActivity(), userId, EVENT_SOURCE_TITLE, false);
                }

                @Override 
       public void onTextChanged(CharSequence s, int start, int before, int count) {
           this.mHandler.removeCallbacks(this.mFilterRunnable);
           this.mSearchQuery = s.toString();
           if (!TextUtils.isEmpty(this.mSearchQuery)) {
               this.mHandler.postDelayed(this.mFilterRunnable, 300L);
                    } else {
               restartLoader(0);
                    }
                }

                @Override 
       public boolean shouldShowSearchIcon() {
                    return (this.mCursorAdapter == null || this.mCursorAdapter.isEmpty()) ? false : true;
                }

                
       class RunnableC02131 implements Runnable {
           RunnableC02131() {
                    }

                    @Override 
           public void run() {
               if (!FindFriendsTwitterFragment.access$000(FindFriendsTwitterFragment.this)) {
                   FindFriendsTwitterFragment.this.initLoader(1);
                   FindFriendsTwitterFragment.access$002(FindFriendsTwitterFragment.this, true);
                        } else {
                   FindFriendsTwitterFragment.this.restartLoader(1);
                        }
                    }
                }

                @Override 
       public void onLoaderReset(Loader<Cursor> loader) {
           this.mCursorAdapter.swapCursor(null);
                }

       class FriendsListener extends AppSessionListener {
           FriendsListener() {
                    }

                    @Override 
           public void onGetTwitterFriendsComplete(String reqId, int statusCode, String reasonPhrase, int count, ArrayList<VineUser> users) {
               FindFriendsTwitterFragment.this.dismissProgressDialog();
               if (reqId != null && statusCode == 200) {
                   FlurryUtils.trackFindFriendsTwitterCount(count);
                   if (FindFriendsTwitterFragment.access$100(FindFriendsTwitterFragment.this) && count > 0) {
                       FindFriendsTwitterFragment.access$200(FindFriendsTwitterFragment.this).setVisibility(0);
                       FindFriendsTwitterFragment.access$300(FindFriendsTwitterFragment.this).setText(FindFriendsTwitterFragment.this.getString(C0257R.string.find_friends_twitter_count, Integer.valueOf(count)));
                       FindFriendsTwitterFragment.access$500(FindFriendsTwitterFragment.this).setText(FindFriendsTwitterFragment.access$400(FindFriendsTwitterFragment.this) ? C0257R.string.deselect_all : C0257R.string.select_all);
                       FindFriendsTwitterFragment.access$700(FindFriendsTwitterFragment.this).addUsersToFollow(users, FindFriendsTwitterFragment.access$600(FindFriendsTwitterFragment.this));
                       FindFriendsTwitterFragment.access$800(FindFriendsTwitterFragment.this).addAll(users);
                       return;
                            }
                   if (count == 0) {
                       Util.showCenteredToast(FindFriendsTwitterFragment.this.getActivity(), C0257R.string.find_friends_twitter_no_friends);
                       return;
                            }
                   return;
                        }
               FlurryUtils.trackFindFriendsTwitterFailure();
               Util.showCenteredToast(FindFriendsTwitterFragment.this.getActivity(), C0257R.string.find_friends_twitter_error);
                    }

                    @Override 
           public void onRemoveUsersComplete(String reqId) {
               if (FindFriendsTwitterFragment.this.mCursorAdapter.getCursor() == null) {
                   FindFriendsTwitterFragment.this.initLoader();
                        }
                    }

                    @Override 
           public void onPhotoImageLoaded(HashMap<ImageKey, UrlImage> images) {
               ((UsersAdapter) FindFriendsTwitterFragment.this.mCursorAdapter).setUserImages(images);
                    }

                    @Override 
           public void onFollowComplete(String reqId, int statusCode, String reasonPhrase, long userId) {
               if (reqId != null && statusCode != 200) {
                   FindFriendsTwitterFragment.access$600(FindFriendsTwitterFragment.this).removeFollowing(userId);
                        }
                    }

                    @Override 
           public void onUnFollowComplete(String reqId, int statusCode, String reasonPhrase, long userId) {
               if (reqId != null && statusCode != 200) {
                   FindFriendsTwitterFragment.access$600(FindFriendsTwitterFragment.this).addFollowing(userId);
                        }
                    }
                }
            }
