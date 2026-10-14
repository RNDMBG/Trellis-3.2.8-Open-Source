            package com.google.android.gms.internal;

            import android.content.Context;
            import android.view.View;
            import android.view.Window;
            import android.webkit.WebSettings;
            import android.webkit.WebView;
            import java.io.File;
            
            
            
            public final class C0998cj {
                
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

                
                public static void m1436a(Window window) {
                    window.setFlags(16777216, 16777216);
                }

                
                public static void m1437a(WebView webView) {
                    webView.onPause();
                }

                
                public static void m1438b(WebView webView) {
                    webView.onResume();
                }

                
                public static void m1439c(View view) {
                    view.setLayerType(1, null);
                }

                
                public static void m1440d(View view) {
                    view.setLayerType(0, null);
                }
            }
