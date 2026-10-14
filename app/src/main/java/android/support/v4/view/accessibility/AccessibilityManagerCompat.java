            package android.support.v4.view.accessibility;

            import android.accessibilityservice.AccessibilityServiceInfo;
            import android.os.Build;
            import android.view.accessibility.AccessibilityManager;
            import java.util.Collections;
            import java.util.List;
            
            
    public class AccessibilityManagerCompat {
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

        static  AccessibilityManagerVersionImpl access$000() {
            return IMPL;
                }

        static class AccessibilityManagerStubImpl implements AccessibilityManagerVersionImpl {
            AccessibilityManagerStubImpl() {
                    }

                    @Override 
            public Object newAccessiblityStateChangeListener(AccessibilityStateChangeListenerCompat listener) {
                return null;
                    }

                    @Override 
            public boolean addAccessibilityStateChangeListener(AccessibilityManager manager, AccessibilityStateChangeListenerCompat listener) {
                return false;
                    }

                    @Override 
            public boolean removeAccessibilityStateChangeListener(AccessibilityManager manager, AccessibilityStateChangeListenerCompat listener) {
                return false;
                    }

                    @Override 
            public List<AccessibilityServiceInfo> getEnabledAccessibilityServiceList(AccessibilityManager manager, int feedbackTypeFlags) {
                return Collections.emptyList();
                    }

                    @Override 
            public List<AccessibilityServiceInfo> getInstalledAccessibilityServiceList(AccessibilityManager manager) {
                return Collections.emptyList();
                    }

                    @Override 
            public boolean isTouchExplorationEnabled(AccessibilityManager manager) {
                return false;
                    }
                }

        static class AccessibilityManagerIcsImpl extends AccessibilityManagerStubImpl {
            AccessibilityManagerIcsImpl() {
                    }

                    
            class C00551 implements AccessibilityManagerCompatIcs.AccessibilityStateChangeListenerBridge {
                        final  AccessibilityStateChangeListenerCompat val$listener;

                C00551(AccessibilityStateChangeListenerCompat accessibilityStateChangeListenerCompat) {
                            this.val$listener = accessibilityStateChangeListenerCompat;
                        }

                        @Override 
                public void onAccessibilityStateChanged(boolean enabled) {
                    this.val$listener.onAccessibilityStateChanged(enabled);
                        }
                    }

                    @Override 
            public Object newAccessiblityStateChangeListener(AccessibilityStateChangeListenerCompat listener) {
                return AccessibilityManagerCompatIcs.newAccessibilityStateChangeListener(new C00551(listener));
                    }

                    @Override 
            public boolean addAccessibilityStateChangeListener(AccessibilityManager manager, AccessibilityStateChangeListenerCompat listener) {
                return AccessibilityManagerCompatIcs.addAccessibilityStateChangeListener(manager, listener.mListener);
                    }

                    @Override 
           public boolean removeAccessibilityStateChangeListener(AccessibilityManager manager, AccessibilityStateChangeListenerCompat listener) {
               return AccessibilityManagerCompatIcs.removeAccessibilityStateChangeListener(manager, listener.mListener);
                    }

                    @Override 
           public List<AccessibilityServiceInfo> getEnabledAccessibilityServiceList(AccessibilityManager manager, int feedbackTypeFlags) {
               return AccessibilityManagerCompatIcs.getEnabledAccessibilityServiceList(manager, feedbackTypeFlags);
                    }

                    @Override 
           public List<AccessibilityServiceInfo> getInstalledAccessibilityServiceList(AccessibilityManager manager) {
               return AccessibilityManagerCompatIcs.getInstalledAccessibilityServiceList(manager);
                    }

                    @Override 
           public boolean isTouchExplorationEnabled(AccessibilityManager manager) {
               return AccessibilityManagerCompatIcs.isTouchExplorationEnabled(manager);
                    }
                }

                static {
           if (Build.VERSION.SDK_INT >= 14) {
               IMPL = new AccessibilityManagerIcsImpl();
                    } else {
               IMPL = new AccessibilityManagerStubImpl();
                    }
                }

       public static boolean addAccessibilityStateChangeListener(AccessibilityManager manager, AccessibilityStateChangeListenerCompat listener) {
           return IMPL.addAccessibilityStateChangeListener(manager, listener);
                }

       public static boolean removeAccessibilityStateChangeListener(AccessibilityManager manager, AccessibilityStateChangeListenerCompat listener) {
           return IMPL.removeAccessibilityStateChangeListener(manager, listener);
                }

       public static List<AccessibilityServiceInfo> getInstalledAccessibilityServiceList(AccessibilityManager manager) {
           return IMPL.getInstalledAccessibilityServiceList(manager);
                }

       public static List<AccessibilityServiceInfo> getEnabledAccessibilityServiceList(AccessibilityManager manager, int feedbackTypeFlags) {
           return IMPL.getEnabledAccessibilityServiceList(manager, feedbackTypeFlags);
                }

       public static boolean isTouchExplorationEnabled(AccessibilityManager manager) {
           return IMPL.isTouchExplorationEnabled(manager);
                }
            }
