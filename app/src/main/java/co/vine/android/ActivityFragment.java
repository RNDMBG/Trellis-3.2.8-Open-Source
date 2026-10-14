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
            
            
    public class ActivityFragment extends BaseArrayListFragment implements SpanClickListener, View.OnClickListener, OnTabChangedListener {
                private static final String EVENT_SOURCE_TITLE = "Activity Feed";
                private static final String STATE_PRIVATE = "state_private";
                private static final String TAG = "ActivityFragment";
                private boolean mFetched;
                private boolean mPrivate;

        static  void access$000(ActivityFragment x0, int x1, boolean x2, UrlCachePolicy x3) {
            x0.fetchContent(x1, x2, x3);
                }

        static  boolean access$102(ActivityFragment x0, boolean x1) {
            x0.mPrivate = x1;
                    return x1;
                }

                @Override 
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            this.mAppSessionListener = new ActivityListener();
            if (savedInstanceState != null) {
                if (savedInstanceState.containsKey(STATE_PRIVATE)) {
                    this.mPrivate = savedInstanceState.getBoolean(STATE_PRIVATE);
                        }
                    } else {
                SharedPreferences pr = Util.getDefaultSharedPrefs(getActivity());
                this.mPrivate = pr.getBoolean(Settings.PREF_PRIVATE, false);
                    }
                }

                @Override 
        public void onActivityCreated(Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);
            if (getArguments().getBoolean("take_focus", false) && !this.mFetched) {
                HomeTabActivity callback = (HomeTabActivity) getActivity();
                callback.showPage(1, this);
                if (BuildUtil.isLogsOn()) {
                    Log.d(TAG, "Activity tab took focus.");
                        }
                    }
                }

                @Override 
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View v = createView(inflater, C0257R.layout.msg_list_fragment_activity, container);
            this.mListView.setDividerHeight(0);
            this.mAdapter = new ActivityAdapter(getActivity(), this.mListView, this.mAppController, this);
            this.mListView.setAdapter((ListAdapter) this.mAdapter);
            if (getActivity() instanceof HomeTabActivity) {
                View header = LayoutInflater.from(getActivity()).inflate(C0257R.layout.under_nav_header, (ViewGroup) null);
                ((PinnedHeaderListView) this.mListView).setRefreshHeader(header, getResources().getDimensionPixelSize(C0257R.dimen.tabbar_height));
                this.mListView.addHeaderView(header, null, false);
                    }
            return v;
                }

                @Override 
        public void onResume() {
            super.onResume();
            if (this.mAdapter.getCount() == 0 && !hasPendingRequest(3)) {
                fetchContent(3, UrlCachePolicy.NETWORK_THEN_CACHE);
                final ActivityFragment fragment = this;
                View root = getView();
                if (root != null) {
                    root.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (fragment.mAdapter != null && fragment.mAdapter.getCount() == 0 && !fragment.hasPendingRequest(3)) {
                                fragment.hideProgress(3);
                               fragment.showSadface(true);
                                    }
                                }
                            }, 12000L);
                        }
                    }
                }

                @Override 
        protected void onScrollLastItem() {
            if (this.mRefreshable && this.mNextPage > 0 && this.mAdapter.getCount() <= 400) {
                fetchContent(1, UrlCachePolicy.FORCE_REFRESH);
                    }
                }

                @Override 
       public void onSaveInstanceState(Bundle outState) {
           super.onSaveInstanceState(outState);
           outState.putBoolean(STATE_PRIVATE, this.mPrivate);
                }

                @Override 
       protected void refresh() {
           fetchContent(2, UrlCachePolicy.FORCE_REFRESH);
                }

                @Override 
       public void onMoveTo(int oldPosition) {
           super.onMoveTo(oldPosition);
           this.mFocused = true;
           SharedPreferences pr = Util.getDefaultSharedPrefs(getActivity());
           this.mPrivate = pr.getBoolean(Settings.PREF_PRIVATE, false);
           if (this.mAdapter != null && !hasPendingRequest()) {
               if (this.mAdapter.isEmpty()) {
                   fetchContent(3, UrlCachePolicy.NETWORK_THEN_CACHE);
                        } else {
                   fetchContent(2, true, UrlCachePolicy.NETWORK_THEN_CACHE);
                        }
                    }
                }

                @Override 
       public void onMoveAway(int newPosition) {
           super.onMoveAway(newPosition);
           this.mFocused = false;
                }

       private void fetchContent(int fetchType, UrlCachePolicy cachePolicy) {
           fetchContent(fetchType, false, cachePolicy);
                }

       private void fetchContent(int fetchType, boolean silent, UrlCachePolicy cachePolicy) {
           if (!hasPendingRequest(fetchType)) {
               this.mFetched = true;
               if (!silent) {
                   showProgress(fetchType);
                        }
                        switch (fetchType) {
                            case 2:
                       this.mNextPage = 1;
                       this.mAnchor = 0L;
                                break;
                            case 3:
                       this.mNextPage = 1;
                       this.mAnchor = 0L;
                                break;
                        }
               addRequest(this.mAppController.fetchActivity(this.mAppController.getActiveSession(), this.mNextPage, this.mAnchor, this.mPrivate, isFocused(), cachePolicy), fetchType);
               this.mAppController.fetchActivityCounts();
                    }
                }

                @Override 
       public void onSpanClicked(View view, int type, Object tag) {
                    switch (type) {
                        case 1:
                   ProfileActivity.start(getActivity(), ((Long) tag).longValue(), EVENT_SOURCE_TITLE);
                            break;
                        case 2:
                        default:
                   if (view.getId() == C0257R.id.user_image) {
                       ProfileActivity.start(getActivity(), ((Long) tag).longValue(), EVENT_SOURCE_TITLE);
                            }
                            break;
                        case 3:
                   HashtagActivity.start(getActivity(), (String) tag);
                            break;
                        case 4:
                   UsersActivity.startUserListForNotification(getActivity(), ((Long) ((Object[]) tag)[0]).longValue(), ((Long) ((Object[]) tag)[1]).longValue());
                            break;
                        case 5:
                   CommentsActivity.start(getActivity(), ((Long) tag).longValue(), 0L, this.mAppController.getActiveId(), true);
                            break;
                    }
                }

                @Override 
       public void onClick(View view) {
           ActivityAdapter adapter = (ActivityAdapter) this.mAdapter;
           int id = view.getId();
           if (id == C0257R.id.user_image) {
               ProfileActivity.start(getActivity(), ((Long) view.getTag()).longValue(), EVENT_SOURCE_TITLE);
               return;
                    }
           if (id == C0257R.id.follow) {
               long userId = ((Long) view.getTag()).longValue();
               if (!((ActivityAdapter) this.mAdapter).isFollowing(userId)) {
                   adapter.follow(userId);
                   addRequest(this.mAppController.followUser(this.mAppController.getActiveSession(), userId, false));
                        } else {
                   adapter.unfollow(userId);
                   addRequest(this.mAppController.unfollowUser(this.mAppController.getActiveSession(), userId, false));
                        }
                    }
                }

                @Override 
       protected void onListItemClick(ListView l, View v, int position, long id) {
           if (v.getTag() instanceof ActivityViewHolder) {
               ActivityViewHolder holder = (ActivityViewHolder) v.getTag();
               if (holder.clickable) {
                   if (holder.listItemClickType == 1 && holder.postId > 0) {
                       SingleActivity.start(getActivity(), holder.postId);
                       return;
                            }
                   if (holder.listItemClickType == 0 && holder.userId > 0) {
                       ProfileActivity.start(getActivity(), holder.userId, EVENT_SOURCE_TITLE);
                       return;
                            }
                   if (holder.listItemClickType == 2 && holder.notificationId > 0) {
                       UsersActivity.startUserListForNotification(getActivity(), holder.notificationId, holder.anchor);
                            } else if (holder.listItemClickType == 3 && holder.postId > 0) {
                       CommentsActivity.start(getActivity(), holder.postId, 0L, this.mAppController.getActiveId(), true);
                            }
                        }
                    }
                }

       class ActivityListener extends AppSessionListener {
           ActivityListener() {
                    }

                    @Override 
           public void onFollowComplete(String reqId, int statusCode, String reasonPhrase, long userId) {
               PendingRequest req = ActivityFragment.this.removeRequest(reqId);
               if (req != null && statusCode != 200) {
                   ((ActivityAdapter) ActivityFragment.this.mAdapter).unfollow(userId);
                        }
                    }

                    @Override 
           public void onUnFollowComplete(String reqId, int statusCode, String reasonPhrase, long userId) {
               PendingRequest req = ActivityFragment.this.removeRequest(reqId);
               if (req != null && statusCode != 200) {
                   ((ActivityAdapter) ActivityFragment.this.mAdapter).follow(userId);
                        }
                    }

                    
                    @Override 
           public void onGetActivityComplete(String reqId, int statusCode, String reasonPhrase, VinePagedData<VineEverydayNotification> notifications, VinePagedData<VineSingleNotification> followRequests, UrlCachePolicy policyUsed) {
               PendingRequest req = ActivityFragment.this.removeRequest(reqId);

               if (req != null) {
                   if (!policyUsed.mNetworkDataAllowed) {
                       ActivityFragment.access$000(ActivityFragment.this, 3, true, UrlCachePolicy.NETWORK_THEN_CACHE);
                            }
                   ActivityFragment.this.hideProgress(req.fetchType);
                   SharedPreferences pr = Util.getDefaultSharedPrefs(ActivityFragment.this.getActivity());
                   ActivityFragment.access$102(ActivityFragment.this, pr.getBoolean(Settings.PREF_PRIVATE, false));
                   int count = 0;
                   if (notifications != null) {
                       count = notifications.items == null ? 0 : notifications.items.size();
                            }
                   int followRequestCount = 0;
                   if (followRequests != null) {
                       followRequestCount = followRequests.items == null ? 0 : followRequests.items.size();
                            }
                   if (ActivityFragment.this.mAdapter.getCount() == 0 && count == 0 && followRequestCount == 0) {
                       if (statusCode != 200) {
                           ActivityFragment.this.mEmptyTextStringRes = C0257R.string.failed_to_load_activity;
                           ActivityFragment.this.mEmptyText.setText(ActivityFragment.this.mEmptyTextStringRes);
                                }
                       ActivityFragment.this.showSadface(true);
                            } else {
                       ActivityFragment.this.showSadface(false);
                            }
                   if (statusCode == 200) {
                       if (notifications != null) {
                           ActivityFragment.this.mNextPage = notifications.nextPage;
                           ActivityFragment.this.mAnchor = notifications.anchor;
                                }
                       if (ActivityFragment.this.mAdapter != null) {
                           ((ActivityAdapter) ActivityFragment.this.mAdapter).mergeData(notifications == null ? null : notifications.items, followRequests == null ? null : followRequests.items, req.fetchType == 1);
                                }
                            }
                   FragmentActivity activity = ActivityFragment.this.getActivity();
                   if (activity != null) {
                       activity.startService(GCMNotificationService.getClearNotificationIntent(ActivityFragment.this.getActivity(), 1));
                       ((HomeTabActivity) activity).markActivitiesRead();
                            }
                        }
                    }

                    @Override 
           public void onPhotoImageLoaded(HashMap<ImageKey, UrlImage> images) {
               ((ActivityAdapter) ActivityFragment.this.mAdapter).setImages(images);
                    }

                    @Override 
           public void onGetUsersMeComplete(String reqId, int statusCode, String reasonPhrase, long sessionOwnerId, VineUser meUser, UrlCachePolicy policy) {
               if (meUser != null && meUser.isPrivate()) {
                   ActivityFragment.access$102(ActivityFragment.this, true);
                        }
                    }
                }
            }
