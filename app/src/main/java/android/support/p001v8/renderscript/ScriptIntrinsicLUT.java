            package android.support.p001v8.renderscript;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 32 */    public class ScriptIntrinsicLUT extends ScriptIntrinsic {
                private final byte[] mCache;
                private boolean mDirty;
                private final Matrix4f mMatrix;
                private Allocation mTables;

/* 33 */        protected ScriptIntrinsicLUT(int id, RenderScript rs) {
/* 34 */            super(id, rs);
/* 28 */            this.mMatrix = new Matrix4f();
/* 30 */            this.mCache = new byte[1024];
/* 31 */            this.mDirty = true;
                }

/* 47 */        public static ScriptIntrinsicLUT create(RenderScript rs, Element e) {
/* 48 */            if (RenderScript.isNative) {
/* 50 */                return ScriptIntrinsicLUTThunker.create(rs, e);
                    }
/* 53 */            int id = rs.nScriptIntrinsicCreate(3, e.getID(rs));
/* 55 */            ScriptIntrinsicLUT si = new ScriptIntrinsicLUT(id, rs);
/* 56 */            si.mTables = Allocation.createSized(rs, Element.m3U8(rs), 1024);
                    for (int ct = 0; ct < 256; ct++) {
/* 58 */                si.mCache[ct] = (byte) ct;
/* 59 */                si.mCache[ct + 256] = (byte) ct;
/* 60 */                si.mCache[ct + 512] = (byte) ct;
/* 61 */                si.mCache[ct + 768] = (byte) ct;
                    }
/* 63 */            si.setVar(0, si.mTables);
/* 64 */            return si;
                }

/* 68 */        private void validate(int index, int value) {
/* 69 */            if (index < 0 || index > 255) {
/* 70 */                throw new RSIllegalArgumentException("Index out of range (0-255).");
                    }
/* 72 */            if (value < 0 || value > 255) {
/* 73 */                throw new RSIllegalArgumentException("Value out of range (0-255).");
                    }
                }

/* 83 */        public void setRed(int index, int value) {
/* 84 */            validate(index, value);
/* 85 */            this.mCache[index] = (byte) value;
/* 86 */            this.mDirty = true;
                }

/* 95 */        public void setGreen(int index, int value) {
/* 96 */            validate(index, value);
/* 97 */            this.mCache[index + 256] = (byte) value;
/* 98 */            this.mDirty = true;
                }

/* 107 */       public void setBlue(int index, int value) {
/* 108 */           validate(index, value);
/* 109 */           this.mCache[index + 512] = (byte) value;
/* 110 */           this.mDirty = true;
                }

/* 119 */       public void setAlpha(int index, int value) {
/* 120 */           validate(index, value);
/* 121 */           this.mCache[index + 768] = (byte) value;
/* 122 */           this.mDirty = true;
                }

/* 133 */       public void forEach(Allocation ain, Allocation aout) {
/* 134 */           if (this.mDirty) {
/* 135 */               this.mDirty = false;
/* 136 */               this.mTables.copyFromUnchecked(this.mCache);
                    }
/* 138 */           forEach(0, ain, aout, null);
                }

/* 146 */       public Script.KernelID getKernelID() {
/* 147 */           return createKernelID(0, 3, null, null);
                }
            }
