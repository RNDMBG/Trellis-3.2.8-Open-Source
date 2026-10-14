            package com.crashlytics.android.internal;

            import com.crashlytics.android.Crashlytics;
            import java.util.Locale;
            import java.util.concurrent.ExecutorService;
            import java.util.concurrent.TimeUnit;
            
            
            
    final class C0693an extends AbstractRunnableC0680aa {

                
                private  String f381a;

                
                private  ExecutorService f382b;

                
                private  long f383c;

                
                private  TimeUnit f384d;

        C0693an(String str, ExecutorService executorService, long j, TimeUnit timeUnit) {
            this.f381a = str;
                    this.f382b = executorService;
                    this.f383c = j;
                    this.f384d = timeUnit;
                }

                @Override 
                
        public final void mo38a() {
                    try {
                C0727v.m357a().m365b().mo96a(Crashlytics.TAG, "Executing shutdown hook for " + this.f381a);
                this.f382b.shutdown();
                if (!this.f382b.awaitTermination(this.f383c, this.f384d)) {
                    C0727v.m357a().m365b().mo96a(Crashlytics.TAG, this.f381a + " did not shut down in the allocated time. Requesting immediate shutdown.");
                    this.f382b.shutdownNow();
                        }
                    } catch (InterruptedException e) {
                        C0727v.m357a().m365b().mo96a(Crashlytics.TAG, String.format(Locale.US, "Interrupted while waiting for %s to shut down. Requesting immediate shutdown.", this.f381a));
                this.f382b.shutdownNow();
                    }
                }
            }
