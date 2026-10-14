            package android.support.v4.view.accessibility;

            import android.os.Build;
            import android.os.Bundle;
            import java.util.ArrayList;
            import java.util.List;
            
            
    public class AccessibilityNodeProviderCompat {
                private static final AccessibilityNodeProviderImpl IMPL;
                private final Object mProvider;

                interface AccessibilityNodeProviderImpl {
                    Object newAccessibilityNodeProviderBridge(AccessibilityNodeProviderCompat accessibilityNodeProviderCompat);
                }

        static class AccessibilityNodeProviderStubImpl implements AccessibilityNodeProviderImpl {
            AccessibilityNodeProviderStubImpl() {
                    }

                    @Override 
            public Object newAccessibilityNodeProviderBridge(AccessibilityNodeProviderCompat compat) {
                return null;
                    }
                }

        static class AccessibilityNodeProviderJellyBeanImpl extends AccessibilityNodeProviderStubImpl {
            AccessibilityNodeProviderJellyBeanImpl() {
                    }

                    
            class C00571 implements AccessibilityNodeProviderCompatJellyBean.AccessibilityNodeInfoBridge {
                        final  AccessibilityNodeProviderCompat val$compat;

                C00571(AccessibilityNodeProviderCompat accessibilityNodeProviderCompat) {
                            this.val$compat = accessibilityNodeProviderCompat;
                        }

                        @Override 
                public boolean performAction(int virtualViewId, int action, Bundle arguments) {
                    return this.val$compat.performAction(virtualViewId, action, arguments);
                        }

                        @Override 
                public List<Object> findAccessibilityNodeInfosByText(String text, int virtualViewId) {
                    List<AccessibilityNodeInfoCompat> compatInfos = this.val$compat.findAccessibilityNodeInfosByText(text, virtualViewId);
                    List<Object> infos = new ArrayList<>();
                    int infoCount = compatInfos.size();
                            for (int i = 0; i < infoCount; i++) {
                        AccessibilityNodeInfoCompat infoCompat = compatInfos.get(i);
                        infos.add(infoCompat.getInfo());
                            }
                    return infos;
                        }

                        @Override 
                public Object createAccessibilityNodeInfo(int virtualViewId) {
                    AccessibilityNodeInfoCompat compatInfo = this.val$compat.createAccessibilityNodeInfo(virtualViewId);
                    if (compatInfo == null) {
                        return null;
                            }
                            return compatInfo.getInfo();
                        }
                    }

                    @Override 
            public Object newAccessibilityNodeProviderBridge(AccessibilityNodeProviderCompat compat) {
                return AccessibilityNodeProviderCompatJellyBean.newAccessibilityNodeProviderBridge(new C00571(compat));
                    }
                }

        static class AccessibilityNodeProviderKitKatImpl extends AccessibilityNodeProviderStubImpl {
            AccessibilityNodeProviderKitKatImpl() {
                    }

                    
            class C00581 implements AccessibilityNodeProviderCompatKitKat.AccessibilityNodeInfoBridge {
                        final  AccessibilityNodeProviderCompat val$compat;

                C00581(AccessibilityNodeProviderCompat accessibilityNodeProviderCompat) {
                            this.val$compat = accessibilityNodeProviderCompat;
                        }

                        @Override 
                public boolean performAction(int virtualViewId, int action, Bundle arguments) {
                    return this.val$compat.performAction(virtualViewId, action, arguments);
                        }

                        @Override 
                public List<Object> findAccessibilityNodeInfosByText(String text, int virtualViewId) {
                    List<AccessibilityNodeInfoCompat> compatInfos = this.val$compat.findAccessibilityNodeInfosByText(text, virtualViewId);
                   List<Object> infos = new ArrayList<>();
                   int infoCount = compatInfos.size();
                            for (int i = 0; i < infoCount; i++) {
                       AccessibilityNodeInfoCompat infoCompat = compatInfos.get(i);
                       infos.add(infoCompat.getInfo());
                            }
                   return infos;
                        }

                        @Override 
               public Object createAccessibilityNodeInfo(int virtualViewId) {
                   AccessibilityNodeInfoCompat compatInfo = this.val$compat.createAccessibilityNodeInfo(virtualViewId);
                   if (compatInfo == null) {
                       return null;
                            }
                            return compatInfo.getInfo();
                        }

                        @Override 
               public Object findFocus(int focus) {
                   AccessibilityNodeInfoCompat compatInfo = this.val$compat.findFocus(focus);
                   if (compatInfo == null) {
                       return null;
                            }
                            return compatInfo.getInfo();
                        }
                    }

                    @Override 
            public Object newAccessibilityNodeProviderBridge(AccessibilityNodeProviderCompat compat) {
                return AccessibilityNodeProviderCompatKitKat.newAccessibilityNodeProviderBridge(new C00581(compat));
                    }
                }

                static {
           if (Build.VERSION.SDK_INT >= 19) {
               IMPL = new AccessibilityNodeProviderKitKatImpl();
                    } else if (Build.VERSION.SDK_INT >= 16) {
               IMPL = new AccessibilityNodeProviderJellyBeanImpl();
                    } else {
               IMPL = new AccessibilityNodeProviderStubImpl();
                    }
                }

       public AccessibilityNodeProviderCompat() {
           this.mProvider = IMPL.newAccessibilityNodeProviderBridge(this);
                }

       public AccessibilityNodeProviderCompat(Object provider) {
           this.mProvider = provider;
                }

       public Object getProvider() {
           return this.mProvider;
                }

       public AccessibilityNodeInfoCompat createAccessibilityNodeInfo(int virtualViewId) {
           return null;
                }

       public boolean performAction(int virtualViewId, int action, Bundle arguments) {
           return false;
                }

       public List<AccessibilityNodeInfoCompat> findAccessibilityNodeInfosByText(String text, int virtualViewId) {
           return null;
                }

       public AccessibilityNodeInfoCompat findFocus(int focus) {
           return null;
                }
            }
