            package co.vine.android.widget;

            import android.content.Context;
            import android.util.AttributeSet;
            import android.widget.CompoundButton;
            import android.widget.ToggleButton;
            
            
    public class VineToggleButton extends ToggleButton {
                private CompoundButton.OnCheckedChangeListener mListener;

        public VineToggleButton(Context context) {
            super(context);
                }

        public VineToggleButton(Context context, AttributeSet attrs) {
            super(context, attrs);
                }

        public VineToggleButton(Context context, AttributeSet attrs, int defStyle) {
            super(context, attrs, defStyle);
                }

                @Override 
        public void setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener listener) {
            super.setOnCheckedChangeListener(listener);
            this.mListener = listener;
                }

        public void setCheckedWithoutEvent(boolean checked) {
            CompoundButton.OnCheckedChangeListener tmp = this.mListener;
            setOnCheckedChangeListener(null);
            super.setChecked(checked);
            setOnCheckedChangeListener(tmp);
                }
            }
