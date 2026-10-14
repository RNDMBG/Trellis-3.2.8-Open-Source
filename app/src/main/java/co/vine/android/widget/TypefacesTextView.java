            package co.vine.android.widget;

            import android.content.Context;
            import android.content.res.TypedArray;
            import android.graphics.Typeface;
            import android.util.AttributeSet;
            import co.vine.android.C0257R;
            import co.vine.android.views.SdkTextView;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 22 */    public class TypefacesTextView extends SdkTextView {
                private int mWeight;

/* 23 */        public TypefacesTextView(Context context) {
/* 24 */            super(context);
                }

/* 27 */        public TypefacesTextView(Context context, AttributeSet attrs) {
/* 28 */            this(context, attrs, 0);
                }

/* 31 */        public TypefacesTextView(Context context, AttributeSet attrs, int defStyle) {
/* 32 */            super(context, attrs, defStyle);
/* 34 */            TypedArray a = context.obtainStyledAttributes(attrs, C0257R.styleable.VineTextView, defStyle, 0);
/* 36 */            int style = a.getInt(1, 0);
/* 37 */            this.mWeight = a.getInt(0, 2);
/* 38 */            a.recycle();
/* 39 */            setTypeface(Typefaces.get(context).getContentTypeface(style, this.mWeight), style);
/* 40 */            setPaintFlags(getPaintFlags() | 1);
                }

/* 43 */        public void setWeight(int weight) {
/* 44 */            this.mWeight = weight;
                }

                @Override // android.widget.TextView
/* 48 */        public void setTypeface(Typeface tf, int style) {
/* 49 */            super.setTypeface(Typefaces.get(getContext()).getContentTypeface(style, this.mWeight));
                }
            }
