            package com.google.android.gms.internal;

            import android.os.Looper;
            import android.util.Log;
            
            /* JADX INFO: renamed from: com.google.android.gms.internal.db */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public final class C1017db {
                /* JADX INFO: renamed from: a */
                public static void m1523a(boolean z, Object obj) {
                    if (!z) {
                        throw new IllegalStateException(String.valueOf(obj));
                    }
                }

                /* JADX INFO: renamed from: c */
                public static void m1524c(Object obj) {
                    if (obj == null) {
                        throw new IllegalArgumentException("null reference");
                    }
                }

                /* JADX INFO: renamed from: k */
                public static void m1525k(boolean z) {
                    if (!z) {
                        throw new IllegalStateException();
                    }
                }

                /* JADX INFO: renamed from: w */
                public static void m1526w(String str) {
                    if (Looper.getMainLooper().getThread() != Thread.currentThread()) {
                        Log.e("Asserts", "checkMainThread: current thread " + Thread.currentThread() + " IS NOT the main thread " + Looper.getMainLooper().getThread() + "!");
                        throw new IllegalStateException(str);
                    }
                }

                /* JADX INFO: renamed from: x */
                public static void m1527x(String str) {
                    if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
                        Log.e("Asserts", "checkNotMainThread: current thread " + Thread.currentThread() + " IS the main thread " + Looper.getMainLooper().getThread() + "!");
                        throw new IllegalStateException(str);
                    }
                }
            }
