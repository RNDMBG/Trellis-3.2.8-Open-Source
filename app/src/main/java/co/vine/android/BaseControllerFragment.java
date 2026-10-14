            package co.vine.android;

            import android.app.ProgressDialog;
            import android.os.Bundle;
            import co.vine.android.client.AppController;
            import co.vine.android.client.AppSessionListener;
            
            
    public class BaseControllerFragment extends BaseFragment {
                protected AppController mAppController;
                protected AppSessionListener mAppSessionListener;
                protected ProgressDialog mProgressDialog;

                @Override 
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            this.mAppController = AppController.getInstance(getActivity());
                }

                @Override 
        public void onResume() {
            super.onResume();
            if (this.mAppSessionListener != null) {
                this.mAppController.addListener(this.mAppSessionListener);
                    }
                }

                @Override 
        public void onPause() {
            super.onPause();
            AppController appController = this.mAppController;
            if (appController != null && this.mAppSessionListener != null) {
                appController.removeListener(this.mAppSessionListener);
                    }
                }

        public AppController getAppController() {
            return this.mAppController;
                }

        protected void showProgressDialog(int messageResId) {
            if (this.mProgressDialog == null) {
                ProgressDialog d = new ProgressDialog(getActivity(), 2);
                d.setMessage(getString(messageResId));
                d.setProgress(0);
                d.show();
                this.mProgressDialog = d;
                    }
                }

        protected void dismissProgressDialog() {
            if (this.mProgressDialog != null) {
                this.mProgressDialog.dismiss();
                this.mProgressDialog = null;
                    }
                }
            }
