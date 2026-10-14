            package com.google.android.gms.internal;

            import android.util.Log;
            
            /* JADX INFO: renamed from: com.google.android.gms.internal.dh */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public final class C1023dh {

                /* JADX INFO: renamed from: li */
                private final String f1713li;

                public C1023dh(String str) {
                    this.f1713li = (String) C1028dm.m1605e(str);
                }

                /* JADX INFO: renamed from: a */
                public void m1574a(String str, String str2, Throwable th) {
                    if (m1579x(6)) {
                        Log.e(str, str2, th);
                    }
                }

                /* JADX INFO: renamed from: b */
                public void m1575b(String str, String str2) {
                    if (m1579x(3)) {
                        Log.d(str, str2);
                    }
                }

                /* JADX INFO: renamed from: c */
                public void m1576c(String str, String str2) {
                    if (m1579x(5)) {
                        Log.w(str, str2);
                    }
                }

                /* JADX INFO: renamed from: d */
                public void m1577d(String str, String str2) {
                    if (m1579x(6)) {
                        Log.e(str, str2);
                    }
                }

                /* JADX INFO: renamed from: e */
                public void m1578e(String str, String str2) {
                    if (m1579x(4)) {
                    }
                }

                /* JADX INFO: renamed from: x */
                public boolean m1579x(int i) {
                    return Log.isLoggable(this.f1713li, i);
                }
            }
