            package co.vine.android.widget;

            import android.graphics.Paint;
            import android.graphics.Typeface;
            import android.support.annotation.NonNull;
            import android.text.TextPaint;
            import android.text.style.TypefaceSpan;
            
            
    public class TypefacesSpan extends TypefaceSpan {
                private final Typeface newType;

        public TypefacesSpan(Typeface type) {
            super("");
            this.newType = type;
                }

        public TypefacesSpan(String family, Typeface type) {
            super(family);
            this.newType = type;
                }

                @Override 
        public void updateDrawState(@NonNull TextPaint ds) {
            applyCustomTypeFace(ds, this.newType);
                }

                @Override 
        public void updateMeasureState(@NonNull TextPaint paint) {
            applyCustomTypeFace(paint, this.newType);
                }

        private static void applyCustomTypeFace(Paint paint, Typeface tf) {
            paint.setTypeface(tf);
                }

        public Typeface getType() {
            return this.newType;
                }
            }
