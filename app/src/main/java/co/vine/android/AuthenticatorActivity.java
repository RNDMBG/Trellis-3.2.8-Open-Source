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
            
            
    public class AuthenticatorActivity extends AccountAuthenticatorActivity {
                private static final int DIALOG_PROGRESS_ID = 1;
                public static final String PARAM_AUTH_TOKEN_TYPE = "auth_token_type";
                public static final String PARAM_CONFIRM_CREDENTIALS = "confirm_credentials";
                public static final String PARAM_USERNAME = "username";
                private AccountManager mAccountManager;
                Boolean mConfirmCredentials;
                private String mLoginEmail;
                private EditText mPasswordEditView;

                @Override 
        public void onCreate(Bundle icicle) {
            super.onCreate(icicle);
            requestWindowFeature(3);
            setContentView(C0257R.layout.login);
            getWindow().setFeatureDrawableResource(3, android.R.drawable.ic_dialog_alert);
            this.mAccountManager = AccountManager.get(this);
            Intent intent = getIntent();
            this.mLoginEmail = intent.getStringExtra("username");
            this.mConfirmCredentials = Boolean.valueOf(intent.getBooleanExtra(PARAM_CONFIRM_CREDENTIALS, false));
            TextView usernameView = (TextView) findViewById(C0257R.id.login_username);
            usernameView.setText(this.mLoginEmail);
            this.mPasswordEditView = (EditText) findViewById(C0257R.id.login_password);
                }

        public void onClickHandler(View view) {
            String password = this.mPasswordEditView.getText().toString();
            if (!TextUtils.isEmpty(password)) {
                showDialog(1);
                VineServiceResponder stub = new BinderC01491();
               Intent intent = new Intent(this, (Class<?>) VineService.class);
               intent.setAction(SessionChangedReceiver.ACTION_SESSION_LOGIN);
               intent.putExtra("email", this.mLoginEmail);
               intent.putExtra(VineService.EXTRA_PASSWORD, password);
               intent.putExtra(VineService.EXTRA_CALLBACK, new VineServiceCallback(stub));
               startService(intent);
                    }
                }

                
        class BinderC01491 extends VineServiceResponder {
            BinderC01491() {
                    }

                    @Override 
           public void onServiceResponse(int actionCode, int responseCode, String reasonPhrase, Bundle bundle) {
               AuthenticatorActivity.this.removeDialog(1);
               if (responseCode == 200) {
                   String key = bundle.getString("s_key");
                   if (!AuthenticatorActivity.this.mConfirmCredentials.booleanValue()) {
                       AuthenticatorActivity.this.finishLogin(key);
                       return;
                            } else {
                       AuthenticatorActivity.this.finishConfirmCredentials(key, true);
                       return;
                            }
                        }
               throw new UnsupportedOperationException("handle bad password scenario");
                    }
                }

                @Override 
       protected Dialog onCreateDialog(int id) {
                    switch (id) {
                        case 1:
                   ProgressDialog dialog = new ProgressDialog(this, C0257R.style.ProgressDialogTheme);
                   dialog.setMessage(getText(C0257R.string.login_logging_in));
                   dialog.setIndeterminate(true);
                   dialog.setCancelable(true);
                            return dialog;
                        default:
                   return null;
                    }
                }

       void finishConfirmCredentials(String key, boolean result) {
           saveAuth(key);
           Intent intent = new Intent();
           intent.putExtra("booleanResult", result);
           setAccountAuthenticatorResult(intent.getExtras());
           setResult(-1, intent);
           finish();
                }

       void finishLogin(String key) {
           saveAuth(key);
           Intent intent = new Intent();
           intent.putExtra("authAccount", this.mLoginEmail);
           intent.putExtra("accountType", VineAccountHelper.ACCOUNT_TYPE);
           intent.putExtra("authtoken", key);
           setAccountAuthenticatorResult(intent.getExtras());
           setResult(-1, intent);
           AppController appController = AppController.getInstance(this);
           if (appController.isLoggedIn()) {
               appController.updateCredentials(key);
                    }
           finish();
                }

       private void saveAuth(String key) {
           Account account = new Account(this.mLoginEmail, VineAccountHelper.ACCOUNT_TYPE);
           AccountManager am = this.mAccountManager;
           am.setAuthToken(account, VineAccountHelper.SESSION_TOKEN_KEY, key);
                }
            }
