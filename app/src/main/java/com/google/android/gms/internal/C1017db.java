            package com.google.android.gms.internal;

            import android.os.Looper;
            import android.util.Log;
            
            
            
            public final class C1017db {
                
                public static void m1523a(boolean z, Object obj) {
                    if (!z) {
                        throw new IllegalStateException(String.valueOf(obj));
                    }
                }

                
                public static void m1524c(Object obj) {
                    if (obj == null) {
                        throw new IllegalArgumentException("null reference");
                    }
                }

                
                public static void m1525k(boolean z) {
                    if (!z) {
                        throw new IllegalStateException();
                    }
                }

                
                public static void m1526w(String str) {
                    if (Looper.getMainLooper().getThread() != Thread.currentThread()) {
                        Log.e("Asserts", "checkMainThread: current thread " + Thread.currentThread() + " IS NOT the main thread " + Looper.getMainLooper().getThread() + "!");
                        throw new IllegalStateException(str);
                    }
                }

                
                public static void m1527x(String str) {
                    if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
                        Log.e("Asserts", "checkNotMainThread: current thread " + Thread.currentThread() + " IS the main thread " + Looper.getMainLooper().getThread() + "!");
                        throw new IllegalStateException(str);
                    }
                }
            }
