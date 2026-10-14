            package co.vine.android;

            import android.app.ProgressDialog;
            import android.content.Context;
            import android.content.DialogInterface;
            import android.content.Intent;
            import android.content.SharedPreferences;
            import android.database.sqlite.SQLiteException;
            import android.os.Build;
            import android.os.Bundle;
            import android.os.Handler;
            import android.support.v7.app.ActionBar;
            import android.text.Spannable;
            import android.text.Spanned;
            import android.text.method.LinkMovementMethod;
            import android.text.style.ForegroundColorSpan;
            import android.view.View;
            import android.widget.Button;
            import android.widget.TextView;
            import co.vine.android.api.VineKnownErrors;
            import co.vine.android.api.VineLogin;
            import co.vine.android.api.VineUser;
            import co.vine.android.client.AppController;
            import co.vine.android.client.AppSessionListener;
            import co.vine.android.player.VideoViewInterface;
            import co.vine.android.util.BuildUtil;
            import co.vine.android.util.ClientFlagsHelper;
            import co.vine.android.util.CommonUtil;
            import co.vine.android.util.CrashUtil;
            import co.vine.android.util.CrossConstants;
            import co.vine.android.util.SystemUtil;
            import co.vine.android.util.Util;
            import co.vine.android.util.analytics.FlurryUtils;
            import co.vine.android.widget.Typefaces;
            import co.vine.android.widget.TypefacesSpan;
            import co.vine.android.widgets.PromptDialogSupportFragment;
            import com.edisonwang.android.slog.SLog;
            import com.googlecode.javacv.cpp.avformat;
            import com.twitter.android.sdk.Twitter;
            
            
    public class StartActivity extends BaseControllerActionBarActivity implements View.OnClickListener {
                public static final String ACTION_ACTIVITY_NOTIFICATION_PRESSED = "co.vine.android.ACTIVITY_NOTIFICATION_PRESSED";
                public static final String ACTION_MESSAGE_NOTIFICATION_PRESSED = "co.vine.android.MESSAGE_NOTIFICATION_PRESSED";
                public static final String ACTION_ONBOARD_NOTIFICATION_PRESSED = "co.vine.android.ONBOARD_NOTIFICATION_PRESSED";
                public static final String ACTION_RECORD = "co.vine.android.RECORD";
                public static final String ACTION_VIEW_UPLOAD_LIST = "co.vine.android.UPLOAD_LIST";
                private static final int DIALOG_ACTIVATE_ACCOUNT = 1;
                public static final String EXTRA_IS_NEW_USER = "is_new_user";
                public static final String EXTRA_LOGIN_REQUEST = "login_request_start_activity";
                private static final String PREF_LOG_STARTUP_FAIL = "pref_log_startup_fail";
                private static final int REQUEST_CODE_TWITTER_SDK = 1;
                private static final String STATE_AUTH_INTENT = "auth_intent";
                private static final String STATE_STOP_POSITION = "state_p";
                private Intent mAuthIntent;
                private Handler mHandler = new Handler();
                private boolean mLoginRequest;
                private int mStopPosition;
                private Twitter mTwitter;
                private VideoViewInterface mVideoView;

        static  void access$000(StartActivity x0) {
            x0.openStartVideo();
                }

        static  Handler access$100(StartActivity x0) {
            return x0.mHandler;
                }

        static  VideoViewInterface access$200(StartActivity x0) {
            return x0.mVideoView;
                }

        static  boolean access$300(StartActivity x0) {
            return x0.mLoginRequest;
                }

        static  Intent access$400(StartActivity x0) {
            return x0.mAuthIntent;
                }

        static  void access$500(StartActivity x0, Intent x1, boolean x2) {
            x0.loginCheckTwitter(x1, x2);
                }

                @Override 
        protected void onStop() {
            FlurryUtils.trackLockOutSessionCount();
            super.onStop();
                }

        private boolean processIntent(Intent i) {
            setRequestedOrientation(1);
            AppController appController = AppController.getInstance(this);
            if (i != null && i.getAction() != null && i.getAction().startsWith("com.facebook.application")) {
                this.mLoginRequest = false;
                    } else {
                this.mLoginRequest = i != null && i.getBooleanExtra(EXTRA_LOGIN_REQUEST, false);
                    }
            if (appController.getActiveSession().isLoggedIn()) {
                if (this.mLoginRequest) {
                    setResult(-1);
                        } else {
                    AppImpl.getInstance().updateClientProfile(this, appController, i != null && ACTION_RECORD.equals(i.getAction()));
                   if (i != null) {
                       String action = i.getAction();
                       AppInterface impl = AppImpl.getInstance();
                       Intent intent = null;
                       if (ACTION_MESSAGE_NOTIFICATION_PRESSED.equals(action)) {
                           this.mAppController.clearPushNotifications(2);
                           String dataString = i.getDataString();
                           String username = i.getStringExtra("username");
                           long userId = i.getLongExtra("user_id", -1L);
                           boolean amFollowing = i.getBooleanExtra("am_following", false);
                           long conversationObjectId = dataString != null ? Long.parseLong(dataString) : -1L;
                                    if (conversationObjectId > 0) {
                               intent = ConversationActivity.getIntent(this, conversationObjectId, username, userId, false, amFollowing, false);
                               intent.setAction(action);
                                    }
                                }
                       if (intent == null) {
                           if (!ACTION_RECORD.equals(action)) {
                               intent = new Intent(this, (Class<?>) HomeTabActivity.class);
                               intent.setFlags(335544320);
                               intent.setAction(action);
                               if (i.getExtras() != null) {
                                   intent.putExtras(i.getExtras());
                                        }
                               if ("android.intent.action.VIEW".equals(action)) {
                                   intent.setData(i.getData());
                                        } else if (ACTION_ACTIVITY_NOTIFICATION_PRESSED.equals(action)) {
                                   intent.setAction(action);
                                   if (i.getData() != null) {
                                       intent.setAction("android.intent.action.VIEW");
                                       intent.setData(i.getData());
                                            }
                                   this.mAppController.clearPushNotifications(1);
                                   intent.setFlags(avformat.AVFMT_SEEK_TO_PTS);
                                        } else if (ACTION_MESSAGE_NOTIFICATION_PRESSED.equals(action)) {
                                   intent.setAction(action);
                                   intent.setFlags(0);
                                        }
                                    } else {
                               intent = impl.getRecordingIntent(this, 131072, "StartActivity: " + getCallingActivity());
                                    }
                                }
                       if (intent != null) {
                           startActivity(intent);
                                }
                            }
                        }
               finish();
               return true;
                    }
           ClientFlagsHelper.fetchClientFlags(this, false, false);
           return false;
                }

                @Override 
       public void onNewIntent(Intent i) {
           processIntent(i);
                }

                @Override 
       public void onCreate(Bundle savedInstanceState) {
                    ActionBar ab;
           super.onCreate(savedInstanceState, C0257R.layout.start, false);
           if ((getIntent().getFlags() & 4194304) != 0 && "android.intent.action.MAIN".equals(getIntent().getAction())) {
               finish();
               return;
                    }
           if (Build.VERSION.SDK_INT <= 14 && (ab = getSupportActionBar()) != null) {
               ab.hide();
                    }
           if (savedInstanceState != null) {
                        try {
                            if (savedInstanceState.containsKey(STATE_AUTH_INTENT)) {
                       this.mAuthIntent = (Intent) savedInstanceState.getParcelable(STATE_AUTH_INTENT);
                       loginCheckTwitter(this.mAuthIntent, false);
                            }
                        } catch (SQLiteException e) {
                   SharedPreferences prefs = Util.getDefaultSharedPrefs(this);
                   CrashUtil.logException(e, "pref_log_startup_fail: {}.", Integer.valueOf(prefs.getInt(PREF_LOG_STARTUP_FAIL, 0)));
                   Util.showCenteredToast(this, C0257R.string.startup_error);
                   prefs.edit().putInt(PREF_LOG_STARTUP_FAIL, 1).apply();
                   finish();
                   return;
                        }
                    }
                    try {
               boolean processIntent = processIntent(getIntent());
               if (!processIntent) {
                   ClientFlagsHelper.fetchClientFlags(this, false, false);
                   TypefacesSpan boldSpan = new TypefacesSpan(Typefaces.get(this).getContentTypeface(1, 4));
                   TypefacesSpan[] mStyleSpanBoldSingle = {boldSpan};
                   Button signUpEmail = (Button) findViewById(C0257R.id.start_sign_up_email);
                   signUpEmail.setText(getString(C0257R.string.start_signup_email).replace("\"", ""));
                   signUpEmail.setOnClickListener(this);
                   Button signUpTwitter = (Button) findViewById(C0257R.id.sign_up_twitter);
                   signUpTwitter.setText(getString(C0257R.string.start_signup_twitter).replace("\"", ""));
                   signUpTwitter.setOnClickListener(this);
                   TextView signIn = (TextView) findViewById(C0257R.id.start_sign_in);
                   signIn.setMovementMethod(LinkMovementMethod.getInstance());
                   Spanned spanned = Util.getSpannedText(mStyleSpanBoldSingle, getString(C0257R.string.start_already_signed_in), CommonUtil.MARKER_QUOTES);
                   signIn.setText(spanned);
                   Spannable spannable = (Spannable) signIn.getText();
                   int start = spanned.getSpanStart(boldSpan);
                   int end = spanned.getSpanEnd(boldSpan);
                   Util.safeSetSpan(spannable, new ForegroundColorSpan(getResources().getColor(C0257R.color.solid_white)), start, end, 33);
                   signIn.setOnClickListener(this);
                   this.mTwitter = this.mAppController.getTwitter();
                   this.mVideoView = (VideoViewInterface) findViewById(C0257R.id.start_video);
                   this.mVideoView.setKeepScreenOn(true);
                   this.mVideoView.setLooping(true);
                   if (SystemUtil.isNormalVideoPlayable(this) == SystemUtil.PrefBooleanState.UNKNOWN) {
                       SLog.m464i("Video test started.");
                       this.mVideoView.setVideoPathDirect(SystemUtil.getPathFromResourceRaw(this, C0257R.raw.vine_splash));
                       this.mVideoView.setOnErrorListener(new C02971());
                       this.mVideoView.setOnPreparedListener(new C02982());
                            } else {
                       openStartVideo();
                            }
                   if (savedInstanceState != null && savedInstanceState.containsKey(STATE_STOP_POSITION)) {
                       this.mStopPosition = savedInstanceState.getInt(STATE_STOP_POSITION);
                            }
                   this.mAppSessionListener = new StartSessionListener();
                   this.mAppController = AppController.getInstance(this);
                   Util.getDefaultSharedPrefs(this).edit().putInt(PREF_LOG_STARTUP_FAIL, 0).apply();
                            UpdateManager.checkOnStartup(this);
                        }
                    } catch (RuntimeException e2) {
               CrashUtil.logException(e2, "Failed to process intent: " + getCallingPackage() + " - " + getIntent().getAction() + " - " + getCallingActivity(), new Object[0]);
               Util.showCenteredToast(this, C0257R.string.startup_error);
               finish();
                    }
                }

                
       class C02971 implements VideoViewInterface.OnErrorListener {
           C02971() {
                    }

                    @Override 
           public boolean onError(VideoViewInterface videoView, int what, int extra) {
               SLog.m446d("Video test failed.");
               SystemUtil.setNormalVideoPlayable(StartActivity.this.getApplicationContext(), false);
               StartActivity.access$100(StartActivity.this).postDelayed(new AnonymousClass1(), 50L);
               return true;
                    }

                    
           class AnonymousClass1 implements Runnable {
               AnonymousClass1() {
                        }

                        @Override 
               public void run() {
                   StartActivity.access$000(StartActivity.this);
                        }
                    }
                }

                
       class C02982 implements VideoViewInterface.OnPreparedListener {
           C02982() {
                    }

                    @Override 
           public void onPrepared(VideoViewInterface view) {
               SystemUtil.setNormalVideoPlayable(StartActivity.this.getApplicationContext(), true);
               StartActivity.access$200(StartActivity.this).start();
               SLog.m446d("Video test completed, assuming success, may change later.");
                    }
                }

       private void openStartVideo() {
           this.mVideoView.setAutoPlayOnPrepared(true);
           this.mVideoView.setOnPreparedListener(null);
           this.mVideoView.setOnErrorListener(null);
           this.mVideoView.setVideoPathDirect(SystemUtil.getPathFromResourceRaw(this, C0257R.raw.vine_splash));
           this.mVideoView.seekTo(this.mStopPosition);
           this.mVideoView.start();
                }

                @Override 
       protected void onPause() {
           super.onPause();
           if (this.mVideoView != null) {
               this.mStopPosition = this.mVideoView.getCurrentPosition();
               this.mVideoView.pause();
                    }
           dismissDialog();
                }

                @Override 
       protected void onDestroy() {
           super.onDestroy();
           if (this.mVideoView != null) {
               this.mVideoView.suspend();
                    }
                }

                @Override 
       protected void onSaveInstanceState(Bundle outState) {
           super.onSaveInstanceState(outState);
           if (this.mAuthIntent != null) {
               outState.putParcelable(STATE_AUTH_INTENT, this.mAuthIntent);
                    }
                }

                @Override 
       protected void onResume() {
           super.onResume();
           if (this.mVideoView != null) {
               this.mVideoView.seekTo(this.mStopPosition);
               if (this.mVideoView.isPlaying()) {
                   this.mVideoView.resume();
                        } else {
                   this.mVideoView.start();
                        }
                    }
           if (this.mAppController.isLoggedIn() && this.mLoginRequest) {
               setResult(-1);
               finish();
                    }
                }

       public static void toStart(Context context) {
           toStart(context, false);
                }

       public static void toStart(Context context, boolean isFromNUX) {
           Intent i = getStartIntent(context);
           i.putExtra(EXTRA_IS_NEW_USER, isFromNUX);
           context.startActivity(i);
           if (BuildUtil.isExplore() && !(context instanceof StartActivity)) {
               broadcastFinish(context);
                    }
                }

       private static void broadcastFinish(Context context) {
           Intent i = new Intent();
           i.setAction(BaseActionBarActivity.FINISH_BROADCAST_ACTION);
           context.sendBroadcast(i, CrossConstants.BROADCAST_PERMISSION);
                }

       public static Intent getStartIntent(Context context) {
           return getStartIntent(context, "android.intent.action.VIEW");
                }

       public static Intent getStartIntent(Context context, String action) {
           Intent intent = new Intent(context, (Class<?>) StartActivity.class);
           intent.setAction(action);
           intent.setFlags(268468224);
           return intent;
                }

                @Override 
       public void onClick(View view) {
           int id = view.getId();
           if (id == C0257R.id.start_sign_in) {
               if (this.mLoginRequest) {
                   startActivity(LoginActivity.getIntentWithFinish(this));
                   return;
                        } else {
                   startActivity(LoginActivity.getIntent(this));
                   return;
                        }
                    }
           if (id == C0257R.id.start_sign_up_email) {
               Bundle extras = new Bundle();
               if (this.mLoginRequest) {
                   extras.putBoolean("finish", true);
                        }
               startActivity(SignUpPagerActivity.getIntent(this, extras));
               FlurryUtils.trackNuxStarted("email");
               return;
                    }
           if (id == C0257R.id.sign_up_twitter) {
                        try {
                   if (!this.mTwitter.startAuthActivityForResult(this, 1)) {
                       if (this.mLoginRequest) {
                           startActivity(LoginTwitterActivity.getIntentWithFinish(this));
                                } else {
                           startActivity(LoginTwitterActivity.getIntent(this));
                                }
                            } else {
                       FlurryUtils.trackNuxStarted("twitter-sso");
                            }
                        } catch (SecurityException e) {
                   if (this.mLoginRequest) {
                       startActivity(LoginTwitterActivity.getIntentWithFinish(this));
                            } else {
                       startActivity(LoginTwitterActivity.getIntent(this));
                            }
                        }
                    }
                }

                @Override 
       protected void onActivityResult(int requestCode, int resultCode, Intent data) {
           if (requestCode == 1) {
               loginCheckTwitter(data, false);
               this.mAuthIntent = data;
                    }
                }

       private void loginCheckTwitter(Intent data, boolean reactivate) {
           if (data != null) {
               String username = data.getStringExtra("screen_name");
               String mToken = data.getStringExtra(Twitter.EXTRA_TOKEN);
               String mSecret = data.getStringExtra(Twitter.EXTRA_TOKEN_SECRET);
               ProgressDialog dialog = new ProgressDialog(this, C0257R.style.ProgressDialogTheme);
               dialog.setProgressStyle(0);
               dialog.setMessage(getString(C0257R.string.sign_up_authorizing));
               dialog.show();
               this.mProgressDialog = dialog;
               this.mAppController.loginCheckTwitter(username, mToken, mSecret, data.getLongExtra("user_id", 0L), reactivate);
                    }
                }

       class StartSessionListener extends AppSessionListener {
           StartSessionListener() {
                    }

                    @Override 
           public void onCheckTwitterComplete(String reqId, int statusCode, String reasonPhrase, int errorCode, boolean accountExists, VineUser user, VineLogin login) {
               if (accountExists && statusCode == 200) {
                   StartActivity.this.dismissDialog();
                            try {
                       StartActivity.this.mAppController.loginComplete(StartActivity.this.mAppController.getActiveSession(), statusCode, user.username, null, login, user.avatarUrl);
                       if (StartActivity.access$300(StartActivity.this)) {
                           StartActivity.this.setResult(-1);
                           StartActivity.this.finish();
                           return;
                                } else {
                           StartActivity.toStart(StartActivity.this);
                           return;
                                }
                            } catch (VineLoggingException e) {
                       Util.showCenteredToast(StartActivity.this, C0257R.string.failed_to_add_account_on_device);
                       CrashUtil.logException(e);
                       StartActivity.this.finish();
                       return;
                            }
                        }
               StartActivity.this.dismissDialog();
               if (errorCode == VineKnownErrors.BAD_CREDENTIALS.code) {
                   Bundle extras = new Bundle();
                   extras.putParcelable("user", login);
                   StartActivity.this.startActivity(SignUpPagerActivity.getIntent(StartActivity.this, extras));
                        } else {
                   if (errorCode == VineKnownErrors.ACCOUNT_DEACTIVATED.code) {
                       PromptDialogSupportFragment p = PromptDialogSupportFragment.newInstance(1);
                       p.setMessage(C0257R.string.settings_activate_account_dialog);
                       p.setTitle(C0257R.string.settings_activate_account_title);
                       p.setNegativeButton(C0257R.string.cancel);
                       p.setPositiveButton(C0257R.string.settings_activate_account_confirm);
                       p.setListener(new C02991());
                       p.show(StartActivity.this.getSupportFragmentManager());
                       return;
                            }
                   Util.showCenteredToast(StartActivity.this, reasonPhrase);
                        }
                    }

                    
           class C02991 implements PromptDialogSupportFragment.OnDialogDoneListener {
               C02991() {
                        }

                        @Override 
               public void onDialogDone(DialogInterface dialog, int id, int which) {
                            switch (which) {
                                case -1:
                           if (StartActivity.access$400(StartActivity.this) != null) {
                               StartActivity.access$500(StartActivity.this, StartActivity.access$400(StartActivity.this), true);
                                    }
                                    break;
                            }
                        }
                    }
                }
            }
