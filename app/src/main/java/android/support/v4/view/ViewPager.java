            package android.support.v4.view;

            import android.R;
            import android.content.Context;
            import android.content.res.Resources;
            import android.content.res.TypedArray;
            import android.database.DataSetObserver;
            import android.graphics.Canvas;
            import android.graphics.Rect;
            import android.graphics.drawable.Drawable;
            import android.os.Build;
            import android.os.Bundle;
            import android.os.Parcel;
            import android.os.Parcelable;
            import android.os.SystemClock;
            import android.support.v4.os.ParcelableCompat;
            import android.support.v4.os.ParcelableCompatCreatorCallbacks;
            import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
            import android.support.v4.view.accessibility.AccessibilityRecordCompat;
            import android.support.v4.widget.EdgeEffectCompat;
            import android.util.AttributeSet;
            import android.util.Log;
            import android.view.FocusFinder;
            import android.view.KeyEvent;
            import android.view.MotionEvent;
            import android.view.SoundEffectConstants;
            import android.view.VelocityTracker;
            import android.view.View;
            import android.view.ViewConfiguration;
            import android.view.ViewGroup;
            import android.view.ViewParent;
            import android.view.accessibility.AccessibilityEvent;
            import android.view.animation.Interpolator;
            import android.widget.Scroller;
            import java.lang.reflect.Method;
            import java.util.ArrayList;
            import java.util.Collections;
            import java.util.Comparator;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 82 */    public class ViewPager extends ViewGroup {
                private static final int CLOSE_ENOUGH = 2;
                private static final boolean DEBUG = false;
                private static final int DEFAULT_GUTTER_SIZE = 16;
                private static final int DEFAULT_OFFSCREEN_PAGES = 1;
                private static final int DRAW_ORDER_DEFAULT = 0;
                private static final int DRAW_ORDER_FORWARD = 1;
                private static final int DRAW_ORDER_REVERSE = 2;
                private static final int INVALID_POINTER = -1;
                private static final int MAX_SETTLE_DURATION = 600;
                private static final int MIN_DISTANCE_FOR_FLING = 25;
                private static final int MIN_FLING_VELOCITY = 400;
                public static final int SCROLL_STATE_DRAGGING = 1;
                public static final int SCROLL_STATE_IDLE = 0;
                public static final int SCROLL_STATE_SETTLING = 2;
                private static final String TAG = "ViewPager";
                private static final boolean USE_CACHE = false;
                private int mActivePointerId;
                private PagerAdapter mAdapter;
                private OnAdapterChangeListener mAdapterChangeListener;
                private int mBottomPageBounds;
                private boolean mCalledSuper;
                private int mChildHeightMeasureSpec;
                private int mChildWidthMeasureSpec;
                private int mCloseEnough;
                private int mCurItem;
                private int mDecorChildCount;
                private int mDefaultGutterSize;
                private int mDrawingOrder;
                private ArrayList<View> mDrawingOrderedChildren;
                private final Runnable mEndScrollRunnable;
                private int mExpectedAdapterCount;
                private long mFakeDragBeginTime;
                private boolean mFakeDragging;
                private boolean mFirstLayout;
                private float mFirstOffset;
                private int mFlingDistance;
                private int mGutterSize;
                private boolean mIgnoreGutter;
                private boolean mInLayout;
                private float mInitialMotionX;
                private float mInitialMotionY;
                private OnPageChangeListener mInternalPageChangeListener;
                private boolean mIsBeingDragged;
                private boolean mIsUnableToDrag;
                private final ArrayList<ItemInfo> mItems;
                private float mLastMotionX;
                private float mLastMotionY;
                private float mLastOffset;
                private EdgeEffectCompat mLeftEdge;
                private Drawable mMarginDrawable;
                private int mMaximumVelocity;
                private int mMinimumVelocity;
                private boolean mNeedCalculatePageOffsets;
                private PagerObserver mObserver;
                private int mOffscreenPageLimit;
                private OnPageChangeListener mOnPageChangeListener;
                private int mPageMargin;
                private PageTransformer mPageTransformer;
                private boolean mPopulatePending;
                private Parcelable mRestoredAdapterState;
                private ClassLoader mRestoredClassLoader;
                private int mRestoredCurItem;
                private EdgeEffectCompat mRightEdge;
                private int mScrollState;
                private Scroller mScroller;
                private boolean mScrollingCacheEnabled;
                private Method mSetChildrenDrawingOrderEnabled;
                private final ItemInfo mTempItem;
                private final Rect mTempRect;
                private int mTopPageBounds;
                private int mTouchSlop;
                private VelocityTracker mVelocityTracker;
                private static final int[] LAYOUT_ATTRS = {R.attr.layout_gravity};
                private static final Comparator<ItemInfo> COMPARATOR = new C00511();
                private static final Interpolator sInterpolator = new InterpolatorC00522();
                private static final ViewPositionComparator sPositionComparator = new ViewPositionComparator();

                interface Decor {
                }

                interface OnAdapterChangeListener {
                    void onAdapterChanged(PagerAdapter pagerAdapter, PagerAdapter pagerAdapter2);
                }

                public interface OnPageChangeListener {
                    void onPageScrollStateChanged(int i);

                    void onPageScrolled(int i, float f, int i2);

                    void onPageSelected(int i);
                }

                public interface PageTransformer {
                    void transformPage(View view, float f);
                }

/* 83 */        static /* synthetic */ void access$000(ViewPager x0, int x1) {
/* 84 */            x0.setScrollState(x1);
                }

/* 83 */        static /* synthetic */ PagerAdapter access$200(ViewPager x0) {
/* 84 */            return x0.mAdapter;
                }

/* 83 */        static /* synthetic */ int access$300(ViewPager x0) {
/* 84 */            return x0.mCurItem;
                }

/* 83 */        static /* synthetic */ int[] access$400() {
/* 84 */            return LAYOUT_ATTRS;
                }

/* 106 */       static class ItemInfo {
                    Object object;
                    float offset;
                    int position;
                    boolean scrolling;
                    float widthFactor;

/* 107 */           ItemInfo() {
                    }
                }

                /* JADX INFO: renamed from: android.support.v4.view.ViewPager$1 */
/* 114 */       static class C00511 implements Comparator<ItemInfo> {
/* 115 */           C00511() {
                    }

                    @Override // java.util.Comparator
/* 115 */           public /* bridge */ /* synthetic */ int compare(ItemInfo itemInfo, ItemInfo itemInfo2) {
/* 116 */               return compare2(itemInfo, itemInfo2);
                    }

                    /* JADX INFO: renamed from: compare, reason: avoid collision after fix types in other method */
/* 118 */           public int compare2(ItemInfo lhs, ItemInfo rhs) {
/* 119 */               return lhs.position - rhs.position;
                    }
                }

                /* JADX INFO: renamed from: android.support.v4.view.ViewPager$2 */
/* 121 */       static class InterpolatorC00522 implements Interpolator {
/* 122 */           InterpolatorC00522() {
                    }

                    @Override // android.animation.TimeInterpolator
/* 124 */           public float getInterpolation(float t) {
/* 125 */               float t2 = t - 1.0f;
/* 126 */               return (t2 * t2 * t2 * t2 * t2) + 1.0f;
                    }
                }

                /* JADX INFO: renamed from: android.support.v4.view.ViewPager$3 */
/* 239 */       class RunnableC00533 implements Runnable {
/* 240 */           RunnableC00533() {
                    }

                    @Override // java.lang.Runnable
/* 242 */           public void run() {
/* 243 */               ViewPager.access$000(ViewPager.this, 0);
/* 244 */               ViewPager.this.populate();
                    }
                }

/* 294 */       public static class SimpleOnPageChangeListener implements OnPageChangeListener {
                    @Override // android.support.v4.view.ViewPager.OnPageChangeListener
/* 295 */           public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                    }

                    @Override // android.support.v4.view.ViewPager.OnPageChangeListener
/* 300 */           public void onPageSelected(int position) {
                    }

                    @Override // android.support.v4.view.ViewPager.OnPageChangeListener
/* 305 */           public void onPageScrollStateChanged(int state) {
                    }
                }

/* 343 */       public ViewPager(Context context) {
/* 344 */           super(context);
/* 130 */           this.mItems = new ArrayList<>();
/* 131 */           this.mTempItem = new ItemInfo();
/* 133 */           this.mTempRect = new Rect();
/* 137 */           this.mRestoredCurItem = -1;
/* 138 */           this.mRestoredAdapterState = null;
/* 139 */           this.mRestoredClassLoader = null;
/* 151 */           this.mFirstOffset = -3.4028235E38f;
/* 152 */           this.mLastOffset = Float.MAX_VALUE;
/* 161 */           this.mOffscreenPageLimit = 1;
/* 180 */           this.mActivePointerId = -1;
/* 207 */           this.mFirstLayout = true;
/* 208 */           this.mNeedCalculatePageOffsets = false;
/* 241 */           this.mEndScrollRunnable = new RunnableC00533();
/* 248 */           this.mScrollState = 0;
/* 345 */           initViewPager();
                }

/* 348 */       public ViewPager(Context context, AttributeSet attrs) {
/* 349 */           super(context, attrs);
/* 130 */           this.mItems = new ArrayList<>();
/* 131 */           this.mTempItem = new ItemInfo();
/* 133 */           this.mTempRect = new Rect();
/* 137 */           this.mRestoredCurItem = -1;
/* 138 */           this.mRestoredAdapterState = null;
/* 139 */           this.mRestoredClassLoader = null;
/* 151 */           this.mFirstOffset = -3.4028235E38f;
/* 152 */           this.mLastOffset = Float.MAX_VALUE;
/* 161 */           this.mOffscreenPageLimit = 1;
/* 180 */           this.mActivePointerId = -1;
/* 207 */           this.mFirstLayout = true;
/* 208 */           this.mNeedCalculatePageOffsets = false;
/* 241 */           this.mEndScrollRunnable = new RunnableC00533();
/* 248 */           this.mScrollState = 0;
/* 350 */           initViewPager();
                }

/* 353 */       void initViewPager() {
/* 354 */           setWillNotDraw(false);
/* 355 */           setDescendantFocusability(262144);
/* 356 */           setFocusable(true);
/* 357 */           Context context = getContext();
/* 358 */           this.mScroller = new Scroller(context, sInterpolator);
/* 359 */           ViewConfiguration configuration = ViewConfiguration.get(context);
/* 360 */           float density = context.getResources().getDisplayMetrics().density;
/* 362 */           this.mTouchSlop = ViewConfigurationCompat.getScaledPagingTouchSlop(configuration);
/* 363 */           this.mMinimumVelocity = (int) (400.0f * density);
/* 364 */           this.mMaximumVelocity = configuration.getScaledMaximumFlingVelocity();
/* 365 */           this.mLeftEdge = new EdgeEffectCompat(context);
/* 366 */           this.mRightEdge = new EdgeEffectCompat(context);
/* 368 */           this.mFlingDistance = (int) (25.0f * density);
/* 369 */           this.mCloseEnough = (int) (2.0f * density);
/* 370 */           this.mDefaultGutterSize = (int) (16.0f * density);
/* 372 */           ViewCompat.setAccessibilityDelegate(this, new MyAccessibilityDelegate());
/* 374 */           if (ViewCompat.getImportantForAccessibility(this) == 0) {
/* 376 */               ViewCompat.setImportantForAccessibility(this, 1);
                    }
                }

                @Override // android.view.ViewGroup, android.view.View
/* 382 */       protected void onDetachedFromWindow() {
/* 383 */           removeCallbacks(this.mEndScrollRunnable);
/* 384 */           super.onDetachedFromWindow();
                }

/* 387 */       private void setScrollState(int newState) {
/* 388 */           if (this.mScrollState != newState) {
/* 392 */               this.mScrollState = newState;
/* 393 */               if (this.mPageTransformer != null) {
/* 395 */                   enableLayers(newState != 0);
                        }
/* 397 */               if (this.mOnPageChangeListener != null) {
/* 398 */                   this.mOnPageChangeListener.onPageScrollStateChanged(newState);
                        }
                    }
                }

/* 407 */       public void setAdapter(PagerAdapter adapter) {
/* 408 */           if (this.mAdapter != null) {
/* 409 */               this.mAdapter.unregisterDataSetObserver(this.mObserver);
/* 410 */               this.mAdapter.startUpdate((ViewGroup) this);
                        for (int i = 0; i < this.mItems.size(); i++) {
/* 412 */                   ItemInfo ii = this.mItems.get(i);
/* 413 */                   this.mAdapter.destroyItem((ViewGroup) this, ii.position, ii.object);
                        }
/* 415 */               this.mAdapter.finishUpdate((ViewGroup) this);
/* 416 */               this.mItems.clear();
/* 417 */               removeNonDecorViews();
/* 418 */               this.mCurItem = 0;
/* 419 */               scrollTo(0, 0);
                    }
/* 422 */           PagerAdapter oldAdapter = this.mAdapter;
/* 423 */           this.mAdapter = adapter;
/* 424 */           this.mExpectedAdapterCount = 0;
/* 426 */           if (this.mAdapter != null) {
/* 427 */               if (this.mObserver == null) {
/* 428 */                   this.mObserver = new PagerObserver(this, null);
                        }
/* 430 */               this.mAdapter.registerDataSetObserver(this.mObserver);
/* 431 */               this.mPopulatePending = false;
/* 432 */               boolean wasFirstLayout = this.mFirstLayout;
/* 433 */               this.mFirstLayout = true;
/* 434 */               this.mExpectedAdapterCount = this.mAdapter.getCount();
/* 435 */               if (this.mRestoredCurItem >= 0) {
/* 436 */                   this.mAdapter.restoreState(this.mRestoredAdapterState, this.mRestoredClassLoader);
/* 437 */                   setCurrentItemInternal(this.mRestoredCurItem, false, true);
/* 438 */                   this.mRestoredCurItem = -1;
/* 439 */                   this.mRestoredAdapterState = null;
/* 440 */                   this.mRestoredClassLoader = null;
                        } else if (!wasFirstLayout) {
/* 442 */                   populate();
                        } else {
/* 444 */                   requestLayout();
                        }
                    }
/* 448 */           if (this.mAdapterChangeListener != null && oldAdapter != adapter) {
/* 449 */               this.mAdapterChangeListener.onAdapterChanged(oldAdapter, adapter);
                    }
                }

