            package com.fasterxml.jackson.core.p003io;

            import com.flurry.android.Constants;
            import java.io.CharConversionException;
            import java.io.IOException;
            import java.io.InputStream;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 8 */     public class UTF32Reader extends BaseReader {
                protected final boolean _bigEndian;
                protected int _byteCount;
                protected int _charCount;
                protected final boolean _managedBuffers;
                protected char _surrogate;



/* 43 */        public UTF32Reader(IOContext iOContext, InputStream inputStream, byte[] bArr, int i, int i2, boolean z) {
/* 44 */            super(iOContext, inputStream, bArr, i, i2);
/* 20 */            this._surrogate = (char) 0;
/* 25 */            this._charCount = 0;
/* 30 */            this._byteCount = 0;
/* 45 */            this._bigEndian = z;
/* 46 */            this._managedBuffers = inputStream != null;
                }

                @Override // java.io.Reader
/* 59 */        public int read(char[] cArr, int i, int i2) throws IOException {
                    int i3;
                    int i4;
                    int i5;
/* 60 */            if (this._buffer == null) {
                        return -1;
                    }
/* 63 */            if (i2 >= 1) {
/* 67 */                if (i < 0 || i + i2 > cArr.length) {
/* 68 */                    reportBounds(cArr, i, i2);
                        }
/* 71 */                int i6 = i2 + i;
/* 75 */                if (this._surrogate != 0) {
/* 76 */                    i3 = i + 1;
                            cArr[i] = this._surrogate;
/* 77 */                    this._surrogate = (char) 0;
                        } else {
/* 83 */                    int i7 = this._length - this._ptr;
/* 84 */                    if (i7 < 4 && !loadMore(i7)) {
                                return -1;
                            }
                            i3 = i;
                        }
/* 92 */                while (i3 < i6) {
/* 93 */                    int i8 = this._ptr;
/* 96 */                    if (this._bigEndian) {
/* 97 */                        i5 = (this._buffer[i8 + 3] & Constants.UNKNOWN) | (this._buffer[i8] << 24) | ((this._buffer[i8 + 1] & Constants.UNKNOWN) << 16) | ((this._buffer[i8 + 2] & Constants.UNKNOWN) << 8);
                            } else {
/* 100 */                       i5 = (this._buffer[i8 + 3] << 24) | (this._buffer[i8] & Constants.UNKNOWN) | ((this._buffer[i8 + 1] & Constants.UNKNOWN) << 8) | ((this._buffer[i8 + 2] & Constants.UNKNOWN) << 16);
                            }
/* 103 */                   this._ptr += 4;
/* 107 */                   if (i5 > 65535) {
/* 108 */                       if (i5 > 1114111) {
/* 109 */                           reportInvalid(i5, i3 - i, "(above " + Integer.toHexString(1114111) + ") ");
                                }
/* 112 */                       int i9 = i5 - 65536;
/* 113 */                       i4 = i3 + 1;
                                cArr[i3] = (char) (55296 + (i9 >> 10));
/* 115 */                       i5 = (i9 & 1023) | 56320;
/* 117 */                       if (i4 >= i6) {
/* 118 */                           this._surrogate = (char) i5;
                                }
/* 128 */                       int i10 = i4 - i;
/* 129 */                       this._charCount += i10;
/* 130 */                       return i10;
                            }
                            i4 = i3;
/* 122 */                   i3 = i4 + 1;
                            cArr[i4] = (char) i5;
/* 123 */                   if (this._ptr >= this._length) {
                                i4 = i3;
/* 128 */                       int i11 = i4 - i;
/* 129 */                       this._charCount += i11;
/* 130 */                       return i11;
                            }
                        }
                        i4 = i3;
/* 128 */               int i12 = i4 - i;
/* 129 */               this._charCount += i12;
/* 130 */               return i12;
                    }
/* 130 */           return i2;
                }

/* 141 */       private void reportUnexpectedEOF(int i, int i2) throws IOException {
/* 145 */           throw new CharConversionException("Unexpected EOF in the middle of a 4-byte UTF-32 char: got " + i + ", needed " + i2 + ", at char #" + this._charCount + ", byte #" + (this._byteCount + i) + ")");
                }

/* 151 */       private void reportInvalid(int i, int i2, String str) throws IOException {
/* 155 */           throw new CharConversionException("Invalid UTF-32 character 0x" + Integer.toHexString(i) + str + " at char #" + (this._charCount + i2) + ", byte #" + ((this._byteCount + this._ptr) - 1) + ")");
                }

/* 167 */       private boolean loadMore(int i) throws IOException {
/* 168 */           this._byteCount += this._length - i;
/* 171 */           if (i > 0) {
/* 172 */               if (this._ptr > 0) {
/* 173 */                   for (int i2 = 0; i2 < i; i2++) {
/* 174 */                       this._buffer[i2] = this._buffer[this._ptr + i2];
                            }
/* 176 */                   this._ptr = 0;
                        }
/* 178 */               this._length = i;
                    } else {
/* 183 */               this._ptr = 0;
/* 184 */               int i3 = this._in == null ? -1 : this._in.read(this._buffer);
/* 185 */               if (i3 < 1) {
/* 186 */                   this._length = 0;
/* 187 */                   if (i3 < 0) {
/* 188 */                       if (!this._managedBuffers) {
                                    return false;
                                }
/* 189 */                       freeBuffers();
                                return false;
                            }
/* 194 */                   reportStrangeStream();
                        }
/* 196 */               this._length = i3;
                    }
/* 202 */           while (this._length < 4) {
/* 203 */               int i4 = this._in == null ? -1 : this._in.read(this._buffer, this._length, this._buffer.length - this._length);
/* 204 */               if (i4 < 1) {
/* 205 */                   if (i4 < 0) {
/* 206 */                       if (this._managedBuffers) {
/* 207 */                           freeBuffers();
                                }
/* 209 */                       reportUnexpectedEOF(this._length, 4);
                            }
/* 212 */                   reportStrangeStream();
                        }
/* 214 */               this._length = i4 + this._length;
                    }
                    return true;
                }
            }
