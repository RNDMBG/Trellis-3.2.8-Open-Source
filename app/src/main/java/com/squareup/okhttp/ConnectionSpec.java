            package com.squareup.okhttp;

            import com.squareup.okhttp.internal.Platform;
            import com.squareup.okhttp.internal.Util;
            import java.util.Arrays;
            import java.util.List;
            import javax.net.ssl.SSLSocket;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 27 */    public final class ConnectionSpec {
                private final String[] cipherSuites;
                private ConnectionSpec supportedSpec;
                final boolean supportsTlsExtensions;
                final boolean tls;
                private final String[] tlsVersions;
                public static final ConnectionSpec MODERN_TLS = new Builder(true, null).cipherSuites(CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256, CipherSuite.TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256, CipherSuite.TLS_DHE_RSA_WITH_AES_128_GCM_SHA256, CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_256_CBC_SHA, CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_128_CBC_SHA, CipherSuite.TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA, CipherSuite.TLS_ECDHE_RSA_WITH_AES_256_CBC_SHA, CipherSuite.TLS_ECDHE_ECDSA_WITH_RC4_128_SHA, CipherSuite.TLS_ECDHE_RSA_WITH_RC4_128_SHA, CipherSuite.TLS_DHE_RSA_WITH_AES_128_CBC_SHA, CipherSuite.TLS_DHE_DSS_WITH_AES_128_CBC_SHA, CipherSuite.TLS_DHE_RSA_WITH_AES_256_CBC_SHA, CipherSuite.TLS_RSA_WITH_AES_128_GCM_SHA256, CipherSuite.TLS_RSA_WITH_AES_128_CBC_SHA, CipherSuite.TLS_RSA_WITH_AES_256_CBC_SHA, CipherSuite.TLS_RSA_WITH_3DES_EDE_CBC_SHA, CipherSuite.TLS_RSA_WITH_RC4_128_SHA, CipherSuite.TLS_RSA_WITH_RC4_128_MD5).tlsVersions(TlsVersion.TLS_1_2, TlsVersion.TLS_1_1, TlsVersion.TLS_1_0, TlsVersion.SSL_3_0).supportsTlsExtensions(true).build();
                public static final ConnectionSpec COMPATIBLE_TLS = new Builder(MODERN_TLS).tlsVersions(TlsVersion.SSL_3_0).build();
                public static final ConnectionSpec CLEARTEXT = new Builder(false, null).build();

/* 28 */        /* synthetic */ ConnectionSpec(Builder x0, Object x1) {
/* 29 */            this(x0);
                }

/* 28 */        static /* synthetic */ String[] access$500(ConnectionSpec x0) {
/* 29 */            return x0.cipherSuites;
                }

/* 28 */        static /* synthetic */ String[] access$600(ConnectionSpec x0) {
/* 29 */            return x0.tlsVersions;
                }

/* 79 */        private ConnectionSpec(Builder builder) {
/* 81 */            this.tls = Builder.access$100(builder);
/* 82 */            this.cipherSuites = Builder.access$200(builder);
/* 83 */            this.tlsVersions = Builder.access$300(builder);
/* 84 */            this.supportsTlsExtensions = Builder.access$400(builder);
                }

/* 87 */        public boolean isTls() {
/* 88 */            return this.tls;
                }

/* 91 */        public List<CipherSuite> cipherSuites() {
/* 92 */            CipherSuite[] result = new CipherSuite[this.cipherSuites.length];
                    for (int i = 0; i < this.cipherSuites.length; i++) {
/* 94 */                result[i] = CipherSuite.forJavaName(this.cipherSuites[i]);
                    }
/* 96 */            return Util.immutableList(result);
                }

/* 99 */        public List<TlsVersion> tlsVersions() {
/* 100 */           TlsVersion[] result = new TlsVersion[this.tlsVersions.length];
                    for (int i = 0; i < this.tlsVersions.length; i++) {
/* 102 */               result[i] = TlsVersion.forJavaName(this.tlsVersions[i]);
                    }
/* 104 */           return Util.immutableList(result);
                }

/* 107 */       public boolean supportsTlsExtensions() {
/* 108 */           return this.supportsTlsExtensions;
                }

/* 112 */       void apply(SSLSocket sslSocket, Route route) {
/* 113 */           ConnectionSpec specToApply = this.supportedSpec;
/* 114 */           if (specToApply == null) {
/* 115 */               specToApply = supportedSpec(sslSocket);
/* 116 */               this.supportedSpec = specToApply;
                    }
/* 119 */           sslSocket.setEnabledProtocols(specToApply.tlsVersions);
/* 120 */           sslSocket.setEnabledCipherSuites(specToApply.cipherSuites);
/* 122 */           Platform platform = Platform.get();
/* 123 */           if (specToApply.supportsTlsExtensions) {
/* 124 */               platform.configureTlsExtensions(sslSocket, route.address.uriHost, route.address.protocols);
                    }
                }

/* 132 */       private ConnectionSpec supportedSpec(SSLSocket sslSocket) {
/* 133 */           List<String> supportedCipherSuites = Util.intersect(Arrays.asList(this.cipherSuites), Arrays.asList(sslSocket.getSupportedCipherSuites()));
/* 135 */           List<String> supportedTlsVersions = Util.intersect(Arrays.asList(this.tlsVersions), Arrays.asList(sslSocket.getSupportedProtocols()));
/* 137 */           return new Builder(this).cipherSuites((String[]) supportedCipherSuites.toArray(new String[supportedCipherSuites.size()])).tlsVersions((String[]) supportedTlsVersions.toArray(new String[supportedTlsVersions.size()])).build();
                }

/* 143 */       public boolean equals(Object other) {
/* 144 */           if (!(other instanceof ConnectionSpec)) {
                        return false;
                    }
/* 146 */           ConnectionSpec that = (ConnectionSpec) other;
/* 147 */           if (this.tls == that.tls) {
                        return !this.tls || (Arrays.equals(this.cipherSuites, that.cipherSuites) && Arrays.equals(this.tlsVersions, that.tlsVersions) && this.supportsTlsExtensions == that.supportsTlsExtensions);
                    }
                    return false;
                }

/* 158 */       public int hashCode() {
/* 160 */           if (!this.tls) {
/* 159 */               return 17;
                    }
/* 161 */           int result = Arrays.hashCode(this.cipherSuites) + 527;
/* 163 */           return (((result * 31) + Arrays.hashCode(this.tlsVersions)) * 31) + (this.supportsTlsExtensions ? 0 : 1);
                }

/* 168 */       public String toString() {
                    return this.tls ? "ConnectionSpec(cipherSuites=" + cipherSuites() + ", tlsVersions=" + tlsVersions() + ", supportsTlsExtensions=" + this.supportsTlsExtensions + ")" : "ConnectionSpec()";
                }

/* 177 */       public static final class Builder {
                    private String[] cipherSuites;
                    private boolean supportsTlsExtensions;
                    private boolean tls;
                    private String[] tlsVersions;

/* 178 */           /* synthetic */ Builder(boolean x0, Object x1) {
/* 179 */               this(x0);
                    }

/* 178 */           static /* synthetic */ boolean access$100(Builder x0) {
/* 179 */               return x0.tls;
                    }

/* 178 */           static /* synthetic */ String[] access$200(Builder x0) {
/* 179 */               return x0.cipherSuites;
                    }

/* 178 */           static /* synthetic */ String[] access$300(Builder x0) {
/* 179 */               return x0.tlsVersions;
                    }

/* 178 */           static /* synthetic */ boolean access$400(Builder x0) {
/* 179 */               return x0.supportsTlsExtensions;
                    }

/* 184 */           private Builder(boolean tls) {
/* 186 */               this.tls = tls;
                    }

/* 188 */           public Builder(ConnectionSpec connectionSpec) {
/* 190 */               this.tls = connectionSpec.tls;
/* 191 */               this.cipherSuites = ConnectionSpec.access$500(connectionSpec);
/* 192 */               this.tlsVersions = ConnectionSpec.access$600(connectionSpec);
/* 193 */               this.supportsTlsExtensions = connectionSpec.supportsTlsExtensions;
                    }

/* 196 */           public Builder cipherSuites(CipherSuite... cipherSuites) {
/* 197 */               if (!this.tls) {
                            throw new IllegalStateException("no cipher suites for cleartext connections");
                        }
/* 200 */               String[] strings = new String[cipherSuites.length];
                        for (int i = 0; i < cipherSuites.length; i++) {
/* 202 */                   strings[i] = cipherSuites[i].javaName;
                        }
/* 205 */               return cipherSuites(strings);
                    }

/* 208 */           Builder cipherSuites(String[] cipherSuites) {
/* 209 */               this.cipherSuites = cipherSuites;
/* 210 */               return this;
                    }

/* 213 */           public Builder tlsVersions(TlsVersion... tlsVersions) {
/* 214 */               if (!this.tls) {
                            throw new IllegalStateException("no TLS versions for cleartext connections");
                        }
/* 217 */               String[] strings = new String[tlsVersions.length];
                        for (int i = 0; i < tlsVersions.length; i++) {
/* 219 */                   strings[i] = tlsVersions[i].javaName;
                        }
/* 222 */               return tlsVersions(strings);
                    }

/* 225 */           Builder tlsVersions(String... tlsVersions) {
/* 226 */               this.tlsVersions = tlsVersions;
/* 227 */               return this;
                    }

/* 230 */           public Builder supportsTlsExtensions(boolean supportsTlsExtensions) {
/* 231 */               if (!this.tls) {
                            throw new IllegalStateException("no TLS extensions for cleartext connections");
                        }
/* 232 */               this.supportsTlsExtensions = supportsTlsExtensions;
/* 233 */               return this;
                    }

/* 236 */           public ConnectionSpec build() {
/* 237 */               return new ConnectionSpec(this, null);
                    }
                }
            }
