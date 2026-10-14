            package co.vine.android.views;

            import android.content.Context;
            import android.content.res.TypedArray;
            import android.graphics.drawable.Drawable;
            import android.util.AttributeSet;
            import co.vine.android.C0257R;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 14 */    public class RecorderHorizontalListView extends HorizontalListView {
/* 15 */        public RecorderHorizontalListView(Context context) {
/* 16 */            super(context);
                }

/* 19 */        public RecorderHorizontalListView(Context context, AttributeSet attrs) {
/* 20 */            super(context, attrs);
                }

/* 23 */        public RecorderHorizontalListView(Context context, AttributeSet attrs, int defStyle) {
/* 24 */            super(context, attrs, defStyle);
                }

                @Override // co.vine.android.views.HorizontalListView
/* 28 */        protected float getFillWidthHeightRatio(TypedArray a) {
/* 29 */            return a.getFloat(3, 0.0f);
                }

                @Override // co.vine.android.views.HorizontalListView
/* 33 */        protected int getEdgePadding(TypedArray a) {
/* 34 */            return a.getDimensionPixelSize(2, 0);
                }

                @Override // co.vine.android.views.HorizontalListView
/* 38 */        protected void initializeDividerStyles(TypedArray a) {
/* 39 */            Drawable divider = a.getDrawable(0);
/* 40 */            if (divider != null) {
/* 41 */                setDivider(divider);
                    }
/* 43 */            int dividerWidth = a.getDimensionPixelSize(1, 0);
/* 45 */            if (dividerWidth > 0) {
/* 46 */                setDividerWidth(dividerWidth);
                    }
                }

                @Override // co.vine.android.views.HorizontalListView
/* 52 */        protected TypedArray getStylingArray(Context context, AttributeSet attrs, int defStyle) {
/* 53 */            if (defStyle == 0) {
/* 54 */                defStyle = C0257R.attr.horizontalListViewStyle;
                    }
/* 56 */            return context.obtainStyledAttributes(attrs, C0257R.styleable.HorizontalListView, defStyle, 0);
                }
            }