/* 453 */       private void removeNonDecorViews() {
/* 454 */           int i = 0;
                    while (i < getChildCount()) {
/* 455 */               View child = getChildAt(i);
/* 456 */               LayoutParams lp = (LayoutParams) child.getLayoutParams();
/* 457 */               if (!lp.isDecor) {
/* 458 */                   removeViewAt(i);
/* 459 */                   i--;
                        }
/* 454 */               i++;
                    }
                }

/* 469 */       public PagerAdapter getAdapter() {
/* 470 */           return this.mAdapter;
                }

/* 473 */       void setOnAdapterChangeListener(OnAdapterChangeListener listener) {
/* 474 */           this.mAdapterChangeListener = listener;
                }

/* 477 */       private int getClientWidth() {
/* 478 */           return (getMeasuredWidth() - getPaddingLeft()) - getPaddingRight();
                }

/* 488 */       public void setCurrentItem(int item) {
/* 489 */           this.mPopulatePending = false;
/* 490 */           setCurrentItemInternal(item, !this.mFirstLayout, false);
                }

/* 499 */       public void setCurrentItem(int item, boolean smoothScroll) {
/* 500 */           this.mPopulatePending = false;
/* 501 */           setCurrentItemInternal(item, smoothScroll, false);
                }

/* 504 */       public int getCurrentItem() {
/* 505 */           return this.mCurItem;
                }

/* 508 */       void setCurrentItemInternal(int item, boolean smoothScroll, boolean always) {
/* 509 */           setCurrentItemInternal(item, smoothScroll, always, 0);
                }

/* 512 */       void setCurrentItemInternal(int item, boolean smoothScroll, boolean always, int velocity) {
/* 513 */           if (this.mAdapter == null || this.mAdapter.getCount() <= 0) {
/* 514 */               setScrollingCacheEnabled(false);
/* 553 */               return;
                    }
/* 517 */           if (!always && this.mCurItem == item && this.mItems.size() != 0) {
/* 518 */               setScrollingCacheEnabled(false);
/* 553 */               return;
                    }
/* 522 */           if (item < 0) {
/* 523 */               item = 0;
                    } else if (item >= this.mAdapter.getCount()) {
/* 525 */               item = this.mAdapter.getCount() - 1;
                    }
/* 527 */           int pageLimit = this.mOffscreenPageLimit;
/* 528 */           if (item > this.mCurItem + pageLimit || item < this.mCurItem - pageLimit) {
                        for (int i = 0; i < this.mItems.size(); i++) {
/* 533 */                   this.mItems.get(i).scrolling = true;
                        }
                    }
/* 536 */           boolean dispatchSelected = this.mCurItem != item;
/* 538 */           if (this.mFirstLayout) {
/* 541 */               this.mCurItem = item;
/* 542 */               if (dispatchSelected && this.mOnPageChangeListener != null) {
/* 543 */                   this.mOnPageChangeListener.onPageSelected(item);
                        }
/* 545 */               if (dispatchSelected && this.mInternalPageChangeListener != null) {
/* 546 */                   this.mInternalPageChangeListener.onPageSelected(item);
                        }
/* 548 */               requestLayout();
/* 553 */               return;
                    }
/* 550 */           populate(item);
/* 551 */           scrollToItem(item, smoothScroll, velocity, dispatchSelected);
                }

/* 556 */       private void scrollToItem(int item, boolean smoothScroll, int velocity, boolean dispatchSelected) {
/* 557 */           ItemInfo curInfo = infoForPosition(item);
/* 558 */           int destX = 0;
/* 559 */           if (curInfo != null) {
/* 560 */               int width = getClientWidth();
/* 561 */               destX = (int) (width * Math.max(this.mFirstOffset, Math.min(curInfo.offset, this.mLastOffset)));
                    }
/* 564 */           if (smoothScroll) {
/* 565 */               smoothScrollTo(destX, 0, velocity);
/* 566 */               if (dispatchSelected && this.mOnPageChangeListener != null) {
/* 567 */                   this.mOnPageChangeListener.onPageSelected(item);
                        }
/* 569 */               if (dispatchSelected && this.mInternalPageChangeListener != null) {
/* 570 */                   this.mInternalPageChangeListener.onPageSelected(item);
/* 583 */                   return;
                        }
/* 583 */               return;
                    }
/* 573 */           if (dispatchSelected && this.mOnPageChangeListener != null) {
/* 574 */               this.mOnPageChangeListener.onPageSelected(item);
                    }
/* 576 */           if (dispatchSelected && this.mInternalPageChangeListener != null) {
/* 577 */               this.mInternalPageChangeListener.onPageSelected(item);
                    }
/* 579 */           completeScroll(false);
/* 580 */           scrollTo(destX, 0);
/* 581 */           pageScrolled(destX);
                }

/* 591 */       public void setOnPageChangeListener(OnPageChangeListener listener) {
/* 592 */           this.mOnPageChangeListener = listener;
                }

/* 607 */       public void setPageTransformer(boolean reverseDrawingOrder, PageTransformer transformer) {
/* 608 */           if (Build.VERSION.SDK_INT >= 11) {
/* 609 */               boolean hasTransformer = transformer != null;
                        boolean needsPopulate = hasTransformer != (this.mPageTransformer != null);
/* 611 */               this.mPageTransformer = transformer;
/* 612 */               setChildrenDrawingOrderEnabledCompat(hasTransformer);
/* 613 */               if (hasTransformer) {
/* 614 */                   this.mDrawingOrder = reverseDrawingOrder ? 2 : 1;
                        } else {
/* 616 */                   this.mDrawingOrder = 0;
                        }
/* 618 */               if (needsPopulate) {
                            populate();
                        }
                    }
                }

/* 622 */       void setChildrenDrawingOrderEnabledCompat(boolean enable) {
/* 623 */           if (Build.VERSION.SDK_INT >= 7) {
/* 624 */               if (this.mSetChildrenDrawingOrderEnabled == null) {
                            try {
/* 626 */                       this.mSetChildrenDrawingOrderEnabled = ViewGroup.class.getDeclaredMethod("setChildrenDrawingOrderEnabled", Boolean.TYPE);
                            } catch (NoSuchMethodException e) {
/* 629 */                       Log.e(TAG, "Can't find setChildrenDrawingOrderEnabled", e);
                            }
                        }
                        try {
/* 633 */                   this.mSetChildrenDrawingOrderEnabled.invoke(this, Boolean.valueOf(enable));
                        } catch (Exception e2) {
/* 635 */                   Log.e(TAG, "Error changing children drawing order", e2);
                        }
                    }
                }

                @Override // android.view.ViewGroup
/* 641 */       protected int getChildDrawingOrder(int childCount, int i) {
/* 642 */           int index = this.mDrawingOrder == 2 ? (childCount - 1) - i : i;
/* 643 */           int result = ((LayoutParams) this.mDrawingOrderedChildren.get(index).getLayoutParams()).childIndex;
/* 644 */           return result;
                }

/* 653 */       OnPageChangeListener setInternalPageChangeListener(OnPageChangeListener listener) {
/* 654 */           OnPageChangeListener oldListener = this.mInternalPageChangeListener;
/* 655 */           this.mInternalPageChangeListener = listener;
/* 656 */           return oldListener;
                }

/* 666 */       public int getOffscreenPageLimit() {
/* 667 */           return this.mOffscreenPageLimit;
                }

/* 687 */       public void setOffscreenPageLimit(int limit) {
/* 688 */           if (limit < 1) {
/* 689 */               Log.w(TAG, "Requested offscreen page limit " + limit + " too small; defaulting to 1");
/* 691 */               limit = 1;
                    }
/* 693 */           if (limit != this.mOffscreenPageLimit) {
/* 694 */               this.mOffscreenPageLimit = limit;
/* 695 */               populate();
                    }
                }

/* 707 */       public void setPageMargin(int marginPixels) {
/* 708 */           int oldMargin = this.mPageMargin;
/* 709 */           this.mPageMargin = marginPixels;
/* 711 */           int width = getWidth();
/* 712 */           recomputeScrollPosition(width, width, marginPixels, oldMargin);
/* 714 */           requestLayout();
                }

/* 722 */       public int getPageMargin() {
/* 723 */           return this.mPageMargin;
                }

/* 731 */       public void setPageMarginDrawable(Drawable d) {
/* 732 */           this.mMarginDrawable = d;
/* 733 */           if (d != null) {
                        refreshDrawableState();
                    }
/* 734 */           setWillNotDraw(d == null);
/* 735 */           invalidate();
                }

/* 743 */       public void setPageMarginDrawable(int resId) {
/* 744 */           setPageMarginDrawable(getContext().getResources().getDrawable(resId));
                }

                @Override // android.view.View
/* 748 */       protected boolean verifyDrawable(Drawable who) {
                    return super.verifyDrawable(who) || who == this.mMarginDrawable;
                }

                @Override // android.view.ViewGroup, android.view.View
/* 753 */       protected void drawableStateChanged() {
/* 754 */           super.drawableStateChanged();
/* 755 */           Drawable d = this.mMarginDrawable;
/* 756 */           if (d != null && d.isStateful()) {
/* 757 */               d.setState(getDrawableState());
                    }
                }

/* 765 */       float distanceInfluenceForSnapDuration(float f) {
/* 768 */           return (float) Math.sin((float) (((double) (f - 0.5f)) * 0.4712389167638204d));
                }

/* 777 */       void smoothScrollTo(int x, int y) {
/* 778 */           smoothScrollTo(x, y, 0);
                }

/* 788 */       void smoothScrollTo(int x, int y, int velocity) {
                    int duration;
/* 789 */           if (getChildCount() == 0) {
/* 791 */               setScrollingCacheEnabled(false);
/* 827 */               return;
                    }
/* 794 */           int sx = getScrollX();
/* 795 */           int sy = getScrollY();
/* 796 */           int dx = x - sx;
/* 797 */           int dy = y - sy;
/* 798 */           if (dx == 0 && dy == 0) {
/* 799 */               completeScroll(false);
/* 800 */               populate();
/* 801 */               setScrollState(0);
/* 827 */               return;
                    }
/* 805 */           setScrollingCacheEnabled(true);
/* 806 */           setScrollState(2);
/* 808 */           int width = getClientWidth();
/* 809 */           int halfWidth = width / 2;
/* 810 */           float distanceRatio = Math.min(1.0f, (1.0f * Math.abs(dx)) / width);
/* 811 */           float distance = halfWidth + (halfWidth * distanceInfluenceForSnapDuration(distanceRatio));
/* 815 */           int velocity2 = Math.abs(velocity);
/* 816 */           if (velocity2 > 0) {
/* 817 */               duration = Math.round(1000.0f * Math.abs(distance / velocity2)) * 4;
                    } else {
/* 819 */               float pageWidth = width * this.mAdapter.getPageWidth(this.mCurItem);
/* 820 */               float pageDelta = Math.abs(dx) / (this.mPageMargin + pageWidth);
/* 821 */               duration = (int) ((1.0f + pageDelta) * 100.0f);
                    }
/* 825 */           this.mScroller.startScroll(sx, sy, dx, dy, Math.min(duration, MAX_SETTLE_DURATION));
/* 826 */           ViewCompat.postInvalidateOnAnimation(this);
                }

/* 829 */       ItemInfo addNewItem(int position, int index) {
/* 830 */           ItemInfo ii = new ItemInfo();
/* 831 */           ii.position = position;
/* 832 */           ii.object = this.mAdapter.instantiateItem((ViewGroup) this, position);
/* 833 */           ii.widthFactor = this.mAdapter.getPageWidth(position);
/* 834 */           if (index < 0 || index >= this.mItems.size()) {
/* 835 */               this.mItems.add(ii);
                    } else {
/* 837 */               this.mItems.add(index, ii);
                    }
/* 839 */           return ii;
                }

