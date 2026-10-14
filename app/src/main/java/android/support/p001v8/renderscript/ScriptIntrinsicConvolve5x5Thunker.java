            package android.support.p001v8.renderscript;

            import android.renderscript.BaseObj;
            import android.renderscript.Script;
            import android.renderscript.ScriptIntrinsicConvolve5x5;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 19 */    class ScriptIntrinsicConvolve5x5Thunker extends ScriptIntrinsicConvolve5x5 {

                /* JADX INFO: renamed from: mN */
                ScriptIntrinsicConvolve5x5 f67mN;

                @Override // android.support.p001v8.renderscript.Script, android.support.p001v8.renderscript.BaseObj
/* 20 */        /* bridge */ /* synthetic */ BaseObj getNObj() {
/* 21 */            return getNObj();
                }

                @Override // android.support.p001v8.renderscript.Script, android.support.p001v8.renderscript.BaseObj
/* 20 */        /* bridge */ /* synthetic */ Script getNObj() {
/* 21 */            return getNObj();
                }

                @Override // android.support.p001v8.renderscript.Script, android.support.p001v8.renderscript.BaseObj
/* 24 */        ScriptIntrinsicConvolve5x5 getNObj() {
/* 25 */            return this.f67mN;
                }

/* 29 */        ScriptIntrinsicConvolve5x5Thunker(int id, RenderScript rs) {
/* 30 */            super(id, rs);
                }

/* 33 */        public static ScriptIntrinsicConvolve5x5Thunker create(RenderScript rs, Element e) {
/* 34 */            RenderScriptThunker rst = (RenderScriptThunker) rs;
/* 35 */            ElementThunker et = (ElementThunker) e;
/* 37 */            ScriptIntrinsicConvolve5x5Thunker si = new ScriptIntrinsicConvolve5x5Thunker(0, rs);
/* 38 */            si.f67mN = ScriptIntrinsicConvolve5x5.create(rst.f54mN, et.getNObj());
/* 39 */            return si;
                }

                @Override // android.support.p001v8.renderscript.ScriptIntrinsicConvolve5x5
/* 42 */        public void setInput(Allocation ain) {
/* 43 */            AllocationThunker aint = (AllocationThunker) ain;
/* 44 */            this.f67mN.setInput(aint.getNObj());
                }

                @Override // android.support.p001v8.renderscript.ScriptIntrinsicConvolve5x5
/* 47 */        public void setCoefficients(float[] v) {
/* 48 */            this.f67mN.setCoefficients(v);
                }

                @Override // android.support.p001v8.renderscript.ScriptIntrinsicConvolve5x5
/* 51 */        public void forEach(Allocation aout) {
/* 52 */            AllocationThunker aoutt = (AllocationThunker) aout;
/* 53 */            this.f67mN.forEach(aoutt.getNObj());
                }

                @Override // android.support.p001v8.renderscript.ScriptIntrinsicConvolve5x5
/* 57 */        public Script.KernelID getKernelID() {
/* 58 */            Script.KernelID k = createKernelID(0, 2, null, null);
/* 59 */            k.f58mN = this.f67mN.getKernelID();
/* 60 */            return k;
                }

                @Override // android.support.p001v8.renderscript.ScriptIntrinsicConvolve5x5
/* 63 */        public Script.FieldID getFieldID_Input() {
/* 64 */            Script.FieldID f = createFieldID(1, null);
/* 65 */            f.f57mN = this.f67mN.getFieldID_Input();
/* 66 */            return f;
                }
            }
