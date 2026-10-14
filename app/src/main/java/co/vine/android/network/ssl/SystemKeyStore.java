            package co.vine.android.network.ssl;

            import android.content.Context;
            import android.content.res.Resources;
            import co.vine.android.util.CommonUtil;
            import co.vine.android.util.CrossConstants;
            import java.io.BufferedInputStream;
            import java.io.IOException;
            import java.security.GeneralSecurityException;
            import java.security.KeyStore;
            import java.security.KeyStoreException;
            import java.security.NoSuchAlgorithmException;
            import java.security.Principal;
            import java.security.cert.CertificateException;
            import java.security.cert.X509Certificate;
            import java.util.Enumeration;
            import java.util.HashMap;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 34 */    public class SystemKeyStore {
                private static final int CACERTS_FILE_SIZE = 143360;
                private static SystemKeyStore sInstance;
                private final HashMap<Principal, X509Certificate> mTrustRoots;
                private final KeyStore mTrustStore;

/* 35 */        private SystemKeyStore(Context context) {
/* 37 */            KeyStore trustStore = getTrustStore(context);
/* 38 */            this.mTrustRoots = initializeTrustedRoots(trustStore);
/* 39 */            this.mTrustStore = trustStore;
                }

/* 42 */        public boolean isTrustRoot(X509Certificate certificate) {
/* 43 */            X509Certificate trustRoot = this.mTrustRoots.get(certificate.getSubjectX500Principal());
                    return trustRoot != null && trustRoot.getPublicKey().equals(certificate.getPublicKey());
                }

/* 47 */        public X509Certificate getTrustRootFor(X509Certificate certificate) {
/* 48 */            X509Certificate trustRoot = this.mTrustRoots.get(certificate.getIssuerX500Principal());
/* 50 */            if (trustRoot != null && !trustRoot.getSubjectX500Principal().equals(certificate.getSubjectX500Principal())) {
                        try {
/* 59 */                    certificate.verify(trustRoot.getPublicKey());
/* 64 */                    return trustRoot;
                        } catch (GeneralSecurityException e) {
                            return null;
                        }
                    }
                    return null;
                }

/* 68 */        private HashMap<Principal, X509Certificate> initializeTrustedRoots(KeyStore trustStore) {
                    try {
/* 69 */                HashMap<Principal, X509Certificate> trusted = new HashMap<>();
/* 72 */                Enumeration<String> aliases = trustStore.aliases();
/* 73 */                while (aliases.hasMoreElements()) {
/* 74 */                    String alias = aliases.nextElement();
/* 75 */                    X509Certificate cert = (X509Certificate) trustStore.getCertificate(alias);
/* 77 */                    if (cert != null) {
/* 78 */                        trusted.put(cert.getSubjectX500Principal(), cert);
                            }
                        }
/* 82 */                return trusted;
                    } catch (KeyStoreException e) {
/* 84 */                throw new AssertionError(e);
                    }
                }

/* 89 */        private KeyStore getTrustStore(Context context) {
                    try {
/* 90 */                KeyStore trustStore = KeyStore.getInstance("BKS");
/* 91 */                BufferedInputStream bin = new BufferedInputStream(context.getResources().openRawResource(CrossConstants.RES_RAW_CA), CACERTS_FILE_SIZE);
                        try {
/* 96 */                    trustStore.load(bin, "changeit".toCharArray());
/* 98 */                    CommonUtil.closeSilently(bin);
/* 101 */                   return trustStore;
                        } catch (Throwable th) {
                            CommonUtil.closeSilently(bin);
                            throw th;
                        }
                    } catch (Resources.NotFoundException e) {
/* 109 */               throw new AssertionError(e);
                    } catch (IOException e2) {
/* 111 */               throw new AssertionError(e2);
                    } catch (KeyStoreException kse) {
/* 103 */               throw new AssertionError(kse);
                    } catch (NoSuchAlgorithmException e3) {
/* 105 */               throw new AssertionError(e3);
                    } catch (CertificateException e4) {
/* 107 */               throw new AssertionError(e4);
                    }
                }

/* 115 */       public static synchronized SystemKeyStore getInstance(Context context) {
                    try {
                        if (sInstance == null) {
/* 117 */                   sInstance = new SystemKeyStore(context);
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
/* 119 */           return sInstance;
                }

/* 122 */       public KeyStore getTrustStore() {
/* 123 */           return this.mTrustStore;
                }
            }
