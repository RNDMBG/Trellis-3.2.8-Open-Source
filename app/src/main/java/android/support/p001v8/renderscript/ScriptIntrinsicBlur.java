            package android.support.p001v8.renderscript;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 32 */    public class ScriptIntrinsicBlur extends ScriptIntrinsic {
                private Allocation mInput;
                private final float[] mValues;

/* 33 */        protected ScriptIntrinsicBlur(int id, RenderScript rs) {
/* 34 */            super(id, rs);
/* 30 */            this.mValues = new float[9];
                }

/* 48 */        public static ScriptIntrinsicBlur create(RenderScript rs, Element e) {
/* 49 */            if (RenderScript.isNative) {
/* 51 */                return ScriptIntrinsicBlurThunker.create(rs, e);
                    }
/* 53 */            if (!e.isCompatible(Element.U8_4(rs)) && !e.isCompatible(Element.m3U8(rs))) {
/* 54 */                throw new RSIllegalArgumentException("Unsuported element type.");
                    }
/* 56 */            int id = rs.nScriptIntrinsicCreate(5, e.getID(rs));
/* 57 */            ScriptIntrinsicBlur sib = new ScriptIntrinsicBlur(id, rs);
/* 58 */            sib.setRadius(5.0f);
/* 59 */            return sib;
                }

/* 68 */        public void setInput(Allocation ain) {
/* 69 */            this.mInput = ain;
/* 70 */            setVar(1, ain);
                }

/* 80 */        public void setRadius(float radius) {
                    if (radius <= 0.0f || radius > 25.0f) {
/* 82 */                throw new RSIllegalArgumentException("Radius out of range (0 < r <= 25).");
                    }
/* 84 */            setVar(0, radius);
                }

/* 94 */        public void forEach(Allocation aout) {
/* 95 */            forEach(0, null, aout, null);
                }

/* 103 */       public Script.KernelID getKernelID() {
/* 104 */           return createKernelID(0, 2, null, null);
                }

/* 112 */       public Script.FieldID getFieldID_Input() {
/* 113 */           return createFieldID(1, null);
                }
            }
