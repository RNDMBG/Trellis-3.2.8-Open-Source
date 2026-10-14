            package android.support.v4.content;

            import android.content.Context;
            import android.content.Intent;
            import java.io.File;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 25 */    class ContextCompatHoneycomb {
/* 26 */        ContextCompatHoneycomb() {
                }

/* 29 */        static void startActivities(Context context, Intent[] intents) {
/* 30 */            context.startActivities(intents);
                }

/* 33 */        public static File getObbDir(Context context) {
/* 34 */            return context.getObbDir();
                }
            }
