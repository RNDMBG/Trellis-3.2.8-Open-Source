            package com.crashlytics.android;

            import java.util.concurrent.CountDownLatch;
            
            /* JADX INFO: renamed from: com.crashlytics.android.u */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 983 */   final class C0743u {

                /* JADX INFO: renamed from: a */
                private boolean f491a;

                /* JADX INFO: renamed from: b */
                private final CountDownLatch f492b;

/* 984 */       private C0743u(Crashlytics crashlytics) {
/* 986 */           this.f491a = false;
/* 987 */           this.f492b = new CountDownLatch(1);
                }

/* 984 */       /* synthetic */ C0743u(Crashlytics crashlytics, byte b) {
/* 985 */           this(crashlytics);
                }

                /* JADX INFO: renamed from: a */
/* 989 */       final void m375a(boolean z) {
/* 990 */           this.f491a = z;
/* 991 */           this.f492b.countDown();
                }

                /* JADX INFO: renamed from: a */
/* 994 */       final boolean m376a() {
/* 995 */           return this.f491a;
                }

                /* JADX INFO: renamed from: b */
/* 999 */       final void m377b() {
                    try {
/* 1000 */              this.f492b.await();
                    } catch (InterruptedException e) {
                    }
                }
            }
