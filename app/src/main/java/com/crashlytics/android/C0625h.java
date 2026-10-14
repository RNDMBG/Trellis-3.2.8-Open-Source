            package com.crashlytics.android;

            import com.vandalsoftware.p004io.IoUtils;
            import java.io.Flushable;
            import java.io.IOException;
            import java.io.InputStream;
            import java.io.OutputStream;
            
            
            
    final class C0625h implements Flushable {

                
                private final byte[] f206a;

                
                private final int f207b;

                
                private int f208c = 0;

                
                private final OutputStream f209d;

        private C0625h(OutputStream outputStream, byte[] bArr) {
            this.f209d = outputStream;
            this.f206a = bArr;
            this.f207b = bArr.length;
                }

                
        public static C0625h m70a(OutputStream outputStream) {
            return new C0625h(outputStream, new byte[4096]);
                }

                
       public final void m83a(int i, float f) throws IOException {
           m93g(1, 5);
           int iFloatToRawIntBits = Float.floatToRawIntBits(f);
                    m79d(iFloatToRawIntBits & 255);
                    m79d((iFloatToRawIntBits >> 8) & 255);
                    m79d((iFloatToRawIntBits >> 16) & 255);
                    m79d(iFloatToRawIntBits >>> 24);
                }

                
       public final void m85a(int i, long j) throws IOException {
           m93g(i, 0);
           m72a(j);
                }

                
       public final void m88a(int i, boolean z) throws IOException {
           m93g(i, 0);
           m79d(z ? 1 : 0);
                }

                
       public final void m87a(int i, String str) throws IOException {
           m93g(1, 2);
           byte[] bytes = str.getBytes(IoUtils.UTF_8);
                    m90b(bytes.length);
                    m89a(bytes);
                }

                
       public final void m86a(int i, C0621d c0621d) throws IOException {
           m93g(i, 2);
           m90b(c0621d.m64a());
                    int iM64a = c0621d.m64a();
                    if (this.f207b - this.f208c < iM64a) {
               int i2 = this.f207b - this.f208c;
                        c0621d.m65a(this.f206a, 0, this.f208c, i2);
                        int i3 = i2 + 0;
                        int i4 = iM64a - i2;
                        this.f208c = this.f207b;
                        m71a();
                        if (i4 <= this.f207b) {
                            c0621d.m65a(this.f206a, i3, 0, i4);
                            this.f208c = i4;
                   return;
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
               return;
                    }
                    c0621d.m65a(this.f206a, 0, this.f208c, iM64a);
                    this.f208c = iM64a + this.f208c;
                }

                
       public final void m84a(int i, int i2) throws IOException {
           m93g(i, 0);
           m90b(i2);
                }

                
       public final void m91b(int i, int i2) throws IOException {
           m93g(i, 0);
           if (i2 < 0) {
               m72a(i2);
                    } else {
                        m90b(i2);
                    }
                }

                
       public final void m92c(int i, int i2) throws IOException {
           m93g(2, 0);
           m90b(m80e(i2));
                }

                
       public static int m73b(int i, float f) {
           return m69a(1) + 4;
                }

                
       public static int m74b(int i, long j) {
                    int i2;
           int iM69a = m69a(i);
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

                
       public static int m76b(int i, boolean z) {
           return m69a(i) + 1;
                }

                
       public static int m75b(int i, C0621d c0621d) {
           return m69a(i) + m77c(c0621d.m64a()) + c0621d.m64a();
                }

                
       public static int m78d(int i, int i2) {
           return m69a(i) + m77c(i2);
                }

                
       public static int m81e(int i, int i2) {
                    return (i2 >= 0 ? m77c(i2) : 10) + m69a(i);
                }

                
       public static int m82f(int i, int i2) {
           return m69a(2) + m77c(m80e(i2));
                }

                
       private void m71a() throws IOException {
           if (this.f209d == null) {
               throw new C0626i();
                    }
           this.f209d.write(this.f206a, 0, this.f208c);
           this.f208c = 0;
                }

                @Override 
       public final void flush() throws IOException {
           if (this.f209d != null) {
               m71a();
                    }
                }

                
       private void m79d(int i) throws IOException {
           byte b = (byte) i;
                    if (this.f208c == this.f207b) {
                        m71a();
                    }
                    byte[] bArr = this.f206a;
                    int i2 = this.f208c;
                    this.f208c = i2 + 1;
                    bArr[i2] = b;
                }

                
       public final void m89a(byte[] bArr) throws IOException {
           int length = bArr.length;
                    if (this.f207b - this.f208c < length) {
               int i = this.f207b - this.f208c;
                        System.arraycopy(bArr, 0, this.f206a, this.f208c, i);
                        int i2 = i + 0;
                        int i3 = length - i;
                        this.f208c = this.f207b;
                        m71a();
                        if (i3 <= this.f207b) {
                            System.arraycopy(bArr, i2, this.f206a, 0, i3);
                            this.f208c = i3;
                   return;
                        } else {
                            this.f209d.write(bArr, i2, i3);
                   return;
                        }
                    }
                    System.arraycopy(bArr, 0, this.f206a, this.f208c, length);
                    this.f208c = length + this.f208c;
                }

                
       public final void m93g(int i, int i2) throws IOException {
           m90b(C0618ae.m59a(i, i2));
                }

                
       public static int m69a(int i) {
           return m77c(C0618ae.m59a(i, 0));
                }

                
       public final void m90b(int i) throws IOException {
           while ((i & (-128)) != 0) {
               m79d((i & 127) | 128);
               i >>>= 7;
                    }
           m79d(i);
                }

                
       public static int m77c(int i) {
           if ((i & (-128)) == 0) {
                        return 1;
                    }
           if ((i & (-16384)) == 0) {
                        return 2;
                    }
           if (((-2097152) & i) == 0) {
                        return 3;
                    }
                    return ((-268435456) & i) == 0 ? 4 : 5;
                }

                
       private void m72a(long j) throws IOException {
                    while (((-128) & j) != 0) {
               m79d((((int) j) & 127) | 128);
               j >>>= 7;
                    }
           m79d((int) j);
                }

                
       private static int m80e(int i) {
           return (i << 1) ^ (i >> 31);
                }
            }
