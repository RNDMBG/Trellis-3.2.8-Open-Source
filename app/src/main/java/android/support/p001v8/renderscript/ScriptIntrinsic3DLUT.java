            package android.support.p001v8.renderscript;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 33 */    public class ScriptIntrinsic3DLUT extends ScriptIntrinsic {
                private Element mElement;
                private Allocation mLUT;

/* 34 */        protected ScriptIntrinsic3DLUT(int id, RenderScript rs, Element e) {
/* 35 */            super(id, rs);
/* 36 */            this.mElement = e;
                }

/* 49 */        public static ScriptIntrinsic3DLUT create(RenderScript rs, Element e) {
/* 50 */            if (RenderScript.isNative) {
/* 52 */                return ScriptIntrinsic3DLUTThunker.create(rs, e);
                    }
/* 54 */            int id = rs.nScriptIntrinsicCreate(8, e.getID(rs));
/* 56 */            if (!e.isCompatible(Element.U8_4(rs))) {
/* 57 */                throw new RSIllegalArgumentException("Element must be compatible with uchar4.");
                    }
/* 60 */            return new ScriptIntrinsic3DLUT(id, rs, e);
                }

/* 72 */        public void setLUT(Allocation lut) {
/* 73 */            Type t = lut.getType();
/* 75 */            if (t.getZ() == 0) {
/* 76 */                throw new RSIllegalArgumentException("LUT must be 3d.");
                    }
/* 79 */            if (!t.getElement().isCompatible(this.mElement)) {
/* 80 */                throw new RSIllegalArgumentException("LUT element type must match.");
                    }
/* 83 */            this.mLUT = lut;
/* 84 */            setVar(0, this.mLUT);
                }

/* 95 */        public void forEach(Allocation ain, Allocation aout) {
/* 96 */            forEach(0, ain, aout, null);
                }

/* 104 */       public Script.KernelID getKernelID() {
/* 105 */           return createKernelID(0, 3, null, null);
                }
            }
