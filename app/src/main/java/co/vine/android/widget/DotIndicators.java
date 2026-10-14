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
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 39 */    public class DotIndicators extends View {
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

/* 40 */        public DotIndicators(Context context) {
/* 41 */            this(context, null);
                }

/* 44 */        public DotIndicators(Context context, AttributeSet attrs) {
/* 45 */            this(context, attrs, 0);
                }

/* 58 */        public DotIndicators(Context context, AttributeSet attrs, int defStyle) {
/* 59 */            super(context, attrs, defStyle);
/* 61 */            TypedArray a = context.obtainStyledAttributes(attrs, C0257R.styleable.DotIndicators, defStyle, 0);
/* 64 */            int offRes = a.getResourceId(1, C0257R.drawable.pagination_sessions_default);
/* 67 */            int onRes = a.getResourceId(0, C0257R.drawable.pagination_sessions_on);
/* 70 */            this.mOff = BitmapFactory.decodeResource(context.getResources(), offRes);
/* 71 */            this.mOn = BitmapFactory.decodeResource(context.getResources(), onRes);
/* 73 */            int offAlpha = a.getInt(2, 100);
/* 74 */            if (offAlpha != 100) {
/* 75 */                this.mOffAlpha = true;
/* 76 */                this.mOffAlphaValue = offAlpha;
                    }
/* 79 */            this.mFinalIconOn = BitmapFactory.decodeResource(context.getResources(), C0257R.drawable.pagination_sessions_capture_on);
/* 81 */            this.mFinalIconOff = BitmapFactory.decodeResource(context.getResources(), C0257R.drawable.pagination_sessions_capture_default);
/* 84 */            this.mDotPadding = a.getDimensionPixelOffset(3, 0);
/* 86 */            this.mFinalIconWidth = this.mFinalIconOff.getWidth();
/* 87 */            this.mDotWidth = this.mOn.getWidth();
/* 88 */            this.mDotHeight = this.mOn.getHeight();
/* 89 */            this.mShowFinalIcon = true;
/* 90 */            this.mPaint = new Paint();
/* 91 */            a.recycle();
                }

                @Override // android.view.View
/* 95 */        protected void onDraw(Canvas canvas) {
                    int left;
/* 96 */            super.onDraw(canvas);
/* 98 */            if (this.mHeight != 0 && this.mWidth != 0 && this.mNum != 0) {
               int totalWidth = (this.mNum * this.mDotWidth) + (Math.max(0, this.mNum - 1) * this.mDotPadding);
               if (this.mShowFinalIcon) {
                   totalWidth += this.mDotPadding + this.mFinalIconWidth;
               }
               left = Math.max(0, (this.mWidth - totalWidth) / 2);
/* 122 */               int top = (this.mHeight - this.mDotHeight) / 2;
               // Profile dots use assets with inconsistent transparent padding
               // across density buckets. Draw them as uniform circles so the
               // active and inactive indicators share the same size and baseline.
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
/* 125 */                   if (this.mActivePos == i) {
/* 126 */                       canvas.drawBitmap(this.mOn, left, top, (Paint) null);
                            } else if (this.mOffAlpha) {
/* 129 */                       this.mPaint.setAlpha(this.mOffAlphaValue);
/* 130 */                       canvas.drawBitmap(this.mOn, left, top, this.mPaint);
                            } else {
/* 132 */                       canvas.drawBitmap(this.mOff, left, top, (Paint) null);
                            }
/* 135 */                   left += this.mDotWidth + this.mDotPadding;
                        }
/* 139 */               if (this.mShowFinalIcon) {
/* 140 */                   if (this.mActivePos == this.mNum) {
/* 141 */                       canvas.drawBitmap(this.mFinalIconOn, left, top, (Paint) null);
                            } else {
/* 143 */                       canvas.drawBitmap(this.mFinalIconOff, left, top, (Paint) null);
                            }
                        }
                    }
                }

                @Override // android.view.View
/* 149 */       protected void onSizeChanged(int w, int h, int oldw, int oldh) {
/* 150 */           super.onSizeChanged(w, h, oldw, oldh);
/* 151 */           this.mWidth = w;
/* 152 */           this.mHeight = h;
                }

/* 155 */       public void setNumberOfDots(int num) {
/* 156 */                       this.mNum = num;
                    invalidate();
                }

                /** Direct setter so callers do not depend on styleable resolution. */
                public void setDotPadding(int paddingPx) {
                    this.mDotPadding = paddingPx;
                    invalidate();
                }

/* 159 */       public void setFinalIcon(boolean finalIcon) {
/* 160 */           this.mShowFinalIcon = finalIcon;
                }

/* 164 */       public void setActiveDot(int active) {
/* 165 */           this.mActivePos = active;
/* 166 */           invalidate();
                }
            }
