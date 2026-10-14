            package com.google.android.gms.maps.model;

            import android.os.Parcel;
            import android.os.Parcelable;
            import com.google.android.gms.common.internal.safeparcel.C0902b;
            
            
            
            public class C1180d {
                
                static void m2298a(LatLngBounds latLngBounds, Parcel parcel, int i) {
                    int iM1152k = C0902b.m1152k(parcel);
                    C0902b.m1150c(parcel, 1, latLngBounds.getVersionCode());
                    C0902b.m1139a(parcel, 2, (Parcelable) latLngBounds.southwest, i, false);
                    C0902b.m1139a(parcel, 3, (Parcelable) latLngBounds.northeast, i, false);
                    C0902b.m1131C(parcel, iM1152k);
                }
            }
