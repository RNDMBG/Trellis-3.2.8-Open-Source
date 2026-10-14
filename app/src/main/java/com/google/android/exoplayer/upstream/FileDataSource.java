            package com.google.android.exoplayer.upstream;

            import java.io.IOException;
            import java.io.RandomAccessFile;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 31 */    public final class FileDataSource implements DataSource {
                private long bytesRemaining;
                private RandomAccessFile file;

/* 32 */        public static class FileDataSourceException extends IOException {
/* 33 */            public FileDataSourceException(IOException cause) {
/* 34 */                super(cause);
                    }
                }

                @Override // com.google.android.exoplayer.upstream.DataSource
/* 44 */        public long open(DataSpec dataSpec) throws FileDataSourceException {
                    try {
/* 45 */                this.file = new RandomAccessFile(dataSpec.uri.getPath(), "r");
/* 46 */                this.file.seek(dataSpec.position);
/* 47 */                this.bytesRemaining = dataSpec.length == -1 ? this.file.length() - dataSpec.position : dataSpec.length;
/* 49 */                return this.bytesRemaining;
                    } catch (IOException e) {
/* 51 */                throw new FileDataSourceException(e);
                    }
                }

                @Override // com.google.android.exoplayer.upstream.DataSource
/* 56 */        public int read(byte[] buffer, int offset, int readLength) throws FileDataSourceException {
                    if (this.bytesRemaining == 0) {
/* 58 */                return -1;
                    }
                    try {
/* 62 */                int bytesRead = this.file.read(buffer, offset, (int) Math.min(this.bytesRemaining, readLength));
/* 66 */                this.bytesRemaining -= (long) bytesRead;
/* 67 */                return bytesRead;
                    } catch (IOException e) {
/* 64 */                throw new FileDataSourceException(e);
                    }
                }

                @Override // com.google.android.exoplayer.upstream.DataSource
/* 72 */        public void close() throws FileDataSourceException {
/* 73 */            if (this.file != null) {
                        try {
/* 75 */                    this.file.close();
/* 79 */                    this.file = null;
                        } catch (IOException e) {
/* 77 */                    throw new FileDataSourceException(e);
                        }
                    }
                }
            }
