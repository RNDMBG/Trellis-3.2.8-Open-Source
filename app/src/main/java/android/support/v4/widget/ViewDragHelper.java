            package android.support.v4.widget;

            import android.content.Context;
            import android.support.v4.view.MotionEventCompat;
            import android.support.v4.view.VelocityTrackerCompat;
            import android.support.v4.view.ViewCompat;
            import android.view.MotionEvent;
            import android.view.VelocityTracker;
            import android.view.View;
            import android.view.ViewConfiguration;
            import android.view.ViewGroup;
            import android.view.animation.Interpolator;
            import java.util.Arrays;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 156 */   public class ViewDragHelper {
                private static final int BASE_SETTLE_DURATION = 256;
                public static final int DIRECTION_ALL = 3;
                public static final int DIRECTION_HORIZONTAL = 1;
                public static final int DIRECTION_VERTICAL = 2;
                public static final int EDGE_ALL = 15;
                public static final int EDGE_BOTTOM = 8;
                public static final int EDGE_LEFT = 1;
                public static final int EDGE_RIGHT = 2;
                private static final int EDGE_SIZE = 20;
                public static final int EDGE_TOP = 4;
                public static final int INVALID_POINTER = -1;
                private static final int MAX_SETTLE_DURATION = 600;
                public static final int STATE_DRAGGING = 1;
                public static final int STATE_IDLE = 0;
                public static final int STATE_SETTLING = 2;
                private static final String TAG = "ViewDragHelper";
                private static final Interpolator sInterpolator = new InterpolatorC00801();
                private final Callback mCallback;
                private View mCapturedView;
                private int mDragState;
                private int[] mEdgeDragsInProgress;
                private int[] mEdgeDragsLocked;
                private int mEdgeSize;
                private int[] mInitialEdgesTouched;
                private float[] mInitialMotionX;
                private float[] mInitialMotionY;
                private float[] mLastMotionX;
                private float[] mLastMotionY;
                private float mMaxVelocity;
                private float mMinVelocity;
                private final ViewGroup mParentView;
                private int mPointersDown;
                private boolean mReleaseInProgress;
                private ScrollerCompat mScroller;
                private int mTouchSlop;
                private int mTrackingEdges;
                private VelocityTracker mVelocityTracker;
                private int mActivePointerId = -1;
                private final Runnable mSetIdleRunnable = new RunnableC00812();

/* 157 */       public static abstract class Callback {
                    public abstract boolean tryCaptureView(View view, int i);

/* 158 */           public void onViewDragStateChanged(int state) {
                    }

/* 169 */           public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
                    }

/* 180 */           public void onViewCaptured(View capturedChild, int activePointerId) {
                    }

/* 200 */           public void onViewReleased(View releasedChild, float xvel, float yvel) {
                    }

/* 213 */           public void onEdgeTouched(int edgeFlags, int pointerId) {
                    }

/* 225 */           public boolean onEdgeLock(int edgeFlags) {
/* 226 */               return false;
                    }

/* 239 */           public void onEdgeDragStarted(int edgeFlags, int pointerId) {
                    }

/* 248 */           public int getOrderedChildIndex(int index) {
/* 249 */               return index;
                    }

/* 259 */           public int getViewHorizontalDragRange(View child) {
/* 260 */               return 0;
                    }

/* 270 */           public int getViewVerticalDragRange(View child) {
/* 271 */               return 0;
                    }

/* 303 */           public int clampViewPositionHorizontal(View child, int left, int dx) {
/* 304 */               return 0;
                    }

/* 318 */           public int clampViewPositionVertical(View child, int top, int dy) {
/* 319 */               return 0;
                    }
                }

                /* JADX INFO: renamed from: android.support.v4.widget.ViewDragHelper$1 */
/* 324 */       static class InterpolatorC00801 implements Interpolator {
/* 325 */           InterpolatorC00801() {
                    }

                    @Override // android.animation.TimeInterpolator
/* 327 */           public float getInterpolation(float t) {
/* 328 */               float t2 = t - 1.0f;
/* 329 */               return (t2 * t2 * t2 * t2 * t2) + 1.0f;
                    }
                }

                /* JADX INFO: renamed from: android.support.v4.widget.ViewDragHelper$2 */
/* 331 */       class RunnableC00812 implements Runnable {
/* 332 */           RunnableC00812() {
                    }

                    @Override // java.lang.Runnable
/* 334 */           public void run() {
/* 335 */               ViewDragHelper.this.setDragState(0);
                    }
                }

/* 346 */       public static ViewDragHelper create(ViewGroup forParent, Callback cb) {
/* 347 */           return new ViewDragHelper(forParent.getContext(), forParent, cb);
                }

/* 359 */       public static ViewDragHelper create(ViewGroup forParent, float sensitivity, Callback cb) {
/* 360 */           ViewDragHelper helper = create(forParent, cb);
/* 361 */           helper.mTouchSlop = (int) (helper.mTouchSlop * (1.0f / sensitivity));
/* 362 */           return helper;
                }

