            package com.fasterxml.jackson.core;

            import java.io.IOException;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public interface PrettyPrinter {
                void beforeArrayValues(JsonGenerator jsonGenerator) throws IOException;

                void beforeObjectEntries(JsonGenerator jsonGenerator) throws IOException;

                void writeArrayValueSeparator(JsonGenerator jsonGenerator) throws IOException;

                void writeEndArray(JsonGenerator jsonGenerator, int i) throws IOException;

                void writeEndObject(JsonGenerator jsonGenerator, int i) throws IOException;

                void writeObjectEntrySeparator(JsonGenerator jsonGenerator) throws IOException;

                void writeObjectFieldValueSeparator(JsonGenerator jsonGenerator) throws IOException;

                void writeRootValueSeparator(JsonGenerator jsonGenerator) throws IOException;

                void writeStartArray(JsonGenerator jsonGenerator) throws IOException;

                void writeStartObject(JsonGenerator jsonGenerator) throws IOException;
            }
