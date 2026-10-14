            package co.vine.android.drawable;

            import android.content.res.Resources;
            import android.graphics.Bitmap;
            import android.graphics.Canvas;
            import android.graphics.drawable.BitmapDrawable;
            import java.io.InputStream;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 16 */    public class StaticSizedRecyclableBitmapDrawable extends BitmapDrawable {
                private int mSize;

/* 17 */        public StaticSizedRecyclableBitmapDrawable(Resources res, Bitmap bitmap) {
/* 18 */            super(res, bitmap);
/* 15 */            this.mSize = -1;
                }

/* 21 */        public StaticSizedRecyclableBitmapDrawable(Resources res, String filepath) {
/* 22 */            super(res, filepath);
/* 15 */            this.mSize = -1;
                }

/* 25 */        public StaticSizedRecyclableBitmapDrawable(Resources res, InputStream is) {
/* 26 */            super(res, is);
/* 15 */            this.mSize = -1;
                }

/* 29 */        public void setSize(int size) {
/* 30 */            this.mSize = size;
                }

                @Override // android.graphics.drawable.BitmapDrawable, android.graphics.drawable.Drawable
/* 34 */        public void draw(Canvas canvas) {
/* 35 */            Bitmap bitmap = getBitmap();
/* 36 */            if (bitmap != null && !bitmap.isRecycled()) {
                        try {
/* 38 */                    if (this.mSize > 0) {
/* 39 */                        setBounds(0, 0, this.mSize, this.mSize);
                            }
/* 41 */                    super.draw(canvas);
                        } catch (RuntimeException e) {
                        }
                    }
                }
            }
