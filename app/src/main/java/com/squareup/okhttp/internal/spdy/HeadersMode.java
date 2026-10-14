            package com.squareup.okhttp.internal.spdy;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 24 */    public enum HeadersMode {
                SPDY_SYN_STREAM,
                SPDY_REPLY,
                SPDY_HEADERS,
                HTTP_20_HEADERS;

/* 25 */        public boolean failIfStreamAbsent() {
                    return this == SPDY_REPLY || this == SPDY_HEADERS;
                }

/* 30 */        public boolean failIfStreamPresent() {
                    return this == SPDY_SYN_STREAM;
                }

/* 38 */        public boolean failIfHeadersAbsent() {
                    return this == SPDY_HEADERS;
                }

/* 46 */        public boolean failIfHeadersPresent() {
                    return this == SPDY_REPLY;
                }
            }
