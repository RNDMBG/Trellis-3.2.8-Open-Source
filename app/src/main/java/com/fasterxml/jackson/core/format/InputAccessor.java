            package com.fasterxml.jackson.core.format;

            import com.fasterxml.jackson.core.JsonFactory;
            import java.io.EOFException;
            import java.io.IOException;
            import java.io.InputStream;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 62 */    public interface InputAccessor {
                boolean hasMoreBytes() throws IOException;

                byte nextByte() throws IOException;

                void reset();

/* 63 */        public static class Std implements InputAccessor {
                    protected final byte[] _buffer;
                    protected int _bufferedEnd;
                    protected final int _bufferedStart;
                    protected final InputStream _in;
                    protected int _ptr;

/* 64 */            public Std(InputStream inputStream, byte[] bArr) {
/* 66 */                this._in = inputStream;
/* 67 */                this._buffer = bArr;
/* 68 */                this._bufferedStart = 0;
/* 69 */                this._ptr = 0;
/* 70 */                this._bufferedEnd = 0;
                    }

/* 77 */            public Std(byte[] bArr) {
/* 79 */                this._in = null;
/* 80 */                this._buffer = bArr;
/* 82 */                this._bufferedStart = 0;
/* 83 */                this._bufferedEnd = bArr.length;
                    }

/* 92 */            public Std(byte[] bArr, int i, int i2) {
/* 94 */                this._in = null;
/* 95 */                this._buffer = bArr;
/* 96 */                this._ptr = i;
/* 97 */                this._bufferedStart = i;
/* 98 */                this._bufferedEnd = i + i2;
                    }

                    @Override // com.fasterxml.jackson.core.format.InputAccessor
/* 103 */           public boolean hasMoreBytes() throws IOException {
                        int length;
                        int i;
/* 104 */               if (this._ptr < this._bufferedEnd) {
                            return true;
                        }
/* 107 */               if (this._in != null && (length = this._buffer.length - this._ptr) >= 1 && (i = this._in.read(this._buffer, this._ptr, length)) > 0) {
/* 118 */                   this._bufferedEnd += i;
                            return true;
                        }
                        return false;
                    }

                    @Override // com.fasterxml.jackson.core.format.InputAccessor
/* 125 */           public byte nextByte() throws IOException {
/* 126 */               if (this._ptr >= this._bufferedEnd && !hasMoreBytes()) {
/* 128 */                   throw new EOFException("Failed auto-detect: could not read more than " + this._ptr + " bytes (max buffer size: " + this._buffer.length + ")");
                        }
/* 131 */               byte[] bArr = this._buffer;
                        int i = this._ptr;
                        this._ptr = i + 1;
                        return bArr[i];
                    }

                    @Override // com.fasterxml.jackson.core.format.InputAccessor
/* 135 */           public void reset() {
/* 136 */               this._ptr = this._bufferedStart;
                    }

/* 146 */           public DataFormatMatcher createMatcher(JsonFactory jsonFactory, MatchStrength matchStrength) {
/* 147 */               return new DataFormatMatcher(this._in, this._buffer, this._bufferedStart, this._bufferedEnd - this._bufferedStart, jsonFactory, matchStrength);
                    }
                }
            }
