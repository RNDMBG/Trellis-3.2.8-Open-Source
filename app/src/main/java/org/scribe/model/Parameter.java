            package org.scribe.model;

            import org.scribe.utils.OAuthEncoder;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 6 */     public class Parameter implements Comparable<Parameter> {
                private static final String UTF = "UTF8";
                private final String key;
                private final String value;


/* 15 */        public Parameter(String key, String value) {
/* 17 */            this.key = key;
/* 18 */            this.value = value;
                }

/* 22 */        public String asUrlEncodedPair() {
/* 23 */            return OAuthEncoder.encode(this.key).concat("=").concat(OAuthEncoder.encode(this.value));
                }

/* 27 */        public boolean equals(Object other) {
/* 28 */            if (other == null) {
                        return false;
                    }
/* 29 */            if (other == this) {
                        return true;
                    }
/* 30 */            if (!(other instanceof Parameter)) {
                        return false;
                    }
/* 32 */            Parameter otherParam = (Parameter) other;
/* 33 */            return otherParam.key.equals(this.key) && otherParam.value.equals(this.value);
                }

/* 37 */        public int hashCode() {
/* 38 */            return this.key.hashCode() + this.value.hashCode();
                }

                /* JADX INFO: renamed from: compareTo, reason: avoid collision after fix types in other method */
/* 42 */        public int compareTo(Parameter parameter) {
/* 43 */            int keyDiff = this.key.compareTo(parameter.key);
                    return keyDiff != 0 ? keyDiff : this.value.compareTo(parameter.value);
                }
            }
