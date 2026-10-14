            package co.vine.android.widget;

            import android.graphics.Typeface;
            import android.support.annotation.NonNull;
            import android.text.TextPaint;
            
            
    public class ColoredTypefacesSpan extends TypefacesSpan {
                private int mColor;

        public ColoredTypefacesSpan(Typeface type, int color) {
            super(null, type);
            this.mColor = color;
                }

        public ColoredTypefacesSpan(String family, Typeface type, int color) {
            super(family, type);
            this.mColor = color;
                }

        public ColoredTypefacesSpan(ColoredSpan coloredSpan, TypefacesSpan typefacesSpan) {
            super(typefacesSpan.getFamily(), typefacesSpan.getType());
            this.mColor = coloredSpan.getColor();
                }

        public void setColor(int color) {
            this.mColor = color;
                }

                @Override 
        public void updateDrawState(@NonNull TextPaint ds) {
            ds.setColor(this.mColor);
            ds.setUnderlineText(false);
            super.updateDrawState(ds);
                }
            }
