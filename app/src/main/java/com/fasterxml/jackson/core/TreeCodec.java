            package com.fasterxml.jackson.core;

            import java.io.IOException;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public abstract class TreeCodec {
                public abstract TreeNode createArrayNode();

                public abstract TreeNode createObjectNode();

                public abstract <T extends TreeNode> T readTree(JsonParser jsonParser) throws IOException;

                public abstract JsonParser treeAsTokens(TreeNode treeNode);

                public abstract void writeTree(JsonGenerator jsonGenerator, TreeNode treeNode) throws IOException;
            }
