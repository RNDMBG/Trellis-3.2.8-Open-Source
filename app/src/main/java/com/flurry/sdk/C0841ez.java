            package com.flurry.sdk;

            import android.annotation.SuppressLint;
            import android.graphics.Point;
            import android.os.Build;
            import android.util.DisplayMetrics;
            import android.util.Pair;
            import android.view.Display;
            import android.view.WindowManager;
            import java.lang.reflect.Method;
            
            /* JADX INFO: renamed from: com.flurry.sdk.ez */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 28 */    public class C0841ez {
                @SuppressLint({"NewApi"})
                /* JADX INFO: renamed from: a */
/* 29 */        public static Point m940a() {
/* 32 */            Display defaultDisplay = ((WindowManager) C0801dl.m732a().m736b().getSystemService("window")).getDefaultDisplay();
/* 35 */            Point point = new Point();
/* 36 */            if (Build.VERSION.SDK_INT >= 17) {
/* 37 */                defaultDisplay.getRealSize(point);
                    } else if (Build.VERSION.SDK_INT >= 14) {
                        try {
/* 40 */                    Method method = Display.class.getMethod("getRawHeight", new Class[0]);
/* 42 */                    point.x = ((Integer) Display.class.getMethod("getRawWidth", new Class[0]).invoke(defaultDisplay, new Object[0])).intValue();
/* 43 */                    point.y = ((Integer) method.invoke(defaultDisplay, new Object[0])).intValue();
                        } catch (Throwable th) {
/* 45 */                    defaultDisplay.getSize(point);
                        }
                    } else if (Build.VERSION.SDK_INT >= 13) {
/* 48 */                defaultDisplay.getSize(point);
                    } else {
/* 50 */                point.x = defaultDisplay.getWidth();
/* 51 */                point.y = defaultDisplay.getHeight();
                    }
/* 53 */            return point;
                }

                /* JADX INFO: renamed from: b */
/* 57 */        public static DisplayMetrics m942b() {
/* 60 */            Display defaultDisplay = ((WindowManager) C0801dl.m732a().m736b().getSystemService("window")).getDefaultDisplay();
/* 62 */            DisplayMetrics displayMetrics = new DisplayMetrics();
/* 63 */            defaultDisplay.getMetrics(displayMetrics);
/* 65 */            return displayMetrics;
                }

                @SuppressLint({"NewApi"})
                /* JADX INFO: renamed from: c */
/* 69 */        public static DisplayMetrics m943c() {
/* 72 */            Display defaultDisplay = ((WindowManager) C0801dl.m732a().m736b().getSystemService("window")).getDefaultDisplay();
/* 75 */            if (Build.VERSION.SDK_INT >= 17) {
/* 76 */                DisplayMetrics displayMetrics = new DisplayMetrics();
/* 77 */                defaultDisplay.getRealMetrics(displayMetrics);
/* 90 */                return displayMetrics;
                    }
/* 78 */            if (Build.VERSION.SDK_INT >= 14) {
                        try {
/* 80 */                    DisplayMetrics displayMetrics2 = new DisplayMetrics();
/* 82 */                    Display.class.getMethod("getRealMetrics", new Class[0]).invoke(defaultDisplay, displayMetrics2);
/* 90 */                    return displayMetrics2;
                        } catch (Exception e) {
/* 84 */                    return m942b();
                        }
                    }
/* 87 */            return m942b();
                }

                /* JADX INFO: renamed from: d */
/* 96 */        public static float m945d() {
/* 98 */            return m943c().density;
                }

                /* JADX INFO: renamed from: a */
/* 104 */       public static int m939a(int i) {
/* 105 */           return Math.round(i / m943c().density);
                }

                /* JADX INFO: renamed from: b */
/* 112 */       public static int m941b(int i) {
/* 114 */           return Math.round(m943c().density * i);
                }

                /* JADX INFO: renamed from: e */
/* 117 */       public static int m946e() {
/* 118 */           return m940a().x;
                }

                /* JADX INFO: renamed from: f */
/* 121 */       public static int m947f() {
/* 122 */           return m940a().y;
                }

                /* JADX INFO: renamed from: g */
/* 136 */       public static int m948g() {
/* 137 */           return m939a(m946e());
                }

                /* JADX INFO: renamed from: h */
/* 140 */       public static int m949h() {
/* 141 */           return m939a(m947f());
                }

                /* JADX INFO: renamed from: i */
/* 145 */       public static int m950i() {
/* 146 */           Point pointM940a = m940a();
/* 148 */           if (pointM940a.x == pointM940a.y) {
/* 149 */               return 3;
                    }
/* 151 */           if (pointM940a.x < pointM940a.y) {
/* 152 */               return 1;
                    }
/* 154 */           return 2;
                }

                /* JADX INFO: renamed from: j */
/* 163 */       public static Pair<Integer, Integer> m951j() {
/* 164 */           return Pair.create(Integer.valueOf(m948g()), Integer.valueOf(m949h()));
                }

                /* JADX INFO: renamed from: c */
/* 171 */       public static Pair<Integer, Integer> m944c(int i) {
/* 172 */           int iM948g = m948g();
/* 173 */           int iM949h = m949h();
                    switch (i) {
                        case 2:
/* 176 */                   return Pair.create(Integer.valueOf(iM949h), Integer.valueOf(iM948g));
                        default:
/* 179 */                   return Pair.create(Integer.valueOf(iM948g), Integer.valueOf(iM949h));
                    }
                }
            }
