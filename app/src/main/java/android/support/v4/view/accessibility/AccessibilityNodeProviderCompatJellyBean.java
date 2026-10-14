            package android.support.v4.view.accessibility;

            import android.os.Bundle;
            import android.view.accessibility.AccessibilityNodeInfo;
            import android.view.accessibility.AccessibilityNodeProvider;
            import java.util.List;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 26 */    class AccessibilityNodeProviderCompatJellyBean {

                interface AccessibilityNodeInfoBridge {
                    Object createAccessibilityNodeInfo(int i);

                    List<Object> findAccessibilityNodeInfosByText(String str, int i);

                    boolean performAction(int i, int i2, Bundle bundle);
                }

/* 27 */        AccessibilityNodeProviderCompatJellyBean() {
                }

                /* JADX INFO: renamed from: android.support.v4.view.accessibility.AccessibilityNodeProviderCompatJellyBean$1 */
/* 36 */        static class C00591 extends AccessibilityNodeProvider {
                    final /* synthetic */ AccessibilityNodeInfoBridge val$bridge;

/* 37 */            C00591(AccessibilityNodeInfoBridge accessibilityNodeInfoBridge) {
/* 38 */                this.val$bridge = accessibilityNodeInfoBridge;
                    }

                    @Override // android.view.accessibility.AccessibilityNodeProvider
/* 40 */            public AccessibilityNodeInfo createAccessibilityNodeInfo(int virtualViewId) {
/* 41 */                return (AccessibilityNodeInfo) this.val$bridge.createAccessibilityNodeInfo(virtualViewId);
                    }

                    @Override // android.view.accessibility.AccessibilityNodeProvider
/* 48 */            public List<AccessibilityNodeInfo> findAccessibilityNodeInfosByText(String text, int virtualViewId) {
/* 49 */                return this.val$bridge.findAccessibilityNodeInfosByText(text, virtualViewId);
                    }

                    @Override // android.view.accessibility.AccessibilityNodeProvider
/* 54 */            public boolean performAction(int virtualViewId, int action, Bundle arguments) {
/* 55 */                return this.val$bridge.performAction(virtualViewId, action, arguments);
                    }
                }

/* 37 */        public static Object newAccessibilityNodeProviderBridge(AccessibilityNodeInfoBridge bridge) {
/* 38 */            return new C00591(bridge);
                }
            }
