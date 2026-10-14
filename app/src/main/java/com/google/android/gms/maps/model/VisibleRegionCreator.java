            package com.google.android.gms.maps.model;

            import android.os.Parcel;
            import android.os.Parcelable;
            import com.google.android.gms.common.internal.safeparcel.C0901a;
            import com.google.android.gms.common.internal.safeparcel.C0902b;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public class VisibleRegionCreator implements Parcelable.Creator<VisibleRegion> {
                public static final int CONTENT_DESCRIPTION = 0;

                /* JADX INFO: renamed from: a */
                static void m2294a(VisibleRegion visibleRegion, Parcel parcel, int i) {
                    int iM1152k = C0902b.m1152k(parcel);
                    C0902b.m1150c(parcel, 1, visibleRegion.getVersionCode());
                    C0902b.m1139a(parcel, 2, (Parcelable) visibleRegion.nearLeft, i, false);
                    C0902b.m1139a(parcel, 3, (Parcelable) visibleRegion.nearRight, i, false);
                    C0902b.m1139a(parcel, 4, (Parcelable) visibleRegion.farLeft, i, false);
                    C0902b.m1139a(parcel, 5, (Parcelable) visibleRegion.farRight, i, false);
                    C0902b.m1139a(parcel, 6, (Parcelable) visibleRegion.latLngBounds, i, false);
                    C0902b.m1131C(parcel, iM1152k);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public VisibleRegion createFromParcel(Parcel parcel) {
                    LatLngBounds latLngBounds = null;
                    int iM1111j = C0901a.m1111j(parcel);
                    int iM1105f = 0;
                    LatLng latLng = null;
                    LatLng latLng2 = null;
                    LatLng latLng3 = null;
                    LatLng latLng4 = null;
                    while (parcel.dataPosition() < iM1111j) {
                        int iM1109i = C0901a.m1109i(parcel);
                        switch (C0901a.m1126y(iM1109i)) {
                            case 1:
                                iM1105f = C0901a.m1105f(parcel, iM1109i);
                                break;
                            case 2:
                                latLng4 = (LatLng) C0901a.m1096a(parcel, iM1109i, LatLng.CREATOR);
                                break;
                            case 3:
                                latLng3 = (LatLng) C0901a.m1096a(parcel, iM1109i, LatLng.CREATOR);
                                break;
                            case 4:
                                latLng2 = (LatLng) C0901a.m1096a(parcel, iM1109i, LatLng.CREATOR);
                                break;
                            case 5:
                                latLng = (LatLng) C0901a.m1096a(parcel, iM1109i, LatLng.CREATOR);
                                break;
                            case 6:
                                latLngBounds = (LatLngBounds) C0901a.m1096a(parcel, iM1109i, LatLngBounds.CREATOR);
                                break;
                            default:
                                C0901a.m1099b(parcel, iM1109i);
                                break;
                        }
                    }
                    if (parcel.dataPosition() != iM1111j) {
                        throw new C0901a.a("Overread allowed size end=" + iM1111j, parcel);
                    }
                    return new VisibleRegion(iM1105f, latLng4, latLng3, latLng2, latLng, latLngBounds);
                }


                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public VisibleRegion[] newArray(int size) {
                    return new VisibleRegion[size];
                }

            }
