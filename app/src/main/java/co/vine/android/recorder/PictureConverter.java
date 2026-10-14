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
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 16 */    public class PictureConverter {
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

/* 17 */        static /* synthetic */ int access$000(PictureConverter x0) {
/* 18 */            return x0.lastH;
                }

/* 17 */        static /* synthetic */ int access$100(PictureConverter x0) {
/* 18 */            return x0.lastW;
                }

/* 17 */        static /* synthetic */ Bitmap access$200(PictureConverter x0) {
/* 18 */            return x0.srcBmp;
                }

/* 17 */        static /* synthetic */ Paint access$300(PictureConverter x0) {
/* 18 */            return x0.mPaint;
                }

/* 32 */        public class ScaleStep {
                    private final Bitmap mPreScaleBitmap;
                    private final Canvas mPreScaleCanvas;
                    private final Matrix mPreScaleMatrix;
                    private final int scaledH;
                    private final int scaledW;

/* 33 */            void access$400() {
/* 34 */                scale();
                    }

/* 33 */            Bitmap access$500() {
/* 34 */                return this.mPreScaleBitmap;
                    }

/* 33 */            int access$700() {
/* 34 */                return this.scaledW;
                    }

/* 33 */            int access$800() {
/* 34 */                return this.scaledH;
                    }

/* 40 */            private ScaleStep(float w2h) {
/* 42 */                this.mPreScaleMatrix = new Matrix();
/* 43 */                this.scaledW = (int) (PictureConverter.access$000(PictureConverter.this) * w2h);
/* 44 */                this.scaledH = PictureConverter.access$000(PictureConverter.this);
/* 45 */                this.mPreScaleMatrix.setScale(this.scaledW / PictureConverter.access$100(PictureConverter.this), 1.0f);
/* 47 */                this.mPreScaleBitmap = Bitmap.createBitmap(this.scaledW, this.scaledH, RecordConfigUtils.DEFAULT_BITMAP_CONFIG);
/* 50 */                this.mPreScaleCanvas = new Canvas(this.mPreScaleBitmap);
                    }

/* 53 */            private void scale() {
/* 54 */                this.mPreScaleCanvas.drawBitmap(PictureConverter.access$200(PictureConverter.this), this.mPreScaleMatrix, PictureConverter.access$300(PictureConverter.this));
                    }
                }

/* 58 */        public PictureConverter(RenderScript rs, int targetSize, CameraSetting cameraSetting) throws VineNotSupportedException {
/* 60 */            this.mRS = rs;
/* 62 */            this.mTargetSize = targetSize;
                    try {
/* 65 */                setCameraSetting(cameraSetting);
                    } catch (Exception e) {
/* 67 */                throw new VineNotSupportedException();
                    }
                }

/* 71 */        public void draw(Canvas canvas) {
/* 72 */            if (this.mScaleStep == null) {
/* 73 */                canvas.drawBitmap(this.srcBmp, this.mMatrix, this.mPaint);
                    } else {
/* 75 */                this.mScaleStep.access$400();
/* 76 */                canvas.drawBitmap(this.mScaleStep.access$500(), this.mMatrix, this.mPaint);
                    }
                }

/* 80 */        public void drawGeneric(Canvas canvas, Bitmap bitmap, Matrix matrix) {
/* 81 */            canvas.drawBitmap(bitmap, matrix, this.mPaint);
                }

/* 84 */        public boolean updateSettingsIfNeeded(CameraSetting cameraSetting) {
/* 85 */            boolean hasChanged = (this.lastW == cameraSetting.originalW && this.lastH == cameraSetting.originalH && cameraSetting.frontFacing == this.mLastFront) ? false : true;
/* 87 */            if (this.srcBmp == null || hasChanged) {
                        try {
/* 89 */                    setCameraSetting(cameraSetting);
                        } catch (VineNotSupportedException e) {
/* 91 */                    CrashUtil.log("Failed to make RsYuv, it should never reach here.");
                        }
                    }
/* 94 */            return hasChanged;
                }

/* 97 */        private void setCameraSetting(CameraSetting cameraSetting) throws VineNotSupportedException {
/* 98 */            if (this.srcBmp != null) {
/* 99 */                this.srcBmp.recycle();
/* 100 */               this.srcBmp = null;
                    }
/* 102 */           this.srcBmp = Bitmap.createBitmap(cameraSetting.originalW, cameraSetting.originalH, RecordConfigUtils.DEFAULT_BITMAP_CONFIG);
/* 105 */           int currentFrameSize = cameraSetting.originalW * cameraSetting.originalH;
/* 106 */           this.lastH = cameraSetting.originalH;
/* 107 */           this.lastW = cameraSetting.originalW;
/* 108 */           this.mLastFront = cameraSetting.frontFacing;
/* 110 */           if (cameraSetting.frontFacing && cameraSetting.frontFacingAspectRatio > 0.0f) {
/* 111 */               this.mScaleStep = new ScaleStep(cameraSetting.frontFacingAspectRatio);
                    } else if (cameraSetting.backFacingAspectRatio > 0.0f) {
/* 113 */               this.mScaleStep = new ScaleStep(cameraSetting.backFacingAspectRatio);
                    } else {
/* 115 */               this.mScaleStep = null;
                    }
/* 118 */           giveMatrixNewValuesWithScaleIfDegreeHasChanged(cameraSetting.degrees, false);
/* 119 */           allocate(currentFrameSize, cameraSetting.originalW, cameraSetting.originalH);
                }

/* 122 */       public void giveMatrixNewValuesWithScaleIfDegreeHasChangedWithKnownConfigs(int degrees, boolean frontFacing) {
/* 123 */           giveMatrixNewValuesWithScaleIfDegreeHasChanged(degrees, frontFacing);
                }

/* 127 */       public boolean convert(CameraSetting cameraSetting, @NonNull VideoData next, boolean hasChanged) {
                    try {
/* 128 */               convertUsingRenderScript(cameraSetting, next, hasChanged);
/* 129 */               return true;
                    } catch (RSInvalidStateException e) {
                        return false;
                    } catch (android.support.v8.renderscript.RSInvalidStateException e2) {
                        return false;
                    }
                }

/* 139 */       private void convertUsingRenderScript(CameraSetting cameraSetting, VideoData next, boolean hasChanged) {
/* 140 */           if (next.data == null) {
/* 141 */               CrashUtil.log("Data is null.");
                    }
/* 143 */           if (this.mRS != null && (this.mFilterYuv == null || hasChanged)) {
                        try {
/* 145 */                   this.mFilterYuv = new RsYuv(this.mRS, cameraSetting.originalW, cameraSetting.originalH);
                        } catch (VineNotSupportedException e) {
/* 147 */                   CrashUtil.log("Failed to RsYuv, it should never reach here.");
                        }
                    }
/* 150 */           this.mFilterYuv.execute(next.data, this.srcBmp);
                }

/* 153 */       private void giveMatrixNewValuesWithScaleIfDegreeHasChanged(int degrees, boolean mirror) {
/* 154 */           if (this.mLastDegree != degrees || this.mLastMirrored != mirror) {
/* 155 */               int width = this.mScaleStep == null ? this.lastW : this.mScaleStep.access$700();
/* 156 */               int height = this.mScaleStep == null ? this.lastH : this.mScaleStep.access$800();
/* 157 */               this.mMatrix.reset();
/* 158 */               float scaleFactor = Math.max(this.mTargetSize / width, this.mTargetSize / height);
/* 159 */               float actualWidth = width * scaleFactor;
/* 160 */               float actualHeight = height * scaleFactor;
/* 161 */               float offsetX = (-(actualWidth - this.mTargetSize)) / 2.0f;
/* 162 */               float offsetY = (-(actualHeight - this.mTargetSize)) / 2.0f;
/* 163 */               this.mMatrix.postRotate(degrees, width / 2, height / 2);
/* 164 */               SLog.m447d("Creating with mirrored {}.", Boolean.valueOf(mirror));
/* 165 */               if (mirror) {
/* 166 */                   this.mMatrix.postScale(-scaleFactor, scaleFactor);
/* 167 */                   this.mMatrix.postTranslate((-offsetX) + this.mTargetSize, offsetY);
                        } else {
/* 169 */                   this.mMatrix.postScale(scaleFactor, scaleFactor);
/* 170 */                   this.mMatrix.postTranslate(offsetX, offsetY);
                        }
/* 172 */               this.mLastMirrored = mirror;
/* 173 */               this.mLastDegree = degrees;
                    }
                }

/* 177 */       private void allocate(int frameSize, int originalW, int originalH) throws VineNotSupportedException {
/* 178 */           this.mFilterYuv = new RsYuv(this.mRS, originalW, originalH);
                }
            }