/* 372 */       private ViewDragHelper(Context context, ViewGroup forParent, Callback cb) {
/* 374 */           if (forParent == null) {
/* 375 */               throw new IllegalArgumentException("Parent view may not be null");
                    }
/* 377 */           if (cb == null) {
/* 378 */               throw new IllegalArgumentException("Callback may not be null");
                    }
/* 381 */           this.mParentView = forParent;
/* 382 */           this.mCallback = cb;
/* 384 */           ViewConfiguration vc = ViewConfiguration.get(context);
/* 385 */           float density = context.getResources().getDisplayMetrics().density;
/* 386 */           this.mEdgeSize = (int) ((20.0f * density) + 0.5f);
/* 388 */           this.mTouchSlop = vc.getScaledTouchSlop();
/* 389 */           this.mMaxVelocity = vc.getScaledMaximumFlingVelocity();
/* 390 */           this.mMinVelocity = vc.getScaledMinimumFlingVelocity();
/* 391 */           this.mScroller = ScrollerCompat.create(context, sInterpolator);
                }

/* 400 */       public void setMinVelocity(float minVel) {
/* 401 */           this.mMinVelocity = minVel;
                }

/* 411 */       public float getMinVelocity() {
/* 412 */           return this.mMinVelocity;
                }

/* 420 */       public int getViewDragState() {
/* 421 */           return this.mDragState;
                }

/* 436 */       public void setEdgeTrackingEnabled(int edgeFlags) {
/* 437 */           this.mTrackingEdges = edgeFlags;
                }

/* 447 */       public int getEdgeSize() {
/* 448 */           return this.mEdgeSize;
                }

/* 459 */       public void captureChildView(View childView, int activePointerId) {
/* 460 */           if (childView.getParent() != this.mParentView) {
/* 461 */               throw new IllegalArgumentException("captureChildView: parameter must be a descendant of the ViewDragHelper's tracked parent view (" + this.mParentView + ")");
                    }
/* 465 */           this.mCapturedView = childView;
/* 466 */           this.mActivePointerId = activePointerId;
/* 467 */           this.mCallback.onViewCaptured(childView, activePointerId);
/* 468 */           setDragState(1);
                }

/* 474 */       public View getCapturedView() {
/* 475 */           return this.mCapturedView;
                }

/* 482 */       public int getActivePointerId() {
/* 483 */           return this.mActivePointerId;
                }

/* 489 */       public int getTouchSlop() {
/* 490 */           return this.mTouchSlop;
                }

/* 497 */       public void cancel() {
/* 498 */           this.mActivePointerId = -1;
/* 499 */           clearMotionHistory();
/* 501 */           if (this.mVelocityTracker != null) {
/* 502 */               this.mVelocityTracker.recycle();
/* 503 */               this.mVelocityTracker = null;
                    }
                }

/* 511 */       public void abort() {
/* 512 */           cancel();
/* 513 */           if (this.mDragState == 2) {
/* 514 */               int oldX = this.mScroller.getCurrX();
/* 515 */               int oldY = this.mScroller.getCurrY();
/* 516 */               this.mScroller.abortAnimation();
/* 517 */               int newX = this.mScroller.getCurrX();
/* 518 */               int newY = this.mScroller.getCurrY();
/* 519 */               this.mCallback.onViewPositionChanged(this.mCapturedView, newX, newY, newX - oldX, newY - oldY);
                    }
/* 521 */           setDragState(0);
                }

/* 538 */       public boolean smoothSlideViewTo(View child, int finalLeft, int finalTop) {
/* 539 */           this.mCapturedView = child;
/* 540 */           this.mActivePointerId = -1;
/* 542 */           return forceSettleCapturedViewAt(finalLeft, finalTop, 0, 0);
                }

/* 556 */       public boolean settleCapturedViewAt(int finalLeft, int finalTop) {
/* 557 */           if (!this.mReleaseInProgress) {
/* 558 */               throw new IllegalStateException("Cannot settleCapturedViewAt outside of a call to Callback#onViewReleased");
                    }
/* 562 */           return forceSettleCapturedViewAt(finalLeft, finalTop, (int) VelocityTrackerCompat.getXVelocity(this.mVelocityTracker, this.mActivePointerId), (int) VelocityTrackerCompat.getYVelocity(this.mVelocityTracker, this.mActivePointerId));
                }

/* 576 */       private boolean forceSettleCapturedViewAt(int finalLeft, int finalTop, int xvel, int yvel) {
/* 577 */           int startLeft = this.mCapturedView.getLeft();
/* 578 */           int startTop = this.mCapturedView.getTop();
/* 579 */           int dx = finalLeft - startLeft;
/* 580 */           int dy = finalTop - startTop;
/* 582 */           if (dx == 0 && dy == 0) {
/* 584 */               this.mScroller.abortAnimation();
/* 585 */               setDragState(0);
                        return false;
                    }
/* 589 */           int duration = computeSettleDuration(this.mCapturedView, dx, dy, xvel, yvel);
/* 590 */           this.mScroller.startScroll(startLeft, startTop, dx, dy, duration);
/* 592 */           setDragState(2);
/* 593 */           return true;
                }

