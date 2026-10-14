            package com.fasterxml.jackson.core.p003io;

            import com.flurry.android.Constants;
            import java.io.IOException;
            import java.io.InputStream;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 26 */    public final class MergedStream extends InputStream {
                byte[] _buffer;
                protected final IOContext _context;
                final int _end;
                final InputStream _in;
                int _ptr;

/* 27 */        public MergedStream(IOContext iOContext, InputStream inputStream, byte[] bArr, int i, int i2) {
/* 29 */            this._context = iOContext;
/* 30 */            this._in = inputStream;
/* 31 */            this._buffer = bArr;
/* 32 */            this._ptr = i;
/* 33 */            this._end = i2;
                }

                @Override // java.io.InputStream
/* 38 */        public int available() throws IOException {
                    return this._buffer != null ? this._end - this._ptr : this._in.available();
                }

                @Override // java.io.InputStream, java.io.Closeable, java.lang.AutoCloseable
/* 47 */        public void close() throws IOException {
/* 48 */            freeMergedBuffer();
/* 49 */            this._in.close();
                }

                @Override // java.io.InputStream
/* 54 */        public void mark(int i) {
/* 55 */            if (this._buffer == null) {
/* 56 */                this._in.mark(i);
                    }
                }

                @Override // java.io.InputStream
/* 63 */        public boolean markSupported() {
                    return this._buffer == null && this._in.markSupported();
                }

                @Override // java.io.InputStream
/* 69 */        public int read() throws IOException {
/* 70 */            if (this._buffer == null) {
                        return this._in.read();
                    }
/* 71 */            byte[] bArr = this._buffer;
                    int i = this._ptr;
                    this._ptr = i + 1;
                    int i2 = bArr[i] & Constants.UNKNOWN;
/* 72 */            if (this._ptr >= this._end) {
/* 73 */                freeMergedBuffer();
/* 77 */                return i2;
                    }
/* 77 */            return i2;
                }

                @Override // java.io.InputStream
/* 82 */        public int read(byte[] bArr) throws IOException {
/* 83 */            return read(bArr, 0, bArr.length);
                }

                @Override // java.io.InputStream
/* 88 */        public int read(byte[] bArr, int i, int i2) throws IOException {
/* 89 */            if (this._buffer == null) {
                        return this._in.read(bArr, i, i2);
                    }
/* 90 */            int i3 = this._end - this._ptr;
/* 91 */            if (i2 > i3) {
                        i2 = i3;
                    }
/* 94 */            System.arraycopy(this._buffer, this._ptr, bArr, i, i2);
/* 95 */            this._ptr += i2;
/* 96 */            if (this._ptr >= this._end) {
/* 97 */                freeMergedBuffer();
/* 101 */               return i2;
                    }
/* 101 */           return i2;
                }

                @Override // java.io.InputStream
/* 106 */       public void reset() throws IOException {
/* 107 */           if (this._buffer == null) {
/* 108 */               this._in.reset();
                    }
                }

                @Override // java.io.InputStream
/* 116 */       public long skip(long j) throws IOException {
                    long jSkip;
/* 117 */           if (this._buffer != null) {
/* 118 */               int i = this._end - this._ptr;
                        if (i > j) {
/* 121 */                   this._ptr += (int) j;
/* 132 */                   return j;
                        }
/* 124 */               freeMergedBuffer();
/* 125 */               jSkip = ((long) i) + 0;
/* 126 */               j -= (long) i;
                    } else {
                        jSkip = 0;
                    }
                    if (j > 0) {
/* 130 */               jSkip += this._in.skip(j);
                    }
                    return jSkip;
                }

/* 136 */       private void freeMergedBuffer() {
/* 137 */           byte[] bArr = this._buffer;
/* 138 */           if (bArr != null) {
/* 139 */               this._buffer = null;
/* 140 */               if (this._context != null) {
/* 141 */                   this._context.releaseReadIOBuffer(bArr);
                        }
                    }
                }
            }
