            package com.fasterxml.jackson.core.util;

            import java.io.OutputStream;
            import java.util.LinkedList;
            
            
    public final class ByteArrayBuilder extends OutputStream {
                static final int DEFAULT_BLOCK_ARRAY_SIZE = 40;
                private static final int INITIAL_BLOCK_SIZE = 500;
                private static final int MAX_BLOCK_SIZE = 262144;
                private static final byte[] NO_BYTES = new byte[0];
                private final BufferRecycler _bufferRecycler;
                private byte[] _currBlock;
                private int _currBlockPtr;
                private final LinkedList<byte[]> _pastBlocks;
                private int _pastLen;

        public ByteArrayBuilder() {
            this((BufferRecycler) null);
                }

        public ByteArrayBuilder(BufferRecycler bufferRecycler) {
            this(bufferRecycler, 500);
                }

        public ByteArrayBuilder(int i) {
            this(null, i);
                }

        public ByteArrayBuilder(BufferRecycler bufferRecycler, int i) {
            this._pastBlocks = new LinkedList<>();
            this._bufferRecycler = bufferRecycler;
            if (bufferRecycler == null) {
                this._currBlock = new byte[i];
                    } else {
                this._currBlock = bufferRecycler.allocByteBuffer(BufferRecycler.ByteBufferType.WRITE_CONCAT_BUFFER);
                    }
                }

        public void reset() {
            this._pastLen = 0;
            this._currBlockPtr = 0;
            if (!this._pastBlocks.isEmpty()) {
                this._pastBlocks.clear();
                    }
                }

        public void release() {
            reset();
            if (this._bufferRecycler != null && this._currBlock != null) {
                this._bufferRecycler.releaseByteBuffer(BufferRecycler.ByteBufferType.WRITE_CONCAT_BUFFER, this._currBlock);
                this._currBlock = null;
                    }
                }

        public void append(int i) {
           if (this._currBlockPtr >= this._currBlock.length) {
               _allocMore();
                    }
           byte[] bArr = this._currBlock;
                    int i2 = this._currBlockPtr;
                    this._currBlockPtr = i2 + 1;
                    bArr[i2] = (byte) i;
                }

       public void appendTwoBytes(int i) {
           if (this._currBlockPtr + 1 < this._currBlock.length) {
               byte[] bArr = this._currBlock;
                        int i2 = this._currBlockPtr;
                        this._currBlockPtr = i2 + 1;
                        bArr[i2] = (byte) (i >> 8);
               byte[] bArr2 = this._currBlock;
                        int i3 = this._currBlockPtr;
                        this._currBlockPtr = i3 + 1;
                        bArr2[i3] = (byte) i;
               return;
                    }
           append(i >> 8);
           append(i);
                }

       public void appendThreeBytes(int i) {
           if (this._currBlockPtr + 2 < this._currBlock.length) {
               byte[] bArr = this._currBlock;
                        int i2 = this._currBlockPtr;
                        this._currBlockPtr = i2 + 1;
                        bArr[i2] = (byte) (i >> 16);
               byte[] bArr2 = this._currBlock;
                        int i3 = this._currBlockPtr;
                        this._currBlockPtr = i3 + 1;
                        bArr2[i3] = (byte) (i >> 8);
               byte[] bArr3 = this._currBlock;
                        int i4 = this._currBlockPtr;
                        this._currBlockPtr = i4 + 1;
                        bArr3[i4] = (byte) i;
               return;
                    }
           append(i >> 16);
           append(i >> 8);
           append(i);
                }

       public byte[] toByteArray() {
           int i = this._pastLen + this._currBlockPtr;
           if (i == 0) {
               return NO_BYTES;
                    }
           byte[] bArr = new byte[i];
                    int i2 = 0;
                    for (byte[] bArr2 : this._pastBlocks) {
               int length = bArr2.length;
               System.arraycopy(bArr2, 0, bArr, i2, length);
               i2 += length;
                    }
           System.arraycopy(this._currBlock, 0, bArr, i2, this._currBlockPtr);
           int i3 = this._currBlockPtr + i2;
           if (i3 != i) {
               throw new RuntimeException("Internal error: total len assumed to be " + i + ", copied " + i3 + " bytes");
                    }
           if (!this._pastBlocks.isEmpty()) {
               reset();
                    }
                    return bArr;
                }

       public byte[] resetAndGetFirstSegment() {
           reset();
           return this._currBlock;
                }

       public byte[] finishCurrentSegment() {
           _allocMore();
           return this._currBlock;
                }

       public byte[] completeAndCoalesce(int i) {
           this._currBlockPtr = i;
           return toByteArray();
                }

       public byte[] getCurrentSegment() {
           return this._currBlock;
                }

       public void setCurrentSegmentLength(int i) {
           this._currBlockPtr = i;
                }

       public int getCurrentSegmentLength() {
           return this._currBlockPtr;
                }

                @Override 
       public void write(byte[] bArr) {
           write(bArr, 0, bArr.length);
                }

                @Override 
       public void write(byte[] bArr, int i, int i2) {
                    while (true) {
               int iMin = Math.min(this._currBlock.length - this._currBlockPtr, i2);
               if (iMin > 0) {
                   System.arraycopy(bArr, i, this._currBlock, this._currBlockPtr, iMin);
                   i += iMin;
                   this._currBlockPtr += iMin;
                   i2 -= iMin;
                        }
               if (i2 > 0) {
                   _allocMore();
                        } else {
                   return;
                        }
                    }
                }

                @Override 
       public void write(int i) {
           append(i);
                }

                @Override 
       public void close() {
                }

                @Override 
       public void flush() {
                }

       private void _allocMore() {
           this._pastLen += this._currBlock.length;
           int iMax = Math.max(this._pastLen >> 1, 1000);
           int i = iMax <= 262144 ? iMax : 262144;
           this._pastBlocks.add(this._currBlock);
           this._currBlock = new byte[i];
           this._currBlockPtr = 0;
                }
            }
