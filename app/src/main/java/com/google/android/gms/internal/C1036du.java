            package com.google.android.gms.internal;

            import android.os.Parcel;
            import android.os.Parcelable;
            import com.google.android.gms.common.internal.safeparcel.C0901a;
            import com.google.android.gms.common.internal.safeparcel.C0902b;
            import java.util.ArrayList;
            
            /* JADX INFO: renamed from: com.google.android.gms.internal.du */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public class C1036du implements Parcelable.Creator<C1035dt> {
                /* JADX INFO: renamed from: a */
                static void m1633a(C1035dt c1035dt, Parcel parcel, int i) {
                    int iM1152k = C0902b.m1152k(parcel);
                    C0902b.m1150c(parcel, 1, c1035dt.getVersionCode());
                    C0902b.m1149b(parcel, 2, c1035dt.m1628bm(), false);
                    C0902b.m1131C(parcel, iM1152k);
                }

                /* JADX INFO: renamed from: A */
                public C1035dt[] m1634A(int i) {
                    return new C1035dt[i];
                }

                @Override // android.os.Parcelable.Creator
                public /* synthetic */ C1035dt createFromParcel(Parcel x0) {
                    return m1635m(x0);
                }

                /* JADX INFO: renamed from: m */
                public C1035dt m1635m(Parcel parcel) {
                    int iM1111j = C0901a.m1111j(parcel);
                    int iM1105f = 0;
                    ArrayList arrayListM1101c = null;
                    while (parcel.dataPosition() < iM1111j) {
                        int iM1109i = C0901a.m1109i(parcel);
                        switch (C0901a.m1126y(iM1109i)) {
                            case 1:
                                iM1105f = C0901a.m1105f(parcel, iM1109i);
                                break;
                            case 2:
                                arrayListM1101c = C0901a.m1101c(parcel, iM1109i, C1035dt.a.CREATOR);
                                break;
                            default:
                                C0901a.m1099b(parcel, iM1109i);
                                break;
                        }
                    }
                    if (parcel.dataPosition() != iM1111j) {
                        throw new C0901a.a("Overread allowed size end=" + iM1111j, parcel);
                    }
                    return new C1035dt(iM1105f, arrayListM1101c);
                }

                @Override // android.os.Parcelable.Creator
                public /* synthetic */ C1035dt[] newArray(int x0) {
                    return m1634A(x0);
                }
            }
