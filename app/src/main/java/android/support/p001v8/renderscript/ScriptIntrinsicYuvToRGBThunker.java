            package android.support.p001v8.renderscript;

            import android.renderscript.BaseObj;
            import android.renderscript.Script;
            import android.renderscript.ScriptIntrinsicYuvToRGB;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 18 */    public class ScriptIntrinsicYuvToRGBThunker extends ScriptIntrinsicYuvToRGB {

                /* JADX INFO: renamed from: mN */
                ScriptIntrinsicYuvToRGB f69mN;

                @Override // android.support.p001v8.renderscript.Script, android.support.p001v8.renderscript.BaseObj
/* 19 */        /* bridge */ /* synthetic */ BaseObj getNObj() {
/* 20 */            return getNObj();
                }

                @Override // android.support.p001v8.renderscript.Script, android.support.p001v8.renderscript.BaseObj
/* 19 */        /* bridge */ /* synthetic */ Script getNObj() {
/* 20 */            return getNObj();
                }

                @Override // android.support.p001v8.renderscript.Script, android.support.p001v8.renderscript.BaseObj
/* 23 */        ScriptIntrinsicYuvToRGB getNObj() {
/* 24 */            return this.f69mN;
                }

/* 28 */        private ScriptIntrinsicYuvToRGBThunker(int id, RenderScript rs) {
/* 29 */            super(id, rs);
                }

/* 32 */        public static ScriptIntrinsicYuvToRGBThunker create(RenderScript rs, Element e) {
/* 33 */            RenderScriptThunker rst = (RenderScriptThunker) rs;
/* 34 */            ElementThunker et = (ElementThunker) e;
/* 36 */            ScriptIntrinsicYuvToRGBThunker si = new ScriptIntrinsicYuvToRGBThunker(0, rs);
/* 37 */            si.f69mN = ScriptIntrinsicYuvToRGB.create(rst.f54mN, et.getNObj());
/* 38 */            return si;
                }

                @Override // android.support.p001v8.renderscript.ScriptIntrinsicYuvToRGB
/* 42 */        public void setInput(Allocation ain) {
/* 43 */            AllocationThunker aint = (AllocationThunker) ain;
/* 44 */            this.f69mN.setInput(aint.getNObj());
                }

                @Override // android.support.p001v8.renderscript.ScriptIntrinsicYuvToRGB
/* 47 */        public void forEach(Allocation aout) {
/* 48 */            AllocationThunker aoutt = (AllocationThunker) aout;
/* 49 */            this.f69mN.forEach(aoutt.getNObj());
                }

                @Override // android.support.p001v8.renderscript.ScriptIntrinsicYuvToRGB
/* 52 */        public Script.KernelID getKernelID() {
/* 53 */            Script.KernelID k = createKernelID(0, 2, null, null);
/* 54 */            k.f58mN = this.f69mN.getKernelID();
/* 55 */            return k;
                }

                @Override // android.support.p001v8.renderscript.ScriptIntrinsicYuvToRGB
/* 58 */        public Script.FieldID getFieldID_Input() {
/* 59 */            Script.FieldID f = createFieldID(0, null);
/* 60 */            f.f57mN = this.f69mN.getFieldID_Input();
/* 61 */            return f;
                }
            }
