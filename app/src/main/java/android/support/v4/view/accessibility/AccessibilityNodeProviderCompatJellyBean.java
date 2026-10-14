            package android.support.v4.view.accessibility;

            import android.os.Bundle;
            import android.view.accessibility.AccessibilityNodeInfo;
            import android.view.accessibility.AccessibilityNodeProvider;
            import java.util.List;
            
            
    class AccessibilityNodeProviderCompatJellyBean {

                interface AccessibilityNodeInfoBridge {
                    Object createAccessibilityNodeInfo(int i);

                    List<Object> findAccessibilityNodeInfosByText(String str, int i);

                    boolean performAction(int i, int i2, Bundle bundle);
                }

        AccessibilityNodeProviderCompatJellyBean() {
                }

                
        static class C00591 extends AccessibilityNodeProvider {
                    final  AccessibilityNodeInfoBridge val$bridge;

            C00591(AccessibilityNodeInfoBridge accessibilityNodeInfoBridge) {
                this.val$bridge = accessibilityNodeInfoBridge;
                    }

                    @Override 
            public AccessibilityNodeInfo createAccessibilityNodeInfo(int virtualViewId) {
                return (AccessibilityNodeInfo) this.val$bridge.createAccessibilityNodeInfo(virtualViewId);
                    }

                    @Override 
            public List<AccessibilityNodeInfo> findAccessibilityNodeInfosByText(String text, int virtualViewId) {
                return this.val$bridge.findAccessibilityNodeInfosByText(text, virtualViewId);
                    }

                    @Override 
            public boolean performAction(int virtualViewId, int action, Bundle arguments) {
                return this.val$bridge.performAction(virtualViewId, action, arguments);
                    }
                }

        public static Object newAccessibilityNodeProviderBridge(AccessibilityNodeInfoBridge bridge) {
            return new C00591(bridge);
                }
            }
