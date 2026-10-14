            package com.google.android.exoplayer.upstream;

            import com.google.android.exoplayer.util.Assertions;
            import java.io.IOException;
            
            
    public final class TeeDataSource implements DataSource {
                private final DataSink dataSink;
                private final DataSource upstream;

        public TeeDataSource(DataSource upstream, DataSink dataSink) {
            this.upstream = (DataSource) Assertions.checkNotNull(upstream);
            this.dataSink = (DataSink) Assertions.checkNotNull(dataSink);
                }

                @Override 
        public long open(DataSpec dataSpec) throws IOException {
            long dataLength = this.upstream.open(dataSpec);
                    if (dataSpec.length == -1 && dataLength != -1) {
                dataSpec = new DataSpec(dataSpec.uri, dataSpec.absoluteStreamPosition, dataLength, dataSpec.key, dataSpec.position, dataSpec.uriIsFullStream);
                    }
            this.dataSink.open(dataSpec);
            return dataLength;
                }

                @Override 
        public int read(byte[] buffer, int offset, int max) throws IOException {
            int num = this.upstream.read(buffer, offset, max);
            if (num > 0) {
                this.dataSink.write(buffer, offset, num);
                    }
            return num;
                }

                @Override 
        public void close() throws IOException {
            this.upstream.close();
            this.dataSink.close();
                }
            }
