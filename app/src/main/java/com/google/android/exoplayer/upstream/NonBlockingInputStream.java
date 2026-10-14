            package com.google.android.exoplayer.upstream;

            import java.nio.ByteBuffer;
            
            
            public interface NonBlockingInputStream {
                void close();

                long getAvailableByteCount();

                boolean isEndOfStream();

                int read(ByteBuffer byteBuffer, int i);

                int read(byte[] bArr, int i, int i2);

                int skip(int i);
            }
