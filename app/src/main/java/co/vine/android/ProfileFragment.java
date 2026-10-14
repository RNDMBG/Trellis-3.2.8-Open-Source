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
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 72 */    public class ProfileFragment extends BaseTimelineFragment implements View.OnClickListener, ViewOffsetResolver, SpanClickListener, View.OnLongClickListener {
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

/* 73 */        static /* synthetic */ long access$100(ProfileFragment x0) {
/* 74 */            return x0.mWaitingToStartUserId;
                }

/* 73 */        static /* synthetic */ ImageKey access$1000(ProfileFragment x0) {
/* 74 */            return x0.mUserProfileImageKeyForShortcut;
                }

/* 73 */        static /* synthetic */ long access$102(ProfileFragment x0, long x1) {
/* 74 */            x0.mWaitingToStartUserId = x1;
                    return x1;
                }

/* 73 */        static /* synthetic */ void access$1100(ProfileFragment x0, Bitmap x1) {
/* 74 */            x0.addProfileShortCut(x1);
                }

/* 73 */        static /* synthetic */ long access$1200(ProfileFragment x0) {
/* 74 */            return x0.mUserId;
                }

/* 73 */        static /* synthetic */ void access$1300(ProfileFragment x0) {
/* 74 */            x0.bindProfileDataAndCounts();
                }

/* 73 */        static /* synthetic */ LinearLayout access$1400(ProfileFragment x0) {
/* 74 */            return x0.mFollowHeader;
                }

/* 73 */        static /* synthetic */ void access$1500(ProfileFragment x0, long x1) {
/* 74 */            x0.setUserId(x1);
                }

/* 73 */        static /* synthetic */ boolean access$1602(ProfileFragment x0, boolean x1) {
/* 74 */            x0.mHideProfileReposts = x1;
                    return x1;
                }

/* 73 */        static /* synthetic */ boolean access$1700(ProfileFragment x0) {
/* 74 */            return x0.mFavoriteUser;
                }

/* 73 */        static /* synthetic */ boolean access$1702(ProfileFragment x0, boolean x1) {
/* 74 */            x0.mFavoriteUser = x1;
                    return x1;
                }

/* 73 */        static /* synthetic */ ViewGroup access$1800(ProfileFragment x0) {
/* 74 */            return x0.mRootView;
                }

/* 73 */        static /* synthetic */ TooltipOverlayView access$1902(ProfileFragment x0, TooltipOverlayView x1) {
/* 74 */            x0.mTooltipOverlay = x1;
                    return x1;
                }

/* 73 */        static /* synthetic */ SectionAdapter access$200(ProfileFragment x0) {
/* 74 */            return x0.mSectionAdapter;
                }

/* 73 */        static /* synthetic */ SectionAdapter access$202(ProfileFragment x0, SectionAdapter x1) {
/* 74 */            x0.mSectionAdapter = x1;
                    return x1;
                }

/* 73 */        static /* synthetic */ boolean access$300(ProfileFragment x0) {
/* 74 */            return x0.mIsMe;
                }

/* 73 */        static /* synthetic */ boolean access$302(ProfileFragment x0, boolean x1) {
/* 74 */            x0.mIsMe = x1;
                    return x1;
                }

/* 73 */        static /* synthetic */ VineUser access$400(ProfileFragment x0) {
/* 74 */            return x0.mUser;
                }

/* 73 */        static /* synthetic */ VineUser access$402(ProfileFragment x0, VineUser x1) {
/* 74 */            x0.mUser = x1;
                    return x1;
                }

/* 73 */        static /* synthetic */ void access$500(ProfileFragment x0, UrlCachePolicy x1) {
/* 74 */            x0.initProfile(x1);
                }

/* 73 */        static /* synthetic */ boolean access$600(ProfileFragment x0) {
/* 74 */            return x0.isLocked();
                }

/* 73 */        static /* synthetic */ boolean access$700(ProfileFragment x0) {
/* 74 */            return x0.mTakeFocus;
                }

/* 73 */        static /* synthetic */ void access$800(ProfileFragment x0, boolean x1) {
/* 74 */            x0.setup(x1);
                }

/* 73 */        static /* synthetic */ Runnable access$900(ProfileFragment x0) {
/* 74 */            return x0.mFavoriteUserHintRunnable;
                }

                /* JADX INFO: renamed from: co.vine.android.ProfileFragment$1 */
/* 123 */       class AnimationAnimationListenerC02541 implements Animation.AnimationListener {
/* 124 */           AnimationAnimationListenerC02541() {
                    }

                    @Override // android.view.animation.Animation.AnimationListener
/* 127 */           public void onAnimationStart(Animation animation) {
/* 128 */               if (ProfileFragment.this.getActivity() instanceof HomeTabActivity) {
/* 129 */                   ((HomeTabActivity) ProfileFragment.this.getActivity()).setScrollAwayNavEnabled(false);
                        }
                    }

                    @Override // android.view.animation.Animation.AnimationListener
/* 134 */           public void onAnimationEnd(Animation animation) {
/* 135 */               if (ProfileFragment.this.getActivity() instanceof HomeTabActivity) {
/* 136 */                   ((HomeTabActivity) ProfileFragment.this.getActivity()).setScrollAwayNavEnabled(true);
                        }
                    }

                    @Override // android.view.animation.Animation.AnimationListener
/* 142 */           public void onAnimationRepeat(Animation animation) {
                    }
                }

                @Override // co.vine.android.BaseTimelineFragment, co.vine.android.BaseArrayListFragment, co.vine.android.BaseAdapterFragment, co.vine.android.BaseControllerFragment, android.support.v4.app.Fragment
/* 148 */       public void onCreate(Bundle savedInstanceState) {
/* 149 */           super.onCreate(savedInstanceState);
/* 150 */           setHasOptionsMenu(true);
/* 152 */           Bundle b = getArguments();
/* 153 */           setUserId(b.getLong("user_id", -1L));
/* 154 */           setFlurryEventSource("Profile: Tab 1");
/* 155 */           this.mFlurryFollowEventSource = b.getString(ARG_FOLLOW_EVENT_SOURCE);
/* 156 */           this.mTakeFocus = b.getBoolean("take_focus", false);
/* 157 */           if (this.mTakeFocus) {
/* 158 */               setFocused(true);
                    }
/* 160 */           this.mAppSessionListener = new ProfileListener();
/* 162 */           if (savedInstanceState != null) {
/* 163 */               this.mCurrentMode = savedInstanceState.getInt(STATE_MODE);
/* 164 */               if (savedInstanceState.containsKey(STATE_HEADER_ADDED)) {
/* 165 */                   this.mHeaderAdded = savedInstanceState.getBoolean(STATE_HEADER_ADDED);
                        }
                    } else {
/* 168 */               this.mCurrentMode = 1;
/* 169 */               this.mHeaderAdded = false;
                    }
/* 172 */           showSadface(false);
/* 174 */           if (this.mUser != null) {
/* 175 */               this.mUser.repostsEnabled = 1;
                    }
/* 179 */           if (b.getStringArrayList("vanity_url") != null) {
/* 181 */               this.mAppController.fetchUserId(b.getStringArrayList("vanity_url"));
                    } else if (this.mUserId > 0) {
/* 183 */               this.mIsMe = this.mUserId == this.mAppController.getActiveId();
                    }
/* 185 */           if (this.mIsMe) {
/* 186 */               bindProfileDataAndCounts();
                    }
/* 189 */           this.mShowProfileActions = b.getBoolean(ARG_SHOW_PROFILE_ACTIONS, true);
                }

                @Override // co.vine.android.BaseTimelineFragment, co.vine.android.BaseArrayListFragment, co.vine.android.BaseAdapterFragment, co.vine.android.BaseControllerFragment, co.vine.android.BaseFragment, android.support.v4.app.Fragment
/* 193 */       public void onResume() {
/* 194 */           super.onResume();
                    if (this.mUserId == getAppController().getActiveId()) {
/* 196 */               registerMergePostReceiver();
                    }
/* 198 */           if (this.mAdapter.isEmpty()) {
/* 199 */               fetchInitialRequest(UrlCachePolicy.NETWORK_THEN_CACHE);
                    }
/* 201 */           if (this.mIsMe) {
/* 202 */               this.mHideProfileReposts = Util.getDefaultSharedPrefs(getActivity()).getBoolean(Settings.PREF_HIDE_PROFILE_REPOSTS, false);
/* 204 */               setup(isFocused());
                    }
/* 206 */           if (getActivity() instanceof HomeTabActivity) {
/* 207 */               HomeTabActivity activity = (HomeTabActivity) getActivity();
/* 208 */               if (activity.shouldChangeActionBarColor()) {
/* 209 */                   setActionBarColor();
                        }
                    }
/* 212 */           initProfile(UrlCachePolicy.NETWORK_THEN_CACHE);
/* 213 */           if (!this.mColorChangedReceiverIsRegistered) {
/* 214 */               this.mColorChangedReceiverIsRegistered = true;
/* 215 */               getActivity().registerReceiver(this.mColorChangedReceiver, Util.COLOR_CHANGED_INTENT_FILTER, CrossConstants.BROADCAST_PERMISSION, null);
                    }
                }

/* 219 */       private void initProfile(UrlCachePolicy cachePolicy) {
/* 220 */           if (this.mUser != null) {
/* 221 */               bindProfileDataAndCounts();
/* 222 */               hideProgress(3);
                    } else if (this.mIsMe) {
/* 225 */               addRequest(this.mAppController.fetchUsersMe(this.mUserId, cachePolicy));
                    } else {
/* 227 */               addRequest(this.mAppController.fetchUser(this.mUserId, cachePolicy));
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

/* 233 */       private void bindProfileDataAndCounts() {
/* 234 */           if (this.mUser != null) {
/* 237 */               if (this.mIsMe) {
/* 238 */                   SharedPreferences pref = Util.getDefaultSharedPrefs(getActivity());
/* 239 */                   this.mUser.username = pref.getString(Settings.PREF_NAME, null);
/* 240 */                   this.mUser.description = pref.getString(Settings.PREF_DESCRIPTION, null);
/* 241 */                   this.mUser.location = pref.getString(Settings.PREF_LOCATION, null);
/* 242 */                   this.mUser.email = pref.getString(Settings.PREF_EMAIL, null);
/* 243 */                   this.mUser.phoneNumber = pref.getString(Settings.PREF_PHONE, null);
/* 244 */                   this.mUser.avatarUrl = pref.getString(Settings.PREF_AVATAR_URL, null);
/* 245 */                   this.mUser.followingCount = pref.getInt(Settings.PREF_FOLLOWING_COUNT, 0);
/* 246 */                   this.mUser.followerCount = pref.getInt(Settings.PREF_FOLLOWER_COUNT, 0);
/* 247 */                   this.mUser.authoredPostCount = pref.getInt(Settings.PREF_AUTHORED_POST_COUNT, 0);
/* 248 */                   this.mUser.postCount = pref.getInt(Settings.PREF_POST_COUNT, 0);
/* 249 */                   this.mUser.likeCount = pref.getInt(Settings.PREF_LIKE_COUNT, 0);
/* 250 */                   this.mUser.loopCount = pref.getLong(Settings.PREF_LOOP_COUNT, 0L);
/* 251 */                   this.mUser.profileBackground = pref.getInt("profile_background", Settings.DEFAULT_PROFILE_COLOR);
                        }
/* 254 */               bindUserData(this.mUser);
                    }
                }

/* 257 */       void bindUserData(@NonNull VineUser user) {
/* 258 */           if (user.profileBackground <= 0) {
/* 259 */               user.profileBackground = Settings.DEFAULT_PROFILE_COLOR;
                    }
/* 261 */           if (user.profileBackground > 0) {
/* 262 */               if (getActivity() instanceof ProfileActivity) {
/* 263 */                   ((BaseControllerActionBarActivity) getActivity()).setActionBarColor(user.profileBackground);
/* 264 */                   ((BaseControllerActionBarActivity) getActivity()).notifyColorChange(user.profileBackground);
                        }
/* 266 */               if (this.mFeedAdapter != null) {
/* 267 */                   this.mFeedAdapter.setProfileColor(user.profileBackground);
                        }
/* 269 */               this.mProfileBackground = user.profileBackground;
                    }
/* 272 */           this.mProfileHeaderAdapter.bindUser(user);
/* 274 */           if (user.hasFollowApprovalPending() && !this.mHeaderAdded) {
/* 275 */               LayoutInflater inflater = LayoutInflater.from(getActivity());
/* 276 */               LinearLayout approvalPending = (LinearLayout) inflater.inflate(C0257R.layout.follow_request_approval_pending, (ViewGroup) null, false);
/* 278 */               if (this.mListView != null) {
/* 279 */                   this.mListView.addHeaderView(approvalPending);
/* 280 */                   RelativeLayout pendingFollowAccept = (RelativeLayout) approvalPending.findViewById(C0257R.id.follow_request_accept_container);
/* 282 */                   pendingFollowAccept.setOnClickListener(this);
/* 283 */                   RelativeLayout pendingFollowReject = (RelativeLayout) approvalPending.findViewById(C0257R.id.follow_request_reject_container);
/* 285 */                   pendingFollowReject.setOnClickListener(this);
/* 286 */                   this.mFollowHeader = approvalPending;
/* 287 */                   this.mHeaderAdded = true;
                        }
/* 289 */               this.mFollowHeader.setVisibility(0);
                    }
/* 291 */           setRevineOptionText(user, user.areRepostsEnabled());
/* 292 */           if (user.profileBackground > 0 && this.mFeedAdapter != null && this.mListView != null) {
/* 294 */               this.mFeedAdapter.setProfileColor(user.profileBackground);
/* 295 */               RefreshableListView rlv = (RefreshableListView) this.mListView;
/* 296 */               int color = (-16777216) | user.profileBackground;
/* 297 */               rlv.setPullToRefreshBackgroundColor(color);
/* 298 */               rlv.colorizePTR(-1);
                    }
/* 301 */           this.mFavoriteUser = user.notifyPosts;
                }

                @Override // android.support.v4.app.Fragment
/* 305 */       public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
/* 306 */           if (!this.mIsMe) {
/* 307 */               inflater.inflate(C0257R.menu.profile, menu);
                    }
                }

                @Override // android.support.v4.app.Fragment
/* 312 */       public boolean onOptionsItemSelected(MenuItem item) {
                    Bitmap bitmap;
/* 313 */           int id = item.getItemId();
/* 314 */           Activity activity = getActivity();
/* 315 */           if (id == C0257R.id.block_or_report) {
/* 316 */               if (this.mUser != null) {
/* 317 */                   if (!this.mUser.isBlocking()) {
/* 318 */                       Intent i = new Intent(getActivity(), (Class<?>) ReportingActivity.class);
/* 319 */                       i.putExtra(ReportingFragment.EXTRA_MENU_TYPE, "user");
/* 320 */                       i.putExtra("userId", this.mUser.userId);
/* 321 */                       i.putExtra(ReportingFragment.EXTRA_BLOCKING_USER, this.mUser.isBlocking());
/* 322 */                       i.putExtra("username", this.mUser.username);
/* 323 */                       activity.startActivity(i);
                            } else {
/* 325 */                       this.mAppController.unblockUser(this.mUser.userId, null);
                            }
                        }
/* 328 */               return true;
                    }
/* 329 */           if (id == C0257R.id.hide_revines) {
/* 330 */               if (this.mUser != null) {
/* 331 */                   if (this.mUser.areRepostsEnabled()) {
/* 332 */                       addRequest(this.mAppController.disableReposts(this.mUserId));
                            } else {
/* 334 */                       addRequest(this.mAppController.enableReposts(this.mUserId));
                            }
                        }
/* 337 */               return true;
                    }
/* 338 */           if (id == C0257R.id.share_profile) {
/* 339 */               if (this.mUser != null) {
/* 340 */                   FlurryUtils.trackShareProfile();
/* 341 */                   FragmentActivity context = getActivity();
/* 342 */                   Intent share = new Intent("android.intent.action.SEND");
/* 343 */                   share.setType("text/plain");
/* 344 */                   share.addFlags(524288);
/* 345 */                   share.putExtra("android.intent.extra.SUBJECT", "");
/* 346 */                   share.putExtra("android.intent.extra.TEXT", context.getString(C0257R.string.share_profile_txt, new Object[]{this.mUser.username, String.valueOf(this.mUser.userId)}).replace("vine://user", BuildUtil.getWebsiteUrl(context) + "/u"));
/* 349 */                   startActivity(Intent.createChooser(share, getString(C0257R.string.send)));
                        }
/* 351 */               return true;
                    }
/* 352 */           if (id == C0257R.id.create_shortcut) {
/* 353 */               if (this.mUser != null) {
/* 355 */                   this.mUserProfileImageKeyForShortcut = new ImageKey(this.mUser.avatarUrl, true);
/* 356 */                   if (TextUtils.isEmpty(this.mUser.avatarUrl) || Util.isDefaultAvatarUrl(this.mUser.avatarUrl)) {
/* 358 */                       bitmap = BitmapFactory.decodeResource(getResources(), C0257R.drawable.avatar_large);
                            } else {
/* 360 */                       bitmap = this.mAppController.getPhotoBitmap(this.mUserProfileImageKeyForShortcut);
                            }
/* 362 */                   if (bitmap != null) {
/* 363 */                       addProfileShortCut(bitmap);
                            }
                        }
                    } else if (id == C0257R.id.favorite_user && this.mUser != null) {
/* 368 */               this.mFavoriteUser = !this.mFavoriteUser;
/* 369 */               this.mAppController.favoriteUser(this.mUserId, this.mFavoriteUser, this.mUser.isFollowing());
/* 370 */               getActivity().supportInvalidateOptionsMenu();
/* 371 */               if (this.mFavoriteUser) {
/* 372 */                   ModalView modal = new ModalView(getActivity(), this.mRootView, C0257R.layout.favorite_user_modal);
/* 374 */                   if (!TextUtils.isEmpty(this.mUser.username)) {
/* 375 */                       TextView textView = (TextView) modal.findViewById(C0257R.id.description);
/* 376 */                       textView.setText(getString(C0257R.string.favorited_confirmation, this.mUser.username));
                            }
                        }
/* 379 */               if (this.mTooltipOverlay != null) {
                            this.mTooltipOverlay.dismiss();
                        }
                    }
/* 382 */           return super.onOptionsItemSelected(item);
                }

/* 385 */       private void addProfileShortCut(Bitmap bitmap) {
/* 386 */           this.mUserProfileImageKeyForShortcut = null;
/* 387 */           FragmentActivity activity = getActivity();
/* 388 */           if (activity != null) {
/* 389 */               FlurryUtils.trackCreateProfileShortCut();
/* 390 */               Intent shortcutIntent = new Intent(activity, (Class<?>) StartActivity.class);
/* 392 */               shortcutIntent.setAction("android.intent.action.VIEW");
/* 393 */               shortcutIntent.setData(Uri.parse("https://vine.co/u/" + this.mUser.userId));
/* 394 */               shortcutIntent.setFlags(268435456);
/* 395 */               Intent addIntent = new Intent();
/* 396 */               addIntent.putExtra("android.intent.extra.shortcut.INTENT", shortcutIntent);
/* 397 */               addIntent.putExtra("android.intent.extra.shortcut.NAME", this.mUser.username);
/* 398 */               Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, 128, 128, true);
/* 399 */               addIntent.putExtra("android.intent.extra.shortcut.ICON", scaledBitmap);
/* 400 */               addIntent.setAction("com.android.launcher.action.INSTALL_SHORTCUT");
/* 401 */               activity.getApplicationContext().sendBroadcast(addIntent);
/* 402 */               Util.showCenteredToast(activity, C0257R.string.shorcut_created);
                    }
                }

                @Override // android.support.v4.app.Fragment
/* 407 */       public void onPrepareOptionsMenu(Menu menu) {
/* 408 */           boolean isBlocking = this.mUser != null && this.mUser.isBlocking();
/* 409 */           MenuItem block = menu.findItem(C0257R.id.block_or_report);
/* 410 */           if (block != null) {
/* 411 */               if (isBlocking) {
/* 412 */                   block.setTitle(C0257R.string.unblock_person);
                        } else {
/* 414 */                   block.setTitle(C0257R.string.block_or_report);
                        }
                    }
/* 417 */           this.mHideRevines = menu.findItem(C0257R.id.hide_revines);
/* 419 */           boolean revinesEnabled = this.mUser == null || this.mUser.areRepostsEnabled();
/* 420 */           setRevineOptionText(this.mUser, revinesEnabled);
/* 422 */           MenuItem favoriteUser = menu.findItem(C0257R.id.favorite_user);
/* 423 */           if (favoriteUser != null) {
/* 424 */               favoriteUser.setVisible(isBlocking ? false : true);
/* 425 */               if (!isBlocking) {
/* 426 */                   favoriteUser.setIcon(this.mFavoriteUser ? C0257R.drawable.btn_favorite_profile_pressed : C0257R.drawable.btn_favorite_profile_default);
                        }
                    }
                }

                @Override // co.vine.android.BaseTimelineFragment
/* 433 */       protected String fetchPosts(int page, long anchor, boolean userInitiated, UrlCachePolicy cachePolicy) {
/* 434 */           throw new IllegalStateException("Don't call fetch posts for ProfileFragment, this fragment handles its own fetching.");
                }

                @Override // co.vine.android.BaseTimelineFragment, co.vine.android.BaseArrayListFragment, co.vine.android.widget.OnTabChangedListener
/* 439 */       public void onMoveTo(int oldPosition) {
/* 440 */           super.onMoveTo(oldPosition);
/* 441 */           setup(isFocused());
/* 442 */           bindProfileDataAndCounts();
                }

                @Override // co.vine.android.BaseArrayListFragment, android.support.v4.app.Fragment
/* 446 */       public void onViewCreated(View view, Bundle savedInstanceState) {
/* 447 */           super.onViewCreated(view, savedInstanceState);
/* 448 */           setRefreshableHeaderOffset(6);
/* 449 */           this.mListView.setOnScrollListener(this);
/* 450 */           showProgress(3);
                }

                @Override // co.vine.android.BaseArrayListFragment, android.support.v4.app.Fragment
/* 455 */       public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
/* 456 */           View view = createView(inflater, C0257R.layout.msg_list_fragment_profile, container);
/* 457 */           this.mRootView = (ViewGroup) view.findViewById(C0257R.id.layout_root);
/* 458 */           return view;
                }

                @Override // co.vine.android.BaseTimelineFragment, android.support.v4.app.Fragment
