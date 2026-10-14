            package com.google.android.gms.internal;

            import android.os.Parcel;
            import android.os.Parcelable;
            import com.google.android.gms.common.internal.safeparcel.C0901a;
            import com.google.android.gms.common.internal.safeparcel.C0902b;
            import java.util.HashSet;
            import java.util.Set;
            
            /* JADX INFO: renamed from: com.google.android.gms.internal.ft */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public class C1089ft implements Parcelable.Creator<C1088fs> {
                /* JADX INFO: renamed from: a */
                static void m2021a(C1088fs c1088fs, Parcel parcel, int i) {
                    int iM1152k = C0902b.m1152k(parcel);
                    Set<Integer> setM2019di = c1088fs.m2019di();
                    if (setM2019di.contains(1)) {
                        C0902b.m1150c(parcel, 1, c1088fs.getVersionCode());
                    }
                    if (setM2019di.contains(2)) {
                        C0902b.m1140a(parcel, 2, c1088fs.getId(), true);
                    }
                    if (setM2019di.contains(4)) {
                        C0902b.m1139a(parcel, 4, (Parcelable) c1088fs.m2020dz(), i, true);
                    }
                    if (setM2019di.contains(5)) {
                        C0902b.m1140a(parcel, 5, c1088fs.getStartDate(), true);
                    }
                    if (setM2019di.contains(6)) {
                        C0902b.m1139a(parcel, 6, (Parcelable) c1088fs.m2017dA(), i, true);
                    }
                    if (setM2019di.contains(7)) {
                        C0902b.m1140a(parcel, 7, c1088fs.getType(), true);
                    }
                    C0902b.m1131C(parcel, iM1152k);
                }

                /* JADX INFO: renamed from: C */
                public C1088fs m2022C(Parcel parcel) {
                    String strM1113l = null;
                    int iM1111j = C0901a.m1111j(parcel);
                    HashSet hashSet = new HashSet();
                    int iM1105f = 0;
                    C1086fq c1086fq = null;
                    String strM1113l2 = null;
                    C1086fq c1086fq2 = null;
                    String strM1113l3 = null;
                    while (parcel.dataPosition() < iM1111j) {
                        int iM1109i = C0901a.m1109i(parcel);
                        switch (C0901a.m1126y(iM1109i)) {
                            case 1:
                                iM1105f = C0901a.m1105f(parcel, iM1109i);
                                hashSet.add(1);
                                break;
                            case 2:
                                strM1113l3 = C0901a.m1113l(parcel, iM1109i);
                                hashSet.add(2);
                                break;
                            case 3:
                            default:
                                C0901a.m1099b(parcel, iM1109i);
                                break;
                            case 4:
                                C1086fq c1086fq3 = (C1086fq) C0901a.m1096a(parcel, iM1109i, C1086fq.CREATOR);
                                hashSet.add(4);
                                c1086fq2 = c1086fq3;
                                break;
                            case 5:
                                strM1113l2 = C0901a.m1113l(parcel, iM1109i);
                                hashSet.add(5);
                                break;
                            case 6:
                                C1086fq c1086fq4 = (C1086fq) C0901a.m1096a(parcel, iM1109i, C1086fq.CREATOR);
                                hashSet.add(6);
                                c1086fq = c1086fq4;
                                break;
                            case 7:
                                strM1113l = C0901a.m1113l(parcel, iM1109i);
                                hashSet.add(7);
                                break;
                        }
                    }
                    if (parcel.dataPosition() != iM1111j) {
                        throw new C0901a.a("Overread allowed size end=" + iM1111j, parcel);
                    }
                    return new C1088fs(hashSet, iM1105f, strM1113l3, c1086fq2, strM1113l2, c1086fq, strM1113l);
                }

                /* JADX INFO: renamed from: ah */
                public C1088fs[] m2023ah(int i) {
                    return new C1088fs[i];
                }

                @Override // android.os.Parcelable.Creator
                public /* synthetic */ C1088fs createFromParcel(Parcel x0) {
                    return m2022C(x0);
                }

                @Override // android.os.Parcelable.Creator
                public /* synthetic */ C1088fs[] newArray(int x0) {
                    return m2023ah(x0);
                }
            }
