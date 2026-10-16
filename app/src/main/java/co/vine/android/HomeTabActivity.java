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
            import co.vine.android.service.ActivityPollJobService;
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
            
            
    public class HomeTabActivity extends BaseTimelineActivity implements TabHost.OnTabChangeListener, IconTabHost.OnTabClickedListener, View.OnLongClickListener {
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

        static  int access$002(HomeTabActivity x0, int x1) {
            x0.mMessageCount = x1;
                    return x1;
                }

        static  int access$102(HomeTabActivity x0, int x1) {
            x0.mActivityCount = x1;
                    return x1;
                }

        static  void access$200(HomeTabActivity x0) {
            x0.invalidateMessagesBadge();
                }

        static  TabsAdapter access$300(HomeTabActivity x0) {
            return x0.mTabsAdapter;
                }

        static  ViewPager access$400(HomeTabActivity x0) {
            return x0.mViewPager;
                }

        static  Thread access$502(HomeTabActivity x0, Thread x1) {
            x0.mStartUploadServiceThread = x1;
                    return x1;
                }

        static  LongSparseArray access$600(HomeTabActivity x0) {
            return x0.mLastMessageMap;
                }

        static  boolean access$700(HomeTabActivity x0) {
            return x0.mIsNewUser;
                }

                static {
           ACTIVITY_COUNT_FILTER.addAction(VineService.BC_ACTIVITY_COUNTS);
                }

                
       class C02161 extends BroadcastReceiver {
           C02161() {
                    }

                    @Override 
           public void onReceive(Context context, Intent intent) {
               HomeTabActivity.access$002(HomeTabActivity.this, intent.getIntExtra(VineService.EXTRA_MESSAGES_COUNT, 0));
               HomeTabActivity.access$102(HomeTabActivity.this, intent.getIntExtra(VineService.EXTRA_NOTIFICATIONS_COUNT, 0));
               HomeTabActivity.access$200(HomeTabActivity.this);
               HomeTabActivity.this.invalidateActivityBadge();
                    }
                }

                
       class C02172 extends BroadcastReceiver {
           C02172() {
                    }

                    @Override 
           public void onReceive(Context context, Intent intent) {
               TabInfo tab = HomeTabActivity.access$300(HomeTabActivity.this).getTab(2);
               if (tab != null) {
                   Fragment fragment = tab.fragment();
                   if (fragment instanceof ExploreFragment) {
                       ((ExploreFragment) fragment).reloadWebView(false);
                            }
                        }
                    }
                }

                @Override 
       protected boolean isAutoCaptureIconEnabled() {
           return false;
                }

                @Override 
       public boolean isConversationSideMenuEnabled() {
           return true;
                }

                @Override 
       public void onMenuClose() {
           if (!MuteUtil.isMuted(this)) {
               sendBroadcast(new Intent(MuteUtil.ACTION_CHANGED_TO_UNMUTE), CrossConstants.BROADCAST_PERMISSION);
                    }
                }

                @Override 
       public void onMenuOpened() {
           super.onMenuOpened();
           invalidateMessagesBadge();
                }

                @Override 
       public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState, R.layout.main, true);
            ActivityPollJobService.schedule(this);
            UpdateManager.maybeShowChangelogPopup(this);
            if (BuildUtil.isExplore()) {
               setupTabs();
                    }
           if (SLog.sLogsOn) {
               sendBroadcast(new Intent("co.vine.android.debug_receiver"));
                    }
           SLog.m465i("HomeTabActivity pid: {}", Integer.valueOf(Process.myPid()));
           setRequestedOrientation(1);
           setupActionBar((Boolean) false, (Boolean) null, (String) null, (Boolean) null, (Boolean) true);
           hideOuterDuplicateActionBar(getWindow().getDecorView());
           positionHomeLogo();
           this.mAppSessionListener = new HomeTabSessionListener();
           this.mLastMessageMap = new LongSparseArray<>();
           Intent intent = getIntent();
           if (intent.getExtras() != null) {
               this.mIsNewUser = intent.getBooleanExtra(StartActivity.EXTRA_IS_NEW_USER, false);
        }

           if (this.mAppController == null) {
               this.mAppController = AppController.getInstance(this);
                    }
           AppController appController = this.mAppController;
           if (appController.isLoggedIn()) {
               AccountManager am = AccountManager.get(this);
               Account acc = VineAccountHelper.getActiveAccountReadOnly(this);
               VineAccountHelper.migratePassword(am, acc, this);
               SharedPreferences prefs = Util.getDefaultSharedPrefs(this);
               IconTabHost tabHost = this.mTabHost;
               this.mViewPager = (ViewPager) findViewById(R.id.pager);
               this.mViewPager.setPageMargin(getResources().getDimensionPixelSize(R.dimen.home_pager_margin));
               this.mViewPager.setPageMarginDrawable(R.color.bg_grouped_list);
               this.mViewPager.setOffscreenPageLimit(3);
               this.mTabsAdapter = new HomeTabTabsAdapter(tabHost);
               LayoutInflater inflater = getLayoutInflater();
               tabHost.setOnTabChangedListener(this);
               tabHost.setOnTabClickedListener(this);
               processStartIntent(intent);
               Resources res = getResources();
               this.mDisabledTabColor = res.getColor(R.color.solid_dark_gray);
               this.mEnabledTabColor = res.getColor(R.color.vine_green);
               Bundle timelineBundle = new Bundle();
               timelineBundle.putInt("empty_desc", R.string.failed_to_load_posts);
               this.mTabsAdapter.addTab(newTabSpec(inflater, R.layout.activity_tab_indicator, R.drawable.ic_home, "home"), HomeTimelineFragment.class, timelineBundle);
               restoreActivityState();
               Bundle activityBundle = new Bundle();
               activityBundle.putBoolean("take_focus", this.mShowActivityFeed);
               activityBundle.putInt("empty_desc", R.string.activity_empty);
               this.mTabsAdapter.addTab(newTabSpec(inflater, R.layout.activity_tab_indicator, R.drawable.ic_activity, TAG_ACTIVITY), ActivityFragment.class, activityBundle);
               Bundle exploreBundle = new Bundle();
               if (TextUtils.equals(this.mRecentTab, TAG_EXPLORE)) {
                   exploreBundle.putBoolean("take_focus", true);
                        }
               this.mTabsAdapter.addTab(newTabSpec(inflater, R.layout.home_tab_indicator, R.drawable.ic_explore, TAG_EXPLORE), ExploreFragment.class, exploreBundle);
               Bundle profileBundle = new Bundle();
               if (TextUtils.equals(this.mRecentTab, "profile")) {
                   profileBundle.putBoolean("take_focus", true);
                        }
               profileBundle.putLong("user_id", appController.getActiveId());
               profileBundle.putBoolean("refresh", true);
               this.mTabsAdapter.addTab(newTabSpec(inflater, R.layout.home_tab_indicator, R.drawable.ic_profile, "profile"), ProfileFragment.class, profileBundle);
               Session session = this.mAppController.getActiveSession();
               this.mAppController.determineCleanup(session);
               this.mActivityCount = prefs.getInt(Settings.PREF_UNREAD_NOTIFICATION_COUNT, 0);
               DelayHandler handler = new DelayHandler(this);
               if (handler.hasMessages(1)) {
                   handler.removeMessages(1);
                        }
               if (handler.hasMessages(2)) {
                   handler.removeMessages(2);
                        }
               handler.sendMessageDelayed(handler.obtainMessage(1, null), CommonUtil.MINUTE_MILLIS);
               handler.sendMessageDelayed(handler.obtainMessage(2, null), CommonUtil.MINUTE_MILLIS);
               handler.sendMessageDelayed(handler.obtainMessage(3, null), CommonUtil.MINUTE_MILLIS);
               AppImpl.getInstance().startCameraService(this);
               VineAccountHelper.syncNormalVideoPlayable(this);
               PrefetchManager.getInstance(this).delayNextPrefetch();
               UpdateManager.checkOnStartup(this);
                        try {
                   int errorCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
                   if (errorCode == 0) {
                       startService(GCMRegistrationService.getRegisterIntent(this, this.mAppController.getActiveId()));
                            }
                        } catch (Throwable e) {
                   CrashUtil.logException(e, "Failed to validate Google Player Service status.", new Object[0]);
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

       private TabHost.TabSpec newTabSpec(LayoutInflater inflater, int layout, int icon, String tag) {
           TabIndicator indicator = TabIndicator.newIconIndicator(inflater, layout, this.mTabHost, icon, 0);
           indicator.setColor(this.mDisabledTabColor);
           return this.mTabHost.newTabSpec(tag).setIndicator(indicator);
                }

       private String trimSegment(String segment) {
           return segment.replaceAll("\\s+", "").replace("\\", "");
                }

                @Override 
       protected void onPause() {
           super.onPause();
           if (!this.mExploreRefreshReciverIsRegistered) {
               this.mExploreRefreshReciverIsRegistered = true;
               registerReceiver(this.mExploreRefreshReciver, AppController.ACTION_UPDATED_FILTER, CrossConstants.BROADCAST_PERMISSION, null);
                    }
                    try {
               unregisterReceiver(this.mActivityCountReceiver);
                    } catch (IllegalArgumentException e) {
                    }
                }

                @Override 
       protected void onStart() {
           super.onStart();
           ClientFlagsHelper.fetchClientFlags(this, true, true);
           if (ClientFlagsHelper.zeroRatingIsEnabled(this)) {
               FlurryUtils.trackZeroRatedSessionStarted(true);
                    }
                }

                @Override 
       protected void onStop() {
           super.onStop();
                }

                @Override 
       public void onScroll(int delta) {
           if (this.mScrollAwayNavEnabled) {
               ScrollAwayTabWidget tabWidget = (ScrollAwayTabWidget) this.mTabHost.getTabWidget();
               tabWidget.onScroll(delta);
               Fragment fragment = this.mTabsAdapter.getCurrentFragment();
               if (fragment instanceof BaseArrayListFragment) {
                   ((BaseArrayListFragment) fragment).setNavBottom(tabWidget.getNavBottom());
                        }
                    }
                }

       public void setScrollAwayNavEnabled(boolean enabled) {
           this.mScrollAwayNavEnabled = enabled;
                }

       public boolean shouldChangeActionBarColor() {
                    return this.mViewPager != null && this.mViewPager.getCurrentItem() == 3;
                }

       private class HomeTabTabsAdapter extends TabsAdapter {
           public HomeTabTabsAdapter(TabHost tabHost) {
               super(HomeTabActivity.this, tabHost, HomeTabActivity.access$400(HomeTabActivity.this), null);
                    }

                    @Override 
           public void onPageSelected(int position) {
               super.onPageSelected(position);
               HomeTabActivity.this.resetNav();
               if (position == 1) {
                   Util.getDefaultSharedPrefs(HomeTabActivity.this.getApplicationContext()).edit().putInt(Settings.PREF_UNREAD_NOTIFICATION_COUNT, 0).commit();
                   HomeTabActivity.access$102(HomeTabActivity.this, 0);
                   HomeTabActivity.this.invalidateActivityBadge();
                        }
                    }
                }

                @Override 
       protected void onNewIntent(Intent intent) {
           super.onNewIntent(intent);
           processStartIntent(intent);
                }

       public void showPage(int position, Fragment fragment) {
           if (this.mTabsAdapter != null && this.mViewPager != null) {
               this.mViewPager.setCurrentItem(position);
               if (fragment instanceof BaseArrayListFragment) {
                   ((BaseArrayListFragment) fragment).onMoveTo(1);
                        }
               TabInfo tab = this.mTabsAdapter.getTab(this.mViewPager.getCurrentItem());
               if (tab != null) {
                   Fragment current = tab.fragment();
                   if (current instanceof BaseArrayListFragment) {
                       ((BaseArrayListFragment) current).onMoveAway(-1);
                            }
                   resetNav();
                   logTabChange(fragment.getTag());
                        }
                    }
                }

       public void resetNav() {
                    ScrollAwayTabWidget widget;
           if (this.mTabHost != null && (widget = (ScrollAwayTabWidget) this.mTabHost.getTabWidget()) != null) {
               widget.resetScroll();
               widget.invalidate();
               int navBottom = widget.getNavBottom();
               Fragment fragment = this.mTabsAdapter.getCurrentFragment();
               if (fragment != null && (fragment instanceof BaseArrayListFragment)) {
                   ((BaseArrayListFragment) fragment).setNavBottom(navBottom);
                        }
               this.mScrollAwayNavEnabled = true;
                    }
                }

       public void onNotifyClick() {
           getSlidingMenu().toggle(true);
                }

       public void markMessagesAsDismissed() {
           Util.getDefaultSharedPrefs(this).edit().putBoolean(Settings.PREF_UNREAD_MESSAGES_DISMISSED, true);
                }

       public static final class DelayHandler extends Handler {
                    public static final int DETERMINE_SERVER_STATUS = 3;
                    public static final int FETCH_FOLLOWERS = 2;
                    public static final int FETCH_ME = 1;
                    private final WeakReference<Context> mContextRef;

           public DelayHandler(Context context) {
               this.mContextRef = new WeakReference<>(context.getApplicationContext());
                    }

                    @Override 
           public void handleMessage(Message msg) {
               Context context = this.mContextRef.get();
               if (context != null) {
                   AppController appController = AppController.getInstance(context);
                            switch (msg.what) {
                                case 1:
                           appController.fetchUsersMe(appController.getActiveId(), UrlCachePolicy.NETWORK_THEN_CACHE);
                                    break;
                                case 2:
                           appController.fetchFollowing(appController.getActiveId(), 1, 0L);
                                    break;
                                case 3:
                           appController.determineServerStatus();
                                    break;
                            }
                        }
                    }
                }

       public void markActivitiesRead() {
           Util.getDefaultSharedPrefs(this).edit().putInt(Settings.PREF_UNREAD_NOTIFICATION_COUNT, 0).commit();
           this.mActivityCount = 0;
           invalidateActivityBadge();
                }

                @Override 
       public void onTabChanged(String tag) {
           if (SLog.sLogsOn) {
                    try {
                   SLog.m446d(Util.printCursorWindowStats());
                    } catch (Throwable ignored) {
                    }
                    }
           changeTabColor(this.mLastItem);
           int tab = this.mTabHost.getCurrentTab();
           this.mViewPager.setCurrentItem(tab);
           this.mLastItem = tab;
           this.mRecentTab = tag;
           if (TAG_ACTIVITY.equals(tag)) {
               markActivitiesRead();
                    }
           if ("home".equals(tag)) {
               setHomeNewCount(0);
                    }
                    switch (tab) {
                        case 0:
                   getSlidingMenu().setTouchModeAbove(1);
                            break;
                        default:
                   getSlidingMenu().setTouchModeAbove(0);
                            break;
                    }
           logTabChange(tag);
                }

       private void logTabChange(String tag) {
           CrashUtil.set("Current Tab", tag + " - " + System.currentTimeMillis());
           if (tag != null && !tag.contains("switcher")) {
               FlurryUtils.trackTabChange(tag);
                    }
                }

       public void showMenuIfNeeded() {
           if (this.mShouldShowMenu) {
               SLog.m446d("Menu requested.");
               this.mShouldShowMenu = false;
               getSlidingMenu().showMenu(false);
                    }
                }

                @Override 
       protected void onDestroy() {
           super.onDestroy();
           if (this.mExploreRefreshReciverIsRegistered) {
               this.mExploreRefreshReciverIsRegistered = false;
               unregisterReceiver(this.mExploreRefreshReciver);
                    }
                }

                
       class RunnableC02183 implements Runnable {
           RunnableC02183() {
                    }

                    @Override 
           public void run() {
               Intent i = AppImpl.getInstance().getNotifyFailedIntent(HomeTabActivity.this);
               if (i != null) {
                   HomeTabActivity.this.startService(i);
                        }
               HomeTabActivity.access$502(HomeTabActivity.this, null);
                    }
                }

       private synchronized Thread getUploadServiceThread() {
                    Thread thread;
                    try {
                        if (this.mStartUploadServiceThread == null) {
                   this.mStartUploadServiceThread = new Thread(new RunnableC02183());
                   thread = this.mStartUploadServiceThread;
                        } else {
                            thread = null;
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                    return thread;
                }

                @Override 
       protected void onResume() {
           super.onResume();
           if (this.mTabsAdapter != null && this.mTabHost != null) {
               this.mTabsAdapter.previousTab = this.mTabHost.getCurrentTab();
                    }
           Thread startUploadServiceThread = getUploadServiceThread();
           if (startUploadServiceThread != null) {
               startUploadServiceThread.start();
                    }
           TabWidget tabWidget = this.mTabHost.getTabWidget();
           int tabCount = tabWidget == null ? 0 : tabWidget.getTabCount();
                    for (int j = 0; j < tabCount; j++) {
               View tabView = tabWidget.getChildAt(j);
               if (tabView != null) {
                   tabView.setTag(Integer.valueOf(j + 1));
                   tabView.setOnLongClickListener(this);
                        }
                    }
           registerReceiver(this.mActivityCountReceiver, ACTIVITY_COUNT_FILTER, CrossConstants.BROADCAST_PERMISSION, null);
           this.mAppController.fetchActivityCounts();
           invalidateActivityBadge();
           invalidateMessagesBadge();
                }

                @Override 
       protected void onActivityResult(int requestCode, int resultCode, Intent data) {
           if (this.mTabsAdapter != null) {
               ArrayList<TabInfo> tabs = this.mTabsAdapter.getTabs();
               if (requestCode == 0) {
                   SharedPreferences sp = Util.getDefaultSharedPrefs(this);
                   sp.edit().putBoolean(HomeTimelineFragment.PREF_INVITE_BANNER_CLICKED, true).apply();
                   int followCount = sp.getInt(Settings.PREF_FOLLOWING_COUNT, 0);
                   Fragment homeTimeline = tabs.get(this.mTabHost.getCurrentTab()).fragment();
                   if (followCount > 5 && (homeTimeline instanceof HomeTimelineFragment)) {
                       ((HomeTimelineFragment) homeTimeline).removeBanner();
                       return;
                            }
                   return;
                        }
               if (requestCode == 1 && resultCode == 1) {
                            for (TabInfo tab : tabs) {
                       Fragment fragment = tab.fragment();
                       if (fragment instanceof BaseTimelineFragment) {
                           fragment.onActivityResult(requestCode, resultCode, data);
                                }
                            }
                   return;
                        }
               if (requestCode == 1 && resultCode == -1) {
                   if (BuildUtil.isExplore() && this.mMenu != null) {
                       this.mMenu.removeItem(R.id.capture);
                       return;
                            }
                   return;
                        }
               Fragment frag = tabs.get(this.mTabHost.getCurrentTab()).fragment();
               if (frag != null) {
                   frag.onActivityResult(requestCode, resultCode, data);
                        }
                    }
                }

                @Override 
       protected BaseTimelineFragment getCurrentTimeLineFragment() {
                    try {
               Fragment frag = this.mTabsAdapter.getCurrentFragment();
               if (frag instanceof BaseTimelineFragment) {
                   return (BaseTimelineFragment) frag;
                        }
                        return null;
                    } catch (Exception e) {
                        return null;
                    }
                }

                @Override 
       public void onSupportContentChanged() {
           super.onSupportContentChanged();
           if (!BuildUtil.isExplore()) {
               setupTabs();
                    }
                }

       private void setupTabs() {
           this.mTabHost = (IconTabHost) findViewById(android.R.id.tabhost);
           if (this.mTabHost == null) {
               throw new RuntimeException("Your content must have a TabHost whose id attribute is 'android.R.id.tabhost'");
                    }
           this.mTabHost.setup();
           this.mViewPager = (ViewPager) findViewById(R.id.pager);
                }

       public static void start(Activity activity, String tag) {
           Intent intent = new Intent(activity, (Class<?>) HomeTabActivity.class).addFlags(avformat.AVFMT_SEEK_TO_PTS);
           activity.startActivity(intent);
           activity.finish();
                }

                @Override 
       public boolean onCreateOptionsMenu(Menu menu) {
           getMenuInflater().inflate(R.menu.home, menu);
           SharedPreferences prefs = Util.getDefaultSharedPrefs(this);
           if (BuildUtil.isExplore() && prefs.getBoolean(Settings.PREF_GINGERBREAD_NOTIF_DISMISSED, false)) {
               menu.removeItem(R.id.capture);
                    }
           this.mInboxMenu = menu.findItem(R.id.inbox);
           invalidateMessagesBadge();
           this.mMenu = menu;
           return super.onCreateOptionsMenu(menu);
                }

       private void invalidateMessagesBadge() {
           Resources res = getResources();
           if (res != null && this.mInboxMenu != null) {
               Drawable currentDrawable = this.mInboxMenu.getIcon();
               CenteredTextDrawable inboxBadgeDrawable = null;
               if (currentDrawable instanceof CenteredTextDrawable) {
                   inboxBadgeDrawable = (CenteredTextDrawable) currentDrawable;
                        }
               if (inboxBadgeDrawable == null) {
                   inboxBadgeDrawable = new CenteredTextDrawable(res, R.drawable.vm_bubble_outline, R.drawable.vm_bubble_fill);
                   inboxBadgeDrawable.setColorFilter(1, new PorterDuffColorFilter(Util.getProfileColor(this) | (-16777216), PorterDuff.Mode.SRC_IN));
                   int padding = (int) (6.0f * res.getDisplayMetrics().density);
                   inboxBadgeDrawable.setTextPadding(new Rect(padding, padding, padding, padding));
                   inboxBadgeDrawable.setTextColor(-1);
                   inboxBadgeDrawable.setTypeFace(Typefaces.get(this).boldContentBold);
                   inboxBadgeDrawable.setTextSize(TypedValue.applyDimension(2, 12.0f, res.getDisplayMetrics()));
                        }
               if (this.mMessageCount == 0) {
                   this.mInboxMenu.setIcon(R.drawable.ic_action_inbox);
                        } else {
                   inboxBadgeDrawable.setText(this.mMessageCount > 99 ? "99+" : String.valueOf(this.mMessageCount));
                   this.mInboxMenu.setIcon(inboxBadgeDrawable);
                        }
                    }
                }

       public void setHomeNewCount(int newCount) {
           if (this.mTabsAdapter != null) {
               SLog.m465i("Set home badge count: {}", Integer.valueOf(newCount));
               this.mTabsAdapter.setNew(0, newCount > 0);
               ImageView indicator = this.mTabsAdapter.getNewIndicatorForTab(0);
               Resources res = getResources();
               if (res != null && indicator != null) {
                   Drawable drawable = indicator.getDrawable();
                   CenteredTextDrawable textDrawable = null;
                   if (drawable instanceof CenteredTextDrawable) {
                       textDrawable = (CenteredTextDrawable) drawable;
                            }
                   if (textDrawable == null) {
                       textDrawable = makeAcitivytNewIndicator(res);
                       indicator.setImageDrawable(textDrawable);
                            }
                   int padding = (int) (6.0f * res.getDisplayMetrics().density);
                   textDrawable.setTextPadding(new Rect(padding, padding, padding, padding));
                   textDrawable.setText(newCount < 10 ? String.valueOf(newCount) : "9+");
                        }
                    }
                }

       public void invalidateActivityBadge() {
           if (this.mTabsAdapter != null) {
               this.mTabsAdapter.setNew(1, this.mActivityCount > 0);
               ImageView indicator = this.mTabsAdapter.getNewIndicatorForTab(1);
               Resources res = getResources();
               if (res != null && indicator != null) {
                   Drawable drawable = indicator.getDrawable();
                   CenteredTextDrawable textDrawable = null;
                   if (drawable instanceof CenteredTextDrawable) {
                       textDrawable = (CenteredTextDrawable) drawable;
                            }
                   if (textDrawable == null) {
                       textDrawable = makeAcitivytNewIndicator(res);
                       indicator.setImageDrawable(textDrawable);
                            }
                   int minus = 0;
                   if (this.mActivityCount > 9) {
                       minus = 0 + 1;
                            }
                   if (this.mActivityCount > 99) {
                       minus++;
                            }
                   int paddingVertical = (int) (6.0f * res.getDisplayMetrics().density);
                   int paddingSides = (int) ((6 - (minus * 2)) * res.getDisplayMetrics().density);
                   textDrawable.setTextPadding(new Rect(paddingSides, paddingVertical, paddingSides, paddingVertical));
                   textDrawable.setText(this.mActivityCount > 99 ? "99+" : String.valueOf(this.mActivityCount));
                        }
                    }
                }

       private CenteredTextDrawable makeAcitivytNewIndicator(Resources res) {
           float density = res.getDisplayMetrics().density;
           GradientDrawable drawable1 = new GradientDrawable();
           drawable1.setStroke((int) (2.0f * density), -14408668);
           drawable1.setShape(0);
           drawable1.setCornerRadius(4.0f * density);
           drawable1.setColor(res.getColor(R.color.vine_green));
           drawable1.setSize((int) (19.0f * density), (int) (20.0f * density));
           CenteredTextDrawable textDrawable = new CenteredTextDrawable(drawable1);
           textDrawable.setTextColor(-1);
           textDrawable.setTypeFace(Typefaces.get(this).boldContentBold);
           textDrawable.setTextSize(TypedValue.applyDimension(2, 12.0f, res.getDisplayMetrics()));
           return textDrawable;
                }

                @Override 
       public boolean onOptionsItemSelected(MenuItem item) {
           int id = item.getItemId();
           if (id == R.id.find_friends) {
               FlurryUtils.trackVisitFindFriends("Menu");
               startActivity(new Intent(this, (Class<?>) FindFriendsActivity.class));
                        return true;
                    }
           if (id == R.id.settings) {
               FlurryUtils.trackVisitSettings("Menu");
               startActivityForResult(new Intent(this, (Class<?>) SettingsActivity.class), 1);
                        return true;
                    }
           if (id == R.id.search) {
               FlurryUtils.trackVisitSearch("Menu");
               startActivity(new Intent(this, (Class<?>) SearchActivity.class));
                        return true;
                    }
           if (id == R.id.inbox) {
               FlurryUtils.trackVisitInbox("Menu");
               getSlidingMenu().toggle(true);
                        return true;
                    }
           if (id == R.id.gingerbread_notif_button && BuildUtil.isExplore()) {
               Intent intent = ViewOverlayActivity.getIntent(this, R.layout.main_gb_notif, R.id.gingerbread_notif_button, 0);
               startActivityForResult(intent, 1);
                        return true;
                    }
           return super.onOptionsItemSelected(item);
                }

                @Override 
       public boolean onLongClick(View view) {
           int toastResId = -1;
                    switch (((Integer) view.getTag()).intValue()) {
                        case 1:
                   toastResId = R.string.tab_title_timeline;
                            break;
                        case 2:
                   toastResId = R.string.tab_title_activity;
                            break;
                        case 3:
                   toastResId = R.string.tab_title_explore;
                            break;
                        case 4:
                   toastResId = R.string.tab_title_profile;
                            break;
                    }
           if (toastResId > 0) {
               int xOffset = view.getLeft();
               int yOffset = view.getBottom() + view.getMeasuredHeight();
               Toast toast = Toast.makeText(this, toastResId, 0);
               toast.setGravity(51, xOffset, yOffset);
               toast.show();
                    }
           if (view.getClass().equals(TabIndicator.class)) {
               view.performHapticFeedback(0);
               return true;
                    }
           return true;
                }

                @Override 
       public void onCurrentTabClicked() {
           scrollTop();
                }

                
       public void scrollTop() {
                    TopScrollable list;
           Fragment currentFragment = this.mTabsAdapter.getCurrentFragment();
           resetNav();
           if (currentFragment instanceof BaseArrayListFragment) {
               View view = currentFragment.getView();
               if (view != null && (list = (TopScrollable) view.findViewById(android.R.id.list)) != null && !list.scrollTop()) {
                   ((BaseArrayListFragment) currentFragment).invokeScrollFirstItem();
                   return;
                        }
               return;
                    }
           if (currentFragment instanceof TopScrollable) {
               ((TopScrollable) currentFragment).scrollTop();
                    }
                }

       private void changeTabColor(int lastItem) {
           if (lastItem >= 0) {
               ((TabIndicator) this.mTabHost.getTabWidget().getChildTabViewAt(lastItem)).setColor(this.mDisabledTabColor);
                    }
           int newItem = this.mTabHost.getCurrentTab();
           if (newItem != 3) {
               ((TabIndicator) this.mTabHost.getTabWidget().getChildTabViewAt(newItem)).setColor(this.mEnabledTabColor);
                    }
                }

       public void setProfileColor(int color) {
           int current = this.mTabHost.getCurrentTab();
           if (current == 3) {
               ((TabIndicator) this.mTabHost.getTabWidget().getChildTabViewAt(current)).setColor(color);
                    }
                }

       class HomeTabSessionListener extends AppSessionListener {
           HomeTabSessionListener() {
                    }

                    @Override 
           public void onVerifyEmailComplete(String reqId, int statusCode, String reasonPhrase) {
               if (statusCode == 200) {
                   Intent intent = new Intent(HomeTabActivity.this, (Class<?>) SettingsActivity.class);
                   intent.setAction(HomeTabActivity.ACTION_VERIFICATION_COMPLETE);
                   HomeTabActivity.this.startActivityForResult(intent, 1);
                        } else {
                   Context applicationContext = HomeTabActivity.this.getApplicationContext();
                            if (reasonPhrase == null) {
                                reasonPhrase = HomeTabActivity.this.getString(R.string.generic_error);
                            }
                            Util.showCenteredToast(applicationContext, reasonPhrase);
                        }
               HomeTabActivity.this.dismissDialog();
                    }

                    @Override 
           public void onGetPendingNotificationCountComplete(String reqId, int statusCode, String reasonPhrase, int count) {
               if (statusCode == 200) {
                   HomeTabActivity.access$102(HomeTabActivity.this, count);
                   HomeTabActivity.this.invalidateActivityBadge();
                        }
                    }

                    @Override 
           public void onGetActivityCountComplete(String reqId, int statusCode, String reasonPhrase, int activityCount, int followRequestCount, int messagesCount, UrlCachePolicy policyUsed) {
               if (statusCode == 200) {
                   SharedPreferences pref = Util.getDefaultSharedPrefs(HomeTabActivity.this);
                   int mostRecent = pref.getInt(Settings.PREF_MOST_RECENT_UNREAD_MESSAGES_COUNT, 0);
                   boolean dismissed = pref.getBoolean(Settings.PREF_UNREAD_MESSAGES_DISMISSED, false);
                   if (dismissed) {
                       if (mostRecent != messagesCount) {
                           pref.edit().putBoolean(Settings.PREF_UNREAD_MESSAGES_DISMISSED, false).apply();
                           HomeTabActivity.access$002(HomeTabActivity.this, messagesCount);
                           HomeTabActivity.access$200(HomeTabActivity.this);
                           return;
                                }
                       return;
                            }
                   HomeTabActivity.access$002(HomeTabActivity.this, messagesCount);
                   HomeTabActivity.access$200(HomeTabActivity.this);
                        }
                    }

                    @Override 
           public void onReceiveRTCMessage(ArrayList<VineRTCConversation> data) {
                        for (VineRTCConversation conversation : data) {
                            for (VineRTCParticipant participant : conversation.participants) {
                       long lastMessageId = participant.lastMessageId;
                                if (lastMessageId > 0) {
                           Long participantLast = (Long) HomeTabActivity.access$600(HomeTabActivity.this).get(participant.userId);
                           if (participantLast != null && lastMessageId > participantLast.longValue()) {
                               HomeTabActivity.this.mAppController.fetchConversation(1, false, conversation.conversationId, 0L, true);
                               if (!HomeTabActivity.this.getSlidingMenu().isMenuShowing()) {
                                   HomeTabActivity.this.mAppController.fetchActivityCounts();
                                        }
                                    } else {
                               HomeTabActivity.access$600(HomeTabActivity.this).put(participant.userId, Long.valueOf(lastMessageId));
                                    }
                                }
                            }
                        }
                    }

                    @Override 
           public void onAbortAllRequestsComplete() {
               super.onAbortAllRequestsComplete();
               HomeTabActivity.this.mAppController.fetchActivityCounts();
                    }

                    @Override 
           public void onGetTimeLineComplete(String reqId, int statusCode, String reasonPhrase, int type, int count, boolean memory, ArrayList<VinePost> posts, String tag, int pageType, int next, int previous, long anchor, boolean userInitiated, int size, String title, UrlCachePolicy cachePolicy, Bundle bundle) {
               if (!HomeTabActivity.access$700(HomeTabActivity.this) && !OnboardHelper.hasShownFavoriteUserOverlay(HomeTabActivity.this) && type == 1) {
                   HomeTabActivity.this.mAppController.fetchOnboardingSuggestedFavoriteUsers();
                        }
                    }

                    @Override 
           public void onFetchOnboardingSuggestedFavoritesComplete(String reqId, int statusCode, String reasonPhrase, ArrayList<VineUser> users) {
               if (users != null && !users.isEmpty()) {
                   FavoritePeopleOnboardActivity.start(HomeTabActivity.this, users);
                   OnboardHelper.setFavoriteUserOverlayShown(HomeTabActivity.this);
                        }
                    }
                }

       private void processStartIntent(Intent intent) {
           Uri data = intent.getData();
           String action = intent.getAction();
           VineSingleNotification notification = (VineSingleNotification) intent.getParcelableExtra("notification");
           if ("android.intent.action.VIEW".equals(action) && data != null) {
              ArrayList<String> pathSegments = new ArrayList<>(data.getPathSegments());
              String scheme = data.getScheme();
              String host = data.getHost();
              if (host != null) {
                  if (host.contains(VineAPI.HOST_VINE) && !pathSegments.isEmpty()) {
                      String pathPrefix = pathSegments.get(0);
                      if (PREFIX_SHARED_VIDEO.equals(pathPrefix) && pathSegments.size() > 1) {
                          String shareId = trimSegment(pathSegments.get(1));
                          SingleActivity.start(this, shareId);
                                } else if (PREFIX_USER_ID.equals(pathPrefix)) {
                                    try {
                              String userId = trimSegment(data.getLastPathSegment());
                              ProfileActivity.start(this, Long.parseLong(userId), "Deep Link: /u/<userId>");
                                    } catch (NumberFormatException e) {
                              Util.showCenteredToast(this, R.string.invalid_link);
                              finish();
                                    }
                                } else if (PREFIX_CONFIRM_EMAIL.equals(pathPrefix)) {
                          this.mAppController.verifyEmail(this.mAppController.getActiveSession(), data.getLastPathSegment());
                          this.mProgressDialog = new ProgressDialog(this);
                          this.mProgressDialog.setMessage(getString(R.string.login_validating));
                                    try {
                              this.mProgressDialog.show();
                                    } catch (Exception e2) {
                              SLog.m459e("Failed to show pd.", (Throwable) e2);
                                    }
                                } else {
                          ProfileActivity.start(this, pathSegments, "Deep Link: Vanity URL");
                                }
                            } else if (SCHEME_VINE.equals(scheme)) {
                      LinkDispatcher.dispatch(data, this);
                            }
                        }
                    } else if (StartActivity.ACTION_ACTIVITY_NOTIFICATION_PRESSED.equals(action) && notification != null) {
              if (notification.notificationTypeId == 33 && notification.postId > 0) {
                  Intent singleIntent = SingleActivity.getIntent(this, notification.postId);
                  singleIntent.putExtra(SingleFragment.ARG_IS_FAVORITE, true);
                  startActivity(singleIntent);
                        } else {
                  this.mShowActivityFeed = true;
                        }
                    } else if (StartActivity.ACTION_ONBOARD_NOTIFICATION_PRESSED.equals(action)) {
              if (notification != null && GCMNotificationService.ONBOARD_VALUE_FAVORITES.equals(notification.onboard) && OnboardHelper.hasShownFavoriteUserOverlay(this)) {
                  FavoritePeopleActivity.start(this, 0);
                        }
                    } else if (StartActivity.ACTION_MESSAGE_NOTIFICATION_PRESSED.equals(action)) {
              this.mShouldShowMenu = true;
                    } else if (StartActivity.ACTION_VIEW_UPLOAD_LIST.equals(action)) {
              AppImpl.getInstance().startUploadsListActivity(this);
                    }
          if (notification != null) {
              FlurryUtils.trackNotificationClicked(notification.toString());
                    }
                }
            }
