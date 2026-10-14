            package co.vine.android;

            import android.os.Bundle;
            import android.text.TextUtils;
            import android.view.View;
            import android.widget.ListAdapter;
            import android.widget.ListView;
            import co.vine.android.api.VineUser;
            import co.vine.android.cache.image.ImageKey;
            import co.vine.android.cache.image.UrlImage;
            import co.vine.android.client.AppSessionListener;
            import co.vine.android.service.PendingAction;
            import co.vine.android.util.Util;
            import java.util.ArrayList;
            import java.util.HashMap;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 21 */    public class UserSearchFragment extends BaseSearchFragment {
                private static final String EVENT_SOURCE_TITLE = "User Search Results";
                public static final String STATE_FRIENDSHIPS = "state_friendships";
                private static final String STATE_USERS = "state_users";
                private Friendships mFriendships;

/* 22 */        static /* synthetic */ Friendships access$100(UserSearchFragment x0) {
/* 23 */            return x0.mFriendships;
                }

                @Override // co.vine.android.BaseArrayListFragment, co.vine.android.BaseAdapterFragment, co.vine.android.BaseControllerFragment, android.support.v4.app.Fragment
/* 32 */        public void onCreate(Bundle savedInstanceState) {
/* 33 */            super.onCreate(savedInstanceState);
/* 34 */            if (savedInstanceState != null) {
/* 35 */                if (savedInstanceState.containsKey("state_friendships")) {
/* 36 */                    this.mFriendships = (Friendships) savedInstanceState.getParcelable("state_friendships");
                        }
/* 38 */                if (this.mFriendships == null) {
/* 39 */                    this.mFriendships = new Friendships();
/* 44 */                    return;
                        }
/* 44 */                return;
                    }
/* 42 */            this.mFriendships = new Friendships();
                }

                @Override // co.vine.android.BaseSearchFragment, android.support.v4.app.Fragment
/* 47 */        public void onActivityCreated(Bundle savedInstanceState) {
/* 48 */            super.onActivityCreated(savedInstanceState);
/* 50 */            if (this.mAdapter == null) {
/* 51 */                this.mAdapter = new UsersMemoryAdapter(getActivity(), this.mAppController, true, this, this.mFriendships);
                    }
/* 54 */            if (savedInstanceState != null && savedInstanceState.containsKey(STATE_USERS)) {
/* 56 */                ArrayList<VineUser> users = savedInstanceState.getParcelableArrayList(STATE_USERS);
/* 57 */                ((UsersMemoryAdapter) this.mAdapter).mergeData(users, false);
                    }
/* 61 */            this.mListView.setAdapter((ListAdapter) this.mAdapter);
/* 62 */            this.mListView.setDividerHeight(0);
/* 64 */            this.mAppSessionListener = new UserSearchSessionListener(this, null);
                }

                @Override // co.vine.android.BaseSearchFragment
/* 68 */        public int getMinimumSearchQueryLength() {
/* 69 */            return 2;
                }

                @Override // co.vine.android.BaseArrayListFragment
/* 73 */        protected void onScrollLastItem() {
/* 74 */            if (this.mNextPage > 0 && this.mAdapter != null && this.mAdapter.getCount() <= 400) {
/* 76 */                fetchContent(1);
                    }
                }

                @Override // co.vine.android.BaseArrayListFragment, co.vine.android.BaseAdapterFragment, android.support.v4.app.Fragment
/* 81 */        public void onSaveInstanceState(Bundle outState) {
/* 82 */            super.onSaveInstanceState(outState);
/* 83 */            if (this.mAdapter != null) {
/* 84 */                outState.putParcelableArrayList(STATE_USERS, ((UsersMemoryAdapter) this.mAdapter).getUsers());
                    }
                }

                @Override // co.vine.android.BaseArrayListFragment
/* 89 */        protected void onListItemClick(ListView l, View v, int position, long id) {
                    if (id > 0) {
/* 91 */                ProfileActivity.start(getActivity(), id, EVENT_SOURCE_TITLE);
                    }
                }

                @Override // co.vine.android.BaseSearchFragment
/* 96 */        public void performSearch() {
/* 97 */            hideSearchHint();
/* 98 */            cleanSearchResults();
/* 99 */            fetchContent(3);
                }

                @Override // co.vine.android.BaseSearchFragment
/* 108 */       protected void fetchContent(int fetchType) {
/* 109 */           if (!hasPendingRequest(fetchType)) {
                        switch (fetchType) {
                            case 2:
                            case 3:
                            case 4:
/* 117 */                       this.mNextPage = 1;
                                break;
                        }
/* 122 */               String reqId = this.mAppController.searchUsers(this.mEncodedQueryString, this.mNextPage);
/* 124 */               if (reqId != null) {
/* 125 */                   showProgress(fetchType);
/* 126 */                   addRequest(reqId, fetchType);
                        }
                    }
                }

                /* JADX INFO: renamed from: co.vine.android.UserSearchFragment$1 */
/* 130 */       class RunnableC03121 implements Runnable {
/* 131 */           RunnableC03121() {
                    }

                    @Override // java.lang.Runnable
/* 134 */           public void run() {
/* 135 */               UserSearchFragment.this.mDbHelper.cleanUserSearchResults();
                    }
                }

                @Override // co.vine.android.BaseSearchFragment
/* 131 */       public void cleanSearchResults() {
/* 132 */           this.mExecutor.execute(new RunnableC03121());
                }

/* 138 */       private class UserSearchSessionListener extends AppSessionListener {
/* 139 */           private UserSearchSessionListener() {
                    }

/* 139 */           /* synthetic */ UserSearchSessionListener(UserSearchFragment x0, RunnableC03121 x1) {
/* 140 */               this();
                    }

                    @Override // co.vine.android.client.AppSessionListener
/* 144 */           public void onUserSearchComplete(String reqId, int statusCode, String reasonPhrase, int count, int nextPage, int previousPage, ArrayList<VineUser> users) {
/* 145 */               PendingRequest req = UserSearchFragment.this.removeRequest(reqId);
/* 146 */               if (req != null) {
/* 147 */                   UserSearchFragment.this.hideProgress(req.fetchType);
                            switch (statusCode) {
                                case 200:
/* 150 */                           ((UsersMemoryAdapter) UserSearchFragment.this.mAdapter).mergeData(users, req.fetchType == 1);
/* 151 */                           UserSearchFragment.this.mNextPage = nextPage;
/* 152 */                           if (UserSearchFragment.this.mAdapter.getCount() < 1) {
/* 153 */                               UserSearchFragment.this.showSadface(true, false);
                                    }
                                    break;
                                default:
/* 158 */                           if (TextUtils.isEmpty(reasonPhrase)) {
/* 159 */                               reasonPhrase = UserSearchFragment.this.getString(C0257R.string.error_unknown);
                                    }
/* 161 */                           Util.showCenteredToast(UserSearchFragment.this.getActivity(), reasonPhrase);
                                    break;
                            }
                        }
                    }

                    @Override // co.vine.android.client.AppSessionListener
/* 169 */           public void onPhotoImageLoaded(HashMap<ImageKey, UrlImage> images) {
/* 170 */               ((UsersMemoryAdapter) UserSearchFragment.this.mAdapter).setUserImages(images);
                    }

                    @Override // co.vine.android.client.AppSessionListener
/* 174 */           public void onCaptchaRequired(String reqId, String captchaUrl, PendingAction action) {
/* 175 */               PendingRequest req = UserSearchFragment.this.removeRequest(reqId);
/* 176 */               if (req != null) {
/* 177 */                   UserSearchFragment.this.mPendingRequestHelper.onCaptchaRequired(UserSearchFragment.this.getActivity(), reqId, action.actionCode, action.bundle, captchaUrl);
                        }
                    }

                    @Override // co.vine.android.client.AppSessionListener
/* 182 */           public void onFollowComplete(String reqId, int statusCode, String reasonPhrase, long userId) {
/* 183 */               PendingRequest req = UserSearchFragment.this.mPendingRequestHelper.removeRequest(reqId);
/* 184 */               if (req != null && statusCode != 200) {
/* 186 */                   UserSearchFragment.access$100(UserSearchFragment.this).removeFollowing(userId);
/* 187 */                   UserSearchFragment.this.mAdapter.notifyDataSetChanged();
                        }
                    }

                    @Override // co.vine.android.client.AppSessionListener
/* 193 */           public void onUnFollowComplete(String reqId, int statusCode, String reasonPhrase, long userId) {
/* 194 */               PendingRequest req = UserSearchFragment.this.mPendingRequestHelper.removeRequest(reqId);
/* 195 */               if (req != null && statusCode != 200) {
/* 197 */                   UserSearchFragment.access$100(UserSearchFragment.this).addFollowing(userId);
/* 198 */                   UserSearchFragment.this.mAdapter.notifyDataSetChanged();
                        }
                    }
                }

                @Override // co.vine.android.BaseSearchFragment, android.view.View.OnClickListener
/* 205 */       public void onClick(View view) {
                    FollowButtonViewHolder holder;
/* 206 */           if (view.getId() == 2131427917 && (holder = (FollowButtonViewHolder) view.getTag()) != null && this.mFriendships != null) {
/* 209 */               if (!holder.following) {
/* 210 */                   this.mPendingRequestHelper.addRequest(this.mAppController.followUser(this.mAppController.getActiveSession(), holder.userId, false));
/* 212 */                   this.mFriendships.addFollowing(holder.userId);
/* 213 */                   this.mAdapter.notifyDataSetChanged();
                        } else {
/* 215 */                   this.mPendingRequestHelper.addRequest(this.mAppController.unfollowUser(this.mAppController.getActiveSession(), holder.userId, false, this.mAppController.getActiveId()));
/* 217 */                   this.mFriendships.removeFollowing(holder.userId);
/* 218 */                   this.mAdapter.notifyDataSetChanged();
                        }
                    }
                }
            }
