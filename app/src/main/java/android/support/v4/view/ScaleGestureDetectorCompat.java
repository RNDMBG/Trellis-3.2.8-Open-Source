            package android.support.v4.view;

            import android.os.Build;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 30 */    public class ScaleGestureDetectorCompat {
                static final ScaleGestureDetectorImpl IMPL;

                interface ScaleGestureDetectorImpl {
                    boolean isQuickScaleEnabled(Object obj);

                    void setQuickScaleEnabled(Object obj, boolean z);
                }

/* 31 */        private static class BaseScaleGestureDetectorImpl implements ScaleGestureDetectorImpl {
/* 32 */            private BaseScaleGestureDetectorImpl() {
                    }

/* 32 */            /* synthetic */ BaseScaleGestureDetectorImpl(C00501 x0) {
/* 33 */                this();
                    }

                    @Override // android.support.v4.view.ScaleGestureDetectorCompat.ScaleGestureDetectorImpl
/* 36 */            public void setQuickScaleEnabled(Object o, boolean enabled) {
                    }

                    @Override // android.support.v4.view.ScaleGestureDetectorCompat.ScaleGestureDetectorImpl
/* 40 */            public boolean isQuickScaleEnabled(Object o) {
/* 41 */                return false;
                    }
                }

/* 43 */        private static class ScaleGestureDetectorCompatKitKatImpl implements ScaleGestureDetectorImpl {
/* 44 */            private ScaleGestureDetectorCompatKitKatImpl() {
                    }

/* 44 */            /* synthetic */ ScaleGestureDetectorCompatKitKatImpl(C00501 x0) {
/* 45 */                this();
                    }

                    @Override // android.support.v4.view.ScaleGestureDetectorCompat.ScaleGestureDetectorImpl
/* 47 */            public void setQuickScaleEnabled(Object o, boolean enabled) {
/* 48 */                ScaleGestureDetectorCompatKitKat.setQuickScaleEnabled(o, enabled);
                    }

                    @Override // android.support.v4.view.ScaleGestureDetectorCompat.ScaleGestureDetectorImpl
/* 52 */            public boolean isQuickScaleEnabled(Object o) {
/* 53 */                return ScaleGestureDetectorCompatKitKat.isQuickScaleEnabled(o);
                    }
                }

                static {
/* 58 */            int version = Build.VERSION.SDK_INT;
/* 59 */            if (version >= 19) {
/* 60 */                IMPL = new ScaleGestureDetectorCompatKitKatImpl(null);
                    } else {
/* 62 */                IMPL = new BaseScaleGestureDetectorImpl(null);
                    }
                }

/* 65 */        private ScaleGestureDetectorCompat() {
                }

/* 74 */        public static void setQuickScaleEnabled(Object scaleGestureDetector, boolean enabled) {
/* 75 */            IMPL.setQuickScaleEnabled(scaleGestureDetector, enabled);
                }

/* 82 */        public static boolean isQuickScaleEnabled(Object scaleGestureDetector) {
/* 83 */            return IMPL.isQuickScaleEnabled(scaleGestureDetector);
                }
            }
