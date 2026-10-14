            package com.google.android.gms.internal;

            import android.os.Parcel;
            import android.os.Parcelable;
            import com.google.android.gms.common.internal.safeparcel.C0901a;
            import com.google.android.gms.common.internal.safeparcel.C0902b;
            
            /* JADX INFO: renamed from: com.google.android.gms.internal.ed */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public class C1046ed implements Parcelable.Creator<C1045ec> {
                /* JADX INFO: renamed from: a */
                static void m1719a(C1045ec c1045ec, Parcel parcel, int i) {
                    int iM1152k = C0902b.m1152k(parcel);
                    C0902b.m1150c(parcel, 1, c1045ec.getVersionCode());
                    C0902b.m1138a(parcel, 2, c1045ec.m1717bH(), false);
                    C0902b.m1139a(parcel, 3, (Parcelable) c1045ec.m1718bI(), i, false);
                    C0902b.m1131C(parcel, iM1152k);
                }

                /* JADX INFO: renamed from: G */
                public C1045ec[] m1720G(int i) {
                    return new C1045ec[i];
                }

                @Override // android.os.Parcelable.Creator
                public /* synthetic */ C1045ec createFromParcel(Parcel x0) {
                    return m1721s(x0);
                }

                @Override // android.os.Parcelable.Creator
                public /* synthetic */ C1045ec[] newArray(int x0) {
                    return m1720G(x0);
                }

                /* JADX INFO: renamed from: s */
                public C1045ec m1721s(Parcel parcel) {
                    C1041dz c1041dz = null;
                    int iM1111j = C0901a.m1111j(parcel);
                    int iM1105f = 0;
                    Parcel parcelM1127y = null;
                    while (parcel.dataPosition() < iM1111j) {
                        int iM1109i = C0901a.m1109i(parcel);
                        switch (C0901a.m1126y(iM1109i)) {
                            case 1:
                                iM1105f = C0901a.m1105f(parcel, iM1109i);
                                break;
                            case 2:
                                parcelM1127y = C0901a.m1127y(parcel, iM1109i);
                                break;
                            case 3:
                                c1041dz = (C1041dz) C0901a.m1096a(parcel, iM1109i, C1041dz.CREATOR);
                                break;
                            default:
                                C0901a.m1099b(parcel, iM1109i);
                                break;
                        }
                    }
                    if (parcel.dataPosition() != iM1111j) {
                        throw new C0901a.a("Overread allowed size end=" + iM1111j, parcel);
                    }
                    return new C1045ec(iM1105f, parcelM1127y, c1041dz);
                }
            }
