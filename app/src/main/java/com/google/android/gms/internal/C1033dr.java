            package com.google.android.gms.internal;

            import android.os.Parcel;
            import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
            
            /* JADX INFO: renamed from: com.google.android.gms.internal.dr */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public class C1033dr implements SafeParcelable {
                public static final C1034ds CREATOR = new C1034ds();

                /* JADX INFO: renamed from: iM */
                private final int f1727iM;

                /* JADX INFO: renamed from: lt */
                private final C1035dt f1728lt;

                C1033dr(int i, C1035dt c1035dt) {
                    this.f1727iM = i;
                    this.f1728lt = c1035dt;
                }

                private C1033dr(C1035dt c1035dt) {
                    this.f1727iM = 1;
                    this.f1728lt = c1035dt;
                }

                /* JADX INFO: renamed from: a */
                public static C1033dr m1620a(AbstractC1038dw.b<?, ?> bVar) {
                    if (bVar instanceof C1035dt) {
                        return new C1033dr((C1035dt) bVar);
                    }
                    throw new IllegalArgumentException("Unsupported safe parcelable field converter class.");
                }

                /* JADX INFO: renamed from: bk */
                C1035dt m1621bk() {
                    return this.f1728lt;
                }

                /* JADX INFO: renamed from: bl */
                public AbstractC1038dw.b<?, ?> m1622bl() {
                    if (this.f1728lt != null) {
                        return this.f1728lt;
                    }
                    throw new IllegalStateException("There was no converter wrapped in this ConverterWrapper.");
                }

                @Override // android.os.Parcelable
                public int describeContents() {
                    C1034ds c1034ds = CREATOR;
                    return 0;
                }

                int getVersionCode() {
                    return this.f1727iM;
                }

                @Override // android.os.Parcelable
                public void writeToParcel(Parcel out, int flags) {
                    C1034ds c1034ds = CREATOR;
                    C1034ds.m1623a(this, out, flags);
                }
            }
