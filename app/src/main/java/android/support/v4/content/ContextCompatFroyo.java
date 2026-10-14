            package android.support.v4.content;

            import android.content.Context;
            import java.io.File;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 21 */    class ContextCompatFroyo {
/* 22 */        ContextCompatFroyo() {
                }

/* 24 */        public static File getExternalCacheDir(Context context) {
/* 25 */            return context.getExternalCacheDir();
                }

/* 28 */        public static File getExternalFilesDir(Context context, String type) {
/* 29 */            return context.getExternalFilesDir(type);
                }
            }
