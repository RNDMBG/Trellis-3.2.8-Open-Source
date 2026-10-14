            package android.support.v4.app;

            import android.app.Activity;
            import android.content.Intent;
            import android.os.Build;
            import android.os.Bundle;
            import android.support.v4.content.ContextCompat;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 61 */    public class ActivityCompat extends ContextCompat {
/* 62 */        public static boolean invalidateOptionsMenu(Activity activity) {
/* 63 */            if (Build.VERSION.SDK_INT < 11) {
                        return false;
                    }
/* 64 */            ActivityCompatHoneycomb.invalidateOptionsMenu(activity);
/* 65 */            return true;
                }

/* 87 */        public static void startActivity(Activity activity, Intent intent, Bundle options) {
/* 88 */            if (Build.VERSION.SDK_INT >= 16) {
/* 89 */                ActivityCompatJB.startActivity(activity, intent, options);
                    } else {
/* 91 */                activity.startActivity(intent);
                    }
                }

/* 115 */       public static void startActivityForResult(Activity activity, Intent intent, int requestCode, Bundle options) {
/* 116 */           if (Build.VERSION.SDK_INT >= 16) {
/* 117 */               ActivityCompatJB.startActivityForResult(activity, intent, requestCode, options);
                    } else {
/* 119 */               activity.startActivityForResult(intent, requestCode);
                    }
                }

/* 130 */       public static void finishAffinity(Activity activity) {
/* 131 */           if (Build.VERSION.SDK_INT >= 16) {
/* 132 */               ActivityCompatJB.finishAffinity(activity);
                    } else {
/* 134 */               activity.finish();
                    }
                }
            }
