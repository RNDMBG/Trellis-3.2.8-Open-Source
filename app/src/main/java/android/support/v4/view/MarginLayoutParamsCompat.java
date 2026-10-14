            package android.support.v4.view;

            import android.os.Build;
            import android.view.ViewGroup;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 36 */    public class MarginLayoutParamsCompat {
                static final MarginLayoutParamsCompatImpl IMPL;

                interface MarginLayoutParamsCompatImpl {
                    int getLayoutDirection(ViewGroup.MarginLayoutParams marginLayoutParams);

                    int getMarginEnd(ViewGroup.MarginLayoutParams marginLayoutParams);

                    int getMarginStart(ViewGroup.MarginLayoutParams marginLayoutParams);

                    boolean isMarginRelative(ViewGroup.MarginLayoutParams marginLayoutParams);

                    void resolveLayoutDirection(ViewGroup.MarginLayoutParams marginLayoutParams, int i);

                    void setLayoutDirection(ViewGroup.MarginLayoutParams marginLayoutParams, int i);

                    void setMarginEnd(ViewGroup.MarginLayoutParams marginLayoutParams, int i);

                    void setMarginStart(ViewGroup.MarginLayoutParams marginLayoutParams, int i);
                }

/* 37 */        static class MarginLayoutParamsCompatImplBase implements MarginLayoutParamsCompatImpl {
/* 38 */            MarginLayoutParamsCompatImplBase() {
                    }

                    @Override // android.support.v4.view.MarginLayoutParamsCompat.MarginLayoutParamsCompatImpl
/* 42 */            public int getMarginStart(ViewGroup.MarginLayoutParams lp) {
/* 43 */                return lp.leftMargin;
                    }

                    @Override // android.support.v4.view.MarginLayoutParamsCompat.MarginLayoutParamsCompatImpl
/* 47 */            public int getMarginEnd(ViewGroup.MarginLayoutParams lp) {
/* 48 */                return lp.rightMargin;
                    }

                    @Override // android.support.v4.view.MarginLayoutParamsCompat.MarginLayoutParamsCompatImpl
/* 52 */            public void setMarginStart(ViewGroup.MarginLayoutParams lp, int marginStart) {
/* 53 */                lp.leftMargin = marginStart;
                    }

                    @Override // android.support.v4.view.MarginLayoutParamsCompat.MarginLayoutParamsCompatImpl
/* 57 */            public void setMarginEnd(ViewGroup.MarginLayoutParams lp, int marginEnd) {
/* 58 */                lp.rightMargin = marginEnd;
                    }

                    @Override // android.support.v4.view.MarginLayoutParamsCompat.MarginLayoutParamsCompatImpl
/* 62 */            public boolean isMarginRelative(ViewGroup.MarginLayoutParams lp) {
/* 63 */                return false;
                    }

                    @Override // android.support.v4.view.MarginLayoutParamsCompat.MarginLayoutParamsCompatImpl
/* 67 */            public int getLayoutDirection(ViewGroup.MarginLayoutParams lp) {
/* 68 */                return 0;
                    }

                    @Override // android.support.v4.view.MarginLayoutParamsCompat.MarginLayoutParamsCompatImpl
/* 73 */            public void setLayoutDirection(ViewGroup.MarginLayoutParams lp, int layoutDirection) {
                    }

                    @Override // android.support.v4.view.MarginLayoutParamsCompat.MarginLayoutParamsCompatImpl
/* 78 */            public void resolveLayoutDirection(ViewGroup.MarginLayoutParams lp, int layoutDirection) {
                    }
                }

/* 80 */        static class MarginLayoutParamsCompatImplJbMr1 implements MarginLayoutParamsCompatImpl {
/* 81 */            MarginLayoutParamsCompatImplJbMr1() {
                    }

                    @Override // android.support.v4.view.MarginLayoutParamsCompat.MarginLayoutParamsCompatImpl
/* 85 */            public int getMarginStart(ViewGroup.MarginLayoutParams lp) {
/* 86 */                return MarginLayoutParamsCompatJellybeanMr1.getMarginStart(lp);
                    }

                    @Override // android.support.v4.view.MarginLayoutParamsCompat.MarginLayoutParamsCompatImpl
/* 90 */            public int getMarginEnd(ViewGroup.MarginLayoutParams lp) {
/* 91 */                return MarginLayoutParamsCompatJellybeanMr1.getMarginEnd(lp);
                    }

                    @Override // android.support.v4.view.MarginLayoutParamsCompat.MarginLayoutParamsCompatImpl
/* 95 */            public void setMarginStart(ViewGroup.MarginLayoutParams lp, int marginStart) {
/* 96 */                MarginLayoutParamsCompatJellybeanMr1.setMarginStart(lp, marginStart);
                    }

                    @Override // android.support.v4.view.MarginLayoutParamsCompat.MarginLayoutParamsCompatImpl
/* 100 */           public void setMarginEnd(ViewGroup.MarginLayoutParams lp, int marginEnd) {
/* 101 */               MarginLayoutParamsCompatJellybeanMr1.setMarginEnd(lp, marginEnd);
                    }

                    @Override // android.support.v4.view.MarginLayoutParamsCompat.MarginLayoutParamsCompatImpl
/* 105 */           public boolean isMarginRelative(ViewGroup.MarginLayoutParams lp) {
/* 106 */               return MarginLayoutParamsCompatJellybeanMr1.isMarginRelative(lp);
                    }

                    @Override // android.support.v4.view.MarginLayoutParamsCompat.MarginLayoutParamsCompatImpl
/* 110 */           public int getLayoutDirection(ViewGroup.MarginLayoutParams lp) {
/* 111 */               return MarginLayoutParamsCompatJellybeanMr1.getLayoutDirection(lp);
                    }

                    @Override // android.support.v4.view.MarginLayoutParamsCompat.MarginLayoutParamsCompatImpl
/* 115 */           public void setLayoutDirection(ViewGroup.MarginLayoutParams lp, int layoutDirection) {
/* 116 */               MarginLayoutParamsCompatJellybeanMr1.setLayoutDirection(lp, layoutDirection);
                    }

                    @Override // android.support.v4.view.MarginLayoutParamsCompat.MarginLayoutParamsCompatImpl
/* 120 */           public void resolveLayoutDirection(ViewGroup.MarginLayoutParams lp, int layoutDirection) {
/* 121 */               MarginLayoutParamsCompatJellybeanMr1.resolveLayoutDirection(lp, layoutDirection);
                    }
                }

                static {
/* 127 */           int version = Build.VERSION.SDK_INT;
/* 128 */           if (version >= 17) {
/* 129 */               IMPL = new MarginLayoutParamsCompatImplJbMr1();
                    } else {
/* 131 */               IMPL = new MarginLayoutParamsCompatImplBase();
                    }
                }

/* 146 */       public static int getMarginStart(ViewGroup.MarginLayoutParams lp) {
/* 147 */           return IMPL.getMarginStart(lp);
                }

/* 161 */       public static int getMarginEnd(ViewGroup.MarginLayoutParams lp) {
/* 162 */           return IMPL.getMarginEnd(lp);
                }

/* 176 */       public static void setMarginStart(ViewGroup.MarginLayoutParams lp, int marginStart) {
/* 177 */           IMPL.setMarginStart(lp, marginStart);
                }

/* 191 */       public static void setMarginEnd(ViewGroup.MarginLayoutParams lp, int marginEnd) {
/* 192 */           IMPL.setMarginEnd(lp, marginEnd);
                }

/* 200 */       public static boolean isMarginRelative(ViewGroup.MarginLayoutParams lp) {
/* 201 */           return IMPL.isMarginRelative(lp);
                }

/* 210 */       public static int getLayoutDirection(ViewGroup.MarginLayoutParams lp) {
/* 211 */           return IMPL.getLayoutDirection(lp);
                }

/* 221 */       public static void setLayoutDirection(ViewGroup.MarginLayoutParams lp, int layoutDirection) {
/* 222 */           IMPL.setLayoutDirection(lp, layoutDirection);
                }

/* 230 */       public static void resolveLayoutDirection(ViewGroup.MarginLayoutParams lp, int layoutDirection) {
/* 231 */           IMPL.resolveLayoutDirection(lp, layoutDirection);
                }
            }
