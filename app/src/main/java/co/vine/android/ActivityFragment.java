            package co.vine.android;

            import android.content.SharedPreferences;
            import android.os.Bundle;
            import android.support.v4.app.FragmentActivity;
            import android.util.Log;
            import android.view.LayoutInflater;
            import android.view.View;
            import android.view.ViewGroup;
            import android.widget.ListAdapter;
            import android.widget.ListView;
            import co.vine.android.api.VineEverydayNotification;
            import co.vine.android.api.VinePagedData;
            import co.vine.android.api.VineSingleNotification;
            import co.vine.android.api.VineUser;
            import co.vine.android.cache.image.ImageKey;
            import co.vine.android.cache.image.UrlImage;
            import co.vine.android.client.AppSessionListener;
            import co.vine.android.network.UrlCachePolicy;
            import co.vine.android.service.GCMNotificationService;
            import co.vine.android.util.BuildUtil;
            import co.vine.android.util.Util;
            import co.vine.android.widget.ActivityViewHolder;
            import co.vine.android.widget.OnTabChangedListener;
            import co.vine.android.widget.PinnedHeaderListView;
            import co.vine.android.widget.SpanClickListener;
            import java.util.HashMap;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 31 */    public class ActivityFragment extends BaseArrayListFragment implements SpanClickListener, View.OnClickListener, OnTabChangedListener {
                private static final String EVENT_SOURCE_TITLE = "Activity Feed";
                private static final String STATE_PRIVATE = "state_private";
                private static final String TAG = "ActivityFragment";
                private boolean mFetched;
                private boolean mPrivate;

/* 32 */        static /* synthetic */ void access$000(ActivityFragment x0, int x1, boolean x2, UrlCachePolicy x3) {
/* 33 */            x0.fetchContent(x1, x2, x3);
                }

/* 32 */        static /* synthetic */ boolean access$102(ActivityFragment x0, boolean x1) {
/* 33 */            x0.mPrivate = x1;
                    return x1;
                }

                @Override // co.vine.android.BaseArrayListFragment, co.vine.android.BaseAdapterFragment, co.vine.android.BaseControllerFragment, android.support.v4.app.Fragment
/* 46 */        public void onCreate(Bundle savedInstanceState) {
/* 47 */            super.onCreate(savedInstanceState);
/* 48 */            this.mAppSessionListener = new ActivityListener();
/* 49 */            if (savedInstanceState != null) {
/* 50 */                if (savedInstanceState.containsKey(STATE_PRIVATE)) {
/* 51 */                    this.mPrivate = savedInstanceState.getBoolean(STATE_PRIVATE);
                        }
                    } else {
/* 54 */                SharedPreferences pr = Util.getDefaultSharedPrefs(getActivity());
/* 55 */                this.mPrivate = pr.getBoolean(Settings.PREF_PRIVATE, false);
                    }
                }

                @Override // android.support.v4.app.Fragment
/* 60 */        public void onActivityCreated(Bundle savedInstanceState) {
/* 61 */            super.onActivityCreated(savedInstanceState);
/* 63 */            if (getArguments().getBoolean("take_focus", false) && !this.mFetched) {
/* 64 */                HomeTabActivity callback = (HomeTabActivity) getActivity();
/* 65 */                callback.showPage(1, this);
/* 66 */                if (BuildUtil.isLogsOn()) {
/* 67 */                    Log.d(TAG, "Activity tab took focus.");
                        }
                    }
                }

                @Override // co.vine.android.BaseArrayListFragment, android.support.v4.app.Fragment
/* 74 */        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
/* 75 */            View v = createView(inflater, C0257R.layout.msg_list_fragment_activity, container);
/* 76 */            this.mListView.setDividerHeight(0);
/* 77 */            this.mAdapter = new ActivityAdapter(getActivity(), this.mListView, this.mAppController, this);
/* 78 */            this.mListView.setAdapter((ListAdapter) this.mAdapter);
/* 79 */            if (getActivity() instanceof HomeTabActivity) {
/* 80 */                View header = LayoutInflater.from(getActivity()).inflate(C0257R.layout.under_nav_header, (ViewGroup) null);
/* 81 */                ((PinnedHeaderListView) this.mListView).setRefreshHeader(header, getResources().getDimensionPixelSize(C0257R.dimen.tabbar_height));
/* 82 */                this.mListView.addHeaderView(header, null, false);
                    }
/* 84 */            return v;
                }

                @Override // co.vine.android.BaseArrayListFragment, co.vine.android.BaseAdapterFragment, co.vine.android.BaseControllerFragment, co.vine.android.BaseFragment, android.support.v4.app.Fragment
