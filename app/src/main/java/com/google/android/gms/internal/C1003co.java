            package com.google.android.gms.internal;

            import android.os.Parcel;
            import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
            
            /* JADX INFO: renamed from: com.google.android.gms.internal.co */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public final class C1003co implements SafeParcelable {
                public static final C1004cp CREATOR = new C1004cp();

                /* JADX INFO: renamed from: hP */
                public String f1596hP;

                /* JADX INFO: renamed from: hQ */
                public int f1597hQ;

                /* JADX INFO: renamed from: hR */
                public int f1598hR;

                /* JADX INFO: renamed from: hS */
                public boolean f1599hS;
                public final int versionCode;

                public C1003co(int i, int i2, boolean z) {
                    this(1, "afma-sdk-a-v" + i + "." + i2 + "." + (z ? "0" : "1"), i, i2, z);
                }

                C1003co(int i, String str, int i2, int i3, boolean z) {
                    this.versionCode = i;
                    this.f1596hP = str;
                    this.f1597hQ = i2;
                    this.f1598hR = i3;
                    this.f1599hS = z;
                }

                @Override // android.os.Parcelable
                public int describeContents() {
                    return 0;
                }

                @Override // android.os.Parcelable
                public void writeToParcel(Parcel out, int flags) {
                    C1004cp.m1459a(this, out, flags);
                }
            }