/* 462 */       public void onActivityCreated(Bundle savedInstanceState) {
/* 463 */           super.onActivityCreated(savedInstanceState);
/* 464 */           if (getArguments().getBoolean("take_focus", false)) {
/* 465 */               Activity activity = getActivity();
/* 466 */               if (activity instanceof HomeTabActivity) {
/* 467 */                   HomeTabActivity callback = (HomeTabActivity) activity;
/* 468 */                   callback.showPage(3, this);
/* 469 */                   if (BuildUtil.isLogsOn()) {
/* 470 */                       Log.d(TAG, "Me tab took focus.");
                            }
                        }
                    }
/* 474 */           this.mBold = new TypefacesSpan(null, Typefaces.get(getActivity()).getContentTypeface(1, 4));
/* 477 */           this.mVineGreen = getActivity().getResources().getColor(C0257R.color.vine_green);
/* 479 */           setup(this.mTakeFocus);
                    bindCachedProfileWhileLoading();
                }

                @Override // co.vine.android.BaseTimelineFragment, co.vine.android.BaseArrayListFragment, android.support.v4.app.Fragment
/* 483 */       public void onActivityResult(int requestCode, int resultCode, Intent data) {
/* 484 */           super.onActivityResult(requestCode, resultCode, data);
                    switch (requestCode) {
                        case 1:
/* 487 */                   if (resultCode == 1 && this.mUserId > 0) {
/* 489 */                       addRequest(this.mAppController.fetchFriends(1, 1));
/* 490 */                       addRequest(this.mAppController.fetchFriends(1, 0));
                                break;
                            }
                            break;
                    }
                }

                @Override // co.vine.android.BaseTimelineFragment, co.vine.android.BaseArrayListFragment, co.vine.android.BaseAdapterFragment, android.support.v4.app.Fragment
