            package co.vine.android.widget;

            import android.content.Context;
            import android.util.AttributeSet;
            import android.widget.CompoundButton;
            import android.widget.ToggleButton;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 13 */    public class VineToggleButton extends ToggleButton {
                private CompoundButton.OnCheckedChangeListener mListener;

/* 14 */        public VineToggleButton(Context context) {
/* 15 */            super(context);
                }

/* 18 */        public VineToggleButton(Context context, AttributeSet attrs) {
/* 19 */            super(context, attrs);
                }

/* 22 */        public VineToggleButton(Context context, AttributeSet attrs, int defStyle) {
/* 23 */            super(context, attrs, defStyle);
                }

                @Override // android.widget.CompoundButton
/* 27 */        public void setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener listener) {
/* 28 */            super.setOnCheckedChangeListener(listener);
/* 29 */            this.mListener = listener;
                }

/* 32 */        public void setCheckedWithoutEvent(boolean checked) {
/* 33 */            CompoundButton.OnCheckedChangeListener tmp = this.mListener;
/* 34 */            setOnCheckedChangeListener(null);
/* 35 */            super.setChecked(checked);
/* 36 */            setOnCheckedChangeListener(tmp);
                }
            }
