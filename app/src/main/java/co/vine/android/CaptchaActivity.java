            package co.vine.android;

            import android.content.Context;
            import android.content.Intent;
            import android.net.Uri;
            import android.os.AsyncTask;
            import android.os.Bundle;
            import android.view.MenuItem;
            import android.webkit.WebSettings;
            import android.webkit.WebView;
            import android.webkit.WebViewClient;
            import co.vine.android.network.NetworkOperation;
            import co.vine.android.service.VineService;
            
            
    public class CaptchaActivity extends BaseControllerActionBarActivity {
                public static final int CAPTCHA_CANCELED = 2;
                private static final String CAPTCHA_COMPLETE = "_captcha_complete";
                private String mReqId;

        static  String access$000(CaptchaActivity x0) {
            return x0.mReqId;
                }

                @Override 
        public void onCreate(Bundle bun) {
            super.onCreate(bun, C0257R.layout.webview_activity, false);
            Bundle b = getIntent().getExtras();
            WebView webView = (WebView) findViewById(C0257R.id.webview);
            this.mReqId = b.getString(VineService.EXTRA_REQUEST_ID);
            String captchaUrl = b.getString("captcha_url");
            WebSettings settings = webView.getSettings();
            settings.setUserAgentString(NetworkOperation.USER_AGENT_STRING);
            settings.setSavePassword(false);
            settings.setSaveFormData(false);
            webView.loadUrl(captchaUrl);
            webView.setWebViewClient(new C01601());
                }

                
        class C01601 extends WebViewClient {
            C01601() {
                    }

                    @Override 
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Uri parsedUri = Uri.parse(url);
                if (!parsedUri.getScheme().equals(HomeTabActivity.SCHEME_VINE) && !parsedUri.getScheme().equals("vine-dev")) {
                            return false;
                        }
                String host = parsedUri.getHost();
                if (CaptchaActivity.CAPTCHA_COMPLETE.equals(host)) {
                    Intent i = new Intent();
                    i.putExtra(VineService.EXTRA_REQUEST_ID, CaptchaActivity.access$000(CaptchaActivity.this));
                    CaptchaActivity.this.setResult(-1, i);
                    new AnonymousClass1().execute(new Void[0]);
                    CaptchaActivity.this.finish();
                        }
                return true;
                    }

                    
            class AnonymousClass1 extends AsyncTask<Void, Void, Void> {
                AnonymousClass1() {
                        }


                        
                protected Void doInBackground(Void... voids) {
                    AppImpl.getInstance().clearUploadCaptchas(CaptchaActivity.this.getApplicationContext());
                    return null;
                        }
                    }

                    @Override 
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                view.setVisibility(8);
                    }
                }

        public static Intent getIntent(Context context, String captchaUrl, String reqId) {
            Intent i = new Intent(context, (Class<?>) CaptchaActivity.class);
            i.putExtra("captcha_url", captchaUrl);
            i.putExtra(VineService.EXTRA_REQUEST_ID, reqId);
            return i;
                }

                @Override 
        public void onBackPressed() {
            cancelCaptcha();
                }

                @Override 
        public boolean onOptionsItemSelected(MenuItem item) {
            if (item.getItemId() != 16908332) {
                        return false;
                    }
            cancelCaptcha();
            return true;
                }

        private void cancelCaptcha() {
            Intent i = new Intent();
           i.putExtra(VineService.EXTRA_REQUEST_ID, this.mReqId);
           setResult(2, i);
           finish();
                }
            }
