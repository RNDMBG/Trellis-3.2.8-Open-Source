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
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 39 */    public class LoginTwitterActivity extends BaseControllerActionBarActivity implements SpanClickListener {
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

/* 40 */        static /* synthetic */ boolean access$000(LoginTwitterActivity x0) {
/* 41 */            return x0.validateAndDisplayToastIfCredentialsInvalid();
                }

/* 40 */        static /* synthetic */ void access$100(LoginTwitterActivity x0, boolean x1) {
/* 41 */            x0.login(x1);
                }

/* 40 */        static /* synthetic */ boolean access$300(LoginTwitterActivity x0) {
/* 41 */            return x0.mFinish;
                }

/* 40 */        static /* synthetic */ VineLogin access$402(LoginTwitterActivity x0, VineLogin x1) {
/* 41 */            x0.mVineLogin = x1;
                    return x1;
                }

/* 40 */        static /* synthetic */ PromptDialogSupportFragment.OnDialogDoneListener access$500(LoginTwitterActivity x0) {
/* 41 */            return x0.mActivateAccountDialogDoneListener;
                }

                @Override // co.vine.android.BaseActionBarActivity, android.support.v7.app.ActionBarActivity, android.support.v4.app.FragmentActivity, android.app.Activity
/* 67 */        public void onCreate(Bundle savedInstanceState) {
/* 68 */            super.onCreate(savedInstanceState, C0257R.layout.xauth, false);
/* 70 */            setupActionBar((Boolean) true, (Boolean) true, C0257R.string.login_xauth_title, (Boolean) true, (Boolean) true);
/* 72 */            this.mUsername = (EditText) findViewById(C0257R.id.login_username);
/* 73 */            this.mUsername.setTypeface(Typefaces.get(this).getContentTypeface(0, 2));
/* 74 */            this.mPassword = (EditText) findViewById(C0257R.id.login_password);
/* 75 */            this.mPassword.setTypeface(Typefaces.get(this).getContentTypeface(0, 2));
/* 76 */            this.mPassword.setOnEditorActionListener(new C02431());
/* 87 */            this.mFinish = getIntent().getBooleanExtra("finish", false);
/* 89 */            this.mAppSessionListener = new LoginTwitterListener(this, null);
/* 91 */            TextView mTos = (TextView) findViewById(C0257R.id.tos_line_2);
/* 92 */            StyleSpan[] boldStyle = {new StyleSpan(1), new StyleSpan(1)};
/* 93 */            Spanned spanned = Util.getSpannedText(boldStyle, getString(C0257R.string.login_xauth_tos_line_2), CommonUtil.MARKER_QUOTES);
/* 94 */            mTos.setText(spanned);
/* 95 */            mTos.setMovementMethod(LinkMovementMethod.getInstance());
/* 97 */            Spannable clickSpannable = (Spannable) mTos.getText();
/* 98 */            StyleSpan[] spans = (StyleSpan[]) spanned.getSpans(0, spanned.length(), StyleSpan.class);
/* 99 */            Resources res = getResources();
/* 102 */           int start = spanned.getSpanStart(spans[0]);
/* 103 */           int end = spanned.getSpanEnd(spans[0]);
/* 104 */           StyledClickableSpan clickableSpan = new StyledClickableSpan(2, null, this);
/* 105 */           clickableSpan.setColor(res.getColor(C0257R.color.text_fineprint));
/* 106 */           Util.safeSetSpan(clickSpannable, clickableSpan, start, end, 33);
/* 108 */           if (savedInstanceState != null && savedInstanceState.containsKey(STATE_VINE_LOGIN)) {
/* 109 */               this.mVineLogin = (VineLogin) savedInstanceState.getParcelable(STATE_VINE_LOGIN);
                    }
/* 113 */           int start2 = spanned.getSpanStart(spans[1]);
/* 114 */           int end2 = spanned.getSpanEnd(spans[1]);
/* 115 */           StyledClickableSpan clickableSpan2 = new StyledClickableSpan(1, null, this);
/* 116 */           clickableSpan2.setColor(res.getColor(C0257R.color.text_fineprint));
/* 117 */           Util.safeSetSpan(clickSpannable, clickableSpan2, start2, end2, 33);
/* 118 */           FlurryUtils.trackNuxStarted("twitter-xauth");
                }

                /* JADX INFO: renamed from: co.vine.android.LoginTwitterActivity$1 */
/* 74 */        class C02431 implements TextView.OnEditorActionListener {
/* 75 */            C02431() {
                    }

                    @Override // android.widget.TextView.OnEditorActionListener
/* 78 */            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
/* 79 */                if ((event != null && event.getKeyCode() == 66) || (actionId == 6 && LoginTwitterActivity.access$000(LoginTwitterActivity.this))) {
/* 81 */                    LoginTwitterActivity.access$100(LoginTwitterActivity.this, false);
                        }
                        return false;
                    }
                }

                @Override // co.vine.android.BaseActionBarActivity, android.support.v4.app.FragmentActivity, android.app.Activity
