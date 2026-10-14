            package com.fasterxml.jackson.core.util;

            import com.fasterxml.jackson.core.Base64Variant;
            import com.fasterxml.jackson.core.FormatSchema;
            import com.fasterxml.jackson.core.JsonGenerator;
            import com.fasterxml.jackson.core.JsonParser;
            import com.fasterxml.jackson.core.JsonStreamContext;
            import com.fasterxml.jackson.core.ObjectCodec;
            import com.fasterxml.jackson.core.PrettyPrinter;
            import com.fasterxml.jackson.core.SerializableString;
            import com.fasterxml.jackson.core.TreeNode;
            import com.fasterxml.jackson.core.Version;
            import com.fasterxml.jackson.core.p003io.CharacterEscapes;
            import java.io.IOException;
            import java.io.InputStream;
            import java.math.BigDecimal;
            import java.math.BigInteger;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 30 */    public class JsonGeneratorDelegate extends JsonGenerator {
                protected JsonGenerator delegate;
                protected boolean delegateCopyMethods;

/* 31 */        public JsonGeneratorDelegate(JsonGenerator jsonGenerator) {
/* 32 */            this(jsonGenerator, true);
                }

/* 39 */        public JsonGeneratorDelegate(JsonGenerator jsonGenerator, boolean z) {
/* 41 */            this.delegate = jsonGenerator;
/* 42 */            this.delegateCopyMethods = z;
                }

/* 51 */        public JsonGenerator getDelegate() {
/* 52 */            return this.delegate;
                }

                @Override // com.fasterxml.jackson.core.JsonGenerator
/* 62 */        public ObjectCodec getCodec() {
/* 63 */            return this.delegate.getCodec();
                }

                @Override // com.fasterxml.jackson.core.JsonGenerator
/* 67 */        public JsonGenerator setCodec(ObjectCodec objectCodec) {
/* 68 */            this.delegate.setCodec(objectCodec);
/* 69 */            return this;
                }

                @Override // com.fasterxml.jackson.core.JsonGenerator
/* 73 */        public void setSchema(FormatSchema formatSchema) {
/* 74 */            this.delegate.setSchema(formatSchema);
                }

                @Override // com.fasterxml.jackson.core.JsonGenerator
/* 78 */        public FormatSchema getSchema() {
/* 79 */            return this.delegate.getSchema();
                }

                @Override // com.fasterxml.jackson.core.JsonGenerator, com.fasterxml.jackson.core.Versioned
/* 83 */        public Version version() {
/* 84 */            return this.delegate.version();
                }

                @Override // com.fasterxml.jackson.core.JsonGenerator
/* 88 */        public Object getOutputTarget() {
/* 89 */            return this.delegate.getOutputTarget();
                }

                @Override // com.fasterxml.jackson.core.JsonGenerator
/* 99 */        public boolean canUseSchema(FormatSchema formatSchema) {
/* 100 */           return this.delegate.canUseSchema(formatSchema);
                }

                @Override // com.fasterxml.jackson.core.JsonGenerator
/* 104 */       public boolean canWriteTypeId() {
/* 105 */           return this.delegate.canWriteTypeId();
                }

                @Override // com.fasterxml.jackson.core.JsonGenerator
/* 109 */       public boolean canWriteObjectId() {
/* 110 */           return this.delegate.canWriteObjectId();
                }

                @Override // com.fasterxml.jackson.core.JsonGenerator
/* 114 */       public boolean canWriteBinaryNatively() {
/* 115 */           return this.delegate.canWriteBinaryNatively();
                }

                @Override // com.fasterxml.jackson.core.JsonGenerator
/* 119 */       public boolean canOmitFields() {
/* 120 */           return this.delegate.canOmitFields();
                }

                @Override // com.fasterxml.jackson.core.JsonGenerator
/* 130 */       public JsonGenerator enable(JsonGenerator.Feature feature) {
/* 131 */           this.delegate.enable(feature);
/* 132 */           return this;
                }

                @Override // com.fasterxml.jackson.core.JsonGenerator
/* 136 */       public JsonGenerator disable(JsonGenerator.Feature feature) {
/* 137 */           this.delegate.disable(feature);
/* 138 */           return this;
                }

                @Override // com.fasterxml.jackson.core.JsonGenerator
/* 142 */       public boolean isEnabled(JsonGenerator.Feature feature) {
/* 143 */           return this.delegate.isEnabled(feature);
                }

                @Override // com.fasterxml.jackson.core.JsonGenerator
/* 150 */       public int getFeatureMask() {
/* 151 */           return this.delegate.getFeatureMask();
                }

                @Override // com.fasterxml.jackson.core.JsonGenerator
/* 155 */       public JsonGenerator setFeatureMask(int i) {
/* 156 */           this.delegate.setFeatureMask(i);
/* 157 */           return this;
                }

                @Override // com.fasterxml.jackson.core.JsonGenerator
/* 167 */       public JsonGenerator setPrettyPrinter(PrettyPrinter prettyPrinter) {
/* 168 */           this.delegate.setPrettyPrinter(prettyPrinter);
/* 169 */           return this;
                }

                @Override // com.fasterxml.jackson.core.JsonGenerator
/* 173 */       public PrettyPrinter getPrettyPrinter() {
/* 174 */           return this.delegate.getPrettyPrinter();
                }

                @Override // com.fasterxml.jackson.core.JsonGenerator
/* 178 */       public JsonGenerator useDefaultPrettyPrinter() {
/* 179 */           this.delegate.useDefaultPrettyPrinter();
/* 180 */           return this;
                }

                @Override // com.fasterxml.jackson.core.JsonGenerator
/* 184 */       public JsonGenerator setHighestNonEscapedChar(int i) {
/* 185 */           this.delegate.setHighestNonEscapedChar(i);
/* 186 */           return this;
                }

                @Override // com.fasterxml.jackson.core.JsonGenerator
/* 190 */       public int getHighestEscapedChar() {
/* 191 */           return this.delegate.getHighestEscapedChar();
                }

                @Override // com.fasterxml.jackson.core.JsonGenerator
/* 195 */       public CharacterEscapes getCharacterEscapes() {
/* 196 */           return this.delegate.getCharacterEscapes();
                }

                @Override // com.fasterxml.jackson.core.JsonGenerator
/* 200 */       public JsonGenerator setCharacterEscapes(CharacterEscapes characterEscapes) {
/* 201 */           this.delegate.setCharacterEscapes(characterEscapes);
/* 202 */           return this;
                }

                @Override // com.fasterxml.jackson.core.JsonGenerator
/* 206 */       public JsonGenerator setRootValueSeparator(SerializableString serializableString) {
/* 207 */           this.delegate.setRootValueSeparator(serializableString);
/* 208 */           return this;
                }

                @Override // com.fasterxml.jackson.core.JsonGenerator
/* 218 */       public void writeStartArray() throws IOException {
/* 219 */           this.delegate.writeStartArray();
                }

                @Override // com.fasterxml.jackson.core.JsonGenerator
/* 224 */       public void writeEndArray() throws IOException {
/* 225 */           this.delegate.writeEndArray();
                }

                @Override // com.fasterxml.jackson.core.JsonGenerator
/* 229 */       public void writeStartObject() throws IOException {
/* 230 */           this.delegate.writeStartObject();
                }

                @Override // com.fasterxml.jackson.core.JsonGenerator
/* 234 */       public void writeEndObject() throws IOException {
/* 235 */           this.delegate.writeEndObject();
                }

                @Override // com.fasterxml.jackson.core.JsonGenerator
/* 241 */       public void writeFieldName(String str) throws IOException {
/* 242 */           this.delegate.writeFieldName(str);
                }

                @Override // com.fasterxml.jackson.core.JsonGenerator
/* 248 */       public void writeFieldName(SerializableString serializableString) throws IOException {
/* 249 */           this.delegate.writeFieldName(serializableString);
                }

                @Override // com.fasterxml.jackson.core.JsonGenerator
/* 259 */       public void writeString(String str) throws IOException {
/* 260 */           this.delegate.writeString(str);
                }

                @Override // com.fasterxml.jackson.core.JsonGenerator
/* 264 */       public void writeString(char[] cArr, int i, int i2) throws IOException {
/* 265 */           this.delegate.writeString(cArr, i, i2);
                }

                @Override // com.fasterxml.jackson.core.JsonGenerator
/* 269 */       public void writeString(SerializableString serializableString) throws IOException {
/* 270 */           this.delegate.writeString(serializableString);
                }

                @Override // com.fasterxml.jackson.core.JsonGenerator
/* 276 */       public void writeRawUTF8String(byte[] bArr, int i, int i2) throws IOException {
/* 277 */           this.delegate.writeRawUTF8String(bArr, i, i2);
                }

                @Override // com.fasterxml.jackson.core.JsonGenerator
/* 283 */       public void writeUTF8String(byte[] bArr, int i, int i2) throws IOException {
/* 284 */           this.delegate.writeUTF8String(bArr, i, i2);
                }

                @Override // com.fasterxml.jackson.core.JsonGenerator
/* 294 */       public void writeRaw(String str) throws IOException {
/* 295 */           this.delegate.writeRaw(str);
                }

                @Override // com.fasterxml.jackson.core.JsonGenerator
/* 299 */       public void writeRaw(String str, int i, int i2) throws IOException {
/* 300 */           this.delegate.writeRaw(str, i, i2);
                }

                @Override // com.fasterxml.jackson.core.JsonGenerator
/* 305 */       public void writeRaw(SerializableString serializableString) throws IOException {
/* 306 */           this.delegate.writeRaw(serializableString);
                }

                @Override // com.fasterxml.jackson.core.JsonGenerator
/* 310 */       public void writeRaw(char[] cArr, int i, int i2) throws IOException {
/* 311 */           this.delegate.writeRaw(cArr, i, i2);
                }

                @Override // com.fasterxml.jackson.core.JsonGenerator
/* 315 */       public void writeRaw(char c) throws IOException {
/* 316 */           this.delegate.writeRaw(c);
                }

                @Override // com.fasterxml.jackson.core.JsonGenerator
/* 320 */       public void writeRawValue(String str) throws IOException {
/* 321 */           this.delegate.writeRawValue(str);
                }

                @Override // com.fasterxml.jackson.core.JsonGenerator
/* 325 */       public void writeRawValue(String str, int i, int i2) throws IOException {
/* 326 */           this.delegate.writeRawValue(str, i, i2);
                }

                @Override // com.fasterxml.jackson.core.JsonGenerator
/* 330 */       public void writeRawValue(char[] cArr, int i, int i2) throws IOException {
/* 331 */           this.delegate.writeRawValue(cArr, i, i2);
                }

                @Override // com.fasterxml.jackson.core.JsonGenerator
/* 337 */       public void writeBinary(Base64Variant base64Variant, byte[] bArr, int i, int i2) throws IOException {
/* 338 */           this.delegate.writeBinary(base64Variant, bArr, i, i2);
                }

                @Override // com.fasterxml.jackson.core.JsonGenerator
/* 343 */       public int writeBinary(Base64Variant base64Variant, InputStream inputStream, int i) throws IOException {
/* 344 */           return this.delegate.writeBinary(base64Variant, inputStream, i);
                }

                @Override // com.fasterxml.jackson.core.JsonGenerator
/* 354 */       public void writeNumber(short s) throws IOException {
/* 355 */           this.delegate.writeNumber(s);
                }

                @Override // com.fasterxml.jackson.core.JsonGenerator
/* 359 */       public void writeNumber(int i) throws IOException {
/* 360 */           this.delegate.writeNumber(i);
                }

                @Override // com.fasterxml.jackson.core.JsonGenerator
/* 364 */       public void writeNumber(long j) throws IOException {
/* 365 */           this.delegate.writeNumber(j);
                }

                @Override // com.fasterxml.jackson.core.JsonGenerator
/* 370 */       public void writeNumber(BigInteger bigInteger) throws IOException {
/* 371 */           this.delegate.writeNumber(bigInteger);
                }

                @Override // com.fasterxml.jackson.core.JsonGenerator
/* 376 */       public void writeNumber(double d) throws IOException {
/* 377 */           this.delegate.writeNumber(d);
                }

                @Override // com.fasterxml.jackson.core.JsonGenerator
/* 382 */       public void writeNumber(float f) throws IOException {
/* 383 */           this.delegate.writeNumber(f);
                }

                @Override // com.fasterxml.jackson.core.JsonGenerator
/* 388 */       public void writeNumber(BigDecimal bigDecimal) throws IOException {
/* 389 */           this.delegate.writeNumber(bigDecimal);
                }

                @Override // com.fasterxml.jackson.core.JsonGenerator
/* 393 */       public void writeNumber(String str) throws UnsupportedOperationException, IOException {
/* 394 */           this.delegate.writeNumber(str);
                }

                @Override // com.fasterxml.jackson.core.JsonGenerator
/* 398 */       public void writeBoolean(boolean z) throws IOException {
/* 399 */           this.delegate.writeBoolean(z);
                }

                @Override // com.fasterxml.jackson.core.JsonGenerator
/* 403 */       public void writeNull() throws IOException {
/* 404 */           this.delegate.writeNull();
                }

                @Override // com.fasterxml.jackson.core.JsonGenerator
/* 416 */       public void writeOmittedField(String str) throws IOException {
/* 417 */           this.delegate.writeOmittedField(str);
                }

                @Override // com.fasterxml.jackson.core.JsonGenerator
/* 428 */       public void writeObjectId(Object obj) throws IOException {
/* 429 */           this.delegate.writeObjectId(obj);
                }

                @Override // com.fasterxml.jackson.core.JsonGenerator
/* 434 */       public void writeObjectRef(Object obj) throws IOException {
/* 435 */           this.delegate.writeObjectRef(obj);
                }

                @Override // com.fasterxml.jackson.core.JsonGenerator
/* 440 */       public void writeTypeId(Object obj) throws IOException {
/* 441 */           this.delegate.writeTypeId(obj);
                }

                @Override // com.fasterxml.jackson.core.JsonGenerator
/* 451 */       public void writeObject(Object obj) throws IOException {
/* 452 */           if (this.delegateCopyMethods) {
/* 453 */               this.delegate.writeObject(obj);
/* 466 */               return;
                    }
/* 457 */           if (obj == null) {
/* 458 */               writeNull();
                    } else if (getCodec() != null) {
/* 461 */               getCodec().writeValue(this, obj);
                    } else {
/* 464 */               _writeSimpleObject(obj);
                    }
                }

                @Override // com.fasterxml.jackson.core.JsonGenerator
/* 469 */       public void writeTree(TreeNode treeNode) throws IOException {
/* 470 */           if (this.delegateCopyMethods) {
/* 471 */               this.delegate.writeTree(treeNode);
                    } else if (treeNode == null) {
/* 476 */               writeNull();
                    } else {
/* 478 */               if (getCodec() == null) {
/* 479 */                   throw new IllegalStateException("No ObjectCodec defined");
                        }
/* 481 */               getCodec().writeValue(this, treeNode);
                    }
                }

                @Override // com.fasterxml.jackson.core.JsonGenerator
/* 500 */       public void copyCurrentEvent(JsonParser jsonParser) throws IOException {
/* 501 */           if (this.delegateCopyMethods) {
/* 502 */               this.delegate.copyCurrentEvent(jsonParser);
                    } else {
/* 504 */               super.copyCurrentEvent(jsonParser);
                    }
                }

                @Override // com.fasterxml.jackson.core.JsonGenerator
/* 510 */       public void copyCurrentStructure(JsonParser jsonParser) throws IOException {
/* 511 */           if (this.delegateCopyMethods) {
/* 512 */               this.delegate.copyCurrentStructure(jsonParser);
                    } else {
/* 514 */               super.copyCurrentStructure(jsonParser);
                    }
                }

                @Override // com.fasterxml.jackson.core.JsonGenerator
/* 525 */       public JsonStreamContext getOutputContext() {
/* 526 */           return this.delegate.getOutputContext();
                }

                @Override // com.fasterxml.jackson.core.JsonGenerator, java.io.Flushable
/* 536 */       public void flush() throws IOException {
/* 537 */           this.delegate.flush();
                }

                @Override // com.fasterxml.jackson.core.JsonGenerator, java.io.Closeable, java.lang.AutoCloseable
/* 541 */       public void close() throws IOException {
/* 542 */           this.delegate.close();
                }

                @Override // com.fasterxml.jackson.core.JsonGenerator
/* 552 */       public boolean isClosed() {
/* 553 */           return this.delegate.isClosed();
                }
            }
