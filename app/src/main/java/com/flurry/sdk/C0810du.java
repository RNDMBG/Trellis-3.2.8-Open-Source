            package com.flurry.sdk;

            import android.content.Context;
            import android.content.pm.ApplicationInfo;
            import android.content.pm.PackageInfo;
            import android.content.pm.PackageManager;
            import android.os.Bundle;
            
            
            
    public final class C0810du {

                
                private static final String f730a = C0810du.class.getSimpleName();

                
        public static PackageInfo m773a(Context context) {
            if (context == null) {
                return null;
                    }
                    try {
                return context.getPackageManager().getPackageInfo(context.getPackageName(), 20815);
                    } catch (PackageManager.NameNotFoundException e) {
                C0827el.m887a(f730a, "Cannot find package info for package: " + context.getPackageName());
                return null;
                    }
                }

                
        public static ApplicationInfo m774b(Context context) {
            if (context == null) {
                return null;
                    }
                    try {
                return context.getPackageManager().getApplicationInfo(context.getPackageName(), 128);
                    } catch (PackageManager.NameNotFoundException e) {
                C0827el.m887a(f730a, "Cannot find application info for package: " + context.getPackageName());
                return null;
                    }
                }

                
        public static String m775c(Context context) {
            PackageInfo packageInfoM773a = m773a(context);
                    return (packageInfoM773a == null || packageInfoM773a.packageName == null) ? "" : packageInfoM773a.packageName;
                }

                
        public static Bundle m776d(Context context) {
            ApplicationInfo applicationInfoM774b = m774b(context);
                    return (applicationInfoM774b == null || applicationInfoM774b.metaData == null) ? Bundle.EMPTY : applicationInfoM774b.metaData;
                }
            }
