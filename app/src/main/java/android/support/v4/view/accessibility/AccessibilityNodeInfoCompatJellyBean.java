            package android.support.v4.view.accessibility;

            import android.os.Bundle;
            import android.view.View;
            import android.view.accessibility.AccessibilityNodeInfo;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 24 */    class AccessibilityNodeInfoCompatJellyBean {
/* 25 */        AccessibilityNodeInfoCompatJellyBean() {
                }

/* 28 */        public static void addChild(Object info, View child, int virtualDescendantId) {
/* 29 */            ((AccessibilityNodeInfo) info).addChild(child, virtualDescendantId);
                }

/* 32 */        public static void setSource(Object info, View root, int virtualDescendantId) {
/* 33 */            ((AccessibilityNodeInfo) info).setSource(root, virtualDescendantId);
                }

/* 36 */        public static boolean isVisibleToUser(Object info) {
/* 37 */            return ((AccessibilityNodeInfo) info).isVisibleToUser();
                }

/* 40 */        public static void setVisibleToUser(Object info, boolean visibleToUser) {
/* 41 */            ((AccessibilityNodeInfo) info).setVisibleToUser(visibleToUser);
                }

/* 44 */        public static boolean performAction(Object info, int action, Bundle arguments) {
/* 45 */            return ((AccessibilityNodeInfo) info).performAction(action, arguments);
                }

/* 48 */        public static void setMovementGranularities(Object info, int granularities) {
/* 49 */            ((AccessibilityNodeInfo) info).setMovementGranularities(granularities);
                }

/* 52 */        public static int getMovementGranularities(Object info) {
/* 53 */            return ((AccessibilityNodeInfo) info).getMovementGranularities();
                }

/* 56 */        public static Object obtain(View root, int virtualDescendantId) {
/* 57 */            return AccessibilityNodeInfo.obtain(root, virtualDescendantId);
                }

/* 60 */        public static Object findFocus(Object info, int focus) {
/* 61 */            return ((AccessibilityNodeInfo) info).findFocus(focus);
                }

/* 64 */        public static Object focusSearch(Object info, int direction) {
/* 65 */            return ((AccessibilityNodeInfo) info).focusSearch(direction);
                }

/* 68 */        public static void setParent(Object info, View root, int virtualDescendantId) {
/* 69 */            ((AccessibilityNodeInfo) info).setParent(root, virtualDescendantId);
                }

/* 72 */        public static boolean isAccessibilityFocused(Object info) {
/* 73 */            return ((AccessibilityNodeInfo) info).isAccessibilityFocused();
                }

/* 76 */        public static void setAccesibilityFocused(Object info, boolean focused) {
/* 77 */            ((AccessibilityNodeInfo) info).setAccessibilityFocused(focused);
                }
            }
