            package co.vine.android.views;

            import android.content.Context;
            import android.util.AttributeSet;
            import android.widget.RelativeLayout;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 16 */    public class OnMeasureNotifyingRelativeLayout extends RelativeLayout {
                private OnMeasureListener mOnMeasureListener;

                public interface OnMeasureListener {
                    void onMeasure();
                }

/* 17 */        public OnMeasureNotifyingRelativeLayout(Context context) {
/* 18 */            super(context);
                }

/* 21 */        public OnMeasureNotifyingRelativeLayout(Context context, AttributeSet attrs) {
/* 22 */            super(context, attrs);
                }

/* 25 */        public OnMeasureNotifyingRelativeLayout(Context context, AttributeSet attrs, int defStyle) {
/* 26 */            super(context, attrs, defStyle);
                }

/* 29 */        public void setOnMeasureListener(OnMeasureListener listener) {
/* 30 */            this.mOnMeasureListener = listener;
                }

                @Override // android.widget.RelativeLayout, android.view.View
/* 34 */        public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
/* 35 */            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
/* 36 */            if (this.mOnMeasureListener != null) {
/* 37 */                this.mOnMeasureListener.onMeasure();
                    }
                }
            }
