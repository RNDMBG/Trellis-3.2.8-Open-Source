            package android.support.p001v8.renderscript;

            import android.content.Context;
            import android.renderscript.RenderScript;
            
            
    class RenderScriptThunker extends RenderScript {

                
                RenderScript f54mN;

                @Override 
        void validate() {
            if (this.f54mN == null) {
                throw new RSInvalidStateException("Calling RS with no Context active.");
                    }
                }

                @Override 
        public void setPriority(RenderScript.Priority p) {
            if (p == RenderScript.Priority.LOW) {
                        this.f54mN.setPriority(RenderScript.Priority.LOW);
                    }
            if (p == RenderScript.Priority.NORMAL) {
                        this.f54mN.setPriority(RenderScript.Priority.NORMAL);
                    }
                }

        RenderScriptThunker(Context ctx) {
            super(ctx);
            isNative = true;
                }

        public static RenderScript create(Context ctx, int sdkVersion) {
            RenderScriptThunker rs = new RenderScriptThunker(ctx);
            rs.f54mN = RenderScript.create(ctx, sdkVersion);
            return rs;
                }

                @Override 
        public void contextDump() {
            this.f54mN.contextDump();
                }

                @Override 
        public void finish() {
            this.f54mN.finish();
                }

                @Override 
        public void destroy() {
            this.f54mN.destroy();
            this.f54mN = null;
                }
            }