/* 844 */       void dataSetChanged() {
/* 845 */           int adapterCount = this.mAdapter.getCount();
/* 846 */           this.mExpectedAdapterCount = adapterCount;
/* 847 */           boolean needPopulate = this.mItems.size() < (this.mOffscreenPageLimit * 2) + 1 && this.mItems.size() < adapterCount;
/* 849 */           int newCurrItem = this.mCurItem;
/* 851 */           boolean isUpdating = false;
/* 852 */           int i = 0;
                    while (i < this.mItems.size()) {
/* 853 */               ItemInfo ii = this.mItems.get(i);
/* 854 */               int newPos = this.mAdapter.getItemPosition(ii.object);
/* 856 */               if (newPos != -1) {
/* 860 */                   if (newPos == -2) {
/* 861 */                       this.mItems.remove(i);
/* 862 */                       i--;
/* 864 */                       if (!isUpdating) {
/* 865 */                           this.mAdapter.startUpdate((ViewGroup) this);
/* 866 */                           isUpdating = true;
                                }
/* 869 */                       this.mAdapter.destroyItem((ViewGroup) this, ii.position, ii.object);
/* 870 */                       needPopulate = true;
/* 872 */                       if (this.mCurItem == ii.position) {
/* 874 */                           newCurrItem = Math.max(0, Math.min(this.mCurItem, adapterCount - 1));
/* 875 */                           needPopulate = true;
                                }
                            } else if (ii.position != newPos) {
/* 881 */                       if (ii.position == this.mCurItem) {
/* 883 */                           newCurrItem = newPos;
                                }
/* 886 */                       ii.position = newPos;
/* 887 */                       needPopulate = true;
                            }
                        }
/* 852 */               i++;
                    }
/* 891 */           if (isUpdating) {
/* 892 */               this.mAdapter.finishUpdate((ViewGroup) this);
                    }
/* 895 */           Collections.sort(this.mItems, COMPARATOR);
/* 897 */           if (needPopulate) {
/* 899 */               int childCount = getChildCount();
                        for (int i2 = 0; i2 < childCount; i2++) {
/* 901 */                   View child = getChildAt(i2);
/* 902 */                   LayoutParams lp = (LayoutParams) child.getLayoutParams();
/* 903 */                   if (!lp.isDecor) {
/* 904 */                       lp.widthFactor = 0.0f;
                            }
                        }
/* 908 */               setCurrentItemInternal(newCurrItem, false, true);
/* 909 */               requestLayout();
                    }
                }

/* 913 */       void populate() {
/* 914 */           populate(this.mCurItem);
                }

/* 917 */       void populate(int newCurrentItem) {
                    String resName;
                    ItemInfo ii;
/* 918 */           ItemInfo oldCurInfo = null;
/* 919 */           int focusDirection = 2;
/* 920 */           if (this.mCurItem != newCurrentItem) {
/* 921 */               focusDirection = this.mCurItem < newCurrentItem ? 66 : 17;
/* 922 */               oldCurInfo = infoForPosition(this.mCurItem);
/* 923 */               this.mCurItem = newCurrentItem;
                    }
/* 926 */           if (this.mAdapter == null) {
/* 927 */               sortChildDrawingOrder();
/* 1103 */              return;
                    }
/* 935 */           if (this.mPopulatePending) {
/* 937 */               sortChildDrawingOrder();
/* 1103 */              return;
                    }
/* 944 */           if (getWindowToken() != null) {
/* 948 */               this.mAdapter.startUpdate((ViewGroup) this);
/* 950 */               int pageLimit = this.mOffscreenPageLimit;
/* 951 */               int startPos = Math.max(0, this.mCurItem - pageLimit);
/* 952 */               int N = this.mAdapter.getCount();
/* 953 */               int endPos = Math.min(N - 1, this.mCurItem + pageLimit);
/* 955 */               if (N != this.mExpectedAdapterCount) {
                            try {
/* 958 */                       resName = getResources().getResourceName(getId());
                            } catch (Resources.NotFoundException e) {
/* 960 */                       resName = Integer.toHexString(getId());
                            }
/* 962 */                   throw new IllegalStateException("The application's PagerAdapter changed the adapter's contents without calling PagerAdapter#notifyDataSetChanged! Expected adapter item count: " + this.mExpectedAdapterCount + ", found: " + N + " Pager id: " + resName + " Pager class: " + getClass() + " Problematic adapter: " + this.mAdapter.getClass());
                        }
/* 972 */               ItemInfo curItem = null;
/* 973 */               int curIndex = 0;
                        while (curIndex < this.mItems.size()) {
/* 974 */                   ItemInfo ii2 = this.mItems.get(curIndex);
/* 975 */                   if (ii2.position < this.mCurItem) {
/* 973 */                       curIndex++;
                            } else {
/* 976 */                       if (ii2.position != this.mCurItem) {
                                    break;
                                }
                                curItem = ii2;
                                break;
                            }
                        }
/* 981 */               if (curItem == null && N > 0) {
/* 982 */                   curItem = addNewItem(this.mCurItem, curIndex);
                        }
/* 988 */               if (curItem != null) {
/* 989 */                   float extraWidthLeft = 0.0f;
/* 990 */                   int itemIndex = curIndex - 1;
/* 991 */                   ItemInfo ii3 = itemIndex >= 0 ? this.mItems.get(itemIndex) : null;
/* 992 */                   int clientWidth = getClientWidth();
/* 993 */                   float leftWidthNeeded = clientWidth <= 0 ? 0.0f : (2.0f - curItem.widthFactor) + (getPaddingLeft() / clientWidth);
                            for (int pos = this.mCurItem - 1; pos >= 0; pos--) {
                                if (extraWidthLeft >= leftWidthNeeded && pos < startPos) {
/* 997 */                           if (ii3 == null) {
                                        break;
                                    }
/* 1000 */                          if (pos == ii3.position && !ii3.scrolling) {
/* 1001 */                              this.mItems.remove(itemIndex);
/* 1002 */                              this.mAdapter.destroyItem((ViewGroup) this, pos, ii3.object);
/* 1007 */                              itemIndex--;
/* 1008 */                              curIndex--;
/* 1009 */                              ii3 = itemIndex >= 0 ? this.mItems.get(itemIndex) : null;
                                    }
                                } else if (ii3 != null && pos == ii3.position) {
/* 1012 */                          extraWidthLeft += ii3.widthFactor;
/* 1013 */                          itemIndex--;
/* 1014 */                          ii3 = itemIndex >= 0 ? this.mItems.get(itemIndex) : null;
                                } else {
/* 1017 */                          extraWidthLeft += addNewItem(pos, itemIndex + 1).widthFactor;
/* 1018 */                          curIndex++;
/* 1019 */                          ii3 = itemIndex >= 0 ? this.mItems.get(itemIndex) : null;
                                }
                            }
/* 1023 */                  float extraWidthRight = curItem.widthFactor;
/* 1024 */                  int itemIndex2 = curIndex + 1;
                            if (extraWidthRight < 2.0f) {
/* 1026 */                      ItemInfo ii4 = itemIndex2 < this.mItems.size() ? this.mItems.get(itemIndex2) : null;
/* 1027 */                      float rightWidthNeeded = clientWidth <= 0 ? 0.0f : (getPaddingRight() / clientWidth) + 2.0f;
                                for (int pos2 = this.mCurItem + 1; pos2 < N; pos2++) {
                                    if (extraWidthRight >= rightWidthNeeded && pos2 > endPos) {
/* 1031 */                              if (ii4 == null) {
                                            break;
                                        }
/* 1034 */                              if (pos2 == ii4.position && !ii4.scrolling) {
/* 1035 */                                  this.mItems.remove(itemIndex2);
/* 1036 */                                  this.mAdapter.destroyItem((ViewGroup) this, pos2, ii4.object);
/* 1041 */                                  ii4 = itemIndex2 < this.mItems.size() ? this.mItems.get(itemIndex2) : null;
                                        }
                                    } else if (ii4 != null && pos2 == ii4.position) {
/* 1044 */                              extraWidthRight += ii4.widthFactor;
/* 1045 */                              itemIndex2++;
/* 1046 */                              ii4 = itemIndex2 < this.mItems.size() ? this.mItems.get(itemIndex2) : null;
                                    } else {
/* 1048 */                              ItemInfo ii5 = addNewItem(pos2, itemIndex2);
/* 1049 */                              itemIndex2++;
/* 1050 */                              extraWidthRight += ii5.widthFactor;
/* 1051 */                              ii4 = itemIndex2 < this.mItems.size() ? this.mItems.get(itemIndex2) : null;
                                    }
                                }
                            }
/* 1056 */                  calculatePageOffsets(curItem, curIndex, oldCurInfo);
                        }
/* 1066 */              this.mAdapter.setPrimaryItem((ViewGroup) this, this.mCurItem, curItem != null ? curItem.object : null);
/* 1068 */              this.mAdapter.finishUpdate((ViewGroup) this);
/* 1072 */              int childCount = getChildCount();
                        for (int i = 0; i < childCount; i++) {
/* 1074 */                  View child = getChildAt(i);
/* 1075 */                  LayoutParams lp = (LayoutParams) child.getLayoutParams();
/* 1076 */                  lp.childIndex = i;
/* 1077 */                  if (!lp.isDecor && lp.widthFactor == 0.0f && (ii = infoForChild(child)) != null) {
/* 1081 */                      lp.widthFactor = ii.widthFactor;
/* 1082 */                      lp.position = ii.position;
                            }
                        }
/* 1086 */              sortChildDrawingOrder();
/* 1088 */              if (hasFocus()) {
/* 1089 */                  View currentFocused = findFocus();
/* 1090 */                  ItemInfo ii6 = currentFocused != null ? infoForAnyChild(currentFocused) : null;
/* 1091 */                  if (ii6 == null || ii6.position != this.mCurItem) {
                                for (int i2 = 0; i2 < getChildCount(); i2++) {
/* 1093 */                          View child2 = getChildAt(i2);
/* 1094 */                          ItemInfo ii7 = infoForChild(child2);
/* 1095 */                          if (ii7 != null && ii7.position == this.mCurItem && child2.requestFocus(focusDirection)) {
/* 1103 */                              return;
                                    }
                                }
                            }
                        }
                    }
                }

/* 1105 */      private void sortChildDrawingOrder() {
/* 1106 */          if (this.mDrawingOrder != 0) {
/* 1107 */              if (this.mDrawingOrderedChildren == null) {
/* 1108 */                  this.mDrawingOrderedChildren = new ArrayList<>();
                        } else {
/* 1110 */                  this.mDrawingOrderedChildren.clear();
                        }
/* 1112 */              int childCount = getChildCount();
                        for (int i = 0; i < childCount; i++) {
/* 1114 */                  View child = getChildAt(i);
/* 1115 */                  this.mDrawingOrderedChildren.add(child);
                        }
/* 1117 */              Collections.sort(this.mDrawingOrderedChildren, sPositionComparator);
                    }
                }

/* 1121 */      private void calculatePageOffsets(ItemInfo curItem, int curIndex, ItemInfo oldCurInfo) {
                    ItemInfo ii;
                    ItemInfo ii2;
/* 1122 */          int N = this.mAdapter.getCount();
/* 1123 */          int width = getClientWidth();
/* 1124 */          float marginOffset = width > 0 ? this.mPageMargin / width : 0.0f;
/* 1126 */          if (oldCurInfo != null) {
/* 1127 */              int oldCurPosition = oldCurInfo.position;
/* 1129 */              if (oldCurPosition < curItem.position) {
/* 1130 */                  int itemIndex = 0;
/* 1132 */                  float offset = oldCurInfo.offset + oldCurInfo.widthFactor + marginOffset;
/* 1133 */                  int pos = oldCurPosition + 1;
/* 1134 */                  while (pos <= curItem.position && itemIndex < this.mItems.size()) {
/* 1135 */                      ItemInfo ii3 = this.mItems.get(itemIndex);
                                while (true) {
                                    ii2 = ii3;
/* 1136 */                          if (pos <= ii2.position || itemIndex >= this.mItems.size() - 1) {
                                        break;
                                    }
/* 1137 */                          itemIndex++;
/* 1138 */                          ii3 = this.mItems.get(itemIndex);
                                }
/* 1140 */                      while (pos < ii2.position) {
/* 1143 */                          offset += this.mAdapter.getPageWidth(pos) + marginOffset;
/* 1144 */                          pos++;
                                }
/* 1146 */                      ii2.offset = offset;
/* 1147 */                      offset += ii2.widthFactor + marginOffset;
/* 1134 */                      pos++;
                            }
                        } else if (oldCurPosition > curItem.position) {
/* 1150 */                  int itemIndex2 = this.mItems.size() - 1;
/* 1152 */                  float offset2 = oldCurInfo.offset;
/* 1153 */                  int pos2 = oldCurPosition - 1;
/* 1154 */                  while (pos2 >= curItem.position && itemIndex2 >= 0) {
/* 1155 */                      ItemInfo ii4 = this.mItems.get(itemIndex2);
                                while (true) {
                                    ii = ii4;
/* 1156 */                          if (pos2 >= ii.position || itemIndex2 <= 0) {
                                        break;
                                    }
/* 1157 */                          itemIndex2--;
/* 1158 */                          ii4 = this.mItems.get(itemIndex2);
                                }
/* 1160 */                      while (pos2 > ii.position) {
/* 1163 */                          offset2 -= this.mAdapter.getPageWidth(pos2) + marginOffset;
/* 1164 */                          pos2--;
                                }
/* 1166 */                      offset2 -= ii.widthFactor + marginOffset;
/* 1167 */                      ii.offset = offset2;
/* 1154 */                      pos2--;
                            }
                        }
                    }
/* 1173 */          int itemCount = this.mItems.size();
/* 1174 */          float offset3 = curItem.offset;
/* 1175 */          int pos3 = curItem.position - 1;
/* 1176 */          this.mFirstOffset = curItem.position == 0 ? curItem.offset : -3.4028235E38f;
/* 1177 */          this.mLastOffset = curItem.position == N + (-1) ? (curItem.offset + curItem.widthFactor) - 1.0f : Float.MAX_VALUE;
/* 1180 */          int i = curIndex - 1;
                    while (i >= 0) {
/* 1181 */              ItemInfo ii5 = this.mItems.get(i);
/* 1182 */              while (pos3 > ii5.position) {
/* 1183 */                  offset3 -= this.mAdapter.getPageWidth(pos3) + marginOffset;
                            pos3--;
                        }
/* 1185 */              offset3 -= ii5.widthFactor + marginOffset;
/* 1186 */              ii5.offset = offset3;
/* 1187 */              if (ii5.position == 0) {
                            this.mFirstOffset = offset3;
                        }
/* 1180 */              i--;
                        pos3--;
                    }
/* 1189 */          float offset4 = curItem.offset + curItem.widthFactor + marginOffset;
/* 1190 */          int pos4 = curItem.position + 1;
/* 1192 */          int i2 = curIndex + 1;
                    while (i2 < itemCount) {
/* 1193 */              ItemInfo ii6 = this.mItems.get(i2);
/* 1194 */              while (pos4 < ii6.position) {
/* 1195 */                  offset4 += this.mAdapter.getPageWidth(pos4) + marginOffset;
                            pos4++;
                        }
/* 1197 */              if (ii6.position == N - 1) {
/* 1198 */                  this.mLastOffset = (ii6.widthFactor + offset4) - 1.0f;
                        }
/* 1200 */              ii6.offset = offset4;
/* 1201 */              offset4 += ii6.widthFactor + marginOffset;
/* 1192 */              i2++;
                        pos4++;
                    }
/* 1204 */          this.mNeedCalculatePageOffsets = false;
                }

