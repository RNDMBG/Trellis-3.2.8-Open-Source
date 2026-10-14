            package com.google.android.gms.wallet;

            import android.os.Parcel;
            import android.os.Parcelable;
            import com.google.android.gms.common.internal.safeparcel.C0901a;
            import com.google.android.gms.common.internal.safeparcel.C0902b;
            
            /* JADX INFO: renamed from: com.google.android.gms.wallet.a */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public class C1207a implements Parcelable.Creator<Address> {
                /* JADX INFO: renamed from: a */
                static void m2342a(Address address, Parcel parcel, int i) {
                    int iM1152k = C0902b.m1152k(parcel);
                    C0902b.m1150c(parcel, 1, address.getVersionCode());
                    C0902b.m1140a(parcel, 2, address.name, false);
                    C0902b.m1140a(parcel, 3, address.f2457tu, false);
                    C0902b.m1140a(parcel, 4, address.f2458tv, false);
                    C0902b.m1140a(parcel, 5, address.f2459tw, false);
                    C0902b.m1140a(parcel, 6, address.f2452hl, false);
                    C0902b.m1140a(parcel, 7, address.f2460tx, false);
                    C0902b.m1140a(parcel, 8, address.f2461ty, false);
                    C0902b.m1140a(parcel, 9, address.f2462tz, false);
                    C0902b.m1140a(parcel, 10, address.f2454tA, false);
                    C0902b.m1143a(parcel, 11, address.f2455tB);
                    C0902b.m1140a(parcel, 12, address.f2456tC, false);
                    C0902b.m1131C(parcel, iM1152k);
                }

                /* JADX INFO: renamed from: N */
                public Address m2343N(Parcel parcel) {
                    int iM1111j = C0901a.m1111j(parcel);
                    int iM1105f = 0;
                    String strM1113l = null;
                    String strM1113l2 = null;
                    String strM1113l3 = null;
                    String strM1113l4 = null;
                    String strM1113l5 = null;
                    String strM1113l6 = null;
                    String strM1113l7 = null;
                    String strM1113l8 = null;
                    String strM1113l9 = null;
                    boolean zM1102c = false;
                    String strM1113l10 = null;
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
                                strM1113l2 = C0901a.m1113l(parcel, iM1109i);
                                break;
                            case 4:
                                strM1113l3 = C0901a.m1113l(parcel, iM1109i);
                                break;
                            case 5:
                                strM1113l4 = C0901a.m1113l(parcel, iM1109i);
                                break;
                            case 6:
                                strM1113l5 = C0901a.m1113l(parcel, iM1109i);
                                break;
                            case 7:
                                strM1113l6 = C0901a.m1113l(parcel, iM1109i);
                                break;
                            case 8:
                                strM1113l7 = C0901a.m1113l(parcel, iM1109i);
                                break;
                            case 9:
                                strM1113l8 = C0901a.m1113l(parcel, iM1109i);
                                break;
                            case 10:
                                strM1113l9 = C0901a.m1113l(parcel, iM1109i);
                                break;
                            case 11:
                                zM1102c = C0901a.m1102c(parcel, iM1109i);
                                break;
                            case 12:
                                strM1113l10 = C0901a.m1113l(parcel, iM1109i);
                                break;
                            default:
                                C0901a.m1099b(parcel, iM1109i);
                                break;
                        }
                    }
                    if (parcel.dataPosition() != iM1111j) {
                        throw new C0901a.a("Overread allowed size end=" + iM1111j, parcel);
                    }
                    return new Address(iM1105f, strM1113l, strM1113l2, strM1113l3, strM1113l4, strM1113l5, strM1113l6, strM1113l7, strM1113l8, strM1113l9, zM1102c, strM1113l10);
                }

                /* JADX INFO: renamed from: as */
                public Address[] m2344as(int i) {
                    return new Address[i];
                }

                @Override // android.os.Parcelable.Creator
                public /* synthetic */ Address createFromParcel(Parcel x0) {
                    return m2343N(x0);
                }

                @Override // android.os.Parcelable.Creator
                public /* synthetic */ Address[] newArray(int x0) {
                    return m2344as(x0);
                }
            }
