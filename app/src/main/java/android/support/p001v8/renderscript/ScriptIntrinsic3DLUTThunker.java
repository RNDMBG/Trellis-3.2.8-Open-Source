            package android.support.p001v8.renderscript;

            import android.renderscript.BaseObj;
            import android.renderscript.Script;
            import android.renderscript.ScriptIntrinsic3DLUT;
            
            
    class ScriptIntrinsic3DLUTThunker extends ScriptIntrinsic3DLUT {

                
                ScriptIntrinsic3DLUT f62mN;

                @Override 
          BaseObj getNObj() {
            return getNObj();
                }

                @Override 
          Script getNObj() {
            return getNObj();
                }

                @Override 
        ScriptIntrinsic3DLUT getNObj() {
            return this.f62mN;
                }

        private ScriptIntrinsic3DLUTThunker(int id, RenderScript rs, Element e) {
            super(id, rs, e);
                }

        public static ScriptIntrinsic3DLUTThunker create(RenderScript rs, Element e) {
            RenderScriptThunker rst = (RenderScriptThunker) rs;
            ElementThunker et = (ElementThunker) e;
            ScriptIntrinsic3DLUTThunker lut = new ScriptIntrinsic3DLUTThunker(0, rs, e);
            lut.f62mN = ScriptIntrinsic3DLUT.create(rst.f54mN, et.getNObj());
            return lut;
                }

                @Override 
        public void setLUT(Allocation lut) {
            AllocationThunker lutt = (AllocationThunker) lut;
            this.f62mN.setLUT(lutt.getNObj());
                }

                @Override 
        public void forEach(Allocation ain, Allocation aout) {
            AllocationThunker aint = (AllocationThunker) ain;
            AllocationThunker aoutt = (AllocationThunker) aout;
            this.f62mN.forEach(aint.getNObj(), aoutt.getNObj());
                }

                @Override 
        public Script.KernelID getKernelID() {
            Script.KernelID k = createKernelID(0, 3, null, null);
            k.f58mN = this.f62mN.getKernelID();
            return k;
                }
            }
