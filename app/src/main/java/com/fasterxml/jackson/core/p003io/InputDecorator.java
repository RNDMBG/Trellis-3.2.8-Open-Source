            package com.fasterxml.jackson.core.p003io;

            import java.io.IOException;
            import java.io.InputStream;
            import java.io.Reader;
            import java.io.Serializable;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public abstract class InputDecorator implements Serializable {
                private static final long serialVersionUID = 1;

                public abstract InputStream decorate(IOContext iOContext, InputStream inputStream) throws IOException;

                public abstract InputStream decorate(IOContext iOContext, byte[] bArr, int i, int i2) throws IOException;

                public abstract Reader decorate(IOContext iOContext, Reader reader) throws IOException;
            }
