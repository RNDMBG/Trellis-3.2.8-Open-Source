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
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 28 */    public class FavoritePeopleFragment extends BaseArrayListFragment implements CompoundButton.OnCheckedChangeListener {
                private static final int SECTION_ID_FAVORITES = 0;
                private static final int SECTION_ID_FOLLOWING = 2;
                private static final int SECTION_ID_SUGGESTIONS = 1;
                private static final int VIEW_TYPE_NORMAL = 0;
                private static final int VIEW_TYPE_SECTIONED = 1;
                private HashSet<Long> mFavoritedUserIds = new HashSet<>();
                private boolean mFetched;
                private String mStringAnchor;

/* 29 */        static /* synthetic */ String access$102(FavoritePeopleFragment x0, String x1) {
/* 30 */            x0.mStringAnchor = x1;
                    return x1;
                }

/* 29 */        static /* synthetic */ HashSet access$200(FavoritePeopleFragment x0) {
/* 30 */            return x0.mFavoritedUserIds;
                }

                @Override // android.support.v4.app.Fragment
/* 45 */        public void onActivityCreated(Bundle savedInstanceState) {
/* 46 */            super.onActivityCreated(savedInstanceState);
/* 47 */            this.mAdapter = new FavoritePeopleAdapter(getActivity(), this.mAppController);
/* 48 */            this.mListView.setAdapter((ListAdapter) this.mAdapter);
/* 49 */            this.mListView.setDividerHeight(0);
/* 50 */            this.mAppSessionListener = new FavoritePeopleSessionListener(this, null);
                }

                @Override // co.vine.android.BaseArrayListFragment, co.vine.android.BaseAdapterFragment, co.vine.android.BaseControllerFragment, co.vine.android.BaseFragment, android.support.v4.app.Fragment
/* 54 */        public void onResume() {
/* 55 */            super.onResume();
/* 56 */            if (this.mAdapter.isEmpty() && !this.mFetched) {
/* 57 */                fetchContent(3);
/* 58 */                this.mFetched = true;
                    }
                }

                @Override // co.vine.android.BaseArrayListFragment
/* 63 */        public void onScrollLastItem() {
/* 64 */            if (this.mRefreshable) {
                        if ((this.mNextPage > 0 || "1:0".equals(this.mStringAnchor)) && this.mAdapter.getCount() <= 400) {
/* 66 */                    fetchContent(1);
                        }
                    }
                }

                @Override // co.vine.android.BaseArrayListFragment
/* 71 */        protected void refresh() {
/* 72 */            fetchContent(2);
                }

/* 75 */        private void fetchContent(int fetchType) {
/* 76 */            if (!this.mPendingRequestHelper.hasPendingRequest(fetchType)) {
                        switch (fetchType) {
                            case 2:
                            case 3:
                            case 4:
/* 84 */                        this.mNextPage = 1;
                                break;
                        }
/* 93 */                String reqId = this.mAppController.fetchFavoriteUsers(this.mNextPage, this.mStringAnchor);
/* 94 */                if (reqId != null) {
/* 95 */                    this.mPendingRequestHelper.showProgress(fetchType);
/* 96 */                    this.mPendingRequestHelper.addRequest(reqId, fetchType);
                        }
                    }
                }

                @Override // android.widget.CompoundButton.OnCheckedChangeListener
/* 101 */       public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
/* 102 */           VineUser user = (VineUser) buttonView.getTag();
/* 103 */           if (user != null) {
/* 104 */               long userId = user.userId;
/* 105 */               this.mAppController.favoriteUser(userId, isChecked, user.isFollowing());
/* 106 */               if (isChecked) {
/* 107 */                   this.mFavoritedUserIds.add(Long.valueOf(userId));
                        } else {
/* 109 */                   this.mFavoritedUserIds.remove(Long.valueOf(userId));
                        }
                    }
                }

