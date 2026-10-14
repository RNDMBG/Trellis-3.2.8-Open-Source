            package co.vine.android.recorder;

            import android.content.Context;
            import android.graphics.Canvas;
            import android.graphics.RectF;
            import android.util.AttributeSet;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 14 */    public class InlineProgressView extends ProgressView {
                private final RectF mRect;

/* 15 */        public InlineProgressView(Context context) {
/* 16 */            super(context);
/* 13 */            this.mRect = new RectF(0.0f, 0.0f, 0.0f, 0.0f);
                }

/* 19 */        public InlineProgressView(Context context, AttributeSet attrs) {
/* 20 */            super(context, attrs);
/* 13 */            this.mRect = new RectF(0.0f, 0.0f, 0.0f, 0.0f);
                }

/* 23 */        public InlineProgressView(Context context, AttributeSet attrs, int defStyle) {
/* 24 */            super(context, attrs, defStyle);
/* 13 */            this.mRect = new RectF(0.0f, 0.0f, 0.0f, 0.0f);
                }

                @Override // co.vine.android.recorder.ProgressView
/* 28 */        protected void drawProgress(Canvas canvas, float start, float end, int color) {
/* 29 */            this.mPaint.setColor(color);
/* 30 */            int height = getMeasuredHeight();
/* 31 */            int width = getMeasuredWidth();
/* 32 */            int radius = height / 2;
/* 33 */            this.mRect.left = width * start;
/* 34 */            this.mRect.right = width * end;
/* 35 */            this.mRect.bottom = height;
/* 36 */            canvas.drawRoundRect(this.mRect, radius, radius, this.mPaint);
                }
            }
