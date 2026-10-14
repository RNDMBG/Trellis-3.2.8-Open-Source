            package android.support.v4.graphics.drawable;

            import android.graphics.drawable.Drawable;
            import android.os.Build;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 35 */    public class DrawableCompat {
                static final DrawableImpl IMPL;

                interface DrawableImpl {
                    boolean isAutoMirrored(Drawable drawable);

                    void jumpToCurrentState(Drawable drawable);

                    void setAutoMirrored(Drawable drawable, boolean z);
                }

/* 36 */        static class BaseDrawableImpl implements DrawableImpl {
/* 37 */            BaseDrawableImpl() {
                    }

                    @Override // android.support.v4.graphics.drawable.DrawableCompat.DrawableImpl
/* 40 */            public void jumpToCurrentState(Drawable drawable) {
                    }

                    @Override // android.support.v4.graphics.drawable.DrawableCompat.DrawableImpl
/* 44 */            public void setAutoMirrored(Drawable drawable, boolean mirrored) {
                    }

                    @Override // android.support.v4.graphics.drawable.DrawableCompat.DrawableImpl
/* 48 */            public boolean isAutoMirrored(Drawable drawable) {
/* 49 */                return false;
                    }
                }

/* 54 */        static class HoneycombDrawableImpl extends BaseDrawableImpl {
/* 55 */            HoneycombDrawableImpl() {
                    }

                    @Override // android.support.v4.graphics.drawable.DrawableCompat.BaseDrawableImpl, android.support.v4.graphics.drawable.DrawableCompat.DrawableImpl
/* 58 */            public void jumpToCurrentState(Drawable drawable) {
/* 59 */                DrawableCompatHoneycomb.jumpToCurrentState(drawable);
                    }
                }

/* 64 */        static class KitKatDrawableImpl extends HoneycombDrawableImpl {
/* 65 */            KitKatDrawableImpl() {
                    }

                    @Override // android.support.v4.graphics.drawable.DrawableCompat.BaseDrawableImpl, android.support.v4.graphics.drawable.DrawableCompat.DrawableImpl
/* 68 */            public void setAutoMirrored(Drawable drawable, boolean mirrored) {
/* 69 */                DrawableCompatKitKat.setAutoMirrored(drawable, mirrored);
                    }

                    @Override // android.support.v4.graphics.drawable.DrawableCompat.BaseDrawableImpl, android.support.v4.graphics.drawable.DrawableCompat.DrawableImpl
/* 73 */            public boolean isAutoMirrored(Drawable drawable) {
/* 74 */                return DrawableCompatKitKat.isAutoMirrored(drawable);
                    }
                }

                static {
/* 83 */            int version = Build.VERSION.SDK_INT;
/* 84 */            if (version >= 19) {
/* 85 */                IMPL = new KitKatDrawableImpl();
                    } else if (version >= 11) {
/* 87 */                IMPL = new HoneycombDrawableImpl();
                    } else {
/* 89 */                IMPL = new BaseDrawableImpl();
                    }
                }

/* 101 */       public static void jumpToCurrentState(Drawable drawable) {
/* 102 */           IMPL.jumpToCurrentState(drawable);
                }

/* 117 */       public static void setAutoMirrored(Drawable drawable, boolean mirrored) {
/* 118 */           IMPL.setAutoMirrored(drawable, mirrored);
                }

/* 132 */       public static boolean isAutoMirrored(Drawable drawable) {
/* 133 */           return IMPL.isAutoMirrored(drawable);
                }
            }
