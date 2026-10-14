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
            
            
    public final class OkHostnameVerifier implements HostnameVerifier {
                private static final int ALT_DNS_NAME = 2;
                private static final int ALT_IPA_NAME = 7;
                public static final OkHostnameVerifier INSTANCE = new OkHostnameVerifier();
                private static final Pattern VERIFY_AS_IP_ADDRESS = Pattern.compile("([0-9a-fA-F]*:[0-9a-fA-F:.]*)|([\\d.]+)");

        private OkHostnameVerifier() {
                }

                @Override 
        public boolean verify(String host, SSLSession session) {
                    try {
                Certificate[] certificates = session.getPeerCertificates();
                return verify(host, (X509Certificate) certificates[0]);
                    } catch (SSLException e) {
                        return false;
                    }
                }

        public boolean verify(String host, X509Certificate certificate) {
                    return verifyAsIpAddress(host) ? verifyIpAddress(host, certificate) : verifyHostName(host, certificate);
                }

        static boolean verifyAsIpAddress(String host) {
            return VERIFY_AS_IP_ADDRESS.matcher(host).matches();
                }

        private boolean verifyIpAddress(String ipAddress, X509Certificate certificate) {
                    for (String altName : getSubjectAltNames(certificate, 7)) {
                if (ipAddress.equalsIgnoreCase(altName)) {
                    return true;
                        }
                    }
                    return false;
                }

        private boolean verifyHostName(String hostName, X509Certificate certificate) {
            String hostName2 = hostName.toLowerCase(Locale.US);
            boolean hasDns = false;
                    for (String altName : getSubjectAltNames(certificate, 2)) {
                hasDns = true;
               if (verifyHostName(hostName2, altName)) {
                   return true;
                        }
                    }
           if (!hasDns) {
               X500Principal principal = certificate.getSubjectX500Principal();
               String cn = new DistinguishedNameParser(principal).findMostSpecific("cn");
               if (cn != null) {
                   return verifyHostName(hostName2, cn);
                        }
                    }
           return false;
                }

       private List<String> getSubjectAltNames(X509Certificate certificate, int type) {
                    Integer altNameType;
                    String altName;
           List<String> result = new ArrayList<>();
                    try {
               Collection<?> subjectAltNames = certificate.getSubjectAlternativeNames();
               if (subjectAltNames == null) {
                   return Collections.emptyList();
                        }
                        for (Object subjectAltName : subjectAltNames) {
                   List<?> entry = (List) subjectAltName;
                   if (entry != null && entry.size() >= 2 && (altNameType = (Integer) entry.get(0)) != null && altNameType.intValue() == type && (altName = (String) entry.get(1)) != null) {
                       result.add(altName);
                            }
                        }
               return result;
                    } catch (CertificateParsingException e) {
               return Collections.emptyList();
                    }
                }

       public boolean verifyHostName(String hostName, String cn) {
                    int suffixLength;
                    int suffixStart;
           if (hostName == null || hostName.length() == 0 || cn == null || cn.length() == 0) {
                        return false;
                    }
           String cn2 = cn.toLowerCase(Locale.US);
           if (!cn2.contains("*")) {
               return hostName.equals(cn2);
                    }
           if (cn2.startsWith("*.") && hostName.regionMatches(0, cn2, 2, cn2.length() - 2)) {
                        return true;
                    }
           int asterisk = cn2.indexOf(42);
           int dot = cn2.indexOf(46);
                    return asterisk <= dot && hostName.regionMatches(0, cn2, 0, asterisk) && hostName.indexOf(46, asterisk) >= (suffixStart = hostName.length() - (suffixLength = cn2.length() - (asterisk + 1))) && hostName.regionMatches(suffixStart, cn2, asterisk + 1, suffixLength);
                }
            }
