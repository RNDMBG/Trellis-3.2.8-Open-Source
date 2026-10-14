            package com.google.android.gms.wallet;

            import android.os.Parcel;
            import android.os.Parcelable;
            import com.google.android.gms.common.internal.safeparcel.C0901a;
            import com.google.android.gms.common.internal.safeparcel.C0902b;
            import java.util.ArrayList;
            
            
            
            public class C1208b implements Parcelable.Creator<Cart> {
                
                static void m2345a(Cart cart, Parcel parcel, int i) {
                    int iM1152k = C0902b.m1152k(parcel);
                    C0902b.m1150c(parcel, 1, cart.getVersionCode());
                    C0902b.m1140a(parcel, 2, cart.f2464tD, false);
                    C0902b.m1140a(parcel, 3, cart.f2465tE, false);
                    C0902b.m1149b(parcel, 4, cart.f2466tF, false);
                    C0902b.m1131C(parcel, iM1152k);
                }

                
                public Cart m2346O(Parcel parcel) {
                    String strM1113l = null;
                    int iM1111j = C0901a.m1111j(parcel);
                    int iM1105f = 0;
                    ArrayList arrayList = new ArrayList();
                    String strM1113l2 = null;
                    while (parcel.dataPosition() < iM1111j) {
                        int iM1109i = C0901a.m1109i(parcel);
                        switch (C0901a.m1126y(iM1109i)) {
                            case 1:
                                iM1105f = C0901a.m1105f(parcel, iM1109i);
                                break;
                            case 2:
                                strM1113l2 = C0901a.m1113l(parcel, iM1109i);
                                break;
                            case 3:
                                strM1113l = C0901a.m1113l(parcel, iM1109i);
                                break;
                            case 4:
                                arrayList = C0901a.m1101c(parcel, iM1109i, LineItem.CREATOR);
                                break;
                            default:
                                C0901a.m1099b(parcel, iM1109i);
                                break;
                        }
                    }
                    if (parcel.dataPosition() != iM1111j) {
                        throw new C0901a.a("Overread allowed size end=" + iM1111j, parcel);
                    }
                    return new Cart(iM1105f, strM1113l2, strM1113l, arrayList);
                }

                
                public Cart[] m2347at(int i) {
                    return new Cart[i];
                }

                @Override 
                public  Cart createFromParcel(Parcel x0) {
                    return m2346O(x0);
                }

                @Override 
                public  Cart[] newArray(int x0) {
                    return m2347at(x0);
                }
            }
