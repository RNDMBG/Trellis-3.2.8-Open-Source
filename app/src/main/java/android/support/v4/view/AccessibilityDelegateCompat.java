            package android.support.v4.view;

            import android.os.Build;
            import android.os.Bundle;
            import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
            import android.support.v4.view.accessibility.AccessibilityNodeProviderCompat;
            import android.view.View;
            import android.view.ViewGroup;
            import android.view.accessibility.AccessibilityEvent;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 52 */    public class AccessibilityDelegateCompat {
                private static final Object DEFAULT_DELEGATE;
                private static final AccessibilityDelegateImpl IMPL;
                final Object mBridge = IMPL.newAccessiblityDelegateBridge(this);

                interface AccessibilityDelegateImpl {
                    boolean dispatchPopulateAccessibilityEvent(Object obj, View view, AccessibilityEvent accessibilityEvent);

                    AccessibilityNodeProviderCompat getAccessibilityNodeProvider(Object obj, View view);

                    Object newAccessiblityDelegateBridge(AccessibilityDelegateCompat accessibilityDelegateCompat);

                    Object newAccessiblityDelegateDefaultImpl();

                    void onInitializeAccessibilityEvent(Object obj, View view, AccessibilityEvent accessibilityEvent);

                    void onInitializeAccessibilityNodeInfo(Object obj, View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat);

                    void onPopulateAccessibilityEvent(Object obj, View view, AccessibilityEvent accessibilityEvent);

                    boolean onRequestSendAccessibilityEvent(Object obj, ViewGroup viewGroup, View view, AccessibilityEvent accessibilityEvent);

                    boolean performAccessibilityAction(Object obj, View view, int i, Bundle bundle);

                    void sendAccessibilityEvent(Object obj, View view, int i);

                    void sendAccessibilityEventUnchecked(Object obj, View view, AccessibilityEvent accessibilityEvent);
                }

/* 53 */        static class AccessibilityDelegateStubImpl implements AccessibilityDelegateImpl {
/* 54 */            AccessibilityDelegateStubImpl() {
                    }

                    @Override // android.support.v4.view.AccessibilityDelegateCompat.AccessibilityDelegateImpl
/* 56 */            public Object newAccessiblityDelegateDefaultImpl() {
/* 57 */                return null;
                    }

                    @Override // android.support.v4.view.AccessibilityDelegateCompat.AccessibilityDelegateImpl
/* 61 */            public Object newAccessiblityDelegateBridge(AccessibilityDelegateCompat listener) {
/* 62 */                return null;
                    }

                    @Override // android.support.v4.view.AccessibilityDelegateCompat.AccessibilityDelegateImpl
/* 67 */            public boolean dispatchPopulateAccessibilityEvent(Object delegate, View host, AccessibilityEvent event) {
/* 68 */                return false;
                    }

                    @Override // android.support.v4.view.AccessibilityDelegateCompat.AccessibilityDelegateImpl
/* 74 */            public void onInitializeAccessibilityEvent(Object delegate, View host, AccessibilityEvent event) {
                    }

                    @Override // android.support.v4.view.AccessibilityDelegateCompat.AccessibilityDelegateImpl
/* 80 */            public void onInitializeAccessibilityNodeInfo(Object delegate, View host, AccessibilityNodeInfoCompat info) {
                    }

                    @Override // android.support.v4.view.AccessibilityDelegateCompat.AccessibilityDelegateImpl
/* 86 */            public void onPopulateAccessibilityEvent(Object delegate, View host, AccessibilityEvent event) {
                    }

                    @Override // android.support.v4.view.AccessibilityDelegateCompat.AccessibilityDelegateImpl
/* 91 */            public boolean onRequestSendAccessibilityEvent(Object delegate, ViewGroup host, View child, AccessibilityEvent event) {
/* 92 */                return true;
                    }

                    @Override // android.support.v4.view.AccessibilityDelegateCompat.AccessibilityDelegateImpl
/* 97 */            public void sendAccessibilityEvent(Object delegate, View host, int eventType) {
                    }

                    @Override // android.support.v4.view.AccessibilityDelegateCompat.AccessibilityDelegateImpl
/* 103 */           public void sendAccessibilityEventUnchecked(Object delegate, View host, AccessibilityEvent event) {
                    }

                    @Override // android.support.v4.view.AccessibilityDelegateCompat.AccessibilityDelegateImpl
/* 108 */           public AccessibilityNodeProviderCompat getAccessibilityNodeProvider(Object delegate, View host) {
/* 109 */               return null;
                    }

                    @Override // android.support.v4.view.AccessibilityDelegateCompat.AccessibilityDelegateImpl
/* 114 */           public boolean performAccessibilityAction(Object delegate, View host, int action, Bundle args) {
/* 115 */               return false;
                    }
                }

/* 117 */       static class AccessibilityDelegateIcsImpl extends AccessibilityDelegateStubImpl {
/* 118 */           AccessibilityDelegateIcsImpl() {
                    }

                    @Override // android.support.v4.view.AccessibilityDelegateCompat.AccessibilityDelegateStubImpl, android.support.v4.view.AccessibilityDelegateCompat.AccessibilityDelegateImpl
/* 121 */           public Object newAccessiblityDelegateDefaultImpl() {
/* 122 */               return AccessibilityDelegateCompatIcs.newAccessibilityDelegateDefaultImpl();
                    }

                    /* JADX INFO: renamed from: android.support.v4.view.AccessibilityDelegateCompat$AccessibilityDelegateIcsImpl$1 */
/* 126 */           class C00391 implements AccessibilityDelegateCompatIcs.AccessibilityDelegateBridge {
                        final /* synthetic */ AccessibilityDelegateCompat val$compat;

/* 127 */               C00391(AccessibilityDelegateCompat accessibilityDelegateCompat) {
                            this.val$compat = accessibilityDelegateCompat;
                        }

                        @Override // android.support.v4.view.AccessibilityDelegateCompatIcs.AccessibilityDelegateBridge
/* 131 */               public boolean dispatchPopulateAccessibilityEvent(View host, AccessibilityEvent event) {
/* 132 */                   return this.val$compat.dispatchPopulateAccessibilityEvent(host, event);
                        }

                        @Override // android.support.v4.view.AccessibilityDelegateCompatIcs.AccessibilityDelegateBridge
/* 136 */               public void onInitializeAccessibilityEvent(View host, AccessibilityEvent event) {
/* 137 */                   this.val$compat.onInitializeAccessibilityEvent(host, event);
                        }

                        @Override // android.support.v4.view.AccessibilityDelegateCompatIcs.AccessibilityDelegateBridge
/* 141 */               public void onInitializeAccessibilityNodeInfo(View host, Object info) {
/* 142 */                   this.val$compat.onInitializeAccessibilityNodeInfo(host, new AccessibilityNodeInfoCompat(info));
                        }

                        @Override // android.support.v4.view.AccessibilityDelegateCompatIcs.AccessibilityDelegateBridge
/* 147 */               public void onPopulateAccessibilityEvent(View host, AccessibilityEvent event) {
/* 148 */                   this.val$compat.onPopulateAccessibilityEvent(host, event);
                        }

                        @Override // android.support.v4.view.AccessibilityDelegateCompatIcs.AccessibilityDelegateBridge
/* 153 */               public boolean onRequestSendAccessibilityEvent(ViewGroup host, View child, AccessibilityEvent event) {
/* 154 */                   return this.val$compat.onRequestSendAccessibilityEvent(host, child, event);
                        }

                        @Override // android.support.v4.view.AccessibilityDelegateCompatIcs.AccessibilityDelegateBridge
/* 158 */               public void sendAccessibilityEvent(View host, int eventType) {
/* 159 */                   this.val$compat.sendAccessibilityEvent(host, eventType);
                        }

                        @Override // android.support.v4.view.AccessibilityDelegateCompatIcs.AccessibilityDelegateBridge
/* 163 */               public void sendAccessibilityEventUnchecked(View host, AccessibilityEvent event) {
/* 164 */                   this.val$compat.sendAccessibilityEventUnchecked(host, event);
                        }
                    }

                    @Override // android.support.v4.view.AccessibilityDelegateCompat.AccessibilityDelegateStubImpl, android.support.v4.view.AccessibilityDelegateCompat.AccessibilityDelegateImpl
/* 126 */           public Object newAccessiblityDelegateBridge(AccessibilityDelegateCompat compat) {
/* 127 */               return AccessibilityDelegateCompatIcs.newAccessibilityDelegateBridge(new C00391(compat));
                    }

                    @Override // android.support.v4.view.AccessibilityDelegateCompat.AccessibilityDelegateStubImpl, android.support.v4.view.AccessibilityDelegateCompat.AccessibilityDelegateImpl
/* 171 */           public boolean dispatchPopulateAccessibilityEvent(Object delegate, View host, AccessibilityEvent event) {
/* 172 */               return AccessibilityDelegateCompatIcs.dispatchPopulateAccessibilityEvent(delegate, host, event);
                    }

                    @Override // android.support.v4.view.AccessibilityDelegateCompat.AccessibilityDelegateStubImpl, android.support.v4.view.AccessibilityDelegateCompat.AccessibilityDelegateImpl
/* 178 */           public void onInitializeAccessibilityEvent(Object delegate, View host, AccessibilityEvent event) {
/* 179 */               AccessibilityDelegateCompatIcs.onInitializeAccessibilityEvent(delegate, host, event);
                    }

                    @Override // android.support.v4.view.AccessibilityDelegateCompat.AccessibilityDelegateStubImpl, android.support.v4.view.AccessibilityDelegateCompat.AccessibilityDelegateImpl
/* 184 */           public void onInitializeAccessibilityNodeInfo(Object delegate, View host, AccessibilityNodeInfoCompat info) {
/* 185 */               AccessibilityDelegateCompatIcs.onInitializeAccessibilityNodeInfo(delegate, host, info.getInfo());
                    }

                    @Override // android.support.v4.view.AccessibilityDelegateCompat.AccessibilityDelegateStubImpl, android.support.v4.view.AccessibilityDelegateCompat.AccessibilityDelegateImpl
/* 191 */           public void onPopulateAccessibilityEvent(Object delegate, View host, AccessibilityEvent event) {
/* 192 */               AccessibilityDelegateCompatIcs.onPopulateAccessibilityEvent(delegate, host, event);
                    }

                    @Override // android.support.v4.view.AccessibilityDelegateCompat.AccessibilityDelegateStubImpl, android.support.v4.view.AccessibilityDelegateCompat.AccessibilityDelegateImpl
/* 197 */           public boolean onRequestSendAccessibilityEvent(Object delegate, ViewGroup host, View child, AccessibilityEvent event) {
/* 198 */               return AccessibilityDelegateCompatIcs.onRequestSendAccessibilityEvent(delegate, host, child, event);
                    }

                    @Override // android.support.v4.view.AccessibilityDelegateCompat.AccessibilityDelegateStubImpl, android.support.v4.view.AccessibilityDelegateCompat.AccessibilityDelegateImpl
/* 203 */           public void sendAccessibilityEvent(Object delegate, View host, int eventType) {
/* 204 */               AccessibilityDelegateCompatIcs.sendAccessibilityEvent(delegate, host, eventType);
                    }

                    @Override // android.support.v4.view.AccessibilityDelegateCompat.AccessibilityDelegateStubImpl, android.support.v4.view.AccessibilityDelegateCompat.AccessibilityDelegateImpl
/* 209 */           public void sendAccessibilityEventUnchecked(Object delegate, View host, AccessibilityEvent event) {
/* 210 */               AccessibilityDelegateCompatIcs.sendAccessibilityEventUnchecked(delegate, host, event);
                    }
                }

/* 212 */       static class AccessibilityDelegateJellyBeanImpl extends AccessibilityDelegateIcsImpl {
/* 213 */           AccessibilityDelegateJellyBeanImpl() {
                    }

                    @Override // android.support.v4.view.AccessibilityDelegateCompat.AccessibilityDelegateIcsImpl, android.support.v4.view.AccessibilityDelegateCompat.AccessibilityDelegateStubImpl, android.support.v4.view.AccessibilityDelegateCompat.AccessibilityDelegateImpl
/* 216 */           public Object newAccessiblityDelegateBridge(AccessibilityDelegateCompat compat) {
/* 217 */               return AccessibilityDelegateCompatJellyBean.newAccessibilityDelegateBridge(new C00401(compat));
                    }

                    /* JADX INFO: renamed from: android.support.v4.view.AccessibilityDelegateCompat$AccessibilityDelegateJellyBeanImpl$1 */
/* 217 */           class C00401 implements AccessibilityDelegateCompatJellyBean.AccessibilityDelegateBridgeJellyBean {
                        final /* synthetic */ AccessibilityDelegateCompat val$compat;

/* 218 */               C00401(AccessibilityDelegateCompat accessibilityDelegateCompat) {
                            this.val$compat = accessibilityDelegateCompat;
                        }

                        @Override // android.support.v4.view.AccessibilityDelegateCompatJellyBean.AccessibilityDelegateBridgeJellyBean
/* 222 */               public boolean dispatchPopulateAccessibilityEvent(View host, AccessibilityEvent event) {
/* 223 */                   return this.val$compat.dispatchPopulateAccessibilityEvent(host, event);
                        }

                        @Override // android.support.v4.view.AccessibilityDelegateCompatJellyBean.AccessibilityDelegateBridgeJellyBean
/* 227 */               public void onInitializeAccessibilityEvent(View host, AccessibilityEvent event) {
/* 228 */                   this.val$compat.onInitializeAccessibilityEvent(host, event);
                        }

                        @Override // android.support.v4.view.AccessibilityDelegateCompatJellyBean.AccessibilityDelegateBridgeJellyBean
/* 232 */               public void onInitializeAccessibilityNodeInfo(View host, Object info) {
/* 233 */                   this.val$compat.onInitializeAccessibilityNodeInfo(host, new AccessibilityNodeInfoCompat(info));
                        }

                        @Override // android.support.v4.view.AccessibilityDelegateCompatJellyBean.AccessibilityDelegateBridgeJellyBean
/* 238 */               public void onPopulateAccessibilityEvent(View host, AccessibilityEvent event) {
/* 239 */                   this.val$compat.onPopulateAccessibilityEvent(host, event);
                        }

                        @Override // android.support.v4.view.AccessibilityDelegateCompatJellyBean.AccessibilityDelegateBridgeJellyBean
/* 244 */               public boolean onRequestSendAccessibilityEvent(ViewGroup host, View child, AccessibilityEvent event) {
/* 245 */                   return this.val$compat.onRequestSendAccessibilityEvent(host, child, event);
                        }

                        @Override // android.support.v4.view.AccessibilityDelegateCompatJellyBean.AccessibilityDelegateBridgeJellyBean
/* 249 */               public void sendAccessibilityEvent(View host, int eventType) {
/* 250 */                   this.val$compat.sendAccessibilityEvent(host, eventType);
                        }

                        @Override // android.support.v4.view.AccessibilityDelegateCompatJellyBean.AccessibilityDelegateBridgeJellyBean
/* 254 */               public void sendAccessibilityEventUnchecked(View host, AccessibilityEvent event) {
/* 255 */                   this.val$compat.sendAccessibilityEventUnchecked(host, event);
                        }

                        @Override // android.support.v4.view.AccessibilityDelegateCompatJellyBean.AccessibilityDelegateBridgeJellyBean
/* 259 */               public Object getAccessibilityNodeProvider(View host) {
/* 260 */                   AccessibilityNodeProviderCompat provider = this.val$compat.getAccessibilityNodeProvider(host);
/* 262 */                   if (provider != null) {
                                return provider.getProvider();
                            }
                            return null;
                        }

                        @Override // android.support.v4.view.AccessibilityDelegateCompatJellyBean.AccessibilityDelegateBridgeJellyBean
/* 266 */               public boolean performAccessibilityAction(View host, int action, Bundle args) {
/* 267 */                   return this.val$compat.performAccessibilityAction(host, action, args);
                        }
                    }

                    @Override // android.support.v4.view.AccessibilityDelegateCompat.AccessibilityDelegateStubImpl, android.support.v4.view.AccessibilityDelegateCompat.AccessibilityDelegateImpl
/* 274 */           public AccessibilityNodeProviderCompat getAccessibilityNodeProvider(Object delegate, View host) {
/* 275 */               Object provider = AccessibilityDelegateCompatJellyBean.getAccessibilityNodeProvider(delegate, host);
/* 277 */               if (provider != null) {
/* 278 */                   return new AccessibilityNodeProviderCompat(provider);
                        }
                        return null;
                    }

                    @Override // android.support.v4.view.AccessibilityDelegateCompat.AccessibilityDelegateStubImpl, android.support.v4.view.AccessibilityDelegateCompat.AccessibilityDelegateImpl
/* 285 */           public boolean performAccessibilityAction(Object delegate, View host, int action, Bundle args) {
/* 286 */               return AccessibilityDelegateCompatJellyBean.performAccessibilityAction(delegate, host, action, args);
                    }
                }

                static {
/* 295 */           if (Build.VERSION.SDK_INT >= 16) {
/* 296 */               IMPL = new AccessibilityDelegateJellyBeanImpl();
                    } else if (Build.VERSION.SDK_INT >= 14) {
/* 298 */               IMPL = new AccessibilityDelegateIcsImpl();
                    } else {
/* 300 */               IMPL = new AccessibilityDelegateStubImpl();
                    }
/* 302 */           DEFAULT_DELEGATE = IMPL.newAccessiblityDelegateDefaultImpl();
                }

/* 317 */       Object getBridge() {
/* 318 */           return this.mBridge;
                }

/* 335 */       public void sendAccessibilityEvent(View host, int eventType) {
/* 336 */           IMPL.sendAccessibilityEvent(DEFAULT_DELEGATE, host, eventType);
                }

/* 357 */       public void sendAccessibilityEventUnchecked(View host, AccessibilityEvent event) {
/* 358 */           IMPL.sendAccessibilityEventUnchecked(DEFAULT_DELEGATE, host, event);
                }

/* 378 */       public boolean dispatchPopulateAccessibilityEvent(View host, AccessibilityEvent event) {
/* 379 */           return IMPL.dispatchPopulateAccessibilityEvent(DEFAULT_DELEGATE, host, event);
                }

/* 398 */       public void onPopulateAccessibilityEvent(View host, AccessibilityEvent event) {
/* 399 */           IMPL.onPopulateAccessibilityEvent(DEFAULT_DELEGATE, host, event);
                }

/* 418 */       public void onInitializeAccessibilityEvent(View host, AccessibilityEvent event) {
/* 419 */           IMPL.onInitializeAccessibilityEvent(DEFAULT_DELEGATE, host, event);
                }

/* 437 */       public void onInitializeAccessibilityNodeInfo(View host, AccessibilityNodeInfoCompat info) {
/* 438 */           IMPL.onInitializeAccessibilityNodeInfo(DEFAULT_DELEGATE, host, info);
                }

/* 461 */       public boolean onRequestSendAccessibilityEvent(ViewGroup host, View child, AccessibilityEvent event) {
/* 462 */           return IMPL.onRequestSendAccessibilityEvent(DEFAULT_DELEGATE, host, child, event);
                }

/* 479 */       public AccessibilityNodeProviderCompat getAccessibilityNodeProvider(View host) {
/* 480 */           return IMPL.getAccessibilityNodeProvider(DEFAULT_DELEGATE, host);
                }

/* 499 */       public boolean performAccessibilityAction(View host, int action, Bundle args) {
/* 500 */           return IMPL.performAccessibilityAction(DEFAULT_DELEGATE, host, action, args);
                }
            }
