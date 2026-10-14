            package co.vine.android;

            import android.content.Intent;
            import android.net.Uri;
            import android.os.Bundle;
            import android.widget.ListAdapter;
            import co.vine.android.network.UrlCachePolicy;
            
            
    public class ExploreTimelineFragment extends BaseTimelineFragment {
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

                @Override 
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            Bundle b = getArguments();
            this.mCategory = b.getString("category");
            this.mData = (Uri) b.getParcelable("data");
            this.mTag = b.getString("tag");
            setFlurryEventSource(EVENT_SOURCE_TITLE + this.mCategory);
            setFocused(true);
                }

                @Override 
        public void onActivityCreated(Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);
            this.mListView.setAdapter((ListAdapter) this.mFeedAdapter);
                }

                @Override 
        public void onResume() {
            super.onResume();
            this.mFeedAdapter.onResume(isFocused());
            this.mFeedAdapter.startLoadingTimeProfiling("ExploreTimeline");
            if (this.mFeedAdapter.getCount() == 0 && !this.mFetched) {
                fetchInitialRequest(UrlCachePolicy.NETWORK_THEN_CACHE);
                    }
                }

                @Override 
        public void onPause() {
            super.onPause();
            this.mFeedAdapter.onPause(isFocused());
                }

                @Override 
        protected String fetchPosts(int page, long anchor, boolean userInitiated, UrlCachePolicy cachePolicy) {
                    int type;
            if (this.mCategory == null) {
                return null;
                    }
            if (CATEGORY_ON_THE_RISE.equals(this.mCategory)) {
                type = 4;
                    } else if (CATEGORY_POPULAR_NOW.equals(this.mCategory)) {
                type = 5;
                    } else if ("tag".equals(this.mCategory)) {
                if (this.mTag == null) {
                    throw new IllegalArgumentException("You have to specify a tag.");
                        }
                type = 6;
                    } else if ("timelines".equals(this.mCategory)) {
                type = 11;
                    } else {
                throw new IllegalArgumentException("You have to specify a category.");
                    }
            return this.mAppController.fetchPosts(this.mAppController.getActiveSession(), 10, this.mAppController.getActiveId(), type, page, anchor, userInitiated, this.mTag, null, this.mData, cachePolicy);
                }

        public static Bundle prepareArguments(Intent intent, boolean refreshable, String category, String tag, Uri data) {
            Bundle bundle = BaseArrayListFragment.prepareArguments(intent, refreshable);
           bundle.putString("category", category);
           bundle.putString("tag", tag);
           bundle.putParcelable("data", data);
           return bundle;
                }
            }
