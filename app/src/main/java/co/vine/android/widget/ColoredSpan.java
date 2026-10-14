            package co.vine.android.widget;

            import android.support.annotation.NonNull;
            import android.text.TextPaint;
            import android.text.style.CharacterStyle;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 13 */    public class ColoredSpan extends CharacterStyle {
                private int mColor;

/* 14 */        public ColoredSpan(int color) {
/* 16 */            this.mColor = color;
                }

/* 19 */        public void setColor(int color) {
/* 20 */            this.mColor = color;
                }

                @Override // android.text.style.CharacterStyle
/* 24 */        public void updateDrawState(@NonNull TextPaint ds) {
/* 25 */            ds.setColor(this.mColor);
/* 26 */            ds.setUnderlineText(false);
                }

/* 29 */        public int getColor() {
/* 30 */            return this.mColor;
                }
            }
