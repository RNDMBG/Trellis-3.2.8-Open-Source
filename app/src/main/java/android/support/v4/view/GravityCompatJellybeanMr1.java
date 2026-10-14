            package android.support.v4.view;

            import android.graphics.Rect;
            import android.view.Gravity;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 21 */    class GravityCompatJellybeanMr1 {
/* 22 */        GravityCompatJellybeanMr1() {
                }

/* 25 */        public static int getAbsoluteGravity(int gravity, int layoutDirection) {
/* 26 */            return Gravity.getAbsoluteGravity(gravity, layoutDirection);
                }

/* 30 */        public static void apply(int gravity, int w, int h, Rect container, Rect outRect, int layoutDirection) {
/* 31 */            Gravity.apply(gravity, w, h, container, outRect, layoutDirection);
                }

/* 35 */        public static void apply(int gravity, int w, int h, Rect container, int xAdj, int yAdj, Rect outRect, int layoutDirection) {
/* 36 */            Gravity.apply(gravity, w, h, container, xAdj, yAdj, outRect, layoutDirection);
                }

/* 39 */        public static void applyDisplay(int gravity, Rect display, Rect inoutObj, int layoutDirection) {
/* 40 */            Gravity.applyDisplay(gravity, display, inoutObj, layoutDirection);
                }
            }
