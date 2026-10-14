            package com.fasterxml.jackson.core;

            import com.fasterxml.jackson.core.type.TypeReference;
            import java.io.Closeable;
            import java.io.IOException;
            import java.io.OutputStream;
            import java.io.Writer;
            import java.math.BigDecimal;
            import java.math.BigInteger;
            import java.util.Iterator;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 201 */   public abstract class JsonParser implements Closeable, Versioned {
                private static final int MAX_BYTE_I = 255;
                private static final int MAX_SHORT_I = 32767;
                private static final int MIN_BYTE_I = -128;
                private static final int MIN_SHORT_I = -32768;
                protected int _features;

                public enum NumberType {
                    INT,
                    LONG,
                    BIG_INTEGER,
                    FLOAT,
                    DOUBLE,
                    BIG_DECIMAL
                }

                public abstract void clearCurrentToken();

                @Override // java.io.Closeable, java.lang.AutoCloseable
                public abstract void close() throws IOException;

                public abstract BigInteger getBigIntegerValue() throws IOException;

                public abstract byte[] getBinaryValue(Base64Variant base64Variant) throws IOException;

                public abstract ObjectCodec getCodec();

                public abstract JsonLocation getCurrentLocation();

                public abstract String getCurrentName() throws IOException;

                public abstract JsonToken getCurrentToken();

                public abstract int getCurrentTokenId();

                public abstract BigDecimal getDecimalValue() throws IOException;

                public abstract double getDoubleValue() throws IOException;

                public abstract Object getEmbeddedObject() throws IOException;

                public abstract float getFloatValue() throws IOException;

                public abstract int getIntValue() throws IOException;

                public abstract JsonToken getLastClearedToken();

                public abstract long getLongValue() throws IOException;

                public abstract NumberType getNumberType() throws IOException;

                public abstract Number getNumberValue() throws IOException;

                public abstract JsonStreamContext getParsingContext();

                public abstract String getText() throws IOException;

                public abstract char[] getTextCharacters() throws IOException;

                public abstract int getTextLength() throws IOException;

                public abstract int getTextOffset() throws IOException;

                public abstract JsonLocation getTokenLocation();

                public abstract String getValueAsString(String str) throws IOException;

                public abstract boolean hasCurrentToken();

                public abstract boolean hasTextCharacters();

                public abstract boolean isClosed();

                public abstract JsonToken nextToken() throws IOException;

                public abstract JsonToken nextValue() throws IOException;

                public abstract void overrideCurrentName(String str);

                public abstract void setCodec(ObjectCodec objectCodec);

                public abstract JsonParser skipChildren() throws IOException;

                @Override // com.fasterxml.jackson.core.Versioned
                public abstract Version version();

/* 202 */       public enum Feature {
                    AUTO_CLOSE_SOURCE(true),
                    ALLOW_COMMENTS(false),
                    ALLOW_YAML_COMMENTS(false),
                    ALLOW_UNQUOTED_FIELD_NAMES(false),
                    ALLOW_SINGLE_QUOTES(false),
                    ALLOW_UNQUOTED_CONTROL_CHARS(false),
                    ALLOW_BACKSLASH_ESCAPING_ANY_CHARACTER(false),
                    ALLOW_NUMERIC_LEADING_ZEROS(false),
                    ALLOW_NON_NUMERIC_NUMBERS(false),
                    STRICT_DUPLICATE_DETECTION(false);

                    private final boolean _defaultState;
                    private final int _mask = 1 << ordinal();

/* 203 */           public static int collectDefaults() {
                        int mask = 0;
                        for (Feature feature : values()) {
/* 205 */                   if (feature.enabledByDefault()) {
/* 206 */                       mask |= feature.getMask();
                            }
                        }
/* 209 */               return mask;
                    }

/* 211 */           Feature(boolean z) {
/* 214 */               this._defaultState = z;
                    }

/* 216 */           public boolean enabledByDefault() {
/* 217 */               return this._defaultState;
                    }

/* 221 */           public boolean enabledIn(int i) {
                        return (this._mask & i) != 0;
                    }

/* 223 */           public int getMask() {
/* 224 */               return 1 << ordinal();
                    }
                }

/* 245 */       protected JsonParser() {
                }

/* 246 */       protected JsonParser(int i) {
/* 248 */           this._features = i;
                }

/* 280 */       public Object getInputSource() {
/* 281 */           return null;
                }

/* 305 */       public void setSchema(FormatSchema formatSchema) {
/* 306 */           throw new UnsupportedOperationException("Parser of type " + getClass().getName() + " does not support schema of type '" + formatSchema.getSchemaType() + "'");
                }

/* 316 */       public FormatSchema getSchema() {
/* 317 */           return null;
                }

/* 328 */       public boolean canUseSchema(FormatSchema formatSchema) {
/* 329 */           return false;
                }

/* 351 */       public boolean requiresCustomCodec() {
/* 352 */           return false;
                }

/* 413 */       public int releaseBuffered(OutputStream outputStream) throws IOException {
/* 414 */           return -1;
                }

/* 433 */       public int releaseBuffered(Writer writer) throws IOException {
/* 434 */           return -1;
                }

/* 448 */       public JsonParser enable(Feature feature) {
/* 449 */           this._features |= feature.getMask();
/* 450 */           return this;
                }

/* 458 */       public JsonParser disable(Feature feature) {
/* 459 */           this._features &= feature.getMask() ^ (-1);
/* 460 */           return this;
                }

/* 468 */       public JsonParser configure(Feature feature, boolean z) {
/* 469 */           if (z) {
/* 470 */               enable(feature);
                    } else {
/* 472 */               disable(feature);
                    }
/* 474 */           return this;
                }

/* 480 */       public boolean isEnabled(Feature feature) {
                    return (this._features & feature.getMask()) != 0;
                }

/* 491 */       public int getFeatureMask() {
/* 492 */           return this._features;
                }

/* 502 */       public JsonParser setFeatureMask(int i) {
/* 503 */           this._features = i;
/* 504 */           return this;
                }

/* 560 */       public boolean nextFieldName(SerializableString serializableString) throws IOException {
                    return nextToken() == JsonToken.FIELD_NAME && serializableString.getValue().equals(getCurrentName());
                }

/* 577 */       public String nextTextValue() throws IOException {
/* 578 */           if (nextToken() == JsonToken.VALUE_STRING) {
                        return getText();
                    }
                    return null;
                }

/* 594 */       public int nextIntValue(int i) throws IOException {
                    return nextToken() == JsonToken.VALUE_NUMBER_INT ? getIntValue() : i;
                }

/* 611 */       public long nextLongValue(long j) throws IOException {
                    return nextToken() == JsonToken.VALUE_NUMBER_INT ? getLongValue() : j;
                }

/* 631 */       public Boolean nextBooleanValue() throws IOException {
/* 632 */           JsonToken jsonTokenNextToken = nextToken();
/* 633 */           if (jsonTokenNextToken == JsonToken.VALUE_TRUE) {
/* 634 */               return Boolean.TRUE;
                    }
/* 636 */           if (jsonTokenNextToken == JsonToken.VALUE_FALSE) {
/* 637 */               return Boolean.FALSE;
                    }
/* 639 */           return null;
                }

/* 768 */       public boolean isExpectedStartArrayToken() {
                    return getCurrentToken() == JsonToken.START_ARRAY;
                }

/* 939 */       public byte getByteValue() throws IOException {
/* 940 */           int intValue = getIntValue();
/* 944 */           if (intValue < MIN_BYTE_I || intValue > 255) {
/* 945 */               throw _constructError("Numeric value (" + getText() + ") out of range of Java byte");
                    }
/* 947 */           return (byte) intValue;
                }

/* 965 */       public short getShortValue() throws IOException {
/* 966 */           int intValue = getIntValue();
/* 967 */           if (intValue < MIN_SHORT_I || intValue > 32767) {
/* 968 */               throw _constructError("Numeric value (" + getText() + ") out of range of Java short");
                    }
/* 970 */           return (short) intValue;
                }

/* 1076 */      public boolean getBooleanValue() throws IOException {
/* 1077 */          JsonToken currentToken = getCurrentToken();
/* 1078 */          if (currentToken == JsonToken.VALUE_TRUE) {
                        return true;
                    }
                    if (currentToken == JsonToken.VALUE_FALSE) {
                        return false;
                    }
/* 1080 */          throw new JsonParseException("Current token (" + currentToken + ") not of boolean type", getCurrentLocation());
                }

/* 1130 */      public byte[] getBinaryValue() throws IOException {
/* 1131 */          return getBinaryValue(Base64Variants.getDefaultVariant());
                }

/* 1148 */      public int readBinaryValue(OutputStream outputStream) throws IOException {
/* 1149 */          return readBinaryValue(Base64Variants.getDefaultVariant(), outputStream);
                }

/* 1165 */      public int readBinaryValue(Base64Variant base64Variant, OutputStream outputStream) throws IOException {
/* 1166 */          _reportUnsupportedOperation();
/* 1167 */          return 0;
                }

/* 1187 */      public int getValueAsInt() throws IOException {
/* 1188 */          return getValueAsInt(0);
                }

/* 1202 */      public int getValueAsInt(int i) throws IOException {
/* 1203 */          return i;
                }

/* 1217 */      public long getValueAsLong() throws IOException {
/* 1218 */          return getValueAsLong(0L);
                }

/* 1232 */      public long getValueAsLong(long j) throws IOException {
/* 1233 */          return j;
                }

/* 1247 */      public double getValueAsDouble() throws IOException {
/* 1248 */          return getValueAsDouble(0.0d);
                }

/* 1262 */      public double getValueAsDouble(double d) throws IOException {
/* 1263 */          return d;
                }

/* 1277 */      public boolean getValueAsBoolean() throws IOException {
/* 1278 */          return getValueAsBoolean(false);
                }

/* 1292 */      public boolean getValueAsBoolean(boolean z) throws IOException {
/* 1293 */          return z;
                }

/* 1307 */      public String getValueAsString() throws IOException {
/* 1308 */          return getValueAsString(null);
                }

/* 1343 */      public boolean canReadObjectId() {
/* 1344 */          return false;
                }

/* 1359 */      public boolean canReadTypeId() {
/* 1360 */          return false;
                }

/* 1376 */      public Object getObjectId() throws IOException {
/* 1377 */          return null;
                }

/* 1393 */      public Object getTypeId() throws IOException {
/* 1394 */          return null;
                }

/* 1426 */      public <T> T readValueAs(Class<T> cls) throws IOException {
/* 1427 */          ObjectCodec codec = getCodec();
/* 1428 */          if (codec == null) {
/* 1429 */              throw new IllegalStateException("No ObjectCodec defined for the parser, can not deserialize JSON into Java objects");
                    }
/* 1431 */          return (T) codec.readValue(this, cls);
                }

/* 1455 */      public <T> T readValueAs(TypeReference<?> typeReference) throws IOException {
/* 1456 */          ObjectCodec codec = getCodec();
/* 1457 */          if (codec == null) {
/* 1458 */              throw new IllegalStateException("No ObjectCodec defined for the parser, can not deserialize JSON into Java objects");
                    }
/* 1463 */          return (T) codec.readValue(this, typeReference);
                }

/* 1472 */      public <T> Iterator<T> readValuesAs(Class<T> cls) throws IOException {
/* 1473 */          ObjectCodec codec = getCodec();
/* 1474 */          if (codec == null) {
/* 1475 */              throw new IllegalStateException("No ObjectCodec defined for the parser, can not deserialize JSON into Java objects");
                    }
/* 1477 */          return codec.readValues(this, cls);
                }

/* 1486 */      public <T> Iterator<T> readValuesAs(TypeReference<?> typeReference) throws IOException {
/* 1487 */          ObjectCodec codec = getCodec();
/* 1488 */          if (codec == null) {
/* 1489 */              throw new IllegalStateException("No ObjectCodec defined for the parser, can not deserialize JSON into Java objects");
                    }
/* 1491 */          return codec.readValues(this, typeReference);
                }

/* 1504 */      public <T extends TreeNode> T readValueAsTree() throws IOException {
/* 1505 */          ObjectCodec codec = getCodec();
/* 1506 */          if (codec == null) {
/* 1507 */              throw new IllegalStateException("No ObjectCodec defined for the parser, can not deserialize JSON into JsonNode tree");
                    }
/* 1509 */          return (T) codec.readTree(this);
                }

/* 1523 */      protected JsonParseException _constructError(String str) {
/* 1524 */          return new JsonParseException(str, getCurrentLocation());
                }

/* 1533 */      protected void _reportUnsupportedOperation() {
/* 1534 */          throw new UnsupportedOperationException("Operation not supported by parser of type " + getClass().getName());
                }
            }
