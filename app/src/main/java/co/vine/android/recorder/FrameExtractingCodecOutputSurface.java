            package co.vine.android.recorder;

            import android.graphics.Bitmap;
            import android.graphics.Canvas;
            import android.graphics.Matrix;
            import android.graphics.Point;
            import android.graphics.SurfaceTexture;
            import android.opengl.GLES20;
            import java.nio.ByteBuffer;
            import java.nio.ByteOrder;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 35 */    public class FrameExtractingCodecOutputSurface extends CodecOutputSurface implements SurfaceTexture.OnFrameAvailableListener {
                protected Canvas mBitmapCanvas;
                private final Matrix mMatrix;
                protected Bitmap mOutputBitmap;
                private ByteBuffer mPixelBuf;

/* 36 */        public FrameExtractingCodecOutputSurface(int inWidth, int inHeight, int targetLongestSide, int orientation) {
/* 37 */            super(inWidth, inHeight, targetLongestSide, null);
/* 39 */            this.mOutputBitmap = Bitmap.createBitmap(this.mInWidth, this.mInHeight, Bitmap.Config.ARGB_8888);
/* 40 */            this.mMatrix = new Matrix();
/* 41 */            this.mMatrix.postRotate(orientation, this.mInWidth / 2, this.mInHeight / 2);
/* 43 */            this.mPixelBuf = ByteBuffer.allocateDirect(this.mInWidth * this.mInHeight * 4);
/* 44 */            this.mPixelBuf.order(ByteOrder.LITTLE_ENDIAN);
/* 45 */            this.mPaint.setAntiAlias(false);
/* 46 */            this.mPaint.setDither(false);
                }

                @Override // co.vine.android.recorder.CodecOutputSurface
/* 50 */        protected void establishSize(int inWidth, int inHeight, int outSiz, Point cropOrigin) {
/* 51 */            int longestSide = Math.max(inWidth, inHeight);
/* 52 */            float scale = outSiz / longestSide;
/* 53 */            this.mInWidth = (int) (inWidth * scale);
/* 54 */            this.mInHeight = (int) (inHeight * scale);
                }

/* 58 */        public Bitmap saveFrame() {
/* 59 */            this.mPixelBuf.rewind();
/* 60 */            GLES20.glReadPixels(0, 0, this.mInWidth, this.mInHeight, 6408, 5121, this.mPixelBuf);
/* 62 */            this.mPixelBuf.rewind();
/* 64 */            Bitmap result = Bitmap.createBitmap(this.mInWidth, this.mInHeight, Bitmap.Config.ARGB_8888);
/* 65 */            this.mBitmapCanvas = new Canvas(result);
/* 66 */            this.mOutputBitmap.copyPixelsFromBuffer(this.mPixelBuf);
/* 67 */            this.mBitmapCanvas.drawBitmap(this.mOutputBitmap, this.mMatrix, this.mPaint);
/* 68 */            return result;
                }

                @Override // co.vine.android.recorder.CodecOutputSurface
/* 72 */        public int getMinificationFilteringMode() {
/* 73 */            return 9728;
                }
            }
