            package co.vine.android.widget.trimControls;

            import android.content.Context;
            import android.content.res.Resources;
            import android.graphics.Canvas;
            import android.graphics.Paint;
            import android.graphics.Rect;
            import android.graphics.RectF;
            import android.view.MotionEvent;
            import android.view.View;
            import co.vine.android.C0257R;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 36 */    public class GripTrack extends View {
                private Paint mGripBgPaint;
                private RectF mGripHandle;
                private final int mGripHandleHeight;
                private Paint mGripHandlePaint;
                private final int mGripHandleWidth;
                private GripTrackTouchListener mGripTrackTouchListener;
                private Rect mLeftRect;
                private Rect mRightRect;

                public interface GripTrackTouchListener {
                    boolean onGripTouchEvent(MotionEvent motionEvent);
                }

/* 37 */        public GripTrack(Context context, GripTrackTouchListener listener) {
/* 38 */            super(context);
/* 22 */            this.mGripBgPaint = new Paint();
/* 23 */            this.mGripHandlePaint = new Paint();
/* 25 */            this.mLeftRect = new Rect();
/* 26 */            this.mRightRect = new Rect();
/* 27 */            this.mGripHandle = new RectF();
/* 39 */            Resources res = getResources();
/* 40 */            this.mGripBgPaint.setColor(res.getColor(C0257R.color.gripTrackBackground));
/* 41 */            this.mGripHandlePaint.setColor(res.getColor(C0257R.color.white_forty_percent));
/* 42 */            this.mGripHandleWidth = res.getDimensionPixelSize(C0257R.dimen.grip_handle_width);
/* 43 */            this.mGripHandleHeight = res.getDimensionPixelSize(C0257R.dimen.grip_handle_height);
/* 44 */            this.mGripTrackTouchListener = listener;
                }

/* 47 */        public void setBoundaries(int leftPx, int rightPx) {
/* 48 */            this.mLeftRect.left = 0;
/* 49 */            this.mLeftRect.right = leftPx;
/* 50 */            this.mLeftRect.top = 0;
/* 51 */            this.mLeftRect.bottom = getBottom();
/* 53 */            this.mRightRect.left = rightPx;
/* 54 */            this.mRightRect.right = getWidth();
/* 55 */            this.mRightRect.top = 0;
/* 56 */            this.mRightRect.bottom = getBottom();
/* 58 */            invalidate();
                }

                @Override // android.view.View
/* 61 */        public boolean onTouchEvent(MotionEvent event) {
/* 62 */            if (this.mRightRect.contains((int) event.getX(), (int) event.getY())) {
/* 63 */                return this.mGripTrackTouchListener.onGripTouchEvent(event);
                    }
                    return false;
                }

                @Override // android.view.View
/* 69 */        protected void onDraw(Canvas canvas) {
/* 70 */            super.onDraw(canvas);
/* 73 */            canvas.drawRect(this.mLeftRect, this.mGripBgPaint);
/* 74 */            canvas.drawRect(this.mRightRect, this.mGripBgPaint);
/* 77 */            int gripHandleTop = (getHeight() - this.mGripHandleHeight) / 2;
/* 78 */            this.mGripHandle.set(this.mLeftRect.right - (this.mGripHandleWidth * 5), gripHandleTop, this.mLeftRect.right - (this.mGripHandleWidth * 4), this.mGripHandleHeight + gripHandleTop);
/* 80 */            canvas.drawRoundRect(this.mGripHandle, this.mGripHandleWidth / 2, this.mGripHandleWidth / 2, this.mGripHandlePaint);
/* 81 */            this.mGripHandle.offset(this.mGripHandleWidth * 2, 0.0f);
/* 82 */            canvas.drawRoundRect(this.mGripHandle, this.mGripHandleWidth / 2, this.mGripHandleWidth / 2, this.mGripHandlePaint);
                }
            }
