            package co.vine.android.widget;

            import android.content.Context;
            import android.graphics.Canvas;
            import android.support.annotation.NonNull;
            import android.text.style.StyleSpan;
            import android.util.AttributeSet;
            import android.widget.TextView;
            import co.vine.android.util.CommonUtil;
            import co.vine.android.util.Util;
            
            
    public class StyledTextView extends TypefacesTextView {
                boolean init;
                private SpanClickListener mListener;
                private String mOriginalText;

        public StyledTextView(Context context) {
            this(context, null);
                }

        public StyledTextView(Context context, AttributeSet attrs) {
            this(context, attrs, 0);
                }

        public StyledTextView(Context context, AttributeSet attrs, int defStyle) {
            super(context, attrs, defStyle);
            this.init = false;
                }

        public void setSpanClickListener(SpanClickListener listener) {
            this.mListener = listener;
                }

        public void setStyledText(String styledText, StyleSpan[] mStyleSpanBoldSingle) {
            this.mOriginalText = styledText;
            setText(Util.getSpannedText(mStyleSpanBoldSingle, styledText, CommonUtil.MARKER_QUOTES), TextView.BufferType.SPANNABLE);
                }

                @Override 
        protected void onDraw(@NonNull Canvas canvas) {
            super.onDraw(canvas);
                }
            }
