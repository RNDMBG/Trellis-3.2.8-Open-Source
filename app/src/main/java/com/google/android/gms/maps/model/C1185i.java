            package com.google.android.gms.maps.model;

            import android.os.Parcel;
            import com.google.android.gms.common.internal.safeparcel.C0902b;
            
            
            
            public class C1185i {
                
                static void m2303a(Tile tile, Parcel parcel, int i) {
                    int iM1152k = C0902b.m1152k(parcel);
                    C0902b.m1150c(parcel, 1, tile.getVersionCode());
                    C0902b.m1150c(parcel, 2, tile.width);
                    C0902b.m1150c(parcel, 3, tile.height);
                    C0902b.m1144a(parcel, 4, tile.data, false);
                    C0902b.m1131C(parcel, iM1152k);
                }
            }
