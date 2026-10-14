            package co.vine.android.drawable;

            import android.graphics.Canvas;
            import android.graphics.Paint;
            import android.graphics.drawable.ShapeDrawable;
            import android.graphics.drawable.shapes.OvalShape;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 13 */    public class ColoredOvalDrawable extends ShapeDrawable {
                public int mColor;

/* 14 */        public ColoredOvalDrawable() {
/* 16 */            setShape(new ColoredOvalShape());
                }

/* 19 */        public void setColor(int color) {
/* 20 */            this.mColor = color;
                }

/* 21 */        public class ColoredOvalShape extends OvalShape {
/* 22 */            public ColoredOvalShape() {
                    }

                    @Override // android.graphics.drawable.shapes.OvalShape, android.graphics.drawable.shapes.RectShape, android.graphics.drawable.shapes.Shape
/* 26 */            public void draw(Canvas canvas, Paint paint) {
/* 27 */                paint.setColor(ColoredOvalDrawable.this.mColor);
/* 28 */                super.draw(canvas, paint);
                    }
                }
            }
