            package com.fasterxml.jackson.core.json;

            import com.fasterxml.jackson.core.Base64Variant;
            import com.fasterxml.jackson.core.JsonParser;
            import com.fasterxml.jackson.core.JsonToken;
            import com.fasterxml.jackson.core.ObjectCodec;
            import com.fasterxml.jackson.core.base.ParserBase;
            import com.fasterxml.jackson.core.p003io.CharTypes;
            import com.fasterxml.jackson.core.p003io.IOContext;
            import com.fasterxml.jackson.core.sym.CharsToNameCanonicalizer;
            import com.fasterxml.jackson.core.util.ByteArrayBuilder;
            import com.fasterxml.jackson.core.util.TextBuffer;
            import java.io.IOException;
            import java.io.OutputStream;
            import java.io.Reader;
            import java.io.Writer;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 80 */    public final class ReaderBasedJsonParser extends ParserBase {
                protected static final int[] _icLatin1 = CharTypes.getInputCodeLatin1();
                private static final int[] _icWS = CharTypes.getInputCodeWS();
                protected final int _hashSeed;
                protected char[] _inputBuffer;
                protected ObjectCodec _objectCodec;
                protected Reader _reader;
                protected final CharsToNameCanonicalizer _symbols;
                protected boolean _tokenIncomplete;

/* 81 */        public ReaderBasedJsonParser(IOContext iOContext, int i, Reader reader, ObjectCodec objectCodec, CharsToNameCanonicalizer charsToNameCanonicalizer) {
/* 82 */            super(iOContext, i);
/* 71 */            this._tokenIncomplete = false;
/* 83 */            this._reader = reader;
/* 84 */            this._inputBuffer = iOContext.allocTokenBuffer();
/* 85 */            this._objectCodec = objectCodec;
/* 86 */            this._symbols = charsToNameCanonicalizer;
/* 87 */            this._hashSeed = charsToNameCanonicalizer.hashSeed();
                }

                @Override // com.fasterxml.jackson.core.JsonParser
/* 97 */        public ObjectCodec getCodec() {
/* 98 */            return this._objectCodec;
                }

                @Override // com.fasterxml.jackson.core.JsonParser
/* 102 */       public void setCodec(ObjectCodec objectCodec) {
/* 103 */           this._objectCodec = objectCodec;
                }

                @Override // com.fasterxml.jackson.core.JsonParser
/* 108 */       public int releaseBuffered(Writer writer) throws IOException {
/* 109 */           int i = this._inputEnd - this._inputPtr;
/* 110 */           if (i < 1) {
/* 111 */               return 0;
                    }
/* 115 */           writer.write(this._inputBuffer, this._inputPtr, i);
/* 116 */           return i;
                }

                @Override // com.fasterxml.jackson.core.JsonParser
/* 120 */       public Object getInputSource() {
/* 121 */           return this._reader;
                }

                @Override // com.fasterxml.jackson.core.base.ParserBase
/* 126 */       protected boolean loadMore() throws IOException {
/* 127 */           this._currInputProcessed += (long) this._inputEnd;
/* 128 */           this._currInputRowStart -= this._inputEnd;
/* 130 */           if (this._reader == null) {
                        return false;
                    }
/* 131 */           int i = this._reader.read(this._inputBuffer, 0, this._inputBuffer.length);
/* 132 */           if (i > 0) {
/* 133 */               this._inputPtr = 0;
/* 134 */               this._inputEnd = i;
/* 135 */               return true;
                    }
/* 138 */           _closeInput();
/* 140 */           if (i == 0) {
/* 141 */               throw new IOException("Reader returned 0 characters when trying to read " + this._inputEnd);
                    }
                    return false;
                }

/* 149 */       protected char getNextChar(String str) throws IOException {
/* 150 */           if (this._inputPtr >= this._inputEnd && !loadMore()) {
/* 152 */               _reportInvalidEOF(str);
                    }
/* 155 */           char[] cArr = this._inputBuffer;
                    int i = this._inputPtr;
                    this._inputPtr = i + 1;
                    return cArr[i];
                }

                @Override // com.fasterxml.jackson.core.base.ParserBase
/* 167 */       protected void _closeInput() throws IOException {
/* 168 */           if (this._reader != null) {
/* 169 */               if (this._ioContext.isResourceManaged() || isEnabled(JsonParser.Feature.AUTO_CLOSE_SOURCE)) {
/* 170 */                   this._reader.close();
                        }
/* 172 */               this._reader = null;
                    }
                }

                @Override // com.fasterxml.jackson.core.base.ParserBase
/* 185 */       protected void _releaseBuffers() throws IOException {
/* 186 */           super._releaseBuffers();
/* 188 */           this._symbols.release();
/* 189 */           char[] cArr = this._inputBuffer;
/* 190 */           if (cArr != null) {
/* 191 */               this._inputBuffer = null;
/* 192 */               this._ioContext.releaseTokenBuffer(cArr);
                    }
                }

                @Override // com.fasterxml.jackson.core.base.ParserMinimalBase, com.fasterxml.jackson.core.JsonParser
/* 211 */       public String getText() throws IOException {
/* 212 */           JsonToken jsonToken = this._currToken;
/* 213 */           if (jsonToken != JsonToken.VALUE_STRING) {
                        return _getText2(jsonToken);
                    }
/* 214 */           if (this._tokenIncomplete) {
/* 215 */               this._tokenIncomplete = false;
/* 216 */               _finishString();
                    }
/* 218 */           return this._textBuffer.contentsAsString();
                }

                @Override // com.fasterxml.jackson.core.JsonParser
/* 228 */       public String getValueAsString() throws IOException {
/* 229 */           if (this._currToken != JsonToken.VALUE_STRING) {
                        return super.getValueAsString(null);
                    }
/* 230 */           if (this._tokenIncomplete) {
/* 231 */               this._tokenIncomplete = false;
/* 232 */               _finishString();
                    }
/* 234 */           return this._textBuffer.contentsAsString();
                }

                @Override // com.fasterxml.jackson.core.base.ParserMinimalBase, com.fasterxml.jackson.core.JsonParser
/* 242 */       public String getValueAsString(String str) throws IOException {
/* 243 */           if (this._currToken != JsonToken.VALUE_STRING) {
                        return super.getValueAsString(str);
                    }
/* 244 */           if (this._tokenIncomplete) {
/* 245 */               this._tokenIncomplete = false;
/* 246 */               _finishString();
                    }
/* 248 */           return this._textBuffer.contentsAsString();
                }

/* 254 */       protected String _getText2(JsonToken jsonToken) {
/* 255 */           if (jsonToken == null) {
/* 256 */               return null;
                    }
                    switch (jsonToken.m524id()) {
                        case 5:
/* 260 */                   return this._parsingContext.getCurrentName();
                        case 6:
                        case 7:
                        case 8:
/* 266 */                   return this._textBuffer.contentsAsString();
                        default:
/* 268 */                   return jsonToken.asString();
                    }
                }

                @Override // com.fasterxml.jackson.core.base.ParserMinimalBase, com.fasterxml.jackson.core.JsonParser
/* 275 */       public char[] getTextCharacters() throws IOException {
/* 276 */           if (this._currToken != null) {
                        switch (this._currToken.m524id()) {
                            case 5:
/* 279 */                       if (!this._nameCopied) {
/* 280 */                           String currentName = this._parsingContext.getCurrentName();
/* 281 */                           int length = currentName.length();
/* 282 */                           if (this._nameCopyBuffer == null) {
/* 283 */                               this._nameCopyBuffer = this._ioContext.allocNameCopyBuffer(length);
                                    } else if (this._nameCopyBuffer.length < length) {
/* 285 */                               this._nameCopyBuffer = new char[length];
                                    }
/* 287 */                           currentName.getChars(0, length, this._nameCopyBuffer, 0);
/* 288 */                           this._nameCopied = true;
                                }
/* 290 */                       return this._nameCopyBuffer;
                            case 6:
/* 293 */                       if (this._tokenIncomplete) {
/* 294 */                           this._tokenIncomplete = false;
/* 295 */                           _finishString();
                                }
                                break;
                            case 7:
                            case 8:
                                break;
                            default:
/* 303 */                       return this._currToken.asCharArray();
                        }
/* 300 */               return this._textBuffer.getTextBuffer();
                    }
/* 306 */           return null;
                }

                @Override // com.fasterxml.jackson.core.base.ParserMinimalBase, com.fasterxml.jackson.core.JsonParser
/* 312 */       public int getTextLength() throws IOException {
/* 313 */           if (this._currToken == null) {
                        return 0;
                    }
                    switch (this._currToken.m524id()) {
                        case 5:
/* 317 */                   return this._parsingContext.getCurrentName().length();
                        case 6:
/* 319 */                   if (this._tokenIncomplete) {
/* 320 */                       this._tokenIncomplete = false;
/* 321 */                       _finishString();
                            }
                            break;
                        case 7:
                        case 8:
                            break;
                        default:
/* 329 */                   return this._currToken.asCharArray().length;
                    }
/* 326 */           return this._textBuffer.size();
                }

                @Override // com.fasterxml.jackson.core.base.ParserMinimalBase, com.fasterxml.jackson.core.JsonParser
/* 338 */       public int getTextOffset() throws IOException {
/* 339 */           if (this._currToken == null) {
                        return 0;
                    }
                    switch (this._currToken.m524id()) {
                        case 5:
                        default:
                            return 0;
                        case 6:
/* 344 */                   if (this._tokenIncomplete) {
/* 345 */                       this._tokenIncomplete = false;
/* 346 */                       _finishString();
                            }
                            break;
                        case 7:
                        case 8:
                            break;
                    }
/* 351 */           return this._textBuffer.getTextOffset();
                }

                @Override // com.fasterxml.jackson.core.base.ParserMinimalBase, com.fasterxml.jackson.core.JsonParser
/* 361 */       public byte[] getBinaryValue(Base64Variant base64Variant) throws IOException {
/* 362 */           if (this._currToken != JsonToken.VALUE_STRING && (this._currToken != JsonToken.VALUE_EMBEDDED_OBJECT || this._binaryValue == null)) {
/* 364 */               _reportError("Current token (" + this._currToken + ") not VALUE_STRING or VALUE_EMBEDDED_OBJECT, can not access as binary");
                    }
/* 369 */           if (this._tokenIncomplete) {
                        try {
/* 371 */                   this._binaryValue = _decodeBase64(base64Variant);
/* 378 */                   this._tokenIncomplete = false;
                        } catch (IllegalArgumentException e) {
/* 373 */                   throw _constructError("Failed to decode VALUE_STRING as base64 (" + base64Variant + "): " + e.getMessage());
                        }
                    } else if (this._binaryValue == null) {
/* 382 */               ByteArrayBuilder byteArrayBuilder_getByteArrayBuilder = _getByteArrayBuilder();
/* 383 */               _decodeBase64(getText(), byteArrayBuilder_getByteArrayBuilder, base64Variant);
/* 384 */               this._binaryValue = byteArrayBuilder_getByteArrayBuilder.toByteArray();
                    }
/* 387 */           return this._binaryValue;
                }

                @Override // com.fasterxml.jackson.core.JsonParser
/* 394 */       public int readBinaryValue(Base64Variant base64Variant, OutputStream outputStream) throws IOException {
/* 395 */           if (!this._tokenIncomplete || this._currToken != JsonToken.VALUE_STRING) {
/* 396 */               byte[] binaryValue = getBinaryValue(base64Variant);
/* 397 */               outputStream.write(binaryValue);
/* 398 */               return binaryValue.length;
                    }
/* 401 */           byte[] bArrAllocBase64Buffer = this._ioContext.allocBase64Buffer();
                    try {
/* 403 */               int i_readBinary = _readBinary(base64Variant, outputStream, bArrAllocBase64Buffer);
/* 405 */               this._ioContext.releaseBase64Buffer(bArrAllocBase64Buffer);
/* 405 */               return i_readBinary;
                    } catch (Throwable th) {
                        this._ioContext.releaseBase64Buffer(bArrAllocBase64Buffer);
                        throw th;
                    }
                }

/* 412 */       protected int _readBinary(Base64Variant base64Variant, OutputStream outputStream, byte[] bArr) throws IOException {
                    int i;
/* 413 */           int length = bArr.length - 3;
                    int i2 = 0;
                    int i3 = 0;
                    while (true) {
/* 420 */               if (this._inputPtr >= this._inputEnd) {
/* 421 */                   loadMoreGuaranteed();
                        }
/* 423 */               char[] cArr = this._inputBuffer;
                        int i4 = this._inputPtr;
                        this._inputPtr = i4 + 1;
                        char c = cArr[i4];
/* 424 */               if (c > ' ') {
/* 425 */                   int iDecodeBase64Char = base64Variant.decodeBase64Char(c);
/* 426 */                   if (iDecodeBase64Char < 0) {
/* 427 */                       if (c == '\"') {
                                    break;
                                }
/* 430 */                       iDecodeBase64Char = _decodeBase64Escape(base64Variant, c, 0);
/* 431 */                       if (iDecodeBase64Char < 0) {
                                    continue;
                                }
                            }
                            int i5 = iDecodeBase64Char;
/* 437 */                   if (i3 > length) {
/* 438 */                       i2 += i3;
/* 439 */                       outputStream.write(bArr, 0, i3);
                                i = 0;
                            } else {
                                i = i3;
                            }
/* 447 */                   if (this._inputPtr >= this._inputEnd) {
/* 448 */                       loadMoreGuaranteed();
                            }
/* 450 */                   char[] cArr2 = this._inputBuffer;
                            int i6 = this._inputPtr;
                            this._inputPtr = i6 + 1;
                            char c2 = cArr2[i6];
/* 451 */                   int iDecodeBase64Char2 = base64Variant.decodeBase64Char(c2);
/* 452 */                   if (iDecodeBase64Char2 < 0) {
/* 453 */                       iDecodeBase64Char2 = _decodeBase64Escape(base64Variant, c2, 1);
                            }
/* 455 */                   int i7 = (i5 << 6) | iDecodeBase64Char2;
/* 458 */                   if (this._inputPtr >= this._inputEnd) {
/* 459 */                       loadMoreGuaranteed();
                            }
/* 461 */                   char[] cArr3 = this._inputBuffer;
                            int i8 = this._inputPtr;
                            this._inputPtr = i8 + 1;
                            char c3 = cArr3[i8];
/* 462 */                   int iDecodeBase64Char3 = base64Variant.decodeBase64Char(c3);
/* 465 */                   if (iDecodeBase64Char3 < 0) {
/* 466 */                       if (iDecodeBase64Char3 != -2) {
/* 468 */                           if (c3 == '\"' && !base64Variant.usesPadding()) {
/* 470 */                               i3 = i + 1;
/* 469 */                               bArr[i] = (byte) (i7 >> 4);
                                        break;
                                    }
/* 473 */                           iDecodeBase64Char3 = _decodeBase64Escape(base64Variant, c3, 2);
                                }
/* 475 */                       if (iDecodeBase64Char3 == -2) {
/* 477 */                           if (this._inputPtr >= this._inputEnd) {
/* 478 */                               loadMoreGuaranteed();
                                    }
/* 480 */                           char[] cArr4 = this._inputBuffer;
                                    int i9 = this._inputPtr;
                                    this._inputPtr = i9 + 1;
                                    char c4 = cArr4[i9];
/* 481 */                           if (!base64Variant.usesPaddingChar(c4)) {
/* 482 */                               throw reportInvalidBase64Char(base64Variant, c4, 3, "expected padding character '" + base64Variant.getPaddingChar() + "'");
                                    }
/* 486 */                           i3 = i + 1;
/* 485 */                           bArr[i] = (byte) (i7 >> 4);
                                }
                            }
/* 491 */                   int i10 = (i7 << 6) | iDecodeBase64Char3;
/* 493 */                   if (this._inputPtr >= this._inputEnd) {
/* 494 */                       loadMoreGuaranteed();
                            }
/* 496 */                   char[] cArr5 = this._inputBuffer;
                            int i11 = this._inputPtr;
                            this._inputPtr = i11 + 1;
                            char c5 = cArr5[i11];
/* 497 */                   int iDecodeBase64Char4 = base64Variant.decodeBase64Char(c5);
/* 498 */                   if (iDecodeBase64Char4 < 0) {
/* 499 */                       if (iDecodeBase64Char4 != -2) {
/* 501 */                           if (c5 == '\"' && !base64Variant.usesPadding()) {
/* 502 */                               int i12 = i10 >> 2;
/* 503 */                               int i13 = i + 1;
                                        bArr[i] = (byte) (i12 >> 8);
/* 504 */                               i3 = i13 + 1;
                                        bArr[i13] = (byte) i12;
                                        break;
                                    }
/* 507 */                           iDecodeBase64Char4 = _decodeBase64Escape(base64Variant, c5, 3);
                                }
/* 509 */                       if (iDecodeBase64Char4 == -2) {
/* 516 */                           int i14 = i10 >> 2;
/* 517 */                           int i15 = i + 1;
                                    bArr[i] = (byte) (i14 >> 8);
/* 518 */                           i3 = i15 + 1;
                                    bArr[i15] = (byte) i14;
                                }
                            }
/* 523 */                   int i16 = (i10 << 6) | iDecodeBase64Char4;
/* 524 */                   int i17 = i + 1;
                            bArr[i] = (byte) (i16 >> 16);
/* 525 */                   int i18 = i17 + 1;
                            bArr[i17] = (byte) (i16 >> 8);
/* 526 */                   i3 = i18 + 1;
                            bArr[i18] = (byte) i16;
                        }
                    }
/* 528 */           this._tokenIncomplete = false;
/* 529 */           if (i3 > 0) {
/* 530 */               int i19 = i2 + i3;
/* 531 */               outputStream.write(bArr, 0, i3);
/* 533 */               return i19;
                    }
/* 533 */           return i2;
                }

                /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
                @Override // com.fasterxml.jackson.core.base.ParserMinimalBase, com.fasterxml.jackson.core.JsonParser
/* 549 */       public JsonToken nextToken() throws IOException {
                    JsonToken jsonToken_parseNumber;
/* 550 */           this._numTypesValid = 0;
/* 556 */           if (this._currToken == JsonToken.FIELD_NAME) {
/* 557 */               return _nextAfterName();
                    }
/* 559 */           if (this._tokenIncomplete) {
/* 560 */               _skipString();
                    }
/* 562 */           int i_skipWSOrEnd = _skipWSOrEnd();
/* 563 */           if (i_skipWSOrEnd < 0) {
/* 567 */               close();
/* 568 */               this._currToken = null;
                        return null;
                    }
/* 574 */           this._tokenInputTotal = (this._currInputProcessed + ((long) this._inputPtr)) - 1;
/* 575 */           this._tokenInputRow = this._currInputRow;
/* 576 */           this._tokenInputCol = (this._inputPtr - this._currInputRowStart) - 1;
/* 579 */           this._binaryValue = null;
/* 582 */           if (i_skipWSOrEnd == 93) {
/* 583 */               if (!this._parsingContext.inArray()) {
/* 584 */                   _reportMismatchedEndMarker(i_skipWSOrEnd, '}');
                        }
/* 586 */               this._parsingContext = this._parsingContext.getParent();
/* 587 */               JsonToken jsonToken = JsonToken.END_ARRAY;
                        this._currToken = jsonToken;
/* 688 */               return jsonToken;
                    }
/* 589 */           if (i_skipWSOrEnd == 125) {
/* 590 */               if (!this._parsingContext.inObject()) {
/* 591 */                   _reportMismatchedEndMarker(i_skipWSOrEnd, ']');
                        }
/* 593 */               this._parsingContext = this._parsingContext.getParent();
/* 594 */               JsonToken jsonToken2 = JsonToken.END_OBJECT;
                        this._currToken = jsonToken2;
/* 688 */               return jsonToken2;
                    }
/* 598 */           if (this._parsingContext.expectComma()) {
/* 599 */               if (i_skipWSOrEnd != 44) {
/* 600 */                   _reportUnexpectedChar(i_skipWSOrEnd, "was expecting comma to separate " + this._parsingContext.getTypeDesc() + " entries");
                        }
/* 602 */               i_skipWSOrEnd = _skipWS();
                    }
/* 609 */           boolean zInObject = this._parsingContext.inObject();
/* 610 */           if (zInObject) {
/* 613 */               this._parsingContext.setCurrentName(_parseName(i_skipWSOrEnd));
/* 614 */               this._currToken = JsonToken.FIELD_NAME;
/* 615 */               int i_skipWS = _skipWS();
/* 616 */               if (i_skipWS != 58) {
/* 617 */                   _reportUnexpectedChar(i_skipWS, "was expecting a colon to separate field name and value");
                        }
/* 619 */               i_skipWSOrEnd = _skipWS();
                    }
                    switch (i_skipWSOrEnd) {
                        case 34:
/* 628 */                   this._tokenIncomplete = true;
/* 629 */                   jsonToken_parseNumber = JsonToken.VALUE_STRING;
                            break;
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
/* 676 */                   jsonToken_parseNumber = _parseNumber(i_skipWSOrEnd);
                            break;
                        case 91:
/* 632 */                   if (!zInObject) {
/* 633 */                       this._parsingContext = this._parsingContext.createChildArrayContext(this._tokenInputRow, this._tokenInputCol);
                            }
/* 635 */                   jsonToken_parseNumber = JsonToken.START_ARRAY;
                            break;
                        case 93:
                        case 125:
/* 647 */                   _reportUnexpectedChar(i_skipWSOrEnd, "expected a value");
/* 649 */                   _matchToken("true", 1);
/* 650 */                   jsonToken_parseNumber = JsonToken.VALUE_TRUE;
                            break;
                        case 102:
/* 653 */                   _matchToken("false", 1);
/* 654 */                   jsonToken_parseNumber = JsonToken.VALUE_FALSE;
                            break;
                        case 110:
/* 657 */                   _matchToken("null", 1);
/* 658 */                   jsonToken_parseNumber = JsonToken.VALUE_NULL;
                            break;
                        case 116:
/* 649 */                   _matchToken("true", 1);
/* 650 */                   jsonToken_parseNumber = JsonToken.VALUE_TRUE;
                            break;
                        case 123:
/* 638 */                   if (!zInObject) {
/* 639 */                       this._parsingContext = this._parsingContext.createChildObjectContext(this._tokenInputRow, this._tokenInputCol);
                            }
/* 641 */                   jsonToken_parseNumber = JsonToken.START_OBJECT;
                            break;
                        default:
/* 679 */                   jsonToken_parseNumber = _handleOddValue(i_skipWSOrEnd);
                            break;
                    }
/* 683 */           if (zInObject) {
/* 684 */               this._nextToken = jsonToken_parseNumber;
/* 685 */               return this._currToken;
                    }
/* 687 */           this._currToken = jsonToken_parseNumber;
/* 688 */           return jsonToken_parseNumber;
                }

/* 692 */       private JsonToken _nextAfterName() {
/* 693 */           this._nameCopied = false;
/* 694 */           JsonToken jsonToken = this._nextToken;
/* 695 */           this._nextToken = null;
/* 697 */           if (jsonToken == JsonToken.START_ARRAY) {
/* 698 */               this._parsingContext = this._parsingContext.createChildArrayContext(this._tokenInputRow, this._tokenInputCol);
                    } else if (jsonToken == JsonToken.START_OBJECT) {
/* 700 */               this._parsingContext = this._parsingContext.createChildObjectContext(this._tokenInputRow, this._tokenInputCol);
                    }
/* 702 */           this._currToken = jsonToken;
                    return jsonToken;
                }

                @Override // com.fasterxml.jackson.core.JsonParser
/* 715 */       public String nextTextValue() throws IOException {
/* 716 */           if (this._currToken == JsonToken.FIELD_NAME) {
/* 717 */               this._nameCopied = false;
/* 718 */               JsonToken jsonToken = this._nextToken;
/* 719 */               this._nextToken = null;
/* 720 */               this._currToken = jsonToken;
/* 721 */               if (jsonToken == JsonToken.VALUE_STRING) {
/* 722 */                   if (this._tokenIncomplete) {
/* 723 */                       this._tokenIncomplete = false;
/* 724 */                       _finishString();
                            }
/* 726 */                   return this._textBuffer.contentsAsString();
                        }
/* 728 */               if (jsonToken == JsonToken.START_ARRAY) {
/* 729 */                   this._parsingContext = this._parsingContext.createChildArrayContext(this._tokenInputRow, this._tokenInputCol);
                            return null;
                        }
/* 730 */               if (jsonToken != JsonToken.START_OBJECT) {
                            return null;
                        }
/* 731 */               this._parsingContext = this._parsingContext.createChildObjectContext(this._tokenInputRow, this._tokenInputCol);
                        return null;
                    }
/* 736 */           if (nextToken() == JsonToken.VALUE_STRING) {
                        return getText();
                    }
                    return null;
                }

                @Override // com.fasterxml.jackson.core.JsonParser
/* 743 */       public int nextIntValue(int i) throws IOException {
/* 744 */           if (this._currToken != JsonToken.FIELD_NAME) {
                        return nextToken() == JsonToken.VALUE_NUMBER_INT ? getIntValue() : i;
                    }
/* 745 */           this._nameCopied = false;
/* 746 */           JsonToken jsonToken = this._nextToken;
/* 747 */           this._nextToken = null;
/* 748 */           this._currToken = jsonToken;
/* 749 */           if (jsonToken == JsonToken.VALUE_NUMBER_INT) {
/* 750 */               return getIntValue();
                    }
/* 752 */           if (jsonToken == JsonToken.START_ARRAY) {
/* 753 */               this._parsingContext = this._parsingContext.createChildArrayContext(this._tokenInputRow, this._tokenInputCol);
/* 760 */               return i;
                    }
/* 754 */           if (jsonToken == JsonToken.START_OBJECT) {
/* 755 */               this._parsingContext = this._parsingContext.createChildObjectContext(this._tokenInputRow, this._tokenInputCol);
/* 760 */               return i;
                    }
/* 760 */           return i;
                }

                @Override // com.fasterxml.jackson.core.JsonParser
/* 767 */       public long nextLongValue(long j) throws IOException {
/* 768 */           if (this._currToken != JsonToken.FIELD_NAME) {
                        return nextToken() == JsonToken.VALUE_NUMBER_INT ? getLongValue() : j;
                    }
/* 769 */           this._nameCopied = false;
/* 770 */           JsonToken jsonToken = this._nextToken;
/* 771 */           this._nextToken = null;
/* 772 */           this._currToken = jsonToken;
/* 773 */           if (jsonToken == JsonToken.VALUE_NUMBER_INT) {
/* 774 */               return getLongValue();
                    }
/* 776 */           if (jsonToken == JsonToken.START_ARRAY) {
/* 777 */               this._parsingContext = this._parsingContext.createChildArrayContext(this._tokenInputRow, this._tokenInputCol);
/* 784 */               return j;
                    }
/* 778 */           if (jsonToken == JsonToken.START_OBJECT) {
/* 779 */               this._parsingContext = this._parsingContext.createChildObjectContext(this._tokenInputRow, this._tokenInputCol);
/* 784 */               return j;
                    }
/* 784 */           return j;
                }

                @Override // com.fasterxml.jackson.core.JsonParser
/* 791 */       public Boolean nextBooleanValue() throws IOException {
/* 792 */           if (this._currToken == JsonToken.FIELD_NAME) {
/* 793 */               this._nameCopied = false;
/* 794 */               JsonToken jsonToken = this._nextToken;
/* 795 */               this._nextToken = null;
/* 796 */               this._currToken = jsonToken;
/* 797 */               if (jsonToken == JsonToken.VALUE_TRUE) {
/* 798 */                   return Boolean.TRUE;
                        }
/* 800 */               if (jsonToken == JsonToken.VALUE_FALSE) {
/* 801 */                   return Boolean.FALSE;
                        }
/* 803 */               if (jsonToken == JsonToken.START_ARRAY) {
/* 804 */                   this._parsingContext = this._parsingContext.createChildArrayContext(this._tokenInputRow, this._tokenInputCol);
                            return null;
                        }
/* 805 */               if (jsonToken != JsonToken.START_OBJECT) {
                            return null;
                        }
/* 806 */               this._parsingContext = this._parsingContext.createChildObjectContext(this._tokenInputRow, this._tokenInputCol);
                        return null;
                    }
/* 810 */           JsonToken jsonTokenNextToken = nextToken();
/* 811 */           if (jsonTokenNextToken == null) {
                        return null;
                    }
/* 812 */           int iM524id = jsonTokenNextToken.m524id();
/* 813 */           if (iM524id == 9) {
                        return Boolean.TRUE;
                    }
/* 814 */           if (iM524id == 10) {
                        return Boolean.FALSE;
                    }
                    return null;
                }

                /* 846 */       protected JsonToken _parseNumber(int ch) throws IOException {
/* 847 */           if (ch == '-') {
/* 848 */               return _parseNumber2(true);
                    }
/* 850 */           this._inputPtr--;
/* 851 */           return _parseNumber2(false);
                }

                /* 966 */       private JsonToken _parseNumber2(boolean z) throws IOException {
                    int i;
                    char nextChar;
                    boolean z2;
                    int i2;
                    int i3;
                    char c;
                    char[] cArr;
                    int i4;
                    char c2;
                    char[] cArrFinishCurrentSegment;
                    int i5;
                    boolean z3;
                    char nextChar2;
                    int i6;
                    char nextChar3;
                    int i7;
                    char c3;
                    int i8;
                    boolean z4;
                    int i9;
                    char c4;
                    boolean z5;
                    int i10;
                    int i11 = 0;
/* 967 */           char[] cArrEmptyAndGetCurrentSegment = this._textBuffer.emptyAndGetCurrentSegment();
/* 971 */           if (z) {
/* 972 */               cArrEmptyAndGetCurrentSegment[0] = '-';
                        i = 1;
                    } else {
                        i = 0;
                    }
/* 977 */           if (this._inputPtr < this._inputEnd) {
                        char[] cArr2 = this._inputBuffer;
                        int i12 = this._inputPtr;
                        this._inputPtr = i12 + 1;
                        nextChar = cArr2[i12];
                    } else {
/* 977 */               nextChar = getNextChar("No digit following minus sign");
                    }
/* 978 */           if (nextChar == '0') {
/* 979 */               nextChar = _verifyNoLeadingZeroes();
                    }
                    int i13 = 0;
                    char c5 = nextChar;
                    char[] cArrFinishCurrentSegment2 = cArrEmptyAndGetCurrentSegment;
                    char c6 = c5;
                    while (true) {
/* 985 */               if (c6 >= '0' && c6 <= '9') {
/* 986 */                   i13++;
/* 987 */                   if (i >= cArrFinishCurrentSegment2.length) {
/* 988 */                       cArrFinishCurrentSegment2 = this._textBuffer.finishCurrentSegment();
                                i = 0;
                            }
/* 991 */                   int i14 = i + 1;
                            cArrFinishCurrentSegment2[i] = c6;
/* 992 */                   if (this._inputPtr >= this._inputEnd && !loadMore()) {
                                z2 = true;
                                c = 0;
                                i2 = i13;
                                cArr = cArrFinishCurrentSegment2;
                                i3 = i14;
                                break;
                            }
/* 998 */                   char[] cArr3 = this._inputBuffer;
                            int i15 = this._inputPtr;
                            this._inputPtr = i15 + 1;
                            c6 = cArr3[i15];
                            i = i14;
                        } else {
                            z2 = false;
                            i2 = i13;
                            i3 = i;
                            c = c6;
                            cArr = cArrFinishCurrentSegment2;
                            break;
                        }
                    }
/* 1001 */          if (i2 == 0) {
/* 1002 */              reportInvalidNumber("Missing integer part (next char " + _getCharDesc(c) + ")");
                    }
/* 1007 */          if (c == '.') {
/* 1008 */              int i16 = i3 + 1;
                        cArr[i3] = c;
                        char[] cArrFinishCurrentSegment3 = cArr;
                        int i17 = i16;
                        char c7 = c;
                        int i18 = 0;
                        while (true) {
/* 1012 */                  if (this._inputPtr >= this._inputEnd && !loadMore()) {
                                c2 = c7;
                                z5 = true;
                                break;
                            }
/* 1016 */                  char[] cArr4 = this._inputBuffer;
                            int i19 = this._inputPtr;
                            this._inputPtr = i19 + 1;
                            c7 = cArr4[i19];
/* 1017 */                  if (c7 < '0') {
                                c2 = c7;
                                z5 = z2;
                                break;
                            }
                            if (c7 > '9') {
                                c2 = c7;
                                z5 = z2;
                                break;
                            }
/* 1020 */                  i18++;
/* 1021 */                  if (i17 >= cArrFinishCurrentSegment3.length) {
/* 1022 */                      cArrFinishCurrentSegment3 = this._textBuffer.finishCurrentSegment();
                                i10 = 0;
                            } else {
                                i10 = i17;
                            }
/* 1025 */                  i17 = i10 + 1;
                            cArrFinishCurrentSegment3[i10] = c7;
                        }
/* 1028 */              if (i18 == 0) {
/* 1029 */                  reportUnexpectedNumberChar(c2, "Decimal point not followed by a digit");
                        }
                        i4 = i18;
                        i5 = i17;
                        boolean z6 = z5;
                        cArrFinishCurrentSegment = cArrFinishCurrentSegment3;
                        z3 = z6;
                    } else {
                        i4 = 0;
                        c2 = c;
                        cArrFinishCurrentSegment = cArr;
                        i5 = i3;
                        z3 = z2;
                    }
/* 1034 */          if (c2 == 'e' || c2 == 'E') {
/* 1035 */              if (i5 >= cArrFinishCurrentSegment.length) {
/* 1036 */                  cArrFinishCurrentSegment = this._textBuffer.finishCurrentSegment();
                            i5 = 0;
                        }
/* 1039 */              int i20 = i5 + 1;
                        cArrFinishCurrentSegment[i5] = c2;
/* 1041 */              if (this._inputPtr < this._inputEnd) {
                            char[] cArr5 = this._inputBuffer;
                            int i21 = this._inputPtr;
                            this._inputPtr = i21 + 1;
                            nextChar2 = cArr5[i21];
                        } else {
/* 1041 */                  nextChar2 = getNextChar("expected a digit for number exponent");
                        }
/* 1044 */              if (nextChar2 == '-' || nextChar2 == '+') {
/* 1045 */                  if (i20 >= cArrFinishCurrentSegment.length) {
/* 1046 */                      cArrFinishCurrentSegment = this._textBuffer.finishCurrentSegment();
                                i6 = 0;
                            } else {
                                i6 = i20;
                            }
/* 1049 */                  int i22 = i6 + 1;
                            cArrFinishCurrentSegment[i6] = nextChar2;
/* 1051 */                  if (this._inputPtr < this._inputEnd) {
                                char[] cArr6 = this._inputBuffer;
                                int i23 = this._inputPtr;
                                this._inputPtr = i23 + 1;
                                nextChar3 = cArr6[i23];
                            } else {
/* 1051 */                      nextChar3 = getNextChar("expected a digit for number exponent");
                            }
                            char c8 = nextChar3;
                            i7 = i22;
                            c3 = c8;
                        } else {
                            i7 = i20;
                            c3 = nextChar2;
                        }
                        while (true) {
/* 1056 */                  if (c3 <= '9' && c3 >= '0') {
/* 1057 */                      i11++;
/* 1058 */                      if (i7 >= cArrFinishCurrentSegment.length) {
/* 1059 */                          cArrFinishCurrentSegment = this._textBuffer.finishCurrentSegment();
                                    i7 = 0;
                                }
/* 1062 */                      int i24 = i7 + 1;
                                cArrFinishCurrentSegment[i7] = c3;
/* 1063 */                      if (this._inputPtr >= this._inputEnd && !loadMore()) {
                                    i11 = i11;
                                    z4 = true;
                                    i8 = i24;
                                    break;
                                }
/* 1067 */                      char[] cArr7 = this._inputBuffer;
                                int i25 = this._inputPtr;
                                this._inputPtr = i25 + 1;
                                c3 = cArr7[i25];
                                i7 = i24;
                            } else {
                                i11 = i11;
                                i8 = i7;
                                z4 = z3;
                                break;
                            }
                        }
/* 1070 */              if (i11 == 0) {
/* 1071 */                  reportUnexpectedNumberChar(c3, "Exponent indicator not followed by a digit");
                        }
                        i9 = i8;
                        c4 = c3;
                    } else {
                        c4 = c2;
                        i9 = i5;
                        z4 = z3;
                    }
/* 1076 */          if (!z4) {
/* 1077 */              this._inputPtr--;
/* 1078 */              if (this._parsingContext.inRoot()) {
/* 1079 */                  _verifyRootSpace(c4);
                        }
                    }
/* 1082 */          this._textBuffer.setCurrentLength(i9);
/* 1084 */          return reset(z, i2, i4, i11);
                }

/* 1093 */      private char _verifyNoLeadingZeroes() throws IOException {
/* 1094 */          if (this._inputPtr >= this._inputEnd && !loadMore()) {
                        return '0';
                    }
/* 1097 */          char c = this._inputBuffer[this._inputPtr];
/* 1099 */          if (c < '0' || c > '9') {
                        return '0';
                    }
/* 1102 */          if (!isEnabled(JsonParser.Feature.ALLOW_NUMERIC_LEADING_ZEROS)) {
/* 1103 */              reportInvalidNumber("Leading zeroes not allowed");
                    }
/* 1106 */          this._inputPtr++;
/* 1107 */          if (c == '0') {
/* 1114 */              do {
/* 1108 */                  if (this._inputPtr < this._inputEnd || loadMore()) {
/* 1109 */                      c = this._inputBuffer[this._inputPtr];
/* 1110 */                      if (c < '0' || c > '9') {
                                    return '0';
                                }
/* 1113 */                      this._inputPtr++;
                            } else {
/* 1119 */                      return c;
                            }
/* 1114 */              } while (c == '0');
/* 1119 */              return c;
                    }
/* 1119 */          return c;
                }

                /* 1127 */      protected JsonToken _handleInvalidNumberStart(int ch, boolean negative) throws IOException {
                    if (ch == 'I') {
/* 1128 */          _matchToken("Infinity", 1);
                        if (isEnabled(JsonParser.Feature.ALLOW_NON_NUMERIC_NUMBERS)) {
                            return resetAsNaN(negative ? "-Infinity" : "Infinity",
                                    negative ? Double.NEGATIVE_INFINITY : Double.POSITIVE_INFINITY);
                        }
/* 1129 */          _reportError("Non-standard token '" + (negative ? "-Infinity" : "Infinity")
                                + "': enable JsonParser.Feature.ALLOW_NON_NUMERIC_NUMBERS to allow");
                    }
                    if (ch == 'N' && !negative) {
/* 1130 */          _matchToken("NaN", 1);
                        if (isEnabled(JsonParser.Feature.ALLOW_NON_NUMERIC_NUMBERS)) {
                            return resetAsNaN("NaN", Double.NaN);
                        }
/* 1131 */          _reportError("Non-standard token 'NaN': enable JsonParser.Feature.ALLOW_NON_NUMERIC_NUMBERS to allow");
                    }
                    if (Character.isJavaIdentifierStart(ch)) {
                        _reportInvalidToken("" + ((char) ch), "('true', 'false' or 'null')");
                    }
                    _reportUnexpectedChar(ch, "expected a valid value (number, String, array, object, 'true', 'false' or 'null')");
                    return null;
                }

                /* 1164 */      private final void _verifyRootSpace(int i) throws IOException {
/* 1165 */          this._inputPtr++;
                    switch (i) {
                        case 9:
                        case 32:
                            break;
                        case 10:
/* 1174 */                  this._currInputRow++;
/* 1175 */                  this._currInputRowStart = this._inputPtr;
                            break;
                        case 13:
/* 1171 */                  _skipCR();
                            break;
                        default:
/* 1178 */                  _reportMissingRootWS(i);
                            break;
                    }
                }

/* 1188 */      protected String _parseName(int i) throws IOException {
/* 1189 */          if (i != 34) {
/* 1190 */              return _handleOddName(i);
                    }
/* 1196 */          int i2 = this._inputPtr;
/* 1197 */          int i3 = this._hashSeed;
/* 1198 */          int i4 = this._inputEnd;
/* 1200 */          if (i2 < i4) {
/* 1201 */              int[] iArr = _icLatin1;
/* 1202 */              int length = iArr.length;
/* 1216 */              do {
/* 1205 */                  char c = this._inputBuffer[i2];
/* 1206 */                  if (c < length && iArr[c] != 0) {
/* 1207 */                      if (c != '\"') {
                                    break;
                                }
/* 1208 */                      int i5 = this._inputPtr;
/* 1209 */                      this._inputPtr = i2 + 1;
/* 1210 */                      return this._symbols.findSymbol(this._inputBuffer, i5, i2 - i5, i3);
                            }
/* 1214 */                  i3 = (i3 * 33) + c;
/* 1215 */                  i2++;
/* 1216 */              } while (i2 < i4);
                    }
/* 1219 */          int i6 = this._inputPtr;
/* 1220 */          this._inputPtr = i2;
/* 1221 */          return _parseName2(i6, i3, 34);
                }

/* 1225 */      private String _parseName2(int i, int i2, int i3) throws IOException {
                    char c_decodeEscaped;
/* 1226 */          this._textBuffer.resetWithShared(this._inputBuffer, i, this._inputPtr - i);
/* 1231 */          char[] currentSegment = this._textBuffer.getCurrentSegment();
/* 1232 */          int currentSegmentSize = this._textBuffer.getCurrentSegmentSize();
                    while (true) {
/* 1235 */              if (this._inputPtr >= this._inputEnd && !loadMore()) {
/* 1237 */                  _reportInvalidEOF(": was expecting closing '" + ((char) i3) + "' for name");
                        }
/* 1240 */              char[] cArr = this._inputBuffer;
                        int i4 = this._inputPtr;
                        this._inputPtr = i4 + 1;
                        char c = cArr[i4];
/* 1242 */              if (c > '\\') {
                            c_decodeEscaped = c;
                        } else if (c == '\\') {
/* 1248 */                  c_decodeEscaped = _decodeEscaped();
                        } else {
/* 1249 */                  if (c <= i3) {
/* 1250 */                      if (c != i3) {
/* 1253 */                          if (c < ' ') {
/* 1254 */                              _throwUnquotedSpace(c, "name");
                                    }
                                } else {
/* 1268 */                          this._textBuffer.setCurrentLength(currentSegmentSize);
/* 1270 */                          TextBuffer textBuffer = this._textBuffer;
/* 1275 */                          return this._symbols.findSymbol(textBuffer.getTextBuffer(), textBuffer.getTextOffset(), textBuffer.size(), i2);
                                }
                            }
                            c_decodeEscaped = c;
                        }
/* 1258 */              i2 = (i2 * 33) + c;
/* 1260 */              int i5 = currentSegmentSize + 1;
                        currentSegment[currentSegmentSize] = c_decodeEscaped;
/* 1263 */              if (i5 >= currentSegment.length) {
/* 1264 */                  currentSegment = this._textBuffer.finishCurrentSegment();
/* 1265 */                  currentSegmentSize = 0;
                        } else {
                            currentSegmentSize = i5;
                        }
                    }
                }

/* 1287 */      protected String _handleOddName(int i) throws IOException {
                    boolean zIsJavaIdentifierPart;
/* 1288 */          if (i == 39 && isEnabled(JsonParser.Feature.ALLOW_SINGLE_QUOTES)) {
/* 1289 */              return _parseAposName();
                    }
/* 1292 */          if (!isEnabled(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES)) {
/* 1293 */              _reportUnexpectedChar(i, "was expecting double-quote to start field name");
                    }
/* 1295 */          int[] inputCodeLatin1JsNames = CharTypes.getInputCodeLatin1JsNames();
/* 1296 */          int length = inputCodeLatin1JsNames.length;
/* 1301 */          if (i < length) {
/* 1302 */              zIsJavaIdentifierPart = inputCodeLatin1JsNames[i] == 0;
                    } else {
/* 1304 */              zIsJavaIdentifierPart = Character.isJavaIdentifierPart((char) i);
                    }
/* 1306 */          if (!zIsJavaIdentifierPart) {
/* 1307 */              _reportUnexpectedChar(i, "was expecting either valid name character (for unquoted name) or double-quote (for quoted) to start field name");
                    }
/* 1309 */          int i2 = this._inputPtr;
/* 1310 */          int i3 = this._hashSeed;
/* 1311 */          int i4 = this._inputEnd;
/* 1313 */          if (i2 < i4) {
/* 1329 */              do {
/* 1315 */                  char c = this._inputBuffer[i2];
/* 1316 */                  if (c < length) {
/* 1317 */                      if (inputCodeLatin1JsNames[c] != 0) {
/* 1318 */                          int i5 = this._inputPtr - 1;
/* 1319 */                          this._inputPtr = i2;
/* 1320 */                          return this._symbols.findSymbol(this._inputBuffer, i5, i2 - i5, i3);
                                }
                            } else if (!Character.isJavaIdentifierPart(c)) {
/* 1323 */                      int i6 = this._inputPtr - 1;
/* 1324 */                      this._inputPtr = i2;
/* 1325 */                      return this._symbols.findSymbol(this._inputBuffer, i6, i2 - i6, i3);
                            }
/* 1327 */                  i3 = (i3 * 33) + c;
/* 1328 */                  i2++;
/* 1329 */              } while (i2 < i4);
                    }
/* 1331 */          int i7 = this._inputPtr - 1;
/* 1332 */          this._inputPtr = i2;
/* 1333 */          return _handleOddName2(i7, i3, inputCodeLatin1JsNames);
                }

/* 1338 */      protected String _parseAposName() throws IOException {
/* 1339 */          int i = this._inputPtr;
/* 1340 */          int i2 = this._hashSeed;
/* 1341 */          int i3 = this._inputEnd;
/* 1343 */          if (i < i3) {
/* 1344 */              int[] iArr = _icLatin1;
/* 1345 */              int length = iArr.length;
/* 1359 */              do {
/* 1348 */                  char c = this._inputBuffer[i];
/* 1349 */                  if (c == '\'') {
/* 1350 */                      int i4 = this._inputPtr;
/* 1351 */                      this._inputPtr = i + 1;
/* 1352 */                      return this._symbols.findSymbol(this._inputBuffer, i4, i - i4, i2);
                            }
/* 1354 */                  if (c < length && iArr[c] != 0) {
                                break;
                            }
/* 1357 */                  i2 = (i2 * 33) + c;
/* 1358 */                  i++;
/* 1359 */              } while (i < i3);
                    }
/* 1362 */          int i5 = this._inputPtr;
/* 1363 */          this._inputPtr = i;
/* 1365 */          return _parseName2(i5, i2, 39);
                }

/* 1374 */      protected JsonToken _handleOddValue(int i) throws IOException {
                    switch (i) {
                        case 39:
/* 1384 */                  if (isEnabled(JsonParser.Feature.ALLOW_SINGLE_QUOTES)) {
/* 1385 */                      return _handleApos();
                            }
                            break;
                        case 43:
/* 1403 */                  if (this._inputPtr >= this._inputEnd && !loadMore()) {
/* 1405 */                      _reportInvalidEOFInValue();
                            }
/* 1408 */                  char[] cArr = this._inputBuffer;
                            int i2 = this._inputPtr;
                            this._inputPtr = i2 + 1;
                            return _handleInvalidNumberStart(cArr[i2], false);
                        case 73:
/* 1396 */                  _matchToken("Infinity", 1);
/* 1397 */                  if (isEnabled(JsonParser.Feature.ALLOW_NON_NUMERIC_NUMBERS)) {
/* 1398 */                      return resetAsNaN("Infinity", Double.POSITIVE_INFINITY);
                            }
/* 1400 */                  _reportError("Non-standard token 'Infinity': enable JsonParser.Feature.ALLOW_NON_NUMERIC_NUMBERS to allow");
                            break;
                            break;
                        case 78:
/* 1389 */                  _matchToken("NaN", 1);
/* 1390 */                  if (isEnabled(JsonParser.Feature.ALLOW_NON_NUMERIC_NUMBERS)) {
/* 1391 */                      return resetAsNaN("NaN", Double.NaN);
                            }
/* 1393 */                  _reportError("Non-standard token 'NaN': enable JsonParser.Feature.ALLOW_NON_NUMERIC_NUMBERS to allow");
                            break;
                            break;
                    }
/* 1411 */          if (Character.isJavaIdentifierStart(i)) {
/* 1412 */              _reportInvalidToken("" + ((char) i), "('true', 'false' or 'null')");
                    }
/* 1415 */          _reportUnexpectedChar(i, "expected a valid value (number, String, array, object, 'true', 'false' or 'null')");
/* 1416 */          return null;
                }

/* 1420 */      protected JsonToken _handleApos() throws IOException {
/* 1421 */          char[] cArrEmptyAndGetCurrentSegment = this._textBuffer.emptyAndGetCurrentSegment();
/* 1422 */          int currentSegmentSize = this._textBuffer.getCurrentSegmentSize();
                    while (true) {
/* 1425 */              if (this._inputPtr >= this._inputEnd && !loadMore()) {
/* 1427 */                  _reportInvalidEOF(": was expecting closing quote for a string value");
                        }
/* 1430 */              char[] cArr = this._inputBuffer;
                        int i = this._inputPtr;
                        this._inputPtr = i + 1;
                        char c_decodeEscaped = cArr[i];
/* 1432 */              if (c_decodeEscaped <= '\\') {
/* 1433 */                  if (c_decodeEscaped == '\\') {
/* 1438 */                      c_decodeEscaped = _decodeEscaped();
                            } else if (c_decodeEscaped <= '\'') {
/* 1440 */                      if (c_decodeEscaped != '\'') {
/* 1443 */                          if (c_decodeEscaped < ' ') {
/* 1444 */                              _throwUnquotedSpace(c_decodeEscaped, "string value");
                                    }
                                } else {
/* 1456 */                          this._textBuffer.setCurrentLength(currentSegmentSize);
/* 1457 */                          return JsonToken.VALUE_STRING;
                                }
                            }
                        }
/* 1449 */              if (currentSegmentSize >= cArrEmptyAndGetCurrentSegment.length) {
/* 1450 */                  cArrEmptyAndGetCurrentSegment = this._textBuffer.finishCurrentSegment();
/* 1451 */                  currentSegmentSize = 0;
                        }
                        int i2 = currentSegmentSize;
/* 1454 */              currentSegmentSize = i2 + 1;
                        cArrEmptyAndGetCurrentSegment[i2] = c_decodeEscaped;
                    }
                }

                /* JADX WARN: Code duplicated, block: B:22:0x0069 A[SYNTHETIC] */
                /* JADX WARN: Code duplicated, block: B:24:0x005a A[SYNTHETIC] */
/* 1461 */      private String _handleOddName2(int i, int i2, int[] iArr) throws IOException {
                    int i3;
/* 1462 */          this._textBuffer.resetWithShared(this._inputBuffer, i, this._inputPtr - i);
/* 1463 */          char[] currentSegment = this._textBuffer.getCurrentSegment();
/* 1464 */          int currentSegmentSize = this._textBuffer.getCurrentSegmentSize();
/* 1465 */          int length = iArr.length;
                    while (true) {
/* 1468 */              if (this._inputPtr >= this._inputEnd && !loadMore()) {
                            break;
                        }
/* 1473 */              char c = this._inputBuffer[this._inputPtr];
/* 1475 */              if (c <= length) {
/* 1476 */                  if (iArr[c] != 0) {
                                break;
                            }
/* 1482 */                  this._inputPtr++;
/* 1483 */                  i2 = (i2 * 33) + c;
/* 1485 */                  i3 = currentSegmentSize + 1;
                            currentSegment[currentSegmentSize] = c;
/* 1488 */                  if (i3 >= currentSegment.length) {
/* 1489 */                      currentSegment = this._textBuffer.finishCurrentSegment();
/* 1490 */                      currentSegmentSize = 0;
                            } else {
                                currentSegmentSize = i3;
                            }
                        } else {
/* 1479 */                  if (!Character.isJavaIdentifierPart(c)) {
                                break;
                            }
/* 1482 */                  this._inputPtr++;
/* 1483 */                  i2 = (i2 * 33) + c;
/* 1485 */                  i3 = currentSegmentSize + 1;
                            currentSegment[currentSegmentSize] = c;
/* 1488 */                  if (i3 >= currentSegment.length) {
/* 1489 */                      currentSegment = this._textBuffer.finishCurrentSegment();
/* 1490 */                      currentSegmentSize = 0;
                            } else {
                                currentSegmentSize = i3;
                            }
                        }
                    }
/* 1493 */          this._textBuffer.setCurrentLength(currentSegmentSize);
/* 1495 */          TextBuffer textBuffer = this._textBuffer;
/* 1500 */          return this._symbols.findSymbol(textBuffer.getTextBuffer(), textBuffer.getTextOffset(), textBuffer.size(), i2);
                }

                @Override // com.fasterxml.jackson.core.base.ParserBase
/* 1510 */      protected void _finishString() throws IOException {
/* 1511 */          int i = this._inputPtr;
/* 1512 */          int i2 = this._inputEnd;
/* 1514 */          if (i < i2) {
/* 1515 */              int[] iArr = _icLatin1;
/* 1516 */              int length = iArr.length;
/* 1530 */              do {
/* 1519 */                  char c = this._inputBuffer[i];
/* 1520 */                  if (c < length && iArr[c] != 0) {
/* 1521 */                      if (c != '\"') {
                                    break;
                                }
/* 1522 */                      this._textBuffer.resetWithShared(this._inputBuffer, this._inputPtr, i - this._inputPtr);
/* 1523 */                      this._inputPtr = i + 1;
/* 1539 */                      return;
                            }
/* 1529 */                  i++;
/* 1530 */              } while (i < i2);
                    }
/* 1536 */          this._textBuffer.resetWithCopy(this._inputBuffer, this._inputPtr, i - this._inputPtr);
/* 1537 */          this._inputPtr = i;
/* 1538 */          _finishString2();
                }

/* 1542 */      protected void _finishString2() throws IOException {
/* 1543 */          char[] currentSegment = this._textBuffer.getCurrentSegment();
/* 1544 */          int currentSegmentSize = this._textBuffer.getCurrentSegmentSize();
                    while (true) {
/* 1547 */              if (this._inputPtr >= this._inputEnd && !loadMore()) {
/* 1549 */                  _reportInvalidEOF(": was expecting closing quote for a string value");
                        }
/* 1552 */              char[] cArr = this._inputBuffer;
                        int i = this._inputPtr;
                        this._inputPtr = i + 1;
                        char c_decodeEscaped = cArr[i];
/* 1554 */              if (c_decodeEscaped <= '\\') {
/* 1555 */                  if (c_decodeEscaped == '\\') {
/* 1560 */                      c_decodeEscaped = _decodeEscaped();
                            } else if (c_decodeEscaped <= '\"') {
/* 1562 */                      if (c_decodeEscaped != '\"') {
/* 1565 */                          if (c_decodeEscaped < ' ') {
/* 1566 */                              _throwUnquotedSpace(c_decodeEscaped, "string value");
                                    }
                                } else {
/* 1578 */                          this._textBuffer.setCurrentLength(currentSegmentSize);
/* 1579 */                          return;
                                }
                            }
                        }
/* 1571 */              if (currentSegmentSize >= currentSegment.length) {
/* 1572 */                  currentSegment = this._textBuffer.finishCurrentSegment();
/* 1573 */                  currentSegmentSize = 0;
                        }
                        int i2 = currentSegmentSize;
/* 1576 */              currentSegmentSize = i2 + 1;
                        currentSegment[i2] = c_decodeEscaped;
                    }
                }

/* 1587 */      protected void _skipString() throws IOException {
/* 1588 */          this._tokenIncomplete = false;
/* 1590 */          int i = this._inputPtr;
/* 1591 */          int i2 = this._inputEnd;
/* 1592 */          char[] cArr = this._inputBuffer;
                    while (true) {
/* 1595 */              if (i >= i2) {
/* 1596 */                  this._inputPtr = i;
/* 1597 */                  if (!loadMore()) {
/* 1598 */                      _reportInvalidEOF(": was expecting closing quote for a string value");
                            }
/* 1600 */                  i = this._inputPtr;
/* 1601 */                  i2 = this._inputEnd;
                        }
/* 1603 */              int i3 = i + 1;
                        char c = cArr[i];
/* 1605 */              if (c <= '\\') {
/* 1606 */                  if (c == '\\') {
/* 1611 */                      this._inputPtr = i3;
/* 1612 */                      _decodeEscaped();
/* 1613 */                      i = this._inputPtr;
/* 1614 */                      i2 = this._inputEnd;
                            } else if (c <= '\"') {
/* 1616 */                      if (c == '\"') {
/* 1617 */                          this._inputPtr = i3;
/* 1627 */                          return;
                                } else if (c < ' ') {
/* 1621 */                          this._inputPtr = i3;
/* 1622 */                          _throwUnquotedSpace(c, "string value");
                                }
                            }
                        }
                        i = i3;
                    }
                }

/* 1640 */      protected void _skipCR() throws IOException {
/* 1641 */          if ((this._inputPtr < this._inputEnd || loadMore()) && this._inputBuffer[this._inputPtr] == '\n') {
/* 1643 */              this._inputPtr++;
                    }
/* 1646 */          this._currInputRow++;
/* 1647 */          this._currInputRowStart = this._inputPtr;
                }

/* 1651 */      private int _skipWS() throws IOException {
/* 1652 */          int[] iArr = _icWS;
                    while (true) {
/* 1653 */              if (this._inputPtr < this._inputEnd || loadMore()) {
/* 1654 */                  char[] cArr = this._inputBuffer;
                            int i = this._inputPtr;
                            this._inputPtr = i + 1;
                            char c = cArr[i];
/* 1655 */                  if (c < '@') {
                                switch (iArr[c]) {
                                    case -1:
/* 1660 */                              _throwInvalidSpace(c);
                                        break;
                                    case 0:
                                        break;
                                    case 10:
/* 1666 */                              this._currInputRow++;
/* 1667 */                              this._currInputRowStart = this._inputPtr;
                                        break;
                                    case 13:
/* 1670 */                              _skipCR();
                                        break;
                                    case 35:
/* 1676 */                              if (!_skipYAMLComment()) {
                                        }
                                        break;
                                    case 47:
/* 1673 */                              _skipComment();
                                        break;
                                }
                            }
/* 1677 */                  return c;
                        }
/* 1682 */              throw _constructError("Unexpected end-of-input within/between " + this._parsingContext.getTypeDesc() + " entries");
                    }
                }

/* 1686 */      private int _skipWSOrEnd() throws IOException {
/* 1687 */          int[] iArr = _icWS;
                    while (true) {
/* 1688 */              if (this._inputPtr < this._inputEnd || loadMore()) {
/* 1689 */                  char[] cArr = this._inputBuffer;
                            int i = this._inputPtr;
                            this._inputPtr = i + 1;
                            char c = cArr[i];
/* 1690 */                  if (c < '@') {
                                switch (iArr[c]) {
                                    case -1:
/* 1695 */                              _throwInvalidSpace(c);
/* 1719 */                              return c;
                                    case 0:
/* 1719 */                              return c;
                                    case 10:
/* 1701 */                              this._currInputRow++;
/* 1702 */                              this._currInputRowStart = this._inputPtr;
                                        break;
                                    case 13:
/* 1705 */                              _skipCR();
                                        break;
                                    case 35:
/* 1711 */                              if (!_skipYAMLComment()) {
/* 1719 */                                  return c;
                                        }
                                        break;
                                        break;
                                    case 47:
/* 1708 */                              _skipComment();
                                        break;
                                }
                            } else {
/* 1719 */                      return c;
                            }
                        } else {
/* 1718 */                  _handleEOF();
/* 1719 */                  return -1;
                        }
                    }
                }

/* 1723 */      private void _skipComment() throws IOException {
/* 1724 */          if (!isEnabled(JsonParser.Feature.ALLOW_COMMENTS)) {
/* 1725 */              _reportUnexpectedChar(47, "maybe a (non-standard) comment? (not recognized as one since Feature 'ALLOW_COMMENTS' not enabled for parser)");
                    }
/* 1728 */          if (this._inputPtr >= this._inputEnd && !loadMore()) {
/* 1729 */              _reportInvalidEOF(" in a comment");
                    }
/* 1731 */          char[] cArr = this._inputBuffer;
                    int i = this._inputPtr;
                    this._inputPtr = i + 1;
                    char c = cArr[i];
/* 1732 */          if (c == '/') {
/* 1733 */              _skipLine();
                    } else if (c == '*') {
/* 1735 */              _skipCComment();
                    } else {
/* 1737 */              _reportUnexpectedChar(c, "was expecting either '*' or '/' for a comment");
                    }
                }

/* 1744 */      private void _skipCComment() throws IOException {
                    while (true) {
/* 1745 */              if (this._inputPtr >= this._inputEnd && !loadMore()) {
                            break;
                        }
/* 1746 */              char[] cArr = this._inputBuffer;
                        int i = this._inputPtr;
                        this._inputPtr = i + 1;
                        char c = cArr[i];
/* 1747 */              if (c <= '*') {
/* 1748 */                  if (c == '*') {
/* 1749 */                      if (this._inputPtr >= this._inputEnd && !loadMore()) {
                                    break;
                                } else if (this._inputBuffer[this._inputPtr] == '/') {
/* 1753 */                          this._inputPtr++;
/* 1771 */                          return;
                                }
                            } else if (c < ' ') {
/* 1759 */                      if (c == '\n') {
/* 1760 */                          this._currInputRow++;
/* 1761 */                          this._currInputRowStart = this._inputPtr;
                                } else if (c == '\r') {
/* 1763 */                          _skipCR();
                                } else if (c != '\t') {
/* 1765 */                          _throwInvalidSpace(c);
                                }
                            }
                        }
                    }
/* 1770 */          _reportInvalidEOF(" in a comment");
                }

/* 1774 */      private boolean _skipYAMLComment() throws IOException {
/* 1775 */          if (!isEnabled(JsonParser.Feature.ALLOW_YAML_COMMENTS)) {
/* 1776 */              return false;
                    }
/* 1778 */          _skipLine();
/* 1779 */          return true;
                }

/* 1784 */      private void _skipLine() throws IOException {
                    while (true) {
/* 1785 */              if (this._inputPtr < this._inputEnd || loadMore()) {
/* 1786 */                  char[] cArr = this._inputBuffer;
                            int i = this._inputPtr;
                            this._inputPtr = i + 1;
                            char c = cArr[i];
/* 1787 */                  if (c < ' ') {
/* 1788 */                      if (c == '\n') {
/* 1789 */                          this._currInputRow++;
/* 1790 */                          this._currInputRowStart = this._inputPtr;
/* 1800 */                          return;
                                } else if (c == '\r') {
/* 1793 */                          _skipCR();
/* 1800 */                          return;
                                } else if (c != '\t') {
/* 1796 */                          _throwInvalidSpace(c);
                                }
                            }
                        } else {
/* 1800 */                  return;
                        }
                    }
                }

                @Override // com.fasterxml.jackson.core.base.ParserBase
/* 1804 */      protected char _decodeEscaped() throws IOException {
                    int i = 0;
/* 1805 */          if (this._inputPtr >= this._inputEnd && !loadMore()) {
/* 1807 */              _reportInvalidEOF(" in character escape sequence");
                    }
/* 1810 */          char[] cArr = this._inputBuffer;
                    int i2 = this._inputPtr;
                    this._inputPtr = i2 + 1;
                    char c = cArr[i2];
                    switch (c) {
                        case '\"':
                        case '/':
                        case '\\':
/* 1853 */                  return c;
                        case 'b':
/* 1815 */                  return '\b';
                        case 'f':
/* 1821 */                  return '\f';
                        case 'n':
/* 1819 */                  return '\n';
                        case 'r':
/* 1823 */                  return '\r';
                        case 't':
/* 1817 */                  return '\t';
                        case 'u':
/* 1840 */                  for (int i3 = 0; i3 < 4; i3++) {
/* 1841 */                      if (this._inputPtr >= this._inputEnd && !loadMore()) {
/* 1843 */                          _reportInvalidEOF(" in character escape sequence");
                                }
/* 1846 */                      char[] cArr2 = this._inputBuffer;
                                int i4 = this._inputPtr;
                                this._inputPtr = i4 + 1;
                                char c2 = cArr2[i4];
/* 1847 */                      int iCharToHex = CharTypes.charToHex(c2);
/* 1848 */                      if (iCharToHex < 0) {
/* 1849 */                          _reportUnexpectedChar(c2, "expected a hex-digit for character escape sequence");
                                }
/* 1851 */                      i = (i << 4) | iCharToHex;
                            }
/* 1853 */                  return (char) i;
                        default:
/* 1835 */                  return _handleUnrecognizedCharacterEscape(c);
                    }
                }

/* 1860 */      protected void _matchToken(String str, int i) throws IOException {
                    char c;
/* 1861 */          int length = str.length();
                    do {
/* 1864 */              if (this._inputPtr >= this._inputEnd && !loadMore()) {
/* 1866 */                  _reportInvalidToken(str.substring(0, i));
                        }
/* 1869 */              if (this._inputBuffer[this._inputPtr] != str.charAt(i)) {
/* 1870 */                  _reportInvalidToken(str.substring(0, i));
                        }
/* 1872 */              this._inputPtr++;
/* 1873 */              i++;
                    } while (i < length);
/* 1876 */          if ((this._inputPtr < this._inputEnd || loadMore()) && (c = this._inputBuffer[this._inputPtr]) >= '0' && c != ']' && c != '}' && Character.isJavaIdentifierPart(c)) {
/* 1887 */              _reportInvalidToken(str.substring(0, i));
                    }
                }

/* 1904 */      protected byte[] _decodeBase64(Base64Variant base64Variant) throws IOException {
/* 1905 */          ByteArrayBuilder byteArrayBuilder_getByteArrayBuilder = _getByteArrayBuilder();
                    while (true) {
/* 1912 */              if (this._inputPtr >= this._inputEnd) {
/* 1913 */                  loadMoreGuaranteed();
                        }
/* 1915 */              char[] cArr = this._inputBuffer;
                        int i = this._inputPtr;
                        this._inputPtr = i + 1;
                        char c = cArr[i];
/* 1916 */              if (c > ' ') {
/* 1917 */                  int iDecodeBase64Char = base64Variant.decodeBase64Char(c);
/* 1918 */                  if (iDecodeBase64Char < 0) {
/* 1919 */                      if (c == '\"') {
/* 1920 */                          return byteArrayBuilder_getByteArrayBuilder.toByteArray();
                                }
/* 1922 */                      iDecodeBase64Char = _decodeBase64Escape(base64Variant, c, 0);
/* 1923 */                      if (iDecodeBase64Char < 0) {
                                    continue;
                                }
                            }
/* 1931 */                  if (this._inputPtr >= this._inputEnd) {
/* 1932 */                      loadMoreGuaranteed();
                            }
/* 1934 */                  char[] cArr2 = this._inputBuffer;
                            int i2 = this._inputPtr;
                            this._inputPtr = i2 + 1;
                            char c2 = cArr2[i2];
/* 1935 */                  int iDecodeBase64Char2 = base64Variant.decodeBase64Char(c2);
/* 1936 */                  if (iDecodeBase64Char2 < 0) {
/* 1937 */                      iDecodeBase64Char2 = _decodeBase64Escape(base64Variant, c2, 1);
                            }
/* 1939 */                  int i3 = iDecodeBase64Char2 | (iDecodeBase64Char << 6);
/* 1942 */                  if (this._inputPtr >= this._inputEnd) {
/* 1943 */                      loadMoreGuaranteed();
                            }
/* 1945 */                  char[] cArr3 = this._inputBuffer;
                            int i4 = this._inputPtr;
                            this._inputPtr = i4 + 1;
                            char c3 = cArr3[i4];
/* 1946 */                  int iDecodeBase64Char3 = base64Variant.decodeBase64Char(c3);
/* 1949 */                  if (iDecodeBase64Char3 < 0) {
/* 1950 */                      if (iDecodeBase64Char3 != -2) {
/* 1952 */                          if (c3 == '\"' && !base64Variant.usesPadding()) {
/* 1954 */                              byteArrayBuilder_getByteArrayBuilder.append(i3 >> 4);
/* 1955 */                              return byteArrayBuilder_getByteArrayBuilder.toByteArray();
                                    }
/* 1957 */                          iDecodeBase64Char3 = _decodeBase64Escape(base64Variant, c3, 2);
                                }
/* 1959 */                      if (iDecodeBase64Char3 == -2) {
/* 1961 */                          if (this._inputPtr >= this._inputEnd) {
/* 1962 */                              loadMoreGuaranteed();
                                    }
/* 1964 */                          char[] cArr4 = this._inputBuffer;
                                    int i5 = this._inputPtr;
                                    this._inputPtr = i5 + 1;
                                    char c4 = cArr4[i5];
/* 1965 */                          if (!base64Variant.usesPaddingChar(c4)) {
/* 1966 */                              throw reportInvalidBase64Char(base64Variant, c4, 3, "expected padding character '" + base64Variant.getPaddingChar() + "'");
                                    }
/* 1970 */                          byteArrayBuilder_getByteArrayBuilder.append(i3 >> 4);
                                }
                            }
/* 1976 */                  int i6 = (i3 << 6) | iDecodeBase64Char3;
/* 1978 */                  if (this._inputPtr >= this._inputEnd) {
/* 1979 */                      loadMoreGuaranteed();
                            }
/* 1981 */                  char[] cArr5 = this._inputBuffer;
                            int i7 = this._inputPtr;
                            this._inputPtr = i7 + 1;
                            char c5 = cArr5[i7];
/* 1982 */                  int iDecodeBase64Char4 = base64Variant.decodeBase64Char(c5);
/* 1983 */                  if (iDecodeBase64Char4 < 0) {
/* 1984 */                      if (iDecodeBase64Char4 != -2) {
/* 1986 */                          if (c5 == '\"' && !base64Variant.usesPadding()) {
/* 1988 */                              byteArrayBuilder_getByteArrayBuilder.appendTwoBytes(i6 >> 2);
/* 1989 */                              return byteArrayBuilder_getByteArrayBuilder.toByteArray();
                                    }
/* 1991 */                          iDecodeBase64Char4 = _decodeBase64Escape(base64Variant, c5, 3);
                                }
/* 1993 */                      if (iDecodeBase64Char4 == -2) {
/* 2000 */                          byteArrayBuilder_getByteArrayBuilder.appendTwoBytes(i6 >> 2);
                                }
                            }
/* 2007 */                  byteArrayBuilder_getByteArrayBuilder.appendThreeBytes(iDecodeBase64Char4 | (i6 << 6));
                        }
                    }
                }

/* 2017 */      protected void _reportInvalidToken(String str) throws IOException {
/* 2018 */          _reportInvalidToken(str, "'null', 'true', 'false' or NaN");
                }

/* 2023 */      protected void _reportInvalidToken(String str, String str2) throws IOException {
/* 2024 */          StringBuilder sb = new StringBuilder(str);
                    while (true) {
/* 2030 */              if (this._inputPtr >= this._inputEnd && !loadMore()) {
                            break;
                        }
/* 2035 */              char c = this._inputBuffer[this._inputPtr];
/* 2036 */              if (!Character.isJavaIdentifierPart(c)) {
                            break;
                        }
/* 2039 */              this._inputPtr++;
/* 2040 */              sb.append(c);
                    }
/* 2042 */          _reportError("Unrecognized token '" + sb.toString() + "': was expecting " + str2);
                }
            }
