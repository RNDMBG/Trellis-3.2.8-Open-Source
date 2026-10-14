            package com.google.android.gms.internal;

            import android.os.Parcel;
            import android.os.Parcelable;
            import com.google.android.gms.common.internal.safeparcel.C0901a;
            import com.google.android.gms.common.internal.safeparcel.C0902b;
            
            /* JADX INFO: renamed from: com.google.android.gms.internal.fb */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public class C1071fb implements Parcelable.Creator<C1070fa> {
                /* JADX INFO: renamed from: a */
                static void m1936a(C1070fa c1070fa, Parcel parcel, int i) {
                    int iM1152k = C0902b.m1152k(parcel);
                    C0902b.m1140a(parcel, 1, c1070fa.getRequestId(), false);
                    C0902b.m1150c(parcel, 1000, c1070fa.getVersionCode());
                    C0902b.m1135a(parcel, 2, c1070fa.getExpirationTime());
                    C0902b.m1142a(parcel, 3, c1070fa.m1932co());
                    C0902b.m1133a(parcel, 4, c1070fa.getLatitude());
                    C0902b.m1133a(parcel, 5, c1070fa.getLongitude());
                    C0902b.m1134a(parcel, 6, c1070fa.m1933cp());
                    C0902b.m1150c(parcel, 7, c1070fa.m1934cq());
                    C0902b.m1150c(parcel, 8, c1070fa.getNotificationResponsiveness());
                    C0902b.m1150c(parcel, 9, c1070fa.m1935cr());
                    C0902b.m1131C(parcel, iM1152k);
                }

                /* JADX INFO: renamed from: ac */
                public C1070fa[] m1937ac(int i) {
                    return new C1070fa[i];
                }

                @Override // android.os.Parcelable.Creator
                public /* synthetic */ C1070fa createFromParcel(Parcel x0) {
                    return m1938z(x0);
                }

                @Override // android.os.Parcelable.Creator
                public /* synthetic */ C1070fa[] newArray(int x0) {
                    return m1937ac(x0);
                }

                /* JADX INFO: renamed from: z */
                public C1070fa m1938z(Parcel parcel) {
                    int iM1111j = C0901a.m1111j(parcel);
                    int iM1105f = 0;
                    String strM1113l = null;
                    int iM1105f2 = 0;
                    short sM1104e = 0;
                    double dM1110j = 0.0d;
                    double dM1110j2 = 0.0d;
                    float fM1108i = 0.0f;
                    long jM1106g = 0;
                    int iM1105f3 = 0;
                    int iM1105f4 = -1;
                    while (parcel.dataPosition() < iM1111j) {
                        int iM1109i = C0901a.m1109i(parcel);
                        switch (C0901a.m1126y(iM1109i)) {
                            case 1:
                                strM1113l = C0901a.m1113l(parcel, iM1109i);
                                break;
                            case 2:
                                jM1106g = C0901a.m1106g(parcel, iM1109i);
                                break;
                            case 3:
                                sM1104e = C0901a.m1104e(parcel, iM1109i);
                                break;
                            case 4:
                                dM1110j = C0901a.m1110j(parcel, iM1109i);
                                break;
                            case 5:
                                dM1110j2 = C0901a.m1110j(parcel, iM1109i);
                                break;
                            case 6:
                                fM1108i = C0901a.m1108i(parcel, iM1109i);
                                break;
                            case 7:
                                iM1105f2 = C0901a.m1105f(parcel, iM1109i);
                                break;
                            case 8:
                                iM1105f3 = C0901a.m1105f(parcel, iM1109i);
                                break;
                            case 9:
                                iM1105f4 = C0901a.m1105f(parcel, iM1109i);
                                break;
                            case 1000:
                                iM1105f = C0901a.m1105f(parcel, iM1109i);
                                break;
                            default:
                                C0901a.m1099b(parcel, iM1109i);
                                break;
                        }
                    }
                    if (parcel.dataPosition() != iM1111j) {
                        throw new C0901a.a("Overread allowed size end=" + iM1111j, parcel);
                    }
                    return new C1070fa(iM1105f, strM1113l, iM1105f2, sM1104e, dM1110j, dM1110j2, fM1108i, jM1106g, iM1105f3, iM1105f4);
                }
            }
