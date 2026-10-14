            package co.vine.android.widget;

            import android.content.Context;
            import android.content.res.TypedArray;
            import android.graphics.PorterDuff;
            import android.util.AttributeSet;
            import android.view.View;
            import co.vine.android.C0257R;
            
            
    public class RoundedDivider extends View {
        public RoundedDivider(Context context) {
            this(context, null);
                }

        public RoundedDivider(Context context, AttributeSet attrs) {
            this(context, attrs, 0);
                }

        public RoundedDivider(Context context, AttributeSet attrs, int defStyle) {
            super(context, attrs, defStyle);
            TypedArray a = context.obtainStyledAttributes(attrs, C0257R.styleable.RoundedDivider, defStyle, 0);
            setBackgroundResource(C0257R.drawable.rule_horizontal);
            int color = a.getColor(0, 0);
            getBackground().setColorFilter(color, PorterDuff.Mode.SRC_IN);
            a.recycle();
                }
            }
