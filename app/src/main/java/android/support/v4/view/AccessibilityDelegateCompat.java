            package android.support.v4.view;

            import android.os.Build;
            import android.os.Bundle;
            import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
            import android.support.v4.view.accessibility.AccessibilityNodeProviderCompat;
            import android.view.View;
            import android.view.ViewGroup;
            import android.view.accessibility.AccessibilityEvent;
            
            
    public class AccessibilityDelegateCompat {
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

        static class AccessibilityDelegateStubImpl implements AccessibilityDelegateImpl {
            AccessibilityDelegateStubImpl() {
                    }

                    @Override 
            public Object newAccessiblityDelegateDefaultImpl() {
                return null;
                    }

                    @Override 
            public Object newAccessiblityDelegateBridge(AccessibilityDelegateCompat listener) {
                return null;
                    }

                    @Override 
            public boolean dispatchPopulateAccessibilityEvent(Object delegate, View host, AccessibilityEvent event) {
                return false;
                    }

                    @Override 
            public void onInitializeAccessibilityEvent(Object delegate, View host, AccessibilityEvent event) {
                    }

                    @Override 
            public void onInitializeAccessibilityNodeInfo(Object delegate, View host, AccessibilityNodeInfoCompat info) {
                    }

                    @Override 
            public void onPopulateAccessibilityEvent(Object delegate, View host, AccessibilityEvent event) {
                    }

                    @Override 
            public boolean onRequestSendAccessibilityEvent(Object delegate, ViewGroup host, View child, AccessibilityEvent event) {
                return true;
                    }

                    @Override 
            public void sendAccessibilityEvent(Object delegate, View host, int eventType) {
                    }

                    @Override 
           public void sendAccessibilityEventUnchecked(Object delegate, View host, AccessibilityEvent event) {
                    }

                    @Override 
           public AccessibilityNodeProviderCompat getAccessibilityNodeProvider(Object delegate, View host) {
               return null;
                    }

                    @Override 
           public boolean performAccessibilityAction(Object delegate, View host, int action, Bundle args) {
               return false;
                    }
                }

       static class AccessibilityDelegateIcsImpl extends AccessibilityDelegateStubImpl {
           AccessibilityDelegateIcsImpl() {
                    }

                    @Override 
           public Object newAccessiblityDelegateDefaultImpl() {
               return AccessibilityDelegateCompatIcs.newAccessibilityDelegateDefaultImpl();
                    }

                    
           class C00391 implements AccessibilityDelegateCompatIcs.AccessibilityDelegateBridge {
                        final  AccessibilityDelegateCompat val$compat;

               C00391(AccessibilityDelegateCompat accessibilityDelegateCompat) {
                            this.val$compat = accessibilityDelegateCompat;
                        }

                        @Override 
               public boolean dispatchPopulateAccessibilityEvent(View host, AccessibilityEvent event) {
                   return this.val$compat.dispatchPopulateAccessibilityEvent(host, event);
                        }

                        @Override 
               public void onInitializeAccessibilityEvent(View host, AccessibilityEvent event) {
                   this.val$compat.onInitializeAccessibilityEvent(host, event);
                        }

                        @Override 
               public void onInitializeAccessibilityNodeInfo(View host, Object info) {
                   this.val$compat.onInitializeAccessibilityNodeInfo(host, new AccessibilityNodeInfoCompat(info));
                        }

                        @Override 
               public void onPopulateAccessibilityEvent(View host, AccessibilityEvent event) {
                   this.val$compat.onPopulateAccessibilityEvent(host, event);
                        }

                        @Override 
               public boolean onRequestSendAccessibilityEvent(ViewGroup host, View child, AccessibilityEvent event) {
                   return this.val$compat.onRequestSendAccessibilityEvent(host, child, event);
                        }

                        @Override 
               public void sendAccessibilityEvent(View host, int eventType) {
                   this.val$compat.sendAccessibilityEvent(host, eventType);
                        }

                        @Override 
               public void sendAccessibilityEventUnchecked(View host, AccessibilityEvent event) {
                   this.val$compat.sendAccessibilityEventUnchecked(host, event);
                        }
                    }

                    @Override 
           public Object newAccessiblityDelegateBridge(AccessibilityDelegateCompat compat) {
               return AccessibilityDelegateCompatIcs.newAccessibilityDelegateBridge(new C00391(compat));
                    }

                    @Override 
           public boolean dispatchPopulateAccessibilityEvent(Object delegate, View host, AccessibilityEvent event) {
               return AccessibilityDelegateCompatIcs.dispatchPopulateAccessibilityEvent(delegate, host, event);
                    }

                    @Override 
           public void onInitializeAccessibilityEvent(Object delegate, View host, AccessibilityEvent event) {
               AccessibilityDelegateCompatIcs.onInitializeAccessibilityEvent(delegate, host, event);
                    }

                    @Override 
           public void onInitializeAccessibilityNodeInfo(Object delegate, View host, AccessibilityNodeInfoCompat info) {
               AccessibilityDelegateCompatIcs.onInitializeAccessibilityNodeInfo(delegate, host, info.getInfo());
                    }

                    @Override 
           public void onPopulateAccessibilityEvent(Object delegate, View host, AccessibilityEvent event) {
               AccessibilityDelegateCompatIcs.onPopulateAccessibilityEvent(delegate, host, event);
                    }

                    @Override 
           public boolean onRequestSendAccessibilityEvent(Object delegate, ViewGroup host, View child, AccessibilityEvent event) {
               return AccessibilityDelegateCompatIcs.onRequestSendAccessibilityEvent(delegate, host, child, event);
                    }

                    @Override 
           public void sendAccessibilityEvent(Object delegate, View host, int eventType) {
               AccessibilityDelegateCompatIcs.sendAccessibilityEvent(delegate, host, eventType);
                    }

                    @Override 
           public void sendAccessibilityEventUnchecked(Object delegate, View host, AccessibilityEvent event) {
               AccessibilityDelegateCompatIcs.sendAccessibilityEventUnchecked(delegate, host, event);
                    }
                }

       static class AccessibilityDelegateJellyBeanImpl extends AccessibilityDelegateIcsImpl {
           AccessibilityDelegateJellyBeanImpl() {
                    }

                    @Override 
           public Object newAccessiblityDelegateBridge(AccessibilityDelegateCompat compat) {
               return AccessibilityDelegateCompatJellyBean.newAccessibilityDelegateBridge(new C00401(compat));
                    }

                    
           class C00401 implements AccessibilityDelegateCompatJellyBean.AccessibilityDelegateBridgeJellyBean {
                        final  AccessibilityDelegateCompat val$compat;

               C00401(AccessibilityDelegateCompat accessibilityDelegateCompat) {
                            this.val$compat = accessibilityDelegateCompat;
                        }

                        @Override 
               public boolean dispatchPopulateAccessibilityEvent(View host, AccessibilityEvent event) {
                   return this.val$compat.dispatchPopulateAccessibilityEvent(host, event);
                        }

                        @Override 
               public void onInitializeAccessibilityEvent(View host, AccessibilityEvent event) {
                   this.val$compat.onInitializeAccessibilityEvent(host, event);
                        }

                        @Override 
               public void onInitializeAccessibilityNodeInfo(View host, Object info) {
                   this.val$compat.onInitializeAccessibilityNodeInfo(host, new AccessibilityNodeInfoCompat(info));
                        }

                        @Override 
               public void onPopulateAccessibilityEvent(View host, AccessibilityEvent event) {
                   this.val$compat.onPopulateAccessibilityEvent(host, event);
                        }

                        @Override 
               public boolean onRequestSendAccessibilityEvent(ViewGroup host, View child, AccessibilityEvent event) {
                   return this.val$compat.onRequestSendAccessibilityEvent(host, child, event);
                        }

                        @Override 
               public void sendAccessibilityEvent(View host, int eventType) {
                   this.val$compat.sendAccessibilityEvent(host, eventType);
                        }

                        @Override 
               public void sendAccessibilityEventUnchecked(View host, AccessibilityEvent event) {
                   this.val$compat.sendAccessibilityEventUnchecked(host, event);
                        }

                        @Override 
               public Object getAccessibilityNodeProvider(View host) {
                   AccessibilityNodeProviderCompat provider = this.val$compat.getAccessibilityNodeProvider(host);
                   if (provider != null) {
                                return provider.getProvider();
                            }
                            return null;
                        }

                        @Override 
               public boolean performAccessibilityAction(View host, int action, Bundle args) {
                   return this.val$compat.performAccessibilityAction(host, action, args);
                        }
                    }

                    @Override 
           public AccessibilityNodeProviderCompat getAccessibilityNodeProvider(Object delegate, View host) {
               Object provider = AccessibilityDelegateCompatJellyBean.getAccessibilityNodeProvider(delegate, host);
               if (provider != null) {
                   return new AccessibilityNodeProviderCompat(provider);
                        }
                        return null;
                    }

                    @Override 
           public boolean performAccessibilityAction(Object delegate, View host, int action, Bundle args) {
               return AccessibilityDelegateCompatJellyBean.performAccessibilityAction(delegate, host, action, args);
                    }
                }

                static {
           if (Build.VERSION.SDK_INT >= 16) {
               IMPL = new AccessibilityDelegateJellyBeanImpl();
                    } else if (Build.VERSION.SDK_INT >= 14) {
               IMPL = new AccessibilityDelegateIcsImpl();
                    } else {
               IMPL = new AccessibilityDelegateStubImpl();
                    }
           DEFAULT_DELEGATE = IMPL.newAccessiblityDelegateDefaultImpl();
                }

       Object getBridge() {
           return this.mBridge;
                }

       public void sendAccessibilityEvent(View host, int eventType) {
           IMPL.sendAccessibilityEvent(DEFAULT_DELEGATE, host, eventType);
                }

       public void sendAccessibilityEventUnchecked(View host, AccessibilityEvent event) {
           IMPL.sendAccessibilityEventUnchecked(DEFAULT_DELEGATE, host, event);
                }

       public boolean dispatchPopulateAccessibilityEvent(View host, AccessibilityEvent event) {
           return IMPL.dispatchPopulateAccessibilityEvent(DEFAULT_DELEGATE, host, event);
                }

       public void onPopulateAccessibilityEvent(View host, AccessibilityEvent event) {
           IMPL.onPopulateAccessibilityEvent(DEFAULT_DELEGATE, host, event);
                }

       public void onInitializeAccessibilityEvent(View host, AccessibilityEvent event) {
           IMPL.onInitializeAccessibilityEvent(DEFAULT_DELEGATE, host, event);
                }

       public void onInitializeAccessibilityNodeInfo(View host, AccessibilityNodeInfoCompat info) {
           IMPL.onInitializeAccessibilityNodeInfo(DEFAULT_DELEGATE, host, info);
                }

       public boolean onRequestSendAccessibilityEvent(ViewGroup host, View child, AccessibilityEvent event) {
           return IMPL.onRequestSendAccessibilityEvent(DEFAULT_DELEGATE, host, child, event);
                }

       public AccessibilityNodeProviderCompat getAccessibilityNodeProvider(View host) {
           return IMPL.getAccessibilityNodeProvider(DEFAULT_DELEGATE, host);
                }

       public boolean performAccessibilityAction(View host, int action, Bundle args) {
           return IMPL.performAccessibilityAction(DEFAULT_DELEGATE, host, action, args);
                }
            }
