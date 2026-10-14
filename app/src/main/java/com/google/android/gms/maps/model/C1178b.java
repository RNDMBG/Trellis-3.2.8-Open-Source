            package com.google.android.gms.maps.model;

            import android.os.Parcel;
            import android.os.Parcelable;
            import com.google.android.gms.common.internal.safeparcel.C0902b;
            
            
            
            public class C1178b {
                
                static void m2296a(CircleOptions circleOptions, Parcel parcel, int i) {
                    int iM1152k = C0902b.m1152k(parcel);
                    C0902b.m1150c(parcel, 1, circleOptions.getVersionCode());
                    C0902b.m1139a(parcel, 2, (Parcelable) circleOptions.getCenter(), i, false);
                    C0902b.m1133a(parcel, 3, circleOptions.getRadius());
                    C0902b.m1134a(parcel, 4, circleOptions.getStrokeWidth());
                    C0902b.m1150c(parcel, 5, circleOptions.getStrokeColor());
                    C0902b.m1150c(parcel, 6, circleOptions.getFillColor());
                    C0902b.m1134a(parcel, 7, circleOptions.getZIndex());
                    C0902b.m1143a(parcel, 8, circleOptions.isVisible());
                    C0902b.m1131C(parcel, iM1152k);
                }
            }
