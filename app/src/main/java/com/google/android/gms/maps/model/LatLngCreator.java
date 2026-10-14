            package com.google.android.gms.maps.model;

            import android.os.Parcel;
            import android.os.Parcelable;
            import com.google.android.gms.common.internal.safeparcel.C0901a;
            import com.google.android.gms.common.internal.safeparcel.C0902b;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public class LatLngCreator implements Parcelable.Creator<LatLng> {
                public static final int CONTENT_DESCRIPTION = 0;

                /* JADX INFO: renamed from: a */
                static void m2282a(LatLng latLng, Parcel parcel, int i) {
                    int iM1152k = C0902b.m1152k(parcel);
                    C0902b.m1150c(parcel, 1, latLng.getVersionCode());
                    C0902b.m1133a(parcel, 2, latLng.latitude);
                    C0902b.m1133a(parcel, 3, latLng.longitude);
                    C0902b.m1131C(parcel, iM1152k);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public LatLng createFromParcel(Parcel parcel) {
                    double dM1110j = 0.0d;
                    int iM1111j = C0901a.m1111j(parcel);
                    int iM1105f = 0;
                    double dM1110j2 = 0.0d;
                    while (parcel.dataPosition() < iM1111j) {
                        int iM1109i = C0901a.m1109i(parcel);
                        switch (C0901a.m1126y(iM1109i)) {
                            case 1:
                                iM1105f = C0901a.m1105f(parcel, iM1109i);
                                break;
                            case 2:
                                dM1110j2 = C0901a.m1110j(parcel, iM1109i);
                                break;
                            case 3:
                                dM1110j = C0901a.m1110j(parcel, iM1109i);
                                break;
                            default:
                                C0901a.m1099b(parcel, iM1109i);
                                break;
                        }
                    }
                    if (parcel.dataPosition() != iM1111j) {
                        throw new C0901a.a("Overread allowed size end=" + iM1111j, parcel);
                    }
                    return new LatLng(iM1105f, dM1110j2, dM1110j);
                }


                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public LatLng[] newArray(int size) {
                    return new LatLng[size];
                }

            }
