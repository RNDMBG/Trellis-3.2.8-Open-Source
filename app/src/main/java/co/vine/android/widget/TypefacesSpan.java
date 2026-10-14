            package co.vine.android.widget;

            import android.graphics.Paint;
            import android.graphics.Typeface;
            import android.support.annotation.NonNull;
            import android.text.TextPaint;
            import android.text.style.TypefaceSpan;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 14 */    public class TypefacesSpan extends TypefaceSpan {
                private final Typeface newType;

/* 15 */        public TypefacesSpan(Typeface type) {
/* 16 */            super("");
/* 17 */            this.newType = type;
                }

/* 20 */        public TypefacesSpan(String family, Typeface type) {
/* 21 */            super(family);
/* 22 */            this.newType = type;
                }

                @Override // android.text.style.TypefaceSpan, android.text.style.CharacterStyle
/* 26 */        public void updateDrawState(@NonNull TextPaint ds) {
/* 27 */            applyCustomTypeFace(ds, this.newType);
                }

                @Override // android.text.style.TypefaceSpan, android.text.style.MetricAffectingSpan
/* 31 */        public void updateMeasureState(@NonNull TextPaint paint) {
/* 32 */            applyCustomTypeFace(paint, this.newType);
                }

/* 35 */        private static void applyCustomTypeFace(Paint paint, Typeface tf) {
/* 36 */            paint.setTypeface(tf);
                }

/* 39 */        public Typeface getType() {
/* 40 */            return this.newType;
                }
            }
