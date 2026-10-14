            package android.support.v4.view;

            import android.os.Build;
            import android.view.View;
            import android.view.ViewGroup;
            import android.view.accessibility.AccessibilityEvent;
            
            
    public class ViewGroupCompat {
                static final ViewGroupCompatImpl IMPL;
                public static final int LAYOUT_MODE_CLIP_BOUNDS = 0;
                public static final int LAYOUT_MODE_OPTICAL_BOUNDS = 1;

                interface ViewGroupCompatImpl {
                    int getLayoutMode(ViewGroup viewGroup);

                    boolean onRequestSendAccessibilityEvent(ViewGroup viewGroup, View view, AccessibilityEvent accessibilityEvent);

                    void setLayoutMode(ViewGroup viewGroup, int i);

                    void setMotionEventSplittingEnabled(ViewGroup viewGroup, boolean z);
                }

        static class ViewGroupCompatStubImpl implements ViewGroupCompatImpl {
            ViewGroupCompatStubImpl() {
                    }

                    @Override 
            public boolean onRequestSendAccessibilityEvent(ViewGroup group, View child, AccessibilityEvent event) {
                return true;
                    }

                    @Override 
            public void setMotionEventSplittingEnabled(ViewGroup group, boolean split) {
                    }

                    @Override 
            public int getLayoutMode(ViewGroup group) {
                return 0;
                    }

                    @Override 
            public void setLayoutMode(ViewGroup group, int mode) {
                    }
                }

        static class ViewGroupCompatHCImpl extends ViewGroupCompatStubImpl {
            ViewGroupCompatHCImpl() {
                    }

                    @Override 
            public void setMotionEventSplittingEnabled(ViewGroup group, boolean split) {
                ViewGroupCompatHC.setMotionEventSplittingEnabled(group, split);
                    }
                }

        static class ViewGroupCompatIcsImpl extends ViewGroupCompatHCImpl {
            ViewGroupCompatIcsImpl() {
                    }

                    @Override 
            public boolean onRequestSendAccessibilityEvent(ViewGroup group, View child, AccessibilityEvent event) {
                return ViewGroupCompatIcs.onRequestSendAccessibilityEvent(group, child, event);
                    }
                }

        static class ViewGroupCompatJellybeanMR2Impl extends ViewGroupCompatIcsImpl {
            ViewGroupCompatJellybeanMR2Impl() {
                    }

                    @Override 
            public int getLayoutMode(ViewGroup group) {
                return ViewGroupCompatJellybeanMR2.getLayoutMode(group);
                    }

                    @Override 
            public void setLayoutMode(ViewGroup group, int mode) {
                ViewGroupCompatJellybeanMR2.setLayoutMode(group, mode);
                    }
                }

                static {
           int version = Build.VERSION.SDK_INT;
           if (version >= 18) {
               IMPL = new ViewGroupCompatJellybeanMR2Impl();
               return;
                    }
           if (version >= 14) {
               IMPL = new ViewGroupCompatIcsImpl();
                    } else if (version >= 11) {
               IMPL = new ViewGroupCompatHCImpl();
                    } else {
               IMPL = new ViewGroupCompatStubImpl();
                    }
                }

       private ViewGroupCompat() {
                }

       public static boolean onRequestSendAccessibilityEvent(ViewGroup group, View child, AccessibilityEvent event) {
           return IMPL.onRequestSendAccessibilityEvent(group, child, event);
                }

       public static void setMotionEventSplittingEnabled(ViewGroup group, boolean split) {
           IMPL.setMotionEventSplittingEnabled(group, split);
                }

       public static int getLayoutMode(ViewGroup group) {
           return IMPL.getLayoutMode(group);
                }

       public static void setLayoutMode(ViewGroup group, int mode) {
           IMPL.setLayoutMode(group, mode);
                }
            }
