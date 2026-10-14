            package co.vine.android.util;

            import android.content.Context;
            import android.content.res.Resources;
            import android.support.v4.os.EnvironmentCompat;
            import android.text.TextUtils;
            import android.util.Log;
            import co.vine.android.C0257R;
            import co.vine.android.HomeTabActivity;
            import co.vine.android.recorder.RecordConfigUtils;
            import com.edisonwang.android.slog.SLog;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 34 */    public class BuildUtil {
                public static final int APP_TYPE_EXPLORE = 2;
                public static final int APP_TYPE_REGULAR = 1;
                public static final int ENVIRONMENT_DEV = 2;
                public static final int ENVIRONMENT_PRODUCTION = 1;
                public static final boolean EPHEMERAL_ENABLED = false;
                public static final int MARKET_AMAZON = 2;
                public static final int MARKET_ASTAR = 3;
                public static final int MARKET_GOOGLE = 1;
                public static final String RESOURCE_PACKAGE_NAME = "co.vine.android";
                private static int sAppType;
                private static int sEnvironment;
                private static boolean sLogsOn;
                private static int sMarket;
                private static String sVersionName;
                private static Boolean isOldDeviceOrLowEndDevice = null;
                private static final boolean sIsHwEncodingEnabled = RecordConfigUtils.HW_ENABLED;
                private static final boolean sIsSwEncodingEnabled = RecordConfigUtils.SW_ENABLED;

                static {
/* 41 */            Log.i("bootstrap", "started.");
/* 42 */            setup("google", "regular", "co.vine.android", "production", false);
/* 44 */            CrashUtil.setup(false);
/* 45 */            PlayerUtil.setup("co.vine.android");
/* 46 */            SLog.setup(false, "amazon".equals("google"), "co.vine.android");
                }

/* 52 */        public static void setup(String market, String appType, String authority, String environment, boolean logsOn) {
                    switch (market) {
                        case "amazon":
/* 55 */                    sMarket = 2;
                            break;
                        case "astar":
/* 58 */                    sMarket = 3;
                            break;
                        default:
/* 61 */                    sMarket = 1;
                            break;
                    }
/* 65 */            if ("production".equals(environment)) {
/* 66 */                sEnvironment = 1;
                    } else {
/* 68 */                sEnvironment = 2;
                    }
/* 71 */            if (HomeTabActivity.TAG_EXPLORE.equals(appType)) {
/* 72 */                sAppType = 2;
                    } else {
/* 74 */                sAppType = 1;
                    }
/* 77 */            sLogsOn = logsOn;
/* 79 */            if (!TextUtils.isEmpty(authority)) {
/* 80 */                CrossConstants.AUTHORITY = authority;
                    } else {
/* 82 */                CrossConstants.AUTHORITY = "co.vine.android";
                    }
/* 85 */            CrossConstants.BROADCAST_PERMISSION = CrossConstants.AUTHORITY + ".BROADCAST";
/* 87 */            CrossConstants.RES_RAW_CA = C0257R.raw.cacerts;
/* 89 */            CrossConstants.RES_VINE_GREEN = C0257R.color.vine_green;
                }

/* 93 */        public static String getVersionName(Context context) {
                    try {
/* 94 */                if (sVersionName == null) {
/* 95 */                    sVersionName = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
                        }
/* 98 */                return sVersionName;
                    } catch (Throwable e) {
/* 100 */               CrashUtil.logException(e);
/* 101 */               return EnvironmentCompat.MEDIA_UNKNOWN;
                    }
                }

/* 105 */       public static boolean isIsHwEncodingEnabled() {
/* 106 */           return sIsHwEncodingEnabled;
                }

/* 109 */       public static boolean isAmazon() {
                    return sMarket == 2;
                }

/* 113 */       public static boolean isGoogle() {
                    return sMarket == 1;
                }

/* 117 */       public static boolean isAStar() {
                    return sMarket == 3;
                }

/* 121 */       public static boolean isProduction() {
                    return sEnvironment == 1;
                }

/* 125 */       public static boolean isRegular() {
                    return sAppType == 1;
                }

/* 129 */       public static boolean isExplore() {
                    return sAppType == 2;
                }

                /* JADX WARN: Code duplicated, block: B:11:0x001d  */
/* 133 */       public static boolean isOldDeviceOrLowEndDevice(Context context) {
                    boolean z = false;
/* 134 */           if (isOldDeviceOrLowEndDevice == null) {
/* 135 */               if (!isExplore()) {
                            z = SystemUtil.getMemoryRatio(context, !isExplore()) < 1.0d;
                        }
                        isOldDeviceOrLowEndDevice = Boolean.valueOf(z);
                    }
/* 137 */           return isOldDeviceOrLowEndDevice.booleanValue();
                }

/* 140 */       public static boolean isLogsOn() {
/* 141 */           return sLogsOn;
                }

/* 144 */       public static int getMarket() {
/* 145 */           return sMarket;
                }

/* 148 */       public static boolean isI18nOn() {
/* 149 */           return sLogsOn;
                }

/* 152 */       public static String getAuthority() {
/* 153 */           return getAuthority("");
                }

/* 156 */       public static String getAuthority(String suffix) {
/* 157 */           return CrossConstants.getAuthority(suffix);
                }

/* 160 */       public static String getBaseUrl(Resources context) {
/* 161 */           return context.getString(C0257R.string.vine_api);
                }

/* 164 */       public static String getWebsiteUrl(Context context) {
/* 165 */           return context.getString(C0257R.string.vine_www);
                }

/* 168 */       public static String getMediaUrl(Resources context) {
/* 169 */           return context.getString(C0257R.string.vine_media_api);
                }

/* 172 */       public static String getAmazonBucket(Context context) {
/* 173 */           return context.getString(C0257R.string.amazon_bucket);
                }

/* 176 */       public static String getExploreUrl(Resources context) {
/* 177 */           return context.getString(C0257R.string.explore_url);
                }

/* 180 */       public static String getExplorePath(Resources context) {
/* 181 */           return context.getString(C0257R.string.explore_url_path);
                }

/* 184 */       public static String getConfigUrl(Resources context) {
/* 185 */           return context.getString(C0257R.string.vine_config);
                }

/* 188 */       public static String getStatusUrl(Context context) {
/* 189 */           return context.getString(C0257R.string.status_url);
                }

/* 192 */       public static String getRtcUrl(Resources context) {
/* 193 */           return context.getString(C0257R.string.rtc_url);
                }

/* 196 */       public static String getSenderId(Context context) {
/* 197 */           return context.getString(C0257R.string.sender_id);
                }

/* 200 */       public static void bootstrap() {
/* 201 */           Log.i("bootstrap", "completed.");
                }
            }
