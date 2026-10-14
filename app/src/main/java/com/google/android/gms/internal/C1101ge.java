            package com.google.android.gms.internal;

            import android.os.Parcel;
            import android.os.Parcelable;
            import com.google.android.gms.common.internal.safeparcel.C0901a;
            import com.google.android.gms.common.internal.safeparcel.C0902b;
            import java.util.HashSet;
            import java.util.Set;
            
            
            
            public class C1101ge implements Parcelable.Creator<C1091fv.g> {
                
                static void m2084a(C1091fv.g gVar, Parcel parcel, int i) {
                    int iM1152k = C0902b.m1152k(parcel);
                    Set<Integer> setM2054di = gVar.m2054di();
                    if (setM2054di.contains(1)) {
                        C0902b.m1150c(parcel, 1, gVar.getVersionCode());
                    }
                    if (setM2054di.contains(2)) {
                        C0902b.m1143a(parcel, 2, gVar.isPrimary());
                    }
                    if (setM2054di.contains(3)) {
                        C0902b.m1140a(parcel, 3, gVar.getValue(), true);
                    }
                    C0902b.m1131C(parcel, iM1152k);
                }

                
                public C1091fv.g m2085L(Parcel parcel) {
                    boolean zM1102c = false;
                    int iM1111j = C0901a.m1111j(parcel);
                    HashSet hashSet = new HashSet();
                    String strM1113l = null;
                    int iM1105f = 0;
                    while (parcel.dataPosition() < iM1111j) {
                        int iM1109i = C0901a.m1109i(parcel);
                        switch (C0901a.m1126y(iM1109i)) {
                            case 1:
                                iM1105f = C0901a.m1105f(parcel, iM1109i);
                                hashSet.add(1);
                                break;
                            case 2:
                                zM1102c = C0901a.m1102c(parcel, iM1109i);
                                hashSet.add(2);
                                break;
                            case 3:
                                strM1113l = C0901a.m1113l(parcel, iM1109i);
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
                    return new C1091fv.g(hashSet, iM1105f, zM1102c, strM1113l);
                }

                
                public C1091fv.g[] m2086aq(int i) {
                    return new C1091fv.g[i];
                }

                @Override 
                public  C1091fv.g createFromParcel(Parcel x0) {
                    return m2085L(x0);
                }

                @Override 
                public  C1091fv.g[] newArray(int x0) {
                    return m2086aq(x0);
                }
            }
