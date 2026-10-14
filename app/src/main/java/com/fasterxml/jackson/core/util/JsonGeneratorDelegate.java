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
            
            
    public class JsonGeneratorDelegate extends JsonGenerator {
                protected JsonGenerator delegate;
                protected boolean delegateCopyMethods;

        public JsonGeneratorDelegate(JsonGenerator jsonGenerator) {
            this(jsonGenerator, true);
                }

        public JsonGeneratorDelegate(JsonGenerator jsonGenerator, boolean z) {
            this.delegate = jsonGenerator;
            this.delegateCopyMethods = z;
                }

        public JsonGenerator getDelegate() {
            return this.delegate;
                }

                @Override 
        public ObjectCodec getCodec() {
            return this.delegate.getCodec();
                }

                @Override 
        public JsonGenerator setCodec(ObjectCodec objectCodec) {
            this.delegate.setCodec(objectCodec);
            return this;
                }

                @Override 
        public void setSchema(FormatSchema formatSchema) {
            this.delegate.setSchema(formatSchema);
                }

                @Override 
        public FormatSchema getSchema() {
            return this.delegate.getSchema();
                }

                @Override 
        public Version version() {
            return this.delegate.version();
                }

                @Override 
        public Object getOutputTarget() {
            return this.delegate.getOutputTarget();
                }

                @Override 
        public boolean canUseSchema(FormatSchema formatSchema) {
           return this.delegate.canUseSchema(formatSchema);
                }

                @Override 
       public boolean canWriteTypeId() {
           return this.delegate.canWriteTypeId();
                }

                @Override 
       public boolean canWriteObjectId() {
           return this.delegate.canWriteObjectId();
                }

                @Override 
       public boolean canWriteBinaryNatively() {
           return this.delegate.canWriteBinaryNatively();
                }

                @Override 
       public boolean canOmitFields() {
           return this.delegate.canOmitFields();
                }

                @Override 
       public JsonGenerator enable(JsonGenerator.Feature feature) {
           this.delegate.enable(feature);
           return this;
                }

                @Override 
       public JsonGenerator disable(JsonGenerator.Feature feature) {
           this.delegate.disable(feature);
           return this;
                }

                @Override 
       public boolean isEnabled(JsonGenerator.Feature feature) {
           return this.delegate.isEnabled(feature);
                }

                @Override 
       public int getFeatureMask() {
           return this.delegate.getFeatureMask();
                }

                @Override 
       public JsonGenerator setFeatureMask(int i) {
           this.delegate.setFeatureMask(i);
           return this;
                }

                @Override 
       public JsonGenerator setPrettyPrinter(PrettyPrinter prettyPrinter) {
           this.delegate.setPrettyPrinter(prettyPrinter);
           return this;
                }

                @Override 
       public PrettyPrinter getPrettyPrinter() {
           return this.delegate.getPrettyPrinter();
                }

                @Override 
       public JsonGenerator useDefaultPrettyPrinter() {
           this.delegate.useDefaultPrettyPrinter();
           return this;
                }

                @Override 
       public JsonGenerator setHighestNonEscapedChar(int i) {
           this.delegate.setHighestNonEscapedChar(i);
           return this;
                }

                @Override 
       public int getHighestEscapedChar() {
           return this.delegate.getHighestEscapedChar();
                }

                @Override 
       public CharacterEscapes getCharacterEscapes() {
           return this.delegate.getCharacterEscapes();
                }

                @Override 
       public JsonGenerator setCharacterEscapes(CharacterEscapes characterEscapes) {
           this.delegate.setCharacterEscapes(characterEscapes);
           return this;
                }

                @Override 
       public JsonGenerator setRootValueSeparator(SerializableString serializableString) {
           this.delegate.setRootValueSeparator(serializableString);
           return this;
                }

                @Override 
       public void writeStartArray() throws IOException {
           this.delegate.writeStartArray();
                }

                @Override 
       public void writeEndArray() throws IOException {
           this.delegate.writeEndArray();
                }

                @Override 
       public void writeStartObject() throws IOException {
           this.delegate.writeStartObject();
                }

                @Override 
       public void writeEndObject() throws IOException {
           this.delegate.writeEndObject();
                }

                @Override 
       public void writeFieldName(String str) throws IOException {
           this.delegate.writeFieldName(str);
                }

                @Override 
       public void writeFieldName(SerializableString serializableString) throws IOException {
           this.delegate.writeFieldName(serializableString);
                }

                @Override 
       public void writeString(String str) throws IOException {
           this.delegate.writeString(str);
                }

                @Override 
       public void writeString(char[] cArr, int i, int i2) throws IOException {
           this.delegate.writeString(cArr, i, i2);
                }

                @Override 
       public void writeString(SerializableString serializableString) throws IOException {
           this.delegate.writeString(serializableString);
                }

                @Override 
       public void writeRawUTF8String(byte[] bArr, int i, int i2) throws IOException {
           this.delegate.writeRawUTF8String(bArr, i, i2);
                }

                @Override 
       public void writeUTF8String(byte[] bArr, int i, int i2) throws IOException {
           this.delegate.writeUTF8String(bArr, i, i2);
                }

                @Override 
       public void writeRaw(String str) throws IOException {
           this.delegate.writeRaw(str);
                }

                @Override 
       public void writeRaw(String str, int i, int i2) throws IOException {
           this.delegate.writeRaw(str, i, i2);
                }

                @Override 
       public void writeRaw(SerializableString serializableString) throws IOException {
           this.delegate.writeRaw(serializableString);
                }

                @Override 
       public void writeRaw(char[] cArr, int i, int i2) throws IOException {
           this.delegate.writeRaw(cArr, i, i2);
                }

                @Override 
       public void writeRaw(char c) throws IOException {
           this.delegate.writeRaw(c);
                }

                @Override 
       public void writeRawValue(String str) throws IOException {
           this.delegate.writeRawValue(str);
                }

                @Override 
       public void writeRawValue(String str, int i, int i2) throws IOException {
           this.delegate.writeRawValue(str, i, i2);
                }

                @Override 
       public void writeRawValue(char[] cArr, int i, int i2) throws IOException {
           this.delegate.writeRawValue(cArr, i, i2);
                }

                @Override 
       public void writeBinary(Base64Variant base64Variant, byte[] bArr, int i, int i2) throws IOException {
           this.delegate.writeBinary(base64Variant, bArr, i, i2);
                }

                @Override 
       public int writeBinary(Base64Variant base64Variant, InputStream inputStream, int i) throws IOException {
           return this.delegate.writeBinary(base64Variant, inputStream, i);
                }

                @Override 
       public void writeNumber(short s) throws IOException {
           this.delegate.writeNumber(s);
                }

                @Override 
       public void writeNumber(int i) throws IOException {
           this.delegate.writeNumber(i);
                }

                @Override 
       public void writeNumber(long j) throws IOException {
           this.delegate.writeNumber(j);
                }

                @Override 
       public void writeNumber(BigInteger bigInteger) throws IOException {
           this.delegate.writeNumber(bigInteger);
                }

                @Override 
       public void writeNumber(double d) throws IOException {
           this.delegate.writeNumber(d);
                }

                @Override 
       public void writeNumber(float f) throws IOException {
           this.delegate.writeNumber(f);
                }

                @Override 
       public void writeNumber(BigDecimal bigDecimal) throws IOException {
           this.delegate.writeNumber(bigDecimal);
                }

                @Override 
       public void writeNumber(String str) throws UnsupportedOperationException, IOException {
           this.delegate.writeNumber(str);
                }

                @Override 
       public void writeBoolean(boolean z) throws IOException {
           this.delegate.writeBoolean(z);
                }

                @Override 
       public void writeNull() throws IOException {
           this.delegate.writeNull();
                }

                @Override 
       public void writeOmittedField(String str) throws IOException {
           this.delegate.writeOmittedField(str);
                }

                @Override 
       public void writeObjectId(Object obj) throws IOException {
           this.delegate.writeObjectId(obj);
                }

                @Override 
       public void writeObjectRef(Object obj) throws IOException {
           this.delegate.writeObjectRef(obj);
                }

                @Override 
       public void writeTypeId(Object obj) throws IOException {
           this.delegate.writeTypeId(obj);
                }

                @Override 
       public void writeObject(Object obj) throws IOException {
           if (this.delegateCopyMethods) {
               this.delegate.writeObject(obj);
               return;
                    }
           if (obj == null) {
               writeNull();
                    } else if (getCodec() != null) {
               getCodec().writeValue(this, obj);
                    } else {
               _writeSimpleObject(obj);
                    }
                }

                @Override 
       public void writeTree(TreeNode treeNode) throws IOException {
           if (this.delegateCopyMethods) {
               this.delegate.writeTree(treeNode);
                    } else if (treeNode == null) {
               writeNull();
                    } else {
               if (getCodec() == null) {
                   throw new IllegalStateException("No ObjectCodec defined");
                        }
               getCodec().writeValue(this, treeNode);
                    }
                }

                @Override 
       public void copyCurrentEvent(JsonParser jsonParser) throws IOException {
           if (this.delegateCopyMethods) {
               this.delegate.copyCurrentEvent(jsonParser);
                    } else {
               super.copyCurrentEvent(jsonParser);
                    }
                }

                @Override 
       public void copyCurrentStructure(JsonParser jsonParser) throws IOException {
           if (this.delegateCopyMethods) {
               this.delegate.copyCurrentStructure(jsonParser);
                    } else {
               super.copyCurrentStructure(jsonParser);
                    }
                }

                @Override 
       public JsonStreamContext getOutputContext() {
           return this.delegate.getOutputContext();
                }

                @Override 
       public void flush() throws IOException {
           this.delegate.flush();
                }

                @Override 
       public void close() throws IOException {
           this.delegate.close();
                }

                @Override 
       public boolean isClosed() {
           return this.delegate.isClosed();
                }
            }
