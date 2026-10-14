            package co.vine.android.widget;

            import android.support.annotation.NonNull;
            import android.text.TextPaint;
            import android.text.style.CharacterStyle;
            
            
    public class ColoredSpan extends CharacterStyle {
                private int mColor;

        public ColoredSpan(int color) {
            this.mColor = color;
                }

        public void setColor(int color) {
            this.mColor = color;
                }

                @Override 
        public void updateDrawState(@NonNull TextPaint ds) {
            ds.setColor(this.mColor);
            ds.setUnderlineText(false);
                }

        public int getColor() {
            return this.mColor;
                }
            }