/* 499 */       public void onSaveInstanceState(Bundle outState) {
/* 500 */           super.onSaveInstanceState(outState);
/* 501 */           outState.putInt(STATE_MODE, this.mCurrentMode);
/* 502 */           outState.putBoolean(STATE_HEADER_ADDED, this.mHeaderAdded);
                }

                @Override // co.vine.android.BaseTimelineFragment, co.vine.android.BaseArrayListFragment
/* 508 */       protected void onScrollLastItem() {
/* 509 */           if (this.mRefreshable && this.mNextPage > 0 && this.mFeedAdapter.getCount() <= 400) {
/* 511 */               this.mFeedAdapter.onPause(isFocused());
/* 512 */               this.mLastFetchType = 1;
                        switch (this.mCurrentMode) {
                            case 1:
/* 515 */                       showProgress(1);
/* 516 */                       fetchContent(1, true, UrlCachePolicy.FORCE_REFRESH);
/* 517 */                       this.mPostPage++;
/* 518 */                       FlurryUtils.trackTimeLinePageScroll(getClass().getName() + "_Post", this.mPostPage);
                                break;
                            case 2:
/* 522 */                       showProgress(1);
/* 523 */                       fetchContent(1, true, UrlCachePolicy.FORCE_REFRESH);
/* 524 */                       this.mLikePage++;
/* 525 */                       FlurryUtils.trackTimeLinePageScroll(getClass().getName() + "_Like", this.mLikePage);
                                break;
                        }
                    }
                }

                @Override // co.vine.android.BaseTimelineFragment, co.vine.android.BaseArrayListFragment
