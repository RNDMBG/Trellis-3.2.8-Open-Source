            package co.vine.android;

            import android.accounts.Account;
            import android.accounts.AccountManager;
            import android.app.Activity;
            import android.app.ProgressDialog;
            import android.content.BroadcastReceiver;
            import android.content.Context;
            import android.content.Intent;
            import android.content.IntentFilter;
            import android.content.SharedPreferences;
            import android.content.res.Resources;
            import android.graphics.PorterDuff;
            import android.graphics.PorterDuffColorFilter;
            import android.graphics.Rect;
            import android.graphics.drawable.Drawable;
            import android.graphics.drawable.GradientDrawable;
            import android.net.Uri;
            import android.os.Bundle;
            import android.os.Handler;
            import android.os.Message;
            import android.os.Process;
            import android.support.v4.app.Fragment;
            import android.support.v4.util.LongSparseArray;
            import android.support.v4.view.ViewPager;
            import android.text.TextUtils;
            import android.util.TypedValue;
            import android.view.LayoutInflater;
            import android.view.Menu;
            import android.view.MenuItem;
            import android.view.View;
            import android.widget.ImageView;
            import android.widget.TabHost;
            import android.widget.TabWidget;
            import android.widget.Toast;
            import co.vine.android.api.VinePost;
            import co.vine.android.api.VineRTCConversation;
            import co.vine.android.api.VineRTCParticipant;
            import co.vine.android.api.VineSingleNotification;
            import co.vine.android.api.VineUser;
            import co.vine.android.client.AppController;
            import co.vine.android.client.AppSessionListener;
            import co.vine.android.client.Session;
            import co.vine.android.client.VineAPI;
            import co.vine.android.client.VineAccountHelper;
            import co.vine.android.drawable.CenteredTextDrawable;
            import co.vine.android.network.UrlCachePolicy;
            import co.vine.android.prefetch.PrefetchManager;
            import co.vine.android.service.GCMNotificationService;
            import co.vine.android.service.GCMRegistrationService;
            import co.vine.android.service.VineService;
            import co.vine.android.util.BuildUtil;
            import co.vine.android.util.ClientFlagsHelper;
            import co.vine.android.util.CommonUtil;
            import co.vine.android.util.CrashUtil;
            import co.vine.android.util.CrossConstants;
            import co.vine.android.util.LinkDispatcher;
            import co.vine.android.util.MuteUtil;
            import co.vine.android.util.Util;
            import co.vine.android.util.analytics.FlurryUtils;
            import co.vine.android.widget.OnboardHelper;
            import co.vine.android.widget.Typefaces;
            import co.vine.android.widget.tabs.IconTabHost;
            import co.vine.android.widget.tabs.ScrollAwayTabWidget;
            import co.vine.android.widget.tabs.TabIndicator;
            import co.vine.android.widget.tabs.TabInfo;
            import co.vine.android.widget.tabs.TabsAdapter;
            import com.edisonwang.android.slog.SLog;
            import com.google.android.gms.common.GooglePlayServicesUtil;
            import com.googlecode.javacv.cpp.avformat;
            import com.twitter.android.widget.TopScrollable;
            import java.lang.ref.WeakReference;
            import java.util.ArrayList;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 86 */    public class HomeTabActivity extends BaseTimelineActivity implements TabHost.OnTabChangeListener, IconTabHost.OnTabClickedListener, View.OnLongClickListener {
                public static final String ACTION_VM_SENT = "co.vine.android.VM_SENT";
                public static final int DIALOG_STORE_CONTACTS = 1;
                public static final String HOST_POST = "post";
                public static final String HOST_TIMELINES = "timelines";
                public static final int INDEX_ACTIVITY_TAB = 1;
                public static final int INDEX_EXPLORE_TAB = 2;
                public static final int INDEX_HOME_TAB = 0;
                public static final int INDEX_PROFILE_TAB = 3;
                public static final String PREFIX_CONFIRM_EMAIL = "confirmEmail";
                public static final String PREFIX_SHARED_VIDEO = "v";
                public static final String PREFIX_USER_ID = "u";
                public static final int REQUEST_CODE_VIEW_NOTIF = 1;
                public static final String SCHEME_VINE = "vine";
                public static final String TAG_ACTIVITY = "activity";
                public static final String TAG_EXPLORE = "explore";
                public static final String TAG_HOME = "home";
                public static final String TAG_PROFILE = "profile";
                private int mActivityCount;
                private int mDisabledTabColor;
                private int mEnabledTabColor;
                private boolean mExploreRefreshReciverIsRegistered;
                private MenuItem mInboxMenu;
                private boolean mIsNewUser;
                private int mLastItem;
                private LongSparseArray<Long> mLastMessageMap;
                private Menu mMenu;
                private int mMessageCount;
                private String mRecentTab;
                private boolean mShouldShowMenu;
                private boolean mShowActivityFeed;
                private Thread mStartUploadServiceThread;
                private IconTabHost mTabHost;
                private TabsAdapter mTabsAdapter;
                private ViewPager mViewPager;
                public static final String ACTION_VERIFICATION_COMPLETE = BuildUtil.getAuthority() + ".EMAIL_VERIFIED";
                private static final IntentFilter ACTIVITY_COUNT_FILTER = new IntentFilter();
                private final BroadcastReceiver mActivityCountReceiver = new C02161();
                private final BroadcastReceiver mExploreRefreshReciver = new C02172();
                private boolean mScrollAwayNavEnabled = true;

/* 87 */        static /* synthetic */ int access$002(HomeTabActivity x0, int x1) {
/* 88 */            x0.mMessageCount = x1;
                    return x1;
                }

/* 87 */        static /* synthetic */ int access$102(HomeTabActivity x0, int x1) {
/* 88 */            x0.mActivityCount = x1;
                    return x1;
                }

/* 87 */        static /* synthetic */ void access$200(HomeTabActivity x0) {
/* 88 */            x0.invalidateMessagesBadge();
                }

/* 87 */        static /* synthetic */ TabsAdapter access$300(HomeTabActivity x0) {
/* 88 */            return x0.mTabsAdapter;
                }

/* 87 */        static /* synthetic */ ViewPager access$400(HomeTabActivity x0) {
/* 88 */            return x0.mViewPager;
                }

/* 87 */        static /* synthetic */ Thread access$502(HomeTabActivity x0, Thread x1) {
/* 88 */            x0.mStartUploadServiceThread = x1;
                    return x1;
                }

/* 87 */        static /* synthetic */ LongSparseArray access$600(HomeTabActivity x0) {
/* 88 */            return x0.mLastMessageMap;
                }

/* 87 */        static /* synthetic */ boolean access$700(HomeTabActivity x0) {
/* 88 */            return x0.mIsNewUser;
                }

                static {
/* 127 */           ACTIVITY_COUNT_FILTER.addAction(VineService.BC_ACTIVITY_COUNTS);
                }

                /* JADX INFO: renamed from: co.vine.android.HomeTabActivity$1 */
/* 130 */       class C02161 extends BroadcastReceiver {
/* 131 */           C02161() {
                    }

                    @Override // android.content.BroadcastReceiver
/* 135 */           public void onReceive(Context context, Intent intent) {
/* 136 */               HomeTabActivity.access$002(HomeTabActivity.this, intent.getIntExtra(VineService.EXTRA_MESSAGES_COUNT, 0));
/* 137 */               HomeTabActivity.access$102(HomeTabActivity.this, intent.getIntExtra(VineService.EXTRA_NOTIFICATIONS_COUNT, 0));
/* 138 */               HomeTabActivity.access$200(HomeTabActivity.this);
/* 139 */               HomeTabActivity.this.invalidateActivityBadge();
                    }
                }

                /* JADX INFO: renamed from: co.vine.android.HomeTabActivity$2 */
/* 145 */       class C02172 extends BroadcastReceiver {
/* 146 */           C02172() {
                    }

                    @Override // android.content.BroadcastReceiver
/* 150 */           public void onReceive(Context context, Intent intent) {
/* 151 */               TabInfo tab = HomeTabActivity.access$300(HomeTabActivity.this).getTab(2);
/* 152 */               if (tab != null) {
/* 153 */                   Fragment fragment = tab.fragment();
/* 154 */                   if (fragment instanceof ExploreFragment) {
/* 155 */                       ((ExploreFragment) fragment).reloadWebView(false);
                            }
                        }
                    }
                }

                @Override // co.vine.android.BaseTimelineActivity
/* 172 */       protected boolean isAutoCaptureIconEnabled() {
/* 173 */           return false;
                }

                @Override // co.vine.android.BaseControllerActionBarActivity
/* 177 */       public boolean isConversationSideMenuEnabled() {
/* 178 */           return true;
                }

                @Override // co.vine.android.BaseActionBarActivity, com.jeremyfeinstein.slidingmenu.lib.SlidingActivityHelper.MenuStateHandler.MenuStateListener
/* 182 */       public void onMenuClose() {
/* 183 */           if (!MuteUtil.isMuted(this)) {
/* 184 */               sendBroadcast(new Intent(MuteUtil.ACTION_CHANGED_TO_UNMUTE), CrossConstants.BROADCAST_PERMISSION);
                    }
                }

                @Override // co.vine.android.BaseActionBarActivity, com.jeremyfeinstein.slidingmenu.lib.SlidingActivityHelper.MenuStateHandler.MenuStateListener
/* 189 */       public void onMenuOpened() {
/* 190 */           super.onMenuOpened();
/* 191 */           invalidateMessagesBadge();
                }

                @Override // co.vine.android.BaseActionBarActivity, android.support.v7.app.ActionBarActivity, android.support.v4.app.FragmentActivity, android.app.Activity
/* 195 */       public void onCreate(Bundle savedInstanceState) {
/* 196 */           super.onCreate(savedInstanceState, C0257R.layout.main, true);
/* 198 */           if (BuildUtil.isExplore()) {
/* 199 */               setupTabs();
                    }
/* 202 */           if (SLog.sLogsOn) {
/* 203 */               sendBroadcast(new Intent("co.vine.android.debug_receiver"));
                    }
/* 206 */           SLog.m465i("HomeTabActivity pid: {}", Integer.valueOf(Process.myPid()));
/* 208 */           setRequestedOrientation(1);
/* 210 */           setupActionBar((Boolean) false, (Boolean) null, (String) null, (Boolean) null, (Boolean) true);
/* 211 */           hideOuterDuplicateActionBar(getWindow().getDecorView());
/* 212 */           positionHomeLogo();
/* 212 */           this.mAppSessionListener = new HomeTabSessionListener();
/* 213 */           this.mLastMessageMap = new LongSparseArray<>();
/* 215 */           Intent intent = getIntent();
/* 216 */           if (intent.getExtras() != null) {
/* 217 */               this.mIsNewUser = intent.getBooleanExtra(StartActivity.EXTRA_IS_NEW_USER, false);
        }

/* 220 */           if (this.mAppController == null) {
/* 221 */               this.mAppController = AppController.getInstance(this);
                    }
/* 224 */           AppController appController = this.mAppController;
/* 226 */           if (appController.isLoggedIn()) {
/* 230 */               AccountManager am = AccountManager.get(this);
/* 231 */               Account acc = VineAccountHelper.getActiveAccountReadOnly(this);
/* 232 */               VineAccountHelper.migratePassword(am, acc, this);
/* 235 */               SharedPreferences prefs = Util.getDefaultSharedPrefs(this);
/* 237 */               IconTabHost tabHost = this.mTabHost;
/* 239 */               this.mViewPager = (ViewPager) findViewById(C0257R.id.pager);
/* 240 */               this.mViewPager.setPageMargin(getResources().getDimensionPixelSize(C0257R.dimen.home_pager_margin));
/* 241 */               this.mViewPager.setPageMarginDrawable(C0257R.color.bg_grouped_list);
/* 242 */               this.mViewPager.setOffscreenPageLimit(3);
/* 244 */               this.mTabsAdapter = new HomeTabTabsAdapter(tabHost);
/* 246 */               LayoutInflater inflater = getLayoutInflater();
/* 247 */               tabHost.setOnTabChangedListener(this);
/* 248 */               tabHost.setOnTabClickedListener(this);
/* 250 */               processStartIntent(intent);
/* 252 */               Resources res = getResources();
/* 254 */               this.mDisabledTabColor = res.getColor(C0257R.color.solid_dark_gray);
/* 255 */               this.mEnabledTabColor = res.getColor(C0257R.color.vine_green);
/* 258 */               Bundle timelineBundle = new Bundle();
/* 259 */               timelineBundle.putInt("empty_desc", C0257R.string.failed_to_load_posts);
/* 261 */               this.mTabsAdapter.addTab(newTabSpec(inflater, C0257R.layout.activity_tab_indicator, C0257R.drawable.ic_home, "home"), HomeTimelineFragment.class, timelineBundle);
/* 264 */               restoreActivityState();
/* 267 */               Bundle activityBundle = new Bundle();
/* 268 */               activityBundle.putBoolean("take_focus", this.mShowActivityFeed);
/* 269 */               activityBundle.putInt("empty_desc", C0257R.string.activity_empty);
/* 271 */               this.mTabsAdapter.addTab(newTabSpec(inflater, C0257R.layout.activity_tab_indicator, C0257R.drawable.ic_activity, TAG_ACTIVITY), ActivityFragment.class, activityBundle);
/* 274 */               Bundle exploreBundle = new Bundle();
/* 275 */               if (TextUtils.equals(this.mRecentTab, TAG_EXPLORE)) {
/* 276 */                   exploreBundle.putBoolean("take_focus", true);
                        }
/* 280 */               this.mTabsAdapter.addTab(newTabSpec(inflater, C0257R.layout.home_tab_indicator, C0257R.drawable.ic_explore, TAG_EXPLORE), ExploreFragment.class, exploreBundle);
/* 284 */               Bundle profileBundle = new Bundle();
/* 285 */               if (TextUtils.equals(this.mRecentTab, "profile")) {
/* 286 */                   profileBundle.putBoolean("take_focus", true);
                        }
/* 289 */               profileBundle.putLong("user_id", appController.getActiveId());
/* 290 */               profileBundle.putBoolean("refresh", true);
/* 291 */               this.mTabsAdapter.addTab(newTabSpec(inflater, C0257R.layout.home_tab_indicator, C0257R.drawable.ic_profile, "profile"), ProfileFragment.class, profileBundle);
/* 294 */               Session session = this.mAppController.getActiveSession();
/* 296 */               this.mAppController.determineCleanup(session);
/* 298 */               this.mActivityCount = prefs.getInt(Settings.PREF_UNREAD_NOTIFICATION_COUNT, 0);
/* 300 */               DelayHandler handler = new DelayHandler(this);
/* 301 */               if (handler.hasMessages(1)) {
/* 302 */                   handler.removeMessages(1);
                        }
/* 304 */               if (handler.hasMessages(2)) {
/* 305 */                   handler.removeMessages(2);
                        }
/* 308 */               handler.sendMessageDelayed(handler.obtainMessage(1, null), CommonUtil.MINUTE_MILLIS);
/* 310 */               handler.sendMessageDelayed(handler.obtainMessage(2, null), CommonUtil.MINUTE_MILLIS);
/* 312 */               handler.sendMessageDelayed(handler.obtainMessage(3, null), CommonUtil.MINUTE_MILLIS);
/* 315 */               AppImpl.getInstance().startCameraService(this);
/* 317 */               VineAccountHelper.syncNormalVideoPlayable(this);
/* 319 */               PrefetchManager.getInstance(this).delayNextPrefetch();
               UpdateManager.checkOnStartup(this);
                        try {
/* 322 */                   int errorCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
/* 323 */                   if (errorCode == 0) {
/* 325 */                       startService(GCMRegistrationService.getRegisterIntent(this, this.mAppController.getActiveId()));
                            }
                        } catch (Throwable e) {
/* 328 */                   CrashUtil.logException(e, "Failed to validate Google Player Service status.", new Object[0]);
                        }
                    }
                }

        private void positionHomeLogo() {
            int actionBarId = getResources().getIdentifier("action_bar", "id", getPackageName());
            View actionBar = actionBarId == 0 ? null : findViewById(actionBarId);
            View logo = findFirstToolbarImage(actionBar);
            if (logo == null) {
                return;
            }
            float inset = TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP, 8.0f,
                    getResources().getDisplayMetrics());
            logo.setTranslationX(inset);
        }

        private View findFirstToolbarImage(View view) {
            if (view instanceof ImageView) {
                return view;
            }
            if (view instanceof android.view.ViewGroup) {
                android.view.ViewGroup group = (android.view.ViewGroup) view;
                for (int i = 0; i < group.getChildCount(); i++) {
                    View result = findFirstToolbarImage(group.getChildAt(i));
                    if (result != null) {
                        return result;
                    }
                }
            }
            return null;
        }

        private boolean hideOuterDuplicateActionBar(View view) {
            if (view == null) {
                return false;
            }
            int actionBarContainerId = getResources().getIdentifier("action_bar_container", "id", "android");
            if (actionBarContainerId != 0 && view.getId() == actionBarContainerId) {
                view.setVisibility(View.GONE);
                return true;
            }
            if (view instanceof android.view.ViewGroup) {
                android.view.ViewGroup group = (android.view.ViewGroup) view;
                for (int i = 0; i < group.getChildCount(); i++) {
                    if (hideOuterDuplicateActionBar(group.getChildAt(i))) {
                        return true;
                    }
                }
            }
            return false;
        }

