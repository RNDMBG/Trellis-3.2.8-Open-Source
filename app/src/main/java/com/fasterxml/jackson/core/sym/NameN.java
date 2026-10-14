            package com.fasterxml.jackson.core.sym;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 13 */    public final class NameN extends Name {
                final int mQuadLen;
                final int[] mQuads;

/* 14 */        NameN(String str, int i, int[] iArr, int i2) {
/* 15 */            super(str, i);
/* 19 */            if (i2 < 3) {
/* 20 */                throw new IllegalArgumentException("Qlen must >= 3");
                    }
/* 22 */            this.mQuads = iArr;
/* 23 */            this.mQuadLen = i2;
                }

                @Override // com.fasterxml.jackson.core.sym.Name
/* 27 */        public boolean equals(int i) {
/* 28 */            return false;
                }

                @Override // com.fasterxml.jackson.core.sym.Name
/* 31 */        public boolean equals(int i, int i2) {
/* 32 */            return false;
                }

                @Override // com.fasterxml.jackson.core.sym.Name
/* 36 */        public boolean equals(int[] iArr, int i) {
/* 37 */            if (i != this.mQuadLen) {
                        return false;
                    }
/* 61 */            for (int i2 = 0; i2 < i; i2++) {
/* 62 */                if (iArr[i2] != this.mQuads[i2]) {
                            return false;
                        }
                    }
/* 66 */            return true;
                }
            }
