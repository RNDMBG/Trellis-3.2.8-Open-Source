            package android.support.p001v8.renderscript;

            import android.renderscript.BaseObj;
            import android.renderscript.Script;
            import android.renderscript.ScriptIntrinsicLUT;
            
            
    class ScriptIntrinsicLUTThunker extends ScriptIntrinsicLUT {

                
                ScriptIntrinsicLUT f68mN;

                @Override 
          BaseObj getNObj() {
            return getNObj();
                }

                @Override 
          Script getNObj() {
            return getNObj();
                }

                @Override 
        ScriptIntrinsicLUT getNObj() {
            return this.f68mN;
                }

        private ScriptIntrinsicLUTThunker(int id, RenderScript rs) {
            super(id, rs);
                }

        public static ScriptIntrinsicLUTThunker create(RenderScript rs, Element e) {
            RenderScriptThunker rst = (RenderScriptThunker) rs;
            ElementThunker et = (ElementThunker) e;
            ScriptIntrinsicLUTThunker si = new ScriptIntrinsicLUTThunker(0, rs);
            si.f68mN = ScriptIntrinsicLUT.create(rst.f54mN, et.getNObj());
            return si;
                }

                @Override 
        public void setRed(int index, int value) {
            this.f68mN.setRed(index, value);
                }

                @Override 
        public void setGreen(int index, int value) {
            this.f68mN.setGreen(index, value);
                }

                @Override 
        public void setBlue(int index, int value) {
            this.f68mN.setBlue(index, value);
                }

                @Override 
        public void setAlpha(int index, int value) {
            this.f68mN.setAlpha(index, value);
                }

                @Override 
        public void forEach(Allocation ain, Allocation aout) {
            AllocationThunker aint = (AllocationThunker) ain;
            AllocationThunker aoutt = (AllocationThunker) aout;
            this.f68mN.forEach(aint.getNObj(), aoutt.getNObj());
                }

                @Override 
        public Script.KernelID getKernelID() {
            Script.KernelID k = createKernelID(0, 3, null, null);
            k.f58mN = this.f68mN.getKernelID();
            return k;
                }
            }
