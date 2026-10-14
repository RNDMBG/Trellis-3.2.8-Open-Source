            package com.google.android.gms.internal;

            import android.util.Log;
            import com.google.ads.AdRequest;
            
            /* JADX INFO: renamed from: com.google.android.gms.internal.cn */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public final class C1002cn {
                /* JADX INFO: renamed from: a */
                public static void m1451a(String str, Throwable th) {
                    if (m1453k(3)) {
                        Log.d(AdRequest.LOGTAG, str, th);
                    }
                }

                /* JADX INFO: renamed from: b */
                public static void m1452b(String str, Throwable th) {
                    if (m1453k(5)) {
                        Log.w(AdRequest.LOGTAG, str, th);
                    }
                }

                /* JADX INFO: renamed from: k */
                public static boolean m1453k(int i) {
                    return (i >= 5 || Log.isLoggable(AdRequest.LOGTAG, i)) && i != 2;
                }

                /* JADX INFO: renamed from: m */
                public static void m1454m(String str) {
                    if (m1453k(3)) {
                        Log.d(AdRequest.LOGTAG, str);
                    }
                }

                /* JADX INFO: renamed from: n */
                public static void m1455n(String str) {
                    if (m1453k(6)) {
                        Log.e(AdRequest.LOGTAG, str);
                    }
                }

                /* JADX INFO: renamed from: o */
                public static void m1456o(String str) {
                    if (m1453k(4)) {
                        Log.i(AdRequest.LOGTAG, str);
                    }
                }

                /* JADX INFO: renamed from: p */
                public static void m1457p(String str) {
                    if (m1453k(2)) {
                        Log.v(AdRequest.LOGTAG, str);
                    }
                }

                /* JADX INFO: renamed from: q */
                public static void m1458q(String str) {
                    if (m1453k(5)) {
                        Log.w(AdRequest.LOGTAG, str);
                    }
                }
            }
