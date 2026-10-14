            package com.jeremyfeinstein.slidingmenu.lib;

            import android.content.Context;
            import android.graphics.Canvas;
            import android.graphics.Rect;
            import android.os.Build;
            import android.support.annotation.NonNull;
            import android.support.v4.view.KeyEventCompat;
            import android.support.v4.view.MotionEventCompat;
            import android.support.v4.view.VelocityTrackerCompat;
            import android.support.v4.view.ViewCompat;
            import android.support.v4.view.ViewConfigurationCompat;
            import android.util.AttributeSet;
            import android.util.FloatMath;
            import android.view.FocusFinder;
            import android.view.KeyEvent;
            import android.view.MotionEvent;
            import android.view.SoundEffectConstants;
            import android.view.VelocityTracker;
            import android.view.View;
            import android.view.ViewConfiguration;
            import android.view.ViewGroup;
            import android.view.animation.Interpolator;
            import android.widget.Scroller;
            import java.util.ArrayList;
            import java.util.List;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 33 */    public class CustomViewAbove extends ViewGroup {
                private static final boolean DEBUG = false;
                private static final int INVALID_POINTER = -1;
                private static final int MAX_SETTLE_DURATION = 600;
                private static final int MIN_DISTANCE_FOR_FLING = 25;
                private static final String TAG = "CustomViewAbove";
                private static final boolean USE_CACHE = false;
                private static final Interpolator sInterpolator = new InterpolatorC12411();
                protected int mActivePointerId;
                private SlidingMenu.OnClosedListener mClosedListener;
                private View mContent;
                private int mCurItem;
                private boolean mEnabled;
                private int mFlingDistance;
                private List<View> mIgnoredViews;
                private float mInitialMotionX;
                private OnPageChangeListener mInternalPageChangeListener;
                private boolean mIsBeingDragged;
                private boolean mIsUnableToDrag;
                private boolean mLastIntersecption;
                private float mLastMotionX;
                private float mLastMotionY;
                protected int mMaximumVelocity;
                private int mMinimumVelocity;
                private OnPageChangeListener mOnPageChangeListener;
                private SlidingMenu.OnOpenedListener mOpenedListener;
                private boolean mQuickReturn;
                private float mScrollX;
                private Scroller mScroller;
                private boolean mScrolling;
                private boolean mScrollingCacheEnabled;
                protected int mTouchMode;
                private int mTouchSlop;
                protected VelocityTracker mVelocityTracker;
                private CustomViewBehind mViewBehind;

                public interface OnPageChangeListener {
                    void onPageScrolled(int i, float f, int i2);

                    void onPageSelected(int i);
                }

/* 34 */        static /* synthetic */ CustomViewBehind access$000(CustomViewAbove x0) {
/* 35 */            return x0.mViewBehind;
                }

                /* JADX INFO: renamed from: com.jeremyfeinstein.slidingmenu.lib.CustomViewAbove$1 */
/* 43 */        static class InterpolatorC12411 implements Interpolator {
/* 44 */            InterpolatorC12411() {
                    }

                    @Override // android.animation.TimeInterpolator
/* 46 */            public float getInterpolation(float t) {
/* 47 */                float t2 = t - 1.0f;
/* 48 */                return (t2 * t2 * t2 * t2 * t2) + 1.0f;
                    }
                }

/* 139 */       public static class SimpleOnPageChangeListener implements OnPageChangeListener {
                    @Override // com.jeremyfeinstein.slidingmenu.lib.CustomViewAbove.OnPageChangeListener
/* 140 */           public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                    }

                    @Override // com.jeremyfeinstein.slidingmenu.lib.CustomViewAbove.OnPageChangeListener
/* 144 */           public void onPageSelected(int position) {
                    }

/* 148 */           public void onPageScrollStateChanged(int state) {
                    }
                }

/* 153 */       public CustomViewAbove(Context context) {
/* 154 */           this(context, null);
                }

/* 157 */       public CustomViewAbove(Context context, AttributeSet attrs) {
/* 158 */           super(context, attrs);
/* 74 */            this.mActivePointerId = -1;
/* 91 */            this.mEnabled = true;
/* 101 */           this.mIgnoredViews = new ArrayList();
/* 568 */           this.mTouchMode = 0;
/* 614 */           this.mQuickReturn = false;
/* 866 */           this.mScrollX = 0.0f;
/* 159 */           initCustomViewAbove();
                }

