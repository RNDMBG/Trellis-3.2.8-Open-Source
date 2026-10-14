            package com.crashlytics.android;

            import java.util.concurrent.Callable;
            
            /* JADX INFO: renamed from: com.crashlytics.android.z */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 434 */   final class CallableC0748z implements Callable<Void> {

                /* JADX INFO: renamed from: a */
                private /* synthetic */ C0744v f523a;

/* 435 */       CallableC0748z(C0744v c0744v) {
/* 436 */           this.f523a = c0744v;
                }

                @Override // java.util.concurrent.Callable
/* 435 */       public final /* synthetic */ Void call() throws Exception {
/* 436 */           if (this.f523a.m440g()) {
                        return null;
                    }
                    C0744v.m415c(this.f523a);
                    return null;
                }
            }
