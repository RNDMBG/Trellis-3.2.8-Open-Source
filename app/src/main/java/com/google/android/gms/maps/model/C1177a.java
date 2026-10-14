            package com.google.android.gms.maps.model;

            import android.os.Parcel;
            import android.os.Parcelable;
            import com.google.android.gms.common.internal.safeparcel.C0902b;
            
            
            
            public class C1177a {
                
                static void m2295a(CameraPosition cameraPosition, Parcel parcel, int i) {
                    int iM1152k = C0902b.m1152k(parcel);
                    C0902b.m1150c(parcel, 1, cameraPosition.getVersionCode());
                    C0902b.m1139a(parcel, 2, (Parcelable) cameraPosition.target, i, false);
                    C0902b.m1134a(parcel, 3, cameraPosition.zoom);
                    C0902b.m1134a(parcel, 4, cameraPosition.tilt);
                    C0902b.m1134a(parcel, 5, cameraPosition.bearing);
                    C0902b.m1131C(parcel, iM1152k);
                }
            }
