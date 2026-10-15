            package co.vine.android.widget;

            import android.content.Context;
            import android.content.res.TypedArray;
            import android.graphics.Typeface;
            import android.util.AttributeSet;
            import co.vine.android.R;
            import co.vine.android.views.SdkTextView;
            
            
    public class TypefacesTextView extends SdkTextView {
                private int mWeight;

        public TypefacesTextView(Context context) {
            super(context);
                }

        public TypefacesTextView(Context context, AttributeSet attrs) {
            this(context, attrs, 0);
                }

        public TypefacesTextView(Context context, AttributeSet attrs, int defStyle) {
            super(context, attrs, defStyle);
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.VineTextView, defStyle, 0);
            int style = a.getInt(R.styleable.VineTextView_textStyle, 0);
            this.mWeight = a.getInt(R.styleable.VineTextView_textWeight, 2);
            a.recycle();
            setTypeface(Typefaces.get(context).getContentTypeface(style, this.mWeight), style);
            setPaintFlags(getPaintFlags() | 1);
                }

        public void setWeight(int weight) {
            this.mWeight = weight;
                }

                @Override 
        public void setTypeface(Typeface tf, int style) {
            super.setTypeface(Typefaces.get(getContext()).getContentTypeface(style, this.mWeight));
                }
            }
