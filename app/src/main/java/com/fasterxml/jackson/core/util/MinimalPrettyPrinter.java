            package com.fasterxml.jackson.core.util;

            import co.vine.android.util.CommonUtil;
            import com.fasterxml.jackson.core.JsonGenerator;
            import com.fasterxml.jackson.core.PrettyPrinter;
            import java.io.IOException;
            import java.io.Serializable;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 43 */    public class MinimalPrettyPrinter implements PrettyPrinter, Serializable {
                public static final String DEFAULT_ROOT_VALUE_SEPARATOR = " ";
                private static final long serialVersionUID = -562765100295218442L;
                protected String _rootValueSeparator;

/* 44 */        public MinimalPrettyPrinter() {
/* 45 */            this(DEFAULT_ROOT_VALUE_SEPARATOR);
                }

/* 47 */        public MinimalPrettyPrinter(String str) {
/* 36 */            this._rootValueSeparator = DEFAULT_ROOT_VALUE_SEPARATOR;
/* 49 */            this._rootValueSeparator = str;
                }

/* 52 */        public void setRootValueSeparator(String str) {
/* 53 */            this._rootValueSeparator = str;
                }

                @Override // com.fasterxml.jackson.core.PrettyPrinter
/* 64 */        public void writeRootValueSeparator(JsonGenerator jsonGenerator) throws IOException {
/* 65 */            if (this._rootValueSeparator != null) {
/* 66 */                jsonGenerator.writeRaw(this._rootValueSeparator);
                    }
                }

                @Override // com.fasterxml.jackson.core.PrettyPrinter
/* 73 */        public void writeStartObject(JsonGenerator jsonGenerator) throws IOException {
/* 74 */            jsonGenerator.writeRaw('{');
                }

                @Override // com.fasterxml.jackson.core.PrettyPrinter
/* 81 */        public void beforeObjectEntries(JsonGenerator jsonGenerator) throws IOException {
                }

                @Override // com.fasterxml.jackson.core.PrettyPrinter
/* 94 */        public void writeObjectFieldValueSeparator(JsonGenerator jsonGenerator) throws IOException {
/* 95 */            jsonGenerator.writeRaw(CommonUtil.MARKER_COLON);
                }

                @Override // com.fasterxml.jackson.core.PrettyPrinter
/* 108 */       public void writeObjectEntrySeparator(JsonGenerator jsonGenerator) throws IOException {
/* 109 */           jsonGenerator.writeRaw(',');
                }

                @Override // com.fasterxml.jackson.core.PrettyPrinter
/* 115 */       public void writeEndObject(JsonGenerator jsonGenerator, int i) throws IOException {
/* 116 */           jsonGenerator.writeRaw('}');
                }

                @Override // com.fasterxml.jackson.core.PrettyPrinter
/* 122 */       public void writeStartArray(JsonGenerator jsonGenerator) throws IOException {
/* 123 */           jsonGenerator.writeRaw('[');
                }

                @Override // com.fasterxml.jackson.core.PrettyPrinter
/* 130 */       public void beforeArrayValues(JsonGenerator jsonGenerator) throws IOException {
                }

                @Override // com.fasterxml.jackson.core.PrettyPrinter
/* 143 */       public void writeArrayValueSeparator(JsonGenerator jsonGenerator) throws IOException {
/* 144 */           jsonGenerator.writeRaw(',');
                }

                @Override // com.fasterxml.jackson.core.PrettyPrinter
/* 150 */       public void writeEndArray(JsonGenerator jsonGenerator, int i) throws IOException {
/* 151 */           jsonGenerator.writeRaw(']');
                }
            }
