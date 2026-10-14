            package com.facebook.widget;

            import android.annotation.SuppressLint;
            import android.app.Dialog;
            import android.app.ProgressDialog;
            import android.content.Context;
            import android.content.DialogInterface;
            import android.content.Intent;
            import android.graphics.Bitmap;
            import android.graphics.drawable.Drawable;
            import android.net.Uri;
            import android.net.http.SslError;
            import android.os.Bundle;
            import android.view.View;
            import android.view.ViewGroup;
            import android.webkit.SslErrorHandler;
            import android.webkit.WebView;
            import android.webkit.WebViewClient;
            import android.widget.FrameLayout;
            import android.widget.ImageView;
            import android.widget.LinearLayout;
            import co.vine.android.C0257R;
            import com.facebook.FacebookDialogException;
            import com.facebook.FacebookException;
            import com.facebook.FacebookOperationCanceledException;
            import com.facebook.FacebookRequestError;
            import com.facebook.FacebookServiceException;
            import com.facebook.Session;
            import com.facebook.internal.ServerProtocol;
            import com.facebook.internal.Utility;
            import com.facebook.internal.Validate;
            import com.vandalsoftware.p004io.IoUtils;
            import java.io.UnsupportedEncodingException;
            import java.net.MalformedURLException;
            import java.net.URL;
            import java.net.URLDecoder;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 57 */    public class WebDialog extends Dialog {
                static final String CANCEL_URI = "fbconnect://cancel";
                public static final int DEFAULT_THEME = 16973840;
                static final boolean DISABLE_SSL_CHECK_FOR_TESTING = false;
                private static final String DISPLAY_TOUCH = "touch";
                private static final String LOG_TAG = "FacebookSDK.WebDialog";
                static final String REDIRECT_URI = "fbconnect://success";
                private static int STRING_FACEBOOK_LOADING = -1;
                private static final String USER_AGENT = "user_agent";
                private FrameLayout contentFrameLayout;
                private ImageView crossImageView;
                private boolean isDetached;
                private boolean listenerCalled;
                private OnCompleteListener onCompleteListener;
                private ProgressDialog spinner;
                private String url;
                private WebView webView;

                public interface OnCompleteListener {
                    void onComplete(Bundle bundle, FacebookException facebookException);
                }

/* 58 */        static /* synthetic */ void access$000(WebDialog x0) {
/* 59 */            x0.sendCancelToListener();
                }

/* 58 */        static /* synthetic */ void access$200(WebDialog x0, Bundle x1) {
/* 59 */            x0.sendSuccessToListener(x1);
                }

/* 58 */        static /* synthetic */ void access$300(WebDialog x0, Throwable x1) {
/* 59 */            x0.sendErrorToListener(x1);
                }

/* 58 */        static /* synthetic */ boolean access$400(WebDialog x0) {
/* 59 */            return x0.isDetached;
                }

/* 58 */        static /* synthetic */ ProgressDialog access$500(WebDialog x0) {
/* 59 */            return x0.spinner;
                }

/* 58 */        static /* synthetic */ FrameLayout access$600(WebDialog x0) {
/* 59 */            return x0.contentFrameLayout;
                }

/* 58 */        static /* synthetic */ WebView access$700(WebDialog x0) {
/* 59 */            return x0.webView;
                }

/* 58 */        static /* synthetic */ ImageView access$800(WebDialog x0) {
/* 59 */            return x0.crossImageView;
                }

/* 80 */        public static void initLoadingString(int loadingStringRes) {
/* 81 */            STRING_FACEBOOK_LOADING = loadingStringRes;
                }

/* 106 */       public WebDialog(Context context, String url) {
/* 107 */           this(context, url, 16973840);
                }

/* 118 */       public WebDialog(Context context, String url, int theme) {
/* 119 */           super(context, theme);
/* 75 */            this.listenerCalled = false;
/* 76 */            this.isDetached = false;
/* 120 */           this.url = url;
                }

/* 132 */       public WebDialog(Context context, String action, Bundle parameters, int theme, OnCompleteListener listener) {
/* 133 */           super(context, theme);
/* 75 */            this.listenerCalled = false;
/* 76 */            this.isDetached = false;
/* 136 */           parameters = parameters == null ? new Bundle() : parameters;
/* 138 */           parameters.putString("display", DISPLAY_TOUCH);
/* 139 */           parameters.putString("type", USER_AGENT);
/* 141 */           Uri uri = Utility.buildUri(ServerProtocol.DIALOG_AUTHORITY, "v2.3/dialog/" + action, parameters);
/* 143 */           this.url = uri.toString();
/* 144 */           this.onCompleteListener = listener;
                }

/* 152 */       public void setOnCompleteListener(OnCompleteListener listener) {
/* 153 */           this.onCompleteListener = listener;
                }

/* 161 */       public OnCompleteListener getOnCompleteListener() {
/* 162 */           return this.onCompleteListener;
                }

                @Override // android.app.Dialog, android.content.DialogInterface
/* 166 */       public void dismiss() {
/* 167 */           if (this.webView != null) {
/* 168 */               this.webView.stopLoading();
                    }
/* 170 */           if (!this.isDetached) {
/* 171 */               if (this.spinner.isShowing()) {
/* 172 */                   this.spinner.dismiss();
                        }
/* 174 */               super.dismiss();
                    }
                }

                @Override // android.app.Dialog, android.view.Window.Callback
/* 179 */       public void onDetachedFromWindow() {
/* 180 */           this.isDetached = true;
/* 181 */           super.onDetachedFromWindow();
                }

                @Override // android.app.Dialog, android.view.Window.Callback
/* 185 */       public void onAttachedToWindow() {
/* 186 */           this.isDetached = false;
/* 187 */           super.onAttachedToWindow();
                }

                @Override // android.app.Dialog
/* 191 */       protected void onCreate(Bundle savedInstanceState) {
/* 192 */           super.onCreate(savedInstanceState);
/* 194 */           setOnCancelListener(new DialogInterfaceOnCancelListenerC07811());
/* 201 */           this.spinner = new ProgressDialog(getContext());
/* 202 */           this.spinner.requestWindowFeature(1);
/* 203 */           this.spinner.setMessage(getContext().getString(STRING_FACEBOOK_LOADING));
/* 204 */           this.spinner.setOnCancelListener(new DialogInterfaceOnCancelListenerC07822());
/* 212 */           requestWindowFeature(1);
/* 213 */           this.contentFrameLayout = new FrameLayout(getContext());
/* 219 */           createCrossImage();
/* 224 */           int crossWidth = this.crossImageView.getDrawable().getIntrinsicWidth();
/* 225 */           setUpWebView(crossWidth / 2);
/* 230 */           this.contentFrameLayout.addView(this.crossImageView, new ViewGroup.LayoutParams(-2, -2));
/* 232 */           addContentView(this.contentFrameLayout, new ViewGroup.LayoutParams(-1, -1));
                }

                /* JADX INFO: renamed from: com.facebook.widget.WebDialog$1 */
/* 192 */       class DialogInterfaceOnCancelListenerC07811 implements DialogInterface.OnCancelListener {
/* 193 */           DialogInterfaceOnCancelListenerC07811() {
                    }

                    @Override // android.content.DialogInterface.OnCancelListener
/* 196 */           public void onCancel(DialogInterface dialogInterface) {
/* 197 */               WebDialog.access$000(WebDialog.this);
                    }
                }

                /* JADX INFO: renamed from: com.facebook.widget.WebDialog$2 */
/* 202 */       class DialogInterfaceOnCancelListenerC07822 implements DialogInterface.OnCancelListener {
/* 203 */           DialogInterfaceOnCancelListenerC07822() {
                    }

                    @Override // android.content.DialogInterface.OnCancelListener
/* 206 */           public void onCancel(DialogInterface dialogInterface) {
/* 207 */               WebDialog.access$000(WebDialog.this);
/* 208 */               WebDialog.this.dismiss();
                    }
                }

/* 236 */       private void sendSuccessToListener(Bundle values) {
/* 237 */           if (this.onCompleteListener != null && !this.listenerCalled) {
/* 238 */               this.listenerCalled = true;
/* 239 */               this.onCompleteListener.onComplete(values, null);
                    }
                }

/* 243 */       private void sendErrorToListener(Throwable error) {
                    FacebookException facebookException;
/* 244 */           if (this.onCompleteListener != null && !this.listenerCalled) {
/* 245 */               this.listenerCalled = true;
/* 247 */               if (error instanceof FacebookException) {
/* 248 */                   facebookException = (FacebookException) error;
                        } else {
/* 250 */                   facebookException = new FacebookException(error);
                        }
/* 252 */               this.onCompleteListener.onComplete(null, facebookException);
                    }
                }

/* 256 */       private void sendCancelToListener() {
/* 257 */           sendErrorToListener(new FacebookOperationCanceledException());
                }

/* 260 */       private void createCrossImage() {
/* 261 */           this.crossImageView = new ImageView(getContext());
/* 263 */           this.crossImageView.setOnClickListener(new ViewOnClickListenerC07833());
/* 270 */           Drawable crossDrawable = getContext().getResources().getDrawable(C0257R.drawable.com_facebook_close);
/* 271 */           this.crossImageView.setImageDrawable(crossDrawable);
/* 275 */           this.crossImageView.setVisibility(4);
                }

                /* JADX INFO: renamed from: com.facebook.widget.WebDialog$3 */
/* 261 */       class ViewOnClickListenerC07833 implements View.OnClickListener {
/* 262 */           ViewOnClickListenerC07833() {
                    }

                    @Override // android.view.View.OnClickListener
/* 265 */           public void onClick(View v) {
/* 266 */               WebDialog.access$000(WebDialog.this);
/* 267 */               WebDialog.this.dismiss();
                    }
                }

                @SuppressLint({"SetJavaScriptEnabled"})
/* 279 */       private void setUpWebView(int margin) {
/* 280 */           LinearLayout webViewContainer = new LinearLayout(getContext());
/* 281 */           this.webView = new WebView(getContext());
/* 282 */           this.webView.setVerticalScrollBarEnabled(false);
/* 283 */           this.webView.setHorizontalScrollBarEnabled(false);
/* 284 */           this.webView.setWebViewClient(new DialogWebViewClient(this, null));
/* 285 */           this.webView.getSettings().setJavaScriptEnabled(true);
/* 286 */           this.webView.loadUrl(this.url);
/* 287 */           this.webView.setLayoutParams(new FrameLayout.LayoutParams(-1, -1));
/* 289 */           this.webView.setVisibility(4);
/* 290 */           this.webView.getSettings().setSavePassword(false);
/* 292 */           webViewContainer.setPadding(margin, margin, margin, margin);
/* 293 */           webViewContainer.addView(this.webView);
/* 294 */           this.contentFrameLayout.addView(webViewContainer);
                }

/* 297 */       public static Bundle decodeUrl(String s) {
/* 298 */           Bundle params = new Bundle();
/* 299 */           if (s != null) {
/* 300 */               String[] array = s.split("&");
                        for (String parameter : array) {
/* 302 */                   String[] v = parameter.split("=");
                            try {
/* 305 */                       if (v.length == 2) {
/* 306 */                           params.putString(URLDecoder.decode(v[0], IoUtils.UTF_8), URLDecoder.decode(v[1], IoUtils.UTF_8));
                                } else if (v.length == 1) {
/* 309 */                           params.putString(URLDecoder.decode(v[0], IoUtils.UTF_8), "");
                                }
                            } catch (UnsupportedEncodingException e) {
                            }
                        }
                    }
/* 316 */           return params;
                }

/* 317 */       private class DialogWebViewClient extends WebViewClient {
/* 318 */           private DialogWebViewClient() {
                    }

/* 318 */           /* synthetic */ DialogWebViewClient(WebDialog x0, DialogInterfaceOnCancelListenerC07811 x1) {
/* 319 */               this();
                    }

                    @Override // android.webkit.WebViewClient
/* 322 */           public boolean shouldOverrideUrlLoading(WebView view, String url) {
                        Bundle values;
/* 323 */               Utility.logd(WebDialog.LOG_TAG, "Redirect URL: " + url);
/* 324 */               if (url.startsWith(WebDialog.REDIRECT_URI)) {
                            try {
/* 327 */                       URL u = new URL(url.replace("fbconnect", "http"));
/* 328 */                       Bundle b = WebDialog.decodeUrl(u.getQuery());
/* 329 */                       b.putAll(WebDialog.decodeUrl(u.getRef()));
/* 330 */                       values = b;
                            } catch (MalformedURLException e) {
/* 332 */                       values = new Bundle();
                            }
/* 335 */                   String error = values.getString("error");
/* 336 */                   if (error == null) {
/* 337 */                       error = values.getString("error_type");
                            }
/* 340 */                   String errorMessage = values.getString("error_msg");
/* 341 */                   if (errorMessage == null) {
/* 342 */                       errorMessage = values.getString("error_description");
                            }
/* 344 */                   String errorCodeString = values.getString("error_code");
/* 345 */                   int errorCode = -1;
/* 346 */                   if (!Utility.isNullOrEmpty(errorCodeString)) {
                                try {
/* 348 */                           errorCode = Integer.parseInt(errorCodeString);
                                } catch (NumberFormatException e2) {
/* 350 */                           errorCode = -1;
                                }
                            }
/* 354 */                   if (Utility.isNullOrEmpty(error) && Utility.isNullOrEmpty(errorMessage) && errorCode == -1) {
/* 356 */                       WebDialog.access$200(WebDialog.this, values);
                            } else if (error != null && (error.equals("access_denied") || error.equals("OAuthAccessDeniedException"))) {
/* 359 */                       WebDialog.access$000(WebDialog.this);
                            } else {
/* 361 */                       FacebookRequestError requestError = new FacebookRequestError(errorCode, error, errorMessage);
/* 362 */                       WebDialog.access$300(WebDialog.this, new FacebookServiceException(requestError, errorMessage));
                            }
/* 365 */                   WebDialog.this.dismiss();
/* 366 */                   return true;
                        }
/* 367 */               if (url.startsWith(WebDialog.CANCEL_URI)) {
/* 368 */                   WebDialog.access$000(WebDialog.this);
/* 369 */                   WebDialog.this.dismiss();
/* 370 */                   return true;
                        }
/* 371 */               if (url.contains(WebDialog.DISPLAY_TOUCH)) {
/* 372 */                   return false;
                        }
/* 375 */               WebDialog.this.getContext().startActivity(new Intent("android.intent.action.VIEW", Uri.parse(url)));
/* 377 */               return true;
                    }

                    @Override // android.webkit.WebViewClient
/* 382 */           public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
/* 383 */               super.onReceivedError(view, errorCode, description, failingUrl);
/* 384 */               WebDialog.access$300(WebDialog.this, new FacebookDialogException(description, errorCode, failingUrl));
/* 385 */               WebDialog.this.dismiss();
                    }

                    @Override // android.webkit.WebViewClient
/* 392 */           public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
/* 393 */               super.onReceivedSslError(view, handler, error);
/* 395 */               WebDialog.access$300(WebDialog.this, new FacebookDialogException(null, -11, null));
/* 396 */               handler.cancel();
/* 397 */               WebDialog.this.dismiss();
                    }

                    @Override // android.webkit.WebViewClient
