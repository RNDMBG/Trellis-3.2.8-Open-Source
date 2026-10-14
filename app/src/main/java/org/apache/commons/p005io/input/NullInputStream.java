            package org.apache.commons.p005io.input;

            import java.io.EOFException;
            import java.io.IOException;
            import java.io.InputStream;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 78 */    public class NullInputStream extends InputStream {
                private boolean eof;
                private long mark;
                private final boolean markSupported;
                private long position;
                private long readlimit;
                private final long size;
                private final boolean throwEofException;

/* 79 */        public NullInputStream(long size) {
/* 80 */            this(size, true, false);
                }

/* 93 */        public NullInputStream(long size, boolean markSupported, boolean throwEofException) {
/* 67 */            this.mark = -1L;
/* 95 */            this.size = size;
/* 96 */            this.markSupported = markSupported;
/* 97 */            this.throwEofException = throwEofException;
                }

/* 105 */       public long getPosition() {
/* 106 */           return this.position;
                }

/* 114 */       public long getSize() {
/* 115 */           return this.size;
                }

                @Override // java.io.InputStream
/* 124 */       public int available() {
/* 125 */           long avail = this.size - this.position;
                    if (avail <= 0) {
/* 127 */               return 0;
                    }
                    if (avail > 2147483647L) {
/* 129 */               return Integer.MAX_VALUE;
                    }
/* 131 */           return (int) avail;
                }

                @Override // java.io.InputStream, java.io.Closeable, java.lang.AutoCloseable
/* 142 */       public void close() throws IOException {
/* 143 */           this.eof = false;
/* 144 */           this.position = 0L;
/* 145 */           this.mark = -1L;
                }

                @Override // java.io.InputStream
/* 156 */       public synchronized void mark(int readlimit) {
                    try {
                        if (!this.markSupported) {
/* 158 */                   throw new UnsupportedOperationException("Mark not supported");
                        }
/* 160 */               this.mark = this.position;
/* 161 */               this.readlimit = readlimit;
                    } catch (Throwable th) {
                        throw th;
                    }
                }

                @Override // java.io.InputStream
/* 170 */       public boolean markSupported() {
/* 171 */           return this.markSupported;
                }

                @Override // java.io.InputStream
/* 185 */       public int read() throws IOException {
/* 186 */           if (this.eof) {
/* 187 */               throw new IOException("Read after end of file");
                    }
                    if (this.position == this.size) {
/* 190 */               return doEndOfFile();
                    }
/* 192 */           this.position++;
/* 193 */           return processByte();
                }

                @Override // java.io.InputStream
/* 208 */       public int read(byte[] bytes) throws IOException {
/* 209 */           return read(bytes, 0, bytes.length);
                }

                @Override // java.io.InputStream
/* 226 */       public int read(byte[] bytes, int offset, int length) throws IOException {
/* 227 */           if (this.eof) {
/* 228 */               throw new IOException("Read after end of file");
                    }
                    if (this.position == this.size) {
/* 231 */               return doEndOfFile();
                    }
/* 233 */           this.position += (long) length;
/* 234 */           int returnLength = length;
                    if (this.position > this.size) {
/* 236 */               returnLength = length - ((int) (this.position - this.size));
/* 237 */               this.position = this.size;
                    }
/* 239 */           processBytes(bytes, offset, returnLength);
/* 240 */           return returnLength;
                }

                @Override // java.io.InputStream
/* 252 */       public synchronized void reset() throws IOException {
                    try {
                        if (!this.markSupported) {
/* 254 */                   throw new UnsupportedOperationException("Mark not supported");
                        }
                        if (this.mark < 0) {
/* 257 */                   throw new IOException("No position has been marked");
                        }
                        if (this.position > this.mark + this.readlimit) {
/* 260 */                   throw new IOException("Marked position [" + this.mark + "] is no longer valid - passed the read limit [" + this.readlimit + "]");
                        }
/* 264 */               this.position = this.mark;
/* 265 */               this.eof = false;
                    } catch (Throwable th) {
                        throw th;
                    }
                }

                @Override // java.io.InputStream
/* 280 */       public long skip(long numberOfBytes) throws IOException {
/* 281 */           if (this.eof) {
/* 282 */               throw new IOException("Skip after end of file");
                    }
                    if (this.position == this.size) {
/* 285 */               return doEndOfFile();
                    }
/* 287 */           this.position += numberOfBytes;
                    if (this.position <= this.size) {
/* 288 */               return numberOfBytes;
                    }
/* 290 */           long returnLength = numberOfBytes - (this.position - this.size);
/* 291 */           this.position = this.size;
/* 293 */           return returnLength;
                }

/* 304 */       protected int processByte() {
/* 305 */           return 0;
                }

/* 319 */       protected void processBytes(byte[] bytes, int offset, int length) {
                }

/* 330 */       private int doEndOfFile() throws EOFException {
/* 331 */           this.eof = true;
/* 332 */           if (this.throwEofException) {
/* 333 */               throw new EOFException();
                    }
/* 335 */           return -1;
                }
            }