/* 332 */       private TabHost.TabSpec newTabSpec(LayoutInflater inflater, int layout, int icon, String tag) {
/* 333 */           TabIndicator indicator = TabIndicator.newIconIndicator(inflater, layout, this.mTabHost, icon, 0);
/* 334 */           indicator.setColor(this.mDisabledTabColor);
/* 335 */           return this.mTabHost.newTabSpec(tag).setIndicator(indicator);
                }

/* 338 */       private String trimSegment(String segment) {
/* 339 */           return segment.replaceAll("\\s+", "").replace("\\", "");
                }

                @Override // co.vine.android.BaseTimelineActivity, co.vine.android.BaseControllerActionBarActivity, co.vine.android.BaseActionBarActivity, android.support.v4.app.FragmentActivity, android.app.Activity
/* 343 */       protected void onPause() {
/* 344 */           super.onPause();
/* 345 */           if (!this.mExploreRefreshReciverIsRegistered) {
/* 346 */               this.mExploreRefreshReciverIsRegistered = true;
/* 347 */               registerReceiver(this.mExploreRefreshReciver, AppController.ACTION_UPDATED_FILTER, CrossConstants.BROADCAST_PERMISSION, null);
                    }
                    try {
/* 350 */               unregisterReceiver(this.mActivityCountReceiver);
                    } catch (IllegalArgumentException e) {
                    }
                }

                @Override // co.vine.android.BaseActionBarActivity, android.support.v4.app.FragmentActivity, android.app.Activity
