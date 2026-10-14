            package com.crashlytics.android.internal;

            import com.flurry.android.Constants;
            import java.io.Closeable;
            import java.io.File;
            import java.io.FileNotFoundException;
            import java.io.IOException;
            import java.io.RandomAccessFile;
            import java.nio.channels.FileChannel;
            import java.util.NoSuchElementException;
            import java.util.logging.Level;
            import java.util.logging.Logger;
            
            /* JADX INFO: renamed from: com.crashlytics.android.internal.aq */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 54 */    public class C0696aq implements Closeable {

                /* JADX INFO: renamed from: a */
                private static final Logger f398a = Logger.getLogger(C0696aq.class.getName());

                /* JADX INFO: renamed from: b */
                private final RandomAccessFile f399b;

                /* JADX INFO: renamed from: c */
                private int f400c;

                /* JADX INFO: renamed from: d */
                private int f401d;

                /* JADX INFO: renamed from: e */
                private C0698as f402e;

                /* JADX INFO: renamed from: f */
                private C0698as f403f;

                /* JADX INFO: renamed from: g */
                private final byte[] f404g = new byte[16];

                /* JADX INFO: renamed from: a */
/* 55 */        static /* synthetic */ int m266a(C0696aq c0696aq, int i) {
/* 56 */            return c0696aq.m277b(i);
                }

                /* JADX INFO: renamed from: a */
/* 55 */        static /* synthetic */ RandomAccessFile m269a(C0696aq c0696aq) {
/* 56 */            return c0696aq.f399b;
                }

                /* JADX INFO: renamed from: a */
/* 55 */        static /* synthetic */ Object m271a(Object obj, String str) {
/* 56 */            return m278b(obj, str);
                }

                /* JADX INFO: renamed from: a */
/* 55 */        static /* synthetic */ void m274a(C0696aq c0696aq, int i, byte[] bArr, int i2, int i3) throws IOException {
/* 56 */            c0696aq.m279b(i, bArr, i2, i3);
                }

/* 109 */       public C0696aq(File file) throws IOException {
/* 111 */           if (!file.exists()) {
/* 112 */               File file2 = new File(file.getPath() + ".tmp");
                        RandomAccessFile randomAccessFileM270a = m270a(file2);
                        try {
                            randomAccessFileM270a.setLength(4096L);
                            randomAccessFileM270a.seek(0L);
                            byte[] bArr = new byte[16];
                            m276a(bArr, 4096, 0, 0, 0);
                            randomAccessFileM270a.write(bArr);
                            randomAccessFileM270a.close();
                            if (!file2.renameTo(file)) {
                                throw new IOException("Rename failed!");
                            }
                        } catch (Throwable th) {
                            randomAccessFileM270a.close();
                            throw th;
                        }
                    }
/* 114 */           this.f399b = m270a(file);
/* 115 */           this.f399b.seek(0L);
                    this.f399b.readFully(this.f404g);
                    this.f400c = m267a(this.f404g, 0);
                    if (this.f400c > this.f399b.length()) {
                        throw new IOException("File is truncated. Expected length: " + this.f400c + ", Actual length: " + this.f399b.length());
                    }
                    this.f401d = m267a(this.f404g, 4);
                    int iM267a = m267a(this.f404g, 8);
                    int iM267a2 = m267a(this.f404g, 12);
                    this.f402e = m268a(iM267a);
                    this.f403f = m268a(iM267a2);
                }

                /* JADX INFO: renamed from: a */
/* 127 */       private static void m275a(byte[] bArr, int i, int i2) {
/* 128 */           bArr[i] = i2 >> 24;
/* 129 */           bArr[i + 1] = (byte) (i2 >> 16);
/* 130 */           bArr[i + 2] = (byte) (i2 >> 8);
/* 131 */           bArr[i + 3] = (byte) i2;
                }

                /* JADX INFO: renamed from: a */
/* 139 */       private static void m276a(byte[] bArr, int... iArr) {
                    int i = 0;
                    for (int i2 : iArr) {
/* 141 */               m275a(bArr, i, i2);
/* 142 */               i += 4;
                    }
                }

                /* JADX INFO: renamed from: a */
/* 147 */       private static int m267a(byte[] bArr, int i) {
/* 148 */           return ((bArr[i] & Constants.UNKNOWN) << 24) + ((bArr[i + 1] & Constants.UNKNOWN) << 16) + ((bArr[i + 2] & Constants.UNKNOWN) << 8) + (bArr[i + 3] & Constants.UNKNOWN);
                }

                /* JADX INFO: renamed from: a */
/* 174 */       private void m272a(int i, int i2, int i3, int i4) throws IOException {
/* 175 */           m276a(this.f404g, i, i2, i3, i4);
/* 176 */           this.f399b.seek(0L);
/* 177 */           this.f399b.write(this.f404g);
                }

                /* JADX INFO: renamed from: a */
/* 181 */       private C0698as m268a(int i) throws IOException {
/* 182 */           if (i == 0) {
/* 183 */               return C0698as.f407a;
                    }
/* 185 */           this.f399b.seek(i);
/* 186 */           return new C0698as(i, this.f399b.readInt());
                }

                /* JADX INFO: renamed from: a */
/* 211 */       private static RandomAccessFile m270a(File file) throws FileNotFoundException {
/* 212 */           return new RandomAccessFile(file, "rwd");
                }

                /* JADX INFO: renamed from: b */
/* 216 */       private int m277b(int i) {
                    return i < this.f400c ? i : (i + 16) - this.f400c;
                }

                /* JADX INFO: renamed from: a */
/* 228 */       private void m273a(int i, byte[] bArr, int i2, int i3) throws IOException {
/* 229 */           int iM277b = m277b(i);
/* 230 */           if (iM277b + i3 <= this.f400c) {
/* 231 */               this.f399b.seek(iM277b);
/* 232 */               this.f399b.write(bArr, i2, i3);
/* 242 */               return;
                    }
/* 236 */           int i4 = this.f400c - iM277b;
/* 237 */           this.f399b.seek(iM277b);
/* 238 */           this.f399b.write(bArr, i2, i4);
/* 239 */           this.f399b.seek(16L);
/* 240 */           this.f399b.write(bArr, i2 + i4, i3 - i4);
                }

                /* JADX INFO: renamed from: b */
/* 251 */       private void m279b(int i, byte[] bArr, int i2, int i3) throws IOException {
/* 252 */           int iM277b = m277b(i);
/* 253 */           if (iM277b + i3 <= this.f400c) {
/* 254 */               this.f399b.seek(iM277b);
/* 255 */               this.f399b.readFully(bArr, i2, i3);
/* 265 */               return;
                    }
/* 259 */           int i4 = this.f400c - iM277b;
/* 260 */           this.f399b.seek(iM277b);
/* 261 */           this.f399b.readFully(bArr, i2, i4);
/* 262 */           this.f399b.seek(16L);
/* 263 */           this.f399b.readFully(bArr, i2 + i4, i3 - i4);
                }

                /* JADX INFO: renamed from: a */
/* 272 */       public final void m286a(byte[] bArr) throws IOException {
/* 273 */           m280b(bArr, 0, bArr.length);
                }

                /* JADX INFO: renamed from: b */
/* 285 */       private synchronized void m280b(byte[] bArr, int i, int i2) throws IOException {
                    try {
                        m278b(bArr, "buffer");
/* 287 */               if ((i2 | 0) < 0 || i2 > bArr.length) {
/* 288 */                   throw new IndexOutOfBoundsException();
                        }
/* 291 */               m281c(i2);
/* 294 */               boolean zM288b = m288b();
/* 296 */               C0698as c0698as = new C0698as(zM288b ? 16 : m277b(this.f403f.f408b + 4 + this.f403f.f409c), i2);
/* 299 */               m275a(this.f404g, 0, i2);
/* 300 */               m273a(c0698as.f408b, this.f404g, 0, 4);
/* 303 */               m273a(c0698as.f408b + 4, bArr, 0, i2);
/* 307 */               m272a(this.f400c, this.f401d + 1, zM288b ? c0698as.f408b : this.f402e.f408b, c0698as.f408b);
/* 308 */               this.f403f = c0698as;
/* 309 */               this.f401d++;
/* 310 */               if (zM288b) {
/* 311 */                   this.f402e = this.f403f;
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }

                /* JADX INFO: renamed from: a */
/* 316 */       public final int m284a() {
/* 317 */           if (this.f401d == 0) {
/* 318 */               return 16;
                    }
/* 321 */           if (this.f403f.f408b >= this.f402e.f408b) {
/* 323 */               return (this.f403f.f408b - this.f402e.f408b) + 4 + this.f403f.f409c + 16;
                    }
/* 328 */           return (((this.f403f.f408b + 4) + this.f403f.f409c) + this.f400c) - this.f402e.f408b;
                }

                /* JADX INFO: renamed from: b */
/* 340 */       public final synchronized boolean m288b() {
                    try {
                    } catch (Throwable th) {
                        throw th;
                    }
                    return this.f401d == 0;
                }

                /* JADX INFO: renamed from: c */
/* 349 */       private void m281c(int i) throws IOException {
/* 350 */           int i2 = i + 4;
/* 351 */           int iM284a = this.f400c - m284a();
/* 352 */           if (iM284a < i2) {
/* 357 */               int i3 = this.f400c;
/* 364 */               do {
/* 361 */                   iM284a += i3;
/* 362 */                   i3 <<= 1;
/* 364 */               } while (iM284a < i2);
/* 366 */               m283d(i3);
/* 369 */               int iM277b = m277b(this.f403f.f408b + 4 + this.f403f.f409c);
/* 372 */               if (iM277b < this.f402e.f408b) {
/* 373 */                   FileChannel channel = this.f399b.getChannel();
/* 374 */                   channel.position(this.f400c);
/* 375 */                   int i4 = iM277b - 4;
                            if (channel.transferTo(16L, i4, channel) != i4) {
/* 377 */                       throw new AssertionError("Copied insufficient number of bytes!");
                            }
                        }
/* 382 */               if (this.f403f.f408b < this.f402e.f408b) {
/* 383 */                   int i5 = (this.f400c + this.f403f.f408b) - 16;
/* 384 */                   m272a(i3, this.f401d, this.f402e.f408b, i5);
/* 385 */                   this.f403f = new C0698as(i5, this.f403f.f409c);
                        } else {
/* 387 */                   m272a(i3, this.f401d, this.f402e.f408b, this.f403f.f408b);
                        }
/* 390 */               this.f400c = i3;
                    }
                }

                /* JADX INFO: renamed from: d */
/* 395 */       private void m283d(int i) throws IOException {
/* 396 */           this.f399b.setLength(i);
/* 397 */           this.f399b.getChannel().force(true);
                }

                /* JADX INFO: renamed from: a */
/* 421 */       public final synchronized void m285a(InterfaceC0700au interfaceC0700au) throws IOException {
                    synchronized (this) {
                        try {
                            int iM277b = this.f402e.f408b;
/* 423 */                   for (int i = 0; i < this.f401d; i++) {
/* 424 */                       C0698as c0698asM268a = m268a(iM277b);
/* 425 */                       interfaceC0700au.mo290a(new C0699at(this, c0698asM268a, (byte) 0), c0698asM268a.f409c);
                                iM277b = m277b(c0698asM268a.f409c + c0698asM268a.f408b + 4);
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }

                /* JADX INFO: renamed from: b */
/* 435 */       private static <T> T m278b(T t, String str) {
/* 436 */           if (t == null) {
/* 437 */               throw new NullPointerException(str);
                    }
/* 439 */           return t;
                }

                /* JADX INFO: renamed from: c */
/* 494 */       public final synchronized void m289c() throws IOException {
                    try {
                        if (m288b()) {
/* 496 */                   throw new NoSuchElementException();
                        }
/* 498 */               if (this.f401d == 1) {
/* 499 */                   m282d();
                        } else {
/* 502 */                   int iM277b = m277b(this.f402e.f408b + 4 + this.f402e.f409c);
/* 503 */                   m279b(iM277b, this.f404g, 0, 4);
/* 504 */                   int iM267a = m267a(this.f404g, 0);
/* 505 */                   m272a(this.f400c, this.f401d - 1, iM277b, this.f403f.f408b);
/* 506 */                   this.f401d--;
/* 507 */                   this.f402e = new C0698as(iM277b, iM267a);
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }

                /* JADX INFO: renamed from: d */
/* 512 */       private synchronized void m282d() throws IOException {
                    try {
                        m272a(4096, 0, 0, 0);
/* 514 */               this.f401d = 0;
/* 515 */               this.f402e = C0698as.f407a;
/* 516 */               this.f403f = C0698as.f407a;
/* 517 */               if (this.f400c > 4096) {
/* 518 */                   m283d(4096);
                        }
/* 519 */               this.f400c = 4096;
                    } catch (Throwable th) {
                        throw th;
                    }
                }

                @Override // java.io.Closeable, java.lang.AutoCloseable
/* 523 */       public synchronized void close() throws IOException {
                    try {
                        this.f399b.close();
                    } catch (Throwable th) {
                        throw th;
                    }
                }

                /* JADX INFO: renamed from: a */
/* 531 */       public final boolean m287a(int i, int i2) {
                    return (m284a() + 4) + i <= i2;
                }

/* 536 */       public String toString() {
/* 537 */           StringBuilder sb = new StringBuilder();
/* 538 */           sb.append(getClass().getSimpleName()).append('[');
/* 539 */           sb.append("fileLength=").append(this.f400c);
/* 540 */           sb.append(", size=").append(this.f401d);
/* 541 */           sb.append(", first=").append(this.f402e);
/* 542 */           sb.append(", last=").append(this.f403f);
/* 543 */           sb.append(", element lengths=[");
                    try {
/* 545 */               m285a(new C0697ar(this, sb));
                    } catch (IOException e) {
/* 559 */               f398a.log(Level.WARNING, "read error", (Throwable) e);
                    }
/* 561 */           sb.append("]]");
/* 562 */           return sb.toString();
                }
            }
