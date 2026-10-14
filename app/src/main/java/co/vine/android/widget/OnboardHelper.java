            package co.vine.android.widget;

            import android.content.Context;
            import co.vine.android.util.Util;
            
            
    public class OnboardHelper {
                public static final String PREF_CHANNEL_FOLLOW_HINT_SHOWN = "channel_follow_hint_shown";
                public static final String PREF_FAVORITE_USER_OVERLAY_SHOWN = "favorite_user_overlay_shown";
                public static final String PREF_FAVORITE_USER_TOOLTIP_SHOWN = "favorite_user_tooltip_shown";

        public static boolean getChannelFollowHintShown(Context context) {
            if (context != null) {
                return Util.getDefaultSharedPrefs(context).getBoolean(PREF_CHANNEL_FOLLOW_HINT_SHOWN, false);
                    }
                    return true;
                }

        public static void setChannelFollowHintShown(Context context) {
            if (context != null) {
                Util.getDefaultSharedPrefs(context).edit().putBoolean(PREF_CHANNEL_FOLLOW_HINT_SHOWN, true).apply();
                    }
                }

        public static boolean getFavoriteUserTooltipShown(Context context) {
            if (context != null) {
                return Util.getDefaultSharedPrefs(context).getBoolean(PREF_FAVORITE_USER_TOOLTIP_SHOWN, false);
                    }
                    return true;
                }

        public static void setFavoriteUserTooltipShown(Context context) {
            if (context != null) {
                Util.getDefaultSharedPrefs(context).edit().putBoolean(PREF_FAVORITE_USER_TOOLTIP_SHOWN, true).apply();
                    }
                }

        public static boolean hasShownFavoriteUserOverlay(Context context) {
            if (context != null) {
                return Util.getDefaultSharedPrefs(context).getBoolean(PREF_FAVORITE_USER_OVERLAY_SHOWN, false);
                    }
                    return true;
                }

        public static void setFavoriteUserOverlayShown(Context context) {
            if (context != null) {
                Util.getDefaultSharedPrefs(context).edit().putBoolean(PREF_FAVORITE_USER_OVERLAY_SHOWN, true).apply();
                    }
                }
            }
