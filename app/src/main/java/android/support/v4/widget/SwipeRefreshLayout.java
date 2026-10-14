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
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 56 */    public class SwipeRefreshLayout extends ViewGroup {
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

/* 57 */        static /* synthetic */ int access$000(SwipeRefreshLayout x0) {
/* 58 */            return x0.mFrom;
                }

/* 57 */        static /* synthetic */ int access$100(SwipeRefreshLayout x0) {
/* 58 */            return x0.mOriginalOffsetTop;
                }

/* 57 */        static /* synthetic */ Animation.AnimationListener access$1000(SwipeRefreshLayout x0) {
/* 58 */            return x0.mReturnToStartPositionListener;
                }

/* 57 */        static /* synthetic */ void access$1100(SwipeRefreshLayout x0, int x1, Animation.AnimationListener x2) {
/* 58 */            x0.animateOffsetToStartPosition(x1, x2);
                }

/* 57 */        static /* synthetic */ int access$1200(SwipeRefreshLayout x0) {
/* 58 */            return x0.mMediumAnimationDuration;
                }

/* 57 */        static /* synthetic */ Animation access$1300(SwipeRefreshLayout x0) {
/* 58 */            return x0.mShrinkTrigger;
                }

/* 57 */        static /* synthetic */ Animation.AnimationListener access$1400(SwipeRefreshLayout x0) {
/* 58 */            return x0.mShrinkAnimationListener;
                }

/* 57 */        static /* synthetic */ DecelerateInterpolator access$1500(SwipeRefreshLayout x0) {
/* 58 */            return x0.mDecelerateInterpolator;
                }

/* 57 */        static /* synthetic */ View access$200(SwipeRefreshLayout x0) {
/* 58 */            return x0.mTarget;
                }

/* 57 */        static /* synthetic */ void access$300(SwipeRefreshLayout x0, int x1) {
/* 58 */            x0.setTargetOffsetTopAndBottom(x1);
                }

/* 57 */        static /* synthetic */ float access$400(SwipeRefreshLayout x0) {
/* 58 */            return x0.mFromPercentage;
                }

/* 57 */        static /* synthetic */ float access$402(SwipeRefreshLayout x0, float x1) {
/* 58 */            x0.mFromPercentage = x1;
                    return x1;
                }

/* 57 */        static /* synthetic */ SwipeProgressBar access$500(SwipeRefreshLayout x0) {
/* 58 */            return x0.mProgressBar;
                }

/* 57 */        static /* synthetic */ int access$700(SwipeRefreshLayout x0) {
/* 58 */            return x0.mCurrentTargetOffsetTop;
                }

/* 57 */        static /* synthetic */ int access$702(SwipeRefreshLayout x0, int x1) {
/* 58 */            x0.mCurrentTargetOffsetTop = x1;
                    return x1;
                }

/* 57 */        static /* synthetic */ float access$800(SwipeRefreshLayout x0) {
/* 58 */            return x0.mCurrPercentage;
                }

/* 57 */        static /* synthetic */ float access$802(SwipeRefreshLayout x0, float x1) {
/* 58 */            x0.mCurrPercentage = x1;
                    return x1;
                }

/* 57 */        static /* synthetic */ boolean access$902(SwipeRefreshLayout x0, boolean x1) {
/* 58 */            x0.mReturningToStart = x1;
                    return x1;
                }

                /* JADX INFO: renamed from: android.support.v4.widget.SwipeRefreshLayout$1 */
/* 88 */        class C00741 extends Animation {
/* 89 */            C00741() {
                    }

                    @Override // android.view.animation.Animation
/* 92 */            public void applyTransformation(float interpolatedTime, Transformation t) {
/* 93 */                int targetTop = 0;
/* 94 */                if (SwipeRefreshLayout.access$000(SwipeRefreshLayout.this) != SwipeRefreshLayout.access$100(SwipeRefreshLayout.this)) {
/* 95 */                    targetTop = SwipeRefreshLayout.access$000(SwipeRefreshLayout.this) + ((int) ((SwipeRefreshLayout.access$100(SwipeRefreshLayout.this) - SwipeRefreshLayout.access$000(SwipeRefreshLayout.this)) * interpolatedTime));
                        }
/* 97 */                int offset = targetTop - SwipeRefreshLayout.access$200(SwipeRefreshLayout.this).getTop();
/* 98 */                int currentTop = SwipeRefreshLayout.access$200(SwipeRefreshLayout.this).getTop();
/* 99 */                if (offset + currentTop < 0) {
/* 100 */                   offset = 0 - currentTop;
                        }
/* 102 */               SwipeRefreshLayout.access$300(SwipeRefreshLayout.this, offset);
                    }
                }

                /* JADX INFO: renamed from: android.support.v4.widget.SwipeRefreshLayout$2 */
/* 104 */       class C00752 extends Animation {
/* 105 */           C00752() {
                    }

                    @Override // android.view.animation.Animation
/* 108 */           public void applyTransformation(float interpolatedTime, Transformation t) {
/* 109 */               float percent = SwipeRefreshLayout.access$400(SwipeRefreshLayout.this) + ((0.0f - SwipeRefreshLayout.access$400(SwipeRefreshLayout.this)) * interpolatedTime);
/* 110 */               SwipeRefreshLayout.access$500(SwipeRefreshLayout.this).setTriggerPercentage(percent);
                    }
                }

                /* JADX INFO: renamed from: android.support.v4.widget.SwipeRefreshLayout$3 */
/* 112 */       class C00763 extends BaseAnimationListener {
/* 113 */           C00763() {
                        super(SwipeRefreshLayout.this, null);
                    }

                    @Override // android.support.v4.widget.SwipeRefreshLayout.BaseAnimationListener, android.view.animation.Animation.AnimationListener
/* 118 */           public void onAnimationEnd(Animation animation) {
/* 119 */               SwipeRefreshLayout.access$702(SwipeRefreshLayout.this, 0);
                    }
                }

                /* JADX INFO: renamed from: android.support.v4.widget.SwipeRefreshLayout$4 */
/* 121 */       class C00774 extends BaseAnimationListener {
/* 122 */           C00774() {
                        super(SwipeRefreshLayout.this, null);
                    }

                    @Override // android.support.v4.widget.SwipeRefreshLayout.BaseAnimationListener, android.view.animation.Animation.AnimationListener
/* 125 */           public void onAnimationEnd(Animation animation) {
/* 126 */               SwipeRefreshLayout.access$802(SwipeRefreshLayout.this, 0.0f);
                    }
                }

                /* JADX INFO: renamed from: android.support.v4.widget.SwipeRefreshLayout$5 */
/* 128 */       class RunnableC00785 implements Runnable {
/* 129 */           RunnableC00785() {
                    }

                    @Override // java.lang.Runnable
/* 133 */           public void run() {
/* 134 */               SwipeRefreshLayout.access$902(SwipeRefreshLayout.this, true);
/* 135 */               SwipeRefreshLayout.access$1100(SwipeRefreshLayout.this, SwipeRefreshLayout.access$700(SwipeRefreshLayout.this) + SwipeRefreshLayout.this.getPaddingTop(), SwipeRefreshLayout.access$1000(SwipeRefreshLayout.this));
                    }
                }

                /* JADX INFO: renamed from: android.support.v4.widget.SwipeRefreshLayout$6 */
/* 140 */       class RunnableC00796 implements Runnable {
/* 141 */           RunnableC00796() {
                    }

                    @Override // java.lang.Runnable
/* 145 */           public void run() {
/* 146 */               SwipeRefreshLayout.access$902(SwipeRefreshLayout.this, true);
/* 149 */               if (SwipeRefreshLayout.access$500(SwipeRefreshLayout.this) != null) {
/* 150 */                   SwipeRefreshLayout.access$402(SwipeRefreshLayout.this, SwipeRefreshLayout.access$800(SwipeRefreshLayout.this));
/* 151 */                   SwipeRefreshLayout.access$1300(SwipeRefreshLayout.this).setDuration(SwipeRefreshLayout.access$1200(SwipeRefreshLayout.this));
/* 152 */                   SwipeRefreshLayout.access$1300(SwipeRefreshLayout.this).setAnimationListener(SwipeRefreshLayout.access$1400(SwipeRefreshLayout.this));
/* 153 */                   SwipeRefreshLayout.access$1300(SwipeRefreshLayout.this).reset();
/* 154 */                   SwipeRefreshLayout.access$1300(SwipeRefreshLayout.this).setInterpolator(SwipeRefreshLayout.access$1500(SwipeRefreshLayout.this));
/* 155 */                   SwipeRefreshLayout.this.startAnimation(SwipeRefreshLayout.access$1300(SwipeRefreshLayout.this));
                        }
/* 157 */               SwipeRefreshLayout.access$1100(SwipeRefreshLayout.this, SwipeRefreshLayout.access$700(SwipeRefreshLayout.this) + SwipeRefreshLayout.this.getPaddingTop(), SwipeRefreshLayout.access$1000(SwipeRefreshLayout.this));
                    }
                }

/* 167 */       public SwipeRefreshLayout(Context context) {
/* 168 */           this(context, null);
                }

/* 176 */       public SwipeRefreshLayout(Context context, AttributeSet attrs) {
/* 177 */           super(context, attrs);
/* 72 */            this.mRefreshing = false;
/* 74 */            this.mDistanceToTriggerSync = -1.0f;
/* 77 */            this.mFromPercentage = 0.0f;
/* 78 */            this.mCurrPercentage = 0.0f;
/* 90 */            this.mAnimateToStartPosition = new C00741();
/* 106 */           this.mShrinkTrigger = new C00752();
/* 114 */           this.mReturnToStartPositionListener = new C00763();
/* 123 */           this.mShrinkAnimationListener = new C00774();
/* 130 */           this.mReturnToStartPosition = new RunnableC00785();
/* 142 */           this.mCancel = new RunnableC00796();
/* 179 */           this.mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
/* 181 */           this.mMediumAnimationDuration = getResources().getInteger(R.integer.config_mediumAnimTime);
/* 184 */           setWillNotDraw(false);
/* 185 */           this.mProgressBar = new SwipeProgressBar(this);
/* 186 */           DisplayMetrics metrics = getResources().getDisplayMetrics();
/* 187 */           this.mProgressBarHeight = (int) (metrics.density * 4.0f);
/* 188 */           this.mDecelerateInterpolator = new DecelerateInterpolator(DECELERATE_INTERPOLATION_FACTOR);
/* 189 */           this.mAccelerateInterpolator = new AccelerateInterpolator(ACCELERATE_INTERPOLATION_FACTOR);
/* 191 */           TypedArray a = context.obtainStyledAttributes(attrs, LAYOUT_ATTRS);
/* 192 */           setEnabled(a.getBoolean(0, true));
/* 193 */           a.recycle();
                }

                @Override // android.view.ViewGroup, android.view.View
/* 197 */       public void onAttachedToWindow() {
/* 198 */           super.onAttachedToWindow();
/* 199 */           removeCallbacks(this.mCancel);
/* 200 */           removeCallbacks(this.mReturnToStartPosition);
                }

                @Override // android.view.ViewGroup, android.view.View
/* 204 */       public void onDetachedFromWindow() {
/* 205 */           super.onDetachedFromWindow();
/* 206 */           removeCallbacks(this.mReturnToStartPosition);
/* 207 */           removeCallbacks(this.mCancel);
                }

/* 210 */       private void animateOffsetToStartPosition(int from, Animation.AnimationListener listener) {
/* 211 */           this.mFrom = from;
/* 212 */           this.mAnimateToStartPosition.reset();
/* 213 */           this.mAnimateToStartPosition.setDuration(this.mMediumAnimationDuration);
/* 214 */           this.mAnimateToStartPosition.setAnimationListener(listener);
/* 215 */           this.mAnimateToStartPosition.setInterpolator(this.mDecelerateInterpolator);
/* 216 */           this.mTarget.startAnimation(this.mAnimateToStartPosition);
                }

/* 223 */       public void setOnRefreshListener(OnRefreshListener listener) {
/* 224 */           this.mListener = listener;
                }

/* 227 */       private void setTriggerPercentage(float percent) {
                    if (percent == 0.0f) {
/* 231 */               this.mCurrPercentage = 0.0f;
                    } else {
/* 234 */               this.mCurrPercentage = percent;
/* 235 */               this.mProgressBar.setTriggerPercentage(percent);
                    }
                }

/* 244 */       public void setRefreshing(boolean refreshing) {
/* 245 */           if (this.mRefreshing != refreshing) {
/* 246 */               ensureTarget();
/* 247 */               this.mCurrPercentage = 0.0f;
/* 248 */               this.mRefreshing = refreshing;
/* 249 */               if (this.mRefreshing) {
/* 250 */                   this.mProgressBar.start();
                        } else {
/* 252 */                   this.mProgressBar.stop();
                        }
                    }
                }

/* 267 */       public void setColorScheme(int colorRes1, int colorRes2, int colorRes3, int colorRes4) {
/* 268 */           ensureTarget();
/* 269 */           Resources res = getResources();
/* 270 */           int color1 = res.getColor(colorRes1);
/* 271 */           int color2 = res.getColor(colorRes2);
/* 272 */           int color3 = res.getColor(colorRes3);
/* 273 */           int color4 = res.getColor(colorRes4);
/* 274 */           this.mProgressBar.setColorScheme(color1, color2, color3, color4);
                }

/* 281 */       public boolean isRefreshing() {
/* 282 */           return this.mRefreshing;
                }

/* 286 */       private void ensureTarget() {
/* 287 */           if (this.mTarget == null) {
/* 288 */               if (getChildCount() > 1 && !isInEditMode()) {
/* 289 */                   throw new IllegalStateException("SwipeRefreshLayout can host only one direct child");
                        }
/* 292 */               this.mTarget = getChildAt(0);
/* 293 */               this.mOriginalOffsetTop = this.mTarget.getTop() + getPaddingTop();
                    }
/* 296 */           if (this.mDistanceToTriggerSync == -1.0f && getParent() != null && ((View) getParent()).getHeight() > 0) {
/* 297 */               DisplayMetrics metrics = getResources().getDisplayMetrics();
/* 298 */               this.mDistanceToTriggerSync = (int) Math.min(((View) getParent()).getHeight() * MAX_SWIPE_DISTANCE_FACTOR, 120.0f * metrics.density);
                    }
                }

                @Override // android.view.View
/* 306 */       public void draw(Canvas canvas) {
/* 307 */           super.draw(canvas);
/* 308 */           this.mProgressBar.draw(canvas);
                }

                @Override // android.view.ViewGroup, android.view.View
/* 312 */       protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
/* 313 */           int width = getMeasuredWidth();
/* 314 */           int height = getMeasuredHeight();
/* 315 */           this.mProgressBar.setBounds(0, 0, width, this.mProgressBarHeight);
/* 316 */           if (getChildCount() != 0) {
/* 319 */               View child = getChildAt(0);
/* 320 */               int childLeft = getPaddingLeft();
/* 321 */               int childTop = this.mCurrentTargetOffsetTop + getPaddingTop();
/* 322 */               int childWidth = (width - getPaddingLeft()) - getPaddingRight();
/* 323 */               int childHeight = (height - getPaddingTop()) - getPaddingBottom();
/* 324 */               child.layout(childLeft, childTop, childLeft + childWidth, childTop + childHeight);
                    }
                }

                @Override // android.view.View
