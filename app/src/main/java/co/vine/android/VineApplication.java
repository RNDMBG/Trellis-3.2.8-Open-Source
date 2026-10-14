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
            
            
    public class VineApplication extends DefaultApplicationLike implements Thread.UncaughtExceptionHandler {
                private static VineApplication INSTANCE;
                private final Application mAppContext;
                private Thread.UncaughtExceptionHandler sDefaultHandler;

        static  Application access$000(VineApplication x0) {
            return x0.mAppContext;
                }

        static  void access$100(Context x0, Locale x1, boolean x2) {
            forceCustomLocale(x0, x1, x2);
                }

        public VineApplication(Application appContext) {
            this.mAppContext = appContext;
            BuildUtil.bootstrap();
            NetworkOperation.setLoggerFile(BuildUtil.isLogsOn() ? LogReaderTask.NETWORK_LOG : null);
            AnalyticsManager.onApplicationInit();
            MergeResourceManager.onApplicationInit();
            Session.DEFAULT_FACEBOOK_ID = FacebookVineApp.APP_ID;
            if (BuildUtil.isLogsOn() && SystemUtil.isRunningOnServiceProcess(appContext)) {
                FileLogger.getLogger(LogReaderTask.NETWORK_LOG).clear();
                    }
                }

                @Override 
        public void onCreate() {
            super.onCreate();
            if (BuildUtil.isI18nOn()) {
                Resources resources = this.mAppContext.getResources();
                if (resources != null) {
                    forceCustomLocale(this.mAppContext);
                        } else {
                   return;
                        }
                    }
            CrashUtil.start(this.mAppContext);
            Thread.UncaughtExceptionHandler defaultHandler = Thread.getDefaultUncaughtExceptionHandler();
            if (defaultHandler != this) {
                this.sDefaultHandler = defaultHandler;
                Thread.setDefaultUncaughtExceptionHandler(this);
                    }
                    try {
                ArrayList<String> commandLine = new ArrayList<>();
                commandLine.add("logcat");
                commandLine.add("-c");
                Runtime.getRuntime().exec((String[]) commandLine.toArray(new String[commandLine.size()]));
                    } catch (Throwable th) {
                    }
                    try {
                CrashUtil.set("RAM Budget", SystemUtil.getMemoryBudgetForLargeMemoryClass(this.mAppContext));
                    } catch (Exception e) {
                CrashUtil.log("Failed to get ram budeget.");
                    }
                    try {
                boolean useVinePlayer = ClientFlagsHelper.getUseVinePlayer(this.mAppContext);
                SdkVideoView.setGlobalUseVinePlayer(useVinePlayer);
                CrashUtil.set("Using Vine Player", String.valueOf(useVinePlayer));
                    } catch (Exception e2) {
                CrashUtil.log("Failed to set and get VinePlayer status.");
                    }
           if (!BuildUtil.isExplore()) {
               SLog.m446d("Start changing value.");
                        try {
                   Field field = CursorWindow.class.getDeclaredField("sCursorWindowSize");
                   field.setAccessible(true);
                   Object original = field.get(null);
                   SLog.m447d("Original value: {}.", original);
                   int target = 10000000;
                   int ratio = SystemUtil.getMemoryBudgetForLargeMemoryClass(this.mAppContext);
                   if (ratio == 1) {
                       target = (int) (((double) 10000000) * 0.8d);
                            }
                   SLog.m447d("Changing using weighted value: {}", Integer.valueOf(target));
                   if (((Integer) original).intValue() < target) {
                       field.set(null, Integer.valueOf(target));
                            }
                   SLog.m446d("Sucessfully updated sCursorWindowSize.");
                        } catch (Exception e3) {
                   CrashUtil.logException(e3, "You are too evil.", new Object[0]);
                        }
                        try {
                   Field field2 = CursorWindow.class.getDeclaredField("sCursorWindowSize");
                   field2.setAccessible(true);
                   SLog.m447d("Verify value: {}.", field2.get(null));
                        } catch (Exception e4) {
                   CrashUtil.logException(e4, "You are too evil.", new Object[0]);
                        }
               SLog.m446d("End changing value.");
                    }
           INSTANCE = this;
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
       public static VineApplication getInstance() {
           return INSTANCE;
                }

                @Override 
       public void onConfigurationChanged(Configuration newConfig) {
           super.onConfigurationChanged(newConfig);
                    try {
               if (BuildUtil.isI18nOn()) {
                   Locale locale = getCustomLocale(this.mAppContext);
                   if (newConfig.locale != null) {
                       CrashUtil.set("locale", newConfig.locale.getDisplayName());
                            }
                   if (locale != null && !locale.equals(newConfig.locale)) {
                       new Handler().postDelayed(new RunnableC03131(locale), 500L);
                            }
                        }
                    } catch (Exception e) {
               CrashUtil.logException(e);
                    }
                }

                
       class RunnableC03131 implements Runnable {
                    final  Locale val$locale;

           RunnableC03131(Locale locale) {
                        this.val$locale = locale;
                    }

                    @Override 
           public void run() {
               VineApplication.access$100(VineApplication.access$000(VineApplication.this), this.val$locale, true);
                    }
                }

                @Override 
                @TargetApi(14)
       public void onTrimMemory(int level) {
           super.onTrimMemory(level);
           CrashUtil.log("onTrimMemory happened: {}.", Integer.valueOf(level));
           AppController instance = AppController.getInstance(this.mAppContext);
           if (instance != null) {
               instance.onTrimMemory(level);
                    }
                }

                @Override 
       public void onLowMemory() {
           super.onLowMemory();
           CrashUtil.log("onLowMemory happened.");
           AppController instance = AppController.getInstance(this.mAppContext);
           if (instance != null) {
               instance.onLowMemory();
                    }
                }

       public Locale getLocale() {
           return this.mAppContext.getResources().getConfiguration().locale;
                }

                @Override 
       public void uncaughtException(Thread thread, Throwable ex) {
                    String msg;
           if (ex != null && (msg = ex.getMessage()) != null && (msg.contains("detachFromGLContext") || msg.contains("startPreview") || msg.contains("setParameters"))) {
               CrashUtil.collectLogs(300, CrashUtil.getDefaultIgnoreCountForLogCollection(ex));
                    }
           if (this.sDefaultHandler != null) {
               this.sDefaultHandler.uncaughtException(thread, ex);
                    }
                }

       public Context getApplicationContext() {
           return this.mAppContext;
                }

       private static void forceCustomLocale(Context context, Locale locale, boolean updateResources) {
           if (locale != null) {
               Locale.setDefault(locale);
               VineAPI.getInstance(context).refreshLocale();
               if (updateResources) {
                   Resources resources = context.getApplicationContext().getResources();
                   Configuration config = resources.getConfiguration();
                   config.locale = locale;
                   resources.updateConfiguration(config, resources.getDisplayMetrics());
                        }
                    }
                }

       private static void forceCustomLocale(Context context) {
           forceCustomLocale(context, getCustomLocale(context), true);
                }

       private static Locale getCustomLocale(Context context) {
                    Locale locale;
           SharedPreferences prefs = Util.getDefaultSharedPrefs(context);
           if (prefs.getBoolean(Settings.PREF_CUSTOM_LOCALE_ENABLED, false)) {
               String localeCode = prefs.getString(Settings.PREF_CUSTOM_LOCALE, null);
               String countryCode = prefs.getString(Settings.PREF_CUSTOME_LOCALE_COUNTRY, null);
               if (!TextUtils.isEmpty(localeCode)) {
                   if (!TextUtils.isEmpty(countryCode)) {
                       locale = new Locale(localeCode, countryCode);
                            } else {
                       locale = new Locale(localeCode);
                            }
                   if (!TextUtils.isEmpty(locale.getISO3Language())) {
                       return locale;
                            }
                        }
                    }
                    return null;
                }

       private static boolean isUsEnglishLocale() {
           String code = Locale.getDefault().getLanguage();
           return Locale.ENGLISH.getLanguage().equals(code);
                }
            }
