            package com.google.android.gms.wallet;

            import android.os.Parcel;
            import android.os.Parcelable;
            import com.google.android.gms.common.internal.safeparcel.C0901a;
            import com.google.android.gms.common.internal.safeparcel.C0902b;
            
            
            
            public class C1216j implements Parcelable.Creator<OfferWalletObject> {
                
                static void m2369a(OfferWalletObject offerWalletObject, Parcel parcel, int i) {
                    int iM1152k = C0902b.m1152k(parcel);
                    C0902b.m1150c(parcel, 1, offerWalletObject.getVersionCode());
                    C0902b.m1140a(parcel, 2, offerWalletObject.f2520tU, false);
                    C0902b.m1140a(parcel, 3, offerWalletObject.f2521ul, false);
                    C0902b.m1131C(parcel, iM1152k);
                }

                
                public OfferWalletObject m2370W(Parcel parcel) {
                    String strM1113l = null;
                    int iM1111j = C0901a.m1111j(parcel);
                    int iM1105f = 0;
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
                            default:
                                C0901a.m1099b(parcel, iM1109i);
                                break;
                        }
                    }
                    if (parcel.dataPosition() != iM1111j) {
                        throw new C0901a.a("Overread allowed size end=" + iM1111j, parcel);
                    }
                    return new OfferWalletObject(iM1105f, strM1113l2, strM1113l);
                }

                
                public OfferWalletObject[] m2371aB(int i) {
                    return new OfferWalletObject[i];
                }

                @Override 
                public  OfferWalletObject createFromParcel(Parcel x0) {
                    return m2370W(x0);
                }

                @Override 
                public  OfferWalletObject[] newArray(int x0) {
                    return m2371aB(x0);
                }
            }
