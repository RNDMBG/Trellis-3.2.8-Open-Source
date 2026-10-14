            package com.google.android.gms.internal;

            import android.os.Bundle;
            import android.os.Parcel;
            import android.os.Parcelable;
            import com.google.android.gms.common.internal.safeparcel.C0901a;
            import com.google.android.gms.common.internal.safeparcel.C0902b;
            import java.util.ArrayList;
            
            
            
            public class C1125w implements Parcelable.Creator<C1124v> {
                
                static void m2175a(C1124v c1124v, Parcel parcel, int i) {
                    int iM1152k = C0902b.m1152k(parcel);
                    C0902b.m1150c(parcel, 1, c1124v.versionCode);
                    C0902b.m1135a(parcel, 2, c1124v.f2159es);
                    C0902b.m1136a(parcel, 3, c1124v.extras, false);
                    C0902b.m1150c(parcel, 4, c1124v.f2160et);
                    C0902b.m1141a(parcel, 5, c1124v.f2161eu, false);
                    C0902b.m1143a(parcel, 6, c1124v.f2162ev);
                    C0902b.m1150c(parcel, 7, c1124v.tagForChildDirectedTreatment);
                    C0902b.m1131C(parcel, iM1152k);
                }

                
                public C1124v m2176a(Parcel parcel) {
                    ArrayList<String> arrayListM1125x = null;
                    int iM1105f = 0;
                    int iM1111j = C0901a.m1111j(parcel);
                    long jM1106g = 0;
                    boolean zM1102c = false;
                    int iM1105f2 = 0;
                    Bundle bundleM1115n = null;
                    int iM1105f3 = 0;
                    while (parcel.dataPosition() < iM1111j) {
                        int iM1109i = C0901a.m1109i(parcel);
                        switch (C0901a.m1126y(iM1109i)) {
                            case 1:
                                iM1105f3 = C0901a.m1105f(parcel, iM1109i);
                                break;
                            case 2:
                                jM1106g = C0901a.m1106g(parcel, iM1109i);
                                break;
                            case 3:
                                bundleM1115n = C0901a.m1115n(parcel, iM1109i);
                                break;
                            case 4:
                                iM1105f2 = C0901a.m1105f(parcel, iM1109i);
                                break;
                            case 5:
                                arrayListM1125x = C0901a.m1125x(parcel, iM1109i);
                                break;
                            case 6:
                                zM1102c = C0901a.m1102c(parcel, iM1109i);
                                break;
                            case 7:
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
                    return new C1124v(iM1105f3, jM1106g, bundleM1115n, iM1105f2, arrayListM1125x, zM1102c, iM1105f);
                }

                
                public C1124v[] m2177b(int i) {
                    return new C1124v[i];
                }

                @Override 
                public  C1124v createFromParcel(Parcel x0) {
                    return m2176a(x0);
                }

                @Override 
                public  C1124v[] newArray(int x0) {
                    return m2177b(x0);
                }
            }
