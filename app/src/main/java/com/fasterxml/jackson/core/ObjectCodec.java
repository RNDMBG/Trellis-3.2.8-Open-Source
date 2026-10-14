            package com.fasterxml.jackson.core;

            import com.fasterxml.jackson.core.type.ResolvedType;
            import com.fasterxml.jackson.core.type.TypeReference;
            import java.io.IOException;
            import java.util.Iterator;
            
            
    public abstract class ObjectCodec extends TreeCodec implements Versioned {
                @Override 
                public abstract TreeNode createArrayNode();

                @Override 
                public abstract TreeNode createObjectNode();

                @Deprecated
                public abstract JsonFactory getJsonFactory();

                @Override 
                public abstract <T extends TreeNode> T readTree(JsonParser jsonParser) throws IOException;

                public abstract <T> T readValue(JsonParser jsonParser, ResolvedType resolvedType) throws IOException;

                public abstract <T> T readValue(JsonParser jsonParser, TypeReference<?> typeReference) throws IOException;

                public abstract <T> T readValue(JsonParser jsonParser, Class<T> cls) throws IOException;

                public abstract <T> Iterator<T> readValues(JsonParser jsonParser, ResolvedType resolvedType) throws IOException;

                public abstract <T> Iterator<T> readValues(JsonParser jsonParser, TypeReference<?> typeReference) throws IOException;

                public abstract <T> Iterator<T> readValues(JsonParser jsonParser, Class<T> cls) throws IOException;

                @Override 
                public abstract JsonParser treeAsTokens(TreeNode treeNode);

                public abstract <T> T treeToValue(TreeNode treeNode, Class<T> cls) throws JsonProcessingException;

                @Override 
                public abstract void writeTree(JsonGenerator jsonGenerator, TreeNode treeNode) throws IOException;

                public abstract void writeValue(JsonGenerator jsonGenerator, Object obj) throws IOException;

        protected ObjectCodec() {
                }

                @Override 
        public Version version() {
            return Version.unknownVersion();
                }

       public JsonFactory getFactory() {
           return getJsonFactory();
                }
            }
