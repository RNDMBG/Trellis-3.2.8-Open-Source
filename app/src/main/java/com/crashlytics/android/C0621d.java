            package com.crashlytics.android;

            import com.vandalsoftware.p004io.IoUtils;
            import java.io.ByteArrayInputStream;
            import java.io.InputStream;
            import java.io.UnsupportedEncodingException;
            
            /* JADX INFO: renamed from: com.crashlytics.android.d */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 49 */    final class C0621d {

                /* JADX INFO: renamed from: a */
                private final byte[] f198a;

                /* JADX INFO: renamed from: b */
                private volatile int f199b = 0;

/* 50 */        private C0621d(byte[] bArr) {
/* 52 */            this.f198a = bArr;
                }

                /* JADX INFO: renamed from: a */
/* 67 */        public final int m64a() {
/* 68 */            return this.f198a.length;
                }

                static {
/* 84 */            new C0621d(new byte[0]);
                }

                /* JADX INFO: renamed from: a */
/* 90 */        public static C0621d m63a(byte[] bArr, int i, int i2) {
/* 91 */            byte[] bArr2 = new byte[i2];
/* 92 */            System.arraycopy(bArr, 0, bArr2, 0, i2);
/* 93 */            return new C0621d(bArr2);
                }

                /* JADX INFO: renamed from: a */
/* 135 */       public static C0621d m62a(String str) {
                    try {
/* 136 */               return new C0621d(str.getBytes(IoUtils.UTF_8));
                    } catch (UnsupportedEncodingException e) {
/* 138 */               throw new RuntimeException("UTF-8 not supported.", e);
                    }
                }

                /* JADX INFO: renamed from: a */
/* 193 */       public final void m65a(byte[] bArr, int i, int i2, int i3) {
/* 194 */           System.arraycopy(this.f198a, i, bArr, i2, i3);
                }

/* 252 */       public final boolean equals(Object o) {
/* 253 */           if (o == this) {
                        return true;
                    }
/* 257 */           if (!(o instanceof C0621d)) {
                        return false;
                    }
/* 261 */           C0621d c0621d = (C0621d) o;
/* 262 */           int length = this.f198a.length;
/* 263 */           if (length != c0621d.f198a.length) {
                        return false;
                    }
/* 267 */           byte[] bArr = this.f198a;
/* 268 */           byte[] bArr2 = c0621d.f198a;
/* 269 */           for (int i = 0; i < length; i++) {
/* 270 */               if (bArr[i] != bArr2[i]) {
                            return false;
                        }
                    }
                    return true;
                }

/* 281 */       public final int hashCode() {
/* 282 */           int i = this.f199b;
/* 284 */           if (i == 0) {
/* 285 */               byte[] bArr = this.f198a;
/* 286 */               int length = this.f198a.length;
/* 289 */               int i2 = 0;
                        i = length;
                        while (i2 < length) {
/* 290 */                   int i3 = bArr[i2] + (i * 31);
/* 289 */                   i2++;
                            i = i3;
                        }
/* 292 */               if (i == 0) {
/* 293 */                   i = 1;
                        }
/* 296 */               this.f199b = i;
                    }
/* 299 */           return i;
                }

                /* JADX INFO: renamed from: b */
/* 308 */       public final InputStream m66b() {
/* 309 */           return new ByteArrayInputStream(this.f198a);
                }
            }