/* 162 */       void initCustomViewAbove() {
/* 163 */           setWillNotDraw(false);
/* 164 */           setDescendantFocusability(262144);
/* 165 */           setFocusable(true);
/* 166 */           Context context = getContext();
/* 167 */           this.mScroller = new Scroller(context, sInterpolator);
/* 168 */           ViewConfiguration configuration = ViewConfiguration.get(context);
/* 169 */           this.mTouchSlop = ViewConfigurationCompat.getScaledPagingTouchSlop(configuration);
/* 170 */           this.mMinimumVelocity = configuration.getScaledMinimumFlingVelocity();
/* 171 */           this.mMaximumVelocity = configuration.getScaledMaximumFlingVelocity();
/* 172 */           setInternalPageChangeListener(new C12422());
/* 188 */           float density = context.getResources().getDisplayMetrics().density;
/* 189 */           this.mFlingDistance = (int) (25.0f * density);
                }

                /* JADX INFO: renamed from: com.jeremyfeinstein.slidingmenu.lib.CustomViewAbove$2 */
/* 170 */       class C12422 extends SimpleOnPageChangeListener {
/* 171 */           C12422() {
                    }

                    @Override // com.jeremyfeinstein.slidingmenu.lib.CustomViewAbove.SimpleOnPageChangeListener, com.jeremyfeinstein.slidingmenu.lib.CustomViewAbove.OnPageChangeListener
/* 173 */           public void onPageSelected(int position) {
/* 174 */               if (CustomViewAbove.access$000(CustomViewAbove.this) != null) {
                            switch (position) {
                                case 0:
                                case 2:
/* 178 */                           CustomViewAbove.access$000(CustomViewAbove.this).setChildrenEnabled(true);
                                    break;
                                case 1:
/* 181 */                           CustomViewAbove.access$000(CustomViewAbove.this).setChildrenEnabled(false);
                                    break;
                            }
                        }
                    }
                }

/* 199 */       public void setCurrentItem(int item) {
/* 200 */           setCurrentItemInternal(item, true, false);
                }

/* 209 */       public void setCurrentItem(int item, boolean smoothScroll) {
/* 210 */           setCurrentItemInternal(item, smoothScroll, false);
                }

/* 213 */       public int getCurrentItem() {
/* 214 */           return this.mCurItem;
                }

/* 217 */       void setCurrentItemInternal(int item, boolean smoothScroll, boolean always) {
/* 218 */           setCurrentItemInternal(item, smoothScroll, always, 0);
                }

/* 221 */       void setCurrentItemInternal(int item, boolean smoothScroll, boolean always, int velocity) {
/* 222 */           if (!always && this.mCurItem == item) {
/* 223 */               setScrollingCacheEnabled(false);
/* 244 */               return;
                    }
/* 227 */           int item2 = this.mViewBehind.getMenuPage(item);
/* 229 */           boolean dispatchSelected = this.mCurItem != item2;
/* 230 */           this.mCurItem = item2;
/* 231 */           int destX = getDestScrollX(this.mCurItem);
/* 232 */           if (dispatchSelected && this.mOnPageChangeListener != null) {
/* 233 */               this.mOnPageChangeListener.onPageSelected(item2);
                    }
/* 235 */           if (dispatchSelected && this.mInternalPageChangeListener != null) {
/* 236 */               this.mInternalPageChangeListener.onPageSelected(item2);
                    }
/* 238 */           if (smoothScroll) {
/* 239 */               smoothScrollTo(destX, 0, velocity);
                    } else {
/* 241 */               completeScroll();
/* 242 */               scrollTo(destX, 0);
                    }
                }

/* 252 */       public void setOnPageChangeListener(OnPageChangeListener listener) {
/* 253 */           this.mOnPageChangeListener = listener;
                }

/* 265 */       public void setOnOpenedListener(SlidingMenu.OnOpenedListener l) {
/* 266 */           this.mOpenedListener = l;
                }

/* 269 */       public void setOnClosedListener(SlidingMenu.OnClosedListener l) {
/* 270 */           this.mClosedListener = l;
                }

/* 279 */       OnPageChangeListener setInternalPageChangeListener(OnPageChangeListener listener) {
/* 280 */           OnPageChangeListener oldListener = this.mInternalPageChangeListener;
/* 281 */           this.mInternalPageChangeListener = listener;
/* 282 */           return oldListener;
                }

