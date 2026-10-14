            package com.google.android.gms.internal;

            import android.os.Looper;
            
            /* JADX INFO: renamed from: com.google.android.gms.internal.dm */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public final class C1028dm {
                /* JADX INFO: renamed from: a */
                public static <T> T m1601a(T t, Object obj) {
                    if (t == null) {
                        throw new NullPointerException(String.valueOf(obj));
                    }
                    return t;
                }

                /* JADX INFO: renamed from: a */
                public static void m1602a(boolean z, Object obj) {
                    if (!z) {
                        throw new IllegalStateException(String.valueOf(obj));
                    }
                }

                /* JADX INFO: renamed from: a */
                public static void m1603a(boolean z, String str, Object... objArr) {
                    if (!z) {
                        throw new IllegalArgumentException(String.format(str, objArr));
                    }
                }

                /* JADX INFO: renamed from: b */
                public static void m1604b(boolean z, Object obj) {
                    if (!z) {
                        throw new IllegalArgumentException(String.valueOf(obj));
                    }
                }

                /* JADX INFO: renamed from: e */
                public static <T> T m1605e(T t) {
                    if (t == null) {
                        throw new NullPointerException("null reference");
                    }
                    return t;
                }

                /* JADX INFO: renamed from: k */
                public static void m1606k(boolean z) {
                    if (!z) {
                        throw new IllegalStateException();
                    }
                }

                /* JADX INFO: renamed from: m */
                public static void m1607m(boolean z) {
                    if (!z) {
                        throw new IllegalArgumentException();
                    }
                }

                /* JADX INFO: renamed from: w */
                public static void m1608w(String str) {
                    if (Looper.myLooper() != Looper.getMainLooper()) {
                        throw new IllegalStateException(str);
                    }
                }

                /* JADX INFO: renamed from: x */
                public static void m1609x(String str) {
                    if (Looper.myLooper() == Looper.getMainLooper()) {
                        throw new IllegalStateException(str);
                    }
                }
            }