/* 596 */       private int computeSettleDuration(View child, int dx, int dy, int xvel, int yvel) {
/* 597 */           int xvel2 = clampMag(xvel, (int) this.mMinVelocity, (int) this.mMaxVelocity);
/* 598 */           int yvel2 = clampMag(yvel, (int) this.mMinVelocity, (int) this.mMaxVelocity);
/* 599 */           int absDx = Math.abs(dx);
/* 600 */           int absDy = Math.abs(dy);
/* 601 */           int absXVel = Math.abs(xvel2);
/* 602 */           int absYVel = Math.abs(yvel2);
/* 603 */           int addedVel = absXVel + absYVel;
/* 604 */           int addedDistance = absDx + absDy;
/* 606 */           float xweight = xvel2 != 0 ? absXVel / addedVel : absDx / addedDistance;
/* 608 */           float yweight = yvel2 != 0 ? absYVel / addedVel : absDy / addedDistance;
/* 611 */           int xduration = computeAxisDuration(dx, xvel2, this.mCallback.getViewHorizontalDragRange(child));
/* 612 */           int yduration = computeAxisDuration(dy, yvel2, this.mCallback.getViewVerticalDragRange(child));
/* 614 */           return (int) ((xduration * xweight) + (yduration * yweight));
                }

/* 617 */       private int computeAxisDuration(int delta, int velocity, int motionRange) {
                    int duration;
/* 618 */           if (delta == 0) {
/* 619 */               return 0;
                    }
/* 622 */           int width = this.mParentView.getWidth();
/* 623 */           int halfWidth = width / 2;
/* 624 */           float distanceRatio = Math.min(1.0f, Math.abs(delta) / width);
/* 625 */           float distance = halfWidth + (halfWidth * distanceInfluenceForSnapDuration(distanceRatio));
/* 629 */           int velocity2 = Math.abs(velocity);
/* 630 */           if (velocity2 > 0) {
/* 631 */               duration = Math.round(1000.0f * Math.abs(distance / velocity2)) * 4;
                    } else {
/* 633 */               float range = Math.abs(delta) / motionRange;
/* 634 */               duration = (int) ((range + 1.0f) * 256.0f);
                    }
/* 636 */           return Math.min(duration, MAX_SETTLE_DURATION);
                }

/* 649 */       private int clampMag(int value, int absMin, int absMax) {
/* 650 */           int absValue = Math.abs(value);
/* 651 */           if (absValue < absMin) {
                        return 0;
                    }
/* 652 */           if (absValue > absMax) {
                        return value <= 0 ? -absMax : absMax;
                    }
                    return value;
                }

/* 666 */       private float clampMag(float value, float absMin, float absMax) {
/* 667 */           float absValue = Math.abs(value);
                    if (absValue < absMin) {
                        return 0.0f;
                    }
                    if (absValue > absMax) {
                        return value <= 0.0f ? -absMax : absMax;
                    }
                    return value;
                }

/* 673 */       private float distanceInfluenceForSnapDuration(float f) {
/* 676 */           return (float) Math.sin((float) (((double) (f - 0.5f)) * 0.4712389167638204d));
                }

/* 689 */       public void flingCapturedView(int minLeft, int minTop, int maxLeft, int maxTop) {
/* 690 */           if (!this.mReleaseInProgress) {
/* 691 */               throw new IllegalStateException("Cannot flingCapturedView outside of a call to Callback#onViewReleased");
                    }
/* 695 */           this.mScroller.fling(this.mCapturedView.getLeft(), this.mCapturedView.getTop(), (int) VelocityTrackerCompat.getXVelocity(this.mVelocityTracker, this.mActivePointerId), (int) VelocityTrackerCompat.getYVelocity(this.mVelocityTracker, this.mActivePointerId), minLeft, maxLeft, minTop, maxTop);
/* 700 */           setDragState(2);
                }

/* 714 */       public boolean continueSettling(boolean deferCallbacks) {
/* 715 */           if (this.mDragState == 2) {
/* 716 */               boolean keepGoing = this.mScroller.computeScrollOffset();
/* 717 */               int x = this.mScroller.getCurrX();
/* 718 */               int y = this.mScroller.getCurrY();
/* 719 */               int dx = x - this.mCapturedView.getLeft();
/* 720 */               int dy = y - this.mCapturedView.getTop();
/* 722 */               if (dx != 0) {
/* 723 */                   this.mCapturedView.offsetLeftAndRight(dx);
                        }
/* 725 */               if (dy != 0) {
/* 726 */                   this.mCapturedView.offsetTopAndBottom(dy);
                        }
/* 729 */               if (dx != 0 || dy != 0) {
/* 730 */                   this.mCallback.onViewPositionChanged(this.mCapturedView, x, y, dx, dy);
                        }
/* 733 */               if (keepGoing && x == this.mScroller.getFinalX() && y == this.mScroller.getFinalY()) {
/* 736 */                   this.mScroller.abortAnimation();
/* 737 */                   keepGoing = this.mScroller.isFinished();
                        }
/* 740 */               if (!keepGoing) {
/* 741 */                   if (deferCallbacks) {
/* 742 */                       this.mParentView.post(this.mSetIdleRunnable);
                            } else {
/* 744 */                       setDragState(0);
                            }
                        }
                    }
                    return this.mDragState == 2;
                }

