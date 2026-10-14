            package co.vine.android;

            import android.app.ProgressDialog;
            import android.content.Context;
            import android.content.Intent;
            import android.net.Uri;
            import android.os.Bundle;
            import android.support.v4.app.Fragment;
            import android.support.v4.app.FragmentManager;
            import android.support.v4.app.FragmentStatePagerAdapter;
            import android.text.TextUtils;
            import android.view.MenuItem;
            import co.vine.android.api.TwitterUser;
            import co.vine.android.api.VineError;
            import co.vine.android.api.VineKnownErrors;
            import co.vine.android.api.VineLogin;
            import co.vine.android.api.VineUser;
            import co.vine.android.client.AppSessionListener;
            import co.vine.android.client.Session;
            import co.vine.android.util.CrashUtil;
            import co.vine.android.util.DialogUtils;
            import co.vine.android.util.Util;
            import co.vine.android.util.analytics.FlurryUtils;
            import co.vine.android.widget.ConfigurableViewPager;
            import java.lang.ref.WeakReference;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 30 */    public class SignUpPagerActivity extends BaseControllerActionBarActivity {
                public static final String EXTRA_FINISH = "finish";
                public static final String EXTRA_LOGIN = "user";
                public static final int FLOW_STEP_DETAILS = 1;
                public static final int FLOW_STEP_NAME_AVATAR = 0;
                private static final int NUM_PAGES = 2;
                public static final int REQUEST_CODE_CONFIRM_PHONE = 1653;
                private static final String STATE_LOGIN = "s_login";
                private static final String STATE_NAME = "s_name";
                private static final String STATE_PASSWORD = "s_password";
                private static final String STATE_PHONE = "s_phone";
                private static final String STATE_PROFILE = "s_profile";
                private WeakReference<Fragment> mAvatarFrag;
                private WeakReference<Fragment> mDetailsFrag;
                private boolean mFinish;
                private boolean mFollowVineOnTwitter;
                private String mLogin;
                private String mName;
                private ConfigurableViewPager mPager;
                private String mPassword;
                private String mPhone;
                private Uri mProfile;
                private TwitterUser mTwitterUser;
                private VineLogin mVineLogin;

/* 31 */        static /* synthetic */ VineLogin access$000(SignUpPagerActivity x0) {
/* 32 */            return x0.mVineLogin;
                }

/* 31 */        static /* synthetic */ WeakReference access$102(SignUpPagerActivity x0, WeakReference x1) {
/* 32 */            x0.mAvatarFrag = x1;
                    return x1;
                }

/* 31 */        static /* synthetic */ WeakReference access$202(SignUpPagerActivity x0, WeakReference x1) {
/* 32 */            x0.mDetailsFrag = x1;
                    return x1;
                }

/* 31 */        static /* synthetic */ boolean access$300(SignUpPagerActivity x0) {
/* 32 */            return x0.mFinish;
                }

/* 31 */        static /* synthetic */ String access$400(SignUpPagerActivity x0) {
/* 32 */            return x0.mPhone;
                }

                @Override // co.vine.android.BaseActionBarActivity, android.support.v7.app.ActionBarActivity, android.support.v4.app.FragmentActivity, android.app.Activity
/* 65 */        public void onCreate(Bundle savedInstanceState) {
/* 66 */            super.onCreate(savedInstanceState, C0257R.layout.sign_up_flow, false);
/* 68 */            Intent intent = getIntent();
/* 69 */            if (intent != null) {
/* 70 */                this.mFinish = intent.getBooleanExtra("finish", false);
                    }
/* 73 */            this.mPager = (ConfigurableViewPager) findViewById(C0257R.id.pager);
/* 74 */            this.mPager.setSwipingEnabled(false);
/* 75 */            this.mPager.setAdapter(new SignUpPagerAdapter(getSupportFragmentManager()));
/* 77 */            this.mAppSessionListener = new SignUpListener();
/* 78 */            this.mVineLogin = (VineLogin) getIntent().getParcelableExtra("user");
/* 80 */            if (savedInstanceState != null) {
/* 81 */                this.mLogin = savedInstanceState.getString(STATE_LOGIN);
/* 82 */                this.mName = savedInstanceState.getString(STATE_NAME);
/* 83 */                this.mPassword = savedInstanceState.getString(STATE_PASSWORD);
/* 84 */                this.mPhone = savedInstanceState.getString(STATE_PHONE);
/* 85 */                this.mProfile = (Uri) savedInstanceState.getParcelable(STATE_PROFILE);
                    }
/* 88 */            setupActionBar((Boolean) true, (Boolean) true, (String) null, (Boolean) true, (Boolean) true);
/* 89 */            FlurryUtils.trackNuxScreenDisplayed("sign_up_username");
                }

                @Override // co.vine.android.BaseActionBarActivity, android.support.v4.app.FragmentActivity, android.app.Activity
/* 93 */        protected void onSaveInstanceState(Bundle outState) {
/* 94 */            super.onSaveInstanceState(outState);
/* 95 */            outState.putString(STATE_LOGIN, this.mLogin);
/* 96 */            outState.putString(STATE_PASSWORD, this.mPassword);
/* 97 */            outState.putString(STATE_NAME, this.mName);
/* 98 */            outState.putString(STATE_PHONE, this.mPhone);
/* 99 */            outState.putParcelable(STATE_PROFILE, this.mProfile);
                }

                @Override // co.vine.android.BaseActionBarActivity, android.support.v7.app.ActionBarActivity, android.support.v4.app.FragmentActivity, android.app.Activity
/* 103 */       public void onBackPressed() {
/* 104 */           if (this.mPager.getCurrentItem() == 0) {
/* 105 */               super.onBackPressed();
                    } else {
/* 107 */               this.mPager.setCurrentItem(this.mPager.getCurrentItem() - 1);
                    }
                }

                @Override // co.vine.android.BaseActionBarActivity, android.app.Activity
/* 112 */       public boolean onOptionsItemSelected(MenuItem item) {
                    switch (item.getItemId()) {
                        case android.R.id.home:
/* 115 */                   onBackPressed();
/* 116 */                   return true;
                        default:
/* 118 */                   return super.onOptionsItemSelected(item);
                    }
                }

/* 122 */       protected SignUpDetailsFragment getDetailsFragment() {
/* 123 */           return (SignUpDetailsFragment) this.mDetailsFrag.get();
                }

/* 126 */       protected void toNextStep() {
                    switch (this.mPager.getCurrentItem()) {
                        case 0:
/* 129 */                   if (this.mTwitterUser != null) {
/* 130 */                       ProgressDialog dialog = new ProgressDialog(this, C0257R.style.ProgressDialogTheme);
/* 131 */                       dialog.setMessage(getString(C0257R.string.login_validating));
/* 132 */                       dialog.setProgress(0);
/* 133 */                       DialogUtils.showDialogUnsafe(dialog);
/* 134 */                       this.mProgressDialog = dialog;
/* 135 */                       this.mAppController.signUp(null, null, this.mName, this.mPhone, this.mProfile, this.mTwitterUser, this.mVineLogin);
/* 137 */                       if (this.mFollowVineOnTwitter) {
/* 138 */                           this.mAppController.followVineOnTwitter();
                                }
/* 140 */                       FlurryUtils.trackNuxScreenDisplayed("sign_up_twitter");
                            } else {
/* 142 */                       this.mPager.setCurrentItem(1);
/* 143 */                       FlurryUtils.trackNuxScreenDisplayed("sign_up_email");
/* 144 */                       if (this.mDetailsFrag != null) {
/* 145 */                           Fragment details = this.mDetailsFrag.get();
/* 146 */                           if (details instanceof SignUpDetailsFragment) {
/* 147 */                               ((SignUpDetailsFragment) details).onMoveTo();
                                    }
                                }
                            }
                            break;
                        case 1:
/* 153 */                   ProgressDialog dialog2 = new ProgressDialog(this, C0257R.style.ProgressDialogTheme);
/* 154 */                   this.mProgressDialog = dialog2;
/* 155 */                   dialog2.setProgressStyle(0);
/* 156 */                   dialog2.setMessage(getString(C0257R.string.signing_up));
/* 157 */                   DialogUtils.showDialogUnsafe(dialog2);
/* 158 */                   this.mAppController.signUp(this.mLogin, this.mPassword, this.mName, this.mPhone, this.mProfile, null, null);
                            break;
                    }
                }

/* 163 */       protected void setBarTitle(int resId) {
/* 164 */           setupActionBar((Boolean) null, (Boolean) true, resId, (Boolean) null, (Boolean) true);
                }

/* 167 */       public void setLogin(String login) {
/* 168 */           this.mLogin = login;
                }

/* 171 */       public void setPassword(String password) {
/* 172 */           this.mPassword = password;
                }

/* 175 */       public void setPhone(String phone) {
/* 176 */           this.mPhone = phone;
                }

/* 179 */       public void setName(String name) {
/* 180 */           this.mName = name;
                }

/* 183 */       public void setProfile(Uri uri) {
/* 184 */           this.mProfile = uri;
                }

/* 187 */       public void setTwitterUser(TwitterUser tUser) {
/* 188 */           this.mTwitterUser = tUser;
                }

/* 191 */       public void setFollowVineOnTwitter(boolean followVineOnTwitter) {
/* 192 */           this.mFollowVineOnTwitter = followVineOnTwitter;
                }

                @Override // co.vine.android.BaseActionBarActivity, android.support.v4.app.FragmentActivity, android.app.Activity
/* 196 */       protected void onActivityResult(int requestCode, int resultCode, Intent data) {
                    Fragment frag;
                    Fragment frag2;
                    switch (requestCode) {
                        case REQUEST_CODE_CONFIRM_PHONE /* 1653 */:
/* 199 */                   startActivity(new Intent(this, (Class<?>) ChannelsListNUXActivity.class));
                            break;
                        default:
                            switch (this.mPager.getCurrentItem()) {
                                case 0:
/* 204 */                           if (this.mAvatarFrag != null && (frag2 = this.mAvatarFrag.get()) != null) {
/* 207 */                               frag2.onActivityResult(requestCode, resultCode, data);
                                        break;
                                    }
                                    break;
                                case 1:
/* 213 */                           if (this.mDetailsFrag != null && (frag = this.mDetailsFrag.get()) != null) {
/* 216 */                               frag.onActivityResult(requestCode, resultCode, data);
                                        break;
                                    }
                                    break;
                            }
                            break;
                    }
                }

/* 225 */       private class SignUpPagerAdapter extends FragmentStatePagerAdapter {
/* 226 */           public SignUpPagerAdapter(FragmentManager fm) {
/* 228 */               super(fm);
                    }

                    @Override // android.support.v4.app.FragmentStatePagerAdapter
/* 232 */           public Fragment getItem(int i) {
                        switch (i) {
                            case 0:
/* 235 */                       SignUpNameAvatarFragment avatarFragment = new SignUpNameAvatarFragment();
/* 236 */                       if (SignUpPagerActivity.access$000(SignUpPagerActivity.this) != null) {
/* 237 */                           avatarFragment.setArguments(SignUpPagerActivity.this.getIntent().getExtras());
                                }
/* 239 */                       SignUpPagerActivity.access$102(SignUpPagerActivity.this, new WeakReference(avatarFragment));
/* 244 */                       return avatarFragment;
                            case 1:
/* 242 */                       SignUpDetailsFragment detailsFragment = new SignUpDetailsFragment();
/* 243 */                       SignUpPagerActivity.access$202(SignUpPagerActivity.this, new WeakReference(detailsFragment));
                                return detailsFragment;
                            default:
/* 246 */                       throw new IllegalArgumentException("no pager found for " + i);
                        }
                    }

                    @Override // android.support.v4.view.PagerAdapter
/* 251 */           public int getCount() {
/* 252 */               return 2;
                    }
                }

/* 254 */       class SignUpListener extends AppSessionListener {
/* 255 */           SignUpListener() {
                    }

                    @Override // co.vine.android.client.AppSessionListener
/* 260 */           public void onSignUpComplete(String reqId, int statusCode, String reasonPhrase, VineLogin vineLogin, String accountName, String password, String avatarUrl) {
/* 261 */               if (statusCode == 200 && vineLogin != null && vineLogin.userId > 0) {
                            switch (vineLogin.loginType) {
                                case 1:
/* 264 */                           FlurryUtils.trackNuxAccountCreated("email");
                                    try {
/* 266 */                               SignUpPagerActivity.this.mAppController.loginComplete(SignUpPagerActivity.this.mAppController.getActiveSession(), statusCode, accountName, password, vineLogin, avatarUrl);
/* 275 */                               SignUpPagerActivity.this.dismissDialog();
/* 276 */                               if (SignUpPagerActivity.access$300(SignUpPagerActivity.this)) {
/* 277 */                                   SignUpPagerActivity.this.setResult(-1);
/* 278 */                                   SignUpPagerActivity.this.finish();
                                        } else if (!TextUtils.isEmpty(SignUpPagerActivity.access$400(SignUpPagerActivity.this))) {
/* 281 */                                   ConfirmationFlowActivity.requestPhoneVerification(SignUpPagerActivity.this.mAppController, SignUpPagerActivity.access$400(SignUpPagerActivity.this));
/* 283 */                                   SignUpPagerActivity.this.startActivityForResult(ConfirmationFlowActivity.getIntent(SignUpPagerActivity.this, SignUpPagerActivity.access$400(SignUpPagerActivity.this), true), SignUpPagerActivity.REQUEST_CODE_CONFIRM_PHONE);
                                        } else {
/* 287 */                                   SignUpPagerActivity.this.startActivity(new Intent(SignUpPagerActivity.this, (Class<?>) ChannelsListNUXActivity.class));
                                        }
                                    } catch (VineLoggingException e) {
/* 269 */                               SignUpPagerActivity.this.dismissDialog();
/* 270 */                               Util.showCenteredToast(SignUpPagerActivity.this, C0257R.string.failed_to_add_account_on_device);
/* 271 */                               CrashUtil.logException(e);
/* 272 */                               SignUpPagerActivity.this.finish();
/* 318 */                               return;
                                    }
                                    break;
                                case 2:
/* 293 */                           if (SignUpPagerActivity.access$000(SignUpPagerActivity.this) != null) {
/* 294 */                               FlurryUtils.trackNuxAccountCreated("twitter");
/* 295 */                               ProgressDialog dialog = new ProgressDialog(SignUpPagerActivity.this, C0257R.style.ProgressDialogTheme);
/* 296 */                               SignUpPagerActivity.this.mProgressDialog = dialog;
/* 297 */                               dialog.setProgressStyle(0);
/* 298 */                               dialog.setMessage(SignUpPagerActivity.this.getString(C0257R.string.signing_up));
/* 299 */                               DialogUtils.showDialogUnsafe(dialog);
/* 300 */                               SignUpPagerActivity.this.mAppController.loginCheckTwitter(SignUpPagerActivity.access$000(SignUpPagerActivity.this).twitterUsername, SignUpPagerActivity.access$000(SignUpPagerActivity.this).twitterToken, SignUpPagerActivity.access$000(SignUpPagerActivity.this).twitterSecret, SignUpPagerActivity.access$000(SignUpPagerActivity.this).userId, false);
                                    } else {
/* 304 */                               FlurryUtils.onSignupFailure(true, new VineError(-1, "mVineLogin is null."), -1);
/* 305 */                               SignUpPagerActivity.this.finish();
                                    }
                                    break;
                            }
/* 318 */                   return;
                        }
/* 311 */               SignUpPagerActivity.this.dismissDialog();
/* 312 */               if (!TextUtils.isEmpty(reasonPhrase)) {
/* 313 */                   Util.showCenteredToast(SignUpPagerActivity.this, reasonPhrase);
                        } else {
/* 315 */                   Util.showCenteredToast(SignUpPagerActivity.this, C0257R.string.error_sign_up);
                        }
                    }

                    @Override // co.vine.android.client.AppSessionListener
/* 322 */           public void onLoginComplete(Session session, String reqId, int statusCode, String reasonPhrase, int errorCode, VineLogin login) {
/* 323 */               SignUpPagerActivity.this.dismissDialog();
/* 324 */               if (statusCode == 200) {
/* 325 */                   Util.showCenteredToast(SignUpPagerActivity.this, SignUpPagerActivity.this.getString(C0257R.string.login_success));
/* 326 */                   if (SignUpPagerActivity.access$300(SignUpPagerActivity.this)) {
/* 327 */                       SignUpPagerActivity.this.setResult(-1);
/* 328 */                       SignUpPagerActivity.this.finish();
/* 346 */                       return;
                            } else if (!TextUtils.isEmpty(SignUpPagerActivity.access$400(SignUpPagerActivity.this))) {
/* 331 */                       ConfirmationFlowActivity.requestPhoneVerification(SignUpPagerActivity.this.mAppController, SignUpPagerActivity.access$400(SignUpPagerActivity.this));
/* 332 */                       SignUpPagerActivity.this.startActivityForResult(ConfirmationFlowActivity.getIntent(SignUpPagerActivity.this, SignUpPagerActivity.access$400(SignUpPagerActivity.this), true), SignUpPagerActivity.REQUEST_CODE_CONFIRM_PHONE);
/* 346 */                       return;
                            } else {
/* 335 */                       SignUpPagerActivity.this.startActivity(new Intent(SignUpPagerActivity.this, (Class<?>) ChannelsListNUXActivity.class));
/* 336 */                       SignUpPagerActivity.this.mAppController.getEditions();
/* 346 */                       return;
                            }
                        }
/* 340 */               if (!TextUtils.isEmpty(reasonPhrase)) {
/* 341 */                   Util.showCenteredToast(SignUpPagerActivity.this, reasonPhrase);
                        } else {
/* 343 */                   Util.showCenteredToast(SignUpPagerActivity.this, C0257R.string.error_sign_up);
                        }
                    }

                    @Override // co.vine.android.client.AppSessionListener
/* 351 */           public void onCheckTwitterComplete(String reqId, int statusCode, String reasonPhrase, int errorCode, boolean accountExists, VineUser user, VineLogin login) {
/* 352 */               if (accountExists && statusCode == 200) {
                            try {
/* 355 */                       SignUpPagerActivity.this.mAppController.loginComplete(SignUpPagerActivity.this.mAppController.getActiveSession(), statusCode, user.username, null, login, user.avatarUrl);
/* 364 */                       SignUpPagerActivity.this.dismissDialog();
/* 365 */                       if (SignUpPagerActivity.access$300(SignUpPagerActivity.this)) {
/* 366 */                           SignUpPagerActivity.this.setResult(-1);
/* 367 */                           SignUpPagerActivity.this.finish();
/* 385 */                           return;
                                } else if (!TextUtils.isEmpty(SignUpPagerActivity.access$400(SignUpPagerActivity.this))) {
/* 370 */                           ConfirmationFlowActivity.requestPhoneVerification(SignUpPagerActivity.this.mAppController, SignUpPagerActivity.access$400(SignUpPagerActivity.this));
/* 371 */                           SignUpPagerActivity.this.startActivityForResult(ConfirmationFlowActivity.getIntent(SignUpPagerActivity.this, SignUpPagerActivity.access$400(SignUpPagerActivity.this), true), SignUpPagerActivity.REQUEST_CODE_CONFIRM_PHONE);
/* 385 */                           return;
                                } else {
/* 374 */                           SignUpPagerActivity.this.startActivity(new Intent(SignUpPagerActivity.this, (Class<?>) ChannelsListNUXActivity.class));
/* 385 */                           return;
                                }
                            } catch (VineLoggingException e) {
/* 358 */                       Util.showCenteredToast(SignUpPagerActivity.this, C0257R.string.failed_to_add_account_on_device);
/* 359 */                       CrashUtil.logException(e);
/* 360 */                       SignUpPagerActivity.this.dismissDialog();
/* 361 */                       SignUpPagerActivity.this.finish();
/* 385 */                       return;
                            }
                        }
/* 377 */               if (errorCode == VineKnownErrors.BAD_CREDENTIALS.code) {
/* 379 */                   Bundle extras = new Bundle();
/* 380 */                   extras.putParcelable("user", login);
/* 381 */                   SignUpPagerActivity.this.startActivity(SignUpPagerActivity.getIntent(SignUpPagerActivity.this, extras));
/* 385 */                   return;
                        }
/* 383 */               Util.showCenteredToast(SignUpPagerActivity.this, reasonPhrase);
                    }
                }

/* 388 */       public static Intent getIntent(Context context, Bundle extras) {
/* 389 */           Intent intent = new Intent(context, (Class<?>) SignUpPagerActivity.class);
/* 390 */           if (extras != null) {
/* 391 */               intent.putExtras(extras);
                    }
/* 393 */           return intent;
                }
            }
