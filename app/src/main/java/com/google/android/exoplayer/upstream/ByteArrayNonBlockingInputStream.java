            package com.google.android.exoplayer.upstream;

            import com.google.android.exoplayer.util.Assertions;
            import java.nio.ByteBuffer;
            
            
    public final class ByteArrayNonBlockingInputStream implements NonBlockingInputStream {
                private final byte[] data;
                private int position;

        public ByteArrayNonBlockingInputStream(byte[] data) {
            this.data = (byte[]) Assertions.checkNotNull(data);
                }

                @Override 
        public int skip(int length) {
            int skipLength = getReadLength(length);
            this.position += skipLength;
            return skipLength;
                }

                @Override 
        public int read(byte[] buffer, int offset, int length) {
            if (isEndOfStream()) {
                return -1;
                    }
            int readLength = getReadLength(length);
            System.arraycopy(this.data, this.position, buffer, offset, readLength);
            this.position += readLength;
            return readLength;
                }

                @Override 
        public int read(ByteBuffer buffer, int length) {
            if (isEndOfStream()) {
                return -1;
                    }
            int readLength = getReadLength(length);
            buffer.put(this.data, this.position, readLength);
            this.position += readLength;
            return readLength;
                }

                @Override 
        public long getAvailableByteCount() {
            return this.data.length - this.position;
                }

                @Override 
        public boolean isEndOfStream() {
                    return this.position == this.data.length;
                }

                @Override 
        public void close() {
                }

        private int getReadLength(int requestedLength) {
            return Math.min(requestedLength, this.data.length - this.position);
                }
            }