/* 758 */       private void dispatchViewReleased(float xvel, float yvel) {
/* 759 */           this.mReleaseInProgress = true;
/* 760 */           this.mCallback.onViewReleased(this.mCapturedView, xvel, yvel);
/* 761 */           this.mReleaseInProgress = false;
/* 763 */           if (this.mDragState == 1) {
/* 765 */               setDragState(0);
                    }
                }

/* 769 */       private void clearMotionHistory() {
/* 770 */           if (this.mInitialMotionX != null) {
/* 773 */               Arrays.fill(this.mInitialMotionX, 0.0f);
/* 774 */               Arrays.fill(this.mInitialMotionY, 0.0f);
/* 775 */               Arrays.fill(this.mLastMotionX, 0.0f);
/* 776 */               Arrays.fill(this.mLastMotionY, 0.0f);
/* 777 */               Arrays.fill(this.mInitialEdgesTouched, 0);
/* 778 */               Arrays.fill(this.mEdgeDragsInProgress, 0);
/* 779 */               Arrays.fill(this.mEdgeDragsLocked, 0);
/* 780 */               this.mPointersDown = 0;
                    }
                }

/* 783 */       private void clearMotionHistory(int pointerId) {
/* 784 */           if (this.mInitialMotionX != null) {
/* 787 */               this.mInitialMotionX[pointerId] = 0.0f;
/* 788 */               this.mInitialMotionY[pointerId] = 0.0f;
/* 789 */               this.mLastMotionX[pointerId] = 0.0f;
/* 790 */               this.mLastMotionY[pointerId] = 0.0f;
/* 791 */               this.mInitialEdgesTouched[pointerId] = 0;
/* 792 */               this.mEdgeDragsInProgress[pointerId] = 0;
/* 793 */               this.mEdgeDragsLocked[pointerId] = 0;
/* 794 */               this.mPointersDown &= (1 << pointerId) ^ (-1);
                    }
                }

/* 797 */       private void ensureMotionHistorySizeForId(int pointerId) {
/* 798 */           if (this.mInitialMotionX == null || this.mInitialMotionX.length <= pointerId) {
/* 799 */               float[] imx = new float[pointerId + 1];
/* 800 */               float[] imy = new float[pointerId + 1];
/* 801 */               float[] lmx = new float[pointerId + 1];
/* 802 */               float[] lmy = new float[pointerId + 1];
/* 803 */               int[] iit = new int[pointerId + 1];
/* 804 */               int[] edip = new int[pointerId + 1];
/* 805 */               int[] edl = new int[pointerId + 1];
/* 807 */               if (this.mInitialMotionX != null) {
/* 808 */                   System.arraycopy(this.mInitialMotionX, 0, imx, 0, this.mInitialMotionX.length);
/* 809 */                   System.arraycopy(this.mInitialMotionY, 0, imy, 0, this.mInitialMotionY.length);
/* 810 */                   System.arraycopy(this.mLastMotionX, 0, lmx, 0, this.mLastMotionX.length);
/* 811 */                   System.arraycopy(this.mLastMotionY, 0, lmy, 0, this.mLastMotionY.length);
/* 812 */                   System.arraycopy(this.mInitialEdgesTouched, 0, iit, 0, this.mInitialEdgesTouched.length);
/* 813 */                   System.arraycopy(this.mEdgeDragsInProgress, 0, edip, 0, this.mEdgeDragsInProgress.length);
/* 814 */                   System.arraycopy(this.mEdgeDragsLocked, 0, edl, 0, this.mEdgeDragsLocked.length);
                        }
/* 817 */               this.mInitialMotionX = imx;
/* 818 */               this.mInitialMotionY = imy;
/* 819 */               this.mLastMotionX = lmx;
/* 820 */               this.mLastMotionY = lmy;
/* 821 */               this.mInitialEdgesTouched = iit;
/* 822 */               this.mEdgeDragsInProgress = edip;
/* 823 */               this.mEdgeDragsLocked = edl;
                    }
                }

/* 827 */       private void saveInitialMotion(float x, float y, int pointerId) {
/* 828 */           ensureMotionHistorySizeForId(pointerId);
/* 829 */           float[] fArr = this.mInitialMotionX;
                    this.mLastMotionX[pointerId] = x;
                    fArr[pointerId] = x;
/* 830 */           float[] fArr2 = this.mInitialMotionY;
                    this.mLastMotionY[pointerId] = y;
                    fArr2[pointerId] = y;
/* 831 */           this.mInitialEdgesTouched[pointerId] = getEdgesTouched((int) x, (int) y);
/* 832 */           this.mPointersDown |= 1 << pointerId;
                }

