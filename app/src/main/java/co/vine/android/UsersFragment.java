            package co.vine.android;

            import android.os.Bundle;
            import android.view.View;
            import android.widget.ListAdapter;
            import android.widget.ListView;
            import co.vine.android.api.VineUser;
            import co.vine.android.cache.image.ImageKey;
            import co.vine.android.cache.image.UrlImage;
            import co.vine.android.client.AppSessionListener;
            import co.vine.android.client.Session;
            import co.vine.android.service.PendingAction;
            import java.util.ArrayList;
            import java.util.HashMap;
            
            
    public class UsersFragment extends BaseArrayListFragment implements View.OnClickListener {
                public static final String ARG_ANCHOR = "anchor";
                public static final String ARG_NOTIFICATION_ID = "notification_id";
                public static final String ARG_POST_ID = "post_id";
                public static final String ARG_PROFILE_ID = "p_id";
                public static final String ARG_USERS_TYPE = "u_type";
                public static final String STATE_FRIENDSHIPS = "state_friendships";
                private long mAnchorArgument;
                private boolean mFetched;
                private Friendships mFriendships;
                private long mNotificationId;
                private long mPostId;
                private long mProfileId;
                private int mUsersType;

        static  Friendships access$000(UsersFragment x0) {
            return x0.mFriendships;
                }

                @Override 
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            Bundle args = getArguments();
            this.mUsersType = args.getInt("u_type", -1);
            this.mProfileId = args.getLong("p_id");
            this.mPostId = args.getLong("post_id", -1L);
            this.mNotificationId = args.getLong("notification_id", -1L);
            this.mAnchorArgument = args.getLong("anchor", 0L);
            if (savedInstanceState != null) {
                if (savedInstanceState.containsKey("state_friendships")) {
                    this.mFriendships = (Friendships) savedInstanceState.getParcelable("state_friendships");
                        }
                if (this.mFriendships == null) {
                    this.mFriendships = new Friendships();
                    return;
                        }
                return;
                    }
            this.mFriendships = new Friendships();
                }

                @Override 
        public void onActivityCreated(Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);
            this.mAdapter = new UsersMemoryAdapter(getActivity(), this.mAppController, true, this, this.mFriendships);
            this.mListView.setAdapter((ListAdapter) this.mAdapter);
            this.mListView.setDividerHeight(0);
            this.mAppSessionListener = new UsersAppSessionListener();
                }

                @Override 
        public void onResume() {
            super.onResume();
            if (this.mAdapter.isEmpty() && !this.mFetched) {
                fetchContent(3);
                    }
                }

                @Override 
        public void onScrollLastItem() {
            if (this.mRefreshable && this.mNextPage > 0 && this.mAdapter.getCount() <= 400) {
                fetchContent(1);
                    }
                }

                @Override 
        public void onSaveInstanceState(Bundle outState) {
            super.onSaveInstanceState(outState);
            outState.putParcelable("state_friendships", this.mFriendships);
                }

                @Override 
        public void onListItemClick(ListView l, View v, int position, long id) {
                    if (id > 0) {
                ProfileActivity.start(getActivity(), id, "");
                    }
                }

                @Override 
       protected void refresh() {
           fetchContent(2);
                }

       private void fetchContent(int fetchType) {
           if (!this.mPendingRequestHelper.hasPendingRequest(fetchType)) {
               String reqId = null;
                        switch (fetchType) {
                            case 2:
                            case 3:
                            case 4:
                       this.mNextPage = 1;
                       this.mAnchor = this.mAnchorArgument;
                                break;
                        }
                        switch (this.mUsersType) {
                            case 1:
                       reqId = this.mAppController.fetchFollowing(this.mProfileId, this.mNextPage, this.mAnchor);
                                break;
                            case 2:
                       reqId = this.mAppController.fetchFollowers(this.mProfileId, this.mNextPage, this.mAnchor);
                                break;
                            case 5:
                       reqId = this.mAppController.fetchPostLikers(this.mAppController.getActiveSession(), this.mPostId, this.mNextPage, this.mAnchor);
                                break;
                            case 9:
                       reqId = this.mAppController.fetchReviners(this.mAppController.getActiveSession(), this.mPostId, this.mNextPage, this.mAnchor);
                                break;
                            case 12:
                       reqId = this.mAppController.fetchNotificationUsers(this.mAppController.getActiveSession(), this.mNotificationId, this.mNextPage, this.mAnchor);
                                break;
                        }
               if (reqId != null) {
                   this.mPendingRequestHelper.showProgress(fetchType);
                   this.mPendingRequestHelper.addRequest(reqId, fetchType);
                        }
                    }
                }

                @Override 
       public void onClick(View view) {
                    FollowButtonViewHolder holder;
           if (view.getId() == R.id.follow_button && (holder = (FollowButtonViewHolder) view.getTag()) != null && this.mFriendships != null) {
               if (!holder.following) {
                   this.mPendingRequestHelper.addRequest(this.mAppController.followUser(this.mAppController.getActiveSession(), holder.userId, false));
                   this.mFriendships.addFollowing(holder.userId);
                   this.mAdapter.notifyDataSetChanged();
                        } else {
                   this.mPendingRequestHelper.addRequest(this.mAppController.unfollowUser(this.mAppController.getActiveSession(), holder.userId, false, this.mProfileId));
                   this.mFriendships.removeFollowing(holder.userId);
                   this.mAdapter.notifyDataSetChanged();
                        }
                    }
                }

       class UsersAppSessionListener extends AppSessionListener {
           UsersAppSessionListener() {
                    }

                    @Override 
           public void onGetUsersComplete(Session session, String reqId, int errorCode, String reasonPhrase, int count, ArrayList<VineUser> users, int nextPage, int prevPage, long anchor) {
                        switch (errorCode) {
                            case 200:
                       PendingRequest req = UsersFragment.this.mPendingRequestHelper.removeRequest(reqId);
                       if (req != null) {
                           UsersFragment.this.mPendingRequestHelper.hideProgress(req.fetchType);
                           if (UsersFragment.this.mAdapter != null) {
                               ((UsersMemoryAdapter) UsersFragment.this.mAdapter).mergeData(users, req.fetchType == 1);
                                    }
                           UsersFragment.this.mNextPage = nextPage;
                           UsersFragment.this.mAnchor = anchor;
                                }
                                break;
                            default:
                       UsersFragment.this.mPendingRequestHelper.hideProgress(3);
                                break;
                        }
                    }

                    @Override 
           public void onFollowComplete(String reqId, int statusCode, String reasonPhrase, long userId) {
               PendingRequest req = UsersFragment.this.mPendingRequestHelper.removeRequest(reqId);
               if (req != null && statusCode != 200) {
                   UsersFragment.access$000(UsersFragment.this).removeFollowing(userId);
                   UsersFragment.this.mAdapter.notifyDataSetChanged();
                        }
                    }

                    @Override 
           public void onUnFollowComplete(String reqId, int statusCode, String reasonPhrase, long userId) {
               PendingRequest req = UsersFragment.this.mPendingRequestHelper.removeRequest(reqId);
               if (req != null && statusCode != 200) {
                   UsersFragment.access$000(UsersFragment.this).addFollowing(userId);
                   UsersFragment.this.mAdapter.notifyDataSetChanged();
                        }
                    }

                    @Override 
           public void onPhotoImageLoaded(HashMap<ImageKey, UrlImage> images) {
               if (UsersFragment.this.mAdapter != null) {
                   ((UsersMemoryAdapter) UsersFragment.this.mAdapter).setUserImages(images);
                        }
                    }

                    @Override 
           public void onCaptchaRequired(String reqId, String captchaUrl, PendingAction action) {
               PendingRequest req = UsersFragment.this.mPendingRequestHelper.removeRequest(reqId);
               if (req != null) {
                   UsersFragment.this.mPendingRequestHelper.onCaptchaRequired(UsersFragment.this.getActivity(), reqId, action.actionCode, action.bundle, captchaUrl);
                        }
                    }
                }
            }
