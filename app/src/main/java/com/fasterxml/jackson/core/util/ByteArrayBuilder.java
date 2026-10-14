            package com.fasterxml.jackson.core.util;

            import java.io.OutputStream;
            import java.util.LinkedList;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 57 */    public final class ByteArrayBuilder extends OutputStream {
                static final int DEFAULT_BLOCK_ARRAY_SIZE = 40;
                private static final int INITIAL_BLOCK_SIZE = 500;
                private static final int MAX_BLOCK_SIZE = 262144;
                private static final byte[] NO_BYTES = new byte[0];
                private final BufferRecycler _bufferRecycler;
                private byte[] _currBlock;
                private int _currBlockPtr;
                private final LinkedList<byte[]> _pastBlocks;
                private int _pastLen;

/* 58 */        public ByteArrayBuilder() {
/* 59 */            this((BufferRecycler) null);
                }

/* 60 */        public ByteArrayBuilder(BufferRecycler bufferRecycler) {
/* 61 */            this(bufferRecycler, 500);
                }

/* 62 */        public ByteArrayBuilder(int i) {
/* 63 */            this(null, i);
                }

/* 65 */        public ByteArrayBuilder(BufferRecycler bufferRecycler, int i) {
/* 48 */            this._pastBlocks = new LinkedList<>();
/* 67 */            this._bufferRecycler = bufferRecycler;
/* 68 */            if (bufferRecycler == null) {
/* 69 */                this._currBlock = new byte[i];
                    } else {
/* 71 */                this._currBlock = bufferRecycler.allocByteBuffer(BufferRecycler.ByteBufferType.WRITE_CONCAT_BUFFER);
                    }
                }

/* 76 */        public void reset() {
/* 77 */            this._pastLen = 0;
/* 78 */            this._currBlockPtr = 0;
/* 80 */            if (!this._pastBlocks.isEmpty()) {
/* 81 */                this._pastBlocks.clear();
                    }
                }

/* 90 */        public void release() {
/* 91 */            reset();
/* 92 */            if (this._bufferRecycler != null && this._currBlock != null) {
/* 93 */                this._bufferRecycler.releaseByteBuffer(BufferRecycler.ByteBufferType.WRITE_CONCAT_BUFFER, this._currBlock);
/* 94 */                this._currBlock = null;
                    }
                }

/* 99 */        public void append(int i) {
/* 100 */           if (this._currBlockPtr >= this._currBlock.length) {
/* 101 */               _allocMore();
                    }
/* 103 */           byte[] bArr = this._currBlock;
                    int i2 = this._currBlockPtr;
                    this._currBlockPtr = i2 + 1;
                    bArr[i2] = (byte) i;
                }

/* 107 */       public void appendTwoBytes(int i) {
/* 108 */           if (this._currBlockPtr + 1 < this._currBlock.length) {
/* 109 */               byte[] bArr = this._currBlock;
                        int i2 = this._currBlockPtr;
                        this._currBlockPtr = i2 + 1;
                        bArr[i2] = (byte) (i >> 8);
/* 110 */               byte[] bArr2 = this._currBlock;
                        int i3 = this._currBlockPtr;
                        this._currBlockPtr = i3 + 1;
                        bArr2[i3] = (byte) i;
/* 115 */               return;
                    }
/* 112 */           append(i >> 8);
/* 113 */           append(i);
                }

/* 118 */       public void appendThreeBytes(int i) {
/* 119 */           if (this._currBlockPtr + 2 < this._currBlock.length) {
/* 120 */               byte[] bArr = this._currBlock;
                        int i2 = this._currBlockPtr;
                        this._currBlockPtr = i2 + 1;
                        bArr[i2] = (byte) (i >> 16);
/* 121 */               byte[] bArr2 = this._currBlock;
                        int i3 = this._currBlockPtr;
                        this._currBlockPtr = i3 + 1;
                        bArr2[i3] = (byte) (i >> 8);
/* 122 */               byte[] bArr3 = this._currBlock;
                        int i4 = this._currBlockPtr;
                        this._currBlockPtr = i4 + 1;
                        bArr3[i4] = (byte) i;
/* 128 */               return;
                    }
/* 124 */           append(i >> 16);
/* 125 */           append(i >> 8);
/* 126 */           append(i);
                }

/* 135 */       public byte[] toByteArray() {
/* 136 */           int i = this._pastLen + this._currBlockPtr;
/* 138 */           if (i == 0) {
/* 139 */               return NO_BYTES;
                    }
/* 142 */           byte[] bArr = new byte[i];
                    int i2 = 0;
                    for (byte[] bArr2 : this._pastBlocks) {
/* 146 */               int length = bArr2.length;
/* 147 */               System.arraycopy(bArr2, 0, bArr, i2, length);
/* 148 */               i2 += length;
                    }
/* 150 */           System.arraycopy(this._currBlock, 0, bArr, i2, this._currBlockPtr);
/* 151 */           int i3 = this._currBlockPtr + i2;
/* 152 */           if (i3 != i) {
/* 153 */               throw new RuntimeException("Internal error: total len assumed to be " + i + ", copied " + i3 + " bytes");
                    }
/* 156 */           if (!this._pastBlocks.isEmpty()) {
/* 157 */               reset();
                    }
                    return bArr;
                }

/* 172 */       public byte[] resetAndGetFirstSegment() {
/* 173 */           reset();
/* 174 */           return this._currBlock;
                }

/* 182 */       public byte[] finishCurrentSegment() {
/* 183 */           _allocMore();
/* 184 */           return this._currBlock;
                }

/* 197 */       public byte[] completeAndCoalesce(int i) {
/* 198 */           this._currBlockPtr = i;
/* 199 */           return toByteArray();
                }

/* 202 */       public byte[] getCurrentSegment() {
/* 203 */           return this._currBlock;
                }

/* 206 */       public void setCurrentSegmentLength(int i) {
/* 207 */           this._currBlockPtr = i;
                }

/* 210 */       public int getCurrentSegmentLength() {
/* 211 */           return this._currBlockPtr;
                }

                @Override // java.io.OutputStream
/* 221 */       public void write(byte[] bArr) {
/* 222 */           write(bArr, 0, bArr.length);
                }

                @Override // java.io.OutputStream
/* 228 */       public void write(byte[] bArr, int i, int i2) {
                    while (true) {
/* 230 */               int iMin = Math.min(this._currBlock.length - this._currBlockPtr, i2);
/* 231 */               if (iMin > 0) {
/* 232 */                   System.arraycopy(bArr, i, this._currBlock, this._currBlockPtr, iMin);
/* 233 */                   i += iMin;
/* 234 */                   this._currBlockPtr += iMin;
/* 235 */                   i2 -= iMin;
                        }
/* 237 */               if (i2 > 0) {
/* 238 */                   _allocMore();
                        } else {
/* 240 */                   return;
                        }
                    }
                }

                @Override // java.io.OutputStream
/* 243 */       public void write(int i) {
/* 244 */           append(i);
                }

                @Override // java.io.OutputStream, java.io.Closeable, java.lang.AutoCloseable
/* 246 */       public void close() {
                }

                @Override // java.io.OutputStream, java.io.Flushable
/* 248 */       public void flush() {
                }

/* 258 */       private void _allocMore() {
/* 259 */           this._pastLen += this._currBlock.length;
/* 267 */           int iMax = Math.max(this._pastLen >> 1, 1000);
/* 269 */           int i = iMax <= 262144 ? iMax : 262144;
/* 272 */           this._pastBlocks.add(this._currBlock);
/* 273 */           this._currBlock = new byte[i];
/* 274 */           this._currBlockPtr = 0;
                }
            }
