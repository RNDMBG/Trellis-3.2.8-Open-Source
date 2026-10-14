            package co.vine.android.recorder;

            import android.content.Context;
            import android.graphics.Canvas;
            import android.graphics.Paint;
            import android.util.AttributeSet;
            import android.view.View;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 21 */    public abstract class ProgressView extends View {
                private static int DEFAULT_PRIMARY_COLOR = -1;
                protected final Paint mPaint;
                private int mPrimaryColor;
                private volatile float mProgressRatio;
                private int mSelectedColor;
                private volatile float mSelectedEndRatio;
                private volatile float mSelectedStartRatio;

                protected abstract void drawProgress(Canvas canvas, float f, float f2, int i);

/* 22 */        public static void initDefaultPirmaryColor(int color) {
/* 23 */            DEFAULT_PRIMARY_COLOR = color;
                }

/* 26 */        public ProgressView(Context context) {
/* 27 */            super(context);
/* 28 */            this.mPaint = init();
                }

/* 31 */        public ProgressView(Context context, AttributeSet attrs) {
/* 32 */            super(context, attrs);
/* 33 */            this.mPaint = init();
                }

/* 36 */        public ProgressView(Context context, AttributeSet attrs, int defStyle) {
/* 37 */            super(context, attrs, defStyle);
/* 38 */            this.mPaint = init();
                }

/* 41 */        private Paint init() {
/* 42 */            Paint paint = new Paint();
/* 43 */            paint.setStyle(Paint.Style.FILL);
/* 44 */            this.mPrimaryColor = DEFAULT_PRIMARY_COLOR != -1 ? getResources().getColor(DEFAULT_PRIMARY_COLOR) : -16777216;
/* 46 */            return paint;
                }

/* 49 */        public void setColor(int color) {
/* 50 */            this.mPrimaryColor = color;
                }

/* 53 */        public void setSelectedColor(int color) {
/* 54 */            this.mSelectedColor = color;
                }

/* 57 */        public void setSelectedSection(float start, float end) {
                    if (this.mSelectedEndRatio < this.mSelectedStartRatio) {
/* 59 */                throw new IllegalArgumentException("End " + end + " < Start " + start);
                    }
/* 61 */            this.mSelectedStartRatio = start;
/* 62 */            this.mSelectedEndRatio = end;
/* 63 */            postInvalidate();
                }

                @Override // android.view.View
/* 67 */        protected void onDraw(Canvas canvas) {
                    if (this.mProgressRatio > 0.0f) {
                        if (this.mSelectedStartRatio > 0.0f) {
/* 70 */                    drawProgress(canvas, 0.0f, this.mSelectedStartRatio, this.mPrimaryColor);
/* 71 */                    drawProgress(canvas, this.mSelectedStartRatio, this.mSelectedEndRatio, this.mSelectedColor);
                            if (this.mSelectedEndRatio < this.mProgressRatio) {
/* 73 */                        drawProgress(canvas, this.mSelectedEndRatio, this.mProgressRatio, this.mPrimaryColor);
/* 86 */                        return;
                            }
/* 86 */                    return;
                        }
                        if (this.mSelectedStartRatio == 0.0f && this.mSelectedEndRatio != 0.0f) {
/* 77 */                    drawProgress(canvas, 0.0f, this.mSelectedEndRatio, this.mSelectedColor);
                            if (this.mSelectedEndRatio < this.mProgressRatio) {
/* 79 */                        drawProgress(canvas, this.mSelectedEndRatio, this.mProgressRatio, this.mPrimaryColor);
/* 86 */                        return;
                            }
/* 86 */                    return;
                        }
/* 82 */                drawProgress(canvas, 0.0f, this.mProgressRatio, this.mPrimaryColor);
                    }
                }

/* 90 */        public void setProgressRatio(float ratio) {
                    if (ratio > 1.0f) {
                        ratio = 1.0f;
                    }
/* 92 */            this.mProgressRatio = ratio;
/* 93 */            postInvalidate();
                }

/* 96 */        public float getProgressRatio() {
/* 97 */            return this.mProgressRatio;
                }
            }
