            package org.apache.commons.p005io.input;

            import java.io.FilterReader;
            import java.io.IOException;
            import java.io.Reader;
            import java.nio.CharBuffer;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 41 */    public abstract class ProxyReader extends FilterReader {
/* 42 */        public ProxyReader(Reader proxy) {
/* 43 */            super(proxy);
                }

                @Override // java.io.FilterReader, java.io.Reader
/* 54 */        public int read() throws IOException {
                    try {
/* 55 */                beforeRead(1);
/* 56 */                int c = this.in.read();
/* 57 */                afterRead(c == -1 ? -1 : 1);
/* 61 */                return c;
                    } catch (IOException e) {
/* 60 */                handleIOException(e);
                        return -1;
                    }
                }

                @Override // java.io.Reader
/* 73 */        public int read(char[] chr) throws IOException {
                    int length;
/* 74 */            if (chr == null) {
/* 74 */                length = 0;
                    } else {
                        try {
                            length = chr.length;
                        } catch (IOException e) {
/* 79 */                    handleIOException(e);
/* 80 */                    return -1;
                        }
                    }
                    beforeRead(length);
/* 75 */            int n = this.in.read(chr);
/* 76 */            afterRead(n);
/* 80 */            return n;
                }

                @Override // java.io.FilterReader, java.io.Reader
/* 94 */        public int read(char[] chr, int st, int len) throws IOException {
                    try {
/* 95 */                beforeRead(len);
/* 96 */                int n = this.in.read(chr, st, len);
/* 97 */                afterRead(n);
/* 101 */               return n;
                    } catch (IOException e) {
/* 100 */               handleIOException(e);
/* 101 */               return -1;
                    }
                }

                @Override // java.io.Reader, java.lang.Readable
/* 114 */       public int read(CharBuffer target) throws IOException {
                    int length;
/* 115 */           if (target == null) {
/* 115 */               length = 0;
                    } else {
                        try {
                            length = target.length();
                        } catch (IOException e) {
/* 120 */                   handleIOException(e);
/* 121 */                   return -1;
                        }
                    }
                    beforeRead(length);
/* 116 */           int n = this.in.read(target);
/* 117 */           afterRead(n);
/* 121 */           return n;
                }

                @Override // java.io.FilterReader, java.io.Reader
/* 133 */       public long skip(long ln) throws IOException {
                    try {
/* 134 */               return this.in.skip(ln);
                    } catch (IOException e) {
/* 136 */               handleIOException(e);
/* 137 */               return 0L;
                    }
                }

                @Override // java.io.FilterReader, java.io.Reader
/* 148 */       public boolean ready() throws IOException {
                    try {
/* 149 */               return this.in.ready();
                    } catch (IOException e) {
/* 151 */               handleIOException(e);
/* 152 */               return false;
                    }
                }

                @Override // java.io.FilterReader, java.io.Reader, java.io.Closeable, java.lang.AutoCloseable
/* 162 */       public void close() throws IOException {
                    try {
/* 163 */               this.in.close();
                    } catch (IOException e) {
/* 165 */               handleIOException(e);
                    }
                }

                @Override // java.io.FilterReader, java.io.Reader
/* 176 */       public synchronized void mark(int idx) throws IOException {
                    try {
                        try {
                            this.in.mark(idx);
                        } catch (IOException e) {
/* 179 */                   handleIOException(e);
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }

                @Override // java.io.FilterReader, java.io.Reader
/* 189 */       public synchronized void reset() throws IOException {
                    try {
                        try {
                            this.in.reset();
                        } catch (IOException e) {
/* 192 */                   handleIOException(e);
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }

                @Override // java.io.FilterReader, java.io.Reader
/* 201 */       public boolean markSupported() {
/* 202 */           return this.in.markSupported();
                }

/* 223 */       protected void beforeRead(int n) throws IOException {
                }

/* 243 */       protected void afterRead(int n) throws IOException {
                }

/* 255 */       protected void handleIOException(IOException e) throws IOException {
/* 256 */           throw e;
                }
            }
