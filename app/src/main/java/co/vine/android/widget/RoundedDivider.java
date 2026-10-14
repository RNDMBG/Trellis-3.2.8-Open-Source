            package co.vine.android.widget;

            import android.content.Context;
            import android.content.res.TypedArray;
            import android.graphics.PorterDuff;
            import android.util.AttributeSet;
            import android.view.View;
            import co.vine.android.C0257R;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 15 */    public class RoundedDivider extends View {
/* 16 */        public RoundedDivider(Context context) {
/* 17 */            this(context, null);
                }

/* 20 */        public RoundedDivider(Context context, AttributeSet attrs) {
/* 21 */            this(context, attrs, 0);
                }

/* 24 */        public RoundedDivider(Context context, AttributeSet attrs, int defStyle) {
/* 25 */            super(context, attrs, defStyle);
/* 26 */            TypedArray a = context.obtainStyledAttributes(attrs, C0257R.styleable.RoundedDivider, defStyle, 0);
/* 28 */            setBackgroundResource(C0257R.drawable.rule_horizontal);
/* 29 */            int color = a.getColor(0, 0);
/* 30 */            getBackground().setColorFilter(color, PorterDuff.Mode.SRC_IN);
/* 31 */            a.recycle();
                }
            }
