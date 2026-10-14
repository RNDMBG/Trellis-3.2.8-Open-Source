            package com.google.android.exoplayer.upstream;

            import com.google.android.exoplayer.util.Assertions;
            import java.io.IOException;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 34 */    public class PriorityDataSource implements DataSource {
                private final int priority;
                private final DataSource upstream;

/* 35 */        public PriorityDataSource(int priority, DataSource upstream) {
/* 37 */            this.priority = priority;
/* 38 */            this.upstream = (DataSource) Assertions.checkNotNull(upstream);
                }

                @Override // com.google.android.exoplayer.upstream.DataSource
/* 42 */        public long open(DataSpec dataSpec) throws IOException {
/* 43 */            NetworkLock.instance.proceedOrThrow(this.priority);
/* 44 */            return this.upstream.open(dataSpec);
                }

                @Override // com.google.android.exoplayer.upstream.DataSource
/* 48 */        public int read(byte[] buffer, int offset, int max) throws IOException {
/* 49 */            NetworkLock.instance.proceedOrThrow(this.priority);
/* 50 */            return this.upstream.read(buffer, offset, max);
                }

                @Override // com.google.android.exoplayer.upstream.DataSource
/* 54 */        public void close() throws IOException {
/* 55 */            this.upstream.close();
                }
            }
