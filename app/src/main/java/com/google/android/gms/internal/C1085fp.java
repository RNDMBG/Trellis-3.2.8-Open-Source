            package com.google.android.gms.internal;

            import android.os.Parcel;
            import android.os.Parcelable;
            import com.google.android.gms.common.internal.safeparcel.C0901a;
            import com.google.android.gms.common.internal.safeparcel.C0902b;
            
            
            
            public class C1085fp implements Parcelable.Creator<C1083fn> {
                
                static void m1994a(C1083fn c1083fn, Parcel parcel, int i) {
                    int iM1152k = C0902b.m1152k(parcel);
                    C0902b.m1140a(parcel, 1, c1083fn.getAccountName(), false);
                    C0902b.m1150c(parcel, 1000, c1083fn.getVersionCode());
                    C0902b.m1146a(parcel, 2, c1083fn.m1982cZ(), false);
                    C0902b.m1146a(parcel, 3, c1083fn.m1983da(), false);
                    C0902b.m1146a(parcel, 4, c1083fn.m1984db(), false);
                    C0902b.m1140a(parcel, 5, c1083fn.m1985dc(), false);
                    C0902b.m1140a(parcel, 6, c1083fn.m1986dd(), false);
                    C0902b.m1140a(parcel, 7, c1083fn.m1987de(), false);
                    C0902b.m1140a(parcel, 8, c1083fn.m1988df(), false);
                    C0902b.m1131C(parcel, iM1152k);
                }

                
                public C1083fn m1995A(Parcel parcel) {
                    String strM1113l = null;
                    int iM1111j = C0901a.m1111j(parcel);
                    int iM1105f = 0;
                    String strM1113l2 = null;
                    String strM1113l3 = null;
                    String strM1113l4 = null;
                    String[] strArrM1124w = null;
                    String[] strArrM1124w2 = null;
                    String[] strArrM1124w3 = null;
                    String strM1113l5 = null;
                    while (parcel.dataPosition() < iM1111j) {
                        int iM1109i = C0901a.m1109i(parcel);
                        switch (C0901a.m1126y(iM1109i)) {
                            case 1:
                                strM1113l5 = C0901a.m1113l(parcel, iM1109i);
                                break;
                            case 2:
                                strArrM1124w3 = C0901a.m1124w(parcel, iM1109i);
                                break;
                            case 3:
                                strArrM1124w2 = C0901a.m1124w(parcel, iM1109i);
                                break;
                            case 4:
                                strArrM1124w = C0901a.m1124w(parcel, iM1109i);
                                break;
                            case 5:
                                strM1113l4 = C0901a.m1113l(parcel, iM1109i);
                                break;
                            case 6:
                                strM1113l3 = C0901a.m1113l(parcel, iM1109i);
                                break;
                            case 7:
                                strM1113l2 = C0901a.m1113l(parcel, iM1109i);
                                break;
                            case 8:
                                strM1113l = C0901a.m1113l(parcel, iM1109i);
                                break;
                            case 1000:
                                iM1105f = C0901a.m1105f(parcel, iM1109i);
                                break;
                            default:
                                C0901a.m1099b(parcel, iM1109i);
                                break;
                        }
                    }
                    if (parcel.dataPosition() != iM1111j) {
                        throw new C0901a.a("Overread allowed size end=" + iM1111j, parcel);
                    }
                    return new C1083fn(iM1105f, strM1113l5, strArrM1124w3, strArrM1124w2, strArrM1124w, strM1113l4, strM1113l3, strM1113l2, strM1113l);
                }

                
                public C1083fn[] m1996af(int i) {
                    return new C1083fn[i];
                }

                @Override 
                public  C1083fn createFromParcel(Parcel x0) {
                    return m1995A(x0);
                }

                @Override 
                public  C1083fn[] newArray(int x0) {
                    return m1996af(x0);
                }
            }