/* 88 */        public void onResume() {
/* 89 */            super.onResume();
/* 90 */            if (this.mAdapter.getCount() == 0 && !hasPendingRequest(3)) {
/* 91 */                fetchContent(3, UrlCachePolicy.NETWORK_THEN_CACHE);
/* 92 */                final ActivityFragment fragment = this;
/* 93 */                View root = getView();
/* 94 */                if (root != null) {
/* 95 */                    root.postDelayed(new Runnable() {
/* 96 */                        @Override
/* 97 */                        public void run() {
/* 98 */                            if (fragment.mAdapter != null && fragment.mAdapter.getCount() == 0 && !fragment.hasPendingRequest(3)) {
/* 99 */                                fragment.hideProgress(3);
/* 100 */                               fragment.showSadface(true);
                                    }
                                }
                            }, 12000L);
                        }
                    }
                }

                @Override // co.vine.android.BaseArrayListFragment
/* 95 */        protected void onScrollLastItem() {
/* 96 */            if (this.mRefreshable && this.mNextPage > 0 && this.mAdapter.getCount() <= 400) {
/* 98 */                fetchContent(1, UrlCachePolicy.FORCE_REFRESH);
                    }
                }

                @Override // co.vine.android.BaseArrayListFragment, co.vine.android.BaseAdapterFragment, android.support.v4.app.Fragment
/* 103 */       public void onSaveInstanceState(Bundle outState) {
/* 104 */           super.onSaveInstanceState(outState);
/* 105 */           outState.putBoolean(STATE_PRIVATE, this.mPrivate);
                }

                @Override // co.vine.android.BaseArrayListFragment
/* 109 */       protected void refresh() {
/* 110 */           fetchContent(2, UrlCachePolicy.FORCE_REFRESH);
                }

                @Override // co.vine.android.BaseArrayListFragment, co.vine.android.widget.OnTabChangedListener
/* 114 */       public void onMoveTo(int oldPosition) {
/* 115 */           super.onMoveTo(oldPosition);
/* 116 */           this.mFocused = true;
/* 117 */           SharedPreferences pr = Util.getDefaultSharedPrefs(getActivity());
/* 118 */           this.mPrivate = pr.getBoolean(Settings.PREF_PRIVATE, false);
/* 120 */           if (this.mAdapter != null && !hasPendingRequest()) {
/* 121 */               if (this.mAdapter.isEmpty()) {
/* 122 */                   fetchContent(3, UrlCachePolicy.NETWORK_THEN_CACHE);
                        } else {
/* 124 */                   fetchContent(2, true, UrlCachePolicy.NETWORK_THEN_CACHE);
                        }
                    }
                }

                @Override // co.vine.android.BaseArrayListFragment, co.vine.android.widget.OnTabChangedListener
/* 130 */       public void onMoveAway(int newPosition) {
/* 131 */           super.onMoveAway(newPosition);
/* 132 */           this.mFocused = false;
                }

/* 135 */       private void fetchContent(int fetchType, UrlCachePolicy cachePolicy) {
/* 136 */           fetchContent(fetchType, false, cachePolicy);
                }

/* 139 */       private void fetchContent(int fetchType, boolean silent, UrlCachePolicy cachePolicy) {
/* 140 */           if (!hasPendingRequest(fetchType)) {
/* 143 */               this.mFetched = true;
/* 145 */               if (!silent) {
/* 146 */                   showProgress(fetchType);
                        }
                        switch (fetchType) {
                            case 2:
/* 150 */                       this.mNextPage = 1;
/* 151 */                       this.mAnchor = 0L;
                                break;
                            case 3:
/* 155 */                       this.mNextPage = 1;
/* 156 */                       this.mAnchor = 0L;
                                break;
                        }
/* 164 */               addRequest(this.mAppController.fetchActivity(this.mAppController.getActiveSession(), this.mNextPage, this.mAnchor, this.mPrivate, isFocused(), cachePolicy), fetchType);
/* 166 */               this.mAppController.fetchActivityCounts();
                    }
                }

                @Override // co.vine.android.widget.SpanClickListener
