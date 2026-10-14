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
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 47 */    public class SingleFragmentWrapper {
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

/* 48 */        public boolean onCreate(BaseControllerFragment fragment, AppSessionListener listener) {
/* 49 */            this.mFragment = fragment;
/* 50 */            this.mAppController = this.mFragment.getAppController();
/* 51 */            Bundle args = fragment.getArguments();
/* 52 */            this.mLocked = args.getBoolean(ARG_LOCKED, false);
/* 54 */            if (!this.mLocked) {
/* 55 */                this.mPostId = args.getLong("post_id");
/* 56 */                this.mShareId = args.getString("post_share_id");
/* 57 */                boolean takeFocus = args.getBoolean("take_focus", false);
/* 58 */                this.mSinglePostListener = listener;
                        return takeFocus;
                    }
/* 63 */            this.mUsername = args.getString("username");
/* 64 */            this.mUserId = args.getLong("userId");
                    return false;
                }

/* 69 */        public boolean isLocked() {
/* 70 */            return this.mLocked;
                }

/* 73 */        public View onCreateLockedView(View v, SpanClickListener listener) {
/* 74 */            TextView tv = (TextView) v.findViewById(C0257R.id.lock_text);
/* 75 */            tv.setMovementMethod(LinkMovementMethod.getInstance());
/* 76 */            StyledClickableSpan styled = new StyledClickableSpan(1, Long.valueOf(this.mUserId), listener);
/* 78 */            styled.setColor(this.mFragment.getResources().getColor(C0257R.color.vine_green));
/* 79 */            Spanned sp = Util.getSpannedText(new Object[]{styled}, this.mFragment.getString(C0257R.string.post_locked, this.mUsername), CommonUtil.MARKER_QUOTES);
/* 81 */            tv.setText(sp);
/* 82 */            return v;
                }

/* 85 */        public boolean onResume() {
/* 86 */            if (isLocked()) {
/* 87 */                return false;
                    }
/* 89 */            if (this.mSinglePostListener != null) {
/* 90 */                this.mAppController.addListener(this.mSinglePostListener);
                    }
/* 92 */            return true;
                }

/* 96 */        public String fetchPostContent(UrlCachePolicy cachePolicy) {
/* 97 */            return this.mAppController.fetchPost(this.mAppController.getActiveSession(), this.mPostId, cachePolicy);
                }

/* 100 */       public String fetchPostId() {
/* 101 */           return this.mAppController.fetchPostId(this.mAppController.getActiveSession(), this.mShareId);
                }

/* 104 */       public void onGetPostIdComplete(long postId) {
/* 105 */           this.mPostId = postId;
                }

/* 108 */       public void onPause() {
/* 109 */           if (this.mSinglePostListener != null) {
/* 110 */               this.mAppController.removeListener(this.mSinglePostListener);
                    }
                }

/* 114 */       public Loader<Cursor> onCreateLoader() {
/* 115 */           if (this.mLocked) {
                        return null;
                    }
                    return new CursorLoader(this.mFragment.getActivity(), ContentUris.withAppendedId(Vine.PostCommentsLikesView.CONTENT_URI_ALL_TIMELINE_SINGLE, this.mPostId), VineDatabaseSQL.PostCommentsLikesQuery.PROJECTION, null, null, null);
                }

/* 124 */       public long getPostId() {
/* 125 */           return this.mPostId;
                }

/* 128 */       public String getSharedId() {
/* 129 */           return this.mShareId;
                }
            }
