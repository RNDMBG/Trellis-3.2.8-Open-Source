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
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 54 */    public class SystemUtil {
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

/* 55 */        public static boolean isViewServerEnabled(Context context) {
                    return SLog.sLogsOn && isTargetProcess(context, 0, TARGET_VIEW_SERVER);
                }

/* 59 */        public static boolean isRunningOnRecordingProcess(Context context) {
/* 60 */            initProcessName(context);
/* 61 */            return ":record".equals(mSubProcessName);
                }

/* 64 */        public static boolean isRunningOnServiceProcess(Context context) {
/* 65 */            initProcessName(context);
/* 66 */            return "".equals(mSubProcessName);
                }

/* 69 */        public static String getSubProcessName(Context context) {
/* 70 */            initProcessName(context);
/* 71 */            return mSubProcessName.replace(":", "");
                }

/* 74 */        public static boolean isSinglePlayerEnabled(Context context) {
                    return SLog.sIsAmazon || isTargetProcess(context, 1, ":record");
                }

                @TargetApi(14)
/* 79 */        public static int getMemoryBudgetForLargeMemoryClass(Context context) {
/* 80 */            return ((ActivityManager) context.getSystemService(HomeTabActivity.TAG_ACTIVITY)).getLargeMemoryClass();
                }

/* 83 */        public static boolean isOnMainThread() {
                    return Looper.getMainLooper().getThread() == Thread.currentThread();
                }

                /* JADX WARN: Code duplicated, block: B:10:0x0017  */
                /* JADX WARN: Code duplicated, block: B:14:0x001e A[Catch: Exception -> 0x0030, TRY_ENTER, TRY_LEAVE, TryCatch #0 {Exception -> 0x0030, blocks: (B:6:0x000b, B:8:0x0011, B:14:0x001e), top: B:20:0x000b }] */
                /* JADX WARN: Code duplicated, block: B:16:0x002b  */
/* 87 */        public static double getMemoryRatio(Context context, boolean isLargeMemoryClassEnabled) {
                    double d;
                    if (MEMORY_RATIO == -1.0d) {
/* 89 */                int mem = 0;
/* 91 */                if (!isLargeMemoryClassEnabled) {
/* 94 */                    mem = ((ActivityManager) context.getSystemService(HomeTabActivity.TAG_ACTIVITY)).getMemoryClass();
/* 99 */                    if (mem == 0) {
                                d = 1.0d;
                            } else {
/* 99 */                        d = ((double) mem) / 128.0d;
                            }
                            MEMORY_RATIO = d;
                        } else {
                            try {
                                if (Build.VERSION.SDK_INT >= 14) {
/* 92 */                            mem = getMemoryBudgetForLargeMemoryClass(context);
                                } else {
/* 94 */                            mem = ((ActivityManager) context.getSystemService(HomeTabActivity.TAG_ACTIVITY)).getMemoryClass();
                                }
                            } catch (Exception e) {
                            }
/* 99 */                    if (mem == 0) {
                                d = 1.0d;
                            } else {
/* 99 */                        d = ((double) mem) / 128.0d;
                            }
                            MEMORY_RATIO = d;
                        }
                    }
/* 101 */           return MEMORY_RATIO;
                }

/* 104 */       private static synchronized void initProcessName(Context context) {
                    try {
                        if (mProcessName == null) {
/* 106 */                   mProcessName = context.getPackageName();
/* 107 */                   ActivityManager am = (ActivityManager) context.getSystemService(HomeTabActivity.TAG_ACTIVITY);
/* 108 */                   List<ActivityManager.RunningAppProcessInfo> procInfos = am.getRunningAppProcesses();
/* 109 */                   if (procInfos != null) {
                                for (ActivityManager.RunningAppProcessInfo procInfo : procInfos) {
/* 111 */                           if (Process.myPid() == procInfo.pid) {
/* 112 */                               mProcessName = procInfo.processName;
                                    }
                                }
                            }
/* 116 */                   int index = mProcessName.indexOf(58);
/* 117 */                   if (index != -1) {
/* 118 */                       mSubProcessName = mProcessName.substring(index);
                            } else {
/* 120 */                       mSubProcessName = "";
                            }
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }

/* 126 */       public static String getPathFromAsset(String fileName) {
/* 127 */           return "file:///android_asset/" + fileName;
                }

/* 130 */       public static String getPathFromResourceRaw(Context context, int res) {
/* 131 */           return "android.resource://" + context.getPackageName() + "/" + res;
                }

/* 134 */       public static void onConnectivityChanged(Context context) {
/* 135 */           sNetworkContext = getNetworkContext(context);
                }

/* 138 */       public static boolean isOnWifi(Context context) {
/* 139 */           ConnectivityManager cm = (ConnectivityManager) context.getSystemService("connectivity");
/* 140 */           NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
/* 141 */           if (activeNetwork == null || !activeNetwork.isConnected()) {
                        return false;
                    }
/* 142 */           int type = activeNetwork.getType();
                    return type == 9 || type == 1;
                }

/* 149 */       public static boolean isRoaming(Context context) {
/* 150 */           ConnectivityManager cm = (ConnectivityManager) context.getSystemService("connectivity");
/* 151 */           NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
                    return activeNetwork != null && activeNetwork.isRoaming();
                }

/* 169 */       public static PrefBooleanState isNormalVideoPlayable(Context context) {
/* 170 */           SharedPreferences sharedPref = context.getSharedPreferences("normalVideoTestPlayable", 0);
/* 171 */           return PrefBooleanState.valueOf(sharedPref.getString("normalVideoTestPlayable", PrefBooleanState.UNKNOWN.name()));
                }

/* 174 */       public static void setNormalVideoPlayable(Context context, boolean isPlayable) {
                    context.getSharedPreferences("normalVideoTestPlayable", 0).edit().putString("normalVideoTestPlayable", (isPlayable ? PrefBooleanState.TRUE : PrefBooleanState.FALSE).name()).apply();
                }

/* 180 */       private static boolean isTargetProcess(Context context, int targetKey, String targetProcess) {
/* 181 */           Boolean oldInfo = mTargetProcessInfo.get(targetKey, null);
/* 182 */           if (oldInfo == null) {
/* 183 */               initProcessName(context);
/* 184 */               if (targetProcess == null) {
/* 185 */                   oldInfo = Boolean.valueOf(SLog.getAuthority().equals(mProcessName));
                        } else {
/* 187 */                   oldInfo = Boolean.valueOf(targetProcess.equals(mSubProcessName));
                        }
/* 189 */               mTargetProcessInfo.put(targetKey, oldInfo);
/* 190 */               SLog.m467i("Is target process for {} a {}: {}.", Integer.valueOf(targetKey), targetProcess, oldInfo);
                    }
/* 192 */           return oldInfo.booleanValue();
                }

                @TargetApi(13)
/* 196 */       public static Point getDisplaySize(Display display) {
/* 197 */           if (Build.VERSION.SDK_INT >= 13) {
/* 198 */               Point p = new Point();
/* 199 */               display.getSize(p);
                        return p;
                    }
/* 202 */           Point p2 = new Point();
/* 203 */           p2.x = display.getWidth();
/* 204 */           p2.y = display.getHeight();
                    return p2;
                }

/* 209 */       public static void copyToClip(Context context, String title, String text) {
/* 210 */           if (Build.VERSION.SDK_INT < 11) {
/* 211 */               ClipboardManager clipboard = (ClipboardManager) context.getSystemService("clipboard");
/* 212 */               clipboard.setText(text);
                    } else {
/* 214 */               android.content.ClipboardManager clipboard2 = (android.content.ClipboardManager) context.getSystemService("clipboard");
/* 215 */               ClipData clip = ClipData.newPlainText(title, text);
/* 216 */               clipboard2.setPrimaryClip(clip);
                    }
                }

/* 220 */       public static Point getDisplaySize(Context context) {
/* 221 */           return getDisplaySize(((WindowManager) context.getSystemService("window")).getDefaultDisplay());
                }

/* 225 */       public static Point getDisplaySize(Activity activity) {
/* 226 */           return getDisplaySize(activity.getWindowManager().getDefaultDisplay());
                }

/* 230 */       public static void quietlyEnsureParentExists(File f) {
                    File parent;
                    try {
/* 231 */               if (!f.exists() && (parent = f.getParentFile()) != null && !parent.exists()) {
/* 234 */                   CrashUtil.logException(new VineLoggingException("Invalid folder, but we caught it."));
/* 235 */                   parent.mkdirs();
                        }
                    } catch (Exception e) {
/* 239 */               CrashUtil.logException(new VineLoggingException("Failed to make parent folder."));
                    }
                }

/* 247 */       public static class SystemNetworkContext {
                    public final int cmType;
                    public final String country;
                    public final String operatorName;
                    public final int tmType;

/* 248 */           public SystemNetworkContext(String country, String operatorName, int tmType, int cmType) {
/* 250 */               this.country = country;
/* 251 */               this.operatorName = operatorName;
/* 252 */               this.tmType = tmType;
/* 253 */               this.cmType = cmType;
                    }

/* 257 */           public String toString() {
                        Object[] objArr = new Object[3];
                        objArr[0] = Integer.valueOf((this.cmType * 1000) + this.tmType);
                        objArr[1] = this.country == null ? EnvironmentCompat.MEDIA_UNKNOWN : this.country;
                        objArr[2] = this.operatorName == null ? EnvironmentCompat.MEDIA_UNKNOWN : this.operatorName;
/* 258 */               return MessageFormatter.toStringMessage("{} - ({} - {})", objArr);
                    }
                }

/* 266 */       public static SystemNetworkContext getLastNetworkContext(Context context) {
/* 267 */           if (sNetworkContext == null) {
/* 268 */               sNetworkContext = getNetworkContext(context);
                    }
/* 270 */           return sNetworkContext;
                }

/* 274 */       private static SystemNetworkContext getNetworkContext(Context context) {
/* 275 */           String country = null;
/* 276 */           int tmType = -1;
/* 277 */           String operatorName = null;
                    try {
/* 279 */               TelephonyManager tm = (TelephonyManager) context.getSystemService(VineService.EXTRA_PHONE);
/* 280 */               if (tm != null) {
/* 281 */                   country = tm.getNetworkCountryIso();
/* 282 */                   tmType = tm.getNetworkType();
/* 283 */                   operatorName = tm.getNetworkOperatorName();
                        }
                    } catch (Throwable th) {
                    }
/* 290 */           int cmType = -1;
                    try {
/* 293 */               ConnectivityManager cm = (ConnectivityManager) context.getSystemService("connectivity");
/* 294 */               NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
/* 295 */               if (activeNetwork != null && activeNetwork.isConnected()) {
/* 296 */                   cmType = activeNetwork.getType();
                        }
                    } catch (Throwable th2) {
                    }
/* 301 */           return new SystemNetworkContext(country, operatorName, tmType, cmType);
                }

/* 304 */       public static boolean isBatteryCharging(Context context) {
/* 305 */           Intent batteryStatus = context.registerReceiver(null, new IntentFilter("android.intent.action.BATTERY_CHANGED"));
/* 306 */           if (batteryStatus == null) {
                        return false;
                    }
/* 307 */           int status = batteryStatus.getIntExtra("status", -1);
                    return status == 2 || status == 5;
                }
            }