/* 285 */       public void addIgnoredView(View v) {
/* 286 */           if (!this.mIgnoredViews.contains(v)) {
/* 287 */               this.mIgnoredViews.add(v);
                    }
                }

/* 291 */       public void removeIgnoredView(View v) {
/* 292 */           this.mIgnoredViews.remove(v);
                }

/* 295 */       public void clearIgnoredViews() {
/* 296 */           this.mIgnoredViews.clear();
                }

/* 303 */       float distanceInfluenceForSnapDuration(float f) {
/* 306 */           return (float) Math.sin(((double) (f - 0.5f)) * 0.4712389167638204d);
                }

/* 309 */       public int getDestScrollX(int page) {
                    switch (page) {
                        case 0:
                        case 2:
/* 313 */                   return this.mViewBehind.getMenuLeft(this.mContent, page);
                        case 1:
/* 315 */                   return this.mContent.getLeft();
                        default:
/* 317 */                   return 0;
                    }
                }

/* 320 */       private int getLeftBound() {
/* 321 */           return this.mViewBehind.getAbsLeftBound(this.mContent);
                }

/* 324 */       private int getRightBound() {
/* 325 */           return this.mViewBehind.getAbsRightBound(this.mContent);
                }

/* 328 */       public int getContentLeft() {
/* 329 */           return this.mContent.getLeft() + this.mContent.getPaddingLeft();
                }

/* 332 */       public boolean isMenuOpen() {
                    return this.mCurItem == 0 || this.mCurItem == 2;
                }

/* 336 */       private boolean isInIgnoredView(MotionEvent ev) {
/* 337 */           Rect rect = new Rect();
                    for (View v : this.mIgnoredViews) {
/* 339 */               v.getHitRect(rect);
/* 340 */               if (rect.contains((int) ev.getX(), (int) ev.getY())) {
                            return true;
                        }
                    }
                    return false;
                }

/* 345 */       public int getBehindWidth() {
/* 346 */           if (this.mViewBehind == null) {
/* 347 */               return 0;
                    }
                    return this.mViewBehind.getBehindWidth();
                }

/* 353 */       public int getChildWidth(int i) {
                    switch (i) {
                        case 0:
/* 356 */                   return getBehindWidth();
                        case 1:
/* 358 */                   return this.mContent.getWidth();
                        default:
/* 360 */                   return 0;
                    }
                }

/* 364 */       public boolean isSlidingEnabled() {
/* 365 */           return this.mEnabled;
                }

/* 368 */       public void setSlidingEnabled(boolean b) {
/* 369 */           this.mEnabled = b;
                }

/* 378 */       void smoothScrollTo(int x, int y) {
/* 379 */           smoothScrollTo(x, y, 0);
                }

/* 389 */       void smoothScrollTo(int x, int y, int velocity) {
                    int duration;
/* 390 */           if (getChildCount() == 0) {
/* 392 */               setScrollingCacheEnabled(false);
/* 433 */               return;
                    }
/* 395 */           int sx = getScrollX();
/* 396 */           int sy = getScrollY();
/* 397 */           int dx = x - sx;
/* 398 */           int dy = y - sy;
/* 399 */           if (dx == 0 && dy == 0) {
/* 400 */               completeScroll();
/* 401 */               if (isMenuOpen()) {
/* 402 */                   if (this.mOpenedListener != null) {
/* 403 */                       this.mOpenedListener.onOpened();
/* 433 */                       return;
                            }
/* 433 */                   return;
                        } else {
/* 405 */                   if (this.mClosedListener != null) {
/* 406 */                       this.mClosedListener.onClosed();
/* 433 */                       return;
                            }
/* 433 */                   return;
                        }
                    }
/* 411 */           setScrollingCacheEnabled(true);
/* 412 */           this.mScrolling = true;
/* 414 */           int width = getBehindWidth();
/* 415 */           int halfWidth = width / 2;
/* 416 */           float distanceRatio = Math.min(1.0f, (1.0f * Math.abs(dx)) / width);
/* 417 */           float distance = halfWidth + (halfWidth * distanceInfluenceForSnapDuration(distanceRatio));
/* 421 */           int velocity2 = Math.abs(velocity);
/* 422 */           if (velocity2 > 0) {
/* 423 */               duration = Math.round(1000.0f * Math.abs(distance / velocity2)) * 4;
                    } else {
/* 425 */               float pageDelta = Math.abs(dx) / width;
/* 427 */               duration = MAX_SETTLE_DURATION;
                    }
/* 431 */           this.mScroller.startScroll(sx, sy, dx, dy, Math.min(duration, MAX_SETTLE_DURATION));
/* 432 */           invalidate();
                }

