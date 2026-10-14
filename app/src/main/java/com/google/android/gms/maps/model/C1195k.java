            package com.google.android.gms.maps.model;

            import android.os.Parcel;
            import android.os.Parcelable;
            import com.google.android.gms.common.internal.safeparcel.C0902b;
            
            /* JADX INFO: renamed from: com.google.android.gms.maps.model.k */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public class C1195k {
                /* JADX INFO: renamed from: a */
                static void m2328a(VisibleRegion visibleRegion, Parcel parcel, int i) {
                    int iM1152k = C0902b.m1152k(parcel);
                    C0902b.m1150c(parcel, 1, visibleRegion.getVersionCode());
                    C0902b.m1139a(parcel, 2, (Parcelable) visibleRegion.nearLeft, i, false);
                    C0902b.m1139a(parcel, 3, (Parcelable) visibleRegion.nearRight, i, false);
                    C0902b.m1139a(parcel, 4, (Parcelable) visibleRegion.farLeft, i, false);
                    C0902b.m1139a(parcel, 5, (Parcelable) visibleRegion.farRight, i, false);
                    C0902b.m1139a(parcel, 6, (Parcelable) visibleRegion.latLngBounds, i, false);
                    C0902b.m1131C(parcel, iM1152k);
                }
            }
