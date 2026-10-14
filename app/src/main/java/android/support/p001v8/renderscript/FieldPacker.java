            package android.support.p001v8.renderscript;

            import android.renderscript.Byte2;
            import android.renderscript.Byte3;
            import android.renderscript.Byte4;
            import android.renderscript.Double2;
            import android.renderscript.Double3;
            import android.renderscript.Double4;
            import android.renderscript.Float2;
            import android.renderscript.Float3;
            import android.renderscript.Float4;
            import android.renderscript.Int2;
            import android.renderscript.Int3;
            import android.renderscript.Int4;
            import android.renderscript.Long2;
            import android.renderscript.Long3;
            import android.renderscript.Long4;
            import android.renderscript.Matrix2f;
            import android.renderscript.Matrix3f;
            import android.renderscript.Matrix4f;
            import android.renderscript.Short2;
            import android.renderscript.Short3;
            import android.renderscript.Short4;
            import android.util.Log;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 32 */    public class FieldPacker {
                private final byte[] mData;
                private int mLen;

                /* JADX INFO: renamed from: mN */
                private android.renderscript.FieldPacker f26mN;
                private int mPos = 0;

/* 33 */        public FieldPacker(int len) {
/* 36 */            this.mLen = len;
/* 37 */            this.mData = new byte[len];
/* 38 */            if (RenderScript.shouldThunk()) {
/* 39 */                this.f26mN = new android.renderscript.FieldPacker(len);
                    }
                }

/* 43 */        public void align(int v) {
/* 44 */            if (RenderScript.shouldThunk()) {
/* 45 */                this.f26mN.align(v);
/* 55 */                return;
                    }
/* 48 */            if (v <= 0 || ((v - 1) & v) != 0) {
/* 49 */                throw new RSIllegalArgumentException("argument must be a non-negative non-zero power of 2: " + v);
                    }
/* 52 */            while ((this.mPos & (v - 1)) != 0) {
/* 53 */                byte[] bArr = this.mData;
                        int i = this.mPos;
                        this.mPos = i + 1;
                        bArr[i] = 0;
                    }
                }

/* 57 */        public void reset() {
/* 58 */            if (RenderScript.shouldThunk()) {
/* 59 */                this.f26mN.reset();
                    } else {
/* 62 */                this.mPos = 0;
                    }
                }

/* 64 */        public void reset(int i) {
/* 65 */            if (RenderScript.shouldThunk()) {
/* 66 */                this.f26mN.reset(i);
                    } else {
/* 69 */                if (i < 0 || i >= this.mLen) {
/* 70 */                    throw new RSIllegalArgumentException("out of range argument: " + i);
                        }
/* 72 */                this.mPos = i;
                    }
                }

/* 75 */        public void skip(int i) {
/* 76 */            if (RenderScript.shouldThunk()) {
/* 77 */                this.f26mN.skip(i);
/* 85 */                return;
                    }
/* 80 */            int res = this.mPos + i;
/* 81 */            if (res < 0 || res > this.mLen) {
/* 82 */                throw new RSIllegalArgumentException("out of range argument: " + i);
                    }
/* 84 */            this.mPos = res;
                }

/* 87 */        public void addI8(byte v) {
/* 88 */            if (RenderScript.shouldThunk()) {
/* 89 */                this.f26mN.addI8(v);
/* 93 */                return;
                    }
/* 92 */            byte[] bArr = this.mData;
                    int i = this.mPos;
                    this.mPos = i + 1;
                    bArr[i] = v;
                }

/* 95 */        public void addI16(short v) {
/* 96 */            if (RenderScript.shouldThunk()) {
/* 97 */                this.f26mN.addI16(v);
/* 103 */               return;
                    }
/* 100 */           align(2);
/* 101 */           byte[] bArr = this.mData;
                    int i = this.mPos;
                    this.mPos = i + 1;
                    bArr[i] = (byte) (v & 255);
/* 102 */           byte[] bArr2 = this.mData;
                    int i2 = this.mPos;
                    this.mPos = i2 + 1;
                    bArr2[i2] = (byte) (v >> 8);
                }

/* 105 */       public void addI32(int v) {
/* 106 */           if (RenderScript.shouldThunk()) {
/* 107 */               this.f26mN.addI32(v);
/* 115 */               return;
                    }
/* 110 */           align(4);
/* 111 */           byte[] bArr = this.mData;
                    int i = this.mPos;
                    this.mPos = i + 1;
                    bArr[i] = (byte) (v & 255);
/* 112 */           byte[] bArr2 = this.mData;
                    int i2 = this.mPos;
                    this.mPos = i2 + 1;
                    bArr2[i2] = (byte) ((v >> 8) & 255);
/* 113 */           byte[] bArr3 = this.mData;
                    int i3 = this.mPos;
                    this.mPos = i3 + 1;
                    bArr3[i3] = (byte) ((v >> 16) & 255);
/* 114 */           byte[] bArr4 = this.mData;
                    int i4 = this.mPos;
                    this.mPos = i4 + 1;
                    bArr4[i4] = (byte) ((v >> 24) & 255);
                }

/* 117 */       public void addI64(long v) {
/* 118 */           if (RenderScript.shouldThunk()) {
/* 119 */               this.f26mN.addI64(v);
/* 131 */               return;
                    }
/* 122 */           align(8);
/* 123 */           byte[] bArr = this.mData;
                    int i = this.mPos;
                    this.mPos = i + 1;
                    bArr[i] = (byte) (v & 255);
/* 124 */           byte[] bArr2 = this.mData;
                    int i2 = this.mPos;
                    this.mPos = i2 + 1;
                    bArr2[i2] = (byte) ((v >> 8) & 255);
/* 125 */           byte[] bArr3 = this.mData;
                    int i3 = this.mPos;
                    this.mPos = i3 + 1;
                    bArr3[i3] = (byte) ((v >> 16) & 255);
/* 126 */           byte[] bArr4 = this.mData;
                    int i4 = this.mPos;
                    this.mPos = i4 + 1;
                    bArr4[i4] = (byte) ((v >> 24) & 255);
/* 127 */           byte[] bArr5 = this.mData;
                    int i5 = this.mPos;
                    this.mPos = i5 + 1;
                    bArr5[i5] = (byte) ((v >> 32) & 255);
/* 128 */           byte[] bArr6 = this.mData;
                    int i6 = this.mPos;
                    this.mPos = i6 + 1;
                    bArr6[i6] = (byte) ((v >> 40) & 255);
/* 129 */           byte[] bArr7 = this.mData;
                    int i7 = this.mPos;
                    this.mPos = i7 + 1;
                    bArr7[i7] = (byte) ((v >> 48) & 255);
/* 130 */           byte[] bArr8 = this.mData;
                    int i8 = this.mPos;
                    this.mPos = i8 + 1;
                    bArr8[i8] = (byte) ((v >> 56) & 255);
                }

/* 133 */       public void addU8(short v) {
/* 134 */           if (RenderScript.shouldThunk()) {
/* 135 */               this.f26mN.addU8(v);
/* 142 */               return;
                    }
/* 138 */           if (v < 0 || v > 255) {
/* 139 */               throw new IllegalArgumentException("Saving value out of range for type");
                    }
/* 141 */           byte[] bArr = this.mData;
                    int i = this.mPos;
                    this.mPos = i + 1;
                    bArr[i] = (byte) v;
                }

/* 144 */       public void addU16(int v) {
/* 145 */           if (RenderScript.shouldThunk()) {
/* 146 */               this.f26mN.addU16(v);
/* 156 */               return;
                    }
/* 149 */           if (v < 0 || v > 65535) {
/* 150 */               Log.e("rs", "FieldPacker.addU16( " + v + " )");
/* 151 */               throw new IllegalArgumentException("Saving value out of range for type");
                    }
/* 153 */           align(2);
/* 154 */           byte[] bArr = this.mData;
                    int i = this.mPos;
                    this.mPos = i + 1;
                    bArr[i] = (byte) (v & 255);
/* 155 */           byte[] bArr2 = this.mData;
                    int i2 = this.mPos;
                    this.mPos = i2 + 1;
                    bArr2[i2] = (byte) (v >> 8);
                }

/* 158 */       public void addU32(long v) {
/* 159 */           if (RenderScript.shouldThunk()) {
/* 160 */               this.f26mN.addU32(v);
/* 172 */               return;
                    }
                    if (v < 0 || v > 4294967295L) {
/* 164 */               Log.e("rs", "FieldPacker.addU32( " + v + " )");
/* 165 */               throw new IllegalArgumentException("Saving value out of range for type");
                    }
/* 167 */           align(4);
/* 168 */           byte[] bArr = this.mData;
                    int i = this.mPos;
                    this.mPos = i + 1;
                    bArr[i] = (byte) (v & 255);
/* 169 */           byte[] bArr2 = this.mData;
                    int i2 = this.mPos;
                    this.mPos = i2 + 1;
                    bArr2[i2] = (byte) ((v >> 8) & 255);
/* 170 */           byte[] bArr3 = this.mData;
                    int i3 = this.mPos;
                    this.mPos = i3 + 1;
                    bArr3[i3] = (byte) ((v >> 16) & 255);
/* 171 */           byte[] bArr4 = this.mData;
                    int i4 = this.mPos;
                    this.mPos = i4 + 1;
                    bArr4[i4] = (byte) ((v >> 24) & 255);
                }

/* 174 */       public void addU64(long v) {
/* 175 */           if (RenderScript.shouldThunk()) {
/* 176 */               this.f26mN.addU64(v);
/* 192 */               return;
                    }
                    if (v < 0) {
/* 180 */               Log.e("rs", "FieldPacker.addU64( " + v + " )");
/* 181 */               throw new IllegalArgumentException("Saving value out of range for type");
                    }
/* 183 */           align(8);
/* 184 */           byte[] bArr = this.mData;
                    int i = this.mPos;
                    this.mPos = i + 1;
                    bArr[i] = (byte) (v & 255);
/* 185 */           byte[] bArr2 = this.mData;
                    int i2 = this.mPos;
                    this.mPos = i2 + 1;
                    bArr2[i2] = (byte) ((v >> 8) & 255);
/* 186 */           byte[] bArr3 = this.mData;
                    int i3 = this.mPos;
                    this.mPos = i3 + 1;
                    bArr3[i3] = (byte) ((v >> 16) & 255);
/* 187 */           byte[] bArr4 = this.mData;
                    int i4 = this.mPos;
                    this.mPos = i4 + 1;
                    bArr4[i4] = (byte) ((v >> 24) & 255);
/* 188 */           byte[] bArr5 = this.mData;
                    int i5 = this.mPos;
                    this.mPos = i5 + 1;
                    bArr5[i5] = (byte) ((v >> 32) & 255);
/* 189 */           byte[] bArr6 = this.mData;
                    int i6 = this.mPos;
                    this.mPos = i6 + 1;
                    bArr6[i6] = (byte) ((v >> 40) & 255);
/* 190 */           byte[] bArr7 = this.mData;
                    int i7 = this.mPos;
                    this.mPos = i7 + 1;
                    bArr7[i7] = (byte) ((v >> 48) & 255);
/* 191 */           byte[] bArr8 = this.mData;
                    int i8 = this.mPos;
                    this.mPos = i8 + 1;
                    bArr8[i8] = (byte) ((v >> 56) & 255);
                }

/* 194 */       public void addF32(float v) {
/* 195 */           if (RenderScript.shouldThunk()) {
/* 196 */               this.f26mN.addF32(v);
                    } else {
/* 199 */               addI32(Float.floatToRawIntBits(v));
                    }
                }

/* 202 */       public void addF64(double v) {
/* 203 */           if (RenderScript.shouldThunk()) {
/* 204 */               this.f26mN.addF64(v);
                    } else {
/* 207 */               addI64(Double.doubleToRawLongBits(v));
                    }
                }

/* 210 */       public void addObj(BaseObj obj) {
/* 211 */           if (RenderScript.shouldThunk()) {
/* 212 */               if (obj != null) {
/* 213 */                   this.f26mN.addObj(obj.getNObj());
/* 224 */                   return;
                        } else {
/* 215 */                   this.f26mN.addObj(null);
/* 224 */                   return;
                        }
                    }
/* 219 */           if (obj != null) {
/* 220 */               addI32(obj.getID(null));
                    } else {
/* 222 */               addI32(0);
                    }
                }

/* 226 */       public void addF32(Float2 v) {
/* 227 */           if (RenderScript.shouldThunk()) {
/* 228 */               this.f26mN.addF32(new Float2(v.f27x, v.f28y));
                    } else {
/* 231 */               addF32(v.f27x);
/* 232 */               addF32(v.f28y);
                    }
                }

/* 234 */       public void addF32(Float3 v) {
/* 235 */           if (RenderScript.shouldThunk()) {
/* 236 */               this.f26mN.addF32(new Float3(v.f29x, v.f30y, v.f31z));
/* 242 */               return;
                    }
/* 239 */           addF32(v.f29x);
/* 240 */           addF32(v.f30y);
/* 241 */           addF32(v.f31z);
                }

/* 243 */       public void addF32(Float4 v) {
/* 244 */           if (RenderScript.shouldThunk()) {
/* 245 */               this.f26mN.addF32(new Float4(v.f33x, v.f34y, v.f35z, v.f32w));
/* 252 */               return;
                    }
/* 248 */           addF32(v.f33x);
/* 249 */           addF32(v.f34y);
/* 250 */           addF32(v.f35z);
/* 251 */           addF32(v.f32w);
                }

/* 254 */       public void addF64(Double2 v) {
/* 255 */           if (RenderScript.shouldThunk()) {
/* 256 */               this.f26mN.addF64(new Double2(v.f14x, v.f15y));
                    } else {
/* 259 */               addF64(v.f14x);
/* 260 */               addF64(v.f15y);
                    }
                }

/* 262 */       public void addF64(Double3 v) {
/* 263 */           if (RenderScript.shouldThunk()) {
/* 264 */               this.f26mN.addF64(new Double3(v.f16x, v.f17y, v.f18z));
/* 270 */               return;
                    }
/* 267 */           addF64(v.f16x);
/* 268 */           addF64(v.f17y);
/* 269 */           addF64(v.f18z);
                }

/* 271 */       public void addF64(Double4 v) {
/* 272 */           if (RenderScript.shouldThunk()) {
/* 273 */               this.f26mN.addF64(new Double4(v.f20x, v.f21y, v.f22z, v.f19w));
/* 280 */               return;
                    }
/* 276 */           addF64(v.f20x);
/* 277 */           addF64(v.f21y);
/* 278 */           addF64(v.f22z);
/* 279 */           addF64(v.f19w);
                }

/* 282 */       public void addI8(Byte2 v) {
/* 283 */           if (RenderScript.shouldThunk()) {
/* 284 */               this.f26mN.addI8(new Byte2(v.f5x, v.f6y));
                    } else {
/* 287 */               addI8(v.f5x);
/* 288 */               addI8(v.f6y);
                    }
                }

/* 290 */       public void addI8(Byte3 v) {
/* 291 */           if (RenderScript.shouldThunk()) {
/* 292 */               this.f26mN.addI8(new Byte3(v.f7x, v.f8y, v.f9z));
/* 298 */               return;
                    }
/* 295 */           addI8(v.f7x);
/* 296 */           addI8(v.f8y);
/* 297 */           addI8(v.f9z);
                }

/* 299 */       public void addI8(Byte4 v) {
/* 300 */           if (RenderScript.shouldThunk()) {
/* 301 */               this.f26mN.addI8(new Byte4(v.f11x, v.f12y, v.f13z, v.f10w));
/* 308 */               return;
                    }
/* 304 */           addI8(v.f11x);
/* 305 */           addI8(v.f12y);
/* 306 */           addI8(v.f13z);
/* 307 */           addI8(v.f10w);
                }

/* 310 */       public void addU8(Short2 v) {
/* 311 */           if (RenderScript.shouldThunk()) {
/* 312 */               this.f26mN.addU8(new Short2(v.f70x, v.f71y));
                    } else {
/* 315 */               addU8(v.f70x);
/* 316 */               addU8(v.f71y);
                    }
                }

/* 318 */       public void addU8(Short3 v) {
/* 319 */           if (RenderScript.shouldThunk()) {
/* 320 */               this.f26mN.addU8(new Short3(v.f72x, v.f73y, v.f74z));
/* 326 */               return;
                    }
/* 323 */           addU8(v.f72x);
/* 324 */           addU8(v.f73y);
/* 325 */           addU8(v.f74z);
                }

/* 327 */       public void addU8(Short4 v) {
/* 328 */           if (RenderScript.shouldThunk()) {
/* 329 */               this.f26mN.addU8(new Short4(v.f76x, v.f77y, v.f78z, v.f75w));
/* 336 */               return;
                    }
/* 332 */           addU8(v.f76x);
/* 333 */           addU8(v.f77y);
/* 334 */           addU8(v.f78z);
/* 335 */           addU8(v.f75w);
                }

/* 338 */       public void addI16(Short2 v) {
/* 339 */           if (RenderScript.shouldThunk()) {
/* 340 */               this.f26mN.addI16(new Short2(v.f70x, v.f71y));
                    } else {
/* 343 */               addI16(v.f70x);
/* 344 */               addI16(v.f71y);
                    }
                }

/* 346 */       public void addI16(Short3 v) {
/* 347 */           if (RenderScript.shouldThunk()) {
/* 348 */               this.f26mN.addI16(new Short3(v.f72x, v.f73y, v.f74z));
/* 354 */               return;
                    }
/* 351 */           addI16(v.f72x);
/* 352 */           addI16(v.f73y);
/* 353 */           addI16(v.f74z);
                }

/* 355 */       public void addI16(Short4 v) {
/* 356 */           if (RenderScript.shouldThunk()) {
/* 357 */               this.f26mN.addI16(new Short4(v.f76x, v.f77y, v.f78z, v.f75w));
/* 364 */               return;
                    }
/* 360 */           addI16(v.f76x);
/* 361 */           addI16(v.f77y);
/* 362 */           addI16(v.f78z);
/* 363 */           addI16(v.f75w);
                }

/* 366 */       public void addU16(Int2 v) {
/* 367 */           if (RenderScript.shouldThunk()) {
/* 368 */               this.f26mN.addU16(new Int2(v.f36x, v.f37y));
                    } else {
/* 371 */               addU16(v.f36x);
/* 372 */               addU16(v.f37y);
                    }
                }

/* 374 */       public void addU16(Int3 v) {
/* 375 */           if (RenderScript.shouldThunk()) {
/* 376 */               this.f26mN.addU16(new Int3(v.f38x, v.f39y, v.f40z));
/* 382 */               return;
                    }
/* 379 */           addU16(v.f38x);
/* 380 */           addU16(v.f39y);
/* 381 */           addU16(v.f40z);
                }

/* 383 */       public void addU16(Int4 v) {
/* 384 */           if (RenderScript.shouldThunk()) {
/* 385 */               this.f26mN.addU16(new Int4(v.f42x, v.f43y, v.f44z, v.f41w));
/* 392 */               return;
                    }
/* 388 */           addU16(v.f42x);
/* 389 */           addU16(v.f43y);
/* 390 */           addU16(v.f44z);
/* 391 */           addU16(v.f41w);
                }

/* 394 */       public void addI32(Int2 v) {
/* 395 */           if (RenderScript.shouldThunk()) {
/* 396 */               this.f26mN.addI32(new Int2(v.f36x, v.f37y));
                    } else {
/* 399 */               addI32(v.f36x);
/* 400 */               addI32(v.f37y);
                    }
                }

/* 402 */       public void addI32(Int3 v) {
/* 403 */           if (RenderScript.shouldThunk()) {
/* 404 */               this.f26mN.addI32(new Int3(v.f38x, v.f39y, v.f40z));
/* 410 */               return;
                    }
/* 407 */           addI32(v.f38x);
/* 408 */           addI32(v.f39y);
/* 409 */           addI32(v.f40z);
                }

/* 411 */       public void addI32(Int4 v) {
/* 412 */           if (RenderScript.shouldThunk()) {
/* 413 */               this.f26mN.addI32(new Int4(v.f42x, v.f43y, v.f44z, v.f41w));
/* 420 */               return;
                    }
/* 416 */           addI32(v.f42x);
/* 417 */           addI32(v.f43y);
/* 418 */           addI32(v.f44z);
/* 419 */           addI32(v.f41w);
                }

/* 422 */       public void addU32(Long2 v) {
/* 423 */           if (RenderScript.shouldThunk()) {
/* 424 */               this.f26mN.addU32(new Long2(v.f45x, v.f46y));
                    } else {
/* 427 */               addU32(v.f45x);
/* 428 */               addU32(v.f46y);
                    }
                }

/* 430 */       public void addU32(Long3 v) {
/* 431 */           if (RenderScript.shouldThunk()) {
/* 432 */               this.f26mN.addU32(new Long3(v.f47x, v.f48y, v.f49z));
/* 438 */               return;
                    }
/* 435 */           addU32(v.f47x);
/* 436 */           addU32(v.f48y);
/* 437 */           addU32(v.f49z);
                }

/* 439 */       public void addU32(Long4 v) {
/* 440 */           if (RenderScript.shouldThunk()) {
/* 441 */               this.f26mN.addU32(new Long4(v.f51x, v.f52y, v.f53z, v.f50w));
/* 448 */               return;
                    }
/* 444 */           addU32(v.f51x);
/* 445 */           addU32(v.f52y);
/* 446 */           addU32(v.f53z);
/* 447 */           addU32(v.f50w);
                }

/* 450 */       public void addI64(Long2 v) {
/* 451 */           if (RenderScript.shouldThunk()) {
/* 452 */               this.f26mN.addI64(new Long2(v.f45x, v.f46y));
                    } else {
/* 455 */               addI64(v.f45x);
/* 456 */               addI64(v.f46y);
                    }
                }

/* 458 */       public void addI64(Long3 v) {
/* 459 */           if (RenderScript.shouldThunk()) {
/* 460 */               this.f26mN.addI64(new Long3(v.f47x, v.f48y, v.f49z));
/* 466 */               return;
                    }
/* 463 */           addI64(v.f47x);
/* 464 */           addI64(v.f48y);
/* 465 */           addI64(v.f49z);
                }

/* 467 */       public void addI64(Long4 v) {
/* 468 */           if (RenderScript.shouldThunk()) {
/* 469 */               this.f26mN.addI64(new Long4(v.f51x, v.f52y, v.f53z, v.f50w));
/* 476 */               return;
                    }
/* 472 */           addI64(v.f51x);
/* 473 */           addI64(v.f52y);
/* 474 */           addI64(v.f53z);
/* 475 */           addI64(v.f50w);
                }

/* 478 */       public void addU64(Long2 v) {
/* 479 */           if (RenderScript.shouldThunk()) {
/* 480 */               this.f26mN.addU64(new Long2(v.f45x, v.f46y));
                    } else {
/* 483 */               addU64(v.f45x);
/* 484 */               addU64(v.f46y);
                    }
                }

/* 486 */       public void addU64(Long3 v) {
/* 487 */           if (RenderScript.shouldThunk()) {
/* 488 */               this.f26mN.addU64(new Long3(v.f47x, v.f48y, v.f49z));
/* 494 */               return;
                    }
/* 491 */           addU64(v.f47x);
/* 492 */           addU64(v.f48y);
/* 493 */           addU64(v.f49z);
                }

/* 495 */       public void addU64(Long4 v) {
/* 496 */           if (RenderScript.shouldThunk()) {
/* 497 */               this.f26mN.addU64(new Long4(v.f51x, v.f52y, v.f53z, v.f50w));
/* 504 */               return;
                    }
/* 500 */           addU64(v.f51x);
/* 501 */           addU64(v.f52y);
/* 502 */           addU64(v.f53z);
/* 503 */           addU64(v.f50w);
                }

/* 506 */       public void addMatrix(Matrix4f v) {
/* 507 */           if (RenderScript.shouldThunk()) {
/* 508 */               this.f26mN.addMatrix(new Matrix4f(v.getArray()));
/* 514 */               return;
                    }
                    for (int i = 0; i < v.mMat.length; i++) {
/* 512 */               addF32(v.mMat[i]);
                    }
                }

/* 516 */       public void addMatrix(Matrix3f v) {
/* 517 */           if (RenderScript.shouldThunk()) {
/* 518 */               this.f26mN.addMatrix(new Matrix3f(v.getArray()));
/* 524 */               return;
                    }
                    for (int i = 0; i < v.mMat.length; i++) {
/* 522 */               addF32(v.mMat[i]);
                    }
                }

/* 526 */       public void addMatrix(Matrix2f v) {
/* 527 */           if (RenderScript.shouldThunk()) {
/* 528 */               this.f26mN.addMatrix(new Matrix2f(v.getArray()));
/* 534 */               return;
                    }
                    for (int i = 0; i < v.mMat.length; i++) {
/* 532 */               addF32(v.mMat[i]);
                    }
                }

/* 536 */       public void addBoolean(boolean v) {
/* 537 */           if (RenderScript.shouldThunk()) {
/* 538 */               this.f26mN.addBoolean(v);
                    } else {
                        addI8((byte) (v ? 1 : 0));
                    }
                }

/* 544 */       public final byte[] getData() {
                    return RenderScript.shouldThunk() ? this.f26mN.getData() : this.mData;
                }
            }
