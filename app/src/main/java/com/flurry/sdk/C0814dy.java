            package com.flurry.sdk;

            import android.text.TextUtils;
            
            /* JADX INFO: renamed from: com.flurry.sdk.dy */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 18 */    public class C0814dy {

                /* JADX INFO: renamed from: a */
                private static final String f752a = C0814dy.class.getSimpleName();

                /* JADX INFO: renamed from: b */
                private static String f753b;

                /* JADX INFO: renamed from: c */
                private static String f754c;

                /* JADX INFO: renamed from: a */
/* 19 */        public static void m813a(String str) {
/* 20 */            f753b = str;
                }

                /* JADX INFO: renamed from: a */
/* 32 */        public static synchronized String m812a() {
                    String str;
                    try {
                        if (!TextUtils.isEmpty(f753b)) {
/* 34 */                    str = f753b;
                        } else if (!TextUtils.isEmpty(f754c)) {
/* 38 */                    str = f754c;
                        } else {
/* 41 */                    f754c = m814b();
/* 42 */                    str = f754c;
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                    return str;
                }

                /* JADX INFO: renamed from: b */
/* 46 */        private static java.lang.String m814b() {
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
