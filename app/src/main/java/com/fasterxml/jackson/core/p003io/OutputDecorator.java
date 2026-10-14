            package com.fasterxml.jackson.core.p003io;

            import java.io.IOException;
            import java.io.OutputStream;
            import java.io.Serializable;
            import java.io.Writer;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public abstract class OutputDecorator implements Serializable {
                private static final long serialVersionUID = 1;

                public abstract OutputStream decorate(IOContext iOContext, OutputStream outputStream) throws IOException;

                public abstract Writer decorate(IOContext iOContext, Writer writer) throws IOException;
            }
