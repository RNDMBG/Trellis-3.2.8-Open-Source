            package co.vine.android;

            import android.app.Activity;
            import android.content.ContentUris;
            import android.content.Intent;
            import android.database.Cursor;
            import android.net.Uri;
            import android.os.Bundle;
            import android.support.v4.content.CursorLoader;
            import android.support.v4.content.Loader;
            import android.text.TextUtils;
            import android.view.LayoutInflater;
            import android.view.View;
            import android.view.ViewGroup;
            import android.widget.AdapterView;
            import android.widget.Button;
            import android.widget.ListAdapter;
            import co.vine.android.api.VineRecipient;
            import co.vine.android.api.VineUser;
            import co.vine.android.provider.FriendsPickerExclusiveCursor;
            import co.vine.android.provider.Vine;
            import co.vine.android.provider.VineDatabaseSQL;
            import co.vine.android.util.StringAnchorManager;
            import co.vine.android.util.analytics.FlurryUtils;
            import com.edisonwang.android.slog.SLog;
            import com.twitter.android.widget.RefreshableListView;
            import java.util.ArrayList;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 37 */    public class FriendsRecipientPickerFragment extends BaseRecipientPickerFragment implements View.OnClickListener {
                private static final int LOADER_ID_FILTER_FRIENDS = 1;
                private StringAnchorManager mAnchorManager;
                private RecipientPickerActivity mCallback;
                private Button mFindFriendsCta;
                private FriendsPickerAdapter mFriendsPickerAdapter;
                private boolean mIsFetched;
                private boolean mIsFiltering;
                private boolean mIsLoaderReady;
                private String mLastFiredQuery;
                private long mRefreshingTime;

/* 38 */        static /* synthetic */ String access$002(FriendsRecipientPickerFragment x0, String x1) {
/* 39 */            x0.mLastFiredQuery = x1;
                    return x1;
                }


                @Override // android.support.v4.app.Fragment
/* 54 */        public void onAttach(Activity activity) {
/* 55 */            super.onAttach(activity);
/* 56 */            this.mCallback = (RecipientPickerActivity) activity;
                }

                @Override // co.vine.android.BaseCursorListFragment, android.support.v4.app.Fragment
/* 60 */        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
/* 61 */            return createView(inflater, C0257R.layout.recipient_friends_fragment, container);
                }

                @Override // co.vine.android.BaseRecipientPickerFragment, android.support.v4.app.Fragment
/* 65 */        public void onActivityCreated(Bundle savedInstanceState) {
/* 66 */            super.onActivityCreated(savedInstanceState);
/* 67 */            this.mAnchorManager = Vine.getUserSectionsAnchorManager(getActivity(), 0);
/* 69 */            this.mContentUri = ContentUris.withAppendedId(Vine.UserGroupsView.CONTENT_URI_FRIENDS, this.mAppController.getActiveId());
/* 71 */            this.mProjection = VineDatabaseSQL.UsersQuery.PROJECTION;
/* 73 */            refreshSelections();
/* 74 */            ((RefreshableListView) this.mListView).disablePTR();
/* 76 */            this.mSortOrder = Vine.UserGroupsView.SORT_ORDER_FRIEND_REFRESH_FIRST_THEN_FRIEND_INDEX;
/* 78 */            if (this.mCursorAdapter == null) {
/* 79 */                this.mFriendsPickerAdapter = new FriendsPickerAdapter(getActivity(), this.mAppController, 0, this);
/* 80 */                this.mCursorAdapter = this.mFriendsPickerAdapter;
                    }
/* 82 */            this.mListView.setAdapter((ListAdapter) this.mCursorAdapter);
/* 83 */            this.mListView.setChoiceMode(0);
                }

                @Override // co.vine.android.BaseCursorListFragment, android.support.v4.app.Fragment
/* 87 */        public void onViewCreated(View view, Bundle savedInstanceState) {
/* 88 */            super.onViewCreated(view, savedInstanceState);
/* 89 */            this.mFindFriendsCta = (Button) view.findViewById(C0257R.id.find_friends);
/* 90 */            this.mFindFriendsCta.setOnClickListener(this);
/* 91 */            this.mFindFriendsCta.bringToFront();
                }

