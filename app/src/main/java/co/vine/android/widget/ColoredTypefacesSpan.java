            package co.vine.android.widget;

            import android.graphics.Typeface;
            import android.support.annotation.NonNull;
            import android.text.TextPaint;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 14 */    public class ColoredTypefacesSpan extends TypefacesSpan {
                private int mColor;

/* 15 */        public ColoredTypefacesSpan(Typeface type, int color) {
/* 16 */            super(null, type);
/* 17 */            this.mColor = color;
                }

/* 20 */        public ColoredTypefacesSpan(String family, Typeface type, int color) {
/* 21 */            super(family, type);
/* 22 */            this.mColor = color;
                }

/* 25 */        public ColoredTypefacesSpan(ColoredSpan coloredSpan, TypefacesSpan typefacesSpan) {
/* 26 */            super(typefacesSpan.getFamily(), typefacesSpan.getType());
/* 27 */            this.mColor = coloredSpan.getColor();
                }

/* 30 */        public void setColor(int color) {
/* 31 */            this.mColor = color;
                }

                @Override // co.vine.android.widget.TypefacesSpan, android.text.style.TypefaceSpan, android.text.style.CharacterStyle
/* 35 */        public void updateDrawState(@NonNull TextPaint ds) {
/* 36 */            ds.setColor(this.mColor);
/* 37 */            ds.setUnderlineText(false);
/* 38 */            super.updateDrawState(ds);
                }
            }
