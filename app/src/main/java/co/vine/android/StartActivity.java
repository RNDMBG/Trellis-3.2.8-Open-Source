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
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 44 */    public class StartActivity extends BaseControllerActionBarActivity implements View.OnClickListener {
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

/* 45 */        static /* synthetic */ void access$000(StartActivity x0) {
/* 46 */            x0.openStartVideo();
                }

/* 45 */        static /* synthetic */ Handler access$100(StartActivity x0) {
/* 46 */            return x0.mHandler;
                }

/* 45 */        static /* synthetic */ VideoViewInterface access$200(StartActivity x0) {
/* 46 */            return x0.mVideoView;
                }

/* 45 */        static /* synthetic */ boolean access$300(StartActivity x0) {
/* 46 */            return x0.mLoginRequest;
                }

/* 45 */        static /* synthetic */ Intent access$400(StartActivity x0) {
/* 46 */            return x0.mAuthIntent;
                }

/* 45 */        static /* synthetic */ void access$500(StartActivity x0, Intent x1, boolean x2) {
/* 46 */            x0.loginCheckTwitter(x1, x2);
                }

                @Override // co.vine.android.BaseActionBarActivity, android.support.v7.app.ActionBarActivity, android.support.v4.app.FragmentActivity, android.app.Activity
/* 80 */        protected void onStop() {
/* 81 */            FlurryUtils.trackLockOutSessionCount();
/* 82 */            super.onStop();
                }

/* 85 */        private boolean processIntent(Intent i) {
/* 86 */            setRequestedOrientation(1);
/* 87 */            AppController appController = AppController.getInstance(this);
/* 89 */            if (i != null && i.getAction() != null && i.getAction().startsWith("com.facebook.application")) {
/* 90 */                this.mLoginRequest = false;
                    } else {
/* 92 */                this.mLoginRequest = i != null && i.getBooleanExtra(EXTRA_LOGIN_REQUEST, false);
                    }
/* 95 */            if (appController.getActiveSession().isLoggedIn()) {
/* 96 */                if (this.mLoginRequest) {
/* 97 */                    setResult(-1);
                        } else {
/* 99 */                    AppImpl.getInstance().updateClientProfile(this, appController, i != null && ACTION_RECORD.equals(i.getAction()));
/* 101 */                   if (i != null) {
/* 102 */                       String action = i.getAction();
/* 103 */                       AppInterface impl = AppImpl.getInstance();
/* 104 */                       Intent intent = null;
/* 105 */                       if (ACTION_MESSAGE_NOTIFICATION_PRESSED.equals(action)) {
/* 106 */                           this.mAppController.clearPushNotifications(2);
/* 107 */                           String dataString = i.getDataString();
/* 108 */                           String username = i.getStringExtra("username");
/* 109 */                           long userId = i.getLongExtra("user_id", -1L);
/* 110 */                           boolean amFollowing = i.getBooleanExtra("am_following", false);
/* 111 */                           long conversationObjectId = dataString != null ? Long.parseLong(dataString) : -1L;
                                    if (conversationObjectId > 0) {
/* 118 */                               intent = ConversationActivity.getIntent(this, conversationObjectId, username, userId, false, amFollowing, false);
/* 120 */                               intent.setAction(action);
                                    }
                                }
/* 123 */                       if (intent == null) {
/* 124 */                           if (!ACTION_RECORD.equals(action)) {
/* 125 */                               intent = new Intent(this, (Class<?>) HomeTabActivity.class);
/* 126 */                               intent.setFlags(335544320);
/* 127 */                               intent.setAction(action);
/* 128 */                               if (i.getExtras() != null) {
/* 129 */                                   intent.putExtras(i.getExtras());
                                        }
/* 131 */                               if ("android.intent.action.VIEW".equals(action)) {
/* 132 */                                   intent.setData(i.getData());
                                        } else if (ACTION_ACTIVITY_NOTIFICATION_PRESSED.equals(action)) {
/* 134 */                                   intent.setAction(action);
/* 135 */                                   if (i.getData() != null) {
/* 136 */                                       intent.setAction("android.intent.action.VIEW");
/* 137 */                                       intent.setData(i.getData());
                                            }
/* 139 */                                   this.mAppController.clearPushNotifications(1);
/* 140 */                                   intent.setFlags(avformat.AVFMT_SEEK_TO_PTS);
                                        } else if (ACTION_MESSAGE_NOTIFICATION_PRESSED.equals(action)) {
/* 142 */                                   intent.setAction(action);
/* 143 */                                   intent.setFlags(0);
                                        }
                                    } else {
/* 146 */                               intent = impl.getRecordingIntent(this, 131072, "StartActivity: " + getCallingActivity());
                                    }
                                }
/* 150 */                       if (intent != null) {
/* 151 */                           startActivity(intent);
                                }
                            }
                        }
/* 155 */               finish();
/* 156 */               return true;
                    }
/* 159 */           ClientFlagsHelper.fetchClientFlags(this, false, false);
/* 160 */           return false;
                }

                @Override // android.support.v4.app.FragmentActivity, android.app.Activity
/* 165 */       public void onNewIntent(Intent i) {
/* 166 */           processIntent(i);
                }

                @Override // co.vine.android.BaseActionBarActivity, android.support.v7.app.ActionBarActivity, android.support.v4.app.FragmentActivity, android.app.Activity
/* 170 */       public void onCreate(Bundle savedInstanceState) {
                    ActionBar ab;
/* 171 */           super.onCreate(savedInstanceState, C0257R.layout.start, false);
/* 173 */           if ((getIntent().getFlags() & 4194304) != 0 && "android.intent.action.MAIN".equals(getIntent().getAction())) {
/* 177 */               finish();
/* 291 */               return;
                    }
/* 181 */           if (Build.VERSION.SDK_INT <= 14 && (ab = getSupportActionBar()) != null) {
/* 184 */               ab.hide();
                    }
/* 189 */           if (savedInstanceState != null) {
                        try {
                            if (savedInstanceState.containsKey(STATE_AUTH_INTENT)) {
/* 190 */                       this.mAuthIntent = (Intent) savedInstanceState.getParcelable(STATE_AUTH_INTENT);
/* 191 */                       loginCheckTwitter(this.mAuthIntent, false);
                            }
                        } catch (SQLiteException e) {
/* 282 */                   SharedPreferences prefs = Util.getDefaultSharedPrefs(this);
/* 283 */                   CrashUtil.logException(e, "pref_log_startup_fail: {}.", Integer.valueOf(prefs.getInt(PREF_LOG_STARTUP_FAIL, 0)));
/* 284 */                   Util.showCenteredToast(this, C0257R.string.startup_error);
/* 285 */                   prefs.edit().putInt(PREF_LOG_STARTUP_FAIL, 1).apply();
/* 286 */                   finish();
/* 291 */                   return;
                        }
                    }
                    try {
/* 196 */               boolean processIntent = processIntent(getIntent());
/* 204 */               if (!processIntent) {
/* 205 */                   ClientFlagsHelper.fetchClientFlags(this, false, false);
/* 207 */                   TypefacesSpan boldSpan = new TypefacesSpan(Typefaces.get(this).getContentTypeface(1, 4));
/* 209 */                   TypefacesSpan[] mStyleSpanBoldSingle = {boldSpan};
/* 213 */                   Button signUpEmail = (Button) findViewById(C0257R.id.start_sign_up_email);
/* 214 */                   signUpEmail.setText(getString(C0257R.string.start_signup_email).replace("\"", ""));
/* 215 */                   signUpEmail.setOnClickListener(this);
/* 217 */                   Button signUpTwitter = (Button) findViewById(C0257R.id.sign_up_twitter);
/* 218 */                   signUpTwitter.setText(getString(C0257R.string.start_signup_twitter).replace("\"", ""));
/* 219 */                   signUpTwitter.setOnClickListener(this);
/* 221 */                   TextView signIn = (TextView) findViewById(C0257R.id.start_sign_in);
/* 222 */                   signIn.setMovementMethod(LinkMovementMethod.getInstance());
/* 224 */                   Spanned spanned = Util.getSpannedText(mStyleSpanBoldSingle, getString(C0257R.string.start_already_signed_in), CommonUtil.MARKER_QUOTES);
/* 226 */                   signIn.setText(spanned);
/* 228 */                   Spannable spannable = (Spannable) signIn.getText();
/* 229 */                   int start = spanned.getSpanStart(boldSpan);
/* 230 */                   int end = spanned.getSpanEnd(boldSpan);
/* 232 */                   Util.safeSetSpan(spannable, new ForegroundColorSpan(getResources().getColor(C0257R.color.solid_white)), start, end, 33);
/* 235 */                   signIn.setOnClickListener(this);
/* 237 */                   this.mTwitter = this.mAppController.getTwitter();
/* 239 */                   this.mVideoView = (VideoViewInterface) findViewById(C0257R.id.start_video);
/* 240 */                   this.mVideoView.setKeepScreenOn(true);
/* 241 */                   this.mVideoView.setLooping(true);
/* 243 */                   if (SystemUtil.isNormalVideoPlayable(this) == SystemUtil.PrefBooleanState.UNKNOWN) {
/* 244 */                       SLog.m464i("Video test started.");
/* 246 */                       this.mVideoView.setVideoPathDirect(SystemUtil.getPathFromResourceRaw(this, C0257R.raw.vine_splash));
/* 247 */                       this.mVideoView.setOnErrorListener(new C02971());
/* 261 */                       this.mVideoView.setOnPreparedListener(new C02982());
                            } else {
/* 270 */                       openStartVideo();
                            }
/* 273 */                   if (savedInstanceState != null && savedInstanceState.containsKey(STATE_STOP_POSITION)) {
/* 275 */                       this.mStopPosition = savedInstanceState.getInt(STATE_STOP_POSITION);
                            }
/* 277 */                   this.mAppSessionListener = new StartSessionListener();
/* 278 */                   this.mAppController = AppController.getInstance(this);
/* 279 */                   Util.getDefaultSharedPrefs(this).edit().putInt(PREF_LOG_STARTUP_FAIL, 0).apply();
                            UpdateManager.checkOnStartup(this);
                        }
                    } catch (RuntimeException e2) {
/* 198 */               CrashUtil.logException(e2, "Failed to process intent: " + getCallingPackage() + " - " + getIntent().getAction() + " - " + getCallingActivity(), new Object[0]);
/* 199 */               Util.showCenteredToast(this, C0257R.string.startup_error);
/* 200 */               finish();
                    }
                }

                /* JADX INFO: renamed from: co.vine.android.StartActivity$1 */
/* 245 */       class C02971 implements VideoViewInterface.OnErrorListener {
/* 246 */           C02971() {
                    }

                    @Override // co.vine.android.player.VideoViewInterface.OnErrorListener
/* 249 */           public boolean onError(VideoViewInterface videoView, int what, int extra) {
/* 250 */               SLog.m446d("Video test failed.");
/* 251 */               SystemUtil.setNormalVideoPlayable(StartActivity.this.getApplicationContext(), false);
/* 252 */               StartActivity.access$100(StartActivity.this).postDelayed(new AnonymousClass1(), 50L);
/* 258 */               return true;
                    }

                    /* JADX INFO: renamed from: co.vine.android.StartActivity$1$1, reason: invalid class name */
/* 250 */           class AnonymousClass1 implements Runnable {
/* 251 */               AnonymousClass1() {
                        }

                        @Override // java.lang.Runnable
/* 254 */               public void run() {
/* 255 */                   StartActivity.access$000(StartActivity.this);
                        }
                    }
                }

                /* JADX INFO: renamed from: co.vine.android.StartActivity$2 */
/* 259 */       class C02982 implements VideoViewInterface.OnPreparedListener {
/* 260 */           C02982() {
                    }

                    @Override // co.vine.android.player.VideoViewInterface.OnPreparedListener
/* 263 */           public void onPrepared(VideoViewInterface view) {
/* 264 */               SystemUtil.setNormalVideoPlayable(StartActivity.this.getApplicationContext(), true);
/* 265 */               StartActivity.access$200(StartActivity.this).start();
/* 266 */               SLog.m446d("Video test completed, assuming success, may change later.");
                    }
                }

/* 293 */       private void openStartVideo() {
/* 294 */           this.mVideoView.setAutoPlayOnPrepared(true);
/* 295 */           this.mVideoView.setOnPreparedListener(null);
/* 296 */           this.mVideoView.setOnErrorListener(null);
/* 297 */           this.mVideoView.setVideoPathDirect(SystemUtil.getPathFromResourceRaw(this, C0257R.raw.vine_splash));
/* 298 */           this.mVideoView.seekTo(this.mStopPosition);
/* 299 */           this.mVideoView.start();
                }

                @Override // co.vine.android.BaseControllerActionBarActivity, co.vine.android.BaseActionBarActivity, android.support.v4.app.FragmentActivity, android.app.Activity
/* 304 */       protected void onPause() {
/* 305 */           super.onPause();
/* 306 */           if (this.mVideoView != null) {
/* 307 */               this.mStopPosition = this.mVideoView.getCurrentPosition();
/* 308 */               this.mVideoView.pause();
                    }
/* 310 */           dismissDialog();
                }

                @Override // co.vine.android.BaseActionBarActivity, android.support.v4.app.FragmentActivity, android.app.Activity
/* 314 */       protected void onDestroy() {
/* 315 */           super.onDestroy();
/* 316 */           if (this.mVideoView != null) {
/* 317 */               this.mVideoView.suspend();
                    }
                }

                @Override // co.vine.android.BaseActionBarActivity, android.support.v4.app.FragmentActivity, android.app.Activity
/* 322 */       protected void onSaveInstanceState(Bundle outState) {
/* 323 */           super.onSaveInstanceState(outState);
/* 324 */           if (this.mAuthIntent != null) {
/* 325 */               outState.putParcelable(STATE_AUTH_INTENT, this.mAuthIntent);
                    }
                }

                @Override // co.vine.android.BaseControllerActionBarActivity, co.vine.android.BaseActionBarActivity, android.support.v4.app.FragmentActivity, android.app.Activity
/* 330 */       protected void onResume() {
/* 331 */           super.onResume();
/* 332 */           if (this.mVideoView != null) {
/* 333 */               this.mVideoView.seekTo(this.mStopPosition);
/* 334 */               if (this.mVideoView.isPlaying()) {
/* 335 */                   this.mVideoView.resume();
                        } else {
/* 337 */                   this.mVideoView.start();
                        }
                    }
/* 340 */           if (this.mAppController.isLoggedIn() && this.mLoginRequest) {
/* 341 */               setResult(-1);
/* 342 */               finish();
                    }
                }

/* 352 */       public static void toStart(Context context) {
/* 353 */           toStart(context, false);
                }

/* 356 */       public static void toStart(Context context, boolean isFromNUX) {
/* 357 */           Intent i = getStartIntent(context);
/* 358 */           i.putExtra(EXTRA_IS_NEW_USER, isFromNUX);
/* 359 */           context.startActivity(i);
/* 360 */           if (BuildUtil.isExplore() && !(context instanceof StartActivity)) {
/* 361 */               broadcastFinish(context);
                    }
                }

/* 365 */       private static void broadcastFinish(Context context) {
/* 366 */           Intent i = new Intent();
/* 367 */           i.setAction(BaseActionBarActivity.FINISH_BROADCAST_ACTION);
/* 368 */           context.sendBroadcast(i, CrossConstants.BROADCAST_PERMISSION);
                }

/* 371 */       public static Intent getStartIntent(Context context) {
/* 372 */           return getStartIntent(context, "android.intent.action.VIEW");
                }

/* 375 */       public static Intent getStartIntent(Context context, String action) {
/* 376 */           Intent intent = new Intent(context, (Class<?>) StartActivity.class);
/* 377 */           intent.setAction(action);
/* 378 */           intent.setFlags(268468224);
/* 379 */           return intent;
                }

                @Override // android.view.View.OnClickListener
/* 383 */       public void onClick(View view) {
/* 384 */           int id = view.getId();
/* 385 */           if (id == C0257R.id.start_sign_in) {
/* 386 */               if (this.mLoginRequest) {
/* 387 */                   startActivity(LoginActivity.getIntentWithFinish(this));
/* 417 */                   return;
                        } else {
/* 389 */                   startActivity(LoginActivity.getIntent(this));
/* 417 */                   return;
                        }
                    }
/* 391 */           if (id == C0257R.id.start_sign_up_email) {
/* 392 */               Bundle extras = new Bundle();
/* 393 */               if (this.mLoginRequest) {
/* 394 */                   extras.putBoolean("finish", true);
                        }
/* 396 */               startActivity(SignUpPagerActivity.getIntent(this, extras));
/* 397 */               FlurryUtils.trackNuxStarted("email");
/* 417 */               return;
                    }
/* 398 */           if (id == C0257R.id.sign_up_twitter) {
                        try {
/* 400 */                   if (!this.mTwitter.startAuthActivityForResult(this, 1)) {
/* 401 */                       if (this.mLoginRequest) {
/* 402 */                           startActivity(LoginTwitterActivity.getIntentWithFinish(this));
                                } else {
/* 404 */                           startActivity(LoginTwitterActivity.getIntent(this));
                                }
                            } else {
/* 407 */                       FlurryUtils.trackNuxStarted("twitter-sso");
                            }
                        } catch (SecurityException e) {
/* 410 */                   if (this.mLoginRequest) {
/* 411 */                       startActivity(LoginTwitterActivity.getIntentWithFinish(this));
                            } else {
/* 413 */                       startActivity(LoginTwitterActivity.getIntent(this));
                            }
                        }
                    }
                }

                @Override // co.vine.android.BaseActionBarActivity, android.support.v4.app.FragmentActivity, android.app.Activity
/* 420 */       protected void onActivityResult(int requestCode, int resultCode, Intent data) {
/* 421 */           if (requestCode == 1) {
/* 422 */               loginCheckTwitter(data, false);
/* 423 */               this.mAuthIntent = data;
                    }
                }

/* 427 */       private void loginCheckTwitter(Intent data, boolean reactivate) {
/* 428 */           if (data != null) {
/* 431 */               String username = data.getStringExtra("screen_name");
/* 432 */               String mToken = data.getStringExtra(Twitter.EXTRA_TOKEN);
/* 433 */               String mSecret = data.getStringExtra(Twitter.EXTRA_TOKEN_SECRET);
/* 435 */               ProgressDialog dialog = new ProgressDialog(this, C0257R.style.ProgressDialogTheme);
/* 436 */               dialog.setProgressStyle(0);
/* 437 */               dialog.setMessage(getString(C0257R.string.sign_up_authorizing));
/* 438 */               dialog.show();
/* 439 */               this.mProgressDialog = dialog;
/* 441 */               this.mAppController.loginCheckTwitter(username, mToken, mSecret, data.getLongExtra("user_id", 0L), reactivate);
                    }
                }

/* 443 */       class StartSessionListener extends AppSessionListener {
/* 444 */           StartSessionListener() {
                    }

                    @Override // co.vine.android.client.AppSessionListener
/* 448 */           public void onCheckTwitterComplete(String reqId, int statusCode, String reasonPhrase, int errorCode, boolean accountExists, VineUser user, VineLogin login) {
/* 449 */               if (accountExists && statusCode == 200) {
/* 450 */                   StartActivity.this.dismissDialog();
                            try {
/* 453 */                       StartActivity.this.mAppController.loginComplete(StartActivity.this.mAppController.getActiveSession(), statusCode, user.username, null, login, user.avatarUrl);
/* 461 */                       if (StartActivity.access$300(StartActivity.this)) {
/* 462 */                           StartActivity.this.setResult(-1);
/* 463 */                           StartActivity.this.finish();
/* 502 */                           return;
                                } else {
/* 465 */                           StartActivity.toStart(StartActivity.this);
/* 502 */                           return;
                                }
                            } catch (VineLoggingException e) {
/* 456 */                       Util.showCenteredToast(StartActivity.this, C0257R.string.failed_to_add_account_on_device);
/* 457 */                       CrashUtil.logException(e);
/* 458 */                       StartActivity.this.finish();
/* 502 */                       return;
                            }
                        }
/* 468 */               StartActivity.this.dismissDialog();
/* 469 */               if (errorCode == VineKnownErrors.BAD_CREDENTIALS.code) {
/* 471 */                   Bundle extras = new Bundle();
/* 472 */                   extras.putParcelable("user", login);
/* 473 */                   StartActivity.this.startActivity(SignUpPagerActivity.getIntent(StartActivity.this, extras));
                        } else {
/* 474 */                   if (errorCode == VineKnownErrors.ACCOUNT_DEACTIVATED.code) {
/* 475 */                       PromptDialogSupportFragment p = PromptDialogSupportFragment.newInstance(1);
/* 477 */                       p.setMessage(C0257R.string.settings_activate_account_dialog);
/* 478 */                       p.setTitle(C0257R.string.settings_activate_account_title);
/* 479 */                       p.setNegativeButton(C0257R.string.cancel);
/* 480 */                       p.setPositiveButton(C0257R.string.settings_activate_account_confirm);
/* 481 */                       p.setListener(new C02991());
/* 497 */                       p.show(StartActivity.this.getSupportFragmentManager());
/* 502 */                       return;
                            }
/* 499 */                   Util.showCenteredToast(StartActivity.this, reasonPhrase);
                        }
                    }

                    /* JADX INFO: renamed from: co.vine.android.StartActivity$StartSessionListener$1 */
/* 479 */           class C02991 implements PromptDialogSupportFragment.OnDialogDoneListener {
/* 480 */               C02991() {
                        }

                        @Override // co.vine.android.widgets.PromptDialogFragment.OnDialogDoneListener
/* 483 */               public void onDialogDone(DialogInterface dialog, int id, int which) {
                            switch (which) {
                                case -1:
/* 486 */                           if (StartActivity.access$400(StartActivity.this) != null) {
/* 487 */                               StartActivity.access$500(StartActivity.this, StartActivity.access$400(StartActivity.this), true);
                                    }
                                    break;
                            }
                        }
                    }
                }
            }
