            package okio;

            import java.io.IOException;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 22 */    public abstract class ForwardingSource implements Source {
                private final Source delegate;

/* 23 */        public ForwardingSource(Source delegate) {
/* 25 */            if (delegate == null) {
                        throw new IllegalArgumentException("delegate == null");
                    }
/* 26 */            this.delegate = delegate;
                }

/* 30 */        public final Source delegate() {
/* 31 */            return this.delegate;
                }

                @Override // okio.Source
/* 34 */        public long read(Buffer sink, long byteCount) throws IOException {
/* 35 */            return this.delegate.read(sink, byteCount);
                }

                @Override // okio.Source
/* 38 */        public Timeout timeout() {
/* 39 */            return this.delegate.timeout();
                }

                @Override // okio.Source, java.io.Closeable, java.lang.AutoCloseable
/* 42 */        public void close() throws IOException {
/* 43 */            this.delegate.close();
                }

/* 46 */        public String toString() {
/* 47 */            return getClass().getSimpleName() + "(" + this.delegate.toString() + ")";
                }
            }
