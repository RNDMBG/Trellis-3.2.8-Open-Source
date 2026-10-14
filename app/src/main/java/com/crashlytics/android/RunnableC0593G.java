            package com.crashlytics.android;

            import com.crashlytics.android.internal.C0727v;
            
            /* JADX INFO: renamed from: com.crashlytics.android.G */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 1956 */  final class RunnableC0593G implements Runnable {

                /* JADX INFO: renamed from: a */
                private /* synthetic */ Runnable f153a;

/* 1957 */      RunnableC0593G(C0744v c0744v, Runnable runnable) {
/* 1958 */          this.f153a = runnable;
                }

                @Override // java.lang.Runnable
/* 1961 */      public final void run() {
                    try {
/* 1962 */              this.f153a.run();
                    } catch (Exception e) {
/* 1964 */              C0727v.m357a().m365b().mo97a(Crashlytics.TAG, "Failed to execute task.", e);
                    }
                }
            }
