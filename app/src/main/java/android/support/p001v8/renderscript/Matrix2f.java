            package android.support.p001v8.renderscript;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 30 */    public class Matrix2f {
                final float[] mMat;

/* 31 */        public Matrix2f() {
/* 33 */            this.mMat = new float[4];
/* 34 */            loadIdentity();
                }

/* 43 */        public Matrix2f(float[] dataArray) {
/* 45 */            this.mMat = new float[4];
/* 46 */            System.arraycopy(dataArray, 0, this.mMat, 0, this.mMat.length);
                }

/* 55 */        public float[] getArray() {
/* 56 */            return this.mMat;
                }

/* 67 */        public float get(int x, int y) {
/* 68 */            return this.mMat[(x * 2) + y];
                }

/* 77 */        public void set(int x, int y, float v) {
/* 78 */            this.mMat[(x * 2) + y] = v;
                }

/* 84 */        public void loadIdentity() {
/* 85 */            this.mMat[0] = 1.0f;
/* 86 */            this.mMat[1] = 0.0f;
/* 88 */            this.mMat[2] = 0.0f;
/* 89 */            this.mMat[3] = 1.0f;
                }

/* 97 */        public void load(Matrix2f src) {
/* 98 */            System.arraycopy(src.getArray(), 0, this.mMat, 0, this.mMat.length);
                }

/* 107 */       public void loadRotate(float rot) {
/* 108 */           float rot2 = rot * 0.017453292f;
/* 109 */           float c = (float) Math.cos(rot2);
/* 110 */           float s = (float) Math.sin(rot2);
/* 111 */           this.mMat[0] = c;
/* 112 */           this.mMat[1] = -s;
/* 113 */           this.mMat[2] = s;
/* 114 */           this.mMat[3] = c;
                }

/* 123 */       public void loadScale(float x, float y) {
/* 124 */           loadIdentity();
/* 125 */           this.mMat[0] = x;
/* 126 */           this.mMat[3] = y;
                }

/* 136 */       public void loadMultiply(Matrix2f lhs, Matrix2f rhs) {
                    for (int i = 0; i < 2; i++) {
/* 138 */               float ri0 = 0.0f;
/* 139 */               float ri1 = 0.0f;
                        for (int j = 0; j < 2; j++) {
/* 141 */                   float rhs_ij = rhs.get(i, j);
/* 142 */                   ri0 += lhs.get(j, 0) * rhs_ij;
/* 143 */                   ri1 += lhs.get(j, 1) * rhs_ij;
                        }
/* 145 */               set(i, 0, ri0);
/* 146 */               set(i, 1, ri1);
                    }
                }

/* 155 */       public void multiply(Matrix2f rhs) {
/* 156 */           Matrix2f tmp = new Matrix2f();
/* 157 */           tmp.loadMultiply(this, rhs);
/* 158 */           load(tmp);
                }

/* 166 */       public void rotate(float rot) {
/* 167 */           Matrix2f tmp = new Matrix2f();
/* 168 */           tmp.loadRotate(rot);
/* 169 */           multiply(tmp);
                }

/* 178 */       public void scale(float x, float y) {
/* 179 */           Matrix2f tmp = new Matrix2f();
/* 180 */           tmp.loadScale(x, y);
/* 181 */           multiply(tmp);
                }

/* 186 */       public void transpose() {
/* 187 */           float temp = this.mMat[1];
/* 188 */           this.mMat[1] = this.mMat[2];
/* 189 */           this.mMat[2] = temp;
                }
            }
