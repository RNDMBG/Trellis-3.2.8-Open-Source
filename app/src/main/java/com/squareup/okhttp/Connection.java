            package com.squareup.okhttp;

            import com.squareup.okhttp.internal.Platform;
            import com.squareup.okhttp.internal.Util;
            import com.squareup.okhttp.internal.http.HttpConnection;
            import com.squareup.okhttp.internal.http.HttpEngine;
            import com.squareup.okhttp.internal.http.HttpTransport;
            import com.squareup.okhttp.internal.http.OkHeaders;
            import com.squareup.okhttp.internal.http.SpdyTransport;
            import com.squareup.okhttp.internal.http.Transport;
            import com.squareup.okhttp.internal.spdy.SpdyConnection;
            import java.io.IOException;
            import java.net.Proxy;
            import java.net.Socket;
            import java.net.URL;
            import javax.net.ssl.SSLSocket;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 82 */    public final class Connection {
                private Handshake handshake;
                private HttpConnection httpConnection;
                private long idleStartTimeNs;
                private Object owner;
                private final ConnectionPool pool;
                private int recycleCount;
                private final Route route;
                private Socket socket;
                private SpdyConnection spdyConnection;
                private boolean connected = false;
                private Protocol protocol = Protocol.HTTP_1_1;

/* 83 */        public Connection(ConnectionPool pool, Route route) {
/* 85 */            this.pool = pool;
/* 86 */            this.route = route;
                }

/* 89 */        Object getOwner() {
                    Object obj;
                    synchronized (this.pool) {
                        try {
/* 91 */                    obj = this.owner;
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                    return obj;
                }

/* 95 */        void setOwner(Object owner) {
/* 96 */            if (!isSpdy()) {
                        synchronized (this.pool) {
                            try {
/* 98 */                        if (this.owner != null) {
                                    throw new IllegalStateException("Connection already has an owner!");
                                }
/* 99 */                        this.owner = owner;
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                    }
                }

/* 109 */       boolean clearOwner() {
                    boolean z;
                    synchronized (this.pool) {
                        try {
/* 111 */                   if (this.owner == null) {
/* 113 */                       z = false;
                            } else {
/* 116 */                       this.owner = null;
/* 117 */                       z = true;
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
/* 117 */           return z;
                }

/* 125 */       void closeIfOwnedBy(Object owner) throws IOException {
/* 126 */           if (isSpdy()) {
                        throw new IllegalStateException();
                    }
                    synchronized (this.pool) {
                        try {
/* 128 */                   if (this.owner == owner) {
/* 132 */                       this.owner = null;
/* 136 */                       this.socket.close();
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }

/* 140 */       void connect(int connectTimeout, int readTimeout, int writeTimeout, Request tunnelRequest) throws IOException {
/* 141 */           if (this.connected) {
                        throw new IllegalStateException("already connected");
                    }
/* 143 */           if (this.route.proxy.type() == Proxy.Type.DIRECT || this.route.proxy.type() == Proxy.Type.HTTP) {
/* 144 */               this.socket = this.route.address.socketFactory.createSocket();
                    } else {
/* 146 */               this.socket = new Socket(this.route.proxy);
                    }
/* 149 */           this.socket.setSoTimeout(readTimeout);
/* 150 */           Platform.get().connectSocket(this.socket, this.route.inetSocketAddress, connectTimeout);
/* 152 */           if (this.route.address.sslSocketFactory != null) {
/* 153 */               upgradeToTls(tunnelRequest, readTimeout, writeTimeout);
                    } else {
/* 155 */               this.httpConnection = new HttpConnection(this.pool, this, this.socket);
                    }
/* 157 */           this.connected = true;
                }

/* 164 */       void connectAndSetOwner(OkHttpClient client, Object owner, Request request) throws IOException {
/* 165 */           setOwner(owner);
/* 167 */           if (!isConnected()) {
/* 168 */               Request tunnelRequest = tunnelRequest(request);
/* 169 */               connect(client.getConnectTimeout(), client.getReadTimeout(), client.getWriteTimeout(), tunnelRequest);
/* 171 */               if (isSpdy()) {
/* 172 */                   client.getConnectionPool().share(this);
                        }
/* 174 */               client.routeDatabase().connected(getRoute());
                    }
/* 177 */           setTimeouts(client.getReadTimeout(), client.getWriteTimeout());
                }

/* 187 */       private Request tunnelRequest(Request request) throws IOException {
/* 188 */           if (!this.route.requiresTunnel()) {
                        return null;
                    }
/* 190 */           String host = request.url().getHost();
/* 191 */           int port = Util.getEffectivePort(request.url());
/* 192 */           String authority = port == Util.getDefaultPort("https") ? host : host + ":" + port;
/* 193 */           Request.Builder result = new Request.Builder().url(new URL("https", host, port, "/")).header("Host", authority).header("Proxy-Connection", "Keep-Alive");
/* 199 */           String userAgent = request.header("User-Agent");
/* 200 */           if (userAgent != null) {
/* 201 */               result.header("User-Agent", userAgent);
                    }
/* 205 */           String proxyAuthorization = request.header("Proxy-Authorization");
/* 206 */           if (proxyAuthorization != null) {
/* 207 */               result.header("Proxy-Authorization", proxyAuthorization);
                    }
/* 210 */           return result.build();
                }

/* 218 */       private void upgradeToTls(Request tunnelRequest, int readTimeout, int writeTimeout) throws IOException {
                    String maybeProtocol;
/* 219 */           Platform platform = Platform.get();
/* 222 */           if (tunnelRequest != null) {
/* 223 */               makeTunnel(tunnelRequest, readTimeout, writeTimeout);
                    }
/* 227 */           this.socket = this.route.address.sslSocketFactory.createSocket(this.socket, this.route.address.uriHost, this.route.address.uriPort, true);
/* 229 */           SSLSocket sslSocket = (SSLSocket) this.socket;
/* 232 */           this.route.connectionSpec.apply(sslSocket, this.route);
/* 235 */           sslSocket.startHandshake();
/* 238 */           if (!this.route.address.hostnameVerifier.verify(this.route.address.uriHost, sslSocket.getSession())) {
/* 239 */               throw new IOException("Hostname '" + this.route.address.uriHost + "' was not verified");
                    }
/* 243 */           this.route.address.certificatePinner.check(this.route.address.uriHost, sslSocket.getSession().getPeerCertificates());
/* 246 */           this.handshake = Handshake.get(sslSocket.getSession());
/* 249 */           if (this.route.connectionSpec.supportsTlsExtensions() && (maybeProtocol = platform.getSelectedProtocol(sslSocket)) != null) {
/* 251 */               this.protocol = Protocol.get(maybeProtocol);
                    }
/* 254 */           if (this.protocol == Protocol.SPDY_3 || this.protocol == Protocol.HTTP_2) {
/* 255 */               sslSocket.setSoTimeout(0);
/* 256 */               this.spdyConnection = new SpdyConnection.Builder(this.route.address.getUriHost(), true, this.socket).protocol(this.protocol).build();
/* 258 */               this.spdyConnection.sendConnectionPreface();
/* 262 */               return;
                    }
/* 260 */           this.httpConnection = new HttpConnection(this.pool, this, this.socket);
                }

/* 265 */       boolean isConnected() {
/* 266 */           return this.connected;
                }

/* 270 */       public Route getRoute() {
/* 271 */           return this.route;
                }

/* 278 */       public Socket getSocket() {
/* 279 */           return this.socket;
                }

/* 283 */       boolean isAlive() {
                    return (this.socket.isClosed() || this.socket.isInputShutdown() || this.socket.isOutputShutdown()) ? false : true;
                }

/* 292 */       boolean isReadable() {
/* 293 */           if (this.httpConnection != null) {
                        return this.httpConnection.isReadable();
                    }
                    return true;
                }

/* 297 */       void resetIdleStartTime() {
/* 298 */           if (this.spdyConnection != null) {
                        throw new IllegalStateException("spdyConnection != null");
                    }
/* 299 */           this.idleStartTimeNs = System.nanoTime();
                }

/* 303 */       boolean isIdle() {
                    return this.spdyConnection == null || this.spdyConnection.isIdle();
                }

/* 311 */       boolean isExpired(long keepAliveDurationNs) {
                    return getIdleStartTimeNs() < System.nanoTime() - keepAliveDurationNs;
                }

/* 319 */       long getIdleStartTimeNs() {
                    return this.spdyConnection == null ? this.idleStartTimeNs : this.spdyConnection.getIdleStartTimeNs();
                }

/* 323 */       public Handshake getHandshake() {
/* 324 */           return this.handshake;
                }

/* 328 */       Transport newTransport(HttpEngine httpEngine) throws IOException {
                    return this.spdyConnection != null ? new SpdyTransport(httpEngine, this.spdyConnection) : new HttpTransport(httpEngine, this.httpConnection);
                }

/* 338 */       boolean isSpdy() {
                    return this.spdyConnection != null;
                }

/* 346 */       public Protocol getProtocol() {
/* 347 */           return this.protocol;
                }

/* 354 */       void setProtocol(Protocol protocol) {
/* 355 */           if (protocol == null) {
                        throw new IllegalArgumentException("protocol == null");
                    }
/* 356 */           this.protocol = protocol;
                }

/* 359 */       void setTimeouts(int readTimeoutMillis, int writeTimeoutMillis) throws IOException {
/* 360 */           if (!this.connected) {
                        throw new IllegalStateException("setTimeouts - not connected");
                    }
/* 363 */           if (this.httpConnection != null) {
/* 364 */               this.socket.setSoTimeout(readTimeoutMillis);
/* 365 */               this.httpConnection.setTimeouts(readTimeoutMillis, writeTimeoutMillis);
                    }
                }

/* 369 */       void incrementRecycleCount() {
/* 370 */           this.recycleCount++;
                }

/* 377 */       int recycleCount() {
/* 378 */           return this.recycleCount;
                }

                /* JADX WARN: Switch 'out' block B:3:0x003b for B:4:0x0058 already processed. Defaulting to fallback option. */
/* 387 */       private void makeTunnel(Request request, int readTimeout, int writeTimeout) throws IOException {
/* 388 */           HttpConnection tunnelConnection = new HttpConnection(this.pool, this, this.socket);
/* 389 */           tunnelConnection.setTimeouts(readTimeout, writeTimeout);
/* 390 */           URL url = request.url();
/* 391 */           String requestLine = "CONNECT " + url.getHost() + ":" + url.getPort() + " HTTP/1.1";
/* 410 */           do {
/* 393 */               tunnelConnection.writeRequest(request.headers(), requestLine);
/* 394 */               tunnelConnection.flush();
/* 395 */               Response response = tunnelConnection.readResponse().request(request).build();
/* 396 */               tunnelConnection.emptyResponseBody();
                        switch (response.code()) {
                            case 200:
                                if (tunnelConnection.bufferSize() > 0) {
/* 403 */                           throw new IOException("TLS tunnel buffered too many bytes!");
                                }
/* 405 */                       return;
                            case 407:
/* 408 */                       request = OkHeaders.processAuthHeader(this.route.address.authenticator, response, this.route.proxy);
                                break;
                            default:
/* 414 */                       throw new IOException("Unexpected response code for CONNECT: " + response.code());
                        }
/* 410 */           } while (request != null);
/* 411 */           throw new IOException("Failed to authenticate with proxy");
                }
            }
