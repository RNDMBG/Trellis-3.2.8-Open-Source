            package co.vine.android.widget;

            import android.content.Context;
            import android.content.res.TypedArray;
            import android.graphics.drawable.Drawable;
            import android.util.AttributeSet;
            import co.vine.android.C0257R;
            import co.vine.android.dragsort.DragSortWidget;
            
            
    public class RecorderDragSortWidget extends DragSortWidget {
        public RecorderDragSortWidget(Context context) {
            super(context);
                }

        public RecorderDragSortWidget(Context context, AttributeSet attrs) {
            super(context, attrs);
                }

        public RecorderDragSortWidget(Context context, AttributeSet attrs, int defStyle) {
            super(context, attrs, defStyle);
                }

                @Override 
        protected TypedArray getStylingArray(Context context, AttributeSet attrs, int defStyle) {
            if (defStyle == 0) {
                defStyle = C0257R.attr.dragSortWidgetStyle;
                    }
            return context.obtainStyledAttributes(attrs, C0257R.styleable.DragSortWidget, defStyle, 0);
                }

                @Override 
        protected int getFloatViewDefaultSize(TypedArray a) {
            return a.getDimensionPixelSize(2, 0);
                }

                @Override 
        protected Drawable getFloatViewActivatedDrawable(TypedArray a) {
            return a.getDrawable(1);
                }

                @Override 
        protected Drawable getActiveDrawable(TypedArray a) {
            return a.getDrawable(0);
                }
            }
