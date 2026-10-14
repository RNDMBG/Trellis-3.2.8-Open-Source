            package co.vine.android.recorder;

            import android.graphics.Bitmap;
            import android.graphics.Canvas;
            import android.graphics.Matrix;
            import android.graphics.Paint;
            import android.renderscript.RSInvalidStateException;
            import android.support.annotation.NonNull;
            import android.support.v8.renderscript.RenderScript;
            import co.vine.android.VineNotSupportedException;
            import co.vine.android.recorder.camera.CameraSetting;
            import co.vine.android.recorder.video.VideoData;
            import co.vine.android.util.CrashUtil;
            import com.edisonwang.android.slog.SLog;
            
            
    public class PictureConverter {
                private int lastH;
                private int lastW;
                private RsYuv mFilterYuv;
                private boolean mLastFront;
                private RenderScript mRS;
                private ScaleStep mScaleStep;
                private final int mTargetSize;
                private Bitmap srcBmp;
                private int mLastDegree = -99999;
                public final int[] LOCK = new int[0];
                private boolean mLastMirrored = false;
                private final Matrix mMatrix = new Matrix();
                private final Paint mPaint = new Paint();

        static  int access$000(PictureConverter x0) {
            return x0.lastH;
                }

        static  int access$100(PictureConverter x0) {
            return x0.lastW;
                }

        static  Bitmap access$200(PictureConverter x0) {
            return x0.srcBmp;
                }

        static  Paint access$300(PictureConverter x0) {
            return x0.mPaint;
                }

        public class ScaleStep {
                    private final Bitmap mPreScaleBitmap;
                    private final Canvas mPreScaleCanvas;
                    private final Matrix mPreScaleMatrix;
                    private final int scaledH;
                    private final int scaledW;

            void access$400() {
                scale();
                    }

            Bitmap access$500() {
                return this.mPreScaleBitmap;
                    }

            int access$700() {
                return this.scaledW;
                    }

            int access$800() {
                return this.scaledH;
                    }

            private ScaleStep(float w2h) {
                this.mPreScaleMatrix = new Matrix();
                this.scaledW = (int) (PictureConverter.access$000(PictureConverter.this) * w2h);
                this.scaledH = PictureConverter.access$000(PictureConverter.this);
                this.mPreScaleMatrix.setScale(this.scaledW / PictureConverter.access$100(PictureConverter.this), 1.0f);
                this.mPreScaleBitmap = Bitmap.createBitmap(this.scaledW, this.scaledH, RecordConfigUtils.DEFAULT_BITMAP_CONFIG);
                this.mPreScaleCanvas = new Canvas(this.mPreScaleBitmap);
                    }

            private void scale() {
                this.mPreScaleCanvas.drawBitmap(PictureConverter.access$200(PictureConverter.this), this.mPreScaleMatrix, PictureConverter.access$300(PictureConverter.this));
                    }
                }

        public PictureConverter(RenderScript rs, int targetSize, CameraSetting cameraSetting) throws VineNotSupportedException {
            this.mRS = rs;
            this.mTargetSize = targetSize;
                    try {
                setCameraSetting(cameraSetting);
                    } catch (Exception e) {
                throw new VineNotSupportedException();
                    }
                }

        public void draw(Canvas canvas) {
            if (this.mScaleStep == null) {
                canvas.drawBitmap(this.srcBmp, this.mMatrix, this.mPaint);
                    } else {
                this.mScaleStep.access$400();
                canvas.drawBitmap(this.mScaleStep.access$500(), this.mMatrix, this.mPaint);
                    }
                }

        public void drawGeneric(Canvas canvas, Bitmap bitmap, Matrix matrix) {
            canvas.drawBitmap(bitmap, matrix, this.mPaint);
                }

        public boolean updateSettingsIfNeeded(CameraSetting cameraSetting) {
            boolean hasChanged = (this.lastW == cameraSetting.originalW && this.lastH == cameraSetting.originalH && cameraSetting.frontFacing == this.mLastFront) ? false : true;
            if (this.srcBmp == null || hasChanged) {
                        try {
                    setCameraSetting(cameraSetting);
                        } catch (VineNotSupportedException e) {
                    CrashUtil.log("Failed to make RsYuv, it should never reach here.");
                        }
                    }
            return hasChanged;
                }

        private void setCameraSetting(CameraSetting cameraSetting) throws VineNotSupportedException {
            if (this.srcBmp != null) {
                this.srcBmp.recycle();
               this.srcBmp = null;
                    }
           this.srcBmp = Bitmap.createBitmap(cameraSetting.originalW, cameraSetting.originalH, RecordConfigUtils.DEFAULT_BITMAP_CONFIG);
           int currentFrameSize = cameraSetting.originalW * cameraSetting.originalH;
           this.lastH = cameraSetting.originalH;
           this.lastW = cameraSetting.originalW;
           this.mLastFront = cameraSetting.frontFacing;
           if (cameraSetting.frontFacing && cameraSetting.frontFacingAspectRatio > 0.0f) {
               this.mScaleStep = new ScaleStep(cameraSetting.frontFacingAspectRatio);
                    } else if (cameraSetting.backFacingAspectRatio > 0.0f) {
               this.mScaleStep = new ScaleStep(cameraSetting.backFacingAspectRatio);
                    } else {
               this.mScaleStep = null;
                    }
           giveMatrixNewValuesWithScaleIfDegreeHasChanged(cameraSetting.degrees, false);
           allocate(currentFrameSize, cameraSetting.originalW, cameraSetting.originalH);
                }

       public void giveMatrixNewValuesWithScaleIfDegreeHasChangedWithKnownConfigs(int degrees, boolean frontFacing) {
           giveMatrixNewValuesWithScaleIfDegreeHasChanged(degrees, frontFacing);
                }

       public boolean convert(CameraSetting cameraSetting, @NonNull VideoData next, boolean hasChanged) {
                    try {
               convertUsingRenderScript(cameraSetting, next, hasChanged);
               return true;
                    } catch (RSInvalidStateException e) {
                        return false;
                    } catch (android.support.v8.renderscript.RSInvalidStateException e2) {
                        return false;
                    }
                }

       private void convertUsingRenderScript(CameraSetting cameraSetting, VideoData next, boolean hasChanged) {
           if (next.data == null) {
               CrashUtil.log("Data is null.");
                    }
           if (this.mRS != null && (this.mFilterYuv == null || hasChanged)) {
                        try {
                   this.mFilterYuv = new RsYuv(this.mRS, cameraSetting.originalW, cameraSetting.originalH);
                        } catch (VineNotSupportedException e) {
                   CrashUtil.log("Failed to RsYuv, it should never reach here.");
                        }
                    }
           this.mFilterYuv.execute(next.data, this.srcBmp);
                }

       private void giveMatrixNewValuesWithScaleIfDegreeHasChanged(int degrees, boolean mirror) {
           if (this.mLastDegree != degrees || this.mLastMirrored != mirror) {
               int width = this.mScaleStep == null ? this.lastW : this.mScaleStep.access$700();
               int height = this.mScaleStep == null ? this.lastH : this.mScaleStep.access$800();
               this.mMatrix.reset();
               float scaleFactor = Math.max(this.mTargetSize / width, this.mTargetSize / height);
               float actualWidth = width * scaleFactor;
               float actualHeight = height * scaleFactor;
               float offsetX = (-(actualWidth - this.mTargetSize)) / 2.0f;
               float offsetY = (-(actualHeight - this.mTargetSize)) / 2.0f;
               this.mMatrix.postRotate(degrees, width / 2, height / 2);
               SLog.m447d("Creating with mirrored {}.", Boolean.valueOf(mirror));
               if (mirror) {
                   this.mMatrix.postScale(-scaleFactor, scaleFactor);
                   this.mMatrix.postTranslate((-offsetX) + this.mTargetSize, offsetY);
                        } else {
                   this.mMatrix.postScale(scaleFactor, scaleFactor);
                   this.mMatrix.postTranslate(offsetX, offsetY);
                        }
               this.mLastMirrored = mirror;
               this.mLastDegree = degrees;
                    }
                }

       private void allocate(int frameSize, int originalW, int originalH) throws VineNotSupportedException {
           this.mFilterYuv = new RsYuv(this.mRS, originalW, originalH);
                }
            }
