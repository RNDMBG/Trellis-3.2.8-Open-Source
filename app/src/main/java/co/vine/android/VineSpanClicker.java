            package co.vine.android;

            import android.content.Intent;
            import android.support.v4.app.FragmentActivity;
            import android.view.View;
            import co.vine.android.widget.FeedViewHolder;
            import co.vine.android.widget.GenericVideoAdapter;
            import co.vine.android.widget.SpanClickListener;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 31 */    public class VineSpanClicker implements SpanClickListener {
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

/* 32 */        public VineSpanClicker(BaseFragment fragment) {
/* 34 */            this.mFragment = fragment;
                }

/* 36 */        public VineSpanClicker(FragmentActivity activity) {
/* 38 */            this.mActivity = activity;
                }

/* 41 */        public void setFlurryEventSource(String source) {
/* 42 */            this.mFlurryEventSource = source;
                }

/* 45 */        public void setUserId(long userId) {
/* 46 */            this.mUserId = userId;
                }

/* 49 */        public FragmentActivity getActivity() {
                    return this.mFragment != null ? this.mFragment.getActivity() : this.mActivity;
                }

/* 57 */        public void startActivity(Intent i) {
/* 58 */            if (this.mFragment != null) {
/* 59 */                this.mFragment.startActivity(i);
                    } else {
/* 61 */                this.mActivity.startActivity(i);
                    }
                }

/* 65 */        public void onFollowersClicked() {
/* 66 */            FragmentActivity activity = getActivity();
/* 67 */            if (activity != null) {
/* 68 */                Intent i = new Intent(activity, (Class<?>) UsersActivity.class);
/* 69 */                i.putExtra("p_id", this.mUserId);
/* 70 */                i.putExtra("u_type", 2);
/* 72 */                startActivity(i);
                    }
                }

/* 76 */        public void onFollowingClicked() {
/* 77 */            FragmentActivity activity = getActivity();
/* 78 */            if (activity != null) {
/* 79 */                Intent i = new Intent(activity, (Class<?>) UsersActivity.class);
/* 80 */                i.putExtra("p_id", this.mUserId);
/* 81 */                i.putExtra("u_type", 1);
/* 83 */                startActivity(i);
                    }
                }

                @Override // co.vine.android.widget.SpanClickListener
/* 88 */        public void onSpanClicked(View view, int type, Object tag) {
/* 89 */            FragmentActivity activity = getActivity();
/* 90 */            if (activity != null) {
                        switch (type) {
                            case 1:
/* 109 */                       if (this.mFragment != null && (this.mFragment instanceof ProfileFragment)) {
/* 110 */                           long userId = ((ProfileFragment) this.mFragment).getUserId();
/* 112 */                           if (tag != null && tag.equals(Long.valueOf(userId))) {
                                    }
                                }
/* 116 */                       ProfileActivity.start(activity, ((Long) tag).longValue(), this.mFlurryEventSource);
                                break;
                            case 2:
/* 101 */                       UsersActivity.startUserListForLikers(activity, ((Long) tag).longValue());
                                break;
                            case 3:
/* 124 */                       GenericVideoAdapter<FeedViewHolder>.SpanCommentObjectTag spanTag = (GenericVideoAdapter.SpanCommentObjectTag) tag;
/* 126 */                       CommentsActivity.start(activity, spanTag.postId, spanTag.repostId, spanTag.authorId, true);
                                break;
                            case 4:
/* 120 */                       HashtagActivity.start(activity, (String) tag);
                                break;
                            case 5:
/* 97 */                        onFollowersClicked();
                                break;
                            case 6:
/* 93 */                        onFollowingClicked();
                                break;
                            case 7:
/* 132 */                       UsersActivity.startUserListForReviners(activity, ((GenericVideoAdapter.SpanCommentObjectTag) tag).postId);
                                break;
                            case 8:
/* 136 */                       onRevineBySpanClicked(((Long) tag).longValue());
                                break;
                            case 9:
/* 105 */                       SingleActivity.start(activity, ((Long) tag).longValue());
                                break;
                        }
                    }
                }

/* 143 */       public void onRevineBySpanClicked(long userId) {
/* 144 */           FragmentActivity activity = getActivity();
/* 145 */           if (activity != null) {
/* 146 */               ProfileActivity.start(activity, userId, this.mFlurryEventSource);
                    }
                }
            }
