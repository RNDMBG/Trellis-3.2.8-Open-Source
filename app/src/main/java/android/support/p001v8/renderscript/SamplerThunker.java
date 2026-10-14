            package android.support.p001v8.renderscript;

            import android.renderscript.BaseObj;
            import android.renderscript.Sampler;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 35 */    class SamplerThunker extends Sampler {

                /* JADX INFO: renamed from: mN */
                Sampler f55mN;

/* 36 */        protected SamplerThunker(int id, RenderScript rs) {
/* 37 */            super(id, rs);
                }

                @Override // android.support.p001v8.renderscript.BaseObj
/* 40 */        BaseObj getNObj() {
/* 41 */            return this.f55mN;
                }

/* 44 */        static Sampler.Value convertValue(Sampler.Value v) {
                    switch (v) {
                        case NEAREST:
/* 47 */                    return Sampler.Value.NEAREST;
                        case LINEAR:
/* 49 */                    return Sampler.Value.LINEAR;
                        case LINEAR_MIP_LINEAR:
/* 51 */                    return Sampler.Value.LINEAR_MIP_LINEAR;
                        case LINEAR_MIP_NEAREST:
/* 53 */                    return Sampler.Value.LINEAR_MIP_NEAREST;
                        case WRAP:
/* 55 */                    return Sampler.Value.WRAP;
                        case CLAMP:
/* 57 */                    return Sampler.Value.CLAMP;
                        case MIRRORED_REPEAT:
/* 59 */                    return Sampler.Value.MIRRORED_REPEAT;
                        default:
/* 61 */                    return null;
                    }
                }

/* 76 */        public static class Builder {
                    float mAniso;
                    RenderScriptThunker mRS;
                    Sampler.Value mMin = Sampler.Value.NEAREST;
                    Sampler.Value mMag = Sampler.Value.NEAREST;
                    Sampler.Value mWrapS = Sampler.Value.WRAP;
                    Sampler.Value mWrapT = Sampler.Value.WRAP;
                    Sampler.Value mWrapR = Sampler.Value.WRAP;

/* 77 */            public Builder(RenderScriptThunker rs) {
/* 79 */                this.mRS = rs;
                    }

/* 87 */            public void setMinification(Sampler.Value v) {
/* 88 */                if (v == Sampler.Value.NEAREST || v == Sampler.Value.LINEAR || v == Sampler.Value.LINEAR_MIP_LINEAR || v == Sampler.Value.LINEAR_MIP_NEAREST) {
/* 92 */                    this.mMin = v;
/* 96 */                    return;
                        }
/* 94 */                throw new IllegalArgumentException("Invalid value");
                    }

/* 98 */            public void setMagnification(Sampler.Value v) {
/* 99 */                if (v == Sampler.Value.NEAREST || v == Sampler.Value.LINEAR) {
/* 100 */                   this.mMag = v;
/* 104 */                   return;
                        }
/* 102 */               throw new IllegalArgumentException("Invalid value");
                    }

/* 106 */           public void setWrapS(Sampler.Value v) {
/* 107 */               if (v == Sampler.Value.WRAP || v == Sampler.Value.CLAMP || v == Sampler.Value.MIRRORED_REPEAT) {
/* 108 */                   this.mWrapS = v;
/* 112 */                   return;
                        }
/* 110 */               throw new IllegalArgumentException("Invalid value");
                    }

/* 114 */           public void setWrapT(Sampler.Value v) {
/* 115 */               if (v == Sampler.Value.WRAP || v == Sampler.Value.CLAMP || v == Sampler.Value.MIRRORED_REPEAT) {
/* 116 */                   this.mWrapT = v;
/* 120 */                   return;
                        }
/* 118 */               throw new IllegalArgumentException("Invalid value");
                    }

/* 122 */           public void setAnisotropy(float v) {
                        if (v >= 0.0f) {
/* 124 */                   this.mAniso = v;
/* 128 */                   return;
                        }
/* 126 */               throw new IllegalArgumentException("Invalid value");
                    }

/* 130 */           public Sampler create() {
/* 131 */               this.mRS.validate();
/* 132 */               Sampler.Builder b = new Sampler.Builder(this.mRS.f54mN);
/* 133 */               b.setMinification(SamplerThunker.convertValue(this.mMin));
/* 134 */               b.setMagnification(SamplerThunker.convertValue(this.mMag));
/* 135 */               b.setWrapS(SamplerThunker.convertValue(this.mWrapS));
/* 136 */               b.setWrapT(SamplerThunker.convertValue(this.mWrapT));
/* 137 */               b.setAnisotropy(this.mAniso);
/* 138 */               Sampler s = b.create();
/* 140 */               SamplerThunker sampler = new SamplerThunker(0, this.mRS);
/* 141 */               sampler.mMin = this.mMin;
/* 142 */               sampler.mMag = this.mMag;
/* 143 */               sampler.mWrapS = this.mWrapS;
/* 144 */               sampler.mWrapT = this.mWrapT;
/* 145 */               sampler.mWrapR = this.mWrapR;
/* 146 */               sampler.mAniso = this.mAniso;
/* 147 */               sampler.f55mN = s;
/* 149 */               return sampler;
                    }
                }
            }
