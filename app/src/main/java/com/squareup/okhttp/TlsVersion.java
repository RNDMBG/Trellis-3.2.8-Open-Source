            package com.squareup.okhttp;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 31 */    public enum TlsVersion {
                TLS_1_2("TLSv1.2"),
                TLS_1_1("TLSv1.1"),
                TLS_1_0("TLSv1"),
                SSL_3_0("SSLv3");

                final String javaName;

/* 32 */        TlsVersion(String javaName) {
/* 34 */            this.javaName = javaName;
                }

/* 37 */        static TlsVersion forJavaName(String javaName) {
                    switch (javaName) {
                        case "TLSv1.2":
/* 39 */                    return TLS_1_2;
                        case "TLSv1.1":
/* 40 */                    return TLS_1_1;
                        case "TLSv1":
/* 41 */                    return TLS_1_0;
                        case "SSLv3":
/* 42 */                    return SSL_3_0;
                        default:
/* 44 */                    throw new IllegalArgumentException("Unexpected TLS version: " + javaName);
                    }
                }
            }
