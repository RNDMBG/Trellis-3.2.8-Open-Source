            package android.support.p001v8.renderscript;

            import android.renderscript.BaseObj;
            import android.renderscript.ScriptGroup;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 20 */    class ScriptGroupThunker extends ScriptGroup {

                /* JADX INFO: renamed from: mN */
                ScriptGroup f60mN;

                @Override // android.support.p001v8.renderscript.BaseObj
/* 21 */        /* bridge */ /* synthetic */ BaseObj getNObj() {
/* 22 */            return getNObj();
                }

                @Override // android.support.p001v8.renderscript.BaseObj
/* 25 */        ScriptGroup getNObj() {
/* 26 */            return this.f60mN;
                }

/* 29 */        ScriptGroupThunker(int id, RenderScript rs) {
/* 30 */            super(id, rs);
                }

                @Override // android.support.p001v8.renderscript.ScriptGroup
/* 33 */        public void setInput(Script.KernelID s, Allocation a) {
/* 34 */            AllocationThunker at = (AllocationThunker) a;
/* 35 */            this.f60mN.setInput(s.f58mN, at.getNObj());
                }

                @Override // android.support.p001v8.renderscript.ScriptGroup
/* 38 */        public void setOutput(Script.KernelID s, Allocation a) {
/* 39 */            AllocationThunker at = (AllocationThunker) a;
/* 40 */            this.f60mN.setOutput(s.f58mN, at.getNObj());
                }

                @Override // android.support.p001v8.renderscript.ScriptGroup
/* 43 */        public void execute() {
/* 44 */            this.f60mN.execute();
                }

/* 51 */        public static final class Builder {

                    /* JADX INFO: renamed from: bN */
                    ScriptGroup.Builder f61bN;
                    RenderScript mRS;

/* 52 */            Builder(RenderScript rs) {
/* 54 */                RenderScriptThunker rst = (RenderScriptThunker) rs;
/* 55 */                this.mRS = rs;
/* 56 */                this.f61bN = new ScriptGroup.Builder(rst.f54mN);
                    }

/* 59 */            public Builder addKernel(Script.KernelID k) {
/* 60 */                this.f61bN.addKernel(k.f58mN);
/* 61 */                return this;
                    }

/* 64 */            public Builder addConnection(Type t, Script.KernelID from, Script.FieldID to) {
/* 65 */                TypeThunker tt = (TypeThunker) t;
/* 66 */                this.f61bN.addConnection(tt.getNObj(), from.f58mN, to.f57mN);
/* 67 */                return this;
                    }

/* 70 */            public Builder addConnection(Type t, Script.KernelID from, Script.KernelID to) {
/* 71 */                TypeThunker tt = (TypeThunker) t;
/* 72 */                this.f61bN.addConnection(tt.getNObj(), from.f58mN, to.f58mN);
/* 73 */                return this;
                    }

/* 78 */            public ScriptGroupThunker create() {
/* 79 */                ScriptGroupThunker sg = new ScriptGroupThunker(0, this.mRS);
/* 80 */                sg.f60mN = this.f61bN.create();
/* 81 */                return sg;
                    }
                }
            }
