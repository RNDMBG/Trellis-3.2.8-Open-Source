            package android.support.p001v8.renderscript;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 28 */    public class ScriptIntrinsicConvolve3x3 extends ScriptIntrinsic {
                private Allocation mInput;
                private final float[] mValues;

/* 29 */        ScriptIntrinsicConvolve3x3(int id, RenderScript rs) {
/* 30 */            super(id, rs);
/* 26 */            this.mValues = new float[9];
                }

/* 49 */        public static ScriptIntrinsicConvolve3x3 create(RenderScript rs, Element e) {
/* 50 */            if (RenderScript.isNative) {
/* 52 */                return ScriptIntrinsicConvolve3x3Thunker.create(rs, e);
                    }
/* 55 */            float[] f = {0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f};
/* 56 */            if (!e.isCompatible(Element.U8_4(rs))) {
/* 57 */                throw new RSIllegalArgumentException("Unsuported element type.");
                    }
/* 59 */            int id = rs.nScriptIntrinsicCreate(1, e.getID(rs));
/* 60 */            ScriptIntrinsicConvolve3x3 si = new ScriptIntrinsicConvolve3x3(id, rs);
/* 61 */            si.setCoefficients(f);
/* 62 */            return si;
                }

/* 72 */        public void setInput(Allocation ain) {
/* 73 */            this.mInput = ain;
/* 74 */            setVar(1, ain);
                }

/* 89 */        public void setCoefficients(float[] v) {
/* 90 */            FieldPacker fp = new FieldPacker(36);
                    for (int ct = 0; ct < this.mValues.length; ct++) {
/* 92 */                this.mValues[ct] = v[ct];
/* 93 */                fp.addF32(this.mValues[ct]);
                    }
/* 95 */            setVar(0, fp);
                }

/* 105 */       public void forEach(Allocation aout) {
/* 106 */           forEach(0, null, aout, null);
                }

/* 114 */       public Script.KernelID getKernelID() {
/* 115 */           return createKernelID(0, 2, null, null);
                }

/* 123 */       public Script.FieldID getFieldID_Input() {
/* 124 */           return createFieldID(1, null);
                }
            }
