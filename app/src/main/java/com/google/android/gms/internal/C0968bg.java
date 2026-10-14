            package com.google.android.gms.internal;

            import android.os.IBinder;
            import android.os.Parcel;
            import android.os.Parcelable;
            import com.google.android.gms.common.internal.safeparcel.C0901a;
            import com.google.android.gms.common.internal.safeparcel.C0902b;
            
            
            
            public class C0968bg implements Parcelable.Creator<C0969bh> {
                
                static void m1313a(C0969bh c0969bh, Parcel parcel, int i) {
                    int iM1152k = C0902b.m1152k(parcel);
                    C0902b.m1150c(parcel, 1, c0969bh.versionCode);
                    C0902b.m1139a(parcel, 2, (Parcelable) c0969bh.f1443fR, i, false);
                    C0902b.m1137a(parcel, 3, c0969bh.m1318U(), false);
                    C0902b.m1137a(parcel, 4, c0969bh.m1319V(), false);
                    C0902b.m1137a(parcel, 5, c0969bh.m1320W(), false);
                    C0902b.m1137a(parcel, 6, c0969bh.m1321X(), false);
                    C0902b.m1140a(parcel, 7, c0969bh.f1448fW, false);
                    C0902b.m1143a(parcel, 8, c0969bh.f1449fX);
                    C0902b.m1140a(parcel, 9, c0969bh.f1450fY, false);
                    C0902b.m1137a(parcel, 10, c0969bh.m1322Y(), false);
                    C0902b.m1150c(parcel, 11, c0969bh.orientation);
                    C0902b.m1150c(parcel, 12, c0969bh.f1453ga);
                    C0902b.m1140a(parcel, 13, c0969bh.f1452fz, false);
                    C0902b.m1139a(parcel, 14, (Parcelable) c0969bh.f1442eg, i, false);
                    C0902b.m1131C(parcel, iM1152k);
                }

                @Override 
                public  C0969bh createFromParcel(Parcel x0) {
                    return m1314d(x0);
                }

                
                public C0969bh m1314d(Parcel parcel) {
                    int iM1111j = C0901a.m1111j(parcel);
                    int iM1105f = 0;
                    C0966be c0966be = null;
                    IBinder iBinderM1114m = null;
                    IBinder iBinderM1114m2 = null;
                    IBinder iBinderM1114m3 = null;
                    IBinder iBinderM1114m4 = null;
                    String strM1113l = null;
                    boolean zM1102c = false;
                    String strM1113l2 = null;
                    IBinder iBinderM1114m5 = null;
                    int iM1105f2 = 0;
                    int iM1105f3 = 0;
                    String strM1113l3 = null;
                    C1003co c1003co = null;
                    while (parcel.dataPosition() < iM1111j) {
                        int iM1109i = C0901a.m1109i(parcel);
                        switch (C0901a.m1126y(iM1109i)) {
                            case 1:
                                iM1105f = C0901a.m1105f(parcel, iM1109i);
                                break;
                            case 2:
                                c0966be = (C0966be) C0901a.m1096a(parcel, iM1109i, C0966be.CREATOR);
                                break;
                            case 3:
                                iBinderM1114m = C0901a.m1114m(parcel, iM1109i);
                                break;
                            case 4:
                                iBinderM1114m2 = C0901a.m1114m(parcel, iM1109i);
                                break;
                            case 5:
                                iBinderM1114m3 = C0901a.m1114m(parcel, iM1109i);
                                break;
                            case 6:
                                iBinderM1114m4 = C0901a.m1114m(parcel, iM1109i);
                                break;
                            case 7:
                                strM1113l = C0901a.m1113l(parcel, iM1109i);
                                break;
                            case 8:
                                zM1102c = C0901a.m1102c(parcel, iM1109i);
                                break;
                            case 9:
                                strM1113l2 = C0901a.m1113l(parcel, iM1109i);
                                break;
                            case 10:
                                iBinderM1114m5 = C0901a.m1114m(parcel, iM1109i);
                                break;
                            case 11:
                                iM1105f2 = C0901a.m1105f(parcel, iM1109i);
                                break;
                            case 12:
                                iM1105f3 = C0901a.m1105f(parcel, iM1109i);
                                break;
                            case 13:
                                strM1113l3 = C0901a.m1113l(parcel, iM1109i);
                                break;
                            case 14:
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
                    return new C0969bh(iM1105f, c0966be, iBinderM1114m, iBinderM1114m2, iBinderM1114m3, iBinderM1114m4, strM1113l, zM1102c, strM1113l2, iBinderM1114m5, iM1105f2, iM1105f3, strM1113l3, c1003co);
                }

                
                public C0969bh[] m1315h(int i) {
                    return new C0969bh[i];
                }

                @Override 
                public  C0969bh[] newArray(int x0) {
                    return m1315h(x0);
                }
            }