/* 357 */       protected void onStart() {
/* 358 */           super.onStart();
/* 359 */           ClientFlagsHelper.fetchClientFlags(this, true, true);
/* 360 */           if (ClientFlagsHelper.zeroRatingIsEnabled(this)) {
/* 361 */               FlurryUtils.trackZeroRatedSessionStarted(true);
                    }
                }

                @Override // co.vine.android.BaseActionBarActivity, android.support.v7.app.ActionBarActivity, android.support.v4.app.FragmentActivity, android.app.Activity
/* 366 */       protected void onStop() {
/* 367 */           super.onStop();
                }

                @Override // co.vine.android.BaseControllerActionBarActivity, co.vine.android.ScrollListener
/* 370 */       public void onScroll(int delta) {
/* 371 */           if (this.mScrollAwayNavEnabled) {
/* 372 */               ScrollAwayTabWidget tabWidget = (ScrollAwayTabWidget) this.mTabHost.getTabWidget();
/* 373 */               tabWidget.onScroll(delta);
/* 374 */               Fragment fragment = this.mTabsAdapter.getCurrentFragment();
/* 375 */               if (fragment instanceof BaseArrayListFragment) {
/* 376 */                   ((BaseArrayListFragment) fragment).setNavBottom(tabWidget.getNavBottom());
                        }
                    }
                }

