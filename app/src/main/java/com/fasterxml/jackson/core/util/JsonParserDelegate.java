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
            
            
    public class JsonParserDelegate extends JsonParser {
                protected JsonParser delegate;

        public JsonParserDelegate(JsonParser jsonParser) {
            this.delegate = jsonParser;
                }

                @Override 
        public void setCodec(ObjectCodec objectCodec) {
            this.delegate.setCodec(objectCodec);
                }

                @Override 
        public ObjectCodec getCodec() {
            return this.delegate.getCodec();
                }

                @Override 
        public JsonParser enable(JsonParser.Feature feature) {
            this.delegate.enable(feature);
            return this;
                }

                @Override 
        public JsonParser disable(JsonParser.Feature feature) {
            this.delegate.disable(feature);
            return this;
                }

                @Override 
        public boolean isEnabled(JsonParser.Feature feature) {
            return this.delegate.isEnabled(feature);
                }

                @Override 
        public JsonParser configure(JsonParser.Feature feature, boolean z) {
            return this.delegate.configure(feature, z);
                }

                @Override 
        public int getFeatureMask() {
            return this.delegate.getFeatureMask();
                }

                @Override 
        public JsonParser setFeatureMask(int i) {
            this.delegate.setFeatureMask(i);
            return this;
                }

                @Override 
        public FormatSchema getSchema() {
            return this.delegate.getSchema();
                }

                @Override 
        public void setSchema(FormatSchema formatSchema) {
            this.delegate.setSchema(formatSchema);
                }

                @Override 
        public boolean canUseSchema(FormatSchema formatSchema) {
            return this.delegate.canUseSchema(formatSchema);
                }

                @Override 
        public Version version() {
            return this.delegate.version();
                }

                @Override 
       public Object getInputSource() {
           return this.delegate.getInputSource();
                }

                @Override 
       public boolean requiresCustomCodec() {
           return this.delegate.requiresCustomCodec();
                }

                @Override 
       public void close() throws IOException {
           this.delegate.close();
                }

                @Override 
       public boolean isClosed() {
           return this.delegate.isClosed();
                }

                @Override 
       public JsonToken getCurrentToken() {
           return this.delegate.getCurrentToken();
                }

                @Override 
       public int getCurrentTokenId() {
           return this.delegate.getCurrentTokenId();
                }

                @Override 
       public boolean hasCurrentToken() {
           return this.delegate.hasCurrentToken();
                }

                @Override 
       public String getCurrentName() throws IOException {
           return this.delegate.getCurrentName();
                }

                @Override 
       public JsonLocation getCurrentLocation() {
           return this.delegate.getCurrentLocation();
                }

                @Override 
       public JsonStreamContext getParsingContext() {
           return this.delegate.getParsingContext();
                }

                @Override 
       public boolean isExpectedStartArrayToken() {
           return this.delegate.isExpectedStartArrayToken();
                }

                @Override 
       public void clearCurrentToken() {
           this.delegate.clearCurrentToken();
                }

                @Override 
       public JsonToken getLastClearedToken() {
           return this.delegate.getLastClearedToken();
                }

                @Override 
       public void overrideCurrentName(String str) {
           this.delegate.overrideCurrentName(str);
                }

                @Override 
       public String getText() throws IOException {
           return this.delegate.getText();
                }

                @Override 
       public boolean hasTextCharacters() {
           return this.delegate.hasTextCharacters();
                }

                @Override 
       public char[] getTextCharacters() throws IOException {
           return this.delegate.getTextCharacters();
                }

                @Override 
       public int getTextLength() throws IOException {
           return this.delegate.getTextLength();
                }

                @Override 
       public int getTextOffset() throws IOException {
           return this.delegate.getTextOffset();
                }

                @Override 
       public String nextTextValue() throws IOException {
           return this.delegate.nextTextValue();
                }

                @Override 
       public BigInteger getBigIntegerValue() throws IOException {
           return this.delegate.getBigIntegerValue();
                }

                @Override 
       public boolean getBooleanValue() throws IOException {
           return this.delegate.getBooleanValue();
                }

                @Override 
       public byte getByteValue() throws IOException {
           return this.delegate.getByteValue();
                }

                @Override 
       public short getShortValue() throws IOException {
           return this.delegate.getShortValue();
                }

                @Override 
       public BigDecimal getDecimalValue() throws IOException {
           return this.delegate.getDecimalValue();
                }

                @Override 
       public double getDoubleValue() throws IOException {
           return this.delegate.getDoubleValue();
                }

                @Override 
       public float getFloatValue() throws IOException {
           return this.delegate.getFloatValue();
                }

                @Override 
       public int getIntValue() throws IOException {
           return this.delegate.getIntValue();
                }

                @Override 
       public long getLongValue() throws IOException {
           return this.delegate.getLongValue();
                }

                @Override 
       public JsonParser.NumberType getNumberType() throws IOException {
           return this.delegate.getNumberType();
                }

                @Override 
       public Number getNumberValue() throws IOException {
           return this.delegate.getNumberValue();
                }

                @Override 
       public Boolean nextBooleanValue() throws IOException {
           return this.delegate.nextBooleanValue();
                }

                @Override 
       public int nextIntValue(int i) throws IOException {
           return this.delegate.nextIntValue(i);
                }

                @Override 
       public long nextLongValue(long j) throws IOException {
           return this.delegate.nextLongValue(j);
                }

                @Override 
       public int getValueAsInt() throws IOException {
           return this.delegate.getValueAsInt();
                }

                @Override 
       public int getValueAsInt(int i) throws IOException {
           return this.delegate.getValueAsInt(i);
                }

                @Override 
       public long getValueAsLong() throws IOException {
           return this.delegate.getValueAsLong();
                }

                @Override 
       public long getValueAsLong(long j) throws IOException {
           return this.delegate.getValueAsLong(j);
                }

                @Override 
       public double getValueAsDouble() throws IOException {
           return this.delegate.getValueAsDouble();
                }

                @Override 
       public double getValueAsDouble(double d) throws IOException {
           return this.delegate.getValueAsDouble(d);
                }

                @Override 
       public boolean getValueAsBoolean() throws IOException {
           return this.delegate.getValueAsBoolean();
                }

                @Override 
       public boolean getValueAsBoolean(boolean z) throws IOException {
           return this.delegate.getValueAsBoolean(z);
                }

                @Override 
       public String getValueAsString() throws IOException {
           return this.delegate.getValueAsString();
                }

                @Override 
       public String getValueAsString(String str) throws IOException {
           return this.delegate.getValueAsString(str);
                }

                @Override 
       public <T> T readValueAs(Class<T> cls) throws IOException {
           return (T) this.delegate.readValueAs(cls);
                }

                @Override 
       public <T> T readValueAs(TypeReference<?> typeReference) throws IOException {
           return (T) this.delegate.readValueAs(typeReference);
                }

                @Override 
       public <T> Iterator<T> readValuesAs(Class<T> cls) throws IOException {
           return this.delegate.readValuesAs(cls);
                }

                @Override 
       public <T> Iterator<T> readValuesAs(TypeReference<?> typeReference) throws IOException {
           return this.delegate.readValuesAs(typeReference);
                }

                @Override 
       public <T extends TreeNode> T readValueAsTree() throws IOException {
           return (T) this.delegate.readValueAsTree();
                }

                @Override 
       public Object getEmbeddedObject() throws IOException {
           return this.delegate.getEmbeddedObject();
                }

                @Override 
       public byte[] getBinaryValue(Base64Variant base64Variant) throws IOException {
           return this.delegate.getBinaryValue(base64Variant);
                }

                @Override 
       public byte[] getBinaryValue() throws IOException {
           return this.delegate.getBinaryValue();
                }

                @Override 
       public int readBinaryValue(Base64Variant base64Variant, OutputStream outputStream) throws IOException {
           return this.delegate.readBinaryValue(base64Variant, outputStream);
                }

                @Override 
       public int readBinaryValue(OutputStream outputStream) throws IOException {
           return this.delegate.readBinaryValue(outputStream);
                }

                @Override 
       public JsonLocation getTokenLocation() {
           return this.delegate.getTokenLocation();
                }

                @Override 
       public JsonToken nextToken() throws IOException {
           return this.delegate.nextToken();
                }

                @Override 
       public JsonToken nextValue() throws IOException {
           return this.delegate.nextValue();
                }

                @Override 
       public boolean nextFieldName(SerializableString serializableString) throws IOException {
           return this.delegate.nextFieldName(serializableString);
                }

                @Override 
       public JsonParser skipChildren() throws IOException {
           this.delegate.skipChildren();
           return this;
                }

                @Override 
       public int releaseBuffered(OutputStream outputStream) throws IOException {
           return this.delegate.releaseBuffered(outputStream);
                }

                @Override 
       public int releaseBuffered(Writer writer) throws IOException {
           return this.delegate.releaseBuffered(writer);
                }

                @Override 
       public boolean canReadObjectId() {
           return this.delegate.canReadObjectId();
                }

                @Override 
       public boolean canReadTypeId() {
           return this.delegate.canReadTypeId();
                }

                @Override 
       public Object getObjectId() throws IOException {
           return this.delegate.getObjectId();
                }

                @Override 
       public Object getTypeId() throws IOException {
           return this.delegate.getTypeId();
                }
            }
