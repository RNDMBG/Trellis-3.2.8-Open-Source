            package android.support.v4.view;

            import android.os.Bundle;
            import android.view.View;
            import android.view.ViewGroup;
            import android.view.accessibility.AccessibilityEvent;
            import android.view.accessibility.AccessibilityNodeInfo;
            import android.view.accessibility.AccessibilityNodeProvider;
            
            
    class AccessibilityDelegateCompatJellyBean {

                public interface AccessibilityDelegateBridgeJellyBean {
                    boolean dispatchPopulateAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent);

                    Object getAccessibilityNodeProvider(View view);

                    void onInitializeAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent);

                    void onInitializeAccessibilityNodeInfo(View view, Object obj);

                    void onPopulateAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent);

                    boolean onRequestSendAccessibilityEvent(ViewGroup viewGroup, View view, AccessibilityEvent accessibilityEvent);

                    boolean performAccessibilityAction(View view, int i, Bundle bundle);

                    void sendAccessibilityEvent(View view, int i);

                    void sendAccessibilityEventUnchecked(View view, AccessibilityEvent accessibilityEvent);
                }

        AccessibilityDelegateCompatJellyBean() {
                }

                
        static class C00421 extends View.AccessibilityDelegate {
                    final  AccessibilityDelegateBridgeJellyBean val$bridge;

            C00421(AccessibilityDelegateBridgeJellyBean accessibilityDelegateBridgeJellyBean) {
                this.val$bridge = accessibilityDelegateBridgeJellyBean;
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

                    @Override 
            public AccessibilityNodeProvider getAccessibilityNodeProvider(View host) {
                return (AccessibilityNodeProvider) this.val$bridge.getAccessibilityNodeProvider(host);
                    }

                    @Override 
            public boolean performAccessibilityAction(View host, int action, Bundle args) {
                return this.val$bridge.performAccessibilityAction(host, action, args);
                    }
                }

        public static Object newAccessibilityDelegateBridge(AccessibilityDelegateBridgeJellyBean bridge) {
            return new C00421(bridge);
                }

        public static Object getAccessibilityNodeProvider(Object delegate, View host) {
            return ((View.AccessibilityDelegate) delegate).getAccessibilityNodeProvider(host);
                }

       public static boolean performAccessibilityAction(Object delegate, View host, int action, Bundle args) {
           return ((View.AccessibilityDelegate) delegate).performAccessibilityAction(host, action, args);
                }
            }
