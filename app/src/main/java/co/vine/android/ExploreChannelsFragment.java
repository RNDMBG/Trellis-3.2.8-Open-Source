            package co.vine.android;

            import android.app.Activity;
            import android.content.Intent;
            import android.os.Bundle;
            import android.view.ViewGroup;
            import android.widget.ListAdapter;
            import co.vine.android.network.UrlCachePolicy;
            import java.lang.ref.WeakReference;
            
            
    public class ExploreChannelsFragment extends BaseTimelineFragment {
                public static final String ARG_CHANNEL_ID = "channelId";
                public static final String ARG_COLOR = "color";
                public static final String ARG_SORT = "sort";
                public static final String ARG_TAG = "tag";
                public static final String CATEGORY_CHANNELS = "channels";
                private static final String EVENT_SOURCE_TITLE = "Explore Channel: ";
                public static final String SORT_POPULAR = "popular";
                public static final String SORT_RECENT = "recent";
                private WeakReference<ExploreChannelsActivity> mCallback;
                private String mChannelId;
                private int mColor;
                private String mSort;
                private String mTabTag;

                @Override 
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ExploreChannelsActivity callback = (ExploreChannelsActivity) activity;
            this.mCallback = new WeakReference<>(callback);
                }

                @Override 
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            Bundle b = getArguments();
            this.mChannelId = b.getString("channelId");
            setFlurryEventSource(EVENT_SOURCE_TITLE + this.mChannelId);
            this.mSort = b.getString("sort");
            if (this.mSort == null) {
                this.mSort = "popular";
                    }
            this.mColor = b.getInt("color");
            this.mTabTag = b.getString("tag");
            if (this.mTabTag == null) {
                this.mTabTag = "popular";
                    }
                }

                @Override 
        public void onMoveTo(int oldPosition) {
            super.onMoveTo(oldPosition);
            ((ExploreChannelsActivity) getActivity()).resetNav();
                }

                @Override 
        public void onActivityCreated(Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);
            this.mFeedAdapter.setProfileColor(16777215 & this.mColor);
            this.mListView.setAdapter((ListAdapter) this.mFeedAdapter);
            if (((ExploreChannelsActivity) getActivity()).hasTabs()) {
                setUpUnderNavHeader();
                ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) this.mEmptyProgress.getLayoutParams();
                params.topMargin = getActivity().getResources().getDimensionPixelSize(C0257R.dimen.tabbar_height);
                    }
                }

                @Override 
        public void onResume() {
            super.onResume();
            handleFocus();
            this.mFeedAdapter.startLoadingTimeProfiling("ExploreChannels");
            if (this.mFeedAdapter.isEmpty()) {
                fetchInitialRequest(UrlCachePolicy.NETWORK_THEN_CACHE);
                    }
                }

                @Override 
        public void onPause() {
            super.onPause();
            setFocused(false);
            this.mFeedAdapter.onPause(false);
                }

       private void handleFocus() {
           if (this.mCallback != null && this.mCallback.get() != null) {
               if (this.mCallback.get().isShowing(this.mTabTag)) {
                   setFocused(true);
                   if (this.mAdapter.isEmpty()) {
                       showProgress(3);
                       fetchContent(3, false, UrlCachePolicy.CACHE_THEN_NETWORK);
                            }
                   if (!this.mFeedAdapter.isPlaying()) {
                       this.mFeedAdapter.onResume(true);
                       return;
                            }
                   return;
                        }
               setFocused(false);
               this.mFeedAdapter.onResume(false);
                    }
                }

                @Override 
       protected String fetchPosts(int page, long anchor, boolean userInitiated, UrlCachePolicy cachePolicy) {
                    int groupType;
           if (this.mSort.equals("popular")) {
               groupType = 8;
                    } else {
               groupType = 9;
                    }
           return this.mAppController.fetchPosts(this.mAppController.getActiveSession(), 10, this.mAppController.getActiveId(), groupType, page, anchor, userInitiated, this.mChannelId, this.mSort, null, cachePolicy);
                }

       public static Bundle prepareArguments(Intent intent, boolean refreshable, String channelId, String sort, String tag, int color) {
           Bundle b = BaseArrayListFragment.prepareArguments(intent, refreshable);
           b.putString("channelId", channelId);
           b.putString("sort", sort);
           b.putString("tag", tag);
           b.putInt("color", color);
           return b;
                }
            }
