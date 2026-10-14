            package android.support.p001v8.renderscript;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 50 */    public class Sampler extends BaseObj {
                float mAniso;
                Value mMag;
                Value mMin;
                Value mWrapR;
                Value mWrapS;
                Value mWrapT;

/* 51 */        public enum Value {
                    NEAREST(0),
                    LINEAR(1),
                    LINEAR_MIP_LINEAR(2),
                    LINEAR_MIP_NEAREST(5),
                    WRAP(3),
                    CLAMP(4),
                    MIRRORED_REPEAT(6);

                    int mID;

/* 52 */            Value(int id) {
/* 54 */                this.mID = id;
                    }
                }

/* 65 */        Sampler(int id, RenderScript rs) {
/* 66 */            super(id, rs);
                }

/* 72 */        public Value getMinification() {
/* 73 */            return this.mMin;
                }

/* 79 */        public Value getMagnification() {
/* 80 */            return this.mMag;
                }

/* 86 */        public Value getWrapS() {
/* 87 */            return this.mWrapS;
                }

/* 93 */        public Value getWrapT() {
/* 94 */            return this.mWrapT;
                }

/* 100 */       public float getAnisotropy() {
/* 101 */           return this.mAniso;
                }

/* 112 */       public static Sampler CLAMP_NEAREST(RenderScript rs) {
/* 113 */           if (rs.mSampler_CLAMP_NEAREST == null) {
/* 114 */               Builder b = new Builder(rs);
/* 115 */               b.setMinification(Value.NEAREST);
/* 116 */               b.setMagnification(Value.NEAREST);
/* 117 */               b.setWrapS(Value.CLAMP);
/* 118 */               b.setWrapT(Value.CLAMP);
/* 119 */               rs.mSampler_CLAMP_NEAREST = b.create();
                    }
/* 121 */           return rs.mSampler_CLAMP_NEAREST;
                }

/* 132 */       public static Sampler CLAMP_LINEAR(RenderScript rs) {
/* 133 */           if (rs.mSampler_CLAMP_LINEAR == null) {
/* 134 */               Builder b = new Builder(rs);
/* 135 */               b.setMinification(Value.LINEAR);
/* 136 */               b.setMagnification(Value.LINEAR);
/* 137 */               b.setWrapS(Value.CLAMP);
/* 138 */               b.setWrapT(Value.CLAMP);
/* 139 */               rs.mSampler_CLAMP_LINEAR = b.create();
                    }
/* 141 */           return rs.mSampler_CLAMP_LINEAR;
                }

/* 152 */       public static Sampler CLAMP_LINEAR_MIP_LINEAR(RenderScript rs) {
/* 153 */           if (rs.mSampler_CLAMP_LINEAR_MIP_LINEAR == null) {
/* 154 */               Builder b = new Builder(rs);
/* 155 */               b.setMinification(Value.LINEAR_MIP_LINEAR);
/* 156 */               b.setMagnification(Value.LINEAR);
/* 157 */               b.setWrapS(Value.CLAMP);
/* 158 */               b.setWrapT(Value.CLAMP);
/* 159 */               rs.mSampler_CLAMP_LINEAR_MIP_LINEAR = b.create();
                    }
/* 161 */           return rs.mSampler_CLAMP_LINEAR_MIP_LINEAR;
                }

/* 172 */       public static Sampler WRAP_NEAREST(RenderScript rs) {
/* 173 */           if (rs.mSampler_WRAP_NEAREST == null) {
/* 174 */               Builder b = new Builder(rs);
/* 175 */               b.setMinification(Value.NEAREST);
/* 176 */               b.setMagnification(Value.NEAREST);
/* 177 */               b.setWrapS(Value.WRAP);
/* 178 */               b.setWrapT(Value.WRAP);
/* 179 */               rs.mSampler_WRAP_NEAREST = b.create();
                    }
/* 181 */           return rs.mSampler_WRAP_NEAREST;
                }

/* 192 */       public static Sampler WRAP_LINEAR(RenderScript rs) {
/* 193 */           if (rs.mSampler_WRAP_LINEAR == null) {
/* 194 */               Builder b = new Builder(rs);
/* 195 */               b.setMinification(Value.LINEAR);
/* 196 */               b.setMagnification(Value.LINEAR);
/* 197 */               b.setWrapS(Value.WRAP);
/* 198 */               b.setWrapT(Value.WRAP);
/* 199 */               rs.mSampler_WRAP_LINEAR = b.create();
                    }
/* 201 */           return rs.mSampler_WRAP_LINEAR;
                }

/* 212 */       public static Sampler WRAP_LINEAR_MIP_LINEAR(RenderScript rs) {
/* 213 */           if (rs.mSampler_WRAP_LINEAR_MIP_LINEAR == null) {
/* 214 */               Builder b = new Builder(rs);
/* 215 */               b.setMinification(Value.LINEAR_MIP_LINEAR);
/* 216 */               b.setMagnification(Value.LINEAR);
/* 217 */               b.setWrapS(Value.WRAP);
/* 218 */               b.setWrapT(Value.WRAP);
/* 219 */               rs.mSampler_WRAP_LINEAR_MIP_LINEAR = b.create();
                    }
/* 221 */           return rs.mSampler_WRAP_LINEAR_MIP_LINEAR;
                }

/* 232 */       public static Sampler MIRRORED_REPEAT_NEAREST(RenderScript rs) {
/* 233 */           if (rs.mSampler_MIRRORED_REPEAT_NEAREST == null) {
/* 234 */               Builder b = new Builder(rs);
/* 235 */               b.setMinification(Value.NEAREST);
/* 236 */               b.setMagnification(Value.NEAREST);
/* 237 */               b.setWrapS(Value.MIRRORED_REPEAT);
/* 238 */               b.setWrapT(Value.MIRRORED_REPEAT);
/* 239 */               rs.mSampler_MIRRORED_REPEAT_NEAREST = b.create();
                    }
/* 241 */           return rs.mSampler_MIRRORED_REPEAT_NEAREST;
                }

/* 252 */       public static Sampler MIRRORED_REPEAT_LINEAR(RenderScript rs) {
/* 253 */           if (rs.mSampler_MIRRORED_REPEAT_LINEAR == null) {
/* 254 */               Builder b = new Builder(rs);
/* 255 */               b.setMinification(Value.LINEAR);
/* 256 */               b.setMagnification(Value.LINEAR);
/* 257 */               b.setWrapS(Value.MIRRORED_REPEAT);
/* 258 */               b.setWrapT(Value.MIRRORED_REPEAT);
/* 259 */               rs.mSampler_MIRRORED_REPEAT_LINEAR = b.create();
                    }
/* 261 */           return rs.mSampler_MIRRORED_REPEAT_LINEAR;
                }

/* 275 */       public static class Builder {
                    RenderScript mRS;
                    Value mMin = Value.NEAREST;
                    Value mMag = Value.NEAREST;
                    Value mWrapS = Value.WRAP;
                    Value mWrapT = Value.WRAP;
                    Value mWrapR = Value.WRAP;
                    float mAniso = 1.0f;

/* 276 */           public Builder(RenderScript rs) {
/* 278 */               this.mRS = rs;
                    }

/* 287 */           public void setMinification(Value v) {
/* 288 */               if (v == Value.NEAREST || v == Value.LINEAR || v == Value.LINEAR_MIP_LINEAR || v == Value.LINEAR_MIP_NEAREST) {
/* 292 */                   this.mMin = v;
/* 296 */                   return;
                        }
/* 294 */               throw new IllegalArgumentException("Invalid value");
                    }

/* 298 */           public void setMagnification(Value v) {
/* 299 */               if (v == Value.NEAREST || v == Value.LINEAR) {
/* 300 */                   this.mMag = v;
/* 304 */                   return;
                        }
/* 302 */               throw new IllegalArgumentException("Invalid value");
                    }

/* 306 */           public void setWrapS(Value v) {
/* 307 */               if (v == Value.WRAP || v == Value.CLAMP || v == Value.MIRRORED_REPEAT) {
/* 308 */                   this.mWrapS = v;
/* 312 */                   return;
                        }
/* 310 */               throw new IllegalArgumentException("Invalid value");
                    }

/* 314 */           public void setWrapT(Value v) {
/* 315 */               if (v == Value.WRAP || v == Value.CLAMP || v == Value.MIRRORED_REPEAT) {
/* 316 */                   this.mWrapT = v;
/* 320 */                   return;
                        }
/* 318 */               throw new IllegalArgumentException("Invalid value");
                    }

/* 322 */           public void setAnisotropy(float v) {
                        if (v >= 0.0f) {
/* 324 */                   this.mAniso = v;
/* 328 */                   return;
                        }
/* 326 */               throw new IllegalArgumentException("Invalid value");
                    }

/* 330 */           public Sampler create() {
/* 331 */               RenderScript renderScript = this.mRS;
                        if (RenderScript.isNative) {
/* 332 */                   RenderScriptThunker rst = (RenderScriptThunker) this.mRS;
/* 333 */                   SamplerThunker.Builder b = new SamplerThunker.Builder(rst);
/* 334 */                   b.setMinification(this.mMin);
/* 335 */                   b.setMagnification(this.mMag);
/* 336 */                   b.setWrapS(this.mWrapS);
/* 337 */                   b.setWrapT(this.mWrapT);
/* 338 */                   b.setAnisotropy(this.mAniso);
/* 339 */                   return b.create();
                        }
/* 341 */               this.mRS.validate();
/* 342 */               int id = this.mRS.nSamplerCreate(this.mMag.mID, this.mMin.mID, this.mWrapS.mID, this.mWrapT.mID, this.mWrapR.mID, this.mAniso);
/* 344 */               Sampler sampler = new Sampler(id, this.mRS);
/* 345 */               sampler.mMin = this.mMin;
/* 346 */               sampler.mMag = this.mMag;
/* 347 */               sampler.mWrapS = this.mWrapS;
/* 348 */               sampler.mWrapT = this.mWrapT;
/* 349 */               sampler.mWrapR = this.mWrapR;
/* 350 */               sampler.mAniso = this.mAniso;
/* 351 */               return sampler;
                    }
                }
            }
