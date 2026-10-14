            package com.google.android.gms.maps.model;

            import android.os.Parcel;
            import android.os.Parcelable;
            import com.google.android.gms.common.internal.safeparcel.C0902b;
            
            /* JADX INFO: renamed from: com.google.android.gms.maps.model.d */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public class C1180d {
                /* JADX INFO: renamed from: a */
                static void m2298a(LatLngBounds latLngBounds, Parcel parcel, int i) {
                    int iM1152k = C0902b.m1152k(parcel);
                    C0902b.m1150c(parcel, 1, latLngBounds.getVersionCode());
                    C0902b.m1139a(parcel, 2, (Parcelable) latLngBounds.southwest, i, false);
                    C0902b.m1139a(parcel, 3, (Parcelable) latLngBounds.northeast, i, false);
                    C0902b.m1131C(parcel, iM1152k);
                }
            }
