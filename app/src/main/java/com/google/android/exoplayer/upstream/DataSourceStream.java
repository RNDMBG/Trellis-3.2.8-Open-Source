            package com.google.android.exoplayer.upstream;

            import com.google.android.exoplayer.util.Assertions;
            import com.google.android.exoplayer.util.Util;
            import java.io.IOException;
            import java.nio.ByteBuffer;
            
            
    public final class DataSourceStream implements Loader.Loadable, NonBlockingInputStream {
                private static final int CHUNKED_ALLOCATION_INCREMENT = 262144;
                private Allocation allocation;
                private final Allocator allocator;
                private final DataSource dataSource;
                private final DataSpec dataSpec;
                private volatile boolean loadCanceled;
                private volatile long loadPosition;
                private final ReadHead readHead;
                private volatile long resolvedLength;
                private int writeFragmentIndex;
                private int writeFragmentOffset;
                private int writeFragmentRemainingLength;

        public static class DataSourceStreamLoadException extends IOException {
            public DataSourceStreamLoadException(IOException cause) {
                super(cause);
                    }
                }

        public DataSourceStream(DataSource dataSource, DataSpec dataSpec, Allocator allocator) {
            Assertions.checkState(dataSpec.length <= 2147483647L);
            this.dataSource = dataSource;
            this.dataSpec = dataSpec;
            this.allocator = allocator;
            this.resolvedLength = -1L;
            this.readHead = new ReadHead(null);
                }

        public void resetReadPosition() {
            this.readHead.reset();
                }

        public long getReadPosition() {
            return ReadHead.access$100(this.readHead);
                }

        public long getLoadPosition() {
            return this.loadPosition;
                }

       public long getLength() {
                    return this.resolvedLength != -1 ? this.resolvedLength : this.dataSpec.length;
                }

       public boolean isLoadFinished() {
                    return this.resolvedLength != -1 && this.loadPosition == this.resolvedLength;
                }

       public final byte[] getLoadedData() {
                    if (this.loadPosition == 0) {
                        return null;
                    }
           byte[] rawData = new byte[(int) this.loadPosition];
           read(null, rawData, 0, new ReadHead(null), rawData.length);
                    return rawData;
                }

                @Override 
       public long getAvailableByteCount() {
           return this.loadPosition - ((long) ReadHead.access$100(this.readHead));
                }

                @Override 
       public boolean isEndOfStream() {
                    return this.resolvedLength != -1 && ((long) ReadHead.access$100(this.readHead)) == this.resolvedLength;
                }

                @Override 
       public void close() {
           if (this.allocation != null) {
               this.allocation.release();
               this.allocation = null;
                    }
                }

                @Override 
       public int skip(int skipLength) {
           return read(null, null, 0, this.readHead, skipLength);
                }

                @Override 
       public int read(ByteBuffer target1, int readLength) {
           return read(target1, null, 0, this.readHead, readLength);
                }

                @Override 
       public int read(byte[] target, int offset, int readLength) {
           return read(null, target, offset, this.readHead, readLength);
                }

       private int read(ByteBuffer target, byte[] targetArray, int targetArrayOffset, ReadHead readHead, int readLength) {
           if (isEndOfStream()) {
               return -1;
                    }
           int bytesToRead = (int) Math.min(this.loadPosition - ((long) ReadHead.access$100(readHead)), readLength);
           if (bytesToRead == 0) {
                        return 0;
                    }
           if (ReadHead.access$100(readHead) == 0) {
               ReadHead.access$202(readHead, 0);
               ReadHead.access$302(readHead, this.allocation.getFragmentOffset(0));
               ReadHead.access$402(readHead, this.allocation.getFragmentLength(0));
                    }
           int bytesRead = 0;
           byte[][] buffers = this.allocation.getBuffers();
           while (bytesRead < bytesToRead) {
               if (ReadHead.access$400(readHead) == 0) {
                   ReadHead.access$208(readHead);
                   ReadHead.access$302(readHead, this.allocation.getFragmentOffset(ReadHead.access$200(readHead)));
                   ReadHead.access$402(readHead, this.allocation.getFragmentLength(ReadHead.access$200(readHead)));
                        }
               int bufferReadLength = Math.min(ReadHead.access$400(readHead), bytesToRead - bytesRead);
               if (target != null) {
                   target.put(buffers[ReadHead.access$200(readHead)], ReadHead.access$300(readHead), bufferReadLength);
                        } else if (targetArray != null) {
                   System.arraycopy(buffers[ReadHead.access$200(readHead)], ReadHead.access$300(readHead), targetArray, targetArrayOffset, bufferReadLength);
                   targetArrayOffset += bufferReadLength;
                        }
               ReadHead.access$112(readHead, bufferReadLength);
               bytesRead += bufferReadLength;
               ReadHead.access$312(readHead, bufferReadLength);
               ReadHead.access$420(readHead, bufferReadLength);
                    }
           return bytesRead;
                }

                @Override 
       public void cancelLoad() {
           this.loadCanceled = true;
                }

                @Override 
       public boolean isLoadCanceled() {
           return this.loadCanceled;
                }

                @Override 
       public void load() throws InterruptedException, IOException {
           if (!this.loadCanceled && !isLoadFinished()) {
                        try {
                            if (this.loadPosition == 0 && this.resolvedLength == -1) {
                       DataSpec loadDataSpec = this.dataSpec;
                       long resolvedLength = this.dataSource.open(loadDataSpec);
                                if (resolvedLength > 2147483647L) {
                           throw new DataSourceStreamLoadException(new UnexpectedLengthException(this.dataSpec.length, resolvedLength));
                                }
                       this.resolvedLength = resolvedLength;
                            } else {
                       long remainingLength = this.resolvedLength != -1 ? this.resolvedLength - this.loadPosition : -1L;
                       DataSpec loadDataSpec2 = new DataSpec(this.dataSpec.uri, this.dataSpec.position + this.loadPosition, remainingLength, this.dataSpec.key);
                       this.dataSource.open(loadDataSpec2);
                            }
                   if (this.allocation == null) {
                       int initialAllocationSize = this.resolvedLength != -1 ? (int) this.resolvedLength : 262144;
                       this.allocation = this.allocator.allocate(initialAllocationSize);
                            }
                   int allocationCapacity = this.allocation.capacity();
                            if (this.loadPosition == 0) {
                       this.writeFragmentIndex = 0;
                       this.writeFragmentOffset = this.allocation.getFragmentOffset(0);
                       this.writeFragmentRemainingLength = this.allocation.getFragmentLength(0);
                            }
                   int read = Integer.MAX_VALUE;
                   byte[][] buffers = this.allocation.getBuffers();
                   while (!this.loadCanceled && read > 0 && maybeMoreToLoad()) {
                       if (Thread.interrupted()) {
                           throw new InterruptedException();
                                }
                       read = this.dataSource.read(buffers[this.writeFragmentIndex], this.writeFragmentOffset, this.writeFragmentRemainingLength);
                       if (read > 0) {
                           this.loadPosition += (long) read;
                           this.writeFragmentOffset += read;
                           this.writeFragmentRemainingLength -= read;
                           if (this.writeFragmentRemainingLength == 0 && maybeMoreToLoad()) {
                               this.writeFragmentIndex++;
                                        if (this.loadPosition == allocationCapacity) {
                                   this.allocation.ensureCapacity(262144 + allocationCapacity);
                                   allocationCapacity = this.allocation.capacity();
                                   buffers = this.allocation.getBuffers();
                                        }
                               this.writeFragmentOffset = this.allocation.getFragmentOffset(this.writeFragmentIndex);
                               this.writeFragmentRemainingLength = this.allocation.getFragmentLength(this.writeFragmentIndex);
                                    }
                                } else if (this.resolvedLength == -1) {
                           this.resolvedLength = this.loadPosition;
                                } else if (this.resolvedLength != this.loadPosition) {
                           throw new DataSourceStreamLoadException(new UnexpectedLengthException(this.resolvedLength, this.loadPosition));
                                }
                            }
                   Util.closeQuietly(this.dataSource);
                        } catch (Throwable th) {
                            Util.closeQuietly(this.dataSource);
                            throw th;
                        }
                    }
                }

       private boolean maybeMoreToLoad() {
                    return this.resolvedLength == -1 || this.loadPosition < this.resolvedLength;
                }

       private static class ReadHead {
                    private int fragmentIndex;
                    private int fragmentOffset;
                    private int fragmentRemaining;
                    private int position;

           private ReadHead() {
                    }

            ReadHead(Object x0) {
               this();
                    }

           static  int access$100(ReadHead x0) {
               return x0.position;
                    }

           static  int access$112(ReadHead x0, int x1) {
               int i = x0.position + x1;
                        x0.position = i;
                        return i;
                    }

           static  int access$200(ReadHead x0) {
               return x0.fragmentIndex;
                    }

           static  int access$202(ReadHead x0, int x1) {
               x0.fragmentIndex = x1;
                        return x1;
                    }

           static  int access$208(ReadHead x0) {
               int i = x0.fragmentIndex;
                        x0.fragmentIndex = i + 1;
                        return i;
                    }

           static  int access$300(ReadHead x0) {
               return x0.fragmentOffset;
                    }

           static  int access$302(ReadHead x0, int x1) {
               x0.fragmentOffset = x1;
                        return x1;
                    }

           static  int access$312(ReadHead x0, int x1) {
               int i = x0.fragmentOffset + x1;
                        x0.fragmentOffset = i;
                        return i;
                    }

           static  int access$400(ReadHead x0) {
               return x0.fragmentRemaining;
                    }

           static  int access$402(ReadHead x0, int x1) {
               x0.fragmentRemaining = x1;
                        return x1;
                    }

           static  int access$420(ReadHead x0, int x1) {
               int i = x0.fragmentRemaining - x1;
                        x0.fragmentRemaining = i;
                        return i;
                    }

           public void reset() {
               this.position = 0;
               this.fragmentIndex = 0;
               this.fragmentOffset = 0;
               this.fragmentRemaining = 0;
                    }
                }
            }
