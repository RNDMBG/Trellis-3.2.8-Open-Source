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
            
            
    public class ReshareFragment extends BaseControllerFragment {
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

        static  ProgressDialog access$000(ReshareFragment x0) {
            return x0.mShareProgress;
                }

        static  String access$100(ReshareFragment x0) {
            return x0.mNetwork;
                }

        static  ProgressDialog access$200(ReshareFragment x0) {
            return x0.mProgress;
                }

        static  ImageKey access$300(ReshareFragment x0) {
            return x0.mThumbnailKey;
                }

        static  void access$400(ReshareFragment x0, Bitmap x1) {
            x0.setThumbnailImage(x1);
                }

        static  CaptchaRequestHelper access$500(ReshareFragment x0) {
            return x0.mCaptchaRequestHelper;
                }

                
        class C02731 extends CaptchaRequestHelper {
            C02731() {
                    }

                    @Override 
            protected void handleRetryCaptchaRequest(PendingCaptchaRequest pendingCaptchaRequest) {
                ReshareFragment.this.mAppController.retryRequest(this.mPendingCaptchaRequest.actionCode, this.mPendingCaptchaRequest.bundle);
                    }

                    @Override 
            protected void handleFailedCaptchaRequest(PendingCaptchaRequest pendingCaptchaRequest) {
                String newRequestToFail = ReshareFragment.this.mAppController.generateReqIdForCanceledCaptcha();
                ReshareFragment.this.mAppController.failRequest(newRequestToFail, this.mPendingCaptchaRequest.actionCode, this.mPendingCaptchaRequest.bundle);
                    }
                }

                @Override 
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            this.mAppSessionListener = new ReshareListener();
            Bundle args = getArguments();
            this.mPostId = args.getLong("post_id");
            this.mDescription = args.getString("description");
            this.mNetwork = args.getString("network");
            this.mShareUrl = args.getString("share_url");
            if (this.mShareUrl == null) {
                this.mShareUrl = "";
                    }
            this.mThumbnailUrl = args.getString("thumbnail_url");
           if (this.mThumbnailUrl == null) {
               this.mThumbnailUrl = "";
                    }
           this.mTwitter = new Twitter(TwitterVineApp.API_KEY, TwitterVineApp.API_SECRET);
           if (savedInstanceState != null) {
               this.mCaptchaRequestHelper.restorePendingCaptchaRequest(savedInstanceState);
                    }
           setHasOptionsMenu(true);
                }

       private boolean validateSocialConnect() {
           if ("facebook".equals(this.mNetwork)) {
               return this.mAppController.isConnectedToFacebook(getActivity());
                    }
           if ("twitter".equals(this.mNetwork)) {
               return Util.getDefaultSharedPrefs(getActivity()).getBoolean(Settings.PREF_TWITTER_CONNECTED, false);
                    }
                    return false;
                }

                @Override 
       public void onResume() {
           super.onResume();
           if (this.mCaption != null) {
               Util.setSoftKeyboardVisibility(getActivity(), this.mCaption, true);
                    }
           this.mCaptchaRequestHelper.handlePendingCaptchaRequest();
                }

                @Override 
       public void onActivityCreated(Bundle savedInstanceState) {
           super.onActivityCreated(savedInstanceState);
           if ("facebook".equals(this.mNetwork)) {
               Session session = this.mAppController.getValidFacebookSession(getActivity(), false);
               if (session == null) {
                   this.mAppController.connectToFacebookInitialize(this);
                        } else if (this.mAppController.getPendingFacebookToken() != null) {
                   this.mAppController.sendFacebookToken();
                        }
                    } else if ("twitter".equals(this.mNetwork)) {
               Activity activity = getActivity();
               Account ac = VineAccountHelper.getAccount(activity, this.mAppController.getActiveSession().getLoginEmail());
               AccountManager am = AccountManager.get(activity);
               if (ac == null || am == null) {
                   CrashUtil.logException(new VineLoggingException("Account or AM is null: " + ac + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + am));
                        } else {
                   String token = am.getUserData(ac, VineAccountHelper.KEY_TWITTER_TOKEN);
                   if (TextUtils.isEmpty(token)) {
                       AppController.startTwitterAuthWithFinish(this.mTwitter, activity);
                            }
                        }
                    }
           this.mCaption.setLines(8);
           this.mCaption.setHorizontallyScrolling(false);
           if (!TextUtils.isEmpty(this.mThumbnailUrl)) {
               this.mThumbnailKey = new ImageKey(this.mThumbnailUrl);
               setThumbnailImage(this.mAppController.getPhotoBitmap(this.mThumbnailKey));
                    }
           if (!TextUtils.isEmpty(this.mDescription)) {
               this.mCaption.setText(this.mDescription);
                    }
                }

                @Override 
       public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
           View v = inflater.inflate(R.layout.reshare, container, false);
           this.mCaption = (EditText) v.findViewById(R.id.caption);
           this.mThumbnailView = (ImageView) v.findViewById(R.id.thumbnail);
           return v;
                }

                @Override 
       public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
           if (!isDetached()) {
               inflater.inflate(R.menu.reshare, menu);
               MenuItem menuItem = menu.findItem(R.id.done);
                        try {
                   if ("facebook".equals(this.mNetwork)) {
                       menuItem.setTitle(getString(R.string.share_action_facebook).toUpperCase());
                            } else if ("twitter".equals(this.mNetwork)) {
                       menuItem.setTitle(getString(R.string.share_action_twitter).toUpperCase());
                            }
                   super.onCreateOptionsMenu(menu, inflater);
                        } catch (IllegalStateException e) {
                        }
                    }
                }

                @Override 
       public boolean onOptionsItemSelected(MenuItem item) {
           if (item.getItemId() == 2131427939) {
               int characterLimit = (140 - this.mShareUrl.length()) - 1;
               if ("twitter".equals(this.mNetwork) && this.mCaption.getText().length() > characterLimit) {
                   Util.showCenteredToast(getActivity(), getString(R.string.reshare_tweet_length_exceeded, Integer.valueOf(characterLimit)));
                            return true;
                        }
               if (validateSocialConnect()) {
                   InputMethodManager imm = (InputMethodManager) getActivity().getSystemService("input_method");
                   imm.hideSoftInputFromWindow(this.mCaption.getWindowToken(), 0);
                   String message = this.mCaption.getText().toString();
                   this.mAppController.sharePost(this.mNetwork, message, this.mPostId);
                   ProgressDialog d = new ProgressDialog(getActivity(), R.style.ProgressDialogTheme);
                   d.setMessage(getString(R.string.sharing_post));
                   d.setProgress(0);
                   d.show();
                   this.mShareProgress = d;
                            return true;
                        }
               String properNetwork = this.mNetwork.substring(0, 1).toUpperCase() + this.mNetwork.substring(1);
               Util.showCenteredToast(getActivity(), getString(R.string.share_failed_invalid_session, properNetwork));
               getActivity().finish();
                        return true;
                    }
           return super.onOptionsItemSelected(item);
                }

                @Override 
       public void onActivityResult(int requestCode, int resultCode, Intent data) {
           if (resultCode == 0) {
               getActivity().finish();
                    }
           if (this.mCaptchaRequestHelper.onActivityResult(requestCode, resultCode, data)) {
               if (this.mShareProgress != null && this.mShareProgress.isShowing()) {
                   this.mShareProgress.hide();
                   return;
                        }
               return;
                    }
           if (this.mProgress == null || !this.mProgress.isShowing()) {
               ProgressDialog d = new ProgressDialog(getActivity(), R.style.ProgressDialogTheme);
               d.setMessage(getString(R.string.login_logging_in));
               d.setProgress(0);
               d.show();
               this.mProgress = d;
                    }
                    switch (requestCode) {
                        case 1:
                   if (resultCode == -1) {
                       String username = data.getStringExtra("screen_name");
                       String token = data.getStringExtra(Twitter.EXTRA_TOKEN);
                       String secret = data.getStringExtra(Twitter.EXTRA_TOKEN_SECRET);
                       long userId = data.getLongExtra("user_id", 0L);
                       this.mAppController.connectTwitter(this.mAppController.getActiveSession(), username, token, secret, userId);
                            }
                            break;
                        case 2:
                   if (resultCode == -1) {
                       String username2 = data.getStringExtra("screen_name");
                       String token2 = data.getStringExtra("token");
                       String secret2 = data.getStringExtra("secret");
                       long userId2 = data.getLongExtra("user_id", 0L);
                       this.mAppController.connectTwitter(this.mAppController.getActiveSession(), username2, token2, secret2, userId2);
                            }
                            break;
                        default:
                   SLog.m447d("Facebook auth came back: {}", Integer.valueOf(requestCode));
                   if (resultCode == -1) {
                       Session session = Session.getActiveSession();
                       if (session != null) {
                           if (session.onActivityResult(getActivity(), requestCode, resultCode, data)) {
                               if (session.getPermissions().contains(AppController.PUBLISH_ACTIONS)) {
                                   AppController.setPendingFacebookToken(getActivity(), session.getAccessToken());
                                   this.mAppController.sendFacebookToken();
                                        } else {
                                   getActivity().runOnUiThread(new RunnableC02742());
                                   this.mAppController.connectToFacebookPublish(this);
                                        }
                                    }
                                } else {
                           super.onActivityResult(requestCode, resultCode, data);
                                }
                            }
                            break;
                    }
                }

                
       class RunnableC02742 implements Runnable {
           RunnableC02742() {
                    }

                    @Override 
           public void run() {
               Util.showCenteredToast(ReshareFragment.this.getActivity(), R.string.publish_to_facebook_toast);
                    }
                }

       private void setThumbnailImage(Bitmap bitmap) {
           if (bitmap != null) {
               this.mThumbnailView.setImageDrawable(new RecyclableBitmapDrawable(this.mThumbnailView.getResources(), bitmap));
                    } else {
               this.mThumbnailView.setImageBitmap(null);
               this.mThumbnailView.setBackgroundColor(getResources().getColor(R.color.solid_light_gray));
                    }
                }

       class ReshareListener extends AppSessionListener {
           ReshareListener() {
                    }

                    @Override 
           public void onSharePostComplete(String reqId, int statusCode, String reasonPhrase) {
               if (ReshareFragment.access$000(ReshareFragment.this) != null) {
                   ReshareFragment.access$000(ReshareFragment.this).dismiss();
                        }
               if (statusCode != 200) {
                   if (!TextUtils.isEmpty(reasonPhrase)) {
                       Util.showCenteredToast(ReshareFragment.this.getActivity(), ReshareFragment.this.getString(R.string.error_share_post, reasonPhrase));
                            }
                        } else {
                   if ("facebook".equals(ReshareFragment.access$100(ReshareFragment.this))) {
                       Util.showCenteredToast(ReshareFragment.this.getActivity(), R.string.share_facebook_success);
                            } else if ("twitter".equals(ReshareFragment.access$100(ReshareFragment.this))) {
                       Util.showCenteredToast(ReshareFragment.this.getActivity(), R.string.share_twitter_success);
                            }
                   ReshareFragment.this.getActivity().finish();
                        }
                    }

                    @Override 
           public void onConnectFacebookComplete(String reqId, int statusCode, String reasonPhrase) {
               if (ReshareFragment.access$200(ReshareFragment.this) != null) {
                   ReshareFragment.access$200(ReshareFragment.this).dismiss();
                        }
               if (statusCode != 200) {
                   Util.showCenteredToast(ReshareFragment.this.getActivity(), R.string.error_facebook_send_token);
                   ReshareFragment.this.getActivity().finish();
                        }
                    }

                    @Override 
           public void onConnectTwitterComplete(String reqId, int statusCode, String reasonPhrase, String username, String token, String secret, long userId) {
               if (ReshareFragment.access$200(ReshareFragment.this) != null) {
                   ReshareFragment.access$200(ReshareFragment.this).dismiss();
                        }
               if (statusCode != 200) {
                   Util.showCenteredToast(ReshareFragment.this.getActivity(), reasonPhrase);
                   ReshareFragment.this.getActivity().finish();
                        } else if (userId > 0 && !TextUtils.isEmpty(token) && !TextUtils.isEmpty(secret)) {
                   VineAccountHelper.saveTwitterInfo(ReshareFragment.this.getActivity(), ReshareFragment.this.mAppController.getActiveSession().getLoginEmail(), username, token, secret, userId);
                   Util.getDefaultSharedPrefs(ReshareFragment.this.getActivity()).edit().putBoolean(Settings.PREF_TWITTER_CONNECTED, true).apply();
                        }
                    }

                    @Override 
           public void onPhotoImageLoaded(HashMap<ImageKey, UrlImage> images) {
               UrlImage image = images.get(ReshareFragment.access$300(ReshareFragment.this));
               if (image != null && image.isValid()) {
                   ReshareFragment.access$400(ReshareFragment.this, image.bitmap);
                        }
                    }

                    @Override 
           public void onCaptchaRequired(String reqId, String captchaUrl, PendingAction action) {
               ReshareFragment.access$500(ReshareFragment.this).onCaptchaRequired(ReshareFragment.this.getActivity(), reqId, action.actionCode, action.bundle, captchaUrl);
                    }
                }

                @Override 
       public void onSaveInstanceState(Bundle outState) {
           super.onSaveInstanceState(outState);
           this.mCaptchaRequestHelper.onSaveInstanceState(outState);
                }
            }
