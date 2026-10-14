            package twitter4j.internal.http;

            import java.io.IOException;
            import java.io.InputStream;
            import java.util.zip.GZIPInputStream;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 26 */    final class StreamingGZIPInputStream extends GZIPInputStream {
                private final InputStream wrapped;

/* 27 */        public StreamingGZIPInputStream(InputStream is) throws IOException {
/* 28 */            super(is);
/* 29 */            this.wrapped = is;
                }

                @Override // java.util.zip.InflaterInputStream, java.io.FilterInputStream, java.io.InputStream
/* 44 */        public int available() throws IOException {
/* 45 */            return this.wrapped.available();
                }
            }
