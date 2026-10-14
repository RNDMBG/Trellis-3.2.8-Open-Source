            package android.support.v4.widget;

            import android.content.res.Resources;
            import android.os.SystemClock;
            import android.support.v4.view.MotionEventCompat;
            import android.support.v4.view.ViewCompat;
            import android.util.DisplayMetrics;
            import android.view.MotionEvent;
            import android.view.View;
            import android.view.ViewConfiguration;
            import android.view.animation.AccelerateInterpolator;
            import android.view.animation.AnimationUtils;
            import android.view.animation.Interpolator;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 82 */    public abstract class AutoScrollHelper implements View.OnTouchListener {
                private static final int DEFAULT_ACTIVATION_DELAY = ViewConfiguration.getTapTimeout();
                private static final int DEFAULT_EDGE_TYPE = 1;
                private static final float DEFAULT_MAXIMUM_EDGE = Float.MAX_VALUE;
                private static final int DEFAULT_MAXIMUM_VELOCITY_DIPS = 1575;
                private static final int DEFAULT_MINIMUM_VELOCITY_DIPS = 315;
                private static final int DEFAULT_RAMP_DOWN_DURATION = 500;
                private static final int DEFAULT_RAMP_UP_DURATION = 500;
                private static final float DEFAULT_RELATIVE_EDGE = 0.2f;
                private static final float DEFAULT_RELATIVE_VELOCITY = 1.0f;
                public static final int EDGE_TYPE_INSIDE = 0;
                public static final int EDGE_TYPE_INSIDE_EXTEND = 1;
                public static final int EDGE_TYPE_OUTSIDE = 2;
                private static final int HORIZONTAL = 0;
                public static final float NO_MAX = Float.MAX_VALUE;
                public static final float NO_MIN = 0.0f;
                public static final float RELATIVE_UNSPECIFIED = 0.0f;
                private static final int VERTICAL = 1;
                private int mActivationDelay;
                private boolean mAlreadyDelayed;
                private boolean mAnimating;
                private int mEdgeType;
                private boolean mEnabled;
                private boolean mExclusive;
                private boolean mNeedsCancel;
                private boolean mNeedsReset;
                private Runnable mRunnable;
                private final View mTarget;
                private final ClampedScroller mScroller = new ClampedScroller();
                private final Interpolator mEdgeInterpolator = new AccelerateInterpolator();
                private float[] mRelativeEdges = {0.0f, 0.0f};
                private float[] mMaximumEdges = {Float.MAX_VALUE, Float.MAX_VALUE};
                private float[] mRelativeVelocity = {0.0f, 0.0f};
                private float[] mMinimumVelocity = {0.0f, 0.0f};
                private float[] mMaximumVelocity = {Float.MAX_VALUE, Float.MAX_VALUE};

                public abstract boolean canTargetScrollHorizontally(int i);

                public abstract boolean canTargetScrollVertically(int i);

                public abstract void scrollTargetBy(int i, int i2);

/* 83 */        static /* synthetic */ boolean access$100(AutoScrollHelper x0) {
/* 84 */            return x0.mAnimating;
                }

/* 83 */        static /* synthetic */ boolean access$102(AutoScrollHelper x0, boolean x1) {
/* 84 */            x0.mAnimating = x1;
                    return x1;
                }

/* 83 */        static /* synthetic */ boolean access$200(AutoScrollHelper x0) {
/* 84 */            return x0.mNeedsReset;
                }

/* 83 */        static /* synthetic */ boolean access$202(AutoScrollHelper x0, boolean x1) {
/* 84 */            x0.mNeedsReset = x1;
                    return x1;
                }

/* 83 */        static /* synthetic */ ClampedScroller access$300(AutoScrollHelper x0) {
/* 84 */            return x0.mScroller;
                }

/* 83 */        static /* synthetic */ boolean access$400(AutoScrollHelper x0) {
/* 84 */            return x0.shouldAnimate();
                }

/* 83 */        static /* synthetic */ boolean access$500(AutoScrollHelper x0) {
/* 84 */            return x0.mNeedsCancel;
                }

/* 83 */        static /* synthetic */ boolean access$502(AutoScrollHelper x0, boolean x1) {
/* 84 */            x0.mNeedsCancel = x1;
                    return x1;
                }

/* 83 */        static /* synthetic */ void access$600(AutoScrollHelper x0) {
/* 84 */            x0.cancelTargetTouch();
                }

/* 83 */        static /* synthetic */ View access$700(AutoScrollHelper x0) {
/* 84 */            return x0.mTarget;
                }

/* 83 */        static /* synthetic */ int access$800(int x0, int x1, int x2) {
/* 84 */            return constrain(x0, x1, x2);
                }

/* 83 */        static /* synthetic */ float access$900(float x0, float x1, float x2) {
/* 84 */            return constrain(x0, x1, x2);
                }

/* 208 */       public AutoScrollHelper(View target) {
/* 210 */           this.mTarget = target;
/* 212 */           DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
/* 213 */           int maxVelocity = (int) ((1575.0f * metrics.density) + 0.5f);
/* 214 */           int minVelocity = (int) ((315.0f * metrics.density) + 0.5f);
/* 215 */           setMaximumVelocity(maxVelocity, maxVelocity);
/* 216 */           setMinimumVelocity(minVelocity, minVelocity);
/* 218 */           setEdgeType(1);
/* 219 */           setMaximumEdges(Float.MAX_VALUE, Float.MAX_VALUE);
/* 220 */           setRelativeEdges(0.2f, 0.2f);
/* 221 */           setRelativeVelocity(1.0f, 1.0f);
/* 222 */           setActivationDelay(DEFAULT_ACTIVATION_DELAY);
/* 223 */           setRampUpDuration(500);
/* 224 */           setRampDownDuration(500);
                }

/* 234 */       public AutoScrollHelper setEnabled(boolean enabled) {
/* 235 */           if (this.mEnabled && !enabled) {
/* 236 */               requestStop();
                    }
/* 239 */           this.mEnabled = enabled;
/* 240 */           return this;
                }

/* 246 */       public boolean isEnabled() {
/* 247 */           return this.mEnabled;
                }

/* 262 */       public AutoScrollHelper setExclusive(boolean exclusive) {
/* 263 */           this.mExclusive = exclusive;
/* 264 */           return this;
                }

/* 275 */       public boolean isExclusive() {
/* 276 */           return this.mExclusive;
                }

/* 293 */       public AutoScrollHelper setMaximumVelocity(float horizontalMax, float verticalMax) {
/* 294 */           this.mMaximumVelocity[0] = horizontalMax / 1000.0f;
/* 295 */           this.mMaximumVelocity[1] = verticalMax / 1000.0f;
/* 296 */           return this;
                }

/* 311 */       public AutoScrollHelper setMinimumVelocity(float horizontalMin, float verticalMin) {
/* 312 */           this.mMinimumVelocity[0] = horizontalMin / 1000.0f;
/* 313 */           this.mMinimumVelocity[1] = verticalMin / 1000.0f;
/* 314 */           return this;
                }

/* 332 */       public AutoScrollHelper setRelativeVelocity(float horizontal, float vertical) {
/* 333 */           this.mRelativeVelocity[0] = horizontal / 1000.0f;
/* 334 */           this.mRelativeVelocity[1] = vertical / 1000.0f;
/* 335 */           return this;
                }

/* 353 */       public AutoScrollHelper setEdgeType(int type) {
/* 354 */           this.mEdgeType = type;
/* 355 */           return this;
                }

/* 372 */       public AutoScrollHelper setRelativeEdges(float horizontal, float vertical) {
/* 373 */           this.mRelativeEdges[0] = horizontal;
/* 374 */           this.mRelativeEdges[1] = vertical;
/* 375 */           return this;
                }

/* 394 */       public AutoScrollHelper setMaximumEdges(float horizontalMax, float verticalMax) {
/* 395 */           this.mMaximumEdges[0] = horizontalMax;
/* 396 */           this.mMaximumEdges[1] = verticalMax;
/* 397 */           return this;
                }

/* 411 */       public AutoScrollHelper setActivationDelay(int delayMillis) {
/* 412 */           this.mActivationDelay = delayMillis;
/* 413 */           return this;
                }

/* 426 */       public AutoScrollHelper setRampUpDuration(int durationMillis) {
/* 427 */           this.mScroller.setRampUpDuration(durationMillis);
/* 428 */           return this;
                }

/* 441 */       public AutoScrollHelper setRampDownDuration(int durationMillis) {
/* 442 */           this.mScroller.setRampDownDuration(durationMillis);
/* 443 */           return this;
                }

                /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
                @Override // android.view.View.OnTouchListener
/* 455 */       public boolean onTouch(View v, MotionEvent event) {
/* 456 */           if (!this.mEnabled) {
                        return false;
                    }
/* 460 */           int action = MotionEventCompat.getActionMasked(event);
                    switch (action) {
                        case 0:
/* 463 */                   this.mNeedsCancel = true;
/* 464 */                   this.mAlreadyDelayed = false;
/* 467 */                   float xTargetVelocity = computeTargetVelocity(0, event.getX(), v.getWidth(), this.mTarget.getWidth());
/* 469 */                   float yTargetVelocity = computeTargetVelocity(1, event.getY(), v.getHeight(), this.mTarget.getHeight());
/* 471 */                   this.mScroller.setTargetVelocity(xTargetVelocity, yTargetVelocity);
/* 475 */                   if (!this.mAnimating && shouldAnimate()) {
/* 476 */                       startAnimating();
                            }
                            break;
                        case 1:
                        case 3:
/* 481 */                   requestStop();
                            break;
                        case 2:
/* 467 */                   float xTargetVelocity2 = computeTargetVelocity(0, event.getX(), v.getWidth(), this.mTarget.getWidth());
/* 469 */                   float yTargetVelocity2 = computeTargetVelocity(1, event.getY(), v.getHeight(), this.mTarget.getHeight());
/* 471 */                   this.mScroller.setTargetVelocity(xTargetVelocity2, yTargetVelocity2);
/* 475 */                   if (!this.mAnimating) {
/* 476 */                       startAnimating();
                            }
                            break;
                    }
/* 485 */           return this.mExclusive && this.mAnimating;
                }

/* 491 */       private boolean shouldAnimate() {
/* 492 */           ClampedScroller scroller = this.mScroller;
/* 493 */           int verticalDirection = scroller.getVerticalDirection();
/* 494 */           int horizontalDirection = scroller.getHorizontalDirection();
                    return (verticalDirection != 0 && canTargetScrollVertically(verticalDirection)) || (horizontalDirection != 0 && canTargetScrollHorizontally(horizontalDirection));
                }

/* 503 */       private void startAnimating() {
/* 504 */           if (this.mRunnable == null) {
/* 505 */               this.mRunnable = new ScrollAnimationRunnable(this, null);
                    }
/* 508 */           this.mAnimating = true;
/* 509 */           this.mNeedsReset = true;
/* 511 */           if (!this.mAlreadyDelayed && this.mActivationDelay > 0) {
/* 512 */               ViewCompat.postOnAnimationDelayed(this.mTarget, this.mRunnable, this.mActivationDelay);
                    } else {
/* 514 */               this.mRunnable.run();
                    }
/* 519 */           this.mAlreadyDelayed = true;
                }

/* 527 */       private void requestStop() {
/* 528 */           if (this.mNeedsReset) {
/* 531 */               this.mAnimating = false;
                    } else {
/* 533 */               this.mScroller.requestStop();
                    }
                }

/* 538 */       private float computeTargetVelocity(int direction, float coordinate, float srcSize, float dstSize) {
/* 539 */           float relativeEdge = this.mRelativeEdges[direction];
/* 540 */           float maximumEdge = this.mMaximumEdges[direction];
/* 541 */           float value = getEdgeValue(relativeEdge, srcSize, maximumEdge, coordinate);
                    if (value == 0.0f) {
                        return 0.0f;
                    }
/* 547 */           float relativeVelocity = this.mRelativeVelocity[direction];
/* 548 */           float minimumVelocity = this.mMinimumVelocity[direction];
/* 549 */           float maximumVelocity = this.mMaximumVelocity[direction];
/* 550 */           float targetVelocity = relativeVelocity * dstSize;
                    if (value > 0.0f) {
/* 556 */               return constrain(value * targetVelocity, minimumVelocity, maximumVelocity);
                    }
/* 558 */           return -constrain((-value) * targetVelocity, minimumVelocity, maximumVelocity);
                }

/* 606 */       private float getEdgeValue(float relativeValue, float size, float maxValue, float current) {
                    float interpolated;
/* 607 */           float edgeSize = constrain(relativeValue * size, 0.0f, maxValue);
/* 608 */           float valueLeading = constrainEdgeValue(current, edgeSize);
/* 609 */           float valueTrailing = constrainEdgeValue(size - current, edgeSize);
/* 610 */           float value = valueTrailing - valueLeading;
                    if (value < 0.0f) {
/* 613 */               interpolated = -this.mEdgeInterpolator.getInterpolation(-value);
                    } else {
                        if (value <= 0.0f) {
                            return 0.0f;
                        }
/* 615 */               interpolated = this.mEdgeInterpolator.getInterpolation(value);
                    }
/* 620 */           return constrain(interpolated, -1.0f, 1.0f);
                }

/* 623 */       private float constrainEdgeValue(float current, float leading) {
                    if (leading == 0.0f) {
                        return 0.0f;
                    }
                    switch (this.mEdgeType) {
                        case 0:
                        case 1:
                            if (current >= leading) {
                                return 0.0f;
                            }
                            if (current >= 0.0f) {
/* 634 */                       return 1.0f - (current / leading);
                            }
                            return (this.mAnimating && this.mEdgeType == 1) ? 1.0f : 0.0f;
                        case 2:
                            if (current < 0.0f) {
/* 644 */                       return current / (-leading);
                            }
                            return 0.0f;
                        default:
                            return 0.0f;
                    }
                }

/* 652 */       private static int constrain(int value, int min, int max) {
/* 653 */           if (value > max) {
/* 658 */               return max;
                    }
                    return value < min ? min : value;
                }

/* 662 */       private static float constrain(float value, float min, float max) {
                    if (value > max) {
/* 668 */               return max;
                    }
                    return value < min ? min : value;
                }

/* 676 */       private void cancelTargetTouch() {
/* 677 */           long eventTime = SystemClock.uptimeMillis();
/* 678 */           MotionEvent cancel = MotionEvent.obtain(eventTime, eventTime, 3, 0.0f, 0.0f, 0);
/* 680 */           this.mTarget.onTouchEvent(cancel);
/* 681 */           cancel.recycle();
                }

/* 682 */       private class ScrollAnimationRunnable implements Runnable {
/* 683 */           private ScrollAnimationRunnable() {
                    }

/* 683 */           /* synthetic */ ScrollAnimationRunnable(AutoScrollHelper x0, C00611 x1) {
/* 684 */               this();
                    }

                    @Override // java.lang.Runnable
/* 686 */           public void run() {
/* 687 */               if (AutoScrollHelper.access$100(AutoScrollHelper.this)) {
/* 691 */                   if (AutoScrollHelper.access$200(AutoScrollHelper.this)) {
/* 692 */                       AutoScrollHelper.access$202(AutoScrollHelper.this, false);
/* 693 */                       AutoScrollHelper.access$300(AutoScrollHelper.this).start();
                            }
/* 696 */                   ClampedScroller scroller = AutoScrollHelper.access$300(AutoScrollHelper.this);
/* 697 */                   if (scroller.isFinished() || !AutoScrollHelper.access$400(AutoScrollHelper.this)) {
/* 698 */                       AutoScrollHelper.access$102(AutoScrollHelper.this, false);
/* 715 */                       return;
                            }
/* 702 */                   if (AutoScrollHelper.access$500(AutoScrollHelper.this)) {
/* 703 */                       AutoScrollHelper.access$502(AutoScrollHelper.this, false);
/* 704 */                       AutoScrollHelper.access$600(AutoScrollHelper.this);
                            }
/* 707 */                   scroller.computeScrollDelta();
/* 709 */                   int deltaX = scroller.getDeltaX();
/* 710 */                   int deltaY = scroller.getDeltaY();
/* 711 */                   AutoScrollHelper.this.scrollTargetBy(deltaX, deltaY);
/* 714 */                   ViewCompat.postOnAnimation(AutoScrollHelper.access$700(AutoScrollHelper.this), this);
                        }
                    }
                }

/* 750 */       private static class ClampedScroller {
                    private int mEffectiveRampDown;
                    private int mRampDownDuration;
                    private int mRampUpDuration;
                    private float mStopValue;
                    private float mTargetVelocityX;
                    private float mTargetVelocityY;
                    private long mStartTime = Long.MIN_VALUE;
                    private long mStopTime = -1;
                    private long mDeltaTime = 0;
                    private int mDeltaX = 0;
                    private int mDeltaY = 0;

/* 751 */           public void setRampUpDuration(int durationMillis) {
/* 752 */               this.mRampUpDuration = durationMillis;
                    }

/* 755 */           public void setRampDownDuration(int durationMillis) {
/* 756 */               this.mRampDownDuration = durationMillis;
                    }

/* 762 */           public void start() {
/* 763 */               this.mStartTime = AnimationUtils.currentAnimationTimeMillis();
/* 764 */               this.mStopTime = -1L;
/* 765 */               this.mDeltaTime = this.mStartTime;
/* 766 */               this.mStopValue = 0.5f;
/* 767 */               this.mDeltaX = 0;
/* 768 */               this.mDeltaY = 0;
                    }

/* 774 */           public void requestStop() {
/* 775 */               long currentTime = AnimationUtils.currentAnimationTimeMillis();
/* 776 */               this.mEffectiveRampDown = AutoScrollHelper.access$800((int) (currentTime - this.mStartTime), 0, this.mRampDownDuration);
/* 777 */               this.mStopValue = getValueAt(currentTime);
/* 778 */               this.mStopTime = currentTime;
                    }

/* 781 */           public boolean isFinished() {
                        return this.mStopTime > 0 && AnimationUtils.currentAnimationTimeMillis() > this.mStopTime + ((long) this.mEffectiveRampDown);
                    }

/* 786 */           private float getValueAt(long currentTime) {
                        if (currentTime < this.mStartTime) {
                            return 0.0f;
                        }
                        if (this.mStopTime < 0 || currentTime < this.mStopTime) {
/* 790 */                   long elapsedSinceStart = currentTime - this.mStartTime;
/* 791 */                   return AutoScrollHelper.access$900(elapsedSinceStart / this.mRampUpDuration, 0.0f, 1.0f) * 0.5f;
                        }
/* 793 */               long elapsedSinceEnd = currentTime - this.mStopTime;
                        return (AutoScrollHelper.access$900(elapsedSinceEnd / this.mEffectiveRampDown, 0.0f, 1.0f) * this.mStopValue) + (1.0f - this.mStopValue);
                    }

/* 806 */           private float interpolateValue(float value) {
/* 807 */               return ((-4.0f) * value * value) + (4.0f * value);
                    }

/* 817 */           public void computeScrollDelta() {
                        if (this.mDeltaTime == 0) {
/* 819 */                   throw new RuntimeException("Cannot compute scroll delta before calling start()");
                        }
/* 822 */               long currentTime = AnimationUtils.currentAnimationTimeMillis();
/* 823 */               float value = getValueAt(currentTime);
/* 824 */               float scale = interpolateValue(value);
/* 825 */               long elapsedSinceDelta = currentTime - this.mDeltaTime;
/* 827 */               this.mDeltaTime = currentTime;
/* 828 */               this.mDeltaX = (int) (elapsedSinceDelta * scale * this.mTargetVelocityX);
/* 829 */               this.mDeltaY = (int) (elapsedSinceDelta * scale * this.mTargetVelocityY);
                    }

/* 838 */           public void setTargetVelocity(float x, float y) {
/* 839 */               this.mTargetVelocityX = x;
/* 840 */               this.mTargetVelocityY = y;
                    }

/* 843 */           public int getHorizontalDirection() {
/* 844 */               return (int) (this.mTargetVelocityX / Math.abs(this.mTargetVelocityX));
                    }

/* 847 */           public int getVerticalDirection() {
/* 848 */               return (int) (this.mTargetVelocityY / Math.abs(this.mTargetVelocityY));
                    }

/* 855 */           public int getDeltaX() {
/* 856 */               return this.mDeltaX;
                    }

/* 863 */           public int getDeltaY() {
/* 864 */               return this.mDeltaY;
                    }
                }
            }
