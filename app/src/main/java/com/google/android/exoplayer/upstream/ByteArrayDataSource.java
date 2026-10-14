            package com.google.android.exoplayer.upstream;

            import com.google.android.exoplayer.util.Assertions;
            import java.io.IOException;
            
            
    public class ByteArrayDataSource implements DataSource {
                private final byte[] data;
                private int readPosition;

        public ByteArrayDataSource(byte[] data) {
            this.data = (byte[]) Assertions.checkNotNull(data);
                }

                @Override 
        public long open(DataSpec dataSpec) throws IOException {
                    if (dataSpec.length == -1) {
                Assertions.checkArgument(dataSpec.position < ((long) this.data.length));
                    } else {
                Assertions.checkArgument(dataSpec.position + dataSpec.length <= ((long) this.data.length));
                    }
            this.readPosition = (int) dataSpec.position;
                    return dataSpec.length == -1 ? ((long) this.data.length) - dataSpec.position : dataSpec.length;
                }

                @Override 
        public void close() throws IOException {
                }

                @Override 
        public int read(byte[] buffer, int offset, int length) throws IOException {
            System.arraycopy(this.data, this.readPosition, buffer, offset, length);
            this.readPosition += length;
            return length;
                }
            }
