            package com.fasterxml.jackson.core.util;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 37 */    public class BufferRecycler {
                public static final int DEFAULT_WRITE_CONCAT_BUFFER_LEN = 2000;
                protected final byte[][] _byteBuffers = new byte[ByteBufferType.values().length][];
                protected final char[][] _charBuffers = new char[CharBufferType.values().length][];

/* 38 */        public enum ByteBufferType {
                    READ_IO_BUFFER(4000),
                    WRITE_ENCODING_BUFFER(4000),
                    WRITE_CONCAT_BUFFER(2000),
                    BASE64_CODEC_BUFFER(2000);

                    protected final int size;

/* 39 */            ByteBufferType(int i) {
                        this.size = i;
                    }
                }

/* 50 */        public enum CharBufferType {
                    TOKEN_BUFFER(2000),
                    CONCAT_BUFFER(2000),
                    TEXT_BUFFER(200),
                    NAME_COPY_BUFFER(200);

                    protected final int size;

/* 51 */            CharBufferType(int i) {
                        this.size = i;
                    }
                }

/* 60 */        public final byte[] allocByteBuffer(ByteBufferType byteBufferType) {
/* 61 */            return allocByteBuffer(byteBufferType, 0);
                }

/* 65 */        public final byte[] allocByteBuffer(ByteBufferType byteBufferType, int i) {
/* 66 */            int iOrdinal = byteBufferType.ordinal();
/* 67 */            int i2 = byteBufferType.size;
/* 68 */            if (i < i2) {
                        i = i2;
                    }
/* 71 */            byte[] bArr = this._byteBuffers[iOrdinal];
/* 72 */            if (bArr == null || bArr.length < i) {
/* 73 */                return balloc(i);
                    }
/* 75 */            this._byteBuffers[iOrdinal] = null;
/* 77 */            return bArr;
                }

/* 81 */        public final void releaseByteBuffer(ByteBufferType byteBufferType, byte[] bArr) {
/* 82 */            this._byteBuffers[byteBufferType.ordinal()] = bArr;
                }

/* 86 */        public final char[] allocCharBuffer(CharBufferType charBufferType) {
/* 87 */            return allocCharBuffer(charBufferType, 0);
                }

/* 91 */        public final char[] allocCharBuffer(CharBufferType charBufferType, int i) {
/* 92 */            if (charBufferType.size > i) {
/* 93 */                i = charBufferType.size;
                    }
/* 95 */            int iOrdinal = charBufferType.ordinal();
/* 96 */            char[] cArr = this._charBuffers[iOrdinal];
/* 97 */            if (cArr == null || cArr.length < i) {
/* 98 */                return calloc(i);
                    }
/* 100 */           this._charBuffers[iOrdinal] = null;
/* 102 */           return cArr;
                }

/* 106 */       public final void releaseCharBuffer(CharBufferType charBufferType, char[] cArr) {
/* 107 */           this._charBuffers[charBufferType.ordinal()] = cArr;
                }

/* 117 */       private byte[] balloc(int i) {
/* 118 */           return new byte[i];
                }

/* 122 */       private char[] calloc(int i) {
/* 123 */           return new char[i];
                }
            }
