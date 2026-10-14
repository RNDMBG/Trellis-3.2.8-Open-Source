            package com.google.android.gms.internal;

            import android.content.Context;
            import android.view.View;
            import android.view.Window;
            import android.webkit.WebSettings;
            import android.webkit.WebView;
            import java.io.File;
            
            /* JADX INFO: renamed from: com.google.android.gms.internal.cj */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public final class C0998cj {
                /* JADX INFO: renamed from: a */
                public static void m1435a(Context context, WebSettings webSettings) {
                    webSettings.setAppCachePath(new File(context.getCacheDir(), "com.google.android.gms.ads.appcache").getAbsolutePath());
                    webSettings.setAppCacheMaxSize(0L);
                    webSettings.setAppCacheEnabled(true);
                    webSettings.setDatabasePath(context.getDatabasePath("com.google.android.gms.ads.db").getAbsolutePath());
                    webSettings.setDatabaseEnabled(true);
                    webSettings.setDomStorageEnabled(true);
                    webSettings.setDisplayZoomControls(false);
                    webSettings.setBuiltInZoomControls(true);
                    webSettings.setSupportZoom(true);
                }

                /* JADX INFO: renamed from: a */
                public static void m1436a(Window window) {
                    window.setFlags(16777216, 16777216);
                }

                /* JADX INFO: renamed from: a */
                public static void m1437a(WebView webView) {
                    webView.onPause();
                }

                /* JADX INFO: renamed from: b */
                public static void m1438b(WebView webView) {
                    webView.onResume();
                }

                /* JADX INFO: renamed from: c */
                public static void m1439c(View view) {
                    view.setLayerType(1, null);
                }

                /* JADX INFO: renamed from: d */
                public static void m1440d(View view) {
                    view.setLayerType(0, null);
                }
            }
