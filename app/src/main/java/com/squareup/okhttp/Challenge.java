            package com.squareup.okhttp;

            import com.squareup.okhttp.internal.Util;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 23 */    public final class Challenge {
                private final String realm;
                private final String scheme;

/* 24 */        public Challenge(String scheme, String realm) {
/* 26 */            this.scheme = scheme;
/* 27 */            this.realm = realm;
                }

/* 31 */        public String getScheme() {
/* 32 */            return this.scheme;
                }

/* 36 */        public String getRealm() {
/* 37 */            return this.realm;
                }

/* 40 */        public boolean equals(Object o) {
                    return (o instanceof Challenge) && Util.equal(this.scheme, ((Challenge) o).scheme) && Util.equal(this.realm, ((Challenge) o).realm);
                }

/* 46 */        public int hashCode() {
/* 48 */            int result = (this.realm != null ? this.realm.hashCode() : 0) + 899;
/* 49 */            return (result * 31) + (this.scheme != null ? this.scheme.hashCode() : 0);
                }

/* 53 */        public String toString() {
/* 54 */            return this.scheme + " realm=\"" + this.realm + "\"";
                }
            }
