            package com.google.android.gms.wallet;

            import android.os.Parcel;
            import android.os.Parcelable;
            import com.google.android.gms.common.internal.safeparcel.C0901a;
            import com.google.android.gms.common.internal.safeparcel.C0902b;
            
            /* JADX INFO: renamed from: com.google.android.gms.wallet.h */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public class C1214h implements Parcelable.Creator<MaskedWalletRequest> {
                /* JADX INFO: renamed from: a */
                static void m2363a(MaskedWalletRequest maskedWalletRequest, Parcel parcel, int i) {
                    int iM1152k = C0902b.m1152k(parcel);
                    C0902b.m1150c(parcel, 1, maskedWalletRequest.getVersionCode());
                    C0902b.m1140a(parcel, 2, maskedWalletRequest.f2505tI, false);
                    C0902b.m1143a(parcel, 3, maskedWalletRequest.f2507ub);
                    C0902b.m1143a(parcel, 4, maskedWalletRequest.f2508uc);
                    C0902b.m1143a(parcel, 5, maskedWalletRequest.f2509ud);
                    C0902b.m1140a(parcel, 6, maskedWalletRequest.f2510ue, false);
                    C0902b.m1140a(parcel, 7, maskedWalletRequest.f2504tE, false);
                    C0902b.m1140a(parcel, 8, maskedWalletRequest.f2511uf, false);
                    C0902b.m1139a(parcel, 9, (Parcelable) maskedWalletRequest.f2506tO, i, false);
                    C0902b.m1143a(parcel, 10, maskedWalletRequest.f2512ug);
                    C0902b.m1143a(parcel, 11, maskedWalletRequest.f2513uh);
                    C0902b.m1131C(parcel, iM1152k);
                }

                /* JADX INFO: renamed from: U */
                public MaskedWalletRequest m2364U(Parcel parcel) {
                    Cart cart = null;
                    boolean zM1102c = false;
                    int iM1111j = C0901a.m1111j(parcel);
                    boolean zM1102c2 = false;
                    String strM1113l = null;
                    String strM1113l2 = null;
                    String strM1113l3 = null;
                    boolean zM1102c3 = false;
                    boolean zM1102c4 = false;
                    boolean zM1102c5 = false;
                    String strM1113l4 = null;
                    int iM1105f = 0;
                    while (parcel.dataPosition() < iM1111j) {
                        int iM1109i = C0901a.m1109i(parcel);
                        switch (C0901a.m1126y(iM1109i)) {
                            case 1:
                                iM1105f = C0901a.m1105f(parcel, iM1109i);
                                break;
                            case 2:
                                strM1113l4 = C0901a.m1113l(parcel, iM1109i);
                                break;
                            case 3:
                                zM1102c5 = C0901a.m1102c(parcel, iM1109i);
                                break;
                            case 4:
                                zM1102c4 = C0901a.m1102c(parcel, iM1109i);
                                break;
                            case 5:
                                zM1102c3 = C0901a.m1102c(parcel, iM1109i);
                                break;
                            case 6:
                                strM1113l3 = C0901a.m1113l(parcel, iM1109i);
                                break;
                            case 7:
                                strM1113l2 = C0901a.m1113l(parcel, iM1109i);
                                break;
                            case 8:
                                strM1113l = C0901a.m1113l(parcel, iM1109i);
                                break;
                            case 9:
                                cart = (Cart) C0901a.m1096a(parcel, iM1109i, Cart.CREATOR);
                                break;
                            case 10:
                                zM1102c2 = C0901a.m1102c(parcel, iM1109i);
                                break;
                            case 11:
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
                    return new MaskedWalletRequest(iM1105f, strM1113l4, zM1102c5, zM1102c4, zM1102c3, strM1113l3, strM1113l2, strM1113l, cart, zM1102c2, zM1102c);
                }

                /* JADX INFO: renamed from: az */
                public MaskedWalletRequest[] m2365az(int i) {
                    return new MaskedWalletRequest[i];
                }

                @Override // android.os.Parcelable.Creator
                public /* synthetic */ MaskedWalletRequest createFromParcel(Parcel x0) {
                    return m2364U(x0);
                }

                @Override // android.os.Parcelable.Creator
                public /* synthetic */ MaskedWalletRequest[] newArray(int x0) {
                    return m2365az(x0);
                }
            }
