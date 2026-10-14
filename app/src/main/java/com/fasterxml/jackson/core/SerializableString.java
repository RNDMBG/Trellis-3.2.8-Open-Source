            package com.fasterxml.jackson.core;

            import java.io.IOException;
            import java.io.OutputStream;
            import java.nio.ByteBuffer;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public interface SerializableString {
                int appendQuoted(char[] cArr, int i);

                int appendQuotedUTF8(byte[] bArr, int i);

                int appendUnquoted(char[] cArr, int i);

                int appendUnquotedUTF8(byte[] bArr, int i);

                char[] asQuotedChars();

                byte[] asQuotedUTF8();

                byte[] asUnquotedUTF8();

                int charLength();

                String getValue();

                int putQuotedUTF8(ByteBuffer byteBuffer) throws IOException;

                int putUnquotedUTF8(ByteBuffer byteBuffer) throws IOException;

                int writeQuotedUTF8(OutputStream outputStream) throws IOException;

                int writeUnquotedUTF8(OutputStream outputStream) throws IOException;
            }