/* 122 */       public void onSaveInstanceState(Bundle outState) {
/* 123 */           super.onSaveInstanceState(outState);
/* 124 */           if (this.mVineLogin != null) {
/* 125 */               outState.putParcelable(STATE_VINE_LOGIN, this.mVineLogin);
                    }
                }

                @Override // co.vine.android.BaseActionBarActivity, android.app.Activity
/* 130 */       public boolean onCreateOptionsMenu(Menu menu) {
/* 131 */           getMenuInflater().inflate(C0257R.menu.login, menu);
/* 132 */           return true;
                }

                @Override // co.vine.android.BaseActionBarActivity, android.app.Activity
/* 136 */       public boolean onOptionsItemSelected(MenuItem item) {
/* 137 */           if (item.getItemId() != 2131427943 || !validateAndDisplayToastIfCredentialsInvalid()) {
                        return super.onOptionsItemSelected(item);
                    }
/* 138 */           login(false);
/* 139 */           return true;
                }

                @Override // co.vine.android.widget.SpanClickListener
/* 146 */       public void onSpanClicked(View view, int type, Object tag) {
/* 147 */           Intent intent = new Intent(this, (Class<?>) WebViewActivity.class);
                    switch (type) {
                        case 1:
/* 150 */                   intent.putExtra("type", 1);
                            break;
                        case 2:
/* 153 */                   intent.putExtra("type", 2);
                            break;
                        default:
/* 159 */                   return;
                    }
/* 158 */           startActivity(intent);
                }

/* 161 */       private boolean validateAndDisplayToastIfCredentialsInvalid() {
/* 162 */           if (this.mUsername.getText().toString().isEmpty()) {
/* 163 */               Util.showCenteredToast(this, C0257R.string.login_twitter_empty_username_email);
                        return false;
                    }
/* 165 */           if (this.mPassword.getText().toString().isEmpty()) {
/* 166 */               Util.showCenteredToast(this, C0257R.string.login_empty_password);
                        return false;
                    }
/* 169 */           return true;
                }

/* 178 */       private void login(boolean reactivate) {
/* 179 */           ProgressDialog dialog = new ProgressDialog(this, C0257R.style.ProgressDialogTheme);
/* 180 */           dialog.setMessage(getString(C0257R.string.sign_up_authorizing));
/* 181 */           dialog.setProgress(0);
/* 183 */           DialogUtils.showDialogUnsafe(dialog);
/* 184 */           this.mProgressDialog = dialog;
/* 185 */           if (!reactivate) {
/* 186 */               this.mAppController.loginWithxAuth(this.mUsername.getText().toString(), this.mPassword.getText().toString());
                    } else if (this.mVineLogin != null) {
/* 188 */               VineLogin login = this.mVineLogin;
/* 189 */               this.mAppController.loginCheckTwitter(login.twitterUsername, login.twitterToken, login.twitterSecret, login.twitterUserId, true);
                    } else {
/* 191 */               Util.showCenteredToast(this, C0257R.string.error_xauth);
                    }
                }

