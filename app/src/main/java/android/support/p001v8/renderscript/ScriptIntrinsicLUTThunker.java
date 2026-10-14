            package android.support.p001v8.renderscript;

            import android.renderscript.BaseObj;
            import android.renderscript.Script;
            import android.renderscript.ScriptIntrinsicLUT;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 19 */    class ScriptIntrinsicLUTThunker extends ScriptIntrinsicLUT {

                /* JADX INFO: renamed from: mN */
                ScriptIntrinsicLUT f68mN;

                @Override // android.support.p001v8.renderscript.Script, android.support.p001v8.renderscript.BaseObj
/* 20 */        /* bridge */ /* synthetic */ BaseObj getNObj() {
/* 21 */            return getNObj();
                }

                @Override // android.support.p001v8.renderscript.Script, android.support.p001v8.renderscript.BaseObj
/* 20 */        /* bridge */ /* synthetic */ Script getNObj() {
/* 21 */            return getNObj();
                }

                @Override // android.support.p001v8.renderscript.Script, android.support.p001v8.renderscript.BaseObj
/* 24 */        ScriptIntrinsicLUT getNObj() {
/* 25 */            return this.f68mN;
                }

/* 28 */        private ScriptIntrinsicLUTThunker(int id, RenderScript rs) {
/* 29 */            super(id, rs);
                }

/* 32 */        public static ScriptIntrinsicLUTThunker create(RenderScript rs, Element e) {
/* 33 */            RenderScriptThunker rst = (RenderScriptThunker) rs;
/* 34 */            ElementThunker et = (ElementThunker) e;
/* 36 */            ScriptIntrinsicLUTThunker si = new ScriptIntrinsicLUTThunker(0, rs);
/* 37 */            si.f68mN = ScriptIntrinsicLUT.create(rst.f54mN, et.getNObj());
/* 38 */            return si;
                }

                @Override // android.support.p001v8.renderscript.ScriptIntrinsicLUT
/* 41 */        public void setRed(int index, int value) {
/* 42 */            this.f68mN.setRed(index, value);
                }

                @Override // android.support.p001v8.renderscript.ScriptIntrinsicLUT
/* 45 */        public void setGreen(int index, int value) {
/* 46 */            this.f68mN.setGreen(index, value);
                }

                @Override // android.support.p001v8.renderscript.ScriptIntrinsicLUT
/* 49 */        public void setBlue(int index, int value) {
/* 50 */            this.f68mN.setBlue(index, value);
                }

                @Override // android.support.p001v8.renderscript.ScriptIntrinsicLUT
/* 53 */        public void setAlpha(int index, int value) {
/* 54 */            this.f68mN.setAlpha(index, value);
                }

                @Override // android.support.p001v8.renderscript.ScriptIntrinsicLUT
/* 57 */        public void forEach(Allocation ain, Allocation aout) {
/* 58 */            AllocationThunker aint = (AllocationThunker) ain;
/* 59 */            AllocationThunker aoutt = (AllocationThunker) aout;
/* 60 */            this.f68mN.forEach(aint.getNObj(), aoutt.getNObj());
                }

                @Override // android.support.p001v8.renderscript.ScriptIntrinsicLUT
/* 63 */        public Script.KernelID getKernelID() {
/* 64 */            Script.KernelID k = createKernelID(0, 3, null, null);
/* 65 */            k.f58mN = this.f68mN.getKernelID();
/* 66 */            return k;
                }
            }
