            package com.google.android.gms.internal;

            import android.os.Parcel;
            import android.os.Parcelable;
            import com.google.android.gms.common.internal.safeparcel.C0901a;
            import com.google.android.gms.common.internal.safeparcel.C0902b;
            import java.util.HashSet;
            import java.util.Set;
            
            /* JADX INFO: renamed from: com.google.android.gms.internal.gc */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public class C1099gc implements Parcelable.Creator<C1091fv.d> {
                /* JADX INFO: renamed from: a */
                static void m2078a(C1091fv.d dVar, Parcel parcel, int i) {
                    int iM1152k = C0902b.m1152k(parcel);
                    Set<Integer> setM2049di = dVar.m2049di();
                    if (setM2049di.contains(1)) {
                        C0902b.m1150c(parcel, 1, dVar.getVersionCode());
                    }
                    if (setM2049di.contains(2)) {
                        C0902b.m1140a(parcel, 2, dVar.getFamilyName(), true);
                    }
                    if (setM2049di.contains(3)) {
                        C0902b.m1140a(parcel, 3, dVar.getFormatted(), true);
                    }
                    if (setM2049di.contains(4)) {
                        C0902b.m1140a(parcel, 4, dVar.getGivenName(), true);
                    }
                    if (setM2049di.contains(5)) {
                        C0902b.m1140a(parcel, 5, dVar.getHonorificPrefix(), true);
                    }
                    if (setM2049di.contains(6)) {
                        C0902b.m1140a(parcel, 6, dVar.getHonorificSuffix(), true);
                    }
                    if (setM2049di.contains(7)) {
                        C0902b.m1140a(parcel, 7, dVar.getMiddleName(), true);
                    }
                    C0902b.m1131C(parcel, iM1152k);
                }

                /* JADX INFO: renamed from: J */
                public C1091fv.d m2079J(Parcel parcel) {
                    String strM1113l = null;
                    int iM1111j = C0901a.m1111j(parcel);
                    HashSet hashSet = new HashSet();
                    int iM1105f = 0;
                    String strM1113l2 = null;
                    String strM1113l3 = null;
                    String strM1113l4 = null;
                    String strM1113l5 = null;
                    String strM1113l6 = null;
                    while (parcel.dataPosition() < iM1111j) {
                        int iM1109i = C0901a.m1109i(parcel);
                        switch (C0901a.m1126y(iM1109i)) {
                            case 1:
                                iM1105f = C0901a.m1105f(parcel, iM1109i);
                                hashSet.add(1);
                                break;
                            case 2:
                                strM1113l6 = C0901a.m1113l(parcel, iM1109i);
                                hashSet.add(2);
                                break;
                            case 3:
                                strM1113l5 = C0901a.m1113l(parcel, iM1109i);
                                hashSet.add(3);
                                break;
                            case 4:
                                strM1113l4 = C0901a.m1113l(parcel, iM1109i);
                                hashSet.add(4);
                                break;
                            case 5:
                                strM1113l3 = C0901a.m1113l(parcel, iM1109i);
                                hashSet.add(5);
                                break;
                            case 6:
                                strM1113l2 = C0901a.m1113l(parcel, iM1109i);
                                hashSet.add(6);
                                break;
                            case 7:
                                strM1113l = C0901a.m1113l(parcel, iM1109i);
                                hashSet.add(7);
                                break;
                            default:
                                C0901a.m1099b(parcel, iM1109i);
                                break;
                        }
                    }
                    if (parcel.dataPosition() != iM1111j) {
                        throw new C0901a.a("Overread allowed size end=" + iM1111j, parcel);
                    }
                    return new C1091fv.d(hashSet, iM1105f, strM1113l6, strM1113l5, strM1113l4, strM1113l3, strM1113l2, strM1113l);
                }

                /* JADX INFO: renamed from: ao */
                public C1091fv.d[] m2080ao(int i) {
                    return new C1091fv.d[i];
                }

                @Override // android.os.Parcelable.Creator
                public /* synthetic */ C1091fv.d createFromParcel(Parcel x0) {
                    return m2079J(x0);
                }

                @Override // android.os.Parcelable.Creator
                public /* synthetic */ C1091fv.d[] newArray(int x0) {
                    return m2080ao(x0);
                }
            }
