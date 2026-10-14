            package co.vine.android.gles;

            import android.opengl.Matrix;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 38 */    public class Sprite2d {
                private static final String TAG = "Grafika";
                private float mAngle;
                private Drawable2d mDrawable;
                private boolean mMatrixReady;
                private float[] mModelViewMatrix;
                private float mPosX;
                private float mPosY;
                private float mScaleX;
                private float mScaleY;
                private int mTextureId;
                private float[] mScratchMatrix = new float[16];
                private float[] mColor = new float[4];

/* 39 */        public Sprite2d(Drawable2d drawable) {
/* 41 */            this.mDrawable = drawable;
/* 43 */            this.mColor[3] = 1.0f;
/* 44 */            this.mTextureId = -1;
/* 46 */            this.mModelViewMatrix = new float[16];
/* 47 */            this.mMatrixReady = false;
                }

/* 54 */        private void recomputeMatrix() {
/* 55 */            float[] modelView = this.mModelViewMatrix;
/* 57 */            Matrix.setIdentityM(modelView, 0);
/* 58 */            Matrix.translateM(modelView, 0, this.mPosX, this.mPosY, 0.0f);
                    if (this.mAngle != 0.0f) {
/* 60 */                Matrix.rotateM(modelView, 0, this.mAngle, 0.0f, 0.0f, 1.0f);
                    }
/* 62 */            Matrix.scaleM(modelView, 0, this.mScaleX, this.mScaleY, 1.0f);
/* 63 */            this.mMatrixReady = true;
                }

/* 69 */        public float getScaleX() {
/* 70 */            return this.mScaleX;
                }

/* 76 */        public float getScaleY() {
/* 77 */            return this.mScaleY;
                }

/* 83 */        public void setScale(float scaleX, float scaleY) {
/* 84 */            this.mScaleX = scaleX;
/* 85 */            this.mScaleY = scaleY;
/* 86 */            this.mMatrixReady = false;
                }

/* 92 */        public float getRotation() {
/* 93 */            return this.mAngle;
                }

/* 100 */       public void setRotation(float angle) {
                    while (angle >= 360.0f) {
/* 102 */               angle -= 360.0f;
                    }
                    while (angle <= -360.0f) {
/* 105 */               angle += 360.0f;
                    }
/* 107 */           this.mAngle = angle;
/* 108 */           this.mMatrixReady = false;
                }

/* 114 */       public float getPositionX() {
/* 115 */           return this.mPosX;
                }

/* 121 */       public float getPositionY() {
/* 122 */           return this.mPosY;
                }

/* 128 */       public void setPosition(float posX, float posY) {
/* 129 */           this.mPosX = posX;
/* 130 */           this.mPosY = posY;
/* 131 */           this.mMatrixReady = false;
                }

/* 139 */       public float[] getModelViewMatrix() {
/* 140 */           if (!this.mMatrixReady) {
/* 141 */               recomputeMatrix();
                    }
/* 143 */           return this.mModelViewMatrix;
                }

/* 149 */       public void setColor(float red, float green, float blue) {
/* 150 */           this.mColor[0] = red;
/* 151 */           this.mColor[1] = green;
/* 152 */           this.mColor[2] = blue;
                }

/* 158 */       public void setTexture(int textureId) {
/* 159 */           this.mTextureId = textureId;
                }

/* 167 */       public float[] getColor() {
/* 168 */           return this.mColor;
                }

/* 175 */       public void draw(FlatShadedProgram program, float[] projectionMatrix) {
/* 176 */           Matrix.multiplyMM(this.mScratchMatrix, 0, projectionMatrix, 0, getModelViewMatrix(), 0);
/* 178 */           program.draw(this.mScratchMatrix, this.mColor, this.mDrawable.getVertexArray(), 0, this.mDrawable.getVertexCount(), this.mDrawable.getCoordsPerVertex(), this.mDrawable.getVertexStride());
                }

/* 187 */       public void draw(Texture2dProgram program, float[] projectionMatrix) {
/* 188 */           Matrix.multiplyMM(this.mScratchMatrix, 0, projectionMatrix, 0, getModelViewMatrix(), 0);
/* 190 */           program.draw(this.mScratchMatrix, this.mDrawable.getVertexArray(), 0, this.mDrawable.getVertexCount(), this.mDrawable.getCoordsPerVertex(), this.mDrawable.getVertexStride(), GlUtil.IDENTITY_MATRIX, this.mDrawable.getTexCoordArray(), this.mTextureId, this.mDrawable.getTexCoordStride());
                }

/* 197 */       public String toString() {
/* 198 */           return "[Sprite2d pos=" + this.mPosX + "," + this.mPosY + " scale=" + this.mScaleX + "," + this.mScaleY + " angle=" + this.mAngle + " color={" + this.mColor[0] + "," + this.mColor[1] + "," + this.mColor[2] + "} drawable=" + this.mDrawable + "]";
                }
            }
