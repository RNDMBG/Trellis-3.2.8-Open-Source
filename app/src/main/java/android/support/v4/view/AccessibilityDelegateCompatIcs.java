            package android.support.v4.view;

            import android.view.View;
            import android.view.ViewGroup;
            import android.view.accessibility.AccessibilityEvent;
            import android.view.accessibility.AccessibilityNodeInfo;
            
            
    class AccessibilityDelegateCompatIcs {

                public interface AccessibilityDelegateBridge {
                    boolean dispatchPopulateAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent);

                    void onInitializeAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent);

                    void onInitializeAccessibilityNodeInfo(View view, Object obj);

                    void onPopulateAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent);

                    boolean onRequestSendAccessibilityEvent(ViewGroup viewGroup, View view, AccessibilityEvent accessibilityEvent);

                    void sendAccessibilityEvent(View view, int i);

                    void sendAccessibilityEventUnchecked(View view, AccessibilityEvent accessibilityEvent);
                }

        AccessibilityDelegateCompatIcs() {
                }

        public static Object newAccessibilityDelegateDefaultImpl() {
            return new View.AccessibilityDelegate();
                }

                
        static class C00411 extends View.AccessibilityDelegate {
                    final  AccessibilityDelegateBridge val$bridge;

            C00411(AccessibilityDelegateBridge accessibilityDelegateBridge) {
                this.val$bridge = accessibilityDelegateBridge;
                    }

                    @Override 
            public boolean dispatchPopulateAccessibilityEvent(View host, AccessibilityEvent event) {
                return this.val$bridge.dispatchPopulateAccessibilityEvent(host, event);
                    }

                    @Override 
            public void onInitializeAccessibilityEvent(View host, AccessibilityEvent event) {
                this.val$bridge.onInitializeAccessibilityEvent(host, event);
                    }

                    @Override 
            public void onInitializeAccessibilityNodeInfo(View host, AccessibilityNodeInfo info) {
                this.val$bridge.onInitializeAccessibilityNodeInfo(host, info);
                    }

                    @Override 
            public void onPopulateAccessibilityEvent(View host, AccessibilityEvent event) {
                this.val$bridge.onPopulateAccessibilityEvent(host, event);
                    }

                    @Override 
            public boolean onRequestSendAccessibilityEvent(ViewGroup host, View child, AccessibilityEvent event) {
                return this.val$bridge.onRequestSendAccessibilityEvent(host, child, event);
                    }

                    @Override 
            public void sendAccessibilityEvent(View host, int eventType) {
                this.val$bridge.sendAccessibilityEvent(host, eventType);
                    }

                    @Override 
            public void sendAccessibilityEventUnchecked(View host, AccessibilityEvent event) {
                this.val$bridge.sendAccessibilityEventUnchecked(host, event);
                    }
                }

        public static Object newAccessibilityDelegateBridge(AccessibilityDelegateBridge bridge) {
            return new C00411(bridge);
                }

        public static boolean dispatchPopulateAccessibilityEvent(Object delegate, View host, AccessibilityEvent event) {
            return ((View.AccessibilityDelegate) delegate).dispatchPopulateAccessibilityEvent(host, event);
                }

        public static void onInitializeAccessibilityEvent(Object delegate, View host, AccessibilityEvent event) {
            ((View.AccessibilityDelegate) delegate).onInitializeAccessibilityEvent(host, event);
                }

        public static void onInitializeAccessibilityNodeInfo(Object delegate, View host, Object info) {
            ((View.AccessibilityDelegate) delegate).onInitializeAccessibilityNodeInfo(host, (AccessibilityNodeInfo) info);
                }

       public static void onPopulateAccessibilityEvent(Object delegate, View host, AccessibilityEvent event) {
           ((View.AccessibilityDelegate) delegate).onPopulateAccessibilityEvent(host, event);
                }

       public static boolean onRequestSendAccessibilityEvent(Object delegate, ViewGroup host, View child, AccessibilityEvent event) {
           return ((View.AccessibilityDelegate) delegate).onRequestSendAccessibilityEvent(host, child, event);
                }

       public static void sendAccessibilityEvent(Object delegate, View host, int eventType) {
           ((View.AccessibilityDelegate) delegate).sendAccessibilityEvent(host, eventType);
                }

       public static void sendAccessibilityEventUnchecked(Object delegate, View host, AccessibilityEvent event) {
           ((View.AccessibilityDelegate) delegate).sendAccessibilityEventUnchecked(host, event);
                }
            }