/* 381 */       public void setScrollAwayNavEnabled(boolean enabled) {
/* 382 */           this.mScrollAwayNavEnabled = enabled;
                }

/* 385 */       public boolean shouldChangeActionBarColor() {
                    return this.mViewPager != null && this.mViewPager.getCurrentItem() == 3;
                }

/* 389 */       private class HomeTabTabsAdapter extends TabsAdapter {
/* 390 */           public HomeTabTabsAdapter(TabHost tabHost) {
/* 392 */               super(HomeTabActivity.this, tabHost, HomeTabActivity.access$400(HomeTabActivity.this), null);
                    }

                    @Override // co.vine.android.widget.tabs.TabsAdapter, android.support.v4.view.ViewPager.OnPageChangeListener
/* 396 */           public void onPageSelected(int position) {
/* 397 */               super.onPageSelected(position);
/* 398 */               HomeTabActivity.this.resetNav();
/* 399 */               if (position == 1) {
/* 400 */                   Util.getDefaultSharedPrefs(HomeTabActivity.this.getApplicationContext()).edit().putInt(Settings.PREF_UNREAD_NOTIFICATION_COUNT, 0).commit();
/* 402 */                   HomeTabActivity.access$102(HomeTabActivity.this, 0);
/* 403 */                   HomeTabActivity.this.invalidateActivityBadge();
                        }
                    }
                }

                @Override // android.support.v4.app.FragmentActivity, android.app.Activity
/* 409 */       protected void onNewIntent(Intent intent) {
/* 410 */           super.onNewIntent(intent);
/* 411 */           processStartIntent(intent);
                }

/* 414 */       public void showPage(int position, Fragment fragment) {
/* 415 */           if (this.mTabsAdapter != null && this.mViewPager != null) {
/* 416 */               this.mViewPager.setCurrentItem(position);
/* 417 */               if (fragment instanceof BaseArrayListFragment) {
/* 418 */                   ((BaseArrayListFragment) fragment).onMoveTo(1);
                        }
/* 420 */               TabInfo tab = this.mTabsAdapter.getTab(this.mViewPager.getCurrentItem());
/* 421 */               if (tab != null) {
/* 422 */                   Fragment current = tab.fragment();
/* 423 */                   if (current instanceof BaseArrayListFragment) {
/* 424 */                       ((BaseArrayListFragment) current).onMoveAway(-1);
                            }
/* 426 */                   resetNav();
/* 427 */                   logTabChange(fragment.getTag());
                        }
                    }
                }

/* 432 */       public void resetNav() {
                    ScrollAwayTabWidget widget;
/* 433 */           if (this.mTabHost != null && (widget = (ScrollAwayTabWidget) this.mTabHost.getTabWidget()) != null) {
/* 436 */               widget.resetScroll();
/* 437 */               widget.invalidate();
/* 438 */               int navBottom = widget.getNavBottom();
/* 440 */               Fragment fragment = this.mTabsAdapter.getCurrentFragment();
/* 441 */               if (fragment != null && (fragment instanceof BaseArrayListFragment)) {
/* 442 */                   ((BaseArrayListFragment) fragment).setNavBottom(navBottom);
                        }
/* 445 */               this.mScrollAwayNavEnabled = true;
                    }
                }

/* 451 */       public void onNotifyClick() {
/* 452 */           getSlidingMenu().toggle(true);
                }

/* 455 */       public void markMessagesAsDismissed() {
/* 456 */           Util.getDefaultSharedPrefs(this).edit().putBoolean(Settings.PREF_UNREAD_MESSAGES_DISMISSED, true);
                }

/* 464 */       public static final class DelayHandler extends Handler {
                    public static final int DETERMINE_SERVER_STATUS = 3;
                    public static final int FETCH_FOLLOWERS = 2;
                    public static final int FETCH_ME = 1;
                    private final WeakReference<Context> mContextRef;

/* 465 */           public DelayHandler(Context context) {
/* 467 */               this.mContextRef = new WeakReference<>(context.getApplicationContext());
                    }

                    @Override // android.os.Handler
/* 471 */           public void handleMessage(Message msg) {
/* 472 */               Context context = this.mContextRef.get();
/* 473 */               if (context != null) {
/* 476 */                   AppController appController = AppController.getInstance(context);
                            switch (msg.what) {
                                case 1:
/* 479 */                           appController.fetchUsersMe(appController.getActiveId(), UrlCachePolicy.NETWORK_THEN_CACHE);
                                    break;
                                case 2:
/* 483 */                           appController.fetchFollowing(appController.getActiveId(), 1, 0L);
                                    break;
                                case 3:
/* 488 */                           appController.determineServerStatus();
                                    break;
                            }
                        }
                    }
                }

/* 495 */       public void markActivitiesRead() {
/* 496 */           Util.getDefaultSharedPrefs(this).edit().putInt(Settings.PREF_UNREAD_NOTIFICATION_COUNT, 0).commit();
/* 498 */           this.mActivityCount = 0;
/* 499 */           invalidateActivityBadge();
                }

                @Override // android.widget.TabHost.OnTabChangeListener
/* 503 */       public void onTabChanged(String tag) {
/* 504 */           if (SLog.sLogsOn) {
                    try {
/* 505 */                   SLog.m446d(Util.printCursorWindowStats());
                    } catch (Throwable ignored) {
                    }
                    }
/* 507 */           changeTabColor(this.mLastItem);
/* 508 */           int tab = this.mTabHost.getCurrentTab();
/* 509 */           this.mViewPager.setCurrentItem(tab);
/* 510 */           this.mLastItem = tab;
/* 511 */           this.mRecentTab = tag;
/* 512 */           if (TAG_ACTIVITY.equals(tag)) {
/* 513 */               markActivitiesRead();
                    }
/* 515 */           if ("home".equals(tag)) {
/* 516 */               setHomeNewCount(0);
                    }
                    switch (tab) {
                        case 0:
/* 520 */                   getSlidingMenu().setTouchModeAbove(1);
                            break;
                        default:
/* 523 */                   getSlidingMenu().setTouchModeAbove(0);
                            break;
                    }
/* 526 */           logTabChange(tag);
                }

/* 529 */       private void logTabChange(String tag) {
/* 530 */           CrashUtil.set("Current Tab", tag + " - " + System.currentTimeMillis());
/* 531 */           if (tag != null && !tag.contains("switcher")) {
/* 532 */               FlurryUtils.trackTabChange(tag);
                    }
                }

/* 536 */       public void showMenuIfNeeded() {
/* 537 */           if (this.mShouldShowMenu) {
/* 538 */               SLog.m446d("Menu requested.");
/* 539 */               this.mShouldShowMenu = false;
/* 540 */               getSlidingMenu().showMenu(false);
                    }
                }

                @Override // co.vine.android.BaseActionBarActivity, android.support.v4.app.FragmentActivity, android.app.Activity
