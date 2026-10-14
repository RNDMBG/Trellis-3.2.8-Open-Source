            package org.apache.commons.p005io.input;

            import java.io.EOFException;
            import java.io.IOException;
            import java.io.Reader;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 78 */    public class NullReader extends Reader {
                private boolean eof;
                private long mark;
                private final boolean markSupported;
                private long position;
                private long readlimit;
                private final long size;
                private final boolean throwEofException;

/* 79 */        public NullReader(long size) {
/* 80 */            this(size, true, false);
                }

/* 93 */        public NullReader(long size, boolean markSupported, boolean throwEofException) {
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

                @Override // java.io.Reader, java.io.Closeable, java.lang.AutoCloseable
/* 125 */       public void close() throws IOException {
/* 126 */           this.eof = false;
/* 127 */           this.position = 0L;
/* 128 */           this.mark = -1L;
                }

                @Override // java.io.Reader
/* 139 */       public synchronized void mark(int readlimit) {
                    try {
                        if (!this.markSupported) {
/* 141 */                   throw new UnsupportedOperationException("Mark not supported");
                        }
/* 143 */               this.mark = this.position;
/* 144 */               this.readlimit = readlimit;
                    } catch (Throwable th) {
                        throw th;
                    }
                }

                @Override // java.io.Reader
/* 153 */       public boolean markSupported() {
/* 154 */           return this.markSupported;
                }

                @Override // java.io.Reader
/* 168 */       public int read() throws IOException {
/* 169 */           if (this.eof) {
/* 170 */               throw new IOException("Read after end of file");
                    }
                    if (this.position == this.size) {
/* 173 */               return doEndOfFile();
                    }
/* 175 */           this.position++;
/* 176 */           return processChar();
                }

                @Override // java.io.Reader
/* 191 */       public int read(char[] chars) throws IOException {
/* 192 */           return read(chars, 0, chars.length);
                }

                @Override // java.io.Reader
/* 209 */       public int read(char[] chars, int offset, int length) throws IOException {
/* 210 */           if (this.eof) {
/* 211 */               throw new IOException("Read after end of file");
                    }
                    if (this.position == this.size) {
/* 214 */               return doEndOfFile();
                    }
/* 216 */           this.position += (long) length;
/* 217 */           int returnLength = length;
                    if (this.position > this.size) {
/* 219 */               returnLength = length - ((int) (this.position - this.size));
/* 220 */               this.position = this.size;
                    }
/* 222 */           processChars(chars, offset, returnLength);
/* 223 */           return returnLength;
                }

                @Override // java.io.Reader
/* 235 */       public synchronized void reset() throws IOException {
                    try {
                        if (!this.markSupported) {
/* 237 */                   throw new UnsupportedOperationException("Mark not supported");
                        }
                        if (this.mark < 0) {
/* 240 */                   throw new IOException("No position has been marked");
                        }
                        if (this.position > this.mark + this.readlimit) {
/* 243 */                   throw new IOException("Marked position [" + this.mark + "] is no longer valid - passed the read limit [" + this.readlimit + "]");
                        }
/* 247 */               this.position = this.mark;
/* 248 */               this.eof = false;
                    } catch (Throwable th) {
                        throw th;
                    }
                }

                @Override // java.io.Reader
/* 263 */       public long skip(long numberOfChars) throws IOException {
/* 264 */           if (this.eof) {
/* 265 */               throw new IOException("Skip after end of file");
                    }
                    if (this.position == this.size) {
/* 268 */               return doEndOfFile();
                    }
/* 270 */           this.position += numberOfChars;
                    if (this.position <= this.size) {
/* 271 */               return numberOfChars;
                    }
/* 273 */           long returnLength = numberOfChars - (this.position - this.size);
/* 274 */           this.position = this.size;
/* 276 */           return returnLength;
                }

/* 287 */       protected int processChar() {
/* 288 */           return 0;
                }

/* 302 */       protected void processChars(char[] chars, int offset, int length) {
                }

/* 313 */       private int doEndOfFile() throws EOFException {
/* 314 */           this.eof = true;
/* 315 */           if (this.throwEofException) {
/* 316 */               throw new EOFException();
                    }
/* 318 */           return -1;
                }
            }