/* 534 */       protected void refresh() {
                    // Always reset the pull-to-refresh state before starting a new request.
                    // A previous request can have been completed by the service without a
                    // matching fragment callback (for example after deleting a post).
                    // Without this reset, the profile and subsequent sections remain stuck
                    // in the refreshing state.
                    clearPendingRequests(2);
/* 535 */           hideProgress(2);
/* 535 */           fetchContent(2, true, UrlCachePolicy.FORCE_REFRESH);
/* 536 */           if (this.mIsMe) {
/* 537 */               addRequest(this.mAppController.fetchUsersMe(this.mAppController.getActiveId(), UrlCachePolicy.FORCE_REFRESH));
                    } else {
/* 539 */               addRequest(this.mAppController.fetchUser(this.mUserId, UrlCachePolicy.FORCE_REFRESH));
                    }
                }

/* 544 */       private void setRevineOptionText(VineUser user, boolean repostsEnabled) {
/* 545 */           MenuItem hideRevines = this.mHideRevines;
/* 547 */           if (hideRevines != null) {
/* 548 */               if (user != null && !user.isFollowing()) {
/* 549 */                   hideRevines.setVisible(false);
                        } else if (repostsEnabled) {
/* 551 */                   hideRevines.setVisible(true);
/* 552 */                   hideRevines.setTitle(C0257R.string.hide_revines);
                        } else {
/* 554 */                   hideRevines.setVisible(true);
/* 555 */                   hideRevines.setTitle(C0257R.string.show_revines);
                        }
                    }
                }

/* 560 */       public void changeMode(int mode) {
                    switch (mode) {
                        case 1:
/* 574 */                   if (this.mCurrentMode != 1) {
/* 577 */                       this.mCurrentMode = 1;
/* 578 */                       this.mSectionAdapter = new SectionAdapter(new BaseAdapter[]{this.mProfileHeaderAdapter});
/* 579 */                       this.mListView.setAdapter((ListAdapter) this.mSectionAdapter);
/* 580 */                       setFlurryEventSource("Profile: Tab 1");
/* 581 */                       fetchContent(3, true, UrlCachePolicy.FORCE_REFRESH);
                            }
                            break;
                        case 2:
/* 563 */                   if (this.mCurrentMode != 2) {
/* 566 */                       this.mCurrentMode = 2;
/* 567 */                       setFlurryEventSource("Profile: Tab 2");
/* 568 */                       this.mSectionAdapter = new SectionAdapter(new BaseAdapter[]{this.mProfileHeaderAdapter});
/* 569 */                       this.mListView.setAdapter((ListAdapter) this.mSectionAdapter);
/* 570 */                       fetchContent(3, true, UrlCachePolicy.FORCE_REFRESH);
                            }
                            break;
                    }
                }

                @Override // co.vine.android.BaseTimelineFragment, co.vine.android.BaseArrayListFragment, co.vine.android.BaseAdapterFragment, co.vine.android.BaseControllerFragment, android.support.v4.app.Fragment
/* 588 */       public void onPause() {
/* 589 */           super.onPause();
/* 590 */           this.mFeedAdapter.onPause(isFocused());
                }

                @Override // co.vine.android.BaseTimelineFragment, android.support.v4.app.Fragment
/* 594 */       public void onDestroy() {
/* 595 */           super.onDestroy();
/* 596 */           if (this.mColorChangedReceiverIsRegistered) {
/* 597 */               getActivity().unregisterReceiver(this.mColorChangedReceiver);
/* 598 */               this.mColorChangedReceiverIsRegistered = false;
                    }
                }

/* 602 */       private void setup(boolean takeFocus) {
/* 603 */           if (this.mSectionAdapter == null) {
/* 604 */               if (isLocked()) {
/* 605 */                   this.mProfileHeaderAdapter = new ProfileHeaderAdapter(getActivity(), this.mAppController, this, true, this.mUser.isBlocked(), this.mFlurryFollowEventSource, this.mHideProfileReposts, this.mShowProfileActions);
/* 608 */                   this.mPrivateAdapter = new PrivateAccountAdapter(this.mUser.isBlocked(), this.mUser.isPrivateLocked());
/* 610 */                   this.mSectionAdapter = new SectionAdapter(new BaseAdapter[]{this.mProfileHeaderAdapter, this.mPrivateAdapter});
/* 612 */                   this.mListView.setAdapter((ListAdapter) this.mSectionAdapter);
/* 613 */                   this.mSectionAdapter.notifyDataSetChanged();
/* 630 */                   return;
                        }
/* 615 */               this.mProfileHeaderAdapter = new ProfileHeaderAdapter(getActivity(), this.mAppController, this, false, false, this.mFlurryFollowEventSource, this.mHideProfileReposts, this.mShowProfileActions);
/* 618 */               this.mSectionAdapter = new SectionAdapter(new BaseAdapter[]{this.mProfileHeaderAdapter, this.mFeedAdapter});
/* 621 */               if (takeFocus) {
/* 622 */                   this.mFeedAdapter.onResume(true);
                        }
/* 624 */               this.mListView.setAdapter((ListAdapter) this.mSectionAdapter);
/* 625 */               this.mFeedAdapter.setOffsetResolver(this);
/* 630 */               return;
                    }
/* 627 */           if (this.mFeedAdapter != null && !isLocked() && takeFocus) {
/* 628 */               this.mFeedAdapter.onResume(true);
                    }
                }

                @Override // co.vine.android.BaseTimelineFragment
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

                @Override // co.vine.android.BaseTimelineFragment
/* 633 */       protected void fetchContent(int fetchType, boolean userInitiated, UrlCachePolicy cachePolicy) {
/* 634 */           this.mLastFetchType = fetchType;
/* 635 */           if (!hasPendingRequest(fetchType)) {
                        switch (fetchType) {
                            case 2:
/* 641 */                       showProgress(fetchType);
                            case 3:
                            case 4:
/* 645 */                       this.mNextPage = 1;
/* 646 */                       this.mAnchor = 0L;
                                break;
                        }
/* 651 */               int postGroup = this.mHideProfileReposts ? 10 : 2;
/* 654 */               if (this.mCurrentMode == 2) {
/* 655 */                   postGroup = 3;
                        }
/* 657 */               addRequest(this.mAppController.fetchPosts(this.mAppController.getActiveSession(), 10, this.mUserId, postGroup, this.mNextPage, this.mAnchor, userInitiated, String.valueOf(this.mUserId), null, null, cachePolicy), fetchType);
                    }
                }

                @Override // co.vine.android.BaseTimelineFragment, android.view.View.OnClickListener
