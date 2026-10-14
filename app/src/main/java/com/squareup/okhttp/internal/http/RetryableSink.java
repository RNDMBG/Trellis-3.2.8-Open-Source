            package com.squareup.okhttp.internal.http;

            import com.squareup.okhttp.internal.Util;
            import java.io.IOException;
            import java.net.ProtocolException;
            import okio.Buffer;
            import okio.Sink;
            import okio.Timeout;
            
            
    public final class RetryableSink implements Sink {
                private boolean closed;
                private final Buffer content;
                private final int limit;

        public RetryableSink(int limit) {
            this.content = new Buffer();
            this.limit = limit;
                }

        public RetryableSink() {
            this(-1);
                }

                @Override 
        public void close() throws IOException {
            if (!this.closed) {
                this.closed = true;
                        if (this.content.size() < this.limit) {
                    throw new ProtocolException("content-length promised " + this.limit + " bytes, but received " + this.content.size());
                        }
                    }
                }

                @Override 
        public void write(Buffer source, long byteCount) throws IOException {
            if (this.closed) {
                        throw new IllegalStateException("closed");
                    }
            Util.checkOffsetAndCount(source.size(), 0L, byteCount);
            if (this.limit != -1 && this.content.size() > ((long) this.limit) - byteCount) {
                throw new ProtocolException("exceeded content-length limit of " + this.limit + " bytes");
                    }
            this.content.write(source, byteCount);
                }

                @Override 
        public void flush() throws IOException {
                }

                @Override 
        public Timeout timeout() {
            return Timeout.NONE;
                }

        public long contentLength() throws IOException {
            return this.content.size();
                }

        public void writeToSocket(Sink socketOut) throws IOException {
            Buffer buffer = this.content.m2481clone();
            socketOut.write(buffer, buffer.size());
                }
            }
