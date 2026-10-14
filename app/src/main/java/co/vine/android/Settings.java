            package co.vine.android;

            import android.content.Context;
            import co.vine.android.util.CommonUtil;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 151 */   public class Settings {
                public static final String ABOUT = "about";
                public static final String ATTRIBUTION = "attribution";
                public static final String CLEAR_CACHE = "cache";
                public static final String FEEDBACK = "feedback";
                public static final String LOGOUT = "logout";
                public static final String PASSWORD = "password";

                /* JADX INFO: renamed from: PP */
                public static final String f91PP = "pp";
                public static final String PREF_ACCEPT_OON = "accept_out_of_network_messages";
                public static final String PREF_AUTHORED_POST_COUNT = "profile_authored_post_count";
                public static final String PREF_AVATAR_URL = "settings_profile_avatar_url";
                public static final String PREF_COLOR_INDEX = "color_index";
                public static final String PREF_CUSTOME_LOCALE_COUNTRY = "pref_custom_locale_country";
                public static final String PREF_CUSTOM_LOCALE = "pref_custom_locale";
                public static final String PREF_CUSTOM_LOCALE_ENABLED = "pref_custom_locale_enabled";
                public static final String PREF_DESCRIPTION = "settings_profile_description";
                public static final String PREF_EDITION = "settings_edition";
                public static final String PREF_EMAIL = "settings_profile_email";
                public static final String PREF_EMAIL_DISCOVERABLE = "email_discoverable";
                public static final String PREF_EMAIL_VERIFIED = "profile_email_verified";
                public static final String PREF_ENABLE_ADDRESS_BOOK = "enable_address_book";
                public static final String PREF_EXPLICIT = "settings_explicit";
                public static final String PREF_FOLLOWER_COUNT = "profile_follower_count";
                public static final String PREF_FOLLOWING_COUNT = "profile_follow_count";
                public static final String PREF_FOLLOW_EDITORS_PICKS = "settings_follow_editors_picks";
                public static final String PREF_FOLLOW_REQUEST_COUNT = "settings_follow_pref_count";
                public static final String PREF_GINGERBREAD_NOTIF_DISMISSED = "pref_gb_notif_dismissed";
                public static final String PREF_HIDE_PROFILE_REPOSTS = "profile_hide_reposts";
                public static final String PREF_LAST_CLEANUP = "last_cleanup";
                public static final String PREF_LAST_SERVER_STATUS_REFRESH = "server_upload_last_refresh";
                public static final String PREF_LIKE_COUNT = "profile_like_count";
                public static final String PREF_LOCATION = "settings_profile_location";
                public static final String PREF_LOOP_COUNT = "profile_loop_count";
                public static final String PREF_LOOP_COUNT_RECORDS = "pref_loop_count_records";
                public static final String PREF_MOST_RECENT_UNREAD_MESSAGES_COUNT = "pref_mrumc";
                public static final String PREF_NAME = "settings_profile_name";
                public static final String PREF_NOTIFICATIONS_ENABLED = "pref_notifications_enabled";
                public static final String PREF_NOTIFICATIONS_LIGHT = "pref_notifications_light";
                public static final String PREF_NOTIFICATIONS_RINGTONE = "pref_notifications_ringtone";
                public static final String PREF_NOTIFICATIONS_SOUND = "pref_notifications_sound";
                public static final String PREF_NOTIFICATIONS_VIBRATE = "pref_notifications_vibrate";
                public static final String PREF_PHONE = "settings_profile_phone";
                public static final String PREF_PHONE_DISCOVERABLE = "phone_discoverable";
                public static final String PREF_PHONE_VERIFIED = "profile_phone_verified";
                public static final String PREF_POST_COUNT = "profile_post_count";
                public static final String PREF_POST_HOME_TIMELINE = "post_home_timeline";
                public static final String PREF_POST_HOME_TIMELINE_OFFSET = "post_home_timeline_offset";
                public static final String PREF_PRIVATE = "settings_private";
                public static final String PREF_PROFILE_BACKGROUND = "profile_background";
                public static final String PREF_SAVED_STATE_DATA = "pref_saved_state_data";
                public static final String PREF_SERVER_UPLOAD_TYPE = "server_upload_type";
                public static final String PREF_TWITTER_CONNECTED = "settings_twitter_connected";
                public static final String PREF_TWITTER_DISCOVERABLE = "twitter_discoverable";
                public static final String PREF_UNREAD_MESSAGES_DISMISSED = "pref_dismissed";
                public static final String PREF_UNREAD_MESSAGE_COUNT = "settings_unread_messages_count";
                public static final String PREF_UNREAD_NOTIFICATION_COUNT = "unread_notification_count";
                public static final String PREF_USER_ROW_ID = "pref_user_row_id";
                public static final int PROFILE_BACKGROUND_COLOR_DEFAULT_INDEX = 0;
                public static final String TOS = "tos";
                public static final String TWITTER = "twitter";
                public static final int[] PROFILE_BACKGROUND_COLORS = {3355443, ReportingFragment.BLOCK_COLOR, 16742733, 16756544, 6864736, 3394751, 7188722, 5276389, 7893196, 15890860};
                public static final int DEFAULT_PROFILE_COLOR = PROFILE_BACKGROUND_COLORS[0];

/* 152 */       public static boolean isNotificationEnabled(Context context) {
/* 153 */           return CommonUtil.getDefaultSharedPrefs(context).getBoolean(PREF_NOTIFICATIONS_ENABLED, true);
                }
            }
