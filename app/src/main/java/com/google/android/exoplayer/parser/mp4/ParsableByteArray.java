            package com.google.android.exoplayer.parser.mp4;

            import com.flurry.android.Constants;
            import java.nio.ByteBuffer;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 28 */    final class ParsableByteArray {
                public byte[] data;
                private int position;

/* 29 */        public ParsableByteArray(int length) {
/* 31 */            this.data = new byte[length];
                }

/* 34 */        public int length() {
/* 35 */            return this.data.length;
                }

/* 38 */        public int getPosition() {
/* 39 */            return this.position;
                }

/* 42 */        public void setPosition(int position) {
/* 43 */            this.position = position;
                }

/* 46 */        public void skip(int bytes) {
/* 47 */            this.position += bytes;
                }

/* 50 */        public void rewind(int bytes) {
/* 51 */            this.position -= bytes;
                }

/* 54 */        public void readBytes(byte[] buffer, int offset, int length) {
/* 55 */            System.arraycopy(this.data, this.position, buffer, offset, length);
/* 56 */            this.position += length;
                }

/* 59 */        public void readBytes(ByteBuffer buffer, int length) {
/* 60 */            buffer.put(this.data, this.position, length);
/* 61 */            this.position += length;
                }

/* 64 */        public int readUnsignedByte() {
/* 65 */            int result = shiftIntoInt(this.data, this.position, 1);
/* 66 */            this.position++;
/* 67 */            return result;
                }

/* 70 */        public int readUnsignedShort() {
/* 71 */            int result = shiftIntoInt(this.data, this.position, 2);
/* 72 */            this.position += 2;
/* 73 */            return result;
                }

/* 76 */        public long readUnsignedInt() {
/* 77 */            long result = shiftIntoLong(this.data, this.position, 4);
/* 78 */            this.position += 4;
/* 79 */            return result;
                }

/* 82 */        public int readInt() {
/* 83 */            int result = shiftIntoInt(this.data, this.position, 4);
/* 84 */            this.position += 4;
/* 85 */            return result;
                }

/* 88 */        public long readLong() {
/* 89 */            long result = shiftIntoLong(this.data, this.position, 8);
/* 90 */            this.position += 8;
/* 91 */            return result;
                }

/* 97 */        public int readUnsignedFixedPoint1616() {
/* 98 */            int result = shiftIntoInt(this.data, this.position, 2);
/* 99 */            this.position += 4;
/* 100 */           return result;
                }

/* 109 */       public int readUnsignedIntToInt() {
/* 110 */           int result = shiftIntoInt(this.data, this.position, 4);
/* 111 */           this.position += 4;
/* 112 */           if (result < 0) {
/* 113 */               throw new IllegalArgumentException("Top bit not zero: " + result);
                    }
/* 115 */           return result;
                }

/* 124 */       public long readUnsignedLongToLong() {
/* 125 */           long result = shiftIntoLong(this.data, this.position, 8);
/* 126 */           this.position += 8;
                    if (result < 0) {
/* 128 */               throw new IllegalArgumentException("Top bit not zero: " + result);
                    }
/* 130 */           return result;
                }

/* 133 */       private static int shiftIntoInt(byte[] bytes, int offset, int length) {
/* 134 */           int result = bytes[offset] & Constants.UNKNOWN;
                    for (int i = offset + 1; i < offset + length; i++) {
/* 137 */               result = (result << 8) | (bytes[i] & Constants.UNKNOWN);
                    }
/* 139 */           return result;
                }

/* 142 */       private static long shiftIntoLong(byte[] bytes, int offset, int length) {
/* 143 */           long result = bytes[offset] & Constants.UNKNOWN;
                    for (int i = offset + 1; i < offset + length; i++) {
/* 146 */               result = (result << 8) | ((long) (bytes[i] & Constants.UNKNOWN));
                    }
/* 148 */           return result;
                }
            }