/* 835 */       private void saveLastMotion(MotionEvent ev) {
/* 836 */           int pointerCount = MotionEventCompat.getPointerCount(ev);
                    for (int i = 0; i < pointerCount; i++) {
/* 838 */               int pointerId = MotionEventCompat.getPointerId(ev, i);
/* 839 */               float x = MotionEventCompat.getX(ev, i);
/* 840 */               float y = MotionEventCompat.getY(ev, i);
/* 841 */               this.mLastMotionX[pointerId] = x;
/* 842 */               this.mLastMotionY[pointerId] = y;
                    }
                }

/* 859 */       public boolean isPointerDown(int pointerId) {
                    return (this.mPointersDown & (1 << pointerId)) != 0;
                }

/* 863 */       void setDragState(int state) {
/* 864 */           if (this.mDragState != state) {
/* 865 */               this.mDragState = state;
/* 866 */               this.mCallback.onViewDragStateChanged(state);
/* 867 */               if (state == 0) {
/* 868 */                   this.mCapturedView = null;
                        }
                    }
                }

/* 882 */       boolean tryCaptureViewForDrag(View toCapture, int pointerId) {
/* 883 */           if (toCapture == this.mCapturedView && this.mActivePointerId == pointerId) {
                        return true;
                    }
/* 887 */           if (toCapture != null && this.mCallback.tryCaptureView(toCapture, pointerId)) {
/* 888 */               this.mActivePointerId = pointerId;
/* 889 */               captureChildView(toCapture, pointerId);
                        return true;
                    }
/* 892 */           return false;
                }

/* 907 */       protected boolean canScroll(View v, boolean checkV, int dx, int dy, int x, int y) {
/* 908 */           if (v instanceof ViewGroup) {
/* 909 */               ViewGroup group = (ViewGroup) v;
/* 910 */               int scrollX = v.getScrollX();
/* 911 */               int scrollY = v.getScrollY();
/* 912 */               int count = group.getChildCount();
                        for (int i = count - 1; i >= 0; i--) {
/* 917 */                   View child = group.getChildAt(i);
/* 918 */                   if (x + scrollX >= child.getLeft() && x + scrollX < child.getRight() && y + scrollY >= child.getTop() && y + scrollY < child.getBottom() && canScroll(child, true, dx, dy, (x + scrollX) - child.getLeft(), (y + scrollY) - child.getTop())) {
/* 922 */                       return true;
                            }
                        }
                    }
                    return checkV && (ViewCompat.canScrollHorizontally(v, -dx) || ViewCompat.canScrollVertically(v, -dy));
                }

/* 938 */       public boolean shouldInterceptTouchEvent(MotionEvent ev) {
                    View toCapture;
                    View toCapture2;
/* 939 */           int action = MotionEventCompat.getActionMasked(ev);
/* 940 */           int actionIndex = MotionEventCompat.getActionIndex(ev);
/* 942 */           if (action == 0) {
/* 945 */               cancel();
                    }
/* 948 */           if (this.mVelocityTracker == null) {
/* 949 */               this.mVelocityTracker = VelocityTracker.obtain();
                    }
/* 951 */           this.mVelocityTracker.addMovement(ev);
                    switch (action) {
                        case 0:
/* 955 */                   float x = ev.getX();
/* 956 */                   float y = ev.getY();
/* 957 */                   int pointerId = MotionEventCompat.getPointerId(ev, 0);
/* 958 */                   saveInitialMotion(x, y, pointerId);
/* 960 */                   View toCapture3 = findTopChildUnder((int) x, (int) y);
/* 963 */                   if (toCapture3 == this.mCapturedView && this.mDragState == 2) {
/* 964 */                       tryCaptureViewForDrag(toCapture3, pointerId);
                            }
/* 967 */                   int edgesTouched = this.mInitialEdgesTouched[pointerId];
/* 968 */                   if ((this.mTrackingEdges & edgesTouched) != 0) {
/* 969 */                       this.mCallback.onEdgeTouched(this.mTrackingEdges & edgesTouched, pointerId);
                            }
                            break;
                        case 1:
                        case 3:
/* 1031 */                  cancel();
                            break;
                        case 2:
/* 999 */                   int pointerCount = MotionEventCompat.getPointerCount(ev);
                            for (int i = 0; i < pointerCount; i++) {
/* 1001 */                      int pointerId2 = MotionEventCompat.getPointerId(ev, i);
/* 1002 */                      float x2 = MotionEventCompat.getX(ev, i);
/* 1003 */                      float y2 = MotionEventCompat.getY(ev, i);
/* 1004 */                      float dx = x2 - this.mInitialMotionX[pointerId2];
/* 1005 */                      float dy = y2 - this.mInitialMotionY[pointerId2];
/* 1007 */                      reportNewEdgeDrags(dx, dy, pointerId2);
/* 1008 */                      if (this.mDragState == 1 || ((toCapture = findTopChildUnder((int) x2, (int) y2)) != null && checkTouchSlop(toCapture, dx, dy) && tryCaptureViewForDrag(toCapture, pointerId2))) {
/* 1019 */                          saveLastMotion(ev);
                                }
                                break;
                            }
/* 1019 */                  saveLastMotion(ev);
                            break;
                        case 5:
/* 975 */                   int pointerId3 = MotionEventCompat.getPointerId(ev, actionIndex);
/* 976 */                   float x3 = MotionEventCompat.getX(ev, actionIndex);
/* 977 */                   float y3 = MotionEventCompat.getY(ev, actionIndex);
/* 979 */                   saveInitialMotion(x3, y3, pointerId3);
/* 982 */                   if (this.mDragState == 0) {
/* 983 */                       int edgesTouched2 = this.mInitialEdgesTouched[pointerId3];
/* 984 */                       if ((this.mTrackingEdges & edgesTouched2) != 0) {
/* 985 */                           this.mCallback.onEdgeTouched(this.mTrackingEdges & edgesTouched2, pointerId3);
                                }
                            } else if (this.mDragState == 2 && (toCapture2 = findTopChildUnder((int) x3, (int) y3)) == this.mCapturedView) {
/* 991 */                       tryCaptureViewForDrag(toCapture2, pointerId3);
                            }
                            break;
                        case 6:
/* 1025 */                  clearMotionHistory(MotionEventCompat.getPointerId(ev, actionIndex));
                            break;
                    }
                    return this.mDragState == 1;
                }

                /* JADX WARN: Code duplicated, block: B:51:0x027d  */
