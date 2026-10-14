            package com.google.android.exoplayer.upstream;

            import java.io.IOException;
            import java.io.RandomAccessFile;
            
            
    public final class FileDataSource implements DataSource {
                private long bytesRemaining;
                private RandomAccessFile file;

        public static class FileDataSourceException extends IOException {
            public FileDataSourceException(IOException cause) {
                super(cause);
                    }
                }

                @Override 
        public long open(DataSpec dataSpec) throws FileDataSourceException {
                    try {
                this.file = new RandomAccessFile(dataSpec.uri.getPath(), "r");
                this.file.seek(dataSpec.position);
                this.bytesRemaining = dataSpec.length == -1 ? this.file.length() - dataSpec.position : dataSpec.length;
                return this.bytesRemaining;
                    } catch (IOException e) {
                throw new FileDataSourceException(e);
                    }
                }

                @Override 
        public int read(byte[] buffer, int offset, int readLength) throws FileDataSourceException {
                    if (this.bytesRemaining == 0) {
                return -1;
                    }
                    try {
                int bytesRead = this.file.read(buffer, offset, (int) Math.min(this.bytesRemaining, readLength));
                this.bytesRemaining -= (long) bytesRead;
                return bytesRead;
                    } catch (IOException e) {
                throw new FileDataSourceException(e);
                    }
                }

                @Override 
        public void close() throws FileDataSourceException {
            if (this.file != null) {
                        try {
                    this.file.close();
                    this.file = null;
                        } catch (IOException e) {
                    throw new FileDataSourceException(e);
                        }
                    }
                }
            }
