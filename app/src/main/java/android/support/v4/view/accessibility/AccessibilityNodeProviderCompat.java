            package android.support.v4.view.accessibility;

            import android.os.Build;
            import android.os.Bundle;
            import java.util.ArrayList;
            import java.util.List;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 33 */    public class AccessibilityNodeProviderCompat {
                private static final AccessibilityNodeProviderImpl IMPL;
                private final Object mProvider;

                interface AccessibilityNodeProviderImpl {
                    Object newAccessibilityNodeProviderBridge(AccessibilityNodeProviderCompat accessibilityNodeProviderCompat);
                }

/* 34 */        static class AccessibilityNodeProviderStubImpl implements AccessibilityNodeProviderImpl {
/* 35 */            AccessibilityNodeProviderStubImpl() {
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityNodeProviderCompat.AccessibilityNodeProviderImpl
/* 38 */            public Object newAccessibilityNodeProviderBridge(AccessibilityNodeProviderCompat compat) {
/* 39 */                return null;
                    }
                }

/* 41 */        static class AccessibilityNodeProviderJellyBeanImpl extends AccessibilityNodeProviderStubImpl {
/* 42 */            AccessibilityNodeProviderJellyBeanImpl() {
                    }

                    /* JADX INFO: renamed from: android.support.v4.view.accessibility.AccessibilityNodeProviderCompat$AccessibilityNodeProviderJellyBeanImpl$1 */
/* 46 */            class C00571 implements AccessibilityNodeProviderCompatJellyBean.AccessibilityNodeInfoBridge {
                        final /* synthetic */ AccessibilityNodeProviderCompat val$compat;

/* 47 */                C00571(AccessibilityNodeProviderCompat accessibilityNodeProviderCompat) {
                            this.val$compat = accessibilityNodeProviderCompat;
                        }

                        @Override // android.support.v4.view.accessibility.AccessibilityNodeProviderCompatJellyBean.AccessibilityNodeInfoBridge
/* 51 */                public boolean performAction(int virtualViewId, int action, Bundle arguments) {
/* 52 */                    return this.val$compat.performAction(virtualViewId, action, arguments);
                        }

                        @Override // android.support.v4.view.accessibility.AccessibilityNodeProviderCompatJellyBean.AccessibilityNodeInfoBridge
/* 57 */                public List<Object> findAccessibilityNodeInfosByText(String text, int virtualViewId) {
/* 58 */                    List<AccessibilityNodeInfoCompat> compatInfos = this.val$compat.findAccessibilityNodeInfosByText(text, virtualViewId);
/* 60 */                    List<Object> infos = new ArrayList<>();
/* 61 */                    int infoCount = compatInfos.size();
                            for (int i = 0; i < infoCount; i++) {
/* 63 */                        AccessibilityNodeInfoCompat infoCompat = compatInfos.get(i);
/* 64 */                        infos.add(infoCompat.getInfo());
                            }
/* 66 */                    return infos;
                        }

                        @Override // android.support.v4.view.accessibility.AccessibilityNodeProviderCompatJellyBean.AccessibilityNodeInfoBridge
/* 71 */                public Object createAccessibilityNodeInfo(int virtualViewId) {
/* 72 */                    AccessibilityNodeInfoCompat compatInfo = this.val$compat.createAccessibilityNodeInfo(virtualViewId);
/* 74 */                    if (compatInfo == null) {
/* 75 */                        return null;
                            }
                            return compatInfo.getInfo();
                        }
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityNodeProviderCompat.AccessibilityNodeProviderStubImpl, android.support.v4.view.accessibility.AccessibilityNodeProviderCompat.AccessibilityNodeProviderImpl
/* 46 */            public Object newAccessibilityNodeProviderBridge(AccessibilityNodeProviderCompat compat) {
/* 47 */                return AccessibilityNodeProviderCompatJellyBean.newAccessibilityNodeProviderBridge(new C00571(compat));
                    }
                }

/* 82 */        static class AccessibilityNodeProviderKitKatImpl extends AccessibilityNodeProviderStubImpl {
/* 83 */            AccessibilityNodeProviderKitKatImpl() {
                    }

                    /* JADX INFO: renamed from: android.support.v4.view.accessibility.AccessibilityNodeProviderCompat$AccessibilityNodeProviderKitKatImpl$1 */
/* 87 */            class C00581 implements AccessibilityNodeProviderCompatKitKat.AccessibilityNodeInfoBridge {
                        final /* synthetic */ AccessibilityNodeProviderCompat val$compat;

/* 88 */                C00581(AccessibilityNodeProviderCompat accessibilityNodeProviderCompat) {
                            this.val$compat = accessibilityNodeProviderCompat;
                        }

                        @Override // android.support.v4.view.accessibility.AccessibilityNodeProviderCompatKitKat.AccessibilityNodeInfoBridge
/* 92 */                public boolean performAction(int virtualViewId, int action, Bundle arguments) {
/* 93 */                    return this.val$compat.performAction(virtualViewId, action, arguments);
                        }

                        @Override // android.support.v4.view.accessibility.AccessibilityNodeProviderCompatKitKat.AccessibilityNodeInfoBridge
/* 98 */                public List<Object> findAccessibilityNodeInfosByText(String text, int virtualViewId) {
/* 99 */                    List<AccessibilityNodeInfoCompat> compatInfos = this.val$compat.findAccessibilityNodeInfosByText(text, virtualViewId);
/* 101 */                   List<Object> infos = new ArrayList<>();
/* 102 */                   int infoCount = compatInfos.size();
                            for (int i = 0; i < infoCount; i++) {
/* 104 */                       AccessibilityNodeInfoCompat infoCompat = compatInfos.get(i);
/* 105 */                       infos.add(infoCompat.getInfo());
                            }
/* 107 */                   return infos;
                        }

                        @Override // android.support.v4.view.accessibility.AccessibilityNodeProviderCompatKitKat.AccessibilityNodeInfoBridge
/* 111 */               public Object createAccessibilityNodeInfo(int virtualViewId) {
/* 112 */                   AccessibilityNodeInfoCompat compatInfo = this.val$compat.createAccessibilityNodeInfo(virtualViewId);
/* 114 */                   if (compatInfo == null) {
/* 115 */                       return null;
                            }
                            return compatInfo.getInfo();
                        }

                        @Override // android.support.v4.view.accessibility.AccessibilityNodeProviderCompatKitKat.AccessibilityNodeInfoBridge
/* 122 */               public Object findFocus(int focus) {
/* 123 */                   AccessibilityNodeInfoCompat compatInfo = this.val$compat.findFocus(focus);
/* 124 */                   if (compatInfo == null) {
/* 125 */                       return null;
                            }
                            return compatInfo.getInfo();
                        }
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityNodeProviderCompat.AccessibilityNodeProviderStubImpl, android.support.v4.view.accessibility.AccessibilityNodeProviderCompat.AccessibilityNodeProviderImpl
/* 87 */            public Object newAccessibilityNodeProviderBridge(AccessibilityNodeProviderCompat compat) {
/* 88 */                return AccessibilityNodeProviderCompatKitKat.newAccessibilityNodeProviderBridge(new C00581(compat));
                    }
                }

                static {
/* 139 */           if (Build.VERSION.SDK_INT >= 19) {
/* 140 */               IMPL = new AccessibilityNodeProviderKitKatImpl();
                    } else if (Build.VERSION.SDK_INT >= 16) {
/* 142 */               IMPL = new AccessibilityNodeProviderJellyBeanImpl();
                    } else {
/* 144 */               IMPL = new AccessibilityNodeProviderStubImpl();
                    }
                }

/* 150 */       public AccessibilityNodeProviderCompat() {
/* 152 */           this.mProvider = IMPL.newAccessibilityNodeProviderBridge(this);
                }

/* 160 */       public AccessibilityNodeProviderCompat(Object provider) {
/* 162 */           this.mProvider = provider;
                }

/* 168 */       public Object getProvider() {
/* 169 */           return this.mProvider;
                }

/* 194 */       public AccessibilityNodeInfoCompat createAccessibilityNodeInfo(int virtualViewId) {
/* 195 */           return null;
                }

/* 211 */       public boolean performAction(int virtualViewId, int action, Bundle arguments) {
/* 212 */           return false;
                }

/* 230 */       public List<AccessibilityNodeInfoCompat> findAccessibilityNodeInfosByText(String text, int virtualViewId) {
/* 231 */           return null;
                }

/* 245 */       public AccessibilityNodeInfoCompat findFocus(int focus) {
/* 246 */           return null;
                }
            }
