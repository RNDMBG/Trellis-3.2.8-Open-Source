            package com.fasterxml.jackson.core.p003io;

            import java.io.IOException;
            import java.io.InputStream;
            import java.io.Reader;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 38 */    abstract class BaseReader extends Reader {
                protected static final int LAST_VALID_UNICODE_CHAR = 1114111;
                protected static final char NULL_BYTE = 0;
                protected static final char NULL_CHAR = 0;
                protected byte[] _buffer;
                protected final IOContext _context;
                protected InputStream _in;
                protected int _length;
                protected int _ptr;
                protected char[] _tmpBuf = null;

/* 39 */        protected BaseReader(IOContext iOContext, InputStream inputStream, byte[] bArr, int i, int i2) {
/* 41 */            this._context = iOContext;
/* 42 */            this._in = inputStream;
/* 43 */            this._buffer = bArr;
/* 44 */            this._ptr = i;
/* 45 */            this._length = i2;
                }

                @Override // java.io.Reader, java.io.Closeable, java.lang.AutoCloseable
/* 56 */        public void close() throws IOException {
/* 57 */            InputStream inputStream = this._in;
/* 59 */            if (inputStream != null) {
/* 60 */                this._in = null;
/* 61 */                freeBuffers();
/* 62 */                inputStream.close();
                    }
                }

                @Override // java.io.Reader
/* 75 */        public int read() throws IOException {
/* 76 */            if (this._tmpBuf == null) {
/* 77 */                this._tmpBuf = new char[1];
                    }
/* 79 */            if (read(this._tmpBuf, 0, 1) < 1) {
/* 80 */                return -1;
                    }
                    return this._tmpBuf[0];
                }

/* 97 */        public final void freeBuffers() {
/* 98 */            byte[] bArr = this._buffer;
/* 99 */            if (bArr != null) {
/* 100 */               this._buffer = null;
/* 101 */               this._context.releaseReadIOBuffer(bArr);
                    }
                }

/* 107 */       protected void reportBounds(char[] cArr, int i, int i2) throws IOException {
/* 108 */           throw new ArrayIndexOutOfBoundsException("read(buf," + i + "," + i2 + "), cbuf[" + cArr.length + "]");
                }

/* 113 */       protected void reportStrangeStream() throws IOException {
/* 114 */           throw new IOException("Strange I/O stream, returned 0 bytes on read");
                }
            }
