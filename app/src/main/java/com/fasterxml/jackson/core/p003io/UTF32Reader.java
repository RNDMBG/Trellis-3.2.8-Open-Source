            package com.fasterxml.jackson.core.p003io;

            import com.flurry.android.Constants;
            import java.io.CharConversionException;
            import java.io.IOException;
            import java.io.InputStream;
            
            
     public class UTF32Reader extends BaseReader {
                protected final boolean _bigEndian;
                protected int _byteCount;
                protected int _charCount;
                protected final boolean _managedBuffers;
                protected char _surrogate;



        public UTF32Reader(IOContext iOContext, InputStream inputStream, byte[] bArr, int i, int i2, boolean z) {
            super(iOContext, inputStream, bArr, i, i2);
            this._surrogate = (char) 0;
            this._charCount = 0;
            this._byteCount = 0;
            this._bigEndian = z;
            this._managedBuffers = inputStream != null;
                }

                @Override 
        public int read(char[] cArr, int i, int i2) throws IOException {
                    int i3;
                    int i4;
                    int i5;
            if (this._buffer == null) {
                        return -1;
                    }
            if (i2 >= 1) {
                if (i < 0 || i + i2 > cArr.length) {
                    reportBounds(cArr, i, i2);
                        }
                int i6 = i2 + i;
                if (this._surrogate != 0) {
                    i3 = i + 1;
                            cArr[i] = this._surrogate;
                    this._surrogate = (char) 0;
                        } else {
                    int i7 = this._length - this._ptr;
                    if (i7 < 4 && !loadMore(i7)) {
                                return -1;
                            }
                            i3 = i;
                        }
                while (i3 < i6) {
                    int i8 = this._ptr;
                    if (this._bigEndian) {
                        i5 = (this._buffer[i8 + 3] & Constants.UNKNOWN) | (this._buffer[i8] << 24) | ((this._buffer[i8 + 1] & Constants.UNKNOWN) << 16) | ((this._buffer[i8 + 2] & Constants.UNKNOWN) << 8);
                            } else {
                       i5 = (this._buffer[i8 + 3] << 24) | (this._buffer[i8] & Constants.UNKNOWN) | ((this._buffer[i8 + 1] & Constants.UNKNOWN) << 8) | ((this._buffer[i8 + 2] & Constants.UNKNOWN) << 16);
                            }
                   this._ptr += 4;
                   if (i5 > 65535) {
                       if (i5 > 1114111) {
                           reportInvalid(i5, i3 - i, "(above " + Integer.toHexString(1114111) + ") ");
                                }
                       int i9 = i5 - 65536;
                       i4 = i3 + 1;
                                cArr[i3] = (char) (55296 + (i9 >> 10));
                       i5 = (i9 & 1023) | 56320;
                       if (i4 >= i6) {
                           this._surrogate = (char) i5;
                                }
                       int i10 = i4 - i;
                       this._charCount += i10;
                       return i10;
                            }
                            i4 = i3;
                   i3 = i4 + 1;
                            cArr[i4] = (char) i5;
                   if (this._ptr >= this._length) {
                                i4 = i3;
                       int i11 = i4 - i;
                       this._charCount += i11;
                       return i11;
                            }
                        }
                        i4 = i3;
               int i12 = i4 - i;
               this._charCount += i12;
               return i12;
                    }
           return i2;
                }

       private void reportUnexpectedEOF(int i, int i2) throws IOException {
           throw new CharConversionException("Unexpected EOF in the middle of a 4-byte UTF-32 char: got " + i + ", needed " + i2 + ", at char #" + this._charCount + ", byte #" + (this._byteCount + i) + ")");
                }

       private void reportInvalid(int i, int i2, String str) throws IOException {
           throw new CharConversionException("Invalid UTF-32 character 0x" + Integer.toHexString(i) + str + " at char #" + (this._charCount + i2) + ", byte #" + ((this._byteCount + this._ptr) - 1) + ")");
                }

       private boolean loadMore(int i) throws IOException {
           this._byteCount += this._length - i;
           if (i > 0) {
               if (this._ptr > 0) {
                   for (int i2 = 0; i2 < i; i2++) {
                       this._buffer[i2] = this._buffer[this._ptr + i2];
                            }
                   this._ptr = 0;
                        }
               this._length = i;
                    } else {
               this._ptr = 0;
               int i3 = this._in == null ? -1 : this._in.read(this._buffer);
               if (i3 < 1) {
                   this._length = 0;
                   if (i3 < 0) {
                       if (!this._managedBuffers) {
                                    return false;
                                }
                       freeBuffers();
                                return false;
                            }
                   reportStrangeStream();
                        }
               this._length = i3;
                    }
           while (this._length < 4) {
               int i4 = this._in == null ? -1 : this._in.read(this._buffer, this._length, this._buffer.length - this._length);
               if (i4 < 1) {
                   if (i4 < 0) {
                       if (this._managedBuffers) {
                           freeBuffers();
                                }
                       reportUnexpectedEOF(this._length, 4);
                            }
                   reportStrangeStream();
                        }
               this._length = i4 + this._length;
                    }
                    return true;
                }
            }
