            package co.vine.android;

            import android.accounts.Account;
            import android.accounts.AccountManager;
            import android.app.Activity;
            import android.app.ProgressDialog;
            import android.content.Intent;
            import android.graphics.Bitmap;
            import android.os.Bundle;
            import android.text.TextUtils;
            import android.view.LayoutInflater;
            import android.view.Menu;
            import android.view.MenuInflater;
            import android.view.MenuItem;
            import android.view.View;
            import android.view.ViewGroup;
            import android.view.inputmethod.InputMethodManager;
            import android.widget.EditText;
            import android.widget.ImageView;
            import co.vine.android.cache.image.ImageKey;
            import co.vine.android.cache.image.UrlImage;
            import co.vine.android.client.AppController;
            import co.vine.android.client.AppSessionListener;
            import co.vine.android.client.TwitterVineApp;
            import co.vine.android.client.VineAccountHelper;
            import co.vine.android.drawable.RecyclableBitmapDrawable;
            import co.vine.android.service.PendingAction;
            import co.vine.android.util.CrashUtil;
            import co.vine.android.util.Util;
            import com.edisonwang.android.slog.SLog;
            import com.facebook.Session;
            import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
            import com.twitter.android.sdk.Twitter;
            import java.util.HashMap;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 40 */    public class ReshareFragment extends BaseControllerFragment {
                public static final String ARG_DESCRIPTION = "description";
                public static final String ARG_NETWORK = "network";
                public static final String ARG_POST_ID = "post_id";
                public static final String ARG_SHARE_URL = "share_url";
                public static final String ARG_THUMBNAIL_URL = "thumbnail_url";
                public static final String FRAGMENT_TAG = "reshare";
                private static final int MAX_CHARS_TWITTER = 140;
                public static final String NETWORK_FACEBOOK = "facebook";
                public static final String NETWORK_TWITTER = "twitter";
                private CaptchaRequestHelper mCaptchaRequestHelper = new C02731();
                private EditText mCaption;
                private String mDescription;
                private String mNetwork;
                private long mPostId;
                private ProgressDialog mProgress;
                private ProgressDialog mShareProgress;
                private String mShareUrl;
                private ImageKey mThumbnailKey;
                private String mThumbnailUrl;
                private ImageView mThumbnailView;
                private Twitter mTwitter;

/* 41 */        static /* synthetic */ ProgressDialog access$000(ReshareFragment x0) {
/* 42 */            return x0.mShareProgress;
                }

/* 41 */        static /* synthetic */ String access$100(ReshareFragment x0) {
/* 42 */            return x0.mNetwork;
                }

/* 41 */        static /* synthetic */ ProgressDialog access$200(ReshareFragment x0) {
/* 42 */            return x0.mProgress;
                }

/* 41 */        static /* synthetic */ ImageKey access$300(ReshareFragment x0) {
/* 42 */            return x0.mThumbnailKey;
                }

/* 41 */        static /* synthetic */ void access$400(ReshareFragment x0, Bitmap x1) {
/* 42 */            x0.setThumbnailImage(x1);
                }

/* 41 */        static /* synthetic */ CaptchaRequestHelper access$500(ReshareFragment x0) {
/* 42 */            return x0.mCaptchaRequestHelper;
                }

                /* JADX INFO: renamed from: co.vine.android.ReshareFragment$1 */
/* 68 */        class C02731 extends CaptchaRequestHelper {
/* 69 */            C02731() {
                    }

                    @Override // co.vine.android.CaptchaRequestHelper
/* 73 */            protected void handleRetryCaptchaRequest(PendingCaptchaRequest pendingCaptchaRequest) {
/* 74 */                ReshareFragment.this.mAppController.retryRequest(this.mPendingCaptchaRequest.actionCode, this.mPendingCaptchaRequest.bundle);
                    }

                    @Override // co.vine.android.CaptchaRequestHelper
/* 79 */            protected void handleFailedCaptchaRequest(PendingCaptchaRequest pendingCaptchaRequest) {
/* 80 */                String newRequestToFail = ReshareFragment.this.mAppController.generateReqIdForCanceledCaptcha();
/* 81 */                ReshareFragment.this.mAppController.failRequest(newRequestToFail, this.mPendingCaptchaRequest.actionCode, this.mPendingCaptchaRequest.bundle);
                    }
                }

                @Override // co.vine.android.BaseControllerFragment, android.support.v4.app.Fragment
/* 87 */        public void onCreate(Bundle savedInstanceState) {
/* 88 */            super.onCreate(savedInstanceState);
/* 89 */            this.mAppSessionListener = new ReshareListener();
/* 91 */            Bundle args = getArguments();
/* 92 */            this.mPostId = args.getLong("post_id");
/* 93 */            this.mDescription = args.getString("description");
/* 94 */            this.mNetwork = args.getString("network");
/* 95 */            this.mShareUrl = args.getString("share_url");
/* 96 */            if (this.mShareUrl == null) {
/* 97 */                this.mShareUrl = "";
                    }
/* 99 */            this.mThumbnailUrl = args.getString("thumbnail_url");
/* 100 */           if (this.mThumbnailUrl == null) {
/* 101 */               this.mThumbnailUrl = "";
                    }
/* 103 */           this.mTwitter = new Twitter(TwitterVineApp.API_KEY, TwitterVineApp.API_SECRET);
/* 105 */           if (savedInstanceState != null) {
/* 106 */               this.mCaptchaRequestHelper.restorePendingCaptchaRequest(savedInstanceState);
                    }
/* 109 */           setHasOptionsMenu(true);
                }

/* 112 */       private boolean validateSocialConnect() {
/* 113 */           if ("facebook".equals(this.mNetwork)) {
/* 114 */               return this.mAppController.isConnectedToFacebook(getActivity());
                    }
/* 115 */           if ("twitter".equals(this.mNetwork)) {
/* 116 */               return Util.getDefaultSharedPrefs(getActivity()).getBoolean(Settings.PREF_TWITTER_CONNECTED, false);
                    }
                    return false;
                }

                @Override // co.vine.android.BaseControllerFragment, co.vine.android.BaseFragment, android.support.v4.app.Fragment
/* 122 */       public void onResume() {
/* 123 */           super.onResume();
/* 124 */           if (this.mCaption != null) {
/* 125 */               Util.setSoftKeyboardVisibility(getActivity(), this.mCaption, true);
                    }
/* 127 */           this.mCaptchaRequestHelper.handlePendingCaptchaRequest();
                }

                @Override // android.support.v4.app.Fragment
/* 131 */       public void onActivityCreated(Bundle savedInstanceState) {
/* 132 */           super.onActivityCreated(savedInstanceState);
/* 135 */           if ("facebook".equals(this.mNetwork)) {
/* 136 */               Session session = this.mAppController.getValidFacebookSession(getActivity(), false);
/* 137 */               if (session == null) {
/* 138 */                   this.mAppController.connectToFacebookInitialize(this);
                        } else if (this.mAppController.getPendingFacebookToken() != null) {
/* 141 */                   this.mAppController.sendFacebookToken();
                        }
                    } else if ("twitter".equals(this.mNetwork)) {
/* 145 */               Activity activity = getActivity();
/* 146 */               Account ac = VineAccountHelper.getAccount(activity, this.mAppController.getActiveSession().getLoginEmail());
/* 148 */               AccountManager am = AccountManager.get(activity);
/* 149 */               if (ac == null || am == null) {
/* 150 */                   CrashUtil.logException(new VineLoggingException("Account or AM is null: " + ac + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + am));
                        } else {
/* 152 */                   String token = am.getUserData(ac, VineAccountHelper.KEY_TWITTER_TOKEN);
/* 153 */                   if (TextUtils.isEmpty(token)) {
/* 154 */                       AppController.startTwitterAuthWithFinish(this.mTwitter, activity);
                            }
                        }
                    }
/* 160 */           this.mCaption.setLines(8);
/* 161 */           this.mCaption.setHorizontallyScrolling(false);
/* 163 */           if (!TextUtils.isEmpty(this.mThumbnailUrl)) {
/* 164 */               this.mThumbnailKey = new ImageKey(this.mThumbnailUrl);
/* 165 */               setThumbnailImage(this.mAppController.getPhotoBitmap(this.mThumbnailKey));
                    }
/* 167 */           if (!TextUtils.isEmpty(this.mDescription)) {
/* 168 */               this.mCaption.setText(this.mDescription);
                    }
                }

                @Override // android.support.v4.app.Fragment
/* 174 */       public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
/* 175 */           View v = inflater.inflate(C0257R.layout.reshare, container, false);
/* 176 */           this.mCaption = (EditText) v.findViewById(C0257R.id.caption);
/* 177 */           this.mThumbnailView = (ImageView) v.findViewById(C0257R.id.thumbnail);
/* 178 */           return v;
                }

                @Override // android.support.v4.app.Fragment