/* 1217 */      public static class SavedState extends View.BaseSavedState {
                    public static final Parcelable.Creator<SavedState> CREATOR = ParcelableCompat.newCreator(new C00541());
                    Parcelable adapterState;
                    ClassLoader loader;
                    int position;

/* 1218 */          public SavedState(Parcelable superState) {
/* 1219 */              super(superState);
                    }

                    @Override // android.view.View.BaseSavedState, android.view.AbsSavedState, android.os.Parcelable
/* 1223 */          public void writeToParcel(Parcel out, int flags) {
/* 1224 */              super.writeToParcel(out, flags);
/* 1225 */              out.writeInt(this.position);
/* 1226 */              out.writeParcelable(this.adapterState, flags);
                    }

/* 1230 */          public String toString() {
/* 1231 */              return "FragmentPager.SavedState{" + Integer.toHexString(System.identityHashCode(this)) + " position=" + this.position + "}";
                    }

                    /* JADX INFO: renamed from: android.support.v4.view.ViewPager$SavedState$1 */
/* 1235 */          static class C00541 implements ParcelableCompatCreatorCallbacks<SavedState> {
/* 1236 */              C00541() {
                        }

                        @Override // android.support.v4.os.ParcelableCompatCreatorCallbacks
/* 1236 */              public /* bridge */ /* synthetic */ SavedState createFromParcel(Parcel x0, ClassLoader x1) {
/* 1237 */                  return createFromParcel(x0, x1);
                        }

                        @Override // android.support.v4.os.ParcelableCompatCreatorCallbacks
/* 1236 */              public /* bridge */ /* synthetic */ SavedState[] newArray(int x0) {
/* 1237 */                  return newArray(x0);
                        }

                        /* JADX WARN: Can't rename method to resolve collision */
                        @Override // android.support.v4.os.ParcelableCompatCreatorCallbacks
/* 1239 */              public SavedState createFromParcel(Parcel in, ClassLoader loader) {
/* 1240 */                  return new SavedState(in, loader);
                        }

                        /* JADX WARN: Can't rename method to resolve collision */
                        @Override // android.support.v4.os.ParcelableCompatCreatorCallbacks
/* 1243 */              public SavedState[] newArray(int size) {
/* 1244 */                  return new SavedState[size];
                        }
                    }

/* 1248 */          SavedState(Parcel in, ClassLoader loader) {
/* 1249 */              super(in);
/* 1251 */              loader = loader == null ? getClass().getClassLoader() : loader;
/* 1253 */              this.position = in.readInt();
/* 1254 */              this.adapterState = in.readParcelable(loader);
/* 1255 */              this.loader = loader;
                    }
                }

                @Override // android.view.View
/* 1260 */      public Parcelable onSaveInstanceState() {
/* 1261 */          Parcelable superState = super.onSaveInstanceState();
/* 1262 */          SavedState ss = new SavedState(superState);
/* 1263 */          ss.position = this.mCurItem;
/* 1264 */          if (this.mAdapter != null) {
/* 1265 */              ss.adapterState = this.mAdapter.saveState();
                    }
/* 1267 */          return ss;
                }

                @Override // android.view.View
/* 1271 */      public void onRestoreInstanceState(Parcelable state) {
/* 1272 */          if (!(state instanceof SavedState)) {
/* 1273 */              super.onRestoreInstanceState(state);
/* 1288 */              return;
                    }
/* 1277 */          SavedState ss = (SavedState) state;
/* 1278 */          super.onRestoreInstanceState(ss.getSuperState());
/* 1280 */          if (this.mAdapter != null) {
/* 1281 */              this.mAdapter.restoreState(ss.adapterState, ss.loader);
/* 1282 */              setCurrentItemInternal(ss.position, false, true);
                    } else {
/* 1284 */              this.mRestoredCurItem = ss.position;
/* 1285 */              this.mRestoredAdapterState = ss.adapterState;
/* 1286 */              this.mRestoredClassLoader = ss.loader;
                    }
                }

                @Override // android.view.ViewGroup
/* 1291 */      public void addView(View child, int index, ViewGroup.LayoutParams params) {
/* 1292 */          if (!checkLayoutParams(params)) {
/* 1293 */              params = generateLayoutParams(params);
                    }
/* 1295 */          LayoutParams lp = (LayoutParams) params;
/* 1296 */          lp.isDecor |= child instanceof Decor;
/* 1297 */          if (this.mInLayout) {
/* 1298 */              if (lp != null && lp.isDecor) {
/* 1299 */                  throw new IllegalStateException("Cannot add pager decor view during layout");
                        }
/* 1301 */              lp.needsMeasure = true;
/* 1302 */              addViewInLayout(child, index, params);
/* 1314 */              return;
                    }
/* 1304 */          super.addView(child, index, params);
                }

                @Override // android.view.ViewGroup, android.view.ViewManager
/* 1317 */      public void removeView(View view) {
/* 1318 */          if (this.mInLayout) {
/* 1319 */              removeViewInLayout(view);
                    } else {
/* 1321 */              super.removeView(view);
                    }
                }

/* 1325 */      ItemInfo infoForChild(View child) {
                    for (int i = 0; i < this.mItems.size(); i++) {
/* 1327 */              ItemInfo ii = this.mItems.get(i);
/* 1328 */              if (this.mAdapter.isViewFromObject(child, ii.object)) {
/* 1332 */                  return ii;
                        }
                    }
/* 1332 */          return null;
                }

/* 1336 */      ItemInfo infoForAnyChild(View child) {
                    while (true) {
/* 1337 */              Object parent = child.getParent();
                        if (parent != this) {
/* 1338 */                  if (parent == null || !(parent instanceof View)) {
/* 1339 */                      return null;
                            }
/* 1341 */                  child = (View) parent;
                        } else {
/* 1343 */                  return infoForChild(child);
                        }
                    }
                }

/* 1346 */      ItemInfo infoForPosition(int position) {
                    for (int i = 0; i < this.mItems.size(); i++) {
/* 1348 */              ItemInfo ii = this.mItems.get(i);
/* 1349 */              if (ii.position == position) {
/* 1353 */                  return ii;
                        }
                    }
/* 1353 */          return null;
                }

                @Override // android.view.ViewGroup, android.view.View
/* 1357 */      protected void onAttachedToWindow() {
/* 1358 */          super.onAttachedToWindow();
/* 1359 */          this.mFirstLayout = true;
                }

                @Override // android.view.View
/* 1368 */      protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
                    LayoutParams lp;
                    LayoutParams lp2;
/* 1369 */          setMeasuredDimension(getDefaultSize(0, widthMeasureSpec), getDefaultSize(0, heightMeasureSpec));
/* 1372 */          int measuredWidth = getMeasuredWidth();
/* 1373 */          int maxGutterSize = measuredWidth / 10;
/* 1374 */          this.mGutterSize = Math.min(maxGutterSize, this.mDefaultGutterSize);
/* 1377 */          int childWidthSize = (measuredWidth - getPaddingLeft()) - getPaddingRight();
/* 1378 */          int childHeightSize = (getMeasuredHeight() - getPaddingTop()) - getPaddingBottom();
/* 1385 */          int size = getChildCount();
                    for (int i = 0; i < size; i++) {
/* 1387 */              View child = getChildAt(i);
/* 1388 */              if (child.getVisibility() != 8 && (lp2 = (LayoutParams) child.getLayoutParams()) != null && lp2.isDecor) {
/* 1391 */                  int hgrav = lp2.gravity & 7;
/* 1392 */                  int vgrav = lp2.gravity & 112;
/* 1393 */                  int widthMode = Integer.MIN_VALUE;
/* 1394 */                  int heightMode = Integer.MIN_VALUE;
/* 1395 */                  boolean consumeVertical = vgrav == 48 || vgrav == 80;
/* 1396 */                  boolean consumeHorizontal = hgrav == 3 || hgrav == 5;
/* 1398 */                  if (consumeVertical) {
/* 1399 */                      widthMode = 1073741824;
                            } else if (consumeHorizontal) {
/* 1401 */                      heightMode = 1073741824;
                            }
/* 1404 */                  int widthSize = childWidthSize;
/* 1405 */                  int heightSize = childHeightSize;
/* 1406 */                  if (lp2.width != -2) {
/* 1407 */                      widthMode = 1073741824;
/* 1408 */                      if (lp2.width != -1) {
/* 1409 */                          widthSize = lp2.width;
                                }
                            }
/* 1412 */                  if (lp2.height != -2) {
/* 1413 */                      heightMode = 1073741824;
/* 1414 */                      if (lp2.height != -1) {
/* 1415 */                          heightSize = lp2.height;
                                }
                            }
/* 1418 */                  int widthSpec = View.MeasureSpec.makeMeasureSpec(widthSize, widthMode);
/* 1419 */                  int heightSpec = View.MeasureSpec.makeMeasureSpec(heightSize, heightMode);
/* 1420 */                  child.measure(widthSpec, heightSpec);
/* 1422 */                  if (consumeVertical) {
/* 1423 */                      childHeightSize -= child.getMeasuredHeight();
                            } else if (consumeHorizontal) {
/* 1425 */                      childWidthSize -= child.getMeasuredWidth();
                            }
                        }
                    }
/* 1431 */          this.mChildWidthMeasureSpec = View.MeasureSpec.makeMeasureSpec(childWidthSize, 1073741824);
/* 1432 */          this.mChildHeightMeasureSpec = View.MeasureSpec.makeMeasureSpec(childHeightSize, 1073741824);
/* 1435 */          this.mInLayout = true;
/* 1436 */          populate();
/* 1437 */          this.mInLayout = false;
/* 1440 */          int size2 = getChildCount();
                    for (int i2 = 0; i2 < size2; i2++) {
/* 1442 */              View child2 = getChildAt(i2);
/* 1443 */              if (child2.getVisibility() != 8 && ((lp = (LayoutParams) child2.getLayoutParams()) == null || !lp.isDecor)) {
/* 1449 */                  int widthSpec2 = View.MeasureSpec.makeMeasureSpec((int) (childWidthSize * lp.widthFactor), 1073741824);
/* 1451 */                  child2.measure(widthSpec2, this.mChildHeightMeasureSpec);
                        }
                    }
                }

                @Override // android.view.View
/* 1458 */      protected void onSizeChanged(int w, int h, int oldw, int oldh) {
/* 1459 */          super.onSizeChanged(w, h, oldw, oldh);
/* 1462 */          if (w != oldw) {
/* 1463 */              recomputeScrollPosition(w, oldw, this.mPageMargin, this.mPageMargin);
                    }
                }

