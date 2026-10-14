            package com.squareup.okhttp;

            import com.squareup.okhttp.internal.Internal;
            import com.squareup.okhttp.internal.InternalCache;
            import com.squareup.okhttp.internal.Network;
            import com.squareup.okhttp.internal.RouteDatabase;
            import com.squareup.okhttp.internal.Util;
            import com.squareup.okhttp.internal.http.AuthenticatorAdapter;
            import com.squareup.okhttp.internal.http.HttpEngine;
            import com.squareup.okhttp.internal.http.Transport;
            import com.squareup.okhttp.internal.tls.OkHostnameVerifier;
            import java.io.IOException;
            import java.net.CookieHandler;
            import java.net.Proxy;
            import java.net.ProxySelector;
            import java.security.GeneralSecurityException;
            import java.util.List;
            import java.util.concurrent.TimeUnit;
            import javax.net.SocketFactory;
            import javax.net.ssl.HostnameVerifier;
            import javax.net.ssl.SSLContext;
            import javax.net.ssl.SSLSocketFactory;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 49 */    public class OkHttpClient implements Cloneable {
                private static SSLSocketFactory defaultSslSocketFactory;
                private Authenticator authenticator;
                private Cache cache;
                private CertificatePinner certificatePinner;
                private int connectTimeout;
                private ConnectionPool connectionPool;
                private List<ConnectionSpec> connectionSpecs;
                private CookieHandler cookieHandler;
                private Dispatcher dispatcher;
                private boolean followRedirects;
                private boolean followSslRedirects;
                private HostnameVerifier hostnameVerifier;
                private InternalCache internalCache;
                private Network network;
                private List<Protocol> protocols;
                private Proxy proxy;
                private ProxySelector proxySelector;
                private int readTimeout;
                private final RouteDatabase routeDatabase;
                private SocketFactory socketFactory;
                private SSLSocketFactory sslSocketFactory;
                private int writeTimeout;
                private static final List<Protocol> DEFAULT_PROTOCOLS = Util.immutableList(Protocol.HTTP_2, Protocol.SPDY_3, Protocol.HTTP_1_1);
                private static final List<ConnectionSpec> DEFAULT_CONNECTION_SPECS = Util.immutableList(ConnectionSpec.MODERN_TLS, ConnectionSpec.COMPATIBLE_TLS, ConnectionSpec.CLEARTEXT);

/* 50 */        static /* synthetic */ Network access$000(OkHttpClient x0) {
/* 51 */            return x0.network;
                }

/* 50 */        static /* synthetic */ Network access$002(OkHttpClient x0, Network x1) {
/* 51 */            x0.network = x1;
                    return x1;
                }

                /* JADX INFO: renamed from: clone, reason: collision with other method in class */

                static {
/* 59 */            Internal.instance = new C12641();
                }

                /* JADX INFO: renamed from: com.squareup.okhttp.OkHttpClient$1 */
/* 57 */        static class C12641 extends Internal {
/* 58 */            C12641() {
                    }

                    @Override // com.squareup.okhttp.internal.Internal
/* 61 */            public Transport newTransport(Connection connection, HttpEngine httpEngine) throws IOException {
/* 62 */                return connection.newTransport(httpEngine);
                    }

                    @Override // com.squareup.okhttp.internal.Internal
/* 65 */            public boolean clearOwner(Connection connection) {
/* 66 */                return connection.clearOwner();
                    }

                    @Override // com.squareup.okhttp.internal.Internal
/* 69 */            public void closeIfOwnedBy(Connection connection, Object owner) throws IOException {
/* 70 */                connection.closeIfOwnedBy(owner);
                    }

                    @Override // com.squareup.okhttp.internal.Internal
/* 73 */            public int recycleCount(Connection connection) {
/* 74 */                return connection.recycleCount();
                    }

                    @Override // com.squareup.okhttp.internal.Internal
/* 77 */            public void setProtocol(Connection connection, Protocol protocol) {
/* 78 */                connection.setProtocol(protocol);
                    }

                    @Override // com.squareup.okhttp.internal.Internal
/* 81 */            public void setOwner(Connection connection, HttpEngine httpEngine) {
/* 82 */                connection.setOwner(httpEngine);
                    }

                    @Override // com.squareup.okhttp.internal.Internal
/* 85 */            public boolean isReadable(Connection pooled) {
/* 86 */                return pooled.isReadable();
                    }

                    @Override // com.squareup.okhttp.internal.Internal
/* 89 */            public void addLine(Headers.Builder builder, String line) {
/* 90 */                builder.addLine(line);
                    }

                    @Override // com.squareup.okhttp.internal.Internal
/* 93 */            public void setCache(OkHttpClient client, InternalCache internalCache) {
/* 94 */                client.setInternalCache(internalCache);
                    }

                    @Override // com.squareup.okhttp.internal.Internal
/* 97 */            public InternalCache internalCache(OkHttpClient client) {
/* 98 */                return client.internalCache();
                    }

                    @Override // com.squareup.okhttp.internal.Internal
/* 101 */           public void recycle(ConnectionPool pool, Connection connection) {
/* 102 */               pool.recycle(connection);
                    }

                    @Override // com.squareup.okhttp.internal.Internal
/* 105 */           public RouteDatabase routeDatabase(OkHttpClient client) {
/* 106 */               return client.routeDatabase();
                    }

                    @Override // com.squareup.okhttp.internal.Internal
/* 109 */           public Network network(OkHttpClient client) {
/* 110 */               return OkHttpClient.access$000(client);
                    }

                    @Override // com.squareup.okhttp.internal.Internal
/* 113 */           public void setNetwork(OkHttpClient client, Network network) {
/* 114 */               OkHttpClient.access$002(client, network);
                    }

                    @Override // com.squareup.okhttp.internal.Internal
/* 118 */           public void connectAndSetOwner(OkHttpClient client, Connection connection, HttpEngine owner, Request request) throws IOException {
/* 119 */               connection.connectAndSetOwner(client, owner, request);
                    }
                }

/* 151 */       public OkHttpClient() {
/* 146 */           this.followSslRedirects = true;
/* 147 */           this.followRedirects = true;
/* 153 */           this.routeDatabase = new RouteDatabase();
/* 154 */           this.dispatcher = new Dispatcher();
                }

/* 156 */       private OkHttpClient(OkHttpClient okHttpClient) {
/* 146 */           this.followSslRedirects = true;
/* 147 */           this.followRedirects = true;
/* 158 */           this.routeDatabase = okHttpClient.routeDatabase;
/* 159 */           this.dispatcher = okHttpClient.dispatcher;
/* 160 */           this.proxy = okHttpClient.proxy;
/* 161 */           this.protocols = okHttpClient.protocols;
/* 162 */           this.connectionSpecs = okHttpClient.connectionSpecs;
/* 163 */           this.proxySelector = okHttpClient.proxySelector;
/* 164 */           this.cookieHandler = okHttpClient.cookieHandler;
/* 165 */           this.cache = okHttpClient.cache;
/* 166 */           this.internalCache = this.cache != null ? this.cache.internalCache : okHttpClient.internalCache;
/* 167 */           this.socketFactory = okHttpClient.socketFactory;
/* 168 */           this.sslSocketFactory = okHttpClient.sslSocketFactory;
/* 169 */           this.hostnameVerifier = okHttpClient.hostnameVerifier;
/* 170 */           this.certificatePinner = okHttpClient.certificatePinner;
/* 171 */           this.authenticator = okHttpClient.authenticator;
/* 172 */           this.connectionPool = okHttpClient.connectionPool;
/* 173 */           this.network = okHttpClient.network;
/* 174 */           this.followSslRedirects = okHttpClient.followSslRedirects;
/* 175 */           this.followRedirects = okHttpClient.followRedirects;
/* 176 */           this.connectTimeout = okHttpClient.connectTimeout;
/* 177 */           this.readTimeout = okHttpClient.readTimeout;
/* 178 */           this.writeTimeout = okHttpClient.writeTimeout;
                }

/* 186 */       public final void setConnectTimeout(long timeout, TimeUnit unit) {
                    if (timeout < 0) {
                        throw new IllegalArgumentException("timeout < 0");
                    }
/* 188 */           if (unit == null) {
                        throw new IllegalArgumentException("unit == null");
                    }
/* 189 */           long millis = unit.toMillis(timeout);
                    if (millis > 2147483647L) {
                        throw new IllegalArgumentException("Timeout too large.");
                    }
/* 191 */           this.connectTimeout = (int) millis;
                }

/* 195 */       public final int getConnectTimeout() {
/* 196 */           return this.connectTimeout;
                }

/* 204 */       public final void setReadTimeout(long timeout, TimeUnit unit) {
                    if (timeout < 0) {
                        throw new IllegalArgumentException("timeout < 0");
                    }
/* 206 */           if (unit == null) {
                        throw new IllegalArgumentException("unit == null");
                    }
/* 207 */           long millis = unit.toMillis(timeout);
                    if (millis > 2147483647L) {
                        throw new IllegalArgumentException("Timeout too large.");
                    }
/* 209 */           this.readTimeout = (int) millis;
                }

/* 213 */       public final int getReadTimeout() {
/* 214 */           return this.readTimeout;
                }

/* 220 */       public final void setWriteTimeout(long timeout, TimeUnit unit) {
                    if (timeout < 0) {
                        throw new IllegalArgumentException("timeout < 0");
                    }
/* 222 */           if (unit == null) {
                        throw new IllegalArgumentException("unit == null");
                    }
/* 223 */           long millis = unit.toMillis(timeout);
                    if (millis > 2147483647L) {
                        throw new IllegalArgumentException("Timeout too large.");
                    }
/* 225 */           this.writeTimeout = (int) millis;
                }

/* 229 */       public final int getWriteTimeout() {
/* 230 */           return this.writeTimeout;
                }

/* 239 */       public final OkHttpClient setProxy(Proxy proxy) {
/* 240 */           this.proxy = proxy;
/* 241 */           return this;
                }

/* 244 */       public final Proxy getProxy() {
/* 245 */           return this.proxy;
                }

/* 257 */       public final OkHttpClient setProxySelector(ProxySelector proxySelector) {
/* 258 */           this.proxySelector = proxySelector;
/* 259 */           return this;
                }

/* 262 */       public final ProxySelector getProxySelector() {
/* 263 */           return this.proxySelector;
                }

/* 273 */       public final OkHttpClient setCookieHandler(CookieHandler cookieHandler) {
/* 274 */           this.cookieHandler = cookieHandler;
/* 275 */           return this;
                }

/* 278 */       public final CookieHandler getCookieHandler() {
/* 279 */           return this.cookieHandler;
                }

/* 283 */       final void setInternalCache(InternalCache internalCache) {
/* 284 */           this.internalCache = internalCache;
/* 285 */           this.cache = null;
                }

/* 288 */       final InternalCache internalCache() {
/* 289 */           return this.internalCache;
                }

/* 292 */       public final OkHttpClient setCache(Cache cache) {
/* 293 */           this.cache = cache;
/* 294 */           this.internalCache = null;
/* 295 */           return this;
                }

/* 298 */       public final Cache getCache() {
/* 299 */           return this.cache;
                }

/* 308 */       public final OkHttpClient setSocketFactory(SocketFactory socketFactory) {
/* 309 */           this.socketFactory = socketFactory;
/* 310 */           return this;
                }

/* 313 */       public final SocketFactory getSocketFactory() {
/* 314 */           return this.socketFactory;
                }

/* 322 */       public final OkHttpClient setSslSocketFactory(SSLSocketFactory sslSocketFactory) {
/* 323 */           this.sslSocketFactory = sslSocketFactory;
/* 324 */           return this;
                }

/* 327 */       public final SSLSocketFactory getSslSocketFactory() {
/* 328 */           return this.sslSocketFactory;
                }

/* 337 */       public final OkHttpClient setHostnameVerifier(HostnameVerifier hostnameVerifier) {
/* 338 */           this.hostnameVerifier = hostnameVerifier;
/* 339 */           return this;
                }

/* 342 */       public final HostnameVerifier getHostnameVerifier() {
/* 343 */           return this.hostnameVerifier;
                }

/* 352 */       public final OkHttpClient setCertificatePinner(CertificatePinner certificatePinner) {
/* 353 */           this.certificatePinner = certificatePinner;
/* 354 */           return this;
                }

/* 357 */       public final CertificatePinner getCertificatePinner() {
/* 358 */           return this.certificatePinner;
                }

/* 368 */       public final OkHttpClient setAuthenticator(Authenticator authenticator) {
/* 369 */           this.authenticator = authenticator;
/* 370 */           return this;
                }

/* 373 */       public final Authenticator getAuthenticator() {
/* 374 */           return this.authenticator;
                }

/* 383 */       public final OkHttpClient setConnectionPool(ConnectionPool connectionPool) {
/* 384 */           this.connectionPool = connectionPool;
/* 385 */           return this;
                }

/* 388 */       public final ConnectionPool getConnectionPool() {
/* 389 */           return this.connectionPool;
                }

/* 399 */       public final OkHttpClient setFollowSslRedirects(boolean followProtocolRedirects) {
/* 400 */           this.followSslRedirects = followProtocolRedirects;
/* 401 */           return this;
                }

/* 404 */       public final boolean getFollowSslRedirects() {
/* 405 */           return this.followSslRedirects;
                }

/* 409 */       public final void setFollowRedirects(boolean followRedirects) {
/* 410 */           this.followRedirects = followRedirects;
                }

/* 413 */       public final boolean getFollowRedirects() {
/* 414 */           return this.followRedirects;
                }

/* 417 */       final RouteDatabase routeDatabase() {
/* 418 */           return this.routeDatabase;
                }

/* 425 */       public final OkHttpClient setDispatcher(Dispatcher dispatcher) {
/* 426 */           if (dispatcher == null) {
                        throw new IllegalArgumentException("dispatcher == null");
                    }
/* 427 */           this.dispatcher = dispatcher;
/* 428 */           return this;
                }

/* 431 */       public final Dispatcher getDispatcher() {
/* 432 */           return this.dispatcher;
                }

/* 466 */       public final OkHttpClient setProtocols(List<Protocol> protocols) {
/* 467 */           List<Protocol> protocols2 = Util.immutableList(protocols);
/* 468 */           if (!protocols2.contains(Protocol.HTTP_1_1)) {
/* 469 */               throw new IllegalArgumentException("protocols doesn't contain http/1.1: " + protocols2);
                    }
/* 471 */           if (protocols2.contains(Protocol.HTTP_1_0)) {
/* 472 */               throw new IllegalArgumentException("protocols must not contain http/1.0: " + protocols2);
                    }
/* 474 */           if (protocols2.contains(null)) {
/* 475 */               throw new IllegalArgumentException("protocols must not contain null");
                    }
/* 477 */           this.protocols = Util.immutableList(protocols2);
/* 478 */           return this;
                }

/* 481 */       public final List<Protocol> getProtocols() {
/* 482 */           return this.protocols;
                }

/* 485 */       public final OkHttpClient setConnectionSpecs(List<ConnectionSpec> connectionSpecs) {
/* 486 */           this.connectionSpecs = Util.immutableList(connectionSpecs);
/* 487 */           return this;
                }

/* 490 */       public final List<ConnectionSpec> getConnectionSpecs() {
/* 491 */           return this.connectionSpecs;
                }

/* 497 */       public Call newCall(Request request) {
/* 498 */           return new Call(this, request);
                }

/* 505 */       public OkHttpClient cancel(Object tag) {
/* 506 */           getDispatcher().cancel(tag);
/* 507 */           return this;
                }

/* 514 */       final OkHttpClient copyWithDefaults() {
/* 515 */           OkHttpClient result = new OkHttpClient(this);
/* 516 */           if (result.proxySelector == null) {
/* 517 */               result.proxySelector = ProxySelector.getDefault();
                    }
/* 519 */           if (result.cookieHandler == null) {
/* 520 */               result.cookieHandler = CookieHandler.getDefault();
                    }
/* 522 */           if (result.socketFactory == null) {
/* 523 */               result.socketFactory = SocketFactory.getDefault();
                    }
/* 525 */           if (result.sslSocketFactory == null) {
/* 526 */               result.sslSocketFactory = getDefaultSSLSocketFactory();
                    }
/* 528 */           if (result.hostnameVerifier == null) {
/* 529 */               result.hostnameVerifier = OkHostnameVerifier.INSTANCE;
                    }
/* 531 */           if (result.certificatePinner == null) {
/* 532 */               result.certificatePinner = CertificatePinner.DEFAULT;
                    }
/* 534 */           if (result.authenticator == null) {
/* 535 */               result.authenticator = AuthenticatorAdapter.INSTANCE;
                    }
/* 537 */           if (result.connectionPool == null) {
/* 538 */               result.connectionPool = ConnectionPool.getDefault();
                    }
/* 540 */           if (result.protocols == null) {
/* 541 */               result.protocols = DEFAULT_PROTOCOLS;
                    }
/* 543 */           if (result.connectionSpecs == null) {
/* 544 */               result.connectionSpecs = DEFAULT_CONNECTION_SPECS;
                    }
/* 546 */           if (result.network == null) {
/* 547 */               result.network = Network.DEFAULT;
                    }
/* 549 */           return result;
                }

/* 563 */       private synchronized SSLSocketFactory getDefaultSSLSocketFactory() {
                    try {
                        if (defaultSslSocketFactory == null) {
                            try {
/* 566 */                       SSLContext sslContext = SSLContext.getInstance("TLS");
/* 567 */                       sslContext.init(null, null, null);
/* 568 */                       defaultSslSocketFactory = sslContext.getSocketFactory();
                            } catch (GeneralSecurityException e) {
/* 570 */                       throw new AssertionError();
                            }
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
/* 573 */           return defaultSslSocketFactory;
                }

/* 578 */       public final OkHttpClient clone() {
                    try {
/* 579 */               return (OkHttpClient) super.clone();
                    } catch (CloneNotSupportedException e) {
/* 581 */               throw new AssertionError();
                    }
                }
            }
