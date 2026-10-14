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
            
            
    public final class Connection {
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

        public Connection(ConnectionPool pool, Route route) {
            this.pool = pool;
            this.route = route;
                }

        Object getOwner() {
                    Object obj;
                    synchronized (this.pool) {
                        try {
                    obj = this.owner;
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                    return obj;
                }

        void setOwner(Object owner) {
            if (!isSpdy()) {
                        synchronized (this.pool) {
                            try {
                        if (this.owner != null) {
                                    throw new IllegalStateException("Connection already has an owner!");
                                }
                        this.owner = owner;
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                    }
                }

       boolean clearOwner() {
                    boolean z;
                    synchronized (this.pool) {
                        try {
                   if (this.owner == null) {
                       z = false;
                            } else {
                       this.owner = null;
                       z = true;
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
           return z;
                }

       void closeIfOwnedBy(Object owner) throws IOException {
           if (isSpdy()) {
                        throw new IllegalStateException();
                    }
                    synchronized (this.pool) {
                        try {
                   if (this.owner == owner) {
                       this.owner = null;
                       this.socket.close();
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }

       void connect(int connectTimeout, int readTimeout, int writeTimeout, Request tunnelRequest) throws IOException {
           if (this.connected) {
                        throw new IllegalStateException("already connected");
                    }
           if (this.route.proxy.type() == Proxy.Type.DIRECT || this.route.proxy.type() == Proxy.Type.HTTP) {
               this.socket = this.route.address.socketFactory.createSocket();
                    } else {
               this.socket = new Socket(this.route.proxy);
                    }
           this.socket.setSoTimeout(readTimeout);
           Platform.get().connectSocket(this.socket, this.route.inetSocketAddress, connectTimeout);
           if (this.route.address.sslSocketFactory != null) {
               upgradeToTls(tunnelRequest, readTimeout, writeTimeout);
                    } else {
               this.httpConnection = new HttpConnection(this.pool, this, this.socket);
                    }
           this.connected = true;
                }

       void connectAndSetOwner(OkHttpClient client, Object owner, Request request) throws IOException {
           setOwner(owner);
           if (!isConnected()) {
               Request tunnelRequest = tunnelRequest(request);
               connect(client.getConnectTimeout(), client.getReadTimeout(), client.getWriteTimeout(), tunnelRequest);
               if (isSpdy()) {
                   client.getConnectionPool().share(this);
                        }
               client.routeDatabase().connected(getRoute());
                    }
           setTimeouts(client.getReadTimeout(), client.getWriteTimeout());
                }

       private Request tunnelRequest(Request request) throws IOException {
           if (!this.route.requiresTunnel()) {
                        return null;
                    }
           String host = request.url().getHost();
           int port = Util.getEffectivePort(request.url());
           String authority = port == Util.getDefaultPort("https") ? host : host + ":" + port;
           Request.Builder result = new Request.Builder().url(new URL("https", host, port, "/")).header("Host", authority).header("Proxy-Connection", "Keep-Alive");
           String userAgent = request.header("User-Agent");
           if (userAgent != null) {
               result.header("User-Agent", userAgent);
                    }
           String proxyAuthorization = request.header("Proxy-Authorization");
           if (proxyAuthorization != null) {
               result.header("Proxy-Authorization", proxyAuthorization);
                    }
           return result.build();
                }

       private void upgradeToTls(Request tunnelRequest, int readTimeout, int writeTimeout) throws IOException {
                    String maybeProtocol;
           Platform platform = Platform.get();
           if (tunnelRequest != null) {
               makeTunnel(tunnelRequest, readTimeout, writeTimeout);
                    }
           this.socket = this.route.address.sslSocketFactory.createSocket(this.socket, this.route.address.uriHost, this.route.address.uriPort, true);
           SSLSocket sslSocket = (SSLSocket) this.socket;
           this.route.connectionSpec.apply(sslSocket, this.route);
           sslSocket.startHandshake();
           if (!this.route.address.hostnameVerifier.verify(this.route.address.uriHost, sslSocket.getSession())) {
               throw new IOException("Hostname '" + this.route.address.uriHost + "' was not verified");
                    }
           this.route.address.certificatePinner.check(this.route.address.uriHost, sslSocket.getSession().getPeerCertificates());
           this.handshake = Handshake.get(sslSocket.getSession());
           if (this.route.connectionSpec.supportsTlsExtensions() && (maybeProtocol = platform.getSelectedProtocol(sslSocket)) != null) {
               this.protocol = Protocol.get(maybeProtocol);
                    }
           if (this.protocol == Protocol.SPDY_3 || this.protocol == Protocol.HTTP_2) {
               sslSocket.setSoTimeout(0);
               this.spdyConnection = new SpdyConnection.Builder(this.route.address.getUriHost(), true, this.socket).protocol(this.protocol).build();
               this.spdyConnection.sendConnectionPreface();
               return;
                    }
           this.httpConnection = new HttpConnection(this.pool, this, this.socket);
                }

       boolean isConnected() {
           return this.connected;
                }

       public Route getRoute() {
           return this.route;
                }

       public Socket getSocket() {
           return this.socket;
                }

       boolean isAlive() {
                    return (this.socket.isClosed() || this.socket.isInputShutdown() || this.socket.isOutputShutdown()) ? false : true;
                }

       boolean isReadable() {
           if (this.httpConnection != null) {
                        return this.httpConnection.isReadable();
                    }
                    return true;
                }

       void resetIdleStartTime() {
           if (this.spdyConnection != null) {
                        throw new IllegalStateException("spdyConnection != null");
                    }
           this.idleStartTimeNs = System.nanoTime();
                }

       boolean isIdle() {
                    return this.spdyConnection == null || this.spdyConnection.isIdle();
                }

       boolean isExpired(long keepAliveDurationNs) {
                    return getIdleStartTimeNs() < System.nanoTime() - keepAliveDurationNs;
                }

       long getIdleStartTimeNs() {
                    return this.spdyConnection == null ? this.idleStartTimeNs : this.spdyConnection.getIdleStartTimeNs();
                }

       public Handshake getHandshake() {
           return this.handshake;
                }

       Transport newTransport(HttpEngine httpEngine) throws IOException {
                    return this.spdyConnection != null ? new SpdyTransport(httpEngine, this.spdyConnection) : new HttpTransport(httpEngine, this.httpConnection);
                }

       boolean isSpdy() {
                    return this.spdyConnection != null;
                }

       public Protocol getProtocol() {
           return this.protocol;
                }

       void setProtocol(Protocol protocol) {
           if (protocol == null) {
                        throw new IllegalArgumentException("protocol == null");
                    }
           this.protocol = protocol;
                }

       void setTimeouts(int readTimeoutMillis, int writeTimeoutMillis) throws IOException {
           if (!this.connected) {
                        throw new IllegalStateException("setTimeouts - not connected");
                    }
           if (this.httpConnection != null) {
               this.socket.setSoTimeout(readTimeoutMillis);
               this.httpConnection.setTimeouts(readTimeoutMillis, writeTimeoutMillis);
                    }
                }

       void incrementRecycleCount() {
           this.recycleCount++;
                }

       int recycleCount() {
           return this.recycleCount;
                }

                
       private void makeTunnel(Request request, int readTimeout, int writeTimeout) throws IOException {
           HttpConnection tunnelConnection = new HttpConnection(this.pool, this, this.socket);
           tunnelConnection.setTimeouts(readTimeout, writeTimeout);
           URL url = request.url();
           String requestLine = "CONNECT " + url.getHost() + ":" + url.getPort() + " HTTP/1.1";
           do {
               tunnelConnection.writeRequest(request.headers(), requestLine);
               tunnelConnection.flush();
               Response response = tunnelConnection.readResponse().request(request).build();
               tunnelConnection.emptyResponseBody();
                        switch (response.code()) {
                            case 200:
                                if (tunnelConnection.bufferSize() > 0) {
                           throw new IOException("TLS tunnel buffered too many bytes!");
                                }
                       return;
                            case 407:
                       request = OkHeaders.processAuthHeader(this.route.address.authenticator, response, this.route.proxy);
                                break;
                            default:
                       throw new IOException("Unexpected response code for CONNECT: " + response.code());
                        }
           } while (request != null);
           throw new IOException("Failed to authenticate with proxy");
                }
            }
