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
            
            
    public class ResetPasswordActivity extends BaseControllerActionBarActivity {
                private static final int DIALOG_CONFIRMED = 1;
                public static final String EXTRA_EMAIL = "email";
                private EditText mEmailAddress;

                @Override 
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState, R.layout.reset_password, false);
            InputMethodManager imm = (InputMethodManager) getSystemService("input_method");
            imm.showSoftInputFromInputMethod(getWindow().getDecorView().getWindowToken(), 0);
            setupActionBar((Boolean) true, (Boolean) true, R.string.password_reset_title, (Boolean) true, (Boolean) true);
            this.mAppSessionListener = new ResetPasswordListener(this, null);
            this.mEmailAddress = (EditText) findViewById(R.id.email);
            this.mEmailAddress.setTypeface(Typefaces.get(this).getContentTypeface(0, 4));
            Bundle bundle = getIntent().getExtras();
            if (bundle != null) {
                String emailAddress = bundle.getString("email");
                if (!TextUtils.isEmpty(emailAddress)) {
                    this.mEmailAddress.setText(emailAddress);
                    this.mEmailAddress.setSelection(this.mEmailAddress.length());
                        }
                int backgroundColor = bundle.getInt("color");
                if (backgroundColor != 0) {
                    setActionBarColor(backgroundColor);
                        }
                    }
                }

                @Override 
        public boolean onCreateOptionsMenu(Menu menu) {
            getMenuInflater().inflate(R.menu.reset_password, menu);
            return true;
                }

                @Override 
        public boolean onOptionsItemSelected(MenuItem item) {
            if (item.getItemId() == R.id.send) {
                String emailAddress = this.mEmailAddress.getText().toString();
                if (emailAddress.isEmpty()) {
                    Util.showCenteredToast(this, R.string.login_empty_email);
                            return false;
                        }
                if (!AuthenticationUtils.isEmailAddressValid(emailAddress)) {
                    Util.showCenteredToast(this, R.string.login_invalid_email);
                            return false;
                        }
                ProgressDialog dialog = new ProgressDialog(this, R.style.ProgressDialogTheme);
                dialog.setProgress(0);
                dialog.setMessage(getString(R.string.dialog_sending));
                dialog.show();
                this.mProgressDialog = dialog;
                this.mAppController.resetPassword(this.mEmailAddress.getText().toString());
                return true;
                    }
            return super.onOptionsItemSelected(item);
                }

                @Override 
        protected void onPause() {
            super.onPause();
            Util.setSoftKeyboardVisibility(this, this.mEmailAddress, false);
                }

                @Override 
        protected void onResume() {
            super.onResume();
            Util.setSoftKeyboardVisibility(this, this.mEmailAddress, true);
                }

        private class ResetPasswordListener extends AppSessionListener {
            private ResetPasswordListener() {
                    }

             ResetPasswordListener(ResetPasswordActivity x0, Object x1) {
                this();
                    }

                    @Override 
            public void onResetPasswordComplete(String reqId, int statusCode, String reasonPhrase) {
                        int error;
               ResetPasswordActivity.this.dismissDialog();
                        switch (statusCode) {
                            case 200:
                       error = R.string.password_reset_confirm;
                                break;
                            case 404:
                       error = R.string.password_reset_not_found;
                                break;
                            default:
                       error = R.string.password_reset_error;
                                break;
                        }
               PromptDialogSupportFragment fragment = PromptDialogSupportFragment.newInstance(1);
               fragment.setMessage(error);
               fragment.setPositiveButton(R.string.ok);
               fragment.show(ResetPasswordActivity.this.getSupportFragmentManager());
                    }
                }
            }
