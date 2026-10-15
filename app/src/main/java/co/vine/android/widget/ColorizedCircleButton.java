            package co.vine.android.widget;

            import android.content.Context;
            import android.content.res.Resources;
            import android.graphics.Canvas;
            import android.graphics.PorterDuff;
            import android.graphics.drawable.Drawable;
            import android.support.annotation.NonNull;
            import android.util.AttributeSet;
            import android.widget.ImageView;
            import co.vine.android.R;
            
            
    public class ColorizedCircleButton extends ImageView {
                public static final int PRESSED = 2;
                public static final int UNPRESSED = 1;
                private int mColor;
                private final int mGray;
                private Drawable mIcon;
                private int mState;

        public ColorizedCircleButton(Context context) {
            this(context, null);
                }

        public ColorizedCircleButton(Context context, AttributeSet attrs) {
            this(context, attrs, 0);
                }

        public ColorizedCircleButton(Context context, AttributeSet attrs, int defStyle) {
            super(context, attrs, defStyle);
            Resources res = getResources();
            this.mGray = res.getColor(R.color.black_thirty_percent);
            this.mColor = this.mGray;
            this.mIcon = getDrawable();
                }

                @Override 
        protected void onDraw(@NonNull Canvas canvas) {
            if (isPressed() || this.mState == 2) {
                this.mIcon.setColorFilter(this.mColor, PorterDuff.Mode.SRC_ATOP);
                    } else {
                this.mIcon.setColorFilter(this.mGray, PorterDuff.Mode.SRC_ATOP);
                    }
            super.onDraw(canvas);
                }

        public void setColor(int color) {
            this.mColor = color;
            invalidate();
                }

        public void setState(int state) {
            this.mState = state;
                }

                @Override 
        public void setSelected(boolean selected) {
            setState(selected ? 2 : 1);
            super.setSelected(selected);
                }
            }
