            package com.squareup.okhttp;

            import com.squareup.okhttp.internal.Util;
            import java.net.Proxy;
            import java.util.List;
            import javax.net.SocketFactory;
            import javax.net.ssl.HostnameVerifier;
            import javax.net.ssl.SSLSocketFactory;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 50 */    public final class Address {
                final Authenticator authenticator;
                final CertificatePinner certificatePinner;
                final List<ConnectionSpec> connectionSpecs;
                final HostnameVerifier hostnameVerifier;
                final List<Protocol> protocols;
                final Proxy proxy;
                final SocketFactory socketFactory;
                final SSLSocketFactory sslSocketFactory;
                final String uriHost;
                final int uriPort;

/* 51 */        public Address(String uriHost, int uriPort, SocketFactory socketFactory, SSLSocketFactory sslSocketFactory, HostnameVerifier hostnameVerifier, CertificatePinner certificatePinner, Authenticator authenticator, Proxy proxy, List<Protocol> protocols, List<ConnectionSpec> connectionSpecs) {
/* 53 */            if (uriHost == null) {
                        throw new NullPointerException("uriHost == null");
                    }
/* 54 */            if (uriPort <= 0) {
                        throw new IllegalArgumentException("uriPort <= 0: " + uriPort);
                    }
/* 55 */            if (authenticator == null) {
                        throw new IllegalArgumentException("authenticator == null");
                    }
/* 56 */            if (protocols == null) {
                        throw new IllegalArgumentException("protocols == null");
                    }
/* 57 */            this.proxy = proxy;
/* 58 */            this.uriHost = uriHost;
/* 59 */            this.uriPort = uriPort;
/* 60 */            this.socketFactory = socketFactory;
/* 61 */            this.sslSocketFactory = sslSocketFactory;
/* 62 */            this.hostnameVerifier = hostnameVerifier;
/* 63 */            this.certificatePinner = certificatePinner;
/* 64 */            this.authenticator = authenticator;
/* 65 */            this.protocols = Util.immutableList(protocols);
/* 66 */            this.connectionSpecs = Util.immutableList(connectionSpecs);
                }

/* 70 */        public String getUriHost() {
/* 71 */            return this.uriHost;
                }

/* 78 */        public int getUriPort() {
/* 79 */            return this.uriPort;
                }

/* 83 */        public SocketFactory getSocketFactory() {
/* 84 */            return this.socketFactory;
                }

/* 91 */        public SSLSocketFactory getSslSocketFactory() {
/* 92 */            return this.sslSocketFactory;
                }

/* 99 */        public HostnameVerifier getHostnameVerifier() {
/* 100 */           return this.hostnameVerifier;
                }

/* 106 */       public Authenticator getAuthenticator() {
/* 107 */           return this.authenticator;
                }

/* 114 */       public List<Protocol> getProtocols() {
/* 115 */           return this.protocols;
                }

/* 118 */       public List<ConnectionSpec> getConnectionSpecs() {
/* 119 */           return this.connectionSpecs;
                }

/* 126 */       public Proxy getProxy() {
/* 127 */           return this.proxy;
                }

/* 130 */       public boolean equals(Object other) {
/* 131 */           if (!(other instanceof Address)) {
                        return false;
                    }
/* 132 */           Address that = (Address) other;
                    return Util.equal(this.proxy, that.proxy) && this.uriHost.equals(that.uriHost) && this.uriPort == that.uriPort && Util.equal(this.sslSocketFactory, that.sslSocketFactory) && Util.equal(this.hostnameVerifier, that.hostnameVerifier) && Util.equal(this.certificatePinner, that.certificatePinner) && Util.equal(this.authenticator, that.authenticator) && Util.equal(this.protocols, that.protocols);
                }

/* 145 */       public int hashCode() {
/* 147 */           int result = this.uriHost.hashCode() + 527;
/* 154 */           return (((((((((((((result * 31) + this.uriPort) * 31) + (this.sslSocketFactory != null ? this.sslSocketFactory.hashCode() : 0)) * 31) + (this.hostnameVerifier != null ? this.hostnameVerifier.hashCode() : 0)) * 31) + (this.certificatePinner != null ? this.certificatePinner.hashCode() : 0)) * 31) + this.authenticator.hashCode()) * 31) + (this.proxy != null ? this.proxy.hashCode() : 0)) * 31) + this.protocols.hashCode();
                }
            }
