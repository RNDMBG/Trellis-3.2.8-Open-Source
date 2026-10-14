            package com.google.android.exoplayer.upstream;

            import com.google.android.exoplayer.util.Assertions;
            import java.io.IOException;
            import java.io.InputStream;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 38 */    public class DataSourceInputStream extends InputStream {
                private final DataSource dataSource;
                private final DataSpec dataSpec;
                private boolean opened = false;
                private boolean closed = false;
                private final byte[] singleByteArray = new byte[1];

/* 39 */        public DataSourceInputStream(DataSource dataSource, DataSpec dataSpec) {
/* 41 */            this.dataSource = dataSource;
/* 42 */            this.dataSpec = dataSpec;
                }

                @Override // java.io.InputStream
/* 47 */        public int read() throws IOException {
/* 48 */            read(this.singleByteArray);
/* 49 */            return this.singleByteArray[0];
                }

                @Override // java.io.InputStream
/* 53 */        public int read(byte[] buffer) throws IOException {
/* 54 */            return read(buffer, 0, buffer.length);
                }

                @Override // java.io.InputStream
/* 58 */        public int read(byte[] buffer, int offset, int length) throws IOException {
/* 59 */            Assertions.checkState(!this.closed);
/* 60 */            checkOpened();
/* 61 */            return this.dataSource.read(buffer, offset, length);
                }

                @Override // java.io.InputStream
/* 65 */        public long skip(long byteCount) throws IOException {
/* 66 */            Assertions.checkState(!this.closed);
/* 67 */            checkOpened();
/* 68 */            return super.skip(byteCount);
                }

                @Override // java.io.InputStream, java.io.Closeable, java.lang.AutoCloseable
/* 72 */        public void close() throws IOException {
/* 73 */            if (!this.closed) {
/* 74 */                this.dataSource.close();
/* 75 */                this.closed = true;
                    }
                }

/* 79 */        private void checkOpened() throws IOException {
/* 80 */            if (!this.opened) {
/* 81 */                this.dataSource.open(this.dataSpec);
/* 82 */                this.opened = true;
                    }
                }
            }
