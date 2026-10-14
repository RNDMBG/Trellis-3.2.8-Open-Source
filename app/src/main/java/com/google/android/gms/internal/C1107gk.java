            package com.google.android.gms.internal;
            
            /* JADX INFO: renamed from: com.google.android.gms.internal.gk */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public class C1107gk {

                /* JADX INFO: renamed from: uu */
                private final byte[] f2110uu = new byte[256];

                /* JADX INFO: renamed from: uv */
                private int f2111uv;

                /* JADX INFO: renamed from: uw */
                private int f2112uw;

                public C1107gk(byte[] bArr) {
                    for (int i = 0; i < 256; i++) {
                        this.f2110uu[i] = (byte) i;
                    }
                    int i2 = 0;
                    for (int i3 = 0; i3 < 256; i3++) {
                        i2 = (i2 + this.f2110uu[i3] + bArr[i3 % bArr.length]) & 255;
                        byte b = this.f2110uu[i3];
                        this.f2110uu[i3] = this.f2110uu[i2];
                        this.f2110uu[i2] = b;
                    }
                    this.f2111uv = 0;
                    this.f2112uw = 0;
                }

                /* JADX INFO: renamed from: f */
                public void m2107f(byte[] bArr) {
                    int i = this.f2111uv;
                    int i2 = this.f2112uw;
                    for (int i3 = 0; i3 < bArr.length; i3++) {
                        i = (i + 1) & 255;
                        i2 = (i2 + this.f2110uu[i]) & 255;
                        byte b = this.f2110uu[i];
                        this.f2110uu[i] = this.f2110uu[i2];
                        this.f2110uu[i2] = b;
                        bArr[i3] = (byte) (bArr[i3] ^ this.f2110uu[(this.f2110uu[i] + this.f2110uu[i2]) & 255]);
                    }
                    this.f2111uv = i;
                    this.f2112uw = i2;
                }
            }
