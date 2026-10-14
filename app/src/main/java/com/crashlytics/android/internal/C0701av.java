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
            
            /* JADX INFO: renamed from: com.crashlytics.android.internal.av */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 21 */    public class C0701av {

                /* JADX INFO: renamed from: a */
                private final InterfaceC0722q f413a;

                /* JADX INFO: renamed from: b */
                private InterfaceC0660aG f414b;

                /* JADX INFO: renamed from: c */
                private SSLSocketFactory f415c;

                /* JADX INFO: renamed from: d */
                private boolean f416d;

                /* JADX INFO: renamed from: a */
/* 22 */        public static X509Certificate[] m293a(X509Certificate[] x509CertificateArr, C0662aI c0662aI) throws CertificateException {
                    boolean z = true;
/* 23 */            LinkedList linkedList = new LinkedList();
/* 27 */            boolean z2 = c0662aI.m172a(x509CertificateArr[0]);
/* 31 */            linkedList.add(x509CertificateArr[0]);
                    boolean z3 = z2;
                    int i = 1;
/* 33 */            while (i < x509CertificateArr.length) {
/* 34 */                if (c0662aI.m172a(x509CertificateArr[i])) {
                            z3 = true;
                        }
/* 38 */                if (!m292a(x509CertificateArr[i], x509CertificateArr[i - 1])) {
                            break;
                        }
/* 39 */                linkedList.add(x509CertificateArr[i]);
/* 33 */                i++;
                    }
/* 45 */            X509Certificate x509CertificateM173b = c0662aI.m173b(x509CertificateArr[i - 1]);
/* 47 */            if (x509CertificateM173b != null) {
/* 48 */                linkedList.add(x509CertificateM173b);
                    } else {
                        z = z3;
                    }
/* 52 */            if (z) {
/* 53 */                return (X509Certificate[]) linkedList.toArray(new X509Certificate[linkedList.size()]);
                    }
/* 55 */            throw new CertificateException("Didn't find a trust anchor in chain cleanup!");
                }

/* 30 */        public C0701av() {
/* 31 */            this(new C0723r());
                }

/* 33 */        public C0701av(InterfaceC0722q interfaceC0722q) {
/* 35 */            this.f413a = interfaceC0722q;
                }

                /* JADX INFO: renamed from: a */
/* 44 */        public void m298a(InterfaceC0660aG interfaceC0660aG) {
/* 45 */            if (this.f414b != interfaceC0660aG) {
/* 46 */                this.f414b = interfaceC0660aG;
/* 47 */                m291a();
                    }
                }

                /* JADX INFO: renamed from: a */
/* 51 */        private synchronized void m291a() {
                    try {
                        this.f416d = false;
/* 53 */                this.f415c = null;
                    } catch (Throwable th) {
                        throw th;
                    }
                }

                /* JADX INFO: renamed from: a */
/* 57 */        public C0704ay m296a(EnumC0703ax enumC0703ax, String str) {
/* 58 */            return m297a(enumC0703ax, str, Collections.emptyMap());
                }

                /* JADX INFO: renamed from: a */
/* 59 */        private static boolean m292a(X509Certificate x509Certificate, X509Certificate x509Certificate2) {
/* 60 */            if (!x509Certificate.getSubjectX500Principal().equals(x509Certificate2.getIssuerX500Principal())) {
                        return false;
                    }
                    try {
/* 65 */                x509Certificate2.verify(x509Certificate.getPublicKey());
/* 70 */                return true;
                    } catch (GeneralSecurityException e) {
                        return false;
                    }
                }

                /* JADX INFO: renamed from: a */
/* 64 */        public C0704ay m297a(EnumC0703ax enumC0703ax, String str, Map<String, String> map) {
                    C0704ay c0704ayM306b;
                    SSLSocketFactory sSLSocketFactoryM294b;
                    switch (C0702aw.f417a[enumC0703ax.ordinal()]) {
                        case 1:
/* 67 */                    c0704ayM306b = C0704ay.m302a((CharSequence) str, (Map<?, ?>) map, true);
                            break;
                        case 2:
/* 71 */                    c0704ayM306b = C0704ay.m307b((CharSequence) str, (Map<?, ?>) map, true);
                            break;
                        case 3:
/* 75 */                    c0704ayM306b = C0704ay.m301a((CharSequence) str);
                            break;
                        case 4:
/* 79 */                    c0704ayM306b = C0704ay.m306b((CharSequence) str);
                            break;
                        default:
/* 83 */                    throw new IllegalArgumentException("Unsupported HTTP method!");
                    }
/* 87 */            if ((str == null ? false : str.toLowerCase().startsWith("https")) && this.f414b != null && (sSLSocketFactoryM294b = m294b()) != null) {
/* 91 */                ((HttpsURLConnection) c0704ayM306b.m330a()).setSSLSocketFactory(sSLSocketFactoryM294b);
                    }
/* 96 */            return c0704ayM306b;
                }

                /* JADX INFO: renamed from: b */
/* 103 */       private synchronized SSLSocketFactory m294b() {
                    try {
                        if (this.f415c == null && !this.f416d) {
/* 105 */                   this.f415c = m295c();
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
/* 107 */           return this.f415c;
                }

                /* JADX INFO: renamed from: c */
/* 110 */       private synchronized SSLSocketFactory m295c() {
                    SSLSocketFactory socketFactory;
                    try {
                        this.f416d = true;
                        try {
/* 114 */                   InterfaceC0660aG interfaceC0660aG = this.f414b;
                            SSLContext sSLContext = SSLContext.getInstance("TLS");
                            sSLContext.init(null, new TrustManager[]{new C0661aH(new C0662aI(interfaceC0660aG.mo164a(), interfaceC0660aG.mo165b()), interfaceC0660aG)}, null);
                            socketFactory = sSLContext.getSocketFactory();
/* 115 */                   this.f413a.mo96a(Crashlytics.TAG, "Custom SSL pinning enabled");
                        } catch (Exception e) {
/* 118 */                   this.f413a.mo97a(Crashlytics.TAG, "Exception while validating pinned certs", e);
                            socketFactory = null;
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                    return socketFactory;
                }
            }
