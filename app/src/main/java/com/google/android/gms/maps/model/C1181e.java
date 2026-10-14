            package com.google.android.gms.maps.model;

            import android.os.Parcel;
            import com.google.android.gms.common.internal.safeparcel.C0902b;
            
            
            
            public class C1181e {
                
                static void m2299a(LatLng latLng, Parcel parcel, int i) {
                    int iM1152k = C0902b.m1152k(parcel);
                    C0902b.m1150c(parcel, 1, latLng.getVersionCode());
                    C0902b.m1133a(parcel, 2, latLng.latitude);
                    C0902b.m1133a(parcel, 3, latLng.longitude);
                    C0902b.m1131C(parcel, iM1152k);
                }
            }
