            package com.facebook;

            import android.app.Activity;
            import android.content.Intent;
            import android.os.Bundle;
            import co.vine.android.R;
            import com.edisonwang.android.slog.SLog;
            
            
    public class LoginActivity extends Activity {
                private static final String EXTRA_REQUEST = "request";
                private static final String NULL_CALLING_PKG_ERROR_MSG = "Cannot call LoginActivity with a null calling package. This can occur if the launchMode of the caller is singleInstance.";
                static final String RESULT_KEY = "com.facebook.LoginActivity:Result";
                private static final String SAVED_AUTH_CLIENT = "authorizationClient";
                private static final String SAVED_CALLING_PKG_KEY = "callingPackage";
                private AuthorizationClient authorizationClient;
                private String callingPackage;
                private AuthorizationClient.AuthorizationRequest request;

        static  void access$000(LoginActivity x0, AuthorizationClient.Result x1) {
            x0.onAuthClientCompleted(x1);
                }

                @Override 
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.com_facebook_login_activity_layout);
            if (savedInstanceState != null) {
                this.callingPackage = savedInstanceState.getString(SAVED_CALLING_PKG_KEY);
                this.authorizationClient = (AuthorizationClient) savedInstanceState.getSerializable(SAVED_AUTH_CLIENT);
                    } else {
                this.callingPackage = getCallingPackage();
                this.authorizationClient = new AuthorizationClient();
                this.request = (AuthorizationClient.AuthorizationRequest) getIntent().getSerializableExtra(EXTRA_REQUEST);
                    }
            this.authorizationClient.setContext((Activity) this);
            this.authorizationClient.setOnCompletedListener(new C07591());
            this.authorizationClient.setBackgroundProcessingListener(new C07602());
                }

                
        class C07591 implements AuthorizationClient.OnCompletedListener {
            C07591() {
                    }

                    @Override 
            public void onCompleted(AuthorizationClient.Result outcome) {
                LoginActivity.access$000(LoginActivity.this, outcome);
                    }
                }

                
        class C07602 implements AuthorizationClient.BackgroundProcessingListener {
            C07602() {
                    }

                    @Override 
            public void onBackgroundProcessingStarted() {
                LoginActivity.this.findViewById(R.id.com_facebook_login_activity_progress_bar).setVisibility(0);
                    }

                    @Override 
            public void onBackgroundProcessingStopped() {
                LoginActivity.this.findViewById(R.id.com_facebook_login_activity_progress_bar).setVisibility(8);
                    }
                }

        private void onAuthClientCompleted(AuthorizationClient.Result outcome) {
            this.request = null;
            int resultCode = outcome.code == AuthorizationClient.Result.Code.CANCEL ? 0 : -1;
            Bundle bundle = new Bundle();
            bundle.putSerializable(RESULT_KEY, outcome);
            Intent resultIntent = new Intent();
            resultIntent.putExtras(bundle);
           setResult(resultCode, resultIntent);
           finish();
                }

                @Override 
       public void onResume() {
           super.onResume();
           if (this.callingPackage == null) {
               SLog.m455e(NULL_CALLING_PKG_ERROR_MSG);
               finish();
                    } else {
               this.authorizationClient.startOrContinueAuth(this.request);
                    }
                }

                @Override 
       public void onPause() {
           super.onPause();
           this.authorizationClient.cancelCurrentHandler();
           findViewById(R.id.com_facebook_login_activity_progress_bar).setVisibility(8);
                }

                @Override 
       public void onSaveInstanceState(Bundle outState) {
           super.onSaveInstanceState(outState);
           outState.putString(SAVED_CALLING_PKG_KEY, this.callingPackage);
           outState.putSerializable(SAVED_AUTH_CLIENT, this.authorizationClient);
                }

                @Override 
       protected void onActivityResult(int requestCode, int resultCode, Intent data) {
           this.authorizationClient.onActivityResult(requestCode, resultCode, data);
                }

       static Bundle populateIntentExtras(AuthorizationClient.AuthorizationRequest request) {
           Bundle extras = new Bundle();
           extras.putSerializable(EXTRA_REQUEST, request);
           return extras;
                }
            }
