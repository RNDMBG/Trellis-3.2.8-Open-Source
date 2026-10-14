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
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 58 */    public final class HttpConnection {
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

/* 59 */        static /* synthetic */ BufferedSink access$200(HttpConnection x0) {
/* 60 */            return x0.sink;
                }

/* 59 */        static /* synthetic */ int access$300(HttpConnection x0) {
/* 60 */            return x0.state;
                }

/* 59 */        static /* synthetic */ int access$302(HttpConnection x0, int x1) {
/* 60 */            x0.state = x1;
                    return x1;
                }

/* 59 */        static /* synthetic */ byte[] access$400() {
/* 60 */            return FINAL_CHUNK;
                }

/* 59 */        static /* synthetic */ byte[] access$500() {
/* 60 */            return HEX_DIGITS;
                }

/* 59 */        static /* synthetic */ int access$600(HttpConnection x0) {
/* 60 */            return x0.onIdle;
                }

/* 59 */        static /* synthetic */ int access$602(HttpConnection x0, int x1) {
/* 60 */            x0.onIdle = x1;
                    return x1;
                }

/* 59 */        static /* synthetic */ ConnectionPool access$700(HttpConnection x0) {
/* 60 */            return x0.pool;
                }

/* 59 */        static /* synthetic */ Connection access$800(HttpConnection x0) {
/* 60 */            return x0.connection;
                }

/* 59 */        static /* synthetic */ BufferedSource access$900(HttpConnection x0) {
/* 60 */            return x0.source;
                }

/* 82 */        public HttpConnection(ConnectionPool pool, Connection connection, Socket socket) throws IOException {
/* 84 */            this.pool = pool;
/* 85 */            this.connection = connection;
/* 86 */            this.socket = socket;
/* 87 */            this.source = Okio.buffer(Okio.source(socket));
/* 88 */            this.sink = Okio.buffer(Okio.sink(socket));
                }

/* 91 */        public void setTimeouts(int readTimeoutMillis, int writeTimeoutMillis) {
/* 92 */            if (readTimeoutMillis != 0) {
/* 93 */                this.source.timeout().timeout(readTimeoutMillis, TimeUnit.MILLISECONDS);
                    }
/* 95 */            if (writeTimeoutMillis != 0) {
/* 96 */                this.sink.timeout().timeout(writeTimeoutMillis, TimeUnit.MILLISECONDS);
                    }
                }

/* 104 */       public void poolOnIdle() {
/* 105 */           this.onIdle = 1;
/* 108 */           if (this.state == 0) {
/* 109 */               this.onIdle = 0;
/* 110 */               Internal.instance.recycle(this.pool, this.connection);
                    }
                }

/* 118 */       public void closeOnIdle() throws IOException {
/* 119 */           this.onIdle = 2;
/* 122 */           if (this.state == 0) {
/* 123 */               this.state = 6;
/* 124 */               this.connection.getSocket().close();
                    }
                }

/* 129 */       public boolean isClosed() {
                    return this.state == 6;
                }

/* 133 */       public void closeIfOwnedBy(Object owner) throws IOException {
/* 134 */           Internal.instance.closeIfOwnedBy(this.connection, owner);
                }

/* 137 */       public void flush() throws IOException {
/* 138 */           this.sink.flush();
                }

/* 142 */       public long bufferSize() {
/* 143 */           return this.source.buffer().size();
                }

/* 148 */       public boolean isReadable() {
                    try {
/* 149 */               int readTimeout = this.socket.getSoTimeout();
                        try {
/* 151 */                   this.socket.setSoTimeout(1);
/* 152 */                   if (this.source.exhausted()) {
/* 157 */                       this.socket.setSoTimeout(readTimeout);
                                return false;
                            }
/* 157 */                   this.socket.setSoTimeout(readTimeout);
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

/* 167 */       public void writeRequest(Headers headers, String requestLine) throws IOException {
/* 168 */           if (this.state != 0) {
                        throw new IllegalStateException("state: " + this.state);
                    }
/* 169 */           this.sink.writeUtf8(requestLine).writeUtf8("\r\n");
                    for (int i = 0; i < headers.size(); i++) {
/* 171 */               this.sink.writeUtf8(headers.name(i)).writeUtf8(": ").writeUtf8(headers.value(i)).writeUtf8("\r\n");
                    }
/* 176 */           this.sink.writeUtf8("\r\n");
/* 177 */           this.state = 1;
                }

/* 181 */       public Response.Builder readResponse() throws IOException {
                    StatusLine statusLine;
                    Response.Builder responseBuilder;
/* 182 */           if (this.state != 1 && this.state != 3) {
/* 183 */               throw new IllegalStateException("state: " + this.state);
                    }
/* 199 */           do {
/* 187 */               statusLine = StatusLine.parse(this.source.readUtf8LineStrict());
/* 189 */               responseBuilder = new Response.Builder().protocol(statusLine.protocol).code(statusLine.code).message(statusLine.message);
/* 194 */               Headers.Builder headersBuilder = new Headers.Builder();
/* 195 */               readHeaders(headersBuilder);
/* 196 */               headersBuilder.add(OkHeaders.SELECTED_PROTOCOL, statusLine.protocol.toString());
/* 197 */               responseBuilder.headers(headersBuilder.build());
/* 199 */           } while (statusLine.code == 100);
/* 200 */           this.state = 4;
/* 201 */           return responseBuilder;
                }

/* 208 */       public void readHeaders(Headers.Builder builder) throws IOException {
                    while (true) {
/* 209 */               String line = this.source.readUtf8LineStrict();
                        if (line.length() != 0) {
/* 210 */                   Internal.instance.addLine(builder, line);
                        } else {
/* 212 */                   return;
                        }
                    }
                }

/* 221 */       public boolean discard(Source in, int timeoutMillis) {
                    try {
/* 222 */               int socketTimeout = this.socket.getSoTimeout();
/* 223 */               this.socket.setSoTimeout(timeoutMillis);
                        try {
/* 225 */                   boolean zSkipAll = Util.skipAll(in, timeoutMillis);
/* 227 */                   this.socket.setSoTimeout(socketTimeout);
/* 230 */                   return zSkipAll;
                        } catch (Throwable th) {
                            this.socket.setSoTimeout(socketTimeout);
                            throw th;
                        }
                    } catch (IOException e) {
/* 230 */               return false;
                    }
                }

/* 234 */       public Sink newChunkedSink() {
/* 235 */           if (this.state != 1) {
                        throw new IllegalStateException("state: " + this.state);
                    }
/* 236 */           this.state = 2;
/* 237 */           return new ChunkedSink(this, null);
                }

/* 240 */       public Sink newFixedLengthSink(long contentLength) {
/* 241 */           if (this.state != 1) {
                        throw new IllegalStateException("state: " + this.state);
                    }
/* 242 */           this.state = 2;
/* 243 */           return new FixedLengthSink(this, contentLength, null);
                }

/* 246 */       public void writeRequestBody(RetryableSink requestBody) throws IOException {
/* 247 */           if (this.state != 1) {
                        throw new IllegalStateException("state: " + this.state);
                    }
/* 248 */           this.state = 3;
/* 249 */           requestBody.writeToSocket(this.sink);
                }

/* 253 */       public Source newFixedLengthSource(CacheRequest cacheRequest, long length) throws IOException {
/* 254 */           if (this.state != 4) {
                        throw new IllegalStateException("state: " + this.state);
                    }
/* 255 */           this.state = 5;
/* 256 */           return new FixedLengthSource(cacheRequest, length);
                }

/* 263 */       public void emptyResponseBody() throws IOException {
/* 264 */           newFixedLengthSource(null, 0L);
                }

/* 268 */       public Source newChunkedSource(CacheRequest cacheRequest, HttpEngine httpEngine) throws IOException {
/* 269 */           if (this.state != 4) {
                        throw new IllegalStateException("state: " + this.state);
                    }
/* 270 */           this.state = 5;
/* 271 */           return new ChunkedSource(cacheRequest, httpEngine);
                }

/* 274 */       public Source newUnknownLengthSource(CacheRequest cacheRequest) throws IOException {
/* 275 */           if (this.state != 4) {
                        throw new IllegalStateException("state: " + this.state);
                    }
/* 276 */           this.state = 5;
/* 277 */           return new UnknownLengthSource(cacheRequest);
                }

/* 279 */       private final class FixedLengthSink implements Sink {
                    private long bytesRemaining;
                    private boolean closed;

/* 280 */           /* synthetic */ FixedLengthSink(HttpConnection x0, long x1, Object x2) {
/* 281 */               this(x1);
                    }

/* 284 */           private FixedLengthSink(long bytesRemaining) {
/* 286 */               this.bytesRemaining = bytesRemaining;
                    }

                    @Override // okio.Sink
/* 289 */           public Timeout timeout() {
/* 290 */               return HttpConnection.access$200(HttpConnection.this).timeout();
                    }

                    @Override // okio.Sink
/* 293 */           public void write(Buffer source, long byteCount) throws IOException {
/* 294 */               if (this.closed) {
                            throw new IllegalStateException("closed");
                        }
/* 295 */               Util.checkOffsetAndCount(source.size(), 0L, byteCount);
                        if (byteCount > this.bytesRemaining) {
/* 297 */                   throw new ProtocolException("expected " + this.bytesRemaining + " bytes but received " + byteCount);
                        }
/* 300 */               HttpConnection.access$200(HttpConnection.this).write(source, byteCount);
/* 301 */               this.bytesRemaining -= byteCount;
                    }

                    @Override // okio.Sink
/* 304 */           public void flush() throws IOException {
/* 305 */               if (!this.closed) {
/* 306 */                   HttpConnection.access$200(HttpConnection.this).flush();
                        }
                    }

                    @Override // okio.Sink, java.io.Closeable, java.lang.AutoCloseable
/* 309 */           public void close() throws IOException {
/* 310 */               if (!this.closed) {
/* 311 */                   this.closed = true;
                            if (this.bytesRemaining > 0) {
                                throw new ProtocolException("unexpected end of stream");
                            }
/* 313 */                   HttpConnection.access$302(HttpConnection.this, 3);
                        }
                    }
                }

/* 326 */       private final class ChunkedSink implements Sink {
                    private boolean closed;
                    private final byte[] hex;

/* 327 */           private ChunkedSink() {
/* 330 */               this.hex = new byte[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 13, 10};
                    }

/* 327 */           /* synthetic */ ChunkedSink(HttpConnection x0, Object x1) {
/* 328 */               this();
                    }

                    @Override // okio.Sink
/* 334 */           public Timeout timeout() {
/* 335 */               return HttpConnection.access$200(HttpConnection.this).timeout();
                    }

                    @Override // okio.Sink
/* 338 */           public void write(Buffer source, long byteCount) throws IOException {
/* 339 */               if (this.closed) {
                            throw new IllegalStateException("closed");
                        }
                        if (byteCount != 0) {
/* 342 */                   writeHex(byteCount);
/* 343 */                   HttpConnection.access$200(HttpConnection.this).write(source, byteCount);
/* 344 */                   HttpConnection.access$200(HttpConnection.this).writeUtf8("\r\n");
                        }
                    }

                    @Override // okio.Sink
/* 347 */           public synchronized void flush() throws IOException {
                        try {
                            if (!this.closed) {
/* 349 */                       HttpConnection.access$200(HttpConnection.this).flush();
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }

                    @Override // okio.Sink, java.io.Closeable, java.lang.AutoCloseable
/* 352 */           public synchronized void close() throws IOException {
                        try {
                            if (!this.closed) {
/* 354 */                       this.closed = true;
/* 355 */                       HttpConnection.access$200(HttpConnection.this).write(HttpConnection.access$400());
/* 356 */                       HttpConnection.access$302(HttpConnection.this, 3);
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }

/* 363 */           private void writeHex(long i) throws IOException {
/* 364 */               int cursor = 16;
                        do {
                            cursor--;
/* 366 */                   this.hex[cursor] = HttpConnection.access$500()[(int) (15 & i)];
/* 367 */                   i >>>= 4;
                        } while (i != 0);
/* 368 */               HttpConnection.access$200(HttpConnection.this).write(this.hex, cursor, this.hex.length - cursor);
                    }
                }

/* 375 */       private class AbstractSource {
                    protected final Sink cacheBody;
                    private final CacheRequest cacheRequest;
                    protected boolean closed;

/* 376 */           AbstractSource(CacheRequest cacheRequest) throws IOException {
/* 379 */               Sink cacheBody = cacheRequest != null ? cacheRequest.body() : null;
/* 380 */               cacheRequest = cacheBody == null ? null : cacheRequest;
/* 384 */               this.cacheBody = cacheBody;
/* 385 */               this.cacheRequest = cacheRequest;
                    }

/* 389 */           protected final void cacheWrite(Buffer source, long byteCount) throws IOException {
/* 390 */               if (this.cacheBody != null) {
/* 392 */                   Buffer sourceCopy = source.m2481clone();
/* 393 */                   sourceCopy.skip(sourceCopy.size() - byteCount);
/* 394 */                   this.cacheBody.write(sourceCopy, byteCount);
                        }
                    }

/* 402 */           protected final void endOfInput(boolean recyclable) throws IOException {
/* 403 */               if (HttpConnection.access$300(HttpConnection.this) != 5) {
                            throw new IllegalStateException("state: " + HttpConnection.access$300(HttpConnection.this));
                        }
/* 405 */               if (this.cacheRequest != null) {
/* 406 */                   this.cacheBody.close();
                        }
/* 409 */               HttpConnection.access$302(HttpConnection.this, 0);
/* 410 */               if (recyclable && HttpConnection.access$600(HttpConnection.this) == 1) {
/* 411 */                   HttpConnection.access$602(HttpConnection.this, 0);
/* 412 */                   Internal.instance.recycle(HttpConnection.access$700(HttpConnection.this), HttpConnection.access$800(HttpConnection.this));
                        } else if (HttpConnection.access$600(HttpConnection.this) == 2) {
/* 414 */                   HttpConnection.access$302(HttpConnection.this, 6);
/* 415 */                   HttpConnection.access$800(HttpConnection.this).getSocket().close();
                        }
                    }

/* 431 */           protected final void unexpectedEndOfInput() {
/* 432 */               if (this.cacheRequest != null) {
/* 433 */                   this.cacheRequest.abort();
                        }
/* 435 */               Util.closeQuietly(HttpConnection.access$800(HttpConnection.this).getSocket());
/* 436 */               HttpConnection.access$302(HttpConnection.this, 6);
                    }
                }

/* 442 */       private class FixedLengthSource extends AbstractSource implements Source {
                    private long bytesRemaining;

/* 443 */           public FixedLengthSource(CacheRequest cacheRequest, long length) throws IOException {
/* 445 */               super(cacheRequest);
/* 446 */               this.bytesRemaining = length;
                        if (this.bytesRemaining == 0) {
/* 448 */                   endOfInput(true);
                        }
                    }

                    @Override // okio.Source
/* 453 */           public long read(Buffer sink, long byteCount) throws IOException {
                        if (byteCount < 0) {
                            throw new IllegalArgumentException("byteCount < 0: " + byteCount);
                        }
/* 455 */               if (this.closed) {
                            throw new IllegalStateException("closed");
                        }
                        if (this.bytesRemaining == 0) {
                            return -1L;
                        }
/* 458 */               long read = HttpConnection.access$900(HttpConnection.this).read(sink, Math.min(this.bytesRemaining, byteCount));
                        if (read == -1) {
/* 460 */                   unexpectedEndOfInput();
/* 461 */                   throw new ProtocolException("unexpected end of stream");
                        }
/* 464 */               this.bytesRemaining -= read;
/* 465 */               cacheWrite(sink, read);
                        if (this.bytesRemaining == 0) {
/* 467 */                   endOfInput(true);
/* 469 */                   return read;
                        }
/* 469 */               return read;
                    }

                    @Override // okio.Source
/* 472 */           public Timeout timeout() {
/* 473 */               return HttpConnection.access$900(HttpConnection.this).timeout();
                    }

                    @Override // okio.Source, java.io.Closeable, java.lang.AutoCloseable
/* 476 */           public void close() throws IOException {
/* 477 */               if (!this.closed) {
                            if (this.bytesRemaining != 0 && !HttpConnection.this.discard(this, 100)) {
/* 480 */                       unexpectedEndOfInput();
                            }
/* 483 */                   this.closed = true;
                        }
                    }
                }

/* 492 */       private class ChunkedSource extends AbstractSource implements Source {
                    private static final int NO_CHUNK_YET = -1;
                    private int bytesRemainingInChunk;
                    private boolean hasMoreChunks;
                    private final HttpEngine httpEngine;

/* 493 */           ChunkedSource(CacheRequest cacheRequest, HttpEngine httpEngine) throws IOException {
/* 495 */               super(cacheRequest);
/* 490 */               this.bytesRemainingInChunk = -1;
/* 491 */               this.hasMoreChunks = true;
/* 496 */               this.httpEngine = httpEngine;
                    }

                    @Override // okio.Source
/* 500 */           public long read(Buffer sink, long byteCount) throws IOException {
                        if (byteCount < 0) {
                            throw new IllegalArgumentException("byteCount < 0: " + byteCount);
                        }
/* 502 */               if (this.closed) {
                            throw new IllegalStateException("closed");
                        }
/* 503 */               if (!this.hasMoreChunks) {
                            return -1L;
                        }
/* 505 */               if (this.bytesRemainingInChunk == 0 || this.bytesRemainingInChunk == -1) {
/* 506 */                   readChunkSize();
/* 507 */                   if (!this.hasMoreChunks) {
                                return -1L;
                            }
                        }
/* 510 */               long read = HttpConnection.access$900(HttpConnection.this).read(sink, Math.min(byteCount, this.bytesRemainingInChunk));
                        if (read == -1) {
/* 512 */                   unexpectedEndOfInput();
/* 513 */                   throw new IOException("unexpected end of stream");
                        }
/* 515 */               this.bytesRemainingInChunk = (int) (((long) this.bytesRemainingInChunk) - read);
/* 516 */               cacheWrite(sink, read);
/* 517 */               return read;
                    }

/* 521 */           private void readChunkSize() throws IOException {
/* 522 */               if (this.bytesRemainingInChunk != -1) {
/* 523 */                   HttpConnection.access$900(HttpConnection.this).readUtf8LineStrict();
                        }
/* 525 */               String chunkSizeString = HttpConnection.access$900(HttpConnection.this).readUtf8LineStrict();
/* 526 */               int index = chunkSizeString.indexOf(";");
/* 527 */               if (index != -1) {
/* 528 */                   chunkSizeString = chunkSizeString.substring(0, index);
                        }
                        try {
/* 531 */                   this.bytesRemainingInChunk = Integer.parseInt(chunkSizeString.trim(), 16);
/* 535 */                   if (this.bytesRemainingInChunk == 0) {
/* 536 */                       this.hasMoreChunks = false;
/* 537 */                       Headers.Builder trailersBuilder = new Headers.Builder();
/* 538 */                       HttpConnection.this.readHeaders(trailersBuilder);
/* 539 */                       this.httpEngine.receiveHeaders(trailersBuilder.build());
/* 540 */                       endOfInput(true);
                            }
                        } catch (NumberFormatException e) {
/* 533 */                   throw new ProtocolException("Expected a hex chunk size but was " + chunkSizeString);
                        }
                    }

                    @Override // okio.Source
/* 544 */           public Timeout timeout() {
/* 545 */               return HttpConnection.access$900(HttpConnection.this).timeout();
                    }

                    @Override // okio.Source, java.io.Closeable, java.lang.AutoCloseable
/* 548 */           public void close() throws IOException {
/* 549 */               if (!this.closed) {
/* 550 */                   if (this.hasMoreChunks && !HttpConnection.this.discard(this, 100)) {
/* 551 */                       unexpectedEndOfInput();
                            }
/* 553 */                   this.closed = true;
                        }
                    }
                }

/* 559 */       class UnknownLengthSource extends AbstractSource implements Source {
                    private boolean inputExhausted;

/* 560 */           UnknownLengthSource(CacheRequest cacheRequest) throws IOException {
/* 562 */               super(cacheRequest);
                    }

                    @Override // okio.Source
/* 566 */           public long read(Buffer sink, long byteCount) throws IOException {
                        if (byteCount < 0) {
                            throw new IllegalArgumentException("byteCount < 0: " + byteCount);
                        }
/* 568 */               if (this.closed) {
                            throw new IllegalStateException("closed");
                        }
/* 569 */               if (this.inputExhausted) {
                            return -1L;
                        }
/* 571 */               long read = HttpConnection.access$900(HttpConnection.this).read(sink, byteCount);
                        if (read == -1) {
/* 573 */                   this.inputExhausted = true;
/* 574 */                   endOfInput(false);
                            return -1L;
                        }
/* 577 */               cacheWrite(sink, read);
/* 578 */               return read;
                    }

                    @Override // okio.Source
/* 581 */           public Timeout timeout() {
/* 582 */               return HttpConnection.access$900(HttpConnection.this).timeout();
                    }

                    @Override // okio.Source, java.io.Closeable, java.lang.AutoCloseable
/* 585 */           public void close() throws IOException {
/* 586 */               if (!this.closed) {
/* 588 */                   if (!this.inputExhausted) {
/* 589 */                       unexpectedEndOfInput();
                            }
/* 591 */                   this.closed = true;
                        }
                    }
                }
            }
