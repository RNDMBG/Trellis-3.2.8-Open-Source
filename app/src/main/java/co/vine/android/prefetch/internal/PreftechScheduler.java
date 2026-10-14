            package co.vine.android.prefetch.internal;

            import co.vine.android.prefetch.PrefetchManager;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public interface PreftechScheduler {
                void cancel(PrefetchManager prefetchManager);

                long getNextSync(long j, PrefetchManager prefetchManager);

                void runNow(PrefetchManager prefetchManager, long j);

                int schedule(PrefetchManager prefetchManager, boolean z);

                void scheduleFailedFetch(PrefetchManager prefetchManager);
            }