/* 328 */       public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
/* 329 */           super.onMeasure(widthMeasureSpec, heightMeasureSpec);
/* 330 */           if (getChildCount() > 1 && !isInEditMode()) {
/* 331 */               throw new IllegalStateException("SwipeRefreshLayout can host only one direct child");
                    }
/* 333 */           if (getChildCount() > 0) {
/* 334 */               getChildAt(0).measure(View.MeasureSpec.makeMeasureSpec((getMeasuredWidth() - getPaddingLeft()) - getPaddingRight(), 1073741824), View.MeasureSpec.makeMeasureSpec((getMeasuredHeight() - getPaddingTop()) - getPaddingBottom(), 1073741824));
                    }
                }

/* 348 */       public boolean canChildScrollUp() {
/* 349 */           if (Build.VERSION.SDK_INT < 14) {
/* 350 */               if (!(this.mTarget instanceof AbsListView)) {
                            return this.mTarget.getScrollY() > 0;
                        }
/* 351 */               AbsListView absListView = (AbsListView) this.mTarget;
                        return absListView.getChildCount() > 0 && (absListView.getFirstVisiblePosition() > 0 || absListView.getChildAt(0).getTop() < absListView.getPaddingTop());
                    }
/* 359 */           return ViewCompat.canScrollVertically(this.mTarget, -1);
                }

                @Override // android.view.ViewGroup
