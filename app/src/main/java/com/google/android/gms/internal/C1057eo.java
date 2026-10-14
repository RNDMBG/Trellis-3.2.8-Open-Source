            package com.google.android.gms.internal;

            import android.content.Intent;
            import android.net.Uri;
            import com.google.android.gms.common.GooglePlayServicesUtil;
            
            
            
            public final class C1057eo {
                
                public static final Intent m1799c(Intent intent) {
                    intent.setData(Uri.fromParts("version", Integer.toString(GooglePlayServicesUtil.GOOGLE_PLAY_SERVICES_VERSION_CODE), null));
                    return intent;
                }
            }
