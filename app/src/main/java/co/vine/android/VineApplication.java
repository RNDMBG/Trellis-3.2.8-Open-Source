            package co.vine.android;

            import android.annotation.TargetApi;
            import android.app.Application;
            import android.content.Context;
            import android.content.SharedPreferences;
            import android.content.res.Configuration;
            import android.content.res.Resources;
            import android.database.CursorWindow;
            import android.os.Handler;
            import android.support.annotation.Nullable;
            import android.text.TextUtils;
            import co.vine.android.client.AppController;
            import co.vine.android.client.FacebookVineApp;
            import co.vine.android.client.VineAPI;
            import co.vine.android.network.NetworkOperation;
import co.vine.android.player.SdkVideoView;
import co.vine.android.plugin.LogReaderTask;
import co.vine.android.provider.VineDatabaseHelper;
            import co.vine.android.util.BuildUtil;
            import co.vine.android.util.ClientFlagsHelper;
            import co.vine.android.util.CrashUtil;
            import co.vine.android.util.FileLogger;
            import co.vine.android.util.SystemUtil;
            import co.vine.android.util.Util;
            import co.vine.android.util.analytics.AnalyticsManager;
            import com.edisonwang.android.slog.SLog;
            import com.facebook.Session;
            import com.facebook.buck.android.support.exopackage.DefaultApplicationLike;
            import java.lang.reflect.Field;
            import java.util.ArrayList;
            import java.util.Locale;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 38 */    public class VineApplication extends DefaultApplicationLike implements Thread.UncaughtExceptionHandler {
                private static VineApplication INSTANCE;
                private final Application mAppContext;
                private Thread.UncaughtExceptionHandler sDefaultHandler;

/* 39 */        static /* synthetic */ Application access$000(VineApplication x0) {
/* 40 */            return x0.mAppContext;
                }

/* 39 */        static /* synthetic */ void access$100(Context x0, Locale x1, boolean x2) {
/* 40 */            forceCustomLocale(x0, x1, x2);
                }

/* 46 */        public VineApplication(Application appContext) {
/* 48 */            this.mAppContext = appContext;
/* 49 */            BuildUtil.bootstrap();
/* 50 */            NetworkOperation.setLoggerFile(BuildUtil.isLogsOn() ? LogReaderTask.NETWORK_LOG : null);
/* 51 */            AnalyticsManager.onApplicationInit();
/* 52 */            MergeResourceManager.onApplicationInit();
/* 53 */            Session.DEFAULT_FACEBOOK_ID = FacebookVineApp.APP_ID;
/* 54 */            if (BuildUtil.isLogsOn() && SystemUtil.isRunningOnServiceProcess(appContext)) {
/* 55 */                FileLogger.getLogger(LogReaderTask.NETWORK_LOG).clear();
                    }
                }

                @Override // com.facebook.buck.android.support.exopackage.DefaultApplicationLike, com.facebook.buck.android.support.exopackage.ApplicationLike
/* 60 */        public void onCreate() {
/* 61 */            super.onCreate();
/* 62 */            if (BuildUtil.isI18nOn()) {
/* 63 */                Resources resources = this.mAppContext.getResources();
/* 64 */                if (resources != null) {
/* 67 */                    forceCustomLocale(this.mAppContext);
                        } else {
/* 132 */                   return;
                        }
                    }
/* 70 */            CrashUtil.start(this.mAppContext);
/* 72 */            Thread.UncaughtExceptionHandler defaultHandler = Thread.getDefaultUncaughtExceptionHandler();
/* 73 */            if (defaultHandler != this) {
/* 74 */                this.sDefaultHandler = defaultHandler;
/* 75 */                Thread.setDefaultUncaughtExceptionHandler(this);
                    }
                    try {
/* 79 */                ArrayList<String> commandLine = new ArrayList<>();
/* 80 */                commandLine.add("logcat");
/* 81 */                commandLine.add("-c");
/* 82 */                Runtime.getRuntime().exec((String[]) commandLine.toArray(new String[commandLine.size()]));
                    } catch (Throwable th) {
                    }
                    try {
/* 88 */                CrashUtil.set("RAM Budget", SystemUtil.getMemoryBudgetForLargeMemoryClass(this.mAppContext));
                    } catch (Exception e) {
/* 90 */                CrashUtil.log("Failed to get ram budeget.");
                    }
                    try {
/* 94 */                boolean useVinePlayer = ClientFlagsHelper.getUseVinePlayer(this.mAppContext);
/* 95 */                SdkVideoView.setGlobalUseVinePlayer(useVinePlayer);
/* 96 */                CrashUtil.set("Using Vine Player", String.valueOf(useVinePlayer));
                    } catch (Exception e2) {
/* 98 */                CrashUtil.log("Failed to set and get VinePlayer status.");
                    }
/* 101 */           if (!BuildUtil.isExplore()) {
/* 102 */               SLog.m446d("Start changing value.");
                        try {
/* 104 */                   Field field = CursorWindow.class.getDeclaredField("sCursorWindowSize");
/* 105 */                   field.setAccessible(true);
/* 106 */                   Object original = field.get(null);
/* 107 */                   SLog.m447d("Original value: {}.", original);
/* 108 */                   int target = 10000000;
/* 109 */                   int ratio = SystemUtil.getMemoryBudgetForLargeMemoryClass(this.mAppContext);
/* 110 */                   if (ratio == 1) {
/* 111 */                       target = (int) (((double) 10000000) * 0.8d);
                            }
/* 113 */                   SLog.m447d("Changing using weighted value: {}", Integer.valueOf(target));
/* 114 */                   if (((Integer) original).intValue() < target) {
/* 115 */                       field.set(null, Integer.valueOf(target));
                            }
/* 117 */                   SLog.m446d("Sucessfully updated sCursorWindowSize.");
                        } catch (Exception e3) {
/* 119 */                   CrashUtil.logException(e3, "You are too evil.", new Object[0]);
                        }
                        try {
/* 122 */                   Field field2 = CursorWindow.class.getDeclaredField("sCursorWindowSize");
/* 123 */                   field2.setAccessible(true);
/* 124 */                   SLog.m447d("Verify value: {}.", field2.get(null));
                        } catch (Exception e4) {
/* 126 */                   CrashUtil.logException(e4, "You are too evil.", new Object[0]);
                        }
/* 128 */               SLog.m446d("End changing value.");
                    }
/* 131 */           INSTANCE = this;
                    schedulePeriodicCacheRefresh();
                }

                private void schedulePeriodicCacheRefresh() {
                    if (SystemUtil.isRunningOnServiceProcess(this.mAppContext)) {
                        return;
                    }
                    final SharedPreferences prefs = Util.getDefaultSharedPrefs(this.mAppContext);
                    int starts = prefs.getInt("vine_app_start_count", 0) + 1;
                    boolean shouldRefresh = starts >= 4;
                    prefs.edit().putInt("vine_app_start_count", shouldRefresh ? 0 : starts).apply();
                    if (!shouldRefresh) {
                        return;
                    }
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                VineDatabaseHelper.getDatabaseHelper(VineApplication.this.mAppContext).clearCachedData(true);
                                SLog.m446d("Periodic Vine cache refresh completed.");
                            } catch (Throwable error) {
                                CrashUtil.logException(error, "Periodic Vine cache refresh failed.");
                            }
                        }
                    }, "vine-cache-refresh").start();
                }

                @Nullable
