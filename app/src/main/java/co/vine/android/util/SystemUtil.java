            package co.vine.android.util;

            import android.annotation.TargetApi;
            import android.app.Activity;
            import android.app.ActivityManager;
            import android.content.ClipData;
            import android.content.Context;
            import android.content.Intent;
            import android.content.IntentFilter;
            import android.content.SharedPreferences;
            import android.graphics.Point;
            import android.net.ConnectivityManager;
            import android.net.NetworkInfo;
            import android.os.Build;
            import android.os.Looper;
            import android.os.Process;
            import android.support.v4.os.EnvironmentCompat;
            import android.telephony.TelephonyManager;
            import android.text.ClipboardManager;
            import android.view.Display;
            import android.view.WindowManager;
            import co.vine.android.HomeTabActivity;
            import co.vine.android.VineLoggingException;
            import co.vine.android.service.VineService;
            import com.edisonwang.android.slog.MessageFormatter;
            import com.edisonwang.android.slog.SLog;
            import java.io.File;
            import java.util.List;
            
            
    public class SystemUtil {
                private static final int TARGET_KEY_SINGLE_PLAYER = 1;
                private static final int TARGET_KEY_VIEW_SERVER = 0;
                private static final String TARGET_PROCESS_MAIN = "";
                private static final String TARGET_PROCESS_RECORD = ":record";
                private static final String TARGET_SINGLE_PLAYER = ":record";
                private static String mProcessName;
                private static String mSubProcessName;
                private static SystemNetworkContext sNetworkContext;
                private static double MEMORY_RATIO = -1.0d;
                private static final android.util.SparseArray<Boolean> mTargetProcessInfo = new android.util.SparseArray<>();
                private static final String TARGET_PROCESS_DEFAULT = null;
                private static final String TARGET_VIEW_SERVER = TARGET_PROCESS_DEFAULT;

                public enum PrefBooleanState {
                    UNKNOWN,
                    TRUE,
                    FALSE
                }

        public static boolean isViewServerEnabled(Context context) {
                    return SLog.sLogsOn && isTargetProcess(context, 0, TARGET_VIEW_SERVER);
                }

        public static boolean isRunningOnRecordingProcess(Context context) {
            initProcessName(context);
            return ":record".equals(mSubProcessName);
                }

        public static boolean isRunningOnServiceProcess(Context context) {
            initProcessName(context);
            return "".equals(mSubProcessName);
                }

        public static String getSubProcessName(Context context) {
            initProcessName(context);
            return mSubProcessName.replace(":", "");
                }

        public static boolean isSinglePlayerEnabled(Context context) {
                    return SLog.sIsAmazon || isTargetProcess(context, 1, ":record");
                }

                @TargetApi(14)
        public static int getMemoryBudgetForLargeMemoryClass(Context context) {
            return ((ActivityManager) context.getSystemService(HomeTabActivity.TAG_ACTIVITY)).getLargeMemoryClass();
                }

        public static boolean isOnMainThread() {
                    return Looper.getMainLooper().getThread() == Thread.currentThread();
                }

                
                
                
        public static double getMemoryRatio(Context context, boolean isLargeMemoryClassEnabled) {
                    double d;
                    if (MEMORY_RATIO == -1.0d) {
                int mem = 0;
                if (!isLargeMemoryClassEnabled) {
                    mem = ((ActivityManager) context.getSystemService(HomeTabActivity.TAG_ACTIVITY)).getMemoryClass();
                    if (mem == 0) {
                                d = 1.0d;
                            } else {
                        d = ((double) mem) / 128.0d;
                            }
                            MEMORY_RATIO = d;
                        } else {
                            try {
                                if (Build.VERSION.SDK_INT >= 14) {
                            mem = getMemoryBudgetForLargeMemoryClass(context);
                                } else {
                            mem = ((ActivityManager) context.getSystemService(HomeTabActivity.TAG_ACTIVITY)).getMemoryClass();
                                }
                            } catch (Exception e) {
                            }
                    if (mem == 0) {
                                d = 1.0d;
                            } else {
                        d = ((double) mem) / 128.0d;
                            }
                            MEMORY_RATIO = d;
                        }
                    }
           return MEMORY_RATIO;
                }

       private static synchronized void initProcessName(Context context) {
                    try {
                        if (mProcessName == null) {
                   mProcessName = context.getPackageName();
                   ActivityManager am = (ActivityManager) context.getSystemService(HomeTabActivity.TAG_ACTIVITY);
                   List<ActivityManager.RunningAppProcessInfo> procInfos = am.getRunningAppProcesses();
                   if (procInfos != null) {
                                for (ActivityManager.RunningAppProcessInfo procInfo : procInfos) {
                           if (Process.myPid() == procInfo.pid) {
                               mProcessName = procInfo.processName;
                                    }
                                }
                            }
                   int index = mProcessName.indexOf(58);
                   if (index != -1) {
                       mSubProcessName = mProcessName.substring(index);
                            } else {
                       mSubProcessName = "";
                            }
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }

       public static String getPathFromAsset(String fileName) {
           return "file:///android_asset/" + fileName;
                }

       public static String getPathFromResourceRaw(Context context, int res) {
           return "android.resource://" + context.getPackageName() + "/" + res;
                }

       public static void onConnectivityChanged(Context context) {
           sNetworkContext = getNetworkContext(context);
                }

       public static boolean isOnWifi(Context context) {
           ConnectivityManager cm = (ConnectivityManager) context.getSystemService("connectivity");
           NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
           if (activeNetwork == null || !activeNetwork.isConnected()) {
                        return false;
                    }
           int type = activeNetwork.getType();
                    return type == 9 || type == 1;
                }

       public static boolean isRoaming(Context context) {
           ConnectivityManager cm = (ConnectivityManager) context.getSystemService("connectivity");
           NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
                    return activeNetwork != null && activeNetwork.isRoaming();
                }

       public static PrefBooleanState isNormalVideoPlayable(Context context) {
           SharedPreferences sharedPref = context.getSharedPreferences("normalVideoTestPlayable", 0);
           return PrefBooleanState.valueOf(sharedPref.getString("normalVideoTestPlayable", PrefBooleanState.UNKNOWN.name()));
                }

       public static void setNormalVideoPlayable(Context context, boolean isPlayable) {
                    context.getSharedPreferences("normalVideoTestPlayable", 0).edit().putString("normalVideoTestPlayable", (isPlayable ? PrefBooleanState.TRUE : PrefBooleanState.FALSE).name()).apply();
                }

       private static boolean isTargetProcess(Context context, int targetKey, String targetProcess) {
           Boolean oldInfo = mTargetProcessInfo.get(targetKey, null);
           if (oldInfo == null) {
               initProcessName(context);
               if (targetProcess == null) {
                   oldInfo = Boolean.valueOf(SLog.getAuthority().equals(mProcessName));
                        } else {
                   oldInfo = Boolean.valueOf(targetProcess.equals(mSubProcessName));
                        }
               mTargetProcessInfo.put(targetKey, oldInfo);
               SLog.m467i("Is target process for {} a {}: {}.", Integer.valueOf(targetKey), targetProcess, oldInfo);
                    }
           return oldInfo.booleanValue();
                }

                @TargetApi(13)
       public static Point getDisplaySize(Display display) {
           if (Build.VERSION.SDK_INT >= 13) {
               Point p = new Point();
               display.getSize(p);
                        return p;
                    }
           Point p2 = new Point();
           p2.x = display.getWidth();
           p2.y = display.getHeight();
                    return p2;
                }

       public static void copyToClip(Context context, String title, String text) {
           if (Build.VERSION.SDK_INT < 11) {
               ClipboardManager clipboard = (ClipboardManager) context.getSystemService("clipboard");
               clipboard.setText(text);
                    } else {
               android.content.ClipboardManager clipboard2 = (android.content.ClipboardManager) context.getSystemService("clipboard");
               ClipData clip = ClipData.newPlainText(title, text);
               clipboard2.setPrimaryClip(clip);
                    }
                }

       public static Point getDisplaySize(Context context) {
           return getDisplaySize(((WindowManager) context.getSystemService("window")).getDefaultDisplay());
                }

       public static Point getDisplaySize(Activity activity) {
           return getDisplaySize(activity.getWindowManager().getDefaultDisplay());
                }

       public static void quietlyEnsureParentExists(File f) {
                    File parent;
                    try {
               if (!f.exists() && (parent = f.getParentFile()) != null && !parent.exists()) {
                   CrashUtil.logException(new VineLoggingException("Invalid folder, but we caught it."));
                   parent.mkdirs();
                        }
                    } catch (Exception e) {
               CrashUtil.logException(new VineLoggingException("Failed to make parent folder."));
                    }
                }

       public static class SystemNetworkContext {
                    public final int cmType;
                    public final String country;
                    public final String operatorName;
                    public final int tmType;

           public SystemNetworkContext(String country, String operatorName, int tmType, int cmType) {
               this.country = country;
               this.operatorName = operatorName;
               this.tmType = tmType;
               this.cmType = cmType;
                    }

           public String toString() {
                        Object[] objArr = new Object[3];
                        objArr[0] = Integer.valueOf((this.cmType * 1000) + this.tmType);
                        objArr[1] = this.country == null ? EnvironmentCompat.MEDIA_UNKNOWN : this.country;
                        objArr[2] = this.operatorName == null ? EnvironmentCompat.MEDIA_UNKNOWN : this.operatorName;
               return MessageFormatter.toStringMessage("{} - ({} - {})", objArr);
                    }
                }

       public static SystemNetworkContext getLastNetworkContext(Context context) {
           if (sNetworkContext == null) {
               sNetworkContext = getNetworkContext(context);
                    }
           return sNetworkContext;
                }

       private static SystemNetworkContext getNetworkContext(Context context) {
           String country = null;
           int tmType = -1;
           String operatorName = null;
                    try {
               TelephonyManager tm = (TelephonyManager) context.getSystemService(VineService.EXTRA_PHONE);
               if (tm != null) {
                   country = tm.getNetworkCountryIso();
                   tmType = tm.getNetworkType();
                   operatorName = tm.getNetworkOperatorName();
                        }
                    } catch (Throwable th) {
                    }
           int cmType = -1;
                    try {
               ConnectivityManager cm = (ConnectivityManager) context.getSystemService("connectivity");
               NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
               if (activeNetwork != null && activeNetwork.isConnected()) {
                   cmType = activeNetwork.getType();
                        }
                    } catch (Throwable th2) {
                    }
           return new SystemNetworkContext(country, operatorName, tmType, cmType);
                }

       public static boolean isBatteryCharging(Context context) {
           Intent batteryStatus = context.registerReceiver(null, new IntentFilter("android.intent.action.BATTERY_CHANGED"));
           if (batteryStatus == null) {
                        return false;
                    }
           int status = batteryStatus.getIntExtra("status", -1);
                    return status == 2 || status == 5;
                }
            }
