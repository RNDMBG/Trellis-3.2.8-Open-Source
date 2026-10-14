            package com.google.android.gms.maps.model;

            import android.os.Parcel;
            import android.os.Parcelable;
            import com.google.android.gms.common.internal.safeparcel.C0902b;
            
            
            
            public class C1182f {
                
                static void m2300a(MarkerOptions markerOptions, Parcel parcel, int i) {
                    int iM1152k = C0902b.m1152k(parcel);
                    C0902b.m1150c(parcel, 1, markerOptions.getVersionCode());
                    C0902b.m1139a(parcel, 2, (Parcelable) markerOptions.getPosition(), i, false);
                    C0902b.m1140a(parcel, 3, markerOptions.getTitle(), false);
                    C0902b.m1140a(parcel, 4, markerOptions.getSnippet(), false);
                    C0902b.m1137a(parcel, 5, markerOptions.m2283cN(), false);
                    C0902b.m1134a(parcel, 6, markerOptions.getAnchorU());
                    C0902b.m1134a(parcel, 7, markerOptions.getAnchorV());
                    C0902b.m1143a(parcel, 8, markerOptions.isDraggable());
                    C0902b.m1143a(parcel, 9, markerOptions.isVisible());
                    C0902b.m1131C(parcel, iM1152k);
                }
            }