/* 1045 */      public void processTouchEvent(MotionEvent ev) {
/* 1046 */          int action = MotionEventCompat.getActionMasked(ev);
/* 1047 */          int actionIndex = MotionEventCompat.getActionIndex(ev);
/* 1049 */          if (action == 0) {
/* 1052 */              cancel();
                    }
/* 1055 */          if (this.mVelocityTracker == null) {
/* 1056 */              this.mVelocityTracker = VelocityTracker.obtain();
                    }
/* 1058 */          this.mVelocityTracker.addMovement(ev);
                    switch (action) {
                        case 0:
/* 1062 */                  float x = ev.getX();
/* 1063 */                  float y = ev.getY();
/* 1064 */                  int pointerId = MotionEventCompat.getPointerId(ev, 0);
/* 1065 */                  View toCapture = findTopChildUnder((int) x, (int) y);
/* 1067 */                  saveInitialMotion(x, y, pointerId);
/* 1072 */                  tryCaptureViewForDrag(toCapture, pointerId);
/* 1074 */                  int edgesTouched = this.mInitialEdgesTouched[pointerId];
/* 1075 */                  if ((this.mTrackingEdges & edgesTouched) != 0) {
/* 1076 */                      this.mCallback.onEdgeTouched(this.mTrackingEdges & edgesTouched, pointerId);
                            }
                            break;
                        case 1:
/* 1179 */                  if (this.mDragState == 1) {
/* 1180 */                      releaseViewForPointerUp();
                            }
/* 1182 */                  cancel();
                            break;
                        case 2:
/* 1110 */                  if (this.mDragState == 1) {
/* 1111 */                      int index = MotionEventCompat.findPointerIndex(ev, this.mActivePointerId);
/* 1112 */                      float x2 = MotionEventCompat.getX(ev, index);
/* 1113 */                      float y2 = MotionEventCompat.getY(ev, index);
/* 1114 */                      int idx = (int) (x2 - this.mLastMotionX[this.mActivePointerId]);
/* 1115 */                      int idy = (int) (y2 - this.mLastMotionY[this.mActivePointerId]);
/* 1117 */                      dragTo(this.mCapturedView.getLeft() + idx, this.mCapturedView.getTop() + idy, idx, idy);
/* 1119 */                      saveLastMotion(ev);
                            } else {
/* 1122 */                      int pointerCount = MotionEventCompat.getPointerCount(ev);
                                for (int i = 0; i < pointerCount; i++) {
/* 1124 */                          int pointerId2 = MotionEventCompat.getPointerId(ev, i);
/* 1125 */                          float x3 = MotionEventCompat.getX(ev, i);
/* 1126 */                          float y3 = MotionEventCompat.getY(ev, i);
/* 1127 */                          float dx = x3 - this.mInitialMotionX[pointerId2];
/* 1128 */                          float dy = y3 - this.mInitialMotionY[pointerId2];
/* 1130 */                          reportNewEdgeDrags(dx, dy, pointerId2);
/* 1131 */                          if (this.mDragState != 1) {
/* 1136 */                              View toCapture2 = findTopChildUnder((int) x3, (int) y3);
/* 1137 */                              if (!checkTouchSlop(toCapture2, dx, dy) || !tryCaptureViewForDrag(toCapture2, pointerId2)) {
                                        }
                                    }
/* 1142 */                          saveLastMotion(ev);
                                    break;
                                }
/* 1142 */                      saveLastMotion(ev);
                            }
                            break;
                        case 3:
/* 1187 */                  if (this.mDragState == 1) {
/* 1188 */                      dispatchViewReleased(0.0f, 0.0f);
                            }
/* 1190 */                  cancel();
                            break;
                        case 5:
/* 1082 */                  int pointerId3 = MotionEventCompat.getPointerId(ev, actionIndex);
/* 1083 */                  float x4 = MotionEventCompat.getX(ev, actionIndex);
/* 1084 */                  float y4 = MotionEventCompat.getY(ev, actionIndex);
/* 1086 */                  saveInitialMotion(x4, y4, pointerId3);
/* 1089 */                  if (this.mDragState == 0) {
/* 1093 */                      tryCaptureViewForDrag(findTopChildUnder((int) x4, (int) y4), pointerId3);
/* 1095 */                      int edgesTouched2 = this.mInitialEdgesTouched[pointerId3];
/* 1096 */                      if ((this.mTrackingEdges & edgesTouched2) != 0) {
/* 1097 */                          this.mCallback.onEdgeTouched(this.mTrackingEdges & edgesTouched2, pointerId3);
                                }
                            } else if (isCapturedViewUnder((int) x4, (int) y4)) {
/* 1104 */                      tryCaptureViewForDrag(this.mCapturedView, pointerId3);
                            }
                            break;
                        case 6:
/* 1148 */                  int pointerId4 = MotionEventCompat.getPointerId(ev, actionIndex);
/* 1149 */                  if (this.mDragState == 1 && pointerId4 == this.mActivePointerId) {
/* 1151 */                      int newActivePointer = -1;
/* 1152 */                      int pointerCount2 = MotionEventCompat.getPointerCount(ev);
                                for (int i2 = 0; i2 < pointerCount2; i2++) {
/* 1154 */                          int id = MotionEventCompat.getPointerId(ev, i2);
/* 1155 */                          if (id != this.mActivePointerId) {
/* 1161 */                              if (findTopChildUnder((int) MotionEventCompat.getX(ev, i2), (int) MotionEventCompat.getY(ev, i2)) == this.mCapturedView && tryCaptureViewForDrag(this.mCapturedView, id)) {
/* 1164 */                                  newActivePointer = this.mActivePointerId;
/* 1169 */                                  if (newActivePointer == -1) {
/* 1171 */                                      releaseViewForPointerUp();
                                            }
                                        }
                                    }
                                }
/* 1169 */                      if (newActivePointer == -1) {
/* 1171 */                          releaseViewForPointerUp();
                                }
                            }
/* 1174 */                  clearMotionHistory(pointerId4);
                            break;
                    }
                }

