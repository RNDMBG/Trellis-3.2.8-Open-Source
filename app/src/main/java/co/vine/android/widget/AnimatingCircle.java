            package co.vine.android.widget;

            import android.content.Context;
            import android.content.res.Resources;
            import android.content.res.TypedArray;
            import android.graphics.Canvas;
            import android.graphics.Paint;
            import android.graphics.Point;
            import android.graphics.PorterDuff;
            import android.graphics.PorterDuffXfermode;
            import android.os.Handler;
            import android.util.AttributeSet;
            import android.util.TypedValue;
            import android.view.View;
            import co.vine.android.C0257R;
            import co.vine.android.util.SystemUtil;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 23 */    public class AnimatingCircle extends View {
                private static final int DEFAULT_DURATION = 250;
                private static final int STEP_LENGTH = 5;
                private int mBackgroundColor;
                private final Paint mClearPaint;
                private float mHypoteneuse;
                private boolean mIsAnimating;
                private AnimatingCircleListener mListener;
                private float mNewRadius;
                private final Paint mPaint;
                private final int mRadius;
                private Runnable mRunnable;
                private float mStepDistance;
                private int mXMargin;
                private final float mXOffset;
                private static final double FORTY_FIVE_DEGREES_IN_RADIANS = 0.8377580642700195d;
                private static final double COSINE_45_DEGREES = Math.cos(FORTY_FIVE_DEGREES_IN_RADIANS);

                public interface AnimatingCircleListener {
                    void onAnimationEnd();

                    void onAnimationRepeat();

                    void onAnimationStart();
                }

/* 24 */        static /* synthetic */ float access$000(AnimatingCircle x0) {
/* 25 */            return x0.mNewRadius;
                }

/* 24 */        static /* synthetic */ float access$002(AnimatingCircle x0, float x1) {
/* 25 */            x0.mNewRadius = x1;
                    return x1;
                }

/* 24 */        static /* synthetic */ float access$100(AnimatingCircle x0) {
/* 25 */            return x0.mStepDistance;
                }

/* 75 */        public AnimatingCircle(Context context) {
/* 76 */            this(context, null);
                }

/* 79 */        public AnimatingCircle(Context context, AttributeSet attrs) {
/* 80 */            this(context, attrs, 0);
                }

/* 83 */        public AnimatingCircle(Context context, AttributeSet attrs, int defStyle) {
/* 84 */            super(context, attrs, defStyle);
/* 85 */            Resources res = context.getResources();
/* 87 */            TypedArray a = context.obtainStyledAttributes(attrs, C0257R.styleable.AnimatingCircle, defStyle, 0);
/* 89 */            this.mBackgroundColor = a.getColor(0, res.getColor(C0257R.color.draft_record_bg));
/* 91 */            this.mRadius = a.getDimensionPixelOffset(1, res.getDimensionPixelOffset(C0257R.dimen.rect_circle_radius));
/* 93 */            a.recycle();
/* 95 */            this.mPaint = new Paint();
/* 96 */            this.mClearPaint = new Paint();
/* 97 */            this.mClearPaint.setAntiAlias(true);
/* 98 */            this.mClearPaint.setDither(true);
/* 99 */            this.mClearPaint.setFilterBitmap(true);
/* 100 */           this.mClearPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
/* 102 */           Point size = SystemUtil.getDisplaySize(context);
/* 103 */           int width = size.x;
/* 104 */           int height = size.y;
/* 106 */           int determiningLength = Math.max(width, height);
/* 109 */           int adjacent = determiningLength / 2;
/* 110 */           float hypotenuse = adjacent / ((float) COSINE_45_DEGREES);
/* 111 */           float f = hypotenuse / this.mRadius;
/* 113 */           this.mHypoteneuse = hypotenuse;
/* 115 */           this.mXOffset = res.getDimensionPixelOffset(C0257R.dimen.animating_circle_left_offset);
/* 117 */           this.mXMargin = TypedValue.complexToDimensionPixelOffset(6, res.getDisplayMetrics());
/* 118 */           this.mRunnable = new AnimationRunnable();
/* 120 */           float animationDistance = this.mHypoteneuse - this.mRadius;
/* 121 */           this.mStepDistance = (animationDistance / 250.0f) * 5.0f;
/* 122 */           this.mIsAnimating = false;
/* 125 */           setLayerType(1, null);
                }

                @Override // android.view.View
/* 129 */       protected void onDraw(Canvas canvas) {
                    if (this.mNewRadius == 0.0f) {
/* 131 */               this.mNewRadius = this.mRadius;
                    }
/* 133 */           this.mPaint.setColor(this.mBackgroundColor);
/* 134 */           canvas.drawRect(0.0f, 0.0f, getMeasuredWidth() - this.mXMargin, getMeasuredHeight(), this.mPaint);
/* 135 */           canvas.drawCircle((getMeasuredWidth() / 2) + this.mXOffset, getMeasuredHeight() / 2, this.mNewRadius, this.mClearPaint);
                }

/* 139 */       public void startDefaultAnimation() {
/* 140 */           if (!this.mIsAnimating) {
/* 141 */               this.mIsAnimating = true;
/* 142 */               this.mListener.onAnimationStart();
                    } else {
/* 144 */               this.mListener.onAnimationRepeat();
                    }
                    if (this.mNewRadius < this.mHypoteneuse) {
/* 147 */               Handler handler = getHandler();
/* 148 */               if (handler != null) {
/* 149 */                   handler.removeCallbacks(this.mRunnable);
/* 150 */                   handler.postDelayed(this.mRunnable, 5L);
/* 156 */                   return;
                        }
/* 156 */               return;
                    }
/* 153 */           this.mIsAnimating = false;
/* 154 */           this.mListener.onAnimationEnd();
                }

/* 158 */       public void resetAnimation() {
/* 159 */           this.mNewRadius = 0.0f;
/* 160 */           this.mIsAnimating = false;
                }

/* 163 */       public void setAnimationListener(AnimatingCircleListener listener) {
/* 164 */           this.mListener = listener;
                }

/* 165 */       public class AnimationRunnable implements Runnable {
/* 166 */           public AnimationRunnable() {
                    }

                    @Override // java.lang.Runnable
/* 170 */           public void run() {
/* 171 */               AnimatingCircle.access$002(AnimatingCircle.this, AnimatingCircle.access$000(AnimatingCircle.this) + AnimatingCircle.access$100(AnimatingCircle.this));
/* 172 */               AnimatingCircle.this.invalidate();
/* 173 */               AnimatingCircle.this.startDefaultAnimation();
                    }
                }
            }
