            package com.squareup.okhttp;

            import com.squareup.okhttp.internal.Platform;
            import com.squareup.okhttp.internal.Util;
            import java.util.Arrays;
            import java.util.List;
            import javax.net.ssl.SSLSocket;
            
            
    public final class ConnectionSpec {
                private final String[] cipherSuites;
                private ConnectionSpec supportedSpec;
                final boolean supportsTlsExtensions;
                final boolean tls;
                private final String[] tlsVersions;
                public static final ConnectionSpec MODERN_TLS = new Builder(true, null).cipherSuites(CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256, CipherSuite.TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256, CipherSuite.TLS_DHE_RSA_WITH_AES_128_GCM_SHA256, CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_256_CBC_SHA, CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_128_CBC_SHA, CipherSuite.TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA, CipherSuite.TLS_ECDHE_RSA_WITH_AES_256_CBC_SHA, CipherSuite.TLS_ECDHE_ECDSA_WITH_RC4_128_SHA, CipherSuite.TLS_ECDHE_RSA_WITH_RC4_128_SHA, CipherSuite.TLS_DHE_RSA_WITH_AES_128_CBC_SHA, CipherSuite.TLS_DHE_DSS_WITH_AES_128_CBC_SHA, CipherSuite.TLS_DHE_RSA_WITH_AES_256_CBC_SHA, CipherSuite.TLS_RSA_WITH_AES_128_GCM_SHA256, CipherSuite.TLS_RSA_WITH_AES_128_CBC_SHA, CipherSuite.TLS_RSA_WITH_AES_256_CBC_SHA, CipherSuite.TLS_RSA_WITH_3DES_EDE_CBC_SHA, CipherSuite.TLS_RSA_WITH_RC4_128_SHA, CipherSuite.TLS_RSA_WITH_RC4_128_MD5).tlsVersions(TlsVersion.TLS_1_2, TlsVersion.TLS_1_1, TlsVersion.TLS_1_0, TlsVersion.SSL_3_0).supportsTlsExtensions(true).build();
                public static final ConnectionSpec COMPATIBLE_TLS = new Builder(MODERN_TLS).tlsVersions(TlsVersion.SSL_3_0).build();
                public static final ConnectionSpec CLEARTEXT = new Builder(false, null).build();

         ConnectionSpec(Builder x0, Object x1) {
            this(x0);
                }

        static  String[] access$500(ConnectionSpec x0) {
            return x0.cipherSuites;
                }

        static  String[] access$600(ConnectionSpec x0) {
            return x0.tlsVersions;
                }

        private ConnectionSpec(Builder builder) {
            this.tls = Builder.access$100(builder);
            this.cipherSuites = Builder.access$200(builder);
            this.tlsVersions = Builder.access$300(builder);
            this.supportsTlsExtensions = Builder.access$400(builder);
                }

        public boolean isTls() {
            return this.tls;
                }

        public List<CipherSuite> cipherSuites() {
            CipherSuite[] result = new CipherSuite[this.cipherSuites.length];
                    for (int i = 0; i < this.cipherSuites.length; i++) {
                result[i] = CipherSuite.forJavaName(this.cipherSuites[i]);
                    }
            return Util.immutableList(result);
                }

        public List<TlsVersion> tlsVersions() {
           TlsVersion[] result = new TlsVersion[this.tlsVersions.length];
                    for (int i = 0; i < this.tlsVersions.length; i++) {
               result[i] = TlsVersion.forJavaName(this.tlsVersions[i]);
                    }
           return Util.immutableList(result);
                }

       public boolean supportsTlsExtensions() {
           return this.supportsTlsExtensions;
                }

       void apply(SSLSocket sslSocket, Route route) {
           ConnectionSpec specToApply = this.supportedSpec;
           if (specToApply == null) {
               specToApply = supportedSpec(sslSocket);
               this.supportedSpec = specToApply;
                    }
           sslSocket.setEnabledProtocols(specToApply.tlsVersions);
           sslSocket.setEnabledCipherSuites(specToApply.cipherSuites);
           Platform platform = Platform.get();
           if (specToApply.supportsTlsExtensions) {
               platform.configureTlsExtensions(sslSocket, route.address.uriHost, route.address.protocols);
                    }
                }

       private ConnectionSpec supportedSpec(SSLSocket sslSocket) {
           List<String> supportedCipherSuites = Util.intersect(Arrays.asList(this.cipherSuites), Arrays.asList(sslSocket.getSupportedCipherSuites()));
           List<String> supportedTlsVersions = Util.intersect(Arrays.asList(this.tlsVersions), Arrays.asList(sslSocket.getSupportedProtocols()));
           return new Builder(this).cipherSuites((String[]) supportedCipherSuites.toArray(new String[supportedCipherSuites.size()])).tlsVersions((String[]) supportedTlsVersions.toArray(new String[supportedTlsVersions.size()])).build();
                }

       public boolean equals(Object other) {
           if (!(other instanceof ConnectionSpec)) {
                        return false;
                    }
           ConnectionSpec that = (ConnectionSpec) other;
           if (this.tls == that.tls) {
                        return !this.tls || (Arrays.equals(this.cipherSuites, that.cipherSuites) && Arrays.equals(this.tlsVersions, that.tlsVersions) && this.supportsTlsExtensions == that.supportsTlsExtensions);
                    }
                    return false;
                }

       public int hashCode() {
           if (!this.tls) {
               return 17;
                    }
           int result = Arrays.hashCode(this.cipherSuites) + 527;
           return (((result * 31) + Arrays.hashCode(this.tlsVersions)) * 31) + (this.supportsTlsExtensions ? 0 : 1);
                }

       public String toString() {
                    return this.tls ? "ConnectionSpec(cipherSuites=" + cipherSuites() + ", tlsVersions=" + tlsVersions() + ", supportsTlsExtensions=" + this.supportsTlsExtensions + ")" : "ConnectionSpec()";
                }

       public static final class Builder {
                    private String[] cipherSuites;
                    private boolean supportsTlsExtensions;
                    private boolean tls;
                    private String[] tlsVersions;

            Builder(boolean x0, Object x1) {
               this(x0);
                    }

           static  boolean access$100(Builder x0) {
               return x0.tls;
                    }

           static  String[] access$200(Builder x0) {
               return x0.cipherSuites;
                    }

           static  String[] access$300(Builder x0) {
               return x0.tlsVersions;
                    }

           static  boolean access$400(Builder x0) {
               return x0.supportsTlsExtensions;
                    }

           private Builder(boolean tls) {
               this.tls = tls;
                    }

           public Builder(ConnectionSpec connectionSpec) {
               this.tls = connectionSpec.tls;
               this.cipherSuites = ConnectionSpec.access$500(connectionSpec);
               this.tlsVersions = ConnectionSpec.access$600(connectionSpec);
               this.supportsTlsExtensions = connectionSpec.supportsTlsExtensions;
                    }

           public Builder cipherSuites(CipherSuite... cipherSuites) {
               if (!this.tls) {
                            throw new IllegalStateException("no cipher suites for cleartext connections");
                        }
               String[] strings = new String[cipherSuites.length];
                        for (int i = 0; i < cipherSuites.length; i++) {
                   strings[i] = cipherSuites[i].javaName;
                        }
               return cipherSuites(strings);
                    }

           Builder cipherSuites(String[] cipherSuites) {
               this.cipherSuites = cipherSuites;
               return this;
                    }

           public Builder tlsVersions(TlsVersion... tlsVersions) {
               if (!this.tls) {
                            throw new IllegalStateException("no TLS versions for cleartext connections");
                        }
               String[] strings = new String[tlsVersions.length];
                        for (int i = 0; i < tlsVersions.length; i++) {
                   strings[i] = tlsVersions[i].javaName;
                        }
               return tlsVersions(strings);
                    }

           Builder tlsVersions(String... tlsVersions) {
               this.tlsVersions = tlsVersions;
               return this;
                    }

           public Builder supportsTlsExtensions(boolean supportsTlsExtensions) {
               if (!this.tls) {
                            throw new IllegalStateException("no TLS extensions for cleartext connections");
                        }
               this.supportsTlsExtensions = supportsTlsExtensions;
               return this;
                    }

           public ConnectionSpec build() {
               return new ConnectionSpec(this, null);
                    }
                }
            }
