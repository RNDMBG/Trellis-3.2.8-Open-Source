            package com.flurry.sdk;

            import java.security.KeyStore;
            import java.security.KeyStoreException;
            import java.security.NoSuchAlgorithmException;
            import java.security.cert.CertificateException;
            import java.security.cert.X509Certificate;
            import javax.net.ssl.TrustManager;
            import javax.net.ssl.TrustManagerFactory;
            import javax.net.ssl.X509TrustManager;
            
            
            
    public class C0822eg implements X509TrustManager {

                
                private X509TrustManager f779a;

        public C0822eg(KeyStore keyStore) throws NoSuchAlgorithmException, KeyStoreException {
            this.f779a = null;
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init(keyStore);
            TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();
            if (trustManagers.length == 0) {
                throw new NoSuchAlgorithmException("no trust manager found");
                    }
            this.f779a = (X509TrustManager) trustManagers[0];
                }

                @Override 
        public void checkClientTrusted(X509Certificate[] x509CertificateArr, String str) throws CertificateException {
            this.f779a.checkClientTrusted(x509CertificateArr, str);
                }

                @Override 
        public void checkServerTrusted(X509Certificate[] x509CertificateArr, String str) throws CertificateException {
            if (x509CertificateArr != null && x509CertificateArr.length == 1) {
                x509CertificateArr[0].checkValidity();
                    } else {
                this.f779a.checkServerTrusted(x509CertificateArr, str);
                    }
                }

                @Override 
        public X509Certificate[] getAcceptedIssuers() {
            return this.f779a.getAcceptedIssuers();
                }
            }
