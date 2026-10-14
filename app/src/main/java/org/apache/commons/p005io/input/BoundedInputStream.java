            package org.apache.commons.p005io.input;

            import java.io.IOException;
            import java.io.InputStream;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 58 */    public class BoundedInputStream extends InputStream {

                /* JADX INFO: renamed from: in */
                private final InputStream f2538in;
                private long mark;
                private final long max;
                private long pos;
                private boolean propagateClose;

/* 59 */        public BoundedInputStream(InputStream in, long size) {
/* 45 */            this.pos = 0L;
/* 48 */            this.mark = -1L;
/* 51 */            this.propagateClose = true;
/* 63 */            this.max = size;
/* 64 */            this.f2538in = in;
                }

/* 73 */        public BoundedInputStream(InputStream in) {
/* 74 */            this(in, -1L);
                }

                @Override // java.io.InputStream
/* 85 */        public int read() throws IOException {
                    if (this.max >= 0 && this.pos >= this.max) {
/* 87 */                return -1;
                    }
/* 89 */            int i = this.f2538in.read();
/* 90 */            this.pos++;
/* 91 */            return i;
                }

                @Override // java.io.InputStream
/* 102 */       public int read(byte[] b) throws IOException {
/* 103 */           return read(b, 0, b.length);
                }

                @Override // java.io.InputStream
/* 116 */       public int read(byte[] b, int off, int len) throws IOException {
                    if (this.max >= 0 && this.pos >= this.max) {
                        return -1;
                    }
/* 120 */           long maxRead = this.max >= 0 ? Math.min(len, this.max - this.pos) : len;
/* 121 */           int bytesRead = this.f2538in.read(b, off, (int) maxRead);
/* 123 */           if (bytesRead == -1) {
                        return -1;
                    }
/* 127 */           this.pos += (long) bytesRead;
/* 128 */           return bytesRead;
                }

                @Override // java.io.InputStream
/* 138 */       public long skip(long n) throws IOException {
                    long toSkip = this.max >= 0 ? Math.min(n, this.max - this.pos) : n;
/* 140 */           long skippedBytes = this.f2538in.skip(toSkip);
/* 141 */           this.pos += skippedBytes;
/* 142 */           return skippedBytes;
                }

                @Override // java.io.InputStream
/* 149 */       public int available() throws IOException {
                    if (this.max < 0 || this.pos < this.max) {
                        return this.f2538in.available();
                    }
/* 151 */           return 0;
                }

/* 161 */       public String toString() {
/* 162 */           return this.f2538in.toString();
                }

                @Override // java.io.InputStream, java.io.Closeable, java.lang.AutoCloseable
/* 171 */       public void close() throws IOException {
/* 172 */           if (this.propagateClose) {
/* 173 */               this.f2538in.close();
                    }
                }

                @Override // java.io.InputStream
/* 182 */       public synchronized void reset() throws IOException {
                    try {
                        this.f2538in.reset();
/* 184 */               this.pos = this.mark;
                    } catch (Throwable th) {
                        throw th;
                    }
                }

                @Override // java.io.InputStream
/* 192 */       public synchronized void mark(int readlimit) {
                    try {
                        this.f2538in.mark(readlimit);
/* 194 */               this.mark = this.pos;
                    } catch (Throwable th) {
                        throw th;
                    }
                }

                @Override // java.io.InputStream
/* 202 */       public boolean markSupported() {
/* 203 */           return this.f2538in.markSupported();
                }

/* 214 */       public boolean isPropagateClose() {
/* 215 */           return this.propagateClose;
                }

/* 227 */       public void setPropagateClose(boolean propagateClose) {
/* 228 */           this.propagateClose = propagateClose;
                }
            }
