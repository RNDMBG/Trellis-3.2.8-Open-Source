            package com.fasterxml.jackson.core.util;

            import com.fasterxml.jackson.core.Base64Variant;
            import com.fasterxml.jackson.core.FormatSchema;
            import com.fasterxml.jackson.core.JsonLocation;
            import com.fasterxml.jackson.core.JsonParser;
            import com.fasterxml.jackson.core.JsonStreamContext;
            import com.fasterxml.jackson.core.JsonToken;
            import com.fasterxml.jackson.core.ObjectCodec;
            import com.fasterxml.jackson.core.SerializableString;
            import com.fasterxml.jackson.core.TreeNode;
            import com.fasterxml.jackson.core.Version;
            import com.fasterxml.jackson.core.type.TypeReference;
            import java.io.IOException;
            import java.io.OutputStream;
            import java.io.Writer;
            import java.math.BigDecimal;
            import java.math.BigInteger;
            import java.util.Iterator;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 25 */    public class JsonParserDelegate extends JsonParser {
                protected JsonParser delegate;

/* 26 */        public JsonParserDelegate(JsonParser jsonParser) {
/* 28 */            this.delegate = jsonParser;
                }

                @Override // com.fasterxml.jackson.core.JsonParser
/* 38 */        public void setCodec(ObjectCodec objectCodec) {
/* 39 */            this.delegate.setCodec(objectCodec);
                }

                @Override // com.fasterxml.jackson.core.JsonParser
/* 43 */        public ObjectCodec getCodec() {
/* 44 */            return this.delegate.getCodec();
                }

                @Override // com.fasterxml.jackson.core.JsonParser
/* 48 */        public JsonParser enable(JsonParser.Feature feature) {
/* 49 */            this.delegate.enable(feature);
/* 50 */            return this;
                }

                @Override // com.fasterxml.jackson.core.JsonParser
/* 54 */        public JsonParser disable(JsonParser.Feature feature) {
/* 55 */            this.delegate.disable(feature);
/* 56 */            return this;
                }

                @Override // com.fasterxml.jackson.core.JsonParser
/* 60 */        public boolean isEnabled(JsonParser.Feature feature) {
/* 61 */            return this.delegate.isEnabled(feature);
                }

                @Override // com.fasterxml.jackson.core.JsonParser
/* 64 */        public JsonParser configure(JsonParser.Feature feature, boolean z) {
/* 65 */            return this.delegate.configure(feature, z);
                }

                @Override // com.fasterxml.jackson.core.JsonParser
/* 69 */        public int getFeatureMask() {
/* 70 */            return this.delegate.getFeatureMask();
                }

                @Override // com.fasterxml.jackson.core.JsonParser
/* 74 */        public JsonParser setFeatureMask(int i) {
/* 75 */            this.delegate.setFeatureMask(i);
/* 76 */            return this;
                }

                @Override // com.fasterxml.jackson.core.JsonParser
/* 80 */        public FormatSchema getSchema() {
/* 81 */            return this.delegate.getSchema();
                }

                @Override // com.fasterxml.jackson.core.JsonParser
/* 85 */        public void setSchema(FormatSchema formatSchema) {
/* 86 */            this.delegate.setSchema(formatSchema);
                }

                @Override // com.fasterxml.jackson.core.JsonParser
/* 90 */        public boolean canUseSchema(FormatSchema formatSchema) {
/* 91 */            return this.delegate.canUseSchema(formatSchema);
                }

                @Override // com.fasterxml.jackson.core.JsonParser, com.fasterxml.jackson.core.Versioned
/* 95 */        public Version version() {
/* 96 */            return this.delegate.version();
                }

                @Override // com.fasterxml.jackson.core.JsonParser
/* 100 */       public Object getInputSource() {
/* 101 */           return this.delegate.getInputSource();
                }

                @Override // com.fasterxml.jackson.core.JsonParser
/* 111 */       public boolean requiresCustomCodec() {
/* 112 */           return this.delegate.requiresCustomCodec();
                }

                @Override // com.fasterxml.jackson.core.JsonParser, java.io.Closeable, java.lang.AutoCloseable
/* 122 */       public void close() throws IOException {
/* 123 */           this.delegate.close();
                }

                @Override // com.fasterxml.jackson.core.JsonParser
/* 127 */       public boolean isClosed() {
/* 128 */           return this.delegate.isClosed();
                }

                @Override // com.fasterxml.jackson.core.JsonParser
/* 138 */       public JsonToken getCurrentToken() {
/* 139 */           return this.delegate.getCurrentToken();
                }

                @Override // com.fasterxml.jackson.core.JsonParser
/* 143 */       public int getCurrentTokenId() {
/* 144 */           return this.delegate.getCurrentTokenId();
                }

                @Override // com.fasterxml.jackson.core.JsonParser
/* 148 */       public boolean hasCurrentToken() {
/* 149 */           return this.delegate.hasCurrentToken();
                }

                @Override // com.fasterxml.jackson.core.JsonParser
/* 153 */       public String getCurrentName() throws IOException {
/* 154 */           return this.delegate.getCurrentName();
                }

                @Override // com.fasterxml.jackson.core.JsonParser
/* 158 */       public JsonLocation getCurrentLocation() {
/* 159 */           return this.delegate.getCurrentLocation();
                }

                @Override // com.fasterxml.jackson.core.JsonParser
/* 163 */       public JsonStreamContext getParsingContext() {
/* 164 */           return this.delegate.getParsingContext();
                }

                @Override // com.fasterxml.jackson.core.JsonParser
/* 166 */       public boolean isExpectedStartArrayToken() {
/* 167 */           return this.delegate.isExpectedStartArrayToken();
                }

                @Override // com.fasterxml.jackson.core.JsonParser
/* 176 */       public void clearCurrentToken() {
/* 177 */           this.delegate.clearCurrentToken();
                }

                @Override // com.fasterxml.jackson.core.JsonParser
/* 181 */       public JsonToken getLastClearedToken() {
/* 182 */           return this.delegate.getLastClearedToken();
                }

                @Override // com.fasterxml.jackson.core.JsonParser
/* 186 */       public void overrideCurrentName(String str) {
/* 187 */           this.delegate.overrideCurrentName(str);
                }

                @Override // com.fasterxml.jackson.core.JsonParser
/* 197 */       public String getText() throws IOException {
/* 198 */           return this.delegate.getText();
                }

                @Override // com.fasterxml.jackson.core.JsonParser
/* 202 */       public boolean hasTextCharacters() {
/* 203 */           return this.delegate.hasTextCharacters();
                }

                @Override // com.fasterxml.jackson.core.JsonParser
/* 207 */       public char[] getTextCharacters() throws IOException {
/* 208 */           return this.delegate.getTextCharacters();
                }

                @Override // com.fasterxml.jackson.core.JsonParser
/* 212 */       public int getTextLength() throws IOException {
/* 213 */           return this.delegate.getTextLength();
                }

                @Override // com.fasterxml.jackson.core.JsonParser
/* 217 */       public int getTextOffset() throws IOException {
/* 218 */           return this.delegate.getTextOffset();
                }

                @Override // com.fasterxml.jackson.core.JsonParser
/* 221 */       public String nextTextValue() throws IOException {
/* 222 */           return this.delegate.nextTextValue();
                }

                @Override // com.fasterxml.jackson.core.JsonParser
/* 232 */       public BigInteger getBigIntegerValue() throws IOException {
/* 233 */           return this.delegate.getBigIntegerValue();
                }

                @Override // com.fasterxml.jackson.core.JsonParser
/* 237 */       public boolean getBooleanValue() throws IOException {
/* 238 */           return this.delegate.getBooleanValue();
                }

                @Override // com.fasterxml.jackson.core.JsonParser
/* 242 */       public byte getByteValue() throws IOException {
/* 243 */           return this.delegate.getByteValue();
                }

                @Override // com.fasterxml.jackson.core.JsonParser
/* 247 */       public short getShortValue() throws IOException {
/* 248 */           return this.delegate.getShortValue();
                }

                @Override // com.fasterxml.jackson.core.JsonParser
/* 252 */       public BigDecimal getDecimalValue() throws IOException {
/* 253 */           return this.delegate.getDecimalValue();
                }

                @Override // com.fasterxml.jackson.core.JsonParser
/* 257 */       public double getDoubleValue() throws IOException {
/* 258 */           return this.delegate.getDoubleValue();
                }

                @Override // com.fasterxml.jackson.core.JsonParser
/* 262 */       public float getFloatValue() throws IOException {
/* 263 */           return this.delegate.getFloatValue();
                }

                @Override // com.fasterxml.jackson.core.JsonParser
/* 267 */       public int getIntValue() throws IOException {
/* 268 */           return this.delegate.getIntValue();
                }

                @Override // com.fasterxml.jackson.core.JsonParser
/* 272 */       public long getLongValue() throws IOException {
/* 273 */           return this.delegate.getLongValue();
                }

                @Override // com.fasterxml.jackson.core.JsonParser
/* 277 */       public JsonParser.NumberType getNumberType() throws IOException {
/* 278 */           return this.delegate.getNumberType();
                }

                @Override // com.fasterxml.jackson.core.JsonParser
/* 282 */       public Number getNumberValue() throws IOException {
/* 283 */           return this.delegate.getNumberValue();
                }

                @Override // com.fasterxml.jackson.core.JsonParser
/* 286 */       public Boolean nextBooleanValue() throws IOException {
/* 287 */           return this.delegate.nextBooleanValue();
                }

                @Override // com.fasterxml.jackson.core.JsonParser
/* 290 */       public int nextIntValue(int i) throws IOException {
/* 291 */           return this.delegate.nextIntValue(i);
                }

                @Override // com.fasterxml.jackson.core.JsonParser
/* 294 */       public long nextLongValue(long j) throws IOException {
/* 295 */           return this.delegate.nextLongValue(j);
                }

                @Override // com.fasterxml.jackson.core.JsonParser
/* 305 */       public int getValueAsInt() throws IOException {
/* 306 */           return this.delegate.getValueAsInt();
                }

                @Override // com.fasterxml.jackson.core.JsonParser
/* 310 */       public int getValueAsInt(int i) throws IOException {
/* 311 */           return this.delegate.getValueAsInt(i);
                }

                @Override // com.fasterxml.jackson.core.JsonParser
/* 315 */       public long getValueAsLong() throws IOException {
/* 316 */           return this.delegate.getValueAsLong();
                }

                @Override // com.fasterxml.jackson.core.JsonParser
/* 320 */       public long getValueAsLong(long j) throws IOException {
/* 321 */           return this.delegate.getValueAsLong(j);
                }

                @Override // com.fasterxml.jackson.core.JsonParser
/* 325 */       public double getValueAsDouble() throws IOException {
/* 326 */           return this.delegate.getValueAsDouble();
                }

                @Override // com.fasterxml.jackson.core.JsonParser
/* 330 */       public double getValueAsDouble(double d) throws IOException {
/* 331 */           return this.delegate.getValueAsDouble(d);
                }

                @Override // com.fasterxml.jackson.core.JsonParser
/* 335 */       public boolean getValueAsBoolean() throws IOException {
/* 336 */           return this.delegate.getValueAsBoolean();
                }

                @Override // com.fasterxml.jackson.core.JsonParser
/* 340 */       public boolean getValueAsBoolean(boolean z) throws IOException {
/* 341 */           return this.delegate.getValueAsBoolean(z);
                }

                @Override // com.fasterxml.jackson.core.JsonParser
/* 345 */       public String getValueAsString() throws IOException {
/* 346 */           return this.delegate.getValueAsString();
                }

                @Override // com.fasterxml.jackson.core.JsonParser
/* 350 */       public String getValueAsString(String str) throws IOException {
/* 351 */           return this.delegate.getValueAsString(str);
                }

                @Override // com.fasterxml.jackson.core.JsonParser
/* 354 */       public <T> T readValueAs(Class<T> cls) throws IOException {
/* 355 */           return (T) this.delegate.readValueAs(cls);
                }

                @Override // com.fasterxml.jackson.core.JsonParser
/* 358 */       public <T> T readValueAs(TypeReference<?> typeReference) throws IOException {
/* 359 */           return (T) this.delegate.readValueAs(typeReference);
                }

                @Override // com.fasterxml.jackson.core.JsonParser
/* 362 */       public <T> Iterator<T> readValuesAs(Class<T> cls) throws IOException {
/* 363 */           return this.delegate.readValuesAs(cls);
                }

                @Override // com.fasterxml.jackson.core.JsonParser
/* 366 */       public <T> Iterator<T> readValuesAs(TypeReference<?> typeReference) throws IOException {
/* 367 */           return this.delegate.readValuesAs(typeReference);
                }

                @Override // com.fasterxml.jackson.core.JsonParser
/* 370 */       public <T extends TreeNode> T readValueAsTree() throws IOException {
/* 371 */           return (T) this.delegate.readValueAsTree();
                }

                @Override // com.fasterxml.jackson.core.JsonParser
/* 381 */       public Object getEmbeddedObject() throws IOException {
/* 382 */           return this.delegate.getEmbeddedObject();
                }

                @Override // com.fasterxml.jackson.core.JsonParser
/* 386 */       public byte[] getBinaryValue(Base64Variant base64Variant) throws IOException {
/* 387 */           return this.delegate.getBinaryValue(base64Variant);
                }

                @Override // com.fasterxml.jackson.core.JsonParser
/* 390 */       public byte[] getBinaryValue() throws IOException {
/* 391 */           return this.delegate.getBinaryValue();
                }

                @Override // com.fasterxml.jackson.core.JsonParser
/* 395 */       public int readBinaryValue(Base64Variant base64Variant, OutputStream outputStream) throws IOException {
/* 396 */           return this.delegate.readBinaryValue(base64Variant, outputStream);
                }

                @Override // com.fasterxml.jackson.core.JsonParser
/* 399 */       public int readBinaryValue(OutputStream outputStream) throws IOException {
/* 400 */           return this.delegate.readBinaryValue(outputStream);
                }

                @Override // com.fasterxml.jackson.core.JsonParser
/* 404 */       public JsonLocation getTokenLocation() {
/* 405 */           return this.delegate.getTokenLocation();
                }

                @Override // com.fasterxml.jackson.core.JsonParser
/* 409 */       public JsonToken nextToken() throws IOException {
/* 410 */           return this.delegate.nextToken();
                }

                @Override // com.fasterxml.jackson.core.JsonParser
/* 414 */       public JsonToken nextValue() throws IOException {
/* 415 */           return this.delegate.nextValue();
                }

                @Override // com.fasterxml.jackson.core.JsonParser
/* 418 */       public boolean nextFieldName(SerializableString serializableString) throws IOException {
/* 419 */           return this.delegate.nextFieldName(serializableString);
                }

                @Override // com.fasterxml.jackson.core.JsonParser
/* 423 */       public JsonParser skipChildren() throws IOException {
/* 424 */           this.delegate.skipChildren();
/* 426 */           return this;
                }

                @Override // com.fasterxml.jackson.core.JsonParser
/* 429 */       public int releaseBuffered(OutputStream outputStream) throws IOException {
/* 430 */           return this.delegate.releaseBuffered(outputStream);
                }

                @Override // com.fasterxml.jackson.core.JsonParser
/* 433 */       public int releaseBuffered(Writer writer) throws IOException {
/* 434 */           return this.delegate.releaseBuffered(writer);
                }

                @Override // com.fasterxml.jackson.core.JsonParser
/* 444 */       public boolean canReadObjectId() {
/* 445 */           return this.delegate.canReadObjectId();
                }

                @Override // com.fasterxml.jackson.core.JsonParser
/* 449 */       public boolean canReadTypeId() {
/* 450 */           return this.delegate.canReadTypeId();
                }

                @Override // com.fasterxml.jackson.core.JsonParser
/* 454 */       public Object getObjectId() throws IOException {
/* 455 */           return this.delegate.getObjectId();
                }

                @Override // com.fasterxml.jackson.core.JsonParser
/* 459 */       public Object getTypeId() throws IOException {
/* 460 */           return this.delegate.getTypeId();
                }
            }
