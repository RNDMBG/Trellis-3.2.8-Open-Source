            package co.vine.android.prefetch.internal.job;

            import co.vine.android.prefetch.PrefetchManager;
            import co.vine.android.prefetch.internal.PreftechScheduler;
            
            
    public class JobPrefretechScheduler implements PreftechScheduler {
                @Override 
        public int schedule(PrefetchManager prefetchManager, boolean canSyncSoon) {
            return -1;
                }

                @Override 
        public void cancel(PrefetchManager prefetchManager) {
                }

                @Override 
        public void runNow(PrefetchManager prefetchManager, long delayMs) {
                }

                @Override 
        public long getNextSync(long expected, PrefetchManager manager) {
            return -1L;
                }

                @Override 
        public void scheduleFailedFetch(PrefetchManager prefetchManager) {
                }
            }
