            package com.google.android.gms.internal;

            import java.io.IOException;
            
            /* JADX INFO: renamed from: com.google.android.gms.internal.m */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            class C1115m implements InterfaceC1113k {

                /* JADX INFO: renamed from: dD */
                private C1108gl f2134dD;

                /* JADX INFO: renamed from: dE */
                private byte[] f2135dE;

                /* JADX INFO: renamed from: dF */
                private final int f2136dF;

                public C1115m(int i) {
                    this.f2136dF = i;
                    reset();
                }

                @Override // com.google.android.gms.internal.InterfaceC1113k
                /* JADX INFO: renamed from: b */
                public void mo2129b(int i, long j) throws IOException {
                    this.f2134dD.m2114b(i, j);
                }

                @Override // com.google.android.gms.internal.InterfaceC1113k
                /* JADX INFO: renamed from: b */
                public void mo2130b(int i, String str) throws IOException {
                    this.f2134dD.m2115b(i, str);
                }

                @Override // com.google.android.gms.internal.InterfaceC1113k
                /* JADX INFO: renamed from: h */
                public byte[] mo2131h() throws IOException {
                    int iM2118ec = this.f2134dD.m2118ec();
                    if (iM2118ec < 0) {
                        throw new IOException();
                    }
                    if (iM2118ec == 0) {
                        return this.f2135dE;
                    }
                    byte[] bArr = new byte[this.f2135dE.length - iM2118ec];
                    System.arraycopy(this.f2135dE, 0, bArr, 0, bArr.length);
                    return bArr;
                }

                @Override // com.google.android.gms.internal.InterfaceC1113k
                public void reset() {
                    this.f2135dE = new byte[this.f2136dF];
                    this.f2134dD = C1108gl.m2109g(this.f2135dE);
                }
            }
