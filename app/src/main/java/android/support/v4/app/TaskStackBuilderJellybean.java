            package android.support.v4.app;

            import android.app.PendingIntent;
            import android.content.Context;
            import android.content.Intent;
            import android.os.Bundle;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 22 */    class TaskStackBuilderJellybean {
/* 23 */        TaskStackBuilderJellybean() {
                }

/* 27 */        public static PendingIntent getActivitiesPendingIntent(Context context, int requestCode, Intent[] intents, int flags, Bundle options) {
/* 28 */            return PendingIntent.getActivities(context, requestCode, intents, flags, options);
                }
            }