/* 112 */       private class FavoritePeopleSessionListener extends AppSessionListener {
/* 113 */           private FavoritePeopleSessionListener() {
                    }

/* 113 */           /* synthetic */ FavoritePeopleSessionListener(FavoritePeopleFragment x0, Object x1) {
/* 114 */               this();
                    }

                    @Override // co.vine.android.client.AppSessionListener
/* 118 */           public void onFetchFavoritePeopleComplete(String reqId, int statusCode, String reasonPhrase, ArrayList<VineUser> users, int nextPage, int prevPage, String anchor) {
                        switch (statusCode) {
                            case 200:
/* 121 */                       PendingRequest req = FavoritePeopleFragment.this.mPendingRequestHelper.removeRequest(reqId);
/* 122 */                       if (req != null) {
/* 123 */                           FavoritePeopleFragment.this.mPendingRequestHelper.hideProgress(req.fetchType);
/* 124 */                           if (FavoritePeopleFragment.this.mAdapter != null) {
/* 125 */                               ((UsersMemoryAdapter) FavoritePeopleFragment.this.mAdapter).mergeData(users, req.fetchType == 1);
                                    }
/* 128 */                           FavoritePeopleFragment.this.mNextPage = nextPage;
/* 129 */                           FavoritePeopleFragment.access$102(FavoritePeopleFragment.this, anchor);
                                }
                                break;
                            default:
/* 134 */                       FavoritePeopleFragment.this.mPendingRequestHelper.hideProgress(3);
                                break;
                        }
                    }

                    @Override // co.vine.android.client.AppSessionListener
/* 140 */           public void onPhotoImageLoaded(HashMap<ImageKey, UrlImage> images) {
/* 141 */               if (FavoritePeopleFragment.this.mAdapter != null) {
/* 142 */                   ((UsersMemoryAdapter) FavoritePeopleFragment.this.mAdapter).setUserImages(images);
                        }
                    }
                }

                @Override // co.vine.android.BaseArrayListFragment
/* 148 */       protected void onListItemClick(ListView l, View v, int position, long id) {
                    if (id > 0) {
/* 150 */               ProfileActivity.start(getActivity(), id, "");
                    }
                }

/* 154 */       private class FavoritePeopleAdapter extends UsersMemoryAdapter {
/* 155 */           public FavoritePeopleAdapter(Context context, AppController appController) {
/* 157 */               super(context, appController, false, null, null);
                    }

                    @Override // co.vine.android.UsersMemoryAdapter
/* 161 */           public View newView(int position, ViewGroup parent) {
                        View view;
/* 162 */               LayoutInflater inflater = LayoutInflater.from(this.mContext);
                        switch (getItemViewType(position)) {
                            case 1:
/* 166 */                       view = inflater.inflate(C0257R.layout.user_row_favorites_sectioned_view, parent, false);
                                break;
                            default:
/* 171 */                       view = inflater.inflate(C0257R.layout.user_row_favorites_view, parent, false);
                                break;
                        }
/* 175 */               FavoriteUserViewHolder holder = FavoritePeopleFragment.this.new FavoriteUserViewHolder(view);
/* 176 */               this.mViewHolders.add(new WeakReference<>(holder));
/* 177 */               view.setTag(holder);
/* 179 */               return view;
                    }

                    @Override // co.vine.android.UsersMemoryAdapter
/* 183 */           public void bindView(View view, int position) {
/* 184 */               super.bindView(view, position);
/* 185 */               FavoriteUserViewHolder holder = (FavoriteUserViewHolder) view.getTag();
/* 186 */               VineUser user = this.mUsers.get(position);
/* 188 */               if (getItemViewType(position) == 1) {
/* 189 */                   holder.sectionTitle.setText(user.sectionTitle);
                        }
/* 192 */               if (user.sectionId == 0 || FavoritePeopleFragment.access$200(FavoritePeopleFragment.this).contains(Long.valueOf(user.userId))) {
/* 194 */                   holder.favorite.setCheckedWithoutEvent(true);
                        } else {
/* 196 */                   holder.favorite.setCheckedWithoutEvent(false);
                        }
/* 199 */               holder.favorite.setTag(user);
                    }

                    @Override // android.widget.BaseAdapter, android.widget.Adapter
/* 203 */           public int getViewTypeCount() {
/* 204 */               return 2;
                    }

                    @Override // android.widget.BaseAdapter, android.widget.Adapter
/* 208 */           public int getItemViewType(int position) {
/* 209 */               VineUser user = this.mUsers.get(position);
                        return (user == null || !shouldShowSectionTitle(user, position)) ? 0 : 1;
                    }

/* 217 */           private boolean shouldShowSectionTitle(VineUser thisUser, int position) {
/* 218 */               if (position > 0 && this.mUsers.size() > 0) {
/* 219 */                   VineUser previousUser = this.mUsers.get(position - 1);
/* 220 */                   if (previousUser.sectionId != thisUser.sectionId && !TextUtils.isEmpty(thisUser.sectionTitle)) {
/* 221 */                       return true;
                            }
                        }
                        return false;
                    }
                }

/* 230 */       private class FavoriteUserViewHolder extends UserViewHolder {
                    public VineToggleButton favorite;
                    public TextView sectionTitle;

/* 231 */           public FavoriteUserViewHolder(View view) {
/* 233 */               super(view);
/* 234 */               this.sectionTitle = (TextView) view.findViewById(C0257R.id.section_title);
/* 235 */               this.favorite = (VineToggleButton) view.findViewById(C0257R.id.favorite_button);
/* 236 */               this.favorite.setOnCheckedChangeListener(FavoritePeopleFragment.this);
                    }
                }
            }
