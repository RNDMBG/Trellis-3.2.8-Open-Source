            package android.support.p001v8.renderscript;

            import android.renderscript.BaseObj;
            import android.renderscript.Matrix3f;
            import android.renderscript.Matrix4f;
            import android.renderscript.Script;
            import android.renderscript.ScriptIntrinsicColorMatrix;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 19 */    class ScriptIntrinsicColorMatrixThunker extends ScriptIntrinsicColorMatrix {

                /* JADX INFO: renamed from: mN */
                ScriptIntrinsicColorMatrix f65mN;

                @Override // android.support.p001v8.renderscript.Script, android.support.p001v8.renderscript.BaseObj
/* 20 */        /* bridge */ /* synthetic */ BaseObj getNObj() {
/* 21 */            return getNObj();
                }

                @Override // android.support.p001v8.renderscript.Script, android.support.p001v8.renderscript.BaseObj
/* 20 */        /* bridge */ /* synthetic */ Script getNObj() {
/* 21 */            return getNObj();
                }

                @Override // android.support.p001v8.renderscript.Script, android.support.p001v8.renderscript.BaseObj
/* 24 */        ScriptIntrinsicColorMatrix getNObj() {
/* 25 */            return this.f65mN;
                }

/* 28 */        private ScriptIntrinsicColorMatrixThunker(int id, RenderScript rs) {
/* 29 */            super(id, rs);
                }

/* 32 */        public static ScriptIntrinsicColorMatrixThunker create(RenderScript rs, Element e) {
/* 33 */            RenderScriptThunker rst = (RenderScriptThunker) rs;
/* 34 */            ElementThunker et = (ElementThunker) e;
/* 36 */            ScriptIntrinsicColorMatrixThunker cm = new ScriptIntrinsicColorMatrixThunker(0, rs);
/* 37 */            cm.f65mN = ScriptIntrinsicColorMatrix.create(rst.f54mN, et.getNObj());
/* 38 */            return cm;
                }

                @Override // android.support.p001v8.renderscript.ScriptIntrinsicColorMatrix
/* 42 */        public void setColorMatrix(Matrix4f m) {
/* 43 */            this.f65mN.setColorMatrix(new Matrix4f(m.getArray()));
                }

                @Override // android.support.p001v8.renderscript.ScriptIntrinsicColorMatrix
/* 46 */        public void setColorMatrix(Matrix3f m) {
/* 47 */            this.f65mN.setColorMatrix(new Matrix3f(m.getArray()));
                }

                @Override // android.support.p001v8.renderscript.ScriptIntrinsicColorMatrix
/* 50 */        public void setGreyscale() {
/* 51 */            this.f65mN.setGreyscale();
                }

                @Override // android.support.p001v8.renderscript.ScriptIntrinsicColorMatrix
/* 54 */        public void setYUVtoRGB() {
/* 55 */            this.f65mN.setYUVtoRGB();
                }

                @Override // android.support.p001v8.renderscript.ScriptIntrinsicColorMatrix
/* 58 */        public void setRGBtoYUV() {
/* 59 */            this.f65mN.setRGBtoYUV();
                }

                @Override // android.support.p001v8.renderscript.ScriptIntrinsicColorMatrix
/* 63 */        public void forEach(Allocation ain, Allocation aout) {
/* 64 */            AllocationThunker aint = (AllocationThunker) ain;
/* 65 */            AllocationThunker aoutt = (AllocationThunker) aout;
/* 66 */            this.f65mN.forEach(aint.getNObj(), aoutt.getNObj());
                }

                @Override // android.support.p001v8.renderscript.ScriptIntrinsicColorMatrix
/* 69 */        public Script.KernelID getKernelID() {
/* 70 */            Script.KernelID k = createKernelID(0, 3, null, null);
/* 71 */            k.f58mN = this.f65mN.getKernelID();
/* 72 */            return k;
                }
            }
