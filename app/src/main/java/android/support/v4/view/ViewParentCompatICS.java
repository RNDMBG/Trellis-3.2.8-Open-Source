            package android.support.v4.view;

            import android.view.View;
            import android.view.ViewParent;
            import android.view.accessibility.AccessibilityEvent;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 27 */    public class ViewParentCompatICS {
/* 28 */        public static boolean requestSendAccessibilityEvent(ViewParent parent, View child, AccessibilityEvent event) {
/* 29 */            return parent.requestSendAccessibilityEvent(child, event);
                }
            }
