            package com.crashlytics.android.internal;

            import com.crashlytics.android.Crashlytics;
            import java.security.GeneralSecurityException;
            import java.security.cert.CertificateException;
            import java.security.cert.X509Certificate;
            import java.util.Collections;
            import java.util.LinkedList;
            import java.util.Map;
            import javax.net.ssl.HttpsURLConnection;
            import javax.net.ssl.SSLContext;
            import javax.net.ssl.SSLSocketFactory;
            import javax.net.ssl.TrustManager;
            
            
            
    public class C0701av {

                
                private final InterfaceC0722q f413a;

                
                private InterfaceC0660aG f414b;

                
                private SSLSocketFactory f415c;

                
                private boolean f416d;

                
        public static X509Certificate[] m293a(X509Certificate[] x509CertificateArr, C0662aI c0662aI) throws CertificateException {
                    boolean z = true;
            LinkedList linkedList = new LinkedList();
            boolean z2 = c0662aI.m172a(x509CertificateArr[0]);
            linkedList.add(x509CertificateArr[0]);
                    boolean z3 = z2;
                    int i = 1;
            while (i < x509CertificateArr.length) {
                if (c0662aI.m172a(x509CertificateArr[i])) {
                            z3 = true;
                        }
                if (!m292a(x509CertificateArr[i], x509CertificateArr[i - 1])) {
                            break;
                        }
                linkedList.add(x509CertificateArr[i]);
                i++;
                    }
            X509Certificate x509CertificateM173b = c0662aI.m173b(x509CertificateArr[i - 1]);
            if (x509CertificateM173b != null) {
                linkedList.add(x509CertificateM173b);
                    } else {
                        z = z3;
                    }
            if (z) {
                return (X509Certificate[]) linkedList.toArray(new X509Certificate[linkedList.size()]);
                    }
            throw new CertificateException("Didn't find a trust anchor in chain cleanup!");
                }

        public C0701av() {
            this(new C0723r());
                }

        public C0701av(InterfaceC0722q interfaceC0722q) {
            this.f413a = interfaceC0722q;
                }

                
        public void m298a(InterfaceC0660aG interfaceC0660aG) {
            if (this.f414b != interfaceC0660aG) {
                this.f414b = interfaceC0660aG;
                m291a();
                    }
                }

                
        private synchronized void m291a() {
                    try {
                        this.f416d = false;
                this.f415c = null;
                    } catch (Throwable th) {
                        throw th;
                    }
                }

                
        public C0704ay m296a(EnumC0703ax enumC0703ax, String str) {
            return m297a(enumC0703ax, str, Collections.emptyMap());
                }

                
        private static boolean m292a(X509Certificate x509Certificate, X509Certificate x509Certificate2) {
            if (!x509Certificate.getSubjectX500Principal().equals(x509Certificate2.getIssuerX500Principal())) {
                        return false;
                    }
                    try {
                x509Certificate2.verify(x509Certificate.getPublicKey());
                return true;
                    } catch (GeneralSecurityException e) {
                        return false;
                    }
                }

                
        public C0704ay m297a(EnumC0703ax enumC0703ax, String str, Map<String, String> map) {
                    C0704ay c0704ayM306b;
                    SSLSocketFactory sSLSocketFactoryM294b;
                    switch (C0702aw.f417a[enumC0703ax.ordinal()]) {
                        case 1:
                    c0704ayM306b = C0704ay.m302a((CharSequence) str, (Map<?, ?>) map, true);
                            break;
                        case 2:
                    c0704ayM306b = C0704ay.m307b((CharSequence) str, (Map<?, ?>) map, true);
                            break;
                        case 3:
                    c0704ayM306b = C0704ay.m301a((CharSequence) str);
                            break;
                        case 4:
                    c0704ayM306b = C0704ay.m306b((CharSequence) str);
                            break;
                        default:
                    throw new IllegalArgumentException("Unsupported HTTP method!");
                    }
            if ((str == null ? false : str.toLowerCase().startsWith("https")) && this.f414b != null && (sSLSocketFactoryM294b = m294b()) != null) {
                ((HttpsURLConnection) c0704ayM306b.m330a()).setSSLSocketFactory(sSLSocketFactoryM294b);
                    }
            return c0704ayM306b;
                }

                
       private synchronized SSLSocketFactory m294b() {
                    try {
                        if (this.f415c == null && !this.f416d) {
                   this.f415c = m295c();
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
           return this.f415c;
                }

                
       private synchronized SSLSocketFactory m295c() {
                    SSLSocketFactory socketFactory;
                    try {
                        this.f416d = true;
                        try {
                   InterfaceC0660aG interfaceC0660aG = this.f414b;
                            SSLContext sSLContext = SSLContext.getInstance("TLS");
                            sSLContext.init(null, new TrustManager[]{new C0661aH(new C0662aI(interfaceC0660aG.mo164a(), interfaceC0660aG.mo165b()), interfaceC0660aG)}, null);
                            socketFactory = sSLContext.getSocketFactory();
                   this.f413a.mo96a(Crashlytics.TAG, "Custom SSL pinning enabled");
                        } catch (Exception e) {
                   this.f413a.mo97a(Crashlytics.TAG, "Exception while validating pinned certs", e);
                            socketFactory = null;
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                    return socketFactory;
                }
            }
