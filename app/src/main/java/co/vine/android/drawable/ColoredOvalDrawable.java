            package co.vine.android.drawable;

            import android.graphics.Canvas;
            import android.graphics.Paint;
            import android.graphics.drawable.ShapeDrawable;
            import android.graphics.drawable.shapes.OvalShape;
            
            
    public class ColoredOvalDrawable extends ShapeDrawable {
                public int mColor;

        public ColoredOvalDrawable() {
            setShape(new ColoredOvalShape());
                }

        public void setColor(int color) {
            this.mColor = color;
                }

        public class ColoredOvalShape extends OvalShape {
            public ColoredOvalShape() {
                    }

                    @Override 
            public void draw(Canvas canvas, Paint paint) {
                paint.setColor(ColoredOvalDrawable.this.mColor);
                super.draw(canvas, paint);
                    }
                }
            }
