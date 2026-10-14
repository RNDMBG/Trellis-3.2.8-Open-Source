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
            import co.vine.android.C0257R;
            import co.vine.android.Settings;
            import co.vine.android.util.ViewUtil;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 20 */    public class ColorPickerCircle extends ImageView {
                private int mIndex;
                private final Runnable mIndexChangeRunnable;
                private int mNextIndex;
                private boolean mSelected;
                private Drawable mWhiteCircle;

/* 21 */        static /* synthetic */ int access$000(ColorPickerCircle x0) {
/* 22 */            return x0.mNextIndex;
                }

/* 29 */        public ColorPickerCircle(Context context) {
/* 30 */            this(context, null);
                }

/* 33 */        public ColorPickerCircle(Context context, AttributeSet attrs) {
/* 34 */            this(context, attrs, 0);
                }

/* 37 */        public ColorPickerCircle(Context context, AttributeSet attrs, int defStyleAttr) {
/* 38 */            super(context, attrs, defStyleAttr);
/* 83 */            this.mIndexChangeRunnable = new RunnableC05481();
/* 39 */            Resources res = getResources();
/* 40 */            TypedArray a = context.obtainStyledAttributes(attrs, C0257R.styleable.ColorPickerCircle, defStyleAttr, 0);
/* 42 */            a.recycle();
/* 43 */            this.mWhiteCircle = res.getDrawable(C0257R.drawable.circle_shape_white);
                }

                @Override // android.widget.ImageView, android.view.View
/* 47 */        protected void onDraw(@NonNull Canvas canvas) {
/* 48 */            super.onDraw(canvas);
/* 49 */            if (this.mSelected) {
/* 50 */                int width = getWidth();
/* 51 */                this.mWhiteCircle.setBounds(width / 4, width / 4, (width * 3) / 4, (width * 3) / 4);
/* 52 */                this.mWhiteCircle.draw(canvas);
                    }
                }

                @Override // android.widget.ImageView, android.view.View
/* 57 */        public void setSelected(boolean selected) {
/* 58 */            super.setSelected(selected);
/* 59 */            this.mSelected = selected;
/* 60 */            invalidate();
                }

/* 63 */        public void setIndex(int index) {
/* 64 */            this.mIndex = index;
/* 65 */            Drawable d = getResources().getDrawable(C0257R.drawable.circle_shape_light);
/* 66 */            d.setColorFilter(Settings.PROFILE_BACKGROUND_COLORS[index] | (-16777216), PorterDuff.Mode.SRC_ATOP);
/* 68 */            ViewUtil.setBackground(this, d);
/* 69 */            invalidate();
                }

/* 72 */        public int getIndex() {
/* 73 */            return this.mIndex;
                }

/* 76 */        public void postIndexChange(int index) {
/* 77 */            this.mNextIndex = index;
/* 78 */            post(this.mIndexChangeRunnable);
                }

                /* JADX INFO: renamed from: co.vine.android.widget.ColorPickerCircle$1 */
/* 81 */        class RunnableC05481 implements Runnable {
/* 82 */            RunnableC05481() {
                    }

                    @Override // java.lang.Runnable
/* 85 */            public void run() {
/* 86 */                ColorPickerCircle.this.setIndex(ColorPickerCircle.access$000(ColorPickerCircle.this));
                    }
                }
            }
