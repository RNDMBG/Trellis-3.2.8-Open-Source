            package android.support.v4.view.accessibility;

            import android.view.accessibility.AccessibilityEvent;
            import android.view.accessibility.AccessibilityRecord;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 23 */    class AccessibilityEventCompatIcs {
/* 24 */        AccessibilityEventCompatIcs() {
                }

/* 27 */        public static int getRecordCount(AccessibilityEvent event) {
/* 28 */            return event.getRecordCount();
                }

/* 31 */        public static void appendRecord(AccessibilityEvent event, Object record) {
/* 32 */            event.appendRecord((AccessibilityRecord) record);
                }

/* 35 */        public static Object getRecord(AccessibilityEvent event, int index) {
/* 36 */            return event.getRecord(index);
                }
            }
