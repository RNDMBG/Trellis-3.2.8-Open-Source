            package com.google.android.gms.maps.model;

            import android.os.Parcel;
            import com.google.android.gms.common.internal.safeparcel.C0902b;
            
            
            
            public class C1194j {
                
                static void m2327a(TileOverlayOptions tileOverlayOptions, Parcel parcel, int i) {
                    int iM1152k = C0902b.m1152k(parcel);
                    C0902b.m1150c(parcel, 1, tileOverlayOptions.getVersionCode());
                    C0902b.m1137a(parcel, 2, tileOverlayOptions.m2290cP(), false);
                    C0902b.m1143a(parcel, 3, tileOverlayOptions.isVisible());
                    C0902b.m1134a(parcel, 4, tileOverlayOptions.getZIndex());
                    C0902b.m1131C(parcel, iM1152k);
                }
            }
