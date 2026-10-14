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
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 21 */    public class UsersFragment extends BaseArrayListFragment implements View.OnClickListener {
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

/* 22 */        static /* synthetic */ Friendships access$000(UsersFragment x0) {
/* 23 */            return x0.mFriendships;
                }

                @Override // co.vine.android.BaseArrayListFragment, co.vine.android.BaseAdapterFragment, co.vine.android.BaseControllerFragment, android.support.v4.app.Fragment
/* 42 */        public void onCreate(Bundle savedInstanceState) {
/* 43 */            super.onCreate(savedInstanceState);
/* 44 */            Bundle args = getArguments();
/* 45 */            this.mUsersType = args.getInt("u_type", -1);
/* 46 */            this.mProfileId = args.getLong("p_id");
/* 47 */            this.mPostId = args.getLong("post_id", -1L);
/* 48 */            this.mNotificationId = args.getLong("notification_id", -1L);
/* 50 */            this.mAnchorArgument = args.getLong("anchor", 0L);
/* 52 */            if (savedInstanceState != null) {
/* 53 */                if (savedInstanceState.containsKey("state_friendships")) {
/* 54 */                    this.mFriendships = (Friendships) savedInstanceState.getParcelable("state_friendships");
                        }
/* 56 */                if (this.mFriendships == null) {
/* 57 */                    this.mFriendships = new Friendships();
/* 62 */                    return;
                        }
/* 62 */                return;
                    }
/* 60 */            this.mFriendships = new Friendships();
                }

                @Override // android.support.v4.app.Fragment
/* 65 */        public void onActivityCreated(Bundle savedInstanceState) {
/* 66 */            super.onActivityCreated(savedInstanceState);
/* 67 */            this.mAdapter = new UsersMemoryAdapter(getActivity(), this.mAppController, true, this, this.mFriendships);
/* 69 */            this.mListView.setAdapter((ListAdapter) this.mAdapter);
/* 70 */            this.mListView.setDividerHeight(0);
/* 71 */            this.mAppSessionListener = new UsersAppSessionListener();
                }

                @Override // co.vine.android.BaseArrayListFragment, co.vine.android.BaseAdapterFragment, co.vine.android.BaseControllerFragment, co.vine.android.BaseFragment, android.support.v4.app.Fragment
/* 75 */        public void onResume() {
/* 76 */            super.onResume();
/* 77 */            if (this.mAdapter.isEmpty() && !this.mFetched) {
/* 78 */                fetchContent(3);
                    }
                }

                @Override // co.vine.android.BaseArrayListFragment
/* 83 */        public void onScrollLastItem() {
/* 84 */            if (this.mRefreshable && this.mNextPage > 0 && this.mAdapter.getCount() <= 400) {
/* 86 */                fetchContent(1);
                    }
                }

                @Override // co.vine.android.BaseArrayListFragment, co.vine.android.BaseAdapterFragment, android.support.v4.app.Fragment
/* 90 */        public void onSaveInstanceState(Bundle outState) {
/* 91 */            super.onSaveInstanceState(outState);
/* 92 */            outState.putParcelable("state_friendships", this.mFriendships);
                }

                @Override // co.vine.android.BaseArrayListFragment
/* 96 */        public void onListItemClick(ListView l, View v, int position, long id) {
                    if (id > 0) {
/* 98 */                ProfileActivity.start(getActivity(), id, "");
                    }
                }

                @Override // co.vine.android.BaseArrayListFragment
/* 103 */       protected void refresh() {
/* 104 */           fetchContent(2);
                }

/* 107 */       private void fetchContent(int fetchType) {
/* 108 */           if (!this.mPendingRequestHelper.hasPendingRequest(fetchType)) {
/* 111 */               String reqId = null;
                        switch (fetchType) {
                            case 2:
                            case 3:
                            case 4:
/* 118 */                       this.mNextPage = 1;
/* 121 */                       this.mAnchor = this.mAnchorArgument;
                                break;
                        }
                        switch (this.mUsersType) {
                            case 1:
/* 132 */                       reqId = this.mAppController.fetchFollowing(this.mProfileId, this.mNextPage, this.mAnchor);
                                break;
                            case 2:
/* 136 */                       reqId = this.mAppController.fetchFollowers(this.mProfileId, this.mNextPage, this.mAnchor);
                                break;
                            case 5:
/* 140 */                       reqId = this.mAppController.fetchPostLikers(this.mAppController.getActiveSession(), this.mPostId, this.mNextPage, this.mAnchor);
                                break;
                            case 9:
/* 145 */                       reqId = this.mAppController.fetchReviners(this.mAppController.getActiveSession(), this.mPostId, this.mNextPage, this.mAnchor);
                                break;
                            case 12:
/* 150 */                       reqId = this.mAppController.fetchNotificationUsers(this.mAppController.getActiveSession(), this.mNotificationId, this.mNextPage, this.mAnchor);
                                break;
                        }
/* 159 */               if (reqId != null) {
/* 160 */                   this.mPendingRequestHelper.showProgress(fetchType);
/* 161 */                   this.mPendingRequestHelper.addRequest(reqId, fetchType);
                        }
                    }
                }

                @Override // android.view.View.OnClickListener
/* 166 */       public void onClick(View view) {
                    FollowButtonViewHolder holder;
/* 167 */           if (view.getId() == 2131427917 && (holder = (FollowButtonViewHolder) view.getTag()) != null && this.mFriendships != null) {
/* 170 */               if (!holder.following) {
/* 171 */                   this.mPendingRequestHelper.addRequest(this.mAppController.followUser(this.mAppController.getActiveSession(), holder.userId, false));
/* 173 */                   this.mFriendships.addFollowing(holder.userId);
/* 174 */                   this.mAdapter.notifyDataSetChanged();
                        } else {
/* 176 */                   this.mPendingRequestHelper.addRequest(this.mAppController.unfollowUser(this.mAppController.getActiveSession(), holder.userId, false, this.mProfileId));
/* 178 */                   this.mFriendships.removeFollowing(holder.userId);
/* 179 */                   this.mAdapter.notifyDataSetChanged();
                        }
                    }
                }

/* 186 */       class UsersAppSessionListener extends AppSessionListener {
/* 187 */           UsersAppSessionListener() {
                    }

                    @Override // co.vine.android.client.AppSessionListener
/* 193 */           public void onGetUsersComplete(Session session, String reqId, int errorCode, String reasonPhrase, int count, ArrayList<VineUser> users, int nextPage, int prevPage, long anchor) {
                        switch (errorCode) {
                            case 200:
/* 196 */                       PendingRequest req = UsersFragment.this.mPendingRequestHelper.removeRequest(reqId);
/* 197 */                       if (req != null) {
/* 198 */                           UsersFragment.this.mPendingRequestHelper.hideProgress(req.fetchType);
/* 199 */                           if (UsersFragment.this.mAdapter != null) {
/* 200 */                               ((UsersMemoryAdapter) UsersFragment.this.mAdapter).mergeData(users, req.fetchType == 1);
                                    }
/* 202 */                           UsersFragment.this.mNextPage = nextPage;
/* 203 */                           UsersFragment.this.mAnchor = anchor;
                                }
                                break;
                            default:
/* 208 */                       UsersFragment.this.mPendingRequestHelper.hideProgress(3);
                                break;
                        }
                    }

                    @Override // co.vine.android.client.AppSessionListener
/* 215 */           public void onFollowComplete(String reqId, int statusCode, String reasonPhrase, long userId) {
/* 216 */               PendingRequest req = UsersFragment.this.mPendingRequestHelper.removeRequest(reqId);
/* 217 */               if (req != null && statusCode != 200) {
/* 219 */                   UsersFragment.access$000(UsersFragment.this).removeFollowing(userId);
/* 220 */                   UsersFragment.this.mAdapter.notifyDataSetChanged();
                        }
                    }

                    @Override // co.vine.android.client.AppSessionListener
/* 226 */           public void onUnFollowComplete(String reqId, int statusCode, String reasonPhrase, long userId) {
/* 227 */               PendingRequest req = UsersFragment.this.mPendingRequestHelper.removeRequest(reqId);
/* 228 */               if (req != null && statusCode != 200) {
/* 230 */                   UsersFragment.access$000(UsersFragment.this).addFollowing(userId);
/* 231 */                   UsersFragment.this.mAdapter.notifyDataSetChanged();
                        }
                    }

                    @Override // co.vine.android.client.AppSessionListener
/* 237 */           public void onPhotoImageLoaded(HashMap<ImageKey, UrlImage> images) {
/* 238 */               if (UsersFragment.this.mAdapter != null) {
/* 239 */                   ((UsersMemoryAdapter) UsersFragment.this.mAdapter).setUserImages(images);
                        }
                    }

                    @Override // co.vine.android.client.AppSessionListener
/* 244 */           public void onCaptchaRequired(String reqId, String captchaUrl, PendingAction action) {
/* 245 */               PendingRequest req = UsersFragment.this.mPendingRequestHelper.removeRequest(reqId);
/* 246 */               if (req != null) {
/* 247 */                   UsersFragment.this.mPendingRequestHelper.onCaptchaRequired(UsersFragment.this.getActivity(), reqId, action.actionCode, action.bundle, captchaUrl);
                        }
                    }
                }
            }
