            package android.support.p001v8.renderscript;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 33 */    public class ScriptIntrinsicColorMatrix extends ScriptIntrinsic {
                private Allocation mInput;
                private final Matrix4f mMatrix;

/* 34 */        protected ScriptIntrinsicColorMatrix(int id, RenderScript rs) {
/* 35 */            super(id, rs);
/* 31 */            this.mMatrix = new Matrix4f();
                }

/* 49 */        public static ScriptIntrinsicColorMatrix create(RenderScript rs, Element e) {
/* 50 */            if (RenderScript.isNative) {
/* 52 */                return ScriptIntrinsicColorMatrixThunker.create(rs, e);
                    }
/* 55 */            if (!e.isCompatible(Element.U8_4(rs))) {
/* 56 */                throw new RSIllegalArgumentException("Unsuported element type.");
                    }
/* 58 */            int id = rs.nScriptIntrinsicCreate(2, e.getID(rs));
/* 59 */            return new ScriptIntrinsicColorMatrix(id, rs);
                }

/* 63 */        private void setMatrix() {
/* 64 */            FieldPacker fp = new FieldPacker(64);
/* 65 */            fp.addMatrix(this.mMatrix);
/* 66 */            setVar(0, fp);
                }

/* 75 */        public void setColorMatrix(Matrix4f m) {
/* 76 */            this.mMatrix.load(m);
/* 77 */            setMatrix();
                }

/* 86 */        public void setColorMatrix(Matrix3f m) {
/* 87 */            this.mMatrix.load(m);
/* 88 */            setMatrix();
                }

/* 96 */        public void setGreyscale() {
/* 97 */            this.mMatrix.loadIdentity();
/* 98 */            this.mMatrix.set(0, 0, 0.299f);
/* 99 */            this.mMatrix.set(1, 0, 0.587f);
/* 100 */           this.mMatrix.set(2, 0, 0.114f);
/* 101 */           this.mMatrix.set(0, 1, 0.299f);
/* 102 */           this.mMatrix.set(1, 1, 0.587f);
/* 103 */           this.mMatrix.set(2, 1, 0.114f);
/* 104 */           this.mMatrix.set(0, 2, 0.299f);
/* 105 */           this.mMatrix.set(1, 2, 0.587f);
/* 106 */           this.mMatrix.set(2, 2, 0.114f);
/* 107 */           setMatrix();
                }

/* 115 */       public void setYUVtoRGB() {
/* 116 */           this.mMatrix.loadIdentity();
/* 117 */           this.mMatrix.set(0, 0, 1.0f);
/* 118 */           this.mMatrix.set(1, 0, 0.0f);
/* 119 */           this.mMatrix.set(2, 0, 1.13983f);
/* 120 */           this.mMatrix.set(0, 1, 1.0f);
/* 121 */           this.mMatrix.set(1, 1, -0.39465f);
/* 122 */           this.mMatrix.set(2, 1, -0.5806f);
/* 123 */           this.mMatrix.set(0, 2, 1.0f);
/* 124 */           this.mMatrix.set(1, 2, 2.03211f);
/* 125 */           this.mMatrix.set(2, 2, 0.0f);
/* 126 */           setMatrix();
                }

/* 134 */       public void setRGBtoYUV() {
/* 135 */           this.mMatrix.loadIdentity();
/* 136 */           this.mMatrix.set(0, 0, 0.299f);
/* 137 */           this.mMatrix.set(1, 0, 0.587f);
/* 138 */           this.mMatrix.set(2, 0, 0.114f);
/* 139 */           this.mMatrix.set(0, 1, -0.14713f);
/* 140 */           this.mMatrix.set(1, 1, -0.28886f);
/* 141 */           this.mMatrix.set(2, 1, 0.436f);
/* 142 */           this.mMatrix.set(0, 2, 0.615f);
/* 143 */           this.mMatrix.set(1, 2, -0.51499f);
/* 144 */           this.mMatrix.set(2, 2, -0.10001f);
/* 145 */           setMatrix();
                }

/* 156 */       public void forEach(Allocation ain, Allocation aout) {
/* 157 */           forEach(0, ain, aout, null);
                }

/* 165 */       public Script.KernelID getKernelID() {
/* 166 */           return createKernelID(0, 3, null, null);
                }
            }
