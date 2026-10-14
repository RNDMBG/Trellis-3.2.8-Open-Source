            package com.squareup.okhttp;

            import java.net.InetSocketAddress;
            import java.net.Proxy;
            
            
    public final class Route {
                final Address address;
                final ConnectionSpec connectionSpec;
                final InetSocketAddress inetSocketAddress;
                final Proxy proxy;

        public Route(Address address, Proxy proxy, InetSocketAddress inetSocketAddress, ConnectionSpec connectionSpec) {
            if (address == null) {
                throw new NullPointerException("address == null");
                    }
            if (proxy == null) {
                throw new NullPointerException("proxy == null");
                    }
            if (inetSocketAddress == null) {
                throw new NullPointerException("inetSocketAddress == null");
                    }
            if (connectionSpec == null) {
                throw new NullPointerException("connectionConfiguration == null");
                    }
            this.address = address;
            this.proxy = proxy;
            this.inetSocketAddress = inetSocketAddress;
            this.connectionSpec = connectionSpec;
                }

        public Address getAddress() {
            return this.address;
                }

        public Proxy getProxy() {
            return this.proxy;
                }

        public InetSocketAddress getSocketAddress() {
            return this.inetSocketAddress;
                }

        public ConnectionSpec getConnectionSpec() {
            return this.connectionSpec;
                }

        public boolean requiresTunnel() {
                    return this.address.sslSocketFactory != null && this.proxy.type() == Proxy.Type.HTTP;
                }

        public boolean equals(Object obj) {
            if (!(obj instanceof Route)) {
                        return false;
                    }
            Route other = (Route) obj;
                    return this.address.equals(other.address) && this.proxy.equals(other.proxy) && this.inetSocketAddress.equals(other.inetSocketAddress) && this.connectionSpec.equals(other.connectionSpec);
                }

       public int hashCode() {
           int result = this.address.hashCode() + 527;
           return (((((result * 31) + this.proxy.hashCode()) * 31) + this.inetSocketAddress.hashCode()) * 31) + this.connectionSpec.hashCode();
                }
            }