/* 663 */       public void onClick(View view) {
                    View v;
/* 664 */           int id = view.getId();
/* 665 */           if (id == C0257R.id.user_image || id == C0257R.id.profile_follow || id == C0257R.id.settings) {
/* 668 */               this.mProfileHeaderAdapter.onProfileHeaderClick(view, this, this.mUser);
/* 734 */               return;
                    }
/* 669 */           if (id == C0257R.id.message_button) {
                        if (this.mWaitingToStartUserId == -1) {
/* 671 */                   this.mWaitingToStartUserId = this.mUserId;
/* 674 */                   this.mAppController.fetchConversationRowIdFromUserRemoteId(this.mUserId, 1);
/* 734 */                   return;
                        }
/* 734 */               return;
                    }
/* 677 */           if (id == C0257R.id.postsParent || id == C0257R.id.likesParent) {
/* 678 */               this.mProfileHeaderAdapter.onProfileHeaderClick(view, this, this.mUser);
/* 734 */               return;
                    }
/* 679 */           if (id == C0257R.id.follow_request_accept_container) {
/* 680 */               this.mAppController.acceptFollowRequest(this.mAppController.getActiveSession(), this.mUserId);
/* 734 */               return;
                    }
/* 681 */           if (id == C0257R.id.follow_request_reject_container) {
/* 682 */               this.mAppController.acceptRejectRequest(this.mAppController.getActiveSession(), this.mUserId);
/* 734 */               return;
                    }
/* 683 */           if (id == C0257R.id.posts_dropdown) {
/* 684 */               onDropDownClicked();
/* 734 */               return;
                    }
/* 685 */           if (id == C0257R.id.postsDropDownHideRevine) {
/* 686 */               Resources res = getResources();
/* 687 */               if (this.mUser != null && res != null && (v = getView()) != null) {
/* 692 */                   View postsButton = v.findViewById(C0257R.id.postsParent);
/* 693 */                   View dropDown = v.findViewById(C0257R.id.postsLikeDropDown);
/* 694 */                   if (dropDown != null) {
/* 695 */                       if (dropDown.getVisibility() == 0) {
/* 696 */                           dismissDropdown();
/* 734 */                           return;
                                }
/* 698 */                       if (postsButton != null) {
/* 699 */                           ProfileHeaderAdapter.DropdownHolder tag = (ProfileHeaderAdapter.DropdownHolder) dropDown.getTag();
/* 700 */                           String hideRevines = res.getString(this.mHideProfileReposts ? C0257R.string.show_revines : C0257R.string.hide_revines);
/* 702 */                           tag.revineParent.setVisibility(0);
/* 703 */                           Util.setTextWithSpan(this.mBold, hideRevines, tag.revineLabel);
/* 704 */                           SpannableStringBuilder contentSb = new SpannableStringBuilder(hideRevines);
/* 705 */                           Util.safeSetSpan(contentSb, this.mBold, 0, contentSb.length(), 33);
/* 706 */                           tag.revineLabel.setText(contentSb);
/* 707 */                           tag.revineIcon.setImageResource(this.mHideProfileReposts ? C0257R.drawable.ic_show_revines : C0257R.drawable.ic_hide_revines);
/* 709 */                           Animation anim = new HeightAnimation(getActivity(), dropDown, true, 300L);
/* 710 */                           anim.setAnimationListener(this.mDrawerListener);
/* 711 */                           dropDown.startAnimation(anim);
/* 712 */                           View arrowImage = v.findViewById(C0257R.id.profile_arrow_image);
/* 713 */                           if (arrowImage instanceof ImageView) {
/* 714 */                               if (this.mProfileBackground == Settings.DEFAULT_PROFILE_COLOR) {
/* 715 */                                   ((ImageView) arrowImage).setColorFilter(new PorterDuffColorFilter((-16777216) | this.mVineGreen, PorterDuff.Mode.SRC_IN));
/* 734 */                                   return;
                                        } else {
/* 717 */                                   ((ImageView) arrowImage).setColorFilter(new PorterDuffColorFilter((-16777216) | this.mProfileBackground, PorterDuff.Mode.SRC_IN));
/* 734 */                                   return;
                                        }
                                    }
/* 734 */                           return;
                                }
/* 734 */                       return;
                            }
/* 734 */                   return;
                        }
/* 734 */               return;
                    }
/* 724 */           if (id == C0257R.id.followers || id == C0257R.id.following) {
/* 725 */               Intent i = new Intent(getActivity(), (Class<?>) UsersActivity.class);
/* 726 */               i.putExtra("p_id", this.mUserId);
/* 728 */               int type = view.getId() == C0257R.id.followers ? 2 : 1;
/* 729 */               i.putExtra("u_type", type);
/* 730 */               startActivity(i);
/* 734 */               return;
                    }
/* 732 */           super.onClick(view);
                }

                @Override // android.view.View.OnLongClickListener
/* 737 */       public boolean onLongClick(View view) {
/* 738 */           int id = view.getId();
/* 739 */           if (id != C0257R.id.user_image && id != C0257R.id.profile_follow && id != C0257R.id.settings) {
                        return false;
                    }
/* 742 */           this.mProfileHeaderAdapter.onProfileHeaderLongClick(view);
/* 743 */           return true;
                }

/* 749 */       public void onDropDownClicked() {
/* 750 */           this.mHideProfileReposts = !this.mHideProfileReposts;
/* 751 */           FlurryUtils.trackFilterProfileReposts(this.mHideProfileReposts, this.mUser.following > 0, this.mIsMe);
/* 753 */           if (this.mIsMe) {
/* 754 */               SharedPreferences.Editor e = Util.getDefaultSharedPrefs(getActivity()).edit();
/* 755 */               e.putBoolean(Settings.PREF_HIDE_PROFILE_REPOSTS, this.mHideProfileReposts);
/* 756 */               e.apply();
                    } else {
/* 758 */               this.mAppController.setHideProfileReposts(this.mUserId, this.mHideProfileReposts);
                    }
                    clearPendingRequests(3);
                    if (this.mHideProfileReposts && this.mFeedAdapter instanceof FeedAdapter) {
                        ((FeedAdapter) this.mFeedAdapter).removeReposts();
                    }
/* 760 */           this.mFeedAdapter.onPause(isFocused());
/* 761 */           fetchContent(3, true, UrlCachePolicy.CACHE_THEN_NETWORK);
/* 763 */           dismissDropdown();
                }

/* 766 */       private void dismissDropdown() {
/* 767 */           View view = getView();
/* 768 */           if (view != null) {
/* 769 */               View dropDown = view.findViewById(C0257R.id.postsLikeDropDown);
/* 770 */               if (dropDown != null) {
/* 771 */                   Animation anim = new HeightAnimation(getActivity(), dropDown, false, 300L);
/* 772 */                   anim.setAnimationListener(this.mDrawerListener);
/* 773 */                   dropDown.startAnimation(anim);
                        }
/* 775 */               View arrowImage = view.findViewById(C0257R.id.profile_arrow_image);
/* 776 */               if (arrowImage instanceof ImageView) {
/* 777 */                   ((ImageView) arrowImage).setColorFilter(new PorterDuffColorFilter(1275068416, PorterDuff.Mode.SRC_IN));
                        }
                    }
                }

                @Override // co.vine.android.ViewOffsetResolver
/* 783 */       public int getOffset(BaseAdapter me) {
/* 784 */           return this.mSectionAdapter.getPositionOffset(this.mSectionAdapter.getMyAdapterIndex(me));
                }

                @Override // co.vine.android.BaseFragment
/* 788 */       public void setActionBarColor() {
/* 789 */           Activity activity = getActivity();
/* 790 */           if (activity instanceof BaseActionBarActivity) {
/* 792 */               if (this.mProfileBackground == 0) {
/* 793 */                   this.mProfileBackground = Util.getDefaultSharedPrefs(getActivity()).getInt("profile_background", Settings.DEFAULT_PROFILE_COLOR);
                        }
/* 796 */               ((BaseActionBarActivity) activity).setActionBarColor(this.mProfileBackground);
                    }
                }

/* 800 */       private void setUserId(long userId) {
/* 801 */           this.mUserId = userId;
/* 802 */           this.mVineSpanClicker.setUserId(this.mUserId);
                }

/* 806 */       private boolean isLocked() {
                    return (this.mUser == null || this.mIsMe || (!this.mUser.isPrivateLocked() && !this.mUser.isBlocked())) ? false : true;
                }

/* 810 */       public long getUserId() {
/* 811 */           return this.mUserId;
                }

