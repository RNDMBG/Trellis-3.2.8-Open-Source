            package co.vine.android.views;

            import android.content.Context;
            import android.util.AttributeSet;
            import android.view.MotionEvent;
            import android.widget.RelativeLayout;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 20 */    public class TouchableRelativeLayout extends RelativeLayout {
                public TouchListener mTouchListener;

                public interface TouchListener {
                    boolean onInterceptTouchEvent(MotionEvent motionEvent);

                    boolean onTouchEvent(MotionEvent motionEvent);
                }

/* 21 */        public TouchableRelativeLayout(Context context) {
/* 22 */            super(context);
                }

/* 25 */        public TouchableRelativeLayout(Context context, AttributeSet attrs) {
/* 26 */            super(context, attrs);
                }

/* 29 */        public void setTouchListener(TouchListener listener) {
/* 30 */            this.mTouchListener = listener;
                }

                @Override // android.view.ViewGroup
/* 34 */        public boolean onInterceptTouchEvent(MotionEvent ev) {
                    return this.mTouchListener != null ? this.mTouchListener.onInterceptTouchEvent(ev) : super.onInterceptTouchEvent(ev);
                }

                @Override // android.view.View
/* 43 */        public boolean onTouchEvent(MotionEvent ev) {
                    return this.mTouchListener != null ? this.mTouchListener.onTouchEvent(ev) : super.onTouchEvent(ev);
                }
            }
