            package com.google.android.exoplayer.upstream;

            import com.google.android.exoplayer.util.Assertions;
            import java.util.Arrays;
            
            
    public final class BufferPool implements Allocator {
                private static final int INITIAL_RECYCLED_BUFFERS_CAPACITY = 100;
                private int allocatedBufferCount;
                public final int bufferLength;
                private int recycledBufferCount;
                private byte[][] recycledBuffers;

        public BufferPool(int bufferLength) {
            Assertions.checkArgument(bufferLength > 0);
            this.bufferLength = bufferLength;
            this.recycledBuffers = new byte[100][];
                }

                @Override 
        public synchronized int getAllocatedSize() {
                    try {
                    } catch (Throwable th) {
                        throw th;
                    }
                    return this.allocatedBufferCount * this.bufferLength;
                }

                @Override 
        public synchronized void trim(int targetSize) {
                    try {
                        int targetBufferCount = ((this.bufferLength + targetSize) - 1) / this.bufferLength;
                int targetRecycledBufferCount = Math.max(0, targetBufferCount - this.allocatedBufferCount);
                if (targetRecycledBufferCount < this.recycledBufferCount) {
                    Arrays.fill(this.recycledBuffers, targetRecycledBufferCount, this.recycledBufferCount, (Object) null);
                    this.recycledBufferCount = targetRecycledBufferCount;
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }

                @Override 
        public synchronized Allocation allocate(int size) {
                    try {
                    } catch (Throwable th) {
                        throw th;
                    }
                    return new AllocationImpl(allocate(size, (byte[][]) null));
                }

        synchronized byte[][] allocate(int size, byte[][] existing) {
                    byte[] bArr;
                    try {
                        int requiredBufferCount = requiredBufferCount(size);
                if (existing == null || requiredBufferCount > existing.length) {
                    byte[][] buffers = new byte[requiredBufferCount][];
                    int firstNewBufferIndex = 0;
                    if (existing != null) {
                        firstNewBufferIndex = existing.length;
                        System.arraycopy(existing, 0, buffers, 0, firstNewBufferIndex);
                            }
                    this.allocatedBufferCount += requiredBufferCount - firstNewBufferIndex;
                            for (int i = firstNewBufferIndex; i < requiredBufferCount; i++) {
                        if (this.recycledBufferCount > 0) {
                                    byte[][] bArr2 = this.recycledBuffers;
                                    int i2 = this.recycledBufferCount - 1;
                                    this.recycledBufferCount = i2;
                                    bArr = bArr2[i2];
                                } else {
                            bArr = new byte[this.bufferLength];
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

       synchronized void release(AllocationImpl allocation) {
                    try {
                        byte[][] buffers = allocation.getBuffers();
               this.allocatedBufferCount -= buffers.length;
               int newRecycledBufferCount = this.recycledBufferCount + buffers.length;
               if (this.recycledBuffers.length < newRecycledBufferCount) {
                   byte[][] newRecycledBuffers = new byte[newRecycledBufferCount * 2][];
                   if (this.recycledBufferCount > 0) {
                       System.arraycopy(this.recycledBuffers, 0, newRecycledBuffers, 0, this.recycledBufferCount);
                            }
                   this.recycledBuffers = newRecycledBuffers;
                        }
               System.arraycopy(buffers, 0, this.recycledBuffers, this.recycledBufferCount, buffers.length);
               this.recycledBufferCount = newRecycledBufferCount;
                    } catch (Throwable th) {
                        throw th;
                    }
                }

       private int requiredBufferCount(long size) {
           return (int) (((((long) this.bufferLength) + size) - 1) / ((long) this.bufferLength));
                }

       private class AllocationImpl implements Allocation {
                    private byte[][] buffers;

           public AllocationImpl(byte[][] buffers) {
               this.buffers = buffers;
                    }

                    @Override 
           public void ensureCapacity(int size) {
               this.buffers = BufferPool.this.allocate(size, this.buffers);
                    }

                    @Override 
           public int capacity() {
               return BufferPool.this.bufferLength * this.buffers.length;
                    }

                    @Override 
           public byte[][] getBuffers() {
               return this.buffers;
                    }

                    @Override 
           public int getFragmentOffset(int index) {
               return 0;
                    }

                    @Override 
           public int getFragmentLength(int index) {
               return BufferPool.this.bufferLength;
                    }

                    @Override 
           public void release() {
               if (this.buffers != null) {
                   BufferPool.this.release(this);
                   this.buffers = (byte[][]) null;
                        }
                    }
                }
            }
