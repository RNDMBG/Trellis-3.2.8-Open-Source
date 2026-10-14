            package com.google.android.exoplayer.upstream;

            import com.google.android.exoplayer.util.Assertions;
            import java.io.IOException;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 32 */    public class ByteArrayDataSource implements DataSource {
                private final byte[] data;
                private int readPosition;

/* 33 */        public ByteArrayDataSource(byte[] data) {
/* 35 */            this.data = (byte[]) Assertions.checkNotNull(data);
                }

                @Override // com.google.android.exoplayer.upstream.DataSource
/* 39 */        public long open(DataSpec dataSpec) throws IOException {
                    if (dataSpec.length == -1) {
/* 41 */                Assertions.checkArgument(dataSpec.position < ((long) this.data.length));
                    } else {
/* 43 */                Assertions.checkArgument(dataSpec.position + dataSpec.length <= ((long) this.data.length));
                    }
/* 45 */            this.readPosition = (int) dataSpec.position;
                    return dataSpec.length == -1 ? ((long) this.data.length) - dataSpec.position : dataSpec.length;
                }

                @Override // com.google.android.exoplayer.upstream.DataSource
/* 52 */        public void close() throws IOException {
                }

                @Override // com.google.android.exoplayer.upstream.DataSource
/* 56 */        public int read(byte[] buffer, int offset, int length) throws IOException {
/* 57 */            System.arraycopy(this.data, this.readPosition, buffer, offset, length);
/* 58 */            this.readPosition += length;
/* 59 */            return length;
                }
            }
