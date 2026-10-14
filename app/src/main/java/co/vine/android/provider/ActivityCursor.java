            package co.vine.android.provider;

            import android.database.Cursor;
            import android.database.MergeCursor;
            import android.os.Bundle;
            
            
    public class ActivityCursor extends MergeCursor {
                public static final String EXTRA_NUM_REQUESTS = "num_reqs";
                private int mNumFollowRequests;

        public ActivityCursor(Cursor[] cursors) {
            super(cursors);
                }

        public ActivityCursor(Cursor[] cursors, int numFollowRequests) {
            super(cursors);
            this.mNumFollowRequests = numFollowRequests;
                }

                @Override 
        public Bundle getExtras() {
            Bundle extras = new Bundle(super.getExtras());
            extras.putInt(EXTRA_NUM_REQUESTS, this.mNumFollowRequests);
            return extras;
                }
            }
