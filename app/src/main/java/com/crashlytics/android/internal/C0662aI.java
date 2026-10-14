            package com.crashlytics.android.internal;

            import java.io.BufferedInputStream;
            import java.io.IOException;
            import java.io.InputStream;
            import java.security.GeneralSecurityException;
            import java.security.KeyStore;
            import java.security.KeyStoreException;
            import java.security.NoSuchAlgorithmException;
            import java.security.Principal;
            import java.security.cert.CertificateException;
            import java.security.cert.X509Certificate;
            import java.util.Enumeration;
            import java.util.HashMap;
            
            /* JADX INFO: renamed from: com.crashlytics.android.internal.aI */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 25 */    final class C0662aI {

                /* JADX INFO: renamed from: a */
                final KeyStore f301a;

                /* JADX INFO: renamed from: b */
                private final HashMap<Principal, X509Certificate> f302b;

/* 26 */        public C0662aI(InputStream inputStream, String str) {
/* 28 */            KeyStore keyStoreM170a = m170a(inputStream, str);
/* 29 */            this.f302b = m171a(keyStoreM170a);
/* 30 */            this.f301a = keyStoreM170a;
                }

                /* JADX INFO: renamed from: a */
/* 33 */        public final boolean m172a(X509Certificate x509Certificate) {
/* 34 */            X509Certificate x509Certificate2 = this.f302b.get(x509Certificate.getSubjectX500Principal());
                    return x509Certificate2 != null && x509Certificate2.getPublicKey().equals(x509Certificate.getPublicKey());
                }

                /* JADX INFO: renamed from: b */
/* 38 */        public final X509Certificate m173b(X509Certificate x509Certificate) {
/* 39 */            X509Certificate x509Certificate2 = this.f302b.get(x509Certificate.getIssuerX500Principal());
/* 41 */            if (x509Certificate2 != null && !x509Certificate2.getSubjectX500Principal().equals(x509Certificate.getSubjectX500Principal())) {
                        try {
/* 50 */                    x509Certificate.verify(x509Certificate2.getPublicKey());
/* 55 */                    return x509Certificate2;
                        } catch (GeneralSecurityException e) {
                            return null;
                        }
                    }
                    return null;
                }

                /* JADX INFO: renamed from: a */
/* 59 */        private static HashMap<Principal, X509Certificate> m171a(KeyStore keyStore) {
                    try {
/* 60 */                HashMap<Principal, X509Certificate> map = new HashMap<>();
/* 63 */                Enumeration<String> enumerationAliases = keyStore.aliases();
/* 64 */                while (enumerationAliases.hasMoreElements()) {
/* 66 */                    X509Certificate x509Certificate = (X509Certificate) keyStore.getCertificate(enumerationAliases.nextElement());
/* 68 */                    if (x509Certificate != null) {
/* 69 */                        map.put(x509Certificate.getSubjectX500Principal(), x509Certificate);
                            }
                        }
/* 73 */                return map;
                    } catch (KeyStoreException e) {
/* 75 */                throw new AssertionError(e);
                    }
                }

                /* JADX INFO: renamed from: a */
/* 80 */        private static KeyStore m170a(InputStream inputStream, String str) {
                    try {
/* 81 */                KeyStore keyStore = KeyStore.getInstance("BKS");
/* 82 */                BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
                        try {
/* 85 */                    keyStore.load(bufferedInputStream, str.toCharArray());
/* 87 */                    bufferedInputStream.close();
/* 90 */                    return keyStore;
                        } catch (Throwable th) {
                            bufferedInputStream.close();
                            throw th;
                        }
                    } catch (IOException e) {
/* 98 */                throw new AssertionError(e);
                    } catch (KeyStoreException e2) {
/* 92 */                throw new AssertionError(e2);
                    } catch (NoSuchAlgorithmException e3) {
/* 94 */                throw new AssertionError(e3);
                    } catch (CertificateException e4) {
/* 96 */                throw new AssertionError(e4);
                    }
                }
            }