/* 1467 */      private void recomputeScrollPosition(int width, int oldWidth, int margin, int oldMargin) {
/* 1468 */          if (oldWidth > 0 && !this.mItems.isEmpty()) {
/* 1469 */              int widthWithMargin = ((width - getPaddingLeft()) - getPaddingRight()) + margin;
/* 1470 */              int oldWidthWithMargin = ((oldWidth - getPaddingLeft()) - getPaddingRight()) + oldMargin;
/* 1472 */              int xpos = getScrollX();
/* 1473 */              float pageOffset = xpos / oldWidthWithMargin;
/* 1474 */              int newOffsetPixels = (int) (widthWithMargin * pageOffset);
/* 1476 */              scrollTo(newOffsetPixels, getScrollY());
/* 1477 */              if (!this.mScroller.isFinished()) {
/* 1479 */                  int newDuration = this.mScroller.getDuration() - this.mScroller.timePassed();
/* 1480 */                  ItemInfo targetInfo = infoForPosition(this.mCurItem);
/* 1481 */                  this.mScroller.startScroll(newOffsetPixels, 0, (int) (targetInfo.offset * width), 0, newDuration);
/* 1494 */                  return;
                        }
/* 1494 */              return;
                    }
/* 1485 */          ItemInfo ii = infoForPosition(this.mCurItem);
/* 1486 */          float scrollOffset = ii != null ? Math.min(ii.offset, this.mLastOffset) : 0.0f;
/* 1487 */          int scrollPos = (int) (((width - getPaddingLeft()) - getPaddingRight()) * scrollOffset);
/* 1489 */          if (scrollPos != getScrollX()) {
/* 1490 */              completeScroll(false);
/* 1491 */              scrollTo(scrollPos, getScrollY());
                    }
                }

                @Override // android.view.ViewGroup, android.view.View
/* 1497 */      protected void onLayout(boolean changed, int l, int t, int r, int b) {
                    ItemInfo ii;
                    int childLeft;
                    int childTop;
/* 1498 */          int count = getChildCount();
/* 1499 */          int width = r - l;
/* 1500 */          int height = b - t;
/* 1501 */          int paddingLeft = getPaddingLeft();
/* 1502 */          int paddingTop = getPaddingTop();
/* 1503 */          int paddingRight = getPaddingRight();
/* 1504 */          int paddingBottom = getPaddingBottom();
/* 1505 */          int scrollX = getScrollX();
/* 1507 */          int decorCount = 0;
                    for (int i = 0; i < count; i++) {
/* 1512 */              View child = getChildAt(i);
/* 1513 */              if (child.getVisibility() != 8) {
/* 1514 */                  LayoutParams lp = (LayoutParams) child.getLayoutParams();
/* 1517 */                  if (lp.isDecor) {
/* 1518 */                      int hgrav = lp.gravity & 7;
/* 1519 */                      int vgrav = lp.gravity & 112;
                                switch (hgrav) {
                                    case 1:
/* 1529 */                              childLeft = Math.max((width - child.getMeasuredWidth()) / 2, paddingLeft);
                                        break;
                                    case 2:
                                    case 4:
                                    default:
/* 1522 */                              childLeft = paddingLeft;
                                        break;
                                    case 3:
/* 1525 */                              childLeft = paddingLeft;
/* 1526 */                              paddingLeft += child.getMeasuredWidth();
                                        break;
                                    case 5:
/* 1533 */                              childLeft = (width - paddingRight) - child.getMeasuredWidth();
/* 1534 */                              paddingRight += child.getMeasuredWidth();
                                        break;
                                }
                                switch (vgrav) {
                                    case 16:
/* 1546 */                              childTop = Math.max((height - child.getMeasuredHeight()) / 2, paddingTop);
                                        break;
                                    case 48:
/* 1542 */                              childTop = paddingTop;
/* 1543 */                              paddingTop += child.getMeasuredHeight();
                                        break;
                                    case 80:
/* 1550 */                              childTop = (height - paddingBottom) - child.getMeasuredHeight();
/* 1551 */                              paddingBottom += child.getMeasuredHeight();
                                        break;
                                    default:
/* 1539 */                              childTop = paddingTop;
                                        break;
                                }
/* 1554 */                      int childLeft2 = childLeft + scrollX;
/* 1555 */                      child.layout(childLeft2, childTop, child.getMeasuredWidth() + childLeft2, child.getMeasuredHeight() + childTop);
/* 1558 */                      decorCount++;
                            }
                        }
                    }
/* 1563 */          int childWidth = (width - paddingLeft) - paddingRight;
                    for (int i2 = 0; i2 < count; i2++) {
/* 1566 */              View child2 = getChildAt(i2);
/* 1567 */              if (child2.getVisibility() != 8) {
/* 1568 */                  LayoutParams lp2 = (LayoutParams) child2.getLayoutParams();
/* 1570 */                  if (!lp2.isDecor && (ii = infoForChild(child2)) != null) {
/* 1571 */                      int loff = (int) (childWidth * ii.offset);
/* 1572 */                      int childLeft3 = paddingLeft + loff;
/* 1573 */                      int childTop2 = paddingTop;
/* 1574 */                      if (lp2.needsMeasure) {
/* 1577 */                          lp2.needsMeasure = false;
/* 1578 */                          int widthSpec = View.MeasureSpec.makeMeasureSpec((int) (childWidth * lp2.widthFactor), 1073741824);
/* 1581 */                          int heightSpec = View.MeasureSpec.makeMeasureSpec((height - paddingTop) - paddingBottom, 1073741824);
/* 1584 */                          child2.measure(widthSpec, heightSpec);
                                }
/* 1589 */                      child2.layout(childLeft3, childTop2, child2.getMeasuredWidth() + childLeft3, child2.getMeasuredHeight() + childTop2);
                            }
                        }
                    }
/* 1595 */          this.mTopPageBounds = paddingTop;
/* 1596 */          this.mBottomPageBounds = height - paddingBottom;
/* 1597 */          this.mDecorChildCount = decorCount;
/* 1599 */          if (this.mFirstLayout) {
/* 1600 */              scrollToItem(this.mCurItem, false, 0, false);
                    }
/* 1602 */          this.mFirstLayout = false;
                }

                @Override // android.view.View
/* 1606 */      public void computeScroll() {
/* 1607 */          if (!this.mScroller.isFinished() && this.mScroller.computeScrollOffset()) {
/* 1608 */              int oldX = getScrollX();
/* 1609 */              int oldY = getScrollY();
/* 1610 */              int x = this.mScroller.getCurrX();
/* 1611 */              int y = this.mScroller.getCurrY();
/* 1613 */              if (oldX != x || oldY != y) {
/* 1614 */                  scrollTo(x, y);
/* 1615 */                  if (!pageScrolled(x)) {
/* 1616 */                      this.mScroller.abortAnimation();
/* 1617 */                      scrollTo(0, y);
                            }
                        }
/* 1622 */              ViewCompat.postInvalidateOnAnimation(this);
/* 1628 */              return;
                    }
/* 1627 */          completeScroll(true);
                }

/* 1630 */      private boolean pageScrolled(int xpos) {
/* 1631 */          if (this.mItems.size() == 0) {
/* 1632 */              this.mCalledSuper = false;
/* 1633 */              onPageScrolled(0, 0.0f, 0);
/* 1634 */              if (this.mCalledSuper) {
                            return false;
                        }
/* 1635 */              throw new IllegalStateException("onPageScrolled did not call superclass implementation");
                    }
/* 1640 */          ItemInfo ii = infoForCurrentScrollPosition();
/* 1641 */          int width = getClientWidth();
/* 1642 */          int widthWithMargin = width + this.mPageMargin;
/* 1643 */          float marginOffset = this.mPageMargin / width;
/* 1644 */          int currentPage = ii.position;
/* 1645 */          float pageOffset = ((xpos / width) - ii.offset) / (ii.widthFactor + marginOffset);
/* 1647 */          int offsetPixels = (int) (widthWithMargin * pageOffset);
/* 1649 */          this.mCalledSuper = false;
/* 1650 */          onPageScrolled(currentPage, pageOffset, offsetPixels);
/* 1651 */          if (!this.mCalledSuper) {
/* 1652 */              throw new IllegalStateException("onPageScrolled did not call superclass implementation");
                    }
/* 1655 */          return true;
                }

/* 1671 */      protected void onPageScrolled(int position, float offset, int offsetPixels) {
                    int childLeft;
/* 1672 */          if (this.mDecorChildCount > 0) {
/* 1673 */              int scrollX = getScrollX();
/* 1674 */              int paddingLeft = getPaddingLeft();
/* 1675 */              int paddingRight = getPaddingRight();
/* 1676 */              int width = getWidth();
/* 1677 */              int childCount = getChildCount();
                        for (int i = 0; i < childCount; i++) {
/* 1679 */                  View child = getChildAt(i);
/* 1680 */                  LayoutParams lp = (LayoutParams) child.getLayoutParams();
/* 1681 */                  if (lp.isDecor) {
/* 1683 */                      int hgrav = lp.gravity & 7;
                                switch (hgrav) {
                                    case 1:
/* 1694 */                              childLeft = Math.max((width - child.getMeasuredWidth()) / 2, paddingLeft);
                                        break;
                                    case 2:
                                    case 4:
                                    default:
/* 1687 */                              childLeft = paddingLeft;
                                        break;
                                    case 3:
/* 1690 */                              childLeft = paddingLeft;
/* 1691 */                              paddingLeft += child.getWidth();
                                        break;
                                    case 5:
/* 1698 */                              childLeft = (width - paddingRight) - child.getMeasuredWidth();
/* 1699 */                              paddingRight += child.getMeasuredWidth();
                                        break;
                                }
/* 1704 */                      int childOffset = (childLeft + scrollX) - child.getLeft();
/* 1705 */                      if (childOffset != 0) {
/* 1706 */                          child.offsetLeftAndRight(childOffset);
                                }
                            }
                        }
                    }
/* 1711 */          if (this.mOnPageChangeListener != null) {
/* 1712 */              this.mOnPageChangeListener.onPageScrolled(position, offset, offsetPixels);
                    }
/* 1714 */          if (this.mInternalPageChangeListener != null) {
/* 1715 */              this.mInternalPageChangeListener.onPageScrolled(position, offset, offsetPixels);
                    }
/* 1718 */          if (this.mPageTransformer != null) {
/* 1719 */              int scrollX2 = getScrollX();
/* 1720 */              int childCount2 = getChildCount();
                        for (int i2 = 0; i2 < childCount2; i2++) {
/* 1722 */                  View child2 = getChildAt(i2);
/* 1725 */                  if (!((LayoutParams) child2.getLayoutParams()).isDecor) {
/* 1727 */                      float transformPos = (child2.getLeft() - scrollX2) / getClientWidth();
/* 1728 */                      this.mPageTransformer.transformPage(child2, transformPos);
                            }
                        }
                    }
/* 1732 */          this.mCalledSuper = true;
                }

/* 1735 */      private void completeScroll(boolean postEvents) {
/* 1736 */          boolean needPopulate = this.mScrollState == 2;
/* 1737 */          if (needPopulate) {
/* 1739 */              setScrollingCacheEnabled(false);
/* 1740 */              this.mScroller.abortAnimation();
/* 1741 */              int oldX = getScrollX();
/* 1742 */              int oldY = getScrollY();
/* 1743 */              int x = this.mScroller.getCurrX();
/* 1744 */              int y = this.mScroller.getCurrY();
/* 1745 */              if (oldX != x || oldY != y) {
/* 1746 */                  scrollTo(x, y);
                        }
                    }
/* 1749 */          this.mPopulatePending = false;
                    for (int i = 0; i < this.mItems.size(); i++) {
/* 1751 */              ItemInfo ii = this.mItems.get(i);
/* 1752 */              if (ii.scrolling) {
/* 1753 */                  needPopulate = true;
/* 1754 */                  ii.scrolling = false;
                        }
                    }
/* 1757 */          if (needPopulate) {
/* 1758 */              if (postEvents) {
/* 1759 */                  ViewCompat.postOnAnimation(this, this.mEndScrollRunnable);
                        } else {
/* 1761 */                  this.mEndScrollRunnable.run();
                        }
                    }
                }

/* 1766 */      private boolean isGutterDrag(float x, float dx) {
                    return (x < ((float) this.mGutterSize) && dx > 0.0f) || (x > ((float) (getWidth() - this.mGutterSize)) && dx < 0.0f);
                }

/* 1770 */      private void enableLayers(boolean enable) {
/* 1771 */          int childCount = getChildCount();
                    for (int i = 0; i < childCount; i++) {
/* 1773 */              int layerType = enable ? 2 : 0;
/* 1775 */              ViewCompat.setLayerType(getChildAt(i), layerType, null);
                    }
                }

                @Override // android.view.ViewGroup
