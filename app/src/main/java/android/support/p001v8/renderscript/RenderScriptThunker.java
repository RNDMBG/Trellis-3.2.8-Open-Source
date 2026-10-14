            package android.support.p001v8.renderscript;

            import android.content.Context;
            import android.renderscript.RenderScript;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 31 */    class RenderScriptThunker extends RenderScript {

                /* JADX INFO: renamed from: mN */
                RenderScript f54mN;

                @Override // android.support.p001v8.renderscript.RenderScript
/* 32 */        void validate() {
/* 33 */            if (this.f54mN == null) {
/* 34 */                throw new RSInvalidStateException("Calling RS with no Context active.");
                    }
                }

                @Override // android.support.p001v8.renderscript.RenderScript
/* 38 */        public void setPriority(RenderScript.Priority p) {
/* 39 */            if (p == RenderScript.Priority.LOW) {
                        this.f54mN.setPriority(RenderScript.Priority.LOW);
                    }
/* 40 */            if (p == RenderScript.Priority.NORMAL) {
                        this.f54mN.setPriority(RenderScript.Priority.NORMAL);
                    }
                }

/* 43 */        RenderScriptThunker(Context ctx) {
/* 44 */            super(ctx);
/* 45 */            isNative = true;
                }

/* 48 */        public static RenderScript create(Context ctx, int sdkVersion) {
/* 49 */            RenderScriptThunker rs = new RenderScriptThunker(ctx);
/* 50 */            rs.f54mN = RenderScript.create(ctx, sdkVersion);
/* 51 */            return rs;
                }

                @Override // android.support.p001v8.renderscript.RenderScript
/* 54 */        public void contextDump() {
/* 55 */            this.f54mN.contextDump();
                }

                @Override // android.support.p001v8.renderscript.RenderScript
/* 58 */        public void finish() {
/* 59 */            this.f54mN.finish();
                }

                @Override // android.support.p001v8.renderscript.RenderScript
/* 62 */        public void destroy() {
/* 63 */            this.f54mN.destroy();
/* 64 */            this.f54mN = null;
                }
            }
