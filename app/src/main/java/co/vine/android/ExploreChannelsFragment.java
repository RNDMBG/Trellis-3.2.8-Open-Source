            package co.vine.android;

            import android.app.Activity;
            import android.content.Intent;
            import android.os.Bundle;
            import android.view.ViewGroup;
            import android.widget.ListAdapter;
            import co.vine.android.network.UrlCachePolicy;
            import java.lang.ref.WeakReference;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 38 */    public class ExploreChannelsFragment extends BaseTimelineFragment {
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

                @Override // co.vine.android.BaseTimelineFragment, android.support.v4.app.Fragment
/* 39 */        public void onAttach(Activity activity) {
/* 40 */            super.onAttach(activity);
/* 41 */            ExploreChannelsActivity callback = (ExploreChannelsActivity) activity;
/* 42 */            this.mCallback = new WeakReference<>(callback);
                }

                @Override // co.vine.android.BaseTimelineFragment, co.vine.android.BaseArrayListFragment, co.vine.android.BaseAdapterFragment, co.vine.android.BaseControllerFragment, android.support.v4.app.Fragment
/* 46 */        public void onCreate(Bundle savedInstanceState) {
/* 47 */            super.onCreate(savedInstanceState);
/* 48 */            Bundle b = getArguments();
/* 49 */            this.mChannelId = b.getString("channelId");
/* 51 */            setFlurryEventSource(EVENT_SOURCE_TITLE + this.mChannelId);
/* 53 */            this.mSort = b.getString("sort");
/* 54 */            if (this.mSort == null) {
/* 55 */                this.mSort = "popular";
                    }
/* 58 */            this.mColor = b.getInt("color");
/* 60 */            this.mTabTag = b.getString("tag");
/* 61 */            if (this.mTabTag == null) {
/* 62 */                this.mTabTag = "popular";
                    }
                }

                @Override // co.vine.android.BaseTimelineFragment, co.vine.android.BaseArrayListFragment, co.vine.android.widget.OnTabChangedListener
/* 67 */        public void onMoveTo(int oldPosition) {
/* 68 */            super.onMoveTo(oldPosition);
/* 69 */            ((ExploreChannelsActivity) getActivity()).resetNav();
                }

                @Override // co.vine.android.BaseTimelineFragment, android.support.v4.app.Fragment
/* 73 */        public void onActivityCreated(Bundle savedInstanceState) {
/* 74 */            super.onActivityCreated(savedInstanceState);
/* 75 */            this.mFeedAdapter.setProfileColor(16777215 & this.mColor);
/* 76 */            this.mListView.setAdapter((ListAdapter) this.mFeedAdapter);
/* 77 */            if (((ExploreChannelsActivity) getActivity()).hasTabs()) {
/* 78 */                setUpUnderNavHeader();
/* 79 */                ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) this.mEmptyProgress.getLayoutParams();
/* 80 */                params.topMargin = getActivity().getResources().getDimensionPixelSize(C0257R.dimen.tabbar_height);
                    }
                }

                @Override // co.vine.android.BaseTimelineFragment, co.vine.android.BaseArrayListFragment, co.vine.android.BaseAdapterFragment, co.vine.android.BaseControllerFragment, co.vine.android.BaseFragment, android.support.v4.app.Fragment
/* 85 */        public void onResume() {
/* 86 */            super.onResume();
/* 87 */            handleFocus();
/* 88 */            this.mFeedAdapter.startLoadingTimeProfiling("ExploreChannels");
/* 89 */            if (this.mFeedAdapter.isEmpty()) {
/* 90 */                fetchInitialRequest(UrlCachePolicy.NETWORK_THEN_CACHE);
                    }
                }

                @Override // co.vine.android.BaseTimelineFragment, co.vine.android.BaseArrayListFragment, co.vine.android.BaseAdapterFragment, co.vine.android.BaseControllerFragment, android.support.v4.app.Fragment
/* 95 */        public void onPause() {
/* 96 */            super.onPause();
/* 97 */            setFocused(false);
/* 98 */            this.mFeedAdapter.onPause(false);
                }

/* 101 */       private void handleFocus() {
/* 102 */           if (this.mCallback != null && this.mCallback.get() != null) {
/* 103 */               if (this.mCallback.get().isShowing(this.mTabTag)) {
/* 104 */                   setFocused(true);
/* 105 */                   if (this.mAdapter.isEmpty()) {
/* 106 */                       showProgress(3);
/* 107 */                       fetchContent(3, false, UrlCachePolicy.CACHE_THEN_NETWORK);
                            }
/* 109 */                   if (!this.mFeedAdapter.isPlaying()) {
/* 110 */                       this.mFeedAdapter.onResume(true);
/* 117 */                       return;
                            }
/* 117 */                   return;
                        }
/* 113 */               setFocused(false);
/* 114 */               this.mFeedAdapter.onResume(false);
                    }
                }

                @Override // co.vine.android.BaseTimelineFragment
/* 121 */       protected String fetchPosts(int page, long anchor, boolean userInitiated, UrlCachePolicy cachePolicy) {
                    int groupType;
/* 122 */           if (this.mSort.equals("popular")) {
/* 123 */               groupType = 8;
                    } else {
/* 125 */               groupType = 9;
                    }
/* 128 */           return this.mAppController.fetchPosts(this.mAppController.getActiveSession(), 10, this.mAppController.getActiveId(), groupType, page, anchor, userInitiated, this.mChannelId, this.mSort, null, cachePolicy);
                }

/* 134 */       public static Bundle prepareArguments(Intent intent, boolean refreshable, String channelId, String sort, String tag, int color) {
/* 135 */           Bundle b = BaseArrayListFragment.prepareArguments(intent, refreshable);
/* 136 */           b.putString("channelId", channelId);
/* 137 */           b.putString("sort", sort);
/* 138 */           b.putString("tag", tag);
/* 139 */           b.putInt("color", color);
/* 140 */           return b;
                }
            }
