            package android.support.v4.accessibilityservice;

            import android.accessibilityservice.AccessibilityServiceInfo;
            import android.content.pm.ResolveInfo;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 24 */    class AccessibilityServiceInfoCompatIcs {
/* 25 */        AccessibilityServiceInfoCompatIcs() {
                }

/* 28 */        public static boolean getCanRetrieveWindowContent(AccessibilityServiceInfo info) {
/* 29 */            return info.getCanRetrieveWindowContent();
                }

/* 32 */        public static String getDescription(AccessibilityServiceInfo info) {
/* 33 */            return info.getDescription();
                }

/* 36 */        public static String getId(AccessibilityServiceInfo info) {
/* 37 */            return info.getId();
                }

/* 40 */        public static ResolveInfo getResolveInfo(AccessibilityServiceInfo info) {
/* 41 */            return info.getResolveInfo();
                }

/* 44 */        public static String getSettingsActivityName(AccessibilityServiceInfo info) {
/* 45 */            return info.getSettingsActivityName();
                }
            }
