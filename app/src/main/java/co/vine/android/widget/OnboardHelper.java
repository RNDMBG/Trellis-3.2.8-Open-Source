            package co.vine.android.widget;

            import android.content.Context;
            import co.vine.android.util.Util;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 14 */    public class OnboardHelper {
                public static final String PREF_CHANNEL_FOLLOW_HINT_SHOWN = "channel_follow_hint_shown";
                public static final String PREF_FAVORITE_USER_OVERLAY_SHOWN = "favorite_user_overlay_shown";
                public static final String PREF_FAVORITE_USER_TOOLTIP_SHOWN = "favorite_user_tooltip_shown";

/* 15 */        public static boolean getChannelFollowHintShown(Context context) {
/* 16 */            if (context != null) {
/* 17 */                return Util.getDefaultSharedPrefs(context).getBoolean(PREF_CHANNEL_FOLLOW_HINT_SHOWN, false);
                    }
                    return true;
                }

/* 22 */        public static void setChannelFollowHintShown(Context context) {
/* 23 */            if (context != null) {
/* 24 */                Util.getDefaultSharedPrefs(context).edit().putBoolean(PREF_CHANNEL_FOLLOW_HINT_SHOWN, true).apply();
                    }
                }

/* 30 */        public static boolean getFavoriteUserTooltipShown(Context context) {
/* 31 */            if (context != null) {
/* 32 */                return Util.getDefaultSharedPrefs(context).getBoolean(PREF_FAVORITE_USER_TOOLTIP_SHOWN, false);
                    }
                    return true;
                }

/* 37 */        public static void setFavoriteUserTooltipShown(Context context) {
/* 38 */            if (context != null) {
/* 39 */                Util.getDefaultSharedPrefs(context).edit().putBoolean(PREF_FAVORITE_USER_TOOLTIP_SHOWN, true).apply();
                    }
                }

/* 45 */        public static boolean hasShownFavoriteUserOverlay(Context context) {
/* 46 */            if (context != null) {
/* 47 */                return Util.getDefaultSharedPrefs(context).getBoolean(PREF_FAVORITE_USER_OVERLAY_SHOWN, false);
                    }
                    return true;
                }

/* 52 */        public static void setFavoriteUserOverlayShown(Context context) {
/* 53 */            if (context != null) {
/* 54 */                Util.getDefaultSharedPrefs(context).edit().putBoolean(PREF_FAVORITE_USER_OVERLAY_SHOWN, true).apply();
                    }
                }
            }
