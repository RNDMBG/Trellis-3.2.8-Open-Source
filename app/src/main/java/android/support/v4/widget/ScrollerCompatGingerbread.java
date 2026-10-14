            package android.support.v4.widget;

            import android.content.Context;
            import android.view.animation.Interpolator;
            import android.widget.OverScroller;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 22 */    class ScrollerCompatGingerbread {
/* 23 */        ScrollerCompatGingerbread() {
                }

/* 25 */        public static Object createScroller(Context context, Interpolator interpolator) {
                    return interpolator != null ? new OverScroller(context, interpolator) : new OverScroller(context);
                }

/* 30 */        public static boolean isFinished(Object scroller) {
/* 31 */            return ((OverScroller) scroller).isFinished();
                }

/* 34 */        public static int getCurrX(Object scroller) {
/* 35 */            return ((OverScroller) scroller).getCurrX();
                }

/* 38 */        public static int getCurrY(Object scroller) {
/* 39 */            return ((OverScroller) scroller).getCurrY();
                }

/* 42 */        public static boolean computeScrollOffset(Object scroller) {
/* 43 */            return ((OverScroller) scroller).computeScrollOffset();
                }

/* 46 */        public static void startScroll(Object scroller, int startX, int startY, int dx, int dy) {
/* 47 */            ((OverScroller) scroller).startScroll(startX, startY, dx, dy);
                }

/* 51 */        public static void startScroll(Object scroller, int startX, int startY, int dx, int dy, int duration) {
/* 52 */            ((OverScroller) scroller).startScroll(startX, startY, dx, dy, duration);
                }

/* 56 */        public static void fling(Object scroller, int startX, int startY, int velX, int velY, int minX, int maxX, int minY, int maxY) {
/* 57 */            ((OverScroller) scroller).fling(startX, startY, velX, velY, minX, maxX, minY, maxY);
                }

/* 61 */        public static void fling(Object scroller, int startX, int startY, int velX, int velY, int minX, int maxX, int minY, int maxY, int overX, int overY) {
/* 62 */            ((OverScroller) scroller).fling(startX, startY, velX, velY, minX, maxX, minY, maxY, overX, overY);
                }

/* 66 */        public static void abortAnimation(Object scroller) {
/* 67 */            ((OverScroller) scroller).abortAnimation();
                }

/* 71 */        public static void notifyHorizontalEdgeReached(Object scroller, int startX, int finalX, int overX) {
/* 72 */            ((OverScroller) scroller).notifyHorizontalEdgeReached(startX, finalX, overX);
                }

/* 75 */        public static void notifyVerticalEdgeReached(Object scroller, int startY, int finalY, int overY) {
/* 76 */            ((OverScroller) scroller).notifyVerticalEdgeReached(startY, finalY, overY);
                }

/* 79 */        public static boolean isOverScrolled(Object scroller) {
/* 80 */            return ((OverScroller) scroller).isOverScrolled();
                }

/* 83 */        public static int getFinalX(Object scroller) {
/* 84 */            return ((OverScroller) scroller).getFinalX();
                }

/* 87 */        public static int getFinalY(Object scroller) {
/* 88 */            return ((OverScroller) scroller).getFinalY();
                }
            }
