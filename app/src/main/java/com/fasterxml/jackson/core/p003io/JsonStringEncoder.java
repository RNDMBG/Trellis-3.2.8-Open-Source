            package com.fasterxml.jackson.core.p003io;

            import com.fasterxml.jackson.core.util.BufferRecycler;
            import com.fasterxml.jackson.core.util.ByteArrayBuilder;
            import com.fasterxml.jackson.core.util.TextBuffer;
            import java.lang.ref.SoftReference;
            import org.apache.commons.io.IOUtils;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 62 */    public final class JsonStringEncoder {
                private static final int INT_0 = 48;
                private static final int INT_BACKSLASH = 92;
                private static final int INT_U = 117;
                private static final int SURR1_FIRST = 55296;
                private static final int SURR1_LAST = 56319;
                private static final int SURR2_FIRST = 56320;
                private static final int SURR2_LAST = 57343;
                protected ByteArrayBuilder _byteBuilder;
                protected final char[] _quoteBuffer = new char[6];
                protected TextBuffer _textBuffer;
                private static final char[] HEX_CHARS = CharTypes.copyHexChars();
                private static final byte[] HEX_BYTES = CharTypes.copyHexBytes();
                protected static final ThreadLocal<SoftReference<JsonStringEncoder>> _threadEncoder = new ThreadLocal<>();

/* 63 */        public JsonStringEncoder() {
/* 66 */            this._quoteBuffer[0] = IOUtils.DIR_SEPARATOR_WINDOWS;
/* 67 */            this._quoteBuffer[2] = '0';
/* 68 */            this._quoteBuffer[3] = '0';
                }

/* 76 */        public static JsonStringEncoder getInstance() {
/* 77 */            SoftReference<JsonStringEncoder> softReference = _threadEncoder.get();
/* 78 */            JsonStringEncoder jsonStringEncoder = softReference == null ? null : softReference.get();
/* 80 */            if (jsonStringEncoder == null) {
/* 81 */                JsonStringEncoder jsonStringEncoder2 = new JsonStringEncoder();
/* 82 */                _threadEncoder.set(new SoftReference<>(jsonStringEncoder2));
/* 84 */                return jsonStringEncoder2;
                    }
/* 84 */            return jsonStringEncoder;
                }

/* 98 */        public char[] quoteAsString(String str) {
                    int i;
/* 99 */            TextBuffer textBuffer = this._textBuffer;
/* 100 */           if (textBuffer == null) {
/* 102 */               textBuffer = new TextBuffer(null);
                        this._textBuffer = textBuffer;
                    }
/* 104 */           char[] cArrEmptyAndGetCurrentSegment = textBuffer.emptyAndGetCurrentSegment();
/* 105 */           int[] iArr = CharTypes.get7BitOutputEscapes();
/* 106 */           int length = iArr.length;
/* 108 */           int length2 = str.length();
                    int i2 = 0;
                    int i3 = 0;
/* 112 */           loop0: while (i3 < length2) {
                        while (true) {
/* 115 */                   char cCharAt = str.charAt(i3);
/* 116 */                   if (cCharAt >= length || iArr[cCharAt] == 0) {
/* 119 */                       if (i2 >= cArrEmptyAndGetCurrentSegment.length) {
/* 120 */                           cArrEmptyAndGetCurrentSegment = textBuffer.finishCurrentSegment();
                                    i = 0;
                                } else {
                                    i = i2;
                                }
/* 123 */                       i2 = i + 1;
                                cArrEmptyAndGetCurrentSegment[i] = cCharAt;
/* 124 */                       i3++;
                                if (i3 >= length2) {
                                    break loop0;
                                }
                            }
                        }
/* 129 */               int i4 = i3 + 1;
                        char cCharAt2 = str.charAt(i3);
/* 130 */               int i5 = iArr[cCharAt2];
/* 131 */               int i_appendNumericEscape = i5 < 0 ? _appendNumericEscape(cCharAt2, this._quoteBuffer) : _appendNamedEscape(i5, this._quoteBuffer);
/* 135 */               if (i2 + i_appendNumericEscape > cArrEmptyAndGetCurrentSegment.length) {
/* 136 */                   int length3 = cArrEmptyAndGetCurrentSegment.length - i2;
/* 137 */                   if (length3 > 0) {
/* 138 */                       System.arraycopy(this._quoteBuffer, 0, cArrEmptyAndGetCurrentSegment, i2, length3);
                            }
/* 140 */                   cArrEmptyAndGetCurrentSegment = textBuffer.finishCurrentSegment();
/* 141 */                   i2 = i_appendNumericEscape - length3;
/* 142 */                   System.arraycopy(this._quoteBuffer, length3, cArrEmptyAndGetCurrentSegment, 0, i2);
                        } else {
/* 145 */                   System.arraycopy(this._quoteBuffer, 0, cArrEmptyAndGetCurrentSegment, i2, i_appendNumericEscape);
/* 146 */                   i2 += i_appendNumericEscape;
                        }
                        i3 = i4;
                    }
/* 149 */           textBuffer.setCurrentLength(i2);
/* 150 */           return textBuffer.contentsAsArray();
                }

/* 159 */       public byte[] quoteAsUTF8(String str) {
                    int i;
                    int i2;
                    byte[] bArrFinishCurrentSegment;
                    int i3;
                    int i4;
                    int i5;
                    int i6;
/* 160 */           ByteArrayBuilder byteArrayBuilder = this._byteBuilder;
/* 161 */           if (byteArrayBuilder == null) {
/* 163 */               byteArrayBuilder = new ByteArrayBuilder((BufferRecycler) null);
                        this._byteBuilder = byteArrayBuilder;
                    }
/* 166 */           int length = str.length();
/* 168 */           byte[] bArrResetAndGetFirstSegment = byteArrayBuilder.resetAndGetFirstSegment();
                    int i_appendByteEscape = 0;
                    int i7 = 0;
/* 171 */           loop0: while (i7 < length) {
/* 172 */               int[] iArr = CharTypes.get7BitOutputEscapes();
                        while (true) {
/* 176 */                   char cCharAt = str.charAt(i7);
/* 177 */                   if (cCharAt > 127 || iArr[cCharAt] != 0) {
                                break;
                            }
/* 180 */                   if (i_appendByteEscape >= bArrResetAndGetFirstSegment.length) {
/* 181 */                       bArrResetAndGetFirstSegment = byteArrayBuilder.finishCurrentSegment();
                                i6 = 0;
                            } else {
                                i6 = i_appendByteEscape;
                            }
/* 184 */                   i_appendByteEscape = i6 + 1;
                            bArrResetAndGetFirstSegment[i6] = (byte) cCharAt;
/* 185 */                   i7++;
                            if (i7 >= length) {
                                break loop0;
                            }
                        }
/* 189 */               if (i_appendByteEscape >= bArrResetAndGetFirstSegment.length) {
/* 190 */                   bArrResetAndGetFirstSegment = byteArrayBuilder.finishCurrentSegment();
                            i_appendByteEscape = 0;
                        }
/* 194 */               int i8 = i7 + 1;
                        char cCharAt2 = str.charAt(i7);
/* 195 */               if (cCharAt2 <= 127) {
/* 198 */                   i_appendByteEscape = _appendByteEscape(cCharAt2, iArr[cCharAt2], byteArrayBuilder, i_appendByteEscape);
/* 199 */                   bArrResetAndGetFirstSegment = byteArrayBuilder.getCurrentSegment();
                            i7 = i8;
                        } else {
/* 201 */                   if (cCharAt2 <= 2047) {
/* 202 */                       i2 = i_appendByteEscape + 1;
                                bArrResetAndGetFirstSegment[i_appendByteEscape] = (byte) ((cCharAt2 >> 6) | 192);
                                bArrFinishCurrentSegment = bArrResetAndGetFirstSegment;
/* 203 */                       i3 = (cCharAt2 & '?') | 128;
                            } else if (cCharAt2 < SURR1_FIRST || cCharAt2 > SURR2_LAST) {
/* 207 */                       int i9 = i_appendByteEscape + 1;
                                bArrResetAndGetFirstSegment[i_appendByteEscape] = (byte) ((cCharAt2 >> '\f') | 224);
/* 208 */                       if (i9 >= bArrResetAndGetFirstSegment.length) {
/* 209 */                           bArrResetAndGetFirstSegment = byteArrayBuilder.finishCurrentSegment();
                                    i = 0;
                                } else {
                                    i = i9;
                                }
/* 212 */                       i2 = i + 1;
                                bArrResetAndGetFirstSegment[i] = (byte) (((cCharAt2 >> 6) & 63) | 128);
                                bArrFinishCurrentSegment = bArrResetAndGetFirstSegment;
/* 213 */                       i3 = (cCharAt2 & '?') | 128;
                            } else {
/* 215 */                       if (cCharAt2 > SURR1_LAST) {
/* 216 */                           _illegalSurrogate(cCharAt2);
                                }
/* 219 */                       if (i8 >= length) {
/* 220 */                           _illegalSurrogate(cCharAt2);
                                }
/* 222 */                       int i10 = i8 + 1;
                                int i_convertSurrogate = _convertSurrogate(cCharAt2, str.charAt(i8));
/* 223 */                       if (i_convertSurrogate > 1114111) {
/* 224 */                           _illegalSurrogate(i_convertSurrogate);
                                }
/* 226 */                       int i11 = i_appendByteEscape + 1;
                                bArrResetAndGetFirstSegment[i_appendByteEscape] = (byte) ((i_convertSurrogate >> 18) | 240);
/* 227 */                       if (i11 >= bArrResetAndGetFirstSegment.length) {
/* 228 */                           bArrResetAndGetFirstSegment = byteArrayBuilder.finishCurrentSegment();
                                    i4 = 0;
                                } else {
                                    i4 = i11;
                                }
/* 231 */                       int i12 = i4 + 1;
                                bArrResetAndGetFirstSegment[i4] = (byte) (((i_convertSurrogate >> 12) & 63) | 128);
/* 232 */                       if (i12 >= bArrResetAndGetFirstSegment.length) {
/* 233 */                           bArrResetAndGetFirstSegment = byteArrayBuilder.finishCurrentSegment();
                                    i5 = 0;
                                } else {
                                    i5 = i12;
                                }
/* 236 */                       i2 = i5 + 1;
                                bArrResetAndGetFirstSegment[i5] = (byte) (((i_convertSurrogate >> 6) & 63) | 128);
/* 237 */                       int i13 = (i_convertSurrogate & 63) | 128;
                                i8 = i10;
                                byte[] bArr = bArrResetAndGetFirstSegment;
                                i3 = i13;
                                bArrFinishCurrentSegment = bArr;
                            }
/* 240 */                   if (i2 >= bArrFinishCurrentSegment.length) {
/* 241 */                       bArrFinishCurrentSegment = byteArrayBuilder.finishCurrentSegment();
                                i2 = 0;
                            }
/* 244 */                   int i14 = i2 + 1;
                            bArrFinishCurrentSegment[i2] = (byte) i3;
                            bArrResetAndGetFirstSegment = bArrFinishCurrentSegment;
                            i7 = i8;
                            i_appendByteEscape = i14;
                        }
                    }
/* 246 */           return this._byteBuilder.completeAndCoalesce(i_appendByteEscape);
                }

/* 255 */       public byte[] encodeAsUTF8(String str) {
                    int i;
                    int i2;
                    int i3;
                    int i4;
                    int i5;
/* 256 */           ByteArrayBuilder byteArrayBuilder = this._byteBuilder;
/* 257 */           if (byteArrayBuilder == null) {
/* 259 */               byteArrayBuilder = new ByteArrayBuilder((BufferRecycler) null);
                        this._byteBuilder = byteArrayBuilder;
                    }
/* 262 */           int length = str.length();
/* 264 */           byte[] bArrResetAndGetFirstSegment = byteArrayBuilder.resetAndGetFirstSegment();
/* 265 */           int length2 = bArrResetAndGetFirstSegment.length;
                    int i6 = 0;
                    int i7 = 0;
/* 268 */           while (i7 < length) {
/* 269 */               int i8 = i7 + 1;
                        char cCharAt = str.charAt(i7);
                        int i9 = length2;
                        byte[] bArrFinishCurrentSegment = bArrResetAndGetFirstSegment;
                        int i10 = i6;
                        int length3 = i9;
/* 272 */               while (cCharAt <= 127) {
/* 273 */                   if (i10 >= length3) {
/* 274 */                       bArrFinishCurrentSegment = byteArrayBuilder.finishCurrentSegment();
/* 275 */                       length3 = bArrFinishCurrentSegment.length;
                                i10 = 0;
                            }
/* 278 */                   int i11 = i10 + 1;
                            bArrFinishCurrentSegment[i10] = (byte) cCharAt;
/* 279 */                   if (i8 < length) {
                                cCharAt = str.charAt(i8);
/* 282 */                       i8++;
                                i10 = i11;
                            } else {
                                i = i11;
/* 337 */                       return this._byteBuilder.completeAndCoalesce(i);
                            }
                        }
/* 286 */               if (i10 >= length3) {
/* 287 */                   bArrFinishCurrentSegment = byteArrayBuilder.finishCurrentSegment();
/* 288 */                   length3 = bArrFinishCurrentSegment.length;
                            i2 = 0;
                        } else {
                            i2 = i10;
                        }
/* 291 */               if (cCharAt < 2048) {
/* 292 */                   i3 = i2 + 1;
                            bArrFinishCurrentSegment[i2] = (byte) ((cCharAt >> 6) | 192);
                            i4 = cCharAt;
                            i7 = i8;
                        } else if (cCharAt < SURR1_FIRST || cCharAt > SURR2_LAST) {
/* 296 */                   int i12 = i2 + 1;
                            bArrFinishCurrentSegment[i2] = (byte) ((cCharAt >> '\f') | 224);
/* 297 */                   if (i12 >= length3) {
/* 298 */                       bArrFinishCurrentSegment = byteArrayBuilder.finishCurrentSegment();
/* 299 */                       length3 = bArrFinishCurrentSegment.length;
                                i12 = 0;
                            }
                            bArrFinishCurrentSegment[i12] = (byte) (((cCharAt >> 6) & 63) | 128);
/* 302 */                   i3 = i12 + 1;
                            i4 = cCharAt;
                            i7 = i8;
                        } else {
/* 304 */                   if (cCharAt > SURR1_LAST) {
/* 305 */                       _illegalSurrogate(cCharAt);
                            }
/* 308 */                   if (i8 >= length) {
/* 309 */                       _illegalSurrogate(cCharAt);
                            }
/* 311 */                   int i13 = i8 + 1;
                            int i_convertSurrogate = _convertSurrogate(cCharAt, str.charAt(i8));
/* 312 */                   if (i_convertSurrogate > 1114111) {
/* 313 */                       _illegalSurrogate(i_convertSurrogate);
                            }
/* 315 */                   int i14 = i2 + 1;
                            bArrFinishCurrentSegment[i2] = (byte) ((i_convertSurrogate >> 18) | 240);
/* 316 */                   if (i14 >= length3) {
/* 317 */                       bArrFinishCurrentSegment = byteArrayBuilder.finishCurrentSegment();
/* 318 */                       length3 = bArrFinishCurrentSegment.length;
                                i14 = 0;
                            }
/* 321 */                   int i15 = i14 + 1;
                            bArrFinishCurrentSegment[i14] = (byte) (((i_convertSurrogate >> 12) & 63) | 128);
/* 322 */                   if (i15 >= length3) {
/* 323 */                       bArrFinishCurrentSegment = byteArrayBuilder.finishCurrentSegment();
/* 324 */                       length3 = bArrFinishCurrentSegment.length;
                                i5 = 0;
                            } else {
                                i5 = i15;
                            }
                            bArrFinishCurrentSegment[i5] = (byte) (((i_convertSurrogate >> 6) & 63) | 128);
/* 327 */                   i3 = i5 + 1;
                            i4 = i_convertSurrogate;
                            i7 = i13;
                        }
/* 330 */               if (i3 >= length3) {
/* 331 */                   bArrFinishCurrentSegment = byteArrayBuilder.finishCurrentSegment();
/* 332 */                   length3 = bArrFinishCurrentSegment.length;
                            i3 = 0;
                        }
/* 335 */               int i16 = i3 + 1;
                        bArrFinishCurrentSegment[i3] = (byte) ((i4 & 63) | 128);
                        bArrResetAndGetFirstSegment = bArrFinishCurrentSegment;
                        length2 = length3;
                        i6 = i16;
                    }
                    i = i6;
/* 337 */           return this._byteBuilder.completeAndCoalesce(i);
                }

/* 347 */       private int _appendNumericEscape(int i, char[] cArr) {
/* 348 */           cArr[1] = 'u';
/* 350 */           cArr[4] = HEX_CHARS[i >> 4];
/* 351 */           cArr[5] = HEX_CHARS[i & 15];
/* 352 */           return 6;
                }

/* 356 */       private int _appendNamedEscape(int i, char[] cArr) {
/* 357 */           cArr[1] = (char) i;
/* 358 */           return 2;
                }

/* 362 */       private int _appendByteEscape(int i, int i2, ByteArrayBuilder byteArrayBuilder, int i3) {
/* 363 */           byteArrayBuilder.setCurrentSegmentLength(i3);
/* 364 */           byteArrayBuilder.append(92);
/* 365 */           if (i2 < 0) {
/* 366 */               byteArrayBuilder.append(117);
/* 367 */               if (i > 255) {
/* 368 */                   int i4 = i >> 8;
/* 369 */                   byteArrayBuilder.append(HEX_BYTES[i4 >> 4]);
/* 370 */                   byteArrayBuilder.append(HEX_BYTES[i4 & 15]);
/* 371 */                   i &= 255;
                        } else {
/* 373 */                   byteArrayBuilder.append(48);
/* 374 */                   byteArrayBuilder.append(48);
                        }
/* 376 */               byteArrayBuilder.append(HEX_BYTES[i >> 4]);
/* 377 */               byteArrayBuilder.append(HEX_BYTES[i & 15]);
                    } else {
/* 379 */               byteArrayBuilder.append((byte) i2);
                    }
/* 381 */           return byteArrayBuilder.getCurrentSegmentLength();
                }

/* 386 */       protected static int _convertSurrogate(int i, int i2) {
/* 387 */           if (i2 < SURR2_FIRST || i2 > SURR2_LAST) {
/* 388 */               throw new IllegalArgumentException("Broken surrogate pair: first char 0x" + Integer.toHexString(i) + ", second 0x" + Integer.toHexString(i2) + "; illegal combination");
                    }
/* 390 */           return 65536 + ((i - SURR1_FIRST) << 10) + (i2 - SURR2_FIRST);
                }

/* 393 */       protected static void _illegalSurrogate(int i) {
/* 394 */           throw new IllegalArgumentException(UTF8Writer.illegalSurrogateDesc(i));
                }
            }
