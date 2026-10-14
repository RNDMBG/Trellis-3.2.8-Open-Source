            package android.support.v4.widget;

            import android.content.Context;
            import android.graphics.Canvas;
            import android.widget.EdgeEffect;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 26 */    class EdgeEffectCompatIcs {
/* 27 */        EdgeEffectCompatIcs() {
                }

/* 29 */        public static Object newEdgeEffect(Context context) {
/* 30 */            return new EdgeEffect(context);
                }

/* 33 */        public static void setSize(Object edgeEffect, int width, int height) {
/* 34 */            ((EdgeEffect) edgeEffect).setSize(width, height);
                }

/* 37 */        public static boolean isFinished(Object edgeEffect) {
/* 38 */            return ((EdgeEffect) edgeEffect).isFinished();
                }

/* 41 */        public static void finish(Object edgeEffect) {
/* 42 */            ((EdgeEffect) edgeEffect).finish();
                }

/* 45 */        public static boolean onPull(Object edgeEffect, float deltaDistance) {
/* 46 */            ((EdgeEffect) edgeEffect).onPull(deltaDistance);
/* 47 */            return true;
                }

/* 50 */        public static boolean onRelease(Object edgeEffect) {
/* 51 */            EdgeEffect eff = (EdgeEffect) edgeEffect;
/* 52 */            eff.onRelease();
/* 53 */            return eff.isFinished();
                }

/* 56 */        public static boolean onAbsorb(Object edgeEffect, int velocity) {
/* 57 */            ((EdgeEffect) edgeEffect).onAbsorb(velocity);
/* 58 */            return true;
                }

/* 61 */        public static boolean draw(Object edgeEffect, Canvas canvas) {
/* 62 */            return ((EdgeEffect) edgeEffect).draw(canvas);
                }
            }
