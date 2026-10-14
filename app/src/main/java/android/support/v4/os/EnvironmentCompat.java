            package android.support.v4.os;

            import android.os.Build;
            import android.os.Environment;
            import android.util.Log;
            import java.io.File;
            import java.io.IOException;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 54 */    public class EnvironmentCompat {
                public static final String MEDIA_UNKNOWN = "unknown";
                private static final String TAG = "EnvironmentCompat";

/* 55 */        public static String getStorageState(File path) {
/* 56 */            int version = Build.VERSION.SDK_INT;
/* 57 */            if (version >= 19) {
/* 58 */                return EnvironmentCompatKitKat.getStorageState(path);
                    }
                    try {
/* 62 */                String canonicalPath = path.getCanonicalPath();
/* 63 */                String canonicalExternal = Environment.getExternalStorageDirectory().getCanonicalPath();
/* 66 */                if (canonicalPath.startsWith(canonicalExternal)) {
/* 67 */                    return Environment.getExternalStorageState();
                        }
                    } catch (IOException e) {
/* 70 */                Log.w(TAG, "Failed to resolve canonical path: " + e);
                    }
/* 73 */            return MEDIA_UNKNOWN;
                }
            }