/* 1786 */      public boolean onInterceptTouchEvent(MotionEvent ev) {
/* 1787 */          int action = ev.getAction() & 255;
/* 1790 */          if (action == 3 || action == 1) {
/* 1793 */              this.mIsBeingDragged = false;
/* 1794 */              this.mIsUnableToDrag = false;
/* 1795 */              this.mActivePointerId = -1;
/* 1796 */              if (this.mVelocityTracker != null) {
/* 1797 */                  this.mVelocityTracker.recycle();
/* 1798 */                  this.mVelocityTracker = null;
                        }
/* 1800 */              return false;
                    }
/* 1805 */          if (action != 0) {
/* 1806 */              if (this.mIsBeingDragged) {
/* 1808 */                  return true;
                        }
/* 1810 */              if (this.mIsUnableToDrag) {
/* 1812 */                  return false;
                        }
                    }
                    switch (action) {
                        case 0:
/* 1880 */                  float x = ev.getX();
                            this.mInitialMotionX = x;
                            this.mLastMotionX = x;
/* 1881 */                  float y = ev.getY();
                            this.mInitialMotionY = y;
                            this.mLastMotionY = y;
/* 1882 */                  this.mActivePointerId = MotionEventCompat.getPointerId(ev, 0);
/* 1883 */                  this.mIsUnableToDrag = false;
/* 1885 */                  this.mScroller.computeScrollOffset();
/* 1886 */                  if (this.mScrollState == 2 && Math.abs(this.mScroller.getFinalX() - this.mScroller.getCurrX()) > this.mCloseEnough) {
/* 1889 */                      this.mScroller.abortAnimation();
/* 1890 */                      this.mPopulatePending = false;
/* 1891 */                      populate();
/* 1892 */                      this.mIsBeingDragged = true;
/* 1893 */                      requestParentDisallowInterceptTouchEvent(true);
/* 1894 */                      setScrollState(1);
                            } else {
/* 1896 */                      completeScroll(false);
/* 1897 */                      this.mIsBeingDragged = false;
                            }
                            break;
                        case 2:
/* 1827 */                  int activePointerId = this.mActivePointerId;
/* 1828 */                  if (activePointerId != -1) {
/* 1833 */                      int pointerIndex = MotionEventCompat.findPointerIndex(ev, activePointerId);
/* 1834 */                      float x2 = MotionEventCompat.getX(ev, pointerIndex);
/* 1835 */                      float dx = x2 - this.mLastMotionX;
/* 1836 */                      float xDiff = Math.abs(dx);
/* 1837 */                      float y2 = MotionEventCompat.getY(ev, pointerIndex);
/* 1838 */                      float yDiff = Math.abs(y2 - this.mInitialMotionY);
                                if (dx != 0.0f && !isGutterDrag(this.mLastMotionX, dx) && canScroll(this, false, (int) dx, (int) x2, (int) y2)) {
/* 1844 */                          this.mLastMotionX = x2;
/* 1845 */                          this.mLastMotionY = y2;
/* 1846 */                          this.mIsUnableToDrag = true;
/* 1847 */                          return false;
                                }
                                if (xDiff > this.mTouchSlop && 0.5f * xDiff > yDiff) {
/* 1851 */                          this.mIsBeingDragged = true;
/* 1852 */                          requestParentDisallowInterceptTouchEvent(true);
/* 1853 */                          setScrollState(1);
/* 1854 */                          this.mLastMotionX = dx > 0.0f ? this.mInitialMotionX + this.mTouchSlop : this.mInitialMotionX - this.mTouchSlop;
/* 1856 */                          this.mLastMotionY = y2;
/* 1857 */                          setScrollingCacheEnabled(true);
                                } else if (yDiff > this.mTouchSlop) {
/* 1864 */                          this.mIsUnableToDrag = true;
                                }
/* 1866 */                      if (this.mIsBeingDragged && performDrag(x2)) {
/* 1869 */                          ViewCompat.postInvalidateOnAnimation(this);
                                }
                            }
                            break;
                        case 6:
/* 1907 */                  onSecondaryPointerUp(ev);
                            break;
                    }
/* 1911 */          if (this.mVelocityTracker == null) {
/* 1912 */              this.mVelocityTracker = VelocityTracker.obtain();
                    }
/* 1914 */          this.mVelocityTracker.addMovement(ev);
/* 1920 */          return this.mIsBeingDragged;
                }

                @Override // android.view.View
/* 1924 */      public boolean onTouchEvent(MotionEvent ev) {
/* 1925 */          if (this.mFakeDragging) {
/* 1929 */              return true;
                    }
/* 1932 */          if ((ev.getAction() == 0 && ev.getEdgeFlags() != 0) || this.mAdapter == null || this.mAdapter.getCount() == 0) {
/* 1940 */              return false;
                    }
/* 1943 */          if (this.mVelocityTracker == null) {
/* 1944 */              this.mVelocityTracker = VelocityTracker.obtain();
                    }
/* 1946 */          this.mVelocityTracker.addMovement(ev);
/* 1948 */          int action = ev.getAction();
/* 1949 */          boolean needsInvalidate = false;
                    switch (action & 255) {
                        case 0:
/* 1953 */                  this.mScroller.abortAnimation();
/* 1954 */                  this.mPopulatePending = false;
/* 1955 */                  populate();
/* 1958 */                  float x = ev.getX();
                            this.mInitialMotionX = x;
                            this.mLastMotionX = x;
/* 1959 */                  float y = ev.getY();
                            this.mInitialMotionY = y;
                            this.mLastMotionY = y;
/* 1960 */                  this.mActivePointerId = MotionEventCompat.getPointerId(ev, 0);
                            break;
                        case 1:
/* 1998 */                  if (this.mIsBeingDragged) {
/* 1999 */                      VelocityTracker velocityTracker = this.mVelocityTracker;
/* 2000 */                      velocityTracker.computeCurrentVelocity(1000, this.mMaximumVelocity);
/* 2001 */                      int initialVelocity = (int) VelocityTrackerCompat.getXVelocity(velocityTracker, this.mActivePointerId);
/* 2003 */                      this.mPopulatePending = true;
/* 2004 */                      int width = getClientWidth();
/* 2005 */                      int scrollX = getScrollX();
/* 2006 */                      ItemInfo ii = infoForCurrentScrollPosition();
/* 2007 */                      int currentPage = ii.position;
/* 2008 */                      float pageOffset = ((scrollX / width) - ii.offset) / ii.widthFactor;
/* 2009 */                      int activePointerIndex = MotionEventCompat.findPointerIndex(ev, this.mActivePointerId);
/* 2011 */                      float x2 = MotionEventCompat.getX(ev, activePointerIndex);
/* 2012 */                      int totalDelta = (int) (x2 - this.mInitialMotionX);
/* 2013 */                      int nextPage = determineTargetPage(currentPage, pageOffset, initialVelocity, totalDelta);
/* 2015 */                      setCurrentItemInternal(nextPage, true, true, initialVelocity);
/* 2017 */                      this.mActivePointerId = -1;
/* 2018 */                      endDrag();
/* 2019 */                      needsInvalidate = this.mLeftEdge.onRelease() | this.mRightEdge.onRelease();
                            }
                            break;
                        case 2:
/* 1964 */                  if (!this.mIsBeingDragged) {
/* 1965 */                      int pointerIndex = MotionEventCompat.findPointerIndex(ev, this.mActivePointerId);
/* 1966 */                      float x3 = MotionEventCompat.getX(ev, pointerIndex);
/* 1967 */                      float xDiff = Math.abs(x3 - this.mLastMotionX);
/* 1968 */                      float y2 = MotionEventCompat.getY(ev, pointerIndex);
/* 1969 */                      float yDiff = Math.abs(y2 - this.mLastMotionY);
                                if (xDiff > this.mTouchSlop && xDiff > yDiff) {
/* 1973 */                          this.mIsBeingDragged = true;
/* 1974 */                          requestParentDisallowInterceptTouchEvent(true);
/* 1975 */                          this.mLastMotionX = x3 - this.mInitialMotionX > 0.0f ? this.mInitialMotionX + this.mTouchSlop : this.mInitialMotionX - this.mTouchSlop;
/* 1977 */                          this.mLastMotionY = y2;
/* 1978 */                          setScrollState(1);
/* 1979 */                          setScrollingCacheEnabled(true);
/* 1982 */                          ViewParent parent = getParent();
/* 1983 */                          if (parent != null) {
/* 1984 */                              parent.requestDisallowInterceptTouchEvent(true);
                                    }
                                }
                            }
/* 1989 */                  if (this.mIsBeingDragged) {
/* 1991 */                      int activePointerIndex2 = MotionEventCompat.findPointerIndex(ev, this.mActivePointerId);
/* 1993 */                      float x4 = MotionEventCompat.getX(ev, activePointerIndex2);
/* 1949 */                      needsInvalidate = false | performDrag(x4);
                            }
                            break;
                        case 3:
/* 2023 */                  if (this.mIsBeingDragged) {
/* 2024 */                      scrollToItem(this.mCurItem, true, 0, false);
/* 2025 */                      this.mActivePointerId = -1;
/* 2026 */                      endDrag();
/* 2027 */                      needsInvalidate = this.mLeftEdge.onRelease() | this.mRightEdge.onRelease();
                            }
                            break;
                        case 5:
/* 2031 */                  int index = MotionEventCompat.getActionIndex(ev);
/* 2032 */                  float x5 = MotionEventCompat.getX(ev, index);
/* 2033 */                  this.mLastMotionX = x5;
/* 2034 */                  this.mActivePointerId = MotionEventCompat.getPointerId(ev, index);
                            break;
                        case 6:
/* 2038 */                  onSecondaryPointerUp(ev);
/* 2039 */                  this.mLastMotionX = MotionEventCompat.getX(ev, MotionEventCompat.findPointerIndex(ev, this.mActivePointerId));
                            break;
                    }
/* 2043 */          if (needsInvalidate) {
/* 2044 */              ViewCompat.postInvalidateOnAnimation(this);
                    }
/* 2046 */          return true;
                }

/* 2049 */      private void requestParentDisallowInterceptTouchEvent(boolean disallowIntercept) {
/* 2050 */          ViewParent parent = getParent();
/* 2051 */          if (parent != null) {
/* 2052 */              parent.requestDisallowInterceptTouchEvent(disallowIntercept);
                    }
                }

/* 2056 */      private boolean performDrag(float x) {
/* 2057 */          boolean needsInvalidate = false;
/* 2059 */          float deltaX = this.mLastMotionX - x;
/* 2060 */          this.mLastMotionX = x;
/* 2062 */          float oldScrollX = getScrollX();
/* 2063 */          float scrollX = oldScrollX + deltaX;
/* 2064 */          int width = getClientWidth();
/* 2066 */          float leftBound = width * this.mFirstOffset;
/* 2067 */          float rightBound = width * this.mLastOffset;
/* 2068 */          boolean leftAbsolute = true;
/* 2069 */          boolean rightAbsolute = true;
/* 2071 */          ItemInfo firstItem = this.mItems.get(0);
/* 2072 */          ItemInfo lastItem = this.mItems.get(this.mItems.size() - 1);
/* 2073 */          if (firstItem.position != 0) {
/* 2074 */              leftAbsolute = false;
/* 2075 */              leftBound = firstItem.offset * width;
                    }
/* 2077 */          if (lastItem.position != this.mAdapter.getCount() - 1) {
/* 2078 */              rightAbsolute = false;
/* 2079 */              rightBound = lastItem.offset * width;
                    }
                    if (scrollX < leftBound) {
/* 2083 */              if (leftAbsolute) {
/* 2084 */                  float over = leftBound - scrollX;
/* 2085 */                  needsInvalidate = this.mLeftEdge.onPull(Math.abs(over) / width);
                        }
/* 2087 */              scrollX = leftBound;
                    } else if (scrollX > rightBound) {
/* 2089 */              if (rightAbsolute) {
/* 2090 */                  float over2 = scrollX - rightBound;
/* 2091 */                  needsInvalidate = this.mRightEdge.onPull(Math.abs(over2) / width);
                        }
/* 2093 */              scrollX = rightBound;
                    }
/* 2096 */          this.mLastMotionX += scrollX - ((int) scrollX);
/* 2097 */          scrollTo((int) scrollX, getScrollY());
/* 2098 */          pageScrolled((int) scrollX);
/* 2100 */          return needsInvalidate;
                }

/* 2107 */      private ItemInfo infoForCurrentScrollPosition() {
/* 2108 */          int width = getClientWidth();
/* 2109 */          float scrollOffset = width > 0 ? getScrollX() / width : 0.0f;
/* 2110 */          float marginOffset = width > 0 ? this.mPageMargin / width : 0.0f;
/* 2111 */          int lastPos = -1;
/* 2112 */          float lastOffset = 0.0f;
/* 2113 */          float lastWidth = 0.0f;
/* 2114 */          boolean first = true;
/* 2116 */          ItemInfo lastItem = null;
/* 2117 */          int i = 0;
                    while (i < this.mItems.size()) {
/* 2118 */              ItemInfo ii = this.mItems.get(i);
/* 2120 */              if (!first && ii.position != lastPos + 1) {
/* 2122 */                  ii = this.mTempItem;
/* 2123 */                  ii.offset = lastOffset + lastWidth + marginOffset;
/* 2124 */                  ii.position = lastPos + 1;
/* 2125 */                  ii.widthFactor = this.mAdapter.getPageWidth(ii.position);
/* 2126 */                  i--;
                        }
/* 2128 */              float offset = ii.offset;
/* 2131 */              float rightBound = ii.widthFactor + offset + marginOffset;
/* 2132 */              if (!first && scrollOffset < offset) {
/* 2146 */                  return lastItem;
                        }
                        if (scrollOffset >= rightBound && i != this.mItems.size() - 1) {
/* 2139 */                  first = false;
/* 2140 */                  lastPos = ii.position;
/* 2141 */                  lastOffset = offset;
/* 2142 */                  lastWidth = ii.widthFactor;
/* 2143 */                  lastItem = ii;
/* 2117 */                  i++;
                        } else {
                            ItemInfo lastItem2 = ii;
/* 2146 */                  return lastItem2;
                        }
                    }
/* 2146 */          return lastItem;
                }

