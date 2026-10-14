            package com.squareup.okhttp.internal.http;

            import com.squareup.okhttp.internal.Util;
            import java.io.IOException;
            import java.net.ProtocolException;
            import okio.Buffer;
            import okio.Sink;
            import okio.Timeout;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 35 */    public final class RetryableSink implements Sink {
                private boolean closed;
                private final Buffer content;
                private final int limit;

/* 36 */        public RetryableSink(int limit) {
/* 35 */            this.content = new Buffer();
/* 38 */            this.limit = limit;
                }

/* 41 */        public RetryableSink() {
/* 42 */            this(-1);
                }

                @Override // okio.Sink, java.io.Closeable, java.lang.AutoCloseable
/* 45 */        public void close() throws IOException {
/* 46 */            if (!this.closed) {
/* 47 */                this.closed = true;
                        if (this.content.size() < this.limit) {
/* 49 */                    throw new ProtocolException("content-length promised " + this.limit + " bytes, but received " + this.content.size());
                        }
                    }
                }

                @Override // okio.Sink
/* 54 */        public void write(Buffer source, long byteCount) throws IOException {
/* 55 */            if (this.closed) {
                        throw new IllegalStateException("closed");
                    }
/* 56 */            Util.checkOffsetAndCount(source.size(), 0L, byteCount);
/* 57 */            if (this.limit != -1 && this.content.size() > ((long) this.limit) - byteCount) {
/* 58 */                throw new ProtocolException("exceeded content-length limit of " + this.limit + " bytes");
                    }
/* 60 */            this.content.write(source, byteCount);
                }

                @Override // okio.Sink
/* 63 */        public void flush() throws IOException {
                }

                @Override // okio.Sink
/* 66 */        public Timeout timeout() {
/* 67 */            return Timeout.NONE;
                }

/* 70 */        public long contentLength() throws IOException {
/* 71 */            return this.content.size();
                }

/* 75 */        public void writeToSocket(Sink socketOut) throws IOException {
/* 76 */            Buffer buffer = this.content.m2481clone();
/* 77 */            socketOut.write(buffer, buffer.size());
                }
            }
