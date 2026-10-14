            package com.google.android.gms.internal;

            import android.content.Intent;
            import android.net.Uri;
            import com.google.android.gms.common.GooglePlayServicesUtil;
            
            
            
            public class C1022dg {

                
                private static final Uri f1711lg = Uri.parse("http://plus.google.com/");

                
                private static final Uri f1712lh = f1711lg.buildUpon().appendPath("circles").appendPath("find").build();

                
                private static Uri m1569A(String str) {
                    return Uri.parse("market://details").buildUpon().appendQueryParameter("id", str).build();
                }

                
                public static Intent m1570B(String str) {
                    Intent intent = new Intent("android.intent.action.VIEW");
                    intent.setData(m1569A(str));
                    intent.setPackage(GooglePlayServicesUtil.GOOGLE_PLAY_STORE_PACKAGE);
                    intent.addFlags(524288);
                    return intent;
                }

                
                public static Intent m1571C(String str) {
                    Uri uri = Uri.parse("bazaar://search?q=pname:" + str);
                    Intent intent = new Intent("android.intent.action.VIEW");
                    intent.setData(uri);
                    intent.setFlags(524288);
                    return intent;
                }

                
                public static Intent m1572bj() {
                    return new Intent("android.settings.DATE_SETTINGS");
                }

                
                public static Intent m1573z(String str) {
                    Uri uriFromParts = Uri.fromParts("package", str, null);
                    Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
                    intent.setData(uriFromParts);
                    return intent;
                }
            }
