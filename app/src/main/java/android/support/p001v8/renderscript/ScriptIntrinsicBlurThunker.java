            package android.support.p001v8.renderscript;

            import android.renderscript.BaseObj;
            import android.renderscript.Script;
            import android.renderscript.ScriptIntrinsicBlur;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 21 */    class ScriptIntrinsicBlurThunker extends ScriptIntrinsicBlur {

                /* JADX INFO: renamed from: mN */
                ScriptIntrinsicBlur f64mN;

                @Override // android.support.p001v8.renderscript.Script, android.support.p001v8.renderscript.BaseObj
/* 22 */        /* bridge */ /* synthetic */ BaseObj getNObj() {
/* 23 */            return getNObj();
                }

                @Override // android.support.p001v8.renderscript.Script, android.support.p001v8.renderscript.BaseObj
/* 22 */        /* bridge */ /* synthetic */ Script getNObj() {
/* 23 */            return getNObj();
                }

                @Override // android.support.p001v8.renderscript.Script, android.support.p001v8.renderscript.BaseObj
/* 27 */        ScriptIntrinsicBlur getNObj() {
/* 28 */            return this.f64mN;
                }

/* 31 */        protected ScriptIntrinsicBlurThunker(int id, RenderScript rs) {
/* 32 */            super(id, rs);
                }

/* 35 */        public static ScriptIntrinsicBlurThunker create(RenderScript rs, Element e) {
/* 36 */            RenderScriptThunker rst = (RenderScriptThunker) rs;
/* 37 */            ElementThunker et = (ElementThunker) e;
/* 39 */            ScriptIntrinsicBlurThunker blur = new ScriptIntrinsicBlurThunker(0, rs);
/* 40 */            blur.f64mN = ScriptIntrinsicBlur.create(rst.f54mN, et.getNObj());
/* 41 */            return blur;
                }

                @Override // android.support.p001v8.renderscript.ScriptIntrinsicBlur
/* 44 */        public void setInput(Allocation ain) {
/* 45 */            AllocationThunker aint = (AllocationThunker) ain;
/* 46 */            this.f64mN.setInput(aint.getNObj());
                }

                @Override // android.support.p001v8.renderscript.ScriptIntrinsicBlur
/* 49 */        public void setRadius(float radius) {
/* 50 */            this.f64mN.setRadius(radius);
                }

                @Override // android.support.p001v8.renderscript.ScriptIntrinsicBlur
/* 53 */        public void forEach(Allocation aout) {
/* 54 */            AllocationThunker aoutt = (AllocationThunker) aout;
/* 55 */            if (aoutt != null) {
/* 56 */                this.f64mN.forEach(aoutt.getNObj());
                    }
                }

                @Override // android.support.p001v8.renderscript.ScriptIntrinsicBlur
/* 60 */        public Script.KernelID getKernelID() {
/* 61 */            Script.KernelID k = createKernelID(0, 2, null, null);
/* 62 */            k.f58mN = this.f64mN.getKernelID();
/* 63 */            return k;
                }

                @Override // android.support.p001v8.renderscript.ScriptIntrinsicBlur
/* 66 */        public Script.FieldID getFieldID_Input() {
/* 67 */            Script.FieldID f = createFieldID(1, null);
/* 68 */            f.f57mN = this.f64mN.getFieldID_Input();
/* 69 */            return f;
                }
            }
