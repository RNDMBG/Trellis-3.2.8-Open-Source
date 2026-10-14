            package android.support.v4.view.accessibility;

            import android.view.accessibility.AccessibilityRecord;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 22 */    class AccessibilityRecordCompatIcsMr1 {
/* 23 */        AccessibilityRecordCompatIcsMr1() {
                }

/* 26 */        public static int getMaxScrollX(Object record) {
/* 27 */            return ((AccessibilityRecord) record).getMaxScrollX();
                }

/* 30 */        public static int getMaxScrollY(Object record) {
/* 31 */            return ((AccessibilityRecord) record).getMaxScrollY();
                }

/* 33 */        public static void setMaxScrollX(Object record, int maxScrollX) {
/* 34 */            ((AccessibilityRecord) record).setMaxScrollX(maxScrollX);
                }

/* 37 */        public static void setMaxScrollY(Object record, int maxScrollY) {
/* 38 */            ((AccessibilityRecord) record).setMaxScrollY(maxScrollY);
                }
            }
