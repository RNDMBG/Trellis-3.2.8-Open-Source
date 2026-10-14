            package com.google.android.exoplayer.parser.webm;

            import com.google.android.exoplayer.upstream.NonBlockingInputStream;
            import java.nio.ByteBuffer;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            interface EbmlReader {
                public static final int READ_RESULT_CONTINUE = 0;
                public static final int READ_RESULT_END_OF_STREAM = 2;
                public static final int READ_RESULT_NEED_MORE_DATA = 1;
                public static final int TYPE_BINARY = 4;
                public static final int TYPE_FLOAT = 5;
                public static final int TYPE_MASTER = 1;
                public static final int TYPE_STRING = 3;
                public static final int TYPE_UNKNOWN = 0;
                public static final int TYPE_UNSIGNED_INT = 2;

                long getBytesRead();

                int read(NonBlockingInputStream nonBlockingInputStream);

                void readBytes(NonBlockingInputStream nonBlockingInputStream, ByteBuffer byteBuffer, int i);

                void readBytes(NonBlockingInputStream nonBlockingInputStream, byte[] bArr, int i);

                long readVarint(NonBlockingInputStream nonBlockingInputStream);

                void reset();

                void setEventHandler(EbmlEventHandler ebmlEventHandler);

                void skipBytes(NonBlockingInputStream nonBlockingInputStream, int i);
            }
