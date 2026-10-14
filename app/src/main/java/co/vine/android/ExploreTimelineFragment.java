            package co.vine.android;

            import android.content.Intent;
            import android.net.Uri;
            import android.os.Bundle;
            import android.widget.ListAdapter;
            import co.vine.android.network.UrlCachePolicy;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 36 */    public class ExploreTimelineFragment extends BaseTimelineFragment {
                public static final String ARG_CATEGORY = "category";
                public static final String ARG_DATA = "data";
                public static final String ARG_TAG = "tag";
                public static final String CATEGORY_ON_THE_RISE = "trending-people";
                public static final String CATEGORY_POPULAR_NOW = "popular-now";
                public static final String CATEGORY_TAG = "tag";
                public static final String CATEGORY_TIMELINES = "timelines";
                private static final String EVENT_SOURCE_TITLE = "Explore Timeline: ";
                private static final String STATE_CATEGORY = "category";
                private static final String STATE_DATA = "data";
                private static final String STATE_TAG = "tag";
                private String mCategory;
                private Uri mData;
                private String mTag;

                @Override // co.vine.android.BaseTimelineFragment, co.vine.android.BaseArrayListFragment, co.vine.android.BaseAdapterFragment, co.vine.android.BaseControllerFragment, android.support.v4.app.Fragment
/* 37 */        public void onCreate(Bundle savedInstanceState) {
/* 38 */            super.onCreate(savedInstanceState);
/* 39 */            Bundle b = getArguments();
/* 41 */            this.mCategory = b.getString("category");
/* 42 */            this.mData = (Uri) b.getParcelable("data");
/* 43 */            this.mTag = b.getString("tag");
/* 45 */            setFlurryEventSource(EVENT_SOURCE_TITLE + this.mCategory);
/* 46 */            setFocused(true);
                }

                @Override // co.vine.android.BaseTimelineFragment, android.support.v4.app.Fragment
/* 50 */        public void onActivityCreated(Bundle savedInstanceState) {
/* 51 */            super.onActivityCreated(savedInstanceState);
/* 52 */            this.mListView.setAdapter((ListAdapter) this.mFeedAdapter);
                }

                @Override // co.vine.android.BaseTimelineFragment, co.vine.android.BaseArrayListFragment, co.vine.android.BaseAdapterFragment, co.vine.android.BaseControllerFragment, co.vine.android.BaseFragment, android.support.v4.app.Fragment
/* 56 */        public void onResume() {
/* 57 */            super.onResume();
/* 58 */            this.mFeedAdapter.onResume(isFocused());
/* 59 */            this.mFeedAdapter.startLoadingTimeProfiling("ExploreTimeline");
/* 60 */            if (this.mFeedAdapter.getCount() == 0 && !this.mFetched) {
/* 61 */                fetchInitialRequest(UrlCachePolicy.NETWORK_THEN_CACHE);
                    }
                }

                @Override // co.vine.android.BaseTimelineFragment, co.vine.android.BaseArrayListFragment, co.vine.android.BaseAdapterFragment, co.vine.android.BaseControllerFragment, android.support.v4.app.Fragment
/* 66 */        public void onPause() {
/* 67 */            super.onPause();
/* 68 */            this.mFeedAdapter.onPause(isFocused());
                }

                @Override // co.vine.android.BaseTimelineFragment
/* 72 */        protected String fetchPosts(int page, long anchor, boolean userInitiated, UrlCachePolicy cachePolicy) {
                    int type;
/* 73 */            if (this.mCategory == null) {
/* 74 */                return null;
                    }
/* 77 */            if (CATEGORY_ON_THE_RISE.equals(this.mCategory)) {
/* 78 */                type = 4;
                    } else if (CATEGORY_POPULAR_NOW.equals(this.mCategory)) {
/* 80 */                type = 5;
                    } else if ("tag".equals(this.mCategory)) {
/* 82 */                if (this.mTag == null) {
/* 83 */                    throw new IllegalArgumentException("You have to specify a tag.");
                        }
/* 85 */                type = 6;
                    } else if ("timelines".equals(this.mCategory)) {
/* 87 */                type = 11;
                    } else {
/* 89 */                throw new IllegalArgumentException("You have to specify a category.");
                    }
/* 92 */            return this.mAppController.fetchPosts(this.mAppController.getActiveSession(), 10, this.mAppController.getActiveId(), type, page, anchor, userInitiated, this.mTag, null, this.mData, cachePolicy);
                }

/* 98 */        public static Bundle prepareArguments(Intent intent, boolean refreshable, String category, String tag, Uri data) {
/* 99 */            Bundle bundle = BaseArrayListFragment.prepareArguments(intent, refreshable);
/* 100 */           bundle.putString("category", category);
/* 101 */           bundle.putString("tag", tag);
/* 102 */           bundle.putParcelable("data", data);
/* 103 */           return bundle;
                }
            }
