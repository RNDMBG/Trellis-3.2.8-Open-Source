            package android.support.v4.view.accessibility;

            import android.os.Bundle;
            import android.view.accessibility.AccessibilityNodeInfo;
            import android.view.accessibility.AccessibilityNodeProvider;
            import java.util.List;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 26 */    class AccessibilityNodeProviderCompatKitKat {

                interface AccessibilityNodeInfoBridge {
                    Object createAccessibilityNodeInfo(int i);

                    List<Object> findAccessibilityNodeInfosByText(String str, int i);

                    Object findFocus(int i);

                    boolean performAction(int i, int i2, Bundle bundle);
                }

/* 27 */        AccessibilityNodeProviderCompatKitKat() {
                }

                /* JADX INFO: renamed from: android.support.v4.view.accessibility.AccessibilityNodeProviderCompatKitKat$1 */
/* 37 */        static class C00601 extends AccessibilityNodeProvider {
                    final /* synthetic */ AccessibilityNodeInfoBridge val$bridge;

/* 38 */            C00601(AccessibilityNodeInfoBridge accessibilityNodeInfoBridge) {
/* 39 */                this.val$bridge = accessibilityNodeInfoBridge;
                    }

                    @Override // android.view.accessibility.AccessibilityNodeProvider
/* 41 */            public AccessibilityNodeInfo createAccessibilityNodeInfo(int virtualViewId) {
/* 42 */                return (AccessibilityNodeInfo) this.val$bridge.createAccessibilityNodeInfo(virtualViewId);
                    }

                    @Override // android.view.accessibility.AccessibilityNodeProvider
/* 49 */            public List<AccessibilityNodeInfo> findAccessibilityNodeInfosByText(String text, int virtualViewId) {
/* 50 */                return this.val$bridge.findAccessibilityNodeInfosByText(text, virtualViewId);
                    }

                    @Override // android.view.accessibility.AccessibilityNodeProvider
/* 55 */            public boolean performAction(int virtualViewId, int action, Bundle arguments) {
/* 56 */                return this.val$bridge.performAction(virtualViewId, action, arguments);
                    }

                    @Override // android.view.accessibility.AccessibilityNodeProvider
/* 60 */            public AccessibilityNodeInfo findFocus(int focus) {
/* 61 */                return (AccessibilityNodeInfo) this.val$bridge.findFocus(focus);
                    }
                }

/* 38 */        public static Object newAccessibilityNodeProviderBridge(AccessibilityNodeInfoBridge bridge) {
/* 39 */            return new C00601(bridge);
                }
            }
