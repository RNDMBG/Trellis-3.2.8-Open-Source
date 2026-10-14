            package com.google.android.gms.internal;

            import android.content.Context;
            import android.webkit.WebSettings;
            
            /* JADX INFO: renamed from: com.google.android.gms.internal.ck */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public final class C0999ck {
                /* JADX INFO: renamed from: a */
                public static void m1441a(Context context, WebSettings webSettings) {
                    C0998cj.m1435a(context, webSettings);
                    webSettings.setMediaPlaybackRequiresUserGesture(false);
                }

                public static String getDefaultUserAgent(Context context) {
                    return WebSettings.getDefaultUserAgent(context);
                }
            }
