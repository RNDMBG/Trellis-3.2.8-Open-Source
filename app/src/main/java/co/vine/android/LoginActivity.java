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
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 35 */    public class LoginActivity extends BaseControllerActionBarActivity {
                private static final int DIALOG_ACTIVATE_ACCOUNT = 1;
                public static final String EXTRA_FINISH = "finish";
                private final PromptDialogSupportFragment.OnDialogDoneListener mActivateAccountDialogDoneListener = new C02413();
                private EditText mEmailAddress;
                private boolean mFinish;
                private EditText mPassword;

/* 36 */        static /* synthetic */ boolean access$100(LoginActivity x0) {
/* 37 */            return x0.validateAndDisplayToastIfCredentialsInvalid();
                }

/* 36 */        static /* synthetic */ EditText access$200(LoginActivity x0) {
/* 37 */            return x0.mPassword;
                }

/* 36 */        static /* synthetic */ void access$300(LoginActivity x0, boolean x1) {
/* 37 */            x0.login(x1);
                }

/* 36 */        static /* synthetic */ EditText access$400(LoginActivity x0) {
/* 37 */            return x0.mEmailAddress;
                }

/* 36 */        static /* synthetic */ void access$500(LoginActivity x0, int x1, String x2, int x3) {
/* 37 */            x0.completeLogin(x1, x2, x3);
                }

                @Override // co.vine.android.BaseActionBarActivity, android.support.v7.app.ActionBarActivity, android.support.v4.app.FragmentActivity, android.app.Activity
/* 47 */        public void onCreate(Bundle savedInstanceState) {
/* 48 */            super.onCreate(savedInstanceState, C0257R.layout.login, false);
/* 49 */            setupActionBar((Boolean) true, (Boolean) true, C0257R.string.login_sign_in, (Boolean) true, (Boolean) true);
/* 50 */            this.mAppSessionListener = new LoginSessionListener(this, null);
/* 53 */            this.mEmailAddress = (EditText) findViewById(C0257R.id.login_username);
/* 54 */            this.mPassword = (EditText) findViewById(C0257R.id.login_password);
/* 55 */            this.mPassword.setOnEditorActionListener(new C02391());
/* 67 */            StyleSpan[] mStyleSpanBoldSingle = {new StyleSpan(1)};
/* 68 */            Spanned resetPasswordSpan = Util.getSpannedText(mStyleSpanBoldSingle, getString(C0257R.string.login_forgot_password), CommonUtil.MARKER_QUOTES);
/* 69 */            TextView mResetPassword = (TextView) findViewById(C0257R.id.login_reset_password);
/* 70 */            mResetPassword.setText(resetPasswordSpan, TextView.BufferType.SPANNABLE);
/* 71 */            mResetPassword.setOnClickListener(new ViewOnClickListenerC02402());
/* 85 */            Intent intent = getIntent();
/* 86 */            if (intent != null) {
/* 87 */                this.mFinish = intent.getBooleanExtra("finish", false);
                    }
                }

                /* JADX INFO: renamed from: co.vine.android.LoginActivity$1 */
/* 53 */        class C02391 implements TextView.OnEditorActionListener {
/* 54 */            C02391() {
                    }

                    @Override // android.widget.TextView.OnEditorActionListener
/* 57 */            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
/* 58 */                if ((event != null && event.getKeyCode() == 66) || (actionId == 6 && LoginActivity.access$100(LoginActivity.this))) {
/* 60 */                    InputMethodManager imm = (InputMethodManager) LoginActivity.this.getSystemService("input_method");
/* 61 */                    imm.hideSoftInputFromWindow(LoginActivity.access$200(LoginActivity.this).getWindowToken(), 0);
/* 62 */                    LoginActivity.access$300(LoginActivity.this, false);
/* 64 */                    return true;
                        }
/* 64 */                return true;
                    }
                }

                /* JADX INFO: renamed from: co.vine.android.LoginActivity$2 */
/* 69 */        class ViewOnClickListenerC02402 implements View.OnClickListener {
/* 70 */            ViewOnClickListenerC02402() {
                    }

                    @Override // android.view.View.OnClickListener
/* 73 */            public void onClick(View view) {
/* 74 */                if (view.getId() == C0257R.id.login_reset_password) {
/* 75 */                    Intent intent = new Intent(LoginActivity.this, (Class<?>) ResetPasswordActivity.class);
/* 76 */                    String email = LoginActivity.access$400(LoginActivity.this).getText().toString();
/* 77 */                    if (!TextUtils.isEmpty(email)) {
/* 78 */                        intent.putExtra("email", email);
                            }
/* 80 */                    LoginActivity.this.startActivity(intent);
                        }
                    }
                }

                @Override // co.vine.android.BaseActionBarActivity, android.app.Activity
/* 92 */        public boolean onCreateOptionsMenu(Menu menu) {
/* 93 */            getMenuInflater().inflate(C0257R.menu.login, menu);
/* 94 */            return true;
                }

                @Override // co.vine.android.BaseActionBarActivity, android.app.Activity
