            package co.vine.android.widget.tabs;

            import android.content.Context;
            import android.content.res.TypedArray;
            import android.graphics.Canvas;
            import android.graphics.Paint;
            import android.graphics.RectF;
            import android.util.AttributeSet;
            import android.util.FloatMath;
            import android.view.View;
            import co.vine.android.C0257R;
            import co.vine.android.VineLoggingException;
            import co.vine.android.util.CrashUtil;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 36 */    public class ViewPagerScrollBar extends View {
                private int[] mColorIds;
                private int mHeight;
                private boolean mNavBottomSet;
                private int mOffset;
                private int mOffsetPixels;
                private final Paint mPaint;
                private int mPosition;
                private int mRange;
                private final RectF mTabBounds;

/* 37 */        public ViewPagerScrollBar(Context context) {
/* 38 */            this(context, null, C0257R.attr.viewPagerScrollBarStyle);
                }

/* 41 */        public ViewPagerScrollBar(Context context, AttributeSet attrs) {
/* 42 */            this(context, attrs, C0257R.attr.viewPagerScrollBarStyle);
                }

/* 45 */        public ViewPagerScrollBar(Context context, AttributeSet attrs, int defStyle) {
/* 46 */            super(context, attrs, defStyle);
/* 29 */            this.mHeight = 0;
/* 31 */            this.mRange = 1;
/* 34 */            this.mOffset = 0;
/* 35 */            this.mNavBottomSet = false;
/* 47 */            this.mPaint = new Paint();
/* 48 */            this.mTabBounds = new RectF();
/* 49 */            TypedArray a = context.obtainStyledAttributes(attrs, C0257R.styleable.ViewPagerScrollBar, defStyle, 0);
/* 52 */            int colorArrayRes = a.getResourceId(2, 0);
/* 53 */            this.mHeight = a.getDimensionPixelSize(1, 0);
/* 54 */            if (colorArrayRes > 0) {
/* 55 */                TypedArray ta = context.getResources().obtainTypedArray(colorArrayRes);
/* 56 */                this.mColorIds = new int[ta.length()];
                        for (int i = 0; i < ta.length(); i++) {
/* 58 */                    this.mColorIds[i] = ta.getColor(i, 0);
                        }
/* 60 */                ta.recycle();
/* 61 */                this.mPaint.setColor(this.mColorIds[0]);
                    } else {
/* 64 */                this.mPaint.setColor(a.getColor(0, -1));
                    }
/* 67 */            a.recycle();
                }

                @Override // android.view.View
/* 71 */        protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
/* 72 */            super.onLayout(changed, left, top, right, bottom);
/* 73 */            if (!this.mNavBottomSet) {
/* 74 */                this.mOffset = getHeight();
                    }
/* 76 */            updateBounds();
                }

                @Override // android.view.View
/* 80 */        public void draw(Canvas canvas) {
/* 81 */            canvas.drawRect(this.mTabBounds, this.mPaint);
                }

/* 84 */        public void updateBounds() {
/* 85 */            float width = getWidth() / this.mRange;
/* 86 */            int v = this.mOffsetPixels;
/* 87 */            float w = width * this.mPosition;
/* 88 */            float left = v + w;
/* 89 */            this.mTabBounds.set(left, this.mOffset - this.mHeight, left + width, this.mOffset);
                }

/* 92 */        public void setPosition(int position) {
/* 93 */            this.mPosition = position;
/* 94 */            this.mOffsetPixels = 0;
/* 95 */            updateBounds();
/* 96 */            invalidate();
                }

/* 99 */        public void setNavOffset(int offset) {
/* 100 */           this.mOffset = offset;
/* 101 */           this.mNavBottomSet = true;
/* 102 */           updateBounds();
/* 103 */           invalidate();
                }

/* 106 */       public void setRange(int range) {
/* 107 */           this.mRange = range;
/* 108 */           if (this.mRange == 0) {
/* 109 */               this.mRange = 1;
/* 110 */               CrashUtil.logException(new VineLoggingException("Someone is trying to set the range to be 0, defaulting back to 1."));
                    }
/* 112 */           requestLayout();
/* 113 */           invalidate();
                }

/* 116 */       public void scroll(int position, int pixels) {
/* 117 */           this.mPosition = position;
/* 118 */           this.mOffsetPixels = (int) Math.ceil(pixels / this.mRange);
/* 119 */           updateBounds();
/* 120 */           invalidate();
                }

/* 124 */       public void setPageColor(int position) {
/* 125 */           if (this.mColorIds != null) {
/* 126 */               this.mPaint.setColor(this.mColorIds[position]);
                    }
                }

/* 130 */       public void setColorIds(int colorsResId) {
/* 131 */           this.mColorIds = getResources().getIntArray(colorsResId);
/* 132 */           this.mPaint.setColor(this.mColorIds[0]);
/* 133 */           requestLayout();
/* 134 */           invalidate();
                }

/* 137 */       public void setLineColor(int color) {
/* 138 */           this.mPaint.setColor(color);
                }
            }
