            package org.apache.commons.p005io.input;

            import java.io.FilterInputStream;
            import java.io.IOException;
            import java.io.InputStream;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 43 */    public abstract class ProxyInputStream extends FilterInputStream {
/* 44 */        public ProxyInputStream(InputStream proxy) {
/* 45 */            super(proxy);
                }

                @Override // java.io.FilterInputStream, java.io.InputStream
/* 56 */        public int read() throws IOException {
                    try {
/* 57 */                beforeRead(1);
/* 58 */                int b = this.in.read();
/* 59 */                afterRead(b == -1 ? -1 : 1);
/* 63 */                return b;
                    } catch (IOException e) {
/* 62 */                handleIOException(e);
                        return -1;
                    }
                }

                @Override // java.io.FilterInputStream, java.io.InputStream
/* 75 */        public int read(byte[] bts) throws IOException {
                    int length;
/* 76 */            if (bts == null) {
/* 76 */                length = 0;
                    } else {
                        try {
                            length = bts.length;
                        } catch (IOException e) {
/* 81 */                    handleIOException(e);
/* 82 */                    return -1;
                        }
                    }
                    beforeRead(length);
/* 77 */            int n = this.in.read(bts);
/* 78 */            afterRead(n);
/* 82 */            return n;
                }

                @Override // java.io.FilterInputStream, java.io.InputStream
/* 96 */        public int read(byte[] bts, int off, int len) throws IOException {
                    try {
/* 97 */                beforeRead(len);
/* 98 */                int n = this.in.read(bts, off, len);
/* 99 */                afterRead(n);
/* 103 */               return n;
                    } catch (IOException e) {
/* 102 */               handleIOException(e);
/* 103 */               return -1;
                    }
                }

                @Override // java.io.FilterInputStream, java.io.InputStream
/* 115 */       public long skip(long ln) throws IOException {
                    try {
/* 116 */               return this.in.skip(ln);
                    } catch (IOException e) {
/* 118 */               handleIOException(e);
/* 119 */               return 0L;
                    }
                }

                @Override // java.io.FilterInputStream, java.io.InputStream
/* 130 */       public int available() throws IOException {
                    try {
/* 131 */               return super.available();
                    } catch (IOException e) {
/* 133 */               handleIOException(e);
/* 134 */               return 0;
                    }
                }

                @Override // java.io.FilterInputStream, java.io.InputStream, java.io.Closeable, java.lang.AutoCloseable
/* 144 */       public void close() throws IOException {
                    try {
/* 145 */               this.in.close();
                    } catch (IOException e) {
/* 147 */               handleIOException(e);
                    }
                }

                @Override // java.io.FilterInputStream, java.io.InputStream
/* 156 */       public synchronized void mark(int readlimit) {
                    try {
                        this.in.mark(readlimit);
                    } catch (Throwable th) {
                        throw th;
                    }
                }

                @Override // java.io.FilterInputStream, java.io.InputStream
/* 166 */       public synchronized void reset() throws IOException {
                    try {
                        try {
                            this.in.reset();
                        } catch (IOException e) {
/* 169 */                   handleIOException(e);
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }

                @Override // java.io.FilterInputStream, java.io.InputStream
/* 178 */       public boolean markSupported() {
/* 179 */           return this.in.markSupported();
                }

/* 200 */       protected void beforeRead(int n) throws IOException {
                }

/* 220 */       protected void afterRead(int n) throws IOException {
                }

/* 232 */       protected void handleIOException(IOException e) throws IOException {
/* 233 */           throw e;
                }
            }
