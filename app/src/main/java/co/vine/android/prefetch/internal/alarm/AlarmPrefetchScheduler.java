            package co.vine.android.prefetch.internal.alarm;

            import android.accounts.Account;
            import android.app.AlarmManager;
            import android.app.PendingIntent;
            import android.content.Context;
            import android.os.SystemClock;
            import co.vine.android.plugin.LogReaderTask;
            import co.vine.android.prefetch.PrefetchManager;
            import co.vine.android.prefetch.internal.PreftechScheduler;
            import co.vine.android.service.VineService;
            import co.vine.android.util.FileLogger;
            import com.edisonwang.android.slog.SLog;
            import com.edisonwang.android.slog.SLogger;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 25 */    public class AlarmPrefetchScheduler implements PreftechScheduler {
                private final SLogger mLogger = PrefetchManager.getPrefetchLogger();

                @Override // co.vine.android.prefetch.internal.PreftechScheduler
/* 26 */        public int schedule(PrefetchManager prefetchManager, boolean canSyncSoon) {
/* 27 */            Account account = prefetchManager.getSyncableAccount();
/* 28 */            if (account == null) {
                        return -1;
                    }
/* 29 */            cancel(prefetchManager);
/* 30 */            long timeMs = prefetchManager.getNextFetchDelay(canSyncSoon);
/* 31 */            schedule(prefetchManager.getApplicationContext(), timeMs);
/* 32 */            if (SLog.sLogsOn) {
/* 33 */                this.mLogger.m506i("Scheduled next sync to be {}s later.", Long.valueOf(timeMs / 1000));
/* 34 */                FileLogger.getLogger(LogReaderTask.PREFETCH_LOG).write("Next sync scheduled at {}s", Long.valueOf(timeMs));
                    }
/* 37 */            return (int) (timeMs / 1000);
                }

/* 42 */        private void schedule(Context context, long timeMs) {
/* 43 */            AlarmManager am = (AlarmManager) context.getSystemService("alarm");
/* 44 */            am.set(2, SystemClock.elapsedRealtime() + timeMs, getPendingIntent(context, false));
                }

/* 47 */        private PendingIntent getPendingIntent(Context context, boolean manual) {
/* 48 */            return PendingIntent.getService(context, 0, VineService.getPrefetchIntent(context, manual), 0);
                }

                @Override // co.vine.android.prefetch.internal.PreftechScheduler
/* 52 */        public void cancel(PrefetchManager prefetchManager) {
/* 53 */            Context context = prefetchManager.getApplicationContext();
/* 54 */            AlarmManager am = (AlarmManager) context.getSystemService("alarm");
/* 55 */            am.cancel(getPendingIntent(context, false));
/* 56 */            prefetchManager.onCancelled();
                }

                @Override // co.vine.android.prefetch.internal.PreftechScheduler
/* 60 */        public void runNow(PrefetchManager prefetchManager, long delayMs) {
/* 61 */            Context context = prefetchManager.getApplicationContext();
/* 62 */            context.startService(VineService.getPrefetchIntent(context, true));
                }

                @Override // co.vine.android.prefetch.internal.PreftechScheduler
/* 66 */        public long getNextSync(long expected, PrefetchManager manager) {
/* 67 */            return expected;
                }

                @Override // co.vine.android.prefetch.internal.PreftechScheduler
/* 71 */        public void scheduleFailedFetch(PrefetchManager prefetchManager) {
/* 72 */            schedule(prefetchManager.getApplicationContext(), 300000L);
                }
            }
