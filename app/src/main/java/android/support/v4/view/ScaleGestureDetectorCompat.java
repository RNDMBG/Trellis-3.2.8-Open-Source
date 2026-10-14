            package android.support.v4.view;

            import android.os.Build;
            
            
    public class ScaleGestureDetectorCompat {
                static final ScaleGestureDetectorImpl IMPL;

                interface ScaleGestureDetectorImpl {
                    boolean isQuickScaleEnabled(Object obj);

                    void setQuickScaleEnabled(Object obj, boolean z);
                }

        private static class BaseScaleGestureDetectorImpl implements ScaleGestureDetectorImpl {
            private BaseScaleGestureDetectorImpl() {
                    }

             BaseScaleGestureDetectorImpl(C00501 x0) {
                this();
                    }

                    @Override 
            public void setQuickScaleEnabled(Object o, boolean enabled) {
                    }

                    @Override 
            public boolean isQuickScaleEnabled(Object o) {
                return false;
                    }
                }

        private static class ScaleGestureDetectorCompatKitKatImpl implements ScaleGestureDetectorImpl {
            private ScaleGestureDetectorCompatKitKatImpl() {
                    }

             ScaleGestureDetectorCompatKitKatImpl(C00501 x0) {
                this();
                    }

                    @Override 
            public void setQuickScaleEnabled(Object o, boolean enabled) {
                ScaleGestureDetectorCompatKitKat.setQuickScaleEnabled(o, enabled);
                    }

                    @Override 
            public boolean isQuickScaleEnabled(Object o) {
                return ScaleGestureDetectorCompatKitKat.isQuickScaleEnabled(o);
                    }
                }

                static {
            int version = Build.VERSION.SDK_INT;
            if (version >= 19) {
                IMPL = new ScaleGestureDetectorCompatKitKatImpl(null);
                    } else {
                IMPL = new BaseScaleGestureDetectorImpl(null);
                    }
                }

        private ScaleGestureDetectorCompat() {
                }

        public static void setQuickScaleEnabled(Object scaleGestureDetector, boolean enabled) {
            IMPL.setQuickScaleEnabled(scaleGestureDetector, enabled);
                }

        public static boolean isQuickScaleEnabled(Object scaleGestureDetector) {
            return IMPL.isQuickScaleEnabled(scaleGestureDetector);
                }
            }
