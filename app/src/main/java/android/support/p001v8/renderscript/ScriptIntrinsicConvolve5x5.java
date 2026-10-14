            package android.support.p001v8.renderscript;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 28 */    public class ScriptIntrinsicConvolve5x5 extends ScriptIntrinsic {
                private Allocation mInput;
                private final float[] mValues;

/* 29 */        ScriptIntrinsicConvolve5x5(int id, RenderScript rs) {
/* 30 */            super(id, rs);
/* 26 */            this.mValues = new float[25];
                }

/* 50 */        public static ScriptIntrinsicConvolve5x5 create(RenderScript rs, Element e) {
/* 51 */            if (RenderScript.isNative) {
/* 53 */                return ScriptIntrinsicConvolve5x5Thunker.create(rs, e);
                    }
/* 55 */            int id = rs.nScriptIntrinsicCreate(4, e.getID(rs));
/* 56 */            return new ScriptIntrinsicConvolve5x5(id, rs);
                }

/* 66 */        public void setInput(Allocation ain) {
/* 67 */            this.mInput = ain;
/* 68 */            setVar(1, ain);
                }

/* 85 */        public void setCoefficients(float[] v) {
/* 86 */            FieldPacker fp = new FieldPacker(100);
                    for (int ct = 0; ct < this.mValues.length; ct++) {
/* 88 */                this.mValues[ct] = v[ct];
/* 89 */                fp.addF32(this.mValues[ct]);
                    }
/* 91 */            setVar(0, fp);
                }

/* 101 */       public void forEach(Allocation aout) {
/* 102 */           forEach(0, null, aout, null);
                }

/* 110 */       public Script.KernelID getKernelID() {
/* 111 */           return createKernelID(0, 2, null, null);
                }

/* 119 */       public Script.FieldID getFieldID_Input() {
/* 120 */           return createFieldID(1, null);
                }
            }
