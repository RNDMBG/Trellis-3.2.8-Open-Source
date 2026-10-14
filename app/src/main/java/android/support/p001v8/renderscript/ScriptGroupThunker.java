            package android.support.p001v8.renderscript;

            import android.renderscript.BaseObj;
            import android.renderscript.ScriptGroup;
            
            
    class ScriptGroupThunker extends ScriptGroup {

                
                ScriptGroup f60mN;

                @Override 
          BaseObj getNObj() {
            return getNObj();
                }

                @Override 
        ScriptGroup getNObj() {
            return this.f60mN;
                }

        ScriptGroupThunker(int id, RenderScript rs) {
            super(id, rs);
                }

                @Override 
        public void setInput(Script.KernelID s, Allocation a) {
            AllocationThunker at = (AllocationThunker) a;
            this.f60mN.setInput(s.f58mN, at.getNObj());
                }

                @Override 
        public void setOutput(Script.KernelID s, Allocation a) {
            AllocationThunker at = (AllocationThunker) a;
            this.f60mN.setOutput(s.f58mN, at.getNObj());
                }

                @Override 
        public void execute() {
            this.f60mN.execute();
                }

        public static final class Builder {

                    
                    ScriptGroup.Builder f61bN;
                    RenderScript mRS;

            Builder(RenderScript rs) {
                RenderScriptThunker rst = (RenderScriptThunker) rs;
                this.mRS = rs;
                this.f61bN = new ScriptGroup.Builder(rst.f54mN);
                    }

            public Builder addKernel(Script.KernelID k) {
                this.f61bN.addKernel(k.f58mN);
                return this;
                    }

            public Builder addConnection(Type t, Script.KernelID from, Script.FieldID to) {
                TypeThunker tt = (TypeThunker) t;
                this.f61bN.addConnection(tt.getNObj(), from.f58mN, to.f57mN);
                return this;
                    }

            public Builder addConnection(Type t, Script.KernelID from, Script.KernelID to) {
                TypeThunker tt = (TypeThunker) t;
                this.f61bN.addConnection(tt.getNObj(), from.f58mN, to.f58mN);
                return this;
                    }

            public ScriptGroupThunker create() {
                ScriptGroupThunker sg = new ScriptGroupThunker(0, this.mRS);
                sg.f60mN = this.f61bN.create();
                return sg;
                    }
                }
            }
