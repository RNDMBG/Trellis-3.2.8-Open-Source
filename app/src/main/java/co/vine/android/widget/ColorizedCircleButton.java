            package co.vine.android.widget;

            import android.content.Context;
            import android.content.res.Resources;
            import android.graphics.Canvas;
            import android.graphics.PorterDuff;
            import android.graphics.drawable.Drawable;
            import android.support.annotation.NonNull;
            import android.util.AttributeSet;
            import android.widget.ImageView;
            import co.vine.android.C0257R;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 25 */    public class ColorizedCircleButton extends ImageView {
                public static final int PRESSED = 2;
                public static final int UNPRESSED = 1;
                private int mColor;
                private final int mGray;
                private Drawable mIcon;
                private int mState;

/* 26 */        public ColorizedCircleButton(Context context) {
/* 27 */            this(context, null);
                }

/* 30 */        public ColorizedCircleButton(Context context, AttributeSet attrs) {
/* 31 */            this(context, attrs, 0);
                }

/* 34 */        public ColorizedCircleButton(Context context, AttributeSet attrs, int defStyle) {
/* 35 */            super(context, attrs, defStyle);
/* 37 */            Resources res = getResources();
/* 39 */            this.mGray = res.getColor(C0257R.color.black_thirty_percent);
/* 41 */            this.mColor = this.mGray;
/* 43 */            this.mIcon = getDrawable();
                }

                @Override // android.widget.ImageView, android.view.View
/* 48 */        protected void onDraw(@NonNull Canvas canvas) {
/* 49 */            if (isPressed() || this.mState == 2) {
/* 50 */                this.mIcon.setColorFilter(this.mColor, PorterDuff.Mode.SRC_ATOP);
                    } else {
/* 52 */                this.mIcon.setColorFilter(this.mGray, PorterDuff.Mode.SRC_ATOP);
                    }
/* 54 */            super.onDraw(canvas);
                }

/* 57 */        public void setColor(int color) {
/* 58 */            this.mColor = color;
/* 59 */            invalidate();
                }

/* 62 */        public void setState(int state) {
/* 63 */            this.mState = state;
                }

                @Override // android.widget.ImageView, android.view.View
/* 67 */        public void setSelected(boolean selected) {
/* 68 */            setState(selected ? 2 : 1);
/* 69 */            super.setSelected(selected);
                }
            }
