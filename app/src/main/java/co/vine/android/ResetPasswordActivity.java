            package co.vine.android;

            import android.app.ProgressDialog;
            import android.os.Bundle;
            import android.text.TextUtils;
            import android.view.Menu;
            import android.view.MenuItem;
            import android.view.inputmethod.InputMethodManager;
            import android.widget.EditText;
            import co.vine.android.client.AppSessionListener;
            import co.vine.android.util.AuthenticationUtils;
            import co.vine.android.util.Util;
            import co.vine.android.widget.Typefaces;
            import co.vine.android.widgets.PromptDialogSupportFragment;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 30 */    public class ResetPasswordActivity extends BaseControllerActionBarActivity {
                private static final int DIALOG_CONFIRMED = 1;
                public static final String EXTRA_EMAIL = "email";
                private EditText mEmailAddress;

                @Override // co.vine.android.BaseActionBarActivity, android.support.v7.app.ActionBarActivity, android.support.v4.app.FragmentActivity, android.app.Activity
/* 31 */        public void onCreate(Bundle savedInstanceState) {
/* 32 */            super.onCreate(savedInstanceState, C0257R.layout.reset_password, false);
/* 33 */            InputMethodManager imm = (InputMethodManager) getSystemService("input_method");
/* 34 */            imm.showSoftInputFromInputMethod(getWindow().getDecorView().getWindowToken(), 0);
/* 35 */            setupActionBar((Boolean) true, (Boolean) true, C0257R.string.password_reset_title, (Boolean) true, (Boolean) true);
/* 36 */            this.mAppSessionListener = new ResetPasswordListener(this, null);
/* 38 */            this.mEmailAddress = (EditText) findViewById(C0257R.id.email);
/* 39 */            this.mEmailAddress.setTypeface(Typefaces.get(this).getContentTypeface(0, 4));
/* 40 */            Bundle bundle = getIntent().getExtras();
/* 41 */            if (bundle != null) {
/* 42 */                String emailAddress = bundle.getString("email");
/* 43 */                if (!TextUtils.isEmpty(emailAddress)) {
/* 44 */                    this.mEmailAddress.setText(emailAddress);
/* 45 */                    this.mEmailAddress.setSelection(this.mEmailAddress.length());
                        }
/* 48 */                int backgroundColor = bundle.getInt("color");
/* 49 */                if (backgroundColor != 0) {
/* 50 */                    setActionBarColor(backgroundColor);
                        }
                    }
                }

                @Override // co.vine.android.BaseActionBarActivity, android.app.Activity
/* 56 */        public boolean onCreateOptionsMenu(Menu menu) {
/* 57 */            getMenuInflater().inflate(C0257R.menu.reset_password, menu);
/* 58 */            return true;
                }

                @Override // co.vine.android.BaseActionBarActivity, android.app.Activity
/* 62 */        public boolean onOptionsItemSelected(MenuItem item) {
/* 63 */            if (item.getItemId() == 2131427946) {
/* 64 */                String emailAddress = this.mEmailAddress.getText().toString();
/* 65 */                if (emailAddress.isEmpty()) {
/* 66 */                    Util.showCenteredToast(this, C0257R.string.login_empty_email);
                            return false;
                        }
/* 68 */                if (!AuthenticationUtils.isEmailAddressValid(emailAddress)) {
/* 69 */                    Util.showCenteredToast(this, C0257R.string.login_invalid_email);
                            return false;
                        }
/* 72 */                ProgressDialog dialog = new ProgressDialog(this, C0257R.style.ProgressDialogTheme);
/* 73 */                dialog.setProgress(0);
/* 74 */                dialog.setMessage(getString(C0257R.string.dialog_sending));
/* 75 */                dialog.show();
/* 76 */                this.mProgressDialog = dialog;
/* 77 */                this.mAppController.resetPassword(this.mEmailAddress.getText().toString());
/* 79 */                return true;
                    }
/* 81 */            return super.onOptionsItemSelected(item);
                }

                @Override // co.vine.android.BaseControllerActionBarActivity, co.vine.android.BaseActionBarActivity, android.support.v4.app.FragmentActivity, android.app.Activity
/* 86 */        protected void onPause() {
/* 87 */            super.onPause();
/* 88 */            Util.setSoftKeyboardVisibility(this, this.mEmailAddress, false);
                }

                @Override // co.vine.android.BaseControllerActionBarActivity, co.vine.android.BaseActionBarActivity, android.support.v4.app.FragmentActivity, android.app.Activity
/* 92 */        protected void onResume() {
/* 93 */            super.onResume();
/* 94 */            Util.setSoftKeyboardVisibility(this, this.mEmailAddress, true);
                }

/* 95 */        private class ResetPasswordListener extends AppSessionListener {
/* 96 */            private ResetPasswordListener() {
                    }

/* 96 */            /* synthetic */ ResetPasswordListener(ResetPasswordActivity x0, Object x1) {
/* 97 */                this();
                    }

                    @Override // co.vine.android.client.AppSessionListener
/* 99 */            public void onResetPasswordComplete(String reqId, int statusCode, String reasonPhrase) {
                        int error;
/* 100 */               ResetPasswordActivity.this.dismissDialog();
                        switch (statusCode) {
                            case 200:
/* 105 */                       error = C0257R.string.password_reset_confirm;
                                break;
                            case 404:
/* 108 */                       error = C0257R.string.password_reset_not_found;
                                break;
                            default:
/* 111 */                       error = C0257R.string.password_reset_error;
                                break;
                        }
/* 115 */               PromptDialogSupportFragment fragment = PromptDialogSupportFragment.newInstance(1);
/* 116 */               fragment.setMessage(error);
/* 117 */               fragment.setPositiveButton(C0257R.string.f88ok);
/* 118 */               fragment.show(ResetPasswordActivity.this.getSupportFragmentManager());
                    }
                }
            }
