            package com.fasterxml.jackson.core.sym;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 15 */    public final class Name3 extends Name {
                final int mQuad1;
                final int mQuad2;
                final int mQuad3;

/* 16 */        Name3(String str, int i, int i2, int i3, int i4) {
/* 17 */            super(str, i);
/* 18 */            this.mQuad1 = i2;
/* 19 */            this.mQuad2 = i3;
/* 20 */            this.mQuad3 = i4;
                }

                @Override // com.fasterxml.jackson.core.sym.Name
/* 24 */        public boolean equals(int i) {
/* 25 */            return false;
                }

                @Override // com.fasterxml.jackson.core.sym.Name
/* 28 */        public boolean equals(int i, int i2) {
/* 29 */            return false;
                }

                @Override // com.fasterxml.jackson.core.sym.Name
/* 33 */        public boolean equals(int[] iArr, int i) {
                    return i == 3 && iArr[0] == this.mQuad1 && iArr[1] == this.mQuad2 && iArr[2] == this.mQuad3;
                }
            }
