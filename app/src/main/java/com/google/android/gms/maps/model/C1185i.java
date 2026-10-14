            package com.google.android.gms.maps.model;

            import android.os.Parcel;
            import com.google.android.gms.common.internal.safeparcel.C0902b;
            
            /* JADX INFO: renamed from: com.google.android.gms.maps.model.i */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public class C1185i {
                /* JADX INFO: renamed from: a */
                static void m2303a(Tile tile, Parcel parcel, int i) {
                    int iM1152k = C0902b.m1152k(parcel);
                    C0902b.m1150c(parcel, 1, tile.getVersionCode());
                    C0902b.m1150c(parcel, 2, tile.width);
                    C0902b.m1150c(parcel, 3, tile.height);
                    C0902b.m1144a(parcel, 4, tile.data, false);
                    C0902b.m1131C(parcel, iM1152k);
                }
            }
