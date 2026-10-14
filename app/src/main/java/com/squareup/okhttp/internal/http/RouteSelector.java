            package com.squareup.okhttp.internal.http;

            import com.squareup.okhttp.Address;
            import com.squareup.okhttp.CertificatePinner;
            import com.squareup.okhttp.Connection;
            import com.squareup.okhttp.ConnectionPool;
            import com.squareup.okhttp.ConnectionSpec;
            import com.squareup.okhttp.OkHttpClient;
            import com.squareup.okhttp.Request;
            import com.squareup.okhttp.Route;
            import com.squareup.okhttp.internal.Internal;
            import com.squareup.okhttp.internal.Network;
            import com.squareup.okhttp.internal.RouteDatabase;
            import com.squareup.okhttp.internal.Util;
            import java.io.IOException;
            import java.net.InetAddress;
            import java.net.InetSocketAddress;
            import java.net.Proxy;
            import java.net.ProxySelector;
            import java.net.SocketAddress;
            import java.net.SocketException;
            import java.net.URI;
            import java.net.UnknownHostException;
            import java.util.ArrayList;
            import java.util.Collections;
            import java.util.List;
            import java.util.NoSuchElementException;
            import javax.net.ssl.HostnameVerifier;
            import javax.net.ssl.SSLHandshakeException;
            import javax.net.ssl.SSLProtocolException;
            import javax.net.ssl.SSLSocketFactory;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 82 */    public final class RouteSelector {
                private final Address address;
                private final OkHttpClient client;
                private InetSocketAddress lastInetSocketAddress;
                private Proxy lastProxy;
                private ConnectionSpec lastSpec;
                private final Network network;
                private int nextInetSocketAddressIndex;
                private int nextProxyIndex;
                private int nextSpecIndex;
                private final ConnectionPool pool;
                private final ProxySelector proxySelector;
                private final Request request;
                private final RouteDatabase routeDatabase;
                private final URI uri;
                private List<Proxy> proxies = Collections.emptyList();
                private List<InetSocketAddress> inetSocketAddresses = Collections.emptyList();
                private List<ConnectionSpec> connectionSpecs = Collections.emptyList();
                private final List<Route> postponedRoutes = new ArrayList();

/* 83 */        private RouteSelector(Address address, URI uri, OkHttpClient client, Request request) {
/* 85 */            this.address = address;
/* 86 */            this.uri = uri;
/* 87 */            this.client = client;
/* 88 */            this.proxySelector = client.getProxySelector();
/* 89 */            this.pool = client.getConnectionPool();
/* 90 */            this.routeDatabase = Internal.instance.routeDatabase(client);
/* 91 */            this.network = Internal.instance.network(client);
/* 92 */            this.request = request;
/* 94 */            resetNextProxy(uri, address.getProxy());
                }

/* 97 */        public static RouteSelector get(Request request, OkHttpClient client) throws IOException {
/* 98 */            String uriHost = request.url().getHost();
/* 99 */            if (uriHost == null || uriHost.length() == 0) {
/* 100 */               throw new UnknownHostException(request.url().toString());
                    }
/* 103 */           SSLSocketFactory sslSocketFactory = null;
/* 104 */           HostnameVerifier hostnameVerifier = null;
/* 105 */           CertificatePinner certificatePinner = null;
/* 106 */           if (request.isHttps()) {
/* 107 */               sslSocketFactory = client.getSslSocketFactory();
/* 108 */               hostnameVerifier = client.getHostnameVerifier();
/* 109 */               certificatePinner = client.getCertificatePinner();
                    }
/* 112 */           Address address = new Address(uriHost, Util.getEffectivePort(request.url()), client.getSocketFactory(), sslSocketFactory, hostnameVerifier, certificatePinner, client.getAuthenticator(), client.getProxy(), client.getProtocols(), client.getConnectionSpecs());
/* 117 */           return new RouteSelector(address, request.uri(), client, request);
                }

/* 124 */       public boolean hasNext() {
                    return hasNextConnectionSpec() || hasNextInetSocketAddress() || hasNextProxy() || hasNextPostponed();
                }

/* 132 */       public Connection next(HttpEngine owner) throws IOException {
/* 133 */           Connection connection = nextUnconnected();
/* 134 */           Internal.instance.connectAndSetOwner(this.client, connection, owner, this.request);
/* 135 */           return connection;
                }

/* 144 */       Connection nextUnconnected() throws IOException {
                    while (true) {
/* 145 */               Connection pooled = this.pool.get(this.address);
                        if (pooled != null) {
/* 146 */                   if (!this.request.method().equals("GET") && !Internal.instance.isReadable(pooled)) {
/* 147 */                       pooled.getSocket().close();
                            } else {
/* 172 */                       return pooled;
                            }
                        } else {
/* 151 */                   if (!hasNextConnectionSpec()) {
/* 152 */                       if (!hasNextInetSocketAddress()) {
/* 153 */                           if (!hasNextProxy()) {
/* 154 */                               if (!hasNextPostponed()) {
/* 155 */                                   throw new NoSuchElementException();
                                        }
/* 157 */                               return new Connection(this.pool, nextPostponed());
                                    }
/* 159 */                           this.lastProxy = nextProxy();
                                }
/* 161 */                       this.lastInetSocketAddress = nextInetSocketAddress();
                            }
/* 163 */                   this.lastSpec = nextConnectionSpec();
/* 165 */                   Route route = new Route(this.address, this.lastProxy, this.lastInetSocketAddress, this.lastSpec);
/* 166 */                   if (this.routeDatabase.shouldPostpone(route)) {
/* 167 */                       this.postponedRoutes.add(route);
/* 169 */                       return nextUnconnected();
                            }
/* 172 */                   return new Connection(this.pool, route);
                        }
                    }
                }

/* 180 */       public void connectFailed(Connection connection, IOException failure) {
/* 181 */           if (Internal.instance.recycleCount(connection) <= 0) {
/* 183 */               Route failedRoute = connection.getRoute();
/* 184 */               if (failedRoute.getProxy().type() != Proxy.Type.DIRECT && this.proxySelector != null) {
/* 186 */                   this.proxySelector.connectFailed(this.uri, failedRoute.getProxy().address(), failure);
                        }
/* 189 */               this.routeDatabase.failed(failedRoute);
/* 194 */               if (!(failure instanceof SSLHandshakeException) && !(failure instanceof SSLProtocolException)) {
/* 195 */                   while (this.nextSpecIndex < this.connectionSpecs.size()) {
                                Address address = this.address;
                                Proxy proxy = this.lastProxy;
                                InetSocketAddress inetSocketAddress = this.lastInetSocketAddress;
                                List<ConnectionSpec> list = this.connectionSpecs;
                                int i = this.nextSpecIndex;
                                this.nextSpecIndex = i + 1;
/* 196 */                       Route toSuppress = new Route(address, proxy, inetSocketAddress, list.get(i));
/* 198 */                       this.routeDatabase.failed(toSuppress);
                            }
                        }
                    }
                }

/* 204 */       private void resetNextProxy(URI uri, Proxy proxy) {
/* 205 */           if (proxy != null) {
/* 207 */               this.proxies = Collections.singletonList(proxy);
                    } else {
/* 211 */               this.proxies = new ArrayList();
/* 212 */               List<Proxy> selectedProxies = this.proxySelector.select(uri);
/* 213 */               if (selectedProxies != null) {
                            this.proxies.addAll(selectedProxies);
                        }
/* 215 */               this.proxies.removeAll(Collections.singleton(Proxy.NO_PROXY));
/* 216 */               this.proxies.add(Proxy.NO_PROXY);
                    }
/* 218 */           this.nextProxyIndex = 0;
                }

/* 222 */       private boolean hasNextProxy() {
                    return this.nextProxyIndex < this.proxies.size();
                }

/* 227 */       private Proxy nextProxy() throws IOException {
/* 228 */           if (!hasNextProxy()) {
/* 229 */               throw new SocketException("No route to " + this.address.getUriHost() + "; exhausted proxy configurations: " + this.proxies);
                    }
/* 232 */           List<Proxy> list = this.proxies;
                    int i = this.nextProxyIndex;
                    this.nextProxyIndex = i + 1;
                    Proxy result = list.get(i);
/* 233 */           resetNextInetSocketAddress(result);
/* 234 */           return result;
                }

/* 239 */       private void resetNextInetSocketAddress(Proxy proxy) throws UnknownHostException {
                    String socketHost;
                    int socketPort;
/* 240 */           this.inetSocketAddresses = new ArrayList();
/* 244 */           if (proxy.type() == Proxy.Type.DIRECT) {
/* 245 */               socketHost = this.address.getUriHost();
/* 246 */               socketPort = Util.getEffectivePort(this.uri);
                    } else {
/* 248 */               SocketAddress proxyAddress = proxy.address();
/* 249 */               if (!(proxyAddress instanceof InetSocketAddress)) {
/* 250 */                   throw new IllegalArgumentException("Proxy.address() is not an InetSocketAddress: " + proxyAddress.getClass());
                        }
/* 253 */               InetSocketAddress proxySocketAddress = (InetSocketAddress) proxyAddress;
/* 254 */               socketHost = proxySocketAddress.getHostName();
/* 255 */               socketPort = proxySocketAddress.getPort();
                    }
/* 259 */           InetAddress[] arr$ = this.network.resolveInetAddresses(socketHost);
                    for (InetAddress inetAddress : arr$) {
/* 260 */               this.inetSocketAddresses.add(new InetSocketAddress(inetAddress, socketPort));
                    }
/* 262 */           this.nextInetSocketAddressIndex = 0;
                }

/* 266 */       private boolean hasNextInetSocketAddress() {
                    return this.nextInetSocketAddressIndex < this.inetSocketAddresses.size();
                }

/* 271 */       private InetSocketAddress nextInetSocketAddress() throws IOException {
/* 272 */           if (!hasNextInetSocketAddress()) {
/* 273 */               throw new SocketException("No route to " + this.address.getUriHost() + "; exhausted inet socket addresses: " + this.inetSocketAddresses);
                    }
/* 276 */           List<InetSocketAddress> list = this.inetSocketAddresses;
                    int i = this.nextInetSocketAddressIndex;
                    this.nextInetSocketAddressIndex = i + 1;
                    InetSocketAddress result = list.get(i);
/* 277 */           resetConnectionSpecs();
/* 278 */           return result;
                }

/* 282 */       private void resetConnectionSpecs() {
/* 283 */           this.connectionSpecs = new ArrayList();
                    for (ConnectionSpec spec : this.address.getConnectionSpecs()) {
/* 285 */               if (this.request.isHttps() == spec.isTls()) {
/* 286 */                   this.connectionSpecs.add(spec);
                        }
                    }
/* 289 */           this.nextSpecIndex = 0;
                }

/* 293 */       private boolean hasNextConnectionSpec() {
                    return this.nextSpecIndex < this.connectionSpecs.size();
                }

/* 298 */       private ConnectionSpec nextConnectionSpec() throws IOException {
/* 299 */           if (!hasNextConnectionSpec()) {
/* 300 */               throw new SocketException("No route to " + this.address.getUriHost() + "; exhausted connection specs: " + this.connectionSpecs);
                    }
/* 303 */           List<ConnectionSpec> list = this.connectionSpecs;
                    int i = this.nextSpecIndex;
                    this.nextSpecIndex = i + 1;
                    return list.get(i);
                }

/* 307 */       private boolean hasNextPostponed() {
                    return !this.postponedRoutes.isEmpty();
                }

/* 312 */       private Route nextPostponed() {
/* 313 */           return this.postponedRoutes.remove(0);
                }
            }
