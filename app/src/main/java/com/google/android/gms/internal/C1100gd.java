            package com.google.android.gms.internal;

            import android.os.Parcel;
            import android.os.Parcelable;
            import com.google.android.gms.common.internal.safeparcel.C0901a;
            import com.google.android.gms.common.internal.safeparcel.C0902b;
            import java.util.HashSet;
            import java.util.Set;
            
            
            
            public class C1100gd implements Parcelable.Creator<C1091fv.f> {
                
                static void m2081a(C1091fv.f fVar, Parcel parcel, int i) {
                    int iM1152k = C0902b.m1152k(parcel);
                    Set<Integer> setM2052di = fVar.m2052di();
                    if (setM2052di.contains(1)) {
                        C0902b.m1150c(parcel, 1, fVar.getVersionCode());
                    }
                    if (setM2052di.contains(2)) {
                        C0902b.m1140a(parcel, 2, fVar.getDepartment(), true);
                    }
                    if (setM2052di.contains(3)) {
                        C0902b.m1140a(parcel, 3, fVar.getDescription(), true);
                    }
                    if (setM2052di.contains(4)) {
                        C0902b.m1140a(parcel, 4, fVar.getEndDate(), true);
                    }
                    if (setM2052di.contains(5)) {
                        C0902b.m1140a(parcel, 5, fVar.getLocation(), true);
                    }
                    if (setM2052di.contains(6)) {
                        C0902b.m1140a(parcel, 6, fVar.getName(), true);
                    }
                    if (setM2052di.contains(7)) {
                        C0902b.m1143a(parcel, 7, fVar.isPrimary());
                    }
                    if (setM2052di.contains(8)) {
                        C0902b.m1140a(parcel, 8, fVar.getStartDate(), true);
                    }
                    if (setM2052di.contains(9)) {
                        C0902b.m1140a(parcel, 9, fVar.getTitle(), true);
                    }
                    if (setM2052di.contains(10)) {
                        C0902b.m1150c(parcel, 10, fVar.getType());
                    }
                    C0902b.m1131C(parcel, iM1152k);
                }

                
                public C1091fv.f m2082K(Parcel parcel) {
                    int iM1105f = 0;
                    String strM1113l = null;
                    int iM1111j = C0901a.m1111j(parcel);
                    HashSet hashSet = new HashSet();
                    String strM1113l2 = null;
                    boolean zM1102c = false;
                    String strM1113l3 = null;
                    String strM1113l4 = null;
                    String strM1113l5 = null;
                    String strM1113l6 = null;
                    String strM1113l7 = null;
                    int iM1105f2 = 0;
                    while (parcel.dataPosition() < iM1111j) {
                        int iM1109i = C0901a.m1109i(parcel);
                        switch (C0901a.m1126y(iM1109i)) {
                            case 1:
                                iM1105f2 = C0901a.m1105f(parcel, iM1109i);
                                hashSet.add(1);
                                break;
                            case 2:
                                strM1113l7 = C0901a.m1113l(parcel, iM1109i);
                                hashSet.add(2);
                                break;
                            case 3:
                                strM1113l6 = C0901a.m1113l(parcel, iM1109i);
                                hashSet.add(3);
                                break;
                            case 4:
                                strM1113l5 = C0901a.m1113l(parcel, iM1109i);
                                hashSet.add(4);
                                break;
                            case 5:
                                strM1113l4 = C0901a.m1113l(parcel, iM1109i);
                                hashSet.add(5);
                                break;
                            case 6:
                                strM1113l3 = C0901a.m1113l(parcel, iM1109i);
                                hashSet.add(6);
                                break;
                            case 7:
                                zM1102c = C0901a.m1102c(parcel, iM1109i);
                                hashSet.add(7);
                                break;
                            case 8:
                                strM1113l2 = C0901a.m1113l(parcel, iM1109i);
                                hashSet.add(8);
                                break;
                            case 9:
                                strM1113l = C0901a.m1113l(parcel, iM1109i);
                                hashSet.add(9);
                                break;
                            case 10:
                                iM1105f = C0901a.m1105f(parcel, iM1109i);
                                hashSet.add(10);
                                break;
                            default:
                                C0901a.m1099b(parcel, iM1109i);
                                break;
                        }
                    }
                    if (parcel.dataPosition() != iM1111j) {
                        throw new C0901a.a("Overread allowed size end=" + iM1111j, parcel);
                    }
                    return new C1091fv.f(hashSet, iM1105f2, strM1113l7, strM1113l6, strM1113l5, strM1113l4, strM1113l3, zM1102c, strM1113l2, strM1113l, iM1105f);
                }

                
                public C1091fv.f[] m2083ap(int i) {
                    return new C1091fv.f[i];
                }

                @Override 
                public  C1091fv.f createFromParcel(Parcel x0) {
                    return m2082K(x0);
                }

                @Override 
                public  C1091fv.f[] newArray(int x0) {
                    return m2083ap(x0);
                }
            }
