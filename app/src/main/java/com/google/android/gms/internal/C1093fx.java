            package com.google.android.gms.internal;

            import android.os.Parcel;
            import android.os.Parcelable;
            import com.google.android.gms.common.internal.safeparcel.C0901a;
            import com.google.android.gms.common.internal.safeparcel.C0902b;
            import java.util.HashSet;
            import java.util.Set;
            
            /* JADX INFO: renamed from: com.google.android.gms.internal.fx */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public class C1093fx implements Parcelable.Creator<C1091fv.a> {
                /* JADX INFO: renamed from: a */
                static void m2061a(C1091fv.a aVar, Parcel parcel, int i) {
                    int iM1152k = C0902b.m1152k(parcel);
                    Set<Integer> setM2037di = aVar.m2037di();
                    if (setM2037di.contains(1)) {
                        C0902b.m1150c(parcel, 1, aVar.getVersionCode());
                    }
                    if (setM2037di.contains(2)) {
                        C0902b.m1150c(parcel, 2, aVar.getMax());
                    }
                    if (setM2037di.contains(3)) {
                        C0902b.m1150c(parcel, 3, aVar.getMin());
                    }
                    C0902b.m1131C(parcel, iM1152k);
                }

                /* JADX INFO: renamed from: E */
                public C1091fv.a m2062E(Parcel parcel) {
                    int iM1105f = 0;
                    int iM1111j = C0901a.m1111j(parcel);
                    HashSet hashSet = new HashSet();
                    int iM1105f2 = 0;
                    int iM1105f3 = 0;
                    while (parcel.dataPosition() < iM1111j) {
                        int iM1109i = C0901a.m1109i(parcel);
                        switch (C0901a.m1126y(iM1109i)) {
                            case 1:
                                iM1105f3 = C0901a.m1105f(parcel, iM1109i);
                                hashSet.add(1);
                                break;
                            case 2:
                                iM1105f2 = C0901a.m1105f(parcel, iM1109i);
                                hashSet.add(2);
                                break;
                            case 3:
                                iM1105f = C0901a.m1105f(parcel, iM1109i);
                                hashSet.add(3);
                                break;
                            default:
                                C0901a.m1099b(parcel, iM1109i);
                                break;
                        }
                    }
                    if (parcel.dataPosition() != iM1111j) {
                        throw new C0901a.a("Overread allowed size end=" + iM1111j, parcel);
                    }
                    return new C1091fv.a(hashSet, iM1105f3, iM1105f2, iM1105f);
                }

                /* JADX INFO: renamed from: aj */
                public C1091fv.a[] m2063aj(int i) {
                    return new C1091fv.a[i];
                }

                @Override // android.os.Parcelable.Creator
                public /* synthetic */ C1091fv.a createFromParcel(Parcel x0) {
                    return m2062E(x0);
                }

                @Override // android.os.Parcelable.Creator
                public /* synthetic */ C1091fv.a[] newArray(int x0) {
                    return m2063aj(x0);
                }
            }
