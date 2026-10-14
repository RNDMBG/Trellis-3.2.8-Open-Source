            package com.google.android.exoplayer.upstream;

            import java.nio.ByteBuffer;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public interface NonBlockingInputStream {
                void close();

                long getAvailableByteCount();

                boolean isEndOfStream();

                int read(ByteBuffer byteBuffer, int i);

                int read(byte[] bArr, int i, int i2);

                int skip(int i);
            }
