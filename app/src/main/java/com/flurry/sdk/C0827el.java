            package com.flurry.sdk;

            import android.text.TextUtils;
            import android.util.Log;
            
            /* JADX INFO: renamed from: com.flurry.sdk.el */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 32 */    public final class C0827el {

                /* JADX INFO: renamed from: a */
                private static int f816a = 4000;

                /* JADX INFO: renamed from: b */
                private static boolean f817b = false;

                /* JADX INFO: renamed from: c */
                private static int f818c = 5;

                /* JADX INFO: renamed from: d */
                private static boolean f819d = false;

                /* JADX INFO: renamed from: a */
/* 33 */        public static void m883a() {
/* 34 */            f817b = true;
                }

                /* JADX INFO: renamed from: b */
/* 39 */        public static void m890b() {
/* 40 */            f817b = false;
                }

                /* JADX INFO: renamed from: c */
/* 47 */        public static int m895c() {
/* 48 */            return f818c;
                }

                /* JADX INFO: renamed from: d */
/* 51 */        public static boolean m901d() {
/* 52 */            return f819d;
                }

                /* JADX INFO: renamed from: a */
/* 62 */        public static void m884a(int i) {
/* 63 */            f818c = i;
                }

                /* JADX INFO: renamed from: a */
/* 66 */        public static void m889a(boolean z) {
/* 67 */            f819d = z;
                }

                /* JADX INFO: renamed from: a */
/* 78 */        public static void m887a(String str, String str2) {
/* 79 */            m891b(3, str, str2);
                }

                /* JADX INFO: renamed from: a */
/* 82 */        public static void m888a(String str, String str2, Throwable th) {
/* 83 */            m892b(6, str, str2, th);
                }

                /* JADX INFO: renamed from: b */
/* 86 */        public static void m893b(String str, String str2) {
/* 87 */            m891b(6, str, str2);
                }

                /* JADX INFO: renamed from: c */
/* 102 */       public static void m898c(String str, String str2) {
/* 103 */           m891b(2, str, str2);
                }

                /* JADX INFO: renamed from: b */
/* 106 */       public static void m894b(String str, String str2, Throwable th) {
/* 107 */           m892b(5, str, str2, th);
                }

                /* JADX INFO: renamed from: d */
/* 110 */       public static void m900d(String str, String str2) {
/* 111 */           m891b(5, str, str2);
                }

                /* JADX INFO: renamed from: a */
/* 114 */       public static void m886a(int i, String str, String str2, Throwable th) {
/* 115 */           m897c(i, str, str2, th);
                }

                /* JADX INFO: renamed from: a */
/* 118 */       public static void m885a(int i, String str, String str2) {
/* 119 */           m896c(i, str, str2);
                }

                /* JADX INFO: renamed from: b */
/* 122 */       private static void m892b(int i, String str, String str2, Throwable th) {
/* 123 */           m891b(i, str, str2 + '\n' + Log.getStackTraceString(th));
                }

                /* JADX INFO: renamed from: b */
/* 126 */       private static void m891b(int i, String str, String str2) {
/* 127 */           if (!f817b && f818c <= i) {
/* 128 */               m899d(i, str, str2);
                    }
                }

                /* JADX INFO: renamed from: c */
/* 132 */       private static void m897c(int i, String str, String str2, Throwable th) {
/* 133 */           m896c(i, str, str2 + '\n' + Log.getStackTraceString(th));
                }

                /* JADX INFO: renamed from: c */
/* 136 */       private static void m896c(int i, String str, String str2) {
/* 137 */           if (f819d) {
/* 138 */               m899d(i, str, str2);
                    }
                }

                /* JADX INFO: renamed from: d */
/* 142 */       private static void m899d(int i, String str, String str2) {
/* 143 */           if (!f819d) {
/* 143 */               str = "FlurryAgent";
                    }
/* 150 */           int length = TextUtils.isEmpty(str2) ? 0 : str2.length();
                    int i2 = 0;
/* 151 */           while (i2 < length) {
/* 152 */               int i3 = f816a > length - i2 ? length : f816a + i2;
/* 155 */               if (Log.println(i, str, str2.substring(i2, i3)) <= 0) {
/* 162 */                   return;
                        } else {
                            i2 = i3;
                        }
                    }
                }
            }
