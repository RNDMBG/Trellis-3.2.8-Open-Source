            package co.vine.android.widget;

            import android.R;
            import android.content.Context;
            import android.content.res.TypedArray;
            import android.graphics.Bitmap;
            import android.graphics.BitmapFactory;
            import android.graphics.Canvas;
            import android.graphics.Paint;
            import android.graphics.PorterDuff;
            import android.graphics.PorterDuffXfermode;
            import android.support.annotation.NonNull;
            import android.util.AttributeSet;
            import android.widget.ImageView;
            import co.vine.android.C0257R;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 25 */    public class MaskedImageView extends ImageView {
                private Bitmap mMaskBitmap;
                private int mMaskResId;
                private Paint mPaint;

/* 26 */        public MaskedImageView(Context context) {
/* 27 */            super(context);
/* 28 */            init();
                }

/* 31 */        public MaskedImageView(Context context, AttributeSet attrs) {
/* 32 */            this(context, attrs, 0);
                }

/* 35 */        public MaskedImageView(Context context, AttributeSet attrs, int defStyle) {
/* 36 */            super(context, attrs, defStyle);
/* 37 */            TypedArray a = context.obtainStyledAttributes(attrs, C0257R.styleable.MaskedImageView, defStyle, 0);
/* 39 */            this.mMaskResId = a.getResourceId(0, 0);
/* 40 */            a.recycle();
/* 41 */            init();
                }

/* 44 */        private void init() {
/* 45 */            if (this.mMaskResId > 0) {
/* 46 */                this.mMaskBitmap = BitmapFactory.decodeResource(getResources(), this.mMaskResId);
/* 47 */                this.mPaint = new Paint();
/* 48 */                this.mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
                    }
/* 50 */            setBackgroundColor(getResources().getColor(R.color.transparent));
                }

                @Override // android.widget.ImageView, android.view.View
/* 54 */        protected void onDraw(@NonNull Canvas canvas) {
/* 55 */            super.onDraw(canvas);
/* 56 */            if (this.mMaskResId > 0) {
/* 57 */                canvas.drawBitmap(this.mMaskBitmap, 0.0f, 0.0f, this.mPaint);
                    }
                }
            }
