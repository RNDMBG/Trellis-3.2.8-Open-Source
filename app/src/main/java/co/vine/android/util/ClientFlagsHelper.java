            package co.vine.android.util;

            import android.content.Context;
            import android.content.SharedPreferences;
            import android.support.annotation.NonNull;
            import android.text.TextUtils;
            import co.vine.android.api.VineClientFlags;
            import co.vine.android.client.AppController;
            import co.vine.android.player.SdkVideoView;
            
            
    public class ClientFlagsHelper {
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

        public static boolean isClientFlagsTtlInRange(@NonNull Context context) {
            SharedPreferences prefs = Util.getDefaultSharedPrefs(context);
            long lastChangedMillis = prefs.getLong(PREF_CLIENT_FLAGS_LAST_CHANGED, 0L);
            long ttlMillis = prefs.getLong(PREF_CLIENT_FLAGS_TTL, 0L);
                    return System.currentTimeMillis() - lastChangedMillis < ttlMillis;
                }

        public static void updateClientFlags(@NonNull Context context, @NonNull VineClientFlags clientFlags) {
            SharedPreferences.Editor editor = Util.getDefaultSharedPrefs(context).edit();
            long ttlMillis = clientFlags.ttlSeconds * 1000;
            editor.putLong(PREF_CLIENT_FLAGS_TTL, ttlMillis);
            editor.putLong(PREF_CLIENT_FLAGS_LAST_CHANGED, System.currentTimeMillis());
            editor.putString(PREF_MESSAGE_TITLE, clientFlags.messageTitle);
            editor.putString(PREF_MESSAGE_TEXT, clientFlags.messageText);
            editor.putString(PREF_API_HOST, clientFlags.apiHost);
            editor.putString(PREF_RTC_HOST, clientFlags.rtcHost);
            editor.putString(PREF_MEDIA_HOST, clientFlags.mediaHost);
            editor.putString(PREF_EXPLORE_HOST, clientFlags.exploreHost);
            editor.putBoolean(PREF_DOUBLE_TAP_TO_LIKE_ON_PAUSE, clientFlags.doubleTapToLikeOnPause);
            editor.putBoolean(PREF_PREFETCH_ENABLED, clientFlags.prefetchEnabled);
            editor.putBoolean(PREF_CLIENT_FLAGS_USE_VINE_PLAYER, clientFlags.useVinePlayer);
            editor.apply();
                }

        public static boolean getUseVinePlayer(@NonNull Context context) {
            return Util.getDefaultSharedPrefs(context).getBoolean(PREF_CLIENT_FLAGS_USE_VINE_PLAYER, SdkVideoView.getDefaultGlobalVinePlayer());
                }

        public static long getLastCheckMillis(@NonNull Context context) {
            return Util.getDefaultSharedPrefs(context).getLong(PREF_CLIENT_FLAGS_LAST_CHECKED, 0L);
                }

        public static boolean doubleTapToLikeOnPause(@NonNull Context context) {
            return Util.getDefaultSharedPrefs(context).getBoolean(PREF_DOUBLE_TAP_TO_LIKE_ON_PAUSE, true);
                }

        public static boolean prefetchEnabled(@NonNull Context context) {
            return Util.getDefaultSharedPrefs(context).getBoolean(PREF_PREFETCH_ENABLED, true);
                }

        public static void setLastCheckMillis(@NonNull Context context) {
            Util.getDefaultSharedPrefs(context).edit().putLong(PREF_CLIENT_FLAGS_LAST_CHECKED, System.currentTimeMillis()).apply();
                }

        public static boolean hostsDidChange(@NonNull Context context, @NonNull VineClientFlags clientFlags) {
            SharedPreferences prefs = Util.getDefaultSharedPrefs(context);
            String apiHost = prefs.getString(PREF_API_HOST, null);
            String rtcHost = prefs.getString(PREF_RTC_HOST, null);
            String mediaHost = prefs.getString(PREF_MEDIA_HOST, null);
            String exploreHost = prefs.getString(PREF_EXPLORE_HOST, null);
            if (apiHost != null) {
                        if (!apiHost.equals(clientFlags.apiHost)) {
                            return true;
                        }
                    } else if (clientFlags.apiHost != null) {
                        return true;
                    }
            if (rtcHost != null) {
                        if (!rtcHost.equals(clientFlags.rtcHost)) {
                            return true;
                        }
                    } else if (clientFlags.rtcHost != null) {
                        return true;
                    }
            if (mediaHost != null) {
                        if (!mediaHost.equals(clientFlags.mediaHost)) {
                            return true;
                        }
                    } else if (clientFlags.mediaHost != null) {
                        return true;
                    }
            if (exploreHost != null) {
                        if (!exploreHost.equals(clientFlags.exploreHost)) {
                            return true;
                        }
                    } else if (clientFlags.exploreHost != null) {
                        return true;
                    }
            return false;
                }

        public static boolean shouldShowClientFlagsBanner(@NonNull Context context) {
            SharedPreferences prefs = Util.getDefaultSharedPrefs(context);
            String messageTitle = prefs.getString(PREF_MESSAGE_TITLE, null);
           String messageText = prefs.getString(PREF_MESSAGE_TEXT, null);
                    return (TextUtils.isEmpty(messageTitle) && TextUtils.isEmpty(messageText)) ? false : true;
                }

       public static boolean zeroRatingIsEnabled(@NonNull Context context) {
           SharedPreferences prefs = Util.getDefaultSharedPrefs(context);
           String apiHost = prefs.getString(PREF_API_HOST, null);
                    return !TextUtils.isEmpty(apiHost);
                }

       public static void fetchClientFlags(Context context, boolean abortOnHostChange, boolean force) {
           if (!force) {
                        try {
                            if (isClientFlagRateLimited(context)) {
                       return;
                            }
                        } catch (Exception e) {
                   CrashUtil.logException(e);
                   return;
                        }
                    }
           AppController.getInstance(context).fetchClientFlags(abortOnHostChange);
                }

       private static boolean isClientFlagRateLimited(Context context) {
           long lastChecked = getLastCheckMillis(context);
                    boolean rateLimitClientFlagsFetch = System.currentTimeMillis() - lastChecked < 300000;
                    return rateLimitClientFlagsFetch || isClientFlagsTtlInRange(context);
                }
            }