/* 364 */       public boolean onInterceptTouchEvent(MotionEvent ev) {
/* 365 */           ensureTarget();
/* 366 */           boolean handled = false;
/* 367 */           if (this.mReturningToStart && ev.getAction() == 0) {
/* 368 */               this.mReturningToStart = false;
                    }
/* 370 */           if (isEnabled() && !this.mReturningToStart && !canChildScrollUp()) {
/* 371 */               handled = onTouchEvent(ev);
                    }
                    return !handled ? super.onInterceptTouchEvent(ev) : handled;
                }

                @Override // android.view.ViewGroup, android.view.ViewParent
/* 378 */       public void requestDisallowInterceptTouchEvent(boolean b) {
                }

                @Override // android.view.View
/* 382 */       public boolean onTouchEvent(MotionEvent event) {
/* 383 */           int action = event.getAction();
                    switch (action) {
                        case 0:
/* 387 */                   this.mCurrPercentage = 0.0f;
/* 388 */                   this.mDownEvent = MotionEvent.obtain(event);
/* 389 */                   this.mPrevY = this.mDownEvent.getY();
/* 384 */                   return false;
                        case 1:
                        case 3:
/* 428 */                   if (this.mDownEvent == null) {
/* 384 */                       return false;
                            }
/* 429 */                   this.mDownEvent.recycle();
/* 430 */                   this.mDownEvent = null;
/* 384 */                   return false;
                        case 2:
/* 392 */                   if (this.mDownEvent == null || this.mReturningToStart) {
/* 384 */                       return false;
                            }
/* 393 */                   float eventY = event.getY();
/* 394 */                   float yDiff = eventY - this.mDownEvent.getY();
                            if (yDiff <= this.mTouchSlop) {
/* 384 */                       return false;
                            }
                            if (yDiff > this.mDistanceToTriggerSync) {
/* 399 */                       startRefresh();
/* 400 */                       return true;
                            }
/* 404 */                   setTriggerPercentage(this.mAccelerateInterpolator.getInterpolation(yDiff / this.mDistanceToTriggerSync));
/* 407 */                   float offsetTop = yDiff;
                            if (this.mPrevY > eventY) {
/* 409 */                       offsetTop = yDiff - this.mTouchSlop;
                            }
/* 411 */                   updateContentOffsetTop((int) offsetTop);
                            if (this.mPrevY > eventY && this.mTarget.getTop() < this.mTouchSlop) {
/* 416 */                       removeCallbacks(this.mCancel);
                            } else {
/* 418 */                       updatePositionTimeout();
                            }
/* 420 */                   this.mPrevY = event.getY();
/* 421 */                   return true;
                        default:
/* 384 */                   return false;
                    }
                }

