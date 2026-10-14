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
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 49 */    public class ExploreFragment extends BaseControllerFragment implements OnTabChangedListener, TopScrollable, ScrollDeltaScrollView.ScrollDeltaListener {
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

/* 50 */        static /* synthetic */ RelativeLayout access$100(ExploreFragment x0) {
/* 51 */            return x0.mProgressContainer;
                }

/* 50 */        static /* synthetic */ View access$200(ExploreFragment x0) {
/* 51 */            return x0.mEmpty;
                }

/* 50 */        static /* synthetic */ ImageView access$300(ExploreFragment x0) {
/* 51 */            return x0.mSadface;
                }

/* 50 */        static /* synthetic */ View access$400(ExploreFragment x0) {
/* 51 */            return x0.mSadfaceContainer;
                }

/* 50 */        static /* synthetic */ TextView access$500(ExploreFragment x0) {
/* 51 */            return x0.mEmptyText;
                }

                /* JADX INFO: renamed from: co.vine.android.ExploreFragment$1 */
/* 77 */        class C02051 extends BroadcastReceiver {
/* 78 */            C02051() {
                    }

                    @Override // android.content.BroadcastReceiver
/* 81 */            public void onReceive(Context context, Intent intent) {
                        NetworkInfo ni;
/* 82 */                ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
/* 85 */                if (connectivityManager == null) {
/* 85 */                    ni = null;
                        } else {
                            try {
                                ni = connectivityManager.getActiveNetworkInfo();
                            } catch (SecurityException e) {
/* 94 */                        return;
                            }
                        }
/* 88 */                if (ni != null && ni.isConnected()) {
/* 89 */                    ExploreFragment.this.reloadWebView(true);
                        }
                    }
                }

/* 95 */        private static class WebViewInitializedListener implements WebViewContainerLayout.OnWebViewInitializedListener {
/* 96 */            private WebViewInitializedListener() {
                    }

/* 96 */            /* synthetic */ WebViewInitializedListener(C02051 x0) {
/* 97 */                this();
                    }

                    @Override // co.vine.android.views.WebViewContainerLayout.OnWebViewInitializedListener
                    @SuppressLint({"SetJavaScriptEnabled"})
/* 102 */           public void onInitialized(WebView webView) {
/* 103 */               WebSettings settings = webView.getSettings();
/* 104 */               settings.setSavePassword(false);
/* 105 */               settings.setSaveFormData(false);
                        try {
/* 107 */                   settings.setJavaScriptEnabled(true);
                        } catch (Exception e) {
                        }
/* 111 */               webView.setHorizontalScrollBarEnabled(false);
                    }
                }

                @Override // android.support.v4.app.Fragment
/* 117 */       public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
/* 118 */           View v = inflater.inflate(C0257R.layout.explore, container, false);
/* 119 */           this.mEmpty = v.findViewById(C0257R.id.empty);
/* 120 */           this.mEmptyText = (TextView) v.findViewById(C0257R.id.empty_text);
/* 121 */           this.mProgressContainer = (RelativeLayout) v.findViewById(C0257R.id.progress_container);
/* 122 */           this.mSadfaceContainer = v.findViewById(C0257R.id.real_sadface);
/* 123 */           this.mSadface = (ImageView) v.findViewById(C0257R.id.sadface);
/* 124 */           this.mWebViewContainer = (WebViewContainerLayout) v.findViewById(C0257R.id.webview_container);
/* 125 */           this.mWebViewContainer.setInitializationListener(new WebViewInitializedListener(null));
/* 126 */           v.findViewById(C0257R.id.search).setOnClickListener(new ViewOnClickListenerC02062());
/* 132 */           ((ScrollDeltaScrollView) v.findViewById(C0257R.id.scrollview)).setScrollDeltaListener(this);
/* 133 */           this.mPrefs = Util.getDefaultSharedPrefs(getActivity());
/* 134 */           return v;
                }

                /* JADX INFO: renamed from: co.vine.android.ExploreFragment$2 */
/* 124 */       class ViewOnClickListenerC02062 implements View.OnClickListener {
/* 125 */           ViewOnClickListenerC02062() {
                    }

                    @Override // android.view.View.OnClickListener
/* 128 */           public void onClick(View v) {
/* 129 */               ExploreFragment.this.startActivity(new Intent(ExploreFragment.this.getActivity(), (Class<?>) SearchActivity.class));
                    }
                }

                @Override // android.support.v4.app.Fragment
/* 138 */       public void onViewCreated(View view, Bundle savedInstanceState) {
/* 139 */           this.mWebViewContainer.onViewCreated();
                }

                @Override // android.support.v4.app.Fragment
