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
            
            
    public abstract class ParserMinimalBase extends JsonParser {
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

                @Override 
                public abstract void close() throws IOException;

                @Override 
                public abstract byte[] getBinaryValue(Base64Variant base64Variant) throws IOException;

                @Override 
                public abstract String getCurrentName() throws IOException;

                @Override 
                public abstract JsonStreamContext getParsingContext();

                @Override 
                public abstract String getText() throws IOException;

                @Override 
                public abstract char[] getTextCharacters() throws IOException;

                @Override 
                public abstract int getTextLength() throws IOException;

                @Override 
                public abstract int getTextOffset() throws IOException;

                @Override 
                public abstract boolean hasTextCharacters();

                @Override 
                public abstract boolean isClosed();

                @Override 
                public abstract JsonToken nextToken() throws IOException;

                @Override 
                public abstract void overrideCurrentName(String str);

        protected ParserMinimalBase() {
                }

        protected ParserMinimalBase(int i) {
            super(i);
                }

                @Override 
        public Version version() {
            return VersionUtil.versionFor(getClass());
                }

                @Override 
       public JsonToken getCurrentToken() {
           return this._currToken;
                }

                @Override 
       public final int getCurrentTokenId() {
           JsonToken jsonToken = this._currToken;
           if (jsonToken == null) {
                        return 0;
                    }
                    return jsonToken.m524id();
                }

                @Override 
       public boolean hasCurrentToken() {
                    return this._currToken != null;
                }

                @Override 
       public JsonToken nextValue() throws IOException {
           JsonToken jsonTokenNextToken = nextToken();
           if (jsonTokenNextToken == JsonToken.FIELD_NAME) {
               return nextToken();
                    }
           return jsonTokenNextToken;
                }

                @Override 
       public JsonParser skipChildren() throws IOException {
           if (this._currToken == JsonToken.START_OBJECT || this._currToken == JsonToken.START_ARRAY) {
               int i = 1;
                        while (true) {
                   JsonToken jsonTokenNextToken = nextToken();
                   if (jsonTokenNextToken == null) {
                       _handleEOF();
                                break;
                            }
                   if (jsonTokenNextToken.isStructStart()) {
                       i++;
                            } else if (jsonTokenNextToken.isStructEnd() && (i = i - 1) == 0) {
                                break;
                            }
                        }
                    }
           return this;
                }

                @Override 
       public void clearCurrentToken() {
           if (this._currToken != null) {
               this._lastClearedToken = this._currToken;
               this._currToken = null;
                    }
                }

                @Override 
       public JsonToken getLastClearedToken() {
           return this._lastClearedToken;
                }

                @Override 
       public boolean getValueAsBoolean(boolean z) throws IOException {
           JsonToken jsonToken = this._currToken;
           if (jsonToken == null) {
                        return z;
                    }
                    switch (jsonToken.m524id()) {
                        case 6:
                   String strTrim = getText().trim();
                   if ("true".equals(strTrim)) {
                                return true;
                            }
                   if (!"false".equals(strTrim) && !_hasTextualNull(strTrim)) {
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
                   Object embeddedObject = getEmbeddedObject();
                            return embeddedObject instanceof Boolean ? ((Boolean) embeddedObject).booleanValue() : z;
                    }
                }

                @Override 
       public int getValueAsInt(int i) throws IOException {
           JsonToken jsonToken = this._currToken;
           if (jsonToken != null) {
                        switch (jsonToken.m524id()) {
                            case 6:
                       String text = getText();
                       if (_hasTextualNull(text)) {
                                    return 0;
                                }
                       return NumberInput.parseAsInt(text, i);
                            case 7:
                            case 8:
                       return getIntValue();
                            case 9:
                       return 1;
                            case 10:
                                return 0;
                            case 11:
                                return 0;
                            case 12:
                       Object embeddedObject = getEmbeddedObject();
                       if (embeddedObject instanceof Number) {
                           return ((Number) embeddedObject).intValue();
                                }
                       return i;
                            default:
                       return i;
                        }
                    }
           return i;
                }

                @Override 
       public long getValueAsLong(long j) throws IOException {
           JsonToken jsonToken = this._currToken;
           if (jsonToken != null) {
                        switch (jsonToken.m524id()) {
                            case 6:
                       String text = getText();
                       if (_hasTextualNull(text)) {
                                    return 0L;
                                }
                       return NumberInput.parseAsLong(text, j);
                            case 7:
                            case 8:
                       return getLongValue();
                            case 9:
                       return 1L;
                            case 10:
                            case 11:
                                return 0L;
                            case 12:
                       Object embeddedObject = getEmbeddedObject();
                       if (embeddedObject instanceof Number) {
                           return ((Number) embeddedObject).longValue();
                                }
                       return j;
                            default:
                       return j;
                        }
                    }
           return j;
                }

                @Override 
       public double getValueAsDouble(double d) throws IOException {
           JsonToken jsonToken = this._currToken;
           if (jsonToken != null) {
                        switch (jsonToken.m524id()) {
                            case 6:
                       String text = getText();
                       if (_hasTextualNull(text)) {
                                    return 0.0d;
                                }
                       return NumberInput.parseAsDouble(text, d);
                            case 7:
                            case 8:
                       return getDoubleValue();
                            case 9:
                       return 1.0d;
                            case 10:
                            case 11:
                                return 0.0d;
                            case 12:
                       Object embeddedObject = getEmbeddedObject();
                       if (embeddedObject instanceof Number) {
                           return ((Number) embeddedObject).doubleValue();
                                }
                       return d;
                            default:
                       return d;
                        }
                    }
           return d;
                }

                @Override 
       public String getValueAsString(String str) throws IOException {
                    return (this._currToken == JsonToken.VALUE_STRING || !(this._currToken == null || this._currToken == JsonToken.VALUE_NULL || !this._currToken.isScalarValue())) ? getText() : str;
                }

       protected void _decodeBase64(String str, ByteArrayBuilder byteArrayBuilder, Base64Variant base64Variant) throws IOException {
                    try {
               base64Variant.decode(str, byteArrayBuilder);
                    } catch (IllegalArgumentException e) {
               _reportError(e.getMessage());
                    }
                }

                @Deprecated
       protected void _reportInvalidBase64(Base64Variant base64Variant, char c, int i, String str) throws JsonParseException {
                    String str2;
           if (c <= ' ') {
               str2 = "Illegal white space character (code 0x" + Integer.toHexString(c) + ") as character #" + (i + 1) + " of 4-char base64 unit: can only used between units";
                    } else if (base64Variant.usesPaddingChar(c)) {
               str2 = "Unexpected padding character ('" + base64Variant.getPaddingChar() + "') as character #" + (i + 1) + " of 4-char base64 unit: padding only legal as 3rd or 4th character";
                    } else if (!Character.isDefined(c) || Character.isISOControl(c)) {
               str2 = "Illegal character (code 0x" + Integer.toHexString(c) + ") in base64 content";
                    } else {
               str2 = "Illegal character '" + c + "' (code 0x" + Integer.toHexString(c) + ") in base64 content";
                    }
           if (str != null) {
               str2 = str2 + ": " + str;
                    }
           throw _constructError(str2);
                }

                @Deprecated
       protected void _reportBase64EOF() throws JsonParseException {
           throw _constructError("Unexpected end-of-String in base64 content");
                }

       protected boolean _hasTextualNull(String str) {
           return "null".equals(str);
                }

       protected void _reportUnexpectedChar(int i, String str) throws JsonParseException {
           if (i < 0) {
               _reportInvalidEOF();
                    }
           String str2 = "Unexpected character (" + _getCharDesc(i) + ")";
           if (str != null) {
               str2 = str2 + ": " + str;
                    }
           _reportError(str2);
                }

       protected void _reportInvalidEOF() throws JsonParseException {
           _reportInvalidEOF(" in " + this._currToken);
                }

       protected void _reportInvalidEOF(String str) throws JsonParseException {
           _reportError("Unexpected end-of-input" + str);
                }

       protected void _reportInvalidEOFInValue() throws JsonParseException {
           _reportInvalidEOF(" in a value");
                }

       protected void _reportMissingRootWS(int i) throws JsonParseException {
           _reportUnexpectedChar(i, "Expected space separating root-level values");
                }

       protected void _throwInvalidSpace(int i) throws JsonParseException {
           _reportError("Illegal character (" + _getCharDesc((char) i) + "): only regular white space (\\r, \\n, \\t) is allowed between tokens");
                }

       protected void _throwUnquotedSpace(int i, String str) throws JsonParseException {
           if (!isEnabled(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS) || i >= 32) {
               _reportError("Illegal unquoted character (" + _getCharDesc((char) i) + "): has to be escaped using backslash to be included in " + str);
                    }
                }

       protected char _handleUnrecognizedCharacterEscape(char c) throws JsonProcessingException {
           if (!isEnabled(JsonParser.Feature.ALLOW_BACKSLASH_ESCAPING_ANY_CHARACTER) && (c != '\'' || !isEnabled(JsonParser.Feature.ALLOW_SINGLE_QUOTES))) {
               _reportError("Unrecognized character escape " + _getCharDesc(c));
                    }
           return c;
                }

       protected static final String _getCharDesc(int i) {
           char c = (char) i;
           if (Character.isISOControl(c)) {
               return "(CTRL-CHAR, code " + i + ")";
                    }
           if (i > 255) {
               return "'" + c + "' (code " + i + " / 0x" + Integer.toHexString(i) + ")";
                    }
           return "'" + c + "' (code " + i + ")";
                }

       protected final void _reportError(String str) throws JsonParseException {
           throw _constructError(str);
                }

       protected final void _wrapError(String str, Throwable th) throws JsonParseException {
           throw _constructError(str, th);
                }

       protected final void _throwInternal() {
           VersionUtil.throwInternal();
                }

       protected final JsonParseException _constructError(String str, Throwable th) {
           return new JsonParseException(str, getCurrentLocation(), th);
                }
            }