/* 437 */       private void startRefresh() {
/* 438 */           removeCallbacks(this.mCancel);
/* 439 */           this.mReturnToStartPosition.run();
/* 440 */           setRefreshing(true);
/* 441 */           this.mListener.onRefresh();
                }

/* 444 */       private void updateContentOffsetTop(int targetTop) {
/* 445 */           int currentTop = this.mTarget.getTop();
                    if (targetTop > this.mDistanceToTriggerSync) {
/* 447 */               targetTop = (int) this.mDistanceToTriggerSync;
                    } else if (targetTop < 0) {
/* 449 */               targetTop = 0;
                    }
/* 451 */           setTargetOffsetTopAndBottom(targetTop - currentTop);
                }

/* 454 */       private void setTargetOffsetTopAndBottom(int offset) {
/* 455 */           this.mTarget.offsetTopAndBottom(offset);
/* 456 */           this.mCurrentTargetOffsetTop = this.mTarget.getTop();
                }

/* 459 */       private void updatePositionTimeout() {
/* 460 */           removeCallbacks(this.mCancel);
/* 461 */           postDelayed(this.mCancel, 300L);
                }

/* 474 */       private class BaseAnimationListener implements Animation.AnimationListener {
/* 475 */           private BaseAnimationListener() {
                    }

/* 475 */           /* synthetic */ BaseAnimationListener(SwipeRefreshLayout x0, C00741 x1) {
/* 476 */               this();
                    }

                    @Override // android.view.animation.Animation.AnimationListener
/* 478 */           public void onAnimationStart(Animation animation) {
                    }

                    @Override // android.view.animation.Animation.AnimationListener
/* 482 */           public void onAnimationEnd(Animation animation) {
                    }

                    @Override // android.view.animation.Animation.AnimationListener
/* 486 */           public void onAnimationRepeat(Animation animation) {
                    }
                }
            }
