            package android.support.p001v8.renderscript;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 29 */    public class ScriptIntrinsicYuvToRGB extends ScriptIntrinsic {
                private Allocation mInput;

/* 30 */        ScriptIntrinsicYuvToRGB(int id, RenderScript rs) {
/* 31 */            super(id, rs);
                }

/* 44 */        public static ScriptIntrinsicYuvToRGB create(RenderScript rs, Element e) {
/* 45 */            if (RenderScript.isNative) {
/* 47 */                return ScriptIntrinsicYuvToRGBThunker.create(rs, e);
                    }
/* 51 */            int id = rs.nScriptIntrinsicCreate(6, e.getID(rs));
/* 52 */            return new ScriptIntrinsicYuvToRGB(id, rs);
                }

/* 62 */        public void setInput(Allocation ain) {
/* 63 */            this.mInput = ain;
/* 64 */            setVar(0, ain);
                }

/* 73 */        public void forEach(Allocation aout) {
/* 74 */            forEach(0, null, aout, null);
                }

/* 82 */        public Script.KernelID getKernelID() {
/* 83 */            return createKernelID(0, 2, null, null);
                }

/* 91 */        public Script.FieldID getFieldID_Input() {
/* 92 */            return createFieldID(0, null);
                }
            }
