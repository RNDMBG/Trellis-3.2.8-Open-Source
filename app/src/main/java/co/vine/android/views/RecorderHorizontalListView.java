            package co.vine.android.views;

            import android.content.Context;
            import android.content.res.TypedArray;
            import android.graphics.drawable.Drawable;
            import android.util.AttributeSet;
            import co.vine.android.R;
            
            
    public class RecorderHorizontalListView extends HorizontalListView {
        public RecorderHorizontalListView(Context context) {
            super(context);
                }

        public RecorderHorizontalListView(Context context, AttributeSet attrs) {
            super(context, attrs);
                }

        public RecorderHorizontalListView(Context context, AttributeSet attrs, int defStyle) {
            super(context, attrs, defStyle);
                }

                @Override 
        protected float getFillWidthHeightRatio(TypedArray a) {
            return a.getFloat(R.styleable.HorizontalListView_fillWidthHeightRatio, 0.0f);
                }

                @Override 
        protected int getEdgePadding(TypedArray a) {
            return a.getDimensionPixelSize(R.styleable.HorizontalListView_edgePadding, 0);
                }

                @Override 
        protected void initializeDividerStyles(TypedArray a) {
            Drawable divider = a.getDrawable(R.styleable.HorizontalListView_listDivider);
            if (divider != null) {
                setDivider(divider);
                    }
            int dividerWidth = a.getDimensionPixelSize(R.styleable.HorizontalListView_dividerWidth, 0);
            if (dividerWidth > 0) {
                setDividerWidth(dividerWidth);
                    }
                }

                @Override 
        protected TypedArray getStylingArray(Context context, AttributeSet attrs, int defStyle) {
            if (defStyle == 0) {
                defStyle = R.attr.horizontalListViewStyle;
                    }
            return context.obtainStyledAttributes(attrs, R.styleable.HorizontalListView, defStyle, 0);
                }
            }
