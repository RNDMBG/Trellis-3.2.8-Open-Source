            package android.support.p001v8.renderscript;

            import android.renderscript.BaseObj;
            import android.renderscript.Script;
            import android.renderscript.ScriptIntrinsicBlur;
            
            
    class ScriptIntrinsicBlurThunker extends ScriptIntrinsicBlur {

                
                ScriptIntrinsicBlur f64mN;

                @Override 
          BaseObj getNObj() {
            return getNObj();
                }

                @Override 
          Script getNObj() {
            return getNObj();
                }

                @Override 
        ScriptIntrinsicBlur getNObj() {
            return this.f64mN;
                }

        protected ScriptIntrinsicBlurThunker(int id, RenderScript rs) {
            super(id, rs);
                }

        public static ScriptIntrinsicBlurThunker create(RenderScript rs, Element e) {
            RenderScriptThunker rst = (RenderScriptThunker) rs;
            ElementThunker et = (ElementThunker) e;
            ScriptIntrinsicBlurThunker blur = new ScriptIntrinsicBlurThunker(0, rs);
            blur.f64mN = ScriptIntrinsicBlur.create(rst.f54mN, et.getNObj());
            return blur;
                }

                @Override 
        public void setInput(Allocation ain) {
            AllocationThunker aint = (AllocationThunker) ain;
            this.f64mN.setInput(aint.getNObj());
                }

                @Override 
        public void setRadius(float radius) {
            this.f64mN.setRadius(radius);
                }

                @Override 
        public void forEach(Allocation aout) {
            AllocationThunker aoutt = (AllocationThunker) aout;
            if (aoutt != null) {
                this.f64mN.forEach(aoutt.getNObj());
                    }
                }

                @Override 
        public Script.KernelID getKernelID() {
            Script.KernelID k = createKernelID(0, 2, null, null);
            k.f58mN = this.f64mN.getKernelID();
            return k;
                }

                @Override 
        public Script.FieldID getFieldID_Input() {
            Script.FieldID f = createFieldID(1, null);
            f.f57mN = this.f64mN.getFieldID_Input();
            return f;
                }
            }
