            package android.support.v4.view;

            import android.os.Build;
            import android.view.View;
            import android.view.ViewParent;
            import android.view.accessibility.AccessibilityEvent;
            import android.view.accessibility.AccessibilityManager;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 34 */    public class ViewParentCompat {
                static final ViewParentCompatImpl IMPL;

                interface ViewParentCompatImpl {
                    boolean requestSendAccessibilityEvent(ViewParent viewParent, View view, AccessibilityEvent accessibilityEvent);
                }

/* 35 */        static class ViewParentCompatStubImpl implements ViewParentCompatImpl {
/* 36 */            ViewParentCompatStubImpl() {
                    }

                    @Override // android.support.v4.view.ViewParentCompat.ViewParentCompatImpl
/* 41 */            public boolean requestSendAccessibilityEvent(ViewParent parent, View child, AccessibilityEvent event) {
/* 42 */                if (child == null) {
/* 43 */                    return false;
                        }
/* 45 */                AccessibilityManager manager = (AccessibilityManager) child.getContext().getSystemService("accessibility");
/* 47 */                manager.sendAccessibilityEvent(event);
/* 48 */                return true;
                    }
                }

/* 50 */        static class ViewParentCompatICSImpl extends ViewParentCompatStubImpl {
/* 51 */            ViewParentCompatICSImpl() {
                    }

                    @Override // android.support.v4.view.ViewParentCompat.ViewParentCompatStubImpl, android.support.v4.view.ViewParentCompat.ViewParentCompatImpl
/* 55 */            public boolean requestSendAccessibilityEvent(ViewParent parent, View child, AccessibilityEvent event) {
/* 56 */                return ViewParentCompatICS.requestSendAccessibilityEvent(parent, child, event);
                    }
                }

                static {
/* 62 */            int version = Build.VERSION.SDK_INT;
/* 63 */            if (version >= 14) {
/* 64 */                IMPL = new ViewParentCompatICSImpl();
                    } else {
/* 66 */                IMPL = new ViewParentCompatStubImpl();
                    }
                }

/* 72 */        private ViewParentCompat() {
                }

/* 95 */        public static boolean requestSendAccessibilityEvent(ViewParent parent, View child, AccessibilityEvent event) {
/* 96 */            return IMPL.requestSendAccessibilityEvent(parent, child, event);
                }
            }
