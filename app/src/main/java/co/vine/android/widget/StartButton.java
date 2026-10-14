            package co.vine.android.widget;

            import android.content.Context;
            import android.util.AttributeSet;
            import android.widget.Button;
            
            
    public class StartButton extends Button {
        public StartButton(Context context) {
            super(context);
                }

        public StartButton(Context context, AttributeSet attrs) {
            super(context, attrs);
                }

        public StartButton(Context context, AttributeSet attrs, int defStyle) {
            super(context, attrs, defStyle);
                }

                @Override 
        public int getCompoundPaddingLeft() {
            return 0;
                }
            }