/* 435 */       public void setContent(View v) {
/* 436 */           if (this.mContent != null) {
/* 437 */               removeView(this.mContent);
                    }
/* 438 */           this.mContent = v;
/* 439 */           addView(this.mContent);
                }

/* 442 */       public View getContent() {
/* 443 */           return this.mContent;
                }

/* 446 */       public void setCustomViewBehind(CustomViewBehind cvb) {
/* 447 */           this.mViewBehind = cvb;
                }

                @Override // android.view.View
/* 452 */       protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
/* 453 */           int width = getDefaultSize(0, widthMeasureSpec);
/* 454 */           int height = getDefaultSize(0, heightMeasureSpec);
/* 455 */           setMeasuredDimension(width, height);
/* 457 */           int contentWidth = getChildMeasureSpec(widthMeasureSpec, 0, width);
/* 458 */           int contentHeight = getChildMeasureSpec(heightMeasureSpec, 0, height);
/* 459 */           this.mContent.measure(contentWidth, contentHeight);
                }

                @Override // android.view.View
/* 463 */       protected void onSizeChanged(int w, int h, int oldw, int oldh) {
/* 464 */           super.onSizeChanged(w, h, oldw, oldh);
/* 466 */           if (w != oldw) {
/* 470 */               completeScroll();
/* 471 */               scrollTo(getDestScrollX(this.mCurItem), getScrollY());
                    }
                }

                @Override // android.view.ViewGroup, android.view.View
/* 476 */       protected void onLayout(boolean changed, int l, int t, int r, int b) {
/* 477 */           int width = r - l;
/* 478 */           int height = b - t;
/* 479 */           this.mContent.layout(0, 0, width, height);
                }

/* 484 */       public void setAboveOffset(int i) {
/* 485 */           this.mContent.setPadding(i, this.mContent.getPaddingTop(), this.mContent.getPaddingRight(), this.mContent.getPaddingBottom());
                }

                @Override // android.view.View
/* 491 */       public void computeScroll() {
/* 492 */           if (!this.mScroller.isFinished() && this.mScroller.computeScrollOffset()) {
/* 494 */               int oldX = getScrollX();
/* 495 */               int oldY = getScrollY();
/* 496 */               int x = this.mScroller.getCurrX();
/* 497 */               int y = this.mScroller.getCurrY();
/* 499 */               if (oldX != x || oldY != y) {
/* 500 */                   scrollTo(x, y);
/* 501 */                   pageScrolled(x);
                        }
/* 505 */               invalidate();
/* 512 */               return;
                    }
/* 511 */           completeScroll();
                }

/* 514 */       private void pageScrolled(int xpos) {
/* 515 */           int widthWithMargin = getWidth();
/* 516 */           int position = xpos / widthWithMargin;
/* 517 */           int offsetPixels = xpos % widthWithMargin;
/* 518 */           float offset = offsetPixels / widthWithMargin;
/* 520 */           onPageScrolled(position, offset, offsetPixels);
                }

/* 535 */       protected void onPageScrolled(int position, float offset, int offsetPixels) {
/* 536 */           if (this.mOnPageChangeListener != null) {
/* 537 */               this.mOnPageChangeListener.onPageScrolled(position, offset, offsetPixels);
                    }
/* 539 */           if (this.mInternalPageChangeListener != null) {
/* 540 */               this.mInternalPageChangeListener.onPageScrolled(position, offset, offsetPixels);
                    }
                }

/* 544 */       private void completeScroll() {
/* 545 */           boolean needPopulate = this.mScrolling;
/* 546 */           if (needPopulate) {
/* 548 */               setScrollingCacheEnabled(false);
/* 549 */               this.mScroller.abortAnimation();
/* 550 */               int oldX = getScrollX();
/* 551 */               int oldY = getScrollY();
/* 552 */               int x = this.mScroller.getCurrX();
/* 553 */               int y = this.mScroller.getCurrY();
/* 554 */               if (oldX != x || oldY != y) {
/* 555 */                   scrollTo(x, y);
                        }
/* 557 */               if (isMenuOpen()) {
/* 558 */                   if (this.mOpenedListener != null) {
/* 559 */                       this.mOpenedListener.onOpened();
                            }
                        } else if (this.mClosedListener != null) {
/* 562 */                   this.mClosedListener.onClosed();
                        }
                    }
/* 565 */           this.mScrolling = false;
                }

