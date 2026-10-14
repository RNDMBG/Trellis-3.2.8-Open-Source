            package com.google.android.gms.internal;

            import com.vandalsoftware.p004io.IoUtils;
            import java.io.IOException;
            
            /* JADX INFO: renamed from: com.google.android.gms.internal.gl */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public final class C1108gl {
                private final byte[] buffer;
                private int position;

                /* JADX INFO: renamed from: ux */
                private final int f2113ux;

                /* JADX INFO: renamed from: com.google.android.gms.internal.gl$a */
                public static class a extends IOException {
                    a(int i, int i2) {
                        super("CodedOutputStream was writing to a flat byte array and ran out of space (pos " + i + " limit " + i2 + ").");
                    }
                }

                private C1108gl(byte[] bArr, int i, int i2) {
                    this.buffer = bArr;
                    this.position = i;
                    this.f2113ux = i + i2;
                }

                /* JADX INFO: renamed from: a */
                public static C1108gl m2108a(byte[] bArr, int i, int i2) {
                    return new C1108gl(bArr, i, i2);
                }

                /* JADX INFO: renamed from: g */
                public static C1108gl m2109g(byte[] bArr) {
                    return m2108a(bArr, 0, bArr.length);
                }

                /* JADX INFO: renamed from: aD */
                public void m2110aD(int i) throws IOException {
                    m2113b((byte) i);
                }

                /* JADX INFO: renamed from: aE */
                public void m2111aE(int i) throws IOException {
                    while ((i & (-128)) != 0) {
                        m2110aD((i & 127) | 128);
                        i >>>= 7;
                    }
                    m2110aD(i);
                }

                /* JADX INFO: renamed from: ab */
                public void m2112ab(String str) throws IOException {
                    byte[] bytes = str.getBytes(IoUtils.UTF_8);
                    m2111aE(bytes.length);
                    m2119h(bytes);
                }

                /* JADX INFO: renamed from: b */
                public void m2113b(byte b) throws IOException {
                    if (this.position == this.f2113ux) {
                        throw new a(this.position, this.f2113ux);
                    }
                    byte[] bArr = this.buffer;
                    int i = this.position;
                    this.position = i + 1;
                    bArr[i] = b;
                }

                /* JADX INFO: renamed from: b */
                public void m2114b(int i, long j) throws IOException {
                    m2117d(i, 0);
                    m2120i(j);
                }

                /* JADX INFO: renamed from: b */
                public void m2115b(int i, String str) throws IOException {
                    m2117d(i, 2);
                    m2112ab(str);
                }

                /* JADX INFO: renamed from: b */
                public void m2116b(byte[] bArr, int i, int i2) throws IOException {
                    if (this.f2113ux - this.position < i2) {
                        throw new a(this.position, this.f2113ux);
                    }
                    System.arraycopy(bArr, i, this.buffer, this.position, i2);
                    this.position += i2;
                }

                /* JADX INFO: renamed from: d */
                public void m2117d(int i, int i2) throws IOException {
                    m2111aE(C1109gm.m2122e(i, i2));
                }

                /* JADX INFO: renamed from: ec */
                public int m2118ec() {
                    return this.f2113ux - this.position;
                }

                /* JADX INFO: renamed from: h */
                public void m2119h(byte[] bArr) throws IOException {
                    m2116b(bArr, 0, bArr.length);
                }

                /* JADX INFO: renamed from: i */
                public void m2120i(long j) throws IOException {
                    m2121j(j);
                }

                /* JADX INFO: renamed from: j */
                public void m2121j(long j) throws IOException {
                    while (((-128) & j) != 0) {
                        m2110aD((((int) j) & 127) | 128);
                        j >>>= 7;
                    }
                    m2110aD((int) j);
                }
            }
