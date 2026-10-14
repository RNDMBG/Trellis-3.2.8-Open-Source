            package android.support.v4.view;

            import android.view.ScaleGestureDetector;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 27 */    class ScaleGestureDetectorCompatKitKat {
/* 28 */        private ScaleGestureDetectorCompatKitKat() {
                }

/* 32 */        public static void setQuickScaleEnabled(Object scaleGestureDetector, boolean enabled) {
/* 33 */            ((ScaleGestureDetector) scaleGestureDetector).setQuickScaleEnabled(enabled);
                }

/* 36 */        public static boolean isQuickScaleEnabled(Object scaleGestureDetector) {
/* 37 */            return ((ScaleGestureDetector) scaleGestureDetector).isQuickScaleEnabled();
                }
            }
