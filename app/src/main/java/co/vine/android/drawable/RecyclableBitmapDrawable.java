            package co.vine.android.drawable;

            import android.content.res.Resources;
            import android.graphics.Bitmap;
            import android.graphics.Canvas;
            import android.graphics.drawable.BitmapDrawable;
            import java.io.InputStream;
            
            
    public class RecyclableBitmapDrawable extends BitmapDrawable {
        public RecyclableBitmapDrawable(Resources res, Bitmap bitmap) {
            super(res, bitmap);
                }

        public RecyclableBitmapDrawable(Resources res, String filepath) {
            super(res, filepath);
                }

        public RecyclableBitmapDrawable(Resources res, InputStream is) {
            super(res, is);
                }

                @Override 
        public void draw(Canvas canvas) {
            Bitmap bitmap = getBitmap();
            if (bitmap != null && !bitmap.isRecycled()) {
                        try {
                    super.draw(canvas);
                        } catch (RuntimeException e) {
                        }
                    }
                }
            }
