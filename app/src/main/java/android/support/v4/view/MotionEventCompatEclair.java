            package android.support.v4.view;

            import android.view.MotionEvent;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 22 */    class MotionEventCompatEclair {
/* 23 */        MotionEventCompatEclair() {
                }

/* 25 */        public static int findPointerIndex(MotionEvent event, int pointerId) {
/* 26 */            return event.findPointerIndex(pointerId);
                }

/* 28 */        public static int getPointerId(MotionEvent event, int pointerIndex) {
/* 29 */            return event.getPointerId(pointerIndex);
                }

/* 31 */        public static float getX(MotionEvent event, int pointerIndex) {
/* 32 */            return event.getX(pointerIndex);
                }

/* 34 */        public static float getY(MotionEvent event, int pointerIndex) {
/* 35 */            return event.getY(pointerIndex);
                }

/* 37 */        public static int getPointerCount(MotionEvent event) {
/* 38 */            return event.getPointerCount();
                }
            }
