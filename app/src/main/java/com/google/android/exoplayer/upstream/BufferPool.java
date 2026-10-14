            package com.google.android.exoplayer.upstream;

            import com.google.android.exoplayer.util.Assertions;
            import java.util.Arrays;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 45 */    public final class BufferPool implements Allocator {
                private static final int INITIAL_RECYCLED_BUFFERS_CAPACITY = 100;
                private int allocatedBufferCount;
                public final int bufferLength;
                private int recycledBufferCount;
                private byte[][] recycledBuffers;

/* 46 */        public BufferPool(int bufferLength) {
/* 48 */            Assertions.checkArgument(bufferLength > 0);
/* 49 */            this.bufferLength = bufferLength;
/* 50 */            this.recycledBuffers = new byte[100][];
                }

                @Override // com.google.android.exoplayer.upstream.Allocator
/* 54 */        public synchronized int getAllocatedSize() {
                    try {
                    } catch (Throwable th) {
                        throw th;
                    }
                    return this.allocatedBufferCount * this.bufferLength;
                }

                @Override // com.google.android.exoplayer.upstream.Allocator
/* 59 */        public synchronized void trim(int targetSize) {
                    try {
                        int targetBufferCount = ((this.bufferLength + targetSize) - 1) / this.bufferLength;
/* 61 */                int targetRecycledBufferCount = Math.max(0, targetBufferCount - this.allocatedBufferCount);
/* 62 */                if (targetRecycledBufferCount < this.recycledBufferCount) {
/* 63 */                    Arrays.fill(this.recycledBuffers, targetRecycledBufferCount, this.recycledBufferCount, (Object) null);
/* 64 */                    this.recycledBufferCount = targetRecycledBufferCount;
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }

                @Override // com.google.android.exoplayer.upstream.Allocator
/* 69 */        public synchronized Allocation allocate(int size) {
                    try {
                    } catch (Throwable th) {
                        throw th;
                    }
                    return new AllocationImpl(allocate(size, (byte[][]) null));
                }

/* 82 */        synchronized byte[][] allocate(int size, byte[][] existing) {
                    byte[] bArr;
                    try {
                        int requiredBufferCount = requiredBufferCount(size);
/* 84 */                if (existing == null || requiredBufferCount > existing.length) {
/* 89 */                    byte[][] buffers = new byte[requiredBufferCount][];
/* 90 */                    int firstNewBufferIndex = 0;
/* 91 */                    if (existing != null) {
/* 92 */                        firstNewBufferIndex = existing.length;
/* 93 */                        System.arraycopy(existing, 0, buffers, 0, firstNewBufferIndex);
                            }
/* 96 */                    this.allocatedBufferCount += requiredBufferCount - firstNewBufferIndex;
                            for (int i = firstNewBufferIndex; i < requiredBufferCount; i++) {
/* 99 */                        if (this.recycledBufferCount > 0) {
                                    byte[][] bArr2 = this.recycledBuffers;
                                    int i2 = this.recycledBufferCount - 1;
                                    this.recycledBufferCount = i2;
                                    bArr = bArr2[i2];
                                } else {
/* 99 */                            bArr = new byte[this.bufferLength];
                                }
                                buffers[i] = bArr;
                            }
                            existing = buffers;
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                    return existing;
                }

/* 110 */       synchronized void release(AllocationImpl allocation) {
                    try {
                        byte[][] buffers = allocation.getBuffers();
/* 112 */               this.allocatedBufferCount -= buffers.length;
/* 114 */               int newRecycledBufferCount = this.recycledBufferCount + buffers.length;
/* 115 */               if (this.recycledBuffers.length < newRecycledBufferCount) {
/* 117 */                   byte[][] newRecycledBuffers = new byte[newRecycledBufferCount * 2][];
/* 118 */                   if (this.recycledBufferCount > 0) {
/* 119 */                       System.arraycopy(this.recycledBuffers, 0, newRecycledBuffers, 0, this.recycledBufferCount);
                            }
/* 121 */                   this.recycledBuffers = newRecycledBuffers;
                        }
/* 123 */               System.arraycopy(buffers, 0, this.recycledBuffers, this.recycledBufferCount, buffers.length);
/* 124 */               this.recycledBufferCount = newRecycledBufferCount;
                    } catch (Throwable th) {
                        throw th;
                    }
                }

/* 127 */       private int requiredBufferCount(long size) {
/* 128 */           return (int) (((((long) this.bufferLength) + size) - 1) / ((long) this.bufferLength));
                }

/* 133 */       private class AllocationImpl implements Allocation {
                    private byte[][] buffers;

/* 134 */           public AllocationImpl(byte[][] buffers) {
/* 136 */               this.buffers = buffers;
                    }

                    @Override // com.google.android.exoplayer.upstream.Allocation
/* 140 */           public void ensureCapacity(int size) {
/* 141 */               this.buffers = BufferPool.this.allocate(size, this.buffers);
                    }

                    @Override // com.google.android.exoplayer.upstream.Allocation
/* 145 */           public int capacity() {
/* 146 */               return BufferPool.this.bufferLength * this.buffers.length;
                    }

                    @Override // com.google.android.exoplayer.upstream.Allocation
/* 150 */           public byte[][] getBuffers() {
/* 151 */               return this.buffers;
                    }

                    @Override // com.google.android.exoplayer.upstream.Allocation
/* 155 */           public int getFragmentOffset(int index) {
/* 156 */               return 0;
                    }

                    @Override // com.google.android.exoplayer.upstream.Allocation
/* 160 */           public int getFragmentLength(int index) {
/* 161 */               return BufferPool.this.bufferLength;
                    }

                    @Override // com.google.android.exoplayer.upstream.Allocation
/* 165 */           public void release() {
/* 166 */               if (this.buffers != null) {
/* 167 */                   BufferPool.this.release(this);
/* 168 */                   this.buffers = (byte[][]) null;
                        }
                    }
                }
            }
