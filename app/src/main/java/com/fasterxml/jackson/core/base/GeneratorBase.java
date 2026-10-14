            package com.fasterxml.jackson.core.base;

            import com.fasterxml.jackson.core.Base64Variant;
            import com.fasterxml.jackson.core.JsonGenerator;
            import com.fasterxml.jackson.core.JsonStreamContext;
            import com.fasterxml.jackson.core.ObjectCodec;
            import com.fasterxml.jackson.core.SerializableString;
            import com.fasterxml.jackson.core.TreeNode;
            import com.fasterxml.jackson.core.Version;
            import com.fasterxml.jackson.core.json.DupDetector;
            import com.fasterxml.jackson.core.json.JsonWriteContext;
            import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
            import com.fasterxml.jackson.core.util.VersionUtil;
            import java.io.IOException;
            import java.io.InputStream;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 14 */    public abstract class GeneratorBase extends JsonGenerator {
                protected boolean _cfgNumbersAsStrings;
                protected boolean _closed;
                protected int _features;
                protected ObjectCodec _objectCodec;
                protected JsonWriteContext _writeContext;

                protected abstract void _releaseBuffers();

                protected abstract void _verifyValueWrite(String str) throws IOException;

                @Override // com.fasterxml.jackson.core.JsonGenerator, java.io.Flushable
                public abstract void flush() throws IOException;


/* 67 */        protected GeneratorBase(int i, ObjectCodec objectCodec) {
/* 69 */            this._features = i;
/* 72 */            this._writeContext = JsonWriteContext.createRootContext(JsonGenerator.Feature.STRICT_DUPLICATE_DETECTION.enabledIn(i) ? DupDetector.rootDetector(this) : null);
/* 73 */            this._objectCodec = objectCodec;
/* 74 */            this._cfgNumbersAsStrings = JsonGenerator.Feature.WRITE_NUMBERS_AS_STRINGS.enabledIn(i);
                }

                @Override // com.fasterxml.jackson.core.JsonGenerator, com.fasterxml.jackson.core.Versioned
/* 82 */        public Version version() {
/* 83 */            return VersionUtil.versionFor(getClass());
                }

                @Override // com.fasterxml.jackson.core.JsonGenerator
/* 93 */        public JsonGenerator enable(JsonGenerator.Feature feature) {
/* 94 */            this._features |= feature.getMask();
/* 95 */            if (feature == JsonGenerator.Feature.WRITE_NUMBERS_AS_STRINGS) {
/* 96 */                this._cfgNumbersAsStrings = true;
                    } else if (feature == JsonGenerator.Feature.ESCAPE_NON_ASCII) {
/* 98 */                setHighestNonEscapedChar(127);
                    }
/* 100 */           return this;
                }

                @Override // com.fasterxml.jackson.core.JsonGenerator
/* 104 */       public JsonGenerator disable(JsonGenerator.Feature feature) {
/* 105 */           this._features &= feature.getMask() ^ (-1);
/* 106 */           if (feature == JsonGenerator.Feature.WRITE_NUMBERS_AS_STRINGS) {
/* 107 */               this._cfgNumbersAsStrings = false;
                    } else if (feature == JsonGenerator.Feature.ESCAPE_NON_ASCII) {
/* 109 */               setHighestNonEscapedChar(0);
                    }
/* 111 */           return this;
                }

                @Override // com.fasterxml.jackson.core.JsonGenerator
/* 117 */       public final boolean isEnabled(JsonGenerator.Feature feature) {
                    return (this._features & feature.getMask()) != 0;
                }

                @Override // com.fasterxml.jackson.core.JsonGenerator
/* 122 */       public int getFeatureMask() {
/* 123 */           return this._features;
                }

                @Override // com.fasterxml.jackson.core.JsonGenerator
/* 127 */       public JsonGenerator setFeatureMask(int i) {
/* 128 */           this._features = i;
/* 129 */           return this;
                }

                @Override // com.fasterxml.jackson.core.JsonGenerator
/* 136 */       public JsonGenerator useDefaultPrettyPrinter() {
                    return getPrettyPrinter() != null ? this : setPrettyPrinter(new DefaultPrettyPrinter());
                }

                @Override // com.fasterxml.jackson.core.JsonGenerator
/* 144 */       public JsonGenerator setCodec(ObjectCodec objectCodec) {
/* 145 */           this._objectCodec = objectCodec;
/* 146 */           return this;
                }

                @Override // com.fasterxml.jackson.core.JsonGenerator
/* 149 */       public final ObjectCodec getCodec() {
/* 150 */           return this._objectCodec;
                }

                @Override // com.fasterxml.jackson.core.JsonGenerator
/* 161 */       public final JsonWriteContext getOutputContext() {
/* 162 */           return this._writeContext;
                }

                @Override // com.fasterxml.jackson.core.JsonGenerator
/* 182 */       public void writeFieldName(SerializableString serializableString) throws IOException {
/* 183 */           writeFieldName(serializableString.getValue());
                }

                @Override // com.fasterxml.jackson.core.JsonGenerator
/* 195 */       public void writeString(SerializableString serializableString) throws IOException {
/* 196 */           writeString(serializableString.getValue());
                }

                @Override // com.fasterxml.jackson.core.JsonGenerator
/* 200 */       public void writeRawValue(String str) throws IOException {
/* 201 */           _verifyValueWrite("write raw value");
/* 202 */           writeRaw(str);
                }

                @Override // com.fasterxml.jackson.core.JsonGenerator
/* 206 */       public void writeRawValue(String str, int i, int i2) throws IOException {
/* 207 */           _verifyValueWrite("write raw value");
/* 208 */           writeRaw(str, i, i2);
                }

                @Override // com.fasterxml.jackson.core.JsonGenerator
/* 212 */       public void writeRawValue(char[] cArr, int i, int i2) throws IOException {
/* 213 */           _verifyValueWrite("write raw value");
/* 214 */           writeRaw(cArr, i, i2);
                }

                @Override // com.fasterxml.jackson.core.JsonGenerator
/* 219 */       public int writeBinary(Base64Variant base64Variant, InputStream inputStream, int i) throws IOException {
/* 220 */           _reportUnsupportedOperation();
/* 221 */           return 0;
                }

                @Override // com.fasterxml.jackson.core.JsonGenerator
/* 250 */       public void writeObject(Object obj) throws IOException {
/* 251 */           if (obj == null) {
/* 253 */               writeNull();
                    } else if (this._objectCodec != null) {
/* 261 */               this._objectCodec.writeValue(this, obj);
                    } else {
/* 264 */               _writeSimpleObject(obj);
                    }
                }

                @Override // com.fasterxml.jackson.core.JsonGenerator
/* 271 */       public void writeTree(TreeNode treeNode) throws IOException {
/* 272 */           if (treeNode == null) {
/* 273 */               writeNull();
                    } else {
/* 275 */               if (this._objectCodec == null) {
/* 276 */                   throw new IllegalStateException("No ObjectCodec defined");
                        }
/* 278 */               this._objectCodec.writeValue(this, treeNode);
                    }
                }

                @Override // com.fasterxml.jackson.core.JsonGenerator, java.io.Closeable, java.lang.AutoCloseable
/* 288 */       public void close() throws IOException {
/* 289 */           this._closed = true;
                }

                @Override // com.fasterxml.jackson.core.JsonGenerator
/* 289 */       public boolean isClosed() {
/* 290 */           return this._closed;
                }

                @Override // com.fasterxml.jackson.core.JsonGenerator
/* 315 */       protected void _writeSimpleObject(Object obj) throws IOException {
/* 316 */           super._writeSimpleObject(obj);
                }
            }
