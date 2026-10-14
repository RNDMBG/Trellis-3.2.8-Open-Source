            package com.flurry.sdk;

            import android.annotation.SuppressLint;
            import android.graphics.Point;
            import android.os.Build;
            import android.util.DisplayMetrics;
            import android.util.Pair;
            import android.view.Display;
            import android.view.WindowManager;
            import java.lang.reflect.Method;
            
            
            
    public class C0841ez {
                @SuppressLint({"NewApi"})
                
        public static Point m940a() {
            Display defaultDisplay = ((WindowManager) C0801dl.m732a().m736b().getSystemService("window")).getDefaultDisplay();
            Point point = new Point();
            if (Build.VERSION.SDK_INT >= 17) {
                defaultDisplay.getRealSize(point);
                    } else if (Build.VERSION.SDK_INT >= 14) {
                        try {
                    Method method = Display.class.getMethod("getRawHeight", new Class[0]);
                    point.x = ((Integer) Display.class.getMethod("getRawWidth", new Class[0]).invoke(defaultDisplay, new Object[0])).intValue();
                    point.y = ((Integer) method.invoke(defaultDisplay, new Object[0])).intValue();
                        } catch (Throwable th) {
                    defaultDisplay.getSize(point);
                        }
                    } else if (Build.VERSION.SDK_INT >= 13) {
                defaultDisplay.getSize(point);
                    } else {
                point.x = defaultDisplay.getWidth();
                point.y = defaultDisplay.getHeight();
                    }
            return point;
                }

                
        public static DisplayMetrics m942b() {
            Display defaultDisplay = ((WindowManager) C0801dl.m732a().m736b().getSystemService("window")).getDefaultDisplay();
            DisplayMetrics displayMetrics = new DisplayMetrics();
            defaultDisplay.getMetrics(displayMetrics);
            return displayMetrics;
                }

                @SuppressLint({"NewApi"})
                
        public static DisplayMetrics m943c() {
            Display defaultDisplay = ((WindowManager) C0801dl.m732a().m736b().getSystemService("window")).getDefaultDisplay();
            if (Build.VERSION.SDK_INT >= 17) {
                DisplayMetrics displayMetrics = new DisplayMetrics();
                defaultDisplay.getRealMetrics(displayMetrics);
                return displayMetrics;
                    }
            if (Build.VERSION.SDK_INT >= 14) {
                        try {
                    DisplayMetrics displayMetrics2 = new DisplayMetrics();
                    Display.class.getMethod("getRealMetrics", new Class[0]).invoke(defaultDisplay, displayMetrics2);
                    return displayMetrics2;
                        } catch (Exception e) {
                    return m942b();
                        }
                    }
            return m942b();
                }

                
        public static float m945d() {
            return m943c().density;
                }

                
       public static int m939a(int i) {
           return Math.round(i / m943c().density);
                }

                
       public static int m941b(int i) {
           return Math.round(m943c().density * i);
                }

                
       public static int m946e() {
           return m940a().x;
                }

                
       public static int m947f() {
           return m940a().y;
                }

                
       public static int m948g() {
           return m939a(m946e());
                }

                
       public static int m949h() {
           return m939a(m947f());
                }

                
       public static int m950i() {
           Point pointM940a = m940a();
           if (pointM940a.x == pointM940a.y) {
               return 3;
                    }
           if (pointM940a.x < pointM940a.y) {
               return 1;
                    }
           return 2;
                }

                
       public static Pair<Integer, Integer> m951j() {
           return Pair.create(Integer.valueOf(m948g()), Integer.valueOf(m949h()));
                }

                
       public static Pair<Integer, Integer> m944c(int i) {
           int iM948g = m948g();
           int iM949h = m949h();
                    switch (i) {
                        case 2:
                   return Pair.create(Integer.valueOf(iM949h), Integer.valueOf(iM948g));
                        default:
                   return Pair.create(Integer.valueOf(iM948g), Integer.valueOf(iM949h));
                    }
                }
            }
