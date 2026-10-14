            package okio;

            import java.io.IOException;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 22 */    public abstract class ForwardingSink implements Sink {
                private final Sink delegate;

/* 23 */        public ForwardingSink(Sink delegate) {
/* 25 */            if (delegate == null) {
                        throw new IllegalArgumentException("delegate == null");
                    }
/* 26 */            this.delegate = delegate;
                }

/* 30 */        public final Sink delegate() {
/* 31 */            return this.delegate;
                }

                @Override // okio.Sink
/* 34 */        public void write(Buffer source, long byteCount) throws IOException {
/* 35 */            this.delegate.write(source, byteCount);
                }

                @Override // okio.Sink
/* 38 */        public void flush() throws IOException {
/* 39 */            this.delegate.flush();
                }

                @Override // okio.Sink
/* 42 */        public Timeout timeout() {
/* 43 */            return this.delegate.timeout();
                }

                @Override // okio.Sink, java.io.Closeable, java.lang.AutoCloseable
/* 46 */        public void close() throws IOException {
/* 47 */            this.delegate.close();
                }

/* 50 */        public String toString() {
/* 51 */            return getClass().getSimpleName() + "(" + this.delegate.toString() + ")";
                }
            }
