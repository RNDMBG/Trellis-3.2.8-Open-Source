            package co.vine.android.drawable;

            import android.content.res.Resources;
            import android.graphics.Bitmap;
            import android.graphics.Canvas;
            import android.graphics.drawable.BitmapDrawable;
            import java.io.InputStream;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 14 */    public class RecyclableBitmapDrawable extends BitmapDrawable {
/* 15 */        public RecyclableBitmapDrawable(Resources res, Bitmap bitmap) {
/* 16 */            super(res, bitmap);
                }

/* 19 */        public RecyclableBitmapDrawable(Resources res, String filepath) {
/* 20 */            super(res, filepath);
                }

/* 23 */        public RecyclableBitmapDrawable(Resources res, InputStream is) {
/* 24 */            super(res, is);
                }

                @Override // android.graphics.drawable.BitmapDrawable, android.graphics.drawable.Drawable
/* 28 */        public void draw(Canvas canvas) {
/* 29 */            Bitmap bitmap = getBitmap();
/* 30 */            if (bitmap != null && !bitmap.isRecycled()) {
                        try {
/* 32 */                    super.draw(canvas);
                        } catch (RuntimeException e) {
                        }
                    }
                }
            }
