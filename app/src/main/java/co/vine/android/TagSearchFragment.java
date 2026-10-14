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
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 27 */    public class TagSearchFragment extends BaseSearchFragment {
                private static final String STATE_TAGS = "state_tags";

                @Override // co.vine.android.BaseSearchFragment, android.support.v4.app.Fragment
/* 28 */        public void onActivityCreated(Bundle savedInstanceState) {
                    ArrayList<VineTag> tags;
/* 29 */            super.onActivityCreated(savedInstanceState);
/* 30 */            if (this.mAdapter == null) {
/* 31 */                this.mProjection = VineDatabaseSQL.TagsQuery.PROJECTION;
/* 32 */                this.mSortOrder = "_id ASC";
/* 33 */                this.mAdapter = new TagsAdapter(getActivity(), this.mAppController);
                    }
/* 36 */            if (savedInstanceState != null && savedInstanceState.containsKey(STATE_TAGS) && (tags = savedInstanceState.getParcelableArrayList(STATE_TAGS)) != null) {
/* 40 */                ((TagsAdapter) this.mAdapter).mergeData(tags, false);
                    }
/* 45 */            this.mListView.setAdapter((ListAdapter) this.mAdapter);
/* 46 */            this.mAppSessionListener = new TagSearchSessionListener(this, null);
                }

                @Override // co.vine.android.BaseSearchFragment
/* 50 */        public int getMinimumSearchQueryLength() {
/* 51 */            return 3;
                }

                @Override // co.vine.android.BaseArrayListFragment, co.vine.android.BaseAdapterFragment, android.support.v4.app.Fragment
/* 54 */        public void onSaveInstanceState(Bundle outState) {
/* 55 */            super.onSaveInstanceState(outState);
/* 56 */            if (this.mAdapter != null) {
/* 57 */                outState.putParcelableArrayList(STATE_TAGS, ((TagsAdapter) this.mAdapter).getTags());
                    }
                }

                @Override // co.vine.android.BaseArrayListFragment
/* 62 */        protected void onScrollLastItem() {
/* 63 */            if (this.mNextPage > 0 && this.mAdapter.getCount() <= 400) {
/* 65 */                fetchContent(1);
                    }
                }

                @Override // co.vine.android.BaseArrayListFragment
/* 70 */        protected void onListItemClick(ListView l, View v, int position, long id) {
/* 71 */            Object tag = v.getTag();
/* 72 */            if (tag instanceof TagViewHolder) {
/* 73 */                TagViewHolder holder = (TagViewHolder) tag;
/* 74 */                Intent intent = ExploreVideoListActivity.getIntent(getActivity(), "tag", holder.tagName);
/* 76 */                startActivity(intent);
                    }
                }

                @Override // co.vine.android.BaseSearchFragment
/* 81 */        public void performSearch() {
/* 82 */            hideSearchHint();
/* 83 */            cleanSearchResults();
/* 84 */            fetchContent(3);
                }

                @Override // co.vine.android.BaseSearchFragment
/* 92 */        protected void fetchContent(int fetchType) {
/* 93 */            if (!hasPendingRequest(fetchType)) {
                        switch (fetchType) {
                            case 2:
                            case 3:
                            case 4:
/* 101 */                       this.mNextPage = 1;
                                break;
                        }
/* 106 */               String reqId = this.mAppController.searchTags(this.mEncodedQueryString, this.mNextPage);
/* 108 */               if (reqId != null) {
/* 109 */                   showProgress(fetchType);
/* 110 */                   addRequest(reqId, fetchType);
                        }
                    }
                }

                /* JADX INFO: renamed from: co.vine.android.TagSearchFragment$1 */
/* 114 */       class RunnableC03091 implements Runnable {
/* 115 */           RunnableC03091() {
                    }

                    @Override // java.lang.Runnable
/* 118 */           public void run() {
/* 119 */               TagSearchFragment.this.mDbHelper.cleanTagSearchResults();
                    }
                }

                @Override // co.vine.android.BaseSearchFragment
/* 115 */       public void cleanSearchResults() {
/* 116 */           this.mExecutor.execute(new RunnableC03091());
                }

/* 122 */       private class TagSearchSessionListener extends AppSessionListener {
/* 123 */           private TagSearchSessionListener() {
                    }

/* 123 */           /* synthetic */ TagSearchSessionListener(TagSearchFragment x0, RunnableC03091 x1) {
/* 124 */               this();
                    }

                    @Override // co.vine.android.client.AppSessionListener
/* 128 */           public void onTagSearchComplete(String reqId, int statusCode, String reasonPhrase, int count, int nextPage, int previousPage, ArrayList<VineTag> tags) {
/* 129 */               PendingRequest req = TagSearchFragment.this.removeRequest(reqId);
/* 130 */               if (req != null) {
/* 131 */                   TagSearchFragment.this.hideProgress(req.fetchType);
                            switch (statusCode) {
                                case 200:
/* 134 */                           ((TagsAdapter) TagSearchFragment.this.mAdapter).mergeData(tags, req.fetchType == 1);
/* 135 */                           TagSearchFragment.this.mNextPage = nextPage;
/* 136 */                           if (TagSearchFragment.this.mAdapter.getCount() < 1) {
/* 137 */                               TagSearchFragment.this.showSadface(true, false);
                                    }
                                    break;
                                default:
/* 142 */                           if (TextUtils.isEmpty(reasonPhrase)) {
/* 143 */                               reasonPhrase = TagSearchFragment.this.getString(C0257R.string.error_unknown);
                                    }
/* 145 */                           Util.showCenteredToast(TagSearchFragment.this.getActivity(), reasonPhrase);
                                    break;
                            }
                        }
                    }
                }
            }