/* 182 */       public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
/* 183 */           if (!isDetached()) {
/* 184 */               inflater.inflate(C0257R.menu.reshare, menu);
/* 185 */               MenuItem menuItem = menu.findItem(C0257R.id.done);
                        try {
/* 187 */                   if ("facebook".equals(this.mNetwork)) {
/* 188 */                       menuItem.setTitle(getString(C0257R.string.share_action_facebook).toUpperCase());
                            } else if ("twitter".equals(this.mNetwork)) {
/* 190 */                       menuItem.setTitle(getString(C0257R.string.share_action_twitter).toUpperCase());
                            }
/* 192 */                   super.onCreateOptionsMenu(menu, inflater);
                        } catch (IllegalStateException e) {
                        }
                    }
                }

                @Override // android.support.v4.app.Fragment
/* 201 */       public boolean onOptionsItemSelected(MenuItem item) {
/* 202 */           if (item.getItemId() == 2131427939) {
/* 204 */               int characterLimit = (140 - this.mShareUrl.length()) - 1;
/* 205 */               if ("twitter".equals(this.mNetwork) && this.mCaption.getText().length() > characterLimit) {
/* 206 */                   Util.showCenteredToast(getActivity(), getString(C0257R.string.reshare_tweet_length_exceeded, Integer.valueOf(characterLimit)));
                            return true;
                        }
/* 208 */               if (validateSocialConnect()) {
/* 209 */                   InputMethodManager imm = (InputMethodManager) getActivity().getSystemService("input_method");
/* 211 */                   imm.hideSoftInputFromWindow(this.mCaption.getWindowToken(), 0);
/* 213 */                   String message = this.mCaption.getText().toString();
/* 214 */                   this.mAppController.sharePost(this.mNetwork, message, this.mPostId);
/* 215 */                   ProgressDialog d = new ProgressDialog(getActivity(), C0257R.style.ProgressDialogTheme);
/* 217 */                   d.setMessage(getString(C0257R.string.sharing_post));
/* 218 */                   d.setProgress(0);
/* 219 */                   d.show();
/* 220 */                   this.mShareProgress = d;
                            return true;
                        }
/* 222 */               String properNetwork = this.mNetwork.substring(0, 1).toUpperCase() + this.mNetwork.substring(1);
/* 223 */               Util.showCenteredToast(getActivity(), getString(C0257R.string.share_failed_invalid_session, properNetwork));
/* 225 */               getActivity().finish();
                        return true;
                    }
/* 229 */           return super.onOptionsItemSelected(item);
                }

                @Override // android.support.v4.app.Fragment
