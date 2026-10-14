            package com.google.android.exoplayer.upstream;

            import com.google.android.exoplayer.util.Assertions;
            import java.io.IOException;
            import java.io.InputStream;
            
            
    public class DataSourceInputStream extends InputStream {
                private final DataSource dataSource;
                private final DataSpec dataSpec;
                private boolean opened = false;
                private boolean closed = false;
                private final byte[] singleByteArray = new byte[1];

        public DataSourceInputStream(DataSource dataSource, DataSpec dataSpec) {
            this.dataSource = dataSource;
            this.dataSpec = dataSpec;
                }

                @Override 
        public int read() throws IOException {
            read(this.singleByteArray);
            return this.singleByteArray[0];
                }

                @Override 
        public int read(byte[] buffer) throws IOException {
            return read(buffer, 0, buffer.length);
                }

                @Override 
        public int read(byte[] buffer, int offset, int length) throws IOException {
            Assertions.checkState(!this.closed);
            checkOpened();
            return this.dataSource.read(buffer, offset, length);
                }

                @Override 
        public long skip(long byteCount) throws IOException {
            Assertions.checkState(!this.closed);
            checkOpened();
            return super.skip(byteCount);
                }

                @Override 
        public void close() throws IOException {
            if (!this.closed) {
                this.dataSource.close();
                this.closed = true;
                    }
                }

        private void checkOpened() throws IOException {
            if (!this.opened) {
                this.dataSource.open(this.dataSpec);
                this.opened = true;
                    }
                }
            }
