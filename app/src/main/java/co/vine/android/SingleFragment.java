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
            
            
    public class SingleFragment extends BaseTimelineFragment {
                public static final String ARG_IS_FAVORITE = "is_favorite";
                private static final String EVENT_SOURCE_TITLE = "Single Post";
                private boolean mIsFavorite;
                private final SingleFragmentWrapper mWrapper = new SingleFragmentWrapper();

        static  SingleFragmentWrapper access$000(SingleFragment x0) {
            return x0.mWrapper;
                }

                @Override 
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            if (this.mWrapper.onCreate(this, new SinglePostListener())) {
                setFocused(true);
                    }
            if (savedInstanceState == null) {
                this.mIsFavorite = getArguments().getBoolean(ARG_IS_FAVORITE);
                    }
            setFlurryEventSource(EVENT_SOURCE_TITLE);
                }

                @Override 
        public void onActivityCreated(Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);
            if (!this.mWrapper.isLocked()) {
                this.mListView.setAdapter((ListAdapter) this.mFeedAdapter);
                    }
                }

                @Override 
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
                    return this.mWrapper.isLocked() ? this.mWrapper.onCreateLockedView(createView(inflater, R.layout.post_locked_list, container), this) : super.onCreateView(inflater, container, savedInstanceState);
                }

                @Override 
        public void onResume() {
            super.onResume();
            if (this.mWrapper.onResume()) {
                this.mFeedAdapter.onResume(isFocused());
                if (this.mFetched) {
                    return;
                        }
                        if (this.mWrapper.getPostId() > 0) {
                    fetchPostContent(3, UrlCachePolicy.NETWORK_THEN_CACHE);
                        } else if (this.mWrapper.getSharedId() != null) {
                    fetchPostId(3);
                        }
                    }
                }

                @Override 
        public void onPause() {
            super.onPause();
            this.mFeedAdapter.onPause(isFocused());
            this.mWrapper.onPause();
                }

                @Override 
        protected String fetchPosts(int page, long anchor, boolean userInitiated, UrlCachePolicy cachePolicy) {
            throw new IllegalStateException("Cannot fetch on single post");
                }

                @Override 
        protected void fetchContent(int fetchType, boolean userInitiated, UrlCachePolicy cachePolicy) {
                }

        protected void fetchPostContent(int fetchType, UrlCachePolicy cachePolicy) {
            if (!this.mWrapper.isLocked()) {
                addRequest(this.mWrapper.fetchPostContent(cachePolicy), fetchType);
                this.mFetched = true;
                    }
                }

       private void fetchPostId(int fetchType) {
           if (!this.mWrapper.isLocked()) {
               addRequest(this.mWrapper.fetchPostId(), fetchType);
                    }
                }

       class SinglePostListener extends AppSessionListener {
           SinglePostListener() {
                    }

                    @Override 
           public void onGetSinglePostComplete(String reqId, int statusCode, String reasonPhrase, VinePost post) {
               if (statusCode == 200) {
                   ((FeedAdapter) SingleFragment.this.mFeedAdapter).mergePost(post);
                        } else {
                   SingleFragment.this.getActivity().finish();
                        }
                    }

                    @Override 
           public void onGetPostIdComplete(String reqId, int statusCode, String reasonPhrase, long postId) {
               SingleFragment.access$000(SingleFragment.this).onGetPostIdComplete(postId);
               SingleFragment.this.fetchPostContent(3, UrlCachePolicy.NETWORK_THEN_CACHE);
                    }
                }

                @Override 
       public void onClick(View view) {
           int id = view.getId();
           if (id == R.id.more_options) {
               VinePost post = (VinePost) view.getTag();
               FragmentActivity activity = getActivity();
               if (activity != null) {
                            Intent intent = PostOptionsDialogActivity.getSinglePostMoreIntent(post, activity, post.userId == this.mAppController.getActiveId(), false, this.mIsFavorite);
                   activity.startActivityForResult(intent, 10);
                   return;
                        }
               return;
                    }
           super.onClick(view);
                }

                @Override 
       public void onActivityResult(int requestCode, int resultCode, Intent data) {
           super.onActivityResult(requestCode, resultCode, data);
           if (-1 == resultCode && data != null && PostOptionsDialogActivity.ACTION_UNFAVORITE_USER.equals(data.getAction())) {
               this.mIsFavorite = false;
                    }
                }
            }
