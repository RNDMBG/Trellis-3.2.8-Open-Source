            package com.fasterxml.jackson.core;

            import co.vine.android.util.CommonUtil;
            import com.fasterxml.jackson.core.util.ByteArrayBuilder;
            import com.flurry.android.Constants;
            import java.io.Serializable;
            import java.util.Arrays;
            import org.apache.commons.io.IOUtils;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 108 */   public final class Base64Variant implements Serializable {
                public static final int BASE64_VALUE_INVALID = -1;
                public static final int BASE64_VALUE_PADDING = -2;
                private static final int INT_SPACE = 32;
                static final char PADDING_CHAR_NONE = 0;
                private static final long serialVersionUID = 1;
                private final transient int[] _asciiToBase64;
                private final transient byte[] _base64ToAsciiB;
                private final transient char[] _base64ToAsciiC;
                protected final transient int _maxLineLength;
                protected final String _name;
                protected final transient char _paddingChar;
                protected final transient boolean _usesPadding;

/* 109 */       public Base64Variant(String str, String str2, boolean z, char c, int i) {
/* 55 */            this._asciiToBase64 = new int[128];
/* 61 */            this._base64ToAsciiC = new char[64];
/* 67 */            this._base64ToAsciiB = new byte[64];
/* 111 */           this._name = str;
/* 112 */           this._usesPadding = z;
/* 113 */           this._paddingChar = c;
/* 114 */           this._maxLineLength = i;
/* 119 */           int length = str2.length();
/* 120 */           if (length != 64) {
/* 121 */               throw new IllegalArgumentException("Base64Alphabet length must be exactly 64 (was " + length + ")");
                    }
/* 125 */           str2.getChars(0, length, this._base64ToAsciiC, 0);
/* 126 */           Arrays.fill(this._asciiToBase64, -1);
/* 127 */           for (int i2 = 0; i2 < length; i2++) {
/* 128 */               char c2 = this._base64ToAsciiC[i2];
/* 129 */               this._base64ToAsciiB[i2] = (byte) c2;
/* 130 */               this._asciiToBase64[c2] = i2;
                    }
/* 134 */           if (z) {
/* 135 */               this._asciiToBase64[c] = -2;
                    }
                }

/* 145 */       public Base64Variant(Base64Variant base64Variant, String str, int i) {
/* 146 */           this(base64Variant, str, base64Variant._usesPadding, base64Variant._paddingChar, i);
                }

/* 154 */       public Base64Variant(Base64Variant base64Variant, String str, boolean z, char c, int i) {
/* 55 */            this._asciiToBase64 = new int[128];
/* 61 */            this._base64ToAsciiC = new char[64];
/* 67 */            this._base64ToAsciiB = new byte[64];
/* 156 */           this._name = str;
/* 157 */           byte[] bArr = base64Variant._base64ToAsciiB;
/* 158 */           System.arraycopy(bArr, 0, this._base64ToAsciiB, 0, bArr.length);
/* 159 */           char[] cArr = base64Variant._base64ToAsciiC;
/* 160 */           System.arraycopy(cArr, 0, this._base64ToAsciiC, 0, cArr.length);
/* 161 */           int[] iArr = base64Variant._asciiToBase64;
/* 162 */           System.arraycopy(iArr, 0, this._asciiToBase64, 0, iArr.length);
/* 164 */           this._usesPadding = z;
/* 165 */           this._paddingChar = c;
/* 166 */           this._maxLineLength = i;
                }

/* 179 */       protected Object readResolve() {
/* 180 */           return Base64Variants.valueOf(this._name);
                }

/* 188 */       public String getName() {
/* 189 */           return this._name;
                }

/* 190 */       public boolean usesPadding() {
/* 191 */           return this._usesPadding;
                }

/* 191 */       public boolean usesPaddingChar(char c) {
                    return c == this._paddingChar;
                }

/* 192 */       public boolean usesPaddingChar(int i) {
                    return i == this._paddingChar;
                }

/* 193 */       public char getPaddingChar() {
/* 194 */           return this._paddingChar;
                }

/* 194 */       public byte getPaddingByte() {
/* 195 */           return (byte) this._paddingChar;
                }

/* 196 */       public int getMaxLineLength() {
/* 197 */           return this._maxLineLength;
                }

/* 210 */       public int decodeBase64Char(char c) {
/* 211 */           if (c <= 127) {
                        return this._asciiToBase64[c];
                    }
                    return -1;
                }

/* 215 */       public int decodeBase64Char(int i) {
/* 216 */           if (i <= 127) {
                        return this._asciiToBase64[i];
                    }
                    return -1;
                }

/* 221 */       public int decodeBase64Byte(byte b) {
/* 222 */           if (b <= 127) {
                        return this._asciiToBase64[b];
                    }
                    return -1;
                }

/* 235 */       public char encodeBase64BitsAsChar(int i) {
/* 236 */           return this._base64ToAsciiC[i];
                }

/* 244 */       public int encodeBase64Chunk(int i, char[] cArr, int i2) {
/* 245 */           int i3 = i2 + 1;
                    cArr[i2] = this._base64ToAsciiC[(i >> 18) & 63];
/* 246 */           int i4 = i3 + 1;
                    cArr[i3] = this._base64ToAsciiC[(i >> 12) & 63];
/* 247 */           int i5 = i4 + 1;
                    cArr[i4] = this._base64ToAsciiC[(i >> 6) & 63];
/* 248 */           int i6 = i5 + 1;
                    cArr[i5] = this._base64ToAsciiC[i & 63];
/* 249 */           return i6;
                }

/* 253 */       public void encodeBase64Chunk(StringBuilder sb, int i) {
/* 254 */           sb.append(this._base64ToAsciiC[(i >> 18) & 63]);
/* 255 */           sb.append(this._base64ToAsciiC[(i >> 12) & 63]);
/* 256 */           sb.append(this._base64ToAsciiC[(i >> 6) & 63]);
/* 257 */           sb.append(this._base64ToAsciiC[i & 63]);
                }

/* 269 */       public int encodeBase64Partial(int i, int i2, char[] cArr, int i3) {
/* 270 */           int i4 = i3 + 1;
                    cArr[i3] = this._base64ToAsciiC[(i >> 18) & 63];
/* 271 */           int i5 = i4 + 1;
                    cArr[i4] = this._base64ToAsciiC[(i >> 12) & 63];
/* 272 */           if (this._usesPadding) {
/* 273 */               int i6 = i5 + 1;
/* 273 */               cArr[i5] = i2 == 2 ? this._base64ToAsciiC[(i >> 6) & 63] : this._paddingChar;
/* 275 */               int i7 = i6 + 1;
                        cArr[i6] = this._paddingChar;
/* 281 */               return i7;
                    }
/* 277 */           if (i2 != 2) {
                        return i5;
                    }
/* 278 */           int i8 = i5 + 1;
                    cArr[i5] = this._base64ToAsciiC[(i >> 6) & 63];
/* 281 */           return i8;
                }

/* 285 */       public void encodeBase64Partial(StringBuilder sb, int i, int i2) {
/* 286 */           sb.append(this._base64ToAsciiC[(i >> 18) & 63]);
/* 287 */           sb.append(this._base64ToAsciiC[(i >> 12) & 63]);
/* 288 */           if (this._usesPadding) {
/* 289 */               sb.append(i2 == 2 ? this._base64ToAsciiC[(i >> 6) & 63] : this._paddingChar);
/* 291 */               sb.append(this._paddingChar);
                    } else if (i2 == 2) {
/* 294 */               sb.append(this._base64ToAsciiC[(i >> 6) & 63]);
                    }
                }

/* 301 */       public byte encodeBase64BitsAsByte(int i) {
/* 302 */           return this._base64ToAsciiB[i];
                }

/* 310 */       public int encodeBase64Chunk(int i, byte[] bArr, int i2) {
/* 311 */           int i3 = i2 + 1;
                    bArr[i2] = this._base64ToAsciiB[(i >> 18) & 63];
/* 312 */           int i4 = i3 + 1;
                    bArr[i3] = this._base64ToAsciiB[(i >> 12) & 63];
/* 313 */           int i5 = i4 + 1;
                    bArr[i4] = this._base64ToAsciiB[(i >> 6) & 63];
/* 314 */           int i6 = i5 + 1;
                    bArr[i5] = this._base64ToAsciiB[i & 63];
/* 315 */           return i6;
                }

/* 327 */       public int encodeBase64Partial(int i, int i2, byte[] bArr, int i3) {
/* 328 */           int i4 = i3 + 1;
                    bArr[i3] = this._base64ToAsciiB[(i >> 18) & 63];
/* 329 */           int i5 = i4 + 1;
                    bArr[i4] = this._base64ToAsciiB[(i >> 12) & 63];
/* 330 */           if (this._usesPadding) {
/* 331 */               byte b = (byte) this._paddingChar;
/* 332 */               int i6 = i5 + 1;
                        bArr[i5] = i2 == 2 ? this._base64ToAsciiB[(i >> 6) & 63] : b;
/* 334 */               int i7 = i6 + 1;
                        bArr[i6] = b;
/* 340 */               return i7;
                    }
/* 336 */           if (i2 != 2) {
                        return i5;
                    }
/* 337 */           int i8 = i5 + 1;
                    bArr[i5] = this._base64ToAsciiB[(i >> 6) & 63];
/* 340 */           return i8;
                }

/* 358 */       public String encode(byte[] bArr) {
/* 359 */           return encode(bArr, false);
                }

/* 371 */       public String encode(byte[] bArr, boolean z) {
/* 372 */           int length = bArr.length;
/* 377 */           StringBuilder sb = new StringBuilder((length >> 2) + length + (length >> 3));
/* 379 */           if (z) {
/* 380 */               sb.append(CommonUtil.MARKER_QUOTES);
                    }
/* 386 */           int i = 0;
/* 387 */           int i2 = length - 3;
/* 383 */           int maxLineLength = getMaxLineLength() >> 2;
/* 389 */           while (i <= i2) {
/* 391 */               int i3 = i + 1;
/* 392 */               int i4 = i3 + 1;
/* 393 */               int i5 = ((bArr[i] << 8) | (bArr[i3] & Constants.UNKNOWN)) << 8;
                        int i6 = i4 + 1;
/* 394 */               encodeBase64Chunk(sb, i5 | (bArr[i4] & Constants.UNKNOWN));
/* 395 */               int maxLineLength2 = maxLineLength - 1;
                        if (maxLineLength2 <= 0) {
/* 397 */                   sb.append(IOUtils.DIR_SEPARATOR_WINDOWS);
/* 398 */                   sb.append('n');
/* 399 */                   maxLineLength2 = getMaxLineLength() >> 2;
                        }
                        maxLineLength = maxLineLength2;
                        i = i6;
                    }
/* 404 */           int i7 = length - i;
/* 405 */           if (i7 > 0) {
/* 406 */               int i8 = i + 1;
                        int i9 = bArr[i] << 16;
/* 407 */               if (i7 == 2) {
/* 408 */                   int i10 = i8 + 1;
                            i9 |= (bArr[i8] & Constants.UNKNOWN) << 8;
                        }
/* 410 */               encodeBase64Partial(sb, i9, i7);
                    }
/* 413 */           if (z) {
/* 414 */               sb.append(CommonUtil.MARKER_QUOTES);
                    }
/* 416 */           return sb.toString();
                }

/* 431 */       public byte[] decode(String str) throws IllegalArgumentException {
/* 432 */           ByteArrayBuilder byteArrayBuilder = new ByteArrayBuilder();
/* 433 */           decode(str, byteArrayBuilder);
/* 434 */           return byteArrayBuilder.toByteArray();
                }

/* 452 */       public void decode(String str, ByteArrayBuilder byteArrayBuilder) throws IllegalArgumentException {
                    int i;
                    char cCharAt;
/* 453 */           int length = str.length();
                    int i2 = 0;
/* 456 */           while (i2 < length) {
                        while (true) {
/* 460 */                   i = i2 + 1;
                            cCharAt = str.charAt(i2);
/* 461 */                   if (i < length) {
/* 464 */                       if (cCharAt > ' ') {
                                    break;
                                } else {
                                    i2 = i;
                                }
                            } else {
/* 537 */                       return;
                            }
                        }
/* 465 */               int iDecodeBase64Char = decodeBase64Char(cCharAt);
/* 466 */               if (iDecodeBase64Char < 0) {
/* 467 */                   _reportInvalidBase64(cCharAt, 0, null);
                        }
/* 471 */               if (i >= length) {
/* 472 */                   _reportBase64EOF();
                        }
/* 474 */               int i3 = i + 1;
                        char cCharAt2 = str.charAt(i);
/* 475 */               int iDecodeBase64Char2 = decodeBase64Char(cCharAt2);
/* 476 */               if (iDecodeBase64Char2 < 0) {
/* 477 */                   _reportInvalidBase64(cCharAt2, 1, null);
                        }
/* 479 */               int i4 = (iDecodeBase64Char << 6) | iDecodeBase64Char2;
/* 481 */               if (i3 >= length) {
/* 483 */                   if (!usesPadding()) {
/* 485 */                       byteArrayBuilder.append(i4 >> 4);
/* 537 */                       return;
                            }
/* 488 */                   _reportBase64EOF();
                        }
/* 490 */               int i5 = i3 + 1;
                        char cCharAt3 = str.charAt(i3);
/* 491 */               int iDecodeBase64Char3 = decodeBase64Char(cCharAt3);
/* 494 */               if (iDecodeBase64Char3 < 0) {
/* 495 */                   if (iDecodeBase64Char3 != -2) {
/* 496 */                       _reportInvalidBase64(cCharAt3, 2, null);
                            }
/* 499 */                   if (i5 >= length) {
/* 500 */                       _reportBase64EOF();
                            }
/* 502 */                   i2 = i5 + 1;
                            char cCharAt4 = str.charAt(i5);
/* 503 */                   if (!usesPaddingChar(cCharAt4)) {
/* 504 */                       _reportInvalidBase64(cCharAt4, 3, "expected padding character '" + getPaddingChar() + "'");
                            }
/* 508 */                   byteArrayBuilder.append(i4 >> 4);
                        } else {
/* 512 */                   int i6 = (i4 << 6) | iDecodeBase64Char3;
/* 514 */                   if (i5 >= length) {
/* 516 */                       if (!usesPadding()) {
/* 518 */                           byteArrayBuilder.appendTwoBytes(i6 >> 2);
/* 537 */                           return;
                                }
/* 521 */                       _reportBase64EOF();
                            }
/* 523 */                   i2 = i5 + 1;
                            char cCharAt5 = str.charAt(i5);
/* 524 */                   int iDecodeBase64Char4 = decodeBase64Char(cCharAt5);
/* 525 */                   if (iDecodeBase64Char4 < 0) {
/* 526 */                       if (iDecodeBase64Char4 != -2) {
/* 527 */                           _reportInvalidBase64(cCharAt5, 3, null);
                                }
/* 530 */                       byteArrayBuilder.appendTwoBytes(i6 >> 2);
                            } else {
/* 534 */                       byteArrayBuilder.appendThreeBytes((i6 << 6) | iDecodeBase64Char4);
                            }
                        }
                    }
                }

/* 545 */       public String toString() {
/* 546 */           return this._name;
                }

/* 550 */       public boolean equals(Object obj) {
                    return obj == this;
                }

/* 555 */       public int hashCode() {
/* 556 */           return this._name.hashCode();
                }

/* 572 */       protected void _reportInvalidBase64(char c, int i, String str) throws IllegalArgumentException {
                    String str2;
/* 573 */           if (c <= ' ') {
/* 574 */               str2 = "Illegal white space character (code 0x" + Integer.toHexString(c) + ") as character #" + (i + 1) + " of 4-char base64 unit: can only used between units";
                    } else if (usesPaddingChar(c)) {
/* 576 */               str2 = "Unexpected padding character ('" + getPaddingChar() + "') as character #" + (i + 1) + " of 4-char base64 unit: padding only legal as 3rd or 4th character";
                    } else if (!Character.isDefined(c) || Character.isISOControl(c)) {
/* 579 */               str2 = "Illegal character (code 0x" + Integer.toHexString(c) + ") in base64 content";
                    } else {
/* 581 */               str2 = "Illegal character '" + c + "' (code 0x" + Integer.toHexString(c) + ") in base64 content";
                    }
/* 583 */           if (str != null) {
/* 584 */               str2 = str2 + ": " + str;
                    }
/* 586 */           throw new IllegalArgumentException(str2);
                }

/* 589 */       protected void _reportBase64EOF() throws IllegalArgumentException {
/* 590 */           throw new IllegalArgumentException("Unexpected end-of-String in base64 content");
                }
            }