/* 812 */       class ProfileListener extends BaseTimelineFragment.TimeLineSessionListener {
/* 813 */           ProfileListener() {
                        super();
                    }

                    @Override // co.vine.android.BaseTimelineFragment.TimeLineSessionListener, co.vine.android.client.AppSessionListener
/* 817 */           public void onAbortAllRequestsComplete() {
/* 818 */               ProfileFragment.this.refresh();
                    }

                    @Override // co.vine.android.client.AppSessionListener
/* 823 */           public void onGetConversationRowIdComplete(long recipientId, boolean isRecipientExternal, long conversationObjectId, String username, boolean amFollowing) {
                        if (recipientId == ProfileFragment.access$100(ProfileFragment.this)) {
/* 825 */                   ProfileFragment.access$102(ProfileFragment.this, -1L);
/* 826 */                   ProfileFragment.this.mAppController.clearUnreadMessageCount(conversationObjectId);
/* 827 */                   ProfileFragment.this.startActivity(ConversationActivity.getIntent(ProfileFragment.this.getActivity(), conversationObjectId, username, recipientId, isRecipientExternal, amFollowing, true));
                        }
                    }

                    @Override // co.vine.android.BaseTimelineFragment.TimeLineSessionListener, co.vine.android.client.AppSessionListener
/* 837 */           public void onGetTimeLineComplete(String reqId, int statusCode, String reasonPhrase, int type, int count, boolean memory, ArrayList<VinePost> posts, String tag, int pageType, int next, int previous, long anchor, boolean userInitiated, int size, String title, UrlCachePolicy cachePolicy, Bundle bundle) {
/* 838 */               PendingRequest req = ProfileFragment.this.removeRequest(reqId);
/* 839 */               if (req != null) {
/* 840 */                   if (statusCode == 200) {
                        if (ProfileFragment.this.mHideProfileReposts && posts != null) {
                            ArrayList<VinePost> filteredPosts = new ArrayList<>();
                            for (VinePost post : posts) {
                                if (post != null && post.repost == null && post.myRepostId <= 0) {
                                    filteredPosts.add(post);
                                }
                            }
                            posts = filteredPosts;
                        }
/* 841 */                       int tab = 0;
/* 842 */                       if (type == 3) {
/* 843 */                           tab = 1;
                                }
/* 845 */                       ProfileFragment.this.mProfileHeaderAdapter.updatePostCount(size, tab);
/* 846 */                       ProfileFragment.this.mProfileHeaderAdapter.notifyDataSetChanged();
                            }
/* 849 */                   if (memory && statusCode == 200) {
/* 850 */                       ProfileFragment.this.mNextPage = next;
/* 851 */                       ProfileFragment.this.mAnchor = anchor;
/* 852 */                       if (req.fetchType == 1) {
/* 853 */                           ((FeedAdapter) ProfileFragment.this.mFeedAdapter).addOlderPosts(posts);
                                } else {
/* 856 */                           ((FeedAdapter) ProfileFragment.this.mFeedAdapter).replacePosts(posts);
                                }
/* 858 */                       if (ProfileFragment.access$200(ProfileFragment.this).getNumberOfAdapters() == 1) {
/* 859 */                           ProfileFragment.access$202(ProfileFragment.this, new SectionAdapter(new BaseAdapter[]{ProfileFragment.this.mProfileHeaderAdapter, ProfileFragment.this.mFeedAdapter}));
/* 860 */                           ProfileFragment.this.mListView.setAdapter((ListAdapter) ProfileFragment.access$200(ProfileFragment.this));
                                }
/* 863 */                       ProfileFragment.this.mAppController.saveLoadedPosts(posts, type, tag, pageType, next, previous, anchor, false);
                            }
/* 866 */                   if (!cachePolicy.mNetworkDataAllowed) {
                                // Hide this phase's progress before chaining the network
                                // phase so a skipped/lost chained request cannot leave
                                // the indicator spinning.
                                ProfileFragment.this.hideProgress(req.fetchType);
                                ProfileFragment.this.fetchInitialRequest(UrlCachePolicy.NETWORK_THEN_CACHE);
                            } else {
                                ProfileFragment.this.hideProgress(req.fetchType);
                            }
                        }
                    }

                    @Override // co.vine.android.BaseTimelineFragment.TimeLineSessionListener, co.vine.android.client.AppSessionListener
/* 874 */           public void onTrimMemory(int level) {
/* 875 */               super.onTrimMemory(level);
/* 876 */               GenericVideoAdapter adapter = ProfileFragment.this.mFeedAdapter;
/* 877 */               if (adapter != null) {
/* 878 */                   adapter.onTrimMemory(level);
                        }
                    }

                    @Override // co.vine.android.client.AppSessionListener
/* 884 */           public void onGetUsersMeComplete(String reqId, int statusCode, String reasonPhrase, long sessionOwnerId, VineUser user, UrlCachePolicy policy) {
/* 885 */               PendingRequest req = ProfileFragment.this.removeRequest(reqId);
/* 887 */               if (req != null) {
/* 888 */                   if (statusCode == 200 && ProfileFragment.access$300(ProfileFragment.this)) {
/* 889 */                       ProfileFragment.access$402(ProfileFragment.this, user);
/* 890 */                       if (user != null) {
/* 891 */                           ProfileFragment.this.bindUserData(user);
/* 893 */                           ProfileFragment.this.mProfileHeaderAdapter.notifyDataSetChanged();
                                }
                            }
/* 896 */                   ProfileFragment.this.hideProgress(req.fetchType);
/* 897 */                   Intent intent = new Intent(VineService.ACTION_PROFILE_COLOR_CHANGED);
/* 898 */                   ProfileFragment.this.getActivity().sendBroadcast(intent, CrossConstants.BROADCAST_PERMISSION);
/* 899 */                   if (!policy.mNetworkDataAllowed) {
/* 900 */                       ProfileFragment.access$500(ProfileFragment.this, UrlCachePolicy.NETWORK_THEN_CACHE);
                            }
                        }
                    }

                    @Override // co.vine.android.BaseTimelineFragment.TimeLineSessionListener, co.vine.android.client.AppSessionListener
/* 906 */           public void onVideoPathObtained(HashMap<VideoKey, UrlVideo> videos) {
/* 907 */               ProfileFragment.this.mFeedAdapter.onVideoPathObtained(videos);
                    }

                    @Override // co.vine.android.BaseTimelineFragment.TimeLineSessionListener, co.vine.android.client.AppSessionListener
/* 912 */           public void onVideoPathError(VideoKey key, HttpResult result) {
                    }

                    @Override // co.vine.android.BaseTimelineFragment.TimeLineSessionListener, co.vine.android.client.AppSessionListener
/* 917 */           public void onPhotoImageError(ImageKey key, HttpResult result) {
                    }

                    @Override // co.vine.android.client.AppSessionListener
/* 922 */           public void onGetUserComplete(String reqId, int statusCode, String reasonPhrase, VineUser user, UrlCachePolicy policy) {
/* 923 */               PendingRequest req = ProfileFragment.this.removeRequest(reqId);
/* 925 */               if (req != null) {
/* 926 */                   if (statusCode == 200) {
/* 927 */                       ProfileFragment.access$402(ProfileFragment.this, user);
/* 928 */                       if (ProfileFragment.access$600(ProfileFragment.this)) {
/* 929 */                           ProfileFragment.access$202(ProfileFragment.this, null);
/* 930 */                           ProfileFragment.access$800(ProfileFragment.this, ProfileFragment.this.isFocused() || ProfileFragment.access$700(ProfileFragment.this));
                                }
/* 932 */                       if (ProfileFragment.this.mProfileHeaderAdapter != null && user != null) {
/* 933 */                           ProfileFragment.this.bindUserData(user);
/* 935 */                           ProfileFragment.this.mProfileHeaderAdapter.notifyDataSetChanged();
                                }
/* 937 */                       if (user != null && user.isFollowing() && !user.notifyPosts) {
/* 938 */                           ProfileFragment.this.mHandler.postDelayed(ProfileFragment.access$900(ProfileFragment.this), 750L);
                                }
                            }
/* 941 */                   if (!policy.mNetworkDataAllowed) {
/* 942 */                       ProfileFragment.access$500(ProfileFragment.this, UrlCachePolicy.NETWORK_THEN_CACHE);
                            }
/* 944 */                   ProfileFragment.this.hideProgress(req.fetchType);
                        }
                    }

                    @Override // co.vine.android.BaseTimelineFragment.TimeLineSessionListener, co.vine.android.client.AppSessionListener
/* 949 */           public void onPhotoImageLoaded(HashMap<ImageKey, UrlImage> images) {
                        UrlImage urlImage;
/* 950 */               if (ProfileFragment.this.mProfileHeaderAdapter != null) {
/* 951 */                   ProfileFragment.this.mProfileHeaderAdapter.onImageLoaded(images);
/* 952 */                   ProfileFragment.this.mFeedAdapter.setImages(images);
                        }
/* 954 */               if (ProfileFragment.access$1000(ProfileFragment.this) != null && (urlImage = images.get(ProfileFragment.access$1000(ProfileFragment.this))) != null && urlImage.isValid()) {
/* 957 */                   ProfileFragment.access$1100(ProfileFragment.this, urlImage.bitmap);
                        }
                    }

                    @Override // co.vine.android.BaseTimelineFragment.TimeLineSessionListener, co.vine.android.client.AppSessionListener
/* 963 */           public void onLikePost(String reqId, int statusCode, String reasonPhrase, long postId) {
/* 964 */               ProfileFragment.this.removeRequest(reqId);
/* 965 */               if (statusCode >= 200 && statusCode < 300) {
                      ProfileFragment.this.mLikeCache.like(postId);
                      ProfileFragment.this.mFeedAdapter.showLikedByMe(postId);
                  } else {
/* 967 */                   ProfileFragment.this.mLikeCache.removeEntry(postId);
/* 968 */                   ProfileFragment.this.mFeedAdapter.showUnlikedByMe(postId);
                        }
                    }

                    @Override // co.vine.android.BaseTimelineFragment.TimeLineSessionListener, co.vine.android.client.AppSessionListener
/* 974 */           public void onUnlikePost(String reqId, int statusCode, String reasonPhrase, long postId) {
/* 975 */               ProfileFragment.this.removeRequest(reqId);
/* 976 */               if (statusCode >= 200 && statusCode < 300) {
                      ProfileFragment.this.mLikeCache.unlike(postId);
                      ProfileFragment.this.mFeedAdapter.showUnlikedByMe(postId);
                  } else {
/* 977 */                   if (statusCode == 404) {
/* 980 */                       ProfileFragment.this.mLikeCache.removeEntry(postId);
/* 981 */                       ProfileFragment.this.mFeedAdapter.showUnlikedByMe(postId);
                            } else {
/* 983 */                       ProfileFragment.this.mLikeCache.like(postId);
/* 984 */                       ProfileFragment.this.mFeedAdapter.showLikedByMe(postId);
                            }
                        }
                    }

                    @Override // co.vine.android.BaseTimelineFragment.TimeLineSessionListener, co.vine.android.client.AppSessionListener
/* 990 */           public void onUnrevine(String reqId, int statusCode, String reasonPhrase, long postId) {
/* 991 */               if (statusCode == 200) {
/* 992 */                   ((FeedAdapter) ProfileFragment.this.mFeedAdapter).removePost(postId);
                        }
/* 994 */               super.onUnrevine(reqId, statusCode, reasonPhrase, postId);
                    }

                    @Override // co.vine.android.client.AppSessionListener
/* 999 */           public void onBlockUserComplete(String reqId, int statusCode, String reasonPhrase, boolean userBlocked, String blockedUsername) {
/* 1000 */              if (statusCode == 200) {
/* 1001 */                  if (ProfileFragment.access$400(ProfileFragment.this) != null) {
/* 1002 */                      ProfileFragment.access$400(ProfileFragment.this).blocking = userBlocked ? 1 : 0;
                            }
/* 1004 */                  ProfileFragment.this.getActivity().supportInvalidateOptionsMenu();
/* 1005 */                  String username = isUsernameValid() ? ProfileFragment.access$400(ProfileFragment.this).username : ProfileFragment.this.getString(C0257R.string.username_placeholder);
/* 1007 */                  if (!TextUtils.isEmpty(blockedUsername)) {
/* 1008 */                      username = blockedUsername;
                            }
/* 1011 */                  if (userBlocked) {
/* 1012 */                      Util.showDefaultToast(ProfileFragment.this.getActivity(), ProfileFragment.this.getString(C0257R.string.user_blocked, username));
/* 1014 */                      VineDatabaseHelper dbHelper = VineDatabaseHelper.getDatabaseHelper(ProfileFragment.this.getActivity());
/* 1015 */                      dbHelper.removeUserWithType(ProfileFragment.access$1200(ProfileFragment.this), 2, true, Vine.UserGroupsView.CONTENT_URI_FOLLOWERS);
/* 1023 */                      return;
                            }
/* 1018 */                  Util.showDefaultToast(ProfileFragment.this.getActivity(), ProfileFragment.this.getString(C0257R.string.user_blocked_error));
/* 1023 */                  return;
                        }
/* 1021 */              Util.showDefaultToast(ProfileFragment.this.getActivity(), ProfileFragment.this.getString(C0257R.string.user_blocked_error));
                    }

                    @Override // co.vine.android.client.AppSessionListener
/* 1027 */          public void onUnblockUserComplete(String reqId, int statusCode, String reasonPhrase, boolean userUnblocked, String blockedUsername) {
/* 1028 */              if (statusCode == 200) {
/* 1029 */                  if (ProfileFragment.access$400(ProfileFragment.this) != null) {
/* 1030 */                      ProfileFragment.access$400(ProfileFragment.this).blocking = !userUnblocked ? 1 : 0;
                            }
/* 1032 */                  ProfileFragment.this.getActivity().supportInvalidateOptionsMenu();
/* 1033 */                  String username = isUsernameValid() ? ProfileFragment.access$400(ProfileFragment.this).username : ProfileFragment.this.getString(C0257R.string.username_placeholder);
/* 1035 */                  if (!TextUtils.isEmpty(blockedUsername)) {
/* 1036 */                      username = blockedUsername;
                            }
/* 1038 */                  if (userUnblocked) {
/* 1039 */                      Util.showDefaultToast(ProfileFragment.this.getActivity(), ProfileFragment.this.getString(C0257R.string.user_unblocked, username));
/* 1041 */                      ProfileFragment.this.addRequest(ProfileFragment.this.mAppController.fetchUser(ProfileFragment.access$1200(ProfileFragment.this), UrlCachePolicy.FORCE_REFRESH));
/* 1048 */                      return;
                            } else {
/* 1043 */                      Util.showDefaultToast(ProfileFragment.this.getActivity(), ProfileFragment.this.getString(C0257R.string.user_blocked_error));
/* 1048 */                      return;
                            }
                        }
/* 1046 */              Util.showDefaultToast(ProfileFragment.this.getActivity(), ProfileFragment.this.getString(C0257R.string.user_unblocked_error));
                    }

                    @Override // co.vine.android.client.AppSessionListener
/* 1052 */          public void onEnableUserRepostsComplete(String reqId, int statusCode, String reasonPhrase, boolean success) {
/* 1053 */              if (ProfileFragment.this.removeRequest(reqId) != null) {
/* 1054 */                  if (statusCode == 200) {
/* 1055 */                      if (ProfileFragment.access$400(ProfileFragment.this) != null) {
/* 1056 */                          ProfileFragment.access$400(ProfileFragment.this).repostsEnabled = success ? 1 : 0;
/* 1057 */                          ProfileFragment.this.getActivity().supportInvalidateOptionsMenu();
/* 1059 */                          if (success) {
/* 1060 */                              String username = isUsernameValid() ? ProfileFragment.access$400(ProfileFragment.this).username : ProfileFragment.this.getString(C0257R.string.username_placeholder_alt);
/* 1062 */                              Util.showDefaultToast(ProfileFragment.this.getActivity(), ProfileFragment.this.getString(C0257R.string.reposts_enabled, username));
/* 1071 */                              return;
                                    } else {
/* 1064 */                              Util.showDefaultToast(ProfileFragment.this.getActivity(), ProfileFragment.this.getString(C0257R.string.reposts_enabled_fail));
/* 1071 */                              return;
                                    }
                                }
/* 1071 */                      return;
                            }
/* 1068 */                  Util.showDefaultToast(ProfileFragment.this.getActivity(), ProfileFragment.this.getString(C0257R.string.reposts_enabled_fail));
                        }
                    }

                    @Override // co.vine.android.client.AppSessionListener
/* 1075 */          public void onDisableUserRepostsComplete(String reqId, int statusCode, String reasonPhrase, boolean success) {
/* 1076 */              if (ProfileFragment.this.removeRequest(reqId) != null) {
/* 1077 */                  if (statusCode == 200) {
/* 1078 */                      if (ProfileFragment.access$400(ProfileFragment.this) != null) {
/* 1079 */                          ProfileFragment.access$400(ProfileFragment.this).repostsEnabled = success ? 0 : 1;
/* 1080 */                          ProfileFragment.this.getActivity().supportInvalidateOptionsMenu();
/* 1082 */                          if (success) {
/* 1083 */                              String username = isUsernameValid() ? ProfileFragment.access$400(ProfileFragment.this).username : ProfileFragment.this.getString(C0257R.string.username_placeholder_alt);
/* 1085 */                              Util.showDefaultToast(ProfileFragment.this.getActivity(), ProfileFragment.this.getString(C0257R.string.reposts_disabled, username));
/* 1094 */                              return;
                                    } else {
/* 1087 */                              Util.showDefaultToast(ProfileFragment.this.getActivity(), ProfileFragment.this.getString(C0257R.string.reposts_disabled_fail));
/* 1094 */                              return;
                                    }
                                }
/* 1094 */                      return;
                            }
/* 1091 */                  Util.showDefaultToast(ProfileFragment.this.getActivity(), ProfileFragment.this.getString(C0257R.string.reposts_disabled_fail));
                        }
                    }

                    @Override // co.vine.android.client.AppSessionListener
/* 1097 */          public void onReportUserComplete(String reqId, int statusCode, String reasonPhrase) {
                        Activity activity;
/* 1098 */              if (statusCode != 200 && (activity = ProfileFragment.this.getActivity()) != null) {
/* 1101 */                  Util.showDefaultToast(activity, ProfileFragment.this.getString(C0257R.string.user_reported_error));
                        }
                    }

/* 1106 */          private boolean isUsernameValid() {
                        return (ProfileFragment.access$400(ProfileFragment.this) == null || TextUtils.isEmpty(ProfileFragment.access$400(ProfileFragment.this).username)) ? false : true;
                    }

                    @Override // co.vine.android.BaseTimelineFragment.TimeLineSessionListener, co.vine.android.client.AppSessionListener
/* 1111 */          public void onDeletePostComplete(String reqId, long postId, int statusCode, String reasonPhrase) {
/* 1112 */              super.onDeletePostComplete(reqId, postId, statusCode, reasonPhrase);
/* 1113 */              if (statusCode >= 200 && statusCode < 300) {
/* 1114 */                  if (ProfileFragment.this.mFeedAdapter != null) {
/* 1115 */                      ((FeedAdapter) ProfileFragment.this.mFeedAdapter).removePost(postId);
                            }
/* 1117 */                  ProfileFragment.access$200(ProfileFragment.this).notifyDataSetChanged();
/* 1118 */                  ProfileFragment.access$1300(ProfileFragment.this);
                        }
                    }

                    @Override // co.vine.android.client.AppSessionListener
/* 1125 */          public void onRespondFollowRequestComplete(String reqId, int statusCode, String reasonPhrase, boolean accept, long userId) {
/* 1126 */              if (statusCode == 200) {
/* 1127 */                  if (ProfileFragment.access$1400(ProfileFragment.this) != null) {
/* 1128 */                      View inner = ProfileFragment.access$1400(ProfileFragment.this).findViewById(C0257R.id.follow_request_header_inner);
/* 1129 */                      inner.setLayoutParams(new LinearLayout.LayoutParams(-1, 0));
/* 1131 */                      ProfileFragment.access$1400(ProfileFragment.this).setVisibility(8);
/* 1140 */                      return;
                            }
/* 1140 */                  return;
                        }
/* 1134 */              if (accept) {
/* 1135 */                  Util.showCenteredToast(ProfileFragment.this.getActivity(), C0257R.string.accept_failed);
                        } else {
/* 1137 */                  Util.showCenteredToast(ProfileFragment.this.getActivity(), C0257R.string.deny_failed);
                        }
                    }

                    @Override // co.vine.android.client.AppSessionListener
/* 1144 */          public void onGetUserIdComplete(String reqId, int statusCode, String reasonPhrase, long userId) {
                        if (userId > 0) {
/* 1146 */                  ProfileFragment.access$1500(ProfileFragment.this, userId);
/* 1147 */                  ProfileFragment.access$302(ProfileFragment.this, ProfileFragment.access$1200(ProfileFragment.this) == ProfileFragment.this.mAppController.getActiveId());
/* 1148 */                  if (ProfileFragment.access$300(ProfileFragment.this)) {
/* 1149 */                      ProfileFragment.access$1602(ProfileFragment.this, Util.getDefaultSharedPrefs(ProfileFragment.this.getActivity()).getBoolean(Settings.PREF_HIDE_PROFILE_REPOSTS, false));
/* 1151 */                      ProfileFragment.access$800(ProfileFragment.this, ProfileFragment.this.isFocused() || ProfileFragment.access$700(ProfileFragment.this));
/* 1152 */                      ProfileFragment.access$500(ProfileFragment.this, UrlCachePolicy.CACHE_ONLY);
/* 1158 */                      return;
                            }
/* 1158 */                  return;
                        }
/* 1156 */              ProfileFragment.this.getActivity().finish();
                    }

                    @Override // co.vine.android.client.AppSessionListener
/* 1161 */          public void onFavoriteUserComplete(String reqId, int statusCode, String reasonPhrase) {
/* 1162 */              VineUser user = ProfileFragment.access$400(ProfileFragment.this);
/* 1163 */              if (statusCode != 200) {
/* 1164 */                  ProfileFragment.access$1702(ProfileFragment.this, ProfileFragment.access$1700(ProfileFragment.this) ? false : true);
/* 1165 */                  ProfileFragment.this.getActivity().supportInvalidateOptionsMenu();
/* 1166 */                  Util.showCenteredToast(ProfileFragment.this.getActivity(), C0257R.string.generic_error);
                        } else if (user != null && !user.isFollowing()) {
/* 1168 */                  user.following = 1;
/* 1169 */                  ProfileFragment.this.mProfileHeaderAdapter.updateFollowButtonState(user);
                        }
                    }

                    @Override // co.vine.android.client.AppSessionListener
/* 1174 */          public void onFollowComplete(String reqId, int statusCode, String reasonPhrase, long userId) {
/* 1175 */              if (statusCode == 200) {
/* 1176 */                  ProfileFragment.this.mHandler.postDelayed(ProfileFragment.access$900(ProfileFragment.this), 500L);
                        }
                    }
                }

