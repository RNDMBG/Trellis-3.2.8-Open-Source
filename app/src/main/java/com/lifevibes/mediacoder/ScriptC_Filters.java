            package com.lifevibes.mediacoder;

            import android.support.v8.renderscript.Allocation;
            import android.support.v8.renderscript.RenderScript;
            
            
    public class ScriptC_Filters {
                private Allocation _output;

        public ScriptC_Filters(RenderScript mRenderScript) {
                }

        public void set_input(Allocation ain2) {
                }

        public void forEach_RGB888YUV444(Allocation ain) {
                }

        public void set_output(Allocation _output) {
            this._output = _output;
                }

        public Allocation get_output() {
            return this._output;
                }

        public void forEach_YUVPackedToPlanar(Allocation ain, Allocation aout) {
                }

        public void forEach_YUV444ToYUV420(Allocation ain4) {
                }

        public void forEach_YUV444ToYUV420SemiPlanar(Allocation ain4) {
                }

        public void set_output2(Allocation ain4) {
                }

        public void set_output1(Allocation ain3) {
                }
            }
