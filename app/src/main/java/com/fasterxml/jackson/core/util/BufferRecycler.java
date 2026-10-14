            package com.fasterxml.jackson.core.util;
            
            
    public class BufferRecycler {
                public static final int DEFAULT_WRITE_CONCAT_BUFFER_LEN = 2000;
                protected final byte[][] _byteBuffers = new byte[ByteBufferType.values().length][];
                protected final char[][] _charBuffers = new char[CharBufferType.values().length][];

        public enum ByteBufferType {
                    READ_IO_BUFFER(4000),
                    WRITE_ENCODING_BUFFER(4000),
                    WRITE_CONCAT_BUFFER(2000),
                    BASE64_CODEC_BUFFER(2000);

                    protected final int size;

            ByteBufferType(int i) {
                        this.size = i;
                    }
                }

        public enum CharBufferType {
                    TOKEN_BUFFER(2000),
                    CONCAT_BUFFER(2000),
                    TEXT_BUFFER(200),
                    NAME_COPY_BUFFER(200);

                    protected final int size;

            CharBufferType(int i) {
                        this.size = i;
                    }
                }

        public final byte[] allocByteBuffer(ByteBufferType byteBufferType) {
            return allocByteBuffer(byteBufferType, 0);
                }

        public final byte[] allocByteBuffer(ByteBufferType byteBufferType, int i) {
            int iOrdinal = byteBufferType.ordinal();
            int i2 = byteBufferType.size;
            if (i < i2) {
                        i = i2;
                    }
            byte[] bArr = this._byteBuffers[iOrdinal];
            if (bArr == null || bArr.length < i) {
                return balloc(i);
                    }
            this._byteBuffers[iOrdinal] = null;
            return bArr;
                }

        public final void releaseByteBuffer(ByteBufferType byteBufferType, byte[] bArr) {
            this._byteBuffers[byteBufferType.ordinal()] = bArr;
                }

        public final char[] allocCharBuffer(CharBufferType charBufferType) {
            return allocCharBuffer(charBufferType, 0);
                }

        public final char[] allocCharBuffer(CharBufferType charBufferType, int i) {
            if (charBufferType.size > i) {
                i = charBufferType.size;
                    }
            int iOrdinal = charBufferType.ordinal();
            char[] cArr = this._charBuffers[iOrdinal];
            if (cArr == null || cArr.length < i) {
                return calloc(i);
                    }
           this._charBuffers[iOrdinal] = null;
           return cArr;
                }

       public final void releaseCharBuffer(CharBufferType charBufferType, char[] cArr) {
           this._charBuffers[charBufferType.ordinal()] = cArr;
                }

       private byte[] balloc(int i) {
           return new byte[i];
                }

       private char[] calloc(int i) {
           return new char[i];
                }
            }
