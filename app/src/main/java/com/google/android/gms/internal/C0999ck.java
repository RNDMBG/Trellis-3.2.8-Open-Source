            package com.google.android.gms.internal;

            import android.content.Context;
            import android.webkit.WebSettings;
            
            
            
            public final class C0999ck {
                
                public static void m1441a(Context context, WebSettings webSettings) {
                    C0998cj.m1435a(context, webSettings);
                    webSettings.setMediaPlaybackRequiresUserGesture(false);
                }

                public static String getDefaultUserAgent(Context context) {
                    return WebSettings.getDefaultUserAgent(context);
                }
            }