/* 234 */       public void onActivityResult(int requestCode, int resultCode, Intent data) {
/* 235 */           if (resultCode == 0) {
/* 236 */               getActivity().finish();
                    }
/* 239 */           if (this.mCaptchaRequestHelper.onActivityResult(requestCode, resultCode, data)) {
/* 240 */               if (this.mShareProgress != null && this.mShareProgress.isShowing()) {
/* 241 */                   this.mShareProgress.hide();
/* 307 */                   return;
                        }
/* 307 */               return;
                    }
/* 246 */           if (this.mProgress == null || !this.mProgress.isShowing()) {
/* 247 */               ProgressDialog d = new ProgressDialog(getActivity(), C0257R.style.ProgressDialogTheme);
/* 249 */               d.setMessage(getString(C0257R.string.login_logging_in));
/* 250 */               d.setProgress(0);
/* 251 */               d.show();
/* 252 */               this.mProgress = d;
                    }
                    switch (requestCode) {
                        case 1:
/* 257 */                   if (resultCode == -1) {
/* 258 */                       String username = data.getStringExtra("screen_name");
/* 259 */                       String token = data.getStringExtra(Twitter.EXTRA_TOKEN);
/* 260 */                       String secret = data.getStringExtra(Twitter.EXTRA_TOKEN_SECRET);
/* 261 */                       long userId = data.getLongExtra("user_id", 0L);
/* 262 */                       this.mAppController.connectTwitter(this.mAppController.getActiveSession(), username, token, secret, userId);
                            }
                            break;
                        case 2:
/* 268 */                   if (resultCode == -1) {
/* 269 */                       String username2 = data.getStringExtra("screen_name");
/* 271 */                       String token2 = data.getStringExtra("token");
/* 272 */                       String secret2 = data.getStringExtra("secret");
/* 273 */                       long userId2 = data.getLongExtra("user_id", 0L);
/* 274 */                       this.mAppController.connectTwitter(this.mAppController.getActiveSession(), username2, token2, secret2, userId2);
                            }
                            break;
                        default:
/* 280 */                   SLog.m447d("Facebook auth came back: {}", Integer.valueOf(requestCode));
/* 281 */                   if (resultCode == -1) {
/* 282 */                       Session session = Session.getActiveSession();
/* 283 */                       if (session != null) {
/* 284 */                           if (session.onActivityResult(getActivity(), requestCode, resultCode, data)) {
/* 286 */                               if (session.getPermissions().contains(AppController.PUBLISH_ACTIONS)) {
/* 287 */                                   AppController.setPendingFacebookToken(getActivity(), session.getAccessToken());
/* 289 */                                   this.mAppController.sendFacebookToken();
                                        } else {
/* 291 */                                   getActivity().runOnUiThread(new RunnableC02742());
/* 297 */                                   this.mAppController.connectToFacebookPublish(this);
                                        }
                                    }
                                } else {
/* 301 */                           super.onActivityResult(requestCode, resultCode, data);
                                }
                            }
                            break;
                    }
                }

                /* JADX INFO: renamed from: co.vine.android.ReshareFragment$2 */
