            package com.squareup.okhttp;

            import com.squareup.okhttp.internal.Util;
            import java.security.Principal;
            import java.security.cert.Certificate;
            import java.security.cert.X509Certificate;
            import java.util.Collections;
            import java.util.List;
            import javax.net.ssl.SSLPeerUnverifiedException;
            import javax.net.ssl.SSLSession;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 39 */    public final class Handshake {
                private final String cipherSuite;
                private final List<Certificate> localCertificates;
                private final List<Certificate> peerCertificates;

/* 40 */        private Handshake(String cipherSuite, List<Certificate> peerCertificates, List<Certificate> localCertificates) {
/* 42 */            this.cipherSuite = cipherSuite;
/* 43 */            this.peerCertificates = peerCertificates;
/* 44 */            this.localCertificates = localCertificates;
                }

/* 47 */        public static Handshake get(SSLSession session) {
                    Certificate[] peerCertificates;
/* 48 */            String cipherSuite = session.getCipherSuite();
/* 49 */            if (cipherSuite == null) {
                        throw new IllegalStateException("cipherSuite == null");
                    }
                    try {
/* 53 */                peerCertificates = session.getPeerCertificates();
                    } catch (SSLPeerUnverifiedException e) {
/* 55 */                peerCertificates = null;
                    }
/* 57 */            List<Certificate> peerCertificatesList = peerCertificates != null ? Util.immutableList(peerCertificates) : Collections.emptyList();
/* 61 */            Certificate[] localCertificates = session.getLocalCertificates();
/* 62 */            List<Certificate> localCertificatesList = localCertificates != null ? Util.immutableList(localCertificates) : Collections.emptyList();
/* 66 */            return new Handshake(cipherSuite, peerCertificatesList, localCertificatesList);
                }

/* 70 */        public static Handshake get(String cipherSuite, List<Certificate> peerCertificates, List<Certificate> localCertificates) {
/* 71 */            if (cipherSuite == null) {
                        throw new IllegalArgumentException("cipherSuite == null");
                    }
/* 72 */            return new Handshake(cipherSuite, Util.immutableList(peerCertificates), Util.immutableList(localCertificates));
                }

/* 77 */        public String cipherSuite() {
/* 78 */            return this.cipherSuite;
                }

/* 82 */        public List<Certificate> peerCertificates() {
/* 83 */            return this.peerCertificates;
                }

/* 87 */        public Principal peerPrincipal() {
/* 88 */            if (this.peerCertificates.isEmpty()) {
                        return null;
                    }
                    return ((X509Certificate) this.peerCertificates.get(0)).getSubjectX500Principal();
                }

/* 94 */        public List<Certificate> localCertificates() {
/* 95 */            return this.localCertificates;
                }

/* 99 */        public Principal localPrincipal() {
/* 100 */           if (this.localCertificates.isEmpty()) {
                        return null;
                    }
                    return ((X509Certificate) this.localCertificates.get(0)).getSubjectX500Principal();
                }

/* 105 */       public boolean equals(Object other) {
/* 106 */           if (!(other instanceof Handshake)) {
                        return false;
                    }
/* 107 */           Handshake that = (Handshake) other;
                    return this.cipherSuite.equals(that.cipherSuite) && this.peerCertificates.equals(that.peerCertificates) && this.localCertificates.equals(that.localCertificates);
                }

/* 113 */       public int hashCode() {
/* 115 */           int result = this.cipherSuite.hashCode() + 527;
/* 117 */           return (((result * 31) + this.peerCertificates.hashCode()) * 31) + this.localCertificates.hashCode();
                }
            }
