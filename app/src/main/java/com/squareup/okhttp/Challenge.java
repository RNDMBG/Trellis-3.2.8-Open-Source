            package com.squareup.okhttp;

            import com.squareup.okhttp.internal.Util;
            
            
    public final class Challenge {
                private final String realm;
                private final String scheme;

        public Challenge(String scheme, String realm) {
            this.scheme = scheme;
            this.realm = realm;
                }

        public String getScheme() {
            return this.scheme;
                }

        public String getRealm() {
            return this.realm;
                }

        public boolean equals(Object o) {
                    return (o instanceof Challenge) && Util.equal(this.scheme, ((Challenge) o).scheme) && Util.equal(this.realm, ((Challenge) o).realm);
                }

        public int hashCode() {
            int result = (this.realm != null ? this.realm.hashCode() : 0) + 899;
            return (result * 31) + (this.scheme != null ? this.scheme.hashCode() : 0);
                }

        public String toString() {
            return this.scheme + " realm=\"" + this.realm + "\"";
                }
            }
