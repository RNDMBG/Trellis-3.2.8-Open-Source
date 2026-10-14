            package com.fasterxml.jackson.core.util;

            import co.vine.android.util.CommonUtil;
            import com.fasterxml.jackson.core.JsonGenerator;
            import com.fasterxml.jackson.core.PrettyPrinter;
            import java.io.IOException;
            import java.io.Serializable;
            
            
    public class MinimalPrettyPrinter implements PrettyPrinter, Serializable {
                public static final String DEFAULT_ROOT_VALUE_SEPARATOR = " ";
                private static final long serialVersionUID = -562765100295218442L;
                protected String _rootValueSeparator;

        public MinimalPrettyPrinter() {
            this(DEFAULT_ROOT_VALUE_SEPARATOR);
                }

        public MinimalPrettyPrinter(String str) {
            this._rootValueSeparator = DEFAULT_ROOT_VALUE_SEPARATOR;
            this._rootValueSeparator = str;
                }

        public void setRootValueSeparator(String str) {
            this._rootValueSeparator = str;
                }

                @Override 
        public void writeRootValueSeparator(JsonGenerator jsonGenerator) throws IOException {
            if (this._rootValueSeparator != null) {
                jsonGenerator.writeRaw(this._rootValueSeparator);
                    }
                }

                @Override 
        public void writeStartObject(JsonGenerator jsonGenerator) throws IOException {
            jsonGenerator.writeRaw('{');
                }

                @Override 
        public void beforeObjectEntries(JsonGenerator jsonGenerator) throws IOException {
                }

                @Override 
        public void writeObjectFieldValueSeparator(JsonGenerator jsonGenerator) throws IOException {
            jsonGenerator.writeRaw(CommonUtil.MARKER_COLON);
                }

                @Override 
       public void writeObjectEntrySeparator(JsonGenerator jsonGenerator) throws IOException {
           jsonGenerator.writeRaw(',');
                }

                @Override 
       public void writeEndObject(JsonGenerator jsonGenerator, int i) throws IOException {
           jsonGenerator.writeRaw('}');
                }

                @Override 
       public void writeStartArray(JsonGenerator jsonGenerator) throws IOException {
           jsonGenerator.writeRaw('[');
                }

                @Override 
       public void beforeArrayValues(JsonGenerator jsonGenerator) throws IOException {
                }

                @Override 
       public void writeArrayValueSeparator(JsonGenerator jsonGenerator) throws IOException {
           jsonGenerator.writeRaw(',');
                }

                @Override 
       public void writeEndArray(JsonGenerator jsonGenerator, int i) throws IOException {
           jsonGenerator.writeRaw(']');
                }
            }
