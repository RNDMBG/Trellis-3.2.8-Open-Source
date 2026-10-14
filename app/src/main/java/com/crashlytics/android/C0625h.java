            package com.crashlytics.android;

            import com.vandalsoftware.p004io.IoUtils;
            import java.io.Flushable;
            import java.io.IOException;
            import java.io.InputStream;
            import java.io.OutputStream;
            
            /* JADX INFO: renamed from: com.crashlytics.android.h */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 84 */    final class C0625h implements Flushable {

                /* JADX INFO: renamed from: a */
                private final byte[] f206a;

                /* JADX INFO: renamed from: b */
                private final int f207b;

                /* JADX INFO: renamed from: c */
                private int f208c = 0;

                /* JADX INFO: renamed from: d */
                private final OutputStream f209d;

/* 85 */        private C0625h(OutputStream outputStream, byte[] bArr) {
/* 87 */            this.f209d = outputStream;
/* 88 */            this.f206a = bArr;
/* 90 */            this.f207b = bArr.length;
                }

                /* JADX INFO: renamed from: a */
/* 97 */        public static C0625h m70a(OutputStream outputStream) {
/* 98 */            return new C0625h(outputStream, new byte[4096]);
                }

                /* JADX INFO: renamed from: a */
/* 145 */       public final void m83a(int i, float f) throws IOException {
/* 146 */           m93g(1, 5);
/* 147 */           int iFloatToRawIntBits = Float.floatToRawIntBits(f);
                    m79d(iFloatToRawIntBits & 255);
                    m79d((iFloatToRawIntBits >> 8) & 255);
                    m79d((iFloatToRawIntBits >> 16) & 255);
                    m79d(iFloatToRawIntBits >>> 24);
                }

                /* JADX INFO: renamed from: a */
/* 152 */       public final void m85a(int i, long j) throws IOException {
/* 153 */           m93g(i, 0);
/* 154 */           m72a(j);
                }

                /* JADX INFO: renamed from: a */
/* 187 */       public final void m88a(int i, boolean z) throws IOException {
/* 188 */           m93g(i, 0);
/* 189 */           m79d(z ? 1 : 0);
                }

                /* JADX INFO: renamed from: a */
/* 194 */       public final void m87a(int i, String str) throws IOException {
/* 195 */           m93g(1, 2);
/* 196 */           byte[] bytes = str.getBytes(IoUtils.UTF_8);
                    m90b(bytes.length);
                    m89a(bytes);
                }

                /* JADX INFO: renamed from: a */
/* 201 */       public final void m86a(int i, C0621d c0621d) throws IOException {
/* 202 */           m93g(i, 2);
/* 203 */           m90b(c0621d.m64a());
                    int iM64a = c0621d.m64a();
                    if (this.f207b - this.f208c < iM64a) {
/* 203 */               int i2 = this.f207b - this.f208c;
                        c0621d.m65a(this.f206a, 0, this.f208c, i2);
                        int i3 = i2 + 0;
                        int i4 = iM64a - i2;
                        this.f208c = this.f207b;
                        m71a();
                        if (i4 <= this.f207b) {
                            c0621d.m65a(this.f206a, i3, 0, i4);
                            this.f208c = i4;
/* 204 */                   return;
                        }
                        InputStream inputStreamM66b = c0621d.m66b();
                        if (i3 != inputStreamM66b.skip(i3)) {
                            throw new IllegalStateException("Skip failed.");
                        }
                        while (i4 > 0) {
                            int iMin = Math.min(i4, this.f207b);
                            int i5 = inputStreamM66b.read(this.f206a, 0, iMin);
                            if (i5 != iMin) {
                                throw new IllegalStateException("Read failed.");
                            }
                            this.f209d.write(this.f206a, 0, i5);
                            i4 -= i5;
                        }
/* 204 */               return;
                    }
                    c0621d.m65a(this.f206a, 0, this.f208c, iM64a);
                    this.f208c = iM64a + this.f208c;
                }

                /* JADX INFO: renamed from: a */
/* 208 */       public final void m84a(int i, int i2) throws IOException {
/* 209 */           m93g(i, 0);
/* 210 */           m90b(i2);
                }

                /* JADX INFO: renamed from: b */
/* 218 */       public final void m91b(int i, int i2) throws IOException {
/* 219 */           m93g(i, 0);
/* 220 */           if (i2 < 0) {
/* 220 */               m72a(i2);
                    } else {
                        m90b(i2);
                    }
                }

                /* JADX INFO: renamed from: c */
/* 239 */       public final void m92c(int i, int i2) throws IOException {
/* 240 */           m93g(2, 0);
/* 241 */           m90b(m80e(i2));
                }

                /* JADX INFO: renamed from: b */
/* 375 */       public static int m73b(int i, float f) {
/* 376 */           return m69a(1) + 4;
                }

                /* JADX INFO: renamed from: b */
/* 383 */       public static int m74b(int i, long j) {
                    int i2;
/* 384 */           int iM69a = m69a(i);
                    if (((-128) & j) == 0) {
                        i2 = 1;
                    } else if (((-16384) & j) == 0) {
                        i2 = 2;
                    } else if (((-2097152) & j) == 0) {
                        i2 = 3;
                    } else if (((-268435456) & j) == 0) {
                        i2 = 4;
                    } else if (((-34359738368L) & j) == 0) {
                        i2 = 5;
                    } else if (((-4398046511104L) & j) == 0) {
                        i2 = 6;
                    } else if (((-562949953421312L) & j) == 0) {
                        i2 = 7;
                    } else if (((-72057594037927936L) & j) == 0) {
                        i2 = 8;
                    } else {
                        i2 = (Long.MIN_VALUE & j) == 0 ? 9 : 10;
                    }
                    return i2 + iM69a;
                }

                /* JADX INFO: renamed from: b */
/* 426 */       public static int m76b(int i, boolean z) {
/* 427 */           return m69a(i) + 1;
                }

                /* JADX INFO: renamed from: b */
/* 444 */       public static int m75b(int i, C0621d c0621d) {
/* 445 */           return m69a(i) + m77c(c0621d.m64a()) + c0621d.m64a();
                }

                /* JADX INFO: renamed from: d */
/* 452 */       public static int m78d(int i, int i2) {
/* 453 */           return m69a(i) + m77c(i2);
                }

                /* JADX INFO: renamed from: e */
/* 461 */       public static int m81e(int i, int i2) {
                    return (i2 >= 0 ? m77c(i2) : 10) + m69a(i);
                }

                /* JADX INFO: renamed from: f */
/* 487 */       public static int m82f(int i, int i2) {
/* 488 */           return m69a(2) + m77c(m80e(i2));
                }

                /* JADX INFO: renamed from: a */
/* 659 */       private void m71a() throws IOException {
/* 660 */           if (this.f209d == null) {
/* 662 */               throw new C0626i();
                    }
/* 667 */           this.f209d.write(this.f206a, 0, this.f208c);
/* 668 */           this.f208c = 0;
                }

                @Override // java.io.Flushable
/* 675 */       public final void flush() throws IOException {
/* 676 */           if (this.f209d != null) {
/* 677 */               m71a();
                    }
                }

                /* JADX INFO: renamed from: d */
/* 733 */       private void m79d(int i) throws IOException {
/* 734 */           byte b = (byte) i;
                    if (this.f208c == this.f207b) {
                        m71a();
                    }
                    byte[] bArr = this.f206a;
                    int i2 = this.f208c;
                    this.f208c = i2 + 1;
                    bArr[i2] = b;
                }

                /* JADX INFO: renamed from: a */
/* 743 */       public final void m89a(byte[] bArr) throws IOException {
/* 744 */           int length = bArr.length;
                    if (this.f207b - this.f208c < length) {
/* 744 */               int i = this.f207b - this.f208c;
                        System.arraycopy(bArr, 0, this.f206a, this.f208c, i);
                        int i2 = i + 0;
                        int i3 = length - i;
                        this.f208c = this.f207b;
                        m71a();
                        if (i3 <= this.f207b) {
                            System.arraycopy(bArr, i2, this.f206a, 0, i3);
                            this.f208c = i3;
/* 745 */                   return;
                        } else {
                            this.f209d.write(bArr, i2, i3);
/* 745 */                   return;
                        }
                    }
                    System.arraycopy(bArr, 0, this.f206a, this.f208c, length);
                    this.f208c = length + this.f208c;
                }

                /* JADX INFO: renamed from: g */
/* 827 */       public final void m93g(int i, int i2) throws IOException {
/* 828 */           m90b(C0618ae.m59a(i, i2));
                }

                /* JADX INFO: renamed from: a */
/* 832 */       public static int m69a(int i) {
/* 833 */           return m77c(C0618ae.m59a(i, 0));
                }

                /* JADX INFO: renamed from: b */
/* 841 */       public final void m90b(int i) throws IOException {
/* 842 */           while ((i & (-128)) != 0) {
/* 846 */               m79d((i & 127) | 128);
/* 847 */               i >>>= 7;
                    }
/* 843 */           m79d(i);
                }

                /* JADX INFO: renamed from: c */
/* 857 */       public static int m77c(int i) {
/* 858 */           if ((i & (-128)) == 0) {
                        return 1;
                    }
/* 859 */           if ((i & (-16384)) == 0) {
                        return 2;
                    }
/* 860 */           if (((-2097152) & i) == 0) {
                        return 3;
                    }
                    return ((-268435456) & i) == 0 ? 4 : 5;
                }

                /* JADX INFO: renamed from: a */
/* 867 */       private void m72a(long j) throws IOException {
                    while (((-128) & j) != 0) {
/* 872 */               m79d((((int) j) & 127) | 128);
/* 873 */               j >>>= 7;
                    }
/* 869 */           m79d((int) j);
                }

                /* JADX INFO: renamed from: e */
/* 927 */       private static int m80e(int i) {
/* 928 */           return (i << 1) ^ (i >> 31);
                }
            }