/* 2150 */      private int determineTargetPage(int currentPage, float pageOffset, int velocity, int deltaX) {
                    int targetPage;
/* 2151 */          if (Math.abs(deltaX) > this.mFlingDistance && Math.abs(velocity) > this.mMinimumVelocity) {
/* 2152 */              targetPage = velocity > 0 ? currentPage : currentPage + 1;
                    } else {
/* 2154 */              float truncator = currentPage >= this.mCurItem ? 0.4f : 0.6f;
/* 2155 */              targetPage = (int) (currentPage + pageOffset + truncator);
                    }
/* 2158 */          if (this.mItems.size() > 0) {
/* 2159 */              ItemInfo firstItem = this.mItems.get(0);
/* 2160 */              ItemInfo lastItem = this.mItems.get(this.mItems.size() - 1);
/* 2163 */              return Math.max(firstItem.position, Math.min(targetPage, lastItem.position));
                    }
/* 2166 */          return targetPage;
                }

                @Override // android.view.View
/* 2170 */      public void draw(Canvas canvas) {
/* 2171 */          super.draw(canvas);
/* 2172 */          boolean needsInvalidate = false;
/* 2174 */          int overScrollMode = ViewCompat.getOverScrollMode(this);
/* 2175 */          if (overScrollMode == 0 || (overScrollMode == 1 && this.mAdapter != null && this.mAdapter.getCount() > 1)) {
/* 2178 */              if (!this.mLeftEdge.isFinished()) {
/* 2179 */                  int restoreCount = canvas.save();
/* 2180 */                  int height = (getHeight() - getPaddingTop()) - getPaddingBottom();
/* 2181 */                  int width = getWidth();
/* 2183 */                  canvas.rotate(270.0f);
/* 2184 */                  canvas.translate((-height) + getPaddingTop(), this.mFirstOffset * width);
/* 2185 */                  this.mLeftEdge.setSize(height, width);
/* 2172 */                  needsInvalidate = false | this.mLeftEdge.draw(canvas);
/* 2187 */                  canvas.restoreToCount(restoreCount);
                        }
/* 2189 */              if (!this.mRightEdge.isFinished()) {
/* 2190 */                  int restoreCount2 = canvas.save();
/* 2191 */                  int width2 = getWidth();
/* 2192 */                  int height2 = (getHeight() - getPaddingTop()) - getPaddingBottom();
/* 2194 */                  canvas.rotate(90.0f);
/* 2195 */                  canvas.translate(-getPaddingTop(), (-(this.mLastOffset + 1.0f)) * width2);
/* 2196 */                  this.mRightEdge.setSize(height2, width2);
/* 2197 */                  needsInvalidate |= this.mRightEdge.draw(canvas);
/* 2198 */                  canvas.restoreToCount(restoreCount2);
                        }
                    } else {
/* 2201 */              this.mLeftEdge.finish();
/* 2202 */              this.mRightEdge.finish();
                    }
/* 2205 */          if (needsInvalidate) {
/* 2207 */              ViewCompat.postInvalidateOnAnimation(this);
                    }
                }

                @Override // android.view.View
/* 2212 */      protected void onDraw(Canvas canvas) {
                    float drawAt;
/* 2213 */          super.onDraw(canvas);
/* 2216 */          if (this.mPageMargin > 0 && this.mMarginDrawable != null && this.mItems.size() > 0 && this.mAdapter != null) {
/* 2217 */              int scrollX = getScrollX();
/* 2218 */              int width = getWidth();
/* 2220 */              float marginOffset = this.mPageMargin / width;
/* 2221 */              int itemIndex = 0;
/* 2222 */              ItemInfo ii = this.mItems.get(0);
/* 2223 */              float offset = ii.offset;
/* 2224 */              int itemCount = this.mItems.size();
/* 2225 */              int firstPos = ii.position;
/* 2226 */              int lastPos = this.mItems.get(itemCount - 1).position;
                        for (int pos = firstPos; pos < lastPos; pos++) {
/* 2228 */                  while (pos > ii.position && itemIndex < itemCount) {
                                itemIndex++;
/* 2229 */                      ii = this.mItems.get(itemIndex);
                            }
/* 2233 */                  if (pos == ii.position) {
/* 2234 */                      drawAt = (ii.offset + ii.widthFactor) * width;
/* 2235 */                      offset = ii.offset + ii.widthFactor + marginOffset;
                            } else {
/* 2237 */                      float widthFactor = this.mAdapter.getPageWidth(pos);
/* 2238 */                      drawAt = (offset + widthFactor) * width;
/* 2239 */                      offset += widthFactor + marginOffset;
                            }
                            if (this.mPageMargin + drawAt > scrollX) {
/* 2243 */                      this.mMarginDrawable.setBounds((int) drawAt, this.mTopPageBounds, (int) (this.mPageMargin + drawAt + 0.5f), this.mBottomPageBounds);
/* 2245 */                      this.mMarginDrawable.draw(canvas);
                            }
                            if (drawAt > scrollX + width) {
/* 2253 */                      return;
                            }
                        }
                    }
                }

/* 2272 */      public boolean beginFakeDrag() {
/* 2273 */          if (this.mIsBeingDragged) {
                        return false;
                    }
/* 2276 */          this.mFakeDragging = true;
/* 2277 */          setScrollState(1);
/* 2278 */          this.mLastMotionX = 0.0f;
                    this.mInitialMotionX = 0.0f;
/* 2279 */          if (this.mVelocityTracker == null) {
/* 2280 */              this.mVelocityTracker = VelocityTracker.obtain();
                    } else {
/* 2282 */              this.mVelocityTracker.clear();
                    }
/* 2284 */          long time = SystemClock.uptimeMillis();
/* 2285 */          MotionEvent ev = MotionEvent.obtain(time, time, 0, 0.0f, 0.0f, 0);
/* 2286 */          this.mVelocityTracker.addMovement(ev);
/* 2287 */          ev.recycle();
/* 2288 */          this.mFakeDragBeginTime = time;
                    return true;
                }

/* 2298 */      public void endFakeDrag() {
/* 2299 */          if (!this.mFakeDragging) {
/* 2300 */              throw new IllegalStateException("No fake drag in progress. Call beginFakeDrag first.");
                    }
/* 2303 */          VelocityTracker velocityTracker = this.mVelocityTracker;
/* 2304 */          velocityTracker.computeCurrentVelocity(1000, this.mMaximumVelocity);
/* 2305 */          int initialVelocity = (int) VelocityTrackerCompat.getXVelocity(velocityTracker, this.mActivePointerId);
/* 2307 */          this.mPopulatePending = true;
/* 2308 */          int width = getClientWidth();
/* 2309 */          int scrollX = getScrollX();
/* 2310 */          ItemInfo ii = infoForCurrentScrollPosition();
/* 2311 */          int currentPage = ii.position;
/* 2312 */          float pageOffset = ((scrollX / width) - ii.offset) / ii.widthFactor;
/* 2313 */          int totalDelta = (int) (this.mLastMotionX - this.mInitialMotionX);
/* 2314 */          int nextPage = determineTargetPage(currentPage, pageOffset, initialVelocity, totalDelta);
/* 2316 */          setCurrentItemInternal(nextPage, true, true, initialVelocity);
/* 2317 */          endDrag();
/* 2319 */          this.mFakeDragging = false;
                }

/* 2329 */      public void fakeDragBy(float xOffset) {
/* 2330 */          if (!this.mFakeDragging) {
/* 2331 */              throw new IllegalStateException("No fake drag in progress. Call beginFakeDrag first.");
                    }
/* 2334 */          this.mLastMotionX += xOffset;
/* 2336 */          float oldScrollX = getScrollX();
/* 2337 */          float scrollX = oldScrollX - xOffset;
/* 2338 */          int width = getClientWidth();
/* 2340 */          float leftBound = width * this.mFirstOffset;
/* 2341 */          float rightBound = width * this.mLastOffset;
/* 2343 */          ItemInfo firstItem = this.mItems.get(0);
/* 2344 */          ItemInfo lastItem = this.mItems.get(this.mItems.size() - 1);
/* 2345 */          if (firstItem.position != 0) {
/* 2346 */              leftBound = firstItem.offset * width;
                    }
/* 2348 */          if (lastItem.position != this.mAdapter.getCount() - 1) {
/* 2349 */              rightBound = lastItem.offset * width;
                    }
                    if (scrollX < leftBound) {
/* 2353 */              scrollX = leftBound;
                    } else if (scrollX > rightBound) {
/* 2355 */              scrollX = rightBound;
                    }
/* 2358 */          this.mLastMotionX += scrollX - ((int) scrollX);
/* 2359 */          scrollTo((int) scrollX, getScrollY());
/* 2360 */          pageScrolled((int) scrollX);
/* 2363 */          long time = SystemClock.uptimeMillis();
/* 2364 */          MotionEvent ev = MotionEvent.obtain(this.mFakeDragBeginTime, time, 2, this.mLastMotionX, 0.0f, 0);
/* 2366 */          this.mVelocityTracker.addMovement(ev);
/* 2367 */          ev.recycle();
                }

/* 2379 */      public boolean isFakeDragging() {
/* 2380 */          return this.mFakeDragging;
                }

/* 2383 */      private void onSecondaryPointerUp(MotionEvent ev) {
/* 2384 */          int pointerIndex = MotionEventCompat.getActionIndex(ev);
/* 2385 */          int pointerId = MotionEventCompat.getPointerId(ev, pointerIndex);
/* 2386 */          if (pointerId == this.mActivePointerId) {
/* 2389 */              int newPointerIndex = pointerIndex == 0 ? 1 : 0;
/* 2390 */              this.mLastMotionX = MotionEventCompat.getX(ev, newPointerIndex);
/* 2391 */              this.mActivePointerId = MotionEventCompat.getPointerId(ev, newPointerIndex);
/* 2392 */              if (this.mVelocityTracker != null) {
/* 2393 */                  this.mVelocityTracker.clear();
                        }
                    }
                }

/* 2398 */      private void endDrag() {
/* 2399 */          this.mIsBeingDragged = false;
/* 2400 */          this.mIsUnableToDrag = false;
/* 2402 */          if (this.mVelocityTracker != null) {
/* 2403 */              this.mVelocityTracker.recycle();
/* 2404 */              this.mVelocityTracker = null;
                    }
                }

/* 2408 */      private void setScrollingCacheEnabled(boolean enabled) {
/* 2409 */          if (this.mScrollingCacheEnabled != enabled) {
/* 2410 */              this.mScrollingCacheEnabled = enabled;
                    }
                }

                @Override // android.view.View
/* 2423 */      public boolean canScrollHorizontally(int direction) {
/* 2424 */          if (this.mAdapter == null) {
                        return false;
                    }
/* 2428 */          int width = getClientWidth();
/* 2429 */          int scrollX = getScrollX();
/* 2430 */          if (direction < 0) {
/* 2431 */              return scrollX > ((int) (((float) width) * this.mFirstOffset));
                    }
/* 2432 */          if (direction > 0) {
/* 2433 */              return scrollX < ((int) (((float) width) * this.mLastOffset));
                    }
                    return false;
                }

/* 2450 */      protected boolean canScroll(View v, boolean checkV, int dx, int x, int y) {
/* 2451 */          if (v instanceof ViewGroup) {
/* 2452 */              ViewGroup group = (ViewGroup) v;
/* 2453 */              int scrollX = v.getScrollX();
/* 2454 */              int scrollY = v.getScrollY();
/* 2455 */              int count = group.getChildCount();
                        for (int i = count - 1; i >= 0; i--) {
/* 2460 */                  View child = group.getChildAt(i);
/* 2461 */                  if (x + scrollX >= child.getLeft() && x + scrollX < child.getRight() && y + scrollY >= child.getTop() && y + scrollY < child.getBottom() && canScroll(child, true, dx, (x + scrollX) - child.getLeft(), (y + scrollY) - child.getTop())) {
/* 2465 */                      return true;
                            }
                        }
                    }
                    return checkV && ViewCompat.canScrollHorizontally(v, -dx);
                }

                @Override // android.view.ViewGroup, android.view.View
/* 2475 */      public boolean dispatchKeyEvent(KeyEvent event) {
                    return super.dispatchKeyEvent(event) || executeKeyEvent(event);
                }

/* 2487 */      public boolean executeKeyEvent(KeyEvent event) {
/* 2489 */          if (event.getAction() != 0) {
/* 2488 */              return false;
                    }
                    switch (event.getKeyCode()) {
                        case 21:
/* 2492 */                  boolean handled = arrowScroll(17);
/* 2510 */                  return handled;
                        case 22:
/* 2495 */                  boolean handled2 = arrowScroll(66);
/* 2510 */                  return handled2;
                        case 61:
/* 2498 */                  if (Build.VERSION.SDK_INT < 11) {
/* 2488 */                      return false;
                            }
/* 2501 */                  if (KeyEventCompat.hasNoModifiers(event)) {
/* 2502 */                      boolean handled3 = arrowScroll(2);
/* 2510 */                      return handled3;
                            }
/* 2503 */                  if (!KeyEventCompat.hasModifiers(event, 1)) {
/* 2488 */                      return false;
                            }
/* 2504 */                  boolean handled4 = arrowScroll(1);
/* 2510 */                  return handled4;
                        default:
/* 2488 */                  return false;
                    }
                }

