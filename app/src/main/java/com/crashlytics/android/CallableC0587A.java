            package com.crashlytics.android;

            import com.crashlytics.android.internal.C0727v;
            import java.util.concurrent.Callable;
            
            /* JADX INFO: renamed from: com.crashlytics.android.A */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 446 */   final class CallableC0587A implements Callable<Void> {

                /* JADX INFO: renamed from: a */
                private /* synthetic */ C0744v f125a;

/* 447 */       CallableC0587A(C0744v c0744v) {
/* 448 */           this.f125a = c0744v;
                }

                @Override // java.util.concurrent.Callable
/* 447 */       public final /* synthetic */ Void call() throws Exception {
/* 448 */           C0744v.m419f(this.f125a).createNewFile();
                    C0727v.m357a().m365b().mo96a(Crashlytics.TAG, "Initialization marker file created.");
                    return null;
                }
            }
