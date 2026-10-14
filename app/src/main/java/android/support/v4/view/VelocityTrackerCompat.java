            package android.support.v4.view;

            import android.os.Build;
            import android.view.VelocityTracker;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 34 */    public class VelocityTrackerCompat {
                static final VelocityTrackerVersionImpl IMPL;

                interface VelocityTrackerVersionImpl {
                    float getXVelocity(VelocityTracker velocityTracker, int i);

                    float getYVelocity(VelocityTracker velocityTracker, int i);
                }

/* 35 */        static class BaseVelocityTrackerVersionImpl implements VelocityTrackerVersionImpl {
/* 36 */            BaseVelocityTrackerVersionImpl() {
                    }

                    @Override // android.support.v4.view.VelocityTrackerCompat.VelocityTrackerVersionImpl
/* 39 */            public float getXVelocity(VelocityTracker tracker, int pointerId) {
/* 40 */                return tracker.getXVelocity();
                    }

                    @Override // android.support.v4.view.VelocityTrackerCompat.VelocityTrackerVersionImpl
/* 43 */            public float getYVelocity(VelocityTracker tracker, int pointerId) {
/* 44 */                return tracker.getYVelocity();
                    }
                }

/* 49 */        static class HoneycombVelocityTrackerVersionImpl implements VelocityTrackerVersionImpl {
/* 50 */            HoneycombVelocityTrackerVersionImpl() {
                    }

                    @Override // android.support.v4.view.VelocityTrackerCompat.VelocityTrackerVersionImpl
/* 53 */            public float getXVelocity(VelocityTracker tracker, int pointerId) {
/* 54 */                return VelocityTrackerCompatHoneycomb.getXVelocity(tracker, pointerId);
                    }

                    @Override // android.support.v4.view.VelocityTrackerCompat.VelocityTrackerVersionImpl
/* 57 */            public float getYVelocity(VelocityTracker tracker, int pointerId) {
/* 58 */                return VelocityTrackerCompatHoneycomb.getYVelocity(tracker, pointerId);
                    }
                }

                static {
/* 67 */            if (Build.VERSION.SDK_INT >= 11) {
/* 68 */                IMPL = new HoneycombVelocityTrackerVersionImpl();
                    } else {
/* 70 */                IMPL = new BaseVelocityTrackerVersionImpl();
                    }
                }

/* 81 */        public static float getXVelocity(VelocityTracker tracker, int pointerId) {
/* 82 */            return IMPL.getXVelocity(tracker, pointerId);
                }

/* 90 */        public static float getYVelocity(VelocityTracker tracker, int pointerId) {
/* 91 */            return IMPL.getYVelocity(tracker, pointerId);
                }
            }
