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
            
            
   public class UTF8StreamJsonParser extends ParserBase {
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

       public UTF8StreamJsonParser(IOContext iOContext, int i, InputStream inputStream, ObjectCodec objectCodec, BytesToNameCanonicalizer bytesToNameCanonicalizer, byte[] bArr, int i2, int i3, boolean z) {
           super(iOContext, i);
            this._quadBuffer = new int[16];
            this._tokenIncomplete = false;
           this._inputStream = inputStream;
           this._objectCodec = objectCodec;
           this._symbols = bytesToNameCanonicalizer;
           this._inputBuffer = bArr;
           this._inputPtr = i2;
           this._inputEnd = i3;
           this._currInputRowStart = i2;
           this._currInputProcessed = -i2;
           this._bufferRecyclable = z;
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
       public int releaseBuffered(OutputStream outputStream) throws IOException {
           int i = this._inputEnd - this._inputPtr;
           if (i < 1) {
               return 0;
                    }
           outputStream.write(this._inputBuffer, this._inputPtr, i);
           return i;
                }

                @Override 
       public Object getInputSource() {
           return this._inputStream;
                }

                @Override 
       protected final boolean loadMore() throws IOException {
           this._currInputProcessed += (long) this._inputEnd;
           this._currInputRowStart -= this._inputEnd;
           if (this._inputStream == null) {
                        return false;
                    }
           int i = this._inputStream.read(this._inputBuffer, 0, this._inputBuffer.length);
           if (i > 0) {
               this._inputPtr = 0;
               this._inputEnd = i;
               return true;
                    }
           _closeInput();
           if (i == 0) {
               throw new IOException("InputStream.read() returned 0 characters when trying to read " + this._inputBuffer.length + " bytes");
                    }
                    return false;
                }

       protected final boolean _loadToHaveAtLeast(int i) throws IOException {
           if (this._inputStream == null) {
                        return false;
                    }
           int i2 = this._inputEnd - this._inputPtr;
           if (i2 > 0 && this._inputPtr > 0) {
               this._currInputProcessed += (long) this._inputPtr;
               this._currInputRowStart -= this._inputPtr;
               System.arraycopy(this._inputBuffer, this._inputPtr, this._inputBuffer, 0, i2);
               this._inputEnd = i2;
                    } else {
               this._inputEnd = 0;
                    }
           this._inputPtr = 0;
           while (this._inputEnd < i) {
               int i3 = this._inputStream.read(this._inputBuffer, this._inputEnd, this._inputBuffer.length - this._inputEnd);
               if (i3 < 1) {
                   _closeInput();
                   if (i3 == 0) {
                       throw new IOException("InputStream.read() returned 0 characters when trying to read " + i2 + " bytes");
                            }
                            return false;
                        }
               this._inputEnd = i3 + this._inputEnd;
                    }
                    return true;
                }

                @Override 
       protected void _closeInput() throws IOException {
           if (this._inputStream != null) {
               if (this._ioContext.isResourceManaged() || isEnabled(JsonParser.Feature.AUTO_CLOSE_SOURCE)) {
                   this._inputStream.close();
                        }
               this._inputStream = null;
                    }
                }

                @Override 
       protected void _releaseBuffers() throws IOException {
                    byte[] bArr;
           super._releaseBuffers();
           this._symbols.release();
           if (this._bufferRecyclable && (bArr = this._inputBuffer) != null) {
               this._inputBuffer = null;
               this._ioContext.releaseReadIOBuffer(bArr);
                    }
                }

                @Override 
       public String getText() throws IOException {
           if (this._currToken != JsonToken.VALUE_STRING) {
                        return _getText2(this._currToken);
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

       protected final String _getText2(JsonToken jsonToken) {
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
               byte[] bArr2 = this._inputBuffer;
                        int i4 = this._inputPtr;
                        this._inputPtr = i4 + 1;
                        int i5 = bArr2[i4] & Constants.UNKNOWN;
               if (i5 > 32) {
                   int iDecodeBase64Char = base64Variant.decodeBase64Char(i5);
                   if (iDecodeBase64Char < 0) {
                       if (i5 == 34) {
                                    break;
                                }
                       iDecodeBase64Char = _decodeBase64Escape(base64Variant, i5, 0);
                       if (iDecodeBase64Char < 0) {
                                    continue;
                                }
                            }
                            int i6 = iDecodeBase64Char;
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
                   byte[] bArr3 = this._inputBuffer;
                            int i7 = this._inputPtr;
                            this._inputPtr = i7 + 1;
                            int i8 = bArr3[i7] & Constants.UNKNOWN;
                   int iDecodeBase64Char2 = base64Variant.decodeBase64Char(i8);
                   if (iDecodeBase64Char2 < 0) {
                       iDecodeBase64Char2 = _decodeBase64Escape(base64Variant, i8, 1);
                            }
                   int i9 = (i6 << 6) | iDecodeBase64Char2;
                   if (this._inputPtr >= this._inputEnd) {
                       loadMoreGuaranteed();
                            }
                   byte[] bArr4 = this._inputBuffer;
                            int i10 = this._inputPtr;
                            this._inputPtr = i10 + 1;
                            int i11 = bArr4[i10] & Constants.UNKNOWN;
                   int iDecodeBase64Char3 = base64Variant.decodeBase64Char(i11);
                   if (iDecodeBase64Char3 < 0) {
                       if (iDecodeBase64Char3 != -2) {
                           if (i11 == 34 && !base64Variant.usesPadding()) {
                               i3 = i + 1;
                               bArr[i] = (byte) (i9 >> 4);
                                        break;
                                    }
                           iDecodeBase64Char3 = _decodeBase64Escape(base64Variant, i11, 2);
                                }
                       if (iDecodeBase64Char3 == -2) {
                           if (this._inputPtr >= this._inputEnd) {
                               loadMoreGuaranteed();
                                    }
                           byte[] bArr5 = this._inputBuffer;
                                    int i12 = this._inputPtr;
                                    this._inputPtr = i12 + 1;
                                    int i13 = bArr5[i12] & Constants.UNKNOWN;
                           if (!base64Variant.usesPaddingChar(i13)) {
                               throw reportInvalidBase64Char(base64Variant, i13, 3, "expected padding character '" + base64Variant.getPaddingChar() + "'");
                                    }
                           i3 = i + 1;
                           bArr[i] = (byte) (i9 >> 4);
                                }
                            }
                   int i14 = (i9 << 6) | iDecodeBase64Char3;
                   if (this._inputPtr >= this._inputEnd) {
                       loadMoreGuaranteed();
                            }
                   byte[] bArr6 = this._inputBuffer;
                            int i15 = this._inputPtr;
                            this._inputPtr = i15 + 1;
                            int i16 = bArr6[i15] & Constants.UNKNOWN;
                   int iDecodeBase64Char4 = base64Variant.decodeBase64Char(i16);
                   if (iDecodeBase64Char4 < 0) {
                       if (iDecodeBase64Char4 != -2) {
                           if (i16 == 34 && !base64Variant.usesPadding()) {
                               int i17 = i14 >> 2;
                               int i18 = i + 1;
                                        bArr[i] = (byte) (i17 >> 8);
                               i3 = i18 + 1;
                                        bArr[i18] = (byte) i17;
                                        break;
                                    }
                           iDecodeBase64Char4 = _decodeBase64Escape(base64Variant, i16, 3);
                                }
                       if (iDecodeBase64Char4 == -2) {
                           int i19 = i14 >> 2;
                           int i20 = i + 1;
                                    bArr[i] = (byte) (i19 >> 8);
                           i3 = i20 + 1;
                                    bArr[i20] = (byte) i19;
                                }
                            }
                   int i21 = (i14 << 6) | iDecodeBase64Char4;
                   int i22 = i + 1;
                            bArr[i] = (byte) (i21 >> 16);
                   int i23 = i22 + 1;
                            bArr[i22] = (byte) (i21 >> 8);
                   i3 = i23 + 1;
                            bArr[i23] = (byte) i21;
                        }
                    }
           this._tokenIncomplete = false;
           if (i3 > 0) {
               int i24 = i2 + i3;
               outputStream.write(bArr, 0, i3);
               return i24;
                    }
           return i2;
                }

                @Override 
       public JsonLocation getTokenLocation() {
           return new JsonLocation(this._ioContext.getSourceReference(), getTokenCharacterOffset(), -1L, getTokenLineNr(), getTokenColumnNr());
                }

                @Override 
       public JsonLocation getCurrentLocation() {
           return new JsonLocation(this._ioContext.getSourceReference(), this._currInputProcessed + ((long) this._inputPtr), -1L, this._currInputRow, (this._inputPtr - this._currInputRowStart) + 1);
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
           if (!this._parsingContext.inObject()) {
               return _nextTokenNotInObject(i_skipWSOrEnd);
                    }
           this._parsingContext.setCurrentName(_parseName(i_skipWSOrEnd).getName());
           this._currToken = JsonToken.FIELD_NAME;
           if (this._inputPtr < this._inputEnd && this._inputBuffer[this._inputPtr] == 58) {
               this._inputPtr++;
                    } else {
               int i_skipWS = _skipWS();
               if (i_skipWS != 58) {
                   _reportUnexpectedChar(i_skipWS, "was expecting a colon to separate field name and value");
                        }
                    }
           int i_skipWS2 = _skipWS();
           if (i_skipWS2 == 34) {
               this._tokenIncomplete = true;
               this._nextToken = JsonToken.VALUE_STRING;
               return this._currToken;
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
                   jsonToken_parseNumber = _parseNumber(i_skipWS2);
                            break;
                        case 91:
                   jsonToken_parseNumber = JsonToken.START_ARRAY;
                            break;
                        case 93:
                        case 125:
                   _reportUnexpectedChar(i_skipWS2, "expected a value");
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
                   jsonToken_parseNumber = JsonToken.START_OBJECT;
                            break;
                        default:
                   jsonToken_parseNumber = _handleUnexpectedValue(i_skipWS2);
                            break;
                    }
           this._nextToken = jsonToken_parseNumber;
           return this._currToken;
                }

       private final JsonToken _nextTokenNotInObject(int i) throws IOException {
           if (i == 34) {
               this._tokenIncomplete = true;
               JsonToken jsonToken = JsonToken.VALUE_STRING;
                        this._currToken = jsonToken;
               return jsonToken;
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
                   JsonToken jsonToken_parseNumber = _parseNumber(i);
                            this._currToken = jsonToken_parseNumber;
                   return jsonToken_parseNumber;
                        case 91:
                   this._parsingContext = this._parsingContext.createChildArrayContext(this._tokenInputRow, this._tokenInputCol);
                   JsonToken jsonToken2 = JsonToken.START_ARRAY;
                            this._currToken = jsonToken2;
                   return jsonToken2;
                        case 93:
                        case 125:
                   _reportUnexpectedChar(i, "expected a value");
                            break;
                        case 102:
                   _matchToken("false", 1);
                   JsonToken jsonToken3 = JsonToken.VALUE_FALSE;
                            this._currToken = jsonToken3;
                   return jsonToken3;
                        case 110:
                   _matchToken("null", 1);
                   JsonToken jsonToken4 = JsonToken.VALUE_NULL;
                            this._currToken = jsonToken4;
                   return jsonToken4;
                        case 116:
                            break;
                        case 123:
                   this._parsingContext = this._parsingContext.createChildObjectContext(this._tokenInputRow, this._tokenInputCol);
                   JsonToken jsonToken5 = JsonToken.START_OBJECT;
                            this._currToken = jsonToken5;
                   return jsonToken5;
                        default:
                   JsonToken jsonToken_handleUnexpectedValue = _handleUnexpectedValue(i);
                            this._currToken = jsonToken_handleUnexpectedValue;
                   return jsonToken_handleUnexpectedValue;
                    }
           _matchToken("true", 1);
           JsonToken jsonToken6 = JsonToken.VALUE_TRUE;
                    this._currToken = jsonToken6;
           return jsonToken6;
                }

       private final JsonToken _nextAfterName() {
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
       public boolean nextFieldName(SerializableString serializableString) throws IOException {
           this._numTypesValid = 0;
           if (this._currToken == JsonToken.FIELD_NAME) {
               _nextAfterName();
                        return false;
                    }
           if (this._tokenIncomplete) {
               _skipString();
                    }
           int i_skipWSOrEnd = _skipWSOrEnd();
           if (i_skipWSOrEnd < 0) {
               close();
               this._currToken = null;
                        return false;
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
               this._currToken = JsonToken.END_ARRAY;
                        return false;
                    }
           if (i_skipWSOrEnd == 125) {
               if (!this._parsingContext.inObject()) {
                   _reportMismatchedEndMarker(i_skipWSOrEnd, ']');
                        }
               this._parsingContext = this._parsingContext.getParent();
               this._currToken = JsonToken.END_OBJECT;
                        return false;
                    }
           if (this._parsingContext.expectComma()) {
               if (i_skipWSOrEnd != 44) {
                   _reportUnexpectedChar(i_skipWSOrEnd, "was expecting comma to separate " + this._parsingContext.getTypeDesc() + " entries");
                        }
               i_skipWSOrEnd = _skipWS();
                    }
           if (!this._parsingContext.inObject()) {
               _nextTokenNotInObject(i_skipWSOrEnd);
                        return false;
                    }
           if (i_skipWSOrEnd == 34) {
               byte[] bArrAsQuotedUTF8 = serializableString.asQuotedUTF8();
               int length = bArrAsQuotedUTF8.length;
               if (this._inputPtr + length < this._inputEnd) {
                   int i = this._inputPtr + length;
                   if (this._inputBuffer[i] == 34) {
                       int i2 = this._inputPtr;
                       for (int i3 = 0; i3 != length; i3++) {
                           if (bArrAsQuotedUTF8[i3] == this._inputBuffer[i2 + i3]) {
                                    }
                                }
                       this._inputPtr = i + 1;
                       this._parsingContext.setCurrentName(serializableString.getValue());
                       this._currToken = JsonToken.FIELD_NAME;
                       _isNextTokenNameYes();
                       return true;
                            }
                        }
                    }
           return _isNextTokenNameMaybe(i_skipWSOrEnd, serializableString);
                }

       private final void _isNextTokenNameYes() throws IOException {
                    int i_skipColon;
           if (this._inputPtr < this._inputEnd - 1 && this._inputBuffer[this._inputPtr] == 58) {
               byte[] bArr = this._inputBuffer;
                        int i = this._inputPtr + 1;
                        this._inputPtr = i;
                        byte b = bArr[i];
               this._inputPtr++;
               if (b == 34) {
                   this._tokenIncomplete = true;
                   this._nextToken = JsonToken.VALUE_STRING;
                  return;
                        } else {
                   if (b == 123) {
                       this._nextToken = JsonToken.START_OBJECT;
                      return;
                            }
                   if (b == 91) {
                       this._nextToken = JsonToken.START_ARRAY;
                      return;
                            }
                   i_skipColon = b & Constants.UNKNOWN;
                   if (i_skipColon <= 32 || i_skipColon == 47) {
                       this._inputPtr--;
                       i_skipColon = _skipWS();
                            }
                        }
                    } else {
               i_skipColon = _skipColon();
                    }
                    switch (i_skipColon) {
                        case 34:
                   this._tokenIncomplete = true;
                   this._nextToken = JsonToken.VALUE_STRING;
                  return;
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
                   this._nextToken = _parseNumber(i_skipColon);
                  return;
                        case 91:
                   this._nextToken = JsonToken.START_ARRAY;
                  return;
                        case 93:
                        case 125:
                   _reportUnexpectedChar(i_skipColon, "expected a value");
                            break;
                        case 102:
                   _matchToken("false", 1);
                   this._nextToken = JsonToken.VALUE_FALSE;
                  return;
                        case 110:
                   _matchToken("null", 1);
                   this._nextToken = JsonToken.VALUE_NULL;
                  return;
                        case 116:
                            break;
                        case 123:
                   this._nextToken = JsonToken.START_OBJECT;
                  return;
                        default:
                   this._nextToken = _handleUnexpectedValue(i_skipColon);
                  return;
                    }
           _matchToken("true", 1);
           this._nextToken = JsonToken.VALUE_TRUE;
                }

                
      private final boolean _isNextTokenNameMaybe(int i, SerializableString serializableString) throws IOException {
                    JsonToken jsonToken_parseNumber;
          String name = _parseName(i).getName();
          this._parsingContext.setCurrentName(name);
          boolean zEquals = name.equals(serializableString.getValue());
          this._currToken = JsonToken.FIELD_NAME;
          int i_skipWS = _skipWS();
          if (i_skipWS != 58) {
              _reportUnexpectedChar(i_skipWS, "was expecting a colon to separate field name and value");
                    }
          int i_skipWS2 = _skipWS();
          if (i_skipWS2 == 34) {
              this._tokenIncomplete = true;
              this._nextToken = JsonToken.VALUE_STRING;
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
                  jsonToken_parseNumber = _parseNumber(i_skipWS2);
                            break;
                        case 91:
                  jsonToken_parseNumber = JsonToken.START_ARRAY;
                            break;
                        case 93:
                        case 125:
                  _reportUnexpectedChar(i_skipWS2, "expected a value");
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
                  jsonToken_parseNumber = JsonToken.START_OBJECT;
                            break;
                        default:
                  jsonToken_parseNumber = _handleUnexpectedValue(i_skipWS2);
                            break;
                    }
          this._nextToken = jsonToken_parseNumber;
                    return zEquals;
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
                    switch (nextToken().m524id()) {
                        case 9:
                  return Boolean.TRUE;
                        case 10:
                  return Boolean.FALSE;
                        default:
                            return null;
                    }
                }

      protected JsonToken _parseNumber(int i) throws IOException {
                    int i2;
                    int i_verifyNoLeadingZeroes;
                    int i3;
                    int i4 = 1;
          char[] cArrEmptyAndGetCurrentSegment = this._textBuffer.emptyAndGetCurrentSegment();
          boolean z = i == 45;
          if (z) {
              cArrEmptyAndGetCurrentSegment[0] = '-';
              if (this._inputPtr >= this._inputEnd) {
                  loadMoreGuaranteed();
                        }
              byte[] bArr = this._inputBuffer;
                        int i5 = this._inputPtr;
                        this._inputPtr = i5 + 1;
                        i_verifyNoLeadingZeroes = bArr[i5] & Constants.UNKNOWN;
              if (i_verifyNoLeadingZeroes < 48 || i_verifyNoLeadingZeroes > 57) {
                  return _handleInvalidNumberStart(i_verifyNoLeadingZeroes, true);
                        }
                        i2 = 1;
                    } else {
                        i2 = 0;
                        i_verifyNoLeadingZeroes = i;
                    }
          if (i_verifyNoLeadingZeroes == 48) {
              i_verifyNoLeadingZeroes = _verifyNoLeadingZeroes();
                    }
          int i6 = i2 + 1;
                    cArrEmptyAndGetCurrentSegment[i2] = (char) i_verifyNoLeadingZeroes;
          int length = this._inputPtr + cArrEmptyAndGetCurrentSegment.length;
          if (length > this._inputEnd) {
              length = this._inputEnd;
                    }
          while (this._inputPtr < length) {
              byte[] bArr2 = this._inputBuffer;
                        int i7 = this._inputPtr;
                        this._inputPtr = i7 + 1;
                        int i8 = bArr2[i7] & Constants.UNKNOWN;
              if (i8 >= 48 && i8 <= 57) {
                  i4++;
                  if (i6 >= cArrEmptyAndGetCurrentSegment.length) {
                      cArrEmptyAndGetCurrentSegment = this._textBuffer.finishCurrentSegment();
                                i3 = 0;
                            } else {
                                i3 = i6;
                            }
                  i6 = i3 + 1;
                            cArrEmptyAndGetCurrentSegment[i3] = (char) i8;
                        } else {
                  if (i8 == 46 || i8 == 101 || i8 == 69) {
                      return _parseFloat(cArrEmptyAndGetCurrentSegment, i6, i8, z, i4);
                            }
                  this._inputPtr--;
                  this._textBuffer.setCurrentLength(i6);
                  if (this._parsingContext.inRoot()) {
                      _verifyRootSpace(i8);
                            }
                  return resetInt(z, i4);
                        }
                    }
          return _parserNumber2(cArrEmptyAndGetCurrentSegment, i6, z, i4);
                }

      private final JsonToken _parserNumber2(char[] cArr, int i, boolean z, int i2) throws IOException {
          int i3 = i2;
                    int i4 = i;
                    char[] cArrFinishCurrentSegment = cArr;
                    while (true) {
                        if (this._inputPtr >= this._inputEnd && !loadMore()) {
                  this._textBuffer.setCurrentLength(i4);
                  return resetInt(z, i3);
                        }
              byte[] bArr = this._inputBuffer;
                        int i5 = this._inputPtr;
                        this._inputPtr = i5 + 1;
                        int i6 = bArr[i5] & Constants.UNKNOWN;
              if (i6 > 57 || i6 < 48) {
                  if (i6 == 46 || i6 == 101 || i6 == 69) {
                      return _parseFloat(cArrFinishCurrentSegment, i4, i6, z, i3);
                            }
                  this._inputPtr--;
                  this._textBuffer.setCurrentLength(i4);
                  if (this._parsingContext.inRoot()) {
                      byte[] bArr2 = this._inputBuffer;
                                int i7 = this._inputPtr;
                                this._inputPtr = i7 + 1;
                                _verifyRootSpace(bArr2[i7] & Constants.UNKNOWN);
                            }
                  return resetInt(z, i3);
                        }
              if (i4 >= cArrFinishCurrentSegment.length) {
                  cArrFinishCurrentSegment = this._textBuffer.finishCurrentSegment();
                  i4 = 0;
                        }
                        int i8 = i4;
              i4 = i8 + 1;
                        cArrFinishCurrentSegment[i8] = (char) i6;
              i3++;
                    }
                }

      private final int _verifyNoLeadingZeroes() throws IOException {
          if (this._inputPtr >= this._inputEnd && !loadMore()) {
                        return 48;
                    }
          int i = this._inputBuffer[this._inputPtr] & Constants.UNKNOWN;
          if (i < 48 || i > 57) {
                        return 48;
                    }
          if (!isEnabled(JsonParser.Feature.ALLOW_NUMERIC_LEADING_ZEROS)) {
              reportInvalidNumber("Leading zeroes not allowed");
                    }
          this._inputPtr++;
          if (i == 48) {
              do {
                  if (this._inputPtr < this._inputEnd || loadMore()) {
                      i = this._inputBuffer[this._inputPtr] & Constants.UNKNOWN;
                      if (i < 48 || i > 57) {
                                    return 48;
                                }
                      this._inputPtr++;
                            } else {
                      return i;
                            }
              } while (i == 48);
              return i;
                    }
          return i;
                }

      private final JsonToken _parseFloat(char[] cArr, int i, int i2, boolean z, int i3) throws IOException {
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
          int i12 = 0;
          boolean z3 = false;
          if (i2 != 46) {
              i4 = 0;
                        i5 = i2;
                        cArrFinishCurrentSegment = cArr;
                        i6 = i;
                    } else {
              int i13 = i + 1;
                        cArr[i] = (char) i2;
                        while (true) {
                  if (this._inputPtr >= this._inputEnd && !loadMore()) {
                      z3 = true;
                                i5 = i2;
                                break;
                            }
                  byte[] bArr = this._inputBuffer;
                            int i14 = this._inputPtr;
                            this._inputPtr = i14 + 1;
                            i2 = bArr[i14] & Constants.UNKNOWN;
                  if (i2 < 48) {
                                i5 = i2;
                                break;
                            }
                            if (i2 > 57) {
                                i5 = i2;
                                break;
                            }
                  i12++;
                  if (i13 >= cArr.length) {
                      cArr = this._textBuffer.finishCurrentSegment();
                      i13 = 0;
                            }
                            int i15 = i13;
                  i13 = i15 + 1;
                            cArr[i15] = (char) i2;
                        }
              if (i12 == 0) {
                  reportUnexpectedNumberChar(i5, "Decimal point not followed by a digit");
                        }
                        i4 = i12;
                        i6 = i13;
                        cArrFinishCurrentSegment = cArr;
                    }
          int i16 = 0;
          if (i5 != 101 && i5 != 69) {
                        z2 = z3;
                        i8 = i5;
                        i10 = i6;
              i11 = 0;
                    } else {
              if (i6 >= cArrFinishCurrentSegment.length) {
                  cArrFinishCurrentSegment = this._textBuffer.finishCurrentSegment();
                  i6 = 0;
                        }
              int i17 = i6 + 1;
                        cArrFinishCurrentSegment[i6] = (char) i5;
              if (this._inputPtr >= this._inputEnd) {
                  loadMoreGuaranteed();
                        }
              byte[] bArr2 = this._inputBuffer;
                        int i18 = this._inputPtr;
                        this._inputPtr = i18 + 1;
                        int i19 = bArr2[i18] & Constants.UNKNOWN;
              if (i19 == 45 || i19 == 43) {
                  if (i17 >= cArrFinishCurrentSegment.length) {
                      cArrFinishCurrentSegment = this._textBuffer.finishCurrentSegment();
                      i7 = 0;
                            } else {
                                i7 = i17;
                            }
                  int i20 = i7 + 1;
                            cArrFinishCurrentSegment[i7] = (char) i19;
                  if (this._inputPtr >= this._inputEnd) {
                      loadMoreGuaranteed();
                            }
                  byte[] bArr3 = this._inputBuffer;
                            int i21 = this._inputPtr;
                            this._inputPtr = i21 + 1;
                            i8 = bArr3[i21] & Constants.UNKNOWN;
                            i9 = i20;
                        } else {
                            i9 = i17;
                            i8 = i19;
                        }
                        while (true) {
                  if (i8 <= 57 && i8 >= 48) {
                      i16++;
                      if (i9 >= cArrFinishCurrentSegment.length) {
                          cArrFinishCurrentSegment = this._textBuffer.finishCurrentSegment();
                          i9 = 0;
                                }
                      int i22 = i9 + 1;
                                cArrFinishCurrentSegment[i9] = (char) i8;
                      if (this._inputPtr >= this._inputEnd && !loadMore()) {
                                    i11 = i16;
                          z2 = true;
                                    i10 = i22;
                                    break;
                                }
                      byte[] bArr4 = this._inputBuffer;
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
              if (i11 == 0) {
                  reportUnexpectedNumberChar(i8, "Exponent indicator not followed by a digit");
                        }
                    }
          if (!z2) {
              this._inputPtr--;
              if (this._parsingContext.inRoot()) {
                  _verifyRootSpace(i8);
                        }
                    }
          this._textBuffer.setCurrentLength(i10);
          return resetFloat(z, i3, i4, i11);
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

      protected Name _parseName(int i) throws IOException {
          if (i != 34) {
              return _handleOddName(i);
                    }
          if (this._inputPtr + 9 > this._inputEnd) {
              return slowParseName();
                    }
          byte[] bArr = this._inputBuffer;
          int[] iArr = _icLatin1;
          int i2 = this._inputPtr;
                    this._inputPtr = i2 + 1;
                    int i3 = bArr[i2] & Constants.UNKNOWN;
          if (iArr[i3] == 0) {
              int i4 = this._inputPtr;
                        this._inputPtr = i4 + 1;
                        int i5 = bArr[i4] & Constants.UNKNOWN;
              if (iArr[i5] == 0) {
                  int i6 = (i3 << 8) | i5;
                  int i7 = this._inputPtr;
                            this._inputPtr = i7 + 1;
                            int i8 = bArr[i7] & Constants.UNKNOWN;
                  if (iArr[i8] == 0) {
                      int i9 = (i6 << 8) | i8;
                      int i10 = this._inputPtr;
                                this._inputPtr = i10 + 1;
                                int i11 = bArr[i10] & Constants.UNKNOWN;
                      if (iArr[i11] == 0) {
                          int i12 = (i9 << 8) | i11;
                          int i13 = this._inputPtr;
                                    this._inputPtr = i13 + 1;
                                    int i14 = bArr[i13] & Constants.UNKNOWN;
                          if (iArr[i14] == 0) {
                              this._quad1 = i12;
                              return parseMediumName(i14, iArr);
                                    }
                          if (i14 == 34) {
                              return findName(i12, 4);
                                    }
                          return parseName(i12, i14, 4);
                                }
                      if (i11 == 34) {
                          return findName(i9, 3);
                                }
                      return parseName(i9, i11, 3);
                            }
                  if (i8 == 34) {
                      return findName(i6, 2);
                            }
                  return parseName(i6, i8, 2);
                        }
              if (i5 == 34) {
                  return findName(i3, 1);
                        }
              return parseName(i3, i5, 1);
                    }
          if (i3 == 34) {
              return BytesToNameCanonicalizer.getEmptyName();
                    }
          return parseName(0, i3, 0);
                }

      protected Name parseMediumName(int i, int[] iArr) throws IOException {
          byte[] bArr = this._inputBuffer;
                    int i2 = this._inputPtr;
                    this._inputPtr = i2 + 1;
                    int i3 = bArr[i2] & Constants.UNKNOWN;
          if (iArr[i3] != 0) {
              if (i3 == 34) {
                  return findName(this._quad1, i, 1);
                        }
              return parseName(this._quad1, i, i3, 1);
                    }
          int i4 = i3 | (i << 8);
          byte[] bArr2 = this._inputBuffer;
                    int i5 = this._inputPtr;
                    this._inputPtr = i5 + 1;
                    int i6 = bArr2[i5] & Constants.UNKNOWN;
          if (iArr[i6] != 0) {
              if (i6 == 34) {
                  return findName(this._quad1, i4, 2);
                        }
              return parseName(this._quad1, i4, i6, 2);
                    }
          int i7 = (i4 << 8) | i6;
          byte[] bArr3 = this._inputBuffer;
                    int i8 = this._inputPtr;
                    this._inputPtr = i8 + 1;
                    int i9 = bArr3[i8] & Constants.UNKNOWN;
          if (iArr[i9] != 0) {
              if (i9 == 34) {
                  return findName(this._quad1, i7, 3);
                        }
              return parseName(this._quad1, i7, i9, 3);
                    }
          int i10 = (i7 << 8) | i9;
          byte[] bArr4 = this._inputBuffer;
                    int i11 = this._inputPtr;
                    this._inputPtr = i11 + 1;
                    int i12 = bArr4[i11] & Constants.UNKNOWN;
          if (iArr[i12] != 0) {
              if (i12 == 34) {
                  return findName(this._quad1, i10, 4);
                        }
              return parseName(this._quad1, i10, i12, 4);
                    }
          this._quadBuffer[0] = this._quad1;
          this._quadBuffer[1] = i10;
          return parseLongName(i12);
                }

      protected Name parseLongName(int i) throws IOException {
          int[] iArr = _icLatin1;
                    int i2 = 2;
                    int i3 = i;
          while (this._inputEnd - this._inputPtr >= 4) {
              byte[] bArr = this._inputBuffer;
                        int i4 = this._inputPtr;
                        this._inputPtr = i4 + 1;
                        int i5 = bArr[i4] & Constants.UNKNOWN;
              if (iArr[i5] != 0) {
                  if (i5 == 34) {
                      return findName(this._quadBuffer, i2, i3, 1);
                            }
                  return parseEscapedName(this._quadBuffer, i2, i3, i5, 1);
                        }
              int i6 = (i3 << 8) | i5;
              byte[] bArr2 = this._inputBuffer;
                        int i7 = this._inputPtr;
                        this._inputPtr = i7 + 1;
                        int i8 = bArr2[i7] & Constants.UNKNOWN;
              if (iArr[i8] != 0) {
                  if (i8 == 34) {
                      return findName(this._quadBuffer, i2, i6, 2);
                            }
                  return parseEscapedName(this._quadBuffer, i2, i6, i8, 2);
                        }
              int i9 = (i6 << 8) | i8;
              byte[] bArr3 = this._inputBuffer;
                        int i10 = this._inputPtr;
                        this._inputPtr = i10 + 1;
                        int i11 = bArr3[i10] & Constants.UNKNOWN;
              if (iArr[i11] != 0) {
                  if (i11 == 34) {
                      return findName(this._quadBuffer, i2, i9, 3);
                            }
                  return parseEscapedName(this._quadBuffer, i2, i9, i11, 3);
                        }
              int i12 = (i9 << 8) | i11;
              byte[] bArr4 = this._inputBuffer;
                        int i13 = this._inputPtr;
                        this._inputPtr = i13 + 1;
                        i3 = bArr4[i13] & Constants.UNKNOWN;
              if (iArr[i3] != 0) {
                  if (i3 == 34) {
                      return findName(this._quadBuffer, i2, i12, 4);
                            }
                  return parseEscapedName(this._quadBuffer, i2, i12, i3, 4);
                        }
              if (i2 >= this._quadBuffer.length) {
                  this._quadBuffer = growArrayBy(this._quadBuffer, i2);
                        }
              this._quadBuffer[i2] = i12;
                        i2++;
                    }
          return parseEscapedName(this._quadBuffer, i2, 0, i3, 0);
                }

      protected Name slowParseName() throws IOException {
          if (this._inputPtr >= this._inputEnd && !loadMore()) {
              _reportInvalidEOF(": was expecting closing '\"' for name");
                    }
          byte[] bArr = this._inputBuffer;
                    int i = this._inputPtr;
                    this._inputPtr = i + 1;
                    int i2 = bArr[i] & Constants.UNKNOWN;
                    return i2 == 34 ? BytesToNameCanonicalizer.getEmptyName() : parseEscapedName(this._quadBuffer, 0, 0, i2, 0);
                }

      private final Name parseName(int i, int i2, int i3) throws IOException {
          return parseEscapedName(this._quadBuffer, 0, i, i2, i3);
                }

      private final Name parseName(int i, int i2, int i3, int i4) throws IOException {
          this._quadBuffer[0] = i;
          return parseEscapedName(this._quadBuffer, 1, i2, i3, i4);
                }

                

      protected Name parseEscapedName(int[] iArr, int i, int i2, int i3, int i4) throws IOException {
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
          int[] iArr4 = _icLatin1;
                    while (true) {
              if (iArr4[i3] == 0) {
                            i5 = i2;
                            iArrGrowArrayBy = iArr;
                            i2 = i3;
                        } else {
                  if (i3 == 34) {
                                break;
                            }
                  if (i3 != 92) {
                      _throwUnquotedSpace(i3, "name");
                            } else {
                      i3 = _decodeEscaped();
                            }
                  if (i3 > 127) {
                      if (i4 >= 4) {
                          if (i >= iArr.length) {
                              iArr = growArrayBy(iArr, iArr.length);
                                        this._quadBuffer = iArr;
                                    }
                          i6 = i + 1;
                                    iArr[i] = i2;
                                    i4 = 0;
                                    i2 = 0;
                                    iArrGrowArrayBy2 = iArr;
                                } else {
                                    i6 = i;
                                    iArrGrowArrayBy2 = iArr;
                                }
                      if (i3 < 2048) {
                          i10 = (i3 >> 6) | 192 | (i2 << 8);
                                    iArr3 = iArrGrowArrayBy2;
                          i11 = i4 + 1;
                                } else {
                          int i12 = (i3 >> 12) | 224 | (i2 << 8);
                          int i13 = i4 + 1;
                          if (i13 >= 4) {
                              if (i6 >= iArrGrowArrayBy2.length) {
                                  iArrGrowArrayBy2 = growArrayBy(iArrGrowArrayBy2, iArrGrowArrayBy2.length);
                                            this._quadBuffer = iArrGrowArrayBy2;
                                        }
                                        iArrGrowArrayBy2[i6] = i12;
                              i8 = i6 + 1;
                                        iArr2 = iArrGrowArrayBy2;
                                        i9 = 0;
                                        i7 = 0;
                                    } else {
                                        i7 = i12;
                                        i8 = i6;
                                        iArr2 = iArrGrowArrayBy2;
                                        i9 = i13;
                                    }
                          i10 = (i7 << 8) | ((i3 >> 6) & 63) | 128;
                          i11 = i9 + 1;
                                    int i14 = i8;
                                    iArr3 = iArr2;
                                    i6 = i14;
                                }
                      i2 = (i3 & 63) | 128;
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
              if (i4 < 4) {
                  i4++;
                  i2 |= i5 << 8;
                            iArr = iArrGrowArrayBy;
                        } else {
                  if (i >= iArrGrowArrayBy.length) {
                      iArrGrowArrayBy = growArrayBy(iArrGrowArrayBy, iArrGrowArrayBy.length);
                                this._quadBuffer = iArrGrowArrayBy;
                            }
                            iArrGrowArrayBy[i] = i5;
                  i4 = 1;
                  i++;
                            iArr = iArrGrowArrayBy;
                        }
              if (this._inputPtr >= this._inputEnd && !loadMore()) {
                  _reportInvalidEOF(" in field name");
                        }
              byte[] bArr = this._inputBuffer;
                        int i15 = this._inputPtr;
                        this._inputPtr = i15 + 1;
                        i3 = bArr[i15] & Constants.UNKNOWN;
                    }
          if (i4 > 0) {
              if (i >= iArr.length) {
                  iArr = growArrayBy(iArr, iArr.length);
                            this._quadBuffer = iArr;
                        }
                        iArr[i] = i2;
              i++;
                    }
          Name nameFindName = this._symbols.findName(iArr, i);
          if (nameFindName == null) {
              return addName(iArr, i, i4);
                    }
          return nameFindName;
                }

      protected Name _handleOddName(int i) throws IOException {
                    int[] iArrGrowArrayBy;
                    int i2;
                    int i3;
                    int i4;
          if (i == 39 && isEnabled(JsonParser.Feature.ALLOW_SINGLE_QUOTES)) {
              return _parseAposName();
                    }
          if (!isEnabled(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES)) {
              _reportUnexpectedChar(i, "was expecting double-quote to start field name");
                    }
          int[] inputCodeUtf8JsNames = CharTypes.getInputCodeUtf8JsNames();
          if (inputCodeUtf8JsNames[i] != 0) {
              _reportUnexpectedChar(i, "was expecting either valid name character (for unquoted name) or double-quote (for quoted) to start field name");
                    }
                    int i5 = 0;
                    int i6 = 0;
                    int i7 = i;
                    int i8 = 0;
          int[] iArrGrowArrayBy2 = this._quadBuffer;
                    while (true) {
              if (i5 < 4) {
                  int i9 = i5 + 1;
                  i3 = i7 | (i6 << 8);
                            i4 = i8;
                            iArrGrowArrayBy = iArrGrowArrayBy2;
                            i2 = i9;
                        } else {
                  if (i8 >= iArrGrowArrayBy2.length) {
                      iArrGrowArrayBy2 = growArrayBy(iArrGrowArrayBy2, iArrGrowArrayBy2.length);
                                this._quadBuffer = iArrGrowArrayBy2;
                            }
                  int i10 = i8 + 1;
                            iArrGrowArrayBy2[i8] = i6;
                            iArrGrowArrayBy = iArrGrowArrayBy2;
                  i2 = 1;
                            i3 = i7;
                            i4 = i10;
                        }
              if (this._inputPtr >= this._inputEnd && !loadMore()) {
                  _reportInvalidEOF(" in field name");
                        }
              int i11 = this._inputBuffer[this._inputPtr] & Constants.UNKNOWN;
              if (inputCodeUtf8JsNames[i11] != 0) {
                            break;
                        }
              this._inputPtr++;
                        i6 = i3;
                        i5 = i2;
                        iArrGrowArrayBy2 = iArrGrowArrayBy;
                        i8 = i4;
                        i7 = i11;
                    }
          if (i2 > 0) {
              if (i4 >= iArrGrowArrayBy.length) {
                  iArrGrowArrayBy = growArrayBy(iArrGrowArrayBy, iArrGrowArrayBy.length);
                            this._quadBuffer = iArrGrowArrayBy;
                        }
                        iArrGrowArrayBy[i4] = i3;
              i4++;
                    }
          Name nameFindName = this._symbols.findName(iArrGrowArrayBy, i4);
                    return nameFindName == null ? addName(iArrGrowArrayBy, i4, i2) : nameFindName;
                }

                

      protected Name _parseAposName() throws IOException {
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
          if (this._inputPtr >= this._inputEnd && !loadMore()) {
              _reportInvalidEOF(": was expecting closing ''' for name");
                    }
          byte[] bArr = this._inputBuffer;
                    int i16 = this._inputPtr;
                    this._inputPtr = i16 + 1;
                    int i_decodeEscaped = bArr[i16] & Constants.UNKNOWN;
          if (i_decodeEscaped == 39) {
              return BytesToNameCanonicalizer.getEmptyName();
                    }
          int[] iArrGrowArrayBy = this._quadBuffer;
          int[] iArr5 = _icLatin1;
                    int i17 = 0;
                    int i18 = 0;
                    int i19 = 0;
          while (i_decodeEscaped != 39) {
              if (i_decodeEscaped == 34 || iArr5[i_decodeEscaped] == 0) {
                            i2 = i18;
                            i3 = i19;
                            i4 = i17;
                            i5 = i_decodeEscaped;
                        } else {
                  if (i_decodeEscaped != 92) {
                      _throwUnquotedSpace(i_decodeEscaped, "name");
                            } else {
                      i_decodeEscaped = _decodeEscaped();
                            }
                  if (i_decodeEscaped > 127) {
                      if (i17 >= 4) {
                          if (i19 >= iArrGrowArrayBy.length) {
                              iArrGrowArrayBy = growArrayBy(iArrGrowArrayBy, iArrGrowArrayBy.length);
                                        this._quadBuffer = iArrGrowArrayBy;
                                    }
                          int i20 = i19 + 1;
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
                      if (i_decodeEscaped < 2048) {
                          int i22 = i10 + 1;
                          i14 = (i9 << 8) | (i_decodeEscaped >> 6) | 192;
                                    iArr4 = iArrGrowArrayBy;
                                    i15 = i22;
                                } else {
                          int i23 = (i9 << 8) | (i_decodeEscaped >> 12) | 224;
                          int i24 = i10 + 1;
                          if (i24 >= 4) {
                              if (i3 >= iArrGrowArrayBy.length) {
                                  iArrGrowArrayBy = growArrayBy(iArrGrowArrayBy, iArrGrowArrayBy.length);
                                            this._quadBuffer = iArrGrowArrayBy;
                                        }
                                        iArrGrowArrayBy[i3] = i23;
                              i12 = i3 + 1;
                                        iArr3 = iArrGrowArrayBy;
                                        i13 = 0;
                                        i11 = 0;
                                    } else {
                                        i11 = i23;
                                        i12 = i3;
                                        iArr3 = iArrGrowArrayBy;
                                        i13 = i24;
                                    }
                          i14 = (i11 << 8) | ((i_decodeEscaped >> 6) & 63) | 128;
                          i15 = i13 + 1;
                                    int i25 = i12;
                                    iArr4 = iArr3;
                                    i3 = i25;
                                }
                                i2 = i14;
                                i4 = i15;
                                iArrGrowArrayBy = iArr4;
                      i5 = (i_decodeEscaped & 63) | 128;
                            } else {
                                i2 = i18;
                                i3 = i19;
                                i4 = i17;
                                i5 = i_decodeEscaped;
                            }
                        }
              if (i4 < 4) {
                  int i26 = i4 + 1;
                  i7 = i5 | (i2 << 8);
                            i8 = i3;
                            iArr2 = iArrGrowArrayBy;
                            i6 = i26;
                        } else {
                  if (i3 >= iArrGrowArrayBy.length) {
                      iArrGrowArrayBy = growArrayBy(iArrGrowArrayBy, iArrGrowArrayBy.length);
                                this._quadBuffer = iArrGrowArrayBy;
                            }
                  int i27 = i3 + 1;
                            iArrGrowArrayBy[i3] = i2;
                            iArr2 = iArrGrowArrayBy;
                  i6 = 1;
                            i7 = i5;
                            i8 = i27;
                        }
              if (this._inputPtr >= this._inputEnd && !loadMore()) {
                  _reportInvalidEOF(" in field name");
                        }
              byte[] bArr2 = this._inputBuffer;
                        int i28 = this._inputPtr;
                        this._inputPtr = i28 + 1;
                        i_decodeEscaped = bArr2[i28] & Constants.UNKNOWN;
                        int i29 = i6;
                        iArrGrowArrayBy = iArr2;
                        i18 = i7;
                        i19 = i8;
                        i17 = i29;
                    }
          if (i17 > 0) {
              if (i19 >= iArrGrowArrayBy.length) {
                  iArrGrowArrayBy = growArrayBy(iArrGrowArrayBy, iArrGrowArrayBy.length);
                            this._quadBuffer = iArrGrowArrayBy;
                        }
                        iArrGrowArrayBy[i19] = i18;
                        iArr = iArrGrowArrayBy;
              i = i19 + 1;
                    } else {
                        iArr = iArrGrowArrayBy;
                        i = i19;
                    }
          Name nameFindName = this._symbols.findName(iArr, i);
                    return nameFindName == null ? addName(iArr, i, i17) : nameFindName;
                }

      private final Name findName(int i, int i2) throws JsonParseException {
          Name nameFindName = this._symbols.findName(i);
          if (nameFindName == null) {
              this._quadBuffer[0] = i;
              return addName(this._quadBuffer, 1, i2);
                    }
          return nameFindName;
                }

      private final Name findName(int i, int i2, int i3) throws JsonParseException {
          Name nameFindName = this._symbols.findName(i, i2);
          if (nameFindName == null) {
              this._quadBuffer[0] = i;
              this._quadBuffer[1] = i2;
              return addName(this._quadBuffer, 2, i3);
                    }
          return nameFindName;
                }

      private final Name findName(int[] iArr, int i, int i2, int i3) throws JsonParseException {
          if (i >= iArr.length) {
              iArr = growArrayBy(iArr, iArr.length);
                        this._quadBuffer = iArr;
                    }
          int i4 = i + 1;
                    iArr[i] = i2;
          Name nameFindName = this._symbols.findName(iArr, i4);
          if (nameFindName == null) {
              return addName(iArr, i4, i3);
                    }
          return nameFindName;
                }

                


      private final Name addName(int[] iArr, int i, int i2) throws JsonParseException {
                    int i3;
                    char[] cArrExpandCurrentSegment;
                    int i4;
                    int i5;
                    int i6;
                    int i7;
                    int i8;
          int i9 = ((i << 2) - 4) + i2;
          if (i2 < 4) {
              i3 = iArr[i - 1];
              iArr[i - 1] = i3 << ((4 - i2) << 3);
                    } else {
              i3 = 0;
                    }
          char[] cArrEmptyAndGetCurrentSegment = this._textBuffer.emptyAndGetCurrentSegment();
          int i10 = 0;
          int i11 = 0;
                    while (i11 < i9) {
              int i12 = (iArr[i11 >> 2] >> ((3 - (i11 & 3)) << 3)) & 255;
              int i13 = i11 + 1;
              if (i12 <= 127) {
                            cArrExpandCurrentSegment = cArrEmptyAndGetCurrentSegment;
                            i4 = i12;
                            i5 = i13;
                            i6 = i10;
                        } else {
                  if ((i12 & 224) == 192) {
                      i7 = i12 & 31;
                      i8 = 1;
                            } else if ((i12 & 240) == 224) {
                      i7 = i12 & 15;
                      i8 = 2;
                            } else if ((i12 & 248) == 240) {
                      i7 = i12 & 7;
                      i8 = 3;
                            } else {
                      _reportInvalidInitial(i12);
                      i7 = 1;
                      i8 = 1;
                            }
                  if (i13 + i8 > i9) {
                      _reportInvalidEOF(" in field name");
                            }
                  int i14 = iArr[i13 >> 2] >> ((3 - (i13 & 3)) << 3);
                  i13++;
                  if ((i14 & 192) != 128) {
                      _reportInvalidOther(i14);
                            }
                  i12 = (i7 << 6) | (i14 & 63);
                  if (i8 > 1) {
                      int i15 = iArr[i13 >> 2] >> ((3 - (i13 & 3)) << 3);
                      i13++;
                      if ((i15 & 192) != 128) {
                          _reportInvalidOther(i15);
                                }
                      i12 = (i12 << 6) | (i15 & 63);
                      if (i8 > 2) {
                          int i16 = iArr[i13 >> 2] >> ((3 - (i13 & 3)) << 3);
                          i13++;
                          if ((i16 & 192) != 128) {
                              _reportInvalidOther(i16 & 255);
                                    }
                          i12 = (i12 << 6) | (i16 & 63);
                                }
                            }
                  if (i8 > 2) {
                      int i17 = i12 - 65536;
                      if (i10 >= cArrEmptyAndGetCurrentSegment.length) {
                          cArrEmptyAndGetCurrentSegment = this._textBuffer.expandCurrentSegment();
                                }
                                cArrEmptyAndGetCurrentSegment[i10] = (char) (55296 + (i17 >> 10));
                      int i18 = (i17 & 1023) | 56320;
                                i5 = i13;
                      i6 = i10 + 1;
                                cArrExpandCurrentSegment = cArrEmptyAndGetCurrentSegment;
                                i4 = i18;
                            } else {
                                cArrExpandCurrentSegment = cArrEmptyAndGetCurrentSegment;
                                i4 = i12;
                                i5 = i13;
                                i6 = i10;
                            }
                        }
              if (i6 >= cArrExpandCurrentSegment.length) {
                  cArrExpandCurrentSegment = this._textBuffer.expandCurrentSegment();
                        }
              i10 = i6 + 1;
                        cArrExpandCurrentSegment[i6] = (char) i4;
                        i11 = i5;
                        cArrEmptyAndGetCurrentSegment = cArrExpandCurrentSegment;
                    }
          String str = new String(cArrEmptyAndGetCurrentSegment, 0, i10);
          if (i2 < 4) {
              iArr[i - 1] = i3;
                    }
          return this._symbols.addName(str, iArr, i);
                }

                @Override 
      protected void _finishString() throws IOException {
          int i = this._inputPtr;
          if (i >= this._inputEnd) {
              loadMoreGuaranteed();
              i = this._inputPtr;
                    }
          char[] cArrEmptyAndGetCurrentSegment = this._textBuffer.emptyAndGetCurrentSegment();
          int[] iArr = _icUTF8;
          int iMin = Math.min(this._inputEnd, cArrEmptyAndGetCurrentSegment.length + i);
          byte[] bArr = this._inputBuffer;
                    int i2 = i;
          int i3 = 0;
          while (i2 < iMin) {
              int i4 = bArr[i2] & Constants.UNKNOWN;
              if (iArr[i4] != 0) {
                  if (i4 != 34) {
                                break;
                            }
                  this._inputPtr = i2 + 1;
                  this._textBuffer.setCurrentLength(i3);
                  return;
                        }
                        cArrEmptyAndGetCurrentSegment[i3] = (char) i4;
              i3++;
              i2++;
                    }
          this._inputPtr = i2;
          _finishString2(cArrEmptyAndGetCurrentSegment, i3);
                }

      private final void _finishString2(char[] cArr, int i) throws IOException {
                    int i2;
          int[] iArr = _icUTF8;
          byte[] bArr = this._inputBuffer;
                    while (true) {
              int i3 = this._inputPtr;
              if (i3 >= this._inputEnd) {
                  loadMoreGuaranteed();
                  i3 = this._inputPtr;
                        }
              if (i >= cArr.length) {
                  cArr = this._textBuffer.finishCurrentSegment();
                            i = 0;
                        }
              int iMin = Math.min(this._inputEnd, (cArr.length - i) + i3);
                        while (true) {
                  if (i3 < iMin) {
                      int i4 = i3 + 1;
                                int i_decodeUtf8_3 = bArr[i3] & Constants.UNKNOWN;
                      if (iArr[i_decodeUtf8_3] != 0) {
                          this._inputPtr = i4;
                          if (i_decodeUtf8_3 != 34) {
                                        switch (iArr[i_decodeUtf8_3]) {
                                            case 1:
                                      i_decodeUtf8_3 = _decodeEscaped();
                                                break;
                                            case 2:
                                      i_decodeUtf8_3 = _decodeUtf8_2(i_decodeUtf8_3);
                                                break;
                                            case 3:
                                      if (this._inputEnd - this._inputPtr >= 2) {
                                          i_decodeUtf8_3 = _decodeUtf8_3fast(i_decodeUtf8_3);
                                                } else {
                                          i_decodeUtf8_3 = _decodeUtf8_3(i_decodeUtf8_3);
                                                }
                                                break;
                                            case 4:
                                      int i_decodeUtf8_4 = _decodeUtf8_4(i_decodeUtf8_3);
                                      int i5 = i + 1;
                                                cArr[i] = (char) (55296 | (i_decodeUtf8_4 >> 10));
                                      if (i5 >= cArr.length) {
                                          cArr = this._textBuffer.finishCurrentSegment();
                                                    i5 = 0;
                                                }
                                                i = i5;
                                      i_decodeUtf8_3 = (i_decodeUtf8_4 & 1023) | 56320;
                                                break;
                                            default:
                                      if (i_decodeUtf8_3 < 32) {
                                          _throwUnquotedSpace(i_decodeUtf8_3, "string value");
                                                } else {
                                          _reportInvalidChar(i_decodeUtf8_3);
                                                }
                                                break;
                                        }
                              if (i >= cArr.length) {
                                  cArr = this._textBuffer.finishCurrentSegment();
                                            i2 = 0;
                                        } else {
                                            i2 = i;
                                        }
                              i = i2 + 1;
                                        cArr[i2] = (char) i_decodeUtf8_3;
                                        break;
                                    }
                          this._textBuffer.setCurrentLength(i);
                          return;
                                }
                                cArr[i] = (char) i_decodeUtf8_3;
                                i3 = i4;
                      i++;
                            } else {
                      this._inputPtr = i3;
                                break;
                            }
                        }
                    }
                }

      protected void _skipString() throws IOException {
          this._tokenIncomplete = false;
          int[] iArr = _icUTF8;
          byte[] bArr = this._inputBuffer;
                    while (true) {
              int i = this._inputPtr;
              int i2 = this._inputEnd;
              if (i >= i2) {
                  loadMoreGuaranteed();
                  i = this._inputPtr;
                  i2 = this._inputEnd;
                        }
                        while (true) {
                  if (i < i2) {
                      int i3 = i + 1;
                                int i4 = bArr[i] & Constants.UNKNOWN;
                      if (iArr[i4] != 0) {
                          this._inputPtr = i3;
                          if (i4 != 34) {
                                        switch (iArr[i4]) {
                                            case 1:
                                      _decodeEscaped();
                                                break;
                                            case 2:
                                      _skipUtf8_2(i4);
                                                break;
                                            case 3:
                                      _skipUtf8_3(i4);
                                                break;
                                            case 4:
                                      _skipUtf8_4(i4);
                                                break;
                                            default:
                                      if (i4 < 32) {
                                          _throwUnquotedSpace(i4, "string value");
                                                } else {
                                          _reportInvalidChar(i4);
                                                }
                                                break;
                                        }
                                    }
                          return;
                                }
                                i = i3;
                            } else {
                      this._inputPtr = i;
                                break;
                            }
                        }
                    }
                }

      protected JsonToken _handleUnexpectedValue(int i) throws IOException {
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
                  byte[] bArr = this._inputBuffer;
                            int i2 = this._inputPtr;
                            this._inputPtr = i2 + 1;
                            return _handleInvalidNumberStart(bArr[i2] & Constants.UNKNOWN, false);
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
                    int i_decodeUtf8_3;
                    int i;
          char[] cArrEmptyAndGetCurrentSegment = this._textBuffer.emptyAndGetCurrentSegment();
          int[] iArr = _icUTF8;
          byte[] bArr = this._inputBuffer;
                    int i2 = 0;
                    while (true) {
              if (this._inputPtr >= this._inputEnd) {
                  loadMoreGuaranteed();
                        }
              if (i2 >= cArrEmptyAndGetCurrentSegment.length) {
                  cArrEmptyAndGetCurrentSegment = this._textBuffer.finishCurrentSegment();
                            i2 = 0;
                        }
              int i3 = this._inputEnd;
              int length = this._inputPtr + (cArrEmptyAndGetCurrentSegment.length - i2);
              if (length >= i3) {
                            length = i3;
                        }
              while (this._inputPtr < length) {
                  int i4 = this._inputPtr;
                            this._inputPtr = i4 + 1;
                            int i5 = bArr[i4] & Constants.UNKNOWN;
                  if (i5 != 39 && iArr[i5] == 0) {
                                cArrEmptyAndGetCurrentSegment[i2] = (char) i5;
                      i2++;
                            } else {
                      if (i5 != 39) {
                                    switch (iArr[i5]) {
                                        case 1:
                                  i_decodeUtf8_3 = i5 == 39 ? i5 : _decodeEscaped();
                                            break;
                                        case 2:
                                  i_decodeUtf8_3 = _decodeUtf8_2(i5);
                                            break;
                                        case 3:
                                  if (this._inputEnd - this._inputPtr >= 2) {
                                      i_decodeUtf8_3 = _decodeUtf8_3fast(i5);
                                            } else {
                                      i_decodeUtf8_3 = _decodeUtf8_3(i5);
                                            }
                                            break;
                                        case 4:
                                  int i_decodeUtf8_4 = _decodeUtf8_4(i5);
                                  int i6 = i2 + 1;
                                            cArrEmptyAndGetCurrentSegment[i2] = (char) (55296 | (i_decodeUtf8_4 >> 10));
                                  if (i6 >= cArrEmptyAndGetCurrentSegment.length) {
                                      cArrEmptyAndGetCurrentSegment = this._textBuffer.finishCurrentSegment();
                                                i2 = 0;
                                            } else {
                                                i2 = i6;
                                            }
                                  i_decodeUtf8_3 = 56320 | (i_decodeUtf8_4 & 1023);
                                            break;
                                        default:
                                  if (i5 < 32) {
                                      _throwUnquotedSpace(i5, "string value");
                                            }
                                  _reportInvalidChar(i5);
                                            break;
                                    }
                          if (i2 >= cArrEmptyAndGetCurrentSegment.length) {
                              cArrEmptyAndGetCurrentSegment = this._textBuffer.finishCurrentSegment();
                                        i = 0;
                                    } else {
                                        i = i2;
                                    }
                          i2 = i + 1;
                                    cArrEmptyAndGetCurrentSegment[i] = (char) i_decodeUtf8_3;
                                    break;
                                }
                      this._textBuffer.setCurrentLength(i2);
                      return JsonToken.VALUE_STRING;
                            }
                        }
                    }
                }

      protected JsonToken _handleInvalidNumberStart(int i, boolean z) throws IOException {
                    String str;
          int i2 = i;
                    while (i2 == 73) {
              if (this._inputPtr >= this._inputEnd && !loadMore()) {
                  _reportInvalidEOFInValue();
                        }
              byte[] bArr = this._inputBuffer;
                        int i3 = this._inputPtr;
                        this._inputPtr = i3 + 1;
                        byte b = bArr[i3];
              if (b == 78) {
                  str = z ? "-INF" : "+INF";
                        } else {
                  if (b != 110) {
                                i2 = b;
                                break;
                            }
                  str = z ? "-Infinity" : "+Infinity";
                        }
              _matchToken(str, 3);
              if (isEnabled(JsonParser.Feature.ALLOW_NON_NUMERIC_NUMBERS)) {
                            return resetAsNaN(str, z ? Double.NEGATIVE_INFINITY : Double.POSITIVE_INFINITY);
                        }
              _reportError("Non-standard token '" + str + "': enable JsonParser.Feature.ALLOW_NON_NUMERIC_NUMBERS to allow");
                        i2 = b;
                    }
          reportUnexpectedNumberChar(i2, "expected digit (0-9) to follow minus sign, for valid numeric value");
          return null;
                }

      protected void _matchToken(String str, int i) throws IOException {
                    int i2;
          int length = str.length();
                    do {
              if ((this._inputPtr >= this._inputEnd && !loadMore()) || this._inputBuffer[this._inputPtr] != str.charAt(i)) {
                  _reportInvalidToken(str.substring(0, i));
                        }
              this._inputPtr++;
              i++;
                    } while (i < length);
          if ((this._inputPtr < this._inputEnd || loadMore()) && (i2 = this._inputBuffer[this._inputPtr] & Constants.UNKNOWN) >= 48 && i2 != 93 && i2 != 125 && Character.isJavaIdentifierPart((char) _decodeCharForError(i2))) {
              _reportInvalidToken(str.substring(0, i));
                    }
                }

      private final int _skipWS() throws IOException {
          int[] iArr = _icWS;
                    while (true) {
              if (this._inputPtr < this._inputEnd || loadMore()) {
                  byte[] bArr = this._inputBuffer;
                            int i = this._inputPtr;
                            this._inputPtr = i + 1;
                            int i2 = bArr[i] & Constants.UNKNOWN;
                            switch (iArr[i2]) {
                                case 0:
                          return i2;
                                case 1:
                                    break;
                                case 2:
                          _skipUtf8_2(i2);
                                    break;
                                case 3:
                          _skipUtf8_3(i2);
                                    break;
                                case 4:
                          _skipUtf8_4(i2);
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
                              return i2;
                                    }
                                    break;
                                    break;
                                case 47:
                          _skipComment();
                                    break;
                                default:
                          if (i2 < 32) {
                              _throwInvalidSpace(i2);
                                    }
                          _reportInvalidChar(i2);
                                    break;
                            }
                        } else {
                  throw _constructError("Unexpected end-of-input within/between " + this._parsingContext.getTypeDesc() + " entries");
                        }
                    }
                }

      private final int _skipWSOrEnd() throws IOException {
          int[] iArr = _icWS;
                    while (true) {
              if (this._inputPtr < this._inputEnd || loadMore()) {
                  byte[] bArr = this._inputBuffer;
                            int i = this._inputPtr;
                            this._inputPtr = i + 1;
                            int i2 = bArr[i] & Constants.UNKNOWN;
                            switch (iArr[i2]) {
                                case 0:
                          return i2;
                                case 1:
                                    break;
                                case 2:
                          _skipUtf8_2(i2);
                                    break;
                                case 3:
                          _skipUtf8_3(i2);
                                    break;
                                case 4:
                          _skipUtf8_4(i2);
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
                              return i2;
                                    }
                                    break;
                                    break;
                                case 47:
                          _skipComment();
                                    break;
                                default:
                          _reportInvalidChar(i2);
                                    break;
                            }
                        } else {
                  _handleEOF();
                  return -1;
                        }
                    }
                }

                
                
                
                
      private final int _skipColon() throws IOException {
                    int i;
                    int i2;
          if (this._inputPtr >= this._inputEnd) {
              loadMoreGuaranteed();
                    }
          byte[] bArr = this._inputBuffer;
                    int i3 = this._inputPtr;
                    this._inputPtr = i3 + 1;
                    byte b = bArr[i3];
          if (b == 58) {
              if (this._inputPtr < this._inputEnd && (i2 = this._inputBuffer[this._inputPtr] & Constants.UNKNOWN) > 32 && i2 != 47) {
                  this._inputPtr++;
                        }
              return i2;
                    }
                    while (true) {
                        switch (i) {
                            case 9:
                            case 32:
                      if (this._inputPtr >= this._inputEnd) {
                          loadMoreGuaranteed();
                                }
                      byte[] bArr2 = this._inputBuffer;
                                int i4 = this._inputPtr;
                                this._inputPtr = i4 + 1;
                                b = bArr2[i4];
                                break;
                            case 10:
                      this._currInputRow++;
                      this._currInputRowStart = this._inputPtr;
                      if (this._inputPtr >= this._inputEnd) {
                          loadMoreGuaranteed();
                                }
                      byte[] bArr3 = this._inputBuffer;
                                int i5 = this._inputPtr;
                                this._inputPtr = i5 + 1;
                                b = bArr3[i5];
                                break;
                            case 13:
                      _skipCR();
                      if (this._inputPtr >= this._inputEnd) {
                          loadMoreGuaranteed();
                                }
                      byte[] bArr4 = this._inputBuffer;
                                int i6 = this._inputPtr;
                                this._inputPtr = i6 + 1;
                                b = bArr4[i6];
                                break;
                            case 47:
                      _skipComment();
                      if (this._inputPtr >= this._inputEnd) {
                          loadMoreGuaranteed();
                                }
                      byte[] bArr5 = this._inputBuffer;
                                int i7 = this._inputPtr;
                                this._inputPtr = i7 + 1;
                                b = bArr5[i7];
                                break;
                        }
              if (i < 32) {
                  _throwInvalidSpace(i);
                        }
              if (i != 58) {
                  _reportUnexpectedChar(i, "was expecting a colon to separate field name and value");
                        }
                    }
                    while (true) {
              if (this._inputPtr < this._inputEnd || loadMore()) {
                  byte[] bArr6 = this._inputBuffer;
                            int i8 = this._inputPtr;
                            this._inputPtr = i8 + 1;
                            i2 = bArr6[i8] & Constants.UNKNOWN;
                  if (i2 > 32) {
                      if (i2 != 47) {
                          return i2;
                                }
                      _skipComment();
                            } else if (i2 != 32) {
                      if (i2 == 10) {
                          this._currInputRow++;
                          this._currInputRowStart = this._inputPtr;
                                } else if (i2 == 13) {
                          _skipCR();
                                } else if (i2 != 9) {
                          _throwInvalidSpace(i2);
                                }
                            }
                        } else {
                  throw _constructError("Unexpected end-of-input within/between " + this._parsingContext.getTypeDesc() + " entries");
                        }
                    }
                }

      private final void _skipComment() throws IOException {
          if (!isEnabled(JsonParser.Feature.ALLOW_COMMENTS)) {
              _reportUnexpectedChar(47, "maybe a (non-standard) comment? (not recognized as one since Feature 'ALLOW_COMMENTS' not enabled for parser)");
                    }
          if (this._inputPtr >= this._inputEnd && !loadMore()) {
              _reportInvalidEOF(" in a comment");
                    }
          byte[] bArr = this._inputBuffer;
                    int i = this._inputPtr;
                    this._inputPtr = i + 1;
                    int i2 = bArr[i] & Constants.UNKNOWN;
          if (i2 == 47) {
              _skipLine();
                    } else if (i2 == 42) {
              _skipCComment();
                    } else {
              _reportUnexpectedChar(i2, "was expecting either '*' or '/' for a comment");
                    }
                }

      private final void _skipCComment() throws IOException {
          int[] inputCodeComment = CharTypes.getInputCodeComment();
                    while (true) {
              if (this._inputPtr < this._inputEnd || loadMore()) {
                  byte[] bArr = this._inputBuffer;
                            int i = this._inputPtr;
                            this._inputPtr = i + 1;
                            int i2 = bArr[i] & Constants.UNKNOWN;
                  int i3 = inputCodeComment[i2];
                  if (i3 != 0) {
                                switch (i3) {
                                    case 2:
                              _skipUtf8_2(i2);
                                        break;
                                    case 3:
                              _skipUtf8_3(i2);
                                        break;
                                    case 4:
                              _skipUtf8_4(i2);
                                        break;
                                    case 10:
                              this._currInputRow++;
                              this._currInputRowStart = this._inputPtr;
                                        break;
                                    case 13:
                              _skipCR();
                                        break;
                                    case 42:
                              if (this._inputPtr < this._inputEnd || loadMore()) {
                                  if (this._inputBuffer[this._inputPtr] == 47) {
                                      this._inputPtr++;
                                      return;
                                            }
                                        }
                                        break;
                                    default:
                              _reportInvalidChar(i2);
                                        break;
                                }
                            }
                        }
                    }
          _reportInvalidEOF(" in a comment");
                }

      private final boolean _skipYAMLComment() throws IOException {
          if (!isEnabled(JsonParser.Feature.ALLOW_YAML_COMMENTS)) {
              return false;
                    }
          _skipLine();
          return true;
                }

      private final void _skipLine() throws IOException {
          int[] inputCodeComment = CharTypes.getInputCodeComment();
                    while (true) {
              if (this._inputPtr < this._inputEnd || loadMore()) {
                  byte[] bArr = this._inputBuffer;
                            int i = this._inputPtr;
                            this._inputPtr = i + 1;
                            int i2 = bArr[i] & Constants.UNKNOWN;
                  int i3 = inputCodeComment[i2];
                  if (i3 != 0) {
                                switch (i3) {
                                    case 2:
                              _skipUtf8_2(i2);
                                        break;
                                    case 3:
                              _skipUtf8_3(i2);
                                        break;
                                    case 4:
                              _skipUtf8_4(i2);
                                        break;
                                    case 10:
                              this._currInputRow++;
                              this._currInputRowStart = this._inputPtr;
                              return;
                                    case 13:
                              _skipCR();
                              return;
                                    case 42:
                                        break;
                                    default:
                              if (i3 < 0) {
                                  _reportInvalidChar(i2);
                                        }
                                        break;
                                }
                            }
                        } else {
                  return;
                        }
                    }
                }

                @Override 
      protected char _decodeEscaped() throws IOException {
          if (this._inputPtr >= this._inputEnd && !loadMore()) {
              _reportInvalidEOF(" in character escape sequence");
                    }
          byte[] bArr = this._inputBuffer;
                    int i = this._inputPtr;
                    this._inputPtr = i + 1;
                    byte b = bArr[i];
                    switch (b) {
                        case 34:
                        case 47:
                        case 92:
                  return (char) b;
                        case 98:
                  return '\b';
                        case 102:
                  return '\f';
                        case 110:
                  return '\n';
                        case 114:
                  return '\r';
                        case 116:
                  return '\t';
                        case 117:
                            int i2 = 0;
                  for (int i3 = 0; i3 < 4; i3++) {
                      if (this._inputPtr >= this._inputEnd && !loadMore()) {
                          _reportInvalidEOF(" in character escape sequence");
                                }
                      byte[] bArr2 = this._inputBuffer;
                                int i4 = this._inputPtr;
                                this._inputPtr = i4 + 1;
                                byte b2 = bArr2[i4];
                      int iCharToHex = CharTypes.charToHex(b2);
                      if (iCharToHex < 0) {
                          _reportUnexpectedChar(b2, "expected a hex-digit for character escape sequence");
                                }
                      i2 = (i2 << 4) | iCharToHex;
                            }
                  return (char) i2;
                        default:
                  return _handleUnrecognizedCharacterEscape((char) _decodeCharForError(b));
                    }
                }

      protected int _decodeCharForError(int i) throws IOException {
                    char c;
          if (i < 0) {
              if ((i & 224) == 192) {
                  i &= 31;
                            c = 1;
                        } else if ((i & 240) == 224) {
                  i &= 15;
                            c = 2;
                        } else if ((i & 248) == 240) {
                  i &= 7;
                  c = 3;
                        } else {
                  _reportInvalidInitial(i & 255);
                            c = 1;
                        }
              int iNextByte = nextByte();
              if ((iNextByte & 192) != 128) {
                  _reportInvalidOther(iNextByte & 255);
                        }
              int i2 = (i << 6) | (iNextByte & 63);
              if (c > 1) {
                  int iNextByte2 = nextByte();
                  if ((iNextByte2 & 192) != 128) {
                      _reportInvalidOther(iNextByte2 & 255);
                            }
                  int i3 = (i2 << 6) | (iNextByte2 & 63);
                  if (c > 2) {
                      int iNextByte3 = nextByte();
                      if ((iNextByte3 & 192) != 128) {
                          _reportInvalidOther(iNextByte3 & 255);
                                }
                      return (i3 << 6) | (iNextByte3 & 63);
                            }
                  return i3;
                        }
              return i2;
                    }
          return i;
                }

      private final int _decodeUtf8_2(int i) throws IOException {
          if (this._inputPtr >= this._inputEnd) {
              loadMoreGuaranteed();
                    }
          byte[] bArr = this._inputBuffer;
                    int i2 = this._inputPtr;
                    this._inputPtr = i2 + 1;
                    byte b = bArr[i2];
          if ((b & 192) != 128) {
              _reportInvalidOther(b & Constants.UNKNOWN, this._inputPtr);
                    }
          return (b & 63) | ((i & 31) << 6);
                }

      private final int _decodeUtf8_3(int i) throws IOException {
          if (this._inputPtr >= this._inputEnd) {
              loadMoreGuaranteed();
                    }
          int i2 = i & 15;
          byte[] bArr = this._inputBuffer;
                    int i3 = this._inputPtr;
                    this._inputPtr = i3 + 1;
                    byte b = bArr[i3];
          if ((b & 192) != 128) {
              _reportInvalidOther(b & Constants.UNKNOWN, this._inputPtr);
                    }
          int i4 = (i2 << 6) | (b & 63);
          if (this._inputPtr >= this._inputEnd) {
              loadMoreGuaranteed();
                    }
          byte[] bArr2 = this._inputBuffer;
                    int i5 = this._inputPtr;
                    this._inputPtr = i5 + 1;
                    byte b2 = bArr2[i5];
          if ((b2 & 192) != 128) {
              _reportInvalidOther(b2 & Constants.UNKNOWN, this._inputPtr);
                    }
          return (i4 << 6) | (b2 & 63);
                }

      private final int _decodeUtf8_3fast(int i) throws IOException {
          int i2 = i & 15;
          byte[] bArr = this._inputBuffer;
                    int i3 = this._inputPtr;
                    this._inputPtr = i3 + 1;
                    byte b = bArr[i3];
          if ((b & 192) != 128) {
              _reportInvalidOther(b & Constants.UNKNOWN, this._inputPtr);
                    }
          int i4 = (i2 << 6) | (b & 63);
          byte[] bArr2 = this._inputBuffer;
                    int i5 = this._inputPtr;
                    this._inputPtr = i5 + 1;
                    byte b2 = bArr2[i5];
          if ((b2 & 192) != 128) {
              _reportInvalidOther(b2 & Constants.UNKNOWN, this._inputPtr);
                    }
          return (i4 << 6) | (b2 & 63);
                }

      private final int _decodeUtf8_4(int i) throws IOException {
          if (this._inputPtr >= this._inputEnd) {
              loadMoreGuaranteed();
                    }
          byte[] bArr = this._inputBuffer;
                    int i2 = this._inputPtr;
                    this._inputPtr = i2 + 1;
                    byte b = bArr[i2];
          if ((b & 192) != 128) {
              _reportInvalidOther(b & Constants.UNKNOWN, this._inputPtr);
                    }
          int i3 = (b & 63) | ((i & 7) << 6);
          if (this._inputPtr >= this._inputEnd) {
              loadMoreGuaranteed();
                    }
          byte[] bArr2 = this._inputBuffer;
                    int i4 = this._inputPtr;
                    this._inputPtr = i4 + 1;
                    byte b2 = bArr2[i4];
          if ((b2 & 192) != 128) {
              _reportInvalidOther(b2 & Constants.UNKNOWN, this._inputPtr);
                    }
          int i5 = (i3 << 6) | (b2 & 63);
          if (this._inputPtr >= this._inputEnd) {
              loadMoreGuaranteed();
                    }
          byte[] bArr3 = this._inputBuffer;
                    int i6 = this._inputPtr;
                    this._inputPtr = i6 + 1;
                    byte b3 = bArr3[i6];
          if ((b3 & 192) != 128) {
              _reportInvalidOther(b3 & Constants.UNKNOWN, this._inputPtr);
                    }
          return ((i5 << 6) | (b3 & 63)) - 65536;
                }

      private final void _skipUtf8_2(int i) throws IOException {
          if (this._inputPtr >= this._inputEnd) {
              loadMoreGuaranteed();
                    }
          byte[] bArr = this._inputBuffer;
                    int i2 = this._inputPtr;
                    this._inputPtr = i2 + 1;
                    byte b = bArr[i2];
          if ((b & 192) != 128) {
              _reportInvalidOther(b & Constants.UNKNOWN, this._inputPtr);
                    }
                }

      private final void _skipUtf8_3(int i) throws IOException {
          if (this._inputPtr >= this._inputEnd) {
              loadMoreGuaranteed();
                    }
          byte[] bArr = this._inputBuffer;
                    int i2 = this._inputPtr;
                    this._inputPtr = i2 + 1;
                    byte b = bArr[i2];
          if ((b & 192) != 128) {
              _reportInvalidOther(b & Constants.UNKNOWN, this._inputPtr);
                    }
          if (this._inputPtr >= this._inputEnd) {
              loadMoreGuaranteed();
                    }
          byte[] bArr2 = this._inputBuffer;
                    int i3 = this._inputPtr;
                    this._inputPtr = i3 + 1;
                    byte b2 = bArr2[i3];
          if ((b2 & 192) != 128) {
              _reportInvalidOther(b2 & Constants.UNKNOWN, this._inputPtr);
                    }
                }

      private final void _skipUtf8_4(int i) throws IOException {
          if (this._inputPtr >= this._inputEnd) {
              loadMoreGuaranteed();
                    }
          byte[] bArr = this._inputBuffer;
                    int i2 = this._inputPtr;
                    this._inputPtr = i2 + 1;
                    byte b = bArr[i2];
          if ((b & 192) != 128) {
              _reportInvalidOther(b & Constants.UNKNOWN, this._inputPtr);
                    }
          if (this._inputPtr >= this._inputEnd) {
              loadMoreGuaranteed();
                    }
          byte[] bArr2 = this._inputBuffer;
                    int i3 = this._inputPtr;
                    this._inputPtr = i3 + 1;
                    byte b2 = bArr2[i3];
          if ((b2 & 192) != 128) {
              _reportInvalidOther(b2 & Constants.UNKNOWN, this._inputPtr);
                    }
          if (this._inputPtr >= this._inputEnd) {
              loadMoreGuaranteed();
                    }
          byte[] bArr3 = this._inputBuffer;
                    int i4 = this._inputPtr;
                    this._inputPtr = i4 + 1;
                    byte b3 = bArr3[i4];
          if ((b3 & 192) != 128) {
              _reportInvalidOther(b3 & Constants.UNKNOWN, this._inputPtr);
                    }
                }

      protected final void _skipCR() throws IOException {
          if ((this._inputPtr < this._inputEnd || loadMore()) && this._inputBuffer[this._inputPtr] == 10) {
              this._inputPtr++;
                    }
          this._currInputRow++;
          this._currInputRowStart = this._inputPtr;
                }

      private int nextByte() throws IOException {
          if (this._inputPtr >= this._inputEnd) {
              loadMoreGuaranteed();
                    }
          byte[] bArr = this._inputBuffer;
                    int i = this._inputPtr;
                    this._inputPtr = i + 1;
                    return bArr[i] & Constants.UNKNOWN;
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
              byte[] bArr = this._inputBuffer;
                        int i = this._inputPtr;
                        this._inputPtr = i + 1;
              char c_decodeCharForError = (char) _decodeCharForError(bArr[i]);
              if (!Character.isJavaIdentifierPart(c_decodeCharForError)) {
                            break;
                        } else {
                  sb.append(c_decodeCharForError);
                        }
                    }
          _reportError("Unrecognized token '" + sb.toString() + "': was expecting " + str2);
                }

      protected void _reportInvalidChar(int i) throws JsonParseException {
          if (i < 32) {
              _throwInvalidSpace(i);
                    }
          _reportInvalidInitial(i);
                }

      protected void _reportInvalidInitial(int i) throws JsonParseException {
          _reportError("Invalid UTF-8 start byte 0x" + Integer.toHexString(i));
                }

      protected void _reportInvalidOther(int i) throws JsonParseException {
          _reportError("Invalid UTF-8 middle byte 0x" + Integer.toHexString(i));
                }

      protected void _reportInvalidOther(int i, int i2) throws JsonParseException {
          this._inputPtr = i2;
          _reportInvalidOther(i);
                }

      public static int[] growArrayBy(int[] iArr, int i) {
                    return iArr == null ? new int[i] : ArraysCompat.copyOf(iArr, iArr.length + i);
                }

      protected final byte[] _decodeBase64(Base64Variant base64Variant) throws IOException {
          ByteArrayBuilder byteArrayBuilder_getByteArrayBuilder = _getByteArrayBuilder();
                    while (true) {
              if (this._inputPtr >= this._inputEnd) {
                  loadMoreGuaranteed();
                        }
              byte[] bArr = this._inputBuffer;
                        int i = this._inputPtr;
                        this._inputPtr = i + 1;
                        int i2 = bArr[i] & Constants.UNKNOWN;
              if (i2 > 32) {
                  int iDecodeBase64Char = base64Variant.decodeBase64Char(i2);
                  if (iDecodeBase64Char < 0) {
                      if (i2 == 34) {
                          return byteArrayBuilder_getByteArrayBuilder.toByteArray();
                                }
                      iDecodeBase64Char = _decodeBase64Escape(base64Variant, i2, 0);
                      if (iDecodeBase64Char < 0) {
                                    continue;
                                }
                            }
                  if (this._inputPtr >= this._inputEnd) {
                      loadMoreGuaranteed();
                            }
                  byte[] bArr2 = this._inputBuffer;
                            int i3 = this._inputPtr;
                            this._inputPtr = i3 + 1;
                            int i4 = bArr2[i3] & Constants.UNKNOWN;
                  int iDecodeBase64Char2 = base64Variant.decodeBase64Char(i4);
                  if (iDecodeBase64Char2 < 0) {
                      iDecodeBase64Char2 = _decodeBase64Escape(base64Variant, i4, 1);
                            }
                  int i5 = iDecodeBase64Char2 | (iDecodeBase64Char << 6);
                  if (this._inputPtr >= this._inputEnd) {
                      loadMoreGuaranteed();
                            }
                  byte[] bArr3 = this._inputBuffer;
                            int i6 = this._inputPtr;
                            this._inputPtr = i6 + 1;
                            int i7 = bArr3[i6] & Constants.UNKNOWN;
                  int iDecodeBase64Char3 = base64Variant.decodeBase64Char(i7);
                  if (iDecodeBase64Char3 < 0) {
                      if (iDecodeBase64Char3 != -2) {
                          if (i7 == 34 && !base64Variant.usesPadding()) {
                              byteArrayBuilder_getByteArrayBuilder.append(i5 >> 4);
                              return byteArrayBuilder_getByteArrayBuilder.toByteArray();
                                    }
                          iDecodeBase64Char3 = _decodeBase64Escape(base64Variant, i7, 2);
                                }
                      if (iDecodeBase64Char3 == -2) {
                          if (this._inputPtr >= this._inputEnd) {
                              loadMoreGuaranteed();
                                    }
                          byte[] bArr4 = this._inputBuffer;
                                    int i8 = this._inputPtr;
                                    this._inputPtr = i8 + 1;
                                    int i9 = bArr4[i8] & Constants.UNKNOWN;
                          if (!base64Variant.usesPaddingChar(i9)) {
                              throw reportInvalidBase64Char(base64Variant, i9, 3, "expected padding character '" + base64Variant.getPaddingChar() + "'");
                                    }
                          byteArrayBuilder_getByteArrayBuilder.append(i5 >> 4);
                                }
                            }
                  int i10 = (i5 << 6) | iDecodeBase64Char3;
                  if (this._inputPtr >= this._inputEnd) {
                      loadMoreGuaranteed();
                            }
                  byte[] bArr5 = this._inputBuffer;
                            int i11 = this._inputPtr;
                            this._inputPtr = i11 + 1;
                            int i12 = bArr5[i11] & Constants.UNKNOWN;
                  int iDecodeBase64Char4 = base64Variant.decodeBase64Char(i12);
                  if (iDecodeBase64Char4 < 0) {
                      if (iDecodeBase64Char4 != -2) {
                          if (i12 == 34 && !base64Variant.usesPadding()) {
                              byteArrayBuilder_getByteArrayBuilder.appendTwoBytes(i10 >> 2);
                              return byteArrayBuilder_getByteArrayBuilder.toByteArray();
                                    }
                          iDecodeBase64Char4 = _decodeBase64Escape(base64Variant, i12, 3);
                                }
                      if (iDecodeBase64Char4 == -2) {
                          byteArrayBuilder_getByteArrayBuilder.appendTwoBytes(i10 >> 2);
                                }
                            }
                  byteArrayBuilder_getByteArrayBuilder.appendThreeBytes(iDecodeBase64Char4 | (i10 << 6));
                        }
                    }
                }
            }
