            package co.vine.android;

            import android.accounts.Account;
            import android.accounts.AccountAuthenticatorActivity;
            import android.accounts.AccountManager;
            import android.app.Dialog;
            import android.app.ProgressDialog;
            import android.content.Intent;
            import android.os.Bundle;
            import android.text.TextUtils;
            import android.view.View;
            import android.widget.EditText;
            import android.widget.TextView;
            import co.vine.android.client.AppController;
            import co.vine.android.client.VineAccountHelper;
            import co.vine.android.service.SessionChangedReceiver;
            import co.vine.android.service.VineService;
            import co.vine.android.service.VineServiceCallback;
            import co.vine.android.service.VineServiceResponder;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 62 */    public class AuthenticatorActivity extends AccountAuthenticatorActivity {
                private static final int DIALOG_PROGRESS_ID = 1;
                public static final String PARAM_AUTH_TOKEN_TYPE = "auth_token_type";
                public static final String PARAM_CONFIRM_CREDENTIALS = "confirm_credentials";
                public static final String PARAM_USERNAME = "username";
                private AccountManager mAccountManager;
                Boolean mConfirmCredentials;
                private String mLoginEmail;
                private EditText mPasswordEditView;

                @Override // android.accounts.AccountAuthenticatorActivity, android.app.Activity
/* 63 */        public void onCreate(Bundle icicle) {
/* 64 */            super.onCreate(icicle);
/* 66 */            requestWindowFeature(3);
/* 67 */            setContentView(C0257R.layout.login);
/* 68 */            getWindow().setFeatureDrawableResource(3, android.R.drawable.ic_dialog_alert);
/* 71 */            this.mAccountManager = AccountManager.get(this);
/* 73 */            Intent intent = getIntent();
/* 74 */            this.mLoginEmail = intent.getStringExtra("username");
/* 76 */            this.mConfirmCredentials = Boolean.valueOf(intent.getBooleanExtra(PARAM_CONFIRM_CREDENTIALS, false));
/* 78 */            TextView usernameView = (TextView) findViewById(C0257R.id.login_username);
/* 79 */            usernameView.setText(this.mLoginEmail);
/* 81 */            this.mPasswordEditView = (EditText) findViewById(C0257R.id.login_password);
                }

/* 90 */        public void onClickHandler(View view) {
/* 91 */            String password = this.mPasswordEditView.getText().toString();
/* 92 */            if (!TextUtils.isEmpty(password)) {
/* 96 */                showDialog(1);
/* 98 */                VineServiceResponder stub = new BinderC01491();
/* 119 */               Intent intent = new Intent(this, (Class<?>) VineService.class);
/* 120 */               intent.setAction(SessionChangedReceiver.ACTION_SESSION_LOGIN);
/* 121 */               intent.putExtra("email", this.mLoginEmail);
/* 122 */               intent.putExtra(VineService.EXTRA_PASSWORD, password);
/* 123 */               intent.putExtra(VineService.EXTRA_CALLBACK, new VineServiceCallback(stub));
/* 124 */               startService(intent);
                    }
                }

                /* JADX INFO: renamed from: co.vine.android.AuthenticatorActivity$1 */
/* 96 */        class BinderC01491 extends VineServiceResponder {
/* 97 */            BinderC01491() {
                    }

                    @Override // co.vine.android.service.VineServiceResponder
/* 103 */           public void onServiceResponse(int actionCode, int responseCode, String reasonPhrase, Bundle bundle) {
/* 104 */               AuthenticatorActivity.this.removeDialog(1);
/* 106 */               if (responseCode == 200) {
/* 107 */                   String key = bundle.getString("s_key");
/* 108 */                   if (!AuthenticatorActivity.this.mConfirmCredentials.booleanValue()) {
/* 109 */                       AuthenticatorActivity.this.finishLogin(key);
/* 116 */                       return;
                            } else {
/* 111 */                       AuthenticatorActivity.this.finishConfirmCredentials(key, true);
/* 116 */                       return;
                            }
                        }
/* 114 */               throw new UnsupportedOperationException("handle bad password scenario");
                    }
                }

                @Override // android.app.Activity
/* 128 */       protected Dialog onCreateDialog(int id) {
                    switch (id) {
                        case 1:
/* 131 */                   ProgressDialog dialog = new ProgressDialog(this, C0257R.style.ProgressDialogTheme);
/* 132 */                   dialog.setMessage(getText(C0257R.string.login_logging_in));
/* 133 */                   dialog.setIndeterminate(true);
/* 134 */                   dialog.setCancelable(true);
                            return dialog;
                        default:
/* 139 */                   return null;
                    }
                }

/* 151 */       void finishConfirmCredentials(String key, boolean result) {
/* 152 */           saveAuth(key);
/* 153 */           Intent intent = new Intent();
/* 154 */           intent.putExtra("booleanResult", result);
/* 155 */           setAccountAuthenticatorResult(intent.getExtras());
/* 156 */           setResult(-1, intent);
/* 157 */           finish();
                }

/* 167 */       void finishLogin(String key) {
/* 168 */           saveAuth(key);
/* 169 */           Intent intent = new Intent();
/* 170 */           intent.putExtra("authAccount", this.mLoginEmail);
/* 171 */           intent.putExtra("accountType", VineAccountHelper.ACCOUNT_TYPE);
/* 172 */           intent.putExtra("authtoken", key);
/* 173 */           setAccountAuthenticatorResult(intent.getExtras());
/* 174 */           setResult(-1, intent);
/* 177 */           AppController appController = AppController.getInstance(this);
/* 178 */           if (appController.isLoggedIn()) {
/* 179 */               appController.updateCredentials(key);
                    }
/* 181 */           finish();
                }

/* 189 */       private void saveAuth(String key) {
/* 190 */           Account account = new Account(this.mLoginEmail, VineAccountHelper.ACCOUNT_TYPE);
/* 191 */           AccountManager am = this.mAccountManager;
/* 192 */           am.setAuthToken(account, VineAccountHelper.SESSION_TOKEN_KEY, key);
                }
            }
