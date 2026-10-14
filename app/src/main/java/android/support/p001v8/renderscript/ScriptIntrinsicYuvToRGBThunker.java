            package android.support.p001v8.renderscript;

            import android.renderscript.BaseObj;
            import android.renderscript.Script;
            import android.renderscript.ScriptIntrinsicYuvToRGB;
            
            
    public class ScriptIntrinsicYuvToRGBThunker extends ScriptIntrinsicYuvToRGB {

                
                ScriptIntrinsicYuvToRGB f69mN;

                @Override 
          BaseObj getNObj() {
            return getNObj();
                }

                @Override 
          Script getNObj() {
            return getNObj();
                }

                @Override 
        ScriptIntrinsicYuvToRGB getNObj() {
            return this.f69mN;
                }

        private ScriptIntrinsicYuvToRGBThunker(int id, RenderScript rs) {
            super(id, rs);
                }

        public static ScriptIntrinsicYuvToRGBThunker create(RenderScript rs, Element e) {
            RenderScriptThunker rst = (RenderScriptThunker) rs;
            ElementThunker et = (ElementThunker) e;
            ScriptIntrinsicYuvToRGBThunker si = new ScriptIntrinsicYuvToRGBThunker(0, rs);
            si.f69mN = ScriptIntrinsicYuvToRGB.create(rst.f54mN, et.getNObj());
            return si;
                }

                @Override 
        public void setInput(Allocation ain) {
            AllocationThunker aint = (AllocationThunker) ain;
            this.f69mN.setInput(aint.getNObj());
                }

                @Override 
        public void forEach(Allocation aout) {
            AllocationThunker aoutt = (AllocationThunker) aout;
            this.f69mN.forEach(aoutt.getNObj());
                }

                @Override 
        public Script.KernelID getKernelID() {
            Script.KernelID k = createKernelID(0, 2, null, null);
            k.f58mN = this.f69mN.getKernelID();
            return k;
                }

                @Override 
        public Script.FieldID getFieldID_Input() {
            Script.FieldID f = createFieldID(0, null);
            f.f57mN = this.f69mN.getFieldID_Input();
            return f;
                }
            }
