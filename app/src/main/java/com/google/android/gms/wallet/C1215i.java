            package com.google.android.gms.wallet;

            import android.os.Parcel;
            import android.os.Parcelable;
            import com.google.android.gms.common.internal.safeparcel.C0901a;
            import com.google.android.gms.common.internal.safeparcel.C0902b;
            
            /* JADX INFO: renamed from: com.google.android.gms.wallet.i */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public class C1215i implements Parcelable.Creator<NotifyTransactionStatusRequest> {
                /* JADX INFO: renamed from: a */
                static void m2366a(NotifyTransactionStatusRequest notifyTransactionStatusRequest, Parcel parcel, int i) {
                    int iM1152k = C0902b.m1152k(parcel);
                    C0902b.m1150c(parcel, 1, notifyTransactionStatusRequest.f2515iM);
                    C0902b.m1140a(parcel, 2, notifyTransactionStatusRequest.f2516tH, false);
                    C0902b.m1150c(parcel, 3, notifyTransactionStatusRequest.status);
                    C0902b.m1140a(parcel, 4, notifyTransactionStatusRequest.f2517uj, false);
                    C0902b.m1131C(parcel, iM1152k);
                }

                /* JADX INFO: renamed from: V */
                public NotifyTransactionStatusRequest m2367V(Parcel parcel) {
                    String strM1113l = null;
                    int iM1105f = 0;
                    int iM1111j = C0901a.m1111j(parcel);
                    String strM1113l2 = null;
                    int iM1105f2 = 0;
                    while (parcel.dataPosition() < iM1111j) {
                        int iM1109i = C0901a.m1109i(parcel);
                        switch (C0901a.m1126y(iM1109i)) {
                            case 1:
                                iM1105f2 = C0901a.m1105f(parcel, iM1109i);
                                break;
                            case 2:
                                strM1113l2 = C0901a.m1113l(parcel, iM1109i);
                                break;
                            case 3:
                                iM1105f = C0901a.m1105f(parcel, iM1109i);
                                break;
                            case 4:
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
                    return new NotifyTransactionStatusRequest(iM1105f2, strM1113l2, iM1105f, strM1113l);
                }

                /* JADX INFO: renamed from: aA */
                public NotifyTransactionStatusRequest[] m2368aA(int i) {
                    return new NotifyTransactionStatusRequest[i];
                }

                @Override // android.os.Parcelable.Creator
                public /* synthetic */ NotifyTransactionStatusRequest createFromParcel(Parcel x0) {
                    return m2367V(x0);
                }

                @Override // android.os.Parcelable.Creator
                public /* synthetic */ NotifyTransactionStatusRequest[] newArray(int x0) {
                    return m2368aA(x0);
                }
            }