/* 289 */       class RunnableC02742 implements Runnable {
/* 290 */           RunnableC02742() {
                    }

                    @Override // java.lang.Runnable
/* 293 */           public void run() {
/* 294 */               Util.showCenteredToast(ReshareFragment.this.getActivity(), C0257R.string.publish_to_facebook_toast);
                    }
                }

/* 309 */       private void setThumbnailImage(Bitmap bitmap) {
/* 310 */           if (bitmap != null) {
/* 311 */               this.mThumbnailView.setImageDrawable(new RecyclableBitmapDrawable(this.mThumbnailView.getResources(), bitmap));
                    } else {
/* 313 */               this.mThumbnailView.setImageBitmap(null);
/* 314 */               this.mThumbnailView.setBackgroundColor(getResources().getColor(C0257R.color.solid_light_gray));
                    }
                }

/* 316 */       class ReshareListener extends AppSessionListener {
/* 317 */           ReshareListener() {
                    }

                    @Override // co.vine.android.client.AppSessionListener
/* 321 */           public void onSharePostComplete(String reqId, int statusCode, String reasonPhrase) {
/* 322 */               if (ReshareFragment.access$000(ReshareFragment.this) != null) {
/* 323 */                   ReshareFragment.access$000(ReshareFragment.this).dismiss();
                        }
/* 326 */               if (statusCode != 200) {
/* 327 */                   if (!TextUtils.isEmpty(reasonPhrase)) {
/* 328 */                       Util.showCenteredToast(ReshareFragment.this.getActivity(), ReshareFragment.this.getString(C0257R.string.error_share_post, reasonPhrase));
                            }
                        } else {
/* 331 */                   if ("facebook".equals(ReshareFragment.access$100(ReshareFragment.this))) {
/* 332 */                       Util.showCenteredToast(ReshareFragment.this.getActivity(), C0257R.string.share_facebook_success);
                            } else if ("twitter".equals(ReshareFragment.access$100(ReshareFragment.this))) {
/* 334 */                       Util.showCenteredToast(ReshareFragment.this.getActivity(), C0257R.string.share_twitter_success);
                            }
/* 336 */                   ReshareFragment.this.getActivity().finish();
                        }
                    }

                    @Override // co.vine.android.client.AppSessionListener
/* 341 */           public void onConnectFacebookComplete(String reqId, int statusCode, String reasonPhrase) {
/* 342 */               if (ReshareFragment.access$200(ReshareFragment.this) != null) {
/* 343 */                   ReshareFragment.access$200(ReshareFragment.this).dismiss();
                        }
/* 346 */               if (statusCode != 200) {
/* 347 */                   Util.showCenteredToast(ReshareFragment.this.getActivity(), C0257R.string.error_facebook_send_token);
/* 348 */                   ReshareFragment.this.getActivity().finish();
                        }
                    }

                    @Override // co.vine.android.client.AppSessionListener
/* 355 */           public void onConnectTwitterComplete(String reqId, int statusCode, String reasonPhrase, String username, String token, String secret, long userId) {
/* 356 */               if (ReshareFragment.access$200(ReshareFragment.this) != null) {
/* 357 */                   ReshareFragment.access$200(ReshareFragment.this).dismiss();
                        }
/* 360 */               if (statusCode != 200) {
/* 370 */                   Util.showCenteredToast(ReshareFragment.this.getActivity(), reasonPhrase);
/* 371 */                   ReshareFragment.this.getActivity().finish();
                        } else if (userId > 0 && !TextUtils.isEmpty(token) && !TextUtils.isEmpty(secret)) {
/* 362 */                   VineAccountHelper.saveTwitterInfo(ReshareFragment.this.getActivity(), ReshareFragment.this.mAppController.getActiveSession().getLoginEmail(), username, token, secret, userId);
/* 365 */                   Util.getDefaultSharedPrefs(ReshareFragment.this.getActivity()).edit().putBoolean(Settings.PREF_TWITTER_CONNECTED, true).apply();
                        }
                    }

                    @Override // co.vine.android.client.AppSessionListener
/* 376 */           public void onPhotoImageLoaded(HashMap<ImageKey, UrlImage> images) {
/* 377 */               UrlImage image = images.get(ReshareFragment.access$300(ReshareFragment.this));
/* 378 */               if (image != null && image.isValid()) {
/* 379 */                   ReshareFragment.access$400(ReshareFragment.this, image.bitmap);
                        }
                    }

                    @Override // co.vine.android.client.AppSessionListener
/* 384 */           public void onCaptchaRequired(String reqId, String captchaUrl, PendingAction action) {
/* 385 */               ReshareFragment.access$500(ReshareFragment.this).onCaptchaRequired(ReshareFragment.this.getActivity(), reqId, action.actionCode, action.bundle, captchaUrl);
                    }
                }

                @Override // android.support.v4.app.Fragment
/* 390 */       public void onSaveInstanceState(Bundle outState) {
/* 391 */           super.onSaveInstanceState(outState);
/* 392 */           this.mCaptchaRequestHelper.onSaveInstanceState(outState);
                }
            }
