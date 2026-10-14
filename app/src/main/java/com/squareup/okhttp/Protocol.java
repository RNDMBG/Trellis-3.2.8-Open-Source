            package com.squareup.okhttp;

            import java.io.IOException;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 74 */    public enum Protocol {
                HTTP_1_0("http/1.0"),
                HTTP_1_1("http/1.1"),
                SPDY_3("spdy/3.1"),
                HTTP_2("h2-14");

                private final String protocol;

/* 75 */        Protocol(String protocol) {
/* 77 */            this.protocol = protocol;
                }

/* 85 */        public static Protocol get(String protocol) throws IOException {
/* 86 */            if (protocol.equals(HTTP_1_0.protocol)) {
                        return HTTP_1_0;
                    }
/* 87 */            if (protocol.equals(HTTP_1_1.protocol)) {
                        return HTTP_1_1;
                    }
/* 88 */            if (protocol.equals(HTTP_2.protocol)) {
                        return HTTP_2;
                    }
/* 89 */            if (protocol.equals(SPDY_3.protocol)) {
                        return SPDY_3;
                    }
/* 90 */            throw new IOException("Unexpected protocol: " + protocol);
                }

                @Override // java.lang.Enum
/* 97 */        public String toString() {
/* 98 */            return this.protocol;
                }
            }
