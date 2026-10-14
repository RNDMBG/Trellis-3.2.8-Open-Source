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
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 89 */    public final class HttpEngine {
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

                /* JADX INFO: renamed from: com.squareup.okhttp.internal.http.HttpEngine$1 */
/* 90 */        static class C12771 extends ResponseBody {
/* 91 */            C12771() {
                    }

                    @Override // com.squareup.okhttp.ResponseBody
/* 93 */            public MediaType contentType() {
/* 94 */                return null;
                    }

                    @Override // com.squareup.okhttp.ResponseBody
/* 96 */            public long contentLength() {
/* 97 */                return 0L;
                    }

                    @Override // com.squareup.okhttp.ResponseBody
/* 99 */            public BufferedSource source() {
/* 100 */               return new Buffer();
                    }
                }

/* 187 */       public HttpEngine(OkHttpClient client, Request request, boolean bufferRequestBody, Connection connection, RouteSelector routeSelector, RetryableSink requestBodyOut, Response priorResponse) {
/* 189 */           this.client = client;
/* 190 */           this.userRequest = request;
/* 191 */           this.bufferRequestBody = bufferRequestBody;
/* 192 */           this.connection = connection;
/* 193 */           this.routeSelector = routeSelector;
/* 194 */           this.requestBodyOut = requestBodyOut;
/* 195 */           this.priorResponse = priorResponse;
/* 197 */           if (connection != null) {
/* 198 */               Internal.instance.setOwner(connection, this);
/* 199 */               this.route = connection.getRoute();
                    } else {
/* 201 */               this.route = null;
                    }
                }

/* 210 */       public void sendRequest() throws IOException {
/* 211 */           if (this.cacheStrategy == null) {
/* 212 */               if (this.transport != null) {
                            throw new IllegalStateException();
                        }
/* 214 */               Request request = networkRequest(this.userRequest);
/* 216 */               InternalCache responseCache = Internal.instance.internalCache(this.client);
/* 217 */               Response cacheCandidate = responseCache != null ? responseCache.get(request) : null;
/* 221 */               long now = System.currentTimeMillis();
/* 222 */               this.cacheStrategy = new CacheStrategy.Factory(now, request, cacheCandidate).get();
/* 223 */               this.networkRequest = this.cacheStrategy.networkRequest;
/* 224 */               this.cacheResponse = this.cacheStrategy.cacheResponse;
/* 226 */               if (responseCache != null) {
/* 227 */                   responseCache.trackResponse(this.cacheStrategy);
                        }
/* 230 */               if (cacheCandidate != null && this.cacheResponse == null) {
/* 231 */                   Util.closeQuietly(cacheCandidate.body());
                        }
/* 234 */               if (this.networkRequest != null) {
/* 236 */                   if (this.connection == null) {
/* 237 */                       connect(this.networkRequest);
                            }
/* 240 */                   this.transport = Internal.instance.newTransport(this.connection, this);
/* 244 */                   if (permitsRequestBody() && this.requestBodyOut == null) {
/* 245 */                       long contentLength = OkHeaders.contentLength(request);
/* 246 */                       if (!this.bufferRequestBody) {
/* 263 */                           this.transport.writeRequestHeaders(request);
/* 264 */                           this.requestBodyOut = this.transport.createRequestBody(request, contentLength);
/* 298 */                           return;
                                } else {
                                    if (contentLength > 2147483647L) {
/* 248 */                               throw new IllegalStateException("Use setFixedLengthStreamingMode() or setChunkedStreamingMode() for requests larger than 2 GiB.");
                                    }
                                    if (contentLength != -1) {
/* 254 */                               this.transport.writeRequestHeaders(request);
/* 255 */                               this.requestBodyOut = new RetryableSink((int) contentLength);
/* 298 */                               return;
                                    } else {
/* 260 */                               this.requestBodyOut = new RetryableSink();
/* 298 */                               return;
                                    }
                                }
                            }
/* 298 */                   return;
                        }
/* 270 */               if (this.connection != null) {
/* 271 */                   Internal.instance.recycle(this.client.getConnectionPool(), this.connection);
/* 272 */                   this.connection = null;
                        }
/* 275 */               if (this.cacheResponse != null) {
/* 277 */                   this.userResponse = this.cacheResponse.newBuilder().request(this.userRequest).priorResponse(stripBody(this.priorResponse)).cacheResponse(stripBody(this.cacheResponse)).build();
                        } else {
/* 284 */                   this.userResponse = new Response.Builder().request(this.userRequest).priorResponse(stripBody(this.priorResponse)).protocol(Protocol.HTTP_1_1).code(HttpResponseCode.GATEWAY_TIMEOUT).message("Unsatisfiable Request (only-if-cached)").body(EMPTY_BODY).build();
                        }
/* 294 */               if (this.userResponse.body() != null) {
/* 295 */                   initContentStream(this.userResponse.body().source());
                        }
                    }
                }

/* 300 */       private static Response stripBody(Response response) {
                    return (response == null || response.body() == null) ? response : response.newBuilder().body(null).build();
                }

/* 307 */       private void connect(Request request) throws IOException {
/* 308 */           if (this.connection != null) {
                        throw new IllegalStateException();
                    }
/* 310 */           if (this.routeSelector == null) {
/* 311 */               this.routeSelector = RouteSelector.get(request, this.client);
                    }
/* 314 */           this.connection = this.routeSelector.next(this);
/* 315 */           this.route = this.connection.getRoute();
                }

/* 322 */       public void writingRequestHeaders() {
                    if (this.sentRequestMillis != -1) {
                        throw new IllegalStateException();
                    }
/* 324 */           this.sentRequestMillis = System.currentTimeMillis();
                }

/* 327 */       boolean permitsRequestBody() {
/* 328 */           return HttpMethod.permitsRequestBody(this.userRequest.method());
                }

/* 332 */       public Sink getRequestBody() {
/* 333 */           if (this.cacheStrategy == null) {
                        throw new IllegalStateException();
                    }
/* 334 */           return this.requestBodyOut;
                }

/* 337 */       public BufferedSink getBufferedRequestBody() {
                    BufferedSink result;
/* 338 */           BufferedSink result2 = this.bufferedRequestBody;
/* 339 */           if (result2 == null) {
/* 340 */               Sink requestBody = getRequestBody();
/* 341 */               if (requestBody != null) {
                            result = Okio.buffer(requestBody);
                            this.bufferedRequestBody = result;
                        } else {
                            result = null;
                        }
                        return result;
                    }
/* 341 */           return result2;
                }

/* 346 */       public boolean hasResponse() {
                    return this.userResponse != null;
                }

/* 350 */       public Request getRequest() {
/* 351 */           return this.userRequest;
                }

/* 356 */       public Response getResponse() {
/* 357 */           if (this.userResponse == null) {
                        throw new IllegalStateException();
                    }
/* 358 */           return this.userResponse;
                }

/* 361 */       public BufferedSource getResponseBody() {
/* 362 */           if (this.userResponse == null) {
                        throw new IllegalStateException();
                    }
/* 363 */           return this.responseBody;
                }

/* 366 */       public InputStream getResponseBodyBytes() {
/* 367 */           InputStream result = this.responseBodyBytes;
/* 368 */           if (result != null) {
                        return result;
                    }
                    InputStream result2 = Okio.buffer(getResponseBody()).inputStream();
                    this.responseBodyBytes = result2;
                    return result2;
                }

/* 373 */       public Connection getConnection() {
/* 374 */           return this.connection;
                }

/* 383 */       public HttpEngine recover(IOException e, Sink requestBodyOut) {
/* 384 */           if (this.routeSelector != null && this.connection != null) {
/* 385 */               this.routeSelector.connectFailed(this.connection, e);
                    }
/* 388 */           boolean canRetryRequestBody = requestBodyOut == null || (requestBodyOut instanceof RetryableSink);
/* 389 */           if ((this.routeSelector == null && this.connection == null) || ((this.routeSelector != null && !this.routeSelector.hasNext()) || !isRecoverable(e) || !canRetryRequestBody)) {
/* 393 */               return null;
                    }
/* 396 */           Connection connection = close();
/* 399 */           return new HttpEngine(this.client, this.userRequest, this.bufferRequestBody, connection, this.routeSelector, (RetryableSink) requestBodyOut, this.priorResponse);
                }

/* 403 */       public HttpEngine recover(IOException e) {
/* 404 */           return recover(e, this.requestBodyOut);
                }

/* 409 */       private boolean isRecoverable(IOException e) {
/* 410 */           boolean sslFailure = (e instanceof SSLPeerUnverifiedException) || ((e instanceof SSLHandshakeException) && (e.getCause() instanceof CertificateException));
/* 412 */           boolean protocolFailure = e instanceof ProtocolException;
                    return (sslFailure || protocolFailure) ? false : true;
                }

/* 420 */       public Route getRoute() {
/* 421 */           return this.route;
                }

/* 424 */       private void maybeCache() throws IOException {
/* 425 */           InternalCache responseCache = Internal.instance.internalCache(this.client);
/* 426 */           if (responseCache != null) {
/* 429 */               if (!CacheStrategy.isCacheable(this.userResponse, this.networkRequest)) {
/* 430 */                   if (HttpMethod.invalidatesCache(this.networkRequest.method())) {
                                try {
/* 432 */                           responseCache.remove(this.networkRequest);
/* 442 */                           return;
                                } catch (IOException e) {
/* 442 */                           return;
                                }
                            }
/* 442 */                   return;
                        }
/* 441 */               this.storeRequest = responseCache.put(stripBody(this.userResponse));
                    }
                }

/* 449 */       public void releaseConnection() throws IOException {
/* 450 */           if (this.transport != null && this.connection != null) {
/* 451 */               this.transport.releaseConnectionOnIdle();
                    }
/* 453 */           this.connection = null;
                }

/* 462 */       public void disconnect() {
/* 463 */           if (this.transport != null) {
                        try {
/* 465 */                   this.transport.disconnect(this);
                        } catch (IOException e) {
                        }
                    }
                }

/* 475 */       public Connection close() {
/* 476 */           if (this.bufferedRequestBody != null) {
/* 478 */               Util.closeQuietly(this.bufferedRequestBody);
                    } else if (this.requestBodyOut != null) {
/* 480 */               Util.closeQuietly(this.requestBodyOut);
                    }
/* 484 */           if (this.responseBody == null) {
/* 485 */               if (this.connection != null) {
                            Util.closeQuietly(this.connection.getSocket());
                        }
/* 486 */               this.connection = null;
                        return null;
                    }
/* 491 */           Util.closeQuietly(this.responseBody);
/* 494 */           Util.closeQuietly(this.responseBodyBytes);
/* 497 */           if (this.transport != null && this.connection != null && !this.transport.canReuseConnection()) {
/* 498 */               Util.closeQuietly(this.connection.getSocket());
/* 499 */               this.connection = null;
                        return null;
                    }
/* 504 */           if (this.connection != null && !Internal.instance.clearOwner(this.connection)) {
/* 505 */               this.connection = null;
                    }
/* 508 */           Connection connection = this.connection;
/* 509 */           this.connection = null;
/* 510 */           return connection;
                }

/* 529 */       private void initContentStream(Source transferSource) throws IOException {
/* 530 */           this.responseTransferSource = transferSource;
/* 531 */           if (this.transparentGzip && "gzip".equalsIgnoreCase(this.userResponse.header("Content-Encoding"))) {
/* 532 */               this.userResponse = this.userResponse.newBuilder().removeHeader("Content-Encoding").removeHeader("Content-Length").build();
/* 536 */               this.responseBody = Okio.buffer(new GzipSource(transferSource));
                    } else {
/* 538 */               this.responseBody = Okio.buffer(transferSource);
                    }
                }

/* 547 */       public boolean hasResponseBody() {
/* 548 */           if (this.userRequest.method().equals("HEAD")) {
                        return false;
                    }
/* 552 */           int responseCode = this.userResponse.code();
/* 553 */           if ((responseCode >= 100 && responseCode < 200) || responseCode == 204 || responseCode == 304) {
                        return OkHeaders.contentLength(this.networkResponse) != -1 || "chunked".equalsIgnoreCase(this.networkResponse.header("Transfer-Encoding"));
                    }
                    return true;
                }

/* 576 */       private Request networkRequest(Request request) throws IOException {
/* 577 */           Request.Builder result = request.newBuilder();
/* 579 */           if (request.header("Host") == null) {
/* 580 */               result.header("Host", hostHeader(request.url()));
                    }
/* 583 */           if ((this.connection == null || this.connection.getProtocol() != Protocol.HTTP_1_0) && request.header("Connection") == null) {
/* 585 */               result.header("Connection", "Keep-Alive");
                    }
/* 588 */           if (request.header("Accept-Encoding") == null) {
/* 589 */               this.transparentGzip = true;
/* 590 */               result.header("Accept-Encoding", "gzip");
                    }
/* 593 */           CookieHandler cookieHandler = this.client.getCookieHandler();
/* 594 */           if (cookieHandler != null) {
/* 598 */               Map<String, List<String>> headers = OkHeaders.toMultimap(result.build().headers(), null);
/* 600 */               Map<String, List<String>> cookies = cookieHandler.get(request.uri(), headers);
/* 603 */               OkHeaders.addCookies(result, cookies);
                    }
/* 606 */           if (request.header("User-Agent") == null) {
/* 607 */               result.header("User-Agent", Version.userAgent());
                    }
/* 610 */           return result.build();
                }

/* 613 */       public static String hostHeader(URL url) {
                    return Util.getEffectivePort(url) != Util.getDefaultPort(url.getProtocol()) ? url.getHost() + ":" + url.getPort() : url.getHost();
                }

/* 623 */       public void readResponse() throws IOException {
/* 624 */           if (this.userResponse == null) {
/* 627 */               if (this.networkRequest == null && this.cacheResponse == null) {
/* 628 */                   throw new IllegalStateException("call sendRequest() first!");
                        }
/* 630 */               if (this.networkRequest != null) {
/* 635 */                   if (this.bufferedRequestBody != null && this.bufferedRequestBody.buffer().size() > 0) {
/* 636 */                       this.bufferedRequestBody.flush();
                            }
                            if (this.sentRequestMillis == -1) {
                                if (OkHeaders.contentLength(this.networkRequest) == -1 && (this.requestBodyOut instanceof RetryableSink)) {
/* 643 */                           long contentLength = ((RetryableSink) this.requestBodyOut).contentLength();
/* 644 */                           this.networkRequest = this.networkRequest.newBuilder().header("Content-Length", Long.toString(contentLength)).build();
                                }
/* 648 */                       this.transport.writeRequestHeaders(this.networkRequest);
                            }
/* 651 */                   if (this.requestBodyOut != null) {
/* 652 */                       if (this.bufferedRequestBody != null) {
/* 654 */                           this.bufferedRequestBody.close();
                                } else {
/* 656 */                           this.requestBodyOut.close();
                                }
/* 658 */                       if (this.requestBodyOut instanceof RetryableSink) {
/* 659 */                           this.transport.writeRequestBody((RetryableSink) this.requestBodyOut);
                                }
                            }
/* 663 */                   this.transport.flushRequest();
/* 665 */                   this.networkResponse = this.transport.readResponseHeaders().request(this.networkRequest).handshake(this.connection.getHandshake()).header(OkHeaders.SENT_MILLIS, Long.toString(this.sentRequestMillis)).header(OkHeaders.RECEIVED_MILLIS, Long.toString(System.currentTimeMillis())).build();
/* 671 */                   Internal.instance.setProtocol(this.connection, this.networkResponse.protocol());
/* 672 */                   receiveHeaders(this.networkResponse.headers());
/* 675 */                   if (this.cacheResponse != null) {
/* 676 */                       if (validate(this.cacheResponse, this.networkResponse)) {
/* 677 */                           this.userResponse = this.cacheResponse.newBuilder().request(this.userRequest).priorResponse(stripBody(this.priorResponse)).headers(combine(this.cacheResponse.headers(), this.networkResponse.headers())).cacheResponse(stripBody(this.cacheResponse)).networkResponse(stripBody(this.networkResponse)).build();
/* 684 */                           this.transport.emptyTransferStream();
/* 685 */                           releaseConnection();
/* 689 */                           InternalCache responseCache = Internal.instance.internalCache(this.client);
/* 690 */                           responseCache.trackConditionalCacheHit();
/* 691 */                           responseCache.update(this.cacheResponse, stripBody(this.userResponse));
/* 693 */                           if (this.cacheResponse.body() != null) {
/* 694 */                               initContentStream(this.cacheResponse.body().source());
/* 718 */                               return;
                                    }
/* 718 */                           return;
                                }
/* 698 */                       Util.closeQuietly(this.cacheResponse.body());
                            }
/* 702 */                   this.userResponse = this.networkResponse.newBuilder().request(this.userRequest).priorResponse(stripBody(this.priorResponse)).cacheResponse(stripBody(this.cacheResponse)).networkResponse(stripBody(this.networkResponse)).build();
/* 709 */                   if (!hasResponseBody()) {
/* 711 */                       this.responseTransferSource = this.transport.getTransferStream(this.storeRequest);
/* 712 */                       this.responseBody = Okio.buffer(this.responseTransferSource);
                            } else {
/* 716 */                       maybeCache();
/* 717 */                       initContentStream(this.transport.getTransferStream(this.storeRequest));
                            }
                        }
                    }
                }

/* 724 */       private static boolean validate(Response cached, Response network) {
                    Date networkLastModified;
/* 725 */           if (network.code() == 304) {
                        return true;
                    }
/* 732 */           Date lastModified = cached.headers().getDate("Last-Modified");
                    return (lastModified == null || (networkLastModified = network.headers().getDate("Last-Modified")) == null || networkLastModified.getTime() >= lastModified.getTime()) ? false : true;
                }

/* 748 */       private static Headers combine(Headers cachedHeaders, Headers networkHeaders) throws IOException {
/* 749 */           Headers.Builder result = new Headers.Builder();
                    for (int i = 0; i < cachedHeaders.size(); i++) {
/* 752 */               String fieldName = cachedHeaders.name(i);
/* 753 */               String value = cachedHeaders.value(i);
/* 754 */               if ((!"Warning".equalsIgnoreCase(fieldName) || !value.startsWith("1")) && (!OkHeaders.isEndToEnd(fieldName) || networkHeaders.get(fieldName) == null)) {
/* 758 */                   result.add(fieldName, value);
                        }
                    }
                    for (int i2 = 0; i2 < networkHeaders.size(); i2++) {
/* 763 */               String fieldName2 = networkHeaders.name(i2);
/* 764 */               if (!"Content-Length".equalsIgnoreCase(fieldName2) && OkHeaders.isEndToEnd(fieldName2)) {
/* 768 */                   result.add(fieldName2, networkHeaders.value(i2));
                        }
                    }
/* 772 */           return result.build();
                }

/* 775 */       public void receiveHeaders(Headers headers) throws IOException {
/* 776 */           CookieHandler cookieHandler = this.client.getCookieHandler();
/* 777 */           if (cookieHandler != null) {
/* 778 */               cookieHandler.put(this.userRequest.uri(), OkHeaders.toMultimap(headers, null));
                    }
                }

/* 787 */       public Request followUpRequest() throws IOException {
                    String location;
/* 788 */           if (this.userResponse == null) {
                        throw new IllegalStateException();
                    }
/* 789 */           Proxy selectedProxy = getRoute() != null ? getRoute().getProxy() : this.client.getProxy();
/* 792 */           int responseCode = this.userResponse.code();
                    switch (responseCode) {
                        case 307:
                        case 308:
/* 807 */                   if (!this.userRequest.method().equals("GET") && !this.userRequest.method().equals("HEAD")) {
                                return null;
                            }
                        case 300:
                        case avutil.AV_PIX_FMT_YUV420P12BE /* 301 */:
                        case 302:
                        case avutil.AV_PIX_FMT_YUV420P14BE /* 303 */:
/* 816 */                   if (!this.client.getFollowRedirects() || (location = this.userResponse.header("Location")) == null) {
                                return null;
                            }
/* 820 */                   URL url = new URL(this.userRequest.url(), location);
/* 823 */                   if (!url.getProtocol().equals("https") && !url.getProtocol().equals("http")) {
                                return null;
                            }
/* 826 */                   boolean sameProtocol = url.getProtocol().equals(this.userRequest.url().getProtocol());
/* 827 */                   if (!sameProtocol && !this.client.getFollowSslRedirects()) {
                                return null;
                            }
/* 830 */                   Request.Builder requestBuilder = this.userRequest.newBuilder();
/* 831 */                   if (HttpMethod.permitsRequestBody(this.userRequest.method())) {
/* 832 */                       requestBuilder.method("GET", null);
/* 833 */                       requestBuilder.removeHeader("Transfer-Encoding");
/* 834 */                       requestBuilder.removeHeader("Content-Length");
/* 835 */                       requestBuilder.removeHeader("Content-Type");
                            }
/* 841 */                   if (!sameConnection(url)) {
/* 842 */                       requestBuilder.removeHeader(OAuthConstants.HEADER);
                            }
/* 845 */                   return requestBuilder.url(url).build();
                        case 407:
/* 796 */                   if (selectedProxy.type() != Proxy.Type.HTTP) {
/* 797 */                       throw new ProtocolException("Received HTTP_PROXY_AUTH (407) code while not using proxy");
                            }
                        case 401:
/* 801 */                   return OkHeaders.processAuthHeader(this.client.getAuthenticator(), this.userResponse, selectedProxy);
                        default:
                            return null;
                    }
                }

/* 856 */       public boolean sameConnection(URL followUp) {
/* 857 */           URL url = this.userRequest.url();
                    return url.getHost().equals(followUp.getHost()) && Util.getEffectivePort(url) == Util.getEffectivePort(followUp) && url.getProtocol().equals(followUp.getProtocol());
                }
            }
