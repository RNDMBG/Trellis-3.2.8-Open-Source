            package co.vine.android.recorder;

            import android.content.Context;
            import android.graphics.Canvas;
            import android.util.AttributeSet;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 11 */    public class RegularProgressView extends ProgressView {
/* 12 */        public RegularProgressView(Context context) {
/* 13 */            super(context);
                }

/* 16 */        public RegularProgressView(Context context, AttributeSet attrs) {
/* 17 */            super(context, attrs);
                }

/* 20 */        public RegularProgressView(Context context, AttributeSet attrs, int defStyle) {
/* 21 */            super(context, attrs, defStyle);
                }

                @Override // co.vine.android.recorder.ProgressView
/* 25 */        protected void drawProgress(Canvas canvas, float start, float end, int color) {
/* 26 */            this.mPaint.setColor(color);
/* 27 */            int width = getMeasuredWidth();
/* 28 */            canvas.drawRect(start * width, 0.0f, end * width, getMeasuredHeight(), this.mPaint);
                }
            }
