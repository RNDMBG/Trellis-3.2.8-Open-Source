            package android.support.v4.view;

            import android.view.VelocityTracker;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 22 */    class VelocityTrackerCompatHoneycomb {
/* 23 */        VelocityTrackerCompatHoneycomb() {
                }

/* 25 */        public static float getXVelocity(VelocityTracker tracker, int pointerId) {
/* 26 */            return tracker.getXVelocity(pointerId);
                }

/* 28 */        public static float getYVelocity(VelocityTracker tracker, int pointerId) {
/* 29 */            return tracker.getYVelocity(pointerId);
                }
            }
