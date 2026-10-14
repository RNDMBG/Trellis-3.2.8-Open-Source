            package co.vine.android;

            import android.content.Context;
            import android.content.SharedPreferences;
            import android.support.annotation.Nullable;
            import co.vine.android.util.Util;
            
            
    public class CameraOnboardHelper {
                public static final String PREF_CAMERA_ONBOARD_STEP = "camera_onboard_step";
                public static final String PREF_DID_SHOW_CAMERA_OVERLAY = "did_show_camera_overlay";
                public static final String STEP_DELETE = "delete";
                public static final String STEP_DELETE_2 = "delete_2";
                public static final String STEP_GRAB = "grab";
                public static final String STEP_MORE_TOOLS = "more_tools";

        public static boolean getHasShownOverlay(Context context) {
            if (context == null) {
                        return true;
                    }
            SharedPreferences sp = getDefaultSharedPrefs(context);
            return sp.getBoolean(PREF_DID_SHOW_CAMERA_OVERLAY, false);
                }

        private static SharedPreferences getDefaultSharedPrefs(Context context) {
            return Util.getSharedPrefs(context, "CameraOnBoardHelper");
                }

                @Nullable
        public static String getLastCompletedStep(Context context) {
            if (context == null) {
                        return "dont_onboard";
                    }
            SharedPreferences sp = getDefaultSharedPrefs(context);
            return sp.getString(PREF_CAMERA_ONBOARD_STEP, null);
                }

        public static void setHasShownOverlay(Context context, boolean didShow) {
            if (context != null) {
                SharedPreferences sp = getDefaultSharedPrefs(context);
                sp.edit().putBoolean(PREF_DID_SHOW_CAMERA_OVERLAY, didShow).apply();
                    }
                }

        public static void setLastCompletedStep(Context context, String step) {
            if (context != null) {
                SharedPreferences sp = getDefaultSharedPrefs(context);
                sp.edit().putString(PREF_CAMERA_ONBOARD_STEP, step).apply();
                    }
                }
            }
