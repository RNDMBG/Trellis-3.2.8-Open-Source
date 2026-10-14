            package org.scribe.model;

            import org.scribe.utils.Preconditions;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 19 */    public class Verifier {
                private final String value;

/* 20 */        public Verifier(String value) {
/* 22 */            Preconditions.checkNotNull(value, "Must provide a valid string as verifier");
/* 23 */            this.value = value;
                }

/* 27 */        public String getValue() {
/* 28 */            return this.value;
                }
            }
