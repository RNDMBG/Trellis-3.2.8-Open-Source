            package android.support.p001v8.renderscript;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 30 */    public class Matrix3f {
                final float[] mMat;

/* 31 */        public Matrix3f() {
/* 33 */            this.mMat = new float[9];
/* 34 */            loadIdentity();
                }

/* 43 */        public Matrix3f(float[] dataArray) {
/* 45 */            this.mMat = new float[9];
/* 46 */            System.arraycopy(dataArray, 0, this.mMat, 0, this.mMat.length);
                }

/* 55 */        public float[] getArray() {
/* 56 */            return this.mMat;
                }

/* 67 */        public float get(int x, int y) {
/* 68 */            return this.mMat[(x * 3) + y];
                }

/* 77 */        public void set(int x, int y, float v) {
/* 78 */            this.mMat[(x * 3) + y] = v;
                }

/* 84 */        public void loadIdentity() {
/* 85 */            this.mMat[0] = 1.0f;
/* 86 */            this.mMat[1] = 0.0f;
/* 87 */            this.mMat[2] = 0.0f;
/* 89 */            this.mMat[3] = 0.0f;
/* 90 */            this.mMat[4] = 1.0f;
/* 91 */            this.mMat[5] = 0.0f;
/* 93 */            this.mMat[6] = 0.0f;
/* 94 */            this.mMat[7] = 0.0f;
/* 95 */            this.mMat[8] = 1.0f;
                }

/* 103 */       public void load(Matrix3f src) {
/* 104 */           System.arraycopy(src.getArray(), 0, this.mMat, 0, this.mMat.length);
                }

/* 117 */       public void loadRotate(float rot, float x, float y, float z) {
/* 118 */           float rot2 = rot * 0.017453292f;
/* 119 */           float c = (float) Math.cos(rot2);
/* 120 */           float s = (float) Math.sin(rot2);
/* 122 */           float len = (float) Math.sqrt((x * x) + (y * y) + (z * z));
                    if (len == 1.0f) {
/* 124 */               float recipLen = 1.0f / len;
/* 125 */               x *= recipLen;
/* 126 */               y *= recipLen;
/* 127 */               z *= recipLen;
                    }
/* 129 */           float nc = 1.0f - c;
/* 130 */           float xy = x * y;
/* 131 */           float yz = y * z;
/* 132 */           float zx = z * x;
/* 133 */           float xs = x * s;
/* 134 */           float ys = y * s;
/* 135 */           float zs = z * s;
/* 136 */           this.mMat[0] = (x * x * nc) + c;
/* 137 */           this.mMat[3] = (xy * nc) - zs;
/* 138 */           this.mMat[6] = (zx * nc) + ys;
/* 139 */           this.mMat[1] = (xy * nc) + zs;
/* 140 */           this.mMat[4] = (y * y * nc) + c;
/* 141 */           this.mMat[7] = (yz * nc) - xs;
/* 142 */           this.mMat[2] = (zx * nc) - ys;
/* 143 */           this.mMat[5] = (yz * nc) + xs;
/* 144 */           this.mMat[8] = (z * z * nc) + c;
                }

/* 152 */       public void loadRotate(float rot) {
/* 153 */           loadIdentity();
/* 155 */           float rot2 = rot * 0.017453292f;
/* 156 */           float c = (float) Math.cos(rot2);
/* 157 */           float s = (float) Math.sin(rot2);
/* 158 */           this.mMat[0] = c;
/* 159 */           this.mMat[1] = -s;
/* 160 */           this.mMat[3] = s;
/* 161 */           this.mMat[4] = c;
                }

/* 170 */       public void loadScale(float x, float y) {
/* 171 */           loadIdentity();
/* 172 */           this.mMat[0] = x;
/* 173 */           this.mMat[4] = y;
                }

/* 183 */       public void loadScale(float x, float y, float z) {
/* 184 */           loadIdentity();
/* 185 */           this.mMat[0] = x;
/* 186 */           this.mMat[4] = y;
/* 187 */           this.mMat[8] = z;
                }

/* 197 */       public void loadTranslate(float x, float y) {
/* 198 */           loadIdentity();
/* 199 */           this.mMat[6] = x;
/* 200 */           this.mMat[7] = y;
                }

/* 210 */       public void loadMultiply(Matrix3f lhs, Matrix3f rhs) {
                    for (int i = 0; i < 3; i++) {
/* 212 */               float ri0 = 0.0f;
/* 213 */               float ri1 = 0.0f;
/* 214 */               float ri2 = 0.0f;
                        for (int j = 0; j < 3; j++) {
/* 216 */                   float rhs_ij = rhs.get(i, j);
/* 217 */                   ri0 += lhs.get(j, 0) * rhs_ij;
/* 218 */                   ri1 += lhs.get(j, 1) * rhs_ij;
/* 219 */                   ri2 += lhs.get(j, 2) * rhs_ij;
                        }
/* 221 */               set(i, 0, ri0);
/* 222 */               set(i, 1, ri1);
/* 223 */               set(i, 2, ri2);
                    }
                }

/* 232 */       public void multiply(Matrix3f rhs) {
/* 233 */           Matrix3f tmp = new Matrix3f();
/* 234 */           tmp.loadMultiply(this, rhs);
/* 235 */           load(tmp);
                }

/* 247 */       public void rotate(float rot, float x, float y, float z) {
/* 248 */           Matrix3f tmp = new Matrix3f();
/* 249 */           tmp.loadRotate(rot, x, y, z);
/* 250 */           multiply(tmp);
                }

/* 259 */       public void rotate(float rot) {
/* 260 */           Matrix3f tmp = new Matrix3f();
/* 261 */           tmp.loadRotate(rot);
/* 262 */           multiply(tmp);
                }

/* 272 */       public void scale(float x, float y) {
/* 273 */           Matrix3f tmp = new Matrix3f();
/* 274 */           tmp.loadScale(x, y);
/* 275 */           multiply(tmp);
                }

/* 286 */       public void scale(float x, float y, float z) {
/* 287 */           Matrix3f tmp = new Matrix3f();
/* 288 */           tmp.loadScale(x, y, z);
/* 289 */           multiply(tmp);
                }

/* 299 */       public void translate(float x, float y) {
/* 300 */           Matrix3f tmp = new Matrix3f();
/* 301 */           tmp.loadTranslate(x, y);
/* 302 */           multiply(tmp);
                }

/* 308 */       public void transpose() {
                    for (int i = 0; i < 2; i++) {
                        for (int j = i + 1; j < 3; j++) {
/* 311 */                   float temp = this.mMat[(i * 3) + j];
/* 312 */                   this.mMat[(i * 3) + j] = this.mMat[(j * 3) + i];
/* 313 */                   this.mMat[(j * 3) + i] = temp;
                        }
                    }
                }
            }
