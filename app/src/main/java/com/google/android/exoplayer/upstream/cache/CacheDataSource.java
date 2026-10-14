            package com.google.android.exoplayer.upstream.cache;

            import android.net.Uri;
            import com.google.android.exoplayer.upstream.DataSink;
            import com.google.android.exoplayer.upstream.DataSource;
            import com.google.android.exoplayer.upstream.DataSpec;
            import com.google.android.exoplayer.upstream.FileDataSource;
            import com.google.android.exoplayer.upstream.TeeDataSource;
            import com.google.android.exoplayer.util.Assertions;
            import java.io.IOException;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 75 */    public final class CacheDataSource implements DataSource {
                private final boolean blockOnCache;
                private long bytesRemaining;
                private final Cache cache;
                private final DataSource cacheReadDataSource;
                private final DataSource cacheWriteDataSource;
                private DataSource currentDataSource;
                private final EventListener eventListener;
                private boolean ignoreCache;
                private final boolean ignoreCacheOnError;
                private String key;
                private CacheSpan lockedSpan;
                private long readPosition;
                private long totalCachedBytesRead;
                private final DataSource upstreamDataSource;
                private Uri uri;

                public interface EventListener {
                    void onCachedBytesRead(long j, long j2);
                }

/* 76 */        public CacheDataSource(Cache cache, DataSource upstream, boolean blockOnCache, boolean ignoreCacheOnError) {
/* 77 */            this(cache, upstream, blockOnCache, ignoreCacheOnError, Long.MAX_VALUE);
                }

/* 86 */        public CacheDataSource(Cache cache, DataSource upstream, boolean blockOnCache, boolean ignoreCacheOnError, long maxCacheFileSize) {
/* 87 */            this(cache, upstream, new FileDataSource(), new CacheDataSink(cache, maxCacheFileSize), blockOnCache, ignoreCacheOnError, null);
                }

/* 108 */       public CacheDataSource(Cache cache, DataSource upstream, DataSource cacheReadDataSource, DataSink cacheWriteDataSink, boolean blockOnCache, boolean ignoreCacheOnError, EventListener eventListener) {
/* 110 */           this.cache = cache;
/* 111 */           this.cacheReadDataSource = cacheReadDataSource;
/* 112 */           this.blockOnCache = blockOnCache;
/* 113 */           this.ignoreCacheOnError = ignoreCacheOnError;
/* 114 */           this.upstreamDataSource = upstream;
/* 115 */           if (cacheWriteDataSink != null) {
/* 116 */               this.cacheWriteDataSource = new TeeDataSource(upstream, cacheWriteDataSink);
                    } else {
/* 118 */               this.cacheWriteDataSource = null;
                    }
/* 120 */           this.eventListener = eventListener;
                }

                @Override // com.google.android.exoplayer.upstream.DataSource
/* 124 */       public long open(DataSpec dataSpec) throws IOException {
/* 125 */           Assertions.checkState(dataSpec.uriIsFullStream);
/* 128 */           Assertions.checkState(dataSpec.length != -1);
                    try {
/* 130 */               this.uri = dataSpec.uri;
/* 131 */               this.key = dataSpec.key;
/* 132 */               this.readPosition = dataSpec.position;
/* 133 */               this.bytesRemaining = dataSpec.length;
/* 134 */               openNextSource();
/* 135 */               return dataSpec.length;
                    } catch (IOException e) {
/* 137 */               handleBeforeThrow(e);
/* 138 */               throw e;
                    }
                }

                @Override // com.google.android.exoplayer.upstream.DataSource
/* 144 */       public int read(byte[] buffer, int offset, int max) throws IOException {
                    try {
/* 145 */               int bytesRead = this.currentDataSource.read(buffer, offset, max);
/* 146 */               if (bytesRead >= 0) {
/* 147 */                   if (this.currentDataSource == this.cacheReadDataSource) {
/* 148 */                       this.totalCachedBytesRead += (long) bytesRead;
                            }
/* 150 */                   this.readPosition += (long) bytesRead;
/* 151 */                   this.bytesRemaining -= (long) bytesRead;
/* 159 */                   return bytesRead;
                        }
/* 153 */               closeCurrentSource();
                        if (this.bytesRemaining > 0) {
/* 155 */                   openNextSource();
/* 156 */                   return read(buffer, offset, max);
                        }
/* 159 */               return bytesRead;
                    } catch (IOException e) {
/* 161 */               handleBeforeThrow(e);
/* 162 */               throw e;
                    }
                }

                @Override // com.google.android.exoplayer.upstream.DataSource
/* 167 */       public void close() throws IOException {
/* 168 */           notifyBytesRead();
                    try {
/* 170 */               closeCurrentSource();
                    } catch (IOException e) {
/* 172 */               handleBeforeThrow(e);
/* 173 */               throw e;
                    }
                }

/* 185 */       private void openNextSource() throws IOException {
                    CacheSpan span;
                    DataSpec dataSpec;
                    try {
/* 186 */               if (this.ignoreCache) {
/* 187 */                   span = null;
                        } else if (this.blockOnCache) {
/* 189 */                   span = this.cache.startReadWrite(this.key, this.readPosition);
                        } else {
/* 191 */                   span = this.cache.startReadWriteNonBlocking(this.key, this.readPosition);
                        }
/* 193 */               if (span == null) {
/* 196 */                   this.currentDataSource = this.upstreamDataSource;
/* 197 */                   dataSpec = new DataSpec(this.uri, this.readPosition, this.bytesRemaining, this.key);
                        } else if (span.isCached) {
/* 200 */                   Uri fileUri = Uri.fromFile(span.file);
/* 201 */                   long filePosition = this.readPosition - span.position;
/* 202 */                   long length = Math.min(span.length - filePosition, this.bytesRemaining);
/* 203 */                   dataSpec = new DataSpec(fileUri, this.readPosition, length, this.key, filePosition);
/* 204 */                   this.currentDataSource = this.cacheReadDataSource;
                        } else {
/* 207 */                   this.lockedSpan = span;
/* 208 */                   long length2 = span.isOpenEnded() ? this.bytesRemaining : Math.min(span.length, this.bytesRemaining);
/* 209 */                   dataSpec = new DataSpec(this.uri, this.readPosition, length2, this.key);
/* 210 */                   this.currentDataSource = this.cacheWriteDataSource != null ? this.cacheWriteDataSource : this.upstreamDataSource;
                        }
/* 213 */               this.currentDataSource.open(dataSpec);
                    } catch (InterruptedException e) {
/* 216 */               throw new RuntimeException(e);
                    }
                }

/* 220 */       private void closeCurrentSource() throws IOException {
/* 221 */           if (this.currentDataSource != null) {
                        try {
/* 225 */                   this.currentDataSource.close();
/* 226 */                   this.currentDataSource = null;
/* 228 */                   if (this.lockedSpan != null) {
/* 229 */                       this.cache.releaseHoleSpan(this.lockedSpan);
/* 230 */                       this.lockedSpan = null;
                            }
                        } catch (Throwable th) {
                            if (this.lockedSpan != null) {
/* 229 */                       this.cache.releaseHoleSpan(this.lockedSpan);
/* 230 */                       this.lockedSpan = null;
                            }
                            throw th;
                        }
                    }
                }

/* 235 */       private void handleBeforeThrow(IOException exception) {
/* 236 */           if (this.ignoreCacheOnError) {
                        if (this.currentDataSource == this.cacheReadDataSource || (exception instanceof CacheDataSink.CacheDataSinkException)) {
/* 239 */                   this.ignoreCache = true;
                        }
                    }
                }

/* 243 */       private void notifyBytesRead() {
/* 244 */           if (this.eventListener != null && this.totalCachedBytesRead > 0) {
/* 245 */               this.eventListener.onCachedBytesRead(this.cache.getCacheSpace(), this.totalCachedBytesRead);
/* 246 */               this.totalCachedBytesRead = 0L;
                    }
                }
            }
