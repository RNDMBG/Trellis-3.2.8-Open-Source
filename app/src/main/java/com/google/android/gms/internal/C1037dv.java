            package com.google.android.gms.internal;

            import android.os.Parcel;
            import android.os.Parcelable;
            import com.google.android.gms.common.internal.safeparcel.C0901a;
            import com.google.android.gms.common.internal.safeparcel.C0902b;
            
            /* JADX INFO: renamed from: com.google.android.gms.internal.dv */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public class C1037dv implements Parcelable.Creator<C1035dt.a> {
                /* JADX INFO: renamed from: a */
                static void m1636a(C1035dt.a aVar, Parcel parcel, int i) {
                    int iM1152k = C0902b.m1152k(parcel);
                    C0902b.m1150c(parcel, 1, aVar.versionCode);
                    C0902b.m1140a(parcel, 2, aVar.f1733lx, false);
                    C0902b.m1150c(parcel, 3, aVar.f1734ly);
                    C0902b.m1131C(parcel, iM1152k);
                }

                /* JADX INFO: renamed from: B */
                public C1035dt.a[] m1637B(int i) {
                    return new C1035dt.a[i];
                }

                @Override // android.os.Parcelable.Creator
                public /* synthetic */ C1035dt.a createFromParcel(Parcel x0) {
                    return m1638n(x0);
                }

                /* JADX INFO: renamed from: n */
                public C1035dt.a m1638n(Parcel parcel) {
                    int iM1105f = 0;
                    int iM1111j = C0901a.m1111j(parcel);
                    String strM1113l = null;
                    int iM1105f2 = 0;
                    while (parcel.dataPosition() < iM1111j) {
                        int iM1109i = C0901a.m1109i(parcel);
                        switch (C0901a.m1126y(iM1109i)) {
                            case 1:
                                iM1105f2 = C0901a.m1105f(parcel, iM1109i);
                                break;
                            case 2:
                                strM1113l = C0901a.m1113l(parcel, iM1109i);
                                break;
                            case 3:
                                iM1105f = C0901a.m1105f(parcel, iM1109i);
                                break;
                            default:
                                C0901a.m1099b(parcel, iM1109i);
                                break;
                        }
                    }
                    if (parcel.dataPosition() != iM1111j) {
                        throw new C0901a.a("Overread allowed size end=" + iM1111j, parcel);
                    }
                    return new C1035dt.a(iM1105f2, strM1113l, iM1105f);
                }

                @Override // android.os.Parcelable.Creator
                public /* synthetic */ C1035dt.a[] newArray(int x0) {
                    return m1637B(x0);
                }
            }
