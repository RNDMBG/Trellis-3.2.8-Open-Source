            package co.vine.android;

            import android.content.Context;
            import android.os.Bundle;
            import android.text.TextUtils;
            import android.view.LayoutInflater;
            import android.view.View;
            import android.view.ViewGroup;
            import android.widget.CompoundButton;
            import android.widget.ListAdapter;
            import android.widget.ListView;
            import android.widget.TextView;
            import co.vine.android.api.VineUser;
            import co.vine.android.cache.image.ImageKey;
            import co.vine.android.cache.image.UrlImage;
            import co.vine.android.client.AppController;
            import co.vine.android.client.AppSessionListener;
            import co.vine.android.widget.UserViewHolder;
            import co.vine.android.widget.VineToggleButton;
            import java.lang.ref.WeakReference;
            import java.util.ArrayList;
            import java.util.HashMap;
            import java.util.HashSet;
            
            
    public class FavoritePeopleFragment extends BaseArrayListFragment implements CompoundButton.OnCheckedChangeListener {
                private static final int SECTION_ID_FAVORITES = 0;
                private static final int SECTION_ID_FOLLOWING = 2;
                private static final int SECTION_ID_SUGGESTIONS = 1;
                private static final int VIEW_TYPE_NORMAL = 0;
                private static final int VIEW_TYPE_SECTIONED = 1;
                private HashSet<Long> mFavoritedUserIds = new HashSet<>();
                private boolean mFetched;
                private String mStringAnchor;

        static  String access$102(FavoritePeopleFragment x0, String x1) {
            x0.mStringAnchor = x1;
                    return x1;
                }

        static  HashSet access$200(FavoritePeopleFragment x0) {
            return x0.mFavoritedUserIds;
                }

                @Override 
        public void onActivityCreated(Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);
            this.mAdapter = new FavoritePeopleAdapter(getActivity(), this.mAppController);
            this.mListView.setAdapter((ListAdapter) this.mAdapter);
            this.mListView.setDividerHeight(0);
            this.mAppSessionListener = new FavoritePeopleSessionListener(this, null);
                }

                @Override 
        public void onResume() {
            super.onResume();
            if (this.mAdapter.isEmpty() && !this.mFetched) {
                fetchContent(3);
                this.mFetched = true;
                    }
                }

                @Override 
        public void onScrollLastItem() {
            if (this.mRefreshable) {
                        if ((this.mNextPage > 0 || "1:0".equals(this.mStringAnchor)) && this.mAdapter.getCount() <= 400) {
                    fetchContent(1);
                        }
                    }
                }

                @Override 
        protected void refresh() {
            fetchContent(2);
                }

        private void fetchContent(int fetchType) {
            if (!this.mPendingRequestHelper.hasPendingRequest(fetchType)) {
                        switch (fetchType) {
                            case 2:
                            case 3:
                            case 4:
                        this.mNextPage = 1;
                                break;
                        }
                String reqId = this.mAppController.fetchFavoriteUsers(this.mNextPage, this.mStringAnchor);
                if (reqId != null) {
                    this.mPendingRequestHelper.showProgress(fetchType);
                    this.mPendingRequestHelper.addRequest(reqId, fetchType);
                        }
                    }
                }

                @Override 
       public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
           VineUser user = (VineUser) buttonView.getTag();
           if (user != null) {
               long userId = user.userId;
               this.mAppController.favoriteUser(userId, isChecked, user.isFollowing());
               if (isChecked) {
                   this.mFavoritedUserIds.add(Long.valueOf(userId));
                        } else {
                   this.mFavoritedUserIds.remove(Long.valueOf(userId));
                        }
                    }
                }

       private class FavoritePeopleSessionListener extends AppSessionListener {
           private FavoritePeopleSessionListener() {
                    }

            FavoritePeopleSessionListener(FavoritePeopleFragment x0, Object x1) {
               this();
                    }

                    @Override 
           public void onFetchFavoritePeopleComplete(String reqId, int statusCode, String reasonPhrase, ArrayList<VineUser> users, int nextPage, int prevPage, String anchor) {
                        switch (statusCode) {
                            case 200:
                       PendingRequest req = FavoritePeopleFragment.this.mPendingRequestHelper.removeRequest(reqId);
                       if (req != null) {
                           FavoritePeopleFragment.this.mPendingRequestHelper.hideProgress(req.fetchType);
                           if (FavoritePeopleFragment.this.mAdapter != null) {
                               ((UsersMemoryAdapter) FavoritePeopleFragment.this.mAdapter).mergeData(users, req.fetchType == 1);
                                    }
                           FavoritePeopleFragment.this.mNextPage = nextPage;
                           FavoritePeopleFragment.access$102(FavoritePeopleFragment.this, anchor);
                                }
                                break;
                            default:
                       FavoritePeopleFragment.this.mPendingRequestHelper.hideProgress(3);
                                break;
                        }
                    }

                    @Override 
           public void onPhotoImageLoaded(HashMap<ImageKey, UrlImage> images) {
               if (FavoritePeopleFragment.this.mAdapter != null) {
                   ((UsersMemoryAdapter) FavoritePeopleFragment.this.mAdapter).setUserImages(images);
                        }
                    }
                }

                @Override 
       protected void onListItemClick(ListView l, View v, int position, long id) {
                    if (id > 0) {
               ProfileActivity.start(getActivity(), id, "");
                    }
                }

       private class FavoritePeopleAdapter extends UsersMemoryAdapter {
           public FavoritePeopleAdapter(Context context, AppController appController) {
               super(context, appController, false, null, null);
                    }

                    @Override 
           public View newView(int position, ViewGroup parent) {
                        View view;
               LayoutInflater inflater = LayoutInflater.from(this.mContext);
                        switch (getItemViewType(position)) {
                            case 1:
                       view = inflater.inflate(C0257R.layout.user_row_favorites_sectioned_view, parent, false);
                                break;
                            default:
                       view = inflater.inflate(C0257R.layout.user_row_favorites_view, parent, false);
                                break;
                        }
               FavoriteUserViewHolder holder = FavoritePeopleFragment.this.new FavoriteUserViewHolder(view);
               this.mViewHolders.add(new WeakReference<>(holder));
               view.setTag(holder);
               return view;
                    }

                    @Override 
           public void bindView(View view, int position) {
               super.bindView(view, position);
               FavoriteUserViewHolder holder = (FavoriteUserViewHolder) view.getTag();
               VineUser user = this.mUsers.get(position);
               if (getItemViewType(position) == 1) {
                   holder.sectionTitle.setText(user.sectionTitle);
                        }
               if (user.sectionId == 0 || FavoritePeopleFragment.access$200(FavoritePeopleFragment.this).contains(Long.valueOf(user.userId))) {
                   holder.favorite.setCheckedWithoutEvent(true);
                        } else {
                   holder.favorite.setCheckedWithoutEvent(false);
                        }
               holder.favorite.setTag(user);
                    }

                    @Override 
           public int getViewTypeCount() {
               return 2;
                    }

                    @Override 
           public int getItemViewType(int position) {
               VineUser user = this.mUsers.get(position);
                        return (user == null || !shouldShowSectionTitle(user, position)) ? 0 : 1;
                    }

           private boolean shouldShowSectionTitle(VineUser thisUser, int position) {
               if (position > 0 && this.mUsers.size() > 0) {
                   VineUser previousUser = this.mUsers.get(position - 1);
                   if (previousUser.sectionId != thisUser.sectionId && !TextUtils.isEmpty(thisUser.sectionTitle)) {
                       return true;
                            }
                        }
                        return false;
                    }
                }

       private class FavoriteUserViewHolder extends UserViewHolder {
                    public VineToggleButton favorite;
                    public TextView sectionTitle;

           public FavoriteUserViewHolder(View view) {
               super(view);
               this.sectionTitle = (TextView) view.findViewById(C0257R.id.section_title);
               this.favorite = (VineToggleButton) view.findViewById(C0257R.id.favorite_button);
               this.favorite.setOnCheckedChangeListener(FavoritePeopleFragment.this);
                    }
                }
            }
