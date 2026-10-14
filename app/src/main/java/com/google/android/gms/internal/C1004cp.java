            package com.google.android.gms.internal;

            import android.os.Parcel;
            import android.os.Parcelable;
            import com.google.android.gms.common.internal.safeparcel.C0901a;
            import com.google.android.gms.common.internal.safeparcel.C0902b;
            
            
            
            public class C1004cp implements Parcelable.Creator<C1003co> {
                
                static void m1459a(C1003co c1003co, Parcel parcel, int i) {
                    int iM1152k = C0902b.m1152k(parcel);
                    C0902b.m1150c(parcel, 1, c1003co.versionCode);
                    C0902b.m1140a(parcel, 2, c1003co.f1596hP, false);
                    C0902b.m1150c(parcel, 3, c1003co.f1597hQ);
                    C0902b.m1150c(parcel, 4, c1003co.f1598hR);
                    C0902b.m1143a(parcel, 5, c1003co.f1599hS);
                    C0902b.m1131C(parcel, iM1152k);
                }

                @Override 
                public  C1003co createFromParcel(Parcel x0) {
                    return m1460g(x0);
                }

                
                public C1003co m1460g(Parcel parcel) {
                    boolean zM1102c = false;
                    int iM1111j = C0901a.m1111j(parcel);
                    String strM1113l = null;
                    int iM1105f = 0;
                    int iM1105f2 = 0;
                    int iM1105f3 = 0;
                    while (parcel.dataPosition() < iM1111j) {
                        int iM1109i = C0901a.m1109i(parcel);
                        switch (C0901a.m1126y(iM1109i)) {
                            case 1:
                                iM1105f3 = C0901a.m1105f(parcel, iM1109i);
                                break;
                            case 2:
                                strM1113l = C0901a.m1113l(parcel, iM1109i);
                                break;
                            case 3:
                                iM1105f2 = C0901a.m1105f(parcel, iM1109i);
                                break;
                            case 4:
                                iM1105f = C0901a.m1105f(parcel, iM1109i);
                                break;
                            case 5:
                                zM1102c = C0901a.m1102c(parcel, iM1109i);
                                break;
                            default:
                                C0901a.m1099b(parcel, iM1109i);
                                break;
                        }
                    }
                    if (parcel.dataPosition() != iM1111j) {
                        throw new C0901a.a("Overread allowed size end=" + iM1111j, parcel);
                    }
                    return new C1003co(iM1105f3, strM1113l, iM1105f2, iM1105f, zM1102c);
                }

                
                public C1003co[] m1461l(int i) {
                    return new C1003co[i];
                }

                @Override 
                public  C1003co[] newArray(int x0) {
                    return m1461l(x0);
                }
            }
