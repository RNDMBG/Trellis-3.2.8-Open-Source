            package co.vine.android.widget;

            import android.content.Context;
            import android.content.res.TypedArray;
            import android.graphics.Bitmap;
            import android.graphics.BitmapShader;
            import android.graphics.Canvas;
            import android.graphics.Paint;
            import android.graphics.PorterDuff;
            import android.graphics.RectF;
            import android.graphics.Shader;
            import android.graphics.drawable.BitmapDrawable;
            import android.graphics.drawable.Drawable;
            import android.support.annotation.NonNull;
            import android.util.AttributeSet;
            import android.widget.ImageView;
            import co.vine.android.C0257R;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 28 */    public class RoundedCornerBitmapImageView extends ImageView {
                public Bitmap mCanvasBitmap;
                public int mRadius;

/* 29 */        public RoundedCornerBitmapImageView(Context context) {
/* 30 */            super(context);
                }

/* 33 */        public RoundedCornerBitmapImageView(Context context, AttributeSet attrs) {
/* 34 */            this(context, attrs, 0);
                }

/* 37 */        public RoundedCornerBitmapImageView(Context context, AttributeSet attrs, int defStyle) {
/* 38 */            super(context, attrs, defStyle);
/* 39 */            TypedArray a = context.obtainStyledAttributes(attrs, C0257R.styleable.RoundedCornerImageView, defStyle, 0);
/* 41 */            this.mRadius = a.getDimensionPixelSize(0, 0);
                }

                @Override // android.view.View
/* 44 */        public void draw(@NonNull Canvas c) {
/* 45 */            Drawable d = getDrawable();
/* 46 */            if (d instanceof BitmapDrawable) {
/* 47 */                Bitmap bitmap = ((BitmapDrawable) d).getBitmap();
/* 48 */                int width = getMeasuredWidth();
/* 49 */                int height = getMeasuredHeight();
/* 51 */                if (this.mCanvasBitmap == null || width != this.mCanvasBitmap.getWidth() || height != this.mCanvasBitmap.getHeight()) {
/* 52 */                    if (this.mCanvasBitmap != null) {
/* 53 */                        this.mCanvasBitmap.recycle();
                            }
/* 56 */                    this.mCanvasBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
                        }
/* 59 */                Canvas bitmapCanvas = new Canvas(this.mCanvasBitmap);
/* 60 */                bitmapCanvas.drawColor(0, PorterDuff.Mode.CLEAR);
/* 62 */                super.draw(bitmapCanvas);
/* 64 */                if (bitmap != null) {
/* 66 */                    BitmapShader shader = new BitmapShader(this.mCanvasBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
/* 68 */                    Paint paint = new Paint();
/* 69 */                    paint.setAntiAlias(true);
/* 70 */                    paint.setShader(shader);
/* 72 */                    RectF rect = new RectF(0.0f, 0.0f, width, height);
/* 74 */                    c.drawRoundRect(rect, this.mRadius, this.mRadius, paint);
/* 81 */                    return;
                        }
/* 76 */                super.draw(c);
/* 81 */                return;
                    }
/* 79 */            super.draw(c);
                }
            }
