            package android.support.v4.app;

            import android.app.Activity;
            import android.content.Intent;
            import android.content.pm.ActivityInfo;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 21 */    class NavUtilsJB {
/* 22 */        NavUtilsJB() {
                }

/* 24 */        public static Intent getParentActivityIntent(Activity activity) {
/* 25 */            return activity.getParentActivityIntent();
                }

/* 28 */        public static boolean shouldUpRecreateTask(Activity activity, Intent targetIntent) {
/* 29 */            return activity.shouldUpRecreateTask(targetIntent);
                }

/* 32 */        public static void navigateUpTo(Activity activity, Intent upIntent) {
/* 33 */            activity.navigateUpTo(upIntent);
                }

/* 36 */        public static String getParentActivityName(ActivityInfo info) {
/* 37 */            return info.parentActivityName;
                }
            }