/* 570 */       public void setTouchMode(int i) {
/* 571 */           this.mTouchMode = i;
                }

/* 574 */       public int getTouchMode() {
/* 575 */           return this.mTouchMode;
                }

/* 578 */       private boolean thisTouchAllowed(MotionEvent ev) {
/* 579 */           int x = (int) (ev.getX() + this.mScrollX);
/* 580 */           if (isMenuOpen()) {
/* 581 */               return this.mViewBehind.menuOpenTouchAllowed(this.mContent, this.mCurItem, x);
                    }
                    switch (this.mTouchMode) {
                        case 0:
/* 589 */                   return this.mViewBehind.marginTouchAllowed(this.mContent, x);
                        case 1:
                            return !isInIgnoredView(ev);
                        case 2:
                        default:
                            return false;
                    }
                }

/* 595 */       private boolean thisSlideAllowed(float dx) {
/* 597 */           if (isMenuOpen()) {
/* 598 */               boolean allowed = this.mViewBehind.menuOpenSlideAllowed(dx);
/* 604 */               return allowed;
                    }
/* 600 */           boolean allowed2 = this.mViewBehind.menuClosedSlideAllowed(dx);
/* 604 */           return allowed2;
                }

/* 607 */       private int getPointerIndex(MotionEvent ev, int id) {
/* 608 */           int activePointerIndex = MotionEventCompat.findPointerIndex(ev, id);
/* 609 */           if (activePointerIndex == -1) {
/* 610 */               this.mActivePointerId = -1;
                    }
/* 611 */           return activePointerIndex;
                }

/* 616 */       public void addMovement(MotionEvent ev) {
/* 617 */           if (this.mVelocityTracker == null) {
/* 618 */               this.mVelocityTracker = VelocityTracker.obtain();
                    }
/* 620 */           if (this.mVelocityTracker != null) {
/* 621 */               this.mVelocityTracker.addMovement(ev);
                    }
                }

                @Override // android.view.ViewGroup
/* 627 */       public boolean onInterceptTouchEvent(MotionEvent ev) {
/* 628 */           if (!this.mEnabled) {
                        return false;
                    }
/* 631 */           int action = ev.getAction() & 255;
/* 637 */           if (action == 3 || action == 1 || (action != 0 && this.mIsUnableToDrag)) {
/* 639 */               endDrag();
/* 643 */               this.mLastIntersecption = false;
                        return false;
                    }
                    switch (action) {
                        case 0:
/* 652 */                   int index = MotionEventCompat.getActionIndex(ev);
/* 653 */                   this.mActivePointerId = MotionEventCompat.getPointerId(ev, index);
/* 654 */                   if (this.mActivePointerId != -1) {
/* 656 */                       float x = MotionEventCompat.getX(ev, index);
                                this.mInitialMotionX = x;
                                this.mLastMotionX = x;
/* 657 */                       this.mLastMotionY = MotionEventCompat.getY(ev, index);
/* 658 */                       if (thisTouchAllowed(ev)) {
/* 659 */                           this.mIsBeingDragged = false;
/* 660 */                           this.mIsUnableToDrag = false;
/* 661 */                           if (isMenuOpen() && this.mViewBehind.menuTouchInQuickReturn(this.mContent, this.mCurItem, ev.getX() + this.mScrollX)) {
/* 662 */                               this.mQuickReturn = true;
                                    }
                                } else {
/* 665 */                           this.mIsUnableToDrag = true;
                                }
                            }
                            break;
                        case 2:
/* 649 */                   determineDrag(ev);
                            break;
                        case 6:
/* 669 */                   onSecondaryPointerUp(ev);
                            break;
                    }
/* 673 */           if (!this.mIsBeingDragged) {
/* 674 */               addMovement(ev);
                    }
/* 677 */           this.mLastIntersecption = this.mIsBeingDragged || this.mQuickReturn;
                    return this.mIsBeingDragged || this.mQuickReturn;
                }

/* 687 */       public void setInitialTouchEvents(float initialX, int activePointerId) {
/* 688 */           this.mInitialMotionX = initialX;
/* 689 */           this.mLastMotionX = initialX;
/* 690 */           this.mActivePointerId = activePointerId;
/* 691 */           this.mIsBeingDragged = true;
                }

                /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
                @Override // android.view.View
