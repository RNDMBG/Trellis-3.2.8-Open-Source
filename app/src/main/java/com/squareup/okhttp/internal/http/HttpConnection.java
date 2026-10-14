            package com.squareup.okhttp.internal.http;

            import com.squareup.okhttp.Connection;
            import com.squareup.okhttp.ConnectionPool;
            import com.squareup.okhttp.Headers;
            import com.squareup.okhttp.Response;
            import com.squareup.okhttp.internal.Internal;
            import com.squareup.okhttp.internal.Util;
            import java.io.IOException;
            import java.net.ProtocolException;
            import java.net.Socket;
            import java.net.SocketTimeoutException;
            import java.util.concurrent.TimeUnit;
            import okio.Buffer;
            import okio.BufferedSink;
            import okio.BufferedSource;
            import okio.Okio;
            import okio.Sink;
            import okio.Source;
            import okio.Timeout;
            
            
    public final class HttpConnection {
                private static final String CRLF = "\r\n";
                private static final int ON_IDLE_CLOSE = 2;
                private static final int ON_IDLE_HOLD = 0;
                private static final int ON_IDLE_POOL = 1;
                private static final int STATE_CLOSED = 6;
                private static final int STATE_IDLE = 0;
                private static final int STATE_OPEN_REQUEST_BODY = 1;
                private static final int STATE_OPEN_RESPONSE_BODY = 4;
                private static final int STATE_READING_RESPONSE_BODY = 5;
                private static final int STATE_READ_RESPONSE_HEADERS = 3;
                private static final int STATE_WRITING_REQUEST_BODY = 2;
                private final Connection connection;
                private final ConnectionPool pool;
                private final BufferedSink sink;
                private final Socket socket;
                private final BufferedSource source;
                private static final byte[] HEX_DIGITS = {48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 97, 98, 99, 100, 101, 102};
                private static final byte[] FINAL_CHUNK = {48, 13, 10, 13, 10};
                private int state = 0;
                private int onIdle = 0;

        static  BufferedSink access$200(HttpConnection x0) {
            return x0.sink;
                }

        static  int access$300(HttpConnection x0) {
            return x0.state;
                }

        static  int access$302(HttpConnection x0, int x1) {
            x0.state = x1;
                    return x1;
                }

        static  byte[] access$400() {
            return FINAL_CHUNK;
                }

        static  byte[] access$500() {
            return HEX_DIGITS;
                }

        static  int access$600(HttpConnection x0) {
            return x0.onIdle;
                }

        static  int access$602(HttpConnection x0, int x1) {
            x0.onIdle = x1;
                    return x1;
                }

        static  ConnectionPool access$700(HttpConnection x0) {
            return x0.pool;
                }

        static  Connection access$800(HttpConnection x0) {
            return x0.connection;
                }

        static  BufferedSource access$900(HttpConnection x0) {
            return x0.source;
                }

        public HttpConnection(ConnectionPool pool, Connection connection, Socket socket) throws IOException {
            this.pool = pool;
            this.connection = connection;
            this.socket = socket;
            this.source = Okio.buffer(Okio.source(socket));
            this.sink = Okio.buffer(Okio.sink(socket));
                }

        public void setTimeouts(int readTimeoutMillis, int writeTimeoutMillis) {
            if (readTimeoutMillis != 0) {
                this.source.timeout().timeout(readTimeoutMillis, TimeUnit.MILLISECONDS);
                    }
            if (writeTimeoutMillis != 0) {
                this.sink.timeout().timeout(writeTimeoutMillis, TimeUnit.MILLISECONDS);
                    }
                }

       public void poolOnIdle() {
           this.onIdle = 1;
           if (this.state == 0) {
               this.onIdle = 0;
               Internal.instance.recycle(this.pool, this.connection);
                    }
                }

       public void closeOnIdle() throws IOException {
           this.onIdle = 2;
           if (this.state == 0) {
               this.state = 6;
               this.connection.getSocket().close();
                    }
                }

       public boolean isClosed() {
                    return this.state == 6;
                }

       public void closeIfOwnedBy(Object owner) throws IOException {
           Internal.instance.closeIfOwnedBy(this.connection, owner);
                }

       public void flush() throws IOException {
           this.sink.flush();
                }

       public long bufferSize() {
           return this.source.buffer().size();
                }

       public boolean isReadable() {
                    try {
               int readTimeout = this.socket.getSoTimeout();
                        try {
                   this.socket.setSoTimeout(1);
                   if (this.source.exhausted()) {
                       this.socket.setSoTimeout(readTimeout);
                                return false;
                            }
                   this.socket.setSoTimeout(readTimeout);
                            return true;
                        } catch (Throwable th) {
                            this.socket.setSoTimeout(readTimeout);
                            throw th;
                        }
                    } catch (SocketTimeoutException e) {
                        return true;
                    } catch (IOException e2) {
                        return false;
                    }
                }

       public void writeRequest(Headers headers, String requestLine) throws IOException {
           if (this.state != 0) {
                        throw new IllegalStateException("state: " + this.state);
                    }
           this.sink.writeUtf8(requestLine).writeUtf8("\r\n");
                    for (int i = 0; i < headers.size(); i++) {
               this.sink.writeUtf8(headers.name(i)).writeUtf8(": ").writeUtf8(headers.value(i)).writeUtf8("\r\n");
                    }
           this.sink.writeUtf8("\r\n");
           this.state = 1;
                }

       public Response.Builder readResponse() throws IOException {
                    StatusLine statusLine;
                    Response.Builder responseBuilder;
           if (this.state != 1 && this.state != 3) {
               throw new IllegalStateException("state: " + this.state);
                    }
           do {
               statusLine = StatusLine.parse(this.source.readUtf8LineStrict());
               responseBuilder = new Response.Builder().protocol(statusLine.protocol).code(statusLine.code).message(statusLine.message);
               Headers.Builder headersBuilder = new Headers.Builder();
               readHeaders(headersBuilder);
               headersBuilder.add(OkHeaders.SELECTED_PROTOCOL, statusLine.protocol.toString());
               responseBuilder.headers(headersBuilder.build());
           } while (statusLine.code == 100);
           this.state = 4;
           return responseBuilder;
                }

       public void readHeaders(Headers.Builder builder) throws IOException {
                    while (true) {
               String line = this.source.readUtf8LineStrict();
                        if (line.length() != 0) {
                   Internal.instance.addLine(builder, line);
                        } else {
                   return;
                        }
                    }
                }

       public boolean discard(Source in, int timeoutMillis) {
                    try {
               int socketTimeout = this.socket.getSoTimeout();
               this.socket.setSoTimeout(timeoutMillis);
                        try {
                   boolean zSkipAll = Util.skipAll(in, timeoutMillis);
                   this.socket.setSoTimeout(socketTimeout);
                   return zSkipAll;
                        } catch (Throwable th) {
                            this.socket.setSoTimeout(socketTimeout);
                            throw th;
                        }
                    } catch (IOException e) {
               return false;
                    }
                }

       public Sink newChunkedSink() {
           if (this.state != 1) {
                        throw new IllegalStateException("state: " + this.state);
                    }
           this.state = 2;
           return new ChunkedSink(this, null);
                }

       public Sink newFixedLengthSink(long contentLength) {
           if (this.state != 1) {
                        throw new IllegalStateException("state: " + this.state);
                    }
           this.state = 2;
           return new FixedLengthSink(this, contentLength, null);
                }

       public void writeRequestBody(RetryableSink requestBody) throws IOException {
           if (this.state != 1) {
                        throw new IllegalStateException("state: " + this.state);
                    }
           this.state = 3;
           requestBody.writeToSocket(this.sink);
                }

       public Source newFixedLengthSource(CacheRequest cacheRequest, long length) throws IOException {
           if (this.state != 4) {
                        throw new IllegalStateException("state: " + this.state);
                    }
           this.state = 5;
           return new FixedLengthSource(cacheRequest, length);
                }

       public void emptyResponseBody() throws IOException {
           newFixedLengthSource(null, 0L);
                }

       public Source newChunkedSource(CacheRequest cacheRequest, HttpEngine httpEngine) throws IOException {
           if (this.state != 4) {
                        throw new IllegalStateException("state: " + this.state);
                    }
           this.state = 5;
           return new ChunkedSource(cacheRequest, httpEngine);
                }

       public Source newUnknownLengthSource(CacheRequest cacheRequest) throws IOException {
           if (this.state != 4) {
                        throw new IllegalStateException("state: " + this.state);
                    }
           this.state = 5;
           return new UnknownLengthSource(cacheRequest);
                }

       private final class FixedLengthSink implements Sink {
                    private long bytesRemaining;
                    private boolean closed;

            FixedLengthSink(HttpConnection x0, long x1, Object x2) {
               this(x1);
                    }

           private FixedLengthSink(long bytesRemaining) {
               this.bytesRemaining = bytesRemaining;
                    }

                    @Override 
           public Timeout timeout() {
               return HttpConnection.access$200(HttpConnection.this).timeout();
                    }

                    @Override 
           public void write(Buffer source, long byteCount) throws IOException {
               if (this.closed) {
                            throw new IllegalStateException("closed");
                        }
               Util.checkOffsetAndCount(source.size(), 0L, byteCount);
                        if (byteCount > this.bytesRemaining) {
                   throw new ProtocolException("expected " + this.bytesRemaining + " bytes but received " + byteCount);
                        }
               HttpConnection.access$200(HttpConnection.this).write(source, byteCount);
               this.bytesRemaining -= byteCount;
                    }

                    @Override 
           public void flush() throws IOException {
               if (!this.closed) {
                   HttpConnection.access$200(HttpConnection.this).flush();
                        }
                    }

                    @Override 
           public void close() throws IOException {
               if (!this.closed) {
                   this.closed = true;
                            if (this.bytesRemaining > 0) {
                                throw new ProtocolException("unexpected end of stream");
                            }
                   HttpConnection.access$302(HttpConnection.this, 3);
                        }
                    }
                }

       private final class ChunkedSink implements Sink {
                    private boolean closed;
                    private final byte[] hex;

           private ChunkedSink() {
               this.hex = new byte[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 13, 10};
                    }

            ChunkedSink(HttpConnection x0, Object x1) {
               this();
                    }

                    @Override 
           public Timeout timeout() {
               return HttpConnection.access$200(HttpConnection.this).timeout();
                    }

                    @Override 
           public void write(Buffer source, long byteCount) throws IOException {
               if (this.closed) {
                            throw new IllegalStateException("closed");
                        }
                        if (byteCount != 0) {
                   writeHex(byteCount);
                   HttpConnection.access$200(HttpConnection.this).write(source, byteCount);
                   HttpConnection.access$200(HttpConnection.this).writeUtf8("\r\n");
                        }
                    }

                    @Override 
           public synchronized void flush() throws IOException {
                        try {
                            if (!this.closed) {
                       HttpConnection.access$200(HttpConnection.this).flush();
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }

                    @Override 
           public synchronized void close() throws IOException {
                        try {
                            if (!this.closed) {
                       this.closed = true;
                       HttpConnection.access$200(HttpConnection.this).write(HttpConnection.access$400());
                       HttpConnection.access$302(HttpConnection.this, 3);
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }

           private void writeHex(long i) throws IOException {
               int cursor = 16;
                        do {
                            cursor--;
                   this.hex[cursor] = HttpConnection.access$500()[(int) (15 & i)];
                   i >>>= 4;
                        } while (i != 0);
               HttpConnection.access$200(HttpConnection.this).write(this.hex, cursor, this.hex.length - cursor);
                    }
                }

       private class AbstractSource {
                    protected final Sink cacheBody;
                    private final CacheRequest cacheRequest;
                    protected boolean closed;

           AbstractSource(CacheRequest cacheRequest) throws IOException {
               Sink cacheBody = cacheRequest != null ? cacheRequest.body() : null;
               cacheRequest = cacheBody == null ? null : cacheRequest;
               this.cacheBody = cacheBody;
               this.cacheRequest = cacheRequest;
                    }

           protected final void cacheWrite(Buffer source, long byteCount) throws IOException {
               if (this.cacheBody != null) {
                   Buffer sourceCopy = source.m2481clone();
                   sourceCopy.skip(sourceCopy.size() - byteCount);
                   this.cacheBody.write(sourceCopy, byteCount);
                        }
                    }

           protected final void endOfInput(boolean recyclable) throws IOException {
               if (HttpConnection.access$300(HttpConnection.this) != 5) {
                            throw new IllegalStateException("state: " + HttpConnection.access$300(HttpConnection.this));
                        }
               if (this.cacheRequest != null) {
                   this.cacheBody.close();
                        }
               HttpConnection.access$302(HttpConnection.this, 0);
               if (recyclable && HttpConnection.access$600(HttpConnection.this) == 1) {
                   HttpConnection.access$602(HttpConnection.this, 0);
                   Internal.instance.recycle(HttpConnection.access$700(HttpConnection.this), HttpConnection.access$800(HttpConnection.this));
                        } else if (HttpConnection.access$600(HttpConnection.this) == 2) {
                   HttpConnection.access$302(HttpConnection.this, 6);
                   HttpConnection.access$800(HttpConnection.this).getSocket().close();
                        }
                    }

           protected final void unexpectedEndOfInput() {
               if (this.cacheRequest != null) {
                   this.cacheRequest.abort();
                        }
               Util.closeQuietly(HttpConnection.access$800(HttpConnection.this).getSocket());
               HttpConnection.access$302(HttpConnection.this, 6);
                    }
                }

       private class FixedLengthSource extends AbstractSource implements Source {
                    private long bytesRemaining;

           public FixedLengthSource(CacheRequest cacheRequest, long length) throws IOException {
               super(cacheRequest);
               this.bytesRemaining = length;
                        if (this.bytesRemaining == 0) {
                   endOfInput(true);
                        }
                    }

                    @Override 
           public long read(Buffer sink, long byteCount) throws IOException {
                        if (byteCount < 0) {
                            throw new IllegalArgumentException("byteCount < 0: " + byteCount);
                        }
               if (this.closed) {
                            throw new IllegalStateException("closed");
                        }
                        if (this.bytesRemaining == 0) {
                            return -1L;
                        }
               long read = HttpConnection.access$900(HttpConnection.this).read(sink, Math.min(this.bytesRemaining, byteCount));
                        if (read == -1) {
                   unexpectedEndOfInput();
                   throw new ProtocolException("unexpected end of stream");
                        }
               this.bytesRemaining -= read;
               cacheWrite(sink, read);
                        if (this.bytesRemaining == 0) {
                   endOfInput(true);
                   return read;
                        }
               return read;
                    }

                    @Override 
           public Timeout timeout() {
               return HttpConnection.access$900(HttpConnection.this).timeout();
                    }

                    @Override 
           public void close() throws IOException {
               if (!this.closed) {
                            if (this.bytesRemaining != 0 && !HttpConnection.this.discard(this, 100)) {
                       unexpectedEndOfInput();
                            }
                   this.closed = true;
                        }
                    }
                }

       private class ChunkedSource extends AbstractSource implements Source {
                    private static final int NO_CHUNK_YET = -1;
                    private int bytesRemainingInChunk;
                    private boolean hasMoreChunks;
                    private final HttpEngine httpEngine;

           ChunkedSource(CacheRequest cacheRequest, HttpEngine httpEngine) throws IOException {
               super(cacheRequest);
               this.bytesRemainingInChunk = -1;
               this.hasMoreChunks = true;
               this.httpEngine = httpEngine;
                    }

                    @Override 
           public long read(Buffer sink, long byteCount) throws IOException {
                        if (byteCount < 0) {
                            throw new IllegalArgumentException("byteCount < 0: " + byteCount);
                        }
               if (this.closed) {
                            throw new IllegalStateException("closed");
                        }
               if (!this.hasMoreChunks) {
                            return -1L;
                        }
               if (this.bytesRemainingInChunk == 0 || this.bytesRemainingInChunk == -1) {
                   readChunkSize();
                   if (!this.hasMoreChunks) {
                                return -1L;
                            }
                        }
               long read = HttpConnection.access$900(HttpConnection.this).read(sink, Math.min(byteCount, this.bytesRemainingInChunk));
                        if (read == -1) {
                   unexpectedEndOfInput();
                   throw new IOException("unexpected end of stream");
                        }
               this.bytesRemainingInChunk = (int) (((long) this.bytesRemainingInChunk) - read);
               cacheWrite(sink, read);
               return read;
                    }

           private void readChunkSize() throws IOException {
               if (this.bytesRemainingInChunk != -1) {
                   HttpConnection.access$900(HttpConnection.this).readUtf8LineStrict();
                        }
               String chunkSizeString = HttpConnection.access$900(HttpConnection.this).readUtf8LineStrict();
               int index = chunkSizeString.indexOf(";");
               if (index != -1) {
                   chunkSizeString = chunkSizeString.substring(0, index);
                        }
                        try {
                   this.bytesRemainingInChunk = Integer.parseInt(chunkSizeString.trim(), 16);
                   if (this.bytesRemainingInChunk == 0) {
                       this.hasMoreChunks = false;
                       Headers.Builder trailersBuilder = new Headers.Builder();
                       HttpConnection.this.readHeaders(trailersBuilder);
                       this.httpEngine.receiveHeaders(trailersBuilder.build());
                       endOfInput(true);
                            }
                        } catch (NumberFormatException e) {
                   throw new ProtocolException("Expected a hex chunk size but was " + chunkSizeString);
                        }
                    }

                    @Override 
           public Timeout timeout() {
               return HttpConnection.access$900(HttpConnection.this).timeout();
                    }

                    @Override 
           public void close() throws IOException {
               if (!this.closed) {
                   if (this.hasMoreChunks && !HttpConnection.this.discard(this, 100)) {
                       unexpectedEndOfInput();
                            }
                   this.closed = true;
                        }
                    }
                }

       class UnknownLengthSource extends AbstractSource implements Source {
                    private boolean inputExhausted;

           UnknownLengthSource(CacheRequest cacheRequest) throws IOException {
               super(cacheRequest);
                    }

                    @Override 
           public long read(Buffer sink, long byteCount) throws IOException {
                        if (byteCount < 0) {
                            throw new IllegalArgumentException("byteCount < 0: " + byteCount);
                        }
               if (this.closed) {
                            throw new IllegalStateException("closed");
                        }
               if (this.inputExhausted) {
                            return -1L;
                        }
               long read = HttpConnection.access$900(HttpConnection.this).read(sink, byteCount);
                        if (read == -1) {
                   this.inputExhausted = true;
                   endOfInput(false);
                            return -1L;
                        }
               cacheWrite(sink, read);
               return read;
                    }

                    @Override 
           public Timeout timeout() {
               return HttpConnection.access$900(HttpConnection.this).timeout();
                    }

                    @Override 
           public void close() throws IOException {
               if (!this.closed) {
                   if (!this.inputExhausted) {
                       unexpectedEndOfInput();
                            }
                   this.closed = true;
                        }
                    }
                }
            }