/* 170 */       public void onSpanClicked(View view, int type, Object tag) {
                    switch (type) {
                        case 1:
/* 173 */                   ProfileActivity.start(getActivity(), ((Long) tag).longValue(), EVENT_SOURCE_TITLE);
                            break;
                        case 2:
                        default:
/* 195 */                   if (view.getId() == C0257R.id.user_image) {
/* 196 */                       ProfileActivity.start(getActivity(), ((Long) tag).longValue(), EVENT_SOURCE_TITLE);
                            }
                            break;
                        case 3:
/* 177 */                   HashtagActivity.start(getActivity(), (String) tag);
                            break;
                        case 4:
/* 181 */                   UsersActivity.startUserListForNotification(getActivity(), ((Long) ((Object[]) tag)[0]).longValue(), ((Long) ((Object[]) tag)[1]).longValue());
                            break;
                        case 5:
/* 191 */                   CommentsActivity.start(getActivity(), ((Long) tag).longValue(), 0L, this.mAppController.getActiveId(), true);
                            break;
                    }
                }

                @Override // android.view.View.OnClickListener
/* 201 */       public void onClick(View view) {
/* 202 */           ActivityAdapter adapter = (ActivityAdapter) this.mAdapter;
/* 203 */           int id = view.getId();
/* 204 */           if (id == C0257R.id.user_image) {
/* 205 */               ProfileActivity.start(getActivity(), ((Long) view.getTag()).longValue(), EVENT_SOURCE_TITLE);
/* 218 */               return;
                    }
/* 206 */           if (id == C0257R.id.follow) {
/* 207 */               long userId = ((Long) view.getTag()).longValue();
/* 208 */               if (!((ActivityAdapter) this.mAdapter).isFollowing(userId)) {
/* 209 */                   adapter.follow(userId);
/* 210 */                   addRequest(this.mAppController.followUser(this.mAppController.getActiveSession(), userId, false));
                        } else {
/* 213 */                   adapter.unfollow(userId);
/* 214 */                   addRequest(this.mAppController.unfollowUser(this.mAppController.getActiveSession(), userId, false));
                        }
                    }
                }

                @Override // co.vine.android.BaseArrayListFragment
/* 221 */       protected void onListItemClick(ListView l, View v, int position, long id) {
/* 222 */           if (v.getTag() instanceof ActivityViewHolder) {
/* 223 */               ActivityViewHolder holder = (ActivityViewHolder) v.getTag();
/* 224 */               if (holder.clickable) {
/* 225 */                   if (holder.listItemClickType == 1 && holder.postId > 0) {
/* 226 */                       SingleActivity.start(getActivity(), holder.postId);
/* 237 */                       return;
                            }
/* 227 */                   if (holder.listItemClickType == 0 && holder.userId > 0) {
/* 228 */                       ProfileActivity.start(getActivity(), holder.userId, EVENT_SOURCE_TITLE);
/* 237 */                       return;
                            }
/* 229 */                   if (holder.listItemClickType == 2 && holder.notificationId > 0) {
/* 230 */                       UsersActivity.startUserListForNotification(getActivity(), holder.notificationId, holder.anchor);
                            } else if (holder.listItemClickType == 3 && holder.postId > 0) {
/* 233 */                       CommentsActivity.start(getActivity(), holder.postId, 0L, this.mAppController.getActiveId(), true);
                            }
                        }
                    }
                }