/* 700 */       public boolean onTouchEvent(@NonNull MotionEvent ev) {
/* 701 */           if (!this.mEnabled) {
/* 702 */               return false;
                    }
/* 704 */           if (!this.mIsBeingDragged && !thisTouchAllowed(ev)) {
/* 705 */               return false;
                    }
/* 710 */           int action = ev.getAction();
/* 712 */           if (this.mVelocityTracker == null) {
/* 713 */               this.mVelocityTracker = VelocityTracker.obtain();
                    }
/* 715 */           this.mVelocityTracker.addMovement(ev);
                    switch (action & 255) {
                        case 0:
/* 723 */                   completeScroll();
/* 726 */                   int index = MotionEventCompat.getActionIndex(ev);
/* 727 */                   this.mActivePointerId = MotionEventCompat.getPointerId(ev, index);
/* 728 */                   float x = ev.getX();
                            this.mInitialMotionX = x;
                            this.mLastMotionX = x;
/* 805 */                   return true;
                        case 1:
/* 760 */                   if (this.mIsBeingDragged) {
/* 761 */                       VelocityTracker velocityTracker = this.mVelocityTracker;
/* 762 */                       velocityTracker.computeCurrentVelocity(1000, this.mMaximumVelocity);
/* 763 */                       int initialVelocity = (int) VelocityTrackerCompat.getXVelocity(velocityTracker, this.mActivePointerId);
/* 766 */                       float pageOffset = (getScrollX() - getDestScrollX(this.mCurItem)) / getBehindWidth();
/* 767 */                       int activePointerIndex = getPointerIndex(ev, this.mActivePointerId);
/* 768 */                       if (this.mActivePointerId != -1) {
/* 770 */                           int totalDelta = (int) (MotionEventCompat.getX(ev, activePointerIndex) - this.mInitialMotionX);
/* 771 */                           int nextPage = determineTargetPage(pageOffset, initialVelocity, totalDelta);
/* 772 */                           setCurrentItemInternal(nextPage, true, true, initialVelocity);
                                } else {
/* 774 */                           setCurrentItemInternal(this.mCurItem, true, true, initialVelocity);
                                }
/* 776 */                       this.mActivePointerId = -1;
/* 777 */                       endDrag();
                            } else if (this.mQuickReturn && this.mViewBehind.menuTouchInQuickReturn(this.mContent, this.mCurItem, ev.getX() + this.mScrollX)) {
/* 780 */                       setCurrentItem(1);
/* 781 */                       endDrag();
                            }
/* 805 */                   return true;
                        case 2:
/* 731 */                   if (!this.mIsBeingDragged) {
/* 732 */                       determineDrag(ev);
/* 733 */                       if (this.mIsUnableToDrag) {
/* 734 */                           return false;
                                }
                            }
/* 736 */                   if (this.mIsBeingDragged) {
/* 738 */                       int activePointerIndex2 = getPointerIndex(ev, this.mActivePointerId);
/* 739 */                       if (this.mActivePointerId != -1) {
/* 741 */                           float x2 = MotionEventCompat.getX(ev, activePointerIndex2);
/* 742 */                           float deltaX = this.mLastMotionX - x2;
/* 743 */                           this.mLastMotionX = x2;
/* 744 */                           float oldScrollX = getScrollX();
/* 745 */                           float scrollX = oldScrollX + deltaX;
/* 746 */                           float leftBound = getLeftBound();
/* 747 */                           float rightBound = getRightBound();
                                    if (scrollX < leftBound) {
/* 749 */                               scrollX = leftBound;
                                    } else if (scrollX > rightBound) {
/* 751 */                               scrollX = rightBound;
                                    }
/* 754 */                           this.mLastMotionX += scrollX - ((int) scrollX);
/* 755 */                           scrollTo((int) scrollX, getScrollY());
/* 756 */                           pageScrolled((int) scrollX);
                                }
                            }
/* 805 */                   return true;
                        case 3:
/* 785 */                   if (this.mIsBeingDragged) {
/* 786 */                       setCurrentItemInternal(this.mCurItem, true, true);
/* 787 */                       this.mActivePointerId = -1;
/* 788 */                       endDrag();
                            }
/* 805 */                   return true;
                        case 4:
                        default:
/* 805 */                   return true;
                        case 5:
/* 792 */                   int indexx = MotionEventCompat.getActionIndex(ev);
/* 793 */                   this.mLastMotionX = MotionEventCompat.getX(ev, indexx);
/* 794 */                   this.mActivePointerId = MotionEventCompat.getPointerId(ev, indexx);
/* 805 */                   return true;
                        case 6:
/* 798 */                   onSecondaryPointerUp(ev);
/* 799 */                   int pointerIndex = getPointerIndex(ev, this.mActivePointerId);
/* 800 */                   if (this.mActivePointerId != -1) {
/* 802 */                       this.mLastMotionX = MotionEventCompat.getX(ev, pointerIndex);
                            }
/* 805 */                   return true;
                    }
                }

