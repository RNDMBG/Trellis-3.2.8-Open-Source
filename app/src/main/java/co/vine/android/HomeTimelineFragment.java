            package co.vine.android;

            import android.accounts.Account;
            import android.accounts.AccountManager;
            import android.content.DialogInterface;
            import android.content.Intent;
            import android.content.SharedPreferences;
            import android.os.Bundle;
            import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
            import android.view.View;
            import android.widget.AbsListView;
            import android.widget.FrameLayout;
            import android.widget.ListAdapter;
            import co.vine.android.api.VineClientFlags;
            import co.vine.android.api.VinePost;
            import co.vine.android.client.VineAccountHelper;
            import co.vine.android.network.UrlCachePolicy;
            import co.vine.android.recorder.BasicVineRecorder;
            import co.vine.android.util.BuildUtil;
            import co.vine.android.util.ClientFlagsHelper;
            import co.vine.android.util.CrashUtil;
            import co.vine.android.util.Util;
            import co.vine.android.util.analytics.FlurryUtils;
            import co.vine.android.widget.FeedAdapter;
            import co.vine.android.widgets.PromptDialogSupportFragment;
            import java.util.ArrayList;
            
            
    public class HomeTimelineFragment extends BaseTimelineFragment {
                private static final int DIALOG_ADD_WIDGET = 992;
                private static final String EVENT_SOURCE_TITLE = "Home Timeline";
                public static final String PREF_INVITE_BANNER_CLICKED = "inviteBannerWasClicked";
                public static final String PREF_INVITE_BANNER_VIEW_COUNT = "inviteBannerViewCount";
                public static final int REQUEST_FIND_FRIENDS = 0;
                public static final int REQUEST_SETTINGS = 1;
                public static final int SHOW_ADD_WIDGET_THRESHOLD = 5;
                private static boolean sIsNewUser = false;
                private FrameLayout mInviteBottomBanner;
                private ArrayList<VinePost> mNewPosts;
                private int mScrollState = 0;
                private final PromptDialogSupportFragment.OnDialogDoneListener mAddWidgetDialogDoneListener = new C02202();

        static  FrameLayout access$100(HomeTimelineFragment x0) {
            return x0.mInviteBottomBanner;
                }

                @Override 
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            this.mAppSessionListener = new HomeTimeLineSessionListener(this, null);
            setFlurryEventSource(EVENT_SOURCE_TITLE);
            setFocused(true);
                }

                @Override 
        public synchronized void onScrollStateChanged(AbsListView view, int scrollState) {
                    try {
                        super.onScrollStateChanged(view, scrollState);
                this.mScrollState = scrollState;
                if (this.mNewPosts != null) {
                    appendPosts(this.mNewPosts);
                    this.mNewPosts = null;
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }

        private void appendPosts(ArrayList<VinePost> posts) {
            int badgeCount = ((FeedAdapter) this.mFeedAdapter).appendPostsOnTop(posts);
            if (badgeCount < 0) {
                ((FeedAdapter) this.mFeedAdapter).replacePosts(posts);
                    } else if (badgeCount > 0) {
                FragmentActivity activity = getActivity();
                if (activity instanceof HomeTabActivity) {
                    ((HomeTabActivity) activity).setHomeNewCount(badgeCount);
                        }
                    }
                }

                @Override 
        public void onTopViewBinded() {
            FragmentActivity activity = getActivity();
            if (activity instanceof HomeTabActivity) {
                ((HomeTabActivity) activity).setHomeNewCount(0);
                    }
                }

                @Override 
        protected boolean trackNewCount() {
            return true;
                }

                @Override 
       protected void onPostUpdatesCameIn(ArrayList<VinePost> posts) {
           if (this.mScrollState != 0) {
               this.mNewPosts = posts;
                    } else {
               appendPosts(posts);
                    }
                }

                @Override 
       public void onActivityCreated(Bundle savedInstanceState) {
           super.onActivityCreated(savedInstanceState);
           this.mListView.setAdapter((ListAdapter) this.mFeedAdapter);
          
          
          
          
          if (!hasPendingRequest(3)) {
              fetchInitialRequest(UrlCachePolicy.NETWORK_THEN_CACHE);
          }
           FragmentActivity activity = getActivity();
           AccountManager am = AccountManager.get(activity);
           Account account = VineAccountHelper.getAccount(getActivity(), this.mAppController.getActiveSession().getLoginEmail());
           SharedPreferences sp = Util.getDefaultSharedPrefs(getActivity());
           int followCount = sp.getInt(Settings.PREF_FOLLOWING_COUNT, 0);
           if (!sp.getBoolean(PREF_INVITE_BANNER_CLICKED, false) || followCount <= 5) {
                        try {
                   this.mInviteBottomBanner = (FrameLayout) getView().findViewById(R.id.find_friends);
                   this.mInviteBottomBanner.setOnClickListener(new ViewOnClickListenerC02191());
                   this.mInviteBottomBanner.setVisibility(0);
                   long viewCount = sp.getLong(PREF_INVITE_BANNER_VIEW_COUNT, 0L) + 1;
                   sp.edit().putLong(PREF_INVITE_BANNER_VIEW_COUNT, viewCount).apply();
                            if ((viewCount < 10 && viewCount % 5 == 0) || viewCount % 10 == 0) {
                       FlurryUtils.trackInviteBannerViewed(viewCount);
                            }
                        } catch (Exception e) {
                   CrashUtil.logException(e, "Failed to show invite banner, which is ok.", new Object[0]);
                   sp.edit().putBoolean(PREF_INVITE_BANNER_CLICKED, true).apply();
                        }
                    }
           Util.getDefaultSharedPrefs(getActivity());
           Intent activityIntent = getActivity().getIntent();
           if (activityIntent.hasExtra(StartActivity.EXTRA_IS_NEW_USER)) {
               sIsNewUser = true;
                    }
           SharedPreferences capturePrefs = getActivity().getSharedPreferences(BasicVineRecorder.PREFS_CAPTURE, 0);
           int launchCount = capturePrefs.getInt(AbstractRecordingActivity.PREF_CAPTURE_LAUNCH_COUNT, 0);
           boolean shouldShowAddWidget = account != null && !BuildUtil.isAmazon() && AppImpl.getInstance().doAddWidget(activity, am, account) && launchCount > 5;
           if (shouldShowAddWidget) {
               VineAccountHelper.setDidShowAddWidget(am, account);
               PromptDialogSupportFragment p = PromptDialogSupportFragment.newInstance(DIALOG_ADD_WIDGET);
               p.setTitle(R.string.add_capture_widget_title);
               p.setMessage(R.string.add_capture_widget);
               p.setNegativeButton(R.string.cancel);
               p.setPositiveButton(R.string.add_widget);
               p.setListener(this.mAddWidgetDialogDoneListener);
                        try {
                   p.show(activity.getSupportFragmentManager());
                        } catch (Exception e2) {
                        }
                    }
                }

                
       class ViewOnClickListenerC02191 implements View.OnClickListener {
           ViewOnClickListenerC02191() {
                    }

                    @Override 
           public void onClick(View view) {
               FlurryUtils.trackVisitFindFriends("InviteBanner");
               Intent intent = new Intent(HomeTimelineFragment.this.getActivity(), (Class<?>) FindFriendsActivity.class);
               HomeTimelineFragment.this.getActivity().startActivityForResult(intent, 0);
                    }
                }

                @Override 
       public void onActivityResult(int requestCode, int resultCode, Intent data) {
           super.onActivityResult(requestCode, resultCode, data);
           if (requestCode == 1 && resultCode == 1) {
               this.mFetched = false;
                    }
                }

       public void removeBanner() {
           if (this.mInviteBottomBanner != null) {
               this.mInviteBottomBanner.setVisibility(8);
                    }
                }

                @Override 
       public void onResume() {
           super.onResume();
           registerMergePostReceiver();
           ((HomeTabActivity) getActivity()).showMenuIfNeeded();
           this.mFeedAdapter.onResume(isFocused());
           this.mFeedAdapter.startLoadingTimeProfiling("HomeTimelineFragment");
           
           
           
           if (this.mFeedAdapter.getCount() == 0 && !hasPendingRequest(3)) {
               fetchInitialRequest(UrlCachePolicy.NETWORK_THEN_CACHE);
               View root = getView();
               if (root != null) {
                   root.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    if (HomeTimelineFragment.this.mAdapter != null && HomeTimelineFragment.this.mAdapter.isEmpty()) {
                                        HomeTimelineFragment.this.hideProgress(3);
                                        HomeTimelineFragment.this.showSadface(true);
                                    }
                                }
                            }, 12000L);
                            }
                    }
           if (ClientFlagsHelper.shouldShowClientFlagsBanner(getActivity()) && !this.mClientFlagsBannerWasDismissed && Util.getActiveNetworkType(getActivity()) == 0) {
               showClientFlagsBanner();
               SharedPreferences prefs = Util.getDefaultSharedPrefs(getActivity());
               String messageTitle = prefs.getString(ClientFlagsHelper.PREF_MESSAGE_TITLE, null);
               String messageText = prefs.getString(ClientFlagsHelper.PREF_MESSAGE_TEXT, null);
               if (!TextUtils.isEmpty(messageTitle)) {
                            setBannerText(2131427459, messageTitle);
                        }
               if (!TextUtils.isEmpty(messageText)) {
                            setBannerText(2131427465, messageText);
                        }
               if (this.mInviteBottomBanner != null) {
                   this.mInviteBottomBanner.setVisibility(8);
                        }
                    }
                }

                @Override 
       public void onPause() {
           super.onPause();
           this.mFeedAdapter.onPause(isFocused());
                }

                @Override 
       protected String fetchPosts(int page, long anchor, boolean userInitiated, UrlCachePolicy cachePolicy) {
           this.mAppController.fetchActivityCounts();
           return this.mAppController.fetchPosts(this.mAppController.getActiveSession(), 10, this.mAppController.getActiveId(), 1, page, anchor, userInitiated, String.valueOf(this.mAppController.getActiveId()), null, null, cachePolicy);
                }

                
       class C02202 implements PromptDialogSupportFragment.OnDialogDoneListener {
           C02202() {
                    }

                    @Override 
           public void onDialogDone(DialogInterface dialog, int id, int which) {
                        switch (which) {
                            case -1:
                       AppImpl.getInstance().setupWidget(HomeTimelineFragment.this.getActivity());
                                break;
                        }
               dialog.dismiss();
                    }
                }

       private class HomeTimeLineSessionListener extends BaseTimelineFragment.TimeLineSessionListener {
           private HomeTimeLineSessionListener() {
                        super();
                    }

            HomeTimeLineSessionListener(HomeTimelineFragment x0, ViewOnClickListenerC02191 x1) {
               this();
                    }

                    @Override 
           public void onFetchClientFlagsComplete(String reqId, int statusCode, String reasonPhrase, VineClientFlags clientFlags, boolean showClientFlagsBanner) {
               super.onFetchClientFlagsComplete(reqId, statusCode, reasonPhrase, clientFlags, showClientFlagsBanner);
               if (statusCode == 200 && HomeTimelineFragment.this.isFocused() && HomeTimelineFragment.access$100(HomeTimelineFragment.this) != null && showClientFlagsBanner) {
                   HomeTimelineFragment.access$100(HomeTimelineFragment.this).setVisibility(8);
                        }
                    }

                    @Override 
           public void onMobileDataNetworkActivated() {
               super.onMobileDataNetworkActivated();
               if (!ClientFlagsHelper.shouldShowClientFlagsBanner(HomeTimelineFragment.this.getActivity()) || !HomeTimelineFragment.this.isFocused() || HomeTimelineFragment.access$100(HomeTimelineFragment.this) == null) {
                   return;
                        }
                        HomeTimelineFragment.access$100(HomeTimelineFragment.this).setVisibility(8);
                    }
                }
            }
