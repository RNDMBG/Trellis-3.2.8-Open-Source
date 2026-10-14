            package co.vine.android.widget.trimControls;

            import android.annotation.SuppressLint;
            import android.content.Context;
            import android.graphics.Canvas;
            import android.graphics.Paint;
            import android.graphics.Rect;
            import android.graphics.drawable.NinePatchDrawable;
            import android.support.annotation.NonNull;
            import android.view.MotionEvent;
            import android.view.View;
            import co.vine.android.C0257R;
            
            
    public class ThumbnailTrimmer extends View {
                private int mCarouselPadding;
                private int mContainerRight;
                private Paint mDeselectedPaint;
                private Rect mDeselectedRect;
                private Rect mFrontDeselectedRect;
                private OnScrubberMovedListener mListener;
                private Paint mOutlinePaint;
                private Rect mOutlineRect;
                private int mPaddingLeft;
                private int mScrubberOffset;
                private int mScrubberPadding;
                private Paint mScrubberPaint;
                private Rect mScrubberRect;
                private boolean mScrubbing;
                private int mStrokeWidth;
                private NinePatchDrawable mTrimScrubber;
                public int outlineRectWidth;
                public int scrubberDrawableWidth;

                public interface OnScrubberMovedListener {
                    void onScrubberMoved(int i, int i2);
                }

        public ThumbnailTrimmer(Context context, int paddingLeft) {
            super(context);
            init(paddingLeft);
                }

        private void init(int paddingLeft) {
            this.mTrimScrubber = (NinePatchDrawable) getResources().getDrawable(C0257R.drawable.scrub_right);
            this.scrubberDrawableWidth = this.mTrimScrubber.getMinimumWidth();
            this.mScrubberPadding = getResources().getDimensionPixelSize(C0257R.dimen.scrubber_padding);
            this.mCarouselPadding = getResources().getDimensionPixelSize(C0257R.dimen.carousel_padding);
            this.mScrubberRect = new Rect();
            this.mScrubberPaint = new Paint();
            this.mScrubberPaint.setColor(0);
            this.mStrokeWidth = getResources().getDimensionPixelOffset(C0257R.dimen.trimmer_stroke_width);
            this.mOutlineRect = new Rect();
            this.mOutlinePaint = new Paint();
            this.mOutlinePaint.setStyle(Paint.Style.STROKE);
            this.mOutlinePaint.setColor(getResources().getColor(C0257R.color.trim_yellow));
            this.mOutlinePaint.setStrokeWidth(this.mStrokeWidth);
            this.mDeselectedRect = new Rect();
            this.mFrontDeselectedRect = new Rect();
            this.mDeselectedPaint = new Paint();
            this.mDeselectedPaint.setColor(-1879048192);
            setPadding(paddingLeft, 0, 0, 0);
            this.mPaddingLeft = paddingLeft;
                }

        public void setListener(OnScrubberMovedListener listener) {
            this.mListener = listener;
                }

                @Override 
        protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
            if (changed) {
                int paddedTop = top + this.mCarouselPadding;
                int paddedBottom = bottom - this.mCarouselPadding;
                int paddedLeft = this.mPaddingLeft + left + (this.mStrokeWidth / 2);
                this.mContainerRight = right;
                this.mScrubberRect.set(right - this.scrubberDrawableWidth, top, right, bottom);
                this.mOutlineRect.set(paddedLeft, paddedTop, right, paddedBottom);
                this.mDeselectedRect.set(right, paddedTop, right, paddedBottom);
                this.mFrontDeselectedRect.set(0, paddedTop, paddedLeft - (this.mStrokeWidth / 2), paddedBottom);
                updateOutlineWidth();
                    }
                }

        public void setDeselectedBounds(int leftPx, int rightPx) {
            this.mFrontDeselectedRect.left = leftPx;
            this.mDeselectedRect.right = rightPx;
            invalidate();
                }

                @Override 
       protected void onDraw(Canvas canvas) {
           canvas.drawRect(this.mScrubberRect, this.mScrubberPaint);
           canvas.drawRect(this.mOutlineRect, this.mOutlinePaint);
           canvas.drawRect(this.mDeselectedRect, this.mDeselectedPaint);
           canvas.drawRect(this.mFrontDeselectedRect, this.mDeselectedPaint);
           NinePatchDrawable trimScrubber = this.mTrimScrubber;
           if (trimScrubber != null) {
               trimScrubber.setBounds(this.mScrubberRect);
               trimScrubber.draw(canvas);
                    }
                }

                @Override 
                @SuppressLint({"ClickableViewAccessibility"})
       public boolean onTouchEvent(@NonNull MotionEvent event) {
           int x = (int) event.getX();
           int y = (int) event.getY();
                    switch (event.getAction()) {
                        case 0:
                   if (inScrubber(x, y)) {
                       this.mScrubbing = true;
                       this.mScrubberOffset = x - this.mScrubberRect.left;
                                return true;
                            }
                        case 2:
                   int lowerBound = this.mPaddingLeft;
                   int upperBound = this.mContainerRight - this.scrubberDrawableWidth;
                   if (this.mScrubbing && x - this.mScrubberOffset <= upperBound && x - this.mScrubberOffset >= lowerBound) {
                       this.mScrubberRect.left = x - this.mScrubberOffset;
                       this.mScrubberRect.right = (x - this.mScrubberOffset) + this.scrubberDrawableWidth;
                       this.mOutlineRect.right = (x - this.mScrubberOffset) + (this.scrubberDrawableWidth / 2);
                       this.mDeselectedRect.left = this.mOutlineRect.right;
                       updateOutlineWidth();
                       this.mListener.onScrubberMoved(this.mOutlineRect.right, getWidth());
                       invalidate();
                                return true;
                            }
                        case 1:
                        case 3:
                   if (this.mScrubbing) {
                       this.mScrubbing = false;
                                return true;
                            }
                        default:
                            return false;
                    }
                }

       private void updateOutlineWidth() {
           this.outlineRectWidth = this.mOutlineRect.width();
                }

       private boolean inScrubber(int touchX, int touchY) {
                    return touchX < this.mScrubberRect.right + this.mScrubberPadding && touchX > this.mScrubberRect.left - this.mScrubberPadding && touchY < this.mScrubberRect.bottom + this.mScrubberPadding && touchY > this.mScrubberRect.top - this.mScrubberPadding;
                }
            }
