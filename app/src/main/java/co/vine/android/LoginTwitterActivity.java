            package co.vine.android;

            import android.app.ProgressDialog;
            import android.content.Context;
            import android.content.DialogInterface;
            import android.content.Intent;
            import android.content.res.Resources;
            import android.os.Bundle;
            import android.text.Spannable;
            import android.text.Spanned;
            import android.text.TextUtils;
            import android.text.method.LinkMovementMethod;
            import android.text.style.StyleSpan;
            import android.view.KeyEvent;
            import android.view.Menu;
            import android.view.MenuItem;
            import android.view.View;
            import android.widget.EditText;
            import android.widget.TextView;
            import co.vine.android.api.VineKnownErrors;
            import co.vine.android.api.VineLogin;
            import co.vine.android.api.VineUser;
            import co.vine.android.client.AppSessionListener;
            import co.vine.android.util.CommonUtil;
            import co.vine.android.util.CrashUtil;
            import co.vine.android.util.DialogUtils;
            import co.vine.android.util.Util;
            import co.vine.android.util.analytics.FlurryUtils;
            import co.vine.android.widget.SpanClickListener;
            import co.vine.android.widget.StyledClickableSpan;
            import co.vine.android.widget.Typefaces;
            import co.vine.android.widgets.PromptDialogSupportFragment;
            
            
    public class LoginTwitterActivity extends BaseControllerActionBarActivity implements SpanClickListener {
                private static final int DIALOG_ACTIVATE_ACCOUNT = 1;
                public static final String EXTRA_FINISH = "finish";
                public static final String EXTRA_SCREEN_NAME = "screen_name";
                public static final String EXTRA_SECRET = "secret";
                public static final String EXTRA_TOKEN = "token";
                public static final String EXTRA_USER_ID = "user_id";
                private static final int SPAN_VINE_PP = 1;
                private static final int SPAN_VINE_TOS = 2;
                private static final String STATE_VINE_LOGIN = "vine_login";
                private final PromptDialogSupportFragment.OnDialogDoneListener mActivateAccountDialogDoneListener = new C02442();
                private boolean mFinish;
                private EditText mPassword;
                private EditText mUsername;
                private VineLogin mVineLogin;

        static  boolean access$000(LoginTwitterActivity x0) {
            return x0.validateAndDisplayToastIfCredentialsInvalid();
                }

        static  void access$100(LoginTwitterActivity x0, boolean x1) {
            x0.login(x1);
                }

        static  boolean access$300(LoginTwitterActivity x0) {
            return x0.mFinish;
                }

        static  VineLogin access$402(LoginTwitterActivity x0, VineLogin x1) {
            x0.mVineLogin = x1;
                    return x1;
                }

        static  PromptDialogSupportFragment.OnDialogDoneListener access$500(LoginTwitterActivity x0) {
            return x0.mActivateAccountDialogDoneListener;
                }

                @Override 
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState, R.layout.xauth, false);
            setupActionBar((Boolean) true, (Boolean) true, R.string.login_xauth_title, (Boolean) true, (Boolean) true);
            this.mUsername = (EditText) findViewById(R.id.login_username);
            this.mUsername.setTypeface(Typefaces.get(this).getContentTypeface(0, 2));
            this.mPassword = (EditText) findViewById(R.id.login_password);
            this.mPassword.setTypeface(Typefaces.get(this).getContentTypeface(0, 2));
            this.mPassword.setOnEditorActionListener(new C02431());
            this.mFinish = getIntent().getBooleanExtra("finish", false);
            this.mAppSessionListener = new LoginTwitterListener(this, null);
            TextView mTos = (TextView) findViewById(R.id.tos_line_2);
            StyleSpan[] boldStyle = {new StyleSpan(1), new StyleSpan(1)};
            Spanned spanned = Util.getSpannedText(boldStyle, getString(R.string.login_xauth_tos_line_2), CommonUtil.MARKER_QUOTES);
            mTos.setText(spanned);
            mTos.setMovementMethod(LinkMovementMethod.getInstance());
            Spannable clickSpannable = (Spannable) mTos.getText();
            StyleSpan[] spans = (StyleSpan[]) spanned.getSpans(0, spanned.length(), StyleSpan.class);
            Resources res = getResources();
           int start = spanned.getSpanStart(spans[0]);
           int end = spanned.getSpanEnd(spans[0]);
           StyledClickableSpan clickableSpan = new StyledClickableSpan(2, null, this);
           clickableSpan.setColor(res.getColor(R.color.text_fineprint));
           Util.safeSetSpan(clickSpannable, clickableSpan, start, end, 33);
           if (savedInstanceState != null && savedInstanceState.containsKey(STATE_VINE_LOGIN)) {
               this.mVineLogin = (VineLogin) savedInstanceState.getParcelable(STATE_VINE_LOGIN);
                    }
           int start2 = spanned.getSpanStart(spans[1]);
           int end2 = spanned.getSpanEnd(spans[1]);
           StyledClickableSpan clickableSpan2 = new StyledClickableSpan(1, null, this);
           clickableSpan2.setColor(res.getColor(R.color.text_fineprint));
           Util.safeSetSpan(clickSpannable, clickableSpan2, start2, end2, 33);
           FlurryUtils.trackNuxStarted("twitter-xauth");
                }

                
        class C02431 implements TextView.OnEditorActionListener {
            C02431() {
                    }

                    @Override 
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event != null && event.getKeyCode() == 66) || (actionId == 6 && LoginTwitterActivity.access$000(LoginTwitterActivity.this))) {
                    LoginTwitterActivity.access$100(LoginTwitterActivity.this, false);
                        }
                        return false;
                    }
                }

                @Override 
       public void onSaveInstanceState(Bundle outState) {
           super.onSaveInstanceState(outState);
           if (this.mVineLogin != null) {
               outState.putParcelable(STATE_VINE_LOGIN, this.mVineLogin);
                    }
                }

                @Override 
       public boolean onCreateOptionsMenu(Menu menu) {
           getMenuInflater().inflate(R.menu.login, menu);
           return true;
                }

                @Override 
       public boolean onOptionsItemSelected(MenuItem item) {
           if (item.getItemId() != R.id.sign_in || !validateAndDisplayToastIfCredentialsInvalid()) {
                        return super.onOptionsItemSelected(item);
                    }
           login(false);
           return true;
                }

                @Override 
       public void onSpanClicked(View view, int type, Object tag) {
           Intent intent = new Intent(this, (Class<?>) WebViewActivity.class);
                    switch (type) {
                        case 1:
                   intent.putExtra("type", 1);
                            break;
                        case 2:
                   intent.putExtra("type", 2);
                            break;
                        default:
                   return;
                    }
           startActivity(intent);
                }

       private boolean validateAndDisplayToastIfCredentialsInvalid() {
           if (this.mUsername.getText().toString().isEmpty()) {
               Util.showCenteredToast(this, R.string.login_twitter_empty_username_email);
                        return false;
                    }
           if (this.mPassword.getText().toString().isEmpty()) {
               Util.showCenteredToast(this, R.string.login_empty_password);
                        return false;
                    }
           return true;
                }

       private void login(boolean reactivate) {
           ProgressDialog dialog = new ProgressDialog(this, R.style.ProgressDialogTheme);
           dialog.setMessage(getString(R.string.sign_up_authorizing));
           dialog.setProgress(0);
           DialogUtils.showDialogUnsafe(dialog);
           this.mProgressDialog = dialog;
           if (!reactivate) {
               this.mAppController.loginWithxAuth(this.mUsername.getText().toString(), this.mPassword.getText().toString());
                    } else if (this.mVineLogin != null) {
               VineLogin login = this.mVineLogin;
               this.mAppController.loginCheckTwitter(login.twitterUsername, login.twitterToken, login.twitterSecret, login.twitterUserId, true);
                    } else {
               Util.showCenteredToast(this, R.string.error_xauth);
                    }
                }

       private class LoginTwitterListener extends AppSessionListener {
           private LoginTwitterListener() {
                    }

            LoginTwitterListener(LoginTwitterActivity x0, C02431 x1) {
               this();
                    }

                    @Override 
           public void onTwitterxAuthComplete(String reqId, int statusCode, String reasonPhrase, VineLogin login) {
               if (LoginTwitterActivity.access$300(LoginTwitterActivity.this)) {
                   if (statusCode == 200) {
                       if (!LoginTwitterActivity.this.mAppController.isLoggedIn()) {
                           LoginTwitterActivity.this.mAppController.loginCheckTwitter(login.twitterUsername, login.twitterToken, login.twitterSecret, login.twitterUserId, false);
                           return;
                                }
                       Intent intent = new Intent();
                       intent.putExtra("token", login.twitterToken);
                       intent.putExtra("secret", login.twitterSecret);
                       intent.putExtra("user_id", login.twitterUserId);
                       intent.putExtra("screen_name", login.twitterUsername);
                       LoginTwitterActivity.this.setResult(-1, intent);
                       LoginTwitterActivity.this.finish();
                       return;
                            }
                   LoginTwitterActivity.this.dismissDialog();
                   Util.showCenteredToast(LoginTwitterActivity.this, R.string.find_friends_twitter_xauth_error);
                   return;
                        }
               if (statusCode == 200 && login != null) {
                   LoginTwitterActivity.access$402(LoginTwitterActivity.this, login);
                   LoginTwitterActivity.this.mAppController.loginCheckTwitter(login.twitterUsername, login.twitterToken, login.twitterSecret, login.twitterUserId, false);
                   return;
                        }
               LoginTwitterActivity.this.dismissDialog();
               if (!TextUtils.isEmpty(reasonPhrase)) {
                   Util.showCenteredToast(LoginTwitterActivity.this, reasonPhrase);
                        } else {
                   Util.showCenteredToast(LoginTwitterActivity.this, R.string.error_xauth);
                        }
                    }

                    @Override 
           public void onCheckTwitterComplete(String reqId, int statusCode, String reasonPhrase, int errorCode, boolean accountExists, VineUser user, VineLogin login) {
               if (accountExists && statusCode == 200) {
                            try {
                       LoginTwitterActivity.this.mAppController.loginComplete(LoginTwitterActivity.this.mAppController.getActiveSession(), statusCode, user.username, null, login, user.avatarUrl);
                       if (LoginTwitterActivity.access$300(LoginTwitterActivity.this)) {
                           LoginTwitterActivity.this.finish();
                                } else {
                           StartActivity.toStart(LoginTwitterActivity.this);
                                }
                       return;
                            } catch (VineLoggingException e) {
                       Util.showCenteredToast(LoginTwitterActivity.this, R.string.failed_to_add_account_on_device);
                       CrashUtil.logException(e);
                       LoginTwitterActivity.this.finish();
                       return;
                            }
                        }
               LoginTwitterActivity.this.dismissDialog();
               if (errorCode == VineKnownErrors.BAD_CREDENTIALS.code) {
                   Bundle extras = new Bundle();
                   extras.putParcelable("user", login);
                   LoginTwitterActivity.this.startActivity(SignUpPagerActivity.getIntent(LoginTwitterActivity.this, extras));
                        } else {
                   if (errorCode == VineKnownErrors.ACCOUNT_DEACTIVATED.code) {
                       PromptDialogSupportFragment dialogFragment = PromptDialogSupportFragment.newInstance(1);
                       dialogFragment.setMessage(R.string.settings_activate_account_dialog);
                       dialogFragment.setTitle(R.string.settings_activate_account_title);
                       dialogFragment.setNegativeButton(R.string.cancel);
                       dialogFragment.setPositiveButton(R.string.settings_activate_account_confirm);
                       dialogFragment.setListener(LoginTwitterActivity.access$500(LoginTwitterActivity.this));
                       dialogFragment.show(LoginTwitterActivity.this.getSupportFragmentManager());
                       return;
                            }
                   Util.showCenteredToast(LoginTwitterActivity.this, reasonPhrase);
                        }
                    }
                }

                
       class C02442 implements PromptDialogSupportFragment.OnDialogDoneListener {
           C02442() {
                    }

                    @Override 
           public void onDialogDone(DialogInterface dialog, int id, int which) {
               if (which == -1) {
                   LoginTwitterActivity.access$100(LoginTwitterActivity.this, true);
                        }
                    }
                }

       public static Intent getIntent(Context context) {
           return new Intent(context, (Class<?>) LoginTwitterActivity.class);
                }

       public static Intent getIntentWithFinish(Context context) {
           Intent intent = new Intent(context, (Class<?>) LoginTwitterActivity.class);
           intent.putExtra("finish", true);
           return intent;
                }
            }