/* 143 */       public void onActivityCreated(Bundle savedInstanceState) {
/* 144 */           super.onActivityCreated(savedInstanceState);
/* 145 */           this.mExploreUrl = VineAPI.getInstance(getActivity()).getExploreUrl();
/* 146 */           if (getArguments().getBoolean("take_focus", false)) {
/* 147 */               HomeTabActivity callback = (HomeTabActivity) getActivity();
/* 148 */               callback.showPage(2, this);
/* 149 */               if (BuildUtil.isLogsOn()) {
/* 150 */                   Log.d(TAG, "Explore tab took focus.");
                        }
                    }
/* 153 */           reloadWebView(true);
                }

                @Override // co.vine.android.BaseControllerFragment, co.vine.android.BaseFragment, android.support.v4.app.Fragment
/* 157 */       public void onResume() {
/* 158 */           super.onResume();
/* 159 */           if (this.mWebViewContainer != null) {
/* 160 */               this.mWebViewContainer.onResume();
                    }
/* 162 */           IntentFilter filter = new IntentFilter();
/* 163 */           filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
/* 164 */           filter.addAction(AppController.ACTION_ABORT_ALL_REQUESTS);
/* 165 */           getActivity().registerReceiver(this.mConnectivityReceiver, filter, CrossConstants.BROADCAST_PERMISSION, null);
                }

                @Override // co.vine.android.BaseControllerFragment, android.support.v4.app.Fragment
/* 169 */       public void onPause() {
/* 170 */           super.onPause();
/* 171 */           if (this.mWebViewContainer != null) {
/* 172 */               this.mWebViewContainer.onPause();
                    }
/* 174 */           getActivity().unregisterReceiver(this.mConnectivityReceiver);
                }

/* 177 */       public void reloadWebView(boolean cache) {
/* 178 */           WebView webView = getWebView();
/* 179 */           if (webView != null) {
/* 180 */               webView.setVisibility(0);
/* 181 */               this.mSadfaceContainer.setVisibility(8);
/* 182 */               webView.setWebViewClient(new VineVideoListWebViewClient());
/* 183 */               WebSettings s = webView.getSettings();
/* 184 */               if (cache) {
/* 185 */                   s.setCacheMode(-1);
                        } else {
/* 187 */                   s.setCacheMode(2);
                        }
/* 189 */               Map<String, String> headers = new HashMap<>(1);
/* 190 */               String language = Util.getLocale();
/* 191 */               if (!TextUtils.isEmpty(language)) {
/* 192 */                   headers.put(VineAPI.ACCEPT_LANGUAGE_HEADER, language);
                        }
/* 194 */               VineAPI api = VineAPI.getInstance(getActivity());
/* 195 */               headers.put(VineAPI.X_VINE_CLIENT, api.getVineClientHeader());
/* 196 */               if (!BuildUtil.isProduction()) {
/* 197 */                   headers.put(VineAPI.SECRET_AUTH_HEADER, api.getAuthHeaderSecret());
                        }
/* 199 */               String exploreUrl = getExploreUrl();
/* 200 */               if (BuildUtil.isLogsOn()) {
/* 201 */                   Log.d(TAG, "Accessing explore url: " + exploreUrl);
                        }
/* 203 */               webView.loadUrl(exploreUrl, headers);
                    }
                }

/* 210 */       private String getExploreUrl() {
                    String versionName;
                    try {
/* 211 */               versionName = Util.getVersionName(getActivity());
                    } catch (PackageManager.NameNotFoundException e) {
/* 214 */               versionName = VALUE_UNDEFINED;
                    }
/* 218 */           String model = String.format(Locale.US, "%s_%s", Build.MANUFACTURER, Build.MODEL);
/* 220 */           ArrayList<BasicNameValuePair> data = new ArrayList<>();
/* 222 */           data.add(new BasicNameValuePair(PARAM_MODEL, model.replaceAll("\\s+", "-")));
/* 223 */           data.add(new BasicNameValuePair("v", versionName));
/* 224 */           data.add(new BasicNameValuePair(PARAM_OS, VALUE_OS));
/* 225 */           data.add(new BasicNameValuePair(PARAM_EDITION, SettingsManager.getEdition(getActivity())));
/* 227 */           return this.mExploreUrl + "?" + URLEncodedUtils.format(data, IoUtils.UTF_8);
                }

                @Override // co.vine.android.widget.OnTabChangedListener
/* 231 */       public void onMoveAway(int newPosition) {
                }

                @Override // co.vine.android.widget.OnTabChangedListener
