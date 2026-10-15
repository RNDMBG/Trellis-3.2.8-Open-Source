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
            
            
    public class UserSearchFragment extends BaseSearchFragment {
                private static final String EVENT_SOURCE_TITLE = "User Search Results";
                public static final String STATE_FRIENDSHIPS = "state_friendships";
                private static final String STATE_USERS = "state_users";
                private Friendships mFriendships;

        static  Friendships access$100(UserSearchFragment x0) {
            return x0.mFriendships;
                }

                @Override 
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
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
            if (this.mAdapter == null) {
                this.mAdapter = new UsersMemoryAdapter(getActivity(), this.mAppController, true, this, this.mFriendships);
                    }
            if (savedInstanceState != null && savedInstanceState.containsKey(STATE_USERS)) {
                ArrayList<VineUser> users = savedInstanceState.getParcelableArrayList(STATE_USERS);
                ((UsersMemoryAdapter) this.mAdapter).mergeData(users, false);
                    }
            this.mListView.setAdapter((ListAdapter) this.mAdapter);
            this.mListView.setDividerHeight(0);
            this.mAppSessionListener = new UserSearchSessionListener(this, null);
                }

                @Override 
        public int getMinimumSearchQueryLength() {
            return 2;
                }

                @Override 
        protected void onScrollLastItem() {
            if (this.mNextPage > 0 && this.mAdapter != null && this.mAdapter.getCount() <= 400) {
                fetchContent(1);
                    }
                }

                @Override 
        public void onSaveInstanceState(Bundle outState) {
            super.onSaveInstanceState(outState);
            if (this.mAdapter != null) {
                outState.putParcelableArrayList(STATE_USERS, ((UsersMemoryAdapter) this.mAdapter).getUsers());
                    }
                }

                @Override 
        protected void onListItemClick(ListView l, View v, int position, long id) {
                    if (id > 0) {
                ProfileActivity.start(getActivity(), id, EVENT_SOURCE_TITLE);
                    }
                }

                @Override 
        public void performSearch() {
            hideSearchHint();
            cleanSearchResults();
            fetchContent(3);
                }

                @Override 
       protected void fetchContent(int fetchType) {
           if (!hasPendingRequest(fetchType)) {
                        switch (fetchType) {
                            case 2:
                            case 3:
                            case 4:
                       this.mNextPage = 1;
                                break;
                        }
               String reqId = this.mAppController.searchUsers(this.mEncodedQueryString, this.mNextPage);
               if (reqId != null) {
                   showProgress(fetchType);
                   addRequest(reqId, fetchType);
                        }
                    }
                }

                
       class RunnableC03121 implements Runnable {
           RunnableC03121() {
                    }

                    @Override 
           public void run() {
               UserSearchFragment.this.mDbHelper.cleanUserSearchResults();
                    }
                }

                @Override 
       public void cleanSearchResults() {
           this.mExecutor.execute(new RunnableC03121());
                }

       private class UserSearchSessionListener extends AppSessionListener {
           private UserSearchSessionListener() {
                    }

            UserSearchSessionListener(UserSearchFragment x0, RunnableC03121 x1) {
               this();
                    }

                    @Override 
           public void onUserSearchComplete(String reqId, int statusCode, String reasonPhrase, int count, int nextPage, int previousPage, ArrayList<VineUser> users) {
               PendingRequest req = UserSearchFragment.this.removeRequest(reqId);
               if (req != null) {
                   UserSearchFragment.this.hideProgress(req.fetchType);
                            switch (statusCode) {
                                case 200:
                           ((UsersMemoryAdapter) UserSearchFragment.this.mAdapter).mergeData(users, req.fetchType == 1);
                           UserSearchFragment.this.mNextPage = nextPage;
                           if (UserSearchFragment.this.mAdapter.getCount() < 1) {
                               UserSearchFragment.this.showSadface(true, false);
                                    }
                                    break;
                                default:
                           if (TextUtils.isEmpty(reasonPhrase)) {
                               reasonPhrase = UserSearchFragment.this.getString(R.string.error_unknown);
                                    }
                           Util.showCenteredToast(UserSearchFragment.this.getActivity(), reasonPhrase);
                                    break;
                            }
                        }
                    }

                    @Override 
           public void onPhotoImageLoaded(HashMap<ImageKey, UrlImage> images) {
               ((UsersMemoryAdapter) UserSearchFragment.this.mAdapter).setUserImages(images);
                    }

                    @Override 
           public void onCaptchaRequired(String reqId, String captchaUrl, PendingAction action) {
               PendingRequest req = UserSearchFragment.this.removeRequest(reqId);
               if (req != null) {
                   UserSearchFragment.this.mPendingRequestHelper.onCaptchaRequired(UserSearchFragment.this.getActivity(), reqId, action.actionCode, action.bundle, captchaUrl);
                        }
                    }

                    @Override 
           public void onFollowComplete(String reqId, int statusCode, String reasonPhrase, long userId) {
               PendingRequest req = UserSearchFragment.this.mPendingRequestHelper.removeRequest(reqId);
               if (req != null && statusCode != 200) {
                   UserSearchFragment.access$100(UserSearchFragment.this).removeFollowing(userId);
                   UserSearchFragment.this.mAdapter.notifyDataSetChanged();
                        }
                    }

                    @Override 
           public void onUnFollowComplete(String reqId, int statusCode, String reasonPhrase, long userId) {
               PendingRequest req = UserSearchFragment.this.mPendingRequestHelper.removeRequest(reqId);
               if (req != null && statusCode != 200) {
                   UserSearchFragment.access$100(UserSearchFragment.this).addFollowing(userId);
                   UserSearchFragment.this.mAdapter.notifyDataSetChanged();
                        }
                    }
                }

                @Override 
       public void onClick(View view) {
                    FollowButtonViewHolder holder;
           if (view.getId() == 2131427917 && (holder = (FollowButtonViewHolder) view.getTag()) != null && this.mFriendships != null) {
               if (!holder.following) {
                   this.mPendingRequestHelper.addRequest(this.mAppController.followUser(this.mAppController.getActiveSession(), holder.userId, false));
                   this.mFriendships.addFollowing(holder.userId);
                   this.mAdapter.notifyDataSetChanged();
                        } else {
                   this.mPendingRequestHelper.addRequest(this.mAppController.unfollowUser(this.mAppController.getActiveSession(), holder.userId, false, this.mAppController.getActiveId()));
                   this.mFriendships.removeFollowing(holder.userId);
                   this.mAdapter.notifyDataSetChanged();
                        }
                    }
                }
            }
