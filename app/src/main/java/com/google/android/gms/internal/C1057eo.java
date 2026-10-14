            package com.google.android.gms.internal;

            import android.content.Intent;
            import android.net.Uri;
            import com.google.android.gms.common.GooglePlayServicesUtil;
            
            /* JADX INFO: renamed from: com.google.android.gms.internal.eo */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public final class C1057eo {
                /* JADX INFO: renamed from: c */
                public static final Intent m1799c(Intent intent) {
                    intent.setData(Uri.fromParts("version", Integer.toString(GooglePlayServicesUtil.GOOGLE_PLAY_SERVICES_VERSION_CODE), null));
                    return intent;
                }
            }
