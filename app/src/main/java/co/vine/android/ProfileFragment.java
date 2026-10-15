            package co.vine.android;

            import android.app.Activity;
            import android.content.BroadcastReceiver;
            import android.content.Context;
            import android.content.Intent;
            import android.content.SharedPreferences;
            import android.content.res.Resources;
            import android.graphics.Bitmap;
            import android.graphics.BitmapFactory;
            import android.graphics.PorterDuff;
            import android.graphics.PorterDuffColorFilter;
            import android.net.Uri;
            import android.os.Bundle;
            import android.support.annotation.NonNull;
            import android.support.v4.app.FragmentActivity;
            import android.text.SpannableStringBuilder;
            import android.text.TextUtils;
            import android.util.Log;
            import android.view.LayoutInflater;
            import android.view.Menu;
            import android.view.MenuInflater;
            import android.view.MenuItem;
            import android.view.View;
            import android.view.ViewGroup;
            import android.view.animation.Animation;
            import android.widget.BaseAdapter;
            import android.widget.ImageView;
            import android.widget.LinearLayout;
            import android.widget.ListAdapter;
            import android.widget.RelativeLayout;
            import android.widget.TextView;
            import co.vine.android.animation.HeightAnimation;
            import co.vine.android.api.VinePost;
            import co.vine.android.api.VineUser;
            import co.vine.android.cache.image.ImageKey;
            import co.vine.android.cache.image.UrlImage;
            import co.vine.android.cache.video.UrlVideo;
            import co.vine.android.cache.video.VideoKey;
            import co.vine.android.network.HttpResult;
            import co.vine.android.network.UrlCachePolicy;
            import co.vine.android.provider.Vine;
            import co.vine.android.provider.VineDatabaseHelper;
            import co.vine.android.service.VineService;
            import co.vine.android.util.BuildUtil;
            import co.vine.android.util.CrossConstants;
            import co.vine.android.util.Util;
            import co.vine.android.util.analytics.FlurryUtils;
            import co.vine.android.widget.FeedAdapter;
            import co.vine.android.widget.GenericVideoAdapter;
            import co.vine.android.widget.ModalView;
            import co.vine.android.widget.OnboardHelper;
            import co.vine.android.widget.SectionAdapter;
            import co.vine.android.widget.SpanClickListener;
            import co.vine.android.widget.TooltipOverlayView;
            import co.vine.android.widget.Typefaces;
            import co.vine.android.widget.TypefacesSpan;
            import com.twitter.android.widget.RefreshableListView;
            import java.util.ArrayList;
            import java.util.HashMap;
            
            
    public class ProfileFragment extends BaseTimelineFragment implements View.OnClickListener, ViewOffsetResolver, SpanClickListener, View.OnLongClickListener {
                public static final String ARG_FOLLOW_EVENT_SOURCE = "event_source";
                public static final String ARG_SHOW_PROFILE_ACTIONS = "show_profile_actions";
                public static final String ARG_USER_ID = "user_id";
                public static final String ARG_VANITY_URL = "vanity_url";
                private static final String EVENT_SOURCE_TITLE = "Profile: Tab ";
                public static final String FRAGMENT_TAG = "profile";
                public static final int MODE_LIKES = 2;
                public static final int MODE_POSTS = 1;
                private static final String STATE_HEADER_ADDED = "stated_header_added";
                private static final String STATE_MODE = "state_mode";
                private static final String TAG = "ProfileFragment";
                private TypefacesSpan mBold;
                private boolean mColorChangedReceiverIsRegistered;
                private int mCurrentMode;
                private boolean mFavoriteUser;
                protected String mFlurryFollowEventSource;
                private LinearLayout mFollowHeader;
                private boolean mHeaderAdded;
                private boolean mHideProfileReposts;
                private MenuItem mHideRevines;
                private boolean mIsMe;
                private int mLikePage;
                private int mPostPage;
                PrivateAccountAdapter mPrivateAdapter;
                private int mProfileBackground;
                ProfileHeaderAdapter mProfileHeaderAdapter;
                private ViewGroup mRootView;
                private SectionAdapter mSectionAdapter;
                private boolean mShowProfileActions;
                private boolean mTakeFocus;
                private TooltipOverlayView mTooltipOverlay;
                private VineUser mUser;
                private long mUserId;
                private ImageKey mUserProfileImageKeyForShortcut;
                private int mVineGreen;
                private final ColorChangedReceiver mColorChangedReceiver = new ColorChangedReceiver(this, null);
                private long mWaitingToStartUserId = -1;
                private Animation.AnimationListener mDrawerListener = new AnimationAnimationListenerC02541();
                private Runnable mFavoriteUserHintRunnable = new RunnableC02552();

        static  long access$100(ProfileFragment x0) {
            return x0.mWaitingToStartUserId;
                }

        static  ImageKey access$1000(ProfileFragment x0) {
            return x0.mUserProfileImageKeyForShortcut;
                }

        static  long access$102(ProfileFragment x0, long x1) {
            x0.mWaitingToStartUserId = x1;
                    return x1;
                }

        static  void access$1100(ProfileFragment x0, Bitmap x1) {
            x0.addProfileShortCut(x1);
                }

        static  long access$1200(ProfileFragment x0) {
            return x0.mUserId;
                }

        static  void access$1300(ProfileFragment x0) {
            x0.bindProfileDataAndCounts();
                }

        static  LinearLayout access$1400(ProfileFragment x0) {
            return x0.mFollowHeader;
                }

        static  void access$1500(ProfileFragment x0, long x1) {
            x0.setUserId(x1);
                }

        static  boolean access$1602(ProfileFragment x0, boolean x1) {
            x0.mHideProfileReposts = x1;
                    return x1;
                }

        static  boolean access$1700(ProfileFragment x0) {
            return x0.mFavoriteUser;
                }

        static  boolean access$1702(ProfileFragment x0, boolean x1) {
            x0.mFavoriteUser = x1;
                    return x1;
                }

        static  ViewGroup access$1800(ProfileFragment x0) {
            return x0.mRootView;
                }

        static  TooltipOverlayView access$1902(ProfileFragment x0, TooltipOverlayView x1) {
            x0.mTooltipOverlay = x1;
                    return x1;
                }

        static  SectionAdapter access$200(ProfileFragment x0) {
            return x0.mSectionAdapter;
                }

        static  SectionAdapter access$202(ProfileFragment x0, SectionAdapter x1) {
            x0.mSectionAdapter = x1;
                    return x1;
                }

        static  boolean access$300(ProfileFragment x0) {
            return x0.mIsMe;
                }

        static  boolean access$302(ProfileFragment x0, boolean x1) {
            x0.mIsMe = x1;
                    return x1;
                }

        static  VineUser access$400(ProfileFragment x0) {
            return x0.mUser;
                }

        static  VineUser access$402(ProfileFragment x0, VineUser x1) {
            x0.mUser = x1;
                    return x1;
                }

        static  void access$500(ProfileFragment x0, UrlCachePolicy x1) {
            x0.initProfile(x1);
                }

        static  boolean access$600(ProfileFragment x0) {
            return x0.isLocked();
                }

        static  boolean access$700(ProfileFragment x0) {
            return x0.mTakeFocus;
                }

        static  void access$800(ProfileFragment x0, boolean x1) {
            x0.setup(x1);
                }

        static  Runnable access$900(ProfileFragment x0) {
            return x0.mFavoriteUserHintRunnable;
                }

                
       class AnimationAnimationListenerC02541 implements Animation.AnimationListener {
           AnimationAnimationListenerC02541() {
                    }

                    @Override 
           public void onAnimationStart(Animation animation) {
               if (ProfileFragment.this.getActivity() instanceof HomeTabActivity) {
                   ((HomeTabActivity) ProfileFragment.this.getActivity()).setScrollAwayNavEnabled(false);
                        }
                    }

                    @Override 
           public void onAnimationEnd(Animation animation) {
               if (ProfileFragment.this.getActivity() instanceof HomeTabActivity) {
                   ((HomeTabActivity) ProfileFragment.this.getActivity()).setScrollAwayNavEnabled(true);
                        }
                    }

                    @Override 
           public void onAnimationRepeat(Animation animation) {
                    }
                }

                @Override 
       public void onCreate(Bundle savedInstanceState) {
           super.onCreate(savedInstanceState);
           setHasOptionsMenu(true);
           Bundle b = getArguments();
           setUserId(b.getLong("user_id", -1L));
           setFlurryEventSource("Profile: Tab 1");
           this.mFlurryFollowEventSource = b.getString(ARG_FOLLOW_EVENT_SOURCE);
           this.mTakeFocus = b.getBoolean("take_focus", false);
           if (this.mTakeFocus) {
               setFocused(true);
                    }
           this.mAppSessionListener = new ProfileListener();
           if (savedInstanceState != null) {
               this.mCurrentMode = savedInstanceState.getInt(STATE_MODE);
               if (savedInstanceState.containsKey(STATE_HEADER_ADDED)) {
                   this.mHeaderAdded = savedInstanceState.getBoolean(STATE_HEADER_ADDED);
                        }
                    } else {
               this.mCurrentMode = 1;
               this.mHeaderAdded = false;
                    }
           showSadface(false);
           if (this.mUser != null) {
               this.mUser.repostsEnabled = 1;
                    }
           if (b.getStringArrayList("vanity_url") != null) {
               this.mAppController.fetchUserId(b.getStringArrayList("vanity_url"));
                    } else if (this.mUserId > 0) {
               this.mIsMe = this.mUserId == this.mAppController.getActiveId();
                    }
           if (this.mIsMe) {
               bindProfileDataAndCounts();
                    }
           this.mShowProfileActions = b.getBoolean(ARG_SHOW_PROFILE_ACTIONS, true);
                }

                @Override 
       public void onResume() {
           super.onResume();
                    if (this.mUserId == getAppController().getActiveId()) {
               registerMergePostReceiver();
                    }
           if (this.mAdapter.isEmpty()) {
               fetchInitialRequest(UrlCachePolicy.NETWORK_THEN_CACHE);
                    }
           if (this.mIsMe) {
               this.mHideProfileReposts = Util.getDefaultSharedPrefs(getActivity()).getBoolean(Settings.PREF_HIDE_PROFILE_REPOSTS, false);
               setup(isFocused());
                    }
           if (getActivity() instanceof HomeTabActivity) {
               HomeTabActivity activity = (HomeTabActivity) getActivity();
               if (activity.shouldChangeActionBarColor()) {
                   setActionBarColor();
                        }
                    }
           initProfile(UrlCachePolicy.NETWORK_THEN_CACHE);
           if (!this.mColorChangedReceiverIsRegistered) {
               this.mColorChangedReceiverIsRegistered = true;
               getActivity().registerReceiver(this.mColorChangedReceiver, Util.COLOR_CHANGED_INTENT_FILTER, CrossConstants.BROADCAST_PERMISSION, null);
                    }
                }

       private void initProfile(UrlCachePolicy cachePolicy) {
           if (this.mUser != null) {
               bindProfileDataAndCounts();
               hideProgress(3);
                    } else if (this.mIsMe) {
               addRequest(this.mAppController.fetchUsersMe(this.mUserId, cachePolicy));
                    } else {
               addRequest(this.mAppController.fetchUser(this.mUserId, cachePolicy));
                    }
                }

                private void bindCachedProfileWhileLoading() {
                    if (!this.mIsMe || this.mUser != null || this.mProfileHeaderAdapter == null) {
                        return;
                    }
                    SharedPreferences prefs = Util.getDefaultSharedPrefs(getActivity());
                    String username = prefs.getString(Settings.PREF_NAME, null);
                    if (TextUtils.isEmpty(username)) {
                        return;
                    }
                    VineUser cachedUser = new VineUser();
                    cachedUser.userId = this.mUserId;
                    cachedUser.username = username;
                    cachedUser.description = prefs.getString(Settings.PREF_DESCRIPTION, null);
                    cachedUser.location = prefs.getString(Settings.PREF_LOCATION, null);
                    cachedUser.email = prefs.getString(Settings.PREF_EMAIL, null);
                    cachedUser.phoneNumber = prefs.getString(Settings.PREF_PHONE, null);
                    cachedUser.avatarUrl = prefs.getString(Settings.PREF_AVATAR_URL, null);
                    cachedUser.followingCount = prefs.getInt(Settings.PREF_FOLLOWING_COUNT, 0);
                    cachedUser.followerCount = prefs.getInt(Settings.PREF_FOLLOWER_COUNT, 0);
                    cachedUser.authoredPostCount = prefs.getInt(Settings.PREF_AUTHORED_POST_COUNT, 0);
                    cachedUser.postCount = prefs.getInt(Settings.PREF_POST_COUNT, 0);
                    cachedUser.likeCount = prefs.getInt(Settings.PREF_LIKE_COUNT, 0);
                    cachedUser.loopCount = prefs.getLong(Settings.PREF_LOOP_COUNT, 0L);
                    cachedUser.profileBackground = prefs.getInt("profile_background", Settings.DEFAULT_PROFILE_COLOR);
                    cachedUser.repostsEnabled = 1;
                    bindUserData(cachedUser);
                    this.mProfileHeaderAdapter.notifyDataSetChanged();
                    hideProgress(3);
                }

       private void bindProfileDataAndCounts() {
           if (this.mUser != null) {
               if (this.mIsMe) {
                   SharedPreferences pref = Util.getDefaultSharedPrefs(getActivity());
                   this.mUser.username = pref.getString(Settings.PREF_NAME, null);
                   this.mUser.description = pref.getString(Settings.PREF_DESCRIPTION, null);
                   this.mUser.location = pref.getString(Settings.PREF_LOCATION, null);
                   this.mUser.email = pref.getString(Settings.PREF_EMAIL, null);
                   this.mUser.phoneNumber = pref.getString(Settings.PREF_PHONE, null);
                   this.mUser.avatarUrl = pref.getString(Settings.PREF_AVATAR_URL, null);
                   this.mUser.followingCount = pref.getInt(Settings.PREF_FOLLOWING_COUNT, 0);
                   this.mUser.followerCount = pref.getInt(Settings.PREF_FOLLOWER_COUNT, 0);
                   this.mUser.authoredPostCount = pref.getInt(Settings.PREF_AUTHORED_POST_COUNT, 0);
                   this.mUser.postCount = pref.getInt(Settings.PREF_POST_COUNT, 0);
                   this.mUser.likeCount = pref.getInt(Settings.PREF_LIKE_COUNT, 0);
                   this.mUser.loopCount = pref.getLong(Settings.PREF_LOOP_COUNT, 0L);
                   this.mUser.profileBackground = pref.getInt("profile_background", Settings.DEFAULT_PROFILE_COLOR);
                        }
               bindUserData(this.mUser);
                    }
                }

       void bindUserData(@NonNull VineUser user) {
           if (user.profileBackground <= 0) {
               user.profileBackground = Settings.DEFAULT_PROFILE_COLOR;
                    }
           if (user.profileBackground > 0) {
               if (getActivity() instanceof ProfileActivity) {
                   ((BaseControllerActionBarActivity) getActivity()).setActionBarColor(user.profileBackground);
                   ((BaseControllerActionBarActivity) getActivity()).notifyColorChange(user.profileBackground);
                        }
               if (this.mFeedAdapter != null) {
                   this.mFeedAdapter.setProfileColor(user.profileBackground);
                        }
               this.mProfileBackground = user.profileBackground;
                    }
           this.mProfileHeaderAdapter.bindUser(user);
           if (user.hasFollowApprovalPending() && !this.mHeaderAdded) {
               LayoutInflater inflater = LayoutInflater.from(getActivity());
               LinearLayout approvalPending = (LinearLayout) inflater.inflate(C0257R.layout.follow_request_approval_pending, (ViewGroup) null, false);
               if (this.mListView != null) {
                   this.mListView.addHeaderView(approvalPending);
                   RelativeLayout pendingFollowAccept = (RelativeLayout) approvalPending.findViewById(C0257R.id.follow_request_accept_container);
                   pendingFollowAccept.setOnClickListener(this);
                   RelativeLayout pendingFollowReject = (RelativeLayout) approvalPending.findViewById(C0257R.id.follow_request_reject_container);
                   pendingFollowReject.setOnClickListener(this);
                   this.mFollowHeader = approvalPending;
                   this.mHeaderAdded = true;
                        }
               this.mFollowHeader.setVisibility(0);
                    }
           setRevineOptionText(user, user.areRepostsEnabled());
           if (user.profileBackground > 0 && this.mFeedAdapter != null && this.mListView != null) {
               this.mFeedAdapter.setProfileColor(user.profileBackground);
               RefreshableListView rlv = (RefreshableListView) this.mListView;
               int color = (-16777216) | user.profileBackground;
               rlv.setPullToRefreshBackgroundColor(color);
               rlv.colorizePTR(-1);
                    }
           this.mFavoriteUser = user.notifyPosts;
                }

                @Override 
       public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
           if (!this.mIsMe) {
               inflater.inflate(C0257R.menu.profile, menu);
                    }
                }

                @Override 
       public boolean onOptionsItemSelected(MenuItem item) {
                    Bitmap bitmap;
           int id = item.getItemId();
           Activity activity = getActivity();
           if (id == C0257R.id.block_or_report) {
               if (this.mUser != null) {
                   if (!this.mUser.isBlocking()) {
                       Intent i = new Intent(getActivity(), (Class<?>) ReportingActivity.class);
                       i.putExtra(ReportingFragment.EXTRA_MENU_TYPE, "user");
                       i.putExtra("userId", this.mUser.userId);
                       i.putExtra(ReportingFragment.EXTRA_BLOCKING_USER, this.mUser.isBlocking());
                       i.putExtra("username", this.mUser.username);
                       activity.startActivity(i);
                            } else {
                       this.mAppController.unblockUser(this.mUser.userId, null);
                            }
                        }
               return true;
                    }
           if (id == C0257R.id.hide_revines) {
               if (this.mUser != null) {
                   if (this.mUser.areRepostsEnabled()) {
                       addRequest(this.mAppController.disableReposts(this.mUserId));
                            } else {
                       addRequest(this.mAppController.enableReposts(this.mUserId));
                            }
                        }
               return true;
                    }
           if (id == C0257R.id.share_profile) {
               if (this.mUser != null) {
                   FlurryUtils.trackShareProfile();
                   FragmentActivity context = getActivity();
                   Intent share = new Intent("android.intent.action.SEND");
                   share.setType("text/plain");
                   share.addFlags(524288);
                   share.putExtra("android.intent.extra.SUBJECT", "");
                   share.putExtra("android.intent.extra.TEXT", context.getString(C0257R.string.share_profile_txt, new Object[]{this.mUser.username, String.valueOf(this.mUser.userId)}).replace("vine://user", BuildUtil.getWebsiteUrl(context) + "/u"));
                   startActivity(Intent.createChooser(share, getString(C0257R.string.send)));
                        }
               return true;
                    }
           if (id == C0257R.id.create_shortcut) {
               if (this.mUser != null) {
                   this.mUserProfileImageKeyForShortcut = new ImageKey(this.mUser.avatarUrl, true);
                   if (TextUtils.isEmpty(this.mUser.avatarUrl) || Util.isDefaultAvatarUrl(this.mUser.avatarUrl)) {
                       bitmap = BitmapFactory.decodeResource(getResources(), C0257R.drawable.avatar_large);
                            } else {
                       bitmap = this.mAppController.getPhotoBitmap(this.mUserProfileImageKeyForShortcut);
                            }
                   if (bitmap != null) {
                       addProfileShortCut(bitmap);
                            }
                        }
                    } else if (id == C0257R.id.favorite_user && this.mUser != null) {
               this.mFavoriteUser = !this.mFavoriteUser;
               this.mAppController.favoriteUser(this.mUserId, this.mFavoriteUser, this.mUser.isFollowing());
               getActivity().supportInvalidateOptionsMenu();
               if (this.mFavoriteUser) {
                   ModalView modal = new ModalView(getActivity(), this.mRootView, C0257R.layout.favorite_user_modal);
                   if (!TextUtils.isEmpty(this.mUser.username)) {
                       TextView textView = (TextView) modal.findViewById(C0257R.id.description);
                       textView.setText(getString(C0257R.string.favorited_confirmation, this.mUser.username));
                            }
                        }
               if (this.mTooltipOverlay != null) {
                            this.mTooltipOverlay.dismiss();
                        }
                    }
           return super.onOptionsItemSelected(item);
                }

       private void addProfileShortCut(Bitmap bitmap) {
           this.mUserProfileImageKeyForShortcut = null;
           FragmentActivity activity = getActivity();
           if (activity != null) {
               FlurryUtils.trackCreateProfileShortCut();
               Intent shortcutIntent = new Intent(activity, (Class<?>) StartActivity.class);
               shortcutIntent.setAction("android.intent.action.VIEW");
               shortcutIntent.setData(Uri.parse("https://vine.co/u/" + this.mUser.userId));
               shortcutIntent.setFlags(268435456);
               Intent addIntent = new Intent();
               addIntent.putExtra("android.intent.extra.shortcut.INTENT", shortcutIntent);
               addIntent.putExtra("android.intent.extra.shortcut.NAME", this.mUser.username);
               Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, 128, 128, true);
               addIntent.putExtra("android.intent.extra.shortcut.ICON", scaledBitmap);
               addIntent.setAction("com.android.launcher.action.INSTALL_SHORTCUT");
               activity.getApplicationContext().sendBroadcast(addIntent);
               Util.showCenteredToast(activity, C0257R.string.shorcut_created);
                    }
                }

                @Override 
       public void onPrepareOptionsMenu(Menu menu) {
           boolean isBlocking = this.mUser != null && this.mUser.isBlocking();
           MenuItem block = menu.findItem(C0257R.id.block_or_report);
           if (block != null) {
               if (isBlocking) {
                   block.setTitle(C0257R.string.unblock_person);
                        } else {
                   block.setTitle(C0257R.string.block_or_report);
                        }
                    }
           this.mHideRevines = menu.findItem(C0257R.id.hide_revines);
           boolean revinesEnabled = this.mUser == null || this.mUser.areRepostsEnabled();
           setRevineOptionText(this.mUser, revinesEnabled);
           MenuItem favoriteUser = menu.findItem(C0257R.id.favorite_user);
           if (favoriteUser != null) {
               favoriteUser.setVisible(isBlocking ? false : true);
               if (!isBlocking) {
                   favoriteUser.setIcon(this.mFavoriteUser ? C0257R.drawable.btn_favorite_profile_pressed : C0257R.drawable.btn_favorite_profile_default);
                        }
                    }
                }

                @Override 
       protected String fetchPosts(int page, long anchor, boolean userInitiated, UrlCachePolicy cachePolicy) {
           throw new IllegalStateException("Don't call fetch posts for ProfileFragment, this fragment handles its own fetching.");
                }

                @Override 
       public void onMoveTo(int oldPosition) {
           super.onMoveTo(oldPosition);
           setup(isFocused());
           bindProfileDataAndCounts();
                }

                @Override 
       public void onViewCreated(View view, Bundle savedInstanceState) {
           super.onViewCreated(view, savedInstanceState);
           setRefreshableHeaderOffset(6);
           this.mListView.setOnScrollListener(this);
           showProgress(3);
                }

                @Override 
       public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
           View view = createView(inflater, C0257R.layout.msg_list_fragment_profile, container);
           this.mRootView = (ViewGroup) view.findViewById(C0257R.id.layout_root);
           return view;
                }

                @Override 
       public void onActivityCreated(Bundle savedInstanceState) {
           super.onActivityCreated(savedInstanceState);
           if (getArguments().getBoolean("take_focus", false)) {
               Activity activity = getActivity();
               if (activity instanceof HomeTabActivity) {
                   HomeTabActivity callback = (HomeTabActivity) activity;
                   callback.showPage(3, this);
                   if (BuildUtil.isLogsOn()) {
                       Log.d(TAG, "Me tab took focus.");
                            }
                        }
                    }
           this.mBold = new TypefacesSpan(null, Typefaces.get(getActivity()).getContentTypeface(1, 4));
           this.mVineGreen = getActivity().getResources().getColor(C0257R.color.vine_green);
           setup(this.mTakeFocus);
                    bindCachedProfileWhileLoading();
                }

                @Override 
       public void onActivityResult(int requestCode, int resultCode, Intent data) {
           super.onActivityResult(requestCode, resultCode, data);
                    switch (requestCode) {
                        case 1:
                   if (resultCode == 1 && this.mUserId > 0) {
                       addRequest(this.mAppController.fetchFriends(1, 1));
                       addRequest(this.mAppController.fetchFriends(1, 0));
                                break;
                            }
                            break;
                    }
                }

                @Override 
       public void onSaveInstanceState(Bundle outState) {
           super.onSaveInstanceState(outState);
           outState.putInt(STATE_MODE, this.mCurrentMode);
           outState.putBoolean(STATE_HEADER_ADDED, this.mHeaderAdded);
                }

                @Override 
       protected void onScrollLastItem() {
           if (this.mRefreshable && this.mNextPage > 0 && this.mFeedAdapter.getCount() <= 400) {
               this.mFeedAdapter.onPause(isFocused());
               this.mLastFetchType = 1;
                        switch (this.mCurrentMode) {
                            case 1:
                       showProgress(1);
                       fetchContent(1, true, UrlCachePolicy.FORCE_REFRESH);
                       this.mPostPage++;
                       FlurryUtils.trackTimeLinePageScroll(getClass().getName() + "_Post", this.mPostPage);
                                break;
                            case 2:
                       showProgress(1);
                       fetchContent(1, true, UrlCachePolicy.FORCE_REFRESH);
                       this.mLikePage++;
                       FlurryUtils.trackTimeLinePageScroll(getClass().getName() + "_Like", this.mLikePage);
                                break;
                        }
                    }
                }

                @Override 
       protected void refresh() {
                    
                    
                    
                    
                    
                    clearPendingRequests(2);
           hideProgress(2);
           fetchContent(2, true, UrlCachePolicy.FORCE_REFRESH);
           if (this.mIsMe) {
               addRequest(this.mAppController.fetchUsersMe(this.mAppController.getActiveId(), UrlCachePolicy.FORCE_REFRESH));
                    } else {
               addRequest(this.mAppController.fetchUser(this.mUserId, UrlCachePolicy.FORCE_REFRESH));
                    }
                }

       private void setRevineOptionText(VineUser user, boolean repostsEnabled) {
           MenuItem hideRevines = this.mHideRevines;
           if (hideRevines != null) {
               if (user != null && !user.isFollowing()) {
                   hideRevines.setVisible(false);
                        } else if (repostsEnabled) {
                   hideRevines.setVisible(true);
                   hideRevines.setTitle(C0257R.string.hide_revines);
                        } else {
                   hideRevines.setVisible(true);
                   hideRevines.setTitle(C0257R.string.show_revines);
                        }
                    }
                }

       public void changeMode(int mode) {
                    switch (mode) {
                        case 1:
                   if (this.mCurrentMode != 1) {
                       this.mCurrentMode = 1;
                       this.mSectionAdapter = new SectionAdapter(new BaseAdapter[]{this.mProfileHeaderAdapter});
                       this.mListView.setAdapter((ListAdapter) this.mSectionAdapter);
                       setFlurryEventSource("Profile: Tab 1");
                       fetchContent(3, true, UrlCachePolicy.FORCE_REFRESH);
                            }
                            break;
                        case 2:
                   if (this.mCurrentMode != 2) {
                       this.mCurrentMode = 2;
                       setFlurryEventSource("Profile: Tab 2");
                       this.mSectionAdapter = new SectionAdapter(new BaseAdapter[]{this.mProfileHeaderAdapter});
                       this.mListView.setAdapter((ListAdapter) this.mSectionAdapter);
                       fetchContent(3, true, UrlCachePolicy.FORCE_REFRESH);
                            }
                            break;
                    }
                }

                @Override 
       public void onPause() {
           super.onPause();
           this.mFeedAdapter.onPause(isFocused());
                }

                @Override 
       public void onDestroy() {
           super.onDestroy();
           if (this.mColorChangedReceiverIsRegistered) {
               getActivity().unregisterReceiver(this.mColorChangedReceiver);
               this.mColorChangedReceiverIsRegistered = false;
                    }
                }

       private void setup(boolean takeFocus) {
           if (this.mSectionAdapter == null) {
               if (isLocked()) {
                   this.mProfileHeaderAdapter = new ProfileHeaderAdapter(getActivity(), this.mAppController, this, true, this.mUser.isBlocked(), this.mFlurryFollowEventSource, this.mHideProfileReposts, this.mShowProfileActions);
                   this.mPrivateAdapter = new PrivateAccountAdapter(this.mUser.isBlocked(), this.mUser.isPrivateLocked());
                   this.mSectionAdapter = new SectionAdapter(new BaseAdapter[]{this.mProfileHeaderAdapter, this.mPrivateAdapter});
                   this.mListView.setAdapter((ListAdapter) this.mSectionAdapter);
                   this.mSectionAdapter.notifyDataSetChanged();
                   return;
                        }
               this.mProfileHeaderAdapter = new ProfileHeaderAdapter(getActivity(), this.mAppController, this, false, false, this.mFlurryFollowEventSource, this.mHideProfileReposts, this.mShowProfileActions);
               this.mSectionAdapter = new SectionAdapter(new BaseAdapter[]{this.mProfileHeaderAdapter, this.mFeedAdapter});
               if (takeFocus) {
                   this.mFeedAdapter.onResume(true);
                        }
               this.mListView.setAdapter((ListAdapter) this.mSectionAdapter);
               this.mFeedAdapter.setOffsetResolver(this);
               return;
                    }
           if (this.mFeedAdapter != null && !isLocked() && takeFocus) {
               this.mFeedAdapter.onResume(true);
                    }
                }

                @Override 
                protected void onRevineStateReconciled(long postId, boolean revined) {
                    super.onRevineStateReconciled(postId, revined);
                    if (!revined) {
                        removeUnrevinedPost(postId, 200);
                    }
                }

                private void removeUnrevinedPost(long postId, int statusCode) {
                    if (statusCode < 200 || statusCode >= 300 || this.mCurrentMode == 2) {
                        return;
                    }
                    if (this.mFeedAdapter instanceof FeedAdapter) {
                        ((FeedAdapter) this.mFeedAdapter).removePost(postId);
                    }
                }

                @Override 
       protected void fetchContent(int fetchType, boolean userInitiated, UrlCachePolicy cachePolicy) {
           this.mLastFetchType = fetchType;
           if (!hasPendingRequest(fetchType)) {
                        switch (fetchType) {
                            case 2:
                       showProgress(fetchType);
                            case 3:
                            case 4:
                       this.mNextPage = 1;
                       this.mAnchor = 0L;
                                break;
                        }
               int postGroup = this.mHideProfileReposts ? 10 : 2;
               if (this.mCurrentMode == 2) {
                   postGroup = 3;
                        }
               addRequest(this.mAppController.fetchPosts(this.mAppController.getActiveSession(), 10, this.mUserId, postGroup, this.mNextPage, this.mAnchor, userInitiated, String.valueOf(this.mUserId), null, null, cachePolicy), fetchType);
                    }
                }

                @Override 
       public void onClick(View view) {
                    View v;
           int id = view.getId();
           if (id == C0257R.id.user_image || id == C0257R.id.profile_follow || id == C0257R.id.settings) {
               this.mProfileHeaderAdapter.onProfileHeaderClick(view, this, this.mUser);
               return;
                    }
           if (id == C0257R.id.message_button) {
                        if (this.mWaitingToStartUserId == -1) {
                   this.mWaitingToStartUserId = this.mUserId;
                   this.mAppController.fetchConversationRowIdFromUserRemoteId(this.mUserId, 1);
                   return;
                        }
               return;
                    }
           if (id == C0257R.id.postsParent || id == C0257R.id.likesParent) {
               this.mProfileHeaderAdapter.onProfileHeaderClick(view, this, this.mUser);
               return;
                    }
           if (id == C0257R.id.follow_request_accept_container) {
               this.mAppController.acceptFollowRequest(this.mAppController.getActiveSession(), this.mUserId);
               return;
                    }
           if (id == C0257R.id.follow_request_reject_container) {
               this.mAppController.acceptRejectRequest(this.mAppController.getActiveSession(), this.mUserId);
               return;
                    }
           if (id == C0257R.id.posts_dropdown) {
               onDropDownClicked();
               return;
                    }
           if (id == C0257R.id.postsDropDownHideRevine) {
               Resources res = getResources();
               if (this.mUser != null && res != null && (v = getView()) != null) {
                   View postsButton = v.findViewById(C0257R.id.postsParent);
                   View dropDown = v.findViewById(C0257R.id.postsLikeDropDown);
                   if (dropDown != null) {
                       if (dropDown.getVisibility() == 0) {
                           dismissDropdown();
                           return;
                                }
                       if (postsButton != null) {
                           ProfileHeaderAdapter.DropdownHolder tag = (ProfileHeaderAdapter.DropdownHolder) dropDown.getTag();
                           String hideRevines = res.getString(this.mHideProfileReposts ? C0257R.string.show_revines : C0257R.string.hide_revines);
                           tag.revineParent.setVisibility(0);
                           Util.setTextWithSpan(this.mBold, hideRevines, tag.revineLabel);
                           SpannableStringBuilder contentSb = new SpannableStringBuilder(hideRevines);
                           Util.safeSetSpan(contentSb, this.mBold, 0, contentSb.length(), 33);
                           tag.revineLabel.setText(contentSb);
                           tag.revineIcon.setImageResource(this.mHideProfileReposts ? C0257R.drawable.ic_show_revines : C0257R.drawable.ic_hide_revines);
                           Animation anim = new HeightAnimation(getActivity(), dropDown, true, 300L);
                           anim.setAnimationListener(this.mDrawerListener);
                           dropDown.startAnimation(anim);
                           View arrowImage = v.findViewById(C0257R.id.profile_arrow_image);
                           if (arrowImage instanceof ImageView) {
                               if (this.mProfileBackground == Settings.DEFAULT_PROFILE_COLOR) {
                                   ((ImageView) arrowImage).setColorFilter(new PorterDuffColorFilter((-16777216) | this.mVineGreen, PorterDuff.Mode.SRC_IN));
                                   return;
                                        } else {
                                   ((ImageView) arrowImage).setColorFilter(new PorterDuffColorFilter((-16777216) | this.mProfileBackground, PorterDuff.Mode.SRC_IN));
                                   return;
                                        }
                                    }
                           return;
                                }
                       return;
                            }
                   return;
                        }
               return;
                    }
           if (id == C0257R.id.followers || id == C0257R.id.following) {
               Intent i = new Intent(getActivity(), (Class<?>) UsersActivity.class);
               i.putExtra("p_id", this.mUserId);
               int type = view.getId() == C0257R.id.followers ? 2 : 1;
               i.putExtra("u_type", type);
               startActivity(i);
               return;
                    }
           super.onClick(view);
                }

                @Override 
       public boolean onLongClick(View view) {
           int id = view.getId();
           if (id != C0257R.id.user_image && id != C0257R.id.profile_follow && id != C0257R.id.settings) {
                        return false;
                    }
           this.mProfileHeaderAdapter.onProfileHeaderLongClick(view);
           return true;
                }

       public void onDropDownClicked() {
           this.mHideProfileReposts = !this.mHideProfileReposts;
           FlurryUtils.trackFilterProfileReposts(this.mHideProfileReposts, this.mUser.following > 0, this.mIsMe);
           if (this.mIsMe) {
               SharedPreferences.Editor e = Util.getDefaultSharedPrefs(getActivity()).edit();
               e.putBoolean(Settings.PREF_HIDE_PROFILE_REPOSTS, this.mHideProfileReposts);
               e.apply();
                    } else {
               this.mAppController.setHideProfileReposts(this.mUserId, this.mHideProfileReposts);
                    }
                    clearPendingRequests(3);
                    if (this.mHideProfileReposts && this.mFeedAdapter instanceof FeedAdapter) {
                        ((FeedAdapter) this.mFeedAdapter).removeReposts();
                    }
            this.mFeedAdapter.onPause(isFocused());
            fetchContent(3, true, UrlCachePolicy.NETWORK_THEN_CACHE);
            dismissDropdown();
                }

       private void dismissDropdown() {
           View view = getView();
           if (view != null) {
               View dropDown = view.findViewById(C0257R.id.postsLikeDropDown);
               if (dropDown != null) {
                   Animation anim = new HeightAnimation(getActivity(), dropDown, false, 300L);
                   anim.setAnimationListener(this.mDrawerListener);
                   dropDown.startAnimation(anim);
                        }
               View arrowImage = view.findViewById(C0257R.id.profile_arrow_image);
               if (arrowImage instanceof ImageView) {
                   ((ImageView) arrowImage).setColorFilter(new PorterDuffColorFilter(1275068416, PorterDuff.Mode.SRC_IN));
                        }
                    }
                }

                @Override 
       public int getOffset(BaseAdapter me) {
           return this.mSectionAdapter.getPositionOffset(this.mSectionAdapter.getMyAdapterIndex(me));
                }

                @Override 
       public void setActionBarColor() {
           Activity activity = getActivity();
           if (activity instanceof BaseActionBarActivity) {
               if (this.mProfileBackground == 0) {
                   this.mProfileBackground = Util.getDefaultSharedPrefs(getActivity()).getInt("profile_background", Settings.DEFAULT_PROFILE_COLOR);
                        }
               ((BaseActionBarActivity) activity).setActionBarColor(this.mProfileBackground);
                    }
                }

       private void setUserId(long userId) {
           this.mUserId = userId;
           this.mVineSpanClicker.setUserId(this.mUserId);
                }

       private boolean isLocked() {
                    return (this.mUser == null || this.mIsMe || (!this.mUser.isPrivateLocked() && !this.mUser.isBlocked())) ? false : true;
                }

       public long getUserId() {
           return this.mUserId;
                }

       class ProfileListener extends BaseTimelineFragment.TimeLineSessionListener {
           ProfileListener() {
                        super();
                    }

                    @Override 
           public void onAbortAllRequestsComplete() {
               ProfileFragment.this.refresh();
                    }

                    @Override 
           public void onGetConversationRowIdComplete(long recipientId, boolean isRecipientExternal, long conversationObjectId, String username, boolean amFollowing) {
                        if (recipientId == ProfileFragment.access$100(ProfileFragment.this)) {
                   ProfileFragment.access$102(ProfileFragment.this, -1L);
                   ProfileFragment.this.mAppController.clearUnreadMessageCount(conversationObjectId);
                   ProfileFragment.this.startActivity(ConversationActivity.getIntent(ProfileFragment.this.getActivity(), conversationObjectId, username, recipientId, isRecipientExternal, amFollowing, true));
                        }
                    }

                    @Override 
           public void onGetTimeLineComplete(String reqId, int statusCode, String reasonPhrase, int type, int count, boolean memory, ArrayList<VinePost> posts, String tag, int pageType, int next, int previous, long anchor, boolean userInitiated, int size, String title, UrlCachePolicy cachePolicy, Bundle bundle) {
               PendingRequest req = ProfileFragment.this.removeRequest(reqId);
               if (req != null) {
                   if (statusCode == 200) {
                        if (ProfileFragment.this.mHideProfileReposts && posts != null) {
                            ArrayList<VinePost> filteredPosts = new ArrayList<>();
                            for (VinePost post : posts) {
                                if (post != null && post.repost == null && post.myRepostId <= 0) {
                                    filteredPosts.add(post);
                                }
                            }
                            posts = filteredPosts;
                        }
                       int tab = 0;
                       if (type == 3) {
                           tab = 1;
                                }
                       ProfileFragment.this.mProfileHeaderAdapter.updatePostCount(size, tab);
                       ProfileFragment.this.mProfileHeaderAdapter.notifyDataSetChanged();
                            }
                   if (memory && statusCode == 200) {
                       ProfileFragment.this.mNextPage = next;
                       ProfileFragment.this.mAnchor = anchor;
                       if (req.fetchType == 1) {
                           ((FeedAdapter) ProfileFragment.this.mFeedAdapter).addOlderPosts(posts);
                                } else {
                           ((FeedAdapter) ProfileFragment.this.mFeedAdapter).replacePosts(posts);
                                }
                       if (ProfileFragment.access$200(ProfileFragment.this).getNumberOfAdapters() == 1) {
                           ProfileFragment.access$202(ProfileFragment.this, new SectionAdapter(new BaseAdapter[]{ProfileFragment.this.mProfileHeaderAdapter, ProfileFragment.this.mFeedAdapter}));
                           ProfileFragment.this.mListView.setAdapter((ListAdapter) ProfileFragment.access$200(ProfileFragment.this));
                                }
                       ProfileFragment.this.mAppController.saveLoadedPosts(posts, type, tag, pageType, next, previous, anchor, false);
                            }
                   if (!cachePolicy.mNetworkDataAllowed) {
                                
                                
                                
                                ProfileFragment.this.hideProgress(req.fetchType);
                                ProfileFragment.this.fetchInitialRequest(UrlCachePolicy.NETWORK_THEN_CACHE);
                            } else {
                                ProfileFragment.this.hideProgress(req.fetchType);
                            }
                        }
                    }

                    @Override 
           public void onTrimMemory(int level) {
               super.onTrimMemory(level);
               GenericVideoAdapter adapter = ProfileFragment.this.mFeedAdapter;
               if (adapter != null) {
                   adapter.onTrimMemory(level);
                        }
                    }

                    @Override 
           public void onGetUsersMeComplete(String reqId, int statusCode, String reasonPhrase, long sessionOwnerId, VineUser user, UrlCachePolicy policy) {
               PendingRequest req = ProfileFragment.this.removeRequest(reqId);
               if (req != null) {
                   if (statusCode == 200 && ProfileFragment.access$300(ProfileFragment.this)) {
                       ProfileFragment.access$402(ProfileFragment.this, user);
                       if (user != null) {
                           ProfileFragment.this.bindUserData(user);
                           ProfileFragment.this.mProfileHeaderAdapter.notifyDataSetChanged();
                                }
                            }
                   ProfileFragment.this.hideProgress(req.fetchType);
                   Intent intent = new Intent(VineService.ACTION_PROFILE_COLOR_CHANGED);
                   ProfileFragment.this.getActivity().sendBroadcast(intent, CrossConstants.BROADCAST_PERMISSION);
                   if (!policy.mNetworkDataAllowed) {
                       ProfileFragment.access$500(ProfileFragment.this, UrlCachePolicy.NETWORK_THEN_CACHE);
                            }
                        }
                    }

                    @Override 
           public void onVideoPathObtained(HashMap<VideoKey, UrlVideo> videos) {
               ProfileFragment.this.mFeedAdapter.onVideoPathObtained(videos);
                    }

                    @Override 
           public void onVideoPathError(VideoKey key, HttpResult result) {
                    }

                    @Override 
           public void onPhotoImageError(ImageKey key, HttpResult result) {
                    }

                    @Override 
           public void onGetUserComplete(String reqId, int statusCode, String reasonPhrase, VineUser user, UrlCachePolicy policy) {
               PendingRequest req = ProfileFragment.this.removeRequest(reqId);
               if (req != null) {
                   if (statusCode == 200) {
                       ProfileFragment.access$402(ProfileFragment.this, user);
                       if (ProfileFragment.access$600(ProfileFragment.this)) {
                           ProfileFragment.access$202(ProfileFragment.this, null);
                           ProfileFragment.access$800(ProfileFragment.this, ProfileFragment.this.isFocused() || ProfileFragment.access$700(ProfileFragment.this));
                                }
                       if (ProfileFragment.this.mProfileHeaderAdapter != null && user != null) {
                           ProfileFragment.this.bindUserData(user);
                           ProfileFragment.this.mProfileHeaderAdapter.notifyDataSetChanged();
                                }
                       if (user != null && user.isFollowing() && !user.notifyPosts) {
                           ProfileFragment.this.mHandler.postDelayed(ProfileFragment.access$900(ProfileFragment.this), 750L);
                                }
                            }
                   if (!policy.mNetworkDataAllowed) {
                       ProfileFragment.access$500(ProfileFragment.this, UrlCachePolicy.NETWORK_THEN_CACHE);
                            }
                   ProfileFragment.this.hideProgress(req.fetchType);
                        }
                    }

                    @Override 
           public void onPhotoImageLoaded(HashMap<ImageKey, UrlImage> images) {
                        UrlImage urlImage;
               if (ProfileFragment.this.mProfileHeaderAdapter != null) {
                   ProfileFragment.this.mProfileHeaderAdapter.onImageLoaded(images);
                   ProfileFragment.this.mFeedAdapter.setImages(images);
                        }
               if (ProfileFragment.access$1000(ProfileFragment.this) != null && (urlImage = images.get(ProfileFragment.access$1000(ProfileFragment.this))) != null && urlImage.isValid()) {
                   ProfileFragment.access$1100(ProfileFragment.this, urlImage.bitmap);
                        }
                    }

                    @Override 
           public void onLikePost(String reqId, int statusCode, String reasonPhrase, long postId) {
               ProfileFragment.this.removeRequest(reqId);
               if (statusCode >= 200 && statusCode < 300) {
                      ProfileFragment.this.mLikeCache.like(postId);
                      ProfileFragment.this.mFeedAdapter.showLikedByMe(postId);
                  } else {
                   ProfileFragment.this.mLikeCache.removeEntry(postId);
                   ProfileFragment.this.mFeedAdapter.showUnlikedByMe(postId);
                        }
                    }

                    @Override 
           public void onUnlikePost(String reqId, int statusCode, String reasonPhrase, long postId) {
               ProfileFragment.this.removeRequest(reqId);
               if (statusCode >= 200 && statusCode < 300) {
                      ProfileFragment.this.mLikeCache.unlike(postId);
                      ProfileFragment.this.mFeedAdapter.showUnlikedByMe(postId);
                  } else {
                   if (statusCode == 404) {
                       ProfileFragment.this.mLikeCache.removeEntry(postId);
                       ProfileFragment.this.mFeedAdapter.showUnlikedByMe(postId);
                            } else {
                       ProfileFragment.this.mLikeCache.like(postId);
                       ProfileFragment.this.mFeedAdapter.showLikedByMe(postId);
                            }
                        }
                    }

                    @Override 
           public void onUnrevine(String reqId, int statusCode, String reasonPhrase, long postId) {
               if (statusCode == 200) {
                   ((FeedAdapter) ProfileFragment.this.mFeedAdapter).removePost(postId);
                        }
               super.onUnrevine(reqId, statusCode, reasonPhrase, postId);
                    }

                    @Override 
           public void onBlockUserComplete(String reqId, int statusCode, String reasonPhrase, boolean userBlocked, String blockedUsername) {
              if (statusCode == 200) {
                  if (ProfileFragment.access$400(ProfileFragment.this) != null) {
                      ProfileFragment.access$400(ProfileFragment.this).blocking = userBlocked ? 1 : 0;
                            }
                  ProfileFragment.this.getActivity().supportInvalidateOptionsMenu();
                  String username = isUsernameValid() ? ProfileFragment.access$400(ProfileFragment.this).username : ProfileFragment.this.getString(C0257R.string.username_placeholder);
                  if (!TextUtils.isEmpty(blockedUsername)) {
                      username = blockedUsername;
                            }
                  if (userBlocked) {
                      Util.showDefaultToast(ProfileFragment.this.getActivity(), ProfileFragment.this.getString(C0257R.string.user_blocked, username));
                      VineDatabaseHelper dbHelper = VineDatabaseHelper.getDatabaseHelper(ProfileFragment.this.getActivity());
                      dbHelper.removeUserWithType(ProfileFragment.access$1200(ProfileFragment.this), 2, true, Vine.UserGroupsView.CONTENT_URI_FOLLOWERS);
                      return;
                            }
                  Util.showDefaultToast(ProfileFragment.this.getActivity(), ProfileFragment.this.getString(C0257R.string.user_blocked_error));
                  return;
                        }
              Util.showDefaultToast(ProfileFragment.this.getActivity(), ProfileFragment.this.getString(C0257R.string.user_blocked_error));
                    }

                    @Override 
          public void onUnblockUserComplete(String reqId, int statusCode, String reasonPhrase, boolean userUnblocked, String blockedUsername) {
              if (statusCode == 200) {
                  if (ProfileFragment.access$400(ProfileFragment.this) != null) {
                      ProfileFragment.access$400(ProfileFragment.this).blocking = !userUnblocked ? 1 : 0;
                            }
                  ProfileFragment.this.getActivity().supportInvalidateOptionsMenu();
                  String username = isUsernameValid() ? ProfileFragment.access$400(ProfileFragment.this).username : ProfileFragment.this.getString(C0257R.string.username_placeholder);
                  if (!TextUtils.isEmpty(blockedUsername)) {
                      username = blockedUsername;
                            }
                  if (userUnblocked) {
                      Util.showDefaultToast(ProfileFragment.this.getActivity(), ProfileFragment.this.getString(C0257R.string.user_unblocked, username));
                      ProfileFragment.this.addRequest(ProfileFragment.this.mAppController.fetchUser(ProfileFragment.access$1200(ProfileFragment.this), UrlCachePolicy.FORCE_REFRESH));
                      return;
                            } else {
                      Util.showDefaultToast(ProfileFragment.this.getActivity(), ProfileFragment.this.getString(C0257R.string.user_blocked_error));
                      return;
                            }
                        }
              Util.showDefaultToast(ProfileFragment.this.getActivity(), ProfileFragment.this.getString(C0257R.string.user_unblocked_error));
                    }

                    @Override 
          public void onEnableUserRepostsComplete(String reqId, int statusCode, String reasonPhrase, boolean success) {
              if (ProfileFragment.this.removeRequest(reqId) != null) {
                  if (statusCode == 200) {
                      if (ProfileFragment.access$400(ProfileFragment.this) != null) {
                          ProfileFragment.access$400(ProfileFragment.this).repostsEnabled = success ? 1 : 0;
                          ProfileFragment.this.getActivity().supportInvalidateOptionsMenu();
                          if (success) {
                              String username = isUsernameValid() ? ProfileFragment.access$400(ProfileFragment.this).username : ProfileFragment.this.getString(C0257R.string.username_placeholder_alt);
                              Util.showDefaultToast(ProfileFragment.this.getActivity(), ProfileFragment.this.getString(C0257R.string.reposts_enabled, username));
                              return;
                                    } else {
                              Util.showDefaultToast(ProfileFragment.this.getActivity(), ProfileFragment.this.getString(C0257R.string.reposts_enabled_fail));
                              return;
                                    }
                                }
                      return;
                            }
                  Util.showDefaultToast(ProfileFragment.this.getActivity(), ProfileFragment.this.getString(C0257R.string.reposts_enabled_fail));
                        }
                    }

                    @Override 
          public void onDisableUserRepostsComplete(String reqId, int statusCode, String reasonPhrase, boolean success) {
              if (ProfileFragment.this.removeRequest(reqId) != null) {
                  if (statusCode == 200) {
                      if (ProfileFragment.access$400(ProfileFragment.this) != null) {
                          ProfileFragment.access$400(ProfileFragment.this).repostsEnabled = success ? 0 : 1;
                          ProfileFragment.this.getActivity().supportInvalidateOptionsMenu();
                          if (success) {
                              String username = isUsernameValid() ? ProfileFragment.access$400(ProfileFragment.this).username : ProfileFragment.this.getString(C0257R.string.username_placeholder_alt);
                              Util.showDefaultToast(ProfileFragment.this.getActivity(), ProfileFragment.this.getString(C0257R.string.reposts_disabled, username));
                              return;
                                    } else {
                              Util.showDefaultToast(ProfileFragment.this.getActivity(), ProfileFragment.this.getString(C0257R.string.reposts_disabled_fail));
                              return;
                                    }
                                }
                      return;
                            }
                  Util.showDefaultToast(ProfileFragment.this.getActivity(), ProfileFragment.this.getString(C0257R.string.reposts_disabled_fail));
                        }
                    }

                    @Override 
          public void onReportUserComplete(String reqId, int statusCode, String reasonPhrase) {
                        Activity activity;
              if (statusCode != 200 && (activity = ProfileFragment.this.getActivity()) != null) {
                  Util.showDefaultToast(activity, ProfileFragment.this.getString(C0257R.string.user_reported_error));
                        }
                    }

          private boolean isUsernameValid() {
                        return (ProfileFragment.access$400(ProfileFragment.this) == null || TextUtils.isEmpty(ProfileFragment.access$400(ProfileFragment.this).username)) ? false : true;
                    }

                    @Override 
          public void onDeletePostComplete(String reqId, long postId, int statusCode, String reasonPhrase) {
              super.onDeletePostComplete(reqId, postId, statusCode, reasonPhrase);
              if (statusCode >= 200 && statusCode < 300) {
                  if (ProfileFragment.this.mFeedAdapter != null) {
                      ((FeedAdapter) ProfileFragment.this.mFeedAdapter).removePost(postId);
                            }
                  ProfileFragment.access$200(ProfileFragment.this).notifyDataSetChanged();
                  ProfileFragment.access$1300(ProfileFragment.this);
                        }
                    }

                    @Override 
          public void onRespondFollowRequestComplete(String reqId, int statusCode, String reasonPhrase, boolean accept, long userId) {
              if (statusCode == 200) {
                  if (ProfileFragment.access$1400(ProfileFragment.this) != null) {
                      View inner = ProfileFragment.access$1400(ProfileFragment.this).findViewById(C0257R.id.follow_request_header_inner);
                      inner.setLayoutParams(new LinearLayout.LayoutParams(-1, 0));
                      ProfileFragment.access$1400(ProfileFragment.this).setVisibility(8);
                      return;
                            }
                  return;
                        }
              if (accept) {
                  Util.showCenteredToast(ProfileFragment.this.getActivity(), C0257R.string.accept_failed);
                        } else {
                  Util.showCenteredToast(ProfileFragment.this.getActivity(), C0257R.string.deny_failed);
                        }
                    }

                    @Override 
          public void onGetUserIdComplete(String reqId, int statusCode, String reasonPhrase, long userId) {
                        if (userId > 0) {
                  ProfileFragment.access$1500(ProfileFragment.this, userId);
                  ProfileFragment.access$302(ProfileFragment.this, ProfileFragment.access$1200(ProfileFragment.this) == ProfileFragment.this.mAppController.getActiveId());
                  if (ProfileFragment.access$300(ProfileFragment.this)) {
                      ProfileFragment.access$1602(ProfileFragment.this, Util.getDefaultSharedPrefs(ProfileFragment.this.getActivity()).getBoolean(Settings.PREF_HIDE_PROFILE_REPOSTS, false));
                      ProfileFragment.access$800(ProfileFragment.this, ProfileFragment.this.isFocused() || ProfileFragment.access$700(ProfileFragment.this));
                      ProfileFragment.access$500(ProfileFragment.this, UrlCachePolicy.CACHE_ONLY);
                      return;
                            }
                  return;
                        }
              ProfileFragment.this.getActivity().finish();
                    }

                    @Override 
          public void onFavoriteUserComplete(String reqId, int statusCode, String reasonPhrase) {
              VineUser user = ProfileFragment.access$400(ProfileFragment.this);
              if (statusCode != 200) {
                  ProfileFragment.access$1702(ProfileFragment.this, ProfileFragment.access$1700(ProfileFragment.this) ? false : true);
                  ProfileFragment.this.getActivity().supportInvalidateOptionsMenu();
                  Util.showCenteredToast(ProfileFragment.this.getActivity(), C0257R.string.generic_error);
                        } else if (user != null && !user.isFollowing()) {
                  user.following = 1;
                  ProfileFragment.this.mProfileHeaderAdapter.updateFollowButtonState(user);
                        }
                    }

                    @Override 
          public void onFollowComplete(String reqId, int statusCode, String reasonPhrase, long userId) {
              if (statusCode == 200) {
                  ProfileFragment.this.mHandler.postDelayed(ProfileFragment.access$900(ProfileFragment.this), 500L);
                        }
                    }
                }

      class PrivateAccountAdapter extends BaseAdapter {
                    boolean blocked;
                    boolean priv;

          public PrivateAccountAdapter(boolean blocked, boolean priv) {
              this.blocked = blocked;
              this.priv = priv;
                    }

                    @Override 
          public int getCount() {
              return 1;
                    }

                    @Override 
          public Object getItem(int i) {
              return null;
                    }

                    @Override 
          public long getItemId(int i) {
              return 0L;
                    }

                    @Override 
          public View getView(int i, View view, ViewGroup root) {
                        return this.blocked ? LayoutInflater.from(ProfileFragment.this.getActivity()).inflate(C0257R.layout.profile_blocked, root, false) : LayoutInflater.from(ProfileFragment.this.getActivity()).inflate(C0257R.layout.profile_locked, root, false);
                    }
                }

      private class ColorChangedReceiver extends BroadcastReceiver {
          private ColorChangedReceiver() {
                    }

           ColorChangedReceiver(ProfileFragment x0, AnimationAnimationListenerC02541 x1) {
              this();
                    }

                    @Override 
          public void onReceive(Context context, Intent intent) {
              if (intent != null && VineService.ACTION_PROFILE_COLOR_CHANGED.equals(intent.getAction()) && ProfileFragment.access$400(ProfileFragment.this) != null && ProfileFragment.this.mFocused) {
                  BaseControllerActionBarActivity activity = (BaseControllerActionBarActivity) ProfileFragment.this.getActivity();
                  ProfileFragment.access$400(ProfileFragment.this).profileBackground = Util.getDefaultSharedPrefs(activity).getInt("profile_background", Settings.DEFAULT_PROFILE_COLOR);
                  activity.setActionBarColor(ProfileFragment.access$400(ProfileFragment.this).profileBackground);
                  activity.notifyColorChange(ProfileFragment.access$400(ProfileFragment.this).profileBackground);
                  if (ProfileFragment.this.mFeedAdapter != null) {
                      ProfileFragment.this.mFeedAdapter.notifyDataSetChanged();
                            }
                        }
                    }
                }

                
      class RunnableC02552 implements Runnable {
          RunnableC02552() {
                    }

                    @Override 
          public void run() {
                        View anchor;
              Activity context = ProfileFragment.this.getActivity();
              if (!OnboardHelper.getFavoriteUserTooltipShown(context) && (anchor = context.findViewById(C0257R.id.favorite_user)) != null && ProfileFragment.access$400(ProfileFragment.this) != null) {
                  String user = TextUtils.isEmpty(ProfileFragment.access$400(ProfileFragment.this).username) ? "" : ProfileFragment.access$400(ProfileFragment.this).username;
                  TooltipOverlayView overlay = new TooltipOverlayView(context, ProfileFragment.this.getString(C0257R.string.favorite_user_tooltip, user));
                  ProfileFragment.access$1800(ProfileFragment.this).addView(overlay);
                  overlay.showTooltip(anchor);
                  ProfileFragment.access$1902(ProfileFragment.this, overlay);
                  OnboardHelper.setFavoriteUserTooltipShown(context);
                        }
                    }
                }
            }
