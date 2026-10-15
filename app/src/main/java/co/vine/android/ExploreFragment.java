            package co.vine.android;

            import android.annotation.SuppressLint;
            import android.content.BroadcastReceiver;
            import android.content.Context;
            import android.content.Intent;
            import android.content.IntentFilter;
            import android.content.SharedPreferences;
            import android.content.pm.PackageManager;
            import android.net.ConnectivityManager;
            import android.net.NetworkInfo;
            import android.net.Uri;
            import android.os.Build;
            import android.os.Bundle;
            import android.text.TextUtils;
            import android.util.Log;
            import android.view.LayoutInflater;
            import android.view.View;
            import android.view.ViewGroup;
            import android.webkit.WebSettings;
            import android.webkit.WebView;
            import android.webkit.WebViewClient;
            import android.widget.ImageView;
            import android.widget.RelativeLayout;
            import android.widget.TextView;
            import co.vine.android.client.AppController;
            import co.vine.android.client.VineAPI;
            import co.vine.android.provider.SettingsManager;
            import co.vine.android.util.BuildUtil;
            import co.vine.android.util.CrossConstants;
            import co.vine.android.util.Util;
            import co.vine.android.views.WebViewContainerLayout;
            import co.vine.android.widget.OnTabChangedListener;
            import co.vine.android.widget.ScrollDeltaScrollView;
            import com.edisonwang.android.slog.SLog;
            import com.twitter.android.widget.TopScrollable;
            import com.vandalsoftware.p004io.IoUtils;
            import java.util.ArrayList;
            import java.util.HashMap;
            import java.util.Locale;
            import java.util.Map;
            import org.apache.http.client.utils.URLEncodedUtils;
            import org.apache.http.message.BasicNameValuePair;
            
            
    public class ExploreFragment extends BaseControllerFragment implements OnTabChangedListener, TopScrollable, ScrollDeltaScrollView.ScrollDeltaListener {
                public static final String ARG_TAKE_FOCUS = "take_focus";
                private static final String PARAM_EDITION = "ed";
                private static final String PARAM_MODEL = "model";
                private static final String PARAM_OS = "os";
                private static final String PARAM_VERSION = "v";
                private static final String PREF_LAST_REFRESH_TIME = "last_explore";
                private static final long REFRESH_INTERVAL = 900000;
                private static final String TAG = "ExploreFragment";
                private static final String VALUE_OS = "android";
                private static final String VALUE_UNDEFINED = "undefined";
                private BroadcastReceiver mConnectivityReceiver = new C02051();
                private View mEmpty;
                private TextView mEmptyText;
                private String mExploreUrl;
                private SharedPreferences mPrefs;
                private RelativeLayout mProgressContainer;
                private ImageView mSadface;
                private View mSadfaceContainer;
                private WebViewContainerLayout mWebViewContainer;

        static  RelativeLayout access$100(ExploreFragment x0) {
            return x0.mProgressContainer;
                }

        static  View access$200(ExploreFragment x0) {
            return x0.mEmpty;
                }

        static  ImageView access$300(ExploreFragment x0) {
            return x0.mSadface;
                }

        static  View access$400(ExploreFragment x0) {
            return x0.mSadfaceContainer;
                }

        static  TextView access$500(ExploreFragment x0) {
            return x0.mEmptyText;
                }

                
        class C02051 extends BroadcastReceiver {
            C02051() {
                    }

                    @Override 
            public void onReceive(Context context, Intent intent) {
                        NetworkInfo ni;
                ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
                if (connectivityManager == null) {
                    ni = null;
                        } else {
                            try {
                                ni = connectivityManager.getActiveNetworkInfo();
                            } catch (SecurityException e) {
                        return;
                            }
                        }
                if (ni != null && ni.isConnected()) {
                    ExploreFragment.this.reloadWebView(true);
                        }
                    }
                }

        private static class WebViewInitializedListener implements WebViewContainerLayout.OnWebViewInitializedListener {
            private WebViewInitializedListener() {
                    }

             WebViewInitializedListener(C02051 x0) {
                this();
                    }

                    @Override 
                    @SuppressLint({"SetJavaScriptEnabled"})
           public void onInitialized(WebView webView) {
               WebSettings settings = webView.getSettings();
               settings.setSavePassword(false);
               settings.setSaveFormData(false);
                        try {
                   settings.setJavaScriptEnabled(true);
                        } catch (Exception e) {
                        }
               webView.setHorizontalScrollBarEnabled(false);
                    }
                }

                @Override 
       public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
           View v = inflater.inflate(R.layout.explore, container, false);
           this.mEmpty = v.findViewById(R.id.empty);
           this.mEmptyText = (TextView) v.findViewById(R.id.empty_text);
           this.mProgressContainer = (RelativeLayout) v.findViewById(R.id.progress_container);
           this.mSadfaceContainer = v.findViewById(R.id.real_sadface);
           this.mSadface = (ImageView) v.findViewById(R.id.sadface);
           this.mWebViewContainer = (WebViewContainerLayout) v.findViewById(R.id.webview_container);
           this.mWebViewContainer.setInitializationListener(new WebViewInitializedListener(null));
           v.findViewById(R.id.search).setOnClickListener(new ViewOnClickListenerC02062());
           ((ScrollDeltaScrollView) v.findViewById(R.id.scrollview)).setScrollDeltaListener(this);
           this.mPrefs = Util.getDefaultSharedPrefs(getActivity());
           return v;
                }

                
       class ViewOnClickListenerC02062 implements View.OnClickListener {
           ViewOnClickListenerC02062() {
                    }

                    @Override 
           public void onClick(View v) {
               ExploreFragment.this.startActivity(new Intent(ExploreFragment.this.getActivity(), (Class<?>) SearchActivity.class));
                    }
                }

                @Override 
       public void onViewCreated(View view, Bundle savedInstanceState) {
           this.mWebViewContainer.onViewCreated();
                }

                @Override 
       public void onActivityCreated(Bundle savedInstanceState) {
           super.onActivityCreated(savedInstanceState);
           this.mExploreUrl = VineAPI.getInstance(getActivity()).getExploreUrl();
           if (getArguments().getBoolean("take_focus", false)) {
               HomeTabActivity callback = (HomeTabActivity) getActivity();
               callback.showPage(2, this);
               if (BuildUtil.isLogsOn()) {
                   Log.d(TAG, "Explore tab took focus.");
                        }
                    }
           reloadWebView(true);
                }

                @Override 
       public void onResume() {
           super.onResume();
           if (this.mWebViewContainer != null) {
               this.mWebViewContainer.onResume();
                    }
           IntentFilter filter = new IntentFilter();
           filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
           filter.addAction(AppController.ACTION_ABORT_ALL_REQUESTS);
           getActivity().registerReceiver(this.mConnectivityReceiver, filter, CrossConstants.BROADCAST_PERMISSION, null);
                }

                @Override 
       public void onPause() {
           super.onPause();
           if (this.mWebViewContainer != null) {
               this.mWebViewContainer.onPause();
                    }
           getActivity().unregisterReceiver(this.mConnectivityReceiver);
                }

       public void reloadWebView(boolean cache) {
           WebView webView = getWebView();
           if (webView != null) {
               webView.setVisibility(0);
               this.mSadfaceContainer.setVisibility(8);
               webView.setWebViewClient(new VineVideoListWebViewClient());
               WebSettings s = webView.getSettings();
               if (cache) {
                   s.setCacheMode(-1);
                        } else {
                   s.setCacheMode(2);
                        }
               Map<String, String> headers = new HashMap<>(1);
               String language = Util.getLocale();
               if (!TextUtils.isEmpty(language)) {
                   headers.put(VineAPI.ACCEPT_LANGUAGE_HEADER, language);
                        }
               VineAPI api = VineAPI.getInstance(getActivity());
               headers.put(VineAPI.X_VINE_CLIENT, api.getVineClientHeader());
               if (!BuildUtil.isProduction()) {
                   headers.put(VineAPI.SECRET_AUTH_HEADER, api.getAuthHeaderSecret());
                        }
               String exploreUrl = getExploreUrl();
               if (BuildUtil.isLogsOn()) {
                   Log.d(TAG, "Accessing explore url: " + exploreUrl);
                        }
               webView.loadUrl(exploreUrl, headers);
                    }
                }

       private String getExploreUrl() {
                    String versionName;
                    try {
               versionName = Util.getVersionName(getActivity());
                    } catch (PackageManager.NameNotFoundException e) {
               versionName = VALUE_UNDEFINED;
                    }
           String model = String.format(Locale.US, "%s_%s", Build.MANUFACTURER, Build.MODEL);
           ArrayList<BasicNameValuePair> data = new ArrayList<>();
           data.add(new BasicNameValuePair(PARAM_MODEL, model.replaceAll("\\s+", "-")));
           data.add(new BasicNameValuePair("v", versionName));
           data.add(new BasicNameValuePair(PARAM_OS, VALUE_OS));
           data.add(new BasicNameValuePair(PARAM_EDITION, SettingsManager.getEdition(getActivity())));
           return this.mExploreUrl + "?" + URLEncodedUtils.format(data, IoUtils.UTF_8);
                }

                @Override 
       public void onMoveAway(int newPosition) {
                }

                @Override 
       public void onMoveTo(int oldPosition) {
           long lastTime = this.mPrefs.getLong(PREF_LAST_REFRESH_TIME, 0L);
           SharedPreferences.Editor edit = this.mPrefs.edit();
           long now = System.currentTimeMillis();
           if (edit != null) {
                        if (lastTime > 0 && REFRESH_INTERVAL + lastTime < now) {
                   reloadWebView(true);
                   edit.putLong(PREF_LAST_REFRESH_TIME, now).apply();
                        } else if (lastTime <= 0) {
                   edit.putLong(PREF_LAST_REFRESH_TIME, now).apply();
                        }
                    }
                }

       private WebView getWebView() {
           if (this.mWebViewContainer != null) {
                        return this.mWebViewContainer.getWebView();
                    }
                    return null;
                }

                @Override 
       public boolean scrollTop() {
           WebView webView = getWebView();
           if (webView == null) {
                        return false;
                    }
           webView.scrollTo(0, 0);
           return true;
                }

                @Override 
       public void onScroll(int delta) {
           ((HomeTabActivity) getActivity()).onScroll(delta);
                }

       class VineVideoListWebViewClient extends WebViewClient {
           VineVideoListWebViewClient() {
                    }

                    @Override 
           public boolean shouldOverrideUrlLoading(WebView view, String url) {
               Uri parsedUri = Uri.parse(url);
               if (parsedUri.getScheme().equals(HomeTabActivity.SCHEME_VINE)) {
                   Intent intent = new Intent();
                   intent.setData(parsedUri);
                   String host = parsedUri.getHost();
                   if ("channels".equals(host)) {
                       intent.setClass(ExploreFragment.this.getActivity(), ExploreChannelsActivity.class);
                            } else {
                       intent.setClass(ExploreFragment.this.getActivity(), ExploreVideoListActivity.class);
                            }
                   ExploreFragment.this.startActivity(intent);
                   return true;
                        }
               return false;
                    }

                    @Override 
           public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                        String message;
               SLog.dWithTag(ExploreFragment.TAG, "WebViewClient errorCode=" + errorCode);
               view.setVisibility(8);
               if (ExploreFragment.access$100(ExploreFragment.this) != null) {
                   ExploreFragment.access$100(ExploreFragment.this).setVisibility(8);
                        }
               if (ExploreFragment.access$200(ExploreFragment.this) != null) {
                   ExploreFragment.access$200(ExploreFragment.this).setVisibility(0);
                        }
               if (ExploreFragment.access$300(ExploreFragment.this) != null) {
                   ExploreFragment.access$400(ExploreFragment.this).setVisibility(0);
                        }
               if (ExploreFragment.access$500(ExploreFragment.this) != null) {
                   ExploreFragment.access$500(ExploreFragment.this).setVisibility(0);
                        }
               if (errorCode == -2) {
                   message = ExploreFragment.this.getString(R.string.explore_empty_no_connectivity);
                        } else {
                   message = ExploreFragment.this.getString(R.string.explore_empty_generic);
                        }
               ExploreFragment.access$500(ExploreFragment.this).setText(message);
                    }

                    @Override 
           public void onPageFinished(WebView view, String url) {
               ExploreFragment.access$200(ExploreFragment.this).setVisibility(8);
               view.scrollTo(0, 0);
                    }
                }

                @Override 
       public void onDestroy() {
           if (this.mWebViewContainer != null) {
               this.mWebViewContainer.onDestroy();
                    }
           super.onDestroy();
                }
            }
