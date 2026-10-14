            package android.support.p001v8.renderscript;

            import android.renderscript.BaseObj;
            import android.renderscript.Script;
            import android.renderscript.ScriptIntrinsic3DLUT;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 23 */    class ScriptIntrinsic3DLUTThunker extends ScriptIntrinsic3DLUT {

                /* JADX INFO: renamed from: mN */
                ScriptIntrinsic3DLUT f62mN;

                @Override // android.support.p001v8.renderscript.Script, android.support.p001v8.renderscript.BaseObj
/* 24 */        /* bridge */ /* synthetic */ BaseObj getNObj() {
/* 25 */            return getNObj();
                }

                @Override // android.support.p001v8.renderscript.Script, android.support.p001v8.renderscript.BaseObj
/* 24 */        /* bridge */ /* synthetic */ Script getNObj() {
/* 25 */            return getNObj();
                }

                @Override // android.support.p001v8.renderscript.Script, android.support.p001v8.renderscript.BaseObj
/* 28 */        ScriptIntrinsic3DLUT getNObj() {
/* 29 */            return this.f62mN;
                }

/* 32 */        private ScriptIntrinsic3DLUTThunker(int id, RenderScript rs, Element e) {
/* 33 */            super(id, rs, e);
                }

/* 36 */        public static ScriptIntrinsic3DLUTThunker create(RenderScript rs, Element e) {
/* 37 */            RenderScriptThunker rst = (RenderScriptThunker) rs;
/* 38 */            ElementThunker et = (ElementThunker) e;
/* 40 */            ScriptIntrinsic3DLUTThunker lut = new ScriptIntrinsic3DLUTThunker(0, rs, e);
/* 41 */            lut.f62mN = ScriptIntrinsic3DLUT.create(rst.f54mN, et.getNObj());
/* 42 */            return lut;
                }

                @Override // android.support.p001v8.renderscript.ScriptIntrinsic3DLUT
/* 45 */        public void setLUT(Allocation lut) {
/* 46 */            AllocationThunker lutt = (AllocationThunker) lut;
/* 47 */            this.f62mN.setLUT(lutt.getNObj());
                }

                @Override // android.support.p001v8.renderscript.ScriptIntrinsic3DLUT
/* 58 */        public void forEach(Allocation ain, Allocation aout) {
/* 59 */            AllocationThunker aint = (AllocationThunker) ain;
/* 60 */            AllocationThunker aoutt = (AllocationThunker) aout;
/* 61 */            this.f62mN.forEach(aint.getNObj(), aoutt.getNObj());
                }

                @Override // android.support.p001v8.renderscript.ScriptIntrinsic3DLUT
/* 69 */        public Script.KernelID getKernelID() {
/* 70 */            Script.KernelID k = createKernelID(0, 3, null, null);
/* 71 */            k.f58mN = this.f62mN.getKernelID();
/* 72 */            return k;
                }
            }
