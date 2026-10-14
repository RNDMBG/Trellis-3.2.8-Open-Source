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
            
            
    public final class RouteSelector {
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

        private RouteSelector(Address address, URI uri, OkHttpClient client, Request request) {
            this.address = address;
            this.uri = uri;
            this.client = client;
            this.proxySelector = client.getProxySelector();
            this.pool = client.getConnectionPool();
            this.routeDatabase = Internal.instance.routeDatabase(client);
            this.network = Internal.instance.network(client);
            this.request = request;
            resetNextProxy(uri, address.getProxy());
                }

        public static RouteSelector get(Request request, OkHttpClient client) throws IOException {
            String uriHost = request.url().getHost();
            if (uriHost == null || uriHost.length() == 0) {
               throw new UnknownHostException(request.url().toString());
                    }
           SSLSocketFactory sslSocketFactory = null;
           HostnameVerifier hostnameVerifier = null;
           CertificatePinner certificatePinner = null;
           if (request.isHttps()) {
               sslSocketFactory = client.getSslSocketFactory();
               hostnameVerifier = client.getHostnameVerifier();
               certificatePinner = client.getCertificatePinner();
                    }
           Address address = new Address(uriHost, Util.getEffectivePort(request.url()), client.getSocketFactory(), sslSocketFactory, hostnameVerifier, certificatePinner, client.getAuthenticator(), client.getProxy(), client.getProtocols(), client.getConnectionSpecs());
           return new RouteSelector(address, request.uri(), client, request);
                }

       public boolean hasNext() {
                    return hasNextConnectionSpec() || hasNextInetSocketAddress() || hasNextProxy() || hasNextPostponed();
                }

       public Connection next(HttpEngine owner) throws IOException {
           Connection connection = nextUnconnected();
           Internal.instance.connectAndSetOwner(this.client, connection, owner, this.request);
           return connection;
                }

       Connection nextUnconnected() throws IOException {
                    while (true) {
               Connection pooled = this.pool.get(this.address);
                        if (pooled != null) {
                   if (!this.request.method().equals("GET") && !Internal.instance.isReadable(pooled)) {
                       pooled.getSocket().close();
                            } else {
                       return pooled;
                            }
                        } else {
                   if (!hasNextConnectionSpec()) {
                       if (!hasNextInetSocketAddress()) {
                           if (!hasNextProxy()) {
                               if (!hasNextPostponed()) {
                                   throw new NoSuchElementException();
                                        }
                               return new Connection(this.pool, nextPostponed());
                                    }
                           this.lastProxy = nextProxy();
                                }
                       this.lastInetSocketAddress = nextInetSocketAddress();
                            }
                   this.lastSpec = nextConnectionSpec();
                   Route route = new Route(this.address, this.lastProxy, this.lastInetSocketAddress, this.lastSpec);
                   if (this.routeDatabase.shouldPostpone(route)) {
                       this.postponedRoutes.add(route);
                       return nextUnconnected();
                            }
                   return new Connection(this.pool, route);
                        }
                    }
                }

       public void connectFailed(Connection connection, IOException failure) {
           if (Internal.instance.recycleCount(connection) <= 0) {
               Route failedRoute = connection.getRoute();
               if (failedRoute.getProxy().type() != Proxy.Type.DIRECT && this.proxySelector != null) {
                   this.proxySelector.connectFailed(this.uri, failedRoute.getProxy().address(), failure);
                        }
               this.routeDatabase.failed(failedRoute);
               if (!(failure instanceof SSLHandshakeException) && !(failure instanceof SSLProtocolException)) {
                   while (this.nextSpecIndex < this.connectionSpecs.size()) {
                                Address address = this.address;
                                Proxy proxy = this.lastProxy;
                                InetSocketAddress inetSocketAddress = this.lastInetSocketAddress;
                                List<ConnectionSpec> list = this.connectionSpecs;
                                int i = this.nextSpecIndex;
                                this.nextSpecIndex = i + 1;
                       Route toSuppress = new Route(address, proxy, inetSocketAddress, list.get(i));
                       this.routeDatabase.failed(toSuppress);
                            }
                        }
                    }
                }

       private void resetNextProxy(URI uri, Proxy proxy) {
           if (proxy != null) {
               this.proxies = Collections.singletonList(proxy);
                    } else {
               this.proxies = new ArrayList();
               List<Proxy> selectedProxies = this.proxySelector.select(uri);
               if (selectedProxies != null) {
                            this.proxies.addAll(selectedProxies);
                        }
               this.proxies.removeAll(Collections.singleton(Proxy.NO_PROXY));
               this.proxies.add(Proxy.NO_PROXY);
                    }
           this.nextProxyIndex = 0;
                }

       private boolean hasNextProxy() {
                    return this.nextProxyIndex < this.proxies.size();
                }

       private Proxy nextProxy() throws IOException {
           if (!hasNextProxy()) {
               throw new SocketException("No route to " + this.address.getUriHost() + "; exhausted proxy configurations: " + this.proxies);
                    }
           List<Proxy> list = this.proxies;
                    int i = this.nextProxyIndex;
                    this.nextProxyIndex = i + 1;
                    Proxy result = list.get(i);
           resetNextInetSocketAddress(result);
           return result;
                }

       private void resetNextInetSocketAddress(Proxy proxy) throws UnknownHostException {
                    String socketHost;
                    int socketPort;
           this.inetSocketAddresses = new ArrayList();
           if (proxy.type() == Proxy.Type.DIRECT) {
               socketHost = this.address.getUriHost();
               socketPort = Util.getEffectivePort(this.uri);
                    } else {
               SocketAddress proxyAddress = proxy.address();
               if (!(proxyAddress instanceof InetSocketAddress)) {
                   throw new IllegalArgumentException("Proxy.address() is not an InetSocketAddress: " + proxyAddress.getClass());
                        }
               InetSocketAddress proxySocketAddress = (InetSocketAddress) proxyAddress;
               socketHost = proxySocketAddress.getHostName();
               socketPort = proxySocketAddress.getPort();
                    }
           InetAddress[] arr$ = this.network.resolveInetAddresses(socketHost);
                    for (InetAddress inetAddress : arr$) {
               this.inetSocketAddresses.add(new InetSocketAddress(inetAddress, socketPort));
                    }
           this.nextInetSocketAddressIndex = 0;
                }

       private boolean hasNextInetSocketAddress() {
                    return this.nextInetSocketAddressIndex < this.inetSocketAddresses.size();
                }

       private InetSocketAddress nextInetSocketAddress() throws IOException {
           if (!hasNextInetSocketAddress()) {
               throw new SocketException("No route to " + this.address.getUriHost() + "; exhausted inet socket addresses: " + this.inetSocketAddresses);
                    }
           List<InetSocketAddress> list = this.inetSocketAddresses;
                    int i = this.nextInetSocketAddressIndex;
                    this.nextInetSocketAddressIndex = i + 1;
                    InetSocketAddress result = list.get(i);
           resetConnectionSpecs();
           return result;
                }

       private void resetConnectionSpecs() {
           this.connectionSpecs = new ArrayList();
                    for (ConnectionSpec spec : this.address.getConnectionSpecs()) {
               if (this.request.isHttps() == spec.isTls()) {
                   this.connectionSpecs.add(spec);
                        }
                    }
           this.nextSpecIndex = 0;
                }

       private boolean hasNextConnectionSpec() {
                    return this.nextSpecIndex < this.connectionSpecs.size();
                }

       private ConnectionSpec nextConnectionSpec() throws IOException {
           if (!hasNextConnectionSpec()) {
               throw new SocketException("No route to " + this.address.getUriHost() + "; exhausted connection specs: " + this.connectionSpecs);
                    }
           List<ConnectionSpec> list = this.connectionSpecs;
                    int i = this.nextSpecIndex;
                    this.nextSpecIndex = i + 1;
                    return list.get(i);
                }

       private boolean hasNextPostponed() {
                    return !this.postponedRoutes.isEmpty();
                }

       private Route nextPostponed() {
           return this.postponedRoutes.remove(0);
                }
            }
