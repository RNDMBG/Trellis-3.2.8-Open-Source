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
            
            
            
    public class C0696aq implements Closeable {

                
                private static final Logger f398a = Logger.getLogger(C0696aq.class.getName());

                
                private final RandomAccessFile f399b;

                
                private int f400c;

                
                private int f401d;

                
                private C0698as f402e;

                
                private C0698as f403f;

                
                private final byte[] f404g = new byte[16];

                
        static  int m266a(C0696aq c0696aq, int i) {
            return c0696aq.m277b(i);
                }

                
        static  RandomAccessFile m269a(C0696aq c0696aq) {
            return c0696aq.f399b;
                }

                
        static  Object m271a(Object obj, String str) {
            return m278b(obj, str);
                }

                
        static  void m274a(C0696aq c0696aq, int i, byte[] bArr, int i2, int i3) throws IOException {
            c0696aq.m279b(i, bArr, i2, i3);
                }

       public C0696aq(File file) throws IOException {
           if (!file.exists()) {
               File file2 = new File(file.getPath() + ".tmp");
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
           this.f399b = m270a(file);
           this.f399b.seek(0L);
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

                
       private static void m275a(byte[] bArr, int i, int i2) {
           bArr[i] = i2 >> 24;
           bArr[i + 1] = (byte) (i2 >> 16);
           bArr[i + 2] = (byte) (i2 >> 8);
           bArr[i + 3] = (byte) i2;
                }

                
       private static void m276a(byte[] bArr, int... iArr) {
                    int i = 0;
                    for (int i2 : iArr) {
               m275a(bArr, i, i2);
               i += 4;
                    }
                }

                
       private static int m267a(byte[] bArr, int i) {
           return ((bArr[i] & Constants.UNKNOWN) << 24) + ((bArr[i + 1] & Constants.UNKNOWN) << 16) + ((bArr[i + 2] & Constants.UNKNOWN) << 8) + (bArr[i + 3] & Constants.UNKNOWN);
                }

                
       private void m272a(int i, int i2, int i3, int i4) throws IOException {
           m276a(this.f404g, i, i2, i3, i4);
           this.f399b.seek(0L);
           this.f399b.write(this.f404g);
                }

                
       private C0698as m268a(int i) throws IOException {
           if (i == 0) {
               return C0698as.f407a;
                    }
           this.f399b.seek(i);
           return new C0698as(i, this.f399b.readInt());
                }

                
       private static RandomAccessFile m270a(File file) throws FileNotFoundException {
           return new RandomAccessFile(file, "rwd");
                }

                
       private int m277b(int i) {
                    return i < this.f400c ? i : (i + 16) - this.f400c;
                }

                
       private void m273a(int i, byte[] bArr, int i2, int i3) throws IOException {
           int iM277b = m277b(i);
           if (iM277b + i3 <= this.f400c) {
               this.f399b.seek(iM277b);
               this.f399b.write(bArr, i2, i3);
               return;
                    }
           int i4 = this.f400c - iM277b;
           this.f399b.seek(iM277b);
           this.f399b.write(bArr, i2, i4);
           this.f399b.seek(16L);
           this.f399b.write(bArr, i2 + i4, i3 - i4);
                }

                
       private void m279b(int i, byte[] bArr, int i2, int i3) throws IOException {
           int iM277b = m277b(i);
           if (iM277b + i3 <= this.f400c) {
               this.f399b.seek(iM277b);
               this.f399b.readFully(bArr, i2, i3);
               return;
                    }
           int i4 = this.f400c - iM277b;
           this.f399b.seek(iM277b);
           this.f399b.readFully(bArr, i2, i4);
           this.f399b.seek(16L);
           this.f399b.readFully(bArr, i2 + i4, i3 - i4);
                }

                
       public final void m286a(byte[] bArr) throws IOException {
           m280b(bArr, 0, bArr.length);
                }

                
       private synchronized void m280b(byte[] bArr, int i, int i2) throws IOException {
                    try {
                        m278b(bArr, "buffer");
               if ((i2 | 0) < 0 || i2 > bArr.length) {
                   throw new IndexOutOfBoundsException();
                        }
               m281c(i2);
               boolean zM288b = m288b();
               C0698as c0698as = new C0698as(zM288b ? 16 : m277b(this.f403f.f408b + 4 + this.f403f.f409c), i2);
               m275a(this.f404g, 0, i2);
               m273a(c0698as.f408b, this.f404g, 0, 4);
               m273a(c0698as.f408b + 4, bArr, 0, i2);
               m272a(this.f400c, this.f401d + 1, zM288b ? c0698as.f408b : this.f402e.f408b, c0698as.f408b);
               this.f403f = c0698as;
               this.f401d++;
               if (zM288b) {
                   this.f402e = this.f403f;
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }

                
       public final int m284a() {
           if (this.f401d == 0) {
               return 16;
                    }
           if (this.f403f.f408b >= this.f402e.f408b) {
               return (this.f403f.f408b - this.f402e.f408b) + 4 + this.f403f.f409c + 16;
                    }
           return (((this.f403f.f408b + 4) + this.f403f.f409c) + this.f400c) - this.f402e.f408b;
                }

                
       public final synchronized boolean m288b() {
                    try {
                    } catch (Throwable th) {
                        throw th;
                    }
                    return this.f401d == 0;
                }

                
       private void m281c(int i) throws IOException {
           int i2 = i + 4;
           int iM284a = this.f400c - m284a();
           if (iM284a < i2) {
               int i3 = this.f400c;
               do {
                   iM284a += i3;
                   i3 <<= 1;
               } while (iM284a < i2);
               m283d(i3);
               int iM277b = m277b(this.f403f.f408b + 4 + this.f403f.f409c);
               if (iM277b < this.f402e.f408b) {
                   FileChannel channel = this.f399b.getChannel();
                   channel.position(this.f400c);
                   int i4 = iM277b - 4;
                            if (channel.transferTo(16L, i4, channel) != i4) {
                       throw new AssertionError("Copied insufficient number of bytes!");
                            }
                        }
               if (this.f403f.f408b < this.f402e.f408b) {
                   int i5 = (this.f400c + this.f403f.f408b) - 16;
                   m272a(i3, this.f401d, this.f402e.f408b, i5);
                   this.f403f = new C0698as(i5, this.f403f.f409c);
                        } else {
                   m272a(i3, this.f401d, this.f402e.f408b, this.f403f.f408b);
                        }
               this.f400c = i3;
                    }
                }

                
       private void m283d(int i) throws IOException {
           this.f399b.setLength(i);
           this.f399b.getChannel().force(true);
                }

                
       public final synchronized void m285a(InterfaceC0700au interfaceC0700au) throws IOException {
                    synchronized (this) {
                        try {
                            int iM277b = this.f402e.f408b;
                   for (int i = 0; i < this.f401d; i++) {
                       C0698as c0698asM268a = m268a(iM277b);
                       interfaceC0700au.mo290a(new C0699at(this, c0698asM268a, (byte) 0), c0698asM268a.f409c);
                                iM277b = m277b(c0698asM268a.f409c + c0698asM268a.f408b + 4);
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }

                
       private static <T> T m278b(T t, String str) {
           if (t == null) {
               throw new NullPointerException(str);
                    }
           return t;
                }

                
       public final synchronized void m289c() throws IOException {
                    try {
                        if (m288b()) {
                   throw new NoSuchElementException();
                        }
               if (this.f401d == 1) {
                   m282d();
                        } else {
                   int iM277b = m277b(this.f402e.f408b + 4 + this.f402e.f409c);
                   m279b(iM277b, this.f404g, 0, 4);
                   int iM267a = m267a(this.f404g, 0);
                   m272a(this.f400c, this.f401d - 1, iM277b, this.f403f.f408b);
                   this.f401d--;
                   this.f402e = new C0698as(iM277b, iM267a);
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }

                
       private synchronized void m282d() throws IOException {
                    try {
                        m272a(4096, 0, 0, 0);
               this.f401d = 0;
               this.f402e = C0698as.f407a;
               this.f403f = C0698as.f407a;
               if (this.f400c > 4096) {
                   m283d(4096);
                        }
               this.f400c = 4096;
                    } catch (Throwable th) {
                        throw th;
                    }
                }

                @Override 
       public synchronized void close() throws IOException {
                    try {
                        this.f399b.close();
                    } catch (Throwable th) {
                        throw th;
                    }
                }

                
       public final boolean m287a(int i, int i2) {
                    return (m284a() + 4) + i <= i2;
                }

       public String toString() {
           StringBuilder sb = new StringBuilder();
           sb.append(getClass().getSimpleName()).append('[');
           sb.append("fileLength=").append(this.f400c);
           sb.append(", size=").append(this.f401d);
           sb.append(", first=").append(this.f402e);
           sb.append(", last=").append(this.f403f);
           sb.append(", element lengths=[");
                    try {
               m285a(new C0697ar(this, sb));
                    } catch (IOException e) {
               f398a.log(Level.WARNING, "read error", (Throwable) e);
                    }
           sb.append("]]");
           return sb.toString();
                }
            }
