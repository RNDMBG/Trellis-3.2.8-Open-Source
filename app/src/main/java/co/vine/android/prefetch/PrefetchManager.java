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
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 39 */    public class PrefetchManager {
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

/* 40 */        static /* synthetic */ PreftechScheduler access$000(PrefetchManager x0) {
/* 41 */            return x0.mImpl;
                }

/* 97 */        public static synchronized PrefetchManager getInstance(Context context) {
                    try {
                        if (sInstance == null) {
/* 99 */                    sInstance = new PrefetchManager(context);
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
/* 101 */           return sInstance;
                }

/* 103 */       private PrefetchManager(Context context) {
/* 105 */           this.mAppContext = context;
/* 106 */           this.mAppController = AppController.getInstance(context);
/* 107 */           this.mUserDataHelper = new UserDataHelper(context);
/* 108 */           this.mPref = Util.getSharedPrefs(context, "prefetchStat");
                    this.mFileLogger = FileLogger.getLogger(SLog.sLogsOn ? LogReaderTask.PREFETCH_LOG : null);
/* 110 */           if (Build.VERSION.SDK_INT >= 21) {
                    }
/* 114 */           this.mImpl = new AlarmPrefetchScheduler();
                }

/* 121 */       public long getNextFetchDelay(boolean canSyncSoon) {
/* 122 */           long lastSync = getLastSync();
/* 123 */           long interval = getInterval();
/* 124 */           long requestTime = System.currentTimeMillis();
/* 125 */           long[] timeTuple = findStartAndEndTimes(lastSync, interval, requestTime);
/* 126 */           if (timeTuple == null) {
/* 127 */               return 0L;
                    }
/* 129 */           long start = timeTuple[0];
/* 130 */           long end = timeTuple[1];
/* 132 */           long searchedTime = BehaviorManager.getInstance(this.mAppContext).searchForBestDelay(start, end);
/* 133 */           String log = MessageFormatter.toStringMessage("Search with interval {}, start {}, end {}, searched {}, took {}ms.", Long.valueOf(interval), Long.valueOf((start - requestTime) / 1000), Long.valueOf((end - requestTime) / 1000), Long.valueOf((searchedTime - requestTime) / 1000), Long.valueOf(System.currentTimeMillis() - requestTime));
/* 134 */           this.mFileLogger.write(log, new Object[0]);
/* 135 */           return Math.max(searchedTime - requestTime, 60000L);
                }

/* 139 */       public boolean isClientPrefetchEnabled() {
/* 140 */           return ClientFlagsHelper.prefetchEnabled(this.mAppContext);
                }

/* 144 */       static long[] findStartAndEndTimes(long lastSync, long interval, long requestTime) {
                    if (lastSync + interval + 0.4f < requestTime) {
/* 146 */               return null;
                    }
/* 148 */           long midpoint = lastSync + interval;
/* 149 */           long minStart = lastSync + getMinimumDelay(interval);
/* 150 */           long start = Math.max(midpoint - ((long) (0.4f * interval)), minStart);
/* 151 */           long end = Math.max(((long) (0.8f * interval)) + start, ((long) (0.4f * interval)) + midpoint);
/* 152 */           return new long[]{start, end};
                }

/* 155 */       public static long getMinimumDelay(long interval) {
/* 156 */           return (long) (interval * 0.3f);
                }

/* 161 */       public void onPostFetchOperationComplete(boolean prefetch, NetworkOperation.NetworkOperationResult lastResult, UrlCachePolicy resolvedCachePolicy) {
                    switch (lastResult) {
                        case CACHED:
/* 164 */                   if (!prefetch) {
/* 165 */                       long count = this.mPref.getLong(PREF_CACHE_COUNT, 0L);
/* 166 */                       this.mPref.edit().putLong(PREF_CACHE_COUNT, count + 1).apply();
                            }
                            break;
                        case NETWORK:
/* 171 */                   if (prefetch) {
/* 172 */                       long count2 = this.mPref.getLong(PREF_FETECHED_COUNT, 0L);
/* 173 */                       this.mPref.edit().putLong(PREF_FETECHED_COUNT, count2 + 1).apply();
                            } else if (resolvedCachePolicy.mCachedResponseAllowed) {
/* 175 */                       long count3 = this.mPref.getLong(PREF_MISS_COUNT, 0L);
/* 176 */                       this.mPref.edit().putLong(PREF_MISS_COUNT, count3 + 1).apply();
                            }
                            break;
                        case FAILURE:
/* 181 */                   if (!prefetch && resolvedCachePolicy.mCachedResponseAllowed) {
/* 182 */                       long count4 = this.mPref.getLong(PREF_MISS_COUNT, 0L);
/* 183 */                       this.mPref.edit().putLong(PREF_MISS_COUNT, count4 + 1).apply();
                                break;
                            }
                            break;
                    }
                }

/* 190 */       public String[] getStats() {
/* 192 */           long count = this.mPref.getLong(PREF_MISS_COUNT, 0L);
/* 191 */           String[] stats = {"Total fetched " + count + " when sync happened.", "Was able to save " + count + " when loading.", "Had to fetch " + count + " when loading."};
/* 194 */           long count2 = this.mPref.getLong(PREF_CACHE_COUNT, 0L);
/* 196 */           long count3 = this.mPref.getLong(PREF_FETECHED_COUNT, 0L);
/* 198 */           return stats;
                }

/* 201 */       public static boolean validateNextRunTime(long nextRun, long interval, long lastSync, long requestTime) {
/* 202 */           long nextRunTime = requestTime + nextRun;
                    return ((float) nextRunTime) <= ((float) (lastSync + interval)) + (((float) interval) * 0.4f) && ((float) nextRunTime) >= ((float) (lastSync + interval)) - (((float) interval) * 0.4f);
                }

/* 207 */       public long getNextSync() {
/* 208 */           return this.mImpl.getNextSync(this.mPref.getLong(PREF_NEXT_EXPECTED, -2L), this);
                }

/* 211 */       public void onDeviceBoot() {
/* 212 */           long currentStart = getCurrentSyncStartTime();
                    if (currentStart != -1 && System.currentTimeMillis() - currentStart > 86400000) {
/* 215 */               setCurrentSyncStart(-1L);
                    }
/* 218 */           scheduleNextPrefetch(true);
                }

/* 221 */       public void onCancelled() {
/* 222 */           this.mPref.edit().putLong(PREF_NEXT_EXPECTED, -2L).apply();
                }

/* 226 */       public static class PrefetchFilter extends IntentFilter {
/* 227 */           public PrefetchFilter() {
/* 229 */               addAction(PrefetchManager.STATE_CHANGE_START);
/* 230 */               addAction(PrefetchManager.STATE_CHANGE_END);
/* 231 */               addAction(PrefetchManager.STATE_SCHEDULE_CHANGED);
                    }

/* 234 */           public boolean isStart(String bcAction) {
/* 235 */               return PrefetchManager.STATE_CHANGE_START.equals(bcAction);
                    }

/* 238 */           public boolean isEnd(String bcAction) {
/* 239 */               return PrefetchManager.STATE_CHANGE_END.equals(bcAction);
                    }

/* 242 */           public int getScheduledTimeInSeconds(Bundle extras) {
/* 243 */               if (extras != null) {
                            return extras.getInt(PrefetchManager.EXTRA_SECONDS_FROM_NOW, -2);
                        }
                        return -2;
                    }
                }

/* 248 */       public static PrefetchFilter getStateChangeFilter() {
/* 249 */           return new PrefetchFilter();
                }

/* 252 */       public static SLogger getPrefetchLogger() {
/* 253 */           return SLogger.getLogger(VineService.EXTRA_PREFETCH);
                }

/* 256 */       public void scheduleNextPrefetch(boolean canSyncSoon) {
/* 257 */           if (isClientPrefetchEnabled()) {
/* 258 */               int next = this.mImpl.schedule(this, canSyncSoon);
/* 259 */               if (next > -1) {
/* 260 */                   int value = next * 1000;
/* 261 */                   this.mPref.edit().putLong(PREF_NEXT_EXPECTED, ((long) value) + System.currentTimeMillis()).apply();
/* 262 */                   this.mAppContext.sendBroadcast(new Intent(STATE_SCHEDULE_CHANGED).putExtra(EXTRA_SECONDS_FROM_NOW, value), CrossConstants.BROADCAST_PERMISSION);
/* 267 */                   return;
                        }
/* 267 */               return;
                    }
/* 265 */           SLog.m446d("Client prefetch is disabled, schedule will do nothing.");
                }

/* 269 */       public void cancelNextPrefetch() {
/* 270 */           this.mImpl.cancel(this);
/* 271 */           this.mPref.edit().putLong(PREF_NEXT_EXPECTED, -1L).apply();
/* 272 */           this.mAppContext.sendBroadcast(new Intent(STATE_SCHEDULE_CHANGED).putExtra(EXTRA_SECONDS_FROM_NOW, -1), CrossConstants.BROADCAST_PERMISSION);
                }

                /* JADX INFO: renamed from: co.vine.android.prefetch.PrefetchManager$1 */
/* 275 */       class RunnableC04671 implements Runnable {
                    final /* synthetic */ long val$delayMs;

/* 276 */           RunnableC04671(long j) {
                        this.val$delayMs = j;
                    }

                    @Override // java.lang.Runnable
/* 279 */           public void run() {
/* 280 */               PrefetchManager.access$000(PrefetchManager.this).runNow(PrefetchManager.this, this.val$delayMs);
                    }
                }

/* 275 */       public void run(long delayMs) {
/* 276 */           this.mLogger.m488d("Scheduling a sync {}ms from now.", Long.valueOf(delayMs));
/* 277 */           this.mHandler.postDelayed(new RunnableC04671(delayMs), delayMs);
                }

/* 285 */       public static void injectPrefetchArguments(Bundle serviceBundle, long maxStaleTime) {
/* 286 */           serviceBundle.putParcelable(VineService.EXTRA_CACHE_POLICY, UrlCachePolicy.cacheAllowedPolicy(false, maxStaleTime));
/* 287 */           serviceBundle.putBoolean(VineService.EXTRA_USER_INITIATED, false);
/* 288 */           serviceBundle.putBoolean(VineService.EXTRA_IS_POLLING, true);
                }

/* 291 */       public boolean isEnabled() {
/* 292 */           Account account = getActiveAccount();
                    return account != null && this.mUserDataHelper.getBoolean(account, PREF_ENABLED, false);
                }

/* 296 */       public long getInterval() {
/* 297 */           return this.mUserDataHelper.getLong(getActiveAccount(), PREF_INTERVAL, DEFAULT_INTERVAL);
                }

/* 300 */       public long getLastSync() {
/* 301 */           return this.mUserDataHelper.getLong(getActiveAccount(), PREF_LAST_SYNC, -1L);
                }

/* 304 */       public long getCurrentSyncStartTime() {
/* 305 */           Account account = getActiveAccount();
/* 306 */           if (account != null) {
/* 307 */               return this.mUserDataHelper.getLong(account, PREF_CURRENT_START, -1L);
                    }
                    return -1L;
                }

/* 313 */       public void delayNextPrefetch() {
/* 314 */           cancelNextPrefetch();
/* 315 */           scheduleNextPrefetch(false);
                }

/* 318 */       public boolean isSyncPending() {
                    return getCurrentSyncStartTime() >= 0;
                }

/* 322 */       public boolean isWifiOnly() {
/* 323 */           return this.mUserDataHelper.getBoolean(getActiveAccount(), PREF_WIFI_ONLY, true);
                }

/* 326 */       public boolean isChargingRequired() {
/* 327 */           return this.mUserDataHelper.getBoolean(getActiveAccount(), PREF_CHARGING_REQUIRED, true);
                }

/* 330 */       public boolean isRoamingAllowed() {
/* 331 */           return this.mUserDataHelper.getBoolean(getActiveAccount(), PREF_ROAMING_ALLOWED, false);
                }

/* 334 */       public void setLastSync(long timeMillis) {
/* 335 */           Account account = getActiveAccount();
/* 336 */           if (account != null) {
/* 337 */               this.mUserDataHelper.setLong(account, PREF_LAST_SYNC, timeMillis);
                    }
                }

/* 341 */       public void setChargingRequired(boolean isRequired) {
/* 342 */           Account account = getActiveAccount();
/* 343 */           if (account != null) {
/* 344 */               this.mUserDataHelper.setBoolean(account, PREF_CHARGING_REQUIRED, isRequired);
                    }
                }

/* 348 */       public void setWifiOnly(boolean isWifiOnly) {
/* 349 */           Account account = getActiveAccount();
/* 350 */           if (account != null) {
/* 351 */               this.mUserDataHelper.setBoolean(account, PREF_WIFI_ONLY, isWifiOnly);
                    }
                }

/* 355 */       public void notifySyncStart(Context context) {
/* 356 */           FlurryUtils.trackPrefetchStart(this.mPref.getLong(PREF_LAST_START, -1L));
/* 357 */           this.mPref.edit().putLong(PREF_LAST_START, System.currentTimeMillis()).apply();
/* 358 */           context.sendBroadcast(new Intent(STATE_CHANGE_START), CrossConstants.BROADCAST_PERMISSION);
                }

/* 361 */       public void setCurrentSyncStart(long currentSyncStart) {
/* 362 */           Account account = getActiveAccount();
/* 363 */           if (account != null) {
/* 364 */               this.mUserDataHelper.setLong(account, PREF_CURRENT_START, currentSyncStart);
                    }
                }

/* 368 */       public void notifySyncEnd(Context context, String s, boolean success, boolean manual) {
/* 369 */           if (success) {
/* 370 */               long duration = System.currentTimeMillis() - getCurrentSyncStartTime();
/* 371 */               FileLogger.getLogger(LogReaderTask.PREFETCH_LOG).write("Sync completed, took {}ms.\n", Long.valueOf(duration));
/* 372 */               setLastSync(System.currentTimeMillis());
/* 373 */               FlurryUtils.trackPrefetchEnd(duration);
/* 374 */               scheduleNextPrefetch(false);
                    } else {
/* 376 */               FlurryUtils.trackPrefetchEnd(-1L);
/* 377 */               if (!manual) {
/* 378 */                   scheduleFailedFetch();
                        }
                    }
/* 381 */           setCurrentSyncStart(-1L);
/* 382 */           if (s != null) {
/* 383 */               context.sendBroadcast(new Intent(STATE_CHANGE_END).putExtra("error", s), CrossConstants.BROADCAST_PERMISSION);
                    } else {
/* 385 */               context.sendBroadcast(new Intent(STATE_CHANGE_END), CrossConstants.BROADCAST_PERMISSION);
                    }
                }

/* 389 */       private void scheduleFailedFetch() {
/* 390 */           this.mImpl.scheduleFailedFetch(this);
                }

/* 393 */       public void setRoamingAllowed(boolean isRoamingAllowed) {
/* 394 */           Account account = getActiveAccount();
/* 395 */           if (account != null) {
/* 396 */               this.mUserDataHelper.setBoolean(account, PREF_ROAMING_ALLOWED, isRoamingAllowed);
                    }
                }

/* 400 */       public void setInterval(long interval) {
/* 401 */           Account account = getActiveAccount();
/* 402 */           if (account != null) {
/* 403 */               this.mUserDataHelper.setLong(account, PREF_INTERVAL, interval);
                    }
                }

                @Nullable
/* 408 */       public Account getActiveAccount() {
                    String name;
/* 409 */           Session session = null;
                    try {
/* 411 */               session = this.mAppController.getActiveSessionReadOnly();
                    } catch (Exception e) {
/* 413 */               CrashUtil.logException(e);
                    }
/* 416 */           if (session == null || (name = session.getLoginEmail()) == null) {
/* 415 */               return null;
                    }
/* 419 */           Account account = VineAccountHelper.getAccount(this.mAppContext, name);
/* 422 */           return account;
                }

                @Nullable
/* 426 */       public Account getSyncableAccount() {
/* 427 */           Account account = getActiveAccount();
/* 428 */           if (account != null && !this.mUserDataHelper.getBoolean(account, PREF_ENABLED, false)) {
/* 430 */               this.mLogger.m505i("Account is not syncable.");
/* 431 */               return null;
                    }
/* 434 */           return account;
                }

/* 437 */       public boolean setEnabled(boolean isEnabled) {
/* 438 */           Account account = getActiveAccount();
/* 439 */           if (account != null) {
/* 440 */               this.mUserDataHelper.setBoolean(account, PREF_ENABLED, isEnabled);
/* 441 */               if (isEnabled) {
/* 442 */                   scheduleNextPrefetch(false);
                        } else {
/* 444 */                   cancelNextPrefetch();
                        }
                    }
/* 447 */           return isEnabled();
                }

/* 450 */       public boolean isSuitableToSync() {
/* 451 */           if (!isEnabled()) {
/* 452 */               this.mFileLogger.write("Sync is not suitable because it may be disabled right now.", new Object[0]);
                        return false;
                    }
/* 455 */           if (isChargingRequired() && !SystemUtil.isBatteryCharging(this.mAppContext)) {
/* 456 */               this.mFileLogger.write("Sync is not suitable because charging is required.", new Object[0]);
                        return false;
                    }
/* 459 */           if (isWifiOnly() && !SystemUtil.isOnWifi(this.mAppContext)) {
/* 460 */               this.mFileLogger.write("Sync is not suitable because wifi connection is required.", new Object[0]);
                        return false;
                    }
/* 463 */           if (!isRoamingAllowed() && SystemUtil.isRoaming(this.mAppContext)) {
/* 464 */               this.mFileLogger.write("Sync is not suitable because device might be roaming.", new Object[0]);
                        return false;
                    }
/* 467 */           return true;
                }

/* 471 */       public Context getApplicationContext() {
/* 472 */           return this.mAppContext;
                }
            }