/* 140 */       public static VineApplication getInstance() {
/* 141 */           return INSTANCE;
                }

                @Override // com.facebook.buck.android.support.exopackage.DefaultApplicationLike, com.facebook.buck.android.support.exopackage.ApplicationLike
/* 145 */       public void onConfigurationChanged(Configuration newConfig) {
/* 146 */           super.onConfigurationChanged(newConfig);
                    try {
/* 148 */               if (BuildUtil.isI18nOn()) {
/* 149 */                   Locale locale = getCustomLocale(this.mAppContext);
/* 150 */                   if (newConfig.locale != null) {
/* 151 */                       CrashUtil.set("locale", newConfig.locale.getDisplayName());
                            }
/* 153 */                   if (locale != null && !locale.equals(newConfig.locale)) {
/* 154 */                       new Handler().postDelayed(new RunnableC03131(locale), 500L);
                            }
                        }
                    } catch (Exception e) {
/* 163 */               CrashUtil.logException(e);
                    }
                }

                /* JADX INFO: renamed from: co.vine.android.VineApplication$1 */
/* 152 */       class RunnableC03131 implements Runnable {
                    final /* synthetic */ Locale val$locale;

/* 153 */           RunnableC03131(Locale locale) {
                        this.val$locale = locale;
                    }

                    @Override // java.lang.Runnable
/* 156 */           public void run() {
/* 157 */               VineApplication.access$100(VineApplication.access$000(VineApplication.this), this.val$locale, true);
                    }
                }

                @Override // com.facebook.buck.android.support.exopackage.DefaultApplicationLike, com.facebook.buck.android.support.exopackage.ApplicationLike
                @TargetApi(14)
