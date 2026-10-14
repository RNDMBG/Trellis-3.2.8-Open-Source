            package com.vandalsoftware.p004io;

            import java.io.UnsupportedEncodingException;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 31 */    public class UnsafeByteSequence {
                private byte[] bytes;
                private int count;

/* 32 */        public UnsafeByteSequence(int initialCapacity) {
/* 34 */            this.bytes = new byte[initialCapacity];
                }

/* 37 */        public int size() {
/* 38 */            return this.count;
                }

/* 45 */        public void rewind() {
/* 46 */            this.count = 0;
                }

/* 49 */        public void write(byte[] buffer, int offset, int length) {
/* 50 */            if (this.count + length >= this.bytes.length) {
/* 51 */                byte[] newBytes = new byte[(this.count + length) * 2];
/* 52 */                System.arraycopy(this.bytes, 0, newBytes, 0, this.count);
/* 53 */                this.bytes = newBytes;
                    }
/* 55 */            System.arraycopy(buffer, offset, this.bytes, this.count, length);
/* 56 */            this.count += length;
                }

/* 59 */        public void write(int b) {
/* 60 */            if (this.count == this.bytes.length) {
/* 61 */                byte[] newBytes = new byte[this.count * 2];
/* 62 */                System.arraycopy(this.bytes, 0, newBytes, 0, this.count);
/* 63 */                this.bytes = newBytes;
                    }
/* 65 */            byte[] bArr = this.bytes;
                    int i = this.count;
                    this.count = i + 1;
                    bArr[i] = (byte) b;
                }

/* 68 */        public byte[] toByteArray() {
/* 69 */            if (this.count == this.bytes.length) {
/* 70 */                return this.bytes;
                    }
/* 72 */            byte[] result = new byte[this.count];
/* 73 */            System.arraycopy(this.bytes, 0, result, 0, this.count);
/* 74 */            return result;
                }

/* 78 */        public String toString(String encoding) {
                    try {
/* 79 */                return new String(this.bytes, 0, this.count, encoding);
                    } catch (UnsupportedEncodingException e) {
/* 81 */                return new String(this.bytes, 0, this.count);
                    }
                }
            }
