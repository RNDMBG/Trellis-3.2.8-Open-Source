            package com.google.android.gms.wallet;

            import android.os.Parcel;
            import android.os.Parcelable;
            import com.google.android.gms.common.internal.safeparcel.C0901a;
            import com.google.android.gms.common.internal.safeparcel.C0902b;
            
            /* JADX INFO: renamed from: com.google.android.gms.wallet.f */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public class C1212f implements Parcelable.Creator<LoyaltyWalletObject> {
                /* JADX INFO: renamed from: a */
                static void m2357a(LoyaltyWalletObject loyaltyWalletObject, Parcel parcel, int i) {
                    int iM1152k = C0902b.m1152k(parcel);
                    C0902b.m1150c(parcel, 1, loyaltyWalletObject.getVersionCode());
                    C0902b.m1140a(parcel, 2, loyaltyWalletObject.f2489tU, false);
                    C0902b.m1140a(parcel, 3, loyaltyWalletObject.f2490tV, false);
                    C0902b.m1140a(parcel, 4, loyaltyWalletObject.f2491tW, false);
                    C0902b.m1140a(parcel, 5, loyaltyWalletObject.f2492tX, false);
                    C0902b.m1140a(parcel, 6, loyaltyWalletObject.f2493tY, false);
                    C0902b.m1131C(parcel, iM1152k);
                }

                /* JADX INFO: renamed from: S */
                public LoyaltyWalletObject m2358S(Parcel parcel) {
                    String strM1113l = null;
                    int iM1111j = C0901a.m1111j(parcel);
                    int iM1105f = 0;
                    String strM1113l2 = null;
                    String strM1113l3 = null;
                    String strM1113l4 = null;
                    String strM1113l5 = null;
                    while (parcel.dataPosition() < iM1111j) {
                        int iM1109i = C0901a.m1109i(parcel);
                        switch (C0901a.m1126y(iM1109i)) {
                            case 1:
                                iM1105f = C0901a.m1105f(parcel, iM1109i);
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
                    return new LoyaltyWalletObject(iM1105f, strM1113l5, strM1113l4, strM1113l3, strM1113l2, strM1113l);
                }

                /* JADX INFO: renamed from: ax */
                public LoyaltyWalletObject[] m2359ax(int i) {
                    return new LoyaltyWalletObject[i];
                }

                @Override // android.os.Parcelable.Creator
                public /* synthetic */ LoyaltyWalletObject createFromParcel(Parcel x0) {
                    return m2358S(x0);
                }

                @Override // android.os.Parcelable.Creator
                public /* synthetic */ LoyaltyWalletObject[] newArray(int x0) {
                    return m2359ax(x0);
                }
            }
