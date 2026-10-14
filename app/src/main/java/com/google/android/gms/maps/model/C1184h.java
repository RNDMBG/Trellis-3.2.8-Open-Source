            package com.google.android.gms.maps.model;

            import android.os.Parcel;
            import com.google.android.gms.common.internal.safeparcel.C0902b;
            
            
            
            public class C1184h {
                
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
