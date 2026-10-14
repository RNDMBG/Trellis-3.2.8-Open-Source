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
            
            
    public class AlarmPrefetchScheduler implements PreftechScheduler {
                private final SLogger mLogger = PrefetchManager.getPrefetchLogger();

                @Override 
        public int schedule(PrefetchManager prefetchManager, boolean canSyncSoon) {
            Account account = prefetchManager.getSyncableAccount();
            if (account == null) {
                        return -1;
                    }
            cancel(prefetchManager);
            long timeMs = prefetchManager.getNextFetchDelay(canSyncSoon);
            schedule(prefetchManager.getApplicationContext(), timeMs);
            if (SLog.sLogsOn) {
                this.mLogger.m506i("Scheduled next sync to be {}s later.", Long.valueOf(timeMs / 1000));
                FileLogger.getLogger(LogReaderTask.PREFETCH_LOG).write("Next sync scheduled at {}s", Long.valueOf(timeMs));
                    }
            return (int) (timeMs / 1000);
                }

        private void schedule(Context context, long timeMs) {
            AlarmManager am = (AlarmManager) context.getSystemService("alarm");
            am.set(2, SystemClock.elapsedRealtime() + timeMs, getPendingIntent(context, false));
                }

        private PendingIntent getPendingIntent(Context context, boolean manual) {
            return PendingIntent.getService(context, 0, VineService.getPrefetchIntent(context, manual), 0);
                }

                @Override 
        public void cancel(PrefetchManager prefetchManager) {
            Context context = prefetchManager.getApplicationContext();
            AlarmManager am = (AlarmManager) context.getSystemService("alarm");
            am.cancel(getPendingIntent(context, false));
            prefetchManager.onCancelled();
                }

                @Override 
        public void runNow(PrefetchManager prefetchManager, long delayMs) {
            Context context = prefetchManager.getApplicationContext();
            context.startService(VineService.getPrefetchIntent(context, true));
                }

                @Override 
        public long getNextSync(long expected, PrefetchManager manager) {
            return expected;
                }

                @Override 
        public void scheduleFailedFetch(PrefetchManager prefetchManager) {
            schedule(prefetchManager.getApplicationContext(), 300000L);
                }
            }
