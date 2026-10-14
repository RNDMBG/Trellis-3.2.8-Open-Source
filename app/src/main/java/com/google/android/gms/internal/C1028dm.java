            package com.google.android.gms.internal;

            import android.os.Looper;
            
            
            
            public final class C1028dm {
                
                public static <T> T m1601a(T t, Object obj) {
                    if (t == null) {
                        throw new NullPointerException(String.valueOf(obj));
                    }
                    return t;
                }

                
                public static void m1602a(boolean z, Object obj) {
                    if (!z) {
                        throw new IllegalStateException(String.valueOf(obj));
                    }
                }

                
                public static void m1603a(boolean z, String str, Object... objArr) {
                    if (!z) {
                        throw new IllegalArgumentException(String.format(str, objArr));
                    }
                }

                
                public static void m1604b(boolean z, Object obj) {
                    if (!z) {
                        throw new IllegalArgumentException(String.valueOf(obj));
                    }
                }

                
                public static <T> T m1605e(T t) {
                    if (t == null) {
                        throw new NullPointerException("null reference");
                    }
                    return t;
                }

                
                public static void m1606k(boolean z) {
                    if (!z) {
                        throw new IllegalStateException();
                    }
                }

                
                public static void m1607m(boolean z) {
                    if (!z) {
                        throw new IllegalArgumentException();
                    }
                }

                
                public static void m1608w(String str) {
                    if (Looper.myLooper() != Looper.getMainLooper()) {
                        throw new IllegalStateException(str);
                    }
                }

                
                public static void m1609x(String str) {
                    if (Looper.myLooper() == Looper.getMainLooper()) {
                        throw new IllegalStateException(str);
                    }
                }
            }
