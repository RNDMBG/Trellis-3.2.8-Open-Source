            package co.vine.android.views;

            import android.content.Context;
            import android.graphics.Canvas;
            import android.support.annotation.NonNull;
            import android.util.AttributeSet;
            import android.widget.ListView;
            
            
    public class SdkListView extends ListView {
        public SdkListView(Context context) {
            super(context);
                }

        public SdkListView(Context context, AttributeSet attrs) {
            super(context, attrs);
                }

        public SdkListView(Context context, AttributeSet attrs, int defStyle) {
            super(context, attrs, defStyle);
                }

                @Override 
        protected void dispatchDraw(@NonNull Canvas canvas) {
                    try {
                super.dispatchDraw(canvas);
                    } catch (IndexOutOfBoundsException e) {
                    }
                }
            }
