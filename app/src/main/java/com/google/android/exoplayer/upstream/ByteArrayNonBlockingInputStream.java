            package com.google.android.exoplayer.upstream;

            import com.google.android.exoplayer.util.Assertions;
            import java.nio.ByteBuffer;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 29 */    public final class ByteArrayNonBlockingInputStream implements NonBlockingInputStream {
                private final byte[] data;
                private int position;

/* 30 */        public ByteArrayNonBlockingInputStream(byte[] data) {
/* 32 */            this.data = (byte[]) Assertions.checkNotNull(data);
                }

                @Override // com.google.android.exoplayer.upstream.NonBlockingInputStream
/* 36 */        public int skip(int length) {
/* 37 */            int skipLength = getReadLength(length);
/* 38 */            this.position += skipLength;
/* 39 */            return skipLength;
                }

                @Override // com.google.android.exoplayer.upstream.NonBlockingInputStream
/* 43 */        public int read(byte[] buffer, int offset, int length) {
/* 44 */            if (isEndOfStream()) {
/* 45 */                return -1;
                    }
/* 47 */            int readLength = getReadLength(length);
/* 48 */            System.arraycopy(this.data, this.position, buffer, offset, readLength);
/* 49 */            this.position += readLength;
/* 50 */            return readLength;
                }

                @Override // com.google.android.exoplayer.upstream.NonBlockingInputStream
/* 54 */        public int read(ByteBuffer buffer, int length) {
/* 55 */            if (isEndOfStream()) {
/* 56 */                return -1;
                    }
/* 58 */            int readLength = getReadLength(length);
/* 59 */            buffer.put(this.data, this.position, readLength);
/* 60 */            this.position += readLength;
/* 61 */            return readLength;
                }

                @Override // com.google.android.exoplayer.upstream.NonBlockingInputStream
/* 65 */        public long getAvailableByteCount() {
/* 66 */            return this.data.length - this.position;
                }

                @Override // com.google.android.exoplayer.upstream.NonBlockingInputStream
/* 70 */        public boolean isEndOfStream() {
                    return this.position == this.data.length;
                }

                @Override // com.google.android.exoplayer.upstream.NonBlockingInputStream
/* 76 */        public void close() {
                }

/* 79 */        private int getReadLength(int requestedLength) {
/* 80 */            return Math.min(requestedLength, this.data.length - this.position);
                }
            }
