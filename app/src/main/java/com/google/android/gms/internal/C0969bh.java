            package com.google.android.gms.internal;

            import android.content.Intent;
            import android.os.Bundle;
            import android.os.IBinder;
            import android.os.Parcel;
            import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
            import com.google.android.gms.dynamic.BinderC0905c;
            import com.google.android.gms.dynamic.InterfaceC0904b;
            
            /* JADX INFO: renamed from: com.google.android.gms.internal.bh */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public final class C0969bh implements SafeParcelable {
                public static final C0968bg CREATOR = new C0968bg();

                /* JADX INFO: renamed from: eg */
                public final C1003co f1442eg;

                /* JADX INFO: renamed from: fR */
                public final C0966be f1443fR;

                /* JADX INFO: renamed from: fS */
                public final InterfaceC1119q f1444fS;

                /* JADX INFO: renamed from: fT */
                public final InterfaceC0970bi f1445fT;

                /* JADX INFO: renamed from: fU */
                public final C1005cq f1446fU;

                /* JADX INFO: renamed from: fV */
                public final InterfaceC0941ag f1447fV;

                /* JADX INFO: renamed from: fW */
                public final String f1448fW;

                /* JADX INFO: renamed from: fX */
                public final boolean f1449fX;

                /* JADX INFO: renamed from: fY */
                public final String f1450fY;

                /* JADX INFO: renamed from: fZ */
                public final InterfaceC0973bl f1451fZ;

                /* JADX INFO: renamed from: fz */
                public final String f1452fz;

                /* JADX INFO: renamed from: ga */
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

                /* JADX INFO: renamed from: a */
                public static C0969bh m1316a(Intent intent) {
                    try {
                        Bundle bundleExtra = intent.getBundleExtra("com.google.android.gms.ads.inernal.overlay.AdOverlayInfo");
                        bundleExtra.setClassLoader(C0969bh.class.getClassLoader());
                        return (C0969bh) bundleExtra.getParcelable("com.google.android.gms.ads.inernal.overlay.AdOverlayInfo");
                    } catch (Exception e) {
                        return null;
                    }
                }

                /* JADX INFO: renamed from: a */
                public static void m1317a(Intent intent, C0969bh c0969bh) {
                    Bundle bundle = new Bundle(1);
                    bundle.putParcelable("com.google.android.gms.ads.inernal.overlay.AdOverlayInfo", c0969bh);
                    intent.putExtra("com.google.android.gms.ads.inernal.overlay.AdOverlayInfo", bundle);
                }

                /* JADX INFO: renamed from: U */
                IBinder m1318U() {
                    return BinderC0905c.m1166g(this.f1444fS).asBinder();
                }

                /* JADX INFO: renamed from: V */
                IBinder m1319V() {
                    return BinderC0905c.m1166g(this.f1445fT).asBinder();
                }

                /* JADX INFO: renamed from: W */
                IBinder m1320W() {
                    return BinderC0905c.m1166g(this.f1446fU).asBinder();
                }

                /* JADX INFO: renamed from: X */
                IBinder m1321X() {
                    return BinderC0905c.m1166g(this.f1447fV).asBinder();
                }

                /* JADX INFO: renamed from: Y */
                IBinder m1322Y() {
                    return BinderC0905c.m1166g(this.f1451fZ).asBinder();
                }

                @Override // android.os.Parcelable
                public int describeContents() {
                    return 0;
                }

                @Override // android.os.Parcelable
                public void writeToParcel(Parcel out, int flags) {
                    C0968bg.m1313a(this, out, flags);
                }
            }
