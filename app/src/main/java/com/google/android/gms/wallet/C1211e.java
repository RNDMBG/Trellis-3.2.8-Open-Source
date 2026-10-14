            package com.google.android.gms.wallet;

            import android.os.Parcel;
            import android.os.Parcelable;
            import com.google.android.gms.common.internal.safeparcel.C0901a;
            import com.google.android.gms.common.internal.safeparcel.C0902b;
            
            
            
            public class C1211e implements Parcelable.Creator<LineItem> {
                
                static void m2354a(LineItem lineItem, Parcel parcel, int i) {
                    int iM1152k = C0902b.m1152k(parcel);
                    C0902b.m1150c(parcel, 1, lineItem.getVersionCode());
                    C0902b.m1140a(parcel, 2, lineItem.description, false);
                    C0902b.m1140a(parcel, 3, lineItem.f2484tQ, false);
                    C0902b.m1140a(parcel, 4, lineItem.f2485tR, false);
                    C0902b.m1140a(parcel, 5, lineItem.f2482tD, false);
                    C0902b.m1150c(parcel, 6, lineItem.f2486tS);
                    C0902b.m1140a(parcel, 7, lineItem.f2483tE, false);
                    C0902b.m1131C(parcel, iM1152k);
                }

                
                public LineItem m2355R(Parcel parcel) {
                    int iM1105f = 0;
                    String strM1113l = null;
                    int iM1111j = C0901a.m1111j(parcel);
                    String strM1113l2 = null;
                    String strM1113l3 = null;
                    String strM1113l4 = null;
                    String strM1113l5 = null;
                    int iM1105f2 = 0;
                    while (parcel.dataPosition() < iM1111j) {
                        int iM1109i = C0901a.m1109i(parcel);
                        switch (C0901a.m1126y(iM1109i)) {
                            case 1:
                                iM1105f2 = C0901a.m1105f(parcel, iM1109i);
                                break;
                            case 2:
                                strM1113l5 = C0901a.m1113l(parcel, iM1109i);
                                break;
                            case 3:
                                strM1113l4 = C0901a.m1113l(parcel, iM1109i);
                                break;
                            case 4:
                                strM1113l3 = C0901a.m1113l(parcel, iM1109i);
                                break;
                            case 5:
                                strM1113l2 = C0901a.m1113l(parcel, iM1109i);
                                break;
                            case 6:
                                iM1105f = C0901a.m1105f(parcel, iM1109i);
                                break;
                            case 7:
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
                    return new LineItem(iM1105f2, strM1113l5, strM1113l4, strM1113l3, strM1113l2, iM1105f, strM1113l);
                }

                
                public LineItem[] m2356aw(int i) {
                    return new LineItem[i];
                }

                @Override 
                public  LineItem createFromParcel(Parcel x0) {
                    return m2355R(x0);
                }

                @Override 
                public  LineItem[] newArray(int x0) {
                    return m2356aw(x0);
                }
            }