/* 808 */       public void determineDrag(MotionEvent ev) {
/* 809 */           int activePointerId = this.mActivePointerId;
/* 810 */           int pointerIndex = getPointerIndex(ev, activePointerId);
/* 811 */           if (activePointerId != -1 && pointerIndex != -1) {
/* 813 */               float x = MotionEventCompat.getX(ev, pointerIndex);
/* 814 */               float dx = x - this.mLastMotionX;
/* 815 */               float xDiff = Math.abs(dx);
/* 816 */               float y = MotionEventCompat.getY(ev, pointerIndex);
/* 817 */               float dy = y - this.mLastMotionY;
/* 818 */               float yDiff = Math.abs(dy);
                        if (xDiff <= (isMenuOpen() ? this.mTouchSlop / 2 : this.mTouchSlop) || xDiff <= yDiff || !thisSlideAllowed(dx)) {
                            if (xDiff > this.mTouchSlop) {
/* 826 */                       this.mIsUnableToDrag = true;
                            }
                        } else {
/* 820 */                   startDrag();
/* 821 */                   this.mLastMotionX = x;
/* 822 */                   this.mLastMotionY = y;
/* 823 */                   setScrollingCacheEnabled(true);
                        }
                    }
                }

                @Override // android.view.View
/* 831 */       public void scrollTo(int x, int y) {
/* 832 */           super.scrollTo(x, y);
/* 833 */           this.mScrollX = x;
/* 834 */           this.mViewBehind.scrollBehindTo(this.mContent, x, y);
/* 835 */           ((SlidingMenu) getParent()).manageLayers(getPercentOpen());
                }

/* 838 */       private int determineTargetPage(float pageOffset, int velocity, int deltaX) {
/* 839 */           int targetPage = this.mCurItem;
/* 840 */           if (Math.abs(deltaX) > this.mFlingDistance && Math.abs(velocity) > this.mMinimumVelocity) {
/* 841 */               if (velocity > 0 && deltaX > 0) {
/* 842 */                   return targetPage - 1;
                        }
/* 843 */               if (velocity < 0 && deltaX < 0) {
/* 844 */                   return targetPage + 1;
                        }
/* 849 */               return targetPage;
                    }
/* 847 */           return Math.round(this.mCurItem + pageOffset);
                }

/* 852 */       protected float getPercentOpen() {
/* 853 */           return Math.abs(this.mScrollX - this.mContent.getLeft()) / getBehindWidth();
                }

                @Override // android.view.ViewGroup, android.view.View
/* 857 */       protected void dispatchDraw(Canvas canvas) {
/* 858 */           super.dispatchDraw(canvas);
/* 860 */           this.mViewBehind.drawShadow(this.mContent, canvas);
/* 861 */           this.mViewBehind.drawFade(this.mContent, canvas, getPercentOpen());
/* 862 */           this.mViewBehind.drawSelector(this.mContent, canvas, getPercentOpen());
                }

/* 869 */       private void onSecondaryPointerUp(MotionEvent ev) {
/* 870 */           int pointerIndex = MotionEventCompat.getActionIndex(ev);
/* 871 */           int pointerId = MotionEventCompat.getPointerId(ev, pointerIndex);
/* 872 */           if (pointerId == this.mActivePointerId) {
/* 875 */               int newPointerIndex = pointerIndex == 0 ? 1 : 0;
/* 876 */               this.mLastMotionX = MotionEventCompat.getX(ev, newPointerIndex);
/* 877 */               this.mActivePointerId = MotionEventCompat.getPointerId(ev, newPointerIndex);
/* 878 */               if (this.mVelocityTracker != null) {
/* 879 */                   this.mVelocityTracker.clear();
                        }
                    }
                }

/* 884 */       private void startDrag() {
/* 885 */           this.mIsBeingDragged = true;
/* 886 */           this.mQuickReturn = false;
                }

