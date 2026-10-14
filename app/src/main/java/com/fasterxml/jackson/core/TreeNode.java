            package com.fasterxml.jackson.core;

            import java.util.Iterator;
            
            
            public interface TreeNode {
                JsonToken asToken();

                
                TreeNode m525at(JsonPointer jsonPointer);

                
                TreeNode m526at(String str) throws IllegalArgumentException;

                Iterator<String> fieldNames();

                TreeNode get(int i);

                TreeNode get(String str);

                boolean isArray();

                boolean isContainerNode();

                boolean isMissingNode();

                boolean isObject();

                boolean isValueNode();

                JsonParser.NumberType numberType();

                TreeNode path(int i);

                TreeNode path(String str);

                int size();

                JsonParser traverse();

                JsonParser traverse(ObjectCodec objectCodec);
            }
