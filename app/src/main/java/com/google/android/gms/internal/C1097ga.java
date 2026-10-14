            package com.google.android.gms.internal;

            import android.os.Parcel;
            import android.os.Parcelable;
            import com.google.android.gms.common.internal.safeparcel.C0901a;
            import com.google.android.gms.common.internal.safeparcel.C0902b;
            import java.util.HashSet;
            import java.util.Set;
            
            
            
            public class C1097ga implements Parcelable.Creator<C1091fv.b.C1343b> {
                
                static void m2072a(C1091fv.b.C1343b c1343b, Parcel parcel, int i) {
                    int iM1152k = C0902b.m1152k(parcel);
                    Set<Integer> setM2045di = c1343b.m2045di();
                    if (setM2045di.contains(1)) {
                        C0902b.m1150c(parcel, 1, c1343b.getVersionCode());
                    }
                    if (setM2045di.contains(2)) {
                        C0902b.m1150c(parcel, 2, c1343b.getHeight());
                    }
                    if (setM2045di.contains(3)) {
                        C0902b.m1140a(parcel, 3, c1343b.getUrl(), true);
                    }
                    if (setM2045di.contains(4)) {
                        C0902b.m1150c(parcel, 4, c1343b.getWidth());
                    }
                    C0902b.m1131C(parcel, iM1152k);
                }

                
                public C1091fv.b.C1343b m2073H(Parcel parcel) {
                    int iM1105f = 0;
                    int iM1111j = C0901a.m1111j(parcel);
                    HashSet hashSet = new HashSet();
                    String strM1113l = null;
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
                                strM1113l = C0901a.m1113l(parcel, iM1109i);
                                hashSet.add(3);
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
                    return new C1091fv.b.C1343b(hashSet, iM1105f3, iM1105f2, strM1113l, iM1105f);
                }

                
                public C1091fv.b.C1343b[] m2074am(int i) {
                    return new C1091fv.b.C1343b[i];
                }

                @Override 
                public  C1091fv.b.C1343b createFromParcel(Parcel x0) {
                    return m2073H(x0);
                }

                @Override 
                public  C1091fv.b.C1343b[] newArray(int x0) {
                    return m2074am(x0);
                }
            }
