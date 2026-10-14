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
            
            
    public class SystemKeyStore {
                private static final int CACERTS_FILE_SIZE = 143360;
                private static SystemKeyStore sInstance;
                private final HashMap<Principal, X509Certificate> mTrustRoots;
                private final KeyStore mTrustStore;

        private SystemKeyStore(Context context) {
            KeyStore trustStore = getTrustStore(context);
            this.mTrustRoots = initializeTrustedRoots(trustStore);
            this.mTrustStore = trustStore;
                }

        public boolean isTrustRoot(X509Certificate certificate) {
            X509Certificate trustRoot = this.mTrustRoots.get(certificate.getSubjectX500Principal());
                    return trustRoot != null && trustRoot.getPublicKey().equals(certificate.getPublicKey());
                }

        public X509Certificate getTrustRootFor(X509Certificate certificate) {
            X509Certificate trustRoot = this.mTrustRoots.get(certificate.getIssuerX500Principal());
            if (trustRoot != null && !trustRoot.getSubjectX500Principal().equals(certificate.getSubjectX500Principal())) {
                        try {
                    certificate.verify(trustRoot.getPublicKey());
                    return trustRoot;
                        } catch (GeneralSecurityException e) {
                            return null;
                        }
                    }
                    return null;
                }

        private HashMap<Principal, X509Certificate> initializeTrustedRoots(KeyStore trustStore) {
                    try {
                HashMap<Principal, X509Certificate> trusted = new HashMap<>();
                Enumeration<String> aliases = trustStore.aliases();
                while (aliases.hasMoreElements()) {
                    String alias = aliases.nextElement();
                    X509Certificate cert = (X509Certificate) trustStore.getCertificate(alias);
                    if (cert != null) {
                        trusted.put(cert.getSubjectX500Principal(), cert);
                            }
                        }
                return trusted;
                    } catch (KeyStoreException e) {
                throw new AssertionError(e);
                    }
                }

        private KeyStore getTrustStore(Context context) {
                    try {
                KeyStore trustStore = KeyStore.getInstance("BKS");
                BufferedInputStream bin = new BufferedInputStream(context.getResources().openRawResource(CrossConstants.RES_RAW_CA), CACERTS_FILE_SIZE);
                        try {
                    trustStore.load(bin, "changeit".toCharArray());
                    CommonUtil.closeSilently(bin);
                   return trustStore;
                        } catch (Throwable th) {
                            CommonUtil.closeSilently(bin);
                            throw th;
                        }
                    } catch (Resources.NotFoundException e) {
               throw new AssertionError(e);
                    } catch (IOException e2) {
               throw new AssertionError(e2);
                    } catch (KeyStoreException kse) {
               throw new AssertionError(kse);
                    } catch (NoSuchAlgorithmException e3) {
               throw new AssertionError(e3);
                    } catch (CertificateException e4) {
               throw new AssertionError(e4);
                    }
                }

       public static synchronized SystemKeyStore getInstance(Context context) {
                    try {
                        if (sInstance == null) {
                   sInstance = new SystemKeyStore(context);
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
           return sInstance;
                }

       public KeyStore getTrustStore() {
           return this.mTrustStore;
                }
            }
