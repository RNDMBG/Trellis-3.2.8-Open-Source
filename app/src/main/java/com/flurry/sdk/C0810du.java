            package com.flurry.sdk;

            import android.content.Context;
            import android.content.pm.ApplicationInfo;
            import android.content.pm.PackageInfo;
            import android.content.pm.PackageManager;
            import android.os.Bundle;
            
            /* JADX INFO: renamed from: com.flurry.sdk.du */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 27 */    public final class C0810du {

                /* JADX INFO: renamed from: a */
                private static final String f730a = C0810du.class.getSimpleName();

                /* JADX INFO: renamed from: a */
/* 28 */        public static PackageInfo m773a(Context context) {
/* 30 */            if (context == null) {
/* 29 */                return null;
                    }
                    try {
/* 32 */                return context.getPackageManager().getPackageInfo(context.getPackageName(), 20815);
                    } catch (PackageManager.NameNotFoundException e) {
/* 41 */                C0827el.m887a(f730a, "Cannot find package info for package: " + context.getPackageName());
/* 29 */                return null;
                    }
                }

                /* JADX INFO: renamed from: b */
/* 48 */        public static ApplicationInfo m774b(Context context) {
/* 50 */            if (context == null) {
/* 49 */                return null;
                    }
                    try {
/* 52 */                return context.getPackageManager().getApplicationInfo(context.getPackageName(), 128);
                    } catch (PackageManager.NameNotFoundException e) {
/* 55 */                C0827el.m887a(f730a, "Cannot find application info for package: " + context.getPackageName());
/* 49 */                return null;
                    }
                }

                /* JADX INFO: renamed from: c */
/* 63 */        public static String m775c(Context context) {
/* 64 */            PackageInfo packageInfoM773a = m773a(context);
                    return (packageInfoM773a == null || packageInfoM773a.packageName == null) ? "" : packageInfoM773a.packageName;
                }

                /* JADX INFO: renamed from: d */
/* 75 */        public static Bundle m776d(Context context) {
/* 76 */            ApplicationInfo applicationInfoM774b = m774b(context);
                    return (applicationInfoM774b == null || applicationInfoM774b.metaData == null) ? Bundle.EMPTY : applicationInfoM774b.metaData;
                }
            }
