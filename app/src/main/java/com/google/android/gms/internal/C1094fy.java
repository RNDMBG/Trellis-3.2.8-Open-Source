            package com.google.android.gms.internal;

            import android.os.Parcel;
            import android.os.Parcelable;
            import com.google.android.gms.common.internal.safeparcel.C0901a;
            import com.google.android.gms.common.internal.safeparcel.C0902b;
            import java.util.HashSet;
            import java.util.Set;
            
            
            
            public class C1094fy implements Parcelable.Creator<C1091fv.b> {
                
                static void m2064a(C1091fv.b bVar, Parcel parcel, int i) {
                    int iM1152k = C0902b.m1152k(parcel);
                    Set<Integer> setM2041di = bVar.m2041di();
                    if (setM2041di.contains(1)) {
                        C0902b.m1150c(parcel, 1, bVar.getVersionCode());
                    }
                    if (setM2041di.contains(2)) {
                        C0902b.m1139a(parcel, 2, (Parcelable) bVar.m2038dM(), i, true);
                    }
                    if (setM2041di.contains(3)) {
                        C0902b.m1139a(parcel, 3, (Parcelable) bVar.m2039dN(), i, true);
                    }
                    if (setM2041di.contains(4)) {
                        C0902b.m1150c(parcel, 4, bVar.getLayout());
                    }
                    C0902b.m1131C(parcel, iM1152k);
                }

                
                public C1091fv.b m2065F(Parcel parcel) {
                    C1091fv.b.C1343b c1343b = null;
                    int iM1105f = 0;
                    int iM1111j = C0901a.m1111j(parcel);
                    HashSet hashSet = new HashSet();
                    C1091fv.b.a aVar = null;
                    int iM1105f2 = 0;
                    while (parcel.dataPosition() < iM1111j) {
                        int iM1109i = C0901a.m1109i(parcel);
                        switch (C0901a.m1126y(iM1109i)) {
                            case 1:
                                iM1105f2 = C0901a.m1105f(parcel, iM1109i);
                                hashSet.add(1);
                                break;
                            case 2:
                                C1091fv.b.a aVar2 = (C1091fv.b.a) C0901a.m1096a(parcel, iM1109i, C1091fv.b.a.CREATOR);
                                hashSet.add(2);
                                aVar = aVar2;
                                break;
                            case 3:
                                C1091fv.b.C1343b c1343b2 = (C1091fv.b.C1343b) C0901a.m1096a(parcel, iM1109i, C1091fv.b.C1343b.CREATOR);
                                hashSet.add(3);
                                c1343b = c1343b2;
                                break;
                            case 4:
                                iM1105f = C0901a.m1105f(parcel, iM1109i);
                                hashSet.add(4);
                                break;
                            default:
                                C0901a.m1099b(parcel, iM1109i);
                                break;
                        }
                    }
                    if (parcel.dataPosition() != iM1111j) {
                        throw new C0901a.a("Overread allowed size end=" + iM1111j, parcel);
                    }
                    return new C1091fv.b(hashSet, iM1105f2, aVar, c1343b, iM1105f);
                }

                
                public C1091fv.b[] m2066ak(int i) {
                    return new C1091fv.b[i];
                }

                @Override 
                public  C1091fv.b createFromParcel(Parcel x0) {
                    return m2065F(x0);
                }

                @Override 
                public  C1091fv.b[] newArray(int x0) {
                    return m2066ak(x0);
                }
            }
