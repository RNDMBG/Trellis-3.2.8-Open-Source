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
            
            
    public class WebDialog extends Dialog {
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

        static  void access$000(WebDialog x0) {
            x0.sendCancelToListener();
                }

        static  void access$200(WebDialog x0, Bundle x1) {
            x0.sendSuccessToListener(x1);
                }

        static  void access$300(WebDialog x0, Throwable x1) {
            x0.sendErrorToListener(x1);
                }

        static  boolean access$400(WebDialog x0) {
            return x0.isDetached;
                }

        static  ProgressDialog access$500(WebDialog x0) {
            return x0.spinner;
                }

        static  FrameLayout access$600(WebDialog x0) {
            return x0.contentFrameLayout;
                }

        static  WebView access$700(WebDialog x0) {
            return x0.webView;
                }

        static  ImageView access$800(WebDialog x0) {
            return x0.crossImageView;
                }

        public static void initLoadingString(int loadingStringRes) {
            STRING_FACEBOOK_LOADING = loadingStringRes;
                }

       public WebDialog(Context context, String url) {
           this(context, url, 16973840);
                }

       public WebDialog(Context context, String url, int theme) {
           super(context, theme);
            this.listenerCalled = false;
            this.isDetached = false;
           this.url = url;
                }

       public WebDialog(Context context, String action, Bundle parameters, int theme, OnCompleteListener listener) {
           super(context, theme);
            this.listenerCalled = false;
            this.isDetached = false;
           parameters = parameters == null ? new Bundle() : parameters;
           parameters.putString("display", DISPLAY_TOUCH);
           parameters.putString("type", USER_AGENT);
           Uri uri = Utility.buildUri(ServerProtocol.DIALOG_AUTHORITY, "v2.3/dialog/" + action, parameters);
           this.url = uri.toString();
           this.onCompleteListener = listener;
                }

       public void setOnCompleteListener(OnCompleteListener listener) {
           this.onCompleteListener = listener;
                }

       public OnCompleteListener getOnCompleteListener() {
           return this.onCompleteListener;
                }

                @Override 
       public void dismiss() {
           if (this.webView != null) {
               this.webView.stopLoading();
                    }
           if (!this.isDetached) {
               if (this.spinner.isShowing()) {
                   this.spinner.dismiss();
                        }
               super.dismiss();
                    }
                }

                @Override 
       public void onDetachedFromWindow() {
           this.isDetached = true;
           super.onDetachedFromWindow();
                }

                @Override 
       public void onAttachedToWindow() {
           this.isDetached = false;
           super.onAttachedToWindow();
                }

                @Override 
       protected void onCreate(Bundle savedInstanceState) {
           super.onCreate(savedInstanceState);
           setOnCancelListener(new DialogInterfaceOnCancelListenerC07811());
           this.spinner = new ProgressDialog(getContext());
           this.spinner.requestWindowFeature(1);
           this.spinner.setMessage(getContext().getString(STRING_FACEBOOK_LOADING));
           this.spinner.setOnCancelListener(new DialogInterfaceOnCancelListenerC07822());
           requestWindowFeature(1);
           this.contentFrameLayout = new FrameLayout(getContext());
           createCrossImage();
           int crossWidth = this.crossImageView.getDrawable().getIntrinsicWidth();
           setUpWebView(crossWidth / 2);
           this.contentFrameLayout.addView(this.crossImageView, new ViewGroup.LayoutParams(-2, -2));
           addContentView(this.contentFrameLayout, new ViewGroup.LayoutParams(-1, -1));
                }

                
       class DialogInterfaceOnCancelListenerC07811 implements DialogInterface.OnCancelListener {
           DialogInterfaceOnCancelListenerC07811() {
                    }

                    @Override 
           public void onCancel(DialogInterface dialogInterface) {
               WebDialog.access$000(WebDialog.this);
                    }
                }

                
       class DialogInterfaceOnCancelListenerC07822 implements DialogInterface.OnCancelListener {
           DialogInterfaceOnCancelListenerC07822() {
                    }

                    @Override 
           public void onCancel(DialogInterface dialogInterface) {
               WebDialog.access$000(WebDialog.this);
               WebDialog.this.dismiss();
                    }
                }

       private void sendSuccessToListener(Bundle values) {
           if (this.onCompleteListener != null && !this.listenerCalled) {
               this.listenerCalled = true;
               this.onCompleteListener.onComplete(values, null);
                    }
                }

       private void sendErrorToListener(Throwable error) {
                    FacebookException facebookException;
           if (this.onCompleteListener != null && !this.listenerCalled) {
               this.listenerCalled = true;
               if (error instanceof FacebookException) {
                   facebookException = (FacebookException) error;
                        } else {
                   facebookException = new FacebookException(error);
                        }
               this.onCompleteListener.onComplete(null, facebookException);
                    }
                }

       private void sendCancelToListener() {
           sendErrorToListener(new FacebookOperationCanceledException());
                }

       private void createCrossImage() {
           this.crossImageView = new ImageView(getContext());
           this.crossImageView.setOnClickListener(new ViewOnClickListenerC07833());
           Drawable crossDrawable = getContext().getResources().getDrawable(C0257R.drawable.com_facebook_close);
           this.crossImageView.setImageDrawable(crossDrawable);
           this.crossImageView.setVisibility(4);
                }

                
       class ViewOnClickListenerC07833 implements View.OnClickListener {
           ViewOnClickListenerC07833() {
                    }

                    @Override 
           public void onClick(View v) {
               WebDialog.access$000(WebDialog.this);
               WebDialog.this.dismiss();
                    }
                }

                @SuppressLint({"SetJavaScriptEnabled"})
       private void setUpWebView(int margin) {
           LinearLayout webViewContainer = new LinearLayout(getContext());
           this.webView = new WebView(getContext());
           this.webView.setVerticalScrollBarEnabled(false);
           this.webView.setHorizontalScrollBarEnabled(false);
           this.webView.setWebViewClient(new DialogWebViewClient(this, null));
           this.webView.getSettings().setJavaScriptEnabled(true);
           this.webView.loadUrl(this.url);
           this.webView.setLayoutParams(new FrameLayout.LayoutParams(-1, -1));
           this.webView.setVisibility(4);
           this.webView.getSettings().setSavePassword(false);
           webViewContainer.setPadding(margin, margin, margin, margin);
           webViewContainer.addView(this.webView);
           this.contentFrameLayout.addView(webViewContainer);
                }

       public static Bundle decodeUrl(String s) {
           Bundle params = new Bundle();
           if (s != null) {
               String[] array = s.split("&");
                        for (String parameter : array) {
                   String[] v = parameter.split("=");
                            try {
                       if (v.length == 2) {
                           params.putString(URLDecoder.decode(v[0], IoUtils.UTF_8), URLDecoder.decode(v[1], IoUtils.UTF_8));
                                } else if (v.length == 1) {
                           params.putString(URLDecoder.decode(v[0], IoUtils.UTF_8), "");
                                }
                            } catch (UnsupportedEncodingException e) {
                            }
                        }
                    }
           return params;
                }

       private class DialogWebViewClient extends WebViewClient {
           private DialogWebViewClient() {
                    }

            DialogWebViewClient(WebDialog x0, DialogInterfaceOnCancelListenerC07811 x1) {
               this();
                    }

                    @Override 
           public boolean shouldOverrideUrlLoading(WebView view, String url) {
                        Bundle values;
               Utility.logd(WebDialog.LOG_TAG, "Redirect URL: " + url);
               if (url.startsWith(WebDialog.REDIRECT_URI)) {
                            try {
                       URL u = new URL(url.replace("fbconnect", "http"));
                       Bundle b = WebDialog.decodeUrl(u.getQuery());
                       b.putAll(WebDialog.decodeUrl(u.getRef()));
                       values = b;
                            } catch (MalformedURLException e) {
                       values = new Bundle();
                            }
                   String error = values.getString("error");
                   if (error == null) {
                       error = values.getString("error_type");
                            }
                   String errorMessage = values.getString("error_msg");
                   if (errorMessage == null) {
                       errorMessage = values.getString("error_description");
                            }
                   String errorCodeString = values.getString("error_code");
                   int errorCode = -1;
                   if (!Utility.isNullOrEmpty(errorCodeString)) {
                                try {
                           errorCode = Integer.parseInt(errorCodeString);
                                } catch (NumberFormatException e2) {
                           errorCode = -1;
                                }
                            }
                   if (Utility.isNullOrEmpty(error) && Utility.isNullOrEmpty(errorMessage) && errorCode == -1) {
                       WebDialog.access$200(WebDialog.this, values);
                            } else if (error != null && (error.equals("access_denied") || error.equals("OAuthAccessDeniedException"))) {
                       WebDialog.access$000(WebDialog.this);
                            } else {
                       FacebookRequestError requestError = new FacebookRequestError(errorCode, error, errorMessage);
                       WebDialog.access$300(WebDialog.this, new FacebookServiceException(requestError, errorMessage));
                            }
                   WebDialog.this.dismiss();
                   return true;
                        }
               if (url.startsWith(WebDialog.CANCEL_URI)) {
                   WebDialog.access$000(WebDialog.this);
                   WebDialog.this.dismiss();
                   return true;
                        }
               if (url.contains(WebDialog.DISPLAY_TOUCH)) {
                   return false;
                        }
               WebDialog.this.getContext().startActivity(new Intent("android.intent.action.VIEW", Uri.parse(url)));
               return true;
                    }

                    @Override 
           public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
               super.onReceivedError(view, errorCode, description, failingUrl);
               WebDialog.access$300(WebDialog.this, new FacebookDialogException(description, errorCode, failingUrl));
               WebDialog.this.dismiss();
                    }

                    @Override 
           public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
               super.onReceivedSslError(view, handler, error);
               WebDialog.access$300(WebDialog.this, new FacebookDialogException(null, -11, null));
               handler.cancel();
               WebDialog.this.dismiss();
                    }

                    @Override 
           public void onPageStarted(WebView view, String url, Bitmap favicon) {
               Utility.logd(WebDialog.LOG_TAG, "Webview loading URL: " + url);
               super.onPageStarted(view, url, favicon);
               if (!WebDialog.access$400(WebDialog.this)) {
                   WebDialog.access$500(WebDialog.this).show();
                        }
                    }

                    @Override 
           public void onPageFinished(WebView view, String url) {
               super.onPageFinished(view, url);
               if (!WebDialog.access$400(WebDialog.this)) {
                   WebDialog.access$500(WebDialog.this).dismiss();
                        }
               WebDialog.access$600(WebDialog.this).setBackgroundColor(0);
               WebDialog.access$700(WebDialog.this).setVisibility(0);
               WebDialog.access$800(WebDialog.this).setVisibility(0);
                    }
                }

                
       public static class BuilderBase<CONCRETE extends BuilderBase<?>> {
                    private String action;
                    private String applicationId;
                    private Context context;
                    private OnCompleteListener listener;
                    private Bundle parameters;
                    private Session session;
                    private int theme = 16973840;

           protected BuilderBase(Context context, Session session, String action, Bundle parameters) {
               Validate.notNull(session, "session");
               if (!session.isOpened()) {
                   throw new FacebookException("Attempted to use a Session that was not open.");
                        }
               this.session = session;
               finishInit(context, action, parameters);
                    }

           protected BuilderBase(Context context, String applicationId, String action, Bundle parameters) {
               Validate.notNullOrEmpty(applicationId, "applicationId");
               this.applicationId = applicationId;
               finishInit(context, action, parameters);
                    }

                    
           public CONCRETE setTheme(int theme) {
               this.theme = theme;
               return (CONCRETE) this;
                    }

                    
           public CONCRETE setOnCompleteListener(OnCompleteListener listener) {
               this.listener = listener;
               return (CONCRETE) this;
                    }

           public WebDialog build() {
               if (this.session != null && this.session.isOpened()) {
                   this.parameters.putString(ServerProtocol.DIALOG_PARAM_APP_ID, this.session.getApplicationId());
                   this.parameters.putString("access_token", this.session.getAccessToken());
                        } else {
                   this.parameters.putString(ServerProtocol.DIALOG_PARAM_APP_ID, this.applicationId);
                        }
               if (!this.parameters.containsKey("redirect_uri")) {
                   this.parameters.putString("redirect_uri", WebDialog.REDIRECT_URI);
                        }
               return new WebDialog(this.context, this.action, this.parameters, this.theme, this.listener);
                    }

           protected String getApplicationId() {
               return this.applicationId;
                    }

           protected Context getContext() {
               return this.context;
                    }

           protected int getTheme() {
               return this.theme;
                    }

           protected Bundle getParameters() {
               return this.parameters;
                    }

           protected OnCompleteListener getListener() {
               return this.listener;
                    }

           private void finishInit(Context context, String action, Bundle parameters) {
               this.context = context;
               this.action = action;
               if (parameters != null) {
                   this.parameters = parameters;
                        } else {
                   this.parameters = new Bundle();
                        }
                    }
                }

       public static class Builder extends BuilderBase<Builder> {

           public Builder(Context context, Session session, String action, Bundle parameters) {
               super(context, session, action, parameters);
                    }

           public Builder(Context context, String applicationId, String action, Bundle parameters) {
               super(context, applicationId, action, parameters);
                    }
                }

       public static class FeedDialogBuilder extends BuilderBase<FeedDialogBuilder> {
                    private static final String CAPTION_PARAM = "caption";
                    private static final String DESCRIPTION_PARAM = "description";
                    private static final String FEED_DIALOG = "feed";
                    private static final String FROM_PARAM = "from";
                    private static final String LINK_PARAM = "link";
                    private static final String NAME_PARAM = "name";
                    private static final String PICTURE_PARAM = "picture";
                    private static final String SOURCE_PARAM = "source";
                    private static final String TO_PARAM = "to";


           public FeedDialogBuilder(Context context, Session session) {
               super(context, session, FEED_DIALOG, (Bundle) null);
                    }

           public FeedDialogBuilder(Context context, Session session, Bundle parameters) {
               super(context, session, FEED_DIALOG, parameters);
                    }

           public FeedDialogBuilder setFrom(String id) {
               getParameters().putString(FROM_PARAM, id);
               return this;
                    }

           public FeedDialogBuilder setTo(String id) {
               getParameters().putString(TO_PARAM, id);
               return this;
                    }

           public FeedDialogBuilder setLink(String link) {
               getParameters().putString(LINK_PARAM, link);
               return this;
                    }

           public FeedDialogBuilder setPicture(String picture) {
               getParameters().putString(PICTURE_PARAM, picture);
               return this;
                    }

           public FeedDialogBuilder setSource(String source) {
               getParameters().putString(SOURCE_PARAM, source);
               return this;
                    }

           public FeedDialogBuilder setName(String name) {
               getParameters().putString("name", name);
               return this;
                    }

           public FeedDialogBuilder setCaption(String caption) {
               getParameters().putString(CAPTION_PARAM, caption);
               return this;
                    }

           public FeedDialogBuilder setDescription(String description) {
               getParameters().putString("description", description);
               return this;
                    }
                }

       public static class RequestsDialogBuilder extends BuilderBase<RequestsDialogBuilder> {
                    private static final String APPREQUESTS_DIALOG = "apprequests";
                    private static final String DATA_PARAM = "data";
                    private static final String MESSAGE_PARAM = "message";
                    private static final String TITLE_PARAM = "title";
                    private static final String TO_PARAM = "to";


           public RequestsDialogBuilder(Context context, Session session) {
               super(context, session, APPREQUESTS_DIALOG, (Bundle) null);
                    }

           public RequestsDialogBuilder(Context context, Session session, Bundle parameters) {
               super(context, session, APPREQUESTS_DIALOG, parameters);
                    }

           public RequestsDialogBuilder setMessage(String message) {
               getParameters().putString("message", message);
               return this;
                    }

           public RequestsDialogBuilder setTo(String id) {
               getParameters().putString(TO_PARAM, id);
               return this;
                    }

           public RequestsDialogBuilder setData(String data) {
               getParameters().putString("data", data);
               return this;
                    }

           public RequestsDialogBuilder setTitle(String title) {
               getParameters().putString("title", title);
               return this;
                    }
                }
            }