/* 1196 */      private void reportNewEdgeDrags(float dx, float dy, int pointerId) {
/* 1197 */          int dragsStarted = 0;
/* 1198 */          if (checkNewEdgeDrag(dx, dy, pointerId, 1)) {
/* 1199 */              dragsStarted = 0 | 1;
                    }
/* 1201 */          if (checkNewEdgeDrag(dy, dx, pointerId, 4)) {
/* 1202 */              dragsStarted |= 4;
                    }
/* 1204 */          if (checkNewEdgeDrag(dx, dy, pointerId, 2)) {
/* 1205 */              dragsStarted |= 2;
                    }
/* 1207 */          if (checkNewEdgeDrag(dy, dx, pointerId, 8)) {
/* 1208 */              dragsStarted |= 8;
                    }
/* 1211 */          if (dragsStarted != 0) {
/* 1212 */              int[] iArr = this.mEdgeDragsInProgress;
                        iArr[pointerId] = iArr[pointerId] | dragsStarted;
/* 1213 */              this.mCallback.onEdgeDragStarted(dragsStarted, pointerId);
                    }
                }

/* 1217 */      private boolean checkNewEdgeDrag(float delta, float odelta, int pointerId, int edge) {
/* 1218 */          float absDelta = Math.abs(delta);
/* 1219 */          float absODelta = Math.abs(odelta);
/* 1221 */          if ((this.mInitialEdgesTouched[pointerId] & edge) != edge || (this.mTrackingEdges & edge) == 0 || (this.mEdgeDragsLocked[pointerId] & edge) == edge || (this.mEdgeDragsInProgress[pointerId] & edge) == edge) {
                        return false;
                    }
                    if (absDelta <= this.mTouchSlop && absODelta <= this.mTouchSlop) {
                        return false;
                    }
                    if (absDelta >= 0.5f * absODelta || !this.mCallback.onEdgeLock(edge)) {
                        return (this.mEdgeDragsInProgress[pointerId] & edge) == 0 && absDelta > ((float) this.mTouchSlop);
                    }
/* 1228 */          int[] iArr = this.mEdgeDragsLocked;
                    iArr[pointerId] = iArr[pointerId] | edge;
                    return false;
                }

/* 1244 */      private boolean checkTouchSlop(View child, float dx, float dy) {
/* 1245 */          if (child == null) {
                        return false;
                    }
/* 1248 */          boolean checkHorizontal = this.mCallback.getViewHorizontalDragRange(child) > 0;
/* 1249 */          boolean checkVertical = this.mCallback.getViewVerticalDragRange(child) > 0;
/* 1251 */          if (checkHorizontal && checkVertical) {
                        return (dx * dx) + (dy * dy) > ((float) (this.mTouchSlop * this.mTouchSlop));
                    }
/* 1253 */          if (checkHorizontal) {
                        return Math.abs(dx) > ((float) this.mTouchSlop);
                    }
                    return checkVertical && Math.abs(dy) > ((float) this.mTouchSlop);
                }

