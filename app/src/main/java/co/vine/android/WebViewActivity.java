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
            
            
    public class WebViewActivity extends BaseControllerActionBarActivity {
                public static final String EXTRA_TYPE = "type";
                public static final int TYPE_ATTRIBUTION = 5;
                public static final int TYPE_PRIVACY_POLICY_TWITTER = 3;
                public static final int TYPE_PRIVACY_POLICY_VINE = 1;
                public static final int TYPE_TOS_TWITTER = 4;
                public static final int TYPE_TOS_VINE = 2;

                @Override 
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState, R.layout.webview_activity, false);
            WebView webView = (WebView) findViewById(R.id.webview);
            webView.setWebViewClient(new WebViewClient());
            WebSettings settings = webView.getSettings();
            settings.setJavaScriptEnabled(true);
            settings.setSavePassword(false);
            settings.setSaveFormData(false);
            Bundle b = getIntent().getExtras();
            if (b != null) {
                Map<String, String> headers = new HashMap<>(1);
                String language = Locale.getDefault().getLanguage();
                if (!TextUtils.isEmpty(language)) {
                    headers.put(VineAPI.ACCEPT_LANGUAGE_HEADER, language);
                    headers.put(VineAPI.X_VINE_CLIENT, VineAPI.getInstance(this).getVineClientHeader());
                        }
                        switch (b.getInt("type")) {
                            case 1:
                        webView.loadUrl(getString(R.string.privacy_policy_url), headers);
                                break;
                            case 2:
                        webView.loadUrl(getString(R.string.tos_url), headers);
                                break;
                            case 3:
                        webView.loadUrl(getString(R.string.privacy_policy_twitter_url), headers);
                                break;
                            case 4:
                        webView.loadUrl(getString(R.string.tos_twitter_url), headers);
                                break;
                            case 5:
                        if (BuildUtil.isAStar()) {
                            webView.loadUrl("file:///android_asset/astar_attribution.html");
                                } else {
                            webView.loadUrl(getString(R.string.attribution_url), headers);
                                }
                                break;
                        }
                    } else {
                Uri data = getIntent().getData();
                if (data != null) {
                    webView.loadUrl(data.toString());
                        }
                    }
            setupActionBar((Boolean) true, (Boolean) null, (String) null, (Boolean) null, (Boolean) true);
                }

                @Override 
        public boolean onCreateOptionsMenu(Menu menu) {
            getMenuInflater().inflate(R.menu.done, menu);
            menu.findItem(R.id.done).setEnabled(true);
                    return true;
                }

                @Override 
       public boolean onOptionsItemSelected(MenuItem item) {
           if (item.getItemId() != 2131427939) {
                        return super.onOptionsItemSelected(item);
                    }
           finish();
           return true;
                }

       public static void start(Context context, Uri data) {
           context.startActivity(new Intent(context, (Class<?>) WebViewActivity.class).setData(data));
                }
            }
