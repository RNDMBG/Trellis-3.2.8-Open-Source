            package co.vine.android.drawable;

            import android.content.res.Resources;
            import android.graphics.Bitmap;
            import android.graphics.Canvas;
            import android.graphics.drawable.BitmapDrawable;
            import java.io.InputStream;
            
            
    public class StaticSizedRecyclableBitmapDrawable extends BitmapDrawable {
                private int mSize;

        public StaticSizedRecyclableBitmapDrawable(Resources res, Bitmap bitmap) {
            super(res, bitmap);
            this.mSize = -1;
                }

        public StaticSizedRecyclableBitmapDrawable(Resources res, String filepath) {
            super(res, filepath);
            this.mSize = -1;
                }

        public StaticSizedRecyclableBitmapDrawable(Resources res, InputStream is) {
            super(res, is);
            this.mSize = -1;
                }

        public void setSize(int size) {
            this.mSize = size;
                }

                @Override 
        public void draw(Canvas canvas) {
            Bitmap bitmap = getBitmap();
            if (bitmap != null && !bitmap.isRecycled()) {
                        try {
                    if (this.mSize > 0) {
                        setBounds(0, 0, this.mSize, this.mSize);
                            }
                    super.draw(canvas);
                        } catch (RuntimeException e) {
                        }
                    }
                }
            }
