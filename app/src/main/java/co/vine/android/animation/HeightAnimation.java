            package co.vine.android.animation;

            import android.app.Activity;
            import android.util.DisplayMetrics;
            import android.view.View;
            import android.view.ViewGroup;
            import android.view.animation.Animation;
            import android.view.animation.Transformation;
            
            
    public class HeightAnimation extends Animation {
                private final View mAnimatedView;
                private final int mFinalHeight;
                private final boolean mIsExpansion;

        public HeightAnimation(Activity activity, View view, boolean isExpansion, long duration) {
            this(activity, view, isExpansion, duration, -1);
                }

        public HeightAnimation(Activity activity, View view, boolean isExpansion, long duration, int finalHeight) {
            DisplayMetrics metrics = new DisplayMetrics();
            activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
            this.mAnimatedView = view;
            this.mIsExpansion = isExpansion;
            setDuration(duration);
            int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, 0);
            int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(metrics.widthPixels, 1073741824);
            this.mAnimatedView.measure(widthMeasureSpec, heightMeasureSpec);
            if (finalHeight == -1) {
                this.mFinalHeight = this.mAnimatedView.getMeasuredHeight();
                    } else {
                this.mFinalHeight = finalHeight;
                    }
            if (this.mIsExpansion) {
                this.mAnimatedView.getLayoutParams().height = 0;
                this.mAnimatedView.setVisibility(0);
                    }
                }

                @Override 
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            super.applyTransformation(interpolatedTime, t);
            ViewGroup.LayoutParams params = this.mAnimatedView.getLayoutParams();
            if (params != null) {
                        if (interpolatedTime < 1.0f) {
                    if (this.mIsExpansion) {
                        params.height = (int) (this.mFinalHeight * interpolatedTime);
                            } else {
                        params.height = this.mFinalHeight - ((int) (this.mFinalHeight * interpolatedTime));
                            }
                    this.mAnimatedView.requestLayout();
                    return;
                        }
                if (this.mIsExpansion) {
                    params.height = this.mFinalHeight;
                    this.mAnimatedView.requestLayout();
                        } else {
                    params.height = 0;
                    this.mAnimatedView.setVisibility(8);
                    params.height = this.mFinalHeight;
                        }
                    }
                }
            }
