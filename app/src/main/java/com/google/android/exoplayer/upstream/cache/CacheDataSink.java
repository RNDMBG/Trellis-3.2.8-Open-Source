            package com.google.android.exoplayer.upstream.cache;

            import com.google.android.exoplayer.upstream.DataSink;
            import com.google.android.exoplayer.upstream.DataSpec;
            import com.google.android.exoplayer.util.Assertions;
            import java.io.File;
            import java.io.FileNotFoundException;
            import java.io.FileOutputStream;
            import java.io.IOException;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 45 */    public class CacheDataSink implements DataSink {
                private final Cache cache;
                private DataSpec dataSpec;
                private long dataSpecBytesWritten;
                private File file;
                private final long maxCacheFileSize;
                private FileOutputStream outputStream;
                private long outputStreamBytesWritten;

/* 46 */        public static class CacheDataSinkException extends IOException {
/* 47 */            public CacheDataSinkException(IOException cause) {
/* 48 */                super(cause);
                    }
                }

/* 59 */        public CacheDataSink(Cache cache, long maxCacheFileSize) {
/* 61 */            this.cache = (Cache) Assertions.checkNotNull(cache);
/* 62 */            this.maxCacheFileSize = maxCacheFileSize;
                }

                @Override // com.google.android.exoplayer.upstream.DataSink
/* 68 */        public DataSink open(DataSpec dataSpec) throws CacheDataSinkException {
/* 69 */            Assertions.checkState(dataSpec.length != -1);
                    try {
/* 71 */                this.dataSpec = dataSpec;
/* 72 */                this.dataSpecBytesWritten = 0L;
/* 73 */                openNextOutputStream();
/* 74 */                return this;
                    } catch (FileNotFoundException e) {
/* 76 */                throw new CacheDataSinkException(e);
                    }
                }

                @Override // com.google.android.exoplayer.upstream.DataSink
/* 82 */        public void write(byte[] buffer, int offset, int length) throws CacheDataSinkException {
/* 83 */            int bytesWritten = 0;
/* 84 */            while (bytesWritten < length) {
                        try {
                            if (this.outputStreamBytesWritten == this.maxCacheFileSize) {
/* 86 */                        closeCurrentOutputStream();
/* 87 */                        openNextOutputStream();
                            }
/* 89 */                    int bytesToWrite = (int) Math.min(length - bytesWritten, this.maxCacheFileSize - this.outputStreamBytesWritten);
/* 91 */                    this.outputStream.write(buffer, offset + bytesWritten, bytesToWrite);
/* 92 */                    bytesWritten += bytesToWrite;
/* 93 */                    this.outputStreamBytesWritten += (long) bytesToWrite;
/* 94 */                    this.dataSpecBytesWritten += (long) bytesToWrite;
                        } catch (IOException e) {
/* 97 */                    throw new CacheDataSinkException(e);
                        }
                    }
                }

                @Override // com.google.android.exoplayer.upstream.DataSink
/* 103 */       public void close() throws CacheDataSinkException {
                    try {
/* 104 */               closeCurrentOutputStream();
                    } catch (IOException e) {
/* 106 */               throw new CacheDataSinkException(e);
                    }
                }

/* 110 */       private void openNextOutputStream() throws FileNotFoundException {
/* 111 */           this.file = this.cache.startFile(this.dataSpec.key, this.dataSpec.absoluteStreamPosition + this.dataSpecBytesWritten, Math.min(this.dataSpec.length - this.dataSpecBytesWritten, this.maxCacheFileSize));
/* 113 */           this.outputStream = new FileOutputStream(this.file);
/* 114 */           this.outputStreamBytesWritten = 0L;
                }

/* 117 */       private void closeCurrentOutputStream() throws IOException {
/* 118 */           if (this.outputStream != null) {
/* 119 */               this.outputStream.flush();
/* 120 */               this.outputStream.close();
/* 121 */               this.outputStream = null;
/* 122 */               this.cache.commitFile(this.file);
/* 123 */               this.file = null;
                    }
                }
            }
