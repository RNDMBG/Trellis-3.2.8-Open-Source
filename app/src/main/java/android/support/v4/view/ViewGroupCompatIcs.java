            package android.support.v4.view;

            import android.view.View;
            import android.view.ViewGroup;
            import android.view.accessibility.AccessibilityEvent;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 24 */    class ViewGroupCompatIcs {
/* 25 */        ViewGroupCompatIcs() {
                }

/* 28 */        public static boolean onRequestSendAccessibilityEvent(ViewGroup group, View child, AccessibilityEvent event) {
/* 29 */            return group.onRequestSendAccessibilityEvent(child, event);
                }
            }
