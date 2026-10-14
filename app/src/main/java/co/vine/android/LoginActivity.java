            package co.vine.android;

            import android.app.ProgressDialog;
            import android.content.Context;
            import android.content.DialogInterface;
            import android.content.Intent;
            import android.os.Bundle;
            import android.text.Spanned;
            import android.text.TextUtils;
            import android.text.style.StyleSpan;
            import android.view.KeyEvent;
            import android.view.Menu;
            import android.view.MenuItem;
            import android.view.View;
            import android.view.inputmethod.InputMethodManager;
            import android.widget.EditText;
            import android.widget.TextView;
            import co.vine.android.api.VineKnownErrors;
            import co.vine.android.api.VineLogin;
            import co.vine.android.client.AppSessionListener;
            import co.vine.android.client.Session;
            import co.vine.android.util.AuthenticationUtils;
            import co.vine.android.util.CommonUtil;
            import co.vine.android.util.DialogUtils;
            import co.vine.android.util.Util;
            import co.vine.android.widgets.PromptDialogSupportFragment;
            
            
    public class LoginActivity extends BaseControllerActionBarActivity {
                private static final int DIALOG_ACTIVATE_ACCOUNT = 1;
                public static final String EXTRA_FINISH = "finish";
                private final PromptDialogSupportFragment.OnDialogDoneListener mActivateAccountDialogDoneListener = new C02413();
                private EditText mEmailAddress;
                private boolean mFinish;
                private EditText mPassword;

        static  boolean access$100(LoginActivity x0) {
            return x0.validateAndDisplayToastIfCredentialsInvalid();
                }

        static  EditText access$200(LoginActivity x0) {
            return x0.mPassword;
                }

        static  void access$300(LoginActivity x0, boolean x1) {
            x0.login(x1);
                }

        static  EditText access$400(LoginActivity x0) {
            return x0.mEmailAddress;
                }

        static  void access$500(LoginActivity x0, int x1, String x2, int x3) {
            x0.completeLogin(x1, x2, x3);
                }

                @Override 
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState, C0257R.layout.login, false);
            setupActionBar((Boolean) true, (Boolean) true, C0257R.string.login_sign_in, (Boolean) true, (Boolean) true);
            this.mAppSessionListener = new LoginSessionListener(this, null);
            this.mEmailAddress = (EditText) findViewById(C0257R.id.login_username);
            this.mPassword = (EditText) findViewById(C0257R.id.login_password);
            this.mPassword.setOnEditorActionListener(new C02391());
            StyleSpan[] mStyleSpanBoldSingle = {new StyleSpan(1)};
            Spanned resetPasswordSpan = Util.getSpannedText(mStyleSpanBoldSingle, getString(C0257R.string.login_forgot_password), CommonUtil.MARKER_QUOTES);
            TextView mResetPassword = (TextView) findViewById(C0257R.id.login_reset_password);
            mResetPassword.setText(resetPasswordSpan, TextView.BufferType.SPANNABLE);
            mResetPassword.setOnClickListener(new ViewOnClickListenerC02402());
            Intent intent = getIntent();
            if (intent != null) {
                this.mFinish = intent.getBooleanExtra("finish", false);
                    }
                }

                
        class C02391 implements TextView.OnEditorActionListener {
            C02391() {
                    }

                    @Override 
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event != null && event.getKeyCode() == 66) || (actionId == 6 && LoginActivity.access$100(LoginActivity.this))) {
                    InputMethodManager imm = (InputMethodManager) LoginActivity.this.getSystemService("input_method");
                    imm.hideSoftInputFromWindow(LoginActivity.access$200(LoginActivity.this).getWindowToken(), 0);
                    LoginActivity.access$300(LoginActivity.this, false);
                    return true;
                        }
                return true;
                    }
                }

                
        class ViewOnClickListenerC02402 implements View.OnClickListener {
            ViewOnClickListenerC02402() {
                    }

                    @Override 
            public void onClick(View view) {
                if (view.getId() == C0257R.id.login_reset_password) {
                    Intent intent = new Intent(LoginActivity.this, (Class<?>) ResetPasswordActivity.class);
                    String email = LoginActivity.access$400(LoginActivity.this).getText().toString();
                    if (!TextUtils.isEmpty(email)) {
                        intent.putExtra("email", email);
                            }
                    LoginActivity.this.startActivity(intent);
                        }
                    }
                }

                @Override 
        public boolean onCreateOptionsMenu(Menu menu) {
            getMenuInflater().inflate(C0257R.menu.login, menu);
            return true;
                }

                @Override 
        public boolean onOptionsItemSelected(MenuItem item) {
            if (item.getItemId() != C0257R.id.sign_in) {
                        return super.onOptionsItemSelected(item);
                    }
           if (validateAndDisplayToastIfCredentialsInvalid()) {
               login(false);
                    }
           return true;
                }

       private boolean validateAndDisplayToastIfCredentialsInvalid() {
           String emailAddress = this.mEmailAddress.getText().toString();
           if (emailAddress.isEmpty()) {
               Util.showCenteredToast(this, C0257R.string.login_empty_email);
                        return false;
                    }
           if (!AuthenticationUtils.isEmailAddressValid(emailAddress)) {
               Util.showCenteredToast(this, C0257R.string.login_invalid_email);
                        return false;
                    }
           AuthenticationUtils.Result passwordValidationResult = AuthenticationUtils.validatePassword(this.mPassword.getText().toString());
                    switch (passwordValidationResult) {
                        case EMPTY:
                   Util.showCenteredToast(this, C0257R.string.login_empty_password);
                            return false;
                        case TOO_SHORT:
                   Util.showCenteredToast(this, C0257R.string.login_password_too_short);
                            return false;
                        default:
                   return true;
                    }
                }

       private void login(boolean reactivate) {
           String emailAddress = this.mEmailAddress.getText().toString();
           String password = this.mPassword.getText().toString();
           ProgressDialog dialog = new ProgressDialog(this, C0257R.style.ProgressDialogTheme);
           dialog.setProgressStyle(0);
           dialog.setMessage(getString(C0257R.string.login_logging_in));
           DialogUtils.showDialogUnsafe(dialog);
           this.mProgressDialog = dialog;
           Session session = this.mAppController.getSession(emailAddress);
           VineLogin login = new VineLogin(null, emailAddress, 0L, null);
           this.mAppController.login(session, login, password, reactivate);
                }

       private void completeLogin(int statusCode, String reason, int errorCode) {
                    switch (statusCode) {
                        case 200:
                   if (!this.mFinish) {
                       StartActivity.toStart(this);
                            }
                   finish();
                            break;
                        default:
                   if (errorCode == VineKnownErrors.ACCOUNT_DEACTIVATED.code) {
                       PromptDialogSupportFragment dialogFragment = PromptDialogSupportFragment.newInstance(1);
                       dialogFragment.setMessage(C0257R.string.settings_activate_account_dialog);
                       dialogFragment.setTitle(C0257R.string.settings_activate_account_title);
                       dialogFragment.setNegativeButton(C0257R.string.cancel);
                       dialogFragment.setPositiveButton(C0257R.string.settings_activate_account_confirm);
                       dialogFragment.setListener(this.mActivateAccountDialogDoneListener);
                       dialogFragment.show(getSupportFragmentManager());
                            } else {
                       Util.showCenteredToast(this, reason);
                            }
                            break;
                    }
                }

                
       class C02413 implements PromptDialogSupportFragment.OnDialogDoneListener {
           C02413() {
                    }

                    @Override 
           public void onDialogDone(DialogInterface dialog, int id, int which) {
               if (which == -1) {
                   LoginActivity.access$300(LoginActivity.this, true);
                        }
                    }
                }

       public static Intent getIntent(Context context) {
           return new Intent(context, (Class<?>) LoginActivity.class);
                }

       public static Intent getIntentWithFinish(Context context) {
           Intent intent = new Intent(context, (Class<?>) LoginActivity.class);
           intent.putExtra("finish", true);
           return intent;
                }

       private class LoginSessionListener extends AppSessionListener {
           private LoginSessionListener() {
                    }

            LoginSessionListener(LoginActivity x0, C02391 x1) {
               this();
                    }

                    @Override 
           public void onLoginComplete(Session session, String reqId, int statusCode, String reasonPhrase, int errorCode, VineLogin login) {
               LoginActivity.access$500(LoginActivity.this, statusCode, reasonPhrase, errorCode);
               LoginActivity.this.mAppController.getEditions();
               LoginActivity.this.dismissDialog();
                    }
                }
            }
