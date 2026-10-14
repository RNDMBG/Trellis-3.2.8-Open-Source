            package com.google.android.gms.internal;

            import android.os.Parcel;
            import android.os.Parcelable;
            import com.google.android.gms.common.internal.safeparcel.C0901a;
            import com.google.android.gms.common.internal.safeparcel.C0902b;
            import java.util.ArrayList;
            import java.util.HashSet;
            import java.util.Set;
            
            /* JADX INFO: renamed from: com.google.android.gms.internal.fw */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public class C1092fw implements Parcelable.Creator<C1091fv> {
                /* JADX INFO: renamed from: a */
                static void m2058a(C1091fv c1091fv, Parcel parcel, int i) {
                    int iM1152k = C0902b.m1152k(parcel);
                    Set<Integer> setM2035di = c1091fv.m2035di();
                    if (setM2035di.contains(1)) {
                        C0902b.m1150c(parcel, 1, c1091fv.getVersionCode());
                    }
                    if (setM2035di.contains(2)) {
                        C0902b.m1140a(parcel, 2, c1091fv.getAboutMe(), true);
                    }
                    if (setM2035di.contains(3)) {
                        C0902b.m1139a(parcel, 3, (Parcelable) c1091fv.m2027dD(), i, true);
                    }
                    if (setM2035di.contains(4)) {
                        C0902b.m1140a(parcel, 4, c1091fv.getBirthday(), true);
                    }
                    if (setM2035di.contains(5)) {
                        C0902b.m1140a(parcel, 5, c1091fv.getBraggingRights(), true);
                    }
                    if (setM2035di.contains(6)) {
                        C0902b.m1150c(parcel, 6, c1091fv.getCircledByCount());
                    }
                    if (setM2035di.contains(7)) {
                        C0902b.m1139a(parcel, 7, (Parcelable) c1091fv.m2028dE(), i, true);
                    }
                    if (setM2035di.contains(8)) {
                        C0902b.m1140a(parcel, 8, c1091fv.getCurrentLocation(), true);
                    }
                    if (setM2035di.contains(9)) {
                        C0902b.m1140a(parcel, 9, c1091fv.getDisplayName(), true);
                    }
                    if (setM2035di.contains(12)) {
                        C0902b.m1150c(parcel, 12, c1091fv.getGender());
                    }
                    if (setM2035di.contains(14)) {
                        C0902b.m1140a(parcel, 14, c1091fv.getId(), true);
                    }
                    if (setM2035di.contains(15)) {
                        C0902b.m1139a(parcel, 15, (Parcelable) c1091fv.m2029dF(), i, true);
                    }
                    if (setM2035di.contains(16)) {
                        C0902b.m1143a(parcel, 16, c1091fv.isPlusUser());
                    }
                    if (setM2035di.contains(19)) {
                        C0902b.m1139a(parcel, 19, (Parcelable) c1091fv.m2030dG(), i, true);
                    }
                    if (setM2035di.contains(18)) {
                        C0902b.m1140a(parcel, 18, c1091fv.getLanguage(), true);
                    }
                    if (setM2035di.contains(21)) {
                        C0902b.m1150c(parcel, 21, c1091fv.getObjectType());
                    }
                    if (setM2035di.contains(20)) {
                        C0902b.m1140a(parcel, 20, c1091fv.getNickname(), true);
                    }
                    if (setM2035di.contains(23)) {
                        C0902b.m1149b(parcel, 23, c1091fv.m2032dI(), true);
                    }
                    if (setM2035di.contains(22)) {
                        C0902b.m1149b(parcel, 22, c1091fv.m2031dH(), true);
                    }
                    if (setM2035di.contains(25)) {
                        C0902b.m1150c(parcel, 25, c1091fv.getRelationshipStatus());
                    }
                    if (setM2035di.contains(24)) {
                        C0902b.m1150c(parcel, 24, c1091fv.getPlusOneCount());
                    }
                    if (setM2035di.contains(27)) {
                        C0902b.m1140a(parcel, 27, c1091fv.getUrl(), true);
                    }
                    if (setM2035di.contains(26)) {
                        C0902b.m1140a(parcel, 26, c1091fv.getTagline(), true);
                    }
                    if (setM2035di.contains(29)) {
                        C0902b.m1143a(parcel, 29, c1091fv.isVerified());
                    }
                    if (setM2035di.contains(28)) {
                        C0902b.m1149b(parcel, 28, c1091fv.m2033dJ(), true);
                    }
                    C0902b.m1131C(parcel, iM1152k);
                }

                /* JADX INFO: renamed from: D */
                public C1091fv m2059D(Parcel parcel) {
                    int iM1111j = C0901a.m1111j(parcel);
                    HashSet hashSet = new HashSet();
                    int iM1105f = 0;
                    String strM1113l = null;
                    C1091fv.a aVar = null;
                    String strM1113l2 = null;
                    String strM1113l3 = null;
                    int iM1105f2 = 0;
                    C1091fv.b bVar = null;
                    String strM1113l4 = null;
                    String strM1113l5 = null;
                    int iM1105f3 = 0;
                    String strM1113l6 = null;
                    C1091fv.c cVar = null;
                    boolean zM1102c = false;
                    String strM1113l7 = null;
                    C1091fv.d dVar = null;
                    String strM1113l8 = null;
                    int iM1105f4 = 0;
                    ArrayList arrayListM1101c = null;
                    ArrayList arrayListM1101c2 = null;
                    int iM1105f5 = 0;
                    int iM1105f6 = 0;
                    String strM1113l9 = null;
                    String strM1113l10 = null;
                    ArrayList arrayListM1101c3 = null;
                    boolean zM1102c2 = false;
                    while (parcel.dataPosition() < iM1111j) {
                        int iM1109i = C0901a.m1109i(parcel);
                        switch (C0901a.m1126y(iM1109i)) {
                            case 1:
                                iM1105f = C0901a.m1105f(parcel, iM1109i);
                                hashSet.add(1);
                                break;
                            case 2:
                                strM1113l = C0901a.m1113l(parcel, iM1109i);
                                hashSet.add(2);
                                break;
                            case 3:
                                C1091fv.a aVar2 = (C1091fv.a) C0901a.m1096a(parcel, iM1109i, C1091fv.a.CREATOR);
                                hashSet.add(3);
                                aVar = aVar2;
                                break;
                            case 4:
                                strM1113l2 = C0901a.m1113l(parcel, iM1109i);
                                hashSet.add(4);
                                break;
                            case 5:
                                strM1113l3 = C0901a.m1113l(parcel, iM1109i);
                                hashSet.add(5);
                                break;
                            case 6:
                                iM1105f2 = C0901a.m1105f(parcel, iM1109i);
                                hashSet.add(6);
                                break;
                            case 7:
                                C1091fv.b bVar2 = (C1091fv.b) C0901a.m1096a(parcel, iM1109i, C1091fv.b.CREATOR);
                                hashSet.add(7);
                                bVar = bVar2;
                                break;
                            case 8:
                                strM1113l4 = C0901a.m1113l(parcel, iM1109i);
                                hashSet.add(8);
                                break;
                            case 9:
                                strM1113l5 = C0901a.m1113l(parcel, iM1109i);
                                hashSet.add(9);
                                break;
                            case 10:
                            case 11:
                            case 13:
                            case 17:
                            default:
                                C0901a.m1099b(parcel, iM1109i);
                                break;
                            case 12:
                                iM1105f3 = C0901a.m1105f(parcel, iM1109i);
                                hashSet.add(12);
                                break;
                            case 14:
                                strM1113l6 = C0901a.m1113l(parcel, iM1109i);
                                hashSet.add(14);
                                break;
                            case 15:
                                C1091fv.c cVar2 = (C1091fv.c) C0901a.m1096a(parcel, iM1109i, C1091fv.c.CREATOR);
                                hashSet.add(15);
                                cVar = cVar2;
                                break;
                            case 16:
                                zM1102c = C0901a.m1102c(parcel, iM1109i);
                                hashSet.add(16);
                                break;
                            case 18:
                                strM1113l7 = C0901a.m1113l(parcel, iM1109i);
                                hashSet.add(18);
                                break;
                            case 19:
                                C1091fv.d dVar2 = (C1091fv.d) C0901a.m1096a(parcel, iM1109i, C1091fv.d.CREATOR);
                                hashSet.add(19);
                                dVar = dVar2;
                                break;
                            case 20:
                                strM1113l8 = C0901a.m1113l(parcel, iM1109i);
                                hashSet.add(20);
                                break;
                            case 21:
                                iM1105f4 = C0901a.m1105f(parcel, iM1109i);
                                hashSet.add(21);
                                break;
                            case 22:
                                arrayListM1101c = C0901a.m1101c(parcel, iM1109i, C1091fv.f.CREATOR);
                                hashSet.add(22);
                                break;
                            case 23:
                                arrayListM1101c2 = C0901a.m1101c(parcel, iM1109i, C1091fv.g.CREATOR);
                                hashSet.add(23);
                                break;
                            case 24:
                                iM1105f5 = C0901a.m1105f(parcel, iM1109i);
                                hashSet.add(24);
                                break;
                            case 25:
                                iM1105f6 = C0901a.m1105f(parcel, iM1109i);
                                hashSet.add(25);
                                break;
                            case 26:
                                strM1113l9 = C0901a.m1113l(parcel, iM1109i);
                                hashSet.add(26);
                                break;
                            case 27:
                                strM1113l10 = C0901a.m1113l(parcel, iM1109i);
                                hashSet.add(27);
                                break;
                            case 28:
                                arrayListM1101c3 = C0901a.m1101c(parcel, iM1109i, C1091fv.h.CREATOR);
                                hashSet.add(28);
                                break;
                            case 29:
                                zM1102c2 = C0901a.m1102c(parcel, iM1109i);
                                hashSet.add(29);
                                break;
                        }
                    }
                    if (parcel.dataPosition() != iM1111j) {
                        throw new C0901a.a("Overread allowed size end=" + iM1111j, parcel);
                    }
                    return new C1091fv(hashSet, iM1105f, strM1113l, aVar, strM1113l2, strM1113l3, iM1105f2, bVar, strM1113l4, strM1113l5, iM1105f3, strM1113l6, cVar, zM1102c, strM1113l7, dVar, strM1113l8, iM1105f4, arrayListM1101c, arrayListM1101c2, iM1105f5, iM1105f6, strM1113l9, strM1113l10, arrayListM1101c3, zM1102c2);
                }

                /* JADX INFO: renamed from: ai */
                public C1091fv[] m2060ai(int i) {
                    return new C1091fv[i];
                }

                @Override // android.os.Parcelable.Creator
                public /* synthetic */ C1091fv createFromParcel(Parcel x0) {
                    return m2059D(x0);
                }

                @Override // android.os.Parcelable.Creator
                public /* synthetic */ C1091fv[] newArray(int x0) {
                    return m2060ai(x0);
                }
            }
