            package co.vine.android;

            import android.content.Intent;
            import android.support.v4.app.FragmentActivity;
            import android.view.View;
            import co.vine.android.widget.FeedViewHolder;
            import co.vine.android.widget.GenericVideoAdapter;
            import co.vine.android.widget.SpanClickListener;
            
            
    public class VineSpanClicker implements SpanClickListener {
                public static final int FOLLOWERS_SPAN = 5;
                public static final int FOLLOWING_SPAN = 6;
                public static final int MANY_LIKES_SPAN = 2;
                public static final int OLDER_COMMENTS_SPAN = 3;
                public static final int POST_SPAN = 9;
                public static final int REVINED_BY_SPAN = 8;
                public static final int REVINERS_SPAN = 7;
                public static final int SPAN_FLAGS = 33;
                public static final int TAG_SPAN = 4;
                public static final int USERNAME_SPAN = 1;
                private FragmentActivity mActivity;
                private String mFlurryEventSource;
                private BaseFragment mFragment;
                private long mUserId;

        public VineSpanClicker(BaseFragment fragment) {
            this.mFragment = fragment;
                }

        public VineSpanClicker(FragmentActivity activity) {
            this.mActivity = activity;
                }

        public void setFlurryEventSource(String source) {
            this.mFlurryEventSource = source;
                }

        public void setUserId(long userId) {
            this.mUserId = userId;
                }

        public FragmentActivity getActivity() {
                    return this.mFragment != null ? this.mFragment.getActivity() : this.mActivity;
                }

        public void startActivity(Intent i) {
            if (this.mFragment != null) {
                this.mFragment.startActivity(i);
                    } else {
                this.mActivity.startActivity(i);
                    }
                }

        public void onFollowersClicked() {
            FragmentActivity activity = getActivity();
            if (activity != null) {
                Intent i = new Intent(activity, (Class<?>) UsersActivity.class);
                i.putExtra("p_id", this.mUserId);
                i.putExtra("u_type", 2);
                startActivity(i);
                    }
                }

        public void onFollowingClicked() {
            FragmentActivity activity = getActivity();
            if (activity != null) {
                Intent i = new Intent(activity, (Class<?>) UsersActivity.class);
                i.putExtra("p_id", this.mUserId);
                i.putExtra("u_type", 1);
                startActivity(i);
                    }
                }

                @Override 
        public void onSpanClicked(View view, int type, Object tag) {
            FragmentActivity activity = getActivity();
            if (activity != null) {
                        switch (type) {
                            case 1:
                       if (this.mFragment != null && (this.mFragment instanceof ProfileFragment)) {
                           long userId = ((ProfileFragment) this.mFragment).getUserId();
                           if (tag != null && tag.equals(Long.valueOf(userId))) {
                                    }
                                }
                       ProfileActivity.start(activity, ((Long) tag).longValue(), this.mFlurryEventSource);
                                break;
                            case 2:
                       UsersActivity.startUserListForLikers(activity, ((Long) tag).longValue());
                                break;
                            case 3:
                       GenericVideoAdapter<FeedViewHolder>.SpanCommentObjectTag spanTag = (GenericVideoAdapter.SpanCommentObjectTag) tag;
                       CommentsActivity.start(activity, spanTag.postId, spanTag.repostId, spanTag.authorId, true);
                                break;
                            case 4:
                       HashtagActivity.start(activity, (String) tag);
                                break;
                            case 5:
                        onFollowersClicked();
                                break;
                            case 6:
                        onFollowingClicked();
                                break;
                            case 7:
                       UsersActivity.startUserListForReviners(activity, ((GenericVideoAdapter.SpanCommentObjectTag) tag).postId);
                                break;
                            case 8:
                       onRevineBySpanClicked(((Long) tag).longValue());
                                break;
                            case 9:
                       SingleActivity.start(activity, ((Long) tag).longValue());
                                break;
                        }
                    }
                }

       public void onRevineBySpanClicked(long userId) {
           FragmentActivity activity = getActivity();
           if (activity != null) {
               ProfileActivity.start(activity, userId, this.mFlurryEventSource);
                    }
                }
            }
