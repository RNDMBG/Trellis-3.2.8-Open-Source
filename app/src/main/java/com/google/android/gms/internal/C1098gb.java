            package com.google.android.gms.internal;

            import android.os.Parcel;
            import android.os.Parcelable;
            import com.google.android.gms.common.internal.safeparcel.C0901a;
            import com.google.android.gms.common.internal.safeparcel.C0902b;
            import java.util.HashSet;
            import java.util.Set;
            
            
            
            public class C1098gb implements Parcelable.Creator<C1091fv.c> {
                
                static void m2075a(C1091fv.c cVar, Parcel parcel, int i) {
                    int iM1152k = C0902b.m1152k(parcel);
                    Set<Integer> setM2047di = cVar.m2047di();
                    if (setM2047di.contains(1)) {
                        C0902b.m1150c(parcel, 1, cVar.getVersionCode());
                    }
                    if (setM2047di.contains(2)) {
                        C0902b.m1140a(parcel, 2, cVar.getUrl(), true);
                    }
                    C0902b.m1131C(parcel, iM1152k);
                }

                
                public C1091fv.c m2076I(Parcel parcel) {
                    int iM1111j = C0901a.m1111j(parcel);
                    HashSet hashSet = new HashSet();
                    int iM1105f = 0;
                    String strM1113l = null;
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
                            default:
                                C0901a.m1099b(parcel, iM1109i);
                                break;
                        }
                    }
                    if (parcel.dataPosition() != iM1111j) {
                        throw new C0901a.a("Overread allowed size end=" + iM1111j, parcel);
                    }
                    return new C1091fv.c(hashSet, iM1105f, strM1113l);
                }

                
                public C1091fv.c[] m2077an(int i) {
                    return new C1091fv.c[i];
                }

                @Override 
                public  C1091fv.c createFromParcel(Parcel x0) {
                    return m2076I(x0);
                }

                @Override 
                public  C1091fv.c[] newArray(int x0) {
                    return m2077an(x0);
                }
            }
