            package com.google.android.gms.internal;

            import android.util.Log;
            import com.google.ads.AdRequest;
            
            
            
            public final class C1002cn {
                
                public static void m1451a(String str, Throwable th) {
                    if (m1453k(3)) {
                        Log.d(AdRequest.LOGTAG, str, th);
                    }
                }

                
                public static void m1452b(String str, Throwable th) {
                    if (m1453k(5)) {
                        Log.w(AdRequest.LOGTAG, str, th);
                    }
                }

                
                public static boolean m1453k(int i) {
                    return (i >= 5 || Log.isLoggable(AdRequest.LOGTAG, i)) && i != 2;
                }

                
                public static void m1454m(String str) {
                    if (m1453k(3)) {
                        Log.d(AdRequest.LOGTAG, str);
                    }
                }

                
                public static void m1455n(String str) {
                    if (m1453k(6)) {
                        Log.e(AdRequest.LOGTAG, str);
                    }
                }

                
                public static void m1456o(String str) {
                    if (m1453k(4)) {
                        Log.i(AdRequest.LOGTAG, str);
                    }
                }

                
                public static void m1457p(String str) {
                    if (m1453k(2)) {
                        Log.v(AdRequest.LOGTAG, str);
                    }
                }

                
                public static void m1458q(String str) {
                    if (m1453k(5)) {
                        Log.w(AdRequest.LOGTAG, str);
                    }
                }
            }
