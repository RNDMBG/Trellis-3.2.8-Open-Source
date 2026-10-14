            package com.flurry.sdk;

            import java.security.KeyStore;
            import java.security.KeyStoreException;
            import java.security.NoSuchAlgorithmException;
            import java.security.cert.CertificateException;
            import java.security.cert.X509Certificate;
            import javax.net.ssl.TrustManager;
            import javax.net.ssl.TrustManagerFactory;
            import javax.net.ssl.X509TrustManager;
            
            /* JADX INFO: renamed from: com.flurry.sdk.eg */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 43 */    public class C0822eg implements X509TrustManager {

                /* JADX INFO: renamed from: a */
                private X509TrustManager f779a;

/* 44 */        public C0822eg(KeyStore keyStore) throws NoSuchAlgorithmException, KeyStoreException {
/* 39 */            this.f779a = null;
/* 46 */            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
/* 47 */            trustManagerFactory.init(keyStore);
/* 48 */            TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();
/* 49 */            if (trustManagers.length == 0) {
/* 50 */                throw new NoSuchAlgorithmException("no trust manager found");
                    }
/* 52 */            this.f779a = (X509TrustManager) trustManagers[0];
                }

                @Override // javax.net.ssl.X509TrustManager
/* 58 */        public void checkClientTrusted(X509Certificate[] x509CertificateArr, String str) throws CertificateException {
/* 59 */            this.f779a.checkClientTrusted(x509CertificateArr, str);
                }

                @Override // javax.net.ssl.X509TrustManager
/* 65 */        public void checkServerTrusted(X509Certificate[] x509CertificateArr, String str) throws CertificateException {
/* 66 */            if (x509CertificateArr != null && x509CertificateArr.length == 1) {
/* 67 */                x509CertificateArr[0].checkValidity();
                    } else {
/* 69 */                this.f779a.checkServerTrusted(x509CertificateArr, str);
                    }
                }

                @Override // javax.net.ssl.X509TrustManager
/* 76 */        public X509Certificate[] getAcceptedIssuers() {
/* 77 */            return this.f779a.getAcceptedIssuers();
                }
            }
