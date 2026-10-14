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
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 34 */    public class HomeTimelineFragment extends BaseTimelineFragment {
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

/* 35 */        static /* synthetic */ FrameLayout access$100(HomeTimelineFragment x0) {
/* 36 */            return x0.mInviteBottomBanner;
                }

                @Override // co.vine.android.BaseTimelineFragment, co.vine.android.BaseArrayListFragment, co.vine.android.BaseAdapterFragment, co.vine.android.BaseControllerFragment, android.support.v4.app.Fragment
/* 57 */        public void onCreate(Bundle savedInstanceState) {
/* 58 */            super.onCreate(savedInstanceState);
/* 59 */            this.mAppSessionListener = new HomeTimeLineSessionListener(this, null);
/* 60 */            setFlurryEventSource(EVENT_SOURCE_TITLE);
/* 61 */            setFocused(true);
                }

                @Override // co.vine.android.BaseTimelineFragment, co.vine.android.BaseArrayListFragment, android.widget.AbsListView.OnScrollListener
/* 65 */        public synchronized void onScrollStateChanged(AbsListView view, int scrollState) {
                    try {
                        super.onScrollStateChanged(view, scrollState);
/* 67 */                this.mScrollState = scrollState;
/* 68 */                if (this.mNewPosts != null) {
/* 69 */                    appendPosts(this.mNewPosts);
/* 70 */                    this.mNewPosts = null;
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }

/* 74 */        private void appendPosts(ArrayList<VinePost> posts) {
/* 75 */            int badgeCount = ((FeedAdapter) this.mFeedAdapter).appendPostsOnTop(posts);
/* 76 */            if (badgeCount < 0) {
/* 77 */                ((FeedAdapter) this.mFeedAdapter).replacePosts(posts);
                    } else if (badgeCount > 0) {
/* 79 */                FragmentActivity activity = getActivity();
/* 80 */                if (activity instanceof HomeTabActivity) {
/* 81 */                    ((HomeTabActivity) activity).setHomeNewCount(badgeCount);
                        }
                    }
                }

                @Override // co.vine.android.BaseTimelineFragment
/* 87 */        public void onTopViewBinded() {
/* 88 */            FragmentActivity activity = getActivity();
/* 89 */            if (activity instanceof HomeTabActivity) {
/* 90 */                ((HomeTabActivity) activity).setHomeNewCount(0);
                    }
                }

                @Override // co.vine.android.BaseTimelineFragment
/* 95 */        protected boolean trackNewCount() {
/* 96 */            return true;
                }

                @Override // co.vine.android.BaseTimelineFragment
/* 100 */       protected void onPostUpdatesCameIn(ArrayList<VinePost> posts) {
/* 101 */           if (this.mScrollState != 0) {
/* 102 */               this.mNewPosts = posts;
                    } else {
/* 104 */               appendPosts(posts);
                    }
                }

                @Override // co.vine.android.BaseTimelineFragment, android.support.v4.app.Fragment
/* 109 */       public void onActivityCreated(Bundle savedInstanceState) {
/* 110 */           super.onActivityCreated(savedInstanceState);
/* 111 */           this.mListView.setAdapter((ListAdapter) this.mFeedAdapter);
          // The reconstructed adapter can report a non-zero count for its
          // header/placeholder before it has received any posts. Start the
          // initial request after the ListView is attached instead of relying
          // on that count to decide whether the feed needs loading.
          if (!hasPendingRequest(3)) {
              fetchInitialRequest(UrlCachePolicy.NETWORK_THEN_CACHE);
          }
/* 113 */           FragmentActivity activity = getActivity();
/* 114 */           AccountManager am = AccountManager.get(activity);
/* 115 */           Account account = VineAccountHelper.getAccount(getActivity(), this.mAppController.getActiveSession().getLoginEmail());
/* 118 */           SharedPreferences sp = Util.getDefaultSharedPrefs(getActivity());
/* 119 */           int followCount = sp.getInt(Settings.PREF_FOLLOWING_COUNT, 0);
/* 121 */           if (!sp.getBoolean(PREF_INVITE_BANNER_CLICKED, false) || followCount <= 5) {
                        try {
/* 123 */                   this.mInviteBottomBanner = (FrameLayout) getView().findViewById(C0257R.id.find_friends);
/* 124 */                   this.mInviteBottomBanner.setOnClickListener(new ViewOnClickListenerC02191());
/* 133 */                   this.mInviteBottomBanner.setVisibility(0);
/* 135 */                   long viewCount = sp.getLong(PREF_INVITE_BANNER_VIEW_COUNT, 0L) + 1;
/* 136 */                   sp.edit().putLong(PREF_INVITE_BANNER_VIEW_COUNT, viewCount).apply();
                            if ((viewCount < 10 && viewCount % 5 == 0) || viewCount % 10 == 0) {
/* 138 */                       FlurryUtils.trackInviteBannerViewed(viewCount);
                            }
                        } catch (Exception e) {
/* 142 */                   CrashUtil.logException(e, "Failed to show invite banner, which is ok.", new Object[0]);
/* 143 */                   sp.edit().putBoolean(PREF_INVITE_BANNER_CLICKED, true).apply();
                        }
                    }
/* 147 */           Util.getDefaultSharedPrefs(getActivity());
/* 148 */           Intent activityIntent = getActivity().getIntent();
/* 149 */           if (activityIntent.hasExtra(StartActivity.EXTRA_IS_NEW_USER)) {
/* 150 */               sIsNewUser = true;
                    }
/* 153 */           SharedPreferences capturePrefs = getActivity().getSharedPreferences(BasicVineRecorder.PREFS_CAPTURE, 0);
/* 155 */           int launchCount = capturePrefs.getInt(AbstractRecordingActivity.PREF_CAPTURE_LAUNCH_COUNT, 0);
/* 156 */           boolean shouldShowAddWidget = account != null && !BuildUtil.isAmazon() && AppImpl.getInstance().doAddWidget(activity, am, account) && launchCount > 5;
/* 160 */           if (shouldShowAddWidget) {
/* 161 */               VineAccountHelper.setDidShowAddWidget(am, account);
/* 162 */               PromptDialogSupportFragment p = PromptDialogSupportFragment.newInstance(DIALOG_ADD_WIDGET);
/* 164 */               p.setTitle(C0257R.string.add_capture_widget_title);
/* 165 */               p.setMessage(C0257R.string.add_capture_widget);
/* 166 */               p.setNegativeButton(C0257R.string.cancel);
/* 167 */               p.setPositiveButton(C0257R.string.add_widget);
/* 168 */               p.setListener(this.mAddWidgetDialogDoneListener);
                        try {
/* 170 */                   p.show(activity.getSupportFragmentManager());
                        } catch (Exception e2) {
                        }
                    }
                }

                /* JADX INFO: renamed from: co.vine.android.HomeTimelineFragment$1 */
/* 122 */       class ViewOnClickListenerC02191 implements View.OnClickListener {
/* 123 */           ViewOnClickListenerC02191() {
                    }

                    @Override // android.view.View.OnClickListener
/* 126 */           public void onClick(View view) {
/* 127 */               FlurryUtils.trackVisitFindFriends("InviteBanner");
/* 128 */               Intent intent = new Intent(HomeTimelineFragment.this.getActivity(), (Class<?>) FindFriendsActivity.class);
/* 129 */               HomeTimelineFragment.this.getActivity().startActivityForResult(intent, 0);
                    }
                }

                @Override // co.vine.android.BaseTimelineFragment, co.vine.android.BaseArrayListFragment, android.support.v4.app.Fragment
/* 178 */       public void onActivityResult(int requestCode, int resultCode, Intent data) {
/* 179 */           super.onActivityResult(requestCode, resultCode, data);
/* 180 */           if (requestCode == 1 && resultCode == 1) {
/* 182 */               this.mFetched = false;
                    }
                }

/* 188 */       public void removeBanner() {
/* 189 */           if (this.mInviteBottomBanner != null) {
/* 190 */               this.mInviteBottomBanner.setVisibility(8);
                    }
                }

                @Override // co.vine.android.BaseTimelineFragment, co.vine.android.BaseArrayListFragment, co.vine.android.BaseAdapterFragment, co.vine.android.BaseControllerFragment, co.vine.android.BaseFragment, android.support.v4.app.Fragment
/* 195 */       public void onResume() {
/* 196 */           super.onResume();
/* 197 */           registerMergePostReceiver();
/* 198 */           ((HomeTabActivity) getActivity()).showMenuIfNeeded();
/* 199 */           this.mFeedAdapter.onResume(isFocused());
/* 200 */           this.mFeedAdapter.startLoadingTimeProfiling("HomeTimelineFragment");
/* 201 */           // A ListView header/banner is not a post. Use the feed adapter
/* 202 */           // count and pending-request state so a fresh install always
/* 203 */           // performs the initial network fetch.
/* 204 */           if (this.mFeedAdapter.getCount() == 0 && !hasPendingRequest(3)) {
/* 202 */               fetchInitialRequest(UrlCachePolicy.NETWORK_THEN_CACHE);
/* 203 */               View root = getView();
/* 204 */               if (root != null) {
/* 205 */                   root.postDelayed(new Runnable() {
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
/* 205 */           if (ClientFlagsHelper.shouldShowClientFlagsBanner(getActivity()) && !this.mClientFlagsBannerWasDismissed && Util.getActiveNetworkType(getActivity()) == 0) {
/* 207 */               showClientFlagsBanner();
/* 208 */               SharedPreferences prefs = Util.getDefaultSharedPrefs(getActivity());
/* 209 */               String messageTitle = prefs.getString(ClientFlagsHelper.PREF_MESSAGE_TITLE, null);
/* 210 */               String messageText = prefs.getString(ClientFlagsHelper.PREF_MESSAGE_TEXT, null);
/* 211 */               if (!TextUtils.isEmpty(messageTitle)) {
                            setBannerText(2131427459, messageTitle);
                        }
/* 212 */               if (!TextUtils.isEmpty(messageText)) {
                            setBannerText(2131427465, messageText);
                        }
/* 214 */               if (this.mInviteBottomBanner != null) {
/* 215 */                   this.mInviteBottomBanner.setVisibility(8);
                        }
                    }
                }

                @Override // co.vine.android.BaseTimelineFragment, co.vine.android.BaseArrayListFragment, co.vine.android.BaseAdapterFragment, co.vine.android.BaseControllerFragment, android.support.v4.app.Fragment
/* 221 */       public void onPause() {
/* 222 */           super.onPause();
/* 223 */           this.mFeedAdapter.onPause(isFocused());
                }

                @Override // co.vine.android.BaseTimelineFragment
/* 227 */       protected String fetchPosts(int page, long anchor, boolean userInitiated, UrlCachePolicy cachePolicy) {
/* 229 */           this.mAppController.fetchActivityCounts();
/* 230 */           return this.mAppController.fetchPosts(this.mAppController.getActiveSession(), 10, this.mAppController.getActiveId(), 1, page, anchor, userInitiated, String.valueOf(this.mAppController.getActiveId()), null, null, cachePolicy);
                }

                /* JADX INFO: renamed from: co.vine.android.HomeTimelineFragment$2 */
/* 234 */       class C02202 implements PromptDialogSupportFragment.OnDialogDoneListener {
/* 235 */           C02202() {
                    }

                    @Override // co.vine.android.widgets.PromptDialogFragment.OnDialogDoneListener
/* 238 */           public void onDialogDone(DialogInterface dialog, int id, int which) {
                        switch (which) {
                            case -1:
/* 241 */                       AppImpl.getInstance().setupWidget(HomeTimelineFragment.this.getActivity());
                                break;
                        }
/* 245 */               dialog.dismiss();
                    }
                }

/* 247 */       private class HomeTimeLineSessionListener extends BaseTimelineFragment.TimeLineSessionListener {
/* 248 */           private HomeTimeLineSessionListener() {
                        super();
                    }

/* 248 */           /* synthetic */ HomeTimeLineSessionListener(HomeTimelineFragment x0, ViewOnClickListenerC02191 x1) {
/* 249 */               this();
                    }

                    @Override // co.vine.android.BaseTimelineFragment.TimeLineSessionListener, co.vine.android.client.AppSessionListener
/* 253 */           public void onFetchClientFlagsComplete(String reqId, int statusCode, String reasonPhrase, VineClientFlags clientFlags, boolean showClientFlagsBanner) {
/* 254 */               super.onFetchClientFlagsComplete(reqId, statusCode, reasonPhrase, clientFlags, showClientFlagsBanner);
/* 257 */               if (statusCode == 200 && HomeTimelineFragment.this.isFocused() && HomeTimelineFragment.access$100(HomeTimelineFragment.this) != null && showClientFlagsBanner) {
/* 259 */                   HomeTimelineFragment.access$100(HomeTimelineFragment.this).setVisibility(8);
                        }
                    }

                    @Override // co.vine.android.BaseTimelineFragment.TimeLineSessionListener, co.vine.android.client.AppSessionListener
/* 265 */           public void onMobileDataNetworkActivated() {
/* 266 */               super.onMobileDataNetworkActivated();
/* 267 */               if (!ClientFlagsHelper.shouldShowClientFlagsBanner(HomeTimelineFragment.this.getActivity()) || !HomeTimelineFragment.this.isFocused() || HomeTimelineFragment.access$100(HomeTimelineFragment.this) == null) {
/* 270 */                   return;
                        }
                        HomeTimelineFragment.access$100(HomeTimelineFragment.this).setVisibility(8);
                    }
                }
            }
