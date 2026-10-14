            package android.support.p001v8.renderscript;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 24 */    public class ScriptIntrinsicBlend extends ScriptIntrinsic {
/* 25 */        ScriptIntrinsicBlend(int id, RenderScript rs) {
/* 26 */            super(id, rs);
                }

/* 37 */        public static ScriptIntrinsicBlend create(RenderScript rs, Element e) {
/* 38 */            if (RenderScript.isNative) {
/* 40 */                return ScriptIntrinsicBlendThunker.create(rs, e);
                    }
/* 43 */            int id = rs.nScriptIntrinsicCreate(7, e.getID(rs));
/* 44 */            return new ScriptIntrinsicBlend(id, rs);
                }

/* 48 */        private void blend(int id, Allocation ain, Allocation aout) {
/* 49 */            if (!ain.getElement().isCompatible(Element.U8_4(this.mRS))) {
/* 50 */                throw new RSIllegalArgumentException("Input is not of expected format.");
                    }
/* 52 */            if (!aout.getElement().isCompatible(Element.U8_4(this.mRS))) {
/* 53 */                throw new RSIllegalArgumentException("Output is not of expected format.");
                    }
/* 55 */            forEach(id, ain, aout, null);
                }

/* 64 */        public void forEachClear(Allocation ain, Allocation aout) {
/* 65 */            blend(0, ain, aout);
                }

/* 73 */        public Script.KernelID getKernelIDClear() {
/* 74 */            return createKernelID(0, 3, null, null);
                }

/* 84 */        public void forEachSrc(Allocation ain, Allocation aout) {
/* 85 */            blend(1, ain, aout);
                }

/* 93 */        public Script.KernelID getKernelIDSrc() {
/* 94 */            return createKernelID(1, 3, null, null);
                }

/* 106 */       public void forEachDst(Allocation ain, Allocation aout) {
                }

/* 114 */       public Script.KernelID getKernelIDDst() {
/* 115 */           return createKernelID(2, 3, null, null);
                }

/* 124 */       public void forEachSrcOver(Allocation ain, Allocation aout) {
/* 125 */           blend(3, ain, aout);
                }

/* 133 */       public Script.KernelID getKernelIDSrcOver() {
/* 134 */           return createKernelID(3, 3, null, null);
                }

/* 143 */       public void forEachDstOver(Allocation ain, Allocation aout) {
/* 144 */           blend(4, ain, aout);
                }

/* 152 */       public Script.KernelID getKernelIDDstOver() {
/* 153 */           return createKernelID(4, 3, null, null);
                }

/* 162 */       public void forEachSrcIn(Allocation ain, Allocation aout) {
/* 163 */           blend(5, ain, aout);
                }

/* 171 */       public Script.KernelID getKernelIDSrcIn() {
/* 172 */           return createKernelID(5, 3, null, null);
                }

/* 181 */       public void forEachDstIn(Allocation ain, Allocation aout) {
/* 182 */           blend(6, ain, aout);
                }

/* 190 */       public Script.KernelID getKernelIDDstIn() {
/* 191 */           return createKernelID(6, 3, null, null);
                }

/* 200 */       public void forEachSrcOut(Allocation ain, Allocation aout) {
/* 201 */           blend(7, ain, aout);
                }

/* 209 */       public Script.KernelID getKernelIDSrcOut() {
/* 210 */           return createKernelID(7, 3, null, null);
                }

/* 219 */       public void forEachDstOut(Allocation ain, Allocation aout) {
/* 220 */           blend(8, ain, aout);
                }

/* 228 */       public Script.KernelID getKernelIDDstOut() {
/* 229 */           return createKernelID(8, 3, null, null);
                }

/* 239 */       public void forEachSrcAtop(Allocation ain, Allocation aout) {
/* 240 */           blend(9, ain, aout);
                }

/* 248 */       public Script.KernelID getKernelIDSrcAtop() {
/* 249 */           return createKernelID(9, 3, null, null);
                }

/* 259 */       public void forEachDstAtop(Allocation ain, Allocation aout) {
/* 260 */           blend(10, ain, aout);
                }

/* 268 */       public Script.KernelID getKernelIDDstAtop() {
/* 269 */           return createKernelID(10, 3, null, null);
                }

/* 278 */       public void forEachXor(Allocation ain, Allocation aout) {
/* 279 */           blend(11, ain, aout);
                }

/* 287 */       public Script.KernelID getKernelIDXor() {
/* 288 */           return createKernelID(11, 3, null, null);
                }

/* 307 */       public void forEachMultiply(Allocation ain, Allocation aout) {
/* 308 */           blend(14, ain, aout);
                }

/* 316 */       public Script.KernelID getKernelIDMultiply() {
/* 317 */           return createKernelID(14, 3, null, null);
                }

/* 403 */       public void forEachAdd(Allocation ain, Allocation aout) {
/* 404 */           blend(34, ain, aout);
                }

/* 412 */       public Script.KernelID getKernelIDAdd() {
/* 413 */           return createKernelID(34, 3, null, null);
                }

/* 422 */       public void forEachSubtract(Allocation ain, Allocation aout) {
/* 423 */           blend(35, ain, aout);
                }

/* 431 */       public Script.KernelID getKernelIDSubtract() {
/* 432 */           return createKernelID(35, 3, null, null);
                }
            }
