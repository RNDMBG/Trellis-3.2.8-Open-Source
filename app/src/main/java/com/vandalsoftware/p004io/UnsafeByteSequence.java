            package com.vandalsoftware.p004io;

            import java.io.UnsupportedEncodingException;
            
            
    public class UnsafeByteSequence {
                private byte[] bytes;
                private int count;

        public UnsafeByteSequence(int initialCapacity) {
            this.bytes = new byte[initialCapacity];
                }

        public int size() {
            return this.count;
                }

        public void rewind() {
            this.count = 0;
                }

        public void write(byte[] buffer, int offset, int length) {
            if (this.count + length >= this.bytes.length) {
                byte[] newBytes = new byte[(this.count + length) * 2];
                System.arraycopy(this.bytes, 0, newBytes, 0, this.count);
                this.bytes = newBytes;
                    }
            System.arraycopy(buffer, offset, this.bytes, this.count, length);
            this.count += length;
                }

        public void write(int b) {
            if (this.count == this.bytes.length) {
                byte[] newBytes = new byte[this.count * 2];
                System.arraycopy(this.bytes, 0, newBytes, 0, this.count);
                this.bytes = newBytes;
                    }
            byte[] bArr = this.bytes;
                    int i = this.count;
                    this.count = i + 1;
                    bArr[i] = (byte) b;
                }

        public byte[] toByteArray() {
            if (this.count == this.bytes.length) {
                return this.bytes;
                    }
            byte[] result = new byte[this.count];
            System.arraycopy(this.bytes, 0, result, 0, this.count);
            return result;
                }

        public String toString(String encoding) {
                    try {
                return new String(this.bytes, 0, this.count, encoding);
                    } catch (UnsupportedEncodingException e) {
                return new String(this.bytes, 0, this.count);
                    }
                }
            }
