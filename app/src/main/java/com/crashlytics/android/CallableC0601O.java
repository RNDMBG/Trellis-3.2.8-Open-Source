            package com.crashlytics.android;

            import java.util.concurrent.Callable;
            
            /* JADX INFO: renamed from: com.crashlytics.android.O */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 336 */   final class CallableC0601O implements Callable<Boolean> {

                /* JADX INFO: renamed from: a */
                private /* synthetic */ C0744v f161a;

/* 337 */       CallableC0601O(C0744v c0744v) {
/* 338 */           this.f161a = c0744v;
                }

                @Override // java.util.concurrent.Callable
/* 337 */       public final /* synthetic */ Boolean call() throws Exception {
/* 338 */           if (C0744v.m390a(this.f161a).get()) {
                        return false;
                    }
                    C0744v.m413b(this.f161a);
                    C0744v.m415c(this.f161a);
                    return true;
                }
            }
