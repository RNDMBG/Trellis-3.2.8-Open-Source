            package com.fasterxml.jackson.core.base;

            import com.fasterxml.jackson.core.Base64Variant;
            import com.fasterxml.jackson.core.JsonParseException;
            import com.fasterxml.jackson.core.JsonParser;
            import com.fasterxml.jackson.core.JsonProcessingException;
            import com.fasterxml.jackson.core.JsonStreamContext;
            import com.fasterxml.jackson.core.JsonToken;
            import com.fasterxml.jackson.core.Version;
            import com.fasterxml.jackson.core.p003io.NumberInput;
            import com.fasterxml.jackson.core.util.ByteArrayBuilder;
            import com.fasterxml.jackson.core.util.VersionUtil;
            import java.io.IOException;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 65 */    public abstract class ParserMinimalBase extends JsonParser {
                protected static final int INT_BACKSLASH = 92;
                protected static final int INT_COLON = 58;
                protected static final int INT_COMMA = 44;
                protected static final int INT_CR = 13;
                protected static final int INT_LBRACKET = 91;
                protected static final int INT_LCURLY = 123;
                protected static final int INT_LF = 10;
                protected static final int INT_QUOTE = 34;
                protected static final int INT_RBRACKET = 93;
                protected static final int INT_RCURLY = 125;
                protected static final int INT_SLASH = 47;
                protected static final int INT_SPACE = 32;
                protected static final int INT_TAB = 9;
                protected JsonToken _currToken;
                protected JsonToken _lastClearedToken;

                protected abstract void _handleEOF() throws JsonParseException;

                @Override // com.fasterxml.jackson.core.JsonParser, java.io.Closeable, java.lang.AutoCloseable
                public abstract void close() throws IOException;

                @Override // com.fasterxml.jackson.core.JsonParser
                public abstract byte[] getBinaryValue(Base64Variant base64Variant) throws IOException;

                @Override // com.fasterxml.jackson.core.JsonParser
                public abstract String getCurrentName() throws IOException;

                @Override // com.fasterxml.jackson.core.JsonParser
                public abstract JsonStreamContext getParsingContext();

                @Override // com.fasterxml.jackson.core.JsonParser
                public abstract String getText() throws IOException;

                @Override // com.fasterxml.jackson.core.JsonParser
                public abstract char[] getTextCharacters() throws IOException;

                @Override // com.fasterxml.jackson.core.JsonParser
                public abstract int getTextLength() throws IOException;

                @Override // com.fasterxml.jackson.core.JsonParser
                public abstract int getTextOffset() throws IOException;

                @Override // com.fasterxml.jackson.core.JsonParser
                public abstract boolean hasTextCharacters();

                @Override // com.fasterxml.jackson.core.JsonParser
                public abstract boolean isClosed();

                @Override // com.fasterxml.jackson.core.JsonParser
                public abstract JsonToken nextToken() throws IOException;

                @Override // com.fasterxml.jackson.core.JsonParser
                public abstract void overrideCurrentName(String str);

/* 66 */        protected ParserMinimalBase() {
                }

/* 68 */        protected ParserMinimalBase(int i) {
/* 69 */            super(i);
                }

                @Override // com.fasterxml.jackson.core.JsonParser, com.fasterxml.jackson.core.Versioned
/* 73 */        public Version version() {
/* 74 */            return VersionUtil.versionFor(getClass());
                }

                @Override // com.fasterxml.jackson.core.JsonParser
/* 100 */       public JsonToken getCurrentToken() {
/* 101 */           return this._currToken;
                }

                @Override // com.fasterxml.jackson.core.JsonParser
/* 105 */       public final int getCurrentTokenId() {
/* 106 */           JsonToken jsonToken = this._currToken;
/* 107 */           if (jsonToken == null) {
                        return 0;
                    }
                    return jsonToken.m524id();
                }

                @Override // com.fasterxml.jackson.core.JsonParser
/* 111 */       public boolean hasCurrentToken() {
                    return this._currToken != null;
                }

                @Override // com.fasterxml.jackson.core.JsonParser
/* 122 */       public JsonToken nextValue() throws IOException {
/* 123 */           JsonToken jsonTokenNextToken = nextToken();
/* 124 */           if (jsonTokenNextToken == JsonToken.FIELD_NAME) {
/* 125 */               return nextToken();
                    }
/* 127 */           return jsonTokenNextToken;
                }

                @Override // com.fasterxml.jackson.core.JsonParser
/* 132 */       public JsonParser skipChildren() throws IOException {
/* 133 */           if (this._currToken == JsonToken.START_OBJECT || this._currToken == JsonToken.START_ARRAY) {
/* 137 */               int i = 1;
                        while (true) {
/* 143 */                   JsonToken jsonTokenNextToken = nextToken();
/* 144 */                   if (jsonTokenNextToken == null) {
/* 145 */                       _handleEOF();
                                break;
                            }
/* 152 */                   if (jsonTokenNextToken.isStructStart()) {
/* 153 */                       i++;
                            } else if (jsonTokenNextToken.isStructEnd() && (i = i - 1) == 0) {
                                break;
                            }
                        }
                    }
/* 156 */           return this;
                }

                @Override // com.fasterxml.jackson.core.JsonParser
/* 194 */       public void clearCurrentToken() {
/* 195 */           if (this._currToken != null) {
/* 196 */               this._lastClearedToken = this._currToken;
/* 197 */               this._currToken = null;
                    }
                }

                @Override // com.fasterxml.jackson.core.JsonParser
/* 202 */       public JsonToken getLastClearedToken() {
/* 203 */           return this._lastClearedToken;
                }

                @Override // com.fasterxml.jackson.core.JsonParser
/* 248 */       public boolean getValueAsBoolean(boolean z) throws IOException {
/* 249 */           JsonToken jsonToken = this._currToken;
/* 250 */           if (jsonToken == null) {
                        return z;
                    }
                    switch (jsonToken.m524id()) {
                        case 6:
/* 253 */                   String strTrim = getText().trim();
/* 254 */                   if ("true".equals(strTrim)) {
                                return true;
                            }
/* 257 */                   if (!"false".equals(strTrim) && !_hasTextualNull(strTrim)) {
                                return z;
                            }
                            return false;
                        case 7:
                            return getIntValue() != 0;
                        case 8:
                        default:
                            return z;
                        case 9:
                            return true;
                        case 10:
                        case 11:
                            return false;
                        case 12:
/* 272 */                   Object embeddedObject = getEmbeddedObject();
                            return embeddedObject instanceof Boolean ? ((Boolean) embeddedObject).booleanValue() : z;
                    }
                }

                @Override // com.fasterxml.jackson.core.JsonParser
/* 285 */       public int getValueAsInt(int i) throws IOException {
/* 286 */           JsonToken jsonToken = this._currToken;
/* 287 */           if (jsonToken != null) {
                        switch (jsonToken.m524id()) {
                            case 6:
/* 290 */                       String text = getText();
/* 291 */                       if (_hasTextualNull(text)) {
                                    return 0;
                                }
/* 294 */                       return NumberInput.parseAsInt(text, i);
                            case 7:
                            case 8:
/* 297 */                       return getIntValue();
                            case 9:
/* 299 */                       return 1;
                            case 10:
                                return 0;
                            case 11:
                                return 0;
                            case 12:
/* 305 */                       Object embeddedObject = getEmbeddedObject();
/* 306 */                       if (embeddedObject instanceof Number) {
/* 307 */                           return ((Number) embeddedObject).intValue();
                                }
/* 311 */                       return i;
                            default:
/* 311 */                       return i;
                        }
                    }
/* 311 */           return i;
                }

                @Override // com.fasterxml.jackson.core.JsonParser
/* 316 */       public long getValueAsLong(long j) throws IOException {
/* 317 */           JsonToken jsonToken = this._currToken;
/* 318 */           if (jsonToken != null) {
                        switch (jsonToken.m524id()) {
                            case 6:
/* 321 */                       String text = getText();
/* 322 */                       if (_hasTextualNull(text)) {
                                    return 0L;
                                }
/* 325 */                       return NumberInput.parseAsLong(text, j);
                            case 7:
                            case 8:
/* 328 */                       return getLongValue();
                            case 9:
/* 330 */                       return 1L;
                            case 10:
                            case 11:
                                return 0L;
                            case 12:
/* 335 */                       Object embeddedObject = getEmbeddedObject();
/* 336 */                       if (embeddedObject instanceof Number) {
/* 337 */                           return ((Number) embeddedObject).longValue();
                                }
/* 341 */                       return j;
                            default:
/* 341 */                       return j;
                        }
                    }
/* 341 */           return j;
                }

                @Override // com.fasterxml.jackson.core.JsonParser
/* 346 */       public double getValueAsDouble(double d) throws IOException {
/* 347 */           JsonToken jsonToken = this._currToken;
/* 348 */           if (jsonToken != null) {
                        switch (jsonToken.m524id()) {
                            case 6:
/* 351 */                       String text = getText();
/* 352 */                       if (_hasTextualNull(text)) {
                                    return 0.0d;
                                }
/* 355 */                       return NumberInput.parseAsDouble(text, d);
                            case 7:
                            case 8:
/* 358 */                       return getDoubleValue();
                            case 9:
/* 360 */                       return 1.0d;
                            case 10:
                            case 11:
                                return 0.0d;
                            case 12:
/* 365 */                       Object embeddedObject = getEmbeddedObject();
/* 366 */                       if (embeddedObject instanceof Number) {
/* 367 */                           return ((Number) embeddedObject).doubleValue();
                                }
/* 371 */                       return d;
                            default:
/* 371 */                       return d;
                        }
                    }
/* 371 */           return d;
                }

                @Override // com.fasterxml.jackson.core.JsonParser
/* 376 */       public String getValueAsString(String str) throws IOException {
                    return (this._currToken == JsonToken.VALUE_STRING || !(this._currToken == null || this._currToken == JsonToken.VALUE_NULL || !this._currToken.isScalarValue())) ? getText() : str;
                }

/* 399 */       protected void _decodeBase64(String str, ByteArrayBuilder byteArrayBuilder, Base64Variant base64Variant) throws IOException {
                    try {
/* 400 */               base64Variant.decode(str, byteArrayBuilder);
                    } catch (IllegalArgumentException e) {
/* 402 */               _reportError(e.getMessage());
                    }
                }

                @Deprecated
/* 416 */       protected void _reportInvalidBase64(Base64Variant base64Variant, char c, int i, String str) throws JsonParseException {
                    String str2;
/* 417 */           if (c <= ' ') {
/* 418 */               str2 = "Illegal white space character (code 0x" + Integer.toHexString(c) + ") as character #" + (i + 1) + " of 4-char base64 unit: can only used between units";
                    } else if (base64Variant.usesPaddingChar(c)) {
/* 420 */               str2 = "Unexpected padding character ('" + base64Variant.getPaddingChar() + "') as character #" + (i + 1) + " of 4-char base64 unit: padding only legal as 3rd or 4th character";
                    } else if (!Character.isDefined(c) || Character.isISOControl(c)) {
/* 423 */               str2 = "Illegal character (code 0x" + Integer.toHexString(c) + ") in base64 content";
                    } else {
/* 425 */               str2 = "Illegal character '" + c + "' (code 0x" + Integer.toHexString(c) + ") in base64 content";
                    }
/* 427 */           if (str != null) {
/* 428 */               str2 = str2 + ": " + str;
                    }
/* 430 */           throw _constructError(str2);
                }

                @Deprecated
/* 438 */       protected void _reportBase64EOF() throws JsonParseException {
/* 439 */           throw _constructError("Unexpected end-of-String in base64 content");
                }

/* 455 */       protected boolean _hasTextualNull(String str) {
/* 456 */           return "null".equals(str);
                }

/* 467 */       protected void _reportUnexpectedChar(int i, String str) throws JsonParseException {
/* 468 */           if (i < 0) {
/* 469 */               _reportInvalidEOF();
                    }
/* 471 */           String str2 = "Unexpected character (" + _getCharDesc(i) + ")";
/* 472 */           if (str != null) {
/* 473 */               str2 = str2 + ": " + str;
                    }
/* 475 */           _reportError(str2);
                }

/* 480 */       protected void _reportInvalidEOF() throws JsonParseException {
/* 481 */           _reportInvalidEOF(" in " + this._currToken);
                }

/* 486 */       protected void _reportInvalidEOF(String str) throws JsonParseException {
/* 487 */           _reportError("Unexpected end-of-input" + str);
                }

/* 490 */       protected void _reportInvalidEOFInValue() throws JsonParseException {
/* 491 */           _reportInvalidEOF(" in a value");
                }

/* 494 */       protected void _reportMissingRootWS(int i) throws JsonParseException {
/* 495 */           _reportUnexpectedChar(i, "Expected space separating root-level values");
                }

/* 500 */       protected void _throwInvalidSpace(int i) throws JsonParseException {
/* 503 */           _reportError("Illegal character (" + _getCharDesc((char) i) + "): only regular white space (\\r, \\n, \\t) is allowed between tokens");
                }

/* 514 */       protected void _throwUnquotedSpace(int i, String str) throws JsonParseException {
/* 515 */           if (!isEnabled(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS) || i >= 32) {
/* 518 */               _reportError("Illegal unquoted character (" + _getCharDesc((char) i) + "): has to be escaped using backslash to be included in " + str);
                    }
                }

/* 524 */       protected char _handleUnrecognizedCharacterEscape(char c) throws JsonProcessingException {
/* 525 */           if (!isEnabled(JsonParser.Feature.ALLOW_BACKSLASH_ESCAPING_ANY_CHARACTER) && (c != '\'' || !isEnabled(JsonParser.Feature.ALLOW_SINGLE_QUOTES))) {
/* 532 */               _reportError("Unrecognized character escape " + _getCharDesc(c));
                    }
/* 533 */           return c;
                }

/* 543 */       protected static final String _getCharDesc(int i) {
/* 544 */           char c = (char) i;
/* 545 */           if (Character.isISOControl(c)) {
/* 546 */               return "(CTRL-CHAR, code " + i + ")";
                    }
/* 548 */           if (i > 255) {
/* 549 */               return "'" + c + "' (code " + i + " / 0x" + Integer.toHexString(i) + ")";
                    }
/* 551 */           return "'" + c + "' (code " + i + ")";
                }

/* 556 */       protected final void _reportError(String str) throws JsonParseException {
/* 557 */           throw _constructError(str);
                }

/* 562 */       protected final void _wrapError(String str, Throwable th) throws JsonParseException {
/* 563 */           throw _constructError(str, th);
                }

/* 566 */       protected final void _throwInternal() {
/* 567 */           VersionUtil.throwInternal();
                }

/* 571 */       protected final JsonParseException _constructError(String str, Throwable th) {
/* 572 */           return new JsonParseException(str, getCurrentLocation(), th);
                }
            }
