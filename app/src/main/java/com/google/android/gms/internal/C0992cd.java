            package com.google.android.gms.internal;

            import android.content.Context;
            import android.content.Intent;
            import android.content.pm.ActivityInfo;
            import android.content.pm.PackageInfo;
            import android.content.pm.PackageManager;
            import android.content.pm.ResolveInfo;
            import android.media.AudioManager;
            import android.net.ConnectivityManager;
            import android.net.NetworkInfo;
            import android.net.Uri;
            import android.telephony.TelephonyManager;
            import android.util.DisplayMetrics;
            import co.vine.android.service.VineService;
            import com.google.android.exoplayer.util.MimeTypes;
            import java.util.Locale;
            
            
            
            public final class C0992cd {

                
                public final int f1544hh;

                
                public final boolean f1545hi;

                
                public final boolean f1546hj;

                
                public final String f1547hk;

                
                public final String f1548hl;

                
                public final boolean f1549hm;

                
                public final boolean f1550hn;

                
                public final boolean f1551ho;

                
                public final String f1552hp;

                
                public final String f1553hq;

                
                public final int f1554hr;

                
                public final int f1555hs;

                
                public final int f1556ht;

                
                public final int f1557hu;

                
                public final int f1558hv;

                
                public final int f1559hw;

                
                public final float f1560hx;

                
                public final int f1561hy;

                
                public final int f1562hz;

                public C0992cd(Context context) {
                    AudioManager audioManager = (AudioManager) context.getSystemService(MimeTypes.BASE_TYPE_AUDIO);
                    ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
                    DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
                    Locale locale = Locale.getDefault();
                    PackageManager packageManager = context.getPackageManager();
                    TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(VineService.EXTRA_PHONE);
                    this.f1544hh = audioManager.getMode();
                    this.f1545hi = m1406a(packageManager, "geo:0,0?q=donuts") != null;
                    this.f1546hj = m1406a(packageManager, "http://www.google.com") != null;
                    this.f1547hk = telephonyManager.getNetworkOperator();
                    this.f1548hl = locale.getCountry();
                    this.f1549hm = C1001cm.m1446aq();
                    this.f1550hn = audioManager.isMusicActive();
                    this.f1551ho = audioManager.isSpeakerphoneOn();
                    this.f1552hp = locale.getLanguage();
                    this.f1553hq = m1407a(packageManager);
                    this.f1554hr = audioManager.getStreamVolume(3);
                    this.f1555hs = m1405a(context, connectivityManager, packageManager);
                    this.f1556ht = telephonyManager.getNetworkType();
                    this.f1557hu = telephonyManager.getPhoneType();
                    this.f1558hv = audioManager.getRingerMode();
                    this.f1559hw = audioManager.getStreamVolume(2);
                    this.f1560hx = displayMetrics.density;
                    this.f1561hy = displayMetrics.widthPixels;
                    this.f1562hz = displayMetrics.heightPixels;
                }

                
                private static int m1405a(Context context, ConnectivityManager connectivityManager, PackageManager packageManager) {
                    if (!C0997ci.m1420a(packageManager, context.getPackageName(), "android.permission.ACCESS_NETWORK_STATE")) {
                        return -2;
                    }
                    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
                    if (activeNetworkInfo != null) {
                        return activeNetworkInfo.getType();
                    }
                    return -1;
                }

                
                private static ResolveInfo m1406a(PackageManager packageManager, String str) {
                    return packageManager.resolveActivity(new Intent("android.intent.action.VIEW", Uri.parse(str)), 65536);
                }

                
                private static String m1407a(PackageManager packageManager) {
                    ActivityInfo activityInfo;
                    ResolveInfo resolveInfoM1406a = m1406a(packageManager, "market://details?id=com.google.android.gms.ads");
                    if (resolveInfoM1406a == null || (activityInfo = resolveInfoM1406a.activityInfo) == null) {
                        return null;
                    }
                    try {
                        PackageInfo packageInfo = packageManager.getPackageInfo(activityInfo.packageName, 0);
                        if (packageInfo != null) {
                            return packageInfo.versionCode + "." + activityInfo.packageName;
                        }
                        return null;
                    } catch (PackageManager.NameNotFoundException e) {
                        return null;
                    }
                }
            }
