            package co.vine.android;

            import android.content.ContentUris;
            import android.database.Cursor;
            import android.os.Bundle;
            import android.support.v4.content.CursorLoader;
            import android.support.v4.content.Loader;
            import android.text.Spanned;
            import android.text.method.LinkMovementMethod;
            import android.view.View;
            import android.widget.TextView;
            import co.vine.android.client.AppController;
            import co.vine.android.client.AppSessionListener;
            import co.vine.android.network.UrlCachePolicy;
            import co.vine.android.provider.Vine;
            import co.vine.android.provider.VineDatabaseSQL;
            import co.vine.android.util.CommonUtil;
            import co.vine.android.util.Util;
            import co.vine.android.widget.SpanClickListener;
            import co.vine.android.widget.StyledClickableSpan;
            
            
    public class SingleFragmentWrapper {
                public static final String ARG_LOCKED = "locked";
                public static final String ARG_POST_ID = "post_id";
                public static final String ARG_SHARE_ID = "post_share_id";
                public static final String ARG_TAKE_FOCUS = "take_focus";
                public static final String ARG_USERNAME = "username";
                public static final String ARG_USER_ID = "userId";
                private AppController mAppController;
                private BaseControllerFragment mFragment;
                private boolean mLocked;
                private long mPostId;
                private String mShareId;
                private AppSessionListener mSinglePostListener;
                private long mUserId;
                private String mUsername;

        public boolean onCreate(BaseControllerFragment fragment, AppSessionListener listener) {
            this.mFragment = fragment;
            this.mAppController = this.mFragment.getAppController();
            Bundle args = fragment.getArguments();
            this.mLocked = args.getBoolean(ARG_LOCKED, false);
            if (!this.mLocked) {
                this.mPostId = args.getLong("post_id");
                this.mShareId = args.getString("post_share_id");
                boolean takeFocus = args.getBoolean("take_focus", false);
                this.mSinglePostListener = listener;
                        return takeFocus;
                    }
            this.mUsername = args.getString("username");
            this.mUserId = args.getLong("userId");
                    return false;
                }

        public boolean isLocked() {
            return this.mLocked;
                }

        public View onCreateLockedView(View v, SpanClickListener listener) {
            TextView tv = (TextView) v.findViewById(R.id.lock_text);
            tv.setMovementMethod(LinkMovementMethod.getInstance());
            StyledClickableSpan styled = new StyledClickableSpan(1, Long.valueOf(this.mUserId), listener);
            styled.setColor(this.mFragment.getResources().getColor(R.color.vine_green));
            Spanned sp = Util.getSpannedText(new Object[]{styled}, this.mFragment.getString(R.string.post_locked, this.mUsername), CommonUtil.MARKER_QUOTES);
            tv.setText(sp);
            return v;
                }

        public boolean onResume() {
            if (isLocked()) {
                return false;
                    }
            if (this.mSinglePostListener != null) {
                this.mAppController.addListener(this.mSinglePostListener);
                    }
            return true;
                }

        public String fetchPostContent(UrlCachePolicy cachePolicy) {
            return this.mAppController.fetchPost(this.mAppController.getActiveSession(), this.mPostId, cachePolicy);
                }

       public String fetchPostId() {
           return this.mAppController.fetchPostId(this.mAppController.getActiveSession(), this.mShareId);
                }

       public void onGetPostIdComplete(long postId) {
           this.mPostId = postId;
                }

       public void onPause() {
           if (this.mSinglePostListener != null) {
               this.mAppController.removeListener(this.mSinglePostListener);
                    }
                }

       public Loader<Cursor> onCreateLoader() {
           if (this.mLocked) {
                        return null;
                    }
                    return new CursorLoader(this.mFragment.getActivity(), ContentUris.withAppendedId(Vine.PostCommentsLikesView.CONTENT_URI_ALL_TIMELINE_SINGLE, this.mPostId), VineDatabaseSQL.PostCommentsLikesQuery.PROJECTION, null, null, null);
                }

       public long getPostId() {
           return this.mPostId;
                }

       public String getSharedId() {
           return this.mShareId;
                }
            }
