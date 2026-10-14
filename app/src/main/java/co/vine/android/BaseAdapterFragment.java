            package co.vine.android;

            import android.os.Bundle;
            import android.os.Handler;
            import android.os.Looper;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 21 */    public abstract class BaseAdapterFragment extends BaseControllerFragment {
                protected static final int PROGRESS_FETCH_DATA = 3;
                protected static final int PROGRESS_FETCH_DATA_NEW_AUTO = 4;
                protected static final int PROGRESS_FETCH_NEWER = 2;
                protected static final int PROGRESS_FETCH_OLDER = 1;
                protected final Handler mHandler = new Handler(Looper.getMainLooper());
                protected final PendingRequestHelper mPendingRequestHelper = createPendingRequestHelper();
                protected boolean mRefreshing;

                protected abstract void hideProgress(int i);

                protected abstract void showProgress(int i);

/* 22 */        protected class BasePendingRequestHelper extends PendingRequestHelper {
/* 23 */            protected BasePendingRequestHelper() {
                    }

                    @Override // co.vine.android.PendingRequestHelper
/* 27 */            public void showProgress(int progressType) {
/* 28 */                BaseAdapterFragment.this.showProgress(progressType);
                    }

                    @Override // co.vine.android.PendingRequestHelper
/* 32 */            public void hideProgress(int progressType) {
/* 33 */                BaseAdapterFragment.this.hideProgress(progressType);
                    }
                }

/* 37 */        protected PendingRequestHelper createPendingRequestHelper() {
/* 38 */            return new BasePendingRequestHelper();
                }

                @Override // co.vine.android.BaseControllerFragment, android.support.v4.app.Fragment
/* 42 */        public void onCreate(Bundle savedInstanceState) {
/* 43 */            super.onCreate(savedInstanceState);
/* 44 */            this.mPendingRequestHelper.onCreate(this.mAppController, savedInstanceState);
                }

                @Override // co.vine.android.BaseControllerFragment, co.vine.android.BaseFragment, android.support.v4.app.Fragment
/* 48 */        public void onResume() {
/* 49 */            super.onResume();
/* 50 */            this.mPendingRequestHelper.onResume();
                }

                @Override // co.vine.android.BaseControllerFragment, android.support.v4.app.Fragment
/* 57 */        public void onPause() {
/* 58 */            super.onPause();
/* 59 */            dismissProgressDialog();
                }

                @Override // android.support.v4.app.Fragment
/* 63 */        public void onSaveInstanceState(Bundle outState) {
/* 64 */            super.onSaveInstanceState(outState);
/* 65 */            this.mPendingRequestHelper.onSaveInstanceState(outState);
                }

/* 71 */        protected String addRequest(String reqId) {
/* 72 */            return this.mPendingRequestHelper.addRequest(reqId);
                }

/* 78 */        protected String addRequest(String reqId, int fetchType) {
/* 79 */            return this.mPendingRequestHelper.addRequest(reqId, fetchType);
                }

/* 82 */        protected PendingRequest removeRequest(String reqId) {
/* 83 */            return this.mPendingRequestHelper.removeRequest(reqId);
                }

                protected void clearPendingRequests(int fetchType) {
                    this.mPendingRequestHelper.clearRequests(fetchType);
                }

/* 86 */        protected boolean hasPendingRequest() {
/* 87 */            return this.mPendingRequestHelper.hasPendingRequest();
                }

/* 90 */        protected boolean hasPendingRequest(int fetchType) {
/* 91 */            return this.mPendingRequestHelper.hasPendingRequest(fetchType);
                }
            }