/* 1183 */      class PrivateAccountAdapter extends BaseAdapter {
                    boolean blocked;
                    boolean priv;

/* 1184 */          public PrivateAccountAdapter(boolean blocked, boolean priv) {
/* 1186 */              this.blocked = blocked;
/* 1187 */              this.priv = priv;
                    }

                    @Override // android.widget.Adapter
/* 1191 */          public int getCount() {
/* 1192 */              return 1;
                    }

                    @Override // android.widget.Adapter
/* 1196 */          public Object getItem(int i) {
/* 1197 */              return null;
                    }

                    @Override // android.widget.Adapter
/* 1201 */          public long getItemId(int i) {
/* 1202 */              return 0L;
                    }

                    @Override // android.widget.Adapter
/* 1206 */          public View getView(int i, View view, ViewGroup root) {
                        return this.blocked ? LayoutInflater.from(ProfileFragment.this.getActivity()).inflate(C0257R.layout.profile_blocked, root, false) : LayoutInflater.from(ProfileFragment.this.getActivity()).inflate(C0257R.layout.profile_locked, root, false);
                    }
                }

/* 1215 */      private class ColorChangedReceiver extends BroadcastReceiver {
/* 1216 */          private ColorChangedReceiver() {
                    }

/* 1216 */          /* synthetic */ ColorChangedReceiver(ProfileFragment x0, AnimationAnimationListenerC02541 x1) {
/* 1217 */              this();
                    }

                    @Override // android.content.BroadcastReceiver
/* 1220 */          public void onReceive(Context context, Intent intent) {
/* 1221 */              if (intent != null && VineService.ACTION_PROFILE_COLOR_CHANGED.equals(intent.getAction()) && ProfileFragment.access$400(ProfileFragment.this) != null && ProfileFragment.this.mFocused) {
/* 1224 */                  BaseControllerActionBarActivity activity = (BaseControllerActionBarActivity) ProfileFragment.this.getActivity();
/* 1225 */                  ProfileFragment.access$400(ProfileFragment.this).profileBackground = Util.getDefaultSharedPrefs(activity).getInt("profile_background", Settings.DEFAULT_PROFILE_COLOR);
/* 1228 */                  activity.setActionBarColor(ProfileFragment.access$400(ProfileFragment.this).profileBackground);
/* 1229 */                  activity.notifyColorChange(ProfileFragment.access$400(ProfileFragment.this).profileBackground);
/* 1230 */                  if (ProfileFragment.this.mFeedAdapter != null) {
/* 1231 */                      ProfileFragment.this.mFeedAdapter.notifyDataSetChanged();
                            }
                        }
                    }
                }

                /* JADX INFO: renamed from: co.vine.android.ProfileFragment$2 */
/* 1236 */      class RunnableC02552 implements Runnable {
/* 1237 */          RunnableC02552() {
                    }

                    @Override // java.lang.Runnable
/* 1240 */          public void run() {
                        View anchor;
/* 1241 */              Activity context = ProfileFragment.this.getActivity();
/* 1242 */              if (!OnboardHelper.getFavoriteUserTooltipShown(context) && (anchor = context.findViewById(C0257R.id.favorite_user)) != null && ProfileFragment.access$400(ProfileFragment.this) != null) {
/* 1245 */                  String user = TextUtils.isEmpty(ProfileFragment.access$400(ProfileFragment.this).username) ? "" : ProfileFragment.access$400(ProfileFragment.this).username;
/* 1246 */                  TooltipOverlayView overlay = new TooltipOverlayView(context, ProfileFragment.this.getString(C0257R.string.favorite_user_tooltip, user));
/* 1248 */                  ProfileFragment.access$1800(ProfileFragment.this).addView(overlay);
/* 1249 */                  overlay.showTooltip(anchor);
/* 1250 */                  ProfileFragment.access$1902(ProfileFragment.this, overlay);
/* 1251 */                  OnboardHelper.setFavoriteUserTooltipShown(context);
                        }
                    }
                }
            }
