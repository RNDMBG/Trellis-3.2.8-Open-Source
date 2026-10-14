            package com.crashlytics.android.internal;

            import com.crashlytics.android.Crashlytics;
            import java.security.KeyStoreException;
            import java.security.MessageDigest;
            import java.security.NoSuchAlgorithmException;
            import java.security.cert.CertificateException;
            import java.security.cert.X509Certificate;
            import java.util.Arrays;
            import java.util.Collections;
            import java.util.HashSet;
            import java.util.Iterator;
            import java.util.LinkedList;
            import java.util.List;
            import java.util.Set;
            import javax.net.ssl.TrustManager;
            import javax.net.ssl.TrustManagerFactory;
            import javax.net.ssl.X509TrustManager;
            
            
            
    final class C0661aH implements X509TrustManager {

                
                private final TrustManager[] f296a;

                
                private final C0662aI f297b;

                
                private final List<byte[]> f299d = new LinkedList();

                
                private final Set<X509Certificate> f300e = Collections.synchronizedSet(new HashSet());

                
                private final long f298c = -1;

        public C0661aH(C0662aI c0662aI, InterfaceC0660aG interfaceC0660aG) {
            this.f296a = m169a(c0662aI);
            this.f297b = c0662aI;
                    for (String str : interfaceC0660aG.mo166c()) {
                this.f299d.add(m168a(str));
                    }
                }

                
        private static TrustManager[] m169a(C0662aI c0662aI) {
                    try {
                TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance("X509");
                trustManagerFactory.init(c0662aI.f301a);
                return trustManagerFactory.getTrustManagers();
                    } catch (KeyStoreException e) {
                throw new AssertionError(e);
                    } catch (NoSuchAlgorithmException e2) {
                throw new AssertionError(e2);
                    }
                }

                
        private boolean m167a(X509Certificate x509Certificate) throws CertificateException {
                    try {
               byte[] bArrDigest = MessageDigest.getInstance("SHA1").digest(x509Certificate.getPublicKey().getEncoded());
               Iterator<byte[]> it = this.f299d.iterator();
                        while (it.hasNext()) {
                   if (Arrays.equals(it.next(), bArrDigest)) {
                       return true;
                            }
                        }
                        return false;
                    } catch (NoSuchAlgorithmException e) {
               throw new CertificateException(e);
                    }
                }

                @Override 
       public final void checkClientTrusted(X509Certificate[] x509CertificateArr, String str) throws CertificateException {
           throw new CertificateException("Client certificates not supported!");
                }

                @Override 
       public final void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
           if (!this.f300e.contains(chain[0])) {
                        for (TrustManager trustManager : this.f296a) {
                            ((X509TrustManager) trustManager).checkServerTrusted(chain, authType);
                        }
                        if (this.f298c == -1 || System.currentTimeMillis() - this.f298c <= 15552000000L) {
                            for (X509Certificate x509Certificate : C0701av.m293a(chain, this.f297b)) {
                                if (!m167a(x509Certificate)) {
                                }
                            }
                            throw new CertificateException("No valid pins found in chain!");
                        }
                        C0727v.m357a().m365b().mo99c(Crashlytics.TAG, "Certificate pins are stale, (" + (System.currentTimeMillis() - this.f298c) + " millis vs 15552000000 millis) falling back to system trust.");
               this.f300e.add(chain[0]);
                    }
                }

                @Override 
       public final X509Certificate[] getAcceptedIssuers() {
           return null;
                }

                
       private static byte[] m168a(String str) {
           int length = str.length();
           byte[] bArr = new byte[length / 2];
                    for (int i = 0; i < length; i += 2) {
               bArr[i / 2] = (byte) ((Character.digit(str.charAt(i), 16) << 4) + Character.digit(str.charAt(i + 1), 16));
                    }
           return bArr;
                }
            }
