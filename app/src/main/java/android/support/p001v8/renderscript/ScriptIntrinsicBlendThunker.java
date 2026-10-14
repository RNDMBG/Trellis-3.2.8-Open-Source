            package android.support.p001v8.renderscript;

            import android.renderscript.BaseObj;
            import android.renderscript.Script;
            import android.renderscript.ScriptIntrinsicBlend;
            
            
    class ScriptIntrinsicBlendThunker extends ScriptIntrinsicBlend {

                
                ScriptIntrinsicBlend f63mN;

                @Override 
          BaseObj getNObj() {
            return getNObj();
                }

                @Override 
          Script getNObj() {
            return getNObj();
                }

                @Override 
        ScriptIntrinsicBlend getNObj() {
            return this.f63mN;
                }

        ScriptIntrinsicBlendThunker(int id, RenderScript rs) {
            super(id, rs);
                }

        public static ScriptIntrinsicBlendThunker create(RenderScript rs, Element e) {
            RenderScriptThunker rst = (RenderScriptThunker) rs;
            ElementThunker et = (ElementThunker) e;
            ScriptIntrinsicBlendThunker blend = new ScriptIntrinsicBlendThunker(0, rs);
            blend.f63mN = ScriptIntrinsicBlend.create(rst.f54mN, et.getNObj());
            return blend;
                }

                @Override 
        public void forEachClear(Allocation ain, Allocation aout) {
            AllocationThunker aint = (AllocationThunker) ain;
            AllocationThunker aoutt = (AllocationThunker) aout;
            this.f63mN.forEachClear(aint.getNObj(), aoutt.getNObj());
                }

                @Override 
        public Script.KernelID getKernelIDClear() {
            Script.KernelID k = createKernelID(0, 3, null, null);
            k.f58mN = this.f63mN.getKernelIDClear();
            return k;
                }

                @Override 
        public void forEachSrc(Allocation ain, Allocation aout) {
            AllocationThunker aint = (AllocationThunker) ain;
            AllocationThunker aoutt = (AllocationThunker) aout;
            this.f63mN.forEachSrc(aint.getNObj(), aoutt.getNObj());
                }

                @Override 
        public Script.KernelID getKernelIDSrc() {
            Script.KernelID k = createKernelID(1, 3, null, null);
            k.f58mN = this.f63mN.getKernelIDSrc();
            return k;
                }

                @Override 
        public void forEachDst(Allocation ain, Allocation aout) {
            AllocationThunker aint = (AllocationThunker) ain;
            AllocationThunker aoutt = (AllocationThunker) aout;
            this.f63mN.forEachDst(aint.getNObj(), aoutt.getNObj());
                }

                @Override 
        public Script.KernelID getKernelIDDst() {
            Script.KernelID k = createKernelID(2, 3, null, null);
            k.f58mN = this.f63mN.getKernelIDDst();
            return k;
                }

                @Override 
        public void forEachSrcOver(Allocation ain, Allocation aout) {
            AllocationThunker aint = (AllocationThunker) ain;
            AllocationThunker aoutt = (AllocationThunker) aout;
            this.f63mN.forEachSrcOver(aint.getNObj(), aoutt.getNObj());
                }

                @Override 
        public Script.KernelID getKernelIDSrcOver() {
            Script.KernelID k = createKernelID(3, 3, null, null);
            k.f58mN = this.f63mN.getKernelIDSrcOver();
            return k;
                }

                @Override 
        public void forEachDstOver(Allocation ain, Allocation aout) {
            AllocationThunker aint = (AllocationThunker) ain;
            AllocationThunker aoutt = (AllocationThunker) aout;
            this.f63mN.forEachDstOver(aint.getNObj(), aoutt.getNObj());
                }

                @Override 
        public Script.KernelID getKernelIDDstOver() {
            Script.KernelID k = createKernelID(4, 3, null, null);
           k.f58mN = this.f63mN.getKernelIDDstOver();
           return k;
                }

                @Override 
       public void forEachSrcIn(Allocation ain, Allocation aout) {
           AllocationThunker aint = (AllocationThunker) ain;
           AllocationThunker aoutt = (AllocationThunker) aout;
           this.f63mN.forEachSrcIn(aint.getNObj(), aoutt.getNObj());
                }

                @Override 
       public Script.KernelID getKernelIDSrcIn() {
           Script.KernelID k = createKernelID(5, 3, null, null);
           k.f58mN = this.f63mN.getKernelIDSrcIn();
           return k;
                }

                @Override 
       public void forEachDstIn(Allocation ain, Allocation aout) {
           AllocationThunker aint = (AllocationThunker) ain;
           AllocationThunker aoutt = (AllocationThunker) aout;
           this.f63mN.forEachDstIn(aint.getNObj(), aoutt.getNObj());
                }

                @Override 
       public Script.KernelID getKernelIDDstIn() {
           Script.KernelID k = createKernelID(6, 3, null, null);
           k.f58mN = this.f63mN.getKernelIDDstIn();
           return k;
                }

                @Override 
       public void forEachSrcOut(Allocation ain, Allocation aout) {
           AllocationThunker aint = (AllocationThunker) ain;
           AllocationThunker aoutt = (AllocationThunker) aout;
           this.f63mN.forEachSrcOut(aint.getNObj(), aoutt.getNObj());
                }

                @Override 
       public Script.KernelID getKernelIDSrcOut() {
           Script.KernelID k = createKernelID(7, 3, null, null);
           k.f58mN = this.f63mN.getKernelIDSrcOut();
           return k;
                }

                @Override 
       public void forEachDstOut(Allocation ain, Allocation aout) {
           AllocationThunker aint = (AllocationThunker) ain;
           AllocationThunker aoutt = (AllocationThunker) aout;
           this.f63mN.forEachDstOut(aint.getNObj(), aoutt.getNObj());
                }

                @Override 
       public Script.KernelID getKernelIDDstOut() {
           Script.KernelID k = createKernelID(8, 3, null, null);
           k.f58mN = this.f63mN.getKernelIDDstOut();
           return k;
                }

                @Override 
       public void forEachSrcAtop(Allocation ain, Allocation aout) {
           AllocationThunker aint = (AllocationThunker) ain;
           AllocationThunker aoutt = (AllocationThunker) aout;
           this.f63mN.forEachSrcAtop(aint.getNObj(), aoutt.getNObj());
                }

                @Override 
       public Script.KernelID getKernelIDSrcAtop() {
           Script.KernelID k = createKernelID(9, 3, null, null);
           k.f58mN = this.f63mN.getKernelIDSrcAtop();
           return k;
                }

                @Override 
       public void forEachDstAtop(Allocation ain, Allocation aout) {
           AllocationThunker aint = (AllocationThunker) ain;
           AllocationThunker aoutt = (AllocationThunker) aout;
           this.f63mN.forEachDstAtop(aint.getNObj(), aoutt.getNObj());
                }

                @Override 
       public Script.KernelID getKernelIDDstAtop() {
           Script.KernelID k = createKernelID(10, 3, null, null);
           k.f58mN = this.f63mN.getKernelIDDstAtop();
           return k;
                }

                @Override 
       public void forEachXor(Allocation ain, Allocation aout) {
           AllocationThunker aint = (AllocationThunker) ain;
           AllocationThunker aoutt = (AllocationThunker) aout;
           this.f63mN.forEachXor(aint.getNObj(), aoutt.getNObj());
                }

                @Override 
       public Script.KernelID getKernelIDXor() {
           Script.KernelID k = createKernelID(11, 3, null, null);
           k.f58mN = this.f63mN.getKernelIDXor();
           return k;
                }

                @Override 
       public void forEachMultiply(Allocation ain, Allocation aout) {
           AllocationThunker aint = (AllocationThunker) ain;
           AllocationThunker aoutt = (AllocationThunker) aout;
           this.f63mN.forEachMultiply(aint.getNObj(), aoutt.getNObj());
                }

                @Override 
       public Script.KernelID getKernelIDMultiply() {
           Script.KernelID k = createKernelID(14, 3, null, null);
           k.f58mN = this.f63mN.getKernelIDMultiply();
           return k;
                }

                @Override 
       public void forEachAdd(Allocation ain, Allocation aout) {
           AllocationThunker aint = (AllocationThunker) ain;
           AllocationThunker aoutt = (AllocationThunker) aout;
           this.f63mN.forEachAdd(aint.getNObj(), aoutt.getNObj());
                }

                @Override 
       public Script.KernelID getKernelIDAdd() {
           Script.KernelID k = createKernelID(34, 3, null, null);
           k.f58mN = this.f63mN.getKernelIDAdd();
           return k;
                }

                @Override 
       public void forEachSubtract(Allocation ain, Allocation aout) {
           AllocationThunker aint = (AllocationThunker) ain;
           AllocationThunker aoutt = (AllocationThunker) aout;
           this.f63mN.forEachSubtract(aint.getNObj(), aoutt.getNObj());
                }

                @Override 
       public Script.KernelID getKernelIDSubtract() {
           Script.KernelID k = createKernelID(35, 3, null, null);
           k.f58mN = this.f63mN.getKernelIDSubtract();
           return k;
                }
            }
