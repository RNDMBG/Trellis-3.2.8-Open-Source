            package com.google.android.gms.maps.model;

            import android.os.Parcel;
            import android.os.Parcelable;
            import com.google.android.gms.common.internal.safeparcel.C0902b;
            
            /* JADX INFO: renamed from: com.google.android.gms.maps.model.c */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public class C1179c {
                /* JADX INFO: renamed from: a */
                static void m2297a(GroundOverlayOptions groundOverlayOptions, Parcel parcel, int i) {
                    int iM1152k = C0902b.m1152k(parcel);
                    C0902b.m1150c(parcel, 1, groundOverlayOptions.getVersionCode());
                    C0902b.m1137a(parcel, 2, groundOverlayOptions.m2272cM(), false);
                    C0902b.m1139a(parcel, 3, (Parcelable) groundOverlayOptions.getLocation(), i, false);
                    C0902b.m1134a(parcel, 4, groundOverlayOptions.getWidth());
                    C0902b.m1134a(parcel, 5, groundOverlayOptions.getHeight());
                    C0902b.m1139a(parcel, 6, (Parcelable) groundOverlayOptions.getBounds(), i, false);
                    C0902b.m1134a(parcel, 7, groundOverlayOptions.getBearing());
                    C0902b.m1134a(parcel, 8, groundOverlayOptions.getZIndex());
                    C0902b.m1143a(parcel, 9, groundOverlayOptions.isVisible());
                    C0902b.m1134a(parcel, 10, groundOverlayOptions.getTransparency());
                    C0902b.m1134a(parcel, 11, groundOverlayOptions.getAnchorU());
                    C0902b.m1134a(parcel, 12, groundOverlayOptions.getAnchorV());
                    C0902b.m1131C(parcel, iM1152k);
                }
            }
