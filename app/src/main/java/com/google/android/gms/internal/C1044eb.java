            package com.google.android.gms.internal;

            import android.os.Parcel;
            import android.os.Parcelable;
            import com.google.android.gms.common.internal.safeparcel.C0901a;
            import com.google.android.gms.common.internal.safeparcel.C0902b;
            import java.util.ArrayList;
            
            
            
            public class C1044eb implements Parcelable.Creator<C1041dz.a> {
                
                static void m1702a(C1041dz.a aVar, Parcel parcel, int i) {
                    int iM1152k = C0902b.m1152k(parcel);
                    C0902b.m1150c(parcel, 1, aVar.versionCode);
                    C0902b.m1140a(parcel, 2, aVar.className, false);
                    C0902b.m1149b(parcel, 3, aVar.f1750lM, false);
                    C0902b.m1131C(parcel, iM1152k);
                }

                
                public C1041dz.a[] m1703F(int i) {
                    return new C1041dz.a[i];
                }

                @Override 
                public  C1041dz.a createFromParcel(Parcel x0) {
                    return m1704r(x0);
                }

                @Override 
                public  C1041dz.a[] newArray(int x0) {
                    return m1703F(x0);
                }

                
                public C1041dz.a m1704r(Parcel parcel) {
                    ArrayList arrayListM1101c = null;
                    int iM1111j = C0901a.m1111j(parcel);
                    int iM1105f = 0;
                    String strM1113l = null;
                    while (parcel.dataPosition() < iM1111j) {
                        int iM1109i = C0901a.m1109i(parcel);
                        switch (C0901a.m1126y(iM1109i)) {
                            case 1:
                                iM1105f = C0901a.m1105f(parcel, iM1109i);
                                break;
                            case 2:
                                strM1113l = C0901a.m1113l(parcel, iM1109i);
                                break;
                            case 3:
                                arrayListM1101c = C0901a.m1101c(parcel, iM1109i, C1041dz.b.CREATOR);
                                break;
                            default:
                                C0901a.m1099b(parcel, iM1109i);
                                break;
                        }
                    }
                    if (parcel.dataPosition() != iM1111j) {
                        throw new C0901a.a("Overread allowed size end=" + iM1111j, parcel);
                    }
                    return new C1041dz.a(iM1105f, strM1113l, arrayListM1101c);
                }
            }
