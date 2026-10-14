            package com.google.android.gms.internal;

            import android.content.pm.ApplicationInfo;
            import android.content.pm.PackageInfo;
            import android.os.Bundle;
            import android.os.Parcel;
            import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
            
            /* JADX INFO: renamed from: com.google.android.gms.internal.bu */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public final class C0982bu implements SafeParcelable {
                public static final C0983bv CREATOR = new C0983bv();
                public final String adUnitId;
                public final ApplicationInfo applicationInfo;

                /* JADX INFO: renamed from: ed */
                public final C1126x f1493ed;

                /* JADX INFO: renamed from: eg */
                public final C1003co f1494eg;

                /* JADX INFO: renamed from: gA */
                public final Bundle f1495gA;

                /* JADX INFO: renamed from: gB */
                public final C1124v f1496gB;

                /* JADX INFO: renamed from: gC */
                public final PackageInfo f1497gC;

                /* JADX INFO: renamed from: gD */
                public final String f1498gD;

                /* JADX INFO: renamed from: gE */
                public final String f1499gE;

                /* JADX INFO: renamed from: gF */
                public final String f1500gF;
                public final int versionCode;

                /* JADX INFO: renamed from: com.google.android.gms.internal.bu$a */
                public static final class a {
                    public final String adUnitId;
                    public final ApplicationInfo applicationInfo;

                    /* JADX INFO: renamed from: ed */
                    public final C1126x f1501ed;

                    /* JADX INFO: renamed from: eg */
                    public final C1003co f1502eg;

                    /* JADX INFO: renamed from: gA */
                    public final Bundle f1503gA;

                    /* JADX INFO: renamed from: gB */
                    public final C1124v f1504gB;

                    /* JADX INFO: renamed from: gC */
                    public final PackageInfo f1505gC;

                    /* JADX INFO: renamed from: gE */
                    public final String f1506gE;

                    /* JADX INFO: renamed from: gF */
                    public final String f1507gF;

                    public a(Bundle bundle, C1124v c1124v, C1126x c1126x, String str, ApplicationInfo applicationInfo, PackageInfo packageInfo, String str2, String str3, C1003co c1003co) {
                        this.f1503gA = bundle;
                        this.f1504gB = c1124v;
                        this.f1501ed = c1126x;
                        this.adUnitId = str;
                        this.applicationInfo = applicationInfo;
                        this.f1505gC = packageInfo;
                        this.f1506gE = str2;
                        this.f1507gF = str3;
                        this.f1502eg = c1003co;
                    }
                }

                C0982bu(int i, Bundle bundle, C1124v c1124v, C1126x c1126x, String str, ApplicationInfo applicationInfo, PackageInfo packageInfo, String str2, String str3, String str4, C1003co c1003co) {
                    this.versionCode = i;
                    this.f1495gA = bundle;
                    this.f1496gB = c1124v;
                    this.f1493ed = c1126x;
                    this.adUnitId = str;
                    this.applicationInfo = applicationInfo;
                    this.f1497gC = packageInfo;
                    this.f1498gD = str2;
                    this.f1499gE = str3;
                    this.f1500gF = str4;
                    this.f1494eg = c1003co;
                }

                public C0982bu(Bundle bundle, C1124v c1124v, C1126x c1126x, String str, ApplicationInfo applicationInfo, PackageInfo packageInfo, String str2, String str3, String str4, C1003co c1003co) {
                    this(1, bundle, c1124v, c1126x, str, applicationInfo, packageInfo, str2, str3, str4, c1003co);
                }

                public C0982bu(a aVar, String str) {
                    this(aVar.f1503gA, aVar.f1504gB, aVar.f1501ed, aVar.adUnitId, aVar.applicationInfo, aVar.f1505gC, str, aVar.f1506gE, aVar.f1507gF, aVar.f1502eg);
                }

                @Override // android.os.Parcelable
                public int describeContents() {
                    return 0;
                }

                @Override // android.os.Parcelable
                public void writeToParcel(Parcel out, int flags) {
                    C0983bv.m1371a(this, out, flags);
                }
            }
