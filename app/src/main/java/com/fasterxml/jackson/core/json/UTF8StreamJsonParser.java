            package com.fasterxml.jackson.core.json;

            import com.fasterxml.jackson.core.Base64Variant;
            import com.fasterxml.jackson.core.JsonLocation;
            import com.fasterxml.jackson.core.JsonParseException;
            import com.fasterxml.jackson.core.JsonParser;
            import com.fasterxml.jackson.core.JsonToken;
            import com.fasterxml.jackson.core.ObjectCodec;
            import com.fasterxml.jackson.core.SerializableString;
            import com.fasterxml.jackson.core.base.ParserBase;
            import com.fasterxml.jackson.core.p003io.CharTypes;
            import com.fasterxml.jackson.core.p003io.IOContext;
            import com.fasterxml.jackson.core.sym.BytesToNameCanonicalizer;
            import com.fasterxml.jackson.core.sym.Name;
            import com.fasterxml.jackson.core.util.ArraysCompat;
            import com.fasterxml.jackson.core.util.ByteArrayBuilder;
            import com.flurry.android.Constants;
            import java.io.IOException;
            import java.io.InputStream;
            import java.io.OutputStream;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 115 */   public class UTF8StreamJsonParser extends ParserBase {
                static final byte BYTE_LF = 10;
                protected boolean _bufferRecyclable;
                protected byte[] _inputBuffer;
                protected InputStream _inputStream;
                protected ObjectCodec _objectCodec;
                private int _quad1;
                protected int[] _quadBuffer;
                protected final BytesToNameCanonicalizer _symbols;
                protected boolean _tokenIncomplete;
                private static final int[] _icUTF8 = CharTypes.getInputCodeUtf8();
                protected static final int[] _icLatin1 = CharTypes.getInputCodeLatin1();
                private static final int[] _icWS = CharTypes.getInputCodeWS();

/* 116 */       public UTF8StreamJsonParser(IOContext iOContext, int i, InputStream inputStream, ObjectCodec objectCodec, BytesToNameCanonicalizer bytesToNameCanonicalizer, byte[] bArr, int i2, int i3, boolean z) {
/* 117 */           super(iOContext, i);
/* 62 */            this._quadBuffer = new int[16];
/* 69 */            this._tokenIncomplete = false;
/* 118 */           this._inputStream = inputStream;
/* 119 */           this._objectCodec = objectCodec;
/* 120 */           this._symbols = bytesToNameCanonicalizer;
/* 121 */           this._inputBuffer = bArr;
/* 122 */           this._inputPtr = i2;
/* 123 */           this._inputEnd = i3;
/* 124 */           this._currInputRowStart = i2;
/* 126 */           this._currInputProcessed = -i2;
/* 127 */           this._bufferRecyclable = z;
                }

                @Override // com.fasterxml.jackson.core.JsonParser
/* 131 */       public ObjectCodec getCodec() {
/* 132 */           return this._objectCodec;
                }

                @Override // com.fasterxml.jackson.core.JsonParser
/* 136 */       public void setCodec(ObjectCodec objectCodec) {
/* 137 */           this._objectCodec = objectCodec;
                }

                @Override // com.fasterxml.jackson.core.JsonParser
/* 148 */       public int releaseBuffered(OutputStream outputStream) throws IOException {
/* 149 */           int i = this._inputEnd - this._inputPtr;
/* 150 */           if (i < 1) {
/* 151 */               return 0;
                    }
/* 155 */           outputStream.write(this._inputBuffer, this._inputPtr, i);
/* 156 */           return i;
                }

                @Override // com.fasterxml.jackson.core.JsonParser
/* 160 */       public Object getInputSource() {
/* 161 */           return this._inputStream;
                }

                @Override // com.fasterxml.jackson.core.base.ParserBase
/* 173 */       protected final boolean loadMore() throws IOException {
/* 174 */           this._currInputProcessed += (long) this._inputEnd;
/* 175 */           this._currInputRowStart -= this._inputEnd;
/* 177 */           if (this._inputStream == null) {
                        return false;
                    }
/* 178 */           int i = this._inputStream.read(this._inputBuffer, 0, this._inputBuffer.length);
/* 179 */           if (i > 0) {
/* 180 */               this._inputPtr = 0;
/* 181 */               this._inputEnd = i;
/* 182 */               return true;
                    }
/* 185 */           _closeInput();
/* 187 */           if (i == 0) {
/* 188 */               throw new IOException("InputStream.read() returned 0 characters when trying to read " + this._inputBuffer.length + " bytes");
                    }
                    return false;
                }

/* 201 */       protected final boolean _loadToHaveAtLeast(int i) throws IOException {
/* 202 */           if (this._inputStream == null) {
                        return false;
                    }
/* 206 */           int i2 = this._inputEnd - this._inputPtr;
/* 207 */           if (i2 > 0 && this._inputPtr > 0) {
/* 208 */               this._currInputProcessed += (long) this._inputPtr;
/* 209 */               this._currInputRowStart -= this._inputPtr;
/* 210 */               System.arraycopy(this._inputBuffer, this._inputPtr, this._inputBuffer, 0, i2);
/* 211 */               this._inputEnd = i2;
                    } else {
/* 213 */               this._inputEnd = 0;
                    }
/* 215 */           this._inputPtr = 0;
/* 216 */           while (this._inputEnd < i) {
/* 217 */               int i3 = this._inputStream.read(this._inputBuffer, this._inputEnd, this._inputBuffer.length - this._inputEnd);
/* 218 */               if (i3 < 1) {
/* 220 */                   _closeInput();
/* 222 */                   if (i3 == 0) {
/* 223 */                       throw new IOException("InputStream.read() returned 0 characters when trying to read " + i2 + " bytes");
                            }
                            return false;
                        }
/* 227 */               this._inputEnd = i3 + this._inputEnd;
                    }
                    return true;
                }

                @Override // com.fasterxml.jackson.core.base.ParserBase
/* 238 */       protected void _closeInput() throws IOException {
/* 239 */           if (this._inputStream != null) {
/* 240 */               if (this._ioContext.isResourceManaged() || isEnabled(JsonParser.Feature.AUTO_CLOSE_SOURCE)) {
/* 241 */                   this._inputStream.close();
                        }
/* 243 */               this._inputStream = null;
                    }
                }

                @Override // com.fasterxml.jackson.core.base.ParserBase
/* 255 */       protected void _releaseBuffers() throws IOException {
                    byte[] bArr;
/* 256 */           super._releaseBuffers();
/* 258 */           this._symbols.release();
/* 259 */           if (this._bufferRecyclable && (bArr = this._inputBuffer) != null) {
/* 262 */               this._inputBuffer = null;
/* 263 */               this._ioContext.releaseReadIOBuffer(bArr);
                    }
                }

                @Override // com.fasterxml.jackson.core.base.ParserMinimalBase, com.fasterxml.jackson.core.JsonParser
/* 277 */       public String getText() throws IOException {
/* 278 */           if (this._currToken != JsonToken.VALUE_STRING) {
                        return _getText2(this._currToken);
                    }
/* 279 */           if (this._tokenIncomplete) {
/* 280 */               this._tokenIncomplete = false;
/* 281 */               _finishString();
                    }
/* 283 */           return this._textBuffer.contentsAsString();
                }

                @Override // com.fasterxml.jackson.core.JsonParser
/* 293 */       public String getValueAsString() throws IOException {
/* 294 */           if (this._currToken != JsonToken.VALUE_STRING) {
                        return super.getValueAsString(null);
                    }
/* 295 */           if (this._tokenIncomplete) {
/* 296 */               this._tokenIncomplete = false;
/* 297 */               _finishString();
                    }
/* 299 */           return this._textBuffer.contentsAsString();
                }

                @Override // com.fasterxml.jackson.core.base.ParserMinimalBase, com.fasterxml.jackson.core.JsonParser
/* 307 */       public String getValueAsString(String str) throws IOException {
/* 308 */           if (this._currToken != JsonToken.VALUE_STRING) {
                        return super.getValueAsString(str);
                    }
/* 309 */           if (this._tokenIncomplete) {
/* 310 */               this._tokenIncomplete = false;
/* 311 */               _finishString();
                    }
/* 313 */           return this._textBuffer.contentsAsString();
                }

/* 319 */       protected final String _getText2(JsonToken jsonToken) {
/* 320 */           if (jsonToken == null) {
/* 321 */               return null;
                    }
                    switch (jsonToken.m524id()) {
                        case 5:
/* 325 */                   return this._parsingContext.getCurrentName();
                        case 6:
                        case 7:
                        case 8:
/* 331 */                   return this._textBuffer.contentsAsString();
                        default:
/* 333 */                   return jsonToken.asString();
                    }
                }

                @Override // com.fasterxml.jackson.core.base.ParserMinimalBase, com.fasterxml.jackson.core.JsonParser
/* 340 */       public char[] getTextCharacters() throws IOException {
/* 341 */           if (this._currToken != null) {
                        switch (this._currToken.m524id()) {
                            case 5:
/* 345 */                       if (!this._nameCopied) {
/* 346 */                           String currentName = this._parsingContext.getCurrentName();
/* 347 */                           int length = currentName.length();
/* 348 */                           if (this._nameCopyBuffer == null) {
/* 349 */                               this._nameCopyBuffer = this._ioContext.allocNameCopyBuffer(length);
                                    } else if (this._nameCopyBuffer.length < length) {
/* 351 */                               this._nameCopyBuffer = new char[length];
                                    }
/* 353 */                           currentName.getChars(0, length, this._nameCopyBuffer, 0);
/* 354 */                           this._nameCopied = true;
                                }
/* 356 */                       return this._nameCopyBuffer;
                            case 6:
/* 359 */                       if (this._tokenIncomplete) {
/* 360 */                           this._tokenIncomplete = false;
/* 361 */                           _finishString();
                                }
                                break;
                            case 7:
                            case 8:
                                break;
                            default:
/* 369 */                       return this._currToken.asCharArray();
                        }
/* 366 */               return this._textBuffer.getTextBuffer();
                    }
/* 372 */           return null;
                }

                @Override // com.fasterxml.jackson.core.base.ParserMinimalBase, com.fasterxml.jackson.core.JsonParser
/* 378 */       public int getTextLength() throws IOException {
/* 379 */           if (this._currToken == null) {
                        return 0;
                    }
                    switch (this._currToken.m524id()) {
                        case 5:
/* 383 */                   return this._parsingContext.getCurrentName().length();
                        case 6:
/* 385 */                   if (this._tokenIncomplete) {
/* 386 */                       this._tokenIncomplete = false;
/* 387 */                       _finishString();
                            }
                            break;
                        case 7:
                        case 8:
                            break;
                        default:
/* 395 */                   return this._currToken.asCharArray().length;
                    }
/* 392 */           return this._textBuffer.size();
                }

                @Override // com.fasterxml.jackson.core.base.ParserMinimalBase, com.fasterxml.jackson.core.JsonParser
/* 404 */       public int getTextOffset() throws IOException {
/* 405 */           if (this._currToken == null) {
                        return 0;
                    }
                    switch (this._currToken.m524id()) {
                        case 5:
                        default:
                            return 0;
                        case 6:
/* 410 */                   if (this._tokenIncomplete) {
/* 411 */                       this._tokenIncomplete = false;
/* 412 */                       _finishString();
                            }
                            break;
                        case 7:
                        case 8:
                            break;
                    }
/* 417 */           return this._textBuffer.getTextOffset();
                }

                @Override // com.fasterxml.jackson.core.base.ParserMinimalBase, com.fasterxml.jackson.core.JsonParser
/* 427 */       public byte[] getBinaryValue(Base64Variant base64Variant) throws IOException {
/* 428 */           if (this._currToken != JsonToken.VALUE_STRING && (this._currToken != JsonToken.VALUE_EMBEDDED_OBJECT || this._binaryValue == null)) {
/* 430 */               _reportError("Current token (" + this._currToken + ") not VALUE_STRING or VALUE_EMBEDDED_OBJECT, can not access as binary");
                    }
/* 435 */           if (this._tokenIncomplete) {
                        try {
/* 437 */                   this._binaryValue = _decodeBase64(base64Variant);
/* 444 */                   this._tokenIncomplete = false;
                        } catch (IllegalArgumentException e) {
/* 439 */                   throw _constructError("Failed to decode VALUE_STRING as base64 (" + base64Variant + "): " + e.getMessage());
                        }
                    } else if (this._binaryValue == null) {
/* 448 */               ByteArrayBuilder byteArrayBuilder_getByteArrayBuilder = _getByteArrayBuilder();
/* 449 */               _decodeBase64(getText(), byteArrayBuilder_getByteArrayBuilder, base64Variant);
/* 450 */               this._binaryValue = byteArrayBuilder_getByteArrayBuilder.toByteArray();
                    }
/* 453 */           return this._binaryValue;
                }

                @Override // com.fasterxml.jackson.core.JsonParser
/* 460 */       public int readBinaryValue(Base64Variant base64Variant, OutputStream outputStream) throws IOException {
/* 461 */           if (!this._tokenIncomplete || this._currToken != JsonToken.VALUE_STRING) {
/* 462 */               byte[] binaryValue = getBinaryValue(base64Variant);
/* 463 */               outputStream.write(binaryValue);
/* 464 */               return binaryValue.length;
                    }
/* 467 */           byte[] bArrAllocBase64Buffer = this._ioContext.allocBase64Buffer();
                    try {
/* 469 */               int i_readBinary = _readBinary(base64Variant, outputStream, bArrAllocBase64Buffer);
/* 471 */               this._ioContext.releaseBase64Buffer(bArrAllocBase64Buffer);
/* 471 */               return i_readBinary;
                    } catch (Throwable th) {
                        this._ioContext.releaseBase64Buffer(bArrAllocBase64Buffer);
                        throw th;
                    }
                }

/* 479 */       protected int _readBinary(Base64Variant base64Variant, OutputStream outputStream, byte[] bArr) throws IOException {
                    int i;
/* 480 */           int length = bArr.length - 3;
                    int i2 = 0;
                    int i3 = 0;
                    while (true) {
/* 487 */               if (this._inputPtr >= this._inputEnd) {
/* 488 */                   loadMoreGuaranteed();
                        }
/* 490 */               byte[] bArr2 = this._inputBuffer;
                        int i4 = this._inputPtr;
                        this._inputPtr = i4 + 1;
                        int i5 = bArr2[i4] & Constants.UNKNOWN;
/* 491 */               if (i5 > 32) {
/* 492 */                   int iDecodeBase64Char = base64Variant.decodeBase64Char(i5);
/* 493 */                   if (iDecodeBase64Char < 0) {
/* 494 */                       if (i5 == 34) {
                                    break;
                                }
/* 497 */                       iDecodeBase64Char = _decodeBase64Escape(base64Variant, i5, 0);
/* 498 */                       if (iDecodeBase64Char < 0) {
                                    continue;
                                }
                            }
                            int i6 = iDecodeBase64Char;
/* 504 */                   if (i3 > length) {
/* 505 */                       i2 += i3;
/* 506 */                       outputStream.write(bArr, 0, i3);
                                i = 0;
                            } else {
                                i = i3;
                            }
/* 514 */                   if (this._inputPtr >= this._inputEnd) {
/* 515 */                       loadMoreGuaranteed();
                            }
/* 517 */                   byte[] bArr3 = this._inputBuffer;
                            int i7 = this._inputPtr;
                            this._inputPtr = i7 + 1;
                            int i8 = bArr3[i7] & Constants.UNKNOWN;
/* 518 */                   int iDecodeBase64Char2 = base64Variant.decodeBase64Char(i8);
/* 519 */                   if (iDecodeBase64Char2 < 0) {
/* 520 */                       iDecodeBase64Char2 = _decodeBase64Escape(base64Variant, i8, 1);
                            }
/* 522 */                   int i9 = (i6 << 6) | iDecodeBase64Char2;
/* 525 */                   if (this._inputPtr >= this._inputEnd) {
/* 526 */                       loadMoreGuaranteed();
                            }
/* 528 */                   byte[] bArr4 = this._inputBuffer;
                            int i10 = this._inputPtr;
                            this._inputPtr = i10 + 1;
                            int i11 = bArr4[i10] & Constants.UNKNOWN;
/* 529 */                   int iDecodeBase64Char3 = base64Variant.decodeBase64Char(i11);
/* 532 */                   if (iDecodeBase64Char3 < 0) {
/* 533 */                       if (iDecodeBase64Char3 != -2) {
/* 535 */                           if (i11 == 34 && !base64Variant.usesPadding()) {
/* 537 */                               i3 = i + 1;
/* 536 */                               bArr[i] = (byte) (i9 >> 4);
                                        break;
                                    }
/* 540 */                           iDecodeBase64Char3 = _decodeBase64Escape(base64Variant, i11, 2);
                                }
/* 542 */                       if (iDecodeBase64Char3 == -2) {
/* 544 */                           if (this._inputPtr >= this._inputEnd) {
/* 545 */                               loadMoreGuaranteed();
                                    }
/* 547 */                           byte[] bArr5 = this._inputBuffer;
                                    int i12 = this._inputPtr;
                                    this._inputPtr = i12 + 1;
                                    int i13 = bArr5[i12] & Constants.UNKNOWN;
/* 548 */                           if (!base64Variant.usesPaddingChar(i13)) {
/* 549 */                               throw reportInvalidBase64Char(base64Variant, i13, 3, "expected padding character '" + base64Variant.getPaddingChar() + "'");
                                    }
/* 553 */                           i3 = i + 1;
/* 552 */                           bArr[i] = (byte) (i9 >> 4);
                                }
                            }
/* 558 */                   int i14 = (i9 << 6) | iDecodeBase64Char3;
/* 560 */                   if (this._inputPtr >= this._inputEnd) {
/* 561 */                       loadMoreGuaranteed();
                            }
/* 563 */                   byte[] bArr6 = this._inputBuffer;
                            int i15 = this._inputPtr;
                            this._inputPtr = i15 + 1;
                            int i16 = bArr6[i15] & Constants.UNKNOWN;
/* 564 */                   int iDecodeBase64Char4 = base64Variant.decodeBase64Char(i16);
/* 565 */                   if (iDecodeBase64Char4 < 0) {
/* 566 */                       if (iDecodeBase64Char4 != -2) {
/* 568 */                           if (i16 == 34 && !base64Variant.usesPadding()) {
/* 569 */                               int i17 = i14 >> 2;
/* 570 */                               int i18 = i + 1;
                                        bArr[i] = (byte) (i17 >> 8);
/* 571 */                               i3 = i18 + 1;
                                        bArr[i18] = (byte) i17;
                                        break;
                                    }
/* 574 */                           iDecodeBase64Char4 = _decodeBase64Escape(base64Variant, i16, 3);
                                }
/* 576 */                       if (iDecodeBase64Char4 == -2) {
/* 583 */                           int i19 = i14 >> 2;
/* 584 */                           int i20 = i + 1;
                                    bArr[i] = (byte) (i19 >> 8);
/* 585 */                           i3 = i20 + 1;
                                    bArr[i20] = (byte) i19;
                                }
                            }
/* 590 */                   int i21 = (i14 << 6) | iDecodeBase64Char4;
/* 591 */                   int i22 = i + 1;
                            bArr[i] = (byte) (i21 >> 16);
/* 592 */                   int i23 = i22 + 1;
                            bArr[i22] = (byte) (i21 >> 8);
/* 593 */                   i3 = i23 + 1;
                            bArr[i23] = (byte) i21;
                        }
                    }
/* 595 */           this._tokenIncomplete = false;
/* 596 */           if (i3 > 0) {
/* 597 */               int i24 = i2 + i3;
/* 598 */               outputStream.write(bArr, 0, i3);
/* 600 */               return i24;
                    }
/* 600 */           return i2;
                }

                @Override // com.fasterxml.jackson.core.base.ParserBase, com.fasterxml.jackson.core.JsonParser
/* 606 */       public JsonLocation getTokenLocation() {
/* 607 */           return new JsonLocation(this._ioContext.getSourceReference(), getTokenCharacterOffset(), -1L, getTokenLineNr(), getTokenColumnNr());
                }

                @Override // com.fasterxml.jackson.core.base.ParserBase, com.fasterxml.jackson.core.JsonParser
/* 616 */       public JsonLocation getCurrentLocation() {
/* 618 */           return new JsonLocation(this._ioContext.getSourceReference(), this._currInputProcessed + ((long) this._inputPtr), -1L, this._currInputRow, (this._inputPtr - this._currInputRowStart) + 1);
                }

                /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
                @Override // com.fasterxml.jackson.core.base.ParserMinimalBase, com.fasterxml.jackson.core.JsonParser
/* 636 */       public JsonToken nextToken() throws IOException {
                    JsonToken jsonToken_parseNumber;
/* 637 */           this._numTypesValid = 0;
/* 642 */           if (this._currToken == JsonToken.FIELD_NAME) {
/* 643 */               return _nextAfterName();
                    }
/* 645 */           if (this._tokenIncomplete) {
/* 646 */               _skipString();
                    }
/* 649 */           int i_skipWSOrEnd = _skipWSOrEnd();
/* 650 */           if (i_skipWSOrEnd < 0) {
/* 654 */               close();
/* 655 */               this._currToken = null;
                        return null;
                    }
/* 661 */           this._tokenInputTotal = (this._currInputProcessed + ((long) this._inputPtr)) - 1;
/* 662 */           this._tokenInputRow = this._currInputRow;
/* 663 */           this._tokenInputCol = (this._inputPtr - this._currInputRowStart) - 1;
/* 666 */           this._binaryValue = null;
/* 669 */           if (i_skipWSOrEnd == 93) {
/* 670 */               if (!this._parsingContext.inArray()) {
/* 671 */                   _reportMismatchedEndMarker(i_skipWSOrEnd, '}');
                        }
/* 673 */               this._parsingContext = this._parsingContext.getParent();
/* 674 */               JsonToken jsonToken = JsonToken.END_ARRAY;
                        this._currToken = jsonToken;
/* 769 */               return jsonToken;
                    }
/* 676 */           if (i_skipWSOrEnd == 125) {
/* 677 */               if (!this._parsingContext.inObject()) {
/* 678 */                   _reportMismatchedEndMarker(i_skipWSOrEnd, ']');
                        }
/* 680 */               this._parsingContext = this._parsingContext.getParent();
/* 681 */               JsonToken jsonToken2 = JsonToken.END_OBJECT;
                        this._currToken = jsonToken2;
/* 769 */               return jsonToken2;
                    }
/* 685 */           if (this._parsingContext.expectComma()) {
/* 686 */               if (i_skipWSOrEnd != 44) {
/* 687 */                   _reportUnexpectedChar(i_skipWSOrEnd, "was expecting comma to separate " + this._parsingContext.getTypeDesc() + " entries");
                        }
/* 689 */               i_skipWSOrEnd = _skipWS();
                    }
/* 696 */           if (!this._parsingContext.inObject()) {
/* 697 */               return _nextTokenNotInObject(i_skipWSOrEnd);
                    }
/* 701 */           this._parsingContext.setCurrentName(_parseName(i_skipWSOrEnd).getName());
/* 702 */           this._currToken = JsonToken.FIELD_NAME;
/* 705 */           if (this._inputPtr < this._inputEnd && this._inputBuffer[this._inputPtr] == 58) {
/* 706 */               this._inputPtr++;
                    } else {
/* 708 */               int i_skipWS = _skipWS();
/* 709 */               if (i_skipWS != 58) {
/* 710 */                   _reportUnexpectedChar(i_skipWS, "was expecting a colon to separate field name and value");
                        }
                    }
/* 713 */           int i_skipWS2 = _skipWS();
/* 716 */           if (i_skipWS2 == 34) {
/* 717 */               this._tokenIncomplete = true;
/* 718 */               this._nextToken = JsonToken.VALUE_STRING;
/* 719 */               return this._currToken;
                    }
                    switch (i_skipWS2) {
                        case 45:
                        case 48:
                        case 49:
                        case 50:
                        case 51:
                        case 52:
                        case 53:
                        case 54:
                        case 55:
                        case 56:
                        case 57:
/* 763 */                   jsonToken_parseNumber = _parseNumber(i_skipWS2);
                            break;
                        case 91:
/* 725 */                   jsonToken_parseNumber = JsonToken.START_ARRAY;
                            break;
                        case 93:
                        case 125:
/* 734 */                   _reportUnexpectedChar(i_skipWS2, "expected a value");
/* 736 */                   _matchToken("true", 1);
/* 737 */                   jsonToken_parseNumber = JsonToken.VALUE_TRUE;
                            break;
                        case 102:
/* 740 */                   _matchToken("false", 1);
/* 741 */                   jsonToken_parseNumber = JsonToken.VALUE_FALSE;
                            break;
                        case 110:
/* 744 */                   _matchToken("null", 1);
/* 745 */                   jsonToken_parseNumber = JsonToken.VALUE_NULL;
                            break;
                        case 116:
/* 736 */                   _matchToken("true", 1);
/* 737 */                   jsonToken_parseNumber = JsonToken.VALUE_TRUE;
                            break;
                        case 123:
/* 728 */                   jsonToken_parseNumber = JsonToken.START_OBJECT;
                            break;
                        default:
/* 766 */                   jsonToken_parseNumber = _handleUnexpectedValue(i_skipWS2);
                            break;
                    }
/* 768 */           this._nextToken = jsonToken_parseNumber;
/* 769 */           return this._currToken;
                }

/* 774 */       private final JsonToken _nextTokenNotInObject(int i) throws IOException {
/* 775 */           if (i == 34) {
/* 776 */               this._tokenIncomplete = true;
/* 777 */               JsonToken jsonToken = JsonToken.VALUE_STRING;
                        this._currToken = jsonToken;
/* 817 */               return jsonToken;
                    }
                    switch (i) {
                        case 45:
                        case 48:
                        case 49:
                        case 50:
                        case 51:
                        case 52:
                        case 53:
                        case 54:
                        case 55:
                        case 56:
                        case 57:
/* 815 */                   JsonToken jsonToken_parseNumber = _parseNumber(i);
                            this._currToken = jsonToken_parseNumber;
/* 817 */                   return jsonToken_parseNumber;
                        case 91:
/* 781 */                   this._parsingContext = this._parsingContext.createChildArrayContext(this._tokenInputRow, this._tokenInputCol);
/* 782 */                   JsonToken jsonToken2 = JsonToken.START_ARRAY;
                            this._currToken = jsonToken2;
/* 817 */                   return jsonToken2;
                        case 93:
                        case 125:
/* 790 */                   _reportUnexpectedChar(i, "expected a value");
                            break;
                        case 102:
/* 795 */                   _matchToken("false", 1);
/* 796 */                   JsonToken jsonToken3 = JsonToken.VALUE_FALSE;
                            this._currToken = jsonToken3;
/* 817 */                   return jsonToken3;
                        case 110:
/* 798 */                   _matchToken("null", 1);
/* 799 */                   JsonToken jsonToken4 = JsonToken.VALUE_NULL;
                            this._currToken = jsonToken4;
/* 817 */                   return jsonToken4;
                        case 116:
                            break;
                        case 123:
/* 784 */                   this._parsingContext = this._parsingContext.createChildObjectContext(this._tokenInputRow, this._tokenInputCol);
/* 785 */                   JsonToken jsonToken5 = JsonToken.START_OBJECT;
                            this._currToken = jsonToken5;
/* 817 */                   return jsonToken5;
                        default:
/* 817 */                   JsonToken jsonToken_handleUnexpectedValue = _handleUnexpectedValue(i);
                            this._currToken = jsonToken_handleUnexpectedValue;
/* 817 */                   return jsonToken_handleUnexpectedValue;
                    }
/* 792 */           _matchToken("true", 1);
/* 793 */           JsonToken jsonToken6 = JsonToken.VALUE_TRUE;
                    this._currToken = jsonToken6;
/* 817 */           return jsonToken6;
                }

/* 821 */       private final JsonToken _nextAfterName() {
/* 822 */           this._nameCopied = false;
/* 823 */           JsonToken jsonToken = this._nextToken;
/* 824 */           this._nextToken = null;
/* 826 */           if (jsonToken == JsonToken.START_ARRAY) {
/* 827 */               this._parsingContext = this._parsingContext.createChildArrayContext(this._tokenInputRow, this._tokenInputCol);
                    } else if (jsonToken == JsonToken.START_OBJECT) {
/* 829 */               this._parsingContext = this._parsingContext.createChildObjectContext(this._tokenInputRow, this._tokenInputCol);
                    }
/* 831 */           this._currToken = jsonToken;
                    return jsonToken;
                }

                @Override // com.fasterxml.jackson.core.JsonParser
/* 845 */       public boolean nextFieldName(SerializableString serializableString) throws IOException {
/* 846 */           this._numTypesValid = 0;
/* 847 */           if (this._currToken == JsonToken.FIELD_NAME) {
/* 848 */               _nextAfterName();
                        return false;
                    }
/* 851 */           if (this._tokenIncomplete) {
/* 852 */               _skipString();
                    }
/* 854 */           int i_skipWSOrEnd = _skipWSOrEnd();
/* 855 */           if (i_skipWSOrEnd < 0) {
/* 856 */               close();
/* 857 */               this._currToken = null;
                        return false;
                    }
/* 860 */           this._tokenInputTotal = (this._currInputProcessed + ((long) this._inputPtr)) - 1;
/* 861 */           this._tokenInputRow = this._currInputRow;
/* 862 */           this._tokenInputCol = (this._inputPtr - this._currInputRowStart) - 1;
/* 865 */           this._binaryValue = null;
/* 868 */           if (i_skipWSOrEnd == 93) {
/* 869 */               if (!this._parsingContext.inArray()) {
/* 870 */                   _reportMismatchedEndMarker(i_skipWSOrEnd, '}');
                        }
/* 872 */               this._parsingContext = this._parsingContext.getParent();
/* 873 */               this._currToken = JsonToken.END_ARRAY;
                        return false;
                    }
/* 876 */           if (i_skipWSOrEnd == 125) {
/* 877 */               if (!this._parsingContext.inObject()) {
/* 878 */                   _reportMismatchedEndMarker(i_skipWSOrEnd, ']');
                        }
/* 880 */               this._parsingContext = this._parsingContext.getParent();
/* 881 */               this._currToken = JsonToken.END_OBJECT;
                        return false;
                    }
/* 886 */           if (this._parsingContext.expectComma()) {
/* 887 */               if (i_skipWSOrEnd != 44) {
/* 888 */                   _reportUnexpectedChar(i_skipWSOrEnd, "was expecting comma to separate " + this._parsingContext.getTypeDesc() + " entries");
                        }
/* 890 */               i_skipWSOrEnd = _skipWS();
                    }
/* 893 */           if (!this._parsingContext.inObject()) {
/* 894 */               _nextTokenNotInObject(i_skipWSOrEnd);
                        return false;
                    }
/* 899 */           if (i_skipWSOrEnd == 34) {
/* 901 */               byte[] bArrAsQuotedUTF8 = serializableString.asQuotedUTF8();
/* 902 */               int length = bArrAsQuotedUTF8.length;
/* 903 */               if (this._inputPtr + length < this._inputEnd) {
/* 905 */                   int i = this._inputPtr + length;
/* 906 */                   if (this._inputBuffer[i] == 34) {
/* 908 */                       int i2 = this._inputPtr;
/* 910 */                       for (int i3 = 0; i3 != length; i3++) {
/* 919 */                           if (bArrAsQuotedUTF8[i3] == this._inputBuffer[i2 + i3]) {
                                    }
                                }
/* 911 */                       this._inputPtr = i + 1;
/* 913 */                       this._parsingContext.setCurrentName(serializableString.getValue());
/* 914 */                       this._currToken = JsonToken.FIELD_NAME;
/* 916 */                       _isNextTokenNameYes();
/* 917 */                       return true;
                            }
                        }
                    }
/* 927 */           return _isNextTokenNameMaybe(i_skipWSOrEnd, serializableString);
                }

/* 934 */       private final void _isNextTokenNameYes() throws IOException {
                    int i_skipColon;
/* 935 */           if (this._inputPtr < this._inputEnd - 1 && this._inputBuffer[this._inputPtr] == 58) {
/* 936 */               byte[] bArr = this._inputBuffer;
                        int i = this._inputPtr + 1;
                        this._inputPtr = i;
                        byte b = bArr[i];
/* 937 */               this._inputPtr++;
/* 938 */               if (b == 34) {
/* 939 */                   this._tokenIncomplete = true;
/* 940 */                   this._nextToken = JsonToken.VALUE_STRING;
/* 1000 */                  return;
                        } else {
/* 943 */                   if (b == 123) {
/* 944 */                       this._nextToken = JsonToken.START_OBJECT;
/* 1000 */                      return;
                            }
/* 947 */                   if (b == 91) {
/* 948 */                       this._nextToken = JsonToken.START_ARRAY;
/* 1000 */                      return;
                            }
/* 951 */                   i_skipColon = b & Constants.UNKNOWN;
/* 952 */                   if (i_skipColon <= 32 || i_skipColon == 47) {
/* 953 */                       this._inputPtr--;
/* 954 */                       i_skipColon = _skipWS();
                            }
                        }
                    } else {
/* 957 */               i_skipColon = _skipColon();
                    }
                    switch (i_skipColon) {
                        case 34:
/* 961 */                   this._tokenIncomplete = true;
/* 962 */                   this._nextToken = JsonToken.VALUE_STRING;
/* 1000 */                  return;
                        case 45:
                        case 48:
                        case 49:
                        case 50:
                        case 51:
                        case 52:
                        case 53:
                        case 54:
                        case 55:
                        case 56:
                        case 57:
/* 996 */                   this._nextToken = _parseNumber(i_skipColon);
/* 1000 */                  return;
                        case 91:
/* 965 */                   this._nextToken = JsonToken.START_ARRAY;
/* 1000 */                  return;
                        case 93:
                        case 125:
/* 972 */                   _reportUnexpectedChar(i_skipColon, "expected a value");
                            break;
                        case 102:
/* 978 */                   _matchToken("false", 1);
/* 979 */                   this._nextToken = JsonToken.VALUE_FALSE;
/* 1000 */                  return;
                        case 110:
/* 982 */                   _matchToken("null", 1);
/* 983 */                   this._nextToken = JsonToken.VALUE_NULL;
/* 1000 */                  return;
                        case 116:
                            break;
                        case 123:
/* 968 */                   this._nextToken = JsonToken.START_OBJECT;
/* 1000 */                  return;
                        default:
/* 999 */                   this._nextToken = _handleUnexpectedValue(i_skipColon);
/* 1000 */                  return;
                    }
/* 974 */           _matchToken("true", 1);
/* 975 */           this._nextToken = JsonToken.VALUE_TRUE;
                }

                /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
/* 1006 */      private final boolean _isNextTokenNameMaybe(int i, SerializableString serializableString) throws IOException {
                    JsonToken jsonToken_parseNumber;
/* 1010 */          String name = _parseName(i).getName();
/* 1011 */          this._parsingContext.setCurrentName(name);
/* 1012 */          boolean zEquals = name.equals(serializableString.getValue());
/* 1014 */          this._currToken = JsonToken.FIELD_NAME;
/* 1015 */          int i_skipWS = _skipWS();
/* 1016 */          if (i_skipWS != 58) {
/* 1017 */              _reportUnexpectedChar(i_skipWS, "was expecting a colon to separate field name and value");
                    }
/* 1019 */          int i_skipWS2 = _skipWS();
/* 1022 */          if (i_skipWS2 == 34) {
/* 1023 */              this._tokenIncomplete = true;
/* 1024 */              this._nextToken = JsonToken.VALUE_STRING;
                        return zEquals;
                    }
                    switch (i_skipWS2) {
                        case 45:
                        case 48:
                        case 49:
                        case 50:
                        case 51:
                        case 52:
                        case 53:
                        case 54:
                        case 55:
                        case 56:
                        case 57:
/* 1064 */                  jsonToken_parseNumber = _parseNumber(i_skipWS2);
                            break;
                        case 91:
/* 1031 */                  jsonToken_parseNumber = JsonToken.START_ARRAY;
                            break;
                        case 93:
                        case 125:
/* 1038 */                  _reportUnexpectedChar(i_skipWS2, "expected a value");
/* 1040 */                  _matchToken("true", 1);
/* 1041 */                  jsonToken_parseNumber = JsonToken.VALUE_TRUE;
                            break;
                        case 102:
/* 1044 */                  _matchToken("false", 1);
/* 1045 */                  jsonToken_parseNumber = JsonToken.VALUE_FALSE;
                            break;
                        case 110:
/* 1048 */                  _matchToken("null", 1);
/* 1049 */                  jsonToken_parseNumber = JsonToken.VALUE_NULL;
                            break;
                        case 116:
/* 1040 */                  _matchToken("true", 1);
/* 1041 */                  jsonToken_parseNumber = JsonToken.VALUE_TRUE;
                            break;
                        case 123:
/* 1034 */                  jsonToken_parseNumber = JsonToken.START_OBJECT;
                            break;
                        default:
/* 1067 */                  jsonToken_parseNumber = _handleUnexpectedValue(i_skipWS2);
                            break;
                    }
/* 1069 */          this._nextToken = jsonToken_parseNumber;
                    return zEquals;
                }

                @Override // com.fasterxml.jackson.core.JsonParser
/* 1077 */      public String nextTextValue() throws IOException {
/* 1078 */          if (this._currToken == JsonToken.FIELD_NAME) {
/* 1079 */              this._nameCopied = false;
/* 1080 */              JsonToken jsonToken = this._nextToken;
/* 1081 */              this._nextToken = null;
/* 1082 */              this._currToken = jsonToken;
/* 1083 */              if (jsonToken == JsonToken.VALUE_STRING) {
/* 1084 */                  if (this._tokenIncomplete) {
/* 1085 */                      this._tokenIncomplete = false;
/* 1086 */                      _finishString();
                            }
/* 1088 */                  return this._textBuffer.contentsAsString();
                        }
/* 1090 */              if (jsonToken == JsonToken.START_ARRAY) {
/* 1091 */                  this._parsingContext = this._parsingContext.createChildArrayContext(this._tokenInputRow, this._tokenInputCol);
                            return null;
                        }
/* 1092 */              if (jsonToken != JsonToken.START_OBJECT) {
                            return null;
                        }
/* 1093 */              this._parsingContext = this._parsingContext.createChildObjectContext(this._tokenInputRow, this._tokenInputCol);
                        return null;
                    }
/* 1098 */          if (nextToken() == JsonToken.VALUE_STRING) {
                        return getText();
                    }
                    return null;
                }

                @Override // com.fasterxml.jackson.core.JsonParser
/* 1105 */      public int nextIntValue(int i) throws IOException {
/* 1106 */          if (this._currToken != JsonToken.FIELD_NAME) {
                        return nextToken() == JsonToken.VALUE_NUMBER_INT ? getIntValue() : i;
                    }
/* 1107 */          this._nameCopied = false;
/* 1108 */          JsonToken jsonToken = this._nextToken;
/* 1109 */          this._nextToken = null;
/* 1110 */          this._currToken = jsonToken;
/* 1111 */          if (jsonToken == JsonToken.VALUE_NUMBER_INT) {
/* 1112 */              return getIntValue();
                    }
/* 1114 */          if (jsonToken == JsonToken.START_ARRAY) {
/* 1115 */              this._parsingContext = this._parsingContext.createChildArrayContext(this._tokenInputRow, this._tokenInputCol);
/* 1122 */              return i;
                    }
/* 1116 */          if (jsonToken == JsonToken.START_OBJECT) {
/* 1117 */              this._parsingContext = this._parsingContext.createChildObjectContext(this._tokenInputRow, this._tokenInputCol);
/* 1122 */              return i;
                    }
/* 1122 */          return i;
                }

                @Override // com.fasterxml.jackson.core.JsonParser
/* 1129 */      public long nextLongValue(long j) throws IOException {
/* 1130 */          if (this._currToken != JsonToken.FIELD_NAME) {
                        return nextToken() == JsonToken.VALUE_NUMBER_INT ? getLongValue() : j;
                    }
/* 1131 */          this._nameCopied = false;
/* 1132 */          JsonToken jsonToken = this._nextToken;
/* 1133 */          this._nextToken = null;
/* 1134 */          this._currToken = jsonToken;
/* 1135 */          if (jsonToken == JsonToken.VALUE_NUMBER_INT) {
/* 1136 */              return getLongValue();
                    }
/* 1138 */          if (jsonToken == JsonToken.START_ARRAY) {
/* 1139 */              this._parsingContext = this._parsingContext.createChildArrayContext(this._tokenInputRow, this._tokenInputCol);
/* 1146 */              return j;
                    }
/* 1140 */          if (jsonToken == JsonToken.START_OBJECT) {
/* 1141 */              this._parsingContext = this._parsingContext.createChildObjectContext(this._tokenInputRow, this._tokenInputCol);
/* 1146 */              return j;
                    }
/* 1146 */          return j;
                }

                @Override // com.fasterxml.jackson.core.JsonParser
/* 1153 */      public Boolean nextBooleanValue() throws IOException {
/* 1154 */          if (this._currToken == JsonToken.FIELD_NAME) {
/* 1155 */              this._nameCopied = false;
/* 1156 */              JsonToken jsonToken = this._nextToken;
/* 1157 */              this._nextToken = null;
/* 1158 */              this._currToken = jsonToken;
/* 1159 */              if (jsonToken == JsonToken.VALUE_TRUE) {
/* 1160 */                  return Boolean.TRUE;
                        }
/* 1162 */              if (jsonToken == JsonToken.VALUE_FALSE) {
/* 1163 */                  return Boolean.FALSE;
                        }
/* 1165 */              if (jsonToken == JsonToken.START_ARRAY) {
/* 1166 */                  this._parsingContext = this._parsingContext.createChildArrayContext(this._tokenInputRow, this._tokenInputCol);
                            return null;
                        }
/* 1167 */              if (jsonToken != JsonToken.START_OBJECT) {
                            return null;
                        }
/* 1168 */              this._parsingContext = this._parsingContext.createChildObjectContext(this._tokenInputRow, this._tokenInputCol);
                        return null;
                    }
                    switch (nextToken().m524id()) {
                        case 9:
/* 1175 */                  return Boolean.TRUE;
                        case 10:
/* 1177 */                  return Boolean.FALSE;
                        default:
                            return null;
                    }
                }

/* 1207 */      protected JsonToken _parseNumber(int i) throws IOException {
                    int i2;
                    int i_verifyNoLeadingZeroes;
                    int i3;
                    int i4 = 1;
/* 1208 */          char[] cArrEmptyAndGetCurrentSegment = this._textBuffer.emptyAndGetCurrentSegment();
/* 1210 */          boolean z = i == 45;
/* 1213 */          if (z) {
/* 1214 */              cArrEmptyAndGetCurrentSegment[0] = '-';
/* 1216 */              if (this._inputPtr >= this._inputEnd) {
/* 1217 */                  loadMoreGuaranteed();
                        }
/* 1219 */              byte[] bArr = this._inputBuffer;
                        int i5 = this._inputPtr;
                        this._inputPtr = i5 + 1;
                        i_verifyNoLeadingZeroes = bArr[i5] & Constants.UNKNOWN;
/* 1221 */              if (i_verifyNoLeadingZeroes < 48 || i_verifyNoLeadingZeroes > 57) {
/* 1222 */                  return _handleInvalidNumberStart(i_verifyNoLeadingZeroes, true);
                        }
                        i2 = 1;
                    } else {
                        i2 = 0;
                        i_verifyNoLeadingZeroes = i;
                    }
/* 1227 */          if (i_verifyNoLeadingZeroes == 48) {
/* 1228 */              i_verifyNoLeadingZeroes = _verifyNoLeadingZeroes();
                    }
/* 1232 */          int i6 = i2 + 1;
                    cArrEmptyAndGetCurrentSegment[i2] = (char) i_verifyNoLeadingZeroes;
/* 1236 */          int length = this._inputPtr + cArrEmptyAndGetCurrentSegment.length;
/* 1237 */          if (length > this._inputEnd) {
/* 1238 */              length = this._inputEnd;
                    }
/* 1243 */          while (this._inputPtr < length) {
/* 1247 */              byte[] bArr2 = this._inputBuffer;
                        int i7 = this._inputPtr;
                        this._inputPtr = i7 + 1;
                        int i8 = bArr2[i7] & Constants.UNKNOWN;
/* 1248 */              if (i8 >= 48 && i8 <= 57) {
/* 1251 */                  i4++;
/* 1252 */                  if (i6 >= cArrEmptyAndGetCurrentSegment.length) {
/* 1253 */                      cArrEmptyAndGetCurrentSegment = this._textBuffer.finishCurrentSegment();
                                i3 = 0;
                            } else {
                                i3 = i6;
                            }
/* 1256 */                  i6 = i3 + 1;
                            cArrEmptyAndGetCurrentSegment[i3] = (char) i8;
                        } else {
/* 1258 */                  if (i8 == 46 || i8 == 101 || i8 == 69) {
/* 1259 */                      return _parseFloat(cArrEmptyAndGetCurrentSegment, i6, i8, z, i4);
                            }
/* 1262 */                  this._inputPtr--;
/* 1263 */                  this._textBuffer.setCurrentLength(i6);
/* 1265 */                  if (this._parsingContext.inRoot()) {
/* 1266 */                      _verifyRootSpace(i8);
                            }
/* 1270 */                  return resetInt(z, i4);
                        }
                    }
/* 1245 */          return _parserNumber2(cArrEmptyAndGetCurrentSegment, i6, z, i4);
                }

/* 1282 */      private final JsonToken _parserNumber2(char[] cArr, int i, boolean z, int i2) throws IOException {
/* 1283 */          int i3 = i2;
                    int i4 = i;
                    char[] cArrFinishCurrentSegment = cArr;
                    while (true) {
                        if (this._inputPtr >= this._inputEnd && !loadMore()) {
/* 1284 */                  this._textBuffer.setCurrentLength(i4);
/* 1285 */                  return resetInt(z, i3);
                        }
/* 1287 */              byte[] bArr = this._inputBuffer;
                        int i5 = this._inputPtr;
                        this._inputPtr = i5 + 1;
                        int i6 = bArr[i5] & Constants.UNKNOWN;
/* 1288 */              if (i6 > 57 || i6 < 48) {
/* 1289 */                  if (i6 == 46 || i6 == 101 || i6 == 69) {
/* 1290 */                      return _parseFloat(cArrFinishCurrentSegment, i4, i6, z, i3);
                            }
/* 1301 */                  this._inputPtr--;
/* 1302 */                  this._textBuffer.setCurrentLength(i4);
/* 1304 */                  if (this._parsingContext.inRoot()) {
/* 1305 */                      byte[] bArr2 = this._inputBuffer;
                                int i7 = this._inputPtr;
                                this._inputPtr = i7 + 1;
                                _verifyRootSpace(bArr2[i7] & Constants.UNKNOWN);
                            }
/* 1309 */                  return resetInt(z, i3);
                        }
/* 1294 */              if (i4 >= cArrFinishCurrentSegment.length) {
/* 1295 */                  cArrFinishCurrentSegment = this._textBuffer.finishCurrentSegment();
/* 1296 */                  i4 = 0;
                        }
                        int i8 = i4;
/* 1298 */              i4 = i8 + 1;
                        cArrFinishCurrentSegment[i8] = (char) i6;
/* 1299 */              i3++;
                    }
                }

/* 1320 */      private final int _verifyNoLeadingZeroes() throws IOException {
/* 1321 */          if (this._inputPtr >= this._inputEnd && !loadMore()) {
                        return 48;
                    }
/* 1324 */          int i = this._inputBuffer[this._inputPtr] & Constants.UNKNOWN;
/* 1326 */          if (i < 48 || i > 57) {
                        return 48;
                    }
/* 1330 */          if (!isEnabled(JsonParser.Feature.ALLOW_NUMERIC_LEADING_ZEROS)) {
/* 1331 */              reportInvalidNumber("Leading zeroes not allowed");
                    }
/* 1334 */          this._inputPtr++;
/* 1335 */          if (i == 48) {
/* 1342 */              do {
/* 1336 */                  if (this._inputPtr < this._inputEnd || loadMore()) {
/* 1337 */                      i = this._inputBuffer[this._inputPtr] & Constants.UNKNOWN;
/* 1338 */                      if (i < 48 || i > 57) {
                                    return 48;
                                }
/* 1341 */                      this._inputPtr++;
                            } else {
/* 1347 */                      return i;
                            }
/* 1342 */              } while (i == 48);
/* 1347 */              return i;
                    }
/* 1347 */          return i;
                }

/* 1353 */      private final JsonToken _parseFloat(char[] cArr, int i, int i2, boolean z, int i3) throws IOException {
                    int i4;
                    int i5;
                    char[] cArrFinishCurrentSegment;
                    int i6;
                    int i7;
                    int i8;
                    int i9;
                    boolean z2;
                    int i10;
                    int i11;
/* 1354 */          int i12 = 0;
/* 1355 */          boolean z3 = false;
/* 1358 */          if (i2 != 46) {
/* 1354 */              i4 = 0;
                        i5 = i2;
                        cArrFinishCurrentSegment = cArr;
                        i6 = i;
                    } else {
/* 1359 */              int i13 = i + 1;
                        cArr[i] = (char) i2;
                        while (true) {
/* 1363 */                  if (this._inputPtr >= this._inputEnd && !loadMore()) {
/* 1364 */                      z3 = true;
                                i5 = i2;
                                break;
                            }
/* 1367 */                  byte[] bArr = this._inputBuffer;
                            int i14 = this._inputPtr;
                            this._inputPtr = i14 + 1;
                            i2 = bArr[i14] & Constants.UNKNOWN;
/* 1368 */                  if (i2 < 48) {
                                i5 = i2;
                                break;
                            }
                            if (i2 > 57) {
                                i5 = i2;
                                break;
                            }
/* 1371 */                  i12++;
/* 1372 */                  if (i13 >= cArr.length) {
/* 1373 */                      cArr = this._textBuffer.finishCurrentSegment();
/* 1374 */                      i13 = 0;
                            }
                            int i15 = i13;
/* 1376 */                  i13 = i15 + 1;
                            cArr[i15] = (char) i2;
                        }
/* 1379 */              if (i12 == 0) {
/* 1380 */                  reportUnexpectedNumberChar(i5, "Decimal point not followed by a digit");
                        }
                        i4 = i12;
                        i6 = i13;
                        cArrFinishCurrentSegment = cArr;
                    }
/* 1384 */          int i16 = 0;
/* 1385 */          if (i5 != 101 && i5 != 69) {
                        z2 = z3;
                        i8 = i5;
                        i10 = i6;
/* 1384 */              i11 = 0;
                    } else {
/* 1386 */              if (i6 >= cArrFinishCurrentSegment.length) {
/* 1387 */                  cArrFinishCurrentSegment = this._textBuffer.finishCurrentSegment();
/* 1388 */                  i6 = 0;
                        }
/* 1390 */              int i17 = i6 + 1;
                        cArrFinishCurrentSegment[i6] = (char) i5;
/* 1392 */              if (this._inputPtr >= this._inputEnd) {
/* 1393 */                  loadMoreGuaranteed();
                        }
/* 1395 */              byte[] bArr2 = this._inputBuffer;
                        int i18 = this._inputPtr;
                        this._inputPtr = i18 + 1;
                        int i19 = bArr2[i18] & Constants.UNKNOWN;
/* 1397 */              if (i19 == 45 || i19 == 43) {
/* 1398 */                  if (i17 >= cArrFinishCurrentSegment.length) {
/* 1399 */                      cArrFinishCurrentSegment = this._textBuffer.finishCurrentSegment();
/* 1400 */                      i7 = 0;
                            } else {
                                i7 = i17;
                            }
/* 1402 */                  int i20 = i7 + 1;
                            cArrFinishCurrentSegment[i7] = (char) i19;
/* 1404 */                  if (this._inputPtr >= this._inputEnd) {
/* 1405 */                      loadMoreGuaranteed();
                            }
/* 1407 */                  byte[] bArr3 = this._inputBuffer;
                            int i21 = this._inputPtr;
                            this._inputPtr = i21 + 1;
                            i8 = bArr3[i21] & Constants.UNKNOWN;
                            i9 = i20;
                        } else {
                            i9 = i17;
                            i8 = i19;
                        }
                        while (true) {
/* 1411 */                  if (i8 <= 57 && i8 >= 48) {
/* 1412 */                      i16++;
/* 1413 */                      if (i9 >= cArrFinishCurrentSegment.length) {
/* 1414 */                          cArrFinishCurrentSegment = this._textBuffer.finishCurrentSegment();
/* 1415 */                          i9 = 0;
                                }
/* 1417 */                      int i22 = i9 + 1;
                                cArrFinishCurrentSegment[i9] = (char) i8;
/* 1418 */                      if (this._inputPtr >= this._inputEnd && !loadMore()) {
                                    i11 = i16;
/* 1419 */                          z2 = true;
                                    i10 = i22;
                                    break;
                                }
/* 1422 */                      byte[] bArr4 = this._inputBuffer;
                                int i23 = this._inputPtr;
                                this._inputPtr = i23 + 1;
                                i8 = bArr4[i23] & Constants.UNKNOWN;
                                i9 = i22;
                            } else {
                                z2 = z3;
                                int i24 = i16;
                                i10 = i9;
                                i11 = i24;
                                break;
                            }
                        }
/* 1425 */              if (i11 == 0) {
/* 1426 */                  reportUnexpectedNumberChar(i8, "Exponent indicator not followed by a digit");
                        }
                    }
/* 1431 */          if (!z2) {
/* 1432 */              this._inputPtr--;
/* 1434 */              if (this._parsingContext.inRoot()) {
/* 1435 */                  _verifyRootSpace(i8);
                        }
                    }
/* 1438 */          this._textBuffer.setCurrentLength(i10);
/* 1441 */          return resetFloat(z, i3, i4, i11);
                }

/* 1453 */      private final void _verifyRootSpace(int i) throws IOException {
/* 1454 */          this._inputPtr++;
                    switch (i) {
                        case 9:
                        case 32:
                            break;
                        case 10:
/* 1464 */                  this._currInputRow++;
/* 1465 */                  this._currInputRowStart = this._inputPtr;
                            break;
                        case 13:
/* 1461 */                  _skipCR();
                            break;
                        default:
/* 1468 */                  _reportMissingRootWS(i);
                            break;
                    }
                }

/* 1478 */      protected Name _parseName(int i) throws IOException {
/* 1479 */          if (i != 34) {
/* 1480 */              return _handleOddName(i);
                    }
/* 1483 */          if (this._inputPtr + 9 > this._inputEnd) {
/* 1484 */              return slowParseName();
                    }
/* 1493 */          byte[] bArr = this._inputBuffer;
/* 1494 */          int[] iArr = _icLatin1;
/* 1496 */          int i2 = this._inputPtr;
                    this._inputPtr = i2 + 1;
                    int i3 = bArr[i2] & Constants.UNKNOWN;
/* 1498 */          if (iArr[i3] == 0) {
/* 1499 */              int i4 = this._inputPtr;
                        this._inputPtr = i4 + 1;
                        int i5 = bArr[i4] & Constants.UNKNOWN;
/* 1500 */              if (iArr[i5] == 0) {
/* 1501 */                  int i6 = (i3 << 8) | i5;
/* 1502 */                  int i7 = this._inputPtr;
                            this._inputPtr = i7 + 1;
                            int i8 = bArr[i7] & Constants.UNKNOWN;
/* 1503 */                  if (iArr[i8] == 0) {
/* 1504 */                      int i9 = (i6 << 8) | i8;
/* 1505 */                      int i10 = this._inputPtr;
                                this._inputPtr = i10 + 1;
                                int i11 = bArr[i10] & Constants.UNKNOWN;
/* 1506 */                      if (iArr[i11] == 0) {
/* 1507 */                          int i12 = (i9 << 8) | i11;
/* 1508 */                          int i13 = this._inputPtr;
                                    this._inputPtr = i13 + 1;
                                    int i14 = bArr[i13] & Constants.UNKNOWN;
/* 1509 */                          if (iArr[i14] == 0) {
/* 1510 */                              this._quad1 = i12;
/* 1511 */                              return parseMediumName(i14, iArr);
                                    }
/* 1513 */                          if (i14 == 34) {
/* 1514 */                              return findName(i12, 4);
                                    }
/* 1516 */                          return parseName(i12, i14, 4);
                                }
/* 1518 */                      if (i11 == 34) {
/* 1519 */                          return findName(i9, 3);
                                }
/* 1521 */                      return parseName(i9, i11, 3);
                            }
/* 1523 */                  if (i8 == 34) {
/* 1524 */                      return findName(i6, 2);
                            }
/* 1526 */                  return parseName(i6, i8, 2);
                        }
/* 1528 */              if (i5 == 34) {
/* 1529 */                  return findName(i3, 1);
                        }
/* 1531 */              return parseName(i3, i5, 1);
                    }
/* 1533 */          if (i3 == 34) {
/* 1534 */              return BytesToNameCanonicalizer.getEmptyName();
                    }
/* 1536 */          return parseName(0, i3, 0);
                }

/* 1541 */      protected Name parseMediumName(int i, int[] iArr) throws IOException {
/* 1542 */          byte[] bArr = this._inputBuffer;
                    int i2 = this._inputPtr;
                    this._inputPtr = i2 + 1;
                    int i3 = bArr[i2] & Constants.UNKNOWN;
/* 1543 */          if (iArr[i3] != 0) {
/* 1544 */              if (i3 == 34) {
/* 1545 */                  return findName(this._quad1, i, 1);
                        }
/* 1547 */              return parseName(this._quad1, i, i3, 1);
                    }
/* 1549 */          int i4 = i3 | (i << 8);
/* 1550 */          byte[] bArr2 = this._inputBuffer;
                    int i5 = this._inputPtr;
                    this._inputPtr = i5 + 1;
                    int i6 = bArr2[i5] & Constants.UNKNOWN;
/* 1551 */          if (iArr[i6] != 0) {
/* 1552 */              if (i6 == 34) {
/* 1553 */                  return findName(this._quad1, i4, 2);
                        }
/* 1555 */              return parseName(this._quad1, i4, i6, 2);
                    }
/* 1557 */          int i7 = (i4 << 8) | i6;
/* 1558 */          byte[] bArr3 = this._inputBuffer;
                    int i8 = this._inputPtr;
                    this._inputPtr = i8 + 1;
                    int i9 = bArr3[i8] & Constants.UNKNOWN;
/* 1559 */          if (iArr[i9] != 0) {
/* 1560 */              if (i9 == 34) {
/* 1561 */                  return findName(this._quad1, i7, 3);
                        }
/* 1563 */              return parseName(this._quad1, i7, i9, 3);
                    }
/* 1565 */          int i10 = (i7 << 8) | i9;
/* 1566 */          byte[] bArr4 = this._inputBuffer;
                    int i11 = this._inputPtr;
                    this._inputPtr = i11 + 1;
                    int i12 = bArr4[i11] & Constants.UNKNOWN;
/* 1567 */          if (iArr[i12] != 0) {
/* 1568 */              if (i12 == 34) {
/* 1569 */                  return findName(this._quad1, i10, 4);
                        }
/* 1571 */              return parseName(this._quad1, i10, i12, 4);
                    }
/* 1573 */          this._quadBuffer[0] = this._quad1;
/* 1574 */          this._quadBuffer[1] = i10;
/* 1575 */          return parseLongName(i12);
                }

/* 1580 */      protected Name parseLongName(int i) throws IOException {
/* 1581 */          int[] iArr = _icLatin1;
                    int i2 = 2;
                    int i3 = i;
/* 1589 */          while (this._inputEnd - this._inputPtr >= 4) {
/* 1594 */              byte[] bArr = this._inputBuffer;
                        int i4 = this._inputPtr;
                        this._inputPtr = i4 + 1;
                        int i5 = bArr[i4] & Constants.UNKNOWN;
/* 1595 */              if (iArr[i5] != 0) {
/* 1596 */                  if (i5 == 34) {
/* 1597 */                      return findName(this._quadBuffer, i2, i3, 1);
                            }
/* 1599 */                  return parseEscapedName(this._quadBuffer, i2, i3, i5, 1);
                        }
/* 1602 */              int i6 = (i3 << 8) | i5;
/* 1603 */              byte[] bArr2 = this._inputBuffer;
                        int i7 = this._inputPtr;
                        this._inputPtr = i7 + 1;
                        int i8 = bArr2[i7] & Constants.UNKNOWN;
/* 1604 */              if (iArr[i8] != 0) {
/* 1605 */                  if (i8 == 34) {
/* 1606 */                      return findName(this._quadBuffer, i2, i6, 2);
                            }
/* 1608 */                  return parseEscapedName(this._quadBuffer, i2, i6, i8, 2);
                        }
/* 1611 */              int i9 = (i6 << 8) | i8;
/* 1612 */              byte[] bArr3 = this._inputBuffer;
                        int i10 = this._inputPtr;
                        this._inputPtr = i10 + 1;
                        int i11 = bArr3[i10] & Constants.UNKNOWN;
/* 1613 */              if (iArr[i11] != 0) {
/* 1614 */                  if (i11 == 34) {
/* 1615 */                      return findName(this._quadBuffer, i2, i9, 3);
                            }
/* 1617 */                  return parseEscapedName(this._quadBuffer, i2, i9, i11, 3);
                        }
/* 1620 */              int i12 = (i9 << 8) | i11;
/* 1621 */              byte[] bArr4 = this._inputBuffer;
                        int i13 = this._inputPtr;
                        this._inputPtr = i13 + 1;
                        i3 = bArr4[i13] & Constants.UNKNOWN;
/* 1622 */              if (iArr[i3] != 0) {
/* 1623 */                  if (i3 == 34) {
/* 1624 */                      return findName(this._quadBuffer, i2, i12, 4);
                            }
/* 1626 */                  return parseEscapedName(this._quadBuffer, i2, i12, i3, 4);
                        }
/* 1630 */              if (i2 >= this._quadBuffer.length) {
/* 1631 */                  this._quadBuffer = growArrayBy(this._quadBuffer, i2);
                        }
/* 1633 */              this._quadBuffer[i2] = i12;
                        i2++;
                    }
/* 1590 */          return parseEscapedName(this._quadBuffer, i2, 0, i3, 0);
                }

/* 1644 */      protected Name slowParseName() throws IOException {
/* 1645 */          if (this._inputPtr >= this._inputEnd && !loadMore()) {
/* 1647 */              _reportInvalidEOF(": was expecting closing '\"' for name");
                    }
/* 1650 */          byte[] bArr = this._inputBuffer;
                    int i = this._inputPtr;
                    this._inputPtr = i + 1;
                    int i2 = bArr[i] & Constants.UNKNOWN;
                    return i2 == 34 ? BytesToNameCanonicalizer.getEmptyName() : parseEscapedName(this._quadBuffer, 0, 0, i2, 0);
                }

/* 1658 */      private final Name parseName(int i, int i2, int i3) throws IOException {
/* 1659 */          return parseEscapedName(this._quadBuffer, 0, i, i2, i3);
                }

/* 1663 */      private final Name parseName(int i, int i2, int i3, int i4) throws IOException {
/* 1664 */          this._quadBuffer[0] = i;
/* 1665 */          return parseEscapedName(this._quadBuffer, 1, i2, i3, i4);
                }

                /* JADX WARN: Code duplicated, block: B:52:0x00d7 A[PHI: r13
  0x00d7: PHI (r13v2 int) = (r13v1 int), (r13v5 int) binds: [B:4:0x0006, B:20:0x0035] A[DONT_GENERATE, DONT_INLINE]] */
/* 1682 */      protected Name parseEscapedName(int[] iArr, int i, int i2, int i3, int i4) throws IOException {
                    int i5;
                    int[] iArrGrowArrayBy;
                    int i6;
                    int[] iArrGrowArrayBy2;
                    int i7;
                    int i8;
                    int[] iArr2;
                    int i9;
                    int i10;
                    int i11;
                    int[] iArr3;
/* 1683 */          int[] iArr4 = _icLatin1;
                    while (true) {
/* 1686 */              if (iArr4[i3] == 0) {
                            i5 = i2;
                            iArrGrowArrayBy = iArr;
                            i2 = i3;
                        } else {
/* 1687 */                  if (i3 == 34) {
                                break;
                            }
/* 1691 */                  if (i3 != 92) {
/* 1693 */                      _throwUnquotedSpace(i3, "name");
                            } else {
/* 1696 */                      i3 = _decodeEscaped();
                            }
/* 1703 */                  if (i3 > 127) {
/* 1705 */                      if (i4 >= 4) {
/* 1706 */                          if (i >= iArr.length) {
/* 1707 */                              iArr = growArrayBy(iArr, iArr.length);
                                        this._quadBuffer = iArr;
                                    }
/* 1709 */                          i6 = i + 1;
                                    iArr[i] = i2;
                                    i4 = 0;
                                    i2 = 0;
                                    iArrGrowArrayBy2 = iArr;
                                } else {
                                    i6 = i;
                                    iArrGrowArrayBy2 = iArr;
                                }
/* 1713 */                      if (i3 < 2048) {
/* 1714 */                          i10 = (i3 >> 6) | 192 | (i2 << 8);
                                    iArr3 = iArrGrowArrayBy2;
/* 1715 */                          i11 = i4 + 1;
                                } else {
/* 1718 */                          int i12 = (i3 >> 12) | 224 | (i2 << 8);
/* 1719 */                          int i13 = i4 + 1;
/* 1721 */                          if (i13 >= 4) {
/* 1722 */                              if (i6 >= iArrGrowArrayBy2.length) {
/* 1723 */                                  iArrGrowArrayBy2 = growArrayBy(iArrGrowArrayBy2, iArrGrowArrayBy2.length);
                                            this._quadBuffer = iArrGrowArrayBy2;
                                        }
                                        iArrGrowArrayBy2[i6] = i12;
/* 1725 */                              i8 = i6 + 1;
                                        iArr2 = iArrGrowArrayBy2;
                                        i9 = 0;
                                        i7 = 0;
                                    } else {
                                        i7 = i12;
                                        i8 = i6;
                                        iArr2 = iArrGrowArrayBy2;
                                        i9 = i13;
                                    }
/* 1729 */                          i10 = (i7 << 8) | ((i3 >> 6) & 63) | 128;
/* 1730 */                          i11 = i9 + 1;
                                    int i14 = i8;
                                    iArr3 = iArr2;
                                    i6 = i14;
                                }
/* 1733 */                      i2 = (i3 & 63) | 128;
                                i4 = i11;
                                i = i6;
                                iArrGrowArrayBy = iArr3;
                                i5 = i10;
                            } else {
                                i5 = i2;
                                iArrGrowArrayBy = iArr;
                                i2 = i3;
                            }
                        }
/* 1737 */              if (i4 < 4) {
/* 1738 */                  i4++;
/* 1739 */                  i2 |= i5 << 8;
                            iArr = iArrGrowArrayBy;
                        } else {
/* 1741 */                  if (i >= iArrGrowArrayBy.length) {
/* 1742 */                      iArrGrowArrayBy = growArrayBy(iArrGrowArrayBy, iArrGrowArrayBy.length);
                                this._quadBuffer = iArrGrowArrayBy;
                            }
                            iArrGrowArrayBy[i] = i5;
/* 1746 */                  i4 = 1;
/* 1744 */                  i++;
                            iArr = iArrGrowArrayBy;
                        }
/* 1748 */              if (this._inputPtr >= this._inputEnd && !loadMore()) {
/* 1750 */                  _reportInvalidEOF(" in field name");
                        }
/* 1753 */              byte[] bArr = this._inputBuffer;
                        int i15 = this._inputPtr;
                        this._inputPtr = i15 + 1;
                        i3 = bArr[i15] & Constants.UNKNOWN;
                    }
/* 1756 */          if (i4 > 0) {
/* 1757 */              if (i >= iArr.length) {
/* 1758 */                  iArr = growArrayBy(iArr, iArr.length);
                            this._quadBuffer = iArr;
                        }
                        iArr[i] = i2;
/* 1760 */              i++;
                    }
/* 1762 */          Name nameFindName = this._symbols.findName(iArr, i);
/* 1763 */          if (nameFindName == null) {
/* 1764 */              return addName(iArr, i, i4);
                    }
/* 1766 */          return nameFindName;
                }

/* 1777 */      protected Name _handleOddName(int i) throws IOException {
                    int[] iArrGrowArrayBy;
                    int i2;
                    int i3;
                    int i4;
/* 1778 */          if (i == 39 && isEnabled(JsonParser.Feature.ALLOW_SINGLE_QUOTES)) {
/* 1779 */              return _parseAposName();
                    }
/* 1782 */          if (!isEnabled(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES)) {
/* 1783 */              _reportUnexpectedChar(i, "was expecting double-quote to start field name");
                    }
/* 1789 */          int[] inputCodeUtf8JsNames = CharTypes.getInputCodeUtf8JsNames();
/* 1791 */          if (inputCodeUtf8JsNames[i] != 0) {
/* 1792 */              _reportUnexpectedChar(i, "was expecting either valid name character (for unquoted name) or double-quote (for quoted) to start field name");
                    }
                    int i5 = 0;
                    int i6 = 0;
                    int i7 = i;
                    int i8 = 0;
/* 1799 */          int[] iArrGrowArrayBy2 = this._quadBuffer;
                    while (true) {
/* 1806 */              if (i5 < 4) {
/* 1807 */                  int i9 = i5 + 1;
/* 1808 */                  i3 = i7 | (i6 << 8);
                            i4 = i8;
                            iArrGrowArrayBy = iArrGrowArrayBy2;
                            i2 = i9;
                        } else {
/* 1810 */                  if (i8 >= iArrGrowArrayBy2.length) {
/* 1811 */                      iArrGrowArrayBy2 = growArrayBy(iArrGrowArrayBy2, iArrGrowArrayBy2.length);
                                this._quadBuffer = iArrGrowArrayBy2;
                            }
/* 1813 */                  int i10 = i8 + 1;
                            iArrGrowArrayBy2[i8] = i6;
                            iArrGrowArrayBy = iArrGrowArrayBy2;
/* 1815 */                  i2 = 1;
                            i3 = i7;
                            i4 = i10;
                        }
/* 1817 */              if (this._inputPtr >= this._inputEnd && !loadMore()) {
/* 1819 */                  _reportInvalidEOF(" in field name");
                        }
/* 1822 */              int i11 = this._inputBuffer[this._inputPtr] & Constants.UNKNOWN;
/* 1823 */              if (inputCodeUtf8JsNames[i11] != 0) {
                            break;
                        }
/* 1826 */              this._inputPtr++;
                        i6 = i3;
                        i5 = i2;
                        iArrGrowArrayBy2 = iArrGrowArrayBy;
                        i8 = i4;
                        i7 = i11;
                    }
/* 1829 */          if (i2 > 0) {
/* 1830 */              if (i4 >= iArrGrowArrayBy.length) {
/* 1831 */                  iArrGrowArrayBy = growArrayBy(iArrGrowArrayBy, iArrGrowArrayBy.length);
                            this._quadBuffer = iArrGrowArrayBy;
                        }
                        iArrGrowArrayBy[i4] = i3;
/* 1833 */              i4++;
                    }
/* 1835 */          Name nameFindName = this._symbols.findName(iArrGrowArrayBy, i4);
                    return nameFindName == null ? addName(iArrGrowArrayBy, i4, i2) : nameFindName;
                }

                /* JADX WARN: Code duplicated, block: B:63:0x011a A[PHI: r5
  0x011a: PHI (r5v2 int) = (r5v1 int), (r5v1 int), (r5v12 int) binds: [B:22:0x0053, B:24:0x0057, B:29:0x0064] A[DONT_GENERATE, DONT_INLINE]] */
/* 1848 */      protected Name _parseAposName() throws IOException {
                    int[] iArr;
                    int i;
                    int i2;
                    int i3;
                    int i4;
                    int i5;
                    int[] iArr2;
                    int i6;
                    int i7;
                    int i8;
                    int i9;
                    int i10;
                    int i11;
                    int i12;
                    int[] iArr3;
                    int i13;
                    int i14;
                    int i15;
                    int[] iArr4;
/* 1849 */          if (this._inputPtr >= this._inputEnd && !loadMore()) {
/* 1851 */              _reportInvalidEOF(": was expecting closing ''' for name");
                    }
/* 1854 */          byte[] bArr = this._inputBuffer;
                    int i16 = this._inputPtr;
                    this._inputPtr = i16 + 1;
                    int i_decodeEscaped = bArr[i16] & Constants.UNKNOWN;
/* 1855 */          if (i_decodeEscaped == 39) {
/* 1856 */              return BytesToNameCanonicalizer.getEmptyName();
                    }
/* 1858 */          int[] iArrGrowArrayBy = this._quadBuffer;
/* 1865 */          int[] iArr5 = _icLatin1;
                    int i17 = 0;
                    int i18 = 0;
                    int i19 = 0;
/* 1868 */          while (i_decodeEscaped != 39) {
/* 1872 */              if (i_decodeEscaped == 34 || iArr5[i_decodeEscaped] == 0) {
                            i2 = i18;
                            i3 = i19;
                            i4 = i17;
                            i5 = i_decodeEscaped;
                        } else {
/* 1873 */                  if (i_decodeEscaped != 92) {
/* 1876 */                      _throwUnquotedSpace(i_decodeEscaped, "name");
                            } else {
/* 1879 */                      i_decodeEscaped = _decodeEscaped();
                            }
/* 1886 */                  if (i_decodeEscaped > 127) {
/* 1888 */                      if (i17 >= 4) {
/* 1889 */                          if (i19 >= iArrGrowArrayBy.length) {
/* 1890 */                              iArrGrowArrayBy = growArrayBy(iArrGrowArrayBy, iArrGrowArrayBy.length);
                                        this._quadBuffer = iArrGrowArrayBy;
                                    }
/* 1892 */                          int i20 = i19 + 1;
                                    iArrGrowArrayBy[i19] = i18;
                                    i10 = 0;
                                    i3 = i20;
                                    i9 = 0;
                                } else {
                                    int i21 = i17;
                                    i9 = i18;
                                    i3 = i19;
                                    i10 = i21;
                                }
/* 1896 */                      if (i_decodeEscaped < 2048) {
/* 1898 */                          int i22 = i10 + 1;
/* 1897 */                          i14 = (i9 << 8) | (i_decodeEscaped >> 6) | 192;
                                    iArr4 = iArrGrowArrayBy;
                                    i15 = i22;
                                } else {
/* 1901 */                          int i23 = (i9 << 8) | (i_decodeEscaped >> 12) | 224;
/* 1902 */                          int i24 = i10 + 1;
/* 1904 */                          if (i24 >= 4) {
/* 1905 */                              if (i3 >= iArrGrowArrayBy.length) {
/* 1906 */                                  iArrGrowArrayBy = growArrayBy(iArrGrowArrayBy, iArrGrowArrayBy.length);
                                            this._quadBuffer = iArrGrowArrayBy;
                                        }
                                        iArrGrowArrayBy[i3] = i23;
/* 1908 */                              i12 = i3 + 1;
                                        iArr3 = iArrGrowArrayBy;
                                        i13 = 0;
                                        i11 = 0;
                                    } else {
                                        i11 = i23;
                                        i12 = i3;
                                        iArr3 = iArrGrowArrayBy;
                                        i13 = i24;
                                    }
/* 1912 */                          i14 = (i11 << 8) | ((i_decodeEscaped >> 6) & 63) | 128;
/* 1913 */                          i15 = i13 + 1;
                                    int i25 = i12;
                                    iArr4 = iArr3;
                                    i3 = i25;
                                }
                                i2 = i14;
                                i4 = i15;
                                iArrGrowArrayBy = iArr4;
/* 1916 */                      i5 = (i_decodeEscaped & 63) | 128;
                            } else {
                                i2 = i18;
                                i3 = i19;
                                i4 = i17;
                                i5 = i_decodeEscaped;
                            }
                        }
/* 1920 */              if (i4 < 4) {
/* 1921 */                  int i26 = i4 + 1;
/* 1922 */                  i7 = i5 | (i2 << 8);
                            i8 = i3;
                            iArr2 = iArrGrowArrayBy;
                            i6 = i26;
                        } else {
/* 1924 */                  if (i3 >= iArrGrowArrayBy.length) {
/* 1925 */                      iArrGrowArrayBy = growArrayBy(iArrGrowArrayBy, iArrGrowArrayBy.length);
                                this._quadBuffer = iArrGrowArrayBy;
                            }
/* 1927 */                  int i27 = i3 + 1;
                            iArrGrowArrayBy[i3] = i2;
                            iArr2 = iArrGrowArrayBy;
/* 1929 */                  i6 = 1;
                            i7 = i5;
                            i8 = i27;
                        }
/* 1931 */              if (this._inputPtr >= this._inputEnd && !loadMore()) {
/* 1933 */                  _reportInvalidEOF(" in field name");
                        }
/* 1936 */              byte[] bArr2 = this._inputBuffer;
                        int i28 = this._inputPtr;
                        this._inputPtr = i28 + 1;
                        i_decodeEscaped = bArr2[i28] & Constants.UNKNOWN;
                        int i29 = i6;
                        iArrGrowArrayBy = iArr2;
                        i18 = i7;
                        i19 = i8;
                        i17 = i29;
                    }
/* 1939 */          if (i17 > 0) {
/* 1940 */              if (i19 >= iArrGrowArrayBy.length) {
/* 1941 */                  iArrGrowArrayBy = growArrayBy(iArrGrowArrayBy, iArrGrowArrayBy.length);
                            this._quadBuffer = iArrGrowArrayBy;
                        }
                        iArrGrowArrayBy[i19] = i18;
                        iArr = iArrGrowArrayBy;
/* 1943 */              i = i19 + 1;
                    } else {
                        iArr = iArrGrowArrayBy;
                        i = i19;
                    }
/* 1945 */          Name nameFindName = this._symbols.findName(iArr, i);
                    return nameFindName == null ? addName(iArr, i, i17) : nameFindName;
                }

/* 1961 */      private final Name findName(int i, int i2) throws JsonParseException {
/* 1962 */          Name nameFindName = this._symbols.findName(i);
/* 1963 */          if (nameFindName == null) {
/* 1967 */              this._quadBuffer[0] = i;
/* 1968 */              return addName(this._quadBuffer, 1, i2);
                    }
/* 1968 */          return nameFindName;
                }

/* 1974 */      private final Name findName(int i, int i2, int i3) throws JsonParseException {
/* 1975 */          Name nameFindName = this._symbols.findName(i, i2);
/* 1976 */          if (nameFindName == null) {
/* 1980 */              this._quadBuffer[0] = i;
/* 1981 */              this._quadBuffer[1] = i2;
/* 1982 */              return addName(this._quadBuffer, 2, i3);
                    }
/* 1982 */          return nameFindName;
                }

/* 1987 */      private final Name findName(int[] iArr, int i, int i2, int i3) throws JsonParseException {
/* 1988 */          if (i >= iArr.length) {
/* 1989 */              iArr = growArrayBy(iArr, iArr.length);
                        this._quadBuffer = iArr;
                    }
/* 1991 */          int i4 = i + 1;
                    iArr[i] = i2;
/* 1992 */          Name nameFindName = this._symbols.findName(iArr, i4);
/* 1993 */          if (nameFindName == null) {
/* 1994 */              return addName(iArr, i4, i3);
                    }
/* 1996 */          return nameFindName;
                }

                /* JADX WARN: Code duplicated, block: B:52:0x0114 A[PHI: r2 r3
  0x0114: PHI (r2v6 int) = (r2v5 int), (r2v17 int) binds: [B:8:0x0033, B:30:0x00a4] A[DONT_GENERATE, DONT_INLINE]
  0x0114: PHI (r3v4 int) = (r3v3 int), (r3v9 int) binds: [B:8:0x0033, B:30:0x00a4] A[DONT_GENERATE, DONT_INLINE]] */
/* 2012 */      private final Name addName(int[] iArr, int i, int i2) throws JsonParseException {
                    int i3;
                    char[] cArrExpandCurrentSegment;
                    int i4;
                    int i5;
                    int i6;
                    int i7;
                    int i8;
/* 2013 */          int i9 = ((i << 2) - 4) + i2;
/* 2022 */          if (i2 < 4) {
/* 2023 */              i3 = iArr[i - 1];
/* 2025 */              iArr[i - 1] = i3 << ((4 - i2) << 3);
                    } else {
/* 2027 */              i3 = 0;
                    }
/* 2031 */          char[] cArrEmptyAndGetCurrentSegment = this._textBuffer.emptyAndGetCurrentSegment();
/* 2032 */          int i10 = 0;
/* 2034 */          int i11 = 0;
                    while (i11 < i9) {
/* 2037 */              int i12 = (iArr[i11 >> 2] >> ((3 - (i11 & 3)) << 3)) & 255;
/* 2038 */              int i13 = i11 + 1;
/* 2040 */              if (i12 <= 127) {
                            cArrExpandCurrentSegment = cArrEmptyAndGetCurrentSegment;
                            i4 = i12;
                            i5 = i13;
                            i6 = i10;
                        } else {
/* 2042 */                  if ((i12 & 224) == 192) {
/* 2043 */                      i7 = i12 & 31;
/* 2044 */                      i8 = 1;
                            } else if ((i12 & 240) == 224) {
/* 2046 */                      i7 = i12 & 15;
/* 2047 */                      i8 = 2;
                            } else if ((i12 & 248) == 240) {
/* 2049 */                      i7 = i12 & 7;
/* 2050 */                      i8 = 3;
                            } else {
/* 2052 */                      _reportInvalidInitial(i12);
/* 2053 */                      i7 = 1;
/* 2053 */                      i8 = 1;
                            }
/* 2055 */                  if (i13 + i8 > i9) {
/* 2056 */                      _reportInvalidEOF(" in field name");
                            }
/* 2062 */                  int i14 = iArr[i13 >> 2] >> ((3 - (i13 & 3)) << 3);
/* 2063 */                  i13++;
/* 2065 */                  if ((i14 & 192) != 128) {
/* 2066 */                      _reportInvalidOther(i14);
                            }
/* 2068 */                  i12 = (i7 << 6) | (i14 & 63);
/* 2069 */                  if (i8 > 1) {
/* 2072 */                      int i15 = iArr[i13 >> 2] >> ((3 - (i13 & 3)) << 3);
/* 2073 */                      i13++;
/* 2075 */                      if ((i15 & 192) != 128) {
/* 2076 */                          _reportInvalidOther(i15);
                                }
/* 2078 */                      i12 = (i12 << 6) | (i15 & 63);
/* 2079 */                      if (i8 > 2) {
/* 2082 */                          int i16 = iArr[i13 >> 2] >> ((3 - (i13 & 3)) << 3);
/* 2083 */                          i13++;
/* 2084 */                          if ((i16 & 192) != 128) {
/* 2085 */                              _reportInvalidOther(i16 & 255);
                                    }
/* 2087 */                          i12 = (i12 << 6) | (i16 & 63);
                                }
                            }
/* 2090 */                  if (i8 > 2) {
/* 2091 */                      int i17 = i12 - 65536;
/* 2092 */                      if (i10 >= cArrEmptyAndGetCurrentSegment.length) {
/* 2093 */                          cArrEmptyAndGetCurrentSegment = this._textBuffer.expandCurrentSegment();
                                }
                                cArrEmptyAndGetCurrentSegment[i10] = (char) (55296 + (i17 >> 10));
/* 2096 */                      int i18 = (i17 & 1023) | 56320;
                                i5 = i13;
/* 2095 */                      i6 = i10 + 1;
                                cArrExpandCurrentSegment = cArrEmptyAndGetCurrentSegment;
                                i4 = i18;
                            } else {
                                cArrExpandCurrentSegment = cArrEmptyAndGetCurrentSegment;
                                i4 = i12;
                                i5 = i13;
                                i6 = i10;
                            }
                        }
/* 2099 */              if (i6 >= cArrExpandCurrentSegment.length) {
/* 2100 */                  cArrExpandCurrentSegment = this._textBuffer.expandCurrentSegment();
                        }
/* 2102 */              i10 = i6 + 1;
                        cArrExpandCurrentSegment[i6] = (char) i4;
                        i11 = i5;
                        cArrEmptyAndGetCurrentSegment = cArrExpandCurrentSegment;
                    }
/* 2106 */          String str = new String(cArrEmptyAndGetCurrentSegment, 0, i10);
/* 2108 */          if (i2 < 4) {
/* 2109 */              iArr[i - 1] = i3;
                    }
/* 2111 */          return this._symbols.addName(str, iArr, i);
                }

                @Override // com.fasterxml.jackson.core.base.ParserBase
/* 2123 */      protected void _finishString() throws IOException {
/* 2124 */          int i = this._inputPtr;
/* 2125 */          if (i >= this._inputEnd) {
/* 2126 */              loadMoreGuaranteed();
/* 2127 */              i = this._inputPtr;
                    }
/* 2130 */          char[] cArrEmptyAndGetCurrentSegment = this._textBuffer.emptyAndGetCurrentSegment();
/* 2131 */          int[] iArr = _icUTF8;
/* 2133 */          int iMin = Math.min(this._inputEnd, cArrEmptyAndGetCurrentSegment.length + i);
/* 2134 */          byte[] bArr = this._inputBuffer;
                    int i2 = i;
/* 2129 */          int i3 = 0;
/* 2135 */          while (i2 < iMin) {
/* 2136 */              int i4 = bArr[i2] & Constants.UNKNOWN;
/* 2137 */              if (iArr[i4] != 0) {
/* 2138 */                  if (i4 != 34) {
                                break;
                            }
/* 2139 */                  this._inputPtr = i2 + 1;
/* 2140 */                  this._textBuffer.setCurrentLength(i3);
/* 2150 */                  return;
                        }
                        cArrEmptyAndGetCurrentSegment[i3] = (char) i4;
/* 2146 */              i3++;
/* 2145 */              i2++;
                    }
/* 2148 */          this._inputPtr = i2;
/* 2149 */          _finishString2(cArrEmptyAndGetCurrentSegment, i3);
                }

/* 2157 */      private final void _finishString2(char[] cArr, int i) throws IOException {
                    int i2;
/* 2158 */          int[] iArr = _icUTF8;
/* 2159 */          byte[] bArr = this._inputBuffer;
                    while (true) {
/* 2166 */              int i3 = this._inputPtr;
/* 2167 */              if (i3 >= this._inputEnd) {
/* 2168 */                  loadMoreGuaranteed();
/* 2169 */                  i3 = this._inputPtr;
                        }
/* 2171 */              if (i >= cArr.length) {
/* 2172 */                  cArr = this._textBuffer.finishCurrentSegment();
                            i = 0;
                        }
/* 2175 */              int iMin = Math.min(this._inputEnd, (cArr.length - i) + i3);
                        while (true) {
/* 2176 */                  if (i3 < iMin) {
/* 2177 */                      int i4 = i3 + 1;
                                int i_decodeUtf8_3 = bArr[i3] & Constants.UNKNOWN;
/* 2178 */                      if (iArr[i_decodeUtf8_3] != 0) {
/* 2179 */                          this._inputPtr = i4;
/* 2187 */                          if (i_decodeUtf8_3 != 34) {
                                        switch (iArr[i_decodeUtf8_3]) {
                                            case 1:
/* 2193 */                                      i_decodeUtf8_3 = _decodeEscaped();
                                                break;
                                            case 2:
/* 2196 */                                      i_decodeUtf8_3 = _decodeUtf8_2(i_decodeUtf8_3);
                                                break;
                                            case 3:
/* 2199 */                                      if (this._inputEnd - this._inputPtr >= 2) {
/* 2200 */                                          i_decodeUtf8_3 = _decodeUtf8_3fast(i_decodeUtf8_3);
                                                } else {
/* 2202 */                                          i_decodeUtf8_3 = _decodeUtf8_3(i_decodeUtf8_3);
                                                }
                                                break;
                                            case 4:
/* 2206 */                                      int i_decodeUtf8_4 = _decodeUtf8_4(i_decodeUtf8_3);
/* 2208 */                                      int i5 = i + 1;
                                                cArr[i] = (char) (55296 | (i_decodeUtf8_4 >> 10));
/* 2209 */                                      if (i5 >= cArr.length) {
/* 2210 */                                          cArr = this._textBuffer.finishCurrentSegment();
                                                    i5 = 0;
                                                }
                                                i = i5;
/* 2213 */                                      i_decodeUtf8_3 = (i_decodeUtf8_4 & 1023) | 56320;
                                                break;
                                            default:
/* 2217 */                                      if (i_decodeUtf8_3 < 32) {
/* 2219 */                                          _throwUnquotedSpace(i_decodeUtf8_3, "string value");
                                                } else {
/* 2222 */                                          _reportInvalidChar(i_decodeUtf8_3);
                                                }
                                                break;
                                        }
/* 2226 */                              if (i >= cArr.length) {
/* 2227 */                                  cArr = this._textBuffer.finishCurrentSegment();
                                            i2 = 0;
                                        } else {
                                            i2 = i;
                                        }
/* 2231 */                              i = i2 + 1;
                                        cArr[i2] = (char) i_decodeUtf8_3;
                                        break;
                                    }
/* 2233 */                          this._textBuffer.setCurrentLength(i);
/* 2234 */                          return;
                                }
                                cArr[i] = (char) i_decodeUtf8_3;
                                i3 = i4;
/* 2182 */                      i++;
                            } else {
/* 2184 */                      this._inputPtr = i3;
                                break;
                            }
                        }
                    }
                }

/* 2242 */      protected void _skipString() throws IOException {
/* 2243 */          this._tokenIncomplete = false;
/* 2246 */          int[] iArr = _icUTF8;
/* 2247 */          byte[] bArr = this._inputBuffer;
                    while (true) {
/* 2255 */              int i = this._inputPtr;
/* 2256 */              int i2 = this._inputEnd;
/* 2257 */              if (i >= i2) {
/* 2258 */                  loadMoreGuaranteed();
/* 2259 */                  i = this._inputPtr;
/* 2260 */                  i2 = this._inputEnd;
                        }
                        while (true) {
/* 2262 */                  if (i < i2) {
/* 2263 */                      int i3 = i + 1;
                                int i4 = bArr[i] & Constants.UNKNOWN;
/* 2264 */                      if (iArr[i4] != 0) {
/* 2265 */                          this._inputPtr = i3;
/* 2272 */                          if (i4 != 34) {
                                        switch (iArr[i4]) {
                                            case 1:
/* 2278 */                                      _decodeEscaped();
                                                break;
                                            case 2:
/* 2281 */                                      _skipUtf8_2(i4);
                                                break;
                                            case 3:
/* 2284 */                                      _skipUtf8_3(i4);
                                                break;
                                            case 4:
/* 2287 */                                      _skipUtf8_4(i4);
                                                break;
                                            default:
/* 2290 */                                      if (i4 < 32) {
/* 2292 */                                          _throwUnquotedSpace(i4, "string value");
                                                } else {
/* 2295 */                                          _reportInvalidChar(i4);
                                                }
                                                break;
                                        }
                                    }
/* 2299 */                          return;
                                }
                                i = i3;
                            } else {
/* 2269 */                      this._inputPtr = i;
                                break;
                            }
                        }
                    }
                }

/* 2308 */      protected JsonToken _handleUnexpectedValue(int i) throws IOException {
                    switch (i) {
                        case 39:
/* 2311 */                  if (isEnabled(JsonParser.Feature.ALLOW_SINGLE_QUOTES)) {
/* 2312 */                      return _handleApos();
                            }
                            break;
                        case 43:
/* 2330 */                  if (this._inputPtr >= this._inputEnd && !loadMore()) {
/* 2332 */                      _reportInvalidEOFInValue();
                            }
/* 2335 */                  byte[] bArr = this._inputBuffer;
                            int i2 = this._inputPtr;
                            this._inputPtr = i2 + 1;
                            return _handleInvalidNumberStart(bArr[i2] & Constants.UNKNOWN, false);
                        case 73:
/* 2323 */                  _matchToken("Infinity", 1);
/* 2324 */                  if (isEnabled(JsonParser.Feature.ALLOW_NON_NUMERIC_NUMBERS)) {
/* 2325 */                      return resetAsNaN("Infinity", Double.POSITIVE_INFINITY);
                            }
/* 2327 */                  _reportError("Non-standard token 'Infinity': enable JsonParser.Feature.ALLOW_NON_NUMERIC_NUMBERS to allow");
                            break;
                            break;
                        case 78:
/* 2316 */                  _matchToken("NaN", 1);
/* 2317 */                  if (isEnabled(JsonParser.Feature.ALLOW_NON_NUMERIC_NUMBERS)) {
/* 2318 */                      return resetAsNaN("NaN", Double.NaN);
                            }
/* 2320 */                  _reportError("Non-standard token 'NaN': enable JsonParser.Feature.ALLOW_NON_NUMERIC_NUMBERS to allow");
                            break;
                            break;
                    }
/* 2338 */          if (Character.isJavaIdentifierStart(i)) {
/* 2339 */              _reportInvalidToken("" + ((char) i), "('true', 'false' or 'null')");
                    }
/* 2342 */          _reportUnexpectedChar(i, "expected a valid value (number, String, array, object, 'true', 'false' or 'null')");
/* 2343 */          return null;
                }

/* 2351 */      protected JsonToken _handleApos() throws IOException {
                    int i_decodeUtf8_3;
                    int i;
/* 2352 */          char[] cArrEmptyAndGetCurrentSegment = this._textBuffer.emptyAndGetCurrentSegment();
/* 2355 */          int[] iArr = _icUTF8;
/* 2356 */          byte[] bArr = this._inputBuffer;
                    int i2 = 0;
                    while (true) {
/* 2363 */              if (this._inputPtr >= this._inputEnd) {
/* 2364 */                  loadMoreGuaranteed();
                        }
/* 2366 */              if (i2 >= cArrEmptyAndGetCurrentSegment.length) {
/* 2367 */                  cArrEmptyAndGetCurrentSegment = this._textBuffer.finishCurrentSegment();
                            i2 = 0;
                        }
/* 2370 */              int i3 = this._inputEnd;
/* 2372 */              int length = this._inputPtr + (cArrEmptyAndGetCurrentSegment.length - i2);
/* 2373 */              if (length >= i3) {
                            length = i3;
                        }
/* 2377 */              while (this._inputPtr < length) {
/* 2378 */                  int i4 = this._inputPtr;
                            this._inputPtr = i4 + 1;
                            int i5 = bArr[i4] & Constants.UNKNOWN;
/* 2379 */                  if (i5 != 39 && iArr[i5] == 0) {
                                cArrEmptyAndGetCurrentSegment[i2] = (char) i5;
/* 2382 */                      i2++;
                            } else {
/* 2387 */                      if (i5 != 39) {
                                    switch (iArr[i5]) {
                                        case 1:
/* 2394 */                                  i_decodeUtf8_3 = i5 == 39 ? i5 : _decodeEscaped();
                                            break;
                                        case 2:
/* 2398 */                                  i_decodeUtf8_3 = _decodeUtf8_2(i5);
                                            break;
                                        case 3:
/* 2401 */                                  if (this._inputEnd - this._inputPtr >= 2) {
/* 2402 */                                      i_decodeUtf8_3 = _decodeUtf8_3fast(i5);
                                            } else {
/* 2404 */                                      i_decodeUtf8_3 = _decodeUtf8_3(i5);
                                            }
                                            break;
                                        case 4:
/* 2408 */                                  int i_decodeUtf8_4 = _decodeUtf8_4(i5);
/* 2410 */                                  int i6 = i2 + 1;
                                            cArrEmptyAndGetCurrentSegment[i2] = (char) (55296 | (i_decodeUtf8_4 >> 10));
/* 2411 */                                  if (i6 >= cArrEmptyAndGetCurrentSegment.length) {
/* 2412 */                                      cArrEmptyAndGetCurrentSegment = this._textBuffer.finishCurrentSegment();
                                                i2 = 0;
                                            } else {
                                                i2 = i6;
                                            }
/* 2415 */                                  i_decodeUtf8_3 = 56320 | (i_decodeUtf8_4 & 1023);
                                            break;
                                        default:
/* 2419 */                                  if (i5 < 32) {
/* 2420 */                                      _throwUnquotedSpace(i5, "string value");
                                            }
/* 2423 */                                  _reportInvalidChar(i5);
                                            break;
                                    }
/* 2426 */                          if (i2 >= cArrEmptyAndGetCurrentSegment.length) {
/* 2427 */                              cArrEmptyAndGetCurrentSegment = this._textBuffer.finishCurrentSegment();
                                        i = 0;
                                    } else {
                                        i = i2;
                                    }
/* 2431 */                          i2 = i + 1;
                                    cArrEmptyAndGetCurrentSegment[i] = (char) i_decodeUtf8_3;
                                    break;
                                }
/* 2433 */                      this._textBuffer.setCurrentLength(i2);
/* 2435 */                      return JsonToken.VALUE_STRING;
                            }
                        }
                    }
                }

/* 2444 */      protected JsonToken _handleInvalidNumberStart(int i, boolean z) throws IOException {
                    String str;
/* 2445 */          int i2 = i;
                    while (i2 == 73) {
/* 2446 */              if (this._inputPtr >= this._inputEnd && !loadMore()) {
/* 2448 */                  _reportInvalidEOFInValue();
                        }
/* 2451 */              byte[] bArr = this._inputBuffer;
                        int i3 = this._inputPtr;
                        this._inputPtr = i3 + 1;
                        byte b = bArr[i3];
/* 2453 */              if (b == 78) {
/* 2454 */                  str = z ? "-INF" : "+INF";
                        } else {
/* 2455 */                  if (b != 110) {
                                i2 = b;
                                break;
                            }
/* 2456 */                  str = z ? "-Infinity" : "+Infinity";
                        }
/* 2460 */              _matchToken(str, 3);
/* 2461 */              if (isEnabled(JsonParser.Feature.ALLOW_NON_NUMERIC_NUMBERS)) {
                            return resetAsNaN(str, z ? Double.NEGATIVE_INFINITY : Double.POSITIVE_INFINITY);
                        }
/* 2464 */              _reportError("Non-standard token '" + str + "': enable JsonParser.Feature.ALLOW_NON_NUMERIC_NUMBERS to allow");
                        i2 = b;
                    }
/* 2466 */          reportUnexpectedNumberChar(i2, "expected digit (0-9) to follow minus sign, for valid numeric value");
/* 2467 */          return null;
                }

/* 2472 */      protected void _matchToken(String str, int i) throws IOException {
                    int i2;
/* 2473 */          int length = str.length();
                    do {
/* 2476 */              if ((this._inputPtr >= this._inputEnd && !loadMore()) || this._inputBuffer[this._inputPtr] != str.charAt(i)) {
/* 2478 */                  _reportInvalidToken(str.substring(0, i));
                        }
/* 2480 */              this._inputPtr++;
/* 2481 */              i++;
                    } while (i < length);
/* 2484 */          if ((this._inputPtr < this._inputEnd || loadMore()) && (i2 = this._inputBuffer[this._inputPtr] & Constants.UNKNOWN) >= 48 && i2 != 93 && i2 != 125 && Character.isJavaIdentifierPart((char) _decodeCharForError(i2))) {
/* 2494 */              _reportInvalidToken(str.substring(0, i));
                    }
                }

/* 2505 */      private final int _skipWS() throws IOException {
/* 2506 */          int[] iArr = _icWS;
                    while (true) {
/* 2507 */              if (this._inputPtr < this._inputEnd || loadMore()) {
/* 2508 */                  byte[] bArr = this._inputBuffer;
                            int i = this._inputPtr;
                            this._inputPtr = i + 1;
                            int i2 = bArr[i] & Constants.UNKNOWN;
                            switch (iArr[i2]) {
                                case 0:
/* 2535 */                          return i2;
                                case 1:
                                    break;
                                case 2:
/* 2515 */                          _skipUtf8_2(i2);
                                    break;
                                case 3:
/* 2518 */                          _skipUtf8_3(i2);
                                    break;
                                case 4:
/* 2521 */                          _skipUtf8_4(i2);
                                    break;
                                case 10:
/* 2524 */                          this._currInputRow++;
/* 2525 */                          this._currInputRowStart = this._inputPtr;
                                    break;
                                case 13:
/* 2528 */                          _skipCR();
                                    break;
                                case 35:
/* 2534 */                          if (!_skipYAMLComment()) {
/* 2535 */                              return i2;
                                    }
                                    break;
                                    break;
                                case 47:
/* 2531 */                          _skipComment();
                                    break;
                                default:
/* 2540 */                          if (i2 < 32) {
/* 2541 */                              _throwInvalidSpace(i2);
                                    }
/* 2543 */                          _reportInvalidChar(i2);
                                    break;
                            }
                        } else {
/* 2546 */                  throw _constructError("Unexpected end-of-input within/between " + this._parsingContext.getTypeDesc() + " entries");
                        }
                    }
                }

/* 2550 */      private final int _skipWSOrEnd() throws IOException {
/* 2551 */          int[] iArr = _icWS;
                    while (true) {
/* 2552 */              if (this._inputPtr < this._inputEnd || loadMore()) {
/* 2553 */                  byte[] bArr = this._inputBuffer;
                            int i = this._inputPtr;
                            this._inputPtr = i + 1;
                            int i2 = bArr[i] & Constants.UNKNOWN;
                            switch (iArr[i2]) {
                                case 0:
/* 2589 */                          return i2;
                                case 1:
                                    break;
                                case 2:
/* 2560 */                          _skipUtf8_2(i2);
                                    break;
                                case 3:
/* 2563 */                          _skipUtf8_3(i2);
                                    break;
                                case 4:
/* 2566 */                          _skipUtf8_4(i2);
                                    break;
                                case 10:
/* 2569 */                          this._currInputRow++;
/* 2570 */                          this._currInputRowStart = this._inputPtr;
                                    break;
                                case 13:
/* 2573 */                          _skipCR();
                                    break;
                                case 35:
/* 2579 */                          if (!_skipYAMLComment()) {
/* 2589 */                              return i2;
                                    }
                                    break;
                                    break;
                                case 47:
/* 2576 */                          _skipComment();
                                    break;
                                default:
/* 2584 */                          _reportInvalidChar(i2);
                                    break;
                            }
                        } else {
/* 2588 */                  _handleEOF();
/* 2589 */                  return -1;
                        }
                    }
                }

                /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
                /* JADX WARN: Code duplicated, block: B:31:0x006e  */
                /* JADX WARN: Code duplicated, block: B:67:0x0071 A[SYNTHETIC] */
                /* JADX WARN: Failed to find 'out' block for switch in B:15:0x0036. Please report as an issue. */
/* 2597 */      private final int _skipColon() throws IOException {
                    int i;
                    int i2;
/* 2598 */          if (this._inputPtr >= this._inputEnd) {
/* 2599 */              loadMoreGuaranteed();
                    }
/* 2602 */          byte[] bArr = this._inputBuffer;
                    int i3 = this._inputPtr;
                    this._inputPtr = i3 + 1;
                    byte b = bArr[i3];
/* 2603 */          if (b == 58) {
/* 2604 */              if (this._inputPtr < this._inputEnd && (i2 = this._inputBuffer[this._inputPtr] & Constants.UNKNOWN) > 32 && i2 != 47) {
/* 2607 */                  this._inputPtr++;
                        }
/* 2652 */              return i2;
                    }
                    while (true) {
                        switch (i) {
                            case 9:
                            case 32:
/* 2637 */                      if (this._inputPtr >= this._inputEnd) {
/* 2638 */                          loadMoreGuaranteed();
                                }
/* 2640 */                      byte[] bArr2 = this._inputBuffer;
                                int i4 = this._inputPtr;
                                this._inputPtr = i4 + 1;
                                b = bArr2[i4];
                                break;
                            case 10:
/* 2625 */                      this._currInputRow++;
/* 2626 */                      this._currInputRowStart = this._inputPtr;
/* 2637 */                      if (this._inputPtr >= this._inputEnd) {
/* 2638 */                          loadMoreGuaranteed();
                                }
/* 2640 */                      byte[] bArr3 = this._inputBuffer;
                                int i5 = this._inputPtr;
                                this._inputPtr = i5 + 1;
                                b = bArr3[i5];
                                break;
                            case 13:
/* 2622 */                      _skipCR();
/* 2637 */                      if (this._inputPtr >= this._inputEnd) {
/* 2638 */                          loadMoreGuaranteed();
                                }
/* 2640 */                      byte[] bArr4 = this._inputBuffer;
                                int i6 = this._inputPtr;
                                this._inputPtr = i6 + 1;
                                b = bArr4[i6];
                                break;
                            case 47:
/* 2629 */                      _skipComment();
/* 2637 */                      if (this._inputPtr >= this._inputEnd) {
/* 2638 */                          loadMoreGuaranteed();
                                }
/* 2640 */                      byte[] bArr5 = this._inputBuffer;
                                int i7 = this._inputPtr;
                                this._inputPtr = i7 + 1;
                                b = bArr5[i7];
                                break;
                        }
/* 2632 */              if (i < 32) {
/* 2633 */                  _throwInvalidSpace(i);
                        }
/* 2642 */              if (i != 58) {
/* 2643 */                  _reportUnexpectedChar(i, "was expecting a colon to separate field name and value");
                        }
                    }
                    while (true) {
/* 2648 */              if (this._inputPtr < this._inputEnd || loadMore()) {
/* 2649 */                  byte[] bArr6 = this._inputBuffer;
                            int i8 = this._inputPtr;
                            this._inputPtr = i8 + 1;
                            i2 = bArr6[i8] & Constants.UNKNOWN;
/* 2650 */                  if (i2 > 32) {
/* 2651 */                      if (i2 != 47) {
/* 2652 */                          return i2;
                                }
/* 2654 */                      _skipComment();
                            } else if (i2 != 32) {
/* 2656 */                      if (i2 == 10) {
/* 2657 */                          this._currInputRow++;
/* 2658 */                          this._currInputRowStart = this._inputPtr;
                                } else if (i2 == 13) {
/* 2660 */                          _skipCR();
                                } else if (i2 != 9) {
/* 2662 */                          _throwInvalidSpace(i2);
                                }
                            }
                        } else {
/* 2666 */                  throw _constructError("Unexpected end-of-input within/between " + this._parsingContext.getTypeDesc() + " entries");
                        }
                    }
                }

/* 2670 */      private final void _skipComment() throws IOException {
/* 2671 */          if (!isEnabled(JsonParser.Feature.ALLOW_COMMENTS)) {
/* 2672 */              _reportUnexpectedChar(47, "maybe a (non-standard) comment? (not recognized as one since Feature 'ALLOW_COMMENTS' not enabled for parser)");
                    }
/* 2675 */          if (this._inputPtr >= this._inputEnd && !loadMore()) {
/* 2676 */              _reportInvalidEOF(" in a comment");
                    }
/* 2678 */          byte[] bArr = this._inputBuffer;
                    int i = this._inputPtr;
                    this._inputPtr = i + 1;
                    int i2 = bArr[i] & Constants.UNKNOWN;
/* 2679 */          if (i2 == 47) {
/* 2680 */              _skipLine();
                    } else if (i2 == 42) {
/* 2682 */              _skipCComment();
                    } else {
/* 2684 */              _reportUnexpectedChar(i2, "was expecting either '*' or '/' for a comment");
                    }
                }

/* 2690 */      private final void _skipCComment() throws IOException {
/* 2691 */          int[] inputCodeComment = CharTypes.getInputCodeComment();
                    while (true) {
/* 2695 */              if (this._inputPtr < this._inputEnd || loadMore()) {
/* 2696 */                  byte[] bArr = this._inputBuffer;
                            int i = this._inputPtr;
                            this._inputPtr = i + 1;
                            int i2 = bArr[i] & Constants.UNKNOWN;
/* 2697 */                  int i3 = inputCodeComment[i2];
/* 2698 */                  if (i3 != 0) {
                                switch (i3) {
                                    case 2:
/* 2717 */                              _skipUtf8_2(i2);
                                        break;
                                    case 3:
/* 2720 */                              _skipUtf8_3(i2);
                                        break;
                                    case 4:
/* 2723 */                              _skipUtf8_4(i2);
                                        break;
                                    case 10:
/* 2710 */                              this._currInputRow++;
/* 2711 */                              this._currInputRowStart = this._inputPtr;
                                        break;
                                    case 13:
/* 2714 */                              _skipCR();
                                        break;
                                    case 42:
/* 2701 */                              if (this._inputPtr < this._inputEnd || loadMore()) {
/* 2704 */                                  if (this._inputBuffer[this._inputPtr] == 47) {
/* 2705 */                                      this._inputPtr++;
/* 2732 */                                      return;
                                            }
                                        }
                                        break;
                                    default:
/* 2727 */                              _reportInvalidChar(i2);
                                        break;
                                }
                            }
                        }
                    }
/* 2731 */          _reportInvalidEOF(" in a comment");
                }

/* 2735 */      private final boolean _skipYAMLComment() throws IOException {
/* 2736 */          if (!isEnabled(JsonParser.Feature.ALLOW_YAML_COMMENTS)) {
/* 2737 */              return false;
                    }
/* 2739 */          _skipLine();
/* 2740 */          return true;
                }

/* 2749 */      private final void _skipLine() throws IOException {
/* 2750 */          int[] inputCodeComment = CharTypes.getInputCodeComment();
                    while (true) {
/* 2751 */              if (this._inputPtr < this._inputEnd || loadMore()) {
/* 2752 */                  byte[] bArr = this._inputBuffer;
                            int i = this._inputPtr;
                            this._inputPtr = i + 1;
                            int i2 = bArr[i] & Constants.UNKNOWN;
/* 2753 */                  int i3 = inputCodeComment[i2];
/* 2754 */                  if (i3 != 0) {
                                switch (i3) {
                                    case 2:
/* 2766 */                              _skipUtf8_2(i2);
                                        break;
                                    case 3:
/* 2769 */                              _skipUtf8_3(i2);
                                        break;
                                    case 4:
/* 2772 */                              _skipUtf8_4(i2);
                                        break;
                                    case 10:
/* 2757 */                              this._currInputRow++;
/* 2758 */                              this._currInputRowStart = this._inputPtr;
/* 2782 */                              return;
                                    case 13:
/* 2761 */                              _skipCR();
/* 2782 */                              return;
                                    case 42:
                                        break;
                                    default:
/* 2775 */                              if (i3 < 0) {
/* 2777 */                                  _reportInvalidChar(i2);
                                        }
                                        break;
                                }
                            }
                        } else {
/* 2782 */                  return;
                        }
                    }
                }

                @Override // com.fasterxml.jackson.core.base.ParserBase
/* 2786 */      protected char _decodeEscaped() throws IOException {
/* 2787 */          if (this._inputPtr >= this._inputEnd && !loadMore()) {
/* 2789 */              _reportInvalidEOF(" in character escape sequence");
                    }
/* 2792 */          byte[] bArr = this._inputBuffer;
                    int i = this._inputPtr;
                    this._inputPtr = i + 1;
                    byte b = bArr[i];
                    switch (b) {
                        case 34:
                        case 47:
                        case 92:
/* 2811 */                  return (char) b;
                        case 98:
/* 2797 */                  return '\b';
                        case 102:
/* 2803 */                  return '\f';
                        case 110:
/* 2801 */                  return '\n';
                        case 114:
/* 2805 */                  return '\r';
                        case 116:
/* 2799 */                  return '\t';
                        case 117:
                            int i2 = 0;
/* 2822 */                  for (int i3 = 0; i3 < 4; i3++) {
/* 2823 */                      if (this._inputPtr >= this._inputEnd && !loadMore()) {
/* 2825 */                          _reportInvalidEOF(" in character escape sequence");
                                }
/* 2828 */                      byte[] bArr2 = this._inputBuffer;
                                int i4 = this._inputPtr;
                                this._inputPtr = i4 + 1;
                                byte b2 = bArr2[i4];
/* 2829 */                      int iCharToHex = CharTypes.charToHex(b2);
/* 2830 */                      if (iCharToHex < 0) {
/* 2831 */                          _reportUnexpectedChar(b2, "expected a hex-digit for character escape sequence");
                                }
/* 2833 */                      i2 = (i2 << 4) | iCharToHex;
                            }
/* 2835 */                  return (char) i2;
                        default:
/* 2817 */                  return _handleUnrecognizedCharacterEscape((char) _decodeCharForError(b));
                    }
                }

/* 2840 */      protected int _decodeCharForError(int i) throws IOException {
                    char c;
/* 2841 */          if (i < 0) {
/* 2845 */              if ((i & 224) == 192) {
/* 2846 */                  i &= 31;
                            c = 1;
                        } else if ((i & 240) == 224) {
/* 2849 */                  i &= 15;
                            c = 2;
                        } else if ((i & 248) == 240) {
/* 2853 */                  i &= 7;
/* 2854 */                  c = 3;
                        } else {
/* 2856 */                  _reportInvalidInitial(i & 255);
                            c = 1;
                        }
/* 2860 */              int iNextByte = nextByte();
/* 2861 */              if ((iNextByte & 192) != 128) {
/* 2862 */                  _reportInvalidOther(iNextByte & 255);
                        }
/* 2864 */              int i2 = (i << 6) | (iNextByte & 63);
/* 2866 */              if (c > 1) {
/* 2867 */                  int iNextByte2 = nextByte();
/* 2868 */                  if ((iNextByte2 & 192) != 128) {
/* 2869 */                      _reportInvalidOther(iNextByte2 & 255);
                            }
/* 2871 */                  int i3 = (i2 << 6) | (iNextByte2 & 63);
/* 2872 */                  if (c > 2) {
/* 2873 */                      int iNextByte3 = nextByte();
/* 2874 */                      if ((iNextByte3 & 192) != 128) {
/* 2875 */                          _reportInvalidOther(iNextByte3 & 255);
                                }
/* 2877 */                      return (i3 << 6) | (iNextByte3 & 63);
                            }
/* 2881 */                  return i3;
                        }
/* 2881 */              return i2;
                    }
/* 2881 */          return i;
                }

/* 2891 */      private final int _decodeUtf8_2(int i) throws IOException {
/* 2892 */          if (this._inputPtr >= this._inputEnd) {
/* 2893 */              loadMoreGuaranteed();
                    }
/* 2895 */          byte[] bArr = this._inputBuffer;
                    int i2 = this._inputPtr;
                    this._inputPtr = i2 + 1;
                    byte b = bArr[i2];
/* 2896 */          if ((b & 192) != 128) {
/* 2897 */              _reportInvalidOther(b & Constants.UNKNOWN, this._inputPtr);
                    }
/* 2899 */          return (b & 63) | ((i & 31) << 6);
                }

/* 2903 */      private final int _decodeUtf8_3(int i) throws IOException {
/* 2904 */          if (this._inputPtr >= this._inputEnd) {
/* 2905 */              loadMoreGuaranteed();
                    }
/* 2907 */          int i2 = i & 15;
/* 2908 */          byte[] bArr = this._inputBuffer;
                    int i3 = this._inputPtr;
                    this._inputPtr = i3 + 1;
                    byte b = bArr[i3];
/* 2909 */          if ((b & 192) != 128) {
/* 2910 */              _reportInvalidOther(b & Constants.UNKNOWN, this._inputPtr);
                    }
/* 2912 */          int i4 = (i2 << 6) | (b & 63);
/* 2913 */          if (this._inputPtr >= this._inputEnd) {
/* 2914 */              loadMoreGuaranteed();
                    }
/* 2916 */          byte[] bArr2 = this._inputBuffer;
                    int i5 = this._inputPtr;
                    this._inputPtr = i5 + 1;
                    byte b2 = bArr2[i5];
/* 2917 */          if ((b2 & 192) != 128) {
/* 2918 */              _reportInvalidOther(b2 & Constants.UNKNOWN, this._inputPtr);
                    }
/* 2920 */          return (i4 << 6) | (b2 & 63);
                }

/* 2925 */      private final int _decodeUtf8_3fast(int i) throws IOException {
/* 2926 */          int i2 = i & 15;
/* 2927 */          byte[] bArr = this._inputBuffer;
                    int i3 = this._inputPtr;
                    this._inputPtr = i3 + 1;
                    byte b = bArr[i3];
/* 2928 */          if ((b & 192) != 128) {
/* 2929 */              _reportInvalidOther(b & Constants.UNKNOWN, this._inputPtr);
                    }
/* 2931 */          int i4 = (i2 << 6) | (b & 63);
/* 2932 */          byte[] bArr2 = this._inputBuffer;
                    int i5 = this._inputPtr;
                    this._inputPtr = i5 + 1;
                    byte b2 = bArr2[i5];
/* 2933 */          if ((b2 & 192) != 128) {
/* 2934 */              _reportInvalidOther(b2 & Constants.UNKNOWN, this._inputPtr);
                    }
/* 2936 */          return (i4 << 6) | (b2 & 63);
                }

/* 2945 */      private final int _decodeUtf8_4(int i) throws IOException {
/* 2946 */          if (this._inputPtr >= this._inputEnd) {
/* 2947 */              loadMoreGuaranteed();
                    }
/* 2949 */          byte[] bArr = this._inputBuffer;
                    int i2 = this._inputPtr;
                    this._inputPtr = i2 + 1;
                    byte b = bArr[i2];
/* 2950 */          if ((b & 192) != 128) {
/* 2951 */              _reportInvalidOther(b & Constants.UNKNOWN, this._inputPtr);
                    }
/* 2953 */          int i3 = (b & 63) | ((i & 7) << 6);
/* 2955 */          if (this._inputPtr >= this._inputEnd) {
/* 2956 */              loadMoreGuaranteed();
                    }
/* 2958 */          byte[] bArr2 = this._inputBuffer;
                    int i4 = this._inputPtr;
                    this._inputPtr = i4 + 1;
                    byte b2 = bArr2[i4];
/* 2959 */          if ((b2 & 192) != 128) {
/* 2960 */              _reportInvalidOther(b2 & Constants.UNKNOWN, this._inputPtr);
                    }
/* 2962 */          int i5 = (i3 << 6) | (b2 & 63);
/* 2963 */          if (this._inputPtr >= this._inputEnd) {
/* 2964 */              loadMoreGuaranteed();
                    }
/* 2966 */          byte[] bArr3 = this._inputBuffer;
                    int i6 = this._inputPtr;
                    this._inputPtr = i6 + 1;
                    byte b3 = bArr3[i6];
/* 2967 */          if ((b3 & 192) != 128) {
/* 2968 */              _reportInvalidOther(b3 & Constants.UNKNOWN, this._inputPtr);
                    }
/* 2974 */          return ((i5 << 6) | (b3 & 63)) - 65536;
                }

/* 2978 */      private final void _skipUtf8_2(int i) throws IOException {
/* 2979 */          if (this._inputPtr >= this._inputEnd) {
/* 2980 */              loadMoreGuaranteed();
                    }
/* 2982 */          byte[] bArr = this._inputBuffer;
                    int i2 = this._inputPtr;
                    this._inputPtr = i2 + 1;
                    byte b = bArr[i2];
/* 2983 */          if ((b & 192) != 128) {
/* 2984 */              _reportInvalidOther(b & Constants.UNKNOWN, this._inputPtr);
                    }
                }

/* 2992 */      private final void _skipUtf8_3(int i) throws IOException {
/* 2993 */          if (this._inputPtr >= this._inputEnd) {
/* 2994 */              loadMoreGuaranteed();
                    }
/* 2997 */          byte[] bArr = this._inputBuffer;
                    int i2 = this._inputPtr;
                    this._inputPtr = i2 + 1;
                    byte b = bArr[i2];
/* 2998 */          if ((b & 192) != 128) {
/* 2999 */              _reportInvalidOther(b & Constants.UNKNOWN, this._inputPtr);
                    }
/* 3001 */          if (this._inputPtr >= this._inputEnd) {
/* 3002 */              loadMoreGuaranteed();
                    }
/* 3004 */          byte[] bArr2 = this._inputBuffer;
                    int i3 = this._inputPtr;
                    this._inputPtr = i3 + 1;
                    byte b2 = bArr2[i3];
/* 3005 */          if ((b2 & 192) != 128) {
/* 3006 */              _reportInvalidOther(b2 & Constants.UNKNOWN, this._inputPtr);
                    }
                }

/* 3011 */      private final void _skipUtf8_4(int i) throws IOException {
/* 3012 */          if (this._inputPtr >= this._inputEnd) {
/* 3013 */              loadMoreGuaranteed();
                    }
/* 3015 */          byte[] bArr = this._inputBuffer;
                    int i2 = this._inputPtr;
                    this._inputPtr = i2 + 1;
                    byte b = bArr[i2];
/* 3016 */          if ((b & 192) != 128) {
/* 3017 */              _reportInvalidOther(b & Constants.UNKNOWN, this._inputPtr);
                    }
/* 3019 */          if (this._inputPtr >= this._inputEnd) {
/* 3020 */              loadMoreGuaranteed();
                    }
/* 3022 */          byte[] bArr2 = this._inputBuffer;
                    int i3 = this._inputPtr;
                    this._inputPtr = i3 + 1;
                    byte b2 = bArr2[i3];
/* 3023 */          if ((b2 & 192) != 128) {
/* 3024 */              _reportInvalidOther(b2 & Constants.UNKNOWN, this._inputPtr);
                    }
/* 3026 */          if (this._inputPtr >= this._inputEnd) {
/* 3027 */              loadMoreGuaranteed();
                    }
/* 3029 */          byte[] bArr3 = this._inputBuffer;
                    int i4 = this._inputPtr;
                    this._inputPtr = i4 + 1;
                    byte b3 = bArr3[i4];
/* 3030 */          if ((b3 & 192) != 128) {
/* 3031 */              _reportInvalidOther(b3 & Constants.UNKNOWN, this._inputPtr);
                    }
                }

/* 3046 */      protected final void _skipCR() throws IOException {
/* 3047 */          if ((this._inputPtr < this._inputEnd || loadMore()) && this._inputBuffer[this._inputPtr] == 10) {
/* 3049 */              this._inputPtr++;
                    }
/* 3052 */          this._currInputRow++;
/* 3053 */          this._currInputRowStart = this._inputPtr;
                }

/* 3057 */      private int nextByte() throws IOException {
/* 3058 */          if (this._inputPtr >= this._inputEnd) {
/* 3059 */              loadMoreGuaranteed();
                    }
/* 3061 */          byte[] bArr = this._inputBuffer;
                    int i = this._inputPtr;
                    this._inputPtr = i + 1;
                    return bArr[i] & Constants.UNKNOWN;
                }

/* 3071 */      protected void _reportInvalidToken(String str) throws IOException {
/* 3072 */          _reportInvalidToken(str, "'null', 'true', 'false' or NaN");
                }

/* 3076 */      protected void _reportInvalidToken(String str, String str2) throws IOException {
/* 3077 */          StringBuilder sb = new StringBuilder(str);
                    while (true) {
/* 3084 */              if (this._inputPtr >= this._inputEnd && !loadMore()) {
                            break;
                        }
/* 3087 */              byte[] bArr = this._inputBuffer;
                        int i = this._inputPtr;
                        this._inputPtr = i + 1;
/* 3088 */              char c_decodeCharForError = (char) _decodeCharForError(bArr[i]);
/* 3089 */              if (!Character.isJavaIdentifierPart(c_decodeCharForError)) {
                            break;
                        } else {
/* 3092 */                  sb.append(c_decodeCharForError);
                        }
                    }
/* 3094 */          _reportError("Unrecognized token '" + sb.toString() + "': was expecting " + str2);
                }

/* 3100 */      protected void _reportInvalidChar(int i) throws JsonParseException {
/* 3101 */          if (i < 32) {
/* 3102 */              _throwInvalidSpace(i);
                    }
/* 3104 */          _reportInvalidInitial(i);
                }

/* 3109 */      protected void _reportInvalidInitial(int i) throws JsonParseException {
/* 3110 */          _reportError("Invalid UTF-8 start byte 0x" + Integer.toHexString(i));
                }

/* 3115 */      protected void _reportInvalidOther(int i) throws JsonParseException {
/* 3116 */          _reportError("Invalid UTF-8 middle byte 0x" + Integer.toHexString(i));
                }

/* 3121 */      protected void _reportInvalidOther(int i, int i2) throws JsonParseException {
/* 3122 */          this._inputPtr = i2;
/* 3123 */          _reportInvalidOther(i);
                }

/* 3127 */      public static int[] growArrayBy(int[] iArr, int i) {
                    return iArr == null ? new int[i] : ArraysCompat.copyOf(iArr, iArr.length + i);
                }

/* 3146 */      protected final byte[] _decodeBase64(Base64Variant base64Variant) throws IOException {
/* 3147 */          ByteArrayBuilder byteArrayBuilder_getByteArrayBuilder = _getByteArrayBuilder();
                    while (true) {
/* 3154 */              if (this._inputPtr >= this._inputEnd) {
/* 3155 */                  loadMoreGuaranteed();
                        }
/* 3157 */              byte[] bArr = this._inputBuffer;
                        int i = this._inputPtr;
                        this._inputPtr = i + 1;
                        int i2 = bArr[i] & Constants.UNKNOWN;
/* 3158 */              if (i2 > 32) {
/* 3159 */                  int iDecodeBase64Char = base64Variant.decodeBase64Char(i2);
/* 3160 */                  if (iDecodeBase64Char < 0) {
/* 3161 */                      if (i2 == 34) {
/* 3162 */                          return byteArrayBuilder_getByteArrayBuilder.toByteArray();
                                }
/* 3164 */                      iDecodeBase64Char = _decodeBase64Escape(base64Variant, i2, 0);
/* 3165 */                      if (iDecodeBase64Char < 0) {
                                    continue;
                                }
                            }
/* 3173 */                  if (this._inputPtr >= this._inputEnd) {
/* 3174 */                      loadMoreGuaranteed();
                            }
/* 3176 */                  byte[] bArr2 = this._inputBuffer;
                            int i3 = this._inputPtr;
                            this._inputPtr = i3 + 1;
                            int i4 = bArr2[i3] & Constants.UNKNOWN;
/* 3177 */                  int iDecodeBase64Char2 = base64Variant.decodeBase64Char(i4);
/* 3178 */                  if (iDecodeBase64Char2 < 0) {
/* 3179 */                      iDecodeBase64Char2 = _decodeBase64Escape(base64Variant, i4, 1);
                            }
/* 3181 */                  int i5 = iDecodeBase64Char2 | (iDecodeBase64Char << 6);
/* 3184 */                  if (this._inputPtr >= this._inputEnd) {
/* 3185 */                      loadMoreGuaranteed();
                            }
/* 3187 */                  byte[] bArr3 = this._inputBuffer;
                            int i6 = this._inputPtr;
                            this._inputPtr = i6 + 1;
                            int i7 = bArr3[i6] & Constants.UNKNOWN;
/* 3188 */                  int iDecodeBase64Char3 = base64Variant.decodeBase64Char(i7);
/* 3191 */                  if (iDecodeBase64Char3 < 0) {
/* 3192 */                      if (iDecodeBase64Char3 != -2) {
/* 3194 */                          if (i7 == 34 && !base64Variant.usesPadding()) {
/* 3196 */                              byteArrayBuilder_getByteArrayBuilder.append(i5 >> 4);
/* 3197 */                              return byteArrayBuilder_getByteArrayBuilder.toByteArray();
                                    }
/* 3199 */                          iDecodeBase64Char3 = _decodeBase64Escape(base64Variant, i7, 2);
                                }
/* 3201 */                      if (iDecodeBase64Char3 == -2) {
/* 3203 */                          if (this._inputPtr >= this._inputEnd) {
/* 3204 */                              loadMoreGuaranteed();
                                    }
/* 3206 */                          byte[] bArr4 = this._inputBuffer;
                                    int i8 = this._inputPtr;
                                    this._inputPtr = i8 + 1;
                                    int i9 = bArr4[i8] & Constants.UNKNOWN;
/* 3207 */                          if (!base64Variant.usesPaddingChar(i9)) {
/* 3208 */                              throw reportInvalidBase64Char(base64Variant, i9, 3, "expected padding character '" + base64Variant.getPaddingChar() + "'");
                                    }
/* 3212 */                          byteArrayBuilder_getByteArrayBuilder.append(i5 >> 4);
                                }
                            }
/* 3217 */                  int i10 = (i5 << 6) | iDecodeBase64Char3;
/* 3219 */                  if (this._inputPtr >= this._inputEnd) {
/* 3220 */                      loadMoreGuaranteed();
                            }
/* 3222 */                  byte[] bArr5 = this._inputBuffer;
                            int i11 = this._inputPtr;
                            this._inputPtr = i11 + 1;
                            int i12 = bArr5[i11] & Constants.UNKNOWN;
/* 3223 */                  int iDecodeBase64Char4 = base64Variant.decodeBase64Char(i12);
/* 3224 */                  if (iDecodeBase64Char4 < 0) {
/* 3225 */                      if (iDecodeBase64Char4 != -2) {
/* 3227 */                          if (i12 == 34 && !base64Variant.usesPadding()) {
/* 3229 */                              byteArrayBuilder_getByteArrayBuilder.appendTwoBytes(i10 >> 2);
/* 3230 */                              return byteArrayBuilder_getByteArrayBuilder.toByteArray();
                                    }
/* 3232 */                          iDecodeBase64Char4 = _decodeBase64Escape(base64Variant, i12, 3);
                                }
/* 3234 */                      if (iDecodeBase64Char4 == -2) {
/* 3242 */                          byteArrayBuilder_getByteArrayBuilder.appendTwoBytes(i10 >> 2);
                                }
                            }
/* 3248 */                  byteArrayBuilder_getByteArrayBuilder.appendThreeBytes(iDecodeBase64Char4 | (i10 << 6));
                        }
                    }
                }
            }
