            package co.vine.android;

            import android.content.Intent;
            import android.os.Bundle;
            import android.text.TextUtils;
            import android.view.View;
            import android.widget.ListAdapter;
            import android.widget.ListView;
            import co.vine.android.api.VineTag;
            import co.vine.android.client.AppSessionListener;
            import co.vine.android.provider.VineDatabaseSQL;
            import co.vine.android.util.Util;
            import co.vine.android.widget.TagViewHolder;
            import java.util.ArrayList;
            
            
    public class TagSearchFragment extends BaseSearchFragment {
                private static final String STATE_TAGS = "state_tags";

                @Override 
        public void onActivityCreated(Bundle savedInstanceState) {
                    ArrayList<VineTag> tags;
            super.onActivityCreated(savedInstanceState);
            if (this.mAdapter == null) {
                this.mProjection = VineDatabaseSQL.TagsQuery.PROJECTION;
                this.mSortOrder = "_id ASC";
                this.mAdapter = new TagsAdapter(getActivity(), this.mAppController);
                    }
            if (savedInstanceState != null && savedInstanceState.containsKey(STATE_TAGS) && (tags = savedInstanceState.getParcelableArrayList(STATE_TAGS)) != null) {
                ((TagsAdapter) this.mAdapter).mergeData(tags, false);
                    }
            this.mListView.setAdapter((ListAdapter) this.mAdapter);
            this.mAppSessionListener = new TagSearchSessionListener(this, null);
                }

                @Override 
        public int getMinimumSearchQueryLength() {
            return 3;
                }

                @Override 
        public void onSaveInstanceState(Bundle outState) {
            super.onSaveInstanceState(outState);
            if (this.mAdapter != null) {
                outState.putParcelableArrayList(STATE_TAGS, ((TagsAdapter) this.mAdapter).getTags());
                    }
                }

                @Override 
        protected void onScrollLastItem() {
            if (this.mNextPage > 0 && this.mAdapter.getCount() <= 400) {
                fetchContent(1);
                    }
                }

                @Override 
        protected void onListItemClick(ListView l, View v, int position, long id) {
            Object tag = v.getTag();
            if (tag instanceof TagViewHolder) {
                TagViewHolder holder = (TagViewHolder) tag;
                Intent intent = ExploreVideoListActivity.getIntent(getActivity(), "tag", holder.tagName);
                startActivity(intent);
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
               String reqId = this.mAppController.searchTags(this.mEncodedQueryString, this.mNextPage);
               if (reqId != null) {
                   showProgress(fetchType);
                   addRequest(reqId, fetchType);
                        }
                    }
                }

                
       class RunnableC03091 implements Runnable {
           RunnableC03091() {
                    }

                    @Override 
           public void run() {
               TagSearchFragment.this.mDbHelper.cleanTagSearchResults();
                    }
                }

                @Override 
       public void cleanSearchResults() {
           this.mExecutor.execute(new RunnableC03091());
                }

       private class TagSearchSessionListener extends AppSessionListener {
           private TagSearchSessionListener() {
                    }

            TagSearchSessionListener(TagSearchFragment x0, RunnableC03091 x1) {
               this();
                    }

                    @Override 
           public void onTagSearchComplete(String reqId, int statusCode, String reasonPhrase, int count, int nextPage, int previousPage, ArrayList<VineTag> tags) {
               PendingRequest req = TagSearchFragment.this.removeRequest(reqId);
               if (req != null) {
                   TagSearchFragment.this.hideProgress(req.fetchType);
                            switch (statusCode) {
                                case 200:
                           ((TagsAdapter) TagSearchFragment.this.mAdapter).mergeData(tags, req.fetchType == 1);
                           TagSearchFragment.this.mNextPage = nextPage;
                           if (TagSearchFragment.this.mAdapter.getCount() < 1) {
                               TagSearchFragment.this.showSadface(true, false);
                                    }
                                    break;
                                default:
                           if (TextUtils.isEmpty(reasonPhrase)) {
                               reasonPhrase = TagSearchFragment.this.getString(R.string.error_unknown);
                                    }
                           Util.showCenteredToast(TagSearchFragment.this.getActivity(), reasonPhrase);
                                    break;
                            }
                        }
                    }
                }
            }