/* 2513 */      public boolean arrowScroll(int direction) {
/* 2514 */          View currentFocused = findFocus();
/* 2515 */          if (currentFocused == this) {
/* 2516 */              currentFocused = null;
                    } else if (currentFocused != null) {
/* 2518 */              boolean isChild = false;
                        for (ViewParent parent = currentFocused.getParent(); parent instanceof ViewGroup; parent = parent.getParent()) {
/* 2521 */                  if (parent == this) {
/* 2522 */                      isChild = true;
                                break;
                            }
                        }
/* 2526 */              if (!isChild) {
/* 2528 */                  StringBuilder sb = new StringBuilder();
/* 2529 */                  sb.append(currentFocused.getClass().getSimpleName());
                            for (ViewParent parent2 = currentFocused.getParent(); parent2 instanceof ViewGroup; parent2 = parent2.getParent()) {
/* 2532 */                      sb.append(" => ").append(parent2.getClass().getSimpleName());
                            }
/* 2534 */                  Log.e(TAG, "arrowScroll tried to find focus based on non-child current focused view " + sb.toString());
/* 2536 */                  currentFocused = null;
                        }
                    }
/* 2540 */          boolean handled = false;
/* 2542 */          View nextFocused = FocusFinder.getInstance().findNextFocus(this, currentFocused, direction);
/* 2544 */          if (nextFocused != null && nextFocused != currentFocused) {
/* 2545 */              if (direction == 17) {
/* 2548 */                  int nextLeft = getChildRectInPagerCoordinates(this.mTempRect, nextFocused).left;
/* 2549 */                  int currLeft = getChildRectInPagerCoordinates(this.mTempRect, currentFocused).left;
/* 2553 */                  handled = (currentFocused == null || nextLeft < currLeft) ? nextFocused.requestFocus() : pageLeft();
                        } else if (direction == 66) {
/* 2558 */                  int nextLeft2 = getChildRectInPagerCoordinates(this.mTempRect, nextFocused).left;
/* 2559 */                  int currLeft2 = getChildRectInPagerCoordinates(this.mTempRect, currentFocused).left;
/* 2563 */                  handled = (currentFocused == null || nextLeft2 > currLeft2) ? nextFocused.requestFocus() : pageRight();
                        }
                    } else if (direction == 17 || direction == 1) {
/* 2568 */              handled = pageLeft();
                    } else if (direction == 66 || direction == 2) {
/* 2571 */              handled = pageRight();
                    }
/* 2573 */          if (handled) {
/* 2574 */              playSoundEffect(SoundEffectConstants.getContantForFocusDirection(direction));
                    }
/* 2576 */          return handled;
                }

/* 2579 */      private Rect getChildRectInPagerCoordinates(Rect outRect, View child) {
/* 2580 */          if (outRect == null) {
/* 2581 */              outRect = new Rect();
                    }
/* 2583 */          if (child == null) {
/* 2584 */              outRect.set(0, 0, 0, 0);
                    } else {
/* 2587 */              outRect.left = child.getLeft();
/* 2588 */              outRect.right = child.getRight();
/* 2589 */              outRect.top = child.getTop();
/* 2590 */              outRect.bottom = child.getBottom();
/* 2592 */              ViewParent parent = child.getParent();
/* 2593 */              while ((parent instanceof ViewGroup) && parent != this) {
/* 2594 */                  ViewGroup group = (ViewGroup) parent;
/* 2595 */                  outRect.left += group.getLeft();
/* 2596 */                  outRect.right += group.getRight();
/* 2597 */                  outRect.top += group.getTop();
/* 2598 */                  outRect.bottom += group.getBottom();
/* 2600 */                  parent = group.getParent();
                        }
                    }
/* 2602 */          return outRect;
                }

/* 2605 */      boolean pageLeft() {
/* 2606 */          if (this.mCurItem <= 0) {
                        return false;
                    }
/* 2607 */          setCurrentItem(this.mCurItem - 1, true);
                    return true;
                }

/* 2613 */      boolean pageRight() {
/* 2614 */          if (this.mAdapter == null || this.mCurItem >= this.mAdapter.getCount() - 1) {
                        return false;
                    }
/* 2615 */          setCurrentItem(this.mCurItem + 1, true);
                    return true;
                }

                @Override // android.view.ViewGroup, android.view.View
/* 2625 */      public void addFocusables(ArrayList<View> views, int direction, int focusableMode) {
                    ItemInfo ii;
/* 2626 */          int focusableCount = views.size();
/* 2628 */          int descendantFocusability = getDescendantFocusability();
/* 2630 */          if (descendantFocusability != 393216) {
                        for (int i = 0; i < getChildCount(); i++) {
/* 2632 */                  View child = getChildAt(i);
/* 2633 */                  if (child.getVisibility() == 0 && (ii = infoForChild(child)) != null && ii.position == this.mCurItem) {
/* 2636 */                      child.addFocusables(views, direction, focusableMode);
                            }
                        }
                    }
/* 2646 */          if ((descendantFocusability != 262144 || focusableCount == views.size()) && isFocusable()) {
/* 2655 */              if (((focusableMode & 1) != 1 || !isInTouchMode() || isFocusableInTouchMode()) && views != null) {
/* 2660 */                  views.add(this);
                        }
                    }
                }

                @Override // android.view.ViewGroup, android.view.View
/* 2672 */      public void addTouchables(ArrayList<View> views) {
                    ItemInfo ii;
                    for (int i = 0; i < getChildCount(); i++) {
/* 2674 */              View child = getChildAt(i);
/* 2675 */              if (child.getVisibility() == 0 && (ii = infoForChild(child)) != null && ii.position == this.mCurItem) {
/* 2678 */                  child.addTouchables(views);
                        }
                    }
                }

                @Override // android.view.ViewGroup
/* 2692 */      protected boolean onRequestFocusInDescendants(int direction, Rect previouslyFocusedRect) {
                    int index;
                    int increment;
                    int end;
                    ItemInfo ii;
/* 2693 */          int count = getChildCount();
/* 2694 */          if ((direction & 2) != 0) {
/* 2695 */              index = 0;
/* 2696 */              increment = 1;
/* 2697 */              end = count;
                    } else {
/* 2699 */              index = count - 1;
/* 2700 */              increment = -1;
/* 2701 */              end = -1;
                    }
                    for (int i = index; i != end; i += increment) {
/* 2704 */              View child = getChildAt(i);
/* 2705 */              if (child.getVisibility() == 0 && (ii = infoForChild(child)) != null && ii.position == this.mCurItem && child.requestFocus(direction, previouslyFocusedRect)) {
/* 2709 */                  return true;
                        }
                    }
/* 2714 */          return false;
                }

                @Override // android.view.View
/* 2719 */      public boolean dispatchPopulateAccessibilityEvent(AccessibilityEvent event) {
                    ItemInfo ii;
/* 2720 */          if (event.getEventType() == 4096) {
/* 2721 */              return super.dispatchPopulateAccessibilityEvent(event);
                    }
/* 2725 */          int childCount = getChildCount();
                    for (int i = 0; i < childCount; i++) {
/* 2727 */              View child = getChildAt(i);
/* 2728 */              if (child.getVisibility() == 0 && (ii = infoForChild(child)) != null && ii.position == this.mCurItem && child.dispatchPopulateAccessibilityEvent(event)) {
/* 2732 */                  return true;
                        }
                    }
/* 2737 */          return false;
                }

                @Override // android.view.ViewGroup
/* 2741 */      protected ViewGroup.LayoutParams generateDefaultLayoutParams() {
/* 2742 */          return new LayoutParams();
                }

                @Override // android.view.ViewGroup
/* 2746 */      protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams p) {
/* 2747 */          return generateDefaultLayoutParams();
                }

                @Override // android.view.ViewGroup
/* 2751 */      protected boolean checkLayoutParams(ViewGroup.LayoutParams p) {
                    return (p instanceof LayoutParams) && super.checkLayoutParams(p);
                }

                @Override // android.view.ViewGroup
/* 2756 */      public ViewGroup.LayoutParams generateLayoutParams(AttributeSet attrs) {
/* 2757 */          return new LayoutParams(getContext(), attrs);
                }

/* 2758 */      class MyAccessibilityDelegate extends AccessibilityDelegateCompat {
/* 2759 */          MyAccessibilityDelegate() {
                    }

                    @Override // android.support.v4.view.AccessibilityDelegateCompat
/* 2763 */          public void onInitializeAccessibilityEvent(View host, AccessibilityEvent event) {
/* 2764 */              super.onInitializeAccessibilityEvent(host, event);
/* 2765 */              event.setClassName(ViewPager.class.getName());
/* 2766 */              AccessibilityRecordCompat recordCompat = AccessibilityRecordCompat.obtain();
/* 2767 */              recordCompat.setScrollable(canScroll());
/* 2768 */              if (event.getEventType() == 4096 && ViewPager.access$200(ViewPager.this) != null) {
/* 2770 */                  recordCompat.setItemCount(ViewPager.access$200(ViewPager.this).getCount());
/* 2771 */                  recordCompat.setFromIndex(ViewPager.access$300(ViewPager.this));
/* 2772 */                  recordCompat.setToIndex(ViewPager.access$300(ViewPager.this));
                        }
                    }

                    @Override // android.support.v4.view.AccessibilityDelegateCompat
/* 2777 */          public void onInitializeAccessibilityNodeInfo(View host, AccessibilityNodeInfoCompat info) {
/* 2778 */              super.onInitializeAccessibilityNodeInfo(host, info);
/* 2779 */              info.setClassName(ViewPager.class.getName());
/* 2780 */              info.setScrollable(canScroll());
/* 2781 */              if (ViewPager.this.canScrollHorizontally(1)) {
/* 2782 */                  info.addAction(4096);
                        }
/* 2784 */              if (ViewPager.this.canScrollHorizontally(-1)) {
/* 2785 */                  info.addAction(8192);
                        }
                    }

                    @Override // android.support.v4.view.AccessibilityDelegateCompat
/* 2790 */          public boolean performAccessibilityAction(View host, int action, Bundle args) {
/* 2791 */              if (super.performAccessibilityAction(host, action, args)) {
                            return true;
                        }
                        switch (action) {
                            case 4096:
/* 2796 */                      if (!ViewPager.this.canScrollHorizontally(1)) {
                                    return false;
                                }
/* 2797 */                      ViewPager.this.setCurrentItem(ViewPager.access$300(ViewPager.this) + 1);
                                return true;
                            case 8192:
/* 2802 */                      if (!ViewPager.this.canScrollHorizontally(-1)) {
                                    return false;
                                }
/* 2803 */                      ViewPager.this.setCurrentItem(ViewPager.access$300(ViewPager.this) - 1);
                                return true;
                            default:
                                return false;
                        }
                    }

/* 2811 */          private boolean canScroll() {
                        return ViewPager.access$200(ViewPager.this) != null && ViewPager.access$200(ViewPager.this).getCount() > 1;
                    }
                }

/* 2814 */      private class PagerObserver extends DataSetObserver {
/* 2815 */          private PagerObserver() {
                    }

/* 2815 */          /* synthetic */ PagerObserver(ViewPager x0, C00511 x1) {
/* 2816 */              this();
                    }

                    @Override // android.database.DataSetObserver
/* 2818 */          public void onChanged() {
/* 2819 */              ViewPager.this.dataSetChanged();
                    }

                    @Override // android.database.DataSetObserver
/* 2822 */          public void onInvalidated() {
/* 2823 */              ViewPager.this.dataSetChanged();
                    }
                }

/* 2865 */      public static class LayoutParams extends ViewGroup.LayoutParams {
                    int childIndex;
                    public int gravity;
                    public boolean isDecor;
                    boolean needsMeasure;
                    int position;
                    float widthFactor;

/* 2866 */          public LayoutParams() {
/* 2867 */              super(-1, -1);
/* 2848 */              this.widthFactor = 0.0f;
                    }

/* 2870 */          public LayoutParams(Context context, AttributeSet attrs) {
/* 2871 */              super(context, attrs);
/* 2848 */              this.widthFactor = 0.0f;
/* 2873 */              TypedArray a = context.obtainStyledAttributes(attrs, ViewPager.access$400());
/* 2874 */              this.gravity = a.getInteger(0, 48);
/* 2875 */              a.recycle();
                    }
                }

/* 2877 */      static class ViewPositionComparator implements Comparator<View> {
/* 2878 */          ViewPositionComparator() {
                    }

                    @Override // java.util.Comparator
/* 2878 */          public /* bridge */ /* synthetic */ int compare(View view, View view2) {
/* 2879 */              return compare2(view, view2);
                    }

                    /* JADX INFO: renamed from: compare, reason: avoid collision after fix types in other method */
/* 2881 */          public int compare2(View lhs, View rhs) {
/* 2882 */              LayoutParams llp = (LayoutParams) lhs.getLayoutParams();
/* 2883 */              LayoutParams rlp = (LayoutParams) rhs.getLayoutParams();
/* 2884 */              if (llp.isDecor != rlp.isDecor) {
                            return llp.isDecor ? 1 : -1;
                        }
/* 2887 */              return llp.position - rlp.position;
                    }
                }
            }
