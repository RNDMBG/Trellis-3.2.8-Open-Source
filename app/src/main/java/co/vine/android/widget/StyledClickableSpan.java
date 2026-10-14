            package co.vine.android.widget;

            import android.support.annotation.NonNull;
            import android.text.TextPaint;
            import android.text.style.ClickableSpan;
            import android.view.View;
            
            
    public class StyledClickableSpan extends ClickableSpan {
                private int mColor;
                private final SpanClickListener mListener;
                private boolean mSelected;
                private boolean mSetColor;
                private final Object mTag;
                private final int mType;

        public StyledClickableSpan(int type, Object tag, SpanClickListener listener) {
            this.mType = type;
            this.mTag = tag;
            this.mListener = listener;
                }

        public void setColor(int color) {
            this.mColor = color;
            this.mSetColor = true;
                }

                @Override 
        public void updateDrawState(@NonNull TextPaint ds) {
            if (this.mSetColor) {
                ds.setColor(this.mColor);
                    }
            ds.setUnderlineText(false);
                }

                @Override 
        public void onClick(View view) {
            if (this.mListener != null) {
                this.mListener.onSpanClicked(view, this.mType, this.mTag);
                    }
                }

        public void select(boolean selected) {
            this.mSelected = selected;
                }

        public boolean isSelected() {
            return this.mSelected;
                }
            }
