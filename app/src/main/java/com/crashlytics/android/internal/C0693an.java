            package com.crashlytics.android.internal;

            import com.crashlytics.android.Crashlytics;
            import java.util.Locale;
            import java.util.concurrent.ExecutorService;
            import java.util.concurrent.TimeUnit;
            
            /* JADX INFO: renamed from: com.crashlytics.android.internal.an */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 57 */    final class C0693an extends AbstractRunnableC0680aa {

                /* JADX INFO: renamed from: a */
                private /* synthetic */ String f381a;

                /* JADX INFO: renamed from: b */
                private /* synthetic */ ExecutorService f382b;

                /* JADX INFO: renamed from: c */
                private /* synthetic */ long f383c;

                /* JADX INFO: renamed from: d */
                private /* synthetic */ TimeUnit f384d;

/* 58 */        C0693an(String str, ExecutorService executorService, long j, TimeUnit timeUnit) {
/* 59 */            this.f381a = str;
                    this.f382b = executorService;
                    this.f383c = j;
                    this.f384d = timeUnit;
                }

                @Override // com.crashlytics.android.internal.AbstractRunnableC0680aa
                /* JADX INFO: renamed from: a */
/* 62 */        public final void mo38a() {
                    try {
/* 63 */                C0727v.m357a().m365b().mo96a(Crashlytics.TAG, "Executing shutdown hook for " + this.f381a);
/* 64 */                this.f382b.shutdown();
/* 65 */                if (!this.f382b.awaitTermination(this.f383c, this.f384d)) {
/* 66 */                    C0727v.m357a().m365b().mo96a(Crashlytics.TAG, this.f381a + " did not shut down in the allocated time. Requesting immediate shutdown.");
/* 68 */                    this.f382b.shutdownNow();
                        }
                    } catch (InterruptedException e) {
                        C0727v.m357a().m365b().mo96a(Crashlytics.TAG, String.format(Locale.US, "Interrupted while waiting for %s to shut down. Requesting immediate shutdown.", this.f381a));
/* 74 */                this.f382b.shutdownNow();
                    }
                }
            }
