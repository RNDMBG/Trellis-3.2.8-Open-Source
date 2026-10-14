            package android.support.v4.view;

            import android.graphics.Rect;
            import android.os.Build;
            import android.view.Gravity;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 33 */    public class GravityCompat {
                public static final int END = 8388613;
                static final GravityCompatImpl IMPL;
                public static final int RELATIVE_HORIZONTAL_GRAVITY_MASK = 8388615;
                public static final int RELATIVE_LAYOUT_DIRECTION = 8388608;
                public static final int START = 8388611;

                interface GravityCompatImpl {
                    void apply(int i, int i2, int i3, Rect rect, int i4, int i5, Rect rect2, int i6);

                    void apply(int i, int i2, int i3, Rect rect, Rect rect2, int i4);

                    void applyDisplay(int i, Rect rect, Rect rect2, int i2);

                    int getAbsoluteGravity(int i, int i2);
                }

/* 34 */        static class GravityCompatImplBase implements GravityCompatImpl {
/* 35 */            GravityCompatImplBase() {
                    }

                    @Override // android.support.v4.view.GravityCompat.GravityCompatImpl
/* 39 */            public int getAbsoluteGravity(int gravity, int layoutDirection) {
/* 40 */                return (-8388609) & gravity;
                    }

                    @Override // android.support.v4.view.GravityCompat.GravityCompatImpl
/* 45 */            public void apply(int gravity, int w, int h, Rect container, Rect outRect, int layoutDirection) {
/* 46 */                Gravity.apply(gravity, w, h, container, outRect);
                    }

                    @Override // android.support.v4.view.GravityCompat.GravityCompatImpl
/* 51 */            public void apply(int gravity, int w, int h, Rect container, int xAdj, int yAdj, Rect outRect, int layoutDirection) {
/* 52 */                Gravity.apply(gravity, w, h, container, xAdj, yAdj, outRect);
                    }

                    @Override // android.support.v4.view.GravityCompat.GravityCompatImpl
/* 57 */            public void applyDisplay(int gravity, Rect display, Rect inoutObj, int layoutDirection) {
/* 58 */                Gravity.applyDisplay(gravity, display, inoutObj);
                    }
                }

/* 60 */        static class GravityCompatImplJellybeanMr1 implements GravityCompatImpl {
/* 61 */            GravityCompatImplJellybeanMr1() {
                    }

                    @Override // android.support.v4.view.GravityCompat.GravityCompatImpl
/* 64 */            public int getAbsoluteGravity(int gravity, int layoutDirection) {
/* 65 */                return GravityCompatJellybeanMr1.getAbsoluteGravity(gravity, layoutDirection);
                    }

                    @Override // android.support.v4.view.GravityCompat.GravityCompatImpl
/* 70 */            public void apply(int gravity, int w, int h, Rect container, Rect outRect, int layoutDirection) {
/* 71 */                GravityCompatJellybeanMr1.apply(gravity, w, h, container, outRect, layoutDirection);
                    }

                    @Override // android.support.v4.view.GravityCompat.GravityCompatImpl
/* 76 */            public void apply(int gravity, int w, int h, Rect container, int xAdj, int yAdj, Rect outRect, int layoutDirection) {
/* 77 */                GravityCompatJellybeanMr1.apply(gravity, w, h, container, xAdj, yAdj, outRect, layoutDirection);
                    }

                    @Override // android.support.v4.view.GravityCompat.GravityCompatImpl
/* 82 */            public void applyDisplay(int gravity, Rect display, Rect inoutObj, int layoutDirection) {
/* 83 */                GravityCompatJellybeanMr1.applyDisplay(gravity, display, inoutObj, layoutDirection);
                    }
                }

                static {
/* 89 */            int version = Build.VERSION.SDK_INT;
/* 90 */            if (version >= 17) {
/* 91 */                IMPL = new GravityCompatImplJellybeanMr1();
                    } else {
/* 93 */                IMPL = new GravityCompatImplBase();
                    }
                }

/* 131 */       public static void apply(int gravity, int w, int h, Rect container, Rect outRect, int layoutDirection) {
/* 132 */           IMPL.apply(gravity, w, h, container, outRect, layoutDirection);
                }

/* 161 */       public static void apply(int gravity, int w, int h, Rect container, int xAdj, int yAdj, Rect outRect, int layoutDirection) {
/* 162 */           IMPL.apply(gravity, w, h, container, xAdj, yAdj, outRect, layoutDirection);
                }

/* 185 */       public static void applyDisplay(int gravity, Rect display, Rect inoutObj, int layoutDirection) {
/* 186 */           IMPL.applyDisplay(gravity, display, inoutObj, layoutDirection);
                }

/* 200 */       public static int getAbsoluteGravity(int gravity, int layoutDirection) {
/* 201 */           return IMPL.getAbsoluteGravity(gravity, layoutDirection);
                }
            }