/* 545 */       protected void onDestroy() {
/* 546 */           super.onDestroy();
/* 547 */           if (this.mExploreRefreshReciverIsRegistered) {
/* 548 */               this.mExploreRefreshReciverIsRegistered = false;
/* 549 */               unregisterReceiver(this.mExploreRefreshReciver);
                    }
                }

                /* JADX INFO: renamed from: co.vine.android.HomeTabActivity$3 */
/* 553 */       class RunnableC02183 implements Runnable {
/* 554 */           RunnableC02183() {
                    }

                    @Override // java.lang.Runnable
/* 557 */           public void run() {
/* 558 */               Intent i = AppImpl.getInstance().getNotifyFailedIntent(HomeTabActivity.this);
/* 559 */               if (i != null) {
/* 560 */                   HomeTabActivity.this.startService(i);
                        }
/* 562 */               HomeTabActivity.access$502(HomeTabActivity.this, null);
                    }
                }

/* 553 */       private synchronized Thread getUploadServiceThread() {
                    Thread thread;
                    try {
                        if (this.mStartUploadServiceThread == null) {
/* 555 */                   this.mStartUploadServiceThread = new Thread(new RunnableC02183());
/* 565 */                   thread = this.mStartUploadServiceThread;
                        } else {
                            thread = null;
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                    return thread;
                }

                @Override // co.vine.android.BaseControllerActionBarActivity, co.vine.android.BaseActionBarActivity, android.support.v4.app.FragmentActivity, android.app.Activity
/* 571 */       protected void onResume() {
/* 572 */           super.onResume();
/* 573 */           if (this.mTabsAdapter != null && this.mTabHost != null) {
/* 574 */               this.mTabsAdapter.previousTab = this.mTabHost.getCurrentTab();
                    }
/* 576 */           Thread startUploadServiceThread = getUploadServiceThread();
/* 577 */           if (startUploadServiceThread != null) {
/* 578 */               startUploadServiceThread.start();
                    }
/* 580 */           TabWidget tabWidget = this.mTabHost.getTabWidget();
/* 581 */           int tabCount = tabWidget == null ? 0 : tabWidget.getTabCount();
                    for (int j = 0; j < tabCount; j++) {
/* 583 */               View tabView = tabWidget.getChildAt(j);
/* 584 */               if (tabView != null) {
/* 585 */                   tabView.setTag(Integer.valueOf(j + 1));
/* 586 */                   tabView.setOnLongClickListener(this);
                        }
                    }
/* 589 */           registerReceiver(this.mActivityCountReceiver, ACTIVITY_COUNT_FILTER, CrossConstants.BROADCAST_PERMISSION, null);
/* 590 */           this.mAppController.fetchActivityCounts();
/* 591 */           invalidateActivityBadge();
/* 592 */           invalidateMessagesBadge();
                }

                @Override // co.vine.android.BaseActionBarActivity, android.support.v4.app.FragmentActivity, android.app.Activity
/* 596 */       protected void onActivityResult(int requestCode, int resultCode, Intent data) {
/* 597 */           if (this.mTabsAdapter != null) {
/* 598 */               ArrayList<TabInfo> tabs = this.mTabsAdapter.getTabs();
/* 599 */               if (requestCode == 0) {
/* 600 */                   SharedPreferences sp = Util.getDefaultSharedPrefs(this);
/* 601 */                   sp.edit().putBoolean(HomeTimelineFragment.PREF_INVITE_BANNER_CLICKED, true).apply();
/* 602 */                   int followCount = sp.getInt(Settings.PREF_FOLLOWING_COUNT, 0);
/* 604 */                   Fragment homeTimeline = tabs.get(this.mTabHost.getCurrentTab()).fragment();
/* 605 */                   if (followCount > 5 && (homeTimeline instanceof HomeTimelineFragment)) {
/* 606 */                       ((HomeTimelineFragment) homeTimeline).removeBanner();
/* 627 */                       return;
                            }
/* 627 */                   return;
                        }
/* 608 */               if (requestCode == 1 && resultCode == 1) {
                            for (TabInfo tab : tabs) {
/* 611 */                       Fragment fragment = tab.fragment();
/* 612 */                       if (fragment instanceof BaseTimelineFragment) {
/* 613 */                           fragment.onActivityResult(requestCode, resultCode, data);
                                }
                            }
/* 627 */                   return;
                        }
/* 616 */               if (requestCode == 1 && resultCode == -1) {
/* 617 */                   if (BuildUtil.isExplore() && this.mMenu != null) {
/* 618 */                       this.mMenu.removeItem(C0257R.id.capture);
/* 627 */                       return;
                            }
/* 627 */                   return;
                        }
/* 621 */               Fragment frag = tabs.get(this.mTabHost.getCurrentTab()).fragment();
/* 622 */               if (frag != null) {
/* 623 */                   frag.onActivityResult(requestCode, resultCode, data);
                        }
                    }
                }

                @Override // co.vine.android.BaseTimelineActivity
/* 631 */       protected BaseTimelineFragment getCurrentTimeLineFragment() {
                    try {
/* 632 */               Fragment frag = this.mTabsAdapter.getCurrentFragment();
/* 633 */               if (frag instanceof BaseTimelineFragment) {
/* 634 */                   return (BaseTimelineFragment) frag;
                        }
                        return null;
                    } catch (Exception e) {
                        return null;
                    }
                }

                @Override // android.support.v7.app.ActionBarActivity
/* 644 */       public void onSupportContentChanged() {
/* 645 */           super.onSupportContentChanged();
/* 646 */           if (!BuildUtil.isExplore()) {
/* 647 */               setupTabs();
                    }
                }

/* 651 */       private void setupTabs() {
/* 652 */           this.mTabHost = (IconTabHost) findViewById(android.R.id.tabhost);
/* 653 */           if (this.mTabHost == null) {
/* 654 */               throw new RuntimeException("Your content must have a TabHost whose id attribute is 'android.R.id.tabhost'");
                    }
/* 659 */           this.mTabHost.setup();
/* 660 */           this.mViewPager = (ViewPager) findViewById(C0257R.id.pager);
                }

/* 663 */       public static void start(Activity activity, String tag) {
/* 664 */           Intent intent = new Intent(activity, (Class<?>) HomeTabActivity.class).addFlags(avformat.AVFMT_SEEK_TO_PTS);
/* 666 */           activity.startActivity(intent);
/* 667 */           activity.finish();
                }

                @Override // co.vine.android.BaseTimelineActivity, co.vine.android.BaseActionBarActivity, android.app.Activity
/* 671 */       public boolean onCreateOptionsMenu(Menu menu) {
/* 672 */           getMenuInflater().inflate(C0257R.menu.home, menu);
/* 673 */           SharedPreferences prefs = Util.getDefaultSharedPrefs(this);
/* 674 */           if (BuildUtil.isExplore() && prefs.getBoolean(Settings.PREF_GINGERBREAD_NOTIF_DISMISSED, false)) {
/* 676 */               menu.removeItem(C0257R.id.capture);
                    }
/* 678 */           this.mInboxMenu = menu.findItem(C0257R.id.inbox);
/* 679 */           invalidateMessagesBadge();
/* 680 */           this.mMenu = menu;
/* 681 */           return super.onCreateOptionsMenu(menu);
                }

/* 684 */       private void invalidateMessagesBadge() {
/* 685 */           Resources res = getResources();
/* 686 */           if (res != null && this.mInboxMenu != null) {
/* 687 */               Drawable currentDrawable = this.mInboxMenu.getIcon();
/* 688 */               CenteredTextDrawable inboxBadgeDrawable = null;
/* 689 */               if (currentDrawable instanceof CenteredTextDrawable) {
/* 690 */                   inboxBadgeDrawable = (CenteredTextDrawable) currentDrawable;
                        }
/* 692 */               if (inboxBadgeDrawable == null) {
/* 693 */                   inboxBadgeDrawable = new CenteredTextDrawable(res, C0257R.drawable.vm_bubble_outline, C0257R.drawable.vm_bubble_fill);
/* 694 */                   inboxBadgeDrawable.setColorFilter(1, new PorterDuffColorFilter(Util.getProfileColor(this) | (-16777216), PorterDuff.Mode.SRC_IN));
/* 695 */                   int padding = (int) (6.0f * res.getDisplayMetrics().density);
/* 696 */                   inboxBadgeDrawable.setTextPadding(new Rect(padding, padding, padding, padding));
/* 697 */                   inboxBadgeDrawable.setTextColor(-1);
/* 698 */                   inboxBadgeDrawable.setTypeFace(Typefaces.get(this).boldContentBold);
/* 699 */                   inboxBadgeDrawable.setTextSize(TypedValue.applyDimension(2, 12.0f, res.getDisplayMetrics()));
                        }
/* 702 */               if (this.mMessageCount == 0) {
/* 703 */                   this.mInboxMenu.setIcon(C0257R.drawable.ic_action_inbox);
                        } else {
/* 705 */                   inboxBadgeDrawable.setText(this.mMessageCount > 99 ? "99+" : String.valueOf(this.mMessageCount));
/* 707 */                   this.mInboxMenu.setIcon(inboxBadgeDrawable);
                        }
                    }
                }

/* 712 */       public void setHomeNewCount(int newCount) {
/* 713 */           if (this.mTabsAdapter != null) {
/* 714 */               SLog.m465i("Set home badge count: {}", Integer.valueOf(newCount));
/* 715 */               this.mTabsAdapter.setNew(0, newCount > 0);
/* 716 */               ImageView indicator = this.mTabsAdapter.getNewIndicatorForTab(0);
/* 717 */               Resources res = getResources();
/* 718 */               if (res != null && indicator != null) {
/* 719 */                   Drawable drawable = indicator.getDrawable();
/* 720 */                   CenteredTextDrawable textDrawable = null;
/* 721 */                   if (drawable instanceof CenteredTextDrawable) {
/* 722 */                       textDrawable = (CenteredTextDrawable) drawable;
                            }
/* 724 */                   if (textDrawable == null) {
/* 725 */                       textDrawable = makeAcitivytNewIndicator(res);
/* 726 */                       indicator.setImageDrawable(textDrawable);
                            }
/* 728 */                   int padding = (int) (6.0f * res.getDisplayMetrics().density);
/* 729 */                   textDrawable.setTextPadding(new Rect(padding, padding, padding, padding));
/* 730 */                   textDrawable.setText(newCount < 10 ? String.valueOf(newCount) : "9+");
                        }
                    }
                }

/* 735 */       public void invalidateActivityBadge() {
/* 736 */           if (this.mTabsAdapter != null) {
/* 737 */               this.mTabsAdapter.setNew(1, this.mActivityCount > 0);
/* 738 */               ImageView indicator = this.mTabsAdapter.getNewIndicatorForTab(1);
/* 739 */               Resources res = getResources();
/* 740 */               if (res != null && indicator != null) {
/* 741 */                   Drawable drawable = indicator.getDrawable();
/* 742 */                   CenteredTextDrawable textDrawable = null;
/* 743 */                   if (drawable instanceof CenteredTextDrawable) {
/* 744 */                       textDrawable = (CenteredTextDrawable) drawable;
                            }
/* 746 */                   if (textDrawable == null) {
/* 747 */                       textDrawable = makeAcitivytNewIndicator(res);
/* 748 */                       indicator.setImageDrawable(textDrawable);
                            }
/* 750 */                   int minus = 0;
/* 751 */                   if (this.mActivityCount > 9) {
/* 752 */                       minus = 0 + 1;
                            }
/* 754 */                   if (this.mActivityCount > 99) {
/* 755 */                       minus++;
                            }
/* 757 */                   int paddingVertical = (int) (6.0f * res.getDisplayMetrics().density);
/* 758 */                   int paddingSides = (int) ((6 - (minus * 2)) * res.getDisplayMetrics().density);
/* 759 */                   textDrawable.setTextPadding(new Rect(paddingSides, paddingVertical, paddingSides, paddingVertical));
/* 760 */                   textDrawable.setText(this.mActivityCount > 99 ? "99+" : String.valueOf(this.mActivityCount));
                        }
                    }
                }

/* 766 */       private CenteredTextDrawable makeAcitivytNewIndicator(Resources res) {
/* 767 */           float density = res.getDisplayMetrics().density;
/* 768 */           GradientDrawable drawable1 = new GradientDrawable();
/* 769 */           drawable1.setStroke((int) (2.0f * density), -14408668);
/* 770 */           drawable1.setShape(0);
/* 771 */           drawable1.setCornerRadius(4.0f * density);
/* 772 */           drawable1.setColor(res.getColor(C0257R.color.vine_green));
/* 773 */           drawable1.setSize((int) (19.0f * density), (int) (20.0f * density));
/* 774 */           CenteredTextDrawable textDrawable = new CenteredTextDrawable(drawable1);
/* 775 */           textDrawable.setTextColor(-1);
/* 776 */           textDrawable.setTypeFace(Typefaces.get(this).boldContentBold);
/* 777 */           textDrawable.setTextSize(TypedValue.applyDimension(2, 12.0f, res.getDisplayMetrics()));
/* 779 */           return textDrawable;
                }

                @Override // co.vine.android.BaseActionBarActivity, android.app.Activity
/* 783 */       public boolean onOptionsItemSelected(MenuItem item) {
/* 784 */           int id = item.getItemId();
/* 785 */           if (id == C0257R.id.find_friends) {
/* 786 */               FlurryUtils.trackVisitFindFriends("Menu");
/* 787 */               startActivity(new Intent(this, (Class<?>) FindFriendsActivity.class));
                        return true;
                    }
/* 789 */           if (id == C0257R.id.settings) {
/* 790 */               FlurryUtils.trackVisitSettings("Menu");
/* 791 */               startActivityForResult(new Intent(this, (Class<?>) SettingsActivity.class), 1);
                        return true;
                    }
/* 794 */           if (id == C0257R.id.search) {
/* 795 */               FlurryUtils.trackVisitSearch("Menu");
/* 796 */               startActivity(new Intent(this, (Class<?>) SearchActivity.class));
                        return true;
                    }
/* 798 */           if (id == C0257R.id.inbox) {
/* 799 */               FlurryUtils.trackVisitInbox("Menu");
/* 800 */               getSlidingMenu().toggle(true);
                        return true;
                    }
/* 802 */           if (id == C0257R.id.gingerbread_notif_button && BuildUtil.isExplore()) {
/* 804 */               Intent intent = ViewOverlayActivity.getIntent(this, C0257R.layout.main_gb_notif, C0257R.id.gingerbread_notif_button, 0);
/* 806 */               startActivityForResult(intent, 1);
                        return true;
                    }
/* 810 */           return super.onOptionsItemSelected(item);
                }

                @Override // android.view.View.OnLongClickListener
/* 814 */       public boolean onLongClick(View view) {
/* 815 */           int toastResId = -1;
                    switch (((Integer) view.getTag()).intValue()) {
                        case 1:
/* 818 */                   toastResId = C0257R.string.tab_title_timeline;
                            break;
                        case 2:
/* 822 */                   toastResId = C0257R.string.tab_title_activity;
                            break;
                        case 3:
/* 826 */                   toastResId = C0257R.string.tab_title_explore;
                            break;
                        case 4:
/* 830 */                   toastResId = C0257R.string.tab_title_profile;
                            break;
                    }
/* 838 */           if (toastResId > 0) {
/* 839 */               int xOffset = view.getLeft();
/* 840 */               int yOffset = view.getBottom() + view.getMeasuredHeight();
/* 841 */               Toast toast = Toast.makeText(this, toastResId, 0);
/* 842 */               toast.setGravity(51, xOffset, yOffset);
/* 843 */               toast.show();
                    }
/* 847 */           if (view.getClass().equals(TabIndicator.class)) {
/* 848 */               view.performHapticFeedback(0);
/* 850 */               return true;
                    }
/* 850 */           return true;
                }

                @Override // co.vine.android.widget.tabs.IconTabHost.OnTabClickedListener
/* 854 */       public void onCurrentTabClicked() {
/* 855 */           scrollTop();
                }

                /* JADX WARN: Multi-variable type inference failed */
/* 858 */       public void scrollTop() {
                    TopScrollable list;
/* 859 */           Fragment currentFragment = this.mTabsAdapter.getCurrentFragment();
/* 860 */           resetNav();
/* 861 */           if (currentFragment instanceof BaseArrayListFragment) {
/* 862 */               View view = currentFragment.getView();
/* 863 */               if (view != null && (list = (TopScrollable) view.findViewById(android.R.id.list)) != null && !list.scrollTop()) {
/* 870 */                   ((BaseArrayListFragment) currentFragment).invokeScrollFirstItem();
/* 877 */                   return;
                        }
/* 877 */               return;
                    }
/* 873 */           if (currentFragment instanceof TopScrollable) {
/* 874 */               ((TopScrollable) currentFragment).scrollTop();
                    }
                }

/* 879 */       private void changeTabColor(int lastItem) {
/* 880 */           if (lastItem >= 0) {
/* 881 */               ((TabIndicator) this.mTabHost.getTabWidget().getChildTabViewAt(lastItem)).setColor(this.mDisabledTabColor);
                    }
/* 883 */           int newItem = this.mTabHost.getCurrentTab();
/* 884 */           if (newItem != 3) {
/* 885 */               ((TabIndicator) this.mTabHost.getTabWidget().getChildTabViewAt(newItem)).setColor(this.mEnabledTabColor);
                    }
                }

/* 889 */       public void setProfileColor(int color) {
/* 890 */           int current = this.mTabHost.getCurrentTab();
/* 891 */           if (current == 3) {
/* 892 */               ((TabIndicator) this.mTabHost.getTabWidget().getChildTabViewAt(current)).setColor(color);
                    }
                }

/* 894 */       class HomeTabSessionListener extends AppSessionListener {
/* 895 */           HomeTabSessionListener() {
                    }

                    @Override // co.vine.android.client.AppSessionListener
/* 899 */           public void onVerifyEmailComplete(String reqId, int statusCode, String reasonPhrase) {
/* 900 */               if (statusCode == 200) {
/* 901 */                   Intent intent = new Intent(HomeTabActivity.this, (Class<?>) SettingsActivity.class);
/* 902 */                   intent.setAction(HomeTabActivity.ACTION_VERIFICATION_COMPLETE);
/* 903 */                   HomeTabActivity.this.startActivityForResult(intent, 1);
                        } else {
/* 905 */                   Context applicationContext = HomeTabActivity.this.getApplicationContext();
                            if (reasonPhrase == null) {
                                reasonPhrase = HomeTabActivity.this.getString(C0257R.string.generic_error);
                            }
                            Util.showCenteredToast(applicationContext, reasonPhrase);
                        }
/* 908 */               HomeTabActivity.this.dismissDialog();
                    }

                    @Override // co.vine.android.client.AppSessionListener
/* 913 */           public void onGetPendingNotificationCountComplete(String reqId, int statusCode, String reasonPhrase, int count) {
/* 914 */               if (statusCode == 200) {
/* 915 */                   HomeTabActivity.access$102(HomeTabActivity.this, count);
/* 916 */                   HomeTabActivity.this.invalidateActivityBadge();
                        }
                    }

                    @Override // co.vine.android.client.AppSessionListener
/* 924 */           public void onGetActivityCountComplete(String reqId, int statusCode, String reasonPhrase, int activityCount, int followRequestCount, int messagesCount, UrlCachePolicy policyUsed) {
/* 925 */               if (statusCode == 200) {
/* 926 */                   SharedPreferences pref = Util.getDefaultSharedPrefs(HomeTabActivity.this);
/* 927 */                   int mostRecent = pref.getInt(Settings.PREF_MOST_RECENT_UNREAD_MESSAGES_COUNT, 0);
/* 928 */                   boolean dismissed = pref.getBoolean(Settings.PREF_UNREAD_MESSAGES_DISMISSED, false);
/* 930 */                   if (dismissed) {
/* 932 */                       if (mostRecent != messagesCount) {
/* 933 */                           pref.edit().putBoolean(Settings.PREF_UNREAD_MESSAGES_DISMISSED, false).apply();
/* 934 */                           HomeTabActivity.access$002(HomeTabActivity.this, messagesCount);
/* 935 */                           HomeTabActivity.access$200(HomeTabActivity.this);
/* 943 */                           return;
                                }
/* 943 */                       return;
                            }
/* 939 */                   HomeTabActivity.access$002(HomeTabActivity.this, messagesCount);
/* 940 */                   HomeTabActivity.access$200(HomeTabActivity.this);
                        }
                    }

                    @Override // co.vine.android.client.AppSessionListener
/* 946 */           public void onReceiveRTCMessage(ArrayList<VineRTCConversation> data) {
                        for (VineRTCConversation conversation : data) {
                            for (VineRTCParticipant participant : conversation.participants) {
/* 949 */                       long lastMessageId = participant.lastMessageId;
                                if (lastMessageId > 0) {
/* 951 */                           Long participantLast = (Long) HomeTabActivity.access$600(HomeTabActivity.this).get(participant.userId);
/* 952 */                           if (participantLast != null && lastMessageId > participantLast.longValue()) {
/* 953 */                               HomeTabActivity.this.mAppController.fetchConversation(1, false, conversation.conversationId, 0L, true);
/* 955 */                               if (!HomeTabActivity.this.getSlidingMenu().isMenuShowing()) {
/* 956 */                                   HomeTabActivity.this.mAppController.fetchActivityCounts();
                                        }
                                    } else {
/* 959 */                               HomeTabActivity.access$600(HomeTabActivity.this).put(participant.userId, Long.valueOf(lastMessageId));
                                    }
                                }
                            }
                        }
                    }

                    @Override // co.vine.android.client.AppSessionListener
/* 967 */           public void onAbortAllRequestsComplete() {
/* 968 */               super.onAbortAllRequestsComplete();
/* 969 */               HomeTabActivity.this.mAppController.fetchActivityCounts();
                    }

                    @Override // co.vine.android.client.AppSessionListener
/* 977 */           public void onGetTimeLineComplete(String reqId, int statusCode, String reasonPhrase, int type, int count, boolean memory, ArrayList<VinePost> posts, String tag, int pageType, int next, int previous, long anchor, boolean userInitiated, int size, String title, UrlCachePolicy cachePolicy, Bundle bundle) {
/* 978 */               if (!HomeTabActivity.access$700(HomeTabActivity.this) && !OnboardHelper.hasShownFavoriteUserOverlay(HomeTabActivity.this) && type == 1) {
/* 980 */                   HomeTabActivity.this.mAppController.fetchOnboardingSuggestedFavoriteUsers();
                        }
                    }

                    @Override // co.vine.android.client.AppSessionListener
/* 986 */           public void onFetchOnboardingSuggestedFavoritesComplete(String reqId, int statusCode, String reasonPhrase, ArrayList<VineUser> users) {
/* 987 */               if (users != null && !users.isEmpty()) {
/* 988 */                   FavoritePeopleOnboardActivity.start(HomeTabActivity.this, users);
/* 989 */                   OnboardHelper.setFavoriteUserOverlayShown(HomeTabActivity.this);
                        }
                    }
                }

/* 994 */       private void processStartIntent(Intent intent) {
/* 995 */           Uri data = intent.getData();
/* 996 */           String action = intent.getAction();
/* 997 */           VineSingleNotification notification = (VineSingleNotification) intent.getParcelableExtra("notification");
/* 999 */           if ("android.intent.action.VIEW".equals(action) && data != null) {
/* 1001 */              ArrayList<String> pathSegments = new ArrayList<>(data.getPathSegments());
/* 1002 */              String scheme = data.getScheme();
/* 1003 */              String host = data.getHost();
/* 1005 */              if (host != null) {
/* 1006 */                  if (host.contains(VineAPI.HOST_VINE) && !pathSegments.isEmpty()) {
/* 1007 */                      String pathPrefix = pathSegments.get(0);
/* 1008 */                      if (PREFIX_SHARED_VIDEO.equals(pathPrefix) && pathSegments.size() > 1) {
/* 1010 */                          String shareId = trimSegment(pathSegments.get(1));
/* 1011 */                          SingleActivity.start(this, shareId);
                                } else if (PREFIX_USER_ID.equals(pathPrefix)) {
                                    try {
/* 1015 */                              String userId = trimSegment(data.getLastPathSegment());
/* 1016 */                              ProfileActivity.start(this, Long.parseLong(userId), "Deep Link: /u/<userId>");
                                    } catch (NumberFormatException e) {
/* 1018 */                              Util.showCenteredToast(this, C0257R.string.invalid_link);
/* 1019 */                              finish();
                                    }
                                } else if (PREFIX_CONFIRM_EMAIL.equals(pathPrefix)) {
/* 1023 */                          this.mAppController.verifyEmail(this.mAppController.getActiveSession(), data.getLastPathSegment());
/* 1024 */                          this.mProgressDialog = new ProgressDialog(this);
/* 1025 */                          this.mProgressDialog.setMessage(getString(C0257R.string.login_validating));
                                    try {
/* 1027 */                              this.mProgressDialog.show();
                                    } catch (Exception e2) {
/* 1029 */                              SLog.m459e("Failed to show pd.", (Throwable) e2);
                                    }
                                } else {
/* 1033 */                          ProfileActivity.start(this, pathSegments, "Deep Link: Vanity URL");
                                }
                            } else if (SCHEME_VINE.equals(scheme)) {
/* 1036 */                      LinkDispatcher.dispatch(data, this);
                            }
                        }
                    } else if (StartActivity.ACTION_ACTIVITY_NOTIFICATION_PRESSED.equals(action) && notification != null) {
/* 1041 */              if (notification.notificationTypeId == 33 && notification.postId > 0) {
/* 1043 */                  Intent singleIntent = SingleActivity.getIntent(this, notification.postId);
/* 1044 */                  singleIntent.putExtra(SingleFragment.ARG_IS_FAVORITE, true);
/* 1045 */                  startActivity(singleIntent);
                        } else {
/* 1048 */                  this.mShowActivityFeed = true;
                        }
                    } else if (StartActivity.ACTION_ONBOARD_NOTIFICATION_PRESSED.equals(action)) {
/* 1051 */              if (notification != null && GCMNotificationService.ONBOARD_VALUE_FAVORITES.equals(notification.onboard) && OnboardHelper.hasShownFavoriteUserOverlay(this)) {
/* 1053 */                  FavoritePeopleActivity.start(this, 0);
                        }
                    } else if (StartActivity.ACTION_MESSAGE_NOTIFICATION_PRESSED.equals(action)) {
/* 1058 */              this.mShouldShowMenu = true;
                    } else if (StartActivity.ACTION_VIEW_UPLOAD_LIST.equals(action)) {
/* 1061 */              AppImpl.getInstance().startUploadsListActivity(this);
                    }
/* 1064 */          if (notification != null) {
/* 1065 */              FlurryUtils.trackNotificationClicked(notification.toString());
                    }
                }
            }
