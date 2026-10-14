            package android.support.p001v8.renderscript;

            import android.renderscript.BaseObj;
            import android.renderscript.Script;
            import android.renderscript.ScriptIntrinsicBlend;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 17 */    class ScriptIntrinsicBlendThunker extends ScriptIntrinsicBlend {

                /* JADX INFO: renamed from: mN */
                ScriptIntrinsicBlend f63mN;

                @Override // android.support.p001v8.renderscript.Script, android.support.p001v8.renderscript.BaseObj
/* 18 */        /* bridge */ /* synthetic */ BaseObj getNObj() {
/* 19 */            return getNObj();
                }

                @Override // android.support.p001v8.renderscript.Script, android.support.p001v8.renderscript.BaseObj
/* 18 */        /* bridge */ /* synthetic */ Script getNObj() {
/* 19 */            return getNObj();
                }

                @Override // android.support.p001v8.renderscript.Script, android.support.p001v8.renderscript.BaseObj
/* 22 */        ScriptIntrinsicBlend getNObj() {
/* 23 */            return this.f63mN;
                }

/* 26 */        ScriptIntrinsicBlendThunker(int id, RenderScript rs) {
/* 27 */            super(id, rs);
                }

/* 30 */        public static ScriptIntrinsicBlendThunker create(RenderScript rs, Element e) {
/* 31 */            RenderScriptThunker rst = (RenderScriptThunker) rs;
/* 32 */            ElementThunker et = (ElementThunker) e;
/* 34 */            ScriptIntrinsicBlendThunker blend = new ScriptIntrinsicBlendThunker(0, rs);
/* 35 */            blend.f63mN = ScriptIntrinsicBlend.create(rst.f54mN, et.getNObj());
/* 36 */            return blend;
                }

                @Override // android.support.p001v8.renderscript.ScriptIntrinsicBlend
/* 39 */        public void forEachClear(Allocation ain, Allocation aout) {
/* 40 */            AllocationThunker aint = (AllocationThunker) ain;
/* 41 */            AllocationThunker aoutt = (AllocationThunker) aout;
/* 43 */            this.f63mN.forEachClear(aint.getNObj(), aoutt.getNObj());
                }

                @Override // android.support.p001v8.renderscript.ScriptIntrinsicBlend
/* 46 */        public Script.KernelID getKernelIDClear() {
/* 47 */            Script.KernelID k = createKernelID(0, 3, null, null);
/* 48 */            k.f58mN = this.f63mN.getKernelIDClear();
/* 49 */            return k;
                }

                @Override // android.support.p001v8.renderscript.ScriptIntrinsicBlend
/* 52 */        public void forEachSrc(Allocation ain, Allocation aout) {
/* 53 */            AllocationThunker aint = (AllocationThunker) ain;
/* 54 */            AllocationThunker aoutt = (AllocationThunker) aout;
/* 56 */            this.f63mN.forEachSrc(aint.getNObj(), aoutt.getNObj());
                }

                @Override // android.support.p001v8.renderscript.ScriptIntrinsicBlend
/* 59 */        public Script.KernelID getKernelIDSrc() {
/* 60 */            Script.KernelID k = createKernelID(1, 3, null, null);
/* 61 */            k.f58mN = this.f63mN.getKernelIDSrc();
/* 62 */            return k;
                }

                @Override // android.support.p001v8.renderscript.ScriptIntrinsicBlend
/* 65 */        public void forEachDst(Allocation ain, Allocation aout) {
/* 66 */            AllocationThunker aint = (AllocationThunker) ain;
/* 67 */            AllocationThunker aoutt = (AllocationThunker) aout;
/* 69 */            this.f63mN.forEachDst(aint.getNObj(), aoutt.getNObj());
                }

                @Override // android.support.p001v8.renderscript.ScriptIntrinsicBlend
/* 72 */        public Script.KernelID getKernelIDDst() {
/* 73 */            Script.KernelID k = createKernelID(2, 3, null, null);
/* 74 */            k.f58mN = this.f63mN.getKernelIDDst();
/* 75 */            return k;
                }

                @Override // android.support.p001v8.renderscript.ScriptIntrinsicBlend
/* 78 */        public void forEachSrcOver(Allocation ain, Allocation aout) {
/* 79 */            AllocationThunker aint = (AllocationThunker) ain;
/* 80 */            AllocationThunker aoutt = (AllocationThunker) aout;
/* 82 */            this.f63mN.forEachSrcOver(aint.getNObj(), aoutt.getNObj());
                }

                @Override // android.support.p001v8.renderscript.ScriptIntrinsicBlend
/* 85 */        public Script.KernelID getKernelIDSrcOver() {
/* 86 */            Script.KernelID k = createKernelID(3, 3, null, null);
/* 87 */            k.f58mN = this.f63mN.getKernelIDSrcOver();
/* 88 */            return k;
                }

                @Override // android.support.p001v8.renderscript.ScriptIntrinsicBlend
/* 91 */        public void forEachDstOver(Allocation ain, Allocation aout) {
/* 92 */            AllocationThunker aint = (AllocationThunker) ain;
/* 93 */            AllocationThunker aoutt = (AllocationThunker) aout;
/* 95 */            this.f63mN.forEachDstOver(aint.getNObj(), aoutt.getNObj());
                }

                @Override // android.support.p001v8.renderscript.ScriptIntrinsicBlend
/* 98 */        public Script.KernelID getKernelIDDstOver() {
/* 99 */            Script.KernelID k = createKernelID(4, 3, null, null);
/* 100 */           k.f58mN = this.f63mN.getKernelIDDstOver();
/* 101 */           return k;
                }

                @Override // android.support.p001v8.renderscript.ScriptIntrinsicBlend
/* 104 */       public void forEachSrcIn(Allocation ain, Allocation aout) {
/* 105 */           AllocationThunker aint = (AllocationThunker) ain;
/* 106 */           AllocationThunker aoutt = (AllocationThunker) aout;
/* 108 */           this.f63mN.forEachSrcIn(aint.getNObj(), aoutt.getNObj());
                }

                @Override // android.support.p001v8.renderscript.ScriptIntrinsicBlend
/* 111 */       public Script.KernelID getKernelIDSrcIn() {
/* 112 */           Script.KernelID k = createKernelID(5, 3, null, null);
/* 113 */           k.f58mN = this.f63mN.getKernelIDSrcIn();
/* 114 */           return k;
                }

                @Override // android.support.p001v8.renderscript.ScriptIntrinsicBlend
/* 117 */       public void forEachDstIn(Allocation ain, Allocation aout) {
/* 118 */           AllocationThunker aint = (AllocationThunker) ain;
/* 119 */           AllocationThunker aoutt = (AllocationThunker) aout;
/* 121 */           this.f63mN.forEachDstIn(aint.getNObj(), aoutt.getNObj());
                }

                @Override // android.support.p001v8.renderscript.ScriptIntrinsicBlend
/* 124 */       public Script.KernelID getKernelIDDstIn() {
/* 125 */           Script.KernelID k = createKernelID(6, 3, null, null);
/* 126 */           k.f58mN = this.f63mN.getKernelIDDstIn();
/* 127 */           return k;
                }

                @Override // android.support.p001v8.renderscript.ScriptIntrinsicBlend
/* 130 */       public void forEachSrcOut(Allocation ain, Allocation aout) {
/* 131 */           AllocationThunker aint = (AllocationThunker) ain;
/* 132 */           AllocationThunker aoutt = (AllocationThunker) aout;
/* 134 */           this.f63mN.forEachSrcOut(aint.getNObj(), aoutt.getNObj());
                }

                @Override // android.support.p001v8.renderscript.ScriptIntrinsicBlend
/* 137 */       public Script.KernelID getKernelIDSrcOut() {
/* 138 */           Script.KernelID k = createKernelID(7, 3, null, null);
/* 139 */           k.f58mN = this.f63mN.getKernelIDSrcOut();
/* 140 */           return k;
                }

                @Override // android.support.p001v8.renderscript.ScriptIntrinsicBlend
/* 143 */       public void forEachDstOut(Allocation ain, Allocation aout) {
/* 144 */           AllocationThunker aint = (AllocationThunker) ain;
/* 145 */           AllocationThunker aoutt = (AllocationThunker) aout;
/* 147 */           this.f63mN.forEachDstOut(aint.getNObj(), aoutt.getNObj());
                }

                @Override // android.support.p001v8.renderscript.ScriptIntrinsicBlend
/* 150 */       public Script.KernelID getKernelIDDstOut() {
/* 151 */           Script.KernelID k = createKernelID(8, 3, null, null);
/* 152 */           k.f58mN = this.f63mN.getKernelIDDstOut();
/* 153 */           return k;
                }

                @Override // android.support.p001v8.renderscript.ScriptIntrinsicBlend
/* 156 */       public void forEachSrcAtop(Allocation ain, Allocation aout) {
/* 157 */           AllocationThunker aint = (AllocationThunker) ain;
/* 158 */           AllocationThunker aoutt = (AllocationThunker) aout;
/* 160 */           this.f63mN.forEachSrcAtop(aint.getNObj(), aoutt.getNObj());
                }

                @Override // android.support.p001v8.renderscript.ScriptIntrinsicBlend
/* 163 */       public Script.KernelID getKernelIDSrcAtop() {
/* 164 */           Script.KernelID k = createKernelID(9, 3, null, null);
/* 165 */           k.f58mN = this.f63mN.getKernelIDSrcAtop();
/* 166 */           return k;
                }

                @Override // android.support.p001v8.renderscript.ScriptIntrinsicBlend
/* 169 */       public void forEachDstAtop(Allocation ain, Allocation aout) {
/* 170 */           AllocationThunker aint = (AllocationThunker) ain;
/* 171 */           AllocationThunker aoutt = (AllocationThunker) aout;
/* 173 */           this.f63mN.forEachDstAtop(aint.getNObj(), aoutt.getNObj());
                }

                @Override // android.support.p001v8.renderscript.ScriptIntrinsicBlend
/* 176 */       public Script.KernelID getKernelIDDstAtop() {
/* 177 */           Script.KernelID k = createKernelID(10, 3, null, null);
/* 178 */           k.f58mN = this.f63mN.getKernelIDDstAtop();
/* 179 */           return k;
                }

                @Override // android.support.p001v8.renderscript.ScriptIntrinsicBlend
/* 182 */       public void forEachXor(Allocation ain, Allocation aout) {
/* 183 */           AllocationThunker aint = (AllocationThunker) ain;
/* 184 */           AllocationThunker aoutt = (AllocationThunker) aout;
/* 186 */           this.f63mN.forEachXor(aint.getNObj(), aoutt.getNObj());
                }

                @Override // android.support.p001v8.renderscript.ScriptIntrinsicBlend
/* 189 */       public Script.KernelID getKernelIDXor() {
/* 190 */           Script.KernelID k = createKernelID(11, 3, null, null);
/* 191 */           k.f58mN = this.f63mN.getKernelIDXor();
/* 192 */           return k;
                }

                @Override // android.support.p001v8.renderscript.ScriptIntrinsicBlend
/* 195 */       public void forEachMultiply(Allocation ain, Allocation aout) {
/* 196 */           AllocationThunker aint = (AllocationThunker) ain;
/* 197 */           AllocationThunker aoutt = (AllocationThunker) aout;
/* 199 */           this.f63mN.forEachMultiply(aint.getNObj(), aoutt.getNObj());
                }

                @Override // android.support.p001v8.renderscript.ScriptIntrinsicBlend
/* 202 */       public Script.KernelID getKernelIDMultiply() {
/* 203 */           Script.KernelID k = createKernelID(14, 3, null, null);
/* 204 */           k.f58mN = this.f63mN.getKernelIDMultiply();
/* 205 */           return k;
                }

                @Override // android.support.p001v8.renderscript.ScriptIntrinsicBlend
/* 208 */       public void forEachAdd(Allocation ain, Allocation aout) {
/* 209 */           AllocationThunker aint = (AllocationThunker) ain;
/* 210 */           AllocationThunker aoutt = (AllocationThunker) aout;
/* 212 */           this.f63mN.forEachAdd(aint.getNObj(), aoutt.getNObj());
                }

                @Override // android.support.p001v8.renderscript.ScriptIntrinsicBlend
/* 215 */       public Script.KernelID getKernelIDAdd() {
/* 216 */           Script.KernelID k = createKernelID(34, 3, null, null);
/* 217 */           k.f58mN = this.f63mN.getKernelIDAdd();
/* 218 */           return k;
                }

                @Override // android.support.p001v8.renderscript.ScriptIntrinsicBlend
/* 221 */       public void forEachSubtract(Allocation ain, Allocation aout) {
/* 222 */           AllocationThunker aint = (AllocationThunker) ain;
/* 223 */           AllocationThunker aoutt = (AllocationThunker) aout;
/* 225 */           this.f63mN.forEachSubtract(aint.getNObj(), aoutt.getNObj());
                }

                @Override // android.support.p001v8.renderscript.ScriptIntrinsicBlend
/* 228 */       public Script.KernelID getKernelIDSubtract() {
/* 229 */           Script.KernelID k = createKernelID(35, 3, null, null);
/* 230 */           k.f58mN = this.f63mN.getKernelIDSubtract();
/* 231 */           return k;
                }
            }