/* 94 */        public void refreshSelections() {
/* 95 */            this.mRefreshingTime = this.mAnchorManager.getRefreshTime();
/* 96 */            SLog.m447d("Refreshing time: {}.", Long.valueOf(this.mRefreshingTime));
/* 97 */            this.mSelection = "section_type=0 AND last_section_refresh=?";
/* 99 */            this.mSelArgs = new String[]{String.valueOf(this.mRefreshingTime)};
                }

                @Override // android.view.View.OnClickListener
/* 103 */       public void onClick(View v) {
/* 104 */           if (v.getId() == 2131427664) {
/* 105 */               FlurryUtils.trackVisitFindFriends("VM Share");
/* 106 */               startActivity(new Intent(getActivity(), (Class<?>) FindFriendsActivity.class));
                    }
                }

                @Override // co.vine.android.BaseCursorListFragment
/* 111 */       protected void onScrollLastItem(Cursor cursor) {
/* 112 */           if (!this.mIsFiltering && this.mAnchorManager.haveMore()) {
/* 113 */               fetchContent(1);
/* 114 */               showProgress(7);
                    }
                }

                @Override // co.vine.android.BaseCursorListFragment
/* 119 */       public void refresh() {
/* 120 */           showProgress(3);
/* 121 */           handleFocus();
                }

                @Override // co.vine.android.BaseRecipientPickerFragment
/* 125 */       protected void handleFocus() {
/* 126 */           if (!this.mIsFetched) {
/* 127 */               showProgress(3);
/* 128 */               fetchContent(3);
                    }
/* 130 */           hideProgress(3);
                }

                @Override // co.vine.android.BaseRecipientPickerFragment
/* 134 */       public void onRecipientItemRemoved(VineRecipient recipient) {
/* 135 */           this.mFriendsPickerAdapter.onRecipientItemRemoved(recipient);
                }

                @Override // co.vine.android.BaseRecipientPickerFragment, co.vine.android.BaseCursorAdapterFragment, android.support.v4.app.LoaderManager.LoaderCallbacks
/* 139 */       public Loader<Cursor> onCreateLoader(int id, Bundle args) {
                    switch (id) {
                        case 1:
/* 142 */                   Uri.Builder contentUri = ContentUris.withAppendedId(Vine.UserGroupsView.CONTENT_URI_FRIENDS_FILTER, this.mAppController.getActiveId()).buildUpon();
/* 144 */                   contentUri.appendQueryParameter(Vine.UserGroupColumns.QUERY_FRIEND_FILTER, Uri.encode(this.mQueryString));
/* 145 */                   contentUri.appendQueryParameter(Vine.UserGroupColumns.QUERY_GROUP_TYPE, String.valueOf(10));
/* 147 */                   return new CursorLoader(getActivity(), contentUri.build(), this.mProjection, null, null, null);
                        default:
/* 151 */                   return super.onCreateLoader(id, args);
                    }
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // co.vine.android.BaseCursorAdapterFragment
/* 157 */       public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
/* 158 */           if (!this.mIsFiltering || loader == null) {
                        if (this.mIsFiltering || loader != null) {
/* 159 */                   if (!(cursor instanceof FriendsPickerExclusiveCursor)) {
/* 160 */                       cursor = new FriendsPickerExclusiveCursor(cursor);
                            }
/* 162 */                   ((FriendsPickerExclusiveCursor) cursor).prepare();
/* 163 */                   int count = cursor.getCount();
/* 164 */                   showSadface(count < 1);
/* 166 */                   super.onLoadFinished(loader, cursor);
                        }
                    }
                }

                @Override // co.vine.android.BaseCursorListFragment
/* 171 */       protected void showSadface(boolean show) {
/* 172 */           super.showSadface(show, show);
/* 173 */           showFindFriendsCta(show);
                }

/* 176 */       private void showFindFriendsCta(boolean show) {
/* 177 */           this.mFindFriendsCta.setVisibility(show ? 0 : 8);
                }

                @Override // android.widget.AdapterView.OnItemClickListener
