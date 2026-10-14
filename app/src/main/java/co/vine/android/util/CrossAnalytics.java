            package co.vine.android.util;

            import android.content.Context;
            import android.content.SharedPreferences;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 16 */    public class CrossAnalytics {
                private static final String PREF_NAME = "cross_analytics";
                public static final String PRE_INSTALL_SIGN_UP = "pre_install_sign up";
                private static CrossAnalytics sINSTANCE;
                private final SharedPreferences mPrefs;

/* 17 */        private CrossAnalytics(Context context) {
/* 19 */            this.mPrefs = context.getSharedPreferences(PREF_NAME, 0);
                }

/* 22 */        public static CrossAnalytics getInstance(Context context) {
/* 23 */            if (sINSTANCE == null) {
/* 24 */                sINSTANCE = new CrossAnalytics(context);
                    }
/* 26 */            return sINSTANCE;
                }

/* 29 */        public void set(String key, String value) {
/* 30 */            this.mPrefs.edit().putString(key, value).apply();
                }

/* 33 */        public void clear(String key) {
/* 34 */            this.mPrefs.edit().remove(key).apply();
                }

/* 37 */        public String get(String key) {
/* 38 */            return this.mPrefs.getString(key, null);
                }

/* 41 */        public boolean isSet(String key) {
                    return get(key) != null;
                }
            }
