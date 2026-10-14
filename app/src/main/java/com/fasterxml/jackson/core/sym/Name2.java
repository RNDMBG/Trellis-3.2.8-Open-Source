            package com.fasterxml.jackson.core.sym;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 19 */    public final class Name2 extends Name {
                final int mQuad1;
                final int mQuad2;

/* 20 */        Name2(String str, int i, int i2, int i3) {
/* 21 */            super(str, i);
/* 22 */            this.mQuad1 = i2;
/* 23 */            this.mQuad2 = i3;
                }

                @Override // com.fasterxml.jackson.core.sym.Name
/* 26 */        public boolean equals(int i) {
/* 27 */            return false;
                }

                @Override // com.fasterxml.jackson.core.sym.Name
/* 31 */        public boolean equals(int i, int i2) {
                    return i == this.mQuad1 && i2 == this.mQuad2;
                }

                @Override // com.fasterxml.jackson.core.sym.Name
/* 37 */        public boolean equals(int[] iArr, int i) {
                    return i == 2 && iArr[0] == this.mQuad1 && iArr[1] == this.mQuad2;
                }
            }
