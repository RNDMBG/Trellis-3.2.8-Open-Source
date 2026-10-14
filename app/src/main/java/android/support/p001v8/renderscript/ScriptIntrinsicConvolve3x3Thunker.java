            package android.support.p001v8.renderscript;

            import android.renderscript.BaseObj;
            import android.renderscript.Script;
            import android.renderscript.ScriptIntrinsicConvolve3x3;
            
            
    class ScriptIntrinsicConvolve3x3Thunker extends ScriptIntrinsicConvolve3x3 {

                
                ScriptIntrinsicConvolve3x3 f66mN;

                @Override 
          BaseObj getNObj() {
            return getNObj();
                }

                @Override 
          Script getNObj() {
            return getNObj();
                }

                @Override 
        ScriptIntrinsicConvolve3x3 getNObj() {
            return this.f66mN;
                }

        ScriptIntrinsicConvolve3x3Thunker(int id, RenderScript rs) {
            super(id, rs);
                }

        public static ScriptIntrinsicConvolve3x3Thunker create(RenderScript rs, Element e) {
            RenderScriptThunker rst = (RenderScriptThunker) rs;
            ElementThunker et = (ElementThunker) e;
            ScriptIntrinsicConvolve3x3Thunker si = new ScriptIntrinsicConvolve3x3Thunker(0, rs);
            si.f66mN = ScriptIntrinsicConvolve3x3.create(rst.f54mN, et.getNObj());
            return si;
                }

                @Override 
        public void setInput(Allocation ain) {
            AllocationThunker aint = (AllocationThunker) ain;
            this.f66mN.setInput(aint.getNObj());
                }

                @Override 
        public void setCoefficients(float[] v) {
            this.f66mN.setCoefficients(v);
                }

                @Override 
        public void forEach(Allocation aout) {
            AllocationThunker aoutt = (AllocationThunker) aout;
            this.f66mN.forEach(aoutt.getNObj());
                }

                @Override 
        public Script.KernelID getKernelID() {
            Script.KernelID k = createKernelID(0, 2, null, null);
            k.f58mN = this.f66mN.getKernelID();
            return k;
                }

                @Override 
        public Script.FieldID getFieldID_Input() {
            Script.FieldID f = createFieldID(1, null);
            f.f57mN = this.f66mN.getFieldID_Input();
            return f;
                }
            }
