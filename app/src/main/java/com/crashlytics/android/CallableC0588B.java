            package com.crashlytics.android;

            import com.crashlytics.android.internal.C0727v;
            import java.util.concurrent.Callable;
            
            /* JADX INFO: renamed from: com.crashlytics.android.B */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 462 */   final class CallableC0588B implements Callable<Boolean> {

                /* JADX INFO: renamed from: a */
                private /* synthetic */ C0744v f126a;

/* 463 */       CallableC0588B(C0744v c0744v) {
/* 464 */           this.f126a = c0744v;
                }

                @Override // java.util.concurrent.Callable
/* 463 */       public final /* synthetic */ Boolean call() throws Exception {
/* 464 */           return m5a();
                }

                /* JADX INFO: renamed from: a */
/* 467 */       private Boolean m5a() throws Exception {
                    try {
/* 468 */               boolean zDelete = C0744v.m419f(this.f126a).delete();
/* 469 */               C0727v.m357a().m365b().mo96a(Crashlytics.TAG, "Initialization marker file removed: " + zDelete);
/* 470 */               return Boolean.valueOf(zDelete);
                    } catch (Exception e) {
/* 472 */               C0727v.m357a().m365b().mo97a(Crashlytics.TAG, "Problem encountered deleting Crashlytics initialization marker.", e);
/* 473 */               return false;
                    }
                }
            }
