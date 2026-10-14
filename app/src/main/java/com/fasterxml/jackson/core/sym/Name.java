            package com.fasterxml.jackson.core.sym;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 14 */    public abstract class Name {
                protected final int _hashCode;
                protected final String _name;

                public abstract boolean equals(int i);

                public abstract boolean equals(int i, int i2);

                public abstract boolean equals(int[] iArr, int i);

/* 15 */        protected Name(String str, int i) {
/* 17 */            this._name = str;
/* 18 */            this._hashCode = i;
                }

/* 20 */        public String getName() {
/* 21 */            return this._name;
                }

/* 40 */        public String toString() {
/* 41 */            return this._name;
                }

/* 42 */        public final int hashCode() {
/* 43 */            return this._hashCode;
                }

/* 47 */        public boolean equals(Object obj) {
                    return obj == this;
                }
            }
