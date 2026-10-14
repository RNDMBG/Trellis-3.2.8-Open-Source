            package co.vine.android.util;

            import android.content.Context;
            import android.content.SharedPreferences;
            
            
    public class CrossAnalytics {
                private static final String PREF_NAME = "cross_analytics";
                public static final String PRE_INSTALL_SIGN_UP = "pre_install_sign up";
                private static CrossAnalytics sINSTANCE;
                private final SharedPreferences mPrefs;

        private CrossAnalytics(Context context) {
            this.mPrefs = context.getSharedPreferences(PREF_NAME, 0);
                }

        public static CrossAnalytics getInstance(Context context) {
            if (sINSTANCE == null) {
                sINSTANCE = new CrossAnalytics(context);
                    }
            return sINSTANCE;
                }

        public void set(String key, String value) {
            this.mPrefs.edit().putString(key, value).apply();
                }

        public void clear(String key) {
            this.mPrefs.edit().remove(key).apply();
                }

        public String get(String key) {
            return this.mPrefs.getString(key, null);
                }

        public boolean isSet(String key) {
                    return get(key) != null;
                }
            }
