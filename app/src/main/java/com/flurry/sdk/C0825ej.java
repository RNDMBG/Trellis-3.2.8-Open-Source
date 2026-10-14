            package com.flurry.sdk;

            import java.util.concurrent.PriorityBlockingQueue;
            import java.util.concurrent.TimeUnit;
            
            /* JADX INFO: renamed from: com.flurry.sdk.ej */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 20 */    public final class C0825ej extends C0819ed<C0826ek> {

                /* JADX INFO: renamed from: a */
                private static C0825ej f787a = null;

                /* JADX INFO: renamed from: a */
/* 21 */        public static synchronized C0825ej m856a() {
                    try {
                        if (f787a == null) {
/* 23 */                    f787a = new C0825ej();
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
/* 26 */            return f787a;
                }

/* 29 */        protected C0825ej() {
/* 30 */            super(C0825ej.class.getName(), 0, 5, 5000L, TimeUnit.MILLISECONDS, new PriorityBlockingQueue(11, new C0817eb()));
                }
            }
