            package com.google.android.gms.maps;

            import android.os.Parcel;
            import android.os.Parcelable;
            import com.google.android.gms.common.internal.safeparcel.C0902b;
            
            
            
            public class C1149a {
                
                static void m2226a(GoogleMapOptions googleMapOptions, Parcel parcel, int i) {
                    int iM1152k = C0902b.m1152k(parcel);
                    C0902b.m1150c(parcel, 1, googleMapOptions.getVersionCode());
                    C0902b.m1132a(parcel, 2, googleMapOptions.m2210cv());
                    C0902b.m1132a(parcel, 3, googleMapOptions.m2211cw());
                    C0902b.m1150c(parcel, 4, googleMapOptions.getMapType());
                    C0902b.m1139a(parcel, 5, (Parcelable) googleMapOptions.getCamera(), i, false);
                    C0902b.m1132a(parcel, 6, googleMapOptions.m2212cx());
                    C0902b.m1132a(parcel, 7, googleMapOptions.m2213cy());
                    C0902b.m1132a(parcel, 8, googleMapOptions.m2214cz());
                    C0902b.m1132a(parcel, 9, googleMapOptions.m2207cA());
                    C0902b.m1132a(parcel, 10, googleMapOptions.m2208cB());
                    C0902b.m1132a(parcel, 11, googleMapOptions.m2209cC());
                    C0902b.m1131C(parcel, iM1152k);
                }
            }
