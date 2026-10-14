            package co.vine.android.views;

            import android.content.Context;
            import android.support.annotation.NonNull;
            import android.util.AttributeSet;
            import android.view.MotionEvent;
            import android.widget.TextView;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 13 */    public class SdkTextView extends TextView {
/* 14 */        public SdkTextView(Context context) {
/* 15 */            super(context);
                }

/* 18 */        public SdkTextView(Context context, AttributeSet attrs) {
/* 19 */            super(context, attrs);
                }

/* 22 */        public SdkTextView(Context context, AttributeSet attrs, int defStyle) {
/* 23 */            super(context, attrs, defStyle);
                }

                @Override // android.widget.TextView, android.view.View
/* 28 */        public boolean onTouchEvent(@NonNull MotionEvent event) {
                    try {
/* 29 */                return super.onTouchEvent(event);
                    } catch (IndexOutOfBoundsException | VerifyError e) {
/* 32 */                return false;
                    }
                }
            }