/* 181 */       public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
/* 182 */           FriendsPickerViewHolder holder = (FriendsPickerViewHolder) view.getTag();
/* 183 */           if (isRecipientSelected(holder.recipient)) {
/* 184 */               removeRecipient(holder.recipient);
/* 185 */               holder.setSelected(getResources(), false);
                    } else {
/* 187 */               addRecipient(holder.recipient);
/* 188 */               holder.setSelected(getResources(), true);
                    }
                }

/* 192 */       private void fetchContent(int fetchType) {
                    int pageType;
/* 193 */           if (!hasPendingRequest(fetchType)) {
/* 197 */               SLog.m446d("Fetching content: " + fetchType);
                        switch (fetchType) {
                            case 1:
/* 210 */                       pageType = 3;
                                break;
                            case 2:
                            case 4:
/* 206 */                       pageType = 1;
                                break;
                            case 3:
/* 201 */                       pageType = 1;
                                break;
                            default:
/* 214 */                       pageType = 1;
                                break;
                        }
/* 219 */               String reqId = this.mAppController.fetchFriends(pageType, 0);
/* 220 */               if (reqId != null) {
/* 221 */                   showProgress(fetchType);
/* 222 */                   addRequest(reqId, fetchType);
                        }
                    }
                }

                @Override // co.vine.android.BaseRecipientPickerFragment
/* 227 */       protected void onGetFriendsComplete(int errorCode) {
/* 228 */           this.mIsFetched = true;
                    boolean hasThingsChanged = this.mRefreshingTime != this.mAnchorManager.getRefreshTime();
/* 230 */           SLog.m447d("Has refresh time changed? {}", Boolean.valueOf(hasThingsChanged));
/* 231 */           hideProgress(1);
/* 232 */           if (hasThingsChanged) {
/* 233 */               refreshSelections();
/* 234 */               this.mIsLoaderReady = true;
/* 235 */               initLoader(0);
                    } else if (!this.mIsLoaderReady) {
/* 238 */               this.mIsLoaderReady = true;
/* 239 */               initLoader(0);
                    }
                }

                @Override // co.vine.android.BaseRecipientPickerFragment
/* 245 */       public void onGetFriendsTypeAheadComplete(String query, ArrayList<VineUser> users) {
/* 246 */           if (users != null && query.equals(this.mLastFiredQuery)) {
/* 247 */               onLoadFinished((Loader<Cursor>) null, (Cursor) new FriendsPickerExclusiveCursor(users));
                    } else {
/* 249 */               Loader<Cursor> friendsLoader = getLoaderManager().getLoader(1);
/* 250 */               if (friendsLoader != null && friendsLoader.isStarted()) {
/* 251 */                   restartLoader(1);
                        } else {
/* 253 */                   initLoader(1);
                        }
                    }
/* 256 */           this.mLastFiredQuery = null;
                }

                @Override // co.vine.android.BaseRecipientPickerFragment
/* 261 */       protected void onTextChanged(CharSequence s, int start, int before, int count) {
/* 262 */           super.onTextChanged(s, start, before, count);
/* 263 */           String query = s.toString();
/* 264 */           if (!TextUtils.isEmpty(query)) {
/* 265 */               this.mIsFiltering = true;
/* 266 */               this.mHandler.postDelayed(new FilterRunnable(query), 150L);
                    } else {
/* 268 */               this.mIsFiltering = false;
/* 269 */               restartLoader(0);
                    }
                }

/* 272 */       private class FilterRunnable implements Runnable {
/* 273 */           public FilterRunnable(String query) {
/* 275 */               FriendsRecipientPickerFragment.this.mQueryString = query;
                    }

                    @Override // java.lang.Runnable
/* 279 */           public void run() {
/* 280 */               FriendsRecipientPickerFragment.access$002(FriendsRecipientPickerFragment.this, FriendsRecipientPickerFragment.this.mQueryString);
/* 281 */               FriendsRecipientPickerFragment.this.mAppController.fetchFriendsTypeAhead(FriendsRecipientPickerFragment.this.mQueryString);
                    }
                }
            }
