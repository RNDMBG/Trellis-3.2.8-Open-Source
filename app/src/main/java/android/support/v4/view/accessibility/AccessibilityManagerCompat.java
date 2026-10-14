            package android.support.v4.view.accessibility;

            import android.accessibilityservice.AccessibilityServiceInfo;
            import android.os.Build;
            import android.view.accessibility.AccessibilityManager;
            import java.util.Collections;
            import java.util.List;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 29 */    public class AccessibilityManagerCompat {
                private static final AccessibilityManagerVersionImpl IMPL;

                interface AccessibilityManagerVersionImpl {
                    boolean addAccessibilityStateChangeListener(AccessibilityManager accessibilityManager, AccessibilityStateChangeListenerCompat accessibilityStateChangeListenerCompat);

                    List<AccessibilityServiceInfo> getEnabledAccessibilityServiceList(AccessibilityManager accessibilityManager, int i);

                    List<AccessibilityServiceInfo> getInstalledAccessibilityServiceList(AccessibilityManager accessibilityManager);

                    boolean isTouchExplorationEnabled(AccessibilityManager accessibilityManager);

                    Object newAccessiblityStateChangeListener(AccessibilityStateChangeListenerCompat accessibilityStateChangeListenerCompat);

                    boolean removeAccessibilityStateChangeListener(AccessibilityManager accessibilityManager, AccessibilityStateChangeListenerCompat accessibilityStateChangeListenerCompat);
                }

                public static abstract class AccessibilityStateChangeListenerCompat {
                    final Object mListener = AccessibilityManagerCompat.access$000().newAccessiblityStateChangeListener(this);

                    public abstract void onAccessibilityStateChanged(boolean z);
                }

/* 30 */        static /* synthetic */ AccessibilityManagerVersionImpl access$000() {
/* 31 */            return IMPL;
                }

/* 45 */        static class AccessibilityManagerStubImpl implements AccessibilityManagerVersionImpl {
/* 46 */            AccessibilityManagerStubImpl() {
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityManagerCompat.AccessibilityManagerVersionImpl
/* 49 */            public Object newAccessiblityStateChangeListener(AccessibilityStateChangeListenerCompat listener) {
/* 50 */                return null;
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityManagerCompat.AccessibilityManagerVersionImpl
/* 54 */            public boolean addAccessibilityStateChangeListener(AccessibilityManager manager, AccessibilityStateChangeListenerCompat listener) {
/* 55 */                return false;
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityManagerCompat.AccessibilityManagerVersionImpl
/* 59 */            public boolean removeAccessibilityStateChangeListener(AccessibilityManager manager, AccessibilityStateChangeListenerCompat listener) {
/* 60 */                return false;
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityManagerCompat.AccessibilityManagerVersionImpl
/* 64 */            public List<AccessibilityServiceInfo> getEnabledAccessibilityServiceList(AccessibilityManager manager, int feedbackTypeFlags) {
/* 65 */                return Collections.emptyList();
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityManagerCompat.AccessibilityManagerVersionImpl
/* 69 */            public List<AccessibilityServiceInfo> getInstalledAccessibilityServiceList(AccessibilityManager manager) {
/* 70 */                return Collections.emptyList();
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityManagerCompat.AccessibilityManagerVersionImpl
/* 73 */            public boolean isTouchExplorationEnabled(AccessibilityManager manager) {
/* 74 */                return false;
                    }
                }

/* 76 */        static class AccessibilityManagerIcsImpl extends AccessibilityManagerStubImpl {
/* 77 */            AccessibilityManagerIcsImpl() {
                    }

                    /* JADX INFO: renamed from: android.support.v4.view.accessibility.AccessibilityManagerCompat$AccessibilityManagerIcsImpl$1 */
/* 82 */            class C00551 implements AccessibilityManagerCompatIcs.AccessibilityStateChangeListenerBridge {
                        final /* synthetic */ AccessibilityStateChangeListenerCompat val$listener;

/* 83 */                C00551(AccessibilityStateChangeListenerCompat accessibilityStateChangeListenerCompat) {
                            this.val$listener = accessibilityStateChangeListenerCompat;
                        }

                        @Override // android.support.v4.view.accessibility.AccessibilityManagerCompatIcs.AccessibilityStateChangeListenerBridge
/* 85 */                public void onAccessibilityStateChanged(boolean enabled) {
/* 86 */                    this.val$listener.onAccessibilityStateChanged(enabled);
                        }
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityManagerCompat.AccessibilityManagerStubImpl, android.support.v4.view.accessibility.AccessibilityManagerCompat.AccessibilityManagerVersionImpl
/* 82 */            public Object newAccessiblityStateChangeListener(AccessibilityStateChangeListenerCompat listener) {
/* 83 */                return AccessibilityManagerCompatIcs.newAccessibilityStateChangeListener(new C00551(listener));
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityManagerCompat.AccessibilityManagerStubImpl, android.support.v4.view.accessibility.AccessibilityManagerCompat.AccessibilityManagerVersionImpl
/* 93 */            public boolean addAccessibilityStateChangeListener(AccessibilityManager manager, AccessibilityStateChangeListenerCompat listener) {
/* 94 */                return AccessibilityManagerCompatIcs.addAccessibilityStateChangeListener(manager, listener.mListener);
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityManagerCompat.AccessibilityManagerStubImpl, android.support.v4.view.accessibility.AccessibilityManagerCompat.AccessibilityManagerVersionImpl
/* 100 */           public boolean removeAccessibilityStateChangeListener(AccessibilityManager manager, AccessibilityStateChangeListenerCompat listener) {
/* 101 */               return AccessibilityManagerCompatIcs.removeAccessibilityStateChangeListener(manager, listener.mListener);
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityManagerCompat.AccessibilityManagerStubImpl, android.support.v4.view.accessibility.AccessibilityManagerCompat.AccessibilityManagerVersionImpl
/* 107 */           public List<AccessibilityServiceInfo> getEnabledAccessibilityServiceList(AccessibilityManager manager, int feedbackTypeFlags) {
/* 108 */               return AccessibilityManagerCompatIcs.getEnabledAccessibilityServiceList(manager, feedbackTypeFlags);
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityManagerCompat.AccessibilityManagerStubImpl, android.support.v4.view.accessibility.AccessibilityManagerCompat.AccessibilityManagerVersionImpl
/* 114 */           public List<AccessibilityServiceInfo> getInstalledAccessibilityServiceList(AccessibilityManager manager) {
/* 115 */               return AccessibilityManagerCompatIcs.getInstalledAccessibilityServiceList(manager);
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityManagerCompat.AccessibilityManagerStubImpl, android.support.v4.view.accessibility.AccessibilityManagerCompat.AccessibilityManagerVersionImpl
/* 119 */           public boolean isTouchExplorationEnabled(AccessibilityManager manager) {
/* 120 */               return AccessibilityManagerCompatIcs.isTouchExplorationEnabled(manager);
                    }
                }

                static {
/* 125 */           if (Build.VERSION.SDK_INT >= 14) {
/* 126 */               IMPL = new AccessibilityManagerIcsImpl();
                    } else {
/* 128 */               IMPL = new AccessibilityManagerStubImpl();
                    }
                }

/* 143 */       public static boolean addAccessibilityStateChangeListener(AccessibilityManager manager, AccessibilityStateChangeListenerCompat listener) {
/* 144 */           return IMPL.addAccessibilityStateChangeListener(manager, listener);
                }

/* 155 */       public static boolean removeAccessibilityStateChangeListener(AccessibilityManager manager, AccessibilityStateChangeListenerCompat listener) {
/* 156 */           return IMPL.removeAccessibilityStateChangeListener(manager, listener);
                }

/* 166 */       public static List<AccessibilityServiceInfo> getInstalledAccessibilityServiceList(AccessibilityManager manager) {
/* 167 */           return IMPL.getInstalledAccessibilityServiceList(manager);
                }

/* 185 */       public static List<AccessibilityServiceInfo> getEnabledAccessibilityServiceList(AccessibilityManager manager, int feedbackTypeFlags) {
/* 186 */           return IMPL.getEnabledAccessibilityServiceList(manager, feedbackTypeFlags);
                }

/* 195 */       public static boolean isTouchExplorationEnabled(AccessibilityManager manager) {
/* 196 */           return IMPL.isTouchExplorationEnabled(manager);
                }
            }
