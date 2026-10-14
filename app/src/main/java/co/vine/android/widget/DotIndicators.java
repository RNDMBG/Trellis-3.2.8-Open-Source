            package co.vine.android.widget;

            import android.content.Context;
            import android.content.res.TypedArray;
            import android.graphics.Bitmap;
            import android.graphics.BitmapFactory;
            import android.graphics.Canvas;
            import android.graphics.Paint;
            import android.util.AttributeSet;
            import android.view.View;
            import co.vine.android.C0257R;
            
            
    public class DotIndicators extends View {
                private int mActivePos;
                private int mDotHeight;
                private int mDotPadding;
                private int mDotWidth;
                private Bitmap mFinalIconOff;
                private Bitmap mFinalIconOn;
                private int mFinalIconWidth;
                private int mHeight;
                private int mNum;
                private Bitmap mOff;
                private boolean mOffAlpha;
                private int mOffAlphaValue;
                private Bitmap mOn;
                private Paint mPaint;
                private boolean mShowFinalIcon;
                private int mWidth;

        public DotIndicators(Context context) {
            this(context, null);
                }

        public DotIndicators(Context context, AttributeSet attrs) {
            this(context, attrs, 0);
                }

        public DotIndicators(Context context, AttributeSet attrs, int defStyle) {
            super(context, attrs, defStyle);
            TypedArray a = context.obtainStyledAttributes(attrs, C0257R.styleable.DotIndicators, defStyle, 0);
            int offRes = a.getResourceId(1, C0257R.drawable.pagination_sessions_default);
            int onRes = a.getResourceId(0, C0257R.drawable.pagination_sessions_on);
            this.mOff = BitmapFactory.decodeResource(context.getResources(), offRes);
            this.mOn = BitmapFactory.decodeResource(context.getResources(), onRes);
            int offAlpha = a.getInt(2, 100);
            if (offAlpha != 100) {
                this.mOffAlpha = true;
                this.mOffAlphaValue = offAlpha;
                    }
            this.mFinalIconOn = BitmapFactory.decodeResource(context.getResources(), C0257R.drawable.pagination_sessions_capture_on);
            this.mFinalIconOff = BitmapFactory.decodeResource(context.getResources(), C0257R.drawable.pagination_sessions_capture_default);
            this.mDotPadding = a.getDimensionPixelOffset(3, 0);
            this.mFinalIconWidth = this.mFinalIconOff.getWidth();
            this.mDotWidth = this.mOn.getWidth();
            this.mDotHeight = this.mOn.getHeight();
            this.mShowFinalIcon = true;
            this.mPaint = new Paint();
            a.recycle();
                }

                @Override 
        protected void onDraw(Canvas canvas) {
                    int left;
            super.onDraw(canvas);
            if (this.mHeight != 0 && this.mWidth != 0 && this.mNum != 0) {
               int totalWidth = (this.mNum * this.mDotWidth) + (Math.max(0, this.mNum - 1) * this.mDotPadding);
               if (this.mShowFinalIcon) {
                   totalWidth += this.mDotPadding + this.mFinalIconWidth;
               }
               left = Math.max(0, (this.mWidth - totalWidth) / 2);
               int top = (this.mHeight - this.mDotHeight) / 2;
               
               
               
               if (!this.mShowFinalIcon) {
                   float radius = Math.min(this.mDotWidth, this.mDotHeight) / 2.0f;
                   float centerY = this.mHeight / 2.0f;
                   this.mPaint.setAntiAlias(true);
                   for (int i = 0; i < this.mNum; i++) {
                       this.mPaint.setColor(this.mActivePos == i ? 0xFFFFFFFF : 0xFF666666);
                       float centerX = left + (this.mDotWidth / 2.0f);
                       canvas.drawCircle(centerX, centerY, radius, this.mPaint);
                       left += this.mDotWidth + this.mDotPadding;
                   }
                   return;
               }
                        for (int i = 0; i < this.mNum; i++) {
                   if (this.mActivePos == i) {
                       canvas.drawBitmap(this.mOn, left, top, (Paint) null);
                            } else if (this.mOffAlpha) {
                       this.mPaint.setAlpha(this.mOffAlphaValue);
                       canvas.drawBitmap(this.mOn, left, top, this.mPaint);
                            } else {
                       canvas.drawBitmap(this.mOff, left, top, (Paint) null);
                            }
                   left += this.mDotWidth + this.mDotPadding;
                        }
               if (this.mShowFinalIcon) {
                   if (this.mActivePos == this.mNum) {
                       canvas.drawBitmap(this.mFinalIconOn, left, top, (Paint) null);
                            } else {
                       canvas.drawBitmap(this.mFinalIconOff, left, top, (Paint) null);
                            }
                        }
                    }
                }

                @Override 
       protected void onSizeChanged(int w, int h, int oldw, int oldh) {
           super.onSizeChanged(w, h, oldw, oldh);
           this.mWidth = w;
           this.mHeight = h;
                }

       public void setNumberOfDots(int num) {
                       this.mNum = num;
                    invalidate();
                }

                
                public void setDotPadding(int paddingPx) {
                    this.mDotPadding = paddingPx;
                    invalidate();
                }

       public void setFinalIcon(boolean finalIcon) {
           this.mShowFinalIcon = finalIcon;
                }

       public void setActiveDot(int active) {
           this.mActivePos = active;
           invalidate();
                }
            }
