            package android.support.p001v8.renderscript;

            import android.renderscript.BaseObj;
            import android.renderscript.Matrix3f;
            import android.renderscript.Matrix4f;
            import android.renderscript.Script;
            import android.renderscript.ScriptIntrinsicColorMatrix;
            
            
    class ScriptIntrinsicColorMatrixThunker extends ScriptIntrinsicColorMatrix {

                
                ScriptIntrinsicColorMatrix f65mN;

                @Override 
          BaseObj getNObj() {
            return getNObj();
                }

                @Override 
          Script getNObj() {
            return getNObj();
                }

                @Override 
        ScriptIntrinsicColorMatrix getNObj() {
            return this.f65mN;
                }

        private ScriptIntrinsicColorMatrixThunker(int id, RenderScript rs) {
            super(id, rs);
                }

        public static ScriptIntrinsicColorMatrixThunker create(RenderScript rs, Element e) {
            RenderScriptThunker rst = (RenderScriptThunker) rs;
            ElementThunker et = (ElementThunker) e;
            ScriptIntrinsicColorMatrixThunker cm = new ScriptIntrinsicColorMatrixThunker(0, rs);
            cm.f65mN = ScriptIntrinsicColorMatrix.create(rst.f54mN, et.getNObj());
            return cm;
                }

                @Override 
        public void setColorMatrix(Matrix4f m) {
            this.f65mN.setColorMatrix(new Matrix4f(m.getArray()));
                }

                @Override 
        public void setColorMatrix(Matrix3f m) {
            this.f65mN.setColorMatrix(new Matrix3f(m.getArray()));
                }

                @Override 
        public void setGreyscale() {
            this.f65mN.setGreyscale();
                }

                @Override 
        public void setYUVtoRGB() {
            this.f65mN.setYUVtoRGB();
                }

                @Override 
        public void setRGBtoYUV() {
            this.f65mN.setRGBtoYUV();
                }

                @Override 
        public void forEach(Allocation ain, Allocation aout) {
            AllocationThunker aint = (AllocationThunker) ain;
            AllocationThunker aoutt = (AllocationThunker) aout;
            this.f65mN.forEach(aint.getNObj(), aoutt.getNObj());
                }

                @Override 
        public Script.KernelID getKernelID() {
            Script.KernelID k = createKernelID(0, 3, null, null);
            k.f58mN = this.f65mN.getKernelID();
            return k;
                }
            }
