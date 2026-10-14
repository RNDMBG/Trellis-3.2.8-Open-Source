            package com.squareup.okhttp.internal.tls;

            import java.security.cert.Certificate;
            import java.security.cert.CertificateParsingException;
            import java.security.cert.X509Certificate;
            import java.util.ArrayList;
            import java.util.Collection;
            import java.util.Collections;
            import java.util.List;
            import java.util.Locale;
            import java.util.regex.Pattern;
            import javax.net.ssl.HostnameVerifier;
            import javax.net.ssl.SSLException;
            import javax.net.ssl.SSLSession;
            import javax.security.auth.x500.X500Principal;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 56 */    public final class OkHostnameVerifier implements HostnameVerifier {
                private static final int ALT_DNS_NAME = 2;
                private static final int ALT_IPA_NAME = 7;
                public static final OkHostnameVerifier INSTANCE = new OkHostnameVerifier();
                private static final Pattern VERIFY_AS_IP_ADDRESS = Pattern.compile("([0-9a-fA-F]*:[0-9a-fA-F:.]*)|([\\d.]+)");

/* 57 */        private OkHostnameVerifier() {
                }

                @Override // javax.net.ssl.HostnameVerifier
/* 62 */        public boolean verify(String host, SSLSession session) {
                    try {
/* 63 */                Certificate[] certificates = session.getPeerCertificates();
/* 64 */                return verify(host, (X509Certificate) certificates[0]);
                    } catch (SSLException e) {
                        return false;
                    }
                }

/* 70 */        public boolean verify(String host, X509Certificate certificate) {
                    return verifyAsIpAddress(host) ? verifyIpAddress(host, certificate) : verifyHostName(host, certificate);
                }

/* 76 */        static boolean verifyAsIpAddress(String host) {
/* 77 */            return VERIFY_AS_IP_ADDRESS.matcher(host).matches();
                }

/* 83 */        private boolean verifyIpAddress(String ipAddress, X509Certificate certificate) {
                    for (String altName : getSubjectAltNames(certificate, 7)) {
/* 85 */                if (ipAddress.equalsIgnoreCase(altName)) {
/* 86 */                    return true;
                        }
                    }
                    return false;
                }

/* 95 */        private boolean verifyHostName(String hostName, X509Certificate certificate) {
/* 96 */            String hostName2 = hostName.toLowerCase(Locale.US);
/* 97 */            boolean hasDns = false;
                    for (String altName : getSubjectAltNames(certificate, 2)) {
/* 99 */                hasDns = true;
/* 100 */               if (verifyHostName(hostName2, altName)) {
/* 101 */                   return true;
                        }
                    }
/* 105 */           if (!hasDns) {
/* 106 */               X500Principal principal = certificate.getSubjectX500Principal();
/* 108 */               String cn = new DistinguishedNameParser(principal).findMostSpecific("cn");
/* 109 */               if (cn != null) {
/* 110 */                   return verifyHostName(hostName2, cn);
                        }
                    }
/* 114 */           return false;
                }

/* 117 */       private List<String> getSubjectAltNames(X509Certificate certificate, int type) {
                    Integer altNameType;
                    String altName;
/* 118 */           List<String> result = new ArrayList<>();
                    try {
/* 120 */               Collection<?> subjectAltNames = certificate.getSubjectAlternativeNames();
/* 121 */               if (subjectAltNames == null) {
/* 122 */                   return Collections.emptyList();
                        }
                        for (Object subjectAltName : subjectAltNames) {
/* 125 */                   List<?> entry = (List) subjectAltName;
/* 126 */                   if (entry != null && entry.size() >= 2 && (altNameType = (Integer) entry.get(0)) != null && altNameType.intValue() == type && (altName = (String) entry.get(1)) != null) {
/* 136 */                       result.add(altName);
                            }
                        }
/* 142 */               return result;
                    } catch (CertificateParsingException e) {
/* 142 */               return Collections.emptyList();
                    }
                }

/* 154 */       public boolean verifyHostName(String hostName, String cn) {
                    int suffixLength;
                    int suffixStart;
/* 155 */           if (hostName == null || hostName.length() == 0 || cn == null || cn.length() == 0) {
                        return false;
                    }
/* 159 */           String cn2 = cn.toLowerCase(Locale.US);
/* 161 */           if (!cn2.contains("*")) {
/* 162 */               return hostName.equals(cn2);
                    }
/* 165 */           if (cn2.startsWith("*.") && hostName.regionMatches(0, cn2, 2, cn2.length() - 2)) {
                        return true;
                    }
/* 169 */           int asterisk = cn2.indexOf(42);
/* 170 */           int dot = cn2.indexOf(46);
                    return asterisk <= dot && hostName.regionMatches(0, cn2, 0, asterisk) && hostName.indexOf(46, asterisk) >= (suffixStart = hostName.length() - (suffixLength = cn2.length() - (asterisk + 1))) && hostName.regionMatches(suffixStart, cn2, asterisk + 1, suffixLength);
                }
            }
