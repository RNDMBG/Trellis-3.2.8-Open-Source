            package co.vine.android.views;

            import android.content.Context;
            import android.support.annotation.NonNull;
            import android.util.AttributeSet;
            import android.view.MotionEvent;
            import android.widget.EditText;
            
            
    public class SdkEditText extends EditText {
        public SdkEditText(Context context) {
            super(context);
                }

        public SdkEditText(Context context, AttributeSet attrs) {
            super(context, attrs);
                }

        public SdkEditText(Context context, AttributeSet attrs, int defStyle) {
            super(context, attrs, defStyle);
                }

                @Override 
        public boolean onTouchEvent(@NonNull MotionEvent event) {
                    try {
                return super.onTouchEvent(event);
                    } catch (IndexOutOfBoundsException | VerifyError e) {
                return false;
                    }
                }
            }
