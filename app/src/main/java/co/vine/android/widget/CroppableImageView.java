            package co.vine.android.widget;

            import android.content.Context;
            import android.content.res.Resources;
            import android.content.res.TypedArray;
            import android.graphics.Canvas;
            import android.graphics.Matrix;
            import android.graphics.Paint;
            import android.graphics.RectF;
            import android.support.annotation.NonNull;
            import android.util.AttributeSet;
            import android.widget.ImageView;
            import co.vine.android.C0257R;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 45 */    public class CroppableImageView extends MultiTouchImageView {
                public static final String CROP_NAME_ORIGINAL_ASPECT_RATION = "original_aspect";
                public static final String CROP_NAME_SQUARE = "square_aspect";
                public static final int CROP_TYPE_EDIT_PROFILE = 0;
                public static final int CROP_TYPE_ORIGINAL_ASPECT_RATIO = 1;
                public static final int CROP_TYPE_SQUARE = 2;
                private int mCropRectPadding;
                private int mCropType;
                private Paint mShadowPaint;
                private RectF mShadowRect;
                private Paint mStrokePaint;

/* 46 */        private void init(int strokeColor, int shadowColor) {
/* 47 */            Paint strokePaint = new Paint();
/* 48 */            strokePaint.setColor(strokeColor);
/* 49 */            strokePaint.setStyle(Paint.Style.STROKE);
/* 50 */            this.mStrokePaint = strokePaint;
/* 52 */            Paint shadowPaint = new Paint();
/* 53 */            shadowPaint.setColor(shadowColor);
/* 54 */            this.mShadowPaint = shadowPaint;
/* 55 */            this.mShadowRect = new RectF();
/* 56 */            this.mCropType = 0;
                }

/* 59 */        public CroppableImageView(Context context) {
/* 60 */            super(context);
/* 61 */            Resources res = getResources();
/* 62 */            init(res.getColor(C0257R.color.solid_white), res.getColor(C0257R.color.translucent_black));
                }

/* 65 */        public CroppableImageView(Context context, AttributeSet attrs) {
/* 66 */            this(context, attrs, C0257R.attr.croppableImageViewStyle);
                }

/* 69 */        public CroppableImageView(Context context, AttributeSet attrs, int defStyle) {
/* 70 */            super(context, attrs, defStyle);
/* 72 */            TypedArray a = context.obtainStyledAttributes(attrs, C0257R.styleable.CroppableImageView, defStyle, 0);
/* 74 */            int cropRectPadding = a.getDimensionPixelSize(0, 0);
/* 76 */            if (cropRectPadding > 0) {
/* 77 */                this.mCropRectPadding = cropRectPadding;
                    }
/* 80 */            Resources res = getResources();
/* 81 */            int cropRectStrokeColor = a.getColor(1, res.getColor(C0257R.color.solid_white));
/* 84 */            int shadowColor = a.getColor(2, res.getColor(C0257R.color.translucent_black));
/* 86 */            a.recycle();
/* 88 */            init(cropRectStrokeColor, shadowColor);
                }

/* 91 */        public void setCropType(int type) {
/* 92 */            if (this.mCropType != type) {
/* 93 */                this.mCropType = type;
/* 94 */                invalidate();
                    }
                }

/* 98 */        public void setCropPadding(int padding, int type) {
                    int rectWidth;
                    int rectHeight;
/* 99 */            this.mCropType = type;
/* 100 */           this.mCropRectPadding = padding;
/* 101 */           int width = getMeasuredWidth();
/* 102 */           int height = getMeasuredHeight();
/* 105 */           float imageAr = this.mBitmapWidth / this.mBitmapHeight;
/* 106 */           float viewAr = width / height;
                    if (viewAr > imageAr) {
/* 111 */               rectHeight = height - ((int) (padding / imageAr));
/* 112 */               float scale = this.mBitmapHeight / height;
/* 113 */               rectWidth = ((int) (this.mBitmapWidth / scale)) - padding;
                    } else {
/* 115 */               rectWidth = width - padding;
/* 116 */               float scale2 = this.mBitmapWidth / width;
/* 117 */               rectHeight = ((int) (this.mBitmapHeight / scale2)) - ((int) (padding / imageAr));
                    }
/* 121 */           if (type == 0) {
/* 122 */               rectHeight = rectWidth / 2;
                    } else if (type == 2) {
/* 124 */               if (rectHeight < rectWidth) {
/* 125 */                   rectWidth = rectHeight;
                        } else {
/* 127 */                   rectHeight = rectWidth;
                        }
                    }
/* 131 */           setTransformDimensions(rectWidth, rectHeight, ImageView.ScaleType.CENTER_CROP, false);
                }

                @Override // android.widget.ImageView, android.view.View
/* 135 */       protected void onDraw(@NonNull Canvas canvas) {
/* 136 */           super.onDraw(canvas);
/* 137 */           RectF rect = this.mTransformBounds;
/* 138 */           canvas.drawRect(rect, this.mStrokePaint);
/* 140 */           int width = canvas.getWidth();
/* 141 */           int height = canvas.getHeight();
/* 143 */           Paint shadowPaint = this.mShadowPaint;
/* 144 */           RectF shadowRect = this.mShadowRect;
/* 147 */           shadowRect.set(0.0f, rect.top, rect.left, rect.bottom);
/* 148 */           canvas.drawRect(shadowRect, shadowPaint);
/* 151 */           shadowRect.set(0.0f, 0.0f, width, rect.top);
/* 152 */           canvas.drawRect(shadowRect, shadowPaint);
/* 155 */           shadowRect.set(rect.right, rect.top, width, rect.bottom);
/* 156 */           canvas.drawRect(shadowRect, shadowPaint);
/* 159 */           shadowRect.set(0.0f, rect.bottom, width, height);
/* 160 */           canvas.drawRect(shadowRect, shadowPaint);
                }

                @Override // android.widget.ImageView, android.view.View
/* 164 */       protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
/* 165 */           super.onMeasure(widthMeasureSpec, heightMeasureSpec);
                }

                @Override // co.vine.android.widget.MultiTouchImageView, android.view.View
/* 169 */       protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
/* 170 */           super.onLayout(changed, left, top, right, bottom);
/* 171 */           setCropPadding(this.mCropRectPadding, this.mCropType);
                }

/* 174 */       public RectF getCropRect() {
/* 175 */           RectF rect = new RectF(this.mTransformBounds);
/* 176 */           Matrix invertedMatrix = new Matrix();
/* 177 */           this.mMatrix.invert(invertedMatrix);
/* 178 */           invertedMatrix.mapRect(rect);
/* 179 */           return rect;
                }
            }
