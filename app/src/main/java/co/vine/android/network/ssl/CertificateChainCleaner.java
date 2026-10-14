            package co.vine.android.network.ssl;

            import java.security.GeneralSecurityException;
            import java.security.cert.CertificateException;
            import java.security.cert.X509Certificate;
            import java.util.LinkedList;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 15 */    public class CertificateChainCleaner {
/* 16 */        private CertificateChainCleaner() {
                }

/* 22 */        public static X509Certificate[] getCleanChain(X509Certificate[] chain, SystemKeyStore systemKeyStore) throws CertificateException {
/* 23 */            LinkedList<X509Certificate> cleanChain = new LinkedList<>();
/* 24 */            boolean trustedChain = false;
/* 27 */            if (systemKeyStore.isTrustRoot(chain[0])) {
/* 28 */                trustedChain = true;
                    }
/* 31 */            cleanChain.add(chain[0]);
/* 33 */            int i = 1;
                    while (i < chain.length) {
/* 34 */                if (systemKeyStore.isTrustRoot(chain[i])) {
/* 35 */                    trustedChain = true;
                        }
/* 38 */                if (!isValidLink(chain[i], chain[i - 1])) {
                            break;
                        }
/* 39 */                cleanChain.add(chain[i]);
/* 33 */                i++;
                    }
/* 45 */            X509Certificate trustRoot = systemKeyStore.getTrustRootFor(chain[i - 1]);
/* 47 */            if (trustRoot != null) {
/* 48 */                cleanChain.add(trustRoot);
/* 49 */                trustedChain = true;
                    }
/* 52 */            if (trustedChain) {
/* 53 */                return (X509Certificate[]) cleanChain.toArray(new X509Certificate[cleanChain.size()]);
                    }
/* 55 */            throw new CertificateException("Didn't find a trust anchor in chain cleanup!");
                }

/* 59 */        private static boolean isValidLink(X509Certificate parent, X509Certificate child) {
/* 60 */            if (!parent.getSubjectX500Principal().equals(child.getIssuerX500Principal())) {
                        return false;
                    }
                    try {
/* 65 */                child.verify(parent.getPublicKey());
/* 70 */                return true;
                    } catch (GeneralSecurityException e) {
                        return false;
                    }
                }
            }
