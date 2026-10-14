            package co.vine.android.network.ssl;

            import java.security.KeyStoreException;
            import java.security.MessageDigest;
            import java.security.NoSuchAlgorithmException;
            import java.security.cert.CertificateException;
            import java.security.cert.X509Certificate;
            import java.util.Arrays;
            import java.util.Collections;
            import java.util.HashSet;
            import java.util.LinkedList;
            import java.util.List;
            import java.util.Set;
            import javax.net.ssl.TrustManager;
            import javax.net.ssl.TrustManagerFactory;
            import javax.net.ssl.X509TrustManager;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 70 */    public class PinningTrustManager implements X509TrustManager {
                private static final long PIN_FRESHNESS_DURATION_MILLIS = 15552000000L;
                private final SystemKeyStore mSystemKeyStore;
                private final TrustManager[] mSystemTrustManagers;
                private final List<byte[]> mPins = new LinkedList();
                private final Set<X509Certificate> mCache = Collections.synchronizedSet(new HashSet());

/* 71 */        public PinningTrustManager(SystemKeyStore keyStore, String[] pins) {
/* 73 */            this.mSystemTrustManagers = initializeSystemTrustManagers(keyStore);
/* 74 */            this.mSystemKeyStore = keyStore;
                    for (String pin : pins) {
/* 77 */                this.mPins.add(hexStringToByteArray(pin));
                    }
                }

/* 82 */        private TrustManager[] initializeSystemTrustManagers(SystemKeyStore keyStore) {
                    try {
/* 83 */                TrustManagerFactory tmf = TrustManagerFactory.getInstance("X509");
/* 84 */                tmf.init(keyStore.getTrustStore());
/* 86 */                return tmf.getTrustManagers();
                    } catch (KeyStoreException e) {
/* 90 */                throw new AssertionError(e);
                    } catch (NoSuchAlgorithmException nsae) {
/* 88 */                throw new AssertionError(nsae);
                    }
                }

/* 95 */        private boolean isValidPin(X509Certificate certificate) throws CertificateException {
                    try {
/* 96 */                MessageDigest digest = MessageDigest.getInstance("SHA1");
/* 97 */                byte[] spki = certificate.getPublicKey().getEncoded();
/* 98 */                byte[] pin = digest.digest(spki);
                        for (byte[] validPin : this.mPins) {
/* 101 */                   if (Arrays.equals(validPin, pin)) {
/* 102 */                       return true;
                            }
                        }
                        return false;
                    } catch (NoSuchAlgorithmException nsae) {
/* 108 */               throw new CertificateException(nsae);
                    }
                }

/* 113 */       private void checkSystemTrust(X509Certificate[] chain, String authType) throws CertificateException {
/* 114 */           TrustManager[] arr$ = this.mSystemTrustManagers;
                    for (TrustManager systemTrustManager : arr$) {
/* 115 */               ((X509TrustManager) systemTrustManager).checkServerTrusted(chain, authType);
                    }
                }

/* 133 */       private void checkPinTrust(X509Certificate[] chain) throws CertificateException {
/* 134 */           X509Certificate[] cleanChain = CertificateChainCleaner.getCleanChain(chain, this.mSystemKeyStore);
                    for (X509Certificate certificate : cleanChain) {
/* 138 */               if (isValidPin(certificate)) {
/* 139 */                   return;
                        }
                    }
/* 143 */           throw new CertificateException("No valid pins found in chain!");
                }

                @Override // javax.net.ssl.X509TrustManager
/* 147 */       public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
/* 148 */           throw new CertificateException("Client certificates not supported!");
                }

                @Override // javax.net.ssl.X509TrustManager
/* 152 */       public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
/* 153 */           if (!this.mCache.contains(chain[0])) {
/* 160 */               checkSystemTrust(chain, authType);
/* 161 */               checkPinTrust(chain);
/* 162 */               this.mCache.add(chain[0]);
                    }
                }

                @Override // javax.net.ssl.X509TrustManager
/* 165 */       public X509Certificate[] getAcceptedIssuers() {
/* 166 */           return null;
                }

/* 169 */       private byte[] hexStringToByteArray(String s) {
/* 170 */           int len = s.length();
/* 171 */           byte[] data = new byte[len / 2];
                    for (int i = 0; i < len; i += 2) {
/* 174 */               data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4) + Character.digit(s.charAt(i + 1), 16));
                    }
/* 178 */           return data;
                }
            }
