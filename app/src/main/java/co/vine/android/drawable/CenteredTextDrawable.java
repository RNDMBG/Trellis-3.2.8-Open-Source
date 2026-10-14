            package co.vine.android.drawable;

            import android.content.res.Resources;
            import android.graphics.Canvas;
            import android.graphics.ColorFilter;
            import android.graphics.Paint;
            import android.graphics.Rect;
            import android.graphics.Typeface;
            import android.graphics.drawable.Drawable;
            import android.support.annotation.NonNull;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 34 */    public class CenteredTextDrawable extends Drawable {
                private final Rect mDrawableBounds;
                private Drawable[] mDrawables;
                private int mDrawablesMinHeight;
                private int mDrawablesMinWidth;
                private final Paint mPaint;
                private String mText;
                private final Rect mTextBounds;
                private int mTextMinHeight;
                private int mTextMinWidth;
                private final Rect mTextPadding;

/* 35 */        public CenteredTextDrawable(@NonNull Resources res, @NonNull int... drawableIds) {
/* 36 */            this(getDrawablesFromIds(res, drawableIds));
                }

/* 38 */        public CenteredTextDrawable(@NonNull Drawable... drawables) {
/* 40 */            setDrawables(drawables);
/* 41 */            this.mPaint = init();
/* 42 */            this.mTextBounds = new Rect();
/* 43 */            this.mTextPadding = new Rect();
/* 44 */            this.mDrawableBounds = new Rect();
                }

/* 47 */        public void setDrawables(@NonNull Drawable... drawables) {
/* 48 */            this.mDrawables = drawables;
/* 49 */            int height = 0;
                    int width = 0;
/* 50 */            Drawable[] arr$ = this.mDrawables;
                    for (Drawable drawable : arr$) {
/* 51 */                height = Math.max(height, drawable.getMinimumHeight());
/* 52 */                width = Math.max(width, drawable.getMinimumWidth());
                    }
/* 54 */            this.mDrawablesMinHeight = height;
/* 55 */            this.mDrawablesMinWidth = width;
                }

/* 58 */        private static Drawable[] getDrawablesFromIds(Resources res, int... drawableIds) {
/* 59 */            Drawable[] drawables = new Drawable[drawableIds.length];
/* 61 */            int len$ = drawableIds.length;
                    int i$ = 0;
/* 60 */            int i = 0;
                    while (i$ < len$) {
/* 61 */                int drawableId = drawableIds[i$];
                        drawables[i] = res.getDrawable(drawableId);
/* 61 */                i$++;
/* 62 */                i++;
                    }
/* 64 */            return drawables;
                }

/* 67 */        public void setColorFilter(int index, ColorFilter cf) {
/* 68 */            this.mDrawables[index].setColorFilter(cf);
                }

/* 71 */        public void setText(String text) {
/* 72 */            this.mText = text;
/* 73 */            updateTextBounds();
/* 74 */            invalidateSelf();
                }

/* 77 */        public void setTextPadding(Rect rect) {
/* 78 */            this.mTextPadding.left = rect.left;
/* 79 */            this.mTextPadding.right = rect.right;
/* 80 */            this.mTextPadding.top = rect.top;
/* 81 */            this.mTextPadding.bottom = rect.bottom;
                }

/* 84 */        public void setTextColor(int color) {
/* 85 */            this.mPaint.setColor(color);
/* 86 */            invalidateSelf();
                }

/* 89 */        private Paint init() {
/* 90 */            Paint paint = new Paint(65);
/* 91 */            paint.setTextSize(32.0f);
/* 92 */            paint.setColor(-16777216);
/* 93 */            paint.setStyle(Paint.Style.FILL);
/* 94 */            paint.setTextAlign(Paint.Align.CENTER);
/* 95 */            return paint;
                }

/* 98 */        public void setTypeFace(Typeface typeFace) {
/* 99 */            this.mPaint.setTypeface(typeFace);
/* 100 */           updateTextBounds();
/* 101 */           invalidateSelf();
                }

/* 104 */       public void setTextSize(float textSize) {
/* 105 */           this.mPaint.setTextSize(textSize);
/* 106 */           updateTextBounds();
/* 107 */           invalidateSelf();
                }

/* 110 */       private void updateTextBounds() {
/* 111 */           String text = this.mText == null ? "" : this.mText;
/* 112 */           this.mPaint.getTextBounds(text, 0, text.length(), this.mTextBounds);
/* 113 */           int height = this.mTextBounds.height() + this.mTextPadding.top + this.mTextPadding.bottom;
/* 114 */           this.mTextMinHeight = Math.max(height, this.mDrawablesMinHeight);
/* 115 */           int width = this.mTextBounds.width() + this.mTextPadding.left + this.mTextPadding.right;
/* 116 */           this.mTextMinWidth = Math.max(width, this.mDrawablesMinWidth);
                }

                @Override // android.graphics.drawable.Drawable
/* 120 */       public int getIntrinsicHeight() {
/* 121 */           return this.mTextMinHeight;
                }

                @Override // android.graphics.drawable.Drawable
/* 125 */       public int getIntrinsicWidth() {
/* 126 */           return this.mTextMinWidth;
                }

                @Override // android.graphics.drawable.Drawable
/* 130 */       public void draw(Canvas canvas) {
/* 131 */           this.mDrawableBounds.set(getBounds());
/* 132 */           Drawable[] arr$ = this.mDrawables;
                    for (Drawable drawable : arr$) {
/* 133 */               drawable.setBounds(this.mDrawableBounds);
/* 134 */               drawable.draw(canvas);
                    }
                    this.mDrawableBounds.left += this.mTextPadding.left;
                    this.mDrawableBounds.top += this.mTextPadding.top;
                    this.mDrawableBounds.bottom -= this.mTextPadding.bottom;
                    this.mDrawableBounds.right -= this.mTextPadding.right;
/* 141 */           canvas.drawText(this.mText, this.mDrawableBounds.exactCenterX(), this.mDrawableBounds.exactCenterY() + (this.mTextBounds.height() * 0.5f), this.mPaint);
                }

                @Override // android.graphics.drawable.Drawable
/* 145 */       public void setAlpha(int alpha) {
/* 146 */           this.mPaint.setAlpha(alpha);
                }

                @Override // android.graphics.drawable.Drawable
/* 150 */       public void setColorFilter(ColorFilter cf) {
/* 151 */           this.mPaint.setColorFilter(cf);
                }

                @Override // android.graphics.drawable.Drawable
/* 155 */       public int getOpacity() {
                    return this.mPaint.getAlpha() < 255 ? -3 : -1;
                }
            }
