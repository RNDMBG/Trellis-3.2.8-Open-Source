            package com.google.android.gms.maps.model;

            import android.os.Parcel;
            import android.os.Parcelable;
            import com.google.android.gms.common.internal.safeparcel.C0901a;
            import com.google.android.gms.common.internal.safeparcel.C0902b;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public class LatLngBoundsCreator implements Parcelable.Creator<LatLngBounds> {
                public static final int CONTENT_DESCRIPTION = 0;

                /* JADX INFO: renamed from: a */
                static void m2281a(LatLngBounds latLngBounds, Parcel parcel, int i) {
                    int iM1152k = C0902b.m1152k(parcel);
                    C0902b.m1150c(parcel, 1, latLngBounds.getVersionCode());
                    C0902b.m1139a(parcel, 2, (Parcelable) latLngBounds.southwest, i, false);
                    C0902b.m1139a(parcel, 3, (Parcelable) latLngBounds.northeast, i, false);
                    C0902b.m1131C(parcel, iM1152k);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public LatLngBounds createFromParcel(Parcel parcel) {
                    LatLng latLng;
                    LatLng latLng2;
                    int iM1105f;
                    LatLng latLng3 = null;
                    int iM1111j = C0901a.m1111j(parcel);
                    int i = 0;
                    LatLng latLng4 = null;
                    while (parcel.dataPosition() < iM1111j) {
                        int iM1109i = C0901a.m1109i(parcel);
                        switch (C0901a.m1126y(iM1109i)) {
                            case 1:
                                LatLng latLng5 = latLng3;
                                latLng2 = latLng4;
                                iM1105f = C0901a.m1105f(parcel, iM1109i);
                                latLng = latLng5;
                                break;
                            case 2:
                                LatLng latLng6 = (LatLng) C0901a.m1096a(parcel, iM1109i, LatLng.CREATOR);
                                iM1105f = i;
                                latLng = latLng3;
                                latLng2 = latLng6;
                                break;
                            case 3:
                                latLng = (LatLng) C0901a.m1096a(parcel, iM1109i, LatLng.CREATOR);
                                latLng2 = latLng4;
                                iM1105f = i;
                                break;
                            default:
                                C0901a.m1099b(parcel, iM1109i);
                                latLng = latLng3;
                                latLng2 = latLng4;
                                iM1105f = i;
                                break;
                        }
                        i = iM1105f;
                        latLng4 = latLng2;
                        latLng3 = latLng;
                    }
                    if (parcel.dataPosition() != iM1111j) {
                        throw new C0901a.a("Overread allowed size end=" + iM1111j, parcel);
                    }
                    return new LatLngBounds(i, latLng4, latLng3);
                }


                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public LatLngBounds[] newArray(int size) {
                    return new LatLngBounds[size];
                }

            }
