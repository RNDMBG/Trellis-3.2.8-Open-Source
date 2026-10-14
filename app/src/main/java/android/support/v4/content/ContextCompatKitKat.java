            package android.support.v4.content;

            import android.content.Context;
            import java.io.File;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 21 */    class ContextCompatKitKat {
/* 22 */        ContextCompatKitKat() {
                }

/* 24 */        public static File[] getExternalCacheDirs(Context context) {
/* 25 */            return context.getExternalCacheDirs();
                }

/* 28 */        public static File[] getExternalFilesDirs(Context context, String type) {
/* 29 */            return context.getExternalFilesDirs(type);
                }

/* 32 */        public static File[] getObbDirs(Context context) {
/* 33 */            return context.getObbDirs();
                }
            }
