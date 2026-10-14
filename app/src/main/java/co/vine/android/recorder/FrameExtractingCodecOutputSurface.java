            package co.vine.android.recorder;

            import android.graphics.Bitmap;
            import android.graphics.Canvas;
            import android.graphics.Matrix;
            import android.graphics.Point;
            import android.graphics.SurfaceTexture;
            import android.opengl.GLES20;
            import java.nio.ByteBuffer;
            import java.nio.ByteOrder;
            
            
    public class FrameExtractingCodecOutputSurface extends CodecOutputSurface implements SurfaceTexture.OnFrameAvailableListener {
                protected Canvas mBitmapCanvas;
                private final Matrix mMatrix;
                protected Bitmap mOutputBitmap;
                private ByteBuffer mPixelBuf;

        public FrameExtractingCodecOutputSurface(int inWidth, int inHeight, int targetLongestSide, int orientation) {
            super(inWidth, inHeight, targetLongestSide, null);
            this.mOutputBitmap = Bitmap.createBitmap(this.mInWidth, this.mInHeight, Bitmap.Config.ARGB_8888);
            this.mMatrix = new Matrix();
            this.mMatrix.postRotate(orientation, this.mInWidth / 2, this.mInHeight / 2);
            this.mPixelBuf = ByteBuffer.allocateDirect(this.mInWidth * this.mInHeight * 4);
            this.mPixelBuf.order(ByteOrder.LITTLE_ENDIAN);
            this.mPaint.setAntiAlias(false);
            this.mPaint.setDither(false);
                }

                @Override 
        protected void establishSize(int inWidth, int inHeight, int outSiz, Point cropOrigin) {
            int longestSide = Math.max(inWidth, inHeight);
            float scale = outSiz / longestSide;
            this.mInWidth = (int) (inWidth * scale);
            this.mInHeight = (int) (inHeight * scale);
                }

        public Bitmap saveFrame() {
            this.mPixelBuf.rewind();
            GLES20.glReadPixels(0, 0, this.mInWidth, this.mInHeight, 6408, 5121, this.mPixelBuf);
            this.mPixelBuf.rewind();
            Bitmap result = Bitmap.createBitmap(this.mInWidth, this.mInHeight, Bitmap.Config.ARGB_8888);
            this.mBitmapCanvas = new Canvas(result);
            this.mOutputBitmap.copyPixelsFromBuffer(this.mPixelBuf);
            this.mBitmapCanvas.drawBitmap(this.mOutputBitmap, this.mMatrix, this.mPaint);
            return result;
                }

                @Override 
        public int getMinificationFilteringMode() {
            return 9728;
                }
            }
