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
            
            /* JADX INFO: renamed from: com.crashlytics.android.internal.aH */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 71 */    final class C0661aH implements X509TrustManager {

                /* JADX INFO: renamed from: a */
                private final TrustManager[] f296a;

                /* JADX INFO: renamed from: b */
                private final C0662aI f297b;

                /* JADX INFO: renamed from: d */
                private final List<byte[]> f299d = new LinkedList();

                /* JADX INFO: renamed from: e */
                private final Set<X509Certificate> f300e = Collections.synchronizedSet(new HashSet());

                /* JADX INFO: renamed from: c */
                private final long f298c = -1;

/* 72 */        public C0661aH(C0662aI c0662aI, InterfaceC0660aG interfaceC0660aG) {
/* 74 */            this.f296a = m169a(c0662aI);
/* 75 */            this.f297b = c0662aI;
                    for (String str : interfaceC0660aG.mo166c()) {
/* 79 */                this.f299d.add(m168a(str));
                    }
                }

                /* JADX INFO: renamed from: a */
/* 84 */        private static TrustManager[] m169a(C0662aI c0662aI) {
                    try {
/* 85 */                TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance("X509");
/* 86 */                trustManagerFactory.init(c0662aI.f301a);
/* 88 */                return trustManagerFactory.getTrustManagers();
                    } catch (KeyStoreException e) {
/* 92 */                throw new AssertionError(e);
                    } catch (NoSuchAlgorithmException e2) {
/* 90 */                throw new AssertionError(e2);
                    }
                }

                /* JADX INFO: renamed from: a */
/* 97 */        private boolean m167a(X509Certificate x509Certificate) throws CertificateException {
                    try {
/* 100 */               byte[] bArrDigest = MessageDigest.getInstance("SHA1").digest(x509Certificate.getPublicKey().getEncoded());
/* 102 */               Iterator<byte[]> it = this.f299d.iterator();
                        while (it.hasNext()) {
/* 103 */                   if (Arrays.equals(it.next(), bArrDigest)) {
/* 104 */                       return true;
                            }
                        }
                        return false;
                    } catch (NoSuchAlgorithmException e) {
/* 110 */               throw new CertificateException(e);
                    }
                }

                @Override // javax.net.ssl.X509TrustManager
/* 147 */       public final void checkClientTrusted(X509Certificate[] x509CertificateArr, String str) throws CertificateException {
/* 148 */           throw new CertificateException("Client certificates not supported!");
                }

                @Override // javax.net.ssl.X509TrustManager
/* 152 */       public final void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
/* 153 */           if (!this.f300e.contains(chain[0])) {
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
/* 162 */               this.f300e.add(chain[0]);
                    }
                }

                @Override // javax.net.ssl.X509TrustManager
/* 165 */       public final X509Certificate[] getAcceptedIssuers() {
/* 166 */           return null;
                }

                /* JADX INFO: renamed from: a */
/* 169 */       private static byte[] m168a(String str) {
/* 170 */           int length = str.length();
/* 171 */           byte[] bArr = new byte[length / 2];
                    for (int i = 0; i < length; i += 2) {
/* 174 */               bArr[i / 2] = (byte) ((Character.digit(str.charAt(i), 16) << 4) + Character.digit(str.charAt(i + 1), 16));
                    }
/* 178 */           return bArr;
                }
            }
