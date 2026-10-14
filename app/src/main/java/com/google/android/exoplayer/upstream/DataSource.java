            package com.google.android.exoplayer.upstream;

            import java.io.IOException;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public interface DataSource {
                void close() throws IOException;

                long open(DataSpec dataSpec) throws IOException;

                int read(byte[] bArr, int i, int i2) throws IOException;
            }
