            package co.vine.android.views;

            import android.content.Context;
            import android.util.AttributeSet;
            import android.widget.RelativeLayout;
            
            
    public class OnMeasureNotifyingRelativeLayout extends RelativeLayout {
                private OnMeasureListener mOnMeasureListener;

                public interface OnMeasureListener {
                    void onMeasure();
                }

        public OnMeasureNotifyingRelativeLayout(Context context) {
            super(context);
                }

        public OnMeasureNotifyingRelativeLayout(Context context, AttributeSet attrs) {
            super(context, attrs);
                }

        public OnMeasureNotifyingRelativeLayout(Context context, AttributeSet attrs, int defStyle) {
            super(context, attrs, defStyle);
                }

        public void setOnMeasureListener(OnMeasureListener listener) {
            this.mOnMeasureListener = listener;
                }

                @Override 
        public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            if (this.mOnMeasureListener != null) {
                this.mOnMeasureListener.onMeasure();
                    }
                }
            }
