            package com.fasterxml.jackson.core.sym;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 19 */    public final class Name1 extends Name {
                static final Name1 sEmptyName = new Name1("", 0, 0);
                final int mQuad;

/* 20 */        Name1(String str, int i, int i2) {
/* 21 */            super(str, i);
/* 22 */            this.mQuad = i2;
                }

/* 24 */        static Name1 getEmptyName() {
/* 25 */            return sEmptyName;
                }

                @Override // com.fasterxml.jackson.core.sym.Name
/* 29 */        public boolean equals(int i) {
                    return i == this.mQuad;
                }

                @Override // com.fasterxml.jackson.core.sym.Name
/* 35 */        public boolean equals(int i, int i2) {
                    return i == this.mQuad && i2 == 0;
                }

                @Override // com.fasterxml.jackson.core.sym.Name
/* 41 */        public boolean equals(int[] iArr, int i) {
                    return i == 1 && iArr[0] == this.mQuad;
                }
            }