/* 402 */           public void onPageStarted(WebView view, String url, Bitmap favicon) {
/* 403 */               Utility.logd(WebDialog.LOG_TAG, "Webview loading URL: " + url);
/* 404 */               super.onPageStarted(view, url, favicon);
/* 405 */               if (!WebDialog.access$400(WebDialog.this)) {
/* 406 */                   WebDialog.access$500(WebDialog.this).show();
                        }
                    }

                    @Override // android.webkit.WebViewClient
/* 411 */           public void onPageFinished(WebView view, String url) {
/* 412 */               super.onPageFinished(view, url);
/* 413 */               if (!WebDialog.access$400(WebDialog.this)) {
/* 414 */                   WebDialog.access$500(WebDialog.this).dismiss();
                        }
/* 420 */               WebDialog.access$600(WebDialog.this).setBackgroundColor(0);
/* 421 */               WebDialog.access$700(WebDialog.this).setVisibility(0);
/* 422 */               WebDialog.access$800(WebDialog.this).setVisibility(0);
                    }
                }

                /* JADX INFO: Access modifiers changed from: private */
/* 433 */       public static class BuilderBase<CONCRETE extends BuilderBase<?>> {
                    private String action;
                    private String applicationId;
                    private Context context;
                    private OnCompleteListener listener;
                    private Bundle parameters;
                    private Session session;
                    private int theme = 16973840;

/* 434 */           protected BuilderBase(Context context, Session session, String action, Bundle parameters) {
/* 436 */               Validate.notNull(session, "session");
/* 437 */               if (!session.isOpened()) {
/* 438 */                   throw new FacebookException("Attempted to use a Session that was not open.");
                        }
/* 440 */               this.session = session;
/* 442 */               finishInit(context, action, parameters);
                    }

/* 444 */           protected BuilderBase(Context context, String applicationId, String action, Bundle parameters) {
/* 446 */               Validate.notNullOrEmpty(applicationId, "applicationId");
/* 447 */               this.applicationId = applicationId;
/* 449 */               finishInit(context, action, parameters);
                    }

                    /* JADX WARN: Multi-variable type inference failed */
/* 458 */           public CONCRETE setTheme(int theme) {
/* 459 */               this.theme = theme;
/* 461 */               return (CONCRETE) this;
                    }

                    /* JADX WARN: Multi-variable type inference failed */
/* 471 */           public CONCRETE setOnCompleteListener(OnCompleteListener listener) {
/* 472 */               this.listener = listener;
/* 474 */               return (CONCRETE) this;
                    }

/* 484 */           public WebDialog build() {
/* 485 */               if (this.session != null && this.session.isOpened()) {
/* 486 */                   this.parameters.putString(ServerProtocol.DIALOG_PARAM_APP_ID, this.session.getApplicationId());
/* 487 */                   this.parameters.putString("access_token", this.session.getAccessToken());
                        } else {
/* 489 */                   this.parameters.putString(ServerProtocol.DIALOG_PARAM_APP_ID, this.applicationId);
                        }
/* 492 */               if (!this.parameters.containsKey("redirect_uri")) {
/* 493 */                   this.parameters.putString("redirect_uri", WebDialog.REDIRECT_URI);
                        }
/* 496 */               return new WebDialog(this.context, this.action, this.parameters, this.theme, this.listener);
                    }

/* 499 */           protected String getApplicationId() {
/* 500 */               return this.applicationId;
                    }

/* 503 */           protected Context getContext() {
/* 504 */               return this.context;
                    }

/* 507 */           protected int getTheme() {
/* 508 */               return this.theme;
                    }

/* 511 */           protected Bundle getParameters() {
/* 512 */               return this.parameters;
                    }

/* 515 */           protected OnCompleteListener getListener() {
/* 516 */               return this.listener;
                    }

/* 519 */           private void finishInit(Context context, String action, Bundle parameters) {
/* 520 */               this.context = context;
/* 521 */               this.action = action;
/* 522 */               if (parameters != null) {
/* 523 */                   this.parameters = parameters;
                        } else {
/* 525 */                   this.parameters = new Bundle();
                        }
                    }
                }

