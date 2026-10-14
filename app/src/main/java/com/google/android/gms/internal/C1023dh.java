            package com.google.android.gms.internal;

            import android.util.Log;
            
            
            
            public final class C1023dh {

                
                private final String f1713li;

                public C1023dh(String str) {
                    this.f1713li = (String) C1028dm.m1605e(str);
                }

                
                public void m1574a(String str, String str2, Throwable th) {
                    if (m1579x(6)) {
                        Log.e(str, str2, th);
                    }
                }

                
                public void m1575b(String str, String str2) {
                    if (m1579x(3)) {
                        Log.d(str, str2);
                    }
                }

                
                public void m1576c(String str, String str2) {
                    if (m1579x(5)) {
                        Log.w(str, str2);
                    }
                }

                
                public void m1577d(String str, String str2) {
                    if (m1579x(6)) {
                        Log.e(str, str2);
                    }
                }

                
                public void m1578e(String str, String str2) {
                    if (m1579x(4)) {
                    }
                }

                
                public boolean m1579x(int i) {
                    return Log.isLoggable(this.f1713li, i);
                }
            }
