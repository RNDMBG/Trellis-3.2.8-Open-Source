            package com.squareup.okhttp.internal.http;

            import com.squareup.okhttp.Request;
            import com.squareup.okhttp.Response;
            import java.io.IOException;
            import okio.Sink;
            import okio.Source;
            
            
    public final class HttpTransport implements Transport {
                private final HttpConnection httpConnection;
                private final HttpEngine httpEngine;

        public HttpTransport(HttpEngine httpEngine, HttpConnection httpConnection) {
            this.httpEngine = httpEngine;
            this.httpConnection = httpConnection;
                }

                @Override 
        public Sink createRequestBody(Request request, long contentLength) throws IOException {
            if ("chunked".equalsIgnoreCase(request.header("Transfer-Encoding"))) {
                return this.httpConnection.newChunkedSink();
                    }
                    if (contentLength != -1) {
                return this.httpConnection.newFixedLengthSink(contentLength);
                    }
            throw new IllegalStateException("Cannot stream a request body without chunked encoding or a known content length!");
                }

                @Override 
        public void flushRequest() throws IOException {
            this.httpConnection.flush();
                }

                @Override 
        public void writeRequestBody(RetryableSink requestBody) throws IOException {
            this.httpConnection.writeRequestBody(requestBody);
                }

                @Override 
        public void writeRequestHeaders(Request request) throws IOException {
            this.httpEngine.writingRequestHeaders();
            String requestLine = RequestLine.get(request, this.httpEngine.getConnection().getRoute().getProxy().type(), this.httpEngine.getConnection().getProtocol());
            this.httpConnection.writeRequest(request.headers(), requestLine);
                }

                @Override 
        public Response.Builder readResponseHeaders() throws IOException {
            return this.httpConnection.readResponse();
                }

                @Override 
        public void releaseConnectionOnIdle() throws IOException {
            if (canReuseConnection()) {
                this.httpConnection.poolOnIdle();
                    } else {
                this.httpConnection.closeOnIdle();
                    }
                }

                @Override 
        public boolean canReuseConnection() {
                    return ("close".equalsIgnoreCase(this.httpEngine.getRequest().header("Connection")) || "close".equalsIgnoreCase(this.httpEngine.getResponse().header("Connection")) || this.httpConnection.isClosed()) ? false : true;
                }

                @Override 
       public void emptyTransferStream() throws IOException {
           this.httpConnection.emptyResponseBody();
                }

                @Override 
       public Source getTransferStream(CacheRequest cacheRequest) throws IOException {
           if (!this.httpEngine.hasResponseBody()) {
               return this.httpConnection.newFixedLengthSource(cacheRequest, 0L);
                    }
           if ("chunked".equalsIgnoreCase(this.httpEngine.getResponse().header("Transfer-Encoding"))) {
               return this.httpConnection.newChunkedSource(cacheRequest, this.httpEngine);
                    }
           long contentLength = OkHeaders.contentLength(this.httpEngine.getResponse());
                    if (contentLength != -1) {
               return this.httpConnection.newFixedLengthSource(cacheRequest, contentLength);
                    }
           return this.httpConnection.newUnknownLengthSource(cacheRequest);
                }

                @Override 
       public void disconnect(HttpEngine engine) throws IOException {
           this.httpConnection.closeIfOwnedBy(engine);
                }
            }
