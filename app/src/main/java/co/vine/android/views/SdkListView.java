            package co.vine.android.views;

            import android.content.Context;
            import android.graphics.Canvas;
            import android.support.annotation.NonNull;
            import android.util.AttributeSet;
            import android.widget.ListView;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 13 */    public class SdkListView extends ListView {
/* 14 */        public SdkListView(Context context) {
/* 15 */            super(context);
                }

/* 18 */        public SdkListView(Context context, AttributeSet attrs) {
/* 19 */            super(context, attrs);
                }

/* 22 */        public SdkListView(Context context, AttributeSet attrs, int defStyle) {
/* 23 */            super(context, attrs, defStyle);
                }

                @Override // android.widget.ListView, android.widget.AbsListView, android.view.ViewGroup, android.view.View
/* 28 */        protected void dispatchDraw(@NonNull Canvas canvas) {
                    try {
/* 29 */                super.dispatchDraw(canvas);
                    } catch (IndexOutOfBoundsException e) {
                    }
                }
            }
