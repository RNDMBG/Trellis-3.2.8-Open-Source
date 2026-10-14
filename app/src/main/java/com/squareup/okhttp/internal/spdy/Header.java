            package com.squareup.okhttp.internal.spdy;

            import okio.ByteString;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 22 */    public final class Header {
                final int hpackSize;
                public final ByteString name;
                public final ByteString value;
                public static final ByteString RESPONSE_STATUS = ByteString.encodeUtf8(":status");
                public static final ByteString TARGET_METHOD = ByteString.encodeUtf8(":method");
                public static final ByteString TARGET_PATH = ByteString.encodeUtf8(":path");
                public static final ByteString TARGET_SCHEME = ByteString.encodeUtf8(":scheme");
                public static final ByteString TARGET_AUTHORITY = ByteString.encodeUtf8(":authority");
                public static final ByteString TARGET_HOST = ByteString.encodeUtf8(":host");
                public static final ByteString VERSION = ByteString.encodeUtf8(":version");

/* 23 */        public Header(String name, String value) {
/* 24 */            this(ByteString.encodeUtf8(name), ByteString.encodeUtf8(value));
                }

/* 27 */        public Header(ByteString name, String value) {
/* 28 */            this(name, ByteString.encodeUtf8(value));
                }

/* 30 */        public Header(ByteString name, ByteString value) {
/* 32 */            this.name = name;
/* 33 */            this.value = value;
/* 34 */            this.hpackSize = name.size() + 32 + value.size();
                }

/* 37 */        public boolean equals(Object other) {
/* 38 */            if (!(other instanceof Header)) {
                        return false;
                    }
/* 39 */            Header that = (Header) other;
                    return this.name.equals(that.name) && this.value.equals(that.value);
                }

/* 46 */        public int hashCode() {
/* 48 */            int result = this.name.hashCode() + 527;
/* 49 */            return (result * 31) + this.value.hashCode();
                }

/* 53 */        public String toString() {
/* 54 */            return String.format("%s: %s", this.name.utf8(), this.value.utf8());
                }
            }
