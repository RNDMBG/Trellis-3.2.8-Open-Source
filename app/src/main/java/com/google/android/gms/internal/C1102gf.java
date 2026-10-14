            package com.google.android.gms.internal;

            import android.os.Parcel;
            import android.os.Parcelable;
            import com.google.android.gms.common.internal.safeparcel.C0901a;
            import com.google.android.gms.common.internal.safeparcel.C0902b;
            import java.util.HashSet;
            import java.util.Set;
            
            
            
            public class C1102gf implements Parcelable.Creator<C1091fv.h> {
                
                static void m2087a(C1091fv.h hVar, Parcel parcel, int i) {
                    int iM1152k = C0902b.m1152k(parcel);
                    Set<Integer> setM2057di = hVar.m2057di();
                    if (setM2057di.contains(1)) {
                        C0902b.m1150c(parcel, 1, hVar.getVersionCode());
                    }
                    if (setM2057di.contains(3)) {
                        C0902b.m1150c(parcel, 3, hVar.m2055dV());
                    }
                    if (setM2057di.contains(4)) {
                        C0902b.m1140a(parcel, 4, hVar.getValue(), true);
                    }
                    if (setM2057di.contains(5)) {
                        C0902b.m1140a(parcel, 5, hVar.getLabel(), true);
                    }
                    if (setM2057di.contains(6)) {
                        C0902b.m1150c(parcel, 6, hVar.getType());
                    }
                    C0902b.m1131C(parcel, iM1152k);
                }

                
                public C1091fv.h m2088M(Parcel parcel) {
                    String strM1113l = null;
                    int iM1105f = 0;
                    int iM1111j = C0901a.m1111j(parcel);
                    HashSet hashSet = new HashSet();
                    int iM1105f2 = 0;
                    String strM1113l2 = null;
                    int iM1105f3 = 0;
                    while (parcel.dataPosition() < iM1111j) {
                        int iM1109i = C0901a.m1109i(parcel);
                        switch (C0901a.m1126y(iM1109i)) {
                            case 1:
                                iM1105f3 = C0901a.m1105f(parcel, iM1109i);
                                hashSet.add(1);
                                break;
                            case 2:
                            default:
                                C0901a.m1099b(parcel, iM1109i);
                                break;
                            case 3:
                                iM1105f = C0901a.m1105f(parcel, iM1109i);
                                hashSet.add(3);
                                break;
                            case 4:
                                strM1113l = C0901a.m1113l(parcel, iM1109i);
                                hashSet.add(4);
                                break;
                            case 5:
                                strM1113l2 = C0901a.m1113l(parcel, iM1109i);
                                hashSet.add(5);
                                break;
                            case 6:
                                iM1105f2 = C0901a.m1105f(parcel, iM1109i);
                                hashSet.add(6);
                                break;
                        }
                    }
                    if (parcel.dataPosition() != iM1111j) {
                        throw new C0901a.a("Overread allowed size end=" + iM1111j, parcel);
                    }
                    return new C1091fv.h(hashSet, iM1105f3, strM1113l2, iM1105f2, strM1113l, iM1105f);
                }

                
                public C1091fv.h[] m2089ar(int i) {
                    return new C1091fv.h[i];
                }

                @Override 
                public  C1091fv.h createFromParcel(Parcel x0) {
                    return m2088M(x0);
                }

                @Override 
                public  C1091fv.h[] newArray(int x0) {
                    return m2089ar(x0);
                }
            }
