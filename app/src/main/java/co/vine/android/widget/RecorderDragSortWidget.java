            package co.vine.android.widget;

            import android.content.Context;
            import android.content.res.TypedArray;
            import android.graphics.drawable.Drawable;
            import android.util.AttributeSet;
            import co.vine.android.C0257R;
            import co.vine.android.dragsort.DragSortWidget;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 15 */    public class RecorderDragSortWidget extends DragSortWidget {
/* 16 */        public RecorderDragSortWidget(Context context) {
/* 17 */            super(context);
                }

/* 20 */        public RecorderDragSortWidget(Context context, AttributeSet attrs) {
/* 21 */            super(context, attrs);
                }

/* 24 */        public RecorderDragSortWidget(Context context, AttributeSet attrs, int defStyle) {
/* 25 */            super(context, attrs, defStyle);
                }

                @Override // co.vine.android.dragsort.DragSortWidget
/* 29 */        protected TypedArray getStylingArray(Context context, AttributeSet attrs, int defStyle) {
/* 30 */            if (defStyle == 0) {
/* 31 */                defStyle = C0257R.attr.dragSortWidgetStyle;
                    }
/* 33 */            return context.obtainStyledAttributes(attrs, C0257R.styleable.DragSortWidget, defStyle, 0);
                }

                @Override // co.vine.android.dragsort.DragSortWidget
/* 37 */        protected int getFloatViewDefaultSize(TypedArray a) {
/* 38 */            return a.getDimensionPixelSize(2, 0);
                }

                @Override // co.vine.android.dragsort.DragSortWidget
/* 42 */        protected Drawable getFloatViewActivatedDrawable(TypedArray a) {
/* 43 */            return a.getDrawable(1);
                }

                @Override // co.vine.android.dragsort.DragSortWidget
/* 47 */        protected Drawable getActiveDrawable(TypedArray a) {
/* 48 */            return a.getDrawable(0);
                }
            }
