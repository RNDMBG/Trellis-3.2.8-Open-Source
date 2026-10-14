            package com.flurry.sdk;

            import android.text.TextUtils;
            import android.util.Log;
            
            
            
    public final class C0827el {

                
                private static int f816a = 4000;

                
                private static boolean f817b = false;

                
                private static int f818c = 5;

                
                private static boolean f819d = false;

                
        public static void m883a() {
            f817b = true;
                }

                
        public static void m890b() {
            f817b = false;
                }

                
        public static int m895c() {
            return f818c;
                }

                
        public static boolean m901d() {
            return f819d;
                }

                
        public static void m884a(int i) {
            f818c = i;
                }

                
        public static void m889a(boolean z) {
            f819d = z;
                }

                
        public static void m887a(String str, String str2) {
            m891b(3, str, str2);
                }

                
        public static void m888a(String str, String str2, Throwable th) {
            m892b(6, str, str2, th);
                }

                
        public static void m893b(String str, String str2) {
            m891b(6, str, str2);
                }

                
       public static void m898c(String str, String str2) {
           m891b(2, str, str2);
                }

                
       public static void m894b(String str, String str2, Throwable th) {
           m892b(5, str, str2, th);
                }

                
       public static void m900d(String str, String str2) {
           m891b(5, str, str2);
                }

                
       public static void m886a(int i, String str, String str2, Throwable th) {
           m897c(i, str, str2, th);
                }

                
       public static void m885a(int i, String str, String str2) {
           m896c(i, str, str2);
                }

                
       private static void m892b(int i, String str, String str2, Throwable th) {
           m891b(i, str, str2 + '\n' + Log.getStackTraceString(th));
                }

                
       private static void m891b(int i, String str, String str2) {
           if (!f817b && f818c <= i) {
               m899d(i, str, str2);
                    }
                }

                
       private static void m897c(int i, String str, String str2, Throwable th) {
           m896c(i, str, str2 + '\n' + Log.getStackTraceString(th));
                }

                
       private static void m896c(int i, String str, String str2) {
           if (f819d) {
               m899d(i, str, str2);
                    }
                }

                
       private static void m899d(int i, String str, String str2) {
           if (!f819d) {
               str = "FlurryAgent";
                    }
           int length = TextUtils.isEmpty(str2) ? 0 : str2.length();
                    int i2 = 0;
           while (i2 < length) {
               int i3 = f816a > length - i2 ? length : f816a + i2;
               if (Log.println(i, str, str2.substring(i2, i3)) <= 0) {
                   return;
                        } else {
                            i2 = i3;
                        }
                    }
                }
            }
