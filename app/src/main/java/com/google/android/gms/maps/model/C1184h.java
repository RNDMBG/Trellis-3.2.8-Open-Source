            package com.google.android.gms.maps.model;

            import android.os.Parcel;
            import com.google.android.gms.common.internal.safeparcel.C0902b;
            
            /* JADX INFO: renamed from: com.google.android.gms.maps.model.h */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public class C1184h {
                /* JADX INFO: renamed from: a */
                static void m2302a(PolylineOptions polylineOptions, Parcel parcel, int i) {
                    int iM1152k = C0902b.m1152k(parcel);
                    C0902b.m1150c(parcel, 1, polylineOptions.getVersionCode());
                    C0902b.m1149b(parcel, 2, polylineOptions.getPoints(), false);
                    C0902b.m1134a(parcel, 3, polylineOptions.getWidth());
                    C0902b.m1150c(parcel, 4, polylineOptions.getColor());
                    C0902b.m1134a(parcel, 5, polylineOptions.getZIndex());
                    C0902b.m1143a(parcel, 6, polylineOptions.isVisible());
                    C0902b.m1143a(parcel, 7, polylineOptions.isGeodesic());
                    C0902b.m1131C(parcel, iM1152k);
                }
            }
