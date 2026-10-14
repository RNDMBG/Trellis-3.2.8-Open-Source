            package com.lifevibes.mediacoder;

            import android.support.v8.renderscript.Allocation;
            import android.support.v8.renderscript.RenderScript;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 10 */    public class ScriptC_Filters {
                private Allocation _output;

/* 11 */        public ScriptC_Filters(RenderScript mRenderScript) {
                }

/* 17 */        public void set_input(Allocation ain2) {
                }

/* 21 */        public void forEach_RGB888YUV444(Allocation ain) {
                }

/* 24 */        public void set_output(Allocation _output) {
/* 25 */            this._output = _output;
                }

/* 28 */        public Allocation get_output() {
/* 29 */            return this._output;
                }

/* 33 */        public void forEach_YUVPackedToPlanar(Allocation ain, Allocation aout) {
                }

/* 38 */        public void forEach_YUV444ToYUV420(Allocation ain4) {
                }

/* 42 */        public void forEach_YUV444ToYUV420SemiPlanar(Allocation ain4) {
                }

/* 46 */        public void set_output2(Allocation ain4) {
                }

/* 50 */        public void set_output1(Allocation ain3) {
                }
            }
