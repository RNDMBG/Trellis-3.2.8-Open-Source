            package android.support.v4.widget;

            import android.R;
            import android.content.Context;
            import android.content.res.Resources;
            import android.content.res.TypedArray;
            import android.graphics.Canvas;
            import android.os.Build;
            import android.support.v4.view.ViewCompat;
            import android.util.AttributeSet;
            import android.util.DisplayMetrics;
            import android.view.MotionEvent;
            import android.view.View;
            import android.view.ViewConfiguration;
            import android.view.ViewGroup;
            import android.view.animation.AccelerateInterpolator;
            import android.view.animation.Animation;
            import android.view.animation.DecelerateInterpolator;
            import android.view.animation.Transformation;
            import android.widget.AbsListView;
            
            
    public class SwipeRefreshLayout extends ViewGroup {
                private static final float ACCELERATE_INTERPOLATION_FACTOR = 1.5f;
                private static final float DECELERATE_INTERPOLATION_FACTOR = 2.0f;
                private static final int[] LAYOUT_ATTRS = {R.attr.enabled};
                private static final float MAX_SWIPE_DISTANCE_FACTOR = 0.6f;
                private static final float PROGRESS_BAR_HEIGHT = 4.0f;
                private static final int REFRESH_TRIGGER_DISTANCE = 120;
                private static final long RETURN_TO_ORIGINAL_POSITION_TIMEOUT = 300;
                private final AccelerateInterpolator mAccelerateInterpolator;
                private final Animation mAnimateToStartPosition;
                private final Runnable mCancel;
                private float mCurrPercentage;
                private int mCurrentTargetOffsetTop;
                private final DecelerateInterpolator mDecelerateInterpolator;
                private float mDistanceToTriggerSync;
                private MotionEvent mDownEvent;
                private int mFrom;
                private float mFromPercentage;
                private OnRefreshListener mListener;
                private int mMediumAnimationDuration;
                private int mOriginalOffsetTop;
                private float mPrevY;
                private SwipeProgressBar mProgressBar;
                private int mProgressBarHeight;
                private boolean mRefreshing;
                private final Runnable mReturnToStartPosition;
                private final Animation.AnimationListener mReturnToStartPositionListener;
                private boolean mReturningToStart;
                private final Animation.AnimationListener mShrinkAnimationListener;
                private Animation mShrinkTrigger;
                private View mTarget;
                private int mTouchSlop;

                public interface OnRefreshListener {
                    void onRefresh();
                }

        static  int access$000(SwipeRefreshLayout x0) {
            return x0.mFrom;
                }

        static  int access$100(SwipeRefreshLayout x0) {
            return x0.mOriginalOffsetTop;
                }

        static  Animation.AnimationListener access$1000(SwipeRefreshLayout x0) {
            return x0.mReturnToStartPositionListener;
                }

        static  void access$1100(SwipeRefreshLayout x0, int x1, Animation.AnimationListener x2) {
            x0.animateOffsetToStartPosition(x1, x2);
                }

        static  int access$1200(SwipeRefreshLayout x0) {
            return x0.mMediumAnimationDuration;
                }

        static  Animation access$1300(SwipeRefreshLayout x0) {
            return x0.mShrinkTrigger;
                }

        static  Animation.AnimationListener access$1400(SwipeRefreshLayout x0) {
            return x0.mShrinkAnimationListener;
                }

        static  DecelerateInterpolator access$1500(SwipeRefreshLayout x0) {
            return x0.mDecelerateInterpolator;
                }

        static  View access$200(SwipeRefreshLayout x0) {
            return x0.mTarget;
                }

        static  void access$300(SwipeRefreshLayout x0, int x1) {
            x0.setTargetOffsetTopAndBottom(x1);
                }

        static  float access$400(SwipeRefreshLayout x0) {
            return x0.mFromPercentage;
                }

        static  float access$402(SwipeRefreshLayout x0, float x1) {
            x0.mFromPercentage = x1;
                    return x1;
                }

        static  SwipeProgressBar access$500(SwipeRefreshLayout x0) {
            return x0.mProgressBar;
                }

        static  int access$700(SwipeRefreshLayout x0) {
            return x0.mCurrentTargetOffsetTop;
                }

        static  int access$702(SwipeRefreshLayout x0, int x1) {
            x0.mCurrentTargetOffsetTop = x1;
                    return x1;
                }

        static  float access$800(SwipeRefreshLayout x0) {
            return x0.mCurrPercentage;
                }

        static  float access$802(SwipeRefreshLayout x0, float x1) {
            x0.mCurrPercentage = x1;
                    return x1;
                }

        static  boolean access$902(SwipeRefreshLayout x0, boolean x1) {
            x0.mReturningToStart = x1;
                    return x1;
                }

                
        class C00741 extends Animation {
            C00741() {
                    }

                    @Override 
            public void applyTransformation(float interpolatedTime, Transformation t) {
                int targetTop = 0;
                if (SwipeRefreshLayout.access$000(SwipeRefreshLayout.this) != SwipeRefreshLayout.access$100(SwipeRefreshLayout.this)) {
                    targetTop = SwipeRefreshLayout.access$000(SwipeRefreshLayout.this) + ((int) ((SwipeRefreshLayout.access$100(SwipeRefreshLayout.this) - SwipeRefreshLayout.access$000(SwipeRefreshLayout.this)) * interpolatedTime));
                        }
                int offset = targetTop - SwipeRefreshLayout.access$200(SwipeRefreshLayout.this).getTop();
                int currentTop = SwipeRefreshLayout.access$200(SwipeRefreshLayout.this).getTop();
                if (offset + currentTop < 0) {
                   offset = 0 - currentTop;
                        }
               SwipeRefreshLayout.access$300(SwipeRefreshLayout.this, offset);
                    }
                }

                
       class C00752 extends Animation {
           C00752() {
                    }

                    @Override 
           public void applyTransformation(float interpolatedTime, Transformation t) {
               float percent = SwipeRefreshLayout.access$400(SwipeRefreshLayout.this) + ((0.0f - SwipeRefreshLayout.access$400(SwipeRefreshLayout.this)) * interpolatedTime);
               SwipeRefreshLayout.access$500(SwipeRefreshLayout.this).setTriggerPercentage(percent);
                    }
                }

                
       class C00763 extends BaseAnimationListener {
           C00763() {
                        super(SwipeRefreshLayout.this, null);
                    }

                    @Override 
           public void onAnimationEnd(Animation animation) {
               SwipeRefreshLayout.access$702(SwipeRefreshLayout.this, 0);
                    }
                }

                
       class C00774 extends BaseAnimationListener {
           C00774() {
                        super(SwipeRefreshLayout.this, null);
                    }

                    @Override 
           public void onAnimationEnd(Animation animation) {
               SwipeRefreshLayout.access$802(SwipeRefreshLayout.this, 0.0f);
                    }
                }

                
       class RunnableC00785 implements Runnable {
           RunnableC00785() {
                    }

                    @Override 
           public void run() {
               SwipeRefreshLayout.access$902(SwipeRefreshLayout.this, true);
               SwipeRefreshLayout.access$1100(SwipeRefreshLayout.this, SwipeRefreshLayout.access$700(SwipeRefreshLayout.this) + SwipeRefreshLayout.this.getPaddingTop(), SwipeRefreshLayout.access$1000(SwipeRefreshLayout.this));
                    }
                }

                
       class RunnableC00796 implements Runnable {
           RunnableC00796() {
                    }

                    @Override 
           public void run() {
               SwipeRefreshLayout.access$902(SwipeRefreshLayout.this, true);
               if (SwipeRefreshLayout.access$500(SwipeRefreshLayout.this) != null) {
                   SwipeRefreshLayout.access$402(SwipeRefreshLayout.this, SwipeRefreshLayout.access$800(SwipeRefreshLayout.this));
                   SwipeRefreshLayout.access$1300(SwipeRefreshLayout.this).setDuration(SwipeRefreshLayout.access$1200(SwipeRefreshLayout.this));
                   SwipeRefreshLayout.access$1300(SwipeRefreshLayout.this).setAnimationListener(SwipeRefreshLayout.access$1400(SwipeRefreshLayout.this));
                   SwipeRefreshLayout.access$1300(SwipeRefreshLayout.this).reset();
                   SwipeRefreshLayout.access$1300(SwipeRefreshLayout.this).setInterpolator(SwipeRefreshLayout.access$1500(SwipeRefreshLayout.this));
                   SwipeRefreshLayout.this.startAnimation(SwipeRefreshLayout.access$1300(SwipeRefreshLayout.this));
                        }
               SwipeRefreshLayout.access$1100(SwipeRefreshLayout.this, SwipeRefreshLayout.access$700(SwipeRefreshLayout.this) + SwipeRefreshLayout.this.getPaddingTop(), SwipeRefreshLayout.access$1000(SwipeRefreshLayout.this));
                    }
                }

       public SwipeRefreshLayout(Context context) {
           this(context, null);
                }

       public SwipeRefreshLayout(Context context, AttributeSet attrs) {
           super(context, attrs);
            this.mRefreshing = false;
            this.mDistanceToTriggerSync = -1.0f;
            this.mFromPercentage = 0.0f;
            this.mCurrPercentage = 0.0f;
            this.mAnimateToStartPosition = new C00741();
           this.mShrinkTrigger = new C00752();
           this.mReturnToStartPositionListener = new C00763();
           this.mShrinkAnimationListener = new C00774();
           this.mReturnToStartPosition = new RunnableC00785();
           this.mCancel = new RunnableC00796();
           this.mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
           this.mMediumAnimationDuration = getResources().getInteger(R.integer.config_mediumAnimTime);
           setWillNotDraw(false);
           this.mProgressBar = new SwipeProgressBar(this);
           DisplayMetrics metrics = getResources().getDisplayMetrics();
           this.mProgressBarHeight = (int) (metrics.density * 4.0f);
           this.mDecelerateInterpolator = new DecelerateInterpolator(DECELERATE_INTERPOLATION_FACTOR);
           this.mAccelerateInterpolator = new AccelerateInterpolator(ACCELERATE_INTERPOLATION_FACTOR);
           TypedArray a = context.obtainStyledAttributes(attrs, LAYOUT_ATTRS);
           setEnabled(a.getBoolean(0, true));
           a.recycle();
                }

                @Override 
       public void onAttachedToWindow() {
           super.onAttachedToWindow();
           removeCallbacks(this.mCancel);
           removeCallbacks(this.mReturnToStartPosition);
                }

                @Override 
       public void onDetachedFromWindow() {
           super.onDetachedFromWindow();
           removeCallbacks(this.mReturnToStartPosition);
           removeCallbacks(this.mCancel);
                }

       private void animateOffsetToStartPosition(int from, Animation.AnimationListener listener) {
           this.mFrom = from;
           this.mAnimateToStartPosition.reset();
           this.mAnimateToStartPosition.setDuration(this.mMediumAnimationDuration);
           this.mAnimateToStartPosition.setAnimationListener(listener);
           this.mAnimateToStartPosition.setInterpolator(this.mDecelerateInterpolator);
           this.mTarget.startAnimation(this.mAnimateToStartPosition);
                }

       public void setOnRefreshListener(OnRefreshListener listener) {
           this.mListener = listener;
                }

       private void setTriggerPercentage(float percent) {
                    if (percent == 0.0f) {
               this.mCurrPercentage = 0.0f;
                    } else {
               this.mCurrPercentage = percent;
               this.mProgressBar.setTriggerPercentage(percent);
                    }
                }

       public void setRefreshing(boolean refreshing) {
           if (this.mRefreshing != refreshing) {
               ensureTarget();
               this.mCurrPercentage = 0.0f;
               this.mRefreshing = refreshing;
               if (this.mRefreshing) {
                   this.mProgressBar.start();
                        } else {
                   this.mProgressBar.stop();
                        }
                    }
                }

       public void setColorScheme(int colorRes1, int colorRes2, int colorRes3, int colorRes4) {
           ensureTarget();
           Resources res = getResources();
           int color1 = res.getColor(colorRes1);
           int color2 = res.getColor(colorRes2);
           int color3 = res.getColor(colorRes3);
           int color4 = res.getColor(colorRes4);
           this.mProgressBar.setColorScheme(color1, color2, color3, color4);
                }

       public boolean isRefreshing() {
           return this.mRefreshing;
                }

       private void ensureTarget() {
           if (this.mTarget == null) {
               if (getChildCount() > 1 && !isInEditMode()) {
                   throw new IllegalStateException("SwipeRefreshLayout can host only one direct child");
                        }
               this.mTarget = getChildAt(0);
               this.mOriginalOffsetTop = this.mTarget.getTop() + getPaddingTop();
                    }
           if (this.mDistanceToTriggerSync == -1.0f && getParent() != null && ((View) getParent()).getHeight() > 0) {
               DisplayMetrics metrics = getResources().getDisplayMetrics();
               this.mDistanceToTriggerSync = (int) Math.min(((View) getParent()).getHeight() * MAX_SWIPE_DISTANCE_FACTOR, 120.0f * metrics.density);
                    }
                }

                @Override 
       public void draw(Canvas canvas) {
           super.draw(canvas);
           this.mProgressBar.draw(canvas);
                }

                @Override 
       protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
           int width = getMeasuredWidth();
           int height = getMeasuredHeight();
           this.mProgressBar.setBounds(0, 0, width, this.mProgressBarHeight);
           if (getChildCount() != 0) {
               View child = getChildAt(0);
               int childLeft = getPaddingLeft();
               int childTop = this.mCurrentTargetOffsetTop + getPaddingTop();
               int childWidth = (width - getPaddingLeft()) - getPaddingRight();
               int childHeight = (height - getPaddingTop()) - getPaddingBottom();
               child.layout(childLeft, childTop, childLeft + childWidth, childTop + childHeight);
                    }
                }

                @Override 
       public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
           super.onMeasure(widthMeasureSpec, heightMeasureSpec);
           if (getChildCount() > 1 && !isInEditMode()) {
               throw new IllegalStateException("SwipeRefreshLayout can host only one direct child");
                    }
           if (getChildCount() > 0) {
               getChildAt(0).measure(View.MeasureSpec.makeMeasureSpec((getMeasuredWidth() - getPaddingLeft()) - getPaddingRight(), 1073741824), View.MeasureSpec.makeMeasureSpec((getMeasuredHeight() - getPaddingTop()) - getPaddingBottom(), 1073741824));
                    }
                }

       public boolean canChildScrollUp() {
           if (Build.VERSION.SDK_INT < 14) {
               if (!(this.mTarget instanceof AbsListView)) {
                            return this.mTarget.getScrollY() > 0;
                        }
               AbsListView absListView = (AbsListView) this.mTarget;
                        return absListView.getChildCount() > 0 && (absListView.getFirstVisiblePosition() > 0 || absListView.getChildAt(0).getTop() < absListView.getPaddingTop());
                    }
           return ViewCompat.canScrollVertically(this.mTarget, -1);
                }

                @Override 
       public boolean onInterceptTouchEvent(MotionEvent ev) {
           ensureTarget();
           boolean handled = false;
           if (this.mReturningToStart && ev.getAction() == 0) {
               this.mReturningToStart = false;
                    }
           if (isEnabled() && !this.mReturningToStart && !canChildScrollUp()) {
               handled = onTouchEvent(ev);
                    }
                    return !handled ? super.onInterceptTouchEvent(ev) : handled;
                }

                @Override 
       public void requestDisallowInterceptTouchEvent(boolean b) {
                }

                @Override 
       public boolean onTouchEvent(MotionEvent event) {
           int action = event.getAction();
                    switch (action) {
                        case 0:
                   this.mCurrPercentage = 0.0f;
                   this.mDownEvent = MotionEvent.obtain(event);
                   this.mPrevY = this.mDownEvent.getY();
                   return false;
                        case 1:
                        case 3:
                   if (this.mDownEvent == null) {
                       return false;
                            }
                   this.mDownEvent.recycle();
                   this.mDownEvent = null;
                   return false;
                        case 2:
                   if (this.mDownEvent == null || this.mReturningToStart) {
                       return false;
                            }
                   float eventY = event.getY();
                   float yDiff = eventY - this.mDownEvent.getY();
                            if (yDiff <= this.mTouchSlop) {
                       return false;
                            }
                            if (yDiff > this.mDistanceToTriggerSync) {
                       startRefresh();
                       return true;
                            }
                   setTriggerPercentage(this.mAccelerateInterpolator.getInterpolation(yDiff / this.mDistanceToTriggerSync));
                   float offsetTop = yDiff;
                            if (this.mPrevY > eventY) {
                       offsetTop = yDiff - this.mTouchSlop;
                            }
                   updateContentOffsetTop((int) offsetTop);
                            if (this.mPrevY > eventY && this.mTarget.getTop() < this.mTouchSlop) {
                       removeCallbacks(this.mCancel);
                            } else {
                       updatePositionTimeout();
                            }
                   this.mPrevY = event.getY();
                   return true;
                        default:
                   return false;
                    }
                }

       private void startRefresh() {
           removeCallbacks(this.mCancel);
           this.mReturnToStartPosition.run();
           setRefreshing(true);
           this.mListener.onRefresh();
                }

       private void updateContentOffsetTop(int targetTop) {
           int currentTop = this.mTarget.getTop();
                    if (targetTop > this.mDistanceToTriggerSync) {
               targetTop = (int) this.mDistanceToTriggerSync;
                    } else if (targetTop < 0) {
               targetTop = 0;
                    }
           setTargetOffsetTopAndBottom(targetTop - currentTop);
                }

       private void setTargetOffsetTopAndBottom(int offset) {
           this.mTarget.offsetTopAndBottom(offset);
           this.mCurrentTargetOffsetTop = this.mTarget.getTop();
                }

       private void updatePositionTimeout() {
           removeCallbacks(this.mCancel);
           postDelayed(this.mCancel, 300L);
                }

       private class BaseAnimationListener implements Animation.AnimationListener {
           private BaseAnimationListener() {
                    }

            BaseAnimationListener(SwipeRefreshLayout x0, C00741 x1) {
               this();
                    }

                    @Override 
           public void onAnimationStart(Animation animation) {
                    }

                    @Override 
           public void onAnimationEnd(Animation animation) {
                    }

                    @Override 
           public void onAnimationRepeat(Animation animation) {
                    }
                }
            }
