            package com.google.android.exoplayer.upstream;

            import com.google.android.exoplayer.util.Assertions;
            import java.io.IOException;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 33 */    public final class TeeDataSource implements DataSource {
                private final DataSink dataSink;
                private final DataSource upstream;

/* 34 */        public TeeDataSource(DataSource upstream, DataSink dataSink) {
/* 36 */            this.upstream = (DataSource) Assertions.checkNotNull(upstream);
/* 37 */            this.dataSink = (DataSink) Assertions.checkNotNull(dataSink);
                }

                @Override // com.google.android.exoplayer.upstream.DataSource
/* 41 */        public long open(DataSpec dataSpec) throws IOException {
/* 42 */            long dataLength = this.upstream.open(dataSpec);
                    if (dataSpec.length == -1 && dataLength != -1) {
/* 45 */                dataSpec = new DataSpec(dataSpec.uri, dataSpec.absoluteStreamPosition, dataLength, dataSpec.key, dataSpec.position, dataSpec.uriIsFullStream);
                    }
/* 48 */            this.dataSink.open(dataSpec);
/* 49 */            return dataLength;
                }

                @Override // com.google.android.exoplayer.upstream.DataSource
/* 53 */        public int read(byte[] buffer, int offset, int max) throws IOException {
/* 54 */            int num = this.upstream.read(buffer, offset, max);
/* 55 */            if (num > 0) {
/* 57 */                this.dataSink.write(buffer, offset, num);
                    }
/* 59 */            return num;
                }

                @Override // com.google.android.exoplayer.upstream.DataSource
/* 63 */        public void close() throws IOException {
/* 64 */            this.upstream.close();
/* 65 */            this.dataSink.close();
                }
            }
