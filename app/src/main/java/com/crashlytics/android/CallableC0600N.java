            package com.crashlytics.android;

            import java.util.Date;
            import java.util.concurrent.Callable;
            
            /* JADX INFO: renamed from: com.crashlytics.android.N */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 305 */   final class CallableC0600N implements Callable<Void> {

                /* JADX INFO: renamed from: a */
                private /* synthetic */ Date f157a;

                /* JADX INFO: renamed from: b */
                private /* synthetic */ Thread f158b;

                /* JADX INFO: renamed from: c */
                private /* synthetic */ Throwable f159c;

                /* JADX INFO: renamed from: d */
                private /* synthetic */ C0744v f160d;

/* 306 */       CallableC0600N(C0744v c0744v, Date date, Thread thread, Throwable th) {
/* 307 */           this.f160d = c0744v;
                    this.f157a = date;
                    this.f158b = thread;
                    this.f159c = th;
                }

                @Override // java.util.concurrent.Callable
/* 306 */       public final /* synthetic */ Void call() throws Exception {
/* 307 */           C0744v.m401a(this.f160d, this.f157a, this.f158b, this.f159c);
                    return null;
                }
            }
