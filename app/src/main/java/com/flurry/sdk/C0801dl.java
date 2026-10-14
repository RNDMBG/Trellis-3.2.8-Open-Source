            package com.flurry.sdk;

            import android.content.Context;
            import android.content.pm.PackageManager;
            import android.os.Handler;
            import android.os.HandlerThread;
            import android.os.Looper;
            
            /* JADX INFO: renamed from: com.flurry.sdk.dl */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 20 */    public class C0801dl {

                /* JADX INFO: renamed from: a */
                private static C0801dl f688a;

                /* JADX INFO: renamed from: b */
                private final Context f689b;

                /* JADX INFO: renamed from: c */
                private final Handler f690c = new Handler(Looper.getMainLooper());

                /* JADX INFO: renamed from: d */
                private final Handler f691d;

                /* JADX INFO: renamed from: a */
/* 21 */        public static synchronized void m733a(Context context) {
                    try {
                        if (f688a == null) {
/* 26 */                    if (context == null) {
/* 27 */                        throw new IllegalArgumentException("Context cannot be null");
                            }
/* 30 */                    f688a = new C0801dl(context);
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }

                /* JADX INFO: renamed from: a */
/* 33 */        public static C0801dl m732a() {
/* 34 */            return f688a;
                }

/* 43 */        private C0801dl(Context context) {
/* 45 */            this.f689b = context.getApplicationContext();
/* 48 */            HandlerThread handlerThread = new HandlerThread("FlurryAgent");
/* 49 */            handlerThread.start();
/* 50 */            this.f691d = new Handler(handlerThread.getLooper());
                }

                /* JADX INFO: renamed from: b */
/* 53 */        public Context m736b() {
/* 54 */            return this.f689b;
                }

                /* JADX INFO: renamed from: c */
/* 57 */        public PackageManager m739c() {
/* 58 */            return this.f689b.getPackageManager();
                }

                /* JADX INFO: renamed from: a */
/* 73 */        public void m734a(Runnable runnable) {
/* 74 */            if (runnable != null) {
/* 78 */                this.f690c.post(runnable);
                    }
                }

                /* JADX INFO: renamed from: a */
/* 81 */        public void m735a(Runnable runnable, long j) {
/* 82 */            if (runnable != null) {
/* 86 */                this.f690c.postDelayed(runnable, j);
                    }
                }

                /* JADX INFO: renamed from: b */
/* 89 */        public void m737b(Runnable runnable) {
/* 90 */            if (runnable != null) {
/* 94 */                this.f690c.removeCallbacks(runnable);
                    }
                }

                /* JADX INFO: renamed from: c */
/* 101 */       public void m740c(Runnable runnable) {
/* 102 */           if (runnable != null) {
/* 106 */               this.f691d.post(runnable);
                    }
                }

                /* JADX INFO: renamed from: b */
/* 109 */       public void m738b(Runnable runnable, long j) {
/* 110 */           if (runnable != null) {
/* 114 */               this.f691d.postDelayed(runnable, j);
                    }
                }

                /* JADX INFO: renamed from: d */
/* 117 */       public void m741d(Runnable runnable) {
/* 118 */           if (runnable != null) {
/* 122 */               this.f691d.removeCallbacks(runnable);
                    }
                }
            }
