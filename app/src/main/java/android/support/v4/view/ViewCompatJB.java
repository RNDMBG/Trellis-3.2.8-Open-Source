            package android.support.v4.view;

            import android.os.Bundle;
            import android.view.View;
            import android.view.ViewParent;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 24 */    class ViewCompatJB {
/* 25 */        ViewCompatJB() {
                }

/* 28 */        public static boolean hasTransientState(View view) {
/* 29 */            return view.hasTransientState();
                }

/* 32 */        public static void setHasTransientState(View view, boolean hasTransientState) {
/* 33 */            view.setHasTransientState(hasTransientState);
                }

/* 36 */        public static void postInvalidateOnAnimation(View view) {
/* 37 */            view.postInvalidateOnAnimation();
                }

/* 41 */        public static void postInvalidateOnAnimation(View view, int left, int top, int right, int bottom) {
/* 42 */            view.postInvalidate(left, top, right, bottom);
                }

/* 45 */        public static void postOnAnimation(View view, Runnable action) {
/* 46 */            view.postOnAnimation(action);
                }

/* 49 */        public static void postOnAnimationDelayed(View view, Runnable action, long delayMillis) {
/* 50 */            view.postOnAnimationDelayed(action, delayMillis);
                }

/* 53 */        public static int getImportantForAccessibility(View view) {
/* 54 */            return view.getImportantForAccessibility();
                }

/* 57 */        public static void setImportantForAccessibility(View view, int mode) {
/* 58 */            view.setImportantForAccessibility(mode);
                }

/* 61 */        public static boolean performAccessibilityAction(View view, int action, Bundle arguments) {
/* 62 */            return view.performAccessibilityAction(action, arguments);
                }

/* 65 */        public static Object getAccessibilityNodeProvider(View view) {
/* 66 */            return view.getAccessibilityNodeProvider();
                }

/* 69 */        public static ViewParent getParentForAccessibility(View view) {
/* 70 */            return view.getParentForAccessibility();
                }
            }
