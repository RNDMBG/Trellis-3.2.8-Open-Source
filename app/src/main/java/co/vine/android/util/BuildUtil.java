            package co.vine.android.util;

            import android.content.Context;
            import android.content.res.Resources;
            import android.support.v4.os.EnvironmentCompat;
            import android.text.TextUtils;
            import android.util.Log;
            import co.vine.android.R;
            import co.vine.android.HomeTabActivity;
            import co.vine.android.recorder.RecordConfigUtils;
            import com.edisonwang.android.slog.SLog;
            
            
    public class BuildUtil {
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
            Log.i("bootstrap", "started.");
            setup("google", "regular", "co.vine.android", "production", false);
            CrashUtil.setup(false);
            PlayerUtil.setup("co.vine.android");
            SLog.setup(false, "amazon".equals("google"), "co.vine.android");
                }

        public static void setup(String market, String appType, String authority, String environment, boolean logsOn) {
                    switch (market) {
                        case "amazon":
                    sMarket = 2;
                            break;
                        case "astar":
                    sMarket = 3;
                            break;
                        default:
                    sMarket = 1;
                            break;
                    }
            if ("production".equals(environment)) {
                sEnvironment = 1;
                    } else {
                sEnvironment = 2;
                    }
            if (HomeTabActivity.TAG_EXPLORE.equals(appType)) {
                sAppType = 2;
                    } else {
                sAppType = 1;
                    }
            sLogsOn = logsOn;
            if (!TextUtils.isEmpty(authority)) {
                CrossConstants.AUTHORITY = authority;
                    } else {
                CrossConstants.AUTHORITY = "co.vine.android";
                    }
            CrossConstants.BROADCAST_PERMISSION = CrossConstants.AUTHORITY + ".BROADCAST";
            CrossConstants.RES_RAW_CA = R.raw.cacerts;
            CrossConstants.RES_VINE_GREEN = R.color.vine_green;
                }

        public static String getVersionName(Context context) {
                    try {
                if (sVersionName == null) {
                    sVersionName = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
                        }
                return sVersionName;
                    } catch (Throwable e) {
               CrashUtil.logException(e);
               return EnvironmentCompat.MEDIA_UNKNOWN;
                    }
                }

       public static boolean isIsHwEncodingEnabled() {
           return sIsHwEncodingEnabled;
                }

       public static boolean isAmazon() {
                    return sMarket == 2;
                }

       public static boolean isGoogle() {
                    return sMarket == 1;
                }

       public static boolean isAStar() {
                    return sMarket == 3;
                }

       public static boolean isProduction() {
                    return sEnvironment == 1;
                }

       public static boolean isRegular() {
                    return sAppType == 1;
                }

       public static boolean isExplore() {
                    return sAppType == 2;
                }

                
       public static boolean isOldDeviceOrLowEndDevice(Context context) {
                    boolean z = false;
           if (isOldDeviceOrLowEndDevice == null) {
               if (!isExplore()) {
                            z = SystemUtil.getMemoryRatio(context, !isExplore()) < 1.0d;
                        }
                        isOldDeviceOrLowEndDevice = Boolean.valueOf(z);
                    }
           return isOldDeviceOrLowEndDevice.booleanValue();
                }

       public static boolean isLogsOn() {
           return sLogsOn;
                }

       public static int getMarket() {
           return sMarket;
                }

       public static boolean isI18nOn() {
           return sLogsOn;
                }

       public static String getAuthority() {
           return getAuthority("");
                }

       public static String getAuthority(String suffix) {
           return CrossConstants.getAuthority(suffix);
                }

       public static String getBaseUrl(Resources context) {
           return context.getString(R.string.vine_api);
                }

       public static String getWebsiteUrl(Context context) {
           return context.getString(R.string.vine_www);
                }

       public static String getMediaUrl(Resources context) {
           return context.getString(R.string.vine_media_api);
                }

       public static String getAmazonBucket(Context context) {
           return context.getString(R.string.amazon_bucket);
                }

       public static String getExploreUrl(Resources context) {
           return context.getString(R.string.explore_url);
                }

       public static String getExplorePath(Resources context) {
           return context.getString(R.string.explore_url_path);
                }

       public static String getConfigUrl(Resources context) {
           return context.getString(R.string.vine_config);
                }

       public static String getStatusUrl(Context context) {
           return context.getString(R.string.status_url);
                }

       public static String getRtcUrl(Resources context) {
           return context.getString(R.string.rtc_url);
                }

       public static String getSenderId(Context context) {
           return context.getString(R.string.sender_id);
                }

       public static void bootstrap() {
           Log.i("bootstrap", "completed.");
                }
            }
