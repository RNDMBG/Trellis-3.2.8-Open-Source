            package com.google.android.gms.common.data;

            import android.database.CursorWindow;
            import android.os.Bundle;
            import android.os.Parcel;
            import android.os.Parcelable;
            import com.google.android.gms.common.internal.safeparcel.C0901a;
            import com.google.android.gms.common.internal.safeparcel.C0902b;
            
            /* JADX INFO: renamed from: com.google.android.gms.common.data.e */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public class C0892e implements Parcelable.Creator<C0891d> {
                /* JADX INFO: renamed from: a */
                static void m1053a(C0891d c0891d, Parcel parcel, int i) {
                    int iM1152k = C0902b.m1152k(parcel);
                    C0902b.m1146a(parcel, 1, c0891d.m1040aK(), false);
                    C0902b.m1150c(parcel, 1000, c0891d.getVersionCode());
                    C0902b.m1145a(parcel, 2, (Parcelable[]) c0891d.m1041aL(), i, false);
                    C0902b.m1150c(parcel, 3, c0891d.getStatusCode());
                    C0902b.m1136a(parcel, 4, c0891d.m1042aM(), false);
                    C0902b.m1131C(parcel, iM1152k);
                }

                @Override // android.os.Parcelable.Creator
                public /* synthetic */ C0891d createFromParcel(Parcel x0) {
                    return m1054h(x0);
                }

                /* JADX INFO: renamed from: h */
                public C0891d m1054h(Parcel parcel) {
                    int iM1105f = 0;
                    Bundle bundleM1115n = null;
                    int iM1111j = C0901a.m1111j(parcel);
                    CursorWindow[] cursorWindowArr = null;
                    String[] strArrM1124w = null;
                    int iM1105f2 = 0;
                    while (parcel.dataPosition() < iM1111j) {
                        int iM1109i = C0901a.m1109i(parcel);
                        switch (C0901a.m1126y(iM1109i)) {
                            case 1:
                                strArrM1124w = C0901a.m1124w(parcel, iM1109i);
                                break;
                            case 2:
                                cursorWindowArr = (CursorWindow[]) C0901a.m1100b(parcel, iM1109i, CursorWindow.CREATOR);
                                break;
                            case 3:
                                iM1105f = C0901a.m1105f(parcel, iM1109i);
                                break;
                            case 4:
                                bundleM1115n = C0901a.m1115n(parcel, iM1109i);
                                break;
                            case 1000:
                                iM1105f2 = C0901a.m1105f(parcel, iM1109i);
                                break;
                            default:
                                C0901a.m1099b(parcel, iM1109i);
                                break;
                        }
                    }
                    if (parcel.dataPosition() != iM1111j) {
                        throw new C0901a.a("Overread allowed size end=" + iM1111j, parcel);
                    }
                    C0891d c0891d = new C0891d(iM1105f2, strArrM1124w, cursorWindowArr, iM1105f, bundleM1115n);
                    c0891d.m1039aJ();
                    return c0891d;
                }

                @Override // android.os.Parcelable.Creator
                public /* synthetic */ C0891d[] newArray(int x0) {
                    return m1055s(x0);
                }

                /* JADX INFO: renamed from: s */
                public C0891d[] m1055s(int i) {
                    return new C0891d[i];
                }
            }