/* 1275 */      public boolean checkTouchSlop(int directions) {
/* 1276 */          int count = this.mInitialMotionX.length;
                    for (int i = 0; i < count; i++) {
/* 1278 */              if (checkTouchSlop(directions, i)) {
/* 1279 */                  return true;
                        }
                    }
/* 1282 */          return false;
                }

/* 1300 */      public boolean checkTouchSlop(int directions, int pointerId) {
/* 1301 */          if (!isPointerDown(pointerId)) {
                        return false;
                    }
/* 1305 */          boolean checkHorizontal = (directions & 1) == 1;
/* 1306 */          boolean checkVertical = (directions & 2) == 2;
/* 1308 */          float dx = this.mLastMotionX[pointerId] - this.mInitialMotionX[pointerId];
/* 1309 */          float dy = this.mLastMotionY[pointerId] - this.mInitialMotionY[pointerId];
/* 1311 */          if (checkHorizontal && checkVertical) {
                        return (dx * dx) + (dy * dy) > ((float) (this.mTouchSlop * this.mTouchSlop));
                    }
/* 1313 */          if (checkHorizontal) {
                        return Math.abs(dx) > ((float) this.mTouchSlop);
                    }
                    return checkVertical && Math.abs(dy) > ((float) this.mTouchSlop);
                }

/* 1330 */      public boolean isEdgeTouched(int edges) {
/* 1331 */          int count = this.mInitialEdgesTouched.length;
                    for (int i = 0; i < count; i++) {
/* 1333 */              if (isEdgeTouched(edges, i)) {
/* 1334 */                  return true;
                        }
                    }
/* 1337 */          return false;
                }

/* 1350 */      public boolean isEdgeTouched(int edges, int pointerId) {
                    return isPointerDown(pointerId) && (this.mInitialEdgesTouched[pointerId] & edges) != 0;
                }

/* 1354 */      private void releaseViewForPointerUp() {
/* 1355 */          this.mVelocityTracker.computeCurrentVelocity(1000, this.mMaxVelocity);
/* 1356 */          float xvel = clampMag(VelocityTrackerCompat.getXVelocity(this.mVelocityTracker, this.mActivePointerId), this.mMinVelocity, this.mMaxVelocity);
/* 1359 */          float yvel = clampMag(VelocityTrackerCompat.getYVelocity(this.mVelocityTracker, this.mActivePointerId), this.mMinVelocity, this.mMaxVelocity);
/* 1362 */          dispatchViewReleased(xvel, yvel);
                }

/* 1365 */      private void dragTo(int left, int top, int dx, int dy) {
/* 1366 */          int clampedX = left;
/* 1367 */          int clampedY = top;
/* 1368 */          int oldLeft = this.mCapturedView.getLeft();
/* 1369 */          int oldTop = this.mCapturedView.getTop();
/* 1370 */          if (dx != 0) {
/* 1371 */              clampedX = this.mCallback.clampViewPositionHorizontal(this.mCapturedView, left, dx);
/* 1372 */              this.mCapturedView.offsetLeftAndRight(clampedX - oldLeft);
                    }
/* 1374 */          if (dy != 0) {
/* 1375 */              clampedY = this.mCallback.clampViewPositionVertical(this.mCapturedView, top, dy);
/* 1376 */              this.mCapturedView.offsetTopAndBottom(clampedY - oldTop);
                    }
/* 1379 */          if (dx != 0 || dy != 0) {
/* 1380 */              int clampedDx = clampedX - oldLeft;
/* 1381 */              int clampedDy = clampedY - oldTop;
/* 1382 */              this.mCallback.onViewPositionChanged(this.mCapturedView, clampedX, clampedY, clampedDx, clampedDy);
                    }
                }

/* 1396 */      public boolean isCapturedViewUnder(int x, int y) {
/* 1397 */          return isViewUnder(this.mCapturedView, x, y);
                }

/* 1409 */      public boolean isViewUnder(View view, int x, int y) {
                    return view != null && x >= view.getLeft() && x < view.getRight() && y >= view.getTop() && y < view.getBottom();
                }

/* 1427 */      public View findTopChildUnder(int x, int y) {
/* 1428 */          int childCount = this.mParentView.getChildCount();
                    for (int i = childCount - 1; i >= 0; i--) {
/* 1430 */              View child = this.mParentView.getChildAt(this.mCallback.getOrderedChildIndex(i));
/* 1431 */              if (x >= child.getLeft() && x < child.getRight() && y >= child.getTop() && y < child.getBottom()) {
/* 1436 */                  return child;
                        }
                    }
/* 1436 */          return null;
                }

/* 1439 */      private int getEdgesTouched(int x, int y) {
/* 1440 */          int result = x < this.mParentView.getLeft() + this.mEdgeSize ? 0 | 1 : 0;
/* 1443 */          if (y < this.mParentView.getTop() + this.mEdgeSize) {
                        result |= 4;
                    }
/* 1444 */          if (x > this.mParentView.getRight() - this.mEdgeSize) {
                        result |= 2;
                    }
                    return y > this.mParentView.getBottom() - this.mEdgeSize ? result | 8 : result;
                }
            }
