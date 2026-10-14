            package com.google.android.gms.maps.model;

            import android.os.Parcel;
            import com.google.android.gms.common.internal.safeparcel.C0902b;
            
            /* JADX INFO: renamed from: com.google.android.gms.maps.model.g */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public class C1183g {
                /* JADX INFO: renamed from: a */
                static void m2301a(PolygonOptions polygonOptions, Parcel parcel, int i) {
                    int iM1152k = C0902b.m1152k(parcel);
                    C0902b.m1150c(parcel, 1, polygonOptions.getVersionCode());
                    C0902b.m1149b(parcel, 2, polygonOptions.getPoints(), false);
                    C0902b.m1151c(parcel, 3, polygonOptions.m2285cO(), false);
                    C0902b.m1134a(parcel, 4, polygonOptions.getStrokeWidth());
                    C0902b.m1150c(parcel, 5, polygonOptions.getStrokeColor());
                    C0902b.m1150c(parcel, 6, polygonOptions.getFillColor());
                    C0902b.m1134a(parcel, 7, polygonOptions.getZIndex());
                    C0902b.m1143a(parcel, 8, polygonOptions.isVisible());
                    C0902b.m1143a(parcel, 9, polygonOptions.isGeodesic());
                    C0902b.m1131C(parcel, iM1152k);
                }
            }
