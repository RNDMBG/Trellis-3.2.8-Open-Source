            package co.vine.android.widget;

            import android.content.Context;
            import android.graphics.Canvas;
            import android.support.annotation.NonNull;
            import android.text.style.StyleSpan;
            import android.util.AttributeSet;
            import android.widget.TextView;
            import co.vine.android.util.CommonUtil;
            import co.vine.android.util.Util;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 22 */    public class StyledTextView extends TypefacesTextView {
                boolean init;
                private SpanClickListener mListener;
                private String mOriginalText;

/* 23 */        public StyledTextView(Context context) {
/* 24 */            this(context, null);
                }

/* 27 */        public StyledTextView(Context context, AttributeSet attrs) {
/* 28 */            this(context, attrs, 0);
                }

/* 31 */        public StyledTextView(Context context, AttributeSet attrs, int defStyle) {
/* 32 */            super(context, attrs, defStyle);
/* 21 */            this.init = false;
                }

/* 35 */        public void setSpanClickListener(SpanClickListener listener) {
/* 36 */            this.mListener = listener;
                }

/* 40 */        public void setStyledText(String styledText, StyleSpan[] mStyleSpanBoldSingle) {
/* 41 */            this.mOriginalText = styledText;
/* 42 */            setText(Util.getSpannedText(mStyleSpanBoldSingle, styledText, CommonUtil.MARKER_QUOTES), TextView.BufferType.SPANNABLE);
                }

                @Override // android.widget.TextView, android.view.View
/* 47 */        protected void onDraw(@NonNull Canvas canvas) {
/* 48 */            super.onDraw(canvas);
                }
            }
