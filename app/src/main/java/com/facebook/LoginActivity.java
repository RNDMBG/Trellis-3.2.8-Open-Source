            package com.facebook;

            import android.app.Activity;
            import android.content.Intent;
            import android.os.Bundle;
            import co.vine.android.C0257R;
            import com.edisonwang.android.slog.SLog;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 39 */    public class LoginActivity extends Activity {
                private static final String EXTRA_REQUEST = "request";
                private static final String NULL_CALLING_PKG_ERROR_MSG = "Cannot call LoginActivity with a null calling package. This can occur if the launchMode of the caller is singleInstance.";
                static final String RESULT_KEY = "com.facebook.LoginActivity:Result";
                private static final String SAVED_AUTH_CLIENT = "authorizationClient";
                private static final String SAVED_CALLING_PKG_KEY = "callingPackage";
                private AuthorizationClient authorizationClient;
                private String callingPackage;
                private AuthorizationClient.AuthorizationRequest request;

/* 40 */        static /* synthetic */ void access$000(LoginActivity x0, AuthorizationClient.Result x1) {
/* 41 */            x0.onAuthClientCompleted(x1);
                }

                @Override // android.app.Activity
/* 56 */        public void onCreate(Bundle savedInstanceState) {
/* 57 */            super.onCreate(savedInstanceState);
/* 58 */            setContentView(C0257R.layout.com_facebook_login_activity_layout);
/* 60 */            if (savedInstanceState != null) {
/* 61 */                this.callingPackage = savedInstanceState.getString(SAVED_CALLING_PKG_KEY);
/* 62 */                this.authorizationClient = (AuthorizationClient) savedInstanceState.getSerializable(SAVED_AUTH_CLIENT);
                    } else {
/* 64 */                this.callingPackage = getCallingPackage();
/* 65 */                this.authorizationClient = new AuthorizationClient();
/* 66 */                this.request = (AuthorizationClient.AuthorizationRequest) getIntent().getSerializableExtra(EXTRA_REQUEST);
                    }
/* 69 */            this.authorizationClient.setContext((Activity) this);
/* 70 */            this.authorizationClient.setOnCompletedListener(new C07591());
/* 76 */            this.authorizationClient.setBackgroundProcessingListener(new C07602());
                }

                /* JADX INFO: renamed from: com.facebook.LoginActivity$1 */
/* 68 */        class C07591 implements AuthorizationClient.OnCompletedListener {
/* 69 */            C07591() {
                    }

                    @Override // com.facebook.AuthorizationClient.OnCompletedListener
/* 72 */            public void onCompleted(AuthorizationClient.Result outcome) {
/* 73 */                LoginActivity.access$000(LoginActivity.this, outcome);
                    }
                }

                /* JADX INFO: renamed from: com.facebook.LoginActivity$2 */
/* 74 */        class C07602 implements AuthorizationClient.BackgroundProcessingListener {
/* 75 */            C07602() {
                    }

                    @Override // com.facebook.AuthorizationClient.BackgroundProcessingListener
/* 78 */            public void onBackgroundProcessingStarted() {
/* 79 */                LoginActivity.this.findViewById(C0257R.id.com_facebook_login_activity_progress_bar).setVisibility(0);
                    }

                    @Override // com.facebook.AuthorizationClient.BackgroundProcessingListener
/* 83 */            public void onBackgroundProcessingStopped() {
/* 84 */                LoginActivity.this.findViewById(C0257R.id.com_facebook_login_activity_progress_bar).setVisibility(8);
                    }
                }

/* 89 */        private void onAuthClientCompleted(AuthorizationClient.Result outcome) {
/* 90 */            this.request = null;
/* 92 */            int resultCode = outcome.code == AuthorizationClient.Result.Code.CANCEL ? 0 : -1;
/* 95 */            Bundle bundle = new Bundle();
/* 96 */            bundle.putSerializable(RESULT_KEY, outcome);
/* 98 */            Intent resultIntent = new Intent();
/* 99 */            resultIntent.putExtras(bundle);
/* 100 */           setResult(resultCode, resultIntent);
/* 102 */           finish();
                }

                @Override // android.app.Activity
/* 106 */       public void onResume() {
/* 107 */           super.onResume();
/* 112 */           if (this.callingPackage == null) {
/* 113 */               SLog.m455e(NULL_CALLING_PKG_ERROR_MSG);
/* 114 */               finish();
                    } else {
/* 117 */               this.authorizationClient.startOrContinueAuth(this.request);
                    }
                }

                @Override // android.app.Activity
/* 121 */       public void onPause() {
/* 122 */           super.onPause();
/* 124 */           this.authorizationClient.cancelCurrentHandler();
/* 125 */           findViewById(C0257R.id.com_facebook_login_activity_progress_bar).setVisibility(8);
                }

                @Override // android.app.Activity
/* 129 */       public void onSaveInstanceState(Bundle outState) {
/* 130 */           super.onSaveInstanceState(outState);
/* 132 */           outState.putString(SAVED_CALLING_PKG_KEY, this.callingPackage);
/* 133 */           outState.putSerializable(SAVED_AUTH_CLIENT, this.authorizationClient);
                }

                @Override // android.app.Activity
/* 137 */       protected void onActivityResult(int requestCode, int resultCode, Intent data) {
/* 138 */           this.authorizationClient.onActivityResult(requestCode, resultCode, data);
                }

/* 141 */       static Bundle populateIntentExtras(AuthorizationClient.AuthorizationRequest request) {
/* 142 */           Bundle extras = new Bundle();
/* 143 */           extras.putSerializable(EXTRA_REQUEST, request);
/* 144 */           return extras;
                }
            }
