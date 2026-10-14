            package android.support.v4.view;

            import android.os.Bundle;
            import android.view.View;
            import android.view.ViewGroup;
            import android.view.accessibility.AccessibilityEvent;
            import android.view.accessibility.AccessibilityNodeInfo;
            import android.view.accessibility.AccessibilityNodeProvider;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 28 */    class AccessibilityDelegateCompatJellyBean {

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

/* 29 */        AccessibilityDelegateCompatJellyBean() {
                }

                /* JADX INFO: renamed from: android.support.v4.view.AccessibilityDelegateCompatJellyBean$1 */
/* 45 */        static class C00421 extends View.AccessibilityDelegate {
                    final /* synthetic */ AccessibilityDelegateBridgeJellyBean val$bridge;

/* 46 */            C00421(AccessibilityDelegateBridgeJellyBean accessibilityDelegateBridgeJellyBean) {
/* 47 */                this.val$bridge = accessibilityDelegateBridgeJellyBean;
                    }

                    @Override // android.view.View.AccessibilityDelegate
/* 50 */            public boolean dispatchPopulateAccessibilityEvent(View host, AccessibilityEvent event) {
/* 51 */                return this.val$bridge.dispatchPopulateAccessibilityEvent(host, event);
                    }

                    @Override // android.view.View.AccessibilityDelegate
/* 55 */            public void onInitializeAccessibilityEvent(View host, AccessibilityEvent event) {
/* 56 */                this.val$bridge.onInitializeAccessibilityEvent(host, event);
                    }

                    @Override // android.view.View.AccessibilityDelegate
/* 60 */            public void onInitializeAccessibilityNodeInfo(View host, AccessibilityNodeInfo info) {
/* 61 */                this.val$bridge.onInitializeAccessibilityNodeInfo(host, info);
                    }

                    @Override // android.view.View.AccessibilityDelegate
/* 65 */            public void onPopulateAccessibilityEvent(View host, AccessibilityEvent event) {
/* 66 */                this.val$bridge.onPopulateAccessibilityEvent(host, event);
                    }

                    @Override // android.view.View.AccessibilityDelegate
/* 71 */            public boolean onRequestSendAccessibilityEvent(ViewGroup host, View child, AccessibilityEvent event) {
/* 72 */                return this.val$bridge.onRequestSendAccessibilityEvent(host, child, event);
                    }

                    @Override // android.view.View.AccessibilityDelegate
/* 76 */            public void sendAccessibilityEvent(View host, int eventType) {
/* 77 */                this.val$bridge.sendAccessibilityEvent(host, eventType);
                    }

                    @Override // android.view.View.AccessibilityDelegate
/* 81 */            public void sendAccessibilityEventUnchecked(View host, AccessibilityEvent event) {
/* 82 */                this.val$bridge.sendAccessibilityEventUnchecked(host, event);
                    }

                    @Override // android.view.View.AccessibilityDelegate
/* 86 */            public AccessibilityNodeProvider getAccessibilityNodeProvider(View host) {
/* 87 */                return (AccessibilityNodeProvider) this.val$bridge.getAccessibilityNodeProvider(host);
                    }

                    @Override // android.view.View.AccessibilityDelegate
/* 91 */            public boolean performAccessibilityAction(View host, int action, Bundle args) {
/* 92 */                return this.val$bridge.performAccessibilityAction(host, action, args);
                    }
                }

/* 46 */        public static Object newAccessibilityDelegateBridge(AccessibilityDelegateBridgeJellyBean bridge) {
/* 47 */            return new C00421(bridge);
                }

/* 98 */        public static Object getAccessibilityNodeProvider(Object delegate, View host) {
/* 99 */            return ((View.AccessibilityDelegate) delegate).getAccessibilityNodeProvider(host);
                }

/* 103 */       public static boolean performAccessibilityAction(Object delegate, View host, int action, Bundle args) {
/* 104 */           return ((View.AccessibilityDelegate) delegate).performAccessibilityAction(host, action, args);
                }
            }
