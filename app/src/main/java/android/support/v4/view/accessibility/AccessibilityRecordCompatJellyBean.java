            package android.support.v4.view.accessibility;

            import android.view.View;
            import android.view.accessibility.AccessibilityRecord;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 23 */    class AccessibilityRecordCompatJellyBean {
/* 24 */        AccessibilityRecordCompatJellyBean() {
                }

/* 27 */        public static void setSource(Object record, View root, int virtualDescendantId) {
/* 28 */            ((AccessibilityRecord) record).setSource(root, virtualDescendantId);
                }
            }
