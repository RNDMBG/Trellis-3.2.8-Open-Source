            package com.google.android.gms.internal;

            import android.os.Parcel;
            import android.os.Parcelable;
            import com.google.android.gms.common.internal.safeparcel.C0901a;
            import com.google.android.gms.common.internal.safeparcel.C0902b;
            import java.util.ArrayList;
            
            
            
            public class C1043ea implements Parcelable.Creator<C1041dz> {
                
                static void m1699a(C1041dz c1041dz, Parcel parcel, int i) {
                    int iM1152k = C0902b.m1152k(parcel);
                    C0902b.m1150c(parcel, 1, c1041dz.getVersionCode());
                    C0902b.m1149b(parcel, 2, c1041dz.m1686bE(), false);
                    C0902b.m1140a(parcel, 3, c1041dz.m1687bF(), false);
                    C0902b.m1131C(parcel, iM1152k);
                }

                
                public C1041dz[] m1700E(int i) {
                    return new C1041dz[i];
                }

                @Override 
                public  C1041dz createFromParcel(Parcel x0) {
                    return m1701q(x0);
                }

                @Override 
                public  C1041dz[] newArray(int x0) {
                    return m1700E(x0);
                }

                
                public C1041dz m1701q(Parcel parcel) {
                    String strM1113l = null;
                    int iM1111j = C0901a.m1111j(parcel);
                    int iM1105f = 0;
                    ArrayList arrayListM1101c = null;
                    while (parcel.dataPosition() < iM1111j) {
                        int iM1109i = C0901a.m1109i(parcel);
                        switch (C0901a.m1126y(iM1109i)) {
                            case 1:
                                iM1105f = C0901a.m1105f(parcel, iM1109i);
                                break;
                            case 2:
                                arrayListM1101c = C0901a.m1101c(parcel, iM1109i, C1041dz.a.CREATOR);
                                break;
                            case 3:
                                strM1113l = C0901a.m1113l(parcel, iM1109i);
                                break;
                            default:
                                C0901a.m1099b(parcel, iM1109i);
                                break;
                        }
                    }
                    if (parcel.dataPosition() != iM1111j) {
                        throw new C0901a.a("Overread allowed size end=" + iM1111j, parcel);
                    }
                    return new C1041dz(iM1105f, arrayListM1101c, strM1113l);
                }
            }
