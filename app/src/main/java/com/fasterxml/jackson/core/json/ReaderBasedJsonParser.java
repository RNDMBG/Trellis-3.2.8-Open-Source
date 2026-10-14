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
            
            
    public final class ReaderBasedJsonParser extends ParserBase {
                protected static final int[] _icLatin1 = CharTypes.getInputCodeLatin1();
                private static final int[] _icWS = CharTypes.getInputCodeWS();
                protected final int _hashSeed;
                protected char[] _inputBuffer;
                protected ObjectCodec _objectCodec;
                protected Reader _reader;
                protected final CharsToNameCanonicalizer _symbols;
                protected boolean _tokenIncomplete;

        public ReaderBasedJsonParser(IOContext iOContext, int i, Reader reader, ObjectCodec objectCodec, CharsToNameCanonicalizer charsToNameCanonicalizer) {
            super(iOContext, i);
            this._tokenIncomplete = false;
            this._reader = reader;
            this._inputBuffer = iOContext.allocTokenBuffer();
            this._objectCodec = objectCodec;
            this._symbols = charsToNameCanonicalizer;
            this._hashSeed = charsToNameCanonicalizer.hashSeed();
                }

                @Override 
        public ObjectCodec getCodec() {
            return this._objectCodec;
                }

                @Override 
       public void setCodec(ObjectCodec objectCodec) {
           this._objectCodec = objectCodec;
                }

                @Override 
       public int releaseBuffered(Writer writer) throws IOException {
           int i = this._inputEnd - this._inputPtr;
           if (i < 1) {
               return 0;
                    }
           writer.write(this._inputBuffer, this._inputPtr, i);
           return i;
                }

                @Override 
       public Object getInputSource() {
           return this._reader;
                }

                @Override 
       protected boolean loadMore() throws IOException {
           this._currInputProcessed += (long) this._inputEnd;
           this._currInputRowStart -= this._inputEnd;
           if (this._reader == null) {
                        return false;
                    }
           int i = this._reader.read(this._inputBuffer, 0, this._inputBuffer.length);
           if (i > 0) {
               this._inputPtr = 0;
               this._inputEnd = i;
               return true;
                    }
           _closeInput();
           if (i == 0) {
               throw new IOException("Reader returned 0 characters when trying to read " + this._inputEnd);
                    }
                    return false;
                }

       protected char getNextChar(String str) throws IOException {
           if (this._inputPtr >= this._inputEnd && !loadMore()) {
               _reportInvalidEOF(str);
                    }
           char[] cArr = this._inputBuffer;
                    int i = this._inputPtr;
                    this._inputPtr = i + 1;
                    return cArr[i];
                }

                @Override 
       protected void _closeInput() throws IOException {
           if (this._reader != null) {
               if (this._ioContext.isResourceManaged() || isEnabled(JsonParser.Feature.AUTO_CLOSE_SOURCE)) {
                   this._reader.close();
                        }
               this._reader = null;
                    }
                }

                @Override 
       protected void _releaseBuffers() throws IOException {
           super._releaseBuffers();
           this._symbols.release();
           char[] cArr = this._inputBuffer;
           if (cArr != null) {
               this._inputBuffer = null;
               this._ioContext.releaseTokenBuffer(cArr);
                    }
                }

                @Override 
       public String getText() throws IOException {
           JsonToken jsonToken = this._currToken;
           if (jsonToken != JsonToken.VALUE_STRING) {
                        return _getText2(jsonToken);
                    }
           if (this._tokenIncomplete) {
               this._tokenIncomplete = false;
               _finishString();
                    }
           return this._textBuffer.contentsAsString();
                }

                @Override 
       public String getValueAsString() throws IOException {
           if (this._currToken != JsonToken.VALUE_STRING) {
                        return super.getValueAsString(null);
                    }
           if (this._tokenIncomplete) {
               this._tokenIncomplete = false;
               _finishString();
                    }
           return this._textBuffer.contentsAsString();
                }

                @Override 
       public String getValueAsString(String str) throws IOException {
           if (this._currToken != JsonToken.VALUE_STRING) {
                        return super.getValueAsString(str);
                    }
           if (this._tokenIncomplete) {
               this._tokenIncomplete = false;
               _finishString();
                    }
           return this._textBuffer.contentsAsString();
                }

       protected String _getText2(JsonToken jsonToken) {
           if (jsonToken == null) {
               return null;
                    }
                    switch (jsonToken.m524id()) {
                        case 5:
                   return this._parsingContext.getCurrentName();
                        case 6:
                        case 7:
                        case 8:
                   return this._textBuffer.contentsAsString();
                        default:
                   return jsonToken.asString();
                    }
                }

                @Override 
       public char[] getTextCharacters() throws IOException {
           if (this._currToken != null) {
                        switch (this._currToken.m524id()) {
                            case 5:
                       if (!this._nameCopied) {
                           String currentName = this._parsingContext.getCurrentName();
                           int length = currentName.length();
                           if (this._nameCopyBuffer == null) {
                               this._nameCopyBuffer = this._ioContext.allocNameCopyBuffer(length);
                                    } else if (this._nameCopyBuffer.length < length) {
                               this._nameCopyBuffer = new char[length];
                                    }
                           currentName.getChars(0, length, this._nameCopyBuffer, 0);
                           this._nameCopied = true;
                                }
                       return this._nameCopyBuffer;
                            case 6:
                       if (this._tokenIncomplete) {
                           this._tokenIncomplete = false;
                           _finishString();
                                }
                                break;
                            case 7:
                            case 8:
                                break;
                            default:
                       return this._currToken.asCharArray();
                        }
               return this._textBuffer.getTextBuffer();
                    }
           return null;
                }

                @Override 
       public int getTextLength() throws IOException {
           if (this._currToken == null) {
                        return 0;
                    }
                    switch (this._currToken.m524id()) {
                        case 5:
                   return this._parsingContext.getCurrentName().length();
                        case 6:
                   if (this._tokenIncomplete) {
                       this._tokenIncomplete = false;
                       _finishString();
                            }
                            break;
                        case 7:
                        case 8:
                            break;
                        default:
                   return this._currToken.asCharArray().length;
                    }
           return this._textBuffer.size();
                }

                @Override 
       public int getTextOffset() throws IOException {
           if (this._currToken == null) {
                        return 0;
                    }
                    switch (this._currToken.m524id()) {
                        case 5:
                        default:
                            return 0;
                        case 6:
                   if (this._tokenIncomplete) {
                       this._tokenIncomplete = false;
                       _finishString();
                            }
                            break;
                        case 7:
                        case 8:
                            break;
                    }
           return this._textBuffer.getTextOffset();
                }

                @Override 
       public byte[] getBinaryValue(Base64Variant base64Variant) throws IOException {
           if (this._currToken != JsonToken.VALUE_STRING && (this._currToken != JsonToken.VALUE_EMBEDDED_OBJECT || this._binaryValue == null)) {
               _reportError("Current token (" + this._currToken + ") not VALUE_STRING or VALUE_EMBEDDED_OBJECT, can not access as binary");
                    }
           if (this._tokenIncomplete) {
                        try {
                   this._binaryValue = _decodeBase64(base64Variant);
                   this._tokenIncomplete = false;
                        } catch (IllegalArgumentException e) {
                   throw _constructError("Failed to decode VALUE_STRING as base64 (" + base64Variant + "): " + e.getMessage());
                        }
                    } else if (this._binaryValue == null) {
               ByteArrayBuilder byteArrayBuilder_getByteArrayBuilder = _getByteArrayBuilder();
               _decodeBase64(getText(), byteArrayBuilder_getByteArrayBuilder, base64Variant);
               this._binaryValue = byteArrayBuilder_getByteArrayBuilder.toByteArray();
                    }
           return this._binaryValue;
                }

                @Override 
       public int readBinaryValue(Base64Variant base64Variant, OutputStream outputStream) throws IOException {
           if (!this._tokenIncomplete || this._currToken != JsonToken.VALUE_STRING) {
               byte[] binaryValue = getBinaryValue(base64Variant);
               outputStream.write(binaryValue);
               return binaryValue.length;
                    }
           byte[] bArrAllocBase64Buffer = this._ioContext.allocBase64Buffer();
                    try {
               int i_readBinary = _readBinary(base64Variant, outputStream, bArrAllocBase64Buffer);
               this._ioContext.releaseBase64Buffer(bArrAllocBase64Buffer);
               return i_readBinary;
                    } catch (Throwable th) {
                        this._ioContext.releaseBase64Buffer(bArrAllocBase64Buffer);
                        throw th;
                    }
                }

       protected int _readBinary(Base64Variant base64Variant, OutputStream outputStream, byte[] bArr) throws IOException {
                    int i;
           int length = bArr.length - 3;
                    int i2 = 0;
                    int i3 = 0;
                    while (true) {
               if (this._inputPtr >= this._inputEnd) {
                   loadMoreGuaranteed();
                        }
               char[] cArr = this._inputBuffer;
                        int i4 = this._inputPtr;
                        this._inputPtr = i4 + 1;
                        char c = cArr[i4];
               if (c > ' ') {
                   int iDecodeBase64Char = base64Variant.decodeBase64Char(c);
                   if (iDecodeBase64Char < 0) {
                       if (c == '\"') {
                                    break;
                                }
                       iDecodeBase64Char = _decodeBase64Escape(base64Variant, c, 0);
                       if (iDecodeBase64Char < 0) {
                                    continue;
                                }
                            }
                            int i5 = iDecodeBase64Char;
                   if (i3 > length) {
                       i2 += i3;
                       outputStream.write(bArr, 0, i3);
                                i = 0;
                            } else {
                                i = i3;
                            }
                   if (this._inputPtr >= this._inputEnd) {
                       loadMoreGuaranteed();
                            }
                   char[] cArr2 = this._inputBuffer;
                            int i6 = this._inputPtr;
                            this._inputPtr = i6 + 1;
                            char c2 = cArr2[i6];
                   int iDecodeBase64Char2 = base64Variant.decodeBase64Char(c2);
                   if (iDecodeBase64Char2 < 0) {
                       iDecodeBase64Char2 = _decodeBase64Escape(base64Variant, c2, 1);
                            }
                   int i7 = (i5 << 6) | iDecodeBase64Char2;
                   if (this._inputPtr >= this._inputEnd) {
                       loadMoreGuaranteed();
                            }
                   char[] cArr3 = this._inputBuffer;
                            int i8 = this._inputPtr;
                            this._inputPtr = i8 + 1;
                            char c3 = cArr3[i8];
                   int iDecodeBase64Char3 = base64Variant.decodeBase64Char(c3);
                   if (iDecodeBase64Char3 < 0) {
                       if (iDecodeBase64Char3 != -2) {
                           if (c3 == '\"' && !base64Variant.usesPadding()) {
                               i3 = i + 1;
                               bArr[i] = (byte) (i7 >> 4);
                                        break;
                                    }
                           iDecodeBase64Char3 = _decodeBase64Escape(base64Variant, c3, 2);
                                }
                       if (iDecodeBase64Char3 == -2) {
                           if (this._inputPtr >= this._inputEnd) {
                               loadMoreGuaranteed();
                                    }
                           char[] cArr4 = this._inputBuffer;
                                    int i9 = this._inputPtr;
                                    this._inputPtr = i9 + 1;
                                    char c4 = cArr4[i9];
                           if (!base64Variant.usesPaddingChar(c4)) {
                               throw reportInvalidBase64Char(base64Variant, c4, 3, "expected padding character '" + base64Variant.getPaddingChar() + "'");
                                    }
                           i3 = i + 1;
                           bArr[i] = (byte) (i7 >> 4);
                                }
                            }
                   int i10 = (i7 << 6) | iDecodeBase64Char3;
                   if (this._inputPtr >= this._inputEnd) {
                       loadMoreGuaranteed();
                            }
                   char[] cArr5 = this._inputBuffer;
                            int i11 = this._inputPtr;
                            this._inputPtr = i11 + 1;
                            char c5 = cArr5[i11];
                   int iDecodeBase64Char4 = base64Variant.decodeBase64Char(c5);
                   if (iDecodeBase64Char4 < 0) {
                       if (iDecodeBase64Char4 != -2) {
                           if (c5 == '\"' && !base64Variant.usesPadding()) {
                               int i12 = i10 >> 2;
                               int i13 = i + 1;
                                        bArr[i] = (byte) (i12 >> 8);
                               i3 = i13 + 1;
                                        bArr[i13] = (byte) i12;
                                        break;
                                    }
                           iDecodeBase64Char4 = _decodeBase64Escape(base64Variant, c5, 3);
                                }
                       if (iDecodeBase64Char4 == -2) {
                           int i14 = i10 >> 2;
                           int i15 = i + 1;
                                    bArr[i] = (byte) (i14 >> 8);
                           i3 = i15 + 1;
                                    bArr[i15] = (byte) i14;
                                }
                            }
                   int i16 = (i10 << 6) | iDecodeBase64Char4;
                   int i17 = i + 1;
                            bArr[i] = (byte) (i16 >> 16);
                   int i18 = i17 + 1;
                            bArr[i17] = (byte) (i16 >> 8);
                   i3 = i18 + 1;
                            bArr[i18] = (byte) i16;
                        }
                    }
           this._tokenIncomplete = false;
           if (i3 > 0) {
               int i19 = i2 + i3;
               outputStream.write(bArr, 0, i3);
               return i19;
                    }
           return i2;
                }

                
                @Override 
       public JsonToken nextToken() throws IOException {
                    JsonToken jsonToken_parseNumber;
           this._numTypesValid = 0;
           if (this._currToken == JsonToken.FIELD_NAME) {
               return _nextAfterName();
                    }
           if (this._tokenIncomplete) {
               _skipString();
                    }
           int i_skipWSOrEnd = _skipWSOrEnd();
           if (i_skipWSOrEnd < 0) {
               close();
               this._currToken = null;
                        return null;
                    }
           this._tokenInputTotal = (this._currInputProcessed + ((long) this._inputPtr)) - 1;
           this._tokenInputRow = this._currInputRow;
           this._tokenInputCol = (this._inputPtr - this._currInputRowStart) - 1;
           this._binaryValue = null;
           if (i_skipWSOrEnd == 93) {
               if (!this._parsingContext.inArray()) {
                   _reportMismatchedEndMarker(i_skipWSOrEnd, '}');
                        }
               this._parsingContext = this._parsingContext.getParent();
               JsonToken jsonToken = JsonToken.END_ARRAY;
                        this._currToken = jsonToken;
               return jsonToken;
                    }
           if (i_skipWSOrEnd == 125) {
               if (!this._parsingContext.inObject()) {
                   _reportMismatchedEndMarker(i_skipWSOrEnd, ']');
                        }
               this._parsingContext = this._parsingContext.getParent();
               JsonToken jsonToken2 = JsonToken.END_OBJECT;
                        this._currToken = jsonToken2;
               return jsonToken2;
                    }
           if (this._parsingContext.expectComma()) {
               if (i_skipWSOrEnd != 44) {
                   _reportUnexpectedChar(i_skipWSOrEnd, "was expecting comma to separate " + this._parsingContext.getTypeDesc() + " entries");
                        }
               i_skipWSOrEnd = _skipWS();
                    }
           boolean zInObject = this._parsingContext.inObject();
           if (zInObject) {
               this._parsingContext.setCurrentName(_parseName(i_skipWSOrEnd));
               this._currToken = JsonToken.FIELD_NAME;
               int i_skipWS = _skipWS();
               if (i_skipWS != 58) {
                   _reportUnexpectedChar(i_skipWS, "was expecting a colon to separate field name and value");
                        }
               i_skipWSOrEnd = _skipWS();
                    }
                    switch (i_skipWSOrEnd) {
                        case 34:
                   this._tokenIncomplete = true;
                   jsonToken_parseNumber = JsonToken.VALUE_STRING;
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
                   jsonToken_parseNumber = _parseNumber(i_skipWSOrEnd);
                            break;
                        case 91:
                   if (!zInObject) {
                       this._parsingContext = this._parsingContext.createChildArrayContext(this._tokenInputRow, this._tokenInputCol);
                            }
                   jsonToken_parseNumber = JsonToken.START_ARRAY;
                            break;
                        case 93:
                        case 125:
                   _reportUnexpectedChar(i_skipWSOrEnd, "expected a value");
                   _matchToken("true", 1);
                   jsonToken_parseNumber = JsonToken.VALUE_TRUE;
                            break;
                        case 102:
                   _matchToken("false", 1);
                   jsonToken_parseNumber = JsonToken.VALUE_FALSE;
                            break;
                        case 110:
                   _matchToken("null", 1);
                   jsonToken_parseNumber = JsonToken.VALUE_NULL;
                            break;
                        case 116:
                   _matchToken("true", 1);
                   jsonToken_parseNumber = JsonToken.VALUE_TRUE;
                            break;
                        case 123:
                   if (!zInObject) {
                       this._parsingContext = this._parsingContext.createChildObjectContext(this._tokenInputRow, this._tokenInputCol);
                            }
                   jsonToken_parseNumber = JsonToken.START_OBJECT;
                            break;
                        default:
                   jsonToken_parseNumber = _handleOddValue(i_skipWSOrEnd);
                            break;
                    }
           if (zInObject) {
               this._nextToken = jsonToken_parseNumber;
               return this._currToken;
                    }
           this._currToken = jsonToken_parseNumber;
           return jsonToken_parseNumber;
                }

       private JsonToken _nextAfterName() {
           this._nameCopied = false;
           JsonToken jsonToken = this._nextToken;
           this._nextToken = null;
           if (jsonToken == JsonToken.START_ARRAY) {
               this._parsingContext = this._parsingContext.createChildArrayContext(this._tokenInputRow, this._tokenInputCol);
                    } else if (jsonToken == JsonToken.START_OBJECT) {
               this._parsingContext = this._parsingContext.createChildObjectContext(this._tokenInputRow, this._tokenInputCol);
                    }
           this._currToken = jsonToken;
                    return jsonToken;
                }

                @Override 
       public String nextTextValue() throws IOException {
           if (this._currToken == JsonToken.FIELD_NAME) {
               this._nameCopied = false;
               JsonToken jsonToken = this._nextToken;
               this._nextToken = null;
               this._currToken = jsonToken;
               if (jsonToken == JsonToken.VALUE_STRING) {
                   if (this._tokenIncomplete) {
                       this._tokenIncomplete = false;
                       _finishString();
                            }
                   return this._textBuffer.contentsAsString();
                        }
               if (jsonToken == JsonToken.START_ARRAY) {
                   this._parsingContext = this._parsingContext.createChildArrayContext(this._tokenInputRow, this._tokenInputCol);
                            return null;
                        }
               if (jsonToken != JsonToken.START_OBJECT) {
                            return null;
                        }
               this._parsingContext = this._parsingContext.createChildObjectContext(this._tokenInputRow, this._tokenInputCol);
                        return null;
                    }
           if (nextToken() == JsonToken.VALUE_STRING) {
                        return getText();
                    }
                    return null;
                }

                @Override 
       public int nextIntValue(int i) throws IOException {
           if (this._currToken != JsonToken.FIELD_NAME) {
                        return nextToken() == JsonToken.VALUE_NUMBER_INT ? getIntValue() : i;
                    }
           this._nameCopied = false;
           JsonToken jsonToken = this._nextToken;
           this._nextToken = null;
           this._currToken = jsonToken;
           if (jsonToken == JsonToken.VALUE_NUMBER_INT) {
               return getIntValue();
                    }
           if (jsonToken == JsonToken.START_ARRAY) {
               this._parsingContext = this._parsingContext.createChildArrayContext(this._tokenInputRow, this._tokenInputCol);
               return i;
                    }
           if (jsonToken == JsonToken.START_OBJECT) {
               this._parsingContext = this._parsingContext.createChildObjectContext(this._tokenInputRow, this._tokenInputCol);
               return i;
                    }
           return i;
                }

                @Override 
       public long nextLongValue(long j) throws IOException {
           if (this._currToken != JsonToken.FIELD_NAME) {
                        return nextToken() == JsonToken.VALUE_NUMBER_INT ? getLongValue() : j;
                    }
           this._nameCopied = false;
           JsonToken jsonToken = this._nextToken;
           this._nextToken = null;
           this._currToken = jsonToken;
           if (jsonToken == JsonToken.VALUE_NUMBER_INT) {
               return getLongValue();
                    }
           if (jsonToken == JsonToken.START_ARRAY) {
               this._parsingContext = this._parsingContext.createChildArrayContext(this._tokenInputRow, this._tokenInputCol);
               return j;
                    }
           if (jsonToken == JsonToken.START_OBJECT) {
               this._parsingContext = this._parsingContext.createChildObjectContext(this._tokenInputRow, this._tokenInputCol);
               return j;
                    }
           return j;
                }

                @Override 
       public Boolean nextBooleanValue() throws IOException {
           if (this._currToken == JsonToken.FIELD_NAME) {
               this._nameCopied = false;
               JsonToken jsonToken = this._nextToken;
               this._nextToken = null;
               this._currToken = jsonToken;
               if (jsonToken == JsonToken.VALUE_TRUE) {
                   return Boolean.TRUE;
                        }
               if (jsonToken == JsonToken.VALUE_FALSE) {
                   return Boolean.FALSE;
                        }
               if (jsonToken == JsonToken.START_ARRAY) {
                   this._parsingContext = this._parsingContext.createChildArrayContext(this._tokenInputRow, this._tokenInputCol);
                            return null;
                        }
               if (jsonToken != JsonToken.START_OBJECT) {
                            return null;
                        }
               this._parsingContext = this._parsingContext.createChildObjectContext(this._tokenInputRow, this._tokenInputCol);
                        return null;
                    }
           JsonToken jsonTokenNextToken = nextToken();
           if (jsonTokenNextToken == null) {
                        return null;
                    }
           int iM524id = jsonTokenNextToken.m524id();
           if (iM524id == 9) {
                        return Boolean.TRUE;
                    }
           if (iM524id == 10) {
                        return Boolean.FALSE;
                    }
                    return null;
                }

                       protected JsonToken _parseNumber(int ch) throws IOException {
           if (ch == '-') {
               return _parseNumber2(true);
                    }
           this._inputPtr--;
           return _parseNumber2(false);
                }

                       private JsonToken _parseNumber2(boolean z) throws IOException {
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
           char[] cArrEmptyAndGetCurrentSegment = this._textBuffer.emptyAndGetCurrentSegment();
           if (z) {
               cArrEmptyAndGetCurrentSegment[0] = '-';
                        i = 1;
                    } else {
                        i = 0;
                    }
           if (this._inputPtr < this._inputEnd) {
                        char[] cArr2 = this._inputBuffer;
                        int i12 = this._inputPtr;
                        this._inputPtr = i12 + 1;
                        nextChar = cArr2[i12];
                    } else {
               nextChar = getNextChar("No digit following minus sign");
                    }
           if (nextChar == '0') {
               nextChar = _verifyNoLeadingZeroes();
                    }
                    int i13 = 0;
                    char c5 = nextChar;
                    char[] cArrFinishCurrentSegment2 = cArrEmptyAndGetCurrentSegment;
                    char c6 = c5;
                    while (true) {
               if (c6 >= '0' && c6 <= '9') {
                   i13++;
                   if (i >= cArrFinishCurrentSegment2.length) {
                       cArrFinishCurrentSegment2 = this._textBuffer.finishCurrentSegment();
                                i = 0;
                            }
                   int i14 = i + 1;
                            cArrFinishCurrentSegment2[i] = c6;
                   if (this._inputPtr >= this._inputEnd && !loadMore()) {
                                z2 = true;
                                c = 0;
                                i2 = i13;
                                cArr = cArrFinishCurrentSegment2;
                                i3 = i14;
                                break;
                            }
                   char[] cArr3 = this._inputBuffer;
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
          if (i2 == 0) {
              reportInvalidNumber("Missing integer part (next char " + _getCharDesc(c) + ")");
                    }
          if (c == '.') {
              int i16 = i3 + 1;
                        cArr[i3] = c;
                        char[] cArrFinishCurrentSegment3 = cArr;
                        int i17 = i16;
                        char c7 = c;
                        int i18 = 0;
                        while (true) {
                  if (this._inputPtr >= this._inputEnd && !loadMore()) {
                                c2 = c7;
                                z5 = true;
                                break;
                            }
                  char[] cArr4 = this._inputBuffer;
                            int i19 = this._inputPtr;
                            this._inputPtr = i19 + 1;
                            c7 = cArr4[i19];
                  if (c7 < '0') {
                                c2 = c7;
                                z5 = z2;
                                break;
                            }
                            if (c7 > '9') {
                                c2 = c7;
                                z5 = z2;
                                break;
                            }
                  i18++;
                  if (i17 >= cArrFinishCurrentSegment3.length) {
                      cArrFinishCurrentSegment3 = this._textBuffer.finishCurrentSegment();
                                i10 = 0;
                            } else {
                                i10 = i17;
                            }
                  i17 = i10 + 1;
                            cArrFinishCurrentSegment3[i10] = c7;
                        }
              if (i18 == 0) {
                  reportUnexpectedNumberChar(c2, "Decimal point not followed by a digit");
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
          if (c2 == 'e' || c2 == 'E') {
              if (i5 >= cArrFinishCurrentSegment.length) {
                  cArrFinishCurrentSegment = this._textBuffer.finishCurrentSegment();
                            i5 = 0;
                        }
              int i20 = i5 + 1;
                        cArrFinishCurrentSegment[i5] = c2;
              if (this._inputPtr < this._inputEnd) {
                            char[] cArr5 = this._inputBuffer;
                            int i21 = this._inputPtr;
                            this._inputPtr = i21 + 1;
                            nextChar2 = cArr5[i21];
                        } else {
                  nextChar2 = getNextChar("expected a digit for number exponent");
                        }
              if (nextChar2 == '-' || nextChar2 == '+') {
                  if (i20 >= cArrFinishCurrentSegment.length) {
                      cArrFinishCurrentSegment = this._textBuffer.finishCurrentSegment();
                                i6 = 0;
                            } else {
                                i6 = i20;
                            }
                  int i22 = i6 + 1;
                            cArrFinishCurrentSegment[i6] = nextChar2;
                  if (this._inputPtr < this._inputEnd) {
                                char[] cArr6 = this._inputBuffer;
                                int i23 = this._inputPtr;
                                this._inputPtr = i23 + 1;
                                nextChar3 = cArr6[i23];
                            } else {
                      nextChar3 = getNextChar("expected a digit for number exponent");
                            }
                            char c8 = nextChar3;
                            i7 = i22;
                            c3 = c8;
                        } else {
                            i7 = i20;
                            c3 = nextChar2;
                        }
                        while (true) {
                  if (c3 <= '9' && c3 >= '0') {
                      i11++;
                      if (i7 >= cArrFinishCurrentSegment.length) {
                          cArrFinishCurrentSegment = this._textBuffer.finishCurrentSegment();
                                    i7 = 0;
                                }
                      int i24 = i7 + 1;
                                cArrFinishCurrentSegment[i7] = c3;
                      if (this._inputPtr >= this._inputEnd && !loadMore()) {
                                    i11 = i11;
                                    z4 = true;
                                    i8 = i24;
                                    break;
                                }
                      char[] cArr7 = this._inputBuffer;
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
              if (i11 == 0) {
                  reportUnexpectedNumberChar(c3, "Exponent indicator not followed by a digit");
                        }
                        i9 = i8;
                        c4 = c3;
                    } else {
                        c4 = c2;
                        i9 = i5;
                        z4 = z3;
                    }
          if (!z4) {
              this._inputPtr--;
              if (this._parsingContext.inRoot()) {
                  _verifyRootSpace(c4);
                        }
                    }
          this._textBuffer.setCurrentLength(i9);
          return reset(z, i2, i4, i11);
                }

      private char _verifyNoLeadingZeroes() throws IOException {
          if (this._inputPtr >= this._inputEnd && !loadMore()) {
                        return '0';
                    }
          char c = this._inputBuffer[this._inputPtr];
          if (c < '0' || c > '9') {
                        return '0';
                    }
          if (!isEnabled(JsonParser.Feature.ALLOW_NUMERIC_LEADING_ZEROS)) {
              reportInvalidNumber("Leading zeroes not allowed");
                    }
          this._inputPtr++;
          if (c == '0') {
              do {
                  if (this._inputPtr < this._inputEnd || loadMore()) {
                      c = this._inputBuffer[this._inputPtr];
                      if (c < '0' || c > '9') {
                                    return '0';
                                }
                      this._inputPtr++;
                            } else {
                      return c;
                            }
              } while (c == '0');
              return c;
                    }
          return c;
                }

                      protected JsonToken _handleInvalidNumberStart(int ch, boolean negative) throws IOException {
                    if (ch == 'I') {
          _matchToken("Infinity", 1);
                        if (isEnabled(JsonParser.Feature.ALLOW_NON_NUMERIC_NUMBERS)) {
                            return resetAsNaN(negative ? "-Infinity" : "Infinity",
                                    negative ? Double.NEGATIVE_INFINITY : Double.POSITIVE_INFINITY);
                        }
          _reportError("Non-standard token '" + (negative ? "-Infinity" : "Infinity")
                                + "': enable JsonParser.Feature.ALLOW_NON_NUMERIC_NUMBERS to allow");
                    }
                    if (ch == 'N' && !negative) {
          _matchToken("NaN", 1);
                        if (isEnabled(JsonParser.Feature.ALLOW_NON_NUMERIC_NUMBERS)) {
                            return resetAsNaN("NaN", Double.NaN);
                        }
          _reportError("Non-standard token 'NaN': enable JsonParser.Feature.ALLOW_NON_NUMERIC_NUMBERS to allow");
                    }
                    if (Character.isJavaIdentifierStart(ch)) {
                        _reportInvalidToken("" + ((char) ch), "('true', 'false' or 'null')");
                    }
                    _reportUnexpectedChar(ch, "expected a valid value (number, String, array, object, 'true', 'false' or 'null')");
                    return null;
                }

                      private final void _verifyRootSpace(int i) throws IOException {
          this._inputPtr++;
                    switch (i) {
                        case 9:
                        case 32:
                            break;
                        case 10:
                  this._currInputRow++;
                  this._currInputRowStart = this._inputPtr;
                            break;
                        case 13:
                  _skipCR();
                            break;
                        default:
                  _reportMissingRootWS(i);
                            break;
                    }
                }

      protected String _parseName(int i) throws IOException {
          if (i != 34) {
              return _handleOddName(i);
                    }
          int i2 = this._inputPtr;
          int i3 = this._hashSeed;
          int i4 = this._inputEnd;
          if (i2 < i4) {
              int[] iArr = _icLatin1;
              int length = iArr.length;
              do {
                  char c = this._inputBuffer[i2];
                  if (c < length && iArr[c] != 0) {
                      if (c != '\"') {
                                    break;
                                }
                      int i5 = this._inputPtr;
                      this._inputPtr = i2 + 1;
                      return this._symbols.findSymbol(this._inputBuffer, i5, i2 - i5, i3);
                            }
                  i3 = (i3 * 33) + c;
                  i2++;
              } while (i2 < i4);
                    }
          int i6 = this._inputPtr;
          this._inputPtr = i2;
          return _parseName2(i6, i3, 34);
                }

      private String _parseName2(int i, int i2, int i3) throws IOException {
                    char c_decodeEscaped;
          this._textBuffer.resetWithShared(this._inputBuffer, i, this._inputPtr - i);
          char[] currentSegment = this._textBuffer.getCurrentSegment();
          int currentSegmentSize = this._textBuffer.getCurrentSegmentSize();
                    while (true) {
              if (this._inputPtr >= this._inputEnd && !loadMore()) {
                  _reportInvalidEOF(": was expecting closing '" + ((char) i3) + "' for name");
                        }
              char[] cArr = this._inputBuffer;
                        int i4 = this._inputPtr;
                        this._inputPtr = i4 + 1;
                        char c = cArr[i4];
              if (c > '\\') {
                            c_decodeEscaped = c;
                        } else if (c == '\\') {
                  c_decodeEscaped = _decodeEscaped();
                        } else {
                  if (c <= i3) {
                      if (c != i3) {
                          if (c < ' ') {
                              _throwUnquotedSpace(c, "name");
                                    }
                                } else {
                          this._textBuffer.setCurrentLength(currentSegmentSize);
                          TextBuffer textBuffer = this._textBuffer;
                          return this._symbols.findSymbol(textBuffer.getTextBuffer(), textBuffer.getTextOffset(), textBuffer.size(), i2);
                                }
                            }
                            c_decodeEscaped = c;
                        }
              i2 = (i2 * 33) + c;
              int i5 = currentSegmentSize + 1;
                        currentSegment[currentSegmentSize] = c_decodeEscaped;
              if (i5 >= currentSegment.length) {
                  currentSegment = this._textBuffer.finishCurrentSegment();
                  currentSegmentSize = 0;
                        } else {
                            currentSegmentSize = i5;
                        }
                    }
                }

      protected String _handleOddName(int i) throws IOException {
                    boolean zIsJavaIdentifierPart;
          if (i == 39 && isEnabled(JsonParser.Feature.ALLOW_SINGLE_QUOTES)) {
              return _parseAposName();
                    }
          if (!isEnabled(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES)) {
              _reportUnexpectedChar(i, "was expecting double-quote to start field name");
                    }
          int[] inputCodeLatin1JsNames = CharTypes.getInputCodeLatin1JsNames();
          int length = inputCodeLatin1JsNames.length;
          if (i < length) {
              zIsJavaIdentifierPart = inputCodeLatin1JsNames[i] == 0;
                    } else {
              zIsJavaIdentifierPart = Character.isJavaIdentifierPart((char) i);
                    }
          if (!zIsJavaIdentifierPart) {
              _reportUnexpectedChar(i, "was expecting either valid name character (for unquoted name) or double-quote (for quoted) to start field name");
                    }
          int i2 = this._inputPtr;
          int i3 = this._hashSeed;
          int i4 = this._inputEnd;
          if (i2 < i4) {
              do {
                  char c = this._inputBuffer[i2];
                  if (c < length) {
                      if (inputCodeLatin1JsNames[c] != 0) {
                          int i5 = this._inputPtr - 1;
                          this._inputPtr = i2;
                          return this._symbols.findSymbol(this._inputBuffer, i5, i2 - i5, i3);
                                }
                            } else if (!Character.isJavaIdentifierPart(c)) {
                      int i6 = this._inputPtr - 1;
                      this._inputPtr = i2;
                      return this._symbols.findSymbol(this._inputBuffer, i6, i2 - i6, i3);
                            }
                  i3 = (i3 * 33) + c;
                  i2++;
              } while (i2 < i4);
                    }
          int i7 = this._inputPtr - 1;
          this._inputPtr = i2;
          return _handleOddName2(i7, i3, inputCodeLatin1JsNames);
                }

      protected String _parseAposName() throws IOException {
          int i = this._inputPtr;
          int i2 = this._hashSeed;
          int i3 = this._inputEnd;
          if (i < i3) {
              int[] iArr = _icLatin1;
              int length = iArr.length;
              do {
                  char c = this._inputBuffer[i];
                  if (c == '\'') {
                      int i4 = this._inputPtr;
                      this._inputPtr = i + 1;
                      return this._symbols.findSymbol(this._inputBuffer, i4, i - i4, i2);
                            }
                  if (c < length && iArr[c] != 0) {
                                break;
                            }
                  i2 = (i2 * 33) + c;
                  i++;
              } while (i < i3);
                    }
          int i5 = this._inputPtr;
          this._inputPtr = i;
          return _parseName2(i5, i2, 39);
                }

      protected JsonToken _handleOddValue(int i) throws IOException {
                    switch (i) {
                        case 39:
                  if (isEnabled(JsonParser.Feature.ALLOW_SINGLE_QUOTES)) {
                      return _handleApos();
                            }
                            break;
                        case 43:
                  if (this._inputPtr >= this._inputEnd && !loadMore()) {
                      _reportInvalidEOFInValue();
                            }
                  char[] cArr = this._inputBuffer;
                            int i2 = this._inputPtr;
                            this._inputPtr = i2 + 1;
                            return _handleInvalidNumberStart(cArr[i2], false);
                        case 73:
                  _matchToken("Infinity", 1);
                  if (isEnabled(JsonParser.Feature.ALLOW_NON_NUMERIC_NUMBERS)) {
                      return resetAsNaN("Infinity", Double.POSITIVE_INFINITY);
                            }
                  _reportError("Non-standard token 'Infinity': enable JsonParser.Feature.ALLOW_NON_NUMERIC_NUMBERS to allow");
                            break;
                            break;
                        case 78:
                  _matchToken("NaN", 1);
                  if (isEnabled(JsonParser.Feature.ALLOW_NON_NUMERIC_NUMBERS)) {
                      return resetAsNaN("NaN", Double.NaN);
                            }
                  _reportError("Non-standard token 'NaN': enable JsonParser.Feature.ALLOW_NON_NUMERIC_NUMBERS to allow");
                            break;
                            break;
                    }
          if (Character.isJavaIdentifierStart(i)) {
              _reportInvalidToken("" + ((char) i), "('true', 'false' or 'null')");
                    }
          _reportUnexpectedChar(i, "expected a valid value (number, String, array, object, 'true', 'false' or 'null')");
          return null;
                }

      protected JsonToken _handleApos() throws IOException {
          char[] cArrEmptyAndGetCurrentSegment = this._textBuffer.emptyAndGetCurrentSegment();
          int currentSegmentSize = this._textBuffer.getCurrentSegmentSize();
                    while (true) {
              if (this._inputPtr >= this._inputEnd && !loadMore()) {
                  _reportInvalidEOF(": was expecting closing quote for a string value");
                        }
              char[] cArr = this._inputBuffer;
                        int i = this._inputPtr;
                        this._inputPtr = i + 1;
                        char c_decodeEscaped = cArr[i];
              if (c_decodeEscaped <= '\\') {
                  if (c_decodeEscaped == '\\') {
                      c_decodeEscaped = _decodeEscaped();
                            } else if (c_decodeEscaped <= '\'') {
                      if (c_decodeEscaped != '\'') {
                          if (c_decodeEscaped < ' ') {
                              _throwUnquotedSpace(c_decodeEscaped, "string value");
                                    }
                                } else {
                          this._textBuffer.setCurrentLength(currentSegmentSize);
                          return JsonToken.VALUE_STRING;
                                }
                            }
                        }
              if (currentSegmentSize >= cArrEmptyAndGetCurrentSegment.length) {
                  cArrEmptyAndGetCurrentSegment = this._textBuffer.finishCurrentSegment();
                  currentSegmentSize = 0;
                        }
                        int i2 = currentSegmentSize;
              currentSegmentSize = i2 + 1;
                        cArrEmptyAndGetCurrentSegment[i2] = c_decodeEscaped;
                    }
                }

                
                
      private String _handleOddName2(int i, int i2, int[] iArr) throws IOException {
                    int i3;
          this._textBuffer.resetWithShared(this._inputBuffer, i, this._inputPtr - i);
          char[] currentSegment = this._textBuffer.getCurrentSegment();
          int currentSegmentSize = this._textBuffer.getCurrentSegmentSize();
          int length = iArr.length;
                    while (true) {
              if (this._inputPtr >= this._inputEnd && !loadMore()) {
                            break;
                        }
              char c = this._inputBuffer[this._inputPtr];
              if (c <= length) {
                  if (iArr[c] != 0) {
                                break;
                            }
                  this._inputPtr++;
                  i2 = (i2 * 33) + c;
                  i3 = currentSegmentSize + 1;
                            currentSegment[currentSegmentSize] = c;
                  if (i3 >= currentSegment.length) {
                      currentSegment = this._textBuffer.finishCurrentSegment();
                      currentSegmentSize = 0;
                            } else {
                                currentSegmentSize = i3;
                            }
                        } else {
                  if (!Character.isJavaIdentifierPart(c)) {
                                break;
                            }
                  this._inputPtr++;
                  i2 = (i2 * 33) + c;
                  i3 = currentSegmentSize + 1;
                            currentSegment[currentSegmentSize] = c;
                  if (i3 >= currentSegment.length) {
                      currentSegment = this._textBuffer.finishCurrentSegment();
                      currentSegmentSize = 0;
                            } else {
                                currentSegmentSize = i3;
                            }
                        }
                    }
          this._textBuffer.setCurrentLength(currentSegmentSize);
          TextBuffer textBuffer = this._textBuffer;
          return this._symbols.findSymbol(textBuffer.getTextBuffer(), textBuffer.getTextOffset(), textBuffer.size(), i2);
                }

                @Override 
      protected void _finishString() throws IOException {
          int i = this._inputPtr;
          int i2 = this._inputEnd;
          if (i < i2) {
              int[] iArr = _icLatin1;
              int length = iArr.length;
              do {
                  char c = this._inputBuffer[i];
                  if (c < length && iArr[c] != 0) {
                      if (c != '\"') {
                                    break;
                                }
                      this._textBuffer.resetWithShared(this._inputBuffer, this._inputPtr, i - this._inputPtr);
                      this._inputPtr = i + 1;
                      return;
                            }
                  i++;
              } while (i < i2);
                    }
          this._textBuffer.resetWithCopy(this._inputBuffer, this._inputPtr, i - this._inputPtr);
          this._inputPtr = i;
          _finishString2();
                }

      protected void _finishString2() throws IOException {
          char[] currentSegment = this._textBuffer.getCurrentSegment();
          int currentSegmentSize = this._textBuffer.getCurrentSegmentSize();
                    while (true) {
              if (this._inputPtr >= this._inputEnd && !loadMore()) {
                  _reportInvalidEOF(": was expecting closing quote for a string value");
                        }
              char[] cArr = this._inputBuffer;
                        int i = this._inputPtr;
                        this._inputPtr = i + 1;
                        char c_decodeEscaped = cArr[i];
              if (c_decodeEscaped <= '\\') {
                  if (c_decodeEscaped == '\\') {
                      c_decodeEscaped = _decodeEscaped();
                            } else if (c_decodeEscaped <= '\"') {
                      if (c_decodeEscaped != '\"') {
                          if (c_decodeEscaped < ' ') {
                              _throwUnquotedSpace(c_decodeEscaped, "string value");
                                    }
                                } else {
                          this._textBuffer.setCurrentLength(currentSegmentSize);
                          return;
                                }
                            }
                        }
              if (currentSegmentSize >= currentSegment.length) {
                  currentSegment = this._textBuffer.finishCurrentSegment();
                  currentSegmentSize = 0;
                        }
                        int i2 = currentSegmentSize;
              currentSegmentSize = i2 + 1;
                        currentSegment[i2] = c_decodeEscaped;
                    }
                }

      protected void _skipString() throws IOException {
          this._tokenIncomplete = false;
          int i = this._inputPtr;
          int i2 = this._inputEnd;
          char[] cArr = this._inputBuffer;
                    while (true) {
              if (i >= i2) {
                  this._inputPtr = i;
                  if (!loadMore()) {
                      _reportInvalidEOF(": was expecting closing quote for a string value");
                            }
                  i = this._inputPtr;
                  i2 = this._inputEnd;
                        }
              int i3 = i + 1;
                        char c = cArr[i];
              if (c <= '\\') {
                  if (c == '\\') {
                      this._inputPtr = i3;
                      _decodeEscaped();
                      i = this._inputPtr;
                      i2 = this._inputEnd;
                            } else if (c <= '\"') {
                      if (c == '\"') {
                          this._inputPtr = i3;
                          return;
                                } else if (c < ' ') {
                          this._inputPtr = i3;
                          _throwUnquotedSpace(c, "string value");
                                }
                            }
                        }
                        i = i3;
                    }
                }

      protected void _skipCR() throws IOException {
          if ((this._inputPtr < this._inputEnd || loadMore()) && this._inputBuffer[this._inputPtr] == '\n') {
              this._inputPtr++;
                    }
          this._currInputRow++;
          this._currInputRowStart = this._inputPtr;
                }

      private int _skipWS() throws IOException {
          int[] iArr = _icWS;
                    while (true) {
              if (this._inputPtr < this._inputEnd || loadMore()) {
                  char[] cArr = this._inputBuffer;
                            int i = this._inputPtr;
                            this._inputPtr = i + 1;
                            char c = cArr[i];
                  if (c < '@') {
                                switch (iArr[c]) {
                                    case -1:
                              _throwInvalidSpace(c);
                                        break;
                                    case 0:
                                        break;
                                    case 10:
                              this._currInputRow++;
                              this._currInputRowStart = this._inputPtr;
                                        break;
                                    case 13:
                              _skipCR();
                                        break;
                                    case 35:
                              if (!_skipYAMLComment()) {
                                        }
                                        break;
                                    case 47:
                              _skipComment();
                                        break;
                                }
                            }
                  return c;
                        }
              throw _constructError("Unexpected end-of-input within/between " + this._parsingContext.getTypeDesc() + " entries");
                    }
                }

      private int _skipWSOrEnd() throws IOException {
          int[] iArr = _icWS;
                    while (true) {
              if (this._inputPtr < this._inputEnd || loadMore()) {
                  char[] cArr = this._inputBuffer;
                            int i = this._inputPtr;
                            this._inputPtr = i + 1;
                            char c = cArr[i];
                  if (c < '@') {
                                switch (iArr[c]) {
                                    case -1:
                              _throwInvalidSpace(c);
                              return c;
                                    case 0:
                              return c;
                                    case 10:
                              this._currInputRow++;
                              this._currInputRowStart = this._inputPtr;
                                        break;
                                    case 13:
                              _skipCR();
                                        break;
                                    case 35:
                              if (!_skipYAMLComment()) {
                                  return c;
                                        }
                                        break;
                                        break;
                                    case 47:
                              _skipComment();
                                        break;
                                }
                            } else {
                      return c;
                            }
                        } else {
                  _handleEOF();
                  return -1;
                        }
                    }
                }

      private void _skipComment() throws IOException {
          if (!isEnabled(JsonParser.Feature.ALLOW_COMMENTS)) {
              _reportUnexpectedChar(47, "maybe a (non-standard) comment? (not recognized as one since Feature 'ALLOW_COMMENTS' not enabled for parser)");
                    }
          if (this._inputPtr >= this._inputEnd && !loadMore()) {
              _reportInvalidEOF(" in a comment");
                    }
          char[] cArr = this._inputBuffer;
                    int i = this._inputPtr;
                    this._inputPtr = i + 1;
                    char c = cArr[i];
          if (c == '/') {
              _skipLine();
                    } else if (c == '*') {
              _skipCComment();
                    } else {
              _reportUnexpectedChar(c, "was expecting either '*' or '/' for a comment");
                    }
                }

      private void _skipCComment() throws IOException {
                    while (true) {
              if (this._inputPtr >= this._inputEnd && !loadMore()) {
                            break;
                        }
              char[] cArr = this._inputBuffer;
                        int i = this._inputPtr;
                        this._inputPtr = i + 1;
                        char c = cArr[i];
              if (c <= '*') {
                  if (c == '*') {
                      if (this._inputPtr >= this._inputEnd && !loadMore()) {
                                    break;
                                } else if (this._inputBuffer[this._inputPtr] == '/') {
                          this._inputPtr++;
                          return;
                                }
                            } else if (c < ' ') {
                      if (c == '\n') {
                          this._currInputRow++;
                          this._currInputRowStart = this._inputPtr;
                                } else if (c == '\r') {
                          _skipCR();
                                } else if (c != '\t') {
                          _throwInvalidSpace(c);
                                }
                            }
                        }
                    }
          _reportInvalidEOF(" in a comment");
                }

      private boolean _skipYAMLComment() throws IOException {
          if (!isEnabled(JsonParser.Feature.ALLOW_YAML_COMMENTS)) {
              return false;
                    }
          _skipLine();
          return true;
                }

      private void _skipLine() throws IOException {
                    while (true) {
              if (this._inputPtr < this._inputEnd || loadMore()) {
                  char[] cArr = this._inputBuffer;
                            int i = this._inputPtr;
                            this._inputPtr = i + 1;
                            char c = cArr[i];
                  if (c < ' ') {
                      if (c == '\n') {
                          this._currInputRow++;
                          this._currInputRowStart = this._inputPtr;
                          return;
                                } else if (c == '\r') {
                          _skipCR();
                          return;
                                } else if (c != '\t') {
                          _throwInvalidSpace(c);
                                }
                            }
                        } else {
                  return;
                        }
                    }
                }

                @Override 
      protected char _decodeEscaped() throws IOException {
                    int i = 0;
          if (this._inputPtr >= this._inputEnd && !loadMore()) {
              _reportInvalidEOF(" in character escape sequence");
                    }
          char[] cArr = this._inputBuffer;
                    int i2 = this._inputPtr;
                    this._inputPtr = i2 + 1;
                    char c = cArr[i2];
                    switch (c) {
                        case '\"':
                        case '/':
                        case '\\':
                  return c;
                        case 'b':
                  return '\b';
                        case 'f':
                  return '\f';
                        case 'n':
                  return '\n';
                        case 'r':
                  return '\r';
                        case 't':
                  return '\t';
                        case 'u':
                  for (int i3 = 0; i3 < 4; i3++) {
                      if (this._inputPtr >= this._inputEnd && !loadMore()) {
                          _reportInvalidEOF(" in character escape sequence");
                                }
                      char[] cArr2 = this._inputBuffer;
                                int i4 = this._inputPtr;
                                this._inputPtr = i4 + 1;
                                char c2 = cArr2[i4];
                      int iCharToHex = CharTypes.charToHex(c2);
                      if (iCharToHex < 0) {
                          _reportUnexpectedChar(c2, "expected a hex-digit for character escape sequence");
                                }
                      i = (i << 4) | iCharToHex;
                            }
                  return (char) i;
                        default:
                  return _handleUnrecognizedCharacterEscape(c);
                    }
                }

      protected void _matchToken(String str, int i) throws IOException {
                    char c;
          int length = str.length();
                    do {
              if (this._inputPtr >= this._inputEnd && !loadMore()) {
                  _reportInvalidToken(str.substring(0, i));
                        }
              if (this._inputBuffer[this._inputPtr] != str.charAt(i)) {
                  _reportInvalidToken(str.substring(0, i));
                        }
              this._inputPtr++;
              i++;
                    } while (i < length);
          if ((this._inputPtr < this._inputEnd || loadMore()) && (c = this._inputBuffer[this._inputPtr]) >= '0' && c != ']' && c != '}' && Character.isJavaIdentifierPart(c)) {
              _reportInvalidToken(str.substring(0, i));
                    }
                }

      protected byte[] _decodeBase64(Base64Variant base64Variant) throws IOException {
          ByteArrayBuilder byteArrayBuilder_getByteArrayBuilder = _getByteArrayBuilder();
                    while (true) {
              if (this._inputPtr >= this._inputEnd) {
                  loadMoreGuaranteed();
                        }
              char[] cArr = this._inputBuffer;
                        int i = this._inputPtr;
                        this._inputPtr = i + 1;
                        char c = cArr[i];
              if (c > ' ') {
                  int iDecodeBase64Char = base64Variant.decodeBase64Char(c);
                  if (iDecodeBase64Char < 0) {
                      if (c == '\"') {
                          return byteArrayBuilder_getByteArrayBuilder.toByteArray();
                                }
                      iDecodeBase64Char = _decodeBase64Escape(base64Variant, c, 0);
                      if (iDecodeBase64Char < 0) {
                                    continue;
                                }
                            }
                  if (this._inputPtr >= this._inputEnd) {
                      loadMoreGuaranteed();
                            }
                  char[] cArr2 = this._inputBuffer;
                            int i2 = this._inputPtr;
                            this._inputPtr = i2 + 1;
                            char c2 = cArr2[i2];
                  int iDecodeBase64Char2 = base64Variant.decodeBase64Char(c2);
                  if (iDecodeBase64Char2 < 0) {
                      iDecodeBase64Char2 = _decodeBase64Escape(base64Variant, c2, 1);
                            }
                  int i3 = iDecodeBase64Char2 | (iDecodeBase64Char << 6);
                  if (this._inputPtr >= this._inputEnd) {
                      loadMoreGuaranteed();
                            }
                  char[] cArr3 = this._inputBuffer;
                            int i4 = this._inputPtr;
                            this._inputPtr = i4 + 1;
                            char c3 = cArr3[i4];
                  int iDecodeBase64Char3 = base64Variant.decodeBase64Char(c3);
                  if (iDecodeBase64Char3 < 0) {
                      if (iDecodeBase64Char3 != -2) {
                          if (c3 == '\"' && !base64Variant.usesPadding()) {
                              byteArrayBuilder_getByteArrayBuilder.append(i3 >> 4);
                              return byteArrayBuilder_getByteArrayBuilder.toByteArray();
                                    }
                          iDecodeBase64Char3 = _decodeBase64Escape(base64Variant, c3, 2);
                                }
                      if (iDecodeBase64Char3 == -2) {
                          if (this._inputPtr >= this._inputEnd) {
                              loadMoreGuaranteed();
                                    }
                          char[] cArr4 = this._inputBuffer;
                                    int i5 = this._inputPtr;
                                    this._inputPtr = i5 + 1;
                                    char c4 = cArr4[i5];
                          if (!base64Variant.usesPaddingChar(c4)) {
                              throw reportInvalidBase64Char(base64Variant, c4, 3, "expected padding character '" + base64Variant.getPaddingChar() + "'");
                                    }
                          byteArrayBuilder_getByteArrayBuilder.append(i3 >> 4);
                                }
                            }
                  int i6 = (i3 << 6) | iDecodeBase64Char3;
                  if (this._inputPtr >= this._inputEnd) {
                      loadMoreGuaranteed();
                            }
                  char[] cArr5 = this._inputBuffer;
                            int i7 = this._inputPtr;
                            this._inputPtr = i7 + 1;
                            char c5 = cArr5[i7];
                  int iDecodeBase64Char4 = base64Variant.decodeBase64Char(c5);
                  if (iDecodeBase64Char4 < 0) {
                      if (iDecodeBase64Char4 != -2) {
                          if (c5 == '\"' && !base64Variant.usesPadding()) {
                              byteArrayBuilder_getByteArrayBuilder.appendTwoBytes(i6 >> 2);
                              return byteArrayBuilder_getByteArrayBuilder.toByteArray();
                                    }
                          iDecodeBase64Char4 = _decodeBase64Escape(base64Variant, c5, 3);
                                }
                      if (iDecodeBase64Char4 == -2) {
                          byteArrayBuilder_getByteArrayBuilder.appendTwoBytes(i6 >> 2);
                                }
                            }
                  byteArrayBuilder_getByteArrayBuilder.appendThreeBytes(iDecodeBase64Char4 | (i6 << 6));
                        }
                    }
                }

      protected void _reportInvalidToken(String str) throws IOException {
          _reportInvalidToken(str, "'null', 'true', 'false' or NaN");
                }

      protected void _reportInvalidToken(String str, String str2) throws IOException {
          StringBuilder sb = new StringBuilder(str);
                    while (true) {
              if (this._inputPtr >= this._inputEnd && !loadMore()) {
                            break;
                        }
              char c = this._inputBuffer[this._inputPtr];
              if (!Character.isJavaIdentifierPart(c)) {
                            break;
                        }
              this._inputPtr++;
              sb.append(c);
                    }
          _reportError("Unrecognized token '" + sb.toString() + "': was expecting " + str2);
                }
            }
