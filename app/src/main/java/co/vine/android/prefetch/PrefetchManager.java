            package co.vine.android.prefetch;

            import android.accounts.Account;
            import android.content.Context;
            import android.content.Intent;
            import android.content.IntentFilter;
            import android.content.SharedPreferences;
            import android.os.Build;
            import android.os.Bundle;
            import android.os.Handler;
            import android.os.Looper;
            import android.support.annotation.Nullable;
            import co.vine.android.client.AppController;
            import co.vine.android.client.Session;
            import co.vine.android.client.VineAccountHelper;
            import co.vine.android.network.NetworkOperation;
            import co.vine.android.network.UrlCachePolicy;
            import co.vine.android.plugin.LogReaderTask;
            import co.vine.android.prefetch.internal.PreftechScheduler;
            import co.vine.android.prefetch.internal.alarm.AlarmPrefetchScheduler;
            import co.vine.android.service.VineService;
            import co.vine.android.util.ClientFlagsHelper;
            import co.vine.android.util.CrashUtil;
            import co.vine.android.util.CrossConstants;
            import co.vine.android.util.FileLogger;
            import co.vine.android.util.SystemUtil;
            import co.vine.android.util.UserDataHelper;
            import co.vine.android.util.Util;
            import co.vine.android.util.analytics.BehaviorManager;
            import co.vine.android.util.analytics.FlurryUtils;
            import com.edisonwang.android.slog.MessageFormatter;
            import com.edisonwang.android.slog.SLog;
            import com.edisonwang.android.slog.SLogger;
            
            
    public class PrefetchManager {
                public static final boolean DEFAULT_PREFETCH_ENABLED = false;
                private static final String EXTRA_SECONDS_FROM_NOW = "seconds_from_now";
                public static final long FAIL_DELAY_INTERVAL = 300000;
                private static final boolean JOB_SCHEDULER_ENABLED = false;
                public static final int MAX_TIMELINES_TO_FETCH = 10;
                public static final float MAX_TOLERANCE = 0.4f;
                public static final float MINIUM_DELAY = 0.3f;
                private static final long MIN_INTERVAL = 60000;
                private static final String PREF_CACHE_COUNT = "cache_count";
                private static final String PREF_CHARGING_REQUIRED = "Prefetch_charging_required";
                private static final String PREF_CURRENT_START = "Prefetch_current_start";
                private static final String PREF_ENABLED = "Prefetch_enabled";
                private static final String PREF_FETECHED_COUNT = "fetched_count";
                private static final String PREF_INTERVAL = "Prefetch_interval";
                private static final String PREF_LAST_START = "Prefetch_last_start";
                private static final String PREF_LAST_SYNC = "Prefetch_last_sync";
                private static final String PREF_MISS_COUNT = "miss_count";
                private static final String PREF_NAME = "Prefetch_";
                private static final String PREF_NEXT_EXPECTED = "Prefetch_next_expected";
                private static final String PREF_ROAMING_ALLOWED = "Prefetch_roaming_allowed";
                private static final String PREF_WIFI_ONLY = "Prefetch_wifi_only";
                private static final String STATE_CHANGE_END = "co.vine.android.prefetch.end";
                private static final String STATE_CHANGE_START = "co.vine.android.prefetch.start";
                private static final String STATE_SCHEDULE_CHANGED = "co.vine.android.prefetch.schedule";
                private static PrefetchManager sInstance;
                private final Context mAppContext;
                private final AppController mAppController;
                private final FileLogger mFileLogger;
                private final PreftechScheduler mImpl;
                private final SharedPreferences mPref;
                private final UserDataHelper mUserDataHelper;
                public static final long[] INTERVALS = {7200000, 14400000, 28800000};
                public static final long DEFAULT_INTERVAL = INTERVALS[0];
                private final SLogger mLogger = getPrefetchLogger();
                private final Handler mHandler = new Handler(Looper.getMainLooper());

        static  PreftechScheduler access$000(PrefetchManager x0) {
            return x0.mImpl;
                }

        public static synchronized PrefetchManager getInstance(Context context) {
                    try {
                        if (sInstance == null) {
                    sInstance = new PrefetchManager(context);
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
           return sInstance;
                }

       private PrefetchManager(Context context) {
           this.mAppContext = context;
           this.mAppController = AppController.getInstance(context);
           this.mUserDataHelper = new UserDataHelper(context);
           this.mPref = Util.getSharedPrefs(context, "prefetchStat");
                    this.mFileLogger = FileLogger.getLogger(SLog.sLogsOn ? LogReaderTask.PREFETCH_LOG : null);
           if (Build.VERSION.SDK_INT >= 21) {
                    }
           this.mImpl = new AlarmPrefetchScheduler();
                }

       public long getNextFetchDelay(boolean canSyncSoon) {
           long lastSync = getLastSync();
           long interval = getInterval();
           long requestTime = System.currentTimeMillis();
           long[] timeTuple = findStartAndEndTimes(lastSync, interval, requestTime);
           if (timeTuple == null) {
               return 0L;
                    }
           long start = timeTuple[0];
           long end = timeTuple[1];
           long searchedTime = BehaviorManager.getInstance(this.mAppContext).searchForBestDelay(start, end);
           String log = MessageFormatter.toStringMessage("Search with interval {}, start {}, end {}, searched {}, took {}ms.", Long.valueOf(interval), Long.valueOf((start - requestTime) / 1000), Long.valueOf((end - requestTime) / 1000), Long.valueOf((searchedTime - requestTime) / 1000), Long.valueOf(System.currentTimeMillis() - requestTime));
           this.mFileLogger.write(log, new Object[0]);
           return Math.max(searchedTime - requestTime, 60000L);
                }

       public boolean isClientPrefetchEnabled() {
           return ClientFlagsHelper.prefetchEnabled(this.mAppContext);
                }

       static long[] findStartAndEndTimes(long lastSync, long interval, long requestTime) {
                    if (lastSync + interval + 0.4f < requestTime) {
               return null;
                    }
           long midpoint = lastSync + interval;
           long minStart = lastSync + getMinimumDelay(interval);
           long start = Math.max(midpoint - ((long) (0.4f * interval)), minStart);
           long end = Math.max(((long) (0.8f * interval)) + start, ((long) (0.4f * interval)) + midpoint);
           return new long[]{start, end};
                }

       public static long getMinimumDelay(long interval) {
           return (long) (interval * 0.3f);
                }

       public void onPostFetchOperationComplete(boolean prefetch, NetworkOperation.NetworkOperationResult lastResult, UrlCachePolicy resolvedCachePolicy) {
                    switch (lastResult) {
                        case CACHED:
                   if (!prefetch) {
                       long count = this.mPref.getLong(PREF_CACHE_COUNT, 0L);
                       this.mPref.edit().putLong(PREF_CACHE_COUNT, count + 1).apply();
                            }
                            break;
                        case NETWORK:
                   if (prefetch) {
                       long count2 = this.mPref.getLong(PREF_FETECHED_COUNT, 0L);
                       this.mPref.edit().putLong(PREF_FETECHED_COUNT, count2 + 1).apply();
                            } else if (resolvedCachePolicy.mCachedResponseAllowed) {
                       long count3 = this.mPref.getLong(PREF_MISS_COUNT, 0L);
                       this.mPref.edit().putLong(PREF_MISS_COUNT, count3 + 1).apply();
                            }
                            break;
                        case FAILURE:
                   if (!prefetch && resolvedCachePolicy.mCachedResponseAllowed) {
                       long count4 = this.mPref.getLong(PREF_MISS_COUNT, 0L);
                       this.mPref.edit().putLong(PREF_MISS_COUNT, count4 + 1).apply();
                                break;
                            }
                            break;
                    }
                }

       public String[] getStats() {
           long count = this.mPref.getLong(PREF_MISS_COUNT, 0L);
           String[] stats = {"Total fetched " + count + " when sync happened.", "Was able to save " + count + " when loading.", "Had to fetch " + count + " when loading."};
           long count2 = this.mPref.getLong(PREF_CACHE_COUNT, 0L);
           long count3 = this.mPref.getLong(PREF_FETECHED_COUNT, 0L);
           return stats;
                }

       public static boolean validateNextRunTime(long nextRun, long interval, long lastSync, long requestTime) {
           long nextRunTime = requestTime + nextRun;
                    return ((float) nextRunTime) <= ((float) (lastSync + interval)) + (((float) interval) * 0.4f) && ((float) nextRunTime) >= ((float) (lastSync + interval)) - (((float) interval) * 0.4f);
                }

       public long getNextSync() {
           return this.mImpl.getNextSync(this.mPref.getLong(PREF_NEXT_EXPECTED, -2L), this);
                }

       public void onDeviceBoot() {
           long currentStart = getCurrentSyncStartTime();
                    if (currentStart != -1 && System.currentTimeMillis() - currentStart > 86400000) {
               setCurrentSyncStart(-1L);
                    }
           scheduleNextPrefetch(true);
                }

       public void onCancelled() {
           this.mPref.edit().putLong(PREF_NEXT_EXPECTED, -2L).apply();
                }

       public static class PrefetchFilter extends IntentFilter {
           public PrefetchFilter() {
               addAction(PrefetchManager.STATE_CHANGE_START);
               addAction(PrefetchManager.STATE_CHANGE_END);
               addAction(PrefetchManager.STATE_SCHEDULE_CHANGED);
                    }

           public boolean isStart(String bcAction) {
               return PrefetchManager.STATE_CHANGE_START.equals(bcAction);
                    }

           public boolean isEnd(String bcAction) {
               return PrefetchManager.STATE_CHANGE_END.equals(bcAction);
                    }

           public int getScheduledTimeInSeconds(Bundle extras) {
               if (extras != null) {
                            return extras.getInt(PrefetchManager.EXTRA_SECONDS_FROM_NOW, -2);
                        }
                        return -2;
                    }
                }

       public static PrefetchFilter getStateChangeFilter() {
           return new PrefetchFilter();
                }

       public static SLogger getPrefetchLogger() {
           return SLogger.getLogger(VineService.EXTRA_PREFETCH);
                }

       public void scheduleNextPrefetch(boolean canSyncSoon) {
           if (isClientPrefetchEnabled()) {
               int next = this.mImpl.schedule(this, canSyncSoon);
               if (next > -1) {
                   int value = next * 1000;
                   this.mPref.edit().putLong(PREF_NEXT_EXPECTED, ((long) value) + System.currentTimeMillis()).apply();
                   this.mAppContext.sendBroadcast(new Intent(STATE_SCHEDULE_CHANGED).putExtra(EXTRA_SECONDS_FROM_NOW, value), CrossConstants.BROADCAST_PERMISSION);
                   return;
                        }
               return;
                    }
           SLog.m446d("Client prefetch is disabled, schedule will do nothing.");
                }

       public void cancelNextPrefetch() {
           this.mImpl.cancel(this);
           this.mPref.edit().putLong(PREF_NEXT_EXPECTED, -1L).apply();
           this.mAppContext.sendBroadcast(new Intent(STATE_SCHEDULE_CHANGED).putExtra(EXTRA_SECONDS_FROM_NOW, -1), CrossConstants.BROADCAST_PERMISSION);
                }

                
       class RunnableC04671 implements Runnable {
                    final  long val$delayMs;

           RunnableC04671(long j) {
                        this.val$delayMs = j;
                    }

                    @Override 
           public void run() {
               PrefetchManager.access$000(PrefetchManager.this).runNow(PrefetchManager.this, this.val$delayMs);
                    }
                }

       public void run(long delayMs) {
           this.mLogger.m488d("Scheduling a sync {}ms from now.", Long.valueOf(delayMs));
           this.mHandler.postDelayed(new RunnableC04671(delayMs), delayMs);
                }

       public static void injectPrefetchArguments(Bundle serviceBundle, long maxStaleTime) {
           serviceBundle.putParcelable(VineService.EXTRA_CACHE_POLICY, UrlCachePolicy.cacheAllowedPolicy(false, maxStaleTime));
           serviceBundle.putBoolean(VineService.EXTRA_USER_INITIATED, false);
           serviceBundle.putBoolean(VineService.EXTRA_IS_POLLING, true);
                }

       public boolean isEnabled() {
           Account account = getActiveAccount();
                    return account != null && this.mUserDataHelper.getBoolean(account, PREF_ENABLED, false);
                }

       public long getInterval() {
           return this.mUserDataHelper.getLong(getActiveAccount(), PREF_INTERVAL, DEFAULT_INTERVAL);
                }

       public long getLastSync() {
           return this.mUserDataHelper.getLong(getActiveAccount(), PREF_LAST_SYNC, -1L);
                }

       public long getCurrentSyncStartTime() {
           Account account = getActiveAccount();
           if (account != null) {
               return this.mUserDataHelper.getLong(account, PREF_CURRENT_START, -1L);
                    }
                    return -1L;
                }

       public void delayNextPrefetch() {
           cancelNextPrefetch();
           scheduleNextPrefetch(false);
                }

       public boolean isSyncPending() {
                    return getCurrentSyncStartTime() >= 0;
                }

       public boolean isWifiOnly() {
           return this.mUserDataHelper.getBoolean(getActiveAccount(), PREF_WIFI_ONLY, true);
                }

       public boolean isChargingRequired() {
           return this.mUserDataHelper.getBoolean(getActiveAccount(), PREF_CHARGING_REQUIRED, true);
                }

       public boolean isRoamingAllowed() {
           return this.mUserDataHelper.getBoolean(getActiveAccount(), PREF_ROAMING_ALLOWED, false);
                }

       public void setLastSync(long timeMillis) {
           Account account = getActiveAccount();
           if (account != null) {
               this.mUserDataHelper.setLong(account, PREF_LAST_SYNC, timeMillis);
                    }
                }

       public void setChargingRequired(boolean isRequired) {
           Account account = getActiveAccount();
           if (account != null) {
               this.mUserDataHelper.setBoolean(account, PREF_CHARGING_REQUIRED, isRequired);
                    }
                }

       public void setWifiOnly(boolean isWifiOnly) {
           Account account = getActiveAccount();
           if (account != null) {
               this.mUserDataHelper.setBoolean(account, PREF_WIFI_ONLY, isWifiOnly);
                    }
                }

       public void notifySyncStart(Context context) {
           FlurryUtils.trackPrefetchStart(this.mPref.getLong(PREF_LAST_START, -1L));
           this.mPref.edit().putLong(PREF_LAST_START, System.currentTimeMillis()).apply();
           context.sendBroadcast(new Intent(STATE_CHANGE_START), CrossConstants.BROADCAST_PERMISSION);
                }

       public void setCurrentSyncStart(long currentSyncStart) {
           Account account = getActiveAccount();
           if (account != null) {
               this.mUserDataHelper.setLong(account, PREF_CURRENT_START, currentSyncStart);
                    }
                }

       public void notifySyncEnd(Context context, String s, boolean success, boolean manual) {
           if (success) {
               long duration = System.currentTimeMillis() - getCurrentSyncStartTime();
               FileLogger.getLogger(LogReaderTask.PREFETCH_LOG).write("Sync completed, took {}ms.\n", Long.valueOf(duration));
               setLastSync(System.currentTimeMillis());
               FlurryUtils.trackPrefetchEnd(duration);
               scheduleNextPrefetch(false);
                    } else {
               FlurryUtils.trackPrefetchEnd(-1L);
               if (!manual) {
                   scheduleFailedFetch();
                        }
                    }
           setCurrentSyncStart(-1L);
           if (s != null) {
               context.sendBroadcast(new Intent(STATE_CHANGE_END).putExtra("error", s), CrossConstants.BROADCAST_PERMISSION);
                    } else {
               context.sendBroadcast(new Intent(STATE_CHANGE_END), CrossConstants.BROADCAST_PERMISSION);
                    }
                }

       private void scheduleFailedFetch() {
           this.mImpl.scheduleFailedFetch(this);
                }

       public void setRoamingAllowed(boolean isRoamingAllowed) {
           Account account = getActiveAccount();
           if (account != null) {
               this.mUserDataHelper.setBoolean(account, PREF_ROAMING_ALLOWED, isRoamingAllowed);
                    }
                }

       public void setInterval(long interval) {
           Account account = getActiveAccount();
           if (account != null) {
               this.mUserDataHelper.setLong(account, PREF_INTERVAL, interval);
                    }
                }

                @Nullable
       public Account getActiveAccount() {
                    String name;
           Session session = null;
                    try {
               session = this.mAppController.getActiveSessionReadOnly();
                    } catch (Exception e) {
               CrashUtil.logException(e);
                    }
           if (session == null || (name = session.getLoginEmail()) == null) {
               return null;
                    }
           Account account = VineAccountHelper.getAccount(this.mAppContext, name);
           return account;
                }

                @Nullable
       public Account getSyncableAccount() {
           Account account = getActiveAccount();
           if (account != null && !this.mUserDataHelper.getBoolean(account, PREF_ENABLED, false)) {
               this.mLogger.m505i("Account is not syncable.");
               return null;
                    }
           return account;
                }

       public boolean setEnabled(boolean isEnabled) {
           Account account = getActiveAccount();
           if (account != null) {
               this.mUserDataHelper.setBoolean(account, PREF_ENABLED, isEnabled);
               if (isEnabled) {
                   scheduleNextPrefetch(false);
                        } else {
                   cancelNextPrefetch();
                        }
                    }
           return isEnabled();
                }

       public boolean isSuitableToSync() {
           if (!isEnabled()) {
               this.mFileLogger.write("Sync is not suitable because it may be disabled right now.", new Object[0]);
                        return false;
                    }
           if (isChargingRequired() && !SystemUtil.isBatteryCharging(this.mAppContext)) {
               this.mFileLogger.write("Sync is not suitable because charging is required.", new Object[0]);
                        return false;
                    }
           if (isWifiOnly() && !SystemUtil.isOnWifi(this.mAppContext)) {
               this.mFileLogger.write("Sync is not suitable because wifi connection is required.", new Object[0]);
                        return false;
                    }
           if (!isRoamingAllowed() && SystemUtil.isRoaming(this.mAppContext)) {
               this.mFileLogger.write("Sync is not suitable because device might be roaming.", new Object[0]);
                        return false;
                    }
           return true;
                }

       public Context getApplicationContext() {
           return this.mAppContext;
                }
            }
