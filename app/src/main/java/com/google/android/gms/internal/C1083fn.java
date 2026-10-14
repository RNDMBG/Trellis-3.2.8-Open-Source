            package com.google.android.gms.internal;

            import android.os.Parcel;
            import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
            
            /* JADX INFO: renamed from: com.google.android.gms.internal.fn */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public class C1083fn implements SafeParcelable {
                public static final C1085fp CREATOR = new C1085fp();

                /* JADX INFO: renamed from: iM */
                private final int f1935iM;

                /* JADX INFO: renamed from: it */
                private final String f1936it;

                /* JADX INFO: renamed from: rA */
                private final String[] f1937rA;

                /* JADX INFO: renamed from: rB */
                private final String f1938rB;

                /* JADX INFO: renamed from: rC */
                private final String f1939rC;

                /* JADX INFO: renamed from: rD */
                private final String f1940rD;

                /* JADX INFO: renamed from: rE */
                private final String f1941rE;

                /* JADX INFO: renamed from: ry */
                private final String[] f1942ry;

                /* JADX INFO: renamed from: rz */
                private final String[] f1943rz;

                C1083fn(int i, String str, String[] strArr, String[] strArr2, String[] strArr3, String str2, String str3, String str4, String str5) {
                    this.f1935iM = i;
                    this.f1936it = str;
                    this.f1942ry = strArr;
                    this.f1943rz = strArr2;
                    this.f1937rA = strArr3;
                    this.f1938rB = str2;
                    this.f1939rC = str3;
                    this.f1940rD = str4;
                    this.f1941rE = str5;
                }

                C1083fn(String str, String[] strArr, String[] strArr2, String[] strArr3, String str2, String str3, String str4, String str5) {
                    this.f1935iM = 1;
                    this.f1936it = str;
                    this.f1942ry = strArr;
                    this.f1943rz = strArr2;
                    this.f1937rA = strArr3;
                    this.f1938rB = str2;
                    this.f1939rC = str3;
                    this.f1940rD = str4;
                    this.f1941rE = str5;
                }

                /* JADX INFO: renamed from: cZ */
                public String[] m1982cZ() {
                    return this.f1942ry;
                }

                /* JADX INFO: renamed from: da */
                public String[] m1983da() {
                    return this.f1943rz;
                }

                /* JADX INFO: renamed from: db */
                public String[] m1984db() {
                    return this.f1937rA;
                }

                /* JADX INFO: renamed from: dc */
                public String m1985dc() {
                    return this.f1938rB;
                }

                /* JADX INFO: renamed from: dd */
                public String m1986dd() {
                    return this.f1939rC;
                }

                /* JADX INFO: renamed from: de */
                public String m1987de() {
                    return this.f1940rD;
                }

                @Override // android.os.Parcelable
                public int describeContents() {
                    return 0;
                }

                /* JADX INFO: renamed from: df */
                public String m1988df() {
                    return this.f1941rE;
                }

                public boolean equals(Object obj) {
                    if (!(obj instanceof C1083fn)) {
                        return false;
                    }
                    C1083fn c1083fn = (C1083fn) obj;
                    return this.f1935iM == c1083fn.f1935iM && C1027dl.equal(this.f1936it, c1083fn.f1936it) && C1027dl.equal(this.f1942ry, c1083fn.f1942ry) && C1027dl.equal(this.f1943rz, c1083fn.f1943rz) && C1027dl.equal(this.f1937rA, c1083fn.f1937rA) && C1027dl.equal(this.f1938rB, c1083fn.f1938rB) && C1027dl.equal(this.f1939rC, c1083fn.f1939rC) && C1027dl.equal(this.f1940rD, c1083fn.f1940rD) && C1027dl.equal(this.f1941rE, c1083fn.f1941rE);
                }

                public String getAccountName() {
                    return this.f1936it;
                }

                public int getVersionCode() {
                    return this.f1935iM;
                }

                public int hashCode() {
                    return C1027dl.hashCode(Integer.valueOf(this.f1935iM), this.f1936it, this.f1942ry, this.f1943rz, this.f1937rA, this.f1938rB, this.f1939rC, this.f1940rD, this.f1941rE);
                }

                public String toString() {
                    return C1027dl.m1599d(this).m1600a("versionCode", Integer.valueOf(this.f1935iM)).m1600a("accountName", this.f1936it).m1600a("requestedScopes", this.f1942ry).m1600a("visibleActivities", this.f1943rz).m1600a("requiredFeatures", this.f1937rA).m1600a("packageNameForAuth", this.f1938rB).m1600a("callingPackageName", this.f1939rC).m1600a("applicationName", this.f1940rD).m1600a("clientId", this.f1941rE).toString();
                }

                @Override // android.os.Parcelable
                public void writeToParcel(Parcel out, int flags) {
                    C1085fp.m1994a(this, out, flags);
                }
            }