/* 237 */       class ActivityListener extends AppSessionListener {
/* 238 */           ActivityListener() {
                    }

                    @Override // co.vine.android.client.AppSessionListener
/* 242 */           public void onFollowComplete(String reqId, int statusCode, String reasonPhrase, long userId) {
/* 243 */               PendingRequest req = ActivityFragment.this.removeRequest(reqId);
/* 244 */               if (req != null && statusCode != 200) {
/* 246 */                   ((ActivityAdapter) ActivityFragment.this.mAdapter).unfollow(userId);
                        }
                    }

                    @Override // co.vine.android.client.AppSessionListener
/* 252 */           public void onUnFollowComplete(String reqId, int statusCode, String reasonPhrase, long userId) {
/* 253 */               PendingRequest req = ActivityFragment.this.removeRequest(reqId);
/* 254 */               if (req != null && statusCode != 200) {
/* 256 */                   ((ActivityAdapter) ActivityFragment.this.mAdapter).follow(userId);
                        }
                    }

                    /* JADX WARN: Multi-variable type inference failed */
                    @Override // co.vine.android.client.AppSessionListener
/* 264 */           public void onGetActivityComplete(String reqId, int statusCode, String reasonPhrase, VinePagedData<VineEverydayNotification> notifications, VinePagedData<VineSingleNotification> followRequests, UrlCachePolicy policyUsed) {
/* 265 */               PendingRequest req = ActivityFragment.this.removeRequest(reqId);
/* 266 */
/* 266 */               if (req != null) {
/* 267 */                   if (!policyUsed.mNetworkDataAllowed) {
/* 268 */                       ActivityFragment.access$000(ActivityFragment.this, 3, true, UrlCachePolicy.NETWORK_THEN_CACHE);
                            }
/* 271 */                   ActivityFragment.this.hideProgress(req.fetchType);
/* 273 */                   SharedPreferences pr = Util.getDefaultSharedPrefs(ActivityFragment.this.getActivity());
/* 274 */                   ActivityFragment.access$102(ActivityFragment.this, pr.getBoolean(Settings.PREF_PRIVATE, false));
/* 276 */                   int count = 0;
/* 277 */                   if (notifications != null) {
/* 278 */                       count = notifications.items == null ? 0 : notifications.items.size();
                            }
/* 281 */                   int followRequestCount = 0;
/* 282 */                   if (followRequests != null) {
/* 283 */                       followRequestCount = followRequests.items == null ? 0 : followRequests.items.size();
                            }
/* 286 */                   if (ActivityFragment.this.mAdapter.getCount() == 0 && count == 0 && followRequestCount == 0) {
/* 287 */                       if (statusCode != 200) {
/* 288 */                           ActivityFragment.this.mEmptyTextStringRes = C0257R.string.failed_to_load_activity;
/* 289 */                           ActivityFragment.this.mEmptyText.setText(ActivityFragment.this.mEmptyTextStringRes);
                                }
/* 291 */                       ActivityFragment.this.showSadface(true);
                            } else {
/* 293 */                       ActivityFragment.this.showSadface(false);
                            }
/* 296 */                   if (statusCode == 200) {
/* 297 */                       if (notifications != null) {
/* 298 */                           ActivityFragment.this.mNextPage = notifications.nextPage;
/* 299 */                           ActivityFragment.this.mAnchor = notifications.anchor;
                                }
/* 301 */                       if (ActivityFragment.this.mAdapter != null) {
/* 302 */                           ((ActivityAdapter) ActivityFragment.this.mAdapter).mergeData(notifications == null ? null : notifications.items, followRequests == null ? null : followRequests.items, req.fetchType == 1);
                                }
                            }
/* 308 */                   FragmentActivity activity = ActivityFragment.this.getActivity();
/* 310 */                   if (activity != null) {
/* 312 */                       activity.startService(GCMNotificationService.getClearNotificationIntent(ActivityFragment.this.getActivity(), 1));
/* 315 */                       ((HomeTabActivity) activity).markActivitiesRead();
                            }
                        }
                    }

                    @Override // co.vine.android.client.AppSessionListener
/* 321 */           public void onPhotoImageLoaded(HashMap<ImageKey, UrlImage> images) {
/* 322 */               ((ActivityAdapter) ActivityFragment.this.mAdapter).setImages(images);
                    }

                    @Override // co.vine.android.client.AppSessionListener
/* 327 */           public void onGetUsersMeComplete(String reqId, int statusCode, String reasonPhrase, long sessionOwnerId, VineUser meUser, UrlCachePolicy policy) {
/* 328 */               if (meUser != null && meUser.isPrivate()) {
/* 329 */                   ActivityFragment.access$102(ActivityFragment.this, true);
                        }
                    }
                }
            }
