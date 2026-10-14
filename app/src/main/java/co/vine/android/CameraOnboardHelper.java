            package co.vine.android;

            import android.content.Context;
            import android.content.SharedPreferences;
            import android.support.annotation.Nullable;
            import co.vine.android.util.Util;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 20 */    public class CameraOnboardHelper {
                public static final String PREF_CAMERA_ONBOARD_STEP = "camera_onboard_step";
                public static final String PREF_DID_SHOW_CAMERA_OVERLAY = "did_show_camera_overlay";
                public static final String STEP_DELETE = "delete";
                public static final String STEP_DELETE_2 = "delete_2";
                public static final String STEP_GRAB = "grab";
                public static final String STEP_MORE_TOOLS = "more_tools";

/* 21 */        public static boolean getHasShownOverlay(Context context) {
/* 22 */            if (context == null) {
                        return true;
                    }
/* 23 */            SharedPreferences sp = getDefaultSharedPrefs(context);
/* 24 */            return sp.getBoolean(PREF_DID_SHOW_CAMERA_OVERLAY, false);
                }

/* 29 */        private static SharedPreferences getDefaultSharedPrefs(Context context) {
/* 30 */            return Util.getSharedPrefs(context, "CameraOnBoardHelper");
                }

                @Nullable
/* 34 */        public static String getLastCompletedStep(Context context) {
/* 35 */            if (context == null) {
                        return "dont_onboard";
                    }
/* 36 */            SharedPreferences sp = getDefaultSharedPrefs(context);
/* 37 */            return sp.getString(PREF_CAMERA_ONBOARD_STEP, null);
                }

/* 42 */        public static void setHasShownOverlay(Context context, boolean didShow) {
/* 43 */            if (context != null) {
/* 44 */                SharedPreferences sp = getDefaultSharedPrefs(context);
/* 45 */                sp.edit().putBoolean(PREF_DID_SHOW_CAMERA_OVERLAY, didShow).apply();
                    }
                }

/* 49 */        public static void setLastCompletedStep(Context context, String step) {
/* 50 */            if (context != null) {
/* 51 */                SharedPreferences sp = getDefaultSharedPrefs(context);
/* 52 */                sp.edit().putString(PREF_CAMERA_ONBOARD_STEP, step).apply();
                    }
                }
            }
