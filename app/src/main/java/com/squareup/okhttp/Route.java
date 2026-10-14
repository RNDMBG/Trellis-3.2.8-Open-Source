            package com.squareup.okhttp;

            import java.net.InetSocketAddress;
            import java.net.Proxy;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 41 */    public final class Route {
                final Address address;
                final ConnectionSpec connectionSpec;
                final InetSocketAddress inetSocketAddress;
                final Proxy proxy;

/* 42 */        public Route(Address address, Proxy proxy, InetSocketAddress inetSocketAddress, ConnectionSpec connectionSpec) {
/* 44 */            if (address == null) {
/* 45 */                throw new NullPointerException("address == null");
                    }
/* 47 */            if (proxy == null) {
/* 48 */                throw new NullPointerException("proxy == null");
                    }
/* 50 */            if (inetSocketAddress == null) {
/* 51 */                throw new NullPointerException("inetSocketAddress == null");
                    }
/* 53 */            if (connectionSpec == null) {
/* 54 */                throw new NullPointerException("connectionConfiguration == null");
                    }
/* 56 */            this.address = address;
/* 57 */            this.proxy = proxy;
/* 58 */            this.inetSocketAddress = inetSocketAddress;
/* 59 */            this.connectionSpec = connectionSpec;
                }

/* 62 */        public Address getAddress() {
/* 63 */            return this.address;
                }

/* 73 */        public Proxy getProxy() {
/* 74 */            return this.proxy;
                }

/* 77 */        public InetSocketAddress getSocketAddress() {
/* 78 */            return this.inetSocketAddress;
                }

/* 81 */        public ConnectionSpec getConnectionSpec() {
/* 82 */            return this.connectionSpec;
                }

/* 89 */        public boolean requiresTunnel() {
                    return this.address.sslSocketFactory != null && this.proxy.type() == Proxy.Type.HTTP;
                }

/* 93 */        public boolean equals(Object obj) {
/* 94 */            if (!(obj instanceof Route)) {
                        return false;
                    }
/* 95 */            Route other = (Route) obj;
                    return this.address.equals(other.address) && this.proxy.equals(other.proxy) && this.inetSocketAddress.equals(other.inetSocketAddress) && this.connectionSpec.equals(other.connectionSpec);
                }

/* 104 */       public int hashCode() {
/* 106 */           int result = this.address.hashCode() + 527;
/* 109 */           return (((((result * 31) + this.proxy.hashCode()) * 31) + this.inetSocketAddress.hashCode()) * 31) + this.connectionSpec.hashCode();
                }
            }
