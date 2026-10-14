            package com.google.android.gms.internal;

            import android.os.Parcel;
            import android.os.Parcelable;
            import com.google.android.gms.common.internal.safeparcel.C0901a;
            import com.google.android.gms.common.internal.safeparcel.C0902b;
            import java.util.ArrayList;
            
            /* JADX INFO: renamed from: com.google.android.gms.internal.bx */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public class C0985bx implements Parcelable.Creator<C0984bw> {
                /* JADX INFO: renamed from: a */
                static void m1374a(C0984bw c0984bw, Parcel parcel, int i) {
                    int iM1152k = C0902b.m1152k(parcel);
                    C0902b.m1150c(parcel, 1, c0984bw.versionCode);
                    C0902b.m1140a(parcel, 2, c0984bw.f1510fW, false);
                    C0902b.m1140a(parcel, 3, c0984bw.f1512gG, false);
                    C0902b.m1141a(parcel, 4, c0984bw.f1508eW, false);
                    C0902b.m1150c(parcel, 5, c0984bw.errorCode);
                    C0902b.m1141a(parcel, 6, c0984bw.f1509eX, false);
                    C0902b.m1135a(parcel, 7, c0984bw.f1513gH);
                    C0902b.m1143a(parcel, 8, c0984bw.f1514gI);
                    C0902b.m1135a(parcel, 9, c0984bw.f1515gJ);
                    C0902b.m1141a(parcel, 10, c0984bw.f1516gK, false);
                    C0902b.m1135a(parcel, 11, c0984bw.f1511fa);
                    C0902b.m1150c(parcel, 12, c0984bw.orientation);
                    C0902b.m1131C(parcel, iM1152k);
                }

                @Override // android.os.Parcelable.Creator
                public /* synthetic */ C0984bw createFromParcel(Parcel x0) {
                    return m1375f(x0);
                }

                /* JADX INFO: renamed from: f */
                public C0984bw m1375f(Parcel parcel) {
                    int iM1111j = C0901a.m1111j(parcel);
                    int iM1105f = 0;
                    String strM1113l = null;
                    String strM1113l2 = null;
                    ArrayList<String> arrayListM1125x = null;
                    int iM1105f2 = 0;
                    ArrayList<String> arrayListM1125x2 = null;
                    long jM1106g = 0;
                    boolean zM1102c = false;
                    long jM1106g2 = 0;
                    ArrayList<String> arrayListM1125x3 = null;
                    long jM1106g3 = 0;
                    int iM1105f3 = 0;
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
                                arrayListM1125x = C0901a.m1125x(parcel, iM1109i);
                                break;
                            case 5:
                                iM1105f2 = C0901a.m1105f(parcel, iM1109i);
                                break;
                            case 6:
                                arrayListM1125x2 = C0901a.m1125x(parcel, iM1109i);
                                break;
                            case 7:
                                jM1106g = C0901a.m1106g(parcel, iM1109i);
                                break;
                            case 8:
                                zM1102c = C0901a.m1102c(parcel, iM1109i);
                                break;
                            case 9:
                                jM1106g2 = C0901a.m1106g(parcel, iM1109i);
                                break;
                            case 10:
                                arrayListM1125x3 = C0901a.m1125x(parcel, iM1109i);
                                break;
                            case 11:
                                jM1106g3 = C0901a.m1106g(parcel, iM1109i);
                                break;
                            case 12:
                                iM1105f3 = C0901a.m1105f(parcel, iM1109i);
                                break;
                            default:
                                C0901a.m1099b(parcel, iM1109i);
                                break;
                        }
                    }
                    if (parcel.dataPosition() != iM1111j) {
                        throw new C0901a.a("Overread allowed size end=" + iM1111j, parcel);
                    }
                    return new C0984bw(iM1105f, strM1113l, strM1113l2, arrayListM1125x, iM1105f2, arrayListM1125x2, jM1106g, zM1102c, jM1106g2, arrayListM1125x3, jM1106g3, iM1105f3);
                }

                /* JADX INFO: renamed from: j */
                public C0984bw[] m1376j(int i) {
                    return new C0984bw[i];
                }

                @Override // android.os.Parcelable.Creator
                public /* synthetic */ C0984bw[] newArray(int x0) {
                    return m1376j(x0);
                }
            }
