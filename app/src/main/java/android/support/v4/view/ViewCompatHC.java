            package android.support.v4.view;

            import android.animation.ValueAnimator;
            import android.graphics.Paint;
            import android.view.View;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 21 */    class ViewCompatHC {
/* 22 */        ViewCompatHC() {
                }

/* 24 */        static long getFrameTime() {
/* 25 */            return ValueAnimator.getFrameDelay();
                }

/* 28 */        public static float getAlpha(View view) {
/* 29 */            return view.getAlpha();
                }

/* 32 */        public static void setLayerType(View view, int layerType, Paint paint) {
/* 33 */            view.setLayerType(layerType, paint);
                }

/* 36 */        public static int getLayerType(View view) {
/* 37 */            return view.getLayerType();
                }

/* 40 */        public static int resolveSizeAndState(int size, int measureSpec, int childMeasuredState) {
/* 41 */            return View.resolveSizeAndState(size, measureSpec, childMeasuredState);
                }

/* 44 */        public static int getMeasuredWidthAndState(View view) {
/* 45 */            return view.getMeasuredWidthAndState();
                }

/* 48 */        public static int getMeasuredHeightAndState(View view) {
/* 49 */            return view.getMeasuredHeightAndState();
                }

/* 52 */        public static int getMeasuredState(View view) {
/* 53 */            return view.getMeasuredState();
                }
            }