/* 235 */       public void onMoveTo(int oldPosition) {
/* 236 */           long lastTime = this.mPrefs.getLong(PREF_LAST_REFRESH_TIME, 0L);
/* 237 */           SharedPreferences.Editor edit = this.mPrefs.edit();
/* 238 */           long now = System.currentTimeMillis();
/* 239 */           if (edit != null) {
                        if (lastTime > 0 && REFRESH_INTERVAL + lastTime < now) {
/* 241 */                   reloadWebView(true);
/* 242 */                   edit.putLong(PREF_LAST_REFRESH_TIME, now).apply();
                        } else if (lastTime <= 0) {
/* 244 */                   edit.putLong(PREF_LAST_REFRESH_TIME, now).apply();
                        }
                    }
                }

/* 249 */       private WebView getWebView() {
/* 250 */           if (this.mWebViewContainer != null) {
                        return this.mWebViewContainer.getWebView();
                    }
                    return null;
                }

                @Override // com.twitter.android.widget.TopScrollable
/* 254 */       public boolean scrollTop() {
/* 255 */           WebView webView = getWebView();
/* 256 */           if (webView == null) {
                        return false;
                    }
/* 257 */           webView.scrollTo(0, 0);
/* 258 */           return true;
                }

                @Override // co.vine.android.widget.ScrollDeltaScrollView.ScrollDeltaListener
/* 265 */       public void onScroll(int delta) {
/* 266 */           ((HomeTabActivity) getActivity()).onScroll(delta);
                }

/* 267 */       class VineVideoListWebViewClient extends WebViewClient {
/* 268 */           VineVideoListWebViewClient() {
                    }

                    @Override // android.webkit.WebViewClient
/* 272 */           public boolean shouldOverrideUrlLoading(WebView view, String url) {
/* 273 */               Uri parsedUri = Uri.parse(url);
/* 274 */               if (parsedUri.getScheme().equals(HomeTabActivity.SCHEME_VINE)) {
/* 275 */                   Intent intent = new Intent();
/* 276 */                   intent.setData(parsedUri);
/* 278 */                   String host = parsedUri.getHost();
/* 279 */                   if ("channels".equals(host)) {
/* 280 */                       intent.setClass(ExploreFragment.this.getActivity(), ExploreChannelsActivity.class);
                            } else {
/* 282 */                       intent.setClass(ExploreFragment.this.getActivity(), ExploreVideoListActivity.class);
                            }
/* 284 */                   ExploreFragment.this.startActivity(intent);
/* 285 */                   return true;
                        }
/* 287 */               return false;
                    }

                    @Override // android.webkit.WebViewClient
/* 293 */           public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                        String message;
/* 294 */               SLog.dWithTag(ExploreFragment.TAG, "WebViewClient errorCode=" + errorCode);
/* 297 */               view.setVisibility(8);
/* 301 */               if (ExploreFragment.access$100(ExploreFragment.this) != null) {
/* 302 */                   ExploreFragment.access$100(ExploreFragment.this).setVisibility(8);
                        }
/* 304 */               if (ExploreFragment.access$200(ExploreFragment.this) != null) {
/* 305 */                   ExploreFragment.access$200(ExploreFragment.this).setVisibility(0);
                        }
/* 307 */               if (ExploreFragment.access$300(ExploreFragment.this) != null) {
/* 308 */                   ExploreFragment.access$400(ExploreFragment.this).setVisibility(0);
                        }
/* 310 */               if (ExploreFragment.access$500(ExploreFragment.this) != null) {
/* 311 */                   ExploreFragment.access$500(ExploreFragment.this).setVisibility(0);
                        }
/* 315 */               if (errorCode == -2) {
/* 316 */                   message = ExploreFragment.this.getString(C0257R.string.explore_empty_no_connectivity);
                        } else {
/* 318 */                   message = ExploreFragment.this.getString(C0257R.string.explore_empty_generic);
                        }
/* 320 */               ExploreFragment.access$500(ExploreFragment.this).setText(message);
                    }

                    @Override // android.webkit.WebViewClient
/* 323 */           public void onPageFinished(WebView view, String url) {
/* 324 */               ExploreFragment.access$200(ExploreFragment.this).setVisibility(8);
/* 325 */               view.scrollTo(0, 0);
                    }
                }

                @Override // android.support.v4.app.Fragment
/* 330 */       public void onDestroy() {
/* 331 */           if (this.mWebViewContainer != null) {
/* 332 */               this.mWebViewContainer.onDestroy();
                    }
/* 334 */           super.onDestroy();
                }
            }
