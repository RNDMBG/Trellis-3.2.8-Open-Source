            package co.vine.android.animation;

            import android.view.View;
            import android.widget.RelativeLayout;
            import com.edisonwang.android.slog.SLog;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 29 */    public class MoveResizeAnimator extends SmoothAnimator {
                private final int mDeltaHeight;
                private final int mDeltaWidth;
                private final float mDeltaX;
                private final float mDeltaY;
                private int mId;
                private SmoothAnimator.AnimationListener mListener;
                private final int mOriginalHeight;
                private final int mOriginalWidth;
                private final int mOriginalX;
                private final int mOriginalY;
                private final RelativeLayout.LayoutParams mParam;
                private Object mTag;
                private final View mTarget;
                private final int mTargetHeight;
                private final int mTargetWidth;

/* 30 */        public MoveResizeAnimator(int id, View target, int targetY, double targetZoomX, double targetZoomY, int duration, SmoothAnimator.AnimationListener listener, Object tag) {
/* 31 */            this(id, target, target, 0, targetY, targetZoomX, targetZoomY, duration, listener, tag);
                }

/* 36 */        public MoveResizeAnimator(int id, View target, View measureUsingView, int targetX, int targetY, double targetZoomX, double targetZoomY, int duration, SmoothAnimator.AnimationListener listener, Object tag) {
/* 37 */            super(target, 0.05f, duration);
/* 38 */            this.mTarget = target;
/* 39 */            this.mParam = (RelativeLayout.LayoutParams) target.getLayoutParams();
/* 40 */            this.mDeltaX = this.mParam.leftMargin - targetX;
/* 41 */            this.mDeltaY = this.mParam.topMargin - targetY;
/* 42 */            this.mOriginalX = this.mParam.leftMargin;
/* 43 */            this.mOriginalY = this.mParam.topMargin;
/* 44 */            if (this.mParam.width < 3) {
/* 45 */                this.mParam.width = measureUsingView.getMeasuredWidth();
                    }
/* 47 */            if (this.mParam.height < 3) {
/* 48 */                this.mParam.height = measureUsingView.getMeasuredHeight();
                    }
/* 50 */            this.mTargetWidth = (int) (((double) this.mParam.width) * targetZoomX);
/* 51 */            this.mTargetHeight = (int) (((double) this.mParam.height) * targetZoomY);
/* 52 */            this.mOriginalWidth = this.mParam.width;
/* 53 */            this.mOriginalHeight = this.mParam.height;
/* 54 */            this.mDeltaWidth = this.mTargetWidth - this.mParam.width;
/* 55 */            this.mDeltaHeight = this.mTargetHeight - this.mParam.height;
/* 56 */            this.mListener = listener;
/* 57 */            this.mTag = tag;
/* 58 */            this.mId = id;
                }

                @Override // co.vine.android.animation.SmoothAnimator
/* 62 */        public void onUpdate(float frac, float smoothFrac) {
/* 63 */            float f = 1.0f - smoothFrac;
/* 64 */            this.mParam.leftMargin = this.mOriginalX - ((int) (this.mDeltaX * smoothFrac));
/* 65 */            this.mParam.topMargin = this.mOriginalY - ((int) (this.mDeltaY * smoothFrac));
/* 66 */            if (this.mTargetWidth != this.mParam.width) {
/* 67 */                this.mParam.width = (int) (this.mOriginalWidth + (this.mDeltaWidth * smoothFrac));
                    }
/* 69 */            if (this.mTargetHeight != this.mParam.height) {
/* 70 */                this.mParam.height = (int) (this.mOriginalHeight + (this.mDeltaHeight * smoothFrac));
                    }
/* 72 */            SLog.m453d("Modifying {}: {} {} {} {}", new Object[]{this.mTarget, Integer.valueOf(this.mParam.leftMargin), Integer.valueOf(this.mParam.topMargin), Integer.valueOf(this.mParam.width), Integer.valueOf(this.mParam.height)});
/* 75 */            this.mTarget.setLayoutParams(this.mParam);
                }

                @Override // co.vine.android.animation.SmoothAnimator
/* 79 */        public void onStop() {
/* 80 */            if (this.mListener != null) {
/* 81 */                this.mListener.onAnimationFinish(this.mId, this.mTag);
                    }
                }
            }
