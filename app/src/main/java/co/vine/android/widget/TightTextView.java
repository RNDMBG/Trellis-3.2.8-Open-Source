            package co.vine.android.widget;

            import android.content.Context;
            import android.text.Layout;
            import android.util.AttributeSet;
            import android.view.View;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 19 */    public class TightTextView extends TypefacesTextView {
/* 20 */        public TightTextView(Context context) {
/* 21 */            this(context, null, 0);
                }

/* 24 */        public TightTextView(Context context, AttributeSet attrs) {
/* 25 */            this(context, attrs, 0);
                }

/* 28 */        public TightTextView(Context context, AttributeSet attrs, int defStyle) {
/* 29 */            super(context, attrs, defStyle);
                }

                @Override // android.widget.TextView, android.view.View
/* 33 */        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
                    Layout layout;
                    int linesCount;
/* 34 */            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
/* 35 */            int specModeW = View.MeasureSpec.getMode(widthMeasureSpec);
/* 36 */            if (specModeW != 1073741824 && (linesCount = (layout = getLayout()).getLineCount()) > 1) {
/* 40 */                float textRealMaxWidth = 0.0f;
                        for (int n = 0; n < linesCount; n++) {
/* 42 */                    textRealMaxWidth = Math.max(textRealMaxWidth, layout.getLineWidth(n));
                        }
/* 44 */                int w = Math.round(textRealMaxWidth);
/* 45 */                if (w < getMeasuredWidth()) {
/* 46 */                    super.onMeasure(View.MeasureSpec.makeMeasureSpec(w, Integer.MIN_VALUE), heightMeasureSpec);
                        }
                    }
                }
            }
