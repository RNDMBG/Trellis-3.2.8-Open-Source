            package com.google.android.gms.internal;

            import android.content.Intent;
            import android.os.Bundle;
            import android.os.IBinder;
            import android.os.Parcel;
            import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
            import com.google.android.gms.dynamic.BinderC0905c;
            import com.google.android.gms.dynamic.InterfaceC0904b;
            
            
            
            public final class C0969bh implements SafeParcelable {
                public static final C0968bg CREATOR = new C0968bg();

                
                public final C1003co f1442eg;

                
                public final C0966be f1443fR;

                
                public final InterfaceC1119q f1444fS;

                
                public final InterfaceC0970bi f1445fT;

                
                public final C1005cq f1446fU;

                
                public final InterfaceC0941ag f1447fV;

                
                public final String f1448fW;

                
                public final boolean f1449fX;

                
                public final String f1450fY;

                
                public final InterfaceC0973bl f1451fZ;

                
                public final String f1452fz;

                
                public final int f1453ga;
                public final int orientation;
                public final int versionCode;

                C0969bh(int i, C0966be c0966be, IBinder iBinder, IBinder iBinder2, IBinder iBinder3, IBinder iBinder4, String str, boolean z, String str2, IBinder iBinder5, int i2, int i3, String str3, C1003co c1003co) {
                    this.versionCode = i;
                    this.f1443fR = c0966be;
                    this.f1444fS = (InterfaceC1119q) BinderC0905c.m1165b(InterfaceC0904b.a.m1164z(iBinder));
                    this.f1445fT = (InterfaceC0970bi) BinderC0905c.m1165b(InterfaceC0904b.a.m1164z(iBinder2));
                    this.f1446fU = (C1005cq) BinderC0905c.m1165b(InterfaceC0904b.a.m1164z(iBinder3));
                    this.f1447fV = (InterfaceC0941ag) BinderC0905c.m1165b(InterfaceC0904b.a.m1164z(iBinder4));
                    this.f1448fW = str;
                    this.f1449fX = z;
                    this.f1450fY = str2;
                    this.f1451fZ = (InterfaceC0973bl) BinderC0905c.m1165b(InterfaceC0904b.a.m1164z(iBinder5));
                    this.orientation = i2;
                    this.f1453ga = i3;
                    this.f1452fz = str3;
                    this.f1442eg = c1003co;
                }

                public C0969bh(C0966be c0966be, InterfaceC1119q interfaceC1119q, InterfaceC0970bi interfaceC0970bi, InterfaceC0973bl interfaceC0973bl, C1003co c1003co) {
                    this.versionCode = 1;
                    this.f1443fR = c0966be;
                    this.f1444fS = interfaceC1119q;
                    this.f1445fT = interfaceC0970bi;
                    this.f1446fU = null;
                    this.f1447fV = null;
                    this.f1448fW = null;
                    this.f1449fX = false;
                    this.f1450fY = null;
                    this.f1451fZ = interfaceC0973bl;
                    this.orientation = -1;
                    this.f1453ga = 4;
                    this.f1452fz = null;
                    this.f1442eg = c1003co;
                }

                public C0969bh(InterfaceC1119q interfaceC1119q, InterfaceC0970bi interfaceC0970bi, InterfaceC0941ag interfaceC0941ag, InterfaceC0973bl interfaceC0973bl, C1005cq c1005cq, boolean z, int i, String str, C1003co c1003co) {
                    this.versionCode = 1;
                    this.f1443fR = null;
                    this.f1444fS = interfaceC1119q;
                    this.f1445fT = interfaceC0970bi;
                    this.f1446fU = c1005cq;
                    this.f1447fV = interfaceC0941ag;
                    this.f1448fW = null;
                    this.f1449fX = z;
                    this.f1450fY = null;
                    this.f1451fZ = interfaceC0973bl;
                    this.orientation = i;
                    this.f1453ga = 3;
                    this.f1452fz = str;
                    this.f1442eg = c1003co;
                }

                public C0969bh(InterfaceC1119q interfaceC1119q, InterfaceC0970bi interfaceC0970bi, InterfaceC0941ag interfaceC0941ag, InterfaceC0973bl interfaceC0973bl, C1005cq c1005cq, boolean z, int i, String str, String str2, C1003co c1003co) {
                    this.versionCode = 1;
                    this.f1443fR = null;
                    this.f1444fS = interfaceC1119q;
                    this.f1445fT = interfaceC0970bi;
                    this.f1446fU = c1005cq;
                    this.f1447fV = interfaceC0941ag;
                    this.f1448fW = str2;
                    this.f1449fX = z;
                    this.f1450fY = str;
                    this.f1451fZ = interfaceC0973bl;
                    this.orientation = i;
                    this.f1453ga = 3;
                    this.f1452fz = null;
                    this.f1442eg = c1003co;
                }

                public C0969bh(InterfaceC1119q interfaceC1119q, InterfaceC0970bi interfaceC0970bi, InterfaceC0973bl interfaceC0973bl, C1005cq c1005cq, int i, C1003co c1003co) {
                    this.versionCode = 1;
                    this.f1443fR = null;
                    this.f1444fS = interfaceC1119q;
                    this.f1445fT = interfaceC0970bi;
                    this.f1446fU = c1005cq;
                    this.f1447fV = null;
                    this.f1448fW = null;
                    this.f1449fX = false;
                    this.f1450fY = null;
                    this.f1451fZ = interfaceC0973bl;
                    this.orientation = i;
                    this.f1453ga = 1;
                    this.f1452fz = null;
                    this.f1442eg = c1003co;
                }

                public C0969bh(InterfaceC1119q interfaceC1119q, InterfaceC0970bi interfaceC0970bi, InterfaceC0973bl interfaceC0973bl, C1005cq c1005cq, boolean z, int i, C1003co c1003co) {
                    this.versionCode = 1;
                    this.f1443fR = null;
                    this.f1444fS = interfaceC1119q;
                    this.f1445fT = interfaceC0970bi;
                    this.f1446fU = c1005cq;
                    this.f1447fV = null;
                    this.f1448fW = null;
                    this.f1449fX = z;
                    this.f1450fY = null;
                    this.f1451fZ = interfaceC0973bl;
                    this.orientation = i;
                    this.f1453ga = 2;
                    this.f1452fz = null;
                    this.f1442eg = c1003co;
                }

                
                public static C0969bh m1316a(Intent intent) {
                    try {
                        Bundle bundleExtra = intent.getBundleExtra("com.google.android.gms.ads.inernal.overlay.AdOverlayInfo");
                        bundleExtra.setClassLoader(C0969bh.class.getClassLoader());
                        return (C0969bh) bundleExtra.getParcelable("com.google.android.gms.ads.inernal.overlay.AdOverlayInfo");
                    } catch (Exception e) {
                        return null;
                    }
                }

                
                public static void m1317a(Intent intent, C0969bh c0969bh) {
                    Bundle bundle = new Bundle(1);
                    bundle.putParcelable("com.google.android.gms.ads.inernal.overlay.AdOverlayInfo", c0969bh);
                    intent.putExtra("com.google.android.gms.ads.inernal.overlay.AdOverlayInfo", bundle);
                }

                
                IBinder m1318U() {
                    return BinderC0905c.m1166g(this.f1444fS).asBinder();
                }

                
                IBinder m1319V() {
                    return BinderC0905c.m1166g(this.f1445fT).asBinder();
                }

                
                IBinder m1320W() {
                    return BinderC0905c.m1166g(this.f1446fU).asBinder();
                }

                
                IBinder m1321X() {
                    return BinderC0905c.m1166g(this.f1447fV).asBinder();
                }

                
                IBinder m1322Y() {
                    return BinderC0905c.m1166g(this.f1451fZ).asBinder();
                }

                @Override 
                public int describeContents() {
                    return 0;
                }

                @Override 
                public void writeToParcel(Parcel out, int flags) {
                    C0968bg.m1313a(this, out, flags);
                }
            }
