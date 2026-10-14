            package com.google.android.exoplayer.upstream;

            import com.google.android.exoplayer.util.Assertions;
            import com.google.android.exoplayer.util.Util;
            import java.io.IOException;
            import java.nio.ByteBuffer;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 35 */    public final class DataSourceStream implements Loader.Loadable, NonBlockingInputStream {
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

/* 36 */        public static class DataSourceStreamLoadException extends IOException {
/* 37 */            public DataSourceStreamLoadException(IOException cause) {
/* 38 */                super(cause);
                    }
                }

/* 67 */        public DataSourceStream(DataSource dataSource, DataSpec dataSpec, Allocator allocator) {
/* 69 */            Assertions.checkState(dataSpec.length <= 2147483647L);
/* 70 */            this.dataSource = dataSource;
/* 71 */            this.dataSpec = dataSpec;
/* 72 */            this.allocator = allocator;
/* 73 */            this.resolvedLength = -1L;
/* 74 */            this.readHead = new ReadHead(null);
                }

/* 80 */        public void resetReadPosition() {
/* 81 */            this.readHead.reset();
                }

/* 89 */        public long getReadPosition() {
/* 90 */            return ReadHead.access$100(this.readHead);
                }

/* 98 */        public long getLoadPosition() {
/* 99 */            return this.loadPosition;
                }

/* 109 */       public long getLength() {
                    return this.resolvedLength != -1 ? this.resolvedLength : this.dataSpec.length;
                }

/* 118 */       public boolean isLoadFinished() {
                    return this.resolvedLength != -1 && this.loadPosition == this.resolvedLength;
                }

/* 132 */       public final byte[] getLoadedData() {
                    if (this.loadPosition == 0) {
                        return null;
                    }
/* 137 */           byte[] rawData = new byte[(int) this.loadPosition];
/* 138 */           read(null, rawData, 0, new ReadHead(null), rawData.length);
                    return rawData;
                }

                @Override // com.google.android.exoplayer.upstream.NonBlockingInputStream
/* 145 */       public long getAvailableByteCount() {
/* 146 */           return this.loadPosition - ((long) ReadHead.access$100(this.readHead));
                }

                @Override // com.google.android.exoplayer.upstream.NonBlockingInputStream
/* 150 */       public boolean isEndOfStream() {
                    return this.resolvedLength != -1 && ((long) ReadHead.access$100(this.readHead)) == this.resolvedLength;
                }

                @Override // com.google.android.exoplayer.upstream.NonBlockingInputStream
/* 155 */       public void close() {
/* 156 */           if (this.allocation != null) {
/* 157 */               this.allocation.release();
/* 158 */               this.allocation = null;
                    }
                }

                @Override // com.google.android.exoplayer.upstream.NonBlockingInputStream
/* 163 */       public int skip(int skipLength) {
/* 164 */           return read(null, null, 0, this.readHead, skipLength);
                }

                @Override // com.google.android.exoplayer.upstream.NonBlockingInputStream
/* 168 */       public int read(ByteBuffer target1, int readLength) {
/* 169 */           return read(target1, null, 0, this.readHead, readLength);
                }

                @Override // com.google.android.exoplayer.upstream.NonBlockingInputStream
/* 173 */       public int read(byte[] target, int offset, int readLength) {
/* 174 */           return read(null, target, offset, this.readHead, readLength);
                }

/* 182 */       private int read(ByteBuffer target, byte[] targetArray, int targetArrayOffset, ReadHead readHead, int readLength) {
/* 183 */           if (isEndOfStream()) {
/* 184 */               return -1;
                    }
/* 186 */           int bytesToRead = (int) Math.min(this.loadPosition - ((long) ReadHead.access$100(readHead)), readLength);
/* 187 */           if (bytesToRead == 0) {
                        return 0;
                    }
/* 190 */           if (ReadHead.access$100(readHead) == 0) {
/* 191 */               ReadHead.access$202(readHead, 0);
/* 192 */               ReadHead.access$302(readHead, this.allocation.getFragmentOffset(0));
/* 193 */               ReadHead.access$402(readHead, this.allocation.getFragmentLength(0));
                    }
/* 195 */           int bytesRead = 0;
/* 196 */           byte[][] buffers = this.allocation.getBuffers();
/* 197 */           while (bytesRead < bytesToRead) {
/* 198 */               if (ReadHead.access$400(readHead) == 0) {
/* 199 */                   ReadHead.access$208(readHead);
/* 200 */                   ReadHead.access$302(readHead, this.allocation.getFragmentOffset(ReadHead.access$200(readHead)));
/* 201 */                   ReadHead.access$402(readHead, this.allocation.getFragmentLength(ReadHead.access$200(readHead)));
                        }
/* 203 */               int bufferReadLength = Math.min(ReadHead.access$400(readHead), bytesToRead - bytesRead);
/* 204 */               if (target != null) {
/* 205 */                   target.put(buffers[ReadHead.access$200(readHead)], ReadHead.access$300(readHead), bufferReadLength);
                        } else if (targetArray != null) {
/* 207 */                   System.arraycopy(buffers[ReadHead.access$200(readHead)], ReadHead.access$300(readHead), targetArray, targetArrayOffset, bufferReadLength);
/* 209 */                   targetArrayOffset += bufferReadLength;
                        }
/* 211 */               ReadHead.access$112(readHead, bufferReadLength);
/* 212 */               bytesRead += bufferReadLength;
/* 213 */               ReadHead.access$312(readHead, bufferReadLength);
/* 214 */               ReadHead.access$420(readHead, bufferReadLength);
                    }
/* 217 */           return bytesRead;
                }

                @Override // com.google.android.exoplayer.upstream.Loader.Loadable
/* 223 */       public void cancelLoad() {
/* 224 */           this.loadCanceled = true;
                }

                @Override // com.google.android.exoplayer.upstream.Loader.Loadable
/* 228 */       public boolean isLoadCanceled() {
/* 229 */           return this.loadCanceled;
                }

                @Override // com.google.android.exoplayer.upstream.Loader.Loadable
/* 233 */       public void load() throws InterruptedException, IOException {
/* 234 */           if (!this.loadCanceled && !isLoadFinished()) {
                        try {
                            if (this.loadPosition == 0 && this.resolvedLength == -1) {
/* 242 */                       DataSpec loadDataSpec = this.dataSpec;
/* 243 */                       long resolvedLength = this.dataSource.open(loadDataSpec);
                                if (resolvedLength > 2147483647L) {
/* 245 */                           throw new DataSourceStreamLoadException(new UnexpectedLengthException(this.dataSpec.length, resolvedLength));
                                }
/* 248 */                       this.resolvedLength = resolvedLength;
                            } else {
/* 250 */                       long remainingLength = this.resolvedLength != -1 ? this.resolvedLength - this.loadPosition : -1L;
/* 252 */                       DataSpec loadDataSpec2 = new DataSpec(this.dataSpec.uri, this.dataSpec.position + this.loadPosition, remainingLength, this.dataSpec.key);
/* 254 */                       this.dataSource.open(loadDataSpec2);
                            }
/* 257 */                   if (this.allocation == null) {
/* 258 */                       int initialAllocationSize = this.resolvedLength != -1 ? (int) this.resolvedLength : 262144;
/* 260 */                       this.allocation = this.allocator.allocate(initialAllocationSize);
                            }
/* 262 */                   int allocationCapacity = this.allocation.capacity();
                            if (this.loadPosition == 0) {
/* 265 */                       this.writeFragmentIndex = 0;
/* 266 */                       this.writeFragmentOffset = this.allocation.getFragmentOffset(0);
/* 267 */                       this.writeFragmentRemainingLength = this.allocation.getFragmentLength(0);
                            }
/* 270 */                   int read = Integer.MAX_VALUE;
/* 271 */                   byte[][] buffers = this.allocation.getBuffers();
/* 272 */                   while (!this.loadCanceled && read > 0 && maybeMoreToLoad()) {
/* 273 */                       if (Thread.interrupted()) {
/* 274 */                           throw new InterruptedException();
                                }
/* 276 */                       read = this.dataSource.read(buffers[this.writeFragmentIndex], this.writeFragmentOffset, this.writeFragmentRemainingLength);
/* 278 */                       if (read > 0) {
/* 279 */                           this.loadPosition += (long) read;
/* 280 */                           this.writeFragmentOffset += read;
/* 281 */                           this.writeFragmentRemainingLength -= read;
/* 282 */                           if (this.writeFragmentRemainingLength == 0 && maybeMoreToLoad()) {
/* 283 */                               this.writeFragmentIndex++;
                                        if (this.loadPosition == allocationCapacity) {
/* 285 */                                   this.allocation.ensureCapacity(262144 + allocationCapacity);
/* 286 */                                   allocationCapacity = this.allocation.capacity();
/* 287 */                                   buffers = this.allocation.getBuffers();
                                        }
/* 289 */                               this.writeFragmentOffset = this.allocation.getFragmentOffset(this.writeFragmentIndex);
/* 290 */                               this.writeFragmentRemainingLength = this.allocation.getFragmentLength(this.writeFragmentIndex);
                                    }
                                } else if (this.resolvedLength == -1) {
/* 293 */                           this.resolvedLength = this.loadPosition;
                                } else if (this.resolvedLength != this.loadPosition) {
/* 295 */                           throw new DataSourceStreamLoadException(new UnexpectedLengthException(this.resolvedLength, this.loadPosition));
                                }
                            }
/* 300 */                   Util.closeQuietly(this.dataSource);
                        } catch (Throwable th) {
                            Util.closeQuietly(this.dataSource);
                            throw th;
                        }
                    }
                }

/* 304 */       private boolean maybeMoreToLoad() {
                    return this.resolvedLength == -1 || this.loadPosition < this.resolvedLength;
                }

/* 306 */       private static class ReadHead {
                    private int fragmentIndex;
                    private int fragmentOffset;
                    private int fragmentRemaining;
                    private int position;

/* 307 */           private ReadHead() {
                    }

/* 307 */           /* synthetic */ ReadHead(Object x0) {
/* 308 */               this();
                    }

/* 307 */           static /* synthetic */ int access$100(ReadHead x0) {
/* 308 */               return x0.position;
                    }

/* 307 */           static /* synthetic */ int access$112(ReadHead x0, int x1) {
/* 308 */               int i = x0.position + x1;
                        x0.position = i;
                        return i;
                    }

/* 307 */           static /* synthetic */ int access$200(ReadHead x0) {
/* 308 */               return x0.fragmentIndex;
                    }

/* 307 */           static /* synthetic */ int access$202(ReadHead x0, int x1) {
/* 308 */               x0.fragmentIndex = x1;
                        return x1;
                    }

/* 307 */           static /* synthetic */ int access$208(ReadHead x0) {
/* 308 */               int i = x0.fragmentIndex;
                        x0.fragmentIndex = i + 1;
                        return i;
                    }

/* 307 */           static /* synthetic */ int access$300(ReadHead x0) {
/* 308 */               return x0.fragmentOffset;
                    }

/* 307 */           static /* synthetic */ int access$302(ReadHead x0, int x1) {
/* 308 */               x0.fragmentOffset = x1;
                        return x1;
                    }

/* 307 */           static /* synthetic */ int access$312(ReadHead x0, int x1) {
/* 308 */               int i = x0.fragmentOffset + x1;
                        x0.fragmentOffset = i;
                        return i;
                    }

/* 307 */           static /* synthetic */ int access$400(ReadHead x0) {
/* 308 */               return x0.fragmentRemaining;
                    }

/* 307 */           static /* synthetic */ int access$402(ReadHead x0, int x1) {
/* 308 */               x0.fragmentRemaining = x1;
                        return x1;
                    }

/* 307 */           static /* synthetic */ int access$420(ReadHead x0, int x1) {
/* 308 */               int i = x0.fragmentRemaining - x1;
                        x0.fragmentRemaining = i;
                        return i;
                    }

/* 315 */           public void reset() {
/* 316 */               this.position = 0;
/* 317 */               this.fragmentIndex = 0;
/* 318 */               this.fragmentOffset = 0;
/* 319 */               this.fragmentRemaining = 0;
                    }
                }
            }
