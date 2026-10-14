            package co.vine.android;

            import android.content.Context;
            import android.content.Intent;
            import android.net.Uri;
            import android.os.Bundle;
            import android.text.TextUtils;
            import android.view.Menu;
            import android.view.MenuItem;
            import android.webkit.WebSettings;
            import android.webkit.WebView;
            import android.webkit.WebViewClient;
            import co.vine.android.client.VineAPI;
            import co.vine.android.util.BuildUtil;
            import java.util.HashMap;
            import java.util.Locale;
            import java.util.Map;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 34 */    public class WebViewActivity extends BaseControllerActionBarActivity {
                public static final String EXTRA_TYPE = "type";
                public static final int TYPE_ATTRIBUTION = 5;
                public static final int TYPE_PRIVACY_POLICY_TWITTER = 3;
                public static final int TYPE_PRIVACY_POLICY_VINE = 1;
                public static final int TYPE_TOS_TWITTER = 4;
                public static final int TYPE_TOS_VINE = 2;

                @Override // co.vine.android.BaseActionBarActivity, android.support.v7.app.ActionBarActivity, android.support.v4.app.FragmentActivity, android.app.Activity
/* 35 */        protected void onCreate(Bundle savedInstanceState) {
/* 36 */            super.onCreate(savedInstanceState, C0257R.layout.webview_activity, false);
/* 38 */            WebView webView = (WebView) findViewById(C0257R.id.webview);
/* 39 */            webView.setWebViewClient(new WebViewClient());
/* 40 */            WebSettings settings = webView.getSettings();
/* 41 */            settings.setJavaScriptEnabled(true);
/* 42 */            settings.setSavePassword(false);
/* 43 */            settings.setSaveFormData(false);
/* 44 */            Bundle b = getIntent().getExtras();
/* 45 */            if (b != null) {
/* 46 */                Map<String, String> headers = new HashMap<>(1);
/* 47 */                String language = Locale.getDefault().getLanguage();
/* 48 */                if (!TextUtils.isEmpty(language)) {
/* 49 */                    headers.put(VineAPI.ACCEPT_LANGUAGE_HEADER, language);
/* 50 */                    headers.put(VineAPI.X_VINE_CLIENT, VineAPI.getInstance(this).getVineClientHeader());
                        }
                        switch (b.getInt("type")) {
                            case 1:
/* 55 */                        webView.loadUrl(getString(C0257R.string.privacy_policy_url), headers);
                                break;
                            case 2:
/* 59 */                        webView.loadUrl(getString(C0257R.string.tos_url), headers);
                                break;
                            case 3:
/* 63 */                        webView.loadUrl(getString(C0257R.string.privacy_policy_twitter_url), headers);
                                break;
                            case 4:
/* 67 */                        webView.loadUrl(getString(C0257R.string.tos_twitter_url), headers);
                                break;
                            case 5:
/* 71 */                        if (BuildUtil.isAStar()) {
/* 73 */                            webView.loadUrl("file:///android_asset/astar_attribution.html");
                                } else {
/* 75 */                            webView.loadUrl(getString(C0257R.string.attribution_url), headers);
                                }
                                break;
                        }
                    } else {
/* 84 */                Uri data = getIntent().getData();
/* 85 */                if (data != null) {
/* 86 */                    webView.loadUrl(data.toString());
                        }
                    }
/* 90 */            setupActionBar((Boolean) true, (Boolean) null, (String) null, (Boolean) null, (Boolean) true);
                }

                @Override // co.vine.android.BaseActionBarActivity, android.app.Activity
/* 95 */        public boolean onCreateOptionsMenu(Menu menu) {
/* 96 */            getMenuInflater().inflate(C0257R.menu.done, menu);
/* 97 */            menu.findItem(C0257R.id.done).setEnabled(true);
                    return true;
                }

                @Override // co.vine.android.BaseActionBarActivity, android.app.Activity
/* 102 */       public boolean onOptionsItemSelected(MenuItem item) {
/* 103 */           if (item.getItemId() != 2131427939) {
                        return super.onOptionsItemSelected(item);
                    }
/* 104 */           finish();
/* 105 */           return true;
                }

/* 111 */       public static void start(Context context, Uri data) {
/* 112 */           context.startActivity(new Intent(context, (Class<?>) WebViewActivity.class).setData(data));
                }
            }
