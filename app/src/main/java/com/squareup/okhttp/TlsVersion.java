            package com.squareup.okhttp;
            
            
    public enum TlsVersion {
                TLS_1_2("TLSv1.2"),
                TLS_1_1("TLSv1.1"),
                TLS_1_0("TLSv1"),
                SSL_3_0("SSLv3");

                final String javaName;

        TlsVersion(String javaName) {
            this.javaName = javaName;
                }

        static TlsVersion forJavaName(String javaName) {
                    switch (javaName) {
                        case "TLSv1.2":
                    return TLS_1_2;
                        case "TLSv1.1":
                    return TLS_1_1;
                        case "TLSv1":
                    return TLS_1_0;
                        case "SSLv3":
                    return SSL_3_0;
                        default:
                    throw new IllegalArgumentException("Unexpected TLS version: " + javaName);
                    }
                }
            }
