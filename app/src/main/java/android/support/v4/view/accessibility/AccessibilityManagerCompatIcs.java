            package android.support.v4.view.accessibility;

            import android.accessibilityservice.AccessibilityServiceInfo;
            import android.view.accessibility.AccessibilityManager;
            import java.util.List;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 26 */    class AccessibilityManagerCompatIcs {

                interface AccessibilityStateChangeListenerBridge {
                    void onAccessibilityStateChanged(boolean z);
                }

/* 27 */        AccessibilityManagerCompatIcs() {
                }

                /* JADX INFO: renamed from: android.support.v4.view.accessibility.AccessibilityManagerCompatIcs$1 */
/* 34 */        static class AccessibilityManagerAccessibilityStateChangeListenerC00561 implements AccessibilityManager.AccessibilityStateChangeListener {
                    final /* synthetic */ AccessibilityStateChangeListenerBridge val$bridge;

/* 35 */            AccessibilityManagerAccessibilityStateChangeListenerC00561(AccessibilityStateChangeListenerBridge accessibilityStateChangeListenerBridge) {
/* 36 */                this.val$bridge = accessibilityStateChangeListenerBridge;
                    }

                    @Override // android.view.accessibility.AccessibilityManager.AccessibilityStateChangeListener
/* 38 */            public void onAccessibilityStateChanged(boolean enabled) {
/* 39 */                this.val$bridge.onAccessibilityStateChanged(enabled);
                    }
                }

/* 35 */        public static Object newAccessibilityStateChangeListener(AccessibilityStateChangeListenerBridge bridge) {
/* 36 */            return new AccessibilityManagerAccessibilityStateChangeListenerC00561(bridge);
                }

/* 45 */        public static boolean addAccessibilityStateChangeListener(AccessibilityManager manager, Object listener) {
/* 46 */            return manager.addAccessibilityStateChangeListener((AccessibilityManager.AccessibilityStateChangeListener) listener);
                }

/* 51 */        public static boolean removeAccessibilityStateChangeListener(AccessibilityManager manager, Object listener) {
/* 52 */            return manager.removeAccessibilityStateChangeListener((AccessibilityManager.AccessibilityStateChangeListener) listener);
                }

/* 57 */        public static List<AccessibilityServiceInfo> getEnabledAccessibilityServiceList(AccessibilityManager manager, int feedbackTypeFlags) {
/* 58 */            return manager.getEnabledAccessibilityServiceList(feedbackTypeFlags);
                }

/* 62 */        public static List<AccessibilityServiceInfo> getInstalledAccessibilityServiceList(AccessibilityManager manager) {
/* 63 */            return manager.getInstalledAccessibilityServiceList();
                }

/* 66 */        public static boolean isTouchExplorationEnabled(AccessibilityManager manager) {
/* 67 */            return manager.isTouchExplorationEnabled();
                }
            }
