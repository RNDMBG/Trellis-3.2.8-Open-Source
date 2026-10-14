            package com.google.android.gms.wallet;

            import android.os.Parcel;
            import android.os.Parcelable;
            import com.google.android.gms.common.internal.safeparcel.C0901a;
            import com.google.android.gms.common.internal.safeparcel.C0902b;
            
            /* JADX INFO: renamed from: com.google.android.gms.wallet.g */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public class C1213g implements Parcelable.Creator<MaskedWallet> {
                /* JADX INFO: renamed from: a */
                static void m2360a(MaskedWallet maskedWallet, Parcel parcel, int i) {
                    int iM1152k = C0902b.m1152k(parcel);
                    C0902b.m1150c(parcel, 1, maskedWallet.getVersionCode());
                    C0902b.m1140a(parcel, 2, maskedWallet.f2495tH, false);
                    C0902b.m1140a(parcel, 3, maskedWallet.f2496tI, false);
                    C0902b.m1146a(parcel, 4, maskedWallet.f2500tN, false);
                    C0902b.m1140a(parcel, 5, maskedWallet.f2497tK, false);
                    C0902b.m1139a(parcel, 6, (Parcelable) maskedWallet.f2498tL, i, false);
                    C0902b.m1139a(parcel, 7, (Parcelable) maskedWallet.f2499tM, i, false);
                    C0902b.m1145a(parcel, 8, (Parcelable[]) maskedWallet.f2501tZ, i, false);
                    C0902b.m1145a(parcel, 9, (Parcelable[]) maskedWallet.f2502ua, i, false);
                    C0902b.m1131C(parcel, iM1152k);
                }

                /* JADX INFO: renamed from: T */
                public MaskedWallet m2361T(Parcel parcel) {
                    OfferWalletObject[] offerWalletObjectArr = null;
                    int iM1111j = C0901a.m1111j(parcel);
                    int iM1105f = 0;
                    LoyaltyWalletObject[] loyaltyWalletObjectArr = null;
                    Address address = null;
                    Address address2 = null;
                    String strM1113l = null;
                    String[] strArrM1124w = null;
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
                                strArrM1124w = C0901a.m1124w(parcel, iM1109i);
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
                                loyaltyWalletObjectArr = (LoyaltyWalletObject[]) C0901a.m1100b(parcel, iM1109i, LoyaltyWalletObject.CREATOR);
                                break;
                            case 9:
                                offerWalletObjectArr = (OfferWalletObject[]) C0901a.m1100b(parcel, iM1109i, OfferWalletObject.CREATOR);
                                break;
                            default:
                                C0901a.m1099b(parcel, iM1109i);
                                break;
                        }
                    }
                    if (parcel.dataPosition() != iM1111j) {
                        throw new C0901a.a("Overread allowed size end=" + iM1111j, parcel);
                    }
                    return new MaskedWallet(iM1105f, strM1113l3, strM1113l2, strArrM1124w, strM1113l, address2, address, loyaltyWalletObjectArr, offerWalletObjectArr);
                }

                /* JADX INFO: renamed from: ay */
                public MaskedWallet[] m2362ay(int i) {
                    return new MaskedWallet[i];
                }

                @Override // android.os.Parcelable.Creator
                public /* synthetic */ MaskedWallet createFromParcel(Parcel x0) {
                    return m2361T(x0);
                }

                @Override // android.os.Parcelable.Creator
                public /* synthetic */ MaskedWallet[] newArray(int x0) {
                    return m2362ay(x0);
                }
            }
