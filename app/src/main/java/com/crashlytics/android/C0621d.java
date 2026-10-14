            package com.crashlytics.android;

            import com.vandalsoftware.p004io.IoUtils;
            import java.io.ByteArrayInputStream;
            import java.io.InputStream;
            import java.io.UnsupportedEncodingException;
            
            
            
    final class C0621d {

                
                private final byte[] f198a;

                
                private volatile int f199b = 0;

        private C0621d(byte[] bArr) {
            this.f198a = bArr;
                }

                
        public final int m64a() {
            return this.f198a.length;
                }

                static {
            new C0621d(new byte[0]);
                }

                
        public static C0621d m63a(byte[] bArr, int i, int i2) {
            byte[] bArr2 = new byte[i2];
            System.arraycopy(bArr, 0, bArr2, 0, i2);
            return new C0621d(bArr2);
                }

                
       public static C0621d m62a(String str) {
                    try {
               return new C0621d(str.getBytes(IoUtils.UTF_8));
                    } catch (UnsupportedEncodingException e) {
               throw new RuntimeException("UTF-8 not supported.", e);
                    }
                }

                
       public final void m65a(byte[] bArr, int i, int i2, int i3) {
           System.arraycopy(this.f198a, i, bArr, i2, i3);
                }

       public final boolean equals(Object o) {
           if (o == this) {
                        return true;
                    }
           if (!(o instanceof C0621d)) {
                        return false;
                    }
           C0621d c0621d = (C0621d) o;
           int length = this.f198a.length;
           if (length != c0621d.f198a.length) {
                        return false;
                    }
           byte[] bArr = this.f198a;
           byte[] bArr2 = c0621d.f198a;
           for (int i = 0; i < length; i++) {
               if (bArr[i] != bArr2[i]) {
                            return false;
                        }
                    }
                    return true;
                }

       public final int hashCode() {
           int i = this.f199b;
           if (i == 0) {
               byte[] bArr = this.f198a;
               int length = this.f198a.length;
               int i2 = 0;
                        i = length;
                        while (i2 < length) {
                   int i3 = bArr[i2] + (i * 31);
                   i2++;
                            i = i3;
                        }
               if (i == 0) {
                   i = 1;
                        }
               this.f199b = i;
                    }
           return i;
                }

                
       public final InputStream m66b() {
           return new ByteArrayInputStream(this.f198a);
                }
            }
