            package co.vine.android;

            import android.content.Intent;
            import android.os.Bundle;
            import android.support.v4.app.FragmentActivity;
            import android.view.LayoutInflater;
            import android.view.View;
            import android.view.ViewGroup;
            import android.widget.ListAdapter;
            import co.vine.android.api.VinePost;
            import co.vine.android.client.AppSessionListener;
            import co.vine.android.network.UrlCachePolicy;
            import co.vine.android.widget.FeedAdapter;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 18 */    public class SingleFragment extends BaseTimelineFragment {
                public static final String ARG_IS_FAVORITE = "is_favorite";
                private static final String EVENT_SOURCE_TITLE = "Single Post";
                private boolean mIsFavorite;
                private final SingleFragmentWrapper mWrapper = new SingleFragmentWrapper();

/* 19 */        static /* synthetic */ SingleFragmentWrapper access$000(SingleFragment x0) {
/* 20 */            return x0.mWrapper;
                }

                @Override // co.vine.android.BaseTimelineFragment, co.vine.android.BaseArrayListFragment, co.vine.android.BaseAdapterFragment, co.vine.android.BaseControllerFragment, android.support.v4.app.Fragment
/* 31 */        public void onCreate(Bundle savedInstanceState) {
/* 32 */            super.onCreate(savedInstanceState);
/* 33 */            if (this.mWrapper.onCreate(this, new SinglePostListener())) {
/* 34 */                setFocused(true);
                    }
/* 36 */            if (savedInstanceState == null) {
/* 37 */                this.mIsFavorite = getArguments().getBoolean(ARG_IS_FAVORITE);
                    }
/* 39 */            setFlurryEventSource(EVENT_SOURCE_TITLE);
                }

                @Override // co.vine.android.BaseTimelineFragment, android.support.v4.app.Fragment
/* 43 */        public void onActivityCreated(Bundle savedInstanceState) {
/* 44 */            super.onActivityCreated(savedInstanceState);
/* 45 */            if (!this.mWrapper.isLocked()) {
/* 46 */                this.mListView.setAdapter((ListAdapter) this.mFeedAdapter);
                    }
                }

                @Override // co.vine.android.BaseArrayListFragment, android.support.v4.app.Fragment
/* 52 */        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
                    return this.mWrapper.isLocked() ? this.mWrapper.onCreateLockedView(createView(inflater, C0257R.layout.post_locked_list, container), this) : super.onCreateView(inflater, container, savedInstanceState);
                }

                @Override // co.vine.android.BaseTimelineFragment, co.vine.android.BaseArrayListFragment, co.vine.android.BaseAdapterFragment, co.vine.android.BaseControllerFragment, co.vine.android.BaseFragment, android.support.v4.app.Fragment
/* 61 */        public void onResume() {
/* 62 */            super.onResume();
/* 63 */            if (this.mWrapper.onResume()) {
/* 64 */                this.mFeedAdapter.onResume(isFocused());
/* 66 */                if (this.mFetched) {
/* 74 */                    return;
                        }
                        if (this.mWrapper.getPostId() > 0) {
/* 68 */                    fetchPostContent(3, UrlCachePolicy.NETWORK_THEN_CACHE);
                        } else if (this.mWrapper.getSharedId() != null) {
/* 70 */                    fetchPostId(3);
                        }
                    }
                }

                @Override // co.vine.android.BaseTimelineFragment, co.vine.android.BaseArrayListFragment, co.vine.android.BaseAdapterFragment, co.vine.android.BaseControllerFragment, android.support.v4.app.Fragment
/* 77 */        public void onPause() {
/* 78 */            super.onPause();
/* 79 */            this.mFeedAdapter.onPause(isFocused());
/* 80 */            this.mWrapper.onPause();
                }

                @Override // co.vine.android.BaseTimelineFragment
/* 84 */        protected String fetchPosts(int page, long anchor, boolean userInitiated, UrlCachePolicy cachePolicy) {
/* 85 */            throw new IllegalStateException("Cannot fetch on single post");
                }

                @Override // co.vine.android.BaseTimelineFragment
/* 91 */        protected void fetchContent(int fetchType, boolean userInitiated, UrlCachePolicy cachePolicy) {
                }

/* 94 */        protected void fetchPostContent(int fetchType, UrlCachePolicy cachePolicy) {
/* 95 */            if (!this.mWrapper.isLocked()) {
/* 96 */                addRequest(this.mWrapper.fetchPostContent(cachePolicy), fetchType);
/* 97 */                this.mFetched = true;
                    }
                }

/* 101 */       private void fetchPostId(int fetchType) {
/* 102 */           if (!this.mWrapper.isLocked()) {
/* 103 */               addRequest(this.mWrapper.fetchPostId(), fetchType);
                    }
                }

/* 105 */       class SinglePostListener extends AppSessionListener {
/* 106 */           SinglePostListener() {
                    }

                    @Override // co.vine.android.client.AppSessionListener
/* 109 */           public void onGetSinglePostComplete(String reqId, int statusCode, String reasonPhrase, VinePost post) {
/* 110 */               if (statusCode == 200) {
/* 111 */                   ((FeedAdapter) SingleFragment.this.mFeedAdapter).mergePost(post);
                        } else {
/* 113 */                   SingleFragment.this.getActivity().finish();
                        }
                    }

                    @Override // co.vine.android.client.AppSessionListener
/* 119 */           public void onGetPostIdComplete(String reqId, int statusCode, String reasonPhrase, long postId) {
/* 120 */               SingleFragment.access$000(SingleFragment.this).onGetPostIdComplete(postId);
/* 121 */               SingleFragment.this.fetchPostContent(3, UrlCachePolicy.NETWORK_THEN_CACHE);
                    }
                }

                @Override // co.vine.android.BaseTimelineFragment, android.view.View.OnClickListener
/* 126 */       public void onClick(View view) {
/* 127 */           int id = view.getId();
/* 128 */           if (id == C0257R.id.more_options) {
/* 129 */               VinePost post = (VinePost) view.getTag();
/* 130 */               FragmentActivity activity = getActivity();
/* 131 */               if (activity != null) {
                            Intent intent = PostOptionsDialogActivity.getSinglePostMoreIntent(post, activity, post.userId == this.mAppController.getActiveId(), false, this.mIsFavorite);
/* 134 */                   activity.startActivityForResult(intent, 10);
/* 139 */                   return;
                        }
/* 139 */               return;
                    }
/* 137 */           super.onClick(view);
                }

                @Override // co.vine.android.BaseTimelineFragment, co.vine.android.BaseArrayListFragment, android.support.v4.app.Fragment
/* 142 */       public void onActivityResult(int requestCode, int resultCode, Intent data) {
/* 143 */           super.onActivityResult(requestCode, resultCode, data);
/* 144 */           if (-1 == resultCode && data != null && PostOptionsDialogActivity.ACTION_UNFAVORITE_USER.equals(data.getAction())) {
/* 146 */               this.mIsFavorite = false;
                    }
                }
            }
