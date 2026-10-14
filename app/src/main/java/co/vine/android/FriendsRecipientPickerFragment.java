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
            
            
    public class FriendsRecipientPickerFragment extends BaseRecipientPickerFragment implements View.OnClickListener {
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

        static  String access$002(FriendsRecipientPickerFragment x0, String x1) {
            x0.mLastFiredQuery = x1;
                    return x1;
                }


                @Override 
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            this.mCallback = (RecipientPickerActivity) activity;
                }

                @Override 
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            return createView(inflater, C0257R.layout.recipient_friends_fragment, container);
                }

                @Override 
        public void onActivityCreated(Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);
            this.mAnchorManager = Vine.getUserSectionsAnchorManager(getActivity(), 0);
            this.mContentUri = ContentUris.withAppendedId(Vine.UserGroupsView.CONTENT_URI_FRIENDS, this.mAppController.getActiveId());
            this.mProjection = VineDatabaseSQL.UsersQuery.PROJECTION;
            refreshSelections();
            ((RefreshableListView) this.mListView).disablePTR();
            this.mSortOrder = Vine.UserGroupsView.SORT_ORDER_FRIEND_REFRESH_FIRST_THEN_FRIEND_INDEX;
            if (this.mCursorAdapter == null) {
                this.mFriendsPickerAdapter = new FriendsPickerAdapter(getActivity(), this.mAppController, 0, this);
                this.mCursorAdapter = this.mFriendsPickerAdapter;
                    }
            this.mListView.setAdapter((ListAdapter) this.mCursorAdapter);
            this.mListView.setChoiceMode(0);
                }

                @Override 
        public void onViewCreated(View view, Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
            this.mFindFriendsCta = (Button) view.findViewById(C0257R.id.find_friends);
            this.mFindFriendsCta.setOnClickListener(this);
            this.mFindFriendsCta.bringToFront();
                }

        public void refreshSelections() {
            this.mRefreshingTime = this.mAnchorManager.getRefreshTime();
            SLog.m447d("Refreshing time: {}.", Long.valueOf(this.mRefreshingTime));
            this.mSelection = "section_type=0 AND last_section_refresh=?";
            this.mSelArgs = new String[]{String.valueOf(this.mRefreshingTime)};
                }

                @Override 
       public void onClick(View v) {
           if (v.getId() == 2131427664) {
               FlurryUtils.trackVisitFindFriends("VM Share");
               startActivity(new Intent(getActivity(), (Class<?>) FindFriendsActivity.class));
                    }
                }

                @Override 
       protected void onScrollLastItem(Cursor cursor) {
           if (!this.mIsFiltering && this.mAnchorManager.haveMore()) {
               fetchContent(1);
               showProgress(7);
                    }
                }

                @Override 
       public void refresh() {
           showProgress(3);
           handleFocus();
                }

                @Override 
       protected void handleFocus() {
           if (!this.mIsFetched) {
               showProgress(3);
               fetchContent(3);
                    }
           hideProgress(3);
                }

                @Override 
       public void onRecipientItemRemoved(VineRecipient recipient) {
           this.mFriendsPickerAdapter.onRecipientItemRemoved(recipient);
                }

                @Override 
       public Loader<Cursor> onCreateLoader(int id, Bundle args) {
                    switch (id) {
                        case 1:
                   Uri.Builder contentUri = ContentUris.withAppendedId(Vine.UserGroupsView.CONTENT_URI_FRIENDS_FILTER, this.mAppController.getActiveId()).buildUpon();
                   contentUri.appendQueryParameter(Vine.UserGroupColumns.QUERY_FRIEND_FILTER, Uri.encode(this.mQueryString));
                   contentUri.appendQueryParameter(Vine.UserGroupColumns.QUERY_GROUP_TYPE, String.valueOf(10));
                   return new CursorLoader(getActivity(), contentUri.build(), this.mProjection, null, null, null);
                        default:
                   return super.onCreateLoader(id, args);
                    }
                }

                
                @Override 
       public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
           if (!this.mIsFiltering || loader == null) {
                        if (this.mIsFiltering || loader != null) {
                   if (!(cursor instanceof FriendsPickerExclusiveCursor)) {
                       cursor = new FriendsPickerExclusiveCursor(cursor);
                            }
                   ((FriendsPickerExclusiveCursor) cursor).prepare();
                   int count = cursor.getCount();
                   showSadface(count < 1);
                   super.onLoadFinished(loader, cursor);
                        }
                    }
                }

                @Override 
       protected void showSadface(boolean show) {
           super.showSadface(show, show);
           showFindFriendsCta(show);
                }

       private void showFindFriendsCta(boolean show) {
           this.mFindFriendsCta.setVisibility(show ? 0 : 8);
                }

                @Override 
       public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
           FriendsPickerViewHolder holder = (FriendsPickerViewHolder) view.getTag();
           if (isRecipientSelected(holder.recipient)) {
               removeRecipient(holder.recipient);
               holder.setSelected(getResources(), false);
                    } else {
               addRecipient(holder.recipient);
               holder.setSelected(getResources(), true);
                    }
                }

       private void fetchContent(int fetchType) {
                    int pageType;
           if (!hasPendingRequest(fetchType)) {
               SLog.m446d("Fetching content: " + fetchType);
                        switch (fetchType) {
                            case 1:
                       pageType = 3;
                                break;
                            case 2:
                            case 4:
                       pageType = 1;
                                break;
                            case 3:
                       pageType = 1;
                                break;
                            default:
                       pageType = 1;
                                break;
                        }
               String reqId = this.mAppController.fetchFriends(pageType, 0);
               if (reqId != null) {
                   showProgress(fetchType);
                   addRequest(reqId, fetchType);
                        }
                    }
                }

                @Override 
       protected void onGetFriendsComplete(int errorCode) {
           this.mIsFetched = true;
                    boolean hasThingsChanged = this.mRefreshingTime != this.mAnchorManager.getRefreshTime();
           SLog.m447d("Has refresh time changed? {}", Boolean.valueOf(hasThingsChanged));
           hideProgress(1);
           if (hasThingsChanged) {
               refreshSelections();
               this.mIsLoaderReady = true;
               initLoader(0);
                    } else if (!this.mIsLoaderReady) {
               this.mIsLoaderReady = true;
               initLoader(0);
                    }
                }

                @Override 
       public void onGetFriendsTypeAheadComplete(String query, ArrayList<VineUser> users) {
           if (users != null && query.equals(this.mLastFiredQuery)) {
               onLoadFinished((Loader<Cursor>) null, (Cursor) new FriendsPickerExclusiveCursor(users));
                    } else {
               Loader<Cursor> friendsLoader = getLoaderManager().getLoader(1);
               if (friendsLoader != null && friendsLoader.isStarted()) {
                   restartLoader(1);
                        } else {
                   initLoader(1);
                        }
                    }
           this.mLastFiredQuery = null;
                }

                @Override 
       protected void onTextChanged(CharSequence s, int start, int before, int count) {
           super.onTextChanged(s, start, before, count);
           String query = s.toString();
           if (!TextUtils.isEmpty(query)) {
               this.mIsFiltering = true;
               this.mHandler.postDelayed(new FilterRunnable(query), 150L);
                    } else {
               this.mIsFiltering = false;
               restartLoader(0);
                    }
                }

       private class FilterRunnable implements Runnable {
           public FilterRunnable(String query) {
               FriendsRecipientPickerFragment.this.mQueryString = query;
                    }

                    @Override 
           public void run() {
               FriendsRecipientPickerFragment.access$002(FriendsRecipientPickerFragment.this, FriendsRecipientPickerFragment.this.mQueryString);
               FriendsRecipientPickerFragment.this.mAppController.fetchFriendsTypeAhead(FriendsRecipientPickerFragment.this.mQueryString);
                    }
                }
            }
