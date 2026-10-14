            package com.crashlytics.android;

            import java.util.Date;
            
            /* JADX INFO: renamed from: com.crashlytics.android.P */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 357 */   final class RunnableC0602P implements Runnable {

                /* JADX INFO: renamed from: a */
                private /* synthetic */ Date f162a;

                /* JADX INFO: renamed from: b */
                private /* synthetic */ Thread f163b;

                /* JADX INFO: renamed from: c */
                private /* synthetic */ Throwable f164c;

                /* JADX INFO: renamed from: d */
                private /* synthetic */ C0744v f165d;

/* 358 */       RunnableC0602P(C0744v c0744v, Date date, Thread thread, Throwable th) {
/* 359 */           this.f165d = c0744v;
                    this.f162a = date;
                    this.f163b = thread;
                    this.f164c = th;
                }

                @Override // java.lang.Runnable
/* 361 */       public final void run() {
/* 362 */           if (!C0744v.m390a(this.f165d).get()) {
/* 363 */               C0744v.m414b(this.f165d, this.f162a, this.f163b, this.f164c);
                    }
                }
            }
