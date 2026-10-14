            package co.vine.android.prefetch.internal.job;

            import co.vine.android.prefetch.PrefetchManager;
            import co.vine.android.prefetch.internal.PreftechScheduler;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 11 */    public class JobPrefretechScheduler implements PreftechScheduler {
                @Override // co.vine.android.prefetch.internal.PreftechScheduler
/* 12 */        public int schedule(PrefetchManager prefetchManager, boolean canSyncSoon) {
/* 13 */            return -1;
                }

                @Override // co.vine.android.prefetch.internal.PreftechScheduler
/* 17 */        public void cancel(PrefetchManager prefetchManager) {
                }

                @Override // co.vine.android.prefetch.internal.PreftechScheduler
/* 22 */        public void runNow(PrefetchManager prefetchManager, long delayMs) {
                }

                @Override // co.vine.android.prefetch.internal.PreftechScheduler
/* 26 */        public long getNextSync(long expected, PrefetchManager manager) {
/* 27 */            return -1L;
                }

                @Override // co.vine.android.prefetch.internal.PreftechScheduler
/* 32 */        public void scheduleFailedFetch(PrefetchManager prefetchManager) {
                }
            }
