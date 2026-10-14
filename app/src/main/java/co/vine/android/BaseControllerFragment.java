            package co.vine.android;

            import android.app.ProgressDialog;
            import android.os.Bundle;
            import co.vine.android.client.AppController;
            import co.vine.android.client.AppSessionListener;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 18 */    public class BaseControllerFragment extends BaseFragment {
                protected AppController mAppController;
                protected AppSessionListener mAppSessionListener;
                protected ProgressDialog mProgressDialog;

                @Override // android.support.v4.app.Fragment
/* 19 */        public void onCreate(Bundle savedInstanceState) {
/* 20 */            super.onCreate(savedInstanceState);
/* 21 */            this.mAppController = AppController.getInstance(getActivity());
                }

                @Override // co.vine.android.BaseFragment, android.support.v4.app.Fragment
/* 25 */        public void onResume() {
/* 26 */            super.onResume();
/* 27 */            if (this.mAppSessionListener != null) {
/* 28 */                this.mAppController.addListener(this.mAppSessionListener);
                    }
                }

                @Override // android.support.v4.app.Fragment
/* 33 */        public void onPause() {
/* 34 */            super.onPause();
/* 35 */            AppController appController = this.mAppController;
/* 36 */            if (appController != null && this.mAppSessionListener != null) {
/* 38 */                appController.removeListener(this.mAppSessionListener);
                    }
                }

/* 43 */        public AppController getAppController() {
/* 44 */            return this.mAppController;
                }

/* 47 */        protected void showProgressDialog(int messageResId) {
/* 48 */            if (this.mProgressDialog == null) {
/* 49 */                ProgressDialog d = new ProgressDialog(getActivity(), 2);
/* 50 */                d.setMessage(getString(messageResId));
/* 51 */                d.setProgress(0);
/* 52 */                d.show();
/* 53 */                this.mProgressDialog = d;
                    }
                }

/* 57 */        protected void dismissProgressDialog() {
/* 58 */            if (this.mProgressDialog != null) {
/* 59 */                this.mProgressDialog.dismiss();
/* 60 */                this.mProgressDialog = null;
                    }
                }
            }
