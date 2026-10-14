            package com.squareup.okhttp.internal.http;

            import com.googlecode.javacv.cpp.avutil;
            import com.squareup.okhttp.Connection;
            import com.squareup.okhttp.Headers;
            import com.squareup.okhttp.MediaType;
            import com.squareup.okhttp.OkHttpClient;
            import com.squareup.okhttp.Protocol;
            import com.squareup.okhttp.Request;
            import com.squareup.okhttp.Response;
            import com.squareup.okhttp.ResponseBody;
            import com.squareup.okhttp.Route;
            import com.squareup.okhttp.internal.Internal;
            import com.squareup.okhttp.internal.InternalCache;
            import com.squareup.okhttp.internal.Util;
            import com.squareup.okhttp.internal.Version;
            import java.io.IOException;
            import java.io.InputStream;
            import java.net.CookieHandler;
            import java.net.ProtocolException;
            import java.net.Proxy;
            import java.net.URL;
            import java.security.cert.CertificateException;
            import java.util.Date;
            import java.util.List;
            import java.util.Map;
            import javax.net.ssl.SSLHandshakeException;
            import javax.net.ssl.SSLPeerUnverifiedException;
            import okio.Buffer;
            import okio.BufferedSink;
            import okio.BufferedSource;
            import okio.GzipSource;
            import okio.Okio;
            import okio.Sink;
            import okio.Source;
            import org.scribe.model.OAuthConstants;
            import twitter4j.internal.http.HttpResponseCode;
            
            
    public final class HttpEngine {
                private static final ResponseBody EMPTY_BODY = new C12771();
                public static final int MAX_REDIRECTS = 20;
                public final boolean bufferRequestBody;
                private BufferedSink bufferedRequestBody;
                private Response cacheResponse;
                private CacheStrategy cacheStrategy;
                final OkHttpClient client;
                private Connection connection;
                private Request networkRequest;
                private Response networkResponse;
                private final Response priorResponse;
                private Sink requestBodyOut;
                private BufferedSource responseBody;
                private InputStream responseBodyBytes;
                private Source responseTransferSource;
                private Route route;
                private RouteSelector routeSelector;
                long sentRequestMillis = -1;
                private CacheRequest storeRequest;
                private boolean transparentGzip;
                private Transport transport;
                private final Request userRequest;
                private Response userResponse;

                
        static class C12771 extends ResponseBody {
            C12771() {
                    }

                    @Override 
            public MediaType contentType() {
                return null;
                    }

                    @Override 
            public long contentLength() {
                return 0L;
                    }

                    @Override 
            public BufferedSource source() {
               return new Buffer();
                    }
                }

       public HttpEngine(OkHttpClient client, Request request, boolean bufferRequestBody, Connection connection, RouteSelector routeSelector, RetryableSink requestBodyOut, Response priorResponse) {
           this.client = client;
           this.userRequest = request;
           this.bufferRequestBody = bufferRequestBody;
           this.connection = connection;
           this.routeSelector = routeSelector;
           this.requestBodyOut = requestBodyOut;
           this.priorResponse = priorResponse;
           if (connection != null) {
               Internal.instance.setOwner(connection, this);
               this.route = connection.getRoute();
                    } else {
               this.route = null;
                    }
                }

       public void sendRequest() throws IOException {
           if (this.cacheStrategy == null) {
               if (this.transport != null) {
                            throw new IllegalStateException();
                        }
               Request request = networkRequest(this.userRequest);
               InternalCache responseCache = Internal.instance.internalCache(this.client);
               Response cacheCandidate = responseCache != null ? responseCache.get(request) : null;
               long now = System.currentTimeMillis();
               this.cacheStrategy = new CacheStrategy.Factory(now, request, cacheCandidate).get();
               this.networkRequest = this.cacheStrategy.networkRequest;
               this.cacheResponse = this.cacheStrategy.cacheResponse;
               if (responseCache != null) {
                   responseCache.trackResponse(this.cacheStrategy);
                        }
               if (cacheCandidate != null && this.cacheResponse == null) {
                   Util.closeQuietly(cacheCandidate.body());
                        }
               if (this.networkRequest != null) {
                   if (this.connection == null) {
                       connect(this.networkRequest);
                            }
                   this.transport = Internal.instance.newTransport(this.connection, this);
                   if (permitsRequestBody() && this.requestBodyOut == null) {
                       long contentLength = OkHeaders.contentLength(request);
                       if (!this.bufferRequestBody) {
                           this.transport.writeRequestHeaders(request);
                           this.requestBodyOut = this.transport.createRequestBody(request, contentLength);
                           return;
                                } else {
                                    if (contentLength > 2147483647L) {
                               throw new IllegalStateException("Use setFixedLengthStreamingMode() or setChunkedStreamingMode() for requests larger than 2 GiB.");
                                    }
                                    if (contentLength != -1) {
                               this.transport.writeRequestHeaders(request);
                               this.requestBodyOut = new RetryableSink((int) contentLength);
                               return;
                                    } else {
                               this.requestBodyOut = new RetryableSink();
                               return;
                                    }
                                }
                            }
                   return;
                        }
               if (this.connection != null) {
                   Internal.instance.recycle(this.client.getConnectionPool(), this.connection);
                   this.connection = null;
                        }
               if (this.cacheResponse != null) {
                   this.userResponse = this.cacheResponse.newBuilder().request(this.userRequest).priorResponse(stripBody(this.priorResponse)).cacheResponse(stripBody(this.cacheResponse)).build();
                        } else {
                   this.userResponse = new Response.Builder().request(this.userRequest).priorResponse(stripBody(this.priorResponse)).protocol(Protocol.HTTP_1_1).code(HttpResponseCode.GATEWAY_TIMEOUT).message("Unsatisfiable Request (only-if-cached)").body(EMPTY_BODY).build();
                        }
               if (this.userResponse.body() != null) {
                   initContentStream(this.userResponse.body().source());
                        }
                    }
                }

       private static Response stripBody(Response response) {
                    return (response == null || response.body() == null) ? response : response.newBuilder().body(null).build();
                }

       private void connect(Request request) throws IOException {
           if (this.connection != null) {
                        throw new IllegalStateException();
                    }
           if (this.routeSelector == null) {
               this.routeSelector = RouteSelector.get(request, this.client);
                    }
           this.connection = this.routeSelector.next(this);
           this.route = this.connection.getRoute();
                }

       public void writingRequestHeaders() {
                    if (this.sentRequestMillis != -1) {
                        throw new IllegalStateException();
                    }
           this.sentRequestMillis = System.currentTimeMillis();
                }

       boolean permitsRequestBody() {
           return HttpMethod.permitsRequestBody(this.userRequest.method());
                }

       public Sink getRequestBody() {
           if (this.cacheStrategy == null) {
                        throw new IllegalStateException();
                    }
           return this.requestBodyOut;
                }

       public BufferedSink getBufferedRequestBody() {
                    BufferedSink result;
           BufferedSink result2 = this.bufferedRequestBody;
           if (result2 == null) {
               Sink requestBody = getRequestBody();
               if (requestBody != null) {
                            result = Okio.buffer(requestBody);
                            this.bufferedRequestBody = result;
                        } else {
                            result = null;
                        }
                        return result;
                    }
           return result2;
                }

       public boolean hasResponse() {
                    return this.userResponse != null;
                }

       public Request getRequest() {
           return this.userRequest;
                }

       public Response getResponse() {
           if (this.userResponse == null) {
                        throw new IllegalStateException();
                    }
           return this.userResponse;
                }

       public BufferedSource getResponseBody() {
           if (this.userResponse == null) {
                        throw new IllegalStateException();
                    }
           return this.responseBody;
                }

       public InputStream getResponseBodyBytes() {
           InputStream result = this.responseBodyBytes;
           if (result != null) {
                        return result;
                    }
                    InputStream result2 = Okio.buffer(getResponseBody()).inputStream();
                    this.responseBodyBytes = result2;
                    return result2;
                }

       public Connection getConnection() {
           return this.connection;
                }

       public HttpEngine recover(IOException e, Sink requestBodyOut) {
           if (this.routeSelector != null && this.connection != null) {
               this.routeSelector.connectFailed(this.connection, e);
                    }
           boolean canRetryRequestBody = requestBodyOut == null || (requestBodyOut instanceof RetryableSink);
           if ((this.routeSelector == null && this.connection == null) || ((this.routeSelector != null && !this.routeSelector.hasNext()) || !isRecoverable(e) || !canRetryRequestBody)) {
               return null;
                    }
           Connection connection = close();
           return new HttpEngine(this.client, this.userRequest, this.bufferRequestBody, connection, this.routeSelector, (RetryableSink) requestBodyOut, this.priorResponse);
                }

       public HttpEngine recover(IOException e) {
           return recover(e, this.requestBodyOut);
                }

       private boolean isRecoverable(IOException e) {
           boolean sslFailure = (e instanceof SSLPeerUnverifiedException) || ((e instanceof SSLHandshakeException) && (e.getCause() instanceof CertificateException));
           boolean protocolFailure = e instanceof ProtocolException;
                    return (sslFailure || protocolFailure) ? false : true;
                }

       public Route getRoute() {
           return this.route;
                }

       private void maybeCache() throws IOException {
           InternalCache responseCache = Internal.instance.internalCache(this.client);
           if (responseCache != null) {
               if (!CacheStrategy.isCacheable(this.userResponse, this.networkRequest)) {
                   if (HttpMethod.invalidatesCache(this.networkRequest.method())) {
                                try {
                           responseCache.remove(this.networkRequest);
                           return;
                                } catch (IOException e) {
                           return;
                                }
                            }
                   return;
                        }
               this.storeRequest = responseCache.put(stripBody(this.userResponse));
                    }
                }

       public void releaseConnection() throws IOException {
           if (this.transport != null && this.connection != null) {
               this.transport.releaseConnectionOnIdle();
                    }
           this.connection = null;
                }

       public void disconnect() {
           if (this.transport != null) {
                        try {
                   this.transport.disconnect(this);
                        } catch (IOException e) {
                        }
                    }
                }

       public Connection close() {
           if (this.bufferedRequestBody != null) {
               Util.closeQuietly(this.bufferedRequestBody);
                    } else if (this.requestBodyOut != null) {
               Util.closeQuietly(this.requestBodyOut);
                    }
           if (this.responseBody == null) {
               if (this.connection != null) {
                            Util.closeQuietly(this.connection.getSocket());
                        }
               this.connection = null;
                        return null;
                    }
           Util.closeQuietly(this.responseBody);
           Util.closeQuietly(this.responseBodyBytes);
           if (this.transport != null && this.connection != null && !this.transport.canReuseConnection()) {
               Util.closeQuietly(this.connection.getSocket());
               this.connection = null;
                        return null;
                    }
           if (this.connection != null && !Internal.instance.clearOwner(this.connection)) {
               this.connection = null;
                    }
           Connection connection = this.connection;
           this.connection = null;
           return connection;
                }

       private void initContentStream(Source transferSource) throws IOException {
           this.responseTransferSource = transferSource;
           if (this.transparentGzip && "gzip".equalsIgnoreCase(this.userResponse.header("Content-Encoding"))) {
               this.userResponse = this.userResponse.newBuilder().removeHeader("Content-Encoding").removeHeader("Content-Length").build();
               this.responseBody = Okio.buffer(new GzipSource(transferSource));
                    } else {
               this.responseBody = Okio.buffer(transferSource);
                    }
                }

       public boolean hasResponseBody() {
           if (this.userRequest.method().equals("HEAD")) {
                        return false;
                    }
           int responseCode = this.userResponse.code();
           if ((responseCode >= 100 && responseCode < 200) || responseCode == 204 || responseCode == 304) {
                        return OkHeaders.contentLength(this.networkResponse) != -1 || "chunked".equalsIgnoreCase(this.networkResponse.header("Transfer-Encoding"));
                    }
                    return true;
                }

       private Request networkRequest(Request request) throws IOException {
           Request.Builder result = request.newBuilder();
           if (request.header("Host") == null) {
               result.header("Host", hostHeader(request.url()));
                    }
           if ((this.connection == null || this.connection.getProtocol() != Protocol.HTTP_1_0) && request.header("Connection") == null) {
               result.header("Connection", "Keep-Alive");
                    }
           if (request.header("Accept-Encoding") == null) {
               this.transparentGzip = true;
               result.header("Accept-Encoding", "gzip");
                    }
           CookieHandler cookieHandler = this.client.getCookieHandler();
           if (cookieHandler != null) {
               Map<String, List<String>> headers = OkHeaders.toMultimap(result.build().headers(), null);
               Map<String, List<String>> cookies = cookieHandler.get(request.uri(), headers);
               OkHeaders.addCookies(result, cookies);
                    }
           if (request.header("User-Agent") == null) {
               result.header("User-Agent", Version.userAgent());
                    }
           return result.build();
                }

       public static String hostHeader(URL url) {
                    return Util.getEffectivePort(url) != Util.getDefaultPort(url.getProtocol()) ? url.getHost() + ":" + url.getPort() : url.getHost();
                }

       public void readResponse() throws IOException {
           if (this.userResponse == null) {
               if (this.networkRequest == null && this.cacheResponse == null) {
                   throw new IllegalStateException("call sendRequest() first!");
                        }
               if (this.networkRequest != null) {
                   if (this.bufferedRequestBody != null && this.bufferedRequestBody.buffer().size() > 0) {
                       this.bufferedRequestBody.flush();
                            }
                            if (this.sentRequestMillis == -1) {
                                if (OkHeaders.contentLength(this.networkRequest) == -1 && (this.requestBodyOut instanceof RetryableSink)) {
                           long contentLength = ((RetryableSink) this.requestBodyOut).contentLength();
                           this.networkRequest = this.networkRequest.newBuilder().header("Content-Length", Long.toString(contentLength)).build();
                                }
                       this.transport.writeRequestHeaders(this.networkRequest);
                            }
                   if (this.requestBodyOut != null) {
                       if (this.bufferedRequestBody != null) {
                           this.bufferedRequestBody.close();
                                } else {
                           this.requestBodyOut.close();
                                }
                       if (this.requestBodyOut instanceof RetryableSink) {
                           this.transport.writeRequestBody((RetryableSink) this.requestBodyOut);
                                }
                            }
                   this.transport.flushRequest();
                   this.networkResponse = this.transport.readResponseHeaders().request(this.networkRequest).handshake(this.connection.getHandshake()).header(OkHeaders.SENT_MILLIS, Long.toString(this.sentRequestMillis)).header(OkHeaders.RECEIVED_MILLIS, Long.toString(System.currentTimeMillis())).build();
                   Internal.instance.setProtocol(this.connection, this.networkResponse.protocol());
                   receiveHeaders(this.networkResponse.headers());
                   if (this.cacheResponse != null) {
                       if (validate(this.cacheResponse, this.networkResponse)) {
                           this.userResponse = this.cacheResponse.newBuilder().request(this.userRequest).priorResponse(stripBody(this.priorResponse)).headers(combine(this.cacheResponse.headers(), this.networkResponse.headers())).cacheResponse(stripBody(this.cacheResponse)).networkResponse(stripBody(this.networkResponse)).build();
                           this.transport.emptyTransferStream();
                           releaseConnection();
                           InternalCache responseCache = Internal.instance.internalCache(this.client);
                           responseCache.trackConditionalCacheHit();
                           responseCache.update(this.cacheResponse, stripBody(this.userResponse));
                           if (this.cacheResponse.body() != null) {
                               initContentStream(this.cacheResponse.body().source());
                               return;
                                    }
                           return;
                                }
                       Util.closeQuietly(this.cacheResponse.body());
                            }
                   this.userResponse = this.networkResponse.newBuilder().request(this.userRequest).priorResponse(stripBody(this.priorResponse)).cacheResponse(stripBody(this.cacheResponse)).networkResponse(stripBody(this.networkResponse)).build();
                   if (!hasResponseBody()) {
                       this.responseTransferSource = this.transport.getTransferStream(this.storeRequest);
                       this.responseBody = Okio.buffer(this.responseTransferSource);
                            } else {
                       maybeCache();
                       initContentStream(this.transport.getTransferStream(this.storeRequest));
                            }
                        }
                    }
                }

       private static boolean validate(Response cached, Response network) {
                    Date networkLastModified;
           if (network.code() == 304) {
                        return true;
                    }
           Date lastModified = cached.headers().getDate("Last-Modified");
                    return (lastModified == null || (networkLastModified = network.headers().getDate("Last-Modified")) == null || networkLastModified.getTime() >= lastModified.getTime()) ? false : true;
                }

       private static Headers combine(Headers cachedHeaders, Headers networkHeaders) throws IOException {
           Headers.Builder result = new Headers.Builder();
                    for (int i = 0; i < cachedHeaders.size(); i++) {
               String fieldName = cachedHeaders.name(i);
               String value = cachedHeaders.value(i);
               if ((!"Warning".equalsIgnoreCase(fieldName) || !value.startsWith("1")) && (!OkHeaders.isEndToEnd(fieldName) || networkHeaders.get(fieldName) == null)) {
                   result.add(fieldName, value);
                        }
                    }
                    for (int i2 = 0; i2 < networkHeaders.size(); i2++) {
               String fieldName2 = networkHeaders.name(i2);
               if (!"Content-Length".equalsIgnoreCase(fieldName2) && OkHeaders.isEndToEnd(fieldName2)) {
                   result.add(fieldName2, networkHeaders.value(i2));
                        }
                    }
           return result.build();
                }

       public void receiveHeaders(Headers headers) throws IOException {
           CookieHandler cookieHandler = this.client.getCookieHandler();
           if (cookieHandler != null) {
               cookieHandler.put(this.userRequest.uri(), OkHeaders.toMultimap(headers, null));
                    }
                }

       public Request followUpRequest() throws IOException {
                    String location;
           if (this.userResponse == null) {
                        throw new IllegalStateException();
                    }
           Proxy selectedProxy = getRoute() != null ? getRoute().getProxy() : this.client.getProxy();
           int responseCode = this.userResponse.code();
                    switch (responseCode) {
                        case 307:
                        case 308:
                   if (!this.userRequest.method().equals("GET") && !this.userRequest.method().equals("HEAD")) {
                                return null;
                            }
                        case 300:
                        case avutil.AV_PIX_FMT_YUV420P12BE :
                        case 302:
                        case avutil.AV_PIX_FMT_YUV420P14BE :
                   if (!this.client.getFollowRedirects() || (location = this.userResponse.header("Location")) == null) {
                                return null;
                            }
                   URL url = new URL(this.userRequest.url(), location);
                   if (!url.getProtocol().equals("https") && !url.getProtocol().equals("http")) {
                                return null;
                            }
                   boolean sameProtocol = url.getProtocol().equals(this.userRequest.url().getProtocol());
                   if (!sameProtocol && !this.client.getFollowSslRedirects()) {
                                return null;
                            }
                   Request.Builder requestBuilder = this.userRequest.newBuilder();
                   if (HttpMethod.permitsRequestBody(this.userRequest.method())) {
                       requestBuilder.method("GET", null);
                       requestBuilder.removeHeader("Transfer-Encoding");
                       requestBuilder.removeHeader("Content-Length");
                       requestBuilder.removeHeader("Content-Type");
                            }
                   if (!sameConnection(url)) {
                       requestBuilder.removeHeader(OAuthConstants.HEADER);
                            }
                   return requestBuilder.url(url).build();
                        case 407:
                   if (selectedProxy.type() != Proxy.Type.HTTP) {
                       throw new ProtocolException("Received HTTP_PROXY_AUTH (407) code while not using proxy");
                            }
                        case 401:
                   return OkHeaders.processAuthHeader(this.client.getAuthenticator(), this.userResponse, selectedProxy);
                        default:
                            return null;
                    }
                }

       public boolean sameConnection(URL followUp) {
           URL url = this.userRequest.url();
                    return url.getHost().equals(followUp.getHost()) && Util.getEffectivePort(url) == Util.getEffectivePort(followUp) && url.getProtocol().equals(followUp.getProtocol());
                }
            }
