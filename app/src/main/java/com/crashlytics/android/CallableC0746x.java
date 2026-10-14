            package com.crashlytics.android;

            import java.util.concurrent.Callable;
            
            /* JADX INFO: renamed from: com.crashlytics.android.x */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 371 */   final class CallableC0746x implements Callable<Void> {

                /* JADX INFO: renamed from: a */
                private /* synthetic */ long f518a;

                /* JADX INFO: renamed from: b */
                private /* synthetic */ String f519b;

                /* JADX INFO: renamed from: c */
                private /* synthetic */ C0744v f520c;

/* 372 */       CallableC0746x(C0744v c0744v, long j, String str) {
/* 373 */           this.f520c = c0744v;
                    this.f518a = j;
                    this.f519b = str;
                }

                @Override // java.util.concurrent.Callable
/* 372 */       public final /* synthetic */ Void call() throws Exception {
/* 373 */           if (C0744v.m390a(this.f520c).get()) {
                        return null;
                    }
                    if (C0744v.m417d(this.f520c) == null) {
                        C0744v.m418e(this.f520c);
                    }
                    C0744v c0744v = this.f520c;
                    C0744v.m400a(C0744v.m417d(this.f520c), 65536, this.f518a, this.f519b);
                    return null;
                }
            }
