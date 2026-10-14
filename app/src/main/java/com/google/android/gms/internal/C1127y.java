            package com.google.android.gms.internal;

            import android.os.Parcel;
            import android.os.Parcelable;
            import com.google.android.gms.common.internal.safeparcel.C0901a;
            import com.google.android.gms.common.internal.safeparcel.C0902b;
            
            /* JADX INFO: renamed from: com.google.android.gms.internal.y */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public class C1127y implements Parcelable.Creator<C1126x> {
                /* JADX INFO: renamed from: a */
                static void m2181a(C1126x c1126x, Parcel parcel, int i) {
                    int iM1152k = C0902b.m1152k(parcel);
                    C0902b.m1150c(parcel, 1, c1126x.versionCode);
                    C0902b.m1140a(parcel, 2, c1126x.f2163ew, false);
                    C0902b.m1150c(parcel, 3, c1126x.height);
                    C0902b.m1150c(parcel, 4, c1126x.heightPixels);
                    C0902b.m1143a(parcel, 5, c1126x.f2164ex);
                    C0902b.m1150c(parcel, 6, c1126x.width);
                    C0902b.m1150c(parcel, 7, c1126x.widthPixels);
                    C0902b.m1131C(parcel, iM1152k);
                }

                /* JADX INFO: renamed from: b */
                public C1126x m2182b(Parcel parcel) {
                    int iM1105f = 0;
                    int iM1111j = C0901a.m1111j(parcel);
                    String strM1113l = null;
                    int iM1105f2 = 0;
                    boolean zM1102c = false;
                    int iM1105f3 = 0;
                    int iM1105f4 = 0;
                    int iM1105f5 = 0;
                    while (parcel.dataPosition() < iM1111j) {
                        int iM1109i = C0901a.m1109i(parcel);
                        switch (C0901a.m1126y(iM1109i)) {
                            case 1:
                                iM1105f5 = C0901a.m1105f(parcel, iM1109i);
                                break;
                            case 2:
                                strM1113l = C0901a.m1113l(parcel, iM1109i);
                                break;
                            case 3:
                                iM1105f4 = C0901a.m1105f(parcel, iM1109i);
                                break;
                            case 4:
                                iM1105f3 = C0901a.m1105f(parcel, iM1109i);
                                break;
                            case 5:
                                zM1102c = C0901a.m1102c(parcel, iM1109i);
                                break;
                            case 6:
                                iM1105f2 = C0901a.m1105f(parcel, iM1109i);
                                break;
                            case 7:
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
                    return new C1126x(iM1105f5, strM1113l, iM1105f4, iM1105f3, zM1102c, iM1105f2, iM1105f);
                }

                /* JADX INFO: renamed from: c */
                public C1126x[] m2183c(int i) {
                    return new C1126x[i];
                }

                @Override // android.os.Parcelable.Creator
                public /* synthetic */ C1126x createFromParcel(Parcel x0) {
                    return m2182b(x0);
                }

                @Override // android.os.Parcelable.Creator
                public /* synthetic */ C1126x[] newArray(int x0) {
                    return m2183c(x0);
                }
            }
