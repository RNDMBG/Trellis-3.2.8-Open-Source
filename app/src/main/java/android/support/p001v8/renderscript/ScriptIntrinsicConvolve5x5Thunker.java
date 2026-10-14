            package android.support.p001v8.renderscript;

            import android.renderscript.BaseObj;
            import android.renderscript.Script;
            import android.renderscript.ScriptIntrinsicConvolve5x5;
            
            
    class ScriptIntrinsicConvolve5x5Thunker extends ScriptIntrinsicConvolve5x5 {

                
                ScriptIntrinsicConvolve5x5 f67mN;

                @Override 
          BaseObj getNObj() {
            return getNObj();
                }

                @Override 
          Script getNObj() {
            return getNObj();
                }

                @Override 
        ScriptIntrinsicConvolve5x5 getNObj() {
            return this.f67mN;
                }

        ScriptIntrinsicConvolve5x5Thunker(int id, RenderScript rs) {
            super(id, rs);
                }

        public static ScriptIntrinsicConvolve5x5Thunker create(RenderScript rs, Element e) {
            RenderScriptThunker rst = (RenderScriptThunker) rs;
            ElementThunker et = (ElementThunker) e;
            ScriptIntrinsicConvolve5x5Thunker si = new ScriptIntrinsicConvolve5x5Thunker(0, rs);
            si.f67mN = ScriptIntrinsicConvolve5x5.create(rst.f54mN, et.getNObj());
            return si;
                }

                @Override 
        public void setInput(Allocation ain) {
            AllocationThunker aint = (AllocationThunker) ain;
            this.f67mN.setInput(aint.getNObj());
                }

                @Override 
        public void setCoefficients(float[] v) {
            this.f67mN.setCoefficients(v);
                }

                @Override 
        public void forEach(Allocation aout) {
            AllocationThunker aoutt = (AllocationThunker) aout;
            this.f67mN.forEach(aoutt.getNObj());
                }

                @Override 
        public Script.KernelID getKernelID() {
            Script.KernelID k = createKernelID(0, 2, null, null);
            k.f58mN = this.f67mN.getKernelID();
            return k;
                }

                @Override 
        public Script.FieldID getFieldID_Input() {
            Script.FieldID f = createFieldID(1, null);
            f.f57mN = this.f67mN.getFieldID_Input();
            return f;
                }
            }
