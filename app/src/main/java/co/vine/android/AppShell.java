            package co.vine.android;

            import android.support.multidex.MultiDex;
            import android.util.Log;
            import com.facebook.buck.android.support.exopackage.ExopackageApplication;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 15 */    public class AppShell extends ExopackageApplication {
                private static final String APP_CLASS = "co.vine.android.VineApplication";
                private static final String TAG = "AppShell";

/* 16 */        public AppShell() {
/* 17 */            super(APP_CLASS, false);
/* 18 */            Log.d(TAG, "Exo: false with co.vine.android.VineApplication");
                }

                @Override // com.facebook.buck.android.support.exopackage.ExopackageApplication
/* 22 */        protected void onBaseContextAttached() {
/* 23 */            super.onBaseContextAttached();
/* 25 */            Log.d(TAG, "Android MultiDex : On");
                    try {
/* 27 */                MultiDex.install(getBaseContext());
                    } catch (RuntimeException multiDexException) {
/* 31 */                if (!isDoingRobolectricTest()) {
/* 32 */                    throw multiDexException;
                        }
                    }
/* 35 */            Log.d(TAG, "Android MultiDex : Installed.");
                }

/* 43 */        public static boolean isDoingRobolectricTest() {
                    try {
/* 44 */                Class<?> robolectric = Class.forName("org.robolectric.Robolectric");
                        return robolectric != null;
                    } catch (ClassNotFoundException e) {
/* 47 */                return false;
                    }
                }
            }
