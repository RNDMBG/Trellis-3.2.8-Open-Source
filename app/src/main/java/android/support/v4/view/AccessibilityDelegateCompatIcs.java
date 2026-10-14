            package android.support.v4.view;

            import android.view.View;
            import android.view.ViewGroup;
            import android.view.accessibility.AccessibilityEvent;
            import android.view.accessibility.AccessibilityNodeInfo;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 26 */    class AccessibilityDelegateCompatIcs {

                public interface AccessibilityDelegateBridge {
                    boolean dispatchPopulateAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent);

                    void onInitializeAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent);

                    void onInitializeAccessibilityNodeInfo(View view, Object obj);

                    void onPopulateAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent);

                    boolean onRequestSendAccessibilityEvent(ViewGroup viewGroup, View view, AccessibilityEvent accessibilityEvent);

                    void sendAccessibilityEvent(View view, int i);

                    void sendAccessibilityEventUnchecked(View view, AccessibilityEvent accessibilityEvent);
                }

/* 27 */        AccessibilityDelegateCompatIcs() {
                }

/* 41 */        public static Object newAccessibilityDelegateDefaultImpl() {
/* 42 */            return new View.AccessibilityDelegate();
                }

                /* JADX INFO: renamed from: android.support.v4.view.AccessibilityDelegateCompatIcs$1 */
/* 44 */        static class C00411 extends View.AccessibilityDelegate {
                    final /* synthetic */ AccessibilityDelegateBridge val$bridge;

/* 45 */            C00411(AccessibilityDelegateBridge accessibilityDelegateBridge) {
/* 46 */                this.val$bridge = accessibilityDelegateBridge;
                    }

                    @Override // android.view.View.AccessibilityDelegate
/* 48 */            public boolean dispatchPopulateAccessibilityEvent(View host, AccessibilityEvent event) {
/* 49 */                return this.val$bridge.dispatchPopulateAccessibilityEvent(host, event);
                    }

                    @Override // android.view.View.AccessibilityDelegate
/* 53 */            public void onInitializeAccessibilityEvent(View host, AccessibilityEvent event) {
/* 54 */                this.val$bridge.onInitializeAccessibilityEvent(host, event);
                    }

                    @Override // android.view.View.AccessibilityDelegate
/* 58 */            public void onInitializeAccessibilityNodeInfo(View host, AccessibilityNodeInfo info) {
/* 59 */                this.val$bridge.onInitializeAccessibilityNodeInfo(host, info);
                    }

                    @Override // android.view.View.AccessibilityDelegate
/* 63 */            public void onPopulateAccessibilityEvent(View host, AccessibilityEvent event) {
/* 64 */                this.val$bridge.onPopulateAccessibilityEvent(host, event);
                    }

                    @Override // android.view.View.AccessibilityDelegate
/* 69 */            public boolean onRequestSendAccessibilityEvent(ViewGroup host, View child, AccessibilityEvent event) {
/* 70 */                return this.val$bridge.onRequestSendAccessibilityEvent(host, child, event);
                    }

                    @Override // android.view.View.AccessibilityDelegate
/* 74 */            public void sendAccessibilityEvent(View host, int eventType) {
/* 75 */                this.val$bridge.sendAccessibilityEvent(host, eventType);
                    }

                    @Override // android.view.View.AccessibilityDelegate
/* 79 */            public void sendAccessibilityEventUnchecked(View host, AccessibilityEvent event) {
/* 80 */                this.val$bridge.sendAccessibilityEventUnchecked(host, event);
                    }
                }

/* 45 */        public static Object newAccessibilityDelegateBridge(AccessibilityDelegateBridge bridge) {
/* 46 */            return new C00411(bridge);
                }

/* 86 */        public static boolean dispatchPopulateAccessibilityEvent(Object delegate, View host, AccessibilityEvent event) {
/* 87 */            return ((View.AccessibilityDelegate) delegate).dispatchPopulateAccessibilityEvent(host, event);
                }

/* 91 */        public static void onInitializeAccessibilityEvent(Object delegate, View host, AccessibilityEvent event) {
/* 92 */            ((View.AccessibilityDelegate) delegate).onInitializeAccessibilityEvent(host, event);
                }

/* 95 */        public static void onInitializeAccessibilityNodeInfo(Object delegate, View host, Object info) {
/* 96 */            ((View.AccessibilityDelegate) delegate).onInitializeAccessibilityNodeInfo(host, (AccessibilityNodeInfo) info);
                }

/* 101 */       public static void onPopulateAccessibilityEvent(Object delegate, View host, AccessibilityEvent event) {
/* 102 */           ((View.AccessibilityDelegate) delegate).onPopulateAccessibilityEvent(host, event);
                }

/* 106 */       public static boolean onRequestSendAccessibilityEvent(Object delegate, ViewGroup host, View child, AccessibilityEvent event) {
/* 107 */           return ((View.AccessibilityDelegate) delegate).onRequestSendAccessibilityEvent(host, child, event);
                }

/* 111 */       public static void sendAccessibilityEvent(Object delegate, View host, int eventType) {
/* 112 */           ((View.AccessibilityDelegate) delegate).sendAccessibilityEvent(host, eventType);
                }

/* 116 */       public static void sendAccessibilityEventUnchecked(Object delegate, View host, AccessibilityEvent event) {
/* 117 */           ((View.AccessibilityDelegate) delegate).sendAccessibilityEventUnchecked(host, event);
                }
            }
