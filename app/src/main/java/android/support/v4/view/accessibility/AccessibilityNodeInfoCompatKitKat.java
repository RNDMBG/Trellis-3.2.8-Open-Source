            package android.support.v4.view.accessibility;

            import android.view.accessibility.AccessibilityNodeInfo;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 22 */    class AccessibilityNodeInfoCompatKitKat {
/* 23 */        AccessibilityNodeInfoCompatKitKat() {
                }

/* 25 */        public static int getLiveRegion(Object info) {
/* 26 */            return ((AccessibilityNodeInfo) info).getLiveRegion();
                }

/* 29 */        public static void setLiveRegion(Object info, int mode) {
/* 30 */            ((AccessibilityNodeInfo) info).setLiveRegion(mode);
                }
            }