/* 169 */       public void onTrimMemory(int level) {
/* 170 */           super.onTrimMemory(level);
/* 171 */           CrashUtil.log("onTrimMemory happened: {}.", Integer.valueOf(level));
/* 172 */           AppController instance = AppController.getInstance(this.mAppContext);
/* 173 */           if (instance != null) {
/* 174 */               instance.onTrimMemory(level);
                    }
                }

                @Override // com.facebook.buck.android.support.exopackage.DefaultApplicationLike, com.facebook.buck.android.support.exopackage.ApplicationLike
/* 179 */       public void onLowMemory() {
/* 180 */           super.onLowMemory();
/* 181 */           CrashUtil.log("onLowMemory happened.");
/* 182 */           AppController instance = AppController.getInstance(this.mAppContext);
/* 183 */           if (instance != null) {
/* 184 */               instance.onLowMemory();
                    }
                }

/* 188 */       public Locale getLocale() {
/* 189 */           return this.mAppContext.getResources().getConfiguration().locale;
                }

                @Override // java.lang.Thread.UncaughtExceptionHandler
/* 193 */       public void uncaughtException(Thread thread, Throwable ex) {
                    String msg;
/* 194 */           if (ex != null && (msg = ex.getMessage()) != null && (msg.contains("detachFromGLContext") || msg.contains("startPreview") || msg.contains("setParameters"))) {
/* 201 */               CrashUtil.collectLogs(300, CrashUtil.getDefaultIgnoreCountForLogCollection(ex));
                    }
/* 204 */           if (this.sDefaultHandler != null) {
/* 205 */               this.sDefaultHandler.uncaughtException(thread, ex);
                    }
                }

/* 209 */       public Context getApplicationContext() {
/* 210 */           return this.mAppContext;
                }

/* 213 */       private static void forceCustomLocale(Context context, Locale locale, boolean updateResources) {
/* 214 */           if (locale != null) {
/* 215 */               Locale.setDefault(locale);
/* 216 */               VineAPI.getInstance(context).refreshLocale();
/* 217 */               if (updateResources) {
/* 218 */                   Resources resources = context.getApplicationContext().getResources();
/* 219 */                   Configuration config = resources.getConfiguration();
/* 220 */                   config.locale = locale;
/* 221 */                   resources.updateConfiguration(config, resources.getDisplayMetrics());
                        }
                    }
                }

/* 226 */       private static void forceCustomLocale(Context context) {
/* 227 */           forceCustomLocale(context, getCustomLocale(context), true);
                }

/* 230 */       private static Locale getCustomLocale(Context context) {
                    Locale locale;
/* 231 */           SharedPreferences prefs = Util.getDefaultSharedPrefs(context);
/* 232 */           if (prefs.getBoolean(Settings.PREF_CUSTOM_LOCALE_ENABLED, false)) {
/* 233 */               String localeCode = prefs.getString(Settings.PREF_CUSTOM_LOCALE, null);
/* 234 */               String countryCode = prefs.getString(Settings.PREF_CUSTOME_LOCALE_COUNTRY, null);
/* 235 */               if (!TextUtils.isEmpty(localeCode)) {
/* 237 */                   if (!TextUtils.isEmpty(countryCode)) {
/* 238 */                       locale = new Locale(localeCode, countryCode);
                            } else {
/* 240 */                       locale = new Locale(localeCode);
                            }
/* 242 */                   if (!TextUtils.isEmpty(locale.getISO3Language())) {
/* 247 */                       return locale;
                            }
                        }
                    }
                    return null;
                }

/* 250 */       private static boolean isUsEnglishLocale() {
/* 251 */           String code = Locale.getDefault().getLanguage();
/* 252 */           return Locale.ENGLISH.getLanguage().equals(code);
                }
            }
