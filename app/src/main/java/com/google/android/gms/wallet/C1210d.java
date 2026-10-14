            package com.google.android.gms.wallet;

            import android.os.Parcel;
            import android.os.Parcelable;
            import com.google.android.gms.common.internal.safeparcel.C0901a;
            import com.google.android.gms.common.internal.safeparcel.C0902b;
            
            /* JADX INFO: renamed from: com.google.android.gms.wallet.d */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public class C1210d implements Parcelable.Creator<FullWalletRequest> {
                /* JADX INFO: renamed from: a */
                static void m2351a(FullWalletRequest fullWalletRequest, Parcel parcel, int i) {
                    int iM1152k = C0902b.m1152k(parcel);
                    C0902b.m1150c(parcel, 1, fullWalletRequest.getVersionCode());
                    C0902b.m1140a(parcel, 2, fullWalletRequest.f2477tH, false);
                    C0902b.m1140a(parcel, 3, fullWalletRequest.f2478tI, false);
                    C0902b.m1139a(parcel, 4, (Parcelable) fullWalletRequest.f2479tO, i, false);
                    C0902b.m1131C(parcel, iM1152k);
                }

                /* JADX INFO: renamed from: Q */
                public FullWalletRequest m2352Q(Parcel parcel) {
                    Cart cart = null;
                    int iM1111j = C0901a.m1111j(parcel);
                    int iM1105f = 0;
                    String strM1113l = null;
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
                                cart = (Cart) C0901a.m1096a(parcel, iM1109i, Cart.CREATOR);
                                break;
                            default:
                                C0901a.m1099b(parcel, iM1109i);
                                break;
                        }
                    }
                    if (parcel.dataPosition() != iM1111j) {
                        throw new C0901a.a("Overread allowed size end=" + iM1111j, parcel);
                    }
                    return new FullWalletRequest(iM1105f, strM1113l2, strM1113l, cart);
                }

                /* JADX INFO: renamed from: av */
                public FullWalletRequest[] m2353av(int i) {
                    return new FullWalletRequest[i];
                }

                @Override // android.os.Parcelable.Creator
                public /* synthetic */ FullWalletRequest createFromParcel(Parcel x0) {
                    return m2352Q(x0);
                }

                @Override // android.os.Parcelable.Creator
                public /* synthetic */ FullWalletRequest[] newArray(int x0) {
                    return m2353av(x0);
                }
            }
