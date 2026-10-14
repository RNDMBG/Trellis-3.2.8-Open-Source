            package com.google.android.exoplayer.parser.mp4;

            import com.flurry.android.Constants;
            import java.nio.ByteBuffer;
            
            
    final class ParsableByteArray {
                public byte[] data;
                private int position;

        public ParsableByteArray(int length) {
            this.data = new byte[length];
                }

        public int length() {
            return this.data.length;
                }

        public int getPosition() {
            return this.position;
                }

        public void setPosition(int position) {
            this.position = position;
                }

        public void skip(int bytes) {
            this.position += bytes;
                }

        public void rewind(int bytes) {
            this.position -= bytes;
                }

        public void readBytes(byte[] buffer, int offset, int length) {
            System.arraycopy(this.data, this.position, buffer, offset, length);
            this.position += length;
                }

        public void readBytes(ByteBuffer buffer, int length) {
            buffer.put(this.data, this.position, length);
            this.position += length;
                }

        public int readUnsignedByte() {
            int result = shiftIntoInt(this.data, this.position, 1);
            this.position++;
            return result;
                }

        public int readUnsignedShort() {
            int result = shiftIntoInt(this.data, this.position, 2);
            this.position += 2;
            return result;
                }

        public long readUnsignedInt() {
            long result = shiftIntoLong(this.data, this.position, 4);
            this.position += 4;
            return result;
                }

        public int readInt() {
            int result = shiftIntoInt(this.data, this.position, 4);
            this.position += 4;
            return result;
                }

        public long readLong() {
            long result = shiftIntoLong(this.data, this.position, 8);
            this.position += 8;
            return result;
                }

        public int readUnsignedFixedPoint1616() {
            int result = shiftIntoInt(this.data, this.position, 2);
            this.position += 4;
           return result;
                }

       public int readUnsignedIntToInt() {
           int result = shiftIntoInt(this.data, this.position, 4);
           this.position += 4;
           if (result < 0) {
               throw new IllegalArgumentException("Top bit not zero: " + result);
                    }
           return result;
                }

       public long readUnsignedLongToLong() {
           long result = shiftIntoLong(this.data, this.position, 8);
           this.position += 8;
                    if (result < 0) {
               throw new IllegalArgumentException("Top bit not zero: " + result);
                    }
           return result;
                }

       private static int shiftIntoInt(byte[] bytes, int offset, int length) {
           int result = bytes[offset] & Constants.UNKNOWN;
                    for (int i = offset + 1; i < offset + length; i++) {
               result = (result << 8) | (bytes[i] & Constants.UNKNOWN);
                    }
           return result;
                }

       private static long shiftIntoLong(byte[] bytes, int offset, int length) {
           long result = bytes[offset] & Constants.UNKNOWN;
                    for (int i = offset + 1; i < offset + length; i++) {
               result = (result << 8) | ((long) (bytes[i] & Constants.UNKNOWN));
                    }
           return result;
                }
            }
