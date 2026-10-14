            package android.support.p001v8.renderscript;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 30 */    public class Matrix4f {
                final float[] mMat;

/* 31 */        public Matrix4f() {
/* 33 */            this.mMat = new float[16];
/* 34 */            loadIdentity();
                }

/* 43 */        public Matrix4f(float[] dataArray) {
/* 45 */            this.mMat = new float[16];
/* 46 */            System.arraycopy(dataArray, 0, this.mMat, 0, this.mMat.length);
                }

/* 55 */        public float[] getArray() {
/* 56 */            return this.mMat;
                }

/* 67 */        public float get(int x, int y) {
/* 68 */            return this.mMat[(x * 4) + y];
                }

/* 77 */        public void set(int x, int y, float v) {
/* 78 */            this.mMat[(x * 4) + y] = v;
                }

/* 84 */        public void loadIdentity() {
/* 85 */            this.mMat[0] = 1.0f;
/* 86 */            this.mMat[1] = 0.0f;
/* 87 */            this.mMat[2] = 0.0f;
/* 88 */            this.mMat[3] = 0.0f;
/* 90 */            this.mMat[4] = 0.0f;
/* 91 */            this.mMat[5] = 1.0f;
/* 92 */            this.mMat[6] = 0.0f;
/* 93 */            this.mMat[7] = 0.0f;
/* 95 */            this.mMat[8] = 0.0f;
/* 96 */            this.mMat[9] = 0.0f;
/* 97 */            this.mMat[10] = 1.0f;
/* 98 */            this.mMat[11] = 0.0f;
/* 100 */           this.mMat[12] = 0.0f;
/* 101 */           this.mMat[13] = 0.0f;
/* 102 */           this.mMat[14] = 0.0f;
/* 103 */           this.mMat[15] = 1.0f;
                }

/* 111 */       public void load(Matrix4f src) {
/* 112 */           System.arraycopy(src.getArray(), 0, this.mMat, 0, this.mMat.length);
                }

/* 121 */       public void load(Matrix3f src) {
/* 122 */           this.mMat[0] = src.mMat[0];
/* 123 */           this.mMat[1] = src.mMat[1];
/* 124 */           this.mMat[2] = src.mMat[2];
/* 125 */           this.mMat[3] = 0.0f;
/* 127 */           this.mMat[4] = src.mMat[3];
/* 128 */           this.mMat[5] = src.mMat[4];
/* 129 */           this.mMat[6] = src.mMat[5];
/* 130 */           this.mMat[7] = 0.0f;
/* 132 */           this.mMat[8] = src.mMat[6];
/* 133 */           this.mMat[9] = src.mMat[7];
/* 134 */           this.mMat[10] = src.mMat[8];
/* 135 */           this.mMat[11] = 0.0f;
/* 137 */           this.mMat[12] = 0.0f;
/* 138 */           this.mMat[13] = 0.0f;
/* 139 */           this.mMat[14] = 0.0f;
/* 140 */           this.mMat[15] = 1.0f;
                }

/* 153 */       public void loadRotate(float rot, float x, float y, float z) {
/* 154 */           this.mMat[3] = 0.0f;
/* 155 */           this.mMat[7] = 0.0f;
/* 156 */           this.mMat[11] = 0.0f;
/* 157 */           this.mMat[12] = 0.0f;
/* 158 */           this.mMat[13] = 0.0f;
/* 159 */           this.mMat[14] = 0.0f;
/* 160 */           this.mMat[15] = 1.0f;
/* 161 */           float rot2 = rot * 0.017453292f;
/* 162 */           float c = (float) Math.cos(rot2);
/* 163 */           float s = (float) Math.sin(rot2);
/* 165 */           float len = (float) Math.sqrt((x * x) + (y * y) + (z * z));
                    if (len == 1.0f) {
/* 167 */               float recipLen = 1.0f / len;
/* 168 */               x *= recipLen;
/* 169 */               y *= recipLen;
/* 170 */               z *= recipLen;
                    }
/* 172 */           float nc = 1.0f - c;
/* 173 */           float xy = x * y;
/* 174 */           float yz = y * z;
/* 175 */           float zx = z * x;
/* 176 */           float xs = x * s;
/* 177 */           float ys = y * s;
/* 178 */           float zs = z * s;
/* 179 */           this.mMat[0] = (x * x * nc) + c;
/* 180 */           this.mMat[4] = (xy * nc) - zs;
/* 181 */           this.mMat[8] = (zx * nc) + ys;
/* 182 */           this.mMat[1] = (xy * nc) + zs;
/* 183 */           this.mMat[5] = (y * y * nc) + c;
/* 184 */           this.mMat[9] = (yz * nc) - xs;
/* 185 */           this.mMat[2] = (zx * nc) - ys;
/* 186 */           this.mMat[6] = (yz * nc) + xs;
/* 187 */           this.mMat[10] = (z * z * nc) + c;
                }

/* 197 */       public void loadScale(float x, float y, float z) {
/* 198 */           loadIdentity();
/* 199 */           this.mMat[0] = x;
/* 200 */           this.mMat[5] = y;
/* 201 */           this.mMat[10] = z;
                }

/* 212 */       public void loadTranslate(float x, float y, float z) {
/* 213 */           loadIdentity();
/* 214 */           this.mMat[12] = x;
/* 215 */           this.mMat[13] = y;
/* 216 */           this.mMat[14] = z;
                }

/* 226 */       public void loadMultiply(Matrix4f lhs, Matrix4f rhs) {
                    for (int i = 0; i < 4; i++) {
/* 228 */               float ri0 = 0.0f;
/* 229 */               float ri1 = 0.0f;
/* 230 */               float ri2 = 0.0f;
/* 231 */               float ri3 = 0.0f;
                        for (int j = 0; j < 4; j++) {
/* 233 */                   float rhs_ij = rhs.get(i, j);
/* 234 */                   ri0 += lhs.get(j, 0) * rhs_ij;
/* 235 */                   ri1 += lhs.get(j, 1) * rhs_ij;
/* 236 */                   ri2 += lhs.get(j, 2) * rhs_ij;
/* 237 */                   ri3 += lhs.get(j, 3) * rhs_ij;
                        }
/* 239 */               set(i, 0, ri0);
/* 240 */               set(i, 1, ri1);
/* 241 */               set(i, 2, ri2);
/* 242 */               set(i, 3, ri3);
                    }
                }

/* 256 */       public void loadOrtho(float l, float r, float b, float t, float n, float f) {
/* 257 */           loadIdentity();
/* 258 */           this.mMat[0] = 2.0f / (r - l);
/* 259 */           this.mMat[5] = 2.0f / (t - b);
/* 260 */           this.mMat[10] = (-2.0f) / (f - n);
/* 261 */           this.mMat[12] = (-(r + l)) / (r - l);
/* 262 */           this.mMat[13] = (-(t + b)) / (t - b);
/* 263 */           this.mMat[14] = (-(f + n)) / (f - n);
                }

/* 276 */       public void loadOrthoWindow(int w, int h) {
/* 277 */           loadOrtho(0.0f, w, h, 0.0f, -1.0f, 1.0f);
                }

/* 291 */       public void loadFrustum(float l, float r, float b, float t, float n, float f) {
/* 292 */           loadIdentity();
/* 293 */           this.mMat[0] = (2.0f * n) / (r - l);
/* 294 */           this.mMat[5] = (2.0f * n) / (t - b);
/* 295 */           this.mMat[8] = (r + l) / (r - l);
/* 296 */           this.mMat[9] = (t + b) / (t - b);
/* 297 */           this.mMat[10] = (-(f + n)) / (f - n);
/* 298 */           this.mMat[11] = -1.0f;
/* 299 */           this.mMat[14] = (((-2.0f) * f) * n) / (f - n);
/* 300 */           this.mMat[15] = 0.0f;
                }

/* 311 */       public void loadPerspective(float fovy, float aspect, float near, float far) {
/* 312 */           float top = near * ((float) Math.tan((float) ((((double) fovy) * 3.141592653589793d) / 360.0d)));
/* 313 */           float bottom = -top;
/* 314 */           float left = bottom * aspect;
/* 315 */           float right = top * aspect;
/* 316 */           loadFrustum(left, right, bottom, top, near, far);
                }

/* 328 */       public void loadProjectionNormalized(int w, int h) {
/* 329 */           Matrix4f m1 = new Matrix4f();
/* 330 */           Matrix4f m2 = new Matrix4f();
/* 332 */           if (w > h) {
/* 333 */               float aspect = w / h;
/* 334 */               m1.loadFrustum(-aspect, aspect, -1.0f, 1.0f, 1.0f, 100.0f);
                    } else {
/* 336 */               float aspect2 = h / w;
/* 337 */               m1.loadFrustum(-1.0f, 1.0f, -aspect2, aspect2, 1.0f, 100.0f);
                    }
/* 340 */           m2.loadRotate(180.0f, 0.0f, 1.0f, 0.0f);
/* 341 */           m1.loadMultiply(m1, m2);
/* 343 */           m2.loadScale(-2.0f, 2.0f, 1.0f);
/* 344 */           m1.loadMultiply(m1, m2);
/* 346 */           m2.loadTranslate(0.0f, 0.0f, 2.0f);
/* 347 */           m1.loadMultiply(m1, m2);
/* 349 */           load(m1);
                }

/* 357 */       public void multiply(Matrix4f rhs) {
/* 358 */           Matrix4f tmp = new Matrix4f();
/* 359 */           tmp.loadMultiply(this, rhs);
/* 360 */           load(tmp);
                }

/* 371 */       public void rotate(float rot, float x, float y, float z) {
/* 372 */           Matrix4f tmp = new Matrix4f();
/* 373 */           tmp.loadRotate(rot, x, y, z);
/* 374 */           multiply(tmp);
                }

/* 385 */       public void scale(float x, float y, float z) {
/* 386 */           Matrix4f tmp = new Matrix4f();
/* 387 */           tmp.loadScale(x, y, z);
/* 388 */           multiply(tmp);
                }

/* 399 */       public void translate(float x, float y, float z) {
/* 400 */           Matrix4f tmp = new Matrix4f();
/* 401 */           tmp.loadTranslate(x, y, z);
/* 402 */           multiply(tmp);
                }

/* 404 */       private float computeCofactor(int i, int j) {
/* 405 */           int c0 = (i + 1) % 4;
/* 406 */           int c1 = (i + 2) % 4;
/* 407 */           int c2 = (i + 3) % 4;
/* 408 */           int r0 = (j + 1) % 4;
/* 409 */           int r1 = (j + 2) % 4;
/* 410 */           int r2 = (j + 3) % 4;
/* 412 */           float minor = ((this.mMat[(r0 * 4) + c0] * ((this.mMat[(r1 * 4) + c1] * this.mMat[(r2 * 4) + c2]) - (this.mMat[(r2 * 4) + c1] * this.mMat[(r1 * 4) + c2]))) - (this.mMat[(r1 * 4) + c0] * ((this.mMat[(r0 * 4) + c1] * this.mMat[(r2 * 4) + c2]) - (this.mMat[(r2 * 4) + c1] * this.mMat[(r0 * 4) + c2])))) + (this.mMat[(r2 * 4) + c0] * ((this.mMat[(r0 * 4) + c1] * this.mMat[(r1 * 4) + c2]) - (this.mMat[(r1 * 4) + c1] * this.mMat[(r0 * 4) + c2])));
/* 419 */           if (((i + j) & 1) == 0) {
                        return minor;
                    }
                    float cofactor = -minor;
/* 420 */           return cofactor;
                }

/* 427 */       public boolean inverse() {
/* 428 */           Matrix4f result = new Matrix4f();
                    for (int i = 0; i < 4; i++) {
                        for (int j = 0; j < 4; j++) {
/* 432 */                   result.mMat[(i * 4) + j] = computeCofactor(i, j);
                        }
                    }
/* 437 */           float det = (this.mMat[0] * result.mMat[0]) + (this.mMat[4] * result.mMat[1]) + (this.mMat[8] * result.mMat[2]) + (this.mMat[12] * result.mMat[3]);
                    if (Math.abs(det) < 1.0E-6d) {
                        return false;
                    }
/* 444 */           float det2 = 1.0f / det;
                    for (int i2 = 0; i2 < 16; i2++) {
/* 446 */               this.mMat[i2] = result.mMat[i2] * det2;
                    }
                    return true;
                }

/* 456 */       public boolean inverseTranspose() {
/* 457 */           Matrix4f result = new Matrix4f();
                    for (int i = 0; i < 4; i++) {
                        for (int j = 0; j < 4; j++) {
/* 461 */                   result.mMat[(j * 4) + i] = computeCofactor(i, j);
                        }
                    }
/* 465 */           float det = (this.mMat[0] * result.mMat[0]) + (this.mMat[4] * result.mMat[4]) + (this.mMat[8] * result.mMat[8]) + (this.mMat[12] * result.mMat[12]);
                    if (Math.abs(det) < 1.0E-6d) {
                        return false;
                    }
/* 472 */           float det2 = 1.0f / det;
                    for (int i2 = 0; i2 < 16; i2++) {
/* 474 */               this.mMat[i2] = result.mMat[i2] * det2;
                    }
/* 477 */           return true;
                }

/* 483 */       public void transpose() {
                    for (int i = 0; i < 3; i++) {
                        for (int j = i + 1; j < 4; j++) {
/* 486 */                   float temp = this.mMat[(i * 4) + j];
/* 487 */                   this.mMat[(i * 4) + j] = this.mMat[(j * 4) + i];
/* 488 */                   this.mMat[(j * 4) + i] = temp;
                        }
                    }
                }
            }
