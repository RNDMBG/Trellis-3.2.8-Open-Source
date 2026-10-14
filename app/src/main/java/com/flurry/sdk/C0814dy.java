            package com.flurry.sdk;

            import android.text.TextUtils;
            
            
            
    public class C0814dy {

                
                private static final String f752a = C0814dy.class.getSimpleName();

                
                private static String f753b;

                
                private static String f754c;

                
        public static void m813a(String str) {
            f753b = str;
                }

                
        public static synchronized String m812a() {
                    String str;
                    try {
                        if (!TextUtils.isEmpty(f753b)) {
                    str = f753b;
                        } else if (!TextUtils.isEmpty(f754c)) {
                    str = f754c;
                        } else {
                    f754c = m814b();
                    str = f754c;
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                    return str;
                }

                
        private static java.lang.String m814b() {
                    try {
                        android.content.Context context = com.flurry.sdk.C0801dl.m732a().m736b();
                        android.content.pm.PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
                        if (packageInfo.versionName != null) {
                            return packageInfo.versionName;
                        }
                        if (packageInfo.versionCode != 0) {
                            return java.lang.Integer.toString(packageInfo.versionCode);
                        }
                    } catch (java.lang.Throwable throwable) {
                        com.flurry.sdk.C0827el.m886a(6, com.flurry.sdk.C0814dy.f752a, "", throwable);
                    }
                    return "Unknown";
                }
            }
