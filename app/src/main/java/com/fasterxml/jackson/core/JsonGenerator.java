            package com.fasterxml.jackson.core;

            import com.fasterxml.jackson.core.p003io.CharacterEscapes;
            import com.fasterxml.jackson.core.util.VersionUtil;
            import java.io.Closeable;
            import java.io.Flushable;
            import java.io.IOException;
            import java.io.InputStream;
            import java.math.BigDecimal;
            import java.math.BigInteger;
            import java.util.concurrent.atomic.AtomicBoolean;
            import java.util.concurrent.atomic.AtomicInteger;
            import java.util.concurrent.atomic.AtomicLong;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 176 */   public abstract class JsonGenerator implements Closeable, Flushable, Versioned {
                protected PrettyPrinter _cfgPrettyPrinter;

                @Override // java.io.Closeable, java.lang.AutoCloseable
                public abstract void close() throws IOException;

                public abstract JsonGenerator disable(Feature feature);

                public abstract JsonGenerator enable(Feature feature);

                @Override // java.io.Flushable
                public abstract void flush() throws IOException;

                public abstract ObjectCodec getCodec();

                public abstract int getFeatureMask();

                public abstract JsonStreamContext getOutputContext();

                public abstract boolean isClosed();

                public abstract boolean isEnabled(Feature feature);

                public abstract JsonGenerator setCodec(ObjectCodec objectCodec);

                public abstract JsonGenerator setFeatureMask(int i);

                public abstract JsonGenerator useDefaultPrettyPrinter();

                @Override // com.fasterxml.jackson.core.Versioned
                public abstract Version version();

                public abstract int writeBinary(Base64Variant base64Variant, InputStream inputStream, int i) throws IOException;

                public abstract void writeBinary(Base64Variant base64Variant, byte[] bArr, int i, int i2) throws IOException;

                public abstract void writeBoolean(boolean z) throws IOException;

                public abstract void writeEndArray() throws IOException;

                public abstract void writeEndObject() throws IOException;

                public abstract void writeFieldName(SerializableString serializableString) throws IOException;

                public abstract void writeFieldName(String str) throws IOException;

                public abstract void writeNull() throws IOException;

                public abstract void writeNumber(double d) throws IOException;

                public abstract void writeNumber(float f) throws IOException;

                public abstract void writeNumber(int i) throws IOException;

                public abstract void writeNumber(long j) throws IOException;

                public abstract void writeNumber(String str) throws UnsupportedOperationException, IOException;

                public abstract void writeNumber(BigDecimal bigDecimal) throws IOException;

                public abstract void writeNumber(BigInteger bigInteger) throws IOException;

                public abstract void writeObject(Object obj) throws IOException;

                public abstract void writeRaw(char c) throws IOException;

                public abstract void writeRaw(String str) throws IOException;

                public abstract void writeRaw(String str, int i, int i2) throws IOException;

                public abstract void writeRaw(char[] cArr, int i, int i2) throws IOException;

                public abstract void writeRawUTF8String(byte[] bArr, int i, int i2) throws IOException;

                public abstract void writeRawValue(String str) throws IOException;

                public abstract void writeRawValue(String str, int i, int i2) throws IOException;

                public abstract void writeRawValue(char[] cArr, int i, int i2) throws IOException;

                public abstract void writeStartArray() throws IOException;

                public abstract void writeStartObject() throws IOException;

                public abstract void writeString(SerializableString serializableString) throws IOException;

                public abstract void writeString(String str) throws IOException;

                public abstract void writeString(char[] cArr, int i, int i2) throws IOException;

                public abstract void writeTree(TreeNode treeNode) throws IOException;

                public abstract void writeUTF8String(byte[] bArr, int i, int i2) throws IOException;

/* 177 */       public enum Feature {
                    AUTO_CLOSE_TARGET(true),
                    AUTO_CLOSE_JSON_CONTENT(true),
                    QUOTE_FIELD_NAMES(true),
                    QUOTE_NON_NUMERIC_NUMBERS(true),
                    WRITE_NUMBERS_AS_STRINGS(false),
                    WRITE_BIGDECIMAL_AS_PLAIN(false),
                    FLUSH_PASSED_TO_STREAM(true),
                    ESCAPE_NON_ASCII(false),
                    STRICT_DUPLICATE_DETECTION(false);

                    private final boolean _defaultState;
                    private final int _mask = 1 << ordinal();

/* 178 */           public static int collectDefaults() {
                        int mask = 0;
                        for (Feature feature : values()) {
/* 180 */                   if (feature.enabledByDefault()) {
/* 181 */                       mask |= feature.getMask();
                            }
                        }
/* 184 */               return mask;
                    }

/* 186 */           Feature(boolean z) {
/* 189 */               this._defaultState = z;
                    }

/* 191 */           public boolean enabledByDefault() {
/* 192 */               return this._defaultState;
                    }

/* 196 */           public boolean enabledIn(int i) {
                        return (this._mask & i) != 0;
                    }

/* 198 */           public int getMask() {
/* 199 */               return this._mask;
                    }
                }

/* 220 */       protected JsonGenerator() {
                }

/* 260 */       public Object getOutputTarget() {
/* 261 */           return null;
                }

/* 293 */       public final JsonGenerator configure(Feature feature, boolean z) {
/* 294 */           if (z) {
/* 295 */               enable(feature);
                    } else {
/* 297 */               disable(feature);
                    }
/* 299 */           return this;
                }

/* 349 */       public void setSchema(FormatSchema formatSchema) {
/* 350 */           throw new UnsupportedOperationException("Generator of type " + getClass().getName() + " does not support schema of type '" + formatSchema.getSchemaType() + "'");
                }

/* 360 */       public FormatSchema getSchema() {
/* 361 */           return null;
                }

/* 381 */       public JsonGenerator setPrettyPrinter(PrettyPrinter prettyPrinter) {
/* 382 */           this._cfgPrettyPrinter = prettyPrinter;
/* 383 */           return this;
                }

/* 392 */       public PrettyPrinter getPrettyPrinter() {
/* 393 */           return this._cfgPrettyPrinter;
                }

/* 426 */       public JsonGenerator setHighestNonEscapedChar(int i) {
/* 427 */           return this;
                }

/* 442 */       public int getHighestEscapedChar() {
/* 443 */           return 0;
                }

/* 450 */       public CharacterEscapes getCharacterEscapes() {
/* 451 */           return null;
                }

/* 458 */       public JsonGenerator setCharacterEscapes(CharacterEscapes characterEscapes) {
/* 459 */           return this;
                }

/* 471 */       public JsonGenerator setRootValueSeparator(SerializableString serializableString) {
/* 472 */           throw new UnsupportedOperationException();
                }

/* 489 */       public boolean canUseSchema(FormatSchema formatSchema) {
/* 490 */           return false;
                }

/* 507 */       public boolean canWriteObjectId() {
/* 508 */           return false;
                }

/* 525 */       public boolean canWriteTypeId() {
/* 526 */           return false;
                }

/* 539 */       public boolean canWriteBinaryNatively() {
/* 540 */           return false;
                }

/* 551 */       public boolean canOmitFields() {
/* 552 */           return true;
                }

/* 798 */       public void writeRaw(SerializableString serializableString) throws IOException {
/* 799 */           writeRaw(serializableString.getValue());
                }

/* 851 */       public void writeBinary(byte[] bArr, int i, int i2) throws IOException {
/* 852 */           writeBinary(Base64Variants.getDefaultVariant(), bArr, i, i2);
                }

/* 863 */       public void writeBinary(byte[] bArr) throws IOException {
/* 864 */           writeBinary(Base64Variants.getDefaultVariant(), bArr, 0, bArr.length);
                }

/* 881 */       public int writeBinary(InputStream inputStream, int i) throws IOException {
/* 882 */           return writeBinary(Base64Variants.getDefaultVariant(), inputStream, i);
                }

/* 925 */       public void writeNumber(short s) throws IOException {
/* 926 */           writeNumber((int) s);
                }

/* 1049 */      public void writeObjectId(Object obj) throws IOException {
/* 1050 */          throw new JsonGenerationException("No native support for writing Object Ids");
                }

/* 1063 */      public void writeObjectRef(Object obj) throws IOException {
/* 1064 */          throw new JsonGenerationException("No native support for writing Object Ids");
                }

/* 1079 */      public void writeTypeId(Object obj) throws IOException {
/* 1080 */          throw new JsonGenerationException("No native support for writing Type Ids");
                }

/* 1132 */      public void writeStringField(String str, String str2) throws IOException {
/* 1133 */          writeFieldName(str);
/* 1134 */          writeString(str2);
                }

/* 1147 */      public final void writeBooleanField(String str, boolean z) throws IOException {
/* 1148 */          writeFieldName(str);
/* 1149 */          writeBoolean(z);
                }

/* 1162 */      public final void writeNullField(String str) throws IOException {
/* 1163 */          writeFieldName(str);
/* 1164 */          writeNull();
                }

/* 1177 */      public final void writeNumberField(String str, int i) throws IOException {
/* 1178 */          writeFieldName(str);
/* 1179 */          writeNumber(i);
                }

/* 1192 */      public final void writeNumberField(String str, long j) throws IOException {
/* 1193 */          writeFieldName(str);
/* 1194 */          writeNumber(j);
                }

/* 1207 */      public final void writeNumberField(String str, double d) throws IOException {
/* 1208 */          writeFieldName(str);
/* 1209 */          writeNumber(d);
                }

/* 1222 */      public final void writeNumberField(String str, float f) throws IOException {
/* 1223 */          writeFieldName(str);
/* 1224 */          writeNumber(f);
                }

/* 1238 */      public final void writeNumberField(String str, BigDecimal bigDecimal) throws IOException {
/* 1239 */          writeFieldName(str);
/* 1240 */          writeNumber(bigDecimal);
                }

/* 1254 */      public final void writeBinaryField(String str, byte[] bArr) throws IOException {
/* 1255 */          writeFieldName(str);
/* 1256 */          writeBinary(bArr);
                }

/* 1274 */      public final void writeArrayFieldStart(String str) throws IOException {
/* 1275 */          writeFieldName(str);
/* 1276 */          writeStartArray();
                }

/* 1294 */      public final void writeObjectFieldStart(String str) throws IOException {
/* 1295 */          writeFieldName(str);
/* 1296 */          writeStartObject();
                }

/* 1310 */      public final void writeObjectField(String str, Object obj) throws IOException {
/* 1311 */          writeFieldName(str);
/* 1312 */          writeObject(obj);
                }

/* 1327 */      public void writeOmittedField(String str) throws IOException {
                }

/* 1348 */      public void copyCurrentEvent(JsonParser jsonParser) throws IOException {
/* 1349 */          JsonToken currentToken = jsonParser.getCurrentToken();
/* 1351 */          if (currentToken == null) {
/* 1352 */              _reportError("No current event to copy");
                    }
                    switch (currentToken.m524id()) {
                        case -1:
/* 1356 */                  _reportError("No current event to copy");
                            break;
                        case 0:
                        default:
/* 1416 */                  _throwInternal();
/* 1418 */                  return;
                        case 1:
                            break;
                        case 2:
/* 1361 */                  writeEndObject();
/* 1418 */                  return;
                        case 3:
/* 1364 */                  writeStartArray();
/* 1418 */                  return;
                        case 4:
/* 1367 */                  writeEndArray();
/* 1418 */                  return;
                        case 5:
/* 1370 */                  writeFieldName(jsonParser.getCurrentName());
/* 1418 */                  return;
                        case 6:
/* 1373 */                  if (jsonParser.hasTextCharacters()) {
/* 1374 */                      writeString(jsonParser.getTextCharacters(), jsonParser.getTextOffset(), jsonParser.getTextLength());
/* 1418 */                      return;
                            } else {
/* 1376 */                      writeString(jsonParser.getText());
/* 1418 */                      return;
                            }
                        case 7:
/* 1381 */                  JsonParser.NumberType numberType = jsonParser.getNumberType();
/* 1382 */                  if (numberType == JsonParser.NumberType.INT) {
/* 1383 */                      writeNumber(jsonParser.getIntValue());
/* 1418 */                      return;
                            } else if (numberType == JsonParser.NumberType.BIG_INTEGER) {
/* 1385 */                      writeNumber(jsonParser.getBigIntegerValue());
/* 1418 */                      return;
                            } else {
/* 1387 */                      writeNumber(jsonParser.getLongValue());
/* 1418 */                      return;
                            }
                        case 8:
/* 1393 */                  JsonParser.NumberType numberType2 = jsonParser.getNumberType();
/* 1394 */                  if (numberType2 == JsonParser.NumberType.BIG_DECIMAL) {
/* 1395 */                      writeNumber(jsonParser.getDecimalValue());
/* 1418 */                      return;
                            } else if (numberType2 == JsonParser.NumberType.FLOAT) {
/* 1397 */                      writeNumber(jsonParser.getFloatValue());
/* 1418 */                      return;
                            } else {
/* 1399 */                      writeNumber(jsonParser.getDoubleValue());
/* 1418 */                      return;
                            }
                        case 9:
/* 1404 */                  writeBoolean(true);
/* 1418 */                  return;
                        case 10:
/* 1407 */                  writeBoolean(false);
/* 1418 */                  return;
                        case 11:
/* 1410 */                  writeNull();
/* 1418 */                  return;
                        case 12:
/* 1413 */                  writeObject(jsonParser.getEmbeddedObject());
/* 1418 */                  return;
                    }
/* 1358 */          writeStartObject();
                }

/* 1452 */      public void copyCurrentStructure(JsonParser jsonParser) throws IOException {
/* 1453 */          JsonToken currentToken = jsonParser.getCurrentToken();
/* 1454 */          if (currentToken == null) {
/* 1455 */              _reportError("No current event to copy");
                    }
/* 1458 */          int iM524id = currentToken.m524id();
/* 1459 */          if (iM524id == 5) {
/* 1460 */              writeFieldName(jsonParser.getCurrentName());
/* 1462 */              iM524id = jsonParser.nextToken().m524id();
                    }
                    switch (iM524id) {
                        case 1:
/* 1467 */                  writeStartObject();
/* 1468 */                  while (jsonParser.nextToken() != JsonToken.END_OBJECT) {
/* 1469 */                      copyCurrentStructure(jsonParser);
                            }
/* 1471 */                  writeEndObject();
                            break;
                        case 2:
                        default:
/* 1481 */                  copyCurrentEvent(jsonParser);
                            break;
                        case 3:
/* 1474 */                  writeStartArray();
/* 1475 */                  while (jsonParser.nextToken() != JsonToken.END_ARRAY) {
/* 1476 */                      copyCurrentStructure(jsonParser);
                            }
/* 1478 */                  writeEndArray();
                            break;
                    }
                }

/* 1553 */      protected void _reportError(String str) throws JsonGenerationException {
/* 1554 */          throw new JsonGenerationException(str);
                }

/* 1557 */      protected final void _throwInternal() {
/* 1558 */          VersionUtil.throwInternal();
                }

/* 1561 */      protected void _reportUnsupportedOperation() {
/* 1562 */          throw new UnsupportedOperationException("Operation not supported by generator of type " + getClass().getName());
                }

/* 1578 */      protected void _writeSimpleObject(Object obj) throws IOException {
/* 1579 */          if (obj == null) {
/* 1580 */              writeNull();
/* 1631 */              return;
                    }
/* 1583 */          if (obj instanceof String) {
/* 1584 */              writeString((String) obj);
/* 1631 */              return;
                    }
/* 1587 */          if (obj instanceof Number) {
/* 1588 */              Number number = (Number) obj;
/* 1589 */              if (number instanceof Integer) {
/* 1590 */                  writeNumber(number.intValue());
/* 1631 */                  return;
                        }
/* 1592 */              if (number instanceof Long) {
/* 1593 */                  writeNumber(number.longValue());
/* 1631 */                  return;
                        }
/* 1595 */              if (number instanceof Double) {
/* 1596 */                  writeNumber(number.doubleValue());
/* 1631 */                  return;
                        }
/* 1598 */              if (number instanceof Float) {
/* 1599 */                  writeNumber(number.floatValue());
/* 1631 */                  return;
                        }
/* 1601 */              if (number instanceof Short) {
/* 1602 */                  writeNumber(number.shortValue());
/* 1631 */                  return;
                        }
/* 1604 */              if (number instanceof Byte) {
/* 1605 */                  writeNumber(number.byteValue());
/* 1631 */                  return;
                        }
/* 1607 */              if (number instanceof BigInteger) {
/* 1608 */                  writeNumber((BigInteger) number);
/* 1631 */                  return;
                        }
/* 1610 */              if (number instanceof BigDecimal) {
/* 1611 */                  writeNumber((BigDecimal) number);
/* 1631 */                  return;
                        } else if (number instanceof AtomicInteger) {
/* 1617 */                  writeNumber(((AtomicInteger) number).get());
/* 1631 */                  return;
                        } else if (number instanceof AtomicLong) {
/* 1620 */                  writeNumber(((AtomicLong) number).get());
/* 1631 */                  return;
                        }
                    } else if (obj instanceof byte[]) {
/* 1624 */              writeBinary((byte[]) obj);
/* 1631 */              return;
                    } else if (obj instanceof Boolean) {
/* 1627 */              writeBoolean(((Boolean) obj).booleanValue());
/* 1631 */              return;
                    } else if (obj instanceof AtomicBoolean) {
/* 1630 */              writeBoolean(((AtomicBoolean) obj).get());
/* 1631 */              return;
                    }
/* 1633 */          throw new IllegalStateException("No ObjectCodec defined for the generator, can only serialize simple wrapper types (type passed " + obj.getClass().getName() + ")");
                }
            }
