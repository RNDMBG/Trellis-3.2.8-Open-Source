            package com.google.android.gms.internal;

            import android.os.Parcel;
            import android.os.Parcelable;
            import com.google.android.gms.common.internal.safeparcel.C0901a;
            import com.google.android.gms.common.internal.safeparcel.C0902b;
            
            /* JADX INFO: renamed from: com.google.android.gms.internal.ds */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public class C1034ds implements Parcelable.Creator<C1033dr> {
                /* JADX INFO: renamed from: a */
                static void m1623a(C1033dr c1033dr, Parcel parcel, int i) {
                    int iM1152k = C0902b.m1152k(parcel);
                    C0902b.m1150c(parcel, 1, c1033dr.getVersionCode());
                    C0902b.m1139a(parcel, 2, (Parcelable) c1033dr.m1621bk(), i, false);
                    C0902b.m1131C(parcel, iM1152k);
                }

                @Override // android.os.Parcelable.Creator
                public /* synthetic */ C1033dr createFromParcel(Parcel x0) {
                    return m1624l(x0);
                }

                /* JADX INFO: renamed from: l */
                public C1033dr m1624l(Parcel parcel) {
                    int iM1111j = C0901a.m1111j(parcel);
                    int iM1105f = 0;
                    C1035dt c1035dt = null;
                    while (parcel.dataPosition() < iM1111j) {
                        int iM1109i = C0901a.m1109i(parcel);
                        switch (C0901a.m1126y(iM1109i)) {
                            case 1:
                                iM1105f = C0901a.m1105f(parcel, iM1109i);
                                break;
                            case 2:
                                c1035dt = (C1035dt) C0901a.m1096a(parcel, iM1109i, C1035dt.CREATOR);
                                break;
                            default:
                                C0901a.m1099b(parcel, iM1109i);
                                break;
                        }
                    }
                    if (parcel.dataPosition() != iM1111j) {
                        throw new C0901a.a("Overread allowed size end=" + iM1111j, parcel);
                    }
                    return new C1033dr(iM1105f, c1035dt);
                }

                @Override // android.os.Parcelable.Creator
                public /* synthetic */ C1033dr[] newArray(int x0) {
                    return m1625z(x0);
                }

                /* JADX INFO: renamed from: z */
                public C1033dr[] m1625z(int i) {
                    return new C1033dr[i];
                }
            }
