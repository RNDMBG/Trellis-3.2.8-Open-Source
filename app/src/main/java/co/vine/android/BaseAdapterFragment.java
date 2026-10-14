            package co.vine.android;

            import android.os.Bundle;
            import android.os.Handler;
            import android.os.Looper;
            
            
    public abstract class BaseAdapterFragment extends BaseControllerFragment {
                protected static final int PROGRESS_FETCH_DATA = 3;
                protected static final int PROGRESS_FETCH_DATA_NEW_AUTO = 4;
                protected static final int PROGRESS_FETCH_NEWER = 2;
                protected static final int PROGRESS_FETCH_OLDER = 1;
                protected final Handler mHandler = new Handler(Looper.getMainLooper());
                protected final PendingRequestHelper mPendingRequestHelper = createPendingRequestHelper();
                protected boolean mRefreshing;

                protected abstract void hideProgress(int i);

                protected abstract void showProgress(int i);

        protected class BasePendingRequestHelper extends PendingRequestHelper {
            protected BasePendingRequestHelper() {
                    }

                    @Override 
            public void showProgress(int progressType) {
                BaseAdapterFragment.this.showProgress(progressType);
                    }

                    @Override 
            public void hideProgress(int progressType) {
                BaseAdapterFragment.this.hideProgress(progressType);
                    }
                }

        protected PendingRequestHelper createPendingRequestHelper() {
            return new BasePendingRequestHelper();
                }

                @Override 
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            this.mPendingRequestHelper.onCreate(this.mAppController, savedInstanceState);
                }

                @Override 
        public void onResume() {
            super.onResume();
            this.mPendingRequestHelper.onResume();
                }

                @Override 
        public void onPause() {
            super.onPause();
            dismissProgressDialog();
                }

                @Override 
        public void onSaveInstanceState(Bundle outState) {
            super.onSaveInstanceState(outState);
            this.mPendingRequestHelper.onSaveInstanceState(outState);
                }

        protected String addRequest(String reqId) {
            return this.mPendingRequestHelper.addRequest(reqId);
                }

        protected String addRequest(String reqId, int fetchType) {
            return this.mPendingRequestHelper.addRequest(reqId, fetchType);
                }

        protected PendingRequest removeRequest(String reqId) {
            return this.mPendingRequestHelper.removeRequest(reqId);
                }

                protected void clearPendingRequests(int fetchType) {
                    this.mPendingRequestHelper.clearRequests(fetchType);
                }

        protected boolean hasPendingRequest() {
            return this.mPendingRequestHelper.hasPendingRequest();
                }

        protected boolean hasPendingRequest(int fetchType) {
            return this.mPendingRequestHelper.hasPendingRequest(fetchType);
                }
            }