/* 531 */       public static class Builder extends BuilderBase<Builder> {

/* 544 */           public Builder(Context context, Session session, String action, Bundle parameters) {
/* 545 */               super(context, session, action, parameters);
                    }

/* 557 */           public Builder(Context context, String applicationId, String action, Bundle parameters) {
/* 558 */               super(context, applicationId, action, parameters);
                    }
                }

/* 564 */       public static class FeedDialogBuilder extends BuilderBase<FeedDialogBuilder> {
                    private static final String CAPTION_PARAM = "caption";
                    private static final String DESCRIPTION_PARAM = "description";
                    private static final String FEED_DIALOG = "feed";
                    private static final String FROM_PARAM = "from";
                    private static final String LINK_PARAM = "link";
                    private static final String NAME_PARAM = "name";
                    private static final String PICTURE_PARAM = "picture";
                    private static final String SOURCE_PARAM = "source";
                    private static final String TO_PARAM = "to";


/* 584 */           public FeedDialogBuilder(Context context, Session session) {
/* 585 */               super(context, session, FEED_DIALOG, (Bundle) null);
                    }

/* 598 */           public FeedDialogBuilder(Context context, Session session, Bundle parameters) {
/* 599 */               super(context, session, FEED_DIALOG, parameters);
                    }

/* 610 */           public FeedDialogBuilder setFrom(String id) {
/* 611 */               getParameters().putString(FROM_PARAM, id);
/* 612 */               return this;
                    }

/* 622 */           public FeedDialogBuilder setTo(String id) {
/* 623 */               getParameters().putString(TO_PARAM, id);
/* 624 */               return this;
                    }

/* 633 */           public FeedDialogBuilder setLink(String link) {
/* 634 */               getParameters().putString(LINK_PARAM, link);
/* 635 */               return this;
                    }

/* 644 */           public FeedDialogBuilder setPicture(String picture) {
/* 645 */               getParameters().putString(PICTURE_PARAM, picture);
/* 646 */               return this;
                    }

/* 656 */           public FeedDialogBuilder setSource(String source) {
/* 657 */               getParameters().putString(SOURCE_PARAM, source);
/* 658 */               return this;
                    }

/* 667 */           public FeedDialogBuilder setName(String name) {
/* 668 */               getParameters().putString("name", name);
/* 669 */               return this;
                    }

/* 678 */           public FeedDialogBuilder setCaption(String caption) {
/* 679 */               getParameters().putString(CAPTION_PARAM, caption);
/* 680 */               return this;
                    }

/* 689 */           public FeedDialogBuilder setDescription(String description) {
/* 690 */               getParameters().putString("description", description);
/* 691 */               return this;
                    }
                }

