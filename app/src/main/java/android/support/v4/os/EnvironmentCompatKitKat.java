            package android.support.v4.os;

            import android.os.Environment;
            import java.io.File;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 21 */    class EnvironmentCompatKitKat {
/* 22 */        EnvironmentCompatKitKat() {
                }

/* 24 */        public static String getStorageState(File path) {
/* 25 */            return Environment.getStorageState(path);
                }
            }