/* 98 */        public boolean onOptionsItemSelected(MenuItem item) {
/* 99 */            if (item.getItemId() != C0257R.id.sign_in) {
                        return super.onOptionsItemSelected(item);
                    }
/* 100 */           if (validateAndDisplayToastIfCredentialsInvalid()) {
/* 101 */               login(false);
                    }
/* 103 */           return true;
                }

/* 112 */       private boolean validateAndDisplayToastIfCredentialsInvalid() {
/* 113 */           String emailAddress = this.mEmailAddress.getText().toString();
/* 114 */           if (emailAddress.isEmpty()) {
/* 115 */               Util.showCenteredToast(this, C0257R.string.login_empty_email);
                        return false;
                    }
/* 117 */           if (!AuthenticationUtils.isEmailAddressValid(emailAddress)) {
/* 118 */               Util.showCenteredToast(this, C0257R.string.login_invalid_email);
                        return false;
                    }
/* 122 */           AuthenticationUtils.Result passwordValidationResult = AuthenticationUtils.validatePassword(this.mPassword.getText().toString());
                    switch (passwordValidationResult) {
                        case EMPTY:
/* 125 */                   Util.showCenteredToast(this, C0257R.string.login_empty_password);
                            return false;
                        case TOO_SHORT:
/* 128 */                   Util.showCenteredToast(this, C0257R.string.login_password_too_short);
                            return false;
                        default:
/* 131 */                   return true;
                    }
                }

/* 134 */       private void login(boolean reactivate) {
/* 135 */           String emailAddress = this.mEmailAddress.getText().toString();
/* 136 */           String password = this.mPassword.getText().toString();
/* 138 */           ProgressDialog dialog = new ProgressDialog(this, C0257R.style.ProgressDialogTheme);
/* 139 */           dialog.setProgressStyle(0);
/* 140 */           dialog.setMessage(getString(C0257R.string.login_logging_in));
/* 141 */           DialogUtils.showDialogUnsafe(dialog);
/* 142 */           this.mProgressDialog = dialog;
/* 144 */           Session session = this.mAppController.getSession(emailAddress);
/* 145 */           VineLogin login = new VineLogin(null, emailAddress, 0L, null);
/* 146 */           this.mAppController.login(session, login, password, reactivate);
                }

/* 149 */       private void completeLogin(int statusCode, String reason, int errorCode) {
                    switch (statusCode) {
                        case 200:
/* 152 */                   if (!this.mFinish) {
/* 153 */                       StartActivity.toStart(this);
                            }
/* 155 */                   finish();
                            break;
                        default:
/* 158 */                   if (errorCode == VineKnownErrors.ACCOUNT_DEACTIVATED.code) {
/* 159 */                       PromptDialogSupportFragment dialogFragment = PromptDialogSupportFragment.newInstance(1);
/* 160 */                       dialogFragment.setMessage(C0257R.string.settings_activate_account_dialog);
/* 161 */                       dialogFragment.setTitle(C0257R.string.settings_activate_account_title);
/* 162 */                       dialogFragment.setNegativeButton(C0257R.string.cancel);
/* 163 */                       dialogFragment.setPositiveButton(C0257R.string.settings_activate_account_confirm);
/* 164 */                       dialogFragment.setListener(this.mActivateAccountDialogDoneListener);
/* 165 */                       dialogFragment.show(getSupportFragmentManager());
                            } else {
/* 167 */                       Util.showCenteredToast(this, reason);
                            }
                            break;
                    }
                }

                /* JADX INFO: renamed from: co.vine.android.LoginActivity$3 */
/* 172 */       class C02413 implements PromptDialogSupportFragment.OnDialogDoneListener {
/* 173 */           C02413() {
                    }

                    @Override // co.vine.android.widgets.PromptDialogFragment.OnDialogDoneListener
/* 176 */           public void onDialogDone(DialogInterface dialog, int id, int which) {
/* 177 */               if (which == -1) {
/* 178 */                   LoginActivity.access$300(LoginActivity.this, true);
                        }
                    }
                }

/* 183 */       public static Intent getIntent(Context context) {
/* 184 */           return new Intent(context, (Class<?>) LoginActivity.class);
                }

/* 187 */       public static Intent getIntentWithFinish(Context context) {
/* 188 */           Intent intent = new Intent(context, (Class<?>) LoginActivity.class);
/* 189 */           intent.putExtra("finish", true);
/* 190 */           return intent;
                }

/* 191 */       private class LoginSessionListener extends AppSessionListener {
/* 192 */           private LoginSessionListener() {
                    }

/* 192 */           /* synthetic */ LoginSessionListener(LoginActivity x0, C02391 x1) {
/* 193 */               this();
                    }

                    @Override // co.vine.android.client.AppSessionListener
/* 196 */           public void onLoginComplete(Session session, String reqId, int statusCode, String reasonPhrase, int errorCode, VineLogin login) {
/* 197 */               LoginActivity.access$500(LoginActivity.this, statusCode, reasonPhrase, errorCode);
/* 198 */               LoginActivity.this.mAppController.getEditions();
/* 199 */               LoginActivity.this.dismissDialog();
                    }
                }
            }
