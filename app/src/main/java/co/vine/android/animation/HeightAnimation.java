            package co.vine.android.animation;

            import android.app.Activity;
            import android.util.DisplayMetrics;
            import android.view.View;
            import android.view.ViewGroup;
            import android.view.animation.Animation;
            import android.view.animation.Transformation;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 18 */    public class HeightAnimation extends Animation {
                private final View mAnimatedView;
                private final int mFinalHeight;
                private final boolean mIsExpansion;

/* 19 */        public HeightAnimation(Activity activity, View view, boolean isExpansion, long duration) {
/* 20 */            this(activity, view, isExpansion, duration, -1);
                }

/* 22 */        public HeightAnimation(Activity activity, View view, boolean isExpansion, long duration, int finalHeight) {
/* 24 */            DisplayMetrics metrics = new DisplayMetrics();
/* 25 */            activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
/* 26 */            this.mAnimatedView = view;
/* 27 */            this.mIsExpansion = isExpansion;
/* 28 */            setDuration(duration);
/* 29 */            int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, 0);
/* 30 */            int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(metrics.widthPixels, 1073741824);
/* 31 */            this.mAnimatedView.measure(widthMeasureSpec, heightMeasureSpec);
/* 32 */            if (finalHeight == -1) {
/* 33 */                this.mFinalHeight = this.mAnimatedView.getMeasuredHeight();
                    } else {
/* 35 */                this.mFinalHeight = finalHeight;
                    }
/* 37 */            if (this.mIsExpansion) {
/* 38 */                this.mAnimatedView.getLayoutParams().height = 0;
/* 39 */                this.mAnimatedView.setVisibility(0);
                    }
                }

                @Override // android.view.animation.Animation
/* 44 */        protected void applyTransformation(float interpolatedTime, Transformation t) {
/* 45 */            super.applyTransformation(interpolatedTime, t);
/* 46 */            ViewGroup.LayoutParams params = this.mAnimatedView.getLayoutParams();
/* 47 */            if (params != null) {
                        if (interpolatedTime < 1.0f) {
/* 49 */                    if (this.mIsExpansion) {
/* 50 */                        params.height = (int) (this.mFinalHeight * interpolatedTime);
                            } else {
/* 52 */                        params.height = this.mFinalHeight - ((int) (this.mFinalHeight * interpolatedTime));
                            }
/* 54 */                    this.mAnimatedView.requestLayout();
/* 66 */                    return;
                        }
/* 56 */                if (this.mIsExpansion) {
/* 57 */                    params.height = this.mFinalHeight;
/* 58 */                    this.mAnimatedView.requestLayout();
                        } else {
/* 60 */                    params.height = 0;
/* 61 */                    this.mAnimatedView.setVisibility(8);
/* 62 */                    params.height = this.mFinalHeight;
                        }
                    }
                }
            }
