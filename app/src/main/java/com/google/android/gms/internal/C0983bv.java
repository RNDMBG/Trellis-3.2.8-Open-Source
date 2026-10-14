            package com.google.android.gms.internal;

            import android.content.pm.ApplicationInfo;
            import android.content.pm.PackageInfo;
            import android.os.Bundle;
            import android.os.Parcel;
            import android.os.Parcelable;
            import com.google.android.gms.common.internal.safeparcel.C0901a;
            import com.google.android.gms.common.internal.safeparcel.C0902b;
            
            /* JADX INFO: renamed from: com.google.android.gms.internal.bv */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public class C0983bv implements Parcelable.Creator<C0982bu> {
                /* JADX INFO: renamed from: a */
                static void m1371a(C0982bu c0982bu, Parcel parcel, int i) {
                    int iM1152k = C0902b.m1152k(parcel);
                    C0902b.m1150c(parcel, 1, c0982bu.versionCode);
                    C0902b.m1136a(parcel, 2, c0982bu.f1495gA, false);
                    C0902b.m1139a(parcel, 3, (Parcelable) c0982bu.f1496gB, i, false);
                    C0902b.m1139a(parcel, 4, (Parcelable) c0982bu.f1493ed, i, false);
                    C0902b.m1140a(parcel, 5, c0982bu.adUnitId, false);
                    C0902b.m1139a(parcel, 6, (Parcelable) c0982bu.applicationInfo, i, false);
                    C0902b.m1139a(parcel, 7, (Parcelable) c0982bu.f1497gC, i, false);
                    C0902b.m1140a(parcel, 8, c0982bu.f1498gD, false);
                    C0902b.m1140a(parcel, 9, c0982bu.f1499gE, false);
                    C0902b.m1140a(parcel, 10, c0982bu.f1500gF, false);
                    C0902b.m1139a(parcel, 11, (Parcelable) c0982bu.f1494eg, i, false);
                    C0902b.m1131C(parcel, iM1152k);
                }

                @Override // android.os.Parcelable.Creator
                public /* synthetic */ C0982bu createFromParcel(Parcel x0) {
                    return m1372e(x0);
                }

                /* JADX INFO: renamed from: e */
                public C0982bu m1372e(Parcel parcel) {
                    C1003co c1003co = null;
                    int iM1111j = C0901a.m1111j(parcel);
                    int iM1105f = 0;
                    String strM1113l = null;
                    String strM1113l2 = null;
                    String strM1113l3 = null;
                    PackageInfo packageInfo = null;
                    ApplicationInfo applicationInfo = null;
                    String strM1113l4 = null;
                    C1126x c1126x = null;
                    C1124v c1124v = null;
                    Bundle bundleM1115n = null;
                    while (parcel.dataPosition() < iM1111j) {
                        int iM1109i = C0901a.m1109i(parcel);
                        switch (C0901a.m1126y(iM1109i)) {
                            case 1:
                                iM1105f = C0901a.m1105f(parcel, iM1109i);
                                break;
                            case 2:
                                bundleM1115n = C0901a.m1115n(parcel, iM1109i);
                                break;
                            case 3:
                                c1124v = (C1124v) C0901a.m1096a(parcel, iM1109i, C1124v.CREATOR);
                                break;
                            case 4:
                                c1126x = (C1126x) C0901a.m1096a(parcel, iM1109i, C1126x.CREATOR);
                                break;
                            case 5:
                                strM1113l4 = C0901a.m1113l(parcel, iM1109i);
                                break;
                            case 6:
                                applicationInfo = (ApplicationInfo) C0901a.m1096a(parcel, iM1109i, ApplicationInfo.CREATOR);
                                break;
                            case 7:
                                packageInfo = (PackageInfo) C0901a.m1096a(parcel, iM1109i, PackageInfo.CREATOR);
                                break;
                            case 8:
                                strM1113l3 = C0901a.m1113l(parcel, iM1109i);
                                break;
                            case 9:
                                strM1113l2 = C0901a.m1113l(parcel, iM1109i);
                                break;
                            case 10:
                                strM1113l = C0901a.m1113l(parcel, iM1109i);
                                break;
                            case 11:
                                c1003co = (C1003co) C0901a.m1096a(parcel, iM1109i, C1003co.CREATOR);
                                break;
                            default:
                                C0901a.m1099b(parcel, iM1109i);
                                break;
                        }
                    }
                    if (parcel.dataPosition() != iM1111j) {
                        throw new C0901a.a("Overread allowed size end=" + iM1111j, parcel);
                    }
                    return new C0982bu(iM1105f, bundleM1115n, c1124v, c1126x, strM1113l4, applicationInfo, packageInfo, strM1113l3, strM1113l2, strM1113l, c1003co);
                }

                /* JADX INFO: renamed from: i */
                public C0982bu[] m1373i(int i) {
                    return new C0982bu[i];
                }

                @Override // android.os.Parcelable.Creator
                public /* synthetic */ C0982bu[] newArray(int x0) {
                    return m1373i(x0);
                }
            }
