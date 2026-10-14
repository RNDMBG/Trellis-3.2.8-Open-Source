            package com.google.android.gms.location;

            import android.os.Parcel;
            import android.os.Parcelable;
            import co.vine.android.util.CommonUtil;
            import com.google.android.gms.common.internal.safeparcel.C0901a;
            import com.google.android.gms.common.internal.safeparcel.C0902b;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public class LocationRequestCreator implements Parcelable.Creator<LocationRequest> {
                public static final int CONTENT_DESCRIPTION = 0;

                /* JADX INFO: renamed from: a */
                static void m2191a(LocationRequest locationRequest, Parcel parcel, int i) {
                    int iM1152k = C0902b.m1152k(parcel);
                    C0902b.m1150c(parcel, 1, locationRequest.mPriority);
                    C0902b.m1150c(parcel, 1000, locationRequest.getVersionCode());
                    C0902b.m1135a(parcel, 2, locationRequest.f2186oJ);
                    C0902b.m1135a(parcel, 3, locationRequest.f2187oK);
                    C0902b.m1143a(parcel, 4, locationRequest.f2188oL);
                    C0902b.m1135a(parcel, 5, locationRequest.f2185oC);
                    C0902b.m1150c(parcel, 6, locationRequest.f2189oM);
                    C0902b.m1134a(parcel, 7, locationRequest.f2190oN);
                    C0902b.m1131C(parcel, iM1152k);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public LocationRequest createFromParcel(Parcel parcel) {
                    boolean zM1102c = false;
                    int iM1111j = C0901a.m1111j(parcel);
                    int iM1105f = 102;
                    long jM1106g = CommonUtil.HOUR_MILLIS;
                    long jM1106g2 = 600000;
                    long jM1106g3 = Long.MAX_VALUE;
                    int iM1105f2 = Integer.MAX_VALUE;
                    float fM1108i = 0.0f;
                    int iM1105f3 = 0;
                    while (parcel.dataPosition() < iM1111j) {
                        int iM1109i = C0901a.m1109i(parcel);
                        switch (C0901a.m1126y(iM1109i)) {
                            case 1:
                                iM1105f = C0901a.m1105f(parcel, iM1109i);
                                break;
                            case 2:
                                jM1106g = C0901a.m1106g(parcel, iM1109i);
                                break;
                            case 3:
                                jM1106g2 = C0901a.m1106g(parcel, iM1109i);
                                break;
                            case 4:
                                zM1102c = C0901a.m1102c(parcel, iM1109i);
                                break;
                            case 5:
                                jM1106g3 = C0901a.m1106g(parcel, iM1109i);
                                break;
                            case 6:
                                iM1105f2 = C0901a.m1105f(parcel, iM1109i);
                                break;
                            case 7:
                                fM1108i = C0901a.m1108i(parcel, iM1109i);
                                break;
                            case 1000:
                                iM1105f3 = C0901a.m1105f(parcel, iM1109i);
                                break;
                            default:
                                C0901a.m1099b(parcel, iM1109i);
                                break;
                        }
                    }
                    if (parcel.dataPosition() != iM1111j) {
                        throw new C0901a.a("Overread allowed size end=" + iM1111j, parcel);
                    }
                    return new LocationRequest(iM1105f3, iM1105f, jM1106g, jM1106g2, zM1102c, jM1106g3, iM1105f2, fM1108i);
                }


                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public LocationRequest[] newArray(int size) {
                    return new LocationRequest[size];
                }

            }
