            package com.google.android.exoplayer.upstream;

            import com.google.android.exoplayer.util.Assertions;
            import java.io.ByteArrayOutputStream;
            import java.io.IOException;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 31 */    public class ByteArrayDataSink implements DataSink {
                private ByteArrayOutputStream stream;

                @Override // com.google.android.exoplayer.upstream.DataSink
/* 32 */        public DataSink open(DataSpec dataSpec) throws IOException {
                    if (dataSpec.length == -1) {
/* 34 */                this.stream = new ByteArrayOutputStream();
                    } else {
/* 36 */                Assertions.checkArgument(dataSpec.length <= 2147483647L);
/* 37 */                this.stream = new ByteArrayOutputStream((int) dataSpec.length);
                    }
/* 39 */            return this;
                }

                @Override // com.google.android.exoplayer.upstream.DataSink
/* 43 */        public void close() throws IOException {
/* 44 */            this.stream.close();
                }

                @Override // com.google.android.exoplayer.upstream.DataSink
/* 48 */        public void write(byte[] buffer, int offset, int length) throws IOException {
/* 49 */            this.stream.write(buffer, offset, length);
                }

/* 57 */        public byte[] getData() {
/* 58 */            if (this.stream == null) {
                        return null;
                    }
                    return this.stream.toByteArray();
                }
            }
