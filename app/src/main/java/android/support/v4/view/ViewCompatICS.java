            package android.support.v4.view;

            import android.view.View;
            import android.view.accessibility.AccessibilityEvent;
            import android.view.accessibility.AccessibilityNodeInfo;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 25 */    class ViewCompatICS {
/* 26 */        ViewCompatICS() {
                }

/* 29 */        public static boolean canScrollHorizontally(View v, int direction) {
/* 30 */            return v.canScrollHorizontally(direction);
                }

/* 33 */        public static boolean canScrollVertically(View v, int direction) {
/* 34 */            return v.canScrollVertically(direction);
                }

/* 37 */        public static void setAccessibilityDelegate(View v, Object delegate) {
/* 38 */            v.setAccessibilityDelegate((View.AccessibilityDelegate) delegate);
                }

/* 41 */        public static void onPopulateAccessibilityEvent(View v, AccessibilityEvent event) {
/* 42 */            v.onPopulateAccessibilityEvent(event);
                }

/* 45 */        public static void onInitializeAccessibilityEvent(View v, AccessibilityEvent event) {
/* 46 */            v.onInitializeAccessibilityEvent(event);
                }

/* 49 */        public static void onInitializeAccessibilityNodeInfo(View v, Object info) {
/* 50 */            v.onInitializeAccessibilityNodeInfo((AccessibilityNodeInfo) info);
                }
            }
