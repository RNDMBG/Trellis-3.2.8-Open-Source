            package co.vine.android.widget;

            import android.content.Context;
            import android.content.res.Resources;
            import android.content.res.TypedArray;
            import android.graphics.Canvas;
            import android.graphics.PorterDuff;
            import android.graphics.drawable.Drawable;
            import android.support.annotation.NonNull;
            import android.util.AttributeSet;
            import android.widget.ImageView;
            import co.vine.android.R;
            import co.vine.android.Settings;
            import co.vine.android.util.ViewUtil;
            
            
    public class ColorPickerCircle extends ImageView {
                private int mIndex;
                private final Runnable mIndexChangeRunnable;
                private int mNextIndex;
                private boolean mSelected;
                private Drawable mWhiteCircle;

        static  int access$000(ColorPickerCircle x0) {
            return x0.mNextIndex;
                }

        public ColorPickerCircle(Context context) {
            this(context, null);
                }

        public ColorPickerCircle(Context context, AttributeSet attrs) {
            this(context, attrs, 0);
                }

        public ColorPickerCircle(Context context, AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
            this.mIndexChangeRunnable = new RunnableC05481();
            Resources res = getResources();
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ColorPickerCircle, defStyleAttr, 0);
            a.recycle();
            this.mWhiteCircle = res.getDrawable(R.drawable.circle_shape_white);
                }

                @Override 
        protected void onDraw(@NonNull Canvas canvas) {
            super.onDraw(canvas);
            if (this.mSelected) {
                int width = getWidth();
                this.mWhiteCircle.setBounds(width / 4, width / 4, (width * 3) / 4, (width * 3) / 4);
                this.mWhiteCircle.draw(canvas);
                    }
                }

                @Override 
        public void setSelected(boolean selected) {
            super.setSelected(selected);
            this.mSelected = selected;
            invalidate();
                }

        public void setIndex(int index) {
            this.mIndex = index;
            Drawable d = getResources().getDrawable(R.drawable.circle_shape_light);
            d.setColorFilter(Settings.PROFILE_BACKGROUND_COLORS[index] | (-16777216), PorterDuff.Mode.SRC_ATOP);
            ViewUtil.setBackground(this, d);
            invalidate();
                }

        public int getIndex() {
            return this.mIndex;
                }

        public void postIndexChange(int index) {
            this.mNextIndex = index;
            post(this.mIndexChangeRunnable);
                }

                
        class RunnableC05481 implements Runnable {
            RunnableC05481() {
                    }

                    @Override 
            public void run() {
                ColorPickerCircle.this.setIndex(ColorPickerCircle.access$000(ColorPickerCircle.this));
                    }
                }
            }
