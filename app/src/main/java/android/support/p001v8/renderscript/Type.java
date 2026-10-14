            package android.support.p001v8.renderscript;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 70 */    public class Type extends BaseObj {
                boolean mDimFaces;
                boolean mDimMipmaps;
                int mDimX;
                int mDimY;
                int mDimYuv;
                int mDimZ;
                Element mElement;
                int mElementCount;

/* 71 */        public enum CubemapFace {
                    POSITIVE_X(0),
                    NEGATIVE_X(1),
                    POSITIVE_Y(2),
                    NEGATIVE_Y(3),
                    POSITIVE_Z(4),
                    NEGATIVE_Z(5);

                    int mID;

/* 72 */            CubemapFace(int id) {
/* 74 */                this.mID = id;
                    }
                }

/* 83 */        public Element getElement() {
/* 84 */            return this.mElement;
                }

/* 92 */        public int getX() {
/* 93 */            return this.mDimX;
                }

/* 101 */       public int getY() {
/* 102 */           return this.mDimY;
                }

/* 110 */       public int getZ() {
/* 111 */           return this.mDimZ;
                }

/* 121 */       public int getYuv() {
/* 122 */           return this.mDimYuv;
                }

/* 130 */       public boolean hasMipmaps() {
/* 131 */           return this.mDimMipmaps;
                }

/* 139 */       public boolean hasFaces() {
/* 140 */           return this.mDimFaces;
                }

/* 148 */       public int getCount() {
/* 149 */           return this.mElementCount;
                }

/* 152 */       void calcElementCount() {
/* 153 */           boolean hasLod = hasMipmaps();
/* 154 */           int x = getX();
/* 155 */           int y = getY();
/* 156 */           int z = getZ();
/* 157 */           int faces = 1;
/* 158 */           if (hasFaces()) {
/* 159 */               faces = 6;
                    }
/* 161 */           if (x == 0) {
/* 162 */               x = 1;
                    }
/* 164 */           if (y == 0) {
/* 165 */               y = 1;
                    }
/* 167 */           if (z == 0) {
/* 168 */               z = 1;
                    }
/* 171 */           int count = x * y * z * faces;
/* 173 */           while (hasLod && (x > 1 || y > 1 || z > 1)) {
/* 174 */               if (x > 1) {
/* 175 */                   x >>= 1;
                        }
/* 177 */               if (y > 1) {
/* 178 */                   y >>= 1;
                        }
/* 180 */               if (z > 1) {
/* 181 */                   z >>= 1;
                        }
/* 184 */               count += x * y * z * faces;
                    }
/* 186 */           this.mElementCount = count;
                }

/* 190 */       Type(int id, RenderScript rs) {
/* 191 */           super(id, rs);
                }

/* 213 */       public static class Builder {
                    boolean mDimFaces;
                    boolean mDimMipmaps;
                    int mDimX = 1;
                    int mDimY;
                    int mDimZ;
                    Element mElement;
                    RenderScript mRS;
                    int mYuv;

/* 214 */           public Builder(RenderScript rs, Element e) {
/* 216 */               e.checkValid();
/* 217 */               this.mRS = rs;
/* 218 */               this.mElement = e;
                    }

/* 227 */           public Builder setX(int value) {
/* 228 */               if (value < 1) {
/* 229 */                   throw new RSIllegalArgumentException("Values of less than 1 for Dimension X are not valid.");
                        }
/* 231 */               this.mDimX = value;
/* 232 */               return this;
                    }

/* 235 */           public Builder setY(int value) {
/* 236 */               if (value < 1) {
/* 237 */                   throw new RSIllegalArgumentException("Values of less than 1 for Dimension Y are not valid.");
                        }
/* 239 */               this.mDimY = value;
/* 240 */               return this;
                    }

/* 243 */           public Builder setZ(int value) {
/* 244 */               if (value < 1) {
/* 245 */                   throw new RSIllegalArgumentException("Values of less than 1 for Dimension Z are not valid.");
                        }
/* 247 */               this.mDimZ = value;
/* 248 */               return this;
                    }

/* 251 */           public Builder setMipmaps(boolean value) {
/* 252 */               this.mDimMipmaps = value;
/* 253 */               return this;
                    }

/* 256 */           public Builder setFaces(boolean value) {
/* 257 */               this.mDimFaces = value;
/* 258 */               return this;
                    }

/* 268 */           public Builder setYuvFormat(int yuvFormat) {
                        switch (yuvFormat) {
                            case 17:
                            case 842094169:
/* 278 */                       this.mYuv = yuvFormat;
/* 279 */                       return this;
                            default:
/* 275 */                       throw new RSIllegalArgumentException("Only NV21 and YV12 are supported..");
                        }
                    }

/* 288 */           public Type create() {
                        Type t;
/* 289 */               if (this.mDimZ > 0) {
/* 290 */                   if (this.mDimX < 1 || this.mDimY < 1) {
/* 291 */                       throw new RSInvalidStateException("Both X and Y dimension required when Z is present.");
                            }
/* 293 */                   if (this.mDimFaces) {
/* 294 */                       throw new RSInvalidStateException("Cube maps not supported with 3D types.");
                            }
                        }
/* 297 */               if (this.mDimY > 0 && this.mDimX < 1) {
/* 299 */                   throw new RSInvalidStateException("X dimension required when Y is present.");
                        }
/* 302 */               if (this.mDimFaces && this.mDimY < 1) {
/* 304 */                   throw new RSInvalidStateException("Cube maps require 2D Types.");
                        }
/* 308 */               if (this.mYuv != 0 && (this.mDimZ != 0 || this.mDimFaces || this.mDimMipmaps)) {
/* 310 */                   throw new RSInvalidStateException("YUV only supports basic 2D.");
                        }
/* 315 */               RenderScript renderScript = this.mRS;
                        if (RenderScript.isNative) {
/* 316 */                   RenderScriptThunker rst = (RenderScriptThunker) this.mRS;
/* 317 */                   t = TypeThunker.create(rst, this.mElement, this.mDimX, this.mDimY, this.mDimZ, this.mDimMipmaps, this.mDimFaces, this.mYuv);
                        } else {
/* 320 */                   int id = this.mRS.nTypeCreate(this.mElement.getID(this.mRS), this.mDimX, this.mDimY, this.mDimZ, this.mDimMipmaps, this.mDimFaces, this.mYuv);
/* 322 */                   t = new Type(id, this.mRS);
                        }
/* 324 */               t.mElement = this.mElement;
/* 325 */               t.mDimX = this.mDimX;
/* 326 */               t.mDimY = this.mDimY;
/* 327 */               t.mDimZ = this.mDimZ;
/* 328 */               t.mDimMipmaps = this.mDimMipmaps;
/* 329 */               t.mDimFaces = this.mDimFaces;
/* 330 */               t.mDimYuv = this.mYuv;
/* 332 */               t.calcElementCount();
/* 333 */               return t;
                    }
                }
            }
