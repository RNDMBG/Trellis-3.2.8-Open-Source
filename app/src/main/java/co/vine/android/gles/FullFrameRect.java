            package co.vine.android.gles;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 32 */    public class FullFrameRect {
                private Texture2dProgram mProgram;
                private final Drawable2d mRectDrawable = new Drawable2d(Drawable2d.Prefab.FULL_RECTANGLE);

/* 33 */        public FullFrameRect(Texture2dProgram program) {
/* 35 */            this.mProgram = program;
                }

/* 46 */        public void release(boolean doEglCleanup) {
/* 47 */            if (this.mProgram != null) {
/* 48 */                if (doEglCleanup) {
/* 49 */                    this.mProgram.release();
                        }
/* 51 */                this.mProgram = null;
                    }
                }

/* 58 */        public Texture2dProgram getProgram() {
/* 59 */            return this.mProgram;
                }

/* 67 */        public void changeProgram(Texture2dProgram program) {
/* 68 */            this.mProgram.release();
/* 69 */            this.mProgram = program;
                }

/* 75 */        public int createTextureObject() {
/* 76 */            return this.mProgram.createTextureObject();
                }

/* 83 */        public void drawFrame(int textureId, float[] texMatrix) {
/* 84 */            this.mProgram.draw(GlUtil.IDENTITY_MATRIX, this.mRectDrawable.getVertexArray(), 0, this.mRectDrawable.getVertexCount(), this.mRectDrawable.getCoordsPerVertex(), this.mRectDrawable.getVertexStride(), texMatrix, this.mRectDrawable.getTexCoordArray(), textureId, this.mRectDrawable.getTexCoordStride());
                }
            }
