            package android.support.v4.view;

            import android.os.Build;
            import android.view.View;
            import android.view.ViewGroup;
            import android.view.accessibility.AccessibilityEvent;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 52 */    public class ViewGroupCompat {
                static final ViewGroupCompatImpl IMPL;
                public static final int LAYOUT_MODE_CLIP_BOUNDS = 0;
                public static final int LAYOUT_MODE_OPTICAL_BOUNDS = 1;

                interface ViewGroupCompatImpl {
                    int getLayoutMode(ViewGroup viewGroup);

                    boolean onRequestSendAccessibilityEvent(ViewGroup viewGroup, View view, AccessibilityEvent accessibilityEvent);

                    void setLayoutMode(ViewGroup viewGroup, int i);

                    void setMotionEventSplittingEnabled(ViewGroup viewGroup, boolean z);
                }

/* 53 */        static class ViewGroupCompatStubImpl implements ViewGroupCompatImpl {
/* 54 */            ViewGroupCompatStubImpl() {
                    }

                    @Override // android.support.v4.view.ViewGroupCompat.ViewGroupCompatImpl
/* 57 */            public boolean onRequestSendAccessibilityEvent(ViewGroup group, View child, AccessibilityEvent event) {
/* 58 */                return true;
                    }

                    @Override // android.support.v4.view.ViewGroupCompat.ViewGroupCompatImpl
/* 62 */            public void setMotionEventSplittingEnabled(ViewGroup group, boolean split) {
                    }

                    @Override // android.support.v4.view.ViewGroupCompat.ViewGroupCompatImpl
/* 66 */            public int getLayoutMode(ViewGroup group) {
/* 67 */                return 0;
                    }

                    @Override // android.support.v4.view.ViewGroupCompat.ViewGroupCompatImpl
/* 72 */            public void setLayoutMode(ViewGroup group, int mode) {
                    }
                }

/* 74 */        static class ViewGroupCompatHCImpl extends ViewGroupCompatStubImpl {
/* 75 */            ViewGroupCompatHCImpl() {
                    }

                    @Override // android.support.v4.view.ViewGroupCompat.ViewGroupCompatStubImpl, android.support.v4.view.ViewGroupCompat.ViewGroupCompatImpl
/* 78 */            public void setMotionEventSplittingEnabled(ViewGroup group, boolean split) {
/* 79 */                ViewGroupCompatHC.setMotionEventSplittingEnabled(group, split);
                    }
                }

/* 81 */        static class ViewGroupCompatIcsImpl extends ViewGroupCompatHCImpl {
/* 82 */            ViewGroupCompatIcsImpl() {
                    }

                    @Override // android.support.v4.view.ViewGroupCompat.ViewGroupCompatStubImpl, android.support.v4.view.ViewGroupCompat.ViewGroupCompatImpl
/* 86 */            public boolean onRequestSendAccessibilityEvent(ViewGroup group, View child, AccessibilityEvent event) {
/* 87 */                return ViewGroupCompatIcs.onRequestSendAccessibilityEvent(group, child, event);
                    }
                }

/* 89 */        static class ViewGroupCompatJellybeanMR2Impl extends ViewGroupCompatIcsImpl {
/* 90 */            ViewGroupCompatJellybeanMR2Impl() {
                    }

                    @Override // android.support.v4.view.ViewGroupCompat.ViewGroupCompatStubImpl, android.support.v4.view.ViewGroupCompat.ViewGroupCompatImpl
/* 93 */            public int getLayoutMode(ViewGroup group) {
/* 94 */                return ViewGroupCompatJellybeanMR2.getLayoutMode(group);
                    }

                    @Override // android.support.v4.view.ViewGroupCompat.ViewGroupCompatStubImpl, android.support.v4.view.ViewGroupCompat.ViewGroupCompatImpl
/* 98 */            public void setLayoutMode(ViewGroup group, int mode) {
/* 99 */                ViewGroupCompatJellybeanMR2.setLayoutMode(group, mode);
                    }
                }

                static {
/* 105 */           int version = Build.VERSION.SDK_INT;
/* 106 */           if (version >= 18) {
/* 107 */               IMPL = new ViewGroupCompatJellybeanMR2Impl();
/* 115 */               return;
                    }
/* 108 */           if (version >= 14) {
/* 109 */               IMPL = new ViewGroupCompatIcsImpl();
                    } else if (version >= 11) {
/* 111 */               IMPL = new ViewGroupCompatHCImpl();
                    } else {
/* 113 */               IMPL = new ViewGroupCompatStubImpl();
                    }
                }

/* 119 */       private ViewGroupCompat() {
                }

/* 140 */       public static boolean onRequestSendAccessibilityEvent(ViewGroup group, View child, AccessibilityEvent event) {
/* 141 */           return IMPL.onRequestSendAccessibilityEvent(group, child, event);
                }

/* 160 */       public static void setMotionEventSplittingEnabled(ViewGroup group, boolean split) {
/* 161 */           IMPL.setMotionEventSplittingEnabled(group, split);
                }

/* 176 */       public static int getLayoutMode(ViewGroup group) {
/* 177 */           return IMPL.getLayoutMode(group);
                }

/* 189 */       public static void setLayoutMode(ViewGroup group, int mode) {
/* 190 */           IMPL.setLayoutMode(group, mode);
                }
            }