/* 889 */       private void endDrag() {
/* 890 */           this.mQuickReturn = false;
/* 891 */           this.mIsBeingDragged = false;
/* 892 */           this.mIsUnableToDrag = false;
/* 893 */           this.mActivePointerId = -1;
/* 895 */           if (this.mVelocityTracker != null) {
/* 896 */               this.mVelocityTracker.recycle();
/* 897 */               this.mVelocityTracker = null;
                    }
                }

/* 901 */       private void setScrollingCacheEnabled(boolean enabled) {
/* 902 */           if (this.mScrollingCacheEnabled != enabled) {
/* 903 */               this.mScrollingCacheEnabled = enabled;
                    }
                }

/* 927 */       protected boolean canScroll(View v, boolean checkV, int dx, int x, int y) {
/* 928 */           if (v instanceof ViewGroup) {
/* 929 */               ViewGroup group = (ViewGroup) v;
/* 930 */               int scrollX = v.getScrollX();
/* 931 */               int scrollY = v.getScrollY();
/* 932 */               int count = group.getChildCount();
                        for (int i = count - 1; i >= 0; i--) {
/* 935 */                   View child = group.getChildAt(i);
/* 936 */                   if (x + scrollX >= child.getLeft() && x + scrollX < child.getRight() && y + scrollY >= child.getTop() && y + scrollY < child.getBottom() && canScroll(child, true, dx, (x + scrollX) - child.getLeft(), (y + scrollY) - child.getTop())) {
/* 940 */                       return true;
                            }
                        }
                    }
                    return checkV && ViewCompat.canScrollHorizontally(v, -dx);
                }

                @Override // android.view.ViewGroup, android.view.View
/* 951 */       public boolean dispatchKeyEvent(KeyEvent event) {
                    return super.dispatchKeyEvent(event) || executeKeyEvent(event);
                }

/* 963 */       public boolean executeKeyEvent(KeyEvent event) {
/* 965 */           if (event.getAction() != 0) {
/* 964 */               return false;
                    }
                    switch (event.getKeyCode()) {
                        case 21:
/* 968 */                   boolean handled = arrowScroll(17);
/* 986 */                   return handled;
                        case 22:
/* 971 */                   boolean handled2 = arrowScroll(66);
/* 986 */                   return handled2;
                        case 61:
/* 974 */                   if (Build.VERSION.SDK_INT < 11) {
/* 964 */                       return false;
                            }
/* 977 */                   if (KeyEventCompat.hasNoModifiers(event)) {
/* 978 */                       boolean handled3 = arrowScroll(2);
/* 986 */                       return handled3;
                            }
/* 979 */                   if (!KeyEventCompat.hasModifiers(event, 1)) {
/* 964 */                       return false;
                            }
/* 980 */                   boolean handled4 = arrowScroll(1);
/* 986 */                   return handled4;
                        default:
/* 964 */                   return false;
                    }
                }

/* 989 */       public boolean arrowScroll(int direction) {
/* 990 */           View currentFocused = findFocus();
/* 991 */           if (currentFocused == this) {
                        currentFocused = null;
                    }
/* 993 */           boolean handled = false;
/* 995 */           View nextFocused = FocusFinder.getInstance().findNextFocus(this, currentFocused, direction);
/* 997 */           if (nextFocused != null && nextFocused != currentFocused) {
/* 998 */               if (direction == 17) {
/* 999 */                   handled = nextFocused.requestFocus();
                        } else if (direction == 66) {
/* 1006 */                  handled = (currentFocused == null || nextFocused.getLeft() > currentFocused.getLeft()) ? nextFocused.requestFocus() : pageRight();
                        }
                    } else if (direction == 17 || direction == 1) {
/* 1011 */              handled = pageLeft();
                    } else if (direction == 66 || direction == 2) {
/* 1014 */              handled = pageRight();
                    }
/* 1016 */          if (handled) {
/* 1017 */              playSoundEffect(SoundEffectConstants.getContantForFocusDirection(direction));
                    }
/* 1019 */          return handled;
                }

/* 1022 */      boolean pageLeft() {
/* 1023 */          if (this.mCurItem <= 0) {
                        return false;
                    }
/* 1024 */          setCurrentItem(this.mCurItem - 1, true);
                    return true;
                }

/* 1030 */      boolean pageRight() {
/* 1031 */          if (this.mCurItem >= 1) {
                        return false;
                    }
/* 1032 */          setCurrentItem(this.mCurItem + 1, true);
                    return true;
                }
            }