/* 193 */       private class LoginTwitterListener extends AppSessionListener {
/* 194 */           private LoginTwitterListener() {
                    }

/* 194 */           /* synthetic */ LoginTwitterListener(LoginTwitterActivity x0, C02431 x1) {
/* 195 */               this();
                    }

                    @Override // co.vine.android.client.AppSessionListener
/* 198 */           public void onTwitterxAuthComplete(String reqId, int statusCode, String reasonPhrase, VineLogin login) {
/* 199 */               if (LoginTwitterActivity.access$300(LoginTwitterActivity.this)) {
/* 200 */                   if (statusCode == 200) {
/* 204 */                       if (!LoginTwitterActivity.this.mAppController.isLoggedIn()) {
/* 205 */                           LoginTwitterActivity.this.mAppController.loginCheckTwitter(login.twitterUsername, login.twitterToken, login.twitterSecret, login.twitterUserId, false);
/* 234 */                           return;
                                }
/* 208 */                       Intent intent = new Intent();
/* 209 */                       intent.putExtra("token", login.twitterToken);
/* 210 */                       intent.putExtra("secret", login.twitterSecret);
/* 211 */                       intent.putExtra("user_id", login.twitterUserId);
/* 212 */                       intent.putExtra("screen_name", login.twitterUsername);
/* 213 */                       LoginTwitterActivity.this.setResult(-1, intent);
/* 214 */                       LoginTwitterActivity.this.finish();
/* 234 */                       return;
                            }
/* 217 */                   LoginTwitterActivity.this.dismissDialog();
/* 218 */                   Util.showCenteredToast(LoginTwitterActivity.this, C0257R.string.find_friends_twitter_xauth_error);
/* 234 */                   return;
                        }
/* 221 */               if (statusCode == 200 && login != null) {
/* 222 */                   LoginTwitterActivity.access$402(LoginTwitterActivity.this, login);
/* 223 */                   LoginTwitterActivity.this.mAppController.loginCheckTwitter(login.twitterUsername, login.twitterToken, login.twitterSecret, login.twitterUserId, false);
/* 234 */                   return;
                        }
/* 226 */               LoginTwitterActivity.this.dismissDialog();
/* 227 */               if (!TextUtils.isEmpty(reasonPhrase)) {
/* 228 */                   Util.showCenteredToast(LoginTwitterActivity.this, reasonPhrase);
                        } else {
/* 230 */                   Util.showCenteredToast(LoginTwitterActivity.this, C0257R.string.error_xauth);
                        }
                    }

                    @Override // co.vine.android.client.AppSessionListener
/* 239 */           public void onCheckTwitterComplete(String reqId, int statusCode, String reasonPhrase, int errorCode, boolean accountExists, VineUser user, VineLogin login) {
/* 240 */               if (accountExists && statusCode == 200) {
                            try {
/* 243 */                       LoginTwitterActivity.this.mAppController.loginComplete(LoginTwitterActivity.this.mAppController.getActiveSession(), statusCode, user.username, null, login, user.avatarUrl);
/* 245 */                       if (LoginTwitterActivity.access$300(LoginTwitterActivity.this)) {
/* 246 */                           LoginTwitterActivity.this.finish();
                                } else {
/* 248 */                           StartActivity.toStart(LoginTwitterActivity.this);
                                }
/* 274 */                       return;
                            } catch (VineLoggingException e) {
/* 251 */                       Util.showCenteredToast(LoginTwitterActivity.this, C0257R.string.failed_to_add_account_on_device);
/* 252 */                       CrashUtil.logException(e);
/* 253 */                       LoginTwitterActivity.this.finish();
/* 274 */                       return;
                            }
                        }
/* 256 */               LoginTwitterActivity.this.dismissDialog();
/* 257 */               if (errorCode == VineKnownErrors.BAD_CREDENTIALS.code) {
/* 259 */                   Bundle extras = new Bundle();
/* 260 */                   extras.putParcelable("user", login);
/* 261 */                   LoginTwitterActivity.this.startActivity(SignUpPagerActivity.getIntent(LoginTwitterActivity.this, extras));
                        } else {
/* 262 */                   if (errorCode == VineKnownErrors.ACCOUNT_DEACTIVATED.code) {
/* 263 */                       PromptDialogSupportFragment dialogFragment = PromptDialogSupportFragment.newInstance(1);
/* 264 */                       dialogFragment.setMessage(C0257R.string.settings_activate_account_dialog);
/* 265 */                       dialogFragment.setTitle(C0257R.string.settings_activate_account_title);
/* 266 */                       dialogFragment.setNegativeButton(C0257R.string.cancel);
/* 267 */                       dialogFragment.setPositiveButton(C0257R.string.settings_activate_account_confirm);
/* 268 */                       dialogFragment.setListener(LoginTwitterActivity.access$500(LoginTwitterActivity.this));
/* 269 */                       dialogFragment.show(LoginTwitterActivity.this.getSupportFragmentManager());
/* 274 */                       return;
                            }
/* 271 */                   Util.showCenteredToast(LoginTwitterActivity.this, reasonPhrase);
                        }
                    }
                }

                /* JADX INFO: renamed from: co.vine.android.LoginTwitterActivity$2 */
/* 276 */       class C02442 implements PromptDialogSupportFragment.OnDialogDoneListener {
/* 277 */           C02442() {
                    }

                    @Override // co.vine.android.widgets.PromptDialogFragment.OnDialogDoneListener
/* 280 */           public void onDialogDone(DialogInterface dialog, int id, int which) {
/* 281 */               if (which == -1) {
/* 282 */                   LoginTwitterActivity.access$100(LoginTwitterActivity.this, true);
                        }
                    }
                }

/* 287 */       public static Intent getIntent(Context context) {
/* 288 */           return new Intent(context, (Class<?>) LoginTwitterActivity.class);
                }

/* 291 */       public static Intent getIntentWithFinish(Context context) {
/* 292 */           Intent intent = new Intent(context, (Class<?>) LoginTwitterActivity.class);
/* 293 */           intent.putExtra("finish", true);
/* 294 */           return intent;
                }
            }
