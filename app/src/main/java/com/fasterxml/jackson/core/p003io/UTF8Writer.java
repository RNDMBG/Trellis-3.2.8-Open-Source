            package com.fasterxml.jackson.core.p003io;

            import java.io.IOException;
            import java.io.OutputStream;
            import java.io.Writer;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 3 */     public final class UTF8Writer extends Writer {
                static final int SURR1_FIRST = 55296;
                static final int SURR1_LAST = 56319;
                static final int SURR2_FIRST = 56320;
                static final int SURR2_LAST = 57343;
                private final IOContext _context;
                private OutputStream _out;
                private byte[] _outBuffer;
                private final int _outBufferEnd;
                private int _surrogate = 0;
                private int _outPtr = 0;


/* 29 */        public UTF8Writer(IOContext iOContext, OutputStream outputStream) {
/* 31 */            this._context = iOContext;
/* 32 */            this._out = outputStream;
/* 34 */            this._outBuffer = iOContext.allocWriteEncodingBuffer();
/* 39 */            this._outBufferEnd = this._outBuffer.length - 4;
                }

                @Override // java.io.Writer, java.lang.Appendable
/* 46 */        public Writer append(char c) throws IOException {
/* 47 */            write(c);
/* 48 */            return this;
                }

                @Override // java.io.Writer, java.io.Closeable, java.lang.AutoCloseable
/* 54 */        public void close() throws IOException {
/* 55 */            if (this._out != null) {
/* 56 */                if (this._outPtr > 0) {
/* 57 */                    this._out.write(this._outBuffer, 0, this._outPtr);
/* 58 */                    this._outPtr = 0;
                        }
/* 60 */                OutputStream outputStream = this._out;
/* 61 */                this._out = null;
/* 63 */                byte[] bArr = this._outBuffer;
/* 64 */                if (bArr != null) {
/* 65 */                    this._outBuffer = null;
/* 66 */                    this._context.releaseWriteEncodingBuffer(bArr);
                        }
/* 69 */                outputStream.close();
/* 74 */                int i = this._surrogate;
/* 75 */                this._surrogate = 0;
/* 76 */                if (i > 0) {
/* 77 */                    illegalSurrogate(i);
                        }
                    }
                }

                @Override // java.io.Writer, java.io.Flushable
/* 85 */        public void flush() throws IOException {
/* 86 */            if (this._out != null) {
/* 87 */                if (this._outPtr > 0) {
/* 88 */                    this._out.write(this._outBuffer, 0, this._outPtr);
/* 89 */                    this._outPtr = 0;
                        }
/* 91 */                this._out.flush();
                    }
                }

                @Override // java.io.Writer
/* 98 */        public void write(char[] cArr) throws IOException {
/* 99 */            write(cArr, 0, cArr.length);
                }

                @Override // java.io.Writer
/* 105 */       public void write(char[] cArr, int i, int i2) throws IOException {
                    char c;
/* 106 */           if (i2 < 2) {
/* 107 */               if (i2 == 1) {
/* 108 */                   write(cArr[i]);
/* 197 */                   return;
                        }
/* 197 */               return;
                    }
/* 114 */           if (this._surrogate > 0) {
/* 116 */               i2--;
/* 117 */               write(convertSurrogate(cArr[i]));
/* 115 */               i++;
                    }
/* 121 */           int i3 = this._outPtr;
/* 122 */           byte[] bArr = this._outBuffer;
/* 123 */           int i4 = this._outBufferEnd;
/* 126 */           int i5 = i2 + i;
                    int i6 = i;
/* 129 */           while (i6 < i5) {
/* 133 */               if (i3 >= i4) {
/* 134 */                   this._out.write(bArr, 0, i3);
                            i3 = 0;
                        }
/* 138 */               int i7 = i6 + 1;
                        char c2 = cArr[i6];
/* 140 */               if (c2 < 128) {
/* 141 */                   int i8 = i3 + 1;
                            bArr[i3] = (byte) c2;
/* 143 */                   int i9 = i5 - i7;
/* 144 */                   int i10 = i4 - i8;
/* 146 */                   if (i9 <= i10) {
                                i10 = i9;
                            }
/* 149 */                   int i11 = i10 + i7;
                            int i12 = i8;
                            int i13 = i7;
                            while (true) {
/* 152 */                       if (i13 >= i11) {
                                    int i14 = i12;
                                    i6 = i13;
                                    i3 = i14;
                                } else {
/* 155 */                           int i15 = i13 + 1;
                                    char c3 = cArr[i13];
/* 156 */                           if (c3 >= 128) {
                                        i3 = i12;
                                        i6 = i15;
                                        c = c3;
                                    } else {
                                        bArr[i12] = (byte) c3;
/* 159 */                               i12++;
                                        i13 = i15;
                                    }
                                }
                            }
                        } else {
                            i6 = i7;
                            c = c2;
                        }
/* 164 */               if (c < 2048) {
/* 165 */                   int i16 = i3 + 1;
                            bArr[i3] = (byte) ((c >> 6) | 192);
/* 166 */                   i3 = i16 + 1;
                            bArr[i16] = (byte) ((c & '?') | 128);
                        } else if (c < SURR1_FIRST || c > SURR2_LAST) {
/* 170 */                   int i17 = i3 + 1;
                            bArr[i3] = (byte) ((c >> '\f') | 224);
/* 171 */                   int i18 = i17 + 1;
                            bArr[i17] = (byte) (((c >> 6) & 63) | 128);
/* 172 */                   i3 = i18 + 1;
                            bArr[i18] = (byte) ((c & '?') | 128);
                        } else {
/* 176 */                   if (c > SURR1_LAST) {
/* 177 */                       this._outPtr = i3;
/* 178 */                       illegalSurrogate(c);
                            }
/* 180 */                   this._surrogate = c;
/* 182 */                   if (i6 >= i5) {
                                break;
                            }
/* 185 */                   int i19 = i6 + 1;
                            int iConvertSurrogate = convertSurrogate(cArr[i6]);
/* 186 */                   if (iConvertSurrogate > 1114111) {
/* 187 */                       this._outPtr = i3;
/* 188 */                       illegalSurrogate(iConvertSurrogate);
                            }
/* 190 */                   int i20 = i3 + 1;
                            bArr[i3] = (byte) ((iConvertSurrogate >> 18) | 240);
/* 191 */                   int i21 = i20 + 1;
                            bArr[i20] = (byte) (((iConvertSurrogate >> 12) & 63) | 128);
/* 192 */                   int i22 = i21 + 1;
                            bArr[i21] = (byte) (((iConvertSurrogate >> 6) & 63) | 128);
/* 193 */                   i3 = i22 + 1;
                            bArr[i22] = (byte) ((iConvertSurrogate & 63) | 128);
                            i6 = i19;
                        }
                    }
/* 196 */           this._outPtr = i3;
                }

                @Override // java.io.Writer
/* 202 */       public void write(int i) throws IOException {
                    int i2;
/* 203 */           if (this._surrogate > 0) {
/* 204 */               i = convertSurrogate(i);
                    } else if (i >= SURR1_FIRST && i <= SURR2_LAST) {
/* 208 */               if (i > SURR1_LAST) {
/* 209 */                   illegalSurrogate(i);
                        }
/* 212 */               this._surrogate = i;
/* 243 */               return;
                    }
/* 216 */           if (this._outPtr >= this._outBufferEnd) {
/* 217 */               this._out.write(this._outBuffer, 0, this._outPtr);
/* 218 */               this._outPtr = 0;
                    }
/* 221 */           if (i < 128) {
/* 222 */               byte[] bArr = this._outBuffer;
                        int i3 = this._outPtr;
                        this._outPtr = i3 + 1;
                        bArr[i3] = (byte) i;
/* 243 */               return;
                    }
/* 224 */           int i4 = this._outPtr;
/* 225 */           if (i < 2048) {
                        int i5 = i4 + 1;
/* 226 */               this._outBuffer[i4] = (byte) ((i >> 6) | 192);
                        i2 = i5 + 1;
/* 227 */               this._outBuffer[i5] = (byte) ((i & 63) | 128);
                    } else if (i <= 65535) {
                        int i6 = i4 + 1;
/* 229 */               this._outBuffer[i4] = (byte) ((i >> 12) | 224);
                        int i7 = i6 + 1;
/* 230 */               this._outBuffer[i6] = (byte) (((i >> 6) & 63) | 128);
                        i2 = i7 + 1;
/* 231 */               this._outBuffer[i7] = (byte) ((i & 63) | 128);
                    } else {
/* 233 */               if (i > 1114111) {
/* 234 */                   illegalSurrogate(i);
                        }
                        int i8 = i4 + 1;
/* 236 */               this._outBuffer[i4] = (byte) ((i >> 18) | 240);
                        int i9 = i8 + 1;
/* 237 */               this._outBuffer[i8] = (byte) (((i >> 12) & 63) | 128);
                        int i10 = i9 + 1;
/* 238 */               this._outBuffer[i9] = (byte) (((i >> 6) & 63) | 128);
                        i2 = i10 + 1;
/* 239 */               this._outBuffer[i10] = (byte) ((i & 63) | 128);
                    }
/* 241 */           this._outPtr = i2;
                }

                @Override // java.io.Writer
/* 247 */       public void write(String str) throws IOException {
/* 248 */           write(str, 0, str.length());
                }

                @Override // java.io.Writer
/* 253 */       public void write(String str, int i, int i2) throws IOException {
                    char c;
/* 254 */           if (i2 < 2) {
/* 255 */               if (i2 == 1) {
/* 256 */                   write(str.charAt(i));
/* 345 */                   return;
                        }
/* 345 */               return;
                    }
/* 262 */           if (this._surrogate > 0) {
/* 264 */               i2--;
/* 265 */               write(convertSurrogate(str.charAt(i)));
/* 263 */               i++;
                    }
/* 269 */           int i3 = this._outPtr;
/* 270 */           byte[] bArr = this._outBuffer;
/* 271 */           int i4 = this._outBufferEnd;
/* 274 */           int i5 = i2 + i;
                    int i6 = i;
/* 277 */           while (i6 < i5) {
/* 281 */               if (i3 >= i4) {
/* 282 */                   this._out.write(bArr, 0, i3);
                            i3 = 0;
                        }
/* 286 */               int i7 = i6 + 1;
                        char cCharAt = str.charAt(i6);
/* 288 */               if (cCharAt < 128) {
/* 289 */                   int i8 = i3 + 1;
                            bArr[i3] = (byte) cCharAt;
/* 291 */                   int i9 = i5 - i7;
/* 292 */                   int i10 = i4 - i8;
/* 294 */                   if (i9 <= i10) {
                                i10 = i9;
                            }
/* 297 */                   int i11 = i10 + i7;
                            int i12 = i8;
                            int i13 = i7;
                            while (true) {
/* 300 */                       if (i13 >= i11) {
                                    int i14 = i12;
                                    i6 = i13;
                                    i3 = i14;
                                } else {
/* 303 */                           int i15 = i13 + 1;
                                    char cCharAt2 = str.charAt(i13);
/* 304 */                           if (cCharAt2 >= 128) {
                                        i3 = i12;
                                        i6 = i15;
                                        c = cCharAt2;
                                    } else {
                                        bArr[i12] = (byte) cCharAt2;
/* 307 */                               i12++;
                                        i13 = i15;
                                    }
                                }
                            }
                        } else {
                            i6 = i7;
                            c = cCharAt;
                        }
/* 312 */               if (c < 2048) {
/* 313 */                   int i16 = i3 + 1;
                            bArr[i3] = (byte) ((c >> 6) | 192);
/* 314 */                   i3 = i16 + 1;
                            bArr[i16] = (byte) ((c & '?') | 128);
                        } else if (c < SURR1_FIRST || c > SURR2_LAST) {
/* 318 */                   int i17 = i3 + 1;
                            bArr[i3] = (byte) ((c >> '\f') | 224);
/* 319 */                   int i18 = i17 + 1;
                            bArr[i17] = (byte) (((c >> 6) & 63) | 128);
/* 320 */                   i3 = i18 + 1;
                            bArr[i18] = (byte) ((c & '?') | 128);
                        } else {
/* 324 */                   if (c > SURR1_LAST) {
/* 325 */                       this._outPtr = i3;
/* 326 */                       illegalSurrogate(c);
                            }
/* 328 */                   this._surrogate = c;
/* 330 */                   if (i6 >= i5) {
                                break;
                            }
/* 333 */                   int i19 = i6 + 1;
                            int iConvertSurrogate = convertSurrogate(str.charAt(i6));
/* 334 */                   if (iConvertSurrogate > 1114111) {
/* 335 */                       this._outPtr = i3;
/* 336 */                       illegalSurrogate(iConvertSurrogate);
                            }
/* 338 */                   int i20 = i3 + 1;
                            bArr[i3] = (byte) ((iConvertSurrogate >> 18) | 240);
/* 339 */                   int i21 = i20 + 1;
                            bArr[i20] = (byte) (((iConvertSurrogate >> 12) & 63) | 128);
/* 340 */                   int i22 = i21 + 1;
                            bArr[i21] = (byte) (((iConvertSurrogate >> 6) & 63) | 128);
/* 341 */                   i3 = i22 + 1;
                            bArr[i22] = (byte) ((iConvertSurrogate & 63) | 128);
                            i6 = i19;
                        }
                    }
/* 344 */           this._outPtr = i3;
                }

/* 358 */       protected int convertSurrogate(int i) throws IOException {
/* 359 */           int i2 = this._surrogate;
/* 360 */           this._surrogate = 0;
/* 363 */           if (i < SURR2_FIRST || i > SURR2_LAST) {
/* 364 */               throw new IOException("Broken surrogate pair: first char 0x" + Integer.toHexString(i2) + ", second 0x" + Integer.toHexString(i) + "; illegal combination");
                    }
/* 366 */           return ((i2 - SURR1_FIRST) << 10) + 65536 + (i - SURR2_FIRST);
                }

/* 369 */       protected static void illegalSurrogate(int i) throws IOException {
/* 370 */           throw new IOException(illegalSurrogateDesc(i));
                }

/* 374 */       protected static String illegalSurrogateDesc(int i) {
/* 375 */           if (i > 1114111) {
/* 376 */               return "Illegal character point (0x" + Integer.toHexString(i) + ") to output; max is 0x10FFFF as per RFC 4627";
                    }
/* 378 */           if (i >= SURR1_FIRST) {
/* 379 */               if (i <= SURR1_LAST) {
/* 380 */                   return "Unmatched first part of surrogate pair (0x" + Integer.toHexString(i) + ")";
                        }
/* 382 */               return "Unmatched second part of surrogate pair (0x" + Integer.toHexString(i) + ")";
                    }
/* 385 */           return "Illegal character point (0x" + Integer.toHexString(i) + ") to output";
                }
            }
