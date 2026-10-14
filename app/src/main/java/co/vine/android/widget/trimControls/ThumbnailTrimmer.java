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
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 45 */    public class ThumbnailTrimmer extends View {
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

/* 46 */        public ThumbnailTrimmer(Context context, int paddingLeft) {
/* 47 */            super(context);
/* 48 */            init(paddingLeft);
                }

/* 51 */        private void init(int paddingLeft) {
/* 52 */            this.mTrimScrubber = (NinePatchDrawable) getResources().getDrawable(C0257R.drawable.scrub_right);
/* 53 */            this.scrubberDrawableWidth = this.mTrimScrubber.getMinimumWidth();
/* 54 */            this.mScrubberPadding = getResources().getDimensionPixelSize(C0257R.dimen.scrubber_padding);
/* 55 */            this.mCarouselPadding = getResources().getDimensionPixelSize(C0257R.dimen.carousel_padding);
/* 57 */            this.mScrubberRect = new Rect();
/* 58 */            this.mScrubberPaint = new Paint();
/* 59 */            this.mScrubberPaint.setColor(0);
/* 61 */            this.mStrokeWidth = getResources().getDimensionPixelOffset(C0257R.dimen.trimmer_stroke_width);
/* 62 */            this.mOutlineRect = new Rect();
/* 63 */            this.mOutlinePaint = new Paint();
/* 64 */            this.mOutlinePaint.setStyle(Paint.Style.STROKE);
/* 65 */            this.mOutlinePaint.setColor(getResources().getColor(C0257R.color.trim_yellow));
/* 66 */            this.mOutlinePaint.setStrokeWidth(this.mStrokeWidth);
/* 68 */            this.mDeselectedRect = new Rect();
/* 69 */            this.mFrontDeselectedRect = new Rect();
/* 70 */            this.mDeselectedPaint = new Paint();
/* 71 */            this.mDeselectedPaint.setColor(-1879048192);
/* 73 */            setPadding(paddingLeft, 0, 0, 0);
/* 74 */            this.mPaddingLeft = paddingLeft;
                }

/* 77 */        public void setListener(OnScrubberMovedListener listener) {
/* 78 */            this.mListener = listener;
                }

                @Override // android.view.View
/* 82 */        protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
/* 83 */            if (changed) {
/* 84 */                int paddedTop = top + this.mCarouselPadding;
/* 85 */                int paddedBottom = bottom - this.mCarouselPadding;
/* 86 */                int paddedLeft = this.mPaddingLeft + left + (this.mStrokeWidth / 2);
/* 87 */                this.mContainerRight = right;
/* 88 */                this.mScrubberRect.set(right - this.scrubberDrawableWidth, top, right, bottom);
/* 89 */                this.mOutlineRect.set(paddedLeft, paddedTop, right, paddedBottom);
/* 90 */                this.mDeselectedRect.set(right, paddedTop, right, paddedBottom);
/* 91 */                this.mFrontDeselectedRect.set(0, paddedTop, paddedLeft - (this.mStrokeWidth / 2), paddedBottom);
/* 92 */                updateOutlineWidth();
                    }
                }

/* 96 */        public void setDeselectedBounds(int leftPx, int rightPx) {
/* 97 */            this.mFrontDeselectedRect.left = leftPx;
/* 98 */            this.mDeselectedRect.right = rightPx;
/* 99 */            invalidate();
                }

                @Override // android.view.View
/* 103 */       protected void onDraw(Canvas canvas) {
/* 104 */           canvas.drawRect(this.mScrubberRect, this.mScrubberPaint);
/* 105 */           canvas.drawRect(this.mOutlineRect, this.mOutlinePaint);
/* 106 */           canvas.drawRect(this.mDeselectedRect, this.mDeselectedPaint);
/* 107 */           canvas.drawRect(this.mFrontDeselectedRect, this.mDeselectedPaint);
/* 109 */           NinePatchDrawable trimScrubber = this.mTrimScrubber;
/* 110 */           if (trimScrubber != null) {
/* 111 */               trimScrubber.setBounds(this.mScrubberRect);
/* 112 */               trimScrubber.draw(canvas);
                    }
                }

                @Override // android.view.View
                @SuppressLint({"ClickableViewAccessibility"})
/* 118 */       public boolean onTouchEvent(@NonNull MotionEvent event) {
/* 119 */           int x = (int) event.getX();
/* 120 */           int y = (int) event.getY();
                    switch (event.getAction()) {
                        case 0:
/* 124 */                   if (inScrubber(x, y)) {
/* 125 */                       this.mScrubbing = true;
/* 126 */                       this.mScrubberOffset = x - this.mScrubberRect.left;
                                return true;
                            }
                        case 2:
/* 131 */                   int lowerBound = this.mPaddingLeft;
/* 132 */                   int upperBound = this.mContainerRight - this.scrubberDrawableWidth;
/* 133 */                   if (this.mScrubbing && x - this.mScrubberOffset <= upperBound && x - this.mScrubberOffset >= lowerBound) {
/* 134 */                       this.mScrubberRect.left = x - this.mScrubberOffset;
/* 135 */                       this.mScrubberRect.right = (x - this.mScrubberOffset) + this.scrubberDrawableWidth;
/* 136 */                       this.mOutlineRect.right = (x - this.mScrubberOffset) + (this.scrubberDrawableWidth / 2);
/* 137 */                       this.mDeselectedRect.left = this.mOutlineRect.right;
/* 138 */                       updateOutlineWidth();
/* 139 */                       this.mListener.onScrubberMoved(this.mOutlineRect.right, getWidth());
/* 140 */                       invalidate();
                                return true;
                            }
                        case 1:
                        case 3:
/* 146 */                   if (this.mScrubbing) {
/* 147 */                       this.mScrubbing = false;
                                return true;
                            }
                        default:
                            return false;
                    }
                }

/* 156 */       private void updateOutlineWidth() {
/* 157 */           this.outlineRectWidth = this.mOutlineRect.width();
                }

/* 160 */       private boolean inScrubber(int touchX, int touchY) {
                    return touchX < this.mScrubberRect.right + this.mScrubberPadding && touchX > this.mScrubberRect.left - this.mScrubberPadding && touchY < this.mScrubberRect.bottom + this.mScrubberPadding && touchY > this.mScrubberRect.top - this.mScrubberPadding;
                }
            }
