            package com.crashlytics.android;

            import java.util.concurrent.Callable;
            
            /* JADX INFO: renamed from: com.crashlytics.android.C */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 478 */   final class CallableC0589C implements Callable<Boolean> {

                /* JADX INFO: renamed from: a */
                private /* synthetic */ C0744v f127a;

/* 479 */       CallableC0589C(C0744v c0744v) {
/* 480 */           this.f127a = c0744v;
                }

                @Override // java.util.concurrent.Callable
/* 479 */       public final /* synthetic */ Boolean call() throws Exception {
/* 480 */           return Boolean.valueOf(C0744v.m419f(this.f127a).exists());
                }
            }
