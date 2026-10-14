            package com.google.android.gms.wallet;

            import android.os.Parcel;
            import android.os.Parcelable;
            import com.google.android.gms.common.internal.safeparcel.C0901a;
            import com.google.android.gms.common.internal.safeparcel.C0902b;
            
            
            
            public class C1209c implements Parcelable.Creator<FullWallet> {
                
                static void m2348a(FullWallet fullWallet, Parcel parcel, int i) {
                    int iM1152k = C0902b.m1152k(parcel);
                    C0902b.m1150c(parcel, 1, fullWallet.getVersionCode());
                    C0902b.m1140a(parcel, 2, fullWallet.f2469tH, false);
                    C0902b.m1140a(parcel, 3, fullWallet.f2470tI, false);
                    C0902b.m1139a(parcel, 4, (Parcelable) fullWallet.f2471tJ, i, false);
                    C0902b.m1140a(parcel, 5, fullWallet.f2472tK, false);
                    C0902b.m1139a(parcel, 6, (Parcelable) fullWallet.f2473tL, i, false);
                    C0902b.m1139a(parcel, 7, (Parcelable) fullWallet.f2474tM, i, false);
                    C0902b.m1146a(parcel, 8, fullWallet.f2475tN, false);
                    C0902b.m1131C(parcel, iM1152k);
                }

                
                public FullWallet m2349P(Parcel parcel) {
                    String[] strArrM1124w = null;
                    int iM1111j = C0901a.m1111j(parcel);
                    int iM1105f = 0;
                    Address address = null;
                    Address address2 = null;
                    String strM1113l = null;
                    ProxyCard proxyCard = null;
                    String strM1113l2 = null;
                    String strM1113l3 = null;
                    while (parcel.dataPosition() < iM1111j) {
                        int iM1109i = C0901a.m1109i(parcel);
                        switch (C0901a.m1126y(iM1109i)) {
                            case 1:
                                iM1105f = C0901a.m1105f(parcel, iM1109i);
                                break;
                            case 2:
                                strM1113l3 = C0901a.m1113l(parcel, iM1109i);
                                break;
                            case 3:
                                strM1113l2 = C0901a.m1113l(parcel, iM1109i);
                                break;
                            case 4:
                                proxyCard = (ProxyCard) C0901a.m1096a(parcel, iM1109i, ProxyCard.CREATOR);
                                break;
                            case 5:
                                strM1113l = C0901a.m1113l(parcel, iM1109i);
                                break;
                            case 6:
                                address2 = (Address) C0901a.m1096a(parcel, iM1109i, Address.CREATOR);
                                break;
                            case 7:
                                address = (Address) C0901a.m1096a(parcel, iM1109i, Address.CREATOR);
                                break;
                            case 8:
                                strArrM1124w = C0901a.m1124w(parcel, iM1109i);
                                break;
                            default:
                                C0901a.m1099b(parcel, iM1109i);
                                break;
                        }
                    }
                    if (parcel.dataPosition() != iM1111j) {
                        throw new C0901a.a("Overread allowed size end=" + iM1111j, parcel);
                    }
                    return new FullWallet(iM1105f, strM1113l3, strM1113l2, proxyCard, strM1113l, address2, address, strArrM1124w);
                }

                
                public FullWallet[] m2350au(int i) {
                    return new FullWallet[i];
                }

                @Override 
                public  FullWallet createFromParcel(Parcel x0) {
                    return m2349P(x0);
                }

                @Override 
                public  FullWallet[] newArray(int x0) {
                    return m2350au(x0);
                }
            }
