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
            
            
    public abstract class GeneratorBase extends JsonGenerator {
                protected boolean _cfgNumbersAsStrings;
                protected boolean _closed;
                protected int _features;
                protected ObjectCodec _objectCodec;
                protected JsonWriteContext _writeContext;

                protected abstract void _releaseBuffers();

                protected abstract void _verifyValueWrite(String str) throws IOException;

                @Override 
                public abstract void flush() throws IOException;


        protected GeneratorBase(int i, ObjectCodec objectCodec) {
            this._features = i;
            this._writeContext = JsonWriteContext.createRootContext(JsonGenerator.Feature.STRICT_DUPLICATE_DETECTION.enabledIn(i) ? DupDetector.rootDetector(this) : null);
            this._objectCodec = objectCodec;
            this._cfgNumbersAsStrings = JsonGenerator.Feature.WRITE_NUMBERS_AS_STRINGS.enabledIn(i);
                }

                @Override 
        public Version version() {
            return VersionUtil.versionFor(getClass());
                }

                @Override 
        public JsonGenerator enable(JsonGenerator.Feature feature) {
            this._features |= feature.getMask();
            if (feature == JsonGenerator.Feature.WRITE_NUMBERS_AS_STRINGS) {
                this._cfgNumbersAsStrings = true;
                    } else if (feature == JsonGenerator.Feature.ESCAPE_NON_ASCII) {
                setHighestNonEscapedChar(127);
                    }
           return this;
                }

                @Override 
       public JsonGenerator disable(JsonGenerator.Feature feature) {
           this._features &= feature.getMask() ^ (-1);
           if (feature == JsonGenerator.Feature.WRITE_NUMBERS_AS_STRINGS) {
               this._cfgNumbersAsStrings = false;
                    } else if (feature == JsonGenerator.Feature.ESCAPE_NON_ASCII) {
               setHighestNonEscapedChar(0);
                    }
           return this;
                }

                @Override 
       public final boolean isEnabled(JsonGenerator.Feature feature) {
                    return (this._features & feature.getMask()) != 0;
                }

                @Override 
       public int getFeatureMask() {
           return this._features;
                }

                @Override 
       public JsonGenerator setFeatureMask(int i) {
           this._features = i;
           return this;
                }

                @Override 
       public JsonGenerator useDefaultPrettyPrinter() {
                    return getPrettyPrinter() != null ? this : setPrettyPrinter(new DefaultPrettyPrinter());
                }

                @Override 
       public JsonGenerator setCodec(ObjectCodec objectCodec) {
           this._objectCodec = objectCodec;
           return this;
                }

                @Override 
       public final ObjectCodec getCodec() {
           return this._objectCodec;
                }

                @Override 
       public final JsonWriteContext getOutputContext() {
           return this._writeContext;
                }

                @Override 
       public void writeFieldName(SerializableString serializableString) throws IOException {
           writeFieldName(serializableString.getValue());
                }

                @Override 
       public void writeString(SerializableString serializableString) throws IOException {
           writeString(serializableString.getValue());
                }

                @Override 
       public void writeRawValue(String str) throws IOException {
           _verifyValueWrite("write raw value");
           writeRaw(str);
                }

                @Override 
       public void writeRawValue(String str, int i, int i2) throws IOException {
           _verifyValueWrite("write raw value");
           writeRaw(str, i, i2);
                }

                @Override 
       public void writeRawValue(char[] cArr, int i, int i2) throws IOException {
           _verifyValueWrite("write raw value");
           writeRaw(cArr, i, i2);
                }

                @Override 
       public int writeBinary(Base64Variant base64Variant, InputStream inputStream, int i) throws IOException {
           _reportUnsupportedOperation();
           return 0;
                }

                @Override 
       public void writeObject(Object obj) throws IOException {
           if (obj == null) {
               writeNull();
                    } else if (this._objectCodec != null) {
               this._objectCodec.writeValue(this, obj);
                    } else {
               _writeSimpleObject(obj);
                    }
                }

                @Override 
       public void writeTree(TreeNode treeNode) throws IOException {
           if (treeNode == null) {
               writeNull();
                    } else {
               if (this._objectCodec == null) {
                   throw new IllegalStateException("No ObjectCodec defined");
                        }
               this._objectCodec.writeValue(this, treeNode);
                    }
                }

                @Override 
       public void close() throws IOException {
           this._closed = true;
                }

                @Override 
       public boolean isClosed() {
           return this._closed;
                }

                @Override 
       protected void _writeSimpleObject(Object obj) throws IOException {
           super._writeSimpleObject(obj);
                }
            }
