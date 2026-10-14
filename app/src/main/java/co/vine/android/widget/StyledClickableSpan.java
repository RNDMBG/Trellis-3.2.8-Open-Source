            package co.vine.android.widget;

            import android.support.annotation.NonNull;
            import android.text.TextPaint;
            import android.text.style.ClickableSpan;
            import android.view.View;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 20 */    public class StyledClickableSpan extends ClickableSpan {
                private int mColor;
                private final SpanClickListener mListener;
                private boolean mSelected;
                private boolean mSetColor;
                private final Object mTag;
                private final int mType;

/* 21 */        public StyledClickableSpan(int type, Object tag, SpanClickListener listener) {
/* 23 */            this.mType = type;
/* 24 */            this.mTag = tag;
/* 25 */            this.mListener = listener;
                }

/* 28 */        public void setColor(int color) {
/* 29 */            this.mColor = color;
/* 30 */            this.mSetColor = true;
                }

                @Override // android.text.style.ClickableSpan, android.text.style.CharacterStyle
/* 35 */        public void updateDrawState(@NonNull TextPaint ds) {
/* 36 */            if (this.mSetColor) {
/* 37 */                ds.setColor(this.mColor);
                    }
/* 39 */            ds.setUnderlineText(false);
                }

                @Override // android.text.style.ClickableSpan
/* 43 */        public void onClick(View view) {
/* 44 */            if (this.mListener != null) {
/* 45 */                this.mListener.onSpanClicked(view, this.mType, this.mTag);
                    }
                }

/* 49 */        public void select(boolean selected) {
/* 50 */            this.mSelected = selected;
                }

/* 53 */        public boolean isSelected() {
/* 54 */            return this.mSelected;
                }
            }