/* 697 */       public static class RequestsDialogBuilder extends BuilderBase<RequestsDialogBuilder> {
                    private static final String APPREQUESTS_DIALOG = "apprequests";
                    private static final String DATA_PARAM = "data";
                    private static final String MESSAGE_PARAM = "message";
                    private static final String TITLE_PARAM = "title";
                    private static final String TO_PARAM = "to";


/* 713 */           public RequestsDialogBuilder(Context context, Session session) {
/* 714 */               super(context, session, APPREQUESTS_DIALOG, (Bundle) null);
                    }

/* 727 */           public RequestsDialogBuilder(Context context, Session session, Bundle parameters) {
/* 728 */               super(context, session, APPREQUESTS_DIALOG, parameters);
                    }

/* 738 */           public RequestsDialogBuilder setMessage(String message) {
/* 739 */               getParameters().putString("message", message);
/* 740 */               return this;
                    }

/* 751 */           public RequestsDialogBuilder setTo(String id) {
/* 752 */               getParameters().putString(TO_PARAM, id);
/* 753 */               return this;
                    }

/* 763 */           public RequestsDialogBuilder setData(String data) {
/* 764 */               getParameters().putString("data", data);
/* 765 */               return this;
                    }

/* 774 */           public RequestsDialogBuilder setTitle(String title) {
/* 775 */               getParameters().putString("title", title);
/* 776 */               return this;
                    }
                }
            }
