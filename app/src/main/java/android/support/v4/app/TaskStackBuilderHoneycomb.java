            package android.support.v4.app;

            import android.app.PendingIntent;
            import android.content.Context;
            import android.content.Intent;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 24 */    class TaskStackBuilderHoneycomb {
/* 25 */        TaskStackBuilderHoneycomb() {
                }

/* 28 */        public static PendingIntent getActivitiesPendingIntent(Context context, int requestCode, Intent[] intents, int flags) {
/* 29 */            return PendingIntent.getActivities(context, requestCode, intents, flags);
                }
            }
