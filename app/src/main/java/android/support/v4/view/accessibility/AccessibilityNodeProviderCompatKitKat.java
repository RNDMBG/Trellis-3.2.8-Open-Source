            package android.support.v4.view.accessibility;

            import android.os.Bundle;
            import android.view.accessibility.AccessibilityNodeInfo;
            import android.view.accessibility.AccessibilityNodeProvider;
            import java.util.List;
            
            
    class AccessibilityNodeProviderCompatKitKat {

                interface AccessibilityNodeInfoBridge {
                    Object createAccessibilityNodeInfo(int i);

                    List<Object> findAccessibilityNodeInfosByText(String str, int i);

                    Object findFocus(int i);

                    boolean performAction(int i, int i2, Bundle bundle);
                }

        AccessibilityNodeProviderCompatKitKat() {
                }

                
        static class C00601 extends AccessibilityNodeProvider {
                    final  AccessibilityNodeInfoBridge val$bridge;

            C00601(AccessibilityNodeInfoBridge accessibilityNodeInfoBridge) {
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

                    @Override 
            public AccessibilityNodeInfo findFocus(int focus) {
                return (AccessibilityNodeInfo) this.val$bridge.findFocus(focus);
                    }
                }

        public static Object newAccessibilityNodeProviderBridge(AccessibilityNodeInfoBridge bridge) {
            return new C00601(bridge);
                }
            }
