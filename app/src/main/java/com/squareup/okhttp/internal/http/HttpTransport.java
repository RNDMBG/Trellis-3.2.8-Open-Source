            package com.squareup.okhttp.internal.http;

            import com.squareup.okhttp.Request;
            import com.squareup.okhttp.Response;
            import java.io.IOException;
            import okio.Sink;
            import okio.Source;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 27 */    public final class HttpTransport implements Transport {
                private final HttpConnection httpConnection;
                private final HttpEngine httpEngine;

/* 28 */        public HttpTransport(HttpEngine httpEngine, HttpConnection httpConnection) {
/* 30 */            this.httpEngine = httpEngine;
/* 31 */            this.httpConnection = httpConnection;
                }

                @Override // com.squareup.okhttp.internal.http.Transport
/* 34 */        public Sink createRequestBody(Request request, long contentLength) throws IOException {
/* 35 */            if ("chunked".equalsIgnoreCase(request.header("Transfer-Encoding"))) {
/* 37 */                return this.httpConnection.newChunkedSink();
                    }
                    if (contentLength != -1) {
/* 42 */                return this.httpConnection.newFixedLengthSink(contentLength);
                    }
/* 45 */            throw new IllegalStateException("Cannot stream a request body without chunked encoding or a known content length!");
                }

                @Override // com.squareup.okhttp.internal.http.Transport
/* 49 */        public void flushRequest() throws IOException {
/* 50 */            this.httpConnection.flush();
                }

                @Override // com.squareup.okhttp.internal.http.Transport
/* 53 */        public void writeRequestBody(RetryableSink requestBody) throws IOException {
/* 54 */            this.httpConnection.writeRequestBody(requestBody);
                }

                @Override // com.squareup.okhttp.internal.http.Transport
/* 69 */        public void writeRequestHeaders(Request request) throws IOException {
/* 70 */            this.httpEngine.writingRequestHeaders();
/* 71 */            String requestLine = RequestLine.get(request, this.httpEngine.getConnection().getRoute().getProxy().type(), this.httpEngine.getConnection().getProtocol());
/* 74 */            this.httpConnection.writeRequest(request.headers(), requestLine);
                }

                @Override // com.squareup.okhttp.internal.http.Transport
/* 77 */        public Response.Builder readResponseHeaders() throws IOException {
/* 78 */            return this.httpConnection.readResponse();
                }

                @Override // com.squareup.okhttp.internal.http.Transport
/* 81 */        public void releaseConnectionOnIdle() throws IOException {
/* 82 */            if (canReuseConnection()) {
/* 83 */                this.httpConnection.poolOnIdle();
                    } else {
/* 85 */                this.httpConnection.closeOnIdle();
                    }
                }

                @Override // com.squareup.okhttp.internal.http.Transport
/* 90 */        public boolean canReuseConnection() {
                    return ("close".equalsIgnoreCase(this.httpEngine.getRequest().header("Connection")) || "close".equalsIgnoreCase(this.httpEngine.getResponse().header("Connection")) || this.httpConnection.isClosed()) ? false : true;
                }

                @Override // com.squareup.okhttp.internal.http.Transport
/* 107 */       public void emptyTransferStream() throws IOException {
/* 108 */           this.httpConnection.emptyResponseBody();
                }

                @Override // com.squareup.okhttp.internal.http.Transport
/* 111 */       public Source getTransferStream(CacheRequest cacheRequest) throws IOException {
/* 112 */           if (!this.httpEngine.hasResponseBody()) {
/* 113 */               return this.httpConnection.newFixedLengthSource(cacheRequest, 0L);
                    }
/* 116 */           if ("chunked".equalsIgnoreCase(this.httpEngine.getResponse().header("Transfer-Encoding"))) {
/* 117 */               return this.httpConnection.newChunkedSource(cacheRequest, this.httpEngine);
                    }
/* 120 */           long contentLength = OkHeaders.contentLength(this.httpEngine.getResponse());
                    if (contentLength != -1) {
/* 122 */               return this.httpConnection.newFixedLengthSource(cacheRequest, contentLength);
                    }
/* 128 */           return this.httpConnection.newUnknownLengthSource(cacheRequest);
                }

                @Override // com.squareup.okhttp.internal.http.Transport
/* 131 */       public void disconnect(HttpEngine engine) throws IOException {
/* 132 */           this.httpConnection.closeIfOwnedBy(engine);
                }
            }
