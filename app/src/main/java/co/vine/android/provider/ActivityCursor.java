            package co.vine.android.provider;

            import android.database.Cursor;
            import android.database.MergeCursor;
            import android.os.Bundle;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 14 */    public class ActivityCursor extends MergeCursor {
                public static final String EXTRA_NUM_REQUESTS = "num_reqs";
                private int mNumFollowRequests;

/* 15 */        public ActivityCursor(Cursor[] cursors) {
/* 16 */            super(cursors);
                }

/* 19 */        public ActivityCursor(Cursor[] cursors, int numFollowRequests) {
/* 20 */            super(cursors);
/* 21 */            this.mNumFollowRequests = numFollowRequests;
                }

                @Override // android.database.AbstractCursor, android.database.Cursor
/* 25 */        public Bundle getExtras() {
/* 26 */            Bundle extras = new Bundle(super.getExtras());
/* 27 */            extras.putInt(EXTRA_NUM_REQUESTS, this.mNumFollowRequests);
/* 28 */            return extras;
                }
            }
