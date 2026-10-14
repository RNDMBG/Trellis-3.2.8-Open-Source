            package com.google.android.gms.internal;

            import android.os.Parcel;
            import android.os.Parcelable;
            import com.google.android.gms.common.internal.safeparcel.C0901a;
            import com.google.android.gms.common.internal.safeparcel.C0902b;
            
            /* JADX INFO: renamed from: com.google.android.gms.internal.dy */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public class C1040dy implements Parcelable.Creator<C1041dz.b> {
                /* JADX INFO: renamed from: a */
                static void m1677a(C1041dz.b bVar, Parcel parcel, int i) {
                    int iM1152k = C0902b.m1152k(parcel);
                    C0902b.m1150c(parcel, 1, bVar.versionCode);
                    C0902b.m1140a(parcel, 2, bVar.f1751lN, false);
                    C0902b.m1139a(parcel, 3, (Parcelable) bVar.f1752lO, i, false);
                    C0902b.m1131C(parcel, iM1152k);
                }

                /* JADX INFO: renamed from: D */
                public C1041dz.b[] m1678D(int i) {
                    return new C1041dz.b[i];
                }

                @Override // android.os.Parcelable.Creator
                public /* synthetic */ C1041dz.b createFromParcel(Parcel x0) {
                    return m1679p(x0);
                }

                @Override // android.os.Parcelable.Creator
                public /* synthetic */ C1041dz.b[] newArray(int x0) {
                    return m1678D(x0);
                }

                /* JADX INFO: renamed from: p */
                public C1041dz.b m1679p(Parcel parcel) {
                    AbstractC1038dw.a aVar = null;
                    int iM1111j = C0901a.m1111j(parcel);
                    int iM1105f = 0;
                    String strM1113l = null;
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
                                aVar = (AbstractC1038dw.a) C0901a.m1096a(parcel, iM1109i, AbstractC1038dw.a.CREATOR);
                                break;
                            default:
                                C0901a.m1099b(parcel, iM1109i);
                                break;
                        }
                    }
                    if (parcel.dataPosition() != iM1111j) {
                        throw new C0901a.a("Overread allowed size end=" + iM1111j, parcel);
                    }
                    return new C1041dz.b(iM1105f, strM1113l, aVar);
                }
            }
