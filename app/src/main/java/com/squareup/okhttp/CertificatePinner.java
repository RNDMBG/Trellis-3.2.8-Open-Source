            package com.squareup.okhttp;

            import com.squareup.okhttp.internal.Util;
            import java.security.cert.Certificate;
            import java.security.cert.X509Certificate;
            import java.util.ArrayList;
            import java.util.Collections;
            import java.util.LinkedHashMap;
            import java.util.List;
            import java.util.Map;
            import javax.net.ssl.SSLPeerUnverifiedException;
            import okio.ByteString;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 101 */   public final class CertificatePinner {
                public static final CertificatePinner DEFAULT = new Builder().build();
                private final Map<String, List<ByteString>> hostnameToPins;

/* 102 */       /* synthetic */ CertificatePinner(Builder x0, Object x1) {
/* 103 */           this(x0);
                }

/* 107 */       private CertificatePinner(Builder builder) {
/* 109 */           this.hostnameToPins = Util.immutableMap(Builder.access$000(builder));
                }

/* 122 */       public void check(String hostname, Certificate... peerCertificates) throws SSLPeerUnverifiedException {
/* 123 */           List<ByteString> pins = this.hostnameToPins.get(hostname);
/* 124 */           if (pins != null) {
                        for (Certificate c : peerCertificates) {
/* 128 */                   if (pins.contains(sha1((X509Certificate) c))) {
/* 128 */                       return;
                            }
                        }
/* 132 */               StringBuilder message = new StringBuilder().append("Certificate pinning failure!").append("\n  Peer certificate chain:");
                        for (Certificate c2 : peerCertificates) {
/* 136 */                   X509Certificate x509Certificate = (X509Certificate) c2;
/* 137 */                   message.append("\n    sha1/").append(sha1(x509Certificate).base64()).append(": ").append(x509Certificate.getSubjectDN().getName());
                        }
/* 140 */               message.append("\n  Pinned certificates for ").append(hostname).append(":");
                        for (ByteString pin : pins) {
/* 142 */                   message.append("\n    sha1/").append(pin.base64());
                        }
/* 144 */               throw new SSLPeerUnverifiedException(message.toString());
                    }
                }

/* 152 */       public static String pin(Certificate certificate) {
/* 153 */           if (!(certificate instanceof X509Certificate)) {
/* 154 */               throw new IllegalArgumentException("Certificate pinning requires X509 certificates");
                    }
/* 156 */           return "sha1/" + sha1((X509Certificate) certificate).base64();
                }

/* 159 */       private static ByteString sha1(X509Certificate x509Certificate) {
/* 160 */           return Util.sha1(ByteString.m2468of(x509Certificate.getPublicKey().getEncoded()));
                }

/* 162 */       public static final class Builder {
                    private final Map<String, List<ByteString>> hostnameToPins = new LinkedHashMap();

/* 163 */           static /* synthetic */ Map access$000(Builder x0) {
/* 164 */               return x0.hostnameToPins;
                    }

/* 172 */           public Builder add(String hostname, String... pins) {
/* 173 */               if (hostname == null) {
                            throw new IllegalArgumentException("hostname == null");
                        }
/* 175 */               List<ByteString> hostPins = new ArrayList<>();
/* 176 */               List<ByteString> previousPins = this.hostnameToPins.put(hostname, Collections.unmodifiableList(hostPins));
/* 177 */               if (previousPins != null) {
/* 178 */                   hostPins.addAll(previousPins);
                        }
                        for (String pin : pins) {
/* 182 */                   if (!pin.startsWith("sha1/")) {
/* 183 */                       throw new IllegalArgumentException("pins must start with 'sha1/': " + pin);
                            }
/* 185 */                   ByteString decodedPin = ByteString.decodeBase64(pin.substring("sha1/".length()));
/* 186 */                   if (decodedPin == null) {
/* 187 */                       throw new IllegalArgumentException("pins must be base64: " + pin);
                            }
/* 189 */                   hostPins.add(decodedPin);
                        }
/* 192 */               return this;
                    }

/* 195 */           public CertificatePinner build() {
/* 196 */               return new CertificatePinner(this, null);
                    }
                }
            }
