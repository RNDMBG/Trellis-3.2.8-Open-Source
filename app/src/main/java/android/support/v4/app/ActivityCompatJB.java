            package android.support.v4.app;

            import android.app.Activity;
            import android.content.Context;
            import android.content.Intent;
            import android.os.Bundle;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 22 */    class ActivityCompatJB {
/* 23 */        ActivityCompatJB() {
                }

/* 25 */        public static void startActivity(Context context, Intent intent, Bundle options) {
/* 26 */            context.startActivity(intent, options);
                }

/* 29 */        public static void startActivityForResult(Activity activity, Intent intent, int requestCode, Bundle options) {
/* 30 */            activity.startActivityForResult(intent, requestCode, options);
                }

/* 33 */        public static void finishAffinity(Activity activity) {
/* 34 */            activity.finishAffinity();
                }
            }
