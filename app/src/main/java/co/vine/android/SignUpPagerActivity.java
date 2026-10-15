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
            
            
    public class SignUpPagerActivity extends BaseControllerActionBarActivity {
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

        static  VineLogin access$000(SignUpPagerActivity x0) {
            return x0.mVineLogin;
                }

        static  WeakReference access$102(SignUpPagerActivity x0, WeakReference x1) {
            x0.mAvatarFrag = x1;
                    return x1;
                }

        static  WeakReference access$202(SignUpPagerActivity x0, WeakReference x1) {
            x0.mDetailsFrag = x1;
                    return x1;
                }

        static  boolean access$300(SignUpPagerActivity x0) {
            return x0.mFinish;
                }

        static  String access$400(SignUpPagerActivity x0) {
            return x0.mPhone;
                }

                @Override 
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState, R.layout.sign_up_flow, false);
            Intent intent = getIntent();
            if (intent != null) {
                this.mFinish = intent.getBooleanExtra("finish", false);
                    }
            this.mPager = (ConfigurableViewPager) findViewById(R.id.pager);
            this.mPager.setSwipingEnabled(false);
            this.mPager.setAdapter(new SignUpPagerAdapter(getSupportFragmentManager()));
            this.mAppSessionListener = new SignUpListener();
            this.mVineLogin = (VineLogin) getIntent().getParcelableExtra("user");
            if (savedInstanceState != null) {
                this.mLogin = savedInstanceState.getString(STATE_LOGIN);
                this.mName = savedInstanceState.getString(STATE_NAME);
                this.mPassword = savedInstanceState.getString(STATE_PASSWORD);
                this.mPhone = savedInstanceState.getString(STATE_PHONE);
                this.mProfile = (Uri) savedInstanceState.getParcelable(STATE_PROFILE);
                    }
            setupActionBar((Boolean) true, (Boolean) true, (String) null, (Boolean) true, (Boolean) true);
            FlurryUtils.trackNuxScreenDisplayed("sign_up_username");
                }

                @Override 
        protected void onSaveInstanceState(Bundle outState) {
            super.onSaveInstanceState(outState);
            outState.putString(STATE_LOGIN, this.mLogin);
            outState.putString(STATE_PASSWORD, this.mPassword);
            outState.putString(STATE_NAME, this.mName);
            outState.putString(STATE_PHONE, this.mPhone);
            outState.putParcelable(STATE_PROFILE, this.mProfile);
                }

                @Override 
       public void onBackPressed() {
           if (this.mPager.getCurrentItem() == 0) {
               super.onBackPressed();
                    } else {
               this.mPager.setCurrentItem(this.mPager.getCurrentItem() - 1);
                    }
                }

                @Override 
       public boolean onOptionsItemSelected(MenuItem item) {
                    switch (item.getItemId()) {
                        case android.R.id.home:
                   onBackPressed();
                   return true;
                        default:
                   return super.onOptionsItemSelected(item);
                    }
                }

       protected SignUpDetailsFragment getDetailsFragment() {
           return (SignUpDetailsFragment) this.mDetailsFrag.get();
                }

       protected void toNextStep() {
                    switch (this.mPager.getCurrentItem()) {
                        case 0:
                   if (this.mTwitterUser != null) {
                       ProgressDialog dialog = new ProgressDialog(this, R.style.ProgressDialogTheme);
                       dialog.setMessage(getString(R.string.login_validating));
                       dialog.setProgress(0);
                       DialogUtils.showDialogUnsafe(dialog);
                       this.mProgressDialog = dialog;
                       this.mAppController.signUp(null, null, this.mName, this.mPhone, this.mProfile, this.mTwitterUser, this.mVineLogin);
                       if (this.mFollowVineOnTwitter) {
                           this.mAppController.followVineOnTwitter();
                                }
                       FlurryUtils.trackNuxScreenDisplayed("sign_up_twitter");
                            } else {
                       this.mPager.setCurrentItem(1);
                       FlurryUtils.trackNuxScreenDisplayed("sign_up_email");
                       if (this.mDetailsFrag != null) {
                           Fragment details = this.mDetailsFrag.get();
                           if (details instanceof SignUpDetailsFragment) {
                               ((SignUpDetailsFragment) details).onMoveTo();
                                    }
                                }
                            }
                            break;
                        case 1:
                   ProgressDialog dialog2 = new ProgressDialog(this, R.style.ProgressDialogTheme);
                   this.mProgressDialog = dialog2;
                   dialog2.setProgressStyle(0);
                   dialog2.setMessage(getString(R.string.signing_up));
                   DialogUtils.showDialogUnsafe(dialog2);
                   this.mAppController.signUp(this.mLogin, this.mPassword, this.mName, this.mPhone, this.mProfile, null, null);
                            break;
                    }
                }

       protected void setBarTitle(int resId) {
           setupActionBar((Boolean) null, (Boolean) true, resId, (Boolean) null, (Boolean) true);
                }

       public void setLogin(String login) {
           this.mLogin = login;
                }

       public void setPassword(String password) {
           this.mPassword = password;
                }

       public void setPhone(String phone) {
           this.mPhone = phone;
                }

       public void setName(String name) {
           this.mName = name;
                }

       public void setProfile(Uri uri) {
           this.mProfile = uri;
                }

       public void setTwitterUser(TwitterUser tUser) {
           this.mTwitterUser = tUser;
                }

       public void setFollowVineOnTwitter(boolean followVineOnTwitter) {
           this.mFollowVineOnTwitter = followVineOnTwitter;
                }

                @Override 
       protected void onActivityResult(int requestCode, int resultCode, Intent data) {
                    Fragment frag;
                    Fragment frag2;
                    switch (requestCode) {
                        case REQUEST_CODE_CONFIRM_PHONE :
                   startActivity(new Intent(this, (Class<?>) ChannelsListNUXActivity.class));
                            break;
                        default:
                            switch (this.mPager.getCurrentItem()) {
                                case 0:
                           if (this.mAvatarFrag != null && (frag2 = this.mAvatarFrag.get()) != null) {
                               frag2.onActivityResult(requestCode, resultCode, data);
                                        break;
                                    }
                                    break;
                                case 1:
                           if (this.mDetailsFrag != null && (frag = this.mDetailsFrag.get()) != null) {
                               frag.onActivityResult(requestCode, resultCode, data);
                                        break;
                                    }
                                    break;
                            }
                            break;
                    }
                }

       private class SignUpPagerAdapter extends FragmentStatePagerAdapter {
           public SignUpPagerAdapter(FragmentManager fm) {
               super(fm);
                    }

                    @Override 
           public Fragment getItem(int i) {
                        switch (i) {
                            case 0:
                       SignUpNameAvatarFragment avatarFragment = new SignUpNameAvatarFragment();
                       if (SignUpPagerActivity.access$000(SignUpPagerActivity.this) != null) {
                           avatarFragment.setArguments(SignUpPagerActivity.this.getIntent().getExtras());
                                }
                       SignUpPagerActivity.access$102(SignUpPagerActivity.this, new WeakReference(avatarFragment));
                       return avatarFragment;
                            case 1:
                       SignUpDetailsFragment detailsFragment = new SignUpDetailsFragment();
                       SignUpPagerActivity.access$202(SignUpPagerActivity.this, new WeakReference(detailsFragment));
                                return detailsFragment;
                            default:
                       throw new IllegalArgumentException("no pager found for " + i);
                        }
                    }

                    @Override 
           public int getCount() {
               return 2;
                    }
                }

       class SignUpListener extends AppSessionListener {
           SignUpListener() {
                    }

                    @Override 
           public void onSignUpComplete(String reqId, int statusCode, String reasonPhrase, VineLogin vineLogin, String accountName, String password, String avatarUrl) {
               if (statusCode == 200 && vineLogin != null && vineLogin.userId > 0) {
                            switch (vineLogin.loginType) {
                                case 1:
                           FlurryUtils.trackNuxAccountCreated("email");
                                    try {
                               SignUpPagerActivity.this.mAppController.loginComplete(SignUpPagerActivity.this.mAppController.getActiveSession(), statusCode, accountName, password, vineLogin, avatarUrl);
                               SignUpPagerActivity.this.dismissDialog();
                               if (SignUpPagerActivity.access$300(SignUpPagerActivity.this)) {
                                   SignUpPagerActivity.this.setResult(-1);
                                   SignUpPagerActivity.this.finish();
                                        } else if (!TextUtils.isEmpty(SignUpPagerActivity.access$400(SignUpPagerActivity.this))) {
                                   ConfirmationFlowActivity.requestPhoneVerification(SignUpPagerActivity.this.mAppController, SignUpPagerActivity.access$400(SignUpPagerActivity.this));
                                   SignUpPagerActivity.this.startActivityForResult(ConfirmationFlowActivity.getIntent(SignUpPagerActivity.this, SignUpPagerActivity.access$400(SignUpPagerActivity.this), true), SignUpPagerActivity.REQUEST_CODE_CONFIRM_PHONE);
                                        } else {
                                   SignUpPagerActivity.this.startActivity(new Intent(SignUpPagerActivity.this, (Class<?>) ChannelsListNUXActivity.class));
                                        }
                                    } catch (VineLoggingException e) {
                               SignUpPagerActivity.this.dismissDialog();
                               Util.showCenteredToast(SignUpPagerActivity.this, R.string.failed_to_add_account_on_device);
                               CrashUtil.logException(e);
                               SignUpPagerActivity.this.finish();
                               return;
                                    }
                                    break;
                                case 2:
                           if (SignUpPagerActivity.access$000(SignUpPagerActivity.this) != null) {
                               FlurryUtils.trackNuxAccountCreated("twitter");
                               ProgressDialog dialog = new ProgressDialog(SignUpPagerActivity.this, R.style.ProgressDialogTheme);
                               SignUpPagerActivity.this.mProgressDialog = dialog;
                               dialog.setProgressStyle(0);
                               dialog.setMessage(SignUpPagerActivity.this.getString(R.string.signing_up));
                               DialogUtils.showDialogUnsafe(dialog);
                               SignUpPagerActivity.this.mAppController.loginCheckTwitter(SignUpPagerActivity.access$000(SignUpPagerActivity.this).twitterUsername, SignUpPagerActivity.access$000(SignUpPagerActivity.this).twitterToken, SignUpPagerActivity.access$000(SignUpPagerActivity.this).twitterSecret, SignUpPagerActivity.access$000(SignUpPagerActivity.this).userId, false);
                                    } else {
                               FlurryUtils.onSignupFailure(true, new VineError(-1, "mVineLogin is null."), -1);
                               SignUpPagerActivity.this.finish();
                                    }
                                    break;
                            }
                   return;
                        }
               SignUpPagerActivity.this.dismissDialog();
               if (!TextUtils.isEmpty(reasonPhrase)) {
                   Util.showCenteredToast(SignUpPagerActivity.this, reasonPhrase);
                        } else {
                   Util.showCenteredToast(SignUpPagerActivity.this, R.string.error_sign_up);
                        }
                    }

                    @Override 
           public void onLoginComplete(Session session, String reqId, int statusCode, String reasonPhrase, int errorCode, VineLogin login) {
               SignUpPagerActivity.this.dismissDialog();
               if (statusCode == 200) {
                   Util.showCenteredToast(SignUpPagerActivity.this, SignUpPagerActivity.this.getString(R.string.login_success));
                   if (SignUpPagerActivity.access$300(SignUpPagerActivity.this)) {
                       SignUpPagerActivity.this.setResult(-1);
                       SignUpPagerActivity.this.finish();
                       return;
                            } else if (!TextUtils.isEmpty(SignUpPagerActivity.access$400(SignUpPagerActivity.this))) {
                       ConfirmationFlowActivity.requestPhoneVerification(SignUpPagerActivity.this.mAppController, SignUpPagerActivity.access$400(SignUpPagerActivity.this));
                       SignUpPagerActivity.this.startActivityForResult(ConfirmationFlowActivity.getIntent(SignUpPagerActivity.this, SignUpPagerActivity.access$400(SignUpPagerActivity.this), true), SignUpPagerActivity.REQUEST_CODE_CONFIRM_PHONE);
                       return;
                            } else {
                       SignUpPagerActivity.this.startActivity(new Intent(SignUpPagerActivity.this, (Class<?>) ChannelsListNUXActivity.class));
                       SignUpPagerActivity.this.mAppController.getEditions();
                       return;
                            }
                        }
               if (!TextUtils.isEmpty(reasonPhrase)) {
                   Util.showCenteredToast(SignUpPagerActivity.this, reasonPhrase);
                        } else {
                   Util.showCenteredToast(SignUpPagerActivity.this, R.string.error_sign_up);
                        }
                    }

                    @Override 
           public void onCheckTwitterComplete(String reqId, int statusCode, String reasonPhrase, int errorCode, boolean accountExists, VineUser user, VineLogin login) {
               if (accountExists && statusCode == 200) {
                            try {
                       SignUpPagerActivity.this.mAppController.loginComplete(SignUpPagerActivity.this.mAppController.getActiveSession(), statusCode, user.username, null, login, user.avatarUrl);
                       SignUpPagerActivity.this.dismissDialog();
                       if (SignUpPagerActivity.access$300(SignUpPagerActivity.this)) {
                           SignUpPagerActivity.this.setResult(-1);
                           SignUpPagerActivity.this.finish();
                           return;
                                } else if (!TextUtils.isEmpty(SignUpPagerActivity.access$400(SignUpPagerActivity.this))) {
                           ConfirmationFlowActivity.requestPhoneVerification(SignUpPagerActivity.this.mAppController, SignUpPagerActivity.access$400(SignUpPagerActivity.this));
                           SignUpPagerActivity.this.startActivityForResult(ConfirmationFlowActivity.getIntent(SignUpPagerActivity.this, SignUpPagerActivity.access$400(SignUpPagerActivity.this), true), SignUpPagerActivity.REQUEST_CODE_CONFIRM_PHONE);
                           return;
                                } else {
                           SignUpPagerActivity.this.startActivity(new Intent(SignUpPagerActivity.this, (Class<?>) ChannelsListNUXActivity.class));
                           return;
                                }
                            } catch (VineLoggingException e) {
                       Util.showCenteredToast(SignUpPagerActivity.this, R.string.failed_to_add_account_on_device);
                       CrashUtil.logException(e);
                       SignUpPagerActivity.this.dismissDialog();
                       SignUpPagerActivity.this.finish();
                       return;
                            }
                        }
               if (errorCode == VineKnownErrors.BAD_CREDENTIALS.code) {
                   Bundle extras = new Bundle();
                   extras.putParcelable("user", login);
                   SignUpPagerActivity.this.startActivity(SignUpPagerActivity.getIntent(SignUpPagerActivity.this, extras));
                   return;
                        }
               Util.showCenteredToast(SignUpPagerActivity.this, reasonPhrase);
                    }
                }

       public static Intent getIntent(Context context, Bundle extras) {
           Intent intent = new Intent(context, (Class<?>) SignUpPagerActivity.class);
           if (extras != null) {
               intent.putExtras(extras);
                    }
           return intent;
                }
            }
