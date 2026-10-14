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
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 16 */    public class CaptchaActivity extends BaseControllerActionBarActivity {
                public static final int CAPTCHA_CANCELED = 2;
                private static final String CAPTCHA_COMPLETE = "_captcha_complete";
                private String mReqId;

/* 17 */        static /* synthetic */ String access$000(CaptchaActivity x0) {
/* 18 */            return x0.mReqId;
                }

                @Override // co.vine.android.BaseActionBarActivity, android.support.v7.app.ActionBarActivity, android.support.v4.app.FragmentActivity, android.app.Activity
/* 25 */        public void onCreate(Bundle bun) {
/* 26 */            super.onCreate(bun, C0257R.layout.webview_activity, false);
/* 28 */            Bundle b = getIntent().getExtras();
/* 29 */            WebView webView = (WebView) findViewById(C0257R.id.webview);
/* 31 */            this.mReqId = b.getString(VineService.EXTRA_REQUEST_ID);
/* 32 */            String captchaUrl = b.getString("captcha_url");
/* 34 */            WebSettings settings = webView.getSettings();
/* 35 */            settings.setUserAgentString(NetworkOperation.USER_AGENT_STRING);
/* 36 */            settings.setSavePassword(false);
/* 37 */            settings.setSaveFormData(false);
/* 39 */            webView.loadUrl(captchaUrl);
/* 40 */            webView.setWebViewClient(new C01601());
                }

                /* JADX INFO: renamed from: co.vine.android.CaptchaActivity$1 */
/* 38 */        class C01601 extends WebViewClient {
/* 39 */            C01601() {
                    }

                    @Override // android.webkit.WebViewClient
/* 42 */            public boolean shouldOverrideUrlLoading(WebView view, String url) {
/* 43 */                Uri parsedUri = Uri.parse(url);
/* 44 */                if (!parsedUri.getScheme().equals(HomeTabActivity.SCHEME_VINE) && !parsedUri.getScheme().equals("vine-dev")) {
                            return false;
                        }
/* 46 */                String host = parsedUri.getHost();
/* 47 */                if (CaptchaActivity.CAPTCHA_COMPLETE.equals(host)) {
/* 49 */                    Intent i = new Intent();
/* 50 */                    i.putExtra(VineService.EXTRA_REQUEST_ID, CaptchaActivity.access$000(CaptchaActivity.this));
/* 51 */                    CaptchaActivity.this.setResult(-1, i);
/* 54 */                    new AnonymousClass1().execute(new Void[0]);
/* 61 */                    CaptchaActivity.this.finish();
                        }
/* 63 */                return true;
                    }

                    /* JADX INFO: renamed from: co.vine.android.CaptchaActivity$1$1, reason: invalid class name */
/* 52 */            class AnonymousClass1 extends AsyncTask<Void, Void, Void> {
/* 53 */                AnonymousClass1() {
                        }


                        /* JADX INFO: renamed from: doInBackground, reason: avoid collision after fix types in other method */
/* 56 */                protected Void doInBackground(Void... voids) {
/* 57 */                    AppImpl.getInstance().clearUploadCaptchas(CaptchaActivity.this.getApplicationContext());
/* 58 */                    return null;
                        }
                    }

                    @Override // android.webkit.WebViewClient
/* 71 */            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
/* 72 */                view.setVisibility(8);
                    }
                }

/* 77 */        public static Intent getIntent(Context context, String captchaUrl, String reqId) {
/* 78 */            Intent i = new Intent(context, (Class<?>) CaptchaActivity.class);
/* 79 */            i.putExtra("captcha_url", captchaUrl);
/* 80 */            i.putExtra(VineService.EXTRA_REQUEST_ID, reqId);
/* 81 */            return i;
                }

                @Override // co.vine.android.BaseActionBarActivity, android.support.v7.app.ActionBarActivity, android.support.v4.app.FragmentActivity, android.app.Activity
/* 85 */        public void onBackPressed() {
/* 86 */            cancelCaptcha();
                }

                @Override // co.vine.android.BaseActionBarActivity, android.app.Activity
/* 90 */        public boolean onOptionsItemSelected(MenuItem item) {
/* 91 */            if (item.getItemId() != 16908332) {
                        return false;
                    }
/* 92 */            cancelCaptcha();
/* 93 */            return true;
                }

/* 98 */        private void cancelCaptcha() {
/* 99 */            Intent i = new Intent();
/* 100 */           i.putExtra(VineService.EXTRA_REQUEST_ID, this.mReqId);
/* 101 */           setResult(2, i);
/* 102 */           finish();
                }
            }
