            package co.vine.android.util;

            import android.content.Context;
            import android.content.SharedPreferences;
            import android.support.annotation.NonNull;
            import android.text.TextUtils;
            import co.vine.android.api.VineClientFlags;
            import co.vine.android.client.AppController;
            import co.vine.android.player.SdkVideoView;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 31 */    public class ClientFlagsHelper {
                public static final long CLIENTFLAGS_THROTTLE = 300000;
                public static final String PREF_API_HOST = "client_flags_api_host";
                public static final String PREF_CLIENT_FLAGS_LAST_CHANGED = "client_flags_last_changed_millis";
                public static final String PREF_CLIENT_FLAGS_LAST_CHECKED = "client_flags_last_checked_millis";
                public static final String PREF_CLIENT_FLAGS_TTL = "client_flags_ttl";
                public static final String PREF_CLIENT_FLAGS_USE_VINE_PLAYER = "client_flags_use_vine_layer";
                public static final String PREF_DOUBLE_TAP_TO_LIKE_ON_PAUSE = "client_flags_double_tap_to_like_on_pause";
                public static final String PREF_EXPLORE_HOST = "client_flags_explore_host";
                public static final String PREF_MEDIA_HOST = "client_flags_media_host";
                public static final String PREF_MESSAGE_TEXT = "client_flags_message_text";
                public static final String PREF_MESSAGE_TITLE = "client_flags_message_title";
                public static final String PREF_PREFETCH_ENABLED = "client_flags_prefetch_enabled";
                public static final String PREF_RTC_HOST = "client_flags_rtc_host";

/* 32 */        public static boolean isClientFlagsTtlInRange(@NonNull Context context) {
/* 33 */            SharedPreferences prefs = Util.getDefaultSharedPrefs(context);
/* 34 */            long lastChangedMillis = prefs.getLong(PREF_CLIENT_FLAGS_LAST_CHANGED, 0L);
/* 35 */            long ttlMillis = prefs.getLong(PREF_CLIENT_FLAGS_TTL, 0L);
                    return System.currentTimeMillis() - lastChangedMillis < ttlMillis;
                }

/* 39 */        public static void updateClientFlags(@NonNull Context context, @NonNull VineClientFlags clientFlags) {
/* 40 */            SharedPreferences.Editor editor = Util.getDefaultSharedPrefs(context).edit();
/* 41 */            long ttlMillis = clientFlags.ttlSeconds * 1000;
/* 42 */            editor.putLong(PREF_CLIENT_FLAGS_TTL, ttlMillis);
/* 43 */            editor.putLong(PREF_CLIENT_FLAGS_LAST_CHANGED, System.currentTimeMillis());
/* 44 */            editor.putString(PREF_MESSAGE_TITLE, clientFlags.messageTitle);
/* 45 */            editor.putString(PREF_MESSAGE_TEXT, clientFlags.messageText);
/* 46 */            editor.putString(PREF_API_HOST, clientFlags.apiHost);
/* 47 */            editor.putString(PREF_RTC_HOST, clientFlags.rtcHost);
/* 48 */            editor.putString(PREF_MEDIA_HOST, clientFlags.mediaHost);
/* 49 */            editor.putString(PREF_EXPLORE_HOST, clientFlags.exploreHost);
/* 50 */            editor.putBoolean(PREF_DOUBLE_TAP_TO_LIKE_ON_PAUSE, clientFlags.doubleTapToLikeOnPause);
/* 51 */            editor.putBoolean(PREF_PREFETCH_ENABLED, clientFlags.prefetchEnabled);
/* 52 */            editor.putBoolean(PREF_CLIENT_FLAGS_USE_VINE_PLAYER, clientFlags.useVinePlayer);
/* 53 */            editor.apply();
                }

/* 56 */        public static boolean getUseVinePlayer(@NonNull Context context) {
/* 57 */            return Util.getDefaultSharedPrefs(context).getBoolean(PREF_CLIENT_FLAGS_USE_VINE_PLAYER, SdkVideoView.getDefaultGlobalVinePlayer());
                }

/* 61 */        public static long getLastCheckMillis(@NonNull Context context) {
/* 62 */            return Util.getDefaultSharedPrefs(context).getLong(PREF_CLIENT_FLAGS_LAST_CHECKED, 0L);
                }

/* 65 */        public static boolean doubleTapToLikeOnPause(@NonNull Context context) {
/* 66 */            return Util.getDefaultSharedPrefs(context).getBoolean(PREF_DOUBLE_TAP_TO_LIKE_ON_PAUSE, true);
                }

/* 69 */        public static boolean prefetchEnabled(@NonNull Context context) {
/* 70 */            return Util.getDefaultSharedPrefs(context).getBoolean(PREF_PREFETCH_ENABLED, true);
                }

/* 73 */        public static void setLastCheckMillis(@NonNull Context context) {
/* 74 */            Util.getDefaultSharedPrefs(context).edit().putLong(PREF_CLIENT_FLAGS_LAST_CHECKED, System.currentTimeMillis()).apply();
                }

/* 78 */        public static boolean hostsDidChange(@NonNull Context context, @NonNull VineClientFlags clientFlags) {
/* 79 */            SharedPreferences prefs = Util.getDefaultSharedPrefs(context);
/* 80 */            String apiHost = prefs.getString(PREF_API_HOST, null);
/* 81 */            String rtcHost = prefs.getString(PREF_RTC_HOST, null);
/* 82 */            String mediaHost = prefs.getString(PREF_MEDIA_HOST, null);
/* 83 */            String exploreHost = prefs.getString(PREF_EXPLORE_HOST, null);
/* 85 */            if (apiHost != null) {
                        if (!apiHost.equals(clientFlags.apiHost)) {
                            return true;
                        }
                    } else if (clientFlags.apiHost != null) {
                        return true;
                    }
/* 87 */            if (rtcHost != null) {
                        if (!rtcHost.equals(clientFlags.rtcHost)) {
                            return true;
                        }
                    } else if (clientFlags.rtcHost != null) {
                        return true;
                    }
/* 89 */            if (mediaHost != null) {
                        if (!mediaHost.equals(clientFlags.mediaHost)) {
                            return true;
                        }
                    } else if (clientFlags.mediaHost != null) {
                        return true;
                    }
/* 91 */            if (exploreHost != null) {
                        if (!exploreHost.equals(clientFlags.exploreHost)) {
                            return true;
                        }
                    } else if (clientFlags.exploreHost != null) {
                        return true;
                    }
/* 94 */            return false;
                }

/* 97 */        public static boolean shouldShowClientFlagsBanner(@NonNull Context context) {
/* 98 */            SharedPreferences prefs = Util.getDefaultSharedPrefs(context);
/* 99 */            String messageTitle = prefs.getString(PREF_MESSAGE_TITLE, null);
/* 100 */           String messageText = prefs.getString(PREF_MESSAGE_TEXT, null);
                    return (TextUtils.isEmpty(messageTitle) && TextUtils.isEmpty(messageText)) ? false : true;
                }

/* 109 */       public static boolean zeroRatingIsEnabled(@NonNull Context context) {
/* 110 */           SharedPreferences prefs = Util.getDefaultSharedPrefs(context);
/* 111 */           String apiHost = prefs.getString(PREF_API_HOST, null);
                    return !TextUtils.isEmpty(apiHost);
                }

/* 116 */       public static void fetchClientFlags(Context context, boolean abortOnHostChange, boolean force) {
/* 117 */           if (!force) {
                        try {
                            if (isClientFlagRateLimited(context)) {
/* 123 */                       return;
                            }
                        } catch (Exception e) {
/* 121 */                   CrashUtil.logException(e);
/* 123 */                   return;
                        }
                    }
/* 118 */           AppController.getInstance(context).fetchClientFlags(abortOnHostChange);
                }

/* 125 */       private static boolean isClientFlagRateLimited(Context context) {
/* 126 */           long lastChecked = getLastCheckMillis(context);
                    boolean rateLimitClientFlagsFetch = System.currentTimeMillis() - lastChecked < 300000;
                    return rateLimitClientFlagsFetch || isClientFlagsTtlInRange(context);
                }
            }
